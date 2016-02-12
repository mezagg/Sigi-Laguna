    <!--CSS, modificada para las tabs del domicilio -->
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estiloTab.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
	
	<%@page import="mx.gob.segob.nsjp.comun.enums.catalogo.EntidadFederativa"%>
	<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
		   
	<script type="text/javascript">	 
	
	<% 
	ConfiguracionDTO cfg=(ConfiguracionDTO) request.getSession().getAttribute("KEY_SESSION_CONFIGURACION_GLOBAL");
	String entidadFederativa=cfg.getEntidadFederativaDespliegueId().toString();
	%>
	
	var entidadFederativa = '<%=entidadFederativa%>';
	
	$(document).ready(function(){			
		jQuery(document).ajaxStop(desbloquearPantalla());		
	});

	function seleccionaEntidadFedereativa(){
		if( entidadFederativa=='<%=EntidadFederativa.ZACATECAS.getValorId()%>'){
			$('#cbxEntFederativa').val('<%=EntidadFederativa.ZACATECAS.getValorId()%>');
			onSelectChangeEntFed();
			$('#cbxEntFederativaNotif').val('<%=EntidadFederativa.ZACATECAS.getValorId()%>');
			onSelectChangeEntFedNotif();
		}else if( entidadFederativa=='<%=EntidadFederativa.YUCATAN.getValorId()%>'){
			$('#cbxEntFederativa').val('<%=EntidadFederativa.YUCATAN.getValorId()%>');
			onSelectChangeEntFed();
			$('#cbxEntFederativaNotif').val('<%=EntidadFederativa.YUCATAN.getValorId()%>');
			onSelectChangeEntFedNotif();
		}
	}
	
	 var id0="0";
	 function seleccionaMexico(){
		$('#cbxPais').val(10);
		onSelectChangePais();
		$('#cbxPaisNotif').val(10);
		onSelectChangePaisNotif();
	 }
	 function cambiaNombreTabIngDomicilio(texto)
	 {
		$("#hrefIngDom").html(texto);	 
	 }
	 
	 /**
	  * Oculta el tab para el dimicilio de notificaciones
	  */
	  function ocultaDomicilioNotificaciones(){
		$("#tabs").tabs("option", "disabled", [1]);		
	  }
	 /**
	  * Oculta el renglon para activar el domicilio de notificaciones
	  * y en las tabs, oculta la ceja del domicilio notificaciiones
	  */
	  function killDomicilioNotificaciones(){
		$('#liDomNotif').hide();
		$('#rowDomicilioNotif').hide();		
	  }
	 
	 /**
	  * Oculta el renglon de las coordenadas geograficas
	  */
	  function killCoordenadasGeograficas(){
		$('#rowCoordGPS').hide();
		$("#colCoordGPSUno").hide();
	  }
			
	 /**
	  * Muestra el tab para el dimicilio de notificaciones
	  */	
	  function muestraDomicilioNotificaciones(){
		$('#tabs').tabs('enable', 1);
	  }
	  /**
	  * Muestra el renglon para activar el domicilio de notificaciones
	  * y en las tabs, oculta la ceja del domicilio notificaciiones
	  */
	  function liveDomicilioNotificaciones(){
		$('#liDomNotif').show();
		$('#rowDomicilioNotif').show();		
	  }

	 /**
	  * Muestra u oculta los combo box's o cajas de texto dependiendo
	  * de el pa&iacute;s seleccionado tiene o no, entidades federativas.
	  * Esto para el domicilio.
	  */	
	  function hideControls(existenEntidades) {
		  
		if(existenEntidades == "si" ){
	      $('#divCbxEntFederativa').show();	
		  $('#divCbxCiudad').show();
		  $('#divCbxDelegacionMunicipio').show();
		  $('#divCbxAsentamientoColonia').show();
		  $('#trSector').hide();
		  $('#divCbxTipoAsentamiento').show();
		  $('#divCbxTipoCalle').show();
		  //se oculto para la presentacion
		  $('#codigoPostalButton').show();
		  $('#entidadFederativa').hide();
		  $('#areaDelegacionMunicipio').hide();
		  $('#areaCiudad').hide();
		  $('#areaColonia').hide();
		  $('#areaAsentamiento').hide();
		  $('#areaTipoCalle').hide();
		}
		else{
	      $('#divCbxEntFederativa').hide();
		  $('#divCbxCiudad').hide();
		  $('#divCbxDelegacionMunicipio').hide();
		  $('#divCbxAsentamientoColonia').hide();
		  $('#divCbxTipoAsentamiento').hide();
		  $('#trSector').hide();
		  $('#divCbxTipoCalle').hide();
		  $('#codigoPostalButton').hide();
		  $('#entidadFederativa').show();
		  $('#areaDelegacionMunicipio').show();
		  $('#areaCiudad').show();
		  $('#areaColonia').show();
		  $('#areaAsentamiento').show();
		  $('#areaTipoCalle').show();
		}			
	  }


	 /**
	  * Muestra u oculta los combo box's o cajas de texto dependiendo
	  * de el pa&iacute;s seleccionado tiene o no, entidades federativas.
	  * Esto para el domicilio.
	  */		
	  function hideControlsNotif(existenEntidades) {
	  
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
	  }


	 /**
	  * Limpia todo el formulario
	  *	(AUN SIN FUNCIONALIDAD)
	  */	
	  function limpiarFormulario(){
		  
		$('#ingresarDomicilioForm').each (function() { this.reset(); });
		cleanAllCombos();
		hideControls("no");
		 $('#cbxPais').find("option[value='-1']").attr("selected","selected");
		$('#codigoPostal').val("");
		$('#areaCalle').val("");
		$('#areaEntreCalle').val("");
		$('#areaYCalle').val("");
		$('#areaAlias').val("");
		$('#areaEdificio').val("");
		$("#areaReferencias").val("");
		$("#areaNumeroExterior").val("");
		$("#areaNumeroInterior").val("");
		$("#sector").val("");
		cleanAllCombosNotif();
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
		$("#sectorNotif").val("");
	  }


	 /**
	  * Limpia los combos:Ent Federativa, Ciudad, DelegacionMunicipio, AsentamientoColonia
	  * Tipo de Asentamiento y Tipo de Calle, para el Domicilio
	  */  
	  function cleanAllCombos(){
		  
		$('#cbxEntFederativa').empty();
		$('#cbxEntFederativa').append('<option value="-1">-Seleccione-</option>');	//Por default seleccione
	//	$('#cbxEntFederativa').multiselect('refresh');
		$('#cbxCiudad').empty();
		$('#cbxCiudad').append('<option value="-1">-Seleccione-</option>');	
	//	$('#cbxCiudad').multiselect('refresh');
		$('#cbxDelegacionMunicipio').empty();
		$('#cbxDelegacionMunicipio').append('<option value="-1">-Seleccione-</option>');
	//	$('#cbxDelegacionMunicipio').multiselect('refresh');
		$('#cbxAsentamientoColonia').empty();
		$('#cbxAsentamientoColonia').append('<option value="-1">-Seleccione-</option>');
	//	$('#cbxAsentamientoColonia').multiselect('refresh');
		$('#cbxTipoAsentamiento').empty();
		$('#cbxTipoAsentamiento').append('<option value="-1">-Seleccione-</option>');
		$('#cbxTipoAsentamiento').attr('disabled', 'disabled');
	//	$('#cbxTipoAsentamiento').multiselect('refresh');
		$('#cbxTipoCalle').empty();
		$('#cbxTipoCalle').append('<option value="-1">-Seleccione-</option>');
	//	$('#cbxTipoCalle').multiselect('refresh');
	  }
	  

	 /**
	  * Limpia los combos:Ent Federativa, Ciudad, DelegacionMunicipio, AsentamientoColonia
	  * Tipo de Asentamiento y Tipo de Calle, para el Domicilio de Notificaciones
	  */  
	  function cleanAllCombosNotif(){

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
		
		
	 /**
	  * Limpia los combos que dependen del combo entidad federativa, para 
	  * el domicilio
	  */  
	  function cleanCombosDependsEntidadFed(){
		$('#cbxCiudad').empty();
		$('#cbxCiudad').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxCiudad').multiselect('refresh');
		$('#cbxDelegacionMunicipio').empty();
		$('#cbxDelegacionMunicipio').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxDelegacionMunicipio').multiselect('refresh');
		$('#cbxAsentamientoColonia').empty();
		$('#cbxAsentamientoColonia').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxAsentamientoColonia').multiselect('refresh');
		$("#sector").val("");
	  }
		
		
	 /**
	  * Limpia los combos que dependen del combo entidad federativa, para 
	  * el domicilio de notificaciones
	  */ 	
	  function cleanCombosDependsEntidadFedNotif(){
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
		

	 /**
	  * Limpia los combos que dependen del combo Ciudad, para 
	  * el domicilio
	  */  	
	  function cleanCombosDependsCiudad(){
		$('#cbxDelegacionMunicipio').empty();
		$('#cbxDelegacionMunicipio').append('<option value="-1">-Seleccione-</option>');	
//		$('#cbxDelegacionMunicipio').multiselect('refresh');
		$('#cbxAsentamientoColonia').empty();
		$('#cbxAsentamientoColonia').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxAsentamientoColonia').multiselect('refresh');
		$("#sector").val("");
		  }


	 /**
	  * Limpia los combos que dependen del combo entidad federativa, para 
	  * el domicilio de notificaciones
	  */ 	
	  function cleanCombosDependsCiudadNotif(){
		$('#cbxDelegacionMunicipioNotif').empty();
		$('#cbxDelegacionMunicipioNotif').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxDelegacionMunicipioNotif').multiselect('refresh');
		$('#cbxAsentamientoColoniaNotif').empty();
		$('#cbxAsentamientoColoniaNotif').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxAsentamientoColoniaNotif').multiselect('refresh');
		$("#sectorNotif").val("");
		  }

	 /**
	  * Limpia los combos que dependen del combo Delegacion/Municipio, para 
	  * el domicilio
	  */ 	
	  function cleanCombosDependsDelegMun(){
		$('#cbxAsentamientoColonia').empty();
		$('#cbxAsentamientoColonia').append('<option value="-1">-Seleccione-</option>');
//		$('#cbxAsentamientoColonia').multiselect('refresh');
		$("#sector").val("");
		  }

	 /**
	  * Limpia los combos que dependen del combo Delegacion/Municipio, para 
	  * el domicilio de notificaciones
	  */ 	
	function cleanCombosDependsDelegMunNotif(){
		$('#cbxAsentamientoColoniaNotif').empty();
		$('#cbxAsentamientoColoniaNotif').append('<option value="-1">-Seleccione-</option>');	
//		$('#cbxAsentamientoColoniaNotif').multiselect('refresh');
		$("#sectorNotif").val("");
  	}

	 /**
	  * Limpia los combos que dependen de la consulta por codigo postal
	  * para el domicilio
	  */ 	
	  function cleanAllCombosCodigoPostal(){
		$('#cbxEntFederativa').empty();
		$('#cbxCiudad').empty();
		$('#cbxDelegacionMunicipio').empty();
		$('#cbxAsentamientoColonia').empty();
		$("#sector").val("");
	  }

	 /**
	  * Limpia los combos que dependen de la consulta por codigo postal
	  * para el domicilio de notificaciones
	  */ 	
	  function cleanAllCombosCodigoPostalNotif(){
		$('#cbxEntFederativaNotif').empty();
		$('#cbxCiudadNotif').empty();
		$('#cbxDelegacionMunicipioNotif').empty();
		$('#cbxAsentamientoColoniaNotif').empty();
		$("#sectorNotif").val("");
	  }
	

	//*****FUNCIONES PARA EL CU INGRESAR DOMICILIO*********//

	/**
	*Funcion que limpia el combo box de asentamiento colonia
	*/
	function cleanComboAsentColonia(){
		$('#cbxAsentamientoColonia').empty();
		$('#cbxAsentamientoColonia').append('<option value="-1">-Seleccione-</option>');
	}

	/**
	*Funcion que limpia el combo box de asentamiento colonia
	*/
	function cleanComboAsentColoniaNotif(){
		$('#cbxAsentamientoColoniaNotif').empty();
		$('#cbxAsentamientoColoniaNotif').append('<option value="-1">-Seleccione-</option>');
	  }
	
	/*
	*Funcion que realiza la carga del combo de Paises
	*/
	function cargaPaises() {
		 
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarPaises.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catPaises').each(function(){
					$('#cbxPais').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}


	/*
	*Funcion que realiza la carga del combo de Paises, para el Domicilio de notificaciones	
	*/
	function cargaPaisesNotif() {
		  
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
	  }

	
	/**
	* Si existe un cambio en el combo de paises se realiza la consulta de 
	* entidades federativas, y si la consulta es NO vac&iacute;a se leventa la 
	* bandera para mostrar los combo box. Esto para el domicilio
	*/ 	
	function onSelectChangePais() {
		  
		var selected = $("#cbxPais option:selected");
		var existenEntidades = "no";
			
				
		cleanAllCombos();							//Limpia todos los combo box큦		
		hideControls(existenEntidades);				//Si la opcion seleccionada no contiene entidades federativas se esconden los cbx's
		$.ajax({
			async: false,									// la accion cargar estados y llena el combo con la consulta
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarEntFederativas.do',
			data: 'glCatPaisId=' + selected.val(),	//Parametro para hacer la consulta de Entidades por Id del Pa&iacute;s
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catEntidadesFed').each(function(){
					$('#cbxEntFederativa').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');					
					existenEntidades = "si";	
				});
				hideControls(existenEntidades);	
				cargaTipoAsent();
				cargaTipoCalle();
			}
		});
	}


	/**
	* Si existe un cambio en el combo de paises se realiza la consulta de 
	* entidades federativas, y si la consulta es NO vac&iacute;a se leventa la 
	* bandera para mostrar los combo box. Esto para el domicilio de Notificaciones
	*/ 	
	function onSelectChangePaisNotif() {
	  
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
	}

		  
	/**
	* Si existe un cambio en el combo de Entidades federativas se realiza la consulta de 
	* entidades Ciudades. Esto para el domicilio
	*/ 	
	function onSelectChangeEntFed() {
			  
		var selected = $("#cbxEntFederativa option:selected");
		
		cleanCombosDependsEntidadFed();
		$.ajax({											// la accion cargar cidades
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarCiudades.do',
			data: 'glCatEntidadFederativaId=' + selected.val(), 	//hace la consulta por el id de la Entidad Federativa
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catCiudades').each(function(){											//LLena el comboBox con la consulta
					$('#cbxCiudad').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');					
				});
				
			}
		});
		
		if(selected.val() == entidadFederativa){
			$('#trSector').show();
			$('#sector').attr("disabled","disabled");
		}else{
			$('#trSector').hide();
		}
		
		onSelectChangeCiudad();				
	}


	/**
	* Si existe un cambio en el combo de Entidades federativas se realiza la consulta de 
	* entidades Ciudades. Esto para el domicilio de Notificaciones
	*/ 	
	function onSelectChangeEntFedNotif() {
	  
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
				
				if(selected.val() == entidadFederativa){
					$('#trSectorNotif').show();
					$('#sectorNotif').attr("disabled","disabled");
				}else{
					$('#trSectorNotif').hide();
				}
				
		  	}
		});
		onSelectChangeCiudadNotif();			
	}


	/**
	* Si existe un cambio en el combo de Ciudades se realiza la consulta de 
	* Delegaciones/Municipios.
	*/ 	
	function onSelectChangeCiudad() {
			    
		var selected = $("#cbxEntFederativa option:selected");
		  
		//cleanCombosDependsCiudad();
		$.ajax({
			async: false,														// Ejecuta la accion cargar Delegacion/Municipio
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarDelgMun.do',
			data: 'glCatEntidadFederativaId=' + selected.val(), 				//hace la consulta por el id de la Entidad Federativa
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catDelegMun').each(function(){				//LLena el comboBox con la consulta
					$('#cbxDelegacionMunicipio').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');						
				});
			}	
		});
	}	  

	
	/**
	* Si existe un cambio en el combo de Ciudades se realiza la consulta de 
	* Delegaciones/Municipios. Esto para el domicilio de notificaciones
	*/ 	
	function onSelectChangeCiudadNotif() {
		
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


	/**
	* Si existe un cambio en el combo de Ciudade, delegacion, o tipo de asentamiento
	* se realiza la consulta de por medio de esos tres id큦 para cargar el asentamiento/colonia
	*/ 	
	function onSelectChangeCiudadMunicipioTipoAsentamiento() {

		var parametrosConsulta ='';
			  
		parametrosConsulta += 'glDelgMunId='+ $("#cbxDelegacionMunicipio option:selected").val();
		parametrosConsulta += '&glCatCiudadId=' + $("#cbxCiudad option:selected").val();
		//parametrosConsulta += '&glCatTipoAsentamientoId=' + $("#cbxTipoAsentamiento option:selected").val();

		cleanComboAsentColonia();
		$.ajax({
			async: false,														// Ejecuta la accion cargar Delegacion/Municipio
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarAsentColonia.do',
			data: parametrosConsulta,
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catAsentColonia').each(function(){			//LLena el comboBox con la consulta
					$('#cbxAsentamientoColonia').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');	
				});
			}
		});
	}

	
	/**
	* Si existe un cambio en el combo de Ciudade, delegacion, o tipo de asentamiento
	* se realiza la consulta de por medio de esos tres id큦 
	* para cargar el asentamiento/colonia, para el domicilio notificaciones
	*/ 	
	function onSelectChangeCiudadMunicipioTipoAsentamientoNotif() {

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
	}

	
	/**
	* Si existe un cambio en el combo de Ciudades se realiza la consulta de 
	* Delegaciones/Municipios.
	*/ 	
	function onSelectChangeDelgMun() {
		  
		var selected = $("#cbxDelegacionMunicipio option:selected");
		    
		cleanCombosDependsDelegMun();
		$.ajax({														// Ejecuta la accion cargar Delegacion/Municipio
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarAsentColonia.do',
			data: 'glDelgMunId=' + selected.val(),						//Realiza la consulta de acuerdo al id de la delegacion o municipio
			dataType: 'xml',
			success: function(xml){
			  $(xml).find('catAsentColonia').each(function(){			//LLena el comboBox con la consulta
				$('#cbxAsentamientoColonia').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');	
//				$('#cbxAsentamientoColonia').multiselect('refresh');
				   });
//			  $('#cbxAsentamientoColonia').multiselect('refresh');
			}
		});
	}

	
	/**
	* Si existe un cambio en el combo de Ciudades se realiza la consulta de 
	* Delegaciones/Municipios. Esto para el domicilio de notificaciones
	*/ 	
	function onSelectChangeDelgMunNotif() {
	  
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
	}		

	
		
	/*
	*Funcion que realiza la carga del combo de tipo de asentamiento	
	*/
	function cargaTipoAsent() {	
	  
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarTipoAsent.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catTipoAsentamiento').each(function(){
					$('#cbxTipoAsentamiento').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');					
				});  
		  	}
		});
	  }


	/*
	*Funcion que realiza la carga del combo de tipo de asentamiento, 
	*para el domicilio de notificaciones
	*/
	function cargaTipoAsentNotif() {
	  
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
	}



		
	
	 /*
	  *Funcion que realiza la carga del combo de tipo de calle,
	  *para el domicilio 
	  */
	  function cargaTipoCalle() {
		
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarTiposDeCalle.do',
		  data: '',
		  dataType: 'xml',
		  success: function(xml){
			var option;
			$(xml).find('catTipoCalle').each(function(){
				$('#cbxTipoCalle').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    });			
		  }
		});
	  }


	 /*
	  *Funcion que realiza la carga del combo de tipo de calle, 
	  *para el domicilio de notificaciones
	  */
	  function cargaTipoCalleNotif() {
	  
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
	  }


	  /*
	   *Funcion que realiza la consulta de los datos
	   *por c&oacute;digo postal
	   */
	  function cosultaPorCodigoPostal(){	  
		var codigoPostal = $("#codigoPostal");
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/ConsultaDatosAsent.do',
		  data: 'codigoPostal='+ codigoPostal.val(),
		  dataType: 'xml',
		  success: function(xml){
			//cleanAllCombosCodigoPostal();
			var oAsentamiento = $(xml);
			primeraEntidad = $(xml).find('asentamiento').first();
	
			 var entidadFede = primeraEntidad.find('municipioDTO').find('entidadFederativaId').text();
			 $('#cbxEntFederativa').find("option[value='"+entidadFede+"']").attr("selected","selected");
			 onSelectChangeEntFed();
			 
			 var ciudadfed = primeraEntidad.find('ciudadDTO').find('ciudadId').text();
			 $('#cbxCiudad').find("option[value='"+ciudadfed+"']").attr("selected","selected");
			 //onSelectChangeCiudadMunicipioTipoAsentamiento();			 
			 
			 var municipio=primeraEntidad.find('municipioDTO').find('municipioId').text();
			 $('#cbxDelegacionMunicipio').find("option[value='"+municipio+"']").attr("selected","selected");
			// onSelectChangeDelgMun();
			var asent=primeraEntidad.find('asentamientoId').text();
			 cleanCombosDependsDelegMun();
			 $(xml).find('asentamiento').each(function(){			
					$('#cbxAsentamientoColonia').append('<option value="' + $(this).find('asentamientoId').text() + '">' + $(this).find('nombreAsentamiento').text() + '</option>');	
			 });
			 $('#cbxAsentamientoColonia').find("option[value='"+asent+"']").attr("selected","selected");
			 
			 var tipoAsentamiento=primeraEntidad.find('tipoAsentamientoDTO').find('tipoAsentamientoId').text();
			 $('#cbxTipoAsentamiento').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
			 
			 var sector = primeraEntidad.find('sector').text();
			 $('#sector').val(sector);
		  }
		});
	  }


	  /*
	   *Funcion que realiza la consulta de los datos
	   *por c&oacute;digo postal, para el domicilio de notificaciones
	   *(SIN FUNCIONALIDAD POR EL MOMENTO)
	   */
	  function cosultaPorCodigoPostalNotif(){
		  var codigoPostal = $("#codigoPostalNotif");	
			$.ajax({
				  type: 'POST',
				  url: '<%= request.getContextPath()%>/ConsultaDatosAsent.do',
				  data: 'codigoPostal='+ codigoPostal.val(),
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
						 
						 var sector = primeraEntidad.find('sector').text();
						 $('#sectorNotif').val(sector);
				  }
			});
				  
	  }

	  
		function  pintaDatosDomicilioPersonaMoral(xml){
			
			 id0=$(xml).find('domicilioDTO').find('ciudadDTO').find('valorIdPais').find('idCampo').text();		
			 $('#areaCalle').val($(xml).find('organizacionDTO').find('domicilioDTO').find('calle').text() + " Nuevo valor");
			 $('#areaNumeroExterior').val($(xml).find('domicilioDTO').find('numeroExterior').text());
			 $('#areaNumeroInterior').val($(xml).find('domicilioDTO').find('numeroInterior').text());
			 $('#areaEntreCalle').val($(xml).find('domicilioDTO').find('entreCalle1').text());
			 $('#areaYCalle').val($(xml).find('domicilioDTO').find('entreCalle2').text());
			 $('#areaAlias').val($(xml).find('domicilioDTO').find('alias').text());
			 $('#areaEdificio').val($(xml).find('domicilioDTO').find('edificio').text());
			 $('#areaReferencias').val($(xml).find('domicilioDTO').find('referencias').text());
			 $('#codigoPostal').val($(xml).find('domicilioDTO').find('asentamientoDTO').find('codigoPostal').text());
			 if($(xml).find('involucradoDTO').find('esMismoDomicilio').text() == "false"){
			 	$("#DomicilioNotificacionesNo").attr('checked','checked')
			 }
			 
			 if(id0!="0"){
					$('#cbxPais').find("option[value='"+id0+"']").attr("selected","selected");
					 onSelectChangePais();
					 var entidadFede=$(xml).find('domicilioDTO').find('entidadDTO').find('entidadFederativaId').text();
					 $('#cbxEntFederativa').find("option[value='"+entidadFede+"']").attr("selected","selected");
					 onSelectChangeEntFed();
					 
					 var ciudadfed=$(xml).find('domicilioDTO').find('ciudadDTO').find('ciudadId').text();
					 $('#cbxCiudad').find("option[value='"+ciudadfed+"']").attr("selected","selected");
					 onSelectChangeCiudadMunicipioTipoAsentamiento();
					 
					 
					 var municipio=$(xml).find('domicilioDTO').find('municipioDTO').find('municipioId').text();
					 $('#cbxDelegacionMunicipio').find("option[value='"+municipio+"']").attr("selected","selected");
					 onSelectChangeDelgMun();
					 
					 var colonia=$(xml).find('domicilioDTO').find('asentamientoDTO').find('asentamientoId').text();
					 $('#cbxAsentamientoColonia').find("option[value='"+colonia+"']").attr("selected","selected");
					 
					//sector
					 var sector=$(xml).find('domicilioDTO').find('asentamientoDTO').find('sector').text();
					 $('#sector').val(sector);
					 
					 var tipoAsentamiento=$(xml).find('domicilioDTO').find('asentamientoDTO').find('tipoAsentamientoDTO').find('tipoAsentamientoId').text();
					 $('#cbxTipoAsentamiento').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
					 
					 var tipocalle=$(xml).find('domicilioDTO').find('valorCalleId').find('idCampo').text();
					 $('#cbxTipoCalle').find("option[value='"+tipocalle+"']").attr("selected","selected");
					 
					 
//					 var longitud=$(xml).find('domicilio').find('longitud').text();
//					 $('#txtFldLongitud').val(longitud);
//					 var latitud=$(xml).find('domicilio').find('latitud').text();
//					 $('#txtFldLatitud2').val(latitud);
	 				pintaDatosCoordenadasGPS(xml);
	 				$('#codigoPostalButton').hide();
				}
				
			 //var idPais = $('#cbxPais option:selected').val();
		}


	  
	function  pintaDatosDomicilio(xml){
		
		id0=$(xml).find('domicilio').find('municipioDTO').find('entidadFederativaDTO').find('valorIdPais').find('idCampo').first().text();	
		 
		 if (id0==null || id0.length==0) {
			 id0=$(xml).find('domicilio').find('ciudadDTO').find('valorIdPais').find('idCampo').first().text();			 
		 }
		 if (id0==null || id0.length==0) {
			 id0=$(xml).find('domicilio').find('paisValor').find('idCampo').first().text();
		 }
		 if (id0==null || id0.length==0) {
			 id0=$(xml).find('domicilio').find('entidadDTO').find('valorIdPais').find('idCampo').first().text();
		 }
		
		 $('#areaCalle').val($(xml).find('domicilio').find('calle').text());
		 $('#areaNumeroExterior').val($(xml).find('domicilio').find('numeroExterior').text());
		 $('#areaNumeroInterior').val($(xml).find('domicilio').find('numeroInterior').text());
		 $('#areaEntreCalle').val($(xml).find('domicilio').find('entreCalle1').text());
		 $('#areaYCalle').val($(xml).find('domicilio').find('entreCalle2').text());
		 $('#areaAlias').val($(xml).find('domicilio').find('alias').text());
		 $('#areaEdificio').val($(xml).find('domicilio').find('edificio').text());
		 $('#areaReferencias').val($(xml).find('domicilio').find('referencias').text());		 
		 if($(xml).find('involucradoDTO').find('esMismoDomicilio').text() == "false"){
		 	$("#DomicilioNotificacionesNo").attr('checked','checked')
		 }
		 
		 //$('#cbxPais').find("option[value='"+id0+"']").attr("selected","selected");
		 if(id0=="10"){
				 $('#cbxPais').find("option[value='"+id0+"']").attr("selected","selected");
				 onSelectChangePais();
				 //entidadFederativaId
				 
				 var entidadFede=$(xml).find('domicilio').find('entidadDTO').find('entidadFederativaId').text();				 
				 $('#cbxEntFederativa').find("option[value='"+entidadFede+"']").attr("selected","selected");
				 onSelectChangeEntFed();
				 
				 var ciudadfed=$(xml).find('domicilio').find('ciudadDTO').find('ciudadId').first().text();				 
				 if (ciudadfed==null || ciudadfed.length==0) {					 
					 ciudadfed=$(xml).find('domicilio').find('asentamientoDTO').find('ciudadDTO').find('ciudadId').text();
				 }				 
				 $('#cbxCiudad').find("option[value='"+ciudadfed+"']").attr("selected","selected");
				 onSelectChangeCiudadMunicipioTipoAsentamiento();
				 
				 
				 var municipio=$(xml).find('domicilio').find('municipioDTO').find('municipioId').first().text();
				 if (municipio==null || municipio.length==0){					 
					 municipio==$(xml).find('domicilio').find('asentamientoDTO').find('municipioDTO').find('municipioId').text();
				 }
				 
				 $('#cbxDelegacionMunicipio').find("option[value='"+municipio+"']").attr("selected","selected");				 
				 onSelectChangeDelgMun();
				 
				 var colonia=$(xml).find('domicilio').find('asentamientoDTO').find('asentamientoId').text();
				 $('#cbxAsentamientoColonia').find("option[value='"+colonia+"']").attr("selected","selected");
				 
				 var tipoAsentamiento=$(xml).find('domicilio').find('asentamientoDTO').find('tipoAsentamientoDTO').find('tipoAsentamientoId').text();
				 $('#cbxTipoAsentamiento').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
				 
				//sector
				 var sector=$(xml).find('domicilio').find('asentamientoDTO').find('sector').text();
				 $('#sector').val(sector);
				 
				 var tipocalle=$(xml).find('domicilio').find('valorCalleId').find('idCampo').text();
				 $('#cbxTipoCalle').find("option[value='"+tipocalle+"']").attr("selected","selected");
				 
				 $('#codigoPostal').val($(xml).find('domicilio').find('asentamientoDTO').find('codigoPostal').text());
				 
//				 var longitud=$(xml).find('domicilio').find('longitud').text();
//				 $('#txtFldLongitud').val(longitud);
//				 var latitud=$(xml).find('domicilio').find('latitud').text();
//				 $('#txtFldLatitud2').val(latitud);
 				pintaDatosCoordenadasGPS(xml);
 				$('#codigoPostalButton').hide();
			} else if (id0.length>0) {
				
				$('#cbxPais').find("option[value='"+id0+"']").attr("selected","selected");
				$('#entidadFederativa').val($(xml).find('domicilio').find('estado').text());
				$('#areaCiudad').val($(xml).find('domicilio').find('ciudad').text());
				$('#areaDelegacionMunicipio').val($(xml).find('domicilio').find('municipio').text());
				$('#areaColonia').val($(xml).find('domicilio').find('asentamientoExt').text());
				$('#codigoPostal').val($(xml).find('domicilio').find('codigoPostal').text());
				
				pintaDatosCoordenadasGPS(xml);
				hideControls("no");
			}
			
	}

	function  pintaDatosDomicilioNotif(xml){
		 id0=$(xml).find('domicilioNotificacion').find('municipioDTO').find('entidadFederativaDTO').find('valorIdPais').find('idCampo').first().text();
		 if (id0==null || id0.length==0) {			 
			 id0=$(xml).find('domicilioNotificacion').find('ciudadDTO').find('valorIdPais').find('idCampo').first().text();
		 }
		 if (id0==null || id0.length==0) {
			 id0=$(xml).find('domicilioNotificacion').find('paisValor').find('idCampo').first().text();
		 }
		 if (id0==null || id0.length==0) {
			 id0=$(xml).find('domicilioNotificacion').find('entidadDTO').find('valorIdPais').find('idCampo').first().text();
		 }
		
		 $('#areaCalleNotif').val($(xml).find('domicilioNotificacion').find('calle').text());
		 $('#areaNumeroExteriorNotif').val($(xml).find('domicilioNotificacion').find('numeroExterior').text());
		 $('#areaNumeroInteriorNotif').val($(xml).find('domicilioNotificacion').find('numeroInterior').text());
		 $('#areaEntreCalleNotif').val($(xml).find('domicilioNotificacion').find('entreCalle1').text());
		 $('#areaYCalleNotif').val($(xml).find('domicilioNotificacion').find('entreCalle2').text());
		 $('#areaAliasNotif').val($(xml).find('domicilioNotificacion').find('alias').text());
		 $('#areaEdificioNotif').val($(xml).find('domicilioNotificacion').find('edificio').text());
		 $('#areaReferenciasNotif').val($(xml).find('domicilioNotificacion').find('referencias').text());		 
		 
		 if(id0=="10"){
				$('#cbxPaisNotif').find("option[value='"+id0+"']").attr("selected","selected");
				 onSelectChangePaisNotif();
				 //entidadFederativaId
				 
				 var entidadFede=$(xml).find('domicilioNotificacion').find('entidadDTO').find('entidadFederativaId').text();
				 $('#cbxEntFederativaNotif').find("option[value='"+entidadFede+"']").attr("selected","selected");
				 onSelectChangeEntFedNotif();
				 
				 var ciudadfed=$(xml).find('domicilioNotificacion').find('ciudadDTO').first().find('ciudadId').text();
				 $('#cbxCiudadNotif').find("option[value='"+ciudadfed+"']").attr("selected","selected");
				 onSelectChangeCiudadMunicipioTipoAsentamientoNotif();
				 
				 var municipio=$(xml).find('domicilioNotificacion').find('municipioDTO').first().find('municipioId').text();
				 $('#cbxDelegacionMunicipioNotif').find("option[value='"+municipio+"']").attr("selected","selected");
				 onSelectChangeDelgMunNotif();
				 
				 var colonia=$(xml).find('domicilioNotificacion').find('asentamientoDTO').find('asentamientoId').text();
				 $('#cbxAsentamientoColoniaNotif').find("option[value='"+colonia+"']").attr("selected","selected");
				 
				//sector
				 var sector=$(xml).find('domicilioNotificacion').find('asentamientoDTO').find('sector').text();
				 $('#sectorNotif').val(sector);
				 
				 var tipoAsentamiento=$(xml).find('domicilio').find('asentamientoDTO').find('tipoAsentamientoDTO').find('tipoAsentamientoId').text();
				 $('#cbxTipoAsentamientoNotif').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
				 
				 var tipocalle=$(xml).find('domicilioNotificacion').find('valorCalleId').find('idCampo').text();
				 $('#cbxTipoCalleNotif').find("option[value='"+tipocalle+"']").attr("selected","selected");
				 
				 $('#codigoPostalNotif').val($(xml).find('domicilioNotificacion').find('asentamientoDTO').find('codigoPostal').text());

				 //				 var longitud=$(xml).find('domicilio').find('longitud').text();
//				 $('#txtFldLongitud').val(longitud);
//				 var latitud=$(xml).find('domicilio').find('latitud').text();
//				 $('#txtFldLatitud2').val(latitud);
				pintaDatosCoordenadasGPSNotif(xml);				
				$("#codigoPostalButtonNotif").hide();
			} else if (id0.length>0) {
				
				$('#cbxPaisNotif').find("option[value='"+id0+"']").attr("selected","selected");
				$('#entidadFederativaNotif').val($(xml).find('domicilioNotificacion').find('estado').text());
				$('#areaCiudadNotif').val($(xml).find('domicilioNotificacion').find('ciudad').text());
				$('#areaDelegacionMunicipioNotif').val($(xml).find('domicilioNotificacion').find('municipio').text());
				$('#areaColoniaNotif').val($(xml).find('domicilioNotificacion').find('asentamientoExt').text());
				$('#codigoPostalNotif').val($(xml).find('domicilioNotificacion').find('codigoPostal').text());
				
				pintaDatosCoordenadasGPSNotif(xml);
				hideControlsNotif("no");
			}

	}
	
	function  pintaDatosDomicilioOrganizacion(xml){
		id0=$(xml).find('domicilioDTO').find('municipioDTO').find('entidadFederativaDTO').find('valorIdPais').find('idCampo').first().text();

		if (id0==null || id0.length==0) {
			id0=$(xml).find('domicilioDTO').find('ciudadDTO').find('valorIdPais').find('idCampo').first().text();
		}
		if (id0==null || id0.length==0) {
			id0=$(xml).find('domicilioDTO').find('paisValor').find('idCampo').first().text();
		}
		if (id0==null || id0.length==0) {
			id0=$(xml).find('domicilioDTO').find('entidadDTO').find('valorIdPais').find('idCampo').first().text();
		}
		 
		 $('#areaCalle').val($(xml).find('domicilioDTO').find('calle').text());
		 $('#areaNumeroExterior').val($(xml).find('domicilioDTO').find('numeroExterior').text());
		 $('#areaNumeroInterior').val($(xml).find('domicilioDTO').find('numeroInterior').text());
		 $('#areaEntreCalle').val($(xml).find('domicilioDTO').find('entreCalle1').text());
		 $('#areaYCalle').val($(xml).find('domicilioDTO').find('entreCalle2').text());
		 $('#areaAlias').val($(xml).find('domicilioDTO').find('alias').text());
		 $('#areaEdificio').val($(xml).find('domicilioDTO').find('edificio').text());
		 $('#areaReferencias').val($(xml).find('domicilioDTO').find('referencias').text());
		 $('#codigoPostal').val($(xml).find('domicilioDTO').find('asentamientoDTO').find('codigoPostal').text());
		
		 //$('#cbxPais').find("option[value='"+id0+"']").attr("selected","selected");
		 if(id0=="10"){//FIXME JEFP CONSULATAR LA ENTIDAD FEDERATIVA DE DESPLIEGUE PARA OBTENER EL PAIS Y QUITAR EL 10 QUE ES MEXICO
				$('#cbxPais').find("option[value='"+id0+"']").attr("selected","selected");
				 onSelectChangePais();
				 //entidadFederativaId
				 var entidadFede=$(xml).find('domicilioDTO').find('entidadDTO').find('entidadFederativaId').text();
				 $('#cbxEntFederativa').find("option[value='"+entidadFede+"']").attr("selected","selected");
				 onSelectChangeEntFed();
				 var ciudadfed=$(xml).find('domicilioDTO').find('ciudadDTO').last().find('ciudadId').text();
				 $('#cbxCiudad').find("option[value='"+ciudadfed+"']").attr("selected","selected");
				 onSelectChangeCiudadMunicipioTipoAsentamiento();
				 var municipio=$(xml).find('domicilioDTO').find('municipioDTO').find('municipioId').first().text();
				 /* if (municipio==null || municipio.length==0){					 
					 municipio==$(xml).find('domicilioDTO').find('asentamientoDTO').find('municipioDTO').find('municipioId').text();
				 } */	
				 $('#cbxDelegacionMunicipio').find("option[value='"+municipio+"']").attr("selected","selected");
				 onSelectChangeCiudadMunicipioTipoAsentamiento();
				 var colonia=$(xml).find('domicilioDTO').find('asentamientoDTO').find('asentamientoId').text();
				 $('#cbxAsentamientoColonia').find("option[value='"+colonia+"']").attr("selected","selected");
				 
				//sector
				 var sector=$(xml).find('domicilioDTO').find('asentamientoDTO').find('sector').text();
				 $('#sector').val(sector);
				 
				 var tipocalle=$(xml).find('domicilioDTO').find('valorCalleId').find('idCampo').text();
				 $('#cbxTipoCalle').find("option[value='"+tipocalle+"']").attr("selected","selected");
				 var tipoAsentamiento=$(xml).find('domicilioDTO').find('asentamientoDTO').find('tipoAsentamientoDTO').find('tipoAsentamientoId').text();
				 $('#cbxTipoAsentamiento').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
//				 var longitud=$(xml).find('domicilioDTO').find('longitud').text();
//				 $('#txtFldLongitud').val(longitud);
//				 var latitud=$(xml).find('domicilioDTO').find('latitud').text();
//				 $('#txtFldLatitud2').val(latitud);
				 pintaDatosCoordenadasGPS(xml);
		 } else if (id0.length>0) {
				
				$('#cbxPais').find("option[value='"+id0+"']").attr("selected","selected");
				$('#entidadFederativa').val($(xml).find('domicilioDTO').find('estado').text());
				$('#areaCiudad').val($(xml).find('domicilioDTO').find('ciudad').text());
				$('#areaDelegacionMunicipio').val($(xml).find('domicilioDTO').find('municipio').text());
				$('#areaColonia').val($(xml).find('domicilioDTO').find('asentamientoExt').text());
				$('#codigoPostal').val($(xml).find('domicilioDTO').find('codigoPostal').text());
				
				pintaDatosCoordenadasGPS(xml);
				hideControls("no");
			}
			
		 //var idPais = $('#cbxPais option:selected').val();
	}
	
	/*
	*Funcion para pintar el domicilio cuando se trata de la consulta del traductor-interprete
	*/
	function  pintaDatosDomicilioTraductor(xml){
		 id0=$(xml).find('domicilio').find('ciudadDTO').find('valorIdPais').find('idCampo').text();	
	 
		 $('#areaCalle').val($(xml).find('domicilio').find('calle').text());
		 $('#areaNumeroExterior').val($(xml).find('domicilio').find('numeroExterior').text());
		 $('#areaNumeroInterior').val($(xml).find('domicilio').find('numeroInterior').text());
		 $('#areaEntreCalle').val($(xml).find('domicilio').find('entreCalle1').text());
		 $('#areaYCalle').val($(xml).find('domicilio').find('entreCalle2').text());
		 $('#areaAlias').val($(xml).find('domicilio').find('alias').text());
		 $('#areaEdificio').val($(xml).find('domicilio').find('edificio').text());
		 $('#areaReferencias').val($(xml).find('domicilio').find('referencias').text());
		 $('#codigoPostal').val($(xml).find('domicilio').find('asentamientoDTO').find('codigoPostal').text());
		 if($(xml).find('involucradoDTO').find('esMismoDomicilio').text() == "false"){
		 	$("#DomicilioNotificacionesNo").attr('checked','checked');
		 }
		 
		 //$('#cbxPais').find("option[value='"+id0+"']").attr("selected","selected");
		 if(id0!="0"){
				$('#cbxPais').find("option[value='"+id0+"']").attr("selected","selected");
				 onSelectChangePais();
				 //entidadFederativaId
				 
				 var entidadFede=$(xml).find('domicilio').find('entidadDTO').find('entidadFederativaId').text();
				 $('#cbxEntFederativa').find("option[value='"+entidadFede+"']").attr("selected","selected");
				 onSelectChangeEntFed();
				 
				 var ciudadfed=$(xml).find('domicilio').find('ciudadDTO').find('ciudadId').first().text();
				 $('#cbxCiudad').find("option[value='"+ciudadfed+"']").attr("selected","selected");
				 onSelectChangeCiudadMunicipioTipoAsentamiento();
				 
				 
				 var municipio=$(xml).find('domicilio').find('municipioDTO').find('municipioId').first().text();
				 $('#cbxDelegacionMunicipio').find("option[value='"+municipio+"']").attr("selected","selected");
				 onSelectChangeDelgMun();
				 
				 var colonia=$(xml).find('domicilio').find('asentamientoDTO').find('asentamientoId').first().text();
				 $('#cbxAsentamientoColonia').find("option[value='"+colonia+"']").attr("selected","selected");
				 
				 var tipoAsentamiento=$(xml).find('domicilio').find('asentamientoDTO').find('tipoAsentamientoDTO').find('tipoAsentamientoId').first().text();
				 $('#cbxTipoAsentamiento').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
				 
				 //sector
				 var sector=$(xml).find('domicilio').find('asentamientoDTO').find('sector').text();
				 $('#sector').val(sector);
				 
				 var tipocalle=$(xml).find('domicilio').find('valorCalleId').find('idCampo').first().text();
				 $('#cbxTipoCalle').find("option[value='"+tipocalle+"']").attr("selected","selected");

				pintaDatosCoordenadasGPS(xml);
				$('#codigoPostalButton').hide();
			}
			
	}
			

	function deshabilitaDatosDomicilio(){
		 $('#cbxPais').attr("disabled","disabled");
		 $('#codigoPostal').attr("disabled","disabled");
		 $('#cbxEntFederativa').attr("disabled","disabled");
		 $('#cbxCiudad').attr("disabled","disabled");
		 $('#cbxDelegacionMunicipio').attr("disabled","disabled");
		 $('#cbxAsentamientoColonia').attr("disabled","disabled");
		 $('#sector').attr("disabled","disabled");
		 $('#cbxTipoAsentamiento').attr("disabled","disabled");
		 $('#cbxTipoCalle').attr("disabled","disabled");
		 $('#entidadFederativa').attr("disabled","disabled");
		 $('#areaCiudad').attr("disabled","disabled");
		 $('#areaDelegacionMunicipio').attr("disabled","disabled");
		 $('#areaColonia').attr("disabled","disabled");
		 $('#areaAsentamiento').attr("disabled","disabled");
		 $('#areaTipoCalle').attr("disabled","disabled");
		 $('#areaCalle').attr("disabled","disabled");
		 $('#areaNumeroExterior').attr("disabled","disabled");
		 $('#areaNumeroInterior').attr("disabled","disabled");
		 $('#areaReferencias').attr("disabled","disabled");
		 $('#areaEntreCalle').attr("disabled","disabled");
		 $('#areaYCalle').attr("disabled","disabled");
		 $('#areaAlias').attr("disabled","disabled");
		 $('#areaEdificio').attr("disabled","disabled");
		 //campos de domicilio notificacion
		 $('#cbxPaisNotif').attr("disabled","disabled");
		 $('#codigoPostalNotif').attr("disabled","disabled");
		 $('#cbxEntFederativaNotif').attr("disabled","disabled");
		 $('#cbxCiudadNotif').attr("disabled","disabled");
		 $('#cbxDelegacionMunicipioNotif').attr("disabled","disabled");
		 $('#cbxAsentamientoColoniaNotif').attr("disabled","disabled");
		 $('#sectorNotif').attr("disabled","disabled");
		 $('#cbxTipoAsentamientoNotif').attr("disabled","disabled");
		 $('#cbxTipoCalleNotif').attr("disabled","disabled");
		 $('#entidadFederativaNotif').attr("disabled","disabled");
		 $('#areaCiudadNotif').attr("disabled","disabled");
		 $('#areaDelegacionMunicipioNotif').attr("disabled","disabled");
		 $('#areaColoniaNotif').attr("disabled","disabled");
		 $('#areaAsentamientoNotif').attr("disabled","disabled");
		 $('#areaTipoCalleNotif').attr("disabled","disabled");
		 $('#areaCalleNotif').attr("disabled","disabled");
		 $('#areaNumeroExteriorNotif').attr("disabled","disabled");
		 $('#areaNumeroInteriorNotif').attr("disabled","disabled");
		 $('#areaReferenciasNotif').attr("disabled","disabled");
		 $('#areaEntreCalleNotif').attr("disabled","disabled");
		 $('#areaYCalleNotif').attr("disabled","disabled");
		 $('#areaAliasNotif').attr("disabled","disabled");
		 $('#areaEdificioNotif').attr("disabled","disabled");
		 $("#codigoPostalButtonNotif").hide();
		 $(':radio[name=rbtMismoDomicilioNotificaciones]').attr('disabled','disabled');
		 validaDomicilioNotificaciones();
		 bloqueaCamposCoordenadasGPSHecho(0);
		 bloqueaCamposCoordenadasGPSHechoNotif(0);
		 $('#codigoPostalButton').hide();
	}

	function habilitarDatosDomicilio(){
		$('#codigoPostalButton').show();
		 $('#cbxPais').attr("disabled","");
		 $('#codigoPostal').attr("disabled","");
		 $('#cbxEntFederativa').attr("disabled","");
		 $('#cbxCiudad').attr("disabled","");
		 $('#cbxDelegacionMunicipio').attr("disabled","");
		 $('#cbxAsentamientoColonia').attr("disabled","");
		 $('#sector').attr("disabled","disabled");
		 $('#cbxTipoAsentamiento').attr("disabled","disabled");
		 $('#cbxTipoCalle').attr("disabled","");
		 $('#entidadFederativa').attr("disabled","");
		 $('#areaCiudad').attr("disabled","");
		 $('#areaDelegacionMunicipio').attr("disabled","");
		 $('#areaColonia').attr("disabled","");
		 $('#areaAsentamiento').attr("disabled","");
		 $('#areaTipoCalle').attr("disabled","");
		 $('#areaCalle').attr("disabled","");
	 	 $('#areaNumeroExterior').attr("disabled","");
		 $('#areaNumeroInterior').attr("disabled","");
		 $('#areaReferencias').attr("disabled","");
		 $('#areaEntreCalle').attr("disabled","");
		 $('#areaYCalle').attr("disabled","");
		 $('#areaAlias').attr("disabled","");
		 $('#areaEdificio').attr("disabled","");
		 $("#codigoPostalButton").show();
		 
		 //$('#txtFldLongitud').attr("disabled","");
		 //$('#txtFldLatitud2').attr("disabled","");
		 bloqueaCamposCoordenadasGPSHecho(1);
		 
//		 var $widget = $("select").multiselect();
//			var	state = true; 
//		   state = !state; 
//		   $widget.multiselect(state ? 'disable' : 'enable');

		 //campos de domicilio notificacion
		 $('#cbxPaisNotif').attr("disabled","");
		 $('#codigoPostalNotif').attr("disabled","");
		 $('#cbxEntFederativaNotif').attr("disabled","");
		 $('#cbxCiudadNotif').attr("disabled","");
		 $('#cbxDelegacionMunicipioNotif').attr("disabled","");
		 $('#cbxAsentamientoColoniaNotif').attr("disabled","");
		 $('#sectorNotif').attr("disabled","disabled");
		 $('#cbxTipoAsentamientoNotif').attr("disabled","disabled");
		 $('#cbxTipoCalleNotif').attr("disabled","");
		 $('#entidadFederativaNotif').attr("disabled","");
		 $('#areaCiudadNotif').attr("disabled","");
		 $('#areaDelegacionMunicipioNotif').attr("disabled","");
		 $('#areaColoniaNotif').attr("disabled","");
		 $('#areaAsentamientoNotif').attr("disabled","");
		 $('#areaTipoCalleNotif').attr("disabled","");
		 $('#areaCalleNotif').attr("disabled","");
		 $('#areaNumeroExteriorNotif').attr("disabled","");
		 $('#areaNumeroInteriorNotif').attr("disabled","");
		 $('#areaReferenciasNotif').attr("disabled","");
		 $('#areaEntreCalleNotif').attr("disabled","");
		 $('#areaYCalleNotif').attr("disabled","");
		 $('#areaAliasNotif').attr("disabled","");
		 $('#areaEdificioNotif').attr("disabled","");
		 $("#codigoPostalButtonNotif").show();
		 $(':radio[name=rbtMismoDomicilioNotificaciones]').attr('disabled','');
		 validaDomicilioNotificaciones();
		 bloqueaCamposCoordenadasGPSHechoNotif(1);
	}

	
	  function obtenerParametrosDomicilio(){
	        //Lugar de nacimiento esta pendiente ya que es un campo en BD pero en la pantalla vienen 3 campos, pais, estado y municipio
	        var IDPAIS_MEXICO = 10;
	        var idPais = $('#cbxPais option:selected').val();
	        var parametros = '&pais=' + idPais;
	        parametros += '&codigoPostal=' +  $('#codigoPostal').val();
	        //Cambiar por el id de Mexico
	        if(idPais==IDPAIS_MEXICO){        	
	            parametros += '&entidadFederativa=' + $('#cbxEntFederativa option:selected').val();
	            parametros += '&ciudad=' + $('#cbxCiudad option:selected').val();
	            parametros += '&delegacionMunicipio=' + $('#cbxDelegacionMunicipio option:selected').val();
	            parametros += '&asentamientoColonia=' + $('#cbxAsentamientoColonia option:selected').val();
	            parametros += '&tipoAsentamiento=' + $('#cbxTipoAsentamiento option:selected').val();
	            parametros += '&tipoCalle=' + $('#cbxTipoCalle option:selected').val();
	        }else{
	            parametros += '&entidadFederativa=' + $('#entidadFederativa').val();
	            parametros += '&ciudad=' + $('#areaCiudad').val();
	            parametros += '&delegacionMunicipio=' + $('#areaDelegacionMunicipio').val();
	            parametros += '&asentamientoColonia=' + $('#areaColonia').val();
	            parametros += '&tipoAsentamiento=' + $('#areaAsentamiento').val();        
	            parametros += '&tipoCalle=' + $('#areaTipoCalle').val();
	        }

	        parametros += '&calle=' + $('#areaCalle').val();
	        parametros += '&numExterior=' + $('#areaNumeroExterior').val();
	        parametros += '&numInterior=' + $('#areaNumeroInterior').val();
	        parametros += '&referencias=' + $('#areaReferencias').val();
	        parametros += '&entreCalle=' + $('#areaEntreCalle').val();
	        parametros += '&ycalle=' + $('#areaYCalle').val();
	        parametros += '&aliasDomicilio=' + $('#areaAlias').val(); //ALIAS DE DOMICILIO?
	        parametros += '&edificio=' + $('#areaEdificio').val();
	    //  parametros += '&longitud=' + $('#txtFldLongitud').val();
		       // parametros += '&latitud=' + $('#txtFldLatitud2').val();

		        parametros +='&longitudE='+$("#txtFldLongitudEste").val();
		   		parametros+='&longitudGrados='+$("#txtFldLongitudGrados").val();
		   		parametros+='&longitudMinutos='+$("#txtFldLongitudMinutos").val();
		   		parametros+='&longitudSegundos='+$("#txtFldLongitudSegundos").val();
		   		parametros+='&latitudN='+$("#txtFldLatitudNorte").val();
		   		parametros+='&latitudGrados='+$("#txtFldLatitudGrados").val();
		   		parametros+='&latitudMinutos='+$("#txtFldLatitudMinutos").val();
		   		parametros+='&latitudSegundos='+$("#txtFldLatitudSegundos").val();


	        var mismoDomicilioNotificaciones = $(':radio[name=rbtMismoDomicilioNotificaciones]:checked').val();
	        parametros += '&mismoDomicilioNotificaciones=' + mismoDomicilioNotificaciones;

	        if(mismoDomicilioNotificaciones == 0){
	        	idPais = $('#cbxPaisNotif option:selected').val();
	            parametros += '&paisNotif=' + idPais;
	            parametros += '&codigoPostalNotif=' +  $('#codigoPostalNotif').val();
	            //Cambiar por el id de Mexico
	            if(idPais==IDPAIS_MEXICO){        	
	                parametros += '&entidadFederativaNotif=' + $('#cbxEntFederativaNotif option:selected').val();
	                parametros += '&ciudadNotif=' + $('#cbxCiudadNotif option:selected').val();
	                parametros += '&delegacionMunicipioNotif=' + $('#cbxDelegacionMunicipioNotif option:selected').val();
	                parametros += '&asentamientoColoniaNotif=' + $('#cbxAsentamientoColoniaNotif option:selected').val();
	                parametros += '&tipoAsentamientoNotif=' + $('#cbxTipoAsentamientoNotif option:selected').val();
	                parametros += '&tipoCalleNotif=' + $('#cbxTipoCalleNotif option:selected').val();
	                
	            }else{
	                parametros += '&entidadFederativaNotif=' + $('#entidadFederativaNotif').val();
	                parametros += '&ciudadNotif=' + $('#areaCiudadNotif').val();
	                parametros += '&delegacionMunicipioNotif=' + $('#areaDelegacionMunicipioNotif').val();
	                parametros += '&asentamientoColoniaNotif=' + $('#areaColoniaNotif').val();
	                parametros += '&tipoAsentamientoNotif=' + $('#areaAsentamientoNotif').val();        
	                parametros += '&tipoCalleNotif=' + $('#areaTipoCalleNotif').val();
	            }

	            parametros += '&calleNotif=' + $('#areaCalleNotif').val();
	            parametros += '&numExteriorNotif=' + $('#areaNumeroExteriorNotif').val();
	            parametros += '&numInteriorNotif=' + $('#areaNumeroInteriorNotif').val();
	            parametros += '&referenciasNotif=' + $('#areaReferenciasNotif').val();
	            parametros += '&entreCalleNotif=' + $('#areaEntreCalleNotif').val();
	            parametros += '&ycalleNotif=' + $('#areaYCalleNotif').val();
	            parametros += '&aliasDomicilioNotif=' + $('#areaAliasNotif').val(); //ALIAS DE DOMICILIO?
	            parametros += '&edificioNotif=' + $('#areaEdificioNotif').val();
	            //parametros += '&longitudNotif=' + $('#txtFldLongitud').val();
		        //parametros += '&latitudNotif=' + $('#txtFldLatitud2').val();
	            parametros += recuperaDatosCoordenadasGPSNotif();
	        }else{
	        	idPais = $('#cbxPais option:selected').val();
	            parametros += '&paisNotif=' + idPais;
	            parametros += '&codigoPostalNotif=' +  $('#codigoPostal').val();
	            //Cambiar por el id de Mexico
	            if(idPais==IDPAIS_MEXICO){        	
	                parametros += '&entidadFederativaNotif=' + $('#cbxEntFederativa option:selected').val();
	                parametros += '&ciudadNotif=' + $('#cbxCiudad option:selected').val();
	                parametros += '&delegacionMunicipioNotif=' + $('#cbxDelegacionMunicipio option:selected').val();
	                parametros += '&asentamientoColoniaNotif=' + $('#cbxAsentamientoColonia option:selected').val();
	                parametros += '&tipoAsentamientoNotif=' + $('#cbxTipoAsentamiento option:selected').val();
	                parametros += '&tipoCalleNotif=' + $('#cbxTipoCalle option:selected').val();
	                
	            }else{
	                parametros += '&entidadFederativaNotif=' + $('#entidadFederativa').val();
	                parametros += '&ciudadNotif=' + $('#areaCiudad').val();
	                parametros += '&delegacionMunicipioNotif=' + $('#areaDelegacionMunicipio').val();
	                parametros += '&asentamientoColoniaNotif=' + $('#areaColonia').val();
	                parametros += '&tipoAsentamientoNotif=' + $('#areaAsentamiento').val();        
	                parametros += '&tipoCalleNotif=' + $('#areaTipoCalle').val();
	            }

	            parametros += '&calleNotif=' + $('#areaCalle').val();
	            parametros += '&numExteriorNotif=' + $('#areaNumeroExterior').val();
	            parametros += '&numInteriorNotif=' + $('#areaNumeroInterior').val();
	            parametros += '&referenciasNotif=' + $('#areaReferencias').val();
	            parametros += '&entreCalleNotif=' + $('#areaEntreCalle').val();
	            parametros += '&ycalleNotif=' + $('#areaYCalle').val();
	            parametros += '&aliasDomicilioNotif=' + $('#areaAlias').val(); //ALIAS DE DOMICILIO?
	            parametros += '&edificioNotif=' + $('#areaEdificio').val();
	    	    //  parametros += '&longitud=' + $('#txtFldLongitud').val();
			    // parametros += '&latitud=' + $('#txtFldLatitud2').val();

		        parametros +='&longitudENotif='+$("#txtFldLongitudEste").val();

		   		parametros+='&longitudGradosNotif='+$("#txtFldLongitudGrados").val();
		   		parametros+='&longitudMinutosNotif='+$("#txtFldLongitudMinutos").val();
		   		parametros+='&longitudSegundosNotif='+$("#txtFldLongitudSegundos").val();
		   		parametros+='&latitudNNotif='+$("#txtFldLatitudNorte").val();
		   		parametros+='&latitudGradosNotif='+$("#txtFldLatitudGrados").val();
		   		parametros+='&latitudMinutosNotif='+$("#txtFldLatitudMinutos").val();
		   		parametros+='&latitudSegundosNotif='+$("#txtFldLatitudSegundos").val();
		    }
	        /*Faltan datos pendientes de esta pantalla como pais de nacimiento, estado y mnicipio, faltan loa alias ocupacion y nacionalidad*/
	      	return parametros;
		}
 
	  function  pintaDatosDomicilioHecho(xml){
		  	
		    $('#cbxPais').find("option[value='10']").attr("selected","selected"); 
		    onSelectChangePais();
		    $('#cbxPais').attr("disabled","disabled");
		    
		     $('#areaCalle').val($(xml).find('lugar').find('calle').text());
			 $('#areaNumeroExterior').val($(xml).find('lugar').find('numeroExterior').text());
			 $('#areaNumeroInterior').val($(xml).find('lugar').find('numeroInterior').text());
			 $('#areaEntreCalle').val($(xml).find('lugar').find('entreCalle1').text());
			 $('#areaYCalle').val($(xml).find('lugar').find('entreCalle2').text());
			 $('#areaAlias').val($(xml).find('lugar').find('alias').text());
			 $('#areaEdificio').val($(xml).find('lugar').find('edificio').text());
			 $('#areaReferencias').val($(xml).find('lugar').find('referencias').text());			 			 
			 
			 //$('#cbxAsentamientoColonia').unbind('change');
			 			 
			 //entidadFederativaId
			 var entidadFede=$(xml).find('lugar').find('entidadDTO').find('entidadFederativaId').text();
			 $('#cbxEntFederativa').find("option[value='"+entidadFede+"']").attr("selected","selected");
			 onSelectChangeEntFed();
			 
			//ciudadId
			 var ciudad=$(xml).find('lugar').find('asentamientoDTO').find('ciudadDTO').find('ciudadId').text();
			 if (ciudad==null || ciudad.length==0) {				 
				 ciudad=$(xml).find('lugar').find('ciudadDTO').find('ciudadId').text();
			 }				
			 $('#cbxCiudad').find("option[value='"+ciudad+"']").attr("selected","selected");
			 //onSelectChangeCiudad();
			 
			 //municipioID
			 var municipio=$(xml).find('lugar').find('asentamientoDTO').find('municipioDTO').find('municipioId').text();
			 if (municipio==null || municipio.length==0) {
				 municipio=$(xml).find('lugar').find('municipioDTO').find('municipioId').text(); 				 
			 }			 			 
			 $('#cbxDelegacionMunicipio').find("option[value='"+municipio+"']").attr("selected","selected");
			 onSelectChangeDelgMun();
			 
			//asentamiento
			 var asentamiento=$(xml).find('lugar').find('asentamientoDTO').find('asentamientoId').text();
			 $('#cbxAsentamientoColonia').find("option[value='"+asentamiento+"']").attr("selected","selected");
			 
			//sector
			 var sector=$(xml).find('asentamientoDTO').find('sector').text();
			 $('#sector').val(sector);  
			 
			 //tipo de asentamiento
			 var tipoAsentamiento=$(xml).find('lugar').find('asentamientoDTO').find('tipoAsentamientoDTO').find('tipoAsentamientoId').text();
			 $('#cbxTipoAsentamiento').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
			 
			 $('#codigoPostal').val($(xml).find('lugar').find('asentamientoDTO').find('codigoPostal').text());
			 
			 //tipo de calle
			 var tipoCalle=$(xml).find('lugar').find('valorCalleId').find('idCampo').text();
			 $('#cbxTipoCalle').find("option[value='"+tipoCalle+"']").attr("selected","selected");
			 
			 $("#cbxAsentamientoColonia").change(muestraCPDomicilio);
			 seteaDatosCoordenadasGPSHecho(xml);
		}

	 
	  function  pintaDatosDomicilioAvisoPosHechoDel(xml){
		  	
		    $('#cbxPais').find("option[value='10']").attr("selected","selected"); 
		    onSelectChangePais();
		    $('#cbxPais').attr("disabled","disabled");
		    
		     $('#areaCalle').val($(xml).find('calle').text());
			 $('#areaNumeroExterior').val($(xml).find('numeroExterior').text());
			 $('#areaNumeroInterior').val($(xml).find('numeroInterior').text());
			 $('#areaEntreCalle').val($(xml).find('entreCalle1').text());
			 $('#areaYCalle').val($(xml).find('entreCalle2').text());
			 $('#areaAlias').val($(xml).find('alias').text());
			 $('#areaEdificio').val($(xml).find('edificio').text());
			 $('#areaReferencias').val($(xml).find('referencias').text());
			 
			  //$('#codigoPostal').val($(xml).find('lugar').find('asentamientoDTO').find('codigoPostal').text());

			 //entidadFederativaId
			 var entidadFede=$(xml).find('entidadDTO').find('entidadFederativaId').text();
			 $('#cbxEntFederativa').find("option[value='"+entidadFede+"']").attr("selected","selected");
			 onSelectChangeEntFed();
			 
			//ciudadId
			 var ciudad=$(xml).find('ciudadDTO').find('ciudadId').text();
			 $('#cbxCiudad').find("option[value='"+ciudad+"']").attr("selected","selected");
			 //onSelectChangeCiudad();
			 
			 //municipioID
			 var municipio=$(xml).find('municipioDTO').find('municipioId').text();
			 $('#cbxDelegacionMunicipio').find("option[value='"+municipio+"']").attr("selected","selected");
			 onSelectChangeDelgMun();
			 
			//asentamiento
			 var asentamiento=$(xml).find('asentamientoDTO').find('asentamientoId').text();
			 $('#cbxAsentamientoColonia').find("option[value='"+asentamiento+"']").attr("selected","selected");			 

			//sector
			 var sector=$(xml).find('asentamientoDTO').find('sector').text();
			 $('#sector').val(sector);
			/*  $('#sector').attr("disabled","disabled"); */
			 
			 //tipo de asentamiento
			 var tipoAsentamiento=$(xml).find('lugar').find('asentamientoDTO').find('tipoAsentamientoDTO').find('tipoAsentamientoId').text();
			 $('#cbxTipoAsentamiento').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
			 
			 
			 //tipo de calle
			 var tipoCalle=$(xml).find('lugar').find('valorCalleId').find('idCampo').text();
			 $('#cbxTipoCalle').find("option[value='"+tipoCalle+"']").attr("selected","selected");
			 
			 $("#codigoPostalButton").attr("disabled","disabled");
		}
				
	  
	  	function bloqueaCamposDomicilioHecho(bandera)
	  	{
	  		if(parseInt(bandera)==0)
	  		{
		  		$('#cbxPais').attr("disabled","disabled");
			    $('#areaCalle').attr("disabled","disabled");
				$('#areaNumeroExterior').attr("disabled","disabled");
				$('#areaNumeroInterior').attr("disabled","disabled");
				$('#areaEntreCalle').attr("disabled","disabled");
				$('#areaYCalle').attr("disabled","disabled");
				$('#areaAlias').attr("disabled","disabled");
				$('#areaEdificio').attr("disabled","disabled");
				$("#areaReferencias").attr("disabled","disabled");
				$('#cbxEntFederativa').attr("disabled","disabled");
				 $('#cbxCiudad').attr("disabled","disabled");
				 $('#cbxDelegacionMunicipio').attr("disabled","disabled");
				 $('#cbxAsentamientoColonia').attr("disabled","disabled");
				 $('#sector').attr("disabled","disabled");
				 $('#cbxTipoAsentamiento').attr("disabled","disabled");
				 $('#cbxTipoCalle').attr("disabled","disabled");
				 $('#codigoPostal').attr("disabled","disabled");
				 $('#codigoPostalButton').attr("disabled","disabled");
				bloqueaCamposCoordenadasGPSHecho(0);
	  		}
	  		else
	  		{
	  			$('#cbxPais').attr("disabled","");
			    $('#areaCalle').attr("disabled","");
				$('#areaNumeroExterior').attr("disabled","");
				$('#areaNumeroInterior').attr("disabled","");
				$('#areaEntreCalle').attr("disabled","");
				$('#areaYCalle').attr("disabled","");
				$('#areaAlias').attr("disabled","");
				$('#areaEdificio').attr("disabled","");
				$("#areaReferencias").attr("disabled","");
				$('#cbxEntFederativa').attr("disabled","");
				 $('#cbxCiudad').attr("disabled","");
				 $('#cbxDelegacionMunicipio').attr("disabled","");
				 $('#cbxAsentamientoColonia').attr("disabled","");
				 $('#sector').attr("disabled","disabled");
				 $('#cbxTipoAsentamiento').attr("disabled","disabled");
				 $('#cbxTipoCalle').attr("disabled","");
				 $('#codigoPostal').attr("disabled","");
				 $('#codigoPostalButton').attr("disabled","");
				bloqueaCamposCoordenadasGPSHecho(1);
	  		}
	  	}	
	  	
	  	/*
	  	*	Funcion para cargar el CP y el sector(solo coahuila) al terminar la seleccion de combos, 
	  	*	se ejecuta al seleccionar el asentamiento/colonia
	  	*/
	  	function muestraCPDomicilio(){
	  		var codigoPostal = $('#codigoPostal').val();
	  		var asentamiento = $("#cbxAsentamientoColonia option:selected").val();
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
						$('#codigoPostal').val($(xml).find('asentamiento').find('codigoPostal').text());	
						$('#cbxTipoAsentamiento').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
						$('#sector').val($(xml).find('asentamiento').find('sector').text());
						
						if(asentamiento == "-1"){
							$('#codigoPostal').val("");
							$('#sector').val("");
						}
					}	
				});
	  	}
	  	
	  	/*
	  	*	Funcion para cargar el CP y el sector(solo coahuila) al terminar la seleccion de combos, 
	  	*	se ejecuta al seleccionar el asentamiento/colonia. Esto para el domicilio de notificaciones
	  	*/
		function muestraCPDomicilioNotif(){
	  		var codigoPostal = $('#codigoPostalNotif').val();
	  		var asentamiento = $("#cbxAsentamientoColoniaNotif option:selected").val();
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
						$('#codigoPostalNotif').val($(xml).find('asentamiento').find('codigoPostal').text());	
						$('#cbxTipoAsentamientoNotif').find("option[value='"+tipoAsentamiento+"']").attr("selected","selected");
						$('#sectorNotif').val($(xml).find('asentamiento').find('sector').text());
												
						if(asentamiento == "-1"){
							$('#codigoPostal').val("");
							$('#sectorNotif').val("");
						}
					}	
				});			
	  	}	  	
		
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
		
		function validaDomicilioNotificaciones(){
			//en caso de NO ser el mismo domicilio de notificacion, muestra la seccion de domicilio de notificacion
			 if($(':radio[name=rbtMismoDomicilioNotificaciones]:checked').val() == 0){
				 muestraDomicilioNotificaciones();			 
			 }else{
				 ocultaDomicilioNotificaciones();
			 }
		}
		
		function cambiaEtiquetaColonia(bandera){
			
			if(bandera == "si"){
				document.getElementById("tdAsentamientoColonia").innerHTML = "<b>Localidad:</b>";
			}else{
				document.getElementById("tdAsentamientoColonia").innerHTML = "<b>Asentamiento o Colonia:</b>";
			}
		}
   </script>	

 <div id="tabs">
    <ul>
        <li id="liDom"><a href="#tabs-1" id="hrefIngDom">Ingresar domicilio</a></li>
        <li id="liDomNotif"><a href="#tabs-2">Ingresar domicilio para notificaciones</a></li>
    </ul>
    <div id="tabs-1">
      <table width="762px"  height="243px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="158" height="25" align="right" ><strong>Pa&iacute;s:</strong></td>
          <td width="200" height="25">
            <select id="cbxPais" style="width:200px;" tabindex="1">						
                <option value="-1">-Seleccione-</option>
            </select>
          </td>
          <td width="135" height="25" align="right"><strong>Calle o Avenida:</strong></td>
          <td colspan="3" height="25"  align="right">
            <input type="text" id="areaCalle" size="49"  tabindex="10" maxlength="50"/>
          </td>
        </tr>
        <tr>
          <td align="right" height="25"><strong>C&oacute;digo Postal:</strong></td>
          <td height="25">
            <input type="text" id="codigoPostal" size="8" maxlength="5" tabindex="2" onkeypress="return solonumeros(event);" onblur="validaSoloNumeros(this);"/>
            <input type="button" value="Enviar" id="codigoPostalButton" style="display:;" class="btn_modificar"/>
          </td>
          <td align="right" height="25"><strong>N&uacute;mero Ext.:</strong></td>
          <td width="90" height="25" align="right">
            <input type="text" id="areaNumeroExterior" size="7" maxlength="7" tabindex="11" onkeypress="return numerosYGato(event);"/>
          </td>
          <td width="87" height="25" align="right"><strong>N&uacute;mero Int.:</strong></td>
          <td width="92"  height="25" align="right"><input type="text" id="areaNumeroInterior" size="15" maxlength="12" tabindex="12" onkeypress="return numerosYGato(event);"/></td>
        </tr>
        <tr>
          <td height="25" align="right"><strong>Entidad Federativa:</strong></td>
          <td height="25"><div id="divCbxEntFederativa">
            <select id="cbxEntFederativa" style="width:200px;" tabindex="3">
                <option value="-1">-Seleccione-</option>
            </select></div>
            <input value="" size="20" maxlength="15" type="text" id="entidadFederativa" maxlength="50"/>
          </td>
          <td height="25" align="right"><strong>Entre calle:</strong></td>
          <td height="25" colspan="3"  align="right"><input type="text" id="areaEntreCalle" size="49" tabindex="13" maxlength="60"/></td>
        </tr>
        <tr>
          <td height="25" align="right"><strong>Ciudad:</strong></td>
          <td height="25"><div id="divCbxCiudad">
            <select id="cbxCiudad"  style="width:200px;" tabindex="4">
                <option value="-1">-Seleccione-</option>
            </select></div>
            <input type="text" id="areaCiudad" maxlength="50"/>
          </td>
          <td height="25" align="right"><strong>y calle:</strong></td>
          <td height="25" colspan="3" align="right"><input type="text" id="areaYCalle" size="49" tabindex="14" maxlength="60"/></td>
        </tr>
        <tr>
          <td height="25" align="right"><strong>Delegaci&oacute;n/Municipio:</strong></td>
          <td height="25"><div id="divCbxDelegacionMunicipio">
            <select id="cbxDelegacionMunicipio" style="width:200px;" tabindex="5">
                <option value="-1">-Seleccione-</option>
            </select></div>
            <input type="text" id="areaDelegacionMunicipio" maxlength="50"/>					
          </td>
          <td height="25" align="right"><strong>Alias:</strong></td>
          <td height="25" colspan="3"  align="right"><input type="text" id="areaAlias" size="49" tabindex="15" maxlength="60"/></td>
        </tr>
        <tr>
          <td id="tdAsentamientoColonia" height="25" align="right"><strong>Asentamiento o Colonia:</strong></td>
          <td height="25"><div id="divCbxAsentamientoColonia">
            <select id="cbxAsentamientoColonia" style="width:200px;" tabindex="6" onchange="muestraCPDomicilio()">
                <option value="-1">-Seleccione-</option>
            </select></div>
            <input type="text" id="areaColonia" maxlength="50"/>
          </td>
          <td height="25" align="right"><strong>Edificio:</strong></td>
          <td height="25" colspan="3"  align="right"><input type="text" id="areaEdificio" size="49" tabindex="16" maxlength="60"/></td>
        </tr>
        
        <tr id="trSector">
          <td height="25" align="right"><strong>Sector:</strong></td>
          <td height="25">
          	<input type="text" id="sector" maxlength="1"/>
          </td>
        </tr>
        
        <tr>
          <td height="25" align="right"><strong>Tipo de Asentamiento:</strong></td>
          <td height="25"><div id="divCbxTipoAsentamiento">
            <select id="cbxTipoAsentamiento" style="width:200px;" tabindex="7">
              <option value="-1">-Seleccione-</option>						
            </select></div>
            <input type="text" id="areaAsentamiento" readonly="readonly" maxlength="50"/>
          </td>
          <td height="25" align="right"><strong>Referencias:</strong></td>
          <td height="25" colspan="3" align="right" >
            <textarea id="areaReferencias" maxlength="60" cols="49" rows="5" style="width: 276px; height:50px;"  tabindex="17" maxlength="60"></textarea></td>
        </tr>
        <tr>
          <td height="12" align="right"><strong>Tipo de Calle:</strong></td>
          <td height="12"><div id="divCbxTipoCalle">
            <select id="cbxTipoCalle" style="width:200px;" tabindex="8">
              <option value="-1">-Seleccione-</option>
            </select></div>
            <input type="text" id="areaTipoCalle" readonly="readonly" maxlength="50"/>
          </td>  
          <td height="25" rowspan="2" align="right" id="colCoordGPSUno"><strong>Coord. Geogr&aacute;ficas:</strong></td>
          <td height="25" colspan="3" rowspan="2" id="rowCoordGPS" align="right">
            <jsp:include page="/WEB-INF/paginas/datosCoordenadasGPSView.jsp" flush="true"></jsp:include>
          </td>
        </tr>
        <tr>
          <td  id="rowDomicilioNotif" height="25" colspan="2" align="right"><strong>&iquest;Mismo domicilio para notificaciones?: </strong>
            <input type="radio" name="rbtMismoDomicilioNotificaciones" id="DomicilioNotificacionesSi" value="1" checked="checked" tabindex="9"/>
            <label for="DomicilioNotificacionesSi2"><strong>Si</strong></label>
            <input type="radio" name="rbtMismoDomicilioNotificaciones" id="DomicilioNotificacionesNo" value="0"/>
          <label for="domicilioNotificacionesNo3"><strong>No</strong></label></td>
        </tr>
        <tr>
          <td id="rowDomicilioNotif" height="25" colspan="2" align="right">&nbsp;</td>
          <td height="25" align="right">&nbsp;</td>
          <td colspan="3" >&nbsp;</td>
        </tr>
      </table>
    </div>
    
    <!-- TAB PARA EL DOMICILIO PARA NOTIFICACIONES -->
    
    <div id="tabs-2">
      <table width="762"  height="313px" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="155" height="25" align="right" ><strong>Pa&iacute;s:</strong></td>
          <td width="203" height="25">
            <select id="cbxPaisNotif" style="width:200px;" tabindex="17">						
                <option value="-1">-Seleccione-</option>
            </select>
          </td>
          <td width="135" height="25" align="right"><strong>Calle o Avenida:</strong></td>
          <td height="25" colspan="3"  align="right" >
            <input type="text" id="areaCalleNotif" size="49" tabindex="25" maxlength="50"/>
          </td>
        </tr>
        <tr>
          <td height="25" align="right"><strong>C&oacute;digo Postal:</strong></td>
          <td height="25">
            <input type="text" id="codigoPostalNotif" size="8" maxlength="5" tabindex="18" onkeypress="return solonumeros(event);" onblur="validaSoloNumeros(this);"/>
            <input type="button" value="Enviar" id="codigoPostalButtonNotif" style="display:;" class="btn_modificar"/>
          </td>
          <td height="25" align="right"><strong>N&uacute;mero Ext.:</strong></td>
          <td width="92" height="25">
            <input type="text" id="areaNumeroExteriorNotif" size="15" maxlength="8" tabindex="26" onkeypress="return numerosYGato(event);"/>
          </td>
          <td width="96" height="25" align="right"><strong>N&uacute;mero Int.:</strong></td>
          <td width="81"  height="25" align="right"><input type="text" id="areaNumeroInteriorNotif" size="15" maxlength="8" tabindex="27" onkeypress="return numerosYGato(event);"/></td>
        </tr>
        <tr>
          <td height="25" align="right"><strong>Entidad Federativa:</strong></td>
          <td height="25"><div id="divCbxEntFederativaNotif">
            <select id="cbxEntFederativaNotif" style="width:200px;" tabindex="19">
                <option value="-1">-Seleccione-</option>
            </select></div>
            <input value="" size="20" maxlength="15" type="text" id="entidadFederativaNotif" maxlength="50"/>
          </td>
          <td height="25" align="right"><strong>Entre calle:</strong></td>
          <td height="25" colspan="3"  align="right"><input type="text" id="areaEntreCalleNotif" size="49" tabindex="28" maxlength="60"/></td>
        </tr>
        <tr>
          <td height="25" align="right"><strong>Ciudad:</strong></td>
          <td height="25"><div id="divCbxCiudadNotif">
            <select id="cbxCiudadNotif" style="width:200px;" tabindex="20">
                <option value="-1">-Seleccione-</option>
            </select></div>
            <input type="text" id="areaCiudadNotif" maxlength="50"/>
          </td>
          <td height="25" align="right"><strong>y calle:</strong></td>
          <td height="25" colspan="3"  align="right"><input type="text" id="areaYCalleNotif" size="49" tabindex="29" maxlength="60"/></td>
        </tr>
        <tr>
          <td height="25" align="right"><strong>Delegaci&oacute;n/Municipio:</strong></td>
          <td height="25"><div id="divCbxDelegacionMunicipioNotif">
            <select id="cbxDelegacionMunicipioNotif" style="width:200px;" tabindex="21">
                <option value="-1">-Seleccione-</option>
            </select></div>
            <input type="text" id="areaDelegacionMunicipioNotif" maxlength="50"/>					
          </td>
          <td height="25" align="right"><strong>Alias:</strong></td>
          <td height="25" colspan="3"  align="right"><input type="text" id="areaAliasNotif" size="49" tabindex="30" maxlength="60"/></td>
        </tr>
        <tr>
          <td height="25" align="right"><strong>Asentamiento o Colonia:</strong></td>
          <td height="25"><div id="divCbxAsentamientoColoniaNotif">
            <select id="cbxAsentamientoColoniaNotif" style="width:200px;" tabindex="22" onchange="muestraCPDomicilioNotif()">
                <option value="-1">-Seleccione-</option>
            </select></div>
            <input type="text" id="areaColoniaNotif" maxlength="50"/>
          </td>
          <td height="25" align="right"><strong>Edificio:</strong></td>
          <td height="25" colspan="3"  align="right"><input type="text" id="areaEdificioNotif" size="49" maxlength="60"/></td>
        </tr>
        
        <tr id="trSectorNotif">
          <td height="25" align="right"><strong>Sector:</strong></td>
          <td height="25">
          	<input type="text" id="sectorNotif" maxlength="1"/>
          </td>
        </tr>
        
        <tr>
          <td height="25" align="right"><strong>Tipo de Asentamiento:</strong></td>
          <td height="25"><div id="divCbxTipoAsentamientoNotif">
            <select id="cbxTipoAsentamientoNotif" style="width:200px;" tabindex="23">
              <option value="-1">-Seleccione-</option>						
            </select></div>
            <input type="text" id="areaAsentamientoNotif" maxlength="50"/>
          </td>
          <td height="25" align="right"><strong>Referencias:</strong></td>
          <td height="25" colspan="3"  align="right"><textarea cols="45" rows="5" id="areaReferenciasNotif" style="width: 276px; height:50px;" maxlength="60" ></textarea></td>
        </tr>
        <tr>
          <td height="25" align="right"><strong>Tipo de Calle:</strong></td>
          <td height="25"><div id="divCbxTipoCalleNotif">
            <select id="cbxTipoCalleNotif" style="width:200px;" tabindex="24">
              <option value="-1">-Seleccione-</option>
            </select></div>
            <input type="text" id="areaTipoCalleNotif" maxlength="50"/>
          </td>  
          <td height="25" align="right"><strong>Coord. Geogr&aacute;ficas:</strong></td>
          <td height="25" colspan="3"><jsp:include page="/WEB-INF/paginas/datosCoordenadasGPSNotifView.jsp" flush="true"></jsp:include>
          </td>
        </tr>
        <tr>
          <td height="25" colspan="2">&nbsp;</td>
          <td height="25" align="right">&nbsp;</td>
          <td colspan="3" >&nbsp;</td>
        </tr>
      </table>
    </div>
