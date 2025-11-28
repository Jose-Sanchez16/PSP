package com.Tareadia28;
import java.util.Random;

public class SimulacionConfig {
    public static final int MS_POR_MINUTO = 10; // 1 minuto simulado = 10 ms reales
    public static final int TOTAL_LIBROS = 9;
    public static final int TOTAL_ESTUDIANTES = 4;
    
    // Método para obtener tiempo aleatorio en minutos
    public static int getTiempoAleatorio(Random random, int minMinutos, int maxMinutos) {
        return minMinutos + random.nextInt(maxMinutos - minMinutos + 1);
    }
    
    // Convertir minutos simulados a ms reales
    public static long minutosAMilisegundos(int minutos) {
        return minutos * MS_POR_MINUTO;
    }
}
