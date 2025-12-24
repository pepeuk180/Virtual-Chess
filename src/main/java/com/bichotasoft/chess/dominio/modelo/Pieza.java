package main.java.com.bichotasoft.chess.dominio.modelo;
import main.java.com.bichotasoft.chess.configuracion.ConfiguracionJuego;
import main.java.com.bichotasoft.chess.dominio.modelo.piezas.Alfil;
import main.java.com.bichotasoft.chess.dominio.modelo.piezas.Caballo;
import main.java.com.bichotasoft.chess.dominio.modelo.piezas.Dama;
import main.java.com.bichotasoft.chess.dominio.modelo.piezas.Torre;
import main.java.com.bichotasoft.chess.dominio.modelo.piezas.Peon;
import java.util.Scanner;

public abstract class Pieza {
    private int maxCasillasMover;
    private String simbolo;
    private int Id;
    private String colorPieza;

    public Pieza(String simbolo, int maxCasillasMover) {
        this.simbolo = simbolo;
        this.maxCasillasMover = maxCasillasMover;
    }

    public Pieza(String simbolo, int Id , String color) {
        this.simbolo = simbolo;
        this.Id = Id;
        this.colorPieza  = color;
        aplicarColor();
    }

    public Pieza(String simbolo, int maxCasillasMover ,int Id , String color) {
        this.Id = Id;
        this.colorPieza = color;
        this.simbolo = simbolo;
        this.maxCasillasMover = maxCasillasMover;
        aplicarColor();
    }

    public int obtenerId() {
        return this.Id;
    }

    public void aplicarColor() {
        this.simbolo = this.simbolo.replaceAll("\u001B\\[[;\\d]*m", "");
        this.simbolo = this.colorPieza + this.simbolo + "\u001B[0m";
    }

    public static boolean esMovimientoVerticalValido(int[] coordenadasOrigen, int[] coordenadasDestino, Tablero tablero, int maximoCasillasMover) {
        int filaInicial = coordenadasOrigen[0];
        int columnaInicial = coordenadasOrigen[1];
        int filaDestino = coordenadasDestino[0];
        int columnaDestino = coordenadasDestino[1];
        if (columnaInicial == columnaDestino) {
            int casillasDistancia = Math.abs(filaDestino - filaInicial);
            if (casillasDistancia <= maximoCasillasMover) {
                int direccion = Integer.compare(filaDestino, filaInicial);
                Pieza piezaOrigen = tablero.obtenerPieza(filaInicial, columnaInicial);
                Pieza piezaDestino = tablero.obtenerPieza(filaDestino, columnaDestino);
                if ((casillasDistancia == 1) && (piezaDestino == null || piezaDestino.obtenerId() != piezaOrigen.obtenerId())) {
                    return true;
                }
                for (int fila = filaInicial + direccion; fila != filaDestino; fila += direccion) {
                    if (tablero.obtenerPieza(fila, columnaInicial) != null) {
                        return false;
                    }
                }
                if (piezaDestino == null) {
                    return true;
                } else {
                    return (piezaDestino.obtenerId() != piezaOrigen.obtenerId());
                }
            }
        }
        return false;
    }

    public static boolean esMovimientoHorizontalValido(int[] coordenadasOrigen, int[] coordenadasDestino, Tablero tablero, int maximoCasillasMover) {
        int filaInicial = coordenadasOrigen[0];
        int columnaInicial = coordenadasOrigen[1];
        int filaDestino = coordenadasDestino[0];
        int columnaDestino = coordenadasDestino[1];
        if (filaInicial == filaDestino) {
            int casillasDistancia = Math.abs(columnaDestino - columnaInicial);
            if (casillasDistancia <= maximoCasillasMover) {
                int direccion = Integer.compare(columnaDestino, columnaInicial);
                Pieza piezaOrigen = tablero.obtenerPieza(filaInicial, columnaInicial);
                Pieza piezaDestino = tablero.obtenerPieza(filaDestino, columnaDestino);
                if ((casillasDistancia == 1) && (piezaDestino == null || piezaDestino.obtenerId() != piezaOrigen.obtenerId())) {
                    return true;
                }
                for (int columna = columnaInicial + direccion; columna != columnaDestino; columna += direccion) {
                    if (tablero.obtenerPieza(filaInicial, columna) != null) {
                        return false;
                    }
                }
                if (piezaDestino == null) {
                    return true;
                } else {
                    return (piezaDestino.obtenerId() != piezaOrigen.obtenerId());
                }
            }
        }
        return false;
    }

