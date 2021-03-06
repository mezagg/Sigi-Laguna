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
DELETE Forma where cNombre like '%Oficio enajenación%'

INSERT INTO Forma (TipoForma_val,cNombre,cDescForma,cCuerpo)
SELECT Valor_id,
       'Oficio enajenación',
       'Oficio enajenación',
       '	<p style="text-align: right;"><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">&lt;ciudad&gt;, &lt;estado&gt; &lt;fechaActual&gt;</span></span></p>
	<p><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">OFICIO DE ENAJENACI&Oacute;N DE BIENES</span></span></p>
	<p>&nbsp;</p>
    <p>
		<span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">El d&iacute;a de hoy, &lt;fechaActual&gt; siendo las &lt;horaActual&gt; horas se enajenaron los siguientes bienes:</span></span></p>
		<br />
	<p><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">VEH&Iacute;CULOS</span></p>	
	<table style="border: 1px solid black; width:100%">
		<tr>
			<th>Descripci&oacute;n</th>
			<th>Ubicaci&oacute;n</th> 
			<th>Fecha de notificaci&oacute;n</th>
			<th>D&iacute;as transcurridos</th>
			<th>No. Oficio</th>
		</tr>
		&lt;Vehiculos&gt;
	</table>
	<br />
	<br />
	<p><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">SEMOVIENTES</span></p>	
	<table style="border: 1px solid black; width:100%">
		<tr>
			<th>Descripci&oacute;n</th>
			<th>Ubicaci&oacute;n</th> 
			<th>Fecha de notificaci&oacute;n</th>
			<th>D&iacute;as transcurridos</th>
			<th>No. Oficio</th>
			<th>Color</th>
			<th>Cantidad</th>						
		</tr>
		&lt;Semovientes&gt;
	</table>
	<br />
	<br />
	<p><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">BIENES MUEBLES</span></p>	
	<table style="border: 1px solid black; width:100%">
		<tr>
			<th>Descripci&oacute;n</th>
			<th>Ubicaci&oacute;n</th> 
			<th>Fecha de notificaci&oacute;n</th>
			<th>D&iacute;as transcurridos</th>
			<th>No. Oficio</th>
			<th>Cantidad</th>						
		</tr>
		&lt;BienesMuebles&gt;
	</table>
	<br />
	<br />
	<p><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">VALORES</span></p>	
	<br />
	<p><span style="font-size:11px;"><span style="font-family: arial,helvetica,sans-serif;">Dinero</span></p>	
	<table style="border: 1px solid black; width:100%">
		<tr>
			<th>Descripci&oacute;n</th>
			<th>Ubicaci&oacute;n</th> 
			<th>Fecha de notificaci&oacute;n</th>
			<th>D&iacute;as transcurridos</th>
			<th>No. Oficio</th>
			<th>Procedencia</th>
		</tr>
		&lt;Dinero&gt;
	</table>
	<br />
	<p><span style="font-size:11px;"><span style="font-family: arial,helvetica,sans-serif;">Joyas</span></p>	
	<table style="border: 1px solid black; width:100%">
		<tr>
			<th>Descripci&oacute;n</th>
			<th>Ubicaci&oacute;n</th> 
			<th>Fecha de notificaci&oacute;n</th>
			<th>D&iacute;as transcurridos</th>
			<th>No. Oficio</th>
			<th>Kilataje</th>
			<th>Eval&uacute;o Moneatario</th>						
		</tr>
		&lt;Joyas&gt;'
FROM VALOR
WHERE cValor='Oficio' and CampoCatalogo_id=68;

--Para determinar el valor de forma_id en el metodo enajenarBienes de la clase EnajenarBienesAction, Long formaId = xyzL;
select Valor_id
from Valor
WHERE cValor='Oficio' and CampoCatalogo_id=68;

select Forma_Id from forma
where TipoForma_val = 1611 and cDescForma like 'Oficio enaje%';

