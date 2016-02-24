<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento" %>
<script type="text/javascript">
	var idExpedienteOp = "";
	var contextoPagina = "<%= request.getContextPath()%>";
	var autoInicioAsociado = false;
	var sentenciaAsociada = false;

	$(document).ready(
			
		function(){
			jQuery('#apTabsContenido').tabs();
			jQuery('#divDatosReinsercion').hide();
			
			idExpedienteOp = jQuery('#expedienteId').val();
			
			var dates =	$("#fechaInicioPena, #fechaFinPena").datepicker(
					{
						changeMonth: true,
						changeYear: true,
						//yearRange: new Date().getFullYear() + ":+10",
						numberOfMonths: 1,
						onSelect: function( selectedDate ) {
							var option = this.id == "fechaInicioPena" ? "minDate" : "maxDate",
							instance = $( this ).data( "datepicker" ),
							date = $.datepicker.parseDate(
							instance.settings.dateFormat ||
							$.datepicker._defaults.dateFormat,
							selectedDate, instance.settings );
							dates.not( this ).datepicker( "option", option, date );
												
						},
						onClose: function(selectedDate){
							var fechaInicio = jQuery('#fechaInicioPena').val();
							var fechaFin = jQuery('#fechaFinPena').val();
							if (fechaInicio != ""
								&& fechaInicio != undefined
								&& fechaInicio != 'undefined'
								&& fechaInicio.length > 9){	
								
								var computoActual = calcularIntervaloFechas(fechaInicio,"");
								jQuery('#computoActual').val(computoActual);
							
								if (fechaFin != ""
									&& fechaFin != undefined
									&& fechaFin != 'undefined'
									&& fechaFin.length > 9){
								
									var duracionPena = calcularIntervaloFechas(fechaInicio, fechaFin);
									jQuery('#duracionPena').val(duracionPena);								
								}
							}
						},
						showOn: "button",
						buttonImage: contextoPagina+"/resources/images/date.png",
						buttonImageOnly: true			
					}
				);
			
			jQuery("#btnAdjuntar").button().click(
					function() {
						jQuery("#dialog-selecciona-documento").dialog("open");
					}
			);
			
			jQuery("#btnEnviar").button().click(
					function() {
						if ( autoInicioAsociado
								|| validaDocumentoEnExpediente(<%=TipoDocumento.AUTO_INICIO_PROCEDIMIENTO_EJECUCION.getValorId()%>)){
							var mensaje = validarDatosObligatorios();
							if (mensaje != ""){
								customAlert("Los siguientes campos son obligatorios: <br>"
										+mensaje+"<br> Favor de verificar.", "Error de validaci&oacute;n");
							}else{
								document.getElementById("ingresarSentenciaForm").submit();
							}
						}else{
							customAlert("No se ha adjuntado el Auto de Inicio de Procedimiento de Ejecuci&oacute;n. <br>"+
								"Dicho documento es indispensable para registrar la sentencia ejecutoriada. <br>"+
								"Por favor adjunte el Auto de Inicio de Procedimiento de Ejecuci&oacute;n.",
								"Error de validaci&oacute;n");
						}
					}
			);
			
			jQuery('#dialog:ui-dialog').dialog('destroy');
			
			jQuery("#dialog-selecciona-documento").dialog({
				resizable: false,
				title: 'Seleccionar Documento',
				height:'auto',
				width:'auto',
				modal: true,
				autoOpen: false,
				closeOnEscape: false,
				buttons: 
					[	
					 	{
					 		text:"Adjuntar",
					 		id:"btn_dsaAceptar",
					 		click: function() {
					 			var tipoDocSel = $("input[type='radio'][name='tipoDocSel']:checked").val();
					 			var idActividad = 0;
					 			if (tipoDocSel != undefined 
					 					&& tipoDocSel != "undefined"
					 					&& tipoDocSel != ""){
					 				
					 				var banderaValidacion = false;
					 					if (tipoDocSel == <%=TipoDocumento.AUTO_INICIO_PROCEDIMIENTO_EJECUCION.getValorId()%>){
					 						idActividad = <%=Actividades.ADJUNTAR_AUTO_DE_PROCEDIMIENTO_DE_EJECUCION.getValorId()%>;
					 						banderaValidacion = autoInicioAsociado;
					 					}else if(tipoDocSel == <%=TipoDocumento.SENTENCIA_EJECUTORIADA.getValorId()%> ){
					 						idActividad = <%=Actividades.ADJUNTAR_SENTENCIA_EJECUTORIADA.getValorId()%>;
					 						banderaValidacion = sentenciaAsociada;
					 					}
					 				
					 				if (banderaValidacion 
					 						|| validaDocumentoEnExpediente(tipoDocSel)){
					 					
					 					customAlert("El tipo de documento seleccionado ya ha sido asociado a la sentencia. <br>"+
					 							"Solamente se puede asociar un Auto de Inicio de Procedimiento de Ejecuci&oacute;n <br>"+
					 							"y una Sentencia Ejecutoriada, por lo que no se puede adjuntar un nuevo documento.",
					 							"Error de validaci&oacute;n");
					 					
					 				}else{
					 				
					 					var extensionesPermitidas = ".pdf,.jpg";
					 					$( this ).dialog("close");

					 					$.newWindow({id:"iframewindowAdjuntarDocumento", statusBar: true, posx:50,posy:50,width:450,height:200,title:"Adjuntar documento", type:"iframe"});
										$.updateWindowContent("iframewindowAdjuntarDocumento",
												'<iframe src="' + contextoPagina + 
												'/adjuntarDocumentoAExpedienteGenerico.jsp?extensionesPermitidas=' 
														 + extensionesPermitidas + '&idExpediente='+idExpedienteOp
														 + '&idTipoActividad='+idActividad
													 	+ '&idTipoDocto='+tipoDocSel + '" width="450" height="200" />');	
					 				}									
					 			}else{
					 				customAlert('Por favor seleccione de la lista de tipos de documentos <br> '
					 						+ 'el tipo de documento que desea ajuntar.', 'Error de validaci&oacute;n');
					 			}
							}
						},
						{
							text:"Cancelar",
							id:"btn_dsaCancelar",
							click: function(){
								$( this ).dialog("close");
							}
						}
					],
				close: function() {
					jQuery('#dialog:ui-dialog').dialog('destroy');
				}
			});
			
			<% if (request.getParameter("cambioExitoso") != null){%>
				jQuery("#btnAdjuntar").button( "option", "disabled", true );
				jQuery("#btnEnviar").button( "option", "disabled", true );
				jQuery("#selectEstatus").attr('disabled', true);
				jQuery('#selectEstatus').val(2);
				visualizaDatosRS();
				customAlert("Los datos se han enviado con &eacute;xito", "Actualizaci&oacute;n Exitosa");
				cerrarCustomVentana();
			<%}%>
			
			jQuery('#rowMontoMulta').hide();
			jQuery('#rowMultaPagada').hide();
			jQuery('#rowMontoDanio').hide();
			jQuery('#rowDanioPagado').hide();
		}
	);
	
	function visualizaDatosRS(){
		var idEstatus = jQuery('#selectEstatus').val();
		if (idEstatus == '2'){
			jQuery('#divDatosReinsercion').show();
		}else{
			jQuery('#divDatosReinsercion').hide();
		}
	}
	
	function validaDocumentoEnExpediente(tipoDocumentoId){
		var documentoAsociado = false;
			$.ajax({
				type: "post",
				url:'<%=request.getContextPath()%>/consultarDocumentoPorExpedienteYTipo.do',
				data: {
					idTipoDocto: tipoDocumentoId,
					idNumeroExpediente: jQuery('#numeroExpedienteId').val()
				},
				dataType: "json",
				async: false,
				success: function( objJson ){
					var documentoId = objJson.idDocumento;
					if (documentoId != 'undefined'
							&& documentoId != undefined
							&& documentoId != '0'){
						documentoAsociado = true;
					}
				},
		
				error: function(){
					customAlert("No se pueden mostrar los detalles.<br /> Intente de nuevo por favor ");
				}
			});
		return documentoAsociado;
	}
	
	function calcularIntervaloFechas(fechaInicio, fechaFin){
		var intervalo = "";
			$.ajax({
				type: "post",
				url:'<%=request.getContextPath()%>/calcularIntervaloFechas.do',
				data: {
					fechaInicio: fechaInicio,
					fechaFin: fechaFin
				},
				dataType: "json",
				async: false,
				success: function( objJson ){
					intervalo = objJson.Anios+" A&ntilde;o(s), "+objJson.Meses+" Mes(es), "+objJson.Dias+" D&iacute;a(s) ";
				},
		
				error: function(){
					customAlert("No se puede calcular la duraci&oacute;n de la pena.<br /> "+
					"Por favor verifique que el formato de la fecha de inicio y fin de pena corresponda con dd/mm/yyyy ");
				}
			});
		return intervalo;
	}
	
	function validarDatosObligatorios(){
		var mensaje = "";
		if (jQuery('#fechaInicioPena').val() == ""){
			mensaje += "Inicio de pena";
		}
		if (jQuery('#fechaFinPena').val() == ""){
			if (mensaje.length > 1){
				mensaje += ", "
			}
			mensaje +="Fin de pena";
		}
		if (jQuery("input[type='radio'][name='aplicaMulta']:checked").val() == "true"){
			if (!validaDecimalOchoDos(jQuery('#montoMulta').val())){
				if (mensaje.length > 1){
					mensaje += ", "
				}
				mensaje +="$ Monto (Multa)";
			}
		}
		if (jQuery("input[type='radio'][name='reparacionDanio']:checked").val() == "true"){
			if (!validaDecimalOchoDos(jQuery('#montoDanioReparado').val())){
				if (mensaje.length > 1){
					mensaje += ", "
				}
				mensaje +="$ Monto (Reparaci&oacute;n del Da&ntilde;o)";
			}
		}
		return mensaje;
	}
	
	function habilitaCamposMulta(){
		var aplicaMulta = jQuery("input[type='radio'][name='aplicaMulta']:checked").val();
		if (aplicaMulta == "true"){
			jQuery('#rowMontoMulta').show();
			jQuery('#rowMultaPagada').show();
		}else{
			jQuery('#rowMontoMulta').hide();
			jQuery('#rowMultaPagada').hide();
		}
	}
	
	function habilitaCamposDanio(){
		var aplicaDanio = jQuery("input[type='radio'][name='reparacionDanio']:checked").val();
		if (aplicaDanio == "true"){
			jQuery('#rowMontoDanio').show();
			jQuery('#rowDanioPagado').show();
		}else{
			jQuery('#rowMontoDanio').hide();
			jQuery('#rowDanioPagado').hide();
		}
	}

