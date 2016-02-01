<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusTurno"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@page import="mx.gob.segob.nsjp.model.Forma"%>
<html>
<head>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.calidad.Calidades" %>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades" %>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.objeto.Objetos" %>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.CategoriasActividad" %>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
	 <%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
	 <%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.expediente.OrigenExpediente"%>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros"%>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError" %>
	 <%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
	 <%@page import="mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento"%>
	 <%@ page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
	 <%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
	 
	 
	<% 
	   	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
		
		String rolUsuarioSesion = "";
		Long idRolActivo = 0L;
		Long confInstitucionId = 0L;
		
		if (usuario != null && usuario.getRolActivo() != null){
			rolUsuarioSesion = usuario.getRolActivo();
			idRolActivo = usuario.getRolACtivo().getRol().getRolId();
		}
		
		if(usuario != null && usuario.getInstitucion() != null && usuario.getInstitucion().getConfInstitucionId() != null){
			confInstitucionId = usuario.getInstitucion().getConfInstitucionId();
		}
	%>
	 
	 
	<!--Hoja de estilos de Layout-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	
	<!--Hojas de estilos de los combos multiselect-->
	<!-- link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/prettify.css" /-->
	
	<!--Hoja de estilos ultrasist-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	
	<!--Hoja de estilos windows engine (frames)-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
	
	<!--Hojas de estilos para los componentes UI de Jquery-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
	
	<!--Hoja de estilos para el grid-->
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	
	<!--scripts de java script-->
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-ui-1.8.11.custom.min.js"></script>
	
	<!--script de windows engine (frames)-->
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	
	<!--script de jquery UI-->
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/prettify.js"></script>
	
	<!--script de los combos multiselect-->
	<!-- script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery.multiselect.js"></script-->
	
	<!--scripts del gird-->
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jqgrid/jquery.jqGrid.min.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>				
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	
	<script type="text/javascript">
		$.ajaxSetup({ cache: false });
		
		var CONTEXT_ROOT = '<%= request.getContextPath()%>';
		
	   /*
		* Identificadores de los frames ingresar calidades
		*/
		//Permite mostrar en pantalla Probable Responsable o Probable participe en base al Rol del usuario
		var etiquetaProbableProp = '<bean:message key="probableResponsableTitulo"/>';
		var msjProbableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
		var contextoPagina = "${pageContext.request.contextPath}";
		var idWindowIngresarVictima = 1;	
		var idWindowIngresarTraductor = 1;	
		var idWindowIngresarQuienDetuvo = 1;	
		var idWindowIngresarRepresentanteLegal = 1;
		var idWindowIngresarTestigo = 1;
		var idWindowIngresarTutor = 1;
		var idWindowIngresarProbResponsable = 1;
		var idWindowIngresarContactoDeUnaOrganizacion = 1;
		var idWindowIngresarSentenciadoReinsertar = 1;
		var idWindowIngresarDenunciante = 1;
		var idWindowConsultarProbResponsable = 1;
		var idWindowConsultarDenunciante = 1;
		var idWindowConsultarTestigo = 1;
		var idWindowConsultarTraductor = 1;		
		var idWindowConsultarContactoDeUnaOrganizacion = 1;
		var idWindowModificarDenunciante = 1;
		var idWindowSolicitudTranscripcion =1;
		//var idWindowModificarRepresentanteLegal=1;
		var idWindowIngresarHechos = 1;
		var tipoExpediente = 0;
		var idRolActivo = <%=idRolActivo%>;
		var confInstitucionId = <%=confInstitucionId%>;
		var tieneSubConclusion = false;
		
	   /*
		* Identificadores de los frames ingresar Objetos y evidencias
		*/
		var idWindowIngresarEquipoDeComputo = 1;
		var idWindowIngresarEquipoTelefonico = 1;
		var idWindowIngresarArma = 1;
		var idWindowIngresarExplosivo = 1;
		var idWindowIngresarSustancia = 1;
		var idWindowIngresarAnimal = 1;
		var idWindowIngresarVehiculo = 1;
		var idWindowIngresarAeronave = 1;
		var idWindowIngresarEmbarcacion = 1;
		var idWindowIngresarNumerario = 1;
		var idWindowIngresarVegetal = 1;
		var idWindowIngresarDocumentoOficial = 1;
		var idWindowIngresarJoya = 1;
		var idWindowIngresarObraDeArte = 1;
		var idWindowIngresarOtros = 1;
		var idWindowIngresarPericial = 1;
		var idWindowAsociarIndividuo = 1;
		var iframewindowAbrirteoria = 1;
		
		//Variable para los grids
		var reloadGridDelito=false;
		var validaDelito=false;

		var idExpediente;
		var idInvolucrado = 100;
		//manda 0 por default al ingresar un probable responsable si viene de atpenal 
		//cambia a 1 si se ingresa desde coordinador AMP
		var muestraDetenido=0;
		var numeroExpediente="";
		var idNumeroExpedienteOp="";
		var idNumeroExpedienteConsul="";
		var idExpedienteop="";
		var isDelitoSaved=false;
		var pantallaSolicitada=0;
		var datosXML;
		var numerogeneralCaso= parent.numerocaso;
		var ingresoDenuncia="false";
		//id para las ventanas relacionadas a cadena de custodia
		var idWindowAsntarRegCadCus=1;

		var criterioOportinidadOp=0;
		var actuacion=0;
		
		var idWindowGenerarNotas=1;
		var idDefensor="";
	    var nombreDefensor="";
	    var num="";
	    var idWindowVisorMedidasCautelaresPJENC=1;
	    
	    var idWindowGenConvenio=1;
		var deshabilitarCampos = false;

		//bandera para deshabilitar los campos en la consutla cuando el usuario es policia ministerial
		var deshabilitarCamposPM=false;
		var idExpedienteDeli="";
		//variable para la carga de documentos de visitaduria
		var primeraVezGridDocumentosDigitales = true;
		
		var numeroCaso="";
		var idRelacionDelito="";
		var existeDelitoGraveEnExpediente = "";
		var flagIndexProcView=0;
		
		var banderaFac=false;
		var idIframe = 0;
		
		var validaTipoExpedienteReporte='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getValidaTipoExpedienteReporte() %>';
		var validaDelitoGrave='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getValidaDelitoGrave() %>';
		var extensionesPermitidasAudio = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getExtensionesPermitidasAlAdjuntarAudio() %>';
		var extensionesPermitidasImagen = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getExtensionesPermitidasAlAdjuntarImagen() %>';
		
		var ATPENAL = 3; //1
		var COORDINADOR_JAR = 6; //2
		var AGENTE_MP = 7; //3
		var COORDINADOR_AMP = 8; //4
		var FACILITADOR = 5;
		var POLICIA_MINISTERIAL = 9; //6
		var COORDINADOR_VISITADOR = 42; // coordinadorVIS - 7
		var VISITADOR = 4; //8  
		var COORDINADOR_AMP_GENERAL = 57; //10;
		var POLICIA_MINISTERIAL_DENUNCIA=64; //60
		var COORDINADOR_AT=58; //61
		var ENCARGADO_CAUSA=16;
		var idWindowPantallaActuaciones = 1;

		var visualizaPestanaJar = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getValidaPestanasJar()%>';
		var esModuloConsultaDeExpedientes = '<%= request.getParameter("esModuloConsultaDeExpedientes")%>';
		
		var visualizaMenuAsignarExps = <%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAutoAsignarExpedientes()%>;
		
		var confirmarCierreVentana ='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getMostrarMensajeConfirmacionEnDocumento() %>';
		
		var esModoConsulta = '<%= request.getParameter("esModoConsulta")%>'
		var esExpedienteNoAtendido = '<%= request.getParameter("esExpedienteNoAtendido")%>'
		//Bandera que permite cosultar los involucrados solo una vez, 
		//ya sea al dar click en la pestania de Involucrados o al seleccionar
		//la actucion 'Dirigir a unidad de fiscales investigadores'
		var contadorCargaInvolucrados = 0; 
		//Permite consultar el Parametro NumExpAlterno
		var valorParametroNumExpAlterno = 0;
                
                cargaActuaciones(1);
		//Comienza funcion on ready del documento
		$(document).ready(function() {
                    
                   
                        
			valorParametroNumExpAlterno = consultaParametroNumExpAlterno();
			
			//Permite abrir el visor de solo consulta
			if(idRolActivo === '<%=Roles.PROCURADOR.getValorId()%>' 
				|| idRolActivo === '<%=Roles.SUBPROCURADOR.getValorId()%>'
				|| idRolActivo === '<%=Roles.DIRECTOR_GENERAL.getValorId()%>'
				|| idRolActivo === '<%=Roles.DIRECTOR_UI.getValorId()%>'){
				esModoConsulta = "true";
			}
			
			//On ready
			if(confirmarCierreVentana !==null && (confirmarCierreVentana === "1" || confirmarCierreVentana===1)) {
				confirmarCierreVentana = true;
			}else{
				confirmarCierreVentana = false;
			}
			
			idIframe = '<%= request.getParameter("idIframe")%>';
			var auxiliar='<%= request.getParameter("flagIndexProcView")%>';
			
			if(auxiliar!==null){flagIndexProcView=auxiliar;}
			
			numeroExpediente='<%= request.getAttribute("numeroExpediente")%>';

			if(numeroExpediente === 'null'){
				numeroExpediente='<%= request.getSession().getAttribute("numExpConsul")%>';				
				if(numeroExpediente === 'null'){
					numeroExpediente='<%= request.getParameter("numeroExpediente")%>';
				}
			}
			
			pantallaSolicitada='<%= request.getParameter("pantallaSolicitada")%>';
			if(pantallaSolicitada === 'null'){
				pantallaSolicitada='<%= request.getSession().getAttribute("pantallaSolicitada")%>';
			}
			
			ingresoDenuncia='<%= request.getParameter("ingresoDenuncia")%>';
			
			//Configura texto de tabs y botones
			$("#tapProbableResposable").html(etiquetaProbableProp);
			$("#btnNuevoProbResponsable").val(etiquetaProbableProp);
			 

			idNumeroExpedienteOp='<%= request.getParameter("idNumeroExpedienteop")%>';
			idExpedienteop='<%= request.getParameter("idExpedienteop")%>';
			if(isEmpty(idExpedienteop))
			{
				idExpedienteop='<%= request.getSession().getAttribute("idExpedienteConsulop")%>';
			}
			idExpedienteDeli=idExpedienteop;
			if(idNumeroExpedienteOp === 'null'){
				idNumeroExpedienteOp='<%= request.getSession().getAttribute("idExpedienteConsul")%>';
			}
			
			idNumeroExpedienteConsul='<%= request.getParameter("idNumeroExpedienteop")%>';
			if(idNumeroExpedienteConsul === 'null'){
				idNumeroExpedienteConsul='<%= request.getParameter("idNumeroExpediente")%>';
			
			}

			//INICIA OPTIMIZACION
			$("#tabInvolucrados").click("click", function() {
				$('#tabInvolucrados').addClass("cargando");
				cargarInvolucradosExpediente(idNumeroExpedienteConsul);
        		$('#tabInvolucrados').removeClass("cargando");
			});
			
			cargarHechoExpediente(idNumeroExpedienteConsul);
			
			$("#tapResumen").click("click", function() {
		    	$('#tapResumen').addClass("cargando");
		    	
		    	if(valorParametroNumExpAlterno === '1'){
					buscarNumerosDeExpediente();
				}
		    	
				existenAmparosEnExpediente();
				datosGenerales();
        		$('#tapResumen').removeClass("cargando");
			});
			
			$("#tapRelacionarInfoDeExp").one("click", function() {
				cargaCatalogosDeCategoriasDeRelacion();
			});
			
			$("#tapPericiales").one("click", function() {
				cargaGridSolicitidesPericialesEnviadasPorExpediente();
				cargaGridSolicitidesPericialesRespondidasPorExpediente();
				cargaGridSolicitidesPericialesRegresadasPorExpediente();
			});
			
			$("#tapDelitoYRelaciones").one("click", function() {
		    	$('#tapDelitoYRelaciones').addClass("cargando");				
				//Carga grid con el catalogo de los delitos
				cargaGridDelitos();
				//consultamos las actividades dependiendo de los delitos del expediente
		   		muestraActividadesSugeridasEnConsultaExpediente();
        		$('#tapDelitoYRelaciones').removeClass("cargando");
			});
			
			$("#tabDocumentos").one("click", function() {
				jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
					url:'<%=request.getContextPath()%>/consultarDocumentos.do?idExpedienteop='+idNumeroExpedienteConsul, 
					datatype: "xml",
					colNames:['Area del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento', 'Documento', 'Es Parcial'],
					colModel:[ 	{name:'area',index:'da', width:200},
								{name:'FechaActividad',index:'fechaActividad', width:170},							
								{name:'NombreActividad',index:'nombreActividad', width:400},
					           	{name:'Tipo',index:'tipo', width:155}, 
								{name:'Nombre',index:'nombre', width:255},
					           	{name:'Fecha',index:'fecha', width:170},
					           	{name:'Documento',index:'documento', width:170},
					           	{name:'EsParcial',index:'esParcial', hidden:true}
								],
					pager: jQuery('#pager1Documentos'),
					rowNum:10,
					rowList:[10,20,30,40,50,60,70,80,90,100],
					autowidth: false,
					width:1100,
					sortname: 'turno',
					viewrecords: true,
					id: 'divgrid',
					onSelectRow: function(id){
						var retd = jQuery("#gridDetalleFrmPrincipal").jqGrid('getRowData',id);
						var titulo = retd.Nombre;
						if(retd.EsParcial){
							noEsParcial = retd.EsParcial.indexOf('false');
							if(noEsParcial > 0){//No es parcial
								if(retd.Tipo.indexOf('Archivo de audio') > 0){
									abreFrameAudio(id,titulo,'documentoId');
								}else{
									consultaPDF(id);
								}
							}
							else{//Es parcial
								var activiDocu=0;
								$.ajax({
							    	type: 'POST',
							    	url: '<%=request.getContextPath()%>/cargarActividadGuardadoParcial.do',
							    	async: false,
							    	data: {
							    		documento:id
							    	},
							    	dataType: 'xml',
							    	success: function(xml){
							    		activiDocu=$(xml).find('long').text();
							    	}
								});
								idWindowPantallaActuaciones++;
								$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
				    		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?documentoId='+id+'&numeroUnicoExpediente='+numeroExpediente+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&esTransaccional='+true+'" width="1140" height="400" />');
				    		    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
								
							}
						 }
						
						},
					sortorder: "desc"
				}).navGrid('#pager1Documentos',{edit:false,add:false,del:false});
				$("#gview_gridDetalleFrmPrincipal .ui-jqgrid-bdiv").css('height', '500px');
			});
			

			//$( "#dialogDos:ui-dialog" ).dialog( "destroy" );
			$( "#dialogModosYGrados:ui-dialog" ).dialog( "destroy" );
			
			
			//grid de las alertas
			jQuery("#gridDetalleAlertas").jqGrid({ 
				url:'<%=request.getContextPath()%>/consultarAlertas.do?idExpedienteop='+idNumeroExpedienteConsul, 
				datatype: "xml", 
				colNames:['Fecha y Hora','Descripción'], 
				colModel:[ 	{name:'FechaHora',index:'fechaHora', width:160}, 
				           	{name:'Descripcion',index:'descripcion', width:450}
						],
				pager: jQuery('#pagerGridDetalleAlertas'),
				rowNum:10,
				rowList:[10,20,30,40,50,60],
				autowidth: false,
				width:800,
				sortname: 'turno',
				viewrecords: true,
				async:true,
				id: 'divgrid',
				onSelectRow: function(id){
					//consultaPDF(id);
					},
				sortorder: "desc"
			}).navGrid('#pagerGridDetalleAlertas',{edit:false,add:false,del:false});
			
			
			//cargarObjetosExpediente('<%= request.getParameter("idNumeroExpediente")%>');
			ocultaMuestraTabVisor("tabTabsCriterio",0);

			 $( "#tabs" ).tabs();
			 $( "#tabschild" ).tabs();
			 //$( "#tabschild2" ).tabs();
			 $( "#tabschild3" ).tabs();
			 $( "#tabschild4" ).tabs();
			 $( "#tabschild5" ).tabs();
			 $( "#tabschild7" ).tabs();
			 $( "#tabschild9" ).tabs();
			 $( "#tabschild16" ).tabs();
			 $( "#tabschild17" ).tabs();
				 
			 $('#idTeoriaCaso').hide();
			 //$('#idbotoncarpeta').hide();
			 
			 $('#tdCbxAgentesCoorUI').hide();
			 $('#tdCbxAgentesCoorUI1').hide();
			 
			 $('#tdCbxAgentesCoorJAR').hide();
			 $('#tdCbxAgentesCoorJAR1').hide();
			 
			 $('#idAsignarAgenteMp').hide();
			 $("#idAsignarFacilitador").hide();
			 $("#idReasignarFacilitador").hide();
			 $("#idRadiosBUt").hide();
			 $('#idsNumerosDelExpediente').hide();
			 ocultaMuestraTabVisor("tabTabsVisitaduria",0);
			 	
			 
			 
