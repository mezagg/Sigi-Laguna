<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	
	<!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" /-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/south-street/jquery-ui.css" />
	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.timeentry.js"></script>
	
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
		
	<script type="text/javascript">
	
	</script>
</head>
<body>
	<div style="background-color: #EEEEEE;">
	<table width="555" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="550" >
					<table width="349" border="0" cellspacing="5" cellpadding="0">
				      <tr>
				      	<td width="20%" >N&uacute;mero de caso:</td>
				        <td width="80%"><input type="text" id="txtNumCaso" /></td>
				      </tr>
				      <tr>
				      	<td>Solicitante: </td>
				        <td><input type="text" id="txtSolicitante" style="width: 300px;">
				        </td>
				      </tr>
				      <tr>
				        <td>Dependencia:</td>
				        <td><select>
				        		<option selected="selected">-Seleccione-</option>
				        		<option>Defensor&iacute;a</option>
				        		<option>Ministerio P&uacute;blico</option>
				        	</select>
				        </td>
				      </tr>
				      <tr>
				        <td>Servicio Solicitado:</td>
				        <td><select>
				        		<option selected="selected">-Seleccione-</option>
				        		<option>Audiencia de Inputaci&oacute;n</option>
				        		<option>Audiencia Intermedia</option>
				        		<option>Audiencia de Juicio Oral</option>
				        		<option>Audiencia de Recurso</option>
				        		<option>Audiencia de Vinculaci&oacute;n</option>
				        		<option>Copia de Audio y Video</option>
				        		<option>Transcripci&oacute;n de Audiencia</option>
				        	</select>
				        </td>
				      </tr>
				      <tr>
				      	<td><input id="btnAdjuntar" type="button" value="Adjuntar Documento" class="ui-button ui-corner-all ui-widget"/></td>
				      	<td align="center"><input id="btnGuardar" type="button" value="Guardar" class="ui-button ui-corner-all ui-widget"/></td>
				      </tr>
					</table>	
				</td>
			</tr>
		</table>
	</div>
</body>
</html>