package com.avatar2.home.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class InferenceRequestDto {
    //private Long id;
    private int height;
    private MultipartFile psd;
    private String curtime;


    @Builder
    public InferenceRequestDto(int height, MultipartFile psd){
        //this.id = id;
        this.height = height;
        this.psd = psd;
        this.curtime = LocalTime.now().toString().substring(0, 12);
    }
}
