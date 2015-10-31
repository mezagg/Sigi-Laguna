<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
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
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>  
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" />	

<STYLE type=text/css>
DD P {
	LINE-HEIGHT: 120%
}
#iRepLegalAccordionPane {
	BORDER-BOTTOM: #b5c9e8 0px solid; BORDER-LEFT: #b5c9e8 0px solid; PADDING-BOTTOM: 1px; PADDING-LEFT: 1px; WIDTH: 1000px; PADDING-RIGHT: 1px; BACKGROUND: #fff; HEIGHT: 355px; BORDER-TOP: #b5c9e8 0px solid; BORDER-RIGHT: #b5c9e8 0px solid; PADDING-TOP: 1px
}
#iRepLegalAccordionPane DL {
	WIDTH: 1000px; HEIGHT: 355px
}
#iRepLegalAccordionPane DT {
	TEXT-ALIGN: right;
	PADDING-BOTTOM: 0px;
	LINE-HEIGHT: 44px;
	TEXT-TRANSFORM: uppercase;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 15px;
	FONT-FAMILY: Arial, Helvetica, sans-serif;
	BACKGROUND: url(<%= request.getContextPath()%>/images/jquery/plugins/easyaccordion/slide-title-inactive-1.jpg) #fff no-repeat 0px 0px;
	LETTER-SPACING: 1px;
	HEIGHT: 46px;
	COLOR: #1c94c4;
	FONT-SIZE: 1.1em;
	FONT-WEIGHT: bold;
	PADDING-TOP: 0px
}
#iRepLegalAccordionPane DT.active {
	BACKGROUND: url(<%= request.getContextPath()%>/images/jquery/plugins/easyaccordion/slide-title-active-1.jpg) #fff no-repeat 0px 0px; COLOR: #e78f08; CURSOR: pointer
}
#iRepLegalAccordionPane DT.hover {
	COLOR: #e78f08
}
#iRepLegalAccordionPane DT.hover.active {
	COLOR: #1c94c4
}
#iRepLegalAccordionPane DD {
	BORDER-BOTTOM: #dbe9ea 1px solid; BORDER-LEFT: 0px; PADDING-BOTTOM: 1px; PADDING-LEFT: 1px; PADDING-RIGHT: 1px; BACKGROUND: url(<%= request.getContextPath()%>/images/jquery/plugins/easyaccordion/slide.jpg) repeat-x left bottom; BORDER-TOP: #dbe9ea 1px solid; MARGIN-RIGHT: 1px; BORDER-RIGHT: #dbe9ea 1px solid; PADDING-TOP: 1px
}
#iRepLegalAccordionPane .slide-number {
	COLOR: #68889b; FONT-WEIGHT: bold; LEFT: 10px
}
#iRepLegalAccordionPane .active .slide-number {
	COLOR: #fff
}
#iRepLegalAccordionPane A {
	COLOR: #68889b
}
#iRepLegalAccordionPane DD IMG {
	MARGIN: 0px; FLOAT: right
}
#iRepLegalAccordionPane H2 {
	MARGIN-TOP: 10px; FONT-SIZE: 2.5em
}
#iRepLegalAccordionPane .more {
	DISPLAY: block; PADDING-TOP: 10px
}
body {
	background-image: url(<%= request.getContextPath()%>/images/back_gral.jpg);
	background-repeat: repeat-x;
}
body,td,th {
	font-family: Arial, Helvetica, sans-serif;
}
</STYLE>
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

	<script type="text/javascript">

	var contextoPagina = "${pageContext.request.contextPath}";
	var sesionActiva = '<%= (request.getSession().getAttribute(LoginAction.KEY_SESSION_USUARIO_FIRMADO)!=null)%>';
	var tiempoActiva = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoBloqueoSesion()%>';
	estaSesionActiva();
	
	var outerLayout, innerLayout;

	//Variable que controla que se cargue por primera vez el grid de solicitudes de traslado de imputados no atendidas
	var firstGridSolicitudesDeTrasladoDeImputadosNoAtendidas = true;

	//Variable que controla que se cargue por primera vez el grid de solicitudes de traslado de imputados en proceso
	var firstGridSolicitudesDeTrasladoDeImputadosEnProceso = true;

	//Variable que controla que se cargue por primera vez el grid de solicitudes de traslado de imputados en proceso
	var firstGridSolicitudesDeTrasladoDeImputadosTerminadas = true;

	$(document).ready(function() {
		jQuery(document).ajaxStop(jQuery.unblockUI);						
		outerLayout = $("body").layout( layoutSettings_Outer );		
		$("#accordionmenuprincipal").accordion({  fillSpace: true });
		$("#accordionmenuderprincipal").accordion({ fillSpace: true});
		$("#accordionmenuderprincipal").accordion( "option", "icons", null );
		//Arboles del menu derecho
		$("#seccion1tree").treeview();
		//Muestra el reloj

		//obtenemos el tiempo de las alarmas y ponemos en marcha el timer		
		var tiempo='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoRevisionAlarmas()%>';
		setInterval(muestraAlerta, tiempo);

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
		
		muestraGadgets();
		//Boton para abrir la agenda
		$("#controlAgenda").click(creaAgenda);
		//Carga el grid correspondiente
		cargaGridSolicitudesDeTrasladoDeImputadosNoAtendidas()
		//Evento que envia las solicitudes de traslado
		$("#enviarSolicitud").click(obtenerSolicitudesDeTrasladoSeleccionadas);		



		outerLayout.addToggleBtn( "#tbarBtnHeaderZise", "north" );
		var westSelector = "body > .ui-layout-west"; // outer-west pane
		var eastSelector = "body > .ui-layout-east"; // outer-east pane
		$("<span></span>").addClass("pin-button").prependTo( westSelector );
		$("<span></span>").addClass("pin-button").prependTo( eastSelector );
		outerLayout.addPinBtn( westSelector +" .pin-button", "west");
		outerLayout.addPinBtn( eastSelector +" .pin-button", "east" );
		$("<span></span>").attr("id", "west-closer" ).prependTo( westSelector );
		$("<span></span>").attr("id", "east-closer").prependTo( eastSelector );
		$('#test').weatherfeed(['MXDF0132']);
		outerLayout.addCloseBtn("#west-closer", "west");
		outerLayout.addCloseBtn("#east-closer", "east");
		createInnerLayout () ;

		var ambiente='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAmbiente()%>';
		if(ambiente == undefined || ambiente == "undefined"){
			ambiente = "";
		}
		$("#ambienteLb").html('<strong><big>'+ambiente+'</big></strong>');
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
		,	onresize_end:			function () { $("#gridSolicitudesDeTrasladoDeImputadosNoAtendidas").setGridWidth($("#mainContent").width() - 5, true); 
												  $("#gridSolicitudesDeTrasladoDeImputadosEnProceso").setGridWidth($("#mainContent").width() - 5, true);
												  $("#gridSolicitudesDeTrasladoDeImputadosTerminadas").setGridWidth($("#mainContent").width() - 5, true);
												}
		}
	};
	
	//Funcion que crea la agenda
	function creaAgenda() {
		$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
	    $.updateWindowContent("iframewindowagenda",'<iframe src="<%=request.getContextPath()%>/InicioAgenda.do" width="1150" height="600" />');		
	    $("#" +"iframewindowagenda"+ " .window-maximizeButton").click();
		}

	//Funcion que ejecuta el chat
	function ejecutaChat() {
		$("#dialogoChat").dialog( "open" );
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

	////////////////////////////////////////////////////////////COMIENZA FUNCIONALIDAD DE LA BARRA DE HERRAMIENTAS////////////////////////////////////////////////
    /**Funcion que obtiene los id�s de los renglones seleccionados de grid que este visible en ese momento*/
    function obtenerSolicitudesDeTrasladoSeleccionadas(){

		var idsArray; 

    	if($("#divGridSolicitudesDeTrasladoDeImputadosNoAtendidas").is(":visible")){
        	
    		idsArray = jQuery("#gridSolicitudesDeTrasladoDeImputadosNoAtendidas").jqGrid('getGridParam','selarrrow');

    		
			if(idsArray.length ){
				//alert("Solicitud Enviada, no atendida");
	        	//LLamar al CU Enviar solicitud relacionada al traslado
			}
			else{
				//alert("Seleccione al menos una solicitud");
			}
		
        }
    	if($("#divGridSolicitudesDeTrasladoDeImputadosEnProceso").is(":visible")){

    		idsArray = jQuery("#gridSolicitudesDeTrasladoDeImputadosEnProceso").jqGrid('getGridParam','selarrrow');

       		if(idsArray.length){
    			//alert("Solicitud Enviada, en proceso");;
	        	//LLamar al CU Enviar solicitud relacionada al traslado
			}
			else{
				//alert("Seleccione al menos una solicitud");
			}
        	
        } 
    }	
	////////////////////////////////////////////////////////////COMIENZA FUNCIONALIDAD DE LA CEJA SOLICITUDES /////////////////////////////////////////////////////

	/*
	*Funcion que oculta o muestra los grids, recibe como parametro
	*el nombre del grid que va a mostrar, y todos los demas, se 
	*ocultaran
	*/
	function ocultaMuestraGrids(nombreGrid){

		if(nombreGrid == "gridSolicitudesDeTrasladoDeImputadosNoAtendidas"){
			  
			$("#divGridSolicitudesDeTrasladoDeImputadosEnProceso").hide();			
			$("#divGridSolicitudesDeTrasladoDeImputadosTerminadas").hide();
			$("#divGridSolicitudesDeTrasladoDeImputadosNoAtendidas").show();
						
		}

		if(nombreGrid == "gridSolicitudesDeTrasladoDeImputadosEnProceso"){
  			
			$("#divGridSolicitudesDeTrasladoDeImputadosNoAtendidas").hide();
			$("#divGridSolicitudesDeTrasladoDeImputadosTerminadas").hide();
			$("#divGridSolicitudesDeTrasladoDeImputadosEnProceso").show();
		}
		
		if(nombreGrid == "gridSolicitudesDeTrasladoDeImputadosTerminadas"){

			$("#divGridSolicitudesDeTrasladoDeImputadosNoAtendidas").hide();
			$("#divGridSolicitudesDeTrasladoDeImputadosEnProceso").hide();	
			$("#divGridSolicitudesDeTrasladoDeImputadosTerminadas").show();			
		}
	}
	
	/*
	*Funcion que carga el grid con las solicitudes de traslado de imputados no atendidas
	*/
	function cargaGridSolicitudesDeTrasladoDeImputadosNoAtendidas(){

		if(firstGridSolicitudesDeTrasladoDeImputadosNoAtendidas == true){
			
			jQuery("#gridSolicitudesDeTrasladoDeImputadosNoAtendidas").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarSolicitudesDeTrasladoDeImputadoNoAtendidas.do', 
				datatype: "xml", 
				colNames:['N&uacute;mero de IPH','Nombre del imputado','Tipo de Audiencia','Fecha de Audiencia','Hora de Audiencia','Domicilio Sala','Sala' ], 
				colModel:[ 	{name:'NumeroIPH',index:'numeroIPH', width:109},
				           	{name:'NombreImputado',index:'nombreImputado', width:109},
				           	{name:'TipoAudiencia',index:'tipoAudiencia', width:109},
				           	{name:'FechaAudiencia',index:'FechaAudiencia', width:109}, 
							{name:'HoraAudiencia',index:'horaAudiencia', width:109},
							{name:'DomicilioSala',index:'domicilioSala', width:109},
							{name:'SalaAudiencia',index:'salaAudiencia', width:109}
						],
				pager: jQuery('#pagerGridSolicitudesDeTrasladoDeImputadosNoAtendidas'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				//autoheight:true,
				width:767,
				height:450,
				sortname: 'numeroIPH',
				viewrecords: true,
				sortorder: "desc",
				multiselect: true, 
				caption:"Solicitudes de traslado de imputados no atendidas", 
				toolbar: [true,"top"] 
			}).navGrid('#pagerGridSolicitudesDeTrasladoDeImputadosNoAtendidas',{edit:false,add:false,del:false});

			/*Agrega el boton en la parte superior del grid*/ 
			//$("#t_gridSolicitudesDeTrasladoDeImputadosNoAtendidas").append("<input type='button' value='Enviar Solicitud' style='height:20px;font-size:-3;'/>"); 
			//$("input","#t_gridSolicitudesDeTrasladoDeImputadosNoAtendidas").click(function(){ 
				/*obtenemos los id�s seleccionados*/
			//	var s; s = jQuery("#gridSolicitudesDeTrasladoDeImputadosNoAtendidas").jqGrid('getGridParam','selarrrow'); alert(s); 
			//	alert("Enviar solicitud"); 
			//}); 
			
			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridSolicitudesDeTrasladoDeImputadosNoAtendidas=false;
		}
		else{
			jQuery("#gridSolicitudesDeTrasladoDeImputadosNoAtendidas").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarSolicitudesDeTrasladoDeImputadoNoAtendidas.do',datatype: "xml" });
			$("#gridSolicitudesDeTrasladoDeImputadosNoAtendidas").trigger("reloadGrid");			
		}
		
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridSolicitudesDeTrasladoDeImputadosNoAtendidas');
	}

	
	/*
	*Funcion que carga el grid de solicitudes de traslado de imputados no atendidas
	*/
	function cargaGridSolicitudesDeTrasladoDeImputadosProceso(){

		if(firstGridSolicitudesDeTrasladoDeImputadosEnProceso == true){
			
			jQuery("#gridSolicitudesDeTrasladoDeImputadosEnProceso").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarSolicitudesDeTrasladoDeImputadoEnProceso.do', 
				datatype: "xml", 
				colNames:['Solicitante','N&uacute;mero de IPH','Nombre del imputado','Tipo de Audiencia','Fecha de Audiencia','Hora de Audiencia','Domicilio Sala','Sala','Fecha Aut. de Desencarcelaci&oacute;n' ], 
				colModel:[ 	{name:'Solicitante',index:'solicitante', width:85},
							{name:'NumeroIPH',index:'numeroIPH', width:85},
				           	{name:'NombreImputado',index:'nombreImputado', width:85},
				           	{name:'TipoAudiencia',index:'tipoAudiencia', width:85},
				           	{name:'FechaAudiencia',index:'FechaAudiencia', width:85}, 
							{name:'HoraAudiencia',index:'horaAudiencia', width:85},
							{name:'DomicilioSala',index:'domicilioSala', width:85},
							{name:'SalaAudiencia',index:'salaAudiencia', width:85},
							{name:'FechaAutorizacionDesencarcelacion',index:'FechaAutorizacionDesencarcelacion', width:85}
						],
				pager: jQuery('#pagerGridSolicitudesDeTrasladoDeImputadosEnProceso'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				//autoheight:true,
				width:767,
				height:450,
				sortname: 'numeroExpediente',
				viewrecords: true,
				sortorder: "desc",
				multiselect: true
			}).navGrid('#pagerGridSolicitudesDeTrasladoDeImputadosEnProceso',{edit:false,add:false,del:false});

			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridSolicitudesDeTrasladoDeImputadosEnProceso=false;
		}
		else{
			jQuery("#gridSolicitudesDeTrasladoDeImputadosEnProceso").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarSolicitudesDeTrasladoDeImputadoEnProceso.do',datatype: "xml" });
			$("#gridSolicitudesDeTrasladoDeImputadosEnProceso").trigger("reloadGrid");			
		}
		
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridSolicitudesDeTrasladoDeImputadosEnProceso');
	}

	
	/*
	*Funcion que carga el grid de de traslado de imputados periciales terminadas
	*/
	function cargaGridSolicitudesDeTrasladoDeImputadosTerminadas(){

		if(firstGridSolicitudesDeTrasladoDeImputadosTerminadas == true){
			
			jQuery("#gridSolicitudesDeTrasladoDeImputadosTerminadas").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarSolicitudesDeTrasladoDeImputadoTerminadas.do', 
				datatype: "xml", 
				colNames:['Solicitante','N&uacute;mero de IPH','Nombre del imputado','Tipo de Audiencia','Fecha de Audiencia','Hora de Audiencia','Domicilio Sala','Sala','Fecha Aut. de Desencarcelaci&oacute;n','Fecha Ex&aacute;men M&eacute;dico' ], 
				colModel:[ 	{name:'Solicitante',index:'solicitante', width:76},
							{name:'NumeroIPH',index:'numeroIPH', width:76},
				           	{name:'NombreImputado',index:'nombreImputado', width:76},
				           	{name:'TipoAudiencia',index:'tipoAudiencia', width:76},
				           	{name:'FechaAudiencia',index:'FechaAudiencia', width:76}, 
							{name:'HoraAudiencia',index:'horaAudiencia', width:76},
							{name:'DomicilioSala',index:'domicilioSala', width:76},
							{name:'SalaAudiencia',index:'salaAudiencia', width:76},
							{name:'FechaAutorizacionDesencarcelacion',index:'FechaAutorizacionDesencarcelacion', width:76},
							{name:'FechaExamenMedico',index:'fechaExamenMedico', width:76}
						],
				pager: jQuery('#pagerGridSolicitudesDeTrasladoDeImputadosTerminadas'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				//autoheight:true,
				width:767,
				height:450,
				sortname: 'numeroExpediente',
				viewrecords: true,
				sortorder: "desc"
			}).navGrid('#pagerGridSolicitudesDeTrasladoDeImputadosTerminadas',{edit:false,add:false,del:false});

			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridSolicitudesDeTrasladoDeImputadosTerminadas=false;
		}
		else{
			jQuery("#gridSolicitudesDeTrasladoDeImputadosTerminadas").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarSolicitudesDeTrasladoDeImputadoTerminadas.do',datatype: "xml" });
			$("#gridSolicitudesDeTrasladoDeImputadosTerminadas").trigger("reloadGrid");			
		}
		
		//muestra este grid y oculta los demas
		ocultaMuestraGrids('gridSolicitudesDeTrasladoDeImputadosTerminadas');
	}