--Actualización de la forma para oficio enajenación
UPDATE Forma 
SET cCuerpo='<p style="text-align: right;"><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">&lt;ciudad&gt;, &lt;estado&gt; &lt;fechaActual&gt;</span></span></p>
	<p><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">OFICIO DE ENAJENACI&Oacute;N DE BIENES</span></span></p>
	<p>&nbsp;</p>
    <p>
		<span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">El d&iacute;a de hoy, &lt;fechaActual&gt; siendo las &lt;horaActual&gt; horas se enajenaron los siguientes bienes:</span></span></p>
		<br />
	<p><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">VEH&Iacute;CULOS</span></p>	
	<table style="border: 1px solid black; width:100%">
		<tr>
			<th>Descripci&oacute;n</th>
			<th>Ubicaci&oacute;n</th> 
			<th>Fecha de notificaci&oacute;n</th>
			<th>D&iacute;as transcurridos</th>
			<th>No. Oficio</th>
		</tr>
		&lt;Vehiculos&gt;
	</table>
	<br />
	<br />
	<p><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">SEMOVIENTES</span></p>	
	<table style="border: 1px solid black; width:100%">
		<tr>
			<th>Descripci&oacute;n</th>
			<th>Ubicaci&oacute;n</th> 
			<th>Fecha de notificaci&oacute;n</th>
			<th>D&iacute;as transcurridos</th>
			<th>No. Oficio</th>
			<th>Color</th>
			<th>Cantidad</th>						
		</tr>
		&lt;Semovientes&gt;
	</table>
	<br />
	<br />
	<p><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">BIENES MUEBLES</span></p>	
	<table style="border: 1px solid black; width:100%">
		<tr>
			<th>Descripci&oacute;n</th>
			<th>Ubicaci&oacute;n</th> 
			<th>Fecha de notificaci&oacute;n</th>
			<th>D&iacute;as transcurridos</th>
			<th>No. Oficio</th>
			<th>Cantidad</th>						
		</tr>
		&lt;BienesMuebles&gt;
	</table>
	<br />
	<br />
	<p><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">VALORES</span></p>	
	<br />
	<p><span style="font-size:11px;"><span style="font-family: arial,helvetica,sans-serif;">Dinero</span></p>	
	<table style="border: 1px solid black; width:100%">
		<tr>
			<th>Descripci&oacute;n</th>
			<th>Ubicaci&oacute;n</th> 
			<th>Fecha de notificaci&oacute;n</th>
			<th>D&iacute;as transcurridos</th>
			<th>No. Oficio</th>
			<th>Procedencia</th>
		</tr>
		&lt;Dinero&gt;
	</table>
	<br />
	<p><span style="font-size:11px;"><span style="font-family: arial,helvetica,sans-serif;">Joyas</span></p>	
	<table style="border: 1px solid black; width:100%">
		<tr>
			<th>Descripci&oacute;n</th>
			<th>Ubicaci&oacute;n</th> 
			<th>Fecha de notificaci&oacute;n</th>
			<th>D&iacute;as transcurridos</th>
			<th>No. Oficio</th>
			<th>Kilataje</th>
			<th>Aval&uacute;o Monetario</th>						
		</tr>
		&lt;Joyas&gt;
	</table>
	<br />
	<p><span style="font-size:11px;"><span style="font-family: arial,helvetica,sans-serif;">Obras de Arte</span></p>	
	<table style="border: 1px solid black; width:100%">
		<tr>
			<th>Descripci&oacute;n</th>
			<th>Ubicaci&oacute;n</th> 
			<th>Fecha de notificaci&oacute;n</th>
			<th>D&iacute;as transcurridos</th>
			<th>No. Oficio</th>
			<th>Aval&uacute;o Monetario</th>						
		</tr>
		&lt;Obras de Arte&gt;
	</table>
	<br />
	<br />	
	<p><span style="font-size:12px;"><span style="font-family: arial,helvetica,sans-serif;">OTROS</span></p>	
	<table style="border: 1px solid black; width:100%">
		<tr>
			<th>Descripci&oacute;n</th>
			<th>Ubicaci&oacute;n</th> 
			<th>Fecha de notificaci&oacute;n</th>
			<th>D&iacute;as transcurridos</th>
			<th>No. Oficio</th>
		</tr>
		&lt;Otros&gt;
	</table>'
where TipoForma_val = 1611 and cDescForma like 'Oficio enaje%';