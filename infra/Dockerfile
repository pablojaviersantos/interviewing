# Usa una imagen base de Java
FROM openjdk:8-jdk

COPY /infra/csvProcessor.jar /app/csvProcessor.jar

WORKDIR /app

ENV FUNCION ""
ENV RUTA ""
ENV TOTALROWS ""
ENV LETTER ""
ENV SEPARATOR ""
ENV TOTALLETTERS ""
ENV POSTGRES_DB ""
ENV POSTGRES_USER ""
ENV POSTGRES_PASSWORD ""
ENV POSTGRES_HOST ""

# Comando para ejecutar la aplicación cuando se inicie el contenedor
ENTRYPOINT java -jar csvProcessor.jar ${FUNCION} ${RUTA} ${TOTALROWS} ${LETTER} ${SEPARATOR} ${TOTALLETTERS}