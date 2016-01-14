<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas" %>
<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<% 
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	RolDTO rolDTO = usuario.getRolACtivo().getRol();
 %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<!--link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" /-->	 	
		
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimeout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/timer/jquery.idletimer.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/reloj.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
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

		var outerLayout, innerLayout;
		var tipoResolutivo;
		var formaId;
		var idWindowNuevaDenuncia=1;

		var idWindowMostrarRegistroDetencion=1;
		
		var primeraVezGridAvisos=true;

		//Variable para controlar el grid que se carga en el onready de la pagina
		var primeraVezGridInformePolicial=true;

		/**Variables para la ceja solicitudes**/
		var primeraVezGridSolicitudesDeTraslado=true;
		
		var primeraVezGridRegistroDeDetencion=true;

		var estatusAvisoAuxilio;
		
		$(document).ready(function() {
			jQuery(document).ajaxStop(jQuery.unblockUI);
			estilosMenu('procuracionJusticia');
			
			//cargamos la imagen dependiendo del usuario que llama la ppagina
			var idTipoUsuario='<%= request.getParameter("idUSer")%>';
			if(parseInt(idTipoUsuario)==1)
			{
				//imagen de sspPolicia
				$('#logoPagina').attr('src','<%=request.getContextPath()%>/resources/images/logo_segpub.png');
				$("#mandamientosJudicialesSSPPM").css("display","none");
						}
			
			else
			{
				//policiaMinister
				$('#logoPagina').attr('src','<%=request.getContextPath()%>/resources/images/logo_segpubPolMin.png');
				$("#apoyoFiscaliaSSPPolicia,#ConsyMediacionSSPPolicia,#quejaCiudadanaSSPPolicia").css("display","none");
				
				
				}
			
			//obtenemos el tiempo de las alarmas y ponemos en marcha el timer		
			var tiempo='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoRevisionAlarmas()%>';
			setInterval(muestraAlerta, tiempo);
			
			outerLayout = $("body").layout( layoutSettings_Outer );
			//llenamos el arbol de las solicitudes por atender del menu ixquierdo
			consultarTiposSolicitudPorAreaDelFuncionario('tableSolsXAtender',"0");
			
			$("#accordionmenuprincipal").accordion({  fillSpace: true });
			$("#accordionmenuderprincipal").accordion({ fillSpace: true});
			$("#accordionmenuderprincipal").accordion( "option", "icons", null );
			$("#detencion").click(cargaGridInformePolicial);
			$("#ingresoCedepro").click(muestraIngresoCedepro);
			$("#solicitarDefensor").click(muestraSolicitarDefensor);
			//$("#dictamenPericial").click(muestraDictamenPericial);			
			$("#controlAgenda").click(creaAgenda);
			$("#solicitudes").click(cargaGridSolicitud);
			//$("#procuracionJusticia").click(ocultaGridSolicitud);
			$('#divGridSolicitudes').hide();
			$("#divGridSolsXAtndr").hide();
			//Ocultanos la ceja del acordeon derecho Teporalmente
			//$('#conciliacionMedicion').hide();
			$("#tbarBtnQuejaCiudadana").click(muestraQuejaCiudadana);
			$("#tbarBtnVerGrafica").click(muestraVerGrafica);
			$('#imageViewer').click(generaVisorGraficaView);
			outerLayout.addToggleBtn( "#tbarBtnHeaderZise", "north" );
			var westSelector = "body > .ui-layout-west"; // outer-west pane
			var eastSelector = "body > .ui-layout-east"; // outer-east pane
			$("<span></span>").addClass("pin-button").prependTo( westSelector );
			$("<span></span>").addClass("pin-button").prependTo( eastSelector );
			outerLayout.addPinBtn( westSelector +" .pin-button", "west");
			outerLayout.addPinBtn( eastSelector +" .pin-button", "east" );
			$("<span></span>").attr("id", "west-closer" ).prependTo( westSelector );
			$("<span></span>").attr("id", "east-closer").prependTo( eastSelector );
			outerLayout.addCloseBtn("#west-closer", "west");
			outerLayout.addCloseBtn("#east-closer", "east");
			createInnerLayout () ;
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
			
			//Grid de Solicitudes por atender
			jQuery("#gridSolsXAtndr").jqGrid({ 
				//url:'local', 
				datatype: "xml", 
				colNames:['No. Caso','No. Expediente', 'Folio','Estatus','Fecha Creaci&oacute;n','Fecha Limite','Instituci&oacute;n','Remitente','IdExpediente'], 
				colModel:[ 	{name:'caso',index:'caso', width:150},
				           	{name:'expediente',index:'expediente', width:130}, 
							{name:'folio',index:'folio', width:100}, 
							{name:'estatus',index:'estatus', width:100}, 
							{name:'fechaCreacion',index:'fechaCreacion', width:100},
							{name:'fechaLimite',index:'fechaLimite', width:100},
							{name:'institucion',index:'institucion', width:100},
							{name:'remitente',index:'remitente', width:200},
							{name:'idExpediente',index:'idexpediente', width:200,hidden:true}
						],
				pager: jQuery('#pagerGridSolsXAtndr'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					nuevoNumeroExpediente(rowid);
						}
			}).navGrid('#pagerGridSolsXAtndr',{edit:false,add:false,del:false});	
			$("#gview_gridSolsXAtndr .ui-jqgrid-bdiv").css('height', '400px');
			
			//carga del grid por default
			//cargaGridInformePolicial();	
			cargaGridAvisosAuxilio(<%=EstatusNotificacion.NO_ATENDIDA.getValorId()%>);
			
			//$('#test').weatherfeed(['MXDF0132']);

			//crea el arbol para traslados
			$("#seccion6treePJENC,#apoyoFiscaliaSSPPolicia,#seccion8treePJENC,#seccion9treePJENC").treeview();
			$("#seccion5tree").treeview();

			var ambiente='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAmbiente()%>';
			if(ambiente == undefined || ambiente == "undefined"){
				ambiente = "";
			}
			$("#ambienteLb").html('<strong><big>'+ambiente+'</big></strong>');
		});
		//FIN READY
		
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
					});
				}
				
			});
		}
		
		/*
		*Funcion para realizar la consulta del grid de solicitudes por Atender
		*/
		function cargaGridSolsXAtndr(tipoSolicitud,idArea)
		{
			jQuery("#gridSolsXAtndr").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultaSolsXAtnder.do?tipoSoliciutd='+tipoSolicitud+'&idArea='+idArea+'&estatus=<%=EstatusSolicitud.ABIERTA.getValorId()%>,<%=EstatusSolicitud.EN_PROCESO.getValorId()%>',datatype: "xml" });
			$("#gridSolsXAtndr").trigger("reloadGrid");
			muestraOcultaGrids("gridSolsXAtndr");
		}

		function creaAgenda() {
			$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
		    $.updateWindowContent("iframewindowagenda",'<iframe src="<%=request.getContextPath()%>/InicioAgenda.do" width="1150" height="600" />');		
		}

		function ejecutaChat() {
			$("#dialogoChat").dialog( "open" );
		}
		
		function administrarResolutivosImputado(){
			
			if(tipoResolutivo == "Prisi&oacute;n Preventiva"){

				$.newWindow({id:"iframewindowAdministrarResolutivosImputado", statusBar: true, posx:200,posy:50,width:840,height:450,title:"Administrar Resolutivos Imputado", type:"iframe"});
			    $.updateWindowContent("iframewindowAdministrarResolutivosImputado",'<iframe src="<%=request.getContextPath()%>/visorAdministrarResolutivosImputado.do" width="840" height="450" />'); 
					  	  			  	  		
	  	  		}else{				
	  	  		
		  	  	$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Resolutivo", type:"iframe"});
			    $.updateWindowContent("iframewindowGenerarDocumento","<iframe src='<%= request.getContextPath() %>/enviaResolutivo.do?esconderArbol=1&formaId=18' width='1140' height='400' />");
	  	  		
  	  	  		}  		
			
			}

		function muestraDetencion(folioIPH){
			$.newWindow({id:"iframewindowDetencion", statusBar: true, posx:258,posy:139,width:927,height:530,title:"Detenci&oacute;n", type:"iframe"});
		    $.updateWindowContent("iframewindowDetencion",'<iframe src="<%=request.getContextPath()%>/administrarDetencionDePersona.jsp?folioIPH='+folioIPH+'" width="927" height="530" />'); 
		}

		function muestraIngresoCedepro(){
			$.newWindow({id:"iframewindowIngresoCedepro", statusBar: true, posx:255,posy:112,width:850,height:350,title:"Ingreso a CEDEPRO", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresoCedepro",'<iframe src="<%=request.getContextPath()%>/ingresoCedepro.jsp" width="850" height="350" />'); 
		}
	
		function muestraSolicitarDefensor(){
			$.newWindow({id:"iframewindowSolicitarDefensor", statusBar: true, posx:200,posy:50,width:850,height:350,title:"Solicitar Defensor", type:"iframe"});
		    $.updateWindowContent("iframewindowSolicitarDefensor",'<iframe src="<%=request.getContextPath()%>/solicitarDefensor.jsp" width="850" height="350" />'); 
		}

		//Temporalmente no se utiliza esta funcion
		function muestraDictamenPericial(){
			$.newWindow({id:"iframewindowDictamenPericial", statusBar: true, posx:200,posy:50,width:850,height:350,title:"Solicitar Dictamen Pericial", type:"iframe"});
		    $.updateWindowContent("iframewindowDictamenPericial",'<iframe src="<%=request.getContextPath()%>/solicitarDictamenPericial.jsp" width="850" height="350" />'); 
		}

		//Ventana de captura de queja ciudadana
		function muestraQuejaCiudadana(){
			
			$.newWindow({id:"iframewindowQuejaCiudadana", statusBar: true, posx:255,posy:111,width:1023,height:473,title:"Queja Ciudadana", type:"iframe"});
		    $.updateWindowContent("iframewindowQuejaCiudadana",'<iframe src="<%=request.getContextPath()%>/quejaCiudadana.do" width="1023" height="473" />'); 
		}

		//Ventana de captura de queja ciudadana
		function consultaQuejaCiudadana(rowid){
			$.newWindow({id:"iframewindowConsultaQuejaCiudadana", statusBar: true, posx:200,posy:50,width:850,height:350,title:"Consulta de Queja Ciudadana", type:"iframe"});
		    $.updateWindowContent("iframewindowConsultaQuejaCiudadana",'<iframe src="<%=request.getContextPath()%>/consultaQuejaCiudadana.do?idQueja='+rowid+'" width="850" height="350" />'); 
		}

		function cerrarVentanaConsultaQueja(){
			var pantalla ="iframewindowConsultaQuejaCiudadana";
			$.closeWindow(pantalla);
		}
		
		//Ventana de queja ciudadana concluida
		function quejaCiudadanaConcluida(rowid){
			$.newWindow({id:"iframewindowQuejaCiudadanaConcluida", statusBar: true, posx:200,posy:50,width:850,height:350,title:"Queja Ciudadana Concluida", type:"iframe"});
		    $.updateWindowContent("iframewindowQuejaCiudadanaConcluida",'<iframe src="<%=request.getContextPath()%>/quejaCiudadanaConcluida.do?idQueja='+rowid+'" width="850" height="350" />'); 
		}

		function cerrarVentanaQuejaConcluida(){
			var pantalla ="iframewindowQuejaCiudadanaConcluida";
			$.closeWindow(pantalla);
		}
		
		function cerrarVentanaQueja(){
			var pantalla ="iframewindowQuejaCiudadana";
			$.closeWindow(pantalla);
		}
		
		//Ventana consulta de llamadas de auxilio
		function mostrarVentanaLlamada(rowid){
			$.newWindow({id:"iframewindowLlamadaAuxilio", statusBar: true, posx:50,posy:80,width:1200,height:440,title:"Aviso de Auxilio", type:"iframe"});
		    $.updateWindowContent("iframewindowLlamadaAuxilio",'<iframe src="<%=request.getContextPath()%>/avisosAuxilio.do?avisoId='+rowid+'&estatus='+estatusAvisoAuxilio+'" width="1200" height="440" />'); 
		}

		function cerrarVentanaAviso(){
			var pantalla ="iframewindowLlamadaAuxilio";
			$.closeWindow(pantalla);
		}
		
		function muestraVerGrafica(){
			$.newWindow({id:"iframewindowWindowImageViewer", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Visor de imagenes", type:"iframe"});
		    $.updateWindowContent("iframewindowWindowImageViewer",'<iframe src="<%=request.getContextPath()%>/detallevisorGrafica.do" width="1140" height="400" />');
		}

		function cargaGridSolicitud (){

		jQuery("#gridElaborarOficioResolutivoJuez").jqGrid({ 
			url:'<%=request.getContextPath()%>/consultarSolicitudesMandatoJudicial.do', 
			datatype: "xml", 
			colNames:['N&uacute;mero de IPH','Nombre del Inculpado','Delito','Tipo de Resolutivo','Resoluci&oacute;n Emitida Por','Fecha de Resoluci&oacute;n' ], 
			colModel:[ 	{name:'iph',index:'1', width:40, align:"center"}, 
						{name:'nombreInculpado',index:'2', width:45, align:"center"}, 
						{name:'delito',index:'3', width:30, align:"center"}, 
						{name:'tipoResolutivo',index:'4', width:35, align:"center"},
						{name:'emitidoPor',index:'5', width:50, align:"center"}, 
						{name:'fechaResolucion',index:'6', width:45, align:"center"}
					],
			pager: jQuery('#paginadorSolicitudes'),
			rowNum:20,
			rowList:[10,20,30],
			width:765,
			height:440,
			sortname: '1',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
				
				var ret = jQuery("#gridElaborarOficioResolutivoJuez").jqGrid('getRowData',rowid);

				tipoResolutivo = ret.tipoResolutivo;
				
				administrarResolutivosImputado();
			}
		}).navGrid('#paginadorSolicitudesPJATP',{edit:false,add:false,del:false});
		$("#gview_gridElaborarOficioResolutivoJuez .ui-jqgrid-bdiv").css('height', '440px');

		muestraOcultaGrids("solicitudes");				  
		
		}

		function ocultaGridSolicitud(){

			$('#divGridSolicitudes').hide();

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
			,	onresize_end:			function () { $("#gridElaborarOficioResolutivoJuez").setGridWidth($("#mainContent").width() - 10, true); }
			}
		};
						
		function estilosMenu(opc){
			if(opc=='procuracionJusticia'){
				$("#sp1").css({ color: "#1C94C4"});
				$("#sp2").css({ color: "#1C94C4"});
				$("#sp3").css({ color: "#1C94C4"});
				$("#sp4").css({ color: "#1C94C4"});
				$("#sp5").css({ color: "#1C94C4"});
				$("#sp6").css({ color: "#1C94C4"});
				$("#sp7").css({ color: "#1C94C4"});
			}else if(opc=='sp1'){
				$("#sp1").css({ color: "#E78F08"});
				$("#sp2").css({ color: "#1C94C4"});
				$("#sp3").css({ color: "#1C94C4"});
				$("#sp4").css({ color: "#1C94C4"});
				$("#sp5").css({ color: "#1C94C4"});
				$("#sp6").css({ color: "#1C94C4"});
				$("#sp7").css({ color: "#1C94C4"});
			}else if(opc=='sp2'){
				$("#sp1").css({ color: "#1C94C4"});
				$("#sp2").css({ color: "#E78F08"});
				$("#sp3").css({ color: "#1C94C4"});
				$("#sp4").css({ color: "#1C94C4"});
				$("#sp5").css({ color: "#1C94C4"});
				$("#sp6").css({ color: "#1C94C4"});
				$("#sp7").css({ color: "#1C94C4"});
			}else if(opc=='sp3'){
				$("#sp1").css({ color: "#1C94C4"});
				$("#sp2").css({ color: "#1C94C4"});
				$("#sp3").css({ color: "#E78F08"});
				$("#sp4").css({ color: "#1C94C4"});
				$("#sp5").css({ color: "#1C94C4"});
				$("#sp6").css({ color: "#1C94C4"});
				$("#sp7").css({ color: "#1C94C4"});
			}else if(opc=='sp4'){
				$("#sp1").css({ color: "#1C94C4"});
				$("#sp2").css({ color: "#1C94C4"});
				$("#sp3").css({ color: "#1C94C4"});
				$("#sp4").css({ color: "#E78F08"});
				$("#sp5").css({ color: "#1C94C4"});
				$("#sp6").css({ color: "#1C94C4"});
				$("#sp7").css({ color: "#1C94C4"});
			}else if(opc=='sp5'){
				$("#sp1").css({ color: "#1C94C4"});
				$("#sp2").css({ color: "#1C94C4"});
				$("#sp3").css({ color: "#1C94C4"});
				$("#sp4").css({ color: "#1C94C4"});
				$("#sp5").css({ color: "#E78F08"});
				$("#sp6").css({ color: "#1C94C4"});
				$("#sp7").css({ color: "#1C94C4"});
			}else if(opc=='sp6'){
				$("#sp1").css({ color: "#1C94C4"});
				$("#sp2").css({ color: "#1C94C4"});
				$("#sp3").css({ color: "#1C94C4"});
				$("#sp4").css({ color: "#1C94C4"});
				$("#sp5").css({ color: "#1C94C4"});
				$("#sp6").css({ color: "#E78F08"});
				$("#sp7").css({ color: "#1C94C4"});
			}else if(opc=='sp7'){
				$("#sp1").css({ color: "#1C94C4"});
				$("#sp2").css({ color: "#1C94C4"});
				$("#sp3").css({ color: "#1C94C4"});
				$("#sp4").css({ color: "#1C94C4"});
				$("#sp5").css({ color: "#1C94C4"});
				$("#sp6").css({ color: "#1C94C4"});
				$("#sp7").css({ color: "#E78F08"});
			}
		}


		//Abre una nueva ventana para informar lugar de los hechos
		function abreVentanaLugarDeHechos() {
			$.newWindow({id:"iframewindowLugarDeHechos", statusBar: true, posx:50,posy:80,width:1200,height:430,title:"Llamadas de auxilio", type:"iframe"});
		    $.updateWindowContent("iframewindowLugarDeHechos",'<iframe src="<%= request.getContextPath() %>/lugarDeLosHechos.do" width="1200" height="430" />');		
		}

		function cerrarVentanaLugarDeHechos(){
			var pantalla ="iframewindowLugarDeHechos";
			$.closeWindow(pantalla);
		}
		

/****************************************************************FUNCIONALIDAD PARA EL ON READY/*******************************************************************/
	 	/*
		*Funcion que carga el grid de consulta por fechas
		*/
		function cargaGridInformePolicial(){
			if(primeraVezGridInformePolicial == true){
							
				  jQuery("#gridInformePolicialSPUCA").jqGrid({ 
						url:'<%= request.getContextPath() %>/consultarIPHs.do?involucrado=false',					
						datatype: "xml",
						colNames:['Folio de control','Tipo de Evento','Subtipo de Evento','Detencion','Fecha Informe','Hora Informe','Atendida'], 
						colModel:[ 	{name:'folioControl',index:'1',width:100, align:'center'},
									{name:'tipoEvento',index:'2',width:100, align:'center'}, 
									{name:'subTipoEvento',index:'3', width:100, align:'center'}, 
									{name:'detencion',index:'4', width:50, align:'center', formatter: "checkbox",
										 formatoptions: {disabled : false}},
									{name:'fechaInforme',index:'5', width:50, align:'center'},
									{name:'horaInforme',index:'6', width:50, align:'center'},
									{name:'atendida',index:'7', width:50, align:'center',formatter:'checkbox'},
								],
						autowidth: false,
						width:924, 
						pager: jQuery('#pagerGridInformePolicialSPUCA'),
						rowNum:10,
						rowList:[10,20,30],
						sortname: '1',
						sortorder: "desc", 
						viewrecords: true,
						//caption:"Resultado de la B&uacute;squeda",
						ondblClickRow: function(rowid) {
							//validaMuestraDetencion(rowid);
							mostrarIPH(rowid);
						} 
					}).navGrid('#pagerGridInformePolicialSPUCA',{edit:false,add:false,del:false}); 
					$("#gview_gridInformePolicialSPUCA .ui-jqgrid-bdiv").css('height', '440px');
					primeraVezGridInformePolicial = false;
					//Resize del grid
					$("#gridInformePolicialSPUCA").setGridWidth($("#mainContent").width() - 5, true);
					muestraOcultaGrids("informePolicial");
			}
			else{
				jQuery("#gridInformePolicialSPUCA").jqGrid('setGridParam', {url:'local',datatype: "xml" });
				$("#gridInformePolicialSPUCA").trigger("reloadGrid");
				muestraOcultaGrids("informePolicial");				  
			}
		}

		//Muestra el visor con la informaci&oacute;n del IPH
		function mostrarIPH(rowid){
			var ret = jQuery("#gridInformePolicialSPUCA").jqGrid('getRowData',rowid);
			var folioIPH = ret.folioControl;
			$.newWindow({id:"iframewindowIPH", statusBar: true, posx:20,posy:30,width:1400,height:600,title:"Ingresar Informe Policial Homologado:" +folioIPH, type:"iframe"});
		    $.updateWindowContent("iframewindowIPH",'<iframe src="<%=request.getContextPath()%>/mostrarIPH.do?folioIPH='+folioIPH+'&rowid='+rowid+'" width="1400" height="600" />'); 
		}

		//Muestra el visor de detencion solo para aquellas solicitudes que no han sido atendidas
		function validaMuestraDetencion(idInforme){
			if(idInforme != null){
				var rowSelectedData = jQuery("#gridInformePolicialSPUCA").jqGrid('getRowData',idInforme);
				var folioIPH = rowSelectedData.folioControl;
				//if(rowSelectedData.atendida == true){
					muestraDetencion(folioIPH);
			}
		}


		/*
		*Muestra u oculta los grids dependiendo del grid que fue seleccionado
		*/
		function muestraOcultaGrids(accion){
			if(accion == "informePolicial"){
				$('#gridAcuerdos').hide();
				$('#gridMediar').hide();
				$('#divGridTrasladosSSPP').hide();
				$('#divGridInformePolicialSPUCA').show();
				$('#divGridSolicitudes').hide();
				$('#divGridQuejaPendiente').hide();
				$('#divGridQuejaConcluida').hide();
				$("#divGridSolsXAtndr").hide();
				$('#divGridAvisosAuxilio').hide();
				$("#divGridRegistroDeDetencion").hide();
				$("#divGridDetencion").hide();
			}
			if(accion == "solicitudes"){
				$('#gridAcuerdos').hide();
				$('#gridMediar').hide();
				$('#divGridTrasladosSSPP').hide();
				$('#divGridInformePolicialSPUCA').hide();
				$('#divGridSolicitudes').show();
				$('#divGridQuejaPendiente').hide();
				$('#divGridQuejaConcluida').hide();
				$("#divGridSolsXAtndr").hide();
				$('#divGridAvisosAuxilio').hide();
				$("#divGridRegistroDeDetencion").hide();
				$("#divGridDetencion").hide();
			}
			if(accion == "gridAvisosAuxilio"){
				$('#divGridTrasladosSSPP').hide();
				$('#divGridAvisosAuxilio').show();
				$('#divGridSolicitudes').hide();
				$('#gridAcuerdos').hide();
				$('#gridMediar').hide();
				$('#divGridQuejaPendiente').hide();
				$('#divGridQuejaConcluida').hide();
				$("#divGridSolsXAtndr").hide();
				$('#divGridInformePolicialSPUCA').hide();
				$("#divGridRegistroDeDetencion").hide();
				$("#divGridDetencion").hide();
			}
			if(accion == "Mediar"){
				$('#divGridTrasladosSSPP').hide();
				$('#divGridInformePolicialSPUCA').hide();
				$('#divGridAcuerdos').hide();
				$('#divGridSolicitudes').hide();
				$('#divGridMediar').show();
				$('#divGridQuejaPendiente').hide();
				$('#divGridQuejaConcluida').hide();
				$("#divGridSolsXAtndr").hide();
				$('#divGridAvisosAuxilio').hide();
				$("#divGridRegistroDeDetencion").hide();
				$("#divGridDetencion").hide();
			}
			if(accion == "Acuerdos"){
				$('#divGridTrasladosSSPP').hide();
				$('#divGridInformePolicialSPUCA').hide();
				$('#divGridAcuerdos').show();
				$('#divGridSolicitudes').hide();
				$('#divGridMediar').hide();
				$('#divGridQuejaPendiente').hide();
				$('#divGridQuejaConcluida').hide();
				$("#divGridSolsXAtndr").hide();
				$('#divGridAvisosAuxilio').hide();
				$("#divGridRegistroDeDetencion").hide();
				$("#divGridDetencion").hide();
			}
			if(accion == "QuejaPendiente"){
				$('#divGridTrasladosSSPP').hide();
				$('#divGridInformePolicialSPUCA').hide();
				$('#divGridAcuerdos').hide();
				$('#divGridSolicitudes').hide();
				$('#divGridMediar').hide();
				$('#divGridQuejaPendiente').show();
				$('#divGridQuejaConcluida').hide();
				$("#divGridSolsXAtndr").hide();
				$('#divGridAvisosAuxilio').hide();
				$("#divGridRegistroDeDetencion").hide();
				$("#divGridDetencion").hide();
			}
			if(accion == "QuejaConcluida"){
				$('#divGridTrasladosSSPP').hide();
				$('#divGridInformePolicialSPUCA').hide();
				$('#divGridAcuerdos').hide();
				$('#divGridSolicitudes').hide();
				$('#divGridMediar').hide();
				$('#divGridQuejaPendiente').hide();
				$('#divGridQuejaConcluida').show();
				$("#divGridSolsXAtndr").hide();
				$('#divGridAvisosAuxilio').hide();
				$("#divGridRegistroDeDetencion").hide();
				$("#divGridDetencion").hide();
			}
			if(accion == "traslados"){
				$('#divGridInformePolicialSPUCA').hide();
				$('#divGridAcuerdos').hide();
				$('#divGridSolicitudes').hide();
				$('#divGridMediar').hide();
				$('#divGridQuejaPendiente').hide();
				$('#divGridTrasladosSSPP').show();
				$('#divGridQuejaConcluida').hide();
				$("#divGridSolsXAtndr").hide();
				$('#divGridAvisosAuxilio').hide();
				$("#divGridRegistroDeDetencion").hide();
				$("#divGridDetencion").hide();
			}	
			if(accion == "gridSolsXAtndr"){
				$('#divGridInformePolicialSPUCA').hide();
				$('#divGridAcuerdos').hide();
				$('#divGridSolicitudes').hide();
				$('#divGridMediar').hide();
				$('#divGridQuejaPendiente').hide();
				$('#divGridTrasladosSSPP').hide();
				$('#divGridQuejaConcluida').hide();
				$("#divGridSolsXAtndr").show();
				$('#divGridAvisosAuxilio').hide();
				$("#divGridRegistroDeDetencion").hide();
				$("#divGridDetencion").hide();
			}
			if(accion == "registroDeDetencion"){
				$('#divGridInformePolicialSPUCA').hide();
				$('#divGridAcuerdos').hide();
				$('#divGridSolicitudes').hide();
				$('#divGridMediar').hide();
				$('#divGridQuejaPendiente').hide();
				$('#divGridTrasladosSSPP').hide();
				$('#divGridQuejaConcluida').hide();
				$("#divGridSolsXAtndr").hide();
				$('#divGridAvisosAuxilio').hide();
				$("#divGridDetencion").hide();
				$("#divGridRegistroDeDetencion").show();
			}	

			if(accion == "detencion"){
				$('#divGridInformePolicialSPUCA').hide();
				$('#divGridAcuerdos').hide();
				$('#divGridSolicitudes').hide();
				$('#divGridMediar').hide();
				$('#divGridQuejaPendiente').hide();
				$('#divGridTrasladosSSPP').hide();
				$('#divGridQuejaConcluida').hide();
				$("#divGridSolsXAtndr").hide();
				$('#divGridAvisosAuxilio').hide();
				$("#divGridRegistroDeDetencion").hide();
				$("#divGridDetencion").show();
			}	
	 	}		
/****************************************************************FUNCIONALIDAD PARA EL ON READY/*******************************************************************/
	 function muestraGridMediar(){
		 jQuery("#gridMediar").jqGrid({
				url : '<%= request.getContextPath()%>/.do', 
				datatype: "xml", 
				
				colNames:['Folio Aviso Auxilio','Folio de Control IPH', 'Presunto Responsable','V&iacute;ctima'], 
				colModel:[ 	
				        	{name:'Auxilio',index:'1', sortable:false,width:191},
				           	{name:'Control',index:'2', sortable:false,width:191},
				           	{name:'Presunto',index:'3', sortable:false,width:191},
				           	{name:'Victima',index:'4', sortable:false,width:191}, 
				],
				pager: jQuery('#paginadorMediar'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				height:450,
				width:767,
				sortname: '1',
				viewrecords: true,
				sortorder: "desc",
				onSelectRow: function(rowID) {
					visorConciliacionMediacion();
				}
		}).navGrid('#paginadorMediar',{edit:false,add:false,del:false});
			 muestraOcultaGrids("Mediar");
	 }

	function muestraGridAcuerdos(){
		 jQuery("#gridAcuerdos").jqGrid({
				url : '<%= request.getContextPath()%>/.do', 
				datatype: "xml", 
				colNames:['Folio Acuerdo','Folio de cadena de custodia','Folio de control IPH','Presunto responsable'], 
				colModel:[ 	
				        	{name:'Acuerdo',index:'1', sortable:false},
				           	{name:'Cadena',index:'2', sortable:false},
				           	{name:'Control',index:'3', sortable:false}, 
				           	{name:'Responsable',index:'4', sortable:false}, 
				],
				pager: jQuery('#paginadorAcuerdos'),
				rowNum:10,
				rowList:[10,20,30],
				//autowidth: true,
			    height:450,
				width:767,
				sortname: '1',
				viewrecords: true,
				sortorder: "desc",
				onSelectRow: function(rowID) {
					visorConciliacionMediacion();
				}
		}).navGrid('#paginadorAcuerdos',{edit:false,add:false,del:false});
			 muestraOcultaGrids("Acuerdos");
	}

	//Abre una nueva ventana para informar lugar de los hechos
	function visorConciliacionMediacion() {
		$.newWindow({id:"iframewindowConciliacionMediacion", statusBar: true, posx:255,posy:111,width:838,height:399,title:"Por Medida y Acuerdos", type:"iframe"});
	    $.updateWindowContent("iframewindowConciliacionMediacion",'<iframe src="<%= request.getContextPath() %>/visorConciliacionMediacion.do" width="838" height="399" />');		
	}

	function muestraGridQuejaPendiente(){

		jQuery("#gridQuejaPendiente").jqGrid({
			url : '<%= request.getContextPath()%>/consultaGridQuejasPEndientes.do', 
			datatype: "xml", 
			
			colNames:['Folio de Queja','Nombre de Quejoso','Calidad del Afectado','Nombre del Funcionario','Tipo de Queja'], 
			colModel:[ 	
			        	{name:'FolioQueja',index:'1', sortable:false,width: 150},
			           	{name:'NombreQuejoso',index:'2', sortable:false, width: 250},
			           	{name:'CalidadQuejoso',index:'3', sortable:false, width: 150}, 
			           	{name:'NombreFuncionario',index:'4', sortable:false, width: 250}, 
			           	{name:'TipoQueja',index:'5', sortable:false, width: 200}, 							
			
		],
		autowidth: false,
		width:924, 
		pager: jQuery('#paginadorgridQuejaPendiente'),
		rowNum:10,
		rowList:[10,20,30],
		sortname: '1',
		viewrecords: true,
		sortorder: "desc",
		ondblClickRow: function(rowid) {
			
			consultaQuejaCiudadana(rowid);
			
		} 
		}).navGrid('#paginadorgridQuejaPendiente',{edit:false,add:false,del:false});
		 muestraOcultaGrids("QuejaPendiente");
	}

	function muestraGridQuejaConcluida(){

		jQuery("#gridQuejaConcluida").jqGrid({
			url : '<%= request.getContextPath()%>/consultaGridQuejasConcluidas.do', 
			datatype: "xml", 
			
			colNames:['Folio de Queja','Nombre de Quejoso','Calidad del Afectado','Nombre del Funcionario','Motivo de Rechazo'], 
			colModel:[ 	
			        	{name:'FolioQueja',index:'1', sortable:false,width: 150},
			           	{name:'NombreQuejoso',index:'2', sortable:false, width: 250},
			           	{name:'CalidadQuejoso',index:'3', sortable:false, width: 150}, 
			           	{name:'NombreFuncionario',index:'4', sortable:false, width: 250}, 
			           	{name:'MotivoRechazo',index:'5', sortable:false, width: 200}, 							
			
		],
		autowidth: false,
		width:924, 
		pager: jQuery('#paginadorgridQuejaConcluida'),
		rowNum:10,
		rowList:[10,20,30],
		sortname: '1',
		viewrecords: true,
		sortorder: "desc",
		ondblClickRow: function(rowid) {
			quejaCiudadanaConcluida(rowid);
		} 
		}).navGrid('#paginadorgridQuejaConcluida',{edit:false,add:false,del:false});
		 muestraOcultaGrids("QuejaConcluida");
	}
/****************************************************FUNCIONALIDAD PARA LA CEJA DE SOLICIYUDES*********************************************************************/

	function visorLeyesCodigos() {
		$.newWindow({id:"iframewindowRestaurativa", statusBar: true, posx:254,posy:111,width:809,height:468,title:"Leyes y C&oacute;digos", type:"iframe"});
	    $.updateWindowContent("iframewindowRestaurativa",'<iframe src="<%= request.getContextPath() %>/detalleLeyesyCodigos.do" width="809" height="468" />');
	}

	/*
	*Funcion que llama a la funcionalidad para generar un visualizador de imagen  $('#imageViewer').click(generaVisorGraficaView);
	*/
	function generaVisorGraficaView() {
		$.newWindow({id:"iframewindowWindowImageViewer", statusBar: true, posx:63,posy:111,width:1140,height:400,title:"Visor de imagenes", type:"iframe"});
	    $.updateWindowContent("iframewindowWindowImageViewer",'<iframe src="<%=request.getContextPath()%>/VisorGraficas.do" width="1140" height="400" />');
	}	

	function consultaSolicitudTraslados() {
		$.newWindow({id:"iframewindowWindowImageViewer", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Visor Traslados", type:"iframe"});
	    $.updateWindowContent("iframewindowWindowImageViewer",'<iframe src="<%=request.getContextPath()%>/detallevisorTraslados.do" width="1140" height="400" />');
	}	

	
	/**FUNCIONES PARA GENERAR EL NUEVO NUMERO DE EXPEDIENTE**/
	//Funcion ke genera un nuevo numero de expediente para la ui en el mismo expediente
	function nuevoNumeroExpediente(id){
		
		var rowd = jQuery("#gridSolsXAtndr").jqGrid('getGridParam','selrow');
		var retd = jQuery("#gridSolsXAtndr").jqGrid('getRowData',rowd);
		id=retd.idExpediente.substring(retd.idExpediente.indexOf(">")+1,retd.idExpediente.indexOf("<",1));

		var idExpediente="0";
		var numeroExpediente="0";
		var numeroExpedienteId="0";
		var numeroGeneralCaso="0";
		//var numExpAlter=null;
		
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/nuevoNumeroExpediente.do?idArea='+<%=Areas.POLICIA_MINISTERIAL.ordinal()%>+'&idExpediente='+id,
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			idExpediente=$(xml).find('expedienteDTO').find('expedienteId').text();
    			numeroExpediente=$(xml).find('expedienteDTO').find('numeroExpediente').text();
    			numeroExpedienteId=$(xml).find('expedienteDTO').find('numeroExpedienteId').text();
    			numeroGeneralCaso=$(xml).find('expedienteDTO').find('numeroGeneralCaso').text();
    		}
    		
    	});
    	if(numeroExpedienteId!="0"){
        	var pantallaSolicitada=6;
    		idWindowNuevaDenuncia++;
    		 var ingresoDenuncia = true;
    		$.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Expediente: "+numeroExpediente, type:"iframe"});
    		$.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/BusquedaExpediente.do?abreenPenal=abrPenal&ingresoDenuncia='+ingresoDenuncia +'&idExpedienteop='+idExpediente+'&pantallaSolicitada='+pantallaSolicitada+'&numeroExpediente='+numeroExpediente+'&idNumeroExpedienteop='+numeroExpedienteId+'&idNumeroExpediente='+numeroExpedienteId+'&numeroGeneralCaso='+numeroGeneralCaso+'" width="1430" height="670" />');
    		<%-- $.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/BusquedaExpediente.do?abreenPenal=abrPenal&ingresoDenuncia='+ingresoDenuncia +'&idExpedienteop='+idExpediente+'&pantallaSolicitada='+pantallaSolicitada+'&numeroExpediente='+numeroExpediente+'&idNumeroExpedienteop='+numeroExpedienteId+'&idNumeroExpediente='+numeroExpedienteId+'&numeroGeneralCaso='+numeroGeneralCaso+'&numExpAlter='+numExpAlter+'" width="1430" height="670" />'); --%>
    		 //nuevaDenunciaFaci(idExpediente);
        }
	}
	
	function tituloVentana(num){
		$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Expediente: "+num);
	}
    /**FIN - FUNCIONES PARA GENERAR EL NUEVO NUMERO DE EXPEDIENTE**/
    
    
    
    /************************************************FUNCIONALIDAD PARA LAS BANDEJAS****************************************************************/
    
    
	/*
	*Funcion que carga el grid con los expediente de registro de detencion seg&uacute;n su estatus
	*/
	function cargaGridRegistroDeDetencion(estatus){

		//confirmarRegistroDeDetencion(1);
		if(primeraVezGridRegistroDeDetencion == true){
						
			  jQuery("#gridRegistroDeDetencion").jqGrid({ 
					url:'<%= request.getContextPath() %>/consultarRegistroDeDetencionPorEstatus.do?estatus='+estatus+'',					
					datatype: "xml",
					colNames:['N&uacute;mero de Expediente','Folio Aviso Auxilio','Fecha del Aviso Auxilio','Probable Delito'], 
					colModel:[ 	{name:'NumeroExpediente',index:'1',width:250, align:'center'},
								{name:'FolioAviso',index:'2',width:200, align:'center'}, 
								{name:'FechaAviso',index:'3', width:200, align:'center'}, 
								{name:'ProbableDelito',index:'4', width:100, align:'center'}
							],
						autowidth: false,
						width:924, 
						pager: jQuery('#pagerGridRegistroDeDetencion'),
						rowNum:10,
						rowList:[10,20,30,40,50,60,70,80,90,100],
						sortname: '1',
						sortorder: "desc", 
						viewrecords: true,
						ondblClickRow: function(rowid) {
							confirmarRegistroDeDetencion(rowid);
						} 
				}).navGrid('#pagerGridRegistroDeDetencion',{edit:false,add:false,del:false}); 
				$("#gview_gridRegistroDeDetencion .ui-jqgrid-bdiv").css('height', '440px');
				primeraVezGridRegistroDeDetencion = false;
				//Resize del grid de estado expediente
				$("#gridRegistroDeDetencion").setGridWidth($("#mainContent").width() - 5, true);
		}
		else{
			jQuery("#gridRegistroDeDetencion").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultarRegistroDeDetencionPorEstatus.do?estatus='+estatus+'',datatype: "xml" });
			$("#gridRegistroDeDetencion").trigger("reloadGrid");
		}
		muestraOcultaGrids("registroDeDetencion");
	}


    //variable para controlar la carga del grid
    var primeraVezGridDetencion = true;
    
	/*
	*Funcion que carga el grid con los expediente de registro de detencion seg&uacute;n su estatus
	*/
	function cargaGridDetencion(estatus){

		if(primeraVezGridDetencion == true){
						
			  jQuery("#gridDetencion").jqGrid({ 
					url:'<%= request.getContextPath() %>/consultarDetencionPorEstatus.do?estatus='+estatus+'',					
					datatype: "xml",
					colNames:['N&uacute;mero de Expediente','FechaApertura','Estatus'], 
					colModel:[ 	{name:'NumeroExpediente',index:'1',width:250, align:'center'},
					           	{name:'FechaApertura',index:'2',width:150, align:'center'},
					           	{name:'Estatus',index:'3',width:150, align:'center'}
							],
						autowidth: false,
						width:924, 
						pager: jQuery('#pagerGridDetencion'),
						rowNum:10,
						rowList:[10,20,30,40,50,60,70,80,90,100],
						sortname: '1',
						sortorder: "desc", 
						viewrecords: true,
						ondblClickRow: function(rowid) {
							consultaDetencionPorExpedienteId(rowid);
						} 
				}).navGrid('#pagerGridDetencion',{edit:false,add:false,del:false}); 
				$("#gview_gridDetencion .ui-jqgrid-bdiv").css('height', '440px');
				primeraVezGridDetencion = false;
				//Resize del grid de estado expediente
				$("#gridDetencion").setGridWidth($("#mainContent").width() - 5, true);
		}
		else{
			jQuery("#gridDetencion").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultarDetencionPorEstatus.do?estatus='+estatus+'',datatype: "xml" });
			$("#gridDetencion").trigger("reloadGrid");
		}
		muestraOcultaGrids("detencion");
	}


	//Variable para controlar los ids de las ventanas
	var idWindowDetencion = 1;

	/*
	*Funcion que muestra el visor de detencion
	*/
	function consultaDetencionPorExpedienteId(rowid){
		var ret = jQuery("#gridDetencion").jqGrid('getRowData',rowid);
		idWindowDetencion++;
		$.newWindow({id:"iframeWindowMostrarDetencion" + idWindowDetencion, statusBar: true, posx:150,posy:10,width:1000,height:580,title:"Detenci&oacute;n. Expediente:"+ret.NumeroExpediente, type:"iframe"});
   		$.updateWindowContent("iframeWindowMostrarDetencion" + idWindowDetencion,'<iframe src="<%= request.getContextPath() %>/mostrarRegistroDetencion.do?expedienteId='+rowid+'&numeroExpediente='+ret.NumeroExpediente+'&registroSinHecho=true&consultaDetencion=true" width="1000" height="580" />');		
	}

    
	function consultaRegistroDeDetencionPorExpedienteId(rowid){
		var ret = jQuery("#gridRegistroDeDetencion").jqGrid('getRowData',rowid);
   		idWindowMostrarRegistroDetencion++;
   		$.newWindow({id:"iframewindowMostrarRegistroDetencion" + idWindowMostrarRegistroDetencion, statusBar: true, posx:150,posy:10,width:1000,height:580,title:"Registro de detenci&oacute;n. Expediente:"+ret.NumeroExpediente+". No. Folio:"+ret.FolioAviso, type:"iframe"});
   		$.updateWindowContent("iframewindowMostrarRegistroDetencion" + idWindowMostrarRegistroDetencion,'<iframe src="<%= request.getContextPath() %>/mostrarRegistroDetencion.do?expedienteId='+rowid+'&numeroExpediente='+ret.NumeroExpediente+'" width="1000" height="580" />');		
	}

	
	function cargaGridAvisosAuxilio(estatus){

		//Estatus que se enviara, al abrir la ventana
		estatusAvisoAuxilio = estatus;		
		
		if(primeraVezGridAvisos == true){
			jQuery("#gridAvisosAuxilio").jqGrid({
				url : '<%= request.getContextPath()%>/consultaGridAvisosAuxilio.do?estatus='+estatus+'', 
				datatype: "xml",
				colNames:['Folio de Aviso','Tipo de Delito','Solicitante de Auxilio','Calidad','Domicilio','Fecha Aviso','Hora','Estatus'], 
				colModel:[ 	
				        	{name:'FolioAviso',index:'1', sortable:false,width: 120, align:'center'},
				           	{name:'TipoDelito',index:'2', sortable:false, width: 150, align:'center'},
				           	{name:'Solicitante',index:'3', sortable:false, width: 250, align:'center'}, 
				           	{name:'Calidad',index:'4', sortable:false, width: 100, align:'center'}, 							
				           	{name:'Domicilio',index:'5', sortable:false, width: 150, align:'center'}, 							
				           	{name:'Fecha',index:'6', sortable:false, width: 100, align:'center'}, 							
				           	{name:'Hora',index:'7', sortable:false, width: 80, align:'center'}, 							
				           	{name:'UnidadInvestigacion',index:'8', sortable:false, width: 100, align:'center'}, 
						],
				autowidth: false,
				width:1040,
				heigth:480,
				pager: jQuery('#paginadorgridAvisosAuxilio'),
				rowNum:10,
				rowList:[10,20,30],
				sortname: '1',
				viewrecords: true,
				sortorder: "asc",
				ondblClickRow: function(rowid) {
					mostrarVentanaLlamada(rowid);
				} 
				}).navGrid('#paginadorgridAvisosAuxilio',{edit:false,add:false,del:false});
	
				$("#gview_gridAvisosAuxilio .ui-jqgrid-bdiv").css('height', '390px');
				primeraVezGridAvisos=false;
			
		}else{
			jQuery("#gridAvisosAuxilio").jqGrid('setGridParam', 
					{url:'<%= request.getContextPath()%>/consultaGridAvisosAuxilio.do?estatus='+estatus+'',datatype: "xml" });
			$("#gridAvisosAuxilio").trigger("reloadGrid");
		}
		muestraOcultaGrids("gridAvisosAuxilio");
	}
    
    
	function confirmarRegistroDeDetencion(rowid){
		consultaRegistroDeDetencionPorExpedienteId(rowid);		  		
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
	    				customAlert("Alarma pospuesta");
	    			}
	    			else if(estatus=="cancelar")
	    			{
	    				customAlert("Alarma cancelada");
	    			}
	    			else
	    			{
	    				customAlert("Alarma aceptada");
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
	
	//********************************************FUNCIONES PARA FUNCIONALIDAD DEL BOTON DETENCION************************************************/
	
	function generarExpedienteYDetencion(){

		var expedienteIdSSP="";
		var numeroExpedienteSSP="";
		var numeroExpedienteIdSSP="";
		var errorCode=0;
		var hechoId="";
		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/generarExpedienteSSP.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				
    			errorCode=$(xml).find('response').find('code').text();
    			//Si errorCode=0 entonces continuamos con el flujo
    			if(parseInt(errorCode)==0){
    				expedienteIdSSP = $(xml).find('expedienteDTO').find('expedienteId').first().text();
    				numeroExpedienteSSP = $(xml).find('expedienteDTO').find('numeroExpediente').first().text();
    				numeroExpedienteIdSSP = $(xml).find('expedienteDTO').find('numeroExpedienteId').first().text();
    				hechoId = $(xml).find('expedienteDTO').find('hechoDTO').find('hechoId').first().text();
        		}else{
        			customAlert("No se ha podido generar el expediente para la detencion.");
            	}
			}
		});

		if(parseInt(errorCode)==0){
			idWindowMostrarRegistroDetencion++;
	   		$.newWindow({id:"iframewindowMostrarRegistroDetencion" + idWindowMostrarRegistroDetencion, statusBar: true, posx:150,posy:10,width:1000,height:580,title:"Registro de detenci&oacute;n. Expediente:"+numeroExpedienteSSP, type:"iframe"});
	   		$.updateWindowContent("iframewindowMostrarRegistroDetencion" + idWindowMostrarRegistroDetencion,'<iframe src="<%= request.getContextPath() %>/mostrarRegistroDetencion.do?expedienteId='+expedienteIdSSP+'&numeroExpediente='+numeroExpedienteSSP+'&hechoId='+hechoId+'&registroSinHecho=true" width="1000" height="580" />');
		}
	}
	
		
