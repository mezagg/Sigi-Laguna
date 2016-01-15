<%@page import="mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades" %>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.medida.TipoMedida"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TipoMandamiento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Ingresar Mandamiento Judicial</title>

		<link rel="stylesheet" type="text/css"  href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen"/>
		<link rel="stylesheet" type="text/css"  href="<%= request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css"/>
		<link rel="stylesheet" type="text/css"  media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css"/>
		<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen"/>
		<link type="text/css"  rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>			
					
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
				
		<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
		<script	src="<%=request.getContextPath()%>/resources/js/validate/jquery.maskedinput.js"></script>
		<script src="<%=request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
		<script src="<%=request.getContextPath()%>/resources/js/validate/mktSignup.js"></script>
		<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/funcionesComunMandJudYMedCautelares.js"></script>
		
		
		<%
		String rolActivo = "";
		String jerarquia = "";
		UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO"); 
		if (usuario != null 
				&& usuario.getRolACtivo() != null 
				&& usuario.getRolACtivo().getRol() != null
				&& usuario.getRolACtivo().getRol().getRolId() != null){
			rolActivo = usuario.getRolACtivo().getRol().getRolId().toString();
			if (usuario.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO() != null
					&& usuario.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId() > 0L){
				jerarquia = usuario.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId().toString();
			}
		}
		%>	

		<script type="text/javascript">

		var idInvolucrado             = "";
		
		//idResolutivo
		var rowid =			   '<%=request.getParameter("rowid")%>';
		var resolutivoId =	   '<%=request.getParameter("resolutivoId")%>';
		var idVentana =		   '<%=request.getParameter("idVentana")%>';
		var operacion =		   '<%=request.getParameter("operacion")%>';
		var numexpediente =  '<%=request.getParameter("numeroExpediente")%>';
		var numeroDeCaso =  '<%=request.getParameter("numeroDeCaso")%>';
		var numexpedienteid =  '<%=request.getParameter("numeroExpedienteId")%>';
		var rolActivo = '<%=rolActivo%>';
		var jerarquia = '<%=jerarquia%>';
		var idsRelacionDP = '<%=request.getParameter("idsDelitoPersona")%>';
		var idSolicitudMandamiento = <%=request.getParameter("idSolicitudMandamiento") != null ? request.getParameter("idSolicitudMandamiento") : "0"%>;
		
		var contextoPagina = "${pageContext.request.contextPath}";
		
		//Nuevos atributos desde el padre
		var idAudiencia = <%=request.getParameter("idAudiencia")%>;
		var idInvolucradoSeleccionado = '<%=request.getParameter("idInvolucrado")%>';
		//Id estatus mandamiento judicial
		var estatusActualMandamiento = "";

		var mandamientoJudicialId = "";
		var idWindowPantallaActuaciones = 1;
		var banderaPV=0;
		
		jQuery().ready(function () {
			
			$( "#tabsprincipalconsulta" ).tabs();
						
			//Codigo para obtener los datos de la pantalla
			$("#guardarMandamientoJudicial").click(guardarMandamientoJudicial);

			$('#iMandamientoJudicialWorkSheet').show();
			
			$("#mandamientoJudicialFechaEjecucion").attr("disabled","disabled");

			
			cargaTipoMandamiento();
			cargaTipoSentencia();
			controlTipoMandamiento();
			$('#tipoMandamiento').change(controlTipoMandamiento);
			agregarCalendarios();
			$("#mandamientoJudicialFechaEjecucion").datepicker('disable');
			
			if (rolActivo == '<%=Roles.ENCARGADOCAUSA.getValorId()%>'){
				$("#rowNumeroCausa").hide();
				$("#rowFechaInicioMandamientoJudicial").hide();
			}
			
			if(operacion=='CONSULTA'){
				$('#iMandamientoJudicialWorkSheet').hide();
				obtenerDatosMandamientoJudicial(rowid);
				
				consultarDocumentosDeMandamientoJudicialPorExpediente();
				$("#cbxTipoConsultaDocumentos").change(consultarDocumentosDeMandamientoJudicialPorExpediente);
				
				if (rolActivo == '<%=Roles.ENCARGADOCAUSA.getValorId()%>'
						|| rolActivo == '<%=Roles.DIRECTOR_APREHENSIONES.getValorId()%>'){
					//Muestra seccion para el cambio de estatus
					if(estatusActualMandamiento == '<%=EstatusMandamiento.NO_ATENDIDO.getValorId()%>' || estatusActualMandamiento == '<%=EstatusMandamiento.EN_PROCESO.getValorId()%>'
							|| estatusActualMandamiento == '<%=EstatusMandamiento.CANCELADO.getValorId()%>'){
						$("#seccionCambiarEstatusMandamiento").show();
					}
					//Muestra seccion para adjuntar documentos
					if(estatusActualMandamiento == '<%=EstatusMandamiento.ATENDIDO.getValorId()%>' || estatusActualMandamiento == '<%=EstatusMandamiento.CANCELADO.getValorId()%>'
							|| estatusActualMandamiento == '<%=EstatusMandamiento.CONCLUIDO.getValorId()%>'){
						$("#seccionAdjuntarDocumentoAMandamiento").show();
					}
				}else{//Ajustes necesarios al momento de consultar el Rol de agenteMP					
				    $("#cbxTipoConsultaDocumentos").find("option[value='<%=TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()%>']").remove();
					$("#idEtiquetaNumCausa").html("N&uacute;mero de Expediente:");
				}
				
			}else if(operacion=='REEDICION'){
				
				obtenerDatosMandamientoJudicial(rowid);
				$('#cbxTipoConsultaDocumentos').attr('disabled',true);
				consultaNombreInvolucrado(idInvolucradoSeleccionado)
				consultarDocumentosDeMandamientoJudicialPorExpediente();
				$('#iMandamientoJudicialWorkSheet').show();
			
			}else{//INGRESAR MANDAMIENTO
				consultaNombreInvolucrado(idInvolucradoSeleccionado);
				$("#cmpEstado").val("No atendido");
				$('#txtNumCausa').val(numexpediente);
				if(numeroDeCaso != "")
					$('#txtNumCaso').val(numeroDeCaso);
				else
					$('#txtNumCaso').val("-");

				consultarDocumentosDeMandamientoJudicialPorExpediente();
				$('#cbxTipoConsultaDocumentos').attr('disabled',true);
				$("#mandamientoJudicialFechaEjecucion").datepicker('enable');
			}
						
			
			//Permite cargar el grid con las medidas cautelares relacionadas a un expediente
			consultaGeneralMandamientoJudicial(4, '', numexpediente);
			//Esta funcion se debe llamar despues de consultar el detalle
			consultarCatalogoEstatusMandamiento();
			
			if (rolActivo == '<%=Roles.AGENTEMP.getValorId()%>'
					|| rolActivo == '<%=Roles.DIRECTOR_APREHENSIONES.getValorId()%>'){
				jQuery("#gridMandamientosJudiciales").jqGrid('setLabel', 'numeroCausa', 'N&uacute;mero de Expediente');				
			}
			
			if (rolActivo != '<%=Roles.DIRECTOR_APREHENSIONES.getValorId()%>'){
				jQuery("#tabActuacionesMandamientos").hide();
			}else{
				cargaActuaciones();
			}
			
			if (rolActivo == '<%=Roles.POLICIAMINISTER.getValorId()%>'){
				jQuery("#tabDocumentos").hide();
				jQuery("#tabMandamientosExpediente").hide();
				jQuery("#btnGuardar").button().click(
						function() {
							seleccionaActuacion(<%=ConfActividadDocumento.REPORTE_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId()%>);
							jQuery("#btnGuardar").button( "option", "disabled", true );
						}
				);
				consultaSolicitud(idSolicitudMandamiento);
			}else{
				jQuery("#tabDetalleSolicitud").hide();
			}
			
			consultaDelitos();
		});

		function obtenerDatosMandamientoJudicial(rowid){
			
			mandamientoJudicialId = rowid.split(",")[1];
									
			$.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/consultaMandamientoJudicialPJENC.do',
	    		data: 'idMandamientoJudicial='+mandamientoJudicialId,
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){
	    			deshabilitaCampos();
	    			idInvolucrado=$(xml).find('mandamientoJudicial').find('involucrado').find('elementoId').text();
	    			
	    			var idEstatusExpediente = $(xml).find('mandamientoJudicial').find('resolutivo').find('audiencia').find('expediente').find('estatus').find('idCampo').first().text();
	    			var estadoMedida = "";
				
					var nombre=$(xml).find('mandamientoJudicial').find('involucrado').find('nombresDemograficoDTO').find('nombre').first().text();
					document.getElementById('iMCNombre').value=nombre;
					var apellidoPaterno=$(xml).find('mandamientoJudicial').find('involucrado').find('nombresDemograficoDTO').find('apellidoPaterno').first().text();
					document.getElementById('iMCApellidoPaterno').value=apellidoPaterno;
					var apellidoMaterno=$(xml).find('mandamientoJudicial').find('involucrado').find('nombresDemograficoDTO').find('apellidoMaterno').first().text();
					document.getElementById('iMCApellidoMaterno').value=apellidoMaterno;
					
					$(xml).find('mandamientoJudicial').find('estatus').each(function(){
						if ($(this).find('idCampo').text() == "<%=EstatusMandamiento.ATENDIDO.getValorId()%>"
								|| $(this).find('idCampo').text() == "<%=EstatusMandamiento.CANCELADO.getValorId()%>"
								|| $(this).find('idCampo').text() == "<%=EstatusMandamiento.CONCLUIDO.getValorId()%>"
								|| $(this).find('idCampo').text() == "<%=EstatusMandamiento.EJECUTADO.getValorId()%>"
								|| $(this).find('idCampo').text() == "<%=EstatusMandamiento.EN_PROCESO.getValorId()%>"
								|| $(this).find('idCampo').text() == "<%=EstatusMandamiento.NO_ATENDIDO.getValorId()%>"){
							estadoMedida = $(this).find('valor').text();
							estatusActualMandamiento = $(this).find('idCampo').text();
						}
					});

					$('#cmpEstado').val(estadoMedida);
										
					var descripcion = $(xml).find('mandamientoJudicial').find('descripcion').text();
					$('#descripcionMandamiento').val(descripcion);
					
					var tipoMandamiento = $(xml).find('mandamientoJudicial').find('tipoMandamiento').find('idCampo').text()
					
					if(tipoMandamiento != ''){
						$("#tipoMandamiento").val(tipoMandamiento);
					}
					
					var tipoSentencia = $(xml).find('mandamientoJudicial').find('tipoSentencia').find('idCampo').text()
					
					if(tipoSentencia != ''){

						$("#tipoSentencia").val(tipoSentencia);
						$('#divEtiTipoSentencia').show();
						$('#divCbxTipoSentencia').show();
						$('#divEtiFechaInicioSentencia').show();
						$('#divFechaInicioSentencia').show();
						$('#divEtiFechaFinSentencia').show();
						$('#divFechaFinSentencia').show();
						

						var fechaInicio=$(xml).find('mandamientoJudicial').find('fechaInicioStr').text();
						if(fechaInicio != '' && fechaInicio != null){
							$("#fechaInicioSentencia").val(fechaInicio);
						}
						
						var fechaFin=$(xml).find('mandamientoJudicial').find('fechaFinStr').text();
						if(fechaFin != '' && fechaFin != null){
							$("#fechaFinSentencia").val(fechaFin);
						}
					}

					var fechaEjecucion=$(xml).find('mandamientoJudicial').find('fechaEjecucionStr').text();
					if(fechaEjecucion != '' && fechaEjecucion != null){
						$("#mandamientoJudicialFechaEjecucion").val(fechaEjecucion);
					}
					
					var numCausa = $(xml).find('mandamientoJudicial').find('numeroExpediente').first().text();
					$('#txtNumCausa').val(numCausa);
					numexpediente = numCausa; 

					numexpedienteid = $(xml).find('mandamientoJudicial').find('numeroExpedienteId').first().text();
					
					var numCaso = $(xml).find('mandamientoJudicial').find('numeroGeneralCaso').first().text();
					$('#txtNumCaso').val(numCaso);
					
					if(numCaso != "")
						$('#txtNumCaso').val(numCaso);
					else
						$('#txtNumCaso').val("-");
					
					numeroDeCaso = numCaso;
					
					var numCausaPJ = $(xml).find('mandamientoJudicial').find('numeroCausa').first().text();
					$('#txtNumeroCausaPJ').val(numCausaPJ);
					
					var fechaInicioMandamiento = $(xml).find('mandamientoJudicial').find('strFechaCreacion').first().text();
					$('#mandamientoJudicialFechaInicio').val(fechaInicioMandamiento);
					
					$('#iMandamientoJudicialWorkSheet').hide();
	    		}	
	    	});
		}
		


		/*
		*Funcion para guardar la medida cautelar
		*/
		function guardarMandamientoJudicial(){

			if(validaParametrosDeGuardado() == true){
	
				var params = '';
				params += recuperaDatosMandamientoJudicial();
				//TODO: Cambiar el tipo de forma al momento de guardar el documento
				var formaId = '<%=Formas.MEDIDA_CAUTELAR.getValorId()%>'; 
				var numeroUnicoExpediente = numexpediente;
				
				$.ajax({								
			    	  type: 'POST',
			    	  url: '<%= request.getContextPath()%>/crearMandamientoJudicial.do?idInvolucrado='+idInvolucradoSeleccionado+'',
			    	  data: params,				
			    	  dataType: 'xml',
			    	  async: false,
			    	  success: function(xml){
			    		  	//Si no ten&iacute;a mandamiento
			    		  	var idArchivoMandamiento = $(xml).find("mandamientoDTO").find("archivoDigital").first().find("archivoDigitalId").first().text();
							var idArchivoImagenFunc = $(xml).find("mandamientoDTO").find("resolutivo").first().find("audiencia").find("expediente").find("responsableTramite").find("archivoDigital").find("archivoDigitalId").first().text();
			    		  
			    			if(idArchivoMandamiento != ""
			    					&& idArchivoMandamiento != idArchivoImagenFunc){
								//el documento ya est&aacute; generado, consultarlo
								document.verMandamientoForm.archivoDigitalId.value = idArchivoMandamiento;
								document.verMandamientoForm.documentoId.value = "";
								document.verMandamientoForm.submit();
							}else{
								
								mandamientoJudicialId = $(xml).find("documentoId").first().text();
								numExp = $(xml).find("numeroExpediente").first().text();
								
								//mandamiento reci&eacute;n creado, mostrar el editor
								idWindowPantallaActuaciones++;
								$.newWindow({id:"iframewindowGenerarMandamientoJudicial"+idWindowPantallaActuaciones, 
									statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Generar Mandamiento", 
									type:"iframe", confirmarCierreVentana:true});
						        $.updateWindowContent("iframewindowGenerarMandamientoJudicial"+idWindowPantallaActuaciones,
						        		"<iframe src='<%=request.getContextPath()%>/generarDocumentoSinCaso.do?documentoId="+mandamientoJudicialId+"&numeroUnicoExpediente="+numExp+"&ocultarNumeroOficio=true&idWindowPantallaActuaciones="+idWindowPantallaActuaciones+"' width='1140' height='400' />");
							}
			    			
				    		  //Despues de guardar el mandamiento judicial se oculta el boton, mosotrando el bot&oacute;n de cancelar
				    		  $("#guardarMandamientoJudicial").hide();
			    		  
			    		  
			    	 }
			    });
			}
		}
			
		/*
		*Funcion que valida que se hayan ingresado todos los campos correctamente
		*/
		function validaParametrosDeGuardado(){
			
			var validacion = false
			var tipoMandamiento = $('#tipoMandamiento option:selected').val();
			var tipoSentencia = $('#tipoSentencia option:selected').val();
			
				if( tipoMandamiento == "-1"){
					customAlert("Seleccione un tipo de mandamiento");
				}
				else if($('#mandamientoJudicialFechaEjecucion').val() == ""){
					customAlert("Seleccione una fecha de ejecuci&oacute;n");
				}
				else{
				  		if(tipoMandamiento == <%=TipoMandamiento.SENTENCIA.getValorId()%>){
				  			if( tipoSentencia == "-1"){
								customAlert("Seleccione un tipo de sentencia");
							}else{
								if(validaCamposFechaSentencia() == true)
				  					validacion = true;
							}
					  	}else{
	  						validacion = true;
					  	}
				}
		    return validacion;		    
		}
		
		
		//Funcion que valida si los campos estan llenos al enviar 
		function validaCamposFechaSentencia() {

			if ($('#fechaInicioSentencia').val() == '' || $('#fechaFinSentencia').val() == '') {
				customAlert("Debe ingresar la fecha de inicio y fin de sentencia");
				validaFecha=false;
			}else{

				var fechaIniVal = $('#fechaInicioSentencia').val();
				var fechaFinVal = $('#fechaFinSentencia').val();

				var inicio = fechaIniVal.split("/");
				var fin = fechaFinVal.split("/");

				if(fin[2]>inicio[2]){
					validaFecha=true;
				}
				else{
					if(fin[2]<inicio[2]){
						validaFecha=false;
					}
					else{
						if(fin[1]>inicio[1]){
							validaFecha=true;
						}	
						else{
							if(fin[1]<inicio[1]){
								validaFecha=false;
							}
							else{
								if(fin[0]>=inicio[0]){
									validaFecha=true;
								}
								else{
									validaFecha=false;
								}
							}
						}
					}
				}
					
				if(validaFecha==false){	
					customAlert("La fecha final debe de ser mayor &oacute; igual a la fecha inicial");
				}
				
			}
			return validaFecha;
		}



		function recuperaDatosMandamientoJudicial(){
	        var parametros = '&resolutivoId=' + resolutivoId;
	        parametros += '&tipoMandamiento=' +  $('#tipoMandamiento option:selected').val();
	        parametros += '&tipoSentencia=' +  $('#tipoSentencia option:selected').val();
	        parametros += '&idImputado=' + idInvolucradoSeleccionado;
	        parametros += '&fechaInicio=' + $('#fechaInicioSentencia').val();        
	        parametros += '&fechaFin=' + $('#fechaFinSentencia').val(); 
	        parametros += '&fechaEjecucion=' + $('#mandamientoJudicialFechaEjecucion').val(); 
	        parametros += '&descripcionMandamiento='+  $('#descripcionMandamiento').val();
	        parametros += '&numeroExpedienteId='+numexpedienteid;
	        parametros += '&idAudiencia='+idAudiencia;
	        parametros += '&idsDelitoPersona='+idsRelacionDP;
	        parametros += '&numExpediente='+numexpediente;
			return parametros;
		}

		function habilitaCampos(){
	        $('#tipoMandamiento').attr("disabled","");
	        $('#tipoMandamiento').attr("disabled","");
	        $('#mandamientoJudicialFechaEjecucion').attr("disabled","");
	        $('#tipoSentencia').attr("disabled","");        
	        $('#fechaInicioSentencia').attr("disabled","");        
	        $('#fechaFinSentencia').attr("disabled","");        
	        $('#descripcionMandamiento').attr("disabled",""); 
		}

		function deshabilitaCampos(){
	        $('#tipoMandamiento').attr("disabled","disabled");
	        $('#mandamientoJudicialFechaEjecucion').attr("disabled","disabled");        
	        $('#tipoSentencia').attr("disabled","disabled");        
	        $('#fechaInicioSentencia').attr("disabled","disabled");        
	        $('#fechaFinSentencia').attr("disabled","disabled");        
	        $('#descripcionMandamiento').attr("disabled","disabled"); 
		}
		
