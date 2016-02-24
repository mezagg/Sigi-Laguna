<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
<script type="text/javascript">
	var idInstitucionSSP = "4";
	var idEstatusNumExp = "";
	$(document).ready(
		function () {
			var contadorBeneficios=0;
			var nombreSentenciado = $('#nombre').val()+' '+$('#apellidoPaterno').val()+' '+$('#apellidoMaterno').val();
			$('#nombreCompleto').val(nombreSentenciado);
			
			$("#idFechaInicioPena").datepicker(
					{
						changeMonth: true,
						changeYear: true,
						numberOfMonths: 1,
						showOn: "button",
						buttonImage: contextoPagina+"/resources/images/date.png",
						buttonImageOnly: true,
						onClose: function(selectedDate){
							var fechaInicio = jQuery('#idFechaInicioPena').val();
							var fechaFin = jQuery('#idFechaFinPena').val();
							if (fechaInicio != ""
								&& fechaInicio != undefined
								&& fechaInicio != 'undefined'
								&& fechaInicio.length > 9){	
								
								var computoActual = calcularIntervaloFechas(fechaInicio,"");
								jQuery('#computoActual').val(computoActual);
								
								$( "#idFechaFinPena" ).datepicker( "option", "minDate", fechaInicio );
								$( "#fechaRemisionSTR" ).datepicker( "option", "minDate", fechaInicio );
								
								if (fechaFin != ""
									&& fechaFin != undefined
									&& fechaFin != 'undefined'
									&& fechaFin.length > 9){
									
									var duracionPena = calcularIntervaloFechas(fechaInicio, fechaFin);
									jQuery('#duracionPena').val(duracionPena);								
								}
							}
						}
					}
			);
			
			$("#idFechaFinPena").datepicker(
					{
						changeMonth: true,
						changeYear: true,
						numberOfMonths: 1,
						showOn: "button",
						buttonImage: contextoPagina+"/resources/images/date.png",
						buttonImageOnly: true,
						onClose: function(selectedDate){
							var fechaInicio = jQuery('#idFechaInicioPena').val();
							var fechaFin = jQuery('#idFechaFinPena').val();	
							if (fechaFin != ""
									&& fechaFin != undefined
									&& fechaFin != 'undefined'
									&& fechaFin.length > 9){
									
									jQuery('#fechaSalidaSTR').val(fechaFin);
									$( "#idFechaInicioPena" ).datepicker( "option", "maxDate", fechaFin );
									$( "#fechaRemisionSTR" ).datepicker( "option", "maxDate", fechaFin );
									
									if (fechaInicio != ""
										&& fechaInicio != undefined
										&& fechaInicio != 'undefined'
										&& fechaInicio.length > 9){
										
									var duracionPena = calcularIntervaloFechas(fechaInicio, fechaFin);
									jQuery('#duracionPena').val(duracionPena);								
								}
							}
						}
					}
			);
			
			$("#fechaRemisionSTR").datepicker(
					{
						changeMonth: true,
						changeYear: true,
						numberOfMonths: 1,
						showOn: "button",
						buttonImage: contextoPagina+"/resources/images/date.png",
						buttonImageOnly: true,
						onClose: function(selectedDate){
							var fechaInicioRemision = jQuery('#fechaRemisionSTR').val();
							if (fechaInicioRemision != ""
								&& fechaInicioRemision != undefined
								&& fechaInicioRemision != 'undefined'
								&& fechaInicioRemision.length > 9){
								
								var computoRemision = calcularIntervaloFechas(fechaInicioRemision,"");
								jQuery('#computoRemision').val(computoRemision);
							}
						}
					}
			);
			
			var strDuracionPena = $('#aniosPena').val()+' A&ntilde;o(s), '+$('#mesesPena').val()+' Mes(es), '+$('#diasPena').val()+' D&iacute;a(s)';
			$('#duracionPena').val(strDuracionPena);
			
			var strComputoActual = $('#aniosComputoActual').val()+' A&ntilde;o(s), '+$('#mesesComputoActual').val()+' Mes(es), '+$('#diasComputoActual').val()+' D&iacute;a(s)';
			$('#computoActual').val(strComputoActual);
			
			if ($('#diasComputoRemision').val() > 0
					|| $('#mesesComputoRemision').val() > 0 
					|| $('#aniosComputoRemision').val() > 0){	
				var strComputoRemision = $('#aniosComputoRemision').val()+' A&ntilde;o(s), '+$('#mesesComputoRemision').val()+' Mes(es), '+$('#diasComputoRemision').val()+' D&iacute;a(s)';
				$('#computoRemision').val(strComputoRemision);
			}
			
			idEstatusNumExp = $('#idEstatusNumExp').val(); 
			if (idEstatusNumExp == "<%=EstatusExpediente.RECHAZADO.getValorId()%>"){
				habilitaCamposSentenciaRechazada();
			}else{
				jQuery('#idFechaInicioPena').datepicker('disable');
				jQuery('#idFechaFinPena').datepicker('disable');
			}
			
			if($('#autorizacionRPP').val() != "true"){
				jQuery('#tdRPP').hide();
			}
			
			if ($('#autorizacionLC').val() != "true"){
				jQuery('#tdLC').hide();
			}
			
			if($('#autorizacionRPP').val() != "true"
					&& $('#autorizacionRPP').val() != "true"){
				jQuery('#rowBeneficios').hide();
			}
			
			if ($('#autorizacionRPP').val() == "true"
					&& $('#autorizacionLC').val() == "true"){
				$('#libertadCondicionalRemision').val("true");
			}
			
			if ($('#idSolRPP').val() == "0"){
				deshabilitaRadios('remisionParcial');
				jQuery('#fechaRemisionSTR').datepicker('disable');
				contadorBeneficios++;
			}
			
			if ($('#idSolLC').val() == "0"){
				deshabilitaRadios('libertadCondicional');
				contadorBeneficios++;
			}
			
			if ($('#idSolRD').val() != "0"){
				habilitaRadios("reparacionPagada");
			}else{
				contadorBeneficios++;
			}
			
			if ($('#idSolMulta').val() != "0"){
				habilitaRadios("multaPagada");
			}else{
				contadorBeneficios++;
			}
			
			if (contadorBeneficios == 4
					&& idEstatusNumExp != "<%=EstatusExpediente.RECHAZADO.getValorId()%>"){
				jQuery('#rowBtnGuardar').hide();
			}
			
			jQuery("#btnGuardar").button().click(
					function() {
						var idInstParam = "?idInstitucion="+idInstitucionSSP;
						document.getElementById("actualizarSentenciaForm").action+=idInstParam;
						document.getElementById("actualizarSentenciaForm").submit();
					}
			);
			
			<% if (request.getParameter("cambioExitoso") != null){%>
			jQuery("#btnGuardar").button( "option", "disabled", true );
			customAlert("Los datos se han enviado con &eacute;xito", "Actualizaci&oacute;n Exitosa");
			<%}%>
	});
	
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
					customAlert("No se puede calcular el intervalo de fechas<br /> "+
					"Por favor verifique que el formato de la fecha de inicio y fin de pena corresponda con dd/mm/yyyy ");
				}
			});
		return intervalo;
	}
	
	function habilitaCamposSentenciaRechazada(){
		habilitaRadios("aplicaMulta");
		habilitaRadios("multaPagada");
		habilitaRadios("reparacionDanio");
		habilitaRadios("reparacionPagada");
		jQuery('#montoMulta').attr("readonly",false);
		jQuery('#montoMulta').removeAttr("class");
		jQuery('#montoDanioReparado').attr("readonly",false);
		jQuery('#montoDanioReparado').removeAttr("class");
	}
	
	function deshabilitaRadios(nombreRadio){
		var selector = "input[name='"+nombreRadio+"']";
		jQuery(selector).each(
			function(i) {
				jQuery(this).attr('disabled', true);
			});
	}
	
	function habilitaRadios(nombreRadio){
		var selector = "input[name='"+nombreRadio+"']";
		jQuery(selector).each(
			function(i) {
				jQuery(this).attr('disabled', false);
			});
	}
	
	function actualizaFechaLC(){
		var aplicaLC = $("input[type='radio'][name='libertadCondicional']:checked").val();
		if (aplicaLC == "true"){
			var fechaProbable = jQuery('#fechaProbableLC').val();
			jQuery('#fechaLibCondSTR').val(fechaProbable);
		}else{
			jQuery('#fechaLibCondSTR').val('');
		}
	}
	
