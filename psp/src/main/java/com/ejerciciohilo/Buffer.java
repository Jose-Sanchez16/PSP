package com.ejerciciohilo;

public class Buffer extends Thread {
        private int dato;
        private boolean disponible = false; 

        public synchronized void producir(int valor) {
            while (disponible) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            dato = valor;
            disponible = true;
            System.out.println("Productor produjo: " + valor);
            notify();
        }
        public synchronized int consumir() {
            while (!disponible) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Consumidor consumió: " + dato);
            disponible = false;
            notify();

            return dato;
        }

    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Thread productor = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                buffer.producir(i);
                try {
                    Thread.sleep(300); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        Thread consumidor = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                buffer.consumir();
                try {
                    Thread.sleep(500); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        productor.start();
        consumidor.start();
        
        try {
            productor.join();
            consumidor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Producción y consumo finalizados.");
    }
}
