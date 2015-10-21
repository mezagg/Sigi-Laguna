    <!--CSS, modificada para las tabs del domicilio -->
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estiloTab.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
	
	<%@page import="mx.gob.segob.nsjp.comun.enums.catalogo.EntidadFederativa"%>
	<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
		   
	<script type="text/javascript">	 
	
	<% 
	ConfiguracionDTO cfg=(ConfiguracionDTO) request.getSession().getAttribute("KEY_SESSION_CONFIGURACION_GLOBAL");
	String entidadFederativaDef=cfg.getEntidadFederativaDespliegueId().toString();
	%>
	
	var entidadFederativaDef = '<%=entidadFederativaDef%>';
	
	$(document).ready(function(){			
		jQuery(document).ajaxStop(desbloquearPantalla());
//		muestraDomicilioNotificaciones();
	});

	<%-- function seleccionaEntidadFedereativaDef(){
		if( entidadFederativaDef=='<%=EntidadFederativa.ZACATECAS.getValorId()%>'){
			$('#cbxEntFederativaDef').val('<%=EntidadFederativa.ZACATECAS.getValorId()%>');
			onSelectChangeEntFedDef();
			$('#cbxEntFederativaNotif').val('<%=EntidadFederativa.ZACATECAS.getValorId()%>');
			onSelectChangeEntFedNotif();
		}else if( entidadFederativaDef=='<%=EntidadFederativa.YUCATAN.getValorId()%>'){
			$('#cbxEntFederativaDef').val('<%=EntidadFederativa.YUCATAN.getValorId()%>');
			onSelectChangeEntFedDef();
			$('#cbxEntFederativaNotif').val('<%=EntidadFederativa.YUCATAN.getValorId()%>');
			onSelectChangeEntFedNotif();
		}
	} --%>
	
//	 var id0="0";
//	 function seleccionaMexicoDef(){
//		$('#cbxPaisDef').val(10);
//		onSelectChangePaisDef();
//		$('#cbxPaisNotif').val(10);
//		onSelectChangePaisNotif();
//	 }
//	 function cambiaNombreTabIngDomicilio(texto)
//	 {
//		$("#hrefIngDomDef").html(texto);	 
//	 }
	 
	 /**
	  * Oculta el tab para el dimicilio de notificaciones
	  */
