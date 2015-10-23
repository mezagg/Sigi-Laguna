<%@page import="mx.gob.segob.nsjp.comun.enums.involucrado.SituacionJuridica"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TipoMandamiento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.EstatusPermisosAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@page	import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@page import=" mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Atenci&oacute;n a Solicitudes</title>


<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />
<!--Se importan los scripts necesarios-->
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>		
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>


<!--<jsp:include page="/WEB-INF/paginas/desarrolloJAVS/funcionesComunesJAVS.jsp" flush="true"></jsp:include>-->
<script type="text/javascript">
	<%
		UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
		String rolActivo = "";
		if (usuario != null 
				&& usuario.getRolACtivo() != null 
				&& usuario.getRolACtivo().getRol() != null
				&& usuario.getRolACtivo().getRol().getRolId() != null){
			rolActivo = usuario.getRolACtivo().getRol().getRolId().toString();
		}
	%>
	var contextoPagina = "${pageContext.request.contextPath}";

	/**
	* Variables para solicitudes de audiencia
	*/
	
	var tipoAudGlob;
	var fechaGlob;
	var idNuevaCalidad;
	var idInvolucrado;
	var idEstatusAudiencia=0;
	idAudiencia = <%=request.getParameter("idAudiencia")%>;	    
	var recargaPermisosAudiencias=0;
	var numeroPermisos=0;
	var estado=0;
	var seleccionado=0;		

	var idWindowVisorMedidasCautelaresPJENC=1;
	var numeroExpediente = "";
	var numeroCausa = "";
	var numeroExpedienteId = 0;
	var numeroDeCaso = "";
	var banderaPV = 0;
	var idExpediente = "";
	
	//Permite ocultar/mostrar los botones relacionados a la Notificacion
	var desactivarBotonesEnNotificaciones = true;
	
	//Bandera para recargar el grid de notificaciones
	var primeraConsulta="true";
	
	var contextoPagina = "${pageContext.request.contextPath}";
	var rolActivo = '<%=rolActivo%>';
	var extensionesPermitidasAudio = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getExtensionesPermitidasAlAdjuntarAudio() %>';
	var extensionesPermitidasImagen = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getExtensionesPermitidasAlAdjuntarImagen() %>';
	
	$(document).ready(function() {

	jQuery(document).ajaxStop(jQuery.unblockUI);

	var dates =	$("#fInicioSentencia, #fFinSentencia").datepicker(
		{
			numberOfMonths: 1,
			changeMonth: true,
			changeYear: true,
			yearRange: new Date().getFullYear() + ":+200",
    		showOn: "both",
			onSelect: function( selectedDate ) {
				var option = this.id == "fInicioSentencia" ? "minDate" : "maxDate",
				instance = $( this ).data( "datepicker" ),
				date = $.datepicker.parseDate(
				instance.settings.dateFormat ||
				$.datepicker._defaults.dateFormat,
				selectedDate, instance.settings );
				dates.not( this ).datepicker( "option", option, date );									
			},
			showOn: "button",
			buttonImage: "<%=request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		}
	);

		//crea las tabs
		$( "#tabsprincipalconsulta" ).tabs();
		//Se cargan funciones de la ceja solicitudes en audiencia
		consultaDetalleEvento(idAudiencia);
		consultarJuecesDeAudiencia(idAudiencia);
		
		//Se cargan funciones de la ceja solicitudes en audiencia
		cargarGridSolicitudesTranscripcionAudiencia(idAudiencia,'<%=TiposSolicitudes.TRANSCRIPCION_DE_AUDIENCIA.getValorId()%>');
		cargarGridSolicitudesAudioVideoAudiencia(idAudiencia,'<%=TiposSolicitudes.AUDIO_VIDEO.getValorId()%>');
		
		//Se cargan funciones de la ceja estado de intervinientes
		cargarGridProbResponsable(idAudiencia);
		
		//Se cargan funciones de la ceja mandamientos judiciales
		cargaResolutivosAudiencia(idAudiencia);
		
		//TODO INVOCAR AL VISOR DE MANDAMIENTOS
		$("#btnGenerarMandamiento").click(abrirMandamientoJudicial);

		
		$("#ordenar").click(ordenarSolicitudes);
		
		
		cargaTipoMandamiento();
		cargaTipoSentencia();
		consultarImputadosParaMandamientoXAudiencia();
		$('#tipoMandamiento').change(controlTipoMandamiento);
		
		
		$("#btnAdminMediCaute").click(mostrarVentanaInvolucradosCausa);
		
		
		if (rolActivo != '<%=Roles.ENCARGADOCAUSA.getValorId()%>' ){
			$("#tabTabsDocs").hide();
		}else{
			/*
			*Llamada para la ceja de Documentos
			*/
			
			$("#linkDocs").one("click", function() {
				jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
					url:'<%=request.getContextPath()%>/consultarDocumentosPorAudiencia.do?idAudiencia='+idAudiencia,
					datatype: "xml",
					colNames:['&Aacute;rea del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento', 'Documento', 'Es Parcial'],
					colModel:[ 	{name:'area',index:'da', width:200, align:'center'},
								{name:'FechaActividad',index:'fechaActividad', width:170, align:'center', hidden:true},							
								{name:'NombreActividad',index:'nombreActividad', width:400, align:'center', hidden:true},
					           	{name:'Tipo',index:'tipo', width:155, align:'center'}, 
								{name:'Nombre',index:'nombre', width:255, align:'center'},
					           	{name:'Fecha',index:'fecha', width:170, align:'center'},
					           	{name:'Documento',index:'documento', width:170, align:'center'},
					           	{name:'EsParcial',index:'esParcial', align:'center', hidden:true}
								],
					pager: jQuery('#pager1Documentos'),
					rowNum:10,
					rowList:[10,20,30,40,50,60],
					autowidth: false,
					width:1100,
					sortname: 'turno',
					viewrecords: true,
					id: 'divgrid',
					ondblClickRow: function(id){
						var retd = jQuery("#gridDetalleFrmPrincipal").jqGrid('getRowData',id);
						if(retd.EsParcial){
							noEsParcial = retd.EsParcial.indexOf('false');
							if(noEsParcial > 0){
								consultaPDF(id);
							}
						}
					},
					sortorder: "desc"
				}).navGrid('#pager1Documentos',{edit:false,add:false,del:false});
				$("#gview_gridDetalleFrmPrincipal .ui-jqgrid-bdiv").css('height', '500px');
			});
		}
		
		$('#btnAdjuntarDocumento').click(abreVentanaAdjuntarDocumentoAAudiencia);
					
	});
	
