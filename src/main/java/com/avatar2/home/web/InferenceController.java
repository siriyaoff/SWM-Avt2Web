package com.avatar2.home.web;

import com.avatar2.home.web.dto.InferenceRequestDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;


@RestController
public class InferenceController {
    @PostMapping("/create")
    public InferenceRequestDto inference(@RequestBody InferenceRequestDto infDto){
        WebClient webClient = WebClient.create("http://ec2-13-124-191-61.ap-northeast-2.compute.amazonaws.com:8080");
        return webClient.post()
                .uri("/inference")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(infDto)
                .retrieve()
                .bodyToMono(InferenceRequestDto.class)// response 파일
                .block();
    }
}
