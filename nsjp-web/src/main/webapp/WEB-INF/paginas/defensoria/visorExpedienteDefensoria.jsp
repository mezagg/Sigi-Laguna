<%@page import="mx.gob.segob.nsjp.comun.enums.involucrado.SituacionJuridica"%>

<%@page import="mx.gob.segob.nsjp.comun.enums.objeto.Objetos"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.calidad.Calidades"%>
<%@page import="mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@page import=" mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.catalogo.EntidadFederativa" %>


<%@page import="java.util.List"%>

<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<!--Hoja de estilos de Layout-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />

	<!--Hoja de estilos ultrasist-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css"/>
    <%--  link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.richtext.css"/ --%>
    <%-- <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.alerts.css"/> --%>
   	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
    <%-- <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.colorpicker.css"/> --%>
   	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/cssGrid/ui.jqgrid.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/style.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/prettify.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css"/>
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/jquery.maskedinput.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/mktSignup.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>	
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.timeentry.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ingresarMediosContacto.js"></script>
    
   	<script type="text/javascript" src="<%= request.getContextPath()%>/js/prettify.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery.multiselect.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.tablednd.js"></script>
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/defensoria/funcionesComunes.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/defensoria/cmpActuacionesExpediente.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/defensoria/cmpDocumentosExpediente.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/defensoria/cmpInvolucradosExpediente.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/defensoria/cmpDiligenciasExpediente.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>	
  	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
      
	<style type="text/css">
	.texto{
		width: 225px; 
		border: 0; 
		background: #DDD;
	}
	.transpa {
		background-color: transparent;
		border: 0;
	}
			DD P {
				LINE-HEIGHT: 120%
			}
			#cedulaIdentificacion {
				PADDING-BOTTOM: 0px;
				PADDING-LEFT: 2px;
				WIDTH: 1030px;
				PADDING-RIGHT: 0px;
				HEIGHT: 380px;
				PADDING-TOP: 10px;
				background-image: url(<%=request.getContextPath()%>/resources/images/back_datos_gral.png);
				background-repeat: no-repeat;
				border: 0px solid #000;
			}
			#cedulaIdentificacion DL {
				WIDTH: 1030px; HEIGHT: 390px
			}
			/*acordeon editar*/
			#cedulaIdentificacion DT {
				TEXT-ALIGN: right;
				PADDING-BOTTOM: 16px;
				PADDING-TOP: 2px;
				PADDING-LEFT: 0px;
				LINE-HEIGHT: 35px;
				TEXT-TRANSFORM: none;	
				/*acomodo texto*/PADDING-RIGHT: 40px;
				FONT-FAMILY: Arial, Helvetica, sans-serif;
				LETTER-SPACING: 1px;
				/*distancia persianas*/HEIGHT: 25px;
				COLOR: #f5f5f5;
				FONT-SIZE: 12px;
				FONT-WEIGHT: normal;	
				background-image: url(<%=request.getContextPath()%>/resources/images/barra_ver_act.png);
				background-repeat: no-repeat;
				background-position: 28px;
			}
			#cedulaIdentificacion DT.active {
				BACKGROUND: url(<%=request.getContextPath()%>/resources/images/barra_ver_inact.png);
				background-repeat: no-repeat; 
				COLOR: #f5f5f5; 
				CURSOR: pointer;
				background-position: 30px;
			}
			#cedulaIdentificacion DT.hover {
				COLOR: #f5f5f5
			}
			#cedulaIdentificacion DT.hover.active {
				COLOR: #f5f5f5
			}
			#cedulaIdentificacion DD {
				BORDER-BOTTOM: #ffffff 0px solid; 
				BORDER-LEFT: 0px; 
				PADDING-BOTTOM: 1px; 
				PADDING-LEFT: 1px; 
				PADDING-RIGHT: 1px; 
				/*BACKGROUND: url(<%=request.getContextPath()%>/images/jquery/plugins/easyaccordion/slide.jpg) repeat-x left bottom;*/ 
				BORDER-TOP: #ffffff 0px solid; 
				MARGIN-RIGHT: 1px; 
				BORDER-RIGHT: #ffffff 0px solid; 
				PADDING-TOP: 1px
			}
			/*distancia y color de numero*/
			#cedulaIdentificacion .slide-number {
				COLOR: #68889b; FONT-WEIGHT: bold; LEFT: 30px
			}
			#cedulaIdentificacion .active .slide-number {
				COLOR: #fff
			}
			#cedulaIdentificacion A {
				COLOR: #58595b;
				font-family: Arial, Helvetica, sans-serif;
			}
			#cedulaIdentificacion DD IMG {
				MARGIN: 0px; FLOAT: right
			}
			#cedulaIdentificacion H2 {
				MARGIN-TOP: 10px; FONT-SIZE: 2.5em
			}
			#cedulaIdentificacion .more {
				DISPLAY: block; PADDING-TOP: 10px
			}
			
		<!--ESTILOS PARA LAS TABS BUTTON, POR ESTAR EN LA PARTE DE ABAJO-->
		#tabs { height: 670px; }
		.tabs-bottom { position: relative; }
		.tabs-bottom .ui-tabs-panel { height: 500px; overflow: auto; }
		.tabs-bottom .ui-tabs-nav { position: absolute !important; left: 0; bottom: 0; right:0; padding: 0 0.2em 0.2em 0; }
		.tabs-bottom .ui-tabs-nav li { margin-top: -2px !important; margin-bottom: 1px !important; border-top: none; border-bottom-width: 1px; }
		.ui-tabs-selected { margin-top: -3px !important; }
		.tabEstilo  { height: 350px; overflow: auto; }
	/* 
		#tabs { height: 100% } 
		.tabs-bottom { position: relative; } 
		.tabs-bottom .ui-tabs-panel { height: 600px; overflow: visible; } 
		.tabs-bottom .ui-tabs-nav { position: absolute !important; left: 0; bottom: 0; right:0; padding: 0 0.2em 0.2em 0; } 
		.tabs-bottom .ui-tabs-nav li { margin-top: -2px !important; margin-bottom: 1px !important; border-top: none; border-bottom-width: 1px; }
		.ui-tabs-selected { margin-top: -3px !important; }
		.tabEstilo  { height: 350px; overflow: auto; }
	 */	
	</style>

    <script>
<% 
	ExpedienteDTO exp = (ExpedienteDTO)request.getAttribute("expediente");

	long etapaId = 0L;
	
	if(exp.getEtapa()!=null && exp.getEtapa().getIdCampo()!=null){
		etapaId = exp.getEtapa().getIdCampo();
	}
	if(etapaId==0L && exp.getCatEtapaDTO()!=null 
			&& exp.getCatEtapaDTO().getCatEtapaValor()!=null 
			&& exp.getCatEtapaDTO().getCatEtapaValor().getIdCampo()!=null){
		etapaId=exp.getCatEtapaDTO().getCatEtapaValor().getIdCampo();
	} 
	
	long idExpediente = (Long)(request.getSession().getAttribute("expedienteId"));
	long idExpedienteOK = (Long)(request.getSession().getAttribute("expedienteId"));
	long numeroExpedienteIdOK = exp.getNumeroExpedienteId();
	
	long idNumeroExpediente = exp.getNumeroExpedienteId();

	Long estatusExp = 0L;

	if(exp!=null && exp.getEstatusNumeroExpediente()!=null &&
	   exp.getEstatusNumeroExpediente().getIdCampo()!=null){
		estatusExp = exp.getEstatusNumeroExpediente().getIdCampo();
	}

	String numeroExpediente = exp.getNumeroExpediente();
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	ConfiguracionDTO cfg=(ConfiguracionDTO) request.getSession().getAttribute("KEY_SESSION_CONFIGURACION_GLOBAL");
	String entidadFederativa=cfg.getEntidadFederativaDespliegueId().toString();
	FuncionarioDTO funcionario = usuario.getFuncionario();
	String rolActivo = "";
	if (usuario != null 
			&& usuario.getRolACtivo() != null 
			&& usuario.getRolACtivo().getRol() != null
			&& usuario.getRolACtivo().getRol().getRolId() != null){
		rolActivo = usuario.getRolACtivo().getRol().getRolId().toString();
	}
%>

	//Inician variables para filtrar el catalogo de situacion juridica - Actuaciones
	var idImputado = <%=SituacionJuridica.IMPUTADO.getValorId()%>;
	var idSentenciado = <%=SituacionJuridica.SENTENCIADO.getValorId()%>;
	var idDetenido = <%=SituacionJuridica.DETENIDO.getValorId()%>;
	var idInvestigado = <%=SituacionJuridica.INVESTIGADO.getValorId()%>;
	var idAcusado = <%=SituacionJuridica.ACUSADO.getValorId()%>;
	//Finalizan variables para filtrar el catalogo de situacion juridica

	var contextPath = '<%=request.getContextPath()%>';
	var CONTEXT_ROOT = '<%= request.getContextPath()%>';
	var nombreFuncionario = "<%= funcionario.getNombreFuncionario()%>";
	var apaterFuncionario = "<%= funcionario.getApellidoPaternoFuncionario()%>";
	var amaterFuncionario = "<%= funcionario.getApellidoMaternoFuncionario()%>";
	var involucrados=new Array();
	var idWindowGenerarNotas=1;
	var idWindowAsntarRegCadCus=1;
	var situacionActualDefendido="";
	var idExpedienteop = '<%=idExpedienteOK%>';
	var idNumeroExpedienteOp = '<%=numeroExpedienteIdOK%>';
	var numeroExpediente= '<%=numeroExpediente%>';
	var numeroExpedienteId= idNumeroExpedienteOp;
	
	//@deprecated 
	var idExpedienteOK = '<%=idExpedienteOK%>';
	var idNumeroExpedienteOK = '<%=numeroExpedienteIdOK%>';
	
	var rolActivo = '<%=rolActivo%>';
	var entidadFederativa = '<%=entidadFederativa%>';
	var nombreDefendido="";
	
	//@deprecated No se requiere manejar un arreglo solo para la pestania anterior. Ver cmpInvolucradosExpediente.js
<%
	if(exp.getEtapasPasadas() != null && !exp.getEtapasPasadas().isEmpty()){
		List<EtapasExpediente> etapasExpediente = null;
		etapasExpediente = exp.getEtapasPasadas();
		
		for(EtapasExpediente etapa: etapasExpediente){
		%>
			if('<%=etapa%>' == 'INTEGRACION'){
			 	/* loadEtapaIntegracion(); */
			}else if('<%=etapa%>' == 'TECNICA'){
				/* loadEtapaTecnica();	 */
			}else if('<%=etapa%>' == 'CONCILIACION_Y_MEDIACION'){
				/* loadEtapaRestaurativa(); */
			}else if('<%=etapa%>' == 'EJECUCION'){
				/* loadEtapaEjecucion(); */
			}else if('<%=etapa%>' == 'ASESORIA'){
				/* loadEtapaAsesoria(); */
			}
		<%
		}
	}
%>
//@deprecated No se requiere manejar un arreglo solo para la pestania anterior de involucrados.
<%
	List<InvolucradoDTO> involucrados = null;
	involucrados = exp.getInvolucradoByCalidad(Calidades.DEFENDIDO);
	if(involucrados != null && !involucrados.isEmpty()){
%>		involucrados["DEFENDIDO"]=<%=involucrados.get(0).getElementoId()%>;<%
	}
	involucrados = exp.getInvolucradoByCalidad(Calidades.DENUNCIANTE);
	if(involucrados != null && !involucrados.isEmpty()){
%>	involucrados["DENUNCIANTE"]=<%=involucrados.get(0).getElementoId()%>;<%
	}
	involucrados = exp.getInvolucradoByCalidad(Calidades.SOLICITANTE);
	if(involucrados != null && !involucrados.isEmpty()){
%>	involucrados["SOLICITANTE"]=<%=involucrados.get(0).getElementoId()%>;<%
	}
	involucrados = exp.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA);
	if(involucrados != null && !involucrados.isEmpty()){
%>	involucrados["PROBABLE_RESPONSABLE_PERSONA"]=<%=involucrados.get(0).getElementoId()%>;<%
	}
	involucrados = exp.getInvolucradoByCalidad(Calidades.QUIEN_DETUVO);
	if(involucrados != null && !involucrados.isEmpty()){
%>	involucrados["QUIENDETUVO"]=<%=involucrados.get(0).getElementoId()%>;
<%
	}
