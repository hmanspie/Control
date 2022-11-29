package com.rebalcomb.control.controller;

import com.rebalcomb.control.dto.DataRequest;
import com.rebalcomb.control.dto.ElgamalRequest;
import com.rebalcomb.control.dto.Task3Dto;
import com.rebalcomb.control.dto.Task4;
import com.rebalcomb.control.RSAUtil;
import com.rebalcomb.control.service.Task3;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;
import java.util.Objects;

import static org.slf4j.LoggerFactory.*;

@Controller
public class CryptographyExam3 {

    private Logger logger = getLogger(CryptographyExam3.class);

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
            result = "\nОбчислення n\n";
            result += "n = p * q; \nn = " + p + " * " + q + " = " + module + "\n";
            BigInteger eulerNumber = RSAUtil.getEulerNumber(p, q);
            result += "\nОбчислення phi(n)\n";
            result += "phi(n) = (p - 1) * (q - 1); \nphi(n) = (" + p + " - 1) * (" + q + " - 1) = " + eulerNumber + "\n";
            BigInteger publicKey = null;
            if (dataRequest.getE() != "")
                publicKey = new BigInteger(dataRequest.getE());
            BigInteger privateKey = null;
            if (dataRequest.getD() != "")
                privateKey = new BigInteger(dataRequest.getD());
            if (dataRequest.getE() == "" && dataRequest.getD() != "") {
                publicKey = privateKey.modInverse(eulerNumber);
                result += "\nОбчислення публічного ключа\n";
                result += "e = d^-1mod(phi(n)); \ne = " + privateKey + " ^-1mod(" + eulerNumber + ") = " + publicKey + "\n";
            }
            if (dataRequest.getE() != "" && dataRequest.getD() == "") {
                privateKey = publicKey.modInverse(eulerNumber);
                result += "\nОбчислення приватного ключа\n";
                result += "d = e^-1mod(phi(n)); \nd = " + publicKey + " ^-1mod(" + eulerNumber + ") = " + privateKey + "\n";
            }
            BigInteger M1 = null;
            if (dataRequest.getM1() != "")
                M1 = new BigInteger(dataRequest.getM1());
            BigInteger C = null;
            if (dataRequest.getC() != "")
                C = new BigInteger(dataRequest.getC());

            if (dataRequest.getM1() == "" && dataRequest.getC() != "") {
                M1 = C.modPow(privateKey, module);
                result += "\nРозшифрування повідомлення\n";
                result += "M = C^d mod(n); \nM = " + C + " ^ " + privateKey + " mod(" + module + ") = " + M1 + "\n";
            } else if (dataRequest.getM1() != "" && dataRequest.getC() == "") {
                C = M1.modPow(publicKey, module);
                result += "\nШифрування повідомлення\n";
                result += "C = M^e mod(n); \nC = " + M1 + " ^ " + publicKey + " mod(" + module + ") = " + C + "\n";
                result += "\nРозшифрування повідомлення\n";
                M1 = C.modPow(privateKey, module);
                result += "M = C^d mod(n); \nM = " + C + " ^ " + privateKey + " mod(" + module + ") = " + M1 + "\n";
            }
            BigInteger M2 = null;
            if (dataRequest.getM2() != "")
                M2 = new BigInteger(dataRequest.getM2());
            BigInteger S = null;
            if (dataRequest.getS() != "")
                S = new BigInteger(dataRequest.getS());
            if (dataRequest.getM2() == "" && dataRequest.getS() != "") {
                M2 = S.modPow(publicKey, module);
                result += "\nПеревірка підпису повідомлення\n";
                result += "M = S^e mod(n); \nM = " + S + " ^ " + publicKey + " mod(" + module + ") = " + M2 + "\n";
            } else if (dataRequest.getM2() != "" && dataRequest.getS() == "") {
                result += "\nПідпис повідомлення\n";
                S = M2.modPow(privateKey, module);
                result += "S = M^d mod(n); \nS = " + M2 + " ^ " + privateKey + " mod(" + module + ") = " + S + "\n";
                result += "\nПеревірка підпису повідомлення\n";
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
        modelAndView.addObject("request", new ElgamalRequest());
        return modelAndView;
    }

