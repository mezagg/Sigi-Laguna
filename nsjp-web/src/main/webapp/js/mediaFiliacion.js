/*
* Funciones para consultar los Catalogos de Media filiacion
*/

function configuraEventosCombosDeMediaFiliacion(){
	//SE CARGAN LOS COMBOS ASINCRONAMENTE, DE TAL FORMA QUE CARGUEN POCO A POCO  
	//LOS COMBOS Y NO BLOQUE LA PANTALLA HASTA QUE CARGUE EL ULTIMO COMBO
	if(idElemento != undefined && (idElemento == null || idElemento == "null")){
		//Se definen eventos para cargar los combos de la Media filiacion
		$("#cejaMediaFiliacion").one("click", function() {
			//Tap de cara
			cargaTamanoBoca(true);
			cargaTipoCara(true);
			cargaFormaMenton(true);
			cargaTipoMenton(true);		
			cargaTez(true);
			cargaInclinacionMenton(true)
		});
		
		$("#idTapCabelloMF").one("click", function() {
			//Tap de Cabello
			cargaColorCabello(true);
			cargaFormaCabello(true);
			cargaCalvieTipo(true);
			cargaCabelloImplantacion(true);
			cargaCantidadCabello(true);
		});
		
		$("#idTapOrejasMF").one("click", function() {
			//Tap de Orejas
			cargaTamanoOreja(true);
			cargaLobuloParticularidad(true);
			cargaLobuloDimension(true);
			cargaLobuloAdherencia(true);
			cargaHelixAnterior(true); //Superior
			cargaHelixPosterior(true);
			cargaHelixContorno(true);
			cargaHelixAdherencia(true);
			cargaFormaOreja(true);
			cargaHelixOriginal(true);
			cargaOrejaLobContorno(true);
		});
		
		$("#idTapOjosMF").one("click", function() {
			//Tap Ojos
			cargaTamanoOjos(true);
			cargaColorOjos(true);
			cargaFormaOjos(true);
		});
		
		$("#idTapLabiosMF").one("click", function() {
			//Tap labios
	        cargaAlturaNasoLabial(true);
	        cargaComisuras(true);
	        cargaEspesorLabioInferior(true);
	        cargaEspesorLabioSuperior(true);
	        cargaProminencia(true);
		});
		
		$("#idTapNarizMF").one("click", function() {
	        //Tap de nariz          
	        cargaCatalogoPonEn("AnchoNariz", "ancho_nariz",true);
	        cargaCatalogoPonEn("AlturaNariz", "altura_nariz",true);
	        cargaCatalogoPonEn("BaseNariz", "base_nariz",true);
	        cargaCatalogoPonEn("RaizNariz", "raiz_nariz",true);
		});
		
		$("#idTapFrenteMF").one("click", function() {
			 //Tap Frente
	        cargaCatalogoPonEn("FrenteAltura", "frente_altura",true);
			cargaCatalogoPonEn("FrenteAncho", "frente_ancho",true);
	        cargaCatalogoPonEn("InclinacionFrente", "inclinacion_frente",true);
		});
		
		$("#idTapCejasMF").one("click", function() {
	      //Tap Cejas
			cargaImplantacionCeja(true);
			cargaDireccionCeja(true);
			cargaFormaCejas(true);
			cargaTamanoCeja(true);

		});
		
		$("#idTapOtrosMF").one("click", function() {
			//Tap otros
			cargaTipoSangre(true);
			cargaComplexion(true);
		});
	}else{
		//SE CARGAN LOS COMBOS SINCRONAMENTE, DE TAL FORMA QUE PRIMERO CARGUEN Y 
		//LUEGO SE PINTE SU RESPECTIVO VALOR PARA LAS CONSULTAS
		//Se definen eventos para cargar los combos de la Media filiacion
		$("#cejaMediaFiliacion").one("click", function() {
			//Tap de cara
			cargaTamanoBoca();
			cargaTipoCara();
			cargaFormaMenton();
			cargaTipoMenton();		
			cargaTez();
			cargaInclinacionMenton()
		  	pintaDatosMediaFiliacion(detalleXMLDeInvolucrado);
		});
		
		$("#idTapCabelloMF").one("click", function() {
			//Tap de Cabello
			cargaColorCabello();
			cargaFormaCabello();
			cargaCalvieTipo();
			cargaCabelloImplantacion();
			cargaCantidadCabello();
			pintaDatosMediaFiliacion(detalleXMLDeInvolucrado);
		});
		
		$("#idTapOrejasMF").one("click", function() {
			//Tap de Orejas
			cargaTamanoOreja();
			cargaLobuloParticularidad();
			cargaLobuloDimension();
			cargaLobuloAdherencia();
			cargaHelixAnterior(); //Superior
			cargaHelixPosterior();
			cargaHelixContorno();
			cargaHelixAdherencia();
			cargaFormaOreja();
			cargaHelixOriginal();
			cargaOrejaLobContorno();
			pintaDatosMediaFiliacion(detalleXMLDeInvolucrado);
		});
		
		$("#idTapOjosMF").one("click", function() {
			//Tap Ojos
			cargaTamanoOjos();
			cargaColorOjos();
			cargaFormaOjos();
			pintaDatosMediaFiliacion(detalleXMLDeInvolucrado);
		});
		
		$("#idTapLabiosMF").one("click", function() {
			//Tap labios
	        cargaAlturaNasoLabial();
	        cargaComisuras();
	        cargaEspesorLabioInferior();
	        cargaEspesorLabioSuperior();
	        cargaProminencia();
	        pintaDatosMediaFiliacion(detalleXMLDeInvolucrado);
		});
		
		$("#idTapNarizMF").one("click", function() {
	        //Tap de nariz          
	        cargaCatalogoPonEn("AnchoNariz", "ancho_nariz");
	        cargaCatalogoPonEn("AlturaNariz", "altura_nariz");
	        cargaCatalogoPonEn("BaseNariz", "base_nariz");
	        cargaCatalogoPonEn("RaizNariz", "raiz_nariz");
	        pintaDatosMediaFiliacion(detalleXMLDeInvolucrado);
		});
		
		$("#idTapFrenteMF").one("click", function() {
			 //Tap Frente
	        cargaCatalogoPonEn("FrenteAltura", "frente_altura");
			cargaCatalogoPonEn("FrenteAncho", "frente_ancho");
	        cargaCatalogoPonEn("InclinacionFrente", "inclinacion_frente");
	        pintaDatosMediaFiliacion(detalleXMLDeInvolucrado);
		});
		
		$("#idTapCejasMF").one("click", function() {
		      //Tap Cejas
			cargaImplantacionCeja();
			cargaDireccionCeja();
			cargaFormaCejas();
			cargaTamanoCeja();
			pintaDatosMediaFiliacion(detalleXMLDeInvolucrado);
		});
		
		$("#idTapOtrosMF").one("click", function() {
			//Tap otros
			cargaTipoSangre();
			cargaComplexion();
			pintaDatosMediaFiliacion(detalleXMLDeInvolucrado);
		});
	}
}


    function cargaTamanoBoca(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;
    		
    	$('#cmpTamanoBoca').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoTamanoBoca.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catTamanoBoca').each(function(){
            			$('#cmpTamanoBoca').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
        		$('#cmpTamanoBoca').removeClass("cargando");
    		}
    	});
    }
	
    function cargaTipoCara(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpTipoCara').addClass("cargando");	
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoTipoCara.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catTipoCara').each(function(){
            			$('#cmpTipoCara').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpTipoCara').removeClass("cargando");
    		}
    	});
    }

    function cargaFormaMenton(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpFormaMenton').addClass("cargando");	
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoFormaMenton.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catFormaMenton').each(function(){
            			$('#cmpFormaMenton').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpFormaMenton').removeClass("cargando");
    		}
    	});
    }

    function cargaTipoMenton(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpTipoMenton').addClass("cargando");	

    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoTipoMenton.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catTipoMenton').each(function(){
            			$('#cmpTipoMenton').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpTipoMenton').removeClass("cargando");
    		}
    	});
    }

    function cargaTez(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpTez').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoTez.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catTez').each(function(){
            			$('#cmpTez').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpTez').removeClass("cargando");
    		}
    	});
    }

    function cargaInclinacionMenton(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpInclinacionMenton').addClass("cargando");	
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoInclinacionMenton.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catInclinacionMenton').each(function(){
            			$('#cmpInclinacionMenton').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpInclinacionMenton').removeClass("cargando");
    		}
    	});
    }

    function cargaColorCabello(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpColorCabello').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoColorCabello.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catColorCabello').each(function(){
            			$('#cmpColorCabello').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpColorCabello').removeClass("cargando");
    		}
    	});
    }

    function cargaFormaCabello(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpFormaCabello').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoFormaCabello.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catFormaCabello').each(function(){
            			$('#cmpFormaCabello').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpFormaCabello').removeClass("cargando");
    		}
    	});
    }
	
    function cargaCalvieTipo(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpCalvieTipo').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoCalvieTipo.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catCalvieTipo').each(function(){
            			$('#cmpCalvieTipo').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpCalvieTipo').removeClass("cargando");
    		}
    	});
    }

    function cargaCabelloImplantacion(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpCabelloImplantacion').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoCabelloImplantacion.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catCabelloImplantacion').each(function(){
            			$('#cmpCabelloImplantacion').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpCabelloImplantacion').removeClass("cargando");
    		}
    	});
    }

    function cargaCantidadCabello(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpCantidadCabello').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoCantidadCabello.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catCantidadCabello').each(function(){
            			$('#cmpCantidadCabello').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpCantidadCabello').removeClass("cargando");
    		}
    	});
    }
    
    function cargaOreja(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpOreja').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoOreja.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catOreja').each(function(){
            			$('#cmpOreja').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpOreja').removeClass("cargando");
    		}
    	});
    }

    function cargaTamanoOreja(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpTamanoOreja').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoTamanoOreja.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catTamanoOreja').each(function(){
            			$('#cmpTamanoOreja').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpTamanoOreja').removeClass("cargando");
    		}
    	});
    }
    
    function cargaLobuloParticularidad(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpLobuloParticularidad').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoLobuloParticularidad.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catLobuloParticularidad').each(function(){
            			$('#cmpLobuloParticularidad').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpLobuloParticularidad').removeClass("cargando");
    		}
    	});
    }

    function cargaLobuloDimension(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpLobuloDimension').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoLobuloDimension.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catLobuloDimension').each(function(){
            			$('#cmpLobuloDimension').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpLobuloDimension').removeClass("cargando");
    		}
    	});
    }

    function cargaLobuloAdherencia(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpLobuloAdherencia').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoLobuloAdherencia.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catLobuloAdherencia').each(function(){
            			$('#cmpLobuloAdherencia').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpLobuloAdherencia').removeClass("cargando");
    		}
    	});
    }

    function cargaHelixAnterior(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpHelixAnterior').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoHelixAnterior.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catHelixAnterior').each(function(){
            			$('#cmpHelixAnterior').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpHelixAnterior').removeClass("cargando");
    		}
    	});
    }

    function cargaHelixPosterior(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpHelixPosterior').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoHelixPosterior.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catHelixPosterior').each(function(){
            			$('#cmpHelixPosterior').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpHelixPosterior').removeClass("cargando");
    		}
    	});
    }

    function cargaHelixContorno(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpHelixContorno').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoHelixContorno.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catHelixContorno').each(function(){
            			$('#cmpHelixContorno').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpHelixContorno').removeClass("cargando");
    		}
    	});
    }

    function cargaHelixAdherencia(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpHelixAdherencia').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoHelixAdherencia.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catHelixAdherencia').each(function(){
            			$('#cmpHelixAdherencia').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpHelixAdherencia').removeClass("cargando");
    		}
    	});
    }

    function cargaFormaOreja(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpFormaOreja').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoFormaOreja.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catFormaOreja').each(function(){
            			$('#cmpFormaOreja').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpFormaOreja').removeClass("cargando");
    		}
    	});
    }
    
    function cargaColorOjos(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpColorOjos').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoColorOjos.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catColorOjos').each(function(){
            			$('#cmpColorOjos').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpColorOjos').removeClass("cargando");
    		}
    	});
    }
    
    function cargaTamanoOjos(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpTamanoOjos').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoTamanoOjos.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catTamanoOjos').each(function(){
            			$('#cmpTamanoOjos').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpTamanoOjos').removeClass("cargando");
    		}
    	});
    }
    
    function cargaFormaOjos(esCargaAsincrona){
    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

    	$('#cmpFormaOjos').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoFormaOjos.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
    			var option;
        			$(xml).find('catFormaOjos').each(function(){
            			$('#cmpFormaOjos').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#cmpFormaOjos').removeClass("cargando");
    		}
    	});
    }

        function cargaAlturaNasoLabial(esCargaAsincrona){
        	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

        	$('#altura_nasal').addClass("cargando");
        	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoAlturaNasoLabial.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
        			$(xml).find('catAlturaNasoLabial').each(function(){
            			$('#altura_nasal').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#altura_nasal').removeClass("cargando");
    		}
    	});
    }

        function cargaComisuras(esCargaAsincrona){
        	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

        	$('#comisuras').addClass("cargando");
        	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoComisuras.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
        			$(xml).find('catCatalogoComisuras').each(function(){
            			$('#comisuras').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#comisuras').removeClass("cargando");
    		}
    	});
    }

        function cargaEspesorLabioInferior(esCargaAsincrona){
        	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

        	$('#espesor_labio_inf').addClass("cargando");
        	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoEspesorLabioInferior.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
        			$(xml).find('catEspesorLabioInferior').each(function(){
            			$('#espesor_labio_inf').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#espesor_labio_inf').removeClass("cargando");
    		}
    	});
    }

        function cargaEspesorLabioSuperior(esCargaAsincrona){
        	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

        	$('#espesor_labio_sup').addClass("cargando");
        	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoEspesorLabioSuperior.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
        			$(xml).find('catEspesorLabioSuperior').each(function(){
            			$('#espesor_labio_sup').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#espesor_labio_sup').removeClass("cargando");
    		}
    	});
    }

        function cargaProminencia(esCargaAsincrona){
        	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

        	$('#prominencia').addClass("cargando");
        	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogoProminencia.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
        			$(xml).find('catProminencia').each(function(){
            			$('#prominencia').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#prominencia').removeClass("cargando");
    		}
    	});
    }

        function cargaCatalogoPonEn(catalogo, idObjetivo){
        	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

        	$('#' + idObjetivo).addClass("cargando");
        	$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/ConsultarCatalogo' + catalogo + '.do',
    		data: '',
    		dataType: 'xml',
    		async: esCargaAsincrona,
    		success: function(xml){
        			$(xml).find('cat' + catalogo).each(function(){
            			$('#' + idObjetivo).append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#' + idObjetivo).removeClass("cargando");
    		}
    	});
    }

        function cargaImplantacionCeja(esCargaAsincrona){
        	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

        	$('#cmpImplantacionCejas').addClass("cargando");
        	$.ajax({
	    		type: 'POST',
	    		url: contextoPagina + '/consultarCatalogoImplantacionCeja.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: esCargaAsincrona,
	    		success: function(xml){
	    			var option;
	        			$(xml).find('catImplantacionCeja').each(function(){
	            			$('#cmpImplantacionCejas').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
	    			});
        			$('#cmpImplantacionCejas').removeClass("cargando");
	    		}
	    	});
	    }
        
	    function cargaFormaCejas(esCargaAsincrona){
	    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

	    	$('#cmpFormaCejas').addClass("cargando");
	    	$.ajax({
	    		type: 'POST',
	    		url: contextoPagina + '/consultarCatalogoFormaCeja.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: esCargaAsincrona,
	    		success: function(xml){
	    			var option;
	        			$(xml).find('catFormaCeja').each(function(){
	            			$('#cmpFormaCejas').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
	    			});
        			$('#cmpFormaCejas').removeClass("cargando");
	    		}
	    	});
	    }
	    function cargaTamanoCeja(esCargaAsincrona){
	    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

	    	$('#cmpTamanoCejas').addClass("cargando");
	    	$.ajax({
	    		type: 'POST',
	    		url: contextoPagina + '/consultarCatalogoTamanoCeja.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: esCargaAsincrona,
	    		success: function(xml){
	    			var option;
	        			$(xml).find('catTamanoCeja').each(function(){
	            			$('#cmpTamanoCejas').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
	    			});
        			$('#cmpTamanoCejas').removeClass("cargando");
	    		}
	    	});
	    }

	    function cargaTipoSangre(esCargaAsincrona){
	    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

	    	$('#cmpTipoSangre').addClass("cargando");
	    	$.ajax({
	    		type: 'POST',
	    		url: contextoPagina + '/consultarCatalogoTipoSangre.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: esCargaAsincrona,
	    		success: function(xml){
	    			var option;
	        			$(xml).find('catTipoSangre').each(function(){
	            			$('#cmpTipoSangre').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
	    			});
        			$('#cmpTipoSangre').removeClass("cargando");
	    		}
	    	});
	    }

	    function cargaComplexion(esCargaAsincrona){
	    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

	    	$('#cmpComplexion').addClass("cargando");
	    	$.ajax({
	    		type: 'POST',
	    		url: contextoPagina + '/consultarComplexion.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: esCargaAsincrona,
	    		success: function(xml){
	    			var option;
	    			$(xml).find('catComplexion').each(function(){
            			$('#cmpComplexion').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
        			});
	    			$('#cmpComplexion').removeClass("cargando");
	    		}
	    	});
	    }
	   
	    function cargaOrejaLobContorno(esCargaAsincrona){
	    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

	    	$('#cmplobuloContorno').addClass("cargando");
	    	$.ajax({
	    		type: 'POST',
	    		url: contextoPagina + '/consultarOrejaLobContorno.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: esCargaAsincrona,
	    		success: function(xml){
	    			var option;
	    			$(xml).find('catOrejaLobContorno').each(function(){
            			$('#cmplobuloContorno').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
	        			});
	    			$('#cmplobuloContorno').removeClass("cargando");
	    		}
	    	});
	    }
	    
	    function cargaHelixOriginal(esCargaAsincrona){
	    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

	    	$('#cmpHelixOriginal').addClass("cargando");
	    	$.ajax({
	    		type: 'POST',
	    		url: contextoPagina + '/consultarHelixOriginal.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: esCargaAsincrona,
	    		success: function(xml){
	    			var option;
	    			$(xml).find('cathelixOriginal').each(function(){
            			$('#cmpHelixOriginal').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
        			});
	    			$('#cmpHelixOriginal').removeClass("cargando");
	    		}
	    	});
	    }
	     
	    
	    function cargaDireccionCeja(esCargaAsincrona){
	    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;

	    	$('#cmpDireccionCejas').addClass("cargando");
	    	$.ajax({
	    		type: 'POST',
	    		url: contextoPagina + '/consultarDireccionCeja.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: esCargaAsincrona,
	    		success: function(xml){
	    			var option;
	    			$(xml).find('catdireccionCeja').each(function(){
            			$('#cmpDireccionCejas').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
	        			});
	    			$('#cmpDireccionCejas').removeClass("cargando");
	    		}
	    	});
	    }
	    
	    
	    function cargaCatalogoPonEn(catalogo, idObjetivo, esCargaAsincrona){
	    	esCargaAsincrona === undefined ? esCargaAsincrona = false : esCargaAsincrona = true;
	    	$('#' + idObjetivo).addClass("cargando");
	    	$.ajax({
	    		type: 'POST',
	    		url: contextoPagina + '/ConsultarCatalogo' + catalogo + '.do',
	    		data: '',
	    		dataType: 'xml',
	    		async: esCargaAsincrona,
	    		success: function(xml){
	        			$(xml).find('cat' + catalogo).each(function(){
	            			$('#' + idObjetivo).append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
	    			});
	        	    $('#' + idObjetivo).removeClass("cargando");
	    		}
	    	});
	    }
