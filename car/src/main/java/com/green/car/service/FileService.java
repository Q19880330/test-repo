package com.green.car.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class FileService {
    //파일등록, 파일삭제 메소드 만드는 class
    //파일등록하기
    //매개변수로 받는 fileData는 그 파일 데이터 자체를 말함
    //경로, 원본파일 이름, file자체
    public String uploadFile(String uploadPath, String originalFile, byte[] fileData) throws Exception {
        //uuid 생성 - 파일명이 중복되지 않도록 파일명을 랜덤으로 만들어 줌
        UUID uuid = UUID.randomUUID();
        //확장자 - 만약 dog.jpg면 .jpg만 따로 떼서 extension에 저장
        String extension = originalFile.substring(originalFile.lastIndexOf("."));
        //새로운 파일명 만들기 - uuid + 확장자
        String saveFileName = uuid.toString()+extension;
        //경로와 파일명 더하기
        //shop/item/uuid.jpg
        String fileUploadFullUrl = uploadPath+"/"+saveFileName;
        //파일 출력 스트림 만들기
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        //파일 저장하기
        fos.write(fileData);
        fos.close();
        //DB에 저장하기 위해 따로 return 받는다
        return saveFileName;
    }
    //파일삭제하기
    public void deleteFiles(String filePath){
        //파일 객체 생성
        File deleteFile = new File(filePath);
        //해당 파일이 존재하면 삭제
        if(deleteFile.exists()){
            deleteFile.delete();
            System.out.println("파일을 삭제했습니다.");
        }else {
            System.out.println("파일이 존재하지 않습니다.");
        }
    }
}
