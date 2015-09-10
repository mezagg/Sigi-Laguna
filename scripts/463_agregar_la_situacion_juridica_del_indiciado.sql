


insert into [Funcion] ( [cDescripcionFuncion], [cNombreFuncion] )
values ( '/ConsultarCatalogoSituacionJuridicaDetenido.do', '/ConsultarCatalogoSituacionJuridicaDetenido.do' );


/* Se obtiene el funcion_id para usarlo en ModuloFuncio */
select * from funcion
where cNombreFuncion like '%ConsultarCatalogoSituacionJuridicaDetenido%';

/* Se agrega al Modulo 2*/
insert into [ModuloFuncion] ( [Modulo_id], [Funcion_id]) values ( 2, X);


/* Actualizacion de catalogo*/
insert into [Catalogo] ( [cDescripcion], [bEsSistema], [Catalogo_id], [bEsActivo], [cNombre])
values ( 'Situacion Juridica Detenido', '1', '214', '1', 'Situacion Juridica Detenido');

insert into [CampoCatalogo] (  [CampoCatalogo_id],[cNombreCampo], [bEsRecursivo], [Catalogo_id], [iTipoDato], [bEsLlave])
values ( 225,'Situacion Juridica Detenido', 0, '214', 0, 1);

insert into [valor] ( [cValor], [CampoCatalogo_id], [Registro_id], [Valor_id])
values ( 'Detenido', '225', '1', '99320');

insert into [valor] ( [cValor], [CampoCatalogo_id], [Registro_id], [Valor_id])
values ( 'Libertad', '225', '2', '99321');

insert into [valor] ( [cValor], [CampoCatalogo_id], [Registro_id], [Valor_id])
values ( 'Judicializado', '225', '3', '99322');