</script>
</head>

<body>
	<div class="ui-layout-west">	
		<div class="header">&nbsp;</div>
			<div class="content">
				<div id="accordionmenuprincipal">
					<h3>
						<a id="procuracionJusticia" href="#"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Informe Policial</a>
					</h3>
					<div>
						<ul id="seccion5tree" class="filetree">
							<li class="closed" id=""><span class="folder">Avisos de Auxilio</span>
								<ul>
									<li>
										<span class="file" onclick="cargaGridAvisosAuxilio(<%=EstatusNotificacion.NO_ATENDIDA.getValorId()%>);">
											<a style="cursor: pointer;">Nuevas</a>
										</span>
									</li>
									<li>
										<span class="file" onclick="cargaGridAvisosAuxilio(<%=EstatusNotificacion.ATENDIDA.getValorId()%>);">
											<a style="cursor: pointer;">Atendidas</a>
										</span>
									</li>
								</ul>
							</li>
												
							<li class="closed" id=""><span class="folder">Registro de Detenci&oacute;n</span>
								<ul>
									<li>
										<span class="file" onclick="cargaGridRegistroDeDetencion(<%=EstatusExpediente.ABIERTO.getValorId()%>);">
											<a style="cursor: pointer;">Nuevas</a>
										</span>
									</li>
									<li>
										<span class="file" onclick="cargaGridRegistroDeDetencion(<%=EstatusExpediente.POR_ATENDER.getValorId()%>);">
											<a style="cursor: pointer;">En Proceso</a>
										</span>
									</li>
									<li>
										<span class="file" onclick="cargaGridRegistroDeDetencion(<%=EstatusExpediente.ATENDIDAS.getValorId()%>);">
											<a style="cursor: pointer;">Atendidas</a>
										</span>
									</li>
									<li>
										<span class="file" onclick="cargaGridRegistroDeDetencion(<%=EstatusExpediente.CANALIZADO.getValorId()%>);">
											<a style="cursor: pointer;">Canalizadas</a>
										</span>
									</li>
									<li>
										<span class="file" onclick="cargaGridRegistroDeDetencion(<%=EstatusExpediente.CERRADO.getValorId()%>);">
											<a style="cursor: pointer;">Cerradas</a>
										</span>
									</li>
								</ul>
							</li>
							
							<li class="closed" id=""><span class="folder">Detenci&oacute;n</span>
								<ul>
									<li>
										<span class="file" onclick="cargaGridDetencion(<%=EstatusExpediente.ABIERTO.getValorId()%>);">
											<a style="cursor: pointer;">Nuevas</a>
										</span>
									</li>
									<li>
										<span class="file" onclick="cargaGridDetencion(<%=EstatusExpediente.POR_ATENDER.getValorId()%>);">
											<a style="cursor: pointer;">En Proceso</a>
										</span>
									</li>
									<li>
										<span class="file" onclick="cargaGridDetencion(<%=EstatusExpediente.ATENDIDAS.getValorId()%>);">
											<a style="cursor: pointer;">Atendidas</a>
										</span>
									</li>
									<li>
										<span class="file" onclick="cargaGridDetencion(<%=EstatusExpediente.CANALIZADO.getValorId()%>);">
											<a style="cursor: pointer;">Canalizadas</a>
										</span>
									</li>
									<li>
										<span class="file" onclick="cargaGridDetencion(<%=EstatusExpediente.CERRADO.getValorId()%>);">
											<a style="cursor: pointer;">Cerradas</a>
										</span>
									</li>
								</ul>
							</li>
												
						</ul>
					</div>
					
					<h3  id="quejaCiudadanaSSPPolicia">
						<a id="" href="#"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Queja Ciudadana</a>
					</h3>
					<div>
					<table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="0" style="cursor:pointer">
							<!--  <tr>
							   <td><img src="<%=request.getContextPath()%>/resources/css/check.png" width="16" height="16" /><a id="Mediar" onclick="muestraGridQuejaNueva();">Nuevas</a></td>
							</tr>-->
							<tr>
							   <td><img src="<%=request.getContextPath()%>/resources/images/icn_folderchek.png" width="20" height="16" /><a id="Acuerdo" onclick="muestraGridQuejaPendiente();">Pendientes</a></td>
							</tr>
							<tr>
							   <td><img src="<%=request.getContextPath()%>/resources/images/icn_folderchek.png" width="20" height="16" /><a id="Acuerdo" onclick="muestraGridQuejaConcluida();">Concluidas</a></td>
							</tr>
						</table>	
					</div>
					
