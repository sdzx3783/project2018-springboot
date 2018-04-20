package com.makshi.framework.mainframe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(basePackages= {"com.hotent","com.makshi"})
@ServletComponentScan(basePackages={"com.hotent"})
@ImportResource(locations = {"classpath:app-beans.xml"})
public class MainframeApplication{

    public static void main(String[] args) {
        SpringApplication.run(MainframeApplication.class, args);
    }
   
}
