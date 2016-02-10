<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Solicitar Dictamen Pericial</title>
	
	<!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" /-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/south-street/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	
	<script type="text/javascript">
	$(document).ready(function() {
		$("#tabsPrincipal").tabs();
		$("#chbRegEvidencia").click(addCadenaRegistrada);
		
		$("#btnEnviarSolicitud").click(cerrarVentana);
		cargaFechaCaptura();
	});

	function cerrarVentana(){
		parent.cerrarVentanaEvidencia();
	}

	function cargaFechaCaptura(){
    	$.ajax({
    		type: 'POST',
    	    url: '<%=request.getContextPath()%>/ConsultarFechaCaptura.do',
    	    data: '',
    	    dataType: 'xml',
    	    async: false,
    	    success: function(xml){
    			$('#solDeSSPFecha').val($(xml).find('fechaActual').text());
    		}
		});
    }


	</script>
</head>
<body>
<table width="100%">
	<tr>
		<td>
			<div id="tabsPrincipal">
				<ul>
					<li><a href="#tabsconsultaprincipal-1">Solicitante</a></li>
					<li><a href="#tabsconsultaprincipal-2">PQS</a></li>
					<li><a href="#tabsconsultaprincipal-3">Motivo</a></li>
					<li><a href="#tabsconsultaprincipal-4">Avisar Funcionario</a></li>
					
				</ul>
				<div id="tabsconsultaprincipal-1">
					<table width="100%" border="0" height="90%">
						<tr>
							<td align="right">
								Nombre Servidor P&uacute;blico:
							</td>
							<td>
								<input type="text" class="" size="50" maxlength="50" id="solDeSSPNombre" disabled="disabled" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								Cargo:
							</td>
							<td>
								<input type="text" size="50" maxlength="50"	id="solDeSSPPuesto"  disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								&Aacute;rea Administrativa:
							</td>
							<td>
								<input type="text" size="50" maxlength="50" id="solDeSSPAreaAdmin" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								Fecha Elaboraci&oacute;n:
							</td>
							<td>
								<input type="text" size="50" maxlength="13"	id="solDeSSPFecha" disabled="disabled"/>
							</td>
						</tr>
					</table>
				</div>
				<div id="tabsconsultaprincipal-2">
					<table width="100%" border="0" height="90%">
						<tr>
							<td align="right">
								N&uacute;mero IPH:
							</td>
							<td>
								<input type="text" class="" size="50" maxlength="50" id="solNumeroIPH"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								Fecha de Vencimiento:
							</td>
							<td>
								<input type="text" size="50" maxlength="50"	id="solFechaVencimiento"  />
							</td>
						</tr>
						<tr>
							<td align="right">
								Especialidad:
							</td>
							<td>
								<select id="solSSPEspecialidad" style="width: 300px;">
									<option>- Selecciona -</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="center">
								<input type="button" id="btnGuardar" value="Guardar" class="ui-button ui-corner-all ui-widget"/>
							</td>
						</tr>
					</table>
				</div>
				<div id="tabsconsultaprincipal-3">
					<jsp:include page="/WEB-INF/paginas/ingresarNarrativaView.jsp" flush="true"></jsp:include>
				</div>
				<div id="tabsconsultaprincipal-4">
					<table width="100%" border="0" height="90%">
						<tr>
							<td>
								Nombre del Funcionario
							</td>
						</tr>
						<tr>
							<td>
								Area del Funcionario 
							</td>
						</tr>
					</table>
				</div>
			</div>
		</td>
	</tr>
</table>
</body>
</html>