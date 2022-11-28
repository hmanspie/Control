package com.rebalcomb.control.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CryptographyExam3 {

    @GetMapping("/")
    public ModelAndView getMenu(ModelAndView modelAndView){
        modelAndView.setViewName("menu/menu");
        return modelAndView;
    }

    @GetMapping("/test")
    public ModelAndView getTest(ModelAndView modelAndView){
        modelAndView.setViewName("test/test");
        return modelAndView;
    }

    @GetMapping("/task1")
    public ModelAndView getTask1(ModelAndView modelAndView){
        modelAndView.setViewName("task1/task1");
        return modelAndView;
    }

    @PostMapping("/task1")
    public ModelAndView postTask1(ModelAndView modelAndView){
        modelAndView.setViewName("task1/ttask1");
        return modelAndView;
    }

    @GetMapping("/task2")
    public ModelAndView getTask2(ModelAndView modelAndView){
        modelAndView.setViewName("task2/task2");
        return modelAndView;
    }

    @PostMapping("/task2")
    public ModelAndView postTask2(ModelAndView modelAndView){
        modelAndView.setViewName("task2/task2");
        return modelAndView;
    }

    @GetMapping("/task3")
    public ModelAndView getTask3(ModelAndView modelAndView){
        modelAndView.setViewName("task3/task3");
        return modelAndView;
    }

    @PostMapping("/task3")
    public ModelAndView postTask3(ModelAndView modelAndView){
        modelAndView.setViewName("task3/task3");
        return modelAndView;
    }

    @GetMapping("/task4")
    public ModelAndView getTask4(ModelAndView modelAndView){
        modelAndView.setViewName("task4/task4");
        return modelAndView;
    }

    @PostMapping("/task4")
    public ModelAndView postTask4(ModelAndView modelAndView){
        modelAndView.setViewName("task4/task4");
        return modelAndView;
    }

}
