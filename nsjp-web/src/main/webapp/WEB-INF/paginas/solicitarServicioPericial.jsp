<!-- 
CU Solicitar Servicio Pericial

Da de alta una solicitud de pericial ya sea Dictamen o Asesor&iacute;a
Cada una se dirige a un funcionario diferente.
 -->

<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.funcionario.Especialidades"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.funcionario.TipoEspecialidad"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.funcionario.Puestos"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Solicitar Servicio Pericial</title>
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<!--Script para el idioma del calendario-->
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>	
	
	<script type="text/javascript">
	numeroExpediente = '<%=request.getParameter("numeroExpediente")%>';
	numeroExpedienteId = '<%=request.getParameter("numeroExpedienteId")%>';
	var idSolicitudPericial='<%=request.getParameter("solcitudPericialId")%>';

	//documentoId = 0;

	 // Variable para definir el &aacute;rea de donde proviene la solicitud.
	 // Para Procuraduria el valor es 1
	 // Para Defensoria el valor es 2
	var area = <%=request.getParameter("area")%>;
	var dictamenId = <%= mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes.DICTAMEN.getValorId() %>;
	var subArea = <%=request.getParameter("subArea")%>;
	
/**Variables**/
	var firstGridDetalleCadenaCustodia = true;

	var puestoId;
	var rolId;	
	var existeDestinatario = false;
	
