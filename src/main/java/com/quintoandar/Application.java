package com.quintoandar;

import java.util.HashSet;
import java.util.logging.Logger;
import java.util.logging.Level;
import static com.quintoandar.GeneradorCSV.generateCsv;
import static com.quintoandar.ProcesarCSV.processCsv;


public class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());
    public static void main(String[] args) {

        final int len = args.length;
        HashSet<String> funcionesPosibles = new HashSet<>();

        funcionesPosibles.add("procesar");
        funcionesPosibles.add("generar");
        funcionesPosibles.add("all");

        int totalRows = -1;
        char letter = 'a';
        char separator = ',';
        int totalLetters = -1;

        /* Validaciones */
        if ( len < 2 ){
            logger.log(Level.SEVERE,"Se debe especificar la funcion y la ruta del archivo");
            System.exit(1);
        }

        String funcion = args[0];
        String filePath = args[1];


        if ( !funcionesPosibles.contains(funcion) ){
            logger.log(Level.SEVERE,"Las funcion "+funcion+" no esta soportada");
            System.exit(1);
        }

        if ( funcion.equalsIgnoreCase("generar") && len < 6 ) {
            logger.log(Level.SEVERE,"Las funcion gerar requiere filepath rows letter separetor totalLetters");
            System.exit(1);
        }

        if (  funcion.equalsIgnoreCase("generar") || funcion.equalsIgnoreCase("all") ){
            totalRows       = Integer.parseInt(args[2]);
            letter          = args[3].charAt(0);
            separator       =  args[4].charAt(0);
            totalLetters    = Integer.parseInt(args[5]);
        }


        /* */
        switch (args[0]){
            case "generar":
                logger.log(Level.INFO,"Proceso de generacion de archivos Iniciado");
                generateCsv(filePath, totalRows, letter, separator, totalLetters);
                logger.log(Level.INFO,"Proceso de generacion de archivos Finalizado");
                System.exit(0);
                break;
            case "procesar":
                logger.log(Level.INFO,"Proceso de procesamiento de archivos Iniciado");
                processCsv(filePath);
                logger.log(Level.INFO,"Proceso de procesamiento de archivos Finalizado");
                System.exit(0);
                break;
            case "all":
                logger.log(Level.INFO,"Proceso de generacion de archivos Iniciado");
                generateCsv(filePath, totalRows, letter, separator, totalLetters);
                logger.log(Level.INFO,"Proceso de generacion de archivos Finalizado");
                logger.log(Level.INFO,"Proceso de procesamiento de archivos Iniciado");
                processCsv(filePath);
                logger.log(Level.INFO,"Proceso de generacion de archivos Finalizado");
                System.exit(0);
                break;
        }
    }
}
