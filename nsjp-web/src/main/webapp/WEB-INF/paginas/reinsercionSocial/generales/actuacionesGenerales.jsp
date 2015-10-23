<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<script type="text/javascript">
<!--
	$.ajaxSetup({ cache: false });
	var contextoPagina = "${pageContext.request.contextPath}";
	var cNumeroExpediente = "";
	var numeroExpedienteId = "";
	var expedienteId = "";
				
	//Comienza funcion on ready del documento
	$(document).ready(function() {
		$("#cbxAccionesTab").change(function(e){
			var actividadRS = {
				<%
					// como no se puede acceder a las enumeraciones desde Javascript se
					// copia la enumeraci&oacute;n
				
					for (ConfActividadDocumento actividad : ConfActividadDocumento.values() ){
						if(actividad != null 
							&& actividad.getcForward() != null
							&& actividad.getcForward() != ""
							&& actividad.getPosicion() != null
							&& actividad.getPosicion() == ConfActividadDocumento.ACTUACIONES) {
							out.println("\""+actividad.getValorId() + "\":\"" + actividad.getcForward()+"\",");
						}				
					}
				%>
			};
			
			var actuacion = $("#cbxAccionesTab option:selected").val();
			if (actividadRS[""+actuacion] != undefined && actividadRS[""+actuacion] != "undefined"){
				pasoIntermedio(actuacion);
			} else {
				seleccionaActuacion();
			}
		});
		
		cNumeroExpediente = jQuery("#carpeta").val();
		numeroExpedienteId = jQuery("#numeroExpedienteId").val();
		expedienteId = jQuery("#expedienteId").val();
		sentenciaId = jQuery("#sentenciaId").val();
		cargaActuaciones("1");
	});		
	
		function pasoIntermedio(confActDocId){
		
			var titulo = $("#cbxAccionesTab option:selected").text();
		
		    $.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/obtenerDatosAdicionalesRS.do',
				cache: false,
				data: {
					confActDocId : confActDocId,
					sentenciaId : sentenciaId,
					esActuacion : true
				}, 
				async: false,
				dataType: 'html',
				success: function(responseText){
					customConfirm(responseText, titulo);
				}
			});		
		}	
	
	/*
	*Funcion que realiza la carga del combo de Actuaciones
	*/
	function cargaActuaciones(id) {

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

	function seleccionaActuacion(parametrosExtra){
		var selected = $("#cbxAccionesTab option:selected");
		var confActividadId=selected.val();
		var actividad=0;
		var formaID=4;
		var titulo="op";
		var usaeditor="";
		var estatusId="";
		if(confActividadId <1) return false;
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
			var parametros = "?formaId="+formaID+"&numeroUnicoExpediente="+cNumeroExpediente + "&actividadId=" + actividad + "&sentenciaId=" + sentenciaId+"&numeroExpedienteId="+ numeroExpedienteId;
			
			if(parametrosExtra != undefined && parametrosExtra != "undefined"){
			 	parametros += parametrosExtra;
			}
			if (actividad != '<%=Actividades.ELABORAR_ACUERDO_RECEPCION_SENTENCIA.getValorId()%>'
					&& actividad != '<%=Actividades.ELABORAR_ACUERDO_CANCELACION_INICIO_PROCEDIMIENTO_EJECUCION.getValorId()%>'){
				if (actividad == '<%=Actividades.SOLICITAR_CONSTANCIAS_CERERESO.getValorId()%>'){
					parametros += '&idTipoSolicitud='+<%=TiposSolicitudes.CONSTANCIAS_CERERESO.getValorId()%>;
				}else if (actividad == '<%=Actividades.SOLICITAR_ESTUDIOS_CTI.getValorId()%>'){
					parametros += '&idTipoSolicitud='+<%=TiposSolicitudes.ESTUDIOS_CTI.getValorId()%>;
				}else if (actividad == '<%=Actividades.ELABORAR_SOLICITUD_INFORMACION_DGPRS.getValorId()%>'){
					parametros += '&idTipoSolicitud='+<%=TiposSolicitudes.INFORMACION_DGPRS.getValorId()%>;
				}else if (actividad == '<%=Actividades.COMUNICAR_ACUERDO_RESOLUCION_DGPRS.getValorId()%>'){
					parametros += '&idTipoSolicitud='+<%=TiposSolicitudes.AVISO_DGPRS.getValorId()%>;
				}
				customVentana("iframewindowElaborarDocumentoRS", ""+titulo, "/elaborarDocumentosRS.do", parametros, function (){
					$('#cbxAccionesTab').empty();
					cargaActuaciones("");
					llenarGridDocumentos(listaDesplegada);
				});
			}else{
				parametros += "&esTransaccional=true&esconderArbol=true";
				parametros += "&idNumeroExpediente="+ numeroExpedienteId;
				customVentana("iframewindowElaborarDocumentoRS", ""+titulo, "/generarDocumentoSinCaso.do", parametros, function (){
					$('#cbxAccionesTab').empty();
					cargaActuaciones("");
					llenarGridDocumentos(listaDesplegada);	
				});
			}
		} 
	}	 
	 
//-->
</script>
		<fieldset>
			<table border="0">
				<tr>
					<td>
					Actuaciones: <html:select name="DatosGeneralesReinsercionForm" property="actuacionSeleccionada"
									styleId="cbxAccionesTab">
								 	<!--  option value="-1">--Seleccione una opci&oacute;n--</option -->
								 	<!-- html : optionsCollection name="DatosGeneralesReinsercionForm" property="actuaciones" label="nombre" value="programaId" /-->
								 </html:select>
					</td>
				</tr>
			</table>
		</fieldset>
		