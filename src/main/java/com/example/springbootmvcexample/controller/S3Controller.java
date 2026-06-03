package com.example.springbootmvcexample.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springbootmvcexample.service.S3Service;



@RestController
@RequestMapping("/api/s3")
public class S3Controller {
    private final S3Service s3Service;
    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String response = s3Service.uploadFile(file);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/download")
    public ResponseEntity<String> downloadFile(@RequestParam("key") String key, @RequestParam("downloadPath") String downloadpath){
        String response = s3Service.downloadFile(key,downloadpath);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles(){
        List<String> files = s3Service.listFiles();
        return ResponseEntity.ok(files);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam String key){
        String response = s3Service.deleteFile(key);
        return ResponseEntity.ok(response);
    }

}









