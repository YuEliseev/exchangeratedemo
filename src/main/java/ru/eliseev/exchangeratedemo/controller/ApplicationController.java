package ru.eliseev.exchangeratedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ru.eliseev.exchangeratedemo.model.GifDTO;
import ru.eliseev.exchangeratedemo.service.ApplicationService;

import java.util.Map;

@Controller
@RestController
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/demo")
    public ModelAndView demo(){
        ModelAndView modelAndView = new ModelAndView();
        GifDTO gifByCurrency = applicationService.getGifByCurrency();
        Map<String, Object> model = modelAndView.getModel();
        model.put("gifUrl", gifByCurrency.getUrl());
        model.put("gifWidth", gifByCurrency.getWidth());
        model.put("gifHeight", gifByCurrency.getHeight());
        model.put("tag", gifByCurrency.getTag());
        modelAndView.setViewName("demo");
        return modelAndView;
    }

}
