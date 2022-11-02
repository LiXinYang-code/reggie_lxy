package com.lxyproject_exercise.reggie_lxy;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class UploadFileTest {
    @Test
    public void test1(){

        String fileName = "lxy.jpg";
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
    }

}
