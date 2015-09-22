insert into [dbo].[CatDiscriminante] ( [bOpUIE], [cClaveDiscriminante], [catDistrito_id], [iTipo], [cNombre], [region_id])
values ( '0', '038', '4', '1', 'COORDINACION DE ADOLESCENTES', '1');

insert into [dbo].[CatUIEspecializada] ( [cAcronimo], [cClaveUIE], [cNombreUIE])
values ( 'UA', '40', 'UNIDAD DE ADOLESCENTES');

insert into [dbo].[CatAreasNegocio] ( [ConfInstitucion_id], [bEsEspecializada], [cNombre])
values ( '1', '1', 'UNIDAD DE ADOLESCENTES');

insert into [Funcion] ( [cDescripcionFuncion], [cNombreFuncion] )
values ( '/consultarCatalogoEntidadFederativa.do', '/consultarCatalogoEntidadFederativa.do' );



select * from funcion
where cNombreFuncion like '%consultarCatalogoEntidadFederativa%';

/* Se agrega al Modulo 1*/
insert into [ModuloFuncion] ( [Modulo_id], [Funcion_id]) values ( 1, X);


insert into [Funcion] ( [cDescripcionFuncion], [cNombreFuncion] )
values ( '/consultarCatalogoRegion.do', '/consultarCatalogoRegion.do' );

select * from funcion
where cNombreFuncion like '%consultarCatalogoRegion%';

/* Se agrega al Modulo 1*/
insert into [ModuloFuncion] ( [Modulo_id], [Funcion_id]) values ( 1, X);
