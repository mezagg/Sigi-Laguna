
<%@page pageEncoding="UTF-8"%>

	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript">

	var idRol;
	var idUsuario;
	var modifica=0;
	var activaModifica=true;
	var idsRolesSeleccionados="";
	var firstGridRol=true;
	$(document).ready(function() {
		gridRolesUsuario();
		cargaComboFuncionarioRegistrado();
		gridRolesSeleccionados();
		$('#funcionarioRegistrado').change(llenaUsuario);
	});	

	//valida campos para habilitar el boton de registro
	function habilitaBoton(){
		 if($("#nombreUsuario").val().length >= 1 && $("#contrasena").val().length >= 1 && $("#repetirContrasena").val().length >= 1) {  
			$('#registrarUsuario').removeAttr('disabled');
		 }		 
	}

	//valida campos para hacer el registro del usuario del sistema
	function validaCampos(){
	
		var params;
		
		/* administrador=2 --> el indexAdministradorView lo invoca para: "Modificar Usuario"       */
		/* administrador=1 --> el indexAdministradorView lo invoca para: "Ingresar Usuario"       */				
		var roles=obtenerRolesSeleccionados();
				
		if(parseInt(administrador)==2){
			if(roles==null || roles=="null" || roles==""){
				roles=obtenerRolesSeleccionados();	
			}
		}
		var idRolPrincipal=$('input[name=gridRolPrincipal]:checked').attr('id');
		
		// Se valida que el Nombre de usuario haya sido capturado
		if($('#nombreUsuario').val()==null || $('#nombreUsuario').val()=="null" || $('#nombreUsuario').val()==""){
			alertDinamico("Se necesita proporcionar el nombre de usuario");		 		 		
	 	}
		else if(roles==null || roles=="null" || roles==""){
			alertDinamico("Se necesita seleccionar un rol de la lista");
		}
		else if(idRolPrincipal === undefined){
			alertDinamico("Se necesita seleccionar un rol principal");
		}
		else if($("#contrasena").val()==""){
			alertDinamico("Favor de capturar la contrase&ntilde;a del usuario");
		}
		else if($("#repetirContrasena").val()==""){
			alertDinamico("Favor de confirmar la contrase&ntilde;a del usuario");
		}	 			
		else if($("#contrasena").val()!= $("#repetirContrasena").val()){
			alertDinamico("La contrase&ntilde;a debe ser la misma en los dos campos, favor de verificar");  
	    }
		else if(isContrasenia($('#contrasena').val())){

			params = 'nombreUsuario=' +$('#nombreUsuario').val();
	 		params += '&contrasena=' +escape($('#contrasena').val());
	 		params += '&roles=' +roles;
 		    params += '&idRolPrincipal=' +idRolPrincipal;
	 		params += '&idFuncionario='+idFuncionario;

			if(modifica==0){				
				
				$.ajax({
					type: 'POST',
					url: '<%= request.getContextPath()%>/darDeAltaUsuarioEnSistema.do',
					async: false,
					data: params,
					dataType: 'xml',
					success: function(xml){						
						idUsuario=$(xml).find("long").text();
						if(idUsuario!=0){
							alertDinamico("El registro del usuario se ha llevado con &eacute;xito");
							$('#modificarUsuario').removeAttr('disabled');
							$('#anularUsuario').removeAttr('disabled');						 				 			
							$('#registrarUsuario,#nombreUsuario,#contrasena,#repetirContrasena,#rolSelection').attr('disabled', 'disabled');										 		
							modifica=idUsuario;						 				    		
							activaModifica=false;
						}
						else{
							alertDinamico("El nombre de usuario proporcionado ya esta asignado a otro funcionario,\n favor de ingresar otro");
						}
					}	
				});	
			}
			else{
				
				params += '&idUsuario=' + idUsuario;

				$.ajax({
					type: 'POST',
					url: '<%= request.getContextPath()%>/modificarUsuarioEnSistema.do',
					async: false,
					data: params,
					dataType: 'xml',
					success: function(xml){
						modificaC=$(xml).find("boolean").text();
						if(modificaC=="true"){
		 					alertDinamico("La modificaci&oacute;n del usuario se ha llevado con &eacute;xito.");
						}
						else{
							alertDinamico("El nombre de usuario proporcionado ya esta asignado a otro funcionario,\n favor de ingresar otro");
						}
					}						
		 		});		 			

			}
		}
	}
		
