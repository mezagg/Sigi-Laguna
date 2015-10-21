<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Tablero de Control</title>
	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<!--css para ventanas-->
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>	
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>

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

	var ejecutaDODinamico ="";
	var etiquetaAgencia = '<bean:message key="agencia"/>';
	
	$(document).ready(function() {
		
		$("#selectTipoGrafica").css("display","block");
		$("#imagenReporteDiv").css("display","none");
		$("#imagenGrafica").css("display","block");
		
		$("#idbotonGrafica").hide(); 
		
		cargaListaGraficos();
		
		$("#idTiempoInicioGrafica,#idTiempoFinGrafica").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true
		});
		
		$("#idTiempoInicioGrafica,#idTiempoFinGrafica").attr("disabled","disabled");
	});

	function habilitaBoton(){
		$("#idbotonGrafica").show();
	}
	
	
    /*
	*Funcion que dispara el Action para generar el reporte
	*/
	function invocacionGrafico(){
    	var selectTipoGrafica = $("#selectTipoGrafica option:selected").val();
    	var tiempoInicial= $("#idTiempoInicioGrafica").val();
    	var tiempoFinal=  $("#idTiempoFinGrafica").val();
    	
    	if(tiempoInicial!=""&&tiempoFinal!=""){
    		
    		switch (selectTipoGrafica){
    			//NOTA: Solo queda habiliatado el default para la consulta de indicadores
    			case "1100":
					ejecutaDODinamico='<%= request.getContextPath()%>/graficaParaIndicador.do?tipoGraficaIndicador='+selectTipoGrafica+'&tiempoI='+tiempoInicial+'&tiempoF='+tiempoFinal;
					break;
				case "100":
    				ejecutaDODinamico="<%= request.getContextPath()%>/graficaDenunciaVSTipoDelito.do?tiempoI="+$("#idTiempoInicioGrafica").val()+"&tiempoF="+$("#idTiempoFinGrafica").val();
    				break;
				case "200":
					ejecutaDODinamico="<%= request.getContextPath()%>/graficaDeterminacionPorDenuncia.do?tiempoI="+$("#idTiempoInicioGrafica").val()+"&tiempoF="+$("#idTiempoFinGrafica").val();
					break;
				case "300":
    				ejecutaDODinamico="<%= request.getContextPath()%>/graficaDenunciasProbablesResponsablesDetenidos.do?tiempoI="+$("#idTiempoInicioGrafica").val()+"&tiempoF="+$("#idTiempoFinGrafica").val();
					break;
				case "400":
					ejecutaDODinamico="<%= request.getContextPath()%>/graficaPricipalesDelitos.do?tiempoI="+$("#idTiempoInicioGrafica").val()+"&tiempoF="+$("#idTiempoFinGrafica").val();
					break;
				case "500":
					ejecutaDODinamico="<%= request.getContextPath()%>/graficaDenunciasTiempo.do?tiempoI="+$("#idTiempoInicioGrafica").val()+"&tiempoF="+$("#idTiempoFinGrafica").val();
					break;
				case "600":
					ejecutaDODinamico="<%= request.getContextPath()%>/graficaDetencionesPorTipo.do?tiempoI="+$("#idTiempoInicioGrafica").val()+"&tiempoF="+$("#idTiempoFinGrafica").val();
					break;
				case "700":
					ejecutaDODinamico="<%= request.getContextPath()%>/graficaInformesElaborados.do?tiempoI="+$("#idTiempoInicioGrafica").val()+"&tiempoF="+$("#idTiempoFinGrafica").val();
					break;
				case "800":
					ejecutaDODinamico="<%= request.getContextPath()%>/graficacCasosRemitidos.do?tiempoI="+$("#idTiempoInicioGrafica").val()+"&tiempoF="+$("#idTiempoFinGrafica").val();
					break;
				case "900":
					ejecutaDODinamico="<%= request.getContextPath()%>/graficaSeguimientoMedidasCautelares.do?tiempoI="+$("#idTiempoInicioGrafica").val()+"&tiempoF="+$("#idTiempoFinGrafica").val();
					break;
				case "1000":
        			ejecutaDODinamico="<%= request.getContextPath()%>/graficaSeguimientoMedidasAlternas.do?tiempoI="+$("#idTiempoInicioGrafica").val()+"&tiempoF="+$("#idTiempoFinGrafica").val();
        			break;
        		default:
    				ejecutaDODinamico='<%= request.getContextPath()%>/graficaParaIndicador.do?tipoGraficaIndicador='+selectTipoGrafica+'&tiempoI='+tiempoInicial+'&tiempoF='+tiempoFinal+'&etiquetaAgencia='+etiquetaAgencia;
    		}
    		//abrirGraficaView("Gr&aacute;fica",ejecutaDODinamico);
        	$("#imagenGrafica").attr("src",ejecutaDODinamico);
    	}else{
        	customAlert("Selecciona un rango de fechas");
        }
    }
    
    /**
    *Funcion que dispara el Action para consultar la lista de reportes a generar grafica
    * actualmente se tiene definida en el HTML.
    */
    function cargaListaGraficos(){
    	//TODO Revisar el resto de las graficas para hacer su respectivo Indicador
    	/* $('#selectTipoGrafica').append('<option value="1100">Gr&aacute;fica Avance de Investigaciones</option>');
    	$('#selectTipoGrafica').append('<option value="100">Gr&aacute;fica Denuncias VS Tipo Delito</option>');
    	$('#selectTipoGrafica').append('<option value="200">Gr&aacute;fica Determinaci&oacute;n VS Denuncia</option>');
    	$('#selectTipoGrafica').append('<option value="300">Gr&aacute;fica Denuncias VS <bean:message key="plProbalbeResponsableTitulo"/> Detenidos</option>');
    	$('#selectTipoGrafica').append('<option value="400">Gr&aacute;fica Principales Delitos</option>');
    	$('#selectTipoGrafica').append('<option value="500">Gr&aacute;fica Denuncias Tiempo</option>');
    	$('#selectTipoGrafica').append('<option value="600">Gr&aacute;fica Detenciones Por Tipo</option>');
    	$('#selectTipoGrafica').append('<option value="700">Gr&aacute;fica Informes Elaborados</option>');
    	$('#selectTipoGrafica').append('<option value="800">Gr&aacute;fica Casos Remitidos</option>'); */
    	
    	/* $('#selectTipoGrafica').append('<option value="900">Gr&aacute;fica Seguimiento Medidas Cautelares</option>');
    	$('#selectTipoGrafica').append('<option value="1000">Gr&aacute;fica Seguimiento Medidas Alternas</option>'); */
    	cargaListaReportes();
    }
    
    /*
     * Se carga el listador de reportes
 	*/   
 	function cargaListaReportes(){	
     	$.ajax({
 			type: 'POST',
 		    url: '<%=request.getContextPath()%>/consultarIndicadoresInstitucion.do?graficas=True',
 		    data: '',
 		    dataType: 'xml',
 		    async: false,
 		    success: function(xml){
 	    		$(xml).find('listaIndicadores').find('indicadorDTO').each(function(){			    		
 	    			var titulo = $(this).find('titulo').text();
 		   			$('#selectTipoGrafica').append('<option value="' + $(this).find('indicadorId').text() + '">' + titulo + '</option>');
 				});
 		    }
 		});
 	}
    
 	function abrirGraficaView( titulo, ejecutaDODinamico) {
		$.newWindow({id:"iframewindowWindowGraficaNuevaViewer", statusBar: true, posx:85,posy:86, title:"", type:"iframe"});
	    $.updateWindowContent("iframewindowWindowGraficaNuevaViewer",'<iframe src='+ejecutaDODinamico+' width="100%" height="100%" />');
	    // $("#" +"iframewindowWindowReportViewer" + " .window-maximizeButton").click();
	}
	</script>
