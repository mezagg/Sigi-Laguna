<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<script type="text/javascript">
	var idWindowDetalleDocto = 0;
	
	$(document).ready(
		function() {
			jQuery("#tabsDetalleSolicitudRS").tabs();
			
			jQuery( "#btnConsultarDocumento" ).button().click(
					function() {
						var idDoc = jQuery("#idDocumento").val();
						consultaPDF(idDoc);
					}
				);
			
			if (jQuery("#idDocumento").val() == "0"){
				jQuery("#btnConsultarDocumento").button( "option", "disabled", true );
			}
			
		}
	);
	
	function consultaPDF(id){
		document.frmConsultaDoc.documentoId.value = id;
		document.frmConsultaDoc.submit();
	}

</script>
<div id="tabsDetalleSolicitudRS">
	<ul>
		<li><a href="#DetalleSolicitudRS">Datos de la solicitud</a></li>
	</ul>
	<div id="DetalleSolicitudRS">
		<br/>
		<fieldset style="height: 100%; min-height: 173px;">
			<table border="0" width="95%" align="center">
				<tr>
					<td colspan="2">
						<fieldset>
							<legend>Datos de la Solicitud</legend>
							<table border="0">
								<tr>
									<td align="right">No. Procedimiento:</td>
									<td align="left">
										<html:text name="DetalleSolicitudRSForm" property="numeroExpediente" readonly="true" styleId="numeroExpediente" styleClass="texto" />
									</td>
								</tr>
								<tr>
									<td align="right">No. Caso:</td>
									<td align="left">
										<html:text name="DetalleSolicitudRSForm" property="numeroCaso" readonly="true" styleId="numeroCaso" styleClass="texto" />
									</td>
								</tr>
								<tr>
									<td align="right">Folio de la Solicitud:</td>
									<td align="left">
										<html:text name="DetalleSolicitudRSForm" property="folioSolicitud" readonly="true" styleId="folioSolicitud" styleClass="texto" />
									</td>
								</tr>
								<tr>
									<td align="right">Estatus:</td>
									<td align="left">
										<html:text name="DetalleSolicitudRSForm" property="estatusSolicitud" readonly="true" styleId="estatusSolicitud" styleClass="texto" />
									</td>
								</tr>
								<tr>
									<td align="right">Fecha de Solicitud:</td>
									<td align="left">
										<html:text name="DetalleSolicitudRSForm" property="fechaSolicitudSTR" readonly="true" styleId="fechaSolicitudSTR" styleClass="texto" />
									</td>
								</tr>
								<tr>
									<td align="right">Tipo de Solicitud:</td>
									<td align="left">
										<html:text name="DetalleSolicitudRSForm" property="tipoSolicitud" readonly="true" styleId="tipoSolicitud" styleClass="texto" />
									</td>
								</tr>
							</table>
						</fieldset>
					</td>
					<td colspan="2">
						<fieldset>
							<legend>Datos del Documento</legend>
							<table>
								<tr>
									<td align="right">Destinatario:</td>
									<td align="left">
										<html:text name="DetalleSolicitudRSForm" property="destinatario" readonly="true" styleClass="texto"/>
									</td>
								</tr>
								<tr>
									<td align="right">Instituci&oacute;n:</td>
									<td align="left">
										<html:text name="DetalleSolicitudRSForm" property="institucion" readonly="true" styleClass="texto"/>
									</td>
								</tr>
								<tr>
									<td align="right">Folio del Documento:</td>
									<td align="left">
										<html:text name="DetalleSolicitudRSForm" property="folioDocumento" readonly="true" styleClass="texto"/>
									</td>
								</tr>
								<tr>
									<td align="right">Fecha de Creaci&oacute;n:</td>
									<td align="left">
										<html:text name="DetalleSolicitudRSForm" property="fechaCreacion" readonly="true" styleClass="texto"/>
									</td>
								</tr>
								<tr>
									<td align="right">Tipo del Documento:</td>
									<td align="left">
										<html:text name="DetalleSolicitudRSForm" property="tipoDocumento" readonly="true" styleClass="texto"/>
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<button id="btnConsultarDocumento" type="button">Ver documento</button>
									</td>
								</tr>
							</table>
						</fieldset>
					</td>
			</table>
		</fieldset>
		<html:hidden name="DetalleSolicitudRSForm" property="idDocumento" styleId="idDocumento" />
		<form name="frmConsultaDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
			<input type="hidden" name="documentoId" />
		</form>
	</div>
</div>