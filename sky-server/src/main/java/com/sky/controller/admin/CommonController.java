package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author YueYang
 * Created on 2025/9/24 14:41
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

//    @PostMapping("/upload")
//    @ApiOperation("文件上传")
//    public Result<String> upload(MultipartFile file) {
//        log.info("文件上传: {}", file);
//
//        //将文件上传到阿里云服务器中
//        try {
//            //修改上传的文件名字
//            String originalFilename = file.getOriginalFilename();
//            String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
//
//            // 上传文件后 获取文件的存储路径
//            String filePath = aliOssUtil.upload(file.getBytes(), fileName);
//            return Result.success("上传成功", filePath);
//        } catch (IOException e) {
//            log.error("文件上传失败: {}", e.getMessage());
//        }
//        return Result.error(MessageConstant.UPLOAD_FAILED);
//    }

    @PostMapping("/upload")
    @ApiOperation("本地文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("本地文件上传: {}", file);

        //文件上传到F:\newProjects\cangQiongWaiMai\images
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        //保存文件
        try {
            File file1 = new File("F:\\newProjects\\cangQiongWaiMai\\images\\" + fileName);
            file.transferTo(file1);
            String strName = "F:\\newProjects\\cangQiongWaiMai\\images\\" + fileName;
            return Result.success("上传成功", strName);


        } catch (IOException e) {
            log.error("文件上传失败: {}", e.getMessage());
        }

        return Result.error("文件上传失败");
    }


}
