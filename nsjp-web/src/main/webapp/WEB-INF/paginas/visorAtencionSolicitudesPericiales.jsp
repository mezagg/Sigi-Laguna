<!-- 
Visor atencion a solicitudes periciales
Funcionalidad para:
	-Ver los datos de la solicitud
	-Ver los documentos enviados por el AMP
	-Realizar actuaciones
	-Ver los documentos propios
 -->
<%@ page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.funcionario.Puestos"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades"%>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Atenci&oacute;n a solicitudes periciales</title>
	
	<!--Se importan las css necesarias-->
<!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" /-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/south-street/jquery-ui.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css" />


<!--Se importan los scripts necesarios-->
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	
	
	
<script type="text/javascript">
	
	/*
	*Variables Generales
	*/
	var documentoId; 
	var solicitudId;
	var deshabilitaActucaiones;

	//Variable para adjuntar documentos
	var idNumeroExpediente
	
	/*
	*Variables para la ceja Documentos
	*/
	var primeraVezGridDocumentosDigitales = true;
	var primeraVezGridDocumentosDigitalesNoPdfs = true;

	/*
	*Variables para la ceja Actuaciones
	*/
	var idWindowPantallaActuaciones=1;
	var idWindowSolicitudDeEvidencia=1;
	
	/*
	*Variables para la ceja Documentos Propios
	*/
	var primeraVezGridDocumentosDigitalesPropios = true;
	var idExpedienteDoc=parent.idExpedienteDoc;
	var numidExpedienteDoc=parent.numidExpedienteDoc;
	var numExpediente=parent.numExpediente;
	var idExpedienteop = parent.idExpedienteDoc;
	var contextoPagina = "${pageContext.request.contextPath}";

	var mandaFormaEnConsulta="si";

	//Variables de Cadena de Custodia
	var idWindowAsntarRegCadCus=1;
	var PERITO=61;  //Utilizado en asentarRegCadenaCustodia
	var pantallaSolicitada=PERITO;
	var confirmarCierreVentana ='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getMostrarMensajeConfirmacionEnDocumento() %>';
	var extensionesPermitidasAudio = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getExtensionesPermitidasAlAdjuntarAudio() %>';
	var extensionesPermitidasImagen = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getExtensionesPermitidasAlAdjuntarImagen() %>';
	var CONTEXT_ROOT = '<%= request.getContextPath()%>';
	
	/*
	*On ready del documento
	*/	
	$(document).ready(function() {
		
		//On ready
		if(confirmarCierreVentana !=null && (confirmarCierreVentana == "1" || confirmarCierreVentana==1)) {
			confirmarCierreVentana = true;
		}else{
			confirmarCierreVentana = false;
		}

		//Obtenemos parametros
		documentoId = <%=request.getParameter("documentoId")%>;
		solicitudId = <%=request.getParameter("solicitudId")%>;
		deshabilitaActucaiones = <%=request.getParameter("deshabilitaActucaiones")%>;
		//crear tabs		
		$("#tabsPrincipal").tabs();
		
		/*
		*LLamadas para la ceja de detalle de solicitud
		*/
		consultarDetalleSolicitud();
		/*
		*LLamadas para la ceja documentos
		*/
		cargaGridDocumentosDigitales();
		cargaGridDocumentosDigitalesNoPdfs();
		cargaDatosSolicitud(solicitudId);
		cargaGridEvidencias(solicitudId);
		/*
		*LLamadas para la ceja actuaciones
		*/	
		cargaActuaciones();
		//Escuchador de cambio en el combo box
		$("#lstActuaciones").change(mostrarOcultarElementos);
		//Oculta inicialmente los elementos
		ocultaActuaciones();
		
		/*
		*LLamadas para la ceja documentos propios
		*/
		cargaGridDocumentosDigitalesPropios();

		//Cadena de Custodia
		$("#btnCadCusNuevaCadCus").click(asentarRegCadenaCustodia);
		$("#btnCadCusConsultaCadCus").click(consultarRegCadenaCustodia);
		//FIN Cadena de custodia
		
		if(!isEmpty(deshabilitaActucaiones) && deshabilitaActucaiones == true){
			$("#lstActuaciones").attr('disabled','disabled');
		}
		
	});//FIN ONREADY
