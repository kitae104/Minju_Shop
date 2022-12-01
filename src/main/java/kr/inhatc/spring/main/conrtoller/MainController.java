package kr.inhatc.spring.main.conrtoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    
    @GetMapping(value = "/")
    public String main() {
        return "main";
    }

}
