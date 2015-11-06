<%@page import="mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.CategoriasActividad"%>
	<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades"%>
	
	<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
	<title>Solicitar Defensor</title>
	
	
		<style type="text/css">
			DD P {
				LINE-HEIGHT: 120%
			}
			#iSolicitantePane {
				PADDING-BOTTOM: 0px;
				PADDING-LEFT: 6px;
				
				/* Taminio del frame de cada acordeon */
				WIDTH: 950px; 	
				PADDING-RIGHT: 0px;
				HEIGHT: 392px;//462px;
				PADDING-TOP: 20px;
				background-image: url(<%=request.getContextPath()%>/resources/images/back_datos_gral.png);
				background-repeat: no-repeat;
				border: 0px solid #000;
			}
			#iSolicitantePane DL {
				WIDTH: 1000px; HEIGHT: 400px;
			}
			
			/*acordeon editar*/
			#iSolicitantePane DT {
				TEXT-ALIGN: center;
				PADDING-BOTTOM: 9px;
				PADDING-TOP: 0px;
				PADDING-LEFT: 0px;
				
				/*Alinear texto con Numero*/
				LINE-HEIGHT: 43px;   
				height: 100px;
				TEXT-TRANSFORM: none;	
				
				/*acomodo texto*/
				PADDING-RIGHT: 10px;
				FONT-FAMILY: Arial, Helvetica, sans-serif;
				LETTER-SPACING: 2px;
				
				/*distancia persianas*/
				HEIGHT: 35px;
				COLOR: #f5f5f5;
				FONT-SIZE: 12px;
				FONT-WEIGHT: normal;	
				background-image: url(<%=request.getContextPath()%>/resources/images/barra_ver_act.png);
				background-repeat: no-repeat;
				/* Posicion de la imagen */
				background-position: -24px; 
			}
			#iSolicitantePane DT.active {
				BACKGROUND: url(<%=request.getContextPath()%>/resources/images/barra_ver_inact.png);
				background-repeat: no-repeat; 
				COLOR: #f5f5f5; 
				CURSOR: pointer;
				background-position: -24px;
			}
			#iSolicitantePane DT.hover {
				COLOR: #f5f5f5;
			}
			#iSolicitantePane DT.hover.active {
				COLOR: #f5f5f5;
			}
			
			/*distancia y color de numero*/
			#iSolicitantePane .slide-number {
				COLOR: #68889b; FONT-WEIGHT: bold;
			}
			#iSolicitantePane .active .slide-number {
				COLOR: #fff
			}
			#iSolicitantePane A {
				COLOR: #58595b;
				font-family: Arial, Helvetica, sans-serif;
			}
			#iSolicitantePane DD IMG {
				MARGIN: 0px; FLOAT: right
			}
			#iSolicitantePane H2 {
				MARGIN-TOP: 10px; FONT-SIZE: 2.5em
			}
			#iSolicitantePane .more {
				DISPLAY: block; PADDING-TOP: 10px
			}
		</style>
	
	 <!--Hoja de estilo para los popups-->
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
    <!--	Hoja de estilo para los gadgets-->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
 	<!--Hoja de estilo para el texto dentro de los acordeones-->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<!--Hoja de estilos para el grid-->
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<!--    Hoja de estilo para easyaccordion-->
    <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
 	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>  
	<!--link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" /-->	 	
 	

	<!--scripts -->
	<!--Scripts necesarios para el funcionamiento de la JSP-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/timer/jquery.idletimeout.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/timer/jquery.idletimer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/layout_complex.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/reloj.js"></script>
	<!--scripts del gird-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jqgrid/jquery.jqGrid.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.zweatherfeed.js"></script>
    
    
    <!--Scripts necesarios para la ejecucion del editor-->
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>

	<!--Scrip para el idioma del calendario-->
    <script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
    <!--script de jquery UI-->
	<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/prettify.js"></script> --%>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/defensoria/funcionesComunes.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/defensoria/cmpActuacionesExpediente.js"></script>
	
    <script type="text/javascript">

		var CONTEXT_ROOT = '<%= request.getContextPath()%>';
		var contextoPagina = "${pageContext.request.contextPath}";
		
		var idWindow = '<%= request.getParameter("idWindow")%>';
		var expedienteId = "";
		var numeroExpediente = "";
		var numeroExpedienteId = "";

		$(document).ready(function() {

			jQuery(document).ajaxStop(desbloquearPantalla());
			//Se crean las tabs principales
			$("#tabsPrincipalesSolDefensorDEF" ).tabs();

			//ocultamos el domicilio de notificaciones
			killDomicilioNotificaciones();
			$('#liDom').hide();	
			$('#tabs-2').hide();	
			
			//se genera el acordeon
			$('#iSolicitantePane').easyAccordion({ 
			  autoStart: false, 
			  slideInterval: 3000
			});
			
			construirGridProbablesRespSolicitudDeDefensor();
			
			// Inicialmente el boton se oculta hasta que el usuario comience a escribir
			$('#btnValidarNumCaso').hide();
			//Se oculta la pestania de datos del solicitante
			//hasta que se seleccione uno de los PR
			$('#liAsignarDefensor').hide();
		});

		/**
		* Funcion que se encarga verificar si el usuario ingreso cualquier caracter  
		* (letra, numero o simbolo), el sistema habilita la opcion: "Validar numero de caso" 
		* En caso de que, se tengan espacios y se borren caracteres, y no se tenga un caracter distinto al espacio, se oculta el boton.
		*/
		//MOD DefensorATE
		function habilitarValidacion(campo){
		    var valor = campo.value;
		    //al escribir se habilita el boton
		    if(valor!=null && trim(valor) != ""){
		    	$('#btnValidarNumCaso').show();
		    }
		    else{
		    	$('#btnValidarNumCaso').hide();
		    	limpiarGridGenerico($("#tblGridInvolucradosExpedientes"), funcionLimpiezaGridSolicitud, false, "idDivContInvolucrados");
		    }
		}
		
		/**
		* Funcion para consultar el expediente respecto al numero de caso
		*/
		//MOD DefensorATE
		function consultarExpedientePorCaso(){

			var numeroDeCaso = "";
			numeroDeCaso =  trim($('#txtNumCaso').val());

			if(numeroDeCaso === undefined || numeroDeCaso == null || numeroDeCaso == '' ){
				customAlert("Debe ingresar un n&uacute;mero de caso");
				limpiarGridGenerico($("#tblGridInvolucradosExpedientes"), funcionLimpiezaGridSolicitud, false, "idDivContInvolucrados");
				return;
			}

			var parametros = "";
			
			parametros += 'numeroDeCaso='+ numeroDeCaso;
					
			ejecutaAction("/buscarExpedientePorNumeroDeCaso", function(xmlRespuesta){

				//Limpiar variables
				expedienteId="";
				numeroExpediente="";
				numeroExpedienteId="";
				
				var errorCode=$(xmlRespuesta).find('response').find('code').text();
				if(parseInt(errorCode)==0){
					if(existe($(xmlRespuesta).find('ExpedienteDTO').find('expedienteId').first().text())){
						expedienteId = $(xmlRespuesta).find('ExpedienteDTO').find('expedienteId').first().text();
					}
					if(existe($(xmlRespuesta).find('ExpedienteDTO').find('numeroExpediente').first().text())){
						numeroExpediente = $(xmlRespuesta).find('ExpedienteDTO').find('numeroExpediente').first().text();
					}
					if(existe($(xmlRespuesta).find('ExpedienteDTO').find('numeroExpedienteId').first().text())){
						numeroExpedienteId = $(xmlRespuesta).find('ExpedienteDTO').find('numeroExpedienteId').first().text();
					}
					
					if(existe($(xmlRespuesta).find('ExpedienteDTO').find('discriminante').find('distrito').find('claveDistrito').first().text())){
						$("#distritoExpediente").html($(xmlRespuesta).find('ExpedienteDTO').find('discriminante').find('distrito').find('claveDistrito').first().text() + " - "
								+ $(xmlRespuesta).find('ExpedienteDTO').find('discriminante').find('distrito').find('nombreDist').first().text());
					}
					cargaGridProbablesRespSolicitudDeDefensor();
				}else { 
					customAlert("El n&uacute;mero de caso es incorrecto o no existe");
					limpiarGridGenerico($("#tblGridInvolucradosExpedientes"), funcionLimpiezaGridSolicitud, false, "idDivContInvolucrados");
				}
			} , parametros);
		}
		
		/*
		*Funcion para consultar los probables responsables para solicitud de defensor
		*/
		//MOD DefensorATE
		function cargaGridProbablesRespSolicitudDeDefensor(){
			var tituloProbableResponsable = '<bean:message key="plProbalbeResponsableTitulo" />';
			
			//Limpia los identificadores del grid cada vez que se desea cargar el grid
			limpiarIdentificadores($("#tblGridInvolucradosExpedientes"));
			
			jQuery("#tblGridInvolucradosExpedientes").jqGrid('setGridParam', {
				url:'<%= request.getContextPath()%>/consultarImputadosPorNumExpediente.do?expedienteId='+expedienteId+'',
				datatype: "xml",
				loadComplete: function (xml){
					var totalProbParticipes = $(xml).find('rows').find('records').text();
					
					if(parseInt(totalProbParticipes) <= 0 ){
						customAlert("El n&uacute;mero de caso no tiene "+tituloProbableResponsable+" relacionados al expediente, <br> por lo tanto no se puede realizar la solicitud de defensor.");
						limpiarGridGenerico($("#tblGridInvolucradosExpedientes"), funcionLimpiezaGridSolicitud, false, "idDivContInvolucrados");
					}
					else{
						//Se consultan cuales son los identificadoes, previamente guardados
						//para ser colocados como seleccionados, se psas el grid.
						seleccionarIdentificadores($(this));
						//Habilitar el contenedor del grid
						habilitarGrid(true, "idDivContInvolucrados");
					}
				},
				onSelectRow: function(idIdentificador, esSeleccionado){
					//Se debe de guardar el valor seleccionado
					actualizarIdentificador(idIdentificador, esSeleccionado, $(this));
					validarSeleccionProbablesResponsables();
				},
				onSelectAll: function(idIdentificadores, esSeleccionado){
					//Se debe de guardar los valores seleccionados
					actualizarIdentificadores(idIdentificadores, esSeleccionado, $(this));
					validarSeleccionProbablesResponsables();
				},
				ondblClickRow: function(rowid) {
				},
				gridComplete: function () {
				},
				onPaging: function () {
				},
				onSortCol: function(){
				}
			});
			$("#tblGridInvolucradosExpedientes").trigger("reloadGrid");			
		}

		/**
		* Funcion que define las instrucciones referentes a la limpieza del grid.
		* Por ejemplo, quitar la leyenda, ocultar pestania, etc.
		* Es pasada como parametro en la funcion de "limpiarGridGenerico"
		*/
		//MOD DefensorATE
		function funcionLimpiezaGridSolicitud(){
			$("#distritoExpediente").html("");
			$('#liAsignarDefensor').hide();
		}
		
		
		/**
		* Funcion para construir el grid que mostrara los probables responsables del caso
		*/
		//MOD defensorATE
		function construirGridProbablesRespSolicitudDeDefensor(){
			var tituloProbableResponsable = '<bean:message key="plProbalbeResponsableTitulo" />';
			jQuery("#tblGridInvolucradosExpedientes").jqGrid({
				datatype: "xml",
				colNames:['Nombre Completo'], 
				colModel:[ 	
							{name:'nombresProbResp',index:'nombresProbResp',width:250}, 
						],
				pager: jQuery('#divPagerGridInvolucradosExpedientes'),
				rowNum:10,
				rowList:[10,20,30],
				width:720,
				caption:tituloProbableResponsable,
				multiselect:true,
				viewrecords: true,
				sortorder: "desc"
				
			});
		}

		/**
		* Funcion que determina, de acuerdo al numero de Prob Responsables seleccionados del grid,
		* si se habilita o desabilita la pestania de asignar defensor
		*/
		//MOD defensorATE
		function validarSeleccionProbablesResponsables(){
			var involucradosSeleccionados = obtenerIdentificadoresSeleccionados($("#tblGridInvolucradosExpedientes"));
			if(involucradosSeleccionados.length <= 0){
				$('#liAsignarDefensor').hide();
				$("#tblGridInvolucradosExpedientes").jqGrid('resetSelection');
			}
			else{
				$('#liAsignarDefensor').show();
			}
		}
		
		
