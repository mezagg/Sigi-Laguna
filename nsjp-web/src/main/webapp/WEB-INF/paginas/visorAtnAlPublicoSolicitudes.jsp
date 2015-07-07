<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.TipoAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="logic" uri="/WEB-INF/tld/struts-logic.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/tld/struts-bean.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Atencion a Sollicitudes</title>

<!--Se importan las css necesarias-->
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css" />


<!--Se importan los scripts necesarios-->
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>	
<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>



<script type="text/javascript">
	
	var numeroCausa='<%= request.getParameter("numeroDeCausa")!=null?request.getParameter("numeroDeCausa"):""%>';
	var accPenal='<%= request.getParameter("accPenal")%>';
	var contextoPagina = "${pageContext.request.contextPath}";
	var tipoSolicitud="";	
	var numExpIdGlobal="";
	var validaInvolucrados=true;
	var validaAudiencias=true;
	var expedienteIdGlobal= "";
	
	$(document).ready(function() {
		
		//Ocultamos todos los campos
		ocultaTodosLosCampos();
		//Crea Tabs
		$("#tabsprincipalconsulta" ).tabs();
		//setea el numero de causa al input
		$("#numCasoABuscar").val(numeroCausa);
		//carga el combo box de tipo de solicitudes
		cargaComboTipoSolicitud();
		//Escuchador de cambio en el combo instituciones
		 $("#solicitudPJATP").change(onSelectChangeTipo);
		 //deshabilita todos los botones
		 deshabilitaBotones();
		//carga Date picker para la fecha limite de la audiencia
		cargaDatePickerFechaLimite();

		 $('#horaLimiteAudiencia').timepicker({
	            onSelect: function(time, inst) {
	                $('#floating_selected_time').html('You selected ' + time);
	            }
	        });
		 
		 $('#buscarCausa').click(buscarExpediente);
		 
		if(accPenal == "true"){
			solicitudDesdeAccionPenalPrivada();
		}
	});
	//Fin On Ready	
	
	/*
	*Funcion para la solicitud de accion penal privada
	*/
	function solicitudDesdeAccionPenalPrivada(){
	
		$("#solicitudPJATP").attr('selectedIndex', 1);
		var seleccion = $("#solicitudPJATP option:selected");
		muestraControles(seleccion.val());	
		validarNumeroCausa();
		$("#solicitudPJATP").attr('disabled', true);
		$("#buscarCausa").attr('disabled', true);
		$("#limpiar").attr('disabled', true);
		$("#validarCausa").attr('disabled', true);
		$("#numCasoABuscar").attr('disabled', true);
		$("#institucionSolicitantePJATP").val('<%=Instituciones.PJ.getValorId()%>');
		$("#institucionSolicitantePJATP").attr('disabled', true);
		
	}
	
	/*
	*Funcion que crea el visor buscar Expediente con Store Procedure
	*/
	function buscarExpediente() {
	    	customVentana("iframewindowBuscarExpediente", "Buscar Causa", "/buscarExpedienteConSP.do");
	}
	
	/*
	* Cierra la ventana de b�squeda de expediente y coloca el resultado en el campo de n�mero de causa
	*
	*/
	function seleccionarExpediente(id,numero){
		$("#numCasoABuscar").val(numero);
		$.closeWindow('iframewindowBuscarExpediente');
		
	}
	function cargaDatePickerFechaLimite(){

		$("#fechaLimiteAudiencia").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true
		});
	}
	
	/*
	*Funcion que carga los combos de tipo de solicitudes que se pueden realizar
	*para esta institucion
	*/
	function cargaComboTipoSolicitud() {
	
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarTipoSolicitud.do',
			data: '',
			async: false,
			dataType: 'xml',
			success: function(xml){
				$('#solicitudPJATP').empty();
				$('#solicitudPJATP').append('<option value="-1">- Seleccione -</option>');
				var tipoSol = "";
				$(xml).find('tipoSolicitud').each(function(){
					var tipoSol = $(this).find('clave').text();
					if( tipoSol == <%=TiposSolicitudes.AUDIENCIA.getValorId()%> ||
						parseInt(tipoSol) ==  1774 ||
						tipoSol == <%=TiposSolicitudes.TRANSCRIPCION_DE_AUDIENCIA.getValorId()%> ||
						tipoSol == <%=TiposSolicitudes.RECURSO_APELACION.getValorId()%> ||
						tipoSol == <%=TiposSolicitudes.RECURSO_CASACION.getValorId()%>){
							$('#solicitudPJATP').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						}
				});
			}
		});
	}

	/*
	*Funcion que carga los combos de tipo de solicitudes de audiencia que se pueden realizar
	*/
	function cargaComboTipoAudiencia() {
	
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoTipoAudiencia.do',
			data: '',
			async: false,
			dataType: 'xml',
			success: function(xml){
				$('#tipoDeAudiencia').empty();
				$('#tipoDeAudiencia').append('<option value="-1">- Seleccione -</option>');
				
				$(xml).find('institucion').each(function(){
					$('#tipoDeAudiencia').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}
	
	
	/*
	* Funcion que muestra u oculta los campos dependiendo de la opciion seleccionadas
	*/	
	function muestraControles(tipoSolicitud){
		
		//Limpia todos los campos cuando se cambia de el tipo de audiencia
		limpiaTodosLosCampos();
		//Oculta todos los campos
		ocultaTodosLosCampos();

		//Si es una no ha seleccionado una opcion
		if(tipoSolicitud == "-1"){
			deshabilitaBotones();
		} else
		//Si es una sol de audiencia
		if(tipoSolicitud ==  <%=TiposSolicitudes.AUDIENCIA.getValorId()%>){
			habilitaBotones();
			muestraCamposAudiencia();
		} else

		//Si es una sol de audio y video
		if(tipoSolicitud == <%=TiposSolicitudes.AUDIO_VIDEO.getValorId()%>){
			habilitaBotones();
			muestraCamposAudioVideoTranscripcion();
		} else
		
		//Si es una sol de transcripcion de audiencia
		if(tipoSolicitud ==  <%=TiposSolicitudes.TRANSCRIPCION_DE_AUDIENCIA.getValorId()%>){
			habilitaBotones();
			muestraCamposAudioVideoTranscripcion();			
		} else

		//Si es una sol de recurso de apelacion
		if(tipoSolicitud ==  <%=TiposSolicitudes.RECURSO_APELACION.getValorId()%>){
			habilitaBotones();
			muestraCamposCasacionApelacion("apelacion");					
		} else

		//Si es una sol de recurso de casacion
		if(tipoSolicitud ==  <%=TiposSolicitudes.RECURSO_CASACION.getValorId()%>){
			habilitaBotones();
			muestraCamposCasacionApelacion("casacion");			
		}
	}
	
	/*
	*Funcion que oculta todos los campos, comboBox y areas de texto
	*/	
	function ocultaTodosLosCampos(){
		
		$(".au").hide();
		$(".av").hide();
		$(".ra").hide();
		$(".rc").hide();
		$(".ta").hide();
				
		$("#divEtiquetaInvolucrados").hide();
		$("#gridInvolucradosAudienciaContenedor").hide();
		
		$("#divFundamentoSol").hide();
		$("#divFundamentoSolCampo").hide();
		$("#divSituacionesEspeciales").hide();
		$("#divSituacionesEspecialesCampo").hide();
		
		
		$("#divNumeroCaso").hide();
		$("#divNumeroCausa").hide();
		$("#numCasoPJATP").hide();
		$("#numCausaPJATP").hide();
		$("#divFechaSolicitud").hide();
		$("#fechaSolicitudPJATP").hide();
		$("#divHoraSolicitud").hide();
		$("#horaSolicitudPJATP").hide();
		$("#divInsSolicitante").hide();
		$("#institucionSolicitantePJATP").hide();
		$("#divDatosSolicitante").hide();
		$("#divNombreSol").hide();
		$("#nombreSolicitantePJATP").hide();
		$("#divApellidoPat").hide();
		$("#apPatSolicitantePJATP").hide();
		$("#divApellidoMat").hide();
		$("#apMatSolicitantePJATP").hide();
		$("#divFechaLimite").hide();
		$("#fechaLimiteAudiencia").hide();
		$("#divHoraLimite").hide();
		$("#horaLimiteAudiencia").hide();
		$("#divTipoAudiencia").hide();
		$("#tipoDeAudiencia").hide();
		$("#divTipoRecurso").hide();
		$("#tipoDeRecurso").hide();
		$("#divAdjuntarDoc").hide();
	}

	/*
	*Funcion que muestra los campos para realizar una solicitud
	*de Audiencia
	*/	
	function muestraCamposAudiencia(){
		
		$(".au").show();
		$("#divAdjuntarDoc").show();
		$("#divFundamentoSol").show();
		$("#divFundamentoSolCampo").show();
		$("#divSituacionesEspeciales").show();
		$("#divSituacionesEspecialesCampo").show();
		
		$("#gridInvolucradosAudienciaContenedor").show();
		$("#divEtiquetaInvolucrados").show();
		$("#divNumeroCausa").show();
		$("#divNumeroCaso").show();
		$("#numCausaPJATP").show();
		$("#numCasoPJATP").show();
		$("#divFechaSolicitud").show();
		$("#fechaSolicitudPJATP").show();
		$("#divHoraSolicitud").show();
		$("#horaSolicitudPJATP").show();
		$("#divInsSolicitante").show();
		$("#institucionSolicitantePJATP").show();
		$("#divDatosSolicitante").show();
		$("#divNombreSol").show();
		$("#nombreSolicitantePJATP").show();
		$("#divApellidoPat").show();
		$("#apPatSolicitantePJATP").show();
		$("#divApellidoMat").show();
		$("#apMatSolicitantePJATP").show();
		$("#divFechaLimite").show();
		$("#fechaLimiteAudiencia").show();
		$("#divHoraLimite").show();
		$("#horaLimiteAudiencia").show();
		$("#divTipoAudiencia").show();
		$("#tipoDeAudiencia").show();
		//Carga combo tipo de solicitud de audiencia
		cargaComboTipoAudiencia();
		//carga la fecha y la hora actual
		cargaFechaHoraActual();
		//carga el combo de instituciones solicitantes
		cargaComboInstitucionSolicitante();
	} 

	/*
	*Funcion que muestra los campos para realizar una solicitud
	*de Audio y video y de Transcripcion de audiencia
	*/	
	function muestraCamposAudioVideoTranscripcion(){
		
		$(".av").show();
		$(".ta").show();
		
		$("#divNumeroCausa").show();
		$("#divNumeroCaso").show();
		$("#numCausaPJATP").show();
		$("#numCasoPJATP").show();
		$("#divFechaSolicitud").show();
		$("#fechaSolicitudPJATP").show();
		$("#divHoraSolicitud").show();
		$("#horaSolicitudPJATP").show();
		$("#divInsSolicitante").show();
		$("#institucionSolicitantePJATP").show();
		$("#divDatosSolicitante").show();
		$("#divNombreSol").show();
		$("#nombreSolicitantePJATP").show();
		$("#divApellidoPat").show();
		$("#apPatSolicitantePJATP").show();
		$("#divApellidoMat").show();
		$("#apMatSolicitantePJATP").show();
		//carga la fecha y la hora actual
		cargaFechaHoraActual();
		//carga el combo de instituciones solicitantes
		cargaComboInstitucionSolicitante();
	} 

	/*
	*Funcion que muestra los campos de registro de casacion
	*o recurso de apelacion
	*/	
	function muestraCamposCasacionApelacion(tipo){

		$(".ra").show();
		$(".rc").show();
		
		$("#divNumeroCausa").show();
		$("#divNumeroCaso").show();
		$("#numCausaPJATP").show();
		$("#numCasoPJATP").show();
		$("#divFechaSolicitud").show();
		$("#fechaSolicitudPJATP").show();
		$("#divHoraSolicitud").show();
		$("#horaSolicitudPJATP").show();
		$("#divInsSolicitante").show();
		$("#institucionSolicitantePJATP").show();
		$("#divDatosSolicitante").show();
		$("#divNombreSol").show();
		$("#nombreSolicitantePJATP").show();
		$("#divApellidoPat").show();
		$("#apPatSolicitantePJATP").show();
		$("#divApellidoMat").show();
		$("#apMatSolicitantePJATP").show();
		$("#divTipoRecurso").show();
		$("#tipoDeRecurso").show();
		if(tipo == "apelacion"){
			$("#tipoDeRecurso").val(1);
		}
		if(tipo == "casacion"){
			$("#tipoDeRecurso").val(0);
		}
		$("#divAdjuntarDoc").show();
		//carga la fecha y la hora actual
		cargaFechaHoraActual();
		//carga el combo de instituciones solicitantes
		cargaComboInstitucionSolicitante();
	}

	/*
	*Funcion que deshabilita todos los botones
	*/
	function deshabilitaBotones(){
		
		$('#buscarCausa').attr("disabled", "true");
		$('#limpiar').attr("disabled", "true");
		$('#validarCausa').attr("disabled", "true");
		$('#numCasoPJATP').attr("disabled", "true");
		$('#enviarSolicitud').attr("disabled", "true");
	}
	
	/*
	*Funcion que deshabilita todos los botones
	*/
	function habilitaBotones(){
		
		$('#buscarCausa').removeAttr('disabled');
		$('#limpiar').removeAttr('disabled');
		$('#validarCausa').removeAttr('disabled');
		$('#numCasoPJATP').removeAttr('disabled');
		$('#enviarSolicitud').removeAttr('disabled');
	}

	
	/*
	*Funcion que muestra la fecha y hora del sistema
	*/
	function cargaFechaHoraActual(){
		
		var anioActual='<%=(java.util.Calendar.getInstance().get(Calendar.YEAR))%>';
		var mesActual='<%=(java.util.Calendar.getInstance().get(Calendar.MONTH))%>';
		var diaActual='<%=(java.util.Calendar.getInstance().get(Calendar.DAY_OF_MONTH))%>';

		mesActual=(parseInt(mesActual)+1)+"";
				
		if(diaActual.length < 2 && mesActual.length < 2 ){
			$('#fechaSolicitudPJATP').val(+"0"+diaActual+"/"+"0"+ mesActual+"/"+anioActual);
		}
		if(diaActual.length < 2 && mesActual.length >= 2 ){
			$('#fechaSolicitudPJATP').val(+"0"+diaActual+"/"+ mesActual+"/"+anioActual);
		}
		if(diaActual.length >= 2 && mesActual.length < 2 ){
			$('#fechaSolicitudPJATP').val(diaActual+"/"+"0"+ mesActual+"/"+anioActual);
		}
		if(diaActual.length >= 2 && mesActual.length >= 2 ){
			$('#fechaSolicitudPJATP').val(diaActual+"/"+mesActual+"/"+anioActual);
		}

		var horaActual='<%=(java.util.Calendar.getInstance().get(Calendar.HOUR_OF_DAY))%>';
		var minutoActual='<%=(java.util.Calendar.getInstance().get(Calendar.MINUTE))%>';
		if(minutoActual.length < 2){
			$('#horaSolicitudPJATP').val(horaActual+":0"+minutoActual);
		}
		else{
			$('#horaSolicitudPJATP').val(horaActual+":"+minutoActual);
		}
	}

	//Funcion que carga los combos de las instituciones solicitantes
	function cargaComboInstitucionSolicitante() {
		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarInstitucion.do',
			data: '',
			async: false,
			dataType: 'xml',
			success: function(xml){
				$('#institucionSolicitantePJATP').empty();
				$('#institucionSolicitantePJATP').append('<option value="-1">- Seleccione -</option>');
				var tipoIns = "";
				$(xml).find('institucion').each(function(){
					tipoIns = $(this).find('clave').text();
					if( tipoIns == "1" || tipoIns == "2" || tipoIns == "3"){
						$('#institucionSolicitantePJATP').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					}
				});
			}
		});
	}
	
	/*
	*Limpia el campo de causa
	*/
	function limpiarCampoCausa(){
		$('#numCasoABuscar').val("");
		$('#numCasoPJATP').val("");
		$('#numCausaPJATP').val("");
		limpiaTodosLosCampos();
		//Limpia el grid de involucrados
		//$("#gridInvolucradosAudiencia").jqGrid("clearGridData", true);
		limpiarGridInvolucradosAudiencia();
		//Limpia el grid de Audiencias
		limpiarGridAudiencias();
		cargaFechaHoraActual();
		
	}
	
	function limpiarGridInvolucradosAudiencia(){
		numExpediente="";
		validaInvolucrados=false;
		jQuery("#gridInvolucradosAudiencia").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarInvolucradosVictimasYPRPorNumeroExp.do?numExpediente='+numExpediente+'&limpiar=true',datatype: "xml" });
		$("#gridInvolucradosAudiencia").trigger("reloadGrid");
	}
	
	function limpiarGridAudiencias(){
		vacio="";
		validaAudiencias=false;
		jQuery("#gridAudiencias").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarAudienciasPorNumeroCausa.do?numeroExpedienteId='+vacio+'&limpiar=true', atatype: "xml" });
		$("#gridAudiencias").trigger("reloadGrid");		
	}

	
	/*
	*Funcion que limpia todos los campos
	*/	
	function limpiaTodosLosCampos(){
		
		$("#numCausaPJATP").val("");
		$("#numCasoPJATP").val("");
		$("#fechaSolicitudPJATP").val("");
		$("#horaSolicitudPJATP").val("");
		$("#institucionSolicitantePJATP").attr('selectedIndex', 0);
		$("#nombreSolicitantePJATP").val("");
		$("#apPatSolicitantePJATP").val("");
		$("#apMatSolicitantePJATP").val("");
		$("#fechaLimiteAudiencia").val("");
		$("#horaLimiteAudiencia").val("");
		$("#tipoDeAudiencia").attr('selectedIndex', 0);
		$("#tipoDeRecurso").attr('selectedIndex', 0);
		$("#motivoSolicitud").val("");
		$("#situacionesEspeciales").val("");		
	}

	/*
	*Funcion para validar el numero de causa
	*/
	function validarNumeroCausa(){
		
		if($('#numCasoABuscar').val() != ""){
			
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/validarNumeroDeCausa.do',
				data: 'numeroCausa='+ $('#numCasoABuscar').val(),
				async: false,
				dataType: 'xml',
				success: function(xml){
					var numCausaObtenido=$(xml).find('causa').find('numeroExpediente').text();
					var numCasoObtenido=$(xml).find('causa').find('numeroGeneralCaso').text();
					
					if(numCausaObtenido != "" && numCausaObtenido != null){
						$("#numCausaPJATP").val(numCausaObtenido);
						$("#numCasoPJATP").val(numCasoObtenido);
						
						numExpIdGlobal= $(xml).find('causa').find('numeroExpedienteId').text();
						expedienteIdGlobal= $(xml).find('causa').find('expedienteId').text();
						
						tipoSolSeleccionada = $("#solicitudPJATP option:selected");
						
						if(tipoSolSeleccionada.val()  == <%=TiposSolicitudes.AUDIENCIA.getValorId()%>){
							
							//si es audiencia
							cargarGridSeleccionInvolucradosAudiencia();
						}else{
							//si es otro tipo de solicitud
							cargarGridAudienciasDeCausa();
							
						}
						
						
					}
					else{
						customAlert("El N&uacute;mero de Causa no existe",'<bean:message key="aviso"/>');
					}
				}
			});
		}
		else{
			customAlert("Ingrese un N&uacute;mero de Causa",'<bean:message key="aviso"/>');
		}	
	}

	/*
	* Funcion que al cambiar el combo de instituciones oculta o muestra algunos elementos de la 
	* pantalla
	*/
	function onSelectChangeTipo() {
		var seleccion = $("#solicitudPJATP option:selected");
		muestraControles(seleccion.val());
	}

	/*
	* Funcion que obtiene los parametros para enviar la solicitud
	*/
	function enviarSolicitud(){

		var selected = $("#solicitudPJATP option:selected");

		//Si el numero de causa es vacio se pueden guardar solo solicitudes de audiencia
		if( $("#numCausaPJATP").val()== "" ){
			customAlert("Ingrese un N&uacute;mero de Causa",'<bean:message key="aviso"/>');
		}
		//Si no esta vacio, checar si es solicitud de recurso de apelacion o casacion
		else{
			if(selected.val() == <%=TiposSolicitudes.AUDIENCIA.getValorId()%>){
				if(validaCamposAudiencia() && validaCamposFecha()){
						guardarSolicitud();
				}
			}
			else{
				
				if(jQuery("#gridAudiencias").jqGrid('getGridParam','selrow') == null){
					customAlert("Seleccione una audiencia a la cual ser&aacute; relacionada la solicitud",'<bean:message key="aviso"/>');
					return false;
				}
				if(validaInstitucionYSolicitante()){
					guardarSolicitud();
				}				
			}
		}
	}

	//funcion que guarda las solicitudes de audiencia, de transcripcion de audiencia y  de audio/video
	function guardarSolicitud(){
		
			forma = document.solicitudPJForm;
			
			forma.fechaSolicitud.value = $('#fechaSolicitudPJATP').val();
			forma.horaSolicitud.value = $('#horaSolicitudPJATP').val();
			forma.institucionSolicitante.value = $('#institucionSolicitantePJATP').val();
			forma.nombreSolicitante.value = ($('#nombreSolicitantePJATP').val()+" "+$('#apPatSolicitantePJATP').val()+" "+$('#apMatSolicitantePJATP').val());
			forma.tipoSolicitud.value = $('#solicitudPJATP').val();
			forma.numeroExpedienteId.value = numExpIdGlobal;
			forma.tipoDeRecurso.value = $('#tipoDeRecurso').val();
			forma.formaId.value = <%=Formas.ACUSE_RECIBO.getValorId()%>
			forma.numeroCausa.value = $('#numCausaPJATP').val();
			forma.numeroCaso.value = $('#numCasoPJATP').val();
			forma.fechaLimite.value = $('#fechaLimiteAudiencia').val();
			forma.horaLimite.value = $('#horaLimiteAudiencia').val();
			forma.tipoAudiencia.value = $('#tipoDeAudiencia').val();
			forma.motivoSolicitud.value = $('#motivoSolicitud').val();
			forma.situacionesEspeciales.value = $('#situacionesEspeciales').val();
			forma.involucradosAudiencia.value = jQuery("#gridInvolucradosAudiencia").jqGrid('getGridParam','selarrrow');
			forma.audienciaId.value = jQuery("#gridAudiencias").jqGrid('getGridParam','selrow');
			forma.submit();
	}

	
	function terminarGuardadoSolicitud(documentoId,numExp){
		
		document.formaDocDirecto.documentoId.value = documentoId;
		document.formaDocDirecto.numeroUnicoExpediente.value = numExp;
		document.formaDocDirecto.submit();
		limpiarCampoCausa();
		customAlert("Solicitud enviada correctamente",'<bean:message key="aviso"/>',function(){
			cerrarVentanaNuevaSolicitud();
		});
	}
	

	//Funcion que valida si los campos estan llenos al enviar 
	function validaCamposFecha() {

		var validaFecha = false;
		
		if ($('#fechaSolicitudPJATP').val() == '' || $('#fechaLimiteAudiencia').val() == ''
				|| $('#horaLimiteAudiencia').val() == '') {
			customAlert("Se debe ingresar una fecha y hora l&iacute;mite",'<bean:message key="aviso"/>');
			validaFecha = false;
		} else {

			var fechaIniVal = $('#fechaSolicitudPJATP').val();
			var fechaFinVal = $('#fechaLimiteAudiencia').val();
			var hIniVal = $('#horaSolicitudPJATP').val(); 
			var hFinVal= $('#horaLimiteAudiencia').val();
			var inicio = fechaIniVal.split("/");
			var fin = fechaFinVal.split("/");
			var hInicio=hIniVal.split(":");
			var hFin=hFinVal.split(":");

		//si el a�o fin es mayor termina
		 if (fin[2] > inicio[2] )   
    {   
			 validaFecha = true;
    }   
    else  
    {   
    	//si el a�o fin es = al inicial compara los meses
    if (fin[2] == inicio[2])   
      {    
        //compara que el mes final sea mayor al inicial termina
        if (fin[1]> inicio[1])   
        {   
        	validaFecha = true;
        }   
        else  
        {    
        	//si el mes fin es = al inicial compara los dias
          if (fin[1] == inicio[1])   
          {   
            if (fin[0]> inicio[0])  
                {
            	validaFecha = true; 
            	}
      	 else  {
      		 if (fin[0] ==  inicio[0])   
      	      {    
      	        //comara que el mes final sea mayor al inicial termina
      	        if (hFin[0]> hInicio[0])   
      	        {   
      	        	validaFecha = true;
      	        }   
      		 else  {
      			customAlert("La hora l&iacute;mite debe de ser mayor a la hora de solicitud",'<bean:message key="aviso"/>');
         	   validaFecha = false;  
         	  		 } 	 
      	 }
      	 }
          }   
          else  {
        	  validaFecha = false; 

        	  customAlert("La fecha l&iacute;mite debe de ser mayor a la fecha de solicitud",'<bean:message key="aviso"/>');
        	  }
        	    
        }   
      }   
     else  {
    	 customAlert("La fecha l&iacute;mite debe de ser mayor a la fecha de solicitud",'<bean:message key="aviso"/>');

    	 validaFecha = false; 

         }
    	  
    }   
		}	
		return(validaFecha);
		}
	
	//Funcion que cierra la ventana 
	 function confirmaCancelar() {
		 alertDinamicoDosBotones("�Desea salir sin enviar?","cierraVentanaNuevaSolicitud");
	 } 


	//Funcion que cierra la ventana
	function cerrarVentanaNuevaSolicitud(){
		window.parent.cerrarVentanaNuevaSolicitud();
	}

	/**
	* Carga el grid para seleccionar involucrados en la audiencia con las v�ctimas y probables responsables
	* del expediente seleccionado en los pasos anteriores
	*/
	primeraConsultaInvolucrados = true;
	function cargarGridSeleccionInvolucradosAudiencia(){

		var numExpediente = $('#numCausaPJATP').val();
		var numInvolucrados;
		var seleccionados = new Array();
		validaInvolucrados=true;
		
		if(primeraConsultaInvolucrados){
				
			//Se llena el gird de involucrados VICTIMAS Y PROBABLES RESPONSABLES
			jQuery("#gridInvolucradosAudiencia").jqGrid({
				url:'<%= request.getContextPath()%>/consultarInvolucradosVictimasYPRPorNumeroExp.do?expedienteId='+expedienteIdGlobal+'', 
				datatype: "xml", 
				colNames:['Nombre','Calidad'], 
				colModel:[ 	{name:'nombre',index:'nombre', width:200, align:"left"},
				           	{name:'carga',index:'carga', width:120, align:"center"}  
							
							
						],
				pager: jQuery('#pagerGridInvolucrados'),
				rowNum:10,
				rowList:[25,50,100],
				autowidth: true,
				sortname: 'carga',
				viewrecords: true,
				sortorder: "desc",
				multiselect: true,
				onSelectRow: function(ids) {
					numInvolucrados = jQuery("#gridInvolucradosAudiencia").jqGrid('getGridParam','records');
					seleccionados = jQuery("#gridInvolucradosAudiencia").jqGrid('getGridParam','selarrrow');
					if((numInvolucrados-seleccionados.length) == 0){
						jQuery("#cb_gridInvolucradosAudiencia").attr('checked','checked');
					}else if((numInvolucrados-seleccionados.length) < numInvolucrados && jQuery("#cb_gridInvolucradosAudiencia").is(":checked")){
						jQuery("#cb_gridInvolucradosAudiencia").removeAttr("checked");
					}
				},
				loadComplete: function(){
					if (validaInvolucrados === true){
						validarInvolucradosGrid();	
					}
				}
			}).navGrid('#gridInvolucradosAudiencia',{edit:false,add:false,del:false});

			primeraConsultaInvolucrados=false;
			
		}
		else{
			jQuery("#gridInvolucradosAudiencia").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarInvolucradosVictimasYPRPorNumeroExp.do?expedienteId='+expedienteIdGlobal+'',datatype: "xml" });
			$("#gridInvolucradosAudiencia").trigger("reloadGrid");
		}
	}
	
	function validarInvolucradosGrid(){
		if (jQuery('#gridInvolucradosAudiencia').jqGrid('getGridParam','records') == '0'){
			customAlert("La Causa seleccionada no cuenta con intervinientes, por lo que no se puede enviar la solicitud. <br/>"
					+"Por favor seleccione una causa que tenga intervinientes asociados",'<bean:message key="aviso"/>');
		}	
	}
	
	//variable para controlar la primera carga del grid
	var primeraConsultaAudiencias= true;

	/*
	*Funcion que carga el grid de las audoiencias asociadas al numero de expediente
	*que est�n en estatus de terminada
	*/
	function cargarGridAudienciasDeCausa(){
		validaAudiencias=true;

		if(primeraConsultaAudiencias == true){
		
			//Se llena el gird de audiencias
			jQuery("#gridAudiencias").jqGrid({
				url:'<%= request.getContextPath()%>/consultarAudienciasPorNumeroCausa.do?numeroExpedienteId='+numExpIdGlobal+'', 
				datatype: "xml", 
				colNames:['Fecha Audiencia','Hora Audiencia','Tipo','Sala'], 
				colModel:[ 	{name:'fecha',index:'fecha', width:80, align:"left"},
				           	{name:'hora',index:'hora', width:30, align:"left"},  
				           	{name:'tipo',index:'tipo', width:100, align:"left"},
				           	{name:'sala',index:'sala', width:100, align:"left"}
						],
				pager: jQuery('#pagerGridAudiencias'),
				rowNum:10,
				rowList:[25,50,100],
				autowidth: false,
				sortname: 'fecha',
				viewrecords: true,
				loadComplete: function(){
					if (validaAudiencias === true){
						if (jQuery('#gridAudiencias').jqGrid('getGridParam','records') == '0'){
							customAlert("La causa seleccionada no cuenta con audiencias, por lo que no se puede enviar la solicitud. <br/>"
									+"Por favor seleccione una causa que tenga audiencias asociadas",'<bean:message key="aviso"/>');
						}		
					}
				},
				sortorder: "desc"
			}).navGrid('#gridAudiencias',{edit:false,add:false,del:false});	

			primeraConsultaAudiencias = false;
		}
		else{		
			jQuery("#gridAudiencias").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarAudienciasPorNumeroCausa.do?numeroExpedienteId='+numExpIdGlobal+'', atatype: "xml" });
			$("#gridAudiencias").trigger("reloadGrid");
		}
	}

	
	//Valida los campos requeridos para registrar una audiencia
	function validaCamposAudiencia(){
		
		var involucrados = jQuery("#gridInvolucradosAudiencia").jqGrid('getGridParam','selarrrow')
		if(involucrados.length <= 0)
		{
			customAlert("Por favor seleccione al menos un involucrado",'<bean:message key="aviso"/>');
			return false;
		}
			
			
		if($("#institucionSolicitantePJATP").val() <= 0){
			customAlert("Por favor seleccione una Instituci�n Solicitante",'<bean:message key="aviso"/>');
			$("#institucionSolicitantePJATP").focus();
			return false;
		}
		if($("#nombreSolicitantePJATP").val() == "" || $("#apPatSolicitantePJATP").val() == ""){
			customAlert("Por favor ingrese el nombre y apellido paterno del solicitante",'<bean:message key="aviso"/>');
			$("#nombreSolicitantePJATP").focus();
			return false;
		}
		
		if($("#tipoDeAudiencia").val() <= 0){
			customAlert("Por favor seleccione el tipo de la audiencia",'<bean:message key="aviso"/>');
			$("#tipoDeAudiencia").focus();
			return false;
		}		
		
		return true;
	}

	//Valida los campos requeridos para registrar una audiencia de tipo:
	// Audio y video de audiencias
	// Transcripci�n de audiencias
	function validaInstitucionYSolicitante(){
				
		if($("#institucionSolicitantePJATP").val() <= 0){
			customAlert("Por favor seleccione una Instituci&oacute;n Solicitante",'<bean:message key="aviso"/>');
			$("#institucionSolicitantePJATP").focus();
			return false;
		}
		if($("#nombreSolicitantePJATP").val() == "" || $("#apPatSolicitantePJATP").val() == ""){
			customAlert("Por favor ingrese el nombre y apellido paterno del solicitante",'<bean:message key="aviso"/>');
			$("#nombreSolicitantePJATP").focus();
			return false;
		}	
		
		return true;
	}
	
	
	function activaConfirm(id) {					
		switch(id){
			case "cierraVentanaNuevaSolicitud":
				cerrarVentanaNuevaSolicitud();
				break;						
		}
	}
