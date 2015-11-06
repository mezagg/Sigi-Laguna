<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO"%>

<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>


<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />

<div>
	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100" height="100" align="center">			
				<img width="100" height="100" id="imgConFoto" src="<%=request.getContextPath()%>/resources/images/mono.png" />
			</td>
	    </tr>
	    <tr>
			<td height="10%" align="center">
				<strong>Usuario:</strong>
			</td>
	    </tr>
	    <tr>
			<td height="10%" align="center">
                            <span id="txtNombreUsuario" ></span>	
			</td>
	    </tr>
	    <tr>
			<td height="10%" align="center">
				<strong>&Aacute;rea:</strong>
			</td>
	    </tr>
	    <tr>
			<td height="10%">
                            <span id="txtAreaUsuario" ></span>		
			</td>
	    </tr>
	     <tr id="trTituloAgencia">
			<td id="tdTituloAgencia" height="10%" align="center">
				<strong><bean:message key="agencia"/>:</strong>
			</td>
	    </tr>
	    <tr id="trContenidoAgencia">
			<td height="10%">
                            <span id="txtAgenciaUsuario" ></span>	
                           </td>
	    </tr>
           
	    <tr>
                <td height="10" align="center">&nbsp;</td>
             </tr>    
	    <tr>
	    	<td height="10%" align="center">
				<a href="javascript:abreVentanaModificarContrasena();">Cambiar Contrase&ntilde;a</a>
			</td>
	    </tr>
	</table>
</div>

<script type="text/javascript">
	<%UsuarioDTO usuario = (UsuarioDTO) request.getSession()
					.getAttribute("KEY_SESSION_USUARIO_FIRMADO");
			FuncionarioDTO funcionario = usuario.getFuncionario();

			String areaNegFuncionario = "";

			if (funcionario != null
					&& funcionario.getCatAreaNegocio() != null
					&& funcionario.getCatAreaNegocio()
							.getNombreCatAreaNegocio() != null) {

				areaNegFuncionario = funcionario.getCatAreaNegocio()
						.getNombreCatAreaNegocio();
			}
	%>
	
	var contextPath = '<%=request.getContextPath()%>';
	var claveFuncionario = "<%=funcionario.getClaveFuncionario()%>";
	var nombreFuncionario = "<%=funcionario.getNombreFuncionario()%>";
	var apaterFuncionario = "<%=funcionario.getApellidoPaternoFuncionario()%>";
	var amaterFuncionario = "<%=funcionario.getApellidoMaternoFuncionario()%>";
	var areaFuncionario = '<%=areaNegFuncionario%>';
	 
	
	imprimeDatosFuncionario();
	/**
	*Imprime los datos del funcionario en pantalla
	*/
	function imprimeDatosFuncionario()
	{

		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/tieneImagenFuncionario.do?idFuncionario='+claveFuncionario+'',						
			dataType: 'xml',
			async: false,
			success: function(xml){
				var respuesta=$(xml).find('respuesta').text();						
				if(respuesta=='1' || respuesta==1){
					$("#imgConFoto").attr("src",'<%=request.getContextPath()%>/obtenImagenDeFuncionario.do?idFuncionario='+claveFuncionario+'');
				}
				else{
					$("#imgConFoto").attr("src","<%=request.getContextPath()%>/resources/images/mono.png");
				}
			}
		});
		$('#txtNombreUsuario').append(nombreFuncionario+" "+apaterFuncionario+" "+amaterFuncionario);
		$('#txtAreaUsuario').append(areaFuncionario);
		$.ajax({
		     type: 'POST',
		     url: '<%=request.getContextPath()%>/obtenerNombreAgencia.do',
			 dataType: 'xml',
			 async: false,
			 success: function(xml){
				 if(parseInt($(xml).find('idInstitucion').text())>0)
				 {
					 
					 if(parseInt($(xml).find('idInstitucion').text())==1 || parseInt($(xml).find('idInstitucion').text())==3)
					 {
					   // $('#txtAgenciaUsuario').val($(xml).find('nombreAgencia').text());
                                           $('#txtAgenciaUsuario').append($(xml).find('nombreAgencia').text());
					 }
					 else
					 {
						 $('#trTituloAgencia').hide();
						 $('#trContenidoAgencia').hide();
						 $('#tdTituloAgencia').hide();
					 }
				 }
				 else
				 {
					 $("#trTituloAgencia,#trContenidoAgencia").hide();
				 }
			  }
		});
	}
	
	function abreVentanaModificarContrasena(){
		//window.parent.abreVentanaModificarContrasena();
		$.newWindow({id:"iframewindowModificarPwdUsuario", statusBar: true, posx:400,posy:90,width:350,height:280,title:"Modificar Contrase&ntilde;a", type:"iframe"});
	    $.updateWindowContent("iframewindowModificarPwdUsuario",'<iframe src="<%=request.getContextPath()%>/cambiarContrasena.do?" width="350" height="280" />');
	}
	function cerrarVentanaModificarContrasena(){
		$.closeWindow('iframewindowModificarPwdUsuario');
	}
	
	
</script>
