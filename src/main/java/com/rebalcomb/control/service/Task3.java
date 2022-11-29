package com.rebalcomb.control.service;

import java.io.File;
import java.util.Scanner;

public class Task3 {
    static char [] Ho;
    static int blockLen;
    static char [] message;
    static char [] equation;
    static char [] messageLen;
    static char [] pad;
    static String textResult = "";

    public Task3 (char [] Ho, int blockLen, char [] message, char [] equation) {
        this.Ho = Ho;
        this.blockLen = blockLen;
        this.message = message;
        this.equation = equation;
        getLenAndPad();
        getResult();
    }

    public static void getLenAndPad(){
        messageLen = new char[blockLen];
        char [] tempLen = decToBinary(message.length);
        int zeroCounter = blockLen - tempLen.length;
        int counter = 0;
        while (zeroCounter != 0){
            messageLen[counter] = '0';
            zeroCounter--;
            counter++;
        }
        for (int i = 0; i< tempLen.length; i++){
            messageLen[counter] = tempLen[i];
            counter++;
        }

        pad = new char [blockLen];
        pad[0] = '1';
        for (int i = 1; i< pad.length; i++)
            pad[i] = '0';


    }

    public static String getFinalResult(){
        return textResult;
    }
    public static void getResult(){
        int numberOfBlocks = message.length/blockLen;
        String [] H = new String [numberOfBlocks+1];
        H[0] = charToString(Ho);
        String [] X = new String [numberOfBlocks];
        for (int i = 0; i< X.length; i++){
            String temp = "";
            for (int j = i*blockLen; j<(i+1)*blockLen; j++){
                temp += String.valueOf(message[j]);
            }
            X[i] = temp;
        }

        for (int i=0; i<X.length; i++){
            textResult += "x" + (i+1) + ":" + X[i] + " ";
        }
        String temp = charToString(pad);
        textResult += "pad:" + temp + " ";
        temp = charToString(messageLen);
        textResult += "len:" +temp + "\n\n";

        for (int i = 1; i<=numberOfBlocks; i++){
            String previousH = H[i-1];
            String localH = previousH;
            String localX = X[i-1];
            String localHplusM = "";
            String result = "";

            // Search H
            if (equation[1] != '0'){
                if (equation[0] == '0'){ // Left to Right
                    for (int j = 0; j< Integer.parseInt(String.valueOf(equation[1])); j++){
                        localH = turnLeftToRigth(localH);
                    }
                }
                else if (equation[0] == '1'){ // Right to Left
                    for (int j = 0; j< Integer.parseInt(String.valueOf(equation[1])); j++){
                        localH = turnRightToLeft(localH);
                    }
                }
            }

            textResult += "H:"+ localH + " ";
//            System.out.println("H: "+ localH);

            // Search M
            if (equation[3] != '0'){
                if (equation[2] == '0'){ // Left to Right
                    for (int j = 0; j< Integer.parseInt(String.valueOf(equation[3])); j++){
                        localX = turnLeftToRigth(localX);
                    }
                }
                else if (equation[2] == '1'){ // Right to Left
                    for (int j = 0; j< Integer.parseInt(String.valueOf(equation[3])); j++){
                        localX = turnRightToLeft(localX);
                    }
                }
            }

            textResult += "M:"+ localX + " ";
//            System.out.println("M: "+ localX);

            // H plus M
            localHplusM = XOR(localH, localX);

            textResult += "H XOR M:"+ localHplusM + " ";
//            System.out.println("H XOR M: "+ localHplusM);

            // Check for зсув всього результату
            if (equation[5] != '0'){
                if (equation[4] == '0'){ // Left to Right
                    for (int j = 0; j< Integer.parseInt(String.valueOf(equation[5])); j++){
                        result = turnLeftToRigth(localHplusM);
                    }
                }
                else if (equation[4] == '1'){ // Right to Left
                    for (int j = 0; j< Integer.parseInt(String.valueOf(equation[5])); j++){
                        result = turnRightToLeft(localHplusM);
                    }
                }
            }
            else
                result = localHplusM;

            textResult += "H" + i + ":"+ result + "\n";
//            System.out.println("H" + i + ": "+ result);
            H[i] = result;
        }
    }

    public static String XOR(String H, String M){
        String result = "";
        char [] tempH = H.toCharArray();
        char [] tempM = M.toCharArray();

        for (int i = 0; i< tempH.length; i++){
            if (tempM[i] == '0' && tempH[i] == '0')
                result+="0";
            else if (tempM[i] == '1' && tempH[i] == '0')
                result+="1";
            else if (tempM[i] == '0' && tempH[i] == '1')
                result+="1";
            else if (tempM[i] == '1' && tempH[i] == '1')
                result+="0";
        }
        return result;
    }

    public static String charToString(char [] array){
        String result = "";
        for(char c : array)
            result+= String.valueOf(c);
        return result;
    }

    public static String turnLeftToRigth (String str){
        char [] temp = str.toCharArray();
        String result = "";
        for (int i = 1; i< temp.length; i++){
            result += String.valueOf(temp[i]);
        }
        result += String.valueOf(temp[0]);
        return result;
    }

    public static String turnRightToLeft (String str){
        char [] temp = str.toCharArray();
        String result = "";
        result += String.valueOf(temp[temp.length-1]);
        for (int i = 0; i< temp.length-1; i++){
            result += String.valueOf(temp[i]);
        }
        return result;
    }

    public static char [] decToBinary(int decimal){
        int binary[] = new int[40];
        int index = 0;
        char[] result;
        while(decimal > 0){
            binary[index++] = decimal%2;
            decimal = decimal/2;
        }
        result = new char [index];
        int counter = 0;

        for(int i = index-1;i >= 0;i--){
            result[counter] = (char) binary[i];
            counter++;
        }
        String temp = "";
        for (int i=0; i <result.length; i++)
            temp += String.valueOf(Integer.valueOf(result[i]));

        return temp.toCharArray();
    }

}