</div>
    
	<script type="text/javascript">
		
		/**
		*Carga los escuchadores de eventos
		*/
		cargaPaises();					//Carga el combo de paises
		cargaPaisesNotif();			//Carga el combo box de paises en el tab de domicilio de notificaciones

		/**
		*Carga los Combos con multiselect
		*/	 
//		$("#cbxTipoAsentamiento, #cbxTipoCalle, #cbxAsentamientoColonia").multiselect({ 
//			multiple: false, 
//			header: "Seleccione", 
//			position: { 
//				my: 'top', 
//				at: 'top' 
//			},
//			selectedList: 1 
//		});
//		$("#cbxPais").multiselect({ 
//			multiple: false, 
//			header: "Seleccione", 
//			position: { 
//				my: 'top', 
//				at: 'top' 
//			},
//			selectedList: 1, 
//			close: function (event,ui){
//				onSelectChangePais();}
//		});

$("#cbxPais").change(function(e){
	onSelectChangePais();
}); 
//		$("#cbxEntFederativa").multiselect({ 
//			multiple: false, 
//			header: "Seleccione", 
//			position: { 
//				my: 'bottomr', 
//				at: 'bottom' 
//			},
//			selectedList: 1, 
//			close: function (event,ui){
//				onSelectChangeEntFed();}
//		});

		$("#cbxEntFederativa").change(function(e){
			onSelectChangeEntFed();
		}); 