///////////////////////////////////////////////TERMINAN LAS FUNCIONES PARA GRID DE INVOLUCRADOS///////////////////////////////////		


/////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////FUNCIONES PARA SOLICITAR DEFENSOR DESDE DEFENSOR ATE									/////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	* Funcion que ejecuta la cancelacion del turno.
	*/
	//MOD defensorATE
	function cancelarSolicitudDeDefensor(){
		
		customConfirm("&iquest;Realmente desea cancelar la solicitud de defensor y el turno?",
				"Cancelar solicitud", 
				function(){
					//Aceptar
					window.parent.cancelarTurnoPRCAN();
					window.parent.cerrarVentanaSolicitudDeDefensor(idWindow);
				},
				function(){
					//Calcelar
					$( this ).dialog( "close" );
				}
		)
	}


	/**
	* Funcion que confirma que se realizara, la solicitud de defensor
	*/
	//MOD defensorATE
	function confirmarSolicitudDeDefensor(){

		var solicitanteId = "";
		var validado = validarSolicitudDeDefensor();
		
		if(validado == true && validado != null && validado != undefined && validado != ""){

			solicitanteId = guardarSolicitante();

			if(solicitanteId != undefined && solicitanteId != "" && solicitanteId != null && solicitanteId > 0 ){
				generarSolicitudDefensorDefensoria(solicitanteId);
			}
		}
	}
	
	
	/**
	* Funcion para validar la solicitud de defensor tenga los parametros necesarios.
	*/
	//MOD defensorATE
	function validarSolicitudDeDefensor(){

		var respuesta = true;
		
		var involucradosSeleccionados = obtenerIdentificadoresSeleccionados($("#tblGridInvolucradosExpedientes"));
		
		if(involucradosSeleccionados.length <= 0){
			respuesta = false;
			customAlert("Seleccione almenos un involucrado");
		}
		
		if($('#datosGeneralesCmpNombres').val() == "" 
			|| $('#datosGeneralesCmpNombres').val() == " " 
				|| $('#datosGeneralesCmpNombres').val() == null
					|| $('#datosGeneralesCmpNombres').val() == undefined){
			
			respuesta = false;
			customAlert("Ingrese almenos el nombre del solicitante");
		}

		if((numeroExpediente == "" && expedienteId == "" && numeroExpedienteId == "") 
				|| (numeroExpediente == undefined && expedienteId == undefined && numeroExpedienteId == undefined)
					|| (numeroExpediente == null && expedienteId == null && numeroExpedienteId == null)){
			respuesta = false;
			customAlert("El caso no cuenta con un expediente legible");
		}
		
		return respuesta; 
	}

	/**
	* Funcion que se encarga de recuperar la informacion del solicitante, 
	* para ser guardado con la rel
	*/
	//MOD defensorATE
	function guardarSolicitante(){

		var respuestaSolicitanteId = 0;
		var params = '';
		var idSolicitante = 'idSol';

		params += 'expedienteId='+expedienteId;
		//TODO Revisar que el parametro no es utilizado
		params += '&idsInvolucrados=' + obtenerIdentificadoresSeleccionados($("#tblGridInvolucradosExpedientes"));
		//params += '&idIndividuo='+ idSolicitante;

		//Datos generales, media filiacion, medios de contacto, documentos de identificacion
		var datosGenerales = obtenerParametrosDatosGenerales();//Include de datos generales
		params += datosGenerales;

		//Datos nacimiento
		var datosNacimiento = obtenerParametrosDatosNacimiento();
		params += datosNacimiento;		
		
		//Domicilio
		datosDomicilio = obtenerParametrosDomicilio();
		params += datosDomicilio;

		//Medios de contacto
		datosMedios = obtenerMedios();
		params += datosMedios;

		ejecutaAction("/guardarSolicitanteSolDeDefensor", function(xmlRespuesta){

			var errorCode=$(xmlRespuesta).find('response').find('code').text();
			
			if(parseInt(errorCode)==0){
				if(existe($(xmlRespuesta).find('involucradoSolDefForm').first().text()) &&
						existe($(xmlRespuesta).find('involucradoSolDefForm').find('idIndividuo').first().text()) ){
					
					respuestaSolicitanteId = $(xmlRespuesta).find('involucradoSolDefForm').find('idIndividuo').first().text();
				}else{
					customAlert("Error al intentar guardar el solicitante");
				}
			}else{
				customAlert("Ocurri&oacute; un error al intentar gurdar el solicitante, por favor contacte al administrador");		
			}
		} , params);

		return respuestaSolicitanteId;
	}

