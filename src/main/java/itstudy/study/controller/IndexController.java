package itstudy.study.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j //logger(lombok)
public class IndexController {

    @RequestMapping("/")
    public String home(){
        log.info("index controller");
        return "index"; //home.html 찾아간다.
    }
}
