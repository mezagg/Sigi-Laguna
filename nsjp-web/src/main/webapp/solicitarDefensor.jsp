<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Solicitar Defensor</title>
	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
        
	<script type="text/javascript">

		var contextoPagina = "${pageContext.request.contextPath}";
		
		//parametros para esta ventana 		
		var idExpediente = '<%= request.getParameter("idExpediente")%>';
		var idWindow = '<%= request.getParameter("idWindow")%>';

		//parametros para el editor de texto
		var formaId = '<%= request.getParameter("formaId")%>';
		var actividadId = '<%= request.getParameter("actividadId")%>';
		var titulo = '<%= request.getParameter("titulo")%>';
		var estatusId = '<%= request.getParameter("estatusId")%>';
		
		$(document).ready(function() {
			
			cargaGridProbablesRespSolicitudDeDefensor();			
		});


		function cargaGridProbablesRespSolicitudDeDefensor(){

			jQuery("#gridProbRespSolicitudDeDefensor").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarProbablesResponsablesSolicitudDeDefensor.do?idExpediente='+idExpediente +'', 
				datatype: "xml",
				colNames:['Nombre Completo'], 
				colModel:[ 	
							{name:'nombresProbResp',index:'nombresProbResp',width:250}, 
						],
				pager: jQuery('#pagerGridProbRespSolicitudDeDefensor'),
				rowNum:10,
				rowList:[10,20,30],
				width:720,
				caption:'<bean:message key="plProbalbeResponsableTitulo" />',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
				   
				},
				loadComplete: function(){

		       	}
			})
		}

		/**
		*Funcion que confirma que se realizara, la solicitud de defensor
		*/
		function confirmarSolicitudDeDefensor(){
			
			window.parent.abrirEditorSolicitudDeDefensor(formaId,actividadId,estatusId,titulo);
			window.parent.cerrarVentanaSolicitudDeDefensor(idWindow);
		}
		

		function cancelarSolicitudDeDefensor(){
			
			customConfirm("&iquest;Realmente desea cancelar la solicitud de defensor?",
					"Cancelar solicitud", 
					function(){
						//Aceptar
						window.parent.cerrarVentanaSolicitudDeDefensor(idWindow);
					},
					function(){
						//Calcelar
						$( this ).dialog( "close" );
					}
			)
		}

	//FIXME AGREGAR BEAN WRITE	PARA TODAS LAS PALABRAS PROBABLE RESPONSABLE
	</script>
</head>
<body>
	<table width="100%" border="0">
	  <tr>
		<th scope="col" align="left"> 
			Se realizar&aacute; la solicitud de defensor a la Coordinaci&oacute;n de Defensor&iacute;a P&uacute;blica para los siguientes probables paticipes:
		</th>
	  </tr>
	  <tr>
		<td align="center">
			<table id="gridProbRespSolicitudDeDefensor"></table>
			<div id="pagerGridProbRespSolicitudDeDefensor"></div>
		</td>
	  </tr>
	  <tr>
		<td align="right">
			<input type="button" value="Aceptar" class="btn_Generico" id="btnAceptarSolDefensor" onclick="confirmarSolicitudDeDefensor();"/>
			<input type="button" value="Cancelar" class="btn_Generico" id="btnCancelarSolDefensor" onclick="cancelarSolicitudDeDefensor();"/>
		</td>
	  </tr>
	</table>
</body>
</html>