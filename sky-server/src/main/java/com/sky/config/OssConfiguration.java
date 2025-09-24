package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YueYang
 * Created on 2025/9/24 15:13
 * @version 1.0
 *
 * 用户创建AliOssUtil对象，将配置文件中的配置属性赋值给对象
 */
@Slf4j
@Configuration
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean //没有这个bean的时候创建该Bean对象
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        log.info("开始上传阿里云文件对象: {}",aliOssProperties);
        return new AliOssUtil(aliOssProperties.getEndpoint(),aliOssProperties.getAccessKeyId(),aliOssProperties.getAccessKeySecret(),aliOssProperties.getBucketName());
    }



}
