package com.gk.study.controller;

import com.gk.study.service.ThingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: 24119
 * @DateTime: 2024/1/13 19:09
 **/
@RestController
@RequestMapping("/staticfiles")
public class StaticFileController {

    private final static Logger logger = LoggerFactory.getLogger(ThingController.class);

    @Autowired
    ThingService service;

    @Value("${File.uploadPath}")
    private String basePath;


    @GetMapping("/image/{filename}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable("filename") String filename) throws IOException {
        // 检查文件是否存在
        Path imagePath = Paths.get(basePath,"image", filename);
        if (!Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }
        logger.info("{}",imagePath);
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        // 设置响应头信息
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaTypeFactory.getMediaType(filename).orElse(MediaType.APPLICATION_OCTET_STREAM);
//        MediaType mediaType = MediaType.parseMediaType(extension); // 根据文件扩展名获取MIME类型
        headers.setContentType(mediaType);

        // 创建并返回资源对象
        FileSystemResource fileSystemResource = new FileSystemResource(imagePath.toFile());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileSystemResource.contentLength())
                .body(fileSystemResource);
    }

    @GetMapping("/raw/{filename}")
    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable("filename") String filename) throws IOException {
        // 检查文件是否存在
        Path imagePath = Paths.get(basePath,"raw", filename);
        if (!Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }
        logger.info("{}",imagePath);
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        // 设置响应头信息
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaTypeFactory.getMediaType(filename).orElse(MediaType.APPLICATION_OCTET_STREAM);
//        MediaType mediaType = MediaType.parseMediaType(extension); // 根据文件扩展名获取MIME类型
        headers.setContentType(mediaType);

        // 创建并返回资源对象
        FileSystemResource fileSystemResource = new FileSystemResource(imagePath.toFile());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileSystemResource.contentLength())
                .body(fileSystemResource);
    }
}
