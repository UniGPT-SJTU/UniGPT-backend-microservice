package com.unigpt.chat.serviceimpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.json.JSONException;

import com.unigpt.chat.service.FunGraphService;

@Service
public class FunGraphServiceImpl implements FunGraphService {

    public String invokeFunction(String username, String moduleName, String functionName, List<String> params, String urn) {
        try {
            // Construct the command
            String paramsJson = params.toString().replace("[", "[\"").replace("]", "\"]").replace(", ", "\", \"");
            String body = String.format("{\"module_name\": \"%s\", \"function_name\": \"%s\", \"params\": %s}", moduleName, functionName, paramsJson);
            String command = String.format("hcloud FunctionGraph InvokeFunction --cli-region=\"cn-east-3\" --Content-Type=\"application/json\" --function_urn=\"%s\" --project_id=\"040dd9f934e74b52bcb92bb1ab4c9748\" --body='%s'", urn, body);

            // Execute the command using ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Capture the command output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);

            if (exitCode == 0) {
                // Print the raw command output
                System.out.println("Command output: " + output.toString());

                // Remove invalid escape characters by replacing them with an empty string
                String sanitizedOutput = output.toString().replace("\\", "");

                try {
                    // Parse the JSON output and extract the body
                    JSONObject jsonResponse = new JSONObject(sanitizedOutput);
                    // Print JSON output
                    return jsonResponse.getString("body");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return output.toString();
                }
            } else {
                return "Error: Command exited with code " + exitCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}