    public static boolean esMovimientoDiagonalValido(int[] coordenadasOrigen, int[] coordenadasDestino, Tablero tablero, int maximoCasillasMover) {
        int filaInicial = coordenadasOrigen[0];
        int columnaInicial = coordenadasOrigen[1];
        int filaDestino = coordenadasDestino[0];
        int columnaDestino = coordenadasDestino[1];
        if (Math.abs(filaDestino - filaInicial) == Math.abs(columnaDestino - columnaInicial)) {
            int casillasDistancia = Math.abs(filaDestino - filaInicial);
            if (casillasDistancia <= maximoCasillasMover) {
                int direccionFila = Integer.compare(filaDestino, filaInicial);
                int direccionColumna = Integer.compare(columnaDestino, columnaInicial);
                int fila = filaInicial;
                int columna = columnaInicial;
                Pieza piezaOrigen = tablero.obtenerPieza(filaInicial, columnaInicial);
                Pieza piezaDestino = tablero.obtenerPieza(filaDestino, columnaDestino);
                if ((casillasDistancia == 1) && (piezaDestino == null || piezaDestino.obtenerId() != piezaOrigen.obtenerId())) {
                    return true;
                }
                for (int paso = 1; paso < casillasDistancia; paso++) {
                    fila += direccionFila;
                    columna += direccionColumna;
                    if (tablero.obtenerPieza(fila, columna) != null) {
                        return false;
                    }
                }
                if (piezaDestino == null) {
                    return true;
                } else {
                    return (piezaDestino.obtenerId() != piezaOrigen.obtenerId());
                }
            }
        }
        return false;
    }

    public boolean esMovimientoPeonValido(int[] coordenadasOrigen, int[] coordenadasDestino, Tablero tablero){
        int filaOrigen = coordenadasOrigen[0];
        int columnaOrigen = coordenadasOrigen[1];
        int filaDestino = coordenadasDestino[0];
        int columnaDestino = coordenadasDestino[1];
        int direccion = (this.Id == 1) ? 1 : -1;
        Pieza piezaOrigen = tablero.obtenerPieza(filaOrigen, columnaOrigen);
        Peon peon = (Peon) piezaOrigen;
        Pieza piezaDestino = tablero.obtenerPieza(filaDestino, columnaDestino);
        if (columnaOrigen == columnaDestino && piezaDestino == null) {
            if(filaDestino == filaOrigen + direccion) {
                peon.asignarCasillasPrimerMovimiento(1);
                int turnoactual = tablero.obtenerTurnoActual();
                peon.asignarTurnoPrimerMovimiento(turnoactual);
                return true;
            }
            if (peon.obtenerEsPrimerMovimiento() && filaDestino == filaOrigen + 2 * direccion) {
                int filaIntermedia = filaOrigen + direccion;
                Pieza piezaIntermedia = tablero.obtenerPieza(filaIntermedia, columnaOrigen);
                if(piezaIntermedia == null) {
                    peon.asignarCasillasPrimerMovimiento(2);
                    int turnoactual = tablero.obtenerTurnoActual();
                    peon.asignarTurnoPrimerMovimiento(turnoactual);
                    return true;
                }
            }
        }
        if (filaDestino == filaOrigen + direccion && Math.abs(columnaDestino - columnaOrigen) == 1) {
            if (piezaDestino != null) {
                return true;
            }
        }

        return false;
    }

