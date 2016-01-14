
ALTER TABLE [dbo].[Caso]
ALTER COLUMN [cNumeroGeneralCaso] [varchar](40);
GO

ALTER TABLE [dbo].[Medida]
ALTER COLUMN [cNumeroCaso] [varchar](40);
GO

ALTER TABLE [dbo].[Notificacion]
ALTER COLUMN [cNumeroCasoAsociado] [varchar](40);
GO

ALTER TABLE [dbo].[Solicitud]
ALTER COLUMN [cNumeroCasoAsociado] [varchar](40);
GO



ALTER TABLE [dbo].[CatDiscriminante]
ADD [region_id] [numeric](18, 0) NULL;
GO

ALTER TABLE [dbo].[CatDistrito]
ADD [region_id] [numeric](18, 0) NULL;
GO


CREATE TABLE [dbo].[Region](
	[region_id] [numeric](18, 0) NOT NULL,
	[cClaveRegion] [nvarchar](3) NOT NULL,
	[cNombre] [nvarchar](50) NOT NULL,
	[entidadFederativa_id] [decimal](18, 0) NOT NULL,
 CONSTRAINT [PK_Region] PRIMARY KEY CLUSTERED
(
	[region_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

-----GM
ALTER TABLE [dbo].[CatDiscriminante]  WITH CHECK ADD  CONSTRAINT [FK_CatDiscriminante_Region] FOREIGN KEY([region_id])
REFERENCES [dbo].[Region] ([region_id])
GO


ALTER TABLE [dbo].[CatDistrito]  WITH CHECK ADD  CONSTRAINT [FK_CatDistrito_Region] FOREIGN KEY([region_id])
REFERENCES [dbo].[Region] ([region_id])
GO

ALTER TABLE [dbo].[CatDistrito] CHECK CONSTRAINT [FK_CatDistrito_Region]
GO

ALTER TABLE [dbo].[CatDiscriminante] CHECK CONSTRAINT [FK_CatDiscriminante_Region]
GO

ALTER TABLE [dbo].[CatDiscriminante]  WITH NOCHECK ADD  CONSTRAINT [fk01_CatDiscriminante_CatDistrito] FOREIGN KEY([catDistrito_id])
REFERENCES [dbo].[CatDistrito] ([catDistrito_id])
GO

ALTER TABLE [dbo].[CatDiscriminante] CHECK CONSTRAINT [fk01_CatDiscriminante_CatDistrito]
GO




ALTER TABLE [dbo].[Region]
ADD [entidadFederativa_id] [decimal](18, 0) NULL;
GO

 
ALTER TABLE [dbo].[Region]  WITH CHECK ADD  CONSTRAINT [FK_Region_EntidadFederativa] FOREIGN KEY([entidadFederativa_id])
REFERENCES [dbo].[EntidadFederativa] ([entidadFederativa_id])
GO

ALTER TABLE [dbo].[Region] CHECK CONSTRAINT [FK_Region_EntidadFederativa]
GO


/*

Se agrega la relacion de Funcionario con Entidad Federativa y Region
 */

ALTER TABLE [dbo].[Funcionario]
ADD [entidadFederativa_id] [decimal](18, 0) NULL;
GO

ALTER TABLE [dbo].[Funcionario]
ADD [region_id] [numeric](18, 0) NULL;
GO


ALTER TABLE [dbo].[Funcionario]  WITH CHECK ADD  CONSTRAINT [FK_Funcionario_EntidadFederativa] FOREIGN KEY([entidadFederativa_id])
REFERENCES [dbo].[EntidadFederativa] ([EntidadFederativa_id])
GO

ALTER TABLE [dbo].[Funcionario] CHECK CONSTRAINT [FK_Funcionario_EntidadFederativa]
GO

ALTER TABLE [dbo].[Funcionario]  WITH CHECK ADD  CONSTRAINT [FK_Funcionario_Region] FOREIGN KEY([region_id])
REFERENCES [dbo].[Region] ([region_id])
GO

ALTER TABLE [dbo].[Funcionario] CHECK CONSTRAINT [FK_Funcionario_Region]
GO


INSERT INTO [dbo].[Region] VALUES ('1', 'RN', 'Region Norte',1);


update CatDiscriminante
set region_id = 1;


/**
Ejemplo de configuracion de NUC.

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



insert into [dbo].[CatDiscriminante] ( [bOpUIE], [cClaveDiscriminante], [catDistrito_id], [iTipo], [cNombre], [region_id])
values ( '0', '038', '4', '1', 'COORDINACION DE ADOLESCENTES', '1');

insert into [dbo].[CatUIEspecializada] ( [cAcronimo], [cClaveUIE], [cNombreUIE])
values ( 'UA', '43', 'UNIDAD DE ADOLESCENTES');

select * from CatUIEspecializada

insert into [dbo].[CatAreasNegocio] ( [ConfInstitucion_id], [bEsEspecializada], [cNombre])
values ( '1', '1', 'UNIDAD DE ADOLESCENTES');

select * from JerarquiaOrganizacional

insert into JerarquiaOrganizacional (JerarquiaOrganizacional_id, jerarquiaresponsable_id, cabreviatura, cnombre, tipojerarquia_val, Domicilio_id)
values (74, 1,'UIADO','Unidad de Investigación de Delitos Cometidos por Adolescentes',2015,null);	


insert into [Funcion] ( [cDescripcionFuncion], [cNombreFuncion] )
values ( '/consultarCatalogoEntidadFederativa.do', '/consultarCatalogoEntidadFederativa.do' );



/*select id_funcion from funcion
where cNombreFuncion like '%consultarCatalogoEntidadFederativa%';*/

/* Se agrega al Modulo 1*/

--1755
insert into [ModuloFuncion] ( [Modulo_id], [Funcion_id])  
select 1, funcion_id from funcion
where cNombreFuncion like '%consultarCatalogoEntidadFederativa%';



insert into [Funcion] ( [cDescripcionFuncion], [cNombreFuncion] )
values ( '/consultarCatalogoRegion.do', '/consultarCatalogoRegion.do' );

/*select * from funcion
where cNombreFuncion like '%consultarCatalogoRegion%';
*/
--1756

/* Se agrega al Modulo 1*/
insert into [ModuloFuncion] ( [Modulo_id], [Funcion_id]) 
select 1, funcion_id from funcion
where cNombreFuncion like '%consultarCatalogoRegion%';


/* Ejemplo de configuracion x agencia*/
insert into [Parametro] ( [cDescripcion], [cValor], [cTipoValor], [cClave])
values ( 'UNIDAD', '038,030', '1', 'NUC_AGENCIA');




/***
* se agrega lo correspondiente a la situacion Juridica del Detenido
*/

insert into [Funcion] ( [cDescripcionFuncion], [cNombreFuncion] )
values ( '/ConsultarCatalogoSituacionJuridicaDetenido.do', '/ConsultarCatalogoSituacionJuridicaDetenido.do' );


/* Se obtiene el funcion_id para usarlo en ModuloFuncio */

/* Se agrega al Modulo 2*/
insert into [ModuloFuncion] ( [Modulo_id], [Funcion_id]) 
select 2,Funcion_id  from funcion
where cNombreFuncion like '%ConsultarCatalogoSituacionJuridicaDetenido%';




/* Actualizacion de catalogo*/
insert into [Catalogo] ( [cDescripcion], [bEsSistema], [Catalogo_id], [bEsActivo], [cNombre])
values ( 'Situacion Juridica Detenido', '1', '214', '1', 'Situacion Juridica Detenido');



insert into [CampoCatalogo] (  [CampoCatalogo_id],[cNombreCampo], [bEsRecursivo], [Catalogo_id], [iTipoDato], [bEsLlave])
values ( 225,'Situacion Juridica Detenido', 0, '214', 0, 1);





insert into [valor] ( [cValor], [CampoCatalogo_id], [Registro_id], [Valor_id])
select 'Detenido', '225', '1', max(Valor_id)+1 from Valor;



insert into [valor] ( [cValor], [CampoCatalogo_id], [Registro_id], [Valor_id])
select 'Libertad', '225', '2', max(Valor_id)+1 from Valor;



insert into [valor] ( [cValor], [CampoCatalogo_id], [Registro_id], [Valor_id])
select 'Judicializado', '225', '3', max(Valor_id)+1 from Valor;




/**
* Se reemplazan los parametros de las plantillas  
*
*/


Update Forma Set cCuerpo = CAST(REPLACE(CAST(cCuerpo as NVarchar(MAX)),'COA/FG/XX/____/____/____','&lt;numeroCaso&gt;') AS NText);






/****** Object:  Table [dbo].[PermisoSolicitud]    Script Date: 25/08/2015 17:54:26 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[PermisoSolicitud](
	[Solicitud_id] [dbo].[campo llave] NOT NULL,
	[iClaveFuncionario] [dbo].[campo llave] NOT NULL,
	[dFechaLimite] [datetime] NOT NULL,
	[bEsEscritura] [dbo].[boleano] NOT NULL,
 CONSTRAINT [PK_PermisoSolicitud] PRIMARY KEY CLUSTERED 
(
	[Solicitud_id] ASC,
	[iClaveFuncionario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[PermisoSolicitud]  WITH CHECK ADD  CONSTRAINT [FK_PermisoSolicitud_Funcionario1] FOREIGN KEY([iClaveFuncionario])
REFERENCES [dbo].[Funcionario] ([iClaveFuncionario])
GO

ALTER TABLE [dbo].[PermisoSolicitud] CHECK CONSTRAINT [FK_PermisoSolicitud_Funcionario1]
GO

ALTER TABLE [dbo].[PermisoSolicitud]  WITH CHECK ADD  CONSTRAINT [FK_PermisoSolicitud_Solicitud1] FOREIGN KEY([Solicitud_id])
REFERENCES [dbo].[Solicitud] ([Solicitud_id])
GO

ALTER TABLE [dbo].[PermisoSolicitud] CHECK CONSTRAINT [FK_PermisoSolicitud_Solicitud1]
GO





/**
* Bienes asegurados
*/


--Nuevas ramas en árbol de menú

INSERT INTO ElementoMenu(cNombre,cComando,cIdHTML,cClassHTML,iPosicion,cForward,bEsObligatorio)
VALUES('Bienes Asegurados',
	'void(0)',
	'bienesAsegurados',
	'jstree-closed',
	0,
	'menu',
	0);

INSERT INTO ElementoMenu(ElementoMenuPadre_id,cNombre,cComando,cIdHTML,cClassHTML,iPosicion,cForward,bEsObligatorio)
	SELECT ElementoMenu_id,
	'Para enajenar hoy',
	'consultaPorEnajenarHoy()',
	'paraEnajenarHoy',
	'jstree-closed',
	0,
	'menu',
	0
	FROM ElementoMenu
	WHERE cNombre='Bienes Asegurados';

INSERT INTO ElementoMenu(ElementoMenuPadre_id,cNombre,cComando,cIdHTML,cClassHTML,iPosicion,cForward,bEsObligatorio)
	SELECT ElementoMenu_id,
	'Para enajenar por fecha',
	'consultaPorEnajenarFecha()',
	'paraEnajenarPorFecha',
	'jstree-closed',
	0,
	'menu',
	0
	FROM ElementoMenu
	WHERE cNombre='Bienes Asegurados';

Insert into RolElementoMenu(ElementoMenu_id,Rol_id)
	SELECT ElementoMenu_id,
	7
	FROM ElementoMenu
	WHERE cNombre='Bienes Asegurados';

Insert into RolElementoMenu(ElementoMenu_id,Rol_id)
	SELECT ElementoMenu_id,
	7
	FROM ElementoMenu
	WHERE cNombre='Para enajenar hoy';

Insert into RolElementoMenu(ElementoMenu_id,Rol_id)
	SELECT ElementoMenu_id,
	7
	FROM ElementoMenu
	WHERE cNombre='Para enajenar por fecha';

--CREACIÓN DE NUEVO CAMPO Boolean como bandera de enajenado

ALTER TABLE Objeto
ADD Enajenado boleano NULL DEFAULT 0;

UPDATE Objeto SET Enajenado=0;


--INSERCIÓN DE NUEVA FUNCIÓN Y ASOCIACIÓN CON MÓDULO 2

insert into Funcion ( cDescripcionFuncion, cNombreFuncion )
values ( '/consultarBienesPorEnajenar.do', '/consultarBienesPorEnajenar.do' );

insert into Funcion ( cDescripcionFuncion, cNombreFuncion )
values ( '/enajenarBienes.do', '/enajenarBienes.do' );

select * from Funcion where cNombreFuncion like '/enajenarBienes%';

insert into Funcion ( cDescripcionFuncion, cNombreFuncion )
values ( '/consultarOficioEnajenacion.do', '/consultarOficioEnajenacion.do' );

insert into ModuloFuncion ( Modulo_id, Funcion_id)
	SELECT 2, 
		Funcion_id
	from funcion
	where cNombreFuncion like '%consultarBienesPorEnajenar%';


insert into ModuloFuncion ( Modulo_id, Funcion_id)
	SELECT 2, 
		Funcion_id
	from funcion
	where cNombreFuncion like '%enajenarBienes%';	

insert into ModuloFuncion ( Modulo_id, Funcion_id)
	SELECT 2, 
		Funcion_id
	from funcion
	where cNombreFuncion like '%consultarOficioEnajenacion%';		

--PARAMETRO DIAS PARA ENAJENAR
INSERT INTO Parametro (cClave,cValor,cDescripcion)
values('DIAS_PARA_ENAJENAR','90','Días requeridos para enajenar un bien asegurado')

--CREACIÓN DE NUEVO CAMPO Id documento de enajenación

ALTER TABLE Objeto ADD docEnajenacion_id decimal(18, 0)  NULL;
ALTER TABLE Objeto ADD CONSTRAINT fk07_obje_documento FOREIGN KEY (docEnajenacion_id) REFERENCES Documento (Documento_id);

--CREACIÓN NUEVA FORMA PARA ACTA ENAJENACIÓN
INSERT INTO Forma (TipoForma_val,cNombre,cDescForma,cCuerpo)
SELECT Valor_id,
       'Oficio enajenación',
       'Oficio enajenación',
       '<p style="text-align: right;"><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">&lt;ciudad&gt;, &lt;estado&gt; &lt;fechaActual&gt;</span></span></p>
	   <p style="text-align: right;"><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">OFICIO DE ENAJENACION DE BIENES</span></span></p>
	   <p style="text-align: right;">&nbsp;</p>
        <p>
		<span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">El d&iacute;a de hoy, &lt;fechaActual&gt; siendo las &lt;horaActual&gt; horas se enajenaron los siguientes bienes:</span></span></p>
	   <p>
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	&nbsp;</p>'
FROM VALOR
WHERE cValor='Oficio' and CampoCatalogo_id=68;

--Para determinar el valor de forma_id en el metodo enajenarBienes de la clase EnajenarBienesAction, Long formaId = xyzL;
select Valor_id
from Valor
WHERE cValor='Oficio' and CampoCatalogo_id=68;

select Forma_Id from forma
where TipoForma_val = 1611 and cDescForma like 'Oficio enaje%';




/**
*Actuaciones por Unidad de investigacion.
*/

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ConfActividadUIE](
	[catUIE_id] [decimal](18, 0) NOT NULL,
	[TipoActividad_val] [decimal](18, 0) NOT NULL,
	[bActivo] [dbo].[boleano] NULL,
PRIMARY KEY CLUSTERED 
(
	[catUIE_id] ASC,
	[TipoActividad_val] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[ConfActividadUIE]  WITH CHECK ADD  CONSTRAINT [fk_ConfActvidadUIE_Act] FOREIGN KEY([TipoActividad_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO

ALTER TABLE [dbo].[ConfActividadUIE] CHECK CONSTRAINT [fk_ConfActvidadUIE_Act]
GO

ALTER TABLE [dbo].[ConfActividadUIE]  WITH CHECK ADD  CONSTRAINT [fk_ConfActvidadUIE_CatUIEspecializada] FOREIGN KEY([catUIE_id])
REFERENCES [dbo].[CatUIEspecializada] ([catUIE_id])
GO

ALTER TABLE [dbo].[ConfActividadUIE] CHECK CONSTRAINT [fk_ConfActvidadUIE_CatUIEspecializada]
GO



/**
* Generando Objeto de conclusion del Hecho
*/

CREATE TABLE [dbo].[ConclusionHecho](
	[Hecho_id] [decimal](18, 0) NOT NULL,
	[dFechaConclusion] [date] NULL,
	[TipoConclusion_val] [decimal](18, 0) NULL,
	[TipoSubConclusion_val] [decimal](18, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[Hecho_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[ConclusionHecho]  WITH CHECK ADD FOREIGN KEY([Hecho_id])
REFERENCES [dbo].[Hecho] ([Hecho_id])
GO

ALTER TABLE [dbo].[ConclusionHecho]  WITH CHECK ADD FOREIGN KEY([TipoConclusion_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO

ALTER TABLE [dbo].[ConclusionHecho]  WITH CHECK ADD FOREIGN KEY([TipoSubConclusion_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO



