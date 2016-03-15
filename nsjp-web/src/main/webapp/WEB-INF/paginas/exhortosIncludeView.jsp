<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades" %>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Elaborar Solicitud</title>
	
	<!--iframe que crea una nueva peticion para imprimir un PDF-->
	<iframe id="framePdf" src="" width="0" height="0"></iframe>
	
	<!--		Hojas de estilos asociadas-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/1.8.10/${theme.name}/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/1.8.10/${theme.name}/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/arbolExpediente.js"></script>
	<jsp:include page="/WEB-INF/paginas/encabezadoDocumentos.jsp"/>
	<script type="text/javascript">
	
	<%
		UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
		FuncionarioDTO funcionario = usuario.getFuncionario();%>
		
	var contextPath = '<%=request.getContextPath()%>';
	var nombreFuncionario = "<%=funcionario.getNombreFuncionario()%>";
	var apaterFuncionario = "<%=funcionario.getApellidoPaternoFuncionario()%>";
	var amaterFuncionario = "<%=funcionario.getApellidoMaternoFuncionario()%>";
	var claveFuncionario =  "<%=funcionario.getClaveFuncionario()%>";
			
	var idWindowPdf = 1;
	var esconderArbol = <%=request.getParameter("esconderArbol")!=null?"true":"false"%>;
	var numeroUnicoExpediente = '<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):request.getParameter("causa")%>';
	var idResolutivo= '<%=request.getParameter("idResolutivo")!=null?request.getParameter("idResolutivo"):"Sin Resolutivo"%>';
	var idAudiencia= '<%=request.getParameter("idAudiencia")!=null?request.getParameter("idAudiencia"):"Sin Audiencia"%>';
	var idExhorto= '<%=request.getParameter("idExhorto")!=null?request.getParameter("idExhorto"):"false"%>'
	var numeroExpedienteId='<%= request.getParameter("numeroExpedienteId")%>';
	var idEvento= '<%=request.getParameter("idEvento")!=null ? request.getParameter("idEvento") : request.getParameter("idAudiencia")%>';
	var nombreFuncionario = "<%= funcionario.getNombreFuncionario()%>";
	var apaterFuncionario = "<%= funcionario.getApellidoPaternoFuncionario()%>";
	var amaterFuncionario = "<%= funcionario.getApellidoMaternoFuncionario()%>";
	var numExpIdGlobal=0;
	var idWindowIngresarDenunciante = 1;
	var idWindowIngresarVictima = 1;
	var idWindowIngresarProbResponsable = 1;
	var idWindowIngresarTestigo = 1;
	var idWindowIngresarTraductor = 1;
	var idWindowIngresarQuienDetuvo = 1;
	var idTipoSolicitud=0;
	var salirPantalla = false;
	var documentoParcialGuardado="";
	var confActividadId='<%= ConfActividadDocumento.GENERAR_EXHORTO_JUEZ_PJ.getValorId() %>';
	var actividadId='<%= Actividades.GENERAR_EXHORTO_PJ.getValorId() %>';
	
	var actividad=0;
	var formaID=4;
	var titulo;
	var usaeditor;
	var estatusId;
	var documentoIdParcial='<%=request.getParameter("documentoId")%>';
	var accionConsultarPdf="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do";

	var recuperaTexto;
	var funcionarioNombre = nombreFuncionario + ' ' + apaterFuncionario +  ' ' + amaterFuncionario;
	
		jQuery().ready(function () {
			
			
			$('#guardarNarraTiva').hide();
			cargaFechaCaptura();
			cargaHoraCaptura();
			$('#imprimirNarraTiva').click(confActividadGD);
			$('#guardadoParcialNarrativa').click(confActividadGP);
			$('#vistaPreliminar').click(elaborarVistaPreliminar);
			
			
			cargarDocumento();
			cargarDatosExpediente();
				
			$('#seleccionarDestinatario').hide();
			$('#menuSeleccionarDestinatario').click($('#seleccionarDestinatario').show());
			$('#tblUsuarioExterno').hide();
			$('#tblUsuarioSistema').hide();
			$('#divGridUsuariosExt').hide();
			$('#divGridUsuarios').hide();

		   
		    idTipoSolicitud=<%= request.getParameter("idTipoSolicitud")%>;
			numExpIdGlobal=numeroExpedienteId;
			
			var success='<%= request.getParameter("success")%>';
	    	if(success != null){
	    		if(parseInt(success) == 1)	
	    			window.parent.cierraVentanaNotificacionAuditoria();
	    	}
	    	
// 	    	cargaDatosEncabezado();
	    	consultarTipoTamanioPapel();
	    	cargaTiposDiligencias();
	    	
			$("#dfechaDiligencia").datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '1900:2100',
				changeMonth: true,
				changeYear: true,
				showOn: "button",
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true			
			});
			
			$("#dfechaDateEnvio").datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '1900:2100',
				changeMonth: true,
				changeYear: true,
				showOn: "button",
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true			
			});
			
			
			$("#fechaVencidaPJ").datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '1900:2100',
				changeMonth: true,
				changeYear: true,
				showOn: "button",
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true			
			});
			
	    	if(idExhorto != 'false'){
	    		cargarCampos();
	    	}else{
	    		$("#funcionarioPJ").val(funcionarioNombre);
	    		
	    	}
			
			
		});
		
		
		/*
		*Funcion que realiza la carga del combo de tipos de aeronave
		*/
		function cargaTiposDiligencias() {
			  
			$.ajax({
				async: false,
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultarTiposDiligencias.do',
				data: '',
				dataType: 'xml',
				success: function(xml){
					$(xml).find('catEstatusExhorto').each(function(){
						$('#cbxEstatusExhorto').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}
	
		function cargarCampos(){
				$.ajax({
			    	type: 'POST',
			    	url: '<%=request.getContextPath()%>/consultaExhortoPJ.do?idExhorto='+idExhorto,
			    	dataType: 'xml',
			    	success: function(xml){
							$("#exhortoIdPJ").val($(xml).find('exhorto').find('exhortoId').first().text());
							$("#funcionarioPJ").val($(xml).find('exhorto').find('funcionario').find('nombreFuncionario').first().text()+' ' +$(xml).find('exhorto').find('funcionario').find('apellidoPaternoFuncionario').first().text()+' ' +$(xml).find('exhorto').find('funcionario').find('apellidoMaternoFuncionario').first().text());
							$("#cbxEstatusExhorto").val($(xml).find('exhorto').find('valorEstatus').find('idCampo').first().text());
							$("#folioPJ").val($(xml).find('exhorto').find('folio').first().text());
							$("#fechaVencidaPJ").val($(xml).find('exhorto').find('fechaVencidaStr').first().text());
							$("#dfechaDiligencia").val($(xml).find('exhorto').find('fechaDiligenciaStr').first().text());
							$("#dfechaDateEnvio").val($(xml).find('exhorto').find('fechaEnvioStr').first().text());
							$("#diligenciaPJ").val($(xml).find('exhorto').find('diligencia').first().text());
			    	},
			    	error: function(xml){
			    	}
				});
		

		}
		
		function confActividadGP(){
			
			if(validaDatos()){
				$.ajax({
					type: 'POST',
					url: '<%= request.getContextPath()%>/obtenerConfActividadDocumento.do?idConf='+confActividadId,
					data: '',
					dataType: 'xml',
					async: false,
					success: function(xml){
						actividad=$(xml).find('confActividadDocumentoDTO').find('tipoActividadId').text();
						formaID=$(xml).find('confActividadDocumentoDTO').find('formaId').text();
						titulo=$(xml).find('confActividadDocumentoDTO').find('nombreDocumento').text();
						usaeditor=$(xml).find('confActividadDocumentoDTO').find('usaEditor').text();
						estatusId=$(xml).find('confActividadDocumentoDTO').find('estadoCambioExpediente').find('idCampo').text();
						
						guardadoParcial(actividad, formaID);
					}
				});	
			}
			


		}
		
		function confActividadGD(){
			
			if(documentoIdParcial==null || documentoIdParcial=="null" || documentoIdParcial==""){
				customAlert("Primero debe de <strong>Guardar Parcialmente</strong> el documento.");
				return false;
			}else{
				if(validaDatos()){
					$.ajax({
						type: 'POST',
						url: '<%= request.getContextPath()%>/obtenerConfActividadDocumento.do?idConf='+confActividadId,
						data: '',
						dataType: 'xml',
						async: false,
						success: function(xml){
							actividad=$(xml).find('confActividadDocumentoDTO').find('tipoActividadId').text();
							formaID=$(xml).find('confActividadDocumentoDTO').find('formaId').text();
							titulo=$(xml).find('confActividadDocumentoDTO').find('nombreDocumento').text();
							usaeditor=$(xml).find('confActividadDocumentoDTO').find('usaEditor').text();
							estatusId=$(xml).find('confActividadDocumentoDTO').find('estadoCambioExpediente').find('idCampo').text();
							guardarDefinitivamente(formaID, actividad);
						}
					});
				}
			}
			

		}
		
		
		function guardadoParcial(actividad, formaID){
			

			recuperaTexto=$('.jquery_ckeditor').val();
			var nuevaActividad= actividad;
			var numeroOficio=$('#iNumeroOficio').val();
			documentoIdParcial='<%=request.getParameter("documentoId")%>';
			if(documentoIdParcial=="" || documentoIdParcial==null || documentoIdParcial=="null"){
				documentoIdParcial=documentoParcialGuardado;
			}
			if(documentoIdParcial==null || documentoIdParcial=="null" || documentoIdParcial==""){
				documentoIdParcial=="";
			}
			
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/GenerarDocumento.do',
		    	data: 'parcial=true&formaId=<%=request.getParameter("formaId") %>&numeroUnicoExpediente='+numeroUnicoExpediente+
		    		'&documentoId='+documentoIdParcial+'&texto='+escape(recuperaTexto)+
		    		'&iNumeroOficio='+documentoIdParcial+
		    		'&nuevaActividad='+nuevaActividad+
		    		'&seleccionTamanioPapel='+seleccionTamPapel+
		    		'&audienciaId='+idAudiencia,
		    	dataType: 'xml',
		    	success: function(xml){
		    		var valorNumDocParcial = $(xml).find('long').first().text();
	    			if(valorNumDocParcial == null || valorNumDocParcial == "" || valorNumDocParcial == "null"){
	    				valorNumDocParcial=$(xml).find('response').find('body').find('horaActual').text();
	    			}
	    			if(valorNumDocParcial == null || valorNumDocParcial == "" || valorNumDocParcial == "null"){
	    				valorNumDocParcial=$(xml).find('response').find('body').find('fechaActual').text();
	    			}	    			
	    			if(valorNumDocParcial == ""){
	    				valorNumDocParcial = $(xml).find('response').find('body').find('string').text();
	    			}
	    			
	    		 	var idsDocParcial = valorNumDocParcial.split(',');
	    			documentoParcialGuardado=idsDocParcial[0];
	    			
					if(documentoIdParcial==null || documentoIdParcial=="null" || documentoIdParcial==""){
		    		   guardarExhorto(documentoParcialGuardado);
					}else{
						guardarExhorto(documentoIdParcial);
					}
					documentoIdParcial = documentoParcialGuardado;

					alertDinamico("Guardado parcial exitoso");

		    	},
		    	error: function(xml){
		    	}
			});

		}

		function guardarExhorto(documento){

			var estatusEx = $( "#cbxEstatusExhorto" ).val();
			var folioEx = $("#folioPJ").val();
			var fechaVencidaEx = $("#fechaVencidaPJ").val();
			var fechaDiligenciaEX = $("#dfechaDiligencia").val();
			var fechaEnvioEx = 	$("#dfechaDateEnvio").val();
			var diligenciaPJ = $("#diligenciaPJ").val();

			var parametros = 'estatusEx='+estatusEx+'&documentoId='+documento+'&folioEx='+folioEx+'&fechaVencidaEx='+fechaVencidaEx+'&fechaDiligenciaEX='+
			fechaDiligenciaEX+'&fechaEnvioEx='+fechaEnvioEx+'&diligenciaPJ='+diligenciaPJ+'&numeroExpedienteId='+numeroUnicoExpediente;
			if(idExhorto != 'false'){
				parametros += '&exhortoId='+idExhorto;
	    	}

			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/guardarExhortoPJ.do',
		    	data: parametros,
		    	dataType: 'xml',
		    	success: function(xml){
		    		
		    	}
			});
		}
		
		/**
		*
		*/
		function guardarDefinitivamente(formaId, nuevaActividad){
			
			if(documentoIdParcial=="" || documentoIdParcial==null || documentoIdParcial=="null"){
				documentoIdParcial=documentoParcialGuardado;
			}
			if(documentoIdParcial==null || documentoIdParcial=="null" || documentoIdParcial==""){
				documentoIdParcial=="";
			}
			
			console.info("NEW ** GUARDADO DEFINITIVO DE FORMA TRANSACCIONAL **"); 
			var documento = '<%=request.getParameter("documentoId")%>';
			recuperaTexto=$('.jquery_ckeditor').val();
			
			var par = 'idNumeroExpediente=' + numeroUnicoExpediente;
			par += '&actuacion=' + actividadId;
			par += '&cveFuncionarioAsignado=' + claveFuncionario;
			par += '&seleccionTamanioPapel=' + seleccionTamPapel;
			par += '&formaId=' + formaId;
			par += '&documentoId=' + documento;
			par += '&nuevaActividad=' + nuevaActividad;
			par += '&numeroUnicoExpediente=' + numeroUnicoExpediente;
			par += '&texto=' + escape(recuperaTexto);
			par += '&audienciaId=' + idAudiencia;
			par += '&expedienteId=' + numeroUnicoExpediente;
			par += '&confActividadId=' + confActividadId;
			
			$.ajax({
				type: 'POST',	
			    url: '<%=request.getContextPath()%>/guardarDocumentoDefinitivamente.do',
			    data: par,
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
			    	var idDocumentoDef = parseInt($(xml).find('documentoDTO').find('documentoId').text());
					if(documentoIdParcial==null || documentoIdParcial=="null" || documentoIdParcial==""){
			    		   guardarExhorto(idDocumentoDef);
						}
					
			    	var msj = "";
			    	if (idDocumentoDef > 0){
			    		window.parent.recargarVisor(numeroUnicoExpediente);
			    		consultaPDF(idDocumentoDef);
// 			    		customAlert(msj,"",cerrarCustomVentana);
			    	}else {
						customAlert('Error al intentar guardar la solicitud, int&eacute;ntelo mas tarde');
					}

				}
			});
		}
		
		function consultaPDF(id){
			document.frmConsultaDoc.action=accionConsultarPdf+"?documentoId="+id;
			document.frmConsultaDoc.submit();

		}
		
		function validaDatos(){
			
			var mensaje = "";

			if($("#cbxEstatusExhorto").val() == -1){
				mensaje += "<br />- Estatus";
			}
			if($.trim($("#folioPJ").val()).length==0){
				mensaje += "<br />- Folio";
			}
			if($.trim($("#fechaVencidaPJ").val()).length==0){
				mensaje += "<br />- Fecha vencida";
			}
			if($.trim($("#dfechaDiligencia").val()).length==0){
				mensaje += "<br />- Fecha diligencia";
			}
			if($.trim($("#dfechaDateEnvio").val()).length==0){
				mensaje += "<br />- Fecha de envio";
			}
			if($.trim($("#diligenciaPJ").val()).length==0){
				mensaje += "<br />- Diligencia";
			}			

			if(mensaje != ""){
				alertDinamico("<p align='left'>Favor de revisar los siguientes campos obligatorios: <br />"+mensaje+"</p>");
				return false;
			}
			return true;
			
		}
		
		
		function respuestaAlEnviar(msg){
			alertDinamicoCerrar(msg);			
		}
		
		
		
		function cargarDocumento(){
			var mandaFormaEnConsulta="si";
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/CargarDocumento.do?idAudiencia='+idAudiencia+'&idResolutivo='+idResolutivo+'',
		    	data: 'formaId=4&numeroUnicoExpediente='+numeroUnicoExpediente+'&mandaFormaEnConsulta='+mandaFormaEnConsulta+
		    	'&documentoId=<%=request.getParameter("documentoId")!=null?request.getParameter("documentoId"):""%>',
		    	dataType: 'xml',
		    	success: function(xml){
		    		$('.jquery_ckeditor').val( $(xml).find('<%=ConstantesGenerales.CUERPO_DOCUMENTO_TAG_BUSQUEDA%>').text());
		    		$("#iNumeroOficio").val( $(xml).find('<%=ConstantesGenerales.NUMERO_FOLIO_TAG_BUSQUEDA%>').text());
		    	}
			});
		}


		
		function cargarDatosExpediente(){
			
			if(!esconderArbol){
				$.ajax({
			    	type: 'POST',
			    	url: '<%=request.getContextPath()%>/CargarArbolExpedienteEnDocumento.do',
			    	data: 'numeroUnicoExpediente='+numeroUnicoExpediente,
			    	dataType: 'xml',
			    	success: function(xml){
			    		//Obtener Parametros de Configuracion
			    		var probableResponsableArbolTituloSeccion = '<bean:message key="probableResponsableArbolTituloSeccion"/>';
			    		var probableResponsableArbolTitulo = '<bean:message key="probableResponsableArbolTitulo"/>';
			    		
			    		contenido = escribirDatosGenerales(xml);			
			    		contenido += escribirInvolucrados(xml,probableResponsableArbolTituloSeccion, probableResponsableArbolTitulo);
			    		contenido += escribirUnidadesEspecializadas(xml);
			    		contenido += escribirListaObjetos(xml);
			    		contenido += escribirDelitos(xml);
			    		//contenido += escribirHechos(xml);
			    		
			    		$("#accordionDatosExpediente").append(contenido);
			    		$("#accordionDatosExpediente").treeview();
			    	}
				});
			}else{
				$("#marcoArbolExpediente").css('display','none');
				$("#idExpedientes").css('display','none');
				$("#tdArbolExp").css('width','1px');
			}
		}

		function refresca(){
			$("#accordionDatosExpediente").empty();
			cargarDatosExpediente();
		}
		

		
		//Funci&oacute;n para alertDinamicoCerrar
		function alertDinamicoCerrar(textoAlert){						
			$("#divAlertTextoCerrar").html(textoAlert);
		    $( "#dialog-AlertCerrar" ).dialog({
				autoOpen: true,
				resizable: false,
				modal: true,
				buttons: {				
					
					"Aceptar": function() {						
						window.parent.cierraVentanaNotificacionAuditoria();
						$( this ).dialog( "close" );
						$("#divAlertTextoCerrar").html("");					
					}				
				}
			});    
		 }
		
		
		/*
		*Funcion que dispara el Action para consultar catalogos
		*/
		function cargaCatalogos() {
	
			$('#idDelitosCaso').empty();
		    $.ajax({
				type: 'POST',
		   	  	url: '<%=request.getContextPath()%>/consultaCatalogosCaso.do',
		   	  	data: '',
		   	 	dataType: 'xml',
		   	  	success: function(xml){
					$('#idDelitosCaso').empty();
					$('#idDelitosCaso').append('<option value="-1">- Seleccione -</option>');
					$(xml).find('catCatalogo').each(function(){
						$('#idDelitosCaso').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				   	});
		   	  	}
		   	});
		}
	
		
	   /*
		*Funcion que dispara el Action para consultar catalogos
		*/
		function cargaCatalogos2() {
	
			$('#idFormasParticipacion').empty();
			$.ajax({
				type: 'POST',
		    	url: '<%=request.getContextPath()%>/consultaCatalogosCaso.do',
		    	data: '',
		    	dataType: 'xml',
		    	success: function(xml){
					$('#idFormasParticipacionv').empty();
			    	$('#idFormasParticipacion').append('<option value="-1">- Seleccione -</option>');
		    		$(xml).find('catCatalogo').each(function(){
						$('#idFormasParticipacion').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}
	
	   
	   /*
		*Funcion que carga la fecha actual del sistema y la agrega en la pantalla al campo fecha
		*/
		function cargaFechaCaptura(){
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/ConsultarFechaCaptura.do',
		    	data: '',
		    	dataType: 'xml',
		    	success: function(xml){
		    		$('#generarDocumentoCmpFechaIngreso').val($(xml).find('fechaActual').text());
		    	}
			});
		}
	
	   
	   /*
		*Funcion que carga la hora actual del sistema y la agrega en la pantalla al campo hora
		*/
		function cargaHoraCaptura(){
	    	$.ajax({
	    		type: 'POST',
	    	    url: '<%=request.getContextPath()%>/ConsultarHoraCaptura.do',
	    	    data: '',
	    	    dataType: 'xml',
	    	    success: function(xml){
	    			$('#idHoraDate').val($(xml).find('horaActual').text());
	    		}
			});
	    }

		function imprime(){
			
			var texto=$('.jquery_ckeditor').val();
			
			$.ajax({
				async: false,
	    		type: 'POST',
	    	    url: '<%=request.getContextPath()%>/GenerarDocumento.do',
	    	    data: 'texto='+$('.jquery_ckeditor').val(),
	    	    dataType: 'xml',
	    	    success: function(xml){
	    		}
			});
		}
		
		</script>
</head>



<body>
<!-- div para el alert dinamico antes de cerrar ventana -->
<div id="dialog-AlertCerrar" style="display: none">
	<table align="center">
		<tr>
        	<td align="center">
            	<span id="divAlertTextoCerrar"></span>
            </td>
        </tr>
     </table>              
</div>    

	<!-- ETIQUETAS NECESARIAS PARA LOS CAMPOS DEL ENCABEZADO -->
	<table align="center" border="0" width="820px" height="50%">
		<tr><!-- MENU -->
			<td colspan="4">
				<ul class="toolbar ui-widget-header">
					<div id="menu_head">
                		<!-- <li id="menuSeleccionarDestinatario"  onclick="muestraPopupSeleccionarDestinatario()">Seleccionar Destinatario</li> -->
                        <!-- <li id="agregarElemento">Agregar Elemento</li> -->
                        <!-- <li id="relacionarElementos">Relacionar Elementos</li> -->
                        <!-- <li id="consultarDocumentosDelExpediente">Consultar Documentos del Expediente</li> -->
						<li id="guardadoParcialNarrativa" class="first">Guardado Parcial</li>
						<li id="imprimirNarraTiva"><span></span>Guardado Definitivo</li>					
						<li id="vistaPreliminar"><span></span>Vista Preliminar</li>
						<li id="seccionCbxTamanioPapel"><span></span>Tama&ntilde;o de Papel
							<select name="cbxTamanioPapel" id="cbxTamanioPapel" onchange="recuperarTamanioPapel()" style=" border:0; background-color:#EEEEEE;">
		    				</select>
		    			</li>
						<!-- <li id="tbarBtnConsultarTurnoAtencion" class="first">Salir</li> -->
					</div>
				</ul>
			</td>
	  	</tr>
		<tr>
			<td width="20%">Folio:</td>
			<td width=""><input type="text" title="Folio" size="40" id="folioPJ" onKeyPress="return solonumeros(event);"/></td>
			<td width="">Juzgado de Origen:</td>
			<td width=""><input type="text" title="Juzgado de Origen" size="40" id="funcionarioPJ" disabled="disabled"/></td>
		</tr>
		<tr>
			<td width="20%">Fecha Vencido:</td>
			<td width=""><input type="text" title="Fecha Vencido" id="fechaVencidaPJ" disabled="disabled" size="15" maxlength="10"></td>
			<td width="">Diligencia:</td>
			<td width=""> <input type="text" id="diligenciaPJ" size="40" onKeyPress="return teclasAlfanumericas(event);"/></td>
		</tr>
		<tr>
			<td>Fecha Diligencia:</td>
			<td width=""> <input type="text" id="dfechaDiligencia"  disabled="disabled" size="15" maxlength="10"></td>
			<td>Estatus</td>			
			<td>
			<select id="cbxEstatusExhorto" style="width:180px">
                  <option value="-1">-Seleccione-</option>
                </select>
			</td>
		</tr>
		<tr>
			<td width="20%">Fecha Envio:</td>
			<td width=""> <input type="text" id="dfechaDateEnvio"  disabled="disabled" size="15" maxlength="10"></td>
		</tr>
	</table>
		
	<!-- ETIQUETAS PARA LA SECCION DE LOS ELEMENTOS DEL EXPEDIENTE -->	
	<table align="center" width="1024px" border="0">
		<tr>			
			<td width="800px" valign="top" align="center">
				<div id="divGridUsuarios">				
					<table align="center" id="gridUsuarios" width="800px"></table>
					<div id="pager1"></div>				
					<br>
				</div>
				<div id="divGridUsuariosExt">
					<table align="center" id="gridUsuariosExt" width="800px"></table>
					<div id="pager2"></div>
				</div>
				<div style="margin-top: 0; margin-bottom: auto; vertical-align: top;margin-right: auto; margin-left: auto">
					<br>	
							
					<jsp:include page="/WEB-INF/paginas/ingresarNarrativaView.jsp" flush="true"></jsp:include>
				
				</div>
				
			<form name="frmConsultaDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
				<!--<input type="hidden" name="documentoId" />-->
			</form>
			
				
	<!--  Se ingreso porque es utilizado por la funcion de "elaborarVistaPreliminar"  -->
	<form name="frmDoc" action="<%= request.getContextPath() %>/GenerarDocumento.do" method="post">
		<input type="hidden" name="texto" value=""/>
		<input type="hidden" name="parcial" value=""/>
		<input type="hidden" name="formaId" value="<%=request.getParameter("formaId")!=null?request.getParameter("formaId"):"" %>"/>
		<input type="hidden" name="numeroUnicoExpediente" value="<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):"" %>"/>
		<input type="hidden" name="documentoId" value="<%=request.getParameter("documentoId")!=null?request.getParameter("documentoId"):"" %>"/>
		<input type="hidden" name="tipoOperacion" value="<%=request.getParameter("tipoOperacion")!=null?request.getParameter("tipoOperacion"):"" %>"/>
		<input type="hidden" name="estatusSolicitud" value="<%=request.getParameter("estatusSolicitud")!=null?request.getParameter("estatusSolicitud"):"" %>"/>
		<input type="hidden" name="idResolutivo" value="<%=request.getParameter("idResolutivo")!=null?request.getParameter("idResolutivo"):"" %>"/>
		<input type="hidden" name="seleccionTamanioPapel" value="<%=request.getParameter("seleccionTamanioPapel")!=null?request.getParameter("seleccionTamanioPapel"):"" %>"/>
	</form>	
								
			</td>
		</tr>
	</table>
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
</body>

</html>