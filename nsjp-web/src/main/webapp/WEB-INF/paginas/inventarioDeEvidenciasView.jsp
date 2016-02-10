<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Consultar Personal Operativo</title>
</head>

<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/estilos.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
		
<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>

<script type="text/javascript">

	$(document).ready(function() {
	
		consultaEvidencias();
	});	
	
	  //Llena el grid con los resultados de la busqueda, pasa como parametros el No del Expediente
	  var banderaCargarORecargar=0;
	  function consultaEvidencias(){
		  numeroExpediente='<%= request.getParameter("numeroExpediente")%>';
		  
			//Inicia grid
			if(banderaCargarORecargar==0){
				jQuery("#tablaBuscarPersonalOperativo").jqGrid({
					url:'<%=request.getContextPath()%>/consultarInventarioDeEvidenciasXNumExp.do?numeroExpediente='+ numeroExpediente +				
							'', 
					datatype: "xml",  							
					colNames:['# Cadena','# Evidencia','Nombre Objeto','Cantidad','Estatus'], 
					colModel:[  {name:'nombre',index:'nombre', width:100},
								{name:'especialidad',index:'especialidad', width:100},
								{name:'curp',index:'curp', width:80}, 
								{name:'rfc',index:'rfc', width:80},
								{name:'email',index:'email', width:100}
							],
							autowidth: true, 
							rowNum:10,
							rowList:[10,20,30],
							pager: jQuery('#pager1'), 
							sortname: 'id', 
							rownumbers: true,
							gridview: true, 
							viewrecords: true, 
							sortorder: "desc", 
							height: "50%",
							caption:"Resultado de la B&uacute;squeda" 												
							});
	        	$("#tablaBuscarPersonalOperativo").trigger("reloadGrid");
	        	banderaCargarORecargar=1;
	    }else{
	    	jQuery("#tablaBuscarPersonalOperativo").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarPersonalOperativoAction.do?numeroExpediente='+ numeroExpediente +
				'',datatype: "xml" });
			$("#tablaBuscarPersonalOperativo").trigger("reloadGrid");		
	    }	            	
		//Fin grid
		  
	}//Cierra consultaEvidencias

</script>

<br>
	<table align="center" id="tablaBuscarPersonalOperativo" width="800px"></table>
	<div id="pager1"></div>
<br/>