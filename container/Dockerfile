# Usa una imagen base de Java
FROM openjdk:8-jdk

COPY Ejercicio5toAndar.jar /app/Ejercicio5toAndar.jar

WORKDIR /app

ENV FUNCION ""
ENV RUTA ""
ENV TOTALROWS ""
ENV LETTER ""
ENV SEPARATOR ""
ENV TOTALLETTERS ""

# Comando para ejecutar la aplicación cuando se inicie el contenedor
#CMD ["java", "-jar", "Ejercicio5toAndar.jar", "${FUNCION}", "${RUTA}", "${TOTALROWS}", "${LETTER}", "${SEPARATOR}", "${TOTALLETTERS}"]
ENTRYPOINT java -jar Ejercicio5toAndar.jar ${FUNCION} ${RUTA} ${TOTALROWS} ${LETTER} ${SEPARATOR} ${TOTALLETTERS}