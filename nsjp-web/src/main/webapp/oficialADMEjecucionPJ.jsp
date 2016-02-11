<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
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

<!--COMIENZA CSS DEL DOCUMENTO-->
<!--css para ventanas-->
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/layout_complex.css"
	media="screen" />

<!--css para el estilos de jquery-->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />

<!--css para estilo de los arboles-->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />

<!--estilo ultrasist-->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/estilos.css"
	media="screen" />

<!--estilo del grid-->
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
<link rel="stylesheet" type="text/css" 
	href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" />

<!--COMIENZAN SCRIPTS--> 
 
<!--jquery-->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimeout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimer.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>

<!--para controlar las ventanas-->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>

<!--para creacion de arboles-->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/reloj.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" 
	src="<%=request.getContextPath()%>/resources/js/jquery.zweatherfeed.js"></script>
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
	
	//por default es la primera vez que entra
	var primeraTranscripcion="true";
	
	var outerLayout, innerLayout;
	
	var reloadGridFecha = false;
	
	var validaFecha = false;

	var idAudiencia ="";
	var documentoID="";
	$(document).ready(function() {
		jQuery(document).ajaxStop(jQuery.unblockUI);
		outerLayout = $("body").layout( layoutSettings_Outer );

		$("#accordionmenuprincipal").accordion({  fillSpace: true });
		$("#accordionmenuderprincipal").accordion({ fillSpace: true});
		$("#accordionmenuderprincipal").accordion( "option", "icons", null );
		//crea el arbol de audiencias
		//$("#seccion1treePJENC").treeview();
		//crea el arbol de eventos
		//$("#seccion2treePJENC").treeview();
		//crea el arbol de casos
		//$("#seccion3treePJENC").treeview();
		//agrega el evento generar documento 
		//$("#generarDocumento").click(generarDocumentoView);
		//agreaga el evento para crear la agenda
		$("#controlAgenda").click(creaAgenda);
		//Carga el arbol de casos
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
					document.location.href="<%=request.getContextPath()%>/Logout.do";
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
				}
			}
		});	
		
		 /*
		*Funcion que carga el grid, por default con las nuevas notificaciones 
		*/

		//div contenedor divGridDetalleResolutivaAudiencia
		jQuery("#gridDetalleResolutivaAudiencia").jqGrid({ 
			url:'<%=request.getContextPath()%>/SolicitudesBeneficiosPreliberacion.do', 
			datatype: "xml", 
			colNames:['N&uacute;mero de Causa','Solicitante','Instituci&oacute;n Solicitante','Sentenciado' ], 
			colModel:[ 	{name:'NCausa',index:'nCausa', width:25},
			           	{name:'Solicitante',index:'solicitante', width:15},
			           	{name:'Institucion',index:'institucion', width:15},
			           	{name:'Sentenciado',index:'sentenciado', width:20},
			           	
					],
			pager: jQuery('#paginadorDetalleResolutivaAudiencia'),
			rowNum:10,
			rowList:[25,50,100],
			width:767,
			height:400,
			autoheight:true,
			sortname: 'Solicitante',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {

				documentoID=rowid;
				buscaCarpetaEjecucionEstatus();
				//asignaTranscripcion();
			}
			
		}).navGrid('#paginadorDetalleResolutivaAudiencia',{edit:false,add:false,del:false});
		 $("#gview_ gridDetalleResolutivaAudiencia .ui-jqgrid-bdiv").css('height', '450px');	

			

		
	
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
		//$('#test').weatherfeed(['MXDF0132']);
		createInnerLayout ();
					
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
		,	onresize_end:			function () { $("#gridDetalleResolutivaAudiencia").setGridWidth($("#mainContent").width() - 5, true); }
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
	    $("#" +"iframewindowagenda"+ " .window-maximizeButton").click();
		}


	/*
	*Funcion que llama a la funcionalidad para generar un documento 
	*/
	
	function buscaCarpetaEjecucion() {
		$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:255,posy:111,width:1140,height:400,title:"Generar Documento", type:"iframe"});
	    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%=request.getContextPath()%>/buscaCarpetaEjecucionJSP.do" width="1140" height="400" />');
	    		
	}	

	//Funcion 
	function mostrarVentanaTranscripcionAdudiencia(rowid){

		idWindowTranscripcionAdudiencia++;
		$.newWindow({id:"iframewindowTranscripcionAdudiencia" + idWindowTranscripcionAdudiencia, statusBar: true, posx:255,posy:111,width:1000,height:600,title:"Solicitud de Transcripci&oacute;n de Audiencia", type:"iframe"});
		$.updateWindowContent("iframewindowTranscripcionAdudiencia" + idWindowTranscripcionAdudiencia,'<iframe src="<%=request.getContextPath()%>/transcripcionAdudiencia.do?idSolicitudTranscripcion='+ rowid+'" width="1050" height="620" />');
	}


	function gridResolutivaAudiencia(){
		
	

	
		  }

	function buscaCarpetaEjecucionEstatus() {

		

		//abre la ventana de detalle de la persona
		$("#cambiaEstatusConfirm").dialog("open");
		$("#cambiaEstatusConfirm").dialog({
			autoOpen : true,
			modal : true,
			title : 'Cambiar Estatus',
			dialogClass : 'alert',
			position : [ 500, 150 ],
			width : 260,
			height : 160,
			maxWidth : 500,
			buttons : {
				"Aceptar" : function() {	
					mandaCambioStatus();
					 $("#gridDetalleResolutivaAudiencia").trigger("reloadGrid"); 
				$(this).dialog("close");
				
				},
				"Cancelar" : function() {
					$(this).dialog("close");
				}
			}
		});

	}

	function mandaCambioStatus(){
		
		
			var param ="";
			param += 'documentoID='+documentoID;
			
			//alert(param);
			   $.ajax({
			     type: 'POST',
			     url: '<%=request.getContextPath()%>/BuscaCarpetaEjecucion.do',
				 data: param,
				 dataType: 'xml',
				 success: function(xml){
					
					
				
				  }
				});
			 

		}
	function ejecutaChat() {
		$("#dialogoChat").dialog( "open" );
	}
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
				<h3><a id="preliberacionPJ" href="#"><img src="<%=request.getContextPath()%>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Solicitudes de Preliberaci&oacute;n</a></h3>
					<div><!--			
					<ul id="seccion1treePJENC" class="filetree">
						<li><span class="file"><a id="audienciaDelDia" style="cursor: pointer;" onclick="cargaGridDiaPJENC();">Audiencia de Ejecuci&oacute;n</a></span></li>
						<li><span class="file" id="porFecha" style="cursor: pointer;" onclick="modalFecha()">Carpeta de Ejecuci&oacute;n</span></li>
						<li><span class="file" id="porFecha" style="cursor: pointer;" onclick="modalFecha()">Hist&oacute;rico</span></li>
					</ul>		
				--></div>
				<!--<h3 ><a id="evento" href="#" >Audiencia de Ejecuci&oacute;n</a></h3>
				<div>			
					<ul id="seccion2treePJENC" class="filetree">
						<li><span class="file"><a id="recurso" style="cursor: pointer;" onclick="cargaGridRecursoPJENC();">Recurso</a></span></li>
						<li><span class="file"><a id="recurso" style="cursor: pointer;" onclick="cargaGridTranscripcionAudienciaPJENC();">Transcripci&oacute;n <br></br>de Audiencia</a></span></li>
					</ul>		
				</div>
				<h3 ><a id="eventos" href="#" >Carpeta de Ejecuci&oacute;n</a></h3>
				<div>			
					<ul id="seccion3treePJENC" class="filetree">
						<li class="closed" id="casosPJENC"><span class="folder">Casos</span>
							 Aqui se agregan los casos y expedientes dinamicamente 
						</li>
					</ul>		
				</div>
				
					<h3 ><a id="eventos" href="#" >Hist&oacute;rico</a></h3>
				<div>			
					<ul id="seccion3treePJENC" class="filetree">
						<li class="closed" id="casosPJENC"><span class="folder">Casos</span>
							 Aqui se agregan los casos y expedientes dinamicamente 
						</li>
					</ul>		
				</div>-->
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
<center><jsp:include
	page="/WEB-INF/paginas/datosPersonalesUsuario.jsp" flush="true"></jsp:include>
