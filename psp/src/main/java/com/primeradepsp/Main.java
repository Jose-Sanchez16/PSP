package com.primeradepsp;

public class Main {
    public static void main(String[] args) {
        Runtime r=Runtime.getRuntime();
        String comando ="nautilus";
        Process p;
        try {
            p=r.exec(comando);
        }catch(Exception e){
            System.out.println("Error en el comando " +comando);
        }
    }
}