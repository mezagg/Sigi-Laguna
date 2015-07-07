<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.TipoTurno"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.CategoriasActividad"%>
<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%> 

<% 
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	RolDTO rolDTO = usuario.getRolACtivo().getRol();
 %>
<html>
<head>
	
	<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />				
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>  
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" />	

	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimeout.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimer.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/layout_complex.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/reloj.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.zweatherfeed.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>			
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/sesion.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<jsp:include page="/WEB-INF/paginas/defensoria/moduloAsesoriaLegal.jsp"/>
	<script type="text/javascript">

	var contextoPagina = "${pageContext.request.contextPath}";
	var sesionActiva = '<%= (request.getSession().getAttribute(LoginAction.KEY_SESSION_USUARIO_FIRMADO)!=null)%>';
	var tiempoActiva = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoBloqueoSesion()%>';
	estaSesionActiva();
	//DETERMINAR SI NO IMPACTA ESTE CAMBIO DE AREA
	//  ( (mx.gob.segob.nsjp.dto.usuario.UsuarioDTO)request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO") ).setAreaActual(new mx.gob.segob.nsjp.dto.institucion.AreaDTO(16L));

	/*variables globales*/
	//Compartidas con el moduloAsesoriaLegal.jsp
	var folioSolicitud 		= "";
	var numExpediente 		= "";
	var idExpediente 		= "";
	var numeroExpedienteId	= "";
	var documentoId			= "";
	var turnoEnCurso;
	
	
	var outerLayout, innerLayout;
	
	
	var acuse = "";
	var idSolicitud="";
	var idFuncionario="";
	var defensoria=1; //Para Turno (vAdmonRecepcion.jsp)
	var tipoBusqueda;
	var habilitarAsesoriaLegal = '0';
	
	//Uniformando variables
	var avisoDetencion =   "avisosDetencion";
	var solAtTemprana =    "solAtencionTemprana";
	var solAsesoria =      "solAsesorias";
	var solPJ =            "solicitudPoderJudicial";
	var avisoDetencionId =   1; // SOLICITUD DE AVISO DE PERSONA DETENIDA
	var solAtTempranaId =    2; // SOLICITUD DE DEFENSOR ATENCIÓN TEMPRANA
	var avisoDesignacionId = 3; // AVISO DESIGNACION
	var solPJId =            4; // SOLICITUD DE DEFENSOR PODER JUDICIAL
	var solAsesoriaId =      5; // SOLICITUD DE ASESORIA LEGAL
	
	$(document).ready(function() {
		jQuery(document).ajaxStop(jQuery.unblockUI);
		
		//Validar si es necesario habilitar el modulo de AL
		habilitarModuloAsesoriaLegalDefATE();
		
		//obtenemos el tiempo de las alarmas y ponemos en marcha el timer		
		var tiempo='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoRevisionAlarmas()%>';
		setInterval(muestraAlerta, tiempo);

		outerLayout = $("body").layout( layoutSettings_Outer );
		//pinta turnos por default
		ocultaMuestraGrids('6');
		//asocia funcion al boton de queja ciudadana
		$("#tbarBtnQuejaCiudadana").click(muestraQuejaCiudadana);
		//ajusta el tamaño de el acordeon izquierdo	
		$("#accordionmenuprincipal").accordion({  fillSpace: true });
		//ajusta el tamaño de el acordeon izquierdo				
		$("#accordionmenuderprincipal").accordion({ fillSpace: true});
		$("#accordionmenuderprincipal").accordion( "option", "icons", null );
		//vista de arbol para las solicitudes
		$("#seccion1tree").treeview();
		$("#seccion6tree").treeview();
		//asocia agenda
		$("#controlAgenda").click(creaAgenda);
		
		//asocia funcion a boton de buscar expediente
		//$("#buscarExpediente").click(buscarExpediente);

		//asocia funcion a boton de buscar caso		
		//$("#buscarCaso").click(buscarCaso);
			
		//abre pantalla para la recepcion de una solicitud
		$("#tbarBtnLlamarTurnoSolicitud").click(cambiaTipoBusquedaSolicitud);

		//abre pantalla para la recepcion de una solicitud
		$("#tbarBtnLlamarTurnoAsesoria").click(cambiaTipoBusquedaAsesoria);
		
		//abre ventana de chat						
		$("#dialogoChat").dialog({ autoOpen: false, 
			modal: true, 
			title: 'Chat', 
			dialogClass: 'alert',
			modal: true,
			width: 500 ,
			maxWidth: 600,
			buttons: {"Cancelar":function() {
								$(this).dialog("close");
							}
						} 
		});		

		//abre ventana modal para finalizar sesion
		$( "#dialog-logout" ).dialog({
			autoOpen: false,
			resizable: false,
			//height:290,
			//width:300,
			modal: true,
			buttons: {
				"Aceptar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
					document.location.href="<%= request.getContextPath()%>/Logout.do";
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
				}
			}
		});	
											
		/*******************************
		 ***  CUSTOM LAYOUT BUTTONS  ***
		 *******************************
		 *
		 * Add SPANs to the east/west panes for customer "close" and "pin" buttons
		 *
		 * COULD have hard-coded span, div, button, image, or any element to use as a 'button'...
		 * ... but instead am adding SPANs via script - THEN attaching the layout-events to them
		 *
		 * CSS will size and position the spans, as well as set the background-images
		 */

		// BIND events to hard-coded buttons in the NORTH toolbar
		outerLayout.addToggleBtn( "#tbarBtnHeaderZise", "north" );
		// save selector strings to vars so we don't have to repeat it
		// must prefix paneClass with "body > " to target ONLY the outerLayout panes
		var westSelector = "body > .ui-layout-west"; // outer-west pane
		var eastSelector = "body > .ui-layout-east"; // outer-east pane

		 // CREATE SPANs for pin-buttons - using a generic class as identifiers
		$("<span></span>").addClass("pin-button").prependTo( westSelector );
		$("<span></span>").addClass("pin-button").prependTo( eastSelector );
		// BIND events to pin-buttons to make them functional
		outerLayout.addPinBtn( westSelector +" .pin-button", "west");
		outerLayout.addPinBtn( eastSelector +" .pin-button", "east" );

		 // CREATE SPANs for close-buttons - using unique IDs as identifiers
		$("<span></span>").attr("id", "west-closer" ).prependTo( westSelector );
		$("<span></span>").attr("id", "east-closer").prependTo( eastSelector );
		$('#test').weatherfeed(['MXDF0132']);
		// BIND layout events to close-buttons to make them functional
		outerLayout.addCloseBtn("#west-closer", "west");
		outerLayout.addCloseBtn("#east-closer", "east");
		createInnerLayout();
		
		var ambiente='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAmbiente()%>';
		if(ambiente == undefined || ambiente == "undefined"){
			ambiente = "";
		}
		$("#ambienteLb").html('<strong><big>'+ambiente+'</big></strong>');
		
		//llama funcion para mostrar gadgets
		muestraGadgets();
	});
	//Fin ready

	 
	//abre la ventana para registrar una asesoria legal
	function detalleEnvioAsesoriaLegal(idSolicitud) {
		$.newWindow({id:"iframewindowAsesoriaLegal", statusBar: true, posx:50,posy:110,width:1090,height:570,title:"Detalle Solicitud Asesoría Legal", type:"iframe"});
    	$.updateWindowContent("iframewindowAsesoriaLegal",'<iframe src="<%=request.getContextPath()%>/registrarAsesoriaLegal.do?solicitudId='+idSolicitud+'" width="1090" height="570" />');		
	} 

		//abre la ventana para buscar un expediente
		//function buscarExpediente() {
		//	$.newWindow({id:"iframewindowBuscarExpediente", statusBar: true, posx:50,posy:110,width:653,height:400,title:"Buscar Expediente", type:"iframe"});
	    //	$.updateWindowContent("iframewindowBuscarExpediente",'<iframe src="<%=request.getContextPath()%>/buscarExpediente.do" width="653" height="400" />');		
		//}
		
		//abre la ventana para buscar un caso
		//function buscarCaso() {
		//	$.newWindow({id:"iframewindowBuscarCaso", statusBar: true, posx:50,posy:110,width:653,height:400,title:"Buscar Caso", type:"iframe"});
	    //	$.updateWindowContent("iframewindowBuscarCaso",'<iframe src="<%=request.getContextPath()%>/buscarCaso.do" width="653" height="400" />');		
		//}
		
		//abre la ventana para mostrar el detalle de designaciones
		function mostrarDetalleDesignaciones(rowid, tipoSolicitud, idNumeroExpediente){
			var idDocumento = rowid.split("-")[0];
			var params = "?tipoSolicitud="+tipoSolicitud;
			var titulo = "Expediente: " + numExpediente;
			switch(tipoSolicitud){
				case avisoDetencionId:
					titulo="Aviso de Persona Detenida ";
					break;
				case solAtTempranaId:
					titulo="Expediente: " + numExpediente;
					break;
				case avisoDesignacionId:
					titulo="Expediente: " + numExpediente;
					break;
				case solPJId:
					titulo="Solicitud de Defensor ";
					break;
				case solAsesoriaId:
					titulo="Asesoría: " + numExpediente;
					break;
			}
			
			params += "&idDocumento="+idDocumento;
			params += "&numeroExpedienteId="+idNumeroExpediente;
			params += "&asignarDefensor=0";
			params += "&esConsultaSolicitud=false"; //Identificar de que grid se recarga, ver abrirVisorDefensoria (). Tambien llamado en indexDefensoriaCoordinador.jsp
			
			$.newWindow({id:"iframewindowDetalleDesignaciones", statusBar: true, posx:50,posy:111,width:925,height:514,title:titulo, type:"iframe"});
		    $.updateWindowContent("iframewindowDetalleDesignaciones",'<iframe src="<%=request.getContextPath()%>/mostrarDetalleATDEF.do'+params+'" width="925" height="514" />');		
		    $("#" +"iframewindowDetalleDesignaciones"+ " .window-maximizeButton").click();
			}
		//abre la ventana para la agenda
		function creaAgenda() {
			$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
		    $.updateWindowContent("iframewindowagenda",'<iframe src="<%=request.getContextPath()%>/InicioAgenda.do" width="1150" height="600" />');	
		    $("#" +"iframewindowagenda"+ " .window-maximizeButton").click();		
		}

		
		//abre la ventana para ejecutar el chat
		function ejecutaChat() {
			$("#dialogoChat").dialog( "open" );
		}
		//@deprecated
		//genera numero de acuse
		function cargaAcuse(tipoGenera, idNumeroExpediente){
			var params = "";
			params += "?tipo="+tipoGenera;
			if(idNumeroExpediente != undefined && idNumeroExpediente != null){
				params += "&idExpediente="+idNumeroExpediente;
			}
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/GeneraNumeroAcuse.do'+params+'',
				data: '',
				dataType: 'xml',
				async: false,
				success: function(xml){
					 $(xml).find('solicitudDefensorDTO').each(function(){
						 acuse =$(xml).find('folioDocumento').text();
						 numExpediente =$(xml).find('numeroExpediente').text();
						 idExpediente =$(xml).find('expedienteId').text();
						 numeroExpedienteId=$(xml).find('numeroExpedienteId').text();
						 documentoId =$(xml).find('documentoId').text();					
					});
				}
			});
		}
		//abre pantalla para introducir los datos de la solicitud
		function generaNumeroAcuse(exp){
			if(exp != null && exp != undefined){
				params = "?idExpediente="+exp;
			}
			$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:50,posy:50,width:1080,height:700,title:"Acuse de Atencion: "+acuse+" para el Exp: "+ numExpediente, type:"iframe"});
		    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%=request.getContextPath()%>/DatosGeneralesSolicitud.do'+params +'" width="1080" height="700" />');	
		}
		//abre pantalla detalle de los datos de la solicitud
		function detalleEnvioSolicitud(rowid){
			$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:50,posy:50,width:1080,height:640,title:"Detalle de la Solicitud de Defensor", type:"iframe"});
		    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%=request.getContextPath()%>/DatosGeneralesSolicitud.do?solicitudId='+rowid+'" width="898" height="498" />');	
		}
		//cierra pantalla de solicitud
		
		//@deprecated
		function resBuscarCaso(){
			$windowCloseButton.click();
			cargaAcuse("solicitud");
			generaNumeroAcuse(idExpediente);		
		}	
		//registra una solicitud asociada a un numero de expediente
		function registrarSolicitudAsociada(idNumeroExpediente,numExpediente){
			$windowCloseButton.click();
			cargaAcuse("solicitud", idNumeroExpediente);
			generaNumeroAcuse(idExpediente);		
		}	
		/*Habilita o deshabilita el elemento principal de la pagina segun la opcion seleccionada en el acordeon izquierdo*/
		function ocultaMuestraGrids(idGrid) {
			$("#divGenerarTurno").css("display", "none");
			
			$("#divGridDesignaciones").css("display", "none");
			$("#divGridAvisosDeDetencion").css("display", "none");
			$("#divGridSolicitudAtencionTemprana").css("display", "none");
			$("#divGridSolicitudesDeDefensor").css("display", "none");
			$("#divGridPoderJudicial").css("display", "none");
			$("#divGridExpedientesSubordinados").css("display", "none");
			$("#divGridAsesorias").css("display", "none");
			$("#divGridNoEnvioSolicitud").css("display", "none");
			$("#divGridNoEnvioAsesoria").css("display", "none");
			
			switch(parseInt(idGrid)){
				case 1:	//Manipula el grid de designaciones
						$("#divGridDesignaciones").css("display", "block");
						gridDesignaciones();
						break;
					
				case 2:	//Manipula el grid de avisos de  detencion
						$("#divGridAvisosDeDetencion").css("display", "block");
						gridAvisosDeDetencion();
						break;
				case 3: //Manipula el grid de solicitudes de defensor de atencion temprana
						//alert("NO HACE NADA");
						//AGA
						//$("#divGridSolicitudAtencionTemprana").css("display", "block");
						//gridSolAtencionTemprana();
						$("#divGridSolicitudesDeDefensor").css("display", "block");
						$("#divGridSolicitudesDeDefensor").show();
						//cargaGridSolicitudesDefensor('<%=Instituciones.DEF.getValorId()%>','<%=EstatusSolicitud.ABIERTA.getValorId()%>');
						break;
				case 4:	//Manipula el grid de solicitudes de defensor de poder judicial
						$("#divGridPoderJudicial").css("display", "block");
						gridSolPoderJudicial();
						break;
				case 5:	//Manipula el grid de expedientes
						$("#divGridExpedientesSubordinados").css("display", "block");
						break;
				case 7:	//Manipula grid de asesorias
						$("#divGridAsesorias").css("display", "block");
						//gridAsesoria();
						break;
				case 6:	//Manipula div de turnos
						$("#divGenerarTurno").css("display", "block");
						break;
				case 10:	//Manipula grid de no envio solicitud
						$("#divGridNoEnvioSolicitud").css("display", "block");
						gridNoEnvioSolicitud();
						break;
				case 9:	//Manipula grid de no envio asesoria
						$("#divGridNoEnvioAsesoria").css("display", "block");
						gridNoEnvioAsesoria();
						break;
			}
			restablecerPantallas();
		}
		
		/*
		* Funcion que se encarga de hacer el ajuste de los grid al centro
		* Definida en el comun.js
		*/
		function restablecerPantallas(){
			
			ajustarGridAlCentro($("#gridDesignaciones")); //**
			ajustarGridAlCentro($("#gridAvisosDeDetencion"));
			ajustarGridAlCentro($("#gridSolicitudAtencionTemprana"));
			ajustarGridAlCentro($("#gridSolicitudesDeDefensor"));
			ajustarGridAlCentro($("#gridPoderJudicial"));
			ajustarGridAlCentro($("#gridExpedientesSubordinados"));
			ajustarGridAlCentro($("#gridAsesorias"));
			ajustarGridAlCentro($("#gridNoEnvioSolicitud"));
			ajustarGridAlCentro($("#gridNoEnvioAsesoria"));
		}
		
		
		/* Grid designaciones */
		//deprecated
		function gridDesignaciones() {
	
			jQuery("#gridDesignaciones").jqGrid({
				url:'<%= request.getContextPath()%>/recibirDesignaciones.do', 				 
				datatype: "xml", 
				colNames:['Instituci&oacute;n','Caso','Expediente','Defensor','Fecha - Hora de Designaci&oacute;n'], 
				colModel:[ 
							{name:'origen',index:'2001', width:100},
				           	{name:'caso',index:'2002', width:100},
				           	{name:'expediente',index:'2003', width:100},
							{name:'defensor',index:'2006', width:120},
							{name:'FHDesignacion',index:'2007', width:170}
						],
			pager: jQuery('#pagerGridDesignaciones'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			autowidth: true,
			autoheight:true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			
			ondblClickRow: function(rowid) {	
				var ret =jQuery("#gridDesignaciones").jqGrid('getRowData',rowid); 
			    numExpediente = ret.expediente;	
				
				mostrarDetalleDesignaciones(rowid,avisoDesignacionId,"");
			}
		}).navGrid('#pagerGridDesignaciones',{edit:false,add:false,del:false});
		$("#gview_gridDesignaciones .ui-jqgrid-bdiv").css('height', '525px');
		$("#gridDesignaciones").trigger("reloadGrid"); 
		
		}
		
		/* Grid Avisos de detencion */
		function gridAvisosDeDetencion() {	
			jQuery("#gridAvisosDeDetencion").jqGrid({
						url : '<%= request.getContextPath()%>/SolicitudesNoAtendidas.do', 
						datatype: "xml", 
						colNames:['Folio','Caso','Imputado:','Delito(s)','<bean:message key="agencia"/>','Fecha-Hora de detención','Fecha-Hora de aviso' ], 
						colModel:[ 	{name:'folio',index:'2008', width:150},
						           	{name:'caso',index:'2002', width:180},
						           	{name:'imputado',index:'2009', width:150},
						           	{name:'delito',index:'2004', width:200},
						           	{name:'mp',index:'2010', width:150},
						           	{name:'fechaHoraDetencion',index:'2011', width:150},
						           	{name:'fechaHoraAviso',index:'2012', width:150},
						           	
								],			
			pager: jQuery('#pagerAvisosDeDetencion'),
			rowNum:25,
			rowList:[10,20,30,40,50,60],
			autowidth: true,
			autoheight:true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {	
								
				mostrarDetalleDesignaciones(rowid,avisoDetencionId,"");
			}
		}).navGrid('#pagerAvisosDeDetencion',{edit:false,add:false,del:false});
		$("#gview_gridAvisosDeDetencion .ui-jqgrid-bdiv").css('height', '450px');
		$("#gridAvisosDeDetencion").trigger("reloadGrid"); 
		
		}
		/* Grid Solcitudes de Atencion temprana */
		function gridSolAtencionTemprana(){
			jQuery("#gridSolicitudAtencionTemprana").jqGrid({ 
				url:'<%= request.getContextPath()%>/SolicitudesNoAtendidasAtencionTemprana.do', 
				datatype: "xml", 
				colNames:['Folio de Solicitud','Caso','Expediente','Para Quien se Sólicita: ','Detenido','Fecha y Hora de Solicitud','Numero Expediente Id'], 
				colModel:[ 	{name:'Folio de Solicitud',index:'2013', width:185,align:"center"},
				           	{name:'Caso',index:'2002', width:165,align:"center"},
				           	{name:'Expediente',index:'2003', width:185,align:"center"},
				           	{name:'ParaQuienseSolicita',index:'2009', width:215,align:"center"},
				           	{name:'Detenido',index:'2005', width:185,align:"center"},
				           	{name:'FHSolicitud',index:'2013', width:185,align:"center"},
				        	{name:'numeroExpedienteId',index:'2015', width:185,align:"center", hidden:true}
				           					           	
						],
				pager: jQuery('#pagGridSolicitudAtencionTemprana'),
				rowNum:10,
				rowList:[10,20,30,40,50,60],
				autowidth: true,
				//autoheight:true,
				sortname: 'numeroExpediente',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					var ret =jQuery("#gridSolicitudAtencionTemprana").jqGrid('getRowData',rowid); 
					var numeroExpedienteId = ret.numeroExpedienteId;
					    numExpediente = ret.Expediente;									
					mostrarDetalleDesignaciones(rowid,solAtTempranaId,numeroExpedienteId);
				}			
			}).navGrid('#pagGridSolicitudAtencionTemprana',{edit:false,add:false,del:false});
			$("#gview_gridSolicitudAtencionTemprana .ui-jqgrid-bdiv").css('height', '525px');
			jQuery("#gridSolicitudAtencionTemprana").trigger("reloadGrid");
		}

				
		/* Grid Solcitudes No Enviadas */
		function gridNoEnvioSolicitud(){
			jQuery("#gridNoEnvioSolicitud").jqGrid({ 
				url:'<%= request.getContextPath()%>/solicitudesNoEnviadasAtencionTemprana.do', 
				datatype: "xml", 
				colNames:['Caso','Expediente','Para Quien Solicita','Etapa'], 
				colModel:[ 	{name:'caso',index:'1', width:185,align:"center"},
				         	{name:'expediente',index:'2', width:185,align:"center"},
				           	{name:'interesado',index:'3', width:215,align:"center"},
				          	{name:'etapa',index:'4', width:185,align:"center"}
						],
				pager: jQuery('#pagGridNoEnvioSolicitud'),
				rowNum:20,
				rowList:[20,40,60,80,100],
				autowidth: true,
				//autoheight:true,
				sortname: '2',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					detalleEnvioSolicitud(rowid);
				}			
			}).navGrid('#pagGridNoEnvioSolicitud',{edit:false,add:false,del:false});
			$("#gview_gridNoEnvioSolicitud .ui-jqgrid-bdiv").css('height', '525px');
			jQuery("#gridNoEnvioSolicitud").trigger("reloadGrid");
		}
		
		/* Grid Asesoria No Enviadas */
		function gridNoEnvioAsesoria(){
			jQuery("#gridNoEnvioAsesoria").jqGrid({ 
				url:'<%= request.getContextPath()%>/solicitudesNoEnviadasAsesoria.do', 
				datatype: "xml", 
				colNames:['Caso','Expediente','Interesado','Etapa'], 
				colModel:[ 	{name:'caso',index:'1', width:185,align:"center"},
				         	{name:'expediente',index:'2', width:185,align:"center"},
				           	{name:'interesado',index:'3', width:215,align:"center"},
				          	{name:'etapa',index:'4', width:185,align:"center"}
						],
				pager: jQuery('#pagGridNoEnvioAsesoria'),
				rowNum:20,
				rowList:[20,40,60,80,100],
				autowidth: true,
				//autoheight:true,
				sortname: '2',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					detalleEnvioAsesoriaLegal(rowid);
				}			
			}).navGrid('#pagGridNoEnvioAsesoria',{edit:false,add:false,del:false});
			$("#gview_gridNoEnvioAsesoria .ui-jqgrid-bdiv").css('height', '525px');
			jQuery("#gridNoEnvioAsesoria").trigger("reloadGrid");
		}
		
		//grid de solicitudes de poder judicial
		function gridSolPoderJudicial(){			
			jQuery("#gridPoderJudicial").jqGrid({ 
				url:'<%= request.getContextPath()%>/SolicitudesNoAtendidasPoderJudicial.do', 
				datatype: "xml", 
				colNames:['Folio','Caso','Imputado ','Delito(s)','Tipo de Audiencia','Fecha-Hora de audiencia','Detenido','Fecha-Hora solicitud'], 
				colModel:[ 	{name:'folio',index:'2013', width:130,align:"center"},
				           	{name:'caso',index:'2002', width:120,align:"center"},
				           	{name:'imputado',index:'2009', width:150,align:"center"},
				           	{name:'Delitos',index:'2004', width:130,align:"center", cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }},
				           	{name:'Tipo de Audiencia',index:'2017', width:150,align:"center"},
				           	{name:'fechaHoraAudiencia',index:'2018', width:150,align:"center"},
				           	{name:'detenido',index:'2005', width:130,align:"center"},
				         	{name:'fechaHoraSolicitud',index:'2014', width:160,align:"center"}
				           	
						],
				pager: jQuery('#pagerPoderJudicial'),
				rowNum:10,
				rowList:[10,20,30,40,50,60],
				autowidth: true,
				autoheight:true,
				viewrecords: true,
				ondblClickRow: function(rowid) {

					mostrarDetalleDesignaciones(rowid,solPJId,"");

				}			
			}).navGrid('#pagerPoderJudicial',{edit:false,add:false,del:false});
			$("#gview_gridPoderJudicial .ui-jqgrid-bdiv").css('height', '450px');
			jQuery("#gridPoderJudicial").trigger("reloadGrid");
		}
	
	
		//Ventana de captura de queja ciudadana
		function muestraQuejaCiudadana(){
			
			$.newWindow({id:"iframewindowQuejaCiudadana", statusBar: true, posx:50,posy:111,width:1023,height:473,title:"Queja Ciudadana", type:"iframe"});
		    $.updateWindowContent("iframewindowQuejaCiudadana",'<iframe src="<%=request.getContextPath()%>/quejaCiudadana.do" width="1023" height="473" />'); 
		}

		var isWindowOpen = false;
		
		function actualizaEtapaTemp() {
			var param ="";
			
			param += 'etapaExpediente='+ $('#etapaOptionDEATT option:selected').val();
			param+= '&documentoId='+documentoId1;
			   $.ajax({
			     type: 'POST',
			     url: '<%= request.getContextPath()%>/ActualizarEtapaExpedienteSolicitudDefensoria.do',
				 data: param,
				 dataType: 'xml',
				 success: function(xml){
				  }
				});
			 }
	
	/*
		*#######################
		* INNER LAYOUT SETTINGS
		*#######################
		*
		* These settings are set in 'list format' - no nested data-structures
		* Default settings are specified with just their name, like: fxName:"slide"
		* Pane-specific settings are prefixed with the pane name + 2-underscores: north__fxName:"none"
		*/
		layoutSettings_Inner = {
			applyDefaultStyles:				false // basic styling for testing & demo purposes
		,	minSize:						50 // TESTING ONLY
		,	spacing_closed:					14
		,	north__spacing_closed:			8
		,	south__spacing_closed:			8
		,	north__togglerLength_closed:	-1 // = 100% - so cannot 'slide open'
		,	south__togglerLength_closed:	-1
		,	fxName:							"slide" // do not confuse with "slidable" option!
		,	fxSpeed_open:					1000
		,	fxSpeed_close:					2500
		,	fxSettings_open:				{ easing: "easeInQuint" }
		,	fxSettings_close:				{ easing: "easeOutQuint" }
		,	north__fxName:					"none"
		,	south__fxName:					"drop"
		,	south__fxSpeed_open:			500
		,	south__fxSpeed_close:			1000
		//,	initClosed:						true
		,	center__minWidth:				10
		,	center__minHeight:				200
		};
		/*
		*#######################
		* OUTER LAYOUT SETTINGS
		*#######################
		*
		* This configuration illustrates how extensively the layout can be customized
		* ALL SETTINGS ARE OPTIONAL - and there are more available than shown below
		*
		* These settings are set in 'sub-key format' - ALL data must be in a nested data-structures
		* All default settings (applied to all panes) go inside the defaults:{} key
		* Pane-specific settings go inside their keys: north:{}, south:{}, center:{}, etc
		*/
		var layoutSettings_Outer = {
			name: "outerLayout" // NO FUNCTIONAL USE, but could be used by custom code to 'identify' a layout
			// options.defaults apply to ALL PANES - but overridden by pane-specific settings
		,	defaults: {
				size:					"auto"
			,	minSize:				50
			,	paneClass:				"pane" 		// default = 'ui-layout-pane'
			,	resizerClass:			"resizer"	// default = 'ui-layout-resizer'
			,	togglerClass:			"toggler"	// default = 'ui-layout-toggler'
			,	buttonClass:			"button"	// default = 'ui-layout-button'
			,	contentSelector:		".content"	// inner div to auto-size so only it scrolls, not the entire pane!
			,	contentIgnoreSelector:	"span"		// 'paneSelector' for content to 'ignore' when measuring room for content
			,	togglerLength_open:		35			// WIDTH of toggler on north/south edges - HEIGHT on east/west edges
			,	togglerLength_closed:	35			// "100%" OR -1 = full height
			,	hideTogglerOnSlide:		true		// hide the toggler when pane is 'slid open'
			,	togglerTip_open:		"Cerrar el Panel"
			,	togglerTip_closed:		"Abrir el Panel"
			,	resizerTip:				"Ajustar el Panel"
			//	effect defaults - overridden on some panes
			,	fxName:					"slide"		// none, slide, drop, scale
			,	fxSpeed_open:			750
			,	fxSpeed_close:			1500
			,	fxSettings_open:		{ easing: "easeInQuint" }
			,	fxSettings_close:		{ easing: "easeOutQuint" }
		}
		,	north: {
				spacing_open:			1			// cosmetic spacing
			,	togglerLength_open:		0			// HIDE the toggler button
			,	togglerLength_closed:	-1			// "100%" OR -1 = full width of pane
			,	resizable: 				false
			,	slidable:				false
			
			//	override default effect
			,	fxName:					"none"
			}
		,	south: {
				maxSize:				200
			,	togglerLength_closed:	-1			// "100%" OR -1 = full width of pane
			,	slidable:				false		// REFERENCE - cannot slide if spacing_closed = 0
			,	initClosed:				true
			}
		,	west: {
				size:					250
			,	spacing_closed:			21			// wider space when closed
			,	togglerLength_closed:	21			// make toggler 'square' - 21x21
			,	togglerAlign_closed:	"top"		// align to top of resizer
			,	togglerLength_open:		0			// NONE - using custom togglers INSIDE west-pane
			,	togglerTip_open:		"Cerrar el Panel"
			,	togglerTip_closed:		"Abrir el Panel"
			,	resizerTip_open:		"Ajustar el Panel"
			,	slideTrigger_open:		"click" 	// default
			,	initClosed:				false
			//	add 'bounce' option to default 'slide' effect
			,	fxSettings_open:		{ easing: "" }
			,	west__onresize:		function () { $("#accordionmenuprincipal").accordion("resize"); }
			}
		,	east: {
				size:					250
			,	spacing_closed:			21			// wider space when closed
			,	togglerLength_closed:	21			// make toggler 'square' - 21x21
			,	togglerAlign_closed:	"top"		// align to top of resizer
			,	togglerLength_open:		0 			// NONE - using custom togglers INSIDE east-pane
			,	togglerTip_open:		"Cerrar el Panel"
			,	togglerTip_closed:		"Abrir el Panel"
			,	resizerTip_open:		"Ajustar el Panel"
			,	slideTrigger_open:		"mouseover"
			,	initClosed:				false
			//	override default effect, speed, and settings
			,	fxName:					"drop"
			,	fxSpeed:				"normal"
			,	fxSettings:				{ easing: "" } // nullify default easing
			,	est__onresize:		function () { $("#accordionmenuderprincipal").accordion("resize"); }		
			}
		,	center: {
				paneSelector:			"#mainContent" 			// sample: use an ID to select pane instead of a class
			,	onresize:				"innerLayout.resizeAll"	// resize INNER LAYOUT when center pane resizes	
			,	minWidth:				0
			,	minHeight:				0
			//,	initClosed:				false
			,	onresize_end:			function () { $("#gridDesignaciones").setGridWidth($("#mainContent").width() - 5, true);
													$("#gridAvisosDeDetencion").setGridWidth($("#mainContent").width() - 5, true);
													$("#gridSolicitudAtencionTemprana").setGridWidth($("#mainContent").width() - 5, true);
													$("#gridSolicitudesDeDefensor").setGridWidth($("#mainContent").width() - 5, true);
													$("#gridPoderJudicial").setGridWidth($("#mainContent").width() - 5, true);
													//$("#gridSubordinados").setGridWidth($("#mainContent").width() - 5, true);
													$("#gridExpedientesSubordinados").setGridWidth($("#mainContent").width() - 5, true);
													$("#gridAsesorias").setGridWidth($("#mainContent").width() - 5, true);
													$("#gridNoEnvioSolicitud").setGridWidth($("#mainContent").width() - 5, true);
													$("#gridNoEnvioAsesoria").setGridWidth($("#mainContent").width() - 5, true);
										}
		}
		};


		function visorLeyesCodigos() {
			
			$.newWindow({id:"iframewindowRestaurativa", statusBar: true, posx:50,posy:111,width:809,height:468,title:"Leyes y C&oacute;digos", type:"iframe"});
		    $.updateWindowContent("iframewindowRestaurativa",'<iframe src="<%= request.getContextPath() %>/detalleLeyesyCodigos.do" width="800" height="500" />');
		    		
		}
		/*
		*Funcion que llama a la funcionalidad para generar un visualizador de imagen  $('#imageViewer').click(generaVisorGraficaView);
		*/
		function generaVisorGraficaView() {
			$.newWindow({id:"iframewindowWindowImageViewer", statusBar: true, posx:63,posy:111,width:1140,height:400,title:"Visor de imagenes", type:"iframe"});
		    $.updateWindowContent("iframewindowWindowImageViewer",'<iframe src="<%=request.getContextPath()%>/VisorGraficas.do" width="1140" height="400" />');
		    		
		}	

		function mostrarDetalleSolicitudNoEnviada(rowid){
			customAlert(rowid);
		}
		
		/******************************************************    FUNCIONES PARA LAS ALARMAS      ***************************************************/
		function muestraAlerta(){
			var op="";
			var idAlerta="";

			$.ajax({
	   		type: 'POST',
	    		url: '<%=request.getContextPath()%>/consultarAlarmas.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){
	    			$(xml).find('alertaDTO').each(function(){
	    				op=$(this).find('esAplaza').text();
	    				idAlerta=$(this).find('alertaId').text();
	    				var nombre=$(this).find('nombre').first().text();
	    				$("#mensajeAlarma").html(nombre);
	    				
	        			llamaraCambia(op,idAlerta);
					});
	   		}
	   	});
			
			

		}

		function cambiarEstausAlarma(estatus,tiempo,id,unidad){
			$.ajax({
		   		type: 'POST',
		    		url: '<%=request.getContextPath()%>/actualizarAlarma.do?idAlerta='+id+'&estatus='+estatus+'&tiempo='+tiempo+'&unidad='+unidad,
		    		data: '',
		    		dataType: 'xml',
		    		async: false,
		    		success: function(xml){
		    			if(estatus=="posponer")
		    			{
		    				alert("Alarma pospuesta");
		    			}
		    			else if(estatus=="cancelar")
		    			{
		    				alert("Alarma cancelada");
		    			}
		    			else
		    			{
		    				alert("Alarma aceptada");
		    			}
		   		}
			});
		}

		function llamaraCambia(op,idAlerta){
			if(op!="false"){		
				$( "#dialog-alarm" ).dialog({
					resizable: false,
					height:150,
					width:300,
					modal: true,
					buttons: {
						"Aceptar": function() {
							$( this ).dialog( "close" );
							$( "#dialog:ui-dialog" ).dialog( "destroy" );
							cambiarEstausAlarma("aceptar","0",idAlerta,"0");
						},
						"Cancelar": function() {
							$( this ).dialog( "close" );
							$( "#dialog:ui-dialog" ).dialog( "destroy" );
							cambiarEstausAlarma("cancelar","0",idAlerta,"0");
						},
						"Posponer": function() {
							$( this ).dialog( "close" );
							$( "#dialog:ui-dialog" ).dialog( "destroy" );
							nuevoPoppupAlarma(idAlerta);
							
						}
					}
				});
				$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
			}else if(op=="false"){
				$( "#dialog-alarm" ).dialog({
					resizable: false,
					height:150,
					width:300,
					modal: true,
					buttons: {
						"Aceptar": function() {
							$( this ).dialog( "close" );
							$( "#dialog:ui-dialog" ).dialog( "destroy" );
							cambiarEstausAlarma("aceptar","0",idAlerta,"0");
						},
						"Cancelar": function() {
							$( this ).dialog( "close" );
							$( "#dialog:ui-dialog" ).dialog( "destroy" );
							cambiarEstausAlarma("cancelar","0",idAlerta,"0");
						}
					}
				});
				$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
			}
		}


		function nuevoPoppupAlarma(idAlerta)
		{
			$( "#dialog-alarmPos" ).dialog({
				resizable: false,
				height:200,
				width:500,
				modal: true,
				buttons: {
					"Aceptar": function() {
						$( this ).dialog( "close" );
						$( "#dialog:ui-dialog" ).dialog( "destroy" );
						var unidadTiempo=$( "#cbxTiempo" ).val();
						var tiempoAplazar=$( "#idTiempotex" ).val();
						cambiarEstausAlarma("posponer",tiempoAplazar,idAlerta,unidadTiempo);
					},
					"Cancelar": function() {
						$( this ).dialog( "close" );
						$( "#dialog:ui-dialog" ).dialog( "destroy" );
						//cambiarEstausAlarma("cancelar","0",idAlerta);
					}
				}
			});
			$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
				
		}
		/******************************************************  FIN  FUNCIONES PARA LAS ALARMAS      ***************************************************/
		/*
	 *Funcion para consultar los roles extras de cada usuario y
	 * construlle el arbol dinamico de los tipos de rol en el menu derecho
	 */
	

