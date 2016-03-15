<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.expediente.TipoDeBusquedaDeExpediente" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
   	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	String rolUsuarioSesion = "";
	Long idRolActivo = 0L;
	if (usuario != null && usuario.getRolActivo() != null){
		rolUsuarioSesion = usuario.getRolActivo();
		idRolActivo = usuario.getRolACtivo().getRol().getRolId();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>B&uacute;squeda avanzada de expediente</title>
<style>
	input.error {
		color: red;
		background-color: #FFFFD5;
		border: 2px solid red;
		margin: 0;
	}
</style>
</head>

	<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
	<!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/themes/1.8.10/${theme.name}/jquery-ui.css" /-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />				
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/${theme.name}/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
     
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script src="<%= request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%= request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>	
	


<script type="text/javascript">

	var reloadGridExpediente = false;
    var validaNombre = false;
    var datosCorrectos = false;
    var idWindowNuevaDenuncia=1;
    var opcionNoPenal="";

    var tipoOrigen= '<%=request.getParameter("tipo")!=null?request.getParameter("tipo"):""%>';
    var opcion=<%=request.getAttribute("opcion")%>;

    var rolUsuario= "";
    var rolUserSesion="<%=rolUsuarioSesion%>";
	var idRolActivo = <%=idRolActivo%>;
	var tipoOperacion = '<%=request.getParameter("tipoOperacion")!=null?request.getParameter("tipoOperacion"):""%>';
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
	var DEFENSOR_ATE = '<%=Roles.DEFENSORATE.getValorId()%>'; //15
	var COORDINADOR_DEF = '<%=Roles.COORDINADORDEF.getValorId()%>'; //13
	var contextoPagina = "${pageContext.request.contextPath}";
	
	var BUSQUEDA_AVANZADA_DE_EXPEDIENTE = 1;
	var CONSULTAR_HISTORIAL_DE_EXPEDIENTE = 2;
	
	
	$(document).ready(function() {
		//Se oculta la seccion de persona moral, dado que la seccion que se muestra por default es la de persona moral
		$('#tblOrganizacion').hide();
		$('#idTipoExpdiente').hide();
		
		if(idRolActivo == '<%=Roles.AGENTEMP.getValorId()%>' || 
		   idRolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>' || 
		   idRolActivo == '<%=Roles.COORDINADORAMP.getValorId()%>'){
				$('#idTipoExpdiente').show();	
		}
			
		
		//Configuracion para validar el campo de Numero de Caso y Numero de expediente
		$("#noCaso").bind("keyup blur" , function () {
			validarCampo(/[^0-9a-zA-Z/-]/g , $(this).val()) == true ?
			$(this).removeClass("error") :  $(this).addClass("error");
		});	
		
		$("#noExpediente").bind("keyup blur" , function () {
			validarCampo(/[^0-9a-zA-Z/-]/g , $(this).val()) == true ?
			$(this).removeClass("error") :  $(this).addClass("error");
		});	
		//Para la organizacion
		$("#nombreOrg").bind("keyup blur" , function () {
			validarCampo(/[^a-zA-Z ����������������������&]/g , $(this).val()) == true ?
			$(this).removeClass("error") :  $(this).addClass("error");
		});	
		
		//Nombre
		$("#nombre").bind("keyup blur" , function () {
			validarCampo(/[^a-zA-Z ����������������������]/g , $(this).val()) == true ?
			$(this).removeClass("error") :  $(this).addClass("error");
		});
		//Apellido paterno
		$("#apellido").bind("keyup blur" , function () {
			validarCampo(/[^a-zA-Z ����������������������]/g , $(this).val()) == true ?
			$(this).removeClass("error") :  $(this).addClass("error");
		});	
		//Apellido materno
		$("#apellidoMat").bind("keyup blur" , function () {
			validarCampo(/[^a-zA-Z ����������������������]/g , $(this).val()) == true ?
			$(this).removeClass("error") :  $(this).addClass("error");
		});	
		//Nombre
		$("#nombreFun").bind("keyup blur" , function () {
			validarCampo(/[^a-zA-Z ����������������������]/g , $(this).val()) == true ?
			$(this).removeClass("error") :  $(this).addClass("error");
		});
		//Apellido paterno funcionario
		$("#apellidoFun").bind("keyup blur" , function () {
			validarCampo(/[^a-zA-Z ����������������������]/g , $(this).val()) == true ?
			$(this).removeClass("error") :  $(this).addClass("error");
		});	
		//Apellido Materno Funcionario
		$("#apellidoMatFun").bind("keyup blur" , function () {
			validarCampo(/[^a-zA-Z ����������������������]/g , $(this).val()) == true ?
			$(this).removeClass("error") :  $(this).addClass("error");
		});	
		
		//Eventos
		$("#buscarExpediente").bind("click",ejecutaBusqueda);
		//Carga combo de delitos
		cargaComboDelito();
		$("#idFechaDateLapso").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			onSelect: function(date) {
				//setter fecha minima al segunda calendario
				$( "#idFechaDateLapso2" ).datepicker( "option", "minDate", date );
			},
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		$("#idFechaDateLapso2").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true			
		});
		
		$("#catDelito").multiselect({ 
			multiple: false, 
			header: "- Seleccione un delito -", 
			position: { 
				my: 'top', 
				at: 'top' 
			},
			minWidth: 350,
			selectedList: 1, 
			close: function (event,ui){
				
			}
		});
		
		//vamos por la fecha actual al servidor
		//fechaServidor= consultaFechaHoraMaximaServer();
		//fechaMax=getFechaMaximaServer(fechaServidor);
		//$("#idFechaDateLapso,#idFechaDateLapso2").val(fechaMax);
		
		cargaEstatusDeExpediente();
		$("#cbxEstatusExpediente").multiselect({ 
			multiple: false, 
			header: "- Seleccione un estatus de expediente -", 
			position: { 
				my: 'top', 
				at: 'top' 
			},
			minWidth: 200,
			selectedList: 1, 
			close: function (event,ui){
				
			}
		});
		
		
		consultarAniosParaBusquedaAvanzadaExpediente();
		$("#cbxAnioExpediente").multiselect({ 
			multiple: false, 
			header: "- Seleccione -", 
			position: { 
				my: 'top', 
				at: 'top' 
			},
			minWidth: 130,
			selectedList: 1, 
			close: function (event,ui){
				
			}
		});

	});
	
	function limpiaCampos(){		
		$("#nombre").val("");
		$("#apellido").val("");
		$("#apellidoMat").val("");		
		$("#noExpediente").val("");
		$("#noExpedienteATP").val("");
		$("#noCaso").val("");
		//$("#idFechaDateLapso,#idFechaDateLapso2").val(fechaMax);
		$("#nombreOrg").val("");
	}
	
	function tituloVentana(num){
		$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Expediente: "+num);
	}

	//Llena el grid con los resultados de la busqueda, pasa como parametros el No de expediente
	  function llenaGridExpediente(){
		  var tipoBusqueda = '<%= TipoDeBusquedaDeExpediente.BUS_AVANZADA_DE_EXP.getTipoBusquedaId() %>'
		  var params;
	        params="tipoBusqueda=" + tipoBusqueda;      
     		params+="&fechaIni=" + $("#idFechaDateLapso").val();
     		params+="&fechaFin=" + $("#idFechaDateLapso2").val();
     		params+="&nombreOrganizacion=" + $("#nombreOrg").val();
     		params+="&nombre=" + $('#nombre').val();
     		params+="&apPaterno=" + $('#apellido').val();
     		params+="&apMaterno=" + $('#apellidoMat').val();
 	        params+="&numeroCaso=" + $("#noCaso").val();
 	        params+="&numeroExpediente=" + $("#noExpediente").val();
     		params+="&esBusquedaATP=" + $(':radio[name=radioGroupExpediente]:checked').val();
     		params+="&anio=" + $('#cbxAnioExpediente').val();    
     		params+="&catdelitId=" + $('#catDelito').val();
     		params+="&estatus=" + $('#cbxEstatusExpediente').val();    
     		params+="&nombreFun=" + $('#nombreFun').val();
     		params+="&apPaternoFun=" + $('#apellidoFun').val();
     		params+="&apMaternoFun=" + $('#apellidoMatFun').val();
     		

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
							          'Area','Edificio','Estatus','Consulta','Fecha Apertura','idArea'], 
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
						 				
						 				if(tipoOperacion == CONSULTAR_HISTORIAL_DE_EXPEDIENTE){
						 						verHistorialExpediente(idExpediente,idNumeroExpediente,numeroExpediente,idArea,idCaso, esConsulta);
						 				}else{
							 				if(esConsulta == "1"){
												abrirVisorExpediente(idNumeroExpediente,idExpediente, numeroExpediente, parseInt(idArea));
											}else{//No tiene permisos para visualizar el expediente
												customAlert("Usted no es el due�o del expediente,<br> y no cuenta con permisos para consultarlo.","");
											}	 
						 					
						 				}
						 				
						 			 }     
																						
								},								
								caption:"Resultado de la B&uacute;squeda Avanzada" 
						});
		 			 }
				
			configurarColumnasGridXRol(tipoBusqueda);
  				
  			//Mensaje en caso de no obtener resultados	  			   
 			   var ids = jQuery("#tablaBuscarExpedientes").jqGrid('getDataIDs');
 			   if(parseInt(ids.length) == 0){
 					//customAlert("No se encontraron expedientes que cumplan con los criterios especificados")


 			   }
 			   
 		}
	
	//Funci�n que genera un nuevo n&uacute;mero de expediente para la UI en el mismo expediente
		function nuevoNumeroExpediente(id){
				
			var idExpediente="0";
			var numeroExpediente="0";
			var numeroExpedienteId="0";
			
			$.ajax({
	    		type: 'POST',
	    		url: '<%=request.getContextPath()%>/nuevoNumeroExpediente.do?idArea='+<%=Areas.UNIDAD_INVESTIGACION.ordinal()%>+'&idExpediente='+id+'',
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
	
	//Funcion que valida si los campos estan llenos al enviar 
		function ejecutaBusqueda(){
			var tipoBusqueda = '<%= TipoDeBusquedaDeExpediente.BUS_AVANZADA_DE_EXP.getTipoBusquedaId() %>'
		    validaCamposDelaBusqueda();
			if(datosCorrectos == true){
	      		llenaGridExpediente();
			}
		}	
	
		//Funcion que valida si los campos estan llenos al enviar 
		function validaCamposDelaBusqueda(){
			var mensaje = "";
			//Para numero de Caso
			if(validarCampo(/[^0-9a-zA-Z/-]/g , $("#noCaso").val()) == false)
				mensaje = mensaje + "<br /> - N&uacute;mero de caso"
			//Para numero de expediente
			if(validarCampo(/[^0-9a-zA-Z/-]/g , $("#noExpediente").val()) == false)
				mensaje = mensaje + "<br /> - N&uacute;mero de expediente"
				
			//Nombre Fun
			if(validarCampo(/[^a-zA-Z ����������������������]/g , $("#nombreFun").val()) == false)
				mensaje = mensaje + "<br /> - Nombre del funcionario"
			//Apellido paterno funcionario
			if(validarCampo(/[^a-zA-Z ����������������������]/g , $("#apellidoFun").val()) == false)
				mensaje = mensaje + "<br /> - Apellido paterno del funcionario"
			//Apellido Materno Funcionario
			if(validarCampo(/[^a-zA-Z ����������������������]/g , $("#apellidoMatFun").val()) == false)
				mensaje = mensaje + "<br /> - Apellido materno del funcionario"
				
				if($(':radio[name=RadioGroupTipoPersona]:checked').val() == 2){
					//Para la organizacion
					if(validarCampo(/[^a-zA-Z ����������������������&]/g , $("#nombreOrg").val()) == false)
						mensaje = mensaje + "<br /> - Nombre de la organizaci&oacute;n"
					$("#nombre").val("");
					$("#apellido").val("");
					$("#apellidoMat").val("");
					
					$("#nombre").removeClass("error");
					$("#apellido").removeClass("error");
					$("#apellidoMat").removeClass("error");
					
				}else{
					//Nombre
					if(validarCampo(/[^a-zA-Z ����������������������]/g , $("#nombre").val()) == false)
						mensaje = mensaje + "<br /> - Nombre del involucrado"
					//Apellido paterno
					if(validarCampo(/[^a-zA-Z ����������������������]/g , $("#apellido").val()) == false)
						mensaje = mensaje + "<br /> - Apellido paterno del involucrado"
					//Apellido materno
					if(validarCampo(/[^a-zA-Z ����������������������]/g , $("#apellidoMat").val()) == false)
						mensaje = mensaje + "<br /> - Apellido materno del involucrado"			
						
					$("#nombreOrg").val("");
					$("#nombreOrg").removeClass("error");

				}
				//Validar fecha de creaci�n del expediente:inicio y fin
				if( $("#idFechaDateLapso").val() != "" || $("#idFechaDateLapso2").val() != ""){
					resp = validaCamposFechaConMensaje($("#idFechaDateLapso").val(), $("#idFechaDateLapso2").val());
					if(resp != "")
						mensaje = mensaje + "<br /> - " + resp;			
				}

				
			if(mensaje != ""){
				customAlert("Favor de revisar el(los) siguiente(s) campo(s): " + mensaje);
				//Pone el foco en el primer input con clase de error
				$(":input.error").first().focus();
				datosCorrectos=false;
			}
			else{
				respuseta =existeAlMenosUnCriterioDeBusqueda();
				if(!respuseta){
					customAlert("Debe de seleccionar al menos un criterio de b&uacute;squeda.");
					datosCorrectos=false;
				}else{
					datosCorrectos=true;
				}
			}						
		}
		
		function existeAlMenosUnCriterioDeBusqueda(){
			if( $("#noCaso").val() != "")
				return true;
			if( $("#noExpediente").val() != "")
				return true;
			if( $("#nombreFun").val() != "" || $("#apellidoFun").val() != "" || $("#apellidoMatFun").val() != "")
				return true;
			
			if($(':radio[name=RadioGroupTipoPersona]:checked').val() == 2){
				//Para la organizacion
				if( $("#nombreOrg").val() != "")
					return true;
			}else{
				if( $("#nombre").val() != "" || $("#apellido").val() != "" || $("#apellidoMat").val() != "")
					return true;
			}
			if( $("#cbxAnioExpediente").val() != "0")
				return true;
			if( $("#catDelito").val() != "0")
				return true;
			if( $("#cbxEstatusExpediente").val() != "0")
				return true;
			
			if( $("#idFechaDateLapso").val() != "" && $("#idFechaDateLapso2").val() != "")
				return true;
			return false;
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
			if ($('#noCaso').val()==''){
				if($('#noCaso').val()==''){
					customAlert("Favor de ingresar un n�mero de caso");
					datosCorrectos=false;
				}
			}else {
				datosCorrectos=true;
			}							
		}		
		
		//Funcion que valida si los campos estan llenos al enviar 
		function validaCamposNombre(){
			if ($('#nombre').val()=='' && $('#apellido').val()=='' && $('#apellidoMat').val()==''){
					customAlert("Favor de ingresar un Nombre o un Apellido");
					datosCorrectos=false;
			}
			else{ 
				datosCorrectos=true;
			}
		}
		
		//Funcion que valida si los campos estan llenos al enviar 
		function validaCamposFecha(){
			if ($('#idFechaDateLapso').val()==''|| $('#idFechaDateLapso2').val()==''){
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
	 			url: '<%= request.getContextPath()%>/consultarCatalogoDelitos.do',
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
			if ($('#noExpedienteATP').val()==''){
				if($('#noExpedienteATP').val()==''){
					customAlert("Favor de ingresar un N�mero de Expediente");
					datosCorrectos=false;
				}
				}else {
					datosCorrectos=true;
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
	      		var numeroExpedienteId = nuevoNumeroExpediente(idExpediente);
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
				$.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/BusquedaExpedienteSistTrad.do?abreenPenal=abrPenal&ingresoDenuncia='+ingresoDenuncia +'&idNumeroExpediente='+idNumeroExpediente+'&pantallaSolicitada='+pantallaSolicitada+'" width="100%" height="100%" />');
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
			   $.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/BusquedaExpediente.do?abreenPenal=abrPenal&ingresoDenuncia='+ingresoDenuncia +'&idNumeroExpediente='+idNumeroExpediente+'&pantallaSolicitada='+pantallaSolicitada+'&idIframe='+idIframe+'&idArea='+idArea+'" width="1430" height="670" />');
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
			   $.updateWindowContent("iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia,'<iframe src="<%= request.getContextPath() %>/BusquedaExpedienteSistTrad.do?abreenPenal=abrPenal&ingresoDenuncia='+ingresoDenuncia +'&idNumeroExpediente='+idNumeroExpediente+'&pantallaSolicitada='+pantallaSolicitada+'&idIframe='+idIframe+'&esModuloConsultaDeExpedientes=true'+'&idArea='+idArea+'" width="100%" height="100%" />');
			   $("#" +"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+ " .window-maximizeButton").click();	
		}
		
		
		/**
		* Permite consultar un expediente de atenci�n temprana administrativa
		**/
		function consultaExpedienteDeAtencionTemAdm(numeroExpediente, idNumeroExpediente) {
			$.newWindow({id:"iframewindowRegistraDatosPersona", statusBar: true, posx:200,posy:50,width:1140,height:450,title:"Expediente:  "+numeroExpediente, type:"iframe"});
		    $.updateWindowContent("iframewindowRegistraDatosPersona",'<iframe src="<%= request.getContextPath() %>/BusquedaExpedienteAdminAt.do?formaId=2&numeroExpedienteTempAdmin='+ numeroExpediente +'&idExpedienteTempAdmin='+idNumeroExpediente+'&operacion=CONSULTA " width="1140" height="450" />');
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

		function configurarColumnasGridXRol(tipoBusqueda){

			//Se oculta la columna de caso dado que algunos roles no generan numero de caso
 			if(idRolActivo == '<%=Roles.AGENTEMPSISTRAD.getValorId()%>' || 
			     idRolActivo == '<%=Roles.COORDINADOR_CONSIGNACION.getValorId()%>' || 
				 idRolActivo == '<%=Roles.CONSIGNADOR.getValorId()%>'){
				jQuery("#tablaBuscarExpedientes").jqGrid('hideCol',["cNumeroGeneralCaso"]);
			}else{
				jQuery("#tablaBuscarExpedientes").jqGrid('showCol',["cNumeroGeneralCaso"]);
			}
			
 			//Se muestran las columnas del nombre involucrado y su calidad para el caso en que hayan buscado por persona u org.
			if($("#nombreOrg").val() != "" || $('#nombre').val() != "" || $('#apellido').val() != "" || $('#apellidoMat').val() != "" ){
				//Se muestran columnas
				jQuery("#tablaBuscarExpedientes").jqGrid('showCol',["cInvolucrado","cNombreCalidad"]);
			}else{
				jQuery("#tablaBuscarExpedientes").jqGrid('hideCol',["cInvolucrado","cNombreCalidad"]);
			}

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
		
		function cargaEstatusDeExpediente(){
			$.ajax({
				type: 'POST',
		    	url: '<%=request.getContextPath()%>/consultarEstatusDeExpedientesDiferentes.do',
		    	data: '',
		    	dataType: 'xml',
		    	async: false,
		    	success: function(xml){
			    		$(xml).find('listaCatalogo').find('valorDTO').each(function(){
							$('#cbxEstatusExpediente').append('<option value="' + $(this).find('idCampo').text() + '">' + $(this).find('valor').text()+ '</option>');
						});					
				}
			});
		}
		
		function consultarAniosParaBusquedaAvanzadaExpediente(){
			$.ajax({
				type: 'POST',
		    	url: '<%=request.getContextPath()%>/consultarAniosParaBusquedaAvanzadaExpediente.do',
		    	data: '',
		    	dataType: 'xml',
		    	async: false,
		    	success: function(xml){
			    		$(xml).find('listaCatalogo').find('int').each(function(){
							$('#cbxAnioExpediente').append('<option value="' + $(this).text() + '">' + $(this).text()+ '</option>');
						});					
				}
			});
		}
		
</script>

<body>
	<br>
	<table width="95%" border="0" align="center">
	
	  <tr>
	    <td width="57%">&nbsp;</td>
	    <td width="43%" align="right"><input type="button" name="buscarExpediente" value="Buscar" id="buscarExpediente" class="ui-button ui-corner-all ui-widget"/></td>
	  </tr>
	  <tr>
	    <td valign="top"><fieldset>
	      <table width="100%" border="0">
	        <tr>
	          <td align="center"><strong>Por n&uacute;mero de caso:</strong></td>
	        </tr>
	        <tr>
	          <td align="center">N&uacute;mero de Caso:&nbsp;&nbsp;
	            <input type="text" name="noCaso" id="noCaso" size="50" maxlength="50" /></td>
	        </tr>
	        <tr>
	          <td align="center">* S&oacute;lo se permiten n&uacute;meros, letras y los caracteres especiales: "-" y"/"</td>
	        </tr>
	        <tr>
	          <td align="center">&nbsp;</td>
	        </tr>
	      </table>
	    </fieldset></td>
	    <td>
	    <fieldset>
	      <table width="100%" border="0">
	        <tr>
	          <td align="center"><strong>Por nombre de Funcionario:</strong></td>
	        </tr>
	        <tr>
	          <td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nombre(s):
	            <input type="text" name="nombreFun" id="nombreFun" size="40" maxlength="50"/></td>
	        </tr>
	        <tr>
	          <td align="center">Apellido Paterno:
	            <input type="text" name="apellidoFun" id="apellidoFun" size="40"/></td>
	        </tr>
	        <tr>
	          <td align="center">Apellido Materno:
	            <input type="text" name="apellidoMatFun" id="apellidoMatFun" size="40" maxlength="50"/></td>
	        </tr>
	        <tr>
	          <td align="center">* S&oacute;lo se permiten letras, acentos, espacios y di&eacute;resis</td>
	        </tr>
	      </table>
	    
	    
	    </fieldset>
	    </td>
	  </tr>
	  <tr>
	    <td valign="top">
	    
	    <fieldset>
	    <table width="100%" border="0">
	      <tr>
	        <td align="center"><strong>Por n&uacute;mero de expediente:</strong></td>
	      </tr>
	      <tr id="idTipoExpdiente">
	        <td align="center"><p>
	        
			  <input name="radioGroupExpediente" type="radio" id="radioGroupExpediente_1" value="0" checked>
	          Carpeta de Investigaci&oacute;n
	
	            <input type="radio" name="radioGroupExpediente" value="1" id="radioGroupExpediente_0">
	            Atenci&oacute;n Temprana &nbsp;&nbsp;
	
	            
	          <br>
	        </p></td>
	      </tr>
	      <tr>
	        <td align="center"><span id="etiquetaNumExp">N&uacute;mero Expediente</span>:&nbsp;&nbsp;
	          <input type="text" name="noExpediente" id="noExpediente" size="50" maxlength="50" /></td>
	      </tr>
	      <tr>
	        <td align="center">* S&oacute;lo se permiten n&uacute;meros, letras y los caracteres especiales: "-" y "/"</td>
	      </tr>
	      <tr>
	        <td align="center">A&ntilde;o:
	           <select name="cbxAnioExpediente" id="cbxAnioExpediente">
						<option value="0">- Seleccione -</option>					
				</select>
	        </td>
	      </tr>
	    </table>
	    </fieldset>
	    
	    </td>
	    <td rowspan="2" valign="top">
	    <fieldset>
	      <table width="100%" border="0">
	        <tr>
	          <td align="center"><p><strong>Por nombre de involucrado:</strong></p>
	            <table width="100%" border="0">
	              <tr>
	                <td align="center"><p>
	                  <input name="RadioGroupTipoPersona" type="radio" id="RadioGroupTipoPersona_0" value="1" checked onclick="$('#tblPersonaFisica').show();$('#tblOrganizacion').hide();">
	                  Persona F&iacute;sica &nbsp;&nbsp;
	                  <input type="radio" name="RadioGroupTipoPersona" value="2" id="RadioGroupTipoPersona_1" onclick="$('#tblOrganizacion').show();$('#tblPersonaFisica').hide();">
	                 
	                  Persona Moral <br>
	                </p></td>
	              </tr>
	            </table>
	            </td>
	        </tr>        
	      </table>
	      
	      <table width="100%" border="0" id="tblPersonaFisica">
	        <tr>
	          <td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nombre(s):
	            <input type="text" name="nombre" id="nombre" size="40" maxlength="50"/></td>
	        </tr>
	        <tr>
	          <td align="center">Apellido Paterno:
	            <input type="text" name="apellido" id="apellido" size="40" maxlength="50" /></td>
	        </tr>
	        <tr>
	          <td align="center">Apellido Materno:
	            <input type="text" name="apellidoMat" id="apellidoMat" size="40" maxlength="50" /></td>
	        </tr>
	        <tr>
	          <td align="center">* S&oacute;lo se permiten letras, acentos, espacios y di&eacute;resis</td>
	        </tr>
	      </table>
	      
	      
	      <table width="100%" border="0" id="tblOrganizacion">
	        <tr>
	          <td align="center">&nbsp;</td>
	        </tr>
	        <tr>
	          <td align="center"><span id="etiquetaNombreOrg">Nombre Organizaci&oacute;n</span>:&nbsp;&nbsp;
	            <input type="text" name="nombreOrg" id="nombreOrg" size="50" maxlength="50" /></td>
	        </tr>
	        <tr>
	          <td align="center">* S&oacute;lo se permiten letras, acentos, espacios, di&eacute;resis y &amp; </td>
	        </tr>
	        <tr>
	          <td align="center">&nbsp;</td>
	        </tr>
	        <tr>
	          <td align="center">&nbsp;</td>
	        </tr>
	      </table>
	    </fieldset>
	    </td>
	  </tr>
	  <tr>
	    <td valign="bottom">
	     <fieldset>
		     <table width="100%" border="0">
	        <tr>
		          <td align="center"><strong>Por Delito:</strong></td>
	        </tr>
		        <tr>
		          <td align="center">
			         <select name="catDelito" id="catDelito" style="width: 700px">
			          	<option value="0">- Seleccione un delito -</option>					
			          </select>
		           </td>
	            </tr>
		        <tr>
		          <td align="center">&nbsp;</td>
	            </tr>
	        </table>
         </fieldset>
</td>
      </tr>
	  <tr>
	    <td>
	    <fieldset>
	        <table width="100%" border="0">
	      <tr>
	        <td align="center"><strong>Fecha de creaci&oacute;n del expediente:</strong></td>
	      </tr>
	      <tr>
	        <td align="center">Fecha Inicio:
	          <input type="text" id="idFechaDateLapso" size="15" maxlength="10"></td>
	      </tr>
	      <tr>
	        <td align="center">Fecha Fin:&nbsp;&nbsp;&nbsp;
	          <input type="text" id="idFechaDateLapso2" size="15" maxlength="10"></td>
	      </tr>
	    </table>
	    
	    </fieldset>
	
	    
	    </td>
	    <td valign="top">
	    
	    <fieldset>    
	        <table width="100%" border="0">
	          <tr>
	            <td align="center"><strong>Por estatus del expediente:</strong></td>
	          </tr>
	          <tr>
	            <td align="center">&nbsp;</td>
	          </tr>
	          <tr>
	            <td align="center">
		            <select name="cbxEstatusExpediente" id="cbxEstatusExpediente">
						<option value="0">- Seleccione un estatus de expediente -</option>					
					</select>
				</td>
	          </tr>
	        </table>
	    </fieldset>
	    </td>
	  </tr>
	</table>
	
	<br>
	<table align="center" id="tablaBuscarExpedientes"></table><div id="pagerNoExpediente"></div>
    	
</body>
</html>	