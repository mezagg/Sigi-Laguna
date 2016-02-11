<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Visor Medio de Prueba</title>

	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>				
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/estilos.css"  />	
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/layout_complex.css"  />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/cssGrid/ui.jqgrid.css"  />
	<!--link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.richtext.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.alerts.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.colorpicker.css"/-->
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/prettify.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery.multiselect.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/jquery.maskedinput.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/mktSignup.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.tablednd.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript">

	//Id del dato de prueba
	//var idDatoPrueba=0;
	
	var medioPruebaId;
	var archivoDigitalId="";
	var documentoId="";
	var idInvolucrado="";

	$(document).ready(function() {

		//Recuperamos el id de la audiencia ACTUAL
		medioPruebaId=<%= request.getParameter("idMedioPrueba")%>;
		//crea todas la tabs
		$("#tabsDatoPruebaPJENS").tabs();

		//Consulta los datos del medio de prueba
		consultarMedioPrueba();
		//Carga el grid medios de prueba el id del datoDePrueba
		datosDePruebaAsociados();
		
	});//FIN ON READY

	/*
	*Funcion para consultar un medio de prueba por id
	*/
	function consultarMedioPrueba(){
		
		$.ajax({
	   		type: 'POST',
	   		url: '<%=request.getContextPath()%>/consultarMedioPrueba.do',
	   		data:'medioPruebaId='+medioPruebaId,
	   		dataType: 'xml',
	   		async: false,
	   		success: function(xml){
		   		
	   			var errorCode;
				errorCode=$(xml).find('response').find('code').text();
				if(parseInt(errorCode)==0){
					$("#nombreMedioPruebaDetallePJENS").val($(xml).find('nombreMedio').first().text());
					$("#numeroMedioPruebaDetallePJENS").val($(xml).find('numeroIdentificacion').first().text());
					$("#refUbicacionMedioPruebaDetallePJENS").val($(xml).find('ubicacionFisica').first().text());
					$("#descripcionMedioPruebaPJENS").val($(xml).find('descripcion').first().text());

					documentoId = $(xml).find('documentoMedioPrueba').find('documentoId').first().text();
					archivoDigitalId = $(xml).find('archivoDigital').find('archivoDigitalId').first().text();
					idInvolucrado = $(xml).find('elementoMedioPrueba').find('elementoId').first().text();
				}
	   		}
		});
	}

	
	/*
	*Funcion para consultar un dato de prueba por id
	*/
	function consultarDatoPrueba(idDatoPrueba){
		
		$.ajax({
	   		type: 'POST',
	   		url: '<%= request.getContextPath()%>/consultarDatoPrueba.do',
	   		data:'datoPruebaId='+idDatoPrueba,
	   		dataType: 'xml',
	   		async: false,
	   		success: function(xml){
		   		
	   			var errorCode;
				errorCode=$(xml).find('response').find('code').text();
				if(parseInt(errorCode)==0){
					$("#nombreDatoPruebaDetallePJENS").val($(xml).find('nombreDato').first().text());
					$("#numeroIdDatoPruebaDetallePJENS").val($(xml).find('numeroIdentificacion').first().text());
					$("#rccDatoPruebaDetallePJENS").val($(xml).find('folioCadenaCustodia').first().text());
					var esAceptado = $(xml).find('esAceptado').first().text();
					
					if (esAceptado == "true"){
						
						$("#esAceptado").val("Si");
						
					}else{
						$("#esAceptado").val("No");
						$("#fechaCancelacionDatoPruebaDetallePJENS2").val($(xml).find('tiempoCancelacion').first().text());
						
					}
					$("#descripcionDatoPruebaPJENS").val($(xml).find('descripcion').first().text());
				}
	   		}
		});
	}
	

	/*
	*Carga el grid con los datos de prueba asociados al id del medio de prueba seleccionado por el usuario
	*/
	function datosDePruebaAsociados(){

		jQuery("#gridDatoPruebaAsociadoAlMedioPJENS").jqGrid({ 
			
			url:'<%=request.getContextPath()%>/consultarDatosPruebaAsociadosXMedioPrueba.do?medioPruebaId='+medioPruebaId+'',
			datatype: "xml", 
			colNames:['Nombre','N&uacute;mero'], 
			colModel:[ 	{name:'nombreDatoPrueba',index:'nombreDatoPrueba', width:350,align:'left'},
						{name:'numeroDatoPrueba',index:'numeroDatoPrueba', width:350,align:'left'}
					],
			pager: jQuery('#pagerGridDatoPruebaAsociadoAlMedioPJENS'),
			rowNum:10,
			rowList:[10,20,30],
			//autowidth: true,
			width:300,
			height:250,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			onSelectRow: function(rowid) {
				consultarDatoPrueba(rowid);				
			},
			loadComplete: function(){
				
				var datoPruebaIds=new Array();
				
				datoPruebaIds = jQuery("#gridDatoPruebaAsociadoAlMedioPJENS").getDataIDs();

				if(datoPruebaIds != null){
					if(datoPruebaIds.length > 0){
						consultarDatoPrueba(datoPruebaIds[0]);
					}
				}
			}
			
		}).navGrid('#pagerGridDatoPruebaAsociadoAlMedioPJENS',{edit:false,add:false,del:false});
	}


	function verMedioDePrueba(){
		
		if(documentoId != "null" && documentoId != ""){
			if(archivoDigitalId != null && archivoDigitalId != ""){
				document.frmDoc.archivoDigitalId.value = archivoDigitalId;
				document.frmDoc.documentoId.value = documentoId;
				document.frmDoc.submit();
			}
		}
		else if(idInvolucrado !="null" && idInvolucrado !=""){
			consultarInvolucradoMedioPrueba();
		}
	}

	
	var idWindowConsultarInvolucradoMedioPrueba=1;

	/*
	*Funcion para abrir la ventana de consultar involucrado 
	*/
	function consultarInvolucradoMedioPrueba() {
			
		idWindowConsultarInvolucradoMedioPrueba++;
		
		var idWindow = "iframewindowConsultaInvolucrado" + idWindowConsultarInvolucradoMedioPrueba;
		
		testigo="testigo";
		$.newWindow({id:"iframewindowConsultaInvolucrado" + idWindowConsultarInvolucradoMedioPrueba, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Consultar medio de prueba testigo", type:"iframe"});
	    $.updateWindowContent("iframewindowConsultaInvolucrado" + idWindowConsultarInvolucradoMedioPrueba,'<iframe src="<%= request.getContextPath() %>/IngresarTestigo.do?calidad='+testigo+'&idTestigo='+idInvolucrado+'&idWindow='+idWindow+'&bloquearModificacion='+true+'" width="1050" height="600" />');		
	}
	
	
	</script>
</head>
<body>

	<!--Tab Principal-->
	<div id="tabsDatoPruebaPJENS">
		<ul>
			<li id="pruebas">
				<a href="#tabsDatoPruebaPJENS-1">Medio de Prueba VS Dato de Prueba</a>
			</li>
		</ul>
		
		<!--Comienza Tab Dato de Prueba VS Medio de prueba-->
		<div id="tabsDatoPruebaPJENS-1">
			
<table width="1100" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="200"><strong>Medio de prueba</strong></td>
        <td width="200">&nbsp;</td>
        <td width="200"><strong>Dato de prueba</strong></td>
        <td width="200">&nbsp;</td>
        <td width="300"><strong>Datos de prueba asociados</strong></td>
    </tr>
    <tr>
        <td align="right">&nbsp;</td>
        <td>&nbsp;</td>
        <td align="right">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td align="right"><strong>Nombre Medio:</strong></td>
        <td><input type="text" id="nombreMedioPruebaDetallePJENS"
            style="width: 200px; border: 0; background: #DDD;"
            readonly="readonly" /></td>
        <td align="right"><strong>Nombre Dato:</strong></td>
        <td><input type="text" id="nombreDatoPruebaDetallePJENS"
            style="width: 200px; border: 0; background: #DDD;"
            readonly="readonly" /></td>
        <td rowspan="9" align="center" valign="middle">
        <table id="gridDatoPruebaAsociadoAlMedioPJENS"></table>
        <div id="pagerGridDatoPruebaAsociadoAlMedioPJENS"></div>
        </td>
    </tr>
    <tr>
        <td align="right"><strong>N&uacute;mero de identificaci&oacute;n:</strong></td>
        <td><input type="text" id="numeroMedioPruebaDetallePJENS"
            style="width: 200px; border: 0; background: #DDD;"
            readonly="readonly" /></td>
        <td align="right"><strong>N&uacute;mero identificaci&oacute;n:</strong></td>
        <td><input type="text" id="numeroIdDatoPruebaDetallePJENS"
            style="width: 200px; border: 0; background: #DDD;"
            readonly="readonly" /></td>
    </tr>
    <tr>
        <td align="right"><strong>Ref. a ubicaci&oacute;n f&iacute;sica:</strong></td>
        <td><input type="text" id="refUbicacionMedioPruebaDetallePJENS"
            style="width: 200px; border: 0; background: #DDD;"
            readonly="readonly" /></td>
        <td align="right"><strong>Reg. Cadena de custodia:</strong></td>
        <td><input type="text" id="rccDatoPruebaDetallePJENS"
            style="width: 200px; border: 0; background: #DDD;"
            readonly="readonly" /></td>
    </tr>
    <tr>
        <td align="right"></td>
        <td>
        	
			<input type="button" value="Ver Medio Prueba" id="verMedioPrueba" onclick="verMedioDePrueba();" class="ui-button ui-corner-all ui-widget">
		</td>
        <td align="right"><strong>Aceptado:</strong></td>
        <td>
			<!--<input type="radio" name="radio" id="esAceptado"-->
			<!--value="esAceptado" disabled="disabled"/>-->
            <input type="text" id="esAceptado"
			style="width: 20px; border: 0; background: #DDD;"
			readonly="readonly" />
        </td>
    </tr>
    <tr>
        <td align="right">&nbsp;</td>
        <td>&nbsp;</td>
        <td align="right">
<!--        	<strong>Fecha Cancelaci&oacute;n:</strong>}-->
        </td>
        <td>
<!--        	<input type="text" id="fechaCancelacionDatoPruebaDetallePJENS2" style="width: 200px; border: 0; background: #DDD;" readonly="readonly" />-->
        </td>
    </tr>
    <tr>
        <td align="right">&nbsp;</td>
        <td>&nbsp;</td>
        <td align="right">
<!--        	<strong>Motivo Cancelacion:</strong>-->
        </td>
        <td>
<!--        	<select name="cbxMotivoCancelacionDetalleDatoPruebaPJENS" disabled="disabled" id="cbxMotivoCancelacionDetalleDatoPruebaPJENS" style="width: 200px; border: 0; background: #DDD;"></select>-->
        </td>
    </tr>
    <tr>
        <td align="right"><strong>Descripci&oacute;n:</strong></td>
        <td>&nbsp;</td>
        <td align="right"><strong>Descripci&oacute;n:</strong></td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td align="right">&nbsp;</td>
        <td>&nbsp;</td>
        <td align="right">&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td colspan="2" align="center"><textarea name="descripcionMedioPruebaPJENS"
            cols="45" rows="5"
            readonly="readonly" id="descripcionMedioPruebaPJENS"
            style="border: 0; background: #DDD;"></textarea></td>
        <td colspan="2" align="center"><textarea name="descripcionDatoPruebaPJENS"
            cols="45" rows="5"
            readonly="readonly" id="descripcionDatoPruebaPJENS"
            style="border: 0; background: #DDD;"></textarea></td>
    </tr>
	<!--    <tr>-->
	<!--        <td align="right">&nbsp;</td>-->
	<!--        <td>&nbsp;</td>-->
	<!--        <td align="right">&nbsp;</td>-->
	<!--        <td>&nbsp;</td>-->
	<!--        <td>&nbsp;</td>-->
	<!--    </tr>-->
</table>
		</div>
		<!--Termina Tab Dato de Prueba VS Medio de prueba-->
		
	</div>
	<!--Tab Principal-->
	
	<!--forma para consultar el archivo digital-->
	<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
	<input type="hidden" name="archivoDigitalId" value=""/>
	<input type="hidden" name="documentoId" value="">
	</form>
</body>
</html>