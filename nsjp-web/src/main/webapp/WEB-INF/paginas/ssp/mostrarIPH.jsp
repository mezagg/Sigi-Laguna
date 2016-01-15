<%@page import="mx.gob.segob.nsjp.comun.enums.objeto.Objetos"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.calidad.Calidades" %>
	 <%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mostrar IPH</title>

    <!--Hoja de estilos de Layout-->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />

    <!--Hojas de estilos de los combos multiselect-->
    <!-- <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/style.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/prettify.css" /> -->

    <!--Hoja de estilos ultrasist-->
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />

    <!--Hoja de estilos windows engine (frames)-->
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>

    <!--Hojas de estilos para los componentes UI de Jquery-->
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />

    <!--Hoja de estilos para el grid-->
    <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />

    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>  


    <!--scripts de java script-->
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-ui-1.8.11.custom.min.js"></script>

    <!--script de windows engine (frames)-->
    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>

    <!--script de jquery UI-->
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/prettify.js"></script>

    <!--scripts del gird-->
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/jqgrid/jquery.jqGrid.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>


    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.timeentry.js"></script>

   <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
   <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
		
   <script type="text/javascript">
            var contextoPagina = "${pageContext.request.contextPath}";
    </script>
        

	<!--ESTILOS PARA LAS TABS-->
	<style>
	#tabs { height: 670px; } 
	.tabs-bottom { position: relative; } 
	.tabs-bottom .ui-tabs-panel { height: 500px; overflow: auto; } 
	.tabs-bottom .ui-tabs-nav { position: absolute !important; left: 0; bottom: 0; right:0; padding: 0 0.2em 0.2em 0; } 
	.tabs-bottom .ui-tabs-nav li { margin-top: -2px !important; margin-bottom: 1px !important; border-top: none; border-bottom-width: 1px; }
	.ui-tabs-selected { margin-top: -3px !important; }
	.tabEstilo  { height: 350px; overflow: auto; }
	</style>
    

