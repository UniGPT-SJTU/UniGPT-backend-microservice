package com.unigpt.bot.controller;

import com.google.gson.Gson;
import com.unigpt.bot.dto.FileUploadOkResponseDTO;
import com.unigpt.bot.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import java.io.File;

@RestController
@RequestMapping("/api/file")
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<ResponseDTO> uploadFile( @RequestParam("file") MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String filenameWithoutExtension = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            File tempFile = File.createTempFile(filenameWithoutExtension + "-", extension);

            file.transferTo(tempFile);

            // 使用Unirest发送请求给图片服务器
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post("http://123.60.187.205:10339/upload")
                    .header("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                    .header("Content-Type", "multipart/form-data")
                    .field("file", tempFile)
                    .asString();

            if(response.getStatus() != 200) {
                throw new Exception("Failed to upload file");
            }

            Gson gson = new Gson();
            FileUploadOkResponseDTO dto = gson.fromJson(response.getBody(), FileUploadOkResponseDTO.class);

            String imageUrl = dto.getUrl();

            // 返回图片URL给前端
            return ResponseEntity.ok(new ResponseDTO(true, imageUrl));

        } catch(Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(false, "Failed to upload file"));
        }
    }
}

