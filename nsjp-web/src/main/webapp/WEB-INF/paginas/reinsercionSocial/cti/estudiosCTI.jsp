<%@ page import="org.apache.commons.lang.math.NumberUtils"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<script type="text/javascript">
	var estatusSolicitud = '<%= request.getParameter("estatus") != null ? request.getParameter("estatus") : "0" %>';

	$(document).ready(
		function() {
			$("#cbxAccionesTab").change(
				function(e){
					seleccionaActuacion();
				}
			);
			
			if (estatusSolicitud == '<%=EstatusSolicitud.CERRADA.getValorId()%>'){
				$( "#tabsContenido").tabs({disabled: [2]});
				$( "#liActuacionesRS").hide();				
			}			
			cargaActuaciones(1);
		}
	);		
	
	/*
	*Funcion que realiza la carga del combo de Actuaciones
	*/
	function cargaActuaciones(id) {
		bloquearPantalla();
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarActuaciones.do?id='+id+'&numeroExpediente='+cNumeroExpediente+'&numeroExpedienteId='+numeroExpedienteId,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$('#cbxAccionesTab').append('<option value="-1">--Seleccione una opci&oacute;n--</option>');
				$(xml).find('catActuaciones').each(function(){
					$('#cbxAccionesTab').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});            
			}
		});
	}		 
	
	function seleccionaActuacion(){
		var selected = $("#cbxAccionesTab option:selected");
		var confActividadId=selected.val();
		var actividad=0;
		var formaID=4;
		var titulo="op";
		var usaeditor="";
		var estatusId="";
		bloquearPantalla();
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/obtenerConfActividadDocumento.do?idConf='+confActividadId,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				actividad=$(xml).find('confActividadDocumentoDTO').find('tipoActividadId').text();
				formaID=$(xml).find('confActividadDocumentoDTO').find('formaId').text();
				titulo=$(xml).find('confActividadDocumentoDTO').find('nombreDocumento').text();
				usaeditor=$(xml).find('confActividadDocumentoDTO').find('usaEditor').text();
				estatusId=$(xml).find('confActividadDocumentoDTO').find('estadoCambioExpediente').find('idCampo').text();
			}
		});
		actuacion=actividad;

		if(usaeditor == "true"){			
			var parametros = "?formaId="+formaID+"&numeroUnicoExpediente="+cNumeroExpediente + "&actividadId=" + actividad + "&sentenciaId=" + sentenciaId;
			
			customVentana("iframewindowElaborarDocumentoRS", ""+titulo, "/elaborarDocumentosRS.do", parametros, function (){
				$('#cbxAccionesTab').empty();
				cargaActuaciones("");
				llenarGridDocumentos(listaDesplegada);	
			});		
		}
	}

	function actualizarEstatusSolicitud(estatus){
		$.ajax({
			async: true,
			type: 'POST',
			url: '<%=request.getContextPath()%>/actualizaEstatusSolicitud.do',
			dataType: 'xml',
        	data: {
        		solicitudId : <%=request.getParameter("solicitudId") != null 
        							? request.getParameter("solicitudId") 
        							: "0"%>,
        		estatus : estatus
        	},
			success: function(xml){
				customAlert($(xml).find('body').text());
			}
		});
	}
	
</script>				

<fieldset>
	<legend>Estudios:</legend>
	<table border="0">
		<tr>
			<td>
			Tipo: <html:select name="DatosGeneralesReinsercionForm" property="actuacionSeleccionada"
									   styleId="cbxAccionesTab">
				  </html:select>
			</td>
		</tr>
	</table>
</fieldset>				

<fieldset>
	<legend>Finalizar Solicitud:</legend>
	<table border="0">
		<tr>
			<td>
				<input type="button" name="finalizarSolicitud" id="finalizarSolicitud" value="Finalizar Solicitud" class="ui-button ui-corner-all ui-widget" onClick="actualizarEstatusSolicitud(<%=EstatusSolicitud.CERRADA.getValorId()%>);" />
			</td>
		</tr>
	</table>
</fieldset>				