//******************************************************************FUNCIONALIDAD NUEVOS REQUERIMIENTOS**********************************************//	
	/*
	*	Abre la ventana para llamar el turno de solicitudes
	*/
	function busquedaExpedienteCaso(){
		$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:50,posy:111,width:900,height:500,title:"Solicitud Ciudadana Defensor", type:"iframe"});
	    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%=request.getContextPath()%>/BusquedaExpedienteCaso.do" width="898" height="500" />');	
		
	}

	/*
	*Funcion para invocar la ventana de buscar espediente
	*/ 
	function buscarExpediente() {
		customVentana("iframewindowBuscarExpediente", "Buscar Expediente", "/buscarExpedienteConSP.do");
	}


/////////////////////////////////////////////////////////////////////////////////////
//////////							TURNO									/////////
/////////////////////////////////////////////////////////////////////////////////////

	/**
	* Funcion que se encarga de habilitar el modulo de Asesorias Legal
	* de acuerdo al paremtro definido en BD
	*/
	function habilitarModuloAsesoriaLegalDefATE(){
		var idParametro = '<%=Parametros.ACTIVA_MODULO_ASESORIA_LEGAL.ordinal()%>';
		habilitarAsesoriaLegal = consultarParametroGenerico(idParametro);
		
		// Deshabilitar Asesoria Legal 
		if( habilitarAsesoriaLegal == null || parseInt(habilitarAsesoriaLegal) == 0 ){
			$("#tbarBtnLlamarTurnoAsesoria").hide();
			$("#ligSolicitudesAsesoriaLegal").hide();
			$("#seccionSolicitudesAsesoriaLegal").hide();
		}
	}

	/*
	* Funcion para cambiar el tipo de busqueda solicitud
	*/
	function cambiaTipoBusquedaSolicitud(){
		tipoBusqueda = <%=TipoTurno.SOLICITUD_CIUDADANA.ordinal()%>;
		obtenTurnoPopup();
	}
	
	/**
	* Funcion para cambiar el tipo de busqueda asesoria
	*/
	function cambiaTipoBusquedaAsesoria(){
		tipoBusqueda = <%=TipoTurno.ASESORIA_LEGAL.ordinal()%>;
		obtenTurnoPopup();
	}
	
	/**
	* Funcion para obtener el siguiente turno de la lista
	*/
	function obtenTurnoPopup(){
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/busquedaSiguienteTurnoDefensoria.do?tipoBusqueda='+tipoBusqueda+'',
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			var errorCode = $(xml).find('response').find('code').text();
    			//Si errorCode=0 entonces continuamos con el flujo
    			if(parseInt(errorCode)==0){
        			
    				turnoEnCurso = $(xml).find('numeroTurno').text();
    				//asignamos el numero de turno al span del popup
    				$("#spanNumTurno").html(''+$(xml).find('numeroTurno').text());
    				
    				var tipoTurno="";
    				
    				if($(xml).find('tipoTurno').text()=='ASESORIA_LEGAL'){
    					tipoTurno="SOLICITUD DE ASESORIA LEGAL";
    				}
    				else{
    					tipoTurno="SOLICITUD CIUDADANA DE DEFENSOR";
    				}
    				$("#spanTipoTurno").html(tipoTurno);
    				idSiguienteTurno=$(xml).find('numeroTurno').text();
    				idTurno=$(xml).find('turnoId').text();
    				//Generamos el popup
    				muestraPopupSiguienteTurno();
    			}
    		}
    	});
	}

	/*
	*Funcion que muestra en una ventana modal, el turno que
	*se esta atendiendo actualmente
	*/
	function muestraPopupSiguienteTurno(){

		if(idSiguienteTurno==null||idSiguienteTurno==""){
			customAlert("No hay turnos por atender");
		}else{		
			$( "#dialog-confirm" ).dialog({
			resizable: false,
			height:300,
			width:300,
			modal: true,
			buttons: {
				
				"Atender": function() {
					$(this).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
					
					if(tipoBusqueda == '<%=TipoTurno.ASESORIA_LEGAL.ordinal()%>'){
						//deprecated cargaAcuse('asesoria');
						registrarAsesoriaLegal();
					}else{
						//busquedaExpedienteCaso();
						solicitarDefensor();
					}
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
					cancelarTurnoPRCAN();
					}
				}
			});
			$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
		}
	}

	/*
	*Funcion para cancelar un turno
	*/
	function cancelarTurnoPRCAN(){
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/CancelarTurno.do?turno='+idTurno,
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			customAlert("Turno Cancelado");
    		}
    	});
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////FUNCIONES PARA SOLICITAR DEFENSOR DESDE DEFENSOR ATE									/////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//variable para controlar el id de la ventana de solicitar defensor
	var windowIdSolicitarDefensor = 1;

	/*
	*Funcion para solicitar un defensor a la institucion de defensoria
	*/
	function solicitarDefensor(){

		var parametrosVentana = "";		
		//id de la ventana solicitar defensor
		var idWindow = "iframewindowSolicitarDefensor" + windowIdSolicitarDefensor;
		parametrosVentana += "&idWindow=" + idWindow;
		
	    $.newWindow({id:"iframewindowSolicitarDefensor" + windowIdSolicitarDefensor, statusBar: true, posx:50,posy:110,width:1200,height:570,title:"Solicitar Defensor", type:"iframe"});
	    $.updateWindowContent("iframewindowSolicitarDefensor"+windowIdSolicitarDefensor,'<iframe src="<%= request.getContextPath() %>/solicitarDefensorDefensorAte.jsp?parametrosVentana='+parametrosVentana+'" width="1200" height="570"/>');
	    //$("#" +"iframewindowSolicitarDefensor"+windowIdSolicitarDefensor+ " .window-maximizeButton").click();
		windowIdSolicitarDefensor++;			
	}

	/*
	*Cierra la ventana de confirmacion de solicitud de defensor
	*/
	//MOD defensorATE
	function cerrarVentanaSolicitudDeDefensor(idWindow){
		$.closeWindow(idWindow);
	}	

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////FUNCIONES PARA CONSULTAR SOLICITUDES DE DEFENSOR										/////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	//Variable para recargar el grid de solicitudes
	var recargaGridSolDef = true;

	/*
	*Funcion generica para consultar solicitudes con base en su estatus 
	*y la institucion que la genero
	*/
	function cargaGridSolicitudesDefensor(idInstitucion,estatusSolicitud){
		var tipoSolicitud = '<%=TiposSolicitudes.DEFENSOR.getValorId()%>'
		if(recargaGridSolDef == true){
			jQuery("#gridSolicitudesDeDefensor").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarSolicitudesDeDefensorPorFiltro.do?idInstitucion='+idInstitucion+'&idEstatusSolicitud='+estatusSolicitud+'&idTipoSolicitud='+tipoSolicitud+'', 
				datatype: "xml", 
				colNames:['Folio','Estado','Caso', 'Expediente Asociado', 'Solicitante', 'Fecha-Hora Solicitud'], 
				colModel:[ 	
							{name:'folio',		  index:'folio',		width:100, align:'center',	sortable:false},
							{name:'estado',		  index:'estado',		width:100, align:'center',	sortable:false},
				           	{name:'caso',		  index:'caso',		    width:180, align:'center',	sortable:false},
				           	{name:'expediente',	  index:'expediente',	width:200, align:'center',	sortable:false},
				           	{name:'solicitante',  index:'solicitante',	width:200, align:'center',	sortable:false},
				           	{name:'fechaHoraSol', index:'fechaHoraSol', width:150, align:'center',	sortable:false}
						],
				pager: jQuery('#pagerGridSolicitudesDeDefensor'),
				rowNum:10,
				rowList:[10,20,30,40,50,60],
				viewrecords: true,
				onresize_end: function(){ 
					$("#pagerGridSolicitudesDeDefensor").setGridWidth($("#mainContent").width() - 5, true); 
				},
				ondblClickRow: function(rowid){
					abrirVisorDefensoria(rowid,idInstitucion,estatusSolicitud,null,null,null);
				}
			})
			recargaGridSolDef = false;
		}else{
			//Definimos el evento ondblClickRow para tomar el valor actual de las variables
			jQuery("#gridSolicitudesDeDefensor").jqGrid('setGridParam', { ondblClickRow: function(rowid) { abrirVisorDefensoria(rowid,idInstitucion,estatusSolicitud,null,null,null); } });
			jQuery("#gridSolicitudesDeDefensor").jqGrid('setGridParam',{url:'<%= request.getContextPath()%>/consultarSolicitudesDeDefensorPorFiltro.do?idInstitucion='+idInstitucion+'&idEstatusSolicitud='+estatusSolicitud+'&idTipoSolicitud='+tipoSolicitud+'',datatype:"xml" });
			jQuery("#gridSolicitudesDeDefensor").trigger("reloadGrid");
		}	
		$("#gview_gridSolicitudesDeDefensor .ui-jqgrid-bdiv").css('height', '450px');
		ocultaMuestraGrids('3');
	}


	//Variable para controlar el id del visor de defensoria
	var idWindowVisorDefensoria = 1;
	
	/*
	*Funcion para abrir el visor de defensoria
	*/
	function abrirVisorDefensoria(solicitudDefensorId,idInstitucion,estatusSolicitud,numeroExpedienteId,reasignarDefensor,idDefensorActual){
		
		var tituloVentana="Solicitud de defensor";
		var idWindow = "iframeWindowVisorDefensoria"+idWindowVisorDefensoria;
				
		var params = "?idWindow="+ idWindow;
		params += "&solicitudDefensorId="+solicitudDefensorId;
		params += "&idInstitucion="+idInstitucion;
		params += "&estatusSolicitud="+estatusSolicitud;
		params += "&numeroExpedienteId="+numeroExpedienteId;
		params += "&reasignarDefensor="+reasignarDefensor;
		params += "&consultaSoloResumen="+true;
		params += "&idDefensorActual="+idDefensorActual;
		params += "&esConsultaSolicitud=true";  //Identificar de que grid se recarga, ver mostrarDetalleDesignaciones(). Tambien llamado en indexDefensoriaCoordinador.jsp
				
		$.newWindow({id:"iframeWindowVisorDefensoria"+idWindowVisorDefensoria, statusBar: true, posx:0,posy:0,width:1000,height:500,title:tituloVentana, type:"iframe"});
	    $.updateWindowContent("iframeWindowVisorDefensoria"+idWindowVisorDefensoria,'<iframe src="<%= request.getContextPath() %>/mostrarDetalleATDEF.do'+params+'" width="1000" height="500" />');
		idWindowVisorDefensoria++;
	}
	
	</script>