/**************************************************************FUNCIONALIDAD PARA LA CEJA SOLICITUD************************************************************/

	function consultarDetalleSolicitud(){

		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/consultarDetalleDeSolicitud.do',
    		data: 'solicitudPericialId='+solicitudId,
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			var errorCode;
				errorCode=$(xml).find('response').find('code').text();				
				if(parseInt(errorCode)==0){	
					
					$('#atnServPericialFolioSol').val($(xml).find('folioSolicitud').first().text());
					$('#atnServPericialEspecialidadSol').val($(xml).find('especialidad').find('valor').last().text());
					$('#atnServPericialTipoEstudioSol').val($(xml).find('tipoEstudio').find('valor').last().text());
					$('#atnServPericialNumCaso').val($(xml).find('numeroGeneralCaso').first().text());
					$('#atnServPericialNumExp').val($(xml).find('numeroExpediente').first().text());
					$('#atnServPericialNombSolicitante').val($(xml).find('nombreFuncionario').first().text()+" "+$(xml).find('apellidoPaternoFuncionario').first().text()+" "+$(xml).find('apellidoMaternoFuncionario').first().text());
					$('#atnServPericialFechLim').val($(xml).find('fechaLimiteStr').first().text());
    			}
				else{
					//Mostrar mensaje de error
				}
    		}
    	});
	}

