CREATE TABLE IF NOT EXISTS estadisticas
(
    fecha timestamp without time zone,
    etiqueta character varying(1),
    minimo integer,
    maximo integer,
    promedio double precision,
    cantidad integer,
    desv_estandar double precision
);