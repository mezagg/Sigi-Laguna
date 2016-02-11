<%@ page import="mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO"%>
<%@ page import="mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page
	import="mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO"%>
<%@ page
	import="mx.gob.segob.nsjp.comun.enums.expediente.TipoDeBusquedaDeExpediente"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession()
			.getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	FuncionarioDTO funcionarioDTO = usuarioDTO.getFuncionario();
	CatDiscriminanteDTO catDiscriminanteDTO = funcionarioDTO
			.getDiscriminante();
	RolDTO rolDTO = usuarioDTO.getRolACtivo().getRol();
	JerarquiaOrganizacionalDTO jerarquiaOrganizacionalDTO = rolDTO
			.getJerarquiaOrganizacionalDTO();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reasignar responsable de un expediente en Polic&iacute;a
Ministerial</title>
<style>
input.error {
	color: red;
	background-color: #FFFFD5;
	border: 2px solid red;
	margin: 0;
}
</style>
</head>

<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/estilos.css"
	media="screen" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>



<script type="text/javascript">

	var reloadGridExpediente = false;
    var datosCorrectos = false;
    var idWindowNuevaDenuncia=1;
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
	var contextoPagina = "${pageContext.request.contextPath}";
	var idRolActivo = <%=rolDTO.getRolId()%>;
	
	var BUSQUEDA_POR_FUNCIONARIO = 1;
	var BUSQUEDA_POR_NUM_EXP = 2;
	
	
	$(document).ready(function() {
		//valida expresion regular para el numero de expediente
		$("#noExpediente").bind("keyup blur" , function () {
			validarCampo(/[^0-9a-zA-Z/-]/g , $(this).val()) == true ?
			$(this).removeClass("error") :  $(this).addClass("error");
		});	
		
		//Eventos
		$("#buscarExpediente").bind("click",ejecutaBusqueda);
		$("#btnReasignar").bind("click",reasignarFuncionario);
		
		cargarFuncionariosRolPoliciaMinisterial();
		
		consultarFuncionarios();

		$("#divBusquedaDeFuncionarios").hide();
		$("#seccionFuncionario").hide();
		$("#seccionNumeroExpediente").hide();
		$("#leyendaSeccionNumeroExpediente").hide();
		$("#buscarExpediente").hide();
		$("#btnReasignar").hide();
	});
	
	
	//Configuracion para el tap "Responsables del expediente"
	var gridHistorico = { 
		url:"",
		datatype: "local",
		colNames:['N&uacute;mero Expediente','&iquest;Es solicitud?','Id Responsable', 'Due&ntilde;o actual', 'Id Asigna', 'Quien realizo el cambio', 'Id Revocado', 'Due&ntilde;o anterior', 'Fecha cambio', 'Fecha Fin'], 
		colModel:[
					{name:'NumeroExpediente',index:'NumeroExpediente',width:230, align:"center"},
					{name:'esSolicitud',index:'esSolicitud',width:100, align:"center", sortable:false},
					{name:'IdResponsable',index:'FuncionarioActual', sortable:false, hidden:true, align:"center"}, 
					{name:'NombreResponsable',index:'FuncionarioActual',width:200, align:"center"},
					{name:'IdAsigna',index:'FuncionarioAsiigna', sortable:false, hidden:true, align:"center"}, 
					{name:'NombreAsigna',index:'FuncionarioAsigna',width:200, align:"center"},
					{name:'IdAnterior',index:'FuncionarioAnterior', sortable:false, hidden:true}, 
					{name:'NombreAnterior',index:'FuncionarioAnterior',width:200, align:"center"},							
					{name:'FechaCambio',index:'FechaCambio', align:"center", width:200,sortable:false},
					{name:'FechaFin',index:'FechaFin', align:"center", hidden:false, align:"center"}
				],
		width:1000,
		shrinkToFit:true,
		pager: "#pagerGridHistorico",
		rowNum:10,
		rowList:[10,20,30,40,50,60,70,80,90,100],
		autowidth: "1000px",
		sortname: 'historicoExpedienteId',
		viewrecords: true,
		height:250,
		onSelectRow: function(id){},
		ondblClickRow: function(id) {
		},
		caption: "",
		sortorder: "desc"			
	};
	
	$("#gridHistorico > .ui-jqgrid-titlebar").hide();
	
	function mostrarSeccionFuncionario(){
		$("#cbxFuncionariosPM option[value='0']").attr("selected",true);
		$("#seccionFuncionario").show();
		$("#seccionNumeroExpediente").hide();
		$("#leyendaSeccionNumeroExpediente").hide();
		$("#buscarExpediente").show();
		$("#divBusquedaDeFuncionarios").hide();
		$("#divBusquedaDeExpedientes").hide();
		$("#btnReasignar").hide();
	}
	
	function mostrarSeccionPorTipoExpediente(){
		$("#noExpediente").val('');
		$("#seccionFuncionario").hide();
		$("#seccionNumeroExpediente").show();
		$("#leyendaSeccionNumeroExpediente").show();
		$("#buscarExpediente").show();
		$("#divBusquedaDeFuncionarios").hide();
		$("#divBusquedaDeExpedientes").hide();
		$("#btnReasignar").hide();
	}
	
	function reasignarFuncionario(){
		var idsNumerosExpedientes = jQuery("#gridDeExpedientes").jqGrid('getGridParam','selarrrow');
		var idNuevoFuncionario = jQuery("#gridFuncionarios").jqGrid('getGridParam','selrow');
		var idFuncionarioActual = $("#cbxFuncionariosPM").val();
		
		if($(':radio[name=tipoBusqueda]:checked').val() == BUSQUEDA_POR_NUM_EXP){
			idFuncionarioActual = 0;
		}
		
   	 	if(parseInt(idsNumerosExpedientes.length) <= 0){
			customAlert("Debe de seleccionar al menos un expediente"); 	 		
   	 	}else{
   	 		if(parseInt(idNuevoFuncionario) <= 0){
   	 			customAlert("Debe de seleccionar al funcionario responsable");
   	 		}else{
	   	 		$.ajax({
	   	    		type: 'POST',
	   	    		url: '<%=request.getContextPath()%>/resignarNumerosDeExpedientes.do?idNuevoFuncionario='+idNuevoFuncionario+'&idsNumerosExpedientes='+idsNumerosExpedientes+'&idFuncionarioActual='+idFuncionarioActual+'',
	   	    		data: '',
	   	    		dataType: 'xml',
	   	    		async: false,
	   	    		success: function(xml){
	   	    			var respuesta=$(xml).find('respuesta').text();
	   	    			if(parseInt(respuesta) == 1){
	   	    				llenaGridExpediente();
	   	    				customAlert("Se realiz&oacute; de manera correcta la reasignaci&oacute;n del funcionario");
	   	    			}
	   	    			else{
	   	    				customAlert("No se pudo reasignar el funcionario");
	   	    			}
	   	    		}    		
	   	    	});
   	 		}
   	 	}
	}
	
	function tituloVentana(num){
		$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Expediente: "+num);
	}
	
	//Funcion que valida si los campos estan llenos al enviar 
		function ejecutaBusqueda(){
		    validaCamposDelaBusqueda();
			if(datosCorrectos == true){
	      		llenaGridExpediente();
			}
		}	
	
		//Funcion que valida si los campos estan llenos al enviar 
		function validaCamposDelaBusqueda(){
			datosCorrectos=false;				
				if($(':radio[name=tipoBusqueda]:checked').val() == BUSQUEDA_POR_NUM_EXP){
					if(validarCampo(/[^0-9a-zA-Z/-]/g , $("#noExpediente").val()) == false){
						customAlert("Favor de revisar el N&uacute;mero de expediente");
					}else{
						if($("#noExpediente").val().length < 4){
							customAlert("Favor de ingresar al menos cuatro caracteres del n&uacute;mero de expediente");
						}else{
							datosCorrectos=true;
						}
					}
				}else{
					//Por funcionario
					$("#noExpediente").val("");					
					$("#noExpediente").removeClass("error");
					
					if(parseInt($("#cbxFuncionariosPM").val()) == 0){
						customAlert("Favor de seleccionar un funcionario");
					}else{
						datosCorrectos=true;
					}						
				}
		}

		
		function abrirVisorExpediente(idNumeroExpediente,idExpediente, numeroExpediente, idArea) {
			var pantallaSolicitada;
			
			switch(idRolActivo){
	      	case <%=Roles.ATPENAL.getValorId()%>:
	      		pantallaSolicitada = ATPENAL;
	      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
				break;  	
	      	case <%=Roles.COORDINADORJAR.getValorId()%>:
	      		pantallaSolicitada = COORDINADOR_JAR;
	      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
				break;  	
	      	case <%=Roles.AGENTEMP.getValorId()%>:
	      		pantallaSolicitada = AGENTE_MP;
	      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      	    break;
	      	case <%=Roles.COORDINADORAMP.getValorId()%>:
	      		pantallaSolicitada = COORDINADOR_AMP;
	      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
	      	    break;
	    	case <%=Roles.COORDINADOR_DE_POLICIA_MINISTERIAL.getValorId()%>:
	      		pantallaSolicitada = COORDINADOR_AMP_GENERAL;
	      		consultaExpediente(numeroExpediente, idNumeroExpediente, pantallaSolicitada);
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
		* Permite consultar un expediente de atenci&oacute;n temprana administrativa
		**/
		function consultaExpedienteDeAtencionTemAdm(numeroExpediente, idNumeroExpediente) {
			$.newWindow({id:"iframewindowRegistraDatosPersona", statusBar: true, posx:200,posy:50,width:1140,height:450,title:"Expediente:  "+numeroExpediente, type:"iframe"});
		    $.updateWindowContent("iframewindowRegistraDatosPersona",'<iframe src="<%=request.getContextPath()%>/BusquedaExpedienteAdminAt.do?formaId=2&numeroExpedienteTempAdmin='+ numeroExpediente +'&idExpedienteTempAdmin='+idNumeroExpediente+'&operacion=CONSULTA " width="1140" height="450" />');
		    $("#" +"iframewindowRegistraDatosPersona .window-maximizeButton").click();	
		}
		
	
		function verHistorialExpediente(idExpediente,idNumeroExpediente,numeroExpediente,jerarquiaOrganizacional_id,idCaso, esConsulta) {
			
			var parametros = "?idExpediente="+idExpediente+"&idNumeroExpediente="+idNumeroExpediente+"&numeroExpediente="+numeroExpediente+"&jerarquiaOrganizacional_id="+jerarquiaOrganizacional_id+"&idCaso="+idCaso+"&idRolActivo="+idRolActivo+"&esConsulta="+esConsulta;
	    	customVentana("iframewindowHistorialExpediente", "Historial del Expediente", "/historialExpedienteView.do", parametros);
		}
		
		function validarCampo(filtro, valor)
		{
		        // utilizamos test para comprobar si el parametro valor cumple la regla
		        if(!filtro.test(valor))
		            return true;
		        else
		            return false;
		}
				
		/*
		* Permimte consultar los funcionarios con rol de polic&iacute;a ministerial que pertenezcan al distrito del usuario firmado en sesi&oacute;n
		* y que se encuentren activos.
		*/
		function cargarFuncionariosRolPoliciaMinisterial(){		
			$('#cbxFuncionariosPM').addClass('cargando');
			idRol = '<%=Roles.POLICIAMINISTER.getValorId()%>';
			$.ajax({
   				type: 'POST',
   				url: '<%=request.getContextPath()%>/consultarFuncionariosPorRolYDistrito.do',
   				data: 'idRol='+idRol,
   				dataType: 'xml',
   				async: true,
   				success: function(xml){
   					$(xml).find('FuncionarioDTO').each(function(){
   							$('#cbxFuncionariosPM').append('<option value="' + $(this).find('claveFuncionario').text() + '">'+ $(this).find('nombreFuncionario').text() + ' '+$(this).find('apellidoPaternoFuncionario').text() + ' '+$(this).find('apellidoMaternoFuncionario').text() +'</option>');
   					});
   					$('#cbxFuncionariosPM').removeClass('cargando');
   				}				
            });
		}
		
		//Llena el grid con los resultados de la busqueda, pasa como parametros el No de expediente
		  function llenaGridExpediente(){
			  $("#divBusquedaDeExpedientes").show();
			  var params;
			  
			  if($(':radio[name=tipoBusqueda]:checked').val() == BUSQUEDA_POR_NUM_EXP){
				  	params+="&numeroExpediente=" + $("#noExpediente").val();
				}else{
					//Por funcionario
					params="claveFuncionario=" + parseInt($("#cbxFuncionariosPM").val());						
				}
			if (reloadGridExpediente) {
				  jQuery("#gridDeExpedientes").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/buscarExpedientesAReasignarPM.do?'+params,datatype: "xml" });
			       $("#gridDeExpedientes").trigger("reloadGrid",[{page:1}]);
			  } else {
				  reloadGridExpediente = true;
				  jQuery("#gridDeExpedientes").jqGrid(
							{ 
			                    url:'<%=request.getContextPath()%>/buscarExpedientesAReasignarPM.do?'+params,									
								datatype: "xml", 
								mtype: 'POST',
								colNames:['No. Caso','No. Expediente','Expediente ID','Estatus',
								          'Responsable del expediente','Delito principal','Area','Historial'], 
								          colModel:[ 
										            {name:'cNumeroGeneralCaso',index:'cNumeroGeneralCaso', width:185,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:false},
										            {name:'cNumeroExpediente',index:'cNumeroExpediente', width:185,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:false},
										            {name:'expediente_id', index:'expediente_id',hidden:true},
											        {name:'cEstatus',index:'cEstatus', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:false},
										            {name:'cFuncionario',index:'cFuncionario', width:195,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:false},
										            {name:'cDelito',index:'cDelito', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:false},
										            {name:'jerarquiaOrganizacional_id', index:'jerarquiaOrganizacional_id',width:60, sortable:true, hidden:true, align:"center"},
										            {name:'lnkHistorial', index:'lnkHistorial',width:100, sortable:false, align:"center"},
												], 
									autowidth: "90%", 
									pager: jQuery('#pagerGridDeExpedientes'),
				                    rowNum:10,
				                    rowList:[10,20,30,40,50,60,70,80,90,100,200,300,400,500,600,700,800,900],
									sortname: '1', 
									rownumbers: false,
									gridview: true, 
									viewrecords: true, 
				    				multiselect: true,
									sortorder: "desc", 
				                    height:250,
									onSelectRow: function(id){
				    					$("#divBusquedaDeFuncionarios").show();
									},
									ondblClickRow: function(id){
										var ret = jQuery("#gridDeExpedientes").jqGrid('getRowData',id);
										var idNumeroExpediente = id;
										var idExpediente = ret.expediente_id;
										var numeroExpediente = ret.cNumeroExpediente;
										var idArea = ret.jerarquiaOrganizacional_id;
										abrirVisorExpediente(idNumeroExpediente,idExpediente, numeroExpediente, parseInt(idArea));
									},
									caption:"Resultado de la B&uacute;squeda" 
							});
				  
						  	 $("#cb_gridDeExpedientes").click(function(){
			    					$("#divBusquedaDeFuncionarios").show();
							 });
			 			 }
	 		}
		
		
		//Llena el grid con los resultados de la busqueda del funcionario
		var banderaCargarORecargarFuncionario=0;
	    function consultarFuncionarios(){
			var idDistrito = <%=catDiscriminanteDTO.getDistrito().getCatDistritoId()%>;
			var idCatDiscriminante = '';
			var idArea = <%=Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal()%>;
			var idCatUIE = '';
			
			//Inicia grid
			if(banderaCargarORecargarFuncionario==0){
				jQuery("#gridFuncionarios").jqGrid({
					url:'<%=request.getContextPath()%>/consultarFuncionarioPorDepartamento.do?idDistrito='+idDistrito+'&idCatDiscriminante='+idCatDiscriminante+'&idArea='+idArea+'&idCatUIE='+idCatUIE+'', 			
					datatype: "xml",  		
					async: true,
					colNames:['Nombre','Apellido paterno','Apellido materno'], 
					colModel:[  {name:'nombre',index:'1',width:250,align:'center'},
					            {name:'apellidoPaterno',index:'2', width:250,align:'center'},
								{name:'apellidoMaterno',index:'3',width:300,align:'center'}
							],
							pager: jQuery('#pagerGridFuncionarios'), 
							rowNum:10,
							rowList:[10,20,30],
		                    autowidth: "90%",
		                    height:250,
							sortname: '1', 
							viewrecords: true,
		                    sortorder: "asc",
		    				multiselect: false,
		    				caption: "Polic&iacute;as ministeriales del distrito",
		    				onSelectRow: function(id){
		    					$("#btnReasignar").show();
							}
				});
	        	$("#gridFuncionarios").trigger("reloadGrid");
	        	banderaCargarORecargarFuncionario=1;
		    }else{
		    	
				jQuery("#gridFuncionarios").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarFuncionarioPorDepartamento.do?idDistrito='+idDistrito+'&idCatDiscriminante='+idCatDiscriminante+'&idArea='+idArea+'&idCatUIE='+idCatUIE+'',datatype: "xml" });
				$("#gridFuncionarios").trigger("reloadGrid");		
		    }	            	
			//Fin grid
	    }
	    
		function verHistorial(id){
			gridHistorico.url = "<%=request.getContextPath()%>/consultarHistoricoExpediente.do";
			gridHistorico.postData = {
					numeroExpediente: id

				};
			gridHistorico.datatype = "xml";			
			cargarHistorico();	
			popUpDetalleDeNumeroExpediente();
		}
		
		function cargarHistorico() {
			$("#gridHistorico").GridUnload();
		 	$("#gridHistorico").jqGrid(gridHistorico)
						.navGrid("#pagerGridHistorico",{edit:false,add:false,del:false,search:false});
			try {
				ajustarGridAlCentro($("#gridHistorico"));
			}catch(e){}
		}
		
		function popUpDetalleDeNumeroExpediente(){
			var titulo = "CAMBIO DE DUE&Ntilde;OS DE EXPEDIENTE/SOLICITUD";
			$("#numeroExpediente").val("");
			$("#seccionDeHistorico").dialog("open");
		  	$("#seccionDeHistorico").dialog({ autoOpen: true, 
				modal: true,  
			  	dialogClass: 'alert',
			  	position: 'center',
		  		title: titulo, 
			  	width: 1100,
			  	height: 420,
			  	buttons:{"Aceptar":function() {
	  				$(this).dialog("close");
  				}
			  	}
			});
		}


		
</script>

<body>
<div style="margin-left: 200px; margin-right: 200px; margin-top: 15px"
	align="center">
<fieldset><legend>Tipo de b&uacute;squeda:</legend>

<table width="80%" border="0">
	<tr>
		<td colspan="2" align="center"><label> <input
			type="radio" name="tipoBusqueda" value="1" id="porFuncionario"
			onclick="mostrarSeccionFuncionario()" /> Por funcionario</label> <label>
		&nbsp;&nbsp;&nbsp; <input name="tipoBusqueda" type="radio"
			id="porNumExp" value="2" onclick="mostrarSeccionPorTipoExpediente()" />
		Por n&uacute;mero de expediente</label></td>
	</tr>
	<tr>
		<td width="27%">&nbsp;</td>
		<td width="73%">&nbsp;</td>
	</tr>
	<tr id="seccionFuncionario">
		<td colspan="2" align="center">Funcionario: <select
			name="cbxFuncionariosPM" id="cbxFuncionariosPM">
			<option value="0">- Seleccione -</option>
		</select></td>
	</tr>
	<tr id="seccionNumeroExpediente">
		<td colspan="2" align="center">N&uacute;mero de expediente: <input
			type="text" name="noExpediente" id="noExpediente" size="50"
			maxlength="50" /></td>
	</tr>
	<tr id="leyendaSeccionNumeroExpediente">
		<td colspan="2" align="center">* S&oacute;lo se permiten
		n&uacute;meros, letras y los caracteres especiales: "-" y "/"</td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="button"
			name="buscarExpediente" value="Buscar" id="buscarExpediente"
			class="ui-button ui-corner-all ui-widget" /></td>
	</tr>
</table>
</fieldset>
</div>

<p>&nbsp;</p>

<div id="divBusquedaDeExpedientes" align="center">
<table id="gridDeExpedientes"></table>
<div id="pagerGridDeExpedientes"></div>
</div>


<p>&nbsp;</p>
<div id="divBusquedaDeFuncionarios" align="center">
<table id="gridFuncionarios"></table>
<div id="pagerGridFuncionarios"></div>
</div>

<p>&nbsp;</p>
<p>&nbsp;</p>
<p align="center"><input type="button" name="btnReasignar"
	value="Reasignar" id="btnReasignar" class="ui-button ui-corner-all ui-widget" /></p>

<p>&nbsp;</p>
<p>&nbsp;</p>

<div id="seccionDeHistorico">
	<p>&nbsp;</p>
	<div align="center">
	<table id="gridHistorico"></table>
	<div id="pagerGridHistorico"></div>
	</div>
</div>



</body>
</html>
