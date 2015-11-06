<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.TipoAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.leycodigo.TipoNorma"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento"%>
<%@page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.calidad.Calidades"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.funcionario.TipoEspecialidad"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.funcionario.Puestos"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.EstatusPermisosAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.funcionario.Especialidades"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="java.util.Calendar"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<% 
	request.getSession().removeAttribute("mesDisponibilidadSesion");
%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Atención de Audiencias</title>
		
	
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>				
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/estilos.css"  />	
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/layout_complex.css"  />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/style.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/prettify.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/cssGrid/ui.jqgrid.css"  />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>  

<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/prettify.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/jquery.maskedinput.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/validate/mktSignup.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.tablednd.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/comun.js?n=1"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.timeentry.js"></script>
	
	<!--ESTILOS PARA LAS TABS-->
	<style>
	#tabs { height: 770px; } 
	.tabs-bottom { position: relative; } 
	
	.tabs-bottom .ui-tabs-panel { height: auto; overflow: auto; } 
	.tabs-bottom .ui-tabs-nav { position: absolute !important; left: 0; bottom: 0; right:0; padding: 0 0.2em 0.2em 0; } 
	.tabs-bottom .ui-tabs-nav li { margin-top: -2px !important; margin-bottom: 1px !important; border-top: none; border-bottom-width: 1px; }
	.ui-tabs-selected { margin-top: -3px !important; }
	</style>

	<script type="text/javascript">

	<%
		Long rolId = 0L;
	
		UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
		
		if(usuarioDTO != null && usuarioDTO.getRolACtivo() != null 
				&& usuarioDTO.getRolACtivo().getRol() != null 
					&& usuarioDTO.getRolACtivo().getRol().getRolId() != null){
			rolId = usuarioDTO.getRolACtivo().getRol().getRolId();
		}
	%>
	
	var CONTEXT_ROOT = '<%= request.getContextPath()%>';
	var rolId = <%=rolId%>;
	var contextoPagina = "${pageContext.request.contextPath}";

	
	var audienciaConsulta;
	/**
	* Variables globales para controlar los datos de la pestaña de programar audiencia 
	*/
	var idApplet=0;
	var idPermisos=0;
	//guarda tipo de ley a consultar
	var tipoLey=0;
	//guarda el id del domicilio
	var domicilioId;
	//mes que se muestra en la caja de texto
	var mesActual;
	//anio que se muestra en la caja de texto
	var anioActual;
	//Arreglo usado como carrusel para obtener los meses
	var meses =["Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"];
	//bandera de cambio para recorrer el carrusel de meses
	var banderaCambio=0;
	//primera consulta muestra los grids de salas
	var primeraConsulta= "true";
	//primera consulta jueces muestra los grids de jueces
	var primeraConsultaJueces= "true";
	//Datos que vienen de asignarSalaTemporalPJENA.jsp
	var datosSalaTemporal="";
	//numero de salas
	var salaTemporal="false";
	//id de la audiencia
	var idAudiencia;
	var tipoAudiencia;
	var multiselect=0;	
	var fechaReal="";
	//variables para la consulta de jueces
	var horaSeleccionada="";
	var minutoSeleccionado="";
	var duracionEstimadaAudiencia="";
	var idSalaSeleccionada="";
	var idEvento=parent.audienciasid;
	
	//Permite ocultar/mostrar los botones relacionados a la Notificacion
	var desactivarBotonesEnNotificaciones = true;
	
	/*
	*Variables para la sub ceja de Involucrados
	*/
	puestoFuncionarioSig="";
	
	//variable para almacenar el id de la audiencia siguiente
	var idAudienciaSiguiente=0;

	/**
	* Variables globales para controlar los datos 
	*/
	var lstResolutivos = new Array();
	var idResolutivo = 0;
	//var idEventoGlob;
	var fechaGlob;
	var tipoAudGlob;
	var ubicacionGlob;
	var direccionGlob;

	//variable que almacena el nombre de las salas
	var nombreSalas=new Array();

	//Variable para controlar los ids de las salas
	var identiSala=new Array();

	var primeraFiscal=true;

	var numExpediente = parent.numExpediente;

	/**Variables ceja Individualizacion*/
	var puestoFuncionario="";
	var idWindowIngresarTestigo=1;
	var idWindowIngresarDenunciante=1;
	var numeroExpediente ="";


	/***************************
	*Variables para la ceja de Pruebas*
	*/
	var audienciaId="";
	
	/** Varialbles necesarias para registrar un amparo**/
	var idExpediente = 0;
	var idNumeroExpediente = 0;
	var numeroGeneralCaso;

	/*
	*Constantes generales para tipo de consulta
	*/
	var TIPO_CONSULTA_PR = 1;
	var TIPO_CONSULTA_VICTIMA = 6;
	var TIPO_CONSULTA_DENUNCIANTE = 7;
	var TIPO_CONSULTA_TESTIGO = 3;
	var idWindowPantallaActuaciones = 1;
	
	//variable bandera para saber que ya esta finalizada o cancelada la Audiencia
	var audienciaFinalizadadeshabilitar=false;
	var idEstatusAudiencia=0;
	
	var juezOmision = false; 
	
	$(document).ready(function() {
		
		//desbloquear pantalla
		jQuery(document).ajaxStop(desbloquearPantalla());
		
		//crea todas la tabs
		$("#tabsDetalleAudiencias").tabs();
		//crea las tabs childs de las pruebas
		$( "#tabschildPruebasPJENS" ).tabs();
		//Si esta bandera es true, se activa la ceja de pruebas
		validaCejaPruebas();
		
		/*
		*LLamadas COMUN
		*/
			//Recuperamos el id de la audiencia ACTUAL
			var idEventoParcial=<%= request.getSession().getAttribute("idEvento")%>;

			if(idAudienciaSiguiente == 0){
				idAudienciaSiguiente=<%= request.getAttribute("idAudienciaSiguiente")%>;
				if(idAudienciaSiguiente == null){
					idAudienciaSiguiente = 0;
				}
				else{
					$("#tabsDetalleAudiencias" ).tabs('select', 5);
				}
			}
		
			if(idEventoParcial!="undefined"){
				idEvento=idEventoParcial;
			}			

			idAudiencia=idEvento;
				///Para la ceja de pruebas
			audienciaId=idEvento;
			
			

			deshabilitarHabilitarComponentes("inicio");

		/*
		*LLamdas para la ceja de Detalle de Audiencia
		*/
			consultaDetalleEvento(idEvento);
			cargaImputadosAudiencia(idEvento);
			consultarJuecesDeAudiencia(idAudiencia);
			cargaImputadosProgramarAudiencia(idEvento);



		/*
		*Funcion para cargar las relaciones delito persona, que debe ser
		*llamada despues de consultar el detalle de la audiencia
		*/
		consultaDelitoPersonaPorImputadosAudiencia();

			
		/*
		*LLamdas para la ceja de Individualizacion
		*/
			cargaGridVictima();
			//cargaGridVictimaOrg();
			cargaGridDenunciante();
			cargaGridFiscal();
			cargaGridDefensor();
			cargaGridTestigo();
			cargaGridPerito();
			cargaGridPolicia();
			$('#cbxFuncionarioExtPJENS').change(habilitarCamposFuncionarioExterno);
			
		/*
		*LLamdas para la ceja de Objetos
		*/
			//cargaCadenaCustodia(idEvento);
			
		/**********************************
		*LLamdas para la ceja Pruebas
		************************************/			
			//LLamadas para la sub ceja Datos de Pruebas
			cargaGridDatosPrueba();
			$("#tipoDatoPruebaPJENS").change(controlDatoDePrueba);
			$("#tipoMediaPruebaPJENS").change(controlMedioDePrueba);
			
			//LLamadas para la sub ceja de Medios de Pruebas
			cargaGridMediosDePrueba();
				
			//Llamadas para la sub ceja de Prueba
			cargaGridPrueba();

			
			
		/*
		*LLamdas para la ceja de Programar Audiencia
		*/
		
			$( "#tabschild,#tabschild2" ).tabs();

			//oculta las sub cejas de prog audiencia
			//ocultaSubCejasProgAudiencia();
			
			/**Sub ceja Audiencia*/
			
				//Le da el estilo al combo box de duracion estimada
				$("#duracionEstimadaProgramarAudiencia").multiselect({ 
					multiple: false, 
					header: "Seleccione", 
					position: { 
						my: 'top', 
						at: 'top' 
					},
					selectedList: 1 
				});

				//obtenemos la fecha actual
				mesActual='<%=(java.util.Calendar.getInstance().get(Calendar.MONTH))%>';
				anioActual='<%=(java.util.Calendar.getInstance().get(Calendar.YEAR))%>';
				var mesSeleccionado = meses[mesActual];		

				//escrbimos en pantalla la fecha actual
				$('#mes').val(mesSeleccionado);
				$('#anio').val(anioActual);

				//Carga el grid de la Agenda
				cargaGridAgenda();

				//Limpia los campos
				$('#txtSalaSeleccionada').val("");
				$('#txtHoraInicioSeleccionada').val("");
				
			/**Sub ceja Involucrados*/
			
				//Oculta las pestañas de imputado,testigo,perito y policia ministerial
				ocultaSubCejasProgAudiencia();

			/**Sub ceja Objetos*/
			cargaGridObjetos();	
			cargaCondicion();

		/*
		*LLamdas para la ceja Resolutivos de Audiencia
		*/
			
			$(function(){
	    	  $('#tempVideo').timeEntry({show24Hours:true,timeSteps:[1,1,0],ampmPrefix: ' '});
	    	});
	    	
			mostrarOcultarControlesJAVS();
			cargaGridResolutivosDeAudiencia(idEvento);
			$("#agregarResolutivoBoton").click(agregarRegistrarResolutivo);
			$("#eliminarResolutivoBoton").click(eliminarRegistroResolutivo);
			abreCodigosLeyes(); 
			cargaHoraCaptura();
					

		/*
		*Llamada para la ceja de Documentos
		*/
		jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
			url:'<%=request.getContextPath()%>/consultarDocumentosPorAudiencia.do?idAudiencia='+idAudiencia,
			datatype: "xml",
			colNames:['Área del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento', 'Documento', 'Es Parcial'],
			colModel:[ 	{name:'area',index:'da', width:200, align:'center'},
						{name:'FechaActividad',index:'fechaActividad', width:170, align:'center', hidden:true},							
						{name:'NombreActividad',index:'nombreActividad', width:400, align:'center', hidden:true},
			           	{name:'Tipo',index:'tipo', width:155, align:'center'}, 
						{name:'Nombre',index:'nombre', width:255, align:'center'},
			           	{name:'Fecha',index:'fecha', width:170, align:'center'},
			           	{name:'Documento',index:'documento', width:170, align:'center'},
			           	{name:'EsParcial',index:'esParcial', align:'center', hidden:true}
						],
			pager: jQuery('#pager1Documentos'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			autowidth: false,
			width:1100,
			sortname: 'turno',
			viewrecords: true,
			id: 'divgrid',
			ondblClickRow: function(id){
				var retd = jQuery("#gridDetalleFrmPrincipal").jqGrid('getRowData',id);
				if(retd.EsParcial){
					noEsParcial = retd.EsParcial.indexOf('false');
					if(noEsParcial > 0){
						consultaPDF(id);
					}
					else{
						numExp = $("#numExpPJENS").val();
						idWindowPantallaActuaciones++;
		    		    $.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:retd.Nombre, type:"iframe", confirmarCierreVentana:true});
		    		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?documentoId='+id+'&ocultarNumeroOficio=true&esconderArbol=1&numeroUnicoExpediente='+numExp+'&idAudiencia='+idAudiencia+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&esTransaccional='+true+'" width="1140" height="400" />');
					}
				}
				
			},
			sortorder: "desc"
		}).navGrid('#pager1Documentos',{edit:false,add:false,del:false});
		$("#gview_gridDetalleFrmPrincipal .ui-jqgrid-bdiv").css('height', '500px');
		
//////////////////////FUNCIONALIDAD DESCONOCIDA
		$("#tabs-2").hide();
		$("#rowDomicilioNotif").hide();		
		$("#cbxPais").val(10);
		//onSelectChangePais();
//////////////////////FUNCIONALIDAD DESCONOCIDA
		//$('#validarNotificacion').bind('click',mostrarDetallePersona);

		//Se agrega la funcion solicitarDefensor
		$('#solicitarBoton').bind('click', solicitarDefensor);
		configurarVisorAudienciaPorRol();
		deshabilitarDespuesFinalizadaAudiencia();
	});//FIN ON READY

//**********************************************************FUNCIONALIDAD COMUN A TODAS LAS CEJAS**********************************************************\

	/*
	*Funcion que configura la pantalla por rol
	*/
	function configurarVisorAudienciaPorRol() {

		if(rolId == <%=Roles.JUEZPJ.getValorId()%>) {
			//ocultando la opcion Resolutivos de Audiencia
			ocultaMuestraTabVisor("tabResolitivos",0);
				
		}else if(rolId == <%=Roles.ENCARGADOSALA.getValorId()%>){
			//ocultando la opcion notas del Juez
			ocultaMuestraTabVisor("tabNotas",0);
		}
	}
	
	/*Funcion que oculta o muestra una ceja del visor
	*/
	function ocultaMuestraTabVisor(claseTab,bandera){
		
		if(parseInt(bandera)==0)//oculta
		{
			$("."+claseTab).hide();
		}
		else///muestra
		{
			$("."+claseTab).show();
		}
	}
	
	
	/*
	*Funcion que vallida si la bandera "cejaPruebas" es true, inicializa el visor
	*en la Tab de pruebas, ya que viene de un submit en la forma: medioPruebaForm
	*/
	function validaCejaPruebas(){
		
		var cejaPruebas=<%= request.getAttribute("cejaPruebas")%>;
		
		if(cejaPruebas){
			$("#tabsDetalleAudiencias" ).tabs('select', 3);
		}
	}
	
	/*
	*Funcion que cierra la ventana visor de documentos
	*/
	function cerrarVentanaDocumento(id){
		var pantalla ="iframewindowGenerarDocumento";
		pantalla += id;
		$.closeWindow(pantalla);
	}

	function documentoGenerado(){
		//funcion para incluir alguna accion despues de haber sido generado el archivo digital (guardado definitivo)
	}
	
	/**
	*Funcion para deshabilitar los botones de designar 
	*Juez manualmente y automaticamente, Guardar y Designar Sala Temporal
	*/
	function deshabilitarHabilitarComponentes(accion){

		if(accion == "inicio"){
			
			$('#btnAsignarJuezManual').attr('disabled',true);
			$('#btnAsignarJuezAuto').attr('disabled',true);
			$('#btnGuardarAudiencia').attr('disabled',true);
			$('#btnDesignar').attr('disabled',true);	
			$('#duracionEstimadaProgramarAudiencia').multiselect('disable');
		}
		if(accion == "horarioSala"){
			$('#btnAsignarJuezManual').attr('disabled',false);
			$('#btnAsignarJuezAuto').attr('disabled',false);
			//$('txtSalaSeleccionada').attr('disabled',false);
		}
		
		if(accion == "seleccionFecha"){
			$('#btnDesignar').attr('disabled',false);
			$('#btnAsignarJuezManual').attr('disabled',true);
			$('#btnAsignarJuezAuto').attr('disabled',true);
			$('#btnGuardarAudiencia').attr('disabled',true);
			$('#duracionEstimadaProgramarAudiencia').multiselect('enable');
		}

		if(accion == "designarSala"){
			$('#btnDesignar').attr('disabled',true);
			$('#btnAsignarJuezManual').attr('disabled',false);
			$('#btnAsignarJuezAuto').attr('disabled',false);	
			for (i=0;i<numeroDeSalas;i++){
				salaId = identiSala[i];
				$('#gridSalasPJENA'+salaId).unbind('click');
			}
			$('#duracionEstimadaProgramarAudiencia').multiselect('disable');
		}
		if(accion == "juez"){
			$('#btnAsignarJuezManual').attr('disabled',true);
			$('#btnAsignarJuezAuto').attr('disabled',true);
			$('#btnDesignar').attr('disabled',true);
			$("#gridAgendaPJENA").unbind('click');
			$('#duracionEstimadaProgramarAudiencia').multiselect('disable');
			$('#btnAtrasMes').attr('disabled',true);
			$('#btnAdelanteMes').attr('disabled',true);
			$('#juezSustituto').attr('disabled',true);
			for (i=0;i<numeroDeSalas;i++){
				salaId = identiSala[i];
				$('#gridSalasPJENA'+salaId).unbind('click');
			}
			$('#btnGuardarAudiencia').attr('disabled',false);	
		}
	}

	function invocacionSolicitudJAVS(){
		consultarAudienciaJAVS(idEvento);
	}

	/*
	*Funcion que limpia las cajas de texto
	*antes de llenarlas con los datos de la
	*nueva consulta
	*/	
	function limpiaDatosDetalleEvento(){
		
		$("#tipoAudienciaPJENS").val("");
		$("#numCasoPJENS").val("");
		$("#numExpPJENS").val("");
		$("#audienciaPJENS").val("");
		$("#caracterPJENS").val("");
		$("#fechaAudienciaPJENS").val("");
		$("#horaAudienciaPJENS").val("");
		$("#salaPJENS").val("");
		$("#direccionSalaPJENS").val("");
		$("#ubicacionPJENS").val("");
		$("#juezPJENS").val("");
		$("#audienciaInvPJENS").val("");
		$("#tipoAudienciaInvPJENS").val("");
		$("#numCasoInvPJENS").val("");
		$("#numExpInvPJENS").val("");
		
	}
	
//**********************************************************FUNCIONALIDAD DE LA CEJA DETALLES DE AUDIENCIA**********************************************************\

	/*
	*Funcion que consulta el detalle del evento y llena 
	*los campos de la TAB Detalle evento
	*/
	function consultaDetalleEvento(idEvento){

		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/detalleAudienciasDelDiaPJENS.do',
			data: 'idEvento='+ idEvento, 
			async: false,
			dataType: 'xml',
			success: function(xml){
				var errorCode;
				errorCode=$(xml).find('response').find('code').text();
				if(parseInt(errorCode)==0){
					
					//Variable para validar si el estatus de la audiencia es FINALIZADA o CANCELADA y deshabilitar componentes necesarios
					idEstatusAudiencia = $(xml).find('estatusAudiencia').find('idCampo').first().text();
    				limpiaDatosDetalleEvento();
    			    tipoAudGlob	= $("#tipoAudienciaPJENS").val($(xml).find('audienciaDTO').find('tipoAudiencia').find('valor').first().text());
    			    $("#numCasoPJENS,#numCasoProgramarAudiencia").val($(xml).find('audienciaDTO').find('expediente').find('casoDTO').find('numeroGeneralCaso').first().text()); 
    				numeroExpediente = $(xml).find('numeroExpediente').first().text();
    				$("#numExpPJENS,#numExpedienteProgramarAudiencia").val($(xml).find('numeroExpediente').first().text());
    				$("#audienciaPJENS").val($(xml).find('id').first().text());
    				audienciaConsulta=$(xml).find('id').first().text()
    				$("#caracterPJENS").val($(xml).find('caracter').first().text());

    				var fecha= $(xml).find('fechaEvento').text();
    				fechaGlob=fecha;
    				formateaFechaHoraEvento(fecha);
    				
    				$("#salaPJENS").val($(xml).find('sala').find('nombreSala').first().text());
    				direccionGlob = $("#direccionSalaPJENS").val($(xml).find('sala').find('domicilioSala').first().text());
    				ubicacionGlob = $("#ubicacionPJENS").val($(xml).find('sala').find('ubicacionSala').first().text());
					///////////////////////////////////////JUECES FALTA HACER OTRA CONSULTA///////////////////////////////////////
        			//$("#juezPJENS").val($(xml).find('juez').first().text());    				
    				$("#audienciaInvPJENS").val($(xml).find('id').first().text());
    				$("#tipoAudienciaInvPJENS").val($(xml).find('tipoAudiencia').find('valor').first().text());
    				$("#numCasoInvPJENS").val($(xml).find('audienciaDTO').find('expediente').find('casoDTO').find('numeroGeneralCaso').first().text());
    				$("#numExpInvPJENS").val($(xml).find('numeroExpediente').first().text());
    				
    				$("#audienciaResPJENS").val($(xml).find('id').first().text());
    				$("#tipoAudienciaResPJENS").val($(xml).find('tipoAudiencia').find('valor').first().text());
    				$("#numCasoResPJENS").val($(xml).find('audienciaDTO').find('expediente').find('casoDTO').find('numeroGeneralCaso').first().text());
    				$("#numExpResPJENS").val($(xml).find('numeroExpediente').first().text());
    									
    				//Se configuran variables para registrar un amparo en la ceja actuaciones
    				idExpediente = $(xml).find('expedienteId').first().text();
    				idNumeroExpediente = $(xml).find('numeroExpedienteId').first().text();
    				numeroGeneralCaso = $(xml).find('audienciaDTO').find('expediente').find('casoDTO').find('numeroGeneralCaso').first().text();

    				//Tab Programar Audiencia
    				//$("#numCasoProgramarAudiencia").val($(xml).find('numeroGeneralCaso').first().text());
    				//$("#numExpedienteProgramarAudiencia").val($(xml).find('numeroExpediente').first().text());
    				$("#fechaLimiteProgramarAudiencia").val($(xml).find('strFechaLimite').first().text());
    				
					tipoAudiencia = $(xml).find('tipoAudiencia').find('idCampo').first().text();
					//LLama a cargar combo de tipos de audiencia para la ceja programar audiencia
    				cargaComboTipoAudiencia(tipoAudiencia);
					
					if(idEstatusAudiencia == '<%=EstatusAudiencia.FINALIZADA.getValorId()%>' 
							|| idEstatusAudiencia== '<%=EstatusAudiencia.CANCELADA.getValorId()%>'){
						audienciaFinalizadadeshabilitar=true;
					}
    			}
			}
		});
	}

	/*
	*Funcion que consulta el detalle del evento y llena 
	*los campos de la TAB Detalle evento
	*/
	function formateaFechaHoraEvento(fechaEvento){
		
		var fechaFrac = fechaEvento.split(" ")[0];
		var horaFrac = fechaEvento.split(" ")[1];

		horaFracPos = horaFrac.indexOf(":", 0);
		hora=horaFrac.substring(0,horaFracPos+3);		
							
		$("#fechaAudienciaPJENS").val(fechaFrac);
		$("#horaAudienciaPJENS").val(hora);
	}

	
	/**
	*Funcion que recarga el grid con los imputados de la audiencia
	*/
	function cargaImputadosAudiencia(idEvento){
		
		jQuery("#gridAudienciasPJENC").jqGrid({ 
			url:'<%= request.getContextPath()%>/consultarImputadoAudiencia.do?idEvento='+ idEvento +'&tipo='+TIPO_CONSULTA_PR+'', 
			datatype: "xml", 
			colNames:['Nombre','Calidad','Delito','Detenido'], 
			colModel:[ 	{name:'nombre',index:'nombre', width:150, align:'center'}, 
			           	{name:'calidad',index:'calidad', width:130, align:'center'}, 
			           	{name:'delito',index:'delito', width:155, align:'center'},
			           	{name:'detenido',index:'detenido', width:60, align:'center'}
						
					],
			pager: jQuery('#pager1'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: false,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
				modificarProbResponsable(rowid);
			} ,
			caption:"Imputados en la audiencia actual"
		});
	}

	/*
	*Funcion que es invocada desde la ventana ingresarProbableResponsableView.jsp
	*para refrescar el grid de imputados en la audiencia actual
	*/
	function recargaGridImputados(){
		jQuery("#gridAudienciasPJENC").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarImputadoAudiencia.do?idEvento='+ idEvento +'&tipo=1',datatype: "xml" });
		$("#gridAudienciasPJENC").trigger("reloadGrid");
	}

	
	//Variable para controlar la carga de la ventana ingresar/modificar probable responsable
	var idWindowIngresarProbResponsable = 0;
	//Abre una nueva ventana de probable responsable
	function modificarProbResponsable(id) {

		idWindowIngresarProbResponsable++;
		
		var sistemaTrad = false;
		var muestraDetenido=1;
		var titulo = "Modificar "+'<bean:message key="probableResponsableTitulo"/>';
		
		$.newWindow({id:"iframewindowModificarProbResponsable" + idWindowIngresarProbResponsable, statusBar: true, posx:75,posy:30,width:1100,height:530,title:titulo, type:"iframe",onWindowClose: function(id){
				idWindowIngresarProbResponsable--;
			}
		});
		$.updateWindowContent("iframewindowModificarProbResponsable" + idWindowIngresarProbResponsable,'<iframe src="<%= request.getContextPath() %>/IngresarProbResponsable.do?idProbableResponsable='+id +'&calidadInv=PROBABLE_RESPONSABLE&numeroExpediente='+numeroExpediente +'&detenido='+muestraDetenido+'&sistemaTrad='+sistemaTrad+'&deshabilitar='+audienciaFinalizadadeshabilitar+'" width="1100" height="530" />');
		$("#" +"iframewindowModificarProbResponsable" + idWindowIngresarProbResponsable + " .window-maximizeButton").click();
	}


	/*
	*Funcion que consulta el detalle del evento y llena 
	*los campos de la TAB Detalle evento
	*/
	function consultarJuecesDeAudiencia(idAudiencia){
		
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/consultarJuecesDeAudiencia.do',
			data: 'audienciaId='+ idAudiencia,
			async: false,
			dataType: 'xml',
			success: function(xml){
				var errorCode;
				errorCode=$(xml).find('response').find('code').text();
				if(parseInt(errorCode)==0){
					$(xml).find('listaJueces').find('juez').each(function(){
						//Se pone first en el campo de nombre para formar correctamente el nombre del juez
						$('#juezPJENS').append('<option value="' + $(this).find('involucradoId').text() + '">' + $(this).find('nombre').first().text()+" "+$(this).find('apellidoPaterno').first().text()+" "+$(this).find('apellidoMaterno').first().text()+ '</option>');
					});
				}
			}
		});
	}
	
