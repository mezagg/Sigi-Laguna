<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<% 
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	RolDTO rolDTO = usuario.getRolACtivo().getRol();
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
 
	<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/themes/1.8.10/${theme.name}/jquery-ui.css" /-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />				
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/${theme.name}/jquery-ui.css" />
	<!--link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" /-->	 	
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimeout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimer.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/layout_complex.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/reloj.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/valida/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/valida/jquery.maskedinput.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/valida/mktSignup.js"></script>
	<!--script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.zweatherfeed.js"></script-->
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>			
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/sesion.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>

	<script type="text/javascript">

	var contextoPagina = "${pageContext.request.contextPath}";
	var sesionActiva = '<%= (request.getSession().getAttribute(LoginAction.KEY_SESSION_USUARIO_FIRMADO)!=null)%>';
	var tiempoActiva = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoBloqueoSesion()%>';
	estaSesionActiva();


	//Variable para controlar los id�s de las ventanas
	//de solicitud de transcripcion de audiencia
	var idWindowTranscripcionAdudiencia=1;

	var idWindowSolicitarEvidencia = 1;	
	
	//por default es la primera vez que entra
	var firstGridEvidenciasNuevas = true;
	var firstGridEvidenciasPendientes = true;
	var firstGridEvidenciasConcluidas = true;
	
	var firstGridDocumentosRecibidos = true;
	var firstGridResguardoEvidencia = true;
	//variable para las asignaciones recibidas no atendidas
	var firstGridAsignacionesRecibidasNoAtendidas = true;

	var firstGridAsignacionesRecibidasEnProceso = true;
	
	var outerLayout, innerLayout;
	
	//var reloadGridFecha = false;
	
	//var validaFecha = false;

	var idAudiencia ="";
	var documentoID="";

	//Variable para el visor de atencion a solicitud pericial 
	var idWindowVisorAntencionSolPer = 1;	
	
	$(document).ready(function() {
		jQuery(document).ajaxStop(jQuery.unblockUI);
		//obtenemos el tiempo de las alarmas y ponemos en marcha el timer		
		var tiempo='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoRevisionAlarmas()%>';
		setInterval(muestraAlerta, tiempo);

		outerLayout = $("body").layout( layoutSettings_Outer );

		$("#accordionmenuprincipal").accordion({  fillSpace: true });
		$("#accordionmenuderprincipal").accordion({ fillSpace: true});
		$("#accordionmenuderprincipal").accordion( "option", "icons", null );

		$("#seccion2tree").treeview();
		
		$("#controlAgenda").click(creaAgenda);
		
		//Consulta las asignaciones recibidas que aun no son atendidas  
		cargaGridAsignacionesRecibidasNoAtendidas();
		
		
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
		// BIND layout events to close-buttons to make them functional
		outerLayout.addCloseBtn("#west-closer", "west");
		outerLayout.addCloseBtn("#east-closer", "east");
		createInnerLayout ();
					
		//$('#test').weatherfeed(['MXDF0132']);

		var ambiente='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAmbiente()%>';
		if(ambiente == undefined || ambiente == "undefined"){
			ambiente = "";
		}
		$("#ambienteLb").html('<strong><big>'+ambiente+'</big></strong>');
		
		//llama funcion para mostrar gadgets
		muestraGadgets();
	});
	//FIN ON READY
	
	

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
	,	center__minWidth:				200
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
		,	initClosed:				false
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
		,	minWidth:				200
		,	minHeight:				200
		,	onresize_end:			function () { $("#gridEvidenciasNuevas").setGridWidth($("#mainContent").width() - 5, true);
										$("#gridEvidenciasPendientes").setGridWidth($("#mainContent").width() - 5, true);
										$("#gridEvidenciasConcluidas").setGridWidth($("#mainContent").width() - 5, true);
										$("#gridDocumentosRecibidos").setGridWidth($("#mainContent").width() - 5, true);
										$("#gridResguardoEvidencia").setGridWidth($("#mainContent").width() - 5, true);
										$("#gridAsignacionesRecibidasNoAtendidas").setGridWidth($("#mainContent").width() - 5, true); 										 
									}
		}
	};

	/////////////////////////////////////////////// COMIENZA FUNCIONALIDAD COMUN //////////////////////////////////////////////////////////////////
	/*
	*Funcion que abre el visor de audiencias
	*(Por el momento no acarrea el ID solo abre el visor) 
	*/
	
	/*
	*Funcion que llama a la funcionalidad para crear la agenda 
	*/
	function creaAgenda() {
		$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
	    $.updateWindowContent("iframewindowagenda",'<iframe src="<%=request.getContextPath()%>/InicioAgenda.do" width="1150" height="600" />');		
	}

	//crea una nueva ventana para la agenda	
	function creaAgenda() {
		$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
	    $.updateWindowContent("iframewindowagenda",'<iframe src="<%= request.getContextPath() %>/InicioAgenda.do" width="1150" height="600" />');		
	    $("#" +"iframewindowagenda"+ " .window-maximizeButton").click();	
	}
	
	//Abre una nueva ventana para solicitar evidencia
	function solicitaEvidencia(rowid) {
		idWindowSolicitarEvidencia++;
		$.newWindow({id:"iframewindowSolicitarEvidencia" + idWindowSolicitarEvidencia, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Solicitud de Evidencia", type:"iframe"});
	    $.updateWindowContent("iframewindowSolicitarEvidencia" + idWindowSolicitarEvidencia,'<iframe src="<%= request.getContextPath() %>/solicitudDeEvidencia.do?rowid='+rowid+'" width="1050" height="600" />');		
	}	
	function cerrarVentanaEvidencia(){
	    var pantalla ="iframewindowSolicitarEvidencia"+idWindowSolicitarEvidencia;
		$.closeWindow(pantalla);
	}

    function buscarExpediente() {
		$.newWindow({id:"iframewindowBuscarExpediente", statusBar: true, posx:255,posy:110,width:653,height:400,title:"Buscar Expediente", type:"iframe"});
    	$.updateWindowContent("iframewindowBuscarExpediente",'<iframe src="<%= request.getContextPath() %>/buscarExpediente.do" width="653" height="400" />');		
	}

	
	function buscarCaso() {
		$.newWindow({id:"iframewindowBuscarCaso", statusBar: true, posx:255,posy:110,width:653,height:400,title:"Buscar Caso", type:"iframe"});
    	$.updateWindowContent("iframewindowBuscarCaso",'<iframe src="<%= request.getContextPath() %>/buscarCaso.do" width="653" height="400" />');		
	}

	/*
	*Funcion que carga el grid de evidencias nuevas
	*/
	function cargaGridEvidenciasNuevas(){
		if(firstGridEvidenciasNuevas == true){
			
			jQuery("#gridEvidenciasNuevas").jqGrid({ 
				url:'<%= request.getContextPath()%>/solicitudEvidenciasPorEstatus.do?estatus=1&areaSolicitante=2', 
				datatype: "xml", 
				colNames:['N&uacute;mero de expediente','N&uacute;mero de caso','Cadena de custodia','N&uacute;mero de evidencia','Nombre del solicitante','Fecha l&iacute;mite','Perito responsable','Acuse' ], 
				colModel:[ 	{name:'NumeroExpediente',index:'1', width:200},
				           	{name:'NumeroCaso',index:'2', width:200},
				           	{name:'CadenaCustodia',index:'3', width:150},
				           	{name:'NumeroEvidencia',index:'4', width:150},
				           	{name:'NombreSolicitante',index:'5', width:150},
							{name:'FechaLimite',index:'6', width:50},
				           	{name:'PeritoResponsable',index:'7', width:150}, 
							{name:'Acuse',index:'8', width:50}
						],
				pager: jQuery('#pagerEvidenciasNuevas'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				//autoheight:true,
				width:"100%",
				height:440,
				sortname: '1',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					solicitaEvidencia(rowid);
				},
				onSelectRow: function(rowid){
					mostrarDetalle(rowid);
				}
			}).navGrid('#pagerGridEvidenciasNuevas',{edit:false,add:false,del:false});

			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridEvidenciasNuevas=false;
		}
		else{
			jQuery("#gridEvidenciasNuevas").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/solicitudEvidenciasPorEstatus.do?estatus=1&areaSolicitante=2',datatype: "xml" });
			$("#gridEvidenciasNuevas").trigger("reloadGrid");			
		}
		
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridEvidenciasNuevas');
	}

	/*
	*Funcion que carga el grid de evidencias pendientes
	*/
	function cargaGridEvidenciasPendientes(){
		if(firstGridEvidenciasPendientes == true){
			
			jQuery("#gridEvidenciasPendientes").jqGrid({ 
				url:'<%= request.getContextPath()%>/solicitudEvidenciasPorEstatus.do?estatus=2&areaSolicitante=2', 
				datatype: "xml", 
				colNames:['N&uacute;mero de expediente','N&uacute;mero de caso','Cadena de custodia','Numero de evidencia','Nombre del solicitante','Fecha l&iacute;mite','Fecha ultima de modificaci&oacute;n' ], 
				colModel:[ 	{name:'NumeroExpediente',index:'1', width:200},
				           	{name:'NumeroCaso',index:'2', width:200},
				           	{name:'CadenaCustodia',index:'3', width:200},
				           	{name:'NumeroEvidencia',index:'4', width:150},
				           	{name:'NombreSolicitante',index:'5', width:200},
							{name:'FechaLimite',index:'6', width:50},
				           	{name:'FechaUltimaModificacion',index:'7', width:50, hidden:true}
						],
				pager: jQuery('#pagerEvidenciasPendientes'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				//autoheight:true,
				width:"100%",
				height:440,
				sortname: '1',
				viewrecords: true,
				sortorder: "desc"
			}).navGrid('#pagerGridEvidenciasPendientes',{edit:false,add:false,del:false});

			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridEvidenciasPendientes=false;
		}
		else{
			jQuery("#gridEvidenciasPendientes").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/solicitudEvidenciasPorEstatus.do?estatus=2&areaSolicitante=2',datatype: "xml" });
			$("#gridEvidenciasPendientes").trigger("reloadGrid");			
		}
		
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridEvidenciasPendientes');
	}
	
	/*
	*Funcion que carga el grid de evidencias concluidas
	*/
	function cargaGridEvidenciasConcluidas(){
		if(firstGridEvidenciasConcluidas == true){
			
			jQuery("#gridEvidenciasConcluidas").jqGrid({ 
				url:'<%= request.getContextPath()%>/solicitudEvidenciasPorEstatus.do?estatus=3&areaSolicitante=2', 
				datatype: "xml", 
				colNames:['N&uacute;mero de expediente','N&uacute;mero de caso','Cadena de custodia','N&uacute;mero de evidencia','Nombre del solicitante','Perito responsable','Fecha inicio de pr&eacute;stamo','Fecha fin de pr&eacute;stamo','Fecha de cierre' ], 
				colModel:[ 	{name:'NumeroExpediente',index:'1', width:200},
				           	{name:'NumeroCaso',index:'2', width:150},
				           	{name:'CadenaCustodia',index:'3', width:150},
				           	{name:'NumeroEvidencia',index:'4', width:150},
				           	{name:'NombreSolicitante',index:'5', width:200},
							{name:'PeritoResponsable',index:'6', width:200},
				           	{name:'FechaInicioPrestamo',index:'7', width:50}, 
				           	{name:'FechaFinPrestamo',index:'8', width:50}, 
							{name:'FechaCierre',index:'9', width:50}
						],
				pager: jQuery('#pagerEvidenciasConcluidas'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				//autoheight:true,
				width:"100%",
				height:440,
				sortname: '1',
				viewrecords: true,
				sortorder: "desc"
			}).navGrid('#pagerGridEvidenciasConcluidas',{edit:false,add:false,del:false});

			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridEvidenciasConcluidas=false;
		}
		else{
			jQuery("#gridEvidenciasConcluidas").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/solicitudEvidenciasPorEstatus.do?estatus=3&areaSolicitante=2',datatype: "xml" });
			$("#gridEvidenciasConcluidas").trigger("reloadGrid");			
		}
		
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridEvidenciasConcluidas');
	}

	/*
	*Funcion que carga el grid de resguardo de evidencias
	*/
	function cargaGridResguardoEvidencia(){
		if(firstGridResguardoEvidencia == true){
			
			jQuery("#gridResguardoEvidencia").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultaResguardoEvidencias.do', 
				datatype: "xml", 
				colNames:['N&uacute;mero de Caso','Nombre AMP','Folio de Cadena de Custodia','Numero de evidencias en Resguardo','Nombre del Almacen' ], 
				colModel:[ 	{name:'NumeroCaso',index:'1', width:200},
				           	{name:'NombreAMP',index:'2', width:200},
				           	{name:'FolioCadenaCustodia',index:'3', width:150},
				           	{name:'NumeroEvidencias',index:'4', width:200}, 
							{name:'Nombre del Almacen',index:'5', width:150}
						],
				pager: jQuery('#pagerResguardoEvidencia'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				//autoheight:true,
				width:"100%",
				height:440,
				sortname: '1',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					solicitaDevolucionEvidencia(rowid);//cambiar o ajustar por metodo correcto CU generacion de devolucion de evidencia
				}
			}).navGrid('#pagerGridResguardoEvidencia',{edit:false,add:false,del:false});

			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridResguardoEvidencia=false;
		}
		else{
			jQuery("#gridResguardoEvidencia").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultaResguardoEvidencias.do',datatype: "xml" });
			$("#gridResguardoEvidencia").trigger("reloadGrid");			
		}
		
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridResguardoEvidencia');
	}

	/*
	*Funcion que oculta o muestra los grids, recibe como parametro
	*el nombre del grid que va a mostrar, y todos los demas, se 
	*ocultaran
	*/
	function ocultaMuestraGrids(nombreGrid){

		if(nombreGrid == "gridResguardoEvidencia"){

			$("#divDetalleAsignacionesRecibidasNoAtendidas").hide();	
			$("#divGridAsignacionesRecibidasNoAtendidas").hide();
			$("#divGridEvidenciasPendientes").hide();
			$("#divGridEvidenciasConcluidas").hide();
			$("#divGridEvidenciasNuevas").hide();
			$("#divDetalleEvidencia").hide();
			$("#divGridDocumentosRecibidos").hide();	
			$("#divGridResguardoEvidencia").show();	
		}
		if(nombreGrid == "gridDocumentosRecibidos"){

			$("#divDetalleAsignacionesRecibidasNoAtendidas").hide();
			$("#divGridAsignacionesRecibidasNoAtendidas").hide();
			$("#divGridEvidenciasPendientes").hide();
			$("#divGridEvidenciasConcluidas").hide();
			$("#divGridEvidenciasNuevas").hide();
			$("#divDetalleEvidencia").hide();
			$("#divGridDocumentosRecibidos").show();	
			$("#divGridResguardoEvidencia").hide();	
		}
		if(nombreGrid == "gridEvidenciasNuevas"){

			$("#divDetalleAsignacionesRecibidasNoAtendidas").hide();
			$("#divGridAsignacionesRecibidasNoAtendidas").hide();
			$("#divGridResguardoEvidencia").hide();	
			$("#divGridDocumentosRecibidos").hide();	
			$("#divGridEvidenciasNuevas").show();
			$("#divGridEvidenciasPendientes").hide();
			$("#divGridEvidenciasConcluidas").hide();
			$("#divDetalleEvidencia").hide();
		}
		if(nombreGrid == "gridEvidenciasPendientes"){

			$("#divDetalleAsignacionesRecibidasNoAtendidas").hide();
			$("#divGridAsignacionesRecibidasNoAtendidas").hide();
			$("#divGridResguardoEvidencia").hide();	
			$("#divGridDocumentosRecibidos").hide();	
			$("#divGridEvidenciasNuevas").hide();
			$("#divGridEvidenciasPendientes").show();
			$("#divGridEvidenciasConcluidas").hide();
			$("#divDetalleEvidencia").hide();
		}
		if(nombreGrid == "gridEvidenciasConcluidas"){

			$("#divDetalleAsignacionesRecibidasNoAtendidas").hide();
			$("#divGridAsignacionesRecibidasNoAtendidas").hide();
			$("#divGridResguardoEvidencia").hide();	
			$("#divGridDocumentosRecibidos").hide();	
			$("#divGridEvidenciasNuevas").hide();
			$("#divGridEvidenciasPendientes").hide();
			$("#divGridEvidenciasConcluidas").show();
			$("#divDetalleEvidencia").hide();
		}
		if(nombreGrid == "gridDetalleEvidencia"){

			$("#divDetalleAsignacionesRecibidasNoAtendidas").hide();
			$("#divGridAsignacionesRecibidasNoAtendidas").hide();
			$("#divGridResguardoEvidencia").hide();	
			$("#divGridDocumentosRecibidos").hide();	
			$("#divGridEvidenciasNuevas").hide();
			$("#divGridEvidenciasPendientes").hide();
			$("#divGridEvidenciasConcluidas").hide();
			$("#divDetalleEvidencia").show();
		}

		if(nombreGrid == "gridAsignacionesRecibidasNoAtendidas"){

			
			$("#divGridResguardoEvidencia").hide();	
			$("#divGridDocumentosRecibidos").hide();	
			$("#divGridEvidenciasNuevas").hide();
			$("#divGridEvidenciasPendientes").hide();
			$("#divGridEvidenciasConcluidas").hide();
			$("#divDetalleEvidencia").hide();
			
			$("#divDetalleAsignacionesRecibidasNoAtendidas").hide();
			$("#divGridAsignacionesRecibidasEnProceso").hide();
			$("#divGridAsignacionesRecibidasNoAtendidas").show();
		}

		if(nombreGrid == "gridAsignacionesRecibidasEnProceso"){

			
			$("#divGridResguardoEvidencia").hide();	
			$("#divGridDocumentosRecibidos").hide();	
			$("#divGridEvidenciasNuevas").hide();
			$("#divGridEvidenciasPendientes").hide();
			$("#divGridEvidenciasConcluidas").hide();
			$("#divDetalleEvidencia").hide();

			$("#divGridAsignacionesRecibidasNoAtendidas").hide();
			$("#divDetalleAsignacionesRecibidasNoAtendidas").hide();
			$("#divGridAsignacionesRecibidasEnProceso").show();
		}
	}

/////////////////////////////////////////////////////////////////////////FUNCIONALIDAD PARA ASIGNACIONES RECIBIDAS ////////////////////////////////////////////////////////////////
	/*
	*Funcion que carga el grid de solicitudes periciales no atendidas por el perito, tanto las de asesor&iacute;a, como las de dictamen
	*/
	function cargaGridAsignacionesRecibidasNoAtendidas(){
	
		if(firstGridAsignacionesRecibidasNoAtendidas == true){
						
			jQuery("#gridAsignacionesRecibidasNoAtendidas").jqGrid({ 
				
				url:'<%= request.getContextPath()%>/asignacionesRecibidasNoAtendidas.do', 
				datatype: "xml", 
				colNames:['Nombre del Solicitante','Tipo de Asignacion','N&uacute;mero de Expediente','N&uacute;mero de Caso','Fecha L&iacute;mite','Acuse' ], 
				colModel:[ 	{name:'NombreSolicitante',index:'1', width:180},
				           	{name:'TipoAsignacion',index:'2', width:90},
				           	{name:'NumExpediente',index:'3', width:150},
				           	{name:'NumCaso',index:'4', width:150}, 
							{name:'FechaLimite',index:'5', width:60},
				           	{name:'Acuse',index:'6', width:40}
						],
				pager: jQuery('#pagerGridAsignacionesRecibidasNoAtendidas'),
				//rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				//autoheight:true,
				height:420,
				sortname: '1',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					confirmarAsignacion(rowid);
				},
				onSelectRow: function(rowid){
					mostrarDetalle(rowid);
				}
			}).navGrid('#pagerGridAsignacionesRecibidasNoAtendidas',{edit:false,add:false,del:false});
			$("#gview_gridAsignacionesRecibidasEnProceso .ui-jqgrid-bdiv").css('height', '460px');
			
			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridAsignacionesRecibidasNoAtendidas=false;
		}
		else{
			jQuery("#gridAsignacionesRecibidasNoAtendidas").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/asignacionesRecibidasNoAtendidas.do',datatype: "xml" });
			$("#gridAsignacionesRecibidasNoAtendidas").trigger("reloadGrid");			
		}
		
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridAsignacionesRecibidasNoAtendidas');
	}


	/*Consulta el detalle de la asignacion, es decir el motivo*/
	function mostrarDetalle(rowid){
				
		//Le recorta el alto del grid, para que se muestre el detalle 
		jQuery("#gridAsignacionesRecibidasNoAtendidas").jqGrid('setGridHeight','213px');
		$("#divDetalleAsignacionesRecibidasNoAtendidas").show();
		
		$.ajax({			
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/consultarDetalleDeSolicitud.do',
    		data: 'solicitudPericialId='+rowid,
    		dataType: 'xml',
    		async: true,
    		success: function(xml){

    			var errorCode;
				errorCode=$(xml).find('response').find('code').text();				
				if(parseInt(errorCode)==0){	
					//Detalle de la asignacion
					$('#txtFieldDetalleAsignacionesRecibidas').val($(xml).find('motivo').first().text() );	
    			}
				else{
					//alert("Error al cargar los datos del usuario");
				}
    		}
    	});
	}


	//Funcion que agrega un testigo
	  function confirmarAsignacion(rowid){

		$("#divConfirmarAsignacion").dialog("open");
		$("#divConfirmarAsignacion").dialog({ autoOpen: true, 
			modal: true, 
		  	title: 'Desean confirmar la asignaci&oacute;n?', 
		  	dialogClass: 'alert',
		  	position: [600,200],
		  	width: 200,
		  	height: 100,
		  	maxWidth: 200,
		  	buttons:{"Si":function() {
			  		
		  		$.ajax({			
		    		type: 'POST',
		    		url: '<%=request.getContextPath()%>/confirmarAsignacionDePerito.do',
		    		data: 'solicitudPericialId='+rowid,
		    		dataType: 'xml',
		    		async: true,
		    		success: function(xml){

		    			var errorCode;
						errorCode=$(xml).find('response').find('code').text();
						if(parseInt(errorCode)==0){
							refrescaGridAsignacionesRecibidasNoAtendidas();	
		    			}
						else{
							//alert("Error al cargar los datos del usuario");
						}
		    		}
		    	});
					
		  		$(this).dialog("close");
		  	},
		  	"No":function() {
		  		$(this).dialog("close");
		  	}
		  }
		});	  	
	}

	
   /*
	*Funcion que refresca el grid, limpia la caja de texto la oculta y le da las nuevas dimensiones al gird
	*/
	function refrescaGridAsignacionesRecibidasNoAtendidas(){

		//Recargamos el grid de asignaciones no atendidas, para que no aparesca la asignacion que confirmamos
		jQuery("#gridAsignacionesRecibidasNoAtendidas").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/asignacionesRecibidasNoAtendidas.do',datatype: "xml" });
		$("#gridAsignacionesRecibidasNoAtendidas").trigger("reloadGrid");
		//limpiamos el texto del detalle
		$("#txtFieldDetalleAsignacionesRecibidas").val("");
		//ocultamos el detalle 
		ocultaMuestraGrids('gridAsignacionesRecibidasNoAtendidas');
		//le damos las dimensiones del grid sin, detalle
		jQuery("#gridAsignacionesRecibidasNoAtendidas").jqGrid('setGridHeight','420px');
	}	


   /*
   *Funcion que carga el grid de asignaciones periciales en proceso
   */
   function cargaGridAsignacionesRecibidasEnProceso(){

	  
	   if(firstGridAsignacionesRecibidasEnProceso== true){
		   
		   jQuery("#gridAsignacionesRecibidasEnProceso").jqGrid({ 
	
				url:'<%=request.getContextPath()%>/consultarSolicitudesPericialesPeritoEnProceso.do', 
				datatype: "xml", 
				colNames:['N&uacute;mero Expediente','N&uacute;mero de Caso','Fecha L&iacute;mite','Fecha &Uacute;ltima Modificaci&oacute;n','Documento creado'], 
				colModel:[ 	{name:'numeroExpediente',index:'1', width:220,align:'center'}, 
				           	{name:'numeroCaso',index:'2', width:220,align:'center'}, 
							{name:'fechaLimite',index:'3', width:150,align:'center'},
							{name:'fechaUltimaModificacion',index:'4', width:150,align:'center', hidden:true},
							{name:'documentoCreado',index:'5', width:160,align:'center'}
							
						],
				pager: jQuery('#pagerGridAsignacionesRecibidasEnProceso'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				sortname: 'numeroExpediente',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid){
					mostrarVisorAtencionSolicitudesPericiales(rowid);
				}
			}).navGrid('#pagerGridAsignacionesRecibidasEnProceso',{edit:false,add:false,del:false});
		   $("#gview_gridAsignacionesRecibidasEnProceso .ui-jqgrid-bdiv").css('height', '460px');
		   firstGridAsignacionesRecibidasEnProceso = false;
	   }
	   else{
			jQuery("#gridAsignacionesRecibidasEnProceso").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarSolicitudesPericialesPeritoEnProceso.do',datatype: "xml" });
			$("#gridAsignacionesRecibidasEnProceso").trigger("reloadGrid");			
		}
		
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridAsignacionesRecibidasEnProceso');
	}

	function mostrarVisorAtencionSolicitudesPericiales(rowId){

		params = rowId.split(",");
		documentoId = params[1];
		solicitudId = params[0];

		idWindowVisorAntencionSolPer++;
		$.newWindow({id:"iframewindowVisorAtnSolPer"+idWindowVisorAntencionSolPer, statusBar: true, posx:150,posy:30,width:1200,height:600,title:"Atencion solicitdes periciales", type:"iframe"});
	    $.updateWindowContent("iframewindowVisorAtnSolPer"+idWindowVisorAntencionSolPer,'<iframe src="<%= request.getContextPath() %>/visorAtencionSolicitudesPericiales.do?solicitudId='+solicitudId+'&documentoId='+documentoId+'" width="1200" height="600" />');		

   }
   
	function ejecutaChat() {
		$("#dialogoChat").dialog( "open" );
	}  

function visorLeyesCodigos() {
		
		$.newWindow({id:"iframewindowRestaurativa", statusBar: true, posx:200,posy:50,width:800,height:500,title:"Leyes y C&oacute;digos", type:"iframe"});
	    $.updateWindowContent("iframewindowRestaurativa",'<iframe src="<%= request.getContextPath() %>/detalleLeyesyCodigos.do" width="800" height="500" />');
	    		
	}

	/*
	*Funcion que llama a la funcionalidad para generar un visualizador de imagen  $('#imageViewer').click(generaVisorGraficaView);
	*/
	function generaVisorGraficaView() {
		$.newWindow({id:"iframewindowWindowImageViewer", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Visor de imagenes", type:"iframe"});
	    $.updateWindowContent("iframewindowWindowImageViewer",'<iframe src="<%=request.getContextPath()%>/VisorGraficas.do" width="1140" height="400" />');
	    		
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
    			//alert($(xml).find('alertaDTO').find('nombre').text());
    			//alert('la primera op:'+op);
    			
    			//alert('la xml op:'+$(xml).find('alertaDTO').find('esAplaza').text());
    			//alert('la segunda op:'+op);
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
	    			//alert($(xml).find('alertaDTO').find('nombre').text());
	    			if(estatus=="posponer")
	    			{
	    				alert("Alarma pospuesta.");
	    			}
	    			else if(estatus=="cancelar")
	    			{
	    				alert("Alarma cancelada");
	    			}
	    			else
	    			{
	    				alert("Alarma aceptada.");
	    			}
	   		}
		});
	}

	function llamaraCambia(op,idAlerta){
		//alert('la segunda op:'+op);
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
	
</script>
</head>

<body>

<!--Comienza ui-layout-west-->
<div class="ui-layout-west">
<div class="header">&nbsp;</div>
<div class="content">
<div id="accordionmenuprincipal">
<h3><a id="bPeritoEvidencia" href="#"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Asignaciones Recibidas</a></h3>
<div>
	<ul id="seccion2tree" class="filetree">	
	   <li><span><img src="<%=request.getContextPath()%>/resources/images/icn_folderchek.png" width="20" height="16"  /><a id="evidenciaNueva" onclick="cargaGridAsignacionesRecibidasNoAtendidas();">Nuevas</a></span></li>
	   <li><span><img src="<%=request.getContextPath()%>/resources/images/icn_folderchek.png" width="20" height="16"  /><a id="evidenciaPendiente" onclick="cargaGridAsignacionesRecibidasEnProceso();">Pendientes</a></span></li>
	   <li><span><img src="<%=request.getContextPath()%>/resources/images/icn_folderchek.png" width="20" height="16"  /><a id="evidenciaConcluida" onclick="">Concluidas</a></span></li>	
	</ul>
</div>
</div>
</div>
</div>
<!--Fin ui-layout-west-->

<!--Comienza ui-layout-east-->
<div class="ui-layout-east">
<div class="header">Bienvenido</div>
<div class="content">
<div id="accordionmenuderprincipal">
<h4><a href="#">Datos Personales</a></h4>
<div>
					<center>
						<jsp:include page="/WEB-INF/paginas/datosPersonalesUsuario.jsp" flush="true"></jsp:include>
					</center>
</div>
<!-- <h4><a href="#">Calendario</a></h4>
<div>
					<center>
						<a href="#"><img src="<%=request.getContextPath()%>/resources/images/img_calendario.png" width="201" height="318"></a>
					</center>
</div>-->
<h6><a href="#">Agenda</a></h6>
<div>

	<center>
		<jsp:include page="/WEB-INF/paginas/agendaUsuario.jsp" flush="true"></jsp:include>
	</center>
	<br/>
</div>
<h6><a href="#" id="" onclick="visorLeyesCodigos()">Consultar Leyes y C&oacute;digos</a></h6>
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
</div>
<h1><a href="#">Chat</a></h1>
<div>
	<div id="dialogoChat" title="Chat" align="center">
		<iframe src="<%=((ConfiguracionDTO)session.getAttribute(LoginAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getUrlServidorChat()%>" frameborder="0" width="380" height="280"></iframe>
	</div>
	<center>
		<a onclick="ejecutaChat();" id="controlChat"><img src="<%= request.getContextPath()%>/resources/images/img_chat.png" width="130" height="104"></a>
	</center>
</div>
<!--<h1><a href="#">Clima</a></h1>-->
<!--<div align="left">-->
<!--	<div align="left" id="test"></div>-->
<!--</div>-->
<h1><a href="#" onclick="consultarTiposRol();">Facultades</a></h1>
				<div>
					<table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="0"  style="cursor:pointer" id="tableRolMenu">
					</table>
					<form name="frmRol2" action="<%= request.getContextPath() %>/rolRedirec.do" method="post">
						<input type="hidden" name="rolname" />
					</form>
				</div>
</div>
</div>
</div>
<!--Termina ui-layout-east-->

<!--Comienza ui-layout-north-->
<div class="ui-layout-north">
<div class="content">
			<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" background="<%=request.getContextPath()%>/resources/images/top_gral_sin_version.jpg" height="100%">
				  <TBODY>
					  <TR>
					    <TD width=100 align=left valign="middle"><img src="<%=request.getContextPath()%>/resources/images/logo_gral.png"></TD>
                        <TD width=301 align=left valign="middle">
                        	<div class='nombreInstitucion'><%=rolDTO.getDescripcionRol()%></div>
                        	<div class='versionCodigo'>${nsjp.version.codigo}</div>
                        </TD>
					    <TD width=126 align=left valign="top"><SPAN></SPAN></TD>
					    <TD width=272 align=center valign="top">&nbsp;</TD>
					    <TD width=28 align=middle>&nbsp;</TD><!--<td width="150" align="center"></td>-->
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

<!--comienza barra de herramientas-->
<ul class="toolbar ui-widget-header">
	<div id="menu_head">
	<li id="tbarBtnHeaderZise" class="first"><span></span></li>
	</div>
	<div id="menu_config">
	<li onclick="buscarCaso();">Buscar Caso&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_busca2.png" width="15" height="16"></li>
	<li onclick="buscarExpediente();">Buscar ExpedienteBuscar Expediente&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_busca3.png" width="15" height="16"></li>
	<li id="generarDocumento"><span></span>Generar Documento&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_dctowri.png" width="15" height="16"></li>
	<li><span></span>Adjuntar documento&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_dctoadjun.png" width="10" height="16"></li>
	</div>
</ul>
<!--termina barra de herramientas--></div>
<!--Termina ui-layout-north-->

<!--Comienza ui-layout-south-->
<div class="ui-layout-south">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="ui-widget-header ui-state-hover">
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
<!--Termina ui-layout-south-->

<!--Comienza main content-->
<div id="mainContent">
<div class="ui-layout-center">
<div class="ui-layout-content">
<div class="ui-layout-north">

		<div id="divGridEvidenciasNuevas">
			<table id="gridEvidenciasNuevas"></table>
			<div id="pagerGridEvidenciasNuevas"></div>
		</div>					
		<div id="divGridEvidenciasPendientes">
			<table id="gridEvidenciasPendientes"></table>
			<div id="pagerGridEvidenciasPendientes"></div>
		</div>					
		<div id="divGridEvidenciasConcluidas">
			<table id="gridEvidenciasConcluidas"></table>
			<div id="pagerGridEvidenciasConcluidas"></div>
		</div>					

		<div id="divDetalleEvidencia">
			<table id="gridDetalleEvidencia"></table>
			<div id="pagerGridDetalleEvidencia"></div>
		</div>

		<div id="divGridDocumentosRecibidos">
			<table id="gridDocumentosRecibidos"></table>
			<div id="pagerGridDocumentosRecibidos"></div>
		</div>					
		<div id="divGridResguardoEvidencia">
			<table id="gridResguardoEvidencia"></table>
			<div id="pagerGridResguardoEvidencia"></div>
		</div>
		
		<!--div en el cual se nuestran las asignaciones recibidas para el perito-->
		<div id="divGridAsignacionesRecibidasNoAtendidas">
			<table id="gridAsignacionesRecibidasNoAtendidas"></table>
			<div id="pagerGridAsignacionesRecibidasNoAtendidas"></div>
		</div>
		
		<!--div en el cual se muestra el detalle "motivo" de la asignacion recibida-->
		<div id="divDetalleAsignacionesRecibidasNoAtendidas" align="center">
			<table width="925" border="0" cellspacing="0" cellpadding="0" bgcolor="#EEEEEE">
			  <tr height="20">
			    <td><strong>Detalle de la asignaci&oacute;n:</strong></td>
			  </tr>
			  <tr height="180">
			    <td align="center">
			    	<textarea cols="110" id="txtFieldDetalleAsignacionesRecibidas" rows="9" readonly="readonly"> </textarea>
			    </td>
			  </tr>
			</table>
		</div>
		
		<!--div para la ventana modal en donde se muestra la confirmacion de la asignacion-->
		<div id="divConfirmarAsignacion" style="display: none"></div>
		
		<!--div en el cual se nuestran las asignaciones recibidas para el perito en proceso-->
		<div id="divGridAsignacionesRecibidasEnProceso">
			<table id="gridAsignacionesRecibidasEnProceso"></table>
			<div id="pagerGridAsignacionesRecibidasEnProceso"></div>
		</div>
		
		
</div>
</div>
</div>
<!--Termina main content-->
	<div id="dialog-logout" title="Cerrar Sesi&oacute;n">
		<p align="center">
			<span id="logout">&iquest;Desea cerrar su sesi&oacute;n?</span>
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
					<td colspan="2">La sesi&oacute;n se ha bloqueado, introduce tu contrase&ntilde;a para desbloquear.</td>
					
				</tr>
				<tr>
					<td align="right"><label style="color:#4A5C68">Contrase&ntilde;a:</label></td>
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
	<div id="dialogBlok" title="�Su sesi&oacute;n est&aacute; a punto de caducar!">
			<p>
				<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 50px 0;"></span>
				La sesi&oacute;n se cerrar&aacute; en <span id="dialog-countdown" style="font-weight:bold"></span> segundos.
			</p>

			<p>&iquest;Desea continuar con la sesi&oacute;n?</p>
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

