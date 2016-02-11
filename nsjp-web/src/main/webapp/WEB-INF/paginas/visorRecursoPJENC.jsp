<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Atencion a Sollicitudes</title>

	
	<!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" /-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/south-street/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript">
		    
	$(document).ready(function() {
		$( "#tabsprincipalconsulta" ).tabs();
		
	});

	
	</script>
</head>
<body>

<div id="tabsprincipalconsulta">
<ul>
	<li><a href="#tabsconsultaprincipal-1">Detalle de Recurso</a></li>
</ul>

<!--Comienza tabprincipal 1-->
<div id="tabsconsultaprincipal-1">

	<table width="800" border="0" align="center" cellpadding="0" cellspacing="5">
		<tr>
			<td colspan="4" align="center"><strong>Audiencia:
			  <input type="text"
				id="numCasoAtencionAudienciaDetalle10"
				style="width: 200px; border: 0; background: #DDD;"
				readonly="readonly" value="idemtificador de audiencia"/>
			</strong></td>
            
		</tr>
        <tr>
        	<td colspan="4" align="center"><strong>Tipo de Recurso:
        	  <input type="text"
				id="tipoAudiencia"
				style="width: 150px; border: 0; background: #DDD;"
				readonly="readonly" value="Juicio Oral"/>
        	</strong></td>
        </tr>
		<tr>
			<td width="15%" align="right"><strong>N&uacute;mero de Caso:</strong></td>
			<td width="30%"><input type="text"
				id="numCasoAtencionAudienciaDetalle" value="NSJPYUCPROC201100001"
				style="width: 150px; border: 0; background: #DDD;"
				readonly="readonly" /></td>
			<td width="8%">&nbsp;</td>
			<td width="21%">&nbsp;</td>
		</tr>
		<tr>
			<td align="right"><strong>N&uacute;mero de Expediente:</strong></td>
			<td><input type="text" id="numCasoAtencionAudienciaDetalle2"
				style="width: 150px; border: 0; background: #DDD;"
				readonly="readonly" value="000001" /></td>
			<td colspan="2" rowspan="8">
			<table id="gridAudienciasPJENC"></table>
			<div id="pager1" width="200"></div>
			</td>
		</tr>
		<tr>
			<td align="right"><strong>Fecha de Solicitud::</strong></td>
			<td><input type="text" id="numCasoAtencionAudienciaDetalle4"
				style="width: 150px; border: 0; background: #DDD;"
				readonly="readonly" value="03/06/2011" /></td>
		</tr>
		<tr>
			<td align="right"><strong>Hora de Solicitud:</strong></td>
			<td><input type="text" id="numCasoAtencionAudienciaDetalle5"
				style="width: 150px; border: 0; background: #DDD;"
				readonly="readonly" value="11:00" /></td>
		</tr>
		<tr>
			<td align="right"><strong>Instituci&oacute;n Solicitante:</strong></td>
			<td><input type="text" id="numCasoAtencionAudienciaDetalle6"
				style="width: 150px; border: 0; background: #DDD;"
				readonly="readonly" value="Ministerio P&uacute;blico" /></td>
		</tr>
		<tr>
			<td align="right"><strong>Solicitante:</strong></td>
			<td><input type="text" id="numCasoAtencionAudienciaDetalle9"
				style="width: 150px; border: 0; background: #DDD;"
				readonly="readonly" value="Ricardo Rodr&iacute;guez" /></td>
		</tr>
		<tr>
			<td align="right">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="right">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="right">&nbsp;</td>
			<td>&nbsp;</td>
			
		</tr>
	</table>

</div>
<!--Termina Div Principal 1-->
</div>
</body>
</html>