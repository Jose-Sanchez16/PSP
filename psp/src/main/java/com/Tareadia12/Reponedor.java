package com.Tareadia12;

public class Reponedor extends Thread {
    private final Inventario inventario;
    private final int CANTIDAD_REPOSICION = 10;

    public Reponedor(Inventario inventario) {
        super("Reponedor");
        this.inventario = inventario;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (inventario) {
                    // Esperar hasta que el stock sea bajo
                    while (inventario.getStock() >= inventario.getStockMinimoReponer()) {
                        System.out.println("[Reponedor] Stock suficiente (" + inventario.getStock() + 
                                          "). Esperando...");
                        inventario.wait(); // Espera a ser notificado
                    }

                    // Reponer stock
                    Thread.sleep(500); // Simular tiempo de reposición
                    inventario.reponer(CANTIDAD_REPOSICION, getName());
                }
                
                // Pequeña pausa entre revisiones
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("[Reponedor] Reponedor interrumpido. Terminando...");
            Thread.currentThread().interrupt();
        }
    }
}
