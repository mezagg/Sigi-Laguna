<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Configurar Complejidad de Audiencia</title>


<link rel="stylesheet" type="text/css"
	href="<%= request.getContextPath()%>/themes/1.8.10/${theme.name}/jquery-ui.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/resources/css/estilos.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/themes/1.8.10/${theme.name}/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="<%= request.getContextPath() %>/themes/1.8.10/${theme.name}/jquery-ui.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
<link type="text/css" rel="stylesheet"
	href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css" />

<script type="text/javascript"
	src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
<script type="text/javascript"
	src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>	
<script type="text/javascript">
						    
	$(document).ready(function() {

		jQuery("#gridTipoAudienciaPJENA").jqGrid({ 
			url:'<%=request.getContextPath()%>/consultarCatalogoTipoAudienciaGrid.do', 
			datatype: "xml", 
			colNames:['Tipo de Audiencia'], 
			colModel:[ 	{name:'tipoAudiencia',index:'tipoAudiencia', width:100, align:"center"} 
													
					],
			pager: jQuery('#pagTipoAudienciaPJENA'),
			rowNum:10,
			rowList:[10,20,30,40,50],
			autowidth: true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			onSelectRow: function(rowid) {
				abreModalComplejidad(rowid);
			}
			}).navGrid('#pagTipoAudienciaPJENA',{edit:false,add:false,del:false});		
				
	});
		   

		function abreModalComplejidad(rowid){
			 var complejidadAudiencia = consultaComplejidadAudiencia(rowid);
			 if (complejidadAudiencia != 0){
				 $("#nivelComplejidad").val(complejidadAudiencia);				 
			 }
			
			$("#divComplejidad").dialog("open");
		  	$("#divComplejidad").dialog({ autoOpen: true, 
				modal: true, 
			  	title: '<bean:message key="audiencia.configurar.complejidad.tituloModal"/>', 
			  	dialogClass: 'alert',
			  	position: [20,20],
			  	width: 350,
			  	height: 200,
			  	maxWidth: 1000,
			  	buttons:{"Guardar":function() {

			  		var complejidad = 'complejidad='+ $("#nivelComplejidad option:selected").val();
			  			complejidad += '&clave='+ rowid ;

			  		 $.ajax({
							type: 'POST',
							url: '<%= request.getContextPath()%>/guardaComplejidadTipoAudiencia.do',
							data: complejidad, 
							async: false,
							dataType: 'xml',
							success: function(xml){
				   								
							}
						});
						
			   		customAlert('<bean:message key="audiencia.configurar.complejidad.guardar"/>','<bean:message key="aviso"/>');
			   		$(this).dialog("close");
					
			  		},
			  		"Cancelar":function() {
			  			customConfirm('<bean:message key="audiencia.configurar.complejidad.cancelar"/>','<bean:message key="aviso"/>',function(){
			  				$("#divComplejidad").dialog("close");
			  			});
			  		}
			  	}
			});	  	
			
		}
		
		function consultaComplejidadAudiencia(tipoAudienciaId){
			var complejidad=0;
				$.ajax({
					type: "post",
					url:'<%=request.getContextPath()%>/consultaComplejidadTipoAudiencia.do',
					data: {
						tipoAudienciaId: tipoAudienciaId
					},
					dataType: "json",
					async: false,
					success: function( objJson ){
						var idCampo = objJson.idCampo;
						if (idCampo != 'undefined'
							&& idCampo != undefined
							&& idCampo != '0'){
							
							complejidad = objJson.valor;							
						}
					},
			
					error: function(){
						customAlert("Ocurri&oacute; un error al momento de consultar la complejidad de la audiencia.");
					}
				});
			return complejidad;
		}
	
	</script>
</head>
<body>
	<table width="650" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="4">
				<strong><bean:message key="audiencia.configurar.complejidad.leyenda"/></strong>
			</td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>
		<tr>
			<td width="25">&nbsp;</td>
			<td colspan="2">
				<table id="gridTipoAudienciaPJENA" cellspacing="0" cellpadding="0"></table>
				<div id="pagTipoAudienciaPJENA"></div>
			</td>
			<td width="25">&nbsp;</td>
		</tr>
		
	</table>



<div id="divComplejidad" style="display: none">
	<table width="300" cellspacing="0" cellpadding="0">
		<tr>
			<td width="45">&nbsp;</td>
			<td width="308">&nbsp;</td>
			<td width="45">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="3" align="justify">
				<strong><bean:message key="audiencia.configurar.complejidad.asignar"/></strong>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td align="center"><select id="nivelComplejidad">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
			</select></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>
</div>
</body>
</html>