<script type="text/javascript">

	//variable en donde se almacena el id del funcionario para poder registrar el usuario
    //var idFuncionario;
	//variable global para manipular la pesta&ntilde;a de dar de alta un usuario segun de donde se mande a llamar
    //var administrador= '<%=request.getParameter("administrador")!=null?request.getParameter("administrador"):""%>';
    
	var numeroExpediente;
	var numeroExpedienteId;
	var expedienteId;
	var operativoId;
	var primeraVezGridDocumentosDigitalesPropios = true;
	var iphReplica="false";

	var idExpedienteop;
    
    var idWindowIngresarVictima           = 1;	
	//var idWindowIngresarTraductor         = 1;	
	var idWindowIngresarTestigo           = 1;
	var idWindowIngresarProbResponsable   = 1;
	var idWindowIngresarDenunciante       = 1;
	var idWindowIngresarHechos            = 1;
	var idWindowIngresarVehiculo          = 1;
	var idWindowIngresarAeronave          = 1;
	var idWindowIngresarEmbarcacion       = 1;	
	var idWindowIngresarArma              = 1;
	var idWindowIngresarExplosivo         = 1;
	var idWindowIngresarSustancia         = 1;	
	var idWindowIngresarNumerario         = 1;
	var idWindowIngresarOtros = 1;
	var isDetenidoExist                   = false;	

	var idWindowAsntarRegCadCus = 1;

	//Extensiones permitidas para documentos adjuntos
	var extensionesPermitidasAudio = '';
	var extensionesPermitidasImagen = '';

	var folioIPH='<%= request.getParameter("folioIPH")%>';
	var rowid = <%=request.getParameter("rowid")%>;
	
	var contextoPagina = "${pageContext.request.contextPath}";
	
	$(document).ready(function() {
		
		$('#liDom').hide();
		$('#liDom').addClass("tabEstilo");
		
		$( ".tabs-bottom .ui-tabs-nav, .tabs-bottom .ui-tabs-nav > *" )
			.removeClass( "ui-corner-all ui-corner-top" )
			.addClass( "ui-corner-bottom" );
		
		//Se crean las tabs principales
		$("#tabsconsultaprincipal" ).tabs();
		$("#tabs" ).tabs();

		//SE CREAN LAS TABS INFERIORES
		//personas involucradas
		$( "#tabschild2" ).tabs();
		//medios de transporte
		//$( "#tabschild3" ).tabs();
		//estupefacientes
		//$( "#tabschild4" ).tabs();
		//armas y explosivos
		//$( "#tabschild5" ).tabs();
		//aseguramientos
		$( "#tabschild15" ).tabs();
		
		//Se agrega el evento click para crear nuevos involucrados
		$("#nuevaVictima").click(creaNuevaVictima);
		$("#nuevoProbResponsable").click(creaNuevoProbResponsable);
		$("#crearDenunciante").click(crearDenunciante);
		$("#nuevoTestigo").click(creaNuevoTestigo);
		$("#ingresarHechos").click(ingresarHechos);
		$("#nuevoVehiculo").click(creaNuevoVehiculo);
		$("#nuevaAeronave").click(creaNuevaAeronave);
		$("#nuevaEmbarcacion").click(creaNuevaEmbarcacion);
		$("#nuevaArma").click(creaNuevaArma);
		$("#nuevoExplosivo").click(creaNuevoExplosivo);
		$("#nuevaSustancia").click(creaNuevaSustancia);
		$("#nuevoNumerario").click(creaNuevoNumerario);
		$("#nuevoOtros").click(creaNuevoOtros);
		
		cargaTurnos();
		cargaTipoParticipacion();
		$("#chkOperativo").click(habilitaDivOperativo);	
		ocultaDivOperativo();			
		consultarDistritos();
		/*$( "#dialog-detenido-informe" ).dialog({
			autoOpen: true,
			resizable: false,
			//height:290,
			//width:300,
			modal: true,
			buttons: {
				"Si": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
					//dlgDetenidoInforme(true);
				},
				"No": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
					//dlgDetenidoInforme(false);
				}
			}
		});	*/								
		
		//Seteo listener cadena de custodia
		$("#btnCadCusNuevaCadCus").click(asentarRegCadenaCustodia);
		$("#btnCadCusConsultaCadCus").click(consultarRegCadenaCustodia);

		consultarInformePolicialHomologadoPorFolio(folioIPH);

		resumen();
		$('#tabsconsultaprincipal').tabs({ selected: 2 });		
		cargaGridPertenencias();
		cargaGridProbableResponsable();
	});

	/**
	** Consulta el IPH de acuerdo a un folio determinado.
	**/
	function consultarInformePolicialHomologadoPorFolio(folioIPH){
		$.ajax({
	  		type: 'POST',
	  	  	url: '<%= request.getContextPath()%>/consultarInformePolicialHomologadoPorFolio.do?folioIPH='+folioIPH+'',			
	  	  	dataType: 'xml',
	  	  	async:false,
	  	  	success: function(xml){
	  	  		iphReplica=$(xml).find('InformePolicialHomologadoDTO').find('existeInforme').text();
	  	  		

	  	  		mostrarDatosGenerales(xml);
	  		  	mostrarHechos(xml, 'hecho');
	  		  	mostrarInvolucrados(xml);
	  		  	mostrarMediosDeTransporte(xml);
	  		  	mostrarSustancias(xml);
	  		  	mostrarArmas(xml);
	  		  	mostrarNumerario(xml);
	  		  	mostrarOtros();
	  			cargaGridDocumentosDigitalesPropios();
	  				  		  	
	  	  	}
		});
	}

	/**
	** Obtiene a partir de un xml los valores a mostrar en las pesta&ntilde;as de Datos Generales y Observaciones Generales
	**/
	function mostrarDatosGenerales(xml){
		$("#datosGeneralesCmpNumeroTransporteOf").val($(xml).find('numEcoTransporte').text());
		$("#datosGeneralesCmpAsunto").val($(xml).find('asunto').text());

		if($(xml).find('faltaIph').find('catFaltaAdministrativa').find('nombreFalta') == null ||
			$(xml).find('faltaIph').find('catFaltaAdministrativa').find('nombreFalta').text() == ""){
			$("#motivoCmpTipoEvento").val('1'); //combo
			buscaSubTipoEvento();
			$("#motivoCmpSubtipoEvento").val($(xml).find('InformePolicialHomologadoDTO').find('InvolucradoDTO').first().find('delitosCometidos').first().find('catDelitoDTO').first().find('catDelitoId').first().text()); //combo
		}else{
			$("#motivoCmpTipoEvento").val('2'); //combo
			buscaSubTipoEvento();
			$("#motivoCmpSubtipoEvento").val($(xml).find('faltaIph').first().find('catFaltaAdministrativa').find('catFaltaAdministrativaId').text()); //combo
		}
		
		$("#datosGeneralesCmpNumeroEmpleado").val($(xml).find('funcionarioDestinatario').find('numeroEmpleado').text());
		if($("#datosGeneralesCmpNumeroEmpleado").val() != null && $("#datosGeneralesCmpNumeroEmpleado").val() != ""){
  			buscarFuncionario();	  		  	
		}
		
		$("#datosGeneralesCmpOficial").val($(xml).find('funcionarioDestinatario').find('nombreFuncionario').text() + " " + 
										   $(xml).find('funcionarioDestinatario').find('apellidoPaternoFuncionario').text() + " " +
										   $(xml).find('funcionarioDestinatario').find('apellidoMaternoFuncionario').text());
		$("#datosGeneralesCmpSector").val($(xml).find('funcionarioDestinatario').find('departamento').find('area').find('nombre').first().text());
		$("#datosGeneralesCmpDirigidoA").val();
		
		$("#datosGeneralesCmpTurno").val($(xml).find('turnoLaboralIphs').find('turnoLaboral').find('turnoLaboralId').text()); //combo
		$("#datosGeneralesCmpTipoParticipacion").val($(xml).find('tipoParticipacion').find('idCampo').text());

		$("#editor1").val($(xml).find('objetivosGenerales').text());

		if ($(xml).find('operativo').find('operativoId') != null &&	$(xml).find('operativo').find('operativoId').text() != ""){
			$("#divOperativo").show();
			$('#chkOperativo').attr("checked","checked");
			$("#datosGeneralesCmpNombreOperativo").val($(xml).find('operativo').find('nombre').last().text());
			$("#datosGeneralesCmpComandanteAgrupamiento").val($(xml).find('operativo').find('nombreComte').text());
			$("#datosGeneralesCmpComandanteOperativo").val($(xml).find('operativo').find('nombreComteAgrupto').text());				
		}else{
			$("#divOperativo").hide();
		}

		numeroExpedienteId = $(xml).find('unicoIdNumeroExpediente').text();
		idExpedienteop = numeroExpedienteId;
		numeroExpediente = $(xml).find('expediente').find('numeroExpediente').first().text();
		expedienteId=$(xml).find('expediente').find('expedienteId').first().text();
		if($(xml).find('operativo').find('operativoId').first().text() != null && $(xml).find('operativo').find('operativoId').first().text() != ""){
			
			if($(xml).find('operativo').find('operativoId').first().text()!="null"){
				operativoId = $(xml).find('operativo').find('operativoId').first().text();
			}else{
				operativoId = null;
			}
		}else{
			operativoId = null;
		}
		
		var fCatDistritoId = $(xml).find('fCatDistritoId').first().text();
		var fCatDiscriminanteId=$(xml).find('fCatDiscriminanteId').first().text();
			
		setDistritoYAgencia(fCatDistritoId, fCatDiscriminanteId);
	}
	
	function  setDistritoYAgencia(fCatDistritoId, fCatDiscriminanteId) {
		$("#cbxDistrito").val(fCatDistritoId);
		consultarAgenciasXDistrito(fCatDistritoId);
		$("#cbxAgencia").val(fCatDiscriminanteId);
	}
	
	/**
	** Muestra el listado de Hechos asignados al IPH.
	**/
	function mostrarHechos(xml, nombre){
		$(xml).find('InformePolicialHomologadoDTO').find('hechoDTO').each(function(){
			$('#tablaHecho').append('<tr id="'+$(this).find('hechoId').text()+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="consultarHecho('+$(this).find('hechoId').text()+', '+numeroExpedienteId+');">'+nombre+ '_' + $(this).find('hechoId').text() + '</a></td></tr>');			
		});		
	}

	/**
	** Muestra los involucrados en las pesta&ntilde;as correspondientes seg&uacute;n su tipo.
	**/
	function mostrarInvolucrados(xml){
		$(xml).find('InformePolicialHomologadoDTO').find('involucradosDTO').find('InvolucradoDTO').each(function(){

	      	if($(this).find('valorIdCalidad').find('idCampo').first().text() == '<%= Calidades.DENUNCIANTE.getValorId() %>') {
		      	var idInvolucrado = $(this).find('elementoId').first().text(); 
	      		var nombreDenunciante = $(this).find('nombre').first().text() + ' ' + $(this).find('apellidoPaterno').first().text() + ' ' + $(this).find('apellidoMaterno').first().text();
	      		nombreDenunciante = nombreDenunciante == "" ? 'An&oacute;nimo' : nombreDenunciante;
				$('#tblDenunciante').append('<tr id="' + idInvolucrado + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificarDenuncianteDatos('+idInvolucrado+');">'+ nombreDenunciante + '</a></td></tr>');
				if($(this).find('condicion').first().text() == 1){
					$('#tblVictima').append('<tr id="v' + idInvolucrado + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificarDenuncianteDatos('+idInvolucrado+');">'+ nombreDenunciante + '</a></td></tr>');
				}
				$("#crearDenunciante").hide();
	      	}
	      	
	      	if($(this).find('valorIdCalidad').find('idCampo').first().text() == '<%= Calidades.DENUNCIANTE_ORGANIZACION.getValorId() %>'){
	      		var idInvolucrado = $(this).find('elementoId').first().text();
	      		var nombreDenunciante = $(this).find('nombreOrganizacion').first().text();
	      		nombreDenunciante = nombreDenunciante == "" ? 'An&oacute;nimo' : nombreDenunciante;
				$('#tblDenunciante').append('<tr id="' + idInvolucrado + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificarDenuncianteDatos('+idInvolucrado+');">'+ nombreDenunciante + '</a></td></tr>');
				if($(this).find('condicion').first().text() == 1){
					$('#tblVictima').append('<tr id="' + idInvolucrado + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificarVictima('+idInvolucrado+');">'+ nombreDenunciante + '</a></td></tr>');
				}				
				$("#crearDenunciante").hide();
	      	}

	      	if($(this).find('valorIdCalidad').find('idCampo').first().text() == '<%= Calidades.VICTIMA_PERSONA.getValorId() %>'){
	      		var idInvolucrado = $(this).find('elementoId').first().text();
	      		var nombreVictima = $(this).find('nombre').first().text() + ' ' + $(this).find('apellidoPaterno').first().text() + ' ' + $(this).find('apellidoMaterno').first().text();
      			$('#tblVictima').append('<tr id="' + idInvolucrado + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificarVictima('+idInvolucrado+');">'+ nombreVictima + '</a></td></tr>');	
	      	}
	      
	      	if($(this).find('valorIdCalidad').find('idCampo').first().text() == '<%= Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId() %>'){
	      		var idInvolucrado = $(this).find('elementoId').first().text(); 
	      		var nombreResponsable = $(this).find('nombre').first().text() + ' ' + $(this).find('apellidoPaterno').first().text() + ' ' + $(this).find('apellidoMaterno').first().text();
				$('#tblProbableResponsable').append('<tr id="' + idInvolucrado + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificarProbableResponsable('+idInvolucrado+');">'+ nombreResponsable + '</a></td></tr>');
	      	}

	      	if($(this).find('valorIdCalidad').find('idCampo').first().text() == '<%= Calidades.PROBABLE_RESPONSABLE_ORGANIZACION.getValorId() %>'){
	      		var idInvolucrado = $(this).find('elementoId').first().text();
	      		var nombreResponsable = $(this).find('nombreOrganizacion').first().text();
				$('#tblProbableResponsable').append('<tr id="' + idInvolucrado + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificarProbableResponsable('+idInvolucrado+');">'+ nombreResponsable + '</a></td></tr>');
	      	}
	      	
	      	if($(this).find('valorIdCalidad').find('idCampo').first().text() == '<%= Calidades.TESTIGO.getValorId() %>'){
	      		var idInvolucrado = $(this).find('elementoId').first().text();
		      	var nombreTestigo = $(this).find('nombre').first().text() + ' ' + $(this).find('apellidoPaterno').first().text() + ' ' + $(this).find('apellidoMaterno').first().text();
				$('#tblTestigo').append('<tr id="' + idInvolucrado + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificarTestigo('+idInvolucrado+');">'+ nombreTestigo + '</a></td></tr>');
	      	}
	      	
		});		
	}

	/**
	** Muestra los medios de transporte en las pesta&ntilde;as correspondientes seg&uacute;n su tipo.
	**/
	function mostrarMediosDeTransporte(xml){
		
		customCargaGrid("GridVehiculos", "<%=Objetos.VEHICULO.getValorId()%>", true, consultarVehiculo);		
		
// 		$(xml).find('VehiculoDTO').each(function(){
// 			var idVehiculo = $(this).find('elementoId').first().text();
//       		var nombreVehiculo = $(this).find('valorByTipoVehiculo').find('valor').first().text() + ' ' + $(this).find('placa').first().text();
// 			$('#tblVehiculo').append('<tr id="' + idVehiculo + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="consultarVehiculo('+idVehiculo+');">'+ nombreVehiculo + '</a></td></tr>');
// 		});

		customCargaGrid("GridAeronaves", "<%=Objetos.AERONAVE.getValorId()%>", true, consultarAeronave);

// 		$(xml).find('AeronaveDTO').each(function(){
// 			var idAeronave = $(this).find('elementoId').first().text();
//       		var nombreAeronave = $(this).find('tipoAeroNave').find('valor').first().text();
// 			$('#tblAeronave').append('<tr id="' + idAeronave + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="consultarAeronave('+idAeronave+');">'+ nombreAeronave + '</a></td></tr>');
// 		});		

		customCargaGrid("GridEmbarcaciones", "<%=Objetos.EMBARCACION.getValorId()%>", true, consultarEmbarcacion);	

// 		$(xml).find('EmbarcacionDTO').each(function(){
// 			var idEmbarcacion = $(this).find('elementoId').first().text();
//       		var nombreEmbarcacion = $(this).find('tipoEmbarcacion').find('valor').first().text();
// 			$('#tblEmbarcacion').append('<tr id="' + idEmbarcacion + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="consultarEmbarcacion('+idEmbarcacion+');">'+ nombreEmbarcacion + '</a></td></tr>');
// 		});		
	}
	/**
	** Muestra los estupefacientes en las pesta&ntilde;as correspondientes seg&uacute;n su tipo.
	**/
	function mostrarSustancias(xml){

		customCargaGrid("GridSustancias", "<%=Objetos.SUSTANCIA.getValorId()%>", true, consultarSustancia);
		
// 		$(xml).find('SustanciaDTO').each(function(){
// 			var idSustancia = $(this).find('elementoId').first().text();
//       		var nombreSustancia = $(this).find('tipoSustancia').find('valor').first().text();
// 			$('#tblEstupefacientes').append('<tr id="' + idSustancia + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="consultarSustancia('+idSustancia+');">'+ nombreSustancia + '</a></td></tr>');
// 		});
	}
	/**
	** Muestra las armas en las pesta&ntilde;as correspondientes seg&uacute;n su tipo.
	**/
	function mostrarArmas(xml){
		
		customCargaGrid("GridArmas", "<%=Objetos.ARMA.getValorId()%>", true, consultarArma);
		
// 		$(xml).find('ArmaDTO').each(function(){
// 			var idArma = $(this).find('elementoId').first().text();
//       		var nombreArma = $(this).find('tipoArma').find('valor').first().text();
// 			$('#tblArma').append('<tr id="' + idArma + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="consultarArma('+idArma+');">'+ nombreArma + '</a></td></tr>');
// 		});

		customCargaGrid("GridExplosivos", "<%=Objetos.EXPLOSIVO.getValorId()%>", true, consultarExplosivo);

// 		$(xml).find('ExplosivoDTO').each(function(){
// 			var idExplosivo = $(this).find('elementoId').first().text();
//       		var nombreExplosivo = $(this).find('tipoExplosivo').find('valor').first().text();
// 			$('#tblExplosivo').append('<tr id="' + idExplosivo + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="consultarExplosivo('+idExplosivo+');">'+ nombreExplosivo + '</a></td></tr>');
// 		});		
	}
	/**
	** Muestra los objetos asegurados.
	**/
	function mostrarNumerario(xml){
		
		customCargaGrid("GridNumerarios", "<%=Objetos.NUMERARIO.getValorId()%>", true, consultarNumerario);		
		
// 		$(xml).find('NumerarioDTO').each(function(){
// 			var idNumerario = $(this).find('elementoId').first().text();
//       		var nombreNumerario = $(this).find('moneda').first().text();
// 			$('#tblNumerario').append('<tr id="'+idNumerario+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="consultarNumerario('+idNumerario+');">'+ nombreNumerario + '</a></td></tr>');
// 		});
	}
	
	function mostrarOtros(){
		customCargaGrid("gridObjsOtros", "<%=Objetos.OTRO.getValorId()%>", true, consultarOtros);
	}
		
	/****** listener cadena de Custodia  ****/
	function asentarRegCadenaCustodia()
	{
		idWindowAsntarRegCadCus++;
		$.newWindow({id:"iframewindowAsntrRegCadCus" + idWindowAsntarRegCadCus, statusBar: true, posx:200,posy:50,width:1000, height:600,title:"Asentar registro de cadena de custodia", type:"iframe"});
	    $.updateWindowContent("iframewindowAsntrRegCadCus" + idWindowAsntarRegCadCus,'<iframe src="<%= request.getContextPath() %>/AsentarRegCadCustodia.do?consultaCadena=0&numeroExpediente='+numeroExpediente +'&IPH=1 " width="1000" height="600" />');
	}
	
	function consultarRegCadenaCustodia()
	{
		idWindowAsntarRegCadCus++;
		$.newWindow({id:"iframewindowCnsltrRegCadCus" + idWindowAsntarRegCadCus, statusBar: true, posx:200,posy:50,width:1000, height:600,title:"Cadena de custodia", type:"iframe"});
	    $.updateWindowContent("iframewindowCnsltrRegCadCus" + idWindowAsntarRegCadCus,'<iframe src="<%= request.getContextPath() %>/AsentarRegCadCustodia.do?consultaCadena=1&numeroExpediente='+numeroExpediente +'&IPH=1 " width="1000" height="600" />');
	}
	/****** FIN listener cadena de Custodia  ****/

	function dlgDetenidoInforme(detenidoExist) {
		//isDetenidoExist = detenidoExist;
		//if (detenidoExist) {
			//$("#tabsconsultaprincipal" ).tabs({ enabled: [] });
		//} else {
			//$("#tabsconsultaprincipal" ).tabs({ disabled: [2] });
		//}		
	}
	
	function habilitaDivOperativo(){
		if($("#chkOperativo").is(':checked')){
			$("#divOperativo").show();
		}else{
			ocultaDivOperativo();
		}
	}

	function ocultaDivOperativo(){
		$("#divOperativo").hide();
	}

	function recuperaDatosMotivos(){
			lsDatosMotivo="";
			lsDatosMotivo+="&tipoEvento="+$("#motivoCmpTipoEvento option:selected").val();
			lsDatosMotivo+="&subtipoEvento="+$("#motivoCmpSubtipoEvento option:selected").val();
			lsDatosMotivo+="&turnoLaboralId="+$("#datosGeneralesCmpTurno option:selected").val();
			lsDatosMotivo+="&tipoParticipacionId="+$("#datosGeneralesCmpTipoParticipacion option:selected").val(); 
		return	lsDatosMotivo;
	}
	
	function recuperaDatosGenerales()
	{
	    var lsDatosGenerales="";
	    lsDatosGenerales+="transporteOficialNum="+$("#datosGeneralesCmpNumeroTransporteOf").val();
	    lsDatosGenerales+="&asunto="+$("#datosGeneralesCmpAsunto").val();
	    lsDatosGenerales+="&numeroEmpleado="+$("#datosGeneralesCmpNumeroEmpleado").val();
	    lsDatosGenerales+="&numeroExpediente="+numeroExpediente;

		if($("#chkOperativo").is(':checked')){
			   lsDatosGenerales+="&nombreOperativo="+$("#datosGeneralesCmpNombreOperativo").val();
			   lsDatosGenerales+="&comandanteAgrupamiento="+$("#datosGeneralesCmpComandanteAgrupamiento").val();
			   lsDatosGenerales+="&comandanteOperativo="+$("#datosGeneralesCmpComandanteOperativo").val();
		}	   
	   
	   var motivo = recuperaDatosMotivos();
	   lsDatosGenerales+=motivo;

	    var txtArea = $('.jquery_ckeditor').val();
		lsDatosGenerales+='&observaciones=' + escape(txtArea);
	   
		if ($("#cbxDistrito option:selected").val() != "" && $("#cbxAgencia option:selected").val() != ""){
			lsDatosGenerales+='&fCatDistritoId=' + $("#cbxDistrito option:selected").val();	
			lsDatosGenerales+='&fCatDiscriminanteId=' + $("#cbxAgencia option:selected").val();
		}
		
	   return lsDatosGenerales;
	}
        function missingField(fieldname, valueWrong, tabname, message){
            
            $("#msgError").addClass("ui-helper-hidden");
            $("#msgError").text("");
            $(tabname).removeClass("ui-state-error ui-corner-all");
            
            if($(fieldname).val() === valueWrong){
                     $(fieldname).focus();  
                     $(fieldname).addClass("ui-state-error ui-corner-all");
                     //alert(msgError);
                     $("#msgError").removeClass("ui-helper-hidden");
                     $("#msgError").text(message);
                     $(tabname).addClass("ui-state-error ui-corner-all");
                     return true;
            }else{
                $(fieldname).removeClass("ui-state-error ui-corner-all");
                     //alert(msgError);
            }
            return false;
        }
	function guardarDatosGeneralesIPH(){
		var params = recuperaDatosGenerales();
		params += "&operativoId=" + operativoId;
		var op=false;
                if(
                    missingField("#datosGeneralesCmpNumeroTransporteOf","","#tabDatosGenerales", "Debe ingresar el numero del transporte oficial.") ||
                    missingField("#datosGeneralesCmpAsunto","","#tabDatosGenerales", "Debe ingresar el asunto.") ||
                    missingField("#motivoCmpTipoEvento option:selected","0","#tabDatosGenerales", "Debe ingresar el tipo de Evento.") ||
                    missingField("#motivoCmpSubtipoEvento option:selected","0","#tabDatosGenerales", "Debe ingresar el subtipo de Evento.") ||
                    missingField("#datosGeneralesCmpNumeroEmpleado","","#tabDatosGenerales", "Debe ingresar el numero de empleado.") ||
                    missingField("#datosGeneralesCmpTurno option:selected","","#tabDatosGenerales", "Debe ingresar el turno.") ||    
                    missingField("#datosGeneralesCmpTipoParticipacion option:selected","","#tabDatosGenerales", "Debe ingresar el tipo de participacion.") ||
                    missingField("#cbxDistrito option:selected","","#tabDatosGenerales", "Debe ingresar Distrito.") ||
                    missingField("#cbxAgencia option:selected","","#tabDatosGenerales", "Debe ingresar la agencia.") 

                )return;
                 if($("#chkOperativo").is(':checked')){
                     if(
                        missingField("#datosGeneralesCmpNombreOperativo","","#tabDatosGenerales", "Debe ingresar el nombre del operativo.") ||
                        missingField("#datosGeneralesCmpComandanteAgrupamiento","","#tabDatosGenerales", "Debe ingresar el comandante del agrupamiento.") ||
                        missingField("#datosGeneralesCmpComandanteOperativo","","#tabDatosGenerales", "Debe ingresar el comandante del operativo.")
                       )
                     return;
                 }
                     $("#msg").removeClass("ui-helper-hidden");
                     $("#msg").text('Guardando...');
                 
                $.ajax({								
                          type: 'POST',
                          url: '<%= request.getContextPath()%>/guardarDatosGeneralesIPH.do?folioIPH='+folioIPH+'',
                          data: params,				
                          dataType: 'xml',
                          async:false,
                          success: function(xml){
                                console.log(xml);
                                  $("#msgError").removeClass("ui-helper-hidden");
                                  $("#msg").text('Datos Generales del IPH guardados de manera correcta');
                                  op=true;
                          },
                          error: function(result) {
                           $("#msgError").text('Datos Generales del IPH guardados de manera correcta');
                           $("#msgError").removeClass("ui-helper-hidden");
                           
                        }
                 });
		
		if(op){
			return "ok";
		}
	}

	<%-- function generarInformeIPH(){
		$.ajax({								
		  	  type: 'POST',
		  	  url: '<%= request.getContextPath()%>/generarInformeIPH.do?folioIPH='+folioIPH+'',			
		  	  dataType: 'xml',
		  	  success: function(xml){			  	  
		  		  alert('Informe Policial Homologado generado de manera correcta');
		  		  document.frmDocumento.documentoId.value = $(xml).find('documentoId').text();
				  document.frmDocumento.submit();
		  	  }
		});
	} --%>
	
	function generarInformeIPH(){
		 
		
		var regreso = guardarDatosGeneralesIPH();
		
		if(regreso == "ok" && iphReplica=="false"){
			var idAgencia = parseInt($("#cbxAgencia option:selected").val());
			$.ajax({								
			  	  type: 'POST',
			  	  url: '<%= request.getContextPath()%>/generarInformeIPH.do?folioIPH='+folioIPH+ '&idAgencia=' + idAgencia + '',			
			  	  dataType: 'xml',
			  	  async:false,
			  	  success: function(xml){			  	  
			  			customAlert('Informe Policial Homologado generado de manera correcta');
			  		  document.frmDocumento.documentoId.value = $(xml).find('idDocumentoIPH').text();
					  document.frmDocumento.submit();
					  //window.parent.cerrarVentanaIPH(idVentana);
			  	  }
					
			});
		}
		else if(regreso == "fail"){
			customAlert("Ocurri&oacute; un problema durante el guardado, intente de nuevo");	
		}else if(iphReplica=="true"){
			customAlert("El IPH  ya ha sido enviado");	
		}
	}
	
	function consultarDistritos(){		
		$.ajax({
			type: 'POST',
		    url: '<%=request.getContextPath()%>/consultarDistritos.do',
		    data: '',
		    dataType: 'xml',
		    async: false,
		    success: function(xml){
			    	$(xml).find('listaCatalogo').find('catDistritoDTO').each(function(){
						$('#cbxDistrito').append('<option value="' + $(this).find('catDistritoId').text() + '">' + $(this).find('claveDistrito').text()+"-"+ $(this).find('nombreDist').text() + '</option>');
					});					
			}
		});
	}
	
	function consultarAgenciasXDistrito(distritoId){
		$('#cbxAgencia').empty();
		$('#cbxAgencia').append('<option value="0">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
		    url: '<%=request.getContextPath()%>/consultarAgenciasDePGJxIdDistrito.do?distritoId='+distritoId+'',
		    data: '',
		    dataType: 'xml',
		    async: false,
		    success: function(xml){
			    	var contAgencias=0;
			    	$(xml).find('listaCatalogo').find('catDiscriminanteDTO').each(function(){
						$('#cbxAgencia').append('<option value="' + $(this).find('catDiscriminanteId').text() + '">' + $(this).find('clave').text()+"-"+ $(this).find('nombre').text() + '</option>');
						contAgencias++;
					});
					if(contAgencias == 0){
						alertDinamico('<bean:message key="mensajeAgenciaValidarDistrito"/>');

					}
			}
		});
	}
	
	function actualizaComboAgencias(){
		distritoId = parseInt($("#cbxDistrito option:selected").val());
		if(distritoId > 0)
			consultarAgenciasXDistrito(distritoId);
		else{
			$('#cbxAgencia').empty();
			$('#cbxAgencia').append('<option value="0">-Seleccione-</option>');
		}
	}
	
	function buscarFuncionario(){
		var numeroEmpleado = $('#datosGeneralesCmpNumeroEmpleado').val();

		if(numeroEmpleado != ""){
	    	$.ajax({
	    		type: 'POST',
	    	    url: '<%=request.getContextPath()%>/consultarPersonalOperativoIPH.do?numeroEmpleado='+numeroEmpleado+'',
	    	    data: '',
	    	    dataType: 'xml',
	    	    async: false,
	    	    success: function(xml){
				   var nombre =  $(xml).find('funcionarioDTO').find('nombreFuncionario').first().text()+' '+$(xml).find('funcionarioDTO').find('apellidoPaternoFuncionario').first().text();
				   var sector =  $(xml).find('funcionarioDTO').find('departamento').find('area').find('nombre').first().text();
				   if(nombre != "" && nombre != null){
					   $('#datosGeneralesCmpOficial').val(nombre);
					   $('#datosGeneralesCmpSector').val(sector);
					   var claveFuncionario =  $(xml).find('funcionarioDTO').find('claveFuncionario').first().text();
					   obtenerSuperior(claveFuncionario);
				   }else{
						alert('No existe Funcionario con ese N&uacute;mero de Empleado');
				   }
	    		}
			});
		}else{
			alert("Debe ingresar un n&uacute;mero de Empleado");
		}
	}

	function obtenerSuperior(claveFuncionario){
		if(claveFuncionario != "" && claveFuncionario != null){
	    	$.ajax({
	    		type: 'POST',
	    	    url: '<%=request.getContextPath()%>/consultaFuncionarioSuperior.do?claveFuncionario='+claveFuncionario+'',
	    	    data: '',
	    	    dataType: 'xml',
	    	    async: false,
	    	    success: function(xml){
				   var nombre =  $(xml).find('funcionarioDTO').find('nombreFuncionario').first().text()+' '+$(xml).find('funcionarioDTO').find('apellidoPaternoFuncionario').first().text();

				   $('#datosGeneralesCmpDirigidoA').val(nombre);
	    		}
			});
		}
	}

	/*
	*Funcion que dispara el Action para consultar Turnos
	*/	
	function cargaTurnos(){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoTurnoLaboral.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				var option;
				$(xml).find('turnoLaboralDTO').each(function(){
					$('#datosGeneralesCmpTurno').append('<option value="' + $(this).find('turnoLaboralId').text() + '">'+ $(this).find('nombreTurno').text() + '</option>');
				});
			}
		});
	}

	/*
	*Funcion que dispara el Action para consultar Tipo Participacion
	*/	
	function cargaTipoParticipacion(){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoTipoParticipacion.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				var option;
				$(xml).find('tipoParticipacion').each(function(){
					$('#datosGeneralesCmpTipoParticipacion').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	function buscaSubTipoEvento(){
		var selected = $("#motivoCmpTipoEvento option:selected").val();
		$( "#motivoCmpSubtipoEvento" ).attr('selectedIndex',0);
		$('#motivoCmpSubtipoEvento').empty();
		$('#motivoCmpSubtipoEvento').append('<option value="0">-Seleccione-</option>');
		
		$.ajax({
			async: false,									// la accion cargar las especialidades
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarSubtipoEvento.do?tipoEvento='+selected+'',
			dataType: 'xml',
			success: function(xml){
				if(selected == "1"){
					$(xml).find('delito').each(function(){
						$('#motivoCmpSubtipoEvento').append('<option value="' + $(this).find('catDelitoId').text() + '">' + $(this).find('nombre').text() + '</option>');
					});
				}else if(selected == "2"){
					$(xml).find('falta').each(function(){
						$('#motivoCmpSubtipoEvento').append('<option value="' + $(this).find('catFaltaAdministrativaId').text() + '">' + $(this).find('nombreFalta').text() + '</option>');
					});
				}
			}
		});
	}
	
	/*	FUNCIONES PARA CREAR PERSONAS INVOLUCRADAS EN IPH	*/
	
	//Abre una nueva ventana de crear una nueva victima
	function creaNuevaVictima() {
		idWindowIngresarVictima++;
		$.newWindow({id:"iframewindowIngresarVictima" + idWindowIngresarVictima, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Ingresar V&iacute;ctima", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarVictima" + idWindowIngresarVictima,'<iframe src="<%= request.getContextPath() %>/IngresarVictima.do?numeroExpediente='+numeroExpediente+'" width="1050" height="600" />');		
	}
	function cargaVictima(nombre,id){
		var row=$('#'+id);
		$(row).remove();
		$('#tblVictima').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarVictima" onclick="modificarVictima('+id+')">'+nombre+'</a></td></tr>');
		cerrarVentanaVictima();
	}
		
	function cerrarVentanaVictima(){
		var pantalla ="iframewindowIngresarVictima";
		pantalla += idWindowIngresarVictima;
		$.closeWindow(pantalla);
	}
	//Abre una nueva ventana para modificar una victima
	function modificarVictima(id) {	
		idWindowIngresarVictima++;
		$.newWindow({id:"iframewindowIngresarVictima" + idWindowIngresarVictima, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Modificar V&iacute;ctima", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarVictima" + idWindowIngresarVictima,'<iframe src="<%= request.getContextPath() %>/IngresarVictima.do?idVictima='+id +'&numeroExpediente='+numeroExpediente +'" width="1100" height="530" />');		
	}
	
	function cargaVictimaDenunciante(nombre,id){
		var row=$('#tblVictima tr:#v'+id);
		$(row).remove();
		$('#tblVictima').append('<tr id="v'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarVictima" onclick="modificarDenuncianteDatos('+id+')">'+nombre+'</a></td></tr>');
	} 
	
	//Funcion para quitar la victima del visor de elementos
	function eliminarVictima(id)
	{
		var row=$('#tblVictima tr:#'+id);
		$(row).remove();
	}
	
	//Funcion para quitar la victima del visor de elementos
	function eliminarVictimaDenunciante(id)
	{
		var row=$('#tblVictima tr:#v'+id);
		$(row).remove();
	}
	
	//Abre una nueva ventana de probable responsable
	function creaNuevoProbResponsable() {
		var probableResponsableProp = '<bean:message key="ingProbaleResponsableTitulo"/>';
		idWindowIngresarProbResponsable++;
		$.newWindow({id:"iframewindowIngresarProbResponsable" + idWindowIngresarProbResponsable, statusBar: true, posx:250,posy:150,width:1050,height:620,title:probableResponsableProp, type:"iframe"});
		$.updateWindowContent("iframewindowIngresarProbResponsable" + idWindowIngresarProbResponsable,'<iframe src="<%= request.getContextPath() %>/IngresarProbResponsable.do?numeroExpediente='+numeroExpediente+'&calidadInv=PROBABLE_RESPONSABLE&isDetenidoExist='+isDetenidoExist+'&detenido=1" width="1050" height="620" />');
	}

	function cargaProbableResponsable(nombre,id){
		var row=$('#'+id);
		$(row).remove();
		nombre=nombre+" - "+'<bean:message key="indiciado" />';
		$('#tblProbableResponsable').append('<tr id="'+id+'"><td class="noSub">&nbsp;&nbsp;&nbsp;<a id="consultarProbableResponsable" onclick="modificarProbableResponsable('+id+')">'+nombre+'</a></td></tr>');
		cerrarVentanaProbableResponsable();		
	} 
	function cerrarVentanaProbableResponsable(){
		var pantalla ="iframewindowIngresarProbResponsable";
		pantalla += idWindowIngresarProbResponsable;
		$.closeWindow(pantalla);
	}

	//Abre una nueva ventana de probable responsable
	function modificarProbableResponsable(id) {
		var probableResponsableProp = '<bean:message key="modProbaleResponsableTitulo"/>';
		idWindowIngresarProbResponsable++;
		$.newWindow({id:"iframewindowIngresarProbResponsable" + idWindowIngresarProbResponsable, statusBar: true, posx:250,posy:150,width:1050,height:620,title:probableResponsableProp, type:"iframe"});
		$.updateWindowContent("iframewindowIngresarProbResponsable" + idWindowIngresarProbResponsable,'<iframe src="<%= request.getContextPath() %>/IngresarProbResponsable.do?idProbableResponsable='+id +'&calidadInv=PROBABLE_RESPONSABLE&detenido=1&numeroExpediente='+numeroExpediente +'" width="1050" height="620" />');			
	}
	
	//Funcion para quitar la victima del visor de elementos
	function eliminarProbableResponsable(id)
	{
		var row=$('#'+id);
		$(row).remove();
	}
	
	//Abre una nueva ventana de Denunciante
	function crearDenunciante(){
		idWindowIngresarDenunciante++;
		$.newWindow({id:"iframewindowIngresarDenunciante" + idWindowIngresarDenunciante, statusBar: true, posx:150,posy:20,width:1040,height:570,title:"Ingresar Denunciante", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarDenunciante" + idWindowIngresarDenunciante,'<iframe src="<%= request.getContextPath() %>/IngresarDenunciante.do?numeroExpediente='+numeroExpediente+'&calidadInv=DENUNCIANTE" width="1040" height="570" />');		
	}
	function cargaDenunciante(nombre,id){
		var row=$('#'+id);
		$(row).remove(); 
		$('#tblDenunciante').append('<tr id="'+id+'"><td class="noSub">&nbsp;&nbsp;&nbsp;<a id="consultarDenunciante" onclick="modificarDenuncianteDatos('+id+')">'+nombre+'</a></td></tr>');
		$('#crearDenunciante').hide();
	} 
	function cerrarVentanaDenunciante(){
		var pantalla ="iframewindowIngresarDenunciante";
		pantalla += idWindowIngresarDenunciante;
		$.closeWindow(pantalla);
	}

	//Abre una nueva ventana de Denunciante
	function modificarDenuncianteDatos(id){
		idWindowIngresarDenunciante++;
		$.newWindow({id:"iframewindowIngresarDenunciante" + idWindowIngresarDenunciante, statusBar: true, posx:150,posy:20,width:1040,height:570,title:"Ingresar Denunciante", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarDenunciante" + idWindowIngresarDenunciante,'<iframe src="<%= request.getContextPath() %>/IngresarDenunciante.do?idDenunciante='+id +'&numeroExpediente='+numeroExpediente +'" width="1040" height="570" />');		
	}
		
	//Funcion para quitar la victima del visor de elementos
	function eliminarDenunciante(id)
	{
		var row=$('#tblDenunciante tr:#'+id);
		$(row).remove();
		$('#crearDenunciante').show();
	}
	
	//Crea una nueva ventana de testigo
	function creaNuevoTestigo() {
		idWindowIngresarTestigo++;
		$.newWindow({id:"iframewindowIngresarTestigo" + idWindowIngresarTestigo, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Ingresar Testigo", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarTestigo" + idWindowIngresarTestigo,'<iframe src="<%= request.getContextPath() %>/IngresarTestigo.do?numeroExpediente='+numeroExpediente+'" width="1050" height="600" />');		
	}
	function cargaTestigo(nombre,id){
		var row=$('#'+id);
		$(row).remove(); 
		$('#tblTestigo').append('<tr id="'+id+'"><td class="noSub">&nbsp;&nbsp;&nbsp;<a id="consultarTestigo" onclick="modificarTestigo('+id+')">'+nombre+'</a></td></tr>');
		cerrarVentanaTestigo();		
	}
	function cerrarVentanaTestigo(){
		var pantalla ="iframewindowIngresarTestigo";
		pantalla += idWindowIngresarTestigo;
		$.closeWindow(pantalla);
	}
	function modificaTestigo(id){
		modificarTestigo(id);
	}
	function modificarTestigo(id) {
		idWindowIngresarTestigo++;
		$.newWindow({id:"iframewindowIngresarTestigo" + idWindowIngresarTestigo, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Modificar Testigo", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarTestigo" + idWindowIngresarTestigo,'<iframe src="<%= request.getContextPath() %>/IngresarTestigo.do?idTestigo='+id+'&numeroExpediente='+numeroExpedienteId +'" width="1100" height="530" />');		
	}
	
	//Funcion para quitar la victima del visor de elementos
	function eliminarTestigo(id)
	{
		var row=$('#tblTestigo tr:#'+id);
		$(row).remove();
		$('#crearDenunciante').show();
	}

	//Abre una nueva ventana de ingresar traductor
	function creaNuevoTraductor() {
		//idWindowIngresarTraductor++;
	//$.newWindow({id:"iframewindow" + idWindowIngresarTraductor, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Traductor", type:"iframe"});
    //$.updateWindowContent("iframewindow" + idWindowIngresarTraductor,'<iframe src="<%= request.getContextPath() %>/IngresarTraductor.do?numeroExpediente='+numeroExpediente+'" width="1050" height="600" />');		
	}	
	
	//Elimina un registro del traductor 
	function eliminaTraductorDeMenuIntermedio(id){
		var row =$('#tblTraductor tr:#'+id);
		$(row).remove();
		alertDinamico("Se anul&oacute; exitosamente el traductor");
	}
	
	function cargaTraductor(nombre,id){
		//var row=$('#'+id);
		//$(row).remove(); 
		//$('#tblTraductor').append('<tr id="'+id+'"><td class="noSub">&nbsp;&nbsp;&nbsp;<a id="consultarTraductor" onclick="modificaTraductor('+id+')">'+nombre+'</a></td></tr>');
	} 

	function ingresarHechos() {
		idWindowIngresarHechos++;
		$.newWindow({id:"iframewindowHecho" + idWindowIngresarHechos, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Hechos", type:"iframe"});
	    $.updateWindowContent("iframewindowHecho" + idWindowIngresarHechos,'<iframe src="<%= request.getContextPath() %>/IngresarHechos.do?iphFuncionalidadHidden=true&numeroExpediente='+numeroExpediente+'&idCalidad=DENUNCIANTE&idHecho=0 " width="1050" height="600" />');		
	}
	function cargaIngresoHecho(nombre,id){
		$("#ingresarHechos").hide();
		$('#tablaHecho').append('<tr><td class="noSub">&nbsp;&nbsp;&nbsp;<a id="hechoId" onclick="consultarHecho('+id+','+numeroExpedienteId+');">'+nombre+'</a></td></tr>');
		cerrarVentanaHecho();
	}
	function cerrarVentanaHecho(){
		var pantalla ="iframewindowHecho";
		pantalla += idWindowIngresarHechos;
		$.closeWindow(pantalla);
	}
	function consultarHecho(idHecho, numeroExpedienteId) {	
		idWindowIngresarHechos++;
		$.newWindow({id:"iframewindowHecho" + idWindowIngresarHechos, statusBar: true, posx:200,posy:50,width:1050,height:600,title:"Hechos", type:"iframe"});
	    $.updateWindowContent("iframewindowHecho" + idWindowIngresarHechos,'<iframe src="<%= request.getContextPath() %>/IngresarHechos.do?iphFuncionalidadHidden=true&numeroExpedienteId='+numeroExpedienteId +'&numeroExpediente='+numeroExpediente+'&idCalidad=DENUNCIANTE&idLugar=1&idHecho='+idHecho +' " width="1050" height="600" />');		
	}

	function creaNuevoVehiculo(){
		idWindowIngresarVehiculo++;
		$.newWindow({id:"iframewindowIngresarVehiculo" + idWindowIngresarVehiculo, statusBar: true, posx:200,posy:5,width:570, height:600,title:"Ingresar veh&iacute;culo", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarVehiculo" + idWindowIngresarVehiculo,'<iframe src="<%= request.getContextPath() %>/IngresarVehiculo.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=VEHICULO&idVehiculo=0" width="570" height="600" />');
	    $("#" +"iframewindowIngresarVehiculo"+idWindowIngresarVehiculo+ " .window-maximizeButton").click();

	}

	function consultarVehiculo(idVehiculo){
		 idWindowIngresarVehiculo++;
		$.newWindow({id:"iframewindowIngresarVehiculo" + idWindowIngresarVehiculo, statusBar: true, posx:200,posy:5,width:570, height:600,title:"Consultar veh&iacute;culo", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarVehiculo" + idWindowIngresarVehiculo,'<iframe src="<%= request.getContextPath() %>/IngresarVehiculo.do?numeroExpediente='+numeroExpediente +'&idVehiculo='+idVehiculo+'&tipoObjeto=VEHICULO " width="570" height="600" />');
	    $("#" +"iframewindowIngresarVehiculo"+idWindowIngresarVehiculo+ " .window-maximizeButton").click();

	}
	function cargaVehiculo(id,tipo,placas){
// 		$('#tblVehiculo tr:#'+id).remove();
// 		if(tipo!="" && placas!="")
// 		{
// 			$('#tblVehiculo').append('<tr id="'+id+'"><td class="noSub">&nbsp;&nbsp;&nbsp;<a id="consultarVehiculo_'+id+'" onclick="consultarVehiculo('+id+')">'+tipo+' '+ placas+'</a></td></tr>');			
// 		}
		//cerrarVentanaVehiculo();
		
		customCargaGrid("GridVehiculos", "<%=Objetos.VEHICULO.getValorId()%>", false);		
	} 

	function muestraMenuQuienDetuvo(){
	}
	
	function cerrarVentanaVehiculo(){
		var pantalla ="iframewindowIngresarVehiculo";
		pantalla += idWindowIngresarVehiculo;
		$.closeWindow(pantalla);
	}

	function creaNuevaAeronave(){
		idWindowIngresarAeronave++;
		$.newWindow({id:"iframewindowIngresarAeronave" + idWindowIngresarAeronave, statusBar: true, posx:200,posy:10,width:600, height:530,title:"Ingresar aeronave", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarAeronave" + idWindowIngresarAeronave,'<iframe src="<%= request.getContextPath() %>/IngresarAeronave.do?numeroExpediente='+numeroExpediente+'" width="600" height="530" />');
	    $("#" +"iframewindowIngresarAeronave"+idWindowIngresarAeronave+ " .window-maximizeButton").click();

	}
	function consultarAeronave(idAeronave){
		idWindowIngresarAeronave++;
		$.newWindow({id:"iframewindowIngresarAeronave" + idWindowIngresarAeronave, statusBar: true, posx:200,posy:10,width:600, height:530,title:"Consultar Aeronave", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarAeronave" + idWindowIngresarAeronave,'<iframe src="<%= request.getContextPath() %>/IngresarAeronave.do?numeroExpediente='+numeroExpediente +'&idAeronave='+idAeronave+'&tipoObjeto=AERONAVE " width="600" height="530" />');
	    $("#" +"iframewindowIngresarAeronave"+idWindowIngresarAeronave+ " .window-maximizeButton").click();

	}
	function cargaAeronave(id,tipo){
// 		$('#tblAeronave tr:#'+id).remove();
// 		if(tipo!="")
// 		{
// 			$('#tblAeronave').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="'+id+'" onclick="consultarAeronave('+id+');">'+ tipo + '</a></td></tr>');		
// 		}
		//cerrarVentanaAeronave();
		
		customCargaGrid("GridAeronaves", "<%=Objetos.AERONAVE.getValorId()%>", false);
	} 
	function cerrarVentanaAeronave(){
		var pantalla ="iframewindowIngresarAeronave";
		pantalla += idWindowIngresarAeronave;
		$.closeWindow(pantalla);
	}

	function creaNuevaEmbarcacion(){
		idWindowIngresarEmbarcacion++;
		$.newWindow({id:"iframewindowIngresarEmbarcacion" + idWindowIngresarEmbarcacion, statusBar: true, posx:200,posy:10,width:600, height:530,title:"Ingresar embarcaci&oacute;n", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarEmbarcacion" + idWindowIngresarEmbarcacion,'<iframe src="<%= request.getContextPath() %>/IngresarEmbarcacion.do?numeroExpediente='+numeroExpediente+'" width="600" height="530" />');
	    $("#" +"iframewindowIngresarEmbarcacion"+idWindowIngresarEmbarcacion+ " .window-maximizeButton").click();

	}
	function consultarEmbarcacion(idEmbarcacion){
		idWindowIngresarEmbarcacion++;
		$.newWindow({id:"iframewindowIngresarEmbarcacion" + idWindowIngresarEmbarcacion, statusBar: true, posx:200,posy:10,width:600, height:530,title:"Consultar embarcaci&oacute;n", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarEmbarcacion" + idWindowIngresarEmbarcacion,'<iframe src="<%= request.getContextPath() %>/IngresarEmbarcacion.do?numeroExpediente='+numeroExpediente +'&idEmbarcacion='+idEmbarcacion+'&tipoObjeto=EMBARCACION " width="600" height="530" />');
	    $("#" +"iframewindowIngresarEmbarcacion"+idWindowIngresarEmbarcacion+ " .window-maximizeButton").click();

	}	
	function cargaEmbarcacion(id,tipo){
// 		$('#tblEmbarcacion tr:#'+id).remove();
// 		if(tipo!="")
// 		{
// 			$('#tblEmbarcacion').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="'+id+'" onclick="consultarEmbarcacion('+id+');">'+ tipo + '</a></td></tr>');		
// 		}
		//cerrarVentanaEmbarcacion();
		
		customCargaGrid("GridEmbarcaciones", "<%=Objetos.EMBARCACION.getValorId()%>", false);
		
	} 
	function cerrarVentanaEmbarcacion(){
		var pantalla ="iframewindowIngresarEmbarcacion";
		pantalla += idWindowIngresarEmbarcacion;
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
// 		$('#tblArma tr:#'+id).remove();
// 		if(tipo!="")
// 		{
// 			$('#tblArma').append('<tr id="'+id+'"><td class="noSub">&nbsp;&nbsp;&nbsp;<a id="consultarArma_'+id+'" onclick="consultarArma('+id+')">'+tipo+'</a></td></tr>');
// 		}
		//cerrarVentanaArma();
		
		customCargaGrid("GridArmas", "<%=Objetos.ARMA.getValorId()%>", false);		
	} 	
	function cerrarVentanaArma(){
		var pantalla ="iframewindowIngresarArma";
		pantalla += idWindowIngresarArma;
		$.closeWindow(pantalla);
	}
	
	function creaNuevoExplosivo(){
		idWindowIngresarExplosivo++;
		$.newWindow({id:"iframewindowIngresarExplosivo" + idWindowIngresarExplosivo, statusBar: true, posx:200,posy:50,width:880,height:330,title:"Ingresar explosivo", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarExplosivo" + idWindowIngresarExplosivo,'<iframe src="<%= request.getContextPath() %>/IngresarExplosivo.do?numeroExpediente='+numeroExpediente+'" width="880" height="330" />');
	    $("#" +"iframewindowIngresarExplosivo"+idWindowIngresarExplosivo+ " .window-maximizeButton").click();
	}
	function consultarExplosivo(idExplosivo){
		idWindowIngresarExplosivo++;
		$.newWindow({id:"iframewindowIngresarExplosivo" + idWindowIngresarExplosivo, statusBar: true, posx:200,posy:50,width:880,height:330,title:"Consultar explosivo", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarExplosivo" + idWindowIngresarExplosivo,'<iframe src="<%= request.getContextPath() %>/IngresarExplosivo.do?numeroExpediente='+numeroExpediente+'&idExplosivo='+idExplosivo+'&tipoObjeto=EXPLOSIVO" width="880" height="330" />');
	    $("#" +"iframewindowIngresarExplosivo"+idWindowIngresarExplosivo+ " .window-maximizeButton").click();
	}
	function cargaExplosivo(id,tipo){
// 		$('#tblExplosivo tr:#'+id).remove();
// 		if(tipo!="")
// 		{
// 			$('#tblExplosivo').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="'+id+'" onclick="consultarExplosivo('+id+');">'+ tipo + '</a></td></tr>');
// 		}
		//cerrarVentanaExplosivo();
		
		customCargaGrid("GridExplosivos", "<%=Objetos.EXPLOSIVO.getValorId()%>", false);
	} 	
	function cerrarVentanaExplosivo(){
		var pantalla ="iframewindowIngresarExplosivo";
		pantalla += idWindowIngresarExplosivo;
		$.closeWindow(pantalla);
	}	
	
	function creaNuevaSustancia(){
		 idWindowIngresarSustancia++;
		$.newWindow({id:"iframewindowIngresarSustancia" + idWindowIngresarSustancia, statusBar: true, posx:200,posy:50,width:900,height:330,title:"Ingresar sustancia", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarSustancia" + idWindowIngresarSustancia,'<iframe src="<%= request.getContextPath() %>/IngresarSustancia.do?numeroExpediente='+numeroExpediente+'" width="900" height="330" />');
	    $("#" +"iframewindowIngresarSustancia"+idWindowIngresarSustancia+ " .window-maximizeButton").click();
	}
	function consultarSustancia(idSustancia){
		idWindowIngresarSustancia++;
		$.newWindow({id:"iframewindowIngresarSustancia" + idWindowIngresarSustancia, statusBar: true, posx:200,posy:50,width:900, height:330,title:"Consultar sustancia", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarSustancia" + idWindowIngresarSustancia,'<iframe src="<%= request.getContextPath() %>/IngresarSustancia.do?numeroExpediente='+numeroExpediente +'&idSustancia='+idSustancia+'&tipoObjeto=SUSTANCIA " width="900" height="330" />');
	    $("#" +"iframewindowIngresarSustancia"+idWindowIngresarSustancia+ " .window-maximizeButton").click();
	}
	function cargaSustancia(id, tipo){
// 		$('#tblEstupefacientes tr:#'+id).remove();
// 		if(tipo!="")
// 		{
// 			$('#tblEstupefacientes').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="'+id+'" onclick="consultarSustancia('+id+');">'+ tipo + '</a></td></tr>');
// 		}
		//cerrarVentanaSustancia();
		
		customCargaGrid("GridExplosivos", "<%=Objetos.SUSTANCIA.getValorId()%>", false);
		
	} 
	function cerrarVentanaSustancia(){
		var pantalla ="iframewindowIngresarSustancia";
		pantalla += idWindowIngresarSustancia;
		$.closeWindow(pantalla);
	}
	
	function creaNuevoNumerario(){
		idWindowIngresarNumerario++;
		$.newWindow({id:"iframewindowIngresarNumerario" + idWindowIngresarNumerario, statusBar: true, posx:200,posy:50,width:800,height:350,title:"Ingresar numerario", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarNumerario" + idWindowIngresarNumerario,'<iframe src="<%= request.getContextPath() %>/IngresarNumerario.do?numeroExpediente='+numeroExpediente+'" width="800" height="350" />');
	    $("#" +"iframewindowIngresarNumerario"+idWindowIngresarNumerario+ " .window-maximizeButton").click();

	}
	function consultarNumerario(idNumerario){
		idWindowIngresarNumerario++;
		$.newWindow({id:"iframewindowIngresarNumerario" + idWindowIngresarNumerario, statusBar: true, posx:200,posy:50,width:800, height:350,title:"Consultar Numerario", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarNumerario" + idWindowIngresarNumerario,'<iframe src="<%= request.getContextPath() %>/IngresarNumerario.do?numeroExpediente='+numeroExpediente +'&idNumerario='+idNumerario+'&tipoObjeto=NUMERARIO " width="800" height="350" />');
	    $("#" +"iframewindowIngresarNumerario"+idWindowIngresarNumerario+ " .window-maximizeButton").click();

	}
	function cargaNumerario(id, tipo){
// 		$('#tblNumerario tr:#'+id).remove();
// 		if(tipo!="")
// 		{
// 			$('#tblNumerario').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="'+id+'" onclick="consultarNumerario('+id+');">'+ tipo + '</a></td></tr>');
// 		}
		//cerrarVentanaNumerario();
		
		customCargaGrid("GridNumerarios", "<%=Objetos.NUMERARIO.getValorId()%>", false);
		
	} 
	function cerrarVentanaNumerario(){
		var pantalla ="iframewindowIngresarNumerario";
		pantalla += idWindowIngresarNumerario;
		$.closeWindow(pantalla);
	}
	
	function creaNuevoOtros(){
		 idWindowIngresarOtros++;
		$.newWindow({id:"iframewindowOtros" + idWindowIngresarOtros, statusBar: true, posx:200,posy:50,width:800,height:450,title:"Ingresar Otros", type:"iframe"});
	    $.updateWindowContent("iframewindowOtros" + idWindowIngresarOtros,'<iframe src="<%= request.getContextPath() %>/IngresarOtros.do?numeroExpediente='+numeroExpediente +'&idOtros=0&tipoObjeto=OTRO" width="800" height="450" />');
	    $("#" +"iframewindowOtros"+idWindowIngresarOtros+ " .window-maximizeButton").click();

	}
	
	function consultarOtros(idOtros){
		idWindowIngresarOtros++;
		$.newWindow({id:"iframewindowOtros" + idWindowIngresarOtros, statusBar: true, posx:200,posy:50,width:800,height:450,title:"Consultar otros", type:"iframe"});
	    $.updateWindowContent("iframewindowOtros" + idWindowIngresarOtros,'<iframe src="<%= request.getContextPath() %>/IngresarOtros.do?numeroExpediente='+numeroExpediente+'&idOtros='+idOtros+'&tipoObjeto=OTRO" width="800" height="450" />');
	    $("#" +"iframewindowOtros"+idWindowIngresarOtros+ " .window-maximizeButton").click();

	}
	
	function cargaOtros(id,nombre){
		customCargaGrid("gridObjsOtros", "<%=Objetos.OTRO.getValorId()%>", false);
	}
	
	
	
	function cerrarVentanaOtros(){
		var pantalla ="iframewindowOtros";
		pantalla += idWindowIngresarOtros;
		$.closeWindow(pantalla);
	}

	function resumen(){
		 $.ajax({
		      type: 'POST',
	    	  url: '<%= request.getContextPath()%>/cargarDatosGenerales.do?idNumeroExpedienteOp='+numeroExpedienteId,
	    	  data: '',
	    	  dataType: 'xml',
	    	  async: false,
	    	  success: function(xml){
					$('#Vehiculos').html($(xml).find('totalVehiculos').text()+': '+$(xml).find('vehiculos').text());
					$('#Aeronaves').html($(xml).find('totalAeronaves').text()+': '+$(xml).find('aeronaves').text());
					$('#Embarcacion').html($(xml).find('totalEmbarcaciones').text()+': '+$(xml).find('embarcaciones').text());
					$('#Sustancias').html($(xml).find('totalSustancias').text()+': '+$(xml).find('sustancias').text());
					$('#Armas').html($(xml).find('totalArmas').text()+': '+$(xml).find('armas').text());
					$('#Explosivos').html($(xml).find('totalExplosivos').text()+': '+$(xml).find('explosivos').text());
					$('#Numerario').html($(xml).find('totalNumerarios').text()+': '+$(xml).find('numerarios').text());
					$('#Denunciantes').html($(xml).find('totalDenunciantes').text() == "" ? 'An&oacute;nimo' : $(xml).find('totalDenunciantes').text()+': '+$(xml).find('denunciantes').text());
					$('#Victimas').html($(xml).find('totalVictimas').text()+': '+$(xml).find('victimas').text());
					$('#ProbablesResponsables').html($(xml).find('totalProbablesResposables').text()+': '+$(xml).find('probablesResposables').text());
					$('#Testigos').html($(xml).find('totalTestigos').text()+': '+$(xml).find('testigos').text());

					$('#estatusExpe').html($(xml).find('estatusNumeroExpediente').text());
					$('#origenExpe').html($(xml).find('origenExpediente').text());	
					$('#anonimoDenun').html($(xml).find('esDesconocido').text());
					num=$(xml).find('totalDocumentosDelExpediente').text();
					$('#fehcaApertura').html("Fecha Apertura:"+$(xml).find('fechaApertura').text());

					ocultaImgResumen(xml);				
	    	  }
	    	});
	}

	function ocultaImgResumen(xml){
		if($(xml).find('ve').text()!="1"){
			$('#imgVehiculo').hide();
		}else{
			$('#imgVehiculo').show();
		}
		if($(xml).find('aero').text()!="1"){
			$('#imgAeronaves').hide();
		}else{
			$('#imgAeronaves').show();
		}
		if($(xml).find('embar').text()!="1"){
			$('#imgEmbarcacion').hide();
		}else{
			$('#imgEmbarcacion').show();
		}					
		if($(xml).find('sus').text()!="1"){
			$('#imgSustancias').hide();
		}else{
			$('#imgSustancias').show();
		}
		if($(xml).find('arm').text()!="1"){
			$('#imgArmas').hide();
		}else{
			$('#imgArmas').show();
		}
		if($(xml).find('expl').text()!="1"){
			$('#imgExplosivos').hide();
		}else{
			$('#imgExplosivos').show();
		}
		if($(xml).find('nume').text()!="1"){
			$('#imgNumerario').hide();
		}else{
			$('#imgNumerario').show();
		}
		if($(xml).find('denun').text()!="1"){
			$('#imgDenunciantes').hide();
		}else{
			$('#imgDenunciantes').show();
		}
		if($(xml).find('vic').text()!="1"){
			$('#imgVictimas').hide();
		}else{
			$('#imgVictimas').show();	
		}
		if($(xml).find('proba').text()!="1"){
			$('#imgProbablesResponsables').hide();
		}else{
			$('#imgProbablesResponsables').show();
		}
		if($(xml).find('test').text()!="1"){
			$('#imgTestigos').hide();
		}else{
			$('#imgTestigos').show();
		}
	}

	function cargaGridDocumentosDigitalesPropios(){ 

		if(primeraVezGridDocumentosDigitalesPropios == true){
			jQuery("#gridDocumentosDigitalesPropios").jqGrid({
				url:'<%=request.getContextPath()%>/ConsultaExpedientesDocumento.do?numeroExpedienteId='+numeroExpedienteId+'',
				datatype: "xml", 
				colNames:['Nombre de Documento'],
				colModel:[	{name:'nombre',index:'nombre', width:255},
				       
							],
				pager: jQuery('#pagerGridDocumentosDigitalesPropios'),
				rowNum:20,
				rowList:[10,20,30],
				width:250,
				height:455,
				sortname: 'nombreDocumento',
				viewrecords: true,
				sortorder: "desc",
				//multiselect:true,
				ondblClickRow: function(rowid) {
					if (rowid) {
						abrirDocsDigAsociadosASol(rowid);
					}
				}
				
			}).navGrid('#pagerGridDocumentosDigitalesPropios',{edit:false,add:false,del:false});
			$("#gview_gridDocumentosDigitalesPropios .ui-jqgrid-bdiv").css('height', '455px');
			primeraVezGridDocumentosDigitalesPropios= false;
		}
		else{
			jQuery("#gridDocumentosDigitalesPropios").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/ConsultaExpedientesDocumento?numeroExpedienteId='+numExpedienteId+'',datatype: "xml" });
			$("#gridDocumentosDigitalesPropios").trigger("reloadGrid");
		}
	}	

	/*
	*Funcion que abre el PDF para ver los documentos asociados al numero de causa
	*/
	function abrirDocsDigAsociadosASol(documentoAsocId){
		if(documentoAsocId != null && documentoAsocId != ""){
		$("#visorDocsPropiosFrame").attr("src","<%= request.getContextPath()%>/consultarArchivoDigitalIframe.do?documentoId="+documentoAsocId+"&inFrame=true");
			
		}
		else{
			alert("El documento no puede ser mostrado");
		}
	}
	
	/**
	**Funcion que carga el grid con las pertenencias de los probables responsables
	**/
	function cargaGridPertenencias(){
		//var idInvolucrado = $("#detencionCbxProbableResponsable option:selected").val();
		var probableResponsableProp = '<bean:message key="probableResponsableTitulo"/>';

					
				jQuery("#gridPertenencias").jqGrid({ 
					url:'<%= request.getContextPath()%>/consultarPertenenciasPorExpedienteId.do?expedienteId='+expedienteId+'&numeroExpediente='+numeroExpediente+'', 
					datatype: "xml", 
					colNames:[probableResponsableProp,'Cantidad','Tipo','Condici&oacute;n F&iacute;sica','Descripci&oacute;n' ], 
					colModel:[ 	{name:'ProbableResponsable',index:'4',sortable:false,  width:200},
					           	{name:'Cantidad',index:'2', sortable:true ,  width:60, align:"center"},
					           	{name:'Tipo', index:'1', sortable:true, width:150},
					           	{name:'CondicionFisica', sortable:true, index:'3', width:150},
					           	{name:'Descripcion', sortable:false, index:'5', width:200}
							],
					pager: jQuery('#pagerGridPertenencias'),
					rowNum:10,
					rowList:[10,20,30],
					//autowidth: true,
					//autoheight:true,
					width:780,
					height:300,
					sortname: '1',
					viewrecords: true,
					sortorder: "desc"
				}).navGrid('#pagerGridPertenencias',{edit:false,add:false,del:false});
				//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
				firstGridPertenencias=false;
			

	}
	
	
	/**
	**Funcion que carga el grid con los probables responsables
	**/
	function cargaGridProbableResponsable(){

			jQuery("#gridProbableResponsable").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarProbableResponsablePorNumeroExpediente.do?expedienteId='+expedienteId+'&numeroExpediente='+numeroExpediente+'',
				datatype: "xml", 
				colNames:['Nombre','Apellido Paterno','Apellido Materno','&iquest;Detenido?','Fecha de Detenci&oacute;n','&iquest;Menor de Edad?','ID Tipo Evento','Tipo Evento','ID Subtipo Evento','Subtipo de Evento'], 
				colModel:[ 	{name:'Nombre',index:'4', sortable:false, width:160},
				           	{name:'ApellidoPaterno',index:'2', sortable:false, width:140},
				           	{name:'ApellidoMaterno',index:'3', sortable:false, width:140},
				           	{name:'Detencion',index:'1', sortable:true, width:90,align:"center"},
				           	{name:'FechaDetencion',index:'5', sortable:false, width:160},
				           	{name:'MenorDeEdad',index:'6', sortable:false, width:120,align:"center"},
				           	{name:'idTipoEvento',index:'7', sortable:false, width:10,hidden:true},
				           	{name:'TipoEvento',index:'8', sortable:false, width:170},
				           	{name:'idSubTipoEvento',index:'9', sortable:false, width:10,hidden:true},
				           	{name:'SubTipoEvento',index:'10', sortable:false, width:170}
						],
				pager: jQuery('#pagerGridProbableResponsable'),
				rowNum:10,
				rowList:[10,20,30],
				//autowidth: true,
				//autoheight:true,
				width:900,
				height:300,
				sortname: '1',
				viewrecords: true,
				sortorder: "desc",
				
				ondblClickRow: function(rowid) {
					
					}
				
			}).navGrid('#pagerGridProbableResponsable',{edit:false,add:false,del:false});

	}
	
 	/*
	*Funcion que carga el grid de consulta IPHs
	*/
	function customCargaGrid(id, tipoObjeto, esPrimeraVez, funcDblClic){
		//alert('Entra a cargar grid iph');

		if(esPrimeraVez == true){
			var names;
			var model;
			if (tipoObjeto == '<%=Objetos.VEHICULO.getValorId()%>'){
				names = ['Tipo','Placas']; 
				model = [ 	{name:'tipo',index:'1',width:350, align:'center'},
									{name:'placas',index:'2',width:500, align:'center'}];
			} else {
				names =['Nombre'], 
				model =[ 	{name:'nombre',index:'1',width:700, align:'center'}];				
			}
			
			  jQuery("#"+id).jqGrid({ 
					url:'<%=request.getContextPath()%>/consultarObjetos.do',
					mtype: "POST",
					postData: {
						"expedienteId":expedienteId,
						"tipoObjeto":tipoObjeto,
						"esGrid":true
					},
					datatype: "xml",
					colNames:names, 
					colModel:model,
					autowidth: false,
					width:700, 
					pager: jQuery('#pager'+id),
					rowNum:10,
					rowList:[10,20,30,40,50,60,70,80,90,100],
					sortname: '1',
					sortorder: "desc", 
					viewrecords: true,
					ondblClickRow: function(rowid) {
						funcDblClic(rowid);
					}
				}).navGrid('#pager'+id,{edit:false,add:false,del:false}).trigger("reloadGrid");;
			  
			  
			  
		} else {
			  jQuery("#"+id).jqGrid({ 
					url:'<%=request.getContextPath()%>/consultarObjetos.do',
					type: "post",
					data:{
						"expedienteId":expedienteId,
						"tipoObjeto":tipoObjeto,
						"esGrid":true
					}});

			  $("#"+id).trigger("reloadGrid");
		}
	}
	
</script>

</head>

<body>

	<table width="100%" class="back_pleca_h">
		<tr>	
                    <td align="left " width="50%"> 
                        <span id="msgError" class="ui-helper-hidden ui-state-error ui-corner-all"></span>
                        <span id="msg" class="ui-helper-hidden ui-state-highlight ui-corner-all"></span>
                    </td>
			<td align="right" width="50%">
					<input type="button" value="Adjuntar Documento" id="btnAdjuntar" class="ui-button ui-corner-all ui-widget" onclick="abreVentanaAdjuntarDocumentoAExpediente()"/>
					 <input type="button" value="Guardar" id="btnIPHGuardadoParcial" class="ui-button ui-corner-all ui-widget" onclick="guardarDatosGeneralesIPH()"/>
					 <input type="button" value="Generar Informe" class="ui-button ui-corner-all ui-widget" onclick="generarInformeIPH()"/>
			</td>
		</tr>
	</table>

    <div class="error1" style="display:none;">
      <img src="<%= request.getContextPath()%>/resources/images/warning.gif" alt="Warning!" width="24" height="24" style="float:left; margin: -5px 10px 0px 0px; " />

      <span></span>.<br clear="all"/>
    </div>

	<form name="frmDocumento" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
		<input type="hidden" name="documentoId" />
	</form>

	<div id="tabsconsultaprincipal">
		<ul>
			<li id="tabResumen"><a href="#tabsconsultaprincipal-12" onclick="resumen()">Resumen</a></li>
			<li id="tabRegistroPertenencias"><a href="#tabsconsultaprincipal-13">Registro de Pertenencias</a></li>
			<li id="tabDetencion"><a href="#tabsconsultaprincipal-14">Detenci&oacute;n</a></li>
			<li id="tabDatosGenerales"><a href="#tabsconsultaprincipal-1">Datos Generales</a></li>
			<li id="tabHechos"><a href="#tabsconsultaprincipal-11">Hechos</a></li>
			<li id="tabInvolucrados"><a href="#tabsconsultaprincipal-2">Personas Involucradas</a></li>
			<li id="tabAseguramientos"><a href="#tabsconsultaprincipal-15">Aseguramientos</a></li>
			<li class="tabTabsCadCus" ><a href="#tabsconsultaprincipal-9">Cadena de Custodia</a></li>
			<li id="tabInformacion"><a href="#tabsconsultaprincipal-7">Documentos de Apoyo</a></li>
			<li id="tabObservaciones"><a href="#tabsconsultaprincipal-8">Observaciones Generales</a></li>
		</ul>
		
		<!--COMIENZA TAB DE RESUMEN-->
		<div id="tabsconsultaprincipal-12" class="tabResumen" >
			<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" class="back_hechos">
				<tr><td colspan="6">&nbsp;</td></tr>			
			  	<tr style="border-bottom-style: solid; border: 1;background-image:">
				    <td width="238" style="font-size:14px; background-color:" align="right"><strong>Estatus del Expediente:</strong></td>
				    <td width="19" style="font-size:14px; background-color:" >&nbsp;</td>
				    <td width="507" align="center" style="font-size:14px; background-color:"><strong>Resumen de objetos:</strong></td>
				    <td width="4" style="font-size:14px; background-color:">&nbsp;</td>
			  	</tr>
			  	<tr>
			    	<td  colspan="6" height="20px">
			    		<table width="978" border="0" cellpadding="0" cellspacing="0" class="linea_down_gral" align="center">
					  		<tr>
					    		<td>&nbsp;</td>
					  		</tr>
						</table>			    	
			    	</td>
			  	</tr>
			  	<tr valign="top">
			    	<td id="estatusExpe" align="right"></td>
			    	<td></td>
			    	<td rowspan="16" align="center" width="507" style="background-color:" valign="top">
			    		<table border="0" cellpadding="0" cellspacing="0" width="99%">
		          			<tr>
		            			<td width="145" align="right" nowrap style="background-color:">Veh&iacute;culos:</td>
					            <td width="332" id="Vehiculos">&nbsp;</td>
					            <td id="imgVehiculo" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>			            
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
				            	<td align="right" style="background-color:">Sustancias:</td>
				            	<td id="Sustancias">&nbsp;</td>
				            	<td id="imgSustancias" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
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
					            <td align="right" style="background-color:">Numerario:</td>
					            <td id="Numerario">&nbsp;</td>
					            <td id="imgNumerario" width="25"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
				          	</tr>
				          	<tr>
				          		<td>&nbsp;</td>
				          	</tr>
				          	<tr style="border-bottom-style: solid; border: 1;background-image:">
				          		<td width="670" align="center" style="font-size:14px; background-color:" colspan="2">
				          		<strong>Resumen de involucrados<em>:</em></strong></td>	
				    			<!--<td width="4" style="font-size:14px; background-color:">&nbsp;</td>-->
				    		</tr>
				    		<tr>
								<td height="20px" colspan="6">
									<table class="linea_down_gral" width="578" cellspacing="0" cellpadding="0" border="0" align="center">
										<tbody>
											<tr>
												<td>&nbsp;</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						  	<tr>
						    	<td width="158" align="right" style="background-color:">Denunciantes:</td>
						    	<td width="97" id="Denunciantes">&nbsp;</td>
						    	<td id="imgDenunciantes" width="15"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
						  	</tr>
  							<tr>
							    <td align="right" style="background-color:">Victimas:</td>
							    <td id="Victimas">&nbsp;</td>
							    <td id="imgVictimas" width="15"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
  							</tr>
  							<tr>
							    <td align="right" style="background-color:"><bean:message key="plProbalbeResponsableTitulo"/>:</td>
							    <td id="ProbablesResponsables">&nbsp;</td>
							    <td id="imgProbablesResponsables" width="15"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
  							</tr>
  							<tr>
							    <td align="right" style="background-color:">Testigos:</td>
							    <td id="Testigos">&nbsp;</td>
							    <td id="imgTestigos" width="15"><img title="Contiene mas registros" width="10px" src='<%= request.getContextPath() %>/resources/images/add.png'></td>
  							</tr>
				    		
						</table>
			
					</td>
			  	</tr>
			  	<tr>
			    	<td  style="background-color:"align="right">
			    		<span style="border-left:#000000; border-top:#000000; border-bottom-width:4; font-size:14px; background-color:">
			    			<img src="<%=request.getContextPath()%>/resources/images/icn_doc_chek.png"><strong>Tipo:</strong>
			    		</span>
			    	</td>
			    	<td ></td>
			  	</tr>
			  	<tr>
			    	<td id="origenExpe" align="right"></td>
			    	<td ></td>
			  	</tr>
			  	<tr>
			    	<td ></td>
			    	<td></td>			    
			  	</tr>
			  	<tr>
				    <td>&nbsp;</td>
				    <td></td>
			  	</tr>
			  	<tr>
			    	<td  id="anonimoDenun" align="right">&nbsp;</td>
			    	<td></td>
			  	</tr>
			  	<tr>
			    	<td align="right"><span style="border-left:#000000; border-top:#000000; border-bottom-width:4; font-size:14px; background-color:; display:none;"><strong>Canalizado a:</strong></span> </td>
			    	<td><!-- <input type="radio" name="radio" id="rbtnRestaurativa" value="R" />--></td> 
			    	<td align="right">&nbsp;</td>
			    	<td>&nbsp;</td>
			  	</tr>
			  	<tr>
			    	<td id="fehcaApertura" align="right">&nbsp;</td>
			    	<td><!-- <input type="radio" name="radio" id="rbtnUnidadDeInvestigacion" value="rbtnUnidadDeInvestigacion" />--></td>
			    	<td align="right">&nbsp;</td>
			    	<td>&nbsp;</td>
			  	</tr>
			  	<tr>
			    	<td></td>
			    	<td>&nbsp;</td>
			    	<td align="right">&nbsp;</td>
			    	<td>&nbsp;</td>
			  	</tr>
			  	<tr>
			    	<td>&nbsp;</td>
			    	<td>&nbsp;</td>
			    	<td align="right">&nbsp;</td>
			    	<td>&nbsp;</td>
			  	</tr>
			  	<!--<tr>
			    	<td align="right"><span id="spanGralJAR">Justicia Alternativa Restaurativa</span></td>
			    	<td>&nbsp;</td>
			    	<td align="right">&nbsp;</td>
			    	<td>&nbsp;</td>
			  	</tr>
			  	<tr>
			    	<td align="right"><span id="spanGralUI">Unidad de Investigaci&oacute;n: </span><span id="spanInfoGralUI"></span></td>
			    	<td>&nbsp;</td>
			    	<td align="right">&nbsp;</td>
			    	<td>&nbsp;</td>
			  	</tr>
			  	<tr>
			    	<td align="right"><span id="spanGralIE">Instituci&oacute;n Externa: </span><span id="spanInfoGralIE"></span></td>
			    	<td>&nbsp;</td>
			    	<td align="right">&nbsp;</td>
			    	<td>&nbsp;</td>
			  	</tr>-->
			  	<tr>
			    	<td>&nbsp;</td>
			    	<td>&nbsp;</td>
			    	<td align="right">&nbsp;</td>
			    	<td>&nbsp;</td>
			  	</tr>
			  	<tr>
			    	<td>&nbsp;</td>
			    	<td>&nbsp;</td>
			    	<td align="right">&nbsp;</td>
			    	<td>&nbsp;</td>
			  	</tr>
			</table>
		
		</div>
		<!--TERMINA TAB DE RESUMEN-->

		<!--COMIENZA TAB DE REGISTRO DE PERTENENCIAS-->
		<div id="tabsconsultaprincipal-13" class="tabRegistroPertenencias">
			<fieldset style="width: 900px;">
				<legend>Pertenencias</legend>
				<table width="100%" border="0" height="90%">
					<tr>
						<td colspan="4" align="center">
		            		<table id="gridPertenencias"></table>
		            		<div id="pagerGridPertenencias"></div>
		            	</td>
					</tr>
				</table>
			</fieldset>
		</div>
		<!--TERMINA TAB DE REGISTRO DE PERTENENCIAS-->
		
		<!--COMIENZA TAB DE DETENCION-->
		<div id="tabsconsultaprincipal-14" class="tabDetencion">
			<fieldset style="width: 900px;">
				<legend><bean:message key="plProbalbeResponsableTitulo"/> Detenidos y No Detenidos</legend>
				<table width="100%" border="0" height="90%">
					<tr>
						<td colspan="6" align="center">
		            		<table id="gridProbableResponsable"></table>
		            		<div id="pagerGridProbableResponsable"></div>
		            	</td>
					</tr>
				</table>
			</fieldset>
		
		</div>	
		<!--TERMINA TAB DE DETENCION-->
		
		<!--COMIENZAN TABS INFERIORES DE DATOS GENERALES-->
		<div id="tabsconsultaprincipal-1" class="tabDatosGenerales">
			<div id="tabschild" class="tabs-bottom">
				<div id="tabschild-1">
					<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" class="back_hechos">
						<tr valign="top">
							<td valign="top">
								<table>
									<tr>
										<td colspan="3">&nbsp;</td>
									</tr>
									<tr>
										<td align="right">* N&uacute;mero Ec&oacute;nomico del Transporte Oficial:</td>
										<td><input type="text" style="width: 180px;" maxlength="30" id="datosGeneralesCmpNumeroTransporteOf"/></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td align="right">* Asunto:</td>
										<td><input type="text" style="width: 460px;" maxlength="150" id="datosGeneralesCmpAsunto"/></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td align="right">* Tipo de Evento:</td>
										<td>
											<select id="motivoCmpTipoEvento" style="width: 180px;" onchange="buscaSubTipoEvento();" disabled="disabled">
												<option value="0">- Seleccione -</option>
												<option value="1">Delito</option>
												<option value="2">Falta Administrativa</option>
											</select>
										</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td align="right">* Subtipo de Evento:</td>
										<td>
											<select id="motivoCmpSubtipoEvento" style="width: 400px;">
												<option value="">- Seleccione -</option>
											</select>
										</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td align="right">* N&uacute;mero de Empleado:</td>
										<td><input type="text" style="width: 180px;" maxlength="30" id="datosGeneralesCmpNumeroEmpleado" /></td>
										<td><input type="button" id="btnFuncionario" value="Validar Funcionario" onclick="buscarFuncionario();" class="ui-button ui-corner-all ui-widget" ></td>
									</tr>
									<tr>
										<td align="right">Oficial:</td>
										<td><input type="text" style="width: 180px;" maxlength="50" id="datosGeneralesCmpOficial" disabled="disabled"/></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td align="right">Sector:</td>
										<td><input type="text" style="width: 180px;" maxlength="50" id="datosGeneralesCmpSector" disabled="disabled"/></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td align="right">Dirigido a:</td>
										<td><input type="text" style="width: 180px;" maxlength="50" id="datosGeneralesCmpDirigidoA" disabled="disabled"/></td>
										<td>&nbsp;</td>
									</tr>
									
									<tr>
										<td align="right">Corporacion:</td>
										<td>
											<select id="datosGeneralesCmpCorporaciones" style="width: 180px;">
												<option value="">- Seleccione -</option>
											</select>
										</td>
										<td>&nbsp;</td>
									</tr>									
									
									<tr>
										<td align="right">* Turno:</td>
										<td>
											<select id="datosGeneralesCmpTurno" style="width: 180px;">
												<option value="">- Seleccione -</option>
											</select>
										</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td align="right">* Participaci&oacute;n:</td>
										<td>
											<select id="datosGeneralesCmpTipoParticipacion" style="width: 180px;">
												<option value="">- Seleccione -</option>
											</select>
										</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
									  <td align="right">* Distrito:</td>
									  <td><select name="cbxDistrito" id="cbxDistrito" style="width: 300px;" onchange="actualizaComboAgencias()">
									    <option value="">- Seleccione -</option>
								      </select></td>
									  <td>&nbsp;</td>
								  </tr>
									<tr>
									  <td align="right">* <bean:message key="agencia"/>:</td>
									  <td><select name="cbxAgencia" id="cbxAgencia" style="width: 300px;">
									    <option value="">- Seleccione -</option>
								      </select></td>
									   <td>&nbsp;</td>
								  </tr>
			                        <tr>
			                            <td align="right">Se realiz&oacute; Operativo</td>
			                            <td>
			                              <input type="checkbox" id="chkOperativo"/>
			                            </td>                 
										<td>&nbsp;</td>
			                        </tr>  
			                        <tr>
			                        	<td colspan="3">
				                        	<div id="divOperativo">
				                        		<table>
							                        <tr>
							                            <td align="right" width="52%">* Nombre del Operativo</td>
														<td><input type="text" style="width: 180px;" maxlength="50" id="datosGeneralesCmpNombreOperativo"/></td>
														<td>&nbsp;</td>
							                        </tr>  
							                        <tr>
							                            <td align="right">* Comandante Agrupamiento</td>
														<td><input type="text" style="width: 180px;" maxlength="50" id="datosGeneralesCmpComandanteAgrupamiento"/></td>
														<td>&nbsp;</td>
							                        </tr>  
							                        <tr>
							                            <td align="right">* Comandante Operativo</td>
														<td><input type="text" style="width: 180px;" maxlength="50" id="datosGeneralesCmpComandanteOperativo"/></td>
														<td>&nbsp;</td>
							                        </tr>  
				                        		</table>
				                        	</div>
			                        	</td>
			                        </tr>          
								</table>
							</td>
						</tr>
					</table>
				</div>
				
			</div>
			
		</div>
		<!--TERMINAN TABS INFERIORES DE DATOS GENERALES-->
		
		<!--COMIENZAN TABS INFERIORES DE HECHOS-->
		<div id="tabsconsultaprincipal-11" class="tabHechos">
			<div style="width: 1042px; height: 490px;" class="back_hechos">
			<table border="0" cellspacing="0" cellpadding="0" id="tablaHecho" class="back_hechos" style="padding: .5cm; " >
				<tr valign="top">						
					<td valign="top"><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="ingresarHechos" value="Ingreso Hecho" class="btn_estilo"/></td>
				</tr>
			</table>
			</div>
		</div>
		<!--TERMINAN TABS INFERIORES DE HECHOS-->
		
		<!--COMIENZAN TABS INFERIORES DE PERSONAS INVOLUCRADAS-->
		<div id="tabsconsultaprincipal-2" class="tabInvolucrados">
			<div id="tabschild2" class="tabs-bottom">
				<ul>
					<li><a href="#tabschild2-1">Denunciante</a></li>
					<li><a href="#tabschild2-2">V&iacute;ctima</a></li>
					<li><a href="#tabschild2-3"><bean:message key="probableResponsableTitulo"/></a></li>
					<li><a href="#tabschild2-4">Testigo</a></li>
				</ul>
				
				<div id="tabschild2-1">
					<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" class="back_hechos">
						<tr valign="top">
							<td valign="top">
								<table width="25%" cellpadding="0" cellspacing="0" id="tblDenunciante">
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="crearDenunciante"><input type="button" value="Ingresar Denunciante" class="ui-button ui-corner-all ui-widget"/></a></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="tabschild2-2">
					<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" class="back_hechos">
						<tr valign="top">
							<td valign="top">	
								<table width="25%" cellpadding="0" cellspacing="0" id="tblVictima">
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevaVictima"><input type="button" value="Ingresar V&iacute;ctima" class="ui-button ui-corner-all ui-widget"/></a></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="tabschild2-3">
					<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" class="back_hechos">
						<tr valign="top">
							<td valign="top">
								<table width="25%" cellpadding="0" cellspacing="0" id="tblProbableResponsable">
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevoProbResponsable"><input type="button" value='<bean:message key="ingProbaleResponsable"/>' class="ui-button ui-corner-all ui-widget"/></a></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="tabschild2-4">
					<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" class="back_hechos">
						<tr valign="top">
							<td valign="top">
								<table width="25%" cellpadding="0" cellspacing="0" id="tblTestigo">
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevoTestigo"><input type="button" value="Ingresar Testigo" class="ui-button ui-corner-all ui-widget"/></a></td>
									</tr>
									
								</table>
							</td>
						</tr>
					</table>
				</div>
					
			</div>

		</div>
		
		<!--COMIENZAN TABS INFERIORES DE ASEGURAMIENTO-->
		<div id="tabsconsultaprincipal-15" class="tabAseguramientos">
			<div id="tabschild15" class="tabs-bottom">
				<ul>
					<li><a href="#tabschild15-1">Veh&iacute;culo</a></li>
					<li><a href="#tabschild15-2">Aeronave</a></li>
					<li><a href="#tabschild15-3">Embarcaci&oacute;n</a></li>
					<li><a href="#tabschild15-4">Sustancia</a></li>
					<li><a href="#tabschild15-5">Arma</a></li>
					<li><a href="#tabschild15-6">Explosivo</a></li>
					<li><a href="#tabschild15-7">Numerario</a></li>
					<li><a href="#tabschild15-8">Otro</a></li>
				</ul>
				
			<!--COMIENZAN TABS INFERIORES DE MEDIOS DE TRANSPORTE-->
			<div id="tabschild15-1">
					<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" class="back_hechos">
						<tr valign="top">
							<td valign="top">
								<table width="25%" cellpadding="0" cellspacing="0" id="tblVehiculo">
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
									<td width="50px">&nbsp;</td>
										<td><a id="nuevoVehiculo"><input type="button" value="Ingresar Veh&iacute;culo" class="ui-button ui-corner-all ui-widget"/></a></td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											<div id="divGridVehiculos">
												<table id="GridVehiculos" ></table>
													<div id="pagerGridVehiculos"></div>
											</div>
										</td>
									</tr>									
								</table>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="tabschild15-2">
					<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" class="back_hechos">
						<tr valign="top">
							<td valign="top">
								<table width="25%" cellpadding="0" cellspacing="0" id="tblAeronave">
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td><input type="button" id="nuevaAeronave" value="Ingreso nuevo" class="ui-button ui-corner-all ui-widget"/></td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											<div id="divGridAeronaves">
												<table id="GridAeronaves" ></table>
													<div id="pagerGridAeronaves"></div>
											</div>
										</td>
									</tr>									
								</table>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="tabschild15-3">
					<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" class="back_hechos">
						<tr valign="top">
							<td valign="top">
								<table width="25%" cellpadding="0" cellspacing="0" id="tblEmbarcacion">
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td><input type="button" id="nuevaEmbarcacion" value="Ingreso nuevo" class="ui-button ui-corner-all ui-widget"/></td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											<div id="divGridEmbarcaciones">
												<table id="GridEmbarcaciones" ></table>
													<div id="pagerGridEmbarcaciones"></div>
											</div>
										</td>
									</tr>									
								</table>
							</td>
						</tr>
					</table>
				</div>
				<!--TERMINAN TABS INFERIORES DE MEDIOS DE TRANSPORTE-->
				
				<!--COMIENZAN TABS INFERIORES DE ESTUPEFACIENTES-->
				<div id="tabschild15-4">
					<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" class="back_hechos">
						<tr valign="top">
							<td valign="top">
								<table width="25%" cellpadding="0" cellspacing="0" id="tblEstupefacientes">
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td><input type="button" id="nuevaSustancia" value="Ingreso nuevo" class="ui-button ui-corner-all ui-widget"/></td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											<div id="divGridSustancias">
												<table id="GridSustancias" ></table>
													<div id="pagerGridSustancias"></div>
											</div>
										</td>
									</tr>									
								</table>
							</td>
						</tr>
					</table>
				</div>
				<!--TERMINAN TABS INFERIORES DE ESTUPEFACIENTES-->

				<!--COMIENZAN TABS INFERIORES DE ARMAS-->
				<div id="tabschild15-5">
					<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" class="back_hechos">
						<tr valign="top">
							<td valign="top">
								<table width="25%" cellpadding="0" cellspacing="0" id="tblArma">
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td><input type="button" id="nuevaArma" value="Ingreso nuevo" class="ui-button ui-corner-all ui-widget"/></td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											<div id="divGridArmas">
												<table id="GridArmas" ></table>
													<div id="pagerGridArmas"></div>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="tabschild15-6">
					<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" class="back_hechos">
						<tr valign="top">
							<td valign="top">
								<table width="25%" cellpadding="0" cellspacing="0" id="tblExplosivo">
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td><input type="button" id="nuevoExplosivo" value="Ingreso nuevo" class="ui-button ui-corner-all ui-widget"/></td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											<div id="divGridExplosivos">
												<table id="GridExplosivos" ></table>
													<div id="pagerGridExplosivos"></div>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<!--TERMINAN TABS INFERIORES DE ARMAS-->
				
				<!--COMIENZAN TABS INFERIORES DE NUMERARIO-->
				<div id="tabschild15-7">
					<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" class="back_hechos">
						<tr valign="top">
							<td valign="top">
								<table width="25%" cellpadding="0" cellspacing="0" id="tblNumerario">
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td><input type="button" id="nuevoNumerario" value="Ingreso nuevo" class="ui-button ui-corner-all ui-widget"/></td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											<div id="divGridNumerarios">
												<table id="GridNumerarios" ></table>
													<div id="pagerGridNumerarios"></div>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<!--TERMINAN TABS INFERIORES DE NUMERARIO-->
				
				<!--COMIENZAN TABS INFERIORES DE OTRO-->
				<div id="tabschild15-8">
					<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" class="back_hechos">
						<tr valign="top">
							<td valign="top">
								<table width="25%" cellpadding="0" cellspacing="0" id="tblOtro">
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td><input type="button" id="nuevoOtros" value="Ingreso nuevo" class="ui-button ui-corner-all ui-widget"/></td>
									</tr>
									<tr>
										<td width="50px">&nbsp;</td>
										<td>
											<div id="divGridOtros">
												<table id="gridObjsOtros" width="600px"></table>
												<div id="pagerGridObjsOtros"></div>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<!--TERMINAN TABS INFERIORES DE OTRO-->
		
			</div>			
		</div>
		<!--TERMINAN TABS INFERIORES DE ASEGURAMIENTO-->
		
		<!--COMIENZAN TABS INFERIORES DE DOCUMENTOS DE APOYO-->
		<div id="tabsconsultaprincipal-7">
		<table width="1150"  height="530" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td width="250" align="center" valign="top">
	                        <table id="gridDocumentosDigitalesPropios"></table>
	                        <div id="pagerGridDocumentosDigitalesPropios"></div>
		                </td>
		                <td width="900" align="center" valign="top">
		               
		                
		               	  <iframe id='visorDocsPropiosFrame' width="900" height="500" src="" scrolling="auto" style="display: ;">		               	  
		               	  </iframe>  
		                </td>
		              </tr>
		            </table>
		</div>
		<!--TERMINAN TABS INFERIORES DE DOCUMENTOS DE APOYO-->
		
		<!--COMIENZAN TABS INFERIORES DE OBSERVACIONES GENERALES-->
		<div id="tabsconsultaprincipal-8">
	    	<table width="1042px"  height="490px" border="0" cellspacing="0" cellpadding="0" id="tableHecho" class="back_hechos">
	    		<tr>
		    		<td align="top"><textarea class="jquery_ckeditor" cols="150" id="editor1" name="editor1" rows="10"></textarea></td>
		    	</tr>
		    </table>
		</div>
		<!--TERMINAN TABS INFERIORES DE OBSERVACIONES GENERALES-->
		
		<!--COMIENZAN TABS INFERIORES DE CADENAS DE CUSTODIA-->
		<div id="tabsconsultaprincipal-9" class="tabTabsCadCus">
				 <input type="button" class="btn_grande" id="btnCadCusNuevaCadCus" style="width: 250px;" value="Crear nueva cadena de custodia"/><br/><br/>
				 <input type="button" class="btn_grande" id="btnCadCusConsultaCadCus" style="width: 250px;" value="Consultar cadena de custodia"/><br/><br/>    
  				 <input type="button" class="btn_grande" id="btnCadCusRegEslabones" style="width: 250px;" value="Registrar eslabones"/> <br/><br/>
  				 <input type="button" class="btn_grande" id="btnCadCusRepEvidencias" style="width: 250px;" style="width: 250px;" value="Reporte de evidencias"/> <br/><br/>
  				 <input type="button" class="btn_grande" id="btnCadCusElabOficio" style="width: 250px;" value="Elaborar oficio para fijaci&oacute;n y preservaci&oacute;n"/><br/><br/>  
   				 <input type="button" class="btn_grande" id="btnCadCusAdmDestino" style="width: 250px;" value="Administrar destino legal de evidencia"/>
		</div>	
		<!--TERMINAN TABS INFERIORES DE CADENAS DE CUSTODIA-->
				
	</div>
	<!--<div id="dialog-detenido-informe" title="Aviso">
		<p align="center">
			<span id="logout">&iquest;Existe alg&uacuten detenido en el informe?</span>
		</p>
	</div>-->

</body>
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
		height:'300px',
		width:'900px'
	};			
	$('.jquery_ckeditor').ckeditor(config);

</script>
</html>
