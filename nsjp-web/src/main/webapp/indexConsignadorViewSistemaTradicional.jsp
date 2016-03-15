<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Date"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas" %>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud" %>
<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@page import="mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>


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
	<!--script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.zweatherfeed.js"></script-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/sesion.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>

	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	
	<script type="text/javascript">
	
	<%
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	FuncionarioDTO funcionario = usuario.getFuncionario();
	
	Long idDistrito = 0L;
	Long idDiscriminante = 0L;
	Long idFuncionario = 0L;
	String fechaActual = "";
	
	Date fecha = new Date();
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	
		if(funcionario.getDiscriminante() != null && funcionario.getDiscriminante().getCatDiscriminanteId() != null){
			idDiscriminante = funcionario.getDiscriminante().getCatDiscriminanteId();
			if(funcionario.getDiscriminante().getDistrito() != null && funcionario.getDiscriminante().getDistrito().getCatDistritoId() != null){
				idDistrito = funcionario.getDiscriminante().getDistrito().getCatDistritoId();
			}
		}					
		idFuncionario = funcionario.getClaveFuncionario();
		fechaActual = formato.format(fecha);
		
	RolDTO rolDTO = usuario.getRolACtivo().getRol();

	%>
	
	//Variable para controlar la carga del grid de expedientes
	
	var cargaPrimeraExpPorEstatus = true;
	var tipoDeConsultaDeExpedientes = "delDia"
	var contextoPagina = "${pageContext.request.contextPath}";
	var tiempoActiva = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoBloqueoSesion()%>';


	
	$(document).ready(function() {
		//Herramientas
		chat();
		muestraGadgets();
		logout();
		$("#controlAgenda").click(creaAgenda);

		//Crea las tabs 
		//$("#tabs" ).tabs();			
		outerLayout = $("body").layout( layoutSettings_Outer );
		$("#accordionmenuprincipal").accordion({  fillSpace: true });
		$("#accordionmenuderprincipal").accordion({ fillSpace: true});
		$("#accordionmenuderprincipal").accordion( "option", "icons", null );
		
		$("#seccion1tree").treeview();
		$("#seccion2tree").treeview();
		$("#seccion3tree").treeview();
		$("#seccion4tree").treeview();
		$("#seccion5tree").treeview();

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
		
		//PERMITE CARGAR DE FORMA INICIAL EL GRID CON LOS EXPEDIENTES DEL DIA
		cargaGridExpedientesPorEstatus("<%=EstatusExpediente.POR_ATENDER.getValorId()%>,<%=EstatusExpediente.EN_PROCESO.getValorId()%>,<%=EstatusExpediente.DEVUELTO.getValorId()%>,<%=EstatusExpediente.NEAP.getValorId()%>,<%=EstatusExpediente.CONSIGNADO.getValorId()%>","<%= fechaActual %>","<%= fechaActual %>",<%=Roles.CONSIGNADOR.getValorId()%>,<%=idFuncionario%>);
		
		var ambiente='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAmbiente()%>';
		if(ambiente == undefined || ambiente == "undefined"){
			ambiente = "";
		}
		$("#ambienteLb").html('<strong><big>'+ambiente+'</big></strong>');
	}); //Fin de Onready
	
	
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
		,	onresize_end:			function () { 
										$("#gridExpedientesPorEstatus").setGridWidth($("#mainContent").width() - 1, true);
									}
		}
	};



	/*
	*Funcion que consulta los expedientes de acuerdo a su estatus, fecha inicial, fecha final, idRol y opcionalemente idConsignador
	*/
	function cargaGridExpedientesPorEstatus(estatus,fechaIni,fechaFin,idRol,idConsignador){
		
		var parametros = "";
		
		parametros += 'estatusExpediente=' + estatus;
		parametros += '&fechaInicio=' + fechaIni;
		parametros += '&fechaFin=' + fechaFin;		
		parametros += '&idDistrito=' + <%= idDistrito %>;
		
		if(idConsignador != null && idConsignador != undefined){
			parametros += '&idRol=' + 0 ;
			parametros += '&idFuncionario=' + idConsignador;
		}else{
			parametros += '&idRol=' + idRol;
			parametros += '&idFuncionario=' + 0;
		}
		
		parametros += '&idDiscriminante=' + -1;// -1 No tomar en cuenta para el filtro de expedientes
		
		
		if(cargaPrimeraExpPorEstatus == true){

			jQuery("#gridExpedientesPorEstatus").jqGrid({ 
				url:'<%=request.getContextPath()%>/busquedaExpedientesXEstatus.do?'+ parametros + '',
				datatype: "xml", 
				colNames:['Expediente','Fecha', 'Denunciante', 'Delito','Origen','Estatus'], 
				colModel:[ 	{name:'Expediente',index:'1', width:100, align:"center"}, 
							{name:'Fecha',index:'2', width:50, align:"center"}, 
							{name:'Denunciante',index:'3', width:150, align:"center"}, 
							{name:'Delito',index:'4', width:120, align:"center"},
							{name:'Origen',index:'5', width:50, align:"center"},
							{name:'Estatus',index:'6', width:70, align:"center"},
						],
				pager: jQuery('#pagerGridExpedientesPorEstatus'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				    height:390,
				sortname: '1',
				viewrecords: true,
				ondblClickRow: function(id) {
					consultaDenunciaTradicional(id,estatus,fechaIni,fechaFin,idRol,idConsignador);
				},
				onPaging: function () {
				    //Si la pagina requerida es mas grande que la ultima pagina se resetea a la ultima
				    var requestedPage = $("#gridExpedientesPorEstatus").getGridParam("page");
				    var lastPage = $("#gridExpedientesPorEstatus").getGridParam("lastpage");
				    if (parseInt(requestedPage) > parseInt(lastPage)) {
				      $("#gridExpedientesPorEstatus").setGridParam({page:lastPage});
				    }
				},
				sortorder: "desc"
			}).navGrid('#pagerGridExpedientesPorEstatus',{edit:false,add:false,del:false});

			cargaPrimeraExpPorEstatus = false;
		}else{
			jQuery("#gridExpedientesPorEstatus").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/busquedaExpedientesXEstatus.do?'+ parametros + '',data: parametros,datatype: "xml" });
			$("#gridExpedientesPorEstatus").setGridParam({page:"1"}).trigger("reloadGrid");
			$("#gridExpedientesPorEstatus").trigger("reloadGrid");
		}
	}
	
	
    //Variable para controlar el id de las ventanas
    var idWindowNuevaDenunciaTrad=1;

    /*
    *Abre la ventana para la consulta de la denuncia
    *recibe como parametro el expedienteId
    */
    function consultaDenunciaTradicional(id,estatus,fechaIni,fechaFin,idRol,idConsignador) {

    	var ingresoDenuncia = true;
        var pantallaSolicitada=9;
    	
		var parametros = "";
		
		parametros += 'abrPenal=abrPenal'
		parametros += '&ingresoDenuncia='+ingresoDenuncia;
		parametros += '&idNumeroExpediente='+id;
		parametros += '&pantallaSolicitada='+pantallaSolicitada;
		parametros += '&estatusConsultaExp='+estatus;
		parametros += '&fechaIniConsultaExp='+fechaIni;
		parametros += '&fechaFinConsultaExp='+fechaFin;
		parametros += '&idRolConsultaExp='+idRol;
		parametros += '&idConsignadorConsultaExp='+idConsignador;
		parametros += '&tipoDeConsultaDeExpedientes='+tipoDeConsultaDeExpedientes;


		idWindowNuevaDenunciaTrad++;
	 		
		$.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenunciaTrad, statusBar: true, posx:0,posy:0,width:$(document).width(),height:$(document).height(),title:"Averiguaci&oacute;n previa: ", type:"iframe"});
		$.maximizeWindow("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenunciaTrad);
		$.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenunciaTrad,'<iframe src="<%= request.getContextPath() %>/BusquedaExpedienteSistTrad.do?'+parametros+'" width="100%" height="100%" />');		
	} 

    /*
    *Agrega el numero de expediente al titulo de la ventana hijo (ingresarMenuIntermedioSistTrad.jsp)
    */
    function tituloVentana(numExp){
		$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenunciaTrad+" div.window-titleBar-content").html("Averiguaci&oacute;n Previa:: "+numExp);
	}
    
    
    function asignaValorTipoConsulta(valor){
    	tipoDeConsultaDeExpedientes = valor;
    }
    
	function recargarBadejaIndexConsignador(identificador){
		switch(identificador){
			case 'delDia':
				cargaGridExpedientesPorEstatus("<%=EstatusExpediente.POR_ATENDER.getValorId()%>,<%=EstatusExpediente.EN_PROCESO.getValorId()%>,<%=EstatusExpediente.DEVUELTO.getValorId()%>,<%=EstatusExpediente.NEAP.getValorId()%>,<%=EstatusExpediente.CONSIGNADO.getValorId()%>","<%= fechaActual %>","<%= fechaActual %>",<%=Roles.CONSIGNADOR.getValorId()%>,<%=idFuncionario%>);
				break;
			case 'porAtender':
				cargaGridExpedientesPorEstatus("<%=EstatusExpediente.POR_ATENDER.getValorId()%>","","",<%=Roles.CONSIGNADOR.getValorId()%>,<%=idFuncionario%>)
				break;
			case 'enProceso':
				cargaGridExpedientesPorEstatus("<%=EstatusExpediente.EN_PROCESO.getValorId()%>","","",<%=Roles.CONSIGNADOR.getValorId()%>,<%=idFuncionario%>)
				break;
			case 'devuelto':
				cargaGridExpedientesPorEstatus("<%=EstatusExpediente.DEVUELTO.getValorId()%>","","",<%=Roles.CONSIGNADOR.getValorId()%>,<%=idFuncionario%>)
				break;
			case 'cerrado':
				cargaGridExpedientesPorEstatus("<%=EstatusExpediente.NEAP.getValorId()%>,<%=EstatusExpediente.CONSIGNADO.getValorId()%>","","",<%=Roles.CONSIGNADOR.getValorId()%>,<%=idFuncionario%>);
				break;
		}		
	}
	
		
	/*************************************************************FUNCIONALIDAD DE HERRAMIENTAS****************************************************/
	/*
	*Funcion que ejecuta el chat
	*/
	function chat(){
					
		$("#dialogoChat").dialog({ autoOpen: false, 
			modal: true, 
			title: 'Chat', 
			dialogClass: 'alert',
			modal: true,
			width: 500 ,
			maxWidth: 600,
			buttons: {
				"Cancelar":function() {
						$(this).dialog("close");
					}
				} 
		});	
	}

	/*
	*Funcion que ejecuta el logout de usuario
	*/
	function logout(){
		
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
	}

	//Funcion que permite buscar un numero de expediente dentro de la aplicacion, en base a diferentes criterio
	function buscarExpediente() {
		customVentana("iframewindowBuscarExpediente", "Buscar Expediente", "/buscarExpedienteConSP.do");
	}

	//crea una nueva ventana para la agenda	
	function creaAgenda() {
		$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
	    $.updateWindowContent("iframewindowagenda",'<iframe src="<%= request.getContextPath() %>/InicioAgenda.do" width="1150" height="600" />');		
	    $("#" +"iframewindowagenda"+ " .window-maximizeButton").click();	
	}

	//Funcion que abre la ventana modal para ejecutar el chat
	function ejecutaChat() {
		$("#dialogoChat").dialog( "open" );
	}

	//Funcion para leyes y codigos, abre una ventana
	function visorLeyesCodigos() {
		$.newWindow({id:"iframewindowRestaurativa", statusBar: true, posx:200,posy:50,width:800,height:500,title:"Leyes y C&oacute;digos", type:"iframe"});
	    $.updateWindowContent("iframewindowRestaurativa",'<iframe src="<%= request.getContextPath() %>/detalleLeyesyCodigos.do" width="800" height="500" />');
	}
