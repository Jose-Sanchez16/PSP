package com.Tareadia21;

import java.util.Random;

// Clase Libro
class Libro {
    private int id;
    private String nombre;
    
    public Libro(int id) {
        this.id = id;
        this.nombre = "Libro-" + id;
    }
    
    public int getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}

// Clase Estudiante
class Estudiante extends Thread {
    private Libro[] biblioteca;
    private Random random;
    
    public Estudiante(int id, Libro[] biblioteca) {
        super("Estudiante-" + id);
        this.biblioteca = biblioteca;
        this.random = new Random();
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                // Seleccionar dos libros al azar
                int libro1Idx = random.nextInt(biblioteca.length);
                int libro2Idx;
                do {
                    libro2Idx = random.nextInt(biblioteca.length);
                } while (libro1Idx == libro2Idx);
                
                Libro libro1 = biblioteca[libro1Idx];
                Libro libro2 = biblioteca[libro2Idx];
                
                // Obtener los libros en orden para evitar Bloqueo
                Libro primero, segundo;
                if (libro1.getId() < libro2.getId()) {
                    primero = libro1;
                    segundo = libro2;
                } else {
                    primero = libro2;
                    segundo = libro1;
                }
                
                // Adquirir Bloqueos en orden
                synchronized(primero) {
                    synchronized(segundo) {
                        System.out.println(getName() + " está usando " + libro1 + " y " + libro2);
                        
                        // Usar los libros (1-3 horas simuladas)
                        int tiempoUso = random.nextInt(3) + 1; // 1-3 horas
                        Thread.sleep(tiempoUso * 10); // 10ms por hora (simulado)
                        
                        System.out.println(getName() + " ha terminado de usar " + libro1 + " y " + libro2);
                    }
                }
                
                // Descansar (1-2 horas simuladas)
                int tiempoDescanso = random.nextInt(2) + 1; // 1-2 horas
                Thread.sleep(tiempoDescanso * 10); // 10ms por hora (simulado)
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " ha terminado de estudiar.");
        }
    }
}

// Clase principal del Ejercicio 2
public class Ejercicio2_Biblioteca {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n=== EJERCICIO 2 - BIBLIOTECA CON ESTUDIANTES ===");
        
        // Crear array de 9 libros
        Libro[] libros = new Libro[9];
        for (int i = 0; i < libros.length; i++) {
            libros[i] = new Libro(i + 1);
        }
        
        // Crear 4 estudiantes
        Estudiante[] estudiantes = new Estudiante[4];
        for (int i = 0; i < estudiantes.length; i++) {
            estudiantes[i] = new Estudiante(i + 1, libros);
        }
        
        // Iniciar estudiantes
        System.out.println("Iniciando simulación de biblioteca...");
        for (Estudiante estudiante : estudiantes) {
            estudiante.start();
        }
        
        // Ejecutar por un tiempo limitado
        Thread.sleep(30000); // 30 segundos de simulación
        
        // Terminar estudiantes
        System.out.println("\nFinalizando simulación...");
        for (Estudiante estudiante : estudiantes) {
            estudiante.interrupt();
        }
        
        // Esperar a que terminen
        for (Estudiante estudiante : estudiantes) {
            estudiante.join(1000);
        }
        
        System.out.println("Simulación completada.");
    }
}