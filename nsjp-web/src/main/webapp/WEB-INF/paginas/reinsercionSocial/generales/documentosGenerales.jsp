<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.documento.DocumentoDTO"%>
<%@page import="mx.gob.segob.nsjp.web.reinsercionsocial.action.DocumentosReinsercionAction"%>
<script type="text/javascript">
		var cNumeroExpediente;
		var numeroExpedienteId ;
		var listaDesplegada = '<%=SolicitudDTO.PARCIALES%>';
		var sentenciaId = null;
		var expedienteId = null;
		var consultaRealizada = "";
		var documentosAsociados = true;
		var estatusNumExpId = null;
		var rolActivoId = null;
		var motivo = 'Ingreso';
		var arraigado = true;
		
	$(document).ready(function() {
		 sentenciaId = jQuery("#sentenciaId").val();
		 cNumeroExpediente = jQuery("#carpeta").val();
		 numeroExpedienteId = jQuery("#numeroExpedienteId").val();
		 expedienteId = jQuery("#expedienteId").val();
		 estatusNumExpId = jQuery("#estatusNumExpId").val();
		 rolActivoId = jQuery("#rolActivoId").val();
	
	
		$("#menuDocumentos").jstree({ 
	        	"themes" : {
		            "theme" : "classic",
		            "dots" : true,
		            "icons" : true
		        },
				"plugins" : [ "themes", "html_data" ]
			});
		
		jQuery('#dialog:ui-dialog').dialog('destroy');
		
		jQuery("#dialog-validaDocumentos").dialog({
			resizable: false,
			title: 'Control de Solicitudes para Integraci&oacute;n',
			height:'auto',
			width:'auto',
			modal: true,
			autoOpen: false,
			closeOnEscape: false,
			buttons: 
				[	
				 	{
				 		text:"Integrar Procedimiento",
				 		id:"btn_dsaIntegrar",
				 		click: function() {
				 			if (documentosAsociados == true){
				 				integrarProcedimientoEjecucion();
				 			}else{
				 				customAlert("No se puede llevar a cabo la integraci&oacute;n del procedimiento de ejecuci&oacute;n. <br>"
				 				+"Debido a que no se cuenta con todas las constancias y estudios obligatorios. <br>"
				 				+"Los documentos marcados con (*) son documentos obligatorios. <br>"
				 				+"Favor de verificar.","Error de validaci&oacute;n");
				 			}
				 			$( this ).dialog("close");
						}
					},
					{
						text:"Cancelar",
						id:"btn_dsaCancelar",
						click: function(){
							jQuery("#dialog-validaDocumentos").empty();
							$( this ).dialog("close");
						}
					}
				],
			close: function() {
				jQuery('#dialog-validaDocumentos').empty();
				jQuery('#dialog:ui-dialog').dialog('destroy');
			}
		});
		
		jQuery("#btnIntegrar").button().click(
			function() {
				validarDocumentosIntegracionExpediente(expedienteId);
				jQuery("#dialog-validaDocumentos").dialog("open");
			}
		);
		
		if (rolActivoId != "<%=Roles.DSE.getValorId()%>"
				|| estatusNumExpId != "<%=EstatusExpediente.EN_ESPERA_DE_SENTENCIADO.getValorId()%>"){
			jQuery("#btnIntegrar").hide();
		}

		jQuery("#gridDocumentos").jqGrid({ 
			datatype: "xml", 
			colNames:['No. Caso','No. Expediente', 'Folio','Estatus','Fecha Creaci&oacute;n','Fecha Limite','Instituci&oacute;n','Remitente', 'TipoActividad', 
			          'Nombre Del Documento','Instituci&oacute;n remitente', 'Remitente Externo', 'Documento Adjunto', 'Id Doc Adjunto'], 
			colModel:[ 	{name:'caso',index:'caso', width:150,hidden:true},
			           	{name:'expediente',index:'expediente', width:180,align:'center'}, 
						{name:'folio',index:'folio', width:100,align:'center'}, 
						{name:'estatus',index:'estatus', width:100,align:'center'}, 
						{name:'fechaCreacion',index:'fechaCreacion', width:100,align:'center'},
						{name:'fechaLimite',index:'fechaLimite', width:100,align:'center',hidden:true},
						{name:'institucion',index:'institucion', width:100,align:'center'},
						{name:'remitente',index:'remitente', width:200,align:'center'},
						{name:'tipoActividadId',index:'tipoActividadId', width:0,align:'center', hidden:true},
						{name:'nombreDoc',index:'nombreDoc', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; } },
						{name:'institucionRemitente',index:'institucionRemitente', width:100,align:'center', hidden:true},
						{name:'remitenteExterno',index:'remitenteExterno', width:200,align:'center', hidden:true},
						{name:'nombreDocAdjunto',index:'nombreDocAdjunto', width:0,align:'center', hidden:true},
						{name:'idDocAdjunto',index:'idDocAdjunto', width:0,align:'center', hidden:true}
					],
			caption: "Documentos",
			pager: jQuery("#pagerGridDocumentos"),
			rowNum:10,
			rowList:[10,20,30,40,50,60,70,80,90,100],
			autoheight: true,
			autowidth: true,
			//sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			altRows : true,
		    shrinkToFit:true,
			ondblClickRow: function(rowid) {}
			
		}).navGrid("#pagerGridDocumentos",{edit:false,add:false,del:false});
	});
	
	var idWindowDetalleSolicitud=1;
	/*Funcion que acarrea el id del expediente, para devolverlo
	*a la pantalla de detalle 
	*/
	function dblClickRowBandejaSolicitudes(rowId){
		var idDoc = rowId;
		if (consultaRealizada == ""
				|| (consultaRealizada != "<%=SolicitudDTO.ADJUNTOS_EXPEDIENTE%>" 
						&& consultaRealizada != "<%=SolicitudDTO.PARCIALES%>")){
			customVentana("iframewindowDetalleSolicitud"+idWindowDetalleSolicitud++, "Detalle Del Documento","/consultarDetalleSolicitudBandeja.do", "?idSolicitud=" +rowId + "&tipoUsuario=1" );
		}
		var ret = jQuery("#gridDocumentos").jqGrid('getRowData',rowId);
		var idDocAdjunto = getValorDelGrid(ret.idDocAdjunto);
		if (idDocAdjunto != '---'
				&& idDocAdjunto != undefined
				&& idDocAdjunto != 'undefined'
				&& idDocAdjunto != null){
			idDoc = idDocAdjunto;
		}
		customVentana("visorDocsPropiosFrame"+idWindowDetalleSolicitud,"Documento Asociado","/consultarArchivoDigitalIframe.do","?documentoId="+idDoc+"&inFrame=true");
	}

	function mostrarDocumento(rowId){
		var actividadId = "";
		var id = jQuery("#gridDocumentos").jqGrid('getGridParam','selrow'); 
		if (id) { 
			var ret = jQuery("#gridDocumentos").jqGrid('getRowData',id);
			actividadId = ret.tipoActividadId;
		}
	
		var titulo = "";
		var parametros = "?numeroUnicoExpediente=" + cNumeroExpediente + "&documentoId=" + rowId + "&actividadId=" + actividadId + "&sentenciaId=" + sentenciaId;
		var url = "/elaborarDocumentosRS.do";
		if (actividadId == <%=Actividades.ELABORAR_ACUERDO_RECEPCION_SENTENCIA.getValorId()%>){
			parametros += "&nuevaActividad="+actividadId+"&esTransaccional=true&esconderArbol=true";
			url = "/generarDocumentoSinCaso.do";
		}
		if (actividadId == '<%=Actividades.SOLICITAR_CONSTANCIAS_CERERESO.getValorId()%>'){
			parametros += '&idTipoSolicitud='+<%=TiposSolicitudes.CONSTANCIAS_CERERESO.getValorId()%>;
		}else if (actividadId == '<%=Actividades.SOLICITAR_ESTUDIOS_CTI.getValorId()%>'){
			parametros += '&idTipoSolicitud='+<%=TiposSolicitudes.ESTUDIOS_CTI.getValorId()%>;
		}
		customVentana("iframewindowElaborarDocumentoRS", ""+titulo, url, parametros, function(){
			$('#cbxAccionesTab').empty();
			cargaActuaciones("");
			llenarGridDocumentos(listaDesplegada);			
		});
	}
	
	function llenarGridDocumentos(tipoConsulta) {
		consultaRealizada = tipoConsulta;
		
		var url  = '<%= request.getContextPath()%>/consultarDocumentosConCriterios.do';
			url += '?tipoSoliciutd=<%=TiposSolicitudes.REINSERCION_SOCIAL.getValorId() %>'
					+',<%=TiposSolicitudes.CONSTANCIAS_CERERESO.getValorId()%>,<%=TiposSolicitudes.ESTUDIOS_CTI.getValorId()%>'
					+',<%=TiposSolicitudes.INFORMACION_DGPRS.getValorId()%>,<%=TiposSolicitudes.AVISO_DGPRS.getValorId()%>';
			//url += '&estatus=<%=EstatusSolicitud.ABIERTA.getValorId()%>';
			url += '&numeroExpedienteId=' + numeroExpedienteId; 
			url += '&cNumeroExpediente=' + cNumeroExpediente;
			url += '&tipoConsulta=' + tipoConsulta;
	
		jQuery("#gridDocumentos").jqGrid("clearGridData", true);
		
		if (tipoConsulta != '<%=SolicitudDTO.PARCIALES%>'
				&& tipoConsulta != '<%=SolicitudDTO.ADJUNTOS_EXPEDIENTE%>') {
			jQuery("#gridDocumentos").showCol("remitente");
			if(tipoConsulta == '<%=SolicitudDTO.GENERADAS%>'){
				jQuery("#gridDocumentos").jqGrid("setLabel", "remitente", "Destinatario");
				jQuery("#gridDocumentos").setCaption("Documentos Enviados");
			} else if(tipoConsulta == '<%=SolicitudDTO.POR_ATENDER%>') {
				jQuery("#gridDocumentos").jqGrid("setLabel", "remitente", "Remitente");
				jQuery("#gridDocumentos").setCaption("Documentos Recibidos");
			}
			
			jQuery("#gridDocumentos").jqGrid(
				"setGridParam", {
					url: url,
					ondblClickRow: function(rowid) {
						dblClickRowBandejaSolicitudes(rowid);
					}
				}
			);
			
		}else if (tipoConsulta == '<%= SolicitudDTO.ADJUNTOS_EXPEDIENTE%>'){
			url = '<%= request.getContextPath()%>/consultarDocumentosExpedienteXTipos.do';
			url += '?idTipoDocto='+<%=TipoDocumento.AUTO_INICIO_PROCEDIMIENTO_EJECUCION.getValorId()%>;
			url += ','+<%=TipoDocumento.SENTENCIA_EJECUTORIADA.getValorId()%>;
			url += '&tipoConsulta='+tipoConsulta;
			url += '&idExpediente='+expedienteId;
			
			jQuery("#gridDocumentos").jqGrid(
					"setGridParam", {
						url: url,
						ondblClickRow: function(rowid) {
							dblClickRowBandejaSolicitudes(rowid);
						}
					}
				);
				
				jQuery("#gridDocumentos").hideCol("remitente");
				jQuery("#gridDocumentos").setCaption("Documentos Adjuntos");
		}else {
			jQuery("#gridDocumentos").jqGrid(
				"setGridParam", {
					url: url,
					ondblClickRow: function(rowid) {
						var ret = jQuery("#gridDocumentos").jqGrid('getRowData',rowid);
						var estatusDoc = getValorDelGrid(ret.estatus);
						if (estatusDoc == "<%=DocumentosReinsercionAction.ESTATUS_DOCUMENTO_DEFINITIVO%>"){
							dblClickRowBandejaSolicitudes(rowid);
						}else{
							mostrarDocumento(rowid);							
						}
					}
				}
			);
			
			jQuery("#gridDocumentos").hideCol("remitente");
			jQuery("#gridDocumentos").setCaption("Documentos Registrados");		
		}
		
		jQuery("#gridDocumentos").trigger("reloadGrid");
		
		listaDesplegada = tipoConsulta;	
	}

	function limpiarListaDocumentos(){
		jQuery("#gridDocumentos").jqGrid("clearGridData", true);
		jQuery("#gridDocumentos").trigger("reloadGrid");	
	
	}
	
	function getValorDelGrid(valor){
		if(valor != undefined && valor != "undefined"){
		 	if(valor.indexOf("div") > -1){
		 		var from = valor.indexOf(">", valor.indexOf("div"));
				var to = valor.indexOf("<", from);
				if(from != -1 && to != -1) {
					valor = valor.substring(from+1, to);
				}
			}
		}
		return valor;
	}
	
	function validarDocumentosIntegracionExpediente(expedienteId){
		documentosAsociados = true;
			$.ajax({
				type: "post",
				url:'<%=request.getContextPath()%>/consultarDocumentosIntegracionExpediente.do',
				data: {
					expedienteId: expedienteId
				},
				dataType: "json",
				async: false,
				success: function( objJson ){
					jQuery("#dialog-validaDocumentos").append(creaTablaDocumentosIntegracion(objJson));
				},
				error: function(){
					customAlert("No se pudo llevar a cabo la consulta de los documentos.<br/> Intente de nuevo por favor ");
				}
			});
	}
	
	function creaTablaDocumentosIntegracion(objJson){
		var strContentDiv = '<table>';
		for (key in objJson){
			var nombreDocumento = objJson[key].nombreTipoDocumento;
			strContentDiv += '<tr><td>';
			strContentDiv += '<input type="checkbox" name="'+objJson[key].documentoIntegracionId
					+'" value="'+objJson[key].asociado+'" disabled="true"';
			if (objJson[key].asociado == 'true'
					|| objJson[key].asociado == true){
				strContentDiv += ' checked="checked" ';
			}
			if (objJson[key].obligatorio == 'true'
					|| objJson[key].obligatorio == true){
				nombreDocumento +='*';
				documentosAsociados = documentosAsociados && objJson[key].asociado;
			}
			strContentDiv += '></td><td>'+ nombreDocumento +'</td></tr>';
		}
		strContentDiv += '</table>';
		return strContentDiv;
	}
	
	function integrarProcedimientoEjecucion(){
		jQuery( "#btnIntegrar" ).button( "option", "disabled", true );
		$.ajax({
			type: "post",
			url:'<%=request.getContextPath()%>/integrarProcedimientoEjecucion.do',
			data: {
				idNumeroExpediente			: numeroExpedienteId,
				idEstatusNumeroExpediente	: <%=EstatusExpediente.ABIERTO_EJECUCION.getValorId()%>,
				estaArraigado				: arraigado,
				motivo						: motivo,
				sentenciaId					: sentenciaId
			},
			dataType: "xml",
			async: false,
			success: function(xml){
				customAlert("La integraci&oacute;n del expediente se ha realizado con &eacute;xito","&Eacute;xito",cerrarCustomVentana);
			},
			error: function(){
				customAlert("No se pudo llevar a cabo la integraci&oacute;n del procedimiento de ejecuci&oacute;n.<br/>"
				+" Intente de nuevo por favor ");
			}
		});
	}
