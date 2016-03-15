<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Indicadores</title>
	
	<!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/themes/1.8.10/${theme.name}/jquery-ui.css" /-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/${theme.name}/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<!--css para ventanas-->
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>	
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>

	<!--para controlar las ventanas-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	
	<script type="text/javascript">

	var ejecutaDODinamico="";
	var etiquetaAgencia = '<bean:message key="agencia"/>';
	
	$(document).ready(function() {

		
		
		$("#selectTipoReporte").css("display","block");
	 	$("#imagenReporteDiv").css("display","block");
	 	$("#botonGenerarReporte").hide(); 

	 	cargaListaReportes();
	 	
		$("#idTiempoInicioReporte,#idTiempoFinReporte").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true
		});

		$("#idTiempoInicioReporte,#idTiempoFinReporte").attr("disabled","disabled");
	});

	function habilitaBoton(){
		$("#botonGenerarReporte").show();
	}
	/*
	* Se individualiza el manejo del reporte
	*/
    function invocacionReporte(){
    	var tiempoInicial = $("#idTiempoInicioReporte").val();
    	var tiempoFinal   = $("#idTiempoFinReporte").val();    	
		if(tiempoInicial!="" && tiempoFinal!=""){

			var idReporte = $("#selectTipoReporte option:selected").val();
        	ejecutaDODinamico='<%= request.getContextPath()%>/generarReporteIndicador.do?tiempoI='+tiempoInicial+'&tiempoF='+tiempoFinal+'&indicadorId='+idReporte+'&etiquetaAgencia='+etiquetaAgencia+'';
    	
        	$("#imagenReporte").attr("src",ejecutaDODinamico);
    	}else{
        	customAlert("Selecciona un rango de fechas");
       	}
    }

    /*
    * Se carga el listador de reportes
	*/   
	function cargaListaReportes(){		
    	$.ajax({
			type: 'POST',
		    url: '<%=request.getContextPath()%>/consultarIndicadoresInstitucion.do',
		    data: '',
		    dataType: 'xml',
		    async: false,
		    success: function(xml){
	    		$(xml).find('listaIndicadores').find('indicadorDTO').each(function(){			    		
	    			var titulo = $(this).find('titulo').text();
		   			$('#selectTipoReporte').append('<option value="' + $(this).find('indicadorId').text() + '">' + titulo + '</option>');
				});
		    }
		});
	}

	</script>
</head>
<body>

<table width="829"  height="416" border="0">
  	<tr>
    	<td width="900" height="240">
    		<table width="900" height="402" border="0">
    			<tr><td>&nbsp;</td></tr>
    			<tr><td>&nbsp;</td></tr>    		
  				<tr>
  				    <td width="80">&nbsp;</td>
    				<td colspan="2" align="center">Seleccione el per&iacute;odo de consulta para generar el reporte del indicador: </td>
    			</tr>
    			<tr><td>&nbsp;</td></tr>
  				<tr>
  					<td width="80">&nbsp;</td>
    				<td width="250" align="center">De:      
    					<input type="text" class="" style="width: 180px;" maxlength="3" id="idTiempoInicioReporte" width="50px" name="idTiempoInicioReporte" tabindex="1"/>
      					<!-- input type="text" name="idTiempoInicioReporte" id="idTiempoInicioReporte" width="100" -->
    				</td>
    				<td width="250" align="center">A:
    					<input type="text" class="" style="width: 180px;" maxlength="3" id="idTiempoFinReporte" width="50px" name="idTiempoFinReporte" tabindex="2"/>
      					<!--  input type="text" name="idTiempoFinReporte" id="idTiempoFinReporte" width="100"/-->
    				</td>
  				</tr>
				<tr>
					<td width="80">&nbsp;</td>
		 			<td colspan="2" align="center">
		 				<input type="button" value="Generar Reporte" id="botonGenerarReporte" class="ui-button ui-corner-all ui-widget" onclick="invocacionReporte();"/>
		 			</td>
		 		</tr>
  				<tr><td>&nbsp;</td></tr>
  				<tr>
  					<td width="80">&nbsp;</td>
    				<td colspan="2" align="center">Seleccione del listado que se muestra, el indicador que se desea consultar:</td>
    			</tr>
    			<tr>  					 		
    				<td width="80">&nbsp;</td>    		
    				<td colspan="2" align="center"> 
        				<select name="select" size="15" id="selectTipoReporte" style="display: none;" onclick="habilitaBoton();"></select>
     				</td>
    			</tr>
			</table>
		</td>
		<td width="547"> 
    		<div id="imagenReporteDiv">
	    		<iframe id="imagenReporte" src="" style="display: none;" width="100%" height="100%" scrolling="auto">&nbsp;iframe</iframe>
    		</div>
    	</td>
  	</tr>
</table>	
</body>
</html>