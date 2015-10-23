<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reasignar UIE de expediente</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
        <script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
        
        <script type="text/javascript">
       
         var CONTEXT_ROOT = '<%= request.getContextPath()%>';
         var idExpediente= '<%= request.getParameter("idExpediente")%>';
         
         $(document).ready(function() {
         	$("#btnGuardar").click(asignaUIE);
         	consultarCatalogoUIE();
         	
    		$("#cbxCatUIE").multiselect({ 
    			multiple: false, 
    			header: "- Seleccione una UIE -", 
    			position: { 
    				my: 'center', 
    				at: 'center' 
    			},
    			minWidth: 50,
    			selectedList: 1, 
    			close: function (event,ui){
    				
    			}
    		});
         	
         });
         
		/*
		*Funcion que realiza la carga del combo de unidad de investigacion especializada
		*/
		function consultarCatalogoUIE() {

			$('#cbxCatUIE').empty();
			$('#cbxCatUIE').append('<option value="0">- Seleccione -</option>');
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/consultarCatalogoUIE.do',
				data: '',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('CatUIEspecializadaDTO').each(function(){
						$('#cbxCatUIE').append('<option value="'+$(this).find('catUIEId').text() + '">'+$(this).find('claveUIE').text()+" - "+$(this).find('nombreUIE').text()+'</option>');
					});
				}
			});
		}
		
		/*
		* Funcion que permite asignar una UIE a un expediente
		*/
		function asignaUIE(){
			if( $("#cbxCatUIE").val() != 0){
					var params="";
					params="idExpediente="+idExpediente;
					params+="&idCatUIE="+ $('#cbxCatUIE').val();
					
					$.ajax({
						async: false,
						type: 'POST',
						url: '<%= request.getContextPath()%>/actualizarCatUIEDeExpediente.do',
						data: params,
						dataType: 'xml',
						success: function(xml){
								//revisamos la respuesta enviada
								if(parseInt($(xml).find('code').text())==0)
								{
									var bandera=$(xml).find('bandera').text();
									if(parseInt(bandera)==0)
									{
										customAlert("Ocurri&oacute; un error al reasignar la Unidad de Investigaci&oacute;n Especializada");
									}
									else
									{
										customAlert("Se reasign&oacute; la Unidad de Investigaci&oacute;n Especializada de forma correcta");
									}
									
								}
								else
								{
									customAlert("Ocurri&oacute; un error al reasignar la Unidad de Investigaci&oacute;n Especializada");
								}
						}
					});
			}	
			else{
				customAlert("Debe de seleccionar una Unidad de Investigaci&oacute;n Especializada")
			}
		}

        </script>
    </head>
<body>
	<table border="0" align="center">
	   <tr>
	     <td align="left">&nbsp;</td>
      </tr>
	   <tr>
	     <td align="left">&nbsp;</td>
      </tr>
	   <tr>
	     <td align="left">&nbsp;</td>
      </tr>
	   <tr>
	     <td align="left">&nbsp;</td>
	   </tr>
	   <tr>
	     <td align="center"> * Unidad de Investigaci&oacute;n Especializada:</td>
	   </tr>
	   <tr>
	     <td align="center">	         
	         <select name="cbxCatUIE" id="cbxCatUIE">
				<option value="0">- Seleccione una UIE -</option>					
			 </select>
	     </td>
	   </tr>
	   <tr>
	     <td align="center">
		   
		</td>
	   </tr>
	   <tr>
	     <td align="center">       
	     	<br/>
	     	<input type="button" name="btnGuardar" id="btnGuardar" value="Reasignar Unidad de Investigaci&oacute;n Especializada" class="btn_Generico">
	     </td>
	   </tr>
	</table>
</body>
</html>