/*************************************************FUNCIONALIDAD PARA CONSULTAR DOCUMENTOS***************************************************************/

		//Variable para controlar la carga del grid
		var primeraVezDocumentosRelacionados = true;

		/*
		*Funcion para consultar los doocumentos del mandamiento judicial de acuerdo
		*al tipo de documento seleccionado por el usuario:
		*	- Creacion
		*	- Cambio de estatus
		*	- Adjuntos
		*/
		function consultarDocumentosDeMandamientoJudicialPorExpediente(){

			var tipoDocumento = $('#cbxTipoConsultaDocumentos option:selected').val();
			
			if(primeraVezDocumentosRelacionados == true){
				
				jQuery("#gridDocumentos").jqGrid({ 
					url:'<%=request.getContextPath()%>/consultarGridDeMandamientoJudicialPorExpedienteYTipo.do?mandamientoJudicialId='+mandamientoJudicialId+'&tipoDocumento='+tipoDocumento+'&numeroExpedienteId='+numexpedienteid+'', 
					datatype: "xml", 
					colNames:['Fecha de Elaboracion','Nombre'], 
					colModel:[ 					
					           	{name:'fechaElab',index:'2', width:150, align:'center'}, 
					           	{name:'Nombre',index:'Nombre', width:150, align:'center'}, 
							],
					pager: jQuery('#pagerGridDocumentos'),
					rowNum:10,
					autoWidth:false,
					width:700,
					rowList:[10,20,30],
					sortname: '2',
					viewrecords: true,
					sortorder: "desc",
					ondblClickRow: function(rowid) {
						consultaDocumento(rowid);
					} 
				});
				
				primeraVezDocumentosRelacionados=false;
				
			}else{				
				jQuery("#gridDocumentos").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarGridDeMandamientoJudicialPorExpedienteYTipo.do?mandamientoJudicialId='+mandamientoJudicialId+'&tipoDocumento='+tipoDocumento+'&numeroExpedienteId='+numexpedienteid+'',datatype: "xml" });
				$("#gridDocumentos").trigger("reloadGrid");
			}
			
			$("#gview_pagerGridDocumentos .ui-jqgrid-bdiv").css('height', '150px');
		}
		


		function consultaDocumento(idDocumento){
			document.frmDocumento.documentoId.value = idDocumento;
			document.frmDocumento.submit();
		}


		
		function abrirPDF(rowid){
			document.frmDoc.archivoDigitalId.value = rowid;
			document.frmDoc.submit();
		}
