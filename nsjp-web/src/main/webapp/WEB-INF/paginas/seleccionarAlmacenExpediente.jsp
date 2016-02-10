<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Seleccionar almacen de expedientes</title>
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
	cargaGrid();
	$('#btnAsignar').hide();
});

var idAlmacen;
var descAlmacen;

function cargaGrid(){
	jQuery("#gridAlmacenes").jqGrid({ 
		url:'<%= request.getContextPath()%>/buscarAlmacenes.do', 
		datatype: "xml", 
		colNames:['Nombre','Domicilio','Descripci&oacute;n'], 
		colModel:[ 	{name:'NombreAlmacen',index:'nombreAlmacen', width:100},
		           	{name:'Domicilio',index:'domicilio', width:150},
		           	{name:'Descripcion',index:'descripcion', width:150}
				],
		pager: jQuery('#pagerAlmacenes'),
		rowNum:10,
		rowList:[10,20,30],
		autowidth: true,
		width:"100%",
		height:300,
		sortname: 'nombreAlmacen',
		viewrecords: true,
		sortorder: "desc",
		/*ondblClickRow: function(rowid) {
			seleccionarAlmacen(rowid);
		},*/
		onSelectRow: function(rowid){			
			seleccionarAlmacen(rowid);
		}
	})//.navGrid('#pagerAlmacenes',{edit:false,add:false,del:false});
}

function seleccionarAlmacen(id){
	idAlmacen = id;
	var grid = jQuery('#gridAlmacenes');
	var sel_id = grid.jqGrid('getGridParam', 'selrow');
	var myCellData_NombreAlmacen = grid.jqGrid('getCell',sel_id,'NombreAlmacen');
	var myCellData_Domicilio = grid.jqGrid('getCell',sel_id,'Domicilio');
	var myCellData_Descripcion = grid.jqGrid('getCell',sel_id,'Descripcion');
	descAlmacen = myCellData_NombreAlmacen + ", " + myCellData_Domicilio + ", " + myCellData_Descripcion;
	//descAlmacen = myCellData_NombreAlmacen;
	$('#btnAsignar').show();
}

function asignar(){
	window.parent.cargaAlmacen(idAlmacen, descAlmacen);	
}

function almacenTemporal(){
	alert("CU Registrar almac&eacute;n de expedientes");
}
</script>

<table width="650" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td height="25" colspan="2" align="left">
    		<input type="button" name="btnAsignar" value="Asignar" id="btnAsignar" onclick="asignar()" class="ui-button ui-corner-all ui-widget"/>
    		<input type="button" name="btnAlmacenTemporal" value="Almac&eacute;n Temporal" id="btnAlmacenTemporal" onclick="almacenTemporal()" class="ui-button ui-corner-all ui-widget"/>
    	</td>
    </tr>
</table>

<table id="gridAlmacenes"></table>
<div id="pagerAlmacenes"></div>

</body>
</html>