/********************************************************************FUNCIONALIDAD DE HERRAMIENTAS****************************************************/
	
    
	/* INICIO DE FUNCIONES DE SEGURIDAD */
	function  reiniciar(){
		$.idleTimeout.options.onResume.call($.idleTimeout('#dialogBlok', 'div.ui-dialog-buttonpane button:first'));
		$('#password').val("");
		$('#scaptcha').val("");
		$('#imgcaptcha').hide().attr("src", "<%=request.getContextPath()%>/kaptcha.jpg?<%=new Date().getTime()%>").fadeIn(); 
		
	}
	
	function validaContra(){
		//alert("ejecuta");
		var pass=$('#password').val();
		var capcha=$('#scaptcha').val();
		$.ajax({
	   		type: 'POST',
	    		url: '<%=request.getContextPath()%>/consultarAutenticidad.do',
	    		data: 'password='+pass+'&captcha='+capcha,
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){
	    			var op=$(xml).find('usuarioDTO').find('datosIncorrectos').text();
	    			if(op=="false"){
	    				alert("Los datos son incorrectos","Error");
	    			}else{
	    				$("#dialog-bloqueo").dialog( "close" );
	    				reiniciar();
	    			}
				}
	   	});
	}
	 function bloqueoSesion(){
	    	//crearTimer();
			$( "#dialog-bloqueo" ).dialog({
				resizable: false,
				height:400,
				width:400,
				modal: true,
				closeOnEscape: false,
				buttons: {
					"Aceptar": function() {
						//$( this ).dialog( "close" );
						//$( "#dialog:ui-dialog" ).dialog( "destroy" );
						//cambiarEstausAlarma("aceptar","0",idAlerta,"0");
						validaContra();
						
						
					},
					"Cancelar": function() {
						$( this ).dialog( "close" );
						//$( "#dialog:ui-dialog" ).dialog( "destroy" );
						//cambiarEstausAlarma("cancelar","0",idAlerta,"0");
						document.location.href="<%= request.getContextPath()%>/Logout.do";
					}
				}
			});
			$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
		}
	 
	 
	 /*
		 *Funcion para consultar los roles extras de cada usuario y
		 * construlle el arbol dinamico de los tipos de rol en el menu derecho
		 */
		 
	 /* INICIO DE FUNCIONES DE SEGURIDAD */    
	</script>	
