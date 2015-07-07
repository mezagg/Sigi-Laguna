<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento"%>
<%@page import="mx.gob.segob.nsjp.comun.mandamiento.EstatusMandamientoPersona"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades" %>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento"%>

<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Administrar Mandamiento Judicial</title>

		<!--css de aplicacion-->
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
		
		<!--css para jQuery-->
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
		
		<!--css para jqGrid-->
		<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
		
		<!--css para ventanas windows engine-->
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
		
		<!--js para jQuery-->
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
		
		<!--js para jqGrid-->
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
		
		<!--para controlar las ventanas windows engine-->
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>
		
		<!--js de la aplicacion-->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
		
		<!-- Script de mandamientos judicial -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/funcionesComunMandJudYMedCautelares.js"></script>
		
		<%
			Long rolActivo = 0L;
			String jerarquia = "";
			Long institucionId = 0L;
			
			UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO"); 
			if (usuario != null 
					&& usuario.getRolACtivo() != null 
					&& usuario.getRolACtivo().getRol() != null
					&& usuario.getRolACtivo().getRol().getRolId() != null){
				
				rolActivo = usuario.getRolACtivo().getRol().getRolId();
				
				if (usuario.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO() != null
						&& usuario.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId() > 0L){
					jerarquia = usuario.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId().toString();
				}
			}
			
			if(usuario != null && usuario.getInstitucion() != null && usuario.getInstitucion().getConfInstitucionId() != null){
				institucionId = usuario.getInstitucion().getConfInstitucionId();
			}
		%>	
		
		<script type="text/javascript">
		
			//VARIABLES DE CONTEXTO
			var contextoPagina = "${pageContext.request.contextPath}";
			var CONTEXT_ROOT = '<%= request.getContextPath()%>';

			/*
			*VARIABLES PARA ESTATUS DE MANDAMIENTOS JUDICIALES
			*/
			//Estatus activos
			var NO_ATENDIDO = <%=EstatusMandamiento.NO_ATENDIDO.getValorId()%>; 
			var ATENDIDO = <%=EstatusMandamiento.ATENDIDO.getValorId()%>;
			var EN_PROCESO = <%=EstatusMandamiento.EN_PROCESO.getValorId()%>;
			var SIN_DOCUMENTO_DE_CREACION = <%=EstatusMandamiento.SIN_DOCUMENTO_DE_CREACION.getValorId()%>;
			var SIN_DOCUMENTO_DE_ESTATUS = <%=EstatusMandamiento.SIN_DOCUMENTO_DE_ESTATUS.getValorId()%>;
			var NO_ENVIADO = <%=EstatusMandamiento.NO_ENVIADO.getValorId()%>;
			var ACTUALIZACION_NO_ENVIADA = <%=EstatusMandamiento.ACTUALIZACION_NO_ENVIADA.getValorId()%>;
			
			//Estatus inactivos
			var CONCLUIDO = <%=EstatusMandamiento.CONCLUIDO.getValorId()%>; 
			var CANCELADO = <%=EstatusMandamiento.CANCELADO.getValorId()%>; 
			var EJECUTADO = <%=EstatusMandamiento.EJECUTADO.getValorId()%>; 

			/*
			*VARIABLES PARA LA RECARGA DEL GRID DESDE LA VENTANA
			*/
			var selectorMandamiento_recarga		=	'<%=request.getParameter("selectorMandamiento_recarga")%>';
			var fechaInicio_recarga				=	'<%=request.getParameter("fechaInicio_recarga")%>';
			var fechaFin_recarga				=	'<%=request.getParameter("fechaFin_recarga")%>';
			var numeroExpediente_recarga		=	'<%=request.getParameter("numeroExpediente_recarga")%>';
			var idsTiposMandamientos_recarga	=	'<%=request.getParameter("idsTiposMandamientos_recarga")%>';
			var banderaPropios_recarga			=	'<%=request.getParameter("banderaPropios_recarga")%>';
			var estatusDeFiltrado_recarga		=	'<%=request.getParameter("estatusDeFiltrado_recarga")%>';
			
			/*
			*VARIABLES PARA LA OPERACION
			*/		
			var jerarquia				=	'<%=jerarquia%>';
			var idSolicitudMandamiento	=	<%=request.getParameter("idSolicitudMandamiento") != null ? request.getParameter("idSolicitudMandamiento") : "0"%>;
			var mandamientoJudicialId	=	'<%=request.getParameter("mandamientoId")%>';
			var numeroExpediente;
			var numeroExpedienteId;
			//variable para id de la audiencia, usado en JSP include de mandamientos judiciales del expediente
			//siempre debe estar en cero
			var audienciaId = 0;
			//varibale que indica si se consulta por audiencia o por expediente
			// al JSP de consultarMandamientosJudicialesExp.jsp
			var esAudiencia = false;
			//Rol del usuario activo
			var rolActivo = '<%=rolActivo%>';
			//Parametro para saber si se trata de una consulta o ingreso de mandamiento
			var esConsulta = true;
			//cuando es administrar mandamiento el idExpediente se consulta con el detalle del mandamiento
			var idExpediente;
			//Id estatus mandamiento judicial
			var estatusIdMandamiento = "";
			//Id del estatus de la solicitud (dependiendo de si tiene o no)
			var idEstatusSolicitud = "";
			//para controlar el id del editor de texto
			var idWindowPantallaActuaciones = 0;

					
			
			jQuery().ready(function() {
				//Se crean las tabs
				crearTabs();
				//Funcion que inicializa la ventana
				inicia();
				configurarPantallaMandamientosJudiciales();
					
			});
		
			/*
			 *Funcion para crear las tabs
			 */
			function crearTabs() {
				$("#tabsAdministrarMandamientoJudicial").tabs();
			}

			
			/*
			 *Funcion para inicializar el funcionamiento de la ventana
			 */
			function inicia(){
				//Se cargan funciones de la ceja Administrar Mandamiento Judicial
				cargaTipoMandamientoAdminManJud();
				//Carga el grin 
				cargarGridAdminManJud();
				//Se consulta el detalle del mandamiento judicial
				consultarMandamientoJudicial();
			}


			/*
			*Funcion que configura la pantalla dependiendo del usuario
			*/
			function configurarPantallaMandamientosJudiciales(){

				//Configura las tabs del visor
				if (rolActivo == '<%=Roles.DIRECTOR_APREHENSIONES.getValorId()%>'){
					$("#btnAdjuntarDocAMandamientoJud").show();
					ocultaMuestraTabVisor("tabCumplimientoMandamientosJudiciales",1)
					cargaActuaciones(estatusIdMandamiento);
				}else if(rolActivo == '<%=Roles.POLICIAMINISTER.getValorId()%>'){
					$("#etiquetaCejaAdministrar").html("Detalle del Mandamiento Judicial");
					ocultaMuestraTabVisor("tabMandamientosDelExpediente",0)
					ocultaMuestraTabVisor("tabCumplimientoMandamientosJudiciales",1)
					ocultaMuestraTabVisor("tabDetalleDeLaSolicitud",1)
					consultaSolicitud(idSolicitudMandamiento);
					cargaActuaciones(idEstatusSolicitud);
						
					//Se deshabilita el grid de adiministrar, por eso se pone como si siempre las
					//consultara en este estatus, es solo para aprovechar la funcionalidad
					configurarPantallaPorEstatus(<%=EstatusMandamiento.SIN_DOCUMENTO_DE_CREACION.getValorId()%>);
				}else if(rolActivo == '<%=Roles.AGENTEMP.getValorId()%>'){
					//Solo consulta documentos de creacion y de estatus
				}else if(rolActivo == '<%=Roles.ENCARGADOCAUSA.getValorId()%>'){
					//Solo consulta documentos de creacion y de estatus
				}

				
				//Configura las columnas del gird de administrars
				if(<%=institucionId%> != <%=Instituciones.PJ.getValorId()%>){
					jQuery("#gridAdminManJud").jqGrid('hideCol',["fechaResolutivo"]);
				}	
			}

			/*
			*
			*/
			function ocultaMuestraTabVisor(claseTab,bandera){
				//oculta
				if(parseInt(bandera)==0){
					$("."+claseTab).hide();
				}
				///muestra
				else{
					$("."+claseTab).show();
				}
			}
				
