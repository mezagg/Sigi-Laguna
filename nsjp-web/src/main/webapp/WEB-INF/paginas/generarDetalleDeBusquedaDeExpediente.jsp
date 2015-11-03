<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Buscar Expediente</title>
</head>

<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/estilos.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />	
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script type="text/javascript">


	var reloadGridDetalle = false;
	$(document).ready(function() {
		
		var idRow ='<%= request.getAttribute("idRow")%>';
		if(idRow!=null){
			llenaGridDetalle(idRow);
		}
		
		//cargaTitulo(idRow);	//Funcion que carga el titulo del detalle
						
});
	
	
		//Llena el grid del detalle
		
		  function llenaGridDetalle(idRow){

			   			 	
						
							if (reloadGridDetalle) {
								  jQuery("#tablaGenerarDetalle").jqGrid('setGridParam', {postData: {idRow: idRow}});
								  $("#tablaGenerarDetalle").trigger("reloadGrid"); 
							  } else {
								  reloadGridDetalle = true;
								  jQuery("#tablaGenerarDetalle").jqGrid(
											{ url:'<%= request.getContextPath() %>/generarDetalleExpediente.do', 						
												datatype: "xml", 
												mtype: 'POST',						
												postData: {idRow: function()     {return idRow;}
													
												},
												colNames:['Nombre','A. Paterno','A. Materno','Calidad','Nombre del Delito','Descripci&oacute;n del Delito'], 
												colModel:[ {name:'nombre',index:'nombre', width:150, sortable:false},
												           {name:'apaterno',index:'apaterno', width:150, sortable:false},
												           {name:'amaterno',index:'amaterno', width:150, sortable:false},
												           {name:'calidad',index:'calidad', width:150, sortable:false},
												           {name:'nombredelito',index:'nombredelito', width:300, sortable:false},
												           {name:'descdelito',index:'descdelito', width:300, sortable:false}

				      									], 
													autowidth: true, 
													pager: jQuery('#pager'), 
													sortname: 'id', 
													rownumbers: true,
													gridview: true, 
													viewrecords: true, 
													sortorder: "desc", 
													height: "60%",
													caption:"Resultado de la B&uacute;squeda" 
											}).navGrid('#pager',{edit:false,add:false,del:false});
							 			 }
							 
					   
				  }
							
		//Funcion que carga el titulo del detalle
			
	//		function cargaTitulo(idRow) {
	  //       $.ajax({
		//    	  type: 'POST',
		  //  	  url: '<%= request.getContextPath()%>/cargarTitulo.do',
		    //	  data: "idRow="+ idRow,
		    	//  async: false,
		//    	  dataType: 'xml',
		 //   	  success: function(xml){
		//	    	    $(xml).find('catTipoDelito').each(function(){
		//				$('#caso').val($(xml).find('calidadDTO').find('descripcionEstadoFisico').text());

		//				   });
		//    	  }
		//    	});
		//	}
		
	</script>


<body>

<table width="802" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td >
    	
    </td>
  </tr>
  <tr>
    <td height="10" colspan="6">&nbsp;
      
    </td>
    </tr>
  <tr>
    <td height="12" colspan="5" align="center">No. de Caso: <input type="text" id="caso" /></td>
    <td height="12" align="center">Fecha:
    <input type="text" id="fecha" /></td>
  </tr>
  <tr  >
    <td height="25" colspan="6" align="center">&nbsp;    </td>
    </tr>

</table>
<table id="tablaGenerarDetalle"></table>
<div id="pager"></div>

</body>
</html>