//	  function ocultaDomicilioNotificaciones(){
//		$("#tabsDef").tabs("option", "disabled", [1]);		
//	  }
	 /**
	  * Oculta el renglon para activar el domicilio de notificaciones
	  * y en las tabs, oculta la ceja del domicilio notificaciiones
	  */
	 /*  function killDomicilioNotificaciones(){
		$('#liDomNotif').hide();
		$('#rowDomicilioNotifDef').hide();		
	  } */
	 
	 /**
	  * Oculta el renglon de las coordenadas geograficas
	  */
	 /*  function killCoordenadasGeograficas(){
		$('#rowCoordGPSDef').hide();
		$("#colCoordGPSUnoDef").hide();
	  } */
			
	 /**
	  * Muestra el tab para el dimicilio de notificaciones
	  */	
	  function muestraDomicilioNotificaciones(){
		$('#tabsDef').tabs('enable', 1);
	  }
	  /**
	  * Muestra el renglon para activar el domicilio de notificaciones
	  * y en las tabs, oculta la ceja del domicilio notificaciiones
	  */
	/*   function liveDomicilioNotificaciones(){
		$('#liDomNotif').show();
		$('#rowDomicilioNotifDef').show();		
	  } */

	 /**
	  * Muestra u oculta los combo box's o cajas de texto dependiendo
	  * de el pa&iacute;s seleccionado tiene o no, entidades federativas.
	  * Esto para el domicilio.
	  */	
	  function hideControlsDef(existenEntidades) {
		  
		if(existenEntidades == "si" ){
	      $('#divCbxEntFederativaDef').show();	
//		  $('#divCbxCiudadDef').show();
		  $('#divCbxDelegacionMunicipioDef').show();
		  $('#divCbxAsentamientoColoniaDef').show();
//		  $('#trSectorDef').hide();
//		  $('#divCbxTipoAsentamientoDef').show();
//		  $('#divCbxTipoCalleDef').show();
		  //se oculto para la presentacion
//		  $('#codigoPostalButtonDef').show();
		  $('#entidadFederativaDef').hide();
		  $('#areaDelegacionMunicipioDef').hide();
//		  $('#areaCiudadDef').hide();
		  $('#areaColoniaDef').hide();
//		  $('#areaAsentamientoDef').hide();
//		  $('#areaTipoCalleDef').hide();
		}
		else{
	      $('#divCbxEntFederativaDef').hide();
//		  $('#divCbxCiudadDef').hide();
		  $('#divCbxDelegacionMunicipioDef').hide();
		  $('#divCbxAsentamientoColoniaDef').hide();
//		  $('#divCbxTipoAsentamientoDef').hide();
//		  $('#trSectorDef').hide();
//		  $('#divCbxTipoCalleDef').hide();
//		  $('#codigoPostalButtonDef').hide();
		  $('#entidadFederativaDef').show();
		  $('#areaDelegacionMunicipioDef').show();
//		  $('#areaCiudadDef').show();
		  $('#areaColoniaDef').show();
//		  $('#areaAsentamientoDef').show();
//		  $('#areaTipoCalleDef').show();
		}			
	  }


	 /**
	  * Muestra u oculta los combo box's o cajas de texto dependiendo
	  * de el pa&iacute;s seleccionado tiene o no, entidades federativas.
	  * Esto para el domicilio.
	  */		
	  /* function hideControlsNotif(existenEntidades) {
	  
		if (existenEntidades == "si") {
		    $('#divCbxEntFederativaNotif').show();	
	   	    $('#divCbxCiudadNotif').show();
		    $('#divCbxDelegacionMunicipioNotif').show();
		    $('#divCbxAsentamientoColoniaNotif').show();
		    $('#divCbxTipoAsentamientoNotif').show();
		    $('#trSectorNotif').hide();
		    $('#divCbxTipoCalleNotif').show();
			$('#codigoPostalButtonNotif').show();
			$('#entidadFederativaNotif').hide();
			$('#areaDelegacionMunicipioNotif').hide();
			$('#areaCiudadNotif').hide();
			$('#areaColoniaNotif').hide();
			$('#areaAsentamientoNotif').hide();
			$('#areaTipoCalleNotif').hide();
		  } 
		else {
			$('#divCbxEntFederativaNotif').hide();
			$('#divCbxCiudadNotif').hide();
			$('#divCbxDelegacionMunicipioNotif').hide();
			$('#divCbxAsentamientoColoniaNotif').hide();
			$('#divCbxTipoAsentamientoNotif').hide();
			$('#trSectorNotif').hide();
			$('#divCbxTipoCalleNotif').hide();
			$('#codigoPostalButtonNotif').hide();
			$('#entidadFederativaNotif').show();
			$('#areaDelegacionMunicipioNotif').show();
			$('#areaCiudadNotif').show();
			$('#areaColoniaNotif').show();
			$('#areaAsentamientoNotif').show();
			$('#areaTipoCalleNotif').show();
		  }			
	  } */


	 /**
	  * Limpia todo el formulario
	  *	(AUN SIN FUNCIONALIDAD)
	  */	
	  function limpiarFormularioDef(){
		  
		$('#ingresarDomicilioForm').each (function() { this.reset(); });
//		cleanAllCombosDef();
		hideControlsDef("no");
//		 $('#cbxPaisDef').find("option[value='-1']").attr("selected","selected");
//		$('#codigoPostalDef').val("");
//		$('#areaCalleDef').val("");
//		$('#areaEntreCalleDef').val("");
//		$('#areaYCalleDef').val("");
//		$('#areaAliasDef').val("");
//		$('#areaEdificioDef').val("");
//		$("#areaReferenciasDef").val("");
//		$("#areaNumeroExteriorDef").val("");
//		$("#areaNumeroInteriorDef").val("");
//		$("#sectorDef").val("");
		/* cleanAllCombosNotif();
		hideControlsNotif("no");  	
		$('#cbxPaisNotif').find("option[value='-1']").attr("selected","selected");
		$('#codigoPostalNotif').val("");
		$('#areaCalleNotif').val("");
		$('#areaEntreCalleNotif').val("");
		$('#areaYCalleNotif').val("");
		$('#areaAliasNotif').val("");
		$('#areaEdificioNotif').val("");
		$("#areaReferenciasNotif").val("");
		$("#areaNumeroExteriorNotif").val("");
		$("#areaNumeroInteriorNotif").val("");
		$("#sectorNotif").val(""); */
	  }


	 /**
	  * Limpia los combos:Ent Federativa, Ciudad, DelegacionMunicipio, AsentamientoColonia
	  * Tipo de Asentamiento y Tipo de Calle, para el Domicilio
	  */  
	/*   function cleanAllCombosDef(){
		  
//		$('#cbxEntFederativaDef').empty();
//		$('#cbxEntFederativaDef').append('<option value="-1">-Seleccione-</option>');	//Por default seleccione
	//	$('#cbxEntFederativaDef').multiselect('refresh');
		$('#cbxCiudadDef').empty();
		$('#cbxCiudadDef').append('<option value="-1">-Seleccione-</option>');	
	//	$('#cbxCiudadDef').multiselect('refresh');
		$('#cbxDelegacionMunicipioDef').empty();
		$('#cbxDelegacionMunicipioDef').append('<option value="-1">-Seleccione-</option>');
	//	$('#cbxDelegacionMunicipioDef').multiselect('refresh');
		$('#cbxAsentamientoColoniaDef').empty();
		$('#cbxAsentamientoColoniaDef').append('<option value="-1">-Seleccione-</option>');
	//	$('#cbxAsentamientoColoniaDef').multiselect('refresh');
		$('#cbxTipoAsentamientoDef').empty();
		$('#cbxTipoAsentamientoDef').append('<option value="-1">-Seleccione-</option>');
		$('#cbxTipoAsentamientoDef').attr('disabled', 'disabled');
	//	$('#cbxTipoAsentamientoDef').multiselect('refresh');
		$('#cbxTipoCalleDef').empty();
		$('#cbxTipoCalleDef').append('<option value="-1">-Seleccione-</option>');
	//	$('#cbxTipoCalleDef').multiselect('refresh');
	  } */
	  

	 /**
	  * Limpia los combos:Ent Federativa, Ciudad, DelegacionMunicipio, AsentamientoColonia
	  * Tipo de Asentamiento y Tipo de Calle, para el Domicilio de Notificaciones
	  */  
	 /*  function cleanAllCombosNotif(){

		$('#cbxEntFederativaNotif').empty();
		$('#cbxEntFederativaNotif').append('<option value="-1">-Seleccione-</option>');	//Por default seleccione
//		$('#cbxEntFederativaNotif').multiselect('refresh');
		$('#cbxCiudadNotif').empty();
		$('#cbxCiudadNotif').append('<option value="-1">-Seleccione-</option>');	
//		$('#cbxCiudadNotif').multiselect('refresh');
		$('#cbxDelegacionMunicipioNotif').empty();
		$('#cbxDelegacionMunicipioNotif').append('<option value="-1">-Seleccione-</option>');	
//		$('#cbxDelegacionMunicipioNotif').multiselect('refresh');
		$('#cbxAsentamientoColoniaNotif').empty();
		$('#cbxAsentamientoColoniaNotif').append('<option value="-1">-Seleccione-</option>');	
//		$('#cbxAsentamientoColoniaNotif').multiselect('refresh');
		$('#cbxTipoAsentamientoNotif').empty();
		$('#cbxTipoAsentamientoNotif').append('<option value="-1">-Seleccione-</option>');
		$('#cbxTipoAsentamientoNotif').attr('disabled', 'disabled');
//		$('#cbxTipoAsentamientoNotif').multiselect('refresh');
		$('#cbxTipoCalleNotif').empty();
		$('#cbxTipoCalleNotif').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxTipoCalleNotif').multiselect('refresh');
		  }
		 */
		
	 /**
	  * Limpia los combos que dependen del combo entidad federativa, para 
	  * el domicilio
	  */  
	/*   function cleanCombosDependsEntidadFed(){
		$('#cbxCiudadDef').empty();
		$('#cbxCiudadDef').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxCiudadDef').multiselect('refresh');
		$('#cbxDelegacionMunicipioDef').empty();
		$('#cbxDelegacionMunicipioDef').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxDelegacionMunicipioDef').multiselect('refresh');
		$('#cbxAsentamientoColoniaDef').empty();
		$('#cbxAsentamientoColoniaDef').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxAsentamientoColoniaDef').multiselect('refresh');
		$("#sectorDef").val("");
	  } */
		
		
	 /**
	  * Limpia los combos que dependen del combo entidad federativa, para 
	  * el domicilio de notificaciones
	  */ 	
	 /*  function cleanCombosDependsEntidadFedNotif(){
		$('#cbxCiudadNotif').empty();
		$('#cbxCiudadNotif').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxCiudadNotif').multiselect('refresh');
		$('#cbxDelegacionMunicipioNotif').empty();
		$('#cbxDelegacionMunicipioNotif').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxDelegacionMunicipioNotif').multiselect('refresh');
		$('#cbxAsentamientoColoniaNotif').empty();
		$('#cbxAsentamientoColoniaNotif').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxAsentamientoColoniaNotif').multiselect('refresh');
		$("#sectorNotif").val("");
	}
		 */

	 /**
	  * Limpia los combos que dependen del combo Ciudad, para 
	  * el domicilio
	  */  	
	  /* function cleanCombosDependsCiudad(){
		$('#cbxDelegacionMunicipioDef').empty();
		$('#cbxDelegacionMunicipioDef').append('<option value="-1">-Seleccione-</option>');	
//		$('#cbxDelegacionMunicipioDef').multiselect('refresh');
		$('#cbxAsentamientoColoniaDef').empty();
		$('#cbxAsentamientoColoniaDef').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxAsentamientoColoniaDef').multiselect('refresh');
		$("#sectorDef").val("");
		  } */


	 /**
	  * Limpia los combos que dependen del combo entidad federativa, para 
	  * el domicilio de notificaciones
	  */ 	
	  /* function cleanCombosDependsCiudadNotif(){
		$('#cbxDelegacionMunicipioNotif').empty();
		$('#cbxDelegacionMunicipioNotif').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxDelegacionMunicipioNotif').multiselect('refresh');
		$('#cbxAsentamientoColoniaNotif').empty();
		$('#cbxAsentamientoColoniaNotif').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxAsentamientoColoniaNotif').multiselect('refresh');
		$("#sectorNotif").val("");
		  } */

	 /**
	  * Limpia los combos que dependen del combo Delegacion/Municipio, para 
	  * el domicilio
	  */ 	
	 /*  function cleanCombosDependsDelegMunDef(){
		$('#cbxAsentamientoColoniaDef').empty();
		$('#cbxAsentamientoColoniaDef').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxAsentamientoColoniaDef').multiselect('refresh');
		$("#sectorDef").val("");
		  }
 */
	 /**
	  * Limpia los combos que dependen del combo Delegacion/Municipio, para 
	  * el domicilio de notificaciones
	  */ 	
	/* function cleanCombosDependsDelegMunNotif(){
		$('#cbxAsentamientoColoniaNotif').empty();
		$('#cbxAsentamientoColoniaNotif').append('<option value="-1">-Seleccione-</option>');	
//		$('#cbxAsentamientoColoniaNotif').multiselect('refresh');
		$("#sectorNotif").val("");
  	} */

	 /**
	  * Limpia los combos que dependen de la consulta por codigo postal
	  * para el domicilio
	  */ 	
	  /* function cleanAllCombosCodigoPostalDef(){
		$('#cbxEntFederativaDef').empty();
		$('#cbxCiudadDef').empty();
		$('#cbxDelegacionMunicipioDef').empty();
		$('#cbxAsentamientoColoniaDef').empty();
		$("#sectorDef").val("");
	  } */

	 /**
	  * Limpia los combos que dependen de la consulta por codigo postal
	  * para el domicilio de notificaciones
	  */ 	
	 /*  function cleanAllCombosCodigoPostalNotif(){
		$('#cbxEntFederativaNotif').empty();
		$('#cbxCiudadNotif').empty();
		$('#cbxDelegacionMunicipioNotif').empty();
		$('#cbxAsentamientoColoniaNotif').empty();
		$("#sectorNotif").val("");
	  }
	 */

	//*****FUNCIONES PARA EL CU INGRESAR DOMICILIO*********//

	/**
	*Funcion que limpia el combo box de asentamiento colonia
	*/
	/* function cleanComboAsentColoniaDef(){
		$('#cbxAsentamientoColoniaDef').empty();
		$('#cbxAsentamientoColoniaDef').append('<option value="-1">-Seleccione-</option>');
	}
 */
	/**
	*Funcion que limpia el combo box de asentamiento colonia
	*/
	/* function cleanComboAsentColoniaNotif(){
		$('#cbxAsentamientoColoniaNotif').empty();
		$('#cbxAsentamientoColoniaNotif').append('<option value="-1">-Seleccione-</option>');
	  } */
	
	/*
	*Funcion que realiza la carga del combo de Paises
	*/
	<%-- function cargaPaises() {
		 
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarPaises.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catPaises').each(function(){ 
					$('#cbxPaisDef').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	} --%>


	/*
	*Funcion que realiza la carga del combo de Paises, para el Domicilio de notificaciones	
	*/
	<%-- function cargaPaisesNotif() {
		  
		$.ajax({
			
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarPaises.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catPaises').each(function(){
					$('#cbxPaisNotif').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
//					$('#cbxPaisNotif').multiselect('refresh');
						});
			}
		  });
	  } --%>

	
	/**
	* Si existe un cambio en el combo de paises se realiza la consulta de 
	* entidades federativas, y si la consulta es NO vac&iacute;a se leventa la 
	* bandera para mostrar los combo box. Esto para el domicilio
	*/ 	
	<%-- function onSelectChangePaisDef() {
		  
		var selected = $("#cbxPaisDef option:selected");
		var existenEntidades = "no";
			
				
		cleanAllCombosDef();							//Limpia todos los combo box큦		
		hideControlsDef(existenEntidades);				//Si la opcion seleccionada no contiene entidades federativas se esconden los cbx's
		$.ajax({
			async: false,									// la accion cargar estados y llena el combo con la consulta
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarEntFederativas.do',
			data: 'glCatPaisId=' + selected.val(),	//Parametro para hacer la consulta de Entidades por Id del Pa&iacute;s
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catEntidadesFed').each(function(){
					$('#cbxEntFederativaDef').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');					
					existenEntidades = "si";	
				});
				hideControlsDef(existenEntidades);	
				cargaTipoAsentDef();
				cargaTipoCalleDef();
			}
		});
	} --%>


	/**
	* Si existe un cambio en el combo de paises se realiza la consulta de 
	* entidades federativas, y si la consulta es NO vac&iacute;a se leventa la 
	* bandera para mostrar los combo box. Esto para el domicilio de Notificaciones
	*/ 	
	<%-- function onSelectChangePaisNotif() {
	  
		var selected = $("#cbxPaisNotif option:selected");
		var existenEntidades = "no";
		
		
		cleanAllCombosNotif();						//Limpia todos los combo box큦
		hideControlsNotif(existenEntidades);		//Si la opcion seleccionada no contiene entidades federativas se esconden los cbx's
		$.ajax({
			async: false,									// la accion cargar estados y llena el combo con la consulta
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarEntFederativas.do',
			data: 'glCatPaisId=' + selected.val(),	//Parametro para hacer la consulta de Entidades por Id del Pa&iacute;s
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catEntidadesFed').each(function(){
					$('#cbxEntFederativaNotif').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					existenEntidades = "si";	
				});
				hideControlsNotif(existenEntidades);	
				cargaTipoAsentNotif();
				cargaTipoCalleNotif();
		 	}
		});	
	} --%>

		  
	/**
	* Si existe un cambio en el combo de Entidades federativas se realiza la consulta de 
	* entidades Ciudades. Esto para el domicilio
	*/ 	
	<%-- function onSelectChangeEntFedDef() {
			  
		var selected = $("#cbxEntFederativaDef option:selected");
		
		cleanCombosDependsEntidadFed();
		$.ajax({											// la accion cargar cidades
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarCiudades.do',
			data: 'glCatEntidadFederativaId=' + selected.val(), 	//hace la consulta por el id de la Entidad Federativa
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catCiudades').each(function(){											//LLena el comboBox con la consulta
					$('#cbxCiudadDef').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');					
				});
				
			}
		});
		
		if(selected.val() == entidadFederativaDef){
			$('#trSectorDef').show();
			$('#sectorDef').attr("disabled","disabled");
		}else{
			$('#trSectorDef').hide();
		}
		
		onSelectChangeCiudadDef();				
	} --%>


	/**
	* Si existe un cambio en el combo de Entidades federativas se realiza la consulta de 
	* entidades Ciudades. Esto para el domicilio de Notificaciones
	*/ 	
	<%-- function onSelectChangeEntFedNotif() {
	  
		var selected = $("#cbxEntFederativaNotif option:selected");
	  
		cleanCombosDependsEntidadFedNotif();
		$.ajax({
			async: false,												// la accion cargar cidades
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarCiudades.do',
			data: 'glCatEntidadFederativaId=' + selected.val(), 	//hace la consulta por el id de la Entidad Federativa
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catCiudades').each(function(){											//LLena el comboBox con la consulta
					$('#cbxCiudadNotif').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');	
				});
				
				if(selected.val() == entidadFederativaDef){
					$('#trSectorNotif').show();
					$('#sectorNotif').attr("disabled","disabled");
				}else{
					$('#trSectorNotif').hide();
				}
				
		  	}
		});
		onSelectChangeCiudadNotif();			
	} --%>


	/**
	* Si existe un cambio en el combo de Ciudades se realiza la consulta de 
	* Delegaciones/Municipios.
	*/ 	
	<%-- function onSelectChangeCiudadDef() {
			    
		var selected = $("#cbxEntFederativaDef option:selected");
		  
		//cleanCombosDependsCiudad();
		$.ajax({
			async: false,														// Ejecuta la accion cargar Delegacion/Municipio
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarDelgMun.do',
			data: 'glCatEntidadFederativaId=' + selected.val(), 				//hace la consulta por el id de la Entidad Federativa
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catDelegMun').each(function(){				//LLena el comboBox con la consulta
					$('#cbxDelegacionMunicipioDef').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');						
				});
			}	
		});
	}	  
 --%>
	
	/**
	* Si existe un cambio en el combo de Ciudades se realiza la consulta de 
	* Delegaciones/Municipios. Esto para el domicilio de notificaciones
	*/ 	
	<%-- function onSelectChangeCiudadNotif() {
		
		var selected = $("#cbxEntFederativaNotif option:selected");
		
		//cleanCombosDependsCiudadNotif();
		$.ajax({														// Ejecuta la accion cargar Delegacion/Municipio
			async: false,														
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarDelgMun.do',		
			data: 'glCatEntidadFederativaId=' + selected.val(),			//Realiza la consulta de acuerdo al id de la Ciudad
			dataType: 'xml',
			success: function(xml){
			  $(xml).find('catDelegMun').each(function(){				//LLena el comboBox con la consulta
				$('#cbxDelegacionMunicipioNotif').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');	
	  	      });
			}
		});
	}
 --%>

	/**
	* Si existe un cambio en el combo de Ciudade, delegacion, o tipo de asentamiento
	* se realiza la consulta de por medio de esos tres id큦 para cargar el asentamiento/colonia
	*/ 	
	<%-- function onSelectChangeCiudadMunicipioTipoAsentamientoDef() {

		var parametrosConsulta ='';
			  
		parametrosConsulta += 'glDelgMunId='+ $("#cbxDelegacionMunicipioDef option:selected").val();
		parametrosConsulta += '&glCatCiudadId=' + $("#cbxCiudadDef option:selected").val();
		//parametrosConsulta += '&glCatTipoAsentamientoId=' + $("#cbxTipoAsentamientoDef option:selected").val();

		cleanComboAsentColoniaDef();
		$.ajax({
			async: false,														// Ejecuta la accion cargar Delegacion/Municipio
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarAsentColonia.do',
			data: parametrosConsulta,
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catAsentColonia').each(function(){			//LLena el comboBox con la consulta
					$('#cbxAsentamientoColoniaDef').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');	
				});
			}
		});
	} --%>

	
	/**
	* Si existe un cambio en el combo de Ciudade, delegacion, o tipo de asentamiento
	* se realiza la consulta de por medio de esos tres id큦 
	* para cargar el asentamiento/colonia, para el domicilio notificaciones
	*/ 	
	<%-- function onSelectChangeCiudadMunicipioTipoAsentamientoNotif() {

		var parametrosConsultaNotif='';
			  
		parametrosConsultaNotif += 'glDelgMunId='+ $("#cbxDelegacionMunicipioNotif option:selected").val();
		parametrosConsultaNotif += '&glCatCiudadId=' + $("#cbxCiudadNotif option:selected").val();
		//parametrosConsultaNotif += '&glCatTipoAsentamientoId=' + $("#cbxTipoAsentamientoNotif option:selected").val();

		cleanComboAsentColoniaNotif();
		$.ajax({
			async: false,														// Ejecuta la accion cargar Delegacion/Municipio
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarAsentColonia.do',
			data: parametrosConsultaNotif,
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catAsentColonia').each(function(){			//LLena el comboBox con la consulta
					$('#cbxAsentamientoColoniaNotif').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');	
				});
			}
		});
	} --%>

	
	/**
	* Si existe un cambio en el combo de Ciudades se realiza la consulta de 
	* Delegaciones/Municipios.
	*/ 	
	<%-- function onSelectChangeDelgMunDef() {
		  
		var selected = $("#cbxDelegacionMunicipioDef option:selected");
		    
		cleanCombosDependsDelegMunDef();
		$.ajax({														// Ejecuta la accion cargar Delegacion/Municipio
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarAsentColonia.do',
			data: 'glDelgMunId=' + selected.val(),						//Realiza la consulta de acuerdo al id de la delegacion o municipio
			dataType: 'xml',
			success: function(xml){
			  $(xml).find('catAsentColonia').each(function(){			//LLena el comboBox con la consulta
				$('#cbxAsentamientoColoniaDef').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');	
//				$('#cbxAsentamientoColoniaDef').multiselect('refresh');
				   });
//			  $('#cbxAsentamientoColoniaDef').multiselect('refresh');
			}
		});
	}
 --%>
	
	/**
	* Si existe un cambio en el combo de Ciudades se realiza la consulta de 
	* Delegaciones/Municipios. Esto para el domicilio de notificaciones
	*/ 	
	<%-- function onSelectChangeDelgMunNotif() {
	  
		var selected = $("#cbxDelegacionMunicipioNotif option:selected");

		cleanCombosDependsDelegMunNotif();
		$.ajax({
			async: false,													// Ejecuta la accion cargar Delegacion/Municipio
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarAsentColonia.do',
			data: 'glDelgMunId=' + selected.val(),					//Realiza la consulta de acuerdo al id de la delegacion o municipio
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catAsentColonia').each(function(){														//LLena el comboBox con la consulta
					$('#cbxAsentamientoColoniaNotif').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');	
			 	});
			}
		});
	}		 --%>

	
		
	/*
	*Funcion que realiza la carga del combo de tipo de asentamiento	
	*/
	<%-- function cargaTipoAsentDef() {	
	  
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarTipoAsent.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catTipoAsentamiento').each(function(){
					$('#cbxTipoAsentamientoDef').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');					
				});  
		  	}
		});
	  } --%>


	/*
	*Funcion que realiza la carga del combo de tipo de asentamiento, 
	*para el domicilio de notificaciones
	*/
	<%-- function cargaTipoAsentNotif() {
	  
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarTipoAsent.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catTipoAsentamiento').each(function(){
					$('#cbxTipoAsentamientoNotif').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	} --%>



		
	
	 /*
	  *Funcion que realiza la carga del combo de tipo de calle,
	  *para el domicilio 
	  */
	  <%-- function cargaTipoCalleDef() {
		
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarTiposDeCalle.do',
		  data: '',
		  dataType: 'xml',
		  success: function(xml){
			var option;
			$(xml).find('catTipoCalle').each(function(){
				$('#cbxTipoCalleDef').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    });			
		  }
		});
	  } --%>


	 /*
	  *Funcion que realiza la carga del combo de tipo de calle, 
	  *para el domicilio de notificaciones
	  */
	<%--   function cargaTipoCalleNotif() {
	  
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarTiposDeCalle.do',
		  data: '',
		  dataType: 'xml',
		  success: function(xml){
			$(xml).find('catTipoCalle').each(function(){
				$('#cbxTipoCalleNotif').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
			});
		  }
		});
	  } --%>


	  /*
	   *Funcion que realiza la consulta de los datos
	   *por c&oacute;digo postal
	   */
	 <%--  function cosultaPorCodigoPostalDef(){	  
		var codigoPostalDef = $("#codigoPostalDef");
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/ConsultaDatosAsentDefuncion.do',
		  data: 'codigoPostal='+ codigoPostalDef.val(),
		  dataType: 'xml',
		  success: function(xml){
			//cleanAllCombosCodigoPostal();
			var oAsentamiento = $(xml);
			primeraEntidad = $(xml).find('asentamiento').first();
	
			 var entidadFede = primeraEntidad.find('municipioDTO').find('entidadFederativaId').text();
			 $('#cbxEntFederativaDef').find("option[value='"+entidadFede+"']").attr("selected","selected");
			 onSelectChangeEntFedDef();
			 
			 var ciudadfed = primeraEntidad.find('ciudadDTO').find('ciudadId').text();
			 $('#cbxCiudadDef').find("option[value='"+ciudadfed+"']").attr("selected","selected");
			 //onSelectChangeCiudadMunicipioTipoAsentamiento();			 
			 
			 var municipio=primeraEntidad.find('municipioDTO').find('municipioId').text();
			 $('#cbxDelegacionMunicipioDef').find("option[value='"+municipio+"']").attr("selected","selected");
			// onSelectChangeDelgMun();
			var asent=primeraEntidad.find('asentamientoId').text();
			 cleanCombosDependsDelegMunDef();
			 $(xml).find('asentamiento').each(function(){			
					$('#cbxAsentamientoColoniaDef').append('<option value="' + $(this).find('asentamientoId').text() + '">' + $(this).find('nombreAsentamiento').text() + '</option>');	
			 });
			 $('#cbxAsentamientoColoniaDef').find("option[value='"+asent+"']").attr("selected","selected");
			 
			 var tipoAsentamiento=primeraEntidad.find('tipoAsentamientoDTO').find('tipoAsentamientoId').text();
			 $('#cbxTipoAsentamientoDef').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
			 
			 var sectorDef = primeraEntidad.find('sector').text();
			 $('#sectorDef').val(sectorDef);
		  }
		});
	  } --%>


	  /*
	   *Funcion que realiza la consulta de los datos
	   *por c&oacute;digo postal, para el domicilio de notificaciones
	   *(SIN FUNCIONALIDAD POR EL MOMENTO)
	   */
