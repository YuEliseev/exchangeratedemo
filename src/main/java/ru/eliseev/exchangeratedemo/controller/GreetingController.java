package ru.eliseev.exchangeratedemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RestController
public class GreetingController {

    @GetMapping("/")
    public ModelAndView greeting(){
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> model = modelAndView.getModel();
        modelAndView.setViewName("greeting");
        return modelAndView;
    }
}
