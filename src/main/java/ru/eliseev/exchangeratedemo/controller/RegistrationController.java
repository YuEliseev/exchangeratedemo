package ru.eliseev.exchangeratedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.eliseev.exchangeratedemo.accessingdatajpa.UserRepository;
import ru.eliseev.exchangeratedemo.model.entity.Role;
import ru.eliseev.exchangeratedemo.model.entity.User;

import javax.servlet.ServletException;
import java.util.Collections;
import java.util.Map;

@Controller
@RestController
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> model = modelAndView.getModel();
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView addUser(User user) throws ServletException {
        ModelAndView modelAndView = new ModelAndView();
        User dbUser = userRepository.findByUsername(user.getUsername());
        Map<String, Object> model = modelAndView.getModel();
        if (dbUser != null){
            throw new ServletException();
        }else{
            user.setActive(true);
            user.setRole(Collections.singleton(Role.USER));
            userRepository.save(new User(user.getUsername(), user.getPassword(), user.isActive(), user.getRole()));
        }
        return modelAndView;
    }
}
