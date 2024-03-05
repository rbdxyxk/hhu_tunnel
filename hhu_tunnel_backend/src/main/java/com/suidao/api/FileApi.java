package com.suidao.api;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.suidao.base.Result;
import com.suidao.base.SysConf;
import com.suidao.pojo.User;
import com.suidao.service.FileService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RequestMapping("/file")
@RestController
public class FileApi {
    @Resource
    FileService fileService;
    @Resource
    @Lazy
    FileApi fileApi;

    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file) {
        return fileService.upload(file);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<byte[]> getFileContent(@PathVariable("id") Long id) {
        //根据id查找
        com.suidao.pojo.File fileServiceById = fileService.getById(id);
        if (fileServiceById == null)
            return null;
        File file = new File(SysConf.uploadurl + fileServiceById.getNewname());
        byte[] bytes = FileUtil.readBytes(file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename(file.getName()).build());
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    @GetMapping("/remove/{id}")
    public Result removeFileByUrl(@PathVariable("id") Long id) {
        boolean removeById = fileService.removeFile(id);
        return Result.ok(removeById);
    }

    @GetMapping("/download/{id}")
    public void downloadFile(@PathVariable Long id, HttpServletResponse response) {
        fileService.downloadFile(id, response);
    }


}