/*********************************************************************************************************************/
/************************FUNCIONES PARA RECUPERAR LOS PARAMETROS DEL DOCUMENTO ****************************************/
/*********************************************************************************************************************/

	/*
	*Funcion para consultar el parametro de confacttividad documento
	*para la solicitud de defensor
	*/
	//MOD defensorATE
	function cargaActuaciones() {

		var confActividadDocId; 
		var parametros = "";
		
		parametros += 'categoriaActividadId=' + <%= CategoriasActividad.MINISTERIAL.getValorId()%>;
		parametros += '&muestraCombo=' + false;
		parametros += '&tipoActividadValorId=' + <%=Actividades.SOLICITAR_DEFENSOR_PUBLICO.getValorId()%>;
		
		ejecutaAction("/cargarActuacionesPorFiltro", function(xml){
			confActividadDocId = $(xml).find('catActuaciones').first().find('clave').text();
		} , parametros);

		return confActividadDocId;
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
	//MOD defensorATE
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
		respuesta += '&formaId=' + formaID;
		respuesta += '&titulo=' + titulo;
		respuesta += '&nombreActividad=' + nombreActividad;
	
		return respuesta;
	}
	

	//Variable para controlar el id de la ventana
	var idWindowPantallaActuaciones = 1;
	
	/**
	* Funcion que se debe invocar para generar el documento de solicitud de defensor
	* y que obtiene los parametros necesarios para abrir el editor de texto
	*/
	//MOD defensorATE
	function generarSolicitudDefensorDefensoria(solicitanteId){
		//Incrementar para generar un nuevo Id
		idWindowPantallaActuaciones++;
		
		var confActividadDocumentoId = cargaActuaciones();
		var parametros = obtenerParametrosActividadDocumento(confActividadDocumentoId);

		var arrayInvolucradosSolDefensor = obtenerIdentificadoresSeleccionados($("#tblGridInvolucradosExpedientes"));
		
		parametros += '&numeroUnicoExpediente='+numeroExpediente;
		parametros += '&expedienteId=' + expedienteId;
		parametros += '&idNumeroExpediente=' + numeroExpedienteId;
		//parametros += '&solicitudDefensorId=' + solicitudId;
		parametros += '&solicitanteId=' + solicitanteId;
		parametros += '&ocultarGuardadoParcial=' + true;
		parametros += '&ocultarNumeroOficio=' + true;
		parametros += '&esconderArbol=' + true;
		parametros += '&arrayInvolucradosSolDefensor=' + arrayInvolucradosSolDefensor;
		parametros += '&idWindowPantallaActuaciones=' + "iframewindowGenerarDocumento"+idWindowPantallaActuaciones;

		//de los parametros obtenemos el titulo del documento
		var titulo = (parametros.split('&')[3]).split('=')[1];

		$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:true});
		$.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?parametros='+parametros+'" width="1140" height="400" />');
	
	}

	/*
	*Funcion para cerrar el editor de texto con la solicitud de defensor
	*/
	//MOD defensorATE
	function cerrarVentanaGenerarDocumentoDefensor(idWindowPantallaActuaciones){
		$.closeWindow(idWindowPantallaActuaciones);
		window.parent.cerrarVentanaSolicitudDeDefensor(idWindow);
	}
		
	</script>