<%-- 	  function cosultaPorCodigoPostalNotif(){
		  var codigoPostalDef = $("#codigoPostalNotif");	
			$.ajax({
				  type: 'POST',
				  url: '<%= request.getContextPath()%>/ConsultaDatosAsent.do',
				  data: 'codigoPostalDef='+ codigoPostalDef.val(),
				  dataType: 'xml',
				  success: function(xml){
					//cleanAllCombosCodigoPostal();
						var oAsentamiento = $(xml);
						primeraEntidad = $(xml).find('asentamiento').first();
				
						 var entidadFede = primeraEntidad.find('municipioDTO').find('entidadFederativaId').text();
						 $('#cbxEntFederativaNotif').find("option[value='"+entidadFede+"']").attr("selected","selected");
						 onSelectChangeEntFedNotif();
						 
						 var ciudadfed = primeraEntidad.find('ciudadDTO').find('ciudadId').text();
						 $('#cbxCiudadNotif').find("option[value='"+ciudadfed+"']").attr("selected","selected");
						 //onSelectChangeCiudadMunicipioTipoAsentamientoNotif();			 
						 
						 var municipio=primeraEntidad.find('municipioDTO').find('municipioId').text();
						 $('#cbxDelegacionMunicipioNotif').find("option[value='"+municipio+"']").attr("selected","selected");
						 //onSelectChangeDelgMunNotif();
						 
						 var asent=primeraEntidad.find('asentamientoId').text();
						 cleanCombosDependsDelegMunNotif();
						 $(xml).find('asentamiento').each(function(){			
								$('#cbxAsentamientoColoniaNotif').append('<option value="' + $(this).find('asentamientoId').text() + '">' + $(this).find('nombreAsentamiento').text() + '</option>');	
						 });
						 $('#cbxAsentamientoColoniaNotif').find("option[value='"+asent+"']").attr("selected","selected");
						 
						 var tipoAsentamiento=primeraEntidad.find('tipoAsentamientoDTO').find('tipoAsentamientoId').text();
						 $('#cbxTipoAsentamientoNotif').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
						 
						 var sectorDef = primeraEntidad.find('sectorDef').text();
						 $('#sectorNotif').val(sectorDef);
				  }
			});
				  
	  } --%>

	  
	

	  
	function  pintaDatosDefuncion(xml){
		
		/* id0=$(xml).find('datosDefuncion').find('domicilioDefuncion').find('municipioDTO').find('entidadFederativaDTO').find('valorIdPais').find('idCampo').first().text();	
		 
		 if (id0==null || id0.length==0) {
			 id0=$(xml).find('datosDefuncion').find('domicilioDefuncion').find('ciudadDTO').find('valorIdPais').find('idCampo').first().text();			 
		 }
		 if (id0==null || id0.length==0) {
			 id0=$(xml).find('datosDefuncion').find('domicilioDefuncion').find('paisValor').find('idCampo').first().text();
		 }
		 if (id0==null || id0.length==0) {
			 id0=$(xml).find('datosDefuncion').find('domicilioDefuncion').find('entidadDTO').find('valorIdPais').find('idCampo').first().text();
		 } */
		
//		 $('#areaCalleDef').val($(xml).find('datosDefuncion').find('domicilioDefuncion').find('calle').text());
//		 $('#areaNumeroExteriorDef').val($(xml).find('datosDefuncion').find('domicilioDefuncion').find('numeroExterior').text());
//		 $('#areaNumeroInteriorDef').val($(xml).find('datosDefuncion').find('domicilioDefuncion').find('numeroInterior').text());
//		 $('#areaEntreCalleDef').val($(xml).find('datosDefuncion').find('domicilioDefuncion').find('entreCalle1').text());
//		 $('#areaYCalleDef').val($(xml).find('datosDefuncion').find('domicilioDefuncion').find('entreCalle2').text());
//		 $('#areaAliasDef').val($(xml).find('datosDefuncion').find('domicilioDefuncion').find('alias').text());
//		 $('#areaEdificioDef').val($(xml).find('datosDefuncion').find('domicilioDefuncion').find('edificio').text());
//		 $('#areaReferenciasDef').val($(xml).find('datosDefuncion').find('domicilioDefuncion').find('referencias').text());		 
		// Si es Mexico se llenan los combos
		 /* if(id0=="10"){
				 $('#cbxPaisDef').find("option[value='"+id0+"']").attr("selected","selected");
				 onSelectChangePaisDef();
				 //entidadFederativaId
				 
				 var entidadFede=$(xml).find('datosDefuncion').find('domicilioDefuncion').find('entidadDTO').find('entidadFederativaId').text();				 
				 $('#cbxEntFederativaDef').find("option[value='"+entidadFede+"']").attr("selected","selected");
				 onSelectChangeEntFedDef();
				 
				 var ciudadfed=$(xml).find('datosDefuncion').find('domicilioDefuncion').find('ciudadDTO').find('ciudadId').first().text();				 
				 if (ciudadfed==null || ciudadfed.length==0) {					 
					 ciudadfed=$(xml).find('datosDefuncion').find('domicilioDefuncion').find('asentamientoDTO').find('ciudadDTO').find('ciudadId').text();
				 }				 
				 $('#cbxCiudadDef').find("option[value='"+ciudadfed+"']").attr("selected","selected");
				 onSelectChangeCiudadMunicipioTipoAsentamientoDef();
				 
				 
				 var municipio=$(xml).find('datosDefuncion').find('domicilioDefuncion').find('municipioDTO').find('municipioId').first().text();
				 if (municipio==null || municipio.length==0){					 
					 municipio==$(xml).find('datosDefuncion').find('domicilioDefuncion').find('asentamientoDTO').find('municipioDTO').find('municipioId').text();
				 }
				 
				 $('#cbxDelegacionMunicipioDef').find("option[value='"+municipio+"']").attr("selected","selected");				 
				 onSelectChangeDelgMunDef();
				 
				 var colonia=$(xml).find('datosDefuncion').find('domicilioDefuncion').find('asentamientoDTO').find('asentamientoId').text();
				 $('#cbxAsentamientoColoniaDef').find("option[value='"+colonia+"']").attr("selected","selected");
				 
				 var tipoAsentamiento=$(xml).find('datosDefuncion').find('domicilioDefuncion').find('asentamientoDTO').find('tipoAsentamientoDTO').find('tipoAsentamientoId').text();
				 $('#cbxTipoAsentamientoDef').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
				 
				//sector
				 var sectorDef=$(xml).find('datosDefuncion').find('domicilioDefuncion').find('asentamientoDTO').find('sector').text();
				 $('#sectorDef').val(sectorDef);
				 
				 var tipocalle=$(xml).find('datosDefuncion').find('domicilioDefuncion').find('valorCalleId').find('idCampo').text();
				 $('#cbxTipoCalleDef').find("option[value='"+tipocalle+"']").attr("selected","selected");
				 
				 $('#codigoPostalDef').val($(xml).find('datosDefuncion').find('domicilioDefuncion').find('asentamientoDTO').find('codigoPostal').text());

 				$('#codigoPostalButtonDef').hide();
 				
 			// De lo contrario se llenan los textarea
			} else if (id0.length>0) { */
				
//				$('#cbxPaisDef').find("option[value='"+id0+"']").attr("selected","selected");
				$('#entidadFederativaDef').val($(xml).find('datosDefuncion').find('domicilioDefuncion').find('estado').text());
//				$('#areaCiudadDef').val($(xml).find('datosDefuncion').find('domicilioDefuncion').find('ciudad').text());
				$('#areaDelegacionMunicipioDef').val($(xml).find('datosDefuncion').find('domicilioDefuncion').find('municipio').text());
				$('#areaColoniaDef').val($(xml).find('datosDefuncion').find('domicilioDefuncion').find('asentamientoExt').text());
//				$('#codigoPostalDef').val($(xml).find('datosDefuncion').find('domicilioDefuncion').find('codigoPostal').text());
				$('#entidadFederativaDenuncia').val($(xml).find('datosDefuncion').find('domicilioDefDenuncia').find('estado').text());
				$('#areaDelegacionMunicipioDenuncia').val($(xml).find('datosDefuncion').find('domicilioDefDenuncia').find('municipio').text());
				$('#areaColoniaDenuncia').val($(xml).find('datosDefuncion').find('domicilioDefDenuncia').find('asentamientoExt').text());
				
				hideControlsDef("no");
//			}
		
		 $('#folioDefuncion').val($(xml).find('datosDefuncion').find('folioDefuncion').text());
		 
		 var fechaAv = $(xml).find('datosDefuncion').find('fechaAveriguacion').text();
		 if( fechaAv != null && fechaAv.length>0){
			 var fchAv = fechaAv.split(' ');
			 var fchAvBien = fchAv[0].split('-');
			 $('#fechaAveriguacion').val(fchAvBien[2]+"/"+fchAvBien[1]+"/"+fchAvBien[0]);	 
		 }
		 
		 var fechaDef = $(xml).find('datosDefuncion').find('fechaDefuncion').text();
		 if( fechaDef != null && fechaDef.length>0){
			 var fchDef = fechaDef.split(' ');
			 var fchDefBien = fchDef[0].split('-');
			 $('#fechaDefuncion').val(fchDefBien[2]+"/"+fchDefBien[1]+"/"+fchDefBien[0]);
		 }
		 
		 //tipo defuncion
		 var tipoDef = $(xml).find('datosDefuncion').find('tipoDefuncion').find('idCampo').text();
		 $('#cbxTipoDefuncion').find("option[value='"+tipoDef+"']").attr("selected","selected");
		 //certificada por
		 var certPor = $(xml).find('datosDefuncion').find('certificadaPor').find('idCampo').text();
		 $('#cbxCertificadoPor').find("option[value='"+certPor+"']").attr("selected","selected");
		 //sitio de defuncion
		 var sitioDef = $(xml).find('datosDefuncion').find('sitioDefuncion').find('idCampo').text();
		 $('#cbxSitioDefuncion').find("option[value='"+sitioDef+"']").attr("selected","selected");
		 //sitio de lesion
		 var sitioLesion = $(xml).find('datosDefuncion').find('sitioLesion').find('idCampo').text();
		 $('#cbxSitioLesion').find("option[value='"+sitioLesion+"']").attr("selected","selected");
		 //fue en el trabajo
		 var fueEnTrabajo = $(xml).find('datosDefuncion').find('fueEnTrabajo').find('idCampo').text();
		 $('#cbxFueEnTrabajo').find("option[value='"+fueEnTrabajo+"']").attr("selected","selected");
		 //agente externo
		 $('#areaAgenteExterno').val($(xml).find('datosDefuncion').find('agenteExterno').text());
		 //tipo evento defuncion
		 var tipoEventoDef = $(xml).find('datosDefuncion').find('tipoEventoDefuncion').find('idCampo').text();
		 $('#cbxTipoEvento').find("option[value='"+tipoEventoDef+"']").attr("selected","selected");
		 //tipo de victima
		 var tipoVictima = $(xml).find('datosDefuncion').find('tipoVictima').find('idCampo').text();
		 $('#cbxTipoVictima').find("option[value='"+tipoVictima+"']").attr("selected","selected");
		 //tipo de arma
		 var tipoArma = $(xml).find('datosDefuncion').find('tipoArma').find('idCampo').text();
		 $('#cbxTipoArma').find("option[value='"+tipoArma+"']").attr("selected","selected");
		 //causa A
		 $('#areaCausaA').val($(xml).find('datosDefuncion').find('causaA').text());
		 //duracion A
		 $('#areaDuracionA').val($(xml).find('datosDefuncion').find('duracionA').text());
		 //causa B
		 $('#areaCausaB').val($(xml).find('datosDefuncion').find('causaB').text());
		 //duracion B
		 $('#areaDuracionB').val($(xml).find('datosDefuncion').find('duracionB').text());
		 //causa C
		 $('#areaCausaC').val($(xml).find('datosDefuncion').find('causaC').text());
		 //duracion C
		 $('#areaDuracionC').val($(xml).find('datosDefuncion').find('duracionC').text());
		 //causa D
		 $('#areaCausaD').val($(xml).find('datosDefuncion').find('causaD').text());
		 //duracion D
		 $('#areaDuracionD').val($(xml).find('datosDefuncion').find('duracionD').text());
		 //estado patologico
		 $('#areaEdoPatologico').val($(xml).find('datosDefuncion').find('edoPatologico').text());
		 //duracion patologico
		 $('#areaDuracionPatologico').val($(xml).find('datosDefuncion').find('duracionPatologico').text());
		 // condicion embarazo
		 var condicionEmb = $(xml).find('datosDefuncion').find('condicionEmbarazo').find('idCampo').text();
		 $('#cbxCondicionEmbarazo').find("option[value='"+condicionEmb+"']").attr("selected","selected");
		 // estaba en periodo de posparto
		 var periodoPosparto = $(xml).find('datosDefuncion').find('periodoPosparto').find('idCampo').text();
		 $('#cbxPeriodoPosparto').find("option[value='"+periodoPosparto+"']").attr("selected","selected");
		 
		
	}


			

	function deshabilitaDatosDefuncion(){
		/*  $('#cbxPaisDef').attr("disabled","disabled");
		 $('#codigoPostalDef').attr("disabled","disabled");
		 $('#cbxEntFederativaDef').attr("disabled","disabled");
		 $('#cbxCiudadDef').attr("disabled","disabled");
		 $('#cbxDelegacionMunicipioDef').attr("disabled","disabled");
		 $('#cbxAsentamientoColoniaDef').attr("disabled","disabled");
		 $('#sectorDef').attr("disabled","disabled");
		 $('#cbxTipoAsentamientoDef').attr("disabled","disabled");
		 $('#cbxTipoCalleDef').attr("disabled","disabled"); */
		 $('#entidadFederativaDef').attr("disabled","disabled");
//		 $('#areaCiudadDef').attr("disabled","disabled");
		 $('#areaDelegacionMunicipioDef').attr("disabled","disabled");
		 $('#areaColoniaDef').attr("disabled","disabled");
		 $('#entidadFederativaDenuncia').attr("disabled","disabled");
		 $('#areaDelegacionMunicipioDenuncia').attr("disabled","disabled");
		 $('#areaColoniaDenuncia').attr("disabled","disabled");
		/*  $('#areaAsentamientoDef').attr("disabled","disabled");
		 $('#areaTipoCalleDef').attr("disabled","disabled");
		 $('#areaCalleDef').attr("disabled","disabled");
		 $('#areaNumeroExteriorDef').attr("disabled","disabled");
		 $('#areaNumeroInteriorDef').attr("disabled","disabled");
		 $('#areaReferenciasDef').attr("disabled","disabled");
		 $('#areaEntreCalleDef').attr("disabled","disabled");
		 $('#areaYCalleDef').attr("disabled","disabled");
		 $('#areaAliasDef').attr("disabled","disabled");
		 $('#areaEdificioDef').attr("disabled","disabled");

		 $('#codigoPostalButtonDef').hide(); */
		 
		 $('#folioDefuncion').attr("disabled","disabled");
		 $('#fechaAveriguacion').attr("disabled","disabled");
		 $('#fechaAveriguacion').datepicker('disable');
		 $('#fechaDefuncion').attr("disabled","disabled");
		 $('#fechaDefuncion').datepicker('disable');
		 $('#cbxTipoDefuncion').attr("disabled","disabled");
		 $('#cbxCertificadoPor').attr("disabled","disabled");
		 $('#cbxSitioDefuncion').attr("disabled","disabled");
		 $('#cbxSitioLesion').attr("disabled","disabled");
		 $('#cbxFueEnTrabajo').attr("disabled","disabled");
		 $('#areaAgenteExterno').attr("disabled","disabled");
		 $('#cbxTipoEvento').attr("disabled","disabled");
		 $('#cbxTipoVictima').attr("disabled","disabled");
		 $('#cbxTipoArma').attr("disabled","disabled");
		 
		 $('#areaCausaA').attr("disabled","disabled");
		 $('#areaDuracionA').attr("disabled","disabled");
		 $('#areaCausaB').attr("disabled","disabled");
		 $('#areaDuracionB').attr("disabled","disabled");
		 $('#areaCausaC').attr("disabled","disabled");
		 $('#areaDuracionC').attr("disabled","disabled");
		 $('#areaCausaD').attr("disabled","disabled");
		 $('#areaDuracionD').attr("disabled","disabled");
		 $('#areaEdoPatologico').attr("disabled","disabled");
		 $('#areaDuracionPatologico').attr("disabled","disabled");
		 $('#cbxCondicionEmbarazo').attr("disabled","disabled");
		 $('#cbxPeriodoPosparto').attr("disabled","disabled");
	}

	function habilitaDatosDefuncion(){
		/* $('#codigoPostalButtonDef').show();
		 $('#cbxPaisDef').attr("disabled","");
		 $('#codigoPostalDef').attr("disabled","");
		 $('#cbxEntFederativaDef').attr("disabled","");
		 $('#cbxCiudadDef').attr("disabled","");
		 $('#cbxDelegacionMunicipioDef').attr("disabled","");
		 $('#cbxAsentamientoColoniaDef').attr("disabled","");
		 $('#sectorDef').attr("disabled","disabled");
		 $('#cbxTipoAsentamientoDef').attr("disabled","disabled");
		 $('#cbxTipoCalleDef').attr("disabled",""); */
		 $('#entidadFederativaDef').attr("disabled","");
//		 $('#areaCiudadDef').attr("disabled","");
		 $('#areaDelegacionMunicipioDef').attr("disabled","");
		 $('#areaColoniaDef').attr("disabled","");
		 $('#entidadFederativaDenuncia').attr("disabled","");
		 $('#areaDelegacionMunicipioDenuncia').attr("disabled","");
		 $('#areaColoniaDenuncia').attr("disabled","");
		 /* $('#areaAsentamientoDef').attr("disabled","");
		 $('#areaTipoCalleDef').attr("disabled","");
		 $('#areaCalleDef').attr("disabled","");
	 	 $('#areaNumeroExteriorDef').attr("disabled","");
		 $('#areaNumeroInteriorDef').attr("disabled","");
		 $('#areaReferenciasDef').attr("disabled","");
		 $('#areaEntreCalleDef').attr("disabled","");
		 $('#areaYCalleDef').attr("disabled","");
		 $('#areaAliasDef').attr("disabled","");
		 $('#areaEdificioDef').attr("disabled","");
		 $("#codigoPostalButtonDef").show(); */
		 
		 $('#folioDefuncion').attr("disabled","");
		 $('#fechaAveriguacion').attr("disabled","");
		 $('#fechaAveriguacion').datepicker('enable');
		 $('#fechaDefuncion').attr("disabled","");
		 $('#fechaDefuncion').datepicker('enable');		 
		 $('#cbxTipoDefuncion').attr("disabled","");
		 $('#cbxCertificadoPor').attr("disabled","");
		 $('#cbxSitioDefuncion').attr("disabled","");
		 $('#cbxSitioLesion').attr("disabled","");
		 $('#cbxFueEnTrabajo').attr("disabled","");
		 $('#areaAgenteExterno').attr("disabled","");
		 $('#cbxTipoEvento').attr("disabled","");
		 $('#cbxTipoVictima').attr("disabled","");
		 $('#cbxTipoArma').attr("disabled","");
		 
		 $('#areaCausaA').attr("disabled","");
		 $('#areaDuracionA').attr("disabled","");
		 $('#areaCausaB').attr("disabled","");
		 $('#areaDuracionB').attr("disabled","");
		 $('#areaCausaC').attr("disabled","");
		 $('#areaDuracionC').attr("disabled","");
		 $('#areaCausaD').attr("disabled","");
		 $('#areaDuracionD').attr("disabled","");
		 $('#areaEdoPatologico').attr("disabled","");
		 $('#areaDuracionPatologico').attr("disabled","");
		 $('#cbxCondicionEmbarazo').attr("disabled","");
		 $('#cbxPeriodoPosparto').attr("disabled","");
	}
 
	  function  pintaDatosDomicilioHechoDef(xml){
		  	
		    $('#cbxPaisDef').find("option[value='10']").attr("selected","selected"); 
		    onSelectChangePaisDef();
		    $('#cbxPaisDef').attr("disabled","disabled");
		    
		     $('#areaCalleDef').val($(xml).find('lugar').find('calle').text());
			 $('#areaNumeroExteriorDef').val($(xml).find('lugar').find('numeroExterior').text());
			 $('#areaNumeroInteriorDef').val($(xml).find('lugar').find('numeroInterior').text());
			 $('#areaEntreCalleDef').val($(xml).find('lugar').find('entreCalle1').text());
			 $('#areaYCalleDef').val($(xml).find('lugar').find('entreCalle2').text());
			 $('#areaAliasDef').val($(xml).find('lugar').find('alias').text());
			 $('#areaEdificioDef').val($(xml).find('lugar').find('edificio').text());
			 $('#areaReferenciasDef').val($(xml).find('lugar').find('referencias').text());			 			 
			 
			 //$('#cbxAsentamientoColoniaDef').unbind('change');
			 			 
			 //entidadFederativaId
			 var entidadFede=$(xml).find('lugar').find('entidadDTO').find('entidadFederativaId').text();
			 $('#cbxEntFederativaDef').find("option[value='"+entidadFede+"']").attr("selected","selected");
			 onSelectChangeEntFedDef();
			 
			//ciudadId
			 var ciudad=$(xml).find('lugar').find('asentamientoDTO').find('ciudadDTO').find('ciudadId').text();
			 if (ciudad==null || ciudad.length==0) {				 
				 ciudad=$(xml).find('lugar').find('ciudadDTO').find('ciudadId').text();
			 }				
			 $('#cbxCiudadDef').find("option[value='"+ciudad+"']").attr("selected","selected");
			 //onSelectChangeCiudad();
			 
			 //municipioID
			 var municipio=$(xml).find('lugar').find('asentamientoDTO').find('municipioDTO').find('municipioId').text();
			 if (municipio==null || municipio.length==0) {
				 municipio=$(xml).find('lugar').find('municipioDTO').find('municipioId').text(); 				 
			 }			 			 
			 $('#cbxDelegacionMunicipioDef').find("option[value='"+municipio+"']").attr("selected","selected");
			 onSelectChangeDelgMunDef();
			 
			//asentamiento
			 var asentamiento=$(xml).find('lugar').find('asentamientoDTO').find('asentamientoId').text();
			 $('#cbxAsentamientoColoniaDef').find("option[value='"+asentamiento+"']").attr("selected","selected");
			 
			//sector
			 var sectorDef=$(xml).find('asentamientoDTO').find('sector').text();
			 $('#sectorDef').val(sectorDef);  
			 
			 //tipo de asentamiento
			 var tipoAsentamiento=$(xml).find('lugar').find('asentamientoDTO').find('tipoAsentamientoDTO').find('tipoAsentamientoId').text();
			 $('#cbxTipoAsentamientoDef').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
			 
			 $('#codigoPostalDef').val($(xml).find('lugar').find('asentamientoDTO').find('codigoPostal').text());
			 
			 //tipo de calle
			 var tipoCalle=$(xml).find('lugar').find('valorCalleId').find('idCampo').text();
			 $('#cbxTipoCalleDef').find("option[value='"+tipoCalle+"']").attr("selected","selected");
			 
			 $("#cbxAsentamientoColoniaDef").change(muestraCPDomicilio);
			 seteaDatosCoordenadasGPSHecho(xml);
		}

	 
	  function  pintaDatosDomicilioAvisoPosHechoDef(xml){
		  	
		    $('#cbxPaisDef').find("option[value='10']").attr("selected","selected"); 
		    onSelectChangePaisDef();
		    $('#cbxPaisDef').attr("disabled","disabled");
		    
		     $('#areaCalleDef').val($(xml).find('calle').text());
			 $('#areaNumeroExteriorDef').val($(xml).find('numeroExterior').text());
			 $('#areaNumeroInteriorDef').val($(xml).find('numeroInterior').text());
			 $('#areaEntreCalleDef').val($(xml).find('entreCalle1').text());
			 $('#areaYCalleDef').val($(xml).find('entreCalle2').text());
			 $('#areaAliasDef').val($(xml).find('alias').text());
			 $('#areaEdificioDef').val($(xml).find('edificio').text());
			 $('#areaReferenciasDef').val($(xml).find('referencias').text());
			 
			  //$('#codigoPostalDef').val($(xml).find('lugar').find('asentamientoDTO').find('codigoPostalDef').text());

			 //entidadFederativaId
			 var entidadFede=$(xml).find('entidadDTO').find('entidadFederativaId').text();
			 $('#cbxEntFederativaDef').find("option[value='"+entidadFede+"']").attr("selected","selected");
			 onSelectChangeEntFedDef();
			 
			//ciudadId
			 var ciudad=$(xml).find('ciudadDTO').find('ciudadId').text();
			 $('#cbxCiudadDef').find("option[value='"+ciudad+"']").attr("selected","selected");
			 //onSelectChangeCiudad();
			 
			 //municipioID
			 var municipio=$(xml).find('municipioDTO').find('municipioId').text();
			 $('#cbxDelegacionMunicipioDef').find("option[value='"+municipio+"']").attr("selected","selected");
			 onSelectChangeDelgMunDef();
			 
			//asentamiento
			 var asentamiento=$(xml).find('asentamientoDTO').find('asentamientoId').text();
			 $('#cbxAsentamientoColoniaDef').find("option[value='"+asentamiento+"']").attr("selected","selected");			 

			//sector
			 var sectorDef=$(xml).find('asentamientoDTO').find('sector').text();
			 $('#sectorDef').val(sectorDef);
			/*  $('#sectorDef').attr("disabled","disabled"); */
			 
			 //tipo de asentamiento
			 var tipoAsentamiento=$(xml).find('lugar').find('asentamientoDTO').find('tipoAsentamientoDTO').find('tipoAsentamientoId').text();
			 $('#cbxTipoAsentamientoDef').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
			 
			 
			 //tipo de calle
			 var tipoCalle=$(xml).find('lugar').find('valorCalleId').find('idCampo').text();
			 $('#cbxTipoCalleDef').find("option[value='"+tipoCalle+"']").attr("selected","selected");
			 
			 $("#codigoPostalButtonDef").attr("disabled","disabled");
		}
				
	  
	  	function bloqueaCamposDomicilioHecho(bandera)
	  	{
	  		/* if(parseInt(bandera)==0)
	  		{
		  		$('#cbxPaisDef').attr("disabled","disabled");
			    $('#areaCalleDef').attr("disabled","disabled");
				$('#areaNumeroExteriorDef').attr("disabled","disabled");
				$('#areaNumeroInteriorDef').attr("disabled","disabled");
				$('#areaEntreCalleDef').attr("disabled","disabled");
				$('#areaYCalleDef').attr("disabled","disabled");
				$('#areaAliasDef').attr("disabled","disabled");
				$('#areaEdificioDef').attr("disabled","disabled");
				$("#areaReferenciasDef").attr("disabled","disabled");
				$('#cbxEntFederativaDef').attr("disabled","disabled");
				$('#cbxCiudadDef').attr("disabled","disabled");
				$('#cbxDelegacionMunicipioDef').attr("disabled","disabled");
				$('#cbxAsentamientoColoniaDef').attr("disabled","disabled");
				$('#sectorDef').attr("disabled","disabled");
				$('#cbxTipoAsentamientoDef').attr("disabled","disabled");
				$('#cbxTipoCalleDef').attr("disabled","disabled");
				$('#codigoPostalDef').attr("disabled","disabled");
				$('#codigoPostalButtonDef').attr("disabled","disabled");
	  		}
	  		else
	  		{ */
	  			/* $('#cbxPaisDef').attr("disabled","");
			    $('#areaCalleDef').attr("disabled","");
				$('#areaNumeroExteriorDef').attr("disabled","");
				$('#areaNumeroInteriorDef').attr("disabled","");
				$('#areaEntreCalleDef').attr("disabled","");
				$('#areaYCalleDef').attr("disabled","");
				$('#areaAliasDef').attr("disabled","");
				$('#areaEdificioDef').attr("disabled","");
				$("#areaReferenciasDef").attr("disabled",""); */
				$('#cbxEntFederativaDef').attr("disabled","");
//				$('#cbxCiudadDef').attr("disabled","");
				$('#cbxDelegacionMunicipioDef').attr("disabled","");
				$('#cbxAsentamientoColoniaDef').attr("disabled","");
				$('#entidadFederativaDenuncia').attr("disabled","");
				$('#areaDelegacionMunicipioDenuncia').attr("disabled","");
				$('#areaColoniaDenuncia').attr("disabled","");
				/* $('#sectorDef').attr("disabled","disabled");
				$('#cbxTipoAsentamientoDef').attr("disabled","disabled");
				$('#cbxTipoCalleDef').attr("disabled","");
				$('#codigoPostalDef').attr("disabled","");
				$('#codigoPostalButtonDef').attr("disabled",""); */
//	  		}
	  	}
	  	
	  	/*
	  	*	Funcion para cargar el CP y el sector(solo coahuila) al terminar la seleccion de combos, 
	  	*	se ejecuta al seleccionar el asentamiento/colonia
	  	*/
	  	<%-- function muestraCPDomicilio(){
	  		var codigoPostalDef = $('#codigoPostalDef').val();
	  		var asentamiento = $("#cbxAsentamientoColoniaDef option:selected").val();
	  		var tipoAsentamiento;
				//Se consulta el codigo postal
				$.ajax({
					async: false,													
					type: 'POST',
					url: '<%= request.getContextPath()%>/obtenerCodigoPostalXIdAsentamiento.do',
					data: 'idAsentamiento=' + asentamiento,
					dataType: 'xml',
					success: function(xml){				
						tipoAsentamiento = $(xml).find('asentamiento').find('tipoAsentamientoDTO').find('tipoAsentamientoId').text();						
						$('#codigoPostalDef').val($(xml).find('asentamiento').find('codigoPostal').text());	
						$('#cbxTipoAsentamientoDef').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
						$('#sectorDef').val($(xml).find('asentamiento').find('sector').text());
						
						if(asentamiento == "-1"){
							$('#codigoPostalDef').val("");
							$('#sectorDef').val("");
						}
					}	
				});
	  	} --%>
	  	
	  
	  	
	  	/*
		*Funcuon para acortar el numero de caracteres en un textarea
		*/
		function imposeMaxLength(Object, MaxLen)
		{
		  return (Object.value.length <= MaxLen);
		}
		
		window.onload = function(){
			var selects = document.getElementsByTagName("textarea");
			for (var i = 0; i < selects.length; i++) { 
				if(selects[i].getAttribute("maxlength") > 0){
					selects[i].onkeydown = function(){
		                            if (this.value.length > this.getAttribute("maxlength")) 
		                                this.value = this.value.substring(0, this.getAttribute("maxlength"));
		                        }
		                        selects[i].onblur = function(){
		                            if (this.value.length > this.getAttribute("maxlength")) 
		                                this.value = this.value.substring(0, this.getAttribute("maxlength"));
		                        }
				}
			}
		}
		
		function obtenerParametrosDefuncion(){
			// Lugar de la defuncion
			var parametros = '&folioDefuncion=' + trim($('#folioDefuncion').val());
			 parametros += '&fechaAveriguacion=' +  trim($('#fechaAveriguacion').val());
			 parametros += '&fechaDefuncion=' +  trim($('#fechaDefuncion').val());
			 parametros += '&tipoDefuncion=' +  $('#cbxTipoDefuncion option:selected').val();
			 parametros += '&certificadaPor=' + $('#cbxCertificadoPor option:selected').val();
			 parametros += '&sitioDefuncion=' + $('#cbxSitioDefuncion option:selected').val();
			 parametros += '&sitioLesion=' + $('#cbxSitioLesion option:selected').val();
			 parametros += '&fueEnTrabajo=' + $('#cbxFueEnTrabajo option:selected').val();
			 parametros += '&agenteExterno=' +  trim($('#areaAgenteExterno').val());
			 parametros += '&tipoEventoDefuncion=' + $('#cbxTipoEvento option:selected').val();
			 parametros += '&tipoVictima=' + $('#cbxTipoVictima option:selected').val();
			 parametros += '&tipoArma=' + $('#cbxTipoArma option:selected').val();
			 //domicilio de la defuncion
//			 var IDPAIS_MEXICO = 10;
//	         var idPais = $('#cbxPaisDef option:selected').val();
//			 parametros += '&paisDefuncion=' + idPais;
//			 parametros += '&codigoPostalDefuncion=' + $('#codigoPostalDef').val();
//			 if(idPais==IDPAIS_MEXICO){
//				 parametros += '&entidadFederativaDefuncion=' + $('#cbxEntFederativaDef option:selected').val();
//				 parametros += '&ciudadDefuncion=' + $('#cbxCiudadDef option:selected').val();
//				 parametros += '&delegacionMunicipioDefuncion=' + $('#cbxDelegacionMunicipioDef option:selected').val();
//				 parametros += '&asentamientoColoniaDefuncion=' + $('#cbxAsentamientoColoniaDef option:selected').val();
//				 parametros += '&tipoAsentamientoDefuncion=' + $('#cbxTipoAsentamientoDef option:selected').val();
//				 parametros += '&tipoCalleDefuncion=' + $('#cbxTipoCalleDef option:selected').val();
//			 }else{
				 parametros += '&entidadFederativaDefuncion=' +  trim($('#entidadFederativaDef').val());
//				 parametros += '&ciudadDefuncion=' +  trim($('#areaCiudadDef').val());
				 parametros += '&delegacionMunicipioDefuncion=' +  trim($('#areaDelegacionMunicipioDef').val());
				 parametros += '&asentamientoColoniaDefuncion=' +  trim($('#areaColoniaDef').val());
//				 parametros += '&tipoAsentamientoDefuncion=' +  trim($('#areaAsentamientoDef').val());
//				 parametros += '&tipoCalleDefuncion=' +  trim($('#areaTipoCalleDef').val());
				 parametros += '&entidadFederativaDefDenuncia=' +  trim($('#entidadFederativaDenuncia').val());
				 parametros += '&delegacionMunicipioDefDenuncia=' +  trim($('#areaDelegacionMunicipioDenuncia').val());
				 parametros += '&asentamientoColoniaDefDenuncia=' +  trim($('#areaColoniaDenuncia').val());
//			 }
//			 parametros += '&calleDefuncion=' +  trim($('#areaCalleDef').val());
//			 parametros += '&numExteriorDefuncion=' +  trim($('#areaNumeroExteriorDef').val());
//			 parametros += '&numInteriorDefuncion=' +  trim($('#areaNumeroInteriorDef').val());
//			 parametros += '&referenciasDefuncion=' +  trim($('#areaReferenciasDef').val());
//			 parametros += '&entreCalleDefuncion=' +  trim($('#areaEntreCalleDef').val());
//			 parametros += '&ycalleDefuncion=' +  trim($('#areaYCalleDef').val());
//			 parametros += '&aliasDomicilioDefuncion=' +  trim($('#areaAliasDef').val());
//			 parametros += '&edificioDefuncion=' +  trim($('#areaEdificioDef').val());
			 //causas de la defuncion
			 parametros += '&causaA=' +  trim($('#areaCausaA').val());
			 parametros += '&duracionA=' +  trim($('#areaDuracionA').val());
			 parametros += '&causaB=' +  trim($('#areaCausaB').val());
			 parametros += '&duracionB=' +  trim($('#areaDuracionB').val());
			 parametros += '&causaC=' +  trim($('#areaCausaC').val());
			 parametros += '&duracionC=' +  trim($('#areaDuracionC').val());
			 parametros += '&causaD=' +  trim($('#areaCausaD').val());
			 parametros += '&duracionD=' +  trim($('#areaDuracionD').val());
			 parametros += '&edoPatologico=' +  trim($('#areaEdoPatologico').val());
			 parametros += '&duracionPatologico=' +  trim($('#areaDuracionPatologico').val());
			 parametros += '&condicionEmbarazo=' + $('#cbxCondicionEmbarazo option:selected').val();
			 parametros += '&periodoPosparto=' + $('#cbxPeriodoPosparto option:selected').val();
			 
			 
			 return parametros;
		}
		
   </script>	

 <div id="tabsDef">
    <ul>
        <li id="liDom"><a href="#tabsDef-1" id="hrefIngDomDef">Lugar de la Defunci&#243;n</a></li>
        <li id="liDomNotif"><a href="#tabsDef-2">Domicilios</a></li>
        <li id="liDomNotif"><a href="#tabsDef-3">Causas de la Defunci&#243;n</a></li>
    </ul>
    <div id="tabsDef-1">
      <table width="762px"  height="243px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="100" height="25" align="right" ><strong>Folio Defunci&#243;n:</strong></td>
          <td width="200" height="25">
            <input type="text" id="folioDefuncion" size="20"  tabindex="1" maxlength="10" onkeypress="return solonumeros(event);" onblur="validaSoloNumeros(this);"/>
          </td>
          <td width="140" height="25" align="right" rowspan="3"><strong>Agente Externo:</strong></td>
          <td height="25" rowspan="3">
          	<textarea id="areaAgenteExterno" maxlength="200" cols="49" rows="5" style="width: 276px; height:70px;" tabindex="9"></textarea>
          </td>
        </tr>
        
        <tr>
          <td align="right" height="25"><strong>Fecha Averiguaci&#243;n:</strong></td>
          <td height="25">
            <input type="text" id="fechaAveriguacion" name="fechaAveriguacion" maxlength="10" tabindex="2" readonly="readonly" style="width: 178px;" onKeypress="return bloqueaEnter(event);"/>
          </td>
          
        </tr>
        
        <tr>
          <td height="25" align="right"><strong>Fecha Defunci&#243;n:</strong></td>
          <td height="25">
          	<input type="text" id="fechaDefuncion" name="fechaDefuncion" maxlength="10" tabindex="3" readonly="readonly" style="width: 178px;" onKeypress="return bloqueaEnter(event);"/>
          </td>
          
        </tr>
        
        <tr>
          <td height="25" align="right"><strong>Tipo Defunci&#243;n:</strong></td>
          <td height="25">
            <select id="cbxTipoDefuncion"  style="width:200px;" tabindex="4">
                <option value="-1">-Seleccione-</option>
            </select>
          </td>
          <td height="25" align="right"><strong>Tipo de Evento:</strong></td>
          <td height="25">
          	<select id="cbxTipoEvento"  style="width:200px;" tabindex="10">
                <option value="-1">-Seleccione-</option>
            </select>
          </td>
        </tr>
        
        <tr>
          <td height="25" align="right"><strong>Certificado por:</strong></td>
          <td height="25">
            <select id="cbxCertificadoPor" style="width:200px;" tabindex="5">
                <option value="-1">-Seleccione-</option>
            </select>				
          </td>
          <td height="25" align="right"><strong>Tipo de Victima:</strong></td>
          <td height="25" >
          	<select id="cbxTipoVictima" style="width:200px;" tabindex="11">
                <option value="-1">-Seleccione-</option>
            </select>
          </td>
        </tr>
        
        <tr>
          <td height="25" align="right"><strong>Sitio Defunci&#243;n:</strong></td>
          <td height="25">
            <select id="cbxSitioDefuncion" style="width:200px;" tabindex="6">
                <option value="-1">-Seleccione-</option>
            </select>
          </td>
          <td height="25" align="right"><strong>Tipo de Arma:</strong></td>
          <td height="25">
	         <select id="cbxTipoArma" style="width:200px;" tabindex="12">
	            <option value="-1">-Seleccione-</option>
	         </select>
          </td>
        </tr>
        
        <tr>
        	<td width="135" height="25" align="right"><strong>Sitio de Lesi&#243;n:</strong></td>
          	<td colspan="3" height="25" >
	            <select id="cbxSitioLesion" style="width:200px;" tabindex="7">
	                <option value="-1">-Seleccione-</option>
	            </select>
          	</td>
        </tr>
        
        <tr>
        	<td align="right" height="25"><strong>Fue en el trabajo:</strong></td>
          	<td height="25">
	            <select id="cbxFueEnTrabajo" style="width:200px;" tabindex="8">
	                <option value="-1">-Seleccione-</option>
	            </select>
          	</td>
        </tr>
        
        <tr>
          <td id="rowDomicilioNotifDef" height="25" colspan="2" align="right">&nbsp;</td>
          <td height="25" align="right">&nbsp;</td>
          <td colspan="3" >&nbsp;</td>
        </tr>
      </table>
    </div>
    
    <!-- TAB PARA LOS DOMICILIOS -->
    
    <div id="tabsDef-2">
      <table width="762px"  height="243px" border="0" cellspacing="0" cellpadding="0">
      	<tr>
      		<td style="height:40px;" colspan="2" align="center"><b>DOMICILIO DENUNCIA</b></td>
      		<td colspan="2" align="center"><b>DOMICILIO DEFUNCI&#211;N</b></td>
      	</tr>
        <!-- <tr>
          <td width="158" height="25" align="right" ><strong>Pa&iacute;s:</strong></td>
          <td width="200" height="25">
            <select id="cbxPaisDef" style="width:200px;" tabindex="1">						
                <option value="-1">-Seleccione-</option>
            </select>
          </td>
          <td width="135" height="25" align="right"><strong>Calle o Avenida:</strong></td>
          <td colspan="3" height="25"  align="right">
            <input type="text" id="areaCalleDef" size="49"  tabindex="10" maxlength="50"/>
          </td>
        </tr>
        <tr>
          <td align="right" height="25"><strong>C&oacute;digo Postal:</strong></td>
          <td height="25">
            <input type="text" id="codigoPostalDef" size="8" maxlength="5" tabindex="2" onkeypress="return solonumeros(event);" onblur="validaSoloNumeros(this);"/>
            <input type="button" value="Enviar" id="codigoPostalButtonDef" style="display:;" class="btn_modificar"/>
          </td>
          <td align="right" height="25"><strong>N&uacute;mero Ext.:</strong></td>
          <td width="90" height="25" align="right">
            <input type="text" id="areaNumeroExteriorDef" size="7" maxlength="7" tabindex="11" onkeypress="return numerosYGato(event);"/>
          </td>
          <td width="87" height="25" align="right"><strong>N&uacute;mero Int.:</strong></td>
          <td width="92"  height="25" align="right"><input type="text" id="areaNumeroInteriorDef" size="15" maxlength="12" tabindex="12" onkeypress="return numerosYGato(event);"/></td>
        </tr> -->
        <tr>
          <td height="25" align="right"><strong>Entidad:</strong></td>
          <td height="25"><div id="divCbxEntFederativaDef">
            <select id="cbxEntFederativaDef" style="width:200px;">
                <option value="-1">-Seleccione-</option>
            </select></div>
            <input value="" size="44" maxlength="35" type="text" id="entidadFederativaDef" maxlength="50" tabindex="1"/>
          </td>
          <td height="25" align="right"><strong>Entidad:</strong></td>
          <td height="25">
            <input value="" size="44" maxlength="35" type="text" id="entidadFederativaDenuncia" maxlength="50" tabindex="2"/>
          </td>
          <!-- <td height="25" align="right"><strong>Entre calle:</strong></td>
          <td height="25" colspan="3"  align="right"><input type="text" id="areaEntreCalleDef" size="49" tabindex="13" maxlength="60"/></td> -->
        </tr>
        <!-- <tr>
          <td height="25" align="right"><strong>Ciudad:</strong></td>
          <td height="25"><div id="divCbxCiudadDef">
            <select id="cbxCiudadDef"  style="width:200px;" tabindex="4">
                <option value="-1">-Seleccione-</option>
            </select></div>
            <input type="text" id="areaCiudadDef" maxlength="50"/>
          </td>
          <td height="25" align="right"><strong>y calle:</strong></td>
          <td height="25" colspan="3" align="right"><input type="text" id="areaYCalleDef" size="49" tabindex="14" maxlength="60"/></td>
        </tr> -->
        <tr>
          <td height="25" align="right"><strong>Municipio:</strong></td>
          <td height="25"><div id="divCbxDelegacionMunicipioDef">
            <select id="cbxDelegacionMunicipioDef" style="width:200px;">
                <option value="-1">-Seleccione-</option>
            </select></div>
            <textarea id="areaDelegacionMunicipioDef" maxlength="150" tabindex="3" style="width: 240px; height:70px;"></textarea>					
          </td>
          <td height="25" align="right"><strong>Municipio:</strong></td>
          <td height="25">
            <textarea id="areaDelegacionMunicipioDenuncia" maxlength="150" tabindex="4" style="width: 240px; height:70px;"></textarea>					
          </td>
          <!-- <td height="25" align="right"><strong>Alias:</strong></td>
          <td height="25" colspan="3"  align="right"><input type="text" id="areaAliasDef" size="49" tabindex="15" maxlength="60"/></td> -->
        </tr>
        <tr>
          <td height="25" align="right"><strong>Localidad:</strong></td>
          <td height="25"><div id="divCbxAsentamientoColoniaDef">
            <select id="cbxAsentamientoColoniaDef" style="width:200px;" onchange="muestraCPDomicilio()">
                <option value="-1">-Seleccione-</option>
            </select></div>
            <textarea id="areaColoniaDef" maxlength="150" tabindex="5" style="width: 240px; height:70px;"></textarea>
          </td>
          <td height="25" align="right"><strong>Localidad:</strong></td>
          <td height="25">
            <textarea id="areaColoniaDenuncia" maxlength="150" tabindex="6" style="width: 240px; height:70px;"></textarea>
          </td>
         <!--  <td height="25" align="right"><strong>Edificio:</strong></td>
          <td height="25" colspan="3"  align="right"><input type="text" id="areaEdificioDef" size="49" tabindex="16" maxlength="60"/></td> -->
        </tr>
        
       <!--  <tr id="trSectorDef">
          <td height="25" align="right"><strong>Sector:</strong></td>
          <td height="25">
          	<input type="text" id="sectorDef" maxlength="1"/>
          </td>
        </tr>
        
        <tr>
          <td height="25" align="right"><strong>Tipo de Asentamiento:</strong></td>
          <td height="25"><div id="divCbxTipoAsentamientoDef">
            <select id="cbxTipoAsentamientoDef" style="width:200px;" tabindex="7">
              <option value="-1">-Seleccione-</option>						
            </select></div>
            <input type="text" id="areaAsentamientoDef" readonly="readonly" maxlength="50"/>
          </td>
          <td height="25" align="right"><strong>Referencias:</strong></td>
          <td height="25" colspan="3" align="right" >
            <textarea id="areaReferenciasDef" maxlength="60" cols="49" rows="5" style="width: 276px; height:50px;"  tabindex="17" maxlength="60"></textarea></td>
        </tr>
        <tr>
          <td height="12" align="right"><strong>Tipo de Calle:</strong></td>
          <td height="12"><div id="divCbxTipoCalleDef">
            <select id="cbxTipoCalleDef" style="width:200px;" tabindex="8">
              <option value="-1">-Seleccione-</option>
            </select></div>
            <input type="text" id="areaTipoCalleDef" readonly="readonly" maxlength="50"/>
          </td>   -->
          <%-- <td height="25" rowspan="2" align="right" id="colCoordGPSUnoDef"><strong>Coord. Geogr&aacute;ficas:</strong></td>
          <td height="25" colspan="3" rowspan="2" id="rowCoordGPSDef" align="right">
            <jsp:include page="/WEB-INF/paginas/datosCoordenadasGPSView.jsp" flush="true"></jsp:include>
          </td> --%>
      <!--   </tr> -->
        <!-- <tr>
          <td  id="rowDomicilioNotifDef" height="25" colspan="2" align="right"><strong>&iquest;Mismo domicilio para notificaciones?: </strong>
            <input type="radio" name="rbtMismoDomicilioNotificacionesDef" id="DomicilioNotificacionesSiDef" value="1" checked="checked" tabindex="9"/>
            <label for="DomicilioNotificacionesSi2"><strong>Si</strong></label>
            <input type="radio" name="rbtMismoDomicilioNotificacionesDef" id="DomicilioNotificacionesNoDef" value="0"/>
          <label for="domicilioNotificacionesNo3"><strong>No</strong></label></td>
        </tr> -->
        <tr>
          <td id="rowDomicilioNotifDef" height="25" colspan="2" align="right">&nbsp;</td>
          <td height="25" align="right">&nbsp;</td>
          <td colspan="3" >&nbsp;</td>
        </tr>
      </table>
    </div>
    
    <div id="tabsDef-3">
    	<table border="0" width="762px"  height="243px">
    		<tr>
    			<td align="right" width="130">Causa A:</td>
    			<td width="250">
    				<textarea id="areaCausaA" maxlength="225" 
    						style="width: 250px; height:50px;"  tabindex="1">
    				</textarea>
    			</td>
    			<td align="right" width="120">Causa B:</td>
    			<td width="250">
    				<textarea id="areaCausaB" maxlength="225" 
    						style="width: 250px; height:50px;"  tabindex="3">
    				</textarea>
    			</td>
    		</tr>
    		
    		<tr>
    			<td align="right">Duraci&#243;n A:</td>
    			<td>
    				<input type="text" id="areaDuracionA" maxlength="15" tabindex="2"/>
    			</td>
    			<td align="right">Duraci&#243;n B:</td>
    			<td>
    				<input type="text" id="areaDuracionB" maxlength="15" tabindex="4"/>
    			</td>
    		</tr>
    		
    		<tr>
    			<td align="right">Causa C:</td>
    			<td>
    				<textarea id="areaCausaC" maxlength="225" 
    						style="width: 250px; height:50px;"  tabindex="5">
    				</textarea>
    			</td>
    			<td align="right">Causa D:</td>
    			<td>
    				<textarea id="areaCausaD" maxlength="225" 
    						style="width: 250px; height:50px;"  tabindex="7">
    				</textarea>
    			</td>
    		</tr>
    		
    		<tr>
    			<td align="right">Duraci&#243;n C:</td>
    			<td>
    				<input type="text" id="areaDuracionC" maxlength="15" tabindex="6"/>
    			</td>
    			<td align="right">Duraci&#243;n D:</td>
    			<td>
    				<input type="text" id="areaDuracionD" maxlength="15" tabindex="8"/>
    			</td>
    		</tr>
    		
    		<tr>
    			<td align="right">Estado Patol&#243;gico:</td>
    			<td>
    				<textarea id="areaEdoPatologico" maxlength="40" 
    						style="width: 250px; height:50px;"  tabindex="9">
    				</textarea>
    			</td>
    			<td rowspan="2" colspan="2">
    				<table>
    					<tr>
    						<td colspan="2" align="center"><b>PARA MUJERES DE 10 A 54 A&#209;OS</b></td>
    					</tr>
    					<tr>
    						<td align="right" width="110">Condici&#243;n de Embarazo:</td>
    						<td>
    							<select id="cbxCondicionEmbarazo" style="width:200px;" tabindex="11">
              						<option value="-1">-Seleccione-</option>						
            					</select>
    						</td>
    					</tr>
    					
    					<tr>
    						<td align="right">Estaba en periodo de posparto:</td>
    						<td>
    							<select id="cbxPeriodoPosparto" style="width:200px;" tabindex="12">
              						<option value="-1">-Seleccione-</option>						
            					</select>
    						</td>
    					</tr>
    					
    				</table>
    			</td>
    		</tr>
    		
    		<tr>
    			<td align="right">Duraci&#243;n Patol&#243;gico:</td>
    			<td>
    				<input type="text" id="areaDuracionPatologico" maxlength="15" tabindex="10"/>
    			</td>
    		</tr>
    		
    	</table>	
    </div>
    