//***********************************************************FUNCIONALIDAD COMUN PARA TODAS LAS CEJAS**************************************************************/
 	
	/*
	*Funcion que consulta el detalle del evento y llena 
	*los campos de la TAB Detalle evento
	*/
	function consultarJuecesDeAudiencia(idAudiencia){
		
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/consultarJuecesDeAudiencia.do',
			data: 'audienciaId='+ idAudiencia, 
			//data: 'audienciaId='+3,
			async: false,
			dataType: 'xml',
			success: function(xml){
				var errorCode;
				errorCode=$(xml).find('response').find('code').text();
				if(parseInt(errorCode)==0){
					$(xml).find('listaJueces').find('juez').each(function(){
						$('#juecesAudienciaPJENS').append('<option value="' + $(this).find('involucradoId').text() + '">' + $(this).find('nombre').first().text()+" "+$(this).find('apellidoPaterno').first().text()+" "+$(this).find('apellidoMaterno').first().text() + '</option>');
						$('#juecesAudienciaIntervPJENC').append('<option value="' + $(this).find('involucradoId').text() + '">' + $(this).find('nombre').first().text()+" "+$(this).find('apellidoPaterno').first().text()+" "+$(this).find('apellidoMaterno').first().text() + '</option>');
						$('#juecesAudienciaMandamientosPJENC').append('<option value="' + $(this).find('involucradoId').text() + '">' + $(this).find('nombre').first().text()+" "+$(this).find('apellidoPaterno').first().text()+" "+$(this).find('apellidoMaterno').first().text() + '</option>');
					});
				}
			}
		});
	}
 	
 	
 	/*
	*Funcion que consulta el detalle del evento y llena 
	*los campos de la TAB Detalle evento
	*/
	function consultaDetalleEvento(idAudiencia){

		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/detalleAudienciasDelDiaPJENS.do',
			data: 'idEvento='+ idAudiencia, 
			async: false,
			dataType: 'xml',
			success: function(xml){

					//limpia todos los capos de las diferentes cejas
    				limpiaDatosDetalleEvento();
					
    				idEstatusAudiencia = $(xml).find('estatusAudiencia').find('idCampo').first().text();    				

 					//Ceja Solucitudes en audiencia   				
    			    tipoAudGlob	= $("#tipoAudienciaPJENS").val($(xml).find('audienciaDTO').find('tipoAudiencia').find('valor').first().text());
					    			    
    				$("#numCasoPJENS").val($(xml).find('audienciaDTO').find('expediente').find('casoDTO').find('numeroGeneralCaso').first().text());
    				numeroDeCaso = $(xml).find('audienciaDTO').find('expediente').find('casoDTO').find('numeroGeneralCaso').first().text();
    				$("#numExpPJENS").val($(xml).find('numeroExpediente').first().text());
    				
    				numeroExpediente = $(xml).find('numeroExpediente').first().text();
    				numeroCausa = numeroExpediente;
    				numeroExpedienteId = $(xml).find('numeroExpedienteId').first().text();
    				
    				$("#audienciaPJENS").val($(xml).find('id').first().text());
    				$("#caracterPJENS").val($(xml).find('caracter').first().text());

    				//Ceja Estado Intervinientes   				
    			    $("#tipoAudienciaIntervPJENC").val($(xml).find('audienciaDTO').find('tipoAudiencia').find('valor').first().text());
    				$("#audienciaIntervPJENC").val($(xml).find('id').first().text());			

    				//Ceja Mandamientos Judiciales   				
    			    $("#tipoAudienciaMandamientosPJENC").val($(xml).find('audienciaDTO').find('tipoAudiencia').find('valor').first().text());
    				$("#audienciaMandamientosPJENC").val($(xml).find('id').first().text());			
      				 
    				var fecha= $(xml).find('fechaEvento').text();
					formateaFechaHora(fecha);

					var fechaFin= $(xml).find('fechaHoraFin').text();
					formateaFechaHoraFin(fechaFin);
    				//$("#juezPJENS").val($(xml).find('juez').first().text());

    				idExpediente = $(xml).find('audienciaDTO').find("expediente").find("expedienteId").first().text();
			}
		});
	}

	
	/*
	*Funcion que limpia las cajas de texto
	*antes de llenarlas con los datos de la
	*nueva consulta
	*/	
	function limpiaDatosDetalleEvento(){

		//Limpia datos de la ceja solicitudes de audiencia
		$("#tipoAudienciaPJENS").val("");
		$("#numCasoPJENS").val("");
		$("#numExpPJENS").val("");
		$("#audienciaPJENS").val("");
		$("#caracterPJENS").val("");
		$("#fechaAudienciaPJENS").val("");
		$("#horaAudienciaPJENS").val("");
		//$("#juezPJENS").val("");

		//Limpia datos de la ceja solicitudes de audiencia
		$("#audienciaIntervPJENC").val("");
		$("#tipoAudienciaIntervPJENC").val("");
		$("#juecesAudienciaIntervPJENC").val("");
		$("#fechaAudienciaIntervPJENC").val("");
		$("#horaAudienciaIntervJENC").val("");
		$("#fechaFinAudienciaIntervPJENC").val("");
		$("#horaFinAudienciaIntervPJENC").val("");

		//Limpia datos de la ceja Mandamientos judiciales
		$("#audienciaMandamientosPJENC").val("");
		$("#tipoAudienciaMandamientosPJENC").val("");
		$("#juecesAudienciaMandamientosPJENC").val("");
		$("#fechaAudienciaMandamientosPJENC").val("");
		$("#horaAudienciaMandamientosPJENC").val("");
		$("#fechaFinAudienciaMandamientosPJENC").val("");
		$("#horaFinAudienciaMandamientosPJENC").val("");
	}

	
	/*
	*Funcion que da formato a la fecha
	*/
	function formateaFechaHora(fechaHora){
		
		var fechaFrac = fechaHora.split(" ")[0];
		var horaFrac = fechaHora.split(" ")[1];

		horaFracPos = horaFrac.indexOf(":", 0);
		hora=horaFrac.substring(0,horaFracPos+3);
		//Ceja solicitudes en audiencia		    				
		$("#fechaAudienciaPJENS").val(fechaFrac);
		$("#horaAudienciaPJENS").val(hora);
		//Ceja estado intervinientes
		$("#fechaAudienciaIntervPJENC").val(fechaFrac);
		$("#horaAudienciaIntervJENC").val(hora);
		//Ceja estado intervinientes
		$("#fechaAudienciaMandamientosPJENC").val(fechaFrac);
		$("#horaAudienciaMandamientosPJENC").val(hora);		
	}


	/*
	*Funcion que da formato a la fecha
	*/
	function formateaFechaHoraFin(fechaHora){
		
		var fechaFrac = fechaHora.split(" ")[0];
		var horaFrac = fechaHora.split(" ")[1];

		horaFracPos = horaFrac.indexOf(":", 0);
		hora=horaFrac.substring(0,horaFracPos+3);
		//Ceja solicitudes en audiencia		    				
		$("#fechaFinAudienciaPJENS").val(fechaFrac);
		$("#horaFinAudienciaPJENS").val(hora);
		//Ceja estado intervinientes
		$("#fechaFinAudienciaIntervPJENC").val(fechaFrac);
		$("#horaFinAudienciaIntervPJENC").val(hora);
		//Ceja estado intervinientes
		$("#fechaFinAudienciaMandamientosPJENC").val(fechaFrac);
		$("#horaFinAudienciaMandamientosPJENC").val(hora);		
	}
	
//***********************************************************FUNCIONALIDAD CEJA SOLICITUDES EN AUDIENCIA**************************************************************/
	
	
	/**
	*Funcion que carga el grid con las solicitudes de transcripcion
	*de audiencia
	*/
	gridTranscripcionCargado = false;
	function cargarGridSolicitudesTranscripcionAudiencia(idAudiencia,enumTipoSolicitud){
		if(!gridTranscripcionCargado){
			jQuery("#gridSolTranscripcionAudienciaPJENC").jqGrid({
				url:'<%=request.getContextPath()%>/consultarSolicitudesTransAudioVideoPorEstatus.do?idAudiencia='+idAudiencia+'&enumTipoSolicitud='+enumTipoSolicitud+'',
				datatype: "xml", 
				colNames:['Nombre','Instituci&oacute;n','Ordenado','Atendido'], 
				colModel:[  {name:'NombreSol',index:'nombreSol', width:200, align:'center'},						
				           	{name:'Institucion',index:'institucion', width:150, align:'center'}, 
				           	{name:'Ordenado',index:'ordenado', width:70,align:'center'}, 
				           	{name:'Atendido',index:'Atendido', width:70, align:'center'},
						],
				pager: jQuery('#pagerGridSolTranscripcionAudienciaPJENC'),
				rowNum:10,
				rowList:[25,50,100],
				width:490,
				sortname:'detalle',
				viewrecords:true,
				sortorder:"desc",
				caption:"Solicitantes",
				multiselect: true,
				gridComplete: function () {
					seleccionarItems($(this));
				},
				onPaging: function (a) {
					guardarItemsSeleccionados($(this)); 
				},
				onSortCol: function(){
					eliminarItemsSeleccionados($(this));
				}
			}).navGrid('#pagerGridSolTranscripcionAudienciaPJENC',{edit:false,add:false,del:false});
			
			
			gridTranscripcionCargado = true;
		}else{
			jQuery("#gridSolTranscripcionAudienciaPJENC").jqGrid('setGridParam',{url:'<%=request.getContextPath()%>/consultarSolicitudesTransAudioVideoPorEstatus.do?idAudiencia='+idAudiencia+'&enumTipoSolicitud='+enumTipoSolicitud,datatype: "xml" });
			$("#gridSolTranscripcionAudienciaPJENC").trigger("reloadGrid");
		}
		
	}


	/**
	*Funcion que carga el grid con las solicitudes audio video
	*de audiencia
	*/
	gridSolicitudesAVCargado = false;
	function cargarGridSolicitudesAudioVideoAudiencia(idAudiencia,enumTipoSolicitud){

		if(!gridSolicitudesAVCargado){
			
			jQuery("#gridSolAudioVideoAudienciaPJENC").jqGrid({ 
				url:'<%=request.getContextPath()%>/consultarSolicitudesTransAudioVideoPorEstatus.do?idAudiencia='+idAudiencia+'&enumTipoSolicitud='+enumTipoSolicitud,
				datatype: "xml", 
				colNames:['Nombre','Instituci&oacute;n','Ordenado','Atendido'], 
				colModel:[  {name:'NombreSol',index:'nombreSol', width:200, align:'center'},						
				           	{name:'Institucion',index:'institucion', width:150, align:'center'}, 
				           	{name:'Ordenado',index:'ordenado', width:70,align:'center'}, 
				           	{name:'Atendido',index:'Atendido', width:70, align:'center'},
						],
				pager: jQuery('#pagerGridSolAudioVideoAudienciaPJENC'),
				rowNum:10,
				rowList:[25,50,100],
				width:490,
				sortname:'detalle',
				viewrecords:true,
				sortorder:"desc",
				caption:"Solicitantes",
				multiselect: true,
				gridComplete: function () {
					seleccionarItems($(this));
				},
				onPaging: function (a) {
					guardarItemsSeleccionados($(this)); 
				},
				onSortCol: function(){
					eliminarItemsSeleccionados($(this));
				}
			}).navGrid('#pagerGridSolAudioVideoAudienciaPJENC',{edit:false,add:false,del:false});
			gridSolicitudesAVCargado=true;
		}else{
			jQuery("#gridSolAudioVideoAudienciaPJENC").jqGrid('setGridParam', 
					{url: '<%=request.getContextPath()%>/consultarSolicitudesTransAudioVideoPorEstatus.do?idAudiencia='+idAudiencia+'&enumTipoSolicitud='+enumTipoSolicitud,datatype: "xml" });
			$("#gridSolAudioVideoAudienciaPJENC").trigger("reloadGrid");
		}
		
		
	}

