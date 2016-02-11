<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.CategoriasActividad"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.funcionario.TipoDefensoria"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Visor Defensor&iacute;a</title>

	<!--HOJAS DE ESTILO-->
	<!--	Hoja de estilo para los gadgets-->
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
	<!--    Hoja de estilo para easyaccordion-->
	<link rel="stylesheet" type="text/css" media="screen"
		href="<%=request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
	
	<!--Hoja de estilos para el grid-->
	<link rel="stylesheet" type="text/css" media="screen"
		href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<!--Hoja de estilo para el texto dentro de los acordeones-->
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/resources/css/estilos.css"
		media="screen" />
	<link rel="stylesheet" type="text/css" media="screen"
		href="<%=request.getContextPath()%>/resources/css/layout_complex.css" />
	<!--Hoja de estilo para los popups-->
	<link type="text/css" rel="stylesheet"
		href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />
	
	<!--SCRIPTS-->
	<!--Scripts necesarios para el funcionamiento de la JSP-->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<!--Scrip para el idioma del calendario-->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	<!--Scripts necesarios para la ejecucion del editor-->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
	<!--scripts del gird-->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	<!--script de jquery UI-->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>

	<!--ESTILOS PARA LAS TABS BUTTON, POR ESTAR EN LA PARTE DE ABAJO-->
	<style>
	#tabs { height: 670px; } 
	.tabs-bottom { position: relative; } 
	.tabs-bottom .ui-tabs-panel { height: 500px; overflow: auto; } 
	.tabs-bottom .ui-tabs-nav { position: absolute !important; left: 0; bottom: 0; right:0; padding: 0 0.2em 0.2em 0; } 
	.tabs-bottom .ui-tabs-nav li { margin-top: -2px !important; margin-bottom: 1px !important; border-top: none; border-bottom-width: 1px; }
	.ui-tabs-selected { margin-top: -3px !important; }
	</style>

	<script type="text/javascript">
		
	//Recuperando parametros globales
	var CONTEXT_ROOT = '<%= request.getContextPath()%>';
	var contextoPagina = "${pageContext.request.contextPath}";
	var solicitudId = '<%= request.getParameter("solicitudDefensorId")%>';
	var idInstitucion = '<%= request.getParameter("idInstitucion")%>';
	var estatusSolicitud = '<%= request.getParameter("estatusSolicitud")%>';
	var idWindow = '<%= request.getParameter("idWindow")%>';

	//VARIABLES PARA REASIGNAR DEFENSOR
	var reasignarDefensor = <%=request.getParameter("reasignarDefensor")%>;
	var numeroExpedienteId = '<%= request.getParameter("numeroExpedienteId")%>';
	var idDefensorActual = '<%= request.getParameter("idDefensorActual")%>';

	//VARIABLES PARA CONSULTA DE USUARIOS
	var consultaSoloResumen = <%=request.getParameter("consultaSoloResumen")%>;
	
	//VARIABLES PARA LA CEJA DOCUMENTO
	var idWindowDocumento = 1;
	var expedienteId = "";
	var numeroExpediente = "";
	
	//PARAMETRO PARA IDENTIFICAR EL GRID DE ORIGEN
	var esConsultaSolicitud = <%=request.getParameter("esConsultaSolicitud")%>;
	
	$(document).ready(function() {
		
		jQuery(document).ajaxStop(jQuery.unblockUI);		
 		//Se crean las tabs principales
		$("#tabsPrincipalesVisorDefensoria" ).tabs();
		//Se crean los tabs childs de RESUMEN DE LA SOLICITUD
		$("#tabsChildResumenSolicitud" ).tabs();

		if(reasignarDefensor == true){
			configurarVisorParaReasignacionDefensor();
		}
		else{
			consultarDetalleSolicitud(solicitudId);
		} 
	});


	
	//**************************************** FUNCIONES COMUNES A TODAS LAS CEJAS ***********************************************//
	
	/*
	*Funcion para consultar el detalle de la solicitud
	*/
	function consultarDetalleSolicitud(solicitudId){

		var parametros = "";

		parametros += '&idDocumento=' + solicitudId;
		
		ejecutaAction("/consultarDetalleSolicitud", function(xmlRespuesta){					
			configurarCejasDelVisor(xmlRespuesta);
			cargaGridInvolucradoSolicitudDefensor(solicitudId);
		} , parametros);
	}

	/*
	*Funcion para configurar las cejas del visor
	*/
	function configurarCejasDelVisor(xmlRespuesta){
		if(consultaSoloResumen == true){
			configurarCejaResumen(xmlRespuesta);
			//Oculta las demas cejas
			$('#liAsignarDefensor').hide();
			$('#liDocumentos').hide();
			
		}else{
			configurarCejaResumen(xmlRespuesta);
			configurarCejaDocumentos(xmlRespuesta);
			configurarCejaAsignarDefensor(xmlRespuesta);
		}		
	}

	/*
	*Funcion que configura las cejas para la reasignacion de defensor
	*/
	function configurarVisorParaReasignacionDefensor(){
		//Selecciona la tab de asignar defensor
		$('#tabsPrincipalesVisorDefensoria').tabs('select', '#tabsVisorDefensoria-2');
		//Oculta las otras cejas del visor
		$("#liResumen").hide();
		$("#liResumenIntervinientes").hide();		
		$("#liDocumentos").hide();
		//Carga el grid de defensores
		cargarGridDefensores();
		//Botones
		$("#asignarDefensor").hide();
		$("#reasignarDefensor").show();
	}

	
	//****************************************FUNCIONES PARA LA CEJA DE DOCUMENTOS***********************************************/
	
	/*
	*Funcion para configurar la ceja de documentos
	*/
	function configurarCejaDocumentos(xmlRespuesta){
		
		//Solo debemos mostrar el boton de acuse de recibo si el estatus es abierto o en proceso
		//y si y solo si tiene un aviso de designacion, con un funcionario asignado 
		if(existe($(xmlRespuesta).find('solicitud').find('estatus').find('idCampo').first())){
			
			var estatusSolicitud = $(xmlRespuesta).find('solicitud').find('estatus').find('idCampo').first().text();
			
			if((estatusSolicitud == '<%=EstatusSolicitud.ABIERTA.getValorId()%>') || (estatusSolicitud == '<%=EstatusSolicitud.EN_PROCESO.getValorId()%>')){
				if(existe($(xmlRespuesta).find('solicitud').find('avisoDesignacion').find('funcionario').find('claveFuncionario'))){
					$("#btnGenerarAcuseDeRecibo").show();
				}
			}
		}
	}

	//Variable para controlar la carga del grid de documentos
	var reloadGridDocumentos = false; 
	
	/*
	*Funcion para consultar los documentos asociados al expediente
	*/
	function documentos(){
		//alert("visorDetalleATDEF.jsp-documentos");
		var liga  = '<%=request.getContextPath()%>';
		liga += '/consultarDocumentosDefensoria.do?tipo=1&expedienteId=';
		liga += expedienteId;

		if(reloadGridDocumentos){
			$("#tblGridDocumentos").trigger("reloadGrid"); 
		}else{
			jQuery("#tblGridDocumentos").jqGrid({ 
				url:liga, 
				datatype: "xml", 
				colNames:['Tipo documento','Fecha actividad','Nombre documento','Nombre Actividad','Fecha creaci&oacute;n','&Aacute;rea','Documentos','Documento Parcial'],
				colModel:[ 	{name:'tipo',			index:'tipo',			width:155,	align:'center',	sortable:false}, 
			    	       	{name:'fechaActividad',	index:'fechaActividad',	width:90,	align:'center', sortable:false,	hidden:true	},
							{name:'nombre',			index:'nombre',			width:300,	align:"center", sortable:false},
							{name:'nombreActividad',index:'nombreActividad',width:400,	align:"center", sortable:false},
							{name:'fechaCreacion',	index:'fechaCreacion',	width:170,	align:"center", sortable:false},							
							{name:'area',			index:'area',			width:300,	align:"center", sortable:false},
							{name:'Documento',		index:'documento', 		width:170, 	align:"center", sortable:false},
				           	{name:'EsParcial',		index:'esParcial', 		hidden:true}
					 	],
				pager: jQuery('#divPagerGridDocumentos'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: false,
				width:900,
				sortname: 'turno',
				caption:"Documentos del expediente",
				viewrecords: true,
				id: 'divgrid',
				loadComplete: function(){
					reloadGridDocumentos = true;
				},
				ondblClickRow: function(id){
					
				var retd = jQuery("#tblGridDocumentos").jqGrid('getRowData',id);

					if(retd.EsParcial){
						noEsParcial = retd.EsParcial.indexOf('false');
						if(noEsParcial > 0){ //Es definitivo
							consultaPDF(id);
						}
						else{//Es parcial
							customAlert("El documento no puede ser mostrado, por que aun esta siendo editado.","","");
						}
					 }
				},
				sortorder: "desc"
			});
		}
	}

	
	/*
	*Funcion para consultar el documento digital 
	*/
	function consultaPDF(documentoId){
		document.formaArchivoD.documentoId.value = documentoId;
		document.formaArchivoD.submit();
	}


	//Variable para controlar el id de la ventana
	var idWindowPantallaActuaciones = 1;
	
	/*
	*Funcion que se debe invocar para generar el documento de acuse de recibo
	*y que obtiene los parametros necesarios para abrir el editor de texto
	*/
	function generarAcuseDeRecibo(){
		//Incrementar para generar un nuevo Id
		idWindowPantallaActuaciones++;
		
		var confActividadDocumentoId = cargaActuaciones();
		var parametros = obtenerParametrosActividadDocumento(confActividadDocumentoId);
		
		parametros += '&solicitudDefensorId=' + solicitudId;
		parametros += '&esDocumentoDeAcuseReciboSolDefensor=' + true;
		parametros += '&ocultarGuardadoParcial=' + true;
		parametros += '&ocultarNumeroOficio=' + true;
		parametros += '&esconderArbol=' + true;
		parametros += '&idNumeroExpediente=' + numeroExpedienteId;
		parametros += '&idWindowPantallaActuaciones=' + "iframewindowGenerarDocumento"+idWindowPantallaActuaciones;
				
		//de los parametros obtenemos el titulo del documento
		var titulo = (parametros.split('&')[4]).split('=')[1];

		$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:true});
		$.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?parametros='+parametros+'" width="1140" height="400" />');
	
	}

	/*
	*Funcion para cerrar la ventana de enviar acuse de recibo de solicitud de defensor
	*/
	function cerrarPantallaActuaciones(idWindowPantallaCerrar){
		$('#btnGenerarAcuseDeRecibo').hide();
		$.closeWindow(idWindowPantallaCerrar);
		if(esConsultaSolicitud != undefined && esConsultaSolicitud != null && esConsultaSolicitud == true){
			try{
				window.parent.cargaGridSolicitudesDefensor(idInstitucion,estatusSolicitud);
			}catch(e){}
		}
		else{
			try{
				window.parent.gridRecibirDesignaciones();
			}catch(e){}
		}
		
		
	}


	/**
	 *Funcion para obtener los siguientes parametros de la tabla confActividadDocumento:
	 *
	 *actividadId
	 *formaId
	 *tipoDocumento
	 *nombreDocumento
	 *usaEditor
	 *estatusId (No importa, ya que no cambiara el estatus del expediente)
	 *nombreActividad
	 *
	 *Nota se requiere el parametro:numeroUnicoExpediente, que corresponde al numeroExpediente (String), como
	 *obligatorio, ya que se estara generando una actividad
	 */
	function obtenerParametrosActividadDocumento(confActividadDocumentoId){

		//respuesta de la funcion
		var respuesta="";
		
		var actividad=0;
		var formaID=0;
		var titulo="";
		var usaeditor="";
		var estatusId="";
		var nombreActividad="";

		//Parametros para realizar la consulta
		var parametros = "";
		parametros = '&idConf=' + confActividadDocumentoId;

		
		ejecutaAction("/obtenerConfActividadDocumento", function(xml){
			
			actividad=$(xml).find('confActividadDocumentoDTO').find('tipoActividadId').text();
			formaID=$(xml).find('confActividadDocumentoDTO').find('formaId').text();
			titulo=$(xml).find('confActividadDocumentoDTO').find('nombreDocumento').text();
			usaeditor=$(xml).find('confActividadDocumentoDTO').find('usaEditor').text();
			estatusId=$(xml).find('confActividadDocumentoDTO').find('estadoCambioExpediente').find('idCampo').text();
			nombreActividad=$(xml).find('confActividadDocumentoDTO').find('nombreActividad').text();
			
		} , parametros);

		respuesta = '&actividadId=' + actividad; 
		respuesta += '&numeroUnicoExpediente='+numeroExpediente;
		respuesta += '&formaId=' + formaID;
		respuesta += '&titulo=' + titulo;
		respuesta += '&nombreActividad=' + nombreActividad;
	
		return respuesta;
	}


	/*
	*Funcion para consultar el parametro de confacttividad documento
	*para la solicitud de defensor
	*/
	function cargaActuaciones() {

		var confActividadDocId; 
		var parametros = "";
		
		parametros += 'categoriaActividadId=' + <%= CategoriasActividad.DEFENSORIA_PENAL.getValorId()%>;
		parametros += '&muestraCombo=' + true;
		
		ejecutaAction("/cargarActuacionesPorFiltro", function(xml){
			confActividadDocId = $(xml).find('catActuaciones').first().find('clave').text();
		} , parametros);

		return confActividadDocId;
	}
	//****************************************FUNCIONES PARA LA CEJA DE ASIGNAR DEFENSOR***********************************************/

	/*
	*Funcion para configurar las ceja de asignar defensor
	*/
	function configurarCejaAsignarDefensor(xmlRespuesta){

		if(existe($(xmlRespuesta).find('solicitud').find('avisoDesignacion').find('documentoId').text())){
			$("#liAsignarDefensor").hide();
		}
	}

	//Funcion para controlar la carga del grid de defensores
	var reloadGridDefensor = false;
	
	 /*
	 *Funcion para cargar el grid de defensores por distrito
	 */
	//TODO GBP Similar al de registrarInteresadoAsesoriaLegal
	function cargarGridDefensores(){
		
		if (reloadGridDefensor) {
			$("#tblGridAsignarDefensor").trigger("reloadGrid"); 
		}
		else{
			jQuery("#tblGridAsignarDefensor").jqGrid({ 
				url:'<%=request.getContextPath()%>/SolicitudesDefensorGrid.do', 
				datatype: "xml", 
				colNames:['Nombre'], 
				colModel:[ 
						   {name:'Nombre',index:'nombre', width:550, sortable:false, align:'center'}		           	
						],
				pager: jQuery('#divPagerGridAsignarDefensor'),
				rowNum:10,
				rowList:[10,20,30],
				height:230,
				viewrecords: true,
				sortorder: "desc",
				loadComplete: function(xml){
					var numRegistros = $(xml).find('rows').find('records').text();
					//var arrayIDs=jQuery("#tblGridAsignarDefensor").getDataIDs();
					if(parseInt(numRegistros) <= 0 ){
						customAlert("No existen defensores registrados en el distrito.");
					}
					reloadGridDefensor = true;
					limpiaDatosDefensor();
				},
				onSelectRow: function(rowID) {
						tablaDefensor(rowID);
				}
			});	
		}
	}

	/*
	* Funcion que consulta los datos del defensor
	*/
	//TODO GBP Similar al de registrarInteresadoAsesoriaLegal
	function tablaDefensor(idDefensor){

		var param = '';
		param += 'idDefensor='+ idDefensor;
		
		ejecutaAction("/SolicitudesDefensor", function(xml){
			pintaMedios(xml);
		} , param);		
		
	}
	
	/*
	* Funcion que muestra los datos (correo-e,num. tel., especialidad) del defensor 
	* seleccionado en el grid
	*/
	//TODO GBP Similar al de registrarInteresadoAsesoriaLegal
	function pintaMedios(xml){
		var esPrimero =true;
		
		//Limpia la tabla con los datos anteriores
		limpiaDatosDefensor();
		
		//Parte del XML para mostrar la especialidad del defensor
		$("#campoEspecialidadDefensor").val($(xml).find('especialidad').find('valor').first().text());
		
		//Parte del XML para mostrar los telefonos	
		$(xml).find('mediosContacto').find('TelefonoDTO').each(function(){
			var codigoPais = $(this).find('codigoPais').text();
			var codigoArea = $(this).find('codigoArea').text();
			var numeroTelefonico = $(this).find('numeroTelefonico').first().text();
			
			if(esPrimero){
				$("#numeroTelefono").append(codigoPais+"-"+codigoArea+"-"+numeroTelefonico);
				esPrimero = false;
			}else{
				$("#numeroTelefono").append(", "+codigoPais+"-"+codigoArea+"-"+numeroTelefonico);
			}
		});		

		esPrimero = true;
		//Parte del XML para mostrar el correo electronico del defensor
		$(xml).find('mediosContacto').find('CorreoElectronicoDTO').each(function(){
			var correoElectronico = $(this).find('direccionElectronica').first().text();

			if(esPrimero){
				$("#correo").append(correoElectronico);
				esPrimero = false;
			}else{
				$("#correo").append(", " + correoElectronico);
			}
		});			
	}
	
	/*
	* Funcion para limpiar la tabla con los datos del defensor
	*/
	function limpiaDatosDefensor(){
		
		$("#numeroTelefono").empty();
		$("#correo").empty();
		$("#campoEspecialidadDefensor").val("");
	}

	/*
	*Funcion para mostrar el telefono del defesor
	*/
	function tel(codigoPais,codigoArea,numeroTelefonico){
		this.codigoPais = codigoPais;
		this.codigoArea = codigoArea;
		this.numeroTelefonico = numeroTelefonico;
	}

	
	/*
	* Funcion para asignar un expediente a un defensor
	*/
	function designarAbogadoDefensorCoordinador(){

		var idDefensor = jQuery("#tblGridAsignarDefensor").jqGrid('getGridParam','selrow');
		
		if(idDefensor === undefined || idDefensor == null){
			customAlert("Debe seleccionar un defensor");
			return;
		}
		
		var param = '';
		
		param += 'numSolicitud='+ '<%= request.getParameter("solicitudDefensorId")%>';
		param += '&idDefensor='+ idDefensor;
		param += '&esAsesoria=false';
		
		ejecutaAction("/DesignarAbogadoDefensorExpediente", function(respuesta){
			var errorCode = $(respuesta).find('response').find('code').text();
			//Si errorCode=0 entonces continuamos con el flujo
			if(parseInt(errorCode)==0){
				customConfirm("Se asign&oacute; de forma correcta al defensor","",function(){
					//Aceptar
					//Recargar el gird de solicitudes 
					window.parent.cargaGridSolicitudesDefensor(idInstitucion,estatusSolicitud);
					//Cerrar la ventana
					window.parent.closeVentana(idWindow);
				},"");
			}else{
				customAlert("Ocurri&oacute; un error al asignar el defensor");
			}
		} , param);		
	} 

	
	/*
	*Funcion para reasignar un abogado defensor
	*/
	function reasignarAbogadoDefensor(){
		
		var idDefensor = jQuery("#tblGridAsignarDefensor").jqGrid('getGridParam','selrow');
		
		if(idDefensor === undefined || idDefensor == null || idDefensor == 'undefined'){
			customAlert("Debe seleccionar un defensor");
			return;
		}
		
		var param = '';
		param += 'numeroExpedienteId='+ numeroExpedienteId;
		param += '&idDefensor='+ idDefensor;
				
		ejecutaAction("/reasignarAbogadoDefensorExpediente", function(respuesta){
								
			if($(respuesta).find('body').find('respuesta').text()== "exito"){
				//Recargamos el gird de expedientes
				window.parent.gridExpedientesDeEtapas(idDefensorActual);
				customAlert("Se reasign&oacute; de forma correcta al defensor","Reasignaci&oacute;n de defensor", function(){
					//Cerramos la ventana
					window.parent.closeVentana(idWindow);
				});
				
			}else{
				var codigo = "";
				codigo = $(respuesta).find('code').text();
				
				if(codigo == '<%=CodigoError.NUM_EXP_CON_SOLICITUD_ACTIVA_DE_CARPETA_DE_INVESTIGACION.toString()%>'){
					customAlert("No se ha podido reasignar el expediente, ya que cuenta con una solicitud de <br> carpeta de investigaci&oacute;n,abierta o en proceso","Reasignaci&oacute;n de defensor");
				}
				else if(codigo == '<%=CodigoError.MISMO_FUNCIONARIO.toString()%>'){
					customAlert("No se ha podido reasignar el expediente, ya que el expediente pertenece <br> al funcionario seleccionado","Reasignaci&oacute;n de defensor");
				}
				else{
					customAlert("Ocurri&oacute; un error al reasignar el defensor,<br> por favor contacte a su administrador","Reasignaci&oacute;n de defensor");
				}
			}
		} , param);		
		
	}

	function solicitarAbogadoDefensorExterno(){
		var forma=<%=Formas.SOLICITUD_DEFENSOR_EXTERNO.getValorId()%>;
		var params = "?esconderArbol=0&formaId="+forma;
			params+= "&numeroUnicoExpediente=<%=request.getParameter("numeroExpedienteSt")%>";
		$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Solicitar abogado externo", type:"iframe", confirmarCierreVentana:true});
	    $.updateWindowContent("iframewindowGenerarDocumento","<iframe src='<%=request.getContextPath()%>/generarDocumentoSinCaso.do"+params+"' width='1140' height='400' />");
	}
	
	</script>