function visorLeyesCodigos() {
		
		$.newWindow({id:"iframewindowRestaurativa", statusBar: true, posx:255,posy:111,width:809,height:468,title:"Leyes y C&oacute;digos", type:"iframe"});
	    $.updateWindowContent("iframewindowRestaurativa",'<iframe src="<%= request.getContextPath() %>/detalleLeyesyCodigos.do" width="800" height="500" />');
	    		
	}
/*
 *Funcion para consultar los roles extras de cada usuario y
 * construlle el arbol dinamico de los tipos de rol en el menu derecho
 */

	</script>
</head>

<body>

<!--	comienza ui-layout-west -->
<div class="ui-layout-west">	
	<div class="header">&nbsp;</div>
	<div class="content">
	
		<!--	comienza acordeon del menu derecho-->
		<div id="accordionmenuprincipal">
		
			<!--	comienza tab solicitudes -->
			<h3><a id="solicitudes" href="#"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Polic&iacute;a Procesal</a></h3>
				<div>
					<ul id="seccion1tree" class="filetree">
						<li class="closed" ><span class="folder">Solicitudes de<br/>Traslado</span>
							<ul>
							   <li><span><img src="<%=request.getContextPath()%>/resources/images/icn_folderchek.png" width="20" height="16"  /><a id="pericialesNoAtendida" onclick="cargaGridSolicitudesDeTrasladoDeImputadosNoAtendidas();">No Atendidas</a></span></li>
							   <li><span><img src="<%=request.getContextPath()%>/resources/images/icn_folderchek.png" width="20" height="16"  /><a id="pericialesEnProceso" onclick="cargaGridSolicitudesDeTrasladoDeImputadosProceso();">En Proceso</a></span></li>
							   <li><span><img src="<%=request.getContextPath()%>/resources/images/icn_folderchek.png" width="20" height="16"  /><a id="pericialesTerminada" onclick="cargaGridSolicitudesDeTrasladoDeImputadosTerminadas();">Terminadas</a></span></li>
							</ul>			
						</li>
					</ul>
				</div>
			<!--	termina tab solicitudes -->
		</div>
		<!--	termina acordeon del menu derecho-->
		
	</div>
