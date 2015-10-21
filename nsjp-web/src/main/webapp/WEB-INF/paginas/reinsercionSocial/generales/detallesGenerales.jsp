
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
	<script type="text/javascript">
	jQuery().ready(function () {
		var strDuracionPena = $('#aniosPena').val()+' A&ntilde;o(s), '+$('#mesesPena').val()+' Mes(es), '+$('#diasPena').val()+' D&iacute;a(s)';
		$('#duracionPena').val(strDuracionPena);
		
		var strComputoRemision = $('#aniosComputoRemision').val()+' A&ntilde;o(s), '+$('#mesesComputoRemision').val()+' Mes(es), '+$('#diasComputoRemision').val()+' D&iacute;a(s)';
		$('#computoRemision').val(strComputoRemision);
		
		var strComputoActual = $('#aniosComputoActual').val()+' A&ntilde;o(s), '+$('#mesesComputoActual').val()+' Mes(es), '+$('#diasComputoActual').val()+' D&iacute;a(s)';
		$('#computoActual').val(strComputoActual);
	});
	</script>
		<fieldset style="height: 100%; min-height: 173px;">
			<table border="0">
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
							<tr>
								<td align="right"><strong>Delito:</strong></td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="delitoCometido" readonly="true" styleId="delitoCometido" styleClass="texto" />
								</td>
							</tr>
						</table>
					</td>
					<td colspan="2">
						<table>
							<tr>
								<td align="right">Nombre(s):</td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="nombre" readonly="true" size="30" styleClass="texto"/>
								</td>
							</tr>
							<tr>
								<td align="right">Apellido Paterno:</td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="apellidoPaterno" readonly="true" size="30" styleClass="texto"/>
								</td>
							</tr>
							<tr>
								<td align="right">Apellido Materno:</td>
								<td align="left">
									<html:text name="DatosGeneralesReinsercionForm" property="apellidoMaterno" readonly="true" size="30" styleClass="texto"/>
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
									<html:text name="DatosGeneralesReinsercionForm" property="fechaSalidaSTR" readonly="true" size="12" styleClass="textoMediano"/>
								</td>
							</tr>
<!-- 							<tr> -->
<!-- 								<td align="right">Edad biol&oacute;gica:</td> -->
<!-- 								<td align="left"> -->
<%-- 									<html:text name="DatosGeneralesReinsercionForm" property="edad" readonly="true" size="10" /> --%>
<!-- 								</td> -->
<!-- 							</tr> -->
<!-- 							<tr> -->
<!-- 								<td align="right">&iquest;Present&oacute; lesiones?</td> -->
<!-- 								<td align="left"> -->
<%-- 									S&iacute; <html:radio name="DatosGeneralesReinsercionForm" property="lesionado" title="S&iacute;" value="1" disabled="true" />  --%>
<%-- 									No <html:radio name="DatosGeneralesReinsercionForm" property="lesionado" title="No" value="0" disabled="true" /> --%>
<!-- 								</td> -->
<!-- 							</tr> -->
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<hr/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<fieldset style="height: 100%; min-height: 130px;">
							<legend> Sanci&oacute;n de Prisi&oacute;n </legend>
							<table border="0">
<!-- 								<tr> -->
<!-- 									<td align="right">Tipo de sentencia:</td> -->
<!-- 									<td align="left"> -->
<%-- 										<html:text name="DatosGeneralesReinsercionForm" property="tipoSentencia" readonly="true" size="30"/> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
								<tr>
									<td align="right">Inicio de pena / Fecha de detenci&oacute;n:</td>
									<td align="left">
										<html:text name="DatosGeneralesReinsercionForm" property="fechaInicioPenaSTR" readonly="true" size="30" styleClass="textoMediano"/>
									</td>
								</tr>
								<tr>
									<td align="right">Fin de la pena:</td>
									<td align="left">
										<html:text name="DatosGeneralesReinsercionForm" property="fechaFinPenaSTR" readonly="true" size="30" styleClass="textoMediano"/>
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
					<td colspan="2">
						<fieldset style="height: 100%; min-height: 130px;">
							<legend> Reclusi&oacute;n </legend>
							<table border="0">
								<tr>
									<td align="right"> Lugar de reclusi&oacute;n: </td>
									<td align="left">
										<html:text name="DatosGeneralesReinsercionForm" property="ceresoAsignado" readonly="true" size="30" styleClass="textoMediano"/>
									</td>
								</tr>
