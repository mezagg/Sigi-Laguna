<%@page import="mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos"%>
<%@page import="mx.gob.segob.nsjp.web.catalogo.form.CatalogosForm"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/estilos.css"/>	
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/layout_complex.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.richtext.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.alerts.css"/>
   	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.colorpicker.css"/>
   	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/cssGrid/ui.jqgrid.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/style.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/prettify.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css"/>
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/jquery.maskedinput.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/mktSignup.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>	
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ingresarMediosContacto.js"></script>
    
   	<script type="text/javascript" src="<%= request.getContextPath()%>/js/prettify.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery.multiselect.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.tablednd.js"></script>    
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/defensoria/funcionesComunes.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
			
	<script type="text/javascript">
	
<%
        CatalogosForm forma = (CatalogosForm)request.getAttribute("CatalogosForm");
        List<String> campos = forma.getEstructuraCatalogo();
        String nombre = "";
%>
		var idCatalogo = '<%=forma.getIdCatalogo()%>';		
		var contextPath = '<%=request.getContextPath()%>';

		$(document).ready(function() {
			var headers = [
<%				for(int i = 0; i < campos.size() ; i++){
			    	nombre = campos.get(i);
			        if(i < campos.size() -1){
%>						"<%=nombre%>",<%
			        }else{
%>						"<%=nombre%>"<%
			        } 
				}
%>
					  ]; 
		
			var columnas =[	               
<%					for(int i = 0; i < campos.size() ; i++){
			        	nombre = campos.get(i);
			        	if(i < campos.size() -1){
%>							{name:'<%=nombre%>',index:'310<%=i%>', width:200, align:"center"},<%
						}else{
%>							{name:'<%=nombre%>',index:'310<%=i%>', width:200, align:"center"}<%
			            }
					}
%>	
						  ];
			var varIDCat;
			var parametros = "idCatalogo="+idCatalogo;
			var gridName = "valoresCatalogo"; 
			var idGridTable = "gridCatalogo";
			var idPagerDiv = "pagerCatalogo";
			var liga = contextPath+'/ConsultarValoresCatalogo.do?'+parametros;
			
			crearGrid(gridName, idGridTable, liga, headers, columnas, idPagerDiv, 20, undefined, dobleClickValorCatalogo);
		});
				
		function dobleClickValorCatalogo(idrow){
			if(idCatalogo != null && idCatalogo != '<%=Catalogos.DELITO.ordinal()%>' 
				&& idCatalogo != '<%=Catalogos.SALA_AUDIENCIA.ordinal()%>'
					&& idCatalogo != '<%=Catalogos.AREAS_DE_NEGOCIO.ordinal()%>'){
				
				parent.agregar=false;
	 		var accion = contextPath+"/ConsultaValorDeCatalogo.do?";
	 		accion += "idCatalogo="+ idCatalogo;
	 		accion += "&catalogo.clave="+ idrow;
	 		openNewIframeWindow(window.parent.idFramePadreConsultaCatVal, accion, "Visor de cat&aacute;logos", 600, 400, 50, 50);
		 		varIDCat = idrow;
			}

	 		if(idCatalogo != null && idCatalogo == '<%=Catalogos.DELITO.ordinal()%>'){	
	 			parent.agregar=false;		
				var accion = contextPath+"/ConsultaValorDeCatalogoDelito.do?";
	 			accion += "idCatalogo="+ idCatalogo;
	 			accion += "&catalogo.clave="+ idrow;
	 			accion += "&esNuevo="+ false;
	 			openNewIframeWindow(window.parent.idFramePadreConsultaCatVal, accion, "Consulta Delito", 800, 320, 50, 50);
		 		varIDCat = idrow;
			}

	 		if(idCatalogo != null && idCatalogo == '<%=Catalogos.AREAS_DE_NEGOCIO.ordinal()%>'){	
	 			parent.agregar=false;		
				var accion = contextPath+"/ConsultaValorDeCatalogoAreasDeNegocio.do?";
	 			accion += "idCatalogo="+ idCatalogo;
	 			accion += "&catalogo.clave="+ idrow;
	 			accion += "&esNuevo="+ false;
	 			openNewIframeWindow(window.parent.idFramePadreConsultaCatVal, accion, "Consulta Areas de Negocio", 800, 320, 50, 50);
		 		varIDCat = idrow;
			}
	 		
	 		if(idCatalogo != null && idCatalogo == '<%=Catalogos.SALA_AUDIENCIA.ordinal()%>'){
				var accion = contextPath+"/ConsultaValorDeCatalogoSalaAudiencia.do?";	 			
	 			accion += "&idSala="+ idrow;
	 			accion += "&esNuevo="+ false;	 			
	 			openNewIframeWindow(window.parent.idFramePadreConsultaCatVal, accion, "Consulta Sala de Audiencia", 900, 400, 10, 50);	 			
			}
		}
		
		/*
		*Funcion que se invoca unicamente cuando se modifica un valor del catalogo
		*esta ventana es el padre y recarga su grid 
		*/
		function cerrarVentanaVerValor(){
			if(parent.agregar){
				parent.cerrarVentanaVerValor();
			}else{
				$.closeWindow(window.parent.idFramePadreConsultaCatVal);
			}
			$("#gridCatalogo").trigger("reloadGrid");
		}
		
		/*
		*Esta ventana es el iframe del catalogo y esta funcion se invoca desde el padre
		*que es administrarCatalogos.jsp
		*/
		function recargarGrid(){
			$("#gridCatalogo").trigger("reloadGrid");
		}
	</script>
</head>
<body>
	<div>
		<table id="gridCatalogo"></table>
		<div id="pagerCatalogo"></div>
	</div>
</body>
</html>