package com.lxyproject_exercise.reggie_lxy.controller;

import com.lxyproject_exercise.reggie_lxy.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.SimpleTypeRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 文件的上传和下载
 */

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        log.info(file.toString());

        //获得原始文件名
        String  originalFilename = file.getOriginalFilename();
        //获取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用UUID重新生成文件名，防止文件名重复造成文件覆盖
        String fileName = UUID.randomUUID().toString()+suffix;

        //创建一个目录对象
        File dir = new File(basePath);
        //如果目录不存在，则创建目录
        if (!dir.exists()){
            dir.mkdirs();
        }

        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(basePath+fileName));
        }catch (IOException e){
            e.printStackTrace();
        }
        return  R.success(fileName);
    }

    /**
     * 文件下载
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
       try{
           FileInputStream fileInputStream  = new FileInputStream(new File(basePath + name));
           ServletOutputStream outputStream = response.getOutputStream();

           response.setContentType("image/jpeg");

           int len = 0;
           byte[] bytes = new byte[1024];
           while ((len = fileInputStream.read(bytes))!=-1){
               outputStream.write(bytes,0,len);
               outputStream.flush();
           }
           //关闭资源
            outputStream.close();
            fileInputStream.close();

       }catch (Exception e){
           e.printStackTrace();
       }



    }

}