</head>

<body>
	<div class="ui-layout-west">

		<div class="header">&nbsp;</div>

		<div class="content">
			<div id="accordionmenuprincipal">	
				
				<!--Tab de Turnos-->
				<h3><a href="#" onmousedown="ocultaMuestraGrids('6');">Turnos </a></h3>
					<div >
<!--					<table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="0" class="fondoClaroAp" style="cursor:pointer">-->
<!--						<tr >-->
<!--						   <td width="100%" id="administrativa" ><img src="<%=request.getContextPath()%>/resources/images/icn_folderchek.png" width="20" height="16" /><span class="txt_tit_turno">Asesor&iacute;a legal</span></td>-->
<!--						</tr>-->
<!--						<tr>-->
<!--						   <td width="100%" id="admin-TurnoA"><img src="<%= request.getContextPath() %>/resources/images/icn_fleup.png" width=16 height=14>&#8194;&#8194;&#8194;<span class="txt_num_turno">4</span></td>-->
<!--						   </tr>-->
<!--						   <tr>-->
<!--						   <td width="100%" id="admin-TurnoB"><img src="<%= request.getContextPath() %>/resources/images/icn_fleup.png" width=16 height=14>&#8194;&#8194;&#8194;<span class="txt_num_turno">5</span></td>-->
<!--						</tr>-->
<!--						<tr>-->
<!--						   <td width="100%" id="admin-TurnoC"><img src="<%= request.getContextPath() %>/resources/images/icn_fleup.png" width=16 height=14>&#8194;&#8194;&#8194;<span class="txt_num_turno">6</span></td>-->
<!--						   </tr>-->
<!--						   <tr>-->
<!--						   <td width="100%" id="admin-TurnoD"><img src="<%= request.getContextPath() %>/resources/images/icn_fleup.png" width=16 height=14>&#8194;&#8194;&#8194;<span class="txt_num_turno">7</span></td>-->
<!--						</tr>-->
<!--							<tr>-->
<!--						   <td width="100%" id="penal"><img src="<%=request.getContextPath()%>/resources/images/icn_folderchek.png" width="20" height="16" /><span class="txt_tit_turno">Solicitud ciudadana de defensor</span></td>-->
<!--						</tr>-->
<!--						<tr>-->
<!--						   <td width="100%" id="penal-TurnoA"><img src="<%= request.getContextPath() %>/resources/images/icn_fleup.png" width=16 height=14>&#8194;&#8194;&#8194;<span class="txt_num_turno">1</span></td>-->
<!--						   </tr>-->
<!--						   <tr>-->
<!--						   <td width="100%" id="penal-TurnoB"><img src="<%= request.getContextPath() %>/resources/images/icn_fleup.png" width=16 height=14>&#8194;&#8194;&#8194;<span class="txt_num_turno">2</span></td>-->
<!--						</tr>-->
<!--						<tr>-->
<!--						   <td width="100%" id="penal-TurnoC"><img src="<%= request.getContextPath() %>/resources/images/icn_fleup.png" width=16 height=14>&#8194;&#8194;&#8194;<span class="txt_num_turno">3</span></td>-->
<!--						   </tr>-->
<!--						   <tr>-->
<!--						   <td width="100%" id="penal-TurnoD"><img src="<%= request.getContextPath() %>/resources/images/icn_fleup.png" width=16 height=14>&#8194;&#8194;&#8194;<span class="txt_num_turno">4</span></td>-->
<!--						</tr>-->
<!--											-->
<!--				</table>-->
				</div>

					<!--AGA-->
					<!--<h3><a><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Solicitudes sin enviar</a></h3>-->
					<!--<div>-->
					<!--<ul id="seccion2tree" class="filetree">								-->
					<!--<li class="closed"><span id="noEnvioSolicitud" class="folder" onclick="ocultaMuestraGrids('10');">Solicitudes de defensor</span>							-->
					<!--	<ul>-->
					<!--	</ul>-->
					<!--	</li>-->
						<!--<li class="closed"><span id="noEnvioAsesoria" class="folder" onclick="ocultaMuestraGrids('9');">Solicitudes de asesoria</span>-->
						<!--<ul>-->
						<!--</ul>-->
						<!--</li>-->
