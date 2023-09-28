package com.devtalk.board.consultationboardservice.board.adapter.in.web;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.devtalk.board.consultationboardservice.board.application.port.out.repository.PostRepo;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RefreshScope
@RequestMapping("/board/test")
public class TestApiController {
    private final PostRepo postRepo;
    private final AmazonS3Client amazonS3Client;

    @Value("${health_check}")
    String healthStatus;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @GetMapping("/config/health_check")
    public String healthCheck(){
        return healthStatus;
    }

    @PostMapping("/bulk")
    public void bulk(){
        for (long i = 1; i <= 100; i++) {
            Post newPost = Post.builder()
                    .userId(i)
                    .title(i + "번째 게시물")
                    .content(i + "번째 게시물 내용")
                    .views(0)
                    .build();

            postRepo.save(newPost);
        }
    }

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String folderName = "board-service";
            String objectKey = folderName + "/" + fileName;
            String fileUrl= "https://" + bucket + "/" + objectKey;

            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, objectKey, file.getInputStream(), metadata);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