//**********************************************************FUNCIONALIDAD DE LA CEJA INDIVIDULIZACION*************************************************************\

	//FUNCIONALIDAD PARA AGREGAR FUNCIONARIO EXTERNO
	
	/*
	*Funcion para guardar un nuevo funcionario externo y
	*relacionarlo a la audiencia actual
	*/
	function agregarFuncionarioExternoAudiencia(rolId,esAdudienciaSig){
		
		cargaComboFuncionarioExterno(rolId,esAdudienciaSig);
		habilitarCamposFuncionarioExterno();
		$("#divIngresarFuncionarioExterno").dialog("open");
	  	$("#divIngresarFuncionarioExterno").dialog({ autoOpen: true, 
		modal: true, 
	  	title: 'Agregar Funcionario Externo a la audiencia', 
	  	dialogClass: 'alert',
	  	position: [150,90],
	  	width: 450,
	  	height: 320,
	  	maxWidth: 450,
		maxHeight: 320,
		minWidth: 450,
		minHeight: 320,
	  	buttons:{"Aceptar":function() {
	  			var selected = $("#cbxFuncionarioExtPJENS option:selected").val();
	  			//No ha seleccionado funcionario externo existente
		  		if(parseInt(selected) == 0){
		  			if(validarDatosFuncionarioExterno() == true){
			  			guardarActualizarYAsociarFuncionarioExternoConAudiencia(rolId,esAdudienciaSig);
			  			$(this).dialog("close");
				  	}
			  	}
		  		else{
		  			guardarActualizarYAsociarFuncionarioExternoConAudiencia(rolId,esAdudienciaSig);
		  			$(this).dialog("close");
			  	}
		  	},
			"Cancelar" : function() {
				$(this).dialog("close");
			}
		  }
		});
	}

	
	/**
	*Habilita los campos para guardar un funcionario externo(nuevo)
	*/
	function habilitarCamposFuncionarioExterno(){

		var selected = $("#cbxFuncionarioExtPJENS option:selected").val();
		
		limpiaVentanaIngresarFuncionarioExterno();
		if(selected == 0){
			$('#nombreFuncionarioExtPJENS').attr('disabled', false);
			$('#apPatFuncionarioExtPJENS').attr('disabled', false);
			$('#apMatFuncionarioExtPJENS').attr('disabled', false);
			$('#claveFuncionarioExtPJENS').attr('disabled', false);
		}
		else{
			$('#nombreFuncionarioExtPJENS').attr('disabled', true);
			$('#apPatFuncionarioExtPJENS').attr('disabled', true);
			$('#apMatFuncionarioExtPJENS').attr('disabled', true);
			$('#claveFuncionarioExtPJENS').attr('disabled', true);
		}
	}

	/*
	*Funcion que limpia los datos de la ventana modal
	*ingresar funcionario a audiencia
	*/
	function limpiaVentanaIngresarFuncionarioExterno(){

		$('#nombreFuncionarioExtPJENS').val("");
		$('#apPatFuncionarioExtPJENS').val("");
		$('#apMatFuncionarioExtPJENS').val("");
		$('#claveFuncionarioExtPJENS').val("");
	}

	
	/*
	*Realiza la carga del combo de funcionario externo, en base al rolId,
	*muestra solo los funcionarios con ese rol, que no estan asociados
	*a la audiencia actual, (es decir, no se pueden agregar dos veces).
	*/
	function cargaComboFuncionarioExterno(rolId,esAdudienciaSig){

		var parametros = "";
		
		parametros += 'rolId=' + rolId;
		
		if(esAdudienciaSig == true){
			parametros += '&idEvento=' + idAudienciaSiguiente
		}else{
			parametros += '&idEvento=' + idEvento;
		}

		$('#cbxFuncionarioExtPJENS').empty();
		$('#cbxFuncionarioExtPJENS').append('<option value="0">-Seleccione-</option>');//Por default seleccione
		
		ejecutaAction("/consultarFuncionarioExternoPorFiltroNoAsociadoALaAudiencia", function(xml){
			$(xml).find('listaFuncionariExternoDTO').find('funcionarioExternoDTO').each(function(){
				$('#cbxFuncionarioExtPJENS').append('<option value="' + $(this).find('funcionarioExternoId').text() + '">'+ $(this).find('nombre').first().text() + ' '+$(this).find('apellidoPaterno').first().text() + ' '+$(this).find('apellidoMaterno').first().text() +'</option>');
			});
		} , parametros);
	}


	/*
	*Guarda/Actualiza un funcionario externo y ademas lo 
	*asocia a la audiencia actual
	*/
	function guardarActualizarYAsociarFuncionarioExternoConAudiencia(rolId,esAdudienciaSig){

		var parametros = "";

		parametros += 'rolId=' + rolId;
		if(esAdudienciaSig == true){
			parametros += '&idEvento=' + idAudienciaSiguiente;
		}else{
			parametros += '&idEvento=' + idEvento;
		}
		parametros += '&nombreFuncExterno=' + $('#nombreFuncionarioExtPJENS').val();
		parametros += '&apPatFuncExterno=' + $('#apPatFuncionarioExtPJENS').val();
		parametros += '&apMatFuncExterno=' + $('#apMatFuncionarioExtPJENS').val();
		parametros += '&claveFuncExterno=' + $('#claveFuncionarioExtPJENS').val();
		parametros += '&funcExtId=' + $("#cbxFuncionarioExtPJENS option:selected").val();
		
		ejecutaAction("/guardarActualizarFuncionarioExterno", function(respuesta){
			if(parseInt($(respuesta).find('code').text())==0){
				if($(respuesta).find('body').text() != null 
						&& $(respuesta).find('body').text() != "null"
							&& $(respuesta).find('body').text() != ""
								&& $(respuesta).find('body').text() == "exito"){
					customAlert("El funcionario se relacion&oacute; correctamente a la audiencia");
					recargaGridsFuncionariosExternos(rolId,esAdudienciaSig);
					limpiaVentanaIngresarFuncionarioExterno();								
				}else{
					customAlert("Ocurri&oacute; un error al intentar relacionar el funcionario con la audiencia.<br/>" +
					"por favor contacte al administrador");
				}
			}else{
				customAlert("Ocurri&oacute; un error al intentar relacionar el funcionario con la audiencia.<br/>" +
					"por favor contacte al administrador");
			}
		} , parametros);
	}


	/*
	*Funcion para validar los datos obligatorios del funcionario externo
	*/
	function validarDatosFuncionarioExterno(){
		
		if($('#nombreFuncionarioExtPJENS').val().trim() != "" 
				&& $('#apPatFuncionarioExtPJENS').val().trim() != ""
					&& $('#claveFuncionarioExtPJENS').val().trim() != ""){
			return true;
		}
		else{
			customAlert("Ingrese el nombre completo del funcionario externo y clave");
			return false;
		}
	}

	
	/*
	*Funcion refresca los grids, dependiendo del rol, que fue seleccionado
	*/
	function recargaGridsFuncionariosExternos(rolId,esAdudienciaSig){

		var gridAudSig = "";
		var idAudienciaRecargar = "";
			 
		if(esAdudienciaSig == true){
			gridAudSig = "1";
			idAudienciaRecargar = idAudienciaSiguiente;
		}else{
			gridAudSig = "";
			idAudienciaRecargar = idEvento;
		}
		
		if(rolId == '<%=Roles.DEFENSOR.getValorId()%>'){
			jQuery("#gridDefensor"+gridAudSig).jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarFuncionarioExternoAudienciaPorRol.do?idEvento='+idAudienciaRecargar+'&rolId=<%=Roles.DEFENSOR.getValorId()%>',datatype: "xml" });
			$("#gridDefensor"+gridAudSig).trigger("reloadGrid");
		}
		
		if(rolId == '<%=Roles.AGENTEMP.getValorId()%>'){
			jQuery("#gridFiscal"+gridAudSig).jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarFuncionarioExternoAudienciaPorRol.do?idEvento='+idAudienciaRecargar+'&rolId=<%=Roles.AGENTEMP.getValorId()%>',datatype: "xml" });
			$("#gridFiscal"+gridAudSig).trigger("reloadGrid");
		}
		
		if(rolId == '<%=Roles.PERITOAMP.getValorId()%>'){
			jQuery("#gridPerito"+gridAudSig).jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarFuncionarioExternoAudienciaPorRol.do?idEvento='+idAudienciaRecargar+'&rolId=<%=Roles.PERITOAMP.getValorId()%>',datatype: "xml" });
			$("#gridPerito"+gridAudSig).trigger("reloadGrid");
		}

		if(rolId == '<%=Roles.POLICIAMINISTER.getValorId()%>'){
			jQuery("#gridPolicia"+gridAudSig).jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarFuncionarioExternoAudienciaPorRol.do?idEvento='+idAudienciaRecargar+'&rolId=<%=Roles.POLICIAMINISTER.getValorId()%>',datatype: "xml" });
			$("#gridPolicia"+gridAudSig).trigger("reloadGrid");
		}
	}


	/*
	*Funcion para eliminar la relacion entre un funcionario y la audiencia
	*/
	function eliminarFuncionarioExternoAudiencia(funcExtId,rolId,esAdudienciaSig){

		if (funcExtId){
			
			var parametros='';
			
			parametros += 'funcExtId='+ funcExtId;
			
			if(esAdudienciaSig == true){
				parametros += '&idEvento=' + idAudienciaSiguiente;
			}else{
				parametros += '&idEvento=' + idEvento;
			}

			ejecutaAction("/eliminarFuncionarioExternoAudiencia", function(respuesta){
				if(parseInt($(respuesta).find('code').text())==0){
					if($(respuesta).find('body').text() != null 
							&& $(respuesta).find('body').text() != "null"
								&& $(respuesta).find('body').text() != ""
									&& $(respuesta).find('body').text() == "exito"){
						customAlert("El funcionario se elimin&oacute; correctamente de la audiencia");
						recargaGridsFuncionariosExternos(rolId,esAdudienciaSig);
						limpiaVentanaIngresarFuncionarioExterno();								
					}else{
						alertDinamico("Ocurri&oacute; un error al intentar eliminar el funcionario de la audiencia.<br/>" +
						"por favor contacte al administrador");
					}
				}else{
					customAlert("Ocurri&oacute; un error al intentar eliminar el funcionario de la audiencia.<br/>" +
						"por favor contacte al administrador");
				}
			} , parametros);
		}
		else{
			customAlert("Seleccione un funcionario");
		}
	}

	//TERMINA FUNCIONALIDAD PARA AGREGAR FUNCIONARIO EXTERNO

	/*
	*Funcion que guarda la Asistencia del funcionario, al momento de dar clic en el checkbox
	* se crea desde ConsultarEventosPJENSAction en consultarInvolucradosAudienciaIndividualizacion
	* y sirve como un escuchador de eventos
	***************************Ver Nota************************************************************
	*Nota:Los identificadores de los radio buttons se pueden confundir, cuando, la claveFuncionario
	*es igual al funcionarioExternoId, se deberan cambiar todos los grids para que no ocurra esto
	*por el momento en el action se cambian los prefijos de los identificadores, para hacerlos diferentes,
	*esto, no debe ocurrir con el funcionarioExternoId,ya que todos son diferentes
	*/
	function guardarAsistenciaFuncionario(involucradoID,esExterno){


		var parametros='';
		parametros += 'idAudiencia=' + idAudiencia;

		if(esExterno == true || esExterno == "true"){
			//Para funcionario se usa funcionarioExternoId
			parametros += '&funcionarioExternoId=' + involucradoID;
			parametros += '&esTitular='+ ($('#chkTitFunExt_'+involucradoID).is(':checked')?"true":"false");
			parametros += '&presente=' + ($('#chkPreFunExt_'+involucradoID).is(':checked')?"true":"false");
			parametros += '&esExterno=' + esExterno;
		}else{
			//Para funcionario se usa clave funcionario
			//Nota: cuando se cambie todo a funcionario externo, se debera,
			//quitar todo lo relacionado con funcionario
			parametros += '&claveFuncionario=' + involucradoID;
			parametros += '&esTitular='+ ($('#radioTit_'+involucradoID).is(':checked')?"true":"false");
			parametros += '&presente=' + ($('#chkPre_'+involucradoID).is(':checked')?"true":"false");
		}
		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/registrarAsistenciaFuncionario.do',
			data: parametros,
			dataType: 'xml',
			async: false,
			success: function(xml){
				var errorCode;
				errorCode=$(xml).find('response').find('code').text();				
			}
		});
	}

	/*
	*Funcion que guarda la Asistencia del involucrado en audiencia, al momento de dar clic en el checkbox
	* se crea desde ConsultarEventosPJENSAction en consultarInvolucradosAudienciaIndividualizacion
	* y sirve como un escuchador de eventos
	*/
	function guardarAsistenciaInvolucradoAudiencia(involucradoID,esDenunciante){
		
		var parametros='';

		parametros += 'claveFuncionario=' + involucradoID;
		parametros += '&idAudiencia=' + idAudiencia;
		
		if(esDenunciante==true){
			parametros += '&presente=' + ($('#chkPreDenu_'+involucradoID).is(':checked')?"true":"false");
		}else{
			parametros += '&presente=' + ($('#chkPre_'+involucradoID).is(':checked')?"true":"false");
		}
		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/registrarAsistenciaInvolucradoAudiencia.do',
			data: parametros,
			dataType: 'xml',
			async: false,
			success: function(xml){
				var errorCode;
				errorCode=$(xml).find('response').find('code').text();				
			}
		});
	}
	
	//Crea una nueva ventana de testigo
	function creaNuevoTestigo(idInvolucrado) {
		
		idWindowIngresarTestigo++;
		
		var idWindow = "iframewindowIngresarTestigo" + idWindowIngresarTestigo;
	
		testigo="testigo";
		$.newWindow({id:"iframewindowIngresarTestigo" + idWindowIngresarTestigo, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Ingresar Testigo", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarTestigo" + idWindowIngresarTestigo,'<iframe src="<%= request.getContextPath() %>/IngresarTestigo.do?audienciaId='+idAudiencia+'&numeroExpediente='+numeroExpediente +'&calidad='+testigo+'&idTestigo='+idInvolucrado+'&idWindow='+idWindow+'&bloquearModificacion='+audienciaFinalizadadeshabilitar+'" width="1050" height="600" />');		
	    
	}
	
	//variable para controlar el id de la ventana 
	var idWindowIngresarTestigoAudienciaSiguiente= 1;
	
	//Crea una nueva ventana de testigo para la siguiente audiencia
	function creaNuevoTestigoAudienciaSiguiente(idInvolucrado) {
		
		idWindowIngresarTestigoAudienciaSiguiente++;
		
		var idWindow = "iframewindowIngresarTestigo" + idWindowIngresarTestigoAudienciaSiguiente;
		
		testigo="testigo";
		$.newWindow({id:"iframewindowIngresarTestigo" + idWindowIngresarTestigoAudienciaSiguiente, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Ingresar Testigo", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarTestigo" + idWindowIngresarTestigoAudienciaSiguiente,'<iframe src="<%= request.getContextPath() %>/IngresarTestigo.do?audienciaId='+idAudienciaSiguiente+'&numeroExpediente='+numeroExpediente +'&calidad='+testigo+'&idTestigo='+idInvolucrado+'&idWindow='+idWindow+'&esSiguiente=true" width="1050" height="600" />');		
	}

	//Cierra la ventana que lo invoque de acuerdo a u identificador
	function cerrarVentanaIngresarTestigo(idWindow){

		$.closeWindow(idWindow);
	}
	
	//TERMINA FUNCIONALIDAD PARA AGREGAR FUNCIONARIO
	
	
	
	var banderaCargaGridVictima = true;
	/*
	*Funcion que carga el grid de victimas
	*/
	function cargaGridVictima(){

		if( banderaCargaGridVictima == true){
			
			jQuery("#gridVictima").jqGrid({

				url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idEvento+"&tipoConsulta="+TIPO_CONSULTA_VICTIMA,						
				datatype: "xml",
				colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Presente','check','condicion'], 
				colModel:[ 	{name:'Nombre',index:'Nombre', width:180}, 
							{name:'APaterno',index:'APaterno', width:180}, 
							{name:'AMaterno',index:'AMaterno', width:180},
							{name:'Presente',index:'Presente', width:50},
							{name:'check',index:'check', width:50, hidden:true},  
							{name:'Condicion',index:'Condicion', width:50, hidden:true},
						],
					autowidth: false,
					width:924,
					autoheight:true,
					pager: jQuery('#paginadorGridVictima'), 
					sortname: 'Nombre', 
					rowNum:10,
					rowList:[10,20,30],
					gridview: false, 
					viewrecords: true, 
					sortorder: "desc",
					hidegrid: false,
					toolbar: [true,"top"],
					caption:"Víctima",
					ondblClickRow: function(rowid) {
						
						var row = jQuery("#gridVictima").jqGrid('getRowData',rowid);
						if(row){						
							if(row.Condicion == "1"){
								consultarDenunciante(rowid);
							}else{
								consultarVictima(rowid);
							}
						}
					} 
			}).navGrid('#paginadorGridVictima',{edit:false,add:false,del:false});
			
			$("#t_gridVictima").append("<input type='button' class='btn_Generico' value='Agregar V&iacute;ctima' style='height:20px;font-size:-3'/>");
			$("input","#t_gridVictima").click(function(){
				creaNuevaVictima();
			});
	
			banderaCargaGridVictima= false;
		}
		else{
			jQuery("#gridVictima").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idEvento+"&tipoConsulta="+TIPO_CONSULTA_VICTIMA , datatype: "xml" });
			$("#gridVictima").trigger("reloadGrid");
		}		
	}
	
	var banderaCargaGridVictimaOrg = true;
	/*
	*Funcion que carga el grid de victimas
	*/
	function cargaGridVictimaOrg(){

		if( banderaCargaGridVictimaOrg == true){
			
			jQuery("#gridVictimaOrg").jqGrid({

				url:'<%= request.getContextPath() %>/consultarOrganizacionesDeAudiencia.do?audienciaId='+idAudiencia,						
				datatype: "xml",
				colNames:['Nombre organizaci&oacute;n','Nombre corto', 'Tipo de organizaci&oacute;n'], 
				colModel:[ 	{name:'nombreOrganizacion',index:'nombreOrganizacion', width:230}, 
							{name:'nombreCorto',index:'nombreCorto', width:180}, 
							{name:'tipoOrganizacion',index:'tipoOrganizacion', width:180}
						],
					autowidth: false,
					width:924,
					autoheight:true,
					pager: jQuery('#paginadorGridVictimaOrg'), 
					sortname: 'nombreOrganizacion', 
					rowNum:10,
					rowList:[10,20,30],
					gridview: false, 
					viewrecords: true, 
					sortorder: "desc",
					hidegrid: false,
					caption:"Víctima Persona Moral",
					ondblClickRow: function(rowid) {
						consultarVictimaOrg(rowid);
					} 
			}).navGrid('#paginadorGridVictimaOrg',{edit:false,add:false,del:false});

			banderaCargaGridVictimaOrg= false;
		}
		else{
			jQuery("#gridVictimaOrg").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultarOrganizacionesDeAudiencia.do?audienciaId='+idAudiencia});
			$("#gridVictimaOrg").trigger("reloadGrid");
		}		
	}

	var idWindowIngresarVictima = 1;
	
	//Abre una nueva ventana de crear una nueva victima
	function creaNuevaVictima() {
		idWindowIngresarVictima++;
		var elementoNuevo="si";
		$.newWindow({id:"iframewindowIngresarVictima" + idWindowIngresarVictima, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Víctima", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarVictima" + idWindowIngresarVictima,'<iframe src="<%= request.getContextPath() %>/IngresarVictima.do?numeroExpediente='+numeroExpediente +'&audienciaId='+idAudiencia+'&elemento='+elementoNuevo+'" width="1100" height="530" />');		
	}

	var idWindowConsultarVictima = 1;
	
	//Abre una nueva ventana para consultar una víctima		
	function consultarVictima(idInvolucrado){
		idWindowConsultarVictima++;
		$.newWindow({id:"iframewindowConsultarVictima" + idWindowConsultarVictima, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Consultar V&iacute;ctima", type:"iframe"});		
		$.updateWindowContent("iframewindowConsultarVictima" + idWindowConsultarVictima,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?numeroExpediente='+numeroExpediente+'&idVictima='+idInvolucrado+'&deshabilitar='+audienciaFinalizadadeshabilitar+'&idCalidad=VICTIMA" width="1100" height="530" />');
	}

	//Abre una nueva ventana para consultar una víctima		
	function consultarVictimaOrg(idOrganizacion){
		idWindowConsultarVictima++;
		$.newWindow({id:"iframewindowConsultarVictima" + idWindowConsultarVictima, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Consultar V&iacute;ctima", type:"iframe"});		
		$.updateWindowContent("iframewindowConsultarVictima" + idWindowConsultarVictima,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?numeroExpediente='+numeroExpediente+'&idOrganizacion='+idOrganizacion+'&idCalidad=VICTIMA" width="1100" height="530" />');
	}

	/*
	*Estas funciones son usadas en ingresarVictimaView.jsp con el proposito de modificar esa JSP
	*asi que solo se agregan aqui para refrescar el grid en el caso de que se agregue una victima
	*o se modifique
	*/
	function cargaVictima(){
		cargaGridVictima();
		
		if(idAudienciaSiguiente != 0){
			cargaGridVictima1();
		}
		
	}

	function refresca(){
		cargaGridVictima();
		cargaGridDenunciante();

		if(idAudienciaSiguiente != 0){
			cargaGridVictima1();
			cargaGridDenunciante1();		
		}
	}


	var banderaCargaGridDenunciante = true;
	/*
	*Funcion que carga el grid de denunciantes
	*/
	function cargaGridDenunciante(){

		if( banderaCargaGridDenunciante == true){
			
			jQuery("#gridDenunciante").jqGrid({

				url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idEvento+"&tipoConsulta="+TIPO_CONSULTA_DENUNCIANTE,						
				datatype: "xml",
				colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Presente'], 
				colModel:[ 	{name:'Nombre',index:'Nombre', width:180}, 
							{name:'APaterno',index:'APaterno', width:180}, 
							{name:'AMaterno',index:'AMaterno', width:180},
							{name:'Presente',index:'Presente', width:50}, 
						],
					autowidth: false,
					width:924,
					autoheight:true,
					pager: jQuery('#paginadorGridDenunciante'), 
					sortname: 'Nombre', 
					rowNum:10,
					rowList:[10,20,30],
					gridview: false, 
					viewrecords: true, 
					sortorder: "desc",
					hidegrid: false,
					//toolbar: [true,"top"],
					caption:"Denunciante",
					ondblClickRow: function(rowid) {
						
						consultarDenunciante(rowid);
					} 
			}).navGrid('#paginadorGridDenunciante',{edit:false,add:false,del:false});

			//AGA- No se puede agregar mas de un denunciante, como en PG
			//$("#t_gridDenunciante").append("<input type='button' class='btn_Generico' value='Agregar Denunciante' style='height:20px;font-size:-3'/>");
			//$("input","#t_gridDenunciante").click(function(){
			//	crearDenunciante();
			//});

			banderaCargaGridDenunciante = false;
		}
		else{
			jQuery("#gridDenunciante").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idEvento+"&tipoConsulta="+TIPO_CONSULTA_DENUNCIANTE, datatype: "xml" });
			$("#gridDenunciante").trigger("reloadGrid");
		}		
	}


	var idWindowIngresarDenunciante = 1;
	
	//Abre una nueva ventana de Denunciante
	function crearDenunciante(){
		idWindowIngresarDenunciante++;
		elementoNuevo = "si";
		$.newWindow({id:"iframewindowIngresarDenunciante" + idWindowIngresarDenunciante, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Denunciante", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarDenunciante" + idWindowIngresarDenunciante,'<iframe src="<%= request.getContextPath() %>/IngresarDenunciante.do?numeroExpediente='+numeroExpediente +'&audienciaId='+idAudiencia+'&elemento='+elementoNuevo+'&calidadInv=DENUNCIANTE" width="1100" height="530" />');		
	}

	var idWindowConsultarDenunciante = 1;
	
	//Abre una nueva ventana de Denunciante
	function consultarDenunciante(id){
		idWindowConsultarDenunciante++;
		$.newWindow({id:"iframewindowIngresarDenunciante" + idWindowConsultarDenunciante, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Denunciante", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarDenunciante" + idWindowConsultarDenunciante,'<iframe src="<%= request.getContextPath() %>/IngresarDenunciante.do?idDenunciante='+id +'&numeroExpediente='+numeroExpediente +'&deshabilitar='+audienciaFinalizadadeshabilitar+'&calidadInv=DENUNCIANTE" width="1100" height="530" />');		
	}

	function cargaVictimaDenunciante(){

		cargaGridVictima();
		
		if(idAudienciaSiguiente != 0){
			cargaGridVictima1();
		}
	}

	function cargaDenunciante(){
		cargaGridDenunciante();
		cargaGridVictima();
		
		if(idAudienciaSiguiente != 0){
			cargaGridDenunciante1();
			cargaGridVictima1();
		}
	}

	
	/*
	*Funcion que carga el grid de ficales
	*/		
	function cargaGridFiscal(){
	
		jQuery("#gridFiscal").jqGrid({

			url:'<%= request.getContextPath()%>/consultarFuncionarioExternoAudienciaPorRol.do?idEvento='+idEvento+'&rolId=<%=Roles.AGENTEMP.getValorId()%>',
			datatype: "xml",
			colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Titular','Presente'], 
			colModel:[ 	{name:'Nombre',index:'Nombre', width:180}, 
						{name:'APaterno',index:'APaterno', width:180}, 
						{name:'AMaterno',index:'AMaterno', width:180},
						{name:'Titular',index:'Titular', width:50,align:'center'}, 
						{name:'Presente',index:'Presente', width:60,align:'center'}
					],
				autowidth: false,
				width:924,
				autoheight:true,
				pager: jQuery('#paginadorGridFiscal'), 
				sortname: 'Nombre', 
				rowNum:10,
				rowList:[10,20,30],
				gridview: false, 
				viewrecords: true, 
				sortorder: "desc",
				hidegrid: false,
				toolbar: [true,"top"],
				loadComplete: function(){
					primeraFiscal="false";
				}, 
				caption:'<bean:message key="agenteMp"/>',
				ondblClickRow: function(rowid) {
					$("#butNotificacionFiscal").attr("disable",false);
				} 
		}).navGrid('#paginadorGridFiscal',{edit:false,add:false,del:false});

		//Button agregar amp
		var inputAgregarFiscal = "<input type='button' class='btn_Generico' id='butAgreAmp' value='Agregar ";
		inputAgregarFiscal += '<bean:message key="agenteMp"/>';
		inputAgregarFiscal += "' style='height:20px;font-size:-3'/>";
		$("#t_gridFiscal").append(inputAgregarFiscal);
		
		//Button eliminar amp
		var inputEliminarFiscal = "<input type='button' class='btn_Generico' id='butElimAmp' value='Eliminar ";
		inputEliminarFiscal += '<bean:message key="agenteMp"/>';
		inputEliminarFiscal += "' style='height:20px;font-size:-3'/>";
		$("#t_gridFiscal").append(inputEliminarFiscal);
			
		$("#butAgreAmp","#t_gridFiscal").click(function(){
			agregarFuncionarioExternoAudiencia('<%=Roles.AGENTEMP.getValorId()%>',false);
		});

		$("#butElimAmp","#t_gridFiscal").click(function(){
			var rowid = jQuery("#gridFiscal").jqGrid('getGridParam','selrow');
			eliminarFuncionarioExternoAudiencia(rowid,'<%=Roles.AGENTEMP.getValorId()%>',false);
		});
	
	}

	
	/*
	*Funcion que carga el grid de defensores
	*/		
	function cargaGridDefensor(){
	
		jQuery("#gridDefensor").jqGrid({
			url:'<%= request.getContextPath()%>/consultarFuncionarioExternoAudienciaPorRol.do?idEvento='+idEvento+'&rolId=<%=Roles.DEFENSOR.getValorId()%>',
			datatype: "xml",
			colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Titular','Presente'], 
			colModel:[ 	{name:'Nombre',index:'Nombre', width:180}, 
						{name:'APaterno',index:'APaterno', width:180}, 
						{name:'AMaterno',index:'AMaterno', width:180},
						{name:'Titular',index:'Titular', width:50,align:'center'}, 
						{name:'Presente',index:'Presente', width:60,align:'center'}
					],
				autowidth: false,
				width:924,
				autoheight:true,
				pager: jQuery('#paginadorGridDefensor'), 
				sortname: 'Nombre',
				rowNum:10,
				rowList:[10,20,30],
				gridview: false, 
				viewrecords: true, 
				sortorder: "desc",
				hidegrid: false,
				toolbar: [true,"top"],
				caption:"Defensor"
		}).navGrid('#paginadorGridDefensor',{edit:false,add:false,del:false});
			
		$("#t_gridDefensor").append("<input type='button' class='btn_Generico' id='butADefensor' value='Agregar Defensor' style='height:20px;font-size:-3'/>");

		$("#t_gridDefensor").append("<input type='button' class='btn_Generico' id='butSDefensor' value='Solicitar Defensor' style='height:20px;font-size:-3'/>");
		
		$("#t_gridDefensor").append("<input type='button' class='btn_Generico' id='butElimDef' value='Eliminar Defensor' style='height:20px;font-size:-3'/>");

		
		$("#butADefensor","#t_gridDefensor").click(function(){
			puestoFuncionario="defensor";
			agregarFuncionarioExternoAudiencia('<%=Roles.DEFENSOR.getValorId()%>',false);
		});
		
		$("#butSDefensor","#t_gridDefensor").click(function(){
			solicitarDefensor(false);
		});

		$("#butElimDef","#t_gridDefensor").click(function(){
			puestoFuncionario="defensor";
			var rowid = jQuery("#gridDefensor").jqGrid('getGridParam','selrow');
			eliminarFuncionarioExternoAudiencia(rowid,'<%=Roles.DEFENSOR.getValorId()%>',false);
		});
	
	}


	

	
	var banderaCargaGridTestigo = true;
	/*
	*Funcion que carga el grid de testigos
	*/
	function cargaGridTestigo(){

		tipoConsulta=3;

		if( banderaCargaGridTestigo == true){
			
			jQuery("#gridTestigo").jqGrid({

				url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idEvento+"&tipoConsulta="+tipoConsulta,						
				datatype: "xml",
				colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Presente'], 
				colModel:[ 	{name:'Nombre',index:'Nombre', width:180}, 
							{name:'APaterno',index:'APaterno', width:180}, 
							{name:'AMaterno',index:'AMaterno', width:180},
							{name:'Presente',index:'Presente', width:50}, 
						],
					autowidth: false,
					width:924,
					autoheight:true,
					pager: jQuery('#paginadorGridTestigo'), 
					sortname: 'Nombre', 
					rowNum:10,
					rowList:[10,20,30],
					gridview: false, 
					viewrecords: true, 
					sortorder: "desc",
					hidegrid: false,
					toolbar: [true,"top"],
					caption:"Testigo",
					ondblClickRow: function(rowid) {
						
						creaNuevoTestigo(rowid);
					} 
			}).navGrid('#paginadorGridTestigo',{edit:false,add:false,del:false});
			
			$("#t_gridTestigo").append("<input type='button' class='btn_Generico' value='Agregar Testigo' style='height:20px;font-size:-3'/>");
			$("input","#t_gridTestigo").click(function(){
				creaNuevoTestigo(null);
			});
			
			banderaCargaGridTestigo= false;
		}
		else{
			jQuery("#gridTestigo").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idEvento+"&tipoConsulta="+tipoConsulta , datatype: "xml" });
			$("#gridTestigo").trigger("reloadGrid");
		}		
	}


	/*
	*Funcion que carga el grid de peritos
	*/
	function cargaGridPerito(){
		
		jQuery("#gridPerito").jqGrid({

			url:'<%= request.getContextPath()%>/consultarFuncionarioExternoAudienciaPorRol.do?idEvento='+idEvento+'&rolId=<%=Roles.PERITOAMP.getValorId()%>',
			datatype: "xml",
			colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Titular','Presente'], 
			colModel:[ 	{name:'Nombre',index:'Nombre', width:180}, 
						{name:'APaterno',index:'APaterno', width:180}, 
						{name:'AMaterno',index:'AMaterno', width:180},
						{name:'Titular',index:'Titular', width:50,align:'center',hidden:true}, 
						{name:'Presente',index:'Presente', width:60,align:'center'}
					],
				autowidth: false,
				width:924,
				autoheight:true,
				pager: jQuery('#paginadorGridPerito'), 
				sortname: 'Nombre', 
				rowNum:10,
				rowList:[10,20,30],
				gridview: false, 
				viewrecords: true, 
				sortorder: "desc",
				hidegrid: false,
				toolbar: [true,"top"],
				caption:"Perito"
		}).navGrid('#paginadorGridPerito',{edit:false,add:false,del:false});

		$("#t_gridPerito").append("<input type='button' class='btn_Generico' id='butAgregarPer' value='Agregar Perito' style='height:20px;font-size:-3'/>");
		
		$("#t_gridPerito").append("<input type='button' class='btn_Generico' id='butElimPer' value='Eliminar Perito' style='height:20px;font-size:-3'/>");
		
		$("#butAgregarPer","#t_gridPerito").click(function(){
			puestoFuncionario="perito";
			agregarFuncionarioExternoAudiencia('<%=Roles.PERITOAMP.getValorId()%>',false);
		});

		$("#butElimPer","#t_gridPerito").click(function(){
			puestoFuncionario="perito";
			var rowid = jQuery("#gridPerito").jqGrid('getGridParam','selrow');
			eliminarFuncionarioExternoAudiencia(rowid,'<%=Roles.PERITOAMP.getValorId()%>',false);
		});
		
	}

	
	/*
	*Funcion que carga el grid de elemento de policia ministerial
	*/
	function cargaGridPolicia(){

		jQuery("#gridPolicia").jqGrid({

			url:'<%= request.getContextPath()%>/consultarFuncionarioExternoAudienciaPorRol.do?idEvento='+idEvento+'&rolId=<%=Roles.POLICIAMINISTER.getValorId()%>',						
			datatype: "xml",
			colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Parte','Presente'], 
			colModel:[ 	{name:'Nombre',index:'Nombre', width:180}, 
						{name:'APaterno',index:'APaterno', width:180}, 
						{name:'AMaterno',index:'AMaterno', width:180},
						{name:'Parte',index:'Parte', width:100,hidden:true}, 
						{name:'Presente',index:'Presente', width:50},
					],
				autowidth: false,
				width:924,
				autoheight:true,
				pager: jQuery('#paginadorGridPolicia'), 
				sortname: 'Nombre', 
				rowNum:10,
				rowList:[10,20,30],
				gridview: false, 
				viewrecords: true, 
				sortorder: "desc",
				hidegrid: false,
				toolbar: [true,"top"],
				caption:"Elemento de la policía ministerial"
		}).navGrid('#paginadorGridPolicia',{edit:false,add:false,del:false});
		
		$("#t_gridPolicia").append("<input type='button' class='btn_Generico' id='butAgrePoli' value='Agregar Polic&iacute;a Ministerial' style='height:20px;font-size:-3'/>");

		//Button eliminar policia ministerial
		$("#t_gridPolicia").append("<input type='button' class='btn_Generico' id='butElimPoli' value='Eliminar Polic&iacute;a Ministerial' style='height:20px;font-size:-3'/>");
		
		$("#butAgrePoli","#t_gridPolicia").click(function(){
			puestoFuncionario="policia";
			agregarFuncionarioExternoAudiencia('<%=Roles.POLICIAMINISTER.getValorId()%>',false);
		});

		$("#butElimPoli","#t_gridPolicia").click(function(){
			puestoFuncionario="policia";
			var rowid = jQuery("#gridPolicia").jqGrid('getGridParam','selrow');
			eliminarFuncionarioExternoAudiencia(rowid,'<%=Roles.POLICIAMINISTER.getValorId()%>',false);
		});
	}


	/*
	*Funcion abre una ventana modal para solicitar un abogado defensor
	*/
	function solicitarDefensor(esAdudienciaSig){

		var idAudienciaDefensor="";
		
		if(esAdudienciaSig == true){
			idAudienciaDefensor=idAudienciaSiguiente;
		}else{
			idAudienciaDefensor=idAudiencia;
		}
		
		//Carga el grid con los involucrados 
		jQuery("#ListaImputados").jqGrid({ 
			url:'<%= request.getContextPath()%>/consultarImputadoAudiencia.do?idEvento='+ idAudienciaDefensor +'&tipo=0', 
			datatype: "xml", 
			colNames:['Nombre','Apellido Paterno','Apellido Materno'], 
			colModel:[ 	{name:'nombre',index:'nombre', width:70, align: "center"}, 
			           	{name:'apaterno',index:'apaterno', width:120, align: "center"}, 
			           	{name:'amaterno',index:'amaterno', width:120, align: "center"}
			           
					],
			pager: jQuery('#paginadorListaImputados'),
			rowNum:10,
			rowList:[10,20,30],
			width: 320,
			height:80,
			sortname: 'nombre',
			viewrecords: true,
			sortorder: "desc",
			multiselect: true,
			caption: "Lista de Imputados"
			
		}).navGrid('#paginadorListaImputados',{edit:false,add:false,del:false});

		//Abre la ventana modal
		$("#divSolicitarDefensor").dialog("open");
	  	$("#divSolicitarDefensor").dialog({ autoOpen: true, 
			modal: true, 
		  	title: 'Solicitar Defensor', 
		  	dialogClass: 'alert',
		  	position: [312,40],
		  	width: 435,
		  	height: 300,
		  	maxWidth: 1000,
		  	buttons:{"Enviar":function() {
	   		 		var s;
					s = jQuery("#ListaImputados").jqGrid('getGridParam','selarrrow');
					
					if (s.length > 0){
						var idsConDefensor = validarSolicitudesDefensor(s,idAudienciaDefensor);
					
						if (idsConDefensor == undefined 
								|| idsConDefensor == null 
								|| idsConDefensor.length == 0){
							enviarSolicitudDefensor(s,idAudienciaDefensor);
			   				$(this).dialog("close");
						}else{
							var mensaje = "Los siguientes imputados ya cuentan con una solicitud de defensor asociada: <br>";
							for (var i=0; i<idsConDefensor.length; i++){
								var ret = jQuery("#ListaImputados").jqGrid('getRowData',idsConDefensor[i]);
								mensaje += ret.nombre +" "+ ret.apaterno +" "+ ret.amaterno +"<br>"
							}
							mensaje+= "¿Desea enviar las solicitudes de defensor?";
							customConfirm(mensaje,"Advertencia",function(){
								enviarSolicitudDefensor(s,idAudienciaDefensor);
								$(this).dialog("close");
							});
						}
	   		  		}else{
	   		  			customAlert("Por favor seleccione por lo menos un registro de la lista de imputados.");
	   		  		}
		  		},
		  		"Cancelar":function() {
			  		customConfirm("¿Realmente desea salir de la solicitud del abogado defensor sin enviar la solicitud?", "", cancelarEnvioAudiencia);			  		
		  		}
		  	}
		});	  	
	}
	
	function enviarSolicitudDefensor(idsImputados, idAudiencia){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/enviarSolicitudDefensor.do?idImputado='+idsImputados+'&idAudiencia='+idAudiencia+'',
			async: false,
			dataType: 'xml',
			success: function(xml){
				if(parseInt($(xml).find('code').text())==0){
					alertDinamico("Solicitud enviada");
				}
				else {
					alertDinamico("Ocurri&oacute; un error al intentar enviar la solicitud de Defensor.<br/>" +
					"Por favor contacte al administrador");
				}
			}
		});
	}
	
	function validarSolicitudesDefensor(idsImputados, idAudiencia){
		var idsConDefensor;
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/validarSolicitudDefensorPorImputadoAudiencia.do?idImputado='+idsImputados+'&audienciaId='+idAudiencia+'',
			async: false,
			dataType: 'xml',
			success: function(xml){
				if($(xml).find('string')!=null && $(xml).find('string').text() !='' ){
					var idsCadena = $(xml).find('string').text();
					idsConDefensor = idsCadena.split(",");
				}
			}
		});
		return idsConDefensor;
	}
	
	function cancelarEnvioAudiencia(){
		$("#divSolicitarDefensor").dialog("close");
	}				
	
	
//*************************************************************FUCIONALIDAD PARA LA CEJA PRUEBAS******************************************************************/
	
	//////////////////////FUNCIONALIDAD PARA LA CEJA DATOS DE PRUEBA
	
	var banderaDatosPrueba = true;
	/**
	*Funcion que carga el grid datos de Prueba
	*/
	function cargaGridDatosPrueba(){

		if(banderaDatosPrueba == true){
			jQuery("#gridDatosDePruebaPJENS").jqGrid({ 
				url:'<%=request.getContextPath()%>/consultarDatosDePruebaPorFiltro.do?audienciaId='+audienciaId+'&numeroExpediente='+numeroExpediente+'',
				datatype: "xml",
				colNames:['Nombre del Dato','Número de Identificación', 'Reg. de Cadena de Custodia','Registrado','Aceptado','Desechado'], 
				colModel:[ 					
				           	{name:'nombreDato',index:'nombreDato', width:100, align:'left',sortable:false}, 
				           	{name:'numIdentificacion',index:'numIdentificacion', width:80, align:'left',sortable:false},
				        	{name:'rcc',index:'rcc', width:100, align:'left',sortable:false}, 
				           	{name:'registrado',index:'registrado', width:50, align:'center',sortable:false},
				           	{name:'aceptado',index:'aceptado', width:50, align:'center',sortable:false},
				           	{name:'rechazado',index:'rechazado', width:50, align:'center',sortable:false},
						],
				pager: jQuery('#pagerGridDatosDePruebaPJENS'),
				rowNum:10,
				autoWidth:false,
				width:800,
				//autoheight:true,
				rowList:[10,20,30],
				sortname: 'NombreDato',
				viewrecords: true,
				sortorder: "desc",
				caption:"Datos de Prueba",
				hidegrid: false,
				toolbar: [true,"top"],
				ondblClickRow: function(rowid) {
					consultarDatoDePrueba(rowid);
				} 
			}).navGrid('#pagerGridDatosDePruebaPJENS',{edit:false,add:false,del:false});
			$("#gview_gridDatosDePruebaPJENS .ui-jqgrid-bdiv").css('height', '200px');

			banderaDatosPrueba=false;
			
			$("#t_gridDatosDePruebaPJENS").append("<input type='button' id='btnAgregarDatoPrueba' class='btn_Generico' value='Agregar' style='height:20px;width:60px;font-size:-3'/>"+" "+"<input type='button' id='btnRelacionarDatoPrueba' class='btn_Generico' value='Relacionar' style='height:20px;width:70px;font-size:-3'/>");
			
			$("#btnAgregarDatoPrueba","#t_gridDatosDePruebaPJENS").click(function(){
				agregarDatoDePrueba();
			});

			$("#btnRelacionarDatoPrueba","#t_gridDatosDePruebaPJENS").click(function(){
				var rowid = jQuery("#gridDatosDePruebaPJENS").jqGrid('getGridParam','selrow');
				if(rowid){
					var renglon = jQuery("#gridDatosDePruebaPJENS").jqGrid('getRowData',rowid);
					//verifica si la sub string checked se encuentra en la cadena input,
					//de NO ser asi devuelve -1
					if(renglon.aceptado.indexOf("checked") != -1){
						
						relacionarMediosDePrueba(rowid);
					}
					else{
						alertDinamico("El dato de prueba debe ser aceptado para \n relacionarlo con un medio de prueba");
					}
				}
				else{
					alertDinamico("Seleccione un dato de prueba a relacionar");
				}
			});

			//Originalmente la funcionalidad de asociar paria de este grid
			$("#btnAsociarDatoPrueba","#t_gridDatosDePruebaPJENS").click(function(){
				var rowid = jQuery("#gridDatosDePruebaPJENS").jqGrid('getGridParam','selrow');
				if(rowid){
					var renglon = jQuery("#gridDatosDePruebaPJENS").jqGrid('getRowData',rowid);
					//verifica si la sub string checked se encuentra en la cadena input,
					//de NO ser asi devuelve -1
					if(renglon.aceptado.indexOf("checked") != -1){
						asociarDatoPrueba(rowid);
					}
					else{
						alertDinamico("El dato de prueba debe ser aceptado para \n asociarlo con un imputado");
					}
				}
				else{
					alertDinamico("Seleccione un dato de prueba a asociar");
				}
			});
		}
		else{
			jQuery("#gridDatosDePruebaPJENS").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDatosDePruebaPorFiltro.do?audienciaId='+audienciaId+'&numeroExpediente='+numeroExpediente+'',datatype: "xml" });
			$("#gridDatosDePruebaPJENS").trigger("reloadGrid");
		}
	}

	
	/*
	*Funcion para cambiar el estado del dato de prueba
	*/
	function rechazarDatoPrueba(datoPruebaId){
		$.ajax({
	   		type: 'POST',
	   		url: '<%= request.getContextPath()%>/actualizarEstadoDatoPrueba.do?esAceptado=false&numeroExpediente='+numeroExpediente+'',
	   		data:'datoPruebaId='+datoPruebaId,
	   		dataType: 'xml',
	   		async: false,
	   		success: function(xml){
	   			cargaGridDatosPrueba();
	   			cargaGridPrueba();
	   			alertDinamico("El dato de prueba ha sido rechazado");
	   		}
		});
	}
	
	/*
	*Funcion para cambiar el estado del dato de prueba
	*/
	function aceptarDatoPrueba(datoPruebaId){
		$.ajax({
    		type: 'POST',
    		url: '<%= request.getContextPath()%>/actualizarEstadoDatoPrueba.do?esAceptado=true&numeroExpediente='+numeroExpediente+'',
    		data:'datoPruebaId='+datoPruebaId,
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			alertDinamico("El dato de prueba ha sido aceptado");	
    			cargaGridDatosPrueba();
    			cargaGridPrueba();
    		}
		});
	}
	

	
	//Funcion usada para asignarle id a la ventana de consultar datos de prueba
	var consultarDatoPruebaWindow = 1;
	/*
	*Funcion para consultar datos de prueba vs medios de prueba
	*/
	function consultarDatoDePrueba(idDatoPrueba){
		consultarDatoPruebaWindow++;
		$.newWindow({id:"iframewindowConsultarDatoPrueba"+consultarDatoPruebaWindow, statusBar: true, posx:80,posy:60,width:1240,height:420,title:"Consultar Dato de Prueba", type:"iframe"});
    	$.updateWindowContent("iframewindowConsultarDatoPrueba"+consultarDatoPruebaWindow,'<iframe src="<%= request.getContextPath() %>/consultarDetalleDatoPruebaPJENS.do?idDatoPrueba='+idDatoPrueba+'&numeroExpediente='+numeroExpediente+'" width="1240" height="420" />');
	}


	
	/*
	*Funcion que abre la modal para relacionar los datos de prueba 
	*con los medios de prueba
	*/
	function relacionarMediosDePrueba(idDatoPrueba){
		
		cargaGridMediosPrueba(idDatoPrueba);
		$("#divRelacionarMediosDePruebaPJENS").dialog("open");
		$("#divRelacionarMediosDePruebaPJENS").dialog({ autoOpen: true, modal: true, 
		title: 'Relacionar medios de prueba', 
		dialogClass: 'alert', position: [350,50],
		width: 800, height:480, maxWidth: 800, maxHeight:550,
		buttons:{
			"Relacionar":function() {			  		

				var iDsMediosPrueba=""; 
				iDsMediosPrueba = jQuery("#gridRelacionarMediosDePruebaPJENS").jqGrid('getGridParam','selarrrow');
				guardarRelacionMediosDePruebaVsDatoPrueba(idDatoPrueba,iDsMediosPrueba);
			   	$(this).dialog("close");
			},
			"Cancelar":function() {

				customConfirm("¿Realmente desea salir?", "", cancelarRelacionarMediosDePrueba);
			}
		}
		});
	}
	
	function cancelarRelacionarMediosDePrueba(){
  		$("#divRelacionarMediosDePruebaPJENS").dialog("close");
	}


	
	/**
	*Funcion que guarda la relacion entre los medios de prueba
	*recibe un arreglo con los ids de medios de prueba y el id del dato de prueba
	*con el que se relacionaran
	*/
	function guardarRelacionMediosDePruebaVsDatoPrueba(idDatoPrueba,iDsMediosPrueba){

		$.ajax({
    		type: 'POST',
    		url: '<%= request.getContextPath()%>/relacionarMedioPruebaConDatoPrueba.do?iDsMediosPrueba='+iDsMediosPrueba+'&idDatoPrueba='+idDatoPrueba+'&numeroExpediente='+numeroExpediente+'',
    		data:'',
    		dataType:'xml',
    		async:false,
    		success: function(xml){
	   			if(parseInt($(xml).find('code').text())==0)
	    		  {
	   				alertDinamico("La relación ha sido guardada");
					//Limpiar los ids seleccionados
	   	    	}
	  			else{
	  				alertDinamico("Ocurrió un error durante la relación");
	      		}
    		}
		});
	} 


	//Variable para controlar si se carga por primera vez el grid
	var banderaRelacionarMediosPrueba = true;
	
	/**
	*Funcion que carga el grid medios de Prueba
	*/
	function cargaGridMediosPrueba(idDatoPrueba){

		if(banderaRelacionarMediosPrueba == true){

			jQuery("#gridRelacionarMediosDePruebaPJENS").jqGrid({ 
				url:'<%=request.getContextPath()%>/consultarMediosPruebaXDatoPrueba.do?idDatoPrueba='+idDatoPrueba+'&numeroExpediente='+numeroExpediente+'',
				datatype: "xml",
				colNames:['Nombre del Medio','Número de Identificación'], 
				colModel:[ 					
				           	{name:'nombreDato',index:'nombreDato', width:350, align:'center'}, 
				           	{name:'numIdentificacion',index:'numIdentificacion', width:350, align:'center'},
						],
				pager: jQuery('#pagerGridRelacionarMediosDePruebaPJENS'),
				rowNum:10,
				autoWidth:false,
				width:750,
				height:250,
				//autoheight:true,
				rowList:[10,20,30],
				sortname: 'NombreDato',
				viewrecords: true,
				sortorder: "desc",
				caption:"Medios de Prueba",
				hidegrid: false,
				toolbar: [true,"top"],
				multiselect:true
			}).navGrid('#gridRelacionarMediosDePruebaPJENS',{edit:false,add:false,del:false});
			banderaRelacionarMediosPrueba = false;
					
			$("#t_gridRelacionarMediosDePruebaPJENS").append("<input type='button' class='btn_Generico' value='Agregar' style='height:20px;width:60px;font-size:-3'/>");
			$("input","#t_gridRelacionarMediosDePruebaPJENS").click(function(){
				agregarMedioDePrueba();
			});
		}
		else{
			jQuery("#gridRelacionarMediosDePruebaPJENS").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarMediosPruebaXDatoPrueba.do?idDatoPrueba='+idDatoPrueba+'&numeroExpediente='+numeroExpediente+'',datatype: "xml" });
			$("#gridRelacionarMediosDePruebaPJENS").trigger("reloadGrid");
		}
	}


	/*
	*Funcion que abre la modal para relacionar los datos de prueba 
	*con los medios de prueba
	*/
	function asociarDatoPrueba(idDatoPrueba){
		
		cargaGridImputadosAsociar(idDatoPrueba);
		$("#divAsociarDatoDePruebaPJENS").dialog("open");
		$("#divAsociarDatoDePruebaPJENS").dialog({ autoOpen: true, modal: true, 
		title: 'Asociar imputados al dato de prueba', 
		dialogClass: 'alert', position: [350,50],
		width: 800, height:480, maxWidth: 800, maxHeight:550,
		buttons:{
			"Asociar":function() {			  		

				var iDsImputados=""; 
				iDsImputados = jQuery("#gridAsociarDatoDePruebaPJENS").jqGrid('getGridParam','selarrrow');
				guardarAsociacionDatoPrueba(idDatoPrueba,iDsImputados);
			   	$(this).dialog("close");
			},
			"Cancelar":function() {
				customConfirm("¿Realmente desea salir?", "", salir);						
			}
		}
		});
	}
	
	function salir(){
  		$("#divAsociarDatoDePruebaPJENS").dialog("close");
	}
	

	

	//bandera para controlar el cargado de la asociación de imputados
	banderaAsociarImputado=true;
	
	/**
	*Funcion que carga el grid con los imputados a asociar
	*/
	function cargaGridImputadosAsociar(idDatoPrueba){

		if(banderaAsociarImputado == true){

			
			jQuery("#gridAsociarDatoDePruebaPJENS").jqGrid({ 
				url:'<%=request.getContextPath()%>/consultarImputadosXDatoPrueba.do?idDatoPrueba='+idDatoPrueba+'&idAudiencia='+idAudiencia+'&numeroExpediente='+numeroExpediente+'',
				datatype: "xml",
				colNames:['Nombre del Imputado'], 
				colModel:[ 					
				           	{name:'nombreImputado',index:'nombreDato', width:350, align:'center'}, 
						],

				pager: jQuery('#pagerGridAsociarDatoDePruebaPJENS'),
				rowNum:10,
				autoWidth:false,
				width:750,
				height:250,
				//autoheight:true,
				rowList:[10,20,30],
				sortname: 'NombreDato',
				viewrecords: true,
				sortorder: "desc",
				caption:"Imputados",
				toolbar: [true,"top"],
				multiselect:true
			}).navGrid('#gridAsociarDatoDePruebaPJENS',{edit:false,add:false,del:false});
			banderaAsociarImputado = false;
		}
		else{
			jQuery("#gridAsociarDatoDePruebaPJENS").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarImputadosXDatoPrueba.do?idDatoPrueba='+idDatoPrueba+'&idAudiencia='+idAudiencia+'&numeroExpediente='+numeroExpediente+'',datatype: "xml" });
			$("#gridAsociarDatoDePruebaPJENS").trigger("reloadGrid");
		}
	}

	
	/**
	*Funcion que guarda la asociacion entre los datos de prueba
	*recibe un arreglo con los ids de los imputados y el id del dato de prueba
	*con el que se asociaran
	*/
	function guardarAsociacionDatoPrueba(idDatoPrueba,iDsImputados){

		$.ajax({
    		type: 'POST',
    		url: '<%= request.getContextPath()%>/asociarImputadoConDatoPrueba.do?iDsImputados='+iDsImputados+'&idDatoPrueba='+idDatoPrueba+'',
    		data:'',
    		dataType:'xml',
    		async:false,
    		success: function(xml){
	   			if(parseInt($(xml).find('code').text())==0)
	    		  {
	   				alertDinamico("La asociación ha sido guardada");
					//Limpiar los ids seleccionados
	   	    	}
	  			else{
	  				alertDinamico("Ocurrió un error durante la asociación");
	      		}
	      		cargaGridPrueba();
	      		
    		}
		});
	} 
	
	
	/*
	*Funcion que despliega la ventana para agregar un dato de prueba
	*/
	function agregarDatoDePrueba(){

		seleccioneTipoDatoPrueba();
		$('#tipoDatoPruebaPJENS').attr('selectedIndex', 0);
		$("#divAgregarDatoDePrueba").dialog("open");
		$("#divAgregarDatoDePrueba").dialog({ autoOpen: true, modal: true, 
		title: 'Agregar Dato de Prueba', 
		dialogClass: 'alert', position: [500,50],
		width: 595, height: 450, maxWidth: 595,
		buttons:{
			"Agregar":function() {
				guardarDatoPrueba(null);
			   	//$(this).dialog("close");
			},
			"Cancelar":function() {
				
				customConfirm("¿Realmente desea salir del registro del dato de prueba?", "", cancelarAgregarDatoDePrueba);					  		
			}
		}
		});	 
	}

	function cancelarAgregarDatoDePrueba(){
		limpiarYSalirDatoDePrueba();
  		$("#divAgregarDatoDePrueba").dialog("close");
	}
	
	

	/*
	*Funciones para agregar medios de prueba dependiendo de la seleccion
	*/
	function guardarDatoPrueba(datoPruebaId){
		
		var seleccion = $("#tipoDatoPruebaPJENS option:selected").val();
		var validacion=false;
		var paramDatoPrueba=""; 
		
		if(seleccion == "0"){
			validacion=false;
			alertDinamico("Seleccione un tipo de dato de prueba");	
		}
		//Validacion de documentos
		if(seleccion == "1"){
			if($('#txtNombreDatoPruebaPJENS').val() == ""){
				alertDinamico("Ingrese un nombre");
			}
			else{
				if($('#txtNumeroIdeDatoPruebaPJENS').val() == ""){
					alertDinamico("Ingrese un número de identificación");
				}
				else{
					
					if($('#txtAreaDescripcionDatoPruebaPJENS').val() == ""){
						alertDinamico("Ingrese una descripción");
					}
					else{
						validacion=true;
						paramDatoPrueba += 'audienciaId=' + audienciaId;
						paramDatoPrueba += '&tipoDatoPrueba=' + "documento";
						paramDatoPrueba += '&nombreDato=' + $('#txtNombreDatoPruebaPJENS').val();
						paramDatoPrueba += '&numIdDato=' + $('#txtNumeroIdeDatoPruebaPJENS').val();
						paramDatoPrueba += '&rccDato=' + $('#txtRccDatoPruebaPJENS').val();
						paramDatoPrueba += '&descDato=' + $('#txtAreaDescripcionDatoPruebaPJENS').val();
						paramDatoPrueba += '&numeroExpediente=' + numeroExpediente;
						paramDatoPrueba += '&datoPruebaId=' + datoPruebaId;
					}
				}
			}
		}

		if(seleccion == "2"){

			if($("#cbxTipoObjetoDatoPruebaPJENS option:selected").val() == "0" ){
				alertDinamico("Seleccione un tipo de objeto");
			}
			else{
				if($('#txtNombreDatoPruebaPJENS').val() == ""){
					alertDinamico("Ingrese un nombre");
				}
				else{
					if($('#txtNumeroIdeDatoPruebaPJENS').val() == ""){
						alertDinamico("Ingrese un número de identificación");
					}
					else{
						
						if($('#txtAreaDescripcionDatoPruebaPJENS').val() == ""){
							alertDinamico("Ingrese una descripción");
						}
						else{
							validacion=true;
							paramDatoPrueba += 'audienciaId=' + audienciaId;
							paramDatoPrueba += '&tipoDatoPrueba=' + "objeto";
							paramDatoPrueba += '&tipoObjetoDatoPrueba=' + $("#cbxTipoObjetoDatoPruebaPJENS option:selected").index();
							paramDatoPrueba += '&nombreDato=' + $('#txtNombreDatoPruebaPJENS').val();
							paramDatoPrueba += '&numIdDato=' + $('#txtNumeroIdeDatoPruebaPJENS').val();
							paramDatoPrueba += '&rccDato=' + $('#txtRccDatoPruebaPJENS').val();
							paramDatoPrueba += '&descDato=' + $('#txtAreaDescripcionDatoPruebaPJENS').val();
							paramDatoPrueba += '&numeroExpediente=' + numeroExpediente;
							paramDatoPrueba += '&datoPruebaId=' + datoPruebaId;
						}
					}
				}
			}
		}

		if(validacion==true){
			$.ajax({
	    		type: 'POST',
	    		url: '<%= request.getContextPath()%>/agregarDatoPrueba.do',
	    		data:paramDatoPrueba,
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){
	    			alertDinamico("Dato de prueba registrado correctamente");
	    			$("#divAgregarDatoDePrueba").dialog("close");
	    			limpiarYSalirDatoDePrueba();
	    			cargaGridDatosPrueba();				
	    		}
			});
		}
	}

	
	/*
	*Funcion que controla el mostrar los campos dependiendo de la seleccion
	*del usuario
	*/
	function controlDatoDePrueba(){

		seleccioneTipoDatoPrueba();
		limpiaTipoDatoDePrueba();
				
		var seleccionTipoDato = $("#tipoDatoPruebaPJENS option:selected").val();		
		
		if(seleccionTipoDato == "0"){
			seleccioneTipoDatoPrueba();	
		}
		if(seleccionTipoDato == "1"){

			//Documentos
			seleccioneTipoDatoPrueba();			
			$('#etiNombreDatoPruebaPJENS').show();
			$('#divTxtNombreDatoPruebaPJENS').show();
			$('#etiNumIdeDatoPruebaPJENS').show();
			$('#divTxtNumeroIdeDatoPruebaPJENS').show();
			$('#etiRccDatoPruebaPJENS').show();
			$('#divTxtRccDatoPruebaPJENS').show();
			$('#etiDescripcionPJENS').show();
			$('#divTxtAreaDescripcionDatoPruebaPJENS').show();
		}
		if(seleccionTipoDato == "2"){

			//Objetos
			seleccioneTipoDatoPrueba();
			consultarCatalogoDeObjetosDatoDePrueba();
			$('#etiTipoObjetoDatoPruebaPJENS').show();
			$('#divCbxTipoObjetoDatoPruebaPJENS').show();
			$('#etiNombreDatoPruebaPJENS').show();
			$('#divTxtNombreDatoPruebaPJENS').show();
			$('#etiNumIdeDatoPruebaPJENS').show();
			$('#divTxtNumeroIdeDatoPruebaPJENS').show();
			$('#etiRccDatoPruebaPJENS').show();
			$('#divTxtRccDatoPruebaPJENS').show();
			$('#etiDescripcionPJENS').show();
			$('#divTxtAreaDescripcionDatoPruebaPJENS').show();
			
		}
	}

	/*
	*Oculta todos los div de todos los objetos
	*/
	function seleccioneTipoDatoPrueba(){
		
		$('#etiTipoObjetoDatoPruebaPJENS').hide();
		$('#divCbxTipoObjetoDatoPruebaPJENS').hide();

		$('#etiNombreDatoPruebaPJENS').hide();
		$('#divTxtNombreDatoPruebaPJENS').hide();

		$('#etiNumIdeDatoPruebaPJENS').hide();
		$('#divTxtNumeroIdeDatoPruebaPJENS').hide();

		$('#etiRccDatoPruebaPJENS').hide();
		$('#divTxtRccDatoPruebaPJENS').hide();

		$('#etiDescripcionPJENS').hide();
		$('#divTxtAreaDescripcionDatoPruebaPJENS').hide();
	}

	/*
	*Funcion que carga los combos de las de objetos
	*/
	function consultarCatalogoDeObjetosDatoDePrueba() {
     $.ajax({
    	  type: 'POST',
    	  url: '<%= request.getContextPath()%>/consultarTiposObjeto.do',
    	  data: '',
    	  async: false,
    	  dataType: 'xml',
    	  success: function(xml){
	    	  $('#cbxTipoObjetoDatoPruebaPJENS').empty();
	    	  $('#cbxTipoObjetoDatoPruebaPJENS').append('<option value="0">- Seleccione -</option>');
	    	  $(xml).find('catTipoObjetos').each(function(){
				$('#cbxTipoObjetoDatoPruebaPJENS').append('<option value="' + $(this).find('valor').text() + '">' + $(this).find('valor').text() + '</option>');
				});
    	  }
    	});
	}


	/*
	*Limpia todos los campos de todos los objetos
	*/
	function limpiaTipoDatoDePrueba(){

		$('#txtAreaDescripcionDatoPruebaPJENS').val("");
		$('#txtRccDatoPruebaPJENS').val("");
		$('#txtNumeroIdeDatoPruebaPJENS').val("");
		$('#txtNombreDatoPruebaPJENS').val("");
		$('#cbxTipoObjetoDatoPruebaPJENS').attr('selectedIndex', 0);
	}

	/*
	*Funcion que limpia todos los campos al salir de la ventana
	*y setea el valor seleccione por default
	*/
	function limpiarYSalirDatoDePrueba(){
		limpiaTipoDatoDePrueba();
		$('#tipoDatoPruebaPJENS').attr('selectedIndex',0);

	}

	
	/*
	*Funcion que despliega la ventana para agregar un Medio de prueba
	*/
	function agregarMedioDePrueba(){

		seleccioneTipoMedioPrueba();
		$('#tipoMediaPruebaPJENS').attr('selectedIndex', 0);
		controlMedioDePrueba();
		$("#divAgregarMedioDePrueba").dialog("open");
		$("#divAgregarMedioDePrueba").dialog({ autoOpen: true, modal: true, 
		title: 'Medio de Prueba', 
		dialogClass: 'alert', position: [500,50],
		width: 495, height: 400, maxWidth: 495,
		buttons:{
			"Agregar":function() {			  		
				guardarMedioDePrueba();
				//limpiaTipoMediaPrueba();
			   	//$(this).dialog("close");
			},
			"Cancelar":function() {
		
				customConfirm("¿Realmente desea salir del registro del medio de prueba?", "", cancelarAgregarMedioDePrueba);			
			}
		}
		});	 
	}
	
	function cancelarAgregarMedioDePrueba(){
		limpiarYSalirMedioPrueba();
  		$("#divAgregarMedioDePrueba").dialog("close");
	}

	

	/*
	*Funciones para agregar medios de prueba dependiendo de la seleccion
	*/
	function guardarMedioDePrueba(){
		
		var seleccion = $("#tipoMediaPruebaPJENS option:selected").val();
		var paramMedioPrueba=""; 
		
		if(seleccion == "0"){

			alertDinamico("Seleccione un tipo de medio de prueba");	
		}
		//Validacion de documentos
		if(seleccion == "1"){
			if($("#cbxSubTipoDocumentoMedioPruebaPJENS option:selected").val() == "0"){
				alertDinamico("Ingrese un subtipo de documento");
			}
			else{
				if($('#txtNombreDocumentoMedioPruebaPJENS').val() == ""){
					alertDinamico("Ingrese un nombre");
				}
				else{
					if($('#txtNumeroIdeDocumentoMedioPruebaPJENS').val() == ""){
						alertDinamico("Ingrese un número de identificación");
					}
					else{
						if($('#archivoPorSubir').val() == ""){
							alertDinamico("Adjunte un archivo");
						}
						else{
							if($('#txtAreaDescripcionDocMedioPruebaPJENS').val() == ""){
								alertDinamico("Ingrese una descripción");
							}
							else{
								
								guardarMedioPruebaConDocDig();
								alertDinamico("El medio de prueba ha sido guardado \n y relacionado al dato");
								$("#divAgregarMedioDePrueba").dialog("close");
								$("#divRelacionarMediosDePruebaPJENS").dialog("close");
								cargaGridMediosDePrueba();
							}
					}
				}
			}
		}
		}
		
		if(seleccion == "2"){
			$("#divAgregarMedioDePrueba").dialog("close");
			creaNuevoIndividuo(<%=Calidades.TESTIGO.getValorId()%>);
		}
	}

	//funcion que guarda las solicitudes de audiencia, de transcripcion de audiencia y  de audio/video
	function guardarMedioPruebaConDocDig(){


			forma = document.medioPruebaForm;

			forma.datoPruebaId.value= jQuery("#gridDatosDePruebaPJENS").jqGrid('getGridParam','selrow');
			forma.audienciaId.value = audienciaId;
			forma.tipoMedioPrueba.value = "documento";
			forma.subTipoMedioPrueba.value = $("#cbxSubTipoDocumentoMedioPruebaPJENS option:selected").index();
			forma.nombreDoc.value = $('#txtNombreDocumentoMedioPruebaPJENS').val();
			forma.numIdDoc.value = $('#txtNumeroIdeDocumentoMedioPruebaPJENS').val();
			forma.refUbicacionFisica.value = $('#txtRefUbicacionFisicaMedioPruebaPJENS').val();
			forma.descDocumento.value = $('#txtAreaDescripcionDocMedioPruebaPJENS').val();
			forma.numeroExpediente.value = numeroExpediente;
			forma.submit();
	}

	//Variable para controlar el id de las ventanas
	var idWindowIngresarIndividuoMedioPrueba=1;

	/*
	*Crea una nueva ventana para ingresar individuos
	*/
	function creaNuevoIndividuo(calidad) {

		$("#divRelacionarMediosDePruebaPJENS").dialog("close");
		var stringCalidad = "";
		var datoPrueba;		

		var idDatoPrueba = jQuery("#gridDatosDePruebaPJENS").jqGrid('getGridParam','selrow');
		if(idDatoPrueba){

			datoPrueba = jQuery("#gridDatosDePruebaPJENS").jqGrid('getRowData',idDatoPrueba);
		}
				
		switch(parseInt(calidad)){

			case <%=Calidades.TESTIGO.getValorId()%>:
				stringCalidad="Testigo";
			break;

			case <%=Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId()%>:
				stringCalidad="Probable Responsable";
			break;

			case 3:
				stringCalidad="Perito";
			break;
			
			case 4:
				stringCalidad="Policía ministerial";
			break;

			default: alertDinamico("Error, calidad no encontrada");				
		}
		
		idWindowIngresarIndividuoMedioPrueba++;
		
		var idWindow = "iframewindowIngresarIndividuo" + idWindowIngresarIndividuoMedioPrueba;
						
		$.newWindow({id:"iframewindowIngresarIndividuo" + idWindowIngresarIndividuoMedioPrueba, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Ingresar: "+stringCalidad+" - Relacionado a: "+datoPrueba.nombreDato, type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarIndividuo" + idWindowIngresarIndividuoMedioPrueba,'<iframe src="<%= request.getContextPath() %>/IngresarTestigo.do?audienciaId='+audienciaId+'&numeroExpediente='+numeroExpediente +'&calidad='+calidad+'&idDatoPrueba='+idDatoPrueba+'&idWindow='+idWindow+'" width="1050" height="600" />');
	    $("#iframewindowIngresarIndividuo"+idWindowIngresarIndividuoMedioPrueba+".window-closeButton").fadeIn();
	    //$("#divRelacionarMediosDePruebaPJENS").dialog("open");
	}

	/*
	*Funcion que controla el mostrar los campos dependiendo de la seleccion
	*del usuario
	*/
	function controlMedioDePrueba(){

		var seleccionTipoMedio = $("#tipoMediaPruebaPJENS option:selected").val();

		limpiaTipoMediaPrueba();
		
		if(seleccionTipoMedio == "0"){
			seleccioneTipoMedioPrueba();			
		}
		if(seleccionTipoMedio == "1"){
			seleccioneTipoMedioPrueba();
			//Documentos
			$('#etiSubTipoDocumentoMedioPruebaPJENS').show();
			$('#etiNombreDocumentoMedioPruebaPJENS').show();
			$('#etiNumeroIdeDocumentoMedioPruebaPJENS').show();
			$('#etiAdjuntarDocumentoMedioPruebaPJENS').show();
			$('#etiRefUbicacionDocumentoMedioPruebaPJENS').show();
			$('#etiDescripcionDocMedioPruebaPJENS').show();
			$('#divSubTipoDocumentoMedioPruebaPJENS').show();
			$('#divTxtNombreDocumentoMedioPruebaPJENS').show();
			$('#divNumeroIdeDocumentoMedioPruebaPJENS').show();
			$('#divTxtAdjuntarDocumentoMedioPruebaPJENS').show();
			$('#divTxtRefUbicacionFisicaMedioPruebaPJENS').show();
			$('#divTxtAreaDescripcionDocMedioPruebaPJENS').show();
		}
		if(seleccionTipoMedio == "2"){
			seleccioneTipoMedioPrueba();
			//Persona
			//$('#etiCalidadPersonaMedioPruebaPJENS').show();
			//$('#divCbxCalidadPersonaMedioPruebaPJENS').show();
		}
	}
	

	/*
	*Oculta todos los div de todos los objetos
	*/
	function seleccioneTipoMedioPrueba(){
		//Documentos
		$('#etiSubTipoDocumentoMedioPruebaPJENS').hide();
		$('#etiNombreDocumentoMedioPruebaPJENS').hide();
		$('#etiNumeroIdeDocumentoMedioPruebaPJENS').hide();
		$('#etiAdjuntarDocumentoMedioPruebaPJENS').hide();
		$('#etiRefUbicacionDocumentoMedioPruebaPJENS').hide();
		$('#etiDescripcionDocMedioPruebaPJENS').hide();
		$('#divSubTipoDocumentoMedioPruebaPJENS').hide();
		$('#divTxtNombreDocumentoMedioPruebaPJENS').hide();
		$('#divNumeroIdeDocumentoMedioPruebaPJENS').hide();
		$('#divTxtAdjuntarDocumentoMedioPruebaPJENS').hide();
		$('#divTxtRefUbicacionFisicaMedioPruebaPJENS').hide();
		$('#divTxtAreaDescripcionDocMedioPruebaPJENS').hide();
		//Persona
		//$('#etiCalidadPersonaMedioPruebaPJENS').hide();
		//$('#divCbxCalidadPersonaMedioPruebaPJENS').hide();
	}
	

	/*
	*Limpia todos los campos de todos los objetos
	*/
	function limpiaTipoMediaPrueba(){

		//DOCUMENTOS
		$('#cbxSubTipoDocumentoMedioPruebaPJENS').attr('selectedIndex', 0);
		$('#txtNombreDocumentoMedioPruebaPJENS').val("");
		$('#txtNumeroIdeDocumentoMedioPruebaPJENS').val("");
		//resetea la forma
		document.medioPruebaForm.reset();		
		$('#txtRefUbicacionFisicaMedioPruebaPJENS').val("");
		$('#txtAreaDescripcionDocMedioPruebaPJENS').val("");

		//PERSONA
		$('#cbxCalidadPersonaMedioPruebaPJENS').attr('selectedIndex', 0);

		
	}

	/*
	*Funcion que limpia todos los campos al salir de la ventana
	*y setea el valor seleccione por default
	*/
	function limpiarYSalirMedioPrueba(){
		limpiaTipoMediaPrueba();
		$('#tipoMediaPruebaPJENS').attr('selectedIndex',0);

	}

	//Funcion que carga los combos de las de objetos
	function consultarCatalogoDeObjetos() {
     $.ajax({
    	  type: 'POST',
    	  url: '<%= request.getContextPath()%>/consultarTiposObjeto.do',
    	  data: '',
    	  async: false,
    	  dataType: 'xml',
    	  success: function(xml){
	    	  $('#cbxTipoObjetoMedioPruebaPJENS').empty();
	    	  $('#cbxTipoObjetoMedioPruebaPJENS').append('<option value="0">- Seleccione -</option>');
	    	  $(xml).find('catTipoObjetos').each(function(){
				$('#cbxTipoObjetoMedioPruebaPJENS').append('<option value="' + $(this).find('valor').text() + '">' + $(this).find('valor').text() + '</option>');
				});
    	  }
    	});
	}

	
	//////////////////////FUNCIONALIDAD PARA LA SUB CEJA MEDIOS DE PRUEBA
	
	var banderaMediosPrueba = true;
	/*
	*Funcion que carga el grid de medios de prueba
	*/
	function cargaGridMediosDePrueba(){

		if(banderaMediosPrueba == true){

			jQuery("#gridMediosDePruebaPJENS").jqGrid({

				url:'<%=request.getContextPath()%>/consultarMediosPruebaAsociadosAlExpediente.do?idDatoPrueba=null&numeroExpediente='+numeroExpediente+'',
				datatype: "xml",
				colNames:['Nombre del Medio','Número de Identificación'], 
				colModel:[ 					
				           	{name:'nombreMedio',index:'nombreMedio', width:350, align:'center'}, 
				           	{name:'numIdentificacion',index:'numIdentificacion', width:350, align:'center'},
						],
				pager: jQuery('#pagerGridMediosDePruebaPJENS'),
				rowNum:10,
				autoWidth:false,
				width:750,
				//height:250,
				autoheight:true,
				rowList:[10,20,30],
				sortname: 'nombreMedio',
				viewrecords: true,
				sortorder: "desc",
				caption:"Resumen de medios de prueba",
				hidegrid: false,
				toolbar: [true,"top"],
				//multiselect:true,
				ondblClickRow: function(rowid) {
					consultarMedioDePrueba(rowid);
				} 
			}).navGrid('#pagerGridMediosDePruebaPJENS',{edit:false,add:false,del:false});
			//$("#gview_gridMediosDePruebaPJENS .ui-jqgrid-bdiv").css('height', '200px');
			banderaMediosPrueba = false;
			//$("#t_gridMediosDePruebaPJENS").append("<input type='button' id='btnAgregarMedioPrueba' class='btn_grande' value='Agregar' style='height:20px;width:60px;font-size:-3'/>"+" "+"<input type='button' id='btnDesahogarMedioPrueba' class='btn_grande' value='Desahogar' style='height:20px;width:70px;font-size:-3'/>");
			//$("#btnAgregarMedioPrueba","#t_gridMediosDePruebaPJENS").click(function(){
			//	agregarMedioDePrueba();
			//});
						
			//$("#t_gridMediosDePruebaPJENS").append("<input type='button' id='btnDesahogarMedioPrueba' class='btn_grande' value='Desahogar' style='height:20px;width:70px;font-size:-3'/>");
			//$("#btnDesahogarMedioPrueba","#t_gridMediosDePruebaPJENS").click(function(){
			//});
		}
		else{
			jQuery("#gridMediosDePruebaPJENS").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarMediosPruebaAsociadosAlExpediente.do?idDatoPrueba=null&numeroExpediente='+numeroExpediente+'',datatype: "xml" });
			$("#gridMediosDePruebaPJENS").trigger("reloadGrid");
		}	
	}

	
	//variable usada para asignarle id a la ventana de consultar datos de prueba
	var consultarMedioDePruebaWindow = 1;
	
	/*
	*Funcion para consultar datos de prueba vs medios de prueba
	*/
	function consultarMedioDePrueba(idMedioPrueba){
		
		consultarMedioDePruebaWindow++;
		$.newWindow({id:"iframewindowConsultarMedioDePrueba"+consultarMedioDePruebaWindow, statusBar: true, posx:180,posy:60,width:1140,height:420,title:"Consultar Medio de Prueba", type:"iframe"});
    	$.updateWindowContent("iframewindowConsultarMedioDePrueba"+consultarMedioDePruebaWindow,'<iframe src="<%= request.getContextPath() %>/consultarDetalleMedioPruebaPJENS.do?idMedioPrueba='+idMedioPrueba+'" width="1140" height="420" />');
	}

	//////////////////////FUNCIONALIDAD PARA LA SUB CEJA DE PRUEBA
	
	
	var banderaPrueba = true;
	/*
	*Funcion que carga el grid de medios de prueba
	*/
	function cargaGridPrueba(){

		if(banderaPrueba == true){

			jQuery("#gridPruebaPJENS").jqGrid({ 
				url:'<%=request.getContextPath()%>/consultarPruebasPorNumeroExpediente.do?numeroExpediente='+numeroExpediente+'',
				datatype: "xml", 
				colNames:['Prueba Aceptada','Número de Identificación','Reg. de Cadena de Custodia', 'No. de Imputados Asociados', 'Imputados'], 
				colModel:[ 					
				           	{name:'nombrePrueba',index:'nombrePrueba', width:200, align:'center'}, 
				           	{name:'numIdentificacion',index:'numIdentificacion', width:180, align:'center'},
				        	{name:'rcc',index:'rcc', width:200, align:'center'},
				           	{name:'noProbRespAsociados',index:'numIdentificacion', width:100, align:'center'},
				        	{name:'probablesResponsables',index:'rcc', width:200, align:'center'}				        	
						],
				pager: jQuery('#pagerGridPruebaPJENS'),
				rowNum:10,
				autoWidth:true,
				width:'auto',
				shrinkToFit:true,
				altRows : true,
				height:250,
				//autoheight:true,
				rowList:[10,20,30],
				sortname: 'nombreMedio',
				viewrecords: true,
				sortorder: "desc",
				caption:"Resumen de pruebas",
				hidegrid: false,
				toolbar: [true,"top"]
			}).navGrid('#pagerGridPruebaPJENS',{edit:false,add:false,del:false});
			
			$("#gview_gridPruebaPJENS .ui-jqgrid-bdiv").css('height', '200px');

			banderaPrueba = false;

			//Boton para asociar las pruebas a involucrados
			$("#t_gridPruebaPJENS").append("<input type='button' id='btnAsociarPrueba' class='btn_Generico' value='Asociar' style='height:20px;width:60px;font-size:-3'/>");			
			
			//Funcionalidad para al boton asociar
			$("#btnAsociarPrueba","#t_gridPruebaPJENS").click(function(){

				var rowid = jQuery("#gridPruebaPJENS").jqGrid('getGridParam','selrow');

				if(rowid){
					
					asociarDatoPrueba(rowid);
				}
				else{
					alertDinamico("Seleccione una prueba a asociar");
				}
			
			});
		}
		else{
			jQuery("#gridPruebaPJENS").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarPruebasPorNumeroExpediente.do?numeroExpediente='+numeroExpediente+'',datatype: "xml" });
			$("#gridPruebaPJENS").trigger("reloadGrid");
		}	
	}
//*********************************************************TERMINA FUCIONALIDAD PARA LA CEJA PRUEBAS******************************************************************/




	
	

//*****************************************************FUNCIONALIDAD DE LA CEJA RESOLUTIVOS DE AUDIENCIA**********************************************************\
	/*
	*Carga el grid de los resolitivos de audiencia
	*/
	function cargaGridResolutivosDeAudiencia(idEvento){ 

		jQuery("#gridAudienciasResolutivosPJENS").jqGrid({ 
			url:'<%=request.getContextPath()%>/consultarResolutivosAudienciaPJENS.do?idEvento='+ idEvento,
			datatype: "xml", 
			colNames:['Temp. Video','Resolutivo',], 
			colModel:[ 	{name:'temporizadorVideo',index:'temporizadorVideo', width:150, align:"center"},
						{name:'resolutivo',index:'resolutivo', width:400,align:"center"}
					],
			pager: jQuery('#pager5'),
			rowNum:10,
			rowList:[10,20,30],
			width: 725,
			autoheight: true,
			sortname: 'detalle',
			scrollrows : true,
			viewrecords: true,
			sortorder: "desc",
			editurl: "<%=request.getContextPath()%>/encargadoSala.jsp",
			loadComplete: function(){				
				var registros =jQuery("#gridAudienciasResolutivosPJENS").jqGrid('getDataIDs'); 
				var total = registros.length;
				$("#gridAudienciasResolutivosPJENS").jqGrid('setSelection',registros[total-1]);
					
			 },
			ondblClickRow: function(id){
				var data = jQuery("#gridAudienciasResolutivosPJENS").jqGrid('getRowData',id);
				if(!esFinalizadaAudiencia()){  
					modificarResolutivo(id, data);
				}
			}
		}).navGrid('#pager5',{edit:false,add:false,del:false});
	}
	

	/*
	*Funcion que despliega la ventana para agregar un resolutivo
	*/
	function agregarRegistrarResolutivo(){
		 
		$('#tempVideo').val("");
		cargaHoraCaptura();
		$('#resolutivo').val("");
		$("#divAgregarResolutivo").dialog("open");
		$("#divAgregarResolutivo").dialog({ autoOpen: true, modal: true, 
		title: 'Agregar Resolutivo', 
		dialogClass: 'alert', position: [150,50],
		width:550,
		height:400,
		maxWidth:550,
		maxHeight:400,
		buttons:{
			"Guardar":function() {			  		
				var datosResolutivos="";
				var campoResolutivo=document.getElementById("resolutivo");
				if(validaSoloLetrasYPunto(campoResolutivo)){	
					datosResolutivos += 'tempVideo=' + $('#tempVideo').val();
					datosResolutivos += '&resolutivo=' + $('#resolutivo').val();
		
					$.ajax({
						url: '<%= request.getContextPath()%>/registrarResolutivosPJENS.do?idEvento='+idAudiencia+'',
						type: 'POST', data: datosResolutivos, async: false, dataType: 'xml',
						success: function(xml){$("#gridAudienciasResolutivosPJENS").trigger("reloadGrid");}				
					});					  	
		
				   	$(this).dialog("close");
				}else{
					customAlert("Caracteres inv&aacute;lidos en:<br>"+campoResolutivo.value);
				}
			},
			"Cancelar":function() {
		
				customConfirm("¿Realmente desea salir de el registro de un resolutivo?", "", cancelarAgregarRegistrarResolutivo);
			}
		}
		});	 
	}
	
	

	function cancelarAgregarRegistrarResolutivo(){
  		$("#divAgregarResolutivo").dialog("close");
	}
	


	/*
	*Funcion para modificar el resolutivo seleccionado
	*/
	function modificarResolutivo(id, data){
		
		$('#tempVideo').val(data.temporizadorVideo);
		$('#resolutivo').val(data.resolutivo);
		$("#divAgregarResolutivo").dialog("open");
		$("#divAgregarResolutivo").dialog({ autoOpen: true, modal: true, 
		title: 'Agregar Resolutivo', 
		dialogClass: 'alert', position: [150,50],
		width:550,
		height:400,
		maxWidth:550,
		maxHeight:400,
		buttons:{
			"Guardar":function() {			  		
					var datosResolutivos="";
					var campoResolutivo=document.getElementById("resolutivo");
					if(validaSoloLetrasYPunto(campoResolutivo)){
						datosResolutivos += 'idResolutivo=' + id;
						datosResolutivos += '&tempVideo=' + $('#tempVideo').val();
						datosResolutivos += '&resolutivo=' + $('#resolutivo').val();
						
						$.ajax({
							url: '<%= request.getContextPath()%>/modificarResolutivosPJENS.do',
							type: 'POST', data: datosResolutivos, async: false, dataType: 'xml',
							success: function(xml){$("#gridAudienciasResolutivosPJENS").trigger("reloadGrid");}				
						});					  	
				   		$(this).dialog("close");
					}else{
						customAlert("Caracteres inv&aacute;lidos en:<br>"+campoResolutivo.value);
					}
				},
		
			"Cancelar":function() {
					customConfirm("¿Realmente desea salir de la modificaci&oacute;n de un resolutivo?", "", cancelarModificarResolutivo);			
			}
		  }
		});	 	 			
	}
	
	
	function cancelarModificarResolutivo(){
  		$("#divAgregarResolutivo").dialog("close");
	}



	/*
	*Funcion para eliminar un registro de resolutivo
	*/
	function eliminarRegistroResolutivo(){
		
		var gr = jQuery("#gridAudienciasResolutivosPJENS").jqGrid('getGridParam','selrow');
		var datosResolutivos="";
		
		datosResolutivos += 'idResolutivo=' + gr;
		$.ajax({
			url: '<%= request.getContextPath()%>/eliminarResolutivosPJENS.do',
			type: 'POST', data: datosResolutivos, async: false, dataType: 'xml',
			success: function(xml){$("#gridAudienciasResolutivosPJENS").trigger("reloadGrid");}				
		});	
	}

	
	/*
	*Funcion que carga la hora actual
	*/
	function cargaHoraCaptura(){
		
	  	$.ajax({
	  		type: 'POST',
	  	    url: '<%= request.getContextPath()%>/ConsultarHoraCaptura.do',
	  	    data: '',
	  	    dataType: 'xml',
	  	    success: function(xml){
		  	    var hmsCaptura = $(xml).find('horaActual').text();
				var hCaptura = hmsCaptura.split(":")[0];
				var mCaptura = hmsCaptura.split(":")[1];
		  	    
	  			$('#tempVideo').val(hCaptura+":"+mCaptura);
	  		}
		});
	}

	
	/*
	*Funcion que abre el tipo de ley seleccionada
	*/
	function abreCodigosLeyes(tipo) {

		 tipoLey = tipo;

		 jQuery("#gridLeyesCodigosPJENS").jqGrid('setGridParam', {postData: {tipoLey: tipoLey}});
		 $("#gridLeyesCodigosPJENS").trigger("reloadGrid"); 

		jQuery("#gridLeyesCodigosPJENS").jqGrid({ 
			url:'<%= request.getContextPath()%>/consultarLeyesCodigos.do', 
			datatype: "xml", 
			colNames:['Consulta de Leyes y Codigos'], 
			colModel:[ 	{name:'ley',index:'ley', width:500,align:"center"} ],
			pager: jQuery('#pager10'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			height: 220,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			onCellSelect: function(id,fila,contenido){
				abrirPDF(id,fila,contenido);
				}
			}).navGrid('#pager10',{edit:false,add:false,del:false});
		
	}

	/*
	*Funcion que da un submit a la forma para mostrar las leyes
	*/
	function abrirPDF(idLey,fila,contenido){
		
		document.frmDoc.idLey.value = idLey;
		document.frmDoc.nombreArchivo.value = contenido;
		document.frmDoc.submit();
	}

	function Resolutivo(temporizadorVideo, resolutivo){
		this.temporizadorVideo = temporizadorVideo;
		this.resolutivo = resolutivo;			
	} 


//**********************************************************FUNCIONALIDAD DE LA CEJA PROGRAMAR AUDIENCIA**********************************************************\ 	

	//FUNCIONALIDAD COMUN A LAS 3 SUB CEJAS
	
	/*
	*Funcionalidad para obtener el id de la audiencia siguiente
	*/
	function recuperaIdSiguienteAudiencia(){
		
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/recuperaIdSiguienteAudiencia.do',
			data: 'idAudiencia='+ idAudiencia,
			async: false,
			dataType: 'xml',
			success: function(xml){
				idAudienciaSiguiente=$(xml).find('long').text();
			}
		});
	}
	

	/*
	*Funcion que oculta las cejas de involucrados y objetos
	*de la ceja programar audiencia
	*/
	function ocultaSubCejasProgAudiencia(){
		$("#tabschild").tabs("option", "disabled", [1,2,3,4,5,6,7,8]);					
	  }

	/*
	*Habilita el tab de Involucrados y el teb de objetos
	*/	
	function muestraSubCejasProgAudiencia(){
		$('#tabschild').tabs('enable', 1);
		$('#tabschild').tabs('enable', 2);
		$('#tabschild').tabs('enable', 3);
		$('#tabschild').tabs('enable', 4);
		$('#tabschild').tabs('enable', 5);
		$('#tabschild').tabs('enable', 6);
		$('#tabschild').tabs('enable', 7);
		$('#tabschild').tabs('enable', 8);
	}

	/**
	*Funcion que recarga el grid con la funcionalidad de la agenda
	*/
	function cargaGridAgenda(){
		
		jQuery("#gridAgendaPJENA").jqGrid({ 
			url:'<%=request.getContextPath()%>/calendarioActual.do', 
			datatype: "xml", 
			colNames:['Lu','Ma','Mi','Ju','Vi','Sa','Do'], 
			colModel:[  {name:'lunes',index:'lunes', width:40, align:'center'},						
			           	{name:'martes',index:'martes', width:40, align:'center'}, 
			           	{name:'miercoles',index:'miercoles', width:40,align:'center'}, 
			           	{name:'jueves',index:'jueves', width:40, align:'center'}, 
			           	{name:'viernes',index:'viernes', width:40, align:'center'}, 
			           	{name:'sabado',index:'sabado', width:40, align:'center'}, 
			           	{name:'domingo',index:'domingo', width:40, align:'center'}, 
										
					],
			rowNum:10,
			rowList:[10,20,30],
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			cellEdit: true,
			scrollOffset:0,
			onSelectCell: function(idRow,dia,fecha) {
				seleccionCelda(idRow,dia,fecha);
				deshabilitarHabilitarComponentes("seleccionFecha");
					},	
			loadComplete: function(){
				//Cambia la bandera para avanzar de mes
				banderaCambio=0;
			} 
		});
	}

	/*
	*Funcion que carga los combos de tipo de solicitudes de audiencia que se pueden realizar
	*excepto el tipo de audiencia actual
	*/
	function cargaComboTipoAudiencia(tipoAudiencia) {

		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoTipoAudienciaValidacion.do',
			data: 'tipoAudiencia='+tipoAudiencia,
			async: false,
			dataType: 'xml',
			success: function(xml){
				$('#tipoAudienciaProgramarAudiencia').empty();
				$('#tipoAudienciaProgramarAudiencia').append('<option value="0">- Seleccione -</option>');
				var tipoAud = "";
				$(xml).find('institucion').each(function(){
					$('#tipoAudienciaProgramarAudiencia').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}


	//COMIENZA FUNCIONALIDAD DEL CALENDARIO
	
	/**
	*Funcion que obtiene el valor del desplazamiento para los meses "atras" o "adelante",
	*pasa el valor al action para que este ejecute el movimiento y recarga el grid de 
	*agenda 
	*/
	function atrasAdelanteMes(movimiento){
		recorridoMeses(movimiento);
		jQuery("#gridAgendaPJENA").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/calendarioActual.do?mov='+ movimiento +'',datatype: "xml" });
		$("#gridAgendaPJENA").trigger("reloadGrid");
	}

	/**
	*Funcion que recorre el carrusel de los mese y anio si la bandera de cambio se encuentra encendida
	*los meses no se recorran, hasta que el grid sea refrescado y baje la bandera 
	*/
	function recorridoMeses(movimiento){
		
		if(banderaCambio==0){
			if(movimiento=="atras"){
				
				if(mesActual == 0){
					mesActual=11;
					if(anioActual == 0)
						anioActual=3000;
					else
						anioActual--;
				}
					
				else
					mesActual--;
			}
			else{
				if(mesActual == 11){
					mesActual=0;
					anioActual++;
				}
				else
					mesActual++;
			}
			var mesSeleccionado = meses[mesActual];

			//setea los valor de mes y anio a la caja de texto
			$('#mes').val(mesSeleccionado);
			$('#anio').val(anioActual);
			//levanta la bandera de cambio
			banderaCambio=1;
		}	
	}

	/*
	*Verifica el tipo de audiencia y en el caso de ser de juicio oral
	*ordena se capturen el numero de jueces correspondientes al tipo
	*/
	function verificarTipoAudiencia(automatico){

		var tipoDeAudienciaSol = $("#tipoAudienciaProgramarAudiencia").val();
		//deshabilitamos el combo de tipo de audiencia
		$("#tipoAudienciaProgramarAudiencia").attr('disabled',true);
		
		//Si pulso automaticamente
		if(automatico == true){
			//jQuery("#gridSolicitudDeAudienciaJuecesPJENA").setGridParam({multiselect:false}).hideCol('cb');
			$("#gridSolicitudDeAudienciaJuecesPJENA").unbind('click');
			$("#divGridSolicitudDeAudienciaJuecesPJENA").hide(); 
			multiselect=0;
		}
		//Si pulso manualmente
		else{
	
			if(tipoDeAudienciaSol=="<%=TipoAudiencia.JUICIO_ORAL.getValorId()%>"){
			 	if($("#juezSustituto").is(':checked')){
			 		alertDinamico("Por favor seleccione 4 jueces del listado");
					multiselect=4;
				}else{
					alertDinamico("Por favor seleccione 3 jueces del listado");
				 	multiselect=3;
				}
			}else{
				alertDinamico("Por favor seleccione un juez del listado");
				multiselect=1;
			}
		}
		
	}


	//COMIENZA FUNCIONALIDAD DEL CALENDARIO //
	

	/**
	*Funcion que obtiene el valor de la celda seleccionada en la agenda
	*/
	function seleccionCelda(idRow,dia,fecha){
		
		//verifica si la sub string inhabil se encuentra en la cadena fecha,
		//de NO ser asi devuelve -1
		if(fecha.indexOf("inhabil")==-1){
			var fechaPos1=fecha.indexOf(">", 0);
			var fechaPos2=fecha.indexOf("<", fechaPos1);
			fechaReal="";
			fechaReal=fecha.substring(fechaPos1+1, fechaPos2);
			muestraFechaSeleccionada(fechaReal);
			controlSalas(fechaReal);
			//$("#gridAgendaPJENA").jqGrid('setCell',"1","lunes","",{'font-weight': 'bold',color: 'white','text-align':'center','background-color':'red'});			
		}
		else{
			alertDinamico("Seleccione un d&iacute;a h&aacute;bil");
		}
	}

	/**
	*funcion que muestra en el txtField la fecha seleccionada en el calendario
	*/
	function muestraFechaSeleccionada(){

		var contadorMeses = 0;
		var mesDisponible = $('#mes').val();
		var anioDisponible = $('#anio').val(); 
				
		while(mesDisponible != meses[contadorMeses]){
			contadorMeses++;	
		}

		var diaDisponible;
		if(fechaReal < 10){
			diaDisponible= "0"+fechaReal;  
		}
		else{
			diaDisponible=fechaReal;
		}
		contadorMeses++;
		if((contadorMeses) < 10){
			//$('#fechaSeleccionadaAudiencia').val(fechaReal+"/"+"0"+contadorMeses+"/"+anioDisponible);
			contadorMeses = "0"+contadorMeses;
		}
		$('#fechaSeleccionadaAudiencia').val(diaDisponible+"/"+contadorMeses+"/"+anioDisponible);
	}
		
	var numeroDeSalas=0;
	
	/**
	*Funcion que controla la agenda de las salas
	*/
	function controlSalas(fechaReal){
		
		if(primeraConsulta=="true"){
			numeroDeSalas=obtenerNumeroDeSalas();
			insertaTablas(numeroDeSalas);
			consultaDisponibilidadSalasPorDia(fechaReal);
			gridHorarios();			
			dibujaGrids(numeroDeSalas,fechaReal);			
			primeraConsulta="false";
		}
		else{
			consultaDisponibilidadSalasPorDia(fechaReal);
			
			for (i=0;i<numeroDeSalas;i++){
				salaId = identiSala[i];
				jQuery("#gridSalasPJENA"+salaId).jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDisponibilidadDeUnaSalaPorDia.do?posSala='+salaId+'',datatype: "xml" });
				$("#gridSalasPJENA"+salaId).trigger("reloadGrid");
			}
		}
	}

		
	/**
	*Funcion para obtener el numero de salas disponibles
	*/
	function obtenerNumeroDeSalas(){

		//numeroDeSalas=0;
		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarNumeroDeSalas.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ListaSalas').find('Sala').each(function(){
					identiSala[numeroDeSalas] = $(this).find('salaAudienciaId').first().text();
					nombreSalas[numeroDeSalas] = $(this).find('nombreSala').first().text();
					numeroDeSalas++;
				});
			}
		});
		return numeroDeSalas;
	}
		
	/**
	*Funcion que inserta las tablas en HTML,donde
	*se dibujaran los grids de las salas
	*/
	function insertaTablas(numeroDeSalas){

		var indice;
		
		$('#gridsTd').append('<tr>');
		$('#gridsTd').append('<td><table width="100%" id="gridHorarios" border="0"></table></td>');
		for (indice=0;indice<numeroDeSalas;indice++){
			salaId = identiSala[indice];
			$('#gridsTd').append('<td><table width="100%" id="gridSalasPJENA'+salaId+'" border="0"></table></td>');
		}
		$('#gridsTd').append('</tr>');
	}


	/**
	*Funcion que consulta la disponibilidad de las salas por dia
	*/
	function consultaDisponibilidadSalasPorDia(fechaReal){

		var mesDisponible = $('#mes').val();
		var anioDisponible = $('#anio').val();
		
		//Recuperamos la informacion de las salas
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/consultarDisponibilidadDeSalasPorDia.do?diaDisp='+fechaReal+'&mesDisp='+mesDisponible+'&anioDisp='+anioDisponible+'',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
			}
		});	
	}

		
	/**
	*Funcion que genera el grid con la lista de horarios
	*/
	function gridHorarios(){
		
		jQuery("#gridHorarios").jqGrid({
			datatype: "xml",
			colNames:['Horario'], 
			colModel:[{name:'hora',index:'hora', width:100, align:"center"},
					],
			pager: jQuery('#pagerHora'),
			rowNum:28,
			rowList:[10,20,30],
			autowidth: false,
			height:645,
			scrollOffset:0,
			sortname: 'hora'
		}).navGrid('#pagerHora',{edit:false,add:false,del:false});
		
		var mydata = [ 
						{hora:"09:00-09:30"},
						{hora:"09:30-10:00"},
						{hora:"10:00-10:30"},
						{hora:"10:30-11:00"},
						{hora:"11:00-11:30"},
						{hora:"11:30-12:00"},
						{hora:"12:00-12:30"},
						{hora:"12:30-13:00"},
						{hora:"13:00-13:30"},
						{hora:"13:30-14:00"},
						{hora:"14:00-14:30"},
						{hora:"14:30-15:00"},
						{hora:"15:00-15:30"},
						{hora:"15:30-16:00"},
						{hora:"16:00-16:30"},
						{hora:"16:30-17:00"},
						{hora:"17:00-17:30"},
						{hora:"17:30-18:00"},
						{hora:"18:00-18:30"},
						{hora:"18:30-19:00"},
						{hora:"19:00-19:30"},
						{hora:"19:30-20:00"},
						{hora:"20:00-20:30"},
						{hora:"20:30-21:00"},
						{hora:"21:00-21:30"},
						{hora:"21:30-22:00"},
						{hora:"22:00-22:30"},
						{hora:"22:30-23:00"}
						]; 
		for(var i=0;i<=mydata.length;i++)
			jQuery("#gridHorarios").jqGrid('addRowData',i+1,mydata[i]);

		//Quita el espacio entre grids
		$("#gbox_gridHorarios").css('width','87px');

	}


	/**
	*Funcion que dibuja los grids dentro de las tablas, por cada grid, se ejecuta una peticion
	*,para llenar cada grid se obtienen los valores de la consulta de disponibilidaDeSalasPorDia
	* y la peticion:/consultarDisponibilidadDeUnaSalaPorDia.do?posSala='+numSala+'' obtiene le 
	* informacion para cada sala en especifico
	*/	
	function dibujaGrids(numeroDeSalas,fechaReal){

		var numSala=0;
		
		//Dibujamos tantos grids como numero de salas
		for (numSala=0;numSala<numeroDeSalas;numSala++){

			salaId = identiSala[numSala];
			
			jQuery("#gridSalasPJENA"+salaId).jqGrid({
				url:'<%=request.getContextPath()%>/consultarDisponibilidadDeUnaSalaPorDia.do?posSala='+salaId+'',
				datatype: "xml",
				colNames:[nombreSalas[numSala]], 
				colModel:[
							{name:'sala',index:'sala', width:100, align:"right"},
						],   
				pager: jQuery('#pager'),
				rowNum:28,
				rowList:[10,20,30],
				autowidth: false,
				height:645,
				sortname: 'sala',
				scrollOffset:0,
				cellEdit: true,
				onSelectCell: function(idRow) {
							programaHorarioSala(idRow,fechaReal);
						},	
				loadComplete: function(){
								
					$("#ocupado1").find("td").css('border','1px solid black');
					$("#ocupado1").find("td").css('padding','0 0 0 0');
					$("#ocupado1").find("td").css('background-color','#09F');
					
				
					
					$("#ocupadoInicio").find("td").css('border-bottom','1px solid #09F');
					$("#ocupadoInicio").find("td").css('border-left','1px solid black');
					$("#ocupadoInicio").find("td").css('border-right','1px solid black');
					$("#ocupadoInicio").find("td").css('border-top','1px solid black');
					$("#ocupadoInicio").find("td").css('padding','0 0 0 0');
					$("#ocupadoInicio").find("td").css('background-color','#09F');
				
					
					$("#ocupadoIntermedio").find("td").css('border-bottom','1px solid #09F');
					$("#ocupadoIntermedio").find("td").css('border-left','1px solid black');
					$("#ocupadoIntermedio").find("td").css('border-right','1px solid black');
					$("#ocupadoIntermedio").find("td").css('border-top','1px solid #09F');
					$("#ocupadoIntermedio").find("td").css('padding','0 0 0 0');
					$("#ocupadoIntermedio").find("td").css('background-color','#09F');
					
					
					$("#ocupadoUltimo").find("td").css('border-bottom','1px solid black');
					$("#ocupadoUltimo").find("td").css('border-left','1px solid black');
					$("#ocupadoUltimo").find("td").css('border-right','1px solid black');
					$("#ocupadoUltimo").find("td").css('border-top','1px solid #09F');
					$("#ocupadoUltimo").find("td").css('padding','0 0 0 0');
					$("#ocupadoUltimo").find("td").css('background-color','#09F');				
				}				
			}).navGrid('#pager',{edit:false,add:false,del:false});

			//Quita el espacio entre grids
			$("#gbox_gridSalasPJENA"+salaId).css('width','87px');
		}
	}
	//TERMINA FUNCIONALIDAD PARA LOS GIRDS DINAMICOS DE SALAS


	//COMIENZA FUNCIONES PARA ASIGNAR AUDIENCIA A UNA SALA
	/**
	*Funcion que controla el horario y la sala seleccionada, descompone la string
	*para obtener los parametros del id de la sala, la hora de inicio de cada espacio
	*y el numero de medias hrs. disponibles para ese espacio libre
	*/
	
	function programaHorarioSala(idRow,fechaReal){

		
		//verifica si la sub string ocupado se encuentra en la cadena fecha
		//de NO ser asi, devulve -1
		if(idRow.indexOf("ocupado")==-1){
			
			//id de la sala
			var idSalaPos1=idRow.indexOf("+", 0);
			idSalaSeleccionada=idRow.substring(0,idSalaPos1);		

			//hora de inicio del espacio
			var horaInicioPos1=idRow.indexOf("+",0);
			var horaInicioPos2=idRow.indexOf("/",horaInicioPos1);
			var horaInicio=idRow.substring(horaInicioPos1+1,horaInicioPos2);
			

			//tamaño en medias hrs. del espacio disponible
			var espacioPos1=idRow.indexOf("/",0);
			var espacioPos2=idRow.indexOf("*",espacioPos2);
			var espacioDisponible=idRow.substring(espacioPos1+1,espacioPos2);
			

			//hora de inicio de la celda seleccionada
			var horaSeleccionadaPos1=idRow.indexOf("*",0);
			var horaSeleccionadaPos2=idRow.indexOf(":",horaSeleccionadaPos1);
			horaSeleccionada=idRow.substring(horaSeleccionadaPos1+1,horaSeleccionadaPos2);
			

			//Minuto de inicio de la celda seleccionada
			var minutoSeleccionadoPos1=idRow.indexOf(":",0);
			var minutoSeleccionadoPos2=idRow.indexOf("#",minutoSeleccionadoPos1);
			minutoSeleccionado=idRow.substring(minutoSeleccionadoPos1+1,minutoSeleccionadoPos2);
			
			
			var disponibilidad=parseInt("espacioDisponible");
			duracionEstimadaAudiencia = $("#duracionEstimadaProgramarAudiencia option:selected").val();
			
			
			if(duracionEstimadaAudiencia > 0 ){
				if(	validaHoraAudienciaHoraActual( horaSeleccionada, minutoSeleccionado)){
					if((duracionEstimadaAudiencia/30) <= espacioDisponible){
						muestraHoraSalaSeleccionada();		
						deshabilitarHabilitarComponentes("horarioSala");
					}
					else{
						alertDinamico("La duración estimada es mayor que el tiempo disponible para esta sala.\rIntente con otra sala");	
					}
				}
				else{
					alertDinamico("Esta seleccionando una hora no disponible");
				}
			}
			else{
				
				alertDinamico("Seleccione una duración estimada para la audiencia");
			}
				
		}
		else{
			alertDinamico("La sala se encuentra reservada en ese horario");
		}
	}


	/**
	*funcion que muestra en el txtField correspondiente el id de la sala seleccionada
	*y la hora de inicio seleccionada
	*/
	function muestraHoraSalaSeleccionada(){

		$('#txtSalaSeleccionada').val(idSalaSeleccionada);
		$('#txtHoraInicioSeleccionada').val(horaSeleccionada+":"+minutoSeleccionado);
	}

		
	/**
	*Funcion que sonsulta la lista de jueces disponibles en base a la 
	*hora y la fecha de la audiencia
	*/
	function controlJueces(automatico){

		var mesDisponible = $('#mes').val();
		var anioDisponible = $('#anio').val();
		duracionEstimadaAudiencia = $("#duracionEstimadaProgramarAudiencia option:selected").val();
		juezSustituto =  $("#juezSustituto").is(':checked');
		
		if($("#tipoAudienciaProgramarAudiencia option:selected").val() <= 0){
			
			customAlert('<bean:message key="audiencia.programar.validar.tipoAudiencia"/>','<bean:message key="aviso"/>');
			return false;
		}
		
		if(duracionEstimadaAudiencia == "" || duracionEstimadaAudiencia <= 0){
			customAlert('<bean:message key="audiencia.programar.validar.duracion"/>','<bean:message key="aviso"/>');
			return false;
		}				
		
		if(primeraConsultaJueces=="true"){
			
			//Se llena el gird de jueces
			jQuery("#gridSolicitudDeAudienciaJuecesPJENA").jqGrid({
				url:'<%= request.getContextPath()%>/consultarDisponibilidadJueces.do?audienciaId='+idAudienciaSiguiente+'&diaDisp='+fechaReal+'&mesDisp='+mesDisponible+'&anioDisp='+anioDisponible+'&horaSeleccionada='+horaSeleccionada+'&minutoSeleccionado='+minutoSeleccionado+'&duracionEstimada='+duracionEstimadaAudiencia+'&automatico='+automatico+'&juezSustituto='+juezSustituto+'&tipoAudiencia='+tipoAudiencia + '&tipoDeAudienciaSolicitada=' + $("#tipoAudienciaProgramarAudiencia").val(), 
				datatype: "xml", 
				colNames:['Nombre','Carga de trabajo','Paridad',], 
				colModel:[ 	{name:'nombre',index:'nombre', width:100, align:"center"},
				           	{name:'carga',index:'carga', width:150, align:"center"},  
							{name:'paridad',index:'paridad', width:50, align:"center"},
							
						],
				pager:jQuery("#divGridSolicitudDeAudienciaJuecesPJENA"),
				rowNum:5,
				rowList:[5,10,15],
				autowidth: false,
				sortname: 'carga',
				viewrecords: true,
				sortorder: "desc",
				multiselect: true,
				loadComplete: function(){ 
					if(automatico== true){
						//Deshabilita el multi select
						jQuery("#gridSolicitudDeAudienciaJuecesPJENA").setGridParam({multiselect:false}).hideCol('cb');
					}else{
						if(juezOmision==false){
							seleccionarJuezPorOmision();
						}
					}		
				},
				gridComplete: function () {
					seleccionarItems($(this));
				},
				onPaging: function (a) {
					guardarItemsSeleccionados($(this)); 
				},
				onSortCol: function(){
					eliminarItemsSeleccionados($(this));
				}
			}).navGrid('#divGridSolicitudDeAudienciaJuecesPJENA',{edit:false,add:false,del:false});

			primeraConsultaJueces="false";
			deshabilitarHabilitarComponentes("juez");
			verificarTipoAudiencia(automatico);
		}
		else{
			jQuery("#gridSolicitudDeAudienciaJuecesPJENA").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarDisponibilidadJueces.do?audienciaId='+idAudienciaSiguiente+'&diaDisp='+fechaReal+'&mesDisp='+mesDisponible+'&anioDisp='+anioDisponible+'&horaSeleccionada='+horaSeleccionada+'&minutoSeleccionado='+minutoSeleccionado+'&duracionEstimada='+duracionEstimada+'&automatico='+automatico+'&juezSustituto='+juezSustituto+'&tipoAudiencia='+tipoAudiencia + '&tipoDeAudienciaSolicitada=' + $("#tipoAudienciaProgramarAudiencia").val(),datatype: "xml" });
			$("#gridSolicitudDeAudienciaJuecesPJENA").trigger("reloadGrid");
			deshabilitarHabilitarComponentes("juez");
			verificarTipoAudiencia(automatico);
		}
		
	}
	

	/*
	*Funcion para seleccionar en el grid multiselect
	*/
	function  seleccionarItems(grid){
			
		var currentPage = grid.getGridParam('page').toString();
	
		//retrieve any previously stored rows for this page and re-select them
		var retrieveSelectedRows = grid.data(currentPage);
		
		if (retrieveSelectedRows) {
	         $.each(retrieveSelectedRows, function (index, value) {
	             grid.setSelection(value, false);
			});
		}
	}
			
			
	/*
	*Funcion para guardar en el grid multiselect
	*/
	function guardarItemsSeleccionados(grid) {
		
		var pagerId = grid.getGridParam('pager').toString();
		
		if(pagerId.indexOf("#") != -1){
			pagerId = pagerId.substring(1, pagerId.length);
		}
		// ger paper id like "pager" 
		var pageValue = $('input.ui-pg-input', "#pg_" + $.jgrid.jqID(pagerId)).val(); 
		var saveSelectedRows = grid.getGridParam('selarrrow'); 
		//Store any selected rows 
		grid.data(pageValue.toString(), saveSelectedRows); 
	}


	/*
	*Funcion para borrar la seleccion en el grid multiselect
	*/		
	function eliminarItemsSeleccionados(grid){
		
		guardarItemsSeleccionados(grid);
		grid.jqGrid('resetSelection');
	    var retrieveSelectedRows = grid.data();       
	    if (retrieveSelectedRows) {
	        $.each(retrieveSelectedRows, function (index, value) {
	            $.each(value, function (sub_index, sub_value) {
	                if(typeof(sub_value)=='string'){
	                	grid.data(index, "");
	                }
	            });
	        });
	    }
	    
	}

	/*
	*Funcion para obtener los ids seleccionados del grid multiselect
	*/
	function obtenerSeleccionados(grid){
		
		guardarItemsSeleccionados(grid);
	
	    var retrieveSelectedRows = grid.data();       
	    var arr_values = new Array();
	
	    if (retrieveSelectedRows) {
	        $.each(retrieveSelectedRows, function (index, value) {
	            $.each(value, function (index, sub_value) {
	                if(typeof(sub_value)=='string')
	                arr_values.push(sub_value);
	            });
	        });
	    }
	
	    return arr_values;
	}			

	/*
	*Funcion para obtener el id de la fila seleccionada
	*/			
	function obtenerIdFilaSeleccionada(grid){
		var id = grid.jqGrid('getGridParam','selrow');
		if(id) {
			return id;
		} else {
			return false;
		}
	}
	
	
	/*
	*Funcion para seleccionar el juez loggeado por omision
	*/
	function seleccionarJuezPorOmision(){  
		
		var arrayIDs = new Array() ;			
		var arrayIDs = jQuery("#gridSolicitudDeAudienciaJuecesPJENA").getDataIDs();
		var claveFuncionario = cargaDatosDelUsuario();
		
		for (i=0;i<arrayIDs.length;i++){
			if(claveFuncionario == arrayIDs[i]){
				juezOmision = true;
				jQuery("#gridSolicitudDeAudienciaJuecesPJENA").jqGrid('setSelection',arrayIDs[i]);
			}											
		}
	}
	
	/*
	*Funcion que dispara el Action para consultar los datos de usuario logeado
	*/
    function cargaDatosDelUsuario(){  
        
    	var claveFuncionario;
    	    
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/consultarDatosUsuario.do',
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
				var errorCode=$(xml).find('response').find('code').text();	
							
				if(parseInt(errorCode)==0){	
					claveFuncionario = $(xml).find('usuarioDTO').find('funcionario').find('claveFuncionario').first().text();
    			}
				else{
					//Mostrar mensaje de error
				}
    		}
    	});
    	return claveFuncionario;
    }

   /*
	*Funcion para indicar al usuario si no se selecciono el mismo, por omision
	*/
    function validarJuezPorOmision(){ 

    	//Si la asignacion de jueces fue automatica
    	if(multiselect == 0){
    		guardarAudiencia();
		}
    	//Si la asignacion de jueces fue manual
		else{
			var arrayIDsSelected = new Array() ;
			var arrayIDsSelected = obtenerSeleccionados($("#gridSolicitudDeAudienciaJuecesPJENA"));						
			//var arrayIDsSelected =  jQuery("#gridSolicitudDeAudienciaJuecesPJENA").jqGrid('getGridParam','selarrrow');
			var claveFuncionario = cargaDatosDelUsuario();
			var banderaConfirm = false;
			
			for (i=0;i<arrayIDsSelected.length;i++){
				if(claveFuncionario == arrayIDsSelected[i]){
					banderaConfirm = true;
					break;
				}											
			}

			if(banderaConfirm == false){
				customConfirm("Usted no se ha seleccionado como Juez para la audiencia, ¿Desea continuar?","",guardarAudiencia);
			}
			else{
				guardarAudiencia();
			}	
		}
	}

	//TERMINAN FUNCIONES PARA ASIGNAR AUDIENCIA A UNA SALA
	
	///COMIENZA FUNCIONALIDAD SALA TEMPORAL
	function asignarSalaTemporal(recuperaDatosAsignacionTemporal){
	
		datosSalaTemporal = recuperaDatosAsignacionTemporal;
		salaTemporal = "true";
		
		hrSelecTemp = datosSalaTemporal.split("=")[4];
		var hrSeleccionadaPos1=hrSelecTemp.indexOf("&", 0);
		hora=hrSelecTemp.substring(0,hrSeleccionadaPos1);		
		minuto = datosSalaTemporal.split("=")[5];

		horaSeleccionada="";
		horaSeleccionada= hora;
		minutoSeleccionado="";
		minutoSeleccionado= minuto;
		cerrarVentanaAsignacionTemporal();
		
		$('#txtSalaSeleccionada').val(datosSalaTemporal.split("=")[2].split("&")[0]);
		$('#txtHoraInicioSeleccionada').val(horaSeleccionada+":"+minutoSeleccionado);
		
		
		deshabilitarHabilitarComponentes("designarSala");
		$('#duracionEstimadaProgramarAudiencia').multiselect('enable');
		
	}

	
	/*
	*Funcion que abre la ventana para asignar una sala temporal
	*/
	function mostrarAsignarSalaTemporalPJENA(){
		$.newWindow({id:"iframewindowAsignarSalaTemporalAudiencia" , statusBar: true, posx:250,posy:40,width:650,height:200,title:"Designaci&oacute;n de Sala Temporal de Audiencia", type:"iframe"});
	    $.updateWindowContent("iframewindowAsignarSalaTemporalAudiencia" ,'<iframe src="<%= request.getContextPath() %>/asignarSalaTemporalPJENA.do?idEvento=idAudienciaSiguiente" width="650" height="200" />');
	    banderaSalaTemp=true;
	    $('#btnDesignar').attr('disabled',true);
	}

	
	function cerrarVentanaAsignacionTemporal(){
		$.closeWindow('iframewindowAsignarSalaTemporalAudiencia');
	}
	//TERMINA FUNCIONALIDAD SALA TEMPORAL
	

	/**
	*Funcion para el guardado de la audiencia
	*/
	function guardarAudiencia(){

		//bloquear pantalla
		bloquearPantalla(true, "Programando Audiencia");  

		if(idAudienciaSiguiente == 0){
			recuperaIdSiguienteAudiencia();
		}

		var parametrosGuardado='';

		parametrosGuardado += 'numCarpetaEjecucion='+ $("#numExpPJENS").val();
		parametrosGuardado += '&idAudiencia='+ idAudienciaSiguiente;
		parametrosGuardado += '&anioEvento=' + $('#anio').val();
		parametrosGuardado += '&mesEvento=' + $('#mes').val();
		parametrosGuardado += '&diaEvento=' + fechaReal;
		parametrosGuardado += '&horaEvento=' + horaSeleccionada;
		parametrosGuardado += '&minutoEvento=' + minutoSeleccionado;
		parametrosGuardado += '&duracionEstimada=' + duracionEstimadaAudiencia;
		parametrosGuardado += '&idSala=' + idSalaSeleccionada;
		parametrosGuardado += '&salaTemporal=' + salaTemporal;
		parametrosGuardado += '&tipoDeAudienciaSeleccionada='+tipoDeAudienciaSelecc;
		//Si la asignacion de jueces fue automatica
		if(multiselect == 0){
			parametrosGuardado += '&idJueces=' + jQuery("#gridSolicitudDeAudienciaJuecesPJENA").getDataIDs();
		}
		else{
			parametrosGuardado += '&idJueces=' + obtenerSeleccionados($("#gridSolicitudDeAudienciaJuecesPJENA"));
			//parametrosGuardado += '&idJueces=' + jQuery("#gridSolicitudDeAudienciaJuecesPJENA").jqGrid('getGridParam','selarrrow');	
		}
		parametrosGuardado += '&idInvolucradosPA=' + jQuery("#gridsImputadosAudiencia").jqGrid('getGridParam','selarrrow');
		parametrosGuardado += '&'+datosSalaTemporal;

		parametrosGuardado += '&numeroExpediente='+numeroExpediente;

		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/guardarAudienciaProgramaNueva.do',
			data: parametrosGuardado,
			dataType: 'xml',
			success: function(xml){	

					var mensaje = $(xml).find('body').find('horaActual').text();

					if(mensaje == "fail"){
						alertDinamico("La audiencia ya fue programada anteriormente");
						/*
						*recargamos el grid de audiencias para que la solicitud de audiencia que se abrío ya no 
						*aparesca en la bandeja
						*/


						parent.recargaGridAudienciasDelDia();
						
					}
					else if(mensaje == "salaOcupada"){
						alertDinamico("Otro usuario acaba de programar una audiencia en esta sala y horario");
						try{


							parent.recargaGridAudienciasDelDia();
							setTimeout("recargaVisor()",3000);
						}catch(e){}
					}
					else{
						alertDinamico("La programación de la audiencia se realizó de manera correcta");
						//Refrescamos el grid de las salas
						controlSalas(fechaReal);	
						/*
						*recargamos el grid de audiencias para que la audiencia que se acaba de
						*programar, no se muestre mas 
						*/
						parent.recargaGridAudienciasDelDia();
						
						//deshabilitamos el boton de guardado
						$('#btnGuardarAudiencia').attr('disabled',true);
						//Refrescamos el grid de las salas
						controlSalas(fechaReal);

						//Mostramos las cejas para capturar 
						muestraSubCejasProgAudiencia();
						//Carga Grid denunciante y victima
						cargaGridVictima1();
						cargaGridDenunciante1();

						//Carga gird de imputados que se arrastraron a la audiencia
						cargaGridImputado1();
						//recarga el grid de imputados
						cargaImputadosProgramarAudiencia(idAudiencia);
						//Recarga grid de involucrados a la siguiente audiencia
						cargaGridPolicia1();
						cargaGridPerito1();
						cargaGridTestigo1();
						//////////////////////////
						cargaGridFiscal1();
						cargaGridDefensor1();
					}
					//Desbloquear pantalla
					desbloquearPantalla();								
			}
		});
	}
	

	 /**
	*Funcion para validar el guardado de la audiencia
	*/
	function validaGuardarAudiencia(){
		var valorIdes = new Array(); 
		//var valor = jQuery("#gridSolicitudDeAudienciaJuecesPJENA").jqGrid('getGridParam','selarrrow').length;
		 valorIdes = obtenerSeleccionados($("#gridSolicitudDeAudienciaJuecesPJENA"));
		var valor = valorIdes.length;

		tipoDeAudienciaSelecc=$("#tipoAudienciaProgramarAudiencia option:selected").val();
		//Se verifica que se seleccione un tipo de audiencia
		if(tipoDeAudienciaSelecc != "0"){
			if(valor == multiselect){

				var lstImputados = new Array();
				lstImputados = jQuery("#gridsImputadosAudiencia").jqGrid('getGridParam','selarrrow'); 
				
				if(lstImputados.length > 0){
					validarJuezPorOmision();		
				}
				else{
					alertDinamico("Seleccione almenos un imputado, para la siguiente audiencia");
				}
							
			}else{
				if(multiselect==1){
					alertDinamico('Seleccione &uacute;nicamente un juez del listado');
				}
				else{
					alertDinamico('Seleccione &uacute;nicamente '+multiselect+' jueces del listado');
				}
			}
		}
		else{
			alertDinamico('Seleccione un tipo de audiencia a programar');
		}
	}
	
	/*Funcion que permite refrescar la ventana y redirigirla de nuevo*/
	function recargaVisor(){
			document.frmRecargaVisor.idAudiencia.value = idAudiencia;
			document.frmRecargaVisor.idVisor.value = 2;
			document.frmRecargaVisor.idAudienciaSiguiente.value = idAudienciaSiguiente;
			document.frmRecargaVisor.submit();
	}
	
	
	
	/*****FUNCIONALIDAD PARA LA SUB CEJA INVOLUCRADOS DE LA SIGUIENTE AUDIENCIA*/
	
	
	var banderaGridImputado1= true;
	/*
	*Funcion que carga el grid de Impitados para la siguiente audiencia
	*/
	function cargaGridImputado1(){

		tipoConsulta=0;

		if(banderaGridImputado1 == true){

			jQuery("#gridImputado1").jqGrid({

				url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idAudienciaSiguiente+"&tipoConsulta="+tipoConsulta,						
				datatype: "xml",
				colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Parte','Presente'], 
				colModel:[ 	{name:'Nombre',index:'Nombre', width:180,align:'center'}, 
							{name:'APaterno',index:'APaterno', width:180,align:'center'}, 
							{name:'AMaterno',index:'AMaterno', width:180,align:'center'},
							{name:'Parte',index:'Parte', width:100,align:'center',hidden:true},
							{name:'Presente',index:'Presente', width:150,align:'center',hidden:true}, 
						],
				autowidth: false,
				width:924,
				autoheight: true,
				pager: jQuery('#paginadorGridImputado1'), 
				sortname: 'Nombre', 
				rowNum:10,
				rowList:[10,20,30],
				gridview: false, 
				viewrecords: true, 
				sortorder: "desc",			
				toolbar: [true,"top"],
				caption:"Imputado"
			}).navGrid('#paginadorGridImputado1',{edit:false,add:false,del:false});
			
			
			banderaGridImputado1 = false;
		}
		else{
			jQuery("#gridImputado1").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idAudienciaSiguiente+"&tipoConsulta="+tipoConsulta, datatype: "xml" });
			$("#gridImputado1").trigger("reloadGrid");
		}
		
	}

	/////////////////////////////////
	var banderaCargaGridVictima1 = true;
	/*
	*Funcion que carga el grid de victimas
	*/
	function cargaGridVictima1(){

		var tipoConsulta=6;

		if( banderaCargaGridVictima1 == true){
			
			jQuery("#gridVictima1").jqGrid({

				url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idAudienciaSiguiente+"&tipoConsulta="+tipoConsulta,						
				datatype: "xml",
				colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Presente','check','Condicion'], 
				colModel:[ 	{name:'Nombre',index:'Nombre', width:180,align:'center'}, 
							{name:'APaterno',index:'APaterno', width:180,align:'center'}, 
							{name:'AMaterno',index:'AMaterno', width:180,align:'center'},
							{name:'Presente',index:'Presente', width:50,align:'center',hidden:true},
							{name:'check',index:'check', width:50, hidden:true},  
							{name:'Condicion',index:'Condicion', width:50, hidden:true},
						],
					autowidth: false,
					width:924,
					autoheight:true,
					pager: jQuery('#paginadorGridVictima1'), 
					sortname: 'Nombre',
					rowNum:10,
					rowList:[10,20,30],
					gridview: false, 
					viewrecords: true, 
					sortorder: "desc",
					hidegrid: false,
					toolbar: [true,"top"],
					caption:"Víctima",
					ondblClickRow: function(rowid) {

						var row = jQuery("#gridVictima1").jqGrid('getRowData',rowid);
						if(row){						
							if(row.Condicion == "1"){
								consultarDenunciante1(rowid);
							}else{
								consultarVictima1(rowid);
							}
						}
					} 
			}).navGrid('#paginadorGridVictima1',{edit:false,add:false,del:false});
			
			$("#t_gridVictima1").append("<input type='button' class='btn_Generico' id='btnAgregarVictimaSig' value='Agregar nueva victima' style='height:20px;font-size:-3'/>");
			
			$("#btnAgregarVictimaSig","#t_gridVictima1").click(function(){
				creaNuevaVictima1();
			});

			///Se agrega boton para relacionar victimas existentes con la nueva audiencia
			$("#t_gridVictima1").append("<input type='button' class='btn_Generico' id='btnAgregarVictimaExistente' value='Agregar victima existente' style='height:20px;font-size:-3'/>");
			
			$("#btnAgregarVictimaExistente","#t_gridVictima1").click(function(){
				asociarInvolucradosPorCalidadAudienciaSiguiente("<%=Calidades.VICTIMA_PERSONA.getValorId()%>");
			});

			banderaCargaGridVictima1= false;
		}
		else{
			jQuery("#gridVictima1").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idAudienciaSiguiente+"&tipoConsulta="+tipoConsulta , datatype: "xml" });
			$("#gridVictima1").trigger("reloadGrid");
		}		
	}

	var idWindowIngresarVictima1 = 1;
	
	//Abre una nueva ventana de crear una nueva victima
	function creaNuevaVictima1() {
		idWindowIngresarVictima1++;
		var elementoNuevo="si";
		$.newWindow({id:"iframewindowIngresarVictima1" + idWindowIngresarVictima1, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Víctima", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarVictima1" + idWindowIngresarVictima1,'<iframe src="<%= request.getContextPath() %>/IngresarVictima.do?numeroExpediente='+numeroExpediente +'&audienciaId='+idAudienciaSiguiente+'&elemento='+elementoNuevo+'" width="1100" height="530" />');		
	}

	var idWindowConsultarVictima1 = 1;
	
	//Abre una nueva ventana para consultar una víctima		
	function consultarVictima1(idInvolucrado){
		idWindowConsultarVictima1++;
		$.newWindow({id:"iframewindowConsultarVictima1" + idWindowConsultarVictima1, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Consultar Victima", type:"iframe"});		
		$.updateWindowContent("iframewindowConsultarVictima1" + idWindowConsultarVictima1,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?numeroExpediente='+numeroExpediente+'&idVictima='+idInvolucrado+'&idCalidad=VICTIMA" width="1100" height="530" />');
	}


	var banderaCargaGridDenunciante1 = true;
	
	/*
	*Funcion que carga el grid de denunciantes
	*/
	function cargaGridDenunciante1(){

		tipoConsulta=7;

		if( banderaCargaGridDenunciante1 == true){
			
			jQuery("#gridDenunciante1").jqGrid({

				url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idAudienciaSiguiente+"&tipoConsulta="+tipoConsulta,						
				datatype: "xml",
				colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Presente'], 
				colModel:[ 	{name:'Nombre',index:'Nombre', width:180,align:'center'}, 
							{name:'APaterno',index:'APaterno', width:180,align:'center'}, 
							{name:'AMaterno',index:'AMaterno', width:180,align:'center'},
							{name:'Presente',index:'Presente', width:50,hidden:true,align:'center'}, 
						],
					autowidth: false,
					width:924,
					autoheight:true,
					pager: jQuery('#paginadorGridDenunciante1'), 
					sortname: 'Nombre', 
					rowNum:10,
					rowList:[10,20,30],
					gridview: false, 
					viewrecords: true, 
					sortorder: "desc",
					hidegrid: false,
					toolbar: [true,"top"],
					caption:"Denunciante",
					ondblClickRow: function(rowid) {
						
						consultarDenunciante1(rowid);
					} 
			}).navGrid('#paginadorGridDenunciante1',{edit:false,add:false,del:false});

			//AGA- No se puede agregar mas de un denunciante, como en PG
			//$("#t_gridDenunciante1").append("<input type='button' class='btn_Generico' id='btnAgregarDenuncianteSig' value='Agregar Denunciante' style='height:20px;font-size:-3'/>");
			//$("#btnAgregarDenuncianteSig","#t_gridDenunciante1").click(function(){
			//	crearDenunciante1();
			//});

			$("#t_gridDenunciante1").append("<input type='button' class='btn_Generico' id='btnAgregarDenuncianteExistente' value='Agregar denunciante existente' style='height:20px;font-size:-3'/>");
			$("#btnAgregarDenuncianteExistente","#t_gridDenunciante1").click(function(){
				asociarInvolucradosPorCalidadAudienciaSiguiente("<%=Calidades.DENUNCIANTE.getValorId()%>");
			});

			banderaCargaGridDenunciante1 = false;
		}
		else{
			jQuery("#gridDenunciante1").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idAudienciaSiguiente+"&tipoConsulta="+tipoConsulta , datatype: "xml" });
			$("#gridDenunciante1").trigger("reloadGrid");
		}		
	}


	var idWindowIngresarDenunciante1 = 1;
	
	//Abre una nueva ventana de Denunciante
	function crearDenunciante1(){
		idWindowIngresarDenunciante1++;
		elementoNuevo = "si";
		$.newWindow({id:"iframewindowIngresarDenunciante1" + idWindowIngresarDenunciante1, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Denunciante", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarDenunciante1" + idWindowIngresarDenunciante1,'<iframe src="<%= request.getContextPath() %>/IngresarDenunciante.do?numeroExpediente='+numeroExpediente +'&audienciaId='+idAudienciaSiguiente+'&elemento='+elementoNuevo+'&calidadInv=DENUNCIANTE" width="1100" height="530" />');		
	}

	var idWindowConsultarDenunciante1 = 1;
	
	//Abre una nueva ventana de Denunciante
	function consultarDenunciante1(id){
		idWindowConsultarDenunciante1++;
		$.newWindow({id:"iframewindowIngresarDenunciante1" + idWindowConsultarDenunciante1, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Denunciante", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarDenunciante1" + idWindowConsultarDenunciante1,'<iframe src="<%= request.getContextPath() %>/IngresarDenunciante.do?idDenunciante='+id +'&numeroExpediente='+numeroExpediente +'&calidadInv=DENUNCIANTE" width="1100" height="530" />');		
	}
	
	
	var banderaGridTestigo1= true;
	/*
	*Funcion que carga el grid de Testigos para la siguiente audiencia
	*/
	function cargaGridTestigo1(){

		tipoConsulta=3;

		if(banderaGridTestigo1 == true){

			jQuery("#gridTestigo1").jqGrid({

				url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idAudienciaSiguiente+"&tipoConsulta="+tipoConsulta,						
				datatype: "xml",
				colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Presente'], 
				colModel:[ 	{name:'Nombre',index:'Nombre', width:180,align:'center'}, 
							{name:'APaterno',index:'APaterno', width:180,align:'center'}, 
							{name:'AMaterno',index:'AMaterno', width:180,align:'center'},	
							{name:'Presente',index:'Presente', width:150,align:'center',hidden:true}, 
						],
				autowidth: false,
				width:924,
				autoheight: true,
				pager: jQuery('#paginadorGridTestigo1'), 
				sortname: 'Nombre', 
				rowNum:10,
				rowList:[10,20,30],
				gridview: false, 
				viewrecords: true, 
				sortorder: "desc",
				//hidegrid: true,
				toolbar: [true,"top"],
				caption:"Testigo",
				ondblClickRow: function(rowid) {
					creaNuevoTestigoAudienciaSiguiente(rowid);
				} 
			}).navGrid('#paginadorGridTestigo1',{edit:false,add:false,del:false});
			
			$("#t_gridTestigo1").append("<input type='button' class='btn_Generico' id='btnAgregarTestigoSig' value='Agregar Testigo' style='height:20px;font-size:-3'/>");

			$("#btnAgregarTestigoSig","#t_gridTestigo1").click(function(){
				creaNuevoTestigoAudienciaSiguiente(null);
			});
			
			$("#t_gridTestigo1").append("<input type='button' class='btn_Generico' id='btnAgregarTestigoExistente' value='Agregar testigo existente' style='height:20px;font-size:-3'/>");

			$("#btnAgregarTestigoExistente","#t_gridTestigo1").click(function(){
				asociarInvolucradosPorCalidadAudienciaSiguiente("<%=Calidades.TESTIGO.getValorId()%>");
			});
				
			banderaGridTestigo1 = false;
		}
		else{
			jQuery("#gridTestigo1").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idAudienciaSiguiente+"&tipoConsulta="+tipoConsulta, datatype: "xml" });
			$("#gridTestigo1").trigger("reloadGrid");
		}
		
	}

	/*
	*Funcion que carga el grid para los fiscales de la siguiente audiencia
	*/
	function cargaGridFiscal1(){			

		jQuery("#gridFiscal1").jqGrid({
			url:'<%= request.getContextPath()%>/consultarFuncionarioExternoAudienciaPorRol.do?idEvento='+idAudienciaSiguiente+'&rolId=<%=Roles.AGENTEMP.getValorId()%>',
			datatype: "xml",
			colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Titular','Presente'], 
			colModel:[ 	{name:'Nombre',index:'Nombre', width:180,align:'center'}, 
						{name:'APaterno',index:'APaterno', width:180,align:'center'}, 
						{name:'AMaterno',index:'AMaterno', width:180,align:'center'},
						{name:'Titular',index:'Titular', width:50,align:'center',hidden:true}, 
						{name:'Presente',index:'Presente', width:60,align:'center',hidden:true}
					],
				autowidth: false,
				width:924,
				autoheight:true,
				pager: jQuery('#paginadorGridFiscal1'), 
				sortname: 'Nombre', 
				rowNum:10,
				rowList:[10,20,30],
				gridview: false, 
				viewrecords: true, 
				sortorder: "desc",
				hidegrid: false,
				toolbar: [true,"top"],
				loadComplete: function(){
					primeraFiscal="false";
				}, 
				caption:'<bean:message key="agenteMp"/>'
		}).navGrid('#paginadorGridFiscal',{edit:false,add:false,del:false});

		//Button agregar amp
		var inputAgregarFiscal = "<input type='button' class='btn_Generico' id='butAgreAmp1' value='Agregar ";
		inputAgregarFiscal += '<bean:message key="agenteMp"/>';
		inputAgregarFiscal += "' style='height:20px;font-size:-3'/>";
		$("#t_gridFiscal1").append(inputAgregarFiscal);
		
		//Button eliminar amp
		var inputEliminarFiscal = "<input type='button' class='btn_Generico' id='butElimAmp1' value='Eliminar ";
		inputEliminarFiscal += '<bean:message key="agenteMp"/>';
		inputEliminarFiscal += "' style='height:20px;font-size:-3'/>";
		$("#t_gridFiscal1").append(inputEliminarFiscal);
			
		$("#butAgreAmp1","#t_gridFiscal1").click(function(){
			puestoFuncionarioSig="amp";
			agregarFuncionarioExternoAudiencia('<%=Roles.AGENTEMP.getValorId()%>',true);
		});

		$("#butElimAmp1","#t_gridFiscal1").click(function(){
			puestoFuncionarioSig="amp";
			var rowid = jQuery("#gridFiscal1").jqGrid('getGridParam','selrow');
			eliminarFuncionarioExternoAudiencia(rowid,'<%=Roles.AGENTEMP.getValorId()%>',true);
		});
	}

	
	/*
	*Funcion que carga el grid para los peritos de la siguiente audiencia
	*/
	function cargaGridPerito1(){

		jQuery("#gridPerito1").jqGrid({

			url:'<%= request.getContextPath()%>/consultarFuncionarioExternoAudienciaPorRol.do?idEvento='+idAudienciaSiguiente+'&rolId=<%=Roles.PERITOAMP.getValorId()%>',
			//url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idAudienciaSiguiente+"&tipoConsulta="+tipoConsulta,						
			datatype: "xml",
			colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Parte','Especialidad','Presente'], 
			colModel:[ 	{name:'Nombre',index:'Nombre', width:180,align:'center'}, 
						{name:'APaterno',index:'APaterno', width:180,align:'center'}, 
						{name:'AMaterno',index:'AMaterno', width:180,align:'center'},
						{name:'Parte',index:'Parte', width:100,hidden:true}, 
						{name:'Parentesco',index:'Parentesco', width:150,hidden:true},
						{name:'Titular',index:'Titular', width:150,hidden:true},
					],
			autowidth: false,
			width:924,
			autoheight: true,
			pager: jQuery('#paginadorGridPerito1'), 
			sortname: 'Nombre', 
			rowNum:10,
			rowList:[10,20,30],
			gridview: false, 
			viewrecords: true, 
			sortorder: "desc",
			//hidegrid: true,
			toolbar: [true,"top"],
			caption:"Perito"
		}).navGrid('#paginadorGridPerito1',{edit:false,add:false,del:false});

		$("#t_gridPerito1").append("<input type='button' class='btn_Generico' id='butAgrePer1' value='Agregar Perito' style='height:20px;font-size:-3'/>");

		//Button eliminar perito
		$("#t_gridPerito1").append("<input type='button' class='btn_Generico' id='butElimPer1' value='Eliminar Perito' style='height:20px;font-size:-3'/>");
		
		$("#butAgrePer1","#t_gridPerito1").click(function(){
			puestoFuncionarioSig="perito";
			agregarFuncionarioExternoAudiencia('<%=Roles.PERITOAMP.getValorId()%>',true);
		});

		$("#butElimPer1","#t_gridPerito1").click(function(){
			puestoFuncionarioSig="perito";
			var rowid = jQuery("#gridPerito1").jqGrid('getGridParam','selrow');
			eliminarFuncionarioExternoAudiencia(rowid,'<%=Roles.PERITOAMP.getValorId()%>',true);			
		});
	}

	/*
	*Funcion que carga el grid para los policias ministeriales de la siguiente audiencia
	*/
	function cargaGridPolicia1(){
		
		jQuery("#gridPolicia1").jqGrid({

			url:'<%= request.getContextPath()%>/consultarFuncionarioExternoAudienciaPorRol.do?idEvento='+idAudienciaSiguiente+'&rolId=<%=Roles.POLICIAMINISTER.getValorId()%>',
			//url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idAudienciaSiguiente+"&tipoConsulta="+tipoConsulta,						
			datatype: "xml",
			colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Parte','Aceptado'], 
			colModel:[ 	{name:'Nombre',index:'Nombre', width:150,align:'center'}, 
						{name:'APaterno',index:'APaterno', width:100,align:'center'}, 
						{name:'AMaterno',index:'AMaterno', width:150,align:'center'},
						{name:'Parte',index:'Parte', width:150,hidden:true}, 
						{name:'Aceptado',index:'Aceptado', width:150,hidden:true},
						
					],
			autowidth: false,
			width:924,
			autoheight: true,
			pager: jQuery('#paginadorGridPolicia1'), 
			sortname: 'Nombre', 
			rowNum:10,
			rowList:[10,20,30],
			gridview: false, 
			viewrecords: true, 
			sortorder: "desc",
			toolbar: [true,"top"],
			caption:"Elemento de la policía ministerial"
		}).navGrid('#paginadorGridPolicia1',{edit:false,add:false,del:false});
		
		$("#t_gridPolicia1").append("<input type='button' class='btn_Generico' id='butAgrePolMin1' value='Agregar Policia Ministerial' style='height:20px;font-size:-3'/>");

		//Button eliminar amp
		$("#t_gridPolicia1").append("<input type='button' class='btn_Generico' id='butElimPolMin1' value='Eliminar Policia Ministerial' style='height:20px;font-size:-3'/>");
		
		$("#butAgrePolMin1","#t_gridPolicia1").click(function(){
			puestoFuncionarioSig="policia";
			agregarFuncionarioExternoAudiencia('<%=Roles.POLICIAMINISTER.getValorId()%>',true);
		});

		$("#butElimPolMin1","#t_gridPolicia1").click(function(){
			puestoFuncionarioSig="policia";
			var rowid = jQuery("#gridPolicia1").jqGrid('getGridParam','selrow');
			eliminarFuncionarioExternoAudiencia(rowid,'<%=Roles.POLICIAMINISTER.getValorId()%>',true);
		});
	}
	

	/*
	*Funcion que carga el grid de defensores de la sub tab, para la siguiente audiencia
	*/		
	function cargaGridDefensor1(){

		jQuery("#gridDefensor1").jqGrid({ 

			url:'<%= request.getContextPath()%>/consultarFuncionarioExternoAudienciaPorRol.do?idEvento='+idAudienciaSiguiente+'&rolId=<%=Roles.DEFENSOR.getValorId()%>',
			datatype: "xml",
			colNames:['Nombre','Apellido Paterno', 'Apellido Materno','Titular','Presente'], 
			colModel:[ 	{name:'Nombre',index:'Nombre', width:180,align:'center'}, 
						{name:'APaterno',index:'APaterno', width:180,align:'center'}, 
						{name:'AMaterno',index:'AMaterno', width:180,align:'center'},
						{name:'Titular',index:'Titular', width:50,align:'center',hidden:true}, 
						{name:'Presente',index:'Presente', width:60,align:'center',hidden:true}
					],
				autowidth: false,
				width:924,
				autoheight:true,
				pager: jQuery('#paginadorGridDefensor1'), 
				sortname: 'Nombre',
				rowNum:10,
				rowList:[10,20,30],				
				gridview: false, 
				viewrecords: true, 
				sortorder: "desc",
				hidegrid: false,
				toolbar: [true,"top"],
				caption:"Defensor"
		}).navGrid('#paginadorGridDefensor1',{edit:false,add:false,del:false});

		$("#t_gridDefensor1").append("<input type='button' class='btn_Generico' id='butADefensor1' value='Agregar Defensor' style='height:20px;font-size:-3'/>");

		$("#t_gridDefensor1").append("<input type='button' class='btn_Generico' id='butElimDefensor1' value='Eliminar Defensor' style='height:20px;font-size:-3'/>");

		$("#t_gridDefensor1").append("<input type='button' class='btn_Generico' id='butSDefensor1' value='Solicitar Defensor' style='height:20px;font-size:-3'/>");

		$("#butADefensor1","#t_gridDefensor1").click(function(){
			puestoFuncionarioSig="defensor";
			agregarFuncionarioExternoAudiencia('<%=Roles.DEFENSOR.getValorId()%>',true);
		});

		$("#butElimDefensor1","#t_gridDefensor1").click(function(){
			puestoFuncionarioSig="defensor";
			var rowid = jQuery("#gridDefensor1").jqGrid('getGridParam','selrow');
			eliminarFuncionarioExternoAudiencia(rowid,'<%=Roles.DEFENSOR.getValorId()%>',true);
		});
		
		$("#butSDefensor1","#t_gridDefensor1").click(function(){
			solicitarDefensor(true);
		});
	}	


	/*
	*Funcion que relaciona los involucrado a la audiencia actual
	*/
	function asociarInvolucradosPorCalidadAudienciaSiguiente(calidadId){

		cargaGridRelacionarInvolucradosPorCalidadAudienciaSig(calidadId);
		
		$("#divRelacionarInvolucradosPorCalidadAudienciaSig").dialog("open");
		$("#divRelacionarInvolucradosPorCalidadAudienciaSig").dialog({ autoOpen: true, modal: true, 
		title: 'Asociar involucrados a la siguiente audiencia', 
		dialogClass: 'alert', position: [350,50],
		width: 800, height:480, maxWidth: 800, maxHeight:550,
		buttons:{
			"Asociar":function() {
				customConfirm("¿Realmente desea asociar los involucrados a la siguiente audiencia?", "", asociarIndividuoAAudiencia);
			},
			"Cancelar":function() {
				cerrarAsociarInvolucradosPorCalidadAudienciaSiguiente();
			}
		}
		});	

	}

	/**
	*Cierra el dialog confirm
	*/
	function cerrarAsociarInvolucradosPorCalidadAudienciaSiguiente(){
		$("#divRelacionarInvolucradosPorCalidadAudienciaSig").dialog("close");
	}

	//Variable para controlar si se carga por primera vez el grid
	var banderaRelacionarInvolucradosPorCalidadAudienciaSig = true;
	
	/**
	*Funcion que carga el grid medios de Prueba
	*/
	function cargaGridRelacionarInvolucradosPorCalidadAudienciaSig(calidadId){

		var tipoConsulta= "";
		
		if(calidadId == "<%=Calidades.VICTIMA_PERSONA.getValorId()%>"){
			tipoConsulta=TIPO_CONSULTA_VICTIMA;
		}
		if(calidadId == "<%=Calidades.DENUNCIANTE.getValorId()%>"){
			tipoConsulta=TIPO_CONSULTA_DENUNCIANTE;
		}
		if(calidadId == "<%=Calidades.TESTIGO.getValorId()%>"){
			tipoConsulta=TIPO_CONSULTA_TESTIGO;
		}

		if(banderaRelacionarInvolucradosPorCalidadAudienciaSig == true){

			jQuery("#gridRelacionarInvolucradosPorCalidadAudienciaSig").jqGrid({ 
				url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idEvento+"&tipoConsulta="+tipoConsulta,
				datatype: "xml",
				colNames:['Nombre','Apellido Paterno', 'Apellido Materno'], 
				colModel:[ 	{name:'Nombre',index:'Nombre', width:180,align:'center'}, 
							{name:'APaterno',index:'APaterno', width:180,align:'center'}, 
							{name:'AMaterno',index:'AMaterno', width:180,align:'center'}
						],
				pager: jQuery('#pagerGridRelacionarInvolucradosPorCalidadAudienciaSig'),
				rowNum:10,
				autoWidth:false,
				width:580,
				height:250,
				rowList:[10,20,30],
				sortname: 'Nombre',
				viewrecords: true,
				sortorder: "desc",
				caption:"Involucrados a relacionar",
				multiselect:true
			}).navGrid('#gridRelacionarInvolucradosPorCalidadAudienciaSig',{edit:false,add:false,del:false});
			
			banderaRelacionarInvolucradosPorCalidadAudienciaSig = false;
		}
		else{
			jQuery("#gridRelacionarInvolucradosPorCalidadAudienciaSig").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultarInvolucradosAudienciaIndividualizacion.do?idEvento='+idEvento+"&tipoConsulta="+tipoConsulta , datatype: "xml" });
			$("#gridRelacionarInvolucradosPorCalidadAudienciaSig").trigger("reloadGrid");
		}
	}

	/*
	* Asocia un involucrado recien creado a la audiencia con el id enviado como parametro
	*/
	function asociarIndividuoAAudiencia(){

		var iDsInvolucrados = "";
		var exitoAsociar = 0;
		
		iDsInvolucrados += jQuery("#gridRelacionarInvolucradosPorCalidadAudienciaSig").jqGrid('getGridParam','selarrrow');

		if(iDsInvolucrados != ""){

			var arrayIds = iDsInvolucrados.split(",");
			
			
			for(indice=0;indice<arrayIds.length;indice++){
				
				$.ajax({								
					type: 'POST',
			    	url: '<%= request.getContextPath()%>/asociarInvolucradoAAudiencia.do',
			    	data: 'involucradoId='+arrayIds[indice]+'&audienciaId='+idAudienciaSiguiente,
			    	async:false,				
			    	dataType: 'xml',
			    	success: function(xml){
			    		exitoAsociar++;
			    	}
				});
			}
			
			if(parseInt(exitoAsociar) > 0 ){
				exitoAsociar = 0;
				cargaGridTestigo1();
				cargaGridDenunciante1();
				cargaGridVictima1();
				customAlert("Los involucrados fueron asociados de manera correcta");
				cerrarAsociarInvolucradosPorCalidadAudienciaSiguiente()
			}
			else{
				customAlert("No es posible asociar los involucrados");
			}
			
		}else{
			customAlert("Seleccione al menos un involucrado");
		}
	}
	//TERMINA FUNCIONALIDAD PARA AGREGAR FUNCIONARIO
		
		
		
	
	/****FUNCIONALIDAD PARA LA SUB CEJA OBJETOS*/
	/**
	*Funcion que carga el grid Objetos
	*/
	function cargaGridObjetos(){

		jQuery("#gridSolObjetos").jqGrid({ 
			url:'<%=request.getContextPath()%>/gridConsultaObjetos.do?audienciaId='+idAudienciaSiguiente,
			datatype: "xml", 
			colNames:['Otorgante','No. Prueba', 'Cadena de Custodia', 'Descripción Objeto', 'Aceptado'], 
			colModel:[ 					
			           	{name:'Otorgante',index:'Otorgante', width:100, align:'center'}, 
			           	{name:'Prueba',index:'Prueba', width:80, align:'center'},
			           	{name:'Cadena',index:'Cadena', width:80, align:'center'},
			        	{name:'Objeto',index:'Objeto', width:100, align:'center'}, 
			           	{name:'Aceptado',index:'Aceptado', width:80, align:'center'},
					],
			pager: jQuery('#pagerGridSolObjetos'),
			rowNum:10,
			autoWidth:false,
			width:400,
			rowList:[10,20,30],
			sortname: 'duracion',
			viewrecords: true,
			sortorder: "desc"
		}).navGrid('#gridSolObjetos',{edit:false,add:false,del:false});

		$("#gview_gridSolObjetos .ui-jqgrid-bdiv").css('height', '150px');
			
	}

	
	/*
	*Funcion que guarda un objeto capturado en la sub ceja de objetos de la ceja Prog. Audiencia
	*/
	function guardaObjeto(){
		 
		var param="idAudiencia="+idAudienciaSiguiente;
		param+="&campoObjeto="+$("#campoObjeto").val();
		param+="&campoPrueba="+$("#campoPrueba").val();
		param+="&campoCadena="+$("#campoCadena").val();
		param+="&campoDescripciona="+$("#campoDescripciona").val();
		param+="&campoEstado="+$("#campoEstado option:selected").val();
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/guardaObjetosCarpetaEjecucion.do',
			data: param,
			async: false,
			dataType: 'xml',
			success: function(xml){

			}
		});
		$("#gridSolObjetos").trigger("reloadGrid");
	}

	/*
	*Funcion que carga el catalogo de condicion fisica del objeto
	*/
	function cargaCondicion() {
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCondicion.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catCondicion').each(function(){
					$('#campoEstado').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}	

	/*
	*Funcion que consulta el detalle del evento y llena 
	*los campos de la TAB Programar Audiencia
	*/
	/////////////////////NO SE ESTA USANDO
	function consultaDetalleEventoProgramarAudiencia(){

		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/detalleAudienciasDelDiaPJENS.do',
			data: 'idEvento='+ idAudienciaSiguiente, 
			async: false,
			dataType: 'xml',
			success: function(xml){
				var errorCode;
				errorCode=$(xml).find('response').find('code').text();
				if(parseInt(errorCode)==0){

					
    				limpiaDatosDetalleEvento();
    			   

    				//Tab Programar Audiencia    				
    				tipoAudiencia = $(xml).find('tipoAudiencia').find('idCampo').first().text();
    				$("#numCasoProgramarAudiencia").val($(xml).find('numeroGeneralCaso').first().text());
    				$("#numExpedienteProgramarAudiencia").val($(xml).find('numeroExpediente').first().text());
    				$("#fechaLimiteProgramarAudiencia").val($(xml).find('strFechaLimite').first().text());
						//LLama a cargar combo de tipos de audiencia para la ceja programar audiencia
    					cargaComboTipoAudiencia(tipoAudiencia);
    			}
				else{
					//Mostrar mensaje de error
				}
			}
		});
	}


		function gridTraspasos(){
			/**
			*Funcion que llena el grid de traslado
			*/
			jQuery("#gridTrasladoPJA").jqGrid({
				datatype: "xml", 
				colNames:['Imputado','Delito','Centro de Detención'], 
				colModel:[ 	{name:'imputado',index:'imputado', width:200,align:'center'}, 
							{name:'Delito',index:'delito', width:100,align:'center'},
							{name:'centroDetencion',index:'centroDetencion', width:200,align:'center'}
							
						],
				pager: jQuery('#pager2'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc"
			}).navGrid('#pager2',{edit:false,add:false,del:false});
			}

//**********************************************************************FUNCIONALIDAD DESCONOCIDA****************************************************************
	function detalleInvolucradosPJENS(idRow){

		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarDetalleInvolucradoPJENS.do?idInvolucrado='+idRow+'',
			async: false,
			dataType: 'xml',
			success: function(xml){

				 var IDPAIS_MEXICO = 10;
				 
				$("#nombre").val($(xml).find('nombre').text());
				$("#apellidoPaterno").val($(xml).find('apellidoPaterno').text());
				$("#apellidoMaterno").val($(xml).find('apellidoMaterno').text());
				$("#cmbInstitucion").val($(xml).find('id').text());
				$("#correo").val($(xml).find('direccionElectronica').text());
				$("#codigoPostal").val($(xml).find('domicilio').find('asentamientoDTO').find('codigoPostal').text());	
				$('#cbxPais option:selected').val($(xml).find('domicilio').find('asentamientoDTO').find('municipioDTO').find('entidadFederativaDTO').find('valorIdPais').find('idCampo').text());
				
				var idPais = $('#cbxPais option:selected').val();	

				 if(idPais==IDPAIS_MEXICO){   

			           var entidadFede=$(xml).find('domicilio').find('asentamientoDTO').find('municipioDTO').find('entidadFederativaDTO').find('entidadFederativaId').text();
			           $('#cbxEntFederativa').find("option[value='"+entidadFede+"']").attr("selected","selected");
			           $('#cbxEntFederativa').multiselect('refresh');
			           //onSelectChangeEntFed();
				       $('#cbxCiudad option:selected').val($(xml).find('Pais').text());
				       var municipio=$(xml).find('domicilio').find('asentamientoDTO').find('municipioDTO').find('municipioId').text();
					   $('#cbxDelegacionMunicipio').find("option[value='"+municipio+"']").attr("selected","selected");
			           $('#cbxDelegacionMunicipio').multiselect('refresh');
				       $('#cbxAsentamientoColonia option:selected').val($(xml).find('Pais').text());
			           $('#cbxTipoAsentamiento option:selected').val($(xml).find('Pais').text());
			           $('#cbxTipoCalle option:selected').val($(xml).find('Pais').text());
			            
			        }else{
			            $('#entidadFederativa').val($(xml).find('Pais').text());
			            $('#areaCiudad').val($(xml).find('Pais').text());
			            $('#areaDelegacionMunicipio').val($(xml).find('Pais').text());
			            $('#areaColonia').val($(xml).find('Pais').text());
			            $('#areaAsentamiento').val($(xml).find('Pais').text());        
			            $('#areaTipoCalle').val($(xml).find('Pais').text());
			        }
			        
				$("#areaCalle").val($(xml).find('domicilio').find('calle').text());
				$("#areaNumeroExterior").val($(xml).find('domicilio').find('numeroExterior').text());
				$("#areaNumeroInterior").val($(xml).find('domicilio').find('numeroInterior').text());
				$("#areaReferencias").val($(xml).find('domicilio').find('referencias').text());
				$("#areaEntreCalle").val($(xml).find('domicilio').find('entreCalle1').text());
				$("#areaYCalle").val($(xml).find('domicilio').find('entreCalle2').text());
				$("#areaAlias").val($(xml).find('domicilio').find('alias').text());
				$("#areaEdificio").val($(xml).find('tipoAudiencia').find('valor').text());
				$("#txtFldLongitud").val($(xml).find('').text());
				$("#txtFldLatitud2").val($(xml).find('').text());
				domicilioId = $(xml).find('domicilio').find('elementoId').text();
				    					    			
			}
		});				

		$("#agregarDestinatario").dialog("open");
	  	$("#agregarDestinatario").dialog({ autoOpen: true, 
			modal: true, 
		  	title: 'Detalle del Involucrado', 
		  	dialogClass: 'alert',
		  	position: [312,40],
		  	width: 880,
		  	height: 510,
		  	maxWidth: 1000,
		  	buttons:{"Guardar":function() {	
			  	
	            var IDPAIS_MEXICO = 10;
		        var idPais = $('#cbxPais option:selected').val();
		        var parametros = 'pais=' + idPais;
		        parametros += '&codigoPostal=' +  $('#codigoPostal').val();
		        //Cambiar por el id de Mexico
		        if(idPais==IDPAIS_MEXICO){        	
		            parametros += '&entidadFederativa=' + $('#cbxEntFederativa option:selected').val();
		            parametros += '&ciudad=' + $('#cbxCiudad option:selected').val();
		            parametros += '&delegacionMunicipio=' + $('#cbxDelegacionMunicipio option:selected').val();
		            parametros += '&asentamientoColonia=' + $('#cbxAsentamientoColonia option:selected').val();
		            parametros += '&tipoAsentamiento=' + $('#cbxTipoAsentamiento option:selected').val();
		            parametros += '&tipoCalle=' + $('#cbxTipoCalle option:selected').val();
		        }else{
		            parametros += '&entidadFederativa=' + $('#entidadFederativa').val();
		            parametros += '&ciudad=' + $('#areaCiudad').val();
		            parametros += '&delegacionMunicipio=' + $('#areaDelegacionMunicipio').val();
		            parametros += '&asentamientoColonia=' + $('#areaColonia').val();
		            parametros += '&tipoAsentamiento=' + $('#areaAsentamiento').val();        
		            parametros += '&tipoCalle=' + $('#areaTipoCalle').val();
		        }

		        parametros += '&calle=' + $('#areaCalle').val();
		        parametros += '&numExterior=' + $('#areaNumeroExterior').val();
		        parametros += '&numInterior=' + $('#areaNumeroInterior').val();
		        parametros += '&referencias=' + $('#areaReferencias').val();
		        parametros += '&entreCalle=' + $('#areaEntreCalle').val();
		        parametros += '&ycalle=' + $('#areaYCalle').val();
		        parametros += '&aliasDomicilio=' + $('#areaAlias').val(); //ALIAS DE DOMICILIO?
		        parametros += '&edificio=' + $('#areaEdificio').val();
		        parametros += '&longitud=' + $('#txtFldLongitud').val();
		        parametros += '&latitud=' + $('#txtFldLatitud2').val();

		        parametros += '&nombre=' + $("#nombre").val();
		        parametros += '&aPaterno=' + $("#apellidoPaterno").val();
		        parametros += '&aMaterno=' + $("#apellidoMaterno").val();
		        
		        if($("#cmbInstitucion option:selected").val() == "Ministerio Público"){
		        	parametros +='&institucion=' + 1;
				}else{
					parametros +='&institucion=' + 2;
				}
		        parametros += '&correo=' + $("#correo").val();
		       		       		       
		       parametros += '&domicilioId=' + domicilioId;
		        
		        $.ajax({
					type: 'POST',
					url: '<%= request.getContextPath()%>/modificarDatosInvolucrado.do?idInvolucrado='+idRow+'',
					data: parametros,
					dataType: 'xml',
					async: false,
					success: function(xml){
						$(xml).find('catPaises').each(function(){
							$('#cbxPais').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
							$('#cbxPais').multiselect('refresh');
							});
					}
				});		  		
					  			  					 
		  			$(this).dialog("close");
		  		},
		  		"Cancelar":function() {
		  			$(this).dialog("close");
		  		}
		  	}
		});	  	
	}


	function  ocultaBotones(){
		if($("#gridFiscal_Nombre").display=="none"){
		$("#botonesFiscal").css("display","none");
		}else $("#botonesFiscal").css("display","block");
	}

	//variable para controlar cuando se recarga el grid
	var cargaGridImputadosProgAudiencia = true;
	
	/**
	*Funcion que recarga el grid con los imputados de la audiencia
	*/
	function cargaImputadosProgramarAudiencia(idEvento){
		
		if(cargaGridImputadosProgAudiencia == true){
			
			jQuery("#gridsImputadosAudiencia").jqGrid({ 
	
				url:'<%= request.getContextPath()%>/consultarImputadoMaximaAudiencia.do?idEvento='+idEvento+'', 
				datatype: "xml", 
				colNames:['Nombre'], 
				colModel:[
						{name:'nombre',index:'nombre', width:150}
				],
				pager: jQuery('#pagerinv'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc",
				multiselect: true,
				caption:"Imputados"
			}).navGrid('#pagerinv',{edit:false,add:false,del:false});
			
			cargaGridImputadosProgAudiencia= false;
		}
		else{
			jQuery("#gridsImputadosAudiencia").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarImputadoMaximaAudiencia.do?idEvento='+idEvento+'' ,datatype: "xml" });
			$("#gridsImputadosAudiencia").trigger("reloadGrid");
		}
		
		
	}
	
	//********************************************************************************************************************************************************************/
	
	function eliminarAudienciaJAVS(){
		$.ajax({
			type: 'POST',
			url:'<%= request.getContextPath()%>/eliminarAudienciaJavs.do?idAudiencia='+idAudiencia+'',
			data:'',
			dataType: 'xml',
			async: false,
			success: function(xml){
				var respuesta;
				respuesta=$(xml).find('response').find('body').find('long').text();
				var s_MENSAJE=mensajeEstadoJAVSEliminacion(respuesta);
				if(s_MENSAJE!=""){
					alertDinamico(s_MENSAJE);
				}
				else{
					// Puede ser error de conexión WS
					alertDinamico("Fallo al eliminar la audiencia");
				}
				$("#gridAudienciasResolutivosPJENS").trigger("reloadGrid");
			}
		});
	}

	function mensajeEstadoJAVSEliminacion(idEvento){
		var s_MENSAJE="";
		switch (idEvento){
        case "<%=ConstantesGenerales.FALLO_GENERAL%>":
            s_MENSAJE = "Fallo al conectar con el servidor JAVS.</br>Vuelva a intentarlo m&aacute;s tarde";
            break;
        case "<%=ConstantesGenerales.FALLO_GENERAL_JAVS%>":
            s_MENSAJE = "Fallo al conectar con el servidor JAVS.</br>Vuelva a intentarlo m&aacute;s tarde";
            break;
        case "<%=ConstantesGenerales.ERROR_CREDENCIALES_CONSULTA%>":
        	s_MENSAJE = "Fallo al conectar con el servidor JAVS, Error en credenciales.</br>vuelva a intentarlo m&aacute;s tarde";
            break;
        case "<%=ConstantesGenerales.AUDIENCIA_PROCESO%>":
        	s_MENSAJE = "La audiencia no fue cancelada, ya que esta programada en Sala JAVS y ya se esta llevando a cabo.</br>Se han actualizado los datos de la audiencia";
            break;
        case "<%=ConstantesGenerales.AUDIENCIA_TERMINO%>":
        	s_MENSAJE = "La audiencia no fue cancelada, ya que esta programada en Sala JAVS y ya se llevo a cabo.</br>Se han actualizado los datos de la audiencia";
            break;
        case "<%=ConstantesGenerales.ERROR_ELIMINACION%>":
            s_MENSAJE = "La audiencia no fue eliminada, ocurri&oacute; un problema.</br>Vuelva a intentarlo m&aacute;s tarde";
            break;
        case "<%=ConstantesGenerales.EXITO_ELIMINACION%>":
            s_MENSAJE = "Se eliminó correctamente la agenda del registro del AutoLog del servidor JAVS";
            break;
        case "<%=ConstantesGenerales.NO_HAY_AUDIENCIAS%>":
            s_MENSAJE = "No se encontró la audiencia registrada en el AutoLog de JAVS";
            break;        
        case "<%=ConstantesGenerales.ERROR_SERVICIO_ELIMINACION%>":
            s_MENSAJE = "Fallo al conectar con el servidor JAVS.</br>Vuelva a intentarlo m&aacute;s tarde";
            break;
        case "<%=ConstantesGenerales.NO_ES_JAVS%>":
            s_MENSAJE = "La audiencia no se llevo a cabo en una Sala JAVS";
            break;                      
        }		
        return s_MENSAJE;
	}
	
	
   function cierraVentanaRegistrarAmparo(){
		var pantalla ="iframewindowRegistrarAmparo";
		//pantalla += idWindowIngresarProbResponsable;
		$.closeWindow(pantalla);
	}
   
	function consultaPDF(id){
		document.frmDococumentos.documentoId.value = id;
		document.frmDococumentos.submit();
	}

	function ismaxlength(obj,size){
		if (obj.value.length>size)
			obj.value=obj.value.substring(0,size);
	}	

	
	/*
	*Funcion que valida, que la hora y minuto seleccionados sean mayores o iguales
	*a la hora del servidor, para programar la audiencia, regresa true, en caso
	*verdadero, false caso contrario.
	*/
    function validaHoraAudienciaHoraActual(horaSelec, minutoSelec){

		var fecha = "";
		
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/regresaFechaYHoraDelServidor.do',
			dataType : 'xml',
			async : false,
			success : function(xml) {
				fecha = $(xml).find('fecha').text();
			}
		});
		
		//mes y anio seleccionados
		var mesSelec = $('#mes').val();
		var anioSelec = $('#anio').val();
		
		//hora, minuto, dia, mes y anio actuales
		var horaC = fecha.substring(11, 13);
		var minutoC = fecha.substring(14,16);
		var dia = fecha.substring(8, 10);
		var mesNum = fecha.substring(5,7); 
		 
		var mes = meses[ parseInt(mesNum,10) - 1];
		var anio = fecha.substring(0, 4);
		
		if (parseInt(fechaReal,10) == parseInt(dia,10) && mesSelec == mes
				&& parseInt(anioSelec,10) == parseInt(anio,10)) {
			if (parseInt(horaSelec,10) > parseInt(horaC,10)) {
				return true;
			}
			else{
				if(parseInt(horaSelec,10) == parseInt(horaC,10)){
					if(parseInt(minutoSelec,10)  >= parseInt(minutoC,10)){
						return true;
					}
					else{
						return false;
					}
				}
				else{
					return false;
				}
			}
		} 
		else {
			return true;
		}
	}

	
    function documentos(){
		jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',
			{
				url:'<%= request.getContextPath()%>/consultarDocumentosPorAudiencia.do?idAudiencia='+idAudiencia, 
				datatype: "xml" ,
				loadComplete: function(){
					var rows = jQuery("#gridDetalleFrmPrincipal").jqGrid('getGridParam','records');
					if(rows != null && rows != '' && rows != 'undefined'){
						$('#idTotalDocumentos').html(parseInt(rows));
					}else{
						$('#idTotalDocumentos').html("0");
					}
				},
			});
		$("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
	}


    /*
    *Funcion para consultar si el parametro de JAVS esta encendido en BD
    *para mostrar u ocultar los controles de JAVS de:
    *
    * Notas y Videos de JAVS
    * Eliminar agenda de JAVS 
    */
    function consultaParametroJAVS(){

		var idParametro = '<%=Parametros.CONTROLES_JAVS.ordinal()%>';
		var parametroConfirm = '0';
		
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/consultarParametro.do',
			data: 'idParametro='+ idParametro, 
			async: false,
			dataType: 'xml',
			success: function(xml){					
				parametroConfirm = $(xml).find('body').find('respuesta').text();
			}
		});

		return parametroConfirm;
	}

	
	/*
	*Funcion para mostrar u ocultar los campos de:
	* Notas y Videos de JAVS
	* Eliminar agenda de JAVS
	* en base el paramero en BD 
	*/
	function mostrarOcultarControlesJAVS(){

		if(consultaParametroJAVS() == '0'){
			$('#btnVideos').hide();
			$('#btnEliminar').hide();
		}
	}
	
	/*
	* Funcion para deshabiltar los componentes necesarios despues de  
	* finalizada la audiencia validando si la bandera 'audienciaFinalizadadeshabilitar es true'
	*/
	function deshabilitarDespuesFinalizadaAudiencia(){
			
		if(audienciaFinalizadadeshabilitar==true){
			//Pestaña Individualizacion, se ocultan todos los botones del toolbar para cada subpestaña
			$("#t_gridVictima").hide();
			$("#t_gridFiscal").hide();
			$("#t_gridDefensor").hide();
			$("#t_gridTestigo").hide();
			$("#t_gridPerito").hide();
			$("#t_gridPolicia").hide();
			//Pestaña Pruebas
			$("#t_gridDatosDePruebaPJENS").hide();
			$("#t_gridPruebaPJENS").hide();
			inhabilitaRadiosGridDatosDePrueba();
			jQuery("#gridDatosDePruebaPJENS").jqGrid('setGridParam', { ondblClickRow: function(rowid) { 
				//No hace nada el doble clic
			} })
			$('#gridDatosDePruebaPJENS').unbind('click');
			//Pestaña Resolutivos de Audiencia
			$("#agregarResolutivoBoton").hide();
			$("#eliminarResolutivoBoton").hide();
			//Pestaña Programar Audiencia, Se oculta la pestaña
			$("#calendario").hide();
			$("#tabsDetalleAudiencias-5").hide();
			//Pestaña Administrar Actuaciones de Audiencia
			$("#finalAudienciaPes").hide();
			$("#tabsDetalleAudiencias-7").hide();
			//Pestaña Notas
			$("#botonGuardarNotas").hide();
						
			deshabilitaChecksGrids();
			$("#tabsDetalleAudiencias" ).tabs('select', 0);

		}
	}
	
	/* Funcion que inhabilita los radios opcion del Grid Datos de Prueba
	* de la Pestaña Pruebas cuando el estatus de la audiencia es finalizada 
	*/
	function inhabilitaRadiosGridDatosDePrueba(){
		if(audienciaFinalizadadeshabilitar==true){
			$('input[id^="aceptado_"]').each(function(){
				id = $(this).attr("id");
				$('#'+id).unbind('click');
				$('#'+id).attr('disabled','disabled');
			});
	
			$('input[id^="rechazado_"]').each(function(){
				id = $(this).attr("id");
				$('#'+id).unbind('click');
				$('#'+id).attr('disabled','disabled');
			});
			deshabilitaRadiosGridDatosPruebaAlRecargar();
		}
	}
	
	/*
	* Funcion que deshabilita los radios del Grid de Datos de prueba 
	* cuando se recarga el grid
	*/
	function deshabilitaRadiosGridDatosPruebaAlRecargar(){
		if(audienciaFinalizadadeshabilitar==true){
			jQuery("#gridDatosDePruebaPJENS").jqGrid('setGridParam', {
				gridComplete: function() {
					//Deshabilita el radio de aceptado en el grid Datos de Prueba
					$('input[id^="aceptado_"]').each(function(){
						id = $(this).attr("id");
						$("#"+id).attr('disabled',true);
					});
					//Deshabilita el radio de rechazado en el grid Datos de Prueba
					$('input[id^="rechazado_"]').each(function(){
						id = $(this).attr("id");
						$("#"+id).attr('disabled',true);
					});
				}
			});
		}
	}
	
	/*
	* Funcion que valida si la audiencia es finalizada
	* return true: si la audiencia es finalizada
	*/
	function esFinalizadaAudiencia(){
		if(audienciaFinalizadadeshabilitar==true){
			return true;
		}else{
			return false;
		}
	}
	
	/*
	* Funcion que deshabilita los Checkbox de los grids Victima,Denunciante,Fiscal,
	* Defensor, Testigo, Perito y Policia cuando la audiencia es Cancelada o Finalizada
	*/
	function deshabilitaChecksGrids(){
		if(audienciaFinalizadadeshabilitar==true){
			//Deshabilita el checkbox de Presente en el grid Victima y Testigo
			$('input[id^="chkPre_"]').each(function(){
				id = $(this).attr("id");
				$("#"+id).attr("disabled",true);
			});
			
			//Deshabilita el checkbox de Titular en el grid Fiscal y Defensor
			$('input[id^="chkTitFunExt_"]').each(function(){
				id = $(this).attr("id");
				$("#"+id).attr("disabled",true);
			});
			
			//Deshabilita el checkbox de Presente en el grid Fiscal,Defensor,Perito,Policia
			$('input[id^="chkPreFunExt_"]').each(function(){
				id = $(this).attr("id");
				$("#"+id).attr("disabled",true);
			});
			
			//Deshabilita el checkbox de Presente en el grid Denunciante
			$('input[id^="chkPreDenu_"]').each(function(){
				id = $(this).attr("id");
				$("#"+id).attr("disabled",true);
			});
			deshabilitaCheckBoxAlRecargarGridsComplete();
		}
	}
	
	/*
	* Funcion que deshabilita los checkbox de los grids Victima,Denunciante,Fiscal,
	* Defensor, Testigo, Perito y Policia cuando se recargan los grids
	*/
	function deshabilitaCheckBoxAlRecargarGridsComplete(){
		if(audienciaFinalizadadeshabilitar==true){	
			//Deshabilita el checkbox de Presente en el grid Victima
			jQuery("#gridVictima").jqGrid('setGridParam', {
				gridComplete: function() {
					$('input[id^="chkPre_"]').each(function(){
						id = $(this).attr("id");
						$("#"+id).attr('disabled',true);
					});
				}
			});
			
			//Deshabilita el checkbox Presente en el grid Denunciante
			jQuery("#gridDenunciante").jqGrid('setGridParam', {
				gridComplete: function() {
					$('input[id^="chkPreDenu_"]').each(function(){
						id = $(this).attr("id");
						$("#"+id).attr('disabled',true);
					});
				}
			});
			
			//Deshabilita los checkbox Titular y Presente del Grid Fiscal
			jQuery("#gridFiscal").jqGrid('setGridParam', {
				gridComplete: function() {
					//Deshabilita el checkbox de Titular en el grid Fiscal
					$('input[id^="chkTitFunExt_"]').each(function(){
						id = $(this).attr("id");
						$("#"+id).attr('disabled',true);
					});
					//Deshabilita el checkbox de Presente en el grid Fiscal
					$('input[id^="chkPreFunExt_"]').each(function(){
						id = $(this).attr("id");
						$("#"+id).attr('disabled',true);
					});
				}
			});
			//Deshabilita los checkbox Titular y Presente del Grid Defensor
			jQuery("#gridDefensor").jqGrid('setGridParam', {
				gridComplete: function() {
					//Deshabilita el check box de Titular en el grid Defensor
					$('input[id^="chkTitFunExt_"]').each(function(){
						id = $(this).attr("id");
						$("#"+id).attr('disabled',true);
					});
					//Deshabilita el checkbox de Presente en el grid Defensor
					$('input[id^="chkPreFunExt_"]').each(function(){
						id = $(this).attr("id");
						$("#"+id).attr('disabled',true);
					});
				}
			});
			
			//Deshabilita el checkbox Presente del grid Testigo
			jQuery("#gridTestigo").jqGrid('setGridParam', {
				gridComplete: function() {
					$('input[id^="chkPre_"]').each(function(){
						id = $(this).attr("id");
						$("#"+id).attr('disabled',true);
					});
					
				}
			});
			//Deshabilita el checkbox Presente del grid Perito
			jQuery("#gridPerito").jqGrid('setGridParam', {
				gridComplete: function() {
					$('input[id^="chkPreFunExt_"]').each(function(){
						id = $(this).attr("id");
						$("#"+id).attr('disabled',true);
					});
					
				}
			});
			//Deshabilita el checkbox Presente del grid PoliciaMinisterial
			jQuery("#gridPolicia").jqGrid('setGridParam', {
				gridComplete: function() {
					$('input[id^="chkPreFunExt_"]').each(function(){
						id = $(this).attr("id");
						$("#"+id).attr('disabled',true);
					});
					
				}
			});
				
		}
	}
	
	/**
	* Funcion que valida que solo sean introducidos 
	* letras, letras con acentos y el caracter punto(.)
	*/
	function validaSoloLetrasYPunto(campo){
		var regexp = new RegExp(/^[A-Za-zÑñáéíóúÁÉÍÓÚäëïöüÄËÏÖÜàèìòùÀÈÌÒÙ\.\s]*$/);
		aEvaluar = campo.value;
	    if (!regexp.test(aEvaluar)) {
	    	customAlert("Caracteres invalidos en:<br>"+aEvaluar);
	        return false;
	    }
	    return true;
	}
			 
	</script>
