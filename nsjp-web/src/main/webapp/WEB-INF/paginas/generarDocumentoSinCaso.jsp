<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
<%@ page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.expediente.OrigenExpediente"%>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	
	String idAgenciaUsuario = "-1";
	if (usuario.getFuncionario() != null 
			&& usuario.getFuncionario().getDiscriminante() != null
			&& usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null){
		idAgenciaUsuario = usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId().toString();
	}
	
	String idDistritoUsuario = "-1";
	if (usuario.getFuncionario() != null 
			&& usuario.getFuncionario().getDiscriminante() != null
			&& usuario.getFuncionario().getDiscriminante().getDistrito() != null
			&& usuario.getFuncionario().getDiscriminante().getDistrito().getCatDistritoId() != null){
		idDistritoUsuario = usuario.getFuncionario().getDiscriminante().getDistrito().getCatDistritoId().toString();
	}
	
	Long rolId = 0L;
	Boolean esCoordinadorAmpGeneral = false;

	if (usuario != null && usuario.getRolACtivo() != null
			&& usuario.getRolACtivo().getRol() != null
			&& usuario.getRolACtivo().getRol().getRolId() != null) {
		rolId = usuario.getRolACtivo().getRol().getRolId();
	}

	if (rolId.equals(Roles.COORDINADORAMPGENERAL.getValorId())) {
		esCoordinadorAmpGeneral = true;
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Generar Documento</title>
	
	<!--iframe que crea una nueva peticion para imprimir un PDF-->
	<iframe id="framePdf" src="" width="0" height="0">
	</iframe>
	
	<!--		Hojas de estilos asociadas-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/arbolExpediente.js"></script>
	<jsp:include page="/WEB-INF/paginas/encabezadoDocumentos.jsp"/>
	<script type="text/javascript">
	
	var esCoordinadorAmpGeneral = <%=esCoordinadorAmpGeneral%>;
	var contextoPagina = "${pageContext.request.contextPath}";
	var idWindowPdf = 1;
	var esconderArbol = <%=request.getParameter("esconderArbol")!=null?"true":"false"%>;
	var numeroUnicoExpediente = '<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):""%>';
	var idResolutivo= '<%=request.getParameter("idResolutivo")!=null?request.getParameter("idResolutivo"):""%>';
	var idAudiencia= '<%=request.getParameter("idAudiencia")!=null?request.getParameter("idAudiencia"):""%>';
	var idDistritoUsuario = '<%= idDistritoUsuario %>';
	var idAgenciaUsuario = '<%= idAgenciaUsuario %>';
	
	var enviarAvisoDetencionSSP = <%=request.getParameter("enviarAvisoDetencionSSP")!=null?"true":"false"%>;
	var ocultarGuardadoParcial = <%=request.getParameter("ocultarGuardadoParcial")!=null?"true":"false"%>;
	var ocultarNumeroOficio = <%=request.getParameter("ocultarNumeroOficio")!=null?"true":"false"%>;
	var idInvolucrado= '<%=request.getParameter("idInvolucrado")!=null?request.getParameter("idInvolucrado"):"0"%>';

	/*
	*Parametros para el envio de a coordinador de cosignacion del sistema tradicional
	*/
	var enviarCoordConsignacion = <%=request.getParameter("enviarCoordConsignacion")!=null?"true":"false"%>;
	var cveFuncCoordConsignacion = '<%=request.getParameter("cveFuncionario")!=null?request.getParameter("cveFuncionario"):""%>';
	var cambiarEstatus = <%=request.getParameter("cambiarEstatus")!=null?"true":"false"%>;
	var actividad = '<%=request.getParameter("actividad")!=null?request.getParameter("actividad"):""%>';
	var idNumeroExpediente= '<%=request.getParameter("idNumeroExpediente")!=null?request.getParameter("idNumeroExpediente"):""%>';
	//Variable global que se actualiza en el primer guardado parcial que se lleve acabo para la forma transaccional
	var documentoId = '<%=request.getParameter("documentoId")!=null?request.getParameter("documentoId"):0 %>';
	
	
	/*
	*Parametros necesarios para manejar el cambio de estatus en las Medidas catutelares
	*/
	var esDocumentoDeMedidaCautelar = <%=request.getParameter("esDocumentoDeMedidaCautelar")!=null?"true":"false"%>;
	var medidaCautelarId= '<%=request.getParameter("medidaCautelarId")!=null?request.getParameter("medidaCautelarId"):"0"%>';
	var nuevoEstatusMedidaCautelar= '<%=request.getParameter("nuevoEstatusMedidaCautelar")!=null?request.getParameter("nuevoEstatusMedidaCautelar"):"0"%>';
	var documentoParcialGuardado="";


	/**
	*Parametros necesarios para manejar el cambio de estatus en los Mandamientos Judiciales
	*/
	var esDocumentoDeMandamientoJudicial = <%=request.getParameter("esDocumentoDeMandamientoJudicial")!=null?"true":"false"%>;
	var mandamientoJudicialId = '<%=request.getParameter("mandamientoJudicialId")!=null?request.getParameter("mandamientoJudicialId"):"0"%>';
	var nuevoEstatusMandamientoJudicial= '<%=request.getParameter("nuevoEstatusMandamientoJudicial")!=null?request.getParameter("nuevoEstatusMandamientoJudicial"):"0"%>';

	
	//Permite registrar una actividad al momento de confirmar el envio
	var esTransaccional = '<%=request.getParameter("esTransaccional")!=null?request.getParameter("esTransaccional"):""%>';
	var actividadId = '<%=request.getParameter("actividadId")!=null?request.getParameter("actividadId"):""%>';
	var idFuncionario = '<%=request.getParameter("idFuncionario")!=null?request.getParameter("idFuncionario"):""%>';
        console.log("FUNCIONARIO REQUEST:" + idFuncionario);
	var idWindowPantallaActuaciones = '<%=request.getParameter("idWindowPantallaActuaciones")!=null?request.getParameter("idWindowPantallaActuaciones"):""%>';
	
	/**
	* Parametro para identificar cuando se envia a Controversias (JAR) y mostrar en 
	* el combo de agencias, solo aquellas que tengan un coordinadorJAR
	*/
	var buscarCoordJAR = false;

	/*
	*Parametros necesarios para manejar el acuse de recibo de solicitud de defensor
	*/
	var solicitudDefensorId= '<%=request.getParameter("solicitudDefensorId")!=null?request.getParameter("solicitudDefensorId"):"0"%>';
	
	/*
	*Parametros necesarios para solicitar defensor de defensoria a defensoria
	*/
	var expedienteId = '<%=request.getParameter("expedienteId")!=null?request.getParameter("expedienteId"):""%>';
	var solicitanteId = '<%=request.getParameter("solicitanteId")!=null?request.getParameter("solicitanteId"):""%>';
	var arrayInvolucradosSolDefensor = '<%=request.getParameter("arrayInvolucradosSolDefensor")!=null?request.getParameter("arrayInvolucradosSolDefensor"):""%>'; 
	
	var idSolicitudAnterior = '<%=request.getParameter("idSolicitudAnterior")!=null ? request.getParameter("idSolicitudAnterior") :0 %>';
	var solicitudPeritoId= '<%=request.getParameter("solicitudId")!=null?request.getParameter("solicitudId"):"0"%>';
	var involcradoId = '<%=request.getParameter("involcradoId")!=null ? request.getParameter("involcradoId") :0 %>';
	var confActividadId = '<%=request.getParameter("confActividadId")!=null ? request.getParameter("confActividadId") :0 %>';
	var idFrame ="iframewindowGenerarDocumento" + idWindowPantallaActuaciones;


	//Para solicitudes
	var esEnviarSolicitud =  '<%=request.getParameter("esEnviarSolicitud")!=null ? request.getParameter("esEnviarSolicitud") :false %>';

	//Parametro necesario para documento de cambio de estatus de mandamiento judicial
	var mandamientoId = '<%=request.getParameter("mandamientoId")!=null?request.getParameter("mandamientoId"):""%>';
	
	/*
	*Parametros para  bloqueo de pantalla
	*/
	var CONTEXT_ROOT = '<%= request.getContextPath()%>';
	var idRolActivo = <%=rolId%>;
	
		jQuery().ready(function () {
			jQuery(document).ajaxStop(jQuery.unblockUI);				
			$('#guardarNarraTiva').hide();
			cargaFechaCaptura();
			cargaHoraCaptura();
			$('#btnEnviarSolicitud').click(crearPdf);
			$('#imprimirNarraTiva').click(crearPdf);
			$('#guardadoParcialNarrativa').click(guardadoParcial);
			$('#vistaPreliminar').click(elaborarVistaPreliminar);
			cargarDocumento();
			cargarDatosExpediente();
			cargaDatosEncabezado();
			$("#seccionCbxDistrito").hide();
			$("#etiquetaDistrito").hide();
			$("#etiquetaAgencia").hide();
			$("#seccionCbxAgencia").hide();
			$("#seccionSolicitarDefensor").hide();
			if(enviarAvisoDetencionSSP == true){
				$('#guardadoParcialNarrativa').hide();
				$("#seccionCbxDistrito").show();
				$("#etiquetaDistrito").show();
				$("#seccionSolicitarDefensor").show();
				consultarDistritos('<%=Instituciones.DEF.getValorId()%>');
			}
			
			
			if(ocultarGuardadoParcial == true){
				$('#guardadoParcialNarrativa').hide();
			}

			if(ocultarNumeroOficio == true){
				$('#iNumeroOficio').hide();
				$('#labelNumeroOficio').hide();
			}
			
			$("#btnRegistroDetencion").bind("click",registraDetencion);
			if(actividadId===null || actividadId===undefined || actividadId===""){
				$.ajax({
			    	type: 'POST',
			    	url: '<%=request.getContextPath()%>/cargarActividadGuardadoParcial.do',
			    	async: false,
			    	data: {
			    		documento: documentoId
			    	},
			    	dataType: 'xml',
			    	success: function(xml){
			    		actividadId=$(xml).find('long').text();
			    	}
				});
					
			}
			if (actividadId == '<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS.getValorId() %>' || actividadId == '<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS_SIN_SUSPENDER_EXPEDIENTE.getValorId() %>'){
				$("#seccionCbxDistrito").show();
				$("#etiquetaDistrito").show();
				$("#etiquetaAgencia").show();
				$("#seccionCbxAgencia").show();
				$("#imprimirNarraTiva").hide();
				$("#guardadoParcialNarrativa").hide();
				$("#btnEnviarSolicitud").show();
				buscarCoordJAR = true;
				consultarDistritos();
				$("#cbxDistrito option[value='"+idDistritoUsuario+"']").attr("selected","selected");
				//$("#cbxDistrito").val(idDistritoUsuario);
				consultarAgenciasXDistrito(idDistritoUsuario);
				
				$("#cbxAgencia option[value='"+idAgenciaUsuario+"']").attr("selected","selected");	
			}
			
			consultarTipoTamanioPapel();
		
			if(ocultarGuardadoParcial == false && 
					actividadId == '<%=Actividades.RECIBIR_CANALIZACION_JAR.getValorId() %>'){
				$("#seccionCbxDistrito").show();
				$("#etiquetaDistrito").show();
				$("#etiquetaAgencia").show();
				$("#seccionCbxAgencia").show();
				consultarDistritos();
			}

			if(esEnviarSolicitud == true || esEnviarSolicitud =='true' || 
				actividadId == '<%=Actividades.SOLICITAR_DEFENSOR_PUBLICO.getValorId()%>' ||
				actividadId == '<%=Actividades.GENERAR_ACUSE_DE_RECIBO_DE_SOLICITUD_DE_DEFENSOR_PUBLICO.getValorId()%>' ||
				actividadId == '<%=Actividades.GENERAR_ACUSE_DE_RECIBO_DE_CARPETA_DE_INVESTIGACION.getValorId()%>' ||
				actividadId == '<%=Actividades.GENERAR_ACUSE_DE_RECIBO_DE_CARPETA_DE_INVESTIGACION.getValorId()%>'
			){
				$("#imprimirNarraTiva").hide();
				$("#guardadoParcialNarrativa").hide();
				$("#btnEnviarSolicitud").show();
			}

			if(actividadId == '<%=Actividades.GENERAR_MANDAMIENTO_JUDICIAL.getValorId()%>' || actividadId == '<%=Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL.getValorId()%>'){
				$("#btnEnviarSolicitud").show();
				$("#imprimirNarraTiva").hide();
			}
		});

		function guardadoParcial(){
			if(esTransaccional != null && esTransaccional == 'true'){
				guardadoParcialTransaccional();
			}else{
				guardadoParcialTradicional();
			}
		}
		
		function guardadoParcialTradicional(){
			console.info("OLD ** GUARDADO PARCIAL DE FORMA TRADICIONAL **");
			var recuperaTexto=escape($('.jquery_ckeditor').val());
			var nuevaActividad='<%=request.getParameter("nuevaActividad")%>';
			var numeroOficio=$('#iNumeroOficio').val();
			var documentoIdParcial='<%=request.getParameter("documentoId")%>';
			if(!validaDocumentos()){
				return;
			}
			if(documentoIdParcial=="" || documentoIdParcial==null || documentoIdParcial=="null"){
				documentoIdParcial=documentoParcialGuardado;
			}
			if(documentoIdParcial==null || documentoIdParcial=="null" || documentoIdParcial==""){documentoIdParcial=="";}
			
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/GenerarDocumento.do',
    			data:'parcial=true&formaId=<%=request.getParameter("formaId")%>&numeroUnicoExpediente='+numeroUnicoExpediente+'&audienciaId='+idAudiencia+'&documentoId='+documentoIdParcial+'&texto='+recuperaTexto+'&iNumeroOficio='+numeroOficio+'&seleccionTamanioPapel='+seleccionTamPapel+'&nuevaActividad='+nuevaActividad+'',		    					    	
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

	    		   if(idsDocParcial.length==2){
	    			   $('#iNumeroOficio').val(idsDocParcial[1]);
	    			   documentoParcialGuardado=idsDocParcial[0];
	    			}

					try{window.parent.documentos();}catch(e){}
					pintaChecksTipoAtencion();

					$('#iNumeroOficio').attr('disabled',true);
					mensajeGuardadoParcialExitoso();
		    	},
		    	error: function(xml){		    		
		    	}
			});
		}

		/*
		*Funcion que muestra el mensaje de exito al guardar de forma parcial
		*dependiendo de la actividad que se este, realizando
		*/
		function mensajeGuardadoParcialExitoso(){

			if(actividadId == <%=Actividades.GENERAR_MANDAMIENTO_JUDICIAL.getValorId()%>){
				alertDinamico("Guardado parcial exitoso, consulte el documento "
						+ "en la bandeja Mandamientos judiciales "
						+ "en el estatus de Sin documento de creaci&oacute;n");
			}else if(actividadId == <%=Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL.getValorId()%>){
				alertDinamico("Guardado parcial exitoso, consulte el documento "
						+ "en la bandeja Mandamientos judiciales "
						+ "en el estatus de Sin documento de estatus");
			}else{
				alertDinamico("Guardado parcial exitoso");
			}

		}

		
		function guardadoParcialTransaccional(){
			console.info("NEW ** GUARDADO PARCIAL DE FORMA TRANSACCIONAL **");

			var params = 'parcial=' + true;
			params += '&formaId=' + '<%=request.getParameter("formaId")!=null?request.getParameter("formaId"):"" %>';
			params += '&numeroUnicoExpediente=' + numeroUnicoExpediente;
			params += '&audienciaId=' + idAudiencia;
			params += '&documentoId=' + documentoId;
			params += '&texto=' + escape($('.jquery_ckeditor').val());
			params += '&iNumeroOficio=' + $('#iNumeroOficio').val();
			params += '&seleccionTamanioPapel=' + seleccionTamPapel;			
			params += '&nuevaActividad=' + '<%=request.getParameter("nuevaActividad")!=null?request.getParameter("nuevaActividad"):""%>';
			params += '&actuacion=' + actividadId;
			
			//Se eliminan eventos de los botones de guardado u envio
			deshabilitarBotonesGuardado();
			
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/guardarDocumentoDefinitivamente.do',
			    data: params,
		    	dataType: 'xml',
		    	success: function(xml){
					activarAvisoAlCerrarVentanEnVisorDocumentos(idFrame, false);
	    		   // TODO: Ver que muestre el numero de oficio que se genera de forma automatica
	    			
		    		try{window.parent.documentos();}catch(e){}
					pintaChecksTipoAtencion();
					//$('#iNumeroOficio').attr('disabled',true);
					
	    		    documentoId = parseInt($(xml).find('documentoDTO').find('documentoId').text());
					if(documentoId > 0){
						alertDinamico("Guardado parcial exitoso");
					}else{
						alertDinamico("Ocurri&oacute; un error al generar el documento parcial");
					}
					//Se relacionan eventos de los botones de guardado u envio
					habilitarBotonesGuardado();
					validarActaCircunstanciada();
		    	},
		    	error: function(xml){		    		
		    	}
			});
		}

		function cargarDocumento(){
			parametros ="";
			
			<%for(Object llave:request.getParameterMap().keySet()){%>
				parametros +=  '<%=llave.toString()%>=<%=request.getParameter(llave.toString())%>&';
			<%}%>
			parametros += "mandaFormaEnConsulta=si";

			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/CargarDocumento.do',
		    	data: parametros,
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
		
		/*
		*Funcion que recuepera el texto del editor, y lo envia como una nueva peticion 
		*para que se imprima con formato PDF
		*/
		function crearPdf(){
			if (actividadId == '<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS.getValorId() %>' 
			    || actividadId == '<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS_SIN_SUSPENDER_EXPEDIENTE.getValorId() %>'){
				if(validadDatosSolicitud() == true){
					
					
					var tituloConfirm="";
						var mensajeDeValidacion = '';
					
						if (actividadId == '<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS.getValorId() %>'){
							mensajeDeValidacion = 'Ha aceptado cerrar su expediente, envi&aacute;ndolo al Centro de Justicia Restaurativa.<br/>&#191;Desea Continuar?';						
			   			}else{
			   				mensajeDeValidacion = 'Ha aceptado enviar su expediente al Centro de Justicia Restaurativa.<br/>&#191;Desea Continuar?';
			   			
			   			}
					
					    customConfirm('<span style="font-size:20px">'+ mensajeDeValidacion +'</span>',tituloConfirm,
							function (){
					    		canalizaDeFormaTransaccional();
					    });
					
				
				}
			}
			else if(actividadId == '<%=Actividades.SOLICITAR_DEFENSOR_PUBLICO.getValorId() %>'){
				
				var tituloConfirm="Enviar solicitud de defensor";
				
				var mensajeDeValidacion = 'Ha seleccionado enviar solicitud de denfesor.<br/>&#191;Desea Continuar?';
			    customConfirm('<span style="font-size:20px">'+ mensajeDeValidacion +'</span>',tituloConfirm,
					function (){
						enviarSolicitudDeDefensorTransaccional();
			    });
			}
			else if(actividadId == '<%=Actividades.GENERAR_ACUSE_DE_RECIBO_DE_SOLICITUD_DE_DEFENSOR_PUBLICO.getValorId()%>'){
				
				var tituloConfirm="Enviar acuse";
				
				var mensajeDeValidacion = 'Ha seleccionado enviar el acuse de recibo de solicitud de defensor.<br/>&#191;Desea Continuar?';
				
			    customConfirm('<span style="font-size:20px">'+ mensajeDeValidacion +'</span>',tituloConfirm,
					function (){
			    	enviarAcuseDeReciboDeSolicitudDeDefensor();
			    });
			}
			else if(actividadId == '<%=Actividades.GENERAR_ACUSE_DE_RECIBO_DE_CARPETA_DE_INVESTIGACION.getValorId()%>'){

				var tituloConfirm="Enviar acuse";
				
				var mensajeDeValidacion = 'Ha seleccionado enviar el acuse de recibo de carpeta de investigaci&oacute;n.<br/>&#191;Desea Continuar?';
				
			    customConfirm('<span style="font-size:20px">'+ mensajeDeValidacion +'</span>',tituloConfirm,
					function (){
			    	enviarAcuseDeCarpetaDeInvestigacion();
			    });
			}
			else if( esEnviarSolicitud == true || esEnviarSolicitud =='true' ){
				var tituloConfirm="Enviar solicitud";
				var mensajeDeValidacion = 'Ha seleccionado enviar la solicitud.<br/>&#191;Desea Continuar?';
			    customConfirm('<span style="font-size:20px">'+ mensajeDeValidacion +'</span>',tituloConfirm,
					function (){
				    	enviarSolicitud();
			    });
			}else if(actividadId == '<%=Actividades.GENERAR_MANDAMIENTO_JUDICIAL.getValorId()%>'){

				var tituloConfirm="Enviar mandamiento";
				var mensajeDeValidacion = 'Ha seleccionado guardar y enviar mandamiento judicial<br/>&#191;Desea Continuar?';

			    customConfirm('<span style="font-size:20px">'+ mensajeDeValidacion +'</span>',tituloConfirm,
				function (){
			    	var idMandamientoActual = generaDocumentoFormaTransaccional();
			    	if (parseInt(idMandamientoActual) > 0){
			    		window.parent.enviarMandamiento(idMandamientoActual,false);
					}
		   		});
			}else if(actividadId == '<%=Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL.getValorId()%>'){

				var tituloConfirm="Enviar documento cambio de estatus";
				var mensajeDeValidacion = 'Ha seleccionado guardar y enviar el documento de cambio de estatus<br/>&#191;Desea Continuar?';

			    customConfirm('<span style="font-size:20px">'+ mensajeDeValidacion +'</span>',tituloConfirm,
				function (){
			    	var idDocCambioEstatus = generaDocumentoFormaTransaccional();
			    	if (parseInt(idDocCambioEstatus) > 0){
			    		window.parent.enviarDocumentoCambioEstatus(mandamientoId,idDocCambioEstatus,false);
					}
		   		});
			}
			else if (actividadId === '<%=Actividades.GENERAR_DETERMINACION_DE_ARCHIVO_TEMPORAL_CON_IMPUTADO_DESCONOCIDO.getValorId() %>'){
				var tituloConfirm="Aviso";
				var mensajeDeValidacion = 'Ha aceptado archivar temporalmente su expediente.<br/>&#191;Desea Continuar?';
				customConfirm('<span style="font-size:20px">'+ mensajeDeValidacion +'</span>',tituloConfirm,
						function (){
							generaDocumento();
				    	});
			}
			else{
				customConfirm("&iquest;Est\u00E1 seguro que quiere guardarlo definitivamente?", "Aviso",generaDocumento);
				
			}	
		}
		
		/**
		*
		*/
		function generaDocumento(){
			if(!validaDocumentos()){
				return;
			}			
			//mostramos los divs en el padre de la pesta&ntilde;a de Acciones.
			if(idRolActivo != '<%=Roles.VISITADOR.getValorId()%>' && idRolActivo != '<%=Roles.ENCARGADOCAUSA.getValorId()%>'){
				try{
					if(!window.parent.muestraDIVSCanalizacion()){
						return;
					}
				}catch(e){}
			}
			
			
			
			if(esTransaccional != null && esTransaccional == 'true'){
				generaDocumentoFormaTransaccional();
			}else{
				generaDocumentoFormaTradicional();
			}
                        
                        window.parent.cargaActuaciones(1);
		}
		
		/**
		*
		*/
		function generaDocumentoFormaTradicional(){
			var recuperaTexto=$('.jquery_ckeditor').val();
			
			document.frmDoc.parcial.value = "";
			document.frmDoc.texto.value = recuperaTexto;
			document.frmDoc.audienciaId.value = idAudiencia;
			document.frmDoc.solicitudPeritoId.value = solicitudPeritoId;
			document.frmDoc.iNumeroOficio.value = $("#iNumeroOficio").val();				
			document.frmDoc.seleccionTamanioPapel.value = seleccionTamPapel;
			document.frmDoc.action ="<%= request.getContextPath() %>/GenerarDocumento.do";
			if(document.frmDoc.documentoId.value==""){
				document.frmDoc.documentoId.value = documentoParcialGuardado;
				if(document.frmDoc.documentoId.value == null || document.frmDoc.documentoId.value == "null"){
					document.frmDoc.documentoId.value = "";
				}
			}
			document.frmDoc.submit();
			//para sistema tradicional
			actualizarEstatusExpedienteSistemaTradicional();
			
			//Para medidas cautelares
			if(esDocumentoDeMedidaCautelar == true && parseInt(nuevoEstatusMedidaCautelar) > 0){
				cambiarEstatusMedidaCautelarPJ(medidaCautelarId,nuevoEstatusMedidaCautelar);
			}
			
			//Para mandamiento judicial
			if(esDocumentoDeMandamientoJudicial == true && parseInt(mandamientoJudicialId) > 0){
				cambiarEstatusMandamientoJudicial(mandamientoJudicialId,nuevoEstatusMandamientoJudicial);
			}

			try{window.parent.documentos();}catch(e){}
			try{window.parent.documentoGenerado();}catch(e){}
			pintaChecksTipoAtencion();

			//Funcion para replicar a PG y SSP
			//try{window.parent.documentoGeneradoSincrono($(xml).find("long").first().text());}catch(e){}
			console.info("OLD ** GUARDADO DEFINITIVO DE FORMA TRADICIONAL **");
			customAlert("El documento se ha generado exitosamente.", "", cerrarVentaDocumentoActualizarGrid);
		}
		
		/**
		*
		*/
		function generaDocumentoFormaTransaccional(){
			idDocumento = guardarDefinitivamente();
			
			if(idDocumento > 0){
				try{window.parent.documentos();}catch(e){}
				try{window.parent.recargarActuaciones();}catch(e){}
				try{window.parent.documentoGenerado();}catch(e){}
				pintaChecksTipoAtencion();
				consultaPDF(idDocumento);
				customAlert("El documento se ha generado exitosamente.", "", cerrarVentaDocumentoActualizarGrid);
			}else{
				customAlert("Ocurri&oacute; un error al generar el documento");
			}

			return idDocumento;
		}
		
		/**
		*
		*/
		function canalizaDeFormaTransaccional(){
			console.info("NEW ** CANALIZANDO DE FORMA TRANSACCIONAL **");

			idDocumento = guardarDefinitivamente();
			
			if(idDocumento > 0){
				try{window.parent.documentos();}catch(e){}
				try{window.parent.recargarActuaciones();}catch(e){}
				pintaChecksTipoAtencion();
				consultaPDF(idDocumento);
				customAlert("La canalizaci&oacute;n se realiz&oacute; correctamente.", "", cerrarVentaDocumentoActualizarGrid);
			}else{
				customAlert("Ocurri&oacute; un error al generar el documento");
			}
		}

		
		/*
		*Funcion para enviar la solicitud de defensor
		*/
		//MOD defensorATE
		function enviarSolicitud(){
			idDocumento = guardarDefinitivamente();

			if(idDocumento > 0){
				try{window.parent.documentos();}catch(e){}
				try{window.parent.recargarActuaciones();}catch(e){}
				consultaPDF(idDocumento);
				customAlert("La solicitud se ha enviado exitosamente.","",cerrarVentanaGenerarDocumentoDefensor);
			}else{
				customAlert("Ocurri&oacute; un error al enviar la solicitud.");
			}
		}

		/*
		* Funcion para enviar una solicitud
		*/
		function enviarSolicitudDeDefensorTransaccional(){
			idDocumento = guardarDefinitivamente();

			if(idDocumento > 0){
				try{window.parent.documentos();}catch(e){}
				try{window.parent.recargarActuaciones();}catch(e){}
				consultaPDF(idDocumento);
				customAlert("La solicitud de defensor se realiz&oacute; correctamente.","",cerrarVentanaGenerarDocumentoDefensor);
			}else{
				customAlert("Ocurri&oacute; un error al solicitar defensor");
			}
		}
		
		/*
		*Funcion para enviar el acuse de recibo de la solicitud defensor
		*/
		function enviarAcuseDeReciboDeSolicitudDeDefensor(){
			idDocumento = guardarDefinitivamente();

			if(idDocumento > 0){
				try{window.parent.documentos();}catch(e){}
				try{window.parent.recargarActuaciones();}catch(e){}
				consultaPDF(idDocumento);
				customAlert("El acuse de solicitud de defensor se realiz&oacute; correctamente.","",function(){
					//Aceptar
					window.parent.cerrarPantallaActuaciones(idWindowPantallaActuaciones);
				});
			}else{
				customAlert("Ocurri&oacute; un error al enviar el acuse de la solicitud de defensor");
			}
		}

		
		/*
		*Funcion para enviar el acuse de recibo de la solicitud defensor
		*/
		function enviarAcuseDeCarpetaDeInvestigacion(){
			idDocumento = guardarDefinitivamente();

			if(idDocumento > 0){
				consultaPDF(idDocumento);
				customAlert("El acuse de recibo de carpeta de investigaci&oacute;n se realiz&oacute; correctamente.","",function(){
					//Aceptar
					window.parent.cerrarPantallaActuaciones(idWindowPantallaActuaciones);
				});
			}else{
				customAlert("Ocurri&oacute; un error al intentar enviar el acuse de la carpeta de investigaci&oacute;n");
			}
		}

		function cerrarVentanaGenerarDocumentoDefensor(){
			try{ window.parent.cerrarVentanaGenerarDocumentoDefensor(idWindowPantallaActuaciones);}catch(e){}
		}
		
		/**
		*
		*/
		function guardarDefinitivamente(){
                    console.log("AQUI GUARDAMOS");
			console.info("NEW ** GUARDADO DEFINITIVO DE FORMA TRANSACCIONAL **"); 
			var idDocumento = 0;
			
			var params = 'idNumeroExpediente=' + idNumeroExpediente;
			params += '&actuacion=' + actividadId;
			params += '&cveFuncionarioAsignado=' + idFuncionario;
                        console.log("EL FUNCIONARIO ASIGNADO ES: " + idFuncionario);
			params += '&seleccionTamanioPapel=' + seleccionTamPapel;			
			params += '&formaId=' + '<%=request.getParameter("formaId")!=null?request.getParameter("formaId"):"" %>';
			params += '&documentoId=' + documentoId;
			params += '&tipoOperacion=' + '<%=request.getParameter("tipoOperacion")!=null?request.getParameter("tipoOperacion"):"" %>';
			params += '&nuevaActividad=' + '<%=request.getParameter("nuevaActividad")!=null?request.getParameter("nuevaActividad"):""%>';
			params += '&numeroUnicoExpediente=' + numeroUnicoExpediente;
			params += '&texto=' + escape($('.jquery_ckeditor').val());
			params += '&catDiscriminanteId=' + $("#cbxAgencia option:selected").val();
			params += '&audienciaId=' + idAudiencia;
			params += '&solicitudDefensorId=' + solicitudDefensorId;
			params += '&iNumeroOficio=' + $("#iNumeroOficio").val();;
			//parametros para solicitar defensor desde defensoria
			params += '&expedienteId=' + expedienteId;
			params += '&solicitanteId=' + solicitanteId;
			params += '&solicitudPeritoId='+solicitudPeritoId;
			params += '&arrayInvolucradosSolDefensor=' + arrayInvolucradosSolDefensor;
			params += '&involcradoId=' + involcradoId;
			params += '&confActividadId=' + confActividadId;
			//parametro para documento de cambio de estatus de mandamiento judicial
			params += '&mandamientoId=' + mandamientoId;
			
                        //console.log("LOS PARAMETROS QUE VOY A MANDAR: ");
                        //console.log(params);
			//Se eliminan eventos de los botones de guardado u envio
			deshabilitarBotonesGuardado();
			
			$.ajax({
				type: 'POST',	
			    url: '<%=request.getContextPath()%>/guardarDocumentoDefinitivamente.do',
			    data: params,
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
					activarAvisoAlCerrarVentanEnVisorDocumentos(idFrame, false);
			    	idDocumento = parseInt($(xml).find('documentoDTO').find('documentoId').text());
			    	if (actividadId == "<%=Actividades.ELABORAR_ACUERDO_CANCELACION_INICIO_PROCEDIMIENTO_EJECUCION.getValorId()%>"){
			    		actualizarEstatusSentencia(<%=EstatusExpediente.RECHAZADO.getValorId()%>,idNumeroExpediente, <%=Instituciones.PJ.getValorId()%>);
			    	}else if (actividadId == "<%=Actividades.ELABORAR_ACUERDO_RECEPCION_SENTENCIA.getValorId()%>"){
			    		actualizarEstatusSentencia(<%=EstatusExpediente.ABIERTO.getValorId()%>, idNumeroExpediente, <%=Instituciones.PJ.getValorId()%>);
			    	}else if (idSolicitudAnterior > 0 
			    				&& (actividadId == "<%=Actividades.REGISTRAR_ACUERDO_PROCEDIMIENTO_EJECUCION.getValorId()%>"
			    						|| actividadId == "<%=Actividades.REPORTE_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId()%>")
			    							|| actividadId == "<%=Actividades.SEGUIMIENTO_A_OFICIO_DE_CUMPLIMIENTO_DE_MANDAMIENTO_JUDICIAL.getValorId()%>"){
						
			    		//Registra la relacion del documento con el mandamiento
			    		registrarRelacionDocumento(idSolicitudAnterior, idDocumento);

			    		//Si la actividad es reporte de cumpliento, se debe cambiar el estatus a la solicitud y recargar
			    		//bandejas para reflejar el cambio de estatus 
			    		if(actividadId == "<%=Actividades.REPORTE_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId()%>"){
				    		if (typeof window.parent.actualizarEstatusSolicitud == 'function' ){
				    			window.parent.actualizarEstatusSolicitud(<%=EstatusSolicitud.CERRADA.getValorId()%>);
				    			if (typeof window.parent.recargarObjetosDespuesDeGuardarActuaciones == 'function' ){
				    				window.parent.recargarObjetosDespuesDeGuardarActuaciones();
					    		}
				    		}
					    }
			    	}
			    	//Se relacionan eventos de los botones de guardado u envio
					habilitarBotonesGuardado();
					validarActaCircunstanciada();
				}
			});
	    	return idDocumento;
		}

		/*
		*Funcion que controla el cambio de estatus tanto de los numeros de expediente
		*como del expediente, ademas de llamar a la generacion de numeros de expediente
		*/
		function actualizarEstatusExpedienteSistemaTradicional(){

			//DOCUMENTOS A PARTIR DE ACTUACIONES DEL AGENTE MP ST
			
			if(enviarCoordConsignacion == true && cveFuncCoordConsignacion != ""){
				//Funciones agregadas para sistema tradicional ingresarMenuIntermedioSistemaTradicional.jsp
								
				//Aplica para la actuacion la primera vez que aplica la actuacion cierre de investigacion
				window.parent.generarNumeroExpedienteConsignacion();
			}
			
			if (cambiarEstatus == true){
				//Funciones agregadas para sistema tradicional ingresarMenuIntermedioSistemaTradicional.jsp
				
				//Funcion que es invocada cuando el agenteMpSt requiere cambiar el estatus del expediente del consignador
				//Aplica cuando ejecuta nuevamente la actuacion de cierre de investigacion 
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.COORDINADOR_CONSIGNACION.getValorId()%>,<%=Roles.CONSIGNADOR.getValorId()%>","<%=EstatusExpediente.EN_PROCESO.getValorId()%>","");
				//Cambiamos tambien el estatus del expediente del agenteMpSt
				window.parent.cambiaEstatusNumeroExpediente(<%=EstatusExpediente.CONSIGNADO.getValorId()%>);
				window.parent.habilitaDeshabilitaComboActuaciones();
			}

			//DOCUMENTOS A PARTIR DE ACTUACIONES DEL CONSIGNADOR
			
			if( actividad =='<%= Actividades.GENERAR_OFICIO_DE_CONSIGNACION.getValorId() %>'){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.COORDINADOR_CONSIGNACION.getValorId()%>,<%=Roles.CONSIGNADOR.getValorId()%>,<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.CONSIGNADO.getValorId()%>","<%=EstatusExpediente.CONSIGNADO.getValorId()%>");
				window.parent.habilitaDeshabilitaComboActuaciones();
			}
			if( actividad =='<%= Actividades.DETERMINAR_NEAP.getValorId() %>'){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.COORDINADOR_CONSIGNACION.getValorId()%>,<%=Roles.CONSIGNADOR.getValorId()%>,<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.NEAP.getValorId()%>","<%=EstatusExpediente.NEAP.getValorId()%>");
				window.parent.habilitaDeshabilitaComboActuaciones();
			}			
			if( actividad =='<%= Actividades.DETERMINAR_LA_FALTA_DE_DILIGENCIAS.getValorId() %>' ||
			    actividad=='<%= Actividades.DETERMINAR_LA_FALTA_DE_RESPUESTAS_A_PARTICULARES.getValorId() %>'){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.COORDINADOR_CONSIGNACION.getValorId()%>,<%=Roles.CONSIGNADOR.getValorId()%>,<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.DEVUELTO.getValorId()%>","<%=EstatusExpediente.DEVUELTO.getValorId()%>");
				window.parent.habilitaDeshabilitaComboActuaciones();
			}
			if( actividad=='<%= Actividades.COMPARECENCIA_PARA_OTORGAR_PERDON_18_A.getValorId() %>'){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.CONCLUIDO_POR_PERDON.getValorId()%>","");
				window.parent.habilitaDeshabilitaComboActuaciones();
			}
			if( actividad=='<%= Actividades.ARCHIVO_DEFINITIVO_18_B.getValorId() %>' ){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.ARCHIVO_DEFINITIVO.getValorId()%>","");
				window.parent.habilitaDeshabilitaComboActuaciones();
			} 
			if( actividad=='<%= Actividades.OFICIO_PARA_CANALIZAR_A_MEDIACION.getValorId() %>' ){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.MEDIACION_CONCILIACION.getValorId()%>","");
				window.parent.habilitaDeshabilitaComboActuaciones();
			}
			
		}
		
		function cerrarVentaDocumentoActualizarGrid(){
			if (typeof window.parent.documentos == 'function' ){
				window.parent.documentos();
			}
			if (typeof window.parent.cerrarVentanaDocumento == 'function' ){
				window.parent.cerrarVentanaDocumento(idWindowPantallaActuaciones);
			}
		}
		
		function pintaChecksTipoAtencion(){
			pintaTiposAtencion='<%=request.getParameter("pintaTiposAtencion")%>';
			if(pintaTiposAtencion != null && pintaTiposAtencion>0 ){
				try{window.parent.pintaCheckTipoAtencion(pintaTiposAtencion);}catch(e){}
			}
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

		/*
		 *Funcion que consulta Distritos
		 */
		function consultarDistritos(institucionDestino){
			var parametros = '';
			if (institucionDestino != undefined && institucionDestino != ''){
				parametros = 'institucionId='+institucionDestino;
			}
			
			$.ajax({
				type: 'POST',
			    url: '<%=request.getContextPath()%>/consultarDistritos.do',
			    data: parametros,
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
				    	$(xml).find('listaCatalogo').find('catDistritoDTO').each(function(){
							$('#cbxDistrito').append('<option value="' + $(this).find('catDistritoId').text() + '">' + $(this).find('claveDistrito').text()+"-"+ $(this).find('nombreDist').text() + '</option>');
						});					
				}
			});
		}
		
		/*
		 *Funcion que consulta Agencias dependiento del Distrito seleccionado
		 */
		function consultarAgenciasXDistrito(distritoId){
			$('#cbxAgencia').empty();
			$('#cbxAgencia').append('<option value="0">-Seleccione-</option>');
			
			var params = 'distritoId='+distritoId;
			if(buscarCoordJAR){
				params += '&buscarCoordJAR='+buscarCoordJAR;
			}
			
			$.ajax({
				type: 'POST',
			    url: '<%=request.getContextPath()%>/consultarDiscriminantesXDistrito.do?',
			    data: params,
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
				    	var contAgencias=0;
				    	$(xml).find('CatDiscriminanteDTO').find('catDiscriminanteDTO').each(function(){
							$('#cbxAgencia').append('<option value="' + $(this).find('catDiscriminanteId').text() + '">' + $(this).find('clave').text()+"-"+ $(this).find('nombre').text() + '</option>');
							contAgencias++;
						});
						if(contAgencias == 0){							
							alertDinamico('<bean:message key="mensajeAgenciaValidarDistrito"/>');
						}
				}
			});
		}
		
		/*
		*Actualiza el combo de agencias dependiendo de la seleccion del distrito
		*/
		function actualizaComboAgencias(){
			distritoId = parseInt($("#cbxDistrito option:selected").val());
			if(distritoId > 0)
				consultarAgenciasXDistrito(distritoId);
			else{
				$('#cbxAgencia').empty();
				$('#cbxAgencia').append('<option value="0">-Seleccione-</option>');
			}
		}
		
		/*
		*Valida que existan coordinadores AMP en la agencia seleccionada
		*/
		function validadDatosSolicitud(){
			var idRolJAR = '<%=Roles.COORDINADORJAR.getValorId()%>';
			
			var totalDestinartario = 0;
			//buscar si existen Coordinadores en el &aacute;rea seleccionada
			var params = 'catDiscriminanteId=' + $("#cbxAgencia option:selected").val();
			params += '&idRol=' + idRolJAR;
			
			$.ajax({
				type: 'POST',
			    url: '<%=request.getContextPath()%>/consultarFuncionariosCoordinadoresXDicriminante.do',
			    data: params,
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
			    	$(xml).find('funcionarioDTO').each(function(){
			    		totalDestinartario++;
					});					
				}
			});
			
			if(parseInt(totalDestinartario) > 0 ){
				return true;
			}else{
				customAlert('<bean:message key="mensajeAgenciaValidarSolicitud"/>', '<bean:message key="tituloMensajeAgenciaValidarSolicitud"/>');
				return false;
			}			
		}
		
		function registraDetencion(){
 		    idDistrito = $("#cbxDistrito").val();
			if(idDistrito != "-1"){
				$.ajax({								
			    	  type: 'POST',
			    	  url: '<%= request.getContextPath()%>/enviarAvisosDetencionIPH.do?idInvolucrado='+idInvolucrado+'&numeroExpediente='+numeroUnicoExpediente+'&idDistrito='+idDistrito+'',
			    	  data: '',				
			    	  dataType: 'xml',
			    	  success: function(xml){
			  			customAlert("Registro realizado correctamente.");
			    	  }
			    	});			
			}else{
				customAlert("Debe de seleccionar un distrito.");
			}

		}
		
		/*
		*Funcion que controla el cambio de estatus sobre las madidas cautelares
		*/
		function controlarCambioDeEstatusDeMedidaCautelar(){

			//DOCUMENTOS A PARTIR DE ACTUACIONES DEL sspEPRS
			if( actividad=='<%= Actividades.ARCHIVO_DEFINITIVO_18_B.getValorId() %>' ){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.ARCHIVO_DEFINITIVO.getValorId()%>","");
				window.parent.habilitaDeshabilitaComboActuaciones();
			} 
			if( actividad=='<%= Actividades.OFICIO_PARA_CANALIZAR_A_MEDIACION.getValorId() %>' ){
				window.parent.actualizarEstatusNumerosDeExpedientesPorRolST("<%=Roles.AGENTEMPSISTRAD.getValorId()%>","<%=EstatusExpediente.MEDIACION_CONCILIACION.getValorId()%>","");
				window.parent.habilitaDeshabilitaComboActuaciones();
			}
			
		}
		
		
		
		/*
		*Funcion que controla el estatus de una medida cautelar
		*/
		function cambiarEstatusMedidaCautelar(medidaCautelarId, nuevoEstatusMedidaCautelar){
			window.parent.cambiarEstatusMedidaCautelar(medidaCautelarId,nuevoEstatusMedidaCautelar);
			//Permite actualizar la variable del estatus inicial al actual
			estatus = nuevoEstatusMedidaCautelar; 
			window.parent.estatus = nuevoEstatusMedidaCautelar;
			//Actualizar el grid del estatus incial
			window.parent.cargaGrid(estatus);
			//Cambia vista de la lista de Actuaciones
			window.parent.mostrarActuaciones();
			//Ocultar la pestania de actuaciones dependiendo del estatus de la MC.
			window.parent.ocultarPestaniaActuaciones();
		}

		/**
		*Funcion exclusiva de PJ para cambiar el estatus de medida cautelar, la funcion de arriba
		*ya se encontraba aqui, y es para ssp, en especial para el usuario sspEPRS, esta funcion
		*se llama en la jsp ingresarMedidasCautelaresPJENC.jsp, llamada en el usuario encargadoCausa
		*/
		function cambiarEstatusMedidaCautelarPJ(medidaCautelarId, nuevoEstatusMedidaCautelar){
			window.parent.cambiarEstatusMedidaCautelar(medidaCautelarId, nuevoEstatusMedidaCautelar);

		}
		
		/*
		*Funcion que permite actualizar el estatus del mandamiento judicial
		*/
		function cambiarEstatusMandamientoJudicial(mandamientoJudicialId,nuevoEstatusMandamientoJudicial){
			//LLama a la funcion en el padre, para actualizar el estatus del mandamiento judicial
			window.parent.cambiarEstatusMandamientoJudicial(mandamientoJudicialId, nuevoEstatusMandamientoJudicial);
		}
		


		//Variable para controlar el action para consultar pdf's
		var accionConsultarPdf= contextoPagina +"/ConsultarContenidoArchivoDigital.do";
		/* Precondicion:
		 * - contextoPagina
		 * - Definir la forma frmConsultaDoc
		 */

		function consultaPDF(id){
			document.frmConsultaDoc.action=accionConsultarPdf+"?documentoId="+id;
			document.frmConsultaDoc.submit();
		}
		
		/*
		* Funci&oacute;n que lleva a cabo la actualizacion del estatus de una sentencia en una institucion externa.
		*/
		function actualizarEstatusSentencia(idEstatusExpediente, idNumeroExpediente, idInstitucion){
			var params = "idNumeroExpediente="+idNumeroExpediente+"&idEstatusExpediente="+idEstatusExpediente+"&idInstitucion="+idInstitucion;
			$.ajax({
				type: 'POST',	
			    url: '<%=request.getContextPath()%>/actualizarEstatusSentencia.do',
			    data: params,
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
					
				}
			});
		}
		
		/*
		* Funci&oacute;n que lleva a cabo el registro de dos documentos relacionados.
		*/
		function registrarRelacionDocumento(idDocPrincipal, idDocRelacionado){
			var params = "idDocPrincipal="+idDocPrincipal+"&idDocRelacionado="+idDocRelacionado;
			$.ajax({
				type: 'POST',	
			    url: '<%=request.getContextPath()%>/registrarRelacionDocumento.do',
			    data: params,
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
					
				}
			});
		}
		
		function validarActaCircunstanciada(){
			if(idRolActivo == '<%=Roles.ADMINAT.getValorId()%>' && 
					actividadId == '<%=Actividades.GENERAR_DENUNCIA_EN_ATP.getValorId()%>'){
				window.parent.validarTipoActividadEnExpediente();
			}
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
	<table align="center" border="0" width="820px" height="50%">
		<tr>
			<td colspan="5">
				<ul class="toolbar">
					<div id="menu_head">
						<li id="guardadoParcialNarrativa" class="first"><span></span>Guardado Parcial</li>
						<li id="imprimirNarraTiva"><span></span>Guardado Definitivo</li>
						<li id="btnEnviarSolicitud" style="display:none"><span></span>Enviar</li>
						<li id="vistaPreliminar"><span></span>Vista Preliminar</li>
						<li id="seccionCbxTamanioPapel"><span></span>Tama&ntilde;o de Papel
							<select name="cbxTamanioPapel" id="cbxTamanioPapel" onchange="recuperarTamanioPapel()" style=" border:0; background-color:#EEEEEE;">
		    				</select>
		    			</li>
					</div>
				</ul>
			</td>
	  	</tr>	
		<tr>
			<td width="20%">Nombre Servidor P&uacute;blico:</td>
			<td width=""><input type="text" title="Nombre del Servidor Publico" size="40" id="iNombreServidorPublico" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			<td width="">Hora de Elaboraci&oacute;n:</td>
			<td width=""><input type="text" id="idHoraDate" disabled="disabled" size="30" style=" border:0; background-color:#EEEEEE;"/></td>
		</tr>
		<tr>
			<td width="20%">Cargo:</td>
			<td width=""><input type="text" title="Cargo" size="40" id="iPuesto" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			<td width="">Fecha de Elaboraci&oacute;n:</td>
			<td width=""><input type="text" id="generarDocumentoCmpFechaIngreso" name="generarDocumentoCmpFechaIngreso" disabled="disabled" size="30" style=" border:0; background-color:#EEEEEE;"/></td>
		</tr>
		<tr>
			<td>&Aacute;rea Administrativa:</td>
			<td><input type="text" title="Area Administrativa" size="40" id="iAreaAdministrativa" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			<td>Estado:</td>
			<td><input type="text" title="Estado" size="30" id="iEstado" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>			
		</tr>
		<tr>
			<td width="20%"><span id="labelNumeroOficio">N&uacute;mero de Oficio:</span></td>
			<td width=""><input type="text" title="Numero de Oficio" size="40" id="iNumeroOficio" onkeypress="return letrasNumero(event);" maxlength="20"/></td>
		</tr>
		<tr id="trDistritos">
			<td id="etiquetaDistrito">Distrito:</td>
			<td id="seccionCbxDistrito">
				<select name="cbxDistrito" id="cbxDistrito" onchange="actualizaComboAgencias()" style="width: 180px;">
			  		<option value="-1">- Seleccione -</option>
		    	</select>
	    	</td>
			<td id="etiquetaAgencia"><bean:message key="agencia"/>:</td>
			<td id="seccionCbxAgencia">
				<select id="cbxAgencia">
					<option  value="-1" selected="selected">-Seleccione-</option>
				</select>
			</td>
		</tr>
		<tr id="seccionSolicitarDefensor">
			<td align="center" colspan="4">
					<input type=button class="ui-button ui-corner-all ui-widget" id="btnRegistroDetencion" value="Solicitar Defensor">
			</td>
		</tr>
	</table>
	
	
		
	<table align="center" width="1024px" >
		<tr>
			
			<td width="300px" valign="top" id="tdArbolExp">
				<h3><a href="#" id="idExpedientes">Elementos del Expediente</a></h3>
				<div style="height: 800px; 
						width: 300px;
						overflow: auto;
						border: 1px solid #666;
						padding: 0px;" id="marcoArbolExpediente">
						<ul id="accordionDatosExpediente" class="filetree">
						</ul>
			</div>
			</td>
			<td width="1000px" valign="top" align="center">
				<div style="margin-top: 0; margin-bottom: auto; vertical-align: top;margin-right: auto; margin-left: auto"  >
				<jsp:include page="/WEB-INF/paginas/ingresarNarrativaView.jsp" flush="true"></jsp:include>
				</div>
			</td>
		</tr>
	</table>
	
	<form name="frmDoc" action="<%= request.getContextPath() %>/GenerarDocumento.do" method="post">
		<input type="hidden" name="texto" value=""/>
		<input type="hidden" name="parcial" value=""/>
		<input type="hidden" name="formaId" value="<%=request.getParameter("formaId")!=null?request.getParameter("formaId"):"" %>"/>
		<input type="hidden" name="numeroUnicoExpediente" value="<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):"" %>"/>
		<input type="hidden" name="documentoId" value="<%=request.getParameter("documentoId")!=null?request.getParameter("documentoId"):"" %>"/>
		<input type="hidden" name="tipoOperacion" value="<%=request.getParameter("tipoOperacion")!=null?request.getParameter("tipoOperacion"):"" %>"/>
		<input type="hidden" name="estatusSolicitud" value="<%=request.getParameter("estatusSolicitud")!=null?request.getParameter("estatusSolicitud"):"" %>"/>
		<input type="hidden" name="idResolutivo" value="<%=request.getParameter("idResolutivo")!=null?request.getParameter("idResolutivo"):"" %>"/>
		<input type="hidden" name="iNumeroOficio" value="<%=request.getParameter("iNumeroOficio")!=null?request.getParameter("iNumeroOficio"):"" %>"/>
		<input type="hidden" name="seleccionTamanioPapel" value="<%=request.getParameter("seleccionTamanioPapel")!=null?request.getParameter("seleccionTamanioPapel"):"" %>"/>
		<input type="hidden" name="audienciaId" value="<%=request.getParameter("idAudiencia")!=null?request.getParameter("idAudiencia"):"" %>"/>					                               
		<input type="hidden" name="nuevaActividad" value="<%=request.getParameter("nuevaActividad")!=null?request.getParameter("nuevaActividad"):""%>"/>
		<input type="hidden" name="solicitudPeritoId" value="<%=request.getParameter("solicitudPeritoId")!=null?request.getParameter("solicitudPeritoId"):"" %>"/>
	</form>
	
	
	<form name="frmConsultaDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
		<input type="hidden" name="documentoId" />
	</form>
	
</body>
</html>