<!--					</ul>-->
<!--				</div>-->

				<!--AGA-->
				<!--<h3><a href="#" id="designaciones" onmousedown="ocultaMuestraGrids('1');" ><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Expedientes sin atender</a></h3>-->
				<!--<div>-->
				<!--</div>-->
				
				<!--AGA-->
				<!--<h3><a href="#" id="avisosDetencion" onmousedown="ocultaMuestraGrids('2');" ><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Avisos de personas detenidas</a></h3>-->
				<!--<div>-->			
				<!--</div>-->
				
				<!--Tab de Solicitudes de defensor-->
				<h3><a href="#" id="ligSolicitudesDefensor"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Solicitudes de defensor</a></h3>
					<div>
						<ul id="seccion1tree" class="filetree">
							<!--AGA-->								
							<!--<li class="closed"><span id="solicitudPoderJudicial" class="folder" onmousedown="ocultaMuestraGrids('4');">Poder judicial</span>-->
							<!--<ul>-->
							<!--</ul>-->
							<!--</li>-->
							<li class="closed">
								<span class="folder">Defensor&iacute;a</span>
								<ul>
									<li class="file">
										<span class="file" onclick="cargaGridSolicitudesDefensor('<%=Instituciones.DEF.getValorId()%>','<%=EstatusSolicitud.ABIERTA.getValorId()%>')">Abiertas</span>
									</li>
									<li class="file">
										<span class="file" onclick="cargaGridSolicitudesDefensor('<%=Instituciones.DEF.getValorId()%>','<%=EstatusSolicitud.EN_PROCESO.getValorId()%>')">En proceso</span>
									</li>
									<li class="file">
										<span class="file" onclick="cargaGridSolicitudesDefensor('<%=Instituciones.DEF.getValorId()%>','<%=EstatusSolicitud.CERRADA.getValorId()%>')">Cerrada</span>
									</li>
								</ul>
							</li>

							<!--AGA-->									
							<!--<li class="closed"><span id="solicitudAtencionTemprana" class="folder" onmousedown="ocultaMuestraGrids('3');">Atenci&oacute;n temprana</span>-->
							<!--<ul>-->
							<!--</ul>-->
							<!--</li>-->

							<!--<li class="closed"><span id="asesorias" class="folder" onmousedown="ocultaMuestraGrids('7');">Asesorias</span>-->
							<!--<ul>-->
							<!--</ul>-->
							<!--</li>-->
						</ul>
					</div>
					
				<!--Tab de Solicitudes de Asesoria Legal -->
				<h3 id="seccionSolicitudesAsesoriaLegal"><a href="#" id="ligSolicitudesAsesoriaLegal"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Solicitudes de Asesor&iacute;a Legal</a></h3>
					<div>
						<ul id="seccion6tree" class="filetree">
									<li class="file">
										<span class="file" onclick="cargarGridAsesoria('<%=Instituciones.DEF.getValorId()%>','<%=EstatusSolicitud.ABIERTA.getValorId()%>')">Abiertas</span>
									</li>
									<li class="file">
										<span class="file" onclick="cargarGridAsesoria('<%=Instituciones.DEF.getValorId()%>','<%=EstatusSolicitud.POR_ASIGNAR.getValorId()%>')">Por Asignar</span>
									</li>
									<li class="file">
										<span class="file" onclick="cargarGridAsesoria('<%=Instituciones.DEF.getValorId()%>','<%=EstatusSolicitud.ASIGNADO.getValorId()%>')">Asignadas</span>
									</li>

							<!--AGA  deprecated-->									
							<!--<li class="closed"><span id="solicitudAtencionTemprana" class="folder" onmousedown="ocultaMuestraGrids('3');">Atenci&oacute;n temprana</span>-->
							<!--<ul>-->
							<!--</ul>-->
							<!--</li>-->

							<!--<li class="closed"><span id="asesorias" class="folder" onmousedown="ocultaMuestraGrids('7');">Asesorias</span>-->
							<!--<ul>-->
							<!--</ul>-->
							<!--</li>-->
						</ul>
					</div>
						
