package com.Tareadia28;

import java.util.Arrays;
import java.util.Random;

public class Estudiante extends Thread {
    private final int id;
    private final Libro[] libros;
    private final Random random;
    private volatile boolean ejecutando;
    private long tiempoInicio;
    
    public Estudiante(int id, Libro[] libros, long seed) {
        this.id = id;
        this.libros = libros;
        this.random = new Random(seed + id); // Semilla diferente por estudiante
        this.ejecutando = true;
        setName("Estudiante-" + id);
    }
    
    public void setTiempoInicio(long tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }
    
    public void detenerEjecucion() {
        this.ejecutando = false;
        this.interrupt();
    }
    
    private void log(String mensaje) {
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
        System.out.printf("[T+%5dms] %s: %s%n", tiempoTranscurrido, getName(), mensaje);
    }
    
    private Libro[] seleccionarLibros() {
        int idx1, idx2;
        
        // Seleccionar dos índices distintos
        do {
            idx1 = random.nextInt(libros.length);
            idx2 = random.nextInt(libros.length);
        } while (idx1 == idx2);
        
        Libro[] librosSeleccionados = {libros[idx1], libros[idx2]};
        
        // Ordenar por ID para evitar deadlock
        Arrays.sort(librosSeleccionados, (a, b) -> Integer.compare(a.getId(), b.getId()));
        
        log("seleccionó " + librosSeleccionados[0] + " y " + librosSeleccionados[1]);
        return librosSeleccionados;
    }
    
    private void adquirirLibros(Libro[] librosSeleccionados) throws InterruptedException {
        log("intentando adquirir " + librosSeleccionados[0] + " y " + librosSeleccionados[1]);
        
        // Adquirir en orden para evitar deadlock
        librosSeleccionados[0].adquirir();
        log("adquirió " + librosSeleccionados[0]);
        
        try {
            librosSeleccionados[1].adquirir();
            log("adquirió " + librosSeleccionados[1]);
        } catch (Exception e) {
            // Si falla al adquirir el segundo, liberar el primero
            librosSeleccionados[0].liberar();
            throw e;
        }
    }
    
    private void usarLibros(Libro[] librosSeleccionados) throws InterruptedException {
        int tiempoUso = SimulacionConfig.getTiempoAleatorio(random, 60, 180); // 1-3 horas
        log("usando " + librosSeleccionados[0] + " y " + librosSeleccionados[1] + 
            " por " + tiempoUso + " minutos");
        
        Thread.sleep(SimulacionConfig.minutosAMilisegundos(tiempoUso));
        
        log("terminó de usar los libros");
    }
    
    private void liberarLibros(Libro[] librosSeleccionados) {
        // Liberar en orden inverso (buena práctica)
        librosSeleccionados[1].liberar();
        librosSeleccionados[0].liberar();
        log("liberó " + librosSeleccionados[0] + " y " + librosSeleccionados[1]);
    }
    
    private void descansar() throws InterruptedException {
        int tiempoDescanso = SimulacionConfig.getTiempoAleatorio(random, 60, 120); // 1-2 horas
        log("descansando por " + tiempoDescanso + " minutos");
        
        Thread.sleep(SimulacionConfig.minutosAMilisegundos(tiempoDescanso));
        
        log("terminó el descanso");
    }
    
    @Override
    public void run() {
        log("inició sus actividades");
        int ciclo = 0;
        
        while (ejecutando && !Thread.currentThread().isInterrupted()) {
            try {
                ciclo++;
                log("--- Ciclo " + ciclo + " ---");
                
                Libro[] librosSeleccionados = seleccionarLibros();
                adquirirLibros(librosSeleccionados);
                
                try {
                    usarLibros(librosSeleccionados);
                } finally {
                    liberarLibros(librosSeleccionados);
                }
                
                descansar();
                
            } catch (InterruptedException e) {
                if (ejecutando) {
                    log("fue interrumpido durante la operación");
                    break;
                }
            } catch (Exception e) {
                log("error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        log("finalizó sus actividades");
    }
}