</script>
<html:form action="/actualizarDatosSentencia.do" styleId="actualizarSentenciaForm">
	<fieldset style="height: 100%; min-height: 173px;">
		<table border="0" width="95%" align="center">
			<tr>
				<td colspan="2">
					<table border="0">
						<tr>
							<td align="right"><strong>N&uacute;mero de Caso:</strong></td>
							<td align="left">
								<html:text name="DatosGeneralesReinsercionForm" property="caso" readonly="true" styleId="caso" styleClass="texto" />
							</td>
						</tr>
						<tr>
							<td align="right"><strong>N&uacute;mero de Causa:</strong></td>
							<td align="left">
								<html:text name="DatosGeneralesReinsercionForm" property="causa" readonly="true" styleId="causa" styleClass="texto" />
							</td>
						</tr>
						<tr>
							<td align="right"><strong>Procedimiento de Ejecuci&oacute;n:</strong></td>
							<td align="left">
								<html:text name="DatosGeneralesReinsercionForm" property="carpeta" readonly="true" styleId="carpeta" styleClass="texto" />
							</td>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table>
						<tr>
							<td align="right">Sentenciado:</td>
							<td align="left">
								<input type="text" id="nombreCompleto" name="nombreCompleto" class="texto" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td align="right">Delito(s):</td>
							<td align="left">
								<html:text name="DatosGeneralesReinsercionForm" property="delitoCometido" readonly="true" styleId="delitoCometido" styleClass="texto" />
							</td>
						</tr>
						<tr>
							<td align="right">Estatus de Sentencia:</td>
							<td align="left">
								<!-- Se deja el estatus de sentencia Ejecutoriada, debido a que es la &uacute;nica manera de entrar en este visor -->
								<input type="text" id="estatusSentencia" name="estatusSentencia" class="texto" readonly="readonly" value="Sentencia Ejecutoriada">
							</td>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table>
						<tr>
							<td align="right">N&uacute;mero &Uacute;nico de Sentenciado (NUS):</td>
							<td align="left">
								<html:text name="DatosGeneralesReinsercionForm" property="nus" readonly="true" styleId="carpeta" styleClass="textoMediano"/>
							</td>
						</tr>
						<tr>
							<td align="right">Porcentaje cubierto de pena:</td>
							<td align="left">
								<html:text name="DatosGeneralesReinsercionForm" property="porcentajeCubiertoPena" readonly="true" size="12" styleClass="textoMediano"/>
							</td>
						</tr>
						<tr>
							<td align="right">Fecha de salida:</td>
							<td align="left">
								<html:text name="DatosGeneralesReinsercionForm" property="fechaSalidaSTR" readonly="true" size="12" styleClass="textoMediano" styleId="fechaSalidaSTR"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<hr/>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<fieldset style="height: 100%; min-height: 130px;">
						<legend> Sanci&oacute;n de Prisi&oacute;n </legend>
						<table border="0">
							<tr>
								<td align="right">Inicio de pena / Fecha de detenci&oacute;n:</td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="fechaInicioPenaSTR" readonly="true" size="30" styleId="idFechaInicioPena"/>
								</td>
							</tr>
							<tr>
								<td align="right">Fin de la pena:</td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="fechaFinPenaSTR" readonly="true" size="30" styleId="idFechaFinPena"/>
								</td>
							</tr>
							<tr>
								<td align="right">Duraci&oacute;n de la pena:</td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="duracionPena" readonly="true" size="30" styleId="duracionPena" styleClass="texto"/>
								</td>
							</tr>
							<tr>
								<td align="right">C&oacute;mputo al d&iacute;a de hoy:</td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="computoActual" readonly="true" size="30" styleId="computoActual" styleClass="texto"/>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
				<td colspan="3">
					<fieldset style="height: 100%; min-height: 130px;">
						<legend> Sanciones Pecuniarias </legend>
						<table border="0">
							<tr>
								<td colspan="2">
									<table>
										<tr>
											<td align="right">Aplica Multa:</td>
											<td align="left" style="width:100px" >
												S&iacute; <html:radio name="DatosGeneralesReinsercionForm" property="aplicaMulta" title="S&iacute;" value="true" disabled="true" /> 
												No <html:radio name="DatosGeneralesReinsercionForm" property="aplicaMulta" title="No" value="false" disabled="true" />
											</td>
										</tr>
										<tr>
											<td align="right">$ Monto:</td>
											<td align="left">
												<html:text name="DatosGeneralesReinsercionForm" property="montoMulta" readonly="true" size="10" maxlength="10" styleClass="textoChico" styleId="montoMulta"/>
											</td>
										</tr>
										<tr>
											<td align="right">Cumplida:</td>
											<td align="left" style="width:100px" >
												S&iacute; <html:radio name="DatosGeneralesReinsercionForm" property="multaPagada" title="S&iacute;" value="true" disabled="true" /> 
												No <html:radio name="DatosGeneralesReinsercionForm" property="multaPagada" title="No" value="false" disabled="true" />
											</td>
										</tr>
									</table>
								</td>
								<td colspan="2">
									<table>
										<tr>
											<td align="right">Aplica reparaci&oacute;n del da&ntilde;o: </td>
											<td align="left" style="width:100px" >
												S&iacute; <html:radio name="DatosGeneralesReinsercionForm" property="reparacionDanio" title="S&iacute;" value="true" disabled="true" /> 
												No <html:radio name="DatosGeneralesReinsercionForm" property="reparacionDanio" title="No" value="false" disabled="true" />
											</td>
										</tr>
										<tr>
											<td align="right">$ Monto: </td>
											<td align="left">
												<html:text name="DatosGeneralesReinsercionForm" property="montoDanioReparado" readonly="true" size="10" maxlength="10" styleClass="textoChico" styleId="montoDanioReparado"/>
											</td>
										</tr>
										<tr>
											<td align="right">Cumplida:</td>
											<td align="left" style="width:100px" >
												S&iacute; <html:radio name="DatosGeneralesReinsercionForm" property="reparacionPagada" title="S&iacute;" value="true" disabled="true" /> 
												No <html:radio name="DatosGeneralesReinsercionForm" property="reparacionPagada" title="No" value="false" disabled="true" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<hr/>
				</td>
			</tr>
			<tr id="rowBeneficios">
				<td colspan="4">
					<fieldset style="height: 100%;">
						<legend> Beneficios </legend>
						<table>
							<tr>
								<td colspan=2 id="tdRPP">
									<table>
										<tr>
											<td align="right">Remisi&oacute;n parcial de la pena :</td>
											<td align="left">
												S&iacute; <html:radio name="DatosGeneralesReinsercionForm" property="remisionParcial" title="S&iacute;" value="true" styleId="remisionParcial" /> 
												No <html:radio name="DatosGeneralesReinsercionForm" property="remisionParcial" title="No" value="false" styleId="remisionParcial" />
											</td>
										</tr>
										<tr>
											<td align="right">Fecha de autorizaci&oacute;n :</td>
											<td align="left">
												<html:text name="DatosGeneralesReinsercionForm" property="fechaRemisionSTR" readonly="true" size="30" styleId="fechaRemisionSTR"/>
											</td>
										</tr>
										<tr>
											<td align="right">C&oacute;mputo de remisi&oacute;n parcial de la pena :</td>
											<td align="left">
												<html:text name="DatosGeneralesReinsercionForm" property="computoRemision" readonly="true" size="30" styleClass="texto" styleId="computoRemision"/>
											</td>
										</tr>
									</table>
								</td>
								<td colspan=2 id="tdLC">
									<table>
										<tr>
											<td align="right">Libertad Condicional :</td>
											<td align="left">
												S&iacute; <html:radio name="DatosGeneralesReinsercionForm" property="libertadCondicional" title="S&iacute;" value="true" styleId="libertadCondicional" onchange="actualizaFechaLC()" /> 
												No <html:radio name="DatosGeneralesReinsercionForm" property="libertadCondicional" title="No" value="false" styleId="libertadCondicional" onchange="actualizaFechaLC()" />
											</td>
										</tr>
										<tr>
											<td align="right">Probable fecha de libertad condicional :</td>
											<td align="left">
												<html:text name="DatosGeneralesReinsercionForm" property="fechaLibCondSTR" readonly="true" size="30" styleClass="textoMediano" styleId="fechaLibCondSTR"/>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<hr/>
											</td>
										</tr>
										<tr id="rowRPPLCAplica">
											<td align="right">Libertad condicional con remisi&oacute;n :</td>
											<td align="left">
												S&iacute; <html:radio name="DatosGeneralesReinsercionForm" property="libertadCondicionalRemision" title="S&iacute;" value="true" disabled="true" styleId="libertadCondicionalRemision"/> 
												No <html:radio name="DatosGeneralesReinsercionForm" property="libertadCondicionalRemision" title="No" value="false" disabled="true" styleId="libertadCondicionalRemision"/>
											</td>
										</tr>
										<tr id="rowRPPLCFecha">
											<td align="right">Probable fecha de libertad condicional con remisi&oacute;n :</td>
											<td align="left">
												<html:text name="DatosGeneralesReinsercionForm" property="fechaLibCondRemisionSTR" readonly="true" size="30" styleClass="textoMediano"/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
			<tr id="rowBtnGuardar">
				<td colspan="6" align="center">
					<button id="btnGuardar" type="button" class="ui-button ui-corner-all ui-widget">Guardar</button>
				</td>
			</tr>
		</table>
	</fieldset>
	<html:hidden name="DatosGeneralesReinsercionForm" property="expedienteId" styleId="expedienteId" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="numeroExpedienteId" styleId="numeroExpedienteId" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="sentenciaId" styleId="sentenciaId" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="nombre" styleId="nombre" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="apellidoPaterno" styleId="apellidoPaterno" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="apellidoMaterno" styleId="apellidoMaterno" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="autorizacionRPP" styleId="autorizacionRPP" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="autorizacionLC" styleId="autorizacionLC" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="idSolRPP" styleId="idSolRPP" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="idSolLC" styleId="idSolLC" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="aniosPena" styleId="aniosPena" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="mesesPena" styleId="mesesPena" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="diasPena" styleId="diasPena" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="aniosComputoActual" styleId="aniosComputoActual" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="mesesComputoActual" styleId="mesesComputoActual" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="diasComputoActual" styleId="diasComputoActual" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="aniosComputoRemision" styleId="aniosComputoRemision" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="mesesComputoRemision" styleId="mesesComputoRemision" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="diasComputoRemision" styleId="diasComputoRemision" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="fechaProbableLC" styleId="fechaProbableLC" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="idSolRD" styleId="idSolRD" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="idSolMulta" styleId="idSolMulta" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="idRD" styleId="idRD" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="idMulta" styleId="idMulta" />
	<html:hidden name="DatosGeneralesReinsercionForm" property="estatusNumExpId" styleId="idEstatusNumExp" />
</html:form>