<!-- 			<h3 ><a id="" href="#" onclick="generaVisorGraficaView()"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png"  width="15" height="15">Gráficas y Reportes</a></h3>
				<div>		
					<input type="button" value="Ver Grafica" id="imageViewer" name="imageViewer"/>	
					<ul id="seccion3treePJENC" class="filetree">
						<li class="closed" id="casosPJENC"><span class="folder">Casos</span>
							 Aqui se agregan los casos y expedientes dinamicamente 
						</li>
					</ul>
				</div> -->
				
				
			</div>
		</div>
	</div>

	<div class="ui-layout-east">
		<div class="header">Bienvenido</div>
		<div class="content">
			<div id="accordionmenuderprincipal">
				<h4>
					<a href="#">Datos personales</a>
				</h4>
				<div>
					<center>
						<jsp:include page="/WEB-INF/paginas/datosPersonalesUsuario.jsp" flush="true"></jsp:include>
					</center>
				</div>
				<!-- <h4>
					<a href="#">Calendario</a>
				</h4>
				<div>
					<center>
						<a href="#"><img src="<%=request.getContextPath()%>/resources/images/img_calendario.png" width="201" height="318"></a>
					</center>
				</div>-->
				<h6>
					<a href="#">Agenda</a>
				</h6>
				<div>
					<center>
						<jsp:include page="/WEB-INF/paginas/agendaUsuario.jsp" flush="true"></jsp:include>
					</center>
					<br />
					
				</div>
				