%>
	/* 
	* Identificadores de los frames ingresar involucrados
	*/
	var idWindowIngresarVictima = 1;	
	var idWindowIngresarTraductor = 1;	
	var idWindowIngresarQuienDetuvo = 1;	
	//var idWindowIngresarRepresentanteLegal = 1;
	var idWindowIngresarTestigo = 1;
	//var idWindowIngresarTutor = 1;
	var idWindowIngresarProbResponsable = 1;
	var idWindowIngresarDefendido = 1;
	//var idWindowIngresarContactoDeUnaOrganizacion = 1;
	//var idWindowIngresarSentenciadoReinsertar = 1;
	var idWindowIngresarDenunciante = 1;
	//var idWindowConsultarProbResponsable = 1;
	//var idWindowConsultarTestigo = 1;
	//var idWindowConsultarTraductor = 1;		
	//var idWindowConsultarContactoDeUnaOrganizacion = 1;
	//var idWindowModificarDenunciante = 1;
	//var idWindowSolicitudTranscripcion =1;
	
	/*
	* Identificadores de los frames ingresar Objetos y evidencias
	*/
	var idWindowIngresarVehiculo = 1;
	var idWindowIngresarEquipoDeComputo = 1;
	var idWindowIngresarEquipoTelefonico = 1;
	var idWindowIngresarArma = 1;
	var idWindowIngresarExplosivo = 1;
	var idWindowIngresarSustancia = 1;
	var idWindowIngresarAnimal = 1;	
	var idWindowIngresarAeronave = 1;
	var idWindowIngresarEmbarcacion = 1;
	var idWindowIngresarNumerario = 1;
	var idWindowIngresarVegetal = 1;
	var idWindowIngresarDocumentoOficial = 1;
	var idWindowIngresarJoya = 1;
	var idWindowIngresarObraDeArte = 1;
	var idWindowIngresarOtros = 1;
	var idWindowIngresarPericial = 1;
	
	//bandera para deshabilitar los campos en la consulta, 
	//basado en la consulta de los Involucrados del Expediente 
	//de Defensoria (ingresarProbalbleResponsableView.jsp) 
	//- Relacionado con habilitaDatosEspecificos() de 
	//datosGeneralesView.jsp 
	var deshabilitarCamposPM=false;
	
	//Parametros para las funciones de la pestania de DELITOS
	var deshabilitarCampos=false;
	var idExpedienteop = "";
	var mostrarDelitos = true;
		
	var contextoPagina = "${pageContext.request.contextPath}";
	var etiquetaProbableProp = '<bean:message key="probableResponsableTitulo"/>';
	var muestraDetenido = 1;
	
	var solicitudId = '<%= request.getParameter("solicitudDefensorId")%>';
	
	var confirmarCierreVentana ='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getMostrarMensajeConfirmacionEnDocumento() %>';
	var idWindowPantallaActuaciones = 0;
	
	//Determinar si es un visor de Defensoria - Se oculta el "Guardado Parcial"
	var ocultarGuardadoParcial = true;
	
	$(document).ready(function() {
		idExpedienteop = '<%= request.getParameter("idExpedienteop")%>';
		
		$("#tapPericiales").one("click", function() {
			cargaGridSolicitidesPericialesEnviadasPorExpediente();
			cargaGridSolicitidesPericialesRespondidasPorExpediente();
			cargaGridSolicitidesPericialesRegresadasPorExpediente();
		});
		
		//Ocultar botones de Involucrados
		$("#crearDenunciante").hide();
		$("#nuevaVictima").hide();
		$("#nuevoProbResponsable").hide();
		$("#nuevoTestigo").hide();
		$("#nuevoTraductor").hide();
		$("#quienDetuvo").hide();
		// $('#crearDenunciante').css('display','none'); //.attr("disabled", "disabled");
		
		//@deprecated
		//consultaDetalleExp();
		//Consulta el resumen del expediente
		datosGenerales();
		$("#tabedMenuExpedienteDefensoria").tabs();
		$("#tabs").tabs();
		$("#tabschild" ).tabs();
		$("#tabschild4").tabs();
		$("#tabschild14").tabs();
		
		//Se crean los tabs childs de RESUMEN DE LA SOLICITUD
		$("#tabsconsultaprincipal-1" ).tabs();
		$("#tabsVisorDefensoria-1" ).tabs();
		$("#tabsChildResumenSolicitud" ).tabs();
				
		//@deprecated
		//$("#tabedMenuExpedienteDefensoria").tabs('selected', 4);
		$("#btnCadCusConsultaCadCus").click(consultarRegCadenaCustodia);
		$("#rdbMenuInterRelDelXPersona").bind("click",ocultaMuestraTblsRelacionarDelitos);
		$("#rdbMenuInterRelDelXDelito").bind("click",ocultaMuestraTblsRelacionarDelitos);
		$("#rdbMenuInterRelDelXTodos").bind("click",ocultaMuestraTblsRelacionarDelitos);
	
		//Administador de Tabs, que permite ocultar o mostrar pestanias de acuerdo a la etapa.
		manageTabs();
		killDomicilioNotificaciones();
		killCoordenadasGeograficas();

		//ocultamos las leyendas en la carga de la pagina
		$("#leyendaUnDelitoGrave,#leyendaNingunDelitoGrave").hide();
		$("#btnGuardarDelitosAg").hide();
		$("#btnMedidasCautelares").hide();
		
		//Configura texto de tabs y botones
		$("#tapProbableResposable").html(etiquetaProbableProp);
		$("#btnNuevoProbResponsable").val(etiquetaProbableProp);
		
		$("#spanGralJAR,#spanGralUI,#spanGralIE").hide();
		$("#tabAudienciasEx").hide();
		
		/*SECCION PARA DECLARAR LOS GRIDS DE LOS OBJETOS EN EL VISOR INTERMEDIO*/
		jQuery("#gridObjsVehiculo").jqGrid({ 
			url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.VEHICULO.getValorId() %>', 
			datatype: "xml", 
			colNames:['Veh&iacute;culo','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'Vehiculo',index:'vehiculo', width:100}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:false}, 
					],
			pager: jQuery('#pagerGridObjsVehiculo'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"VEH&Iacute;CULOS",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsVehiculo',
			ondblClickRow: function(id){
				consultarVehiculo(id);
			},
			sortorder: "desc"
		});
		
		//grid de arma
		jQuery("#gridObjsArma").jqGrid({
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Arma','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'Arma',index:'arma', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsArma'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"ARMA",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsArma',
			ondblClickRow: function(id){
				consultarArma(id);
				},
			sortorder: "desc"
		});
		
		//Grid de equipo de computo
		jQuery("#gridObjsEquipoComputo").jqGrid({
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Equipo de C&oacute;mputo','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'EquipoComputo',index:'equipoComputon', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsEquipoComputo'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"EQUIPO DE COMPUTO",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsVehiculo',
			ondblClickRow: function(id){
				consultarEquipoComputo(id);
				},
			sortorder: "desc"
		});
		
		//Grid de Equipo Telefonico
		jQuery("#gridObjsEquipoTelefonico").jqGrid({
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Equipo Telefonico','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'EquipoTelefonico',index:'equipoTelefonicon', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsEquipoTelefonico'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"EQUIPO TELEFONICO",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsEquipoTelefonico',
			ondblClickRow: function(id){
				consultarEquipoTelefonico(id);
				},
			sortorder: "desc"
		});
		
		//Grid de Explosivo
		jQuery("#gridObjsExplosivo").jqGrid({
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Explosivo','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'Explosivo',index:'explosivon', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsExplosivo'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"EXPLOSIVO",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsExplosivo',
			ondblClickRow: function(id){
				consultarExplosivo(id);
				},
			sortorder: "desc"
		});
		
		//Grid de Sustancia
		jQuery("#gridObjsSustancia").jqGrid({
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Sustancia','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'Sustancia',index:'sustancian', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsSustancia'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"SUSTANCIA",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsSustancia',
			ondblClickRow: function(id){
				consultarSustancia(id);
				},
			sortorder: "desc"
		});
		
		//Grid de Animal
		jQuery("#gridObjsAnimal").jqGrid({ 
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Animal','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'Animal',index:'animaln', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsAnimal'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"ANIMAL",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsAnimal',
			ondblClickRow: function(id){
				consultarAnimal(id);
				},
			sortorder: "desc"
		});
		
		//Grid de Aeronave
		jQuery("#gridObjsAeronave").jqGrid({ 
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Aeronave','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'Aeronave',index:'aeronaven', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsAeronave'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"AERONAVE",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsAeronave',
			ondblClickRow: function(id){
				consultarAeronave(id);
				},
			sortorder: "desc"
		});
		
		//Grid de Embarcacion
		jQuery("#gridObjsEmbarcacion").jqGrid({ 
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Embarcacion','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'Embarcacion',index:'embarcacionn', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsEmbarcacion'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"EMBARCACION",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsEmbarcacion',
			ondblClickRow: function(id){
				consultarEmbarcacion(id);
				},
			sortorder: "desc"
		});
		
		//Grid de Numerario
		jQuery("#gridObjsNumerario").jqGrid({ 
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Numerario','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'Numerario',index:'numerarion', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsNumerario'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"NUMERARIO",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsNumerario',
			ondblClickRow: function(id){
				consultarNumerario(id);
				},
			sortorder: "desc"
		});
		
		//Grid de Vegetal
		jQuery("#gridObjsVegetal").jqGrid({ 
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Vegetal','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'Vegetal',index:'vegetaln', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsVegetal'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"VEGETAL",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsVegetal',
			onSelectRow: function(id){
				consultarVegetal(id);
				},
			sortorder: "desc"
		});
		
		//Grid de Documento Oficial
		jQuery("#gridObjsDocumentoOficial").jqGrid({ 
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Documento oficial','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'DocumentoOficial',index:'documentoOficialn', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsDocumentoOficial'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"DOCUMENTO OFICIAL",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsDocumentoOficial',
			ondblClickRow: function(id){
				consultarDocumentoOficial(id);
				},
			sortorder: "desc"
		});
		
		//Grid de Joya
		jQuery("#gridObjsJoya").jqGrid({ 
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Joya','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'Joya',index:'joyan', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsJoya'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"JOYA",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsJoya',
			ondblClickRow: function(id){
				consultarJoya(id);
				},
			sortorder: "desc"
		});
		
		//Grid de Obra de Arte
		jQuery("#gridObjsObraDeArte").jqGrid({ 
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Obra de arte','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'ObraDeArte',index:'obraDeArten', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsObraDeArte'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"OBRA DE ARTE",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsObraDeArte',
			ondblClickRow: function(id){
				consultarObraDeArte(id);
				},
			sortorder: "desc"
		});
		
		//Grid de Otros
		jQuery("#gridObjsOtros").jqGrid({ 
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Otros','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'Otros',index:'otrosn', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsOtros'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"OTROS",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsOtros',
			ondblClickRow: function(id){
				consultarOtros(id);
				},
			sortorder: "desc"
		});
		
		//Grid de Periciales
		jQuery("#gridObjsPericial").jqGrid({ 
			url:'<%= request.getContextPath()%>/limpiarGrid.do',
			datatype: "xml", 
			colNames:['Pericial','Folio de cadena de custodia', 'No. de casos asociados'], 
			colModel:[ 	{name:'Otros',index:'otrosn', width:150}, 
						{name:'FolioCadCus',index:'folioCadCus', width:150}, 
						{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
					],
			pager: jQuery('#pagerGridObjsPericial'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:600,
			caption:"PERICIAL",
			sortname: 'Clave',
			viewrecords: true,
			id: 'gridObjsPericial',
			ondblClickRow: function(id){
				consultarPericial(id);
				},
			sortorder: "desc"
		});
		/*FIN - SECCION PARA DECLARAR LOS GRIDS DE LOS OBJETOS EN EL VISOR INTERMEDIO*/
		
		//Editor del Motivo de la Asesoria
		cargaEditor();

		//activa la funcionalidad del acordeon
		$('#cedulaIdentificacion').easyAccordion({ 
			  autoStart: false, 
			  slideInterval: 3000
		});
		if (rolActivo == '<%=Roles.DEFENSOR.getValorId()%>' || rolActivo == '<%=Roles.DEFENSORATE.getValorId()%>' || rolActivo == '<%=Roles.COORDINADORDEF.getValorId()%>' ){
			$("#nuevoVehiculo").hide();
			$("#nuevoEquipoDeComputo").hide();
			$("#nuevoEquipoTelefonico").hide();
			$("#nuevaArma").hide();
			$("#nuevoExplosivo").hide();
			$("#nuevaSustancia").hide();
			$("#nuevoAnimal").hide();	
			$("#nuevaAeronave").hide();
			$("#nuevaEmbarcacion").hide();
			$("#nuevoNumerario").hide();
			$("#nuevoVegetal").hide();
			$("#nuevoDocumentoOficial").hide();
			$("#nuevaJoya").hide();
			$("#nuevaObraDeArte").hide();
			$("#rdbMenuInterRelDelXTodos").attr("disabled","");
			$("#rdbMenuInterRelDelXPersona").attr("disabled","");
			$("#rdbMenuInterRelDelXDelito").attr("disabled","");
			//bandera para deshabilitar los campos
			deshabilitarCamposPM=true;
			//Delitos en establecerRelacionesDelitoPersonaPG.jsp
			deshabilitarCampos=true;
		}
		
		if( entidadFederativa=='<%=EntidadFederativa.ZACATECAS.getValorId()%>'){
			$("#tab_Diligencias").hide();
			$("#tabPericialesEx").hide();
		}
		
		//Visor de Resumen General "cargarDatosGenerales"
		$("#denuncianteslb").html('Denunciantes:');
		$("#victimaslb").html('V&iacute;ctimas:');
		$("#probableResponsableslb").html('<bean:message key="plProbalbeResponsableTitulo"/>');
		//$('#idsNumerosDelExpediente').hide();

		//TAB ACTUACIONES DEL EXPEDIENTE Y DEFENDIDOS 
		$("#tabschild10").tabs();
	}); //Fin ready
	
	/**
	* Funciones que son utilizadas para la consulta del expediente en defensoria
	* Determinar si es @deprecated
	*/
	function consultaDetalleExp() {
		datosGenerales();
		$.ajax({type : 'POST',
			url : '<%= request.getContextPath()%>/consultaDetalleExpedienteDefensoria.do',
			data : 'numeroExpedienteId=' +idNumeroExpedienteOK+'&numeroExpediente='+'<%=numeroExpediente%>',
			async : false, dataType : 'xml',
			success : function(xml) {
				$(xml).find('involucradoDTO').each(function(){
					if($(this).find('motivoComparecencia')){
						$("#editor2").val($(this).find('motivoComparecencia').text());
					}
				});

				$(xml).find('involucradoDTO').each(function(){
					calidad = $(this).find('calidadDTO').first().find('valorIdCalidad').find('idCampo').text();
					if(calidad == <%=Calidades.DEFENDIDO.getValorId()%>){
						situacionActualDefendido = $(this).find('valorSituacionJuridica').find('valor').text();
						nombre =$(this).find('nombresDemograficoDTO').find('nombreDemografico').first();
						nombreDefendido = $(nombre).find('nombre').text()+" "+$(nombre).find('apellidoPaterno').text()+" "+$(nombre).find('apellidoMaterno').text();
						$("#involucradoDefendido").val(nombreDefendido);
					}
				});
				
				pintaDatosAudiencia(xml);
				pintaDatosResumen(xml);
				//Ver datosGeneralesView.jsp
			    pintaDatosGenerales(xml);
				//Ver ingresarDomicilioView.jsp
			    pintaDatosDomicilio(xml);
				//Ver ingresarMediosContactoView.jsp
	    		pintaDatosContacto(xml);
			 }
		});
	}
	
	//Funcion que permite mostrar el valor para los campos del resumen
	function pintaDatosResumen(detalle){
		//Numero de caso
		if(existe($(detalle).find('avisoDetencion').first().find('numeroCasoAsociado').text())){
			$("#caso").val($(detalle).find('avisoDetencion').first().find('numeroCasoAsociado').text());
		}else{
			$("#caso").val("---");
		}	
		
		//Permite mostrar el delito principal
		if(existe($(detalle).find('avisoDetencion').find('delitos').find('catDelitoDTO').first().find('nombre').text())){
			$("#delito").val($(detalle).find('avisoDetencion').find('delitos').find('catDelitoDTO').first().find('nombre').text());
		}else{
			$("#delito").val("---");
		}	
		
		//Existe Detenido		
		var esDetenido = $(detalle).find('inputado').find('esDetenido').first().text();
		if(esDetenido == "true"){
			$("#detenido").val("Si");			
		}else{
			if(esDetenido == ""){
				if($(detalle).find('strFechaInicioDetencion').first().text() != "")
					$("#detenido").val("SI");
				else
					$("#detenido").val("----");
			}
				
			else
				$("#detenido").val("No");
			
		}
		
		//Lugar de detencion
		if(existe($(detalle).find('inputado').find('detenciones').find('detencionDTO').find('lugarDetencionProvicional').first().text()))
			$("#lugarDetencion").val($(detalle).find('inputado').find('detenciones').find('detencionDTO').find('lugarDetencionProvicional').first().text());
		else
			$("#lugarDetencion").val("---");
		
		//Fecha y hora de detencion
		if(existe($(detalle).find('strFechaInicioDetencion'))){
			var fecha = $(detalle).find('strFechaInicioDetencion').first().text();
			var strfecha = obtenerFecha(fecha);
			var strhora = obtenerHora(fecha);
			$("#fechaDetencion").val(strfecha);
			$("#horaDetencion").val(strhora);
		}else{
			$("#fechaDetencion").val("----");
			$("#horaDetencion").val("----");
		}		
		
		//Fecha y hora de solicitud
		if(existe($(detalle).find('fechaCreacion'))){
			var fecha = $(detalle).find('fechaCreacion').first().text();
			var strfecha = obtenerFecha(fecha);
			var strhora = obtenerHora(fecha);
			$("#fechaSolicitud").val(strfecha);
			$("#horaSolicitud").val(strhora);
		}else{
			$("#fechaSolicitud").val("----");
			$("#horaSolicitud").val("----");
		}
	}

	//Funcion que permite mostrar el valor para los campos correspondientes a la audiencia
	function pintaDatosAudiencia(xml){
		
		if (existe($(xml).find('avisoDesignacionDTO').first().find('solicitudDefensor').find('audiencia').find('tipoAudiencia').find('valor').text())){
			$("#tipoAudiencia").val($(xml).find('avisoDesignacionDTO').first().find('solicitudDefensor').find('audiencia').find('tipoAudiencia').find('valor').text());
		}else{
			$("#tipoAudiencia").val("---");
		}
		
		if (existe($(xml).find('avisoDesignacionDTO').first().find('solicitudDefensor').find('audiencia').find('sala').find('nombreSala').text())){
			$("#salaAudiencia").val($(xml).find('avisoDesignacionDTO').first().find('solicitudDefensor').find('audiencia').find('sala').find('nombreSala').text());
		}else{
			$("#salaAudiencia").val("---");
		}
		
		var fechaAudiencia = $(xml).find('avisoDesignacionDTO').first().find('solicitudDefensor').find('audiencia').find('fechaEvento').text();
		if (existe(fechaAudiencia)){
			$("#fechaAudiencia").val(obtenerFecha(fechaAudiencia));
			$("#horaAudiencia").val(obtenerHora(fechaAudiencia));
		}else{
			$("#fechaAudiencia").val("---");
			$("#horaAudiencia").val("---");
		}
		
		if (existe($(xml).find('avisoDesignacionDTO').first().find('solicitudDefensor').find('strFechaCreacion').text())){
			$("#fechaSolicitud").val($(xml).find('avisoDesignacionDTO').first().find('solicitudDefensor').find('strFechaCreacion').text());
		}else{
			$("#fechaSolicitud").val("---");
		}
		
		if (existe($(xml).find('avisoDesignacionDTO').first().find('solicitudDefensor').find('strHoraCreacion').text())){
			$("#horaSolicitud").val($(xml).find('avisoDesignacionDTO').first().find('solicitudDefensor').find('strHoraCreacion').text());
		}else{
			$("#horaSolicitud").val("---");
		}
		
		if ($("#involucradoDefendido").val() == ""){
			if (existe($(xml).find('avisoDesignacionDTO').first().find('solicitudDefensor').find('detenido').text())){
				$("#involucradoDefendido").val($(xml).find('avisoDesignacionDTO').first().find('solicitudDefensor').find('detenido').text());			
			}else{
				$("#involucradoDefendido").val("---");
			}
		}
		$("#fechaVencimiento").val("---");
		$("#horaVencimiento").val("---");
	}

	//TODO Se ha definido la misma funcion en comun.jsp
	function existe(val){
		if(val != undefined && val != null && val != "" && val.length > 0){
			return true;
		}
		return false;
	}
	
	function obtenerFecha(fecha){
		var fh = fecha.split(" ");
		return fh[0].split("-")[2]+"/"+fh[0].split("-")[1]+"/"+fh[0].split("-")[0];	
	}
	
	function obtenerHora(fecha){
		var fh = fecha.split(" ");
		return fh[1].substring(0,5);
	}


	/**
	* Funcion que, de acuerdo a la etapa se ocultan o muestran pesta&ntilde;as.
	*/
	function manageTabs(){
		$("#tabDatosInteres").hide();
		$("#tabMotivoAsesor").hide();
		//$("#tabInvolucrados").hide();
		$("#tabObjetsyEvids").hide();
		//$("#tabCadebaCustod").hide();
		$("#tab_Diligencias").hide();
		//$("#tabAudienciasEx").hide();
		$("#tabPericialesEx").hide();
		$("#tab_Actuaciones").hide();
		$("#tab_Notas_Exped").hide();
		$("#tabTabsDelito").hide();
		$("#tabCadebaCustod").hide();
		$("#tabTabsInv").hide();
		
		//Resumen de Solicitud o de Expediente
		if(solicitudId != "undefined" 
				&& solicitudId != undefined 
				&& solicitudId != "" 
				&& solicitudId != null 
				&& solicitudId!='null'){
			consultarDetalleSolicitud(solicitudId);
			$("#tabedMenuExpedienteDefensoria").tabs("option", "selected", [1]);
			$("#tabResumenSol").show();
			$("#tabResumenExped").hide();
			
			//Visor de Solicitud (No es visor de Expediente)
			mostrarDelitos = false;
		}
		else{ //Visor de Expediente
			$("#tabResumenSol").hide();
			$("#tabResumenExped").show();
			$("#resumen2").show();
		}
		
		
		switch(parseInt(<%=etapaId%>)){
			//TODO Revisar el visor de ASESORIA...
			case <%=EtapasExpediente.ASESORIA.getValorId()%>:
				$("#tabDatosInteres").show();
				$("#tabMotivoAsesor").show();
				$("#resumen2").hide();
				break;
			case <%=EtapasExpediente.CONCILIACION_Y_MEDIACION.getValorId()%>:
			case <%=EtapasExpediente.INTEGRACION.getValorId()%>:
				//$("#tabInvolucrados").show();
				//inicilizarTabInvolucrados(<%=idExpedienteOK%>, <%=numeroExpedienteIdOK%>, '<%=numeroExpediente%>', <%=etapaId%>, <%=estatusExp%>, involucrados);
				$("#tab_Actuaciones").show();
				inicilizarTabActuaciones(<%=idExpedienteOK%>, <%=numeroExpedienteIdOK%>, '<%=numeroExpediente%>', <%=etapaId%>, involucrados, situacionActualDefendido);
				$("#tab_Notas_Exped").show();
				//inicilizarTabNotas(<%=numeroExpedienteIdOK%>);
				
				//Sin carpeta, se muestra resumen Parcial
				//Ocultar los objetos del resumen
				$("#objetosTD").hide();
				$("#resumenObjetoslb").hide();
				$("#amparo_s").hide();
				
				//Ajustar ancho de los renglones
				$("#espacioEntrelbsEstatus").attr('width', '600px');
				$("#involucradosTD").attr('width', '800px');
					
				break;
			case <%=EtapasExpediente.TECNICA.getValorId()%>:
			case <%=EtapasExpediente.EJECUCION.getValorId()%>:
				//$("#tabInvolucrados").show();
				//inicilizarTabInvolucrados(<%=idExpedienteOK%>, <%=numeroExpedienteIdOK%>, '<%=numeroExpediente%>', <%=etapaId%>, <%=estatusExp%>, involucrados);	
				$("#tab_Actuaciones").show();
				inicilizarTabActuaciones(<%=idExpedienteOK%>, <%=numeroExpedienteIdOK%>, '<%=numeroExpediente%>', <%=etapaId%>, involucrados, situacionActualDefendido);
				$("#tab_Notas_Exped").show();
				
				//inicilizarTabNotas(<%=numeroExpedienteIdOK%>);
				if(<%=estatusExp%> == <%=EstatusExpediente.ABIERTO_TECNICA_CON_CARPETA.getValorId()%>
					|| <%=etapaId%> == <%=EtapasExpediente.EJECUCION.getValorId()%>){
					$("#tabObjetsyEvids").show();
					//$("#tab_Diligencias").show();
					//inicilizarTabDiligencias(<%=numeroExpedienteIdOK%>);
					//$("#tabAudienciasEx").show();
					//$("#tabPericialesEx").show();
					$("#tabTabsDelito").show();
					$("#tabCadebaCustod").show();
					
					$("#tabResumenExped").show();
				}
				else{ //Sin carpeta, se muestra resumen Parcial
					
					//Ocultar los objetos del resumen
					$("#objetosTD").hide();
					$("#resumenObjetoslb").hide();
					$("#amparo_s").hide();
					
					//Ajustar ancho de los renglones
					$("#espacioEntrelbsEstatus").attr('width', '600px');
					$("#involucradosTD").attr('width', '800px');
					
				}
				
				$("#tab_Actuaciones").show();
				inicilizarTabActuaciones(<%=idExpedienteOK%>, <%=numeroExpedienteIdOK%>, '<%=numeroExpediente%>', <%=etapaId%>, involucrados, situacionActualDefendido);
				
				break;
			default :
				if(<%=estatusExp%> == <%=EstatusExpediente.ABIERTO_TECNICA_CON_CARPETA.getValorId()%>){
					$("#tabObjetsyEvids").show();
					//$("#tab_Diligencias").show();
					//inicilizarTabDiligencias(<%=numeroExpedienteIdOK%>);
					//$("#tabAudienciasEx").show();
					//$("#tabPericialesEx").show();
					$("#tabTabsDelito").show();
					$("#tabCadebaCustod").show();
					
					$("#tabResumenExped").show();
					
					//DELITOS DEL EXPEDIENTE
					if(mostrarDelitos){
						$("#tapDelitoYRelaciones").one("click", function() {
					    	$('#tapDelitoYRelaciones').addClass("cargando");				
							//Carga grid con el catalogo de los delitos
							cargaGridDelitos();
							
							//Ocultar las actividades sugereidas
							$('#tdActividadesSugeridas').hide();
							
				       		$('#tapDelitoYRelaciones').removeClass("cargando");
						});
					}
				}
				else{ //Sin carpeta, se muestra resumen Parcial
					//Ocultar los objetos del resumen
					$("#objetosTD").hide();
					$("#resumenObjetoslb").hide();
					$("#amparo_s").hide();
					
					//Ajustar ancho de los renglones
					$("#espacioEntrelbsEstatus").attr('width', '600px');
					$("#involucradosTD").attr('width', '800px');
				}
				$("#tab_Actuaciones").show();
				inicilizarTabActuaciones(<%=idExpedienteOK%>, <%=numeroExpedienteIdOK%>, '<%=numeroExpediente%>', <%=etapaId%>, involucrados, situacionActualDefendido);
				break;
		}
		//inicilizarTabBitacora(<%=numeroExpedienteIdOK%>);
	}
	
	/**
	* Funciones que de acuerdo a la etapa del expediente
	* se muestran u ocultan algunos tabs.
	*/
	function loadEtapaAsesoria(){
		$("#tabResumenExped").show();
		$("#tabDatosInteres").show();
		$("#tabMotivoAsesor").show();
		$("#tabDocumentosEx").show();
		$("#tab_BitacoraExp").show();
	}
	function loadEtapaEjecucion(){
		$("#tabResumenExped").show();
		//$("#tabInvolucrados").show();
		$("#tabObjetsyEvids").show();
		//$("#tabCadebaCustod").show();
		$("#tab_Diligencias").show();
		//$("#tabAudienciasEx").show();
		$("#tabPericialesEx").show();
		$("#tab_Actuaciones").show();
		$("#tab_Notas_Exped").show();
		$("#tabDocumentosEx").show();
		$("#tab_BitacoraExp").show();
		//inicilizarTabInvolucrados(<%=idExpedienteOK%>, <%=numeroExpedienteIdOK%>, '<%=numeroExpediente%>', <%=etapaId%>, <%=estatusExp%>, involucrados);	
		inicilizarTabActuaciones(<%=idExpedienteOK%>, <%=numeroExpedienteIdOK%>, '<%=numeroExpediente%>', <%=etapaId%>, involucrados, situacionActualDefendido);
	}
	function loadEtapaRestaurativa(){
		$("#tabResumenExped").show();
		//$("#tabInvolucrados").show();
		$("#tab_Actuaciones").show();
		$("#tab_Notas_Exped").show();
		$("#tabDocumentosEx").show();
		$("#tab_BitacoraExp").show();
		//$("#tabInvolucrados").show();
		//inicilizarTabInvolucrados(<%=idExpedienteOK%>, <%=numeroExpedienteIdOK%>, '<%=numeroExpediente%>', <%=etapaId%>, <%=estatusExp%>, involucrados);
		inicilizarTabActuaciones(<%=idExpedienteOK%>, <%=numeroExpedienteIdOK%>, '<%=numeroExpediente%>', <%=etapaId%>, involucrados, situacionActualDefendido);
	}
	function loadEtapaTecnica(){
		$("#tabResumenExped").show();
		//$("#tabInvolucrados").show();
		if(<%=estatusExp%> == <%=EstatusExpediente.ABIERTO_TECNICA_CON_CARPETA.getValorId()%>){
			$("#tabObjetsyEvids").show();
			//$("#tabCadebaCustod").show();
			$("#tab_Diligencias").show();
			//$("#tabAudienciasEx").show();
			$("#tabPericialesEx").show();
		}
		$("#tab_Actuaciones").show();
		$("#tab_Notas_Exped").show();
		$("#tabDocumentosEx").show();
		$("#tab_BitacoraExp").show();
		//inicilizarTabInvolucrados(<%=idExpedienteOK%>, <%=numeroExpedienteIdOK%>, '<%=numeroExpediente%>', <%=etapaId%>, <%=estatusExp%>, involucrados);	
		inicilizarTabActuaciones(<%=idExpedienteOK%>, <%=numeroExpedienteIdOK%>, '<%=numeroExpediente%>', <%=etapaId%>, involucrados, situacionActualDefendido);
	}
	function loadEtapaIntegracion(){
		$("#tabResumenExped").show();
		//$("#tabInvolucrados").show();
		$("#tab_Actuaciones").show();
		$("#tab_Notas_Exped").show();
		$("#tabDocumentosEx").show();
		$("#tab_BitacoraExp").show();
		//inicilizarTabInvolucrados(<%=idExpedienteOK%>, <%=numeroExpedienteIdOK%>, '<%=numeroExpediente%>', <%=etapaId%>, <%=estatusExp%>, involucrados);
		inicilizarTabActuaciones(<%=idExpedienteOK%>, <%=numeroExpedienteIdOK%>, '<%=numeroExpediente%>', <%=etapaId%>, involucrados, situacionActualDefendido);
	}
	function consultaPDF(id){
		document.formaArchivoD.documentoId.value = id;
		document.formaArchivoD.submit();
	}
	
		
	//********  FUNCIONES REVISADAS ********//
	
	/************ FIN FUNCION PARA OCULTAR-MOSTRAR LOS TABS DEL VISOR***************/

	
	//******** TAB RESUMEN ********//
	/*
	*Funcion para consultar el detalle de la solicitud
	*/
	function consultarDetalleSolicitud(solicitudId){

		var parametros = "";

		parametros += '&idDocumento=' + solicitudId;
		
		ejecutaAction("/consultarDetalleSolicitud", function(xmlRespuesta){					
			configurarCejaResumen(xmlRespuesta);
			cargaGridInvolucradoSolicitudDefensor(solicitudId);
		} , parametros);
	}

	
	
	//TODO Similar al de ingresarMenuIntermedio.jsp
	function datosGenerales(){
		 $.ajax({
		      type: 'POST',
	    	  url: '<%= request.getContextPath()%>/cargarDatosGenerales.do?idNumeroExpedienteOp='+idNumeroExpedienteOp,
	    	  data: '',
	    	  dataType: 'xml',
	    	  async: false,
	    	  success: function(xml){
					$('#Vehiculos').html($(xml).find('totalVehiculos').text()+': '+$(xml).find('vehiculos').text());
					if($(xml).find('ve').text()!="1"){
						$('#imgVehiculo').hide();
					}
					$('#EquiposDeComputo').html($(xml).find('totalEquiposComputo').text()+': '+$(xml).find('equiposComputo').text());
					if($(xml).find('equiCom').text()!="1"){
						$('#imgEquiposDeComputo').hide();
					}
					$('#EquiposTelefonicos').html($(xml).find('totalEquiposTelefonicos').text()+': '+$(xml).find('equiposTelefonicos').text());
					if($(xml).find('equiTel').text()!="1"){
						$('#imgEquiposTelefonicos').hide();
					}
					$('#Armas').html($(xml).find('totalArmas').text()+': '+$(xml).find('armas').text());
					if($(xml).find('arm').text()!="1"){
						$('#imgArmas').hide();
					}
					$('#Explosivos').html($(xml).find('totalExplosivos').text()+': '+$(xml).find('explosivos').text());
					if($(xml).find('expl').text()!="1"){
						$('#imgExplosivos').hide();
					}
					$('#Sustancias').html($(xml).find('totalSustancias').text()+': '+$(xml).find('sustancias').text());
					if($(xml).find('sus').text()!="1"){
						$('#imgSustancias').hide();
					}
					$('#Animales').html($(xml).find('totalAnimales').text()+': '+$(xml).find('animales').text());
					if($(xml).find('anim').text()!="1"){
						$('#imgAnimales').hide();
					}
					$('#Aeronaves').html($(xml).find('totalAeronaves').text()+': '+$(xml).find('aeronaves').text());
					if($(xml).find('aero').text()!="1"){
						$('#imgAeronaves').hide();
					}
					$('#Embarcacion').html($(xml).find('totalEmbarcaciones').text()+': '+$(xml).find('embarcaciones').text());
					if($(xml).find('embar').text()!="1"){
						$('#imgEmbarcacion').hide();
					}
					$('#Numerario').html($(xml).find('totalNumerarios').text()+': '+$(xml).find('numerarios').text());
					if($(xml).find('nume').text()!="1"){
						$('#imgNumerario').hide();
					}
					$('#Vegetal').html($(xml).find('totalVegetales').text()+': '+$(xml).find('vegetales').text());
					if($(xml).find('vege').text()!="1"){
						$('#imgVegetal').hide();
					}
					$('#DocumentoOficial').html($(xml).find('totalDocumentosOficiales').text()+': '+$(xml).find('documentosOficiales').text());
					if($(xml).find('docuOfi').text()!="1"){
						$('#imgDocumentoOficial').hide();
					}
					$('#Joya').html($(xml).find('totalJoyas').text()+': '+$(xml).find('joyas').text());
					if($(xml).find('joy').text()!="1"){
						$('#imgJoya').hide();
					}
					$('#ObraDeArte').html($(xml).find('totalObrasDeArte').text()+': '+$(xml).find('obrasDeArte').text());
					if($(xml).find('obrArte').text()!="1"){
						$('#imgObraDeArte').hide();
					}
					$('#Periciales').html($(xml).find('totalPericialObjeto').text()+': '+$(xml).find('pericialObjeto').text());
					if($(xml).find('perObj').text()!= "1"){
						$('#imgPericiales').hide();
					}
					$('#Otros').html($(xml).find('totalOtrosObjestos').text()+': '+$(xml).find('otrosObjestos').text());
					if($(xml).find('otrObj').text()!="1"){
						$('#imgOtros').hide();
					}
					$('#Denunciantes').html($(xml).find('totalDenunciantes').text()+': '+$(xml).find('denunciantes').text());
					if($(xml).find('denun').text()!="1"){
						$('#imgDenunciantes').hide();
					}
					$('#Victimas').html($(xml).find('totalVictimas').text()+': '+$(xml).find('victimas').text());
					if($(xml).find('vic').text()!="1"){
						$('#imgVictimas').hide();
					}
					$('#ProbablesResponsables').html($(xml).find('totalProbablesResposables').text()+': '+$(xml).find('probablesResposables').text());
					if($(xml).find('proba').text()!="1"){
						$('#imgProbablesResponsables').hide();
					}
					$('#Testigos').html($(xml).find('totalTestigos').text()+': '+$(xml).find('testigos').text());
					if($(xml).find('test').text()!="1"){
						$('#imgTestigos').hide();
					}
					$('#Traductores').html($(xml).find('totalTraductores').text()+': '+$(xml).find('traductores').text());
					if($(xml).find('tradu').text()!="1"){
						$('#imgTraductores').hide();
					}
					$('#QuienDetuvo').html($(xml).find('quienDetuvo').text()+': '+$(xml).find('quienDetuvoNombre').text());
					if($(xml).find('quienDetu').text()!="1"){
						$('#imgQuienDetuvo').hide();
					}
					$('#estatusExpe').html($(xml).find('estatusNumeroExpediente').text());
					$('#origenExpe').html($(xml).find('origenExpediente').text());	
					$('#anonimoDenun').html($(xml).find('esDesconocido').text());
					num=$(xml).find('totalDocumentosDelExpediente').text();
					//$('#idTotalDocumentos').html($(xml).find('totalDocumentosDelExpediente').text());
					$('#fechaApertura').html("Fecha Apertura:"+$(xml).find('fechaApertura').text());
					$('#responsableTramite').html($(xml).find('responsableTramite').text());
					$('#estatusActuacion').html($(xml).find('estatusActuacion').text());
					$('#origenNumeroExpediente').html($(xml).find('origenNumeroExpediente').text());
					$('#etapaExpediente').html($(xml).find('etapaExpediente').text());
					

					/* if($(xml).find('numeroExpedienteAlterno').text() != ""){
						$('#numExpAltSpan').val("");
						$('#numExpAltSpan').val($(xml).find('numeroExpedienteAlterno').text());
					} */
	    	  }
	    	});
	}
	//******** TAB INTERESADO - ASESORIA ********//
	//******** TAB MOTIVO ASESORIA - ASESORIA ********//
	
	function cargaEditor(){
		//carga el editor de texto
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
					]
			};		
	
		$('.jquery_ckeditor').ckeditor(config);
	}	
	
	//********  TAB INVOLUCRADOS  ********//
	function cargarInvolucradosExpedienteMI(idNumeroExpediente){
		limpiarListaInvolucrados();
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/ConsultaInvolucradosExpediente.do',
    		data: 'idNumeroExpediente='+idNumeroExpediente,
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    	      $(xml).find('involucradoViewDTO').each(function(){
	    	      if($(this).find('calidad').text() == '<%= Calidades.DENUNCIANTE.getValorId() %>' || $(this).find('calidad').text() == '<%= Calidades.DENUNCIANTE_ORGANIZACION.getValorId() %>'){
						var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaDenunciante(' + $(this).find('involucradoId').text() + ');">';
	    	      	  if($(this).find('nombre').text()=='null'){
						  liga += 'An&oacute;nimo';
					  }else if($(this).find('nombre').text()==''){
						  liga += 'An&oacute;nimo';
					  }else if($(this).find('nombre').text()=='   '){							  
						  liga += 'An&oacute;nimo';			
					  }	else{
						  liga += $(this).find('nombre').text();
						}	
					  liga += '</a></td></tr>';
	    	    	  $('#tblDenunciante').append(liga);
	    	    	  $('#crearDenunciante').css('display','none'); //.attr("disabled", "disabled");
	    	      }
	    	      if($(this).find('calidad').text() == '<%= Calidades.VICTIMA_PERSONA.getValorId() %>' || $(this).find('calidad').text() == '<%= Calidades.DENUNCIANTE.getValorId() %>'){
	    	    	  if($(this).find('esVictima').text()=="true"){
		    	    	  	var liga = '<tr id="v' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaDenunciante(' + $(this).find('involucradoId').text() + ');">';
							if($(this).find('nombre').text()=='null'){
								  liga += 'Desconocido';
						}else if($(this).find('nombre').text()=='' || $(this).find('nombre').text()=='   '){
								  liga += 'An&oacute;nimo';
	    	    	    }
  		    	    	    else{
								  liga += $(this).find('nombre').text();
							  }
							  liga += '</a></td></tr>';
			    	    	  $('#tblVictima').append(liga);
					}else if($(this).find('calidad').text() == '<%= Calidades.VICTIMA_PERSONA.getValorId() %>'){
						  var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaVictima(' + $(this).find('involucradoId').text() + ');">';
			    	      if($(this).find('nombre').text()=='null'){
							  liga += 'Desconocido';
			    	      }else{
							  liga += $(this).find('nombre').text();
						  }
						  liga += '</a></td></tr>';
		    	    	  $('#tblVictima').append(liga);
					}
			    	  
	    	      }
	    	      
	    	      if($(this).find('calidad').text() == '<%= Calidades.DEFENSOR_PUBLICO.getValorId() %>' ||	$(this).find('calidad').text() == '<%= Calidades.DEFENSOR_PRIVADO.getValorId() %>'){
	    	    	  idDefensor=$(this).find('involucradoId').text();
		    	    	  nombreDefensor=$(this).find('nombre').text();			    	    
	    	      }
	    	      if($(this).find('calidad').text() == '<%= Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId() %>' || $(this).find('calidad').text() == '<%= Calidades.PROBABLE_RESPONSABLE_ORGANIZACION.getValorId() %>'){
		    	      var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaProbableResponsable(' + $(this).find('involucradoId').text() + ');">';
		    	      if($(this).find('nombre').text()=='null'){
						  liga += 'Desconocido';
					  }else{
						  liga = liga + $(this).find('nombre').text() +' - ' + '<bean:message key="indiciado"/>';
					  }
					  liga += '</a></td></tr>';
	    	    	  $('#tblProbableResponsable').append(liga);
	    	      }
	    	      if($(this).find('calidad').text() == '<%= Calidades.TESTIGO.getValorId() %>'){
		    	      var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaTestigo(' + $(this).find('involucradoId').text() + ');">';
		    	      if($(this).find('nombre').text()=='null'){
						  liga += 'Desconocido';
					  }else{
						  liga += $(this).find('nombre').text();
					  }
					  liga += '</a></td></tr>';
	    	    	  $('#tblTestigo').append(liga);
	    	      }
	    	      if($(this).find('calidad').text() == '<%= Calidades.TRADUCTOR.getValorId() %>'){
	    	    	  var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaTraductor(' + $(this).find('involucradoId').text() + ');">';
		    	      if($(this).find('nombre').text()=='null'){
						  liga += 'Desconocido';
					  }else{
						  liga += $(this).find('nombre').text();
					  }
					  liga += '</a></td></tr>';
	    	    	  $('#tblTraductor').append(liga);
	    	      }		    
	    	      if($(this).find('calidad').text() == '<%= Calidades.QUIEN_DETUVO.getValorId() %>'){
	    	    	  var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaQuienDetuvo(' + $(this).find('involucradoId').text() + ');">';
		    	      if($(this).find('nombre').text()=='null'){
						  liga += 'Desconocido';
					  }else{
						  liga += $(this).find('nombre').text();
					  }
					  liga += '</a></td></tr>';
	    	    	  $('#tblQuienDetuvo').append(liga);
	    	      }
	    	      if($(this).find('calidad').text() == '<%= Calidades.DEFENDIDO.getValorId() %>'){
		    	      var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificarDefendido(' + $(this).find('involucradoId').text() + ');">';
		    	      if($(this).find('nombre').text()=='null'){
						  liga += 'Desconocido';
					  }else{
						  liga = liga + $(this).find('nombre').text() +' - ' + '<bean:message key="indiciado"/>';
					  }
					  liga += '</a></td></tr>';
	    	    	  $('#tblDefendido').append(liga);
	    	      }
	    	      //TODO QUITAR CUANDO SE CONFIRME REQ. DE QUE NO SE OCUPA
	    	      if($(this).find('calidad').text() == '<%= Calidades.SOLICITANTE.getValorId() %>'){
		    	      var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaProbableResponsable(' + $(this).find('involucradoId').text() + ');">';
		    	      if($(this).find('nombre').text()=='null'){
						  liga += 'Desconocido';
					  }else{
						  liga = liga + $(this).find('nombre').text();
					  }
					  liga += '</a></td></tr>';
	    	    	  $('#tblSolicitante').append(liga);
	    	      }
    	      });
    		}	
		});
	}
	
	/**
	* Funcion que se encarga de limpiar la lista de los involucrados, 
	* con el fin de actualizar con los nuevos involucrados
	*/
	function limpiarListaInvolucrados(){
		$('#tblDenunciante').empty();
		$('#tblVictima').empty();
		$('#tblProbableResponsable').empty();
		$('#tblTestigo').empty();
		$('#tblTraductor').empty();
		$('#tblQuienDetuvo').empty();
		$('#tblDefendido').empty();
		$('#tblSolicitante').empty();
	}
	
	
	//TODO cambiar el nombre  Revisar el idWindows, numeroExpediente, COPIAR FUNCIONALIDAD DEL METODO
	function modificaDenunciante(id){
		modificarDenuncianteDatos(id);
	}
	
	function modificarDenuncianteDatos(id){
		idWindowIngresarDenunciante++;
		$.newWindow({id:"iframewindowIngresarDenunciante" + idWindowIngresarDenunciante, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Denunciante", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarDenunciante" + idWindowIngresarDenunciante,'<iframe src="<%= request.getContextPath() %>/IngresarDenunciante.do?idDenunciante='+id +'&numeroExpediente='+numeroExpediente +'&calidadInv=DENUNCIANTE" width="1100" height="530" />');
	    $("#" +"iframewindowIngresarDenunciante" + idWindowIngresarDenunciante + " .window-maximizeButton").click();
	}
	
	//TODO cambiar el nombre  Revisar el idWindows, numeroExpediente, COPIAR FUNCIONALIDAD DEL METODO
	function modificaVictima(id){
		modificarVictima(id);
	}
	
	//Abre una nueva ventana de crear una nuev victima
	function modificarVictima(id) {
		idWindowIngresarVictima++;
		$.newWindow({id:"iframewindowIngresarVictima" + idWindowIngresarVictima, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar V&iacute;ctima", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarVictima" + idWindowIngresarVictima,'<iframe src="<%= request.getContextPath() %>/IngresarVictima.do?idVictima='+id +'&numeroExpediente='+numeroExpediente +'" width="1100" height="530" />');
	    $("#" +"iframewindowIngresarVictima" + idWindowIngresarVictima + " .window-maximizeButton").click();
	}
	
	//TODO cambiar el nombre  Revisar el idWindows, numeroExpediente, COPIAR FUNCIONALIDAD DEL METODO
	function modificaTestigo(id){
		modificarTestigo(id);
	}
	
	//Crea una nueva ventana de testigo
	function modificarTestigo(id) {
		idWindowIngresarTestigo++;
		$.newWindow({id:"iframewindowIngresarTestigo" + idWindowIngresarTestigo, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Testigo", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarTestigo" + idWindowIngresarTestigo,'<iframe src="<%= request.getContextPath() %>/IngresarTestigo.do?idTestigo='+id+'&numeroExpediente='+numeroExpediente +'" width="1100" height="530" />');
	    $("#" +"iframewindowIngresarTestigo" + idWindowIngresarTestigo + " .window-maximizeButton").click();
	}

	//TODO cambiar el nombre  Revisar el idWindows, numeroExpediente, COPIAR FUNCIONALIDAD DEL METODO
	function modificaTraductor(id){
		modificarTraductor(id);
	}

	//Abre una nueva ventana de ingresar traductor
	function modificarTraductor(id) {
		idWindowIngresarTraductor++;
		$.newWindow({id:"iframewindow" + idWindowIngresarTraductor, statusBar: true, posx:75,posy:30,width:1050,height:530,title:"Modificar Traductor", type:"iframe"});
    	$.updateWindowContent("iframewindow" + idWindowIngresarTraductor,'<iframe src="<%= request.getContextPath() %>/IngresarTraductor.do?idTraductor='+id+'&numeroExpediente='+numeroExpediente +'" width="1050" height="600" />');		
	}

	function modificaQuienDetuvo(id) {			
		idWindowIngresarQuienDetuvo++;
		$.newWindow({id:"iframewindowQuienDetuvo" + idWindowIngresarQuienDetuvo, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Qui&eacute;n Detuvo", type:"iframe"});
	    $.updateWindowContent("iframewindowQuienDetuvo" + idWindowIngresarQuienDetuvo,'<iframe src="<%= request.getContextPath() %>/IngresarQuienDetuvo.do?elemento='+id +'&numeroExpediente='+numeroExpediente +'" width="1100" height="530" />');
	    $("#" +"iframewindowQuienDetuvo" + idWindowIngresarQuienDetuvo + " .window-maximizeButton").click();
	}

	//@deprecated para defensoria
	//TODO cambiar el nombre  Revisar el idWindows, numeroExpediente, COPIAR FUNCIONALIDAD DEL METODO
	function modificaProbableResponsable(id){
		modificarProbResponsable(id);
	}

	//Abre una nueva ventana de probable responsable
	function modificarProbResponsable(id) {
		idWindowIngresarProbResponsable++;
		var sistemaTrad = false;
		var titulo = "Modificar " + etiquetaProbableProp;
		$.newWindow({id:"iframewindowModificarProbResponsable" + idWindowIngresarProbResponsable, statusBar: true, posx:75,posy:30,width:1100,height:530,title:titulo, type:"iframe"});
		$.updateWindowContent("iframewindowModificarProbResponsable" + idWindowIngresarProbResponsable,'<iframe src="<%= request.getContextPath() %>/IngresarProbResponsable.do?idProbableResponsable='+id +'&calidadInv=PROBABLE_RESPONSABLE&numeroExpediente='+numeroExpediente +'&detenido='+muestraDetenido+'&sistemaTrad='+sistemaTrad+'" width="1100" height="530" />');
		$("#" +"iframewindowModificarProbResponsable" + idWindowIngresarProbResponsable + " .window-maximizeButton").click();
	}

	//Abre una nueva ventana de Defendido
	function modificarDefendido(id) {
		idWindowIngresarDefendido++;
		var sistemaTrad = false;
		var titulo = "Modificar Defendido";
		$.newWindow({id:"iframewindowModificarDefendido" + idWindowIngresarDefendido, statusBar: true, posx:75,posy:30,width:1100,height:530,title:titulo, type:"iframe"});
		$.updateWindowContent("iframewindowModificarDefendido" + idWindowIngresarDefendido,'<iframe src="<%= request.getContextPath() %>/IngresarProbResponsable.do?idProbableResponsable='+id +'&calidadInv=DEFENDIDO&numeroExpediente='+numeroExpediente +'&detenido='+muestraDetenido+'&sistemaTrad='+sistemaTrad+'" width="1100" height="530" />');
		$("#" +"iframewindowModificarDefendido" + idWindowIngresarDefendido + " .window-maximizeButton").click();
	}

	/**
	* Funcion que carga la lista de involucrados en el panel del Menu intermedio
	* utilizada en ingresarProbableResponsableView.jsp
	**/
	function cargaProbableResponsable(nombre,id){
		var row=$('#'+id);
		$(row).remove();
		nombre=nombre+" - " + '<bean:message key="indiciado"/>';
		$('#tblProbableResponsable').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaProbableResponsable('+id+')">'+nombre+'</a></td></tr>');
	} 
	
	/**
	* Funcion que carga la lista de involucrados en el panel del Menu intermedio
	* utilizada en ingresarProbableResponsableView.jsp
	**/	
	function cargaDefendido(nombre,id){
		var row=$('#'+id);
		$(row).remove();
		nombre=nombre+" - " + '<bean:message key="indiciado"/>';
		$('#tblDefendido').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificarDefendido('+id+')">'+nombre+'</a></td></tr>');
	}
	
	/**
	* Funcion que carga la lista de involucrados en el panel del Menu intermedio
	* utilizada en ingresarTestigoView.jsp
	**/
	function cargaTestigo(nombre,id){		 
		var row=$('#'+id);
		$(row).remove(); 
		$('#tblTestigo').append('<tr id="'+id+'"><td style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarTestigo" onclick="modificaTestigo('+id+')">'+nombre+'</a></td></tr>');
	}
	
	/**
	* Funcion que carga la lista de involucrados en el panel del Menu intermedio
	* utilizada en ingresarVictimaView.jsp
	**/
	function cargaVictima(nombre,id){		 
		var row=$('#'+id);
		
		$(row).remove(); 
		$('#tblVictima').append('<tr id="'+id+'"><td style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarVictima" onclick="modificaVictima('+id+')">'+nombre+'</a></td></tr>');
	} 

	//******** TAB DELITO ********//
	//TODO Similar al ingresarMenuIntermedio.jsp
	function revisaEsDelitoGraveUno(idRadio)
	{
		//recuperamos el valor de la columna gravedad del delito
		var ret = jQuery("#gridCatDelitos").jqGrid('getRowData',idRadio);
		var retDos = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',idRadio);
		var isGrave="No";
		var delitos=<%=(String)request.getSession().getAttribute("delitosXML")%>;
		var delitosXML=$(''+delitos);
		//mostramos los botones correspondientes dependiento del tipo de delito
		if(ret.Gravedad!=null)
		{
			isGrave=ret.Gravedad;
		}
		else
		{
			isGrave=retDos.Gravedad;
		}
		if(isGrave=="No")
		{
			//revisamos que no exista un delito grave NO seleccionado
			if(existeDelitoGraveEnGrid())
			{
				//se le indica al usuario que seleccione un delito grave como principal
				customAlert("Si no hay reincidencia por parte del Probable Part&iacute;cipe,\n se debe canalizar al Centro de Justicia Restaurativa.");
			}
		}
	}
	
	//TODO Similar al ingresarMenuIntermedio.jsp
	function ocultaMuestraTblsRelacionarDelitos()
	{
		var relacionDelitoPorPErsonaDelito = $(':radio[name=relacionaDelitos]:checked').val();
		if(parseInt(relacionDelitoPorPErsonaDelito)==0)
		{
			//Relacion por persona
			$("#tblRelacionaDelXPersona").show();
			$("#tblRelacionaDelXDelito").hide();
			$("#tblRelacionaDelXTodos").hide();
		}
		else if(parseInt(relacionDelitoPorPErsonaDelito)==1)
		{
			//Relacion por delito
			$("#tblRelacionaDelXDelito").show();
			$("#tblRelacionaDelXPersona").hide();
			$("#tblRelacionaDelXTodos").hide();
		}
		else
		{
			//Relacion por todos
			$("#tblRelacionaDelXDelito").hide();
			$("#tblRelacionaDelXPersona").hide();
			$("#tblRelacionaDelXTodos").show();
			cargaGridConsultaDelitosTodos();
		}	
	}
	
	/**
	*
	* Las funciones son definidas en:
	*	-relacionaDelitoPorPersonaView.jsp
	*	-relacionaDelitoPorDelitoView.jsp
	*	-relacionaDelitoPorTodosView.jsp
	**/
	function gridRelacionarDelitosTabDelito(){
		cargaComboProbableResponsableRDPPV();
		cargaComboDelitosAAsociarRDPD();
		jQuery("#gridDetalleTabDelitoRelDel").jqGrid({ 
			
			datatype: "xml", 
			colNames:['','', '','', '','','', '','','' ], 
			colModel:[ 	{name:'caso',index:'caso', width:20}, 
						{name:'expediente',index:'expediente', width:20}, 
						{name:'defensor',index:'defensor', width:20}, 
						{name:'detenido',index:'detenido', width:20},
						{name:'delito',index:'delito', width:20},
						{name:'institucion',index:'institucion', width:20},
						{name:'fecha',index:'fecha', width:20},
						{name:'hora',index:'hora', width:20},
						{name:'abogado',index:'abogado', width:20},
						{name:'enterado',index:'enterado', width:20}
					],
			pager: jQuery('#pagerGridDetalleTabDelitoRelDel'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc"
				
		}).navGrid('#pagerGridDetalleTabDelitoRelDel',{edit:false,add:false,del:false});

		// $("#gview_gridDetalleFrmPrincipalTres .ui-jqgrid-bdiv").css('height', '200px');	

	}
	
	//******** TAB OBJETOS Y EVIDENCIAS ********//
	//TODO Similar a ingresarMenuIntermedio.jsp
	//Seccion para llenar los grids de los objetos en el visor intermedio
	function consultaGridVehiculosVisor()
	{	
		jQuery("#gridObjsVehiculo").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.VEHICULO.getValorId() %>',datatype: "xml" });
		$("#gridObjsVehiculo").trigger("reloadGrid");
	}
	
	function consultaGridArmasVisor()
	{	
		jQuery("#gridObjsArma").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.ARMA.getValorId() %>',datatype: "xml" });
		$("#gridObjsArma").trigger("reloadGrid");
	}
		
	function consultaGridEquiposComputoVisor()
	{	
		jQuery("#gridObjsEquipoComputo").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.EQUIPO_DE_COMPUTO.getValorId() %>',datatype: "xml" });
		$("#gridObjsEquipoComputo").trigger("reloadGrid");
	}
	
	function consultaGridEquipoTelefonicoVisor()
	{	
		jQuery("#gridObjsEquipoTelefonico").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.EQUIPO_TELEFONICO.getValorId() %>',datatype: "xml" });
		$("#gridObjsEquipoTelefonico").trigger("reloadGrid");
	}
		
	function consultaGridExplosivoVisor()
	{	
		jQuery("#gridObjsExplosivo").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.EXPLOSIVO.getValorId() %>',datatype: "xml" });
		$("#gridObjsExplosivo").trigger("reloadGrid");
	}
	
	function consultaGridSustanciaVisor()
	{	
		jQuery("#gridObjsSustancia").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.SUSTANCIA.getValorId() %>',datatype: "xml" });
		$("#gridObjsSustancia").trigger("reloadGrid");
	}
	
	function consultaGridAnimalVisor()
	{	
		jQuery("#gridObjsAnimal").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.ANIMAL.getValorId() %>',datatype: "xml" });
		$("#gridObjsAnimal").trigger("reloadGrid");
	}
	
	function consultaGridAeronaveVisor()
	{	
		jQuery("#gridObjsAeronave").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.AERONAVE.getValorId() %>',datatype: "xml" });
		$("#gridObjsAeronave").trigger("reloadGrid");
	}
	
	function consultaGridEmbarcacionVisor()
	{	
		jQuery("#gridObjsEmbarcacion").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.EMBARCACION.getValorId() %>',datatype: "xml" });
		$("#gridObjsEmbarcacion").trigger("reloadGrid");
	}
	
	function consultaGridNumerarioVisor()
	{	
		jQuery("#gridObjsNumerario").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.NUMERARIO.getValorId() %>',datatype: "xml" });
		$("#gridObjsNumerario").trigger("reloadGrid");
	}
	
	function consultaGridVegetalVisor()
	{	
		jQuery("#gridObjsVegetal").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.VEGETAL.getValorId() %>',datatype: "xml" });
		$("#gridObjsVegetal").trigger("reloadGrid");
	}
	
	function consultaGridDocumentoOficialVisor()
	{	
		jQuery("#gridObjsDocumentoOficial").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.DOCUMENTO_OFICIAL.getValorId() %>',datatype: "xml" });
		$("#gridObjsDocumentoOficial").trigger("reloadGrid");
	}
	
	function consultaGridJoyaVisor()
	{	
		jQuery("#gridObjsJoya").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.JOYA.getValorId() %>',datatype: "xml" });
		$("#gridObjsJoya").trigger("reloadGrid");
	}
	
	function consultaGridObraDeArteVisor()
	{	
		jQuery("#gridObjsObraDeArte").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.OBRA_DE_ARTE.getValorId() %>',datatype: "xml" });
		$("#gridObjsObraDeArte").trigger("reloadGrid");
	}

	function consultaGridOtrosVisor()
	{	
		jQuery("#gridObjsOtros").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.OTRO.getValorId() %>',datatype: "xml" });
		$("#gridObjsOtros").trigger("reloadGrid");
	}
	
	function consultaGridPericialesVisor()
	{	
		jQuery("#gridObjsPericial").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.PERICIAL.getValorId() %>',datatype: "xml" });
		$("#gridObjsPericial").trigger("reloadGrid");
	}
	//FIN Seccion para llenar los grids de los objetos en el visor intermedio
	
	//Inicia seccion para consultar objetos del Grid
	function consultarVehiculo(idVehiculo){
		 idWindowIngresarVehiculo++;
		$.newWindow({id:"iframewindowIngresarVehiculo" + idWindowIngresarVehiculo, statusBar: true, posx:200,posy:5,width:570, height:600,title:"Consultar veh&iacute;culo", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarVehiculo" + idWindowIngresarVehiculo,'<iframe src="<%= request.getContextPath() %>/IngresarVehiculo.do?numeroExpediente='+numeroExpediente+'&idVehiculo='+idVehiculo+'&tipoObjeto=VEHICULO " width="570" height="600" />');
	    $("#" +"iframewindowIngresarVehiculo"+idWindowIngresarVehiculo+ " .window-maximizeButton").click();

	}
	
	function consultarEquipoComputo(idEquipoComputo){
		idWindowIngresarEquipoDeComputo++;
		$.newWindow({id:"iframewindowIngresarEquipoDeComputo" + idWindowIngresarEquipoDeComputo, statusBar: true, posx:200,posy:50,width:830,height:340,title:"Consultar equipo de c&oacute;mputo", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarEquipoDeComputo" + idWindowIngresarEquipoDeComputo,'<iframe src="<%= request.getContextPath() %>/IngresarEquipoDeComputo.do?numeroExpediente='+numeroExpediente+'&idEquipoComputo='+idEquipoComputo+'&tipoObjeto=EQUIPO_COMPUTO " width="830" height="340" />');
	    $("#" +"iframewindowIngresarEquipoDeComputo"+idWindowIngresarEquipoDeComputo+ " .window-maximizeButton").click();
	}
	
	function consultarEquipoTelefonico(idEquipoTelefonico){
		 idWindowIngresarEquipoTelefonico++;
		$.newWindow({id:"iframewindowIngresarEquipoTelefonico" + idWindowIngresarEquipoTelefonico, statusBar: true, posx:200,posy:50,width:870,height:340,title:"Consultar equipo telef&oacute;nico", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarEquipoTelefonico" + idWindowIngresarEquipoTelefonico,'<iframe src="<%= request.getContextPath() %>/IngresarEquipoTelefonico.do?numeroExpediente='+numeroExpediente+'&idEquipoTelefonico='+idEquipoTelefonico+'&tipoObjeto=EQUIPO_TELEFONICO " width="870" height="340" />');
	    $("#" +"iframewindowIngresarEquipoTelefonico"+idWindowIngresarEquipoTelefonico+ " .window-maximizeButton").click();
	}

	function consultarArma(idArma){
		 idWindowIngresarArma++;
		$.newWindow({id:"iframewindowIngresarArma" + idWindowIngresarArma, statusBar: true, posx:200,posy:50,width:800,height:380,title:"Consultar arma", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarArma" + idWindowIngresarArma,'<iframe src="<%= request.getContextPath() %>/IngresarArma.do?numeroExpediente='+numeroExpediente+'&idArma='+idArma+'&tipoObjeto=ARMA" width="800" height="380" />');
	    $("#" +"iframewindowIngresarArma"+idWindowIngresarArma+ " .window-maximizeButton").click();
	}
	
	function consultarExplosivo(idExplosivo){
		idWindowIngresarExplosivo++;
		$.newWindow({id:"iframewindowIngresarExplosivo" + idWindowIngresarExplosivo, statusBar: true, posx:200,posy:50,width:880, height:330,title:"Consultar explosivo", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarExplosivo" + idWindowIngresarExplosivo,'<iframe src="<%= request.getContextPath() %>/IngresarExplosivo.do?numeroExpediente='+numeroExpediente +'&idExplosivo='+idExplosivo+'&tipoObjeto=EXPLOSIVO " width="880" height="330" />');
	    $("#" +"iframewindowIngresarExplosivo"+idWindowIngresarExplosivo+ " .window-maximizeButton").click();
	}
	
	function consultarSustancia(idSustancia){
		idWindowIngresarSustancia++;
		$.newWindow({id:"iframewindowIngresarSustancia" + idWindowIngresarSustancia, statusBar: true, posx:200,posy:50,width:900,height:330,title:"Consultar sustancia", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarSustancia" + idWindowIngresarSustancia,'<iframe src="<%= request.getContextPath() %>/IngresarSustancia.do?numeroExpediente='+numeroExpediente+'&idSustancia='+idSustancia+'&tipoObjeto=SUSTANCIA" width="900" height="330" />');
	    $("#" +"iframewindowIngresarSustancia"+idWindowIngresarSustancia+ " .window-maximizeButton").click();
	}
	
	function consultarAnimal(idAnimal){
		idWindowIngresarAnimal++;
		$.newWindow({id:"iframewindowIngresarAnimal" + idWindowIngresarAnimal, statusBar: true, posx:200,posy:50,width:900,height:330,title:"Consultar animal", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarAnimal" + idWindowIngresarAnimal,'<iframe src="<%= request.getContextPath() %>/IngresarAnimal.do?numeroExpediente='+numeroExpediente+'&idAnimal='+idAnimal+'&tipoObjeto=ANIMAL" width="900" height="330" />');
	    $("#" +"iframewindowIngresarAnimal"+idWindowIngresarAnimal+ " .window-maximizeButton").click();
	}
		
	function consultarAeronave(idAeronave){
		idWindowIngresarAeronave++;
		$.newWindow({id:"iframewindowIngresarAeronave" + idWindowIngresarAeronave, statusBar: true, posx:200,posy:10,width:600,height:530,title:"Consultar aeronave", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarAeronave" + idWindowIngresarAeronave,'<iframe src="<%= request.getContextPath() %>/IngresarAeronave.do?numeroExpediente='+numeroExpediente+'&idAeronave='+idAeronave+'&tipoObjeto=AERONAVE" width="600" height="530" />');
	    $("#" +"iframewindowIngresarAeronave"+idWindowIngresarAeronave+ " .window-maximizeButton").click();
	}
	
	function consultarEmbarcacion(idEmbarcacion){
		idWindowIngresarEmbarcacion++;
		$.newWindow({id:"iframewindowIngresarEmbarcacion" + idWindowIngresarEmbarcacion, statusBar: true, posx:200,posy:10,width:600,height:530,title:"Consultar embarcaci&oacute;n", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarEmbarcacion" + idWindowIngresarEmbarcacion,'<iframe src="<%= request.getContextPath() %>/IngresarEmbarcacion.do?numeroExpediente='+numeroExpediente+'&idEmbarcacion='+idEmbarcacion+'&tipoObjeto=EMBARCACION" width="600" height="530" />');
	    $("#" +"iframewindowIngresarEmbarcacion"+idWindowIngresarEmbarcacion+ " .window-maximizeButton").click();
	}
	
	function consultarNumerario(idNumerario){
		idWindowIngresarNumerario++;
		$.newWindow({id:"iframewindowIngresarNumerario" + idWindowIngresarNumerario, statusBar: true, posx:200,posy:50,width:800, height:350,title:"Consultar numerario", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarNumerario" + idWindowIngresarNumerario,'<iframe src="<%= request.getContextPath() %>/IngresarNumerario.do?numeroExpediente='+numeroExpediente +'&idNumerario='+idNumerario+'&tipoObjeto=NUMERARIO " width="800" height="350" />');
	    $("#" +"iframewindowIngresarNumerario"+idWindowIngresarNumerario+ " .window-maximizeButton").click();
	}
		
	function consultarVegetal(idVegetal){
		idWindowIngresarVegetal++;
		$.newWindow({id:"iframewindowIngresarVegetal" + idWindowIngresarVegetal, statusBar: true, posx:200,posy:50,width:800, height:350,title:"Consultar vegetal", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarVegetal" + idWindowIngresarVegetal,'<iframe src="<%= request.getContextPath() %>/IngresarVegetal.do?numeroExpediente='+numeroExpediente+'&idVegetal='+idVegetal+'&tipoObjeto=VEGETAL" width="800" height="350" />');
	    $("#" +"iframewindowIngresarVegetal"+idWindowIngresarVegetal+ " .window-maximizeButton").click();
	}
	
	function consultarDocumentoOficial(idDocumentoOficial){
		idWindowIngresarDocumentoOficial++;
		$.newWindow({id:"iframewindowIngresarDocumentoOficial" + idWindowIngresarDocumentoOficial, statusBar: true, posx:200,posy:50,width:800,height:350,title:"Consultar documento oficial", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarDocumentoOficial" + idWindowIngresarDocumentoOficial,'<iframe src="<%= request.getContextPath() %>/IngresarDocumentoOficial.do?numeroExpediente='+numeroExpediente+'&idDocOfic='+idDocumentoOficial+'&tipoObjeto=DOCUMENTO_OFICIAL" width="800" height="350" />');
	    $("#" +"iframewindowIngresarDocumentoOficial"+idWindowIngresarDocumentoOficial+ " .window-maximizeButton").click();
	}

	function consultarJoya(idJoya){
		idWindowIngresarJoya++;
		$.newWindow({id:"iframewindowIngresarJoya" + idWindowIngresarJoya, statusBar: true, posx:200,posy:50,width:850,height:380,title:"Consultar joya", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarJoya" + idWindowIngresarJoya,'<iframe src="<%= request.getContextPath() %>/IngresarJoya.do?numeroExpediente='+numeroExpediente+'&idJoya='+idJoya+'&tipoObjeto=JOYA" width="850" height="380" />');
	    $("#" +"iframewindowIngresarJoya"+idWindowIngresarJoya+ " .window-maximizeButton").click();
	}
		
	function consultarObraDeArte(idObraDeArte){
		idWindowIngresarObraDeArte++;
		$.newWindow({id:"iframewindowObraDeArte" + idWindowIngresarObraDeArte, statusBar: true, posx:200,posy:50,width:850,height:380,title:"Consultar obra de arte", type:"iframe"});
	    $.updateWindowContent("iframewindowObraDeArte" + idWindowIngresarObraDeArte,'<iframe src="<%= request.getContextPath() %>/IngresarObraDeArte.do?numeroExpediente='+numeroExpediente+'&idObraArte='+idObraDeArte+'&tipoObjeto=OBRA_DE_ARTE" width="850" height="380" />');
	    $("#" +"iframewindowObraDeArte"+idWindowIngresarObraDeArte+ " .window-maximizeButton").click();
	}
	
	function consultarOtros(idOtros){
		idWindowIngresarOtros++;
		$.newWindow({id:"iframewindowOtros" + idWindowIngresarOtros, statusBar: true, posx:200,posy:50,width:800,height:450,title:"Consultar otros", type:"iframe"});
	    $.updateWindowContent("iframewindowOtros" + idWindowIngresarOtros,'<iframe src="<%= request.getContextPath() %>/IngresarOtros.do?numeroExpediente='+numeroExpediente+'&idOtros='+idOtros+'&tipoObjeto=OTRO" width="800" height="450" />');
	    $("#" +"iframewindowOtros"+idWindowIngresarOtros+ " .window-maximizeButton").click();
	}
	
	function consultarPericial(idOtros){
		idWindowIngresarPericial++;
		$.newWindow({id:"iframewindowPericial" + idWindowIngresarPericial, statusBar: true, posx:200,posy:50,width:800,height:450,title:"Consultar pericial", type:"iframe"});
	    $.updateWindowContent("iframewindowPericial" + idWindowIngresarPericial,'<iframe src="<%= request.getContextPath() %>/IngresarPericial.do?numeroExpediente='+numeroExpediente+'&idOtros='+idOtros+'&tipoObjeto=PERICIAL" width="800" height="450" />');
	    $("#" +"iframewindowPericial"+idWindowIngresarPericial+ " .window-maximizeButton").click();
	}
	//Finaliza seccion para consultar objetos
	
	
	//******** TAB CADENA DE CUSTODIA ********//
	//Se omite el parametro de pantallaSolicitada 
	function consultarRegCadenaCustodia()
	{
		idWindowAsntarRegCadCus++;
		$.newWindow({id:"iframewindowCnsltrRegCadCus" + idWindowAsntarRegCadCus, statusBar: true, posx:200,posy:50,width:1100, height:760,title:"Cadena de custodia", type:"iframe"});
	    $.updateWindowContent("iframewindowCnsltrRegCadCus" + idWindowAsntarRegCadCus,'<iframe src="<%= request.getContextPath() %>/AsentarRegCadCustodia.do?consultaCadena=1&numeroExpediente='+numeroExpediente+'&operacion=2" width="1100" height="760" />');
	    $("#" +"iframewindowCnsltrRegCadCus" + idWindowAsntarRegCadCus + " .window-maximizeButton").click();
	}
	
	//******** TAB DILIGENCIAS ********//
	//Ver diligencias.jsp  y cmpDiligenciasExpediente.js
	//inicilizarTabDiligencias(<%=numeroExpedienteIdOK%>);
	
	//******** TAB AUDIENCIAS ********//
	function gridAudiencia() {
		jQuery("#gridAudiencia").jqGrid({
			url:'<%= request.getContextPath()%>/consultarAudienciasDefensor.do', 
			datatype: "xml", 						
			colNames:['Caso','Car&aacute;cter','Tipo de Audiencia','Fecha de Audiencia','Sala'], 
			colModel:[{name:'caso',	 	index:'2002', 		width:200, align:"let"},
			          {name:'caracter',	index:'2037', 	width:100, align:"center"},
			          {name:'tipo',	 	index:'2017', 	    width:120, align:"center"},
			          {name:'fechaHora',index:'2018',	width:200, align:"center"},
			          {name:'sala' ,	index:'2029', 		width:110, align:"center"}
					],				
			pager: jQuery('#pagerAudiencia'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			autoheight:true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			onSelectRow: function(rowID) {
				idAudiencia=rowID;				
			}
		}).navGrid('#pagerAudiencia',{edit:false,add:false,del:false});
		jQuery("#gridAudiencia").trigger('reloadGrid');
	}
	
	//******** TAB PERICIALES ********//
	//Ver solicitudesTabPericialesView.jsp
	
	//******** TAB ACTUACIONES ********//
	//Ver actuaciones.jsp y cmpActuacionesExpediente.js
	
	//******** TAB NOTAS ********//
	//TODO Similar a ingresarMenuIntermedio.jsp solo cambia el valor para el parametro de idNumeroExpediente  
	/*
	*Consultar las notas del expediente
	*POR EL MOMENTO SOLO SE CONSULTA UNA NOTA
	*/
	function consultarNotas(){
		var notas=$('#editor1').val();
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarNotasExpediente.do?idNumeroExpediente='+<%=numeroExpedienteIdOK%>,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('notaExpedienteDTO').each(function(){
					cargaNota($(this).find('idNota').text(),$(this).find('nombreNota').text());
				});
			}
		});
	}
	
	function cargaNota(id,nombre){
		var row=$('#rowNota_'+id);
		$(row).remove();
		$('#tablaNotasExpediente').append('<tr id="rowNota_'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarNota_'+id+'" onclick="notaExpediente('+id+')">'+nombre+'</a></td></tr>');
		//cerrarVentanaNota();
	}
	
	function notaExpediente(idNota){
		idWindowGenerarNotas++;
		$.newWindow({id:"iframewindowGenerarNotas" + idWindowGenerarNotas, statusBar: true, posx:200,posy:50,width:700, height:450,title:"Nota de Expediente", type:"iframe", modal:true});
	    $.updateWindowContent("iframewindowGenerarNotas" + idWindowGenerarNotas,'<iframe src="<%= request.getContextPath() %>/capturarNotaExpediente.do?idNumeroExpedienteOp='+<%=numeroExpedienteIdOK%>+'&idNota='+idNota+' " width="700" height="450" />');
	}

	function cerrarVentanaNota(){
		var pantalla ="iframewindowGenerarNotas";
		pantalla += idWindowGenerarNotas;
		$.closeWindow(pantalla);
	}
	
	//******** TAB DOCUMENTOS DEFENSORIA ********//
	function documentos(){
		//alert("visorExpedienteDefensoria.jsp-documentos");
		//carga grid de documentos	
		jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
			url:'<%=request.getContextPath()%>/consultarDocumentosDefensoria.do?tipo=3&idExpedienteop='+<%=numeroExpedienteIdOK%>+'&expedienteId='+<%=idExpedienteOK%>, 
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
			width:1100,
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
		    		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?documentoId='+id+'&numeroUnicoExpediente='+numeroExpediente+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&esTransaccional='+true+'&ocultarGuardadoParcial='+ocultarGuardadoParcial+'" width="1140" height="400" />');
		    		    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
					}
				 }
			},
			sortorder: "desc"
		}).navGrid('#pager1',{edit:false,add:false,del:false});
		$("#gview_gridDetalleFrmPrincipal .ui-jqgrid-bdiv").css('height', '500px');
		$("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
	}

	/**
	* Funcion invocada desde el generarDocumentoSinCaso, 
	* despues de generar el pdf.
	*/
	function cerrarVentanaDocumento(id){
		var pantalla ="iframewindowGenerarDocumento";
		pantalla += id;
		$.closeWindow(pantalla);
	}
	
	//Variable para controlar el action para consultar pdf's
	var accionConsultarPdf="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do";
	
	/*
	*Funcion que aplica submit a la forma para abrir el documento solicitado
	*id= id del documento seleccionado en el grid de documentos
	*As&iacute; se obtenia anteriormente:
	*	
	*document.formaArchivoD.documentoId.value = id;
	*document.formaArchivoD.submit();
	*/
	function consultaPDF(id){
		document.formaArchivoD.action=accionConsultarPdf+"?documentoId="+id;
		document.formaArchivoD.submit();
	}
	
	//******** TAB BITACORA ********//
		function cargaGridBitacora() {
		jQuery("#gridBitacora").jqGrid({
					url : '<%= request.getContextPath()%>/consultarBitacoraPorExpediente.do?numeroExpedienteId='+<%=numeroExpedienteIdOK%>+'', 
					datatype: "xml", 						
					colNames:['Fecha','Hora','Movimiento','Descripci&oacute;n'], 
					colModel:[{name:'Fecha',	 	index:'1',  width:200, align:"center"},
					          {name:'Hora',	        index:'4', 	width:200, align:"center", sortable: false},
					          {name:'Movimiento',	index:'2', 	width:200, align:"center"},
					          {name:'Descripcion',	index:'3',  width:400, align:"center", sortable: false}
							],				
					pager: jQuery('#pagerAudiencia'),
					rowNum:20,
					rowList:[20,40,60],
					autowidth: true,
					sortname: '1',
					viewrecords: true,
					sortorder: "desc"
		}).navGrid('#pagerGridBitacora',{edit:false,add:false,del:false});
			jQuery("#gridBitacora").trigger('reloadGrid');
	}

	
    </script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Menu Defensor</title>			
</head>
<body>
<!-- Comienza Tabs Principal del menu -->
<div class="ui-layout-north"></div>

<div id="tabedMenuExpedienteDefensoria">
	<ul>
		<li id="tabResumenExped"><a href="#tabsconsultaprincipal-1" onclick="datosGenerales()" >Resumen</a></li>
		<li id="tabResumenSol"><a href="#tabsVisorDefensoria-1" >Resumen de la Solicitud</a></li>
		<li id="tabDatosInteres"><a href="#tabsconsultaprincipal-2" >Interesado</a></li>
		<li id="tabMotivoAsesor"><a href="#tabsconsultaprincipal-3" >Motivo Asesor&iacute;a</a></li>
		<!-- funcion definida en cmpInvolucradosExpedientes.js --> 
		<!-- <li id="tabInvolucrados"><a href="#tabsconsultaprincipal-4" onclick="cargarTabInvolucradosOnClick()">Involucrados</a></li> -->
		<li class="tabTabsInv"><a href="#tabs-1" onclick="cargarInvolucradosExpedienteMI(<%=idNumeroExpediente%>);">Involucrados</a></li>
		<!-- deprecated funcion definida en seleccionarDelitoViewDefensoria.jsp (evaluar el impacto y eliminar el archivo)-->
		<!-- <li id="tabTabsDelito"><a href="#tabsconsultaprincipal-14" onclick="cargaComboProbableResponsableRDPPVDelito()">Delito</a></li> -->
		<li id="tabTabsDelito" class="tabTabsRelacionesDelitosPersonas"><a href="#tabsconsultaprincipal-14" id="tapDelitoYRelaciones" onclick="cargarGridsInvolucradosRelDelitoPersonaPG()">Delito y Relaciones Delito-Persona</a></li>
		<li id="tabObjetsyEvids"><a href="#tabsconsultaprincipal-5" >Objetos y evidencias</a></li>
		<li id="tabCadebaCustod"><a href="#tabsconsultaprincipal-6" >Cadena de custodia</a></li>
		<li id="tab_Diligencias"><a href="#tabsconsultaprincipal-7" >Diligencias</a></li>
		<li id="tabAudienciasEx"><a href="#tabsconsultaprincipal-8"  onclick="gridAudiencia()">Audiencias</a></li>
		<li id="tabPericialesEx"><a href="#tabsconsultaprincipal-9" id="tapPericiales">Periciales</a></li>
		<li id="tab_Actuaciones"><a href="#tabsconsultaprincipal-10" onclick="cagarGridActuacionesPorDefendido();">Actuaciones</a></li>
		<li id="tab_Notas_Exped"><a href="#tabsconsultaprincipal-11" onclick="consultarNotas()">Notas</a></li>
		<li id="tabDocumentosEx"><a href="#tabsconsultaprincipal-12"  onclick="documentos()">Documentos</a></li>
		<li id="tab_BitacoraExp"><a href="#tabsconsultaprincipal-13" onclick="cargaGridBitacora()">Bit&aacute;cora</a></li>
	</ul>
	
	<!--Inicia Resumen Expediente Cuando tiene una solicitud-->
	<!--Comienza div principal para las tabs-->
	<div id="tabsPrincipalesVisorDefensoria">
		<jsp:include page="visorResumenSolicitudDefensoria.jsp"></jsp:include>
	</div>
	<!--Termina div principal para las tabs-->
	
	<div id="tabsconsultaprincipal-1" style="height: 600px !important;">			

		<!-- Inicia Resumen Expediente con Carpeta de Investigaci&oacute;n -->
		<div id="resumen2" class="fondoClaroAp">
			<table width="1142px"  height="612px" border="0" cellspacing="0" cellpadding="0" class="back_generales">
			  <tr><td colspan="6">&nbsp;</td></tr>			
			  <tr style="border-bottom-style: solid; border: 1;background-image:">
			    <td id="espacioEntrelbsEstatus" width="300" style="font-size:14px; background-color:" align="right">
			    	<strong>Estatus del Expediente:</strong>&nbsp;
			    	<label id="estatusExpe"></label>
			    </td>
			    <td width="19" style="font-size:14px; background-color:" >&nbsp;</td>
			    <td width="4">&nbsp;</td>
			    <td align="left" width="507" style="font-size:14px; background-color:">
					<strong>Estatus de Actuaci&oacute;n:</strong>&nbsp;
					<label id="estatusActuacion"></label>
				</td>
			    <!--  <td id="idsNumerosDelExpediente" width="250" style="font-size:14px; background-color:">
			    	<strong>Folios de Expediente</strong>
					<select id="cbxNumerosExpediente" style="width:170px">
							
					</select>			    	
			     </td>-->
			     <td id="idEtapaExpediente" width="250" style="font-size:14px; background-color:">
			    	<strong>Etapa del Expediente</strong>
			    	<label id="etapaExpediente"></label>
			    </td>
			  </tr>			  
			  <tr id="objetosTR">
			  	<td></td>
				<td></td>
  			    <td id="resumenObjetoslb" width="507" align="left" style="font-size:14px; background-color:"><strong>Resumen de objetos:</strong></td>
			    <td width="4" align="right" style="font-size:14px; background-color:"><strong>Resumen de involucrados:</strong></td>
			    <td width="250" align="left" style="font-size:14px; background-color:">&nbsp;</td>	
		      </tr>
		      <tr>
			    <td  colspan="6" height="20px">
			    	<table width="978" border="0" cellpadding="0" cellspacing="0" class="linea_down_gral" align="center">
					  <tr>
					  </tr>
					</table>			    	
			    </td>
			  </tr>
			  <tr id="objetosDescripcionTR" valign="top">
			    <td align="right"></td>
			    <td></td>
			    <td id="objetosTD"  rowspan="16" align="center" width="507" style="background-color:" valign="top">
			   
			   	 <table  border="0" cellpadding="0" cellspacing="0" width="99%">
		          <tr>
		            <td width="145" align="right" nowrap style="background-color:">Veh&iacute;culos:</td>
		            <td width="332" id="Vehiculos">&nbsp;</td>
		            <td id="imgVehiculo" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td align="right" style="background-color:">Equipos de c&oacute;mputo:</td>
		            <td id="EquiposDeComputo" >&nbsp;</td>
		            <td id="imgEquiposDeComputo" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td align="right" style="background-color:">Equipos Telef&oacute;nicos:</td>
		            <td id="EquiposTelefonicos">&nbsp;</td>
		            <td id="imgEquiposTelefonicos" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td align="right" style="background-color:">Armas:</td>
		            <td id="Armas">&nbsp;</td>
		            <td id="imgArmas" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td align="right" style="background-color:">Explosivos:</td>
		            <td id="Explosivos">&nbsp;</td>
		            <td id="imgExplosivos" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td align="right" style="background-color:">Sustancias:</td>
		            <td id="Sustancias">&nbsp;</td>
		            <td id="imgSustancias" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td align="right" style="background-color:">Animales:</td>
		            <td id="Animales">&nbsp;</td>
		            <td id="imgAnimales" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td align="right" style="background-color:">Aeronaves:</td>
		            <td id="Aeronaves">&nbsp;</td>
		            <td id="imgAeronaves" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td align="right" style="background-color:">Embarcaci&oacute;n:</td>
		            <td id="Embarcacion">&nbsp;</td>
		            <td id="imgEmbarcacion" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td align="right" style="background-color:">Numerario:</td>
		            <td id="Numerario">&nbsp;</td>
		            <td id="imgNumerario" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td align="right" style="background-color:">Vegetal:</td>
		            <td id="Vegetal">&nbsp;</td>
		            <td id="imgVegetal" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td align="right" style="background-color:">Documento oficial:</td>
		            <td id="DocumentoOficial">&nbsp;</td>
		            <td id="imgDocumentoOficial" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td align="right" style="background-color:">Joya:</td>
		            <td id="Joya">&nbsp;</td>
		            <td id="imgJoya" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td align="right" style="background-color:">Obra de arte:</td>
		            <td id="ObraDeArte">&nbsp;</td>
		            <td id="imgObraDeArte" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td height="19" align="right" style="background-color:">Otros:</td>
		            <td id="Otros">&nbsp;</td>
		            <td id="imgOtros" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td height="19" align="right" style="background-color:">Periciales:</td>
		            <td id="Periciales">&nbsp;</td>
		            <td id="imgPericiales" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
				</table>
				</td>
                
			    <td id="involucradosTD" rowspan="6" align="right" style="background-color:" colspan="2">                
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" >
                  <tr id="DenunciantesTR">
                    <td id="denuncianteslb" width="128" align="right" style="background-color:"></td>
                    <td width="127" id="Denunciantes">&nbsp;</td>
                    <td id="imgDenunciantes" width="15"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
                  </tr>
                  <tr id="VictimasTR">
                    <td id="victimaslb" align="right" style="background-color:"></td>
                    <td id="Victimas">&nbsp;</td>
                    <td id="imgVictimas" width="15"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
                  </tr>
                  <tr id="ProbableResponsableTR">
                    <td id="probableResponsableslb" align="right" style="background-color:"></td>
                    <td id="ProbablesResponsables">&nbsp;</td>
                    <td id="imgProbablesResponsables" width="15"><img title="Contiene mas registros" width="10px" src="<%=request.getContextPath() %>/resources/images/add.png"></td>
                  </tr>
                  <tr id="testigosTR">
                    <td align="right" style="background-color:">Testigos:</td>
                    <td id="Testigos">&nbsp;</td>
                    <td id="imgTestigos" width="15"><img title="Contiene mas registros" width="10px" src="<%=request.getContextPath() %>/resources/images/add.png"></td>
                  </tr>
                  <tr id="traductoresTR">
                    <td align="right" style="background-color:">Traductores:</td>
                    <td id="Traductores">&nbsp;</td>
                    <td id="imgTraductores" width="15"><img title="Contiene mas registros" width="10px" src="<%=request.getContextPath() %>/resources/images/add.png"></td>
                  </tr>
                  <tr id="quienDetuvoTR">
                    <td align="right" style="background-color:">Qui&eacute;n detuvo:</td>
                    <td id="QuienDetuvo">&nbsp;</td>
                    <td id="imgQuienDetuvo" width="15"><img title="Contiene mas registros" width="10px" src="<%=request.getContextPath() %>/resources/images/add.png"></td>
                  </tr>
                  <tr id="amparo_s">
                    <td width="158" align="right">Amparo(s):</td>
                    <td width="82" id="lblAmparos">&nbsp;</td>
                  </tr>
                </table>
                    
                    
				</td>
			 		    
			  </tr>
			  <tr>
			    <td align="right">&nbsp;&nbsp;&nbsp;&nbsp;<label id="origenNumeroExpediente"></label></td>
			  </tr>
			  <tr>
			  	<td  style="background-color:"align="right">
			    	<span style="border-left:#000000; border-top:#000000; border-bottom-width:4; font-size:14px; background-color:">
			    		<img src="<%=request.getContextPath()%>/resources/images/icn_doc_chek.png"><strong>Tipo:</strong>
			    	</span>
			    </td>
			  </tr>
			  <tr>
			    <td></td>
			  </tr>
			  <tr>
			  	<td id="origenExpe" align="right"></td>
			  </tr>
			  <tr>
			  	<td  id="anonimoDenun" align="right">&nbsp;</td>
			  </tr>
			  <tr>
			    <td align="right"><span style="border-left:#000000; border-top:#000000; border-bottom-width:4; font-size:14px; background-color:; display:none;"><strong>Canalizado a:</strong></span> </td>
			    <td></td>
			  </tr>
			  <tr>
			    <td id="fechaApertura" align="right">&nbsp;</td>
			  </tr>
			  <tr>
			    <td align="right"><strong>Responsable del Tr&aacute;mite:</strong></td>
			    <td></td>
			  </tr>
			  <tr>
			    <td id="responsableTramite" align="right">&nbsp;</td>
			  </tr>
			  <tr>
			    <td align="right"><span id="spanGralJAR">Justicia Alternativa Restaurativa</span></td>
			  </tr>
			  <tr>
			    <td align="right"><span id="spanGralUI">Unidad de Investigaci&oacute;n: </span><span id="spanInfoGralUI"></span></td>
			  </tr>
			  <tr>
			    <td align="right"><span id="spanGralIE">Instituci&oacute;n Externa: </span><span id="spanInfoGralIE"></span></td>
			  </tr>
			  <tr>
			    <td>&nbsp;</td>
			  </tr>
			  <tr>
			    <td>&nbsp;</td>
			  </tr>
			</table>
			
		
		</div>
		<!--Termina Resumen Expediente con Carpeta de Investigaci&oacute;n -->
		
	</div>
	<div id="tabsconsultaprincipal-2" style="height: 600px !important;">
		<table width="100%" >
				<tr>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td align="right">
						 <input type="button" class="btn_guardar" value="Guardar" id="btnGuardar" />
					</td>
				</tr>
		</table>
	  <!--  ACORDEON CU PROBABLE RESPONSABLE-INGRESAR ORGANIZACION  -->
           <div id="cedulaIdentificacion">
             <dl>
               <dt id="cejaDatosGenerales">Datos Generales</dt>
                 <dd>	
                   <jsp:include page="/WEB-INF/paginas/datosGeneralesView.jsp"/>
                 </dd>
               <dt id="cejaDomicilio">Domicilio</dt>
                 <dd>
                   <jsp:include page="/WEB-INF/paginas/ingresarDomicilioView.jsp"/>
                 </dd>
               <dt id="cejaMediosContacto">Medios de Contacto</dt>
                   <dd>
                     <jsp:include page="/WEB-INF/paginas/ingresarMediosContactoView.jsp"/>
                   </dd>
                 </dl>
               </div>	
	</div>
	<div id="tabsconsultaprincipal-3" style="height: 600px !important;">
		<textarea class="jquery_ckeditor" cols="150" id="editor2" name="editor2" rows="10"></textarea>
	</div>
	
	<!--  Deprecated -->
<%-- 	<div id="tabsconsultaprincipal-4" style="height: 600px !important;">
		<jsp:include page="involucra2.jsp"></jsp:include>
	</div>
 --%>	
	<!--COMIENZAN TABS INFERIORES DE INDIVIDUO-->
	<div id="tabs-1" class="tabTabsInv">		
		<div id="tabschild" class="tabs-bottom">
			<ul>			
				<li><a href="#tabschild-7">Para quien se solicita</a></li>
				<!-- TODO QUITAR AL VALIDADR REQ.  <li><a href="#tabschild-8">Solicitante ciudadano</a></li> -->
				<!-- //TODO Revisar si se ocuparan 	<li id="tabCedulaIdentificacion"><a href="#tabsInvolucrado-3" onclick="cargarCedulaDeIdentificacion()">Cedula de Identificacion</a></li>
		<li id="tabInvDefendidoInvitado"><a href="#tabsInvolucrado-4" onclick="cargarInvitado()">Invitado</a></li>
		<li id="tabSolicitanteJusticiaR"><a href="#tabsInvolucrado-5" onclick="cargarSolicitanteJusticiaRestaurativa()">Solicitante de justicia restaurativa</a></li>  -->				
				<li><a href="#tabschild-1">Denunciante</a></li>
				<li><a href="#tabschild-2">V&iacute;ctima</a></li>
				<li><a href="#tabschild-3" id="tapProbableResposable"></a></li>
				<li><a href="#tabschild-4">Testigo</a></li>
				<li><a href="#tabschild-5">Traductor</a></li>
				<li class="tabTabsQuienDetuvo"><a href="#tabschild-6">Qui&eacute;n detuvo</a></li>
			</ul>
			<div id="tabschild-7">
				<div style="width: 1042px; height: 490px;" class="back_hechos">
					<table width="25%" cellpadding="0" cellspacing="0" id="tblDefendido" style="padding: .5cm;">
<!-- 						<tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevoDefendido"><input type="button" class="ui-button ui-corner-all ui-widget" id="btnNuevoDefendido" value=""/></a></td>
						</tr> -->
 					</table>
				</div>
			</div>
			<!-- TODO QUITAR AL VALIDAR REQ. <div id="tabschild-8">
				<div style="width: 1042px; height: 490px;" class="back_hechos">
					<table width="25%" cellpadding="0" cellspacing="0" id="tblSolicitante" style="padding: .5cm;">
	 					<tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevoSolicitante"><input type="button" class="ui-button ui-corner-all ui-widget" id="btnNuevoSolicitante" value=""/></a></td>
						</tr>
					</table>
				</div>
			</div> -->
			<div id="tabschild-1">
				<div style="width: 1042px; height: 490px;" class="back_hechos">
					<table width="25%" cellpadding="0" cellspacing="0" id="tblDenunciante" style=" padding: .5cm;">
						<tr  vAlign="Top" >
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="crearDenunciante"><input type="button" class="ui-button ui-corner-all ui-widget" value="Ingresar Denunciante"/></a></td>
						</tr>
					</table>
				</div>
			</div>
			<div id="tabschild-2">
				<div style="width: 1042px; height: 490px;" class="back_hechos">
					<table width="25%" cellpadding="0" cellspacing="0" id="tblVictima" style="padding: .5cm;">
						<tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevaVictima"><input type="button" class="ui-button ui-corner-all ui-widget" value="Ingresar V&iacute;ctima"/></a></td>
						</tr>
					</table>
				</div>
			</div>
			<div id="tabschild-3">
				<div style="width: 1042px; height: 490px;" class="back_hechos">
					<table width="25%" cellpadding="0" cellspacing="0" id="tblProbableResponsable" style="padding: .5cm;">
						<tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevoProbResponsable"><input type="button" class="ui-button ui-corner-all ui-widget" id="btnNuevoProbResponsable" value=""/></a></td>
						</tr>
					</table>
				</div>
			</div>
			<div id="tabschild-4">
				<div style="width: 1042px; height: 490px;" class="back_hechos">
					<table width="25%" cellpadding="0" cellspacing="0" id="tblTestigo" style="padding: .5cm;">
						<tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevoTestigo"><input type="button" class="ui-button ui-corner-all ui-widget" value="Ingresar Testigo"/></a></td>
						</tr>
					</table>
				</div>
			</div>
			<div id="tabschild-5">
				<div style="width: 1042px; height: 490px;" class="back_hechos">
					<table width="25%" cellpadding="0" cellspacing="0" id="tblTraductor" style="padding: .5cm;">
						<tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevoTraductor"><input type="button" class="ui-button ui-corner-all ui-widget" value="Ingresar Traductor"/></a></td>
						</tr>
					</table>
				</div>
			</div>
			<div id="tabschild-6">
				<div style="width: 1042px; height: 490px;" class="back_hechos" style="padding: .5cm;">
					<table width="25%" cellpadding="0" cellspacing="0" id="tblQuienDetuvo">
						<tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="quienDetuvo"><input type="button" class="ui-button ui-corner-all ui-widget" value="Ingresar Qui&eacute;n Detuvo"/></a></td>
						</tr>
					</table>
				</div>	
			</div>
		</div>
	</div>
	<!--TERMINAN TABS INFERIORES DE INDIVIDUO-->
	
	<!--COMIENZA TABS OBJETOS Y EVIDENCIAS-->	
	<!-- <div id="tabsconsultaprincipal-5" style="height: 600px !important;"> -->
	<div id="tabsconsultaprincipal-5" class="tabTabsObjs">
	
		<div id="tabschild4" class="tabs-bottom">
		
			<ul>
				<li><a onclick="consultaGridVehiculosVisor()" href="#tabschild4-7">Veh&iacute;culo</a></li>
				<li><a onclick="consultaGridEquiposComputoVisor()" href="#tabschild4-1">Equipo de c&oacute;mputo</a></li>
				<li><a onclick="consultaGridEquipoTelefonicoVisor()"href="#tabschild4-2">Equipo telef&oacute;nico</a></li>
				<li><a onclick="consultaGridArmasVisor()" href="#tabschild4-3">Arma</a></li>
				<li><a onclick="consultaGridExplosivoVisor()" href="#tabschild4-4">Explosivo</a></li>
				<li><a onclick="consultaGridSustanciaVisor()" href="#tabschild4-5">Sustancia</a></li>
				<li><a onclick="consultaGridAnimalVisor()" href="#tabschild4-6">Animal</a></li>					
				<li><a onclick="consultaGridAeronaveVisor()" href="#tabschild4-8">Aeronave</a></li>
				<li><a onclick="consultaGridEmbarcacionVisor()" href="#tabschild4-9">Embarcaci&oacute;n</a></li>
				<li><a onclick="consultaGridNumerarioVisor()" href="#tabschild4-11">Numerario</a></li>
				<li><a onclick="consultaGridVegetalVisor()" href="#tabschild4-12">Vegetal</a></li>
				<li><a onclick="consultaGridDocumentoOficialVisor()" href="#tabschild4-13">Documento oficial</a></li>
				<li><a onclick="consultaGridJoyaVisor()" href="#tabschild4-14">Joya</a></li>
				<li><a onclick="consultaGridObraDeArteVisor()" href="#tabschild4-15">Obra de arte</a></li>
				<li><a onclick="consultaGridOtrosVisor()" href="#tabschild4-16">Otros</a></li>		
				<li><a onclick="consultaGridPericialesVisor()" href="#tabschild4-17">Periciales</a></li>		
			</ul>
			
			<!--EQUIPO DE COMPUTO-->
			<div id="tabschild4-1">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblEquipoComputo">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="nuevoEquipoDeComputo" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsEquipoComputo"></table>
				<div id="pagerGridObjsEquipoComputo"></div>
			</div>
			
			<!--EQUIPO TELEFONICO-->
			<div id="tabschild4-2">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblEquipoTelefonico">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevoEquipoTelefonico" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsEquipoTelefonico" width="600px"></table>
				<div id="pagerGridObjsEquipoTelefonico"></div>
			</div>
			
			<!--ARMA-->
			<div id="tabschild4-3">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblArma">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevaArma" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsArma" width="600px"></table>
				<div id="pagerGridObjsArma"></div>
			</div>
			
			<!--EXPLOSIVO-->
			<div id="tabschild4-4">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblExplosivos">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevoExplosivo" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsExplosivo" width="600px"></table>
				<div id="pagerGridObjsExplosivo"></div>
			</div>
			
			<!--SUSTANCIA-->
			<div id="tabschild4-5">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblSustancia">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevaSustancia" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsSustancia" width="600px"></table>
				<div id="pagerGridObjsSustancia"></div>
			</div>
			
			<!--ANIMAL-->
			<div id="tabschild4-6">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblAnimal">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevoAnimal" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsAnimal" width="600px"></table>
				<div id="pagerGridObjsAnimal"></div>
			</div>
			
			<!--VEHICULO-->
			<div id="tabschild4-7">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblVehiculos">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevoVehiculo" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsVehiculo" width="470px"></table>
				<div id="pagerGridObjsVehiculo"></div>
			</div>
			
			<!--AERONAVE-->
			<div id="tabschild4-8">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblAeronave">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevaAeronave" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsAeronave" width="600px"></table>
				<div id="pagerGridObjsAeronave"></div>
			</div>
			
			<!--EMBARCACION-->
			<div id="tabschild4-9">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblEmbarcacion">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevaEmbarcacion" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsEmbarcacion" width="600px"></table>
				<div id="pagerGridObjsEmbarcacion"></div>
			</div>
			
			<!--NUMERARIO-->
			<div id="tabschild4-11">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblNumerario">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevoNumerario" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsNumerario" width="600px"></table>
				<div id="pagerGridObjsNumerario"></div>
			</div>
			
			<!--VEGETAL-->
			<div id="tabschild4-12">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblVegetal">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevoVegetal" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsVegetal" width="600px"></table>
				<div id="pagerGridObjsVegetal"></div>
			</div>
			
			<!--DOCUMENTO OFICIAL-->
			<div id="tabschild4-13">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblDocOficial">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevoDocumentoOficial" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsDocumentoOficial" width="600px"></table>
				<div id="pagerGridObjsDocumentoOficial"></div>
			</div>
			
			<!--JOYA-->
			<div id="tabschild4-14">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblJoya">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevaJoya" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsJoya" width="600px"></table>
				<div id="pagerGridObjsJoya"></div>
			</div>
			
			<!--OBRA DE ARTE-->
			<div id="tabschild4-15">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblObraArte">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevaObraDeArte" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsObraDeArte" width="600px"></table>
				<div id="pagerGridObjsObraDeArte"></div>
			</div>
			
			<!--OTROS-->
			<div id="tabschild4-16">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblOtros">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevoOtros" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsOtros" width="600px"></table>
				<div id="pagerGridObjsOtros"></div>
			</div>
			
			<!--PERICIAL-->
			<div id="tabschild4-17">
				<table width="25%" cellpadding="0" cellspacing="0" id="tblPericial">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevoPericial" class="ui-button ui-corner-all ui-widget" value="Ingreso nuevo"/></td>
					</tr>
				</table>
				<table id="gridObjsPericial" width="600px"></table>
				<div id="pagerGridObjsPericial"></div>
			</div>
					
		</div>
				
	</div>
	<!--TERMINAN TABS OBJETOS Y EVIDENCIAS-->
	
	<div id="tabsconsultaprincipal-6" style="height: 600px !important;">
		 <input type="button" class="btn_grande" id="btnCadCusConsultaCadCus" style="width: 250px;" value="Consultar cadena de custodia"/><br/><br/>    
	</div>
	<div id="tabsconsultaprincipal-7" style="height: 600px !important;">
		<jsp:include page="diligencias.jsp"/>
	</div>
	<div id="tabsconsultaprincipal-8" style="height: 600px !important;">
		<table width="100%" id="gridAudiencia" onmouseup="camposSeleccionados()"></table>
		<div id="pagerAudiencia"></div>
	</div>
	<div id="tabsconsultaprincipal-9" style="height: 600px !important;">
		<jsp:include page="/WEB-INF/paginas/solicitudesTabPericialesView.jsp"></jsp:include>
	</div>
	
	<div id="tabsconsultaprincipal-10" style="height: 600px !important;">
		<div id="tabschild10" class="tabs-bottom">
			<ul>
				<li><a href="#tabschild10-1" onclick="">Actuaciones de los Defendidos</a></li>
				<li><a href="#tabschild10-2" onclick="">Actuaciones del Expediente</a></li>					
			</ul>
					
			<div id="tabschild10-1">
				<table width="90%" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<jsp:include page="actuacionesPorDefendido.jsp"/>
						</td>
					</tr>
				</table>
			</div>
						
			<div id="tabschild10-2">
				<table cellpadding="0" cellspacing="0" id="" >
					<tr>
						<td>
							<jsp:include page="actuaciones.jsp"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<div id="tabsconsultaprincipal-11" style="height: 600px !important;">
		<table width="25%" cellpadding="0" cellspacing="0" id="tablaNotasExpediente">
			<tr>
				<td>&nbsp;&nbsp;&nbsp;<input type="button" class="ui-button ui-corner-all ui-widget" value="Generar Nota"  id="botonGuardarNotas" onclick="notaExpediente(0);" /></td>
			</tr>
		</table>
	</div>
	<div id="tabsconsultaprincipal-12" style="height: 600px !important;">
		<table id="gridDetalleFrmPrincipal"></table>
			<div id="pager1"></div>
				<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
					<input type="hidden" id="documentoId" name="documentoId" />
				</form>
				<form name="frmDoc2" action="<%= request.getContextPath() %>/GenerarDocumentoDirecto.do" method="post">
					<input type="hidden" name="formaId" />
					<input type="hidden" name="numeroUnicoExpediente" />
				</form>
		<table id="tableDocs">
			<!-- <tr>
				<td>Total de documentos:</td>
				<td id="idTotalDocumentos">&nbsp;</td>
			</tr> -->
		</table>
	</div>
	<div id="tabsconsultaprincipal-13">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	            <td><br></td>
	        </tr>
	        <tr>
	            <td>
	            	<div id="divBitacora">
		            	<table id="gridBitacora"></table>
		            	<div id="pagerGridBitacora"></div>
	            	</div>
	            </td>
	        </tr>
	    </table>	
	</div>
	<!--COMIENZAN TABS INFERIORES DE DELITO-->
	<div id="tabsconsultaprincipal-14"  class="tabTabsRelacionesDelitosPersonas">
		<div id="tabschild14" class="tabs-bottom">
			<ul>
				<li><a href="#tabschild2-1">Relaci&oacute;n Delito Persona</a></li>
				<!-- deprecated Evaluar el impacto y quitar la funcion gridRelacionarDelitosTabDelito definida en este jsp -->
				<!-- <li><a href="#tabschild2-2" onclick="gridRelacionarDelitosTabDelito();cargaGridConsultaDelitosTodos()">Establecer Delito Persona</a></li> -->
			</ul>
			
			<div id="tabschild2-1">

				<%-- deprecated Evaluar el impacto y quitar el archivo
					<table width="25%" cellpadding="0" cellspacing="0">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevaSentenciado">
							<jsp:include page="/WEB-INF/paginas/seleccionarDelitoViewDefensoria.jsp"></jsp:include>
						</a></td>
					</tr>
				</table> --%>
				<div style="overflow-x:scroll; width:100%; height:100%" style="overflow-y:hidden;">
					<jsp:include page="../establecerRelacionesDelitoPersonaPG.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<!--TERMINAN TABS INFERIORES DE DELITO-->
	
		<form name="formaDocumento" action="<%= request.getContextPath() %>GenerarDocumentoDirecto.do" method="post">
			<input type="hidden" value="" id="formaId" >
			<input type="hidden" value="" id="documentoId">
			<input type="hidden" value="" id="tipoDocumento">
			<input type="hidden" name="numeroUnicoExpediente" />
		</form>
		
		<form name="formaArchivoD" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
			<input type="hidden" name="documentoId" />
		</form>
	
	
</div>
</body>
</html>	