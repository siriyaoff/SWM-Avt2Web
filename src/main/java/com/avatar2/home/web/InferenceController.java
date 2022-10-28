package com.avatar2.home.web;

import com.avatar2.home.domain.inference.PElem;
import com.avatar2.home.web.dto.InferenceRequestDto;
import com.avatar2.home.web.dto.InferenceResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEObjectJSON;
import net.bytebuddy.asm.Advice;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.Cookie;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

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

    @PostMapping("/reqdto")
    public String reqdto(MultipartHttpServletRequest request) throws IOException {
        try {
            String height = request.getParameter("height");
            MultipartFile psd=request.getFile("psd");
            byte[] b = psd.getBytes();

            FileUtils.writeByteArrayToFile(new File("C:\\Users\\yang\\Downloads\\realtrue.png"), b);

            return height+"\nheight printed";
        } catch (Exception e) {
            System.out.println(e.getStackTrace()[0]);
        }
        return "error";
    }

    @GetMapping("/corstest")
    public String corstest() {
        WebClient webClient = WebClient.create("http://ec2-13-124-191-61.ap-northeast-2.compute.amazonaws.com:8080");
        return webClient.post()
                .uri("/stringtest")
                //.contentType(MediaType.APPLICATION_JSON)
                .bodyValue("testString")
                .retrieve()
                .bodyToMono(String.class)// response 파일
                .block();
    }

    @GetMapping("/mpfsave")
    public String mpfsave() throws IOException, InterruptedException, FileNotFoundException, ParseException {

        //File file = new File("/Users/yanghyowon/Downloads/tpsd.psd");
        File file = new File("C:\\Users\\yang\\Downloads\\serveriris.png");
        FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
        InputStream input = new FileInputStream(file);
        OutputStream os = fileItem.getOutputStream();
        IOUtils.copy(input, os);
        MultipartFile psd = new CommonsMultipartFile(fileItem);

        byte[] b=psd.getBytes();
        String psdb = Arrays.toString(b);

        FileUtils.writeByteArrayToFile(new File("C:\\Users\\yang\\Downloads\\r3.png"), b);

        return ""+psdb.length();
    }
}
