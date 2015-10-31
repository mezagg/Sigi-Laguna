<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades" %>
	   
			<!--Hoja de estilos de Layout-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<!--Hoja de estilos ultrasist-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	
	<!--Hoja de estilos windows engine (frames)-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
	
	<!--Hojas de estilos para los componentes UI de Jquery-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />	
	
	<!--Hoja de estilos para el grid-->
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	
	<!--scripts de java script-->
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-ui-1.8.11.custom.min.js"></script>
	
	<!--script de windows engine (frames)-->
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	
	<!--script de jquery UI-->
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/prettify.js"></script>
	
   <!--Scripts necesarios para la ejecucion del editor-->
   <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
   
   <!--Scripts para el bloqueo de la pantalla-->
   <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>

   <script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/currencyFormatMask.js"></script>
	      
   <script type="text/javascript">

	   		//Variables de contexto
			var CONTEXT_ROOT = '<%= request.getContextPath()%>';
			var contextoPagina = "${pageContext.request.contextPath}";
		
	   		var tipo;
	   		var idNumeroExpedienteOp;
	   		var formaId;
	   		
	   		
	   		$(document).ready(function() {
	   			
	   			tipo='<%= request.getParameter("tipo")%>';
	   			idNumeroExpedienteOp = '<%= request.getParameter("idNumeroExpedienteOp")%>';
	   			formaId = '<%= request.getParameter("formaId")%>';

	   			if(!isEmpty(tipo))
	   			{
		   			if(tipo=='<%=Actividades.REPORTE_ORDENES_DE_APREHENSION_CUMPLIDAS.getValorId()%>' ){
		   				cargaComboCoorporacionPoliciaca();
		   				$("#noEjercicio").hide();
		   				$("#reparacionDano").hide();
		   			}
		   			else if( tipo=='<%=Actividades.CONCLUSION_POR_VISTA_DE_NO_EJERCICIO.getValorId()%>'){
		   				initFechaPago();
		   				$("#ordenAprehension").hide();
		   				$("#reparacionDano").hide();
	   				}
		   			else if( tipo=='<%=Actividades.CONCLUSION_POR_REPARACION_DEL_DANO.getValorId()%>')
					{
		   				$("#noEjercicio").hide();
		   				$("#ordenAprehension").hide();
					}
		   			$("#btnGenerar").click(guardarConclusionOrdenAprension);
	   			}
	   			
	   		});
	   		
	   		function initFechaPago()
	   		{
	   			$("#fechaPago").datepicker({
	   				dateFormat: 'dd/mm/yy',
	   				yearRange: '1900:2100',
	   				changeMonth: true,
	   				changeYear: true,
	   				showOn: "button",
	   				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
	   				buttonImageOnly: true
	   			});
	   		}
	   		
	   		function cargaComboCoorporacionPoliciaca()
	   		{
	   			$.ajax({
	   				type: 'POST',
	   				url: '<%= request.getContextPath()%>/consultarCatalogoCorporacion.do',
	   				data: '',
	   				dataType: 'xml',
	   				async: false,
	   				success: function(xml){
	   					$(xml).find('corporacion').each(function(){
	   						$('#datosGeneralesCmpCorporaciones').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
	   					});
	   				}
	   			});
	   		}
	   		
	   		function guardarConclusionOrdenAprension(){
	   	    	if(validaCampos()){
	   	    		var params = '&idNumeroExpediente='+idNumeroExpedienteOp;
	   	    		params += obtenerParametros();
	   	    	    
	   	    	    $.ajax({
	   	    			type: 'POST',
	   	    			url: '<%= request.getContextPath()%>/guardarConclusionOrdenAprension.do',
	   	    			data: params, 
	   	    			async: false,
	   	    			dataType: 'xml',
	   	    			success: function(xml){										
	   	    				 if(parseInt($(xml).find('ConclusionOrdenAprensionDTO').find('conclusionOrdenAprensionId').text())>0){
	   	    					window.parent.ventanaDocumentosConclusionOrdenAprension(formaId,tipo,obtenerParametrosDocumento());
	   	    					window.parent.cerrarVentanaConclusionOrdenAprension();
	   	    				 }    					    				 
	   	    				 else{
	   	    					  customAlert('Problemas al intentar guardar, int&eacute;ntelo mas tarde');
	   	    				 }
	   	    			}
	   	    		});    
	   	    	}
	   	    }
	   		
	   		function validaCampos(){
	   			var mensaje = "";
	   			
	   			if(tipo=='<%=Actividades.REPORTE_ORDENES_DE_APREHENSION_CUMPLIDAS.getValorId()%>' ){
		   			if($("#datosGeneralesCmpCorporaciones").val()=="-1")
		   			{
		   				mensaje += "<br />- Corporaci&oacute;n Policiaca";
		   			}
	   			}
	   			else if( tipo=='<%=Actividades.CONCLUSION_POR_VISTA_DE_NO_EJERCICIO.getValorId()%>'){
		   			if($.trim($("#numFichaPago").val()).length==0)
		   			{
		   				mensaje += "<br />- No. de ficha de pago";
		   			}
		   			if($.trim($("#fechaPago").val()).length==0)
		   			{
		   				mensaje += "<br />- Fecha realizacion del pago";
		   			}
		   			if($.trim($("#montoPago").val()).length==0)
		   			{
		   				mensaje += "<br />- Monto";
		   			}
   				}
	   			else if( tipo=='<%=Actividades.CONCLUSION_POR_REPARACION_DEL_DANO.getValorId()%>'){
	   				if($("#esReparacion").val()=="-1")
		   			{
		   				mensaje += "<br />- >Conclusi&oacute;n por Reparaci&oacute;n del Da&ntilde;o";
		   			}
		   			if($.trim($("#montoReparacion").val()).length==0)
		   			{
		   				mensaje += "<br />- Monto";
		   			}
				}

	   			if(mensaje != ""){
	   				customAlert("<p align='left'>Campos obligatorios: <br />"+mensaje+"</p>");
	   				return false;
	   			}
	   			return true;
	   		}
	   		
	   		function eliminaMascaraMonedaNacional(monto){
	   			monto = monto.replace("$","");
	   			monto = monto.replace(",","");
	   			return monto;		
	   		}
	   		
	   		function obtenerParametros(){
	   			var params = '';
	   			
	   			if(tipo=='<%=Actividades.REPORTE_ORDENES_DE_APREHENSION_CUMPLIDAS.getValorId()%>' ){
	   				params += '&corporacion=' + $('#datosGeneralesCmpCorporaciones option:selected').val();
	   			}
	   			else if( tipo=='<%=Actividades.CONCLUSION_POR_VISTA_DE_NO_EJERCICIO.getValorId()%>'){
	   				params += '&numFichaPago=' + $('#numFichaPago').val();
	   				params += '&fechaPago=' + $('#fechaPago').val();
	   				params += '&montoPago=' + eliminaMascaraMonedaNacional($('#montoPago').val());
   				}
	   			else if( tipo=='<%=Actividades.CONCLUSION_POR_REPARACION_DEL_DANO.getValorId()%>'){
	   				params += '&esReparacion=' + $('#esReparacion option:selected').val();
	   				params += '&montoPago=' + eliminaMascaraMonedaNacional($('#montoReparacion').val());
				}

	   			return params;
	   		}
	   		
	   		function obtenerParametrosDocumento(){
	   			var params = '';
	   			
	   			if(tipo=='<%=Actividades.REPORTE_ORDENES_DE_APREHENSION_CUMPLIDAS.getValorId()%>' ){
	   				params += '&corporacion=' + $('#datosGeneralesCmpCorporaciones option:selected').text();
	   			}
	   			else if( tipo=='<%=Actividades.CONCLUSION_POR_VISTA_DE_NO_EJERCICIO.getValorId()%>'){
	   				params += '&numFichaPago=' + $('#numFichaPago').val();
	   				params += '&fechaPago=' + $('#fechaPago').val();
	   				params += '&montoPago=' + eliminaMascaraMonedaNacional($('#montoPago').val());
   				}
	   			else if( tipo=='<%=Actividades.CONCLUSION_POR_REPARACION_DEL_DANO.getValorId()%>'){
	   				params += '&esReparacion=' + $('#esReparacion option:selected').text();
	   				params += '&montoPago=' + eliminaMascaraMonedaNacional($('#montoReparacion').val());
				}

	   			return params;
	   		}
	   		  	
	</script>