    public boolean esMovimientoEnLValido(int[] coordenadasOrigen, int[] coordenadasDestino, Tablero tablero) {
        int filaInicial = coordenadasOrigen[0];
        int columnaInicial = coordenadasOrigen[1];
        int filaDestino = coordenadasDestino[0];
        int columnaDestino = coordenadasDestino[1];
        int dx = Math.abs(filaDestino - filaInicial);
        int dy = Math.abs(columnaDestino - columnaInicial);
        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
            Pieza piezaDestino = tablero.obtenerPieza(filaDestino, columnaDestino);
            Pieza piezaOrigen = tablero.obtenerPieza(filaInicial, columnaInicial);
            return piezaDestino == null || !piezaDestino.obtenerSimbolo().equals(piezaOrigen.obtenerSimbolo());
        }
        return false;
    }

    public abstract boolean validarMovimiento(int[] coordenadasOrigen, int[] coordenadasDestino, Tablero tablero);

    public abstract void asignarEsPrimerMovimiento(boolean esPrimerMovimiento);

    public abstract boolean obtenerEsPrimerMovimiento();

    public String obtenerSimbolo() {
        return this.simbolo;
    }

    public int obtenerMaxCasillasMover() {
        return this.maxCasillasMover;
    }

    public String obtenerColorPieza() {
        return this.colorPieza;
    }

    public void colocarColorPieza(String codigoColor) {
        this.colorPieza = codigoColor;
    }

    public Pieza promocionarPeon(Pieza pieza) {
        Scanner scanner = new Scanner(System.in);
        Pieza nuevaPieza = null;
        while (nuevaPieza == null) {
            try {
                System.out.println(ConfiguracionJuego.SALTO_LINEA + ConfiguracionJuego.COLOR_AMARILLO_FONDO + "🏁 El peón ha llegado al final del tablero. Elige una pieza para promocionar:".toUpperCase() + ConfiguracionJuego.COLOR_RESET);
                System.out.println("1. DAMA   - ♛ ");
                System.out.println("2. TORRE  - ♜ ");
                System.out.println("3. ALFIL  - ♝ ");
                System.out.println("4. CABALLO - ♞ ");
                System.out.print(ConfiguracionJuego.SALTO_LINEA + ConfiguracionJuego.COLOR_AZUL_FONDO + "Ingrese su elección (1-4): ".toUpperCase() + ConfiguracionJuego.COLOR_RESET);
                int opcion = scanner.nextInt();
                switch (opcion) {
                    case 1:
                        nuevaPieza = new Dama(pieza.obtenerId(), pieza.obtenerColorPieza());
                        System.out.println(ConfiguracionJuego.COLOR_AZUL_FONDO + "✅ Peón promocionado a Dama.".toUpperCase() + ConfiguracionJuego.COLOR_RESET);
                        break;
                    case 2:
                        nuevaPieza = new Torre(pieza.obtenerId(), pieza.obtenerColorPieza());
                        System.out.println(ConfiguracionJuego.COLOR_AZUL_FONDO + "✅ Peón promocionado a Torre.".toUpperCase() + ConfiguracionJuego.COLOR_RESET);
                        break;
                    case 3:
                        nuevaPieza = new Alfil(pieza.obtenerId(), pieza.obtenerColorPieza());
                        System.out.println(ConfiguracionJuego.COLOR_AZUL_FONDO + "✅ Peón promocionado a Alfil.".toUpperCase() + ConfiguracionJuego.COLOR_RESET);
                        break;
                    case 4:
                        nuevaPieza = new Caballo(pieza.obtenerId(), pieza.obtenerColorPieza());
                        System.out.println(ConfiguracionJuego.COLOR_AZUL_FONDO + "✅ Peón promocionado a Caballo.".toUpperCase() + ConfiguracionJuego.COLOR_RESET);
                        break;
                    default:
                        System.out.println(ConfiguracionJuego.COLOR_ROJO_FONDO + "❌ Opción inválida. Por favor, elige un número entre 1 y 4.".toUpperCase() + ConfiguracionJuego.COLOR_RESET);
                }
            } catch (Exception e) {
                System.out.println(ConfiguracionJuego.COLOR_ROJO_FONDO + "❌ Entrada inválida. Debes ingresar un número entre 1 y 4.".toUpperCase() + ConfiguracionJuego.COLOR_RESET);
                scanner.nextLine();
            }
        }
        return nuevaPieza;
    }

    public void asignarColor(String codigoColor) {
        this.colorPieza = codigoColor;
    }
}