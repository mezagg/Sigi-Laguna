<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Adjuntar imagen a elemento generico</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
        
        <script type="text/javascript">
       
         var CONTEXT_ROOT = '<%= request.getContextPath()%>';
         var elementoID='<%= request.getParameter("elementoID")%>';
         
                  
         //Permite restringir los tipos de archivos a subir en el servidor
         //Los tipo de archivos(.ext) de deben separar por comas, en minusculas y sin espacios por ejemplo: 
         //.doc,.jpg,.pdf
         var extensionesPermitidas='<%= request.getParameter("extensionesPermitidas")%>';

         var success='<%= request.getParameter("respueta")%>';
         
         $(document).ready(function() {
         	//Permite mandar un mensaje despues de registrar un amparo dado que el action redirige de nuevo a este mismo jsp
         	
         	if(extensionesPermitidas != null)
         		$('#tiposArchivos').html(extensionesPermitidas.replace(/,/g," &oacute; "))
         	
         	if(success != "null"){
         		if(parseInt(success) == 1){
         			customAlert("La imagen se adjunt&oacute; de forma correcta")
         			try{
         				window.parent.cargaGridArchivosDigitalesXElemento();
         			}catch(e){};
         		}	
             	else{
             		customAlert("Error al adjuntar imagen");             	    
             	}
             		    		
         	}         	         	
         	$("#btnGuardar").click(adjuntarDocumento);
         });
         
         function adjuntarDocumento(){        	 
        	if(datosObligatoriosCorrectos()){
          		if(validaExtensionDeArchivo($('#uploadArchivo').val().trim(),extensionesPermitidas))
        			adjuntarArchivo();
        	}        	
         }
         
         function datosObligatoriosCorrectos(){
      		 if($('#uploadArchivo').val().trim() == ""){
         		 msjDeError = "Es necesario seleccionar un archivo\n";
         		 customAlert(msjDeError);
        		 return false;
        	 }else
        		 return true;
         }
         
 		function validaExtensionDeArchivo(archivo,iType) {
 			esExtensionPermitida = false; 
 			if (!archivo) return;
			ext = (archivo.substring(archivo.lastIndexOf("."))).toLowerCase();

 			arregloDeExtensionesPermitidas = extensionesPermitidas.split(",");
 			for (var i = 0; i < arregloDeExtensionesPermitidas.length; i++) { 
	 			if (arregloDeExtensionesPermitidas[i] == ext) { 
		 			esExtensionPermitida = true; 
		 			break; 
	 			} 
 			} 
 			
 			if (!esExtensionPermitida) {
	 			customAlert("Usted s&oacute;lo puede subir archivos con extensiones (" + extensionesPermitidas.replace(/,/g," &oacute; ")+ ")");
 			}
 			return esExtensionPermitida;
 	   }
        
		function adjuntarArchivo(){
				$("#btnGuardar").attr('disabled','-1');
				document.adjuntarDocForm.elementoID.value = elementoID;
				document.adjuntarDocForm.extensionesPermitidas.value = extensionesPermitidas;
				document.adjuntarDocForm.submit();
		}
        </script>
    </head>
<body>

<table width="436" border="0" summary="Tabla para registrar un amparo">
   <tr>
     <td align="left">&nbsp;</td>
   </tr>
   <tr>
     <td align="center">Seleccione la imagen a adjuntar (<label id="tiposArchivos"></label>)</td>
   </tr>
   <tr>
     <td align="center">
       <label for="uploadArchivo"></label>
       
       <form id="adjuntarDocForm" name="adjuntarDocForm" action="<%= request.getContextPath() %>/ingresarImagenAObjeto.do" method="post" enctype="multipart/form-data" >
         * Archivo adjunto:
         <input id="uploadArchivo" type="file" name="archivo" value="" >
         <input type="hidden" name="elementoID" />
         <input type="hidden" name="extensionesPermitidas" />
         
         </form>     
     </td>
   </tr>
   <tr>
     <td align="center">
	   
	</td>
   </tr>
   <tr>
     <td align="center">       
     	<br/>
     	<input type="button" name="btnGuardar" id="btnGuardar" value="Guardar" tabindex="4" class="btn_Generico">
     </td>
   </tr>
</table>

</body>
</html>