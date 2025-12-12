package com.Tareadia12;

import java.util.Random;

public class Empleado extends Thread {
    private final Inventario inventario;
    private final Random random = new Random();
    private final int MAX_VENTAS = 10; // Modificación para punto 5 del cuestionario
    private int ventasRealizadas = 0;

    public Empleado(String nombre, Inventario inventario) {
        super(nombre);
        this.inventario = inventario;
    }

    @Override
    public void run() {
        try {
            // Versión modificada: solo 10 ventas por empleado
            while (ventasRealizadas < MAX_VENTAS) {
                // Simular tiempo de atención (100-400 ms)
                int tiempoAtencion = 100 + random.nextInt(301);
                Thread.sleep(tiempoAtencion);

                // Intentar vender
                inventario.vender(getName());
                ventasRealizadas++;

                System.out.println("[" + getName() + "] Ventas realizadas: " + ventasRealizadas + "/" + MAX_VENTAS);
            }
            
            System.out.println("[" + getName() + "] Ha completado sus " + MAX_VENTAS + " ventas. Terminando...");
            
        } catch (InterruptedException e) {
            System.out.println("[" + getName() + "] Empleado interrumpido.");
            Thread.currentThread().interrupt();
        }
    }

    // Método original (para funcionamiento indefinido)
    public void runInfinito() {
        while (true) {
            try {
                // Simular tiempo de atención (100-400 ms)
                int tiempoAtencion = 100 + random.nextInt(301);
                Thread.sleep(tiempoAtencion);

                // Intentar vender
                inventario.vender(getName());

            } catch (InterruptedException e) {
                System.out.println("[" + getName() + "] Empleado interrumpido.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}