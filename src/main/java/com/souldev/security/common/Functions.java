package com.souldev.security.common;

public class Functions {

    public static String ucFirst(String str) {
        if (str == null || str.isEmpty()) return str;
        else return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String ucNombres(String str){
        String[] parts = str.split(" ");
        String result = "";
        int contador = 0;
        for(String string: parts){
            if(contador == parts.length-1){
                result += ucFirst(string);
            }else{
                result += ucFirst(string) + " ";
            }
            contador++;
        }
        return result;
    }
}
