<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.calidad.Calidades"%>
<%@page import="mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO"%>
<%@page import="java.util.List"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<% 
	ExpedienteDTO exp = (ExpedienteDTO)request.getAttribute("expediente");
	long etapaId = exp.getEtapa().getIdCampo();
	long idExpediente = exp.getExpedienteId();
	long idNumeroExpediente = exp.getNumeroExpedienteId();
	String numeroExpediente = exp.getNumeroExpediente();
	long estatusExp = exp.getEstatusNumeroExpediente().getIdCampo();
%>

<div id="tabsInvolucrados"  class="tabs-bottom">
	<ul>
		<li id="tabInvolucradoDefendido"><a href="#tabsInvolucrado-1" onclick="cargarParaQuienSolicita()">Para quien se solicita</a></li>
		<li id="tabInvolucrdSolicitante"><a href="#tabsInvolucrado-2" onclick="cargarSolicitante()">Solicitante ciudadano</a></li>
<!-- 	<li id="tabCedulaIdentificacion"><a href="#tabsInvolucrado-3" onclick="cargarCedulaDeIdentificacion()">Cedula de Identificacion</a></li>
		<li id="tabInvDefendidoInvitado"><a href="#tabsInvolucrado-4" onclick="cargarInvitado()">Invitado</a></li>
		<li id="tabSolicitanteJusticiaR"><a href="#tabsInvolucrado-5" onclick="cargarSolicitanteJusticiaRestaurativa()">Solicitante de justicia restaurativa</a></li>  -->
		<li id="tabInvolucrdDenunciante"><a href="#tabsInvolucrado-6">Denunciante</a></li>
		<li id="tab_Involucrado_Victima"><a href="#tabsInvolucrado-7">V&iacute;ctimas</a></li>
		<li id="tab_ProbableResponsable"><a href="#tabsInvolucrado-8"><bean:message key="probableResponsableTitulo" /></a></li>
		<li id="tab_Involucrado_Testigo"><a href="#tabsInvolucrado-9">Testigos</a></li>
		<li id="tabInvolucradoTraductor"><a href="#tabsInvolucrado-10">Traductor-Int&eacute;rprete</a></li>
		<li id="tabInvolucrdQuienDetuvo"><a href="#tabsInvolucrado-11" onclick="cargarQuienDetuvo()">Qui&eacute;n Detuvo</a></li>
	</ul>
<%
	Long idDefendido = 0L;
	Long idDenunciante = 0L;
	List<InvolucradoDTO> involucrados = null;
	involucrados = exp.getInvolucradoByCalidad(Calidades.DEFENDIDO);
	if(involucrados != null && !involucrados.isEmpty()){
		idDefendido = involucrados.get(0).getElementoId();
	}
	involucrados = exp.getInvolucradoByCalidad(Calidades.DENUNCIANTE);
	if(involucrados != null && !involucrados.isEmpty()){
		idDenunciante = involucrados.get(0).getElementoId();
	}

%>
	<div id="tabsInvolucrado-1"><!-- PARA QUIEN SE SOLITA calidad DEFENDIDO -->
		<iframe class="tabbedFrame" id="iframe_involucradoDefendido" width="100%" height="100%"></iframe>
	</div>
	<div id="tabsInvolucrado-2"><!-- SOLICITANTE CIUDADANO calidad SOLICITANTE -->
		<iframe class="tabbedFrame" id="iframe_InvolucrdSolicitante" width="100%" height="100%"></iframe>
	</div>
	<div id="tabsInvolucrado-3" style="display:none"><!-- CEDULA DE IDENTIFICACION calidad DEFENDIDO -->
		<iframe class="tabbedFrame" id="iframe_CedulaIdentificacion" width="100%" height="100%"></iframe>
	</div>
	<div id="tabsInvolucrado-4" style="display:none"><!-- INVITADO calidad no se todavia -->
		<iframe class="tabbedFrame" id="iframe_InvDefendidoInvitado" width="100%" height="100%"></iframe>
	</div>
	<div id="tabsInvolucrado-5" style="display:none"><!-- SOLICITANTE DE JUSTICIA RESTAURATIVA no se todavia -->
		<iframe class="tabbedFrame" id="iframe_SolicitanteJusticiaR" width="100%" height="100%"></iframe>
	</div>
	<div id="tabsInvolucrado-6"><!-- DENUNCIANTE calidad DENUNCIANTE -->
		<table width="25%" cellpadding="0" cellspacing="0" id="tblDenuncianteDEF">
		</table>
	</div>
	<div id="tabsInvolucrado-7"><!-- VICTIMAS calidad VICTIMA-->
		<table width="25%" cellpadding="0" cellspacing="0" id="tblVictimaDEF">
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="btnNuevaVictima"><input type="button" class="ui-button ui-corner-all ui-widget" value="Ingresar Victima" onclick="creaNuevaVictima()"/></a></td>
			</tr>
		</table>
	</div>
	<div id="tabsInvolucrado-8"><!-- PROBABLES RESPONSABLES calidad PROBABLE_RESPONSABLES-->
		<table width="25%" cellpadding="0" cellspacing="0" id="tblProbableResponsableDEF">
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="btnNuevoProbRes"><input type="button" class="ui-button ui-corner-all ui-widget" value='<bean:message key="ingProbaleResponsable"/>' onclick="creaNuevoProbResponsable()"/></a></td>
			</tr>
		</table>
	</div>
	<div id="tabsInvolucrado-9"><!-- TESTIGOS calidad TESTIGO-->
		<table width="25%" cellpadding="0" cellspacing="0" id="tblTestigoDEF">
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="btnNuevoTestigo"><input type="button" class="ui-button ui-corner-all ui-widget" value="Ingresar Testigo" onclick="creaNuevoTestigo()"/></a></td>
			</tr>
		</table>
	</div>
	<div id="tabsInvolucrado-10"><!-- TRADUCTOR-INTERPRETE calidad TRADUCTOR-->
		<table width="25%" cellpadding="0" cellspacing="0" id="tblTraductorDEF">
		</table>
	</div>
	<div id="tabsInvolucrado-11"><!-- QUIEN DETUVO calidad QUIEN_DETUVO-->
		<iframe id="iframeInvolucrdQuienDetuvo" width="100%" height="100%" ></iframe>
	</div>
</div>