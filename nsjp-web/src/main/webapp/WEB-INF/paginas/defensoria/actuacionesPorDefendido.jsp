<html>
<head>
	<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
	<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
 <script>

 	$(document).ready(function() {
	 	$("#cbxActuacionesPorDefendido").change(ejecutaActuacionPorDefensor);
 	});

 	
 	
 	var confirmarCierreVentana ='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getMostrarMensajeConfirmacionEnDocumento() %>';
 	var involcradoId = 0;
 	
 	
 	//Bandera para recargar el grid de defendidos
 	var recargaGridActuacionesPorDefendido = true;

 	/**
 	*Funcion que carga los defendidos del expediente y la etapa actual en la que se encuentran
 	*/
	function cagarGridActuacionesPorDefendido(){

		if(recargaGridActuacionesPorDefendido){
			
			jQuery("#gridActuacionesPorDefendido").jqGrid({
				url:'<%= request.getContextPath()%>/consultarDefendidosConEtapaPorExpediente.do?expedienteId='+idExpedienteop+'',
				datatype: "xml", 
				colNames:['Nombre','Etapa del Defendido', 'EtapaValorId'], 
				colModel:[ 	{name:'nombreDefendido',index:'nombreDefendido', width:250}, 
							{name:'etapaDefendido',index:'etapaDefendido', width:200},
							{name:'etapaValorId',index:'etapaValorId', width:200,hidden:true}
						],
				pager: jQuery('#pagerGridActuacionesPorDefendido'),
				rowNum:10,
				rowList:[10,20,30,40,50,60],
				width:600,
				caption:"DEFENDIDOS",
				sortname: 'Clave',
				viewrecords: true,
				id: 'gridActuacionesPorDefendido',
				ondblClickRow: function(id){
					var ret = jQuery("#gridActuacionesPorDefendido").jqGrid('getRowData',id);
					var etapaValorId = ret.etapaValorId;
					involcradoId = id;
					cargaActuacionesPorEtapa(etapaValorId);
				},
				sortorder: "desc"
			});
				
			recargaGridActuacionesPorDefendido = false;
		}else{
			jQuery("#gridActuacionesPorDefendido").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarDefendidosConEtapaPorExpediente.do?expedienteId='+idExpedienteop+'',datatype: "xml" });
			$("#gridActuacionesPorDefendido").trigger("reloadGrid");
		}
	}
	
	/**
	* Funcion utilizada para recargar el grid de actuaciones por defendido.
	* Se manda a invocar en el GenerarDocumentoSinCaso.jsp, despues de 
	* efectuar el guardado definitivo.
	*/
 	function recargarActuaciones(){
 		$('#cbxActuacionesPorDefendido').empty();
		cagarGridActuacionesPorDefendido();
	}
 	
	function cargaActuacionesPorEtapa(etapaValorId) {
		$('#cbxActuacionesPorDefendido').empty();
		var muestraCombo = true;
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarActuacionesPorFiltro.do?grupoActividad='+etapaValorId+'&muestraCombo=true',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catActuaciones').each(function(){
					$('#cbxActuacionesPorDefendido').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});         
			}
		});
	}
	
 	/**
 	*Funcion que ejecuta la actuacion seleccionada por el usuario
 	*/
	function ejecutaActuacionPorDefensor(){
		var selected = $("#cbxActuacionesPorDefendido option:selected");
		var confActividadId = selected.val();
		var actividad=0;
		var formaID=0;
		var titulo="";
		var usaeditor="";
		var estatusId="";
		var nombreActividad="";

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
				nombreActividad=$(xml).find('confActividadDocumentoDTO').find('nombreActividad').text();
				
				ejecutaActuacion(selected, confActividadId, actividad, formaID, titulo, usaeditor, estatusId, null, null);
			}
		});

	}
 	
	function ejecutaActuacion(selected, confActividadId, actividad, formaID, titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave){
		//codigo para cambiar el estatus del expediente
		idWindowPantallaActuaciones++;
		console.info("ID Tipo Actividad:" + actividad);
		$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
	    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&actividadId='+actividad+'&esTransaccional='+true+'&involcradoId='+involcradoId+'&confActividadId='+confActividadId+'&ocultarGuardadoParcial='+ocultarGuardadoParcial+'" width="1140" height="400" />');
	    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
	}
	
 </script>
</head>

<center>
	<table width="100%" border="0">
	  <tr>
	    <td width="5%">&nbsp;</td>
	    <td width="40%">&nbsp;</td>
	    <td width="50%">&nbsp;</td>
	    <td width="5%">&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td rowspan="6"  align="center" valign="top">
			<table width="100%" border="0" id="gridActuacionesPorDefendido"></table>
			<div id="pagerGridActuacionesPorDefendido"></div>
		</td>
	    <td rowspan="6"  align="center" valign="top">
			<select name="acciones" id="cbxActuacionesPorDefendido" size="10" style="width:100%"></select>
		</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
	</table>
</center>
</html>