</head>

<body>
<div class="ui-layout-west">

	<div class="header">&nbsp;</div>

	<div class="content">
		<div id="accordionmenuprincipal">
			<h3>
				<a href="#">
					<img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Expedientes Por Estatus
				</a>
			</h3>
			<div>
				<ul id="seccion1tree" class="filetree">	
					<li class="closed"><span id="deldia" class="folder" onclick='cargaGridExpedientesPorEstatus("<%=EstatusExpediente.POR_ATENDER.getValorId()%>,<%=EstatusExpediente.EN_PROCESO.getValorId()%>,<%=EstatusExpediente.DEVUELTO.getValorId()%>,<%=EstatusExpediente.NEAP.getValorId()%>,<%=EstatusExpediente.CONSIGNADO.getValorId()%>","<%= fechaActual %>","<%= fechaActual %>",<%=Roles.CONSIGNADOR.getValorId()%>,<%=idFuncionario%>);asignaValorTipoConsulta("delDia")'>
						Del d&iacute;a</span>
					</li>
				</ul>
				
				<ul id="seccion2tree" class="filetree">	
					<li class="closed" ><span id="nuevos" class="folder" onclick='cargaGridExpedientesPorEstatus("<%=EstatusExpediente.POR_ATENDER.getValorId()%>","","",<%=Roles.CONSIGNADOR.getValorId()%>,<%=idFuncionario%>);asignaValorTipoConsulta("porAtender")'>
					Por atender</span>
					</li>
				</ul>	
				
				<ul id="seccion3tree" class="filetree">	
					<li class="closed" ><span id="enproceso" class="folder" onclick='cargaGridExpedientesPorEstatus("<%=EstatusExpediente.EN_PROCESO.getValorId()%>","","",<%=Roles.CONSIGNADOR.getValorId()%>,<%=idFuncionario%>);asignaValorTipoConsulta("enProceso")'>
					En proceso</span>
					</li>
				</ul>	
							
				<ul id="seccion4tree" class="filetree">	
					<li class="closed" ><span id="enproceso" class="folder" onclick='cargaGridExpedientesPorEstatus("<%=EstatusExpediente.DEVUELTO.getValorId()%>","","",<%=Roles.CONSIGNADOR.getValorId()%>,<%=idFuncionario%>);asignaValorTipoConsulta("devuelto")'>
					Devueltos</span>
					</li>
				</ul>		
				<ul id="seccion5tree" class="filetree">	
					<li class="closed" ><span id="cerrados" class="folder" onclick='cargaGridExpedientesPorEstatus("<%=EstatusExpediente.NEAP.getValorId()%>,<%=EstatusExpediente.CONSIGNADO.getValorId()%>","","",<%=Roles.CONSIGNADOR.getValorId()%>,<%=idFuncionario%>);asignaValorTipoConsulta("cerrado")'>
					Cerrados</span>
					</li>
				</ul>	
			</div>
		</div>
	</div>
	<!-- div class="footer">&nbsp;</div-->