</script>
<div id="apTabsContenido">
	<ul>
		<li><a href="#registroSentencia">Datos Sentencia</a></li>
	</ul>
	<div id="registroSentencia">
		<html:form action="/registrarSentenciaEjecutoriada.do" styleId="ingresarSentenciaForm">
			<fieldset>
				<legend><strong>Datos de la Sentencia</strong></legend>
				<table border="0" width="95%" align="center">
					<tr>
						<td colspan="3">
							<table border="0">
								<tr>
									<td align="right"><strong>N&uacute;mero de Caso:</strong></td>
									<td align="left">
										<html:text name="IngresarSentenciaForm" property="caso" readonly="true" styleId="caso" styleClass="texto" />
									</td>
								</tr>
								<tr>
									<td align="right"><strong>N&uacute;mero de Causa:</strong></td>
									<td align="left">
										<html:text name="IngresarSentenciaForm" property="causa" readonly="true" styleId="causa" styleClass="texto" />
									</td>
								</tr>
							</table>
						</td>
						<td colspan="3">
							<table border="0">
								<tr>
									<td align="right">Sentenciado:</td>
									<td align="left">
										<html:text name="IngresarSentenciaForm" property="sentenciado" readonly="true" size="80" styleClass="texto"/>
									</td>
								</tr>
								<tr>
									<td align="right">Delito(s):</td>
									<td align="left">
										<html:text name="IngresarSentenciaForm" property="delitos" readonly="true" size="80" styleClass="texto"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<strong>Estatus de Sentencia: </strong>
							<html:select name="IngresarSentenciaForm" property="idEstatus" styleId="selectEstatus" onchange="visualizaDatosRS()">
								<logic:iterate name="IngresarSentenciaForm" property="estatusSentencia" id="valorDTO">
									<option value='<bean:write name="valorDTO" property="idCampo" />'
											id='estatus_<bean:write name="valorDTO" property="idCampo" />'>
										<bean:write name="valorDTO" property="valor" />
									</option>
								</logic:iterate>
							</html:select>
						</td>
					</tr>
				</table>
			</fieldset>
			<div id="divDatosReinsercion">
				<fieldset>
					<legend>Captura de datos para Reinserci&oacute;n Social</legend>
					<table border="0" width="95%" align="center">
						<tr>
							<td colspan="6">
								<table>
									<tr>
										<td align="right">N&uacute;mero &Uacute;nico de Sentenciado: </td>
										<td align="left">
											<logic:iterate name="IngresarSentenciaForm" property="sentenciasNUS" id="sentenciaDTO">
												<bean:write name="sentenciaDTO" property="involucradoDTO.nombreCompleto"/> -
												<bean:write name="sentenciaDTO" property="cnus"/> 
												<bean:define id="valorNus">
													<bean:write name="sentenciaDTO" property="cnus"/>
            									</bean:define>
												<html:radio name="IngresarSentenciaForm" property="nus" value="<%=valorNus%>"/>
											</logic:iterate>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<fieldset style="height: 100%; min-height: 130px;">
									<legend> Sanci&oacute;n de Prisi&oacute;n </legend>
									<table>
										<tr>
											<td align="right">Inicio de pena:</td>
											<td align="left">
												<html:text name="IngresarSentenciaForm" property="fechaInicioPena" size="30" 
													styleId="fechaInicioPena" readonly="true"/>
											</td>
										</tr>
										<tr>
											<td align="right">Fin de pena:</td>
											<td align="left">
												<html:text name="IngresarSentenciaForm" property="fechaFinPena" size="30" 
													styleId="fechaFinPena" readonly="true"/>
											</td>
										</tr>
										<tr>
											<td align="right">Duraci&oacute;n de pena:</td>
											<td align="left">
												<html:text name="IngresarSentenciaForm" property="duracionPena" size="30" 
													styleId="duracionPena" readonly="true" styleClass="texto"/>
											</td>
										</tr>
										<tr>
											<td align="right">C&oacute;mputo al d&iacute;a de hoy:</td>
											<td align="left">
												<html:text name="IngresarSentenciaForm" property="computoActual" readonly="true" size="30" styleId="computoActual" styleClass="texto"/>
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
							<td colspan="3" align="center">
								<fieldset style="height: 100%; min-height: 130px;">
									<legend> Sanciones Pecuniarias </legend>
									<table>
										<tr>
											<td>
												<table>
													<tr>
														<td align="right">Aplica Multa:</td>
														<td align="left">
															S&iacute; <html:radio name="IngresarSentenciaForm" property="aplicaMulta" 
																title="S&iacute;" value="true" onchange="habilitaCamposMulta()"/> 
															No <html:radio name="IngresarSentenciaForm" property="aplicaMulta" 
																title="No" value="false" onchange="habilitaCamposMulta()"/>
														</td>
													</tr>
													<tr id="rowMontoMulta">
														<td align="right">$ Monto:</td>
														<td align="left">
															<html:text name="IngresarSentenciaForm" property="montoMulta" size="10" maxlength="9" styleId="montoMulta" onkeypress="return validaDecimales(this,event,MONTO)"/>
														</td>
													</tr>
													<tr id="rowMultaPagada">
														<td align="right">Cumplida:</td>
														<td align="left" style="width:100px" >
															S&iacute; <html:radio name="IngresarSentenciaForm" property="multaPagada" title="S&iacute;" value="true" /> 
															No <html:radio name="IngresarSentenciaForm" property="multaPagada" title="No" value="false" />
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table>
													<tr>
														<td align="right">Aplica Reparaci&oacute;n del Da&ntilde;o:</td>
														<td align="left">
															S&iacute; <html:radio name="IngresarSentenciaForm" property="reparacionDanio" 
																title="S&iacute;" value="true" onchange="habilitaCamposDanio()"/> 
															No <html:radio name="IngresarSentenciaForm" property="reparacionDanio" 
																title="No" value="false" onchange="habilitaCamposDanio()"/>
														</td>
													</tr>
													<tr id="rowMontoDanio">
														<td align="right">$ Monto:</td>
														<td align="left">
															<html:text name="IngresarSentenciaForm" property="montoDanioReparado" size="10" maxlength="9" styleId="montoDanioReparado" onkeypress="return validaDecimales(this,event,MONTO)" />
														</td>
													</tr>
													<tr id="rowDanioPagado">
														<td align="right">Cumplida:</td>
														<td align="left" style="width:100px" >
															S&iacute; <html:radio name="IngresarSentenciaForm" property="reparacionPagada" title="S&iacute;" value="true" /> 
															No <html:radio name="IngresarSentenciaForm" property="reparacionPagada" title="No" value="false" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
							<td colspan="1">
								<fieldset style="height: 100%; min-height: 130px;">
									<legend> Datos Adicionales </legend>
									<table>
										<tr>
											<td align="right">&iquest;Present&oacute; lesiones?</td>
											<td align="left">
												S&iacute; <html:radio name="IngresarSentenciaForm" property="lesionado" 
													title="S&iacute;" value="true" /> 
												No <html:radio name="IngresarSentenciaForm" property="lesionado" 
													title="No" value="false" />
											</td>
										</tr>
										<tr>
											<td align="right">Puntos por acumular:</td>
											<td align="left">
												<html:text name="IngresarSentenciaForm" property="puntosTotales" size="3" maxlength="3" onkeypress="return solonumeros(event)"/>
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
						<tr>
							<td colspan="6" align="center">
								<button id="btnAdjuntar" type="button" class="ui-button ui-corner-all ui-widget">Adjuntar Documento</button>
								<button id="btnEnviar" type="button" class="ui-button ui-corner-all ui-widget">Enviar</button>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
			<html:hidden name="IngresarSentenciaForm" property="expedienteId" styleId="expedienteId" />
			<html:hidden name="IngresarSentenciaForm" property="involucradoId" styleId="involucradoId" />
			<html:hidden name="IngresarSentenciaForm" property="numeroExpedienteId" styleId="numeroExpedienteId" />
		</html:form>
	</div>
</div>
<div id="dialog-selecciona-documento">
	<span>Por favor seleccione el tipo de documento que desea adjuntar<br></span>
	<logic:iterate name="IngresarSentenciaForm" property="lstTiposDocumento" id="valorDTO">
		<input type='radio' name='tipoDocSel' id='tipoDocSel' value='<bean:write name="valorDTO" property="idCampo"/>'/>
		<bean:write name="valorDTO" property="valor" /><br>
	</logic:iterate>
</div>