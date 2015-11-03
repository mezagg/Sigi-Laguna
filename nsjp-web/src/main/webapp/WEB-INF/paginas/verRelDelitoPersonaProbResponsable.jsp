<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ver Relaci&n Delito-Persona</title>
</head>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/estilos.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />	
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script src="<%=request.getContextPath()%>/js/prettify.js"></script>




<script type="text/javascript">
	
	
$(document).ready(function() {
	var probableResponsableId= '<%=request.getParameter("probableResponsableId") != null ? request.getParameter("probableResponsableId")
					: ""%>';
	var mandamientoPersonaId = '<%=request.getParameter("mandamientoPersonaId") != null ? request.getParameter("mandamientoPersonaId")
					: ""%>';
	var mandamientoJudicialId = '<%=request.getParameter("mandamientoJudicialId") != null ? request.getParameter("mandamientoJudicialId")
						: ""%>';
			
	cargarGridRelacionDelitoPersona(probableResponsableId,mandamientoPersonaId,mandamientoJudicialId);
		
});

	/*
	*Funcion que carga el grid de las audiencias asociadas al numero de expediente
	*/
	function cargarGridRelacionDelitoPersona(probableResponsableId, mandamientoPersonaId, mandamientoJudicialId) {
		
		var parametros='';
		parametros += '&probableResponsableId=' + probableResponsableId;
		parametros += '&mandamientoPersonaId=' + mandamientoPersonaId;
		parametros += '&mandamientoJudicialId=' + mandamientoJudicialId;
		
		//Se llena el grid de relaciones
		jQuery("#gridRelacionDelitoPersona").jqGrid({
			url : '<%=request.getContextPath()%>/consultarRelacionDelitoPersonaProbableResp.do?parametros='+parametros+'',
			datatype: "xml", 
			colNames:['<bean:message key="probableResponsable"/>','Relaci&oacute;n Delito-Persona','Victima'], 
			colModel:[	{name:'probResponsable',index:'probResponsable', width:200, align:"center",sortable:false},
						{name:'relacionDelPer',index:'relacionDelPer', width:180, align:"center",sortable:false},  
						{name:'victima',index:'victima', width:150, align:"center",sortable:false},
						
					],
			pager: jQuery('#pagerGridRelacionDelitoPersona'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth:false,
			height:250,
			viewrecords: true
		});
			
	}
	
	
	
</script>

<body>
	<table align="left" width="580" height="290" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	     <td align="center">
	       <table align="center" id="gridRelacionDelitoPersona"></table>
	       <div id="pagerGridRelacionDelitoPersona"></div>
		 </td>
	  </tr>
	</table>
</body>
</html>