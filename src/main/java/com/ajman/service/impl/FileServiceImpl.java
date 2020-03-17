package com.ajman.service.impl;

import com.ajman.service.IFileService;
import com.ajman.utils.FTPUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
@Service("fileService")
public class FileServiceImpl implements IFileService {
    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file,String path){
        String fileName=file.getOriginalFilename();//获取原来文件名
        //获取扩展名,+1只要扩展名
        String fileExtensionName=fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName= UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传文件名：{}，上传的路径：{}，新文件名：{}",fileName,path,uploadFileName);
        File fileDir=new File(path);
        if(!fileDir.exists()){
            //创建文件夹
            //获取权限 mkdirs可以连续创建文件路径:/a/b/c
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile=new File(path,uploadFileName);

        try {
            //SpringMVC的保存文件
            file.transferTo(targetFile);
            //文件上传成功
            //将targetFile上传到我们的FTP服务器
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //TODO 上传完之后，删除upload下面的文件
            targetFile.delete();

        } catch (IOException e) {
            logger.info("上传文件异常",e);
            return null;
        }


        return targetFile.getName();


    }

}