/******Valiables para la ceja de documentos******/
	var primeraVezGridDocumentosDigitales = true;
	var CONTEXT_ROOT = '<%= request.getContextPath()%>';
		
	$(document).ready(function() {
		
		//verifica si el numero de expediente es nulo
		//verificaExpediente(numeroExpedienteId);
		
		$("#tabsPrincipal").tabs();
		$("#guardarNarraTiva").css("display", "none");
		$("#solServPericialNumExpediente").val(numeroExpediente);
		$("#solServPericialTipoServ").change(consultaFuncionario);
		$("#btnEnviarSolicitud").click(enviarSolicitud);
		$("#btnReenviarSolicitud").click(enviarSolicitudPeritoProceso);
		
		//$("#solServPericialConsCadenaCustodia").click(gridCadenaCustodiaInicial);
		//$("#solServPericialAddCadenaCustodia").click(addCadenaRegistrada);

		$("#solServPericialAddCadenaCustodia").click(agregarEvidencias);
		$("#solServPericialDelCadenaCustodia").click(eliminarEvidencias);

		$("#solServPericialFechaLimite").attr('readonly','readonly');

		if(area == 1){
			$('#solServPericialTipoServ').find("option[value='"+dictamenId+"']").attr("selected","selected");
			$('#solServPericialTipoServ').attr('disabled','disabled');

			//Sub area de atecion temprana no penal
			if(subArea == '<%=Areas.ATENCION_TEMPRANA_PG_NO_PENAL.parseLong()%>'){
				muestraOcultaTab();
			}
		}
				
		//Agregar estilo del date picker para seleccionar anio y mes
		$("#solServPericialFechaLimite").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			minDate: new Date(),
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		
		//Carga los datos del usuario firmado
		cargaDatosDelUsuario();
		cargaEspecialidadPericial();
		cargaFechaCaptura();
		gridCadenaCustodiaInicial(numeroExpedienteId,0);
		gridCadenaCustodiaAsignados();

		//Funciones para destinatarios
		cargaDistritos();

		$("#solServPericialDistrito").change(function(e){
			cargaDiscriminantesPorDistrito();
		});


		$("#solServPericialAgencia").change(function(e){
			consultaFuncionario();
		});
		
	/******Valiables para la ceja de documentos******/
		//Carga los documentos adjuntos que se adjuntaran a la solicitud de periciales
		cargaGridDocumentosDigitales();
		$('#enviarSolProceso').hide();
	if(idSolicitudPericial != null && idSolicitudPericial>0){
		$("#documentsTab").tabs("option", "selected");
		$('#enviarSolProceso').show();
		$('#evidenciaTab').hide();
		$('#solicitudTab').hide();
		$('#enviarSol').hide();
		$('#tabsconsultaprincipal-1').hide();
		cargaGridSolicitidesPericialesRegresadasPorExpediente();
	}
		
	});

	

	/*
	*Funcion para seleccionar la tab de Solicitud y ocultar
	*la tab de evidencia
	*/
	function muestraOcultaTab() {

		// primera tab seleccionada
		var $tabs = $('#tabsPrincipal').tabs(); 
		
	    $tabs.tabs('select', 1); // switch a la segunda tab
	  	//oculta la primera tab
		$('#evidenciaTab').hide(); 
		$('#tabsconsultaprincipal-1').hide();
	}


	/*
	*Funcion que limpia el combo box de discriminantes, dependientes del distrito
	*/
	 function cleanComboDiscriminante(){

			$('#solServPericialAgencia').empty();
			$('#solServPericialAgencia').append('<option value="0">-Seleccione-</option>');	//Por default seleccione
	}

	 /*
	*Funcion que limpia el combo box de destinatario dependientes del discriminante y del tipo de servicio
	*/
	 function cleanComboDestinatario(){
		$('#solServPericialNombreFuncionario').empty();
		$('#solServPericialNombreFuncionario').append('<option value="0">-Seleccione-</option>');	//Por default seleccione
		existeDestinatario = false;
	}
		

	/*
	*Funcion que consulta el distrito para localizar los destinatarios
	*/
	function cargaDistritos(){
		
		$('#solServPericialDistrito').append('<option value="-1">-Seleccione-</option>');	//Por default seleccione
		
		$.ajax({
			type: 'POST',
	    	url: '<%=request.getContextPath()%>/consultarDistritos.do',
	    	data: '',
	    	dataType: 'xml',
	    	async: false,
	    	success: function(xml){
		    		$(xml).find('listaCatalogo').find('catDistritoDTO').each(function(){
						$('#solServPericialDistrito').append('<option value="' + $(this).find('catDistritoId').text() + '">' + $(this).find('claveDistrito').text()+" - "+ $(this).find('nombreDist').text() + '</option>');
					});					
			}
		});
	}

	/*
	*Funcion que obtiene los discriminantes de acuerdo al distrito seleccionado por el usuario
	*y de acuerdo a la institucion actual si es PG, devolvera agencias, si es PJ devolvera tribunales. Etc.
	*/
	function cargaDiscriminantesPorDistrito(){

		var selected = $("#solServPericialDistrito option:selected");
		
		cleanComboDiscriminante();
		cleanComboDestinatario();		
		
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarDiscriminantesXDistritoSinWSInstitucion.do',
			data: 'distritoId=' + selected.val(),	//Parametro para hacer la consulta de Entidades por Id del Pa&iacute;s
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catDiscriminanteDTO').each(function(){
					$('#solServPericialAgencia').append('<option value="' + $(this).find('catDiscriminanteId').text() + '">' + $(this).find('nombre').text() + '</option>');
				});
		 	}
		});	
	}
	

	/*
	*Obtiene los parametros para la consulta del funcionario y valida si est&aacute;n completos. 
	*/
	function obtenerParametrosConsultaFuncionario(){

		$('#solServPericialPuestoFuncionario').val("");
		var parametrosConsulta = "";
		var catDiscriminanteId = $('#solServPericialAgencia').val();
		
		tipoServicio = $("#solServPericialTipoServ option:selected").val();
		

		if(tipoServicio == 0){
			customAlert("Seleccione un tipo de servicio pericial");
		}
		else if(catDiscriminanteId == 0){
			customAlert('<bean:message key="mensajeAgenciaValidarSolicitudSerPer"/>');
		}else{

			if(tipoServicio == '<%=TiposSolicitudes.ASESORIA.getValorId()%>'){
				
				rolId = <%=Roles.COORDINADORPER.getValorId()%>;
				puestoId = <%=Puestos.COORDINADOR_PERICIAL_DE_DEFENSORIA.getValorId()%>;
				$('#solServPericialPuestoFuncionario').val("COORDINADOR PERICIAL DE DEFENSORIA");
				
			}else if(tipoServicio == '<%=TiposSolicitudes.DICTAMEN.getValorId()%>'){
				if(area == 1){
					
					rolId = <%=Roles.COORDPERFIS.getValorId()%>;
					puestoId = <%=Puestos.COORDINADOR_DE_SERVICIOS_PERICIALES.getValorId()%>;
					$('#solServPericialPuestoFuncionario').val("COORDINADOR DE SERVICIOS PERICIALES");
					
				}else{
					
					rolId = <%=Roles.COORDINADORDEF.getValorId()%>;
					puestoId = <%=Puestos.COORDINADOR_DE_DEFENSORES.getValorId()%>;
					$('#solServPericialPuestoFuncionario').val("COORDINADOR DE DEFENSORES");
				}
			}
			 
			parametrosConsulta += 'catDiscriminanteId='+ $('#solServPericialAgencia').val();
			parametrosConsulta += '&rolId=' + rolId;
		}

		return parametrosConsulta;
	}

	
	/**
	* Carga el funcionario a mostrar en la tab de Avisar a Funcionario seg&uacute;n puesto del destinatario
	*/
	function consultaFuncionario(){

		cleanComboDestinatario();
		
		var parametrosConsultaFuncionario="";
		parametrosConsultaFuncionario = obtenerParametrosConsultaFuncionario();
		
		if(parametrosConsultaFuncionario != ""){
			
			$.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/consultarFuncionarioANotificarXDiscriminanteYRol.do',
	    		data: parametrosConsultaFuncionario,
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){

		    		var numeroDestinatarios = 0;  
		    		
	    			$(xml).find('funcionarioDTO').each(function(){
						$('#solServPericialNombreFuncionario').append('<option value="' + $(this).find('claveFuncionario').text() + '">' + 
								$(this).find('nombreFuncionario').first().text()
								+" "+ $(this).find('apellidoPaternoFuncionario').first().text()
								+" "+ $(this).find('apellidoMaternoFuncionario').first().text()
								+ '</option>');
						numeroDestinatarios ++;
					});
					
					if(numeroDestinatarios > 0){
						existeDestinatario = true;
					}
	    		}
	    	});

		}	
		else{
			customAlert("Verifique que todos los campos obligatorios han sido capturados");
		}	
	}


	/*Funcion que dispara el Action para consultar los datos de usuario*/
    function cargaDatosDelUsuario(){
    	
    	$('#solServPericialAreaAdmin').val('');
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/consultarDatosUsuario.do',
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){

    			var errorCode;
				errorCode=$(xml).find('response').find('code').text();				
				if(parseInt(errorCode)==0){	
					$('#solServPericialAreaAdmin').val( $(xml).find('usuarioDTO').find('areaActual').find('nombre').first().text());
					$('#solServPericialNombre').val(
							$(xml).find('usuarioDTO').find('funcionario').find('nombreFuncionario').first().text() + " " +
							$(xml).find('usuarioDTO').find('funcionario').find('apellidoPaternoFuncionario').first().text() + " "+
							$(xml).find('usuarioDTO').find('funcionario').find('apellidoMaternoFuncionario').first().text());
					$('#solServPericialPuesto').val($(xml).find('usuarioDTO').find('funcionario').find('puesto').find('valor').first().text() );
					
    			}
    		}
    	});
    }

    /*Funcion que dispara el Action para consultar Las especialidades Pericialess*/		
    function cargaEspecialidadPericial(){
    	
    	  	$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/consultarCatalogoEspecialidadPericial.do',
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			var option;
    			$(xml).find('catEspecialidadPericial').each(function(){

    				if(subArea == '<%=Areas.ATENCION_TEMPRANA_PG_NO_PENAL.parseLong()%>'){
        				
    					var especialidad =  $(this).find('clave').text();
    					
    					if(especialidad == '<%=Especialidades.ATENCION_MEDICA.getValorId()%>' ||
    							especialidad == '<%=Especialidades.ATENCION_PSICOLOGICA.getValorId()%>' ||
    							especialidad == '<%=Especialidades.ATENCION_DE_SERVICIO_SOCIAL.getValorId()%>'){
							
    						$('#solServPericialEspecialidad').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
        				}
    					
    				}else{
    					$('#solServPericialEspecialidad').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
        			}
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
    	    async: false,
    	    success: function(xml){
    			$('#solServPericialFecha').val($(xml).find('fechaActual').text());
    		}
		});
    }

	/*Funcion que cierra la ventana*/
	function cerrarVentana(){
		parent.cerrarVentana();
	}

	
	function enviarSolicitudPeritoProceso(){
		//Obtiene los ids de los documentos que seran visibles para la solicitud
		var idsDoctsSelecc = obtenerDocumentosSeleccionados();
		//Datos 
		var parametros = "";
		
		parametros += '&idsDoctsSelecc=' + idsDoctsSelecc;
		parametros += '&idSolicitudPericial=' + idSolicitudPericial;
		parametros += '&numeroExpedienteId=' + numeroExpedienteId;
		
		$.ajax({
			///enviarSolicitudServicioPericial.do
    		type: 'POST',
    	    url:'<%=request.getContextPath()%>/enviarDocumentosSolicitudServicioPericialProceso.do',
    	    data:parametros,
    	    dataType: 'xml',
    	    async: false,
    	    success: function(xml){
    	    	customAlert("La solicitud fue enviada correctamente");
    	    	//$('#btnEnviarSolicitud').attr('disabled','disabled');
    			//parent.cerrarVentanaSolicitarServicioPericial();
    		}
		});
	}
	
	/*
	*Funcion que obtiene toda la informacion para enviar la solicitud pericial
	*/
	function enviarSolicitud(){

		//numeroExpediente = + $('#solServPericialNumExpediente').val();
		tipoServicio = $("#solServPericialTipoServ option:selected").val();
		especialidad = $("#solServPericialEspecialidad option:selected").val();
		estudios = $("#solServPericialEstudios option:selected").val();
		fechaLimite = $("#solServPericialFechaLimite").val();
		arrayIds = jQuery("#gridDetalleCadenaCustodiaAsignados").getDataIDs();
		observaciones = $("#areaDescripcion").val();
		//Obtiene los ids de los documentos que seran visibles para la solicitud
		idsDoctsSelecc = obtenerDocumentosSeleccionados();
		funcDestinatario = $('#solServPericialNombreFuncionario').val();
		//existeDestinatario == false
		
		if(fechaLimite == "" || tipoServicio == 0 || especialidad == 0 || (estudios == 0 || estudios == 1) ||  
				funcDestinatario == 0 || (observaciones == "" && subArea != '<%=Areas.ATENCION_TEMPRANA_PG_NO_PENAL.parseLong()%>') ){
			var cadena="";
			if(fechaLimite==""){
				cadena+="<br />- Fecha L&iacute;mite";
			}
			if(tipoServicio==0){
				cadena+="<br />- Tipo de Servicio";
			}
			if(especialidad==0){
				cadena+="<br />- Especialidad";
			}
			if(estudios==0 || estudios==1){
				cadena+="<br />- Estudios";
			}
			if(funcDestinatario==0){
				cadena+="<br />- Funcionario Destinatario";
			}
			if(observaciones=="" && subArea != '<%=Areas.ATENCION_TEMPRANA_PG_NO_PENAL.parseLong()%>'){
				cadena+="<br />- Recomendaciones";
			}
			alertDinamico("<p align='left'>Favor de revisar los siguientes campos obligatorios: <br />"+cadena+"</p>");
		}else{

			//Datos de la ceja Solicitante
			var parametros = "";
			
			parametros += '&numeroExpedienteId=' + numeroExpedienteId;
			parametros += '&tipoServicio=' + tipoServicio;
			parametros += '&especialidad=' + especialidad;
			parametros += '&estudios=' + estudios;
			parametros += '&fechaLimite=' +  fechaLimite;
			parametros += '&arrayIds=' +  arrayIds;
			parametros += '&area=' +  area;
			parametros += '&observaciones=' + observaciones;
			parametros += '&destinatarioId=' + puestoId;
			parametros += '&idsDoctsSelecc=' + idsDoctsSelecc;
			parametros += '&funcDestinatario=' + funcDestinatario;
			parametros += '&rolId=' + rolId;
			
			if(idsDoctsSelecc.length < 1){
				if(confirm("No ha anexado documentos,\n&iquest;Desea continuar enviando la solicitud?")) {
					guardarSolicitud(parametros);
				}				
			}
			else{
				guardarSolicitud(parametros);
			}
		}
	}

	/*
	*Funcion que guarda la solicitud pericial
	*/
	function guardarSolicitud(parametros){
		$.ajax({
			///enviarSolicitudServicioPericial.do
    		type: 'POST',
    	    url:'<%=request.getContextPath()%>/enviarEvidenciasSolicitudServicioPericial.do',
    	    data:parametros,
    	    dataType: 'xml',
    	    async: false,
    	    success: function(xml){
    	    	customAlert("La solicitud fue enviada correctamente");
    	    	$('#btnEnviarSolicitud').attr('disabled','disabled');
    			parent.cerrarVentanaSolicitarServicioPericial();
    		}
		});
	}

	
	function gridCadenaCustodiaInicial(){
		var folioCadenaCustodia = $("#solServPericialCadenaCustodia").val();

		if(firstGridDetalleCadenaCustodia == true){
		jQuery("#gridDetalleCadenaCustodia").jqGrid({ 
			url:'<%= request.getContextPath()%>/consultarCadenaCustodiaXNumeroExpediente.do?numeroExpedienteId='+numeroExpedienteId+'&folioCadenaCustodia='+folioCadenaCustodia+'', 
			data:'',
			datatype: "xml", 
			colNames:['Cadena de Custodia','Objeto','Descripci&oacute;n','C&oacute;digo de Barras'],
			colModel:[ {name:'CadenaCustodia',index:'1', width:150},
						{name:'Objeto',index:'2', width:150},
						{name:'Descripcion',index:'3', width:150},
						{name:'CodigoBarras',index:'4', width:150}
			],
			pager: jQuery('#pagerCadenaCustodia'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			sortname: 'CadenaCustodia',
			viewrecords: true,
			sortorder: "asc",
			multiselect: true
			}).navGrid('#pagerCadenaCustodia',{edit:false,add:false,del:false});

			firstGridDetalleCadenaCustodia=false;
		}else{
			jQuery("#gridDetalleCadenaCustodia").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarCadenaCustodiaXNumeroExpediente.do?numeroExpedienteId='+numeroExpedienteId+'&folioCadenaCustodia='+folioCadenaCustodia+'',datatype: "xml" });
			$("#gridDetalleCadenaCustodia").trigger("reloadGrid");			
		}
	}
	
	function gridCadenaCustodiaAsignados(){
		jQuery("#gridDetalleCadenaCustodiaAsignados").jqGrid({ 
			url:'local', 
			data:'',
			datatype: "xml", 
			colNames:['Cadena de Custodia','Objeto','Descripci&oacute;n','C&oacute;digo de Barras'],
			colModel:[ {name:'CadenaCustodia',index:'1', width:150},
						{name:'Objeto',index:'2', width:150},
						{name:'Descripcion',index:'3', width:150},
						{name:'CodigoBarras',index:'4', width:150}
			],
			pager: jQuery('#pagerCadenaCustodiaAsignados'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			sortname: 'CadenaCustodia',
			viewrecords: true,
			sortorder: "asc",
			multiselect: true
			}).navGrid('#pagerCadenaCustodiaAsignados',{edit:false,add:false,del:false});
		}
		
    function addCadenaRegistrada(){
		var selecciones = jQuery("#gridDetalleCadenaCustodia").jqGrid('getGridParam','selarrrow');
		if (selecciones) {
			selecciones = selecciones +"";
			var id = selecciones.split(",");
			var registrosSeleccionados = "(";
			for(var a= 0; a<id.length;a++){
				var ret = jQuery("#gridDetalleCadenaCustodia").jqGrid('getRowData',id[a]);
				var numEvidencia =  ret.NumeroEvidencia;
				numEvidencia = numEvidencia.substring(numEvidencia.length-9,numEvidencia.length-6);
				registrosSeleccionados = registrosSeleccionados + numEvidencia + ",";
			}
			registrosSeleccionados  = registrosSeleccionados.substring(0, registrosSeleccionados.length - 1);
			registrosSeleccionados = registrosSeleccionados + ')';
			$('#solServPericialCadenasRegistradas').append('<option>'+$('#solServPericialCadenaCustodia').val() + registrosSeleccionados +'</option>');
		} else {
			customAlert("Seleccione un registro");
		} 
		
	}

    //Llena el combo de estudios en base a la especialidad seleccionada
	function buscaEstudios(){
		var selected = $("#solServPericialEspecialidad option:selected").val();
		$( "#solServPericialEstudios" ).attr('selectedIndex',0);
		$('#solServPericialEstudios').empty();
		$('#solServPericialEstudios').append('<option value="0">-Seleccione-</option>');
		
		$.ajax({
			async: false,									// la accion cargar los estudios
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoEstudiosXEspecialidad.do?especialidadId='+selected+'',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('estudio').each(function(){
					$('#solServPericialEstudios').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	/*
	*Funcion que agrega una evidencia del grid de evidencias al grid de evidencias asignadas
	*/
	function agregarEvidencias(){
		//var row = jQuery("#gridDetalleCadenaCustodia").jqGrid('getGridParam','selrow');
		var arraySelRows = jQuery("#gridDetalleCadenaCustodia").getGridParam('selarrrow');

		if (arraySelRows.length > 0) { 
			for(i=0; i<arraySelRows.length; i++){
				var ret = jQuery("#gridDetalleCadenaCustodia").jqGrid('getRowData',arraySelRows[i]);
				var arrayIds = jQuery("#gridDetalleCadenaCustodiaAsignados").getDataIDs();
				var cont=0;
				while(cont<arrayIds.length){
					if(arraySelRows[i] == arrayIds[cont]){
						customAlert("La evidencia ya se encuentra asignada ");
						break;
					}
					cont++;		
				}
				if(cont==arrayIds.length){
					jQuery("#gridDetalleCadenaCustodiaAsignados").jqGrid('addRowData',arraySelRows[i],ret);
				}				
			}
		} 
		else{
			 customAlert("Por favor seleccione un rengl&oacute;n");
		} 		
	} 

	/*
	*Funcion que elimina una evidencia del grid de evidencias seleccionadas
	*/
	function eliminarEvidencias(){
		var arraySelRows = new Array();
		arraySelRows  = jQuery("#gridDetalleCadenaCustodiaAsignados").getGridParam('selarrrow');

		if (arraySelRows.length > 0) {
			for(var i=arraySelRows.length-1; i>=0; i--){
				jQuery("#gridDetalleCadenaCustodiaAsignados").jqGrid('delRowData',arraySelRows[i]);
			}
		} 
		else{
			 customAlert("Por favor seleccione un rengl&oacute;n");
		} 	
	}


/****************************************************************FUNCIONALIDAD PARA LA CEJA DOCUMENTOS************************************************************/
	/*
	*Funcion que carga el grid con los nombre de los documentos digitales asociados 
	*al id de la solicitud de serv. periciales
	*/
	function cargaGridDocumentosDigitales(){

		if(primeraVezGridDocumentosDigitales == true){
			jQuery("#gridDocumentosDigitales").jqGrid({
				url:'<%=request.getContextPath()%>/consultarDocumentos.do?idExpedienteop='+numeroExpedienteId+'',
				datatype: "xml", 
				colNames:['&Aacute;rea del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento','strEsGuardadoParcial','esGuardadoParcial','Tipo de documento'],
				colModel:[ 	{name:'area',index:'area', width:200},
							{name:'fechaActividad',index:'fechaActividad', width:170},							
							{name:'nombreActividad',index:'nombreActividad', width:400},
				           	{name:'tipo',index:'tipo', width:155}, 
							{name:'nombre',index:'nombre', width:255},
				           	{name:'fecha',index:'fecha', width:170},
							{name:'strEsGuardadoParcial',index:'strEsGuardadoParcial', width:255, hidden:true},
							{name:'esGuardadoParcial',index:'esGuardadoParcial', width:255, hidden: true},
							{name:'extension',index:'extension', width:150, align:"center"}
							],
				pager: jQuery('#pagerGridDocumentosDigitales'),
				rowNum:5,
				rowList:[5,10,15,20,25,30,35,40,45,50],
				sortname: 'nombreDocumento',
				viewrecords: true,
				sortorder: "desc",
				multiselect:true,
				width:900,
				ondblClickRow: function(rowid) {
					if (rowid) {
						 var loDocumento = jQuery("#gridDocumentosDigitales").jqGrid('getRowData',rowid);  
						 var loExtension = loDocumento.extension.toLowerCase();
						 var titulo = loDocumento.nombre;
						 if(loExtension.indexOf(".pdf") > 0){
							abrirDocsDigAsociadosASol(rowid);							
						 }else{//Si se trata de un documento diferente al pdf
								$("#visorDocsFrame").hide();
								if(rowid != null && rowid != ""){
									if(loDocumento.tipo.indexOf('Archivo de audio') > 0){
										abreFrameAudio(rowid,titulo,'documentoId');
									}else{
										consultaArchivoDigital(rowid);
									}
								}
						 }
						
					}
				},
				loadComplete: function(){
					jQuery("#gridDocumentosDigitales").jqGrid('hideCol',["area","fechaActividad","nombreActividad","fecha","extension"]);
				}
			}).navGrid('#pagerGridDocumentosDigitales',{edit:false,add:false,del:false});
			primeraVezGridDocumentosDigitales= false;
		}
		else{
			jQuery("#gridDocumentosDigitales").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDocumentos.do?idExpedienteop='+numeroExpedienteId+'',datatype: "xml" });
			$("#gridDocumentosDigitales").trigger("reloadGrid");
		}
	}

	/*
	*Funcion que abre el PDF para ver los documentos asociados al numero de causa
	*/
	function abrirDocsDigAsociadosASol(documentoId){
		if(documentoId != null && documentoId != ""){
			$("#visorDocsFrame").show();
			$("#visorDocsFrame").attr("src","<%= request.getContextPath()%>/consultarArchivoDigitalIframe.do?documentoId="+documentoId+"&inFrame=true");
		}
		else{
			customAlert("El documento no puede ser mostrado");
		}
	}

	/*
	*Funcion que obtiene los ids de los documentos seleccionados, para relacionarlos
	*con la solicitud, y sean visibles para el perito
	*/
	function obtenerDocumentosSeleccionados(){
		var arraySelRows = jQuery("#gridDocumentosDigitales").getGridParam('selarrrow');
		return arraySelRows;
	}
	
	function cargaGridSolicitidesPericialesRegresadasPorExpediente(){

		var concluida= <%=EstatusSolicitud.RESPONDIDA.getValorId()%>;
		jQuery("#gridSolicitudesPeri3").jqGrid({ 
			url:'<%= request.getContextPath()%>/consultarSolicitudesPericialesPorExpediente.do?numeroExpedienteId='+numeroExpedienteId+'&estatus='+concluida+'', 
			datatype: "xml", 
			colNames:['No. Caso','No. Expediente', 'Folio','Estatus','Fecha Creaci&oacute;n','Fecha Limite','Instituci&oacute;n','Remitente'], 
			colModel:[ 	{name:'caso',index:'caso', width:150},
			           	{name:'expediente',index:'expediente', width:130}, 
						{name:'folio',index:'folio', width:125}, 
						{name:'estatus',index:'estatus', width:100,hidden:true}, 
						{name:'fechaCreacion',index:'fechaCreacion', width:125},
						{name:'fechaLimite',index:'fechaLimite', width:125},
						{name:'institucion',index:'institucion', width:125},
						{name:'remitente',index:'remitente', width:200,hidden:true}
					],
			pager: jQuery('#pagerGridSolicitudesPeri3'),
			rowNum:5,
			rowList:[5,10,15,],
			autowidth: true,
			caption:"SOLICITUDES EN PROCESO",
			sortname: 'caso',
			viewrecords: true,
			ondblClickRow: function(rowid){
				
			},
			sortorder: "desc"
		});
	}
	
	
	//Variable para controlar el action para consultar arvhivos digitales
	var accionConsultarArchivo="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do";

	/*
	*Funcion que aplica submit a la forma para abrir el documento solicitado
	*id= id del documento seleccionado en el grid de documentos
	*As&iacute; se obtenia anteriormente:
	*	
	*document.frmDoc.documentoId.value = id;
	*document.frmDoc.submit();
	*/
	function consultaArchivoDigital(id){
		document.frmDoc.action=accionConsultarArchivo+"?documentoId="+id;
		document.frmDoc.submit();
	}
	
	
    </script>
</head>
<body>

	<!-- div para el alert dinamico -->
	<div id="dialog-Alert" style="display: none">
		<table align="center">
			<tr>
				<td align="center">
					<span id="divAlertTexto"></span>
				</td>
			</tr>
		</table>	
	</div> 

<table width="100%">
	<tr>
		<td>
			<div id="tabsPrincipal">
				<ul>
					<li id="evidenciaTab"><a href="#tabsconsultaprincipal-1">Evidencia</a></li>
					<li id="solicitudTab"><a href="#tabsconsultaprincipal-2">Solicitud</a></li>
					<li id="documentsTab" ><a href="#tabsconsultaprincipal-3">Documentos</a></li>
					<li id="enviarSol"><a href="#tabsconsultaprincipal-4">Enviar Solicitud</a></li>
					<li id="enviarSolProceso"><a href="#tabsconsultaprincipal-5">Solicitudes en Proceso</a></li>
				</ul>
				
				<div id="tabsconsultaprincipal-1">
					<table width="100%" border="0">
						<tr>
							<td align="right">
								Cadena de custodia:
							</td>
							<td colspan="2">
								<input type="text" size="50" maxlength="50" id="solServPericialCadenaCustodia"/>
								<input type="button" id="solServPericialConsCadenaCustodia" value="Consultar" onclick="gridCadenaCustodiaInicial()" class="ui-button ui-corner-all ui-widget"/>
							</td>
				          <td>Recomendaciones:</td>
				          							
						</tr>
						<tr>
							<td colspan="3">
								<table id="gridDetalleCadenaCustodia"></table>
								<div id="pagerCadenaCustodia"></div>
							</td>
							<!--<td>
								Cadenas Registradas:<br/>
								<select id="solServPericialCadenasRegistradas" size="3" style="width: 180px;"></select>
							</td>-->

				          <td>
				            <textarea id="areaDescripcion" cols="45" rows="5" style="width: 500px; height:200px;" ></textarea>
			              </td>											
													
						</tr>
						<tr>
							<td colspan="3">
								<table id="gridDetalleCadenaCustodiaAsignados"></table>
								<div id="pagerCadenaCustodiaAsignados"></div>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="right">
								<input type="button" id="solServPericialAddCadenaCustodia" value="Agregar Evidencia" class="ui-button ui-corner-all ui-widget"/>
								<input type="button" id="solServPericialDelCadenaCustodia" value="Eliminar Evidencia" class="ui-button ui-corner-all ui-widget"/>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="tabsconsultaprincipal-2">
				<fieldset style="width: 700px;">
				<legend>Responsable</legend>
					<table width="100%" border="0" height="90%">
						<tr>
							<td align="right">
								Nombre del servidor P&uacute;blico:
							</td>
							<td>
								<input type="text" class="" size="50" maxlength="50" id="solServPericialNombre" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td align="right" >
								Cargo:
							</td>
							<td>
								<input type="text" class="" size="50" maxlength="50" id="solServPericialPuesto" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								&Aacute;rea Administrativa:
							</td>
							<td>
								<input type="text" class="" size="50" maxlength="50" id="solServPericialAreaAdmin" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								Fecha de Solicitud:
							</td>
							<td>
								<input type="text" class="" size="50" maxlength="50" id="solServPericialFecha" disabled="disabled"/>
							</td>
						</tr>
					</table>
				</fieldset>
				<fieldset style="width: 700px;">
				<legend>Datos de la Solicitud</legend>
					<table width="100%" border="0" height="90%">
						<tr>
							<td align="right">
								N&uacute;mero de Expediente:
							</td>
							<td>
								<input type="text" size="50" maxlength="50" id="solServPericialNumExpediente" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								Tipo de servicio:
							</td>
							<td>
								<select id="solServPericialTipoServ" style="width: 180px;">
									<option value="0">- Seleccione -</option>
									<option value="<%= mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes.ASESORIA.getValorId() %>">Asesor&iacute;a</option>
									<option value="<%= mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes.DICTAMEN.getValorId() %>">Dictamen</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">
								Fecha L&iacute;mite:
							</td>
							<td>
								<input type="text" id="solServPericialFechaLimite" size="50" maxlength="50"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								Especialidad:
							</td>
							<td>
								<select id="solServPericialEspecialidad" style="width: 180px;" onchange="buscaEstudios();">
									<option value="0">- Seleccione -</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">
								Estudios:
							</td>
							<td>
								<select id="solServPericialEstudios" style="width: 180px;">
									<option value="0">- Seleccione -</option>
								</select>
							</td>
						</tr>
					</table>					
				</fieldset>
				</div>
				
				<!--Comienza div para adjuntar documentos al enviar la solicitud-->
				<div id="tabsconsultaprincipal-3">
				
						<table width="100%" border="0">
						  <tr>
						    <td align="center">
							     <table id="gridDocumentosDigitales"></table>
							     <div id="pagerGridDocumentosDigitales"></div>
						    </td>
						  </tr>
						  <tr>
						    <td>&nbsp;</td>
						  </tr>
						  <tr>
						    <td align="center"> <iframe id='visorDocsFrame' width="900" height="500" src="" style="display: none"></iframe></td>
						  </tr>
						</table>
					
				</div>
				<!--Termina div para adjuntar documentos al enviar la solicitud-->
				
				
				<div id="tabsconsultaprincipal-4">
					<table width="45%" border="0" height="90%" border="1">
						<tr>
				        	<td align="right">Distrito:</td>
				            <td align="left">
				            	<select style="width:250px" id="solServPericialDistrito"></select>
				            </td>
				        </tr>
				        <tr>
				        	<td align="right"><bean:message key="agencia"/>:</td>
				            <td align="left">
				            	<select style="width:250px" id="solServPericialAgencia">
				            		<option value="0">-Seleccione-</option>
				            	</select>
				            </td>
				        </tr>
				        <tr>
				        	<td align="right">Nombre Funcionario:</td>
				            <td align="left">
								<!--<input type="text" class="" size="50" maxlength="50" id="solServPericialNombreFuncionario" disabled="disabled"/>-->
								<select style="width:250px" id="solServPericialNombreFuncionario">
				            		<option value="0">-Seleccione-</option>
				            	</select>
				            </td>
				        </tr>
				        <tr>
				        	<td align="right">Puesto:</td>
				            <td align="left">				                
				                <input type="text" class="" size="50" maxlength="50" id="solServPericialPuestoFuncionario" disabled="disabled"/>
				            </td>
				        </tr>
				        <tr>
				        	<td>&nbsp;</td>
				        </tr>
						<tr>
							<td align="left">
								<input type="button" id="btnEnviarSolicitud" value="Enviar Solicitud" class="ui-button ui-corner-all ui-widget">
							</td>
						</tr>
				    </table>
				</div>
				
				<!--Comienza div para adjuntar documentos a la solicitud en proceso-->
				<div id="tabsconsultaprincipal-5">
					<table width="1000"  border="0" cellspacing="0" cellpadding="0">
			              <tr>
			    	        <td height="15" colspan="4" align="center" valign="top">
								<table id="gridSolicitudesPeri3" width="670px"></table>
								<div id="pagerGridSolicitudesPeri3"></div>
							</td>
							<td align="left">
								<input type="button" id="btnReenviarSolicitud" value="Enviar Solicitud" class="ui-button ui-corner-all ui-widget">
							</td>
			              </tr>
			            </table>
				</div>
				<!--Termina div para adjuntar documentos a la solicitud en proceso-->
				
			</div>
		</td>
	</tr>
</table>

	<!--Forma asociada para mostrar los archivos digitales -->
	<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
	</form>
	
</body>
</html>