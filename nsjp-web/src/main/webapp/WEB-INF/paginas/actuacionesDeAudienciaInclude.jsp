<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<script type="text/javascript">

var EXITO="exito";

$(document).ready(function() {

	/*
	*LLamdas para la ceja Adiministrar Actuaciones de Audiencia
	*/
	configurarPantallaActuacionesPorRol();
	$("#cbxActuacionesAudiencia").change(seleccionarActuacionAudiencia);
});//FIN ON READY

/*
 * Funciona para configurar los elementos de la pantalla
 * con base al rol del usuario firmado
 */
function configurarPantallaActuacionesPorRol(){

	if(rolId == <%=Roles.ENCARGADOSALA.getValorId()%>){
		//Mostrar el boton el boton de finalizar audiencia
		$("#btnFinalizarAudiencia").show();
	}
}

/*
*Funcion que realiza la carga del combo de Actuaciones
*/
function cargaActuacionesAudiencia() {

	$('#cbxActuacionesAudiencia').empty();
	$('#cbxActuacionesAudiencia').append('<option value="0">-Seleccione-</option>');

	var parametros = 'numeroExpedienteId=' + idNumeroExpediente;
	
	ejecutaAction("/cargarActuaciones", function(respuesta){
		$(respuesta).find('catActuaciones').each(function(){
			$('#cbxActuacionesAudiencia').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		});
	} , parametros);

}


/*
*Funcion que habilita o deshabilita los elementos de la pagina dependiento del tipo 
* de busqueda
*/
function seleccionarActuacionAudiencia(){

	var confActividadDocumentoId = $("#cbxActuacionesAudiencia option:selected").val();		

	if(parseInt(confActividadDocumentoId) != 0){
		ejecutarActuacionAudiencia(confActividadDocumentoId);			  			
	}
}


/**
 *Funcion para obtener los siguientes parametros de la tabla confActividadDocumento:
 *
 *actividadId
 *formaId
 *tipoDocumento
 *nombreDocumento
 *usaEditor
 *estatusId (No importa, ya que no cambiara el estatus del expediente)
 *nombreActividad
 *
 *Nota se requiere el parametro:numeroUnicoExpediente, que corresponde al numeroExpediente (String), como
 *obligatorio, ya que se estara generando una actividad
 */
function ejecutarActuacionAudiencia(confActividadDocumentoId){

	var actividad=0;
	var formaID=0;
	var titulo="";
	var usaeditor="";
	var estatusId="";
	var nombreActividad="";

	var parametrosDeConsulta = 'idConf=' + confActividadDocumentoId;

	
	ejecutaAction("/obtenerConfActividadDocumento", function(respuesta){
			actividad=$(respuesta).find('confActividadDocumentoDTO').find('tipoActividadId').text();
			formaID=$(respuesta).find('confActividadDocumentoDTO').find('formaId').text();
			titulo=$(respuesta).find('confActividadDocumentoDTO').find('nombreDocumento').text();
			usaeditor=$(respuesta).find('confActividadDocumentoDTO').find('usaEditor').text();
			estatusId=$(respuesta).find('confActividadDocumentoDTO').find('estadoCambioExpediente').find('idCampo').text();
			nombreActividad=$(respuesta).find('confActividadDocumentoDTO').find('nombreActividad').text();
	} , parametrosDeConsulta);

	if(actividad == <%=Actividades.GENERAR_ACTA_DE_AUDIENCIA.getValorId()%>){

		var parametros="";
		parametros += '&numeroUnicoExpediente='+numeroExpediente;
		parametros += '&formaId=' + formaID;
		parametros += '&titulo=' + titulo;
		parametros += '&nombreActividad=' + nombreActividad;
		parametros += '&idAudiencia='+idAudiencia;
		
		idWindowPantallaActuaciones++;
		$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+nombreActividad, type:"iframe", confirmarCierreVentana:false});
		$.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?parametros='+parametros+'&ocultarNumeroOficio=true&esconderArbol=1&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&actividadId='+actividad+'&esTransaccional='+true+'" width="1140" height="400" />');
		
	}else if(actividad == <%=Actividades.REGISTRAR_AMPARO.getValorId()%>){
		 $.newWindow({id:"iframewindowRegistrarAmparo", statusBar: true, posx:20,posy:20,width:550,height:500,title:"Registrar Amparo", type:"iframe", confirmarCierreVentana:true});
         $.updateWindowContent("iframewindowRegistrarAmparo",
         	'<iframe src="<%= request.getContextPath() %>/registrarAmparo.jsp?idNumeroExpediente=' + idNumeroExpediente + '&idExpedienteSoli='+ idExpediente + '&numeroGeneralCaso=' +numeroGeneralCaso +'"    width="550" height="500" />');
	}
}


