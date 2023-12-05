package com.quintoandar.cronjobs.libs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GeneradorCSV {

    public static void generateCsv (String filePath, int totalRows, char letter,char separator,int totalLetters) {
        try (FileWriter writer = new FileWriter(filePath)){
            // Escribir la cabecera del CSV
            writer.append("label").append(separator).append("valor\n");

            for (int i = 0; i < totalRows ; i++) {

                char label = generateRandom(totalLetters,letter);
                int  value = generateRandom(totalRows);
                // Escribir en el archivo CSV
                writer.append(label).append(separator).append(Integer.toString(value)).append("\n");
            }
            System.out.println("Archivo CSV generado con éxito en: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static char  generateRandom ( int bound, char letter) {
        Random random = new Random();
        return (char) (letter + random.nextInt(bound));
    }
    private static int  generateRandom ( int bound) {
        Random random = new Random();
        return  (random.nextInt(bound) /*- 10*/);
    }

}