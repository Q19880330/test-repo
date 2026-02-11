package com.green.car.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //업로드한 파일을 읽어올 경로 설정
    @Value("${uploadPath}")     //변수에 application.properties에 있는 값 담아주기
    String uploadPath;          //file:///C:/shop

    //로컬컴퓨터에 업로드한 파일을 찾을 위치를 설정
    //images로 시작하는 경로로 요청하면 uploadPath 위치에 있는 파일을 찾겠다는 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //웹브라우저에 입력하는 url이 /images로 시작하는 경우에
        //uploadPath 프로퍼티 값을 읽어서 설정한 폴더를 기준으로 읽어오도록 설정
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
}
