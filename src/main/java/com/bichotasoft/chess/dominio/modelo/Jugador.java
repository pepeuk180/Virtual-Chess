package main.java.com.bichotasoft.chess.dominio.modelo;
import main.java.com.bichotasoft.chess.dominio.modelo.piezas.*;
import java.util.Timer;
import java.util.TimerTask;

public class Jugador {
    private Pieza[] piezas = new Pieza[16];
    private String codigoColor;
    private String nombreColor;
    private int Id;
    private int tiempoRestante = 15 * 60; // Tiempo restante en segundos (15 minutos = 900 segundos)
    private Timer timer;
    private TimerTask tarea;
    private boolean enEjecucion = false;


    public Jugador(String[] color, int Id) {
        this.codigoColor = color[0];
        this.nombreColor = color[1];
        this.Id = Id;
        this.piezas = new Pieza[]{new Torre(this.Id, this.codigoColor), new Caballo(this.Id, this.codigoColor) , new Alfil(this.Id, this.codigoColor), new Dama(this.Id, this.codigoColor), new Rey(this.Id, this.codigoColor), new Alfil(this.Id, this.codigoColor), new Caballo(this.Id, this.codigoColor), new Torre(this.Id , this.codigoColor),
          new Peon(this.Id, this.codigoColor), new Peon(this.Id, this.codigoColor), new Peon(this.Id, this.codigoColor), new Peon(this.Id, this.codigoColor), new Peon(this.Id, this.codigoColor), new Peon(this.Id, this.codigoColor ), new Peon(this.Id, this.codigoColor), new Peon(this.Id, this.codigoColor)};
    }

    public Pieza[] obtenerPiezas() {
        return this.piezas;
    }

    public String obtenerCodigoColor() {
        return this.codigoColor;
    }

    public String obtenerNombreColor() {
        return this.nombreColor;
    }
    public int obtenerId() {
        return this.Id;
    }

    public void asignarColor(String [] color) {
        this.codigoColor = color[0];
        this.nombreColor = color[1];
        for(Pieza pieza : this.obtenerPiezas()){
            pieza.asignarColor(this.codigoColor);
            pieza.aplicarColor();
        }
    }
    public void reiniciarPiezas() {
        for(Pieza pieza : this.obtenerPiezas()){
            pieza.asignarEsPrimerMovimiento(true);
            if(pieza instanceof Peon){
                Peon peon = (Peon) pieza;
                peon.asignarTurnoPrimerMovimiento(0);
                peon.asignarCasillasPrimerMovimiento(0);
            }
        }
    }

    public void iniciarTiempo() {
        if (enEjecucion) {
            return;
        }

        timer = new Timer();
        tarea = new TimerTask() {
            @Override
            public void run() {
                if (tiempoRestante > 0) {
                    tiempoRestante--;

                } else {
                    pausarTiempo();
                }
            }
        };

        timer.scheduleAtFixedRate(tarea, 0, 1000);
        enEjecucion = true;
    }

    public void pausarTiempo() {
        if (!enEjecucion) {
            return;
        }

        tarea.cancel();
        timer.cancel();
        enEjecucion = false;
    }

    public void reiniciarTiempo() {
        pausarTiempo();
        tiempoRestante = 15 * 60;
    }

    public String formatTiempo() {
        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    public void mostrarTiempoActual() {
        System.out.println("Tiempo actual del jugador " + this.nombreColor + ": " + formatTiempo());
    }

    public int obtenerSegundosRestantes() {
        return tiempoRestante;
    }


}