<!-- 								<tr> -->
<!-- 									<td align="right">&iquest;Se encuentra f&iacute;sicamente en el CERESO?</td> -->
<!-- 									<td align="left"> -->
<%-- 										S&iacute; <html:radio name="DatosGeneralesReinsercionForm" property="encarcelado" title="S&iacute;" value="true" disabled="true" />  --%>
<%-- 										No <html:radio name="DatosGeneralesReinsercionForm" property="encarcelado" title="No" value="false" disabled="true" /> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
								<tr>
									<td align="right">Fecha de ingreso:</td>
									<td align="left">
										<html:text name="DatosGeneralesReinsercionForm" property="fechaIngresoCeresoSTR" readonly="true" size="30" styleClass="textoMediano"/>
									</td>
								</tr>
							</table>
						</fieldset>
					</td>
					<td colspan="2">
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
													<html:text name="DatosGeneralesReinsercionForm" property="montoMulta" readonly="true" size="10" maxlength="10" styleClass="textoChico"/>
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
													<html:text name="DatosGeneralesReinsercionForm" property="montoDanioReparado" readonly="true" size="10" maxlength="10" styleClass="textoChico"/>
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
				<tr>
					<td colspan="4">
						<fieldset style="height: 100%;">
							<legend> Beneficios </legend>
							<table>
								<tr>
									<td colspan=2>
										<table>
											<tr>
												<td align="right">Remisi&oacute;n parcial de la pena :</td>
												<td align="left">
													S&iacute; <html:radio name="DatosGeneralesReinsercionForm" property="remisionParcial" title="S&iacute;" value="true" disabled="true" /> 
													No <html:radio name="DatosGeneralesReinsercionForm" property="remisionParcial" title="No" value="false" disabled="true" />
												</td>
											</tr>
											<tr>
												<td align="right">Fecha de autorizaci&oacute;n :</td>
												<td align="left">
													<html:text name="DatosGeneralesReinsercionForm" property="fechaRemisionSTR" readonly="true" size="30" styleClass="textoMediano"/>
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
									<td colspan=2>
										<table>
											<tr>
												<td align="right">Libertad Condicional :</td>
												<td align="left">
													S&iacute; <html:radio name="DatosGeneralesReinsercionForm" property="libertadCondicional" title="S&iacute;" value="true" disabled="true" /> 
													No <html:radio name="DatosGeneralesReinsercionForm" property="libertadCondicional" title="No" value="false" disabled="true" />
												</td>
											</tr>
											<tr>
												<td align="right">Probable fecha de libertad condicional :</td>
												<td align="left">
													<html:text name="DatosGeneralesReinsercionForm" property="fechaLibCondSTR" readonly="true" size="30" styleClass="textoMediano"/>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<hr/>
												</td>
											</tr>
											<tr>
												<td align="right">Libertad condicional con remisi&oacute;n :</td>
												<td align="left">
													S&iacute; <html:radio name="DatosGeneralesReinsercionForm" property="libertadCondicionalRemision" title="S&iacute;" value="true" disabled="true" /> 
													No <html:radio name="DatosGeneralesReinsercionForm" property="libertadCondicionalRemision" title="No" value="false" disabled="true" />
												</td>
											</tr>
											<tr>
												<td align="right">Probable fecha de libertad condicional con remisi&oacute;n :</td>
												<td align="left">
													<html:text name="DatosGeneralesReinsercionForm" property="fechaLibCondRemisionSTR" readonly="true" size="30" styleClass="textoMediano"/>
												</td>
											</tr>
										</table>
									</td>
<!-- 									<td colspan=2> -->
<!-- 										<table> -->
<!-- 										</table> -->
<!-- 									</td> -->
								</tr>
							</table>
						</fieldset>
					</td>