</head>
<body>

<div id="tabsDetalleAudiencias">

	<ul>
		<li id="detalleAudiencias">
			<a href="#tabsDetalleAudiencias-1">Detalle de Audiencias</a>
		</li>
		<li>
			<a href="#tabsDetalleAudiencias-13">Relaciones Delito-Persona</a>
		</li>
		<li id="involucrados">
			<a href="#tabsDetalleAudiencias-2">Individualización</a>
		</li>
		<li id="Objetos">
			<a href="#tabsDetalleAudiencias-3">Pruebas</a>
		</li>
		<li class="tabResolitivos">
			<a href="#tabsDetalleAudiencias-4">Resolutivos de Audiencia</a>
		</li>
		<li id=calendario>
			<a href="#tabsDetalleAudiencias-5">Programar Audiencia</a>
		</li>
		
		<li id="finalAudienciaPes">
			<a href="#tabsDetalleAudiencias-7" onclick="cargaActuacionesAudiencia();">Administrar actuaciones de audiencia</a>
		</li>
				
		<li class="tabNotas">
			<a href="#tabsDetalleAudiencias-9"  onclick="consultarNotas();" >Notas</a>
		</li>
	    <li class="tabTabsAmparos">
        	<a href="#tabs-10" onclick="consultarAmparosPorExpediente()">Amparo</a>
        </li>
		<li class="tabTabsDocs">
			<a href="#tabs-11" onclick="documentos()">Documentos</a>
		</li>
		<li id="notificaciones">
			<a href="#tabNotificaciones-12">Notificaciones</a>
		</li>
		
		<li id="exhortos">
			<a href="#tabExhortos-13">Exhortos</a>
		</li>
		<li id="acumulacion">
			<a href="#tabAcumulacion-14">Acumulación</a>
		</li>		
	</ul>
	
	
	<!--Comienza Tab Detalle de audiencia-->
	<div id="tabsDetalleAudiencias-1" >
	
		<table width="1142" height="100%" border="0" align="center" cellpadding="0" cellspacing="5" class="back_generales">
		<tbody>
			<tr valign="top">
			</tr>
			<tr height="20%" valign="middle">
				<td colspan="4" valign="middle" align="center"><strong>Audiencia:
				  <input type="text"
					id="audienciaPJENS"
					style="width: 320px; border: 0; background: #DDD;"
					readonly="readonly" />
				</strong></td>
	            
			</tr>
	        <tr>
	        	<td colspan="4" align="center"><strong>Tipo de Audiencia:
	        	  <input type="text"
					id="tipoAudienciaPJENS"
					style="width: 350px; border: 0; background: #DDD;"
					readonly="readonly" />
	        	</strong></td>
	        </tr>
			<tr>
				<td width="13%" align="right">&nbsp;</td>
				<td width="8%">&nbsp;</td>
				<td width="18%">&nbsp;</td>
				<td width="25%">&nbsp;</td>
			</tr>
			<tr>
			  <td width="22%" align="right"><strong>N&uacute;mero de Caso:</strong></td>
			  <td width="17%"><input type="text"
					id="numCasoPJENS"
					style="width: 200px; border: 0; background: #DDD;"
					readonly="readonly" /></td>
				<td colspan="2" rowspan="8">
				<table id="gridAudienciasPJENC"></table>
				<div id="pager1" width="300"></div>
				</td>
			</tr>
			<tr>
			  <td align="right"><strong>N&uacute;mero de Causa:</strong></td>
			  <td><input type="text" id="numExpPJENS"
					style="width: 200px; border: 0; background: #DDD;"
					readonly="readonly"  /></td>
			</tr>
			<tr>
			  <td align="right"><strong>Car&aacute;cter: </strong></td>
			  <td><input type="text" id="caracterPJENS"
					style="width: 150px; border: 0; background: #DDD;"
					readonly="readonly" /></td>
			</tr>
			<tr>
			  <td align="right"><strong>Fecha:</strong></td>
			  <td><input type="text" id="fechaAudienciaPJENS"
					style="width: 150px; border: 0; background: #DDD;"
					readonly="readonly"  /></td>
			</tr>
			<tr>
			  <td align="right"><strong>Hora:</strong></td>
			  <td><input type="text" id="horaAudienciaPJENS"
					style="width: 150px; border: 0; background: #DDD;"
					readonly="readonly" /></td>
			</tr>
			<tr>
			  <td align="right"><strong>Sala:</strong></td>
			  <td><input type="text" id="salaPJENS"
					style="width: 150px; border: 0; background: #DDD;"
					readonly="readonly"  /></td>
			</tr>
			<tr>
			  <td align="right"><strong>Direcci&oacute;n de Sala:</strong></td>
			  <td><input type="text" id="direccionSalaPJENS"
					style="width: 150px; border: 0; background: #DDD; "
					readonly="readonly" 
					 /></td>
			</tr>
			<tr>
			  <td align="right"><strong>Ubicaci&oacute;n:</strong></td>
			  <td><input type="text" id="ubicacionPJENS"
					style="width: 150px; border: 0; background: #DDD;"
					readonly="readonly" /></td>
			</tr>
	        <tr>
	        	<td>&nbsp;</td>
	            <td>&nbsp;</td>
	            <td align="right"><strong>Juez(ces):</strong></td>
	            <td>
					<!--<input type="text" id="juezPJENS" style="width: 180px; border: 0; background: #DDD;" readonly="readonly"/>-->
					<select id="juezPJENS" style="width: 200px; border: 0; background:#DDD;"></select>
				</td>
	        </tr>
	        <tr>
	        	<td>&nbsp;</td>
	            <td>&nbsp;</td>
	            <td align="right">&nbsp;</td>
	            <td>&nbsp;</td>
	        </tr>
	        <tr>
	        	<td>&nbsp;</td>
	            <td>&nbsp;</td>
	            <td align="left">
	            	<table cellspacing="0" cellpadding="0">
						<tr>
							<td width="158" align="right">&nbsp;</td>
						</tr>
	            	</table>
	            </td>
	            <td>
	            	<table cellspacing="0" cellpadding="0">
						<tr>
						  <td width="151">&nbsp;</td>
						</tr>
					</table>
				</td>
	        </tr>
	        <tr height="300">
	        	<td>&nbsp;</td>
	            <td>&nbsp;</td>
	            <td align="left">
					<table cellspacing="0" cellpadding="0">
					  <tr>
					    <td width="158" align="right">&nbsp;</td>
					  </tr>
					</table>
				</td>
	            <td>
	            	<table cellspacing="0" cellpadding="0">
						<tr>
						  <td width="151">&nbsp;</td>
						</tr>
	            	</table>
	            </td>
	        </tr>
	    </tbody>
		</table>
	</div>
	<!--Termina Tab Detalle de audiencia-->
	
	<!--TAB PARA MOSTRAR LAS RELACIONES DELITO PERSONA-->
	<div id="tabsDetalleAudiencias-13" align="left">
		<div id="tabRelacionesDelitoPersona">
			<jsp:include page="/WEB-INF/paginas/relacionDelitoPersonaIncludePJ.jsp" flush="true"></jsp:include>
		</div>
	</div>
	
	<!--Comienza Tab Individualizacion-->
	<div id="tabsDetalleAudiencias-2">
		<div id="tabschild2" class="tabs-bottom">
				
			<ul>
				<li><a href="#tabschild2-6">V&iacute;ctima</a></li>
				<li><a href="#tabschild2-7">Denunciante</a></li>
				<li><a href="#tabschild2-1"><bean:message key="agenteMp"/></a></li>
				<li><a href="#tabschild2-2">Defensor</a></li>
				<li><a href="#tabschild2-3">Testigo</a></li>
				<li><a href="#tabschild2-4">Perito</a></li>
				<li><a href="#tabschild2-5">Polic&iacute;a Ministerial</a></li>
			</ul>
				
			<!--Comienzan  Sub Tabs para la ceja de individualizacion -->
			<div id="tabschild2-6">
				<div id="divGridVictima" >
					<table id="gridVictima" ></table>
					<div id="paginadorGridVictima"></div>
				</div>
				
				<table>
					<tr><td></br></td></tr>
					<tr><td></br></td></tr>
				</table>
				
				<div id="divGridVictimaOrg" >
					<table id="gridVictimaOrg" ></table>
					<div id="paginadorGridVictimaOrg"></div>
				</div>
				
				<table>
					<tr><td></br></td></tr>
					<tr><td></br></td></tr>
				</table>
			</div>
			
			<div id="tabschild2-7">
				<div id="divGridDenunciante" >
					<table id="gridDenunciante" ></table>
					<div id="paginadorGridDenunciante"></div>
				</div>
				
				<table>
					<tr><td></br></td></tr>
					<tr><td></br></td></tr>
				</table>
			</div>
			
			<div id="tabschild2-1">
				<div id="divGridFiscal" >
					<table id="gridFiscal" ></table>
					<div id="paginadorGridFiscal"></div>
				</div>
				
				<table>
					<tr><td></br></td></tr>
					<tr><td></br></td></tr>
				</table>
			</div>
			
			<div id="tabschild2-2">
				<div id="divGridDefensor">
					<table id="gridDefensor"></table>
					<div id="paginadorGridDefensor"></div>
				</div>
				
				<table>
					<tr><td></br></td></tr>
					<tr><td></br></td></tr>
				</table>
			</div>
			
			<div id="tabschild2-3">
				<div id="divGridTestigo">
					<table id="gridTestigo"></table>
					<div id="paginadorGridTestigo"></div>
				</div>
				
				<table>
					<tr><td></br></td></tr>
					<tr><td></br></td></tr>
				</table>
			</div>
			
			<div id="tabschild2-4">
				<div id="divGridPerito">
					<table id="gridPerito"></table>
					<div id="paginadorGridPerito"></div>
				</div>
			
				<table>
					<tr><td></br></td></tr>
					<tr><td></br></td></tr>
				</table>
			</div>
			
			<div id="tabschild2-5">
				<div id="divGridPolicia">
					<table id="gridPolicia"></table>
					<div id="paginadorGridPolicia"></div>
				</div>
				
				<table>
					<tr><td></br></td></tr>
					<tr><td></br></td></tr>
				</table>
			</div>
			
		</div>	
		<!--Espacio para los grids de individualizacion	-->
	</div>
	<!--Termina Tab Individualizacion-->
	
	<!--Comienza Tab de Objetos-->
	<div id="tabsDetalleAudiencias-3">
	
	
	
	
			<!--Comienzan sub cejas de pruebas-->
			<div id="tabschildPruebasPJENS" class="tabs-bottom">
					
				<ul>
					<li><a href="#tabschildPruebasPJENS-1">Datos de Prueba</a></li>
					<li><a href="#tabschildPruebasPJENS-2">Medios de Prueba</a></li>
					<li><a href="#tabschildPruebasPJENS-3">Prueba</a></li>
				</ul>
				
				
							<!--Comienza Sub Tab Datos de prueba  -->
							<div id="tabschildPruebasPJENS-1">
							
								<table width="1000" border="0" cellspacing="0" cellpadding="0">
								  <tr>
								    <td width="100">&nbsp;</td>
								    <td width="400">&nbsp;</td>
								    <td width="400">&nbsp;</td>
								    <td width="100">&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td colspan="2" rowspan="11">
										<table  id="gridDatosDePruebaPJENS" width="100%"></table>
										<div id="pagerGridDatosDePruebaPJENS"></div>
									</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								</table>
								
							</div>
							<!--Termina Sub Tab Datos de prueba  -->
							
							
							<!--Comienza Sub Tab Medios de prueba  -->
							<div id="tabschildPruebasPJENS-2">
							
								<table width="1000" border="0" cellspacing="0" cellpadding="0">
								  <tr>
								    <td width="100">&nbsp;</td>
								    <td width="400">&nbsp;</td>
								    <td width="400">&nbsp;</td>
								    <td width="100">&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td colspan="2" rowspan="11">
										<table  id="gridMediosDePruebaPJENS" width="100%"></table>
										<div id="pagerGridMediosDePruebaPJENS"></div>
									</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								</table>
													
							</div>
							<!--Termina Sub Tab Medios de prueba  -->
										
							<!--Comienza Sub Tab prueba  -->
							
							<div id="tabschildPruebasPJENS-3">
							
								<table width="1000" border="0" cellspacing="0" cellpadding="0">
								  <tr>
								    <td width="100">&nbsp;</td>
								    <td width="400">&nbsp;</td>
								    <td width="400">&nbsp;</td>
								    <td width="100">&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td colspan="2" rowspan="11">
										<table  id="gridPruebaPJENS" width="100%"></table>
										<div id="pagerGridPruebaPJENS"></div>
									</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								</table>
			
							</div>
							<!--Termina Sub Tab prueba  -->
							
			</div>
			<!--Terminan sub cejas de pruebas-->
		
		
	</div>
	<!--Termina Tab de Objetos-->
	
	<!--Comienza Tab Resolutivos de Audiencia-->
	<div id="tabsDetalleAudiencias-4">
		<table width="1060" align="center" height="100%" class="back_obj" border="0" cellpadding="0" cellspacing="5">
			<tbody>
			<tr>
				<td rowspan="8" width="5%">&nbsp;</td>
				<td>&nbsp;</td>
				<td colspan="2">&nbsp;</td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
			  <td width="10%" align="right" valign="top">
			  	<strong>Audiencia: </strong>
			  </td>
			  <td width="5%" valign="top" align="left">
			  	<input type="text" id="audienciaResPJENS" style="width:250px; border: 0; background:#DDD;" readonly="readonly" />
			  </td>
			  <td width="15%" align="right" valign="top">
			  	<strong>Tipo  Audiencia:</strong>
			  </td>
			  <td width="10%" valign="top" align="left">
			  	<input type="text" id=tipoAudienciaResPJENS style="width:350px; border: 0; background:#DDD;" readonly="readonly" value="Juicio Oral"/>
			  </td>
			  <td width="15%" align="left" valign="top">&nbsp;</td>
			  
	  		</tr>
			<tr>
				<td width="10%" align="right" valign="top">
					<strong>No.  Caso:</strong>
				</td>
				<td valign="top">
					<input type="text" id="numCasoResPJENS" style="width:200px; border: 0; background:#DDD;" readonly="readonly" />
				</td>
				<td align="right" valign="top">
					<strong>No. Causa:</strong>
				</td>
				<td valign="top">
					<input type="text" id="numExpResPJENS" style="width:200px; border: 0; background:#DDD;" readonly="readonly" value="000001"/>
				</td>
				<td width="15%" align="left" valign="top">&nbsp;</td>
			</tr>
	  		<tr>
	  			<td align="left">
    				<input class="btn_mediano" width="100" type="button" id="agregarResolutivoBoton" value="Agregar Registro" />
    			</td>	  			
				<td valign="top" height="200" rowspan="6" colspan="3" align="center" >
					<table  id="gridAudienciasResolutivosPJENS" width="100%"></table>
					<div id="pager5"></div>
				</td>
			</tr>
			<tr>
    			<td align="right">
    				<input type="button" class="btn_mediano" width="100" id="eliminarResolutivoBoton" value="Eliminar Registro" />
    			</td>
			</tr>
			<tr>	
				<td align="right">
					<input type="button" class="btn_mediano" width="100" id="btnVideos" value="Notas y Videos de JAVS" onclick="invocacionSolicitudJAVS()"/>
				</td>
			</tr>
			<tr>	
				<td align="right">
					<input type="button" class="btn_mediano" width="100" id="btnEliminar" value="Eliminar agenda de JAVS" onclick="eliminarAudienciaJAVS();"/>
				</td>				
			</tr>
			</tbody>		
	  	</table>
		
		<table width="1060" align="center" height="354" class="back_hechos" border="0" cellpadding="0" cellspacing="0">
	  		<tr valign="top">
  			  <td height="5" colspan="2">&nbsp;</td>
			</tr>
            <tr>
				<td width="18%" align="right">
					<ul class="botonLeyes"></ul>
				</td>
	  		</tr>	
			<tr valign="middle">
				<td width="5%" height="40" align="right">
					<input class="btn_mediano" type="button" id="consPolitica" onClick="abreCodigosLeyes('<%=TipoNorma.CONSTITUCION.getValorId()%>')" value="Constituci&oacute;n Pol&iacute;tica"/>
				</td>
			  	<td width="8%" rowspan="9" valign="middle" >
					<table id="gridLeyesCodigosPJENS" width="100%">
					</table>
		     		 <div id="pager10"></div>
				</td>
  			</tr>
			<tr>		
				<td width="5%" height="39" align="right" class="linea_derecha_gris">
					<input class="btn_mediano" type="button" id="leyesGenerales" onClick="abreCodigosLeyes('<%=TipoNorma.LEYES.getValorId()%>')" value="Leyes Generales"/>
				</td>
			</tr>
			<tr>
				<td width="5%" height="40" align="right" class="linea_derecha_gris">
					<input class="btn_mediano" type="button" id="tratados" onClick="abreCodigosLeyes('<%=TipoNorma.TRATADOS.getValorId()%>')" value="Tratados Internacionales"/>
				</td>
			</tr>
			<tr>
				<td  width="5%" height="40" align="right" class="linea_derecha_gris">
					<input class="btn_mediano" type="button" id="codigos" onClick="abreCodigosLeyes('<%=TipoNorma.CODIGOS.getValorId()%>')" value="C&oacute;digos" />
				</td>
	  		</tr>
			<tr>
				<td width="5%" height="40" align="right" class="linea_derecha_gris">
					<input class="btn_mediano" type="button" id="acuerdos" onClick="abreCodigosLeyes('<%=TipoNorma.ACUERDOS.getValorId()%>')" value="Acuerdos" />
				</td>
	  		</tr>
			<tr>
				<td width="18%" height="40" align="right" class="linea_derecha_gris">
					<input class="btn_mediano" type="button" id="circulares" onClick="abreCodigosLeyes('<%=TipoNorma.CIRCULARES.getValorId()%>')" value="Circulares" />
				</td>
	  		</tr>
			<tr>
				<td  width="18%" height="39" align="right" class="linea_derecha_gris">
					<input class="btn_mediano" type="button" id="manuales" onClick="abreCodigosLeyes('<%=TipoNorma.MANUALES.getValorId()%>')" value="Manuales" />
				</td>
	  		</tr>
			<tr>
				<td width="18%" align="right">
			  		<input class="btn_mediano" type="button" id="instructivos" onClick="abreCodigosLeyes('<%=TipoNorma.INSTRUCTIVOS.getValorId()%>')" value="Instructivos" />
				</td>
	  		</tr>	
            <tr>
				<td width="18%" align="right">
				</td>
	  		</tr>	
		</table>	
	</div>
	<!--Termina Tab Resolutivos de Audiencia-->


	<!--Comienza Tab Programar Audiencia-->
	<div id="tabsDetalleAudiencias-5">
		
		<!--Comienzan sub cejas de programar audiencia-->
		<div id="tabschild" class="tabs-bottom">
				
			<ul>
				<li><a href="#tabschild-1">Audiencia</a></li>
				<li><a href="#tabschild-2">Imputado</a></li>
				<li><a href="#tabschild-8">V&iacute;ctima</a></li>
				<li><a href="#tabschild-9">Denunciante</a></li>
				<li><a href="#tabschild-3">Testigo</a></li>
				<li><a href="#tabschild-4">Perito</a></li>
				<li><a href="#tabschild-5">Polic&iacute;a Ministerial</a></li>
				<li><a href="#tabschild-6"><bean:message key="agenteMp"/></a></li>
				<li><a href="#tabschild-7">Defensor</a></li>
				<!--<li><a href="#tabschild-3">Objetos</a></li>-->
			</ul>
			
			<!--Comienza Sub Tab para audiencia  -->
			<div id="tabschild-1">
			
				<!--<table width="1100" class="back_generales" border="0" cellspacing="0" cellpadding="0">-->
				<table width="1100" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="258">&nbsp;</td>
						<td width="178">&nbsp;</td>
						<td colspan="3" align="center" valign="bottom">
							<input type="button" class="btn_Generico" value="<<" id="btnAtrasMes" onClick="atrasAdelanteMes('atras');"/>
							<input type="text" id="mes" disabled="disabled" style="width: 70px"/>
							<input type="text" id="anio" disabled="disabled" style="width: 70px" />
							<input type="button" class="btn_Generico" value=">>" id="btnAdelanteMes" onClick="atrasAdelanteMes('adelante');"/>
						</td>
						<td width="100" nowrap="nowrap">
							<strong>Asignar Juez:</strong> 
						</td>
					 	<td colspan="2">
							<table width="100%" cellpadding="1" cellspacing="1">
								<tr>
									<td colspan="2">
										<input type="checkbox" value="true" name="juezSustituto" id="juezSustituto"/> Juez sustituto
									</td> 
								</tr>
								<tr>
									<td width="111">
										<input type="button" class="btn_Generico" value="Manualmente" id="btnAsignarJuezManual" onclick="controlJueces(false);"/>
									</td>
									<td width="125">
										<input type="button" class="btn_Generico" value="Autom&aacute;ticamente" id="btnAsignarJuezAuto" onclick="controlJueces(true);"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
		  			<tr>
		    			<td align="right" valign="bottom">
		    				<strong>Tipo de audiencia a programar:</strong>
		    			</td>
					    <td>
							<select id="tipoAudienciaProgramarAudiencia" style="width:200px;"></select>
					    </td>
		    			<td colspan="3" rowspan="7" align="center" valign="bottom">
		    				<table id="gridAgendaPJENA"></table>
		    			</td>
		    			<td colspan="3" rowspan="7">
		    				<table id="gridSolicitudDeAudienciaJuecesPJENA"></table>
		    				<div id="divGridSolicitudDeAudienciaJuecesPJENA"></div>
		    			</td>
		  			</tr>
		  			<tr>
		    			<td align="right" valign="bottom">
		    				<strong>N&uacute;mero de Caso:</strong>
		    			</td>
		    			<td align="left" valign="bottom">
		    				<input type="text" id="numCasoProgramarAudiencia" style="width: 200px; border: 0; background: #DDD;" readonly="readonly" />
		    			</td>
		  			</tr>
		  			<tr>
		    			<td align="right" valign="bottom">
		    				<strong>N&uacute;mero de Causa:</strong>
		    			</td>
		    			<td align="left" valign="bottom">
		    				<input type="text" id="numExpedienteProgramarAudiencia" style="width: 200px; border: 0; background: #DDD;" readonly="readonly"/>
		    			</td>
		  			</tr>
					<tr>
						<td align="right" valign="bottom">
							<strong>Fecha l&iacute;mite de audiencia:</strong>
						</td>
						<td align="left" valign="bottom">
							<input type="text" id="fechaLimiteProgramarAudiencia" style="width: 200px; border: 0; background: #DDD;" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td align="right" valign="bottom">
							<strong>Fecha Seleccionada:</strong>
						</td>
						<td align="left" valign="bottom">
							<input type="text" id="fechaSeleccionadaAudiencia" style="width: 200px; border: 0; background: #DDD;" readonly="readonly" />
						</td>
					</tr>
		  			<tr>
						<td align="right" valign="bottom">
							<strong>Duraci&oacute;n estimada de audiencia:</strong>
						</td>
		    			<td align="left" valign="bottom">
							<select id="duracionEstimadaProgramarAudiencia" style="width:200px;">
							   	<option value="0" >-Seleccione-</option>
			                	<option value="30">30 min.</option>
			                	<option value="60">1 hr. </option>
			                	<option value="90">1 hr. 30 min.</option>
			                	<option value="120">2 hrs.</option>
			                	<option value="150">2 hrs. 30 min.</option>
			                	<option value="180">3 hrs.</option>
			                	<option value="210">3 hrs. 30 min.</option>
			                	<option value="240">4 hrs.</option>
			                	<option value="270">4 hrs. 30 min.</option>
			                	<option value="300">5 hrs.</option>
			                	<option value="330">5 hrs. 30 min</option>
			                	<option value="360">6 hrs.</option>
			                	<option value="420">7 hrs.</option>
			                	<option value="480">8 hrs.</option>
			                	<option value="540">9 hrs.</option>
			                	<option value="600">10 hrs.</option>
			                	<option value="660">11 hrs.</option>
			                	<option value="720">12 hrs.</option>
			                	<option value="780">13 hrs.</option>
			                	<option value="840">14 hrs.</option>
							</select>
		    				<!--<input type="text" id="duracionEstimadaProgramarAudiencia" style="width: 170px; border: 0; background: #DDD;" readonly="readonly" />-->
	     				</td>
					</tr>
					<tr>
						<td align="right">
							<strong>Sala seleccionada:</strong>
						</td>
						<td>
							<input type="text" id="txtSalaSeleccionada" style="width: 200px; border: 0; background: #DDD;" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<strong>Hora de inicio:</strong>
						</td>
						<td>
							<input type="text" id="txtHoraInicioSeleccionada" style="width: 200px; border: 0; background: #DDD;" readonly="readonly" />
						</td>
						<td width="102">
							<strong>
								<input type="text" size="4" style="width: 20px; border: 0; background: #669933;" readonly />
							</strong>Disponible
						</td>
						<td width="123">
							<strong>
								<input type="text" size="4" style="width: 20px; border: 0; background: red;" readonly />
							</strong>No Disponible
						</td>
						<td width="85">
							<input type="text" size="4" style="width: 20px; border: 0; background: #CCCCCC;" readonly />Inh&aacute;bil
						</td>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="6">
						<table id="gridsTd" width="105px"></table>
						</td><td colspan="2">
							<table id="gridsImputadosAudiencia" ></table>
							<div id="pagerinv"></div>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td colspan="3">&nbsp;</td>
						<td colspan="3">&nbsp;</td>
					</tr>
		 			<tr>
					    <td>&nbsp;</td>
					    <td>&nbsp;</td>
					    <td colspan="3" align="center">
							<input type="button" id="btnGuardarAudiencia" class="btn_Generico" value="Guardar"  onclick="validaGuardarAudiencia();"/>
					    </td>
		    			<td colspan="3" align="left">
		    				<input type="button" class="btn_Generico" id="btnDesignar" onclick="mostrarAsignarSalaTemporalPJENA();" value="Designar Sala Temporal" />
		    			</td>
		  			</tr>
		  			<tr>
                           <td colspan="7">&nbsp;</td>
					</tr>
					<tr>
                           <td colspan="7">&nbsp;</td>
					</tr>
					
				</table>
				
			</div>
			<!--Termina Sub Tab para audiencia  -->
				
			<!--Comienza Sub Tab para imputados -->
			<div id="tabschild-2">
				<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			            <td>
			            	<div id="divGridImputado1">
								<table id="gridImputado1"></table>
								<div id="paginadorGridImputado1"></div>
							</div>
			            </td>
			        </tr>
			        <tr>
			            <td>&nbsp;</td>
			        </tr>
			    </table>
			</div>
			<!--Termina Sub Tab para imputados -->
			
			<!--Comienzan  Sub Tabs para la ceja de individualizacion -->
			<div id="tabschild-8">
				<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			            <td>
							<div id="divGridVictima1">
								<table id="gridVictima1"></table>
								<div id="paginadorGridVictima1"></div>
							</div>
			            </td>
			        </tr>
			        <tr>
			            <td>&nbsp;</td>
			        </tr>
			    </table>
			</div>
			
			<div id="tabschild-9">
				<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			            <td>
							<div id="divGridDenunciante1">
								<table id="gridDenunciante1"></table>
								<div id="paginadorGridDenunciante1"></div>
							</div>
			            </td>
			        </tr>
			        <tr>
			            <td>&nbsp;</td>
			        </tr>
			    </table>
			</div>
			
			<!--Comienza Sub Tab para Testigo -->
			<div id="tabschild-3">
				<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			            <td>
			            	<div id="divGridTestigo">
								<table id="gridTestigo1"></table>
								<div id="paginadorGridTestigo1"></div>
							</div>
			            </td>
			        </tr>
			        <tr>
			            <td>&nbsp;</td>
			        </tr>
			    </table>
			</div>
			<!--Termina Sub Tab para Testigo -->
			
			<!--Comienza Sub Tab para Perito -->
			<div id="tabschild-4">
				<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			            <td>
							<div id="divGridPerito">
								<table id="gridPerito1"></table>
								<div id="paginadorGridPerito1"></div>
							</div>
			            </td>
			        </tr>
			        <tr>
			            <td>&nbsp;</td>
			        </tr>
			    </table>
			</div>
			<!--Termina Sub Tab para Perito -->
			
			<!--Comienza Sub Tab para Policia Ministerial -->
			<div id="tabschild-5">
				<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			            <td>
							<div id="divGridPolicia1">
								<table id="gridPolicia1"></table>
								<div id="paginadorGridPolicia1"></div>
							</div>
			            </td>
			        </tr>
			        <tr>
			            <td>&nbsp;</td>
			        </tr>
			    </table>
			</div>
			<!--Termina Sub Tab para Policia Ministerial -->
			
			<!--Comienza Sub Tab para fiscal -->
			<div id="tabschild-6">
				<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			            <td>
							<div id="divGridFiscal1">
								<table id="gridFiscal1"></table>
								<div id="paginadorGridFiscal1"></div>
							</div>
			            </td>
			        </tr>
			        <tr>
			            <td>&nbsp;</td>
			        </tr>
			    </table>
			</div>
			<!--Termina Sub Tab para grid fiscal -->
			
			<!--Comienza Sub Tab para defensor -->
			<div id="tabschild-7">
				<table border="0" cellspacing="0" cellpadding="0">
			        <tr>
			            <td>
							<div id="divGridDefensor1">
								<table id="gridDefensor1"></table>
								<div id="paginadorGridDefensor1"></div>
							</div>
			            </td>
			        </tr>
			        <tr>
			            <td>&nbsp;</td>
			        </tr>
			    </table>
			</div>
			<!--Termina Sub Tab para grid defensor -->
				
			<!--Comienza Sub Tab para objetos 
			<div id="tabschild-3">
				<table>
					<tr>
						<td>
							<table width="200" border="0">
				 		 		<tr>
				    				<td>Objeto</td>
				    				<td>
				    					<input name="" type="text" id="campoObjeto"/>
				    				</td>
				  				</tr>
								<tr>
									<td>No. Prueba</td>
									<td>
										<input name="" type="text" id="campoPrueba"/>
									</td>
								</tr>
								<tr>
									<td>No. Cadena de Custodia</td>
									<td>
										<input name="" type="text" id="campoCadena"/>
									</td>
								</tr>
					  			<tr>
									<td>Descripcion Objeto</td>
									<td>
										<input name="" type="text" id="campoDescripciona"/>
									</td>
								</tr>
								<tr>
									<td>Estado</td>
									<td><select id="campoEstado"> </select> </td>
								</tr>
							</table>
								<input name="" type="button" value="Guardar Objeto" class="btn_modificar" onclick="guardaObjeto()"/>
						</td>
						<td>
							<table id="gridSolObjetos"></table>
							<div id="pagerGridSolObjetos" style="width: 300"></div>
						</td>
					</tr>
					<tr>
			             <td colspan="2">&nbsp;</td>
			        </tr>
				</table>
 			</div> -->
 			<!--Termina Sub Tab para objetos -->
		</div>
		<!--Termina sub cejas de programar audiencia-->
		
	</div>
	<!--Termina Tab programar audiencia-->
	
	<!--COMIENZA TAB ACTUACIONES DE AUDIENCIA-->
	<div id="tabsDetalleAudiencias-7">
		<jsp:include page="/WEB-INF/paginas/actuacionesDeAudienciaInclude.jsp" flush="true"></jsp:include>
	</div>
	<!--TERMINA TAB ACTUACIONES DE AUDIENCIA-->
	
	<!--COMIENZA TAB NOTAS-->
	<div id="tabsDetalleAudiencias-9" >
		<jsp:include page="/WEB-INF/paginas/notasAudienciaInclude.jsp" flush="true"></jsp:include>
	</div>
	<!--TERMINA TAB NOTAS-->
	
	<!--COMIENZA TAB AMPAROS-->
    <div id="tabs-10" class="tabTabsAmparos">
   		<jsp:include page="/consultarAmparo.jsp"></jsp:include>
	</div>
	<!--TERMINA TAB AMPAROS-->
	
	
	<!--COMIENZA TAB DOCUMENTOS-->
	<div id="tabs-11" class="tabTabsDocs">
		<br>
		<table id="gridDetalleFrmPrincipal"></table>
		<div id="pager1Documentos"></div>
		<form name="frmDoc3" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
		</form>
		<form name="frmDoc2" action="<%= request.getContextPath() %>/GenerarDocumentoDirecto.do" method="post">
			<input type="hidden" name="formaId" />
			<input type="hidden" name="numeroUnicoExpediente" />
		</form>
		<table id="tableDocs">
			<tr>
				<td>Total de documentos:</td>
				<td id="idTotalDocumentos">&nbsp;</td>
			</tr>
		</table>
	</div>
	<!--TERMINA TAB DOCUMENTOS-->
		
		
	<!--COMIENZA TAB NOTIFICACIONES-->
    <div id="tabNotificaciones-12" class="notificaciones">
		<jsp:include page="/WEB-INF/paginas/NotificacionesIncludeView.jsp" flush="true"></jsp:include>
	</div>
	<!--TERMINA TAB NOTIFICACIONES-->
	
		<!--COMIENZA TAB EXHORTOS-->
    <div id="tabExhortos-13" class="notificaciones">
		<jsp:include page="/WEB-INF/paginas/ExhortosGridIncludeView.jsp" flush="true"></jsp:include>
	</div>
	<!--TERMINA TAB EXHORTOS-->
	<!--COMIENZA TAB Acumulacion-->
	    <div id="tabAcumulacion-14" class="notificaciones">
			<jsp:include page="/WEB-INF/paginas/AcumulacionGridIncludeView.jsp" flush="true"></jsp:include>
		</div>
	<!--TERMINA TAB Acumulacion-->		
	
	
