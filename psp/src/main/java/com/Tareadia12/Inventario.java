package com.Tareadia12;

public class Inventario {
    private int stock;
    private final int STOCK_MINIMO_REPONER = 5;
    private final int CANTIDAD_REPOSICION = 10;

    public Inventario(int stockInicial) {
        this.stock = stockInicial;
    }

    public synchronized void vender(String empleado) {
        try {
            // Bucle de seguridad con while
            while (stock < 1) {
                System.out.println("[" + empleado + "] Esperando stock... (Stock actual: " + stock + ")");
                wait(); // Libera el monitor y espera
            }

            // Proceso de venta
            stock--;
            System.out.println("[" + empleado + "] Venta realizada. Stock restante: " + stock);

            // Si el stock es bajo, notificar al reponedor
            if (stock < STOCK_MINIMO_REPONER) {
                notifyAll(); // Despertar al reponedor si está esperando
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[" + empleado + "] Interrumpido durante la venta.");
        }
    }

    public synchronized void reponer(int cantidad, String reponedor) {
        stock += cantidad;
        System.out.println("[" + reponedor + "] Repuestos " + cantidad + " códigos. Stock actual: " + stock);
        notifyAll(); // Despertar a todos los empleados en espera
    }

    public synchronized int getStock() {
        return stock;
    }

    public int getStockMinimoReponer() {
        return STOCK_MINIMO_REPONER;
    }
}