//			 $("#tapActuaciones").click(function() {
//				 cargarRelacionesRegistradas();
//				 $('#cbxCategoriaRelacion').attr('selectedIndex', 0);
//				 $('#cbxTipoRelacion').attr('selectedIndex', 0);
//				 $('#cbxPrimerElemento').attr('selectedIndex', 0);
//				 $('#cbxSegundoElemento').attr('selectedIndex', 0);
//			 });
//			 
			// opcion uno es para la pantalla de Atencion temprana penal
			if(pantallaSolicitada === ATPENAL){
				console.log("Pantalla ATPENAL");  
				// Recargar el grid principal del menú intermedio
				try{
					window.parent.activaExpediente();					
				}catch (e) {
		                }
				
				ocultaMuestraTabVisor("tabTabsCadCus",0);
				ocultaMuestraTabVisor("tabTabsPeri",0);
				ocultaMuestraTabVisor("tabTabsPolMin",0);
				ocultaMuestraTabVisor("tabTabsCadCus",0);
				ocultaMuestraTabVisor("tabTabsAudiencias",0);
				ocultaMuestraTabVisor("tabTabsQuienDetuvo",0);				
				ocultaMuestraTabVisor("tabTabsCriterio",0);
				ocultaMuestraTabVisor("tabTabsAlertas",0);
				ocultaMuestraTabVisor("tabTabsAmparos",0);
				
				$('#btnAccAudioVideo').hide();
				$('#btnAccAudiencia').hide();
				$('#btnAccTranscripcionAudiencia').hide();
				$('#btnAccServiciosPericiales').hide();
				$('#btnAccInvestigacionMinisterial').hide();
				$('#btnAccApoyoPericial').hide();
				$('#btnAccGenerarConvenio').hide();

			}else if(pantallaSolicitada === COORDINADOR_AT){//Coordinador de Atencion Temprana
                                console.log("Pantalla COORDINADOR_AT"); 
				deshabilitarCamposPM=true;
				deshabilitarCampos=true;
				ocultaMuestraTabVisor("tabTabsCadCus",0);
				ocultaMuestraTabVisor("tabTabsPeri",0);
				ocultaMuestraTabVisor("tabTabsPolMin",0);
				ocultaMuestraTabVisor("tabTabsCadCus",0);
				ocultaMuestraTabVisor("tabTabsAudiencias",0);
				ocultaMuestraTabVisor("tabTabsQuienDetuvo",0);				
				ocultaMuestraTabVisor("tabTabsCriterio",0);
				ocultaMuestraTabVisor("tabTabsAlertas",0);
				ocultaMuestraTabVisor("tabTabsAmparos",0);
				
				$('#btnAccAudioVideo').hide();
				$('#btnAccAudiencia').hide();
				$('#btnAccTranscripcionAudiencia').hide();
				$('#btnAccServiciosPericiales').hide();
				$('#btnAccInvestigacionMinisterial').hide();
				$('#btnAccApoyoPericial').hide();
				$('#btnAccGenerarConvenio').hide();
			
			}else if(pantallaSolicitada === COORDINADOR_JAR){//coordinador JAR
				console.log("Pantalla COORDINADOR_JAR"); 
				cargaAgenteJAR();
				
				ocultaMuestraTabVisor("tabTabsCriterio",0);
				ocultaMuestraTabVisor("tabTabsAlertas",0);
				ocultaMuestraTabVisor("tabTabsAmparos",0);
				ocultaMuestraTabVisor("tabTabsPeri",0);
				ocultaMuestraTabVisor("tabTabsPolMin",0);
				ocultaMuestraTabVisor("tabTabsAudiencias",0);
				ocultaMuestraTabVisor("tabTabsCadCus",0);

				$('#tdCbxAgentesCoorJAR').show();
				$('#tdCbxAgentesCoorJAR1').show();
				$("#idAsignarFacilitador").show();

				banderaFac='<%= request.getSession().getAttribute("banderaFac")%>';
				if(banderaFac === 'true'){				
					$("#idAsignarFacilitador").hide();
					//$("#idReasignarFacilitador").show();
					$("#cbxAgentesCoorJAR").hide();
					<%session.setAttribute("banderaFac", false);%>					
				}
				else{
					$("#idAsignarFacilitador").show();	
					$("#idReasignarFacilitador").hide();
					$("#cbxAgentesCoorJAR").show();
				}

				if(visualizaPestanaJar===1 || visualizaPestanaJar==='1'){
//					ocultaMuestraTabVisor("tabTabsHechos",0);
//					ocultaMuestraTabVisor("tabTabsInv",0);
//					ocultaMuestraTabVisor("tabTabsDelito",0);						
					ocultaMuestraTabVisor("tabTabsObjs",0);
				}
                                
			}else if(pantallaSolicitada === AGENTE_MP){//agentemp
				console.log("Pantalla AGENTE_MP");
				// Recargar el grid principal del menú intermedio
				//Solo si es consulta
				try{
					if(ingresoDenuncia==='false'){
						window.parent.regresaGrid();
					}
				}catch(e){
				};

				muestraDetenido=1;

				 $("#tapPoliciaMinister").one("click", function() {
					cargaActuacionesPolMinisterial();
					cargaGridSolicitidesPoliMinister();
				 });

				ocultaMuestraTabVisor("tabTabsPeri",1);
				ocultaMuestraTabVisor("tabTabsPolMin",1);
				ocultaMuestraTabVisor("tabTabsAudiencias",1);
				ocultaMuestraTabVisor("tabTabsCriterio",0);
				
				$("#tabs").tabs("option", "selected", 2);
				$('#idTeoriaCaso').show();
				//$('#idbotoncarpeta').show();
				if(valorParametroNumExpAlterno === '1'){
					$('#idsNumerosDelExpediente').show();	
				}
			}else if(pantallaSolicitada === COORDINADOR_AMP){//Coordinador Amp
				console.log("Pantalla COORDINADOR_AMP");
				muestraDetenido=1;
			
				//cargamos las actuaciones para la tab de policia ministerial
				 $("#tapPoliciaMinister").one("click", function() {
					cargaActuacionesPolMinisterial();
					cargaGridSolicitidesPoliMinister();
				 });
			
				ocultaMuestraTabVisor("tabTabsPeri",1);
				ocultaMuestraTabVisor("tabTabsPolMin",1);
				ocultaMuestraTabVisor("tabTabsAudiencias",1);
				ocultaMuestraTabVisor("tabTabsCriterio",0);

 				$("#tabs").tabs("option", "selected", 2);
				$('#idTeoriaCaso').show();
				//$('#idbotoncarpeta').show();
				if(valorParametroNumExpAlterno === '1'){
					$('#idsNumerosDelExpediente').show();	
				}
				
				//Si la bandera esta deshabilitada, entonces se habilita la opcion para asignar expediente
				if(visualizaMenuAsignarExps === 0){
					cargaAgenteMp();
					$('#tdCbxAgentesCoorUI').show();
					$('#tdCbxAgentesCoorUI1').show();
					$('#idAsignarAgenteMp').show();
				}
			} else if(pantallaSolicitada === FACILITADOR){//facilitador
				console.log("Pantalla FACILITADOR");
				ocultaMuestraTabVisor("tabTabsCriterio",0);
				ocultaMuestraTabVisor("tabTabsAlertas",0);
				ocultaMuestraTabVisor("tabTabsAmparos",0);
				
				ocultaMuestraTabVisor("tabTabsPeri",0);
				ocultaMuestraTabVisor("tabTabsPolMin",0);
				ocultaMuestraTabVisor("tabTabsAudiencias",0);
				ocultaMuestraTabVisor("tabTabsCadCus",0);
				
				if(visualizaPestanaJar===1 || visualizaPestanaJar==='1'){
//					ocultaMuestraTabVisor("tabTabsHechos",0);
//					ocultaMuestraTabVisor("tabTabsInv",0);
//					ocultaMuestraTabVisor("tabTabsDelito",0);						
					ocultaMuestraTabVisor("tabTabsObjs",0);
				}
			} else if(pantallaSolicitada === POLICIA_MINISTERIAL){//policia ministerial
                                console.log("Pantalla POLICIA_MINISTERIAL");
				deshabilitarCamposPM=true;
				deshabilitarCampos=true;
				
				muestraDetenido=1;
				
				 $("#tapPoliciaMinister").one("click", function() {
						cargaActuacionesPolMinisterial();
						cargaGridSolicitidesPoliMinister();
				 });
				
				ocultaMuestraTabVisor("tabTabsPeri",1);
				ocultaMuestraTabVisor("tabTabsPolMin",1);
				ocultaMuestraTabVisor("tabTabsAudiencias",0);
				ocultaMuestraTabVisor("tabTabsCadCus",1);
				ocultaMuestraTabVisor("tabTabsDocs",1);
				
				$(".btn_modificar").hide();
				$(".btn_grande").hide();
				$("#cbxAccionesTab9").hide();
				$("#tdCbxAccionesTab9").hide();
				$("#tdCbxAccionesTab9act").hide();
				//$("#idInvestiga").attr("disabled","");
 			    $("#cbxAccionesTab9").attr("disabled","");
				//$("#idInvestiga").attr("disabled","");
				$("#tabs-9").css('display','');
				$("#tabs-10").css('display','');
				$("#tabs-8").css('display','');
				
			} else if(pantallaSolicitada === POLICIA_MINISTERIAL_DENUNCIA){//policia ministerial de denuncia
				//ESte if es especcifico para la pantalla de nueva denuncia en policia ministerial
				//deshabilitarCamposPM=true;
				//deshabilitarCampos=true;
                                console.log("Pantalla POLICIA_MINISTERIAL_DENUNCIA");
				muestraDetenido=1;
				
				$("#tapPoliciaMinister").one("click", function() {
						cargaActuacionesPolMinisterial();
						cargaGridSolicitidesPoliMinister();
				});
				
				ocultaMuestraTabVisor("tabTabsPeri",1);
				ocultaMuestraTabVisor("tabTabsPolMin",1);
				ocultaMuestraTabVisor("tabTabsAudiencias",0);
				ocultaMuestraTabVisor("tabTabsCadCus",1);
				ocultaMuestraTabVisor("tabTabsDocs",1);
				ocultaMuestraTabVisor("tabTabsAlertas",0);
				
				
				$(".btn_modificar").hide();
				$(".btn_grande").hide();
				$("#cbxAccionesTab9").hide();
				$("#tdCbxAccionesTab9").hide();
				$("#tdCbxAccionesTab9act").hide();
				//$("#idInvestiga").attr("disabled","");
				 
 			    $("#cbxAccionesTab9").attr("disabled","");
				//$("#idInvestiga").attr("disabled","");
				
			}else if(pantallaSolicitada === COORDINADOR_VISITADOR){//Coordinador Visitador
				console.log("Pantalla COORDINADOR_VISITADOR");
				var Area='<%= request.getParameter("idArea")%>';
				
				deshabilitarCamposPM=true;
				deshabilitarCampos=true;
				
				cargaInformacionDeResumenVisitaduira();

				ocultaMuestraTabVisor("tabTabsVisitaduria",1);
				
				if(Area==="55" || Area==="44" || Area==="11"){
					ocultaMuestraTabVisor("tabTabsPeri",0);
					ocultaMuestraTabVisor("tabTabsPolMin",0);
					ocultaMuestraTabVisor("tabTabsAudiencias",0);
					ocultaMuestraTabVisor("tabTabsCadCus",0);
					ocultaMuestraTabVisor("tabTabsDocs",0);
					ocultaMuestraTabVisor("tabTabsCriterio",0);					
				}else{
					ocultaMuestraTabVisor("tabTabsPeri",1);
					ocultaMuestraTabVisor("tabTabsPolMin",1);
					ocultaMuestraTabVisor("tabTabsAudiencias",1);
					ocultaMuestraTabVisor("tabTabsCadCus",1);
					ocultaMuestraTabVisor("tabTabsDocs",1);
					ocultaMuestraTabVisor("tabTabsCriterio",0);
				}
					$(".btn_modificar").hide();
					$(".btn_grande").hide();
					
				$("#btnCadCusNuevaCadCus").hide();

			}else if(pantallaSolicitada === VISITADOR){//Visitador
				console.log("Pantalla VISITADOR");
				//Carga los documentos adjuntos que se adjuntaran a la solicitud de periciales
				cargaGridDocumentosDigitales();
				
				var Area='<%= request.getParameter("idArea")%>';
				var estatusVisitador='<%= EstatusExpediente.AUDITANDO.getValorId() %>';
				//Realiza el cambio de estatus solo si la consulta proviene de la bandeja de visitador
				//si la consulta proviene del modulo de "Busqueda de expediente" no se registra
				if(esModuloConsultaDeExpedientes !== "true")
					registraEstatus(estatusVisitador);
				
				deshabilitarCamposPM=true;
				deshabilitarCampos=true;
				
				cargaInformacionDeResumenVisitaduira();

				ocultaMuestraTabVisor("tabTabsVisitaduria",1);

				if(Area==="55" || Area==="44" || Area==="11"){
					ocultaMuestraTabVisor("tabTabsPeri",0);
					ocultaMuestraTabVisor("tabTabsPolMin",0);
					ocultaMuestraTabVisor("tabTabsAudiencias",0);
					ocultaMuestraTabVisor("tabTabsCadCus",0);
					ocultaMuestraTabVisor("tabTabsDocs",0);
					ocultaMuestraTabVisor("tabTabsCriterio",0);
				}else{
					ocultaMuestraTabVisor("tabTabsPeri",1);
					ocultaMuestraTabVisor("tabTabsPolMin",1);
					ocultaMuestraTabVisor("tabTabsAudiencias",1);
					ocultaMuestraTabVisor("tabTabsCadCus",1);
					ocultaMuestraTabVisor("tabTabsDocs",1);
					ocultaMuestraTabVisor("tabTabsCriterio",0);
				}
				ocultaMuestraTabVisor("tabTabsAmparos",0);
					$(".btn_modificar").hide();
					$(".btn_grande").hide();
			} else if(pantallaSolicitada === COORDINADOR_AMP_GENERAL){//Coordinador Amp General
				console.log("Pantalla COORDINADOR_AMP_GENERAL");
				muestraDetenido=1;
		
				$("#tabs").tabs("option", "selected", 2);
				$('#idTeoriaCaso').show();
				//$('#idbotoncarpeta').show();
				$("#ingresarHechos").hide();
				$('#crearDenunciante').hide();
				$('#nuevaVictima').hide();
				$("#btnNuevoProbResponsable").hide();
				$("#nuevoTestigo").hide();
				$("#nuevoTraductor").hide();
				$("#quienDetuvo").hide();	
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
				$("#nuevoOtros").hide();			
				$("#nuevoPericial").hide();			
				$("#btnCadCusNuevaCadCus").hide();
				$("#btnTranscripcionAudiencia").hide();
				$("#tablaAcuseNoPenal").show();
				$("#botonGuardarNotas").hide();
				
				ocultaMuestraTabVisor("tabTabsPeri",1);
				ocultaMuestraTabVisor("tabTabsAudiencias",1);
				ocultaMuestraTabVisor("tabTabsCriterio",0);
				ocultaMuestraTabVisor("tabTabsAccionesHijo",0);
				ocultaMuestraTabVisor("tabTabsPolMin",1);	
				$('#idTeoriaCaso').hide();
				$('#idbotoncarpeta').hide();
				$('#idInvestiga').hide();
				
				if(idRolActivo === '<%=Roles.PROCURADOR.getValorId()%>' || idRolActivo === '<%=Roles.SUBPROCURADOR.getValorId()%>'
					|| idRolActivo === '<%=Roles.DIRECTOR_GENERAL.getValorId()%>' || idRolActivo === '<%=Roles.DIRECTOR_UI.getValorId()%>'){
					$('#btnGuardarDelitosAg').hide();
				}
				
				if(idRolActivo === '<%=Roles.DIRECTOR_GENERAL.getValorId()%>'){
					$("#pasar").hide();
					$("#pasarD").hide();
					ocultaMuestraTabVisor("tabTabsAcciones",0);
					$("#btnAdjuntarDocumento").hide();
				}
			}else if (pantallaSolicitada === ENCARGADO_CAUSA){
                                console.log("Pantalla ENCARGADO_CAUSA");
				//Institucion PJ, Encargado de Causa
				recargarBandejaAccPenalPriv();
				
				//Oculta las cejas inecesarias
				ocultaMuestraTabVisor("tabTabsVisitaduria",0);
				ocultaMuestraTabVisor("tabTabsCriterio",0);
				//ocultaMuestraTabVisor("tabTabsDocs",0);
				ocultaMuestraTabVisor("tabTabsPeri",0);
				ocultaMuestraTabVisor("tabTabsPolMin",0);
				ocultaMuestraTabVisor("tabTabsCadCus",0);
				ocultaMuestraTabVisor("tabTabsAudiencias",0);
				ocultaMuestraTabVisor("tabTabsAlertas",0);
				ocultaMuestraTabVisor("tabTabsCriterio",0);
				ocultaMuestraTabVisor("tabTabsAmparos",0);

				//Oculta las sub cejas Traductor y quien detuvo
				ocultaMuestraTabVisor("tabTabsTraductor",0);
				ocultaMuestraTabVisor("tabTabsQuienDetuvo",0);
			}
			
			//oculta la pestaña de Alertas
			ocultaMuestraTabVisor("tabTabsAlertas",0);
			
			if(ingresoDenuncia==='true'){
				$("#tapNotas").one("click", function() {
					consultarNotas();
				});
				var titulo='<%= request.getSession().getAttribute("numExpConsul")%>';
				if(titulo === undefined || titulo === null || titulo === "null"){
					titulo=numeroExpediente;
				}
				
				numeroCaso='<%= request.getAttribute("numeroCasoConsul")%>';

				if(numeroCaso === undefined || numeroCaso === null || numeroCaso === "null"){
					numeroCaso = '- PENDIENTE -';
				}
				titulo=titulo+" No. Caso: "+numeroCaso;
				window.parent.tituloVentana(titulo);
			}
			else{
				numeroCaso='<%= request.getParameter("numeroGeneralCaso")%>';
			}
						
			$( ".tabs-bottom .ui-tabs-nav, .tabs-bottom .ui-tabs-nav > *" )
				.removeClass( "ui-corner-all ui-corner-top" )
				.addClass( "ui-corner-bottom" );
			

			// Asociación de eventos
			$("#nuevaVictima").click(creaNuevaVictima);
			$("#nuevoTraductor").click(creaNuevoTraductor);
			$("#quienDetuvo").click(creaQuienDetuvo);
			$("#nuevoRepresentanteLegal").click(creaNuevoRepresentanteLegal);
			$("#nuevoProbResponsable").click(creaNuevoProbResponsable);
			$("#crearDenunciante").click(crearDenunciante);
			$("#nuevoTestigo").click(creaNuevoTestigo);
			$("#nuevoContactoDeUnaOrganizacion").click(creaNuevoContactoDeUnaOrganizacion);
			$("#ingresarSentenciadoReinsertar").click(crearSentenciadoReinsertar);
			$("#nuevoTutor").click(creaNuevoTutor);
			$("#consultarDenuncianteUno").click(modificarDenunciante);
			$("#consultarRepresentanteLegalUno").click(modificarRepresentanteLegal);
			$("#consultarTraductorUno").click(modificarTraductor);
			$("#consultarProbResponsableUno").click(consultarProbResponsable);
			$("#consultarContactoDeUnaOrganizacionUno").click(consultarContactoDeUnaOrganizacion);
			$("#consultaVictimaUno").click(ConsultarVictimaUno);
			$("#consultaVictimaDos").click(ConsultarVictimaDos);
			$("#ingresarHechos").click(ingresarHechos);
			$("#nuevoEquipoDeComputo").click(creaNuevoEquipoDeComputo);
			$("#nuevoEquipoTelefonico").click(creaNuevoEquipoTelefonico);
			$("#nuevaArma").click(creaNuevaArma);
			$("#nuevoExplosivo").click(creaNuevoExplosivo);
			$("#nuevaSustancia").click(creaNuevaSustancia);
			$("#nuevoAnimal").click(creaNuevoAnimal);
			$("#nuevoVehiculo").click(creaNuevoVehiculo);
			$("#nuevaAeronave").click(creaNuevaAeronave);
			$("#nuevaEmbarcacion").click(creaNuevaEmbarcacion);
			$("#nuevoNumerario").click(creaNuevoNumerario);
			$("#nuevoVegetal").click(creaNuevoVegetal);
			$("#nuevoDocumentoOficial").click(creaNuevoDocumentoOficial);
			$("#nuevaJoya").click(creaNuevaJoya);
			$("#nuevaObraDeArte").click(creaNuevaObraDeArte);
			$("#nuevoOtros").click(creaNuevoOtros);
			$("#nuevoPericial").click(creaNuevoPericial);
			$("#asociarIndividuo").click(asociarIndividuo);
			//$("#btnAdminMediCaute").click(mostrarVentanaInvolucradosCausa);
			$("#btnTranscripcionAudiencia").click(muestraSolicitudTranscripcion);
					
			//deshabilitamos el boton de Denuncia del tab Acciones
			$("#btnAccDenuncia").hide();
			$('a[name*="padre"]').click(function(event){
				var elem = $(this).next();

				if(elem.is('ul')){
					event.preventDefault();
					$('#menu ul:visible').not(elem).slideUp();
					elem.slideToggle();
					}
			});
			
			$("#consultaTestigo").click(consultarTestigo);

			var opNuevaDenuncia=<%=request.getAttribute("idNuevaDenuncia")%>;

			if(opNuevaDenuncia===1){
				$('#tabschild-op').show();
				$('#tdCbxAgentesCoorUI1').hide();
				$('#tdCbxAgentesCoorUI').hide();
				$('#idAsignarAgenteMp').hide();
			}else{
				$('#tabschild-op').hide();
			}

			//cargamos el combo para las actuaciones de policia ministerial
			$("#cbxAccionesTab9").dblclick(function(e){
				seleccionaActuacionPolMin();
			}); 
			
			//llenamos los combos de UI e IE de la pestaña de Acciones
			cargaInstitucionesExternas();
			mostraDivGenerarOficioCanalizacion(0);
			
			$("#btnGenerarAcciones").click(muestraDivInformativoCanalizacion);
			$("#btnCanalizaAJR").click(muestraDivInformativoCanalizacion);
			$("#spanGralJAR,#spanGralUI,#spanGralIE,#btnGenerarAcciones").hide();

			$("#btnGardadoDefini").click(validaGuardadoDefinitivo);
			$("#btnAccQuerella").hide();
			//seteamos los listener de los radios para la relacion de Delitos por Person o por Delito
			$("#rdbMenuInterRelDelXPersona").bind("click",ocultaMuestraTblsRelacionarDelitos);
			$("#rdbMenuInterRelDelXDelito").bind("click",ocultaMuestraTblsRelacionarDelitos);
			$("#rdbMenuInterRelDelXTodos").bind("click",ocultaMuestraTblsRelacionarDelitos);

			if(idRolActivo !== '<%=Roles.AGENTEMP.getValorId()%>'){
				datosGenerales();
			}
			
			
			//Seteo listener cadena de custodia
			$("#btnCadCusNuevaCadCus").click(asentarRegCadenaCustodia);
			$("#btnCadCusConsultaCadCus").click(consultarRegCadenaCustodia);
			//FIN Seteo listener cadena de custodia
			//consultamos las notas del expediente
			
			if(idNumeroExpedienteOp !== '')
			{
				$("#tapNotas").one("click", function() {
					consultarNotas();
				});
			}
			
			
			$("#tapObjetos").one("click", function() {
				/*SECCION PARA DECLARAR LOS GRIDS DE LOS OBJETOS EN EL VISOR INTERMEDIO*/
				jQuery("#gridObjsVehiculo").jqGrid({ 
					url:'<%= request.getContextPath()%>/ConsultaObjetosGridVisorXTipo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=<%= Objetos.VEHICULO.getValorId() %>', 
					datatype: "xml", 
					colNames:['Veh&iacute;culo','Folio de cadena de custodia', 'No. de casos asociados'], 
					colModel:[ 	{name:'Vehiculo',index:'vehiculon', width:100}, 
								{name:'FolioCadCus',index:'folioCadCus', width:150}, 
								{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:false}, 
							],
					pager: jQuery('#pagerGridObjsVehiculo'),
					rowNum:10,
					rowList:[10,20,30,40,50,60],
					width:600,
					caption:"VEHÍCULOS",
					sortname: 'Clave',
					async:true,
					viewrecords: true,
					id: 'gridObjsVehiculo',
					ondblClickRow: function(id){
						consultarVehiculo(id);
					},
					sortorder: "desc"
				});
				
				//grid de arma
				jQuery("#gridObjsArma").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
					datatype: "xml", 
					colNames:['Arma','Folio de cadena de custodia', 'No. de casos asociados'], 
					colModel:[ 	{name:'Arma',index:'arman', width:150}, 
								{name:'FolioCadCus',index:'folioCadCus', width:150}, 
								{name:'NoExpedientes',index:'noExpedientes', width:200,hidden:true}, 
							],
					pager: jQuery('#pagerGridObjsArma'),
					rowNum:10,
					rowList:[10,20,30,40,50,60],
					width:600,
					caption:"ARMA",
					sortname: 'Clave',
					async:true,
					viewrecords: true,
					id: 'gridObjsArma',
					ondblClickRow: function(id){
						consultarArma(id);
						},
					sortorder: "desc"
				});
				
				//Grid de equipo de computo
				jQuery("#gridObjsEquipoComputo").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
					datatype: "xml", 
					colNames:['Equipo de Cómputo','Folio de cadena de custodia', 'No. de casos asociados'], 
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
					async:true,
					viewrecords: true,
					id: 'gridObjsVehiculo',
					ondblClickRow: function(id){
						consultarEquipoComputo(id);
						},
					sortorder: "desc"
				});
				
				//Grid de Equipo Telefonico
				jQuery("#gridObjsEquipoTelefonico").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
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
					async:true,
					viewrecords: true,
					id: 'gridObjsEquipoTelefonico',
					ondblClickRow: function(id){
						consultarEquipoTelefonico(id);
						},
					sortorder: "desc"
				});
				
				//Grid de Explosivo
				jQuery("#gridObjsExplosivo").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
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
					async:true,
					viewrecords: true,
					id: 'gridObjsExplosivo',
					ondblClickRow: function(id){
						consultarExplosivo(id);
						},
					sortorder: "desc"
				});
				
				//Grid de Sustancia
				jQuery("#gridObjsSustancia").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
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
					async:true,
					viewrecords: true,
					id: 'gridObjsSustancia',
					ondblClickRow: function(id){
						consultarSustancia(id);
						},
					sortorder: "desc"
				});
				
				//Grid de Animal
				jQuery("#gridObjsAnimal").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
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
					async:true,
					viewrecords: true,
					id: 'gridObjsAnimal',
					ondblClickRow: function(id){
						consultarAnimal(id);
						},
					sortorder: "desc"
				});
				
				//Grid de Aeronave
				jQuery("#gridObjsAeronave").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
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
					async:true,
					viewrecords: true,
					id: 'gridObjsAeronave',
					ondblClickRow: function(id){
						consultarAeronave(id);
						},
					sortorder: "desc"
				});
				
				//Grid de Embarcacion
				jQuery("#gridObjsEmbarcacion").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
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
					async:true,
					viewrecords: true,
					id: 'gridObjsEmbarcacion',
					ondblClickRow: function(id){
						consultarEmbarcacion(id);
						},
					sortorder: "desc"
				});
								
				//Grid de Numerario
				jQuery("#gridObjsNumerario").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
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
					async:true,
					viewrecords: true,
					id: 'gridObjsNumerario',
					ondblClickRow: function(id){
						consultarNumerario(id);
						},
					sortorder: "desc"
				});
				
				//Grid de Vegetal
				jQuery("#gridObjsVegetal").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
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
					async:true,
					viewrecords: true,
					id: 'gridObjsVegetal',
					onSelectRow: function(id){
						consultarVegetal(id);
						},
					sortorder: "desc"
				});
				
				//Grid de Documento Oficial
				jQuery("#gridObjsDocumentoOficial").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
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
					async:true,
					viewrecords: true,
					id: 'gridObjsDocumentoOficial',
					ondblClickRow: function(id){
						consultarDocumentoOficial(id);
						},
					sortorder: "desc"
				});
				
				//Grid de Joya
				jQuery("#gridObjsJoya").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
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
					async:true,
					viewrecords: true,
					id: 'gridObjsJoya',
					ondblClickRow: function(id){
						consultarJoya(id);
						},
					sortorder: "desc"
				});
				
				//Grid de Obra de Arte
				jQuery("#gridObjsObraDeArte").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
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
					async:true,
					viewrecords: true,
					id: 'gridObjsObraDeArte',
					ondblClickRow: function(id){
						consultarObraDeArte(id);
						},
					sortorder: "desc"
				});
				
				//Grid de Otros
				jQuery("#gridObjsOtros").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
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
					async:true,
					viewrecords: true,
					id: 'gridObjsOtros',
					ondblClickRow: function(id){
						consultarOtros(id);
						},
					sortorder: "desc"
				});
				
				//Grid de Periciales
				jQuery("#gridObjsPericial").jqGrid({ 
					url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=3',
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
					async:true,
					viewrecords: true,
					id: 'gridObjsPericial',
					ondblClickRow: function(id){
						consultarPericial(id);
						},
					sortorder: "desc"
				});
				
				/*FIN - SECCION PARA DECLARAR LOS GRIDS DE LOS OBJETOS EN EL VISOR INTERMEDIO*/
			});
			
			
									
			//Instruccion pensada solo para el caso de policia ministerial
			if(deshabilitarCampos === true){
				$("#rdbMenuInterRelDelXTodos").attr("disabled","");
				$("#rdbMenuInterRelDelXPersona").attr("disabled","");
				$("#rdbMenuInterRelDelXDelito").attr("disabled","");
				$(":enabled").attr('disabled','disabled');
				$("#cbxAccionesTab").attr('disabled','');
                                $("#cbxOficiosTab").attr('disabled','');
				$("#botonGuardarNotas").show();
				$("#botonGuardarNotas").attr('disabled','');
				$("#idRadiosBUt").hide();
			}
			$("#btnTemporal").attr("disabled","");
			
			tipoExpediente = consultaTipoExpediente();
			
			if(tipoExpediente === '<%=OrigenExpediente.REPORTE.getValorId()%>'){
				cambiaTextoVisor(2);
			} else{ 
				cambiaTextoVisor(1);
			}

			//Visor de Resumen General "cargarDatosGenerales"
			$("#denuncianteslb").html('Denunciantes:');
			$("#victimaslb").html('V&iacute;ctimas:');
			$("#probableResponsableslb").html('<bean:message key="plProbalbeResponsableTitulo"/>');
			
			if(pantallaSolicitada === COORDINADOR_JAR || pantallaSolicitada === FACILITADOR ){//coordinador JAR
				//De acuerdo a la bandera de petanaJAR, se muestran otras leyendas, caso contario la misma version
				if(visualizaPestanaJar===1 || visualizaPestanaJar==='1'){
					$("#denuncianteslb").html('<bean:message key="denunciantes"/>'+':');
					$("#victimaslb").html('<bean:message key="victimas"/>'+':');
					$("#probableResponsableslb").html('<bean:message key="probableResponsableJAR"/>');
					
					$("#testigosTR").hide();
					$("#traductoresTR").hide();
					$("#quienDetuvoTR").hide();
					
					//Ocultar los objetos del resumen
					$("#objetosTD").hide();
					$("#resumenObjetoslb").hide();
					
					//Ajustar ancho de los renglones
					$("#espacioEntrelbsEstatus").attr('width', '600px');
					$("#involucradosTD").attr('width', '800px');

					//Ocular algunos espacios que no se utilizan
					$("#espacioBlanco").hide();
					
					//$("#objetosTD").css("display", "none");
					//$("#resumenObjetoslb").css("display", "none");
					
					$("#amparo_s").hide();
				}
			}
			
			//Siempre visible el boton de adjuntar documento independiente del actor
			$("#btnAdjuntarDocumento").attr("disabled",false);

			//controlNumeroExpedienteAlterno();
			if(pantallaSolicitada === POLICIA_MINISTERIAL){
				$("#idInvestiga").attr('disabled','');
				$("#btnCadCusNuevaCadCus").attr('disabled','');
				$("#btnCadCusConsultaCadCus").attr('disabled','');
			}
			
			if(idRolActivo === '<%=Roles.COORDINADORAMP.getValorId()%>'
					&& visualizaMenuAsignarExps !== 0){
				esModoConsulta = "true";
				//Adjuntar documentos
				$("#btnAdjuntarDocumento").hide();
				//Elaborar teoría del caso
				$("#idTeoriaCaso").hide();
				//Las actuaciones se bloquean
				$("#cbxAccionesTab").attr('disabled','disabled');
                                $("#cbxOficiosTab").attr('disabled','disabled');
				//Las actuaciones de Policia ministerial se bloquean
				$("#cbxAccionesTab9").attr('disabled','disabled');
				//Transcripcion de audiencia
				$("#btnTranscripcionAudiencia").hide();
			}
			
			if(esModoConsulta != null && esModoConsulta === "true"){
				deshabilitarCamposPM=true;
				deshabilitarCampos=true;
				ocultaElementosDelVisor();
			}
			
			if(idRolActivo === '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>' && esExpedienteNoAtendido != null && esExpedienteNoAtendido ==="true"){
					 $("#btnReasignarUIEExpediente").show();
			}
			

			//
			if(idRolActivo !== '<%=Roles.COORDINADORAMP.getValorId()%>'){
				$('#tdCbxAgentesCoorUI1').hide();
				$('#tdCbxAgentesCoorUI').hide();
				$('#idAsignarAgenteMp').hide();
			}	
			
			fechaServidor= consultaFechaHoraMaximaServer();
			fechaMax=getFechaMaximaServerHechos(fechaServidor);
			timeMax=getHoraMaximaServer(fechaServidor);
			
			if(idRolActivo === '<%=Roles.AGENTEMP.getValorId()%>' || idRolActivo === '<%=Roles.COORDINADORAMP.getValorId()%>' ){
				$("#conclusionTab").show();
				
				//se oculta el combo de tipo SubConclusion
				if(!tieneSubConclusion){
					$('#cbxTipoSubConclusion').hide();
					cargaTipoConclusion();
				}
				cargaCalendarioTipoConclusion();
				
				
			}else
				$("#conclusionTab").hide();	
			
			consultarConclusion();
                        
                 $("input[name='rdActuaciones']").change(function() {
                    var sinCatuie = $(':radio[name=rdActuaciones]:checked').val();
                    cargaActuaciones(sinCatuie);
                });   
                
                $("#cbxAccionesTab").delegate('a','click',function(event) {
                    $(this).addClass( "ui-selectable" );
                    if($( this ).hasClass( "ui-selectable" )){
                        seleccionaActuacion($(this));
                    }
                    $( this ).removeClass( "ui-selectable" );
                    
                });
                        
                $("#cbxOficiosTab").delegate('a','click',function(event) {
                    $(this).addClass( "ui-selectable" );
                    if($( this ).hasClass( "ui-selectable" )){
                        seleccionaActuacion($(this));
                    }
                    $( this ).removeClass( "ui-selectable" );
                    
                });
                
                $("#tapActuaciones").click("click", function() {
                    
                    cargaActuaciones(1);
		});
           });             
                         
		//Termina funcion on ready del documento
		
		/*
			*Funcion para traer la fecha y hora del servidor en el formato : YYYY-MM-DD HH:MI:SS
			*/
			function consultaFechaHoraMaximaServer()
			{
				var fecha="";
				   $.ajax({
					     type: 'POST',
					     url: '<%=request.getContextPath()%>/regresaFechaYHoraDelServidor.do',
						 dataType: 'xml',
						 async: false,
						 success: function(xml){
							fecha= $(xml).find('fecha').text();
						  }
						});
				return fecha;
			}
			/*
			 * Funcion para regresar la fecha maxima obtenida desde el servidor
			 * fechaCompleta - cadena con el siguiente formato : YYYY-MM-DD HH:MI:SS
			 * regresa una cadena con la fecha en el formato : DD/MM/YYYY
			 */
			function getFechaMaximaServerHechos(fechaCompleta)
			{
				var arrFechaHora=fechaCompleta.split(" ");
				var digitosFecha=arrFechaHora[0].split("-");
				return digitosFecha[2]+'/'+digitosFecha[1]+'/'+digitosFecha[0];
			}

			/*
			 * Funcion para regresar la hora maxima obtenida desde el servidor
			 * fechaCompleta - cadena con el siguiente formato : YYYY-MM-DD HH:MI:SS
			 * regresa una cadena con la hora en el formato : HH:MMAM o HH:MMPM
			 */
			function getHoraMaximaServer(fechaCompleta)
			{
				var arrFechaHora=fechaCompleta;
				var horaC=arrFechaHora[1].substring(0,5);
				
				return horaC;
			}

		function cerrarVentanaDocumento(id){
			var pantalla ="iframewindowGenerarDocumento";
			pantalla += id;
			$.closeWindow(pantalla);
		}
		
		//SECCION para llenar los grids de los objetos en el visor intermedio
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
		//FIN SECCION para llenar los grids de los objetos en el visor intermedio
		
		/*
		*Funcion para madar consultar la informacion de resumen de visitaduria
		*/
		function cargaInformacionDeResumenVisitaduira()
		{
	    	$.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/consultarInfoResumenVisitaduria.do',
	    		data: 'idExpediente='+idNumeroExpedienteConsul,
	    		dataType: 'xml',
	    		success: function(xml){
	    			$(xml).find('AuditoriaDTO').each(function(){
	    				$("#spanNombrDuenoExpAud").text($(this).find('nombreCompleto').text());
	    				$("#spanNumExpAud").text($(this).find('numeroExpediente').find('numeroExpediente').text());
	    				$("#spanAreaExpAud").text($(this).find('area').find('nombre').text());
	    				$("#spanFechaExpAud").text($(this).find('fechaAperturaNumeroExp').text());
	    				$("#spanEstatusExpAud").text($(this).find('estatus').find('valor').text());
	    			});
	    		}
	    	});
		}
		
		/*
		*Funcion que dispara el Action para consultar el los agentes mp
		*/		
	    function cargaAgenteMp(){
	    	$.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/consultarAgentesUI.do',
	    		data: '',
	    		dataType: 'xml',
	    		success: function(xml){
	    			var option;
	    			$(xml).find('funcionarioDTO').each(function(){
	    				$('#cbxAgentesCoorUI').append('<option value="' + $(this).find('claveFuncionario').text() + '">'+ 
	    	    				                        $(this).find('nombreFuncionario').text() +" "+
	    	    				                        $(this).find('apellidoPaternoFuncionario').text() +" "+
	    	    				                        $(this).find('apellidoMaternoFuncionario').text() + '</option>');
	    			});
	    		}
	    	});
	    }
		
	    /*
		*Funcion que dispara el Action para consultar el los agentes mp
		*/		
	    function cargaAgenteJAR(){
	    	$.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/consultarAgentesJAR.do',
	    		data: '',
	    		dataType: 'xml',
	    		success: function(xml){
	    			var option;
	    			$(xml).find('funcionarioDTO').each(function(){
	    				var nombreFuncionario = $(this).find('nombreFuncionario').text() + " " + $(this).find('apellidoPaternoFuncionario').text() + " " + $(this).find('apellidoMaternoFuncionario').text();
	    				$('#cbxAgentesCoorJAR').append('<option value="' + $(this).find('claveFuncionario').text() + '">'+ nombreFuncionario + '</option>');
	    			});
	    		}
	    	});
	    }
		
		/****** listener cadena de Custodia  ****/
		function asentarRegCadenaCustodia()
		{
			idWindowAsntarRegCadCus++;
			$.newWindow({id:"iframewindowAsntrRegCadCus" + idWindowAsntarRegCadCus, statusBar: true, posx:200,posy:50,width:1100, height:760,title:"Asentar registro de cadena de custodia", type:"iframe"});
		    $.updateWindowContent("iframewindowAsntrRegCadCus" + idWindowAsntarRegCadCus,'<iframe src="<%= request.getContextPath() %>/AsentarRegCadCustodia.do?consultaCadena=0&numeroExpediente='+numeroExpediente +'&operacion=1'+'&pantallaSolicitada='+pantallaSolicitada +'" width="1100" height="760" />');
		    $("#" +"iframewindowAsntrRegCadCus" + idWindowAsntarRegCadCus + " .window-maximizeButton").click();
		}
		
		function consultarRegCadenaCustodia()
		{
			idWindowAsntarRegCadCus++;
			$.newWindow({id:"iframewindowCnsltrRegCadCus" + idWindowAsntarRegCadCus, statusBar: true, posx:200,posy:50,width:1100, height:760,title:"Cadena de custodia", type:"iframe"});
		    $.updateWindowContent("iframewindowCnsltrRegCadCus" + idWindowAsntarRegCadCus,'<iframe src="<%= request.getContextPath() %>/AsentarRegCadCustodia.do?consultaCadena=1&numeroExpediente='+numeroExpediente +'&operacion=2'+'&pantallaSolicitada='+pantallaSolicitada +' " width="1100" height="760" />');
		    $("#" +"iframewindowCnsltrRegCadCus" + idWindowAsntarRegCadCus + " .window-maximizeButton").click();
		}
		/****** FIN listener cadena de Custodia  ****/
		
	function notaExpediente(idNota)
	{
            idWindowGenerarNotas++;
	    $.newWindow({id:"iframewindowGenerarNotas" + idWindowGenerarNotas, statusBar: true, posx:200,posy:50,width:700, height:450,title:"Nota de Expediente", type:"iframe", modal:true});
	    $.updateWindowContent("iframewindowGenerarNotas" + idWindowGenerarNotas,'<iframe src="<%= request.getContextPath() %>/capturarNotaExpediente.do?idNumeroExpedienteOp='+idNumeroExpedienteOp +'&idNota='+idNota+'&porFuncionario=true " width="700" height="450" />');
	}
	
	function cerrarVentanaNota(){
		var pantalla ="iframewindowGenerarNotas";
		pantalla += idWindowGenerarNotas;
		$.closeWindow(pantalla);
	}
	
	function cargaNota(id,nombre){
		var row=$('#rowNota_'+id);
		$(row).remove();
		$('#tablaNotasExpediente').append('<tr id="rowNota_'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarNota_'+id+'" onclick="notaExpediente('+id+')">'+nombre+'</a></td></tr>');
		//cerrarVentanaNota();
	} 
	
	function ventanaNuevaConclusionOrdenAprension(tipo,formaID)
	{
		$.newWindow({id:"iFrameWindowConclusionOrdenAprension", statusBar: true, posx:200,posy:50,width:700, height:450,title:"", type:"iframe"});
	    $.updateWindowContent("iFrameWindowConclusionOrdenAprension",'<iframe src="<%= request.getContextPath() %>/capturarConclusionOrdenAprension.do?idNumeroExpedienteOp='+idNumeroExpedienteOp+'&tipo='+tipo+'&formaId='+formaID+' " width="700" height="450" />');
	}
	
	function cerrarVentanaConclusionOrdenAprension(){
		var pantalla ="iFrameWindowConclusionOrdenAprension";
		$.closeWindow(pantalla);
	}
	
	function ventanaDocumentosConclusionOrdenAprension(formaID,actividad,params){
		idWindowPantallaActuaciones++;
		$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:"", type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
	    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&actividadId='+actividad+'&esTransaccional='+true+''+params+'" width="1140" height="400" />');
	    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
	}

	/*Funcion que abre el visor de medidas cautelares 
	*/
	function mostrarVentanaInvolucradosCausa(){

		// Este servicio tiene como parámetro de acción en ingresarMedidasCautelaresPJENC.jsp
		// a flujoMedCautelar
		var flujoMedCautelar="dePGJaSSP"
		
		idWindowVisorMedidasCautelaresPJENC++;
		$.newWindow({id:"iframewindowVisorMedidasCautelares"+idWindowVisorMedidasCautelaresPJENC, statusBar: true, posx:200,posy:50,width:970,height:480,title:"Visor de Medidas Cautelares", type:"iframe"});
    	$.updateWindowContent("iframewindowVisorMedidasCautelares"+idWindowVisorMedidasCautelaresPJENC,'<iframe src="<%=request.getContextPath()%>/visorMedidaCautelar.do?numeroCausa='+numeroExpediente+'&flujoMedCautelar='+flujoMedCautelar+'" width="970" height="480" />'); 
	}
	
	/*
	*Consultar las notas del expediente
	*POR EL MOMENTO SOLO SE CONSULTA UNA NOTA
	*/
	function consultarNotas(){
                var notas=$('#editor1').val();
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarNotasExpediente.do?idNumeroExpediente='+idNumeroExpedienteOp+'&porFuncionario=true',
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
		
					
	/*
	*Funcion que realiza la carga del combo de Actuaciones
	*/
        
	function cargaActuaciones(sinCatuie) {
        //console.log("cargando actuaciones ...")
		$('#cbxAccionesTab').empty();
                $('#cbxOficiosTab').empty();
                $('#cbxAccionesTab').addClass("cargando");
                $('#cbxOficiosTab').addClass("cargando");
                $('#tapActuaciones').addClass("cargando");
                var ofic;
                var act;
                var url =  '<%= request.getContextPath()%>/cargarActuaciones.do?numeroExpediente='+numeroExpediente+'&sinCatuie='+sinCatuie;
		$.ajax({
			type: 'POST',
			url: url,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
//                            console.log("XML DE ACTUACIONES");
//                            console.log(xml);
                            $(xml).find('entry').each(function(){
                                var resp = $(this).find(':first-child').get( 0 );
                               
                                if($(resp).text() === "listaOficios"){
                                    ofic = $(this).find('catActuaciones');
                                    ofic.each(function(){
                                        $('#cbxOficiosTab').append('<li data-value="' + $(this).find('clave').text() + '"><img src="<%=request.getContextPath() %>/resources/images/oficio.jpg" width="15" height="15" align="absmiddle"/><a href="#" class="actuaciones" idselected="'+$(this).find('clave').text()+'">' + $(this).find('valor').text() + '</a></li>');

                                    });
                                }
                                if($(resp).text() === "listaActuaciones"){
                                    act = $(this).find('catActuaciones');
                                    act.each(function(){
                                        $('#cbxAccionesTab').append('<li data-value="' + $(this).find('clave').text() + '"><img src="<%=request.getContextPath() %>/resources/images/play.png" width="15" height="15" align="absmiddle"/><a href="#" idselected="'+$(this).find('clave').text()+'">' + $(this).find('valor').text() + '</a></li>');
                                    });
                                }
                                 
                             });     
                            $('#cbxAccionesTab').removeClass("cargando");
                            $('#cbxOficiosTab').removeClass("cargando");
                            $('#tapActuaciones').removeClass("cargando");
                            if(act.size() === 0 && ofic.size() === 0){ 
                                if(sinCatuie === 1){
                                    $("#rdbConUaei").attr('checked', true);
                                    $("#rdbSinUaei").attr('checked',false);
                                    $("#rdbSinUaei").attr("disabled", true);
                                    cargaActuaciones(0);
                                }else if(sinCatuie === 0) {
                                    $("#rdbConUaei").attr("disabled", true);
                                    alertDinamico("No existe ninguna Actuación.");	
                                }
                            }
			},
                        error:function(){
                            alertDinamico("Error al obtener las actuaciones y oficios");
                            $('#cbxAccionesTab').removeClass("cargando");
                            $('#cbxOficiosTab').removeClass("cargando");
                            $('#tapActuaciones').removeClass("cargando");
                        }
		});
	}
        
        
	
	/*
	*Funcion que realiza la carga del combo de Actuaciones de policia ministerial
	*/
	function cargaActuacionesPolMinisterial() {
    	$('#cbxAccionesTab9').addClass("cargando");
    	$('#tapPoliciaMinister').addClass("cargando");
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarActuaciones.do?idCategoria=<%= CategoriasActividad.POLICIAL.getValorId() %>&numeroExpediente='+numeroExpediente,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catActuaciones').each(function(){
					$('#cbxAccionesTab9').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
        		$('#cbxAccionesTab9').removeClass("cargando");
        		$('#tapPoliciaMinister').removeClass("cargando");
			}
		});
	}

	function registrarActividadExpediente(actuacionID,estatusId,banderaMensaje,funcionario){
		var idFuncionario;
		if ( funcionario === undefined ) {
			idFuncionario = '';
		}else{
			idFuncionario=funcionario;
		}
		
		//Cambia la actividad del expediente
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/registraActividadExpediente.do?idExpediente='+idExpedienteop+'&idNumeroExpediente='+idNumeroExpedienteOp+'&actuacion='+actuacionID+'&estatus='+estatusId+'&numExpe='+numeroExpediente+'&cveFuncionarioAsignado='+idFuncionario,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				if(parseInt(banderaMensaje)===1)
				{
					alertDinamico("Actividad nueva registrada");	
				}
			}
		});
	}

	function asignarAgenteMP(){
		//Sele colocara la funcion para signar agente ke aun no esta realizada
		var funcio=$('#cbxAgentesCoorUI option:selected').val();
		if(funcio !== "-1"){
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/registrafuncionarioNumeroExpediente.do?funcionario='+funcio+'&idNumeroExpediente='+idNumeroExpedienteOp,
				data: '',
				dataType: 'xml',
				async: false,
				success: function(xml){
					
				}
			});
			registrarActividadExpediente(151,1712,0,funcio);
			alertDinamico("Se asignó correctamente la carpeta de investigación");	
		}else{
			alertDinamico("Debe seleccionar un agente para realizar la asignación");	
		}
	}

	function asignarFacilitador()
	{
		var funcio=$('#cbxAgentesCoorJAR option:selected').val();
		if(funcio != "-1"){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/registrafuncionarioNumeroExpediente.do?funcionario='+funcio+'&idNumeroExpediente='+idNumeroExpedienteOp,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){				
				if($(xml).find('respuesta').find('bandera').text() === '1'){
					//Sele colocara la funcion para signar agente ke aun no esta realizada
					registrarActividadExpediente(<%= Actividades.ATENDER_CANALIZACION_JAR.getValorId() %>,2542,0,funcio);
					$("#idAsignarFacilitador").hide();
					//$("#idReasignarFacilitador").show();
					$("#cbxAgentesCoorJAR").hide();
					alertDinamico("Se asignó correctamente el expediente");
				}
				if($(xml).find('respuesta').find('bandera').text() === '0'){
					$("#idAsignarFacilitador").show();
					$("#idReasignarFacilitador").hide();
					$("#cbxAgentesCoorJAR").show();
					alertDinamico("No se logró asignar el expediente");
				}
			}
		});
		}else{
			alertDinamico("Debe seleccionar un facilitador para realizar la asignación");	
		}
	}
                
	function seleccionaActuacion(a){
		var selected = $(a);
                var confActividadId = selected.attr('idselected');
//                var actividadId = selected.attr('idselected');
                console.log("ID " + confActividadId);
                
		if(isEmpty(confActividadId)){
			return;
		}
		var actividad=0;
		var formaID=4;
		var titulo="op";
		var usaeditor="";
		var estatusId="";
		var habilitarTurno='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getHabilitarTurno() %>';
		var nombreActividad="";
		var parametroConfirm="";
		var banderaUno=false;
		var banderaDos=false;
		var banderaTres=false;
		
		var idParametro = '<%=Parametros.MUESTRA_ALERTS_ACTUACIONES.ordinal()%>';
		var url = '<%= request.getContextPath()%>/obtenerConfActividadDocumento.do?idConf='+confActividadId;
		$.ajax({
			type: 'POST',
			url: url,
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
		
		actuacion=actividad;
		if (parametroConfirm === '1' &&
				(actividad === '<%= Actividades.DIRIGIR_A_INSTANCIA_EXTERNA.getValorId() %>'
				|| actividad === '<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS.getValorId() %>' 
				|| actividad === '<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_FISCALES_INVESTIGADORES.getValorId() %>'
				|| actividad === '<%=Actividades.GENERAR_CONSTANCIA_DE_FALTA_DE_INTERES.getValorId() %>'
				|| actividad === '<%=Actividades.NO_ACEPTACION_DEL_SERVICIO_POR_PARTE_DEL_INVITADO.getValorId() %>'
				|| actividad === '<%=Actividades.GENERAR_CONSTANCIA_DE_TERMINACION_DEL_PROCEDIMIENTO.getValorId() %>'
				|| actividad === '<%=Actividades.DIRIGIR_A_ATENCION_TEMPRANA_PENAL.getValorId() %>')){
			var textoUno = '&#191;Est&aacute; seguro que requiere realizar la siguiente actuaci&oacute;n?:<br/>'+ nombreActividad;
			var textoDos = 'La actuaci&oacute;n que acaba de seleccionar cerrar&aacute; su expediente.<br/>'
						  +'&#191;Est&aacute; seguro que requiere '+nombreActividad+'?';
			var textoTres = "Ha aceptado cerrar su expediente.<br/>&#191;Desea Continuar?";
			if (actividad === '<%=Actividades.DIRIGIR_A_INSTANCIA_EXTERNA.getValorId() %>'){
				textoTres = 'Ha aceptado cerrar su expediente, porque no compete a esta Instituci&oacute;n.<br/>&#191;Desea Continuar?';
			}else if (actividad === '<%=Actividades.DIRIGIR_A_ATENCION_TEMPRANA_PENAL.getValorId() %>'){
				textoTres = 'Ha aceptado cerrar su expediente, generando el oficio para dirigir a Atenci&oacute;n Temprana.<br/>&#191;Desea Continuar?';
			}else if (actividad === '<%=Actividades.GENERAR_CONSTANCIA_DE_FALTA_DE_INTERES.getValorId() %>'){
				textoTres = 'Ha aceptado concluir por falta de inter&eacute;s su expediente, generado la constancia correspondiente.<br/>&#191;Desea Continuar?';
			}else if (actividad === '<%=Actividades.NO_ACEPTACION_DEL_SERVICIO_POR_PARTE_DEL_INVITADO.getValorId() %>'){
				textoTres = 'Ha aceptado cerrar su expediente, con la carta de no Aceptaci&oacute;n de Servicio por el Invitado.<br/>&#191;Desea Continuar?';
			}else if (actividad === '<%=Actividades.GENERAR_CONSTANCIA_DE_TERMINACION_DEL_PROCEDIMIENTO.getValorId() %>'){
				textoTres = 'Ha aceptado concluir su expediente, con la constancia de terminaci&oacute;n del procedimiento.<br/>&#191;Desea Continuar?';
			}
			var tituloConfirm = 'Validar actuaci&oacute;n';
			despliegaMensaje(0, textoUno, textoDos, textoTres, tituloConfirm, selected, confActividadId, actividad, formaID, 
			titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave);
		}else{
			ejecutaActuacion(selected, confActividadId, actividad, formaID, titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave);
		}
	}
	
	function despliegaMensaje(contador, textoUno, textoDos, textoTres, tituloConfirm, selected, confActividadId, actividad, formaID, 
			titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave){
		if (contador === 0){
			contador++;
			customConfirm('<span style="font-size:20px">'+ textoUno +'</span>',tituloConfirm,
					function (){
						despliegaMensaje(contador, textoUno, textoDos, textoTres, tituloConfirm, selected, confActividadId, actividad, formaID, 
								titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave);
					});
		}else if (contador === 1){
			contador++;
			customConfirm('<span style="font-size:20px">'+ textoDos +'</span>',tituloConfirm,
					function (){
						despliegaMensaje(contador, textoUno, textoDos, textoTres, tituloConfirm, selected, confActividadId, actividad, formaID, 
								titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave);
					});
		}else if (contador === 2){
			contador++;			
			//Tiene el objetivo de mostrar un mensaje de confirmacion en canalizarAUnidadFiscalesInvestigadores.jsp en el metodo
			//crearPdf(), para el caso de DIRIGIR_A_LA_UNIDAD_DE_FISCALES_INVESTIGADORES y
			// para Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS
			if(actividad === '<%= Actividades.DIRIGIR_A_LA_UNIDAD_DE_FISCALES_INVESTIGADORES.getValorId()%>' ||
			   actividad === '<%= Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS.getValorId()%>'){				
				despliegaMensaje(contador, textoUno, textoDos, textoTres, tituloConfirm, selected, confActividadId, actividad, formaID, 
						titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave);
			}else{
				customConfirm('<span style="font-size:20px">'+ textoTres +'</span>',tituloConfirm,
						function (){
							despliegaMensaje(contador, textoUno, textoDos, textoTres, tituloConfirm, selected, confActividadId, actividad, formaID, 
									titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave);
						});	
			}
		}else if (contador === 3){
			ejecutaActuacion(selected, confActividadId, actividad, formaID, 
					titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave);
		}
	}
	
	function ejecutaActuacion(selected, confActividadId, actividad, formaID, titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave){
		if( actividad==='<%= Actividades.GENERAR_QUERELLA.getValorId() %>' || actividad==='<%= Actividades.GENERAR_DENUNCIA_EN_ATP.getValorId() %>'){
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/enviarReplicaCaso.do?idExpediente='+idExpedienteop,
				data: '',
				dataType: 'xml',
				async: true
			});
		}
		if(usaeditor=== "true"){
		
			if (actividad === '<%= Actividades.SOLICITAR_AUDIENCIA.getValorId() %>') {

				if(confInstitucionId === '<%=Instituciones.PJ.getValorId()%>'){
					nuevaSolicitudPJATP();
				}else{
                                    console.log("acaa");
					//codigo para cambiar el estatus del expediente
					registrarActividadExpediente(actividad,estatusId,0);
	                $.newWindow({id:"iframewindowSolicitarAudiencia", statusBar: true, posx:20,posy:20,width:1030,height:570,title:"Solicitar Audiencia", type:"iframe"});
	                $.updateWindowContent("iframewindowSolicitarAudiencia",'<iframe src="<%= request.getContextPath() %>/solicitarAudiencia.jsp?idNumeroExpediente=' + idNumeroExpedienteOp + '&idExpedienteSoli='+ idExpedienteop+'&numeroExpediente='+numeroExpediente+'"    width="1040" height="570" />');
	                $("#" +"iframewindowSolicitarAudiencia" + " .window-maximizeButton").click();
				}
			}
			else if (actividad === '<%= Actividades.SOLICITAR_DEFENSOR_PUBLICO.getValorId()%>'){
				validarEjecucionSolicitudDeDefensor(actividad,estatusId,titulo, formaID);				
			}
			else if(actividad==='<%= Actividades.SOLICITAR_ATENCION_PSICOLOGICA_A_LA_DIRECCION_DE_ATENCION_A_VICTIMAS.getValorId() %>')
			{
				//verificamos si se tienen relaciones de delito-persona o delito-delito
				if(consultaTotalRelacionesDelitoPorTodos()>0)
				{
					$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:20,posy:20,width:940,height:350,title:"Solicitud de Ayuda a la Unidad de Atención a V&iacute;ctimas", type:"iframe"});
		            $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/solicitarAyudaPsicologicaUAVD.do?formaId='+formaID+'&idExpedienteop='+idExpedienteop+'&numeroCaso='+numeroCaso+'&numeroUnicoExpediente='+numeroExpediente+'&idNumeroExpedienteOp='+idNumeroExpedienteOp+'"    width="1140" height="550" />');
		            $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
				}
				else
				{
					var texto = "Debe tener registrada la relación de la v&iacute;ctima \n con el " +
								msjProbableResponsableProp +
								" y el delito"
					alertDinamico(texto);
				}
			}
			else if(actividad==='<%=Actividades.SOLICITAR_ATENCION_MEDICA_A_LA_DIRECCION_DE_ATENCION_A_VICTIMAS
					.getValorId()%>' || actividad==='<%=Actividades.SOLICITAR_ORIENTACION_LEGAL_A_LA_DIRECCION_DE_ATENCION_A_VICTIMAS
					.getValorId()%>' || actividad==='<%=Actividades.SOLICITAR_SEGURIDAD_POLICIAL_A_LA_DIRECCION_DE_ATENCION_A_VICTIMAS
					.getValorId()%>')
			{
                var area = 1;
    		 	idWindowPantallaActuaciones++;
		 		$.newWindow({id:"iframewindowGenerarDocumento"+ idWindowPantallaActuaciones, statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Elaborar Solicitud", type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
         		$.updateWindowContent("iframewindowGenerarDocumento" + idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/elaborarSolicitud.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&actividadId='+actividad+'" width="1140" height="550" />');
         		$("#" +"iframewindowGenerarDocumento"+ idWindowPantallaActuaciones + " .window-maximizeButton").click();
			}
			else if(actividad==='<%= Actividades.SOLICITAR_SERVICIO_PERICIAL.getValorId() %>'){
				 // Variable para definir el área de donde proviene la solicitud.
				 // Para Procuraduria el valor es 1
				 // Para Defensoria el valor es 2
				 //codigo para cambiar el estatus del expediente
				registrarActividadExpediente(actividad,estatusId,0);
				var area = 1;			
	            $.newWindow({id:"iframewindowSolicitud", statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Solicitar servicio pericial", type:"iframe"});
	            $.updateWindowContent("iframewindowSolicitud",'<iframe src="<%= request.getContextPath() %>/solicitarServicioPericial.do?numeroExpediente='+numeroExpediente+'&numeroExpedienteId='+idNumeroExpedienteOp+'&area='+area+'"    width="1140" height="550" />');
	            $("#" +"iframewindowSolicitud" + " .window-maximizeButton").click();
	     	}else if(actividad==='<%=Actividades.ELABORAR_OFICIO_INVESTIGACION_POLICIACA
					.getValorId()%>'){
				 var area = 1;			
				//codigo para cambiar el estatus del expediente
				registrarActividadExpediente(actividad,estatusId,0);
	            $.newWindow({id:"iframewindowSolicitudPolicia", statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Solicitar servicio policial", type:"iframe"});
	            $.updateWindowContent("iframewindowSolicitudPolicia",'<iframe src="<%= request.getContextPath() %>/solicitarServicioPolicial.do?numeroExpediente='+numeroExpediente+'&numeroExpedienteId='+idNumeroExpedienteOp+'&area='+area+'"    width="1140" height="550" />');
	            $("#" +"iframewindowSolicitudPolicia" + " .window-maximizeButton").click();
	     	}
			else if(actividad==='<%=Actividades.GENERAR_CONVENIO_DE_CONCILIACION_MEDIACION
					.getValorId()%>')
			{
				idWindowGenConvenio++;
				//codigo para cambiar el estatus del expediente
				registrarActividadExpediente(actividad,estatusId,0);
				$.newWindow({id:"iframewindowGenConvenio"+idWindowGenConvenio, statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Generar Convenio", type:"iframe"});
	            $.updateWindowContent("iframewindowGenConvenio"+idWindowGenConvenio,'<iframe src="<%= request.getContextPath() %>/generarConvenio.do?numeroExpediente='+numeroExpediente+'&numeroExpedienteId='+idNumeroExpedienteOp+'&area='+area+'&formaID='+formaID+'"    width="1140" height="550" />');
	            $("#" +"iframewindowGenConvenio" + " .window-maximizeButton").click();
			}
			else if(actividad==='<%=Actividades.GENERAR_CONSTANCIA_DE_SEGUIMIENTO_A_CONVENIO
					.getValorId()%>')
 			{
 				idWindowGenConvenio++;
 				//codigo para cambiar el estatus del expediente
				registrarActividadExpediente(actividad,estatusId,0);
 				$.newWindow({id:"iframewindowGenConvenio"+idWindowGenConvenio, statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Seguimiento Convenio", type:"iframe"});
 	            $.updateWindowContent("iframewindowGenConvenio"+idWindowGenConvenio,'<iframe src="<%= request.getContextPath() %>/generarSeguimientoConvenio.do?numeroExpediente='+numeroExpediente+'&numeroExpedienteId='+idNumeroExpedienteOp+'&area='+area+'"    width="1140" height="550" />');
 	           $("#" +"iframewindowGenConvenio" + " .window-maximizeButton").click();
 			}
 			else if(actividad==='<%=Actividades.REPORTE_ORDENES_DE_APREHENSION_CUMPLIDAS
				.getValorId()%>' || actividad==='<%=Actividades.CONCLUSION_POR_VISTA_DE_NO_EJERCICIO
				.getValorId()%>' || actividad==='<%=Actividades.CONCLUSION_POR_REPARACION_DEL_DANO
				.getValorId()%>')
			{
				ventanaNuevaConclusionOrdenAprension(actividad,formaID);
			}
			else{
				//incluir mi validacion
				if(actividad==='<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS
					.getValorId()%>' || actividad==='<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS_SIN_SUSPENDER_EXPEDIENTE.getValorId()%>'){
						if(!existeProbableResponsableDeMenuIntermedio()){
							alertDinamico("Necesita un "+msjProbableResponsableProp+" para poder canalizar a JAR.");
						}else{
							//Valida si aplica Reporte
							if(validarReporte()){
								tipoExpediente = consultaTipoExpediente();   
								if(tipoExpediente === '<%=OrigenExpediente.REPORTE.getValorId()%>' ){
										validacionDeDelitoUSC(actividad,estatusId,titulo, formaID, numeroExpediente);						
								}else{//No es un tipo Reporte
									if(pantallaSolicitada !== null && (pantallaSolicitada == AGENTE_MP || pantallaSolicitada == COORDINADOR_AMP) ){
										validacionDeDelitoUSC(actividad,estatusId,titulo, formaID, numeroExpediente);	
									}else{
										alertDinamico("Sólo los tipo de expediente 'Reporte' se pueden canalizar a JAR");	
									}							
								}
							}else{//En caso de no aplicar la regla de reporte
								validacionDeDelitoUSC(actividad,estatusId,titulo, formaID, numeroExpediente);
							}	
						}
					
				}else{
					if(actividad ==='<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_FISCALES_INVESTIGADORES
					.getValorId()%>'){
						
						
						//VALIDA EL TIPO DE EXPEDIENTE, DENUNCIA O QUERELLA
						if(validarReporte()){
							tipoExpediente = consultaTipoExpediente();
							if(tipoExpediente === 0){
								alertDinamico("Se debe registrar la descripci&oacute;n de los hechos");
							}else if(tipoExpediente === '<%=OrigenExpediente.DENUNCIA.getValorId()%>' || tipoExpediente === '<%=OrigenExpediente.QUERELLA.getValorId()%>'){
								validacionUFI(actividad,estatusId,titulo, formaID, numeroExpediente, numeroExpedienteId);						
							}else{//No es un tipo Reporte
								if(pantallaSolicitada !== null && (pantallaSolicitada === AGENTE_MP || pantallaSolicitada === COORDINADOR_AMP) ){
									validacionUFI(actividad,estatusId,titulo, formaID, numeroExpediente, numeroExpedienteId);	
								}else{
									alertDinamico("Un expediente de tipo 'Reporte' no puede ser enviado a UI");	
								}							
							}
						}else{//En caso de no aplicar la regla de reporte
							validacionUFI(actividad,estatusId,titulo, formaID, numeroExpediente, numeroExpedienteId);
						}				

					}else{
						if(actividad==='<%=Actividades.ELABORAR_OFICIO_INVESTIGACION_POLICIAL.getValorId()%>'){
					 		var area = 1;
					 		var tipoSolicitud=<%= TiposSolicitudes.POLICIA_MINISTERIAL.getValorId() %>;
					 		idWindowPantallaActuaciones++;
					 		$.newWindow({id:"iframewindowGenerarDocumento"+ idWindowPantallaActuaciones, statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Elaborar Solicitud", type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
		             		$.updateWindowContent("iframewindowGenerarDocumento"+ idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/elaborarSolicitud.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&idTipoSolicitud='+tipoSolicitud+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&actividadId='+actividad+'" width="1140" height="550" />');
		             		$("#" +"iframewindowGenerarDocumento"+ idWindowPantallaActuaciones + " .window-maximizeButton").click();
						}else{
							//codigo para cambiar el estatus del expediente
							idWindowPantallaActuaciones++;
							console.info("ID Tipo Actividad Pam:" + actividad);
			     			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
			    		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&actividadId='+actividad+'&esTransaccional='+true+'" width="1140" height="400" />');
			    		    																																																																											
			    		    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
						}
					}
				}
             }
		}else{
			if (actividad === '<%= Actividades.REGISTRAR_AMPARO.getValorId() %>') {
                $.newWindow({id:"iframewindowRegistrarAmparo", statusBar: true, posx:20,posy:20,width:450,height:500,title:"Registrar Amparo", type:"iframe"});
                $.updateWindowContent("iframewindowRegistrarAmparo",'<iframe src="<%= request.getContextPath() %>/registrarAmparo.jsp?idNumeroExpediente=' + idNumeroExpedienteOp + '&idExpedienteSoli='+ idExpedienteop+'&numeroExpediente='+numeroExpediente+'"    width="450" height="500" />');
                $("#" +"iframewindowRegistrarAmparo" + " .window-maximizeButton").click();
			}else{
				if(actividad !=='<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_FISCALES_INVESTIGADORES.getValorId()%>' &&
                                        actividad !=='<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_FISCALES_INVESTIGADORES.getValorId()%>'){
				//codigo para cambiar el estatus del expediente
				registrarActividadExpediente(actividad,estatusId,0);
				document.frmDoc2.numeroUnicoExpediente.value = numeroExpediente;
				document.frmDoc2.formaId.value = formaID;
				document.frmDoc2.submit();	
				}
			}
		}
	}

	function recargarActuaciones(){
		$('#cbxAccionesTab').empty();
                $('#cbxOficiosTab').empty();
		cargaActuaciones();
	} 
	
	function recargarActuacionesPolMinisterial(){
		$('#cbxAccionesTab9').empty();
		cargaActuacionesPolMinisterial();
	}
	
	function canalizarControversiaExisteDelitoGrave(actividad,estatusId,titulo, formaID, numeroExpediente ) {
		if(existeProbableResponsableReincidente() === "true"){
						
			var texto = "Existe " +
						msjProbableResponsableProp +
						" reincidente. ¿Desea enviar a la unidad de controversias?";
			
			customConfirm (texto, "Aviso", 
					function(){
						canalizarControversiaPRReincidente(actividad,estatusId,titulo, formaID, numeroExpediente);
					},
					recargarActuaciones()
					);
		}
		else{
			canalizarControversiaPRReincidente(actividad,estatusId,titulo, formaID, numeroExpediente);
		}
	}
	
	function canalizarControversiaPRReincidente(actividad,estatusId,titulo, formaID, numeroExpediente){
		var excede = excedeMediaAritmeticaDelitos(); 
		if( excede === "null"){
			alertDinamico("Existe un problema con la media aritmetica de los delitos");
		}else{
			if(excede === "true"){
				customConfirm ("La media aritmetica de los delitos excede lo permitido. ¿Desea enviar a la unidad de controversias?", "Aviso", 
						function(){
							canalizarControversiaMediaAritmetica(actividad,estatusId,titulo, formaID, numeroExpediente);
						},
						recargarActuaciones()
						);
			}else if(excede === "false"){
				canalizarControversiaMediaAritmetica(actividad,estatusId,titulo, formaID, numeroExpediente);
			}
		}			
	}
	
	function canalizarControversiaMediaAritmetica(actividad,estatusId,titulo, formaID, numeroExpediente){
		idWindowPantallaActuaciones++;
		if(actividad === '<%= Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS.getValorId()%>' || 
		   actividad==='<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS_SIN_SUSPENDER_EXPEDIENTE.getValorId()%>'){
			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&actividadId='+actividad+'&idNumeroExpediente='+idNumeroExpedienteOp+'&esTransaccional='+true+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'" width="1140" height="400" />');
		}else{
			registrarActividadExpediente(actividad,estatusId,0);
			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&actividadId='+actividad+'&idNumeroExpediente='+idNumeroExpedienteOp+'&esTransaccional='+true+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'" width="1140" height="400" />');
		    recargarActuaciones();						
		}
        }
	
	function canalizarInvestigadoresNoExisteDelitoGrave(actividad,estatusId,titulo, formaID, numeroExpediente ){
		//verificamos si se tienen relaciones de delito-persona o delito-delito
		if(consultaTotalRelacionesDelitoPorTodos() <= 0)
		{
			var texto = "No se tienen registradas relaciones de los delitos con el " +
						msjProbableResponsableProp +
						" ¿Desea enviar a la unidad de fiscales investigadores?"
			
			customConfirm (texto, "Aviso", 
					function(){
						canalizarInvestigadoresSinRelaciones(actividad,estatusId,titulo, formaID, numeroExpediente);
					},
					recargarActuaciones()
					);
		}
		else{
			canalizarInvestigadoresSinRelaciones(actividad,estatusId,titulo, formaID, numeroExpediente);
		}
			
	}
	
	function canalizarInvestigadoresSinRelaciones(actividad,estatusId,titulo, formaID, numeroExpediente){
		//Mostrar ventana de Canalización a la Unidad de Fiscales Investigadores
		idWindowPantallaActuaciones++;
 		$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:"", type:"iframe",confirmarCierreVentana:confirmarCierreVentana});
		$.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath()%>/canalizarAUnidadFiscalesInv.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&actividadId='+actividad+'&numeroExpedienteId='+idNumeroExpedienteOp+'&esTransaccional='+true+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'" width="1140" height="400" />');
	}
	
	/*
	*Cerrar ventana de Canalizacion a unidade de investigadores
	*/
	function cerrarVentanaUFI(id){
		var pantalla ="iframewindowGenerarDocumento";
		pantalla += id;
		$.closeWindow(pantalla);
	}


	/** selecciona actuacion para policia ministerial*/
	function seleccionaActuacionPolMin(){
		var selected = $("#cbxAccionesTab9 option:selected");
		var confActividadId=selected.val();
		if(isEmpty(confActividadId)){
			return;
		}
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
		if(usaeditor=== "true"){
			
			if(actividad==='<%=Actividades.ELABORAR_OFICIO_INVESTIGACION_POLICIAL.getValorId()%>'){
		 		var area = 1;
		 		var tipoSolicitud=<%= TiposSolicitudes.POLICIA_MINISTERIAL.getValorId() %>;
		 		idWindowPantallaActuaciones++;
		 		$.newWindow({id:"iframewindowGenerarDocumento"+ idWindowPantallaActuaciones, statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Elaborar Solicitud", type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
	     		$.updateWindowContent("iframewindowGenerarDocumento"+ idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/elaborarSolicitud.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&idTipoSolicitud='+tipoSolicitud+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&actividadId='+actividad+'" width="1140" height="550" />');
	     		$("#" +"iframewindowGenerarDocumento" + idWindowPantallaActuaciones + " .window-maximizeButton").click();
			}else{
				idWindowPantallaActuaciones++;
				console.info("ID Tipo Actividad:" + actividad);
     			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
    		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&actividadId='+actividad+'&esTransaccional='+true+'" width="1140" height="400" />');
    		    																																																																											
    		    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
			}
		}

	}
	/**FIN seleccion de actuacion de policia ministerial**/
	
	
		function muestraMenuQuienDetuvo(){
			ocultaMuestraTabVisor("tabTabsQuienDetuvo",1);
		}

		function documentoGenerado(){
			//funcion para incluir alguna accion despues de haber sido generado el archivo digital (guardado definitivo)
		}
		
		/*
		*Seteamos la bandera cuando el usuario seleccione el tipo de denuncia o querella
		* en la pestaña de Generales
		*/
		function seteaBanderaTipoSelected()
		{
			isTipoSelected=true;
		}
		//Carga el menu de probable responsable con la consulta y un boton para agregar 
		function cargarMenu(){
			$('#tblProbableResponsable').empty();
			//$('#tblProbableResponsable').append('<tr><td class="noSub">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevoProbResponsable">Ingreso nuevo</a></td></tr>');
			$('#tblProbableResponsable').append('<tr><td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevoProbResponsable" value="Ingreso nuevo" class="ui-button ui-corner-all ui-widget"/></td></tr>');
			$('#tblProbableResponsable').append('<tr><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarProbResponsableUno">Prob. Responsable Uno</a></td></tr>');
			$.ajax({
		    	  type: 'POST',
		    	  url:  '<%= request.getContextPath()%>' + '/ObtenerListaIndividuos.do',
		    	  data: 'calidadDelIndividuo=0',
		    	  dataType: 'xml',
		    	  async: false,
		    	  success: function(xml){
		    	      $(xml).find('involucradoViewDTO').each(function(){
			    	      var liga = '<tr><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="consultarProbableResponsable(' + $(this).find('involucradoId').text() + ');">';
						  liga += $(this).find('nombre').text() + ' ' + $(this).find('apellidoPaterno').text() + ' ' + $(this).find('apellidoMaterno').text();
						  liga += '</a></td></tr>';
		    	    	  $('#tblProbableResponsable').append(liga);
					  });
		    	  }
		    });
			/*for(var i=0;i<10;i++){
				$('#tblProbableResponsable').append('<tr><td class="noSub">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="consultarProbResponsableUno" onclick="consultarProbableResponsable();">Probable responsable '+ i +'</a></td></tr>');
			}*/
		}

		function cargaProbableResponsable(nombre,id){
			var row=$('#'+id);
			$(row).remove();
			nombre=nombre+" - " + '<bean:message key="indiciado"/>';
			$('#tblProbableResponsable').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarProbableResponsable" onclick="modificaProbableResponsable('+id+')">'+nombre+'</a></td></tr>');
			
		} 
		
		//Funcion para quitar la victima del visor de elementos
		function eliminarProbableResponsable(id)
		{
			var row=$('#'+id);
			$(row).remove();
		}
		
		/*
		*Funcion que valida length de la tabla del probable responsable para saber si existe alguno
		* debe de ser mayor que uno por que el primero es el boton para agregar un PR
		*/
		function existeProbableResponsableDeMenuIntermedio(){
			cargarInvolucradosExpediente(idNumeroExpedienteConsul);			
			if($('#tblProbableResponsable tr').length > 1){
				return true;
			}
			return false;
			
		}
		
		/*
		*Funcion que elimina el TR en la tabla de PRs despues de anular un PR en su ventana correspondiente
		* ACT - 20120109
		*/
		function eliminaProbableResponsableDeMenuIntermedio(id){
			var row =$('#tblProbableResponsable tr:#'+id);
			$(row).remove();
			
			var texto = "Se anuló exitosamente el " + msjProbableResponsableProp;
			
			alertDinamico(texto);
		}
		
		/*
		*Funcion para cerrar la venta de modificacion de un PR despues de anular el invlucrado
		* ACT - 20120109
		*/
		function cerrarVentanaProbableResponsable(){
		    var pantalla ="iframewindowModificarProbResponsable";
			pantalla += idWindowIngresarProbResponsable;
			$.closeWindow(pantalla);
		}

		function modificaProbableResponsable(id){
			modificarProbResponsable(id);
		}

		//Carga el menu de victima con la consulta y un boton para agregar 
		function cargarMenuVictima(){
			$('#tblVictima').empty();
			//$('#tblVictima').append('<tr><td class="noSub">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevaVictima">Ingreso nuevo</a></td></tr>');
			$('#tblVictima').append('<tr><td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevaVictima" value="Ingreso nuevo" class="ui-button ui-corner-all ui-widget"/></td></tr>');
			$('#tblVictima').append('<tr><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultaVictimaDos">Victima uno</a></td></tr>');
			$.ajax({
		    	  type: 'POST',
		    	  url:  '<%= request.getContextPath()%>/ObtenerListaIndividuos.do',
		    	  data: 'calidadDelIndividuo=2',
		    	  dataType: 'xml',
		    	  async: false,
		    	  success: function(xml){
		    	      $(xml).find('involucradoViewDTO').each(function(){
			    	      var liga = '<tr><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="consultarVictima(' + $(this).find('involucradoId').text() + ');">';
						  liga += $(this).find('nombre').text() + ' ' + $(this).find('apellidoPaterno').text() + ' ' + $(this).find('apellidoMaterno').text();
						  liga += '</a></td></tr>';
		    	    	  $('#tblVictima').append(liga);
					  });
		    	  }
		    });
		}

		function cargaVictima(nombre,id){
			var row=$('#'+id);
			$(row).remove();
			$('#tblVictima').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarVictima" onclick="modificaVictima('+id+')">'+nombre+'</a></td></tr>');
			//$('#nuevaVictima').hide();
		} 
		function cargaVictimaDenunciante(nombre,id){
			var row=$('#v'+id);
			$(row).remove();
			$('#tblVictima').append('<tr id="v'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarVictima" onclick="modificaDenunciante('+id+')">'+nombre+'</a></td></tr>');
			//$('#nuevaVictima').hide();  modificaDenunciante
		} 

		function modificaVictima(id){
			modificarVictima(id);
		}
		
		function cerrarVentanaVictima(){
		    var pantalla ="iframewindowIngresarVictima";
			pantalla += idWindowIngresarVictima;
			$.closeWindow(pantalla);
		}
		
		//Funcion para quitar la victima del visor de elementos
		function eliminarVictima(id)
		{
			var row=$('#'+id);
			$(row).remove();
		}
		
		function registraEstatus(estatus){
			//Cambia la actividad del expediente
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/registraStatusExpediente.do?idExpediente='+idExpedienteop+'&idNumeroExpediente='+idNumeroExpedienteOp+'&estatus='+estatus,
				data: '',
				dataType: 'xml',
				async: false,
				success: function(xml){
						
				}
			});
		}
		
		function cargarInvolucradosExpediente(idNumeroExpediente){
			if(contadorCargaInvolucrados < 1){
				$.ajax({
		    		type: 'POST',
		    		url: '<%=request.getContextPath()%>/ConsultaInvolucradosExpediente.do',
		    		data: 'idNumeroExpediente='+idNumeroExpediente,
		    		dataType: 'xml',
		    		async: false,
		    		success: function(xml){
		    	      $(xml).find('involucradoViewDTO').each(function(){
			    	      if($(this).find('calidad').text() === '<%= Calidades.DENUNCIANTE.getValorId() %>' || $(this).find('calidad').text() === '<%= Calidades.DENUNCIANTE_ORGANIZACION.getValorId() %>'){
								var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaDenunciante(' + $(this).find('involucradoId').text() + ');">';
			    	      	  if($(this).find('nombre').text()==='null'){
								  liga += 'Anónimo';
							  }else if($(this).find('nombre').text()===''){
								  liga += 'Anónimo';
							  }else if($(this).find('nombre').text()==='   '){							  
								  liga += 'Anónimo';			
							  }	else{
								  liga += $(this).find('nombre').text();
								}	
							  liga += '</a></td></tr>';
			    	    	  $('#tblDenunciante').append(liga);
			    	    	  $('#crearDenunciante').css('display','none'); //.attr("disabled", "disabled");
			    	      }
			    	      if($(this).find('calidad').text() === '<%= Calidades.VICTIMA_PERSONA.getValorId() %>' || $(this).find('calidad').text() === '<%= Calidades.DENUNCIANTE.getValorId() %>'){
			    	    	  if($(this).find('esVictima').text()==="true"){
				    	    	  	var liga = '<tr id="v' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaDenunciante(' + $(this).find('involucradoId').text() + ');">';
									if($(this).find('nombre').text()==='null'){
										  liga += 'Desconocido';
								}else if($(this).find('nombre').text()==='' || $(this).find('nombre').text()==='   '){
										  liga += 'Anónimo';
			    	    	    }
	   		    	    	    else{
										  liga += $(this).find('nombre').text();
									  }
									  liga += '</a></td></tr>';
					    	    	  $('#tblVictima').append(liga);
							}else if($(this).find('calidad').text() === '<%= Calidades.VICTIMA_PERSONA.getValorId() %>'){
								  var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaVictima(' + $(this).find('involucradoId').text() + ');">';
					    	      if($(this).find('nombre').text()==='null'){
									  liga += 'Desconocido';
					    	      }else{
									  liga += $(this).find('nombre').text();
								  }
								  liga += '</a></td></tr>';
				    	    	  $('#tblVictima').append(liga);
							}
					    	  
			    	      }
			    	      
			    	      if($(this).find('calidad').text() === '<%= Calidades.DEFENSOR_PUBLICO.getValorId() %>' ||	$(this).find('calidad').text() === '<%= Calidades.DEFENSOR_PRIVADO.getValorId() %>'){
			    	    	  idDefensor=$(this).find('involucradoId').text();
				    	    	  nombreDefensor=$(this).find('nombre').text();			    	    
			    	      }
			    	      if($(this).find('calidad').text() === '<%= Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId() %>' || $(this).find('calidad').text() === '<%= Calidades.PROBABLE_RESPONSABLE_ORGANIZACION.getValorId() %>'){
				    	      var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaProbableResponsable(' + $(this).find('involucradoId').text() + ');">';
				    	      if($(this).find('nombre').text()==='null'){
								  liga += 'Desconocido';
							  }else{
								  liga = liga + $(this).find('nombre').text() +' - ' + $(this).find('situacionJuridica').text();
							  }
							  liga += '</a></td></tr>';
			    	    	  $('#tblProbableResponsable').append(liga);
			    	      }
			    	      if($(this).find('calidad').text() === '<%= Calidades.TESTIGO.getValorId() %>'){
				    	      var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaTestigo(' + $(this).find('involucradoId').text() + ');">';
				    	      if($(this).find('nombre').text()==='null'){
								  liga += 'Desconocido';
							  }else{
								  liga += $(this).find('nombre').text();
							  }
							  liga += '</a></td></tr>';
			    	    	  $('#tblTestigo').append(liga);
			    	      }
			    	      if($(this).find('calidad').text() === '<%= Calidades.TRADUCTOR.getValorId() %>'){
			    	    	  var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaTraductor(' + $(this).find('involucradoId').text() + ');">';
				    	      if($(this).find('nombre').text()==='null'){
								  liga += 'Desconocido';
							  }else{
								  liga += $(this).find('nombre').text();
							  }
							  liga += '</a></td></tr>';
			    	    	  $('#tblTraductor').append(liga);
			    	      }		    
			    	      if($(this).find('calidad').text() === '<%= Calidades.QUIEN_DETUVO.getValorId() %>'){
			    	    	  var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaQuienDetuvo(' + $(this).find('involucradoId').text() + ');">';
				    	      if($(this).find('nombre').text()==='null'){
								  liga += 'Desconocido';
							  }else{
								  liga += $(this).find('nombre').text();
							  }
							  liga += '</a></td></tr>';
			    	    	  $('#tblQuienDetuvo').append(liga);
			    	      }
			    	      
		    	      });
		    		}	
		    	});	
				contadorCargaInvolucrados++;
			}
		}
		
		/*
		*Funcion para pintar el hecho del expediente en su tab correspondiente
		*/
		function cargarHechoExpediente(idNumeroExpediente){
                $.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/ConsultaHechoExpediente.do',
	    		data: 'idNumeroExpediente='+idNumeroExpediente,
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){
	    			if(parseInt($(xml).find('code').text())===0)
		    		{
			    	      $(xml).find('hechoDTO').each(function(){
			    	    	  var liga = '<tr><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="consultarHecho(' + $(this).find('hechoId').text() + ','+idNumeroExpediente+');">';
							  liga += "Hecho";
							  liga += '</a></td></tr>';
			    	    	  $('#tableHecho').append(liga);
			    	    	//deshabilitamos el boton de guardado
			    	    	  $('#ingresarHechos').css('display','none');
			    	      });
		    		}  
	    		}	
	    	});
		}		

		//Carga el menu de denunciante con la consulta y un boton para agregar 
		function cargarMenuDenunciante(){
			$('#tblDenunciante').empty();
			$('#tblDenunciante').append('<tr><td ><input type="button" id="crearDenunciante" value="Ingreso nuevo" class="ui-button ui-corner-all ui-widget"/></td></tr>');
			$.ajax({
		    	  type: 'POST',
		    	  url:  '<%= request.getContextPath()%>/ObtenerListaIndividuos.do',
		    	  data: 'calidadDelIndividuo=4',
		    	  dataType: 'xml',
		    	  async: false,
		    	  success: function(xml){
		    	      $(xml).find('involucradoViewDTO').each(function(){
			    	      var idIn=$(this).find('involucradoId').text();
			    	      var liga = '<tr id="' + idIn + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificaDenunciante(' + $(this).find('involucradoId').text() + ');">';
						  liga += $(this).find('nombre').text() + ' ' + $(this).find('apellidoPaterno').text() + ' ' + $(this).find('apellidoMaterno').text();
						  liga += '</a></td></tr>';
		    	    	  $('#tblDenunciante').append(liga);
					  });
		    	  }
		    });
		}

		function cargaDenunciante(nombre,id){
			var row=$('#'+id);
			$(row).remove(); 
			$('#tblDenunciante').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarDenunciante" onclick="modificaDenunciante('+id+')">'+nombre+'</a></td></tr>');
			$('#crearDenunciante').hide();
		} 
		
		/*
		*Funcion que elimina el TR en la tabla de Denunciantes despues de anular un denunciante en su ventana correspondiente
		* ACT - 20120109
		*/
		function eliminaDenuncianteDeMenuIntermedio(id){
			var row =$('#tblDenunciante tr:#'+id);
			$(row).remove();
			$('#crearDenunciante').show();
			alertDinamico("Se anuló exitosamente el denunciante");
		}
		
		/*
		*Funcion para cerrar la venta de modificacion de un PR despues de anular el invlucrado
		* ACT - 20120109
		*/
		function cerrarVentanaDenunciante(){
		    var pantalla ="iframewindowIngresarDenunciante";
			pantalla += idWindowIngresarDenunciante;
			$.closeWindow(pantalla);
		}

		function modificaDenunciante(id){
			modificarDenuncianteDatos(id);
		}
		
		//Funcion para quitar la victima del visor de elementos
		function eliminarDenunciante(id)
		{
			var row=$('#tblDenunciante tr:#'+id);
			$(row).remove();
			$('#crearDenunciante').show();
		}

		//Carga el menu de testigo con la consulta y un boton para agregar 
		function cargarMenuTestigo(){
			$('#tblTestigo').empty();
			//$('#tblTestigo').append('<tr><td class="noSub">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevoTestigo">Ingreso nuevo</a></td></tr>');
			$('#tblTestigo').append('<tr><td>&nbsp;&nbsp;<input type="button" id="nuevoTestigo" value="Ingreso nuevo" class="ui-button ui-corner-all ui-widget"/></td></tr>');
			$('#tblTestigo').append('<tr><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultaTestigo">Testigo uno</a></td></tr>');
			$.ajax({
		    	  type: 'POST',
		    	  url:  '<%= request.getContextPath()%>/ObtenerListaIndividuos.do',
		    	  data: 'calidadDelIndividuo=5',
		    	  dataType: 'xml',
		    	  async: false,
		    	  success: function(xml){
		    	      $(xml).find('involucradoViewDTO').each(function(){
			    	      var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="consultarTestigo(' + $(this).find('involucradoId').text() + ');">';
						  liga += $(this).find('nombre').text() + ' ' + $(this).find('apellidoPaterno').text() + ' ' + $(this).find('apellidoMaterno').text();
						  liga += '</a></td></tr>';
		    	    	  $('#tblTestigo').append(liga);
					  });
		    	  }
		    });
		}

		function cargaTestigo(nombre,id){
			var row=$('#'+id);
			$(row).remove(); 
			$('#tblTestigo').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarTestigo" onclick="modificaTestigo('+id+')">'+nombre+'</a></td></tr>');
			
		} 

		function modificaTestigo(id){
			modificarTestigo(id);
		}
		
		//Funcion para quitar la victima del visor de elementos
		function eliminarTestigo(id)
		{
			var row=$('#tblTestigo tr:#'+id);
			$(row).remove();
			$('#crearDenunciante').show();
		}
		
		function cerrarVentanaTestigo(){
		    var pantalla ="iframewindowIngresarTestigo";
			pantalla += idWindowIngresarTestigo;
			$.closeWindow(pantalla);
		}
		
		//Carga el menu de traductor con la consulta y un boton para agregar 
		function cargarMenuTraductor(){
			$('#tblTraductor').empty();
			//$('#tblTraductor').append('<tr><td class="noSub">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevoTraductor">Ingreso nuevo</a></td></tr>');
			$('#tblTraductor').append('<tr><td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevoTraductor" value="Ingreso nuevo" class="ui-button ui-corner-all ui-widget"/></td></tr>');
			$('#tblTraductor').append('<tr><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarTraductorUno">Traductor uno</a></td></tr>');
			$.ajax({
		    	  type: 'POST',
		    	  url:  '<%= request.getContextPath()%>/ObtenerListaIndividuos.do',
		    	  data: 'calidadDelIndividuo=7',
		    	  dataType: 'xml',
		    	  async: false,
		    	  success: function(xml){
		    	      $(xml).find('involucradoViewDTO').each(function(){
			    	      var liga = '<tr><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="consultarTraductor(' + $(this).find('involucradoId').text() + ');">';
						  liga += $(this).find('nombre').text() + ' ' + $(this).find('apellidoPaterno').text() + ' ' + $(this).find('apellidoMaterno').text();
						  liga += '</a></td></tr>';
		    	    	  $('#tblTraductor').append(liga);
					  });
		    	  }
		    });
		}

		function cargaTraductor(nombre,id){
			var row=$('#'+id);
			$(row).remove(); 
			$('#tblTraductor').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarTraductor" onclick="modificaTraductor('+id+')">'+nombre+'</a></td></tr>');
			
		} 

		function modificaTraductor(id){
			modificarTraductor(id);
		}
		

		//Carga el menu de Quien detuvo con la consulta y un boton para agregar 
		function cargarMenuQuienDetuvo(){
			$('#tblQuienDetuvo').empty();
			//$('#tblQuienDetuvo').append('<tr><td class="noSub">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a>Ingreso nuevo</a></td></tr>');
			$('#tblQuienDetuvo').append('<tr><td>&nbsp;&nbsp;&nbsp;<input type="button" id="nuevoTraductor" value="Ingreso nuevo" class="ui-button ui-corner-all ui-widget"/></td></tr>');
			$('#tblQuienDetuvo').append('<tr><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a>Quien detuvo uno</a></td></tr>');
			$.ajax({
		    	  type: 'POST',
		    	  url:  '<%= request.getContextPath()%>/ObtenerListaIndividuos.do',
		    	  data: 'calidadDelIndividuo=8',
		    	  dataType: 'xml',
		    	  async: false,
		    	  success: function(xml){
		    	      $(xml).find('involucradoViewDTO').each(function(){
			    	      var liga = '<tr><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="consultarQuienDetuvo(' + $(this).find('involucradoId').text() + ');">';
						  liga += $(this).find('nombre').text() + ' ' + $(this).find('apellidoPaterno').text() + ' ' + $(this).find('apellidoMaterno').text();
						  liga += '</a></td></tr>';
		    	    	  $('#tblQuienDetuvo').append(liga);
					  });
		    	  }
		    });
		}

		function cargaQuienDetuvo(nombre,id){
			var row=$('#'+id);
			$(row).remove(); 
			$('#tblQuienDetuvo').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarQuienDetuvo" onclick="modificaQuienDetuvo('+id+')">'+nombre+'</a></td></tr>');
			cerrarVentanaQuienDetuvo();
		}		
			
		function modificaQuienDetuvo(id) {			
			idWindowIngresarQuienDetuvo++;
			$.newWindow({id:"iframewindowQuienDetuvo" + idWindowIngresarQuienDetuvo, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Qui&eacute;n Detuvo", type:"iframe"});
		    $.updateWindowContent("iframewindowQuienDetuvo" + idWindowIngresarQuienDetuvo,'<iframe src="<%= request.getContextPath() %>/IngresarQuienDetuvo.do?elemento='+id +'&numeroExpediente='+numeroExpediente +'" width="1100" height="530" />');
		    $("#" +"iframewindowQuienDetuvo" + idWindowIngresarQuienDetuvo + " .window-maximizeButton").click();
		}
		
		function cerrarVentanaQuienDetuvo(){
		    var pantalla ="iframewindowQuienDetuvo";
			pantalla += idWindowIngresarQuienDetuvo;
			$.closeWindow(pantalla);
		}
		
		//Abre una nueva ventana de consulta probable responsable  
		function consultarProbResponsable(){
			idWindowConsultarProbResponsable++;
			var titulo = "Consultar " + etiquetaProbableProp;
			$.newWindow({id:"iframewindowConsultarProbResponsable" + idWindowConsultarProbResponsable, statusBar: true, posx:75,posy:30,width:1100,height:530,title:titulo, type:"iframe"});
		    $.updateWindowContent("iframewindowConsultarProbResponsable" + idWindowConsultarProbResponsable,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?idInvolucrado=1000&idCalidad=PROBABLE_RESPONSABLE" width="1100" height="530" />');
		    $("#" +"iframewindowConsultarProbResponsable" + idWindowConsultarProbResponsable + " .window-maximizeButton").click();
		}


		//Abre una ventana de problable responsable
		function consultarProbableResponsable(idInvolucrado){
			idWindowConsultarProbResponsable++;
			var titulo = "Consultar " + etiquetaProbableProp;
			$.newWindow({id:"iframewindowConsultarProbResponsable" + idWindowConsultarProbResponsable, statusBar: true, posx:75,posy:30,width:1100,height:530,title:titulo, type:"iframe"});
			$.updateWindowContent("iframewindowConsultarProbResponsable" + idWindowConsultarProbResponsable,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?idInvolucrado=' +idInvolucrado +'&idCalidad=PROBABLE_RESPONSABLE" width="1100" height="530" />');
			$("#" +"iframewindowConsultarProbResponsable" + idWindowConsultarProbResponsable + " .window-maximizeButton").click();
		}

		//Abre una nueva ventana para consultar una v&iacute;ctima		
		function consultarVictima(idInvolucrado){
			idWindowIngresarSentenciadoReinsertar++;
			$.newWindow({id:"iframewindowIngresarSentenciadoReinsertar" + idWindowIngresarSentenciadoReinsertar, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Consultar Victima", type:"iframe"});
			$.updateWindowContent("iframewindowIngresarSentenciadoReinsertar" + idWindowIngresarSentenciadoReinsertar,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?idInvolucrado=' +idInvolucrado +'&idCalidad=VICTIMA" width="1100" height="530" />');
			$("#" +"iframewindowIngresarSentenciadoReinsertar" + idWindowIngresarSentenciadoReinsertar + " .window-maximizeButton").click();
		}

		//Abre una nueva ventana de consulta de denunciante		
		function consultarDenunciante(idInvolucrado){
			idWindowConsultarDenunciante++;
			$.newWindow({id:"iframewindowConsultarDenunciante" + idWindowConsultarDenunciante, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Consultar Denunciante", type:"iframe"});
			$.updateWindowContent("iframewindowConsultarDenunciante" + idWindowConsultarDenunciante,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?idInvolucrado=' +idInvolucrado +'&idCalidad=DENUNCIANTE" width="1100" height="530"/>');
			$("#" +"iframewindowConsultarDenunciante" + idWindowConsultarDenunciante + " .window-maximizeButton").click();
		}

		//Abre una nueva ventana de consulta de testigo		
		function consultarTestigo() {
			idWindowConsultarTestigo++;
			$.newWindow({id:"iframewindowConsultarTestigo" + idWindowConsultarTestigo, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Consultar Testigo", type:"iframe"});
		    $.updateWindowContent("iframewindowConsultarTestigo" + idWindowConsultarTestigo,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?idInvolucrado=1200&idCalidad=TESTIGO" width="1100" height="530" />');
		    $("#" +"iframewindowConsultarTestigo" + idWindowConsultarTestigo + " .window-maximizeButton").click();
		}

		//Abre una nueva ventana de consulta de testigo
		function consultarTestigo(idInvolucrado){
			idWindowConsultarTestigo++;
			$.newWindow({id:"iframewindowConsultarTestigo" + idWindowConsultarTestigo, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Consultar Testigo", type:"iframe"});
			$.updateWindowContent("iframewindowConsultarTestigo" + idWindowConsultarTestigo,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?idInvolucrado=' +idInvolucrado +'&idCalidad=TESTIGO" width="1100" height="530" />');
			$("#" +"iframewindowConsultarTestigo" + idWindowConsultarTestigo + " .window-maximizeButton").click();
		}

		//Abre una nueva ventana de consulta de traductor
		function consultarTraductor(idInvolucrado){
			idWindowConsultarTraductor++;
			$.newWindow({id:"iframewindowConsultarTraductor" + idWindowConsultarTraductor, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Consultar Traductor", type:"iframe"});
			$.updateWindowContent("iframewindowConsultarTraductor" + idWindowConsultarTraductor,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?idInvolucrado=' +idInvolucrado +'&idCalidad=TRADUCTOR"width="1100" height="530" />');
		}

		//Abre una nueva ventana de consulta de organizacion		
		function consultarContactoDeUnaOrganizacion() {
			
			idWindowConsultarContactoDeUnaOrganizacion++;
			$.newWindow({id:"iframewindowConsultarContactoDeUnaOrganizacion" + idWindowConsultarContactoDeUnaOrganizacion, statusBar: true, posx:250,posy:150,width:1050,height:620,title:"Consultar contacto de una organización", type:"iframe"});
			$.updateWindowContent("iframewindowConsultarContactoDeUnaOrganizacion" + idWindowConsultarContactoDeUnaOrganizacion,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?idInvolucrado=1001&idCalidad=CONTACTO_ORGANIZACION" width="1100" height="530" />');		
		}

		//Abre una nueva ventana de crear una nuev victima
		function creaNuevaVictima() {
			idWindowIngresarVictima++;
			$.newWindow({id:"iframewindowIngresarVictima" + idWindowIngresarVictima, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar V&iacute;ctima", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarVictima" + idWindowIngresarVictima,'<iframe src="<%= request.getContextPath() %>/IngresarVictima.do?numeroExpediente='+numeroExpediente +'" width="1100" height="530" />');
		    $("#" +"iframewindowIngresarVictima" + idWindowIngresarVictima + " .window-maximizeButton").click();
		}

		//Abre una nueva ventana de crear una nuev victima
		function modificarVictima(id) {
			idWindowIngresarVictima++;
			$.newWindow({id:"iframewindowIngresarVictima" + idWindowIngresarVictima, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar V&iacute;ctima", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarVictima" + idWindowIngresarVictima,'<iframe src="<%= request.getContextPath() %>/IngresarVictima.do?idVictima='+id +'&numeroExpediente='+numeroExpediente +'" width="1100" height="530" />');
		    $("#" +"iframewindowIngresarVictima" + idWindowIngresarVictima + " .window-maximizeButton").click();
		}
		
		function eliminaVictimaDeMenuIntermedio(id){
			var row =$('#tblVictima tr:#'+id);
			$(row).remove();
			alertDinamico("Se anuló exitosamente la v&iacute;ctima");
		}

		//Abre una nueva ventana de probable responsable
		function creaNuevoProbResponsable() {
			//numeroExpediente='<%= request.getAttribute("numeroExpediente")%>';
			var sistemaTrad = false;
			idWindowIngresarProbResponsable++;
			var titulo = "Ingresar " + etiquetaProbableProp;
			$.newWindow({id:"iframewindowIngresarProbResponsable" + idWindowIngresarProbResponsable, statusBar: true, posx:75,posy:30,width:1100,height:530,title:titulo, type:"iframe"});
			$.updateWindowContent("iframewindowIngresarProbResponsable" + idWindowIngresarProbResponsable,'<iframe src="<%= request.getContextPath() %>/IngresarProbResponsable.do?numeroExpediente='+idNumeroExpedienteOp +'&calidadInv=PROBABLE_RESPONSABLE&idDefensor='+idDefensor+'&detenido='+muestraDetenido+'&sistemaTrad='+sistemaTrad+'" width="1100" height="530" />');
			$("#" +"iframewindowIngresarProbResponsable" + idWindowIngresarProbResponsable + " .window-maximizeButton").click();
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
		
		//Abre una nueva ventana de Denunciante
		function crearDenunciante(){
			idWindowIngresarDenunciante++;
			$.newWindow({id:"iframewindowIngresarDenunciante" + idWindowIngresarDenunciante, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Denunciante", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarDenunciante" + idWindowIngresarDenunciante,'<iframe src="<%= request.getContextPath() %>/IngresarDenunciante.do?numeroExpediente='+numeroExpediente +'&calidadInv=DENUNCIANTE" width="1100" height="530" />');
		    $("#" +"iframewindowIngresarDenunciante" + idWindowIngresarDenunciante + " .window-maximizeButton").click();
		}

		//Abre una nueva ventana de Denunciante
		function modificarDenuncianteDatos(id){
			idWindowIngresarDenunciante++;
			$.newWindow({id:"iframewindowIngresarDenunciante" + idWindowIngresarDenunciante, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Denunciante", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarDenunciante" + idWindowIngresarDenunciante,'<iframe src="<%= request.getContextPath() %>/IngresarDenunciante.do?idDenunciante='+id +'&numeroExpediente='+numeroExpediente +'&calidadInv=DENUNCIANTE" width="1100" height="530" />');
		    $("#" +"iframewindowIngresarDenunciante" + idWindowIngresarDenunciante + " .window-maximizeButton").click();
		}
			

		//Crea una nueva ventana de testigo
		function creaNuevoTestigo() {
			idWindowIngresarTestigo++;
			$.newWindow({id:"iframewindowIngresarTestigo" + idWindowIngresarTestigo, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Testigo", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarTestigo" + idWindowIngresarTestigo,'<iframe src="<%= request.getContextPath() %>/IngresarTestigo.do?numeroExpediente='+numeroExpediente +'" width="1100" height="530" />');
		    $("#" +"iframewindowIngresarTestigo" + idWindowIngresarTestigo + " .window-maximizeButton").click();
		}

		//Crea una nueva ventana de testigo
		function modificarTestigo(id) {
			idWindowIngresarTestigo++;
			$.newWindow({id:"iframewindowIngresarTestigo" + idWindowIngresarTestigo, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Testigo", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarTestigo" + idWindowIngresarTestigo,'<iframe src="<%= request.getContextPath() %>/IngresarTestigo.do?idTestigo='+id+'&numeroExpediente='+numeroExpediente +'" width="1100" height="530" />');
		    $("#" +"iframewindowIngresarTestigo" + idWindowIngresarTestigo + " .window-maximizeButton").click();
		}
		
		function eliminaTestigoDeMenuIntermedio(id){
			var row =$('#tblTestigo tr:#'+id);
			$(row).remove();
			alertDinamico("Se anuló exitosamente el testigo");
		}
		
		//Crea una ventana de un nuevo contacti de una organizacion		
		function creaNuevoContactoDeUnaOrganizacion() {
			
			idWindowIngresarContactoDeUnaOrganizacion++;
			$.newWindow({id:"iframewindowIngresarContactoDeUnaOrganizacion" + idWindowIngresarContactoDeUnaOrganizacion, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Contacto de una organización", type:"iframe"});
			$.updateWindowContent("iframewindowIngresarContactoDeUnaOrganizacion" + idWindowIngresarContactoDeUnaOrganizacion,'<iframe src="<%= request.getContextPath() %>/IngresarContactoDeUnaOrganizacion.do" width="1100" height="530"  />');		
		}

		//crea una nueva ventana de ingresar tutor
		function creaNuevoTutor() {
			idWindowIngresarTutor++;
			$.newWindow({id:"iframewindowTutor" + idWindowIngresarTutor, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Datos Generales", type:"iframe"});
		    $.updateWindowContent("iframewindowTutor" + idWindowIngresarTutor,'<iframe src="<%= request.getContextPath() %>/IngresarTutor.do" width="1050" height="600" />');		
		}

		//Abre una nueva ventana de ingresar traductor
		function creaNuevoTraductor() {
			idWindowIngresarTraductor++;
		$.newWindow({id:"iframewindow" + idWindowIngresarTraductor, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Traductor", type:"iframe"});
	    $.updateWindowContent("iframewindow" + idWindowIngresarTraductor,'<iframe src="<%= request.getContextPath() %>/IngresarTraductor.do?numeroExpediente='+numeroExpediente+'" width="1050" height="600" />');		
		}	

		//Abre una nueva ventana de ingresar traductor
		function modificarTraductor(id) {
			idWindowIngresarTraductor++;
		$.newWindow({id:"iframewindow" + idWindowIngresarTraductor, statusBar: true, posx:75,posy:30,width:1050,height:530,title:"Modificar Traductor", type:"iframe"});
	    $.updateWindowContent("iframewindow" + idWindowIngresarTraductor,'<iframe src="<%= request.getContextPath() %>/IngresarTraductor.do?idTraductor='+id+'&numeroExpediente='+numeroExpediente +'" width="1050" height="600" />');		
		}
		
		//Elimina un registro del traductor 
		function eliminaTraductorDeMenuIntermedio(id){
			var row =$('#tblTraductor tr:#'+id);
			$(row).remove();
		}
		
		//Cierra la ventana traductor
		function cerrarVentanaTraductor(){
		    var pantalla ="iframewindow";
			pantalla += idWindowIngresarTraductor++;
			$.closeWindow(pantalla);
		}
		
		//Abre una nueva ventana de ingresar quien detuvo
		function creaQuienDetuvo() {
			idWindowIngresarQuienDetuvo++;
		$.newWindow({id:"iframewindowQuienDetuvo" + idWindowIngresarQuienDetuvo, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar Qui&eacute;n detuvo", type:"iframe"});
	    $.updateWindowContent("iframewindowQuienDetuvo" + idWindowIngresarQuienDetuvo,'<iframe src="<%= request.getContextPath() %>/IngresarQuienDetuvo.do?elemento='+0+'&numeroExpediente='+numeroExpediente+'" width="1050" height="600" />');
		}	

		//Abre una nueva ventana de modificar denunciante		
		function modificarDenunciante(){
			
			idWindowModificarDenunciante++;
			$.newWindow({id:"iframewindowModificarDenunciante" + idWindowModificarDenunciante, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Modificar Denunciante", type:"iframe"});
		    $.updateWindowContent("iframewindowModificarDenunciante" + idWindowModificarDenunciante,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?idInvolucrado=1200&idCalidad=DENUNCIANTE" width="1040" height="620" />');		
		}
		
		function creaNuevoRepresentanteLegal() {
			idWindowIngresarRepresentanteLegal++;
		$.newWindow({id:"iframewindowIngresarRepresentanteLegal" + idWindowIngresarRepresentanteLegal, statusBar: true,posx:75,posy:30,width:1100,height:530,title:"Ingresar Representante Legal", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarRepresentanteLegal" + idWindowIngresarRepresentanteLegal,'<iframe src="<%= request.getContextPath() %>/IngresarRepresentanteLegal.do" width="1050" height="600" />');		
		}	

		
		function modificarRepresentanteLegal(){
			
			idWindowModificarRepresentanteLegal++;
			$.newWindow({id:"iframewindowModificarRepresentanteLegal" + idWindowModificarRepresentanteLegal, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Consultar Representante Legal", type:"iframe"});
		    $.updateWindowContent("iframewindowModificarRepresentanteLegal" + idWindowModificarRepresentanteLegal,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?idInvolucrado=1200&idCalidad=REPRESENTANTE_LEGAL" width="1040" height="570" />');		
		}

		
		function crearSentenciadoReinsertar() {
			idWindowIngresarSentenciadoReinsertar++;
			$.newWindow({id:"iframewindowIngresarSentenciadoReinsertar" + idWindowIngresarSentenciadoReinsertar, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Ingresar nuevo sentenciado a reinsertar", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarSentenciadoReinsertar" + idWindowIngresarSentenciadoReinsertar,'<iframe src="<%= request.getContextPath() %>/IngresarSentenciadoReinsertar.do" width="1050" height="600" />');		
		}
		

		function ConsultarVictimaUno() {
			idWindowIngresarSentenciadoReinsertar++;
			$.newWindow({id:"iframewindowSentenciadoReinsertar" + idWindowIngresarSentenciadoReinsertar, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Consultar Victima", type:"iframe"});
		    $.updateWindowContent("iframewindowSentenciadoReinsertar" + idWindowIngresarSentenciadoReinsertar,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?idInvolucrado=1200&idCalidad=VICTIMA_PERSONA" width="1050" height="600" />');		
		}

		
		function ConsultarVictimaDos() {
			idWindowIngresarSentenciadoReinsertar++;
			$.newWindow({id:"iframewindowSentenciadoReinsertar" + idWindowIngresarSentenciadoReinsertar, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Consultar Victima", type:"iframe"});
		    $.updateWindowContent("iframewindowSentenciadoReinsertar" + idWindowIngresarSentenciadoReinsertar,'<iframe src="<%= request.getContextPath() %>/ConsultarIndividuo.do?idInvolucrado=1300&idCalidad=VICTIMA" width="1050" height="600" />');		
		}
			//  Inician funciones para crear ventanas de Objetos
			
		function creaNuevoEquipoDeComputo(){
			idWindowIngresarEquipoDeComputo++;
			$.newWindow({id:"iframewindowIngresarEquipoDeComputo" + idWindowIngresarEquipoDeComputo, statusBar: true, posx:200,posy:50,width:830,height:340,title:"Ingresar equipo de c&oacute;mputo", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarEquipoDeComputo" + idWindowIngresarEquipoDeComputo,'<iframe src="<%= request.getContextPath() %>/IngresarEquipoDeComputo.do?numeroExpediente='+numeroExpediente +'&idEquipoComputo=0&tipoObjeto=EQUIPO_COMPUTO" width="830" height="340" />');
		    $("#" +"iframewindowIngresarEquipoDeComputo"+idWindowIngresarEquipoDeComputo+ " .window-maximizeButton").click();
		}
		
		function consultarEquipoComputo(idEquipoComputo){
			idWindowIngresarEquipoDeComputo++;
			$.newWindow({id:"iframewindowIngresarEquipoDeComputo" + idWindowIngresarEquipoDeComputo, statusBar: true, posx:200,posy:50,width:830,height:340,title:"Consultar equipo de c&oacute;mputo", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarEquipoDeComputo" + idWindowIngresarEquipoDeComputo,'<iframe src="<%= request.getContextPath() %>/IngresarEquipoDeComputo.do?numeroExpediente='+numeroExpediente+'&idEquipoComputo='+idEquipoComputo+'&tipoObjeto=EQUIPO_COMPUTO " width="830" height="340" />');
		    $("#" +"iframewindowIngresarEquipoDeComputo"+idWindowIngresarEquipoDeComputo+ " .window-maximizeButton").click();
		}
		
		function cargaEquipoComputo(id,tipo){
			consultaGridEquiposComputoVisor();
			//cerrarVentanaEquipoComputo();
		} 
		
		function cerrarVentanaEquipoComputo(){
			var pantalla ="iframewindowIngresarEquipoDeComputo";
			pantalla += idWindowIngresarEquipoDeComputo;
			$.closeWindow(pantalla);
		}
			
		function creaNuevoEquipoTelefonico(){
			 idWindowIngresarEquipoTelefonico++;
			$.newWindow({id:"iframewindowIngresarEquipoTelefonico" + idWindowIngresarEquipoTelefonico, statusBar: true, posx:200,posy:50,width:870,height:340,title:"Ingresar equipo telef&oacute;nico", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarEquipoTelefonico" + idWindowIngresarEquipoTelefonico,'<iframe src="<%= request.getContextPath() %>/IngresarEquipoTelefonico.do?numeroExpediente='+numeroExpediente+'&idEquipoTelefonico=0&tipoObjeto=EQUIPO_TELEFONICO " width="870" height="340" />');
		    $("#" +"iframewindowIngresarEquipoTelefonico"+idWindowIngresarEquipoTelefonico+ " .window-maximizeButton").click();

		}
		
		function consultarEquipoTelefonico(idEquipoTelefonico){
			 idWindowIngresarEquipoTelefonico++;
			$.newWindow({id:"iframewindowIngresarEquipoTelefonico" + idWindowIngresarEquipoTelefonico, statusBar: true, posx:200,posy:50,width:870,height:340,title:"Consultar equipo telef&oacute;nico", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarEquipoTelefonico" + idWindowIngresarEquipoTelefonico,'<iframe src="<%= request.getContextPath() %>/IngresarEquipoTelefonico.do?numeroExpediente='+numeroExpediente+'&idEquipoTelefonico='+idEquipoTelefonico+'&tipoObjeto=EQUIPO_TELEFONICO " width="870" height="340" />');
		    $("#" +"iframewindowIngresarEquipoTelefonico"+idWindowIngresarEquipoTelefonico+ " .window-maximizeButton").click();
		}

		function cargaEquipoTelefonico(id,modelo){
			consultaGridEquipoTelefonicoVisor();
			//cerrarVentanaEquipoTelefonico();
		} 
		
		function cerrarVentanaEquipoTelefonico(){
			var pantalla ="iframewindowIngresarEquipoTelefonico";
			pantalla += idWindowIngresarEquipoTelefonico;
			$.closeWindow(pantalla);
		}
		 
		function creaNuevaArma(){
			 idWindowIngresarArma++;
			$.newWindow({id:"iframewindowIngresarArma" + idWindowIngresarArma, statusBar: true, posx:200,posy:50,width:800,height:380,title:"Ingresar arma", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarArma" + idWindowIngresarArma,'<iframe src="<%= request.getContextPath() %>/IngresarArma.do?numeroExpediente='+numeroExpediente+'&idArma=0&tipoObjeto=ARMA " width="800" height="380" />');
		    $("#" +"iframewindowIngresarArma"+idWindowIngresarArma+ " .window-maximizeButton").click();
		}

		function consultarArma(idArma){
			 idWindowIngresarArma++;
			$.newWindow({id:"iframewindowIngresarArma" + idWindowIngresarArma, statusBar: true, posx:200,posy:50,width:800,height:380,title:"Consultar arma", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarArma" + idWindowIngresarArma,'<iframe src="<%= request.getContextPath() %>/IngresarArma.do?numeroExpediente='+numeroExpediente+'&idArma='+idArma+'&tipoObjeto=ARMA" width="800" height="380" />');
		    $("#" +"iframewindowIngresarArma"+idWindowIngresarArma+ " .window-maximizeButton").click();
		}
		
		function cargaArma(id,tipo){
			consultaGridArmasVisor();
			//cerrarVentanaArma();
		} 
		
		function cerrarVentanaArma(){
			var pantalla ="iframewindowIngresarArma";
			pantalla += idWindowIngresarArma;
			$.closeWindow(pantalla);
		}
		
		function creaNuevoExplosivo(){
			 idWindowIngresarExplosivo++;
			$.newWindow({id:"iframewindowIngresarExplosivo" + idWindowIngresarExplosivo, statusBar: true, posx:200,posy:50,width:880,height:330,title:"Ingresar explosivo", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarExplosivo" + idWindowIngresarExplosivo,'<iframe src="<%= request.getContextPath() %>/IngresarExplosivo.do?numeroExpediente='+numeroExpediente +'&tipoObjeto=EXPLOSIVO&idExplosivo=0" width="880" height="330" />');
		    $("#" +"iframewindowIngresarExplosivo"+idWindowIngresarExplosivo+ " .window-maximizeButton").click();

		}
		
		function consultarExplosivo(idExplosivo){
			idWindowIngresarExplosivo++;
			$.newWindow({id:"iframewindowIngresarExplosivo" + idWindowIngresarExplosivo, statusBar: true, posx:200,posy:50,width:880, height:330,title:"Consultar explosivo", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarExplosivo" + idWindowIngresarExplosivo,'<iframe src="<%= request.getContextPath() %>/IngresarExplosivo.do?numeroExpediente='+numeroExpediente +'&idExplosivo='+idExplosivo+'&tipoObjeto=EXPLOSIVO " width="880" height="330" />');
		    $("#" +"iframewindowIngresarExplosivo"+idWindowIngresarExplosivo+ " .window-maximizeButton").click();

		}

		function cargaExplosivo(id,tipo){
			 consultaGridExplosivoVisor();
			 //cerrarVentanaExplosivo();
		} 
		
		function cerrarVentanaExplosivo(){
			var pantalla ="iframewindowIngresarExplosivo";
			pantalla += idWindowIngresarExplosivo;
			$.closeWindow(pantalla);
		}

		
		function creaNuevaSustancia(){
			 idWindowIngresarSustancia++;
			$.newWindow({id:"iframewindowIngresarSustancia" + idWindowIngresarSustancia, statusBar: true, posx:200,posy:50,width:900,height:330,title:"Ingresar sustancia", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarSustancia" + idWindowIngresarSustancia,'<iframe src="<%= request.getContextPath() %>/IngresarSustancia.do?numeroExpediente='+numeroExpediente +'&idSustancia=0&tipoObjeto=SUSTANCIA" width="900" height="330" />');
		    $("#" +"iframewindowIngresarSustancia"+idWindowIngresarSustancia+ " .window-maximizeButton").click();

		}
		
		function consultarSustancia(idSustancia){
			idWindowIngresarSustancia++;
			$.newWindow({id:"iframewindowIngresarSustancia" + idWindowIngresarSustancia, statusBar: true, posx:200,posy:50,width:900,height:330,title:"Consultar sustancia", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarSustancia" + idWindowIngresarSustancia,'<iframe src="<%= request.getContextPath() %>/IngresarSustancia.do?numeroExpediente='+numeroExpediente+'&idSustancia='+idSustancia+'&tipoObjeto=SUSTANCIA" width="900" height="330" />');
		    $("#" +"iframewindowIngresarSustancia"+idWindowIngresarSustancia+ " .window-maximizeButton").click();
		}
		
		function cargaSustancia(id,tipo){
			consultaGridSustanciaVisor();
			//cerrarVentanaSustancia();
		} 
		
		function cerrarVentanaSustancia(){
			var pantalla ="iframewindowIngresarSustancia";
			pantalla += idWindowIngresarSustancia;
			$.closeWindow(pantalla);
		}

		
		function creaNuevoAnimal(){
			 idWindowIngresarAnimal++;
			$.newWindow({id:"iframewindowIngresarAnimal" + idWindowIngresarAnimal, statusBar: true, posx:200,posy:50,width:900,height:330,title:"Ingresar animal", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarAnimal" + idWindowIngresarAnimal,'<iframe src="<%= request.getContextPath() %>/IngresarAnimal.do?numeroExpediente='+numeroExpediente +'&idAnimal=0&tipoObjeto=ANIMAL" width="900" height="330" />');
		    $("#" +"iframewindowIngresarAnimal"+idWindowIngresarAnimal+ " .window-maximizeButton").click();
		}

		function consultarAnimal(idAnimal){
			idWindowIngresarAnimal++;
			$.newWindow({id:"iframewindowIngresarAnimal" + idWindowIngresarAnimal, statusBar: true, posx:200,posy:50,width:900,height:330,title:"Consultar animal", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarAnimal" + idWindowIngresarAnimal,'<iframe src="<%= request.getContextPath() %>/IngresarAnimal.do?numeroExpediente='+numeroExpediente+'&idAnimal='+idAnimal+'&tipoObjeto=ANIMAL" width="900" height="330" />');
		    $("#" +"iframewindowIngresarAnimal"+idWindowIngresarAnimal+ " .window-maximizeButton").click();
		}
		
		function cargaAnimal(id,tipo){
			 consultaGridAnimalVisor();
			 //cerrarVentanaAnimal();
		} 
		
		function cerrarVentanaAnimal(){
			var pantalla ="iframewindowIngresarAnimal";
			pantalla += idWindowIngresarAnimal;
			$.closeWindow(pantalla);
		}
		
		
		
		function creaNuevoVehiculo(){
			 idWindowIngresarVehiculo++;
			 var sistemaTrad = false;
			$.newWindow({id:"iframewindowIngresarVehiculo" + idWindowIngresarVehiculo, statusBar: true, posx:200,posy:5,width:570, height:600,title:"Ingresar veh&iacute;culo", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarVehiculo" + idWindowIngresarVehiculo,'<iframe src="<%= request.getContextPath() %>/IngresarVehiculo.do?numeroExpediente='+numeroExpediente +'&tipoObjeto=VEHICULO&idVehiculo=0&sistemaTrad='+sistemaTrad+'" width="570" height="600" />');
		    $("#" +"iframewindowIngresarVehiculo"+idWindowIngresarVehiculo+ " .window-maximizeButton").click();
		}
		
		function consultarVehiculo(idVehiculo){
			 idWindowIngresarVehiculo++;
			 var sistemaTrad = false;
			$.newWindow({id:"iframewindowIngresarVehiculo" + idWindowIngresarVehiculo, statusBar: true, posx:200,posy:5,width:570, height:600,title:"Consultar veh&iacute;culo", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarVehiculo" + idWindowIngresarVehiculo,'<iframe src="<%= request.getContextPath() %>/IngresarVehiculo.do?numeroExpediente='+numeroExpediente +'&idVehiculo='+idVehiculo+'&tipoObjeto=VEHICULO&sistemaTrad='+sistemaTrad+'" width="570" height="600" />');
		    $("#" +"iframewindowIngresarVehiculo"+idWindowIngresarVehiculo+ " .window-maximizeButton").click();
		}

		function cargaVehiculo(id,tipo,placas){
			consultaGridVehiculosVisor();
			//cerrarVentanaVehiculo();
		} 
		
		function cerrarVentanaVehiculo(){
			var pantalla ="iframewindowIngresarVehiculo";
			pantalla += idWindowIngresarVehiculo;
			$.closeWindow(pantalla);
		}

		 
		function creaNuevaAeronave(){
			 idWindowIngresarAeronave++;
			$.newWindow({id:"iframewindowIngresarAeronave" + idWindowIngresarAeronave, statusBar: true, posx:200,posy:10,width:600, height:530,title:"Ingresar aeronave", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarAeronave" + idWindowIngresarAeronave,'<iframe src="<%= request.getContextPath() %>/IngresarAeronave.do?numeroExpediente='+numeroExpediente +'&idAeronave=0&tipoObjeto=AERONAVE" width="600" height="530" />');
		    $("#" +"iframewindowIngresarAeronave"+idWindowIngresarAeronave+ " .window-maximizeButton").click();
		}
		function consultarAeronave(idAeronave){
			idWindowIngresarAeronave++;
			$.newWindow({id:"iframewindowIngresarAeronave" + idWindowIngresarAeronave, statusBar: true, posx:200,posy:10,width:600,height:530,title:"Consultar aeronave", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarAeronave" + idWindowIngresarAeronave,'<iframe src="<%= request.getContextPath() %>/IngresarAeronave.do?numeroExpediente='+numeroExpediente+'&idAeronave='+idAeronave+'&tipoObjeto=AERONAVE" width="600" height="530" />');
		    $("#" +"iframewindowIngresarAeronave"+idWindowIngresarAeronave+ " .window-maximizeButton").click();
		}
		
		function cargaAeronave(id,tipo){
			 consultaGridAeronaveVisor();
			 //cerrarVentanaAeronave();
		} 
		
		function cerrarVentanaAeronave(){
			var pantalla ="iframewindowIngresarAeronave";
			pantalla += idWindowIngresarAeronave;
			$.closeWindow(pantalla);
		}

		
		function creaNuevaEmbarcacion(){
			 idWindowIngresarEmbarcacion++;
			$.newWindow({id:"iframewindowIngresarEmbarcacion" + idWindowIngresarEmbarcacion, statusBar: true, posx:200,posy:10,width:600, height:530,title:"Ingresar embarcaci&oacute;n", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarEmbarcacion" + idWindowIngresarEmbarcacion,'<iframe src="<%= request.getContextPath() %>/IngresarEmbarcacion.do?numeroExpediente='+numeroExpediente +'&idEmbarcacion=0&tipoObjeto=EMBARCACION" width="600" height="530" />');
		    $("#" +"iframewindowIngresarEmbarcacion"+idWindowIngresarEmbarcacion+ " .window-maximizeButton").click();
		}
		function consultarEmbarcacion(idEmbarcacion){
			idWindowIngresarEmbarcacion++;
			$.newWindow({id:"iframewindowIngresarEmbarcacion" + idWindowIngresarEmbarcacion, statusBar: true, posx:200,posy:10,width:600,height:530,title:"Consultar embarcaci&oacute;n", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarEmbarcacion" + idWindowIngresarEmbarcacion,'<iframe src="<%= request.getContextPath() %>/IngresarEmbarcacion.do?numeroExpediente='+numeroExpediente+'&idEmbarcacion='+idEmbarcacion+'&tipoObjeto=EMBARCACION" width="600" height="530" />');
		    $("#" +"iframewindowIngresarEmbarcacion"+idWindowIngresarEmbarcacion+ " .window-maximizeButton").click();
		}
		
		function cargaEmbarcacion(id,tipo){
			 consultaGridEmbarcacionVisor();
			 //cerrarVentanaEmbarcacion();
		} 
		
		function cerrarVentanaEmbarcacion(){
			var pantalla ="iframewindowIngresarEmbarcacion";
			pantalla += idWindowIngresarEmbarcacion;
			$.closeWindow(pantalla);
		}

		function creaNuevoNumerario(){
			 idWindowIngresarNumerario++;
			$.newWindow({id:"iframewindowIngresarNumerario" + idWindowIngresarNumerario, statusBar: true, posx:200,posy:50,width:800, height:350,title:"Ingresar numerario", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarNumerario" + idWindowIngresarNumerario,'<iframe src="<%= request.getContextPath() %>/IngresarNumerario.do?numeroExpediente='+numeroExpediente +'&idNumerario=0&tipoObjeto=NUMERARIO" width="800" height="350" />');
		    $("#" +"iframewindowIngresarNumerario"+idWindowIngresarNumerario+ " .window-maximizeButton").click();

		}
		function consultarNumerario(idNumerario){
			idWindowIngresarNumerario++;
			$.newWindow({id:"iframewindowIngresarNumerario" + idWindowIngresarNumerario, statusBar: true, posx:200,posy:50,width:800, height:350,title:"Consultar numerario", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarNumerario" + idWindowIngresarNumerario,'<iframe src="<%= request.getContextPath() %>/IngresarNumerario.do?numeroExpediente='+numeroExpediente +'&idNumerario='+idNumerario+'&tipoObjeto=NUMERARIO " width="800" height="350" />');
		    $("#" +"iframewindowIngresarNumerario"+idWindowIngresarNumerario+ " .window-maximizeButton").click();

		}
		function cargaNumerario(id,tipo){
			 consultaGridNumerarioVisor();
			 //cerrarVentanaNumerario();
		} 
		
		function cerrarVentanaNumerario(){
			var pantalla ="iframewindowIngresarNumerario";
			pantalla += idWindowIngresarNumerario;
			$.closeWindow(pantalla);
		}

		 
		function creaNuevoVegetal(){
			 idWindowIngresarVegetal++;
			$.newWindow({id:"iframewindowIngresarVegetal" + idWindowIngresarVegetal, statusBar: true, posx:200,posy:50,width:800,height:350,title:"Ingresar vegetal", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarVegetal" + idWindowIngresarVegetal,'<iframe src="<%= request.getContextPath() %>/IngresarVegetal.do?numeroExpediente='+numeroExpediente +'&idVegetal=0&tipoObjeto=VEGETAL" width="800" height="350" />');
		    $("#" +"iframewindowIngresarVegetal"+idWindowIngresarVegetal+ " .window-maximizeButton").click();

		}
		function consultarVegetal(idVegetal){
			idWindowIngresarVegetal++;
			$.newWindow({id:"iframewindowIngresarVegetal" + idWindowIngresarVegetal, statusBar: true, posx:200,posy:50,width:800, height:350,title:"Consultar vegetal", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarVegetal" + idWindowIngresarVegetal,'<iframe src="<%= request.getContextPath() %>/IngresarVegetal.do?numeroExpediente='+numeroExpediente+'&idVegetal='+idVegetal+'&tipoObjeto=VEGETAL" width="800" height="350" />');
		    $("#" +"iframewindowIngresarVegetal"+idWindowIngresarVegetal+ " .window-maximizeButton").click();
		}
		
		function cargaVegetal(id,tipo){
			 consultaGridVegetalVisor();
			 //cerrarVentanaVegetal();
		} 
		
		function cerrarVentanaVegetal(){
			var pantalla ="iframewindowIngresarVegetal";
			pantalla += idWindowIngresarVegetal;
			$.closeWindow(pantalla);
		}
		 

		function creaNuevoDocumentoOficial(){
			 idWindowIngresarDocumentoOficial++;
			$.newWindow({id:"iframewindowIngresarDocumentoOficial" + idWindowIngresarDocumentoOficial, statusBar: true, posx:200,posy:50,width:800,height:350,title:"Ingresar documento oficial", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarDocumentoOficial" + idWindowIngresarDocumentoOficial,'<iframe src="<%= request.getContextPath() %>/IngresarDocumentoOficial.do?numeroExpediente='+numeroExpediente +'&idDocOfic=0&tipoObjeto=DOCUMENTO_OFICIAL" width="800" height="350" />');
		    $("#" +"iframewindowIngresarDocumentoOficial"+idWindowIngresarDocumentoOficial+ " .window-maximizeButton").click();

		}
		function consultarDocumentoOficial(idDocumentoOficial){
			idWindowIngresarDocumentoOficial++;
			$.newWindow({id:"iframewindowIngresarDocumentoOficial" + idWindowIngresarDocumentoOficial, statusBar: true, posx:200,posy:50,width:800,height:350,title:"Consultar documento oficial", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarDocumentoOficial" + idWindowIngresarDocumentoOficial,'<iframe src="<%= request.getContextPath() %>/IngresarDocumentoOficial.do?numeroExpediente='+numeroExpediente+'&idDocOfic='+idDocumentoOficial+'&tipoObjeto=DOCUMENTO_OFICIAL" width="800" height="350" />');
		    $("#" +"iframewindowIngresarDocumentoOficial"+idWindowIngresarDocumentoOficial+ " .window-maximizeButton").click();
		}
		
		function cargaDocOfic(id,tipo){
			 consultaGridDocumentoOficialVisor();
			 //cerrarVentanaDocumentoOficial();
		} 
		
		function cerrarVentanaDocumentoOficial(){
			var pantalla ="iframewindowIngresarDocumentoOficial";
			pantalla += idWindowIngresarDocumentoOficial;
			$.closeWindow(pantalla);
		}
		 

		function creaNuevaJoya(){
			 idWindowIngresarJoya++;
			$.newWindow({id:"iframewindowIngresarJoya" + idWindowIngresarJoya, statusBar: true, posx:200,posy:50,width:850,height:380,title:"Ingresar joya", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarJoya" + idWindowIngresarJoya,'<iframe src="<%= request.getContextPath() %>/IngresarJoya.do?numeroExpediente='+numeroExpediente +'&idJoya=0&tipoObjeto=JOYA" width="850" height="380" />');
		    $("#" +"iframewindowIngresarJoya"+idWindowIngresarJoya+ " .window-maximizeButton").click();

		}
		function consultarJoya(idJoya){
			idWindowIngresarJoya++;
			$.newWindow({id:"iframewindowIngresarJoya" + idWindowIngresarJoya, statusBar: true, posx:200,posy:50,width:850,height:380,title:"Consultar joya", type:"iframe"});
		    $.updateWindowContent("iframewindowIngresarJoya" + idWindowIngresarJoya,'<iframe src="<%= request.getContextPath() %>/IngresarJoya.do?numeroExpediente='+numeroExpediente+'&idJoya='+idJoya+'&tipoObjeto=JOYA" width="850" height="380" />');
		    $("#" +"iframewindowIngresarJoya"+idWindowIngresarJoya+ " .window-maximizeButton").click();
		}
		
		function cargaJoya(id,tipo){
			 consultaGridJoyaVisor();
			 //cerrarVentanaJoya();
		} 
		
		function cerrarVentanaJoya(){
			var pantalla ="iframewindowIngresarJoya";
			pantalla += idWindowIngresarJoya;
			$.closeWindow(pantalla);
		}
		 

		function creaNuevaObraDeArte(){
			 idWindowIngresarObraDeArte++;
			$.newWindow({id:"iframewindowObraDeArte" + idWindowIngresarObraDeArte, statusBar: true, posx:200,posy:50,width:850,height:380,title:"Ingresar obra de arte", type:"iframe"});
		    $.updateWindowContent("iframewindowObraDeArte" + idWindowIngresarObraDeArte,'<iframe src="<%= request.getContextPath() %>/IngresarObraDeArte.do?numeroExpediente='+numeroExpediente +'&idObraArte=0&tipoObjeto=OBRA_DE_ARTE" width="850" height="380" />');
		    $("#" +"iframewindowObraDeArte"+idWindowIngresarObraDeArte+ " .window-maximizeButton").click();
		}
		
		
		function consultarObraDeArte(idObraDeArte){
			idWindowIngresarObraDeArte++;
			$.newWindow({id:"iframewindowObraDeArte" + idWindowIngresarObraDeArte, statusBar: true, posx:200,posy:50,width:850,height:380,title:"Consultar obra de arte", type:"iframe"});
		    $.updateWindowContent("iframewindowObraDeArte" + idWindowIngresarObraDeArte,'<iframe src="<%= request.getContextPath() %>/IngresarObraDeArte.do?numeroExpediente='+numeroExpediente+'&idObraArte='+idObraDeArte+'&tipoObjeto=OBRA_DE_ARTE" width="850" height="380" />');
		    $("#" +"iframewindowObraDeArte"+idWindowIngresarObraDeArte+ " .window-maximizeButton").click();
		}
		
		function cargaObraDeArte(id,tipo){
			 consultaGridObraDeArteVisor();
			 //cerrarVentanaObraDeArte();
		} 
		
		function cerrarVentanaObraDeArte(){
			var pantalla ="iframewindowObraDeArte";
			pantalla += idWindowIngresarObraDeArte;
			$.closeWindow(pantalla);
		}
					
		/*** Cargar otros ****/
		function creaNuevoOtros(){
			 idWindowIngresarOtros++;
			$.newWindow({id:"iframewindowOtros" + idWindowIngresarOtros, statusBar: true, posx:200,posy:50,width:800,height:450,title:"Ingresar Otros", type:"iframe"});
		    $.updateWindowContent("iframewindowOtros" + idWindowIngresarOtros,'<iframe src="<%= request.getContextPath() %>/IngresarOtros.do?numeroExpediente='+numeroExpediente +'&idOtros=0&tipoObjeto=OTRO" width="800" height="450" />');
		    $("#" +"iframewindowOtros"+idWindowIngresarOtros+ " .window-maximizeButton").click();

		}
		
		/*** Cargar periciales ****/
		function creaNuevoPericial(){
			 idWindowIngresarPericial++;
			$.newWindow({id:"iframewindowPericial" + idWindowIngresarPericial, statusBar: true, posx:200,posy:50,width:800,height:450,title:"Ingresar Pericial", type:"iframe"});
		    $.updateWindowContent("iframewindowPericial" + idWindowIngresarPericial,'<iframe src="<%= request.getContextPath() %>/IngresarPericial.do?numeroExpediente='+numeroExpediente +'&idOtros=0&tipoObjeto=PERICIAL" width="800" height="450" />');
		    $("#" +"iframewindowPericial"+idWindowIngresarPericial+ " .window-maximizeButton").click();

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
		
		function cargaOtros(id,nombre){
			 consultaGridOtrosVisor();
			 //cerrarVentanaOtros();
		} 
		
		function cerrarVentanaOtros(){
			var pantalla ="iframewindowOtros";
			pantalla += idWindowIngresarOtros;
			$.closeWindow(pantalla);
		}
		
		function cargaPericiales(id,nombre){
			 consultaGridPericialesVisor();
			 //cerrarVentanaPericial();
		} 
		
		function cerrarVentanaPericial(){
			var pantalla ="iframewindowPericial";
			pantalla += idWindowIngresarPericial;
			$.closeWindow(pantalla);
		}
		/***** FIN cargar otros *****/
		
		function ingresarHechos() {
                    idWindowIngresarHechos++;
                    $.newWindow({id:"iframewindowHecho" + idWindowIngresarHechos, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Hechos", type:"iframe"});
		    $.updateWindowContent("iframewindowHecho" + idWindowIngresarHechos,'<iframe src="<%= request.getContextPath() %>/IngresarHechos.do?numeroExpediente='+numeroExpediente +'&idNumeroExpedienteOp='+idNumeroExpedienteOp+'&idCalidad=DENUNCIANTE&idHecho=0&menuIntermedio=1'+'&pantallaSolicitada='+pantallaSolicitada+'" width="1100" height="530" />');
		    $("#" +"iframewindowHecho" + idWindowIngresarHechos + " .window-maximizeButton").click();
		}
		
		function consultarHecho(idHecho,idNumeroExpediente) {
                    idWindowIngresarHechos++;
		    $.newWindow({id:"iframewindowHecho" + idWindowIngresarHechos, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Hechos", type:"iframe"});
		    $.updateWindowContent("iframewindowHecho" + idWindowIngresarHechos,'<iframe src="<%= request.getContextPath() %>/IngresarHechos.do?numeroExpediente='+numeroExpediente +'&idNumeroExpedienteOp='+idNumeroExpedienteOp+'&idCalidad=DENUNCIANTE&idHecho='+idHecho +'&menuIntermedio=1'+'&pantallaSolicitada='+pantallaSolicitada+'" width="1100" height="530" />');
		    $("#" +"iframewindowHecho" + idWindowIngresarHechos + " .window-maximizeButton").click();
		}

		
		function asociarIndividuo() {
			idWindowAsociarIndividuo++;
			$.newWindow({id:"iframewindow" + idWindowAsociarIndividuo, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Hechos", type:"iframe"});
		    $.updateWindowContent("iframewindow" + idWindowAsociarIndividuo,'<iframe src="<%= request.getContextPath() %>/AsociarIndividuo.do" width="1100" height="530" />');		
		}
		
		function cargaOcupacion(){
			$.ajax({
		    	type: 'POST',
		    	url: '<%= request.getContextPath()%>/ConsultarCatalogoOcupacion.do',
		    	data: '',
		    	dataType: 'xml',
		    	async: false,
		    	success: function(xml){
		    		var option;
		    		
		    		$(xml).find('ocupacion').each(function(){
		    			$('#consultaVictima').append('<li value="' + $(this).find('gcNombre').text() +  '" title="'+ $(this).find('gcDescripcion').text() + '"  style="background:#99C"  >'+ $(this).find('gcDescripcion').text() + '</li>');
		    		});
		    	}
		    		
		    	});
		}

		function generarDocumentoSinCaso() {
                console.log("GENERANDO DOCUMENTO SIN CASO");
			idWindowPantallaActuaciones++;
			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Denuncia", type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'" width="1140" height="400" />');
		}
		
		
                
		function cargaInstitucionesExternas(){
                        $.ajax({
		    	type: 'POST',
		    	url: '<%= request.getContextPath()%>/consultarInstitucionesExternas.do',
		    	data: '',
		    	dataType: 'xml',
		    	async: false,
		    	success: function(xml){
		    		$('#cbxCanalizaAIE').empty();
		    		$('#cbxCanalizaAIE').append('<option value="-1" selected="selected">-Seleccione-</option>');
		    		$(xml).find('departamentos').each(function(){
						$('#cbxCanalizaAIE').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
		    		
					$("#cbxCanalizaAIE").multiselect({ 
						multiple: false, 
						header: "Seleccione", 
						position: { 
							my: 'top', 
							at: 'top' 
						},
						selectedList: 1 
					});
		    	}	
		    	});
		}
		
		
		function revisaEsDelitoGrave(idRadio)
		{
			//recuperamos el valor de la columna gravedad del delito
			var ret = jQuery("#gridCatDelitos").jqGrid('getRowData',idRadio);
			var retDos = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',idRadio);
			var isGrave="No";
			
			var delitos=<%=(String)request.getSession().getAttribute("delitosXML")%>;
			var delitosXML=$(''+delitos);
			//mostramos los botones correspondientes dependiento del tipo de delito
			if(ret.Gravedad!==null)
			{
				isGrave=ret.Gravedad;
			}
			else
			{
				isGrave=retDos.Gravedad;
			}
			if(isGrave==="No")
			{
				//revisamos que no exista un delito grave NO seleccionado
				if(existeDelitoGraveEnGrid())
				{
					//se le indica al usuario que seleccione un delito grave como principal
					customAlert("Si no hay reincidencia por parte del Probable Part&iacute;cipe,\n se debe canalizar al Centro de Justicia Restaurativa.");
				}
				else
				{
					mostraDivGenerarOficioCanalizacion(1);	
				}
			}
			else{
				//barro el pseudo-XML de delitos	
				delitosXML.find('catDelitoDTO').each(function(){
					if($(this).find('claveDelito').text()===idRadio)
					{
						if($(this).find('departamento').text()!=="")
						{
							//seteamos el combo de la pestaña de Acciones dependiendo del departamento
							$('#cbxCanalizaAUI').val(parseInt($(this).find('departamento').find('departamentoId').text()));
							$('#cbxCanalizaAUI').multiselect('refresh');
							mostraDivGenerarOficioCanalizacion(2);
						}
						else
						{
							//seteamos el combo de la pestaña de Acciones dependiendo de la institucion
							$('#cbxCanalizaAIE').val(parseInt($(this).find('institucion').find('institucionId').text()));
							$('#cbxCanalizaAIE').multiselect('refresh');
							mostraDivGenerarOficioCanalizacion(3);
						}
					}
				});
			}
		}
                
		function mostraDivGenerarOficioCanalizacion(idDiv)
		{
			$("#divCanalizaAUI,#divCanalizaAIE,#btnCanalizaAJR").hide();
			if(parseInt(idDiv)===1)
			{
				$("#btnCanalizaAJR").show();
				$("#btnGenerarAcciones").hide();
			}
			else if(parseInt(idDiv)===2)
			{
				$("#divCanalizaAUI").show();
				$("#btnGenerarAcciones").show();
			}
			else if(parseInt(idDiv)===3)
			{
				$("#divCanalizaAIE").show();
				$("#btnGenerarAcciones").show();
			}
		}
		
		function muestraDivInformativoCanalizacion()
		{
			$("#spanGralUI,#spanGralIE,#spanGralJAR,#spanInfoGralUI,#spanInfoGralIE").hide();
			if($("#divCanalizaAUI").is(':visible'))
			{
				$("#spanInfoGralUI").html($("#cbxCanalizaAUI option:selected").text());
				$("#spanInfoGralUI").show();
				$("#spanGralUI").show();
			}
			else if($("#divCanalizaAIE").is(':visible'))
			{
				$("#spanInfoGralIE").html($("#cbxCanalizaAIE option:selected").text());
				$("#spanInfoGralIE").show();
				$("#spanGralIE").show();
			}
			else if($("#btnCanalizaAJR").is(':visible'))
			{
				$("#spanGralJAR").show();
			}
		}
		
		function muestraDIVSCanalizacion()
		{
			//obtenemos los ID's de los renglones del Grid de Agraviados
			var arrayIDs = new Array() ;
			var arrayIDs=jQuery("#gridDelitosAgraviados").getDataIDs();
			//validamos que haya delitos en el grid de agraviados
			if(arrayIDs.length<1)
			{
				alertDinamico("Debe agregar al menos un delito agraviado");
				return false;
			}
			//revisamos que si hay un delito grave se haya seleccionado
			if(existeDelitoGraveEnGrid())
			{
				if(parseInt(existeUnDelitoPrincipalGraveSeleccionado())===0)
				{
					alertDinamico("Debe seleccionar un delito grave como principal");
					return false;
				}	
			}
			//obtenemos el ID del delito principal
			var idDelPrincipal=$('input[name=gridDelitos]:checked').attr('id').split('_')[1];
			//recuperamos el valor de la columna gravedad del delito
			var retDos = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',idDelPrincipal);
			var isGrave="No";
			var delitos=<%=(String)request.getSession().getAttribute("delitosXML")%>;
			var delitosXML=$(''+delitos);
			//seteamos la gravedad
			isGrave=retDos.Gravedad;
			if(isGrave==="No")
			{
				mostraDivGenerarOficioCanalizacion(1);	
			}
			else
			{
				//barro el pseudo-XML de delitos
				delitosXML.find('catDelitoDTO').each(function()
				{
					if($(this).find('claveDelito').text()===idDelPrincipal)
					{
						if($(this).find('departamento').text()!=="")
						{
							//seteamos el combo de la pestaña de Acciones dependiendo del departamento
							$('#cbxCanalizaAUI').val(parseInt($(this).find('departamento').find('departamentoId').text()));
							$('#cbxCanalizaAUI').multiselect('refresh');
							mostraDivGenerarOficioCanalizacion(2);
						}
						else
						{
							//seteamos el combo de la pestaña de Acciones dependiendo de la institucion
							$('#cbxCanalizaAIE').val(parseInt($(this).find('institucion').find('institucionId').text()));
							$('#cbxCanalizaAIE').multiselect('refresh');
							mostraDivGenerarOficioCanalizacion(3);
						}
					}
				});
			}
			return true;
		}
		

		/*
		*Funcion para mostrar las actividades sugeridas dependiendo de los delitos en el expediente 
		*cuando se consulta un expediente
		*/
		function muestraActividadesSugeridasEnConsultaExpediente()
		{
			//obtenemos los ID's de los renglones del Grid de Agraviados
			var arrayIDs = new Array() ;
			var arrayIDs=jQuery("#gridDelitosAgraviados").getDataIDs();
			//validamos que haya delitos en el grid de agraviados
			if(arrayIDs.length<1)
			{
				return;
			}
			//obtenemos el ID del delito principal			
			var idDelPrincipal=$('input[name=gridDelitos]:checked').attr('id').split('_')[1];
			if(idDelPrincipal===null || idDelPrincipal==='null')
			{
				return;	
			}
			var nombreDelPrincipal=jQuery("#gridDelitosAgraviados").jqGrid('getRowData',idDelPrincipal).Tipo;
			var delitosNormales="";			
			//barremos el grid para generar la cadena de IDs de los delitos normales
			for (i=0;i<arrayIDs.length;i++)
			{
				if(arrayIDs[i]!==idDelPrincipal)
				{
					var retd = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',arrayIDs[i]);
					if(delitosNormales.length>0)
					{
						delitosNormales=delitosNormales+","+retd.Tipo;//arrayIDs[i];
					}
					else
					{
						delitosNormales=""+retd.Tipo;//arrayIDs[i];
					}
				}
			} 
			//ahora mandamos los delitos al back-end
			var params="delitos="+nombreDelPrincipal+"-"+delitosNormales+"&numExp="+numeroExpediente;
		
			//mostramos las leyendas de canalizacion debajo del grid
  			  if(existeDelitoGraveEnGrid())
  			  {
  				  if(pantallaSolicitada!==AGENTE_MP){
  					  $("#leyendaUnDelitoGrave").show();
  					  $("#leyendaNingunDelitoGrave").hide();  
				  }else{
					  $("#leyendaUnDelitoGrave,#leyendaNingunDelitoGrave").hide();
				  }
  			  }
  			  else
  			  {
  				  $("#leyendaUnDelitoGrave").hide();
  				  $("#leyendaNingunDelitoGrave").show();
  			  }
  			  //lanzamos la consulta de actividades que depende de los delitos almacenados
  			  	$.ajax({
	       	  url: '<%= request.getContextPath()%>/consultarActividadesPorDelitosDelExpediente.do',
	    	  dataType: 'xml',
	    	  Type: 'POST',
	    	  data:params,
	    	  async: true,
	    	  success: function(xml){
	    		  if(parseInt($(xml).find('code').text())===0)
	    		  {
	    			  $("#actividadesXDelitosDelExpediente").empty();
	    			  var actividades="";
	    			  //barremos las activiades y generamos el html para ser pintado
	    			  //debajo del anuncio de canalizacion
	    			  $(xml).find('ValorDTO').each(function(){
	    				  actividades = actividades + $(this).find('valor').text()+"<br/>";
		    	      });
	    			  $("#actividadesXDelitosDelExpediente").html(actividades);
	    		  }  			    		
			  }
  				});
  			  //fin de la consulta de actividades que depende de los delitos almacenados
		}
		
		function validaGuardadoDefinitivo()
		{
			//revisamos que selecciono el tipo: Denuncia o Querella en generales
			//obtengo el ID del rdb del delito seleccionado
			var idRdbTipo="";
			var banderaTipo=false;
			idRdbTipo=$('input[name=generales]:checked').attr('id');
			if(idRdbTipo!==null)
			{
				if(idRdbTipo!=="")
				{
					//reviso si el delito seleccionado es grave o no
					if(idRdbTipo==="rbtnDenuncia")//Denuncia
					{
						$("#btnAccDenuncia").show();
						$("#tdCbxAccionesTab").show();
                                                $("#tdCbxOficiosTab").show();
						$("#btnAccQuerella").hide();
						//revisamos si ya guardo el delito
						if(isDelitoSaved)
						{
							//$("#btnAccDenuncia").attr("disabled", "");
						}
						else
						{
							alertDinamico("Debe de seleccionar guardar en la pestaña Delito");
							return;
						}
					}
					else//rbtnQuerella querella
					{
						$("#btnAccQuerella").show();
						$("#btnAccDenuncia").hide();
						$("#tdCbxAccionesTab").hide();
                                                $("#tdCbxOficiosTab").hide();
					}
					banderaTipo=true;
				}
			}
			if(!banderaTipo)
			{
				alertDinamico("Debe seleccionar el tipo en la pestaña Generales");
				return;
			}
		}
		
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
		
		function gridMinisterial(){

			jQuery("#gridDetalleFrmPrincipalTres").jqGrid({ 
				 
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
				pager: jQuery('#pagerTres1'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc"
					
			}).navGrid('#pagerTres12',{edit:false,add:false,del:false});

				

			}
		
		function gridCustodia(){

			jQuery("#gridDetalleFrmPrincipalUno").jqGrid({ 
				 
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
				pager: jQuery('#pageruno'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc"
					
			}).navGrid('#pagerpageruno',{edit:false,add:false,del:false});

			// $("#gview_gridDetalleFrmPrincipalTres .ui-jqgrid-bdiv").css('height', '200px');	

			}
		
		function gridPericiales(){

			jQuery("#gridDetalleFrmPrincipalDos").jqGrid({ 
				 
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
				pager: jQuery('#pagerDos'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc"
					
			}).navGrid('#pagerDos1',{edit:false,add:false,del:false});

			// $("#gview_gridDetalleFrmPrincipalTres .ui-jqgrid-bdiv").css('height', '200px');	

			}

		function abrirDenuncia() {
			$.newWindow({id:"iframewindowAbrirDenuncia", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Denuncia", type:"iframe"});
		    $.updateWindowContent("iframewindowAbrirDenuncia",'<iframe src="<%= request.getContextPath() %>/resources/images/Denuncia en Atención Temprana _JAS.pdf" width="1140" height="400" />');
		   		
		}
		
		function abreTeoria() {
			$.newWindow({id:"iframewindowAbrirteoria", statusBar: true, posx:20,posy:20,width:1390,height:600,title:"Teoria del caso", type:"iframe"});
		    $.updateWindowContent("iframewindowAbrirteoria",'<iframe src="<%= request.getContextPath() %>/teoriaDelCasoView.jsp?idExpediente='+idExpedienteop+'&numeroExpediente='+numeroExpediente+'" width="1390" height="600" />');
		      		
		}

		
		<%--@deprecated  function lanzaCarpetaInvestigacionDefensoria() {
			$.newWindow({id:"iframewindowAbrirteoria", statusBar: true, posx:20,posy:20,width:310,height:231,title:"Enviar Carpeta Investigación", type:"iframe"});
		    $.updateWindowContent("iframewindowAbrirteoria",'<iframe src="<%= request.getContextPath() %>/enviarCarpetaInvestigacionDefensoria.do" width="310" height="231" />');
		      		
		} --%>


		function datosGenerales(){
			 $.ajax({
			      type: 'POST',
		    	  url: '<%= request.getContextPath()%>/cargarDatosGenerales.do?idNumeroExpedienteOp='+idNumeroExpedienteOp,
		    	  data: '',
		    	  dataType: 'xml',
		    	  async: false,
		    	  success: function(xml){
						$('#Vehiculos').html($(xml).find('totalVehiculos').text()+': '+$(xml).find('vehiculos').text());
						if($(xml).find('ve').text()!=="1"){
							$('#imgVehiculo').hide();
						}
						$('#EquiposDeComputo').html($(xml).find('totalEquiposComputo').text()+': '+$(xml).find('equiposComputo').text());
						if($(xml).find('equiCom').text()!=="1"){
							$('#imgEquiposDeComputo').hide();
						}
						$('#EquiposTelefonicos').html($(xml).find('totalEquiposTelefonicos').text()+': '+$(xml).find('equiposTelefonicos').text());
						if($(xml).find('equiTel').text()!=="1"){
							$('#imgEquiposTelefonicos').hide();
						}
						$('#Armas').html($(xml).find('totalArmas').text()+': '+$(xml).find('armas').text());
						if($(xml).find('arm').text()!=="1"){
							$('#imgArmas').hide();
						}
						$('#Explosivos').html($(xml).find('totalExplosivos').text()+': '+$(xml).find('explosivos').text());
						if($(xml).find('expl').text()!=="1"){
							$('#imgExplosivos').hide();
						}
						$('#Sustancias').html($(xml).find('totalSustancias').text()+': '+$(xml).find('sustancias').text());
						if($(xml).find('sus').text()!=="1"){
							$('#imgSustancias').hide();
						}
						$('#Animales').html($(xml).find('totalAnimales').text()+': '+$(xml).find('animales').text());
						if($(xml).find('anim').text()!=="1"){
							$('#imgAnimales').hide();
						}
						$('#Aeronaves').html($(xml).find('totalAeronaves').text()+': '+$(xml).find('aeronaves').text());
						if($(xml).find('aero').text()!=="1"){
							$('#imgAeronaves').hide();
						}
						$('#Embarcacion').html($(xml).find('totalEmbarcaciones').text()+': '+$(xml).find('embarcaciones').text());
						if($(xml).find('embar').text()!=="1"){
							$('#imgEmbarcacion').hide();
						}
						$('#Numerario').html($(xml).find('totalNumerarios').text()+': '+$(xml).find('numerarios').text());
						if($(xml).find('nume').text()!=="1"){
							$('#imgNumerario').hide();
						}
						$('#Vegetal').html($(xml).find('totalVegetales').text()+': '+$(xml).find('vegetales').text());
						if($(xml).find('vege').text()!=="1"){
							$('#imgVegetal').hide();
						}
						$('#DocumentoOficial').html($(xml).find('totalDocumentosOficiales').text()+': '+$(xml).find('documentosOficiales').text());
						if($(xml).find('docuOfi').text()!=="1"){
							$('#imgDocumentoOficial').hide();
						}
						$('#Joya').html($(xml).find('totalJoyas').text()+': '+$(xml).find('joyas').text());
						if($(xml).find('joy').text()!=="1"){
							$('#imgJoya').hide();
						}
						$('#ObraDeArte').html($(xml).find('totalObrasDeArte').text()+': '+$(xml).find('obrasDeArte').text());
						if($(xml).find('obrArte').text()!=="1"){
							$('#imgObraDeArte').hide();
						}
						$('#Periciales').html($(xml).find('totalPericialObjeto').text()+': '+$(xml).find('pericialObjeto').text());
						if($(xml).find('perObj').text()!== "1"){
							$('#imgPericiales').hide();
						}
						$('#Otros').html($(xml).find('totalOtrosObjestos').text()+': '+$(xml).find('otrosObjestos').text());
						if($(xml).find('otrObj').text()!=="1"){
							$('#imgOtros').hide();
						}
						$('#Denunciantes').html($(xml).find('totalDenunciantes').text()+': '+$(xml).find('denunciantes').text());
						if($(xml).find('denun').text()!=="1"){
							$('#imgDenunciantes').hide();
						}
						$('#Victimas').html($(xml).find('totalVictimas').text()+': '+$(xml).find('victimas').text());
						if($(xml).find('vic').text()!=="1"){
							$('#imgVictimas').hide();
						}
						$('#ProbablesResponsables').html($(xml).find('totalProbablesResposables').text()+': '+$(xml).find('probablesResposables').text());
						if($(xml).find('proba').text()!=="1"){
							$('#imgProbablesResponsables').hide();
						}
						$('#Testigos').html($(xml).find('totalTestigos').text()+': '+$(xml).find('testigos').text());
						if($(xml).find('test').text()!=="1"){
							$('#imgTestigos').hide();
						}
						$('#Traductores').html($(xml).find('totalTraductores').text()+': '+$(xml).find('traductores').text());
						if($(xml).find('tradu').text()!=="1"){
							$('#imgTraductores').hide();
						}
						$('#QuienDetuvo').html($(xml).find('quienDetuvo').text()+': '+$(xml).find('quienDetuvoNombre').text());
						if($(xml).find('quienDetu').text()!=="1"){
							$('#imgQuienDetuvo').hide();
						}
						$('#estatusExpe').html($(xml).find('estatusNumeroExpediente').text());
						$('#origenExpe').html($(xml).find('origenExpediente').text());	
						$('#anonimoDenun').html($(xml).find('esDesconocido').text());
						num=$(xml).find('totalDocumentosDelExpediente').text();
						$('#fechaApertura').html("Fecha Apertura: "+$(xml).find('fechaApertura').text());
						$('#responsableTramite').html($(xml).find('responsableTramite').text());
						$('#estatusActuacion').html($(xml).find('estatusActuacion').text());
						$('#origenNumeroExpediente').html($(xml).find('origenNumeroExpediente').text());

						/* if($(xml).find('numeroExpedienteAlterno').text() != ""){
							$('#numExpAltSpan').val("");
							$('#numExpAltSpan').val($(xml).find('numeroExpedienteAlterno').text());
						} */
		    	  }
		    	});
		}

		function buscarNumerosDeExpediente(){
	    	$('#cbxNumerosExpediente').addClass("cargando");

			$('#cbxNumerosExpediente').empty();
			 $.ajax({
			      type: 'POST',
		    	  url: '<%= request.getContextPath()%>/buscarNumerosExpediente.do?idExpedienteop='+idExpedienteop,
		    	  data: '',
		    	  dataType: 'xml',
		    	  async: true,
		    	  success: function(xml){
		    		  $(xml).find('numero').each(function(){
		    			  $('#cbxNumerosExpediente').append('<option value="' + "" + '">'+ 
			                        $(this).text()+ '</option>');
		    			});
	        		$('#cbxNumerosExpediente').removeClass("cargando");
		    	  }
		    	});
		}

		function documentos(){
			 jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
						{url:'<%= request.getContextPath()%>/consultarDocumentos.do?idExpedienteop='+idNumeroExpedienteConsul, 
						datatype: "xml" ,
						success: function(xml){
							alertDinamico('<%=request.getSession().getAttribute("totalRegistrosDocumentos")%>');	 
						 } 
						});
					 $("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
		}
		
		
		function alertas(){
			 jQuery("#gridDetalleAlertas").jqGrid('setGridParam',  
						{url:'<%= request.getContextPath()%>/consultarAlertas.do?idExpedienteop='+idNumeroExpedienteConsul, 
						datatype: "xml" });
					 $("#gridDetalleAlertas").trigger("reloadGrid"); 
		}
		
		/************ FUNCION PARA OCULTAR-MOSTRAR LOS TABS DEL VISOR***************/
		function ocultaMuestraTabVisor(claseTab,bandera)
		{
			if(parseInt(bandera)===0)//oculta
			{
				$("."+claseTab).hide();
			}
			else///muestra
			{
				$("."+claseTab).show();
			}
		}

		//Variable para controlar el action para consultar pdf's
		var accionConsultarPdf="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do";

		/*
		*Funcion que aplica submit a la forma para abrir el documento solicitado
		*id= id del documento seleccionado en el grid de documentos
		*Así se obtenia anteriormente:
		*	
		*document.frmDoc.documentoId.value = id;
		*document.frmDoc.submit();
		*/
		function consultaPDF(id){
			
			document.frmDoc.action=accionConsultarPdf+"?documentoId="+id;
			document.frmDoc.submit();
		}
		
		function cerrarVentanaProableResponsable(){		
			$.closeWindow('iframewindowIngresarProbResponsable');	
		}
		
		
		/************ FIN FUNCION PARA OCULTAR-MOSTRAR LOS TABS DEL VISOR***************/
		
		
		function ocultaMuestraTblsRelacionarDelitos()
		{
			var relacionDelitoPorPErsonaDelito = $(':radio[name=relacionaDelitos]:checked').val();
			if(parseInt(relacionDelitoPorPErsonaDelito)===0)
			{
				//Relacion por persona
				$("#tblRelacionaDelXPersona").show();
				$("#tblRelacionaDelXDelito").hide();
				$("#tblRelacionaDelXTodos").hide();
			}
			else if(parseInt(relacionDelitoPorPErsonaDelito)===1)
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

		function cerrarVentanaEnvio(){
			$windowCloseButton.click();
				
			}
		
		function abreLineasInvestiga(){		
			$.newWindow({id:"iframewindowLineaInvestigacion", statusBar: true, posx:20,posy:20,width:1300,height:550,title:"Investigación", type:"iframe"});
	        $.updateWindowContent("iframewindowLineaInvestigacion",'<iframe src="<%= request.getContextPath() %>/lineasInvestigacion.do?numeroUnicoExpediente='+numeroExpediente+'&idNumeroUnicoExpediente='+idNumeroExpedienteConsul+'&pantalla='+pantallaSolicitada+'"    width="1300" height="550" />');
	        $("#" +"iframewindowLineaInvestigacion" + " .window-maximizeButton").click();
		}
		
		/*
		*Funcion que carga el grid con los nombre de los documentos digitales asociados 
		*al id de la solicitud de visitaduria
		*/
		function cargaGridDocumentosDigitales(){
			if(primeraVezGridDocumentosDigitales === true){
				jQuery("#gridDocumentosDigitales").jqGrid({
					url:'<%=request.getContextPath()%>/consultarDocumentos.do?idExpedienteop='+idNumeroExpedienteConsul+'',
					datatype: "xml", 
					colNames:['Area del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento'],
					colModel:[ 	{name:'area',index:'area', width:200},
								{name:'fechaActividad',index:'fechaActividad', width:170},							
								{name:'nombreActividad',index:'nombreActividad', width:400},
					           	{name:'tipo',index:'tipo', width:155}, 
								{name:'nombre',index:'nombre', width:255},
					           	{name:'fecha',index:'fecha', width:170}
								],
					pager: jQuery('#pagerGridDocumentosDigitales'),
					rowNum:20,
					rowList:[10,20,30],
					width:250,
					sortname: 'nombreDocumento',
					viewrecords: true,
					sortorder: "desc",
					multiselect:true,
					ondblClickRow: function(rowid) {
						if (rowid) {
							abrirDocsDigAsociadosASol(rowid);							
						}
					},
					loadComplete: function(){
						jQuery("#gridDocumentosDigitales").jqGrid('hideCol',["area","fechaActividad","nombreActividad","tipo","fecha"]);
					}
				}).navGrid('#pagerGridDocumentosDigitales',{edit:false,add:false,del:false});
				$("#gview_gridDocumentosDigitales .ui-jqgrid-bdiv").css('height', '455px');
				primeraVezGridDocumentosDigitales= false;
			}
			else{
				jQuery("#gridDocumentosDigitales").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDocumentos.do?idExpedienteop='+numeroExpedienteId+'',datatype: "xml" });
				$("#gridDocumentosDigitales").trigger("reloadGrid");
			}
			$("#gview_gridDocumentosDigitales .ui-jqgrid-bdiv").css('height', '430px');
		}
		
		/*
		*Funcion que abre el PDF para ver los documentos asociados al numero de causa
		*/
		function abrirDocsDigAsociadosASol(documentoId){
			if(documentoId !== null && documentoId !== ""){
				$("#visorDocsFrame").attr("src","<%= request.getContextPath()%>/ConsultarContenidoArchivoDigital.do?documentoId="+documentoId+"&inFrame=true");
			}
			else{
				alertDinamico("El documento no puede ser mostrado");
			}
		}
		
		function elaboraNotificacionAuditoria()
		{	
			var actividad=0;
			var formaID=4;
			var titulo="op";
			var usaeditor="";
			var estatusId="";
			//consultamos la forma para la notificacion de auditoria
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/obtenerConfActividadDocumento.do?idConf=135',
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
			$.newWindow({id:"iframewindowElaborarSolicitud", statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Notificación Auditor&iacute;a", type:"iframe"});
            $.updateWindowContent("iframewindowElaborarSolicitud",'<iframe src="<%= request.getContextPath() %>/elaborarNotificacionAuditoria.do?formaId='+formaID+'&numeroUnicoExpediente='+idNumeroExpedienteOp+'"    width="1140" height="550" />');
            $("#" +"iframewindowElaborarSolicitud" + " .window-maximizeButton").click();
		}
		/*
		
		/*
		*Funcion que abre el PDF para ver los documentos asociados al numero de causa
		*/
		function abrirDocsDigAsociadosASol(documentoId){
			if(documentoId !== null && documentoId !== ""){
				$("#visorDocsFrame").attr("src","<%= request.getContextPath()%>/ConsultarContenidoArchivoDigital.do?documentoId="+documentoId+"&inFrame=true");
			}
			else{
				alertDinamico("El documento no puede ser mostrado");
			}
		}
		
		/*
		*Funcion para regresar el numero de relacionde delito-persona o delito-delito con 
		*las que cuenta el expediente
		*/
		function consultaTotalRelacionesDelitoPorTodos()
		{
			var numeroRelaciones = 0;
			//consultamos la forma para la notificacion de auditoria
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultarTamanoDelitosAsociadosPorTodos.do?idExpediente='+idExpedienteop+'',
				data: '',
				dataType: 'xml',
				async: false,
				success: function(xml){
					numeroRelaciones=$(xml).find('relacionTodosLosDelitos').find('tamanoLista').text();
				}
			});
			return numeroRelaciones;
		}

		/*
		*Funcion que lanza la ventana par asoliciutar una transcripcion de audiencia y de audio y video
		*/
		function muestraSolicitudTranscripcion()
		{
			
			idWindowPantallaActuaciones++;
			var actividad=0;
			var formaID=0;
			var titulo="";
			var usaeditor="";
			var estatusId="";
			var nombreActividad="";
			
			var confActividadDocumentoId = '<%=ConfActividadDocumento.ELABORAR_SOLICITUD_DE_TRANSCRIPCION_DE_AUDIENCIA_EN_PG.getValorId()%>';
		
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
			
 			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&actividadId='+actividad+'&esTransaccional='+true+'" width="1140" height="400" />');
		    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
	    }

		
		
		
		/*
		*Funcion que permite saber si un expedietn tiene un delito grave
		*/
		function existeDelitoGrave()
		{			
	    	$.ajax({
	    		type: 'POST',
	    		url:'<%= request.getContextPath()%>/ExisteDelitoGravePorIdExpediente.do?idNumeroExpediente='+idExpedienteop+'&numeroExpedienteId='+numeroExpedienteId+'',
	    		data: '',
	    		async: false,
	    		dataType: 'xml',
	    		success: function(xml){
	    			existeDelitoGraveEnExpediente = $(xml).find('boolean').text();
	    		}
	    	});
	    	return existeDelitoGraveEnExpediente;
		}
		
		/*
		*Funcion que permite saber si un expediente tiene un probable respnsable reincidente
		*/
		function existeProbableResponsableReincidente()
		{
			var existeProbRespReincidente = "";

			$.ajax({
   				type: 'POST',
   				url: '<%=request.getContextPath()%>/existeReincidenciaDeProbablesResponsables.do',
   				data: 'numeroExpediente='+numeroExpediente+'',
   				dataType: 'xml',
   				async: false,
   				success: function(xml){
   					existeProbRespReincidente = $(xml).find('boolean').text();  
   				}
            });

			//se regresa la bandera que indica que hubo o no un PR reincidente
			return existeProbRespReincidente;
		}

		/*
		*Funcion que permite saber la Media aritmética de los delitos NO exceda de 4 años (definido en tabla Parametro)
		*/
		function excedeMediaAritmeticaDelitos()
		{
			var resultado = "";

			$.ajax({
   				type: 'POST',
   				url: '<%=request.getContextPath()%>/excedeMediaAritmeticaDelitos.do',
   				data: 'numeroExpedienteId='+numeroExpedienteId+'',
   				dataType: 'xml',
   				async: false,
   				success: function(xml){
   					resultado = $(xml).find('boolean').text();
   				}
            });
            
			//se regresa la bandera que indica que hubo o no un PR reincidente
			return resultado;
		}
		
		function cierraVentanaRegistrarAmparo(){
			var pantalla ="iframewindowRegistrarAmparo";
			//pantalla += idWindowIngresarProbResponsable;
			$.closeWindow(pantalla);
		}		

		function consultaTipoExpediente()
		{
			$.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/consultaOrigenExpediente.do',
	    		data: 'idExpediente='+idExpedienteop,
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){
	    				tipoExpediente=$(xml).find('valorDTO').find('idCampo').text();	    				
	    				tipoExpediente = tipoExpediente === "" ? 0: tipoExpediente;
	    		}	
	    	});
			return tipoExpediente;
		}


		
		
		function cambiaTextoVisor(idTipoExpediente){
			//Unicamente para atpenal
			if(pantallaSolicitada === ATPENAL){
				var tituloOriginal =window.parent.recuperaTituloVisor(idIframe);
				var nuevoTitulo = "";
				if(tituloOriginal.toLowerCase().substring(0, 10) === "expediente"){
					nuevoTitulo =  (idTipoExpediente === 2 ? "Reporte" : "Expediente")  + tituloOriginal.substring(10, tituloOriginal.length);
					window.parent.cambiarTituloVisor(idIframe,nuevoTitulo)
				}else{
					if(tituloOriginal.toLowerCase().substring(0, 7) === "reporte"){
						nuevoTitulo =  (idTipoExpediente === 2 ? "Reporte" : "Expediente")  + tituloOriginal.substring(7, tituloOriginal.length);
						window.parent.cambiarTituloVisor(idIframe,nuevoTitulo);
					}					
				}	
			}			
		}
				
		function validarReporte(){
			if(validaTipoExpedienteReporte !=null && (validaTipoExpedienteReporte === "1" || validaTipoExpedienteReporte===1)) {
				return true;
			}
			return false;
		}
		
		
		function validacionDeDelitoUSC(actividad,estatusId,titulo, formaID, numeroExpediente){
			existeDelitoGrave();					
			if(existeDelitoGraveEnExpediente != ""){
				//Valida Delito Grave deshabilitado
				if(validaDelitoGrave !=null && (validaDelitoGrave === "0" || validaDelitoGrave===0)) {
					idWindowPantallaActuaciones++;
	     			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
	    		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&actividadId='+actividad+'&idNumeroExpediente='+idNumeroExpedienteOp+'&esTransaccional='+true+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'" width="1140" height="400" />');
				}
				else{
					if( existeDelitoGraveEnExpediente === "true"){
						customConfirm ("Existe delito grave. ¿Desea enviar a la unidad de controversias?", "Aviso", 
								function(){
									canalizarControversiaExisteDelitoGrave(actividad,estatusId,titulo, formaID, numeroExpediente );
								},
								recargarActuaciones()
								);
					}
					else{
						canalizarControversiaExisteDelitoGrave(actividad,estatusId,titulo, formaID, numeroExpediente );
					}
				}
			}else
				alertDinamico("No existen delito(s) registrado(s) en el expediente");
		}
		
		function validacionUFI(actividad,estatusId,titulo, formaID, numeroExpediente, numeroExpedienteId){
			existeDelitoGrave();	
			if(existeDelitoGraveEnExpediente !== ""){
				//Valida Delito Grave deshabilitado 
				 if(validaDelitoGrave !==null && (validaDelitoGrave === "0" || validaDelitoGrave===0)) {
					//Mostrar ventana de Canalización a la Unidad de Fiscales Investigadores
						idWindowPantallaActuaciones++;
		     			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:"", type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
		    			$.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath()%>/canalizarAUnidadFiscalesInv.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&actividadId='+actividad+'&numeroExpedienteId='+numeroExpedienteId+'&esTransaccional='+true+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'" width="1140" height="400" />');
				 }
				 else{
					 if( existeDelitoGraveEnExpediente === "false"){
						 customConfirm ("No existe delito grave. ¿Desea enviar a la unidad de fiscales investigadores?", "Aviso", 
									function(){
										canalizarInvestigadoresNoExisteDelitoGrave(actividad,estatusId,titulo, formaID, numeroExpediente );
									},
									recargarActuaciones()
									);
					 }
					 else{
						 canalizarInvestigadoresNoExisteDelitoGrave(actividad,estatusId,titulo, formaID, numeroExpediente );
					 }
					 
				 }
			}
			else
				alertDinamico("No existen delito(s) registrado(s) en el expediente");
		}
                
                
 (function ($) {
  jQuery.expr[':'].Contains = function(a,i,m){
      return (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase())>=0;
  };

  function filterList(header, list1, list2) { 
    var form = $("<form>").attr({"class":"filterform","action":"#"}),
        input = $("<input>").attr({"class":"filterinput","type":"text","size":"80"});
    $(form).append(input).appendTo(header);
 
    $(input).change( function () {
        var filter = $(this).val();
       
                                    
        if(filter) {
            $matches1 = $(list1).find('a:Contains(' + filter + ')').parent();
            $('#ofic').empty();
            $('#ofic').append(" (" + $matches1.size() +"): ");
            $matches2 = $(list2).find('a:Contains(' + filter + ')').parent();
            $('#act').empty();
            $('#act').append(" (" + $matches2.size() +"): ");
            
            $('li', list1).not($matches1).slideUp();
            $('li', list2).not($matches2).slideUp();
            $matches1.slideDown();
            $matches2.slideDown();
        } else {
            $(list1).find("li").slideDown();
            $(list2).find("li").slideDown();
        }
        return false;
    }).keyup( function () {
        $(this).change();
       });
  }
 
  $(function () {
    filterList($("#formA"), $("#cbxOficiosTab"), $("#cbxAccionesTab"));
  });
}(jQuery));
         
                


	</script>
	
	<!--ARCHIVOS PARA MENU DE ARBOL-->        
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/simpletreemenu.js"></script>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/simpletree.css" />
	<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/menu.css"  type="text/css">
	
	<!--ESTILOS PARA LAS TABS-->
	<style>
	#tabs { height: 670px; } 
	.tabs-bottom { position: relative; } 
	.tabs-bottom .ui-tabs-panel { height: 500px; overflow: auto; } 
	.tabs-bottom .ui-tabs-nav { position: absolute !important; left: 0; bottom: 0; right:0; padding: 0 0.2em 0.2em 0; } 
	.tabs-bottom .ui-tabs-nav li { margin-top: -2px !important; margin-bottom: 1px !important; border-top: none; border-bottom-width: 1px; }
	.ui-tabs-selected { margin-top: -3px !important; }
	</style>
	
	<script type="text/javascript">
		$(function(){
			// Tabs
			$('#tabs').tabs();
				
			//hover states on the static widgets
			$('#dialog_link, ul#icons li').hover(
				function() { $(this).addClass('ui-state-hover'); }, 
				function() { $(this).removeClass('ui-state-hover'); }
			);
				
		});
		
		function recargaGridAgenteMP(){
			if(flagIndexProcView===1){
				try{
					if(ingresoDenuncia==='false'){
						window.parent.regresaGrid();
					}
				}catch(e){};
			}
		}


//**************************COMIENZAN FUNCIONES PARA CONSULTAR SI SE GENERA UN NUMERO DE EXPEDIENTE ALTERNO******************************************/
		
		/*
		*Funcion para consultar el consecutivo del numero de expediente alterno
		*/
		<%-- function consultarConsecutivoNumeroExpAlterno(){
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/consultarConsecutivoNumeroExpAlterno.do',
				data: 'numeroExpedienteId='+idNumeroExpedienteOp, 
				async: false,
				dataType: 'xml',
				success: function(xml){
					var errorCode;
					
	    			errorCode=$(xml).find('response').find('code').text();
	    			//Si errorCode=0 entonces continuamos con el flujo
	    			if(parseInt(errorCode)===0){
		    			
	    				var numExpAlt = $(xml).find('body').find('respuesta').text();
	    				$("#numExpAltSpan").val(numExpAlt);
	    			}					
	    			else{
		    			if(errorCode === '<%=CodigoError.CLAVE_ROMANA_DISTRITO_INEXISTENTE.toString()%>'){
		    				$("#numExpAlterTable").hide();
							customAlert("No se ha podido generar el n\u00famero de expediente alterno\ndebido a que el distrito no tiene clave romana");
			    		}
		    		}
				}
			});
		} --%>
		/*
		*Funcion que verifica si la bandera del numero de expediente alterno, se encuentra encendida
		*para sustituir el numero de expediente, por el numero de expediente alterno
		*/
		function consultaParametroNumExpAlterno(){

			var idParametro = '<%=Parametros.NUMERO_EXPEDIENTE_ALTERNO.ordinal()%>';
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
		*Funcion que controla la generacion del numero de expediente alterno
		*/
		<%-- function controlNumeroExpedienteAlterno(){
			
			var numExpAlter = '<%=request.getParameter("numExpAlter")%>';
			
			if(numExpAlter != "null"){
				numExpAlter = true
			}
			else{
				numExpAlter = false;
			}
			
			if(numExpAlter === true){
				if(consultaParametroNumExpAlterno() === '1'){
					consultarConsecutivoNumeroExpAlterno();
				}
			}
			else{
				if($('#numExpAltSpan').val() === ""){
					$("#numExpAlterTable").hide();
				}
			}
		} --%>
		/*
		*Funcion para validar si se puede realizar la solicitud de
		*defensor
		*/
		function validarEjecucionSolicitudDeDefensor(actividad,estatusId,titulo, formaID){

			var parametros='';
			
			if (idNumeroExpedienteOp !== undefined && idNumeroExpedienteOp !== null
					&& idNumeroExpedienteOp !== "") {
				parametros += "idNumeroExpediente=" + idNumeroExpedienteOp;
			}
			
			if (idExpedienteop !== undefined && idExpedienteop !== null
					&& idExpedienteop !== "") {
				parametros += "&idExpediente=" + idExpedienteop;
			}
			
			if (numeroExpediente !== undefined && numeroExpediente !== null
					&& numeroExpediente !== "") {
				parametros += "&numeroExpediente=" + numeroExpediente;
			}
			
			ejecutaAction("/validarSolicitudDeDefensor", function(respuesta){					
				if(parseInt($(respuesta).find('code').text())===0){
					if($(respuesta).find('body').text() !== null 
							&& $(respuesta).find('body').text() !== "null"
								&& $(respuesta).find('body').text() !== ""){
						if($(respuesta).find('body').text() === "success"){
							solicitarDefensor(actividad,estatusId,titulo, formaID);
						}else{
							alertDinamico($(respuesta).find('body').text());								
						}
					}
				}else{
					alertDinamico("Ocurri&oacute; un error al intentar enviar la solicitud de defensor.<br/>" +
						"Por favor contacte al administrador");
				}
			} , parametros);
		}
		//variable para controlar el id de la ventana de solicitar defensor
		var windowIdSolicitarDefensor = 1;

		/*
		*Funcion para solicitar un defensor a la institucion de defensoria
		*/
		function solicitarDefensor(actividad,estatusId,titulo, formaID){

			//Ya registra la actividad del documento en el guardado definitivo
			//registrarActividadExpediente(actividad,estatusId,0);
			var parametrosVentana = "";

			//id de la ventana solicitar defensor
			var idWindow = "iframewindowSolicitarDefensor" + windowIdSolicitarDefensor;

			//parametros para el editor de texto
			parametrosVentana += "&formaId=" + formaID;
			parametrosVentana += "&actividadId=" + actividad; 
			parametrosVentana += "&estatusId=" + estatusId;
			parametrosVentana += "&titulo=" + titulo;

			//parametros para ventana solicitar defensor 
			parametrosVentana += "&idExpediente=" + idExpedienteop;
			parametrosVentana += "&numeroExpediente=" + numeroExpediente;
			parametrosVentana += "&idNumeroExpediente=" + idNumeroExpedienteOp;
			parametrosVentana += "&idWindow=" + idWindow;
			
		    $.newWindow({id:"iframewindowSolicitarDefensor" + windowIdSolicitarDefensor, statusBar: true, posx:20,posy:20,width:800,height:360,title:"Solicitar Defensor", type:"iframe"});
		    $.updateWindowContent("iframewindowSolicitarDefensor"+windowIdSolicitarDefensor,'<iframe src="<%= request.getContextPath() %>/solicitarDefensor.jsp?parametrosVentana='+parametrosVentana+'" width="800" height="360"/>');
			windowIdSolicitarDefensor++;			
		}
		/*
		*Funcion abrir el editor de texto para enviar la solicitud de defensor
		*/
		function abrirEditorSolicitudDeDefensor(formaId,actividadId,estatusId,titulo){

			idWindowPantallaActuaciones++;

			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaId+'&numeroUnicoExpediente='+numeroExpediente+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&actividadId='+actividadId+'&esTransaccional='+true+'" width="1140" height="400" />');
		    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
		}
		/*
		*Cierra la ventana de confirmacion de solicitud de defensor
		*/
		function cerrarVentanaSolicitudDeDefensor(idWindow){
			$.closeWindow(idWindow);
		}
		function cerrarVentanaGenerarDocumentoDefensor(idWindow){

			var idCompleto = "iframewindowGenerarDocumento"+idWindow;
			
			$.closeWindow(idCompleto);
		}
		/*
		*  Funcion que carga la informacion de las solicitudes periciales del expediente activo.
		*/
		function cargaGridsPericiales(){
			jQuery("#gridSolicitudesPeri1").trigger("reloadGrid");
			jQuery("#gridSolicitudesPeri2").trigger("reloadGrid");
			jQuery("#gridSolicitudesPeri3").trigger("reloadGrid");
		}
		function abreVentanaReasignarUIEExpediente(){
			if(typeof(idExpedienteop) !== "undefined" && typeof(idExpedienteop) !== "null" && idExpedienteop !== ""){
				$.newWindow({id:"iframewindowReasignarUIEDeExpediente", statusBar: true, posx:50,posy:50,width:900,height:300,title:"Reasignar Unidad de Investigaci&oacute;n Especializada", type:"iframe"});
				$.updateWindowContent("iframewindowReasignarUIEDeExpediente",'<iframe src="' + contextoPagina + '/reasignarUIEDeExpediente.jsp?idExpediente='+idExpedienteop+'" width="900" height="300" />');
			}else{
					alert("Imposible reasignar la Unidad de Investigaci&oacute;n especializada");
			}   
		}
		function cargaGridJudiaciales(){
			
			jQuery("#gridDetalleFrmPrincipalAudiencias").jqGrid({
				url:'<%=request.getContextPath()%>/consultarAudiencias.do?numeroExpediente='+numeroExpediente, 
				datatype: "xml",
				colNames:['Folio','Estatus','Fecha Creaci&oacute;n','Fecha Limite','Tribunal','Solicitante','Destinatario','Tipo Audiencia'],
				colModel:[ 	{name:'folio',index:'folio', width:200,align:'center'},
				           	{name:'estatus',index:'estatus', width:200,align:'center'},
							{name:'fecha',index:'fecha', width:170,align:'center'},							
							{name:'limite',index:'limite', width:400,align:'center'},
				           	{name:'institucion',index:'institucion', width:155,align:'center', hidden:true}, 
				           	{name:'solicitante',index:'solicitante', width:255,align:'center'},
				           	{name:'destinatario',index:'destinatario', width:255,align:'center'},
							{name:'tipoAudiencia',index:'tipoAudiencia', width:255,align:'center'}
				           ],
				pager: jQuery('#pagerAudiencias'),
				rowNum:10,
				rowList:[10,20,30,40,50],
				autowidth: false,
				width:1100,
				sortname: 'fecha',
				viewrecords: true,
				id: 'divgridAudiencias',
				onSelectRow: function(id){
					//consultaPDF(id);
					},
				sortorder: "desc"
			}).navGrid('#pagerAudiencias',{edit:false,add:false,del:false,search:false});
			$("#gview_gridDetalleFrmPrincipalAudiencias .ui-jqgrid-bdiv").css('height', '500px');
			
			jQuery("#gridDetalleFrmPrincipalAudiencias").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarAudiencias.do?numeroExpediente='+numeroExpediente, datatype: "xml"});
			$("#gridDetalleFrmPrincipalAudiencias").trigger("reloadGrid");
		}
		/*
		*Funcion que consulta y carga el grid con las solicitudes policia ministerial
		*/
		function cargaGridSolicitidesPoliMinister(){
			console.info("idExpedienteDeli: " + idExpedienteDeli);
			jQuery("#gridSolicitudesPoli").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarSolicitudesPoliciaMinisterial.do?idExpedienteop='+idExpedienteDeli, 
				datatype: "xml", 
				colNames:['Funcionario solicitante','Fecha solicitud', 'Funcionario destinatario'], 
				colModel:[ 	{name:'solicitante',index:'solicitante', width:100,align:'center',sortable: false},
				           	{name:'fecha',index:'fecha', width:100,align:'center',sortable: false}, 
							{name:'destinatario',index:'destinatario', width:100,align:'center',sortable: false}
						],
				pager: jQuery('#pagerGridSolicitudesPoli'),
				rowNum:5,
				rowList:[5,10,15],
				autowidth: true,
				caption:"INVESTIGACIONES SOLICITADAS",
				sortname: 'caso',
				viewrecords: true,
				sortorder: "desc"
			});
		}
		/*
		*  Funcion que carga la informacion de las solicitudes periciales del expediente activo.
		*/
		function cargaGridPoliciaMinisterial(){
			jQuery("#gridSolicitudesPoli").trigger("reloadGrid");
		}
		/*
		*Funcion para recargar el grid de solicitudes de acc. penal privada
		*cuando esta en estatus de NO atendidas.
		*function window.parent.cargaGirdSolicitudesAccPenalPrivadaPJENC, residente
		*en el archivo: encargadoCausas.jsp
		*/
	  	function recargarBandejaAccPenalPriv(){
		  	
	  		if(<%=request.getParameter("recargarBandejaAccPenalPriv")%> === true 
	  		  		&& typeof window.parent.cargaGirdSolicitudesAccPenalPrivadaPJENC === 'function'){
				window.parent.cargaGirdSolicitudesAccPenalPrivadaPJENC('<%=EstatusTurno.ESPERA.getValorId()%>');
		  	}
		}
		//Variable para controlar la apertura del visor de solicitudes de audiencia
		var idWindowVisorAtnPublicoSol = 0;
		
		/*
		*Funcion que crea el visor de solicitudes para una nueva solicitud
		*/		
		function nuevaSolicitudPJATP() {

			if( idWindowVisorAtnPublicoSol === 0){
				var numeroDeCausa=numeroExpediente;
				var accPenalPrivada="true";
	
				idWindowVisorAtnPublicoSol++;
				$.newWindow({id:"iframewindowVisorAtnPublicoSolicitudes"+idWindowVisorAtnPublicoSol, statusBar: true, posx:255,posy:111,width:1000,height:400,title:"Atenci&oacute;n de Solicitudes", type:"iframe",onWindowClose: function(id){
					idWindowVisorAtnPublicoSol--;
				}});
		    	$.updateWindowContent("iframewindowVisorAtnPublicoSolicitudes"+idWindowVisorAtnPublicoSol,'<iframe src="<%= request.getContextPath() %>/crearNuevaSolicitud.do?numeroDeCausa='+numeroDeCausa+'&accPenal='+accPenalPrivada+'" width="1000" height="400" />');
			}else{
				customAlert("Ya cuenta con una ventana abierta.<br> Por favor cierrela, e int&eacute;ntelo nuevamente",'<bean:message key="aviso"/>');
			}
		}
		/*
		*Funcion que despues de enviar la solicitud de audiencia, cierra la ventan
		*/
		function cerrarVentanaNuevaSolicitud(){
			$.closeWindow("iframewindowVisorAtnPublicoSolicitudes"+idWindowVisorAtnPublicoSol);
		}
		/*
		  *Funcion que realiza la carga del combo de tipo conclusion
		  */
		  function cargaTipoConclusion() {
			  
			$('#cbxTipoConclusion').empty();
			$('#cbxTipoConclusion').append('<option value="-1">-Seleccione-</option>');
			
			$.ajax({
			  type: 'POST',
			  url: '<%= request.getContextPath()%>/cargarTipoConclusion.do',
			  data: '',
			  dataType: 'xml',
			  async: false,
			  success: function(xml){
				var option;
				$(xml).find('catTipoConclusion').each(function(){
					$('#cbxTipoConclusion').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
			    });			
			  }
			});
		  }
		  function cargaCalendarioTipoConclusion(){
				
				$("#fechaConclusion").datepicker({
					dateFormat: 'dd/mm/yy',
					yearRange: '-111:+0',
					maxDate: fechaMax,
					onSelect: function(date) {
						$("#idHora").val(timeMax);
						
					}, 
					changeMonth: true,
					changeYear: true,
					showOn: "button",
					buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
					buttonImageOnly: true			
				});
			}
		  /*
		  *Funcion que realiza la carga del combo de tipo subConclusion
		  */
		  function cargaTipoSubConclusion() {
		  	var tipoConclusion = $("#cbxTipoConclusion option:selected").val();
			if(tipoConclusion !== <%=ConstantesGenerales.HECHOS_TIPO_CONCLUSION_ARCHIVO_TEMP_ID%> && tipoConclusion !== "-1" ){
				
				tieneSubConclusion = true;
				$('#cbxTipoSubConclusion').empty();
				$('#cbxTipoSubConclusion').append('<option value="-1">-Seleccione-</option>');
				$('#cbxTipoSubConclusion').show();
				
				$.ajax({
				  type: 'POST',
				  url: '<%= request.getContextPath()%>/cargarTipoSubConclusion.do?tipoConclusion='+tipoConclusion+'',
				  data: '',
				  dataType: 'xml',
				  async: false,
				  success: function(xml){
					var option;
					$(xml).find('catTipoSubConclusion').each(function(){
						$('#cbxTipoSubConclusion').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				    });			
				  }
				});
			}else{
				$('#cbxTipoSubConclusion').hide();
			}
		  }
		  function guardarConclusion(){
			  var tipoConclusion = $("#cbxTipoConclusion option:selected").val();
			  var subTipoConclusion = $("#cbxTipoSubConclusion option:selected").val();
			  var fechaConclusion = $("#fechaConclusion").val();
			  $.ajax({
					type: 'POST',
					url: '<%=request.getContextPath()%>/guardarConclusionNumeroExpe.do',
					data: 'tipoConclusion='+ tipoConclusion+'&subTipoConclusion='+subTipoConclusion+'&fechaConclusion='+fechaConclusion+'&idNumeroExpediente='+idNumeroExpedienteOp, 
					async: false,
					dataType: 'xml',
					success: function(xml){					
						var resp=$(xml).find('boolean').text();
						if(resp==="true"){
							$('#btnConclusionExpe').hide();
						}
					}
				});
			
    	      
		  }
		  function consultarConclusion(){
			  $.ajax({
					type: 'POST',
					url: '<%=request.getContextPath()%>/consultarConclusionNumeroExpe.do',
					data: 'idNumeroExpediente='+ idNumeroExpedienteOp, 
					async: false,
					dataType: 'xml',
					success: function(xml){					
						
			    	      var tipoConclusion=$(xml).find('conclusionNumeroExpedienteDTO').find('tipoConclusion').find('idCampo').text();
			    	      $('#cbxTipoConclusion').find("option[value='"+tipoConclusion+"']").attr("selected","selected");
			    	      cargaTipoSubConclusion();
			    	      var tipoSubConclusion=$(xml).find('conclusionNumeroExpedienteDTO').find('tipoSubConclusion').find('idCampo').text();
			    	      $('#cbxTipoSubConclusion').find("option[value='"+tipoSubConclusion+"']").attr("selected","selected");
			    	      var fecha=$(xml).find('conclusionNumeroExpedienteDTO').find('fechaConclusionFortmat').text();
			    	      $("#fechaConclusion").val(fecha);
			    	      $('#btnConclusionExpe').hide();
					}
				});
			// cargaTipoConclusion();
    	      //var tipoConclusion=$(xml).find('conclusionHechoDTO').find('tipoConclusion').find('idCampo').text();
    	     // $('#cbxTipoConclusion').find("option[value='"+tipoConclusion+"']").attr("selected","selected");
    	     // cargaTipoSubConclusion();
    	    //  var tipoSubConclusion=$(xml).find('conclusionHechoDTO').find('tipoSubConclusion').find('idCampo').text();
    	    //  $('#cbxTipoSubConclusion').find("option[value='"+tipoSubConclusion+"']").attr("selected","selected");
		  }
                  
                  
                 /*
		*Funcion para saber si se selecciono un delito como principal
		*/
		function existeUnDelitoPrincipalGraveSeleccionado()
		{
			var bandera=0;
			//obtengo el ID del rdb del delito seleccionado
			var idRdb="";
			idRdb=$('input[name=gridDelitos]:checked').attr('id');
			if(idRdb!==null)
			{
				idRdb=idRdb.split('_')[1];
				if(idRdb!=="")
				{
					var resp=jQuery("#gridDelitosAgraviados").jqGrid('getRowData',idRdb);
					if(resp.Gravedad==="Yes")
					{
						//prendemos la badnera al encontrar un radio seleccionado
						bandera=1;	
					}
				}	
			}
			else
			{
				alertDinamico("Debe seleccionar un delito principal para poder guardar");	
				bandera=2;
			}
			return bandera;
		}
                  
                  
                /*
		* Funcion que recorre el grid de delitos agraviados para revisar si existe 
		*un delito grave que no fue seleccionado como principal, de existir regresa true en 
		*caso contrario regresa false
		*/
		function existeDelitoGraveEnGrid()
		{
			var bandera=false;
			//obtenemos los ID's de los renglones del Grid
			var arrayIDs = new Array() ;
			var arrayIDs=jQuery("#gridDelitosAgraviados").getDataIDs();
			//barremos el grid para revisar si hay por lo menos un delito marcado como grave
			for (i=0;i<arrayIDs.length;i++)
			{
				//revisamos el checkbox del renglon i-esimo para ver si es grave
				var isGrave=jQuery("#gridDelitosAgraviados").jqGrid('getRowData',arrayIDs[i]);
				if(isGrave.Gravedad==="Yes")
				{
					bandera=true;
				}
			} 
			return bandera;
		}
                  
                
                  
               /*
		* Funcion que revisa que exista un delito grave en el grid de delitos denunciados 
		*/		
		function existeUnDelitoPrincipalEnGrid()
		{			
			var idRdb="";
			var bandera1=0;
			var bandera2=1;
			
			idRdb=$('input[name=gridDelitos]:checked').attr('id');
			if(idRdb!==null)
			{
				idRdb=idRdb.split('_')[1];
				if(idRdb!=="")
				{
					var resp=jQuery("#gridDelitosAgraviados").jqGrid('getRowData',idRdb);
					if(resp.Gravedad==="No")
					{
						//prendemos la bandera al encontrar un radio seleccionado
						bandera1=1;	
					}
				}	
			}
			else
			{				
				bandera1=0;
			}
			
			var arrayIDs = jQuery("#gridDelitosAgraviados").getDataIDs();
			
			for (i=0;i<arrayIDs.length;i++)
			{								
				var row = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',arrayIDs[i]);
			
				if(row.GravedadFormateada==="Si") bandera2=0; 
			} 					
			
			if(bandera2===1 && bandera1===0)
			return 0;
			else return 1;
		}     

          /*
		*Funcion para asociar los delitos del grid de agraviados con el 
		*expediente
		*/
		function guardarDelitosAgraviadosExp()
		{
			//obtenemos los ID's de los renglones del Grid de Agraviados
			var arrayIDs = new Array() ;
			var arrayIDs=jQuery("#gridDelitosAgraviados").getDataIDs();			
			
			//validamos que haya delitos en el grid de agraviados
			if(arrayIDs.length<1)
			{
				alertDinamico("Debe agregar al menos un delito agraviado");
				return;
			}
			
			//validamos si no hay un delito grave, que al menos exista un delito principal
			if(parseInt(existeUnDelitoPrincipalEnGrid())===0){
				alertDinamico("Debe seleccionar un delito principal");	
				return;
			}
			
			//revisamos que si hay un delito grave se haya seleccionado
			if(existeDelitoGraveEnGrid())
			{
				if(parseInt(existeUnDelitoPrincipalGraveSeleccionado())===0)
				{
					alertDinamico("Debe seleccionar un delito grave como principal");
					return;
				}	
			}
			//obtenemos el ID del delito principal
			var idDelPrincipal=$('input[name=gridDelitos]:checked').attr('id').split('_')[1];
			var nombreDelPrincipal=jQuery("#gridDelitosAgraviados").jqGrid('getRowData',idDelPrincipal).Tipo;
			var delitosNormales="";			
			//barremos el grid para generar la cadena de IDs de los delitos normales
			for (i=0;i<arrayIDs.length;i++)
			{
				if(arrayIDs[i]!==idDelPrincipal)
				{
					var retd = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',arrayIDs[i]);
					if(delitosNormales.length>0)
					{
						delitosNormales=delitosNormales+","+retd.Tipo;//arrayIDs[i];
					}
					else
					{
						delitosNormales=""+retd.Tipo;//arrayIDs[i];
					}
				}
			} 
			//ahora mandamos los delitos al back-end
			var params="delitos="+nombreDelPrincipal+"-"+delitosNormales+"&numExp="+numeroExpediente;
                  $.ajax({
	       	  url: '<%= request.getContextPath()%>/guardarDelitosAgraviadosATP.do',
	    	  dataType: 'xml',
	    	  Type: 'POST',
	    	  data:params,
	    	  async: false,
	    	  success: function(xml){
	    		  if(parseInt($(xml).find('code').text())===0)
	    		  {
	    			  isDelitoSaved=true;
	    			//mostramos las leyendas de canalizacion debajo del grid
	    			  if(existeDelitoGraveEnGrid())
	    			  {
	    				  if(pantallaSolicitada!==AGENTE_MP){
	      					  $("#leyendaUnDelitoGrave").show();
	      					  $("#leyendaNingunDelitoGrave").hide();  
	    				  }else{
	    					  $("#leyendaUnDelitoGrave,#leyendaNingunDelitoGrave").hide();
	    				  }
	    			  }
	    			  else
	    			  {
	    				  $("#leyendaUnDelitoGrave").hide();
	    				  $("#leyendaNingunDelitoGrave").show();
	    			  }
	    			  //lanzamos la consulta de actividades que depende de los delitos almacenados
	    			  	$.ajax({
				       	  url: '<%= request.getContextPath()%>/consultarActividadesPorDelitosDelExpediente.do',
				    	  dataType: 'xml',
				    	  Type: 'POST',
				    	  data:params,
				    	  async: false,
				    	  success: function(xml){
				    		  if(parseInt($(xml).find('code').text())===0)
				    		  {
				    			  $("#actividadesXDelitosDelExpediente").empty();
				    			  var actividades="";
				    			  //barremos las activiades y generamos el html para ser pintado
				    			  //debajo del anuncio de canalizacion
				    			  $(xml).find('ValorDTO').each(function(){
				    				  actividades = actividades + $(this).find('valor').text()+"<br/>";
					    	      });
				    			  $("#actividadesXDelitosDelExpediente").html(actividades);
				    		  }  			    		
						  }
	    				});
	    				
	    			  if (typeof cargaGridDelitosAgraviados === 'function' ){
	    				  cargaGridDelitosAgraviados();
	    			  }
	    			  
	    			  //fin de la consulta de actividades que depende de los delitos almacenados
	    			  alertDinamico("Se guardaron exitosamente los delitos seleccionados");
	    		  }	 
	    		  else
	    		  {
	    			  isDelitoSaved=false;
	    			  alertDinamico("Ocurrió un error al tratar de guardar los delitos agraviados");
	    		  }   			    		
			  }
	    	});
		}
                
          function revisaEsDelitoGraveUno(idRadio)
		{
			//recuperamos el valor de la columna gravedad del delito
			var ret = jQuery("#gridCatDelitos").jqGrid('getRowData',idRadio);
			var retDos = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',idRadio);
			var isGrave="No";
			var delitos=<%=(String)request.getSession().getAttribute("delitosXML")%>;
			var delitosXML=$(''+delitos);
			//mostramos los botones correspondientes dependiento del tipo de delito
			if(ret.Gravedad!==null)
			{
				isGrave=ret.Gravedad;
			}
			else
			{
				isGrave=retDos.Gravedad;
			}
			if(isGrave==="No")
			{
				//revisamos que no exista un delito grave NO seleccionado
				if(existeDelitoGraveEnGrid())
				{
					//se le indica al usuario que seleccione un delito grave como principal
					customAlert("Si no hay reincidencia por parte del Probable Part&iacute;cipe,\n se debe canalizar al Centro de Justicia Restaurativa.");
				}
			}
		}
                
                function cargaIngresoHecho(nombre,id){
			$("#ingresarHechos").hide();
			$('#tableHecho').append('<tr><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="hecho_'+id+'" onclick="consultarHecho('+id+','+idNumeroExpedienteOp+');">'+nombre+'</a></td></tr>');
			cerrarVentanaHecho();
		} 
                
                function cerrarVentanaHecho(){
			var pantalla ="iframewindowHecho";
			pantalla += idWindowIngresarHechos;
			$.closeWindow(pantalla);
		}
                
	</script>	
</head>

<body>

<!-- div para el alert dinamico -->
<div id="dialog-Alert" style="display:none">
	<table align="center">
		<tr>
        	<td align="center">
            	<span id="divAlertTexto"></span>
            </td>
        </tr>
     </table>              
</div>

	<div class="ui-layout-north"></div>

	<!--COMIENZAN TABS SUPERIORES (PRINCIPALES)-->
	<div id="tabs">
		<ul>
			<li class="tabTabsGrals"><a href="#tabs-6" id="tapResumen">Resumen</a></li>
			<li class="tabTabsVisitaduria"><a href="#tabs-16">Visitadur&iacute;a</a></li>
			<li class="tabTabsHechos"><a href="#tabs-3" id="tapHechos">Hechos</a></li>
			<li class="tabTabsInv"><a href="#tabs-1" id="tabInvolucrados">Involucrado</a></li>
			<li class="tabTabsRelacionesDelitosPersonas"><a href="#tabs-17" id="tapDelitoYRelaciones" onclick="cargarGridsInvolucradosRelDelitoPersonaPG()">Delito y Relaciones Delito-Persona</a></li>
			<li class="tabTabsObjs"><a href="#tabs-4" id="tapObjetos">Objetos y evidencias</a></li>
			<li class="tabTabsCriterio"><a href="#tabs-13">Criterio de oportunidad</a></li>
			<li class="tabTabsDocs"><a href="#tabs-11" onclick="documentos()" id="tabDocumentos">Documentos</a></li>
			<li class="tabTabsAcciones"><a href="#tabs-7" id="tapActuaciones">Actuaciones</a></li>
			<li class="tabTabsPeri"><a href="#tabs-8" onclick="cargaGridsPericiales()" id="tapPericiales">Periciales</a></li> <!--onclick : gridPericiales() -->
			<li class="tabTabsPolMin"><a href="#tabs-9" onclick="cargaGridPoliciaMinisterial()" id="tapPoliciaMinister">Polic&iacute;a Investigadora</a></li>
			<li class="tabTabsCadCus"><a href="#tabs-10" onclick="gridCustodia()">Cadena de custodia</a></li>
			<li class="tabTabsAudiencias"><a href="#tabs-12" onclick="cargaGridJudiaciales()">Judiciales</a></li>
			<li class="tabTabsNotas"><a href="#tabs-5" id="tapNotas">Notas</a></li>
			<li class="tabTabsAlertas"><a href="#tabs-14" onclick="alertas()">Bit&aacute;cora de alarmas</a></li>
			<li class="tabTabsAmparos"><a href="#tabs-15" onclick="consultarAmparosPorExpediente()">Amparo</a></li>
			<li class="tabTabsConclusion" id="conclusionTab"><a href="#tabsHechos-17">Conclusi&#243;n</a></li>
		</ul>
            
         
		
		<!--COMIENZAN TABS INFERIORES DE INDIVIDUO-->
		<div id="tabs-1" class="tabTabsInv">		
			<div id="tabschild" class="tabs-bottom">
				<ul>
					<li><a href="#tabschild-1">Denunciante</a></li>
					<li><a href="#tabschild-2">V&iacute;ctima</a></li>
					<li><a href="#tabschild-3" id="tapProbableResposable"></a></li>
					<li><a href="#tabschild-4">Testigo</a></li>
					<li class="tabTabsTraductor"><a href="#tabschild-5">Traductor</a></li>
                                        <li class="tabTabsQuienDetuvo"><a href="#tabschild-6">Qui&eacute;n detuvo</a></li>
				</ul>
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
		
		<!--COMIENZAN TAB Visitaduria-->
		<div id="tabs-16" class="tabTabsVisitaduria">
			<div id="tabschild16" class="tabs-bottom">
				<ul>
					<li><a href="#tabschild16-1">Resumen</a></li>
					<li><a href="#tabschild16-2">Documentos</a></li>				
				</ul>
				<div id="tabschild16-1">
					<div style="width: 1042px; height: 490px;" class="back_hechos" ><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<table width="50%">
							<tr>
								<td width="10%"></td>
								<td>
									<b>Nombre del MP dueño del expediente auditado : </b>
								</td>
								<td id="spanNombrDuenoExpAud"></td>
							</tr>
							<tr>
								<td width="10%"></td>
								<td><b>N&uacute;mero del expediente auditado : </b></td>
								<td id="spanNumExpAud"></td>
							</tr>
							<tr>
								<td width="10%"></td>
								<td><b>&Aacute;rea del expediente auditado : </b></td>
								<td id="spanAreaExpAud"></td>
							</tr>
							<tr>
								<td width="10%"></td>
								<td><b>Fecha de creaci&oacute;n de la carpeta de auditor&iacute;a : </b></td>
								<td id="spanFechaExpAud"></td>
							</tr>
							<tr>
								<td width="10%"></td>
								<td><b>Estatus del expediente de visitadur&iacute;a : </b></td>
								<td id="spanEstatusExpAud"></td>
							</tr>
						</table>
					</div>
				</div>
				<div id="tabschild16-2">
					<table width="1042"  height="490" border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td width="250" height="450px;" align="center" valign="top">
			                        <table id="gridDocumentosDigitales"></table>
			                        <div id="pagerGridDocumentosDigitales"></div>
				                </td>
				                <td width="900" align="center" valign="top">
				               	  <iframe id='visorDocsFrame' width="900" height="450" src="">		               	  
				               	  </iframe>
				                </td>
				              </tr>
			        </table>
			     </div>
			</div>
					
		</div>
		<!--TERMINA TAB Visitaduria-->	
				
		<!--COMIENZAN TAB HECHOS-->
		<div id="tabs-3" class="tabTabsHechos">
			<div id="tabschild3" class="tabs-bottom">
				<ul>
					<li><a href="#tabschild3-1">Hechos</a></li>				
				</ul>
				<div id="tabschild3-1">
					<div style="width: 1042px; height: 490px;" class="back_hechos">
						<table    border="0" cellspacing="0" cellpadding="0" id="tableHecho" class="back_hechos" style="padding: .5cm; " >
							<tr valign="top">
								<td valign="top"><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="ingresarHechos" value="Ingreso Hecho" class="ui-button ui-corner-all ui-widget"/></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<!--TERMINA TAB HECHOS-->
		
		<!--COMIENZA TABS OBJETOS Y EVIDENCIAS-->
		<div id="tabs-4" class="tabTabsObjs">
		
			<div id="tabschild4" class="tabs-bottom">
				
				<ul>
					<li><a onclick="consultaGridVehiculosVisor()" href="#tabschild4-7">Veh&iacute;culo</a></li>
					<li><a onclick="consultaGridEquiposComputoVisor()" href="#tabschild4-1">Equipo de c&oacute;mputo</a></li>
					<li><a onclick="consultaGridEquipoTelefonicoVisor()" href="#tabschild4-2">Equipo telef&oacute;nico</a></li>
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
		
		
		<!--COMIENZA TAB NOTAS-->
		<div id="tabs-5" class="tabTabsNotas">
			<table width="25%" cellpadding="0" cellspacing="0" id="tablaNotasExpediente">
						<tr>
							<td>&nbsp;&nbsp;&nbsp;<input type="button" class="ui-button ui-corner-all ui-widget" value="Generar Nota"  id="botonGuardarNotas" onclick="notaExpediente(0);" /></td>
						</tr>
				</table>
		</div>
		<!--TERMINA TAB NOTAS-->
		
		<!--COMIENZAN TAB GENERALES     "-->
		<!--  <div id="tabs-6" class="tabTabsGrals"    id="">-->
		<div id="tabs-6" class="fondoClaroAp">
			<!--  
			<table id="numExpAlterTable" width="1042px"  height="20px" border="0">
				<tr>
					<td width="200" style="font-size:14px;" align="right">
						<strong>N&uacute;mero expediente alterno:</strong>
					</td>
					<td width="836">
						<input id="numExpAltSpan" maxlength="36" size="36" style=" border: 0; background:#DDD;" readonly="readonly">
					</td>
				</tr>
			</table>
			 -->
			
		
			<table width="1142px"  height="612px" border="0" cellspacing="0" cellpadding="0" class="back_generales">
			  <tr><td colspan="6">&nbsp;</td></tr>			
			  <tr style="border-bottom-style: solid; border: 1px;background-image:">
			    <td id="espacioEntrelbsEstatus" width="300" style="font-size:14px; background-color:" align="right">
			    	<%
			    		if(confInstitucionId != null && confInstitucionId.equals(Instituciones.PJ.getValorId())){
			    	%>
			    		<strong>Estatus <bean:message key="numeroDeCausa"/>:</strong>&nbsp;
			    	<%			
			    		}else{
			    	%>	
			    		<strong>Estatus del Expediente:</strong>&nbsp;
			    	<%
			    		}
			    	%>
			    	<label id="estatusExpe"></label>
			    </td>
			    <td width="19" style="font-size:14px; background-color:" >&nbsp;</td>
			    <td width="4">&nbsp;</td>
			    <td align="left" width="507" style="font-size:14px; background-color:">
					<strong>Estatus de Actuación:</strong>&nbsp;
					<label id="estatusActuacion"></label>
				</td>
			    <td id="idsNumerosDelExpediente" width="250" style="font-size:14px; background-color:">
			    	<strong>Folios de Expediente</strong>
					<select id="cbxNumerosExpediente" style="width:170px">
							
					</select>			    	
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
		            <td align="right" style="background-color:">Equipos de cómputo:</td>
		            <td id="EquiposDeComputo" >&nbsp;</td>
		            <td id="imgEquiposDeComputo" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
		          </tr>
		          <tr>
		            <td align="right" style="background-color:">Equipos Telefónicos:</td>
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
		            <td align="right" style="background-color:">Embarcación:</td>
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
			    <td align="right"><span id="spanGralUI">Unidad de Investigación: </span><span id="spanInfoGralUI"></span></td>
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
		
		
		<!--TERMINA TAB GENERALES-->
	<!--COMIENZAN TABS INFERIORES DE ACTUACIONES-->
		<div id="tabs-7" class="tabTabsAcciones">		
			<div id="tabschild7" class="tabs-bottom">
				<ul>
					<li class="tabTabsAccionesHijo"><a href="#tabschild7-1">Actuaciones</a></li>
					<li><a href="#tabschild7-2" id="tapRelacionarInfoDeExp">Relacionar Información del expediente</a></li>					
				</ul>				
				<div id="tabschild7-1">					
					
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="tablaAcuseNoPenal">
					<tr>
						<td id="tdCbxAgentesCoorJAR1">Facilitadores:</td>
						<td id="tdCbxAgentesCoorJAR"><select id="cbxAgentesCoorJAR" style="width:470px">
							<option value="-1">-Seleccione-</option>
							
						</select></td>
						<td id="tdCbxAgentesCoorUI1">Agentes:</td>
						<td id="tdCbxAgentesCoorUI"><select id="cbxAgentesCoorUI" style="width:470px">
							<option value="-1">-Seleccione-</option>
							
						</select></td>
						<td>
						<button value="Asignar a Agente MP" id="idAsignarAgenteMp" class="ui-button ui-corner-all ui-widget" onclick="asignarAgenteMP()">Asignar a Agente MP</button>
						<button value="Asignar a Facilitador" id="idAsignarFacilitador" class="ui-button ui-corner-all ui-widget" onclick="asignarFacilitador()">Asignar a Facilitador</button>
						<button value="Reasignar a Facilitador" id="idReasignarFacilitador" class="ui-button ui-corner-all ui-widget" onclick="asignarFacilitador()">Reasignar a Facilitador</button>						
						</td>
					</tr>
					<tr id="trActuaciones">
                                            <td>
                                                <tr>
                                                    <td>
                                                        <span id="actuacionesUie">Mostrar Actuaciones:
                                                            <span > 
                                                                <input type="radio" id="rdbConUaei" value="0" name="rdActuaciones"/>Todas
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            
                                                                <input type="radio" id="rdbSinUaei" value="1" name="rdActuaciones" checked="checked" />Unidad Especializada 
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            </span>				
                                                        </span>
                                                    </td>
                                                 </tr>
                                                 <tr></tr>
                                                 <tr>
                                                     <td id="tdCbxAccionesTab1" width="50%">
                                                         <div id="wrapA">
                                                            <div id="formA"></div>
                                                            Actuaciones <span id='act'></span>
                                                            <div class="clear"></div>
                                                         </div>
                                         
                                                     </td>
                                                     <td id="tdCbxOficiosTab1" width="100%">
                                                        <div id="wrapA">
                                                            <div> <p>&nbsp;&nbsp;&nbsp;</p></div>
                                                            Oficios <span id='ofic'></span>
                                                            <div class="clear"></div>
                                                        </div>
                                                     </td>
                                                 </tr>
                                                 <tr>
                                                     <td id="tdCbxAccionesTab" style="vertical-align:top">
                                                        <ul id="cbxAccionesTab" style="list-style:none; width: 600px; height: 400px; overflow: auto;" ></ul>
                                                    </td>
                                                    <td id="tdCbxOficiosTab" style="vertical-align:top">
                                                    <ul id="cbxOficiosTab" style="list-style:none; width: 600px; height: 400px; overflow: auto;" ></ul>
                                                </td>
                                                 </tr>
                                            </td>
                                           
						<td>
								<table>
									<tr>
										<td>
												<button value="Adjuntar documento" id="btnReasignarUIEExpediente" class="ui-button ui-corner-all ui-widget" onclick="abreVentanaReasignarUIEExpediente()" style="display: none">Reasignar Unidad de Investigaci&oacute;n</button>
										</td>
									</tr>
									<tr>
										<td>
												<!--<button value="Adjuntar documento" id="btnAdjuntarDocumento" class="ui-button ui-corner-all ui-widget" onclick="abreVentanaAdjuntarDocumentoAExpediente()" style="width: 100%;">Adjuntar documento</button>-->
										</td>
									</tr>
									<tr>
										<td>
												<!--  <button value="Elaborar teoria del caso" id="idTeoriaCaso" class="ui-button ui-corner-all ui-widget" onclick="abreTeoria()" style="width: 100%; ">Elaborar teor&iacute;a del caso</button>-->
										</td>
									</tr>
									<!-- <tr id="idbotoncarpeta" style="display: none;">
										<td>
											<button value="Enviar de Investigacion" class="ui-button ui-corner-all ui-widget" onclick="lanzaCarpetaInvestigacionDefensoria()">Enviar carpeta de investigación</button>
										</td>
									</tr> -->
								</table>
						</td>
						<td>
							<div id="idRadiosBUt" style="display: none;">
							<table>
								<tr>
								<td>
								Mediación
								</td>
								<td>
								<input type="radio" name="rbConci" id="raio1" checked="checked" />
								</td>
								</tr>
								<tr>
								<td>
								Conciliación
								</td>
								<td>
								<input type="radio" name="rbConci" id="raio2" />
								</td>
								</tr>
								
							</table>
							</div>
						</td>
					</tr>
				</table>
				</div>
				<div id="tabschild7-2">					
					<table width="80%" cellpadding="0" cellspacing="0">
						<tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="idRelacionarElementos">
								<jsp:include page="relacionarElementosView.jsp"></jsp:include>
							</a></td>
						</tr>
					</table>
				</div>
			</div>
					
		</div>
<!--TERMINAN TABS INFERIORES DE ACTUACIONES-->
		<div id="tabs-8" class="tabTabsPeri">
		<br>
		<br>
			<jsp:include page="solicitudesTabPericialesView.jsp"></jsp:include>
		</div>
		<div id="tabs-9" class="tabTabsPolMin">
			<div id="tabschild9" class="tabs-bottom">
				<ul>
					<li><a href="#tabschild9-1">Actuaciones</a></li>
					<!-- li><a href="#tabschild9-2">Documentos</a></li-->					
				</ul>				
				<div id="tabschild9-1">					
					<table width="800" border="0" cellspacing="0" cellpadding="0" id="">
						<tr>
							<td id="tdCbxAccionesTab9act">Actuaciones:</td>
							<td id="tdCbxAccionesTab9"><select id="cbxAccionesTab9" style="width:470px" size="10">
<!-- 								<option value="-1">-Seleccione-</option> -->
							</select></td>
							<td>
								<input type="button" id="idInvestiga" onclick="abreLineasInvestiga()" value="Iniciar Investigación" class="ui-button ui-corner-all ui-widget"/>
							</td>
						</tr>
					</table>
					<table border="0"  width="600px">
						<tr>
							<td height="15" colspan="4" align="center" >
								<table id="gridSolicitudesPoli" width="300px"></table>
								<div id="pagerGridSolicitudesPoli"></div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div id="tabs-10" class="tabTabsCadCus">
				 <input type="button" class="ui-button ui-corner-all ui-widget" id="btnCadCusNuevaCadCus" style="width: 250px;" value="Crear nueva cadena de custodia"/><br/><br/>
				 <input type="button" class="ui-button ui-corner-all ui-widget" id="btnCadCusConsultaCadCus" style="width: 250px;" value="Consultar cadena de custodia"/><br/><br/>    
		</div>
		<div id="tabs-11" class="tabTabsDocs">
		<br>
                <div><button value="Adjuntar documento" id="btnAdjuntarDocumento" class="ui-button ui-corner-all ui-widget" onclick="abreVentanaAdjuntarDocumentoAExpediente()" style="width: 10%;">Adjuntar documento</button></div>
                <div style="height: 10px;"></div>					
			<table id="gridDetalleFrmPrincipal"></table>
			<div id="pager1Documentos"></div>
			<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
				<!--<input type="hidden" name="documentoId" />-->
			</form>
				<form name="frmDoc2" action="<%= request.getContextPath() %>/GenerarDocumentoDirecto.do" method="post">
					<input type="hidden" name="formaId" />
					<input type="hidden" name="numeroUnicoExpediente" />
				</form>
                                        
		</div>
		<div id="tabs-12" class="tabTabsAudiencias">
		<br>
			<table id="tablePestanaAudiencias" align="center" width="100%" height="100%">
			<tr>
				<td>
					<table>
						<tr>
							<td>
								&nbsp;
							</td>
							<td>
								<input type="button" id="btnTranscripcionAudiencia" value="Transcripción de audiencia" class="ui-button ui-corner-all ui-widget" style="display: none;">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table id="gridDetalleFrmPrincipalAudiencias"></table>
					<div id="pagerAudiencias"></div>
				</td>
			<tr>
			</table>
		</div>
		<div id="tabs-13" class="tabTabsCriterio">
		<br>
			<table id="tablePestanaCriterio" align="center" width="100%" height="100%">
			<tr>
				<td>
					Si
				</td>
				<td align="left">
				No
				</td>
			</tr>
			<tr>
				<td>
					<input type="radio" name="noJuridico" id="juridicoSi" onclick="validaCriterios()">
				</td>
				<td>
					<input type="radio" name="noJuridico" id="juridicoNo" onclick="validaCriterios()"> No se afecta bien jur&iacute;dico.
				</td>
			</tr>
			<tr>
				<td>
					<input type="radio" name="noImputado" id="imputadoSi" onclick="validaCriterios()">
				</td>
				<td>
					<input type="radio" name="noImputado" id="imputadoNo" onclick="validaCriterios()"> El Imputado sufrió daños graves.
				</td>
			</tr>
			<tr>
				<td>
					<input type="radio" name="noPena" id="penaSi" onclick="validaCriterios()">
				</td>
				<td>
					<input type="radio" name="noPena" id="penaNo" onclick="validaCriterios()"> La pena por el hecho carece de importancia.
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" id="btnDocumentoCriterio" onclick="" value="Generar razones para criterio de oportunidad" class="ui-button ui-corner-all ui-widget">
				</td>
				<td>
					<input type="button"  id="btnDocumentoDictamen" onclick="abilitaDoc()" value="Generar Dictamen motivado de criterio de oportunidad" class="ui-button ui-corner-all ui-widget">
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" id="btnInformeCriterio" onclick="dialigoDictamenOprtunidad()" value="Informar criterio de oportunidad" class="ui-button ui-corner-all ui-widget">
				</td>
				<td>
					<input type="button"  id="btnTurnarInpugna" onclick="dialigoImpugnacion()" value="Turnar inpugnación de criterio de oportunidad" class="ui-button ui-corner-all ui-widget">
				</td>
			</tr>
			</table>
		</div>
		<div id="tabs-14" class="tabTabsAlertas">
		<br>
			<table id="gridDetalleAlertas"></table>
			<div id="pagerGridDetalleAlertas"></div>
		</div>
	    <!--COMIENZA TAB AMPAROS-->
        <div id="tabs-15" class="tabTabsAmparos">
    		<jsp:include page="/consultarAmparo.jsp"></jsp:include>
		</div>
		<!--TERMINA TAB AMPAROS-->
		
		<!--COMIENZAN TABS INFERIORES DE DELITO Y RELACIONES DELITO PERSONA-->
		<div id="tabs-17" class="tabTabsRelacionesDelitosPersonas">
			<div id="tabschild17" class="tabs-bottom">
				<ul>
					<li><a href="#tabschild17-1" onclick="">Relaci&oacute;n Delito Persona</a></li>
					<li><a href="#tabschild17-2" onclick="gridRelacionesDelitoPersonaModosParticipacionPG();">Grado De Participaci&oacute;n</a></li>					
				</ul>
				<div id="tabschild17-1">
					<div style="overflow-x:scroll; width:100%; height:100%" style="overflow-y:hidden;">
						<jsp:include page="establecerRelacionesDelitoPersonaPG.jsp"></jsp:include>
					</div>
				</div>
				<div id="tabschild17-2">
					<div>
						<jsp:include page="establecerModosYGradosDeParticipacion.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
	<!--TERMINAN TABS INFERIORES DE DELITO Y RELACIONES DELITO PERSONA-->	
	<!--COMIENZAN TABS concluciones-->	
			<div id="tabsHechos-17">
					    	<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" id="tableConclusion" class="back_hechos">
					    		<tr>
					    			<td valign="top">
					    				<table width="500px" border="0" style="margin-top: 60px">
					    					<tr>
									    		<td align="right" width="40%">Fecha:</td>
									    		<td>
									    			<input type="text" id="fechaConclusion" name="fechaConclusion" 
									    					maxlength="10" tabindex="1" readonly="readonly" 
									    					style="width: 178px;" onKeypress="return bloqueaEnter(event);"/>
									    		</td>
									    	</tr>
									    	<tr>
									    		<td align="right">Tipo Conclusi&#243;n:</td>
									    		<td>
									    			<select id="cbxTipoConclusion" style="width:200px;" tabindex="2" onchange="cargaTipoSubConclusion()">
										                <option value="-1">-Seleccione-</option>
										            </select>
									    		</td>
									    	</tr>
									    	<tr id="trTipoSubConclusion">
									    		<td>&nbsp;</td>
									    		<td>
									    			<select id="cbxTipoSubConclusion" style="width:200px;" tabindex="3">
										                <option value="-1">-Seleccione-</option>
										            </select>
									    		</td>
									    	</tr>
					    				</table>
					    			</td>
					    			<td>
					    				<input type="button" id="btnConclusionExpe" onclick="guardarConclusion()" value="Guardar" class="ui-button ui-corner-all ui-widget">
					    			</td>
					    		</tr>
					    		
					    		
						    </table>
					    </div>
        	<!--TERMINAN TABS Conclucion-->	
	</div>
	<!--TERMINAN TABS SUPERIORES (PRINCIPALES)-->
	<!-- DIV para el dialogo de asociacion de un delito por persona -->
<!--		<div id="dialogDos-confirm" title="Establecer Delito Por Persona" >-->
<!--			<table width="650" border="0" cellspacing="0" id="tblDelitoPersonaVariantes">-->
<!--				<tr>-->
<!--					<td align="right">-->
<!--						Delitos del Expediente :-->
<!--					</td>-->
<!--					<td>-->
<!--						<select id="cbxDelitosExpRDPPV">-->
<!--							<option selected="selected" value="-1">-Seleccione-</option>-->
<!--						</select>-->
<!--					</td>-->
<!--				</tr>-->
<!--				<tr id="trFormasParticipacionRDPPV">-->
<!--					<td align="right">-->
<!--						Grado de participaci&oacute;n : -->
<!--					</td>-->
<!--					<td>-->
<!--						<select id="cbxFormasParticipacionRDPPV">-->
<!--							<option selected="selected" value="-1">-Seleccione-</option>-->
<!--						</select>-->
<!--					</td>-->
<!--				</tr>-->
<!--				<tr>-->
<!--					<td align="right">-->
<!--						V&iacute;ctimas : -->
<!--					</td>-->
<!--					<td>-->
<!--						<select id="cbxVictimasExpRDPPV">-->
<!--							<option selected="selected" value="-1">-Seleccione-</option>-->
<!--						</select>-->
<!--					</td>-->
<!--				</tr>-->
<!--			  <tr>-->
<!--			  <td width="100"><div align="right"><bean:message key="clasificacionRelacionDelitoPersona"/> :</div></td>-->
<!--			    <td><select id="cbxClasificacionRDPPV">-->
<!--			      <option selected="selected" value="-1">-Seleccione-</option>-->
<!--			    </select></td>-->
<!--			  </tr>-->
<!--			  <tr>-->
<!--			    <td><div align="right">Lugar :</div></td>-->
<!--			    <td><select id="cbxLugarRDPPV">-->
<!--			      <option selected="selected" value="-1">-Seleccione-</option>-->
<!--			    </select></td>-->
<!--			  </tr>-->
<!--			  <tr id="trModalidadRDPPV">-->
<!--			    <td><div align="right">Modalidad :</div></td>-->
<!--			    <td><select id="cbxModalidadRDPPV">-->
<!--			      <option selected="selected" value="-1">-Seleccione-</option>-->
<!--			    </select></td>-->
<!--			  </tr>-->
<!--			  <tr id="trModusRDPPV">-->
<!--			    <td><div align="right"><bean:message key="modusRelacionDelitoPersona"/> :   </div></td>-->
<!--			    <td><select id="cbxModusRDPPV">-->
<!--			      <option selected="selected" value="-1">-Seleccione-</option>-->
<!--			    </select></td>-->
<!--			  </tr>-->
<!--			  <tr id="trCausaRDPPV">-->
<!--			    <td><div align="right"><bean:message key="causaRelacionDelitoPersona"/> :</div></td>-->
<!--			    <td><select id="cbxCausaRDPPV">-->
<!--			      <option selected="selected" value="-1">-Seleccione-</option>-->
<!--			    </select></td>-->
<!--			  </tr>-->
<!--		</table>-->
<!--	</div>-->
	<!-- FIN DIV para el dialogo de asociacion de un delito por persona -->
	
	<!-- DIV PARA DIALOGO, DE MODUS Y GRADOS DE PARTICIPACION -->
		<div id="dialogModosYGrados-confirm" title="Establecer grados de participaci&oacute;n y atributos" >
			<table width="700" border="0" cellspacing="0" id="tblDelitoPersonaVariantes">
				<tr id="trFormasParticipacionRelDelPer">
					<td align="right">
						Grado de participaci&oacute;n : 
					</td>
					<td>
						<select id="cbxFormasParticipacionRelDelPer" style="width:550px">
							<option selected="selected" value="-1">-Seleccione-</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="100">
						<div align="right"><bean:message key="clasificacionRelacionDelitoPersona"/>:</div>
					</td>
			    	<td>
			    		<select id="cbxClasificacionRelDelPer" style="width:550px">
			      			<option selected="selected" value="-1">-Seleccione-</option>
			    		</select>
			    	</td>
				</tr>
				<tr>
					<td>
				  		<div align="right">Lugar :</div>
				  	</td>
				  	<td>
				  		<select id="cbxLugarRelDelPer" style="width:550px">
				    		<option selected="selected" value="-1">-Seleccione-</option>
				  		</select>
				  	</td>
				</tr>
				<%-- <tr id="trModalidadRelDelPer">
			    	<td>
			    		<div align="right">Modalidad :</div>
			    	</td>
			    	<td>
			    		<select id="cbxModalidadRelDelPer" style="width:550px">
			      			<option selected="selected" value="-1">-Seleccione-</option>
			    		</select>
			    	</td>
				</tr>
				<tr id="trModusRelDelPer">
					<td>
						<div align="right"><bean:message key="modusRelacionDelitoPersona"/> :   </div>
					</td>
			    	<td>
			    		<select id="cbxModusRelDelPer" style="width:550px">
			      			<option selected="selected" value="-1">-Seleccione-</option>
			    		</select>
			    	</td>
				</tr>
				<tr id="trCausaRelDelPer">
			    	<td>
			    		<div align="right"><bean:message key="causaRelacionDelitoPersona"/> :</div>
			    	</td>
			    	<td>
			    		<select id="cbxCausaRelDelPer" style="width:550px">
			      			<option selected="selected" value="-1">-Seleccione-</option>
			    		</select>
			    	</td>
				</tr> --%>
				
				<tr id="trEstadisticaRelDelPer">
			    	<td>
			    		<div align="right">Delito estadistica :</div>
			    	</td>
			    	<td>
			    		<select id="cbxEstadisticaRelDelPer" style="width:550px">
			      			<option selected="selected" value="-1">-Seleccione-</option>
			    		</select>
			    	</td>
				</tr>
				
				<tr id="trTipoRelDelPer">
			    	<td>
			    		<div align="right">Tipo de delito :</div>
			    	</td>
			    	<td>
			    		<select id="cbxTipoRelDelPer" style="width:550px">
			      			<option selected="selected" value="-1">-Seleccione-</option>
			    		</select>
			    	</td>
				</tr>
				<tr id="trCalificacionRelDelPer">
			    	<td>
			    		<div align="right">Calificaci&oacute;n del delito :</div>
			    	</td>
			    	<td>
			    		<select id="cbxCalificacionDelPer" style="width:550px">
			      			<option selected="selected" value="-1">-Seleccione-</option>
			    		</select>
			    	</td>
				</tr>
				
				<tr id="trConcursoRelDelPer">
			    	<td>
			    		<div align="right">Concurso :</div>
			    	</td>
			    	<td>
			    		<select id="cbxConcursoDelPer" style="width:550px">
			      			<option selected="selected" value="-1">-Seleccione-</option>
			    		</select>
			    	</td>
				</tr>
				<tr id="trOrdenRelDelPer">
			    	<td>
			    		<div align="right">Orden de resultado :</div>
			    	</td>
			    	<td>
			    		<select id="cbxOrdenDelPer" style="width:550px">
			      			<option selected="selected" value="-1">-Seleccione-</option>
			    		</select>
			    	</td>
				</tr>
				 
		</table>
	</div>
	<!-- FIN DIV para el dialogo de asociacion de un delito por persona -->
	
	
	<!-- DIV para el dialogo de asociacion de un delito por delito -->
		<div id="dialogTres-confirm" title="Establecer Delito Por Delito" >
		<table width="650" border="0" cellspacing="0">
			<tr>
				<td align="right">
					<bean:message key="plProbalbeResponsableTitulo"/> : 
				</td>
				<td>
					<select id="cbxProbableResponsableExpRDPD">
						<option selected="selected" value="-1">-Seleccione-</option>
					</select>	
				</td>
			</tr>
			<tr id="trFormasParcticipacionRDPD">
				<td align="right">
					Formas de participaci&oacute;n :
				</td>
				<td>
					<select id="cbxFormasParticipacionRDPD">
						<option selected="selected" value="-1">-Seleccione-</option>
					</select>
				</td>
			</tr>
			<tr>
			<td align="right">
				V&iacute;ctimas :
			</td>
			<td>
				<select id="cbxVictimasExpRDPD">
					<option selected="selected" value="-1">-Seleccione-</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="100"><div align="right"><bean:message key="clasificacionRelacionDelitoPersona"/> :</div></td>
		    <td>
		    	<select id="cbxClasificacionRDPD">
		    		<option selected="selected" value="-1">-Seleccione-</option>
		    	</select>
		    </td>
	    </tr>
		<tr>
			<td><div align="right">Lugar :</div></td>
		    <td><select id="cbxLugarRDPD">
		      <option selected="selected" value="-1">-Seleccione-</option>
		    </select></td>
	    </tr>
		  <tr id="trModalidadRDPD">
		    <td><div align="right">Modalidad :</div></td>
		    <td><select id="cbxModalidadRDPD">
		      <option selected="selected" value="-1">-Seleccione-</option>
		    </select></td>
		  </tr>
		  <tr id="trModusRDPD">
		    <td><div align="right"><bean:message key="modusRelacionDelitoPersona"/> :</div></td>
		    <td><select id="cbxModusRDPD">
		      <option selected="selected" value="-1">-Seleccione-</option>
		    </select></td>
		  </tr>
		  <tr id="trCausaRDPD">
		    <td><div align="right"><bean:message key="causaRelacionDelitoPersona"/>:</div></td>
		    <td><select id="cbxCausaRDPD">
		      <option selected="selected" value="-1">-Seleccione-</option>
		    </select></td>
		  </tr>
		</table>
	</div>
	<!-- FIN DIV para el dialogo de asociacion de un delito por delito -->
	<!-- DIV para el dialogo de criterios de oportunidad -->
	<div id="dialogCriterios-confirm" title="Criterio de Opotunidad" >
		<p align="left">
			¿Desea ejercer el criterio de oportunidad? 
		</p>
	</div>
	<!-- DIV para el dialogo de envio de dictamen -->
	<div id="dialogDictamen-confirm" title="Criterio de Opotunidad Env&iacute;o de Dictamen " >
		<p align="left">
			¿Desea enviar el dictamen de criterio de oportunidad? 
		</p>
	</div>
	<!-- DIV para el dialogo de inpugnacion de criterio -->
	<div id="dialogImpugnacion-confirm" title="Criterio de Opotunidad Impugnación " >
		<p align="left">
			¿Desea impugnar el criterio de oportunidad? 
		</p>
	</div>
	<!-- DIV para el dialogo de inpugnacion de criterio para archivo -->
	<div id="dialogImpugnacionARchivo-confirm" title="Criterio de Opotunidad Impugnación " >
		<p align="left">
			Seleccionar Archivo a adjuntar:
			<input type="file">
		</p>
	</div>
</body>
<script type="text/javascript">
//$( "#dialogDos-confirm" ).dialog();
//$( "#dialogDos-confirm" ).dialog( "destroy" );
$( "#dialogModosYGrados-confirm" ).dialog();
$( "#dialogModosYGrados-confirm" ).dialog( "destroy" );
$( "#dialogTres-confirm" ).dialog();
$( "#dialogTres-confirm" ).dialog( "destroy" );
$( "#dialogCriterios-confirm" ).dialog();
$( "#dialogCriterios-confirm" ).dialog( "destroy" );
$( "#dialogDictamen-confirm" ).dialog();
$( "#dialogDictamen-confirm" ).dialog( "destroy" );
$( "#dialogImpugnacion-confirm" ).dialog();
$( "#dialogImpugnacion-confirm" ).dialog( "destroy" );
$( "#dialogImpugnacionARchivo-confirm" ).dialog();
$( "#dialogImpugnacionARchivo-confirm" ).dialog( "destroy" );
$('#btnInformeCriterio').hide();
$('#btnTurnarInpugna').hide();
$('#btnDocumentoCriterio').hide();
$('#btnDocumentoDictamen').hide();
function validaCriterios(){
	if($('#juridicoSi').is(':checked') &&$('#imputadoSi').is(':checked') &&$('#penaSi').is(':checked') ){
		dialigoCriterios();
	}else{
		criterioOportinidadOp=0;
	}
}
function abilitaDoc(){
	$('#btnInformeCriterio').attr("disabled","");
	$('#btnTurnarInpugna').attr("disabled","");
	$('#btnDocumentoDictamen').attr("disabled","disabled");
}
function ocultaElementosDelVisor(){
	//Resumen
	//Hechos
	$("#ingresarHechos").hide();
	//Involucrados
	$('#crearDenunciante').hide();
	$('#nuevaVictima').hide();
	$("#btnNuevoProbResponsable").hide();
	$("#nuevoTestigo").hide();
	$("#nuevoTraductor").hide();
	$("#quienDetuvo").hide();	
	//Delitos
	$('#btnGuardarDelitosAg').hide();
	$("#pasar").hide();
	$("#pasarD").hide();	
	//Objetos
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
	$("#nuevoOtros").hide();			
	$("#nuevoPericial").hide();	
	//Documentos
	//Actuaciones
	if(idRolActivo === '<%=Roles.DIRECTOR_GENERAL.getValorId()%>'){
		 $("#btnAdjuntarDocumento").hide();
	}
	if(idRolActivo === '<%=Roles.POLICIAMINISTER.getValorId()%>'){
	    $("#cbxAccionesTab").attr("disabled","");
            $("#cbxOficiosTab").attr("disabled","");
	}
	//Periciales
	//Policia ministerial
	$('#idInvestiga').hide();
	//Tab cadena de custodia
	$("#btnCadCusNuevaCadCus").hide();
	//Notas (Todos los roles pueden consultar/escribir notas)
}
function dialigoCriterios(){
	//generamos el Dialogo
	$( "#dialogCriterios-confirm" ).dialog({
		resizable: false,
		height:150,
		width:300,
		modal: true,
		buttons: {
			"Si": function() {
				$( this ).dialog( "close" );
				$( "#dialogCriterios:ui-dialog" ).dialog( "destroy" );
				//RelacionarDelitoRDPPV();
				criterioOportinidadOp=1;
				$('#btnInformeCriterio').show();
				$('#btnTurnarInpugna').show();
				$('#btnDocumentoCriterio').show();
				$('#btnDocumentoDictamen').show();
				$('#btnInformeCriterio').attr("disabled","disabled");
				$('#btnTurnarInpugna').attr("disabled","disabled");
			},
			"No": function() {
				$( this ).dialog( "close" );
				$( "#dialogCriterios:ui-dialog" ).dialog( "destroy" );
				criterioOportinidadOp=0;
				 $('#juridicoNo').attr('checked','checked');
				 $('#imputadoNo').attr('checked','checked');
				 $('#penaNo').attr('checked','checked');
			}
		}
	});
	$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
}
function dialigoDictamenOprtunidad(){
	//generamos el Dialogo
	$( "#dialogDictamen-confirm" ).dialog({
		resizable: false,
		height:150,
		width:300,
		modal: true,
		buttons: {
			"Si": function() {
				$( this ).dialog( "close" );
				$( "#dialogDictamen:ui-dialog" ).dialog( "destroy" );
				$('#juridicoNo').attr("disabled","disabled");
				$('#juridicoSi').attr("disabled","disabled");
				$('#imputadoSi').attr("disabled","disabled");
				$('#imputadoNo').attr("disabled","disabled");
				$('#penaNo').attr("disabled","disabled");
				$('#penaSi').attr("disabled","disabled");
				$('#btnInformeCriterio').attr("disabled","disabled");
			},
			"No": function() {
				$( this ).dialog( "close" );
				$( "#dialogDictamen:ui-dialog" ).dialog( "destroy" );
			}
		}
	});
	$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
}
function dialigoImpugnacion(){
	//generamos el Dialogo
	$( "#dialogImpugnacion-confirm" ).dialog({
		resizable: false,
		height:150,
		width:300,
		modal: true,
		buttons: {
			"Si": function() {
				$( this ).dialog( "close" );
				$( "#dialogImpugnacion:ui-dialog" ).dialog( "destroy" );
				dialigoImpugnacionArchivo();
			},
			"No": function() {
				$( this ).dialog( "close" );
				$( "#dialogImpugnacion:ui-dialog" ).dialog( "destroy" );
			}
		}
	});
	$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
}
function dialigoImpugnacionArchivo(){
	//generamos el Dialogo
	$( "#dialogImpugnacionARchivo-confirm" ).dialog({
		resizable: false,
		height:150,
		width:300,
		modal: true,
		buttons: {
			"Aceptar": function() {
				$( this ).dialog( "close" );
				$( "#dialogImpugnacionARchivo:ui-dialog" ).dialog( "destroy" );
				$.newWindow({id:"iframewindowSolicitarAudiencia", statusBar: true, posx:20,posy:20,width:750,height:530,title:"Solicitar Audiencia", type:"iframe"});
                $.updateWindowContent("iframewindowSolicitarAudiencia",'<iframe src="<%= request.getContextPath() %>/solicitarAudiencia.jsp"    width="750" height="530" />');
			},
			"Cancelar": function() {
				$( this ).dialog( "close" );
				$( "#dialogImpugnacionARchivo:ui-dialog" ).dialog( "destroy" );
				
			}
		}
	});
	$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
}
//ocultamos las leyendas en la carga de la pagina
$("#leyendaUnDelitoGrave,#leyendaNingunDelitoGrave").hide();
</script>
</html>