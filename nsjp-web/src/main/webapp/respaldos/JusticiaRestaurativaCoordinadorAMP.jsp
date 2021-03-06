<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud" %>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO"%>
<html>
<head>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas" %>
	<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/themes/1.8.10/${theme.name}/jquery-ui.css" /-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />				
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/${theme.name}/jquery-ui.css" />
	<!--link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" /-->	 
	<style type="text/css">
			dd p{line-height:120%}
			#iRepLegalAccordionPane{width:800px;height:185px;padding:1px;background:#fff;border:0px solid #b5c9e8}
			#iRepLegalAccordionPane dl{width:800px;height:185px}	
			#iRepLegalAccordionPane dt{height:46px;line-height:44px;text-align:right;padding:0 15px 0 0;font-size:1.1em;font-weight:bold;font-family: Tahoma, Geneva, sans-serif;text-transform:uppercase;letter-spacing:1px;background:#fff url(<%= request.getContextPath() %>/images/jquery/plugins/easyaccordion/slide-title-inactive-1.jpg) 0 0 no-repeat;color:#1C94C4}
			#iRepLegalAccordionPane dt.active{cursor:pointer;color:#E78F08;background:#fff url(<%= request.getContextPath() %>/images/jquery/plugins/easyaccordion/slide-title-active-1.jpg) 0 0 no-repeat}
			#iRepLegalAccordionPane dt.hover{color:#E78F08;}
			#iRepLegalAccordionPane dt.active.hover{color:#1C94C4}
			#iRepLegalAccordionPane dd{padding:1px;background:url(<%= request.getContextPath() %>/images/jquery/plugins/easyaccordion/slide.jpg) bottom left repeat-x;border:1px solid #ffffff;border-left:0;margin-right:1px}
			#iRepLegalAccordionPane .slide-number{color:#68889b;left:10px;font-weight:bold}
			#iRepLegalAccordionPane .active .slide-number{color:#fff;}
			#iRepLegalAccordionPane a{color:#68889b}
			#iRepLegalAccordionPane dd img{float:right;margin:0 0 0 0px;}
			#iRepLegalAccordionPane h2{font-size:2.5em;margin-top:10px}
			#iRepLegalAccordionPane .more{padding-top:10px;display:block}
		</style>	
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	
	<script type="text/javascript">
	<%
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	FuncionarioDTO funcionario = usuario.getFuncionario();
	%>
	
	var sesionActiva = '<%= (request.getSession().getAttribute(LoginAction.KEY_SESSION_USUARIO_FIRMADO)!=null)%>';
	if(sesionActiva=="false"){
	//alert(sesionActiva);
	document.location.href="<%= request.getContextPath()%>/Logout.do";
	}
	var outerLayout, innerLayout;
	var idWindowNuevaDenuncia=1;
	var idWindowDetalleSolicitud=1;
	
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
	
	$(document).ready(function() {
		//$('#imageViewer').click(generaVisorGraficaView);
		$("#caso1").css({ color: "#1C94C4"});
		$("#caso2").css({ color: "#1C94C4"});
		$("#caso3").css({ color: "#1C94C4"});
		$("#caso4").css({ color: "#1C94C4"});
		$("#caso5").css({ color: "#1C94C4"});
		$("#caso6").css({ color: "#1C94C4"});
		
		$("#casos_unidad_inv").css({ color: "#1C94C4"});
		$("#casos_at_penal").css({ color: "#1C94C4"});
		$("#caso3").css({ color: "#1C94C4"});
		$("#caso4").css({ color: "#1C94C4"});
		$("#caso5").css({ color: "#1C94C4"});
		$("#caso6").css({ color: "#1C94C4"});
		
		$("#dialogBlok").dialog({
			autoOpen: false,
			modal: true,
			width: 400,
			height: 200,
			closeOnEscape: false,
			draggable: false,
			resizable: false,
			buttons: {
				'¡Autenticarse!': function(){
					// fire whatever the configured onTimeout callback is.
					// using .call(this) keeps the default behavior of "this" being the warning
					// element (the dialog in this case) inside the callback.
					$(this).dialog('close');
					bloqueoSesion();

				}
			}
			
		});			
		//Codigo timer
		var $countdown = $("#dialog-countdown");

		// start the idle timer plugin
		$.idleTimeout('#dialogBlok', 'div.ui-dialog-buttonpane button:first', {
			idleAfter:'<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoBloqueoSesion()%>',
			pollingInterval: 2,
			//keepAliveURL: 'keepalive.php',
			serverResponseEquals: 'OK',
			onTimeout: function(){
				//window.location = "timeout.htm";
				$(this).dialog('close');
				//$(this).dialog( "destroy" ); .click();
				//buttonOpts.click();
				bloqueoSesion();
				
			},
			onIdle: function(){
					$(this).dialog("open");
				//bloqueoSesion();
			},
			onCountdown: function(counter){
				$countdown.html(counter); // update the counter
			},
			onResume: function(){
				
			}
		});	
//Fin
		
		//obtenemos el tiempo de las alarmas y ponemos en marcha el timer		
		var tiempo='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getTiempoRevisionAlarmas()%>';
		setInterval(muestraAlerta, tiempo);
		
		outerLayout = $("body").layout( layoutSettings_Outer );

		$("#accordionmenuprincipal").accordion({  fillSpace: true });
		$("#accordionmenuderprincipal").accordion({ fillSpace: true});	
		$("#accordionmenuderprincipal").accordion( "option", "icons", null );
		$("#seccion1tree").treeview();
		$("#seccion11tree").treeview();
		$("#seccion2tree").treeview();
		$("#seccion3tree").treeview();
		$("#seccion4tree").treeview();
		$("#seccion5tree").treeview();
		$("#entrevista").click(generaCapturaEntrevista);
		$("#caso1").click(activaPrincipal);
		$("#caso2").click(activaSecundario);
		$("#caso3").click(activaSecundario);
		$("#caso4").click(activaSecundario);
		$("#caso5").click(activaSecundario);
		$("#caso6").click(activaSecundario);
		$("#tbarBtnAsignar").click(asigarPermisos);
		//Mandamos consultar los tipos de solicitud dependiendo del Area del Funcionario
		consultarTiposSolicitudPorAreaDelFuncionarioGen('tableSolsGeneradas',"<%= funcionario.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId()%>");
		
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
		jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
								url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=JAR&actividad=RECIBIR_CANALIZACION_JAR&bandera=CERO', 
								datatype: "xml", 
								colNames:['Expediente','Tipo','Fecha', 'Denunciante', 'Delito','Origen','Estatus'],  
								colModel:[ 	{name:'Detalle',index:'detalle', width:70},
								           	{name:'Tipo',index:'tipo', width:120, align:'center', hidden:true}, 
											{name:'Fecha',index:'fecha', width:70}, 
											{name:'Nombre',index:'nombre', width:70}, 
											{name:'Resumen',index:'Resumen', width:110},
											{name:'Origen',index:'origen', width:110},
											{name:'Estatus',index:'estatus', width:110}
										],
								pager: jQuery('#pager1'),
								rowNum:30,
								rowList:[25,50,100],
								autowidth: true,
								sortname: 'detalle',
								viewrecords: true,
								onSelectRow: function(id){
//									muestraAcordeon();
									},
								ondblClickRow: function(id) {
									nuevaDenuncia2(id);
									},
								sortorder: "desc"
							}).navGrid('#pager1',{edit:false,add:false,del:false});	

		//Grid de Solicitudes generadas
		jQuery("#gridSolsGeneradas").jqGrid({ 
			url:'<%= request.getContextPath()%>/consultaSolsGeneradas.do?tipoSoliciutd=0&idArea=0&estatus=0',
			datatype: "xml", 
			colNames:['No. Caso','No. Expediente', 'Folio','Estatus','Fecha Creaci&oacute;n','Fecha Limite','Instituci&oacute;n','Destinatario'], 
			colModel:[ 	{name:'caso',index:'caso', width:150},
			           	{name:'expediente',index:'expediente', width:130}, 
						{name:'folio',index:'folio', width:100}, 
						{name:'estatus',index:'estatus', width:100}, 
						{name:'fechaCreacion',index:'fechaCreacion', width:100},
						{name:'fechaLimite',index:'fechaLimite', width:100},
						{name:'institucion',index:'institucion', width:100},
						{name:'remitente',index:'remitente', width:200}
					],
			pager: jQuery('#pagerGridSolsGeneradas'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
					dblClickRowBandejaSolicitudesGen(rowid);
					}
		}).navGrid('#pagerGridSolsGeneradas',{edit:false,add:false,del:false});
		
		
			
		$("#gview_gridDetalleFrmPrincipal .ui-jqgrid-bdiv").css('height', '430px');
		$("#gview_gridSolsGeneradas .ui-jqgrid-bdiv").css('height', '400px');
		$( "#tabs" ).tabs();	
		$("#iVictimaAccordionDialogoMenorEdad").dialog({ autoOpen: false, 
		modal: true, 
		title: 'Menor de Edad', 
		dialogClass: 'alert', 
		width: 500 ,
		maxWidth: 600,
		buttons: {"Aceptar":function() {
							$(this).dialog("close");
						}
					} 
	});		

												
	$('#iRepLegalAccordionPane').easyAccordion({ 
		autoStart: false, 
		slideInterval: 3000
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
		createInnerLayout () ;
		cargaOcupacion();
		cargaCasos();
		$( "#tabsprincipalconsulta" ).tabs();
			
			/* $("#exp01").click(creaExpediente01);
			$("#exp02").click(creaExpediente02);		 */	
			$("#controlAgenda").click(creaAgenda);
				
			$("#buscarExpediente").click(buscarExpediente);
			$('#casos li').click(agregaExpediente);		
			$("#buscarCaso").click(buscarCaso);	
			$("#tbarBtnNuvaDenuncia").click(nuevaDenuncia);
			//$('#casos1 li').click(justiciaRestaurativa);	
			$("#generarDocumento").click(generarDocumentoViwe);
			//$("#entrevista").click(generaCapturaEntrevista);
			//$("#dictamen01").click(seleccionFila);	
			$("#expediente001").click(seleccionFila);
		//agregamos el click para redireccionar a la valoracion de hechos
		$("#hrefHechos").click(realizarValoracionHechos);	
		$("#datosDenuncia").click(nuevaDenuncia);
		$("#divGridSolsGeneradas").hide();
		//$('#test').weatherfeed(['MXDF0132']);
		//cargo los datos del grid desde la BD
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
				{url:'<%=request.getContextPath()%>/BusquedaCanalizadosRestaurativa.do?area=JAR&actividad=RECIBIR_CANALIZACION_JAR', 
				datatype: "xml" });
			 $("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
			 
		});

	
	function seleccionFila(){
		$("#gridDetalleFrmPrincipal").find("tr:nth-child(2)").css({ color: "#FFFFFF", background: "#FF0000" });
		//$("#gridDetalleFrmPrincipalDos").find("tr:nth-child(2)").css({ color: "#FFFFFF", background: "#FF0000" });
	}

	function nuevaDenuncia2(id) {
		
		var idExpediente="0";
		var numeroExpediente="0";
		var numeroExpedienteId="0";
		var numeroGeneralCaso="0";
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/nuevoNumeroExpediente.do?idArea='+<%=Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.ordinal()%>+'&idExpediente='+id,
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			idExpediente=$(xml).find('expedienteDTO').find('expedienteId').text();
    			//alert("jhgdf"+idExpediente);
    			numeroExpediente=$(xml).find('expedienteDTO').find('numeroExpediente').text();
    			//alert(numeroExpediente);
    			numeroExpedienteId=$(xml).find('expedienteDTO').find('numeroExpedienteId').text();
    			//alert(numeroExpedienteId);
    			numeroGeneralCaso=$(xml).find('expedienteDTO').find('numeroGeneralCaso').text();
    			//alert(numeroGeneralCaso);
    		}
    		
    	});
    	if(numeroExpedienteId!="0"){
    		//alert(idExpediente);
        	var pantallaSolicitada=2;
    		idWindowNuevaDenuncia++;
    		 var ingresoDenuncia = true;
    		//alert('<%= request.getSession().getAttribute("numExpConsul")%>');
    		$.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Expediente: ", type:"iframe"});
    		$.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/BusquedaExpediente.do?abreenPenal=abrPenal&ingresoDenuncia='+ingresoDenuncia +'&idExpedienteop='+idExpediente+'&pantallaSolicitada='+pantallaSolicitada+'&numeroExpediente='+numeroExpediente+'&idNumeroExpedienteop='+numeroExpedienteId+'&idNumeroExpediente='+numeroExpedienteId+'&numeroGeneralCaso='+numeroGeneralCaso+'" width="1430" height="670" />');
    		 //nuevaDenunciaFaci(idExpediente);
        }
		
			
	}

    var iframewindowAPSE = 0;
    function asigarPermisos(){
		$.newWindow({id:"iframewindowAPSE"+iframewindowAPSE, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Asignar permisos sobre Expediente: ", type:"iframe"});
		$.updateWindowContent("iframewindowAPSE"+iframewindowAPSE,'<iframe src="<%= request.getContextPath() %>/asigarPermisosExpediente.do" width="1430" height="670" />');
		$("#" +"iframewindowAPSE"+iframewindowAPSE + " .window-maximizeButton").click();
		iframewindowAPSE++;
    }

	function nuevaDenunciaFaci(id) {
		alert(id);
    	var pantallaSolicitada=2;
		idWindowNuevaDenuncia++;
		 var ingresoDenuncia = true;
		//alert('<%= request.getSession().getAttribute("numExpConsul")%>');
		$.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Expediente: ", type:"iframe"});
		$.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/BusquedaExpediente.do?abreenPenal=abrPenal&ingresoDenuncia='+ingresoDenuncia +'&idNumeroExpediente='+id+'&pantallaSolicitada='+pantallaSolicitada+'" width="1430" height="670" />');
		//$("#" +"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia + " .window-maximizeButton").click();
	}
	
	function tituloVentana(num){
		$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Expediente: "+num);
	}
		
	function ejecutaChat() {
		$("#dialogoChat").dialog( "open" );
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

		function cargaCasos(){
	    	var branches = "";
	    	$.ajax({
	    		type: 'POST',
	    		url: '<%= request.getContextPath()%>/BusquedaInicialCaso.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: true,
	    		success: function(xml){
	    			var branches = "";
					$(xml).find('caso').each(function(){
						var casoId = $(this).find('casoId').text();
						var numeroGralCaso = $(this).find('numeroGeneralCaso').text();
	    				branches ="<ul><li class='closed' id='" + casoId + "CASO' onclick='agregaExpediente(" + casoId + ")'><span class='folder'>" + numeroGralCaso + "</span><ul></ul></li></ul>";
						var casos = $(branches).appendTo("#casos");
						$("#seccion1tree").treeview({
	    					add: casos
	    				});
	    			});
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
		,	onresize_end:			function () { $("#gridDetalleFrmPrincipal").setGridWidth($("#mainContent").width() - 5, true);
										$("#gridDetalleFrmSecundario").setGridWidth($("#mainContent").width() - 5, true);
										$("#gridDetalleFrmUno").setGridWidth($("#mainContent").width() - 5, true);
										$("#gridDetalleFrmDos").setGridWidth($("#mainContent").width() - 5, true);
										$("#gridDetalleSolServPericiales").setGridWidth($("#mainContent").width() - 5, true);
										
									}
		}
	};
	
	/*
	*Listener del click para la redireccion a la valoracion de hechos
	*/
	function realizarValoracionHechos()
	{
		location.href='<%= request.getContextPath()%>/RealizarValoracionHechos.do';
	}
	
	function generaCapturaEntrevista() {
		$.newWindow({id:"iframewindowCapturaEntrevista", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Captura Entrevista", type:"iframe"});
	    $.updateWindowContent("iframewindowCapturaEntrevista",'<iframe src="<%= request.getContextPath() %>/CapturaEntrevista.do" width="1140" height="400" />');
	    		
	}
	function verExpediente(idExpediente, numeroExpediente) {
		$.newWindow({id:"iframewindowExp" + idExpediente, statusBar: true, posx:200,posy:50,width:1140,height:400,title:numeroExpediente, type:"iframe"});
	    $.updateWindowContent("iframewindowExp" + idExpediente,'<iframe src="<%= request.getContextPath() %>/IngresarMenuIntermedio.do" width="1140" height="400" />');			
	}
			
	function buscarExpediente() {
		$.newWindow({id:"iframewindowBuscarExpediente", statusBar: true, posx:255,posy:110,width:653,height:400,title:"Buscar Expediente", type:"iframe"});
    	$.updateWindowContent("iframewindowBuscarExpediente",'<iframe src="<%= request.getContextPath() %>/buscarExpediente.do" width="653" height="400" />');		
	}

	function buscarCaso() {
		$.newWindow({id:"iframewindowBuscarCaso", statusBar: true, posx:255,posy:110,width:653,height:400,title:"Buscar Caso", type:"iframe"});
    	$.updateWindowContent("iframewindowBuscarCaso",'<iframe src="<%= request.getContextPath() %>/buscarCaso.do" width="653" height="400" />');		
	}
	
	function designaAbogadoDefensor() {
		$.newWindow({id:"iframewindowAsignaAbogado", statusBar: true, posx:200,posy:50,width:500,height:400,title:"Designar Abogado Defensor", type:"iframe"});
	    $.updateWindowContent("iframewindowAsignaAbogado",'<iframe src="<%= request.getContextPath() %>/IngresarMenuIntermedio.do" width="500" height="400" />');		
	}
	
	function designaAbogadoDefensor2() {
		$.newWindow({id:"iframewindowAsignaAbogado2", statusBar: true, posx:200,posy:50,width:500,height:400,title:"Designar Abogado Defensor", type:"iframe"});
	    $.updateWindowContent("iframewindowAsignaAbogado2",'<iframe src="<%= request.getContextPath() %>/IngresarMenuIntermedio.do" width="500" height="400" />');		
	}

	function creaAgenda() {
		$.newWindow({id:"iframewindowagenda", statusBar: true, posx:10,posy:10,width:1150,height:600,title:"Agenda", type:"iframe"});
	    $.updateWindowContent("iframewindowagenda",'<iframe src="<%= request.getContextPath() %>/InicioAgenda.do" width="1150" height="600" />');		
	}

	function nuevaDenuncia() {
		$.newWindow({id:"iframewindowNuevaDenuncia", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"NSJYUCROC201105113", type:"iframe"});
	    $.updateWindowContent("iframewindowNuevaDenuncia",'<iframe src="<%= request.getContextPath() %>/IngresarMenuIntermedio.do?idNuevaDenuncia=1" width="1140" height="400" />');		
	}
	
	 
	function justiciaRestaurativa() {
		$.newWindow({id:"iframewindowjusticia", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Justicia Restaurativa", type:"iframe"});
	    $.updateWindowContent("iframewindowjusticia",'<iframe src="<%= request.getContextPath() %>/JusticiaRestaurativa.do" width="1140" height="400" />');		
	}

	function generarDocumentoViwe() {
		$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Generar Documento", type:"iframe"});
	    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumento.do" width="1140" height="400" />');
	    		
	}
	
	/*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle 
	*/
	function dblClickRowBandejaSolicitudesGen(rowID){
		idWindowDetalleSolicitud++;
		$.newWindow({id:"iframewindowDetalleSolicitud"+idWindowDetalleSolicitud, statusBar: true, posx:200,posy:50,width:1100,height:450,title:"Detalle Solicitud", type:"iframe"});
    	$.updateWindowContent("iframewindowDetalleSolicitud"+idWindowDetalleSolicitud,'<iframe src="<%=request.getContextPath()%>/consultarDetalleSolicitudBandejaGen.do?idSolicitud=' +rowID +'&tipoUsuario=0 " width="1140" height="450" />'); 
	}
	
	function ocultaMuestraGridsAlertas(idGrid)
	{
		if(parseInt(idGrid)==1)
		{
			$("#divGridSolicitudes").css("display", "block");
			$("#divGridAudiencia").css("display", "none");
			$("#divGridSolicitudesUno").css("display", "none");
			$("#divGridSolsGeneradas").hide();
		}
		else
		{
			$("#divGridSolicitudes").css("display", "none");
			$("#divGridAudiencia").css("display", "block");
			$("#divGridSolicitudesUno").css("display", "none");
			$("#divGridSolsGeneradas").hide();
			
		}
	}

	function activaSecundario() {
		$("#divGridSolicitudesSecundario").css("display", "block");
		$("#divGridSolicitudes").css("display", "none");
		$("#divGridSolicitudesSecundario .ui-jqgrid-bdiv").css('height', '337px');
		$("#include").css("display", "none");
		$("#divGridSolicitudesUno").css("display", "none");
		$("#divGridSolicitudesDos").css("display", "none");
		$("#divGridSolServPericiales").css("display", "none");
		$("#divGridSolsGeneradas").hide();
	}
	
	function activaGridSolsGeneradas()
	{
		$("#divGridSolicitudesSecundario").css("display", "none");
		$("#divGridSolicitudes").css("display", "none");
		$("#include").css("display", "none");
		$("#divGridSolicitudesUno").css("display", "none");
		$("#divGridSolicitudesDos").css("display", "none");
		$("#divGridSolServPericiales").css("display", "none");
		$("#divGridSolsGeneradas").show();
	}

	function activaPrincipal() {
		$("#divGridSolicitudesSecundario").css("display", "none");
		$("#divGridSolicitudes").css("display", "block");
		$("#include").css("display", "none");
		$("#divGridSolicitudesUno").css("display", "none");
		$("#divGridSolicitudesDos").css("display", "none");
		$("#divGridSolServPericiales").css("display", "none");
		$("#divGridSolsGeneradas").hide();
	}

	function activaUno() {
		$("#divGridSolicitudesSecundario").css("display", "none");
		$("#divGridSolicitudes").css("display", "none");
		$("#include").css("display", "none");
		$("#divGridSolicitudesUno").css("display", "block");
		$("#divGridSolicitudesUno .ui-jqgrid-bdiv").css('height', '337px');
		$("#divGridSolicitudesDos").css("display", "none");
		$("#divGridSolServPericiales").css("display", "none");
		$("#divGridSolsGeneradas").hide();
	}
	function activaDos() {
		$("#divGridSolicitudesSecundario").css("display", "none");
		$("#divGridSolicitudes").css("display", "none");
		$("#include").css("display", "none");
		$("#divGridSolicitudesUno").css("display", "none");
		$("#divGridSolicitudesDos").css("display", "none");
		$("#divGridSolicitudesDos .ui-jqgrid-bdiv").css('height', '337px');
		$("#divGridSolServPericiales").css("display", "block");
		gridSolServPericiales();
		$("#divGridSolsGeneradas").hide();
	}

	function nuevaDenuncia(id) {
        var idExpediente;
        var numeroExpediente;
		////ya estaba
        var idNuevaDenuncia = 1;
      //variable que indica si es un ingreso o una consulta
        var ingresoDenuncia = false;

        var pantallaSolicitada=2;
        
    	$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/nuevoExpedienteDenuncia.do',
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			var option;
    			idExpediente=$(xml).find('expedienteDTO').find('expedienteId').text();
    			//alert(idExpediente);
    			numeroExpediente=$(xml).find('expedienteDTO').find('numeroExpediente').text();
    			//alert(numeroExpediente);
    		}
    		
    	});
		
    	idWindowNuevaDenuncia++;
		if (idWindowNuevaDenuncia>3){
			alert("Ya se abrio una ventana");
		}
		else{
			$.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Expediente: "+numeroExpediente, type:"iframe"});
	    	$.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/IngresarMenuIntermedio.do?idNuevaDenuncia='+idNuevaDenuncia +'&ingresoDenuncia='+ingresoDenuncia +'&pantallaSolicitada='+pantallaSolicitada+'" width="1430" height="670" />');
		}

		//$("#gview_gridDetalleFrmPrincipal .ui-jqgrid-bdiv").css('height', '150px');
		//$("#divGridSolicitudesSecundario .ui-jqgrid-bdiv").css('height', '150px');
		//$("#include").css("display", "block");																																			
    }
    
    function muestraAcordeon(){
        
    	$("#gview_gridDetalleFrmPrincipal .ui-jqgrid-bdiv").css('height', '250px');
		$("#divGridSolicitudesSecundario .ui-jqgrid-bdiv").css('height', '250px');
		$("#include").css("display", "block");
   	}

	function pdf() {
		
		$.newWindow({id:"iframewindowpdf", statusBar: true, posx:200,posy:50,width:900,height:600,title:"Designar Abogado Defensor", type:"iframe"});
	    $.updateWindowContent("iframewindowpdf",'<iframe src="<%= request.getContextPath() %>/resources/images/Denuncia en Atenci&oacute;n Temprana _JAS.pdf" width="900" height="600" />');		
	}

	function gridSolServPericiales(){
		jQuery("#gridDetalleSolServPericiales").jqGrid({ 
			url:'<%= request.getContextPath()%>/.do', 
			datatype: "xml", 
			//Expediente   |   Fecha remitido    | Denunciante     |     Delito principal     |      Origen      |     Estatus 
			colNames:['Expediente',' Fecha Remitido','Denunciante', 'Delito Principal', 'Origen','Estatus'], 
			colModel:[ 	{name:'NumeroExpediente',index:'numeroExpediente', width:120,resizable:true },
			           	{name:'NombreSolicitante',index:'nombreSolicitante', width:120,resizable:true }, 
						{name:'FechaElaboracion',index:'fechaElaboracion', width:120,resizable:true }, 
						{name:'FechaVencimiento',index:'fechaVencimiento', width:120,resizable:true },
						{name:'Especialidad1',index:'Especialida1d', width:120,resizable:true },
						{name:'Especialidad',index:'Especialidad', width:120,resizable:true }, 
					],
			pager: jQuery('#pagerSolServPericiales'),
			rowNum:10,
			rowList:[10,20,30],
			
			autoheight:true,
			height:400,
			
			sortname: 'numeroExpediente',
			viewrecords: true,
			sortorder: "desc"
	}).navGrid('#pagerSolServPericiales',{edit:false,add:false,del:false});
}
	/*
	 *Funcion para consultar los tipos de solicitud y generar 
	 * el arbol dinamico de los tipos de solicitud en el menu izquierdo
	 * param - nombre del elemento en el que se construira de manera dinamica
	 * los tipos de solicutd
	 */
	function consultarTiposSolicitudPorAreaDelFuncionarioGen(idDivArbol,idArea)
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
					trTabla = trTabla + '<td><span><img src="<%=request.getContextPath()%>/resources/css/treeview/images/folder-closed.gif" width="16" height="16"/><a onclick="cargaGridSolsGeneradas('+$(this).find("idCampo").text()+',0)">'+$(this).find("valor").text()+'</a></span></td>';
					trTabla = trTabla + '</tr>';
					$('#'+idDivArbol).append(trTabla);
				});
			}
		});
	}
	
	/*
	*Funcion para realizar la consulta del grid de solicitudes por Atender
	*/
	function cargaGridSolsGeneradas(tipoSolicitud,idArea)
	{
		jQuery("#gridSolsGeneradas").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultaSolsGeneradas.do?tipoSoliciutd='+tipoSolicitud+'&idArea='+idArea+'&estatus=<%=EstatusSolicitud.ABIERTA.getValorId()%>,<%=EstatusSolicitud.EN_PROCESO.getValorId()%>',datatype: "xml" });
		$("#gridSolsGeneradas").trigger("reloadGrid");
		activaGridSolsGeneradas();
	}

