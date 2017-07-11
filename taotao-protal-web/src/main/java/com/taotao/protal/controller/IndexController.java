package com.taotao.protal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wb-ny291824 on 2017/7/7.`
 */
@Controller
@Slf4j
public class IndexController {
    @RequestMapping("/index")
    public String showIndex(){
        return "index";
    }
}
