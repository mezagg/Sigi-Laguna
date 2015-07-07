<%@page import="mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TipoMandamiento"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/funcionesComunMandJudYMedCautelares.js"></script>

<script type="text/javascript">

	/*
	*VARIABLES PARA ESTATUS DE MANDAMIENTOS JUDICIALES
	*/
	
	/**
	 * Estatus activos
	 */
	var NO_ATENDIDO 	= <%=EstatusMandamiento.NO_ATENDIDO.getValorId()%>; 
	var ATENDIDO 		= <%=EstatusMandamiento.ATENDIDO.getValorId()%>;
	var EN_PROCESO 		= <%=EstatusMandamiento.EN_PROCESO.getValorId()%>;
	var SIN_DOCUMENTO_DE_CREACION = <%=EstatusMandamiento.SIN_DOCUMENTO_DE_CREACION.getValorId()%>;
	var SIN_DOCUMENTO_DE_ESTATUS = <%=EstatusMandamiento.SIN_DOCUMENTO_DE_ESTATUS.getValorId()%>;
	var NO_ENVIADO 		= <%=EstatusMandamiento.NO_ENVIADO.getValorId()%>;
	var ACTUALIZACION_NO_ENVIADA = <%=EstatusMandamiento.ACTUALIZACION_NO_ENVIADA.getValorId()%>;
	
	/**
	 * Estatus inactivos
	 */
	var CONCLUIDO = <%=EstatusMandamiento.CONCLUIDO.getValorId()%>; 
	var CANCELADO = <%=EstatusMandamiento.CANCELADO.getValorId()%>; 
	var EJECUTADO = <%=EstatusMandamiento.EJECUTADO.getValorId()%>;
	
	$(document).ready(function() {
		idsTiposMandamientos = '<%=TipoMandamiento.ORDEN_DE_APREHENSION.getValorId()+","+TipoMandamiento.ORDEN_DE_COMPARECENCIA.getValorId()+","+TipoMandamiento.ORDEN_DE_REAPREHENSION.getValorId()%>';
		consultaGeneralMandamientoJudicial(1,<%=EstatusMandamiento.NO_ATENDIDO.getValorId()%>);
		ajustarGridAlCentro($("#gridMandamientosJudiciales"));
	});
	
	function ocultaMuestraGrids(grid){
	
		$('#divGridMandamientosJudiciales').hide();

		switch (grid){
			case "gridMandamientosJudiciales":
				$('#divGridMandamientosJudiciales').show();
    	    break;
		}
	}
</script>
<div id="mainContent">
	<div class="ui-layout-center">
		<div class="ui-layout-content">
			<div class="ui-layout-north">
				<div id="divGridMandamientosJudiciales">
					<table id="gridMandamientosJudiciales"></table>
					<div id="pagerGridMandamientosJudiciales"
						style="vertical-align: top;"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--Comienza div para mostrar la ventana para ingresar las fechas-->
<div id="busquedaFecha" style="display: none">
	<table cellspacing="0" cellpadding="0">
		<tr>
			<td width="153">&nbsp;</td>
			<td width="153">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" align="center"><strong>Fecha Inicio:</strong> <input type="text" id="fechaInicio" size="20" /></td>
		</tr>
		<tr>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" align="center"><strong>Fecha Fin:&nbsp;&nbsp; </strong>&nbsp; <input type="text" id="fechaFin" size="20" />
			</td>
		</tr>
	</table>
</div>
<!--Termina div para mostrar la ventana para ingresar las fechas-->
<!--Comienza div para mostrar la ventana para ingresar el numero de causa en mandamientos judiciales y medidas cautelares-->
<div id="divBusquedaExpediente" style="display: none">
	<table width="400" cellspacing="0" cellpadding="0" height="150">
		<tr>
			<td width="45">&nbsp;</td>
			<td width="300">&nbsp;</td>
			<td width="45">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td align="center"><strong>Ingrese el n&uacute;mero de causa: </strong></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<!--  numeroExpediente equals numeroCaso, se dejo así, porque se factorizaron funciones comúnes a agentemp y
				  encargadoCausa en funcionesComunMandJudYMedCautelares.js -->
			<td align="center"><input type="text" class="" size="30" maxlength="30" id="numeroExpediente" /></td>
			<td>&nbsp;</td>
		</tr>
	</table>
</div>
<!--Termina div para mostrar la ventana para ingresar el numero de causa en mandamientos judiciales-->