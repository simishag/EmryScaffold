package com.emrysoftware;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
    	return "hello";
    }

    @RequestMapping("/ex")
    public String ex() {
    	throw new RuntimeException("blah blah");
    }

    @RequestMapping(path = "/login")
    public String login() {
        return "login";
    }
    
    @RequestMapping(path = "/index")
    public String index() {	    
        return "index";
    }
    
    @RequestMapping(path = "/subindex")
    public String subindex() {
        return "sub/subindex";
    }
    
    @RequestMapping(path = "/index-xh11")
    public String index_xh11() {
        return "index-xh11";
    }
    
    @RequestMapping(path = "/")
    public String root() {
        return "index";
    }

}