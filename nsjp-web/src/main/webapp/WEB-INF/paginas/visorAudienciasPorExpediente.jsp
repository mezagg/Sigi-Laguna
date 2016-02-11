<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Buscar Expediente</title>
</head>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/estilos.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>



<script type="text/javascript">
	var contextoPagina = "${pageContext.request.contextPath}";	
	var CONTEXT_ROOT = '<%= request.getContextPath()%>';
	//variable para comparar cuando el idRolActivo es de Encargado Sala, Juez u Oficialia de partes
	var idRolActivo= '<%=request.getParameter("idRolActivo") != null ? request.getParameter("idRolActivo") : ""%>';
	$(document).ready(function() {
		var numeroExpedienteId= '<%=request.getParameter("numeroExpedienteId") != null
					? request.getParameter("numeroExpedienteId")
					: ""%>';
		var numeroExpediente = '<%=request.getParameter("numeroExpediente") != null
					? request.getParameter("numeroExpediente")
					: ""%>';
		var rolUsuario = '<%=request.getParameter("rolUsuario") != null ? request
					.getParameter("rolUsuario") : ""%>';
		
		$("#numExpediente").val(numeroExpediente);
		cargarGridAudienciasDeCausa(numeroExpedienteId,rolUsuario);
		
	});

	/*
	*Funcion que carga el grid de las audiencias asociadas al numero de expediente
	*/
	function cargarGridAudienciasDeCausa(numeroExpedienteId,rolUsuario) {

		//Se llena el gird de audiencias
		jQuery("#gridAudiencias").jqGrid({
			url : '<%=request.getContextPath()%>/consultarAudienciasPorNumeroCausaYEstatus.do?numeroExpedienteId='+numeroExpedienteId+'', 
			datatype: "xml", 
			colNames:['Fecha','Hora','Tipo','Sala','Estatus'], 
			colModel:[	{name:'fecha',index:'fecha', width:150, align:"center"},
						{name:'hora',index:'hora', width:100, align:"center"},  
						{name:'tipo',index:'tipo', width:150, align:"center"},
						{name:'sala',index:'sala', width:200, align:"center"},
						{name:'estatusAudiencia', index:'estatusAudiencia', hidden:true,width:200, align:"center"}
					],
			pager: jQuery('#pagerGridAudiencias'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth:false,
			height:250,
			sortname:'fecha',
			viewrecords: true,
			sortorder:"desc",
			loadComplete: function(){
				var listaIds = jQuery('#gridAudiencias').getDataIDs();
				if(listaIds==null || listaIds.length<=0 || listaIds == undefined || listaIds == "undefined"){
					top.customAlert("No existen audiencias asociadas a la causa seleccionada.","");	
				}
			},
			ondblClickRow: function(rowid) {
					audienciasid=rowid.split("*")[0];
					numExpediente=rowid.split("*")[1];
					if(rolUsuario == "admonPJ"){
						top.dblClickRowBandejaAudiencias(audienciasid);
					}
					if(idRolActivo == '<%=Roles.ENCARGADOSALA.getValorId()%>'){
						top.visorAudienciaPJENS(audienciasid,numExpediente);
					}
					if(idRolActivo == '<%=Roles.JUEZPJ.getValorId()%>'){
						top.mostrarVisorAudienciaJuezPJJU(audienciasid);
					}
					if(idRolActivo == '<%=Roles.ATENCIONPUBLICO.getValorId()%>'){
						top.customAlert("Usted no tiene permisos para consultar el detalle de las audiencias.","");
					}
				}
			});
			configurarColumnasGridAudiencias();
		}
	
	/*
	* Funcion para configurar las columnas del gridAudiencias PJ 
	* en caso de que el rol sea Encargado de sala, Juez u Oficialia de Partes
	*/
	function configurarColumnasGridAudiencias(){
		//Se muestra la columna Estatus de la audiencia
		if(idRolActivo == '<%=Roles.ENCARGADOSALA.getValorId()%>' 
				|| idRolActivo == '<%=Roles.JUEZPJ.getValorId()%>'
				|| idRolActivo == '<%=Roles.ATENCIONPUBLICO.getValorId()%>'){
			//Se muestran columnas
			jQuery("#gridAudiencias").jqGrid('showCol',["estatusAudiencia"]);
		}else{
			jQuery("#gridAudiencias").jqGrid('hideCol',["estatusAudiencia"]);
		}
	}
	
</script>

<body>
	<table align="center" width="500" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td height="25" colspan="2" align="center">Audiencias del Expediente:&nbsp;
			<input type="text" id="numExpediente" style="width: 200px; border: 0; background: #DDD;" readonly="readonly" />&nbsp;&nbsp;</td>
	  </tr>
	
	  <tr>
	    <td height="25" colspan="2" align="center">&nbsp;</td>
	  </tr>
	  <tr>
	     <td align="center">
	       <table align="center" id="gridAudiencias"></table>
	       <div id="pagerGridAudiencias"></div>
		 </td>
	  </tr>
	</table>
</body>
</html>