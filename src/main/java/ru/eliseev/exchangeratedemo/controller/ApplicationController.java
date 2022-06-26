package ru.eliseev.exchangeratedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ru.eliseev.exchangeratedemo.accessingdatajpa.RatesRepository;
import ru.eliseev.exchangeratedemo.accessingdatajpa.entity.Request;
import ru.eliseev.exchangeratedemo.model.GifDTO;
import ru.eliseev.exchangeratedemo.service.ApplicationService;

import java.util.Map;

@Controller
@RestController
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private RatesRepository ratesRepository;

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

    @GetMapping("/history")
    public @ResponseBody Iterable<Request> history(){
        return ratesRepository.findAll();
    }
}
