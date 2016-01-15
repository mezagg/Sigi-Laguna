<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<% 
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	RolDTO rolDTO = usuario.getRolACtivo().getRol();
 %>
<html>
<head>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas" %>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud" %>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes" %>
	
	<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />				
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>  
	<!--link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" /-->	 	

<style>
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
</style>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas"%> 
	
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
	<!--script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.zweatherfeed.js"></script-->
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>			
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/sesion.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
        
        <!-- Enable JC Funciones comunes para UAVD -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/jsEnableIT/comunesUAVD.js"></script>
        
	<script type="text/javascript">

	var contextoPagina = "${pageContext.request.contextPath}";
	var sesionActiva = '<%= (request.getSession().getAttribute(LoginAction.KEY_SESSION_USUARIO_FIRMADO)!=null)%>';
	var tiempoActiva = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoBloqueoSesion()%>';
	estaSesionActiva();
	
	var outerLayout, innerLayout;
	
	var idsTiposSolicitudes="";
		
	$(document).ready(function() {
	
		jQuery(document).ajaxStop(jQuery.unblockUI);
		$("#anio1").css({ color: "#1C94C4"});
		$("#abiertos-anio1").css({ color: "#1C94C4"});
		$("#cerrados-anio1").css({ color: "#1C94C4"});
		
		$("#anio2").css({ color: "#1C94C4"});
		$("#abiertos-anio2").css({ color: "#1C94C4"});
		$("#cerrados-anio2").css({ color: "#1C94C4"});
		
		$("#elaborar-solicitud").css({ color: "#1C94C4"});
		$("#seguimiento-solicitud").css({ color: "#1C94C4"});
		
		
		
		//obtenemos el tiempo de las alarmas y ponemos en marcha el timer		
		var tiempo='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoRevisionAlarmas()%>';
		setInterval(muestraAlerta, tiempo);
		
		//Mandamos consultar los tipos de solicitud dependiendo del Area del Funcionario
		consultarTiposSolicitudPorAreaDelFuncionario('tableSolsXAtender', <%= Areas.ATENCION_JURIDICA.parseLong() %>);
		
		outerLayout = $("body").layout( layoutSettings_Outer );

		$("#accordionmenuprincipal").accordion({  fillSpace: true });
		$("#accordionmenuderprincipal").accordion({ fillSpace: true});
		$("#accordionmenuderprincipal").accordion( "option", "icons", null );

		$("#seccion1tree").treeview();
		$("#seccion2tree").treeview();
		$("#seccion3tree").treeview();
		$("#seccion4tree").treeview();
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
		
		
		muestraGadgets();
		//alert(idsTiposSolicitudes);
		jQuery("#gridSolsXAtndr").jqGrid({ 
				url:'<%=request.getContextPath()%>/BusquedaInicialExpPsicologicosUAVDGrid.do?idArea='+<%= Areas.COORDINACION_ATENCION_VICTIMAS.parseLong() %>+'&estatus='+<%=EstatusSolicitud.EN_PROCESO.getValorId()%>+'&tipoSoliciutd='+<%=TiposSolicitudes.ATENCION_JURIDICA.getValorId()%>+'&cadenaArea=ATNJUR', 
				datatype: "xml", 
				colNames:['Expediente','Fecha', 'V&iacute;ctima','Delito'], 
				colModel:[ 	{name:'Expediente',index:'1', width:300, align:'center'}, 
							{name:'Fecha',index:'2', width:70, align:'center'},
							{name:'Denunciante',index:'3', width:250, align:'center'}, 
							{name:'Delito',index:'4', width:150, align:'center'}
						],
				pager: jQuery('#pagerGridSolsXAtndr'),
				rowNum:10,
				rowList:[10,20,30,40,50,60],
				autoheight: true,
				autowidth: true,
				sortname: 'turno',
				viewrecords: true,
				id: 'pager1',
				onSelectRow: function(id){
					registraDatosPersona(id,0);
					},
				sortorder: "desc"
			}).navGrid('#pagerGridSolsXAtndr',{edit:false,add:false,del:false});		
		$("#gview_gridSolsXAtndr .ui-jqgrid-bdiv").css('height', '410px');
							
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
		cargaOcupacion();
		//cargaCasos();
		$( "#tabsprincipalconsulta" ).tabs();
			
			$("#exp01").click(creaExpediente01);
			$("#exp02").click(creaExpediente02);			
			$("#controlAgenda").click(creaAgenda);
				
			//$("#buscarExpediente").click(buscarExpediente);
			//$('#casos li').click(agregaExpediente);		
			//$("#buscarCaso").click(buscarCaso);	
			//$("#tbarBtnNuvaDenuncia").click(nuevaDenuncia);	
			//$('#casos1 li').click(justiciaRestaurativa);	
			//$("#generarDocumento").click(generarDocumentoViwe);
			//$("#entrevista").click(generaCapturaEntrevista);
				
		
		//agregamos el click para redireccionar a la valoracion de hechos
		$("#hrefHechos").click(realizarValoracionHechos);	

		$("#exp1").click(seleccionFila1);
		$("#exp2").click(seleccionFila2);
		$("#exp3").click(seleccionFila3);
		$("#exp4").click(seleccionFila4);
		//$('#test').weatherfeed(['MXDF0132']);

		var ambiente='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAmbiente()%>';
		if(ambiente == undefined || ambiente == "undefined"){
			ambiente = "";
		}
		$("#ambienteLb").html('<strong><big>'+ambiente+'</big></strong>');
                
                //Enable JC Clic sobre el menu Expedientes Compartidos
		$("#expCompartidos").click(activaSolicitudesCompartidas);
		$("#expPropios").click(function (){
				cargaGridSolsXAtndr(<%=TiposSolicitudes.ATENCION_JURIDICA.getValorId()%>,<%= Areas.COORDINACION_ATENCION_VICTIMAS.parseLong() %>);
			}
		);
		//ENABLE JC GRID Solicitudes Compartidas
		jQuery("#gridSolicitudesCompartidas").jqGrid({
			url:'<%=request.getContextPath()%>/CargarGridMenuSolicitudesCompartidas.do',
			datatype: "xml",
			colNames:['Expediente','Fecha', 'V&iacute;ctima','Delito'],
			colModel:[ 	{name:'Expediente',index:'1', width:300, align:'center'},
						{name:'Fecha',index:'2', width:70, align:'center'},
						{name:'Denunciante',index:'3', width:250, align:'center'},
						{name:'Delito',index:'4', width:150, align:'center'}
					],
			pager: jQuery('#pagerSolicitudesCompartidas'),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autoheight: true,
			autowidth: true,
			sortname: '1',
			viewrecords: true,
			ondblClickRow: function(rowid) {
				registraDatosPersona(rowid,<%=TiposSolicitudes.ATENCION_JURIDICA.getValorId()%>);
			},
			sortorder: "desc"
		}).navGrid('#pagerSolicitudesCompartidas',{edit:false,add:false,del:false});
		$("#gview_gridSolicitudesCompartidas .ui-jqgrid-bdiv").css('height', '410px');
		//FIN GRID Solicitudes Compartidas
	});

		function seleccionFila1(){
			$("#1").css({ color: "#FFFFFF", background: "#FF0000" });
			$("#2").css({ color: "#FF0000", background: "#FFFFFF" });
			$("#3").css({ color: "#FF0000", background: "#FFFFFF" });
			$("#4").css({ color: "#FF0000", background: "#FFFFFF" });   
		}

		function seleccionFila2(){
			$("#2").css({ color: "#FFFFFF", background: "#FF0000" });
			$("#1").css({ color: "#FF0000", background: "#FFFFFF" });
			$("#3").css({ color: "#FF0000", background: "#FFFFFF" });
			$("#4").css({ color: "#FF0000", background: "#FFFFFF" });   
		}

		function seleccionFila3(){
			$("#3").css({ color: "#FFFFFF", background: "#FF0000" });
			$("#1").css({ color: "#FF0000", background: "#FFFFFF" });
			$("#2").css({ color: "#FF0000", background: "#FFFFFF" });
			$("#4").css({ color: "#FF0000", background: "#FFFFFF" });   
		}

		function seleccionFila4(){
			$("#4").css({ color: "#FFFFFF", background: "#FF0000" });
			$("#1").css({ color: "#FF0000", background: "#FFFFFF" });
			$("#2").css({ color: "#FF0000", background: "#FFFFFF" });
			$("#3").css({ color: "#FF0000", background: "#FFFFFF" });   
		}

		function verExpediente(idExpediente, numeroExpediente) {
			$.newWindow({id:"iframewindowExp" + idExpediente, statusBar: true, posx:200,posy:50,width:1140,height:400,title:numeroExpediente, type:"iframe"});
		    $.updateWindowContent("iframewindowExp" + idExpediente,'<iframe src="<%= request.getContextPath() %>/IngresarMenuIntermedio.do" width="1140" height="400" />');			
		}
				