</head>
<body>
	<table width="90%" >
		<tr align="right">
			<td width="23%" align="right">
				<input type="button" id="btnCancelarSol" value="Cancelar" onClick="cancelarSolicitudDeDefensor()" class="btn_Generico"/>
			</td>
		</tr>
	</table>
	
	<!--Comienza div principal para las tabs-->
	<div id="tabsPrincipalesSolDefensorDEF">
		<ul>
			<li id="liResumen">
				<a href="#tabsVisorSolDefensorDEF-1">Datos Generales</a>
			</li>
			<li id="liAsignarDefensor" class="tabDatosSolicitante">
				<a href="#tabsVisorSolDefensorDEF-2">Datos del Solicitante</a>
			</li>
		</ul>
		
		<!--Comienza tab 1-->
		
		<div id="tabsVisorSolDefensorDEF-1" >
				<table width="100%" border="0">
				  <tr>
					<td width="5%">&nbsp;</td>
					<td width="45%">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
					<td width="5%">&nbsp;</td>
				  </tr>
				  <tr>
					<td>&nbsp;</td>
					<td>
						<strong>Ingrese el n&uacute;mero de caso</strong>
					</td>
					<td colspan="2">&nbsp;</td>
					<td>&nbsp;</td>
				  </tr>
				  <tr>
					<td>&nbsp;</td>
					<td>
						<strong>Numero de Caso: </strong>
						<input type="text" class="" style="width: 200px; border: 0;" id="txtNumCaso" onkeyup="habilitarValidacion(this)" /> 
						<input type="button" id="btnValidarNumCaso" value="Validar n&uacute;mero de caso" onClick="consultarExpedientePorCaso()" class="btn_Generico"/>
					</td>
					<td colspan="2">
						<strong>La solicitud ser&aacute; enviada al Coordinador del Distrito:</strong>
						<strong><label style="font-size:20px" id="distritoExpediente"> </label> </strong>
					</td>
					<td>&nbsp;</td>
				  </tr>
				  <tr>
					<td>&nbsp;</td>
					<td colspan="3" rowspan="4" align="center" valign="middle">
						<div id= "idDivContInvolucrados">
							<table id="tblGridInvolucradosExpedientes"></table>
							<div id="divPagerGridInvolucradosExpedientes" ></div>
						</div>
					</td>
					<td>&nbsp;</td>
				  </tr>
				  <tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				  </tr>
				  <tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				  </tr>
				  <tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				  </tr>
				</table>
		</div>
		
		
		
		<!--Comienza tab 1-->
		
		<div id="tabsVisorSolDefensorDEF-2" class="tabDatosSolicitante">
			<table border="0">
				<tr>
					<td>
						<div id="iSolicitantePane">
				        	<dl>
								<dt id="cejaDatosGeneralesDEF">Datos Generales</dt>
									<dd>
										<jsp:include page="/WEB-INF/paginas/datosGeneralesView.jsp" />
									</dd>
								<dt id="cejaDomicilioDEF">Domicilio</dt>
									<dd>
										<jsp:include page="/WEB-INF/paginas/ingresarDomicilioView.jsp" />
									</dd>
								<dt id="cejaMediosContactoDEF">Medios de Contacto</dt>
									<dd>
										<jsp:include page="/WEB-INF/paginas/ingresarMediosContactoView.jsp" />
									</dd>
							</dl>
						</div>
					</td>
					<td colspan="2">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
					<td align="right" valign="top">
						<input type="button" id="btnAceptarSol" value="Aceptar" onClick="confirmarSolicitudDeDefensor()" class="btn_Generico"/>	
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>