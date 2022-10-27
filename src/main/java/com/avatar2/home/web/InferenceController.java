package com.avatar2.home.web;

import com.avatar2.home.domain.inference.PElem;
import com.avatar2.home.web.dto.InferenceRequestDto;
import com.avatar2.home.web.dto.InferenceResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEObjectJSON;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


@RestController
public class InferenceController {
    @PostMapping("/create")
    public InferenceResponseDto inference(@RequestBody InferenceRequestDto infDto){
        WebClient webClient = WebClient.create("http://ec2-13-124-191-61.ap-northeast-2.compute.amazonaws.com:8080");
        return webClient.post()
                .uri("/inference")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(infDto)
                .retrieve()
                .bodyToMono(InferenceResponseDto.class)// response 파일
                .block();
    }

    @GetMapping("/test")
    public String testinf() throws IOException, InterruptedException, FileNotFoundException, ParseException {

        File file = new File("/Users/yanghyowon/Downloads/tpsd.psd");
        FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
        InputStream input = new FileInputStream(file);
        OutputStream os = fileItem.getOutputStream();
        IOUtils.copy(input, os);
        MultipartFile psd = new CommonsMultipartFile(fileItem);

        InferenceRequestDto inferenceRequestDto= InferenceRequestDto.builder()
                .height(165)
                .psd(psd)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(inferenceRequestDto);
//        WebClient webClient = WebClient.create("http://ec2-13-124-191-61.ap-northeast-2.compute.amazonaws.com:8080");
//
//        String str = webClient.post()
//                .uri("/inference")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(jsonStr)
//                .retrieve()
//                .bodyToMono(String.class)// response 파일
//                .block();
        return jsonStr;
    }
}