//funcion validadora de contrase&ntilde;a. Regresa verdadero si cumple con requisitos o falso si no cumple
function isContrasenia(theElement){
	var s = theElement;
	var resp = false;
	var mPsw1=/^[A-Za-z0-9!@#$%^&*()_]{5,20}$/;
	//Valida que tenga letras
	var mPsw2=/[A-Za-z]/;
	//Valida que tenga n&uacute;meros
	var mPsw3=/[0-9]/;
	
	if (mPsw1.test(s))
	{
		if (mPsw2.test(s))
		{
			if (mPsw3.test(s))
			{
				resp=true;
			}
			else
			{
				alertDinamico("La contrase&ntilde;a debe tener al menos un n&uacute;mero, favor de verificar");
			}
		}
		else
		{
			alertDinamico("La contrase&ntilde;a debe tener al menos una letra, favor de verificar");
		}
	}
	else
	{
		alertDinamico("La contrase&ntilde;a debe tener como m&iacute;nimo 5 y m&aacute;ximo 20 caracteres,\n favor de verificar");
	}
	
	return resp;
}
	
//grid que consulta los roles del sistema
function gridRolesUsuario() {
	obtenerRolesElegidos();
	if(firstGridRol){
			jQuery("#rolesUsuario").jqGrid({
						url : '<%= request.getContextPath()%>/consultarRolesDelSistema.do?idRolesSeleccionados='+idsRolesSeleccionados, 
						datatype: "xml", 
						colNames:['Roles','Rol Principal','RowId','RolPadre_id'], 
						colModel:[ 	{name:'roles',index:'1', width:150}, 
									{name:'Principal',index:'2',width:50,align:'center',hidden:true},
									{name:'RowId',index:'3',width:50,hidden: true,align:'center'},
									{name:'RolPadreId',index:'3',width:50,hidden: true,align:'center'}
						],
						pager: jQuery('#pagerRolesUsuario'),
						rowNum:10,
						rowList:[10,20,30],
						width: 400,
						caption:"LISTA DE ROLES",
						autoheight:true,
						height:270,
						sortname: 'nombreRol',
						viewrecords: true,
						sortorder: "desc"
			}).navGrid('#pagerRolesUsuario',{edit:false,add:false,del:false},
			{}, // edit parameters 
			{}, // add parameters
			{reloadAfterSubmit:false} //delete parameters 
			);
			firstGridRol=false;
	}else{
			jQuery("#rolesUsuario").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarRolesDelSistema.do?idRolesSeleccionados='+idsRolesSeleccionados,datatype: "xml"});
			$("#rolesUsuario").trigger("reloadGrid",[{page:1}]);		
	}
}


	//Funcion que carga el combo con los funcionarios ya registrados
	function cargaComboFuncionarioRegistrado() {
     $.ajax({
    	  type: 'POST',
    	  url: '<%= request.getContextPath()%>/cargarFuncionarioRegistrado.do',
    	  data: '',
    	  async: false,
    	  dataType: 'xml',
    	  success: function(xml){
	    	  $('#funcionarioRegistrado').empty();
	    	  $('#funcionarioRegistrado').append('<option value="-1">- Seleccione -</option>');
	    	  $(xml).find('funcionariosRegistrados').each(function(){
				$('#funcionarioRegistrado').append('<option value="' + $(this).find('claveFuncionario').first().text() + '">' + $(this).find('nombreFuncionario').first().text() +" " +  $(this).find('apellidoPaternoFuncionario').first().text() +" " +  $(this).find('apellidoMaternoFuncionario').first().text() + '</option>');				
			  });
    	  }
    	});
	}

	function llenaUsuario(){
		
		activaModifica=false;

		$('#registrarUsuario,#nombreUsuario,#contrasena,#repetirContrasena,#rolSelection').attr('disabled','disabled');
	
  	    idFuncionario = $('#funcionarioRegistrado').val();
		  
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarUsuarioPorClaveFuncionario.do?funcionarioId='+idFuncionario+'',
			async: false,
			dataType: 'xml',
			success: function(xml){
				
			    idRol =$(xml).find('usuarioDTO').find('usuarioRoles').find('rolId').first().text();				

				idUsuario = $(xml).find('usuarioDTO').find('idUsuario').text();
				$('#nombreUsuario').val($(xml).find('usuarioDTO').find('claveUsuario').text());
				$('#contrasena').val($(xml).find('usuarioDTO').find('password').text());
				$('#repetirContrasena').val($(xml).find('usuarioDTO').find('password').text());
				$('#modificarUsuario').removeAttr('disabled');
				$('#anularUsuario').removeAttr('disabled');

				modifica=idFuncionario;
				var rol =$(xml).find('usuarioDTO').find('usuarioRoles').find('nombreRol').first().text();
				 				 
				$("#rolSelection").val(rol);		
					    					    			
			}
		});
		obtieneRolesFuncionario(idFuncionario);
		gridRolesUsuario();
	}


	function obtieneRolesFuncionario(idFuncionario){
		jQuery("#gridRolesSeleccionados").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarRolesUsuarioPorClaveFuncionario.do?funcionarioId='+idFuncionario,datatype: "xml"});
		$("#gridRolesSeleccionados").trigger("reloadGrid");
	}

	function obtenerRolesSeleccionados(){
		
		var arrayIDs = new Array() ;			
		var arrayIDs = jQuery("#gridRolesSeleccionados").getDataIDs();
		var arrayRolesSeleccionados="";			
		for (i=0;i<arrayIDs.length;i++)
		{								
			var row = jQuery("#gridRolesSeleccionados").jqGrid('getRowData',arrayIDs[i]);
			
			if(arrayRolesSeleccionados.length>0)
			{					
				arrayRolesSeleccionados = arrayRolesSeleccionados + "," + row.RowId;
			}
			else
			{
				arrayRolesSeleccionados = row.RowId;
			}								
		}
		return arrayRolesSeleccionados;
	}
	
	function desbloquea(){
		$('#modificarUsuario').attr('disabled', 'disabled');   
		
		$('#registrarUsuario,#nombreUsuario,#contrasena,#repetirContrasena,#rolSelection').removeAttr('disabled');
		activaModifica=true;
	}
	
	function buscarUsuario() {		
		if(confirm("&iquest;Est\u00E1 seguro que quiere anular al usuario?")) {
			var anulaUsu;			
			$('#anularUsuario').attr('disabled', true);
			var params = '&idUsuario=' + idUsuario;	
			$.ajax ({
				type: 'POST',
				url: '<%= request.getContextPath()%>/buscarUsuarioEnSesion.do',
				async: false,
				data: params,
				dataType: 'xml',
				success: function(xml) {
					anulaUsu=$(xml).find("boolean").text();					
					if (anulaUsu=="true") {						
						customConfirm("El usuario tiene una sesion activa, &iquest;Deseas cerrarla?","Sesion Activa",anulaUsuario,function (){alertDinamico("El usuario no se anulo");});
					}else{
						anulaUsuario();
					}						
				}
			});
		}
	}
	
	function anulaUsuario() {	
		var anulaUsu;			
		$('#anularUsuario').attr('disabled', true);
		var params = '&idUsuario=' + idUsuario;	
		$.ajax ({
			type: 'POST',
			url: '<%= request.getContextPath()%>/anularUsuario.do',
			async: false,
			data: params,
			dataType: 'xml',
			success: function(xml) {
				anulaUsu=$(xml).find("boolean").text();					
				if (anulaUsu=="true") {						
					$('#nombreUsuario').val("");
					$('#rolSelection').val("");
    				$('#contrasena').val("");
					$('#repetirContrasena').val("");					
					gridRolesUsuario();
					cargaComboFuncionarioRegistrado();
					alertDinamico("Se anul&oacute; correctamente al usuario");
				}						
				else{
					alertDinamico("El usuario tiene expedientes abiertos");
				}
			}
		});
		
	}
	function revisaLetras(e){
		tecla = e.charCode ? e.charCode : e.keyCode;
	    // 'Blankspace' = 32
	    if (tecla == 32){ 	
			alertDinamico("No se permiten espacios");
			var texto = $('#nombreUsuario').val();
			$('#nombreUsuario').val(trim(texto));	
		}
		habilitaBoton();
	}
	function gridRolesSeleccionados(){
		jQuery("#gridRolesSeleccionados").jqGrid({ 
			url : '', 
			datatype: "xml",
			ajaxGridOptions : {
                   async:false
            },
            colNames:['Roles','Rol Principal','RowId','RolPadre_id'], 
			colModel:[ 	{name:'roles',index:'1', width:200}, 
						{name:'Principal',index:'2',width:100,align:'center'},
						{name:'RowId',index:'3',width:50,hidden: true,align:'center'},
						{name:'RolPadreId',index:'3',width:50,hidden: true,align:'center'}
					],
			rowNum:30,
			rowList:[10,20,30,40,50,60],
			caption:"LISTA DE ROLES SELECCIONADOS",
			sortname: 'Clave',
			viewrecords: true,
			sortorder: "desc"
		});
	}
	
	/*
		M&eacute;todo que valida que no se puedan seleccionar roles y subroles de la misma familia
	*/
	function validarRoles(rolPadreId){
	
		var ids = jQuery("#gridRolesSeleccionados").getDataIDs();
		for (var i = 0; i < ids.length; i++) {
	   		var seleccionado = jQuery("#gridRolesSeleccionados").getRowData(ids[i]);
    		if(rolPadreId == seleccionado.RolPadreId){
				customAlert("No se puede seleccionar un rol de la misma jerarqu&iacute;a", "Asignaci&oacute;n De Roles");
				return false;    		
    		}
		}
		return true;
	}
	
	function addRowRigthToLeft(){
		
		var row = jQuery("#rolesUsuario").jqGrid('getGridParam','selrow');
		
		if (row) { 
			var ret = jQuery("#rolesUsuario").jqGrid('getRowData',row);
			if(validarRoles(ret.RolPadreId)){
				jQuery("#gridRolesSeleccionados").jqGrid('addRowData',ret.RowId,ret);
				jQuery("#rolesUsuario").jqGrid('delRowData',ret.RowId);
			} 
		} else { 
			alertDinamico("Por favor seleccione un renglon");
		}
	} 

	function addRowLeftToRigth(){
		
		var rowd = jQuery("#gridRolesSeleccionados").jqGrid('getGridParam','selrow');
		if (rowd) { 
			obtenerRolesElegidos();
			var retd = jQuery("#gridRolesSeleccionados").jqGrid('getRowData',rowd);
			jQuery("#rolesUsuario").jqGrid('addRowData',retd.RowId,retd);
			jQuery("#gridRolesSeleccionados").jqGrid('delRowData',retd.RowId);
			jQuery("#gridRolesSeleccionados").jqGrid(idsRolesSeleccionados,gridRolesUsuario());
		} else { 
			customAlert("Por favor seleccione un renglon");
		}
	}
	
	function obtenerRolesElegidos(){
		var arrayIDs = new Array() ;			
		var arrayIDs = jQuery("#gridRolesSeleccionados").getDataIDs();
		var arregloRolSeleccionado="";
		for (i=0;i<arrayIDs.length;i++)
		{								
			var row = jQuery("#gridRolesSeleccionados").jqGrid('getRowData',arrayIDs[i]);
			
			if(arregloRolSeleccionado.length>0)
			{					
				arregloRolSeleccionado = arregloRolSeleccionado + "," + row.RowId;
			}
			else
			{
				arregloRolSeleccionado = row.RowId;
			}								
		} 					
		idsRolesSeleccionados = arregloRolSeleccionado;
	}
	</script>	