</div>
 
    

<!--Terminan los Tabs-->

<!--Comienza div para agregar destinatario-->
<div id="agregarDestinatario" style="display: none">
	<table width="800px" cellspacing="0" cellpadding="0">
		<tr>
			<td width="62">&nbsp;</td>
			<td colspan="5">&nbsp;</td>
		</tr>
		<tr>
			<td height="28">Nombre:</td>
			<td width="199">
				<input type="text" size="30" id="nombre" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/>
			</td>
			<td width="72">Instituci&oacute;n:</td>
			<td width="127">
				<select name="select" id="cmbInstitucion">
					<option value="Defensoria">Defensoria</option>
					<option value="Ministerio Público">Ministerio P&uacute;blico</option>
				</select>
			</td>
			<td width="143">Direcci&oacute;n Electronica:</td>
			<td width="195">
				<input type="text" size="30" id="correo"/>
			</td>
		</tr>
		<tr>
			<td height="30" >Apellido Paterno:</td>
			<td><input type="text" size="30" id="apellidoPaterno" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/></td>
			<td>Apellido Materno:</td>
			<td><input type="text" size="30" id="apellidoMaterno" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td height="28" valign="top">&nbsp;</td>
			<td colspan="5">&nbsp;</td>
		</tr>
		<tr>
			<td height="28" valign="top">Direcci&oacute;n Fis&iacute;ca:</td>
			<td colspan="5">
				<jsp:include page="ingresarDomicilioView.jsp"></jsp:include>
			</td>
		</tr>
	</table>
