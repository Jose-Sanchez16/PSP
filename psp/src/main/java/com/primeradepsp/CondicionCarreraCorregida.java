package com.primeradepsp;

public class CondicionCarreraCorregida implements Runnable {
    private int contador = 0;
    private final Object lock = new Object(); 
    
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized(lock) {
                contador++;
            }
        }
        System.out.println(Thread.currentThread().getName() + " terminó.");
    }
    
    public int getContador() {
        synchronized(lock) {
            return contador;
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        CondicionCarreraCorregida objetoCompartido = new CondicionCarreraCorregida();
        
        Thread hilo1 = new Thread(objetoCompartido, "Hilo-1");
        Thread hilo2 = new Thread(objetoCompartido, "Hilo-2");
        
        long inicio = System.currentTimeMillis();
        
        hilo1.start();
        hilo2.start();
        
        hilo1.join();
        hilo2.join();
        
        long fin = System.currentTimeMillis();
        
        System.out.println("Contador final: " + objetoCompartido.getContador());
        System.out.println("Tiempo de ejecución: " + (fin - inicio) + " ms");
        System.out.println("¿El resultado es 2000? " + (objetoCompartido.getContador() == 2000));
    }
}