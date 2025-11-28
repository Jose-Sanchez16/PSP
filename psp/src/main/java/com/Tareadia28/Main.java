package com.Tareadia28;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SIMULACIÓN DE ESTUDIANTES Y LIBROS ===");
        System.out.println("Configuración: " + SimulacionConfig.TOTAL_ESTUDIANTES + 
                          " estudiantes, " + SimulacionConfig.TOTAL_LIBROS + " libros");
        System.out.println("Escala temporal: 1 minuto simulado = " + 
                          SimulacionConfig.MS_POR_MINUTO + " ms reales\n");
        
        // Crear libros
        Libro[] libros = new Libro[SimulacionConfig.TOTAL_LIBROS];
        for (int i = 0; i < libros.length; i++) {
            libros[i] = new Libro(i + 1); // IDs del 1 al 9
        }
        
        // Crear estudiantes
        Estudiante[] estudiantes = new Estudiante[SimulacionConfig.TOTAL_ESTUDIANTES];
        long seed = System.currentTimeMillis(); // Semilla reproducible
        
        for (int i = 0; i < estudiantes.length; i++) {
            estudiantes[i] = new Estudiante(i + 1, libros, seed); // IDs del 1 al 4
        }
        
        // Iniciar simulación
        long tiempoInicio = System.currentTimeMillis();
        
        for (Estudiante estudiante : estudiantes) {
            estudiante.setTiempoInicio(tiempoInicio);
            estudiante.start();
        }
        
        // Ejecutar simulación por tiempo limitado (opcional)
        int duracionSimulacionMs = 60000; // 1 minuto real
        
        try {
            System.out.println("\nSimulación ejecutándose por " + 
                             (duracionSimulacionMs / 1000) + " segundos...\n");
            Thread.sleep(duracionSimulacionMs);
            
            System.out.println("\n=== DETENIENDO SIMULACIÓN ===");
            for (Estudiante estudiante : estudiantes) {
                estudiante.detenerEjecucion();
            }
            
            // Esperar a que todos terminen
            for (Estudiante estudiante : estudiantes) {
                estudiante.join(2000);
            }
            
        } catch (InterruptedException e) {
            System.out.println("Simulación interrumpida");
        }
        
        System.out.println("\n=== SIMULACIÓN FINALIZADA ===");
    }
}