</div>

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
<!--			<h1><a href="#">Clima</a></h1>-->
<!--				<div align="left">-->
<!--					<div align="left" id="test"></div>-->
<!--				</div>-->
			<h1><a href="#" onclick="consultarTiposRol();">Facultades</a></h1>
				<div>
					<table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="0"  style="cursor:pointer" id="tableRolMenu">
					</table>
					<form name="frmRol2" action="<%= request.getContextPath() %>/rolRedirec.do" method="post">
						<input type="hidden" name="rolname" />
					</form>
				</div>
			<div></div>
		</div>
	</div>
	<!-- div class="footer">&nbsp;</div -->
</div>


<div class="ui-layout-north">
	<div class="content">
			<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" background=<%=request.getContextPath()%>/resources/images/top_gral_sin_version.jpg height="100%">
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
	<ul class="toolbar ui-widget-header">
		<div id="menu_head">
			<li id="tbarBtnHeaderZise" class="first"><span></span></li>
		</div>
		<div id="menu_config">
			<li onclick="buscarExpediente();">Buscar Expediente&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_busca3.png" width="15" height="16"></li>
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
			<div class="ui-layout-north">

				<div id="divGridExpedientePorEstatus">
						<table id="gridExpedientesPorEstatus"></table>
						<div id="pagerGridExpedientesPorEstatus" style=" vertical-align: top;"></div>
				</div>	
				
			</div>		
		</div>
	</div>
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
	<div id="dialogBlok" title="Su sesi&oacute;n est&aacute; a punto de caducar!">
			<p>
				<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 50px 0;"></span>
				La sesi&oacute;n se cerrar&aacute; en <span id="dialog-countdown" style="font-weight:bold"></span> segundos.
			</p>

			<p>&iquest;Desea continuar con la sesi&oacute;n?</p>
	</div>
	
</body>

</html>