package com.rebalcomb.control.controller;

import com.rebalcomb.control.DataRequest;
import com.rebalcomb.control.dto.Task4;
import com.rebalcomb.control.RSAUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;

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

    @PostMapping("/task1Calculate")
    public ModelAndView postTask1(ModelAndView modelAndView, DataRequest dataRequest){
        String result = null;
        if(dataRequest.getP() != "" && dataRequest.getQ() != "") {
            BigInteger p = new BigInteger(dataRequest.getP());
            BigInteger q = new BigInteger(dataRequest.getQ());
            BigInteger module = RSAUtil.getCulcModule(p, q);
            result = "n = p * q; \nn = " + p + " * " + q + " = " + module + "\n";
            BigInteger eulerNumber = RSAUtil.getEulerNumber(p, q);
            result += "phi(n) = (p - 1) * (q - 1); \nphi(n) = (" + p + " - 1) * (" + q + " - 1) = " + eulerNumber + "\n";
            BigInteger publicKey = null;

            if (dataRequest.getE() != "")
                publicKey = new BigInteger(dataRequest.getE());
            BigInteger privateKey = null;
            if (dataRequest.getD() != "")
                privateKey = new BigInteger(dataRequest.getD());
            if (dataRequest.getE() == "" && dataRequest.getD() != "") {
                publicKey = privateKey.modInverse(eulerNumber);
                result += "e = d^-1mod(phi(n)); \ne = " + privateKey + " ^-1mod(" + eulerNumber + ") = " + publicKey + "\n";
            }
            if (dataRequest.getE() != "" && dataRequest.getD() == "") {
                privateKey = publicKey.modInverse(eulerNumber);
                result += "d = e^-1mod(phi(n)); \nd = " + publicKey + " ^-1mod(" + eulerNumber + ") = " + privateKey + "\n";
            }
            BigInteger M1 = null;

            if (dataRequest.getM1() != "")
                M1 = new BigInteger(dataRequest.getM1());
            BigInteger C = null;

            if (dataRequest.getC() != "")
                C = new BigInteger(dataRequest.getC());
            if (dataRequest.getM1() != "" && dataRequest.getC() == "") {
                C = M1.modPow(publicKey, module);
                result += "C = M^e mod(n); \nC = " + M1 + " ^ " + publicKey + " mod(" + module + ") = " + C + "\n";
            } else if (dataRequest.getM1() == "" && dataRequest.getC() != "") {
                M1 = C.modPow(privateKey, module);
                result += "M = C^d mod(n); \nM = " + C + " ^ " + privateKey + " mod(" + module + ") = " + M1 + "\n";
            } else if (dataRequest.getM1() != "" && dataRequest.getC() != "") {
                C = M1.modPow(publicKey, module);
                result += "C = M^e mod(n); \nC = " + M1 + " ^ " + publicKey + " mod(" + module + ") = " + C + "\n";
                M1 = C.modPow(privateKey, module);
                result += "M = C^d mod(n); \nM = " + C + " ^ " + privateKey + " mod(" + module + ") = " + M1 + "\n";
            }

            BigInteger M2 = null;
            ;
            if (dataRequest.getM2() != "")
                M2 = new BigInteger(dataRequest.getM2());
            BigInteger S = null;
            ;
            if (dataRequest.getS() != "")
                S = new BigInteger(dataRequest.getS());

            if (dataRequest.getM2() != "" && dataRequest.getS() == "") {
                S = M2.modPow(privateKey, module);
                result += "S = M^d mod(n); \nS = " + M2 + " ^ " + privateKey + " mod(" + module + ") = " + S + "\n";
            } else if (dataRequest.getM2() == "" && dataRequest.getS() != "") {
                M2 = S.modPow(publicKey, module);
                result += "M = S^e mod(n); \nM = " + S + " ^ " + publicKey + " mod(" + module + ") = " + M2 + "\n";
            } else if (dataRequest.getM2() != "" && dataRequest.getS() != "") {
                S = M2.modPow(privateKey, module);
                result += "S = M^d mod(n); \nS = " + M2 + " ^ " + privateKey + " mod(" + module + ") = " + S + "\n";
                M2 = S.modPow(publicKey, module);
                result += "M = S^e mod(n); \nM = " + S + " ^ " + publicKey + " mod(" + module + ") = " + M2 + "\n";
            }
        }

        modelAndView.addObject("result", result);
        modelAndView.setViewName("task1/task1");
        modelAndView.addObject("dataRequest", new DataRequest());
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
