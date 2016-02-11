<%@page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />


<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
<script	src="<%=request.getContextPath()%>/resources/js/validate/jquery.maskedinput.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/validate/mktSignup.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>


<script type="text/javascript">
var verAlias=0;
var numNacionalidadTotal=0;
var numOcupacionTotal=0;

//Se obtienen para persistirlos en pintaDatosGenerales
var tipoEvento = "";
var subtipoEvento = "";
var TESTIGO="testigo";
var probableResponsable = "PROBABLE_RESPONSABLE";
   jQuery().ready(	function () {
	   
	   if ((typeof(esVictimaMuerta) !== "undefined") && !isEmpty(esVictimaMuerta)&& esVictimaMuerta ){
		   $("#tdDatosGrlsVictimaMuerta").show();
			$("#tdSexoNoEspecificado").show();   
	   }else{		   
	    $("#tdDatosGrlsVictimaMuerta").hide();
		$("#tdSexoNoEspecificado").hide();
	   }
	   

	    //agregamos el listener para el textbox de nombre cuando se genera dinamicamente 
		$(".masmas").live("keypress", function(event) {
			return soloLetrasNPunto(event,"dummy");
		});
	    
		$("#trEdoFisico").hide();
		$("#trEdoConsciencia").hide();
		if($("#cbxEdoInconciente").val() <= 0 && $("#cbxEdoConciencia").val() <= 0){
			$("#trEdoConscienciaInconsciente").hide();	
		}
	    
		var el = $("#datosGeneralesCmpAlias").multiselect(),
		selected = $('#selected'),
		newItem = $('#newItem');
		
		if (typeof (valorCalidad) !== "undefined" && valorCalidad != null 
				&& valorCalidad == TESTIGO) {
			cargaCalendarioFechaNacimientoParaTestigo()
		} else {
			cargaCalendarioFechaNacimientoDefault();
		}
		
	 	if (typeof (valorCalidad) !== "undefined" && valorCalidad != null 
				&& valorCalidad == probableResponsable) {
			muestraCombosEdosFisicoConsciencia();
			cargaComboEdoFisico();
			cargaComboEdoConsciencia();
		} 
		
		jQuery(document).ajaxStop(desbloquearPantalla());
	});
   
    function agregaAlias(){
		var el = $("#datosGeneralesCmpAlias").multiselect(),
			selected = $('#selected'),
			newItem = $('#newItem');
    	
		var v = newItem.val(), opt = $('<option />', {
			value: v,
			text: v
		});
		
		opt.attr('selected','selected');
		opt.appendTo( el );
		el.multiselect('refresh');
		$("#newItem").val('');
    }
	 
	  function camposGeneralesValidos(){
		  
		  var datosCorrectos = 1;
		  var camposInvalidos = "";
		  var curp = $("#datosGeneralesCmpCurp").val();
		  var rfc = $("#datosGeneralesCmpRfc").val();
		  
		  // Permite validar la CURP
		  var expCurp = /^[A-Z Ñ]{4}\d{2}(1|0)\d(0|1|2|3)\d(H|M)[A-Z Ñ]{5}\d{2}$/;
		  var expCurpSinDigitosVerificadores = /^[A-Z Ñ]{4}\d{2}(1|0)\d(0|1|2|3)\d(H|M)[A-Z Ñ]{5}$/;

		  //propuesta para cambiar la expresion regular
		  var expCurp2 = /^[A-Z]{4}\d{6}[A-Z]{6}(\d{2})?$/i;

		  if(curp != "" && !(expCurp.test(curp) || expCurpSinDigitosVerificadores.test(curp))){
				camposInvalidos += "\n\t - CURP";
				datosCorrectos = 0;
		  }	
			
		  // Permite validar la RFC
		  var expRFC = /^[A-Z Ñ]{4}\d{2}(1|0)\d(0|1|2|3)\d(\w{3})?$/;
		  if(rfc != "" && !expRFC.test(rfc)){
				camposInvalidos += "\n\t - RFC";
				datosCorrectos = 2; 
		  }
		  
		  return datosCorrectos;
	  }
    
	/*
	*Funcion que dispara el Action para consultar el Estado Civil
	*/		
    function cargaEstadoCivil(){
    	$('#datosGeneralesCmpEstadoCivil').addClass("cargando");

    	$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/ConsultarListaEstadoCivil.do',
    		data: '',
    		dataType: 'xml',
    		async: true,
    		success: function(xml){
    			var option;
    			$(xml).find('catEstadoCivil').each(function(){
    				$('#datosGeneralesCmpEstadoCivil').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
       			$('#datosGeneralesCmpEstadoCivil').removeClass("cargando");
    		}
    	});
    }
		
	// Función invocada desde la ventana de ingresar involucrado
	function bloqueaAgregaAlias(){
		$("#add").hide();	
	}
	
    function cargaOcupacion(){
        $('#datosGeneralesCmpOcupacion').addClass("cargando");
    	$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/ConsultarCatalogoOcupacion.do',
    		data: '',
    		dataType: 'xml',
    		async: true,
    		success: function(xml){
    			var option;
    			$(xml).find('catOcupacion').each(function(){
    				$('#datosGeneralesCmpOcupacion').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    				numOcupacionTotal++;
    			});
                        $('#datosGeneralesCmpOcupacion').removeClass("cargando");
    		}
    	});
    }

	/*
	*Funcion que dispara el Action para consultar el Idioma
	*/		
    function cargaIdioma(){
    	$('#datosGeneralesCmpIdioma').addClass("cargando");

		//variables para seleccionar el idioma Español
		var indexEsp, idioma=0;
    	$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/ConsultarCatalogoIdioma.do',
    		data: '',
    		dataType: 'xml',
    		async: true,
    		success: function(xml){
    			var option;
    			$(xml).find('catIdioma').each(function(){
    				$('#datosGeneralesCmpIdioma').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    				idioma++;
    				if($(this).find('valor').text() == "Español" || $(this).find('valor').text() == "ESPAÑOL" || $(this).find('valor').text() == "Espanol"){ indexEsp = idioma; }
    			});
       			$('#datosGeneralesCmpIdioma').removeClass("cargando");
    		}
    	});
    	$('#datosGeneralesCmpIdioma').attr('selectedIndex',indexEsp);
    }

    /*
	*Funcion que dispara el Action para consultar Nacionalidad
	*/		
    function cargaNacionalidad(){
    	  	$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/ConsultarCatalogoNacionalidad.do',
    		data: '',
    		dataType: 'xml',
    		async: true,
    		success: function(xml){
    			$(xml).find('catNacionalidad').each(function(){
    				$('#datosGeneralesCmpNacionalidad').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    				numNacionalidadTotal++;
    			});
    		$('#datosGeneralesCmpNacionalidad').find("option[value='2056']").attr("selected","selected");//default mexicana
    		}
    	});
    }    
    
    function cargaEscolaridad(){
    	$('#datosGeneralesCmpEscolaridad').addClass("cargando");

    	$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/ConsultarCatalogoEscolaridad.do',
    		data: '',
    		dataType: 'xml',
    		async: true,
    		success: function(xml){
    			var option;
    			$(xml).find('catEscolaridad').each(function(){
    				$('#datosGeneralesCmpEscolaridad').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    			});
    			
    			$('#datosGeneralesCmpEscolaridad').removeClass("cargando");

    		}
    	});
    } 
	  
   
    
    function cargaReligion(){
    	$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/ConsultarCatalogoReligion.do',
    		data: '',
    		dataType: 'xml',
    		async: true,
    		success: function(xml){
    			var option;
    			$(xml).find('catReligion').each(function(){
    				$('#datosGeneralesCmpReligion').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
    				
    			});
    		}
    	});
    }

    
	function cargaFechaCaptura(){
    	$.ajax({
    		type: 'POST',
    	    url: '<%=request.getContextPath()%>/ConsultarFechaCaptura.do',
    	    data: '',
    	    dataType: 'xml',
    	    async: true,
    	    success: function(xml){
    			$('#datosGeneralesCmpFechaIngreso').val($(xml).find('fechaActual').text());
    		}
		});
    }
    


  //Funcion que refleja los datos de nombre, apellido paterno, apellido materno a la ventana padre
	function espejoDatos(){
		var valorCampoNombre;
		var valorCampoApPat;
		var valorCampoApMat;
		
		valorCampoNombre=document.getElementById('datosGeneralesCmpNombres').value;
		valorCampoApPat=document.getElementById('datosGeneralesCmpApaterno').value;
		valorCampoApMat=document.getElementById('datosGeneralesCmpMaterno').value;

		try{
			//llama a la funcion que escribe los datos en la ventana padre
			imprimeDatosPadre(valorCampoNombre, valorCampoApPat,valorCampoApMat);
		}catch(e){
			//Si no existe la funcion en el padre
		}
	}

	/**
	 *Limpia todos los campos de esta página
	 */
	function cleanDatosGenerales(){

		$('#datosGeneralesCmpApaterno').val("");
		$('#datosGeneralesCmpMaterno').val("");
		$('#datosGeneralesCmpNombres').val("");
		$('#datosGeneralesCmpCurp').val("");
		$('#datosGeneralesCmpRfc').val("");
		$('#newItem').val("");

		
		//limpia los datos de la ventana padre
		imprimeDatosPadre("","","");
		//Limpia todos los combo box
		
		$('#datosGeneralesCmpIdioma').attr('selectedIndex',0);
		$('#datosGeneralesCmpReligion').attr('selectedIndex',0);
		$('#datosGeneralesCmpEscolaridad').attr('selectedIndex',0);
		$('#datosGeneralesCmpEstadoCivil').attr('selectedIndex',0);
		

		$('#cbxEdoFisico').attr('selectedIndex',0);
		$('#cbxEdoConciencia').attr('selectedIndex',0);

		//combos  multi select
		$("#datosGeneralesCmpAlias").multiselect("uncheckAll");
		$("#datosGeneralesCmpOcupacion,#datosGeneralesCmpNacionalidad").multiselect("uncheckAll");
		$("#datosGeneralesCmpAlias").empty();
		$("#datosGeneralesCmpAlias").multiselect("refresh");
	}

	function mandaDatosPadre(){
    	var valorCampoNombre=$('#datosGeneralesCmpNombres').val();
		var valorCampoApPat=$('#datosGeneralesCmpApaterno').val();
		var valorCampoApMat=$('#datosGeneralesCmpMaterno').val();
		var valorCampoCurp=$('#datosGeneralesCmpCurp').val();
		var valorCampoRfc=$('#datosGeneralesCmpRfc').val();
		var valorCampoFechaNac=$('#datosNacimientoCmpFechaNacimiento').val();
		var valorCampoEdadAproximada=$('#datosNacimientoCmpEdadAproximada').val();
 		validaDatosFormatoTipo(valorCampoNombre, valorCampoApPat,valorCampoApMat,valorCampoCurp,valorCampoRfc,valorCampoFechaNac,valorCampoEdadAproximada);
	}

	function obtenerParametrosDatosGenerales(){
        //Lugar de nacimiento esta pendiente ya que es un campo en BD pero en la pantalla vienen 3 campos, pais, estado y municipio
        var parametros = '&nombre=' + trim($('#datosGeneralesCmpNombres').val());
        parametros += '&apellidoPaterno=' +  trim($('#datosGeneralesCmpApaterno').val());
        parametros += '&apellidoMaterno=' + trim($('#datosGeneralesCmpMaterno').val());
        parametros += '&curp=' + $('#datosGeneralesCmpCurp').val();
        parametros += '&rfc=' + $('#datosGeneralesCmpRfc').val();
        parametros += '&fechaIngreso=' + $('#datosGeneralesCmpFechaIngreso').val();
        parametros += '&idioma=' + $('#datosGeneralesCmpIdioma option:selected').val();        
        parametros += '&religion=' + $('#datosGeneralesCmpReligion option:selected').val();
        parametros += '&escolaridad=' + $('#datosGeneralesCmpEscolaridad option:selected').val();
        parametros += '&estadoCivil=' + $('#datosGeneralesCmpEstadoCivil option:selected').val();
        parametros += '&sexo=' + $(':radio[name=rbtSexoDatosGenerales]:checked').val();
        var alias=$('#datosGeneralesCmpAlias').val();
        if(alias!=null)
        {
        	parametros += '&alias=' + $('#datosGeneralesCmpAlias').val();	
        }
        var ocupaciones="";
        for(i=0;i<numOcupacionTotal;i++){
			if($("#ui-multiselect-datosGeneralesCmpOcupacion-option-"+i).is(':checked')==true){
				if(ocupaciones==""){
					ocupaciones+=$("#ui-multiselect-datosGeneralesCmpOcupacion-option-"+i).val();
				}
				else{
					ocupaciones+=","+$("#ui-multiselect-datosGeneralesCmpOcupacion-option-"+i).val();
				}
			}
		}
        parametros += '&ocupacion=' + ocupaciones;
        var nacionalidades="";
        for(i=0;i<numNacionalidadTotal;i++){
			if($("#ui-multiselect-datosGeneralesCmpNacionalidad-option-"+i).is(':checked')==true){
				if(nacionalidades==""){
					nacionalidades+=$("#ui-multiselect-datosGeneralesCmpNacionalidad-option-"+i).val();
				}
				else{
					nacionalidades+=","+$("#ui-multiselect-datosGeneralesCmpNacionalidad-option-"+i).val();
				}
			}
		}
        parametros += '&nacionalidad=' + nacionalidades;
        parametros += '&tipoEvento=' + tipoEvento;
        parametros += '&subtipoEvento=' + subtipoEvento;
        parametros += '&edoFisico=' + $('#cbxEdoFisico option:selected').val();
        parametros += '&edoConsciencia=' + $('#cbxEdoConciencia option:selected').val();
        parametros += '&edoConscienciaInconsciente=' + $('#cbxEdoInconciente option:selected').val();
        //victima muerta
        parametros += '&edadDefuncion=' + $('#edadVictimaMuerta').val();
        parametros += '&edadUnidadDefuncion=' + $('#cbxEdadUnidadVictimaMuerta option:selected').val();
        parametros += '&condicionActividad=' + $('#cbxCondicionActividadVictimaMuerta option:selected').val();
        
        
		return parametros;
	}
			
		 function recuperaDatosDatosGenerales(idCalidad)
		   {
			   var lsDatosGenerales="";
			   lsDatosGenerales="nombre="+$("#datosGeneralesCmpNombres").val();
			   lsDatosGenerales+="&alias="+$("#datosGeneralesCmpAlias option:selected").val();
			   lsDatosGenerales+="&aPaterno="+$("#datosGeneralesCmpApaterno").val();
			   var ocupaciones="";
		        for(i=0;i<numOcupacionTotal;i++){
					if($("#ui-multiselect-datosGeneralesCmpOcupacion-option-"+i).is(':checked')==true){
						if(ocupaciones==""){
							ocupaciones+=$("#ui-multiselect-datosGeneralesCmpOcupacion-option-"+i).val();
						}
						else{
							ocupaciones+=","+$("#ui-multiselect-datosGeneralesCmpOcupacion-option-"+i).val();
						}
					}
				}
		       lsDatosGenerales+="&ocupacion="+ocupaciones;
			   lsDatosGenerales+="&aMaterno="+$("#datosGeneralesCmpMaterno").val();
		        var nacionalidades="";
		        for(i=0;i<numNacionalidadTotal;i++){
					if($("#ui-multiselect-datosGeneralesCmpNacionalidad-option-"+i).is(':checked')==true){
						if(nacionalidades==""){
							nacionalidades+=$("#ui-multiselect-datosGeneralesCmpNacionalidad-option-"+i).val();
						}
						else{
							nacionalidades+=","+$("#ui-multiselect-datosGeneralesCmpNacionalidad-option-"+i).val();
						}
					}
				}
			   lsDatosGenerales+="&nacionalidad="+nacionalidades;
			   lsDatosGenerales+="&curp="+$("#datosGeneralesCmpCurp").val();
			   lsDatosGenerales+="&rfc="+$("#datosGeneralesCmpRfc").val();
			   lsDatosGenerales+="&fIngreso="+$("#datosGeneralesCmpFechaIngreso").val();
			   lsDatosGenerales+="&idioma="+$("#datosGeneralesCmpIdioma option:selected").val();
			   lsDatosGenerales+="&religion="+$("#datosGeneralesCmpReligion option:selected").val();
			   lsDatosGenerales+="&escolaridad="+$("#datosGeneralesCmpIdioma option:selected").val();
			   //lsDatosGenerales+="&estadocivil="+$("#datosGeneralesCmpReligion option:selected").val();
			   
			   lsDatosGenerales += '&edoFisico=' + $('#cbxEdoFisico option:selected').val();
			   lsDatosGenerales += '&edoConsciencia=' + $('#cbxEdoConciencia option:selected').val();
			   lsDatosGenerales += '&edoConscienciaInconsciente=' + $('#cbxEdoInconciente option:selected').val();
			   return lsDatosGenerales;
		   }

			function deshabilitarDatosGenerales(){
				 $('#datosGeneralesCmpNombres').attr("disabled","disabled");
				 $('#datosGeneralesCmpApaterno').attr("disabled","disabled");
				 $('#datosGeneralesCmpMaterno').attr("disabled","disabled");
				 $('#datosGeneralesCmpCurp').attr("disabled","disabled");
				 $('#datosGeneralesCmpRfc').attr("disabled","disabled");
				 $('#datosGeneralesCmpSexoM').attr("disabled","disabled");
				 $('#datosGeneralesCmpSexoF').attr("disabled","disabled");
				 $('#datosGeneralesCmpSexoN').attr("disabled","disabled");
				 $('#datosGeneralesCmpEscolaridad').attr("disabled","disabled");
				 $('#datosGeneralesCmpIdioma').attr("disabled","disabled");
				 $('#datosGeneralesCmpEstadoCivil').attr("disabled","disabled");
				 $('#datosGeneralesCmpAlias').attr("disabled","disabled");
				 $('#datosGeneralesCmpOcupacion').attr("disabled","disabled");
				 $('#datosGeneralesCmpNacionalidad').attr("disabled","disabled");
				 $('#datosNacimientoCmpFechaNacimiento').attr("disabled","disabled");
				 
				 $('#datosGeneralesCmpNacionalidad').multiselect("disable");
				 $('#datosGeneralesCmpOcupacion').multiselect("disable");
				 $('#datosGeneralesCmpAlias').multiselect("disable");

				 $('#datosNacimientoCmpEdadAproximada').attr("disabled","disabled");
				 $('#cbxPaisNacimiento').attr("disabled","disabled");
				 $('#cbxEntFederativaNacimiento').attr("disabled","disabled");
				 $('#cbxCiudadNacimiento').attr("disabled","disabled");
				 
				 $('#cbxEdoFisico').attr("disabled","disabled");
				 $('#cbxEdoConciencia').attr("disabled","disabled");
				 $('#cbxEdoInconciente').attr("disabled","disabled");
				 
				 $('#edadVictimaMuerta').attr("disabled","disabled");
				 $('#cbxEdadUnidadVictimaMuerta').attr("disabled","disabled");
				 $('#cbxCondicionActividadVictimaMuerta').attr("disabled","disabled");
				 
				 $("#newItem,#add").attr("disabled","disabled");
				//Modo consulta
				 cambiarModoConsultaMultiselect(true);
			}

			// Función invocada desde la ventana de ingresar involucrado
			function habilitaDatosEspecificos(){
				 $('#datosGeneralesCmpOcupacion').attr("disabled","");
				 $('#datosGeneralesCmpNacionalidad').attr("disabled","");
				 $('#datosGeneralesCmpAlias').attr("disabled","");
				/*var $widget = $("#datosGeneralesCmpAlias,#datosGeneralesCmpOcupacion,#datosGeneralesCmpNacionalidad").multiselect(),state = true;
				   state = !state;
				   $widget.multiselect(state ? 'disable' : 'enable');*/
 			}
			
			function habilitarDatosGenerales(){
				$('#datosGeneralesCmpNombres').attr("disabled","");
				$('#datosGeneralesCmpApaterno').attr("disabled","");
				 $('#datosGeneralesCmpMaterno').attr("disabled","");
				 $('#datosGeneralesCmpCurp').attr("disabled","");
				 $('#datosGeneralesCmpRfc').attr("disabled","");
				 $('#datosGeneralesCmpSexoM').attr("disabled","");
				 $('#datosGeneralesCmpSexoF').attr("disabled","");
				 $('#datosGeneralesCmpSexoN').attr("disabled","");
				 $('#datosGeneralesCmpEscolaridad').attr("disabled","");
				 $('#datosGeneralesCmpOcupacion').attr("disabled","");
				 $('#datosGeneralesCmpNacionalidad').attr("disabled","");
				 $('#cbxCiudadNacimiento').attr("disabled","");
				 // $('#datosGeneralesCmpEscolaridad').attr("disabled","");

				 //Modo Edicion
				 cambiarModoConsultaMultiselect(false);
				
				 $("#newItem,#add").attr("disabled","");
				 $('#datosGeneralesCmpIdioma').attr("disabled","");
				 $('#datosGeneralesCmpEstadoCivil').attr("disabled","");
				 $('#datosNacimientoCmpFechaNacimiento').attr("disabled","");
				 $("#datosNacimientoCmpFechaNacimiento").datepicker({
						dateFormat: 'dd/mm/yy',
						yearRange: '1900:2100',
						changeMonth: true,
						changeYear: true,
						showOn: "button",
						buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
						buttonImageOnly: true			
					});
				 $('#datosNacimientoCmpEdadAproximada').attr("disabled","");
				 $('#cbxPaisNacimiento').attr("disabled","");
				 $('#cbxEntFederativaNacimiento').attr("disabled","");
				 
				 $('#cbxEdoFisico').attr("disabled","");
				 $('#cbxEdoConciencia').attr("disabled","");
				 $('#cbxEdoInconciente').attr("disabled","");
				 
				 $('#edadVictimaMuerta').attr("disabled","");
				 $('#cbxEdadUnidadVictimaMuerta').attr("disabled","");
				 $('#cbxCondicionActividadVictimaMuerta').attr("disabled","");
			} 

			
		   function inicializaDatosGenerales(){
			   var $widget = $("#datosGeneralesCmpAlias,#datosGeneralesCmpOcupacion,#datosGeneralesCmpNacionalidad").multiselect(),state = true;
			   state = !state;
			   $widget.multiselect(state ? 'disable' : 'enable');
		   }
			
		   /**
		   * De cada uno de los catalogos del multiselect, se seleccionan los particulares
		   * de acuerdo a la consulta generada del involucrado.
		   */
		   function pintaDatosMultiselect(xml){
			   var valorAlias;
			   if($(xml).find('valorIdOcupacion').find('ValorDTO').text()!=""){
				   valorAlias='ValorDTO';
			   }
			   else{
				   valorAlias='valorDTO';
			   }
			   
			   var ocupacionCargada=false;
			   $(xml).find('valorIdOcupacion').find(valorAlias).each(function(){
				   var ocupacion=$(this).find('idCampo').text();
				   $('#datosGeneralesCmpOcupacion').find("option[value='"+ocupacion+"']").attr("selected","selected");
				   ocupacionCargada=true;
			   });
			   
			   if (!ocupacionCargada){
				   $(xml).find('valorIdOcupacion').find('idCampo').each(function(){
					   var ocupacion=$(this).text();
					   $('#datosGeneralesCmpOcupacion').find("option[value='"+ocupacion+"']").attr("selected","selected");
					   ocupacionCargada=true;
				   }); 
			   }
			   $("#datosGeneralesCmpOcupacion").multiselect("refresh");
			   
			   //Nacionalidad
			   if($(xml).find('valorIdNacionalidad').find('ValorDTO').text()!=""){
				   valorAlias='ValorDTO';
			   }
			   else{
				   valorAlias='valorDTO';
			   }
			   
			   $('#datosGeneralesCmpNacionalidad').find("option[value='2056']").attr("selected","");//quita default mexicana
			   var nacionCargada=false;
			   $(xml).find('valorIdNacionalidad').find(valorAlias).each(function(){
				   var nacionalidad=$(this).find('idCampo').text();
				   $('#datosGeneralesCmpNacionalidad').find("option[value='"+nacionalidad+"']").attr("selected","selected");
				   nacionCargada=true;
			   });
			   
			   if (!nacionCargada){
				   $(xml).find('valorIdNacionalidad').find('idCampo').each(function(){
					   var nacionalidad=$(this).text();
					   $('#datosGeneralesCmpNacionalidad').find("option[value='"+nacionalidad+"']").attr("selected","selected");
				   }); 
			   }
			   $("#datosGeneralesCmpNacionalidad").multiselect("refresh");
			   
			   //Alias
			   $('#datosGeneralesCmpAlias').empty();
			   $(xml).find('aliasInvolucradoDTO').find('aliasInvolucradoDTO').each(function(){
				   $("#datosGeneralesCmpAlias").append('<option selected="selected" disabled="disabled" value="' + $(this).find('alias').text() + '">' + $(this).find('alias').text() + '</option>');

			   });

			   $("#datosGeneralesCmpAlias").multiselect("refresh");
		   }
		   
		   /**
		   * Carga solo los datos que se tienen para consulta, omitiendo los datos del catalogo. 
		   **/
		   //@deprecated No se utiliza
		   function pintaDatosMultiselectSoloConsulta(xml){
			   //Ocupacion Consulta
			   var valorAlias;
			   if($(xml).find('valorIdOcupacion').find('ValorDTO').text()!=""){
				   valorAlias='ValorDTO';
			   }
			   else{
				   valorAlias='valorDTO';
			   }
			   
			   $('#datosGeneralesCmpOcupacion').empty();
			   $(xml).find('valorIdOcupacion').find(valorAlias).each(function(){
				   $("#datosGeneralesCmpOcupacion").append('<option selected="selected" value="' + $(this).find('idCampo').text() + '">' + $(this).find('valor').text() + '</option>');
			   });
			   
			   //Nacionalidad Consulta
			   if($(xml).find('valorIdNacionalidad').find('ValorDTO').text()!=""){
				   valorAlias='ValorDTO';
			   }
			   else{
				   valorAlias='valorDTO';
			   }
			   
			   $('#datosGeneralesCmpNacionalidad').empty();
			   $(xml).find('valorIdNacionalidad').find(valorAlias).each(function(){
				   $("#datosGeneralesCmpNacionalidad").append('<option selected="selected" value="' + $(this).find('idcampo').text() + '">' + $(this).find('valor').text() + '</option>');
			   });
			   
			   //Alias
			   $('#datosGeneralesCmpAlias').empty();
			   
			   $(xml).find('aliasInvolucradoDTO').find('aliasInvolucradoDTO').each(function(){
				   $("#datosGeneralesCmpAlias").append('<option selected="selected" value="' + $(this).find('alias').text() + '">' + $(this).find('alias').text() + '</option>');
    	       });
		   }
		   
		   /**
		   * Funci&oacute;n que se encarga de habilitar o deshabilitar los checkbox de los 
		   * multiselect de los datos generales.
		   *
		   * @param esConsulta parametro que determina si es modo consulta o edici&oacute;n
		   */
		   function cambiarModoConsultaMultiselect(esConsulta){
			   //Versión anterior de habilitar  ver habilitarDatosGenerales()
			   /* var $widget = $("#datosGeneralesCmpAlias,#datosGeneralesCmpOcupacion,#datosGeneralesCmpNacionalidad").multiselect(),state = true;
			   state = !state;
			   $widget.multiselect( ? 'disable' : 'enable'); */

				//El multiselect simpre debe de estar habilitado
			   var $widget = $("#datosGeneralesCmpAlias,#datosGeneralesCmpOcupacion,#datosGeneralesCmpNacionalidad").multiselect();
			   $widget.multiselect('enable');
			   
			   //Se habilita o deshabilita los check box
			   $("#datosGeneralesCmpOcupacion").multiselect("widget").find(":checkbox").attr("disabled", esConsulta);
			   $("#datosGeneralesCmpAlias").multiselect("widget").find(":checkbox").attr("disabled",esConsulta);
			   $("#datosGeneralesCmpNacionalidad").multiselect("widget").find(":checkbox").attr("disabled",esConsulta);
		   }
		   
		   function pintaDatosGenerales(xml){
			   tipoEvento = $(xml).contents().children("tipoEvento").text();
			   subtipoEvento = $(xml).contents().children("subtipoDeEvento").text();
			   $('#datosGeneralesCmpNombres').val($(xml).find('nombresDemograficoDTO').find('nombre').first().text());
			   $('#datosGeneralesCmpApaterno').val($(xml).find('nombresDemograficoDTO').find('apellidoPaterno').first().text());
			   $('#datosGeneralesCmpMaterno').val($(xml).find('nombresDemograficoDTO').find('apellidoMaterno').first().text());
			   $('#datosGeneralesCmpCurp').val($(xml).find('nombresDemograficoDTO').find('curp').first().text());			  
			   $('#datosGeneralesCmpRfc').val($(xml).find('nombresDemograficoDTO').find('rfc').first().text());
			   if ($(xml).find('nombresDemograficoDTO').first().find('sexo').text() == "M"){
		    		 $('#datosGeneralesCmpSexoM').attr('checked','checked');
		    		
		    	 }else{
		    		 if ($(xml).find('nombresDemograficoDTO').first().find('sexo').text() == "F"){
		    			 $('#datosGeneralesCmpSexoF').attr('checked','checked');
			    		
			    	 }else{
			    		 $('#datosGeneralesCmpSexoN').attr('checked','checked');
			    	 }
		    		 
		    	 } 
			  var escol=$(xml).find('valorIdEscolaridad').find('idCampo').text();
			   $('#datosGeneralesCmpEscolaridad').find("option[value='"+escol+"']").attr("selected","selected");
			   var estadoCiv=$(xml).find('valorIdEstadoCivil').find('idCampo').text();
			   $('#datosGeneralesCmpEstadoCivil').find("option[value='"+estadoCiv+"']").attr("selected","selected");
			   var idioma=$(xml).find('valorIdIdioma').find('idCampo').text();
			   $('#datosGeneralesCmpIdioma').find("option[value='"+idioma+"']").attr("selected","selected");
			   
			   cargaComboEdoFisico();
			   $('#cbxEdoFisico').find("option[value='"+$(xml).find('nombresDemograficoDTO').find('edoFisico').find('idCampo').text()+"']").attr("selected","selected");
			   cargaComboEdoConsciencia();
			   $('#cbxEdoConciencia').find("option[value='"+$(xml).find('nombresDemograficoDTO').find('edoConsciencia').find('idCampo').text()+"']").attr("selected","selected");
			   cargaComboEdoConscienciaInconsciente();
			   $('#cbxEdoInconciente').find("option[value='"+$(xml).find('nombresDemograficoDTO').find('edoConscienciaInconsciente').find('idCampo').text()+"']").attr("selected","selected");
			   
			   pintaDatosNacimiento(xml);
			   
			   var fechaCreacion = $(xml).find('involucradoDTO').find('strFechaCreacion').first().text();
			   if (fechaCreacion != "" && fechaCreacion != undefined ){
				   $('#datosGeneralesCmpFechaIngreso').val(fechaCreacion);
			   }
			   
			   //edad victima muerta
			   $('#edadVictimaMuerta').val($(xml).find('datosDefuncion').find('edadDefuncion').text());
			   //edad unidad victima muerta
			   var edadUnidadDef=$(xml).find('datosDefuncion').find('edadUnidadDefuncion').find('idCampo').text();
			   $('#cbxEdadUnidadVictimaMuerta').find("option[value='"+edadUnidadDef+"']").attr("selected","selected");
			   //condicion de actividad 
			   var condicionAct=$(xml).find('datosDefuncion').find('condicionActividad').find('idCampo').text();
			   $('#cbxCondicionActividadVictimaMuerta').find("option[value='"+condicionAct+"']").attr("selected","selected");
			   
			   //pintaDatosNacimientoActaCirc(xml);//Para pintar los datos de nacimiento en Acta Circunstanciada
			   //alert("wait");
			   //$('#datosNacimientoCmpFechaNacimiento').val('00/02/0000');
			   espejoDatos();
			}
		   
		   function pintaDatosGeneralesActaCirc(xml){
			   
			   var valorAlias;
			   
			   $('#datosGeneralesCmpNombres').val($(xml).find('nombresDemograficoDTO').find('nombre').text());
			   $('#datosGeneralesCmpApaterno').val($(xml).find('nombresDemograficoDTO').find('apellidoPaterno').text());
			   $('#datosGeneralesCmpMaterno').val($(xml).find('nombresDemograficoDTO').find('apellidoMaterno').text());
			   $('#datosGeneralesCmpCurp').val($(xml).find('nombresDemograficoDTO').find('curp').text());			   
			   $('#datosGeneralesCmpRfc').val($(xml).find('nombresDemograficoDTO').find('rfc').text());
			   if ($(xml).find('nombresDemograficoDTO').find('sexo').text() == "M"){
		    		 $('#datosGeneralesCmpSexoM').attr('checked','checked');
		    		
		    	 }else{
		    		 $('#datosGeneralesCmpSexoF').attr('checked','checked');
		    	 } 
			  var escol=$(xml).find('valorIdEscolaridad').find('idCampo').text();
			   $('#datosGeneralesCmpEscolaridad').find("option[value='"+escol+"']").attr("selected","selected");
			   var estadoCiv=$(xml).find('valorIdEstadoCivil').find('idCampo').text();
			   $('#datosGeneralesCmpEstadoCivil').find("option[value='"+estadoCiv+"']").attr("selected","selected");
			   
			   if($(xml).find('valorIdOcupacion').find('ValorDTO').text()!=""){
				   valorAlias='ValorDTO';
			   }
			   else{
				   valorAlias='valorDTO';
			   }
			   
			   var ocupacionCargada=false;
			   $(xml).find('valorIdOcupacion').find(valorAlias).each(function(){
				   var ocupacion=$(this).find('idCampo').text();
				   $('#datosGeneralesCmpOcupacion').find("option[value='"+ocupacion+"']").attr("selected","selected");
				   ocupacionCargada=true;
			   });
			   
			   if (!ocupacionCargada){
				   $(xml).find('valorIdOcupacion').find('idCampo').each(function(){
					   var ocupacion=$(this).text();
					   $('#datosGeneralesCmpOcupacion').find("option[value='"+ocupacion+"']").attr("selected","selected");
					   ocupacionCargada=true;
				   }); 
			   }
			   
			   if($(xml).find('valorIdNacionalidad').find('ValorDTO').text()!=""){
				   valorAlias='ValorDTO';
			   }
			   else{
				   valorAlias='valorDTO';
			   }

			   $('#datosGeneralesCmpNacionalidad').find("option[value='2056']").attr("selected","");//quita default mexicana
			   var nacionCargada=false;
			   $(xml).find('valorIdNacionalidad').find(valorAlias).each(function(){
				   var nacionalidad=$(this).find('idCampo').text();
				   $('#datosGeneralesCmpNacionalidad').find("option[value='"+nacionalidad+"']").attr("selected","selected");
				   nacionCargada=true;
			   });
			   
			   if (!nacionCargada){
				   $(xml).find('valorIdNacionalidad').find('idCampo').each(function(){
					   var nacionalidad=$(this).text();
					   $('#datosGeneralesCmpNacionalidad').find("option[value='"+nacionalidad+"']").attr("selected","selected");
				   }); 
			   }
			   
			   $(xml).find('aliasInvolucradoDTO').find('alias').each(function(){
				   $("#datosGeneralesCmpAlias").append('<option selected="selected" value="' + $(this).text() + '">' + $(this).text() + '</option>');
    	       });
			   
			   pintaDatosNacimientoActaCirc(xml);//Para pintar los datos de nacimiento en Acta Circunstanciada
			}

		   /*
		    *Funcion para pintar los datos mediante la recuperacion del xml de datos de nacimiento
		    */
		function pintaDatosNacimiento(xml){
 
			var id0=$(xml).find('nombresDemograficoDTO').first().find('nombreDemograficoDTO').find('paisValorDTO').find('idCampo').text();
		   	   
			$('#cbxPaisNacimiento').find("option[value='"+id0+"']").attr("selected","selected");
		   	
			onSelectChangePaisNacimiento();

			var id1=$(xml).find('nombresDemograficoDTO').first().find('nombreDemograficoDTO').find('entidadFederativaDTO').find('entidadFederativaId').text();
		   	  
			$('#cbxEntFederativaNacimiento').find("option[value='"+id1+"']").attr("selected","selected");

			onSelectChangeEntFedNacimiento();
		   		
			var id2=$(xml).find('nombresDemograficoDTO').first().find('nombreDemograficoDTO').find('municipioDTO').find('municipioId').text();
			$('#cbxCiudadNacimiento').find("option[value='"+id2+"']").attr("selected","selected");
			   
		   	if($(xml).find('nombresDemograficoDTO').first().find('nombreDemograficoDTO').find('strFechaNacimiento').text()!=""){
			   $('#datosNacimientoCmpFechaNacimiento').val(''+$(xml).find('nombresDemograficoDTO').first().find('nombreDemograficoDTO').find('strFechaNacimiento').text());
			}
			   
			$('#datosNacimientoCmpEdadAproximada').val($(xml).find('nombresDemograficoDTO').first().find('nombreDemograficoDTO').find('edadAproximada').first().text());
		}		 
		   	
		   /*
		    *Funcion para pintar los datos mediante la recuperacion del xml de datos de nacimiento
		    */
		   function pintaDatosNacimientoActaCirc(xml){
			   var id0=$(xml).find('nombresDemograficoDTO').find('paisValorDTO').find('idCampo').text();
		   	   $('#cbxPaisNacimiento').find("option[value='"+id0+"']").attr("selected","selected");
		   		onSelectChangePaisNacimiento();
		   	   
		   	   var id1=$(xml).find('nombresDemograficoDTO').find('entidadFederativaDTO').find('entidadFederativaId').text();
		   	   $('#cbxEntFederativaNacimiento').find("option[value='"+id1+"']").attr("selected","selected");
		   		onSelectChangeEntFedNacimiento();
		   		var id2=$(xml).find('nombresDemograficoDTO').find('municipioDTO').find('municipioId').text();
			   	$('#cbxCiudadNacimiento').find("option[value='"+id2+"']").attr("selected","selected");
			    $('#newItem').attr("disabled","disabled");
			    $('#add').attr("disabled","disabled");
			    $("#datosNacimientoCmpFechaNacimiento").datepicker("destroy");
			    
			    if($(xml).find('nombresDemograficoDTO').find('fechaNacimiento').text()!=""){
					var fecha=$(xml).find('nombresDemograficoDTO').find('fechaNacimiento').text().split(' ');
					var fechaS=fecha[0].split('-');
					
				   $('#datosNacimientoCmpFechaNacimiento').val(fechaS[2]+'/'+fechaS[1]+'/'+fechaS[0]);
				}

			 	$('#datosNacimientoCmpEdadAproximada').val($(xml).find('nombresDemograficoDTO').find('edadAproximada').first().text());
		   	   }	
		   
		   /*
		   *Funcion para revisar la calidad del indiviudo cuando se ingresara un representante legal
		   */
		   function ocultaCamposPorCalidadIndividuo()
		   {
			   if((gingRepLegCalidadDelIndividuo!=null) && (gingRepLegCalidadDelIndividuo!=""))
			   {
					if(gingRepLegCalidadDelIndividuo="REPRESENTANTE_LEGAL")
					{
						//ocultamos los campos que no se deben mostrar cuando es un representante legal
						$("#trAliasTxt").hide();
						$(".tdAliasCmp").hide();
					}
			   }
		   }
		   
		   /* Metodo que permite generar el RFC y el CURP dado el siguiente algoritmo
			1. Primer letra de tu apellido PATERNO
			2. Primer vocal despues de la primer letra de tu apellido PATERNO
			3. Primer letra de tu apellido (materno)
			4. Primer letra de tu nombre
			5. Año de nacimiento (ultimas 2 cifras)
			6. Mes de nacimiento (2 cifras)
			7. Dia de nacimiento (2 cifras)...
			Hasta aqui el RFC... 
			8. Sexo (H) o (M)
			9. Estado donde te registraron... (abreviado 2 letras)
			10. Primera consonante de apellido paterno y materno MRXX
			11. El ultimo numero de 2 cifras es de control para que en el remoto caso no haya repetidos
		*/
	
			function calculaRFC_CURP_UNO_UNO()
		    {
				var valorCampoNombre=$('#datosGeneralesCmpNombres').val().trim();
				var valorCampoApPat=$('#datosGeneralesCmpApaterno').val().trim();
				var valorCampoApMat=$('#datosGeneralesCmpMaterno').val().trim();
				var valorCampoCurp=$('#datosGeneralesCmpCurp').val().trim();
				var valorCampoRfc=$('#datosGeneralesCmpRfc').val();
				var valorCampoFechaNac=$('#datosNacimientoCmpFechaNacimiento').val();
				var sexo = $(':radio[name=rbtSexoDatosGenerales]:checked').val();      
				var idEstado = $('#cbxEntFederativaNacimiento option:selected').val();
				var str = '';
				var strObj = {};
				if(!isEmpty(valorCampoNombre) && !isEmpty(valorCampoApPat)  && !isEmpty(valorCampoFechaNac) && parseInt(idEstado) != -1)
				{
					var paternoStr = valorCampoApPat;
					str = paternoStr.toLowerCase().replace(/de\s|la\s|del\s|'|-/gi, '');
					var consonateAPaterno = str;
					paternoStr = (str.charAt(0) === 'ñ' ? 'X' : str.charAt(0)) + (str.charAt(1) ? obtieneVocal(str) : 'X');
					consonateAPaterno=obtenConsonante(consonateAPaterno);
						
					if(consonateAPaterno.trim()=="" || consonateAPaterno.trim().length==0)
					{
						consonateAPaterno="X";	
					}
						
					var maternoStr = valorCampoApMat;
					var consonateAMaterno ="";
					if(maternoStr.length>0)
					{
						str = maternoStr.toLowerCase().replace(/de\s|la\s|del\s|'|-/gi, '');
						consonateAMaterno = str;
						maternoStr = str.charAt(0) === 'ñ' ? 'X' : str.charAt(0);		
						//consonateAMaterno=consonateAMaterno.substring(1);					
						consonateAMaterno=obtenConsonante(consonateAMaterno);
						if(consonateAMaterno.trim()=="" || consonateAMaterno.trim().length==0)
						{
							consonateAMaterno="X";	
						}
					}
					else
					{
						maternoStr='X';
						consonateAMaterno = "X";
					}
					var nombreStr = obtenLetraNombre(valorCampoNombre);	
					var consonateNombre = valorCampoNombre;
					//consonateNombre=consonateNombre.substring(1);
					consonateNombre=obtenConsonanteNombreDos(consonateNombre);//obtenConsonante
					
					if(consonateNombre.trim()=="" || consonateNombre.trim().length==0)
					{
						consonateNombre="X";
					}
					
					datosFechaNac = valorCampoFechaNac.split("/");
					var dia = datosFechaNac[0];
					var mes = datosFechaNac[1];
					var anio = datosFechaNac[2].substring(2,4);
					
					//definimos la cadena con palabras no permitidas
					var malas ="bueibueycacacacocagacagocakacakocogecojakogekojokakakulomamemamo";
					malas += "mearmeasmeonmioncojecojicojoculofetogueyjotokacakacokaga";
					malas += "kagomocomulapedapedopeneputaputoqulorataruin";
					//buscaremos los caracteres generados para ver sino cae en las malas palabras
					var patt=new RegExp(paternoStr + maternoStr + nombreStr,"i");
					//Si fue una mala palabra cambiaremos la ultima letra por una X
					if(malas.search(patt)>-1)
					{
						//cambiamos la ultima letra por una X
						str = paternoStr + maternoStr + "X" + anio + mes + dia;
					}
					else
					{
						str = paternoStr + maternoStr + nombreStr + anio + mes + dia;
					}	
					
					//Se obitiene la inicial del sexo
					sexo == 'M' ? sexo = 'H':sexo = 'M'
					//Se obtien el valor del estado seleccionado
					var edo = obtenInicialesEstado(parseInt(idEstado)).toUpperCase();
					var rfc = valorCampoRfc && valorCampoRfc.length > 10 ? str + valorCampoRfc.substr(10) : str;
					$('#datosGeneralesCmpRfc').val(rfc.toUpperCase());
					var curp = valorCampoCurp && valorCampoCurp > 18 ? str + valorCampoCurp.substr(18) : str + 
							sexo + edo + consonateAPaterno.charAt(0) + consonateAMaterno.charAt(0) + consonateNombre.charAt(0);
					$('#datosGeneralesCmpCurp').val(curp.toUpperCase());					
				}
				return strObj;
			}
		   
		   
			function obtenConsonante(cadena){
				var consonantes = /^[bcdfghjklmnñpqrstvwxyz]/i;				
				var i = 0;
				var x= true;
				var s1 = '';
				var cadenaOrg=cadena;
				var longitudCadena = cadena.length;
				
				/* while(i < longitudCadena){
					if((consonantes.test(cadena[i]) == true) & (x != false)){
						s1 = s1 + cadena[i];
						cadena = cadena.replace(cadena[i],"");
					}			
					i++;
				 }
				*/
				
				var pattCnsnts=new RegExp("bcdfghjklmnñpqrstvwxyz","i");
				var cadenaConsonantes="bcdfghjklmnñpqrstvwxyz";
				var cadenaCnsnnts="";
				var cadenaVcls="";
				for(i=0;i<cadenaOrg.length;i++)
				{
					if(cadenaConsonantes.indexOf(cadenaOrg.charAt(i))!=-1)
					{
						cadenaCnsnnts += cadenaOrg[i];
					}
					else
					{
						cadenaVcls += cadenaOrg[i];
					}
				}
							
				
				//si la primer letra una consonante
				if(cadenaConsonantes.indexOf(cadenaOrg.charAt(0))!=-1)
				{
					cadenaCnsnnts=cadenaCnsnnts.substring(1);
				}
				return cadenaCnsnnts;
			}
			
			function obtenInicialesEstado(idEstado){
				var edo;
				switch(idEstado){
					case 1:  edo="AS"; break;
					case 2:  edo="BC"; break;
					case 3:  edo="BS"; break;
					case 4:  edo="CC"; break;
					case 5:  edo="CL"; break;
					case 6:  edo="CM"; break;
					case 7:  edo="CS"; break;
					case 8:  edo="CH"; break;
					case 9:  edo="DF"; break;
					case 10: edo="DG"; break;
					case 11: edo="GT"; break;
					case 12: edo="GR"; break;
					case 13: edo="HG"; break;
					case 14: edo="JC"; break;
					case 15: edo="MC"; break;
					case 16: edo="MN"; break;
					case 17: edo="MS"; break;
					case 18: edo="NT"; break;
					case 19: edo="NL"; break;
					case 20: edo="OC"; break;
					case 21: edo="PL"; break;
					case 22: edo="QT"; break;
					case 23: edo="QR"; break;
					case 24: edo="SP"; break;
					case 25: edo="SL"; break;
					case 26: edo="SR"; break;
					case 27: edo="TC"; break;
					case 28: edo="TS"; break;
					case 29: edo="TL"; break;
					case 30: edo="VZ"; break;
					case 31: edo="YN"; break;
					case 32: edo="ZS"; break;
				}
				return edo;	
		}		
			
			
	 		/**
			 * Obtiene la letra correspondiente al nombre de la persona
			 * @param {String} nombreStr Nombre de la persona
			 * @return {char} letraNombre Primera letra del nombre de la persona
			 */
			function obtenLetraNombre(nombreStr){
				var letraNombre = '';
				nombreStr = nombreStr.trim();
				nombreStr = nombreStr.toLowerCase().replace(/de\s|la\s|del\s|'|-/gi, '');
				var letraBase = nombreStr.charAt(0).toLowerCase() === 'ñ' ? 'X' : nombreStr.charAt(0);
				if (nombreStr.split(' ').length >= 2) {
					var jose = quitaAcentos(nombreStr).toLowerCase().search('jose');
					var maria = quitaAcentos(nombreStr).toLowerCase().search('maria');
					if (jose >= 0 && maria >= 0) {
						letraNombre = letraBase;
					} else if (jose === 0 || maria === 0) {
						letraNombre = nombreStr.split(' ')[1].charAt(0);
					} else {
						letraNombre = nombreStr.split(' ')[0].charAt(0);
					}
				} else {
					letraNombre = letraBase;
				}
				return letraNombre;
			}
			 
			 function obtenConsonanteNombreDos(nombreStr){
				 	var letraNombre = '';
					
					nombreStr = nombreStr.trim();
					nombreStr = nombreStr.toLowerCase().replace(/de\s|la\s|del\s|'|-/gi, '');
					var nombreStrOrg=nombreStr;
					var letraBase = nombreStr.charAt(0).toLowerCase() === 'ñ' ? 'X' : nombreStr.charAt(0);
					if (nombreStrOrg.split(' ').length >= 2) {
						var nombreStrJose = quitaAcentos(nombreStr).toLowerCase();
						var nombreStrMaria= quitaAcentos(nombreStr).toLowerCase();
						//buscaremos los caracteres generados para ver sino cae en las malas palabras
						var pattJose=new RegExp("jose","i");
						var pattMaria=new RegExp("maria","i");
						
						var jose= nombreStrJose.search(pattJose);
						var maria= nombreStrMaria.search(pattMaria);
						if (jose >= 0 && maria >= 0) { //solo para los nombre Jose maria o Maria jose
							letraNombre = letraBase;
						} else if (jose == 0 || maria == 0) {
							letraNombre = obtenConsonante(nombreStrOrg.split(' ')[1]);
						}
					    else if (jose > 0 || maria > 0) {
							letraNombre = obtenConsonante(nombreStrOrg.split(' ')[0]);
					    }
						else {
							letraNombre = obtenConsonante(nombreStrOrg.split(' ')[0]);//letraBase;
						}
					} else {
						letraNombre = obtenConsonante(nombreStrOrg);//letraBase;
					}
					return letraNombre;
				}
			
			
	 		/**
			 * Función que limpia una cadena de acentos y dieresis
			 * @param {String} cadena Cadena a limpiar
			 * @return {String} cadena Cadena sin acentos y dieresis
			 */
			function quitaAcentos(cadena){
				return cadena != "" ? cadena.replace(/á|é|í|ó|ú|ä|ë|ï|ö|ü|Á|É|Í|Ó|Ú|Ä|Ë|Ï|Ö|Ü/ig,function (str) {
					var str =str=="á"?"a":str=="é"?"e":str=="í"?"i":str=="ó"?"o":str=="ú"?"u":str;
					   str =str=="Á"?"A":str=="É"?"E":str=="Í"?"I":str=="Ó"?"O":str=="Ú"?"U":str;
					   str =str=="Á"?"A":str=="É"?"E":str=="Í"?"I":str=="Ó"?"O":str=="Ú"?"U":str;
					   str =str=="ä"?"a":str=="ë"?"e":str=="ï"?"i":str=="ö"?"o":str=="ü"?"u":str;
					   str =str=="Ä"?"A":str=="Ë"?"E":str=="Ï"?"I":str=="Ö"?"O":str=="Ü"?"U":str;
					return (str);
				}) : cadena;
			}
			
			
			/**
			 * Obtiene la primer vocal en una cadena sin contar la primera letra	
			 * @param {Object} str Cadena
			 * @return {String} vocalStr Primera vocal en la cadena
			 */
			function obtieneVocal(str){
				var vocales = ['a','e','i','o','u','á','é','í','ó','ú'];
				var strArray = str.toLowerCase().substr(1);
				var vocalStr = '';
				for (var i = 1; i < str.length; i++) {				
					for (var j = 0; j < vocales.length; j++) {
						if (str.charAt(i) === vocales[j]) {
							vocalStr = quitaAcentos(str.charAt(i));
							return vocalStr;
						}
					}
					if (!isEmpty(vocalStr)) {
						break;
					}
				};
				
				return !isEmpty(vocalStr) ? vocalStr : 'X';

			}
			
			/**
			 * Permite saber si una cadena es nula o esta vacia
			 * @param {Object} str Cadena
			 * @return {Boolean} True si es nula la cadena o vacia, false en caso contrario
			 */		
			  function isEmpty(cadena){ 
				if ( null == cadena || "" == cadena ) 
					return true; 
				else
					return false; 	   
			  }
			 /**
			 * metodo que carga el combo de estado fisico
			 */
			 function cargaComboEdoFisico(){
				 
				 if($('#cbxEdoFisico').val() <= 0){
					$('#cbxEdoFisico').empty();
					$('#cbxEdoFisico').append('<option value="-1">-Seleccione-</option>');
				 
				 
					 $.ajax({
				    		type: 'POST',
				    	    url: '<%=request.getContextPath()%>/ConsultarEdoFisico.do',
				    	    data: '',
				    	    dataType: 'xml',
				    	    async: true,
				    	    success: function(xml){
				    	    	$(xml).find('catEdoFisico').each(function(){
									$('#cbxEdoFisico').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
									$("#cbxEdoFisico").multiselect('refresh');
				    	    	});
				    	    }
						});
				 }
			 }
			 
			 /**
			 * metodo que carga el combo de estado de consciencia
			 */
			 function cargaComboEdoConsciencia(){
				 
				 if($('#cbxEdoConciencia').val() <= 0){
					$('#cbxEdoConciencia').empty();
			 		$('#cbxEdoConciencia').append('<option value="-1">-Seleccione-</option>');
				 
				 
					 $.ajax({
				    		type: 'POST',
				    	    url: '<%=request.getContextPath()%>/ConsultarEdoConsciencia.do',
				    	    data: '',
				    	    dataType: 'xml',
				    	    async: true,
				    	    success: function(xml){
				    	    	$(xml).find('catEdoConsciencia').each(function(){
									$('#cbxEdoConciencia').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
									$("#cbxEdoConciencia").multiselect('refresh');
								});
				    		}
						});
				 }
			 }
			 
		 	 /**
			 * metodo que carga el combo que depende de estado de consciencia
			 */
			 function cargaComboEdoConscienciaInconsciente(){
		 		 
		 		var edoConcienciaId = $('#cbxEdoConciencia option:selected').val();
		 		if(edoConcienciaId == <%=ConstantesGenerales.DATOS_GRLES_EDO_CONSCIENCIA_INCONSCIENTE_ID%>) {
		 			$("#trEdoConscienciaInconsciente").show();
		 			$('#cbxEdoInconciente').empty();
		 			$('#cbxEdoInconciente').append('<option value="-1">-Seleccione-</option>');
		 			
		 			
					 $.ajax({
				    		type: 'POST',
				    	    url: '<%=request.getContextPath()%>/ConsultarEdoConscienciaInconsciente.do?edoConcienciaId='+edoConcienciaId+'',
				    	    data: '',
				    	    dataType: 'xml',
				    	    async: true,
				    	    success: function(xml){
				    	    	$(xml).find('catEdoConscienciaInconsciente').each(function(){
									$('#cbxEdoInconciente').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
									$("#cbxEdoInconciente").multiselect('refresh');
								});
				    		}
						});
					 
		 		}else{
		 			$("#trEdoConscienciaInconsciente").hide();
		 		}
			 }
		 	 
		 	 function muestraCombosEdosFisicoConsciencia(){
		 		 
		 		$("#trEdoFisico").show();
	 			
	 			
	 			$("#trEdoConsciencia").show();
	 			
		 	 }
		    

</script>

<form id="profileForm" class="actionForm" method="get">
<div class="error1" style="display: none;"><img
	src="<%=request.getContextPath()%>/resources/images/warning.gif"
	alt="Warning!" width="24" height="24"
	style="float: left; margin: -5px 10px 0px 0px;" /> <span></span>.<br
	clear="all" />
</div>

<table border="0" width="100%" class="fondoClaroAp">
  <TBODY >
	<tr>
		<td id="etiquetaFechaRegistro" align="right">Fecha de Registro:</td>
		<td colspan="1"><input type="text" size="20" style="border: 0;"
			maxlength="50" id="datosGeneralesCmpFechaIngreso" disabled="disabled"
			name="datosGeneralesCmpFechaIngreso" /></td>
		<td   align="right">Ocupaci&oacute;n:</td>
		<td ><select id="datosGeneralesCmpOcupacion" multiple="multiple"
			name="datosGeneralesCmpOcupacion" style="width: 180px;" tabindex="13"></select></td>
	</tr>
	<tr id ="mostrarAlias">
		<td align="right">Nombre(s):</td>
		<td class="field"><input type="text" class="" size="50"
			maxlength="50" id="datosGeneralesCmpNombres"
			name="datosGeneralesCmpNombres" value="" 
			onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);" onkeyup="espejoDatos();" onchange="calculaRFC_CURP_UNO_UNO()"  tabindex="1"/></td>

		<td align="right" class="tdAliasCmp">Alias:</td>
		<td class="tdAliasCmp"><select id="datosGeneralesCmpAlias"
			multiple="multiple" name="datosGeneralesCmpAlias" style="width: 180px;"  tabindex="12"></select></td>
	</tr>
	<tr>

		<td align="right">Apellido Paterno:</td>
		<td><input type="text" size="50" maxlength="30" class=""
			id="datosGeneralesCmpApaterno" name="datosGeneralesCmpApaterno"
			onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"
			onkeyup="espejoDatos();" onchange="calculaRFC_CURP_UNO_UNO()" tabindex="2"/></td>
		<td  align="right">Nacionalidad:</td>
        <td><select id="datosGeneralesCmpNacionalidad" multiple="multiple"
			name="datosGeneralesCmpNacionalidad" style="width: 180px;" tabindex="14"></select>		</td>

		
	</tr>
	<tr>
		<td align="right">Apellido Materno:</td>
		<td><input type="text" size="50" maxlength="30" class=""
			id="datosGeneralesCmpMaterno" name="datosGeneralesCmpMaterno"
			onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"
			onkeyup="espejoDatos();" onchange="calculaRFC_CURP_UNO_UNO()" tabindex="3"/></td>
            <td>
            </td>
            <td></td>
	</tr>
    <tr>
		<td align="right">Sexo:</td>
		<td>
			<table border ="0">
				<tr>
					<td>
						Masculino 
						<input type="radio" id="datosGeneralesCmpSexoM" name="rbtSexoDatosGenerales" 
							value="M" checked="checked" onchange="calculaRFC_CURP_UNO_UNO()" tabindex="4">
					</td>
					<td>
						&nbsp; &nbsp; Femenino <input type="radio" id="datosGeneralesCmpSexoF" 
							name="rbtSexoDatosGenerales" value="F" onchange="calculaRFC_CURP_UNO_UNO()" tabindex="5">
					</td>
					<td id="tdSexoNoEspecificado">
						&nbsp; &nbsp; No Especificado <input type="radio" id="datosGeneralesCmpSexoN" 
						name="rbtSexoDatosGenerales" value="N"  tabindex="6">
					</td>
					
				</tr>
			</table>
			<div class="formError"></div>
		
		</td>
		<td id="tdDatosNacimiento" rowspan="7" colspan="2" valign="top"><jsp:include
				page="/WEB-INF/paginas/datosNacimientoView.jsp" flush="true"></jsp:include>
		</td>
		<td id="tdDatosGrlsVictimaMuerta" rowspan="7" colspan="2" valign="top">
			<table border="0" width= "340px">
				<tr>
					<td width="30%"align="right">Edad:</td>
					<td>
						<input type="text" maxlength="3" class="" style="width: 105px;"
								id="edadVictimaMuerta" onblur="validaSoloNumeros(this);"/>
					</td>
				</tr>
				<tr>
					<td width="30%" align="right"> Edad unidad:</td>
					<td>
						<select id="cbxEdadUnidadVictimaMuerta" name="cbxEdadUnidadVictimaMuerta" 
								style="width: 110px;" >
							<option>- Selecciona -</option>
						</select>
					</td>
				</tr>		
				<tr>
					<td width="30%" align="right">Condici&#243;n de Actividad:</td>
					<td>
						<select id="cbxCondicionActividadVictimaMuerta" name="cbxCondicionActividadVictimaMuerta" 
								style="width: 110px;" >
							<option>- Selecciona -</option>
						</select>
					</td>
				</tr>				
			</table>
		</td>
       
        
        
	</tr>
	<tr>
		<td align="right">CURP:</td>
		<td><input type="text" size="50" maxlength="18" class=""
			id="datosGeneralesCmpCurp" name="datosGeneralesCmpCurp"  tabindex="7" onKeypress="return bloqueaEnter(event);"/></td>
	</tr>
	<tr>
		<td align="right">RFC:</td>
		<td><input type="text" class="" size="50" maxlength="13"
			id="datosGeneralesCmpRfc" name="datosGeneralesCmpRfc" tabindex="8" onKeypress="return bloqueaEnter(event);" /></td>
            <td></td><td></td>
	</tr>
	
	<tr id="trAliasTxt">
		<td align="right">Alias:</td>
		<td><input type="text" size="20" maxlength="50" id="newItem"
			name="newItem"  tabindex="9" onKeypress="return bloqueaEnter(event);" />
			 <input type="button" id="add" value="Agrega Alias" class="btn_modificar" onclick="agregaAlias()"/>
		</td>
		
	</tr>
	<tr id="trIdiomaLengua">
		<td align="right">Idioma o Lengua:</td>
		<td><select id="datosGeneralesCmpIdioma"
			name="datosGeneralesCmpIdioma" style="width: 180px;" tabindex="10">
			<option id="0">- Selecciona -</option>
		</select></td>
		
	</tr>
	<!-- tr>
		<td align="right">Religion:</td>
		<td>
			<select id="datosGeneralesCmpReligion" name="datosGeneralesCmpReligion">
				<option>- Selecciona -</option>
			</select>
		</td>
	</tr-->
	<tr>
		<td align="right">Escolaridad:</td>
		<td><select id="datosGeneralesCmpEscolaridad"
			name="datosGeneralesCmpEscolaridad" style="width: 180px;" tabindex="11">
			<option>- Selecciona -</option>
		</select></td>
	</tr>
	<tr>
		<td align="right">Estado Civil:</td>
		<td><select id="datosGeneralesCmpEstadoCivil"
			name="datosGeneralesCmpEstadoCivil" style="width: 180px;" tabindex="12">
			<option id="0">- Selecciona -</option>
		</select></td>
	</tr>
	
	<tr id="trEdoFisico">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td align="right">Estado Fisico: </td>
		<td>
			<select id="cbxEdoFisico" style="width:180px">
      			<option value="-1">-Seleccione-</option>
    		</select>
		</td>
	</tr>
	
	<tr id="trEdoConsciencia">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td align="right">Estado de conciencia: </td>
		<td>
			<select id="cbxEdoConciencia" style="width:180px" onchange="cargaComboEdoConscienciaInconsciente()">
      			<option value="-1">-Seleccione-</option>
    		</select>
    	</td>
	</tr>
	
	<tr id="trEdoConscienciaInconsciente">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td align="right">Estado de inconciencia: </td>
		<td>
			<select id="cbxEdoInconciente" style="width:180px">
      			<option value="-1">-Seleccione-</option>
    		</select>
    	</td>
	</tr>
	
  </TBODY>
</table>

<input class="formButton" type="submit" value="Next" 
	id="botonvalida" style="display: none;" /> <script
	type="text/javascript">
$(function(){
	$("#datosGeneralesCmpOcupacion").multiselect();
	$("#datosGeneralesCmpNacionalidad").multiselect(
			{ 
				header: "Selecciona de 1 a 3 opciones",
				click: function(e){
				
					if( $(this).multiselect("widget").find("input:checked").length > 3 ){
						
						return false;
					} 
					
				}		
	
			});
});

function soloLetrasDos(e,id) 
{
	var ExpReg_cad=/^[aA-zZ]*$/;
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 241 || unicode == 46 || unicode == 39 || unicode == 37 || unicode == 32 || unicode == 8 || unicode == 9 || (unicode >= 65 && unicode <= 90) || (unicode >= 97 && unicode <= 122)) { //todas las letras en mayuscula
       if(unicode == 39 || unicode == 46)
       {
    		if(e.keyCode==0)
    		{
    			return false;
    		}
    		else
    		{
    			return true;
    		}
       }
       else
       {
       return true;
       }
	}
	else 
	{
       // otherwise we don't
       return false;
	}
	return true;
}

cargaOcupacion();
cargaNacionalidad();

cargaFechaCaptura();
/* cargaAlias(); */

try{
	if(idElemento != undefined && (idElemento == null || idElemento == "null"|| idElemento == "0")){
		$("#datosGeneralesCmpIdioma").one("click", function() {
			cargaIdioma();	
		});
		
		$("#datosGeneralesCmpEscolaridad").one("click", function() {
			cargaEscolaridad();	
		});
		
		$("#datosGeneralesCmpEstadoCivil").one("click", function() {
			cargaEstadoCivil();
		});
		
	}else{
		cargaIdioma();	
		cargaEscolaridad();	
		cargaEstadoCivil();
	}
	
}catch(e){
	cargaIdioma();	
	cargaEscolaridad();	
	cargaEstadoCivil();
}

if (verAlias==1){
	$('#mostrarAlias').empty();
	$('#mostrarAlias').append('<td align="right">Nombre(s):</td><td class="field"><input colspan="3" type="text" size="50"	maxlength="50" id="datosGeneralesCmpNombres" name="datosGeneralesCmpNombres" value="" class="masmas" onkeyup="espejoDatos();" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);" tabindex="1"/></td>');
	$("#trAliasTxt").empty();
}


function bloqueaEnter(e) 
{
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13){
		return false;
	}else{
		return true;
	} 
}

	/*
	 *Funcion que realiza la carga del combo edad unidad para victimas muertas
	 */
	 function cargaEdadUnidad() {
			  
		$('#cbxEdadUnidadVictimaMuerta').empty();
		$('#cbxEdadUnidadVictimaMuerta').append('<option value="-1">-Seleccione-</option>'); 
		
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarEdadUnidad.do',
		  data: '',
		  dataType: 'xml',
		  async: true,
		  success: function(xml){
			var option;
			$(xml).find('catEdadUnidad').each(function(){
				$('#cbxEdadUnidadVictimaMuerta').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    });			
		  }
		});
	 }
	
	/*
	 *Funcion que realiza la carga del combo condicion de actividad para victimas muertas
	 */
	 function cargaCondicionActividad() {
			  
		$('#cbxCondicionActividadVictimaMuerta').empty();
		$('#cbxCondicionActividadVictimaMuerta').append('<option value="-1">-Seleccione-</option>'); 
		
		$.ajax({
		  type: 'POST',
		  url: '<%= request.getContextPath()%>/cargarCondicionActividad.do',
		  data: '',
		  dataType: 'xml',
		  async: true,
		  success: function(xml){
			var option;
			$(xml).find('catCondicionActividad').each(function(){
				$('#cbxCondicionActividadVictimaMuerta').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    });			
		  }
		});
	 }

</script></form>

