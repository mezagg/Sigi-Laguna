<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.calidad.Calidades"%>
<%@page import="mx.gob.segob.nsjp.comun.mandamiento.EstatusMandamientoPersona"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<script type="text/javascript">

	var SELECCIONE = 0;
	var NUM_COL_DOC = 4;
	var NUM_COL_REDUCIDO = 2;
	
	jQuery().ready(function () {
		llenarCbxTiposDocumentos();
		consultarCbxProbablesResponsables();
	});

/******************************************************* FUNCIONES CEJA DOCUMENTOS  ***********************************************************************/
	
	//Limpia la pantalla de documentos
	function inicializaTabDocumentos(){
		//Limpia el grid de documentos de mandamientos persona
		limpiarGridDocumentos(NUM_COL_REDUCIDO);
		//Setea el valor del combo al default
		$("#tiposDocsGenManJudCbx").val(SELECCIONE);
		//oculta el combo de estatus
		ocultaMuestraColumnasGrid(true);
		ocultarMostrarCbxEstatusDoc(true);
		
	}

	function consultarCbxProbablesResponsables(){

		var calidadId="<%=Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId()%>";

		$.ajax({
			type: 'POST',
			url:'<%=request.getContextPath()%>/consultarInvolucradoPorCalidadParaCombo.do?expedienteId='+idExpediente+'&calidadId='+calidadId+'',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('involucrado').each(function(){
					$('#probableRespDocCbx').append('<option value="' + $(this).find('involucradoId').text() + '">' + $(this).find('nombreCompleto').text() + '</option>');
				});
			}
		});
	}

	//LLena el combo de tipos de documentos dependiendo del usuario logeado
	function llenarCbxTiposDocumentos(){

		$('#tiposDocsGenManJudCbx').change(controladorGrids);
		$('#estatusDocGenManJudCbx').change(controladorCbxCambioEstatus);
		$('#probableRespDocCbx').change(cotroladorCbxProbableResponsable);
		
		if(rolActivo == '<%=Roles.ENCARGADOCAUSA.getValorId()%>'){
			if(esConsulta == true){
				$("#tiposDocsGenManJudCbx").append("<option value='"+<%=TipoDocumento.MANDAMIENTO_JUDICIAL.getValorId()%>+"'>Documento de creaci&oacute;n</option>");
				$("#tiposDocsGenManJudCbx").append("<option value='"+<%=TipoDocumento.CAMBIO_DE_ESTADO_DE_MANDAMIENTO_JUDICIAL.getValorId()%>+"'>Documentos por cambio de estatus</option>");
			}
			else{
				$("#tiposDocsGenManJudCbx").append("<option value='"+<%=TipoDocumento.MANDAMIENTO_JUDICIAL.getValorId()%>+"'>Documentos de creaci&oacute;n</option>");
			}
		}
		else if(rolActivo == '<%=Roles.AGENTEMP.getValorId()%>'){
			
			$("#tiposDocsGenManJudCbx").append("<option value='"+<%=TipoDocumento.MANDAMIENTO_JUDICIAL.getValorId()%>+"'>Documentos de creacion</option>");
			$("#tiposDocsGenManJudCbx").append("<option value='"+<%=TipoDocumento.CAMBIO_DE_ESTADO_DE_MANDAMIENTO_JUDICIAL.getValorId()%>+"'>Documentos por cambio de estatus</option>");
			
		}else if(rolActivo == '<%=Roles.DIRECTOR_APREHENSIONES.getValorId()%>'){
			
			$("#tiposDocsGenManJudCbx").append("<option value='"+<%=TipoDocumento.MANDAMIENTO_JUDICIAL.getValorId()%>+"'>Documentos de creaci&oacute;n</option>");
			$("#tiposDocsGenManJudCbx").append("<option value='"+<%=TipoDocumento.CAMBIO_DE_ESTADO_DE_MANDAMIENTO_JUDICIAL.getValorId()%>+"'>Documentos por cambio de estatus</option>");
			$("#tiposDocsGenManJudCbx").append("<option value='"+<%=TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()%>+"'>Documentos Adjuntos</option>");
			$("#tiposDocsGenManJudCbx").append("<option value='"+<%=TipoDocumento.REPORTE_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId()%>+"'>Documentos de respuesta</option>");
			
		}else if(rolActivo == '<%=Roles.POLICIAMINISTER.getValorId()%>'){
			$("#tiposDocsGenManJudCbx").append("<option value='"+<%=TipoDocumento.REPORTE_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId()%>+"'>Documentos de respuesta</option>");
		}
	}



	function controladorGrids(){

		var seleccion = $("#tiposDocsGenManJudCbx").val();

		if(seleccion == <%=TipoDocumento.MANDAMIENTO_JUDICIAL.getValorId()%>){
			ocultarMostrarCbxEstatusDoc(true);
			ocultaMuestraColumnasGrid(true);
			jQuery("#gridTiposDocsGenManJud").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDocumentosDelMandamiento.do?mandamientoJudicialId='+mandamientoJudicialId+'&tipoDocumento='+seleccion+'',datatype: "xml"});
			$("#gridTiposDocsGenManJud").trigger("reloadGrid");
		}else if(seleccion == <%=TipoDocumento.CAMBIO_DE_ESTADO_DE_MANDAMIENTO_JUDICIAL.getValorId()%>){
			ocultarMostrarCbxEstatusDoc(false);
			ocultaMuestraColumnasGrid(false);
			limpiarGridDocumentos(NUM_COL_REDUCIDO);
		}else if(seleccion == <%=TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()%>){
			ocultarMostrarCbxEstatusDoc(true);
			ocultaMuestraColumnasGrid(true);
			jQuery("#gridTiposDocsGenManJud").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDocumentosDelMandamiento.do?mandamientoJudicialId='+mandamientoJudicialId+'&tipoDocumento='+seleccion+'',datatype: "xml"});
			$("#gridTiposDocsGenManJud").trigger("reloadGrid");
		}else if(seleccion == <%=TipoDocumento.REPORTE_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId()%>){
			ocultarMostrarCbxEstatusDoc(true);
			ocultaMuestraColumnasGrid(true);
			jQuery("#gridTiposDocsGenManJud").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDocumentosDelMandamiento.do?mandamientoJudicialId='+mandamientoJudicialId+'&tipoDocumento='+seleccion+'',datatype: "xml"});
			$("#gridTiposDocsGenManJud").trigger("reloadGrid");
		}else{
			//Aqui limpia el grid de documentos y oculta los combo box de estatus y probable responsable
			ocultarMostrarCbxEstatusDoc(true);
			ocultaMuestraColumnasGrid(true);
			limpiarGridDocumentos(NUM_COL_REDUCIDO);
		}
	}

	function ocultaMuestraColumnasGrid(esOculta){

		if(esOculta == false){
			jQuery("#gridTiposDocsGenManJud").jqGrid('showCol',["mandamiento"]);
			jQuery("#gridTiposDocsGenManJud").jqGrid('showCol',["probResponsable"]);
		}else{
			jQuery("#gridTiposDocsGenManJud").jqGrid('hideCol',["mandamiento"]);
			jQuery("#gridTiposDocsGenManJud").jqGrid('hideCol',["probResponsable"]);
		}
	}


	//Funcion que oculta y muestra el cbx de tipo de
	//estus, ademas de limpiar la seleccion
	function ocultarMostrarCbxEstatusDoc(esOculta){

		$('#estatusDocGenManJudCbx').val(SELECCIONE);
		$('#probableRespDocCbx').val(SELECCIONE);
		
		if(esOculta == false){
			$("#divLabelEstatusDoc").show();
			$("#divCbxEstatusDoc").show();

			$("#divLabelProbableRespDoc").show();
			$("#divCbxProbableRespDoc").show();
		}else{
			$("#divLabelEstatusDoc").hide();
			$("#divCbxEstatusDoc").hide();

			$("#divLabelProbableRespDoc").hide();
			$("#divCbxProbableRespDoc").hide();
		}
	}

	

	function controladorCbxCambioEstatus(){

		var estatusSeleccionado = $('#estatusDocGenManJudCbx').val();

		if(parseInt(estatusSeleccionado) != parseInt(SELECCIONE)){

			var probRespSeleccionado = $('#probableRespDocCbx').val();

			if(parseInt(probRespSeleccionado) != parseInt(SELECCIONE)){
				var tipoDocumento = <%=TipoDocumento.CAMBIO_DE_ESTADO_DE_MANDAMIENTO_JUDICIAL.getValorId()%>;
				jQuery("#gridTiposDocsGenManJud").jqGrid('setGridParam',
						{url:'<%=request.getContextPath()%>/consultarDocumentosDelMandamiento.do?mandamientoJudicialId='+mandamientoJudicialId+'&tipoDocumento='+tipoDocumento+'&estatusDocumentoDeCambio='+estatusSeleccionado+'&probableResponsableId='+probRespSeleccionado+'',datatype: "xml"});
				$("#gridTiposDocsGenManJud").trigger("reloadGrid");
			}else{
				customAlert("Seleccione un "+'<bean:message key="probableResponsable"/>');
			}
		}else{
			customAlert("Seleccione un estatus");
			limpiarGridDocumentos(NUM_COL_DOC);
		}
	}


	function cotroladorCbxProbableResponsable(){

		var probRespSeleccionado = $('#probableRespDocCbx').val();

		if(parseInt(probRespSeleccionado) != parseInt(SELECCIONE)){

			var estatusSeleccionado = $('#estatusDocGenManJudCbx').val();

			if(parseInt(estatusSeleccionado) != parseInt(SELECCIONE)){
				var tipoDocumento = <%=TipoDocumento.CAMBIO_DE_ESTADO_DE_MANDAMIENTO_JUDICIAL.getValorId()%>;
				jQuery("#gridTiposDocsGenManJud").jqGrid('setGridParam',
						{url:'<%=request.getContextPath()%>/consultarDocumentosDelMandamiento.do?mandamientoJudicialId='+mandamientoJudicialId+'&tipoDocumento='+tipoDocumento+'&estatusDocumentoDeCambio='+estatusSeleccionado+'&probableResponsableId='+probRespSeleccionado+'',datatype: "xml"});
				$("#gridTiposDocsGenManJud").trigger("reloadGrid");
			}else{
				customAlert("Seleccione un estatus");
			}	 	
		}else{
			customAlert("Seleccione un "+'<bean:message key="probableResponsable"/>');
			limpiarGridDocumentos(NUM_COL_DOC);
		}
	}


	function limpiarGridDocumentos(numCol){
		jQuery("#gridTiposDocsGenManJud").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas='+numCol+'',datatype: "xml"});
		$("#gridTiposDocsGenManJud").trigger("reloadGrid");
	}
