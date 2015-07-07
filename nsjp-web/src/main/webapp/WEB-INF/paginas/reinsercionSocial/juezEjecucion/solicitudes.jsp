<%@page import="mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.web.reinsercionsocial.action.DatosGeneralesReinsercionAction"%>
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
var cargaPrimeraSolsXAtndr = true;

var contextoPagina = "${pageContext.request.contextPath}";


var idTipoSolicitud = <%=TiposSolicitudes.REINSERCION_SOCIAL.getValorId()%>; 
var estatusAbierto = <%=EstatusSolicitud.ABIERTA.getValorId()%>;
var estatusCerrado = <%=EstatusSolicitud.CERRADA.getValorId()%>;
var estatusConsulta = "";


$(document).ready(
	function() {
			// SE oculta la pestaña de actuaciones
// 			$('#liActuacionesRS').hide();
			
			$("#menuSolicitudes").jstree({ 
	        	"themes" : {
		            "theme" : "classic",
		            "dots" : true,
		            "icons" : true
		        },
				"plugins" : [ "themes", "html_data" ]
			});	
	
			cargaGridSolsXAtndr(idTipoSolicitud, estatusAbierto);			
	}
);


/*
*Funcion que consulta los expedientes de acuerdo a su estatus y la fecha inicial y final
*/

function cargaGridSolsXAtndr(tipoSolicitud, estatus) {
	estatusConsulta = estatus;
	if(cargaPrimeraSolsXAtndr == true){
		//Grid de Solicitudes por atender
		jQuery("#gridSolsXAtndr").jqGrid({
			url  : '<%= request.getContextPath()%>/consultarDocumentosConCriterios.do',
			mType: "POST",
			postData : {
				tipoSoliciutd: tipoSolicitud,
				estatus: estatus,
				numeroExpedienteId: jQuery("#numeroExpedienteId").val(), 
				tipoConsulta: "<%=SolicitudDTO.POR_ATENDER%>"
			},
			datatype: "xml", 
			colNames:['No. Caso','No. Expediente', 'Folio','Estatus','Fecha Creación','Fecha Limite','Institución Destinatario','Destinatario', 'IdExpediente', 'Nombre Del Documento','Instituci&oacute;n', 'Remitente', 'Tipo de Documento'], 
			colModel:[ 	{name:'caso',index:'caso', width:150, hidden:true},
			           	{name:'expediente',index:'expediente', width:180,align:'center', hidden:true}, 
						{name:'folio',index:'folio', width:110,align:'center'}, 
						{name:'estatus',index:'estatus', width:100,align:'center', hidden:true}, 
						{name:'fechaCreacion',index:'fechaCreacion', width:100,align:'center'},
						{name:'fechaLimite',index:'fechaLimite', width:100,align:'center', hidden:true},
						{name:'institucionDest',index:'institucionDest', width:100,align:'center', hidden:true},
						{name:'destinatario',index:'destinatario', width:200,align:'center', hidden:true},
						{name:'expedienteId',index:'expedienteId', width:0,align:'center', hidden:true},
						{name:'nombreDoc',index:'nombreDoc', width:0,align:'center', hidden:true},
						{name:'institucionRem',index:'institucionRem', width:150,align:'center'},
						{name:'remitente',index:'remitente', width:220,align:'center'},
						{name:'nombreDocAdj',index:'nombreDocAdj', width:200,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; } }
					],
			pager: jQuery("#pagerGridSolsXAtndr"),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autoheight: true,
			autowidth: true,
			sortname: 'folio',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(id) {
				var datosSol = jQuery("#gridSolsXAtndr").jqGrid('getRowData',id);
				if (datosSol.id === undefined || datosSol.id === "undefined"){
					datosSol.id = id;
				}
				dblClickRowDocumentosPorSolicitud(datosSol, estatusConsulta);
			}
		}).navGrid("#pagerGridSolsXAtndr",{edit:false,add:false,del:false});
		cargaPrimeraSolsXAtndr = false;
	}else{
		jQuery("#gridSolsXAtndr").jqGrid('setGridParam', { 
			url  : '<%= request.getContextPath()%>/consultarDocumentosConCriterios.do',
			mType: "POST",
			postData : {
				tipoSoliciutd: tipoSolicitud,
				estatus: estatus,
				numeroExpedienteId: numeroExpedienteId, 
				tipoConsulta: "<%=SolicitudDTO.POR_ATENDER%>"
			},
			datatype: "xml"
		});		
		$("#gridSolsXAtndr").trigger("reloadGrid");
	}	
}

function dblClickRowDocumentosPorSolicitud(datosSol, estatus) {
	var sentenciaId = jQuery('#sentenciaId').val();
	customVentana(	"idVentanaDatosGenerales"+getValorDelGrid(datosSol.id), 
					"Detalle Solicitud", 
					"/documentosPorSolicitudRS.do", 
					"?cNumeroExpediente="+ getValorDelGrid(datosSol.expediente) + 
					"&solicitudId="+ getValorDelGrid(datosSol.id) +
					"&estatus="+ estatus + 
					"&sentenciaId=" + sentenciaId,
					 function()	{
						$("#gridSolsXAtndr").trigger("reloadGrid"); 
					 }
				  );
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


<table >
	<tr>
	<td width="20%" valign="top">
		<div id="menuSolicitudes" class="demo">
			<ul>
				<li>
					<a href="javascript:void(0);" onclick="cargaGridSolsXAtndr(idTipoSolicitud, estatusAbierto);">
						Por Atender
					</a>
				</li>
				<li>
					<a onclick="cargaGridSolsXAtndr(idTipoSolicitud, estatusCerrado);">
						Atendidas
					</a>	
				</li>
			</ul>
		</div>
	</td>
	<td width="80%">
		<div id="divGridExpedientePorEstatus">
			<table id="gridSolsXAtndr"></table>
			<div id="pagerGridSolsXAtndr" style="vertical-align: top;"></div>
		</div>	
	</td>
	</tr>
</table>



	