</div>
    
	<script type="text/javascript">
	
	
	  /*
	  *Funcion que realiza la carga del combo de tipo de defuncion
	  */
	  function cargaTipoDefuncion() {
		  
		$('#cbxTipoDefuncion').empty();
		$('#cbxTipoDefuncion').append('<option value="-1">-Seleccione-</option>');
		
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarTiposDefuncion.do',
		  data: '',
		  dataType: 'xml',
		  async: false,
		  success: function(xml){
			var option;
			$(xml).find('catTiposDefuncion').each(function(){
				$('#cbxTipoDefuncion').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    });			
		  }
		});
	  }
	
	  /*
	  *Funcion que realiza la carga del combo certificada por
	  */
	  function cargaCertificadaPor() {
		
		$('#cbxCertificadoPor').empty();
		$('#cbxCertificadoPor').append('<option value="-1">-Seleccione-</option>');  
		  
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarCertificadaPor.do',
		  data: '',
		  dataType: 'xml',
		  async: false,
		  success: function(xml){
			var option;
			$(xml).find('catCertificadaPor').each(function(){
				$('#cbxCertificadoPor').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    });			
		  }
		});
	  }
	  
	  /*
	  *Funcion que realiza la carga del combo sitio defuncion
	  */
	  function cargaSitioDefuncion() {
		  
		$('#cbxSitioDefuncion').empty();
		$('#cbxSitioDefuncion').append('<option value="-1">-Seleccione-</option>'); 
		
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarSitioDefuncion.do',
		  data: '',
		  dataType: 'xml',
		  async: false,
		  success: function(xml){
			var option;
			$(xml).find('catSitioDefuncion').each(function(){
				$('#cbxSitioDefuncion').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    });			
		  }
		});
	  }
  
  	  /*
	  *Funcion que realiza la carga del combo sitio de lesion
	  */
	  function cargaSitioLesion() {
		
		$('#cbxSitioLesion').empty();
		$('#cbxSitioLesion').append('<option value="-1">-Seleccione-</option>'); 
  		  
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarSitioLesion.do',
		  data: '',
		  dataType: 'xml',
		  async: false,
		  success: function(xml){
			var option;
			$(xml).find('catSitioLesion').each(function(){
				$('#cbxSitioLesion').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    });			
		  }
		});
	  }
	
  	  /*
	  *Funcion que realiza la carga del combo fue en el trabajo
	  */
	  function cargaFueEnTrabajo() {
  		  
		$('#cbxFueEnTrabajo').empty();
		$('#cbxFueEnTrabajo').append('<option value="-1">-Seleccione-</option>'); 
		
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarFueEnTrabajo.do',
		  data: '',
		  dataType: 'xml',
		  async: false,
		  success: function(xml){
			var option;
			$(xml).find('catFueEnTrabajo').each(function(){
				$('#cbxFueEnTrabajo').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    });			
		  }
		});
	  }
  	  
		
  	  /*
	  *Funcion que realiza la carga del combo tipo de evento
	  */
	  function cargaTipoEvento() {
  		  
		$('#cbxTipoEvento').empty();
		$('#cbxTipoEvento').append('<option value="-1">-Seleccione-</option>'); 
		
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarTipoEvento.do',
		  data: '',
		  dataType: 'xml',
		  async: false,
		  success: function(xml){
			var option;
			$(xml).find('catTipoEvento').each(function(){
				$('#cbxTipoEvento').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    });			
		  }
		});
	  }
  	  
  	  /*
	  *Funcion que realiza la carga del combo tipo de victima
	  */
	  function cargaTipoVictima() {
  		  
		$('#cbxTipoVictima').empty();
		$('#cbxTipoVictima').append('<option value="-1">-Seleccione-</option>'); 
		
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarTipoVictima.do',
		  data: '',
		  dataType: 'xml',
		  async: false,
		  success: function(xml){
			var option;
			$(xml).find('catTipoVictima').each(function(){
				$('#cbxTipoVictima').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    });			
		  }
		});
	  }
	  	 
  	  /*
	  *Funcion que realiza la carga del combo tipo de arma
	  */
	  function cargaTipoArma() {
  		  
		$('#cbxTipoArma').empty();
		$('#cbxTipoArma').append('<option value="-1">-Seleccione-</option>'); 
		
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarTipoArma.do',
		  data: '',
		  dataType: 'xml',
		  async: false,
		  success: function(xml){
			var option;
			$(xml).find('catTipoArma').each(function(){
				$('#cbxTipoArma').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    });			
		  }
		});
	  }
  	  
	  /*
	  *Funcion que realiza la carga del combo condicion del embarazo
	  */
	  function cargaCondicionEmbarazo() {
  		  
		$('#cbxCondicionEmbarazo').empty();
		$('#cbxCondicionEmbarazo').append('<option value="-1">-Seleccione-</option>'); 
		
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarCondicionEmbarazo.do',
		  data: '',
		  dataType: 'xml',
		  async: false,
		  success: function(xml){
			var option;
			$(xml).find('catCondicionEmbarazo').each(function(){
				$('#cbxCondicionEmbarazo').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    });			
		  }
		});
	  }
	  
	  /*
	  *Funcion que realiza la carga del combo estaba en periodo de posparto
	  */
	  function cargaPeriodoPosparto() {
  		  
		$('#cbxPeriodoPosparto').empty();
		$('#cbxPeriodoPosparto').append('<option value="-1">-Seleccione-</option>'); 
		
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarPeriodoPosparto.do',
		  data: '',
		  dataType: 'xml',
		  async: false,
		  success: function(xml){
			var option;
			$(xml).find('catPeriodoPosparto').each(function(){
				$('#cbxPeriodoPosparto').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    });			
		  }
		});
	  }
  	  
		/**
		*Carga los escuchadores de eventos
		*/