<!-- 			<h3 ><a id="imageViewer" href="#" onclick=""><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png"  width="15" height="15">Gr&aacute;ficas y Reportes</a></h3>
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
<!--			<h1>-->
<!--				<a href="#">Clima</a>-->
<!--			</h1>-->
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
			</div>
		</div>
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
					    <TD width=28>&nbsp;</TD>
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
	<ul class="toolbar">
	<div id="menu_head">
		<ul class="toolbar">
			<li id="tbarBtnHeaderZise" class="first"><span></span></li>
			<li id="tbarBtnQuejaCiudadana" class="first"><span></span>Recibir queja&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_errorinfo.png" width="15" height="16"></li>			
			<li id="lugarHechos" class="first" onclick="abreVentanaLugarDeHechos();"><span></span>Llamadas de auxilio <img src="<%= request.getContextPath() %>/resources/images/icn_folderchek.png" width="15" height="16"></li>
			<li id="detencionBtn" class="first" onclick="generarExpedienteYDetencion();"><span></span>Detenci&oacute;n<img src="<%= request.getContextPath() %>/resources/images/icn_folderchek.png" width="15" height="16"></li>
		</ul>
	</div>
	<div id="menu_config">
		<ul class="toolbar">
<!--			<li id="verde">Configuraci&oacute;n&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_config.png" width="15" height="16"></li>-->
		</ul>
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
					<div id="divGridSolicitudes">
						<table id="gridElaborarOficioResolutivoJuez"></table>
						<div id="paginadorSolicitudes"></div>
					</div>
					
					<!--Comienzan los grids los divs para los grids-->
					<div id="divGridInformePolicialSPUCA">
						<table id="gridInformePolicialSPUCA" ></table>
						<div id="pagerGridInformePolicialSPUCA"></div>
					</div>
					<!--Terminan los grids los divs para los grids-->
					
					<div id="divGridMediar" onclick="visorConciliacionMediacion()">
						<table id="gridMediar"></table>
						<div id="paginadorMediar"></div>
					</div>
					
					<!--Comienzan los grids los divs para los grids-->
					<div id="divGridAcuerdos" onclick="visorConciliacionMediacion()">
						<table id="gridAcuerdos" ></table>
						<div id="paginadorAcuerdos"></div>
					</div>
					
					
					
					<!--Comienzan los grids los divs para los grids-->
					<div id="divGridQuejaPendiente" >
						<table id="gridQuejaPendiente" ></table>
						<div id="paginadorgridQuejaPendiente"></div>
					</div>
					
					<div id="divGridQuejaConcluida" >
						<table id="gridQuejaConcluida" ></table>
						<div id="paginadorgridQuejaConcluida"></div>
					</div>

					<div id="divGridAvisosAuxilio" >
						<table id="gridAvisosAuxilio" ></table>
						<div id="paginadorgridAvisosAuxilio"></div>
					</div>

					<!-- Comienza grid traslados-->
					<div id="divGridTrasladosSSPP" onclick="consultaSolicitudTraslados();">
						<table id="gridTrasladosSSPP" ></table>
						<div id="pagerGridTrasladosSSPP"></div>
					</div>
					<!-- Termina grid traslados-->
					
					<div id="divGridSolsXAtndr" style="display: none;">
					 	<table id="gridSolsXAtndr" width="100%" height="100%"></table>
						<div id="pagerGridSolsXAtndr"></div>
					</div>
					
					<!-- Comienza grid Registro de Detencion-->
					<div id="divGridRegistroDeDetencion">
						<table id="gridRegistroDeDetencion" ></table>
						<div id="pagerGridRegistroDeDetencion"></div>
					</div>
					<!-- Termina grid Registro de Detencion-->
					
					<!-- Comienza grid Detencion-->
					<div id="divGridDetencion">
						<table id="gridDetencion" ></table>
						<div id="pagerGridDetencion"></div>
					</div>
					<!-- Termina grid Detencion-->
					
					<!--div para la ventana modal en donde se muestra la confirmacion del registro de detencion-->
					<div id="divConfirmarRegistroDeDetencion" style="display: none"></div>
					
					
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