/*********************************************** FUNCIONES CEJA ADMINISTRAR MANDAMIENTO JUDICIAL ***********************************************************/
		
		/*
		*Funcion para consultar un mandamiento judicial por su id
		*/
		function consultarMandamientoJudicial(){
									
			$.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/consultarMandamientoJudicial.do',
	    		data: 'mandamientoJudicialId='+mandamientoJudicialId,
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){

	    				//Llenamos el detalle del mandamiento
	    				var numeroDeCaso = $(xml).find('mandamientoJudicial').first().find('expedienteDTO').first().find('casoDTO').first().find('numeroGeneralCaso').text();
	    				numeroExpediente = $(xml).find('mandamientoJudicial').first().find('expedienteDTO').first().find('numeroExpediente').text();
	    				numeroExpedienteId = $(xml).find('mandamientoJudicial').first().find('expedienteDTO').first().find('numeroExpedienteId').text();
	    				var tipoMandamientoId = $(xml).find('mandamientoJudicial').first().find('tipoMandamiento').find('idCampo').text();
	    				var fechaCreacion = $(xml).find('mandamientoJudicial').first().find('strFechaCreacion').text();
	    				idExpediente = $(xml).find('mandamientoJudicial').first().find('expedienteDTO').first().find('expedienteId').text();
						//Parametros para permitir editar el documento de creacion
	    				var folioDocumento = $(xml).find('mandamientoJudicial').first().find('folioDocumento').text();
	    				estatusIdMandamiento = $(xml).find('mandamientoJudicial').last().find('estatus').first().find('idCampo').text();
	    				var esGuardadoParcialMandamiento = $(xml).find('mandamientoJudicial').first().find('esGuardadoParcial').text();
	    				configurarPantallaPorEstatus(estatusIdMandamiento);
	    				generarLigaMandamientoParcial(estatusIdMandamiento,folioDocumento,esGuardadoParcialMandamiento);
	    				//Correspondientes a la ceja de Administrar Mandamiento Judicial
	    				$("#numeroCasoAdminManJudTxt").val(numeroDeCaso);
	    				$("#numeroCausaAdminManJudTxt").val(numeroExpediente);
	    				$('#tipoMandamientoAdminManJudCbx').val(tipoMandamientoId);
	    				$("#fechaCreacionAdminManJudTxt").val(fechaCreacion);

	    				//Correspondientes a la ceja de Administrar Mandamientos Judiciales del expediente
	    				$("#numeroCasoGenManJudTxtExp").val(numeroDeCaso);
	    				$("#numeroCausaGenManJudTxtExp").val(numeroExpediente);
	    		}	
	    	});
		}

		/*
		* Bloquea la pantalla dependiendo del estatus del mandamiento consultado
		*/
		function configurarPantallaPorEstatus(estatusIdMandamiento){

			var estatusMandamiento = parseInt(estatusIdMandamiento);

			if(estatusMandamiento == <%=EstatusMandamiento.SIN_DOCUMENTO_DE_CREACION.getValorId()%>
				|| estatusMandamiento == <%=EstatusMandamiento.SIN_DOCUMENTO_DE_ESTATUS.getValorId()%>
					|| estatusMandamiento == <%=EstatusMandamiento.ATENDIDO.getValorId()%>){

				//Oculta imagen date picker
				$("img.ui-datepicker-trigger").hide();
				//Deshabilita el combo box de cambio de estatus
				$('select[id^="cbxCambioEstausMandPer_"]').each(function(){
					id = $(this).attr("id");
					$("#"+id).attr("disabled",true);
				});
				//Oculta boton guardar
				$("#guardarAdmin").hide();
				//deshabilita cuando el grid esta completo
				deshabilitaGridMand();
			}
		}

		/**
		*Funcion que redefine el evento complete para bloquear todos los elementos
		*tras recargarse el grid
		*/
		function deshabilitaGridMand(){

			jQuery("#gridAdminManJud").jqGrid('setGridParam', { 
				gridComplete: function() { 
					$("img.ui-datepicker-trigger").hide();
					//Deshabilita el combo box de cambio de estatus
					$('select[id^="cbxCambioEstausMandPer_"]').each(function(){
						id = $(this).attr("id");
						$("#"+id).attr("disabled",true);
					});
				} 
			});
		}

		/**
		 * Genera la liga para seguir editando el mandamiento 
		 */
		function generarLigaMandamientoParcial(estatusIdMandamiento,folioDocumento,esGuardadoParcialMandamiento){

			if(parseInt(estatusIdMandamiento) == <%=EstatusMandamiento.SIN_DOCUMENTO_DE_CREACION.getValorId()%>){
				$('#labelLigaMandamientoParcial').show();
				$('#ligaMandamientoParcial').html('<a href="#linkDocumentoCreacion" title="Editar documento de creaci&oacute;n" onclick="abrirEditorDocumentosMandamientos('+mandamientoJudicialId+','+<%=Actividades.GENERAR_MANDAMIENTO_JUDICIAL.getValorId()%>+','+esGuardadoParcialMandamiento+','+true+')">'+folioDocumento+'</a>');																			
			}
		}

		/**
		*Oculta la liga de mandamiento parcial
		*/
		function ocultarLigaMandamientoParcial(){
			$('#labelLigaMandamientoParcial').hide();
			$('#ligaMandamientoParcial').hide();
		}
			
		
		/**
		* Funcion que realiza la carga del catálogo de tipos de mandamiento
		*/
		function cargaTipoMandamientoAdminManJud() {

			$('#tipoMandamientoAdminManJudCbx').empty();
			$('#tipoMandamientoAdminManJudCbx').append('<option value="0"> ---Seleccione--- </option>');	
			
			$.ajax({
				async: true,
				type: 'POST',
				url: '<%=request.getContextPath()%>/consultarCatalogoTipoMandamiento.do',
				data: '',
				dataType: 'xml',
				success: function(xml){
					
					$(xml).find('tipoMandamiento').each(function(){
						$('#tipoMandamientoAdminManJudCbx').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}

		/**
		*Funcion que carga el grid para la administracion de Mandamiento Judicial
		*/
		var gridAdminManJud = true;
		
		function cargarGridAdminManJud(){
			
			if(gridAdminManJud){
				jQuery("#gridAdminManJud").jqGrid({
					url:'<%= request.getContextPath()%>/administrarMandamientosPersona.do?mandamientoJudicialId='+mandamientoJudicialId+'',
					datatype: "xml", 
					colNames:['<bean:message key="probableResponsable"/>','Fecha Resolutivo','Estatus Actual','Estatus ActualId','Cambiar Estatus a:','Fecha de Ejecuci&oacute;n','Documento Estatus','Es Parcial','Ver Relaci&oacute;n'], 
					colModel:[  {name:'probResponsable',index:'probResponsable', width:250, align:'center'},						
					           	{name:'fechaResolutivo',index:'fechaResolutivo', width:100, align:'center'}, 
					           	{name:'estatusMandamientoPersona',index:'estatusMandamientoPersona', width:180,align:'center',sortable:false},
					           	{name:'estatusActualId',index:'estatusActualId', width:180,align:'center',hidden:true,sortable:false}, 
					           	{name:'cambiarEstatus',index:'cambiarEstatus', width:180, align:'center',sortable:false},
					           	{name:'fechaEjecucion',index:'fechaEjecucion', width:110, align:'center'},
					           	{name:'docEstatus',index:'docEstatus', width:200, align:'center',sortable:false},
					           	{name:'esParcial',index:'esParcial', width:70, align:'center',sortable:false},
					           	{name:'verRelacionDelPer',index:'verRelacionDelPer', width:180, align:'center',sortable:false}
							],
					pager: jQuery('#pagerGridAdminManJud'),
					rowNum:10,
					rowList:[10,20,30],
					width:1380,
					viewrecords:true,
					hiddengrid:false,
					async:false,
					sortname:'probResponsable',
					sortorder:"asc",
					caption:"Administraci&oacute;n de Mandamientos Judiciales",
					multiselect: true,
					gridComplete: function () {
						creaDatePickers();
						seleccionarItems($(this));
					},
					onPaging: function () {
						guardarItemsSeleccionados($(this)); 
					},
					onSortCol: function(){
						eliminarItemsSeleccionados($(this));
					}
				});
				
				gridAdminManJud = false;
			}else{
				jQuery("#gridAdminManJud").jqGrid('setGridParam',{url:'<%= request.getContextPath()%>/administrarMandamientosPersona.do?mandamientoJudicialId='+mandamientoJudicialId+'',datatype: "xml" });
				$("#gridAdminManJud").trigger("reloadGrid");
			}
			
		}


		/**
		*Funcion para generar los datepickers del Grid, para
		*la funcionalidad del calendario
		*/
		function creaDatePickers(){

			$('input[id^="fechaEjecucion_"]').each(function(){

				id = $(this).attr("id");

				//Obtenemos el id de la notificacion
				var idMandamietoPersona = id.split("_")[1];
				
				$("#"+id).datepicker({
					dateFormat: 'dd/mm/yy',
					yearRange: '1900:2100',
					changeMonth: true,
					changeYear: true,
					showOn: "button",
					//Set como fecha minima
					//minDate: fechaCreacion,
					buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
					buttonImageOnly: true			
				});		
				
			});
		}


		
		function obtenerDatosSeleccionados(){

			var listaParametros = "";

			var registrosSelecc = new Array();
			registrosSelecc = obtenerSeleccionados($("#gridAdminManJud"));

			//Verificamos que haya seleccionado registros
			if(parseInt(registrosSelecc.length) > 0){

				//Recorremos cada uno de los seleccionados
				for(contReg = 0; contReg < registrosSelecc.length; contReg++){

					var mandPerId = registrosSelecc[contReg];

					
					 var datosRenglon = jQuery("#gridAdminManJud").jqGrid('getRowData',mandPerId);
					 var estatusActualId = datosRenglon.estatusActualId;

					if(parseInt(estatusActualId) == <%=EstatusMandamientoPersona.ATENDIDO.getValorId()%>
						|| parseInt(estatusActualId) == <%=EstatusMandamientoPersona.CANCELADO.getValorId()%>
							|| parseInt(estatusActualId) == <%=EstatusMandamientoPersona.CONCLUIDO.getValorId()%>){
							
						customAlert("Alg&uacute;n mandamiento persona seleccionado, se encuentra en estado terminal.<br/>Verifique su selecci&oacute;n.");
						return false;
					}
					
					//Obtenemos el nuevo estatus
					var estatusSeleccionado =  $("#cbxCambioEstausMandPer_"+mandPerId).val();

					//Validamos el nuevo estatus
					if(parseInt(estatusSeleccionado) != 0){
						//obtenmos la fecha de ejecucion
						var fechaEjecucion = $("#fechaEjecucion_"+mandPerId).val();
						//Validamos la fecha ejecucion
						if(parseInt(estatusSeleccionado) == <%=EstatusMandamientoPersona.ATENDIDO.getValorId()%> 
								|| parseInt(estatusSeleccionado) == <%=EstatusMandamientoPersona.CANCELADO.getValorId()%>
									|| parseInt(estatusSeleccionado) == <%=EstatusMandamientoPersona.CONCLUIDO.getValorId()%>){
							if(fechaEjecucion == 'undefined' || fechaEjecucion == null || fechaEjecucion == 'null'|| fechaEjecucion == ""){
								customAlert("No ha seleccionado fecha de ejecuci&oacute;n para estatus terminal.");
								return false;
							}
						}
						
						listaParametros += mandPerId +","+ estatusSeleccionado +","+ fechaEjecucion +"|";
						
					}else{
						customAlert("Verifique la selecci&oacute;n del estatus de los mandamientos persona.");
					}
				}

				return listaParametros;
			}else{
				customAlert("No ha seleccionado ningun mandamiento persona.");
				return false;
			}
		}

		//Bloquemos pantalla
		bloquearPantalla(true, "Generando actualizaciones");  

		function administrarMandamientosPersona(){
			
			var parametros = obtenerDatosSeleccionados();
			
			if(parametros != false){

				parametros += '&mandamientoId=' + mandamientoJudicialId;
				parametros += '&expedienteId=' + idExpediente;
				
				$.ajax({
					type: 'POST',
					url: '<%= request.getContextPath()%>/generarDocumentoCambioEstatusMandamientoPersona.do',
					data:'parametros='+parametros,
					dataType: 'xml',
					async: false,
					success: function(respuesta){
						if(parseInt($(respuesta).find('code').text())==0){
							 if($(respuesta).find('body').text() != null 
									 && $(respuesta).find('body').find('respuesta').text() != "null"
										 && $(respuesta).find('body').find('respuesta').text() != ""){
								 
									//Ocultar boton de al guardar
									$("#guardarAdmin").hide();
									//recargar bandeja
									cargarGridAdminManJud();
									
									//abrir editor de texto
									var documentoCambioId = $(respuesta).find('body').find('respuesta').text();
									abrirEditorDocumentosMandamientos(documentoCambioId,<%=Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL.getValorId()%>,true, true);
											
							 }else{
								 customAlert("Ocurri&oacute; un error al intentar actualizar los mandamientos persona.<br/>"
										 + "por favor contacte al administrador.");
							 }
						 } else {
							 customAlert("Ocurri&oacute; un error al intentar actualizar los mandamientos persona.<br/>"
									 + "por favor contacte al administrador.");
						 }
						 desbloquearPantalla();
					}
				});
			}			
		}

		/*****************************FUNCIONES ADJUNTAR ARCHIVO DIGITAL****************************/
		
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
		
		//Variable que controla la apertura del jsp ver Relacion Delito-Persona
		var idWindowVerRelDelitoPersonaProbResponsable = 0;
		/**
		* Funcion para consultar la relaciones delito-persona 
		* por ProbableResponsableId y MandamientoPersonaId
		*/
		function abrirVerRelacionDelitoPersona(probableResponsableId,mandamientoPersonaId){
			if(validarNumeroVentanasAbiertas(idWindowVerRelDelitoPersonaProbResponsable)){
				idWindowVerRelDelitoPersonaProbResponsable++;
				$.newWindow({id:"iframeWindowVerRelDelitoPersonaProbResponsable"+idWindowVerRelDelitoPersonaProbResponsable, statusBar: true,minimizeButton: false,maximizeButton: false, posx:255,posy:110,width:580,height:310,title:"Ver Relaci&oacute;n Delito-Persona", type:"iframe",onWindowClose: function(id){
					idWindowVerRelDelitoPersonaProbResponsable--;}
				});
				$.updateWindowContent("iframeWindowVerRelDelitoPersonaProbResponsable"+idWindowVerRelDelitoPersonaProbResponsable,'<iframe src="<%= request.getContextPath() %>/abrirVerRelacionDelitoPersonaProbableResp.do?probableResponsableId='+probableResponsableId+'&mandamientoPersonaId='+mandamientoPersonaId+'&mandamientoJudicialId='+mandamientoJudicialId+'" width="580" height="310" />');
			}
		}
/*********************************************** TERMINAN FUNCIONES CEJA ADMINISTRAR MANDAMIENTO JUDICIAL ******************************************************/


/*********************************************** FUNCIONES CEJA CUMPLIMIENTO DE MANDAMIENTOS JUDICIALES ********************************************************/

		/**
		*Funcion que carga el combo con las actuaciones
		*estatusConsulta es la variable que almacena el estatus de la solicitud mandamiento
		*o el estatus del mandamiento dependiente del rol del usuario logeado
		*Si es director de aprensiones, toma el estatus del mandamiento judicial
		*Si es policia ministerial, toma el valor del estatus de la solicitud
		*/
		function cargaActuaciones(estatusConsulta) {

			$('#cbxActuacionesAdminManJud').empty();
			$('#cbxActuacionesAdminManJud').append('<option value="0">--Seleccione una opci&oacute;n--</option>');
			
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/cargarActuacionesPorMandamiento.do?idEstatus='+estatusConsulta+'&idJerarquia='+jerarquia,
				data: '',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('catActuaciones').each(function(){
						$('#cbxActuacionesAdminManJud').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}
		
		
		/**
		*Funcion para cambiar una actuacion en el combo
		*/
		function seleccionaActuacionCombo(){
		
			var confActividadId= $("#cbxActuacionesAdminManJud option:selected").val();

			if(validarNumeroVentanasAbiertas(idWindowPantallaActuaciones)){

				if(parseInt(confActividadId) > 0){
					seleccionaActuacion(confActividadId);
				}
			}
		}

		/**
		*Funcion para seleccionar una actuacion y 
		*abrir la ventana Elaborar solicitud 
		*/
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
			
			//actuacion=actividad;
			
			if(actividad=='<%=Actividades.ELABORAR_OFICIO_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId()%>'){

				if(abrirActuacionSolCumplimientoMandamientoJud()){
					
					idWindowPantallaActuaciones++;
					
			 		var tipoSolicitud=<%= TiposSolicitudes.CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId() %>;
			 		var url = '<iframe src='+contextoPagina+'/elaborarSolicitud.do?formaId='+formaID+'&esconderArbol=1&numeroUnicoExpediente='+numeroExpediente
			 				+'&idTipoSolicitud='+tipoSolicitud+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones
			 				+'&actividadId='+actividad+'&mandamientoId='+mandamientoJudicialId+' width="1140" height="550" />';
			 		
			 		$.newWindow({id:"iframewindowGenerarDocumento"+ idWindowPantallaActuaciones, statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Elaborar Solicitud", type:"iframe", confirmarCierreVentana:true,muestraMensaje:true, onWindowClose: function(id){
			 				idWindowPantallaActuaciones--;
						}
					});
					
			 		$.updateWindowContent("iframewindowGenerarDocumento"+ idWindowPantallaActuaciones, url);
			 		$("#" +"iframewindowGenerarDocumento" + idWindowPantallaActuaciones + " .window-maximizeButton").click();
				}
			}else if (actividad == '<%=Actividades.REPORTE_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId()%>'
							|| actividad == '<%=Actividades.SEGUIMIENTO_A_OFICIO_DE_CUMPLIMIENTO_DE_MANDAMIENTO_JUDICIAL.getValorId()%>'){
				
				idWindowPantallaActuaciones++;
				
				var parametros = '?formaId='+formaID+'&esconderArbol=1&numeroUnicoExpediente='+numeroExpediente+'&actividadId='+actividad
						+'&esTransaccional=true&idNumeroExpediente='+numeroExpedienteId+'&idSolicitudAnterior='+idSolicitudMandamiento
						+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&ocultarGuardadoParcial=true';						
					
					customVentana("iframewindowGenerarDocumento"+ idWindowPantallaActuaciones, 
							""+titulo, "/generarDocumentoSinCaso.do", parametros,function (){
								idWindowPantallaActuaciones--;
							});
			} else{
				//codigo para cambiar el estatus del expediente
				idWindowPantallaActuaciones++;
				console.info("ID Tipo Actividad:" + actividad);

				var parametros = 'formaId='+formaID;
				parametros += '&esconderArbol='+1;
				parametros += '&numeroUnicoExpediente='+numeroExpediente;
				parametros += '&idWindowPantallaActuaciones='+idWindowPantallaActuaciones;
				parametros += '&actividadId='+actividad;
				parametros += '&esTransaccional='+true;
					
     			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:true});
    		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?parametros='+parametros+'" width="1140" height="400" />');
    		    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
			}
		}

		/*
		* Funcion que cierra el visor de la solicitud y editor de texto de la parte 
		* de actuaciones
		*/
		function cerrarVentanaDocumento(idWindowPantallaActuaciones){
			
			var pantalla ="iframewindowGenerarDocumento";
			pantalla += idWindowPantallaActuaciones;
			$.closeWindow(pantalla);
		}

		/*
		*Funcion que recarga, bandejas, combos, grids despues de
		*la ejecuacion de la actuacion REPORTE_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL
		*se invoca en generarDocumentoSinCaso.jsp
		*/
		function recargarObjetosDespuesDeGuardarActuaciones(){
			
			var nuevoEstatusSolicitud = '<%=EstatusSolicitud.CERRADA.toString().toLowerCase()%>';

			$('#cbxEstatus').val(nuevoEstatusSolicitud);			
			//Recarga el combo box de actuaciones
			cargaActuaciones(<%=EstatusSolicitud.CERRADA.getValorId()%>);
			//recarga la bandeja
			window.parent.consultaSolicitudesMandamiento(idEstatusSolicitud,window.parent.idTipoSolicitudCumplimientoMJ,window.parent.idsTiposMandamientosConsulta);
		}

		/**
		*Funcion para actualizar el estatus de la solicitud de Mandamiento 
		*/
		function actualizarEstatusSolicitud(estatus){
			$.ajax({
				async: false,
				type: 'POST',
				url: '<%=request.getContextPath()%>/actualizaEstatusSolicitud.do',
				dataType: 'xml',
	        	data: {
	        		solicitudId : idSolicitudMandamiento,
	        		estatus : estatus
	        	},
				success: function(xml){
					//nop
				}
			});
		}

		/*
		*Funcion que valida que no se haya ejecutado la actuacion de
		*oficio de oficio de cumplimento de mandamiento judicial
		*/
		function abrirActuacionSolCumplimientoMandamientoJud(){

			var existenSolicitudes = true;
			
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultarSolCumplimientoMandamientoJud.do',
				data: 'mandamientoJudicialId='+mandamientoJudicialId,
				dataType: 'xml',
				async: false,
				success: function(xml){

					if(parseInt($(xml).find('code').text())==0){
						$(xml).find('solicitudMandamientoDTO').each(function(){
							
							var nombreDestinatario = $(this).find('destinatario').find('nombreFuncionario').text();
							var apPatDestinatario = $(this).find('destinatario').find('apellidoPaternoFuncionario').text();
							var apMatDestinatario = $(this).find('destinatario').find('apellidoMaternoFuncionario').text();

							var nombreCompletoDestinatario = nombreDestinatario+" "+apPatDestinatario+" "+apMatDestinatario;
							
							customAlert("El oficio de cumplimiento de mandamiento judicial ya est&aacute; asignado </br>"
									+"al polic&iacute;a: "+nombreCompletoDestinatario,'<bean:message key="aviso"/>');
							
							existenSolicitudes = false;
							//corta el ciclo for each
							return;
						});
					}else {
						 customAlert("Ocurri&oacute; un error.<br/>"
								 + "por favor contacte al administrador");
						 
						 existenSolicitudes = false;
					}
				}
			});

			return existenSolicitudes;			
		}

/******************************************* TERMINAN FUNCIONES CEJA CUMPLIMIENTO DE MANDAMIENTOS JUDICIALES ***************************************************/


/******************************************* COMIENZAN FUNCIONES CEJA SOLICITUD *************************************************************************/	
	
	function consultaSolicitud(idSolicitudMandamiento){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultaDetalleSolicitudGenerada.do',
			data: 'idSolicitud='+idSolicitudMandamiento,
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('SolicitudDTO').each(function(){
					seteaDatosSolicitud(xml);
				});
			}
		});
	}

	/*
	*Muestra en pantalla los datos de la solicitud de mandamiento
	*en la ceja de detalle de la solicitud del visor.
	*/
	function seteaDatosSolicitud(xml){
		
		//setea los datos del xml en los campos de detalle
		$("#txtNoExpediente").val($(xml).find('SolicitudDTO').find('expedienteDTO').find('numeroExpediente').text());
		$("#txtNoCaso").val($(xml).find('SolicitudDTO').find('casoDTO').find('numeroGeneralCaso').text());
		$("#txtFolio").val($(xml).find('SolicitudDTO').find('folioSolicitud').text());
		$("#cbxEstatus").val($(xml).find('SolicitudDTO').contents('estatus').find('valor').text());
		
		idEstatusSolicitud = $(xml).find('SolicitudDTO').contents('estatus').find('idCampo').text();
	
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

	/******************************************* TERMINAN FUNCIONES CEJA SOLICITUD *************************************************************************/
		
		</script>
	</head>
	<body>
		<!-- Comienza definiciones de Tabs -->
		<div id="tabsAdministrarMandamientoJudicial">
			<ul>
				<li class="tabAdministrarMandamientosJudiciales"><a href="#administrarMandamientoJudicial"><span id="etiquetaCejaAdministrar">Administrar Mandamiento Judicial</span> </a></li>
				<li class="tabDocumentosMandamientosJudiciales"><a href="#tabDocumentosAdminManJud" onclick="inicializaTabDocumentos()"> Documentos </a></li>
				<li class="tabMandamientosDelExpediente"><a href="#tabMandamientosExpedienteAdminManJud" onclick="inicializaTabMandamientosDelExpediente()"> Mandamientos judiciales del expediente </a></li>
				<li class="tabCumplimientoMandamientosJudiciales" style="display: none;"><a href="#tabActuacionesAdminManJud">Cumplimiento de mandamientos judiciales</a></li>
				<li class="tabDetalleDeLaSolicitud" style="display: none;"><a href="#tabDetalleDeLaSolicitud">Detalle de la solicitud</a></li>
			</ul>

		<!-- Comienza cuerpo Administrar mandamiento judicial -->
			<div id="administrarMandamientoJudicial">
				<table width="100%" border="0">
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td width="25%" align="right"><strong>N&uacute;mero de Caso:</strong></td>
						<td width="25%"><input type="text"
							id="numeroCasoAdminManJudTxt" tabindex="1" style="width: 250px;" disabled="disabled"/></td>
						<td width="5%">&nbsp;</td>
						<td>&nbsp;</td>
						<td>
							<input type="button" style="display: none;" value= "Adjuntar Documento" id="btnAdjuntarDocAMandamientoJud" class="btn_Generico" onclick="abreVentanaAdjuntarDocumentoAMandamiento();"/>
						</td>
					</tr>
					<tr>
						<td align="right"><strong>N&uacute;mero de Causa:</strong></td>
						<td><input type="text" id="numeroCausaAdminManJudTxt"
							tabindex="2" style="width: 250px" disabled="disabled"/></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><strong>Tipo de mandamiento
								Judicial</strong>:</td>
						<td><select id="tipoMandamientoAdminManJudCbx" tabindex="3"
							style="width: 255px" disabled="disabled">
						</select></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><strong>Fecha de creaci&oacute;n </strong>:</td>
						<td><input type="text" id="fechaCreacionAdminManJudTxt"
							tabindex="4" style="width: 250px" disabled="disabled"/></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>
							<span id="labelLigaMandamientoParcial" style="display:none;">
								Documento de creaci&oacute;n:
							</span>
						</td>
						<td>
							<span id="ligaMandamientoParcial">
							</span>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="5" align="center">
							<table id="gridAdminManJud" border="0">
							</table>
							<div id="pagerGridAdminManJud"></div>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td align="center"><input type="button" id="guardarAdmin"
							value="Guardar" class="btn_Generico"
							onclick="administrarMandamientosPersona();" /></td>
					</tr>
				</table>
			</div>
			<!-- Termina cuerpo Administrar mandamiento judicial -->
			
			<!-- Comienza cuerpo Documentos mandamiento judicial -->
			<div id="tabDocumentosAdminManJud">
				<jsp:include page="/WEB-INF/paginas/consultarDocumentosDelExpediente.jsp" flush="true"></jsp:include>
			</div>
			<!-- Termina cuerpo Documentos mandamiento judicial -->
			
			<!-- Inicia cuerpo Mandamientos judiciales del expediente -->
			<div id="tabMandamientosExpedienteAdminManJud">
	    		<jsp:include page="/WEB-INF/paginas/consultarMandamientosJudicialesExp.jsp" flush="true"></jsp:include>
			</div>
			<!-- Termina cuerpo Mandamientos judiciales del expediente -->
			
			<!-- Comienza cuerpo Cumplimiento de mandamientos judiciales -->
			<div id="tabActuacionesAdminManJud">
				<fieldset>
					<table border="0">
						<tr>
							<td>
								Actuaciones:
								<select id="cbxActuacionesAdminManJud" onchange="seleccionaActuacionCombo()" style="width:280px">
								</select>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
			<!-- Termina cuerpo Cumplimiento de mandamientos judiciales -->
			
			
			<!-- Comienza cuerpo del detalle de la solicitud -->
			
			<div id="tabDetalleDeLaSolicitud">
				<table width="100%" border="0">
					<tr>
						<td width="50%">
							<fieldset style="height: 100%; min-height: 180px;">
								<legend>Datos de la Solicitud</legend>
								<table width="100%" border="0">
									<tr>
										<td>N&uacute;mero de Expediente: </td>
										<td>
											<input type="text" id="txtNoExpediente" disabled="disabled" size="50"/> 
										</td>
									</tr>
									<tr>
										<td>N&uacute;mero de Caso: </td>
										<td>
											<input type="text" id="txtNoCaso" disabled="disabled" size="50"/> 
										</td>
									</tr>
									<tr>
										<td>Folio : </td>
										<td>
											<input type="text" id="txtFolio" disabled="disabled" size="50"/> 
										</td>
									</tr>
									<tr>
										<td>Estatus : </td>
										<td>
											<input type="text" id="cbxEstatus" disabled="disabled" size="50"/> 
										</td>
									</tr>
									<tr>
										<td>Fecha de solicitud : </td>
										<td>
											<input type="text" id="txtFechaSol" disabled="disabled" size="50"/> 
										</td>
									</tr>
									<tr>
										<td>Tipo de Solicitud : </td>
										<td>
											<input type="text" id="cbxTipoSol" disabled="disabled" size="50"/> 
										</td>
									</tr>
								</table>
							</fieldset>
						</td>
						<td width="50%">
							<fieldset style="height: 100%; min-height: 180px;">
								<legend>Informaci&oacute;n Remitente</legend>
								<table width="100%" border="0">
									<tr>
										<td>Nombre del Servidor <br/>P&uacute;blico :</td>
										<td>
											<input type="text" id="txtNombreServidorPub" disabled="disabled" size="50"/> 
										</td>
									</tr>
									<tr>
										<td>Cargo : </td>
										<td>
											<input type="text" id="txtCargo" disabled="disabled" size="80"/> 
										</td>
									</tr>
									<tr>
										<td>Instituci&oacute;n : </td>
										<td>
											<input type="text" id="txtInstitucion" disabled="disabled" size="50"/> 
										</td>
									</tr>
									<tr>
										<td>&Aacute;rea Administrativa :</td>
										<td>
											<input type="text" id="txtAreaAdmin" disabled="disabled" size="50"/> 
										</td>
									</tr>
								</table>
							</fieldset>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							
						</td>
					</tr>
				</table>
			</div>
			
			<!-- Termina cuerpo del detalle de la solicitud -->
			
		</div>
		<!-- Terminan definiciones de Tabs -->
		
	<!--Form para la consulta del archivo digital-->
	<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
	</form>
	</body>
</html>