<!--				<h3>-->
<!--					<a href="#">Clima</a>-->
<!--				</h3>-->
<!--				<div align="left">-->
<!--					<div align="left" id="test"></div>-->
<!--				</div>-->
				
				<h6><a href="#" id="" onclick="visorLeyesCodigos()">Consultar leyes y c&oacute;digos</a></h6>
				<div>
					<!--  <table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="0" bgcolor="#EEEEEE" bordercolorlight="#FFFFFF" style="cursor:pointer">
						<tr>
							<td width="100%" id="leyes"><img src="<%=request.getContextPath()%>/resources/css/check.png" width="16" height="16" />Leyes</td>
						</tr>
						<tr>
							<td id="codigos">&nbsp;<img src="<%=request.getContextPath()%>/resources/css/check.png" width="16" height="16" />Codigos</td>
						</tr>
						<tr>
							<td id="manuales">&nbsp;<img src="<%=request.getContextPath()%>/resources/css/check.png" width="16" height="16" />Manuales</td>
						</tr>
					</table>-->
				</div>
				<h3>
					<a href="#">Chat</a>
				</h3>
				<div align="center">
				
					<div id="dialogoChat" title="Chat" align="center">
						<iframe src="<%=((ConfiguracionDTO)session.getAttribute(LoginAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getUrlServidorChat()%>" frameborder="0" width="380" height="280"></iframe>
					</div>
					<center>
						<a onclick="ejecutaChat();" id="controlChat"><img src="<%= request.getContextPath()%>/resources/images/img_chat.png" width="130" height="104"></a>
					</center>
						<!--iframe src="http://gaby1:5280/web/jwchat/index.html" frameborder="0" width="200" height="200" scrolling="no"></iframe  -->
				</div>
				<h3><a href="#" onclick="consultarTiposRol();">Facultades</a></h3>
				<div>
					<table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="0"  style="cursor:pointer" id="tableRolMenu">
					</table>
					<form name="frmRol2" action="<%= request.getContextPath() %>/rolRedirec.do" method="post">
						<input type="hidden" name="rolname" />
					</form>
				</div>
			</div>
		</div>
		<!-- div class="footer">&nbsp;</div -->
	</div>


	<div class="ui-layout-north">
		<div class="content">
			<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" background="<%=request.getContextPath()%>/resources/images/top_gral_sin_version.jpg" height="100%">
				  <TBODY>
					  <TR>
					    <TD width=100 align=left valign="middle"><img src="<%=request.getContextPath()%>/resources/images/logo_gral.png"></TD>
                        <TD width=301  align=left valign="middle">
                        	<div class='nombreInstitucion'><%=rolDTO.getDescripcionRol()%></div>
                        	<div class='versionCodigo'>${nsjp.version.codigo}</div>
                        </TD>
					    <TD width=126 align=left valign="top"><SPAN></SPAN></TD>
					    <TD width=272 align=center valign="top">&nbsp;</TD>
					    <TD width=28 align="center" valign="middle">&nbsp;</TD><!--<td width="150" align="center"></td>-->
					    <TD width=380 align="right" valign="middle">
					      <table width="362" border="0" cellspacing="0" cellpadding="0">
					        <tr>
					          <td width="165"><table width="141" border="0" cellspacing="0" cellpadding="0">
					            <tr>
					              <td width="53" align="right" class="txt_menu_top">&nbsp;</td>
					              <td width="157">&nbsp;</td>
					            </tr>
					          </table></td>
					          <td width="103"><table width="89" border="0" cellspacing="0" cellpadding="0">
					            <tr>
					            	
					            	
					            	<!-- 
					              <td width="53" class="txt_menu_top">Buscar</td>
					              <td width="147"><img src="<%=request.getContextPath()%>/resources/images/icn_buscar.png" width="22" height="23" border="0"></td>
					               -->
					            </tr>
					          </table>
					            <label for="textfield"></label></td>
					          <td width="204"><table width="89" border="0" cellspacing="0" cellpadding="0">
					            <tr>
					              <td width="107" class="txt_menu_top">Cerrar sesi&oacute;n</td>
					              <td width="42" class="txt_menu_top"><a href="#" onclick='$("#dialog-logout").dialog( "open" );'><img src="<%=request.getContextPath()%>/resources/images/btn_cerrar.png" width="29" height="26" border="0"></a></td>
					            </tr>
					          </table></td>
					        </tr>
					      </table>
					      <table width="363" border="0" cellspacing="0" cellpadding="0">
				        	<tr>
				          		<td width="363" align="right" valign="middle">
				          			<TABLE border=0 cellSpacing=0 cellPadding=0 width="300" height="100%">
				            			<TBODY>
				              				<TR vAlign=top>
				                				<TD width=128 align=right valign="middle">&nbsp;</TD>
				                				<TD width=150 align="right" valign="middle">
				                					<DIV id=liveclock></DIV>
				                				</TD>
				                				<TD width=10 align="right" valign="middle">
				                					<IMG alt="Icono reloj" src="<%=request.getContextPath()%>/resources/images/icn_reloj.png" width=26 height=25>
				                				</TD>
				              				</TR>
				            			</TBODY>
				          			</TABLE>
				          		</td>
				        	</tr>
						</table>
					  </TD>
					  </TR>
				  </TBODY>
			  </TABLE>		
		</div>
		<ul class="toolbar">
			<div id="menu_head">
				<li id="tbarBtnHeaderZise" class="first"><span></span></li>
				<!--<li id="tbarBtnQuejaCiudadana" class="first"><span></span>Recibir queja<img src="<%= request.getContextPath() %>/resources/images/icn_errorinfo.png" width="15" height="16"></li>-->
				<li id="tbarBtnLlamarTurnoSolicitud" class="first"><span></span>Llamar turno para solicitud de defensor<img src="<%= request.getContextPath() %>/resources/images/icn_turno.png" width="15" height="16"></li>
				<li id="tbarBtnLlamarTurnoAsesoria" class="first"><span></span>Llamar turno para solicitar Asesor&iacute;a Legal<img src="<%= request.getContextPath() %>/resources/images/icn_turno.png" width="15" height="16"></li>
			</div>
			
			<div id="menu_config">
				<li onclick="buscarExpediente();">Buscar expediente<img src="<%= request.getContextPath() %>/resources/images/icn_busca3.png" width="15" height="16"></li>