</center>
</div>
<!-- 
<h4><a href="#">Calendario</a></h4>
<div>
<center><a href="#"><img
	src="<%=request.getContextPath()%>/resources/images/img_calendario.png"
	width="201" height="318"></a></center>
</div>
 -->
<h6><a href="#">Agenda</a></h6>
<div>
<center><jsp:include page="/WEB-INF/paginas/agendaUsuario.jsp"
	flush="true"></jsp:include></center>
<br />
</div>
<h6><a href="#" id="">Consultar Leyes y C&oacute;digos</a></h6>
<div>
<table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0"
	cellpadding="0" bgcolor="#EEEEEE" bordercolorlight="#FFFFFF"
	style="cursor: pointer">
	<tr>
		<td width="100%" id="leyes"><img
			src="<%=request.getContextPath()%>/resources/css/check.png"
			width="16" height="16" />Leyes</td>
	</tr>
	<tr>
		<td id="codigos">&nbsp;<img
			src="<%=request.getContextPath()%>/resources/css/check.png"
			width="16" height="16" />Codigos</td>
	</tr>
	<tr>
		<td id="manuales">&nbsp;<img
			src="<%=request.getContextPath()%>/resources/css/check.png"
			width="16" height="16" />Manuales</td>
	</tr>
</table>
</div>
<!--<h4><a href="#">Clima</a></h4>-->
<!--<div align="left">-->
<!--<div align="left" id="test"></div>-->
				<h4><a href="#" onclick="consultarTiposRol();">Facultades</a></h4>
				<div>
					<table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="0"  style="cursor:pointer" id="tableRolMenu">
					</table>
					<form name="frmRol2" action="<%= request.getContextPath() %>/rolRedirec.do" method="post">
						<input type="hidden" name="rolname" />
					</form>
				</div>
