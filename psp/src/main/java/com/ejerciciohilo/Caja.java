package com.ejerciciohilo;

import java.util.Random;

public class Caja extends Thread {
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            System.out.println("Hilo " + getName() + " en la iteración " + i);
            try {
                Thread.sleep(1000+random.nextInt(1, 6));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Hilo " + getName() + " ha terminado.");
    }

    public static void main(String[] args) {
        Caja caja1 = new Caja();
        Caja caja2 = new Caja();
        Caja caja3 = new Caja();

        caja1.start();
        caja2.start();
        caja3.start();

    }
}
