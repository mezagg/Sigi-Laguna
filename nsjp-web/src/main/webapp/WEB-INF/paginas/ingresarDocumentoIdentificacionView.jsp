<script type="text/javascript">
	OPCION_CURP = 13;

	
	$(document).ready(function(){			
		jQuery(document).ajaxStop(desbloquearPantalla());			
	});

	
   /*
	*Funcion que dispara el Action para consultar el tipo de Narrativas
	*/
   function cargaTiposNarrativa() {
         $('#documentoIdentificacion').empty();
	      $.ajax({
		      type: 'POST',
	    	  url: '<%= request.getContextPath()%>/ingresarTipoDeDocumentoDeIdentificacion.do',
	    	  data: '',
	    	  dataType: 'xml',
	    	  async: false,
	    	  success: function(xml){
		    	  $('#documentoIdentificacion').empty();
		    	  $('#documentoIdentificacion').append('<option value="-1">- Seleccione -</option>');
	    		  $(xml).find('catTipoIdentificacion').each(function(){
					$('#documentoIdentificacion').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				   });
	    	  }
	    	});
		}	
   
   /*
   *Funcion para recuperar los datos del tipo de documento de identificacion
   */
   function recuperaDatosTipoDocIdentificacion(idCalidad)
   {
	   var lsDatosTipoDocIdent="";
	   lsDatosTipoDocIdent="&docIdentificacion="+$("#documentoIdentificacion option:selected").val();
	   lsDatosTipoDocIdent+="&folioDoc="+$("#folioDocumentoIdentificacion").val();
	   return lsDatosTipoDocIdent;
   }

   /*
    *Funcion para pintar los datos mediante la recuperacion del xml del tipo de documento de identificacion
    */
   function pintaDatosTipoDocIdentificacion(xml){
	   $('#folioDocumentoIdentificacion').val($(xml).find('folioIdentificacion').first().text());
	   var id=$(xml).find('valorIdIdentificaion').find('idCampo').first().text();
	   $('#documentoIdentificacion').find("option[value='"+id+"']").attr("selected","selected");
	}

   function desavilitarDatosIdentificacion(){
	   $('#folioDocumentoIdentificacion').attr("disabled","disabled");
	   $('#documentoIdentificacion').attr("disabled","disabled");
//	   var $widget = $("select").multiselect();
//		var	state = false; 
//	   state = !state; 
//	   $widget.multiselect(state ? 'disable' : 'enable');
	}

   function avilitarDatosIdentificacion(){
	   $('#folioDocumentoIdentificacion').attr("disabled","");
	   $('#documentoIdentificacion').attr("disabled","");
//	   var $widget = $("select").multiselect();
//		var	state = true; 
//	   state = !state; 
//	   $widget.multiselect(state ? 'disable' : 'enable');
	}
   
   //funcion para limpiar los campos de captura
   function limpiarCmpsCptrDocId()
   {
	   $('#folioDocumentoIdentificacion').val("");
	   $('#documentoIdentificacion').find("option[value='-1']").attr("selected","selected");
   }
   
   function validaCurp(campo){
	   
	   if($('#documentoIdentificacion').val()==	OPCION_CURP){//CURP
		var regexp = /^[A-Z �]{4}\d{2}(1|0)\d(0|1|2|3)\d(H|M)[A-Z �]{5}(\d{2})?$/;
		texto = "Favor de verificar que el CURP ingresado sea correcto";
	   }else{
		   return;
	   }		

	    aEvaluar = campo.value;
	    if (!regexp.test(aEvaluar)) {
	    	customAlert(texto);
	    	campo.value = "";
	    } 
	}
   
</script>

<table border="0" width="100%" class="fondoClaroAp">
	<tr>
		<td>&nbsp;</td>
		<td>Tipo de documento de identificaci�n</td>
		<td>Folio de documento de identificaci�n</td>
	</tr>
	<tr>
		<td>Documento</td>
		<td align="center">
			<select id="documentoIdentificacion" name="documentoIdentificacion" onchange="$('#folioDocumentoIdentificacion').val('')">
				<option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
			</select>
		</td>
		<td><input type="text" id="folioDocumentoIdentificacion" name="folioDocumentoIdentificacion" maxlength="30" onblur="validaCurp(this)"/></td>
	</tr>
</table>

<script type="text/javascript">
   	    cargaTiposNarrativa();

//   	 $("#documentoIdentificacion").multiselect({ 
//			multiple: false, 
//			header: "Seleccione", 
//			position: { 
//				my: 'bottomr', 
//				at: 'bottom' 
//			},
//			selectedList: 1 
//		});
</script>