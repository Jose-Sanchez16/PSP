package com.primeradepsp;

public class CondicionCarrera implements Runnable {

    private int contador = 0;
    
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            contador++; // Operación NO sincronizada - condición de carrera
        }
        System.out.println(Thread.currentThread().getName() + " terminó. Contador parcial: " + contador);
    }
    
    public static void main(String[] args) throws InterruptedException {
        CondicionCarrera objetoCompartido = new CondicionCarrera();
        
        Thread hilo1 = new Thread(objetoCompartido, "Hilo-1");
        Thread hilo2 = new Thread(objetoCompartido, "Hilo-2");
        
        hilo1.start();
        hilo2.start();
        
        hilo1.join();
        hilo2.join();

        System.out.println("\n¿El resultado siempre es 2000?");
        System.out.println("No, debido a la condición de carrera.");
        System.out.println("La operación contador++ no es atómica:");
        System.out.println("1. Lee el valor actual");
        System.out.println("2. Lo incrementa");
        System.out.println("3. Escribe el nuevo valor");
        System.out.println("Si dos hilos leen el mismo valor simultáneamente, se pierden incrementos.");
    }
}

