<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ page import="mx.gob.segob.nsjp.comun.enums.calidad.Calidades"%>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError"%>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
	<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
	<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
	<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
	
    <%
        	UsuarioDTO usuario = (UsuarioDTO) request.getSession()
        			.getAttribute("KEY_SESSION_USUARIO_FIRMADO");
        	String rolUsuarioSesion = "";
        	Long idRolActivo = 0L;
        	if (usuario != null && usuario.getRolActivo() != null) {
        		rolUsuarioSesion = usuario.getRolActivo();
        		idRolActivo = usuario.getRolACtivo().getRol().getRolId();
        	}
        %>
	
    <title></title>

		<style type="text/css">
			DD P {
				LINE-HEIGHT: 120%
			}
			#iSolicitantePane {
				PADDING-BOTTOM: 0px;
				PADDING-LEFT: 6px;
				WIDTH: 950px;
				PADDING-RIGHT: 0px;
				HEIGHT: 392px;
				PADDING-TOP: 10px;
				background-image: url(<%=request.getContextPath()%>/resources/images/back_datos_gral.png);
				background-repeat: no-repeat;
				border: 0px solid #000;
			}
			#iSolicitantePane DL {
				WIDTH: 1000px; HEIGHT: 400px
			}
			/*acordeon editar*/
			#iSolicitantePane DT {
				TEXT-ALIGN: center;
				PADDING-BOTTOM: 16px;
				PADDING-TOP: 2px;
				PADDING-LEFT: 0px;
				LINE-HEIGHT: 35px;
				TEXT-TRANSFORM: none;
				
				/*acomodo texto*/
				PADDING-RIGHT: 40px;
				FONT-FAMILY: Arial, Helvetica, sans-serif;
				LETTER-SPACING: 2px;
				
				/*distancia persianas*/
				HEIGHT: 25px;
				COLOR: #f5f5f5;
				FONT-SIZE: 12px;
				FONT-WEIGHT: normal;	
				background-image: url(<%=request.getContextPath()%>/resources/images/barra_ver_act.png);
				background-repeat: no-repeat;
				background-position: -24px;
			}
			#iSolicitantePane DT.active {
				BACKGROUND: url(<%=request.getContextPath()%>/resources/images/barra_ver_inact.png);
				background-repeat: no-repeat; 
				COLOR: #f5f5f5; 
				CURSOR: pointer;
				background-position: -24px;
			}
			#iSolicitantePane DT.hover {
				COLOR: #f5f5f5
			}
			#iSolicitantePane DT.hover.active {
				COLOR: #f5f5f5
			}
			/*distancia y color de numero*/
			#iSolicitantePane .slide-number {
				COLOR: #68889b; FONT-WEIGHT: bold;
			}
			#iSolicitantePane .active .slide-number {
				COLOR: #fff
			}
			#iSolicitantePane A {
				COLOR: #58595b;
				font-family: Arial, Helvetica, sans-serif;
			}
			#iSolicitantePane DD IMG {
				MARGIN: 0px; FLOAT: right
			}
			#iSolicitantePane H2 {
				MARGIN-TOP: 10px; FONT-SIZE: 2.5em
			}
			#iSolicitantePane .more {
				DISPLAY: block; PADDING-TOP: 10px
			}
		</style>

    <!--Hoja de estilo para los popups-->
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
    <!--	Hoja de estilo para los gadgets-->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
 	<!--Hoja de estilo para el texto dentro de los acordeones-->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<!--Hoja de estilos para el grid-->
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<!--    Hoja de estilo para easyaccordion-->
    <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
 	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>  
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.zweatherfeed.css" />	
 	

	<!--scripts -->
	<!--Scripts necesarios para el funcionamiento de la JSP-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/timer/jquery.idletimeout.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/timer/jquery.idletimer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/layout_complex.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/reloj.js"></script>
	<!--scripts del gird-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jqgrid/jquery.jqGrid.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.zweatherfeed.js"></script>
    
    
    <!--Scripts necesarios para la ejecucion del editor-->
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>

	<!--Scrip para el idioma del calendario-->
    <script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
    <!--script de jquery UI-->
	<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/prettify.js"></script> --%>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/defensoria/funcionesComunes.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/defensoria/cmpActuacionesExpediente.js"></script>
	
    <script type="text/javascript">

		//Recuperando parametros globales
    	var CONTEXT_ROOT = '<%=request.getContextPath()%>';
    	var contextoPagina = "${pageContext.request.contextPath}";
    	
    	var idRolActivo = <%=idRolActivo%>;
    	
    	var confirmarCierreVentana ='<%=((ConfiguracionDTO) request.getSession().getAttribute(
					GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL))
					.getMostrarMensajeConfirmacionEnDocumento()%>';
    	var idWindowPantallaActuaciones = 0;
    	//Determinar si es un visor de Defensoria - Se oculta el "Guardado Parcial"
    	var ocultarGuardadoParcial = true;
    	
		/*Variables globales de la pagina*/
		//guarda el numero unico del expediente
		var numeroExpedienteCadena="";
		var numeroExpediente= "";
		
		//guarda el numero de expediente Id
		var numeroExpedienteId="";
		//utilizada en la pagina de ingresar datos generales para especificar si se muestra el alias o no
		var verAlias;
		//Utilizado para al inicio de la pagina guardar un nuevo individuo y despues modificar ese mismo individuo
		var idIndividuo=null;

		var idindi; 

		var solicitudEnvioId=null;
		var estatusSolicitudId=null;
		//Parametros para recargar el grid de Solicitudes
		var idInstitucionGrid= null;
		//deprecated
		//var estatusSolicitudGrid= null;
		
		
	
		//inicia onready
		jQuery().ready(	function () {
			jQuery(document).ajaxStop(desbloquearPantalla());			

			//@deprecated
			//oculta boton de generar acta
			//$('#generarActa').hide();
			
			ocultaMuestraTabVisor("tabAsignarDefensor" ,0);
			
			//@deprecated
			//asocia funcion a boton de generar acta
			//$('#generarActa').click(generaOficioActa);
			
			//captura el parametro enviado de la jsp padre asignandolo a su variable 
			numeroExpedienteId='<%=request.getParameter("numeroExpedienteId")%>';
			numeroExpedienteCadena='<%=request.getParameter("numeroExpedienteCadena")%>';	
			expedienteId='<%=request.getParameter("expedienteId")%>';	
			solicitudEnvioId = '<%=request.getParameter("solicitudId")%>';
			idInstitucionGrid = '<%=request.getParameter("idInstitucion")%>';
			
			//deprecated
			//estatusSolicitudGrid = '<%=request.getParameter("estatusSolicitud")%>';
			
			//deprecate Quitar..
			<%-- tipoSolicitudGrid  = '<%=request.getParameter("tipoSolicitud")%>'; --%>
			
			//se generan las tabs del domicilio
			$("#tabs").tabs();
			//Se crean las tabs principales
			$("#tabsprincipalconsulta").tabs();
			//ocultamos el domicilio de notificaciones
			killDomicilioNotificaciones();
			killCoordenadasGeograficas();
			//se genera el acordeon
			$('#iSolicitantePane').easyAccordion({ 
			  autoStart: false, 
			  slideInterval: 3000
			});
			//Guarda el solicitante
			$("#btnGuardar").click(confirmGuardado);
			
			//Modifcar habilita los componentes
			$("#btnModificar").click(habilitarComponentes);
			$("#btnModificar").hide();
			
			//deprecated
			//cargaCatAsesoria();
			consultarDetalleSolicitud();
			
			//Definicion del doble click del combo de actuaciones
			$("#cbxAcciones").dblclick(function(e){
				accionesExpediente();
			});
			
  		});//Fin Ready

  		/**
  		* Funcion que se encarga de consultar el detalle de la solcitud
  		*/
  		//MOD defensorATE, coordinadorDEF, defensor
  		function consultarDetalleSolicitud(){

  			if( solicitudEnvioId != null && solicitudEnvioId != ""){
				//TODO GBP Deprecated <%--  url: '<%=request.getContextPath()%>/obtenerDetalleDefensoria.do?idDocumento='+solicitudEnvioId+'&tipoDocumento=5', --%>
						
				$.ajax({
				    type: 'POST', 
				    data: "", 
				    dataType: 'xml',
				    url: '<%=request.getContextPath()%>/consultarDetalleSolicitud.do?idDocumento='+solicitudEnvioId,
					success: function(xml){
						$(xml).find("solicitud").find("involucradoDTO").each(function(){
							//@deprecated
							//var tipoAsesoria=$(xml).find("solicitud").find('tipoAsesoria').find('idCampo').text();
							//$('#tipoAsesoria').find("option[value='"+tipoAsesoria+"']").attr("selected","selected");
							
							//Datos generales del datosGeneralesView.jsp
							pintaDatosGenerales($(xml).find("solicitud").find("involucradoDTO").first());
							pintaDatosMultiselect($(xml).find("solicitud").find("involucradoDTO").first());
							pintaDatosDomicilio($(xml).find("solicitud").find("involucradoDTO").first());
							
							//Consulta el id del involucrado para consultar Medios de Contacto
							idindi = $(xml).find("solicitud").find("involucradoDTO").find("elementoId").first().text();
							idIndividuo = idindi;
							
							mediosContactoTelefonoActualiza();
							mediosContactoCorreoActualiza();
							desbloqueaCamposMediosDeContactoGrid();
								
							//deprecated
							//$('#editor1').val($(xml).find("solicitud").find("motivo").text());
						});
						estatusSolicitudId = $(xml).find("solicitud").find("estatus").find("idCampo").first().text();
						recargaComponentes(true, true, true);
					}
				});
  			}
  		}

  		/**
  		* Funcion que se encarga de consultar la solicitud por el ID
  		* para recuperar el estatus de la solicitud.
  		*/
  		function consultarSolicitudParaEstatus(){
  			$.ajax({
  				async: false,
			    type: 'POST', 
			    data: "", 
			    dataType: 'xml',
			    url: '<%=request.getContextPath()%>/consultarSolicitudBasico.do?idSolicitud='+solicitudEnvioId,
				success: function(xml){
					var errorCode = $(xml).find('response').find('code').text();
	    			//Si errorCode=0 entonces continuamos con el flujo
	    			if(parseInt(errorCode)==0){
						estatusSolicitudId = $(xml).find("solicitudDTO").find("estatus").find("idCampo").first().text();
	    			}
				}
			});
  			return estatusSolicitudId;
  		}
  		
  		/**
  		* Funcion que se encarga de administrar la recarga de los componentes.
  		* Permite centralizar las invocaciones en este modulo
  		**/
  		function recargaComponentes(bActuaciones, bgridSolicitudes, bAdministraPestania){
  			
  			//consultar el estatus de la solicitud
  			estatusSolicitudId = consultarSolicitudParaEstatus();
  			
  			if( (bgridSolicitudes!=undefined || bgridSolicitudes!='undefinde' || bgridSolicitudes!=null ) 
  					&& bgridSolicitudes ==true ){
  				recargarGridSolicitudesAsesoriaLegal();
  			}
  			if( (bAdministraPestania!=undefined || bAdministraPestania!='undefinde' ||bAdministraPestania!=null ) 
  					&& bAdministraPestania ==true ){
  				administracionPestania();
  			}
  			if( (bActuaciones!=undefined || bActuaciones!='undefinde' ||bActuaciones!=null ) 
  					&& bActuaciones ==true ){
  				//Configuracion de Actuaciones en cmpActuacionesExpediente.js
  				cargaActuaciones(numeroExpedienteId, numeroExpedienteCadena);
  			}
  		}
  		
  		/**
  		* Funcion que Administra las pestanias del visor, 
  		* considerando el estatus de Solicitud y del Rol Activo.
  		*/
  		function administracionPestania(){
  			//Se selecciona el tab inicial antes de ocultar
  			$("#tabsprincipalconsulta").tabs("option","selected", 0);

  			//Ocultar las Tab dinamicas
  			ocultaMuestraTabVisor("tabAsignarDefensor",0);
  			ocultaMuestraTabVisor("tabActuaciones",0);
  			
  			switch (parseInt(estatusSolicitudId)){
  				case <%=EstatusSolicitud.ABIERTA.getValorId()%>:
					switch(parseInt(idRolActivo)){
						case  <%=Roles.DEFENSORATE.getValorId()%>:
								var nombre = trim($('#datosGeneralesCmpNombres').val());
								if(nombre != ""){
									ocultaMuestraTabVisor("tabActuaciones",1);
								}
		  					break;
					}
  					break;
  					
  				case <%=EstatusSolicitud.POR_ASIGNAR.getValorId()%>:
  					switch(parseInt(idRolActivo)){
  						case  <%=Roles.DEFENSORATE.getValorId()%>:
  								ocultaMuestraTabVisor("tabAsignarDefensor",1);
  								deshabilitarComponentes();
  							break;
  						case  <%=Roles.COORDINADORDEF.getValorId()%>:
		  						ocultaMuestraTabVisor("tabAsignarDefensor",1);
		  						//TODO GBP REVISAR CON CU $('#asignarDefensor').attr("disabled","");
		  						deshabilitarComponentes();
  							break;
  					}
  					break;
  				case <%=EstatusSolicitud.ASIGNADO.getValorId()%>:
	  				switch(parseInt(idRolActivo)){
						case  <%=Roles.DEFENSORATE.getValorId()%>:
		  			  			habilitarModoConsulta();
							break;
						case  <%=Roles.COORDINADORDEF.getValorId()%>:
								habilitarModoConsulta();
							break;
					}
  					break;
  				case <%=EstatusSolicitud.EN_PROCESO.getValorId()%>:
	  				switch(parseInt(idRolActivo)){
						case  <%=Roles.COORDINADORDEF.getValorId()%>:
								habilitarModoConsulta();
							break;
						case  <%=Roles.DEFENSOR.getValorId()%>:
								ocultaMuestraTabVisor("tabActuaciones",1);
								deshabilitarComponentes();
							break;
					}
  					break;
  				case <%=EstatusSolicitud.CERRADA.getValorId()%>:
	  				switch(parseInt(idRolActivo)){
						case  <%=Roles.COORDINADORDEF.getValorId()%>:
								habilitarModoConsulta();
							break;
						case  <%=Roles.DEFENSOR.getValorId()%>:
								ocultaMuestraTabVisor("tabActuaciones",1);						
		  			  			habilitarModoConsulta();
							break;
					}
  					break;
  			}
  		}
  		
  		/**
  		* Funcion que se encarga de mostrar los campos en modo de consulta.
  		* no es posible habilitarlos, ni con el boton de modificar
  		*/
  		function habilitarModoConsulta(){
  			$('#asignarDefensor').hide();
  			$('#btnGuardar').hide();
  			$('#btnModificar').hide();
  			
  			desavilitarDatosGenerales();
	  		deshabilitaDatosDomicilio();
			bloqueaCamposMediosDeContactoGrid();
			
			$("img.ui-datepicker-trigger").hide();
  		}
  		
  		/**
  		* Funcion que habilita los componentes para ingresar informaci&oacute;n
  		*/
  		function habilitarComponentes(){
  			$('#btnModificar').hide();
  			$('#btnGuardar').show();
  			
  			avilitarDatosGenerales();
  			avilitarDatosDomicilio();
  			desbloqueaCamposMediosDeContactoGrid();
  		}
  		
  		/**
  		* Funcion que deshabilita los componentes para impedir que 
  		* se ingrese informaci&oacute;n
  		*/
  		function deshabilitarComponentes(){
  			$('#btnModificar').show();
  			$('#btnGuardar').hide();
  			
  			desavilitarDatosGenerales();
	  		deshabilitaDatosDomicilio();
			bloqueaCamposMediosDeContactoGrid();
  		}
  		
		/**
		*	Funcion que se encarga de guardar los datos de la solicitud 
		**/
		function confirmGuardado(){
			customConfirm("&iquest;Desea guardar la informaci&oacute;n capturada?", "Aviso",
				function(){	guardaSolicitante();}
			);
		}
		
		/**
		*	Funcion que se encarga de guardar la informaci&oacute;n de la solicitud 
		**/
		function guardaSolicitante(){
			//VALIDACIONES
			var nombre = trim($('#datosGeneralesCmpNombres').val());
			if(nombre == ""){
				customAlert("Favor de capturar el nombre del solicitante");
				return ;
			}
			
			//id del expediente
		    var parametros = 'idExpediente='+expedienteId;
		    parametros += '&idNumeroExpediente='+numeroExpedienteId;
		  	//CALIDAD_SOLICITANTE Definido en InvolucradoFormUtil
		    parametros += '&calidadDelIndividuo=11'; 
		    parametros +='&esPersonaMoral=false';
		    parametros +='&estaDetenido=false';
		    parametros +='&mismoDomicilioNotificaciones=0';
		    
		    
			//extraemos la descripcion del hecho
			//deprecated
			//parametros +="&motivoAsesoria="+escape($('#editor1').val());
			//Datos generales
			parametros += obtenerParametrosDatosGenerales();
			//recuperamos los datos de lugar
			parametros += obtenerParametrosDomicilio();
			//Datos nacimiento
			parametros += obtenerParametrosDatosNacimiento();
			//Medios de contacto
			parametros += obtenerMedios();
			//modificar Individuo
			parametros += '&idIndividuo='+ idIndividuo;
			//@deprecated tipoAsesoria
			//var tipoAsesoria = $("#tipoAsesoria option:selected");
			//parametros += '&idTipoAsesoria='+ tipoAsesoria.val();
			parametros += '&solicitudId='+solicitudEnvioId;
		
			//@deprecated
			//if(tipoAsesoria.val()==-1){
			//	customAlert("Por favor selecciona un tipo de asesor&iacute;a");
			//}else{
				$.ajax({
			    	  type: 'POST',
			    	  url: '<%=request.getContextPath()%>/guardaSolicitanteAsesoria.do',
			    	  data: parametros,
			    	  dataType: 'xml',
			    	  success: function(xml){ 		
			    		  //TODO GBP Manejo de erro
			 				idIndividuo = $(xml).find("solicitud").find("involucradoDTO").find("elementoId").text();
			 				solicitudEnvioId = $(xml).find("solicitud").find("documentoId").text();
			 				folioSolicitud =  $(xml).find("solicitud").find("folioSolicitud").text();
			 				expedienteId = $(xml).find("solicitud").find("expedienteDTO").find("expedienteId").text();
			 				customAlert("Se guard&oacute; correctamente la Solicitud. Folio: "+ folioSolicitud);
			 				recargaComponentes(true, true, true);
			    	  }
			    	  //TODO GBP SOLICTAR EN EL CU El mensaje de error
		    	});
			//}
		}
		
		/**
		* Funcion que se encarga de recargar el Grid de
		* Solicitudes de Asesoria Legal
		*/
		function recargarGridSolicitudesAsesoriaLegal(){
			idInstitucionGrid = <%=Instituciones.DEF.getValorId()%>;
			
			if(estatusSolicitudId == undefined || estatusSolicitudId == 'undefined' || 
					estatusSolicitudId == null || estatusSolicitudId == 'null' ||
					estatusSolicitudId <= 0 ){
				estatusSolicitudId = <%=EstatusSolicitud.ABIERTA.getValorId()%>;
			}

			try{
				window.parent.cargarGridAsesoria(
						idInstitucionGrid,estatusSolicitudId);
			}catch(e){console.info(e);}
		}

		/**
		* Funcion que consulta los documentos asociados a la solicitud
		*/
		function documentos(){
			//alert("registrarInteresadoAsesoriaLegal.jsp-documentos");
			jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
				url:'<%=request.getContextPath()%>/consultarDocumentosDefensoria.do?tipo=1&idExpedienteop='+numeroExpedienteId, 
				datatype: "xml", 
				colNames:['Tipo de documento','Fecha','Nombre de Documento','Nombre de la actividad','Fecha de la actividad','&Aacute;rea del responsable','Documentos', 'Documento Parcial'], 
				colModel:[ 	{name:'Tipo',index:'tipo', width:155, align:"center"}, 
				           	{name:'Fecha',index:'fecha', width:90, align:"center"},
							{name:'Nombre',index:'nombre', width:255, align:"center"},
							{name:'nombreActividad',index:'nombreActividad', width:400, align:"center"},
							{name:'FechaActividad',index:'fechaActividad', width:170, align:"center"},							
							{name:'area',index:'area', width:200, align:"center",hidden: true},
							{name:'Documento',index:'documento', width:170},
				           	{name:'EsParcial',index:'esParcial', hidden:true}
						],
				pager: jQuery('#pager1'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: false,
				width:800,
				sortname: 'turno',
				viewrecords: true,
				id: 'divgrid',
				ondblClickRow: function(id){
					var retd = jQuery("#gridDetalleFrmPrincipal").jqGrid('getRowData',id);
					var titulo = retd.Nombre;
					if(retd.EsParcial){
						noEsParcial = retd.EsParcial.indexOf('false');
						if(noEsParcial > 0){//"No es parcial"
							consultaPDF(id);
						}
						else{//Es parcial
							idWindowPantallaActuaciones++;
			     			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
			    		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%=request.getContextPath()%>/generarDocumentoSinCaso.do?documentoId='+id+'&numeroUnicoExpediente='+numeroExpedienteCadena+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&esTransaccional='+true+'&esconderArbol='+0+'" width="1140" height="400" />');
			    		    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
						}
					 }
				},
				sortorder: "desc"
			});
			$("#gview_gridDetalleFrmPrincipal .ui-jqgrid-bdiv").css('height', '500px');
			$("#gridDetalleFrmPrincipal").trigger("reloadGrid");
				
		}

		//Variable para controlar el action para consultar pdf's
		var accionConsultarPdf="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do";
		
		function consultaPDF(id){
			document.formaArchivoD.action=accionConsultarPdf+"?documentoId="+id;
			document.formaArchivoD.documentoId.value = id;
			document.formaArchivoD.submit();
		}
		//@deprecated
		/* function generaOficioActa(){
			actualizarEstatusSolicitudAsesoria();
			$("#generarActa").attr("disabled","disabled");
			document.formaDocDirecto.numeroUnicoExpediente.value = numeroExpedienteCadena;
			document.formaDocDirecto.submit();
		} */

		//@deprecated
		<%-- function actualizarEstatusSolicitudAsesoria(){
			var documentoId1 = parent.documentoId;
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/atualizarEstatusSolicitudDefensoria.do?solicitudId='+documentoId1+'',
				dataType: 'xml',
				async: false,
				success: function(xml){
				}
			});
			customAlert("La solicitud de asesor&iacute;a se actualiz&oacute; correctamente");
		} --%>
		
		function imprimeDatosPadre(nombre,apellidoP,apellidoM){}

		/*
		* Funcion que carga los combos de tipo de asesoria
		*/
		//@deprecated
		<%-- function cargaCatAsesoria() {
		  $.ajax({
	    	  type: 'POST',
	    	  url: '<%= request.getContextPath()%>/cargarCatalogoTipoAsesoria.do',
	    	  data: '',
	    	  dataType: 'xml',
	    	  success: function(xml){
	    		  $('#tipoAsesoria').empty();
		    	  $('#tipoAsesoria').append('<option value="-1">- Seleccione -</option>');
	    		  $(xml).find('tipoAsesoria').each(function(){
					$('#tipoAsesoria').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				   });
	    	  }
	    	});
		} --%>
		
		//**************************** FUNCIONES DEL TAB DE DEFENSORES  ****************************//
		
		//Variable para controlar la carga del grid de defensores
		var reloadGridDefensor = false;
	
		/*
		 *Funcion para cargar el grid de defensores por distrito
		 */
		 //TODO GBP Similar al de visorDetalleATDEF
		function cargarGridDefensores(){
			
			if (reloadGridDefensor) {
				$("#tblGridAsignarDefensor").trigger("reloadGrid"); 
			}
			else{
				jQuery("#tblGridAsignarDefensor").jqGrid({ 
					url:'<%=request.getContextPath()%>/SolicitudesDefensorGrid.do', 
					datatype: "xml", 
					colNames:['Nombre'], 
					colModel:[ 
							   {name:'Nombre',index:'nombre', width:550, sortable:false, align:'center'}		           	
							],
					pager: jQuery('#divPagerGridAsignarDefensor'),
					rowNum:10,
					rowList:[10,20,30],
					height:230,
					viewrecords: true,
					sortorder: "desc",
					loadComplete: function(xml){
						var numRegistros = $(xml).find('rows').find('records').text();
						//var arrayIDs=jQuery("#tblGridAsignarDefensor").getDataIDs();
						if(parseInt(numRegistros) <= 0 ){
							customAlert("No existen defensores registrados en el distrito.");
						}
						reloadGridDefensor = true;
						limpiaDatosDefensor();
					},
					onSelectRow: function(rowID) {
							tablaDefensor(rowID);
					}
				});	
			}
		}
		
		/*
		* Funcion para limpiar la tabla con los datos del defensor
		*/
		//TODO GBP Similar al de visorDetalleATDEF
		function limpiaDatosDefensor(){
			
			$("#numeroTelefono").empty();
			$("#correo").empty();
			$("#campoEspecialidadDefensor").val("");
		}
		/*
		* Funcion que consulta los datos del defensor
		*/
		//TODO GBP Similar al de visorDetalleATDEF
		function tablaDefensor(idDefensor){

			var param = '';
			param += 'idDefensor='+ idDefensor;
			
			ejecutaAction("/SolicitudesDefensor", function(xml){
				pintaMedios(xml);
			} , param);		
		}

		/*
		* Funcion que muestra los datos (correo-e,num. tel., especialidad) del defensor 
		* seleccionado en el grid
		*/
		//TODO GBP Similar al de visorDetalleATDEF
		function pintaMedios(xml){
			var esPrimero =true;
			
			//Limpia la tabla con los datos anteriores
			limpiaDatosDefensor();
			
			//Parte del XML para mostrar la especialidad del defensor
			$("#campoEspecialidadDefensor").val($(xml).find('especialidad').find('valor').first().text());
			
			//Parte del XML para mostrar los telefonos	
			$(xml).find('mediosContacto').find('TelefonoDTO').each(function(){
				var codigoPais = $(this).find('codigoPais').text();
				var codigoArea = $(this).find('codigoArea').text();
				var numeroTelefonico = $(this).find('numeroTelefonico').first().text();
				
				if(esPrimero){
					$("#numeroTelefono").append(codigoPais+"-"+codigoArea+"-"+numeroTelefonico);
					esPrimero = false;
				}else{
					$("#numeroTelefono").append(", "+codigoPais+"-"+codigoArea+"-"+numeroTelefonico);
				}
			});		

			esPrimero = true;
			//Parte del XML para mostrar el correo electronico del defensor
			$(xml).find('mediosContacto').find('CorreoElectronicoDTO').each(function(){
				var correoElectronico = $(this).find('direccionElectronica').first().text();

				if(esPrimero){
					$("#correo").append(correoElectronico);
					esPrimero = false;
				}else{
					$("#correo").append(", " + correoElectronico);
				}
			});			
		}
		
		/**
		* Funcion que se encarga de designar Abogado Defensor
		*/
		function designarAbogadoDefensorCoordinador(){
			var idDefensor = jQuery("#tblGridAsignarDefensor").jqGrid('getGridParam','selrow');
			if(idDefensor === undefined || idDefensor == null){
				customAlert("Debe seleccionar un defensor");
				return;
			}
			
			var def = jQuery("#tblGridAsignarDefensor").jqGrid('getRowData',idDefensor);
			var nombre = def.Nombre;
			if(nombre==undefined || nombre=='undefined'){
				nombre="";
			}
			
			customConfirm ("Se asignara al defensor "+nombre, "Aviso",
					//funcion fAceptar
					function(){
						var param = '';
						
						param += 'numSolicitud='+ solicitudEnvioId;
						param += '&idDefensor='+ idDefensor;
						param += '&esAsesoria=true';
						
						ejecutaAction("/DesignarAbogadoDefensorExpediente", function(respuesta){
							var errorCode = $(respuesta).find('response').find('code').text();

							//Si errorCode=0 entonces continuamos con el flujo
			    			if(parseInt(errorCode)==0){
								customAlert("Se asign&oacute; de forma correcta al defensor");
								estatusSolicitudId = $(respuesta).find('avisoDesignacionDTO').find('solicitudDefensor').find('estatus').find('idCampo').text();

								recargaComponentes(true, true, true);
							}
							else{
								customAlert("Ocurri&oacute; un error al asignar el defensor");
							}
						} , param);		
					}
			);
		} 

		/*
		*Funcion para reasignar un abogado defensor
		*/
		function reasignarAbogadoDefensor(){
			
			var idDefensor = jQuery("#tblGridAsignarDefensor").jqGrid('getGridParam','selrow');
			
			if(idDefensor === undefined || idDefensor == null || idDefensor == 'undefined'){
				customAlert("Debe seleccionar un defensor");
				return;
			}
			
			var param = '';
			param += 'numeroExpedienteId='+ numeroExpedienteId;
			param += '&idDefensor='+ idDefensor;
					
			ejecutaAction("/reasignarAbogadoDefensorExpediente", function(respuesta){
									
				if($(respuesta).find('body').find('respuesta').text()== "exito"){
					//Recargamos el gird de expedientes
					//TODO GBP Revisar si se ocupa
					//window.parent.gridExpedientesDeEtapas(idDefensorActual);
					customAlert("Se reasign&oacute; de forma correcta al defensor","Reasignaci&oacute;n de defensor", function(){
						//Cerramos la ventana
						window.parent.closeVentana(idWindow);
					});
					
				}else{
					var codigo = "";
					codigo = $(respuesta).find('code').text();
					
					if(codigo == '<%=CodigoError.NUM_EXP_CON_SOLICITUD_ACTIVA_DE_CARPETA_DE_INVESTIGACION
					.toString()%>'){
						customAlert("No se ha podido reasignar el expediente, ya que cuenta con una solicitud de <br> carpeta de investigaci&oacute;n,abierta o en proceso","Reasignaci&oacute;n de defensor");
					}
					else if(codigo == '<%=CodigoError.MISMO_FUNCIONARIO.toString()%>'){
						customAlert("No se ha podido reasignar el expediente, ya que el expediente pertenece <br> al funcionario seleccionado","Reasignaci&oacute;n de defensor");
					}
					else{
						customAlert("Ocurri&oacute; un error al reasignar el defensor,<br> por favor contacte a su administrador","Reasignaci&oacute;n de defensor");
					}
				}
			} , param, false);		
			
		}

		
		//TODO GBP Pasar al Comun, se encuentra en el menuIntermedio
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
		
    </script>
  </head>
