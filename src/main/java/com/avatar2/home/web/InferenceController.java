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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.Cookie;
import javax.sound.midi.Soundbank;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class InferenceController {

    @PostMapping("/infreq")
    public InferenceResponseDto inference(MultipartHttpServletRequest req) {
        try {
            String height = req.getParameter("height");
            MultipartFile psd = req.getFile("psd");
            byte[] b = psd.getBytes();

            InferenceRequestDto reqdto = InferenceRequestDto.builder()
                    .height(Integer.parseInt(height))
                    .psd(psd)
                    .build();

            WebClient webClient = WebClient.create("http://ec2-13-124-191-61.ap-northeast-2.compute.amazonaws.com:8080");
            InferenceResponseDto resdto = webClient.post()
                    .uri("/inference")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(reqdto)
                    .retrieve()
                    .bodyToMono(InferenceResponseDto.class)// response 파일
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
        return new InferenceResponseDto();
    }

    @GetMapping("/ptrig") // trigger to post reqdto by webclient
    public String ptrig() {
        String res;
        try {
            File file = new File("/Users/yanghyowon/Downloads/serveriris.png");
            FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            MultipartFile psd = new CommonsMultipartFile(fileItem);

            InferenceRequestDto reqdto = InferenceRequestDto.builder()
                    .height(165)
                    .psd(psd)
                    .build();

            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("height", reqdto.getHeight());
            builder.part("curtime", reqdto.getCurtime());
            builder.part("name", "psd");
            builder.part("filename", "psd");
            ByteArrayResource bar = new ByteArrayResource(reqdto.getPsd().getBytes()) {
                @Override
                public String getFilename() {
                    return "psd";
                }
            };
            builder.part("file", bar);

//            builder.part("psd", reqdto.getPsd()); // multipartfile을 바로 넣으면 매핑 에러남


            WebClient webClient = WebClient.create("http://localhost:8080");
            res = webClient.post()
                    .uri("/webcl")
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .exchange()
                    .block()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            System.out.println("In '/ptrig' :: " + e.getStackTrace()[0]);
            res = "Error on '/ptrig' :: " + e.getMessage();
        }
        return res;
    }
    @PostMapping("/webcl") // receive Infreqdto as formdata by POST on Webclient
    public String webcl(MultipartHttpServletRequest req) {
        String res;
        try {
            int height = Integer.parseInt(req.getParameter("height"));
            String curtime = req.getParameter("curtime");
            MultipartFile psd = req.getFile("file");
//            ByteArrayResource bar= (ByteArrayResource) req.getFile("file");
//            if(bar==null) System.out.println("bar is empty**************************");
//            MultipartFile psd = (MultipartFile) bar.getFile();

//            MultipartFile psd = req.getFile("psd").getFile();
//            byte[] psdb=req.getParameter("psd").getBytes();


            FileUtils.writeByteArrayToFile(new File("/Users/yanghyowon/Downloads/webclpng.png"), psd.getBytes());

            res = "curtime: " + curtime;
        } catch (Exception e) {
            System.out.println("In '/webcl' :: " + e.getStackTrace());
//            System.out.println(e.getStackTrace());
            res = "Error on '/webcl' :: " + e.getMessage();
        }
        return res;
    }

    @PostMapping("/reqdto") // receive Infreqdto by POST on <form>
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
