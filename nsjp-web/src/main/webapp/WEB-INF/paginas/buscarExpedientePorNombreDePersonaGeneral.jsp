<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
<script type="text/javascript">
var reloadGridNombre = false;
		//Llena el grid con los resultados de la busqueda, pasa como parametros el nombre y el apellido
		  function llenaGridNombre(){
			  
			  var nombre = $("#nombre").val();				//nombre
			  var apellido = $("#apellido").val();				//apellido

			  
			  
						if (validaNombre==true){
							if (reloadGridNombre) {
								  jQuery("#tablaBuscarExpedientePorNombreDePersona").jqGrid('setGridParam', {postData:{nombre: nombre,apellido: apellido}});
								  $("#tablaBuscarExpedientePorNombreDePersona").trigger("reloadGrid"); 
							  } else {
								  reloadGridNombre = true;
								  jQuery("#tablaBuscarExpedientePorNombreDePersona").jqGrid(
											{ url:'<%= request.getContextPath() %>/buscarExpedientePorNombreDePersonaGeneral.do', 						
												datatype: "xml", 
												mtype: 'POST',						
												postData: {
													nombre: function()     { return nombre; },
													apellido: function()     { return apellido; }													
													
												},
												colNames:['Expediente','Nombre','A. Paterno','A. Materno','Calidad'], 
												colModel:[ {name:'expediente',index:'expediente', width:150, sortable:false}, 
															{name:'nombre',index:'nombre', width:150, sortable:false}, 
															{name:'apaterno',index:'apaterno', width:150, sortable:false}, 
															{name:'amaterno',index:'amaterno', width:150, sortable:false},
															{name:'calidad',index:'calidad', width:150, sortable:false} 
															], 
													autowidth: true, 
													pager: jQuery('#pager'), 
													sortname: 'id', 
													rownumbers: true,
													gridview: true, 
													viewrecords: true, 
													sortorder: "desc", 
													height: "60%",
													ondblClickRow: function(id) {

														seleccionarNumeroExpediente(id.split(",")[0],id.split(",")[1]);
														
													},
													caption:"Resultado de la B&uacute;squeda" 
											});
							 			 }
							}			  
					   
				  }
		
		//Funcion que valida si los campos estan llenos al enviar 
		function validaCamposNombre(){

			if ($('#nombre').val()==''&& $('#apellido').val()==''){
					customAlert("Favor de ingresar un Nombre o un Apellido");
					validaNombre=false;
				}
			else { 

				validaNombre=true;
				
				}
	}

		

		
	
</script>

<table width="650" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td >
    </td>
  </tr>
  <tr>
    <td height="10">&nbsp;
      
    </td>
    </tr>
  <tr>
    <td height="12" align="center">Nombre(s):</td>
    </tr>
  <tr>
    <td height="13" align="center"><input type="text" name="nombre" id="nombre" size="40" maxlength="50" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/></td>
    </tr>
  <tr>
    <td height="25" align="center">Apellido(s):</td>
    </tr>
  <tr id="nombreApellido">
    <td height="25" align="center">&nbsp;
      <input type="text" name="apellido" id="apellido" size="40" maxlength="50" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/>      &nbsp;</td>
    </tr>

  <tr id="espacios" >
    <td height="25" align="center">* S�lo se permiten letras y espacios</td>
    </tr>

  <tr >
    <td height="25" align="center">&nbsp;</td>
    </tr>

	<tr >
    <td height="25" align="center"><input type="button" name="buscarNombre" value="Buscar" id="buscarNombre" class="btn_Generico"/></td>
    </tr>
	<tr>
     <td valign="middle" align="center"><table id="tablaBuscarExpedientePorNombreDePersona"></table>
	 </td>
   </tr>
</table>

<script type="text/javascript">
$("#buscarNombre").bind("click",validaCamposNombre);
$("#buscarNombre").bind("click",llenaGridNombre);
</script>