//		$("#cbxCiudad").multiselect({ 
//			multiple: false, 
//			header: "Seleccione", 
//			position: { 
//				my: 'top', 
//				at: 'top' 
//			},
//			selectedList: 1, 
//			close: function (event,ui){
//				onSelectChangeCiudadMunicipioTipoAsentamiento();}
//		});

		$("#cbxCiudad").change(function(e){
			onSelectChangeCiudadMunicipioTipoAsentamiento();
		}); 

//		$("#cbxDelegacionMunicipio").multiselect({ 
//			multiple: false, 
//			header: "Seleccione", 
//			position: { 
//				my: 'top', 
//				at: 'top' 
//			},
//			selectedList: 1, 
//			close: function (event,ui){
//				onSelectChangeCiudadMunicipioTipoAsentamiento();}
//		});

		$("#cbxDelegacionMunicipio").change(function(e){
			onSelectChangeCiudadMunicipioTipoAsentamiento();
		}); 

		/**
		*Carga los Combos de notificacion con multiselect
		*/
//		$("#cbxTipoAsentamientoNotif, #cbxTipoCalleNotif, #cbxAsentamientoColoniaNotif").multiselect({ 
//			multiple: false, 
//			header: "Seleccione", 
//			position: { 
//				my: 'top', 
//				at: 'top' 
//			},
//			selectedList: 1 
//		});
//		$("#cbxPaisNotif").multiselect({ 
//			multiple: false, 
//			header: "Seleccione", 
//			position: { 
//				my: 'top', 
//				at: 'top' 
//			},
//			selectedList: 1, 
///			close: function (event,ui){
//				onSelectChangePaisNotif();}
//		});
		$("#cbxPaisNotif").change(function(e){
			onSelectChangePaisNotif();
		});
