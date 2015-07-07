
<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-nested.tld" prefix="nested"%>

<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes" %>
<%@page import="mx.gob.segob.nsjp.web.reinsercionsocial.action.DatosGeneralesReinsercionAction"%>

<script type="text/javascript">
	//Variable para controlar la carga del grid de expedientes
	var cargaPrimeraExpPorEstatus = true;
	var cargaPrimeraSolsGeneradas = true;
	var cargaPrimeraSolsXAtndr = true;
	var contextoPagina = "${pageContext.request.contextPath}";
	
	
	var estatusNuevas = "";
	var fechaini  = "";
	var fechafin  = "";
	var pp = "penaPrivativa";
	
	estatusPorAtender  = "<%=EstatusExpediente.POR_ATENDER.getValorId()%>";
	estatusAtendidas  = "<%=EstatusExpediente.EN_PROCESO.getValorId()%>";
	estatusLibertad = "<%=EstatusExpediente.CERRADO.getValorId()%>";
	estatusEnTramite  = "<%=EstatusExpediente.EN_TRAMITE.getValorId()%>";
	estatusPendienteIngreso  = "<%=EstatusExpediente.PENDIENTE_DE_INGRESO.getValorId()%>";
	estatusEsperaSentenciado  = "<%=EstatusExpediente.EN_ESPERA_DE_SENTENCIADO.getValorId()%>";
	estatusEjecucion  = "<%=EstatusExpediente.ABIERTO_EJECUCION.getValorId()%>";
	estatusEnviado = "<%=EstatusExpediente.ENVIADO.getValorId()%>";
	estatusRechazado = "<%=EstatusExpediente.RECHAZADO.getValorId()%>";
	
	var estatusAbierto = <%=EstatusSolicitud.ABIERTA.getValorId()%>;
	var estatusCerrado = <%=EstatusSolicitud.CERRADA.getValorId()%>;
	var estatusConsulta = 0;
	
	var idTipoSolicitudCER = "<%=TiposSolicitudes.CONSTANCIAS_CERERESO.getValorId()%>";
	var idTipoSolicitudCTI = "<%=TiposSolicitudes.ESTUDIOS_CTI.getValorId()%>";
	var idTipoSolicitudDGPRS = "<%=TiposSolicitudes.INFORMACION_DGPRS.getValorId()%>";
	var idTipoAvisoDGPRS = "<%=TiposSolicitudes.AVISO_DGPRS.getValorId()%>";
	var tipoSolConsulta = 0;
	
	var pa ="penaAlternativa";
	var idWindowDetalleSolicitud= 0;
	
	$(document).ready(
		function() {
				cargaGridExpedientesPorEstatus();
				restablecerPantallas();	
		}
	);
	
	function restablecerPantallas(){
		ajustarGridAlCentro($("#gridExpedientesPorEstatus"));
	}
	
	/*
	*Funcion que consulta los expedientes de acuerdo a su estatus y la fecha inicial y final
	*/
	
	function cargaGridExpedientesPorEstatus(estatus,fechaIni,fechaFin){
		if(cargaPrimeraExpPorEstatus == true){
			jQuery("#gridExpedientesPorEstatus").jqGrid({ 
				url:'', 
				datatype: "local", 
				postData: {
					estatusExpediente: <%=EstatusExpediente.POR_ATENDER.getValorId()%>
				},
	    		mtype: "POST",
				colNames:['Número De Caso','Número De Causa', 'Procedimiento de Ejecuci&oacute;n', 'Nombre Sentenciado', 'Delito(s)','Fecha De Creación', 'NumExpId'], 
				colModel:[ 	{name:'noCaso',index:'1', width:140}, 
							{name:'noCausa',index:'2', width:70}, 
							{name:'carpeta',index:'3', width:140}, 
							{name:'nombreSentenciado',index:'4', width:140},
							{name:'delitos',index:'5', width:140, cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }},
							{name:'fechaCreacion',index:'6', width:70},
							{name:'numExpId',index:'7', width:70, hidden:true}
						],
				pager: jQuery('#pagerGridExpedientesPorEstatus'),
				rowNum:10,
				rowList:[10,20,30,40,50,60,70,80,90,100],
				autowidth: true,
				height:360,
				sortname: 'Sentencia_id',
				caption:"Sentencias",
				autowidth:true,
				shrinkToFit:true,
				viewrecords: true,
				onSelectRow: function(id){
	
					},
				ondblClickRow: function(id) {
					consultarDatosGenerales(id);
				},
				sortorder: "asc", 
				estatus : 0
			}).navGrid('#pagerGridExpedientesPorEstatus',{edit:false,add:false,del:false});
		
			cargaPrimeraExpPorEstatus = false;
		}else{
			jQuery("#gridExpedientesPorEstatus").jqGrid(
				'setGridParam', {
					url:'<%=request.getContextPath()%>/obtenerSentenciasGrid.do',
					postData: {
						estatusExpediente: estatus
					},
					datatype: "xml",
					estatus :estatus
				}
			);
			$("#gridExpedientesPorEstatus").trigger("reloadGrid");
		}
		jQuery("#divGridExpedientePorEstatus").show();
		jQuery('#divGridSolsXAtndr').hide();
		jQuery("#divGridSolsGeneradas").hide();
	}
	
	function cargaGridSolsGeneradas(tipoSolicitud, idArea, estatus) {
		estatusConsulta = estatus;
		tipoSolConsulta = tipoSolicitud;
		if(cargaPrimeraSolsGeneradas == true){
			//Grid de Solicitudes por atender
			jQuery("#gridSolsGeneradas").jqGrid({ 
				url:"<%= request.getContextPath()%>/consultaSolsGeneradas.do",
				mType: "POST",
				postData :{
					tipoSoliciutd : tipoSolicitud,
					idArea 		: idArea,
					estatus 	: estatus
				},
				datatype: "xml",
				colNames:['No. Caso','Expediente', 'Folio','Estatus','Fecha Creación','Fecha Limite','Institución','Remitente'], 
				colModel:[ 	{name:'caso',index:'caso', width:150,hidden:true},
				           	{name:'expediente',index:'expediente', width:180,align:'center'}, 
							{name:'folio',index:'folio', width:100,align:'center'}, 
							{name:'estatus',index:'estatus', width:100,align:'center'}, 
							{name:'fechaCreacion',index:'fechaCreacion', width:100,align:'center'},
							{name:'fechaLimite',index:'fechaLimite', width:100,align:'center',hidden:true},
							{name:'institucion',index:'institucion', width:145,align:'center'},
							{name:'remitente',index:'remitente', width:200,align:'center'}
						],
				pager: jQuery("#pagerGridSolsGeneradas"),
				rowNum:10,
				rowList:[10,20,30,40,50,60,70,80,90,100],
				height: 420,
				autowidth: true,
				sortname: 'fechaCreacion',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(id) {
					dblClickRowBandejaSolicitudesGen(id);
				}
			}).navGrid("#pagerGridSolsGeneradas",{edit:false,add:false,del:false});
		cargaPrimeraSolsGeneradas = false;
		}else{
			jQuery("#gridSolsGeneradas").jqGrid('setGridParam', { 
					url:"<%= request.getContextPath()%>/consultaSolsGeneradas.do",
					mType: "POST",
					postData :{
						tipoSoliciutd : tipoSolicitud,
						idArea : idArea,
						estatus : estatus
					},
					datatype: "xml"
				}
			);		
			$("#gridSolsGeneradas").trigger("reloadGrid");
		}
		jQuery('#divGridExpedientePorEstatus').hide();
		jQuery('#divGridSolsXAtndr').hide();
		jQuery('#divGridSolsGeneradas').show();
	}
	
	function cargaGridSolsXAtndr(tipoSolicitud, estatus) {
		estatusConsulta = estatus;
		if(cargaPrimeraSolsXAtndr == true){
			//Grid de Solicitudes por atender
			jQuery("#gridSolsXAtndr").jqGrid({ 
				url:"<%= request.getContextPath()%>/consultaSolsRecibidasPorSentencia.do",
				mType: "POST",
				postData :{
					tipoSolicitud : tipoSolicitud,
					estatus 	: estatus
				},
				datatype: "xml",
				colNames:['Procedimiento Ejecuci&oacute;n','Folio', 'Estatus','Sentenciado','Delito(s)','Fecha Creaci&oacute;n','sentenciaId'], 
				colModel:[ 	{name:'procEjecucion',index:'procEjecucion', width:180, align:'center'}, 
							{name:'folio',index:'folio', width:100,align:'center'}, 
							{name:'estatus',index:'estatus', width:100,align:'center'}, 
							{name:'sentenciado',index:'sentenciado', width:160,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; } },
							{name:'delito',index:'delito', width:160,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; } },
							{name:'fechaCreacion',index:'fechaCreacion', width:100,align:'center'},
							{name:'sentenciaId',index:'sentenciaId', width:100,align:'center', hidden:true}
						],
				pager: jQuery("#pagerGridSolsXAtndr"),
				rowNum:10,
				rowList:[10,20,30,40,50,60,70,80,90,100],
				height: 430,
				autowidth: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(id) {
					var datosSol = jQuery("#gridSolsXAtndr").jqGrid('getRowData',id);
					if (estatusConsulta == estatusAbierto){
						crearVentanaSolicitud(id, "Visor General del Procedimiento de Ejecuci&oacute;n", 
							"", estatusConsulta, "solicitudesDGPRS", "#gridSolsXAtndr", getValorDelGrid(datosSol.sentenciaId));
					}else if (estatusConsulta == estatusCerrado){
						var sentenciaId = getValorDelGrid(datosSol.sentenciaId);
						customVentana(id+"-"+sentenciaId,
								"Detalle de la solicitud",
								"/consultaDetalleSolicitudRS.do",
								"?solicitudId="+id+"&sentenciaId="+sentenciaId,
								function (){
									
								});
					}				
				}
			}).navGrid("#pagerGridSolsXAtndr",{edit:false,add:false,del:false});
		cargaPrimeraSolsXAtndr = false;
		}else{
			jQuery("#gridSolsXAtndr").jqGrid('setGridParam', { 
					url:"<%= request.getContextPath()%>/consultaSolsRecibidasPorSentencia.do",
					mType: "POST",
					postData :{
						tipoSolicitud : tipoSolicitud,
						estatus : estatus
					},
					datatype: "xml"
				}
			);		
			$("#gridSolsXAtndr").trigger("reloadGrid");
		}
		jQuery("#divGridExpedientePorEstatus").hide();
		jQuery('#divGridSolsXAtndr').show();
		jQuery("#divGridSolsGeneradas").hide();
	}
	
	function dblClickRowBandejaSolicitudesGen(rowID){
		if (estatusConsulta == estatusAbierto){
			idWindowDetalleSolicitud++;
			customVentana(	"iframewindowDetalleSolicitud"+idWindowDetalleSolicitud,
							"Detalle Solicitud", 
							"/consultarDetalleSolicitudBandejaGen.do"
							,"?idSolicitud="+rowID + "&tipoUsuario=1",
							function () {
								$("#gridGeneral").trigger("reloadGrid");
							});
		}else if (estatusConsulta == estatusCerrado){
			var titulo = "Detalle Solicitud";
			var enviarA = "";
			if (tipoSolConsulta == idTipoSolicitudCER){
				titulo = "Constancias de CERERESO";
				enviarA = "<%=DatosGeneralesReinsercionAction.CONSTANCIAS_CERERESO%>";
			}else if (tipoSolConsulta == idTipoSolicitudCTI){
				titulo = "Estudios de Consejo T&eacute;cnico Interdisciplinario"; 
				enviarA = "<%=DatosGeneralesReinsercionAction.ESTUDIOS_CTI%>";
			}
			var datosSol = jQuery("#gridSolsGeneradas").jqGrid('getRowData',rowID);
			
			crearVentanaSolicitud(rowID,titulo,getValorDelGrid(datosSol.expediente),estatusConsulta,enviarA,"#gridSolsGeneradas","");
			
// 			customVentana("idVentanaDatosGenerales"+ rowID, 
// 					titulo, 
// 					"/consultarDatosGeneralesRS.do", 
// 					"?cNumeroExpediente="+ getValorDelGrid(datosSol.expediente) + 
// 					"&solicitudId="+ rowID +
// 					"&estatus="+ estatusConsulta +
// 					"&enviarA="+ enviarA,
// 					function () {
// 						$("#gridSolsGeneradas").trigger("reloadGrid");
// 					});
		}
	}
	
	function consultarDatosGenerales(id){
		customVentana(	"idVentanaDatosGenerales"+id, 
						"Visor del Procedimiento de Ejecuci&oacute;n", 
						"/consultarDatosGeneralesRS.do", 
						"?sentenciaId="+id,
						function(){
							$("#gridExpedientesPorEstatus").trigger("reloadGrid");
						});
	}
	
	function crearVentanaSolicitud(rowID, titulo, cNumExp, estatus, enviarA, nombreGrid, sentenciaId){
		var cadenaParametros = "";
		if (cNumExp != ""){
			cadenaParametros += "?cNumeroExpediente="+ cNumExp;
		}else{
			cadenaParametros += "?sentenciaId=" + sentenciaId;
		}
		cadenaParametros += "&solicitudId="+rowID+"&estatus="+estatusConsulta+"&enviarA="+ enviarA;
		customVentana("idVentanaDatosGenerales"+ rowID, 
			titulo, 
			"/consultarDatosGeneralesRS.do", 
			cadenaParametros,
			function () {
				$(nombreGrid).trigger("reloadGrid");
			});
	}
	
	function creaVentanaDetenidos(expediente) {
		$.newWindow({id:"iframewindowDetenidos", statusBar: true, posx:0,posy:00,width:$(document).width(),height:$(document).height(),title:"Reinserci&oacute;n Social", type:"iframe"});
		$.maximizeWindow("iframewindowDetenidos");
	    $.updateWindowContent("iframewindowDetenidos",'<iframe src="<%= request.getContextPath() %>/reinsercionSocial.do" width="100%" height="100%" />');
	}
	
	function creaVentanaExpedietesPenaPrivativa(expediente) {
		$.newWindow({id:"iframewindowExpedietesPenaPrivativa", statusBar: true, posx:0,posy:00,width:$(document).width(),height:$(document).height(),title:"Reinserci&oacute;n Social", type:"iframe"});
		$.maximizeWindow("iframewindowExpedietesPenaPrivativa");
	    $.updateWindowContent("iframewindowExpedietesPenaPrivativa",'<iframe src="<%= request.getContextPath() %>/consultarRS.do" width="100%" height="100%" />');
		//$.updateWindowContent("iframewindowExpedietesPenaPrivativa",'<iframe src="<%= request.getContextPath() %>/consultarDatosGeneralesRS.do" width="100%" height="100%" />');
	}
	
	function creaVentanaAsignacionPrograma() {
		
		var estatus = jQuery("#gridExpedientesPorEstatus").jqGrid('getGridParam','estatus');
		if (estatus != estatusEjecucion){
			customAlert("Para poder asignar un programa se debe seleccionar una sentencia que este <strong>En Ejecuci&oacute;n</strong>.",
						"Error");
						return false;
		}
		
		jQuery("#dialog-validarSentenciaId").dialog({
			autoOpen: false,
			height: 'auto',
			width:'auto',
			modal: true,
			buttons: {
				Ok: function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
				}
			},
			resizable: false
		});
		
		var sentenciaId = jQuery("#gridExpedientesPorEstatus").jqGrid('getGridParam','selrow');
		var ret = jQuery("#gridExpedientesPorEstatus").jqGrid('getRowData',sentenciaId); 
		if (sentenciaId) { 
		
		var cNumeroExpediente =ret.carpeta;
		
		 if(cNumeroExpediente.indexOf("div") > -1){
		 	var from = cNumeroExpediente.indexOf(">", cNumeroExpediente.indexOf("div"));
			var to = cNumeroExpediente.indexOf("<", from);
			cNumeroExpediente = cNumeroExpediente.substring(from+1, to);
		}
			customVentana(	"iframewindowExpedietesPenaPrivativa", 
							"Asignar Programa de Reinserci&oacute;n Social", 
							"/asignarProgramaRS.do", 
							"?sentenciaId="+sentenciaId+"&cNumeroExpediente=" + cNumeroExpediente );
		} else { 
			jQuery("#dialog-validarSentenciaId" ).dialog("open");
		}
	}
	
	function regresaGrid(){
	
	}
	
	function asignarExpedientes(){
		customVentana("cambiarResponsableExpediente", "Asignar Responsable A Expediente", "/cambiarResponsableExpediente.do");
	}
	
	function getValorDelGrid(valor){
		if(valor != undefined && valor != "undefined"){
		 	if(valor.indexOf("div") > -1){
		 		var from = valor.indexOf(">", valor.indexOf("div"));
				var to = valor.indexOf("<", from);
				if(from != -1 && to != -1) {
					valor = valor	.substring(from+1, to);
				}
			}
		}
		return valor;
	}
</script>

<div id="dialog-validarSentenciaId" title="Error de validaci&oacute;n">
	<p>
	Para poder asignar un programa a un sentenciado, es necesario seleccionar la sentencia a la cual se asociar&aacute; el programa. <br/>
	Por favor seleccione un registro de la tabla de sentencias disponibles.
	</p>
</div>
<div id="mainContent">
	<div class="ui-layout-center">
		<div class="ui-layout-content">
			<div class="ui-layout-north">
				<div id="divGridExpedientePorEstatus">
					<table id="gridExpedientesPorEstatus"></table>
					<div id="pagerGridExpedientesPorEstatus"
						style="vertical-align: top;"></div>
				</div>
				<div id="divGridSolsGeneradas">
					<table id="gridSolsGeneradas"></table>
					<div id="pagerGridSolsGeneradas" style="vertical-align: top;"></div>
				</div>
				<div id="divGridSolsXAtndr">
					<table id="gridSolsXAtndr"></table>
					<div id="pagerGridSolsXAtndr" style="vertical-align: top;"></div>
				</div>
			</div>
		</div>
	</div>
</div>
	