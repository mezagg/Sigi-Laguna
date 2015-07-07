<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/consultaDeAudiencias.js?n=1"></script>
<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>


<!--Hoja de estilos para el grid-->
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />


<script type="text/javascript">

	var gridRecargaExpediente=true;	
	var COORDINADOR_AMP_GENERAL = 10;
	var pantallaSolicitada=COORDINADOR_AMP_GENERAL;
	var idWindowNuevaDenuncia=1;
	//var numExpAlter=null;

	var ingresoDenuncia = true;
	var fechaInicio="";
	var fechaFin="";
	var numeroExpediente = "";
	
	$(document).ready(function() {
		try{
			//Se da click a la pestania de "Tablero de control" pada que quede en la parte de arriba del menu y no hasta abajo
			$("#menu_230").click();
		}catch(e){};
		
		//consultaGeneralExpediente();
		//$("#divExpedientes").show();
		//ajustarGridAlCentro($("#gridDetalleFrmPrincipal"));
	});
	
	function popUpTipoBusquedaModalXNumeroExpediente(){
		
		var titulo="Consulta por N�mero de Expediente";
		
		$("#numeroExpediente").val("");
		$("#divBusquedaExpediente").dialog("open");
	  	$("#divBusquedaExpediente").dialog({ autoOpen: true, 
			modal: true, 
		  	title: titulo, 
		  	dialogClass: 'alert',
		  	position: [500,220],
		  	width: 450,
		  	height: 300,
		  	maxWidth: 350,
		  	buttons:{"Realizar b�squeda":function() {
	  				consultaGeneralExpediente(3);
		  			$(this).dialog("close");
		  		},
				"Cancelar" : function() {
					$(this).dialog("close");
				}
		  	}
		});
	}
	
	function modalFechaDeNumeroExpediente(){

		$('#fechaInicio').val('');
		$('#fechaFin').val('');
		
		var dates =	$("#fechaInicio, #fechaFin").datepicker(
			{
				dateFormat: 'dd/mm/yy',
				yearRange: '1900:2100',
				changeMonth: true,
				changeYear: true,
				numberOfMonths: 1,
				onSelect: function( selectedDate ) {
					var option = this.id == "fechaInicio" ? "minDate" : "maxDate",
					instance = $( this ).data( "datepicker" ),
					date = $.datepicker.parseDate(
					instance.settings.dateFormat ||
					$.datepicker._defaults.dateFormat,
					selectedDate, instance.settings );
					dates.not( this ).datepicker( "option", option, date );
				},
				showOn: "button",
				buttonImage:  contextoPagina + "/resources/images/date.png",
				buttonImageOnly: true			
			}
		);
		
		//abre la ventana de detalle de la persona
		$("#busquedaFecha").dialog("open");
		$("#busquedaFecha").dialog({ autoOpen: true, 
	  		modal: true, 
	  		title: 'Buscar por Fecha', 
	  		dialogClass: 'alert',
	  		width: 380,
	  		height: 260,
	  		maxWidth: 1000,
	  		buttons:{"Aceptar":function() {
	  				
	  					validaCamposFecha($("#fechaInicio").val(), $("#fechaFin").val());
	  					
	  					if(validaFecha==true){
  							consultaGeneralExpediente(2);
	  						$(this).dialog("close");
				  		}
	  					else{
	  						alert("Favor de verificar el rango seleccionado");
	  					}
	  					
	  				},
	  				"Cancelar":function() {
		  				$(this).dialog("close");
	  				}
	  		}
	  	});				
	}

	function consultaGeneralExpediente(selectorExpediente){
	
		var fechaInicio="";
		var fechaFin="";
		var numeroExpediente = "";
		var consultarCanalizacionesNoAtendidas = false;
		
		// selectorExpediente = 2 -> b�squeda por fechas
		// selectorExpediente = 3 -> b�squeda por N�mero de Expediente
		// selectorExpediente = cualquier otro valor -> b�squeda general
		
		if(selectorExpediente == 2){
			fechaInicio= $('#fechaInicio').val();
			fechaFin=    $('#fechaFin').val();
		}
		else if(selectorExpediente == 3){
			numeroExpediente = $("#numeroExpediente").val();
		}
		else if(selectorExpediente == 4){
			consultarCanalizacionesNoAtendidas = true;
		}

		if(gridRecargaExpediente==true){
    		
    		jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
    			url: contextoPagina + '/busquedaCanalizadosCoordinadorAmpGeneral.do?inicio='+fechaInicio+'&fin='+fechaFin+'&numeroExpediente='+numeroExpediente+'&consultarCanalizacionesNoAtendidas='+consultarCanalizacionesNoAtendidas+'', 
    			datatype: "xml", 
    			colNames:['N\u00FAmero de Expediente','Tipo','Fecha', 'Denunciante', 'Delito','Origen','Estatus'], 
    			colModel:[ 	{name:'Detalle',index:'NumeroExpediente', width:120}, 
    			           	{name:'Tipo',index:'Tipo', width:120, align:'center', hidden:true},
    						{name:'Fecha',index:'Fecha', width:70}, 
    						{name:'Nombre', sortable: false , width:70}, 
    						{name:'Resumen',sortable: false , width:110},
    						{name:'Origen',index:'Origen', width:110},
    						{name:'Estatus',index:'Estatus', width:110}
    					],
    			pager: jQuery('#pagerGridDetalleFrmPrincipal'),
    			rowNum:10,
    			rowList:[10,20,30,40,50,60,70,80,90,100],
    			autowidth: true,
    			shrinkToFit: true,
    			sortname: 'Fecha',
    			viewrecords: true,
    			ondblClickRow: function(id) {
    				consultaExpedienteGrid(id);
    			},
    			sortorder: "desc"
    		}).navGrid('#pagerGridDetalleFrmPrincipal',{edit:false,add:false,del:false});

    		gridRecargaExpediente=false;
		}
		else{			
			jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam', {url: contextoPagina +
				'/busquedaCanalizadosCoordinadorAmpGeneral.do?inicio='+fechaInicio+'&fin='+fechaFin+'&numeroExpediente='+numeroExpediente+'&consultarCanalizacionesNoAtendidas='+consultarCanalizacionesNoAtendidas+'',datatype: "xml" });
			$("#gridDetalleFrmPrincipal").trigger("reloadGrid");
		}		
	}
	
    function consultaExpedienteGrid(id) {
    	idWindowNuevaDenuncia++;
		$.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Carpeta de investigaci�n: ", type:"iframe"});
		$.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/BusquedaExpediente.do?abreenPenal=abrPenal&ingresoDenuncia='+ingresoDenuncia +'&idNumeroExpediente='+id+'&pantallaSolicitada='+pantallaSolicitada+'" width="1430" height="670" />');
		$("#" +"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia + " .window-maximizeButton").click();		
	}

	function regresaGrid(){
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam', {url: contextoPagina +
			'/busquedaCanalizadosCoordinadorAmpGeneral.do?inicio='+fechaInicio+'&fin='+fechaFin+'&numeroExpediente='+numeroExpediente+'',datatype: "xml" });
		$("#gridDetalleFrmPrincipal").trigger("reloadGrid");
	}
	
    function tituloVentana(num){
		$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Carpeta de investigaci�n: "+num);
	}
    function cambiarResponsableExpediente() {
		customVentana("cambiarResponsableExpediente", "Cambiar Responsable A Expediente", "/cambiarResponsableExpediente.do?ampGeneral=1");
	}
    
    function generaVisorReporteView() {
		$.newWindow({id:"iframewindowWindowReportViewer", statusBar: true, posx:85,posy:86,width:1140,height:499,title:"Indicadores", type:"iframe"});
	    $.updateWindowContent("iframewindowWindowReportViewer",'<iframe src="<%=request.getContextPath()%>/visorReportesAdmin.do" width="1140" height="499" />');
	    $("#" +"iframewindowWindowReportViewer" + " .window-maximizeButton").click();
	}
    
    function generaVisorGraficaView() {
		$.newWindow({id:"iframewindowWindowGraficaViewer", statusBar: true, posx:85,posy:86,width:1140,height:499,title:"Tablero de Control", type:"iframe"});
	    $.updateWindowContent("iframewindowWindowGraficaViewer",'<iframe src="<%=request.getContextPath()%>/VisorGraficas.do" width="1140" height="499" />');
	    $("#" +"iframewindowWindowGraficaViewer" + " .window-maximizeButton").click();
	}
    
    
	/*
	*Funcion que oculta o muestra los grids, recibe como parametro
	*el nombre del grid que va a mostrar, y todos los demas, se 
	*ocultaran
	*/ 
	function ocultaMuestraGrids(nombreGrid){
	
		$("#divExpedientes").hide();
		$("#divGridAudiencias").hide();
		
		switch (nombreGrid){		
	        case "gridDetalleFrmPrincipal":
		    	$("#divExpedientes").show();
	    		break;
	        case "gridAudiencias":
		    	$("#divGridAudiencias").show();
	    		break;        
	    }		
		restablecerPantallas();
	}
	
	/**
	 * Permite redimensionar las pantallas en base al espacio
	 */
	function restablecerPantallas(){
		ajustarGridAlCentro($("#gridDetalleFrmPrincipal"));
		ajustarGridAlCentro($("#gridAudiencias"));	
	}

	</script>	
	
	<div id="mainContent">
		<div class="ui-layout-center">
			<div class="ui-layout-content">
				<div class=ui-layout-north>
					 <div id="divExpedientes">
						<table id="gridDetalleFrmPrincipal"></table>
						<div id="pagerGridDetalleFrmPrincipal" style=" vertical-align: top;"></div>
					</div>
					
					<!--Espacio para el grid de audiencias-->
					<div id="divGridAudiencias">
						<table id="gridAudiencias"></table>
						<div id="pagerGridAudiencias"></div>
					</div>	
					
				</div>
			</div>
		</div>
	</div>

	<!-- Comienza div para mostrar la ventana para ingresar el N�mero de Expediente -->
	<div id="divBusquedaExpediente" style="display: none">
		<table width="400" cellspacing="0" cellpadding="0" height="150">
			<tr>
				<td width="45">&nbsp;</td>
				<td width="300">&nbsp;</td>
				<td width="45">&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"><strong>Ingrese el N&uacute;mero de Expediente: </strong></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"><input type="text" class="" size="30" maxlength="30" id="numeroExpediente" /></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<!-- Termina div para mostrar la ventana para ingresar el N�mero de Expediente -->	

	<!-- Comienza div para mostrar la ventana para ingresar las fechas -->
	<div id="busquedaFecha" style="display: none">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td width="153">&nbsp;</td>
				<td width="153">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<strong>Fecha Inicio: </strong>
					<input type="text" id="fechaInicio" size="20"/>
				</td>
			</tr>
			<tr>
				<td align="center">&nbsp;</td>
				<td align="center">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<strong>&nbsp;&nbsp;&nbsp;Fecha Fin: </strong> 
					<input type="text" id="fechaFin" size="20" />
				</td>
			</tr>
		</table>
	</div>
	<!-- Termina div para mostrar la ventana para ingresar las fechas -->
	