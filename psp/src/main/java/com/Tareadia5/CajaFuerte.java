package com.Tareadia5;

public class CajaFuerte {
    private int dineroDisponible;

    public CajaFuerte(int saldoInicial) {
        this.dineroDisponible = saldoInicial;
    }
    
    public synchronized void retirar(int cantidad, String cajero) throws InterruptedException {
        System.out.println("[" + cajero + "] intenta retirar " + cantidad + 
                         " € | dinero disponible: " + dineroDisponible + " €");
        
        // Esperar mientras no haya suficiente dinero (usamos while para evitar falsos despertamientos)
        while (dineroDisponible < cantidad) {
            System.out.println("[" + cajero + "] ESPERA: dinero insuficiente. " +
                             "Solicita: " + cantidad + " €, Disponible: " + dineroDisponible + " €");
            wait(); // Libera el monitor y espera
        }
        
        // Realizar el retiro
        dineroDisponible -= cantidad;
        System.out.println("[" + cajero + "] RETIRO EXITOSO | dinero restante: " + dineroDisponible + " €");
    }
    
    public synchronized void depositar(int cantidad, String cajero) {
        int saldoAnterior = dineroDisponible;
        dineroDisponible += cantidad;
        System.out.println("[" + cajero + "] DEPOSITO de " + cantidad + " € | " +
                         "Saldo anterior: " + saldoAnterior + " € | " +
                         "Nuevo saldo: " + dineroDisponible + " €");
        
        // Notificar a todos los hilos que esperan por dinero
        notifyAll();
    }
    
    public synchronized int getDineroDisponible() {
        return dineroDisponible;
    }
    
    /**
     * Método para mostrar el estado actual de la caja
     */
    public synchronized void mostrarEstado() {
        System.out.println("==================================================");
        System.out.println("ESTADO CAJA FUERTE: " + dineroDisponible + " €");
        System.out.println("==================================================");
    }
}