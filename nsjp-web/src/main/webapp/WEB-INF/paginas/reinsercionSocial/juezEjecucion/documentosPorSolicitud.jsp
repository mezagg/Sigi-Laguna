<%@page import="mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.documento.DocumentoDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<script type="text/javascript">
		var cNumeroExpediente;
		var solicitudId = null;
		var numeroExpedienteId = "";
		var expedienteId = "";
		var sentenciaId = <%=request.getParameter("sentenciaId")!=null?request.getParameter("sentenciaId"):"-1"%>;
	$(document).ready(function() {
		
		jQuery( "#btnResponder" ).button().click(
				function() {
					responderDocumento();
				}
		);
		
		jQuery( "#btnCerrar" ).button().click(
				function() {
					cerrarCustomVentana();
				}
		);
		 
		cNumeroExpediente = "<%=request.getParameter("cNumeroExpediente")%>";
		solicitudId = "<%=request.getParameter("solicitudId")%>"; 
		
		jQuery("#gridDocumentosPorSolicitud").jqGrid({
			url  : '<%= request.getContextPath()%>/consultarDocumentosConCriterios.do',
			postData : {
				tipoSoliciutd:"",
				estatus:"",
				numeroExpedienteId:"", 
				cNumeroExpediente:cNumeroExpediente,
				solicitudId 	: solicitudId,
				tipoConsulta	: "<%=SolicitudDTO.ADJUNTOS%>"
			},
			datatype: "xml", 
			colNames:['No. Caso','No. Expediente', 'Folio','Estatus','Fecha Creaci&oacute;n','Fecha Limite','Instituci&oacute;n','Remitente', 'TipoActividad', 'Nombre Del Documento','confActividadDocumentoId','confActividadNombre'], 
			colModel:[ 	{name:'caso',index:'caso', width:150,hidden:true},
			           	{name:'expediente',index:'expediente', width:180,align:'center'}, 
						{name:'folio',index:'folio', width:100,align:'center'}, 
						{name:'estatus',index:'estatus', width:100,align:'center', hidden:true}, 
						{name:'fechaCreacion',index:'fechaCreacion', width:100,align:'center'},
						{name:'fechaLimite',index:'fechaLimite', width:100,align:'center',hidden:true},
						{name:'institucion',index:'institucion', width:100,align:'center'},
						{name:'remitente',index:'remitente', width:200,align:'center'},
						{name:'tipoActividadId',index:'tipoActividadId', width:0,align:'center', hidden:true},
						{name:'nombreDoc',index:'nombreDoc', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }},
						{name:'confActividadDocumentoId',index:'confActividadDocumentoId', width:0,align:'center', hidden:true},
						{name:'confActividadNombre',index:'confActividadNombre', width:0,align:'center', hidden:true},
					],
			caption: "Documentos Asociados",
			pager: jQuery("#pagerGridDocumentosPorSolicitud"),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autoheight: true,
			autowidth: true,
			//sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			altRows : true,
		    shrinkToFit:true,
		    onSelectRow: function(id){
		    	var ret = jQuery("#gridDocumentosPorSolicitud").jqGrid('getRowData',id);
		    	if (getValorDelGrid(ret.confActividadDocumentoId) != "0"){
		    		jQuery("#btnResponder").button( "option", "disabled", false );
		    	}else{
		    		jQuery("#btnResponder").button( "option", "disabled", true );
		    	}
			},
			ondblClickRow: function(rowid) {
				dblClickRowBandejaSolicitudes(rowid);
			}
			
		}).navGrid("#pagerGridDocumentosPorSolicitud",{edit:false,add:false,del:false});
		
		
		consultaSolicitud(solicitudId);
		
		jQuery('#dialog:ui-dialog').dialog('destroy');
		
		jQuery("#dialog-selecciona-actuacion").dialog({
			resizable: false,
			title: 'Seleccionar actuaci&oacute;n',
			height:'auto',
			width:'auto',
			modal: true,
			autoOpen: false,
			closeOnEscape: false,
			buttons: 
				[	
				 	{
				 		text:"Aceptar",
				 		id:"btn_dsaAceptar",
				 		click: function() {
				 			var actuacionSel = $("input[type='radio'][name='actSeleccionada']:checked").val();
				 			var params = "";
				 			if (sentenciaId > 0){
				 				params="&sentenciaId="+sentenciaId;
				 				params+="&solicitudOriginalId="+solicitudId;
				 			}
				 			if (actuacionSel != undefined 
				 					&& actuacionSel != "undefined"
				 					&& actuacionSel != ""){
				 				seleccionaActuacion(actuacionSel,params);
				 				$( this ).dialog("close");
				 			}else{
				 				customAlert('Por favor seleccione de la lista de actuaciones la actuaci&oacute;n que desea ejecutar.',
				 				'Error de validaci&oacute;n');
				 			}
						}
					},
					{
						text:"Cancelar",
						id:"btn_dsaCancelar",
						click: function(){
							jQuery("#dialog-selecciona-actuacion").empty();
							$( this ).dialog("close");
						}
					}
				],
			close: function() {
				jQuery('#dialog-selecciona-actuacion').empty();
				jQuery('#dialog:ui-dialog').dialog('destroy');
			}
		});
	});
	
	var idWindowDetalleSolicitud=1;
	/*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle 
	*/
	function dblClickRowBandejaSolicitudes(rowId){
		customVentana("visorDocsPropiosFrame"+idWindowDetalleSolicitud,"Documento Asociado","/consultarArchivoDigitalIframe.do","?documentoId="+rowId+"&inFrame=true");
	}
	
	function llenarGridDocumentos() {
			jQuery("#gridDocumentosPorSolicitud").trigger("reloadGrid");
	}

	function consultaSolicitud(idSolicitud) {
		//mandamos a consultar las solicitudes
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultaDetalleSolicitudXAtender.do',
			data: 'idSolicitud='+idSolicitud,
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('SolicitudDTO').each(function(){
					seteaDatossolicitud(xml);
				});
			}
		});
	}
		
		function seteaDatossolicitud(xml) {
		
			numeroExpedienteId = $(xml).find('SolicitudDTO').find('expedienteDTO').find('numeroExpedienteId').text();
			expedienteId = $(xml).find('SolicitudDTO').find('expedienteDTO').find('expedienteId').text();
		
		
			//setea los datos del xml en los campos de detalle
			$("#txtNoExpediente").val(cNumeroExpediente);
			$("#txtNoCaso").val($(xml).find('SolicitudDTO').find('casoDTO').find('numeroGeneralCaso').text());
			if($(xml).find('SolicitudDTO').find('casoDTO').find('numeroGeneralCaso').text()==''){
				$("#etiqueta_noCaso").hide();
				$("#texto_noCaso").hide();
			}
			$("#txtFolio").val($(xml).find('SolicitudDTO').find('folioSolicitud').text());
			$("#cbxEstatus").val($(xml).find('SolicitudDTO').contents('estatus').find('valor').text());
			$("#txtFechaSol").val($(xml).find('SolicitudDTO').find('strFechaCreacion').text());
			$("#txtFechaLim").val(); 
			$("#etiqueta_fechaLim").hide();
			$("#texto_fechaLim").hide(); 
			$("#cbxTipoSol").val($(xml).find('SolicitudDTO').find('tipoSolicitudDTO').find('valor').text());
			if($(xml).find('SolicitudDTO').find('esUrgente').text()=='false')
			{
				$("#rdbUrgenteNo").attr("checked","checked");
			}
			else
			{
				$("#rdbUrgenteSi").attr("checked","checked");
			}
			$(':radio[name=rdbUrgente]').attr('disabled','disabled');
			$("#texto_urgente").hide();
			$("#opcion_urgente").hide();
			
			//funcionario externo
			$("#txtNombreServidorPub").val(
				$(xml).find('SolicitudDTO').contents('solicitanteInstExterna').find('nombre').text() + " "  + 
				$(xml).find('SolicitudDTO').contents('solicitanteInstExterna').find('apellidoPaterno').text() + " " + 
				$(xml).find('SolicitudDTO').contents('solicitanteInstExterna').find('apellidoMaterno').text()
			);
			
			//$("#txtCargo").val($(xml).find('SolicitudDTO').contents('solicitanteInstExterna').find('puesto').text());   
			$("#txtInstitucion").val($(xml).find('SolicitudDTO').contents('solicitanteInstExterna').contents('confInstitucionDTO').find('nombreInst').text());  
			
			//$("#txtAreaAdmin").val($(xml).find('SolicitudDTO').contents('solicitanteInstExterna').find('area').text());
			
			if($(xml).find('SolicitudDTO').find('observaciones').text()!=''){
				$("#txtObservaciones").val($(xml).find('SolicitudDTO').find('observaciones').text());
			}else{
				$("#etiqueta_obs").hide();
				$("#texto_obs").hide();
			}			
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
		
		function responderDocumento(){
		
			var id = $("#gridDocumentosPorSolicitud").jqGrid('getGridParam','selrow');
			if(id) {
				var ret = jQuery("#gridDocumentosPorSolicitud").jqGrid('getRowData',id);
				var idsActuaciones = getValorDelGrid(ret.confActividadDocumentoId).split(",");
				var params = "";
	 			if (sentenciaId > 0){
	 				params="&sentenciaId="+sentenciaId;
	 				params+="&solicitudOriginalId="+solicitudId;
	 			}
				if (idsActuaciones.length > 1){
					obtenActividadPorEjecutar(idsActuaciones, getValorDelGrid(ret.confActividadNombre).split(","))
				}else{
					seleccionaActuacion(idsActuaciones[0], params);					
				}
			} else {
				customAlert("Para realizar la operaci&oacute;n de respuesta se debe seleccionar un documento.",
				"Error de validaci&oacute;n");
			}		
		}
		
		function obtenActividadPorEjecutar(idsActuaciones,nombresActuaciones){
			jQuery("#dialog-selecciona-actuacion").append('<span id="confirmText">El documento seleccionado presenta varias opciones de respuesta.<br>'
					+'Por favor seleccione la actividad que desea generar.<br></span>');
			jQuery("#dialog-selecciona-actuacion").append(crearRadioActuaciones(idsActuaciones,nombresActuaciones));
			jQuery("#dialog-selecciona-actuacion").dialog("open");
		}
		
		function crearRadioActuaciones(idsActuaciones,nombresActuaciones){
			var radio = "";
			for (var i=0;i<idsActuaciones.length;i++){
				radio = radio.concat('<input type="radio" name="actSeleccionada" id="actSeleccionada" value="'+idsActuaciones[i]+'">'+nombresActuaciones[i]);
				if (i < (idsActuaciones.length-1)){
					radio = radio.concat('<br>');
				}
			}
			return radio;
		}
		
	function registrarActividadExpediente(actuacionID,estatusId,banderaMensaje){
		//Cambia la actividad del expediente
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/registraActividadExpediente.do?idExpediente='+expedienteId+'&idNumeroExpediente='+numeroExpedienteId+'&actuacion='+actuacionID+'&estatus='+estatusId+'&numExpe='+cNumeroExpediente,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				if(parseInt(banderaMensaje)==1)
				{
					customAlert("Actividad nueva registrada");
				}
			}
		});
	}	 
	 
	 
	function seleccionaActuacion(confActividadId, parametrosExtra){
		
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
			var parametros = "?formaId="+formaID+"&numeroUnicoExpediente="+cNumeroExpediente + "&actividadId=" + actividad + "&numeroExpedienteId="+ numeroExpedienteId;
			
			if(parametrosExtra != undefined && parametrosExtra != "undefined"){
			 	parametros += parametrosExtra;
			}
			 
			customVentana("iframewindowElaborarDocumentoRS", ""+titulo, "/elaborarDocumentosRS.do", parametros, function (){
				llenarGridDocumentos();	
			});
    			
		}

	}