/***********************************************TERMINA FUNCIONALIDAD PARA CONSULTAR DOCUMENTOS***************************************************************/

	
	/************ FUNCION PARA OCULTAR-MOSTRAR LOS TABS DEL VISOR***************/
	function ocultaMuestraTabVisor(claseTab,bandera)
	{
		if(parseInt(bandera)==0)//oculta
		{
			$("."+claseTab).hide();
		}
		else///muestra
		{
			$("."+claseTab).show();
		}
	}
	
	/**
	* Funcion que realiza la carga del cat&aacute;logo de tipos de mandamiento
	*/
	function cargaTipoMandamiento() {
		
		$.ajax({
			async: true,
			type: 'POST',
			url: '<%=request.getContextPath()%>/consultarCatalogoTipoMandamiento.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				
				$(xml).find('tipoMandamiento').each(function(){
					$('#tipoMandamiento').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	/**
	* Funcion que realiza la carga del cat&aacute;logo de tipos de sentencia
	*/
	function cargaTipoSentencia() {
		
		$.ajax({
			async: true,
			type: 'POST',
			url: '<%=request.getContextPath()%>/consultarCatalogoTipoSentencia.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				
				$(xml).find('tipoSentencia').each(function(){
					$('#tipoSentencia').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}
	
	/*
	*Funcion para mostrar u ocultar el tipo de sentencia dependiendo
	*de la seleccion del usuario
	*/
	function controlTipoMandamiento(){

		var tipoMandamiento = $('#tipoMandamiento option:selected').val();
		
		if(tipoMandamiento == 0){
			limpiaDivTipoMandamiento();
		}
		else if(tipoMandamiento == <%=TipoMandamiento.SENTENCIA.getValorId()%>){
			$('#divEtiTipoSentencia').show();
			$('#divCbxTipoSentencia').show();
			$('#divEtiFechaInicioSentencia').show();
			$('#divFechaInicioSentencia').show();
			$('#divEtiFechaFinSentencia').show();
			$('#divFechaFinSentencia').show();
		}else{
			$('#divEtiTipoSentencia').hide();
			$('#divCbxTipoSentencia').hide();
			$('#divEtiFechaInicioSentencia').hide();
			$('#divFechaInicioSentencia').hide();
			$('#divEtiFechaFinSentencia').hide();
			$('#divFechaFinSentencia').hide();
			
			//limpiaDatosSentencia()
			$('#tipoSentencia').attr('selectedIndex', 0);
			$('#fechaInicioSentencia').val("");
			$('#fechaFinSentencia').val("");
		}
	}
	
	/*
	*Limpia el Div de tipos de mandamiento
	*/
	function limpiaDivTipoMandamiento(){

		$('#tipoMandamiento').attr('selectedIndex', 0);
		$('#nombreDelImputado').attr('selectedIndex', 0);
		$('#tipoSentencia').attr('selectedIndex', 0);
		$('#fechaInicioSentencia').val("");
		$('#fechaFinSentencia').val("");
		//oculta el tipo de sentencia
		$('#divEtiTipoSentencia').hide();
		$('#divCbxTipoSentencia').hide();
		$('#divEtiFechaInicioSentencia').hide();
		$('#divFechaInicioSentencia').hide();
		$('#divEtiFechaFinSentencia').hide();
		$('#divFechaFinSentencia').hide();
	}

	/*
	*Funcion que agrega el calendario a los campos fecha de inicio y fecha de fin
	*/
	function agregarCalendarios(){

		$('#mandamientoJudicialFechaEjecucion').val('');
		$('#fechaInicioSentencia').val('');
		$('#fechaFinSentencia').val('');

		$("#mandamientoJudicialFechaEjecucion").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		
		$("#fechaInicioSentencia").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
	
		$("#fechaFinSentencia").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
	}
	

	//********************************************************FUNCIONES PARA CAMBIO DE ESTATUS**********************************************************/

		/*
		 * Funcion que consulta el catalogo del estatus del mandamiento judicial
		 */
		function consultarCatalogoEstatusMandamiento(){
			
			$.ajax({
				async: true,
				type: 'POST',
				url: '<%=request.getContextPath()%>/consultarCatalogoEstatusMandamiento.do',
				data: 'estatusMandamiento=' + estatusActualMandamiento,
				dataType: 'xml',
				success: function(xml){
					$(xml).find('estatusMandamiento').each(function(){
							$('#cbxEstatusMendamientoJud').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}

		/**
		 * Funcion para invocar al editor de texto y cambiar el estatus del
		 * mandamiento judicial, al presionar el boton "guardado definitivo"
		 */
		function generarDocumentoDeCambioEstatusDeMandamientoJudicial(){
		
			if(validarNuevoEstatusMandamientoJud() == true){
		
				var titulo="Cambio de estatus de mandamiento judicial";
				
				var nuevoEstatusMandamientoJudicial = $('#cbxEstatusMendamientoJud option:selected').val();
				
				var parametros = obtenerParametrosActividadDocumento();
				
				parametros += '&mandamientoJudicialId=' + mandamientoJudicialId;
				parametros += '&nuevoEstatusMandamientoJudicial=' + nuevoEstatusMandamientoJudicial; 
				parametros += '&esDocumentoDeMandamientoJudicial=' + true;
				
				$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:true});
				$.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?parametros='+parametros+'&ocultarNumeroOficio=true" width="1140" height="400" />');
			}
		}


		/**
		 *Funcion para obtener los siguientes parametros de la tabla confActividadDocumento:
		 *
		 *actividadId
		 *formaId
		 *tipoDocumento
		 *nombreDocumento
		 *usaEditor
		 *estatusId (No importa, ya que no cambiara el estatus del expediente)
		 *nombreActividad
		 *
		 *Nota se requiere el parametro:numeroUnicoExpediente, que corresponde al numeroExpediente (String), como
		 *obligatorio, ya que se estara generando una actividad
		 */
		function obtenerParametrosActividadDocumento(){
		
			var parametros="";
			
			var actividad=0;
			var formaID=0;
			var titulo="";
			var usaeditor="";
			var estatusId="";
			var nombreActividad="";
			
			var confActividadDocumentoId = '<%=ConfActividadDocumento.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL.getValorId()%>';
		
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/obtenerConfActividadDocumento.do?idConf='+confActividadDocumentoId+'',
				data: '',
				dataType: 'xml',
				async: false,
				success: function(xml){
					actividad=$(xml).find('confActividadDocumentoDTO').find('tipoActividadId').text();
					formaID=$(xml).find('confActividadDocumentoDTO').find('formaId').text();
					titulo=$(xml).find('confActividadDocumentoDTO').find('nombreDocumento').text();
					usaeditor=$(xml).find('confActividadDocumentoDTO').find('usaEditor').text();
					estatusId=$(xml).find('confActividadDocumentoDTO').find('estadoCambioExpediente').find('idCampo').text();
					nombreActividad=$(xml).find('confActividadDocumentoDTO').find('nombreActividad').text();
				}
			});
		
			parametros = '&nuevaActividad=' + actividad; 
			parametros += '&numeroUnicoExpediente='+numexpediente;
			parametros += '&formaId=' + formaID;
			parametros += '&titulo=' + titulo;
			parametros += '&nombreActividad=' + nombreActividad;
		
			return parametros;
		}


		/*
		 * Funcion para validar que se haya seleccionado un estatus para el mandamiento judicial
		 */
		function validarNuevoEstatusMandamientoJud(){
		
			if($('#cbxEstatusMendamientoJud option:selected').val() == "0"){
				customAlert("Selccione el nuevo estatus");
				return false;
			}else{
				return true
			}
		}


		/*
		 * Funcion que permite actualizar/cambiar el estatus del mandamiento judicial
		 */
		function cambiarEstatusMandamientoJudicial(mandamientoJudicialId, nuevoEstatusMandamientoJudicial){

			
			$.ajax({
		   		type: 'POST',
		   		url: '<%=request.getContextPath()%>/actualizarMandamientoJudicial.do?mandamientoId='+mandamientoJudicialId+'&estatusMandamiento='+nuevoEstatusMandamientoJudicial+'&numeroExpedienteId='+numexpedienteid+'&numeroGeneralCaso='+numeroDeCaso+'&numeroExpediente='+numexpediente+'',
		   		data: '',
		   		dataType: 'xml',
		   		async: false,
		   		success: function(jsonObject){
					customAlert("El estatus del mandamiento judicial fue actualizado de manera correcta");
					//Permite recargar el grid de la bandeja principal con el nuevo estatus 
					recargarBandejaPrincipalMandamientosXEstatus(nuevoEstatusMandamientoJudicial);
				}
		    });
		}


		/*
		*Funcion para cerrar la ventana de generarDocumentoSinCaso.jsp
		*llamada desde esta pantalla
		*/
		function cerrarVentanaDocumento(id){
			pantalla += id;
			if(operacion=='INGRESAR' || operacion=='REEDICION'){
				replicarMandamientoAPGR();
			}
			 
			var pantalla ="iframewindowGenerarMandamientoJudicial";
			
			$.closeWindow(pantalla);
			
			if (typeof window.parent.cerrarVentanaMandamientoJudicial == 'function') {
				
				window.parent.cerrarVentanaMandamientoJudicial(idVentana);
	        }

			if (typeof window.parent.cerrarVentanaMandamientoJudicialJS == 'function') {
				
				window.parent.cerrarVentanaMandamientoJudicialJS(idVentana);
	        }
			
		}
		
		function recargarBandejaPrincipalMandamientosXEstatus(idEstatusMandamiento){			
	         if (typeof window.parent.consultaGeneralMandamientoJudicial == 'function') {
				window.parent.consultaGeneralMandamientoJudicial(1,idEstatusMandamiento)
	          }
		}
		

	//**********************************************FUNCIONES PARA ADJUNTAR DOCUMENTO A MANDAMIENTO JUDICIAL*************************************************/

		/*
		*Funcion para abirir la ventana para adjuntar un documento al mandamiento judicial
		*/
		function abreVentanaAdjuntarDocumentoAMandamiento(){
			
			var extensionesPermitidas = ".pdf,.jpg";
			
			if(typeof(mandamientoJudicialId) != "undefined" && typeof(mandamientoJudicialId) != "null" && mandamientoJudicialId != ""){
				$.newWindow({id:"iframewindowAdjuntarDocumentoAMandamientoJudicial", statusBar: true, posx:50,posy:50,width:450,height:200,title:"Adjuntar documento a mandamiento judicial", type:"iframe"});
				$.updateWindowContent("iframewindowAdjuntarDocumentoAMandamientoJudicial",'<iframe src="' + contextoPagina + '/adjuntarDocumentoAMandamientoJudicial.jsp?extensionesPermitidas=' + extensionesPermitidas + '&mandamientoJudicialId='+mandamientoJudicialId+'" width="450" height="200" />');
			}else{
				customAlert("Imposible adjuntar!");
			}   
		}

		/*
		*Funcion que recarga el grid de adjuntar documento, si esa opcion esta seleccionada
		*esta funcion se invoca en adjuntarDocumentoAMandamientoJudicial.jsp
		*/
		function recargaGridAdjuntarDocumento(){

			var tipoMandamientoSeleccionado = $('#cbxTipoConsultaDocumentos option:selected').val();
			
			if(tipoMandamientoSeleccionado == '<%=TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()%>'){
				jQuery("#gridDocumentos").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarGridDeMandamientoJudicialPorExpedienteYTipo.do?mandamientoJudicialId='+mandamientoJudicialId+'&tipoDocumento='+tipoMandamientoSeleccionado+'&numeroExpedienteId='+numexpedienteid+'',datatype: "xml" });
				$("#gridDocumentos").trigger("reloadGrid");
			}
		}

		
		function consultaNombreInvolucrado(idInvolucradoSeleccionado) {
		       
		      $.ajax({
			      type: 'POST',
		    	  url: '<%= request.getContextPath()%>/ConsultarPersonaDatos.do',
		    	  data:'idInvolucrado='+idInvolucradoSeleccionado,
		    	  dataType: 'xml',
		    	  success: function(xml){			    	
			    	  pintaDatosGenerales(xml);				
		    	  }
		    	});
		}
				
		function pintaDatosGenerales(xml){
			   
			   $('#iMCNombre').val($(xml).find('nombresDemograficoDTO').find('nombreDemograficoDTO').find('nombre').first().text());
			   $('#iMCApellidoPaterno').val($(xml).find('nombresDemograficoDTO').find('nombreDemograficoDTO').find('apellidoPaterno').first().text());
			   $('#iMCApellidoMaterno').val($(xml).find('nombresDemograficoDTO').find('nombreDemograficoDTO').find('apellidoMaterno').first().text());
		 }
		
		/**
		* Funci&oacute;n que es invocada cuando se termina la creaci&oacute;n del archivo digital de la medida
		*/
		function replicarMandamientoAPGR(){
			$.ajax({
				type: 'POST',
				url: contextoPagina +'/enviarMandamientoJudicial.do',
				data: 'mandamientoId='+mandamientoJudicialId,
				dataType: 'xml',
				async: false,
				success: function(xml){
					//actualizarDatosCerrar(documentoGeneradoId);
					var mensaje = $(xml).find('body').find('respuesta').text();
					if(mensaje == "fallo_envio_de_mandamiento_judicial"){
						customAlert("Error al replicar mandamiento judicial !!!");
					}
					else{					
						customAlert("Mandamiento judicial enviado correctamente");
					}
				}

			});
		}
		
		function cargaActuaciones() {
			
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/cargarActuacionesPorMandamiento.do?idEstatus='+estatusActualMandamiento+'&idJerarquia='+jerarquia,
				data: '',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$('#cbxAccionesTab').append('<option value="-1">--Seleccione una opci&oacute;n--</option>');
					$(xml).find('catActuaciones').each(function(){
						$('#cbxAccionesTab').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}
		
		function consultaDelitos(){
			var url = contextoPagina+'/CargarDelitosPorMandamiento.do';
			if (mandamientoJudicialId != ""){
				url += '?idMandamiento='+mandamientoJudicialId;
			}else{
				url += '?idsDelitoPersona='+idsRelacionDP;
			}
	    	if(banderaPV==0){
		       	jQuery("#gridCatDelitosPersona").jqGrid({ 
					url: url, 
					datatype: "xml",
					colNames:['Delito','V&iacute;ctima'], 
					colModel:[ 	{name:'Delito',index:'delito', width:250}, 
								{name:'Victima',index:'victima',width:250}					
							],
					rowNum:0,
					rowList:[0,0,0],
					autowidth: true,
					caption:"Delitos asociados al mandamiento",
					sortname: 'Clave',
					viewrecords: true,
					id: 'divgridIzq',
					sortorder: "desc",
					ondblClickRow: function(rowid) {
//	 				    $.newWindow({id:"consultaDelitoPersona", statusBar: true, posx:20,posy:20,width:500,height:350,title:"Consulta delito persona", type:"iframe"});
	<%--              		$.updateWindowContent("consultaDelitoPersona",'<iframe src="<%= request.getContextPath() %>/abrePantallaConsultaDelitoPersona.do?idDelitoPersona='+rowid+'&idNumeroExpediente='+idNumeroExpedienteOp+'" width="500" height="350" />'); --%>
					},
					loadComplete: function(){
				    }
				}).navGrid('#pager1',{edit:false,add:false,del:false});
		        $("#gridCatDelitosPersona").trigger("reloadGrid");
		        banderaPV=1;
	    	}else{
	    		jQuery("#gridCatDelitosPersona").jqGrid('setGridParam', {url : url,datatype: "xml" });
				$("#gridCatDelitosPersona").trigger("reloadGrid");
	    	}
	    }
		
		function seleccionaActuacionCombo(){
			var confActividadId= $("#cbxAccionesTab option:selected").val();
			seleccionaActuacion(confActividadId);
		}
		
		function seleccionaActuacion(confActividadId){
			var actividad=0;
			var formaID=4;
			var titulo="op";
			var usaeditor="";
			var estatusId="";
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
				}
			});
			actuacion=actividad;
			
			if(actividad=='<%=Actividades.ELABORAR_OFICIO_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId()%>'){
		 		var area = 1;
		 		var tipoSolicitud=<%= TiposSolicitudes.CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId() %>;
		 		var url = '<iframe src='+contextoPagina+'/elaborarSolicitud.do?formaId='+formaID+'&numeroUnicoExpediente='+numexpediente
		 				+'&idTipoSolicitud='+tipoSolicitud+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones
		 				+'&actividadId='+actividad+'&mandamientoId='+mandamientoJudicialId+' width="1140" height="550" />';
		 		idWindowPantallaActuaciones++;
		 		$.newWindow({id:"iframewindowGenerarDocumento"+ idWindowPantallaActuaciones, statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Elaborar Solicitud", type:"iframe"});
	     		$.updateWindowContent("iframewindowGenerarDocumento"+ idWindowPantallaActuaciones, url);
	     		$("#" +"iframewindowGenerarDocumento" + idWindowPantallaActuaciones + " .window-maximizeButton").click();
			}else if (actividad == '<%=Actividades.REPORTE_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId()%>'){
				idWindowPantallaActuaciones++;
				var parametros = '?formaId='+formaID+'&numeroUnicoExpediente='+numexpediente+'&actividadId='+actividad
						+'&esTransaccional=true&idNumeroExpediente='+numexpedienteid+'&idSolicitudAnterior='+idSolicitudMandamiento
						+'&ocultarGuardadoParcial=true';						
 				
 				customVentana("iframewindowGenerarDocumento"+ idWindowPantallaActuaciones, 
 						""+titulo, "/generarDocumentoSinCaso.do", parametros, function (){
 							actualizarEstatusSolicitud(idSolicitudMandamiento,<%=EstatusSolicitud.CERRADA.getValorId()%>);
				});
			}
		}
		
		function consultaSolicitud(idSolicitud){
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultaDetalleSolicitudGenerada.do',
				data: 'idSolicitud='+idSolicitud,
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('SolicitudDTO').each(function(){
						seteaDatossolicitud(xml);
					});
				}
			});
		}
		
		function seteaDatossolicitud(xml)
		{
			//setea los datos del xml en los campos de detalle
			$("#txtNoExpediente").val($(xml).find('SolicitudDTO').find('expedienteDTO').find('numeroExpediente').text());
			$("#txtNoCaso").val($(xml).find('SolicitudDTO').find('casoDTO').find('numeroGeneralCaso').text());
			$("#txtFolio").val($(xml).find('SolicitudDTO').find('folioSolicitud').text());
			$("#cbxEstatus").val($(xml).find('SolicitudDTO').contents('estatus').find('valor').text());
			var idEstatusSolicitud = $(xml).find('SolicitudDTO').contents('estatus').find('idCampo').text();
			if (idEstatusSolicitud != "<%=EstatusSolicitud.ABIERTA.getValorId()%>"){
				$("#btnGuardar").button( "option", "disabled", true );
			}
			$("#txtFechaSol").val($(xml).find('SolicitudDTO').find('strFechaCreacion').text()); 
			$("#cbxTipoSol").val($(xml).find('SolicitudDTO').find('tipoSolicitudDTO').find('valor').text());
			$("#txtNombreServidorPub").val($(xml).find('SolicitudDTO').find('destinatario').find('nombreFuncionario').text()+
					" "+$(xml).find('SolicitudDTO').find('destinatario').find('apellidoPaternoFuncionario').text()+
					" "+$(xml).find('SolicitudDTO').find('destinatario').find('apellidoMaternoFuncionario').text());
			$("#txtCargo").val($(xml).find('SolicitudDTO').find('destinatario').find('puesto').find('valor').text());
			$("#txtInstitucion").val($(xml).find('SolicitudDTO').find('institucion').find('nombreInst').text());  
			if($(xml).find('SolicitudDTO').find('usuario').find('areaActual').find('nombre').text()!=''){
				$("#txtAreaAdmin").val($(xml).find('SolicitudDTO').find('usuario').find('areaActual').find('nombre').text());
			}else{
				$("#txtAreaAdmin").val($(xml).find('SolicitudDTO').find('destinatario').find('departamento').find('area').find('nombre').first().text());
			}
		}
		
		function actualizarEstatusSolicitud(solicitudId, estatus){
			$.ajax({
				async: true,
				type: 'POST',
				url: '<%=request.getContextPath()%>/actualizaEstatusSolicitud.do',
				dataType: 'xml',
	        	data: {
	        		solicitudId : idSolicitudMandamiento,
	        		estatus : estatus
	        	},
				success: function(xml){
					customAlert($(xml).find('body').text());
				}
			});
		}

		</script>
	</head>
<body>
<div id="tabsprincipalconsulta">
	<ul>
		<li><a href="#tabsconsultaprincipal-1" id="labelPestaniaMedida">Detalle del mandamiento judicial</a></li>
		<li class="tabsconsultaprincipal-2" id="tabDocumentos"><a href="#tabsconsultaprincipal-2">Documentos</a></li>
		<li class="tabsconsultaprincipal-3" id="tabMandamientosExpediente"><a href="#tabsconsultaprincipal-3">Mandamientos judiciales del expediente</a></li>
		<li class="tabsconsultaprincipal-4" id="tabActuacionesMandamientos"><a href="#tabsconsultaprincipal-4">Cumplimiento de mandamientos judiciales</a></li>
		<li class="tabsconsultaprincipal-5" id="tabDetalleSolicitud"><a href="#tabsconsultaprincipal-5">Detalle de la solicitud</a></li>
	</ul>
		
	<div id="tabsconsultaprincipal-1">
		<input type="hidden" name="xml" id="xml" />
			
		<table border="0">
			<tr valign="top">
				<td colspan="2">
					<table id="iMedidaCautelarViewHeader" width="100%" border="0">
						<tr>
							<td>
								<table border="0">
									<tr valign="top">
										<td>
											<table style="border: 0" width="100%" cellpadding="0"  cellspacing="0" class="celda2">
												<tr>
					                        		<td width="60%" height="28" align="right">N&uacute;mero de Caso:</td>
							                    	<td width="29%" height="28">
							                    		<input type="text" id="txtNumCaso" style="width: 190px;" disabled="disabled"/>
							                    	</td>
							                	</tr>
					                        	<tr>
					                        		<td width="60%" height="28" align="right" id="idEtiquetaNumCausa">N&uacute;mero de Causa:</td>
							                    	<td width="29%" height="28">
							                    		<input type="text" id="txtNumCausa" style="width: 190px;" disabled="disabled"/>
							                    	</td>
							                	</tr>
							                	<tr id="rowNumeroCausa">
					                        		<td width="60%" height="28" align="right">N&uacute;mero de Causa:</td>
							                    	<td width="29%" height="28">
							                    		<input type="text" id="txtNumeroCausaPJ" style="width: 190px;" disabled="disabled"/>
							                    	</td>
							                	</tr>
							           			<tr>
							                    	<td width="60%" height="30" align="right">&nbsp;</td>
						                        	<td width="29%">&nbsp;</td>
					                        	</tr>							               
					                    	</table>
										
											<table style="border: 0; background:#DDD;" width="100%" height="143" cellpadding="0"  cellspacing="0" class="celda2">
					                        	<tr>
							                    	<td width="60%" height="30" align="right">Nombre:</td>
						                        	<td width="29%">
						                        		<input type="text" value="" readonly="readonly" title="Escribir nombre" size="50" maxlength="40" id="iMCNombre" style="background:#DDD;border: 0;" readonly="readonly"/>
						                        	</td>
					                        	</tr>
					                        	<tr>
							                    	<td width="60%" height="28" align="right">Apellido paterno:</td>
							                    	<td width="29%" height="28">
							                    		<input type="text" value="" readonly="readonly" readonly="readonly" size="50" maxlength="40" id="iMCApellidoPaterno" style="background:#DDD;border: 0;" readonly="readonly"/>
							                    	</td>
							                	</tr>
							                	<tr>
					                          		<td width="60%"  height="35" align="right">Apellido materno:</td>
						                    		<td height="35">
						                    			<input type="text" value="" readonly="readonly"  readonly="readonly" size="50" maxlength="40" id="iMCApellidoMaterno" style="background:#DDD;border: 0;" readonly="readonly"/>
						                    		</td>
					                        	</tr>
					                    	</table>
										</td>
									</tr>
								</table>
								
								<!-- Comienza tabla cambio estatus -->
								<table border="0" >
									<tr id="seccionCambiarEstatusMandamiento" style="display:none">
										<td>
											<strong>Estado:</strong>
											<select id="cbxEstatusMendamientoJud" style="width:150px">
												<option value="0">-Seleccione-</option>
											</select>
										</td>
									    <td>
											<input type="button" value= "Guardar" id="btnGuardarEstatusMandamientoJud" class="ui-button ui-corner-all ui-widget" onclick="generarDocumentoDeCambioEstatusDeMandamientoJudicial();"/>
										</td>
									</tr>
										
									<tr id="seccionAdjuntarDocumentoAMandamiento" style="display:none">
										<td>&nbsp;
												
										</td>
									    <td>
											<input type="button" value= "Adjuntar Documento" id="btnAdjuntarDocAMandamientoJud" class="ui-button ui-corner-all ui-widget" onclick="abreVentanaAdjuntarDocumentoAMandamiento();"/>	
										</td>
									</tr>
									
								</table>
								<!-- Termina tabla cambio estatus -->
								
								
								
							</td>
						</tr>
					</table>
				</td>
				<td width="14%">
					<table border="0">
						<tr>
							<td width="20%" align="right">Tipo de Mandamiento:</td>
							<td width="20%">
						        <select id="tipoMandamiento">
									<option value="-1">-Seleccione-</option>
						    	</select>
							</td>
						</tr>		
										
						<tr id="estadoTR">
							<td width="20%" align="right">Estado:</td>
							<td>
								<input type="text" id="cmpEstado" size="40" style="width: 150px;" disabled="disabled">
							</td>
						</tr>
						
						<tr>
							<td width="20%" align="right">Fecha de ejecuci&oacute;n:</td>
							<td><input type="text" id="mandamientoJudicialFechaEjecucion" style="width: 100px;" /></td>
						</tr>
						
						<tr id="rowFechaInicioMandamientoJudicial">
							<td width="20%" align="right">Fecha de inicio:</td>
							<td><input type="text" id="mandamientoJudicialFechaInicio" style="width: 100px;" disabled="disabled" /></td>
						</tr>
						
						<tr>
					        <td width="20%" align="right">
					        	<div id="divEtiTipoSentencia">
						        	<strong>Tipo de Sentencia:</strong>
					            </div>
					        </td>
					        <td width="20%">
					        	<div id="divCbxTipoSentencia">
					                <select id="tipoSentencia" style="width: 150px;">
					                    <option value="-1">-Seleccione-</option>
					                </select>
					        	</div>
					        </td>
					    </tr>
					    
					    <tr>
					        <td width="20%" align="right">
					        	<div id="divEtiFechaInicioSentencia">
					        		<strong>Fecha Inicio:</strong>
					        	</div>
					        </td>
					        <td width="20%" >
					        	<div id="divFechaInicioSentencia">
					        		<input type="text" id="fechaInicioSentencia" style="width: 100px;"/>
					        	</div>
					        </td>
					    </tr>
					    <tr>
					        <td width="20%" align="right">
					        	<div id="divEtiFechaFinSentencia">
					        		<strong>Fecha Fin:</strong>
					        	</div>
					       	</td>
					        <td width="20%">
					        	<div id="divFechaFinSentencia">
					        		<input type="text" id="fechaFinSentencia" style="width: 100px;"/>
					        	</div>
					        </td>
					    </tr>
					    
					    <tr>
							<td width="20%" align="right">Descripci&oacute;n:</td>
							<td><textarea style="width: 150px;" id="descripcionMandamiento"></textarea></td>
						</tr>
						
						
					</table>	
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center">
		    		<table id="gridCatDelitosPersona" align="center"></table>
		    		<div id="pager1"></div>
				</td>
			</tr>
			<tr valign="top">
				<td colspan="3" align="center">
					<table id="iMandamientoJudicialWorkSheet" width="100%"  border="0">
						<tr valign="top">
							<td align="center">
								<input type="button" value="Generar Mandamiento Judicial" id="guardarMandamientoJudicial" class="ui-button ui-corner-all ui-widget"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	
		<form name="frmDocumento" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
			<input type="hidden" name="documentoId" />
		</form>
	</div>

	<div id="tabsconsultaprincipal-2">
		<table width="700" border="0">
			<tr>
				<td>
					<strong>Tipos de documentos:</strong>
					<select id="cbxTipoConsultaDocumentos">
						<option value="<%=TipoDocumento.MANDAMIENTO_JUDICIAL.getValorId()%>">Documentos de creacion</option>
						<option value="<%=TipoDocumento.CAMBIO_DE_ESTADO_DE_MANDAMIENTO_JUDICIAL.getValorId()%>">Documentos por cambio de estatus</option>
						<option value="<%=TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()%>">Documentos Adjuntos</option>
						<option value="<%=TipoDocumento.REPORTE_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId()%>">Documentos de respuesta</option>
					</select>
				</td>
			</tr>

			<tr>
				<td>
					<div>
						<table id="gridDocumentos" ></table>
						<div id="pagerGridDocumentos" style="width: 300"></div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="tabsconsultaprincipal-3">
	    
		<table border="0">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>
					<div>
						<table id="gridMandamientosJudiciales" ></table>
						<div id="pagerGridMandamientosJudiciales"></div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div id="tabsconsultaprincipal-4">
		<fieldset>
			<table border="0">
				<tr>
					<td>
					Actuaciones: <select id="cbxAccionesTab" onchange="seleccionaActuacionCombo()">
								 </select>
					</td>
				</tr>
			</table>
		</fieldset>
	</div>
	<div id="tabsconsultaprincipal-5">
		<table width="100%">
			<tr>
				<td width="50%">
					<fieldset style="height: 100%; min-height: 180px;">
						<legend>Datos de la Solicitud</legend>
						<table width="100%">
							<tr>
								<td>No. Expediente: </td>
								<td>
									<input type="text" id="txtNoExpediente" maxlength="15" disabled="disabled" size="30"/> 
								</td>
							</tr>
							<tr>
								<td>No. Caso: </td>
								<td>
									<input type="text" id="txtNoCaso" maxlength="15" disabled="disabled" size="30"/> 
								</td>
							</tr>
							<tr>
								<td>Folio : </td>
								<td>
									<input type="text" id="txtFolio" maxlength="15" disabled="disabled" size="30"/> 
								</td>
							</tr>
							<tr>
								<td>Estatus : </td>
								<td>
									<input type="text" id="cbxEstatus" maxlength="30" disabled="disabled" size="30"/> 
								</td>
							</tr>
							<tr>
								<td>Fecha de solicitud : </td>
								<td>
									<input type="text" id="txtFechaSol" maxlength="10" disabled="disabled" size="30"/> 
								</td>
							</tr>
							<tr>
								<td>Tipo de Solicitud : </td>
								<td>
									<input type="text" id="cbxTipoSol" maxlength="30" disabled="disabled" size="30"/> 
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
				<td width="50%">
					<fieldset style="height: 100%; min-height: 180px;">
						<legend>Informaci&oacute;n Remitente</legend>
						<table width="100%">
							<tr>
								<td>Nombre del Servidor <br/>P&uacute;blico :</td>
								<td>
									<input type="text" id="txtNombreServidorPub" maxlength="30" disabled="disabled" size="40"/> 
								</td>
							</tr>
							<tr>
								<td>Cargo : </td>
								<td>
									<input type="text" id="txtCargo" maxlength="30" disabled="disabled" size="40"/> 
								</td>
							</tr>
							<tr>
								<td>Instituci&oacute;n : </td>
								<td>
									<input type="text" id="txtInstitucion" maxlength="30" disabled="disabled" size="40"/> 
								</td>
							</tr>
							<tr>
								<td>&Aacute;rea Administrativa :</td>
								<td>
									<input type="text" id="txtAreaAdmin" maxlength="30" disabled="disabled" size="40"/> 
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button id="btnGuardar" type="button">Responder Solicitud</button>
				</td>
			</tr>
		</table>
	</div>
</div>

<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
	<input type="hidden" name="archivoDigitalId" value=""/>
</form>

<form name="verMandamientoForm" id="verMandamientoForm"
	action="<%=request.getContextPath()%>/ConsultarContenidoArchivoDigital.do"
	method="post">
	<input type="hidden" name="documentoId" />
	<input type="hidden" name="archivoDigitalId" />
</form>
					
</body>
</html>