</script>

<table >
	<tr>
	<td width="20%" valign="top">
	
		<div id="menuDocumentos" class="demo">
			<ul>
				<li>
					<a href="javascript:void(0);" onclick='llenarGridDocumentos("<%=SolicitudDTO.POR_ATENDER%>");'>
						Documentos Recibidos
					</a>
				</li>
				<li>
					<a href="javascript:void(0);" onclick='llenarGridDocumentos("<%=SolicitudDTO.GENERADAS%>");'>
						Documentos Enviados
					</a>
				</li>
				<li>
					<a href="javascript:void(0);" onclick='llenarGridDocumentos("<%=SolicitudDTO.PARCIALES%>");'>
						Documentos Registrados
					</a>
				</li>
				<li>
					<a href="javascript:void(0);" onclick='llenarGridDocumentos("<%=SolicitudDTO.ADJUNTOS_EXPEDIENTE%>");'>
						Documentos Adjuntos
					</a>
				</li>
			</ul>
		</div>
	</td>
	<td width="80%">
		<table id="gridDocumentos"></table>
		<div id="pagerGridDocumentos"></div>
	</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<button id="btnIntegrar" type="button" class="btn_grande">Integrar Procedimiento de Ejecuci&oacute;n</button>
		</td>
	</tr>
</table>
<div id="dialog-validaDocumentos">
</div>