<!--				<li id="buscarCaso"><span></span>Buscar Caso&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_busca2.png" width="15" height="16"></li>-->
<!--				<li id="buscarExpediente"><span></span>Buscar Expediente&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_busca3.png" width="15" height="16"></li>-->
<!--				<li id="verde">Configuraci&oacute;n&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_config.png" width="15" height="16"></li>-->
				<!-- <li id="buscarCaso"><a href="#">Buscar Caso</a></li>
				<li id="buscarExpediente"><a href="#">Buscar Expediente</a></li> 
				<li id="generarDocumento">Generar Documento</li>
				 <li>Imprimir</li> 
				<li>Guardar</li>
				<li><a href="#">Digitalizar Documento</li>
				<li><a href="#">Turnar Documento</a></li>
				<li>Enviar Notificaciones</li>
				<li>config01</li>
				<li>Config02</li>
			    <li>Config03</li>
				<li class="last">config04</li>-->
			</div>
		</ul>
	</div>

<div class="ui-layout-south">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pleca_bottom">
	    <tr>
  			<!-- <td height="15">&nbsp;</td>  -->
			<td height="15" align="center" style="border-left:#FFFFFF; border-top:#FFFFFF;">
				<label id="ambienteLb" style="color:#FBFBEF"></label>	
			</td>
	  </tr>
	</table>
	<div id="pie" align="center" style="BACKGROUND-COLOR: #e7eaeb; BACKGROUND-POSITION: center top; COLOR: #58595b">
		<div id="footer" style="PADDING-BOTTOM: 5px; PADDING-LEFT: 5px; WIDTH: 720px; PADDING-RIGHT: 5px; PADDING-TOP: 5px">
			Direcci&oacute;n de la Instituci&oacute;n
		</div>
	</div>