//		cargaPaises();					//Carga el combo de paises

		var fechaServidor="";
		var fechaMax="";
		
		fechaServidor= consultaFechaHoraMaximaServer();
		fechaMax=getFechaMaximaServerHechos(fechaServidor);
		
		/*
		*Funcion para cargar el icono de fechas
		*/
		function cargaCalendarioDefuncion(){ 
			$("#fechaAveriguacion").datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '-111:+0',
				maxDate: fechaMax, 
				changeMonth: true,
				changeYear: true,
				showOn: "button",
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true			
			});
			
			$("#fechaDefuncion").datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '-111:+0',
				maxDate: fechaMax, 
				changeMonth: true,
				changeYear: true,
				showOn: "button",
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true			
			});
		}
		
		/*
		*Funcion para traer la fecha y hora del servidor en el formato : YYYY-MM-DD HH:MI:SS
		*/
		function consultaFechaHoraMaximaServer()
		{
			var fecha="";
			   $.ajax({
				     type: 'POST',
				     url: '<%=request.getContextPath()%>/regresaFechaYHoraDelServidor.do',
					 dataType: 'xml',
					 async: false,
					 success: function(xml){
						fecha= $(xml).find('fecha').text();
					  }
					});
			return fecha;
		}
		
		/*
		 * Funcion para regresar la fecha maxima obtenida desde el servidor
		 * fechaCompleta - cadena con el siguiente formato : YYYY-MM-DD HH:MI:SS
		 * regresa una cadena con la fecha en el formato : DD/MM/YYYY
		 */
		function getFechaMaximaServerHechos(fechaCompleta)
		{
			var arrFechaHora=fechaCompleta.split(" ");
			var digitosFecha=arrFechaHora[0].split("-");
			return digitosFecha[2]+'/'+digitosFecha[1]+'/'+digitosFecha[0];
		}

		

	/* 	$("#cbxPaisDef").change(function(e){
			onSelectChangePaisDef();
		}); 

		$("#cbxEntFederativaDef").change(function(e){
			onSelectChangeEntFedDef();
		}); 
		$("#cbxCiudadDef").change(function(e){
			onSelectChangeCiudadMunicipioTipoAsentamientoDef();
		}); 

		$("#cbxDelegacionMunicipioDef").change(function(e){
			onSelectChangeCiudadMunicipioTipoAsentamientoDef();
		});  */


						
//		$("#codigoPostalButtonDef").bind("click",cosultaPorCodigoPostalDef);					//Escuchador para consultar pos c&oacute;digo postal
//		$("#limpiarButton").bind("click",limpiarFormularioDef);							//Funcion para limpiar el formulario
		/**
	 	* Escuchador de evento para el domicilio de notificaciones
	 	*/
//		$("#DomicilioNotificacionesSiDef").bind("click",ocultaDomicilioNotificaciones);
//		$("#DomicilioNotificacionesNoDef").bind("click",muestraDomicilioNotificaciones);
//		$("#codigoPostalButtonNotif").bind("click",cosultaPorCodigoPostalNotif);		
				
		hideControlsDef("no");			//Funciones para esconder los controles 
//		hideControlsNotif("no");	//Funciones para esconder los controles del domicilio de notificaciones

		//Selecciona Mexico por default
//		seleccionaMexicoDef();
		//$('#codigoPostalButtonDef').hide();
		//$("#codigoPostalButtonNotif").hide();
		
		//Selecciona la entidad federativa segun donde se encuentre por default		
//		seleccionaEntidadFedereativaDef();
		
		$("#tabsDef").tabs();
		$("#tabs-puntocarretero").tabs();
	</script>