</div>
<!--Termina div para agregar destinatario-->

<!--Comienza Solicitar defensor-->
<div  id="divSolicitarDefensor" style="display:none">
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="2" rowspan="2">
			  	<table cellspacing="0" cellpadding="0" id="ListaImputados"></table>
				<div id="paginadorListaImputados"></div>
			</td>
    		<td>&nbsp;</td>
  		</tr>
		<tr>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		</tr>
		<tr>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
		</tr>
	</table>
</div>
<!--Termina Solicitar defensor-->

<!--Comienza Div Agregar resolutivo-->
<div  id="divAgregarResolutivo" style="display: none">
	<table width="500" border="0">
	  <tr>
	    <td width="25">&nbsp;</td>
	    <td width="450">
			<strong>Temporizador de Video:</strong>
		</td>
	    <td width="25">&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td align="center">
			<input name="text" type="text" id="tempVideo" class="timeRange" style="width:40px; border: 0; background:#DDD;" maxlength="5"/>
		</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>
			<strong>Resolutivo:</strong>
		</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td align="center">
			<textarea name="textarea" cols="50" rows="10" id="resolutivo" onkeyup="return ismaxlength(this,200)" onkeypress="return soloLetrasNPunto(event,this.id);" ></textarea>
		</td>
	    <td>&nbsp;</td>
	  </tr>
	</table>