//		function buscarExpediente() {
//			$.newWindow({id:"iframewindowBuscarExpediente", statusBar: true, posx:255,posy:110,width:653,height:400,title:"Buscar Expediente", type:"iframe"});
//	    	$.updateWindowContent("iframewindowBuscarExpediente",'<iframe src="<%= request.getContextPath() %>/buscarExpediente.do" width="653" height="400" />');		
//		}

//		function buscarCaso() {
//			$.newWindow({id:"iframewindowBuscarCaso", statusBar: true, posx:255,posy:110,width:653,height:400,title:"Buscar Caso", type:"iframe"});
//	    	$.updateWindowContent("iframewindowBuscarCaso",'<iframe src="<%= request.getContextPath() %>/buscarCaso.do" width="653" height="400" />');		
//		}

		function ejecutaChat() {
			$("#dialogoChat").dialog( "open" );
		}
		
		function creaExpediente01() {
			$.newWindow({id:"iframewindowExp01", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"EXP 00000001", type:"iframe"});
		    $.updateWindowContent("iframewindowExp01",'<iframe src="<%= request.getContextPath() %>/IngresarMenuIntermedio.do" width="1140" height="400" />');		
		}
		
		function creaExpediente02() {
			$.newWindow({id:"iframewindowExp02", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"EXP 00000002", type:"iframe"});
		    $.updateWindowContent("iframewindowExp02",'<iframe src="<%= request.getContextPath() %>/IngresarMenuIntermedio.do" width="1140" height="400" />');		
		}
	
		function creaAgenda() {
			$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
		    $.updateWindowContent("iframewindowagenda",'<iframe src="<%= request.getContextPath() %>/InicioAgenda.do" width="1150" height="600" />');		
		}

