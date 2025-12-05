package com.Tareadia5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que simula el sistema bancario con cajeros concurrentes
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   SIMULACION DE CAJEROS AUTOMATICOS BANCARIOS   ");
        System.out.println("==================================================\n");
        
        // Crear la caja fuerte con saldo inicial de 10.000 €
        CajaFuerte caja = new CajaFuerte(10000);
        caja.mostrarEstado();
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\nSeleccione el modo de ejecucion:");
        System.out.println("1. Modo basico (3 cajeros fijos)");
        System.out.println("2. Modo ilimitado (n cajeros)");
        System.out.print("Opcion: ");
        
        int opcion = scanner.nextInt();
        
        switch (opcion) {
            case 1:
                modoBasico(caja);
                break;
            case 2:
                modoIlimitado(caja, scanner);
                break;
            default:
                System.out.println("Opcion no valida. Ejecutando modo basico.");
                modoBasico(caja);
        }
        
        scanner.close();
    }
    
    /**
     * Modo basico con 3 cajeros fijos
     */
    private static void modoBasico(CajaFuerte caja) {
        System.out.println("\nINICIANDO MODO BASICO (3 Cajeros)\n");
        
        // Crear 3 cajeros
        List<Cajero> cajeros = new ArrayList<>();
        cajeros.add(new Cajero(caja, "Cajero-1"));
        cajeros.add(new Cajero(caja, "Cajero-2"));
        cajeros.add(new Cajero(caja, "Cajero-3"));
        
        // Iniciar todos los cajeros
        for (Cajero cajero : cajeros) {
            cajero.start();
        }
        
        // Ejecutar durante un tiempo determinado (30 segundos)
        ejecutarTemporizado(cajeros, caja, 30);
    }
    
    /**
     * Modo con numero ilimitado de cajeros
     */
    private static void modoIlimitado(CajaFuerte caja, Scanner scanner) {
        System.out.print("\nIngrese el numero de cajeros a crear: ");
        int numCajeros = scanner.nextInt();
        
        System.out.println("\nINICIANDO MODO ILIMITADO (" + numCajeros + " Cajeros)\n");
        
        // Crear cajeros
        List<Cajero> cajeros = new ArrayList<>();
        for (int i = 1; i <= numCajeros; i++) {
            Cajero cajero = new Cajero(caja, "Cajero-" + i);
            cajeros.add(cajero);
            cajero.start();
        }
        
        // Ejecutar durante 45 segundos para ver mejor el comportamiento
        ejecutarTemporizado(cajeros, caja, 45);
    }
    
    /**
     * Ejecuta los cajeros durante un tiempo determinado
     */
    private static void ejecutarTemporizado(List<Cajero> cajeros, CajaFuerte caja, int segundos) {
        try {
            System.out.println("\nSimulacion ejecutandose por " + segundos + " segundos...\n");
            
            // Esperar el tiempo especificado
            Thread.sleep(segundos * 10L);
            
            // Detener todos los cajeros
            System.out.println("\nDETENIENDO CAJEROS...\n");
            for (Cajero cajero : cajeros) {
                cajero.detener();
            }
            
            // Esperar a que terminen
            for (Cajero cajero : cajeros) {
                cajero.join(2000);
            }
            
        } catch (InterruptedException e) {
            System.out.println("Simulacion interrumpida");
        }
        
        // Mostrar resumen final
        mostrarResumenFinal(cajeros, caja);
    }
    
    /**
     * Muestra el resumen final de la simulacion
     */
    private static void mostrarResumenFinal(List<Cajero> cajeros, CajaFuerte caja) {
        System.out.println("\n============================================================");
        System.out.println("            RESUMEN FINAL DE LA SIMULACION");
        System.out.println("============================================================");
        
        caja.mostrarEstado();
        
        System.out.println("\nESTADISTICAS POR CAJERO:");
        System.out.println("------------------------------------------------------------");
        
        int totalClientes = 0;
        int totalRetirado = 0;
        
        for (Cajero cajero : cajeros) {
            System.out.printf("%-15s: %3d clientes | %6d € retirados%n",
                    cajero.getNombre(),
                    cajero.getClientesAtendidos(),
                    cajero.getTotalRetirado());
            
            totalClientes += cajero.getClientesAtendidos();
            totalRetirado += cajero.getTotalRetirado();
        }
        
        System.out.println("------------------------------------------------------------");
        System.out.printf("TOTALES:         %3d clientes | %6d € retirados%n", 
                         totalClientes, totalRetirado);
        System.out.println("============================================================");
        
        // Mostrar analisis del comportamiento
        System.out.println("\nANALISIS DE COMPORTAMIENTO:");
        
        if (cajeros.size() > 3) {
            System.out.println("Con " + cajeros.size() + " cajeros concurrentes:");
            System.out.println("  - Mayor competencia por el recurso compartido (caja fuerte)");
            System.out.println("  - Mas tiempo de espera cuando el saldo es bajo");
            System.out.println("  - Posible starvation (inedicion) si no hay reposicion de fondos");
            System.out.println("  - El notifyAll() despierta a todos, pero solo uno puede proceder");
        } else {
            System.out.println("Con 3 cajeros:");
            System.out.println("  - Balance adecuado entre concurrencia y disponibilidad");
            System.out.println("  - Los depositos ocasionales mantienen la caja con fondos");
            System.out.println("  - Menos contencion que con muchos cajeros");
        }
    }
}