//***********************************************************FUNCIONALIDAD CEJA ESTADO DE INTERVINIENTES**************************************************************/
	
	/**
	*Funcion que carga el grid con el probable responsable y la lista de delitos
	*/
	function cargarGridProbResponsable(idAudiencia){
		
		jQuery("#gridProbResponsablePJENC").jqGrid({ 
			url:'<%=request.getContextPath()%>/consultarProbablesResponsablesPorVictimaYDelito.do?audienciaId='+idAudiencia+'',
			datatype: "xml", 
			colNames:['Nombre Probable Responsable','Nombre V&iacute;ctima(s)','Delito(s)','Calidad Actual','Nueva Calidad'], 
			colModel:[  {name:'NombreImputado',index:'nombreImputado', width:200, align:'center'},						
			           	{name:'NombreVictima',index:'nombreVictima', width:200, align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"';} }, 
			           	{name:'Delito',index:'delito', width:120,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"';} }, 
			           	{name:'CalidadActual',index:'calidadActual', width:150, align:'center'},
			           	{name:'NuevaCalidad',index:'NuevaCalidad', width:200, align:'center'},
					],
			pager: jQuery('#pagerGridProbResponsablePJENC'),
			rowNum:10,
			rowList:[25,50,100],
			width:820,
			sortname:'detalle',
			viewrecords:true,
			sortorder:"desc",
			caption:"Probable Responsable / Delito",
			onSelectRow: function(rowID) {
				idInvolucrado=rowID;
				var id2 = jQuery("#gridProbResponsablePJENC").jqGrid('getGridParam','selrow');
                var ret2 = jQuery("#gridProbResponsablePJENC").jqGrid('getRowData',id2);
                idNuevaCalidad= $('#'+id2+'_NuevaCalidad').val();
			},
			loadComplete : function(){
   				jsonParam = delitosPersonaAOrdenar();
   				limpiarJSON();
   				consultarNUSDeLosInvolucrados();
			}
		}).navGrid('#pagerGridProbResponsablePJENC',{edit:false,add:false,del:false});
	}




//***********************************************************FUNCIONALIDAD MANDAMIENTOS JUDICIALES**************************************************************/
	/*
	*Funcion que carga el grid con los resolutivos para dicha audiencia
	*/
	function cargaResolutivosAudiencia(idAudiencia){
		
		jQuery("#gridAudienciasResolutivosPJENC").jqGrid({ 
			url:'<%=request.getContextPath()%>/consultarResolutivosAudienciaPJENS.do?idEvento='+ idAudiencia,
			datatype: "xml", 
			colNames:['Temp. Video','Resolutivo',], 
			colModel:[ 	{name:'temporizadorVideo',index:'temporizadorVideo', width:150, align:"center"},
						{name:'resolutivo',index:'resolutivo', width:400,align:"center"}
					],
			pager: jQuery('#pagergGidAudienciasResolutivosPJENC'),
			rowNum:10,
			rowList:[10,20,30],
			width: 725,
			sortname: 'detalle',
			scrollrows : true,
			viewrecords: true,
			caption:"Resolutivos de Audiencia",
			sortorder: "desc",
			loadComplete: function(){				
				var registros =jQuery("#gridAudienciasResolutivosPJENC").jqGrid('getDataIDs'); 
				var total = registros.length;
				$("#gridAudienciasResolutivosPJENC").jqGrid('setSelection',registros[total-1]);
			 },
			ondblClickRow: function(id){
				var data = jQuery("#gridAudienciasResolutivosPJENC").jqGrid('getRowData',id);
				modificarResolutivo(id, data);
			}
		}).navGrid('#pagergGidAudienciasResolutivosPJENC',{edit:false,add:false,del:false});
	}

	/*
	*Funcion que abre la ventana modal para ver los resolutivos de audiencia
	*/
	function modificarResolutivo(id, data) { 
		
		$('#tempVideo').val(data.temporizadorVideo);
		$('#resolutivo').val(data.resolutivo);
		$("#divAgregarResolutivo").dialog("open");
		$("#divAgregarResolutivo").dialog({
			autoOpen:true,
			modal:true, 
			title:'Agregar Resolutivo', 
			dialogClass:'alert',
			position:[200,50],
			width:350,
			height: 350,
			//maxWidth: 300,
			buttons:{"Aceptar":function() {			  		
			   		$(this).dialog("close");
				}
			}
		});
	}
	
	
	//Variable para controlar el id de la ventana
	var ventanaMandamientoJudicial=1;
	/*
	*Funcion que abre la ventana de mandamiento judicial
	* 
	*/
	function mostrarVentanaMandamientoJudicial(idInvolucrado,madamientoId,reedicion,idsRelacionesDP){
		
		resolutivoId = jQuery("#gridAudienciasResolutivosPJENC").jqGrid('getGridParam','selrow');
		rowid = resolutivoId;		
		ventanaMandamientoJudicial++;
		idVentana = "iframewindowMandamientoJudicial"+ventanaMandamientoJudicial;
		$.newWindow({id:"iframewindowMandamientoJudicial"+ventanaMandamientoJudicial, statusBar: true, posx:70,posy:20,width:920,height:400,title:"Ingresar/consultar Mandamiento Judicial", type:"iframe"});
		var operacion = "";
		var idMandamiento="";
		if(reedicion == false){
			operacion = "INGRESAR";
		}else{
			//rowId es una variable requerida para consultar el mandamiento
			operacion = "REEDICION";
			var invoIdmandamientoId = idInvolucrado+","+madamientoId;
			idMandamiento='&rowid='+invoIdmandamientoId;
		}
		var parametros = '?idAudiencia='+idAudiencia+'&idInvolucrado='+ idInvolucrado+'&numeroExpedienteId='+numeroExpedienteId+'&numeroExpediente='+numeroCausa+'&resolutivoId='+resolutivoId+'&operacion='+operacion+'&idVentana='+idVentana+'&numeroDeCaso='+numeroDeCaso+idMandamiento;
		if (idsRelacionesDP != undefined
				&& idsRelacionesDP.length > 0){
			parametros+="&idsDelitoPersona="+idsRelacionesDP;
		}
		var url ='<iframe src='+contextoPagina+'/ingresarMandamientoJudicial.do'+parametros+' width="920" height="400"/>'; 
<%-- 		$.updateWindowContent("iframewindowMandamientoJudicial"+ventanaMandamientoJudicial,'<iframe src="<%=request.getContextPath()%>/ingresarMandamientoJudicial.do?idAudiencia='+idAudiencia+'&idInvolucrado='+ idInvolucrado+'&numeroExpedienteId='+numeroExpedienteId+'&numeroExpediente='+numeroCausa+'&operacion=REEDICION&rowid='+invoIdmandamientoId+'&idVentana='+idVentana+'&numeroDeCaso='+numeroDeCaso+'&resolutivoId='+resolutivoId+'" width="920" height="400" />'); --%>
	    $.updateWindowContent("iframewindowMandamientoJudicial"+ventanaMandamientoJudicial,url);
	}

	/*
	*Funcion para cerrar la ventana de mandamiento judicial
	*/
	function cerrarVentanaMandamientoJudicial(idVentana){
		$.closeWindow(idVentana);
	}

	/* 	 
	* Funci&oacute;n que genera un Mandamiento y despues abre la pantalla de mandamiento judicial
	*
	*/
	function generarMandamiento(){
		resolutivo = jQuery("#gridAudienciasResolutivosPJENC").jqGrid('getGridParam','selrow');
		
		if(resolutivo == null){
			alertDinamico("Seleccione un resolutivo para generar el mandamiento judicial");
			return;
		}

		consultarMandamientoPorResolutivo();		
	}

	
	//Variable que controla la apertura del jsp generarMandamientoJudicial
	var idWindowGenerarMandamientoJudicial = 0;

	
	/* 	 
	* Funci&oacute;n que para Generar un Mandamiento despues de dar clic en el boton Generar Mandamiento judicial
	*
	*/
	function abrirMandamientoJudicial(){
		idResolutivo = jQuery("#gridAudienciasResolutivosPJENC").jqGrid('getGridParam','selrow');
		
		if(idResolutivo == null){
			alertDinamico("Seleccione un resolutivo para generar el mandamiento judicial");
			return;
		}

		abrirVentanaMandamientoJudicial(idResolutivo);		
	}
	
	/*
	*Funcion que abre la ventana Mandamiento Judicial
	*/
	function abrirVentanaMandamientoJudicial(idResolutivo){

		console.info("**ABRIR VENTANA GENERAR MANDAMIENTO**");
		idWindowGenerarMandamientoJudicial++;
		$.newWindow({id:"iframeWindowGenerarMandamientoJudicial"+idWindowGenerarMandamientoJudicial, statusBar: true, posx:200,posy:50,width:1000,height:450,title:"Generar Mandamiento Judicial", type:"iframe",onWindowClose: function(id){
			idWindowGenerarMandamientoJudicial--;
		}});
		$.updateWindowContent("iframeWindowGenerarMandamientoJudicial"+idWindowGenerarMandamientoJudicial,'<iframe src="<%= request.getContextPath()%>/generarMandamientoJudicial.do?idAudiencia='+idAudiencia+'&idExpediente='+idExpediente+'&idResolutivo='+idResolutivo+'&numeroCausa='+numeroCausa+'&numeroDeCaso='+numeroDeCaso+'" width="1000" height="450" />');
		$("#" +"iframeWindowGenerarMandamientoJudicial"+idWindowGenerarMandamientoJudicial+ " .window-maximizeButton").click();
	}
	
	
	/*
	*Funcion que consulta si el resolutivo ya tiene asociado un mandamiento, de ser as&iacute;
	*verifica si tiene un archivo digital asociado y lo abre en formato pdf.
	*si no abre la ventana para terminar de asociar el mandamiento.
	*/
	function consultarMandamientoPorResolutivo(){

		var resolutivoId =  jQuery("#gridAudienciasResolutivosPJENC").jqGrid('getGridParam','selrow');

		$.ajax({								
	    	  type: 'POST',
	    	  url: '<%=request.getContextPath()%>/consultarMandamientoPorResolutivo.do?resolutivoId='+resolutivoId+'',
	    	  data:'',				
	    	  dataType: 'xml',
	    	  async: false,
	    	  success: function(xml){

	    		var mandamientoIdAsoc = "";
				mandamientoIdAsoc = $(xml).find("mandamientoDTO").find("documentoId").first().text();
				var involucradoId = "";
				involucradoId = $(xml).find("mandamientoDTO").find("involucrado").last().find("elementoId").first().text();
				var idArchivoMandamiento = $(xml).find("mandamientoDTO").find("archivoDigital").first().find("archivoDigitalId").first().text();
				var idArchivoImagenFunc = $(xml).find("mandamientoDTO").find("resolutivo").first().find("audiencia").find("expediente").find("responsableTramite").find("archivoDigital").find("archivoDigitalId").first().text();
				
				
    			if(idArchivoMandamiento != ""
    					&& idArchivoMandamiento != idArchivoImagenFunc){
					//el documento ya est&aacute; generado, consultarlo
    				document.verMandamientoForm.archivoDigitalId.value = idArchivoMandamiento;
					document.verMandamientoForm.documentoId.value = "";
					document.verMandamientoForm.submit();
				}
    			else if(mandamientoIdAsoc !="" && involucradoId !=""){
    				
    				mostrarVentanaMandamientoJudicial(involucradoId,mandamientoIdAsoc,true,[]);
        		}
    			else{
					agregarMandamientoJudicial();
				}
	    	 }
	    });
	}

	
	/*
	*Funcion que agrega el calendario a los campos fecha de inicio y fecha de fin
	*/
	function agregarCalendarios(){

		$('#fechaInicioSentencia').val('');
		$('#fechaFinSentencia').val('');

		$("#fechaInicioSentencia").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%=request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
	
		$("#fechaFinSentencia").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%=request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
	}

	/*
	*Funcion para mostrar u ocultar el tipo de sentencia dependiendo
	*de la seleccion del usuario
	*/
	function controlTipoMandamiento(){

		var tipoMandamiento = $('#tipoMandamiento option:selected').val();
		
		if(tipoMandamiento == 0){
			limpiaDivTipoMandamiento();
		}
		else if(tipoMandamiento == <%=TipoMandamiento.SENTENCIA.getValorId()%>){
			$('#divEtiTipoSentencia').show();
			$('#divCbxTipoSentencia').show();
			$('#divEtiFechaInicioSentencia').show();
			$('#divFechaInicioSentencia').show();
			$('#divEtiFechaFinSentencia').show();
			$('#divFechaFinSentencia').show();
		}else{
			$('#divEtiTipoSentencia').hide();
			$('#divCbxTipoSentencia').hide();
			$('#divEtiFechaInicioSentencia').hide();
			$('#divFechaInicioSentencia').hide();
			$('#divEtiFechaFinSentencia').hide();
			$('#divFechaFinSentencia').hide();
			limpiaDatosSentencia();
		}
	}
	
	/*Funcion que abre el visor de medidas cautelares 
	*/
	function mostrarVentanaInvolucradosCausa(){
		idWindowVisorMedidasCautelaresPJENC++;
		// Este servicio tiene como par&aacute;metro de acci&oacute;n en ingresarMedidasCautelaresPJENC.jsp
		// a flujoMedCautelar
		var flujoMedCautelar = "dePJaSSPyPGJ";
		$.newWindow({id:"iframewindowVisorMedidasCautelares"+idWindowVisorMedidasCautelaresPJENC, statusBar: true, posx:200,posy:50,width:970,height:480,title:"Visor de Medidas Cautelares", type:"iframe"});
    	$.updateWindowContent("iframewindowVisorMedidasCautelares"+idWindowVisorMedidasCautelaresPJENC,'<iframe src="<%=request.getContextPath()%>/visorMedidaCautelar.do?numeroCausa='+numeroExpediente+'&flujoMedCautelar='+flujoMedCautelar+'&numeroExpedienteId='+numeroExpedienteId+'" width="970" height="480" />'); 
	}
	
	/*
	*Limpia el Div de tipos de mandamiento
	*/
	function limpiaDivTipoMandamiento(){

		$('#tipoMandamiento').attr('selectedIndex', 0);
		$('#nombreDelImputado').attr('selectedIndex', 0);
		$('#tipoSentencia').attr('selectedIndex', 0);
		$('#fechaInicioSentencia').val("");
		$('#fechaFinSentencia').val("");
		//oculta el tipo de sentencia
		$('#divEtiTipoSentencia').hide();
		$('#divCbxTipoSentencia').hide();
		$('#divEtiFechaInicioSentencia').hide();
		$('#divFechaInicioSentencia').hide();
		$('#divEtiFechaFinSentencia').hide();
		$('#divFechaFinSentencia').hide();
	}

	/* BORRAR
	*Limpia los campor de sentencia
	*/
	function limpiaDatosSentencia(){

		$('#tipoSentencia').attr('selectedIndex', 0);
		$('#fechaInicioSentencia').val("");
		$('#fechaFinSentencia').val("");
	}
	
	
	/** 
	* Funcion que realiza la carga del cat&aacute;logo de tipos de mandamiento
	*/
	function cargaTipoMandamiento() {
		
		$.ajax({
			async: true,
			type: 'POST',
			url: '<%=request.getContextPath()%>/consultarCatalogoTipoMandamiento.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				
				$(xml).find('tipoMandamiento').each(function(){
					$('#tipoMandamiento').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	
	/** NO BORRAR SOLO QUITAR #tipoSentencia
	* Funcion que realiza la carga del cat&aacute;logo de tipos de sentencia
	*/
	function cargaTipoSentencia() {
		
		$.ajax({
			async: true,
			type: 'POST',
			url: '<%=request.getContextPath()%>/consultarCatalogoTipoSentencia.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				
				$(xml).find('tipoSentencia').each(function(){
					$('#tipoSentencia').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					$('#tipoDeSentencia').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	/** BORRAR
	* Funcion que realiza la carga los involucrados de la audiencia
	*/
	function consultarImputadosParaMandamientoXAudiencia() {
		
		$.ajax({
			async: true,
			type: 'POST',
			url: '<%=request.getContextPath()%>/consultarImputadosParaMandamientoXAudiencia.do?idAudiencia='+idAudiencia+'',
			data: '',
			dataType: 'xml',
			success: function(xml){
				
				$(xml).find('imputado').each(function(){
					$('#nombreDelImputado').append('<option value="' + $(this).find('involucradoId').text() + '">' + $(this).find('nombreCompleto').text() + '</option>');
				});
			}
		});
	}

	

	
	/**
	* Realizar el ordenamiento de las solicitudes seleccionadas
	* Esto es mandarlas a backend a cambiar el estatus de las solicitudes
	*/
	function ordenarSolicitudes(){
		solicitudesAOrdenar = "";
		solicitudesAOrdenar = obtenerIdsSolicitud();
		
		if (solicitudesAOrdenar == ""){
			customAlert('Por favor seleccione un registro de las solicitudes de audio y video <br/>'+
					'o solicitudes de transcripci&oacute;n a ordenar');
		}else{
			$.ajax({
				async: true,
				type: 'POST',
				url: '<%=request.getContextPath()%>/ordenarSolicitudesTranscripcionyAV.do?solicitudesIds='+solicitudesAOrdenar,
				data: '',
				dataType: 'xml',
				success: function(xml){
				
					//Se recargan grids de solicitudes
					cargarGridSolicitudesTranscripcionAudiencia(idAudiencia,'<%=TiposSolicitudes.TRANSCRIPCION_DE_AUDIENCIA.getValorId()%>');
					cargarGridSolicitudesAudioVideoAudiencia(idAudiencia,'<%=TiposSolicitudes.AUDIO_VIDEO.getValorId()%>');
				}
			});
		}
	}
	
	function obtenerIdsSolicitud(){
		var idsOrdenados = "";
		var idsTranscripcion = obtenerSeleccionados($("#gridSolTranscripcionAudienciaPJENC"));
		var idsAudioVideo = obtenerSeleccionados($("#gridSolAudioVideoAudienciaPJENC"));
		var idsTranLength = idsTranscripcion.length;
		var idsAudVidLength = idsAudioVideo.length; 
		for (var i=0; i < idsTranLength; i++){
			idsOrdenados+=idsTranscripcion[i]+",";
		}
		for (var i=0; i < idsAudVidLength; i++){
			idsOrdenados+=idsAudioVideo[i]+",";
		}
		return idsOrdenados;
	}
	
	/*
	*Funcion para seleccionar en el gird multiselect
	*/
	function  seleccionarItems(grid){
			
		var currentPage = grid.getGridParam('page').toString();
	
		//retrieve any previously stored rows for this page and re-select them
		var retrieveSelectedRows = grid.data(currentPage);
		
		if (retrieveSelectedRows) {
	         $.each(retrieveSelectedRows, function (index, value) {
	             grid.setSelection(value, false);
			});
		}
	}
			
			
	/*
	*Funcion para guardar en el gird multiselect
	*/
	function guardarItemsSeleccionados(grid) {
		
		var pagerId = grid.getGridParam('pager').toString();
		
		if(pagerId.indexOf("#") != -1){
			pagerId = pagerId.substring(1, pagerId.length);
		}
		// ger paper id like "pager" 
		var pageValue = $('input.ui-pg-input', "#pg_" + $.jgrid.jqID(pagerId)).val(); 
		var saveSelectedRows = grid.getGridParam('selarrrow'); 
		//Store any selected rows 
		grid.data(pageValue.toString(), saveSelectedRows); 
	}


	/*
	*Funcion para borrar la seleccion en el gird multiselect
	*/		
	function eliminarItemsSeleccionados(grid){
		
		guardarItemsSeleccionados(grid);
		grid.jqGrid('resetSelection');
	    var retrieveSelectedRows = grid.data();       
	    if (retrieveSelectedRows) {
	        $.each(retrieveSelectedRows, function (index, value) {
	            $.each(value, function (sub_index, sub_value) {
	                if(typeof(sub_value)=='string'){
	                	grid.data(index, "");
	                }
	            });
	        });
	    }
	    
	}

	/*
	*Funcion para obtener los ids seleccionados del grid multiselect
	*/
	function obtenerSeleccionados(grid){
		
		guardarItemsSeleccionados(grid);
	
	    var retrieveSelectedRows = grid.data();       
	    var arr_values = new Array();
	
	    if (retrieveSelectedRows) {
	        $.each(retrieveSelectedRows, function (index, value) {
	            $.each(value, function (index, sub_value) {
	                if(typeof(sub_value)=='string')
	                arr_values.push(sub_value);
	            });
	        });
	    }
	
	    return arr_values;
	}

	var jsonParam = [{}];
	
	function actualizarSituacionJuridica(){	
	
//		for (var key in jsonParam) {
//				if (jsonParam[key].situacionJuridica == <%=SituacionJuridica.SENTENCIADO.getValorId()%>){
//					if(!jsonParam[key].todosLosDatos){
//						obtenerDatosSentencia(key);
//						return;
//				}
//			}
//		}			
		
// 		llenarDatosSentencia();
		
		for (var key in jsonParam) {
			actualizarDatos(key);
		}

		enviarDatosActualizarSituacionJuridica();
		
	}
	
	function limpiarJSON(){
			for (var key in jsonParam) {
			if(typeof jsonParam[key].involucradoId === "undefined"){
				//try para funcion recursiva que elimina los elementos vacios del arreglo de JSON 
				try {
					jsonParam.splice(key, 1);
					limpiarJSON();
				} catch (error) {
					return;
				}
			}
		}
	}
	

	/*
	*Funcion para obtener los ids del 
	*DELITO
	*INVOLUCRADO
	*NUEVA CALIDAD
	*/
	function delitosPersonaAOrdenar() {
		var solicitudesAOrdenar = "";
		var i=0;
		var arrayIds = new Array();
		var jsonList = [{}];
		
		
		arrayIds = jQuery("#gridProbResponsablePJENC").getDataIDs();
		
		$('select[id^="delito_"]').each(function(){
			id = $(this).attr("id");
			var datosRegistro = jQuery("#gridProbResponsablePJENC").jqGrid('getRowData', arrayIds[i]);
			jsonList.push({
							involucradoId: arrayIds[i], 
							otro: id.split("_")[1], 
							situacionJuridica: $(this).val(),
							nombreImputado: datosRegistro.NombreImputado,
							tipoSentencia: 0,
							esLesionado: 0,
							fechaInicio: "",
							fechaFin: "",
							puntosPorAcumular: 0,
							todosLosDatos: false,
							yaEstaSentenciado: ($(this).val() == <%=SituacionJuridica.SENTENCIADO.getValorId()%>) ? true : false,
							nus: 0,
							listaNUS: [{}]
							});
			i++;
		});
			
		return jsonList; 
	}

	/**
	* Funci&oacute;n invocada una vez creado y almacenado en la base el archivo digital correspondiente al documento
	* Se env&iacute;a el mandamiento judicial a SSP
	*/
	function documentoGeneradoSincrono(documentoId){
		
		$.closeWindow("iframewindowGenerarMandamientoJudicial");
		
		$.ajax({
			async: true,
			type: 'POST',
			url: '<%=request.getContextPath()%>/enviarMandamientoJudicial.do',
			data: 'mandamientoId='+documentoId,
			dataType: 'xml',
			success: function(xml){	
				alertDinamico("Mandamiento judicial enviado correctamente");
			}

		});

		document.verMandamientoForm.documentoId.value = documentoId;
		document.verMandamientoForm.submit();
		
	}

	function llenarDatosSentencia(){
	
		for (var key in jsonParam) {
			actualizarDatos(key);
			if (jsonParam[key].situacionJuridica == <%=SituacionJuridica.SENTENCIADO.getValorId()%>){
				if(!jsonParam[key].todosLosDatos && !jsonParam[key].yaEstaSentenciado){
					obtenerDatosSentencia(key);
					return;
				}
			}
		}
		
		enviarDatosActualizarSituacionJuridica();
	}
	
	function actualizarDatos(key){
			var datosRegistro = jQuery("#gridProbResponsablePJENC").jqGrid('getRowData', jsonParam[key].involucradoId);								 
			jsonParam[key].situacionJuridica= jQuery("#delito_"+jsonParam[key].otro).val();
	}

	function enviarDatosActualizarSituacionJuridica(){
	 	bloquearPantalla();
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%=request.getContextPath()%>/actualizarSituacionJuridica.do',
			contentType: "application/json; charset=utf-8",
        	dataType: "json",
        	data: JSON.stringify(jsonParam),
			success: function(json){
				try {
					if (json.mensaje != undefined && json.mensaje != "undefined"){
						customAlert(json.mensaje);
					}
				}catch(e){
					console.error(e);				
				}
			}
		});
	
	
	}

	function llenarForma(index){
		var html = "";
		
		var unico = false;
		var listNUS = jsonParam[index].listaNUS;		
		if (listNUS.unico == true || listNUS.unico =="TRUE"){
			unico = true;
		}else{
			html = "<table>";
			html += "<tr>";
			html += "<td colspan=\"4\">Seleccione un NUS para el sentenciado:</td>";
			html += "</tr>";
			html += "<tr>";
			html += "<td>&nbsp;</td>";
			html += "<td>Nombre</td>";
			html += "<td>Fecha Incio</td>";
			html += "<td>Fecha Fin</td>";
			html += "<td>NUS</td>";
			html += "</tr>"; 
		}
		
		
		
		jQuery.each(listNUS, function(k, v){
				if(k != "unico"){
					if(unico){
						html += "<input type=\"radio\" name=\"nuevoNUS\" ";
						html += " checked=\"checked\" ";
						html += "value=\"" + v +"\" style=\"display:none;\">";
						html += "<center><strong>" + v + "</strong></center>";
					} else {
						var myObject = eval('(' + v + ')');
						html += "<tr>";
						html += "<td><input type=\"radio\" name=\"nuevoNUS\" ";
						html += ((myObject.nombreCompleto == "Nuevo") ? " checked=\"checked\" " : " ");
						html += "value=\"" + myObject.NUS +"\"></td>";
						html += "<td>" + myObject.nombreCompleto +"</td>";
						html += "<td>" + myObject.fechaIncio +"</td>";
						html += "<td>" + myObject.fechaIncio +"</td>";
						html += "<td>" + myObject.NUS +"</td>";
						html += "</tr>";
					}
				}
			}
		);
		if(!unico){
			html += "</table>";
		}
		
		jQuery("#nusImputado").html(html);
	
		jQuery("#nombreImputado").val(jsonParam[index].nombreImputado);
		jQuery("#tipoDeSentencia").val(jsonParam[index].tipoSentencia);
		//Radio Buttons son 0-based por lo que el index de S&iacute; = 0, index de No = 1
		if(jsonParam[index].esLesionado == 1){
			jQuery("input:radio[name=bLesion]:nth(0)").attr("checked",true);		
		} else if (jsonParam[index].esLesionado == 0){		
			jQuery("input:radio[name=bLesion]:nth(1)").attr("checked",true);
		}		
		jQuery("#fInicioSentencia").val(jsonParam[index].fechaInicio);
		jQuery("#fFinSentencia").val(jsonParam[index].fechaFin);
		jQuery("#puntosPorAcumular").val(jsonParam[index].puntosPorAcumular);	
	}
	
	function llenarObjeto(index){
		
		jsonParam[index].nus = jQuery("input:radio[name=nuevoNUS]:checked").val();	

		jsonParam[index].tipoSentencia = jQuery("#tipoDeSentencia").val();
		jsonParam[index].esLesionado = jQuery("input:radio[name=bLesion]:checked").val();
		jsonParam[index].fechaInicio = jQuery("#fInicioSentencia").val();
		jsonParam[index].fechaFin = jQuery("#fFinSentencia").val();
		jsonParam[index].puntosPorAcumular = jQuery("#puntosPorAcumular").val();	
		
		if(jsonParam[index].tipoSentencia <= 0){
			customAlert("Se debe seleccionar un tipo de sentencia.", "Validaci&oacute;n");
			return false;
		}
		
		if(!validaCamposFecha(jsonParam[index].fechaInicio, jsonParam[index].fechaFin)){
			return false;
		}
		
		if(jsonParam[index].puntosPorAcumular <= 0){
			customAlert("Se debe ingresar el n&uacute;mero de puntos a acumular.", "Validaci&oacute;n");
			return false;
		}
		
		jsonParam[index].todosLosDatos = true;
		return true;
	} 

	function obtenerDatosSentencia(index){
		
		llenarForma(index);
		
		
		
		jQuery("#divGenerarSentencia").dialog(
			{ 
		  		autoOpen:	false, 
				modal:		true, 
			  	title:		'Datos De La Sentencia',
			  	width:		'auto',
			  	height:		'auto',
				resizable: false,
				closeOnEscape: false,			  	
			  	buttons:	{
			  					"Aceptar": function() {
			  						if(llenarObjeto(index)){
										$(this).dialog("close");								
										llenarDatosSentencia();
									}		  				
			  	  				},
								"Cancelar": function() {
									$(this).dialog("close");
								}
			  				}
			}
		);	 
		
		
		$("#divGenerarSentencia").dialog( "open" );
		
	}
	
	function invocacionConsultaAudienciaJAVS(){
		consultarAudienciaJAVS(idAudiencia);
	}
		
	/**
	* Consulta el NUS de los involucrados
	*/
	function consultarNUSDeLosInvolucrados(){
	 	bloquearPantalla();
		$.ajax({
			async: true,
			type: 'POST',
			url: '<%=request.getContextPath()%>/obtenerNUSDelInvolucreado.do',
			contentType: "application/json; charset=utf-8",
        	dataType: "json",
        	data: JSON.stringify(jsonParam),
			success: function(jsonObject){
				jsonParam = jsonObject;
			}
		});
	
	
	}


	//***********************************************************FUNCIONALIDAD PARA SENTENCIAS***********************************************************/

	/** INICIA SECCION DE MANDAMIENTOS JUDICIALES **/
	var esMandamientoJudicial = 0;
	

	/*
	*Funcion que abre la modal para agregar un mandamiento judicial a un imputado
	*/
	function agregarMandamientoJudicial(){
		
		cargaGridImputados();
		$("#divAgregarMJ").dialog("open");
		$("#divAgregarMJ").dialog({ autoOpen: true, modal: true, 
			title: 'Agregar mandamiento judicial',
			dialogClass: 'alert', position: [350,50],
			width: 800, height:480, maxWidth: 800, maxHeight:550,
			buttons:{
				"Agregar":function() {
					validaSeleccionDeMandamiento();
				},
				"Cancelar":function() {
					
					customConfirm("&iquest;Realmente desea salir?", "", cancelarAgregarInvolucrado);			
				}
			}
		});
	}
	
	function cancelarAgregarInvolucrado(){
  		$("#divAgregarMJ").dialog("close");
	}

	
	
	var banderaImputados = true;
	/**
	*Funcion que carga el grid con los imputados del expediente
	*/
	function cargaGridImputados(){
		if(banderaImputados == true){
			jQuery("#gridAgregarMJ").jqGrid({ 
				url: contextoPagina +'/consultarImputadosPorNumExpediente.do?numeroExpediente='+numeroCausa+'',
				data:'',
				datatype: "xml",
				colNames:['Nombre del Imputado'], 
				colModel:[ 					
				           	{name:'nombreImputado',index:'nombreImputado',width:350, align:'center'}, 
						],
				pager: jQuery('#pagerGridAgregarMJ'),
				rowNum:10,
				autoWidth:false,
				width:750,
				height:250,
				rowList:[10,20,30],
				sortname: 'nombreImputado',
				viewrecords: true,
				sortorder: "desc",
				caption:"Imputados"
			}).navGrid('#gridAgregarMJ',{edit:false,add:false,del:false});
			banderaImputados = false;
		}
		else{
			jQuery("#gridAgregarMJ").jqGrid('setGridParam', {url: contextoPagina +'/consultarImputadosPorNumExpediente.do?numeroExpediente='+numeroCausa+'',datatype: "xml" });
			$("#gridAgregarMJ").trigger("reloadGrid");
		}
	}
	
	function consultaDelitos(idProbable){
    	if(banderaPV==0){
	       	jQuery("#gridCatDelitosRDPPV").jqGrid({ 
				url:'<%= request.getContextPath()%>/CargarDelitoAsociadosInvolucrado.do?idPR='+idProbable +'&idExpediente='+idExpediente, 
				datatype: "xml",
				colNames:['Delito','Forma de Participaci&oacute;n','V&iacute;ctima'], 
				colModel:[ 	{name:'Delito',index:'delito', width:250}, 
							{name:'FormaParticipacion',index:'formaParticipacion',width:550, hidden:true},
							{name:'Victima',index:'victima',width:250}					
						],
				rowNum:0,
				rowList:[0,0,0],
				autowidth: true,
				caption:"LISTA DE DELITOS",
				sortname: 'Clave',
				multiselect: true,
				viewrecords: true,
				id: 'divgridIzq',
				sortorder: "desc",
				ondblClickRow: function(rowid) {
// 				    $.newWindow({id:"consultaDelitoPersona", statusBar: true, posx:20,posy:20,width:500,height:350,title:"Consulta delito persona", type:"iframe"});
<%--              		$.updateWindowContent("consultaDelitoPersona",'<iframe src="<%= request.getContextPath() %>/abrePantallaConsultaDelitoPersona.do?idDelitoPersona='+rowid+'&idNumeroExpediente='+idNumeroExpedienteOp+'" width="500" height="350" />'); --%>
				},
				loadComplete: function(){
			    }
			}).navGrid('#pager1',{edit:false,add:false,del:false});
	        $("#gridCatDelitosRDPPV").trigger("reloadGrid");
	        banderaPV=1;
    	}else{
    		jQuery("#gridCatDelitosRDPPV").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/CargarDelitoAsociadosInvolucrado.do?idPR='+idProbable +'&idExpediente='+idExpediente,datatype: "xml" });
			$("#gridCatDelitosRDPPV").trigger("reloadGrid");
    	}
    }
	
	
	function validaSeleccionDeMandamiento(){
		
		var rowid = jQuery("#gridAgregarMJ").jqGrid('getGridParam','selrow');
		
		if (rowid) {
			$("#divAgregarMJ").dialog("close");
// 			mostrarVentanaMandamientoJudicial(rowid,null,false);
			relacionarDelitoPersona(rowid,null,false);
		}else{
			customAlert("Por favor seleccione un rengl&oacute;n");
		} 
	}
	
	function relacionarDelitoPersona(idInvolucrado,madamientoId,reedicion){
		
		consultaDelitos(idInvolucrado);
		$("#divRelacionarDP").dialog("open");
		$("#divRelacionarDP").dialog({ autoOpen: true, modal: true, 
			title: 'Relacionar delito(s)',
			dialogClass: 'alert', position: [350,50],
			width: 800, height:480, maxWidth: 800, maxHeight:550,
			buttons:{
				"Agregar":function() {
					var idsRelacionesSeleccionados = jQuery("#gridCatDelitosRDPPV").jqGrid('getGridParam','selarrrow');
					if (idsRelacionesSeleccionados.length > 0){
						$("#divRelacionarDP").dialog("close");
						mostrarVentanaMandamientoJudicial(idInvolucrado,madamientoId,reedicion,idsRelacionesSeleccionados);						
					}else{
						customAlert("Por favor seleccione al menos una relaci&oacute;n delito persona a asociar con el mandamiento.",
						"Error de validaci&oacute;n");
					}
				},
				"Cancelar":function() {
					customConfirm("&iquest;Realmente desea salir?", "", function(){
						$("#divRelacionarDP").dialog("close");
					});			
				}
			}
		});
	}
	
	function documentos(){
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',
			{
				url:'<%= request.getContextPath()%>/consultarDocumentosPorAudiencia.do?idAudiencia='+idAudiencia, 
				datatype: "xml" ,
				loadComplete: function(){
					var rows = jQuery("#gridDetalleFrmPrincipal").jqGrid('getGridParam','records');
					if(rows != null && rows != '' && rows != 'undefined'){
						$('#idTotalDocumentos').html(parseInt(rows));
					}else{
						$('#idTotalDocumentos').html("0");
					}
				},
			});
		$("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
	}
	
	function recargaGridAdjuntarDocumento(){
		documentos();
	}
	
	function consultaPDF(id){
		document.frmDococumentos.documentoId.value = id;
		document.frmDococumentos.submit();
	}
	
	/** FINALIZA SECCION DE MANDAMIENTOS JUDICIALES **/
	
	</script>
</head>
<body>

	<div id="tabsprincipalconsulta">
		<ul>
			<li id="detalleAudiencia"><a href="#tabsconsultaprincipal-1">Solicitudes
					en Audiencia</a>
			</li>
			<li id="transcripcionAudiencia"><a
				href="#tabsconsultaprincipal-2">Estado de Intervinientes</a>
			</li>
			<li id="audioVideoAudiencia"><a href="#tabsconsultaprincipal-3">Mandamientos
					Judiciales</a>
			</li>
			
			<li id="notificaciones">
				<a href="#tabNotificaciones-4">Notificaciones</a>
			</li>
			
			<li id="tabTabsDocs">
				<a href="#tabs-11" onclick="documentos()" id="linkDocs">Documentos</a>
			</li>
			<li id="acumulacion">
				<a href="#tabAcumulacion-14">Acumulaci&oacute;n</a>
			</li>
			
		</ul>
		
		<!--COMIENZA TAB Acumulacion-->
		    <div id="tabAcumulacion-14" class="notificaciones">
				<jsp:include page="/WEB-INF/paginas/AcumulacionGridIncludeView.jsp" flush="true"></jsp:include>
			</div>
		<!--TERMINA TAB Acumulacion-->

		<!--Comienza tabprincipal 1-->
		<div id="tabsconsultaprincipal-1">

			<table width="1000" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="139" align="right"><strong>Audiencia:</strong>
					</td>
					<td colspan="2"><strong> <input type="text"
							id="audienciaPJENS"
							style="width: 250px; border: 0; background: #DDD;"
							readonly="readonly" /> </strong>
					</td>
					<td width="76">&nbsp;</td>
					<td width="79">&nbsp;</td>
					<td width="89">&nbsp;</td>
					<td width="99" align="right"><strong>Fecha Inicio:</strong>
					</td>
					<td colspan="2"><input type="text" id="fechaAudienciaPJENS"
						style="width: 150px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
					<td width="111">&nbsp;</td>
				</tr>
				<tr>
					<td align="right"><strong>Tipo de Audiencia:</strong>
					</td>
					<td colspan="2"><strong> <input type="text"
							id="tipoAudienciaPJENS"
							style="width: 350px; border: 0; background: #DDD;"
							readonly="readonly" /> </strong>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="right"><strong>Hora Inicio:</strong>
					</td>
					<td colspan="2"><input type="text" id="horaAudienciaPJENS"
						style="width: 150px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right"><strong>N&uacute;mero de Caso:</strong>
					</td>
					<td colspan="2"><input type="text" id="numCasoPJENS"
						style="width: 200px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="right"><strong>Fecha Fin:</strong>
					</td>
					<td colspan="2"><input type="text" id="fechaFinAudienciaPJENS"
						style="width: 150px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right"><strong>N&uacute;mero de Causa:</strong>
					</td>
					<td colspan="2"><input type="text" id="numExpPJENS"
						style="width: 200px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="right"><strong>Hora Fin:</strong>
					</td>
					<td colspan="2"><input type="text" id="horaFinAudienciaPJENS"
						style="width: 150px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right"><strong>Car&aacute;cter:</strong>
					</td>
					<td colspan="2"><input type="text" id="caracterPJENS"
						style="width: 200px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="right"><strong>Juez(ces):</strong>
					</td>
					<td colspan="2"><select id="juecesAudienciaPJENS"
						style="width: 250px;  border: 0; background:#DDD;"></select> <!--<input type="text" id="juecesAudienciaPJENS" style="width: 200px; border: 0; background: #DDD;" readonly="readonly" /></td>-->
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4" align="center"><strong>Transcripci&oacute;n
							de Audiencia:</strong>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td colspan="3" align="center"><strong>Audio/Video:</strong>
					</td>
					<td>&nbsp;</td>  
				</tr>
				<tr>
					<td colspan="4" rowspan="7" align="right">
						<table id="gridSolTranscripcionAudienciaPJENC"></table>
						<div id="pagerGridSolTranscripcionAudienciaPJENC"></div></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td colspan="4" rowspan="7" align="right">
						<table id="gridSolAudioVideoAudienciaPJENC"></table>
						<div id="pagerGridSolAudioVideoAudienciaPJENC"></div></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
					<td>&nbsp;</td>
					<td colspan="2" align="center"><input type="submit"
						name="ordenar" id="ordenar" value="Ordenar" class="btn_Generico"></td>
					<td align="right">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>				
				<tr>	
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="center">
						<input type="button" colspan="2" class="btn_mediano" width="100" id="btnVideos" value="Videos de JAVS" onclick="invocacionConsultaAudienciaJAVS();"/ style="display:none;">
					</td>
				</tr>				
			</table>

		</div>
		<!--Termina Div Principal 2-->
		<div id="tabsconsultaprincipal-2">

			<table width="950" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="228" align="right"><strong>Audiencia:</strong>
					</td>
					<td width="250"><strong> <input type="text"
							id="audienciaIntervPJENC"
							style="width: 250px; border: 0; background: #DDD;"
							readonly="readonly" /> </strong>
					</td>
					<td width="92" align="right"><strong>Fecha Inicio:</strong>
					</td>
					<td width="163"><input type="text"
						id="fechaAudienciaIntervPJENC"
						style="width: 150px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
					<td colspan="2" align="right"><strong>Fecha Fin:</strong>
					</td>
					<td width="186"><input type="text"
						id="fechaFinAudienciaIntervPJENC"
						style="width: 150px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right"><strong>Tipo:</strong>
					</td>
					<td><strong> <input type="text"
							id="tipoAudienciaIntervPJENC"
							style="width: 350px; border: 0; background: #DDD;"
							readonly="readonly" /> </strong>
					</td>
					<td align="right"><strong>Hora Inicio:</strong>
					</td>
					<td><input type="text" id="horaAudienciaIntervJENC"
						style="width: 150px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
					<td colspan="2" align="right"><strong>Hora Fin:</strong>
					</td>
					<td><input type="text" id="horaFinAudienciaIntervPJENC"
						style="width: 150px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right"><strong>Juez(ces):</strong>
					</td>
					<td><select id="juecesAudienciaIntervPJENC"
						style="width: 250px;  border: 0; background:#DDD;"></select> <!--<input type="text" id="juecesAudienciaIntervPJENC" style="width: 200px; border: 0; background: #DDD;" readonly="readonly" />-->
					</td>
					<td align="right">&nbsp;</td>
					<td>&nbsp;</td>
					<td colspan="2" align="right">&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td colspan="2">&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="5" rowspan="6">
						<table id="gridProbResponsablePJENC"></table>
						<div id="pagerGridProbResponsablePJENC"></div></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td colspan="2">&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td colspan="3"><input type="submit" id="guardar"
						value="Guardar" onclick="actualizarSituacionJuridica();" class="btn_Generico"/>
					</td>
					<td width="74">&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>

		</div>
		<!--Termina Div Principal 2-->
		<!--Termina Div Principal 3-->
		<div id="tabsconsultaprincipal-3">

			<table width="950" border="0" cellspacing="0" cellpadding="0">
			    <tr>
			        <td width="228" align="right"><strong>Audiencia:</strong>
			        </td>
			        <td width="250"><strong> <input type="text"
			                id="audienciaMandamientosPJENC"
			                style="width: 250px; border: 0; background: #DDD;"
			                readonly="readonly" /> </strong>
			        </td>
			        <td width="92" align="right"><strong>Fecha Inicio:</strong>
			        </td>
			        <td width="150"><input type="text"
			            id="fechaAudienciaMandamientosPJENC"
			            style="width: 150px; border: 0; background: #DDD;"
			            readonly="readonly" />
			        </td>
			        <td colspan="2" align="right"><strong>Fecha Fin:</strong>
			        </td>
			        <td width="186"><input type="text"
			            id="fechaFinAudienciaMandamientosPJENC"
			            style="width: 150px; border: 0; background: #DDD;"
			            readonly="readonly" />
			        </td>
			    </tr>
			    <tr>
			        <td align="right"><strong>Tipo:</strong>
			        </td>
			        <td><strong> <input type="text"
			                id="tipoAudienciaMandamientosPJENC"
			                style="width: 350px; border: 0; background: #DDD;"
			                readonly="readonly" /> </strong>
			        </td>
			        <td align="right"><strong>Hora Inicio:</strong>
			        </td>
			        <td><input type="text" id="horaAudienciaMandamientosPJENC"
			            style="width: 150px; border: 0; background: #DDD;"
			            readonly="readonly" />
			        </td>
			        <td colspan="2" align="right"><strong>Hora Fin:</strong>
			        </td>
			        <td><input type="text" id="horaFinAudienciaMandamientosPJENC"
			            style="width: 150px; border: 0; background: #DDD;"
			            readonly="readonly" />
			        </td>
			    </tr>
			    <tr>
			        <td align="right"><strong>Juez(ces):</strong>
			        </td>
			        <td><select id="juecesAudienciaMandamientosPJENC"
			            style="width: 250px;  border: 0; background:#DDD;"></select> <!--<input type="text" id="juecesAudienciaMandamientosPJENC" style="width: 200px; border: 0; background: #DDD;" readonly="readonly" />-->
			        </td>
			        <td align="right">&nbsp;</td>
			        <td>&nbsp;</td>
			        <td colspan="2" align="right">&nbsp;</td>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td>&nbsp;</td>
			        <td>&nbsp;</td>
			        <td>&nbsp;</td>
			        <td>&nbsp;</td>
			        <td colspan="2">&nbsp;</td>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td>&nbsp;</td>
			        <td colspan="5" rowspan="6">
			            <table id="gridAudienciasResolutivosPJENC" width="100%"></table>
			            <div id="pagergGidAudienciasResolutivosPJENC"></div></td>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td>&nbsp;</td>
			        <td>&nbsp;</td>
			        <td>&nbsp;</td>
			        <td>&nbsp;</td>
			        <td colspan="2">&nbsp;</td>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td align="right">&nbsp;</td>
			        
	  		        <td align="center">
	  		        	<input type="button" id="btnGenerarMandamiento" value="Generar Mandamiento Judicial" class="btn_Generico"/>
	  		        </td>
			        
			        <td colspan="3" align="center">
						<input type="button" id="btnAdminMediCaute" value="Administrar medidas cautelares" class="btn_Generico">
						<!--<input type="submit" id="consultarMandamiento" value="Consultar Mandamiento" />-->
			       	</td>
			   
			    </tr>
			</table>
			    

		</div>
		<!--Termina Div Principal 3-->
	
		<!--COMIENZA TAB NOTIFICACIONES-->
    <div id="tabNotificaciones-4" class="notificaciones">
		<jsp:include page="/WEB-INF/paginas/NotificacionesIncludeView.jsp" flush="true"></jsp:include>
	</div>
	<!--TERMINA TAB NOTIFICACIONES-->
	
	<!--COMIENZA TAB DOCUMENTOS-->
	<div id="tabs-11" class="tabTabsDocs">
		<br>
		<table border="0" cellpadding="2">
			<tr>
				<td align="left">
					<input type="button" id="btnAdjuntarDocumento" value="Adjuntar documento"  class="back_button"/>					
				</td>
			</tr>
		</table>
		<br>
		<table id="gridDetalleFrmPrincipal"></table>
		<div id="pager1Documentos"></div>
		<form name="frmDococumentos" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
			<input type="hidden" name="documentoId" />
		</form>
		<form name="frmDoc2" action="<%= request.getContextPath() %>/GenerarDocumentoDirecto.do" method="post">
			<input type="hidden" name="formaId" />
			<input type="hidden" name="numeroUnicoExpediente" />
		</form>
		<table id="tableDocs">
			<tr>
				<td>Total de documentos:</td>
				<td id="idTotalDocumentos">&nbsp;</td>
			</tr>
		</table>
	</div>
	<!--TERMINA TAB DOCUMENTOS-->
    
	
	
	</div>
	<!--Terminan Divs Principales-->

	<!--Div para la ventana modal que muestra el detalle del resolutivo de audiencia-->
	<div id="divAgregarResolutivo" style="display: none">
		<table width="331" cellspacing="0" cellpadding="0">
			<tr>
				<td width="329" align="left"><strong>Temporizador de
						Video:</strong>
				</td>
			</tr>
			<tr>
				<td align="left"><input type="text" id="tempVideo"
					disabled="disabled" />
				</td>
			</tr>
			<tr>
				<td align="left"><strong>Resolutivo:</strong>
				</td>
			</tr>
			<tr>
				<td align="left"><textarea rows="10" cols="50" id="resolutivo"
						disabled="disabled"></textarea>
				</td>
			</tr>
		</table>
	</div>
	
	<!--Div para ventana modal del tipo de mandamieto  BORRAR-->
	<div id="divTipoMandamiento" style="display: none">
	
		<table width="400" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="20">&nbsp;</td>
	        <td colspan="2" align="center">
	        	<strong>Tipo de Mandamiento Judicial</strong>
	       	</td>
	        <td width="20">&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td width="160" align="right">&nbsp;</td>
	        <td width="200">&nbsp;</td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<strong>Tipo de Mandamiento:</strong>
	       	</td>
	        <td>
	        	<select id="tipoMandamiento" style="width: 200px;">
	              <option value="0">-Seleccione-</option>
	            </select>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<strong>Nombre del Imputado:</strong>
	       	</td>
	        <td>
	        	<select id="nombreDelImputado" style="width: 200px;">
	          		<option value="0">-Seleccione-</option>
	        	</select>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="divEtiTipoSentencia">
		        	<strong>Tipo de Sentencia:</strong>
	            </div>
	        </td>
	        <td>
	        	<div id="divCbxTipoSentencia">
	                <select id="tipoSentencia" style="width: 200px;">
	                    <option value="0">-Seleccione-</option>
	                </select>
	        	</div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="divEtiFechaInicioSentencia">
	        		<strong>Fecha Inicio:</strong>
	        	</div>
	        </td>
	        <td>
	        	<div id="divFechaInicioSentencia">
	        		<input type="text" id="fechaInicioSentencia" style="width: 100px;"/>
	        	</div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="divEtiFechaFinSentencia">
	        		<strong>Fecha Fin:</strong>
	        	</div>
	       	</td>
	        <td>
	        	<div id="divFechaFinSentencia">
	        		<input type="text" id="fechaFinSentencia" style="width: 100px;"/>
	        	</div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">&nbsp;</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	      </tr>
	    </table>
	</div>
	
	<!-- div para el alert dinamico -->
	<div id="dialog-Alert" style="display: none">
		<table align="center">
			<tr>
				<td align="center"><span id="divAlertTexto"></span></td>
			</tr>
		</table>
	</div>
	
	
	<div id="divGenerarSentencia" style="display: none">
		<fieldset>
			<legend>N&uacute;mero &Uacute;nico De Sentenciado</legend>
			<div id="nusImputado" ></div>
		</fieldset>
		<fieldset>
			<legend>Datos De La Sentencia</legend>
			<table width="400" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<strong>Nombre del Imputado:</strong>
	       	</td>
	        <td>
	        	<input type="text" id="nombreImputado" style="width: 200px;" readonly="readonly">
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
		        <strong>Tipo De Sentencia:</strong>
	        </td>
	        <td>
                <select id="tipoDeSentencia" style="width: 200px;">
                    <option value="0">-Seleccione-</option>
                </select>
	        </td>
	        <td>&nbsp;</td>
	      </tr>	      
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<strong>&iquest;Presenta Lesiones?:</strong>
	       	</td>
	        <td>
	        	<input type="radio" name="bLesion" value="1"> S&iacute;
				<input type="radio" name="bLesion" value="0" checked="checked"> No
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<strong>Fecha Inicio:</strong>
	        </td>
	        <td>
	        	<input type="text" id="fInicioSentencia" style="width: 100px;"/>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<strong>Fecha Fin:</strong>
	       	</td>
	        <td>
	        	<input type="text" id="fFinSentencia" style="width: 100px;"/>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<strong>Puntos Por Acumular:</strong>
	       	</td>
	        <td>
	        	<input type="text" id="puntosPorAcumular" style="width: 100px;"/>
	        </td>
	        <td>&nbsp;</td>
	      </tr>	      
	    </table>
		</fieldset>	
	</div>
		
		
	<!-- Comienza div Relacionar Medios de Prueba-->
	<div id="divAgregarMJ" style="display: none">
		<table width="700" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="150">&nbsp;</td>
		    <td width="300">&nbsp;</td>
		    <td colspan="2">&nbsp;</td>
		  </tr>
		  <tr>
		    <td height="250" colspan="4">
		    	<table  id="gridAgregarMJ" width="100%"></table>
				<div id="pagerGridAgregarMJ"></div>
		    </td>
		  </tr>
		</table>
	</div> 
	<!-- Termina div Relacionar Medios de Prueba-->
	
	<div id="divRelacionarDP" style="display: none">
		<table width="700" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="150">&nbsp;</td>
		    	<td width="300">&nbsp;</td>
		    	<td colspan="2">&nbsp;</td>
		    </tr>
		    <tr>
		    	<td id="trGridDelitosPRRDPPV" colspan="4" height="250">
		    		<table id="gridCatDelitosRDPPV"></table>
		    		<div id="pager1"></div>
					<br/>				
				</td>
			</tr>
		</table>
	</div>

	<form name="verMandamientoForm" id="verMandamientoForm"
		action="<%=request.getContextPath()%>/ConsultarContenidoArchivoDigital.do" method="post">
		<input type="hidden" name="documentoId" />
		<input type="hidden" name="archivoDigitalId" />
	</form>
</body>
</html>