//		$("#cbxEntFederativaNotif").multiselect({ 
//			multiple: false, 
//			header: "Seleccione", 
//			position: { 
//				my: 'bottomr', 
//				at: 'bottom' 
//			},
//			selectedList: 1, 
//			close: function (event,ui){
//				onSelectChangeEntFedNotif();}
//		});
		$("#cbxEntFederativaNotif").change(function(e){
			onSelectChangeEntFedNotif();
		});
		
//		$("#cbxCiudadNotif").multiselect({ 
//			multiple: false, 
//			header: "Seleccione", 
//			position: { 
//				my: 'top', 
//				at: 'top' 
//			},
//			selectedList: 1, 
//			close: function (event,ui){
//				onSelectChangeCiudadMunicipioTipoAsentamientoNotif();}
//		});
		$("#cbxCiudadNotif").change(function(e){
			onSelectChangeCiudadMunicipioTipoAsentamientoNotif();
		});
		
//		$("#cbxDelegacionMunicipioNotif").multiselect({ 
//			multiple: false, 
//			header: "Seleccione", 
//			position: { 
//				my: 'top', 
//				at: 'top' 
//			},
//			selectedList: 1, 
//			close: function (event,ui){
//				onSelectChangeCiudadMunicipioTipoAsentamientoNotif();}
//		});
		$("#cbxDelegacionMunicipioNotif").change(function(e){
			onSelectChangeCiudadMunicipioTipoAsentamientoNotif();
		});
						
		$("#codigoPostalButton").bind("click",cosultaPorCodigoPostal);					//Escuchador para consultar pos c&oacute;digo postal
		$("#limpiarButton").bind("click",limpiarFormulario);							//Funcion para limpiar el formulario
		/**
	 	* Escuchador de evento para el domicilio de notificaciones
	 	*/
		$("#DomicilioNotificacionesSi").bind("click",ocultaDomicilioNotificaciones);
		$("#DomicilioNotificacionesNo").bind("click",muestraDomicilioNotificaciones);
		$("#codigoPostalButtonNotif").bind("click",cosultaPorCodigoPostalNotif);		
				
		hideControls("no");			//Funciones para esconder los controles 
		hideControlsNotif("no");	//Funciones para esconder los controles del domicilio de notificaciones

		//Selecciona Mexico por default
		seleccionaMexico();
		//$('#codigoPostalButton').hide();
		//$("#codigoPostalButtonNotif").hide();
		
		//Selecciona la entidad federativa segun donde se encuentre por default		
		seleccionaEntidadFedereativa();
		
		$("#tabs").tabs();
		$("#tabs-puntocarretero").tabs();
	</script>