</div>
<!--	Termina ui-layout-west -->

<!--	Comienza ui-layout-east -->
<div class="ui-layout-east">
		<div class="header">Bienvenido</div>
		<div class="content">
			<div id="accordionmenuderprincipal">
				<h4>
					<a href="#" >Datos Personales</a>
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
				<h4>
					<a href="#">Agenda</a>
				</h4>
				<div>
					<center>
						<jsp:include page="/WEB-INF/paginas/agendaUsuario.jsp" flush="true"></jsp:include>
					</center>
					<br />
					
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
<!--				<h4>-->
<!--					<a href="#">Clima</a>-->
<!--				</h4>-->
<!--				<div align="left">-->
<!--					<div align="left" id="test"></div>-->
<!--				</div>-->
				<h4>
					<a href="#">Chat</a>
				</h4>
				<div align="center">
				
					<div id="dialogoChat" title="Chat" align="center">
						<iframe src="<%=((ConfiguracionDTO)session.getAttribute(LoginAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getUrlServidorChat()%>" frameborder="0" width="380" height="280"></iframe>
					</div>
					<center>
						<a onclick="ejecutaChat();" id="controlChat"><img src="<%= request.getContextPath()%>/resources/images/img_chat.png" width="130" height="104"></a>
					</center>
						<!--iframe src="http://gaby1:5280/web/jwchat/index.html" frameborder="0" width="200" height="200" scrolling="no"></iframe  -->
				</div>
				<h4><a href="#" onclick="consultarTiposRol();">Facultades</a></h4>
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
<!--	Comienza ui-layout-east -->


