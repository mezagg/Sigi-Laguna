<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros"%>
<%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn" 
       uri="http://java.sun.com/jsp/jstl/functions" %>


<html>
	<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" /-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/south-street/jquery-ui.css" />
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
		
	<script type="text/javascript">
	var contextoPagina = "${pageContext.request.contextPath}";
	
	jQuery().ready(function () {
                            
		$("#username").focus();
                <c:choose >
                    <c:when test="${error==0}">
                        customAlert("Usuario y/o Contrase&ntilde;a Inv&aacute;lidos,<br/> favor de verificar.", 
						"Error", 
						function() { $("#username").focus(); }
			);
			$('#errorLogin').val('Credenciales Invalidas');
                    </c:when>
                    <c:when test="${error==2}">
                        customAlert("Cuenta Bloqueada, <br/>favor de verificar con el administrador del sistema.", 
						"Error",
						function() { $("#username").focus(); }
			);
			$('#errorLogin').val('Credenciales Invalidas');
                    </c:when>
                    <c:when test="${error==3}">
                        customAlert("C&oacute;digo Captcha err&oacute;neo,<br/>  favor de verificar.", 
						"Error",
						function() { $("#password").focus(); } 
			);
			$('#errorLogin').val('Credenciales Invalidas');
                    </c:when>
                    <c:otherwise>
                        $('#errorLogin').val('');
                    </c:otherwise>
                </c:choose>
		var error='<%= request.getAttribute("error")%>'
		var captcha='<%= request.getAttribute("captcha")%>'
		
		<c:choose >
                    <c:when test="${captcha==0}">
                        $("#captchaJPG").hide();
			$("#captchaTXT").hide();
                    </c:when>
                    <c:when test="${captcha==1}">
                        customAlert("Existe una sesi&oacute;n iniciada en sistema,<br/> favor de volver a ingresar su informaci&oacute;n de usuario.",
								"Error", 
                                                function() { $("#password").focus(); }
                        );
                        $('#errorLogin').val('Sesi&oacute;n Duplicada');
                        $("#captchaJPG").show();
                        $("#captchaTXT").show();
                    </c:when>
                    <c:when test="${captcha==2}">
                        customAlert("C&oacute;digo Captcha err&oacute;neo,<br/> favor de verificar.",
								"Error",
                        function() { $("#password").focus(); }
                        );
                        $('#errorLogin').val('C&oacute;digo Captcha Err&oacute;neo');
                        $("#captchaJPG").show();
                        $("#captchaTXT").show();
                    </c:when>
                </c:choose>
		
		<%
			if (request.getAttribute("nombreUsuario") != null){
		%>
				$("#username").val('<%=(String)request.getAttribute("nombreUsuario")%>');
		<%				
			}
		 %>
		
		
		$("#login").submit(
			function(){
				var error = "";
				var sinError = true; 
				if( $("#username").val().length == 0 ) {
					error += "El campo <b>Usuario</b> es requerido<br/>";
					sinError = false;
        		}
				if( $("#password").val().length == 0 ) {
					error += "El campo <b>Contrase&ntilde;a</b> es requerido<br/>";
					sinError = false;
        		}
        		if(!sinError){
        			error+="Por favor, verifique que los campos esten completos.";
        			customAlert(error, "Validaci&oacute;n De Datos");
        		}
        		return sinError;
			}
		);
		
		var ambiente='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAmbiente()%>';
		if(ambiente == undefined || ambiente == "undefined"){
			ambiente = "";
		}
		$("#ambienteLbH").html('<strong><big>'+ambiente+'</big></strong>');
		$("#ambienteLb").html('<strong><big>'+ambiente+'</big></strong>');
	});

	function ocultarCaptcha(){
		$("#captchaJPG").hide();
		$("#captchaTXT").hide();
		$("#captcha").val(0);
		
		
		
	}
	
		
	</script>
		<title>Nuevo Sistema de Justicia Penal</title>
	</head>
	<body>
	  <center>
		<form method="post" action="<%= request.getContextPath() %>/Login.do" id="login">
	   	<table width="100%" height="100%" border="0" cellspacing="3" cellpadding="0" background="<%= request.getContextPath() %>/images/sistema/back_grallogin.jpg" align="center">
	      <tr height="108px">
	        <td colspan="3">
	        	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" background="<%=request.getContextPath()%>/resources/images/top_gral_sin_version.jpg">
	                <tr>
	                    
					<td width="150" align="center"><img src="<%= request.getContextPath() %>/images/sistema/logo_login.png" alt="Logo sistema de justicia" /></td>     
	                    <td width="150" align="left">
	                    	<div>&nbsp;</div>
	                    	<div>&nbsp;</div>
	                    	<div class='versionCodigo'>${nsjp.version.codigo}</div>	                    
	                    </td>
	                    <td width="150" align="center"></td>
	                    <td width="296" align="center">&nbsp;</td>
	                    <td width="150" align="center"></td>
	                    <td width="180" >
	                        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
	                            <tr>
	                                <td width="168" height="40" colspan="2" align="center">
	                                    <table align="center" cellpadding="2" cellspacing="2">
	                                        <tr>
	                                            <td>&nbsp;</td>
	                                            <td>&nbsp;</td>
	                                            <td>&nbsp;</td>
	                                        </tr>
	                                    </table>
	                                </td>
	                            </tr>
	                            <tr valign="top">
	                                <td  width="128" align="center">&nbsp;</td>
	                                <td width="40">&nbsp;</td>
	                            </tr>
	                        </table>            
	                    </td>
	                </tr>
	                <tr>
	                <td colspan="6"> 
			                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="toolbar back_pleca_h">
						    <tr>
						    	<!--  <td height="15">&nbsp;</td> -->
						    	<td align="center" style="border-left:#FFFFFF; border-top:#FFFFFF;">
	                    			<label id="ambienteLbH" style="color:#FBFBEF"></label>	
	                    		</td>	
						  </tr>
						</table>
					</td>
	                </tr>
	            </table>
	            
	        </td>
	      </tr>
	    
	      <tr>
	        <td>&nbsp;</td>
	        <td valign="top">
	       
	        <table width="537" height="228" border="0" cellspacing="0" cellpadding="0" background="<%= request.getContextPath() %>/images/sistema/back_login.png" align="center" >
	                <tr>
	                    <td align="center" style="border-left:#FFFFFF; border-top:#FFFFFF;">
	                    	<label style="color:#4A5C68; font-weight: bolder">Escriba su usuario y contrase&ntilde;a para iniciar.</label>	
	                    </td>
	                </tr>
	                <tr>
	                    <td valign="top" align="center" style="border-left:#FFFFFF; border-top:#FFFFFF; border-right-width:3; border-bottom-width:3;">
	                        <table align="center" border="0">
	                            <tr>
	                                <td colspan="2" align="right">
	                                <div id="errorLogin"   style=" color:FF0000 ;" >
	                                
	                                </div>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td align="right"><label style="color:#4A5C68">Usuario:</label></td><td><input type="text" name="username" id="username" value="" maxlength="30" size="20"></td>
	                            </tr>
	                            <tr>
	                                <td align="right"><label style="color:#4A5C68">Contrase&ntilde;a:</label></td><td><input type="password" name="password" id="password" value="" maxlength="20" size="20"></td>
	                            </tr>
	                            <tr id="captchaJPG" style="display:none;">
	                                <td align="right">
	                                	<label style="color:#4A5C68">Captcha:</label>
	                                </td>
	                                <td>
	                                  	<img src="<%=request.getContextPath()%>/kaptcha.jpg">
	                                </td>
	                               
	                            </tr>
	                             <tr id="captchaTXT" style="display:none;">
	                                <td align="right">
	                                	<label style="color:#4A5C68">Captcha:</label>
	                                </td>
	                                <td>
	                                	<input type="text" name="scaptcha" id="scaptcha" value="" maxlength="15" size="20">
	                                	<input type="hidden" name="captcha" id="captcha" value='<%= request.getAttribute("captcha")%>'>
	                                </td>
	                            </tr>
                                    <tr>
	                                <td align="center" colspan="2"> &nbsp;
                                        </td>
                                    </tr>
	                            <tr>
	                                <td align="center" colspan="2">
	                                    <input value="Entrar" name="login" id="login" type="submit" class="ui-button ui-corner-all ui-widget"/> 
	                                    <input value="Cancelar" name="cancelar" id="cancelar" type="reset"  class="ui-button ui-corner-all ui-widget" onclick="ocultarCaptcha();"/>
	                                </td>			
	                            </tr>
	                        </table>
	                    </td>
	                    
	                </tr>
	        </table>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td colspan="3">
	        	
	        	<div>
	        	
	                <div id="footer" style="width: 100%;  padding: 5px;" >
	                
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pleca_bottom">
						    <tr>
						    <!-- <td height="15">&nbsp;</td>  -->
						    <td height="15" align="center" style="border-left:#FFFFFF; border-top:#FFFFFF;">
	                    			<label id="ambienteLb" style="color:#FBFBEF"></label>	
	                    	</td>
						  </tr>
						</table>
	                </div> 
	                <div align="center">
	                    Direcci&oacute;n de la Instituci&oacute;n
	                </div> 
	            </div>
	        </td>
	      </tr>
	    </table>	
		</form>
	  </center>
	</body>
</html>