</div>
<!--Termina Div Agregar resolutivo-->

<!-- Comienza div, para agregar funcionario externo -->
<div id="divIngresarFuncionarioExterno" style="display: none">
	<table width="415" cellspacing="0" cellpadding="0">
	    <tr>
	        <td width="144">&nbsp;</td>
	        <td width="253">&nbsp;</td>
	        <td width="16">&nbsp;</td>
	    </tr>
	    <tr>
	        <td colspan="3" align="center"><strong>Datos del Funcionario Externo </strong></td>
	    </tr>
	    <tr>
	        <td align="right"><strong>Seleccionar Funcionario:</strong></td>
	        <td>
	            <select id="cbxFuncionarioExtPJENS" style="width:240px"></select>
	        </td>
	        <td>&nbsp;</td>
	    </tr>
	    <tr>
	        <td align="right"><strong>Nombre:</strong></td>
	        <td align="left">
	            <input type="text" class="" size="40" maxlength="40" id="nombreFuncionarioExtPJENS" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/>
	        </td>
	        <td>&nbsp;</td>
	    </tr>
	    <tr>
	        <td align="right"><strong>Apellido Paterno:</strong></td>
	        <td align="left">
	            <input type="text" class="" size="40" maxlength="40" id="apPatFuncionarioExtPJENS" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/>
	        </td>
	        <td>&nbsp;</td>
	    </tr>
	    <tr>
	        <td align="right"><strong>Apellido Materno:</strong></td>
	        <td align="left">
	            <input type="text" class="" size="40" maxlength="40" id="apMatFuncionarioExtPJENS" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/>
	        </td>
	        <td>&nbsp;</td>
	    </tr>
		<tr>
	        <td align="right"><strong>Clave:</strong></td>
	        <td align="left">
	            <input type="text" size="10" maxlength="9" id="claveFuncionarioExtPJENS" onKeyPress="return solonumeros(event);" onblur="validaSoloNumeros(this);"/>
	        </td>
	        <td>&nbsp;</td>
	    </tr>
	    <tr>
	        <td align="right">&nbsp;</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	    </tr>
	</table>
