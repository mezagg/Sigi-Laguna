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
WHERE cValor='Oficio' and CampoCatalogo_id=68