package com.primeradepsp;

public class HilosSimples extends Thread {

    public HilosSimples(String name){
        super (name);
    }
    @Override
    public void run(){
        for(int i = 0; i < 5; i++){
            System.out.println(getName()+ ": Hola desde el hilo " + getName().charAt(getName().length() - 1));
            try{
                Thread.sleep(500);
            }catch(InterruptedException e){
                System.out.println(getName() + "fue interrumpido");
            }
        }
    }
    public static void main (String[] args){
        HilosSimples hilo1 = new HilosSimples("Hilo-1");
        HilosSimples hilo2 = new HilosSimples("Hilo-2");
        HilosSimples hilo3 = new HilosSimples("Hilo-3");

        hilo1.start();
        hilo2.start();
        hilo3.start();
    }
    
}