</script>

<table width="100%">
	<tr>
		<td width="50%">
			<table width="100%">
				<tr>
					<td>
						No. Carpeta de Ejecuci&oacute;n : 
					</td>
					<td>
						<input type="text" id="txtNoExpediente" maxlength="15" disabled="disabled" size="30"/> 
					</td>
				</tr>
				<tr>
					<td>
						<span id="etiqueta_noCaso">No. Caso :</span> 
					</td>
					<td>
						<span id="texto_noCaso"><input type="text" id="txtNoCaso" maxlength="15" disabled="disabled" size="30"/></span> 
					</td>
				</tr>
				<tr>
					<td>
						Folio de la Solicitud : 
					</td>
					<td>
						<input type="text" id="txtFolio" maxlength="15" disabled="disabled" size="30"/> 
					</td>
				</tr>
				<tr>
					<td>
						Estatus : 
					</td>
					<td>
						<input type="text" id="cbxEstatus" maxlength="30" disabled="disabled" size="30"/> 
					</td>
				</tr>
				<tr>
					<td>
						Fecha de Solicitud : 
					</td>
					<td>
						<input type="text" id="txtFechaSol" maxlength="10" disabled="disabled" size="30"/> 
					</td>
				</tr>
				<tr>
					<td>
						<span id="etiqueta_fechaLim">Fecha L&iacute;mite :</span> 
					</td>
					<td>
						<span id="texto_fechaLim"><input type="text" id="txtFechaLim" maxlength="10" disabled="disabled" size="30"/></span> 
					</td>
				</tr>
				<tr>
					<td>
						Tipo de Solicitud : 
					</td>
					<td>
						<input type="text" id="cbxTipoSol" maxlength="30" disabled="disabled" size="30"/> 
					</td>
				</tr>
				<tr>
					<td>
						<span id="texto_urgente">Urgente :</span> 
					</td>
					<td>
						<span id="opcion_urgente">
							<input type="radio" id="rdbUrgenteSi" name="rdbUrgente" value="1"  > S&iacute; &nbsp;&nbsp;&nbsp;
							<input type="radio" id="rdbUrgenteNo" name="rdbUrgente" value="0" > No
						</span>
					</td>
				</tr>
			</table>
		</td>
		<td width="50%">
			<table width="100%">
				<tr>
					<td>
						&nbsp; 
					</td>
					<td align="left">
						<b>Informaci&oacute;n Remitente : </b> 
					</td>
				</tr>
				<tr>
					<td>
						Nombre del Servidor <br/>P&uacute;blico : 
					</td>
					<td>
						<input type="text" id="txtNombreServidorPub" maxlength="30" disabled="disabled" size="40"/> 
					</td>
				</tr>
				<tr>
					<td>
						Instituci&oacute;n : 
						
					</td>
					<td>
						<input type="text" id="txtInstitucion" maxlength="30" disabled="disabled" size="40"/> 
						
					</td>
				</tr>
				<tr style="display: none">
					<td>
						Cargo : 
					</td>
					<td>
						<input type="text" id="txtCargo" maxlength="30" disabled="disabled" size="40"/> 
					</td>
				</tr>
				<tr  style="display: none">
					<td>
						&Aacute;rea Administrativa : 
					</td>
					<td>
						<input type="text" id="txtAreaAdmin" maxlength="30" disabled="disabled" size="40"/> 
					</td>
				</tr>
				<tr>
					<td>
						<span id="etiqueta_obs"><b>Observaciones : </b></span> 
					</td>
					<td>
						&nbsp; 
					</td>
				</tr>
				<tr>
					<td colspan="2">
						 <span id="texto_obs"><textarea id="txtObservaciones" rows="5" cols="40" readonly="readonly"></textarea></span>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<table id="gridDocumentosPorSolicitud"></table>
			<div id="pagerGridDocumentosPorSolicitud"></div>
		</td>
	</tr>
	<tr>
		<td align="right" >
			<input 	type="button" 
					name="btnResponder" 
					id="btnResponder" 
					value="Responder Documento"  
					class="ui-button ui-corner-all ui-widget"/>
		</td>
		<td align="left" >
			<input 	type="button" 
					name="btnCerrar" 
					id="btnCerrar" 
					value="Cerrar" 
					class="ui-button ui-corner-all ui-widget"/>
		</td>		
	</tr>
</table>
<div id="dialog-selecciona-actuacion">
</div>