/**************************************************************FUNCIONALIDAD PARA LA CEJA EVIDENCIAS************************************************************/
	function cargaGridEvidencias(solicitudId){
		jQuery("#gridDetalleCadenaCustodia").jqGrid({ 
			url:'<%= request.getContextPath()%>/consultarEvidenciasSolicitud.do?solicitudId='+solicitudId+'',
			data:'',
			datatype: "xml", 
			colNames:['N&uacute;mero de Evidencia','Cadena de Custodia','Objeto'/*,'C&oacute;digo de Barras'*/], 
			colModel:[ 	{name:'NumeroEvidencia',index:'numeroEvidencia', width:150},
			           	{name:'CadenaCustodia',index:'cadenaCustodia', width:150},
			           	{name:'Objeto',index:'objeto', width:150}/*,
			           	{name:'CodigoBarras',index:'codigoBarras', width:150}*/
					],
			pager: jQuery('#pagerCadenaCustodia'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			sortname: 'CadenaCustodia',
			viewrecords: true,
			sortorder: "desc",
			multiselect: false
			}).navGrid('#pagerCadenaCustodia',{edit:false,add:false,del:false});
	}


	function cargaDatosSolicitud(solicitudId){
		$.ajax({
	    	  type: 'POST',
	    	  url:  '<%= request.getContextPath()%>/consultaDetalleSolicitudPericial.do',
	    	  data: 'solicitudPericialId='+solicitudId,
	    	  dataType: 'xml',
	    	  async: false,
	    	  success: function(xml){
	    		  pintaDatosSolicitud(xml);
			  }
	    });
	}

	function pintaDatosSolicitud(xml){
	   if($(xml).find('observaciones') != null){
		   $('#areaDescripcion').val($(xml).find('observaciones').text());
	    }
	}

	
/**************************************************************FUNCIONALIDAD PARA LA CEJA DOCUMENTOS DE LA SOLICITUD************************************************************/
	/*
	*Funcion que carga el grid con los nombre de los documentos digitales asociados 
	*al id de la solicitud de serv. periciales
	*/
	function cargaGridDocumentosDigitales(){

		if(primeraVezGridDocumentosDigitales == true){
			jQuery("#gridDocumentosDigitales").jqGrid({
				url:'<%=request.getContextPath()%>/consultarDocumentosPropiosAsociadosASolicitudPericial.do?solicitudId='+solicitudId+'&esPdf=true',
				datatype: "xml", 
				colNames:['&Aacute;rea del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento'],
				colModel:[ 	{name:'area',index:'area', width:200,hidden:true},
							{name:'FechaActividad',index:'fechaActividad', width:170,hidden:true},							
							{name:'NombreActividad',index:'nombreActividad', width:400,hidden:true},
				           	{name:'Tipo',index:'tipo', width:155,hidden:true}, 
							{name:'Nombre',index:'nombre', width:255},
				           	{name:'Fecha',index:'fecha', width:170,hidden:true}
							],
				pager: jQuery('#pagerGridDocumentosDigitales'),
				rowNum:20,
				rowList:[10,20,30],
				width:250,
				sortname: 'nombreDocumento',
				viewrecords: true,
				sortorder: "desc",
				//multiselect:true,
				ondblClickRow: function(rowid) {
					if (rowid) {
						var retd = jQuery("#gridDocumentosDigitales").jqGrid('getRowData',rowid);
						var tipo = retd.Tipo;
						if(tipo != (".pdf")){
							consultaArchivo(rowid);
						}else{
							abrirDocsDigAsociadosASol(rowid);
						}
													
					}
				}
			}).navGrid('#pagerGridDocumentosDigitales',{edit:false,add:false,del:false});
			$("#gview_gridDocumentosDigitales .ui-jqgrid-bdiv").css('height', '227px');
			
			primeraVezGridDocumentosDigitales= false;
		}
		else{
			jQuery("#gridDocumentosDigitales").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDocumentosPropiosAsociadosASolicitudPericial.do?solicitudId='+solicitudId+'&esPdf=true',datatype: "xml" });
			$("#gridDocumentosDigitales").trigger("reloadGrid");
		}
	}
	
	function cargaGridDocumentosDigitalesNoPdfs(){

		if(primeraVezGridDocumentosDigitalesNoPdfs == true){
			jQuery("#gridDocumentosDigitalesNoPdfs").jqGrid({
				url:'<%=request.getContextPath()%>/consultarDocumentosPropiosAsociadosASolicitudPericial.do?solicitudId='+solicitudId+'',
				datatype: "xml", 
				colNames:['&Aacute;rea del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Otros Documentos','Fecha del documento','Tipo de documento'],
				colModel:[ 	{name:'area',index:'area', width:200,hidden:true},
							{name:'FechaActividad',index:'fechaActividad', width:170,hidden:true},							
							{name:'NombreActividad',index:'nombreActividad', width:400,hidden:true},
				           	{name:'Tipo',index:'tipo', width:155,hidden:true}, 
							{name:'Nombre',index:'nombre', width:255},
				           	{name:'Fecha',index:'fecha', width:170,hidden:true},
							{name:'TipoDoc',index:'tipoDoc', width:170,hidden:true}
							],
				pager: jQuery('#pagerGridDocumentosDigitalesNoPdfs'),
				rowNum:20,
				rowList:[10,20,30],
				width:250,
				sortname: 'nombreDocumento',
				viewrecords: true,
				sortorder: "desc",
				//multiselect:true,
				ondblClickRow: function(rowid) {
					if (rowid) {
						var retd = jQuery("#gridDocumentosDigitalesNoPdfs").jqGrid('getRowData',rowid);
						var tipo = retd.Tipo;
						var titulo = retd.Nombre;
						if(tipo != (".pdf")){
							if(retd.TipoDoc.indexOf('Archivo de audio') > 0){
								abreFrameAudio(rowid,titulo,'archivoDigitalId');
							}else{
								consultaArchivo(rowid);
							}
						}else{
							abrirDocsDigAsociadosASol(rowid);
						}
													
					}
				}
			}).navGrid('#pagerGridDocumentosDigitalesNoPdfs',{edit:false,add:false,del:false});
			$("#gview_gridDocumentosDigitalesNoPdfs .ui-jqgrid-bdiv").css('height', '227px');
			
			primeraVezGridDocumentosDigitalesNoPdfs= false;
		}
		else{
			jQuery("#gridDocumentosDigitalesNoPdfs").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDocumentosPropiosAsociadosASolicitudPericial.do?solicitudId='+solicitudId+'',datatype: "xml" });
			$("#gridDocumentosDigitalesNoPdfs").trigger("reloadGrid");
		}
	}

	/*
	*Funcion que abre el PDF para ver los documentos asociados al numero de causa
	*/
	function abrirDocsDigAsociadosASol(documentoAsocId){
		if(documentoAsocId != null && documentoAsocId != ""){
			$("#visorDocsFrame").attr("src","<%= request.getContextPath()%>/consultarArchivoDigitalIframe.do?archivoDigitalId="+documentoAsocId+"&inFrame=true");
		}
		else{
			alert("El documento no puede ser mostrado");
		}
	}
	
/*******************************************************COMIENZA FUNCIONALIDAD PARA ACTUACIONES PERICIALES ***************************************************/
	
	/*************************************************************FUNCIONALIDAD COMUN*********************************************************************/
	
	/*
	*Funcion que oculta o muestra elementos en la 
	*pantalla dependiendo de la opcion seleccionada
	*/
	function mostrarOcultarElementos(){
		var confActividadId = $("#lstActuaciones option:selected").val();

		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/obtenerConfActividadDocumento.do?idConf='+confActividadId,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				actividad=$(xml).find('confActividadDocumentoDTO').find('tipoActividadId').text();
				formaID=$(xml).find('confActividadDocumentoDTO').find('formaId').text();
				titulo=$(xml).find('confActividadDocumentoDTO').find('nombreDocumento').text();
				usaeditor=$(xml).find('confActividadDocumentoDTO').find('usaEditor').text();
				estatusId=$(xml).find('confActividadDocumentoDTO').find('estadoCambioExpediente').find('idCampo').text();
				nombreActividad=$(xml).find('confActividadDocumentoDTO').find('nombreActividad').text();
			}
		});
		
		//Oculta todo
		if(confActividadId == "0"){
			ocultaActuaciones();
		}
		else if(confActividadId == '<%= Actividades.SOLICITUD_DE_EVIDENCIA.getValorId() %>'){
			registrarActividadExpediente(actividad,estatusId,0);
			$("#agregarDocs").hide();
			$("#realizarDictamen").hide();
			$("#realizarInforme").show();
			muestraVisorSolicitudDeEvindecia();
		}
		else if(confActividadId == '<%= Actividades.ANEXAR_DOCUMENTO_PERITO.getValorId() %>'){
			registrarActividadExpediente(actividad,estatusId,0);
			$("#agregarDocs").show();
			$("#realizarDictamen").hide();
			$("#realizarInforme").hide();
		}
		else{
			$("#agregarDocs").hide();
			$("#realizarDictamen").show();
			$("#realizarInforme").hide();
			
			documentoId="";
			var exp = subirExpedienteSesion(formaID);
			abrirDocumento(documentoId,exp,titulo, actividad, formaID);
		}
	}

	function registrarActividadExpediente(actuacionID,estatusId,banderaMensaje,funcionario){
		var idFuncionario;
		if ( funcionario == undefined ) {
			idFuncionario = '';
		}else{
			idFuncionario=funcionario;
		}
		
		//Cambia la actividad del expediente
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/registraActividadExpediente.do?idExpediente='+idExpedienteop+'&actuacion='+actuacionID+'&estatus='+estatusId+'&cveFuncionarioAsignado='+idFuncionario,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				if(parseInt(banderaMensaje)==1)
				{
					alertDinamico("Actividad nueva registrada");	
				}
			}
		});
	}
	
	/*
	*Funcion que realiza la carga del combo de Actuaciones
	*/
	function cargaActuaciones() {
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarActuaciones.do?numeroExpediente='+numExpediente+'&numeroExpedienteId='+numidExpedienteDoc,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catActuaciones').each(function(){
					$('#lstActuaciones').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});            
			}
		});
	}
	
	/*
	*Funcion que oculta todos los elementos de la pantalla
	*/
	function ocultaActuaciones(){
		
		$("#agregarDocs").hide();
		$("#realizarDictamen").hide();
		$("#realizarInforme").hide();
	}

	/********************************************************FUNCIONALIDAD PARA ANEXAR DOCUMENTOS*******************************************************/
	
	/*
	*Funcion para anexar un documento a la solicitud pericial
	*/
	function anexarDocumento(){

		
		//forma = document.anexarDocumentoForm; 
		//forma.documentoId.value = documentoId;
		//forma.submit();
			
	}


	/********************************************************FUNCIONALIDAD PARA REALIZAR DICTAMEN E INFORME***********************************************/
	
	/*
	*Funcion que crea el documento recibe el tipo de documento
	*que se desea crear
	*/
	function crearDocumento(forma){

		var documentoId = "";
		
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/crearDictamenParaSolicitudPericial.do',
			data: 'solicitudPericialId='+solicitudId+'&formaId='+forma,
			dataType: 'xml',
			async: false,
			success: function(xml){

				var errorCode;
				errorCode=$(xml).find('response').find('code').text();				
				if(parseInt(errorCode)==0){
					documentoId = $(xml).find('documentoId').first().text();					
					//$("#gridSolicitud").trigger("reloadGrid");
				}
				else{
					//Mostrar mensaje de error
				}
			}
		});
		return documentoId;
	}

	
	/*
	*Funcion que sube el expediente a sesion
	*param:Id de la forma que se desea crear
	*return;numero de expediente
	*/
	function subirExpedienteSesion(forma){
		
		//colocar el expediente en sesion
		exp = "";
		$.ajax({
	   		type: 'POST',
	   		url: '<%=request.getContextPath()%>/colocarExpedienteParaSolicitudPericial.do',
	   		data: 'solicitudPericialId='+solicitudId+'&formaId='+forma,
	   		dataType: 'xml',
	   		async: false,
	   		success: function(xml){
	   			var errorCode;
				errorCode=$(xml).find('response').find('code').text();				
				if(parseInt(errorCode)==0){	
					exp = $(xml).find('numeroExpediente').first().text();
	   			}
				else{
					//Mostrar mensaje de error
				}
	   		}
	    }); 
		return exp;
	}

	
	/*
	*Funcion que abre el visor para generar documentos 
	*/
	
	function abrirDocumento(documentoId,exp,tipo,actividad, formaID){
		idWindowPantallaActuaciones++;
		$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:tipo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
	    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,"<iframe src='<%= request.getContextPath() %>/generarDocumentoSinCaso.do?documentoId="+documentoId+"&numeroUnicoExpediente="+exp+"&solicitudId="+solicitudId+"&nuevaActividad="+actividad+"&mandaFormaEnConsulta="+mandaFormaEnConsulta+"&formaId="+formaID+"&idWindowPantallaActuaciones="+idWindowPantallaActuaciones+"&esTransaccional="+true+"&actividadId="+actividad+"' width='1140' height='400' />");
	}
	/**
	* Funci&oacute;n que es llamada por el generador de documentos guando se realiza un guardado definitivo del documento
	* Esta funci&oacute;n actualiza el estado de la solicitud pericial y adjunta el archivo digital reci&eacute;n generado a las solicitudes padre
	*/
	function documentoGenerado(){
		
		$.ajax({
	   		type: 'POST',
	   		url: '<%=request.getContextPath()%>/finalizarDictamenInformePericialPeritoAMP.do',
	   		data: 'solicitudId='+solicitudId,
	   		dataType: 'xml',
	   		async: false,
	   		success: function(xml){
	   			var errorCode;
				errorCode=$(xml).find('response').find('code').text();				
				if(parseInt(errorCode)==0){	
					
					
					
	   			}
				else{
					//Mostrar mensaje de error
				}
	   		}
	    }); 
		
		
	}
	
	/********************************************************FUNCIONALIDAD PARA REALIZAR SOLICITUD DE EVIDENCIA***********************************************/
	
	/*
	*Funcion que abre el visor para generar documentos 
	*/
	function muestraVisorSolicitudDeEvindecia(){

		idWindowSolicitudDeEvidencia++;
		$.newWindow({id:"iframewindowVisorSolicitudDeEvidencia"+idWindowSolicitudDeEvidencia, statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Solicitud de Evidencia", type:"iframe"});
	    $.updateWindowContent("iframewindowVisorSolicitudDeEvidencia"+idWindowSolicitudDeEvidencia,"<iframe src='<%= request.getContextPath() %>/solicitudDeEvidencia.do?rowid="+solicitudId+"' width='1140' height='400' />");
	}

	
/*******************************************************FUNCIONALIDAD PARA LA CEJA DOCUMENTOS**********************************************************/
	/*
	*Funcion que carga el grid con los nombre de los documentos digitales asociados 
	***************AL EXPEDIENTE
	*/
	function cargaGridDocumentosDigitalesPropios(){

		if(primeraVezGridDocumentosDigitalesPropios == true){
			jQuery("#gridDocumentosDigitalesPropios").jqGrid({
				url:'<%=request.getContextPath()%>/consultarDocumentos.do?idExpedienteop='+numidExpedienteDoc+'',
				datatype: "xml", 
				colNames:['&Aacute;rea del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento', 'Documento', 'Es Parcial'],
				colModel:[ 	{name:'area',index:'area', width:200},
							{name:'FechaActividad',index:'fechaActividad', width:170},							
							{name:'NombreActividad',index:'nombreActividad', width:400},
				           	{name:'Tipo',index:'tipo', width:155}, 
							{name:'Nombre',index:'nombre', width:255},
				           	{name:'Fecha',index:'fecha', width:170},
				           	{name:'Documento',index:'documento', width:170},
				           	{name:'EsParcial',index:'esParcial', hidden:true}
							],
				pager: jQuery('#pagerGridDocumentosDigitalesPropios'),
				rowNum:20,
				rowList:[10,20,30],
				width:1100,
				sortname: 'nombreDocumento',
				viewrecords: true,
				sortorder: "desc",
				
				ondblClickRow: function(rowid) {
					//Se ingresa las columnas y funcionalidad para el consultar documentos 
					// con guardado parcial y guardado definitivo
					var retd = jQuery("#gridDocumentosDigitalesPropios").jqGrid('getRowData',rowid);
					var titulo = retd.Nombre;
										
					if(retd.EsParcial){
						noEsParcial = retd.EsParcial.indexOf('false');
						if(noEsParcial > 0){//"No es parcial"
							consultaPDF(rowid);
						}
						else{//"Es parcial"
							idWindowPantallaActuaciones++;
			     			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
			    		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?documentoId='+rowid+'&numeroUnicoExpediente='+numExpediente+'&numExpediente='+numidExpedienteDoc+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&esTransaccional='+true+'" width="1140" height="400" />');
						}
					}
				},
				loadComplete: function(){
					jQuery("#gridDocumentosDigitalesPropios").jqGrid('hideCol',["area","fechaActividad","nombreActividad","tipo","fecha"]);
				}
			}).navGrid('#pagerGridDocumentosDigitalesPropios',{edit:false,add:false,del:false});
			$("#gview_gridDocumentosDigitalesPropios .ui-jqgrid-bdiv").css('height', '455px');
			primeraVezGridDocumentosDigitalesPropios= false;
		}
		else{
			jQuery("#gridDocumentosDigitalesPropios").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDocumentos.do?idExpedienteop='+numidExpedienteDoc+'',datatype: "xml" });
			$("#gridDocumentosDigitalesPropios").trigger("reloadGrid");
		}
	}

	/*
	*Funcion que se manda a invocar desde el jsp generarDocumentoSinCaso (cerrarVentaDocumentoActualizarGrid)
	*La cual permite actualizar los documentos propios una vez cerrada la venta de duardado definitivo.
	*/
	function documentos(){
		cargaGridDocumentosDigitalesPropios();
	}

	
	function visorDetalleDocumento(){
		jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
			url:'<%=request.getContextPath()%>/consultarDocumentos.do?idExpedienteop='+idExpedienteDoc, 
			datatype: "xml",
			colNames:['&Aacute;rea del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento'],
			colModel:[ 	{name:'area',index:'area', width:200},
						{name:'FechaActividad',index:'fechaActividad', width:170},							
						{name:'NombreActividad',index:'nombreActividad', width:400},
			           	{name:'Tipo',index:'tipo', width:155}, 
						{name:'Nombre',index:'nombre', width:255},
			           	{name:'Fecha',index:'fecha', width:170}
						],
			pager: jQuery('#pager1'),
			rowNum:0,
			rowList:[0,0,0],
			autowidth: false,
			width:1100,
			sortname: 'turno',
			viewrecords: true,
			id: 'divgrid',
			onSelectRow: function(id){
				consultaPDF(id);
				},
			sortorder: "desc"
		}).navGrid('#pager1',{edit:false,add:false,del:false});
		$("#gview_gridDetalleFrmPrincipal .ui-jqgrid-bdiv").css('height', '500px');

		
	}
	
	function consultaPDF(id){
		document.frmDoc.documentoId.value = id;
		document.frmDoc.submit();
	}
	
	function consultaArchivo(id){
		document.frmArch.archivoDigitalId.value = id;
		document.frmArch.submit();
	}
	
	/****** listener cadena de Custodia  ****/
	function asentarRegCadenaCustodia()
	{
		idWindowAsntarRegCadCus++;
		$.newWindow({id:"iframewindowAsntrRegCadCus" + idWindowAsntarRegCadCus, statusBar: true, posx:200,posy:50,width:1100, height:760,title:"Asentar registro de cadena de custodia", type:"iframe"});
	    $.updateWindowContent("iframewindowAsntrRegCadCus" + idWindowAsntarRegCadCus,'<iframe src="<%= request.getContextPath() %>/AsentarRegCadCustodia.do?consultaCadena=0&numeroExpediente='+numExpediente +'&operacion=1'+'&pantallaSolicitada='+pantallaSolicitada +'&idExpediente='+idExpedienteDoc+'" width="1100" height="760" />');
	}
	
	function consultarRegCadenaCustodia()
	{
		idWindowAsntarRegCadCus++;
		$.newWindow({id:"iframewindowCnsltrRegCadCus" + idWindowAsntarRegCadCus, statusBar: true, posx:200,posy:50,width:1100, height:760,title:"Cadena de custodia", type:"iframe"});
	    $.updateWindowContent("iframewindowCnsltrRegCadCus" + idWindowAsntarRegCadCus,'<iframe src="<%= request.getContextPath() %>/AsentarRegCadCustodia.do?consultaCadena=1&numeroExpediente='+numExpediente +'&operacion=2'+'&pantallaSolicitada='+pantallaSolicitada +'&idExpediente='+idExpedienteDoc+'" width="1100" height="760" />');
	}
	/****** FIN listener cadena de Custodia  ****/

	function gridCustodia(){

			jQuery("#gridDetalleFrmPrincipalUno").jqGrid({ 
				 
				datatype: "xml", 
				colNames:['','', '','', '','','', '','','' ], 
				colModel:[ 	{name:'caso',index:'caso', width:20}, 
							{name:'expediente',index:'expediente', width:20}, 
							{name:'defensor',index:'defensor', width:20}, 
							{name:'detenido',index:'detenido', width:20},
							{name:'delito',index:'delito', width:20},
							{name:'institucion',index:'institucion', width:20},
							{name:'fecha',index:'fecha', width:20},
							{name:'hora',index:'hora', width:20},
							{name:'abogado',index:'abogado', width:20},
							{name:'enterado',index:'enterado', width:20}
						],
				pager: jQuery('#pageruno'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc"
					
			}).navGrid('#pagerpageruno',{edit:false,add:false,del:false});

			// $("#gview_gridDetalleFrmPrincipalTres .ui-jqgrid-bdiv").css('height', '200px');	

	}
	
	function cerrarVentanaDocumento(id){
		var pantalla ="iframewindowGenerarDocumento";
		pantalla += id;
		$.closeWindow(pantalla);
	}
	
</script>

</head>

<body>
<table width="100%">
	<tr>
		<td>
			<div id="tabsPrincipal">
				<ul>
					<li><a href="#tabsconsultaprincipal-1">Solicitud</a></li>
					<li><a href="#tabsconsultaprincipal-5">Evidencia</a></li>
					<li><a href="#tabsconsultaprincipal-2"  onclick="cargaGridDocumentosDigitales();">Documentos asociados a la solicitud</a></li>
					<li><a href="#tabsconsultaprincipal-3">Actuaciones</a></li>
					<li><a href="#tabsconsultaprincipal-4" onclick="cargaGridDocumentosDigitalesPropios()">Documentos</a></li>
					<li class="tabTabsCadCus"><a href="#tabs-10" onclick="gridCustodia()">Cadena de custodia</a></li>
				</ul>
				
				<!--Comienza div para los datos de la solicitud-->
				<div id="tabsconsultaprincipal-1">
				
					<fieldset style="width: 700px;">
					<legend>Datos de la solicitud:</legend>
						<table width="100%" border="0" height="90%">
							<tr>
								<td align="right">
									Folio de la Solicitud:
								</td>
								<td>
									<input type="text" class="" size="50" maxlength="50" id="atnServPericialFolioSol" disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									Especialidad Solicitada:
								</td>
								<td>
									<input type="text" class="" size="50" maxlength="50" id="atnServPericialEspecialidadSol" disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									Tipo de Estudio Solicitado:
								</td>
								<td>
									<input type="text" class="" size="50" maxlength="50" id="atnServPericialTipoEstudioSol" disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<td align="right" >
									N&uacute;mero de Caso:
								</td>
								<td>
									<input type="text" class="" size="50" maxlength="50" id="atnServPericialNumCaso" disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									N&uacute;mero de Expediente:
								</td>
								<td>
									<input type="text" class="" size="50" maxlength="50" id="atnServPericialNumExp" disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									Nombre del Solicitante:
								</td>
								<td>
									<input type="text" class="" size="50" maxlength="50" id="atnServPericialNombSolicitante" disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									Fecha L&iacute;mite:
								</td>
								<td>
									<input type="text" class="" size="50" maxlength="50" id="atnServPericialFechLim" disabled="disabled"/>
								</td>
							</tr>
						</table>
					</fieldset>
					
				</div>
				<!-- TAB de EVIDENCIAS -->
				<div id="tabsconsultaprincipal-5">
					<table>
						<tr>
						<td>
						<fieldset style="width: 500px;">
						<legend>Evidencias Solicitadas</legend>
						<table>
							<tr>
								<td width="100%">
									<table id="gridDetalleCadenaCustodia"></table>
									<div id="pagerCadenaCustodia"></div>
								</td>
							</tr>
						</table>
						</fieldset>
						</td>
						<td>
						<fieldset style="width: 500px;">
						<legend>Recomendaciones</legend>
						<table width="100%" border="0" height="90%">
							<tr>
					          <td>
					            <textarea id="areaDescripcion" cols="45" rows="5" style="width: 500px; height:200px;" disabled="disabled"></textarea>
				              </td>
					        </tr>
						</table>
						</fieldset>
						</td>
						</tr>
					</table>
				</div>
				<!-- FIN TAB de EVIDENCIAS -->
				<!--Comienza div ver los documentos relacionados a la solicitud-->
				<div id="tabsconsultaprincipal-2">
					<table width="1150"  height="530" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td width="250" align="center" valign="top">
			                <table width="250"  height="530" border="0" cellspacing="0" cellpadding="0">
				              	<tr>
				                	<td width="250" align="center" valign="top">
				                        <table id="gridDocumentosDigitales"></table>
			                        	<div id="pagerGridDocumentosDigitales"></div>
			                        </td>
			                    </tr>
			                    <tr>
			                        <td width="250" align="center" valign="top">
			                        	<table id="gridDocumentosDigitalesNoPdfs"></table>
			                        	<div id="pagerGridDocumentosDigitalesNoPdfs"></div>
			                        </td>
			                    </tr>
			                </table>
			            </td>
		                <td width="900" align="center" valign="top">
		               	  <iframe id='visorDocsFrame' width="900" height="500" src="">		               	  
		               	  </iframe>
		                </td>
		              </tr>
		            </table>
		            
				</div>
				<!--Termina div para adjuntar documentos al enviar la solicitud-->
				
				<!--Comienza div para las actuaciones del perito-->
				<div id="tabsconsultaprincipal-3">
				
					<table width="1150" height="550" border="0" cellspacing="0" cellpadding="0">
					  <tr>
					    <td height="20" align="center">
					    	<fieldset style="width: 1150px;">
							  <legend>Actuaciones:</legend>
									<table width="100%" border="0" height="90%">
										<tr>
											<td width="50%">
												<select id="lstActuaciones" style="width:200px;">
													<option value="0">-Seleccione-</option>
										        </select>
											</td>
											<td width="50%">                                            
                                            	<button value="Adjuntar documento" id="btnAdjuntarDocumento" class="ui-button ui-corner-all ui-widget" onclick="abreVentanaAdjuntarDocumentoAExpediente()">Adjuntar documento</button>
											</td>
                                            
										</tr>
									</table>
							</fieldset>
					    </td>
					  </tr>
					  <tr>
					    <td height="530" valign="top">
					       
					       <!--Comienza Agregar Documentos-->
					    	<div id="agregarDocs">
					    	
					    		<table border="0">
					    			<tr>
					    				<td align="right"><span class="au av ra rc ta" ><strong>Anexar documento digital:</strong></span></td>
								        <td>
								        	<div id="divAdjuntarDoc" class="au av ra rc ta">
									        	<form id="anexarDocumentoForm" name="anexarDocumentoForm" action="<%= request.getContextPath() %>/registrarSolicitudPJATP.do" method="post" enctype="multipart/form-data" >
													<input type="file" name="archivoAdjunto" > 
													<input type="hidden" name="documentoId"/>
													
												</form>
											</div>
								        </td>
					    			</tr>
					    			<tr>
					    				<td></td>
					    				<td>
					    					<input id="anexarDoc" type="button" value="Anexar" onClick="anexarDocumento();" class="ui-button ui-corner-all ui-widget"/>
					    					<input id="limpiarAnexarDoc" type="button" value="Limpiar" class="ui-button ui-corner-all ui-widget"/>
					    				</td>
					    			</tr>
					    		</table>
					    		
					       	</div>
					       	<!--Comienza Agregar Documentos-->
					       	
					       	<!--Comienza realizar dictamen-->
					    	<div id="realizarDictamen">
								<!--Realizar Dictamen-->
					       	</div>
					       	<!--Comienza realizar dictamen-->
					       	
					       	<!--Comienza realizar informe-->
					    	<div id="realizarInforme">
								<!--Realizar Informe-->
					       	</div>
					       	<!--Comienza realizar informe-->
					       	
					    </td>
					  </tr>
					</table>
										
				</div>
				<!--Termina div para las actuaciones del perito-->
				
				<!--Comienza div para ver los documentos propios del perito-->
				<div id="tabsconsultaprincipal-4">
					
					<table width="1150"  height="530" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td width="250" align="center" valign="top">
	                        <table id="gridDocumentosDigitalesPropios"></table>
	                        <div id="pagerGridDocumentosDigitalesPropios"></div>	                                  
		                </td>
		              </tr>
		            </table>
		            
		         <form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
					<input type="hidden" name="documentoId" />
				</form>
				
				<form name="frmArch" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
					<input type="hidden" name="archivoDigitalId" />
				</form>
					
				</div>
				<!--Termina div para ver los documentos propios del perito-->
				
				<!--Termina div para ver los documentos propios del perito-->
				<div id="tabs-10" class="tabTabsCadCus">
					 <input type="button" class="ui-button ui-corner-all ui-widget" id="btnCadCusNuevaCadCus" style="width: 250px;" value="Crear nueva cadena de custodia"/><br/><br/>
					 <input type="button" class="ui-button ui-corner-all ui-widget" id="btnCadCusConsultaCadCus" style="width: 250px;" value="Consultar cadena de custodia"/><br/><br/>    
	  				 <!--  <input type="button" class="ui-button ui-corner-all ui-widget" id="btnCadCusRegEslabones" style="width: 250px;" value="Registrar eslabones"/> <br/><br/>
	  				 <input type="button" class="ui-button ui-corner-all ui-widget" id="btnCadCusRepEvidencias" style="width: 250px;" style="width: 250px;" value="Reporte de evidencias"/> <br/><br/>
	  				 <input type="button" class="ui-button ui-corner-all ui-widget" id="btnCadCusElabOficio" style="width: 250px;" value="Elaborar oficio para fijaci&oacute;n y preservaci&oacute;n"/><br/><br/>  
	   				 <input type="button" class="ui-button ui-corner-all ui-widget" id="btnCadCusAdmDestino" style="width: 250px;" value="Administrar destino legal de evidencia"/>
	   				 -->
				</div>		
			</div>
		</td>
	</tr>
</table>

</body>
</html>