<body>
	
	<table width="90%" >
		<tr align="right">
<!--		@deprecated
 			<td>
				Tipo de Asesor&iacute;a:<select id="tipoAsesoria"><option></option></select>
			</td>
 -->			<td>
 <!-- 				 @deprecated -->
<!-- 				 <input type="button" class="btn_Generico" value="Generar Acta" id="generarActa" /> --> 
				 <input type="button" class="btn_guardar" value="Guardar" id="btnGuardar" />
				 <input type="button" class="btn_modificar" value="Modificar" id="btnModificar" />
			</td>
		</tr>
	</table>
	<div id="tabsprincipalconsulta">
		<ul>
			<li class="tabDatosInteresado"><a href="#tabsconsultaprincipal-1">Datos del Solicitante</a></li>
			<!-- deprecated
			<li id="liMotivoAsesoria"><a href="#tabsconsultaprincipal-2">Motivo de la asesor&iacute;a</a> </li> -->
			<li class="tabAsignarDefensor"><a href="#tabsVisorDefensoria-2" onclick="cargarGridDefensores()">Asignar Defensor</a></li>
			<li class="tabDocumentos"><a href="#tabsconsultaprincipal-3" onclick="documentos()">Documentos</a></li>
			<li class="tabActuaciones"><a href="#tabsconsultaprincipal-4">Actuaciones</a></li>
		</ul>
		<div id="tabsconsultaprincipal-1" class="tabDatosInteresado">
              <!--  ACORDEON CU PROBABLE RESPONSABLE-INGRESAR ORGANIZACION  -->
                <div id="iSolicitantePane">
                  <dl>
                    <dt id="cejaDatosGenerales">Datos Generales</dt>
                      <dd>	
                        <jsp:include page="datosGeneralesView.jsp"/>
                      </dd>
                    <dt id="cejaDomicilio">Domicilio</dt>
                      <dd>
                        <jsp:include page="ingresarDomicilioView.jsp"/>
                      </dd>
                      <dt id="cejaMediosContacto">Medios de Contacto</dt>
                        <dd>
                          <jsp:include page="ingresarMediosContactoView.jsp"/>
                        </dd>
                      </dl>
                    </div>
		</div>
		<!-- deprecated 
		<div id="tabsconsultaprincipal-2">
			<textarea class="jquery_ckeditor" cols="150" id="editor1" name="editor1" rows="10"></textarea>
		</div> -->
		<div id="tabsconsultaprincipal-3" class="tabDocumentos">
			<table id="gridDetalleFrmPrincipal"></table>
			<div id="pager1"></div>
			<form name="frmDoc" action="<%=request.getContextPath()%>/ConsultarContenidoArchivoDigital.do" method="post">
					<input type="hidden" name="documentoId" />
				</form>
			<form name="frmDoc2" action="<%=request.getContextPath()%>/GenerarDocumentoDirecto.do" method="post">
						<input type="hidden" name="documentoId" />
						<input type="hidden" name="numeroUnicoExpediente" />
				</form>
		</div>

		<div id="tabsVisorDefensoria-2" class="tabAsignarDefensor" align="left">
			<table width="100%" border="0">
				<tr height="80%">
					<td width="50%" align="center" valign="top">
						<table id="tblGridAsignarDefensor" align="center"></table>
						<div id="divPagerGridAsignarDefensor"></div> 
					</td>
					<td valign="top">
						<table width="100%" border="0">
							<tr>
								<td colspan="2" align="center" class="btn_Generico">Informaci&oacute;n del Defensor</td>
							</tr>
							<tr>
								<td width="35%">Correo Electr&oacute;nico:</td>
								<td id="correo"></td>
							</tr>
							<tr>
								<td>Numero de Tel&eacute;fono:</td>
								<td id="numeroTelefono"></td>
							</tr>
							<tr>
								<td>Especialidad(es):</td>
								<td>
									<input type="text" id="campoEspecialidadDefensor" class="transpa"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="50%" align="center">
						<input type="button" id="asignarDefensor" value="Seleccionar abogado para asesoria" onClick="designarAbogadoDefensorCoordinador()" class="btn_Generico"/>
						<input type="button" id="reasignarDefensor" value="Seleccionar abogado a reasignar" onClick="reasignarAbogadoDefensor()" class="btn_Generico" style="display:none"/>
					</td>
					<td>
					</td>
				</tr>
			</table>
		</div>

		<div id="tabsconsultaprincipal-4" class="tabActuaciones">
				<table cellpadding="0" cellspacing="0" id="" >
					<tr>
						<td id="tdCbxAccionesTab1">Actuaciones:</td>
						<td id="tdCbxAccionesTab"><select size="20" id="cbxAcciones" style="width:470px">
						</select></td>
					</tr>
				</table>
    </div>
	<div id="dialog-Alert" style="display: none">
		<table>
			<tr>
				<td>
					<span id="divAlertTexto"></span>
				</td>
			</tr>
		</table>
	</div>
	
 </div>
</body>
<%-- @deprecated
<form name="formaDocDirecto" action="<%= request.getContextPath() %>/GenerarDocumentoDirecto.do" method="post">
	<input type="hidden" name="formaId" value="33" %>
	<input type="hidden" name="numeroUnicoExpediente" %>
</form> --%>
  	
<form name="formaArchivoD" action="<%=request.getContextPath()%>/ConsultarContenidoArchivoDigital.do" method="post">
	<input type="hidden" name="documentoId" />
</form>
<script type="text/javascript">
	var config = {					
		toolbar:
		[
			['Source','-','Cut','Copy','Paste','-','Undo','Redo','-','Find','Replace','-','RemoveFormat'],
			['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
			['NumberedList','BulletedList','-','Outdent','Indent'],
			['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
			['Table','HorizontalRule'],
			'/',
			['Styles','Format','Font','FontSize','TextColor','BGColor','Maximize']
		],
		
		height:'250px',
		width:'900px'
	};			
	$('.jquery_ckeditor').ckeditor(config);
</script>
</html>