function visorLeyesCodigos() {
																// z-index: 103; left: 253px; top: 109px; width: 803px; height: 498px;
		$.newWindow({id:"iframewindowRestaurativa", statusBar: true, posx:255,posy:111,width:816,height:498,title:"Leyes y C&oacute;digos", type:"iframe"});
	    $.updateWindowContent("iframewindowRestaurativa",'<iframe src="<%= request.getContextPath() %>/detalleLeyesyCodigos.do" width="816" height="498px" />');
	    		
	}

	/*
	*Funcion que llama a la funcionalidad para generar un visualizador de imagen  $('#imageViewer').click(generaVisorGraficaView);
	*/
	function generaVisorGraficaView() {
		$.newWindow({id:"iframewindowWindowImageViewer", statusBar: true, posx:81,posy:111,width:1140,height:400,title:"Visor de imagenes", type:"iframe"});
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
<div class="ui-layout-west">

	<div class="header">&nbsp;</div>

	<div class="content">
		<div id="accordionmenuprincipal">
	<!-- 	<h3><a href="#" onclick="activaSecundario()"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Expedientes Remitidos</a></h3>
			<div>
				<ul id="seccion11tree" class="filetree">
					<li class="closed" ><span id="casos_unidad_inv" class="folder" onclick="activaDos()">Por Unidad de Investigaci&oacute;n</span>
						<ul>
						
						</ul>
					</li>
					<li class="closed" ><span id="casos_at_penal" class="folder" onclick="activaDos()">Por Atenci&oacute;n Temprana Penal</span>
						<ul>
						
						</ul>
					</li>
				</ul>
			</div>
			<h3><a href="#"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Expedientes Propios</a></h3>
			<div>			
				<ul id="seccion1tree" class="filetree">
					
							<li class="closed" id="caso1"><span class="folder">Por Atender</span>
								
							</li>
							<li class="closed" id="caso3"><span class="folder">En Proceso</span>
								
							</li>
							<li class="closed" id="caso4"><span class="folder">En Seguimiento de Acuerdo</span>
								
							</li>
							<li class="closed" id="caso5"><span class="folder">A Unidad de Investigaci&oacute;n</span>
								
							</li>
							<li class="closed" id="caso6"><span class="folder">Cerrados</span>
								
							</li>
						
				</ul>	
			</div>
			<h3><a href="#" onclick="activaDos()"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Expedientes por Asignar</a></h3>
			<div>
			</div>-->	
			<!-- <h3><a href="#" onclick="activaDos()"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Expedientes del &Aacute;rea</a></h3>
			<div>
				AMP: 
				<select id="cbxExpPendientes">
					<option value="-1" selected="selected">-Seleccione-</option>
				</select>
				<br/>
				Estatus: 
				<select id="cbxEstatus">
					<option value="-1" selected="selected">-Seleccione-</option>
				</select>
			</div>
			<h3><a href="#"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png" id="botpenal" width="15" height="15">&nbsp;Solicitudes Generadas</a></h3>
			<div>
				<table width="100%" border="0" bordercolor="#FFFFFF" cellspacing="0" cellpadding="0" style="cursor:pointer" id="tableSolsGeneradas">
				</table>
			</div>-->
<!--			<h3 ><a id="" href="#" onclick="generaVisorGraficaView()"><img src="<%=request.getContextPath() %>/resources/images/icn_carpprincipal.png"  width="15" height="15">Gr&aacute;ficas y Reportes</a></h3>
				<div>		
						<input type="button" value="Ver Grafica" id="imageViewer" name="imageViewer"/>	
					<ul id="seccion3treePJENC" class="filetree">
						<li class="closed" id="casosPJENC"><span class="folder">Casos</span>
							 Aqui se agregan los casos y expedientes dinamicamente 
						</li>
					</ul>	
				</div>	-->
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
				<h4>
					<a href="#">Clima</a>
				</h4>
				<div align="left">
					<div align="left" id="test"></div>
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
			<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" background=<%=request.getContextPath()%>/resources/images/top_gral.jpg height="100%">
				  <TBODY>
					  <TR>
					   <TD width=100 align=left valign="middle"><img src="<%=request.getContextPath()%>/resources/images/logo_gral.png"></TD>
                        <TD width=301 align=left valign="middle"><img src="<%=request.getContextPath()%>/resources/images/logo_cooampjusalt.png"></TD>
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
					            	<td width="107" class="txt_menu_top">Cerrar sesi&oacute;n</td>
					            	<td width="42" class="txt_menu_top"><a href="#" onclick='$("#dialog-logout").dialog( "open" );'><img src="<%=request.getContextPath()%>/resources/images/btn_cerrar.png" width="29" height="26" border="0"></a></td>
					            	<!-- 
					              <td width="53" class="txt_menu_top">Buscar</td>
					              <td width="147"><img src="<%=request.getContextPath()%>/resources/images/icn_buscar.png" width="22" height="23" border="0"></td>
					               -->
					            </tr>
					          </table>
					            <label for="textfield"></label></td>
					          <td width="204"><table width="89" border="0" cellspacing="0" cellpadding="0">
					            <tr>
					              <td width="47">Ayuda</td>
					              <td width="42"><a href="#"><img src="<%=request.getContextPath()%>/resources/images/btn_ayuda.png" width="29" height="26" border="0"></a></td>
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
	<ul class="toolbar ui-widget-header">
		<div id="menu_head">
			<li id="tbarBtnHeaderZise" class="first"><span></span></li>
			<li id="tbarBtnAsignar" class="first"><span></span>Asignar Permisos a Subordinados&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_dctowri.png" width="10" height="16"></li>
<!--			<li id="tbarBtnNuevo" class="first"><span></span>Crear Expediente </li>-->
			<!--<li id="tbarBtnLectura"><span></span>Solicitar Asignaci&oacute;n de N&uacute;mero de Caso</li>-->
<!--			<li id="tbarBtnImpresoras"><span></span>Vincular Expediente a Caso</li>-->
			<!-- <li id="tbarBtnNuvaDenuncia"><span></span>Impresora</li>
			<li id="buscarExpediente3" ><span></span>Scanner</li> -->
			<!-- <li id="buscarCaso3" ><span></span>Configurar</li> -->
			<!-- <li id="generarDocumento3">Generar Documento</li> -->
		</div>
		<div id="menu_config">
			<!--<li><a href="#" id="buscarCaso">Buscar Caso</a></li>-->
			<!--  <li id="buscarCaso">Buscar Caso&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_busca2.png" width="15" height="16"></li>-->
			<li id="buscarExpediente">Buscar Expediente&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_busca3.png" width="15" height="16"></li>
<!--			<li id="generarDocumento">Generar Documento</li>-->
<!--			<li>Solicitudes</li>-->
			<!--<li class="last">config01</li>
			<li class="last">config02</li>
			<li class="last">config03</li>
			<li class="last">config04</li>-->
			<li id="verde">Configuraci&oacute;n&nbsp;<img src="<%= request.getContextPath() %>/resources/images/icn_config.png" width="15" height="16"></li> 
		</div>
	</ul>
</div>


<div class="ui-layout-south">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="ui-widget-header ui-state-hover">
		    <tr>
		    <td height="15">&nbsp;</td>
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
					<table id="gridDetalleFrmPrincipal"></table>
					<div id="pager1"></div>
				</div>
				<div id="divGridSolicitudesSecundario" style="display: none;">
					<table id="gridDetalleFrmSecundario"></table>
					<div id="pager2"></div>
				</div>
				<div id="divGridSolicitudesUno" style="display: none;">
					<table id="gridDetalleFrmUno"></table>
					<div id="pager3"></div>
				</div>
				<div id="divGridSolicitudesDos" style="display: none;">
					<table id="gridDetalleFrmDos"></table>
					<div id="pager4"></div>
				</div>
				
				<div id="divGridSolServPericiales" style="display: none; ">
						<table id="gridDetalleSolServPericiales"></table>
						<div id="pagerSolServPericiales"></div>
				</div>
					<div id="divGridSolsGeneradas" style="display: none;">
				 	<table id="gridSolsGeneradas" width="100%" height="100%"></table>
					<div id="pagerGridSolsGeneradas"></div>
				</div>
			<div id="include" style="display: none;">
				<table width="75%" border="0">
					<tr valign="top">
						<td width="75%" valign="top">
							<div id="iRepLegalAccordionPane" style="width: 75%" >
					            <dl>
					                 <dt>Denuncia</dt>
					                <dd>
					                	<table border="0" cellpadding="0" cellspacing="0">
					                		<tr>
					                			<td>
					                				<img onclick="pdf()" src="<%= request.getContextPath()%>/resources/images/&iacute;ndicePDF.jpg" width="50px" height="50px"/>
					                			</td>
					                		</tr>
					                	</table>
					                </dd>
					                <dt>Oficios</dt>
					                <dd>
					                	<table border="0" cellpadding="0" cellspacing="0">
					                		<tr>
					                			<td>
					                				<a >Oficio de Canalizaci&oacute;n</a>
					                			</td>
					                		</tr>
					                	</table>
					                </dd>
					            </dl>
							</div>
						</td>
					</tr>
				</table>
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
					<option value="1">Minutos</option>
					<option value="2">Horas</option>
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