/*
*Funcion para registrar una solicitud de AV/ o transcripcion de audiencia
*/
function registraSolicitud(tipo){

	var params = 'tipoSolicitud=' + tipo+'idEvento='+ idAudiencia;

	$.ajax({
		type: 'POST',
		url: '<%= request.getContextPath()%>/registrarSolicitudPJENS.do',
		data: params,
		dataType: 'xml',
		async: false,
		success: function(xml){
				numeroDeSalas = $(xml).find('valorDTO').find('valor').text();

		}
	});
}


/**
*Funcion para guardar la programacion de la audiencia y finalizar la audiencia
*/
function finalizarAudiencia(){
	
	var parametros = 'idAudiencia='+idAudiencia;
	var params = '';
	
    $.ajax({
		type: 'POST',
		url: '<%= request.getContextPath()%>/finalizarAudienciaPJENS.do',
		data: parametros,
		dataType: 'xml',
		async: false,
		success: function(xml){

			var respuesta = $(xml).find('mensaje').text();
			
			if(respuesta == EXITO){
				customAlert('<bean:message key="audiencia.finalizar.exito"/>','<bean:message key="aviso"/>',function(){
					if (typeof window.parent.recargaGridAudienciasDelDia == 'function' ){
						window.parent.recargaGridAudienciasDelDia();
					}
				});
				
				// Se envia correo de aviso, conclusion resolucion audiencia
				enviarCorreoAvisoAudienciaConclusion(idAudiencia);
				
				//Se pone la bandera en true para deshabilitar los componentes despues de que se finalizo la audiencia
				audienciaFinalizadadeshabilitar=true;
				deshabilitarDespuesFinalizadaAudiencia();//Funcion para deshabilitar componentes despues de finalizada la audiencia
								 
			}else{

				var msjDeError = '<bean:message key="audiencia.finalizar.error"/>';
				
				if(respuesta == '<%=CodigoError.ACTA_DE_AUDIENCIA_CON_GUARDADO_PARCIAL.toString()%>'){
					msjDeError += '<br/>*' + '<bean:message key="audiencia.finalizar.error.actaParcial"/>';
				}else if(respuesta == '<%=CodigoError.AUDIENCIA_CANCELADA.toString()%>'){
					msjDeError += '<br/>*' + '<bean:message key="audiencia.finalizar.error.cancelada"/>';
				}else if(respuesta == '<%=CodigoError.SIN_ACTA_DE_AUDIENCIA.toString()%>'){
					msjDeError += '<br/>*' + '<bean:message key="audiencia.finalizar.error.sinActa"/>';
				}else if(respuesta == '<%=CodigoError.AUDIENCIA_FINALIZADA.toString()%>'){
					msjDeError = '<bean:message key="audiencia.finalizar.error.audienciaFinalizada"/>';
				}

				customAlert(msjDeError,'<bean:message key="aviso"/>');
			}
		}
	});
}

function enviarCorreoAvisoAudienciaConclusion( idAudiencia ){
	
	$.ajax({
		type: 'POST',
		url: '<%= request.getContextPath()%>/enviarCorreoAudienciaConclusion.do?idAudiencia='+idAudiencia+'',
		data: '', 
		async: false,
		dataType: 'xml',
		success: function(xml){
			
		}
	});

}

</script>


<table width="720" cellspacing="0" cellpadding="0">
	<tr>
		<td width="31">&nbsp;</td>
		<td width="314">&nbsp;</td>
		<td>&nbsp;</td>
		<td width="32">&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td align="right">
			<strong>Actuaciones:</strong>
		</td>
		<td>
			<select id="cbxActuacionesAudiencia" style="width:220px">
			</select>
		</td>
		<td>
			<input id="btnFinalizarAudiencia" type="button" class="btn_Generico" value="Finalizar Audiencia" onclick="finalizarAudiencia();" style="display:none">
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>