<!--	Comienza layout-north -->
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
					          <td width="363" align="right" valign="middle"><TABLE border=0 cellSpacing=0 cellPadding=0 width="300" height="100%">
					            <TBODY>
					              <TR>          
					              <TR vAlign=top>
					                <TD width=128 align=right valign="middle"><table width="141" border="0" cellspacing="0" cellpadding="0">
					                  <tr>
					                    <td width="107" align="right" valign="middle" class="txt_menu_top">&nbsp;</td>
					                  </tr>
					                </table></TD>
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
	<ul class="toolbar">
		<div id="menu_head">
		<li id="tbarBtnHeaderZise" class="first"><span></span></li>
		<li id="enviarSolicitud">Enviar Solicitud&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_mailinfo.png" width="15" height="16"></li>
		</div>
	</ul>
	<!--	Termina barra de herramientas-->

</div>
<!--	Termina layout-north -->

<!--	Comienza layout-south-->
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
<!--	Termina layout-south-->

<!--	Comienza mainContent-->
<div id="mainContent">
	<div class="ui-layout-center">
		<div class="ui-layout-content">
			<div class="ui-layout-north">
			
				<!-- Espacio para el grid con las solicitudes de dictamen no atendidas-->
				<div id="divGridSolicitudesDeTrasladoDeImputadosNoAtendidas">
					<table id="gridSolicitudesDeTrasladoDeImputadosNoAtendidas"></table>
					<div id="pagerGridSolicitudesDeTrasladoDeImputadosNoAtendidas"></div>
				</div>
				
				<!--Espacio para el grid con las solicitudes periciales en proceso-->
				<div id="divGridSolicitudesDeTrasladoDeImputadosEnProceso">
					<table id="gridSolicitudesDeTrasladoDeImputadosEnProceso"></table>
					<div id="pagerGridSolicitudesDeTrasladoDeImputadosEnProceso"></div>
				</div>
				
				<!--Espacio para el grid con las solicitudes periciales terminadas o cerradas-->
					<div id="divGridSolicitudesDeTrasladoDeImputadosTerminadas">
						<table id="gridSolicitudesDeTrasladoDeImputadosTerminadas"></table>
						<div id="pagerGridSolicitudesDeTrasladoDeImputadosTerminadas"></div>
					</div>
			</div>
		</div>
	</div>
</div>
<!--	Termina mainContent-->
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