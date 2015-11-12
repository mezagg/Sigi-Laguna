/**
Ejemplo de configuracion.

Para conformar la llave del nuc se deben de insertar el parametro asociado a la llave, junto con en cValor separado por comas
las claves asociadas a la llave, el ultimo valor es tomado como default

 */
insert into [Parametro] ( [cDescripcion], [cValor], [cTipoValor], [cClave])
values ( 'ESTADO', 'COA', '1', 'NUC_ESTADO');

insert into [Parametro] ( [cDescripcion], [cValor], [cTipoValor], [cClave])
values ( 'REGION', 'RN', '1', 'NUC_REGION');


insert into [Parametro] ( [cDescripcion], [cValor], [cTipoValor], [cClave])
values ( 'INSTITUCION', 'FG', '1', 'NUC_INSTITUCION');


insert into [Parametro] ( [cDescripcion], [cValor], [cTipoValor], [cClave])
values ( 'DISTRITO', 'SAL', '1', 'NUC_DISTRITO');


insert into [Parametro] ( [cDescripcion], [cValor], [cTipoValor], [cClave])
values ( 'UNIDAD', 'PGU,DEF', '1', 'NUC_UNIDAD');
