package com.avatar2.home.web;

import com.avatar2.home.domain.inference.PElem;
import com.avatar2.home.web.dto.InferenceRequestDto;
import com.avatar2.home.web.dto.InferenceResponseDto;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class InferenceController {
    /**
     * for service
     * @param req formdata
     * @return resdto
     */
    @PostMapping("/create")
    public InferenceResponseDto create(MultipartHttpServletRequest req) {
        InferenceResponseDto resdto=null;
        try {
            String height = req.getParameter("height");
            MultipartFile psd = req.getFile("psd");
            InferenceRequestDto reqdto = InferenceRequestDto.builder()
                    .height(Integer.parseInt(height))
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

            WebClient webClient = WebClient.create("http://ec2-13-124-191-61.ap-northeast-2.compute.amazonaws.com:8080");
            resdto = webClient.post()
                    .uri("/inference")
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(InferenceResponseDto.class)
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace()[0]);
        }
        return resdto;
    }

    /**
     * request '/inference' in avt2-inference
     * @return size of iris.png
     */
    @GetMapping("/inftest")
    public String inftest() {
        String res="";
        try {
            // create InferenceRequestDto
            File file = new File("/home/ec2-user/tpsd.psd");
            FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            MultipartFile psd = new CommonsMultipartFile(fileItem);
            InferenceRequestDto reqdto = InferenceRequestDto.builder()
                    .height(165)
                    .psd(psd)
                    .build();

            res += "!";

            // create MultipartBodyBuilder
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

            res += "@";

            // create webclient, send request
            ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                    .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(300 * 1024 * 1024))
                    .build();
            WebClient webClient = WebClient.builder()
                    .exchangeStrategies(exchangeStrategies)
                    .baseUrl("http://ec2-13-124-191-61.ap-northeast-2.compute.amazonaws.com:8080")
                    .build();
            InferenceResponseDto resdto = webClient.post()
                    .uri("/inference")
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(InferenceResponseDto.class)
                    .block();
            res += "#";
            res += "" + resdto.getErr();
        } catch (Exception e) {
            res += "\nError on '/ptrig' :: " + e.getMessage();
        }
        return res;
    }

    /**
     * request /webcl in local
     * @return resdto.getErr()
     */
    @GetMapping("/ptrig")
    public String ptrig() {
        String res;
        try {
            // create InferenceRequestDto
//            File file = new File("/Users/yanghyowon/Downloads/serveriris.png"); // mac
            File file = new File("C:\\Users\\yang\\Downloads\\tpsd.psd"); // windows
            FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            MultipartFile psd = new CommonsMultipartFile(fileItem);
            InferenceRequestDto reqdto = InferenceRequestDto.builder()
                    .height(165)
                    .psd(psd)
                    .build();

            // create MultipartBodyBuilder
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
            System.out.println("MultipartBodyBuilder created");

            // create webclient, send request
            ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                    .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(300 * 1024 * 1024))
                    .build();
            WebClient webClient = WebClient.builder()
                    .exchangeStrategies(exchangeStrategies)
                    .baseUrl("http://localhost:8080")
                    .build();
            InferenceResponseDto resdto = webClient.post()
                    .uri("/webcl")
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(InferenceResponseDto.class)
                    .block();
            System.out.println("'/webcl' finished");
            res = ""+resdto.getCoord().size()+" counts";
        } catch (Exception e) {
            System.out.println("In '/ptrig' :: " + e.getStackTrace()[0]);
            res = "Error on '/ptrig' :: " + e.getMessage();
        }
        return res;
    }

    /**
     * receive mhsr, return resdto
     * @param req multipartbodybuilder
     * @return resdto
     */
    @PostMapping("/webcl") // receive Infreqdto as formdata by POST on Webclient
    public InferenceResponseDto webcl(MultipartHttpServletRequest req) {
        System.out.println("'/webcl' called");
        InferenceResponseDto inferenceResponseDto;
        try {
            int height = Integer.parseInt(req.getParameter("height"));
            String curtime = req.getParameter("curtime");
            MultipartFile psd = req.getFile("file");

            FileUtils.writeByteArrayToFile(new File("C:\\Users\\yang\\Downloads\\webclpng.png"),
                    psd.getBytes());

            // check reqdto successfully received
            String res = "curtime: " + curtime;
            System.out.println(res+"\nreqdto parsed");

            // create and return resdto
            ArrayList<PElem> coord, eyelash;
            JSONParser parser = new JSONParser();
            JSONObject jsonObject, jsonObject1;
            JSONArray pointArray;

            Reader reader = new FileReader("C:\\Users\\yang\\Downloads\\testPoint.json");
            jsonObject = (JSONObject) parser.parse(reader);
            pointArray = (JSONArray) parser.parse(jsonObject.get("Point").toString());
            coord = new ArrayList<>();
            for (Object obj : pointArray) {
                jsonObject1 = (JSONObject) obj;
                coord.add(PElem.builder()
                        .id((String) jsonObject1.get("id"))
                        .x((String) jsonObject1.get("x"))
                        .y((String) jsonObject1.get("y"))
                        .build());
            }

            reader = new FileReader("C:\\Users\\yang\\Downloads\\testPoint.json");
            jsonObject = (JSONObject) parser.parse(reader);
            pointArray = (JSONArray) parser.parse(jsonObject.get("Point").toString());
            eyelash = new ArrayList<>();
            for (Object obj : pointArray) {
                jsonObject1 = (JSONObject) obj;
                eyelash.add(PElem.builder()
                        .id((String) jsonObject1.get("id"))
                        .x((String) jsonObject1.get("x"))
                        .y((String) jsonObject1.get("y"))
                        .build());
            }

            File file = new File("C:\\Users\\yang\\Downloads\\serveriris.png");
            FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            MultipartFile iris = new CommonsMultipartFile(fileItem);
            inferenceResponseDto = new InferenceResponseDto(coord, eyelash, iris.getBytes(), "");
            System.out.println(jsonObject);
            System.out.println("\nresdto created");
        } catch (Exception e) {
            String err = e.getStackTrace()[0].toString();
            System.out.println("In '/webcl' :: " + err);
            inferenceResponseDto = new InferenceResponseDto(null, null, null, err);
        }
        return inferenceResponseDto;
    }

    /**
     * simple test with string for CORS test to EC2 instance 'avt2-inference'
     * @return string received
     */
    @GetMapping("/corstest") // CORS resolved
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
}
