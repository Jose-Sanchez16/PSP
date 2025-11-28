package com.ejerciciohilo;

public class Contador implements Runnable {
    private int contador;

    public Contador() {
        this.contador = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            contador++;
            System.out.println("Hilo " + Thread.currentThread().getName() + " Contador: " + contador);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Hilo " + Thread.currentThread().getName() + " ha terminado con contaodr en: " + contador);
    }

    public static void main(String[] args) {
        Contador contadorR = new Contador();

        Thread hilo1 = new Thread(contadorR, "Hilo-1");
        Thread hilo2 = new Thread(contadorR, "Hilo-2");
        Thread hilo3 = new Thread(contadorR, "Hilo-3");

        hilo1.start();
        hilo2.start();
        hilo3.start();
    }
}