</head>
<body>
	<br><br>
	<table border="0" width="600">
		<tr>
			<td>
				<div id="ordenAprehension">
					<fieldset>
					    <legend>Orden de Aprehensi&oacute;n</legend>
					    <table border="0">
							<tr>
								<td align="right">Corporaci&oacute;n Policiaca: </td>
								<td>
									<select id="datosGeneralesCmpCorporaciones">
										<option value="-1" selected="selected">-Seleccione-</option>
									</select>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div id="noEjercicio">
					<fieldset>
					    <legend>Conclusi&oacute;n por vista de no ejercicio</legend>
					    <table border="0">
							<tr>
								<td align="right">No. de ficha de pago: </td>
								<td>
									<input type="text" size="50" maxlength="40" id="numFichaPago"/>
								</td>
							</tr>
							<tr>
								<td align="right">Fecha realizacion del pago: </td>
								<td>
									<input type="text" id="fechaPago" name="fechaPago" maxlength="10" readonly="readonly" style="width: 180px;" />
								</td>
							</tr>
							<tr>
								<td align="right">Monto: </td>
								<td>
									<input type="textbox" style="text-align:right" id="montoPago"  
	         							onkeyup="javascript:return mask(this.value,this);"
	         							onblur="javascript:return mask(this.value,this);"/>
<!-- 									<input type="text" size="50" maxlength="40" id="montoPago"/> -->
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div id="reparacionDano">
					<fieldset>
					    <legend>Conclusi&oacute;n por Reparaci&oacute;n del Da&ntilde;o</legend>
					    <table border="0">
							<tr>
								<td align="right">Conclusi&oacute;n por Reparaci&oacute;n del Da&ntilde;o: </td>
								<td>
									<select id="esReparacion">
										<option value="-1" selected="selected">-Seleccione-</option>
										<option value="0" >sin reparaci&oacute;n</option>
										<option value="1" >con reparaci&oacute;n</option>
									</select>
								</td>
							</tr>
							<tr>
								<td align="right">Monto: </td>
								<td>
									<input type="textbox" style="text-align:right" id="montoReparacion"  
	         							onkeyup="javascript:return mask(this.value,this);"
	         							onblur="javascript:return mask(this.value,this);"/>
<!-- 									<input type="text" size="50" maxlength="40" id="montoReparacion"/> -->
								</td>
							</tr>
						</table>
					</fieldset>	
				</div>
			</td>
		</tr>
		<tr>
			<td align="right">
				<input type="button" id="btnGenerar" value="Generar" class="btn_Generico"/>
			</td>
		</tr>
	</table>
</body>
</html>