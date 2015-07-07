
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

<script type="text/javascript">

//Variable para controlar la carga del grid de expedientes
var cargaPrimeraExpPorEstatus = true;
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


var pa ="penaAlternativa";



$(document).ready(
	function() {
			cargaGridExpedientesPorEstatus(estatusPorAtender);
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
			url:'<%=request.getContextPath()%>/obtenerSentenciasGrid.do',
			postData: {
				estatusExpediente: estatus
			},
			datatype: "xml", 
    		mtype: "POST",
			colNames:['Número De Caso','Número De Causa', 'Procedimiento de Ejecuci&oacute;n', 'Nombre Sentenciado', 'Delito(s)', 'Fecha De Creación', 'NumExpId'], 
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

}

function consultarDatosGenerales(id){
	customVentana(	"idVentanaDatosGenerales"+id, 
					"Visor del Procedimiento de Ejecuci&oacute;n", 
					"/consultarDatosGeneralesRS.do", 
					"?sentenciaId="+id,
					function (){ 
						$("#gridExpedientesPorEstatus").trigger("reloadGrid");
					}
				);
}


function regresaGrid(){

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
				</div>
			</div>
		</div>
	</div>
	