</head>

<body>
	<!--Comienza div principal para las tabs-->
	<div id="tabsPrincipalesVisorDefensoria">
		<ul>
			<li id="liResumen">
				<a href="#tabsVisorDefensoria-1">Resumen de Solicitud</a>
			</li>
			<li id="liAsignarDefensor">
				<a href="#tabsVisorDefensoria-2" onclick="cargarGridDefensores()">Asignar Defensor</a>
			</li>
			<li id="liDocumentos">
				<a href="#tabsVisorDefensoria-3" onclick="documentos()">Documentos</a>
			</li>
		</ul>
		
		
		<jsp:include page="defensoria/visorResumenSolicitudDefensoria.jsp"></jsp:include>
			
		
		<!--Comienza tab 2-->
		<div id="tabsVisorDefensoria-2" align="left">
			<table width="100%" border="0">
				<tr height="80%">
					<td width="50%" align="center" valign="top">
						<table id="tblGridAsignarDefensor" align="center"></table>
						<div id="divPagerGridAsignarDefensor"></div> 
					</td>
					<td valign="top">
						<table width="100%" border="0">
							<tr>
								<td colspan="2" align="center" class="ui-button ui-corner-all ui-widget">Informaci&oacute;n del Defensor</td>
							</tr>
							<tr>
								<td width="35%">Correo Electr&oacute;nico:</td>
								<td id="correo"></td>
							</tr>
							<tr>
								<td>Numero de Tel&eacute;fono:</td>
								<td id="numeroTelefono"></td>
							</tr>
							<tr>
								<td>Especialidad(es):</td>
								<td>
									<input type="text" id="campoEspecialidadDefensor" class="transpa"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="50%" align="center">
						<input type="button" id="asignarDefensor" value="Seleccionar abogado para defensa" onClick="designarAbogadoDefensorCoordinador()" class="ui-button ui-corner-all ui-widget"/>
						<input type="button" id="reasignarDefensor" value="Seleccionar abogado a reasignar" onClick="reasignarAbogadoDefensor()" class="ui-button ui-corner-all ui-widget" style="display:none"/>
					</td>
					<td>
					</td>
				</tr>
			</table>
		</div>
		<!--Termina tab 2-->
		
		
		
		<!--Comienza tab 3-->
		<div id="tabsVisorDefensoria-3" align="left">
			
			<table width="100%">
				<tr>
					<td>
						<table id="tblGridDocumentos"></table>
						<div id="divPagerGridDocumentos"></div>
					</td>
				</tr>
				<tr>
					<td align="left">
						<input type="button" id="btnGenerarAcuseDeRecibo" style="display:none;" value="Generar Acuse de Recibo" class="ui-button ui-corner-all ui-widget" onclick="generarAcuseDeRecibo()">
					</td>
				</tr>
			</table>
			
			<form name="formaArchivoD"
				action="<%=request.getContextPath()%>/ConsultarContenidoArchivoDigital.do"
				method="post">
				<input type="hidden" name="documentoId" />
			</form>
		</div>
		<!--Termina tab 3-->
		
	</div>
	<!--Termina div principal para las tabs-->
</body>
</html>
