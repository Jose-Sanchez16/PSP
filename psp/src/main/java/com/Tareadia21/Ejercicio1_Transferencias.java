package com.Tareadia21;
import java.util.Random;

// Clase Cuenta
class Cuenta {
    private String nombre;
    private double saldo;
    
    public Cuenta(String nombre, double saldoInicial) {
        this.nombre = nombre;
        this.saldo = saldoInicial;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public synchronized double getSaldo() {
        return saldo;
    }
    
    public synchronized void ingresar(double cantidad) {
        saldo += cantidad;
    }
    
    public synchronized void retirar(double cantidad) {
        saldo -= cantidad;
    }
}

// Gestor de transferencias SIN Bloqueo (orden correcto)
class TransferenciasSinBloqueo {
    public static void transferir(Cuenta desde, Cuenta hacia, double cantidad) {
        if (cantidad <= 0) return;
        
        // ESTABLECER ORDEN GLOBAL para evitar Bloqueo
        Cuenta primera, segunda;
        if (desde.getNombre().compareTo(hacia.getNombre()) < 0) {
            primera = desde;
            segunda = hacia;
        } else {
            primera = hacia;
            segunda = desde;
        }
        
        synchronized(primera) {
            synchronized(segunda) {
                if (desde.getSaldo() >= cantidad) {
                    desde.retirar(cantidad);
                    hacia.ingresar(cantidad);
                    System.out.println(Thread.currentThread().getName() + 
                                     " - Transferencia: " + cantidad + " de " + 
                                     desde.getNombre() + " a " + hacia.getNombre());
                }
            }
        }
    }
}

// Gestor de transferencias CON Bloqueo (orden incorrecto)
class TransferenciasConBloqueo {
    public static void transferir(Cuenta desde, Cuenta hacia, double cantidad) {
        if (cantidad <= 0) return;
        
        // BLOQUEO EN ORDEN DE PARÁMETROS - PUEDE CAUSAR BLOQUEO
        synchronized(desde) {
            synchronized(hacia) {
                if (desde.getSaldo() >= cantidad) {
                    desde.retirar(cantidad);
                    hacia.ingresar(cantidad);
                    System.out.println(Thread.currentThread().getName() + 
                                     " - Transferencia: " + cantidad + " de " + 
                                     desde.getNombre() + " a " + hacia.getNombre());
                }
            }
        }
    }
}

public class Ejercicio1_Transferencias {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== EJERCICIO 1 - TRANSFERENCIAS BANCARIAS ===");
        
        // Crear cuentas
        Cuenta c1 = new Cuenta("ACC01", 1000);
        Cuenta c2 = new Cuenta("ACC02", 1000);
        
        System.out.println("\n1. Probando SIN bloqueo (orden correcto):");
        probarTransferencias(c1, c2, false, false);
        
        System.out.println("\n2. Probando CON bloqueo (orden incorrecto):");
        probarTransferencias(c1, c2, true, false);
        
        System.out.println("\n3. Probando CON bloqueo pero con esperas aleatorias:");
        probarTransferencias(c1, c2, true, true);
    }
    
    private static void probarTransferencias(Cuenta c1, Cuenta c2, boolean conBloqueo, boolean conEsperas) 
            throws InterruptedException {
        Random random = new Random();
        
        // Hilo 1: transfiere de c1 a c2
        Thread hilo1 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                if (conEsperas) {
                    try { 
                        Thread.sleep(random.nextInt(5)); 
                    } catch (InterruptedException e) {}
                }
                
                if (conBloqueo) {
                    TransferenciasConBloqueo.transferir(c1, c2, 10);
                } else {
                    TransferenciasSinBloqueo.transferir(c1, c2, 10);
                }
            }
        }, "Hilo1");
        
        // Hilo 2: transfiere de c2 a c1  
        Thread hilo2 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                if (conEsperas) {
                    try { 
                        Thread.sleep(random.nextInt(5)); 
                    } catch (InterruptedException e) {}
                }
                
                if (conBloqueo) {
                    TransferenciasConBloqueo.transferir(c2, c1, 10);
                } else {
                    TransferenciasSinBloqueo.transferir(c2, c1, 10);
                }
            }
        }, "Hilo2");
        
        hilo1.start();
        hilo2.start();
        
        // Esperar con timeout para evitar bloqueo eterno
        hilo1.join(5000);
        hilo2.join(5000);
        
        if (hilo1.isAlive() || hilo2.isAlive()) {
            System.out.println("!BLOQUEO DETECTADO! Los hilos están bloqueados.");
            hilo1.interrupt();
            hilo2.interrupt();
        } else {
            System.out.println("Transferencias completadas sin bloqueo");
            System.out.println("Saldo final c1: " + c1.getSaldo());
            System.out.println("Saldo final c2: " + c2.getSaldo());
        }
        
        // Restaurar saldos para siguiente prueba
        c1.ingresar(1000 - c1.getSaldo());
        c2.ingresar(1000 - c2.getSaldo());
    }
}
