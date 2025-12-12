package com.Tareadia12;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SIMULACIÓN DE TIENDA DE VIDEOJUEGOS ===\n");
        
        // 1. Crear inventario con 30 códigos iniciales
        Inventario inventario = new Inventario(30);
        System.out.println("Inventario creado con 30 códigos iniciales.\n");
        
        // 2. Crear y lanzar 5 empleados (hilos)
        Empleado[] empleados = new Empleado[5];
        for (int i = 0; i < empleados.length; i++) {
            empleados[i] = new Empleado("Empleado-" + (i + 1), inventario);
            empleados[i].start();
            System.out.println(empleados[i].getName() + " ha comenzado a trabajar.");
        }
        
        // 3. Crear y lanzar el hilo reponedor
        Reponedor reponedor = new Reponedor(inventario);
        reponedor.start();
        System.out.println("\nReponedor activado y monitoreando el stock.\n");
        
        // 4. Esperar a que los empleados terminen (versión modificada)
        try {
            for (Empleado empleado : empleados) {
                empleado.join();
            }
            
            System.out.println("\n=== TODOS LOS EMPLEADOS HAN TERMINADO ===");
            
            // Pregunta 5: ¿Qué le pasa al reponedor?
            System.out.println("\n[Pregunta 5] Los empleados han terminado, pero el Reponedor sigue ejecutándose.");
            System.out.println("El Reponedor NO se detiene automáticamente porque está en un bucle while(true).");
            System.out.println("Solución: Interrumpir el hilo Reponedor manualmente.");
            
            // Interrumpir al reponedor
            reponedor.interrupt();
            reponedor.join();
            
            System.out.println("\n=== SIMULACIÓN COMPLETADA ===");
            
        } catch (InterruptedException e) {
            System.out.println("Simulación interrumpida.");
            Thread.currentThread().interrupt();
        }
    }
}
