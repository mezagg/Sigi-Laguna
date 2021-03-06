<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<% 
	
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	RolDTO rolDTO = usuario.getRolACtivo().getRol();
	String institucion=usuario.getInstitucion().getConfInstitucionId().toString();
	
	
 %>
<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setHeader("Cache-Control", "max-age=0");
response.setHeader("Cache-Control", "must-revalidate");
response.setDateHeader("Expires", 0); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Expires" CONTENT="Sat, 6 May 1995 12:00:00 GMT">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-store, no-cache, must-revalidate">
<META HTTP-EQUIV="Cache-Control" CONTENT="post-check=0, pre-check=0">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<!--COMIENZA CSS DEL DOCUMENTO-->
	<!--css para ventanas-->
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	
	<!--css para el estilos de jquery-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/1.8.10/${theme.name}/jquery-ui.css" />
	
	<!--css para estilo de los arboles-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	
	<!--estilo ultrasist-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	
	<!--estilo del grid-->
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/1.8.10/${theme.name}/jquery-ui.css" />
	 
<!--COMIENZAN SCRIPTS-->
	
	<!--jquery-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimeout.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimer.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	
	<!--para controlar las ventanas-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>

	
	<!--para creacion de arboles-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/reloj.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>			
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/sesion.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
		
<script type="text/javascript">
	var contextoPagina = "${pageContext.request.contextPath}";
	var sesionActiva = '<%= (request.getSession().getAttribute(LoginAction.KEY_SESSION_USUARIO_FIRMADO)!=null)%>';
	var tiempoActiva = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoBloqueoSesion()%>';
	estaSesionActiva();
	
	var outerLayout, innerLayout;
	
	var validaFecha = false;
	//Variable que controla que se cargue por primera vez el grid de consulta de plantillas
	var firstGridAdmonPlantillaConsulta = true;

	//Variable para controlar el id de la ventana de crear o editar plantilla
	var idWindowCrearEditarPlantilla = 1;
	var institucion = '<%=institucion%>';
	
	$(document).ready(function() {
		var op=1;
		var opArchivosMenu=1;
		outerLayout = $("body").layout( layoutSettings_Outer );
		$.ajax({
	   		type: 'POST',
	    		url: '<%=request.getContextPath()%>/busquedaParametro.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: true,
	    		success: function(xml){
	    			op=$(xml).find("bandera").text();
	    			
	   		}
		});
		$.ajax({
	   		type: 'POST',
	    		url: '<%=request.getContextPath()%>/buscarParametroArchivos.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: true,
	    		success: function(xml){
	    			opArchivosMenu=$(xml).find("bandera").text();
	    			
	   		}
		});
		jQuery(document).ajaxStop(jQuery.unblockUI);
			//crea el acordeon
		$("#accordionmenuprincipal").accordion({  fillSpace: true });
		$("#accordionmenuderprincipal").accordion({ fillSpace: true});
		$("#accordionmenuderprincipal").accordion( "option", "icons", null );
		//crea el arbol de administrar usuarios
		$("#seccion3treeAdmin").treeview();
		//crea el arbol de administrar funcionarios
		$("#seccion2treeAdmin").treeview();
		$("#seccion2tree").treeview();
		$("#seccion3treeAdminBitacoras").treeview();
		//crea el arbol de administrar modulos
		$("#seccion4treeAdminModulosRol").treeview();
		//agrega el evento generar documento 
		$("#generarDocumento").click(generarDocumentoView);
		//agreaga el evento para crear la agenda
		$("#controlAgenda").click(creaAgenda);
		$("#reportes").click(generaVisorReporteView);
		$("#graficas").click(generaVisorGraficaView);
		
		$("#ingresaUsuario").click(ventanaNuevoFuncionario);
		$("#modificaUsuario").click(ventanaModificarUsuario);
		$("#agregaUsuarioChat").click(modificaUsuarioChat);
		$("#agregaArchivosDisco").click(modificaArchivosDisco);
		$("#modificaFuncionario").click(ventanaModificarFuncionario);
		$("#administrarModulosPorRol").click(ventanaAdministrarModulosPorRol);
		$("#administrarSubRoles").click(ventanaAdministrarSubRoles);
		
		$("#registroDiasInhabiles").click(abreDiasInhabiles);
		$("#administracionCatalogos").click(administrarCatalogos);

		$("#consulMovimientosAlmacen").click(ventanaBitacoraMovObjetosAlmacen);
		
		
		$("#plantillas").click();
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

		//obtenemos el tiempo de las alarmas y ponemos en marcha el timer		
		 var tiempo='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoRevisionAlarmas()%>';
		 setInterval(muestraAlerta, tiempo);
		 
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
					
		cargaGridAdmonPlantillaConsulta();

		//**Funcionalidad para ceja de Estado del Expediente*****/
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
		
		//Carga el reloj
		muestraGadgets();
		//Oculta el valor para agregar usuarios del nsjp a las tablas del chat
		//alert(op);
		if(op==1){
			$("#idChat").hide();
		}
		if(opArchivosMenu==1){
			$("#idArchivo").hide();	
		}
		var ambiente='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAmbiente()%>';
		if(ambiente == undefined || ambiente == "undefined"){
			ambiente = "";
		}
		$("#ambienteLb").html('<strong><big>'+ambiente+'</big></strong>');
		if(institucion=='<%=Instituciones.DEF.getValorId()%>'){
			$("#bitacoras").hide();
			$("#bitacorasDiv").hide();
			$("#adminModulosRol").hide();
			$("#adminModulosRolDiv").hide();
		}
		$("#idChatMenu").hide();
		$("#idChatMenuDiv").hide();
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
		,	west__onresize:		function () {	$("#accordionmenuprincipal").accordion("resize");
											}
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
		,	onresize_end:			function () {$("#gridAdmonPlantillaConsulta").setGridWidth($("#mainContent").width() - 5, true);}
		}
	};

	/////////////////////////////////////////////// COMIENZA FUNCIONALIDAD COMUN //////////////////////////////////////////////////////////////////

	/*
	 * Genera ventana para registrar a un nuevo funcionario 
	 */
	function ventanaNuevoFuncionario(){
		
		$.newWindow({id:"iframewindowIngresarFuncionario", statusBar: true, posx:200,posy:50,width:930,height:550,title:"Registrar Nuevo Funcionario", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarFuncionario",'<iframe src="<%=request.getContextPath()%>/registrarFuncionario.do?administrador=1" width="930" height="550" />'); 
						
		}

	/*
	 * Genera ventana para modificar a un usuario 
	 */

	function ventanaModificarUsuario(){

		$.newWindow({id:"iframewindowModificarUsuario", statusBar: true, posx:200,posy:50,width:930,height:550,title:"Modificar Usuario", type:"iframe"});
	    $.updateWindowContent("iframewindowModificarUsuario",'<iframe src="<%=request.getContextPath()%>/registrarFuncionario.do?administrador=2" width="930" height="550" />'); 

		}
	
	function modificaUsuarioChat(){
		$.ajax({
	   		type: 'POST',
	    		url: '<%=request.getContextPath()%>/agregaUsuariosChat.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: true,
	    		success: function(xml){
	   		}
		});
		$("#idChat").hide();
	}
	
	function modificaArchivosDisco(){
		$("#idArchivo").hide();
		$.ajax({
	   		type: 'POST',
	    		url: '<%=request.getContextPath()%>/agregaArchivosDisco.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: true,
	    		success: function(xml){
	   		}
		});
		
	}
	
	

	/*
	 * Genera ventana para modificar a un funcionario 
	 */

	function ventanaModificarFuncionario(){

		$.newWindow({id:"iframewindowModificarFuncionario", statusBar: true, posx:200,posy:50,width:930,height:550,title:"Modificar Funcionario", type:"iframe"});
	    $.updateWindowContent("iframewindowModificarFuncionario",'<iframe src="<%=request.getContextPath()%>/registrarFuncionario.do?administrador=3" width="930" height="550" />'); 

		}
	/*
	 * Genera ventana para modificar a un funcionario 
	 */

	function ventanaAdministrarModulosPorRol(){

		$.newWindow({id:"iframewindowAdministrarModulosRol", statusBar: true, posx:200,posy:25,width:900,height:400,title:"Modificar modulos asignados a un rol", type:"iframe"});
	    $.updateWindowContent("iframewindowAdministrarModulosRol",'<iframe src="<%=request.getContextPath()%>/administrarPermisoRol.do" width="1200" height="550" />'); 

		}
	function ventanaAdministrarSubRoles(){

	//	$.newWindow({id:"iframewindowAdministrarSubRoles", statusBar: true, posx:200,posy:25,width:900,height:400,title:"Administrar SubRoles", type:"iframe"});
		
	   // $.updateWindowContent("iframewindowAdministrarSubRoles",'<iframe src="<%=request.getContextPath()%>/administrarSubRoles.do" width="1200" height="550" />');
	    
	    customVentana("iframewindowAdministrarSubRoles","Administrar SubRoles","/administrarSubRoles.do");

		}
	
	
	/*
	 * Genera ventana para registrar dias inhabiles
	 */
	function abreDiasInhabiles(){
		
		$.newWindow({id:"iframewindowRegistrarDiasInhabiles", statusBar: true, posx:200,posy:50,width:900,height:400,title:"Registrar d&iacute;as inh&aacute;biles", type:"iframe"});
	    $.updateWindowContent("iframewindowRegistrarDiasInhabiles",'<iframe src="<%=request.getContextPath()%>/registrarDiasInhabiles.do" width="900" height="400" />'); 
						
		}	
	
	function administrarCatalogos(){
		$.newWindow({id:"iframewindowCatalogo", statusBar: true, posx:200,posy:10,width:1005,height:605,title:"Administraci&oacute;n de cat&aacute;logos", type:"iframe"});
	    $.updateWindowContent("iframewindowCatalogo",'<iframe src="<%=request.getContextPath()%>/AdministrarCatalogos.do" width="1005" height="605" />'); 		
	}
	

	/*
	*Funcion que llama a la funcionalidad para crear la agenda 
	*/
	function creaAgenda() {
		$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
	    $.updateWindowContent("iframewindowagenda",'<iframe src="<%=request.getContextPath()%>/InicioAgenda.do" width="1150" height="600" />');		
	}


	/*
	*Funcion que llama a la funcionalidad para generar un documento 
	*/
	function generarDocumentoView() {
		$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Generar Documento", type:"iframe"});
	    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%=request.getContextPath()%>/generarDocumento.do" width="1140" height="400" />');
	    		
	}	
	
	/**
	*Funcion que abre la ventana modal para la busqueda
	*/
	function abreModalCausa(){
		
		$("#datoCausa").val("");
		$("#divCausa").dialog("open");
	  	$("#divCausa").dialog({ autoOpen: true, 
			modal: true, 
		  	title: 'N&uacute;mero Expediente', 
		  	dialogClass: 'alert',
		  	position: [500,220],
		  	width: 350,
		  	height: 200,
		  	maxWidth: 1000,
		  	buttons:{"Realizar Busqueda":function() {
		  		mostrarVentanaInvolucradosCausa();
		  		$(this).dialog("close");
		  		},
				"Cancelar" : function() {
					$(this).dialog("close");
				}
		  	}
		});	  	
	}
	function agarraGenerarDocumento(){
		generarDocumento();
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
    		async: true,
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
	    		async: true,
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


	function generarDocumento() {
		
		//alert("forma id"+temDocumentoID);
		//alert("resolutivo id"+temVarResolutivo);
		//alert("audiencia id"+idAudiencia);
		
		$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Resolutivo", type:"iframe"});
	    $.updateWindowContent("iframewindowGenerarDocumento","<iframe src='<%= request.getContextPath() %>/enviaResolutivo.do?formaId="+temDocumentoID+"&idAudiencia="+idAudiencia+"&idResolutivo="+temVarResolutivo+"&esconderArbol=1' width='1140' height='400' />");
	   		
	}

	/*
	 * Genera ventana para Bitacora MovObjetosAlmacen
	 */
	function ventanaBitacoraMovObjetosAlmacen(){
		
		$.newWindow({id:"iframewindowBitacoraMovObjetosAlmacen", statusBar: true, posx:200,posy:50,width:1070,height:570,title:"Consultar Movimiento de Objetos de Almac&eacute;n", type:"iframe"});
	    $.updateWindowContent("iframewindowBitacoraMovObjetosAlmacen",'<iframe src="<%=request.getContextPath()%>/bitacoraConsultaMovObjetosAlmacen.do" width="1070" height="570" />'); 
						
	}
	
	function abreModalPlantilla(){
		
		cargaTipoDocumentos();
		$("#divTipoDocto").dialog("open");
	  	$("#divTipoDocto").dialog({ autoOpen: true, 
			modal: true, 
		  	title: 'Tipo de Documento', 
		  	dialogClass: 'alert',
		  	position: [500,220],
		  	width: 350,
		  	height: 200,
		  	maxWidth: 1000,
		  	buttons:{"Realizar Consulta":function() {
		  		cargaGridAdmonPlantillaConsulta();
		  			$(this).dialog("close");
		  		},
				"Cancelar" : function() {
					$(this).dialog("close");
				}
		  	}
		});	  	
	}
	
	/*
	*Funcion que realiza la carga del combo de tipos de documento
	*/
	function cargaTipoDocumentos() {
		 
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoTiposFormas.do',
			data: '',
			dataType: 'xml',
			async: true,
			success: function(xml){
				$(xml).find('catDocumentos').each(function(){
					$('#cbxTipoDocto').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
			}
		});
	}
	
	//Abre una ventana para crear o editar un documento		
	function crearOEditarPlantilla(plantillaId){
		idWindowCrearEditarPlantilla++;
		var tituloVentana = "Crear plantilla";
		
		if(parseInt(plantillaId) > 0){
			tituloVentana = "Editar plantilla";
		}
		
		$.newWindow({id:"iframewindowCrearOEditarPlantilla" + idWindowCrearEditarPlantilla, statusBar: true, posx:50,posy:50,width:1250,height:400,title:tituloVentana, type:"iframe"});
		$.updateWindowContent("iframewindowCrearOEditarPlantilla" + idWindowCrearEditarPlantilla,'<iframe src="<%= request.getContextPath() %>/crearOEditarPlantilla.do?plantillaId=' +plantillaId +'" width="1250" height="400" />');
		$("#" +"iframewindowCrearOEditarPlantilla" + idWindowCrearEditarPlantilla + " .window-maximizeButton").click();	
		}

	//Funcion para cerrar la ventana
	function cerrarVisorCrearOEditarPlantilla(){		
		$.closeWindow('iframewindowCrearOEditarPlantilla'+ idWindowCrearEditarPlantilla);
		idWindowCrearEditarPlantilla--;
	}

	
	/*
	*Carga el grid de las plantillas dependiendo del valor que se seleccione en la
	*el combo box de tipo de documento
	*/
	function cargaGridAdmonPlantillaConsulta(){
		
		var tipoDocumento = $('#cbxTipoDocto option:selected').val();

		if(firstGridAdmonPlantillaConsulta == true){
			
			jQuery("#gridAdmonPlantillaConsulta").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarPlantillasPorTipo.do?tipoDocumento='+tipoDocumento+'', 
				datatype: "xml", 
				colNames:['Nombre de la Plantilla','Tipo de Plantilla','Fecha de Creacion de la Plantilla'], 
				colModel:[ 	{name:'NombrePlantilla',index:'1', width:350},
				           	{name:'TipoPlantilla',index:'2', width:350},
				           	{name:'FechaCreacionPlantilla',index:'3', width:210, hidden:true} 
						],
				pager: jQuery('#pagerGridAdmonPlantillaConsulta'),
				rowNum:15,
				rowList:[15,30,45],
				autowidth: true,
				height:450,
				width:767,
				viewrecords: true,
				ondblClickRow: function(rowid) {
					crearOEditarPlantilla(rowid);
				}
			}).navGrid('#pagerGridAdmonPlantillaConsulta',{edit:false,add:false,del:false});

			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridAdmonPlantillaConsulta=false;
		}
		else{
			jQuery("#gridAdmonPlantillaConsulta").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarPlantillasPorTipo.do?tipoDocumento='+tipoDocumento+'',datatype: "xml" });
			$("#gridAdmonPlantillaConsulta").trigger("reloadGrid");	
			$("#" +"gridAdmonPlantillaConsulta"+".window-maximizeButton").click();			
		}
		
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridAdmonPlantillaConsulta');
	}
	
	function ocultaMuestraGrids(nombreGrid){
		$("#divGridAdmonPlantillaConsulta").hide();
		
		switch(nombreGrid){
			case "gridAdmonPlantillaConsulta": 
				$("#divGridAdmonPlantillaConsulta").show();
				break;
		}
	}
	/*
	 *Funcion para consultar los roles extras de cada usuario y
	 * construlle el arbol dinamico de los tipos de rol en el menu derecho
	 */
		
	
	function generaVisorReporteView() {
		$.newWindow({id:"iframewindowWindowReportViewer", statusBar: true, posx:85,posy:86,width:1140,height:499,title:"Indicadores", type:"iframe"});
	    $.updateWindowContent("iframewindowWindowReportViewer",'<iframe src="<%=request.getContextPath()%>/visorReportesAdmin.do" width="1140" height="499" />');
	    $("#" +"iframewindowWindowReportViewer" + " .window-maximizeButton").click();
	}
	
	/*
	*Funcion que llama a la funcionalidad para generar un visualizador de imagen
	*/
	function generaVisorGraficaView() {
		$.newWindow({id:"iframewindowWindowGraficaViewer", statusBar: true, posx:85,posy:86,width:1140,height:499,title:"Tablero de Control", type:"iframe"});
	    $.updateWindowContent("iframewindowWindowGraficaViewer",'<iframe src="<%=request.getContextPath()%>/VisorGraficas.do" width="1140" height="499" />');
	    $("#" +"iframewindowWindowGraficaViewer" + " .window-maximizeButton").click();
	}
	
	function visorLeyesCodigos() {
		
		$.newWindow({id:"iframewindowRestaurativa", statusBar: true, posx:50,posy:111,width:809,height:468,title:"Leyes y C&oacute;digos", type:"iframe"});
	    $.updateWindowContent("iframewindowRestaurativa",'<iframe src="<%= request.getContextPath() %>/detalleLeyesyCodigos.do" width="809" height="468" />');
	    		
	}
	
</script>
</head>

<body>

	<!--Comienza ui-layout-west-->
	<div class="ui-layout-west">
		<div class="header">&nbsp;</div>
		<div class="content">
			<div id="accordionmenuprincipal">
				<h3 ><a id="adminUsuarios">Administraci&oacute;n de Usuarios</a></h3>
				<div>			
					<ul id="seccion3treeAdmin" class="filetree">
						<li><span class="file"><a id="ingresaUsuario" style="cursor: pointer;">Ingresar Usuario</a></span></li>
						<li><span class="file"><a id="modificaUsuario" style="cursor: pointer;">Modificar Usuario</a></span></li>
						<li id="idChat"><span class="file"><a id="agregaUsuarioChat" style="cursor: pointer;">Agregar Usuarios Chat</a></span></li>
						<li id="idArchivo"><span class="file"><a id="agregaArchivosDisco" style="cursor: pointer;">Ubicar Archivos en Disco</a></span></li>
					</ul>		
				</div>
				<h3 ><a id="adminUsuarios">Administraci&oacute;n de Funcionarios</a></h3>
				<div>			
					<ul id="seccion2treeAdmin" class="filetree">
						<!--<li><span class="file"><a id="ingresaUsuario" style="cursor: pointer;">Ingresar Funcionario</a></span></li>	-->
						<li><span class="file"><a id="modificaFuncionario" style="cursor: pointer;">Modificar Funcionario</a></span></li>
					</ul>		
				</div>
				<h3 ><a id="adminModulosRol">Administraci&oacute;n de Roles</a></h3>
				<div id="adminModulosRolDiv">			
					<ul id="seccion4treeAdminModulosRol" class="filetree">
						<!--<li><span class="file"><a id="ingresaUsuario" style="cursor: pointer;">Ingresar Funcionario</a></span></li>	-->
						<!-- li><span class="file"><a id="administrarModulosPorRol" style="cursor: pointer;">M&oacute;dulos Asignados A Un Rol</a></span></li-->
						<li><span class="file"><a id="administrarSubRoles" style="cursor: pointer;">Configuraci&oacute;n de Subroles</a></span></li>
					</ul>
							
				</div>				
			<!--	comienza tab Administrar Plantillas -->
				<h3><a id="plantillas" href="#">Administraci&oacute;n</a></h3>
				<div>
					<ul id="seccion2tree" class="filetree">
						<li class="closed" ><span class="folder">Plantillas</span>
							<ul>
							   <li><span><img src="<%=request.getContextPath()%>/resources/images/icn_folderchek.png" width="20" height="16"  /><a id="admonPlantillaConsulta" onclick="abreModalPlantilla();">Consulta</a></span></li>
							   <li><span><img src="<%=request.getContextPath()%>/resources/images/icn_folderchek.png" width="20" height="16"  /><a id="admonPlantillaNueva" onclick="crearOEditarPlantilla();">Nueva</a></span></li>
							</ul>			
						</li>
					</ul>
				</div>							
			<!--	termina tab Administrar Plantillas -->
			
				<h3><a id="reportes" href="#">Indicadores</a></h3>
					<div>			
					</div>
				<h3><a id="graficas" href="#">Tablero de Control</a></h3>
				<!-- <div>			
					<ul id="seccion6treeAdmin" class="filetree">
						<li><span class="file"><a  style="cursor: pointer;">Tablero de Control</a></span></li>
					</ul>
				</div> -->
			
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
					<jsp:include page="/WEB-INF/paginas/datosPersonalesUsuario.jsp" flush="true"></jsp:include>
				</div>
				<!-- <h4><a href="#">Calendario</a></h4>
				<div>
					<center>
						<a href="#">
							<img src="<%=request.getContextPath()%>/resources/images/calendario.JPG" width="130" height="104">
						</a>
					</center>
				</div>-->
				<h6><a href="#">Agenda</a></h6>
				<div>
					<center>
						<jsp:include page="/WEB-INF/paginas/agendaUsuario.jsp" flush="true"></jsp:include>
					</center>
					<br />
				</div>
				<h6><a href="#" id="idLeyesCodigos" onclick="visorLeyesCodigos()">Consultar Leyes y C&oacute;digos</a></h6>
				<div id="idLeyesCodigosDiv">
					<!-- table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="0" bgcolor="#EEEEEE" bordercolorlight="#FFFFFF" style="cursor:pointer">
						<tr>
							<td width="100%" id="leyes"><img src="<%=request.getContextPath()%>/resources/css/check.png" width="16" height="16" />Leyes</td>
						</tr>
						<tr>
							<td id="codigos">&nbsp;<img src="<%=request.getContextPath()%>/resources/css/check.png" width="16" height="16" />Codigos</td>
						</tr>
						<tr>
							<td id="manuales">&nbsp;<img src="<%=request.getContextPath()%>/resources/css/check.png" width="16" height="16" />Manuales</td>
						</tr>
					</table-->
				</div>
				<h1 id="idChatMenu"><a href="#">Chat</a></h1>
			<div id="idChatMenuDiv">
				<center>
					<a onclick="ejecutaChat();" id="controlChat"><img src="<%= request.getContextPath()%>/resources/images/Chat.jpg" width="130" height="104"></a>
				</center>
			</div>
<!--				<h1><a href="#">Clima</a></h1>-->
<!--				<div></div>-->
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
					          <td width="363" align="right" valign="middle"><TABLE border=0 cellSpacing=0 cellPadding=0 width="300" height="100%">
					            <TBODY>
					              <TR>          
					              <TR vAlign=top>
					                <TD width=128 align=right valign="middle">&nbsp;</TD>
					                <TD width=150 align="right" valign="middle"><DIV id=liveclock></DIV></TD>
					                <TD width=10 align="right" valign="middle"><IMG alt="Icono reloj" src="<%=request.getContextPath()%>/resources/images/icn_reloj.png" width=26 height=25></TD>
					              </TR>
					            </TBODY>
					          </TABLE></td>
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
				<li id="registroDiasInhabiles" ><span></span>Registro de d&iacute;as inh&aacute;biles</li>
				<li id="administracionCatalogos" ><span></span>Administraci&oacute;n de cat&aacute;logos</li>
			</div>
			<div id="menu_config">
			<%--
				
			// Se borra hasta verificar la funcionalidad de las opciones para el administrador
			
				<li id="generarDocumento"><span></span>Generar Documento</li>
				<li><span></span>Adjuntar documento</li>
			--%>
			</div>
		</ul>
		<!--termina barra de herramientas-->
	</div>
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
					<!--Espacio para el grid con la consulta de plantillas-->
					<div id="divGridAdmonPlantillaConsulta">
						<table id="gridAdmonPlantillaConsulta"></table>
						<div id="pagerGridAdmonPlantillaConsulta"></div>
					</div>

					<div id="divTipoDocto" style="display: none">
						<table width="300" cellspacing="0" cellpadding="0">
							<tr>
								<td width="45">&nbsp;</td>
								<td width="308">&nbsp;</td>
								<td width="45">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="3" align="justify"><strong>Seleccione Tipo de Documento: </strong></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td align="center">
						            <select id="cbxTipoDocto" style="width:200px;">						
						                <option value="0">-Todos-</option>
						            </select>
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
						</table>
					</div>
					
				</div>	
			</div>
		</div>
	</div>
	<!--Termina main content-->

	<div id="divCausa" style="display: none">
		<table width="300" cellspacing="0" cellpadding="0">
			<tr>
				<td width="45">&nbsp;</td>
				<td width="308">&nbsp;</td>
				<td width="45">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3" align="justify"><strong>Ingresar N&uacute;mero de Expediente: </strong></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"><input type="text" class="" size="30" maxlength="30" id="datoCausa"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>

	<div id="busquedaFecha" style="display: none">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td width="153">&nbsp;</td>
				<td width="153">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><strong>Fecha Inicio:</strong> <input
					type="text" id="fechaInicio" size="20" /></td>
			</tr>
			<tr>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><strong>Fecha
						Fin:&nbsp;&nbsp;</strong>&nbsp; <input type="text" id="fechaFin" size="20" />
				</td>
			</tr>
			<tr>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
			</tr>
		</table>
	</div>
	
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
				La sesi&oacute;n se cerrara en <span id="dialog-countdown" style="font-weight:bold"></span> segundos.
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
<!-- var idValort;
		idValort=$('#cmbTipoTel&eacute;fono1 option:selected').val();
		if(idValort==67){
			varTipo="Casa";
			}
		//
		if(varTipo==68){
			varTipo="Celular";
			}
		if(varTipo==71){varTipo="Fax";}
		if(varTipo==69){varTipo="Oficina";}
		if(varTipo==70){varTipo="Recados";} -->