//		function nuevaDenuncia() {
//			$.newWindow({id:"iframewindowNuevaDenuncia", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Nueva Denuncia", type:"iframe"});
//		    $.updateWindowContent("iframewindowNuevaDenuncia",'<iframe src="<%= request.getContextPath() %>/IngresarMenuIntermedio.do?idNuevaDenuncia=1" width="1140" height="400" />');		
//		}
		
		 
		function justiciaRestaurativa() {
			$.newWindow({id:"iframewindowjusticia", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Justicia Restaurativa", type:"iframe"});
		    $.updateWindowContent("iframewindowjusticia",'<iframe src="<%= request.getContextPath() %>/JusticiaRestaurativa.do" width="1140" height="400" />');		
		}

		function generarDocumentoViwe() {
			$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:255,posy:111,width:1024,height:471,title:"Generar Documento", type:"iframe"});
		    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumento.do" width="1140" height="400" />');
		    		
		}
		
		
		
		
		
		function cargaOcupacion(){
		    	//alert("cargaOcupacion");
		    	$.ajax({
		    		type: 'POST',
		    		url: '<%= request.getContextPath()%>/ConsultarCatalogoOcupacion.do',
		    		data: '',
		    		dataType: 'xml',
		    		async: false,
		    		success: function(xml){
		    			var option;
		    			$(xml).find('ocupacion').each(function(){
		    			$('#consultaVictima').append('<li value="' + $(this).find('gcNombre').text() +  '" title="'+ $(this).find('gcDescripcion').text() + '"  style="background:#99C"  >'+ $(this).find('gcDescripcion').text() + '</li>');
		    			});
		    		}
		    		
		    	});
		    }

		var casoAbierto = Array();
		
		function agregaExpediente (idCaso) {	
			//alert("agregarExpediente:" + idCaso)	
			if (casoAbierto[idCaso ] != true) {
				$.ajax({
		    		type: 'POST',
		    		url: '<%= request.getContextPath()%>/BusquedaCaso.do',
		    		data: 'idCaso=' + idCaso,
		    		dataType: 'xml',
		    		async: true,
		    		success: function(xml){
		    			$(xml).find('expediente').each(function(){
		    				var branches = $("<ul><li id='" + $(this).find('expedienteId').text() + "EXP' onclick='verExpediente(" + $(this).find('expedienteId').text() + ",\"" + $(this).find('numeroExpediente').text() + "\")'><span class='file'>" + $(this).find('numeroExpediente').text() + "</span><ul></ul></li></ul>").appendTo("#" + idCaso + "CASO");
		    				$("#" + idCaso + "CASO").treeview({
		    					add: branches
		    				});
			    		});
		    		}		    		
		    	});
			}
			casoAbierto[idCaso] = true;
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
		,	onresize_end:			function () { $("#gridSolsXAtndr").setGridWidth($("#mainContent").width() - 5, true); }		
		}
	};
	
	/*
	*Listener del click para la redireccion a la valoracion de hechos
	*/
	function realizarValoracionHechos()
	{
		location.href='<%= request.getContextPath()%>/RealizarValoracionHechos.do';
	}

	 function registraDatosPersona(idrow, tipoSolCompartida) {
		 	//el idrow es un id compuesto: idSolicitud,ExpedienteId,NumeroExpediente,NumeroExpedienteId
		 	id=1;
		 	var pantalla=1;
		 	var idsNecesarios=idrow.split(",");
		 	
			var idSolicitud=idsNecesarios[0];
			var idNumeroExpediente=idsNecesarios[3];
			var numeroExpediente=idsNecesarios[2];
			var numeroExpedienteId=idsNecesarios[1];
		 	
			$.newWindow({id:"iframewindowRegistraDatosPersona", statusBar: true, posx:255,posy:111,width:911,height:465,title:"Expediente: "+ numeroExpediente, type:"iframe"});
		    //$.updateWindowContent("iframewindowRegistraDatosPersona",'<iframe src="<%= request.getContextPath() %>/RegistraDatosPersonaUAVD.do?idCompuesto='+idrow+'&idDatoPersona='+id +'&pantalla='+pantalla+'&idNumeroExpediente='+idNumeroExpediente+'" width="1140" height="400" />');
                    $.updateWindowContent("iframewindowRegistraDatosPersona",'<iframe src="<%= request.getContextPath() %>/RegistraDatosPersonaUAVD.do?idCompuesto='+idrow+'&idDatoPersona='+id +'&pantalla='+pantalla+'&idNumeroExpediente='+idNumeroExpediente+'&idSolicitud='+idSolicitud+'&tipoSolCompartida='+tipoSolCompartida+'" width="1140" height="400" />');
		    $("#" +"iframewindowRegistraDatosPersona" +" .window-maximizeButton").click();
			}
	
		/*
		 *Funcion para consultar los tipos de solicitud y generar 
		 * el arbol dinamico de los tipos de solicitud en el menu izquierdo
		 * param - nombre del elemento en el que se construira de manera dinamica
		 * los tipos de solicutd
		 */
		function consultarTiposSolicitudPorAreaDelFuncionario(idDivArbol,idArea)
		{
			//limpiamos el menu de los tipos de solicitud
			$("#"+idDivArbol).empty();
			//lanzamos la consulta del tipo de solicitudes
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultaTiposSolsXArea.do',
				data: 'idArea='+idArea,
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('ValorDTO').each(function(){
						var trTabla = '<tr>';
						trTabla = trTabla + '<td><span><img src="<%=request.getContextPath()%>/resources/css/treeview/images/folder-closed.gif" width="16" height="16"/><a onclick="cargaGridSolsXAtndr('+$(this).find("idCampo").text()+','+idArea+')">'+$(this).find("valor").text()+'</a></span></td>';
						trTabla = trTabla + '</tr>';
						$('#'+idDivArbol).append(trTabla);
						//cosntruyo la cadena con los ids de los tipos de solicitud
						if(idsTiposSolicitudes.length==0)
						{
							idsTiposSolicitudes+=$(this).find("idCampo").text();
						}
						else
						{
							idsTiposSolicitudes+=','+$(this).find("idCampo").text();
						}
					});
				}
				
			});
		}
		
		/*
		*Funcion para realizar la consulta del grid de solicitudes por Atender
		*/
		function cargaGridSolsXAtndr(tipoSolicitud,idArea)
		{
//			jQuery("#gridSolsXAtndr").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultaSolsXAtnderUAVD.do?tipoSoliciutd='+tipoSolicitud+'&idArea='+idArea+'&estatus=<%=EstatusSolicitud.EN_PROCESO.getValorId()%>',datatype: "xml" });
                        jQuery("#gridSolsXAtndr").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/BusquedaInicialExpPsicologicosUAVDGrid.do?idArea='+<%= Areas.COORDINACION_ATENCION_VICTIMAS.parseLong() %>+'&estatus='+<%=EstatusSolicitud.EN_PROCESO.getValorId()%>+'&tipoSoliciutd='+<%=TiposSolicitudes.ATENCION_JURIDICA.getValorId()%>+'&cadenaArea=ATNJUR',datatype: "xml" });
			$("#gridSolsXAtndr").trigger("reloadGrid");
			ocultaMuestraGrids("gridSolsXAtndr");
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
	function consultarTiposRol()
	{
		//limpiamos el menu de los tipos de solicitud
		$("#tableRolMenu").empty();
		//lanzamos la consulta del tipo de solicitudes
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultaMenuRol.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('RolDTO').each(function(){
					var rolnuevo=$(this).find("nombreRol").text();
					var rolDesc=$(this).find("descripcionRol").text();
					var trTabla = "<tr>";
					trTabla = trTabla + "<td><span><img src='<%=request.getContextPath()%>/resources/css/check.png' width='16' height='16' />"+
					 					"<a  onclick=\"cargaRolNuevo('"+rolnuevo+"');\">" + rolDesc +
					 					"</a></span></td>";
					trTabla = trTabla + "</tr>";
					$('#tableRolMenu').append(trTabla);
				});
			}

		});
	}

	function cargaRolNuevo(rolNuevo){
		///rolRedirec
		//alert(rolNuevo);
		document.frmRol2.rolname.value = rolNuevo;
		document.frmRol2.submit();

	}

	//************************
	//Enable JC Abre ventana para asignar permisos
	//************************


	/*
	* Enable JC Funcion para recargar el grid de expedientes compartidos desde el arbol izquierdo
	*/
	function activaSolicitudesCompartidas()
	{
		jQuery("#gridSolicitudesCompartidas").jqGrid('setGridParam',
				{url:'<%=request.getContextPath()%>/CargarGridMenuSolicitudesCompartidas.do',
				datatype: "xml" });
			 $("#gridSolicitudesCompartidas").trigger("reloadGrid");
			 ocultaMuestraGrids("expCompartidos");
			$("#gridSolicitudesCompartidas").setGridWidth($("#mainContent").width() - 5, true);
			$("#gview_gridSolicitudesCompartidas .ui-jqgrid-bdiv").css('height', '410px');
			$("#gview_gridSolicitudesCompartidas .ui-jqgrid-bdiv").css('width', '900px');
	}

	//Enable JC Intercambia el grid de solicitudes y expedientes compartidos.
	function ocultaMuestraGrids(idDivGrid)
	{
		if(idDivGrid == "expCompartidos"){
			$("#divGridSolsXAtndr").hide();
			$("#divGridSolicitudesCompartidas").show();
		}else if(idDivGrid == "gridSolsXAtndr"){
			$("#divGridSolsXAtndr").show();
			$("#divGridSolicitudesCompartidas").hide();
		}
	}

	/**
	 * Enable JC Abre ventana para asignar permisos
	 */
	var iframewindowAPSE = 0;
	function asignarPermisos(){
		$.newWindow({id:"iframewindowAPSE"+iframewindowAPSE, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Asignar permisos sobre Expediente: ", type:"iframe"});
		$.updateWindowContent("iframewindowAPSE"+iframewindowAPSE,'<iframe src="<%=request.getContextPath()%>/asigarPermisosExpediente.do?idsTiposSolicitudes='+idsTiposSolicitudes+'" width="1430" height="670" />');
		$("#" +"iframewindowAPSE"+iframewindowAPSE + " .window-maximizeButton").click();
		iframewindowAPSE++;
	}	
	</script>	
</head>

<body>
<div class="ui-layout-west">

	<div class="header">&nbsp;</div>

	<div class="content">
		<div id="accordionmenuprincipal">
			<h3 id="expPropios"><a href="#">&nbsp;Expedientes</a></h3>
			<div>
			</div>
                        <!--Enable JC AGREGAR EL FILTRO CORRESPONDIENTE PARA EXPEDIENTES COMPARTIDOS-->
			<h3><a href="#"><span>Otros Expedientes</span></a></h3>
			<div>
		        <ul id="tableExpPropios" style="cursor:pointer" class="filetree">
					<li class="filetree">
						<span id="expCompartidos" >Expedientes Compartidos</span>
					</li>
				</ul>
		 	</div>
			<!-- <h3><a href="#">Solicitudes por Atender</a></h3>
			<div>
				<table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="0" bgcolor="#EEEEEE" style="cursor:pointer" id="tableSolsXAtender">
				</table>
			</div> -->
<!--			<h3><a href="#"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Expedientes</a></h3>-->
<!--			<div>			-->
<!--				<ul id="seccion1tree" class="filetree">-->
<!--					<li class="closed"><span id="anio1" class="folder">2010</span>-->
<!--						<ul>-->
<!--							<li class="closed" id="abiertos-anio1"><span class="folder">Abiertos</span>-->
<!--								<ul>-->
<!--								-->
<!--								</ul>-->
<!--							</li>-->
<!--						</ul>-->
<!--						<ul>-->
<!--							<li class="closed" id="cerrados-anio1"><span class="folder">Cerrados</span>-->
<!--								<ul>-->
<!--									-->
<!--								</ul>-->
<!--							</li>-->
<!--						</ul>-->
<!--					</li>-->
<!--					<li class="closed"><span id="anio2" class="folder">2011</span>-->
<!--						<ul>-->
<!--							<li class="closed" id="abiertos-anio2"><span class="folder">Abiertos</span>-->
<!--								<ul>-->
<!--									-->
<!--								</ul>-->
<!--							</li>-->
<!--						</ul>-->
<!--						<ul>-->
<!--							<li class="closed" id="cerrados-anio2"><span class="folder">Cerrados</span>-->
<!--								<ul>-->
<!--									-->
<!--								</ul>-->
<!--							</li>-->
<!--						</ul>-->
<!--					</li>-->
<!--				</ul>		-->
<!--			</div>-->
		</div>
	</div>
	<!-- div class="footer">&nbsp;</div-->
</div>

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
<!--			<li id="tbarBtnNuevo" class="first"><span></span>Nuevo</li>-->
<!--			<li id="tbarBtnLectura"><span></span>Lectura</li>-->
<!--			<li id="tbarBtnImpresoras"><span></span>Impresoras</li>-->
<!--			<li id="tbarBtnNuvaDenuncia"><span></span>Nueva Denuncia</li>-->
<!--			<li id="buscarExpediente" ><span></span>Buscar Expediente</li>-->
<!--			<li id="buscarCaso" ><span></span>Buscar Caso</li>
				<li id="EnviarNotif" ><span></span>Enviar Notificaciones&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_errorinf.png" width="15" height="16"></li>
				<li id="Ayuda" ><span></span>Ayuda&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_help4.png" width="15" height="16"></li>
		--></div>
		<div id="menu_config">
                    <li id="tbarBtnAsignarPermisosASubordinados" class="pen" onclick="asignarPermisos();">Asignar Permisos a Subordinados</li>
			<!--<li id="generarDocumento">Generar Documento&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_dctowri.png" width="15" height="16"></li>
			<li>Guardar&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_save_ch.png" width="15" height="16"></li>
			<li>Digitalizar Documentos&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_scan.png" width="15" height="15"></li>
			<li>config01</li>
			<li>config02</li>
			<li>config03</li>
			<li class="last">config04</li>-->
<!--			<li id="verde">Configuraci&oacute;n&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_config.png" width="15" height="16"></li>-->
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
		<div id="pie" align="center"
			style="BACKGROUND-COLOR: #e7eaeb; BACKGROUND-POSITION: center top; COLOR: #58595b">
			<div id="footer"
				style="PADDING-BOTTOM: 5px; PADDING-LEFT: 5px; WIDTH: 720px; PADDING-RIGHT: 5px; PADDING-TOP: 5px">
				Direcci&oacute;n de la Instituci&oacute;n
			</div>
		</div>
	</div>


		<div id="mainContent">
		<div class="ui-layout-center">
		<div class="ui-layout-content">
		<div id="divGridSolsXAtndr" class="ui-layout-north">
			<table id="gridSolsXAtndr"></table>
			<div id="pagerGridSolsXAtndr"></div>
		</div>
                    <!--Enable JC GRID Expedientes compartidos -->
			<div id="divGridSolicitudesCompartidas" style="display: none;">
			 	<table id="gridSolicitudesCompartidas"></table>
				<div id="pagerSolicitudesCompartidas"></div>
			</div>
		</div>
		</div>
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
