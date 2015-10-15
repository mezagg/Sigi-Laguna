<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registrar Denuncia Externa</title>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>	
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<!-- JS para la validacion de solo numeros -->
	<script src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript">
	
	var idWindowNuevaDenuncia=1;
	var success='<%= request.getParameter("success")%>';
    
    $(document).ready(function() {
    	if(success != "null"){
    		window.parent.cierraVentanaAdjuntarDenuncia();
    	}         	
    });
	
	function nuevaDenunciaUI() {
		var idExpediente;
        var numeroExpediente;
        var numeroExpedienteId;
        var numeroCasoNuevo;
        var idNuevaDenuncia = 1;
      //variable que indica si es un ingreso o una consulta
        var ingresoDenuncia = false;
      var incompetencia = true;
      
        if(validadDatosObligatorios()){
        	
	       	$.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/nuevoExpedienteUI.do?activarIncompetencia='+incompetencia+'',
	    		data: '',
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){
	    			var option;
	    			idExpediente=$(xml).find('expedienteDTO').find('expedienteId').text();
                                console.log('EXPEDIENTE:' + idExpediente);
	    			numeroExpediente=$(xml).find('expedienteDTO').find('numeroExpediente').text();
	    			numeroExpedienteId=$(xml).find('expedienteDTO').find('numeroExpedienteId').text();
	    			numeroCasoNuevo=$(xml).find('expedienteDTO').find('casoDTO').find('numeroGeneralCaso').text();
	    		}
	    		
	    	});

			if( ! numeroCasoNuevo){
				numeroCasoNuevo = '- PENDIENTE -';
			}
	        var pantallaSolicitada=3;
        	idWindowNuevaDenuncia++;
    		window.parent.nuevaVentanaMenuIntermedio(idExpediente,numeroExpediente,numeroExpedienteId,numeroCasoNuevo,pantallaSolicitada);
    		adjuntarArchivoActuaciones(idExpediente);
    	}
    }
    
    function validadDatosObligatorios(){ 
  		
    	 if( $('#fileAdjuntarArchivo').val().trim() == "" ){
    		 alert("El campo denuncia externa es requerido");
    		 return false;
    	 }else
    		 return true;
     }
    
    function adjuntarArchivoActuaciones(idExpediente){
		    var descripcion = $('#txtDescripcion').val().trim();
			document.adjuntarDocForm.expedienteId.value = idExpediente;
			document.adjuntarDocForm.descripcion.value = descripcion;
			document.adjuntarDocForm.tipo.value = 1;
			forma = document.adjuntarDocForm;
			forma.submit();
	}
	</script>
</head>
  <body>
	<table width="500px" height="200px" border="0">
		  <tr>
			<td>
				 <table width="500px"  height="200px" border="0" align="center" cellpadding="0" cellspacing="0">
				     <tr height="12.5%">
				        <td width="10%">&nbsp;</td>
				        <td>&nbsp;</td>
						<td>&nbsp;</td>
				        <td>&nbsp;</td>
				     </tr >
				     <tr height="12.5%">
				        <td width="10%">&nbsp;</td>
				        <td width="25%" align="left">Denuncia externa:</td>
						<td align="left">
							<form id="adjuntarDocForm" name="adjuntarDocForm" action="<%= request.getContextPath() %>/adjuntarDenunciaExterna.do" method="post" enctype="multipart/form-data" >
								<input id="fileAdjuntarArchivo" type="file" name="archivoAdjunto" >
								<input type="hidden" name="expedienteId" />
								<input type="hidden" name="tipo" />
								<input type="hidden" name="descripcion" />
		 					</form>
						</td>
				        <td>&nbsp;</td>
				    </tr>
                     <tr height="12.5%">
                     	 <td width="10%">&nbsp;</td>
				         <td width="25%" align="left">Descripci&oacute;n:</td>
				         <td width="50%" rowspan="6" align="left" valign="top">
				            <textarea cols="35" rows="4" id="txtDescripcion" maxlength="200"></textarea>
				        </td>
				         <td width="20%">&nbsp;</td>
				     </tr >
				     <tr height="12.5%">
				        <td width="10%">&nbsp;</td>
				        <td>&nbsp;</td>
						<td>&nbsp;</td>
				    </tr>
				    <tr height="12.5%">
				        <td width="10%">&nbsp;</td>
				        <td>&nbsp;</td>
						<td>&nbsp;</td>
				    </tr>
				    <tr height="12.5%">
				        <td width="10%">&nbsp;</td>
				        <td>&nbsp;</td>
				        <td width="20%" align="left"><input type="button" id="btnGuardarArma" value="Guardar" class="btn_Generico" onclick="nuevaDenunciaUI();" /></td>
				    </tr>
				</table>
			</td>
		</tr>
	</table>
  </body>
</html>