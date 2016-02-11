<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
<%@page import="mx.gob.segob.nsjp.web.reinsercionsocial.action.DatosGeneralesReinsercionAction"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>

<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-nested.tld" prefix="nested"%>


<script type="text/javascript">
	//Variable para controlar la carga del grid de expedientes
	var cargaPrimeraExpPorEstatus = true;
	var cargaPrimeraSolsXAtndr = true;
	var contextoPagina = "${pageContext.request.contextPath}";
		
	var estatusNuevas = "";
	estatusPorAtender  = "<%=EstatusExpediente.POR_ATENDER.getValorId()%>";
	estatusAtendidas  = "";
	estatusLibertad = "";
	var estatusEjecucion = <%=EstatusExpediente.ABIERTO_EJECUCION.getValorId()%>;
	var estatusAbierto = <%=EstatusSolicitud.ABIERTA.getValorId()%>;
	var estatusCerrado = <%=EstatusSolicitud.CERRADA.getValorId()%>;
	var estatusConsultaExpediente = 0;
	
	var fechaini  = "";
	var fechafin  = "";
	var pp = "penaPrivativa";
	var pa ="penaAlternativa";
	
	var idTipoSolicitudCTI = <%=TiposSolicitudes.ESTUDIOS_CTI.getValorId()%>;
	var estatusAbierto = <%=EstatusSolicitud.ABIERTA.getValorId()%>;
	var estatusCerrado = <%=EstatusSolicitud.CERRADA.getValorId()%>;
	
	
	$(document).ready(
		function() {
			cargaGridExpedientesPorEstatus(estatusEjecucion,fechaini,fechafin);
			restablecerPantallas();	
		}
	);
	
	function restablecerPantallas(){
		ajustarGridAlCentro($("#gridExpedientesPorEstatus"));
		ajustarGridAlCentro($("#gridSolsXAtndr"));
	}
	
	/*
	*Funcion que consulta los expedientes de acuerdo a su estatus y la fecha inicial y final
	*/
	
	function cargaGridExpedientesPorEstatus(estatus,fechaIni,fechaFin){
		estatusConsultaExpediente = estatus;
		if(cargaPrimeraExpPorEstatus == true){
			jQuery("#gridExpedientesPorEstatus").jqGrid({ 
				url:'', 
				datatype: "local", 
				postData: {
					estatusExpediente: estatusEjecucion
				},
	    		mtype: "POST",
				colNames:['N&uacute;mero De Caso','N&uacute;mero De Causa', 'Procedimiento de ejecuci&oacute;n', 'Nombre Sentenciado', 'Delito(s)', 'Fecha De Creaci&oacute;n', 'NumExpId'], 
				colModel:[ 	{name:'noCaso',index:'1', width:140}, 
							{name:'noCausa',index:'2', width:70}, 
							{name:'carpeta',index:'3', width:140}, 
							{name:'nombreSentenciado',index:'4', width:140},
							{name:'delitos',index:'5', width:140, cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }},
							{name:'fechaCreacion',index:'6', width:70},
							{name:'numExpId',index:'5', width:70, hidden:true}
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
				sortorder: "asc"
			}).navGrid('#pagerGridExpedientesPorEstatus',{edit:false,add:false,del:false});
		
			cargaPrimeraExpPorEstatus = false;
		}else{
			jQuery("#gridExpedientesPorEstatus").jqGrid(
				'setGridParam', {
					url:'<%=request.getContextPath()%>/obtenerSentenciasGrid.do',
					postData: {
						estatusExpediente: estatus
					},
					datatype: "xml" 
				}
			);
			$("#gridExpedientesPorEstatus").trigger("reloadGrid");
		}
		jQuery('#divGridExpedientePorEstatus').show();
		jQuery('#divGridSolsXAtndr').hide();
	}
	
	function cargaGridSolsXAtndr(tipoSolicitud, idArea, estatus) {
		estatusConsulta = estatus;
		if(cargaPrimeraSolsXAtndr == true){
			//Grid de Solicitudes por atender
			jQuery("#gridSolsXAtndr").jqGrid({ 
				url:"<%= request.getContextPath()%>/consultaSolsXAtnder.do",
				mType: "POST",
				postData :{
					tipoSoliciutd : tipoSolicitud,
					idArea 		: idArea,
					estatus 	: estatus,
					ignorarArea	: true
				},
				datatype: "xml",
				colNames:['No. Caso','Expediente', 'Folio','Estatus','Fecha Creaci&oacute;n','Fecha Limite','Instituci&oacute;n','Remitente'], 
				colModel:[ 	{name:'caso',index:'caso', width:150,hidden:true},
				           	{name:'expediente',index:'expediente', width:180,align:'center'}, 
							{name:'folio',index:'folio', width:90,align:'center'}, 
							{name:'estatus',index:'estatus', width:90,align:'center'}, 
							{name:'fechaCreacion',index:'fechaCreacion', width:100,align:'center'},
							{name:'fechaLimite',index:'fechaLimite', width:100,align:'center',hidden:true},
							{name:'institucion',index:'institucion', width:145,align:'center'},
							{name:'remitente',index:'remitente', width:200,align:'center'}
						],
				pager: jQuery("#pagerGridSolsXAtndr"),
				rowNum:10,
				rowList:[10,20,30,40,50,60,70,80,90,100],
				height: 425,
				autowidth: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(id) {
					var datosSol = jQuery("#gridSolsXAtndr").jqGrid('getRowData',id);
					if (datosSol.id == undefined || datosSol.id == "undefined"){
						datosSol.id = id;
					}
					dblClickRowBandejaSolicitudes(datosSol, estatusConsulta,
							"Estudios de Consejo T&eacute;cnico Interdisciplinario", 
							"<%=DatosGeneralesReinsercionAction.ESTUDIOS_CTI%>");
				}
			}).navGrid("#pagerGridSolsXAtndr",{edit:false,add:false,del:false});
		cargaPrimeraSolsXAtndr = false;
		}else{
			jQuery("#gridSolsXAtndr").jqGrid('setGridParam', { 
					url:"<%= request.getContextPath()%>/consultaSolsXAtnder.do",
					mType: "POST",
					postData :{
						tipoSoliciutd : tipoSolicitud,
						idArea : idArea,
						estatus : estatus,
						ignorarArea	: true
					},
					datatype: "xml"
				}
			);		
			$("#gridSolsXAtndr").trigger("reloadGrid");
		}
		jQuery('#divGridExpedientePorEstatus').hide();
		jQuery('#divGridSolsXAtndr').show();
	}
	
	function dblClickRowBandejaSolicitudes(datosSol, estatus, titulo, enviarA) {
		customVentana(	"idVentanaDatosGenerales"+ getValorDelGrid(datosSol.id), 
						titulo, 
						"/consultarDatosGeneralesRS.do", 
						"?cNumeroExpediente="+ getValorDelGrid(datosSol.expediente) + 
						"&solicitudId="+ getValorDelGrid(datosSol.id) +
						"&estatus="+ estatus +
						"&enviarA="+ enviarA,
						function () {
							$("#gridSolsXAtndr").trigger("reloadGrid");
						});

	}
	
	function consultarDatosGenerales(id){
		customVentana(	"idVentanaDatosGenerales", 
						"Visor del Procedimiento de Ejecuci&oacute;n", 
						"/consultarDatosGeneralesRS.do", 
						"?sentenciaId="+id,
						function () {
							$("#gridExpedientesPorEstatus").trigger("reloadGrid");
						}
					);
	}
	
	function regresaGrid(){
		
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
	
	function creaVentanaAsignacionPrograma() {
		if (estatusConsultaExpediente != estatusEjecucion){
			customAlert("Para poder asignar un programa se debe seleccionar una sentencia que este <strong>En Ejecuci&oacute;n</strong>.",
						"Error");
		}else{
			var sentenciaId = jQuery("#gridExpedientesPorEstatus").jqGrid('getGridParam','selrow');
			var ret = jQuery("#gridExpedientesPorEstatus").jqGrid('getRowData',sentenciaId); 
			if (sentenciaId) { 		
				var cNumeroExpediente = getValorDelGrid(ret.carpeta);
				
				customVentana(	"iframewindowExpedietesPenaPrivativa", 
								"Asignar Programa de Reinserci&oacute;n Social", 
								"/asignarProgramaRS.do", 
								"?sentenciaId="+sentenciaId+"&cNumeroExpediente=" + cNumeroExpediente );
			} else { 
				customAlert("Para poder asignar un programa a un sentenciado, es necesario seleccionar la "
						+"sentencia a la cual se asociar&aacute; el programa. <br/>"
						+"Por favor seleccione un registro de la tabla de sentencias disponibles.");
			}
		}
	}
	
</script>
<div id="mainContent">
	<div class="ui-layout-center">
		<div class="ui-layout-content">
			<div class="ui-layout-north">
				<div id="divGridExpedientePorEstatus">
					<table id="gridExpedientesPorEstatus"></table>
					<div id="pagerGridExpedientesPorEstatus"
						style="vertical-align: top;"></div>
				</div>
				<div id="divGridSolsXAtndr">
					<table id="gridSolsXAtndr"></table>
					<div id="pagerGridSolsXAtndr" style="vertical-align: top;"></div>
				</div>
			</div>
		</div>
	</div>
</div>
	