</div>
<!-- Termina div, para agregar funcionario externo -->

<!--Comienza Form para abrir documento-->
<form name="frmDoc" action="<%= request.getContextPath()%>/abrirPDF.do" method="post">
	<input type="hidden" name="idLey" value=""/>
	<input type="hidden" name="nombreArchivo" value=""/>					
</form>
<!--Termina Form para abrir documento-->

<!---------------------------------------------------Comienzan divs para ventanas modales------------------------------------------------------------------>
	
	<!--Comienza div agregar dato de prueba-->
	<div  id="divAgregarDatoDePrueba" style="display: none">
	
		<table width="440" cellspacing="0" cellpadding="0" border="0">
	      <tr>
	        <td width="5">&nbsp;</td>
	        <td width="230" align="right">&nbsp;</td>
	        <td width="200">&nbsp;</td>
	        <td width="5">&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	            <strong>Tipo de dato de prueba:</strong>
	        </td>
	        <td>
	            <select id="tipoDatoPruebaPJENS" style="width:200px">
	              <option value="0">-Seleccione-</option>
	              <option value="1">Documento</option>
	              <option value="2">Objeto</option>
	            </select>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	            <div id="etiTipoObjetoDatoPruebaPJENS">
	                <strong>Tipo de Objeto:</strong>
	            </div>
	        </td>
	        <td>
	            <div id="divCbxTipoObjetoDatoPruebaPJENS">
	                <select id="cbxTipoObjetoDatoPruebaPJENS" style="width:200px">
	              	</select>
	            </div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiNombreDatoPruebaPJENS">
	        		<strong>Nombre del dato:</strong>
	        	</div>
	        </td>
	        <td>
	        	<div id="divTxtNombreDatoPruebaPJENS">
	        		<input type="text" id="txtNombreDatoPruebaPJENS"  maxlength="30" size="30" style="width:210px"/>
	        	</div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiNumIdeDatoPruebaPJENS">
	            	<strong>N&uacute;mero de identificaci&oacute;n:</strong>
	            </div>
	        </td>
	        <td>
	        	<div id="divTxtNumeroIdeDatoPruebaPJENS">
	        		<input type="text" id="txtNumeroIdeDatoPruebaPJENS" maxlength="20" style="width:210px"/>
	        	</div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiRccDatoPruebaPJENS">
	            	<strong>Reg. Cadena de custodia:</strong>
	            </div>
	        </td>
	        <td>
	        	<div id="divTxtRccDatoPruebaPJENS">
	        		<input type="text" id="txtRccDatoPruebaPJENS" maxlength="30" size="30" style="width:210px"/>
	        	</div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiDescripcionPJENS">
	        		<strong>Descripci&oacute;n:</strong>
	        	</div>
	        </td>
	        <td>
	        	<div id="divTxtAreaDescripcionDatoPruebaPJENS">
	        		<textarea cols="33" rows="6" id="txtAreaDescripcionDatoPruebaPJENS" onkeyup="return ismaxlength(this,200)" style="width:215px"></textarea>
	        	</div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	    </table>
	    
	</div>
	<!--Termina div agregar dato de prueba-->
	
	<!--Comienza div rechazar dato de prueba-->
	<div id="divRechazarDatoDePruebaPJENS" style="display: none">
		<table width="400" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="14">&nbsp;</td>
	        <td width="374">&nbsp;</td>
	        <td width="12">&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td height="200" align="center" valign="middle">
	        	<textarea id="motivoRechazoDatoPrueba" cols="60" rows="12"></textarea>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	      </tr>
	    </table>
	</div>
	<!--Termina div rechazar dato de prueba-->
	
	
	<!--Comienza div Relacionar Medios de Prueba-->
	<div id="divRelacionarMediosDePruebaPJENS" style="display: none">
		<table width="700" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="150">&nbsp;</td>
		    <td width="300">&nbsp;</td>
		    <td colspan="2">&nbsp;</td>
		  </tr>
		  <tr>
		    <td height="250" colspan="4">
		    	<table  id="gridRelacionarMediosDePruebaPJENS" width="100%"></table>
				<div id="pagerGridRelacionarMediosDePruebaPJENS"></div>
		    </td>
		  </tr>
		</table>
	</div>
	<!--Relacionar div Relacionar Medios de Prueba-->
	
	<!--Comienza div Relacionar Medios de Prueba-->
	<div id="divAsociarDatoDePruebaPJENS" style="display: none">
		<table width="700" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="150">&nbsp;</td>
		    <td width="300">&nbsp;</td>
		    <td colspan="2">&nbsp;</td>
		  </tr>
		  <tr>
		    <td height="250" colspan="4">
		    	<table  id="gridAsociarDatoDePruebaPJENS" width="100%"></table>
				<div id="pagerGridAsociarDatoDePruebaPJENS"></div>
		    </td>
		  </tr>
		</table>
	</div>
	<!--Relacionar div Relacionar Medios de Prueba-->
	
	<!--Comienza div agregar Medio de prueba-->
	<div  id="divAgregarMedioDePrueba" style="display: none">
		
		<table width="440" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="5">&nbsp;</td>
	        <td width="230" align="right">&nbsp;</td>
	        <td width="200">&nbsp;</td>
	        <td width="5">&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<strong>Tipo de Medio de Prueba:</strong>
	        </td>
	        <td>
	            <select name="tipoMediaPruebaPJENS" id="tipoMediaPruebaPJENS" style="width:200px">
	              <option value="0">-Seleccione-</option>
	              <option value="1">Documento</option>
	              <option value="2">Persona</option>
	            </select>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiSubTipoDocumentoMedioPruebaPJENS">
			    	<strong>*Subtipo del documento:</strong>
		        </div>