<body>
<!-- div para el alert dinamico -->
<div id="dialog-Alert" style="display: none">
	<table align="center">
		<tr>
        	<td align="center">
            	<span id="divAlertTexto"></span>
            </td>
        </tr>
     </table>              
</div> 
<table width="650" cellspacing="0" cellpadding="0" align="center" border="0">
  <tr>
    <td colspan="6" align="center" id="etiquetaFuncionario"><strong>Funcionario:</strong>      <select name="funcionarioRegistrado" id="funcionarioRegistrado" style="width: 250px;" >
    	</select>
    </td>
  </tr>
  <tr>
   	<td align="right"><strong>Nombre de usuario: </strong></td>
    <td align="left">
     	<input type="text" style="width: 75px;" id="nombreUsuario" onKeyUp="revisaLetras(event)" maxlength="30"/>
    </td>
    <td align="right"><strong>Contrase&ntilde;a:</strong></td>
    <td align="left"><strong>
    	<input type="password" id="contrasena" onKeyUp="habilitaBoton()" maxlength="20"/>
    	</strong>
    </td>
    <td align="right"><strong>Repetir contrase&ntilde;a: </strong></td>
	<td align="left"><strong>
		<input type="password" id="repetirContrasena" onKeyUp="habilitaBoton()" maxlength="20"/>
		</strong>
	</td>
  </tr>
  <tr>
   <td valign="top" rowspan="5" colspan="2">
    	<table id="rolesUsuario" width="450px"></table>
    	<div id="pagerRolesUsuario"></div>
    </td>   
    <td>
	    <input type="button" id="pasar" value=">>" onclick="addRowRigthToLeft();" class="btn_Generico"> <br/>
		<input type="button" id="pasarD" value="<<" onclick="addRowLeftToRigth();" class="btn_Generico">
    </td>
    <td valign="top" colspan="2">
		<table id="gridRolesSeleccionados" width="450px"></table>
		<div id="pgrid2"></div>
    </td>

   
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3" align="right" id="etiquetaFuncionario">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3" align="center">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
   <tr>
    <td colspan="3">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="6" align="center">
    	<input type="button" value="Registrar usuario" id="registrarUsuario" disabled="disabled" class="btn_Generico" onclick="validaCampos()" />
    	<input type="button" value="Modificar usuario" id=modificarUsuario disabled="disabled"  class="btn_Generico" onclick="desbloquea()"/>
    	<input type="button" value="Anular usuario" id="anularUsuario" disabled="disabled"  class="btn_Generico" onclick="buscarUsuario()"/>
    </td>
  </tr>
</table>
</body>