/******************************************************* TERMINAN FUNCIONES CEJA DOCUMENTOS  ****************************************************************/	

</script>

<!-- Comienza cuerpo Documentos mandamiento judicial -->
		
		<div>
			<table width="100%" border="0">
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td width="150" align="right">
						<strong>Tipos de documentos: </strong>
						
					</td>
					<td width="200">
						<select id="tiposDocsGenManJudCbx" tabindex="4" style="width: 255px">
							<option value="0"> -Seleccione- </option>
						</select>
					</td>
					<td width="100" align="right">
						<div id="divLabelEstatusDoc" style="display: none;">
							<strong>Estatus:</strong>
						</div>
					</td>
					<td width="200">
						<div id="divCbxEstatusDoc" style="display: none;">
							<select id="estatusDocGenManJudCbx" tabindex="5" style="width: 255px">
								<option value="0"> -Seleccione- </option>
								<option value="<%=EstatusMandamientoPersona.EN_PROCESO.getValorId()%>">En proceso</option>
								<option value="<%=EstatusMandamientoPersona.ATENDIDO.getValorId()%>">Atendido</option>
								<option value="<%=EstatusMandamientoPersona.CANCELADO.getValorId()%>">Cancelado</option>
								<option value="<%=EstatusMandamientoPersona.CONCLUIDO.getValorId()%>">Concluido</option>
							</select>
						</div>
					</td>
					<td>	
						<div id="divLabelProbableRespDoc" style="display: none;">
							<strong>
								<bean:message key="probableResponsable"/>
							</strong>
						</div>
					</td>
					<td>
						<div id="divCbxProbableRespDoc" style="display: none;">
							<select id="probableRespDocCbx" tabindex="6" style="width: 255px">
								<option value="0"> -Seleccione- </option>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="6" align="center">
						<table id="gridTiposDocsGenManJud" border="0">
						</table>
						<div id="pagerGridTiposDocsGenManJud"></div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>

		</div>
		<!-- Termina cuerpo Documentos mandamiento judicial -->
		
	<script type="text/javascript">
	
		//Construye el grid de documentos
		jQuery("#gridTiposDocsGenManJud").jqGrid({
			datatype: "xml", 
			colNames:['Tipo de Mandamiento','<bean:message key="probableResponsable"/>','Fecha de Elaboraci&oacute;n','Nombre'], 
			colModel:[  {name:'mandamiento',index:'mandamiento', width:250, align:'center', sortable:false},
			            {name:'probResponsable',index:'probResponsable', width:250, align:'center', sortable:false},
			            {name:'fechaElaboracion',index:'fechaElaboracion', width:250, align:'center'},						
			           	{name:'nombre',index:'nombre', width:250, align:'center', sortable:false}, 
			        ],
			pager: jQuery('#pagerGridTiposDocsGenManJud'),
			rowNum:10, 
			rowList:[10,20,30],
			width:1000,
			sortname:'fechaElaboracion',
			viewrecords:true,
			sortorder:"desc",
			ondblClickRow: function(rowid) { 
				abrirEditorDocumentosMandamientos(rowid,null,false, false);
			}
		});
		
	</script>