<!-- 					<td colspan="1"> -->
<!-- 						<fieldset style="height: 100%; min-height: 173px;"> -->
<!-- 							<legend> Programas </legend> -->
<!-- 							<div id="scrollProgramas" style="height: 100%; min-height: 173px; overflow-y:auto;"> -->
<!-- 								<table> -->
<%-- 									<logic:iterate name="DatosGeneralesReinsercionForm" property="lstProgramas" id="programa"> --%>
<!-- 										<tr> -->
<!-- 											<td> -->
<%-- 												<bean:write name="programa"/> --%>
<!-- 											</td> -->
<!-- 										</tr> -->
<%-- 									</logic:iterate> --%>
<!-- 								</table> -->
<!-- 							</div> -->
<!-- 						</fieldset> -->
<!-- 					</td> -->
<!-- 					<td colspan="2"> -->
<!-- 						<fieldset style="height: 100%; min-height: 173px;"> -->
<!-- 							<legend> Puntos Obtenidos </legend> -->
<!-- 							<table> -->
<!-- 								<tr> -->
<!-- 									<td align="right">Puntos acumulados:</td> -->
<!-- 									<td align="left"> -->
<%-- 										<html:text name="DatosGeneralesReinsercionForm" property="puntosAcumulados" readonly="true" size="10"/> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td align="right">Puntos totales:</td> -->
<!-- 									<td align="left"> -->
<%-- 										<html:text name="DatosGeneralesReinsercionForm" property="puntosTotales" readonly="true" size="10"/> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td align="right"> -->
<%-- 										<html:text name="DatosGeneralesReinsercionForm" property="porcentajeCumplido" readonly="true" size="10"  styleClass="texto" style="text-align :right;"/> --%>
<!-- 									</td> -->
<!-- 									<td align="left"><strong>% Cumplido</strong></td> -->
<!-- 								</tr> -->
<!-- 							</table> -->
<!-- 						</fieldset> -->
<!-- 					</td> -->
<!-- 					<td colspan="2"> -->
<!-- 						<fieldset style="height: 100%; min-height: 173px;"> -->
<!-- 							<legend> C&oacute;mputo de pena: </legend> -->
<!-- 							<table border="0"> -->
<!-- 								<tr> -->
<!-- 									<td align="right">Candidato a beneficio:</td> -->
<!-- 									<td align="left"> -->
<%-- 										S&iacute; <html:radio name="DatosGeneralesReinsercionForm" property="candidatoBeneficio" title="S&iacute;" value="1" disabled="true" />  --%>
<%-- 										No <html:radio name="DatosGeneralesReinsercionForm" property="candidatoBeneficio" title="No" value="0" disabled="true" /> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td align="right">Beneficio:</td> -->
<!-- 									<td align="left"> -->
<%-- 										Liberaci&oacute;n con condicional <html:radio name="DatosGeneralesReinsercionForm" property="beneficio"  --%>
<!-- 													title="Liberaci&oacute;n con condicional" value="1" disabled="true" /><br/> -->
<%-- 										Liberaci&oacute;n con definitiva  <html:radio name="DatosGeneralesReinsercionForm" property="beneficio"  --%>
<!-- 													title="Liberaci&oacute;n con definitiva" value="2>" disabled="true" /> -->
<!-- 									</td> -->
<!-- 								</tr> -->
<!-- 							</table> -->
<!-- 						</fieldset> -->
<!-- 					</td> -->
				</tr>		
			</table>
		</fieldset>
<html:hidden name="DatosGeneralesReinsercionForm" property="expedienteId" styleId="expedienteId" />
<html:hidden name="DatosGeneralesReinsercionForm" property="numeroExpedienteId" styleId="numeroExpedienteId" />
<html:hidden name="DatosGeneralesReinsercionForm" property="sentenciaId" styleId="sentenciaId" />
<html:hidden name="DatosGeneralesReinsercionForm" property="aniosPena" styleId="aniosPena" />
<html:hidden name="DatosGeneralesReinsercionForm" property="mesesPena" styleId="mesesPena" />
<html:hidden name="DatosGeneralesReinsercionForm" property="diasPena" styleId="diasPena" />
<html:hidden name="DatosGeneralesReinsercionForm" property="estatusNumExpId" styleId="estatusNumExpId" />
<html:hidden name="DatosGeneralesReinsercionForm" property="rolActivoId" styleId="rolActivoId" />
<html:hidden name="DatosGeneralesReinsercionForm" property="aniosComputoRemision" styleId="aniosComputoRemision" />
<html:hidden name="DatosGeneralesReinsercionForm" property="mesesComputoRemision" styleId="mesesComputoRemision" />
<html:hidden name="DatosGeneralesReinsercionForm" property="diasComputoRemision" styleId="diasComputoRemision" />
<html:hidden name="DatosGeneralesReinsercionForm" property="aniosComputoActual" styleId="aniosComputoActual" />
<html:hidden name="DatosGeneralesReinsercionForm" property="mesesComputoActual" styleId="mesesComputoActual" />
<html:hidden name="DatosGeneralesReinsercionForm" property="diasComputoActual" styleId="diasComputoActual" />