</script>
</head>

<!--Comienza cuerpo del documento-->
<body>
	<!--Comienza tab principal-->
	<div id="tabsprincipalconsulta">
		<ul>
			<li><a href="#tabsconsultaprincipal-1">Datos de la Solicitud</a></li>
		</ul>
		<!--Comienza tab detalle de solicitud-->
		<div id="tabsconsultaprincipal-1" align="left">
			
			<!--Comienza tabla con datos de la solicitud-->
				 <table width="950" border="0" cellspacing="5" cellpadding="0">
			      <tr>
			      	<td colspan="2">
			      	</td>
			      	<td align="right"><div id="divNumeroCaso"><strong>N&uacute;mero de Caso:</strong></div>
			      	</td>
			      	<td>
			      		<input type="text" id="numCasoPJATP" style="width: 200px; border: 0; background: #DDD;"	readonly="readonly" />
			      	</td>
			      	<td colspan="2"> 
			      	</td>
			      </tr>
			      
			      <tr>
			        <td colspan="2"><strong>Tipo de Solicitud:</strong></td>
			        <td width="138" align="right">
			            <div id="divNumeroCausa"><strong>N&uacute;mero de Causa:</strong></div>
			        </td>
			        <td width="203">
			            <input type="text" id="numCausaPJATP" style="width: 200px; border: 0; background: #DDD;"	readonly="readonly" />
			        </td>
			        <td width="125" align="right">
			            <div id="divFechaLimite">
			                <strong>Fecha l�mite: </strong>
			            </div>
			            <div id="divTipoRecurso">
			                <strong>Tipo de recurso:</strong>
			            </div>
			        </td>
			        <td width="209">
			            <input type="text" id="fechaLimiteAudiencia" style="width:78px;" tabindex="12"/>
			            <select id="tipoDeRecurso" style="width: 200px;" disabled="disabled">
			                <option>-Seleccione-</option>
			                <option value="1">Recurso apelaci&oacute;n</option>
			                <option value="0">Recurso casaci&oacute;n</option>
			            </select>
			        </td>
			      </tr>
			      <tr>
			        <td colspan="2"><select id="solicitudPJATP" style="width:200px;" tabindex="1">
			        </select></td>
			        <td align="right">
			            <div id="divFechaSolicitud"><strong>Fecha de Solicitud:</strong></div>
			        </td>
			        <td>
			            <input type="text" id="fechaSolicitudPJATP" style="width: 200px; border: 0; background: #DDD;" readonly="readonly" />
			        </td>
			        <td align="right">
			            <div id="divHoraLimite"><strong>Hora l�mite:</strong></div>
			        </td>
			        <td>
			            <input type="text" id="horaLimiteAudiencia" size="10" value="01:00 AM" tabindex="13"/>
			        </td>
			      </tr>
			      <tr>
			        <td width="137">&nbsp;</td>
			        <td width="77">&nbsp;</td>
			        <td align="right">
			            <div id="divHoraSolicitud"><strong>Hora deSolicitud:</strong></div>
			        </td>
			        <td>
			            <input type="text" id="horaSolicitudPJATP" style="width: 200px; border: 0; background: #DDD;"	readonly="readonly" />
			        </td>
			        <td align="right">
			            <div id="divTipoAudiencia">
			                <strong>Tipo de audiencia:</strong>
			            </div>
			        </td>
			        <td>
			            <select id="tipoDeAudiencia" style="width: 200px;" tabindex="14"></select>
			        </td>
			      </tr>
			      <tr>
			        <td colspan="2"><strong>Ingrese n&uacute;mero de causa:</strong></td>
			        <td align="right">
			            <div id="divInsSolicitante"><strong>Instituci�n Solicitante:</strong></div>
			        </td>
			        <td>
			            <select id="institucionSolicitantePJATP" style="width:200px;" tabindex="6"></select>
			        </td>
			        <td align="right"><span class="au av ra rc ta" ><strong>Adjuntar documento digital:</strong></span></td>
			        <td>
			        	<div id="divAdjuntarDoc" class="au av ra rc ta">
				        	<form id="solicitudPJForm" name="solicitudPJForm" 
				        	action="<%= request.getContextPath() %>/registrarSolicitudPJATP.do" method="post" enctype="multipart/form-data" >
								<input type="file" name="archivoAdjunto" tabindex="15"> 
								<input type="hidden" name="fechaSolicitud" />
								<input type="hidden" name="horaSolicitud" />
								<input type="hidden" name="institucionSolicitante" />
								<input type="hidden" name="nombreSolicitante" />
								<input type="hidden" name="tipoSolicitud" />
								<input type="hidden" name="numeroExpedienteId" />
								<input type="hidden" name="esconderArbol" />
								<input type="hidden" name="formaId" />
								<input type="hidden" name="tipoDeRecurso" />
								<input type="hidden" name="numeroCausa" />
								<input type="hidden" name="numeroCaso" />
								<input type="hidden" name="fechaLimite" />
								<input type="hidden" name="horaLimite" />
								<input type="hidden" name="tipoAudiencia" />
								<input type="hidden" name="motivoSolicitud" />
								<input type="hidden" name="situacionesEspeciales" />
								<input type="hidden" name="involucradosAudiencia" />
								<input type="hidden" name="audienciaId"/>
								
								
							</form>
						</div>
			        </td>
			      </tr>
			      <tr>
			        <td colspan="2"><input type="submit" id="buscarCausa" value="Buscar Causa" tabindex="2" class="btn_Generico" class="btn_Generico"
			        /></td>
			        <td colspan="2" align="center">
			            <div id="divDatosSolicitante">
			                <strong>Datos del solicitante</strong>
			            </div>
			        </td>
			        <td colspan="2" align="center">
			        	<span id="divEtiquetaInvolucrados"><strong>Seleccione los involucrados de la audiencia</strong></span>
			        	<span class="av ra rc ta"><strong>Seleccione la audiencia a la que ser� asociada la solicitud</strong></span>
			        </td>
			      </tr>
			      <tr>
			        <td colspan="2"><input type="text" id="numCasoABuscar" style="width:200px;" tabindex="3"/></td>
			        <td align="right">
			            <div id="divNombreSol"><strong>Nombre(s):</strong></div>
			        </td>
			        <td>
			            <input type="text" id="nombreSolicitantePJATP" style="width: 200px;" onkeypress="return soloLetrasDos(event,this.id);" tabindex="7"/>
			        </td>
			        <td colspan="3" rowspan="10" align="center" valign="top">
			        	<table width="100%" cellpadding="10" id="gridInvolucradosAudienciaContenedor" valign="top">
			        		<tr>
			        			<td><table id="gridInvolucradosAudiencia"></table>
			        			</td>
			        		</tr>
			        	</table>
			        	<table width="100%" cellpadding="10" id="gridAudienciasContenedor" valign="top" class="av ra rc ta">
			        		<tr>
			        			<td><table id="gridAudiencias"></table>
			        			<div id="pagerGridAudiencias"></div>
			        			</td>
			        		</tr>
			        	</table>
			        	
			        </td>
			      </tr>
			      <tr>
			        <td><input type="submit" id="limpiar" value="Limpiar " onclick="limpiarCampoCausa();" tabindex="5" class="btn_Generico"
			        /></td>
			        <td><input type="submit" id="validarCausa" value="Aceptar" onclick="validarNumeroCausa();" tabindex="4" class="btn_Generico"
			        /></td>
			        <td align="right">
			            <div id="divApellidoPat"><strong>Apellido Paterno:</strong></div>
			        </td>
			        <td>
			            <input type="text" id="apPatSolicitantePJATP" style="width: 200px;" onkeypress="return soloLetrasDos(event,this.id);" tabindex="8"/>
			        </td>
			        <td>&nbsp;</td>
			        <td>&nbsp;</td>
			      </tr>
			      <tr>
			        <td colspan="2">&nbsp;</td>
			        <td align="right">
			            <div id="divApellidoMat"><strong>Apellido Materno:</strong></div>
			        </td>
			        <td>
			            <input type="text" id="apMatSolicitantePJATP" style="width: 200px;" onkeypress="return soloLetrasDos(event,this.id);" tabindex="9"/>
			        </td>
			        <td>&nbsp;</td>
			        <td>&nbsp;</td>
			      </tr>
			      <tr>
			        <td colspan="2">&nbsp;</td>
			        <td align="right" valign="top"><span id="divFundamentoSol"><strong>Fundamento de la Solicitud:</strong></span></td>
			        <td><span id="divFundamentoSolCampo"><textarea id="motivoSolicitud" name="motivoSolicitud" 
			        style="width: 200px"
			        cols="35" rows="3"  tabindex="10"></textarea></span></td>
			        <td colspan="2">&nbsp;</td>
			      </tr>
			      <tr>
			         <td colspan="2">&nbsp;</td>
			        <td align="right" valign="top"><strong><span id="divSituacionesEspeciales">Situaciones Especiales a Considerar:</span></strong></td>
			        <td><span id="divSituacionesEspecialesCampo"><textarea cols="35" rows="3"  style="width: 200px"
			        name="situacionesEspeciales"
			        id="situacionesEspeciales" tabindex="11"></textarea></span></td>
			        <td colspan="2">&nbsp;</td>
			      </tr>
			      <tr>
			        <td colspan="2">&nbsp;</td>
			      </tr>
			      <tr>
			        <td colspan="2">&nbsp;</td>
			      </tr>
			      <tr>
			        <td colspan="2">&nbsp;</td>
			        <td colspan="2">&nbsp;</td>
			        <td colspan="2">&nbsp;</td>
			      </tr>
			      <tr>
			        <td colspan="2">
			        	<input type="submit" id="cancelar" value="Cancelar" onclick="confirmaCancelar();" class="btn_Generico"
			        	/>
			        	<input type="submit" id="enviarSolicitud" value="Enviar Solicitud" onclick="enviarSolicitud();" class="btn_Generico"
			        	/></td>
			        <td colspan="2" rowspan="3">&nbsp;</td>
			        <td colspan="2" rowspan="3">&nbsp;</td>
			      </tr>
			      <tr>
			        <td colspan="2">&nbsp;</td>
			      </tr>
			      <tr>
			        <td colspan="2">&nbsp;</td>
			      </tr>
			    </table>
			<!--Termina tabla con datos de la solicitud-->
			
		</div>
		<!--Termina tab detalle de solicitud-->
	</div>
	<!--Termina Tab principal-->
	<form name="formaDocDirecto" id="formaDocDirecto" action="<%= request.getContextPath() %>/GenerarDocumentoDirecto.do" method="post">
		<input type="hidden" name="documentoId" />
		<input type="hidden" name="numeroUnicoExpediente" />
	</form>	
		
	<script type="text/javascript">
		<logic:present name="solicitudNueva">
			terminarGuardadoSolicitud("<%=request.getAttribute("solicitudNueva").toString()%>","<%=request.getAttribute("numeroExpediente").toString()%>");
		</logic:present>
	</script>
	
	<!-- div para el alert dinamico -->
	<div id="dialog-Alert" style="display: none">
		<table align="center">
			<tr>
				<td align="center"><span id="divAlertTexto"></span></td>
			</tr>
		</table>
	</div>
	
</body>
</html>

