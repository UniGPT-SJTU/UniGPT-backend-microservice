package com.unigpt.plugin.serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.unigpt.plugin.service.DockerService;

@Service
public class DockerServiceImpl implements DockerService {

    public String invokeFunction(String filePath, String moduleName, String functionName, List<String> params) {
        try {
            // 获取当前工作目录
            String currentDir = new File("").getAbsolutePath();

            // 创建临时目录
            Path tempDir = Files.createTempDirectory("docker_temp");
            tempDir.toFile().deleteOnExit();

            // 提取run.py文件到临时目录
            Path runScriptPath = extractResourceToTempDir("src/main/resources/func/run.py", tempDir);

            JSONObject jsonParams = new JSONObject();
            jsonParams.put("params", params);

            // 构建Docker命令
            String[] command = {
                "docker", "run", "--rm",
                "-v", filePath + ":/app/" + moduleName + ".py",
                "-v", runScriptPath.toString() + ":/app/run.py",
                "mytest_py",
                "python3", "run.py",
                moduleName,
                functionName,
                jsonParams.toString()
            };

            // 执行命令
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(tempDir.toFile());
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.lines().collect(Collectors.joining("\n"));

            // 等待进程完成
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String errorOutput = errorReader.lines().collect(Collectors.joining("\n"));
                return new JSONObject().put("error", "Docker execution failed").put("details", errorOutput).toString();
            }

            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject().put("error", "Exception occurred").put("details", e.getMessage()).toString();
        }
    }

    private Path extractResourceToTempDir(String resourcePath, Path tempDir) throws IOException {
        try (InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (resourceStream == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }
            Path tempFile = tempDir.resolve(Paths.get(resourcePath).getFileName().toString());
            Files.copy(resourceStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            return tempFile;
        }
    }
}
