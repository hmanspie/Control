package com.rebalcomb.control.controller;

import com.rebalcomb.control.DataRequest;
import com.rebalcomb.control.dto.Task4;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        modelAndView.addObject("dataRequest", new DataRequest());
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
        modelAndView.addObject(new Task4());
        return modelAndView;
    }

    @PostMapping("/task4")
    public ModelAndView postTask4(ModelAndView modelAndView, @ModelAttribute Task4 task4){
        modelAndView.setViewName("task4/task4");

        double first_zn = ((task4.getX1()-task4.getX2())*(task4.getX1()-task4.getX3()));
        double first = task4.getY1()*((task4.getX2()*task4.getX3())/first_zn);

        double second_zn = ((task4.getX2()-task4.getX1()))*(task4.getX2()-task4.getX3());
        double second = task4.getY2() * ((task4.getX1()*task4.getX3())/second_zn);

        double thirth_zn = ((task4.getX3()-task4.getX1()))*(task4.getX3()-task4.getX2());
        double thirth = task4.getY3() * ((task4.getX1()*task4.getX2())/thirth_zn);

        double res = first + second + thirth;

        String result = "(x1 - x2) * (x1 - x3)";

        modelAndView.addObject("result", result);
        return modelAndView;
    }

}
