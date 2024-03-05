package com.suidao.service;

import cn.hutool.http.server.HttpServerResponse;
import com.suidao.base.Result;
import com.suidao.pojo.File;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
* @author zhangqingtian
* @description 针对表【file】的数据库操作Service
* @createDate 2023-04-13 14:48:59
*/
public interface FileService extends IService<File> {

    Result upload(MultipartFile file);

    boolean removeFile(Long id);

    boolean removeFileByUrl(String url);

    void downloadFile(Long id, HttpServletResponse response);
}
