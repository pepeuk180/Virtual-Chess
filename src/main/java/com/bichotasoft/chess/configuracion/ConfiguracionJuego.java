package main.java.com.bichotasoft.chess.configuracion;

public class ConfiguracionJuego {
    //COLORES
    public static final String COLOR_RESET = "\u001B[0m";
    public static final String COLOR_ROJO_FONDO = "\u001B[41m";
    public static final String COLOR_AZUL_FONDO = "\u001b[44m";
    public static final String COLOR_AMARILLO_FONDO = "\u001B[43m";
    public static final String NEGRITA = "\u001B[1m";


    //Palabras reservadas
    public static final String ORIGEN = "ORIGEN :" + COLOR_RESET + " ";
    public static final String DESTINO = "DESTINO :" + COLOR_RESET + " ";
    public static final String ERROR = "❌ ERROR: ";
    public static final String ADVERTENCIA = "⚠ ADVERTENCIA: ";
    public static final String ENROQUE = "ENROQUE ";
    public static final String REALIZADO_CON_EXITO = "REALIZADO CON ÉXITO ";
    public static final String SIGNO_ADMIRACION = "❕ ";
    public static final String JUGADOR = "JUGADOR ";
    public static final String JUGADOR_1 = JUGADOR + "1";
    public static final String JUGADOR_2 = JUGADOR + "2";
    public static final String SALTO_LINEA = "\n";

    // Mensajes para errores
    public static final String ERROR_TAMANO_COORDENADAS = SALTO_LINEA + COLOR_ROJO_FONDO + ERROR + "TAMAÑO DE COORDENADA NO VALIDA" + COLOR_RESET + SALTO_LINEA;
    public static final String ERROR_INDICES_COORDENADAS = SALTO_LINEA + COLOR_ROJO_FONDO + ERROR + "COORDENADA NO VALIDA" + COLOR_RESET + SALTO_LINEA;
    public static final String ERROR_SELECCION_PIEZA = SALTO_LINEA + COLOR_ROJO_FONDO + ERROR + "LA PIEZA NO PERTENECE AL JUGADOR" + COLOR_RESET + SALTO_LINEA;
    public static final String ERROR_MOVIMIENTO_INVALIDO = SALTO_LINEA + COLOR_ROJO_FONDO + ERROR + "MOVIMIENTO NO VALIDO" + COLOR_RESET + SALTO_LINEA;
    public static final String ERROR_COORDENADA_VACIA = SALTO_LINEA + COLOR_ROJO_FONDO + ERROR + "COORDENADA SIN PIEZA SELECCIONADA" + COLOR_RESET + SALTO_LINEA;
    public static final String ERROR_ENROQUE_INVALIDO = SALTO_LINEA + COLOR_ROJO_FONDO + ERROR + "NO PUEDE HACER ENROQUE, PORQUE NO CUMPLE LOS REQUERIMIENTOS" + COLOR_RESET + SALTO_LINEA;
    public static final String ERROR_COORDENADAS_SIMILARES = SALTO_LINEA + COLOR_ROJO_FONDO + ERROR + "INSERTÓ COORDENADAS SIMILARES" + COLOR_RESET + SALTO_LINEA;
    public static final String ERROR_JAQUE_1 = SALTO_LINEA + COLOR_ROJO_FONDO + ERROR + "MOVIMIENTO INVALIDO, TU REY ";
    public static final String ERROR_JAQUE_2 = "SEGUIRA EN JAQUE SI MUEVES ESA PIEZA" + COLOR_RESET + SALTO_LINEA;
    public static final String ERROR_VALIDADOR_NUMEROS = SALTO_LINEA + COLOR_ROJO_FONDO + ERROR + "DEBE INSERTAR UN NUMERO VALIDO Y DENTRO DEL RANGO" + COLOR_RESET + SALTO_LINEA;


    // ADVERTENCIAS
    public static final String MOVIMIENTO_INVALIDO_JAQUE_1 = SALTO_LINEA + COLOR_AMARILLO_FONDO + ADVERTENCIA + "ESE MOVIMIENTO PUDO DEJAR A TU REY EN JAQUE ¡ VUELVE A INTENTARLO !" + COLOR_RESET + SALTO_LINEA;
    public static final String NOTIFICACION_JAQUE_1 = SALTO_LINEA + COLOR_AMARILLO_FONDO + ADVERTENCIA + " ¡" + JUGADOR;
    public static final String NOTIFICACION_JAQUE_2 = " ESTAS EN JAQUE !" + COLOR_RESET + SALTO_LINEA;
    public static final String NOTIFICACION_SOLICITAR_TABLAS_1 = SALTO_LINEA + COLOR_AMARILLO_FONDO + NEGRITA + ADVERTENCIA + "El " + JUGADOR;
    public static final String NOTIFICACION_SOLICITAR_TABLAS_2 = " SOLICITO TABLAS" + COLOR_RESET + SALTO_LINEA;

    //NOTIFICACIONES
    public static final String TURNO_JUGADOR = "TURNO DE LAS PIEZAS DE COLOR ";
    public static final String TABLAS_EMPATE_1 = COLOR_AZUL_FONDO + NEGRITA + SIGNO_ADMIRACION + "SE DETECTO TABLAS, ES UN EMPATE" + COLOR_RESET + SALTO_LINEA;
    public static final String TABLAS_EMPATE_2 = COLOR_AZUL_FONDO + NEGRITA + SIGNO_ADMIRACION + "SE ACORDARON TABLAS, FIN DEL JUEGO" + COLOR_RESET + SALTO_LINEA;
    public static final String CABECERA_SELECCION_COLOR = COLOR_AZUL_FONDO + NEGRITA + "---- COLORES DISPONIBLES ----" + COLOR_RESET;
    public static final String NOTIFICACION_INSERCION_COORDENADAS = COLOR_AZUL_FONDO + NEGRITA + SIGNO_ADMIRACION + "INSERTAR COORDENADA ";
    public static final String NOTIFICACION_ENROQUE_1 = COLOR_AZUL_FONDO + NEGRITA + SIGNO_ADMIRACION + ENROQUE;
    public static final String NOTIFICACION_ENROQUE_2 = REALIZADO_CON_EXITO + COLOR_RESET + SALTO_LINEA;


    // MENSAJES
    public static final String OPCIONES_TABLAS = "\tACEPTAR: 1   |   DENEGAR: 2";
    public static final String SELECCIONA_OPCION = "SELECCIONA LA OPCIÓN: ";
    public static final String JAQUE_MATE = " GANÓ POR JAQUE MATE" + COLOR_RESET;

}
