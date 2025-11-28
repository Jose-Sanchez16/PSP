package com.Tareadia28;

import java.util.concurrent.locks.ReentrantLock;

public class Libro {
    private final int id;
    private final ReentrantLock lock;
    
    public Libro(int id) {
        this.id = id;
        this.lock = new ReentrantLock(true); // true para fairness
    }
    
    public void adquirir() {
        lock.lock();
    }
    
    public void liberar() {
        lock.unlock();
    }
    
    public boolean intentarAdquirir() {
        return lock.tryLock();
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return "Libro-" + id;
    }
}