<!--	            <div id="etiCalidadPersonaMedioPruebaPJENS">-->
<!--			    	<strong>Calidad:</strong>-->
<!--			    </div>-->
	        </td>
	        <td>
	        	<div id="divSubTipoDocumentoMedioPruebaPJENS">
	                <select id="cbxSubTipoDocumentoMedioPruebaPJENS" style="width:200px">
	                  <option value="0">-Seleccione-</option>
	                  <option value="2">Archivo de audio</option>
	                  <option value="1">Archivo de texto</option>	                  
	                  <option value="3">Archivo de video</option>
	                  <option value="4">Im&aacute;genes/Fotograf&iacute;as</option>
	                </select>
	            </div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiNombreDocumentoMedioPruebaPJENS">
			    	<strong>*Nombre del documento:</strong>
			    </div>
	        </td>
	        <td>
	        	<div id="divTxtNombreDocumentoMedioPruebaPJENS">
			    	<input type="text" id="txtNombreDocumentoMedioPruebaPJENS" maxlength="20" style="width:200px"/>
			    </div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	       		<div id="etiNumeroIdeDocumentoMedioPruebaPJENS">
			    	<strong>*N&uacute;mero de identificaci&oacute;n:</strong>
			    </div>
	        </td>
	        <td>
	        	<div id="divNumeroIdeDocumentoMedioPruebaPJENS">
			    	<input type="text" id="txtNumeroIdeDocumentoMedioPruebaPJENS"  maxlength="20" style="width:200px"/>
			    </div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiAdjuntarDocumentoMedioPruebaPJENS">
			    	<span class="au av ra rc ta" ><strong>*Adjuntar:</strong></span>
			    </div>
	        </td>
	        <td>
	        	<div id="divTxtAdjuntarDocumentoMedioPruebaPJENS">
					<div id="divAdjuntarDoc" class="au av ra rc ta">
						
				        	<form id="medioPruebaForm" name="medioPruebaForm" 
				        	action="<%= request.getContextPath() %>/agregarMedioPrueba.do" method="post" enctype="multipart/form-data">
								<input type="file" name="archivoAdjunto" id="archivoPorSubir">
								<input type="hidden" name="datoPruebaId"/>
								<input type="hidden" name="audienciaId"/>
								<input type="hidden" name="tipoMedioPrueba"/>
								<input type="hidden" name="subTipoMedioPrueba"/>
								<input type="hidden" name="nombreDoc"/>
								<input type="hidden" name="numIdDoc"/>
								<input type="hidden" name="refUbicacionFisica"/>
								<input type="hidden" name="descDocumento"/>
								<input type="hidden" name="numeroExpediente"/>
							</form>
					</div>
			    </div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiRefUbicacionDocumentoMedioPruebaPJENS">
			    	<strong>Ref. ubicaci&oacute;n f&iacute;sica:</strong>
			    </div>
	        </td>
	        <td>
	        	<div id="divTxtRefUbicacionFisicaMedioPruebaPJENS">
			    	<input type="text" id="txtRefUbicacionFisicaMedioPruebaPJENS" style="width:200px"/>
			    </div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	        <td align="right">
	        	<div id="etiDescripcionDocMedioPruebaPJENS">
			    	<strong>*Descripci&oacute;n:</strong>
			    </div>
	        </td>
	        <td>
	        	<div id="divTxtAreaDescripcionDocMedioPruebaPJENS">
			    	<textarea cols="31" rows="5" id="txtAreaDescripcionDocMedioPruebaPJENS"></textarea>
			    </div>
	        </td>
	        <td>&nbsp;</td>
	      </tr>
	    </table>
					
	</div>
	<!--Termina div agregar Medio de prueba Documento-->
	
<!--Terminan divs para ventanas modales de pruebas-->
	<form name="frmDococumentos" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
		<input type="hidden" name="documentoId" />
	</form>
	
	<!-- div para el alert dinamico -->
	<div id="dialog-Alert" style="display: none">
		<table align="center">
			<tr>
				<td align="center"><span id="divAlertTexto"></span></td>
			</tr>
		</table>
	</div>
	
	<!-- div para el alert dinamico antes de cerrar ventana -->
	<div id="dialog-AlertApplet" style="display: none">
		<table align="center">
			<tr>
	  			<td>
		    		<div align="center">Introduzca la ruta donde se alojaran los videos:</div>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td>
	    			<div align="center"><input type="text" id="dir" style="width:300px" tabindex="1" maxlength="300"/></div>
	    		</td>
	    	</tr>
     	</table>			
	</div>
	
	<!--Comienza div Relacionar Involucrados a la audiencia siguiente-->
	<div id="divRelacionarInvolucradosPorCalidadAudienciaSig" style="display: none" align="center">
		<table width="700" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td height="250" colspan="4" align="center">
				<table  id="gridRelacionarInvolucradosPorCalidadAudienciaSig" width="90%"></table>
				<div id="pagerGridRelacionarInvolucradosPorCalidadAudienciaSig"></div>
		    </td>
		  </tr>
		</table>
	</div>
	<!--Relacionar div Relacionar Medios de Prueba-->
	    	
	
	<form id="frmRecargaVisor" 
		  name="frmRecargaVisor" action="<%=request.getContextPath()%>/recargaVisor.do"
		  method="post" enctype="multipart/form-data">
		  
		<input type="hidden" name="idAudiencia" />
		<input type="hidden" name="idVisor" />
		<input type="hidden" name="idAudienciaSiguiente" />
	</form>
</body>
</html>