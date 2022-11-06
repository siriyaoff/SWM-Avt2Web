package com.avatar2.home.web.dto;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class InferenceRequestDtoTest {

    @Test
    public void create_reqdto() {
        try {
            //given
            File file = new File("/Users/yanghyowon/Downloads/serveriris.png");
            FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            MultipartFile psd = new CommonsMultipartFile(fileItem);

            String height = "165";
            MultipartFile m = psd;

            InferenceRequestDto infreq = InferenceRequestDto.builder()
                    .height(Integer.parseInt(height))
                    .psd(m)
                    .build();
            System.out.println(infreq.getCurtime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}