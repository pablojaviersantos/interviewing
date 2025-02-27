package com.quintoandar.cronjobs.libs;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;

import java.io.IOException;

import java.sql.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProcesarCSV {

    public static void processCsv(String filePath) {



        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            // Leer todas las filas del CSV
            List<String[]> rows = reader.readAll();

            // Mapa para almacenar los datos agrupados por etiqueta
            Map<String, LabelStats> labelStatsMap = new HashMap<>();

            // Procesar cada fila del CSV
            for (String[] row : rows) {
                if (row.length == 2 && !row[0].equalsIgnoreCase("label")) {
                    String label = row[0];
                    int valor = Integer.parseInt(row[1]);

                    // Obtener o crear estadísticas para la etiqueta actual
                    LabelStats labelStats = labelStatsMap.computeIfAbsent(label, k -> new LabelStats());

                    // Actualizar las estadísticas con el nuevo valor
                    labelStats.update(valor);
                }
            }


            // Imprimir las estadísticas por etiqueta
            labelStatsMap.forEach((label, stats) -> {
                System.out.println("Label: " + label);
                System.out.println("   Mínimo: " + stats.getMin());
                System.out.println("   Máximo: " + stats.getMax());
                System.out.println("   Promedio: " + stats.getAverage());
                System.out.println("   Cantidad: " + stats.getCount());
                System.out.println("   Desviación Estándar: " + stats.getStdDev());
                System.out.println("-----------------------------");
            });

            // Insertar estadísticas en la tabla de PostgreSQL
            insertStatistics(labelStatsMap);

        } catch (IOException | CsvException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertStatistics(Map<String, LabelStats> labelStatsMap) throws SQLException {

       //TODO 6 Elliminar este bloque de codigo y utilizar la coneccion nueva.
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://"+System.getenv("POSTGRES_HOST")+":"+System.getenv("POSTGRES_PORT")+"/"+System.getenv("POSTGRES_DB"),
                System.getenv("POSTGRES_USER"),
                System.getenv("POSTGRES_PASSWORD")
        );

        // Obtener la fecha actual
        java.sql.Timestamp fecha = new java.sql.Timestamp(System.currentTimeMillis());

        // Preparar la consulta SQL para la inserción
        String sql = "INSERT INTO estadisticas (fecha, etiqueta, minimo, maximo, promedio, cantidad, desv_estandar) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Crear la declaración preparada
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Iterar sobre las estadísticas y ejecutar la inserción
            for (Map.Entry<String, LabelStats> entry : labelStatsMap.entrySet()) {
                LabelStats stats = entry.getValue();

                statement.setTimestamp(1, fecha);
                statement.setString(2, entry.getKey());
                statement.setInt(3, stats.getMin());
                statement.setInt(4, stats.getMax());
                statement.setDouble(5, stats.getAverage());
                statement.setInt(6, stats.getCount());
                statement.setDouble(7, stats.getStdDev());

                // Ejecutar la inserción
                statement.executeUpdate();
            }
        }
    }

    // Clase para almacenar estadísticas de cada etiqueta
    static class LabelStats {
        private int min = Integer.MAX_VALUE;
        private int max = Integer.MIN_VALUE;
        private double sum = 0;
        private double sumSquared = 0;
        private int count = 0;

        public void update(int value) {
            min = Math.min(min, value);
            max = Math.max(max, value);
            sum += value;
            sumSquared += Math.pow(value, 2);
            count++;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public double getAverage() {
            return sum / count;
        }

        public int getCount() {
            return count;
        }

        public double getStdDev() {
            double mean = sum / count;
            return Math.sqrt((sumSquared / count) - Math.pow(mean, 2));
        }
    }
}