</div>

	<div id="mainContent">
		<div class="ui-layout-center">
			<div class="ui-layout-content">
				
				<div id="divGridDesignaciones">
					<table id="gridDesignaciones"></table>
					<div id="pagerGridDesignaciones"></div>
				</div>
				
				<div id="divGridAvisosDeDetencion">
					<table id="gridAvisosDeDetencion"></table>
					<div id="pagerAvisosDeDetencion"></div>
				</div>
				
				<div id="divGridSolicitudAtencionTemprana">
					<table id="gridSolicitudAtencionTemprana"></table>
					<div id="pagGridSolicitudAtencionTemprana"></div>
				</div>
				
				<div id="divGridSolicitudesDeDefensor">
					<table id="gridSolicitudesDeDefensor"></table>
					<div id="pagerGridSolicitudesDeDefensor"></div>
				</div>
				
				<div id="divGridPoderJudicial">
					<table id="gridPoderJudicial"></table>
					<div id="pagerPoderJudicial"></div>
				</div>
				
				<div id="divGridExpedientesSubordinados">
					<table id="gridExpedientesSubordinados"></table>
					<div id="pagGridExpedientesSubordinados"></div>
				</div>
				
				<div id="divGridAsesorias">
					<table id="gridAsesorias"></table>
					<div id="pagGridAsesorias"></div>
				</div>
								
				<div id="divGridNoEnvioSolicitud">
					<table id="gridNoEnvioSolicitud"></table>
					<div id="pagGridNoEnvioSolicitud"></div>
				</div>
								
				<div id="divGridNoEnvioAsesoria">
					<table id="gridNoEnvioAsesoria"></table>
					<div id="pagGridNoEnvioAsesoria"></div>
				</div>
								
				<div id="divGenerarTurno">
				<table align="center">
					<tr>
						<td>
							<jsp:include page="vAdmonRecepcion.jsp">
								<jsp:param value="defensoria" name="defensoria"/>
							</jsp:include>
						</td>
					</tr>
				</table>
				
				<table height="200px"></table>
				
				</div>
			</div>
		</div>
	</div>
	

	<div id="dialog-confirm" title="Turno: " style="display: none">
		<p align="center"><span style="font-size: 25px;" id="spanTipoTurno">.</span><br/><span style="font-size: 115px;" id="spanNumTurno">15</span></p>
	</div>
    
    <div id="dialog-logout" title="Cerrar Sesi&oacute;n">
		<p align="center">
			<span id="logout">¿Desea cerrar su sesi&oacute;n?</span>
		</p>
	</div>
    
		<!-- dialogos para las alarmas -->
	<div id="dialog-alarm" title="Alarma ">
		<p align="center">
			<span id="mensajeAlarma">mensajeConfigurable</span>
		</p>
	</div>
	
		<!-- dialogos para Bloqueo de pantalla-->
	<div id="dialog-bloqueo" title="Bloqueo Sesi&oacute;n"  style="display: none;">
		<p align="center">
			<table border="0">
				<tr>
					<td colspan="2">La sesi&oacute;n se ha bloqueado, introduce tu contraseña para desbloquear.</td>
					
				</tr>
				<tr>
					<td align="right"><label style="color:#4A5C68">Contraseña:</label></td>
					<td><input type="password" name="password" id="password" value="" maxlength="15" size="20"></td>
				</tr>
				<tr id="captchaJPG" >
	            	<td align="right">
	                	<label style="color:#4A5C68">Captcha:</label>
                    </td>
	                <td>
	                	<img id="imgcaptcha" src="<%=request.getContextPath()%>/kaptcha.jpg">
	                </td>
	            </tr>
	            <tr id="captchaTXT" >
	            	<td align="right">
	                	<label style="color:#4A5C68">Captcha:</label>
	             	</td>
	                <td>
	                   	<input type="text" id="scaptcha" name="scaptcha" value="" maxlength="15" size="20">
	                   	<input type="hidden" name="captcha" value='<%= request.getAttribute("captcha")%>'>
	                </td>
	            </tr>
			</table>
		</p>
	</div>
	<div id="dialogBlok" title="¡Su sesi&oacute;n est&aacute; a punto de caducar!">
			<p>
				<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 50px 0;"></span>
				La sesi&oacute;n se cerrar&aacute; en <span id="dialog-countdown" style="font-weight:bold"></span> segundos.
			</p>

			<p>¿Desea continuar con la sesi&oacute;n?</p>
	</div>
	
	<div id="dialog-alarmPos" title="Alarma ">
		<p align="center">
			<span id="mensaje">Tiempo deseado para aplazar la alerta</span><br/>
			<span id="tiempo"><input type="text" size="5" maxlength="2" id="idTiempotex" onKeyPress="return solonumeros(event);"/></span>
			<span id="cbxTiempoSpan">
				<select id="cbxTiempo">
					<option value="2">Horas</option>
					<option value="1">Minutos</option>
				</select>
			</span>
		</p>
	</div>
	<!-- FIN dialogos para las alarmas -->
</body>
<script type="text/javascript">
	$( "#dialog-alarm" ).dialog();
	$( "#dialog-alarmPos" ).dialog();
	$( "#dialog-alarm" ).dialog( "destroy" );
	$( "#dialog-alarmPos" ).dialog( "destroy" );	
</script>
</html>
