package com.emrysoftware;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("error")
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

    private static final String ERROR_PATH = "/error";
    
    @RequestMapping(path = ERROR_PATH)
    public String error() {
        return "error";
    }

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
}