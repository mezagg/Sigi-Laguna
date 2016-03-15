<%@page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros"%>
<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>

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
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>  
	<!--link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" /-->	 	

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
	BORDER-BOTTOM: #ffffff 1px solid; BORDER-LEFT: 0px; PADDING-BOTTOM: 1px; PADDING-LEFT: 1px; PADDING-RIGHT: 1px; BACKGROUND: url(<%= request.getContextPath()%>/images/jquery/plugins/easyaccordion/slide.jpg) repeat-x left bottom; BORDER-TOP: #ffffff 1px solid; MARGIN-RIGHT: 1px; BORDER-RIGHT: #ffffff 1px solid; PADDING-TOP: 1px
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
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/timer/jquery.idletimeout.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/timer/jquery.idletimer.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/layout_complex.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/reloj.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.zweatherfeed.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/sesion.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>			
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/comun.js?n=1"></script>

	<jsp:include page="/WEB-INF/paginas/defensoria/moduloAsesoriaLegal.jsp"/>

	<script type="text/javascript">
	
	var idWindowVisorAudiencia=0;
	
	var contextoPagina = "${pageContext.request.contextPath}";
	var sesionActiva = '<%= (request.getSession().getAttribute(LoginAction.KEY_SESSION_USUARIO_FIRMADO)!=null)%>';
	var tiempoActiva = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoBloqueoSesion()%>';
	estaSesionActiva();
	
	/*variables globales*/
	//Compartidas con el moduloAsesoriaLegal.jsp
	var folioSolicitud 		= "";
	var numExpediente 		= "";
	var idExpediente 		= "";
	var numeroExpedienteId	= "";
	var documentoId			= "";
	var turnoEnCurso;
	
	//Valor utilizado para los delitos
	var idExpedienteop		= "";
	
	var gridAud=0;
	var outerLayout, innerLayout;
	var numEtapa="";
	var valorEtapa="";
	var numeroCaso="";
	//var involucradoID="";
	var numeroExpediente;	
	var solicitudDefensorId;
	
	$(document).ready(function() {
		jQuery(document).ajaxStop(jQuery.unblockUI);

		habilitarModuloAsesoriaLegalDefensor();
		
		//obtenemos el tiempo de las alarmas y ponemos en marcha el timer		
		var tiempo='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoRevisionAlarmas()%>';
		setInterval(muestraAlerta, tiempo);

		$("#funcionarios").click(agregarEtapa);
						
		outerLayout = $("body").layout( layoutSettings_Outer );
		
		//genera menu izquierdo
		$("#accordionmenuprincipal").accordion({  fillSpace: true });
		$("#accordionmenuderprincipal").accordion({ fillSpace: true});
		$("#accordionmenuderprincipal").accordion( "option", "icons", null );
		//menu de arbol de expediente	
		$("#seccion1tree").treeview();
		$("#seccion2tree").treeview();
		$("#seccion6tree").treeview();
		//menu del arbol de actividades
		$("#seccion5tree").treeview();
		
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
		
		//llama funcion para mostrar gadgets
		muestraGadgets();
		//llama funcion para habilitar elementos del menu izquierdo
		//menuDependienteEtapa();
		//asocia funcion al boton de acceso al expediente
		$("#accesoExpediente").click(asigarPermisos);
		//crea agenda
		$("#controlAgenda").click(creaAgenda);
		//crea buscar expediente
		$("#buscarExpediente").click(buscarExpediente);
		//crea generar documento
		$("#generarDocumento").click(generarDocumentoViwe);	
		
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
		createInnerLayout () ;

		$('#avisosAsignacion').click(ocultaMuestraGridsAlertas('8'));
		$('#avisosAsignacion').click();
		//$('#test').weatherfeed(['MXDF0132']);
		
		//gridPrincipal();		
				
		$("#seccion3treePJEA").treeview();
		
		var ambiente='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAmbiente()%>';
		if(ambiente == undefined || ambiente == "undefined"){
			ambiente = "";
		}
		$("#ambienteLb").html('<strong><big>'+ambiente+'</big></strong>');
		
		restablecerPantallas();
		consultarEtapasExpedienteDinamico();
	});//Fin onready
	
	function consultarEtapasExpedienteDinamico(){
		//limpiamos el menu de las etapas del expediente
		$("#seccion1tree").empty();
		
		//Consulta de Etapas de Expediente
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarMenuEtapasExpediente.do',
			//data: 'idArea='+idArea,
			dataType: 'xml',
			async: false,
			success: function(xml){
				//lleno el arbol del menu izquierdo
				$(xml).find('catEtapa').each(function(){
					if($(this).find('esEtapaPadre').text() == 'true'){
						var catEtapaValorId = $(this).find('catEtapaValor').first().find('idCampo').text();
						var catEtapaValor = $(this).find('catEtapaValor').first().find('valor').text();
						$("#seccion1tree").append("<li class='closed' id='li_dptos_"+catEtapaValorId+"'><span class='folder'>"+catEtapaValor+"</span>"+"<ul id='li_dptosTree_"+catEtapaValorId+"' class='filetree'></ul></li>");

						//Hijas del padre
						$(this).find('etapasHijas').find('catEtapa').each(function(){
							var catEtapaHijaValorId = $(this).find('catEtapaValor').find('idCampo').text();
							var catEtapaHijaValor = $(this).find('catEtapaValor').find('valor').text();
							$("#li_dptosTree_"+catEtapaValorId).append("<li><span class='file'><a onclick='consultarExpedientePorEtapa("+catEtapaValorId+","+catEtapaHijaValorId+")'>"+catEtapaHijaValor+"</a></span></li>");
						});
					}
				});
			}
		});
		
	}		
	
 	//Bandera para recargar el grid de defendidos
 	var recargaGridEtapaExpediente = true;
	
	function consultarExpedientePorEtapa(pCatEtapaPadreValorId, pCatEtapaHijaValorId){
		if(recargaGridEtapaExpediente ){
			jQuery("#gridEtapaExpediente").jqGrid({
				url : '<%= request.getContextPath()%>/consultarExpedientesPorEtapaDefensoria.do?etapaValorId='+pCatEtapaHijaValorId+'', 
				datatype: "xml", 
				colNames:['Caso','Expediente','Imputados', 'Etapa', 'Estatus', 'ExpedienteId'], 
				colModel:[ 	{name:'caso',			index:'2002',	width:25},
							{name:'expediente',		index:'2003',	width:25},
							{name:'imputados',		index:'2009',	width:30}, 								
							{name:'etapa',			index:'2040',	width:15},
							{name:'estatus',		index:'2011',	width:25},
							{name:'expedienteId',					width:25,	hidden: true}
				],
				pager: jQuery('#pagerDefensaRestaurativa'), 
				rowNum:25, 
				rowList:[10,20,30],
				autowidth: true,
				autoheight: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc",
				scrollOffset:0,
				ondblClickRow: function(rowid) {
					numExpediente=rowid;
					idExpediente=rowid;
					
					//TODO GBP Ver impacto 
					numEtapa=<%=EtapasExpediente.CONCILIACION_Y_MEDIACION.getValorId()%>;
					valorEtapa="Conciliaci&oacute;n y Mediaci&oacute;n";
					var ret = jQuery("#gridEtapaExpediente").jqGrid('getRowData',rowid);
					numeroExpediente=ret.expediente;
					numeroCaso=ret.caso;
					//Diferencia del visor de Solciitudes
					solicitudDefensorId = null;
					
					idExpedienteop = ret.expedienteId;
					defensaIntegracion();
				}
			}).navGrid('#pagerEtapaExpediente',{edit:false,add:false,del:false});
			$("#gridEtapaExpediente").trigger("reloadGrid");
			$("#gview_gridEtapaExpediente .ui-jqgrid-bdiv").css('height', '450px');	
			recargaGridEtapaExpediente = false;
		}
		else{
			jQuery("#gridEtapaExpediente").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarExpedientesPorEtapaDefensoria.do?etapaValorId='+pCatEtapaHijaValorId+'',datatype: "xml" });
			$("#gridEtapaExpediente").trigger("reloadGrid");
		}
		ocultaMuestraGridsAlertas('15');
	}
	
	
	
	
		//Funcion que cierra la etapa
		function cerrarEtapa(){
			$.closeWindow('iframewindowVisorDefensor');
		}

		/*
		*Funcion para recargar el gird de acuerdo a la etapa actual del expediente,
		*usada al cambiar de una etapa a otra
		*/
		function recargarGridPorEtapa(etapaId){

			switch(parseInt(etapaId)){
				case <%= EtapasExpediente.INTEGRACION.getValorId() %>:{
						$("#gridDetalleFrmPrincipalTres").trigger("reloadGrid");
						break;
					}
				case <%= EtapasExpediente.TECNICA.getValorId() %>:{
						$("#gridDefensaTecnica").trigger("reloadGrid");
						break;
					}
				case <%= EtapasExpediente.CONCILIACION_Y_MEDIACION.getValorId() %>:{
						$("#gridDefensaRestaurativa").trigger("reloadGrid");
						break;
					}
				case <%= EtapasExpediente.EJECUCION.getValorId() %>:{
						$("#gridEjecucion").trigger("reloadGrid");
						break;
					}
				case <%= EtapasExpediente.ASESORIA.getValorId() %>:{
						$("#gridAsesorias").trigger("reloadGrid");
						break;
					}
			}
		}
		
		
	
		//cierra ventana visor pericial
		function cerrarVentana(){
			$.closeWindow('iframewindowVisorSolicitarServicioPericial');
		}
		
		//crea div servicio pericial
		function solServicioPericial(idExpediente){
			$.newWindow({id:"iframewindowVisorSolicitarServicioPericial", statusBar: true, posx:50,posy:111,width:850,height:380,title:"Solicitar Servicio Pericial", type:"iframe"});
		    $.updateWindowContent("iframewindowVisorSolicitarServicioPericial",'<iframe src="<%=request.getContextPath()%>/solicitarServicioPericial.do?idExpediente='+idExpediente+'" width="850" height="380" />');
		}

		//ejecuta la pantalla de chat
		function ejecutaChat() {
			$("#dialogoChat").dialog( "open" );
		}

		//busca expediente
		function buscarExpediente() {
			$.newWindow({id:"iframewindowBuscarExpediente", statusBar: true, posx:50,posy:110,width:653,height:400,title:"Buscar Expediente", type:"iframe"});
	    	$.updateWindowContent("iframewindowBuscarExpediente",'<iframe src="<%= request.getContextPath() %>/buscarExpediente.do" width="653" height="400" />');		
		}

		//busca caso
		function buscarCaso() {
			$.newWindow({id:"iframewindowBuscarCaso", statusBar: true, posx:50,posy:111,width:653,height:400,title:"Buscar Caso", type:"iframe"});
	    	$.updateWindowContent("iframewindowBuscarCaso",'<iframe src="<%= request.getContextPath() %>/buscarCaso.do" width="653" height="400" />');		
		}

		//crea agenda
		function creaAgenda() {
			$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
		    $.updateWindowContent("iframewindowagenda",'<iframe src="<%= request.getContextPath() %>/InicioAgenda.do" width="1150" height="600" />');		
		    $("#" +"iframewindowagenda"+ " .window-maximizeButton").click();	
			}

		//genera documento
		function generarDocumentoViwe() {
			$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:50,posy:50,width:1140,height:400,title:"Generar Documento", type:"iframe"});
		    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumento.do" width="1140" height="400" />');
		    $("#" +"iframewindowGenerarDocumento"+ " .window-maximizeButton").click();	
		}
		
		//MOD Defensor
		function defensaIntegracion() {
			
			var titulo="";
			if(numeroCaso != null && numeroCaso != ""){
				titulo += "Caso: "+numeroCaso+" ";
			}			
			if(valorEtapa=="Asesor&iacute;a"){
				titulo += "Asesor&iacute;a: ";
			}else{
				titulo += "Expediente: ";
			}
			$.newWindow({id:"iframewindowVisorDefensor", statusBar: true, posx:50,posy:50,width:1140,height:400,title:titulo + numeroExpediente, type:"iframe"});
		    $.updateWindowContent("iframewindowVisorDefensor",'<iframe src="<%= request.getContextPath() %>/visorExpedienteDefensoria.do?idNumeroExpediente='+numExpediente+'&numeroCaso='+numeroCaso+'&solicitudDefensorId='+solicitudDefensorId+'&idExpedienteop='+idExpedienteop+'" width="1140" height="400" />');
		    $("#" +"iframewindowVisorDefensor"+ " .window-maximizeButton").click();	
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
		,	onresize_end:			function () { $("#gridAvisosAsignacion").setGridWidth($("#mainContent").width() - 5, true);
												$("#gridDefensaRestaurativa").setGridWidth($("#mainContent").width() - 5, true);
												$("#gridEtapaExpediente").setGridWidth($("#mainContent").width() - 5, true);
												$("#gridDetalleFrmPrincipalTres").setGridWidth($("#mainContent").width() - 5, true);
												$("#gridDetalleAsesoriasPericiales").setGridWidth($("#mainContent").width() - 5, true);
												$("#gridDetalleDictamenPericial").setGridWidth($("#mainContent").width() - 5, true);
												$("#gridDefensaTecnica").setGridWidth($("#mainContent").width() - 5, true);
												$("#gridCoordinarCarpeta").setGridWidth($("#mainContent").width() - 5, true);
												$("#gridEjecucion").setGridWidth($("#mainContent").width() - 5, true);
												$("#gridSubordinados").setGridWidth($("#mainContent").width() - 5, true);
												$("#gridAsesorias").setGridWidth($("#mainContent").width() - 5, true);
									}
	}
	};

	/**
	* Funcion que es invocada en el moduloAsesoriaLegal.jsp
	* para recargar el grid correspondiente
	*/
	//MOD defensor
	function ocultaMuestraGrids(idGrid){
		//Solo se redirecciona con el parametro definido en este jsp
		ocultaMuestraGridsAlertas ("13");
	}
	
		function ocultaMuestraGridsAlertas(idGrid) {
			$("#divGridDefensor").css("display", "none");
			$("#divGridEjecucion").css("display", "none");
			$("#divGridSolicitudes").css("display", "none");
			$("#divGridDefensaTecnica").css("display", "none");
			$("#divGridAsesoriasPericiales").css("display", "none");
			$("#divGridDictamenPericial").css("display", "none");
			$("#divGridAvisosAsignacion").css("display", "none");
			$("#divGridCoordinarCarpeta").css("display", "none");
			$("#divGridDefensaRestaurativa").css("display", "none");
			$("#divGridEtapaExpediente").css("display", "none");
			$("#divGridCarpetaInvestigacion").css("display", "none");
			$("#divGridAudioVideo").css("display", "none");
			$("#divGridTranscripcion").css("display", "none");
			$("#divGridAudiencias").css("display", "none");	
			$("#divGridAudienciasGeneradas").css("display", "none");	
			$("#divGridAsesorias").css("display", "none");	

			switch(parseInt(idGrid)){
				case 1:
					$("#divGridSolicitudes").css("display", "block");
					break;
				case 2:
					$("#divGridDefensaRestaurativa").css("display", "block");
					gridDefensaRestaurativa();
					break;
				case 3:
					$("#divGridDefensor").css("display", "block");
					gridDefensaIntegracionDefensoria();
					break;
				case 4:
				case 41:
					$("#divGridAsesoriasPericiales").css("display", "block");
					gridAsesoriasPericiales();
					break;
				case 42:
					$("#divGridDictamenPericial").css("display", "block");
					gridDictamenPericial();
					break;
				case 5:
					$("#divGridDefensaTecnica").css("display", "block");
					gridDefensaTecnicaDefensoria();
					break;
				case 6:
					$("#divGridCoordinarCarpeta").css("display", "block");
					gridCoordinarCarpetaDefensoria();
					break;
				case 7:
					$("#divGridEjecucion").css("display", "block");
					gridEjecucion();
					break;
				case 8:
					$("#divGridAvisosAsignacion").css("display", "block");				
					gridAvisosAsignacion();				
					break;	
				case 9:
					$("#divGridCarpetaInvestigacion").css("display", "block");				
					gridCarpetaInvestigacion();	
					ajustarGridAlCentro($("#divGridCarpetaInvestigacion"));
					break;
				case 10:
					$("#divGridAudioVideo").css("display", "block");
					gridAudioVideo();				
					break;
				case 11:
					$("#divGridTranscripcion").css("display", "block");				
					gridTranscripcion();				
					break;	
				case 12:
					$("#divGridAudiencias").css("display", "block");				
					//gridAudiencias();				
					break;	
				case 13:
					$("#divGridAsesorias").css("display", "block");				
					//gridAsesoria();				
					break;	
				case 14:
					$("#divGridAudienciasGeneradas").css("display", "block");				
					gridAudienciasGeneradas();				
					break;
				case 15:
					$("#divGridEtapaExpediente").css("display", "block");
					break;
			}
			
			restablecerPantallas();
		}
		
		/*
		* Funcion que se encarga de hacer el ajuste de los grid al centro
		* Definida en el comun.js
		*/
		function restablecerPantallas(){
			ajustarGridAlCentro($("#gridDefensaRestaurativa"));
			ajustarGridAlCentro($("#gridEtapaExpediente"));
			ajustarGridAlCentro($("#gridDetalleFrmPrincipal"));
			ajustarGridAlCentro($("#gridDetalleFrmPrincipalTres"));
			ajustarGridAlCentro($("#gridDetalleAsesoriasPericiales"));
			ajustarGridAlCentro($("#gridDetalleDictamenPericial"));
			ajustarGridAlCentro($("#gridDefensaTecnica"));
			ajustarGridAlCentro($("#gridCoordinarCarpeta"));
			ajustarGridAlCentro($("#gridEjecucion"));
			ajustarGridAlCentro($("#gridAvisosAsignacion"));
			ajustarGridAlCentro($("#gridCarpetaInvestigacion"));
			ajustarGridAlCentro($("#gridAudioVideo"));
			ajustarGridAlCentro($("#gridTranscripcion"));
			ajustarGridAlCentro($("#gridAudiencias"));
			ajustarGridAlCentro($("#gridAudienciasGeneradas"));
			ajustarGridAlCentro($("#gridAsesorias"));
		}

		//Variable para recargar el grid de solicitudes sin atender (designaciones)
		var recargaGridAvisoDesignacion = true;
		
		/**
		* Funcion que se encarga de crear el grid de las 
		* Solicitudes de Defensor Asignadas 
		* Es decir, de los Avisos de Designaci&oacute;n asociados
		* al funcionario defensor logeado, mediante 
		* EstatusNotificacion.NO_ATENDIDA
		*/
		//MOD Defensor
		function gridAvisosAsignacion() {
			var tipoSolicitudId = '<%=TiposSolicitudes.DEFENSOR.getValorId()%>';
			if(recargaGridAvisoDesignacion == true){
				jQuery("#gridAvisosAsignacion").jqGrid({
					url : '<%= request.getContextPath()%>/consultarSolicitudesDefensorAsignadas.do?tipoSolicitudId='+tipoSolicitudId+'&esAsignada=true', 
					datatype: "xml", 
					colNames:['Instituci&oacute;n','Folio','Caso','Expediente','Imputado','Etapa','Fecha Asignaci&oacute;n','Defensor','numeroExpedienteId','solicitudDefensorId','ExpedienteId'],  
					colModel:[ 	{name:'origen',		index:'2001',	align:"center",	width:90},
					           	{name:'folio',		index:'2041',	align:'center',	width:85},
								{name:'caso',		index:'2002',	align:'center',	width:180},
					           	{name:'expediente', index:'2003',	align:'center',	width:145},
					           	{name:'imputado',	index:'2009',	align:'center',	width:150}, 
								{name:'etapa',  	index:'2028',	align:'center',	width:100}, 								 
								{name:'fecha',		index:'2007',	align:'center',	width:100},
								{name:'defensor',	index:'2006',	align:"center",	width:190},
								{name:'numeroExpedienteId',	index:'numeroExpedienteId',	align:'center',	width:40,	hidden: true},
								{name:'solicitudDefensorId',	index:'solicitudDefensorId',	align:'center',	width:40,	hidden: true},
								{name:'expedienteId',					width:25,	hidden: true}
							],
					pager: jQuery('#pagerAvisosAsignacion'),
					rowList:[10,20,30,40,50,60],
					sortname: 'folio',
					viewrecords: true,
					onresize_end: function(){ 
						$("#pagerAvisosAsignacion").setGridWidth($("#mainContent").width() - 5, true); 
					},
					sortorder: "desc",
					ondblClickRow: function(rowid) {		
						activaConfirm("avisosAsignacion", rowid);
					}
				});//.navGrid('#pagerAvisosAsignacion',{edit:false,add:false,del:false});

				recargaGridAvisoDesignacion = false;
			}
			else{
				$("#gridAvisosAsignacion").trigger("reloadGrid");
			}
			$("#gview_gridAvisosAsignacion .ui-jqgrid-bdiv").css('height', '525px');	
		}
		 
		//MOD Defensor
		function activaConfirm(funcionalidad, rowid) {
			
			if (funcionalidad == "avisosAsignacion"){ 
			 	customConfirm("&iquest;Dar seguimiento a la solicitud?", "Seguimiento de Solicitud", function(){ 
					var fila = jQuery("#gridAvisosAsignacion").jqGrid('getRowData',rowid); 
					numExpediente = fila.numeroExpedienteId;
					numeroExpediente = fila.expediente;
					numEtapa=fila.etapa;
					idExpediente=fila.numeroExpedienteId;
					solicitudDefensorId = fila.solicitudDefensorId;
					idExpedienteop = fila.expedienteId;
					
					actualizaEstatusAvisoDesignacion(rowid);
					$("#gridAvisosAsignacion").trigger("reloadGrid");
					defensaIntegracion();
			 	});
			}
		}
		
		//MOD Defensor
		function actualizaEstatusAvisoDesignacion(idAvisoDesignacion){			
			var param = 'idDocumento='+ idAvisoDesignacion;
		   	$.ajax({
		   		type: 'POST',
		   		url: "<%= request.getContextPath()%>"+"/actualizaEstatusAvisoDesignacion.do",
		   		data: param,
		   		dataType: 'xml',
		   		async: false,
		   		success: function(xml){}
		   	});
	   }

		//Grid de asesoria
		function gridAsesoria(){
			jQuery("#gridAsesorias").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarExpedientesEtapa.do?etapa=<%=EtapasExpediente.ASESORIA.getValorId()%>',
				datatype: "xml", 
				colNames:['Expediente','Interesado','Fecha de Solicitud'], 
				colModel:[ 	{name:'expediente',index:'expediente', width:230,align:"center"},
				           	{name:'interesado',index:'interesado', width:230,align:"center"},
				          	{name:'FHSolicitud',index:'fHSolicitud', width:230,align:"center"}
				           	
						],
				pager: jQuery('#pagGridAsesorias'),
				rowNum:25,
				rowList:[10,20,30],
				autowidth: true,
				//autoheight:true,
				sortname: 'numeroExpediente',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					numExpediente=rowid;
					idExpediente=rowid;
					numEtapa=<%=EtapasExpediente.ASESORIA.getValorId()%>;
					valorEtapa="Asesor&iacute;a";
					var ret = jQuery("#gridAsesorias").jqGrid('getRowData',rowid);
					numeroExpediente=ret.expediente;
					defensaIntegracion();			
				}			
			}).navGrid('#pagGridAsesorias',{edit:false,add:false,del:false});
			$("#gview_gridAsesorias .ui-jqgrid-bdiv").css('height', '525px');
			jQuery("#gridAsesorias").trigger("reloadGrid");
		}

		//gridDefensaRestaurativa
		function gridDefensaRestaurativa() {
			jQuery("#gridDefensaRestaurativa").jqGrid({
				url : '<%= request.getContextPath()%>/consultarExpedientesEtapa.do?etapa=<%=EtapasExpediente.CONCILIACION_Y_MEDIACION.getValorId()%>', 
				datatype: "xml", 
				colNames:['Caso','Expediente','Imputados', 'Etapa', 'Estatus'], 
				colModel:[ 	{name:'caso',index:'2002', width:25},
							{name:'expediente',index:'2003', width:25},
							{name:'imputados',index:'2009', width:30}, 								
							{name:'etapa',index:'2040', width:15},
							{name:'estatus',index:'2011', width:25}
				],
				pager: jQuery('#pagerDefensaRestaurativa'), 
				rowNum:25, 
				rowList:[10,20,30],
				autowidth: true,
				autoheight: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc",
				scrollOffset:0,
				ondblClickRow: function(rowid) {
					numExpediente=rowid;
					idExpediente=rowid;
					numEtapa=<%=EtapasExpediente.CONCILIACION_Y_MEDIACION.getValorId()%>;
					valorEtapa="Conciliaci&oacute;n y Mediaci&oacute;n";
					var ret = jQuery("#gridDefensaRestaurativa").jqGrid('getRowData',rowid);
					numeroExpediente=ret.expediente;
					numeroCaso=ret.caso;
					defensaIntegracion();
				}
			}).navGrid('#pagerDefensaRestaurativa',{edit:false,add:false,del:false});
			$("#gridDefensaRestaurativa").trigger("reloadGrid");
			$("#gview_gridDefensaRestaurativa .ui-jqgrid-bdiv").css('height', '450px');	
		}
		
		//grid defensa e integracion
		function gridDefensaIntegracionDefensoria() {
			jQuery("#gridDetalleFrmPrincipalTres").jqGrid({
				url : '<%= request.getContextPath()%>/consultarExpedientesEtapa.do?etapa=<%=EtapasExpediente.INTEGRACION.getValorId()%>', 
				datatype: "xml", 
				colNames:['Caso','Expediente','Imputados', 'Etapa', 'Estatus'], 
				colModel:[ 	{name:'caso',index:'2002', width:25},
							{name:'expediente',index:'2003', width:25},
							{name:'imputados',index:'2009', width:30}, 								
							{name:'etapa',index:'2040', width:15},
							{name:'estatus',index:'2011', width:25}
					],
				pager: jQuery('#pagerTres1'), rowNum:25, rowList:[10,20,30],
				autowidth: true, autoheight:true, sortname: 'detalle',
				viewrecords: true, sortorder: "desc",
				ondblClickRow: function(rowid) {
					idExpediente=rowid;
					numExpediente=rowid;
					numEtapa=<%=EtapasExpediente.INTEGRACION.getValorId()%>;
					valorEtapa="Integraci&oacute;n";
					var ret = jQuery("#gridDetalleFrmPrincipalTres").jqGrid('getRowData',rowid);
					numeroExpediente=ret.expediente;
					numeroCaso=ret.caso;
					defensaIntegracion();
				}
			}).navGrid('#pagerTres1',{edit:false,add:false,del:false});
			$("#gridDetalleFrmPrincipalTres").trigger("reloadGrid");
			$("#gview_gridDetalleFrmPrincipalTres .ui-jqgrid-bdiv").css('height', '450px');	
		}
		

		function gridDefensaTecnicaDefensoria() {		
			jQuery("#gridDefensaTecnica").jqGrid({
				url : '<%= request.getContextPath()%>/consultarExpedientesEtapa.do?etapa=<%=EtapasExpediente.TECNICA.getValorId()%>', 
				datatype: "xml", 
				colNames:['Caso','Expediente','Imputados', 'Etapa', 'Estatus'], 
				colModel:[ 	{name:'caso',index:'2002', width:25},
							{name:'expediente',index:'2003', width:25},
							{name:'imputados',index:'2009', width:30}, 								
							{name:'etapa',index:'2040', width:15},
							{name:'estatus',index:'2011', width:25}
					],
				pager: jQuery('#pagerDefensaTecnica'), rowNum:25, rowList:[10,20,30],
				autowidth: true, autoheight:true, sortname: 'detalle',
				viewrecords: true, sortorder: "desc",
				ondblClickRow: function(rowid) {
					numExpediente=rowid;
					numEtapa=<%=EtapasExpediente.TECNICA.getValorId()%>;
					valorEtapa="T&eacute;cnica";
					var ret = jQuery("#gridDefensaTecnica").jqGrid('getRowData',rowid);
					numeroExpediente=ret.expediente;
					numeroCaso=ret.caso;
					defensaIntegracion();
				}
			}).navGrid('#pagerDefensaTecnica',{edit:false,add:false,del:false});
			$("#gridDefensaTecnica").trigger("reloadGrid");
			$("#gview_gridDefensaTecnica .ui-jqgrid-bdiv").css('height', '450px');	
		}

		function gridEjecucion() {
			jQuery("#gridEjecucion").jqGrid({
				url : '<%= request.getContextPath()%>/consultarExpedientesEtapa.do?etapa=<%=EtapasExpediente.EJECUCION.getValorId()%>', 
				datatype: "xml", 
				colNames:['Caso','Expediente','Imputados', 'Etapa', 'Estatus'], 
				colModel:[ 	{name:'caso',index:'2002', width:25},
							{name:'expediente',index:'2003', width:25},
							{name:'imputados',index:'2009', width:30}, 								
							{name:'etapa',index:'2040', width:15},
							{name:'estatus',index:'2011', width:25}
					],
				pager: jQuery('#pagerEjecucion'), rowNum:25, rowList:[10,20,30],
				autowidth: true, autoheight:true, sortname: 'detalle',
				viewrecords: true, sortorder: "desc",
				ondblClickRow: function(rowid) {
					numExpediente=rowid;
					numEtapa=<%=EtapasExpediente.EJECUCION.getValorId()%>;
					var ret = jQuery("#gridEjecucion").jqGrid('getRowData',rowid);
					numeroExpediente=ret.expediente;
					numeroCaso=ret.caso;
					defensaIntegracion();
				}
			}).navGrid('#pagerEjecucion',{edit:false,add:false,del:false});
			$("#gridEjecucion").trigger("reloadGrid");
			$("#gview_gridEjecucion .ui-jqgrid-bdiv").css('height', '450px');
		}

	//grid para carpeta de investigacion
	function gridCarpetaInvestigacion(){
		params="tipoSolicitud="+<%=TiposSolicitudes.CARPETA_DE_INVESTIGACION.getValorId()%>
		jQuery("#gridCarpetaInvestigacion").jqGrid({ 
			url:'<%= request.getContextPath()%>'+"/consultarSolicitudesPorAtender.do?"+params, 
			datatype: "xml", 
			colNames:['Caso','Expediente','Folio','Estatus','Fecha Creaci&oacute;n', 'Fecha L&iacute;mite', 'Institucion','Destinatario'], 
					colModel:[ 	{name:'caso',			index:'caso', 			width:40},
					           	{name:'expediente',		index:'expediente',		width:40},
					           	{name:'folio',			index:'folio', 			width:30},
					           	{name:'estatus',		index:'estatus', 		width:10}, 
								{name:'fechaCreacion',	index:'fechaCreacion',	width:20}, 
								{name:'fechaLimite',	index:'fechaLimite', 	width:10,	hidden:true},
								{name:'institucion',	index:'institucion', 	width:15},
								{name:'destinatario',	index:'destinatario', 	width:45}
							],
				pager: jQuery('#pagerCarpetaInvestigacion'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				autoheight:true,
				sortname: 'numeroExpediente',
				viewrecords: true,
				sortorder: "desc"
		}).navGrid('#pagerCarpetaInvestigacion',{edit:false,add:false,del:false});
		$("#gridCarpetaInvestigacion").trigger("reloadGrid");
		$("#gview_gridCarpetaInvestigacion .ui-jqgrid-bdiv").css('height', '450px');	
	}

	//grid para asesorias periciales
	function gridAsesoriasPericiales(){
		params="tipoSolicitud="+<%=TiposSolicitudes.ASESORIA.getValorId()%>
			jQuery("#gridDetalleAsesoriasPericiales").jqGrid({ 
				url:'<%= request.getContextPath()%>'+"/consultarSolicitudesPorAtender.do?"+params, 
				datatype: "xml", 
				colNames:['Caso','Expediente','Folio','Estatus','Fecha Creaci&oacute;n', 'Fecha L&iacute;mite', 'Institucion','Destinatario'], 
				colModel:[ 	{name:'caso',			index:'caso', width:40},
				           	{name:'expediente',		index:'expediente', width:40},
				           	{name:'folio',			index:'folio', width:40},
				           	{name:'estatus',		index:'estatus', width:40}, 
							{name:'fechaCreacion',	index:'fechaCreacion', width:30}, 
							{name:'fechaLimite',	index:'fechaLimite', width:30},
							{name:'institucion',	index:'institucion', width:40},
							{name:'destinatario',	index:'destinatario', width:10}
						],
				pager: jQuery('#pagerAsesoriasPericiales'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				autoheight:true,
				sortname: 'expediente',
				viewrecords: true,
				ondblClickRow: function(rowid) {
					solServicioPericial(rowid);
				},
				sortorder: "desc"
		}).navGrid('#pagerAsesoriasPericiales',{edit:false,add:false,del:false});
			$("#gridDetalleAsesoriasPericiales").trigger("reloadGrid");
			$("#gview_gridDetalleAsesoriasPericiales .ui-jqgrid-bdiv").css('height', '450px');	
	}
	
	//grid para dictamenes periciales
	function gridDictamenPericial(){
		params="tipoSolicitud="+<%=TiposSolicitudes.DICTAMEN.getValorId()%>
			jQuery("#gridDetalleDictamenPericial").jqGrid({ 
				url:'<%= request.getContextPath()%>'+"/consultarSolicitudesPorAtender.do?"+params,
				datatype: "xml", 
				colNames:['Caso','Expediente','Folio','Estatus','Fecha Creaci&oacute;n', 'Fecha L&iacute;mite', 'Institucion','Destinatario'], 
				colModel:[ 	{name:'caso',index:'caso', width:40},
				           	{name:'expediente',index:'expediente', width:40},
				           	{name:'folio',index:'folio', width:40},
				           	{name:'estatus',index:'estatus', width:40}, 
							{name:'fechaCreacion',index:'fechaCreacion', width:30}, 
							{name:'fechaLimite',index:'fechaLimite', width:30},
							{name:'institucion',index:'institucion', width:40},
							{name:'destinatario',index:'destinatario', width:10}
						],
				pager: jQuery('#pagerDictamenPericial'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				autoheight:true,
				sortname: 'expediente',
				viewrecords: true,
				ondblClickRow: function(rowid) {
					solServicioPericial(rowid);
				},
				sortorder: "desc"
		}).navGrid('#pagerDictamenPericial',{edit:false,add:false,del:false});
		$("#gridDetalleDictamenPericial").trigger("reloadGrid");
		$("#gview_gridDetalleDictamenPericial .ui-jqgrid-bdiv").css('height', '450px');	
	}
	
	//grid para Audio y video
	function gridAudioVideo(){
		params="tipoSolicitud="+<%=TiposSolicitudes.AUDIO_VIDEO.getValorId()%>
		jQuery("#gridAudioVideo").jqGrid({ 
			url:'<%= request.getContextPath()%>'+"/consultarSolicitudesPorAtender.do?"+params, 
			datatype: "xml", 
			colNames:['Caso','Expediente','Folio','Estatus','Fecha Creaci&oacute;n', 'Fecha L&iacute;mite', 'Institucion','Destinatario'], 
			colModel:[ 	{name:'caso',index:'caso', width:40},
			           	{name:'expediente',index:'expediente', width:40},
			           	{name:'folio',index:'folio', width:40},
			           	{name:'estatus',index:'estatus', width:40}, 
						{name:'fechaCreacion',index:'fechaCreacion', width:30}, 
						{name:'fechaLimite',index:'fechaLimite', width:30},
						{name:'institucion',index:'institucion', width:40},
						{name:'destinatario',index:'destinatario', width:10}
					],
			pager: jQuery('#pagerAudioVideo'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			autoheight:true,
			sortname: 'numeroExpediente',
			viewrecords: true,
			sortorder: "desc"
		}).navGrid('#pagerAudioVideo',{edit:false,add:false,del:false});
		$("#gridAudioVideo").trigger("reloadGrid");
		$("#gview_gridAudioVideo .ui-jqgrid-bdiv").css('height', '450px');	
	}

	//grid para Transcripcion
	function gridTranscripcion(){
		params="tipoSolicitud="+<%=TiposSolicitudes.TRANSCRIPCION_DE_AUDIENCIA.getValorId()%>
		jQuery("#gridTranscripcion").jqGrid({ 
			url:'<%= request.getContextPath()%>'+"/consultarSolicitudesPorAtender.do?"+params, 
			datatype: "xml", 
			colNames:['Caso','Expediente','Folio','Estatus','Fecha Creaci&oacute;n', 'Fecha L&iacute;mite', 'Institucion','Destinatario'], 
			colModel:[ 	{name:'caso',index:'caso', width:40},
			           	{name:'expediente',index:'expediente', width:40},
			           	{name:'folio',index:'folio', width:40},
			           	{name:'estatus',index:'estatus', width:40}, 
						{name:'fechaCreacion',index:'fechaCreacion', width:30}, 
						{name:'fechaLimite',index:'fechaLimite', width:30},
						{name:'institucion',index:'institucion', width:40},
						{name:'destinatario',index:'destinatario', width:10}
					],
			pager: jQuery('#pagerTranscripcion'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			autoheight:true,
			sortname: 'numeroExpediente',
			viewrecords: true,
			sortorder: "desc"
		}).navGrid('#pagerTranscripcion',{edit:false,add:false,del:false});
		$("#gridTranscripcion").trigger("reloadGrid");
		$("#gview_gridTranscripcion .ui-jqgrid-bdiv").css('height', '450px');	
	}
	
	function gridCoordinarCarpetaDefensoria() {

		jQuery("#gridCoordinarCarpeta").jqGrid({
					url : '<%= request.getContextPath()%>/CoordinarCarpetaDefensoria.do', 
					datatype: "xml", 
				
					colNames:['Involucrados','Delito','Hechos','Objetos y evidencias','Periciales','Audiencias','Documentos'], 
					colModel:[ 	{name:'Involucrados',index:'Involucrados', width:15},
					        	{name:'Delito',index:'Delito', width:15},
					           	{name:'Hechos',index:'Hechos', width:15}, 
								{name:'Objetos',index:'Objetos', width:30}, 
								
								{name:'Periciales',index:'Periciales', width:30}, 
								{name:'Audiencias',index:'Audiencias', width:30},
								{name:'Documentos',index:'Documentos', width:30}
								
					
				],
		pager: jQuery('#pagerCoordinarCarpeta'),
		rowNum:10,
		rowList:[10,20,30],
		autowidth: true,
		autoheight:true,
		sortname: 'detalle',
		viewrecords: true,
		sortorder: "desc",
		ondblClickRow: function(rowid) {
		numExpediente=rowid.split("-")[0];
		//activaConfirm("DefensaIntegracionDefensoria");		
		numEtapa=rowid.split("-")[1];
		defensaIntegracion();
		
		}
	}).navGrid('#pager1',{edit:false,add:false,del:false});
		$("#gridCoordinarCarpeta").trigger("reloadGrid");
		$("#gview_gridCoordinarCarpeta .ui-jqgrid-bdiv").css('height', '450px');
	}

	

	function menuDependienteEtapa(){
		var param = '';	
	  	$.ajax({
	  		type: 'POST',
	  		url: '<%= request.getContextPath()%>/ConsultarExpedientesUsuarioArea.do',
			data : param,
			dataType : 'xml',
			async : false,
			success : function(xml) {
				/*$(xml).find('lista').find('expedienteDTOs').find('etapa').each(
				function() {
					agregarExpedientesArbol($(this).find('idCampo').text());
				});*/
			}
		});
	}
	
	function agregarExpedientesArbol(etapa){
		switch(parseInt(etapa)){
			case <%= EtapasExpediente.INTEGRACION.getValorId() %>:
				$("#menuDefIntegracion").css("display", "block");
				break;
			case <%= EtapasExpediente.TECNICA.getValorId() %>:
				$("#menuDefTecnica").css("display",	"block");
				break;
			case <%= EtapasExpediente.CONCILIACION_Y_MEDIACION.getValorId() %>:
				$("#menuDefRestaurativa").css("display", "block");
				break;
			case <%= EtapasExpediente.EJECUCION.getValorId() %>:
				$("#menuDefEjecucion").css("display", "block");
				break;
			<%-- case <%= EtapasExpediente.ASESORIA.getValorId() %>:
				$("#menuDefAsesoria").css("display", "block");
				break; --%>
		}
	}
	
		function agregarEtapa(){
			Subordinados();
			
			$("#divEtapaExpediente").dialog("open");
			$("#divEtapaExpediente").dialog({ autoOpen: true, 
				modal: true, 
			  	title: 'Favor de Seleccionar la Etapa del Expediente', 
			  	dialogClass: 'alert',
			  	position: [312,40],
			  	width: 900,
			  	height: 477,
			  	maxWidth: 1000,
			  	buttons:{"Buscar":function() {	
				//$("#checkTodosExpedientes").val();
			  				  		
			  		gridExpedientesDeEtapas();			 
			  			$(this).dialog("close");
			  		},
			  		"Cancelar":function() {
			  			$(this).dialog("close");
			  		}
			  	}
			});	
			    
				 
				  
		}	

		 function Subordinados(){
				jQuery("#gridSubordinados").jqGrid({ 
					url:'<%= request.getContextPath()%>/ConsultarFuncionariosSubordinadosEtapa.do', 
					datatype: "xml", 
					colNames:['Nombre Funcionario'], 
					colModel:[ 	{name:'Funcionario',index:'Funcionario', width:400},
					           	
					           	
							],
					pager: jQuery('#pagGridSubordinados'),
					rowNum:10,
					rowList:[10,20,30],
					autowidth: true,
					autoheight:true,
					height:300,
					sortname: 'numeroExpediente',
					viewrecords: true,
					sortorder: "desc",

					onresize_end:			function () { $("#pagGridSubordinados").setGridWidth($("#mainContent").width() - 5, true); 
					},
					onSelectRow: function(rowid){
						idFuncionario=rowid;
						
						//agregarEtapa();
						
					}
					
				}).navGrid('#pagGridSubordinados',{edit:false,add:false,del:false});
								
				$("#gridSubordinados").trigger("reloadGrid"); 
		    }

		    		 		
   /*
	*Funcion que carga el grid de audiencias, que consulta las audiencias agendadas en PJ
	*el coordinador debe ver todas las audiencias por lo que se quita el filtro de funcionario
	*en la clase action
	*/
	function gridAudiencias(porSemana){
		
		var fechaInicio=$('#fechaInicio').val();
		var fechaFin=$('#fechaFin').val();
		var desarrolloJAVS="TRUE";

		if(gridAud==0){
    		jQuery("#gridAudiencias").jqGrid({ 
    			url: contextoPagina + '/consultarAudienciasDefensor.do?inicio='+fechaInicio+'&desarrolloJAVS='+desarrolloJAVS+'&fin='+fechaFin+'&porSemana='+porSemana+'', 	   		
    			datatype: "xml", 
				colNames:['Audiencia','Caso','Car&aacute;cter','Tipo de Audiencia','Fecha de Audiencia','Sala'], 
				colModel:[	{name:'audiencia',	index:'audiencia',	width:60,	sortable:false},
							{name:'caso',		index:'caso',		width:180,	sortable:false},
			          		{name:'caracter',	index:'caracter',	width:50,	sortable:false},
							{name:'tipo',		index:'tipo',		width:200,	sortable:false},
							{name:'fechaHora',	index:'fechaHora',	width:200,	sortable:false},
							{name:'sala',		index:'sala',		width:150,	sortable:false}				         
					],			
				pager: jQuery('#pagerAudiencias'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				autoheight:true,
				height:400,
				viewrecords: true,
				sortorder: "desc",
				loadComplete: function(){
					$('#fechaInicio').val("");
					$('#fechaFin').val("");
				},
				ondblClickRow: function(rowid) {
					var row = jQuery("#gridAudiencias").jqGrid('getRowData',rowid);
					dblClickRowBandejaAudiencias(row);
				}
			}).navGrid('#pagerAudiencias',{edit:false,add:false,del:false});
    		
			gridAud=1;
		}
		else{
			
			jQuery("#gridAudiencias").jqGrid('setGridParam', {url: contextoPagina + '/consultarAudienciasDefensor.do?inicio='+fechaInicio+'&desarrolloJAVS='+desarrolloJAVS+'&fin='+fechaFin+'&porSemana='+porSemana+'',
					datatype: "xml" });
			$("#gridAudiencias").trigger("reloadGrid");
			$("#gview_gridAudiencias .ui-jqgrid-bdiv").css('height', '450px');
		}
	}
		 
 	function dblClickRowBandejaAudiencias(row){
		audiencia= row.audiencia;
		caso=	   row.caso;
		caracter=  row.caracter;
		tipo=	   row.tipo;
		sala=	   row.sala;		
		fecha= 	   row.fechaHora;
			
		idWindowVisorAudiencia++;
		$.newWindow({id:"idWindowVisorAudiencia"+ idWindowVisorAudiencia, statusBar: true, posx:50,posy:111,width:1130,height:400,title:"Atenci&oacute;n de Audiencias", type:"iframe"});
	    $.updateWindowContent("idWindowVisorAudiencia"+ idWindowVisorAudiencia,'<iframe src="<%=request.getContextPath()%>/visorDetalleAudiencia.do?audiencia='+audiencia+'&caso='+caso+'&caracter='+caracter+'&tipo='+tipo+'&sala='+sala+'&fecha='+fecha+'" width="1130" height="400"/>'); 
	}
	
	 function gridAudienciasGeneradas(){
			params="tipoSolicitud="+<%=TiposSolicitudes.AUDIENCIA.getValorId()%>
			jQuery("#gridAudienciasGeneradas").jqGrid({ 
				url:'<%= request.getContextPath()%>'+"/consultarSolicitudesPorAtender.do?"+params, 
				datatype: "xml", 
				colNames:['Caso','Expediente','Folio','Estatus','Fecha creaci&oacute;n','Fecha limite','Institucion','Destinatario'], 
				colModel:[{name:'caso',	 		index:'2002', 	width:130},
				          {name:'expediente',	index:'2003',	width:130},
				          {name:'folio',	 	index:'2003',	width:130},
				          {name:'estatus',		index:'', 		width:100},
				          {name:'fechaCreacion',index:'', 	    width:120},
				          {name:'fechaLimite',	index:'',		width:155},
				          {name:'institucion' ,	index:'2040', 	width:110},
				          {name:'destinatario' ,index:'', 		width:110}					         
						],
				
				pager: jQuery('#pagerAudienciasGeneradas'),
				rowNum:30,
				rowList:[10,20,30],
				autowidth: true,
				autoheight:true,
				sortname: 'numeroExpediente',
				viewrecords: true,
				sortorder: "desc"
			}).navGrid('#pagerAudienciasGeneradas',{edit:false,add:false,del:false});
				jQuery("#gridAudienciasGeneradas").trigger('reloadGrid');
				$("#gview_gridAudienciasGeneradas .ui-jqgrid-bdiv").css('height', '450px');
			  }


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

	  var iframewindowAPSE = 0;
	    function asigarPermisos(){
			$.newWindow({id:"iframewindowAPSE"+iframewindowAPSE, statusBar: true, posx:10,posy:10,width:1430,height:670,title:"Asignar permisos sobre Expediente: ", type:"iframe"});
			$.updateWindowContent("iframewindowAPSE"+iframewindowAPSE,'<iframe src="<%= request.getContextPath() %>/asigarPermisosExpediente.do" width="1430" height="670" />');
			$("#" +"iframewindowAPSE"+iframewindowAPSE + " .window-maximizeButton").click();
			iframewindowAPSE++;
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
	
	/*
	*Funcion que carga la ventana modal para seleccionar el intervalo de fechas entre las cuales
	*se desea consultar las audiencias agendadas
	*/
	function modalFecha(){

		$('#fechaInicio').val('');
		$('#fechaFin').val('');
		/**
		* Carga los escuchadores de eventos para los combo box's para 
		* el domicilio 
		*/
		$("#fechaInicio").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});

		$("#fechaFin").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		
		//abre la ventana de detalle de la persona
		$("#busquedaFecha").dialog("open");
		$("#busquedaFecha").dialog({ autoOpen: true, 
	  		modal: true, 
	  		title: 'Buscar por Fecha', 
	  		dialogClass: 'alert',
			resizable: false,
			height:'auto',
			width:'auto',
	  		buttons:{"Aceptar":function() {
		  				validaCamposFecha($('#fechaInicio').val(), $('#fechaFin').val());
		  				gridAudiencias(false);
		  				if(validaFecha==true){
		  					$(this).dialog("close");
				  		}
	  				},
	  				"Cancelar":function() {
		  				$(this).dialog("close");
	  				}
	  		}
	  	});
				
	}

	/**
	* Funcion que se encarga de habilitar el modulo de Asesorias Legal
	* de acuerdo al parametro definido en BD
	*/
	function habilitarModuloAsesoriaLegalDefensor(){
		var idParametro = '<%=Parametros.ACTIVA_MODULO_ASESORIA_LEGAL.ordinal()%>';
		habilitarAsesoriaLegal = consultarParametroGenerico(idParametro);

		// Deshabilitar Asesoria Legal 
		if( habilitarAsesoriaLegal == null || parseInt(habilitarAsesoriaLegal) == 0 ){
			$("#ligSolicitudesAsesoriaLegal").hide();
			$("#seccionSolicitudesAsesoriaLegal").hide();
		}
	}


	</script>	
</head>

<body>

	<!--div para la ventana modal de buscar audiencias agendadas por fecha-->
	<div id="busquedaFecha" style="display: none">
		<table cellspacing="0" cellpadding="0" >
			<tr>
				<td width="153">&nbsp;</td>
				<td width="153">&nbsp;</td>
			</tr>
			<tr>
			  <td colspan="2" align="center">
			  	<strong>Fecha Inicio:</strong>
			  	<input type="text" id="fechaInicio" size="20" />		  
			  </td>
		    </tr>
			<tr>
			  <td align="center">&nbsp;</td>
			  <td align="center">&nbsp;</td>
	  		</tr>
			<tr>
			  <td colspan="2" align="center">
			  	<strong>Fecha Fin:&nbsp;&nbsp;</strong>&nbsp;
		      	<input type="text" id="fechaFin" size="20" /></td>
	  		</tr>
			<tr>
			  <td align="center">&nbsp;</td>
			  <td align="center">&nbsp;</td>
	  		</tr>
		</table>
	</div>
	
<div class="ui-layout-west" >
	<div class="header">&nbsp;</div>
	<div class="content">
		<div id="accordionmenuprincipal">
			<h3><a href="#"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Expedientes </a></h3>
			<div>
				<ul id="seccion1tree" class="filetree">
				<!-- 
					<li class="closed"  id="menuDefRestaurativa" onmousedown="ocultaMuestraGridsAlertas('2');" ><span class="folder"  >Conciliaci&oacute;n y mediaci&oacute;n</span>
						<ul></ul>
					</li>	
					<li class="closed"  id="menuDefIntegracion" onmousedown="ocultaMuestraGridsAlertas('3');" ><span class="folder"  >Defensa en integraci&oacute;n</span>
						<ul></ul>
					</li>
					<li class="closed" id="menuDefTecnica" onmousedown="ocultaMuestraGridsAlertas('5')" ><span class="folder"  >Defensa t&eacute;cnica</span>
						<ul></ul>
					</li>	
					<li class="closed" id="menuDefEjecucion" onmousedown="ocultaMuestraGridsAlertas('7')"><span class="folder"  >Defensa en ejecuci&oacute;n</span>
						<ul></ul>
					</li> -->	
				<!-- 	<li class="closed" id="menuDefAsesoria" onmousedown="ocultaMuestraGridsAlertas('13')"><span class="folder"  >Asesor&iacute;a legal</span>
						<ul></ul>
					</li> -->	
					 	
				</ul>	
			</div>
			
			<h3><a href="#" id="avisosAsignacion"  onmousedown="ocultaMuestraGridsAlertas('8');"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Solicitudes Asignadas sin Atender</a></h3>
			<div></div>

			<h3><a href="#" id="Audiencia" onmousedown="ocultaMuestraGridsAlertas('12');"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;AudienciaPP</a></h3>
			<div>			
				<ul id="seccion3treePJEA" class="filetree">
					<li><span class="file"><a id="audiencia" style="cursor: pointer;" onclick="gridAudiencias('false')">Del D&iacute;a </a></span></li>
					<li><span class="file"><a id="audiencia" style="cursor: pointer;" onclick="gridAudiencias('true')">Por Semana</a></span></li>					
					<li><span class="file"><a id="audiencia" style="cursor: pointer;" onclick="modalFecha()">Por Fecha</a></span></li>
				</ul>		
			</div>
							
			<h3><a href="#"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Solicitudes generadas</a></h3>
			<div>
				<ul id="seccion2tree" class="filetree">
					<li class="closed" id="carpetaInvestigacion"><span class="folder" id="PA2" onmousedown="ocultaMuestraGridsAlertas('9')">Carpeta de investigaci&oacute;n</span>
						<ul></ul>
					</li>
					<!-- li class="closed"><span class="folder" id="ligServiciosPericiales" onmousedown="ocultaMuestraGridsAlertas('4');">Servicios periciales</span>
					<ul>
						<li class="closed">
							<span class="folder" id="asesoriasPericiales" onmousedown="ocultaMuestraGridsAlertas('41');">Asesorias Periciales</span>
							<ul></ul>
						</li>
						<li class="closed">
							<span class="folder" id="dictamenPericial" onmousedown="ocultaMuestraGridsAlertas('42');">Dictamenes Periciales</span>
							<ul></ul>
						</li>
					</ul>
					</li>
					<li class="closed" id="audioVideo"><span class="folder" onmousedown="ocultaMuestraGridsAlertas('10')">Audio y video</span>
					<ul></ul>
					</li>		
					<li class="closed" id="fechas"><span class="folder"  onmousedown="ocultaMuestraGridsAlertas('11')">Transcripciones</span>
					<ul></ul>
					</li-->
					<li class="closed" id="audiencia"><span class="folder"  onmousedown="ocultaMuestraGridsAlertas('14')">Audiencia</span>
					<ul></ul>
					</li>
				</ul>				
			</div>
		
			<!--Tab de Solicitudes de Asesoria Legal -->
			<h3 id="seccionSolicitudesAsesoriaLegal"><a href="#" id="ligSolicitudesAsesoriaLegal"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Solicitudes de Asesor&iacute;a Legal</a></h3>
				<div>
					<ul id="seccion6tree" class="filetree">
								<li class="file">
									<span class="file" onclick="cargarGridAsesoria('<%=Instituciones.DEF.getValorId()%>','<%=EstatusSolicitud.ASIGNADO.getValorId()%>')">Asignadas</span>
								</li>
								<li class="file">
									<span class="file" onclick="cargarGridAsesoria('<%=Instituciones.DEF.getValorId()%>','<%=EstatusSolicitud.EN_PROCESO.getValorId()%>')">En Proceso</span>
								</li>
								<li class="file">
									<span class="file" onclick="cargarGridAsesoria('<%=Instituciones.DEF.getValorId()%>','<%=EstatusSolicitud.CERRADA.getValorId()%>')">Cerradas</span>
								</li>
					</ul>
				</div>
		
			
	<!-- 
			<h3><a href="#"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Hist&oacute;rico de expedientes </a></h3>
			<div>
				<ul id="seccion1tree" class="filetree">
					<li class="closed" id="fechas"><span class="folder"  onclick="">Hist&oacute;rico</span>
						<ul>
							<li class="closed" id="caso1"><span class="folder" id="C1" onclick="">2010</span>
								<ul></ul>
							</li>
							<li class="closed" id="caso1"><span class="folder" id="C2" onclick="">2011</span>
								<ul></ul>
							</li>
						</ul>
					</li>
				</ul>
			</div-->
<!-- 			<h3 ><a id="" href="#" onclick="generaVisorGraficaView()"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png"  width="15" height="15">Gr&aacute;ficas y Reportes</a></h3>
				<div>		
					<input type="button" value="Ver Grafica" id="imageViewer" name="imageViewer"/>	
					<ul id="seccion3treePJENC" class="filetree">
						<li class="closed" id="casosPJENC"><span class="folder">Casos</span>
							 Aqui se agregan los casos y expedientes dinamicamente 
						</li>
					</ul>	
				</div>-->
		</div>
	</div>
</div>

<div class="ui-layout-east">
	<div class="header">Bienvenido</div>
	<div class="content">
		<div id="accordionmenuderprincipal">
			<h4>
				<a href="#">Datos Personales</a>
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
			
<!--			<h1>-->
<!--				<a href="#">Clima</a>-->
<!--			</h1>-->
<!--			<div align="left">-->
<!--				<div align="left" id="test"></div>-->
<!--			</div>-->
			<h1>
				<a href="#">Chat</a>
			</h1>
			<div>
				<div id="dialogoChat" title="Chat" align="center">
					<iframe src="<%=((ConfiguracionDTO)session.getAttribute(LoginAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getUrlServidorChat()%>" frameborder="0" width="380" height="280"></iframe>
				</div>
				<center>
					<a onclick="ejecutaChat();" id="controlChat"><img src="<%= request.getContextPath()%>/resources/images/img_chat.png" width="130" height="104"></a>
				</center>
			</div>
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
	<ul class="toolbar ui-widget-header">
		<div id="menu_head">
			<li id="tbarBtnHeaderZise" class="first"><span></span></li>
			
						
		</div>
		<div id="menu_config">
<!--			<li id="buscarCaso">Buscar Caso&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_busca2.png" width="15" height="16"></li>
			<li id="buscarExpediente" >Buscar Expediente&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_busca3.png" width="15" height="16"></li>
			<li id="accesoExpediente">Acceso a Expediente&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_dctosearch1.png" width="15" height="16"></li>
			<li id="generarDocumento">Generar Documento</li>-->
<!--			<li>Turnar Documento</li>-->
<!--			<li>Digitalizar Documento</li>-->
<!--			<li id="verde">Configuraci&oacute;n&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_config.png" width="15" height="16"></li>-->
		</div>
	</ul>
</div>



	<div id="mainContent">
		<div class="ui-layout-center">
			<div class="ui-layout-content">
				<div id="divGridEtapaExpediente">
					<table id="gridEtapaExpediente"></table>
					<div id="pagerEtapaExpediente"></div>
				</div>
				<div id="divGridDefensaRestaurativa">
					<table id="gridDefensaRestaurativa"></table>
					<div id="pagerDefensaRestaurativa"></div>
				</div>
				<div id="divGridSolicitudes">
					<table id="gridDetalleFrmPrincipal"></table>
					<div id="pager1"></div>
				</div>
				<div id="divGridDefensor">
					<table id="gridDetalleFrmPrincipalTres"></table>
					<div id="pagerTres1"></div>
				</div>
				<div id="divGridAsesoriasPericiales">
					<table id="gridDetalleAsesoriasPericiales"></table>
					<div id="pagerAsesoriasPericiales"></div>
				</div>
				
				<div id="divGridDictamenPericial">
					<table id="gridDetalleDictamenPericial"></table>
					<div id="pagerDictamenPericial"></div>
				</div>
				
				<div id="divGridDefensaTecnica">
					<table id="gridDefensaTecnica"></table>
					<div id="pagerDefensaTecnica"></div>
				</div>
				
				<div id="divGridCoordinarCarpeta">
					<table id="gridCoordinarCarpeta"></table>
					<div id="pagerCoordinarCarpeta"></div>
				</div>
								
				<div id="divGridEjecucion">
					<table id="gridEjecucion"></table>
					<div id="pagerEjecucion"></div>
				</div>
				
				<div id="divGridAvisosAsignacion">
					<table id="gridAvisosAsignacion"></table>
					<div id="pagerAvisosAsignacion"></div>
				</div>
				
				<div id="divGridCarpetaInvestigacion">
					<table id="gridCarpetaInvestigacion"></table>
					<div id="pagerCarpetaInvestigacion"></div>
				</div>
				
				<div id="divGridAudioVideo">
					<table id="gridAudioVideo"></table>
					<div id="pagerAudioVideo"></div>
				</div>
				
				<div id="divGridTranscripcion">
					<table id="gridTranscripcion"></table>
					<div id="pagerTranscripcion"></div>
				</div>
				
				<div id="divGridAudiencias">
					<table id="gridAudiencias"></table>
					<div id="pagerAudiencias"></div>
				</div>
				<div id="divGridAudienciasGeneradas">
					<table id="gridAudienciasGeneradas"></table>
					<div id="pagerAudienciasGeneradas"></div>
				</div>
				<!--Espacio para el grid de asesorias-->
					<div id="divGridAsesorias">
					<table id="gridAsesorias"></table>
				<div id="pagGridAsesorias"></div>
					
			</div>
		</div>
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
</div>

<div id="divEtapaExpediente" style="display: none">

</br>
	<table border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
      <td><div id="divGridSubordinados">
						<table id="gridSubordinados"></table>
						<div id="pagGridSubordinados"></div>
					</div></td>
					<td>
       <table> 
       
       
        <tr >
      <td rowspan="2">Todas las Etapas</td><td rowspan="2"><input type="checkbox" name="checkTodosExpedientes" id="checkTodosExpedientes" onclick="boqueaEtapa()"> </td>
      </tr>
      <td rowspan="2"></td><td rowspan="2"> </td>
       <tr >
      
      </tr>
       <tr><td>Etapa del Expediente &emsp; </td>
        <td>
        <select id="etapaOptionDEATT">
        
        </select>
        
         </td>
       
      </tr>
      
      <tr >
      
      </tr>
      </table>
      </td>
       
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
	<div id="dialogBlok" title="¡Su sesi&oacute;n est&aacute; a punto de caducar!">
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
