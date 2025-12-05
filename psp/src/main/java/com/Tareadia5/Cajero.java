package com.Tareadia5;

import java.util.Random;

public class Cajero extends Thread {
    private final CajaFuerte caja;
    private final String nombre;
    private final Random random;
    private int clientesAtendidos;
    private int totalRetirado;
    private boolean enFuncionamiento;
    
    public Cajero(CajaFuerte caja, String nombre) {
        this.caja = caja;
        this.nombre = nombre;
        this.random = new Random();
        this.clientesAtendidos = 0;
        this.totalRetirado = 0;
        this.enFuncionamiento = true;
    }
    
    @Override
    public void run() {
        System.out.println("[" + nombre + "] INICIA OPERACIONES");
        
        // El cajero funciona indefinidamente
        while (enFuncionamiento) {
            try {
                // Simular llegada de cliente
                simularEsperaCliente();
                
                // Generar cantidad aleatoria para retiro (entre 50 y 500 €)
                int cantidadRetiro = generarCantidadRetiro();
                
                // Intentar realizar el retiro
                caja.retirar(cantidadRetiro, nombre);
                
                clientesAtendidos++;
                totalRetirado += cantidadRetiro;
                
                // Simular tiempo de procesamiento del retiro (50-200 ms)
                simularTiempoProcesamiento();
                
                // Ocasionalmente depositar dinero (simulando reposición o depósito de cliente)
                if (debeRealizarDeposito()) {
                    int cantidadDeposito = generarCantidadDeposito();
                    caja.depositar(cantidadDeposito, nombre);
                }
                
                // Mostrar estadísticas periódicamente
                if (clientesAtendidos % 5 == 0) {
                    mostrarEstadisticas();
                }
                
            } catch (InterruptedException e) {
                System.out.println("[" + nombre + "] Interrumpido. Finalizando operaciones...");
                enFuncionamiento = false;
            }
        }
        
        System.out.println("[" + nombre + "] FINALIZA OPERACIONES. " +
                          "Clientes atendidos: " + clientesAtendidos + 
                          ", Total retirado: " + totalRetirado + " €");
    }

    private int generarCantidadRetiro() {
        return 50 + random.nextInt(451); // 50-500 €
    }

    private int generarCantidadDeposito() {
        return 100 + random.nextInt(901); // 100-1000 €
    }

    private boolean debeRealizarDeposito() {
        return random.nextDouble() < 0.2; // 20% de probabilidad
    }

    private void simularEsperaCliente() throws InterruptedException {
        int espera = 30 + random.nextInt(71); // 30-100 ms
        Thread.sleep(espera);
    }
    
    private void simularTiempoProcesamiento() throws InterruptedException {
        int procesamiento = 50 + random.nextInt(151); // 50-200 ms
        Thread.sleep(procesamiento);
    }
    
    /**
     * Muestra estadísticas del cajero
     */
    private void mostrarEstadisticas() {
        System.out.println("----------------------------------------");
        System.out.println("ESTADISTICAS [" + nombre + "]:");
        System.out.println("   Clientes atendidos: " + clientesAtendidos);
        System.out.println("   Total retirado: " + totalRetirado + " €");
        System.out.println("   Saldo actual caja: " + caja.getDineroDisponible() + " €");
        System.out.println("----------------------------------------");
    }
    
    /**
     * Detiene el funcionamiento del cajero
     */
    public void detener() {
        this.enFuncionamiento = false;
        this.interrupt();
    }
    
    // Getters para estadísticas
    public int getClientesAtendidos() {
        return clientesAtendidos;
    }
    
    public int getTotalRetirado() {
        return totalRetirado;
    }
    
    public String getNombre() {
        return nombre;
    }
}