package com.suidao.service.impl;

import cn.hutool.http.server.HttpServerResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suidao.base.Result;
import com.suidao.base.SqlConf;
import com.suidao.base.SysConf;
import com.suidao.pojo.File;
import com.suidao.service.FileService;
import com.suidao.mapper.FileMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;

/**
* @author zhangqingtian
* @description 针对表【file】的数据库操作Service实现
* @createDate 2023-04-13 14:48:59
*/
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File>
    implements FileService{
    @Override
    public Result upload(MultipartFile file) {
        try {
            String originName = file.getOriginalFilename();
            //生成文件新的名称
            String suffix = originName.substring(originName.lastIndexOf("."));
            String newfileName =  System.currentTimeMillis() + suffix ;
            //首先根据sysconf的上传路径进行文件上传
            String savePath = SysConf.getUploadurl() + newfileName;
            String url = SysConf.fireUrlPrefix + java.io.File.separator + newfileName;
            java.io.File saveFile = new java.io.File(savePath);
            if (!saveFile.exists())
                saveFile.mkdirs();
            file.transferTo(saveFile);
            //存储到数据库之中
            File file1 = new File();
            file1.setNewname(newfileName);
            file1.setOriginalname(originName);
            file1.setCreatetime(new Date());
            file1.setUpdatetime(new Date());
            file1.setUrl(url);
            baseMapper.insert(file1);
            HashMap<String, Object> result = new HashMap<>();
            result.put("fileId",file1.getId().toString());
            result.put("orginalName",file1.getOriginalname());
            result.put("fileUrl",file1.getUrl());
            return Result.ok(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeFile(Long id) {
        File file = this.getById(id);
        if (file == null)
            return false;
        boolean b = this.removeById(id);
        java.io.File file1 = new java.io.File(SysConf.getUploadurl() + file.getNewname());
        boolean delete = file1.delete();
        return b && delete;
    }

    @Override
    public boolean removeFileByUrl(String url) {
        QueryWrapper<File> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq(SqlConf.url,url);
        File file = this.getOne(fileQueryWrapper);
        if (file == null)
            return false;
        boolean b = this.removeById(file.getId());
        java.io.File file1 = new java.io.File(SysConf.getUploadurl() + file.getNewname());
        boolean delete = file1.delete();
        return b && delete;
    }

    @Override
    public void downloadFile(Long id, HttpServletResponse response) {
        //根据id获取到文件
        File file = this.getById(id);
        if (file == null)
            return;
        String fileName = file.getOriginalname();
        String newFileName = file.getNewname();
        java.io.File file1 = new java.io.File(SysConf.getUploadurl() + newFileName);
        BufferedInputStream bs = null;
        byte[] buffer = new byte[1024];
        OutputStream os = null;
        if (file1 == null)
            return;
        try {
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
            response.setCharacterEncoding("UTF-8");
             os = response.getOutputStream();
            bs  = new BufferedInputStream(new FileInputStream(file1));
            while (bs.read(buffer)!=-1){
                os.write(buffer);
            }
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (bs != null)
                     bs.close();
                if (os!= null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}




