<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@ page
	import="mx.gob.segob.nsjp.comun.enums.expediente.TipoDeBusquedaDeExpediente"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas"%>
<%@ page
	import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	UsuarioDTO usuario = (UsuarioDTO) request.getSession()
			.getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	String rolUsuarioSesion = "";
	Long idRolActivo = 0L;
	Long confInstitucionId = 0L;

	if (usuario != null && usuario.getRolActivo() != null) {
		rolUsuarioSesion = usuario.getRolActivo();
		idRolActivo = usuario.getRolACtivo().getRol().getRolId();
	}
	//Verificamos cual es la institucion actual traida de session
	if (usuario != null && usuario.getInstitucion() != null
			&& usuario.getInstitucion().getConfInstitucionId() != null) {
		confInstitucionId = usuario.getInstitucion()
				.getConfInstitucionId();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Buscar Expediente</title>
<style>
input.error {
	color: red;
	background-color: #FFFFD5;
	border: 2px solid red;
	margin: 0;
}
</style>
</head>


<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" 	src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>


<script type="text/javascript">

	var reloadGridExpediente = false;
	var reloadGridExpedientePJ = false;
    var validaNombre = false;
    var datosCorrectos = false;
    var datosCorrectos = false;
    var idWindowNuevaDenuncia=1;
    var opcionNoPenal="";

    var tipoOrigen= '<%=request.getParameter("tipo") != null ? request
					.getParameter("tipo") : ""%>';
    var opcion=<%=request.getAttribute("opcion")%>;

    var rolUsuario= "";
    var rolUserSesion="<%=rolUsuarioSesion%>";
	var idRolActivo = <%=idRolActivo%>;
	//variables para setear las fechas y horas maximas
	var fechaServidor="";
	var fechaMax="";
	
	
	
	var ATPENAL = 1;
	var COORDINADOR_JAR = 2;
	var AGENTE_MP = 3;
	var COORDINADOR_AMP = 4;
	var FACILITADOR = 5;
	var POLICIA_MINISTERIAL = 6;
	var COORDINADOR_VISITADOR = 7;
	var VISITADOR = 8;
	var COORDINADOR_AMP_GENERAL = 10;
	var POLICIA_MINISTERIAL_DENUNCIA=60;
	var COORDINADOR_AT=61;
	var SISTEMA_TRADICIONAL = 9;
	var DEFENSOR_ATE = '<%=Roles.DEFENSORATE.getValorId()%>';
	var COORDINADOR_DEF = '<%=Roles.COORDINADORDEF.getValorId()%>';
	var ADMINAT = '<%=Roles.ADMINAT.getValorId()%>';
	// variables para roles de PJ
	var ENC_SALA='<%=Roles.ENCARGADOSALA.getValorId()%>';
	var JUEZ='<%=Roles.JUEZPJ.getValorId()%>';
	var OFIC_PARTES='<%=Roles.ATENCIONPUBLICO.getValorId()%>';
	
	
	var contextoPagina = "${pageContext.request.contextPath}";
	
	var confInstitucionId = <%=confInstitucionId%>;
	
	$(document).ready(function() {
		
		configurarLosTiposBusqueda();
		
		//Oculta las secciones de busqueda
		$('div.seccionDeBusqueda').hide();
		//Eventos
		$("#buscarExpediente").bind("click",validaCampos);
		//Carga combo de delitos
		cargaComboDelito();
		//vamos por la fecha actual al servidor
		fechaServidor= consultaFechaHoraMaximaServer();
		fechaMax=getFechaMaximaServer(fechaServidor);
		
		$("#idFechaDateLapso").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '-111:+0',
			maxDate: fechaMax,
			onSelect: function(date) {
				//setter fecha minima al segunda calendario
				$( "#idFechaDateLapso2" ).datepicker( "option", "minDate", date );
			},
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%=request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		$("#idFechaDateLapso2").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '-111:+0',
			maxDate: fechaMax, 
			minDate: fechaMax,
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%=request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		$("#idFechaDateLapso").attr('disabled','disabled');
		$("#idFechaDateLapso2").attr('disabled','disabled');
		
		
		$("#cmbTipoBusqueda").multiselect({ 
			multiple: false, 
			header: "Seleccione", 
			position: { 
				my: 'top', 
				at: 'top' 
			},
			selectedList: 1, 
			close: function (event,ui){
				onSelectChangeTipo();}
		});
		$("#catDelito").multiselect({ 
			multiple: false, 
			header: "Seleccione", 
			position: { 
				my: 'top', 
				at: 'top' 
			},
			minWidth: 400,
			selectedList: 1, 
			close: function (event,ui){
				onSelectChangeTipoDel();}
		});
		
		
		//$("#idFechaDateLapso,#idFechaDateLapso2").val(fechaMax);
		
		$("#noExpediente").bind("keyup blur" , function () {
			validarCampo(/[^0-9a-zA-Z/-]/g , $(this).val()) == true ?
			$(this).removeClass("error") :  $(this).addClass("error");
		});
		
		$("#noExpedienteATP").bind("keyup blur" , function () {
			validarCampo(/[^0-9a-zA-Z/-]/g , $(this).val()) == true ?
			$(this).removeClass("error") :  $(this).addClass("error");
		});
		//Llamada a la funcion para cambiar las etiquetas que digan expediente por causa en el caso de ser PJ
		cambiarEtiquetasExpedienteaNoCausaPJ();
		
	});
	
	/*
	* Funcion para llamar la funcion de habilitar los elementos de la pantalla
	*/
	function onSelectChangeTipo() {
	  	var selected = $("#cmbTipoBusqueda option:selected");
	  	limpiaCampos();
		enableControls(selected.val());
	}
	
	/*
	* Funcion que habilita o deshabilita los elementos de la pagina dependiento del tipo 
	* seleccionado
	*/
	function enableControls(tipo){
		$("div.seccionDeBusqueda").hide();
		idSeccion = "#seccionDeBusqueda" + tipo;
		$(idSeccion).show();
	}
	
	
	function limpiaCampos(){		
		$("#nombre").val("");
		$("#apellido").val("");
		$("#apellidoMat").val("");		
		$("#noExpediente").val("");
		$("#noExpedienteATP").val("");
		$("#noCaso").val("");
		$("#idFechaDateLapso,#idFechaDateLapso2").val(fechaMax);
		$("#nombreOrg").val("");
	}
	
	function tituloVentana(num){
		$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Expediente: "+num);
	}

	//Llena el grid con los resultados de la busqueda, pasa como parametros el No de expediente
	function llenaGridExpediente(){
		  var tipoBusqueda=$("#cmbTipoBusqueda option:selected").val();// para verificar la opcion de agentemp
		  
		  var params;
	      params="tipoBusqueda=" + tipoBusqueda;      
	      
	      switch(tipoBusqueda){
	      	case '<%=TipoDeBusquedaDeExpediente.POR_DELITO.getTipoBusquedaId()%>':
	      		params+="&catdelitId=" + $('#catDelito').val();
	      	    break;
	      	case '<%=TipoDeBusquedaDeExpediente.POR_NUM_EXP.getTipoBusquedaId()%>':
	  	        params+="&numeroExpediente=" + $("#noExpediente").val();
	      	    break;
	      	case '<%=TipoDeBusquedaDeExpediente.POR_NUM_CASO.getTipoBusquedaId()%>':
	  	        params+="&numeroCaso=" + $("#noCaso").val();
	      	    break;
	      	case '<%=TipoDeBusquedaDeExpediente.POR_FECHAS.getTipoBusquedaId()%>':
	      		params+="&fechaIni=" + $("#idFechaDateLapso").val();
	      		params+="&fechaFin=" + $("#idFechaDateLapso2").val();
	      	    break;
	      	case '<%=TipoDeBusquedaDeExpediente.POR_ATP.getTipoBusquedaId()%>':
	  	        params+="&numeroExpediente=" + $("#noExpedienteATP").val();
	      	    break;
	      	case '<%=TipoDeBusquedaDeExpediente.POR_NOMBRE_PERSONA.getTipoBusquedaId()%>':
	      		params+="&nombre=" + $('#nombre').val();
	      		params+="&apPaterno=" + $('#apellido').val();
	      		params+="&apMaterno=" + $('#apellidoMat').val();
	      	    break;
	      	case '<%=TipoDeBusquedaDeExpediente.POR_NOMBRE_ORGANIZACION.getTipoBusquedaId()%>':
	      		params+="&nombreOrganizacion=" + $("#nombreOrg").val();
	      	    break;
	      	//Empiezan casos para PJ
	      	case '<%=TipoDeBusquedaDeExpediente.POR_NUM_EXP_PJ.getTipoBusquedaId()%>':
	      		params+="&numeroExpediente=" + $("#noExpediente").val();
	      	    break;
	      	case '<%=TipoDeBusquedaDeExpediente.POR_NOMBRE_PERSONA_PJ.getTipoBusquedaId()%>':
	      		params+="&nombre=" + $('#nombre').val();
	      		params+="&apPaterno=" + $('#apellido').val();
	      		params+="&apMaterno=" + $('#apellidoMat').val();
	      	    break;
	      	
	      }
		  //En base a la institucion se manda a crear el Grid de Buscar Expedientes
	      if(confInstitucionId == '<%=Instituciones.PJ.getValorId()%>'){
	    	  crearGridExpedienteParaPJ(tipoBusqueda,params);
	      }else{
	    	  crearGridExpedienteParaPG(tipoBusqueda,params);
	      }
	      			
			//Mensaje en caso de no obtener resultados	  			   
 			var ids = jQuery("#tablaBuscarExpedientes").jqGrid('getDataIDs');
 			if(parseInt(ids.length) == 0){
 					//customAlert("No se encontraron expedientes que cumplan con los criterios especificados")
 			}
 	}
	
	//Funcion que valida si los campos estan llenos al enviar 
		function validaCampos(){
			var tipoBusqueda=$("#cmbTipoBusqueda option:selected").val();
			
			switch(tipoBusqueda){
		      	case '<%=TipoDeBusquedaDeExpediente.POR_DELITO.getTipoBusquedaId()%>':
		      		validaCamposDelito();
					break;  	
		      	case '<%=TipoDeBusquedaDeExpediente.POR_NUM_EXP.getTipoBusquedaId()%>':
		      		validaCamposExpediente();
					break;  	
		      	case '<%=TipoDeBusquedaDeExpediente.POR_NUM_CASO.getTipoBusquedaId()%>':
		      		validaCamposCaso();
		      	    break;
		      	case '<%=TipoDeBusquedaDeExpediente.POR_FECHAS.getTipoBusquedaId()%>':
		      		validaCamposFecha();
		      	    break;
		      	case '<%=TipoDeBusquedaDeExpediente.POR_ATP.getTipoBusquedaId()%>':
		      		validaCamposExpedienteATP();
		      	    break;
		      	case '<%=TipoDeBusquedaDeExpediente.POR_NOMBRE_PERSONA.getTipoBusquedaId()%>':
		      		validaCamposNombre();
		      	    break;
		      	case '<%=TipoDeBusquedaDeExpediente.POR_NOMBRE_ORGANIZACION.getTipoBusquedaId()%>':
		      		validaCamposOrganizacion();
		      	    break; 
		      	    //Empiezan casos para PJ
		      	case '<%=TipoDeBusquedaDeExpediente.POR_NUM_EXP_PJ.getTipoBusquedaId()%>':
		      		validaCamposExpediente();
					break;  
		      	case '<%=TipoDeBusquedaDeExpediente.POR_NOMBRE_PERSONA_PJ.getTipoBusquedaId()%>':
		      		validaCamposNombre();
		      	    break;
		  }
					
			if(datosCorrectos == true){
	      		llenaGridExpediente();
			}
		}	
	
		//Funcion que valida si los campos estan llenos al enviar 
		function validaCamposExpediente(){
			
			if( validarCampo(/[^0-9a-zA-Z/-]/g , $("#noExpediente").val()) == false ){
				customAlert("Favor de revisar el n&uacute;mero de expediente");
				//Pone el foco en el primer input con clase de error
				$(":input.error").first().focus();
				datosCorrectos=false;
				
			}else{
				if ($('#noExpediente').val()==''){
					customAlert("Favor de ingresar un n&uacute;mero de expediente");
					datosCorrectos=false;
				}else {
					datosCorrectos=true;
				}
			}
												
		}
		
		//Funcion que valida si los campos estan llenos al enviar 
		function validaCamposOrganizacion(){
			if ($('#nombreOrg').val()==''){
				if($('#nombreOrg').val()==''){
					customAlert("Favor de ingresar un nombre de Organanizaci&oacute;n");
					datosCorrectos=false;
				}
				}else {
					datosCorrectos=true;
				}						
		}		
	
		//Funcion que valida si los campos estan llenos al enviar 
		function validaCamposCaso(){
			if (trim($('#noCaso').val())==''){
					customAlert("Favor de ingresar un número de caso");
					datosCorrectos=false;
			}else {
				datosCorrectos=true;
			}							
		}		
		
		//Funcion que valida si los campos estan llenos al enviar 
		function validaCamposNombre(){
			if (trim($('#nombre').val())=='' && trim($('#apellido').val())=='' && trim($('#apellidoMat').val())==''){
					customAlert("Favor de ingresar un Nombre o un Apellido");
					datosCorrectos=false;
			}
			else{ 
				datosCorrectos=true;
			}
		}
		
		//Funcion que valida si los campos estan llenos al enviar 
		function validaCamposFecha(){
			if (trim($('#idFechaDateLapso').val())==''|| trim($('#idFechaDateLapso2').val())==''){
				customAlert("Debes ingresar la fecha de inicio y fin");
				datosCorrectos=false;
			}else {
				datosCorrectos=true;
			}						
		}
		
		//Funcion que valida si los campos estan llenos al enviar 
		function validaCamposDelito(){
			if ($('#catDelito').val()==''){
				customAlert("Se debe seleccionar un delito");
				datosCorrectos=false;
			}else {
				datosCorrectos=true;
			}						
		}		
		
		//Funcion que carga los combos de tipo de delito
		function cargaComboDelito() {
	         $.ajax({
	 			type: 'POST',
	 			url: '<%=request.getContextPath()%>/consultarCatalogoDelitos.do',
	 			data: '',
	 			dataType: 'xml',
	 			async: false,
	 			success: function(xml){
	 				$(xml).find('delitos').each(function(){
	 					$('#catDelito').append('<option value="' + $(this).find('catDelitoId').text() + '">'+ $(this).find('nombre').last().text() + '</option>');
	 				});
	 			}
	 		});
		}
		
		function validaCamposExpedienteATP(){
			if( validarCampo(/[^0-9a-zA-Z/-]/g , $("#noExpedienteATP").val()) == false ){
				customAlert("Favor de revisar el n&uacute;mero de expediente");
				//Pone el foco en el primer input con clase de error
				$(":input.error").first().focus();
				datosCorrectos=false;
				
			}else{
				if ($('#noExpedienteATP').val()==''){
					customAlert("Favor de ingresar un n&uacute;mero de expediente");
					datosCorrectos=false;
				}else {
					datosCorrectos=true;
				}
			}
		}
		
		
		/*
		*Funcion que habilita o deshabilita los elementos de la pagina
		*/
		function enableControlsDelito(tipo) 
		{
			if(parseInt(tipo)==-1){
			   $('#buscarDelito').attr('disabled', 'disabled');
			}
			else
			{
			    $('#buscarDelito').attr('disabled', '');
			}
		}
		
		
		/*
		* Funcion para llamar la funcion de habilitar los elementos de la pantalla
		*/
		function onSelectChangeTipoDel() {
		  	var selectedDel = $("#catDelito option:selected");		
			enableControlsDelito(selectedDel.val());
		}
		
		function abrirVisorExpediente(idNumeroExpediente,idExpediente, numeroExpediente, idArea) {
			var pantallaSolicitada;
			
			switch(idRolActivo){
	      	case <%=Roles.ATPENAL.getValorId()%>:
	      		pantallaSolicitada = ATPENAL;
	      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
				break;  	
	      	case <%=Roles.COORDINADORJAR.getValorId()%>:
	      		var numeroExpedienteId = nuevoNumeroExpediente(idExpediente,<%=Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.ordinal()%>);
	      		pantallaSolicitada = COORDINADOR_JAR;
	      		consultaExpediente(numeroExpediente, numeroExpedienteId, pantallaSolicitada);
				break;  	
	      	case <%=Roles.AGENTEMP.getValorId()%>:
	      		pantallaSolicitada = AGENTE_MP;
	      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      	    break;
	      	case <%=Roles.COORDINADORAMP.getValorId()%>:
	      		var numeroExpedienteId = nuevoNumeroExpediente(idExpediente,<%=Areas.UNIDAD_INVESTIGACION.ordinal()%>);
	      		pantallaSolicitada = COORDINADOR_AMP;
	      		consultaExpediente(numeroExpediente, numeroExpedienteId, pantallaSolicitada);
	      	    break;
	      	case <%=Roles.FACILITADOR.getValorId()%>:
	      		pantallaSolicitada = FACILITADOR;
	      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      	    break;
	      	case <%=Roles.POLICIAMINISTER.getValorId()%>:
	      		pantallaSolicitada = POLICIA_MINISTERIAL;
	      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      	    break;
	      	case <%=Roles.COORDINADORVIS.getValorId()%>:
	      		pantallaSolicitada = COORDINADOR_VISITADOR;
	      		switch(idArea){
	      			case <%=Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong()%>://7
	    	      		pantallaSolicitada = FACILITADOR;
			      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
			      	    break;
	      			case <%=Areas.UNIDAD_INVESTIGACION.parseLong()%>://10
	      				pantallaSolicitada = COORDINADOR_AMP;
			      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
			      	    break;
	      			case <%=Areas.COORDINACION_ATENCION_VICTIMAS.parseLong()%>://12 (PENDIENTE)
			      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
			      	    break;
	      			case <%=Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong()%>://15
	    	      		pantallaSolicitada = POLICIA_MINISTERIAL;
			      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
			      	    break;
	      			case <%=Areas.ATENCION_TEMPRANA_PG_PENAL.parseLong()%>://44
	    	      		pantallaSolicitada = ATPENAL;
			      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
			      	    break;
	      			case <%=Areas.ATENCION_TEMPRANA_PG_NO_PENAL.parseLong()%>://45
		      			consultaExpedienteDeAtencionTemAdm(numeroExpediente, idNumeroExpediente);
		      	    	break;
	      			case <%=Areas.AGENCIA_DEL_MINISTERIO_PUBLICO.parseLong()%>://59
	    	      		pantallaSolicitada = SISTEMA_TRADICIONAL;
		      		    consultaExpedienteDeSistTradicional(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
			      	    break;
			      	default:
			      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      		}
	      	    break;
	      	case <%=Roles.VISITADOR.getValorId()%>:
	      		pantallaSolicitada = VISITADOR;
	      		switch(idArea){
	      			case <%=Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong()%>://7
	    	      		pantallaSolicitada = FACILITADOR;
			      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
			      	    break;
	      			case <%=Areas.UNIDAD_INVESTIGACION.parseLong()%>://10
	      				pantallaSolicitada = AGENTE_MP;
			      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
			      	    break;
	      			case <%=Areas.COORDINACION_ATENCION_VICTIMAS.parseLong()%>://12 (PENDIENTE)
			      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
			      	    break;
	      			case <%=Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong()%>://15
	    	      		pantallaSolicitada = POLICIA_MINISTERIAL;
			      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
			      	    break;
	      			case <%=Areas.ATENCION_TEMPRANA_PG_PENAL.parseLong()%>://44
	    	      		pantallaSolicitada = ATPENAL;
			      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
			      	    break;
	      			case <%=Areas.ATENCION_TEMPRANA_PG_NO_PENAL.parseLong()%>://45
		      			consultaExpedienteDeAtencionTemAdm(numeroExpediente, idNumeroExpediente);
		      	    	break;
	      			case <%=Areas.AGENCIA_DEL_MINISTERIO_PUBLICO.parseLong()%>://59
	    	      		pantallaSolicitada = SISTEMA_TRADICIONAL;
		      		    consultaExpedienteDeSistTradicional(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
			      	    break;
			      	default:
			      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      		}
	      	    break;
	      	case <%=Roles.COORDINADORAMPGENERAL.getValorId()%>:
	      		pantallaSolicitada = COORDINADOR_AMP_GENERAL;
	      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      	    break;
	      	case <%=Roles.COORDINADORAT.getValorId()%>:
	      		pantallaSolicitada = COORDINADOR_AT;
	      		if(idArea == '<%=Areas.ATENCION_TEMPRANA_PG_NO_PENAL.parseLong()%>'){//Admnistrativa
	      			consultaExpedienteDeAtencionTemAdm(numeroExpediente, idNumeroExpediente);
	      		}else{//44 Atencion temprana penal
		      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada, idArea);
	      		}	      		
	      	    break;
	      	case <%=Roles.ADMINAT.getValorId()%>:
	      		consultaExpedienteDeAtencionTemAdm(numeroExpediente, idNumeroExpediente);
	      	    break;
	      	case <%=Roles.AGENTEMPSISTRAD.getValorId()%>:
	      		pantallaSolicitada = SISTEMA_TRADICIONAL;
	      		consultaExpedienteDeSistTradicional(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      	    break;
	      	case <%=Roles.COORDINADOR_CONSIGNACION.getValorId()%>:
	      		pantallaSolicitada = SISTEMA_TRADICIONAL;
	      		consultaExpedienteDeSistTradicional(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      	    break;
	      	case <%=Roles.CONSIGNADOR.getValorId()%>:
	      		pantallaSolicitada = SISTEMA_TRADICIONAL;
	      		consultaExpedienteDeSistTradicional(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      	    break;
	      	case <%=Roles.PROCURADOR.getValorId()%>:
	      		pantallaSolicitada = COORDINADOR_AMP_GENERAL;
	      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      	    break;
	       	case <%=Roles.SUBPROCURADOR.getValorId()%>:
	      		pantallaSolicitada = COORDINADOR_AMP_GENERAL;
	      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      	    break;
	       	case <%=Roles.DIRECTOR_GENERAL.getValorId()%>:
	      		pantallaSolicitada = COORDINADOR_AMP_GENERAL;
	      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      	    break;
	       	case <%=Roles.DIRECTOR_UI.getValorId()%>:
	      		pantallaSolicitada = COORDINADOR_AMP_GENERAL;
	      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      	    break;
	      }
		}

		
		/**
		* Permite consultar un expediente del sistema tradicional
		**/
		function consultaExpedienteDeSistTradicional(numeroExpediente, idNumeroExpediente, pantallaSolicitada) {
  	    	   var ingresoDenuncia = true;
			   idWindowNuevaDenuncia++;
				$.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:$(document).width(),height:$(document).height(),title:"Expediente: ", type:"iframe"});
				$.maximizeWindow("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia);
				$.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%=request.getContextPath()%>/BusquedaExpedienteSistTrad.do?abreenPenal=abrPenal&ingresoDenuncia='+ingresoDenuncia +'&idNumeroExpediente='+idNumeroExpediente+'&pantallaSolicitada='+pantallaSolicitada+'" width="100%" height="100%" />');
				$("#" +"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+ " .window-maximizeButton").click();
		}
		
		/**
		* Permite consultar un expediente
		**/
		function consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada, idArea) {
			   idWindowNuevaDenuncia++;
			   var ingresoDenuncia = true;
			   var idIframe = "iframewindowCarpInvNuevaDenuncia" + idWindowNuevaDenuncia;
			   $.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:1430,height:670,title:"Expediente: ", type:"iframe"});
			   $.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%=request.getContextPath()%>/BusquedaExpediente.do?abreenPenal=abrPenal&ingresoDenuncia='+ingresoDenuncia +'&idNumeroExpediente='+idNumeroExpediente+'&pantallaSolicitada='+pantallaSolicitada+'&idIframe='+idIframe+'&idArea='+idArea+'" width="1430" height="670" />');
		  	   $("#" +"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+ " .window-maximizeButton").click();
		}
		
		/**
		* Permite consultar un expediente de Visitaduria
		**/
		function consultaExpedienteVisitaduria(numeroExpediente, idNumeroExpediente, pantallaSolicitada, idArea) {
			   idWindowNuevaDenuncia++;
			   var ingresoDenuncia = true;
			   var idIframe = "iframewindowCarpInvNuevaDenuncia" + idWindowNuevaDenuncia;
			   $.newWindow({id:"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, statusBar: true, posx:0,posy:0,width:$(document).width(),height:$(document).height(),title:"Expediente: ", type:"iframe"});
			   $.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%=request.getContextPath()%>/BusquedaExpedienteSistTrad.do?abreenPenal=abrPenal&ingresoDenuncia='+ingresoDenuncia +'&idNumeroExpediente='+idNumeroExpediente+'&pantallaSolicitada='+pantallaSolicitada+'&idIframe='+idIframe+'&esModuloConsultaDeExpedientes=true'+'&idArea='+idArea+'" width="100%" height="100%" />');
			   $("#" +"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+ " .window-maximizeButton").click();	
		}
		
		
		/**
		* Permite consultar un expediente de atención temprana administrativa
		**/
		function consultaExpedienteDeAtencionTemAdm(numeroExpediente, idNumeroExpediente) {
			$.newWindow({id:"iframewindowRegistraDatosPersona", statusBar: true, posx:200,posy:50,width:1140,height:450,title:"Expediente:  "+numeroExpediente, type:"iframe"});
		    $.updateWindowContent("iframewindowRegistraDatosPersona",'<iframe src="<%=request.getContextPath()%>/BusquedaExpedienteAdminAt.do?formaId=2&numeroExpedienteTempAdmin='+ numeroExpediente +'&idExpedienteTempAdmin='+idNumeroExpediente+'&operacion=CONSULTA " width="1140" height="450" />');
		    $("#" +"iframewindowRegistraDatosPersona .window-maximizeButton").click();	
		}
		
		
		/*
		 * Funcion para regresar la fecha maxima obtenida desde el servidor
		 * fechaCompleta - cadena con el siguiente formato : YYYY-MM-DD HH:MI:SS
		 * regresa una cadena con la fecha en el formato : DD/MM/YYYY
		 */
		function getFechaMaximaServer(fechaCompleta){
			var arrFechaHora=fechaCompleta.split(" ");
			var digitosFecha=arrFechaHora[0].split("-");
			return digitosFecha[2]+'/'+digitosFecha[1]+'/'+digitosFecha[0];
		}
		
		/*
		*Funcion para traer la fecha y hora del servidor en el formato : YYYY-MM-DD HH:MI:SS
		*/
		function consultaFechaHoraMaximaServer(){
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

		//********************************FUNCIONES PARA ADAPTAR EL BUSCADOR A DEFENSORIA**************************************************/

		/*
		*Funcion para llenar el combobox de los tipos de busqueda
		*/
		function configurarLosTiposBusqueda(){

			$('#cmbTipoBusqueda').empty();
			$('#cmbTipoBusqueda').append('<option value="-1">-Seleccione-</option>');
			//Para cargar el combobox cuando la Institucion es PJ
			if(confInstitucionId == <%=Instituciones.PJ.getValorId()%>){
				//Se agrega solo la busqueda por Persona y No. de causa(Expediente) para estos roles
				if(idRolActivo==ENC_SALA || idRolActivo==JUEZ || idRolActivo==OFIC_PARTES){ 
					var valuesOptionsTiposBusqueda = [
					              			  "<%=TipoDeBusquedaDeExpediente.POR_NUM_EXP_PJ.getTipoBusquedaId()%>",
					              			  "<%=TipoDeBusquedaDeExpediente.POR_NOMBRE_PERSONA_PJ.getTipoBusquedaId()%>"
					              			    ];
	
					var nombresOptionsTiposBusqueda = [
							 					"Por n&uacute;mero de Causa",
					          					"Por nombre de Persona"
					           				];
				}
			}else{ 
			
				var valuesOptionsTiposBusqueda = [
				        "<%=TipoDeBusquedaDeExpediente.POR_NUM_EXP.getTipoBusquedaId()%>",
				        "<%=TipoDeBusquedaDeExpediente.POR_NOMBRE_PERSONA.getTipoBusquedaId()%>",
				        "<%=TipoDeBusquedaDeExpediente.POR_FECHAS.getTipoBusquedaId()%>"
					];
	
				var nombresOptionsTiposBusqueda = [
						"Por n&uacute;mero de Expediente",
						"Por nombre del involucrado",
						"Por fecha"
					];
				
				if(idRolActivo != DEFENSOR_ATE && idRolActivo != COORDINADOR_DEF && idRolActivo != ADMINAT){
					
					valuesOptionsTiposBusqueda.push("<%=TipoDeBusquedaDeExpediente.POR_NOMBRE_ORGANIZACION
					.getTipoBusquedaId()%>");
					valuesOptionsTiposBusqueda.push("<%=TipoDeBusquedaDeExpediente.POR_ATP.getTipoBusquedaId()%>");
	
					nombresOptionsTiposBusqueda.push("Por nombre de organizaci&oacute;n");
					nombresOptionsTiposBusqueda.push("Por n&uacute;mero de Expediente en ATP");
				}
				
				if(idRolActivo != ADMINAT){				
					valuesOptionsTiposBusqueda.push("<%=TipoDeBusquedaDeExpediente.POR_NUM_CASO
					.getTipoBusquedaId()%>");
					valuesOptionsTiposBusqueda.push("<%=TipoDeBusquedaDeExpediente.POR_DELITO.getTipoBusquedaId()%>");
	
					nombresOptionsTiposBusqueda.push("Por n&uacute;mero de Caso");
					nombresOptionsTiposBusqueda.push("Por delito");
				}
			
			}
			
			for (indice=0; indice < valuesOptionsTiposBusqueda.length; indice++){
					$('#cmbTipoBusqueda').append('<option value="' +valuesOptionsTiposBusqueda[indice]+ '">' + nombresOptionsTiposBusqueda[indice] + '</option>');
			}
		}
		
		/*
		* Funcion para configurar como se muestran las columnas del gridBuscarExpedientes 
		* cuando la institucion es PG, en base a el RolActivo y al Tipo de Busqueda 
		*/
		function configurarColumnasGridBuscarExpedientes(tipoBusqueda){

			//Se oculta la columna de caso dado que algunos roles no generan numero de caso
 			if(idRolActivo == '<%=Roles.AGENTEMPSISTRAD.getValorId()%>' || 
			     idRolActivo == '<%=Roles.COORDINADOR_CONSIGNACION.getValorId()%>' || 
				 idRolActivo == '<%=Roles.CONSIGNADOR.getValorId()%>'){
				jQuery("#tablaBuscarExpedientes").jqGrid('hideCol',["cNumeroGeneralCaso"]);
			}else{
				jQuery("#tablaBuscarExpedientes").jqGrid('showCol',["cNumeroGeneralCaso"]);
			} 		

 			//Se muestran las columnas del nombre involucrado y su calidad para el caso del tipo
			//de busqueda POR_DELITO
			if(tipoBusqueda == '<%=TipoDeBusquedaDeExpediente.POR_NOMBRE_PERSONA.getTipoBusquedaId()%>' 
					|| tipoBusqueda == '<%=TipoDeBusquedaDeExpediente.POR_NOMBRE_ORGANIZACION.getTipoBusquedaId()%>'){
				//Se muestran columnas
				jQuery("#tablaBuscarExpedientes").jqGrid('showCol',["cInvolucrado","cNombreCalidad"]);
			}else{
				jQuery("#tablaBuscarExpedientes").jqGrid('hideCol',["cInvolucrado","cNombreCalidad"]);
			}

			if(idRolActivo == DEFENSOR_ATE || idRolActivo == COORDINADOR_DEF){
				$("#tablaBuscarExpedientes").jqGrid('hideCol',["iClaveFuncionario","cFuncionario","cArea","cEdificio","cEstatus","jerarquiaOrganizacional_id"]);
			}
		
		}
		
		/*
		* Funcion para crear el grid Expediente cuando la institucion es PG
		*/
		function crearGridExpedienteParaPG(tipoBusqueda,params){
			if (reloadGridExpediente) {
				  jQuery("#tablaBuscarExpedientes").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/buscarExpedientesPotTipoConSP.do?'+params,datatype: "xml" });
			       $("#tablaBuscarExpedientes").trigger("reloadGrid");
			  } else {
				  reloadGridExpediente = true;
				  jQuery("#tablaBuscarExpedientes").jqGrid(
							{ 
			                    url:'<%=request.getContextPath()%>/buscarExpedientesPotTipoConSP.do?'+params,									
								datatype: "xml", 
								mtype: 'POST',
								colNames:['Caso ID','No. Caso','ID No. Expediente','No. Expediente','Involucrado',
								          'Calidad','Delito','Principal','Clave Funcionario','Responsable del expediente',
								          '&Aacute;rea','Edificio','Estatus','Consulta','Fecha Apertura','idArea'], 
								colModel:[ 
										            {name:'caso_id', index:'caso_id', hidden:true},
										            {name:'cNumeroGeneralCaso',index:'cNumeroGeneralCaso', width:185,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
										            {name:'expediente_id', index:'expediente_id',hidden:true},
										            {name:'cNumeroExpediente',index:'cNumeroExpediente', width:185,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
										            {name:'cInvolucrado',index:'cInvolucrado', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
										            {name:'cNombreCalidad',index:'cNombreCalidad', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
										            {name:'cDelito',index:'cDelito', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
										            {name:'bEsPrincipal', index:'bEsPrincipal',width:60, hidden:false, align:"center"},
										            {name:'iClaveFuncionario', index:'iClaveFuncionario',hidden:true},
										            {name:'cFuncionario',index:'cFuncionario', width:195,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
													{name:'cArea',index:'cArea', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
													{name:'cEdificio',index:'cEdificio', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
											        {name:'cEstatus',index:'cEstatus', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
										            {name:'bEsConsulta', index:'bEsConsulta', hidden:true},
										            {name:'dfechaApertura', index:'dfechaApertura',width:100, sortable:true, align:"center"},
										            {name:'jerarquiaOrganizacional_id', index:'jerarquiaOrganizacional_id',width:60, sortable:true, hidden:true, align:"center"},
										            
												], 
											autowidth: true, 
											pager: jQuery('#pagerNoExpediente'),
						                    rowNum:10,
						                    rowList:[10,20,30,40,50,60,70,80,90,100],
									sortname: 'dfechaApertura', 
									rownumbers: true,
									gridview: true, 
									viewrecords: true, 
									sortorder: "desc", 
									height: "60%",
									ondblClickRow: function(id){
										var ret = jQuery("#tablaBuscarExpedientes").jqGrid('getRowData',id);
										var esConsulta = ret.bEsConsulta;
										var idNumeroExpediente = id;
										var idExpediente = ret.expediente_id;
										var numeroExpediente = ret.cNumeroExpediente;
										var idArea = ret.jerarquiaOrganizacional_id;
										var idCaso = ret.caso_id;
										
							 			if(idRolActivo != '<%=Roles.PERITOAMP.getValorId()%>'){
							 				if(esConsulta == "1"){
												abrirVisorExpediente(idNumeroExpediente,idExpediente, numeroExpediente, parseInt(idArea));
											}else{//No tiene permisos para visualizar el expediente
												customAlert("Usted no es el dueño del expediente,<br> y no cuenta con permisos para consultarlo.","");
											}	 
							 			 }    											
									},								
									caption:"Resultado de la B&uacute;squeda" 
							});
									  
			 			 }// fin else		
				configurarColumnasGridBuscarExpedientes(tipoBusqueda);	
		}
		
		//Función que genera un nuevo número de expediente para la UI en el mismo expediente
		function nuevoNumeroExpediente(id,idArea){
				
			var idExpediente="0";
			var numeroExpediente="0";
			var numeroExpedienteId="0";
			
			$.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/nuevoNumeroExpediente.do?idArea='+idArea+'&idExpediente='+id,
	    		data: '',
	    		dataType: 'xml',
	    		async: false,
	    		success: function(xml){
	    			idExpediente=$(xml).find('expedienteDTO').find('expedienteId').text();
	    			numeroExpediente=$(xml).find('expedienteDTO').find('numeroExpediente').text();
	    			numeroExpedienteId=$(xml).find('expedienteDTO').find('numeroExpedienteId').text();
	    			/* numExpAlter=$(xml).find('expedienteDTO').find('esNuevo').text();
	    			if(numExpAlter == ""){
	    				numExpAlter=null;
	        		} */
	    		}
	    		
	    	});
			return numeroExpedienteId;
		}
		
/*******************************************        FUNCIONES PARA PJ   *****************************************************/
		
		/*
		* funcion para cambiar las etiquetas que digan expediente por causa en el caso de ser PJ
		*/
		function cambiarEtiquetasExpedienteaNoCausaPJ(){
			if(confInstitucionId == <%=Instituciones.PJ.getValorId()%>){
				//Se cambia la etiqueta de Numero de expediente por Numero de Causa en la seccion de busqueda por numero de expediente 
				$("#etiquetaNumExp").text('<bean:message key="numeroDeCausa"/>');
			}
		}

		/*
		* Funcion para crear el grid Expediente cuando la institucion es PJ
		*/
		function crearGridExpedienteParaPJ(tipoBusqueda,params){
			if (reloadGridExpedientePJ) {
				  jQuery("#tablaBuscarExpedientes").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/buscarExpedientesPotTipoConSP.do?'+params,datatype: "xml" });
			       $("#tablaBuscarExpedientes").trigger("reloadGrid");
			} else {
				reloadGridExpedientePJ = true;
				  jQuery("#tablaBuscarExpedientes").jqGrid(
							{ 
			                    url:'<%=request.getContextPath()%>/buscarExpedientesPotTipoConSP.do?'+params+'',									
								datatype: "xml", 
								mtype: 'POST',
								colNames:['Caso ID','No. Caso','ID No. Expediente','<bean:message key="numeroDeCausa"/>','Nombre','A. Paterno','A. Materno','Calidad','Tribunal o Juzgado','esConsulta'], 
								colModel:[ 
													{name:'caso_id', index:'caso_id', hidden:true},
													{name:'cNumeroGeneralCaso',index:'cNumeroGeneralCaso', width:320,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
										            {name:'expediente_id', index:'expediente_id',hidden:true},
										            {name:'cNumeroExpediente',index:'cNumeroExpediente', width:320,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
													{name:'nombre', index:'nombre',width:150, align:"center", cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
										            {name:'apePaterno', index:'apePaterno',width:150, align:"center", cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
										            {name:'apeMaterno', index:'apeMaterno',width:150, align:"center", cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
										            {name:'cNombreCalidad',index:'cNombreCalidad', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
										            {name:'tribunal', index:'tribunal',width:185,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
										            {name:'bEsConsulta', index:'bEsConsulta', hidden:true}
												], 
											 
									pager: jQuery('#pagerNoExpediente'),
						            rowNum:10,
						            rowList:[10,20,30,40,50,60,70,80,90,100],
									sortname: 'cNumeroExpediente', 
									gridview: true, 
									viewrecords: true, 
									hidegrid: false,
									sortorder: "desc", 
									height: "60%",
									width: 1040,
									ondblClickRow: function(id){
										var ret = jQuery("#tablaBuscarExpedientes").jqGrid('getRowData',id);
										var idNumeroExpediente = id;
										var idExpediente = ret.expediente_id;
										var numeroExpediente = ret.cNumeroExpediente;
										var esConsulta = ret.bEsConsulta;
										if(esConsulta=="1"){
											ventanaAudiencias(idNumeroExpediente,numeroExpediente);
										}else{
											customAlert("Usted no es el dueño del expediente,<br> y no cuenta con permisos para consultarlo.","");
										}
																														
									},
									caption:"Resultado de la B&uacute;squeda" 
							});
			}// fin else
			configurarColumnasGridBuscarExpedientesParaPJ(tipoBusqueda);	
		}
		
		/*
		* Funcion para configurar las columnas del gridBuscarExpedientesPara PJ 
		* en caso de que la busqueda sea por Nombre de la Persona
		*/
		function configurarColumnasGridBuscarExpedientesParaPJ(tipoBusqueda){
			//Se muestran las columnas de Nombre,Apellido Paterno, Apellido Materno y Calidad
			if(tipoBusqueda == '<%=TipoDeBusquedaDeExpediente.POR_NOMBRE_PERSONA_PJ.getTipoBusquedaId()%>'){
				//Se muestran columnas
				jQuery("#tablaBuscarExpedientes").jqGrid('showCol',["nombre","apePaterno","apeMaterno","cNombreCalidad"]);
			}else{
				jQuery("#tablaBuscarExpedientes").jqGrid('hideCol',["nombre","apePaterno","apeMaterno","cNombreCalidad"]);
			}
		}
		
		/*
		*Funcion que crea el visor Audiencias de un Numero de Expediente
		*/
		var idWindowAudienciasCausa=1; 
								
		function ventanaAudiencias(idNumeroExpediente,numeroExpediente) {
			idWindowAudienciasCausa++;
			$.newWindow({id:"iframewindowAudienciasCausa"+idWindowAudienciasCausa, statusBar: true, posx:255,posy:110,width:700,height:400,title:"Audiencias", type:"iframe",onWindowClose: function(id){
				idWindowAudienciasCausa--;
			}});
	    	$.updateWindowContent("iframewindowAudienciasCausa"+idWindowAudienciasCausa,'<iframe src="<%= request.getContextPath() %>/visorAudienciasPorExpediente.do?numeroExpedienteId='+idNumeroExpediente+'&numeroExpediente='+numeroExpediente+'&idRolActivo='+idRolActivo+'" width="700" height="400" />');
		}
		
		

</script>

<body>
	<p>&nbsp;</p>
	<p>&nbsp;</p>

	<!-- SECCION PARA REALIZAR DIFERENTES TIPOS DE BUSQUEDA -->
	<div align="center">
		<table width="53%" border="0">
			<tr>
				<td colspan="2" align="center">Tipo de b&uacute;squeda: <select
					name="cmbTipoBusqueda" id="cmbTipoBusqueda"></select>
				</td>
			</tr>
			<tr>
				<td width="26%">&nbsp;</td>
				<td width="74%">&nbsp;</td>
			</tr>
		</table>
		<!-- SECCION BUSQUEDA POR DELITO -->
		<div id="seccionDeBusqueda1" class="seccionDeBusqueda">
			<table width="53%" border="0">
				<tr>
					<td align="center">Seleccione el Delito: <select
						name="catDelito" id="catDelito">
					</select></td>
				</tr>
			</table>
		</div>

		<!-- SECCION BUSQUEDA POR FECHAS -->
		<div id="seccionDeBusqueda4" class="seccionDeBusqueda">
			<table width="53%" border="0">
				<tr>
					<td align="center">Fecha Inicio: <input type="text"
						id="idFechaDateLapso" size="15" maxlength="10" readonly="readonly"></td>
				</tr>
				<tr>
					<td align="center">Fecha Fin:&nbsp;&nbsp;&nbsp; <input
						type="text" id="idFechaDateLapso2" size="15" maxlength="10"
						readonly="readonly"></td>
				</tr>
			</table>
		</div>

		<!-- SECCION BUSQUEDA POR NOMBRE DE PERSONA -->
		<!-- Se valida si la institucion es PJ para que se muestra la seccion div dependiendo del enum POR_NOMBRE_PERSONA o POR_NOMBRE_PERSONA_PJ en la funcion enableControls(tipo) -->
		<div id='<%= confInstitucionId==Instituciones.PJ.getValorId() ? "seccionDeBusqueda10":"seccionDeBusqueda6" %>' class="seccionDeBusqueda">
			<table width="53%" border="0">
				<tr>
					<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nombre(s):
						<input type="text" name="nombre" id="nombre" size="40"
						maxlength="50"
						onkeypress="return soloLetrasNPunto(event,this.id);"
						onblur="validaSoloLetras(this);" />
					</td>
				</tr>
				<tr>
					<td align="center">Apellido Paterno: <input type="text"
						name="apellido" id="apellido" size="40" maxlength="50"
						onkeypress="return soloLetrasNPunto(event,this.id);"
						onblur="validaSoloLetras(this);" /></td>
				</tr>
				<tr>
					<td align="center">Apellido Materno: <input type="text"
						name="apellidoMat" id="apellidoMat" size="40" maxlength="50"
						onkeypress="return soloLetrasNPunto(event,this.id);"
						onblur="validaSoloLetras(this);" /></td>
				</tr>
				<tr>
					<td align="center">* S&oacute;lo se permiten letras, acentos,
						espacios y di&eacute;resis</td>
				</tr>
			</table>
		</div>

		<!-- SECCION BUSQUEDA POR NOMBRE ORGANIZACION -->
		<div id="seccionDeBusqueda7" class="seccionDeBusqueda">
			<table width="53%" border="0">
				<tr>
					<td align="center"><span id="etiquetaNombreOrg">Nombre
							Organizaci&oacute;n</span>:&nbsp;&nbsp; <input type="text"
						name="nombreOrg" id="nombreOrg" size="50" maxlength="50" /></td>
				</tr>
				<tr>
					<td align="center">* S&oacute;lo se permiten n&uacute;meros,
						letras y los caracteres especiales: "-","/" y "Y"</td>
				</tr>
			</table>
		</div>

		<!-- SECCION BUSQUEDA POR NUMERO DE CASO -->
		<div id="seccionDeBusqueda3" class="seccionDeBusqueda">
			<table width="53%" border="0">
				<tr>
					<td align="center">N&uacute;mero de Caso:&nbsp;&nbsp; <input
						type="text" name="noCaso" id="noCaso" size="50" maxlength="50" /></td>
				</tr>
				<tr>
					<td align="center">* S&oacute;lo se permiten n&uacute;meros,
						letras y los caracteres especiales: "-","/" y "Y"</td>
				</tr>
			</table>
		</div>

		<!-- SECCION BUSQUEDA POR NUMERO DE EXPEDIENTE -->
		<!-- Se valida si la institucion es PJ para que se muestra la seccion div dependiendo del enum POR_NUM_EXP o POR_NUM_EXP_PJ en la funcion enableControls(tipo) -->
		<div id='<%= confInstitucionId==Instituciones.PJ.getValorId() ? "seccionDeBusqueda9":"seccionDeBusqueda2" %>' class="seccionDeBusqueda">
			<table width="53%" border="0">
				<tr>
					<td align="center"><span id="etiquetaNumExp">N&uacute;mero
							Expediente</span>:&nbsp;&nbsp; <input type="text" name="noExpediente"
						id="noExpediente" size="50" maxlength="50" /></td>
				</tr>
				<tr>
					<td align="center">* S&oacute;lo se permiten n&uacute;meros,
						letras y los caracteres especiales: "-" y "/"</td>
				</tr>
			</table>
		</div>

		<!-- SECCION BUSQUEDA POR NUMERO DE EXPEDIENTE EN ATP -->
		<div id="seccionDeBusqueda5" class="seccionDeBusqueda">
			<table width="53%" border="0">
				<tr>
					<td align="center"><span id="etiquetaNumExp2">N&uacute;mero
							Expediente</span>:&nbsp;&nbsp; <input type="text" name="noExpedienteATP"
						id="noExpedienteATP" size="50" maxlength="50" /></td>
				</tr>
				<tr>
					<td align="center">* S&oacute;lo se permiten n&uacute;meros,
						letras y los caracteres especiales: "-" y "/"</td>
				</tr>
			</table>
		</div>
		<table width="53%" border="0">
			<tr>
				<td align="center"><br>
				<input type="button" name="buscarExpediente" value="Buscar"
					id="buscarExpediente" class="btn_Generico" /></td>
			</tr>
		</table>
		<br>
		<table align="center" id="tablaBuscarExpedientes"></table>
		<div id="pagerNoExpediente"></div>

	</div>
</body>
</html>