    @PostMapping("/task2Calculate")
    public ModelAndView postTask2(ModelAndView modelAndView, ElgamalRequest elgamalRequest){
        BigInteger p = new BigInteger(elgamalRequest.getP());
        BigInteger g = new BigInteger(elgamalRequest.getG());
        BigInteger x = new BigInteger(elgamalRequest.getX());
        String result = null;
        BigInteger y = g.modPow(x, p);
        result = "y = g ^ x mod(p);\ny = " + g + " ^ " + x + " mod(" + p + ") = " + y + "\n";
        result += "Public Key(p, g, y) = (" + p +", "+ g + ", " + y +");   Private Key(x) = (" + x + ");\n";
        BigInteger k1 = null;
        BigInteger M1 = null;
        if(elgamalRequest.getK1() != "" && elgamalRequest.getM1() != "") {
            k1 = new BigInteger(elgamalRequest.getK1());
            M1 = new BigInteger(elgamalRequest.getM1());
            BigInteger a = g.modPow(k1, p);
            BigInteger b = y.modPow(k1, p).multiply(M1).mod(p);
            result += "\nШифрування повідомлення\n";
            result += "a = g ^ k mod(p);\na = " + g + " ^ " + k1 + " mod(" + p + ") = " + a + "\n";
            result += "b = y ^ k * M mod(p);\nb = " + y + " ^ " + k1 + " * " + M1 + " mod(" + p + ") = " + b + "\n";
            result += "C = (a, b); C = (" + a + ", " + b + ");\n";
            BigInteger M = b.multiply(a.pow(x.intValue()).modInverse(p)).mod(p);
            result += "\nРозшифрування повідомлення\n";
            result += "M = b(a^x)^-1mod(p);\nM = " + b + "(" + a + "^" + x + ")^-1 mod(" + p + ") = " + M + "\n";
        }
        BigInteger k2 = null;
        BigInteger M2 = null;
        if(elgamalRequest.getK2() != "" && elgamalRequest.getM2() != "") {
            k2 = new BigInteger(elgamalRequest.getK2());
            M2 = new BigInteger(elgamalRequest.getM2());
            BigInteger r = g.modPow(k2, p);
            result += "\nПідпис повідомлення\n";
            result += "r = g ^ k mod(p);\nr = " + g + " ^ " + k2 + " mod(" + p + ") = " + r + "\n";
            BigInteger s = M2.subtract(x.multiply(r)).multiply(k2.modInverse(p.subtract(BigInteger.ONE))).mod(p.subtract(BigInteger.ONE));
            result += "s = (m - x * r) * k^-1 mod(p - 1);\ns = (" + M2 + " - " + x + " * " + r + ") * " + k2 + "^-1 mod(" + p.subtract(BigInteger.ONE) + ") = " + s + "\n";
            result += "S = (r, s); S = (" + r + ", " + s + ");\n";
            BigInteger z1 = (y.modPow(r, p)).multiply(r.modPow(s, p)).mod(p);
            BigInteger z2 = g.modPow(M2, p);
            result += "\nПеревірка підпису повідомлення\n";
            result += "y^r*r^s == g^m mod(p)\n";
            result += y + " ^ " + r + " * " + r + " ^ s == " + g + " ^ " + M2 + " mod(" + p + ")\n";
            result += z1 + "mod(" + p + ") == " + z2 + "mod(" + p + ");\n";
        }






        modelAndView.addObject("resultt", result);
        modelAndView.setViewName("task2/task2");
        modelAndView.addObject("request", new ElgamalRequest());
        return modelAndView;
    }

    @GetMapping("/task3")
    public ModelAndView getTask3(ModelAndView modelAndView){
        modelAndView.setViewName("task3/task3");
        modelAndView.addObject("task3Dto", new Task3Dto());
        return modelAndView;
    }

    @PostMapping("/task3")
    public ModelAndView postTask3(ModelAndView modelAndView, String firstSelect, String secondSelect, String thirdSelect, @ModelAttribute Task3Dto task3Dto){
        modelAndView.setViewName("task3/task3");

        char[] Ho = task3Dto.getH0().toCharArray();
        int blockLen = Integer.parseInt(task3Dto.getBlockLen());
        char[] message = task3Dto.getMessage().toCharArray();
        char[] equation = new char[6];

        if (Objects.equals(firstSelect, "<<")){
            equation[0] = '0';
        } else {
            equation[0] = '1';
        }

        equation[1] = task3Dto.getFirstNumber().toCharArray()[0];

        if (Objects.equals(secondSelect, "<<")){
            equation[2] = '0';
        } else {
            equation[2] = '1';
        }

        equation[3] = task3Dto.getFirstNumber().toCharArray()[0];

        if (Objects.equals(thirdSelect, "<<")){
            equation[4] = '0';
        } else {
            equation[4] = '1';
        }

        equation[5] = task3Dto.getFirstNumber().toCharArray()[0];

        Task3 task3engine = new Task3(Ho, blockLen, message, equation);
        modelAndView.addObject("result", Task3.getFinalResult());
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

        StringBuilder sb = new StringBuilder();
        sb.append("P(x) = Замініть всі дані (х1,у1; x2,y2; x3,y3 і тд на фото, крім х на  оці дані):\n");

        sb.append("(x1,y1) = (" + task4.getX1() + ", " + task4.getY1() + ")\n" );
        sb.append("(x2,y2) = (" + task4.getX2() + ", " + task4.getY2() + ")\n" );
        sb.append("(x3,y3) = (" + task4.getX3() + ", " + task4.getY3() + ")\n" );

        sb.append("P(x)-:" + res);

        modelAndView.addObject("result", sb.toString());
        return modelAndView;
    }

}