</head>
<body>

<table width="829" height="416" border="0">
  	<tr>
    	<td width="229" height="240">
    		<table width="229" height="402" border="0">
    			<tr><td>&nbsp;</td></tr>
    			<tr><td>&nbsp;</td></tr>
  				<tr>
  					<td width="80">&nbsp;</td>
    				<td colspan="2" align="center">Seleccione el per&iacute;odo de consulta para generar la gr&aacute;fica correspondiente: </td>
    			</tr>
    			<tr><td>&nbsp;</td></tr>
  				<tr>
  					<td width="80">&nbsp;</td>
    				<td width="100" align="center">De:      
      					<input type="text" class="" style="width: 180px;" maxlength="3" name="idTiempoInicioGrafica" id="idTiempoInicioGrafica" width="50px" tabindex="1"/>
    				</td>
    				<td width="100" align="center">A:
      					<input type="text" class="" style="width: 180px;" maxlength="3" name="idTiempoFinGrafica" id="idTiempoFinGrafica" width="50px" tabindex="2"/>
    				</td>
  				</tr>
  				<tr>
  					<td width="80">&nbsp;</td>
		 			<td colspan="2" align="center">
    					<input type="button" name="idbotonGrafica" id="idbotonGrafica" value="Generar Gr&aacute;fica" class="btn_Generico" onclick="invocacionGrafico()"/>
    				</td>
  				</tr>
  				<tr><td>&nbsp;</td></tr>
  				<tr>
  					<td width="80">&nbsp;</td>
  					<td colspan="2" align="center">Seleccione del listado que se muestra, la gr&aacute;fica que se desea generar:</td>
    			</tr>
    			<tr>  					 		
    				<td width="80">&nbsp;</td>
    				<td colspan="2" align="center"> 
        				<select name="select" size="15" id="selectTipoGrafica" style="display: none;" onchange="habilitaBoton();"></select>
    	 			</td>
    			</tr>
			</table>
		</td>
    	<td width="547"><img id="imagenGrafica"/> 
    		<div id="imagenReporteDiv">
    			<iframe id="imagenReporte" src="" style="display: none;" width="100%" height="100%" scrolling="auto">&nbsp;iframe</iframe>
    		</div>
    	</td>
  	</tr>
</table>
	
</body>
</html>