</div>
<h4><a href="#">Chat</a></h4>
<div align="center">

<div id="dialogoChat" title="Chat" align="center"><iframe
	src="<%=((ConfiguracionDTO) session
					.getAttribute(LoginAction.KEY_SESSION_CONFIGURACION_GLOBAL))
					.getUrlServidorChat()%>"
	frameborder="0" width="380" height="280"></iframe></div>
<center><a onclick="ejecutaChat();" id="controlChat"><img
	src="<%=request.getContextPath()%>/resources/images/img_chat.png"
	width="130" height="104"></a></center>
<!--iframe src="http://gaby1:5280/web/jwchat/index.html" frameborder="0" width="200" height="200" scrolling="no"></iframe  -->
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
<!--comienza barra de herramientas-->
<ul class="toolbar">
	<div id="menu_head">
	<li id="tbarBtnHeaderZise" class="first"><span></span></li>
	</div>
	<div id="menu_config">
	<li id="generarDocumento"><span></span>Generar Documento&nbsp;<img src="<%=request.getContextPath()%>/resources/images/icn_dctowri.png" width="15" height="16"></li>
	<li><span></span>Adjuntar documento&nbsp;<img src="<%=request.getContextPath()%>/resources/images/icn_dctoadjun.png" width="10" height="16"></li>
	</div>
</ul>
<!--termina barra de herramientas--></div>
<!--Termina ui-layout-north-->

<!--Comienza ui-layout-south-->
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
<!--Termina ui-layout-south-->

<!--Comienza main content-->
<div id="mainContent">
<div class="ui-layout-center">
<div class="ui-layout-content">
<div class="ui-layout-north">

<div id="divGridDetalleResolutivaAudiencia">
<table id="gridDetalleResolutivaAudiencia"></table>
<div id="paginadorDetalleResolutivaAudiencia"></div>
</div>



</div>
</div>
</div>
</div>
<!--Termina main content-->

<div id="cambiaEstatusConfirm" style="display: none">

		<table cellspacing="0" cellpadding="0">
			<tr>
				<td >Estas Seguro de Actualizar el Estatus</td>
				
			</tr>
		</table>

	</div>
	
	<div id="dialog-logout" title="Cerrar Sesi&oacute;n">
		<p align="center">
			<span id="logout">&iquest;Desea cerrar su sesi&oacute;n?</span>
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
</body>
</html>

