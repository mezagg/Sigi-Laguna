<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO"%>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%@ taglib prefix="c"
       uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn"
       uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registrar Funcionario</title>

	<!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/themes/1.8.10/${theme.name}/jquery-ui.css" /-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/${theme.name}/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<%-- <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.weekcalendar.css" /> --%>
	<%-- <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/demo.css" /> --%>

	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	<%-- <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/date.js"></script> --%>
	<%-- <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.weekcalendar.js"></script> --%>
	<%-- <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/demo.js"></script> --%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>

	<!--css para ventanas-->

	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />

	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>


	<script type="text/javascript">

	var contextoPagina = "${pageContext.request.contextPath}";

	var claveOriginalFuncionario="";
	//variable en donde se almacena el id del funcionario para poder registrar el usuario
	var idFuncionario='<%=request.getParameter("idFuncionario")!=null?request.getParameter("idFuncionario"):""%>';
	//variable global para manipular la pesta&ntilde;a de dar de alta un usuario segun de donde se mande a llamar
	var administrador= '<%=request.getParameter("administrador")!=null?request.getParameter("administrador"):""%>';
	//variable global que controla el estado del usuario
	var comparaUsuario= '<%=request.getParameter("comparaUsuario")!=null?request.getParameter("comparaUsuario"):""%>';
	//variable para manipular los datos de medios de contacto
	var idindi;
	//var se utiliza para la modificacion de datos generales
	var modificaGrid=true;
    //var para almacenar el id del usuario
    var idUsuario;
    var texTipoDiscriminante;
	var hashIdAreaConBandera = new Object();


    <%
	UsuarioDTO usuario =  (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	ConfInstitucionDTO institucion = usuario.getInstitucion();
	long valorInstitucion = institucion.getConfInstitucionId();
	%>

	$(document).ready(function() {
		agenciasPGJ();
		//Se asocia funcion al combo de funcionarios
		$('#cbxFuncionario').change(consultaFuncionario);
		$("#idFechaIngreso").attr("disabled","disabled");


		//variables para setear las fechas y horas maximas
		var fechaServidor="";
		var fechaMax="";
		fechaServidor= consultaFechaHoraMaximaServer();
		fechaMax=getFechaMaximaServerHechos(fechaServidor);
		//se asocia date picker para la fecha de nacimiento del funcionario
		$("#idFechaDate").datepicker({
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:+0',
			maxDate: fechaMax,
			changeMonth: true,
			changeYear: true,
			showOn: "button",
			buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
			buttonImageOnly: true
		});

	      $('#tabsconsultaprincipal-3').hide();
		  $('#tabAlta').hide();
		  $('#funcionarioRegistrado').hide();
		  $('#etiquetaFuncionario').hide();

		//Se crean las tabs principales
		$("#tabsprincipalconsulta" ).tabs();

		switch(parseInt(administrador)){
		case 0:
			cargaAreasNegocio();
			cargaEntidadFederativa();
			cargaRegiones();
			cargaPuestos();
			cargaTipoEspecialidad();
			//cargaFuncionarios();
			$("#idFechaDate").attr("disabled","disabled");
			$("#idFechaIngreso").hide();
			$("#fechaIngresoFuncionario").hide();
			$("#fechaIngresoFuncionarioComplemento").show();
			//Funciones agregadas para UIE
			ocultarUIEspecializada();
			$("#cbxAreasDeNegocio").change(controlComboUIE);
			// Redireccionamiento - Se checa el idFuncionario
			// de no ser nulo, implica que se esta en el guardado de un funcionario

			if(idFuncionario!=null && parseInt(idFuncionario)>0){
				$('#tabAlta').show();
		  		$('#tabsconsultaprincipal-3').show();
		  		$.ajax({
				     type: 'POST',
				     url: '<%= request.getContextPath()%>/buscarFuncionarioPorId.do',
					 data: 'funcionarioId='+  + idFuncionario,
					 dataType: 'xml',
					 success: function(xml){
						 pintaDatosFuncionario(xml);
					  }
				});
		 	}

			break;
		case 1:
			cargaAreasNegocio();
			cargaEntidadFederativa();
			cargaRegiones();
			cargaPuestos();
			cargaTipoEspecialidad();
			//cargaFuncionarios();
			$("#idFechaDate").attr("disabled","disabled");
			$("#idFechaIngreso").hide();
			$("#fechaIngresoFuncionario").hide();
			$("#fechaIngresoFuncionarioComplemento").show();
			//Funciones agregadas para UIE
			ocultarUIEspecializada();
			$("#cbxAreasDeNegocio").change(controlComboUIE);
			// Redireccionamiento - Se checa el idFuncionario
			// de no ser nulo, implica que se esta en el guardado de un funcionario

			if(idFuncionario!=null && parseInt(idFuncionario)>0){
				$('#tabAlta').show();
		  		$('#tabsconsultaprincipal-3').show();
		  		$.ajax({
				     type: 'POST',
				     url: '<%= request.getContextPath()%>/buscarFuncionarioPorId.do',
					 data: 'funcionarioId='+  + idFuncionario,
					 dataType: 'xml',
					 success: function(xml){
						 pintaDatosFuncionario(xml);
					  }
				});
		 	}

			break;
		case 2:
			$('#tabsconsultaprincipal-2').hide();
			$('#tabsconsultaprincipal-1').hide();
			$('#tabDatosGenerales').hide();
			$('#tabMedios').hide();
			$('#tabAlta').show();
			$('#tabsconsultaprincipal-3').show();
			$("#tabsprincipalconsulta").tabs("option", "selected", 2);
			$('#funcionarioRegistrado').show();
			$('#etiquetaFuncionario').show();
			break;

		case 3:
			cargaAreasNegocio();
			cargaEntidadFederativa();
			cargaRegiones();
			cargaPuestos();
			cargaTipoEspecialidad();
			//cargaFuncionarios();
			//Funciones agregadas para UIE
			$("#cbxAreasDeNegocio").change(controlComboUIE);
			$("#idFechaDate").attr("disabled","disabled");
			$("#idFechaIngreso").show();
			$("#fechaIngresoFuncionario").show();
			$("#fechaIngresoFuncionarioComplemento").hide();

			// Se checa el idFuncionario, de ser nulo, quiere decir que no se ha seleccionado alguno,
			// siendo as&iacute;, se muestra en pantalla el grid de funcionarios que se pueden modificar

			if(idFuncionario!=null && parseInt(idFuncionario)>0){

				// Tabs de datos generales del funcionario

				$('#tabDatosGenerales').show();
				$('#tabMedios').show();
				$('#tabsconsultaprincipal-1').show();
				$('#consultaFuncionarios').hide();

				//  Recuperaci&oacute;n de datos generales del individuo

				$.ajax({
				     type: 'POST',
				     url: '<%= request.getContextPath()%>/buscarFuncionarioPorId.do',
					 data: 'funcionarioId='+  + idFuncionario,
					 dataType: 'xml',
					 success: function(xml){
						 pintaDatosFuncionario(xml);
					 }
				});

		   		// Se realiza el comparativo para verificar si el funcionario tiene un usuario asociado
		   		// De no ser as&iacute;, se despliega el tab de captura de usuario, y se permite realizar esta
		   		// acci&oacute;n.

				if(comparaUsuario=="false" || comparaUsuario==false){
					$('#tabAlta').show();
		  		 	$('#tabsconsultaprincipal-3').show();
				}
			}
			else{
				$('#tabDatosGenerales').hide();
				$('#tabMedios').hide();
				$('#tabsconsultaprincipal-1').hide();
				$('#consultaFuncionarios').show();
			}
			break;

		case 4:
			break;

		}
	});

	/*
	*Funcion para verificar si la institucion es PGJ y ocultar o mostrar el combo de agencias
	*/
	function agenciasPGJ(){

		var tipoDiscriminante;

		if(<%=valorInstitucion%> == '<%=Instituciones.PGJ.getValorId()%>'){
			tipoDiscriminante= "agencia";
			$('#agenciaTag').show();
			$('#tribunalTag').hide();
			$('#distritoTag').hide();
			texTipoDiscriminante = '<bean:message key="agencia"/>';
		}
		if(<%=valorInstitucion%> == '<%=Instituciones.PJ.getValorId()%>'){
			tipoDiscriminante= "tribunal";
			$('#agenciaTag').hide();
			$('#tribunalTag').show();
			$('#distritoTag').hide();
			texTipoDiscriminante = "tribunal";
		}
		if(<%=valorInstitucion%> == '<%=Instituciones.DEF.getValorId()%>' || <%=valorInstitucion%> == '<%=Instituciones.SSP.getValorId()%>' || <%=valorInstitucion%> == '<%=Instituciones.RS.getValorId()%>'){
			tipoDiscriminante= "fantasma";
			$('#agenciaTag').hide();
			$('#tribunalTag').hide();
			$('#distritoTag').show();
			texTipoDiscriminante = "distrito";
		}
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoAgenciasPGJ.do?tipoDiscriminante='+tipoDiscriminante+'',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				var option;
				$(xml).find('listaCatalogo').find('catDiscriminante').each(function(){
					$('#datosGeneralesCbxAgencia').append('<option value="' + $(this).find('catDiscriminanteId').text() + '">'+ $(this).find('clave').first().text() +"-"+ $(this).find('nombre').first().text() +'</option>');
				});
			}
		});
	}


	/*
	*Funcion que dispara el Action para consultar Tipo Especialidad
	*/
	function cargaTipoEspecialidad(){
		$('#datosGeneralesCmpTipoEspecialidad').empty();
		$('#datosGeneralesCmpTipoEspecialidad').append('<option value="">-Seleccione-</option>');

		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoTipoEspecialidad.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				var option;
				$(xml).find('tipoEspecialidad').each(function(){
					$('#datosGeneralesCmpTipoEspecialidad').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	/*
	*Funcion que dispara el Action para consultar Especialidad
	*/
	function onSelectChangeTipoEspecialidad() {
		$('#datosGeneralesCmpEspecialidad').empty();
		$('#datosGeneralesCmpEspecialidad').append('<option value="">-Seleccione-</option>');

		var selected = $("#datosGeneralesCmpTipoEspecialidad option:selected").val();
		$( "#datosGeneralesCmpEspecialidad" ).attr('selectedIndex',0);
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoEspecialidad.do?tipoEspecialidadId='+selected+'',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('especialidad').each(function(){
					$('#datosGeneralesCmpEspecialidad').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	/*
	*Funcion que dispara el Action para consultar Funcionario
	*/
	/*function cargaFuncionarios(){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoFuncionarios.do',
			data: '',
			dataType: 'xml',
			async: true,
			success: function(xml){
				var option;
				$(xml).find('listaFuncionarios').find('funcionariosRegistrados').each(function(){
					$('#cbxFuncionario').append('<option value="' + $(this).find('claveFuncionario').text() + '">'+ $(this).find('nombreFuncionario').text() + ' '+$(this).find('apellidoPaternoFuncionario').text() + ' '+$(this).find('apellidoMaternoFuncionario').text() +'</option>');
				});
			}
		});
	}*/

	/*
	*Funcion que dispara el Action para consultar Puestos
	*/
	function cargaPuestos(){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoPuestos.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				var option;
				$(xml).find('puestos').each(function(){
					$('#datosGeneralesCmpPuesto').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	/*
	*Funcion que dispara el Action para consultar Areas
	*/
	function cargaAreasNegocio(){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoAreasDeNegocio.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				var option;
				$(xml).find('areas').each(function(){
					$('#cbxAreasDeNegocio').append('<option value="' + $(this).find('catAreasNegocioId').text() + '">'+ $(this).find('nombreCatAreaNegocio').text() + '</option>');
					hashIdAreaConBandera[$(this).find('catAreasNegocioId').text()] = $(this).find('esUIE').text();
				});
			}
		});

		//for (var n in hashIdAreaConBandera) {
		    //if (hashIdAreaConBandera.hasOwnProperty(n)) {
		      //  alert(n + ": " + hashIdAreaConBandera[n] );
		    //}
		//}
	}

	function cargaEntidadFederativa(){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoEntidadFederativa.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				var option;
				$(xml).find('entidades').each(function(){
					$('#comboEntidades').append('<option value="' + $(this).find('entidadFederativaId').text() + '">'+ $(this).find('nombreEntidad').text() + '</option>');

				});
			}
		});
	}

	function cargaRegiones(){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoRegion.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				var option;
				$(xml).find('regiones').each(function(){
					$('#comboRegiones').append('<option value="' + $(this).find('regionId').text() + '">'+ $(this).find('nombre').text() + '</option>');
				});
			}
		});
	}



/************************************************************FUNCIONES PARA UNIDAD ESPECIALIZADA****************************************************/


	/**
	*Funcion que escucha si el usuario seleccion&oacute; una area que tiene UIE, llama el llenado del combo
	*una sola vez, y despues solo lo muestra o lo oculta
	*/
	function controlComboUIE(){

		if(validaSeleccionUIE()){
			mostrarUIEspecializada();
			//Consulta UIE
			consultarCatalogoUIE();
		}else{
			ocultarUIEspecializada();
		}
	}

	/*
	*Funcion que realiza la carga del combo de unidad de investigacion especializada
	*/
	function consultarCatalogoUIE() {

		$('#jerarquiaUIE').empty();
		$('#jerarquiaUIE').append('<option value="nop">- Seleccione -</option>');

		$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/consultarCatalogoUIE.do',
		data: '',
		dataType: 'xml',
		async: false,
		success: function(xml){
			$(xml).find('CatUIEspecializadaDTO').each(function(){
				$('#jerarquiaUIE').append('<option value="'+$(this).find('catUIEId').text() + '">'+$(this).find('claveUIE').text()+" - "+$(this).find('nombreUIE').text()+'</option>');
			});
			}
		});
	}

	/**
	*Funcion que muestra el combo box y la etiqueta de UIE especializada
	*/
	function mostrarUIEspecializada(){
		$('#labelUIE').show();
		$('#jerarquiaUIE').show();
	}

	/**
	*Funcion que oculta el combo box y la etiqueta de UIE especializada
	*/
	function ocultarUIEspecializada(){
		$('#labelUIE').hide();
		$('#jerarquiaUIE').hide();
		$('#jerarquiaUIE').val("nop");
	}

	/*
	*Funcion que valida si esta seleccionada el area de
	*unidad de investigacion o la coordinacion de unidad de investigacion, de ser asi
	*devuelve verdadero, en otro caso, devuelve falso
	*/
	function validaSeleccionUIE(){
		var selected = $("#cbxAreasDeNegocio option:selected").val();
		var respuesta=false;

		if(hashIdAreaConBandera[selected]=='true'){
			respuesta=true;
		}
		return respuesta;
	}

/************************************************************FUNCIONES PARA UNIDAD ESPECIALIZADA****************************************************/



	/**
	 *Limpia todos los campos de esta p&aacute;gina
	 */
	function cleanDatosGenerales(){

		$('#datosGeneralesCmpNumEmpleado').val("");
		$('#datosGeneralesCmpApaterno').val("");
		$('#datosGeneralesCmpMaterno').val("");
		$('#datosGeneralesCmpNombres').val("");
		$('#datosGeneralesCmpCurp').val("");
		$('#datosGeneralesCmpRfc').val("");

		//combos  multi select
		$( "#cbxAreasDeNegocio" ).attr('selectedIndex',0);
		$( "#datosGeneralesCmpPuesto" ).attr('selectedIndex',0);
		$( "#datosGeneralesCmpTipoEspecialidad" ).attr('selectedIndex',0);
		$( "#datosGeneralesCmpEspecialidad" ).attr('selectedIndex',0);
		$( "#datosGeneralesCmpFuncionario" ).attr('selectedIndex',0);
		$( "#comboEntidades" ).attr('selectedIndex',0);
		$( "#comboRegiones" ).attr('selectedIndex',0);
	}

	function recuperaDatosGenerales()
	{
		   var lsDatosGenerales="";
		   lsDatosGenerales+="numeroEmpleado="+$('#datosGeneralesCmpNumEmpleado').val();
		   lsDatosGenerales+="&nombre="+$("#datosGeneralesCmpNombres").val();
		   lsDatosGenerales+="&apellidoPaterno="+$("#datosGeneralesCmpApaterno").val();
		   lsDatosGenerales+="&apellidoMaterno="+$("#datosGeneralesCmpMaterno").val();
		   lsDatosGenerales+="&curp="+$("#datosGeneralesCmpCurp").val();
		   lsDatosGenerales+="&rfc="+$("#datosGeneralesCmpRfc").val();

		   var sexoT = $(':radio[name=rbtSexoDatosGenerales]:checked').val();
	       lsDatosGenerales += '&sexo=' + sexoT;

	       if(administrador==1 || administrador ==0){
	    	   lsDatosGenerales+="&fechaIngreso="+"";
	       }
	       else
	       {
	    	   if($("#idFechaIngreso").val()!=""){
	    	   		lsDatosGenerales+="&fechaIngreso="+$("#idFechaIngreso").val();
	    	   }
	    	   else{
	    		   lsDatosGenerales+="&fechaIngreso="+"SIN FECHA";
	    	   }
	       }

	       lsDatosGenerales+="&fechaNacimiento="+$("#idFechaDate").val();
		   lsDatosGenerales+="&idAreaDeNegocio="+$("#cbxAreasDeNegocio option:selected").val();
		   lsDatosGenerales+="&puesto="+$("#datosGeneralesCmpPuesto option:selected").val();
		   lsDatosGenerales+="&tipoEspecialidad="+$("#datosGeneralesCmpTipoEspecialidad option:selected").val();
		   lsDatosGenerales+="&especialidad="+$("#datosGeneralesCmpEspecialidad option:selected").val();
		   lsDatosGenerales+="&funcionario="+$("#datosGeneralesCmpFuncionario option:selected").val();
		   lsDatosGenerales+="&agenciaId="+$("#datosGeneralesCbxAgencia option:selected").val();

		   lsDatosGenerales+="&entidadFederativaId="+$("#comboEntidades option:selected").val();
		   lsDatosGenerales+="&regionId="+$("#comboRegiones option:selected").val();

			if(validaSeleccionUIE()){
				//Se agrega el parametro UIE
				lsDatosGenerales+="&unidadInvEspId="+$("#jerarquiaUIE option:selected").val();
			}

		   var mediosContacto = obtenerMedios();

		   lsDatosGenerales+=mediosContacto;
		   lsDatosGenerales+="&idFuncionario="+idFuncionario;
		   return lsDatosGenerales;
	}

	function validaObligatorios(){

		if($("#idFechaDate").val() == "undefined/undefined/"){
			alertDinamico("La fecha de nacimiento es incorrecta");
			return false;
		}

		//Se agrega el parametro UIE
		if(validaSeleccionUIE()){
			if($("#jerarquiaUIE option:selected").val() == "nop"){
				alertDinamico("El campo unidad de investigaci&oacute;n especializada es obligatorio.");
		  		return false;
			}
		}

		if('<%=valorInstitucion%>' != '<%=Instituciones.DEF.getValorId()%>'){
			if($("#datosGeneralesCmpEspecialidad").val()== ""){
				alertDinamico("El campo especialidad es obligatorio.");
	  			return false;
			}
		}

		if($('#datosGeneralesCmpNumEmpleado').val() == "" ){
			alertDinamico("El campo n&uacute;mero de empleado es obligatorio.");
	  		return false;
		}

		if ($("#datosGeneralesCmpNombres").val() == "") {
			alertDinamico("El campo nombre es obligatorio.");
			return false;
		}
		if ($("#datosGeneralesCmpApaterno").val() == "") {
			alertDinamico("El campo apellido paterno es obligatorio.");
			return false;
		}
		if ($("#datosGeneralesCmpMaterno").val() == "") {
			alertDinamico("El campo apellido materno es obligatorio.");
			return false;
		}
		if ($("#idFechaDate").val() == "") {
			alertDinamico("El campo fecha de nacimiento es obligatorio.");
			return false;
		}
		if ($("#cbxAreasDeNegocio").val() == "") {
			alertDinamico("El campo &aacute;rea es obligatorio.");
			return false;
		}
		if ($("#datosGeneralesCmpPuesto").val() == "") {
			alertDinamico("El campo cargo es obligatorio.");
			return false;
		}
		if ($("#datosGeneralesCmpTipoEspecialidad").val() == "") {
			alertDinamico("El campo tipo de especialidad es obligatorio.");
			return false;
		}
		if ($("#datosGeneralesCbxAgencia option:selected").val() == "") {

			var textoAlerta = "El campo "+texTipoDiscriminante+" es obligatorio."
			alertDinamico(textoAlerta);
			return false;
		}

		if ($("#comboEntidades").val() == "") {
			alertDinamico("El campo Entidad Federativa es obligatorio.");
			return false;
		}

		if ($("#comboRegiones").val() == "") {
			alertDinamico("El campo Region es obligatorio.");
			return false;
		}

		return true;
	}

	function guardarDatosFuncionario(){
		if(validaObligatorios()){

			var validacion = false;

			validacion = validaNumEmpleado();

			if(validacion){
				var params = recuperaDatosGenerales();
				$.ajax({
			  	  type: 'POST',
			  	  url: '<%= request.getContextPath()%>/guardarFuncionario.do',
			  	  data: params,
			  	  dataType: 'xml',
			  	  success: function(xml){

			  		/* administrador=1 --> el indexAdministradorView lo invoca para: "Registrar Nuevo Funcionario" */
			  		/* administrador=3 --> el indexAdministradorView lo invoca para: "Modificar Funcionario"       */
			  		// Checar las invocaciones al hacer referencia al registrarFuncionario.do

			  		switch(parseInt(administrador)){
					case 0:
						 $('#tabAlta').show();
				  		 $('#tabsconsultaprincipal-3').show();
				  		 idFuncionario = $(xml).find('funcionarioDTO').find('claveFuncionario').text();
						 enviaImagenDeElemento(idFuncionario);
	             		 break;
					case 1:
						 $('#tabAlta').show();
				  		 $('#tabsconsultaprincipal-3').show();
				  		 idFuncionario = $(xml).find('funcionarioDTO').find('claveFuncionario').text();
						 enviaImagenDeElemento(idFuncionario);
	             		 break;
					case 3:
						if(comparaUsuario=="false" || comparaUsuario==false){
						 	$('#tabAlta').show();
				  		 	$('#tabsconsultaprincipal-3').show();
	                     	idFuncionario = $(xml).find('funcionarioDTO').find('claveFuncionario').text();
						}
						enviaImagenDeElemento(idFuncionario);
						break;
					}
			  		alertDinamico("El registro del funcionario fue correcto");
			  	  }
			  	});
			}else{
				// Al ingresar un funcionario, y ya est&aacute; registrado el n&uacute;mero de empleado
				if(parseInt(administrador)==1 || parseInt(administrador)==0 ){
				 	alertDinamico("El n&uacute;mero de empleado ingresado ya existe, verifique su informaci&oacute;n.");
				}
			}
		}
	}

	function validaNumEmpleado(){
		var validacion=false;
		var numeroEmpleado;

		if(parseInt(administrador)==3){
			numeroEmpleado=claveOriginalFuncionario;
   		}
		else{
			numeroEmpleado=$('#datosGeneralesCmpNumEmpleado').val();
		}

		if(numeroEmpleado != ""){
	    	$.ajax({
	    		type: 'POST',
	    	    url: '<%=request.getContextPath()%>/consultarPersonalOperativoIPH.do?numeroEmpleado='+numeroEmpleado+'',
	    	    data: '',
	    	    dataType: 'xml',
	    	    async: false,
	    	    success: function(xml){

				   var claveFuncionario =  $(xml).find('funcionarioDTO').find('claveFuncionario').first().text();

				   // Ingresar un nuevo funcionario DEFENSORIA
				   if(parseInt(administrador)==0){
				   		if(claveFuncionario != null && claveFuncionario != ""){
							   validacion=false;
				   		}else{
							   validacion=true;
				   		}
				   }

				   // Ingresar un nuevo funcionario
				   if(parseInt(administrador)==1){
				   		if(claveFuncionario != null && claveFuncionario != ""){
							   validacion=false;
				   		}else{
							   validacion=true;
				   		}
				   }

				   // Modificar un funcionario
				   else if(parseInt(administrador)==3){
					    if(claveFuncionario != null && claveFuncionario != ""){
							   validacion=true;
			   			}else{
						   	   validacion=false;
			   			}
				   }
			   }
			});
		}

		return validacion;
	}



	function consultaFuncionario(){

		var selected = $("#cbxFuncionario option:selected");
		idFuncionario = selected.val();

		$.ajax({
		     type: 'POST',
		     url: '<%= request.getContextPath()%>/buscarFuncionarioPorId.do',
			 data: 'funcionarioId='+  + selected.val(),
			 dataType: 'xml',
			 success: function(xml){

				 pintaDatosFuncionario(xml);

			  }
			});


		}

	 function pintaDatosFuncionario(xml){

		    $('#datosGeneralesCbxAgencia').attr('selectedIndex',0);

			// El siguiente if, se utiliza para la asociaci&oacute;n de la imagen al funcionario si es que
			// est&eacute; tiene una asociada, en caso contrario, se muestra la imagen default

		    if(idFuncionario!=0){

				$('#tabDatosGenerales').show();
				$('#tabMedios').show();
				$('#tabsconsultaprincipal-1').show();

				$.ajax({
					type: 'POST',
					url: '<%=request.getContextPath()%>/tieneImagenFuncionario.do?idFuncionario='+idFuncionario+'',
					data: '',
					dataType: 'xml',
					async: false,
					success: function(xml){
						var respuesta=$(xml).find('respuesta').text();
						if(respuesta=='1' || respuesta==1){
							$("#imgConFoto").attr("src",'<%=request.getContextPath()%>/obtenImagenDeFuncionario.do?idFuncionario='+idFuncionario+'');
						}
						else{
							$("#imgConFoto").attr("src","<%=request.getContextPath()%>/resources/images/foto.png");
						}
					}
				});

        	}else{
		       $('#tabDatosGenerales').hide();
		       $('#tabMedios').hide();
			   $('#tabsconsultaprincipal-1').hide();
			   $("#imgConFoto").attr("src","<%=request.getContextPath()%>/resources/images/foto.png");
			}

		    idUsuario = $(xml).find('funcionario').find('usuario').find('idUsuario').text();

		    comparaUsuario = $(xml).find('funcionario').find('usuario').find('idUsuario').text()!= "";

			$('#datosGeneralesCmpApaterno').val($(xml).find('apellidoPaternoFuncionario').first().text());
			$('#datosGeneralesCmpMaterno').val($(xml).find('apellidoMaternoFuncionario').first().text());
			$('#datosGeneralesCmpNombres').val($(xml).find('nombreFuncionario').first().text());
			$('#datosGeneralesCmpNumEmpleado').val($(xml).find('numeroEmpleado').first().text());
			claveOriginalFuncionario=$('#datosGeneralesCmpNumEmpleado').val();
			$('#datosGeneralesCmpRfc').val($(xml).find('rfc').first().text());
			$('#datosGeneralesCmpCurp').val($(xml).find('curp').first().text());
			var sexoOp=$(xml).find('sexo').first().text();
			if(sexoOp=='F'){
				$('#datosGeneralesCmpSexoF').attr("checked", true);
			}else{
				$('#datosGeneralesCmpSexoM').attr("checked", true);
			}
			var fecha = $(xml).find('fechaNacimiento ').first().text();
			var strfecha = obtenerFecha(fecha);
			$('#idFechaDate').val(strfecha);
			if($("#idFechaDate").val() == "undefined/undefined/"){ $('#idFechaDate').val(""); }

			fecha = $(xml).find('fechaIngreso ').first().text();
			strfecha = obtenerFecha(fecha);
			$('#idFechaIngreso').val(strfecha);
			if($("#idFechaIngreso").val() == "undefined/undefined/"){ $('#idFechaIngreso').val(""); }

			var tipoEspecialidad=$(xml).find('tipoEspecialidad').find('idCampo').first().text();
			$('#datosGeneralesCmpTipoEspecialidad').find("option[value='"+tipoEspecialidad+"']").attr("selected","selected");
			onSelectChangeTipoEspecialidad();

			var especialidad=$(xml).find('especialidad').find('idCampo').first().text();
			$('#datosGeneralesCmpEspecialidad').find("option[value='"+especialidad+"']").attr("selected","selected");

			var puesto=$(xml).find('puesto').find('idCampo').first().text();
			$('#datosGeneralesCmpPuesto').find("option[value='"+puesto+"']").attr("selected","selected");

 			var area= $(xml).find('funcionario').find('catAreaNegocio').find("catAreasNegocioId").first().text();
			$('#cbxAreasDeNegocio').find("option[value='"+area+"']").attr("selected","selected");

			var agencia = $(xml).find('funcionario').find('discriminante').find('catDiscriminanteId').first().text();
			$('#datosGeneralesCbxAgencia').find("option[value='"+agencia+"']").attr("selected","selected");


		 var entidad = $(xml).find('funcionario').find('entidadFederativaId').first().text();
		 $('#comboEntidades').find("option[value='"+entidad+"']").attr("selected","selected");

		 var region = $(xml).find('funcionario').find('regionId').first().text();
		 $('#comboRegiones').find("option[value='"+region+"']").attr("selected","selected");


		 var uie=$(xml).find('unidadIEspecializada').find('catUIEId').first().text();

			if(validaSeleccionUIE()){
				controlComboUIE();
				$('#jerarquiaUIE').val(uie);
			}
			else{
				ocultarUIEspecializada();
				$('#jerarquiaUIE').val("nop");
			}
			 idindi=idFuncionario;

			disparaConsultaGridsMediosDeContactoFuncionario(idindi);
		 }

		 function obtenerFecha(fecha){
				var fh = fecha.split(" ");
				return fh[0].split("-")[2]+"/"+fh[0].split("-")[1]+"/"+fh[0].split("-")[0];
			}

		 // Funcion que permite guardar una imagen y la asocia a un funcionario
		 function enviaImagenDeElemento(idFuncionario){
				document.frmImagenElemento.idFuncionario.value = idFuncionario;
				document.frmImagenElemento.administrador.value = administrador;
				document.frmImagenElemento.comparaUsuario.value = comparaUsuario;
			    document.frmImagenElemento.submit();
		}

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
	<table align="center"  id="consultaFuncionarios" style="display: none">
		<tr>
			<td>
		    </td>
		</tr>
		<tr>
			<td>
				Selecciona un funcionario:<select id="cbxFuncionario" style="width: 250px;" >
				    <c:forEach items="${applicationScope.funcionarios}"  var="f" >
                             <option value='<c:out value="${f.claveFuncionario}"/>'> <c:out value="${f.nombreFuncionario} ${f.apellidoPaternoFuncionario} ${f.apellidoMaternoFuncionario}"/> </option>
                    </c:forEach>
                </select>
			</td>
		</tr>
	</table>

	<div id="tabsprincipalconsulta">

		<ul>
			<li id="tabDatosGenerales"><a href="#tabsconsultaprincipal-1">Datos Generales</a></li>
			<li id="tabMedios"><a href="#tabsconsultaprincipal-2">Medios de Contacto</a></li>
			<li id="tabAlta"><a href="#tabsconsultaprincipal-3">Dar de alta usuario en el sistema</a></li>
		</ul>

		<div id="tabsconsultaprincipal-1">

			<p align="center">
				</br><b>Los campos marcados con el s&iacute;mbolo (*) en la secci&oacute;n de datos generales son obligatorios.</b>
			</p>

			<table border="0" width="100%">

				<tr>
		 			<td width="12%" height="130" align="center" rowspan=3>
		 			<img src="<%=request.getContextPath()%>/resources/images/foto.png"
		 				alt="" width="105" height="105" id="imgConFoto" />
		 			</td>
		 			<td  align="right">*Nombre(s):</td>
					<td>
						<input type="text" class="" style="width: 180px;" maxlength="50" id="datosGeneralesCmpNombres" name="datosGeneralesCmpNombres" tabindex="1" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/>
					</td>
					<td align="right">*N&uacute;mero de Empleado:</td>
					<td>
						<input type="text" class="" style="width: 180px;" maxlength="13" id="datosGeneralesCmpNumEmpleado" name="datosGeneralesCmpNumEmpleado" tabindex="9"/>
					</td>
		 		</tr>
				<tr>
					<td align="right">*Apellido Paterno:</td>
					<td>
						<input type="text" style="width: 180px;" maxlength="50" class="" id="datosGeneralesCmpApaterno" name="datosGeneralesCmpApaterno" tabindex="2" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/>
					</td>
					<td align="right">*&Aacute;rea:</td>
					<td>
						 <select id="cbxAreasDeNegocio"  name="cbxAreasDeNegocio" style="width: 182px;" tabindex="10">
							<option value="">- Seleccione -</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">*Apellido Materno:</td>
					<td>
						<input type="text" style="width: 180px;" maxlength="50" class="" id="datosGeneralesCmpMaterno"  name="datosGeneralesCmpMaterno" tabindex="3" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/>
					</td>
					<td align="right">*Cargo:</td>
					<td>
						<select id="datosGeneralesCmpPuesto"  name="datosGeneralesCmpPuesto" style="width: 182px;" tabindex="11">
							<option value="">- Seleccione -</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right" rowspan=2>
						<form id="frmImagenElemento" name="frmImagenElemento" action="<%= request.getContextPath() %>/ingresarImagenDelElementoFuncionario.do" method="post" enctype="multipart/form-data" align="left">
							<input type="hidden" name="idFuncionario"/>
							<input type="hidden" name="administrador"/>
							<input type="hidden" name="comparaUsuario"/>
							<input type="file" name="archivo" id="uploadArchivo">
						</form>
					</td>
					<td align="right">CURP:</td>
		 			<td>
		 				<input type="text" style="width: 180px;" maxlength="18" class="" id="datosGeneralesCmpCurp"  name="datosGeneralesCmpCurp" tabindex="4"/>
		 			</td>
		 			<td align="right">*Tipo de Especialidad:</td>
					<td>
						<select id="datosGeneralesCmpTipoEspecialidad" onchange="onSelectChangeTipoEspecialidad();" name="datosGeneralesCmpTipoEspecialidad" style="width: 182px;" tabindex="12">
							<option value="">- Seleccione -</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">RFC:</td>
		 			<td>
		 				<input type="text" class="" style="width: 180px;" maxlength="13" id="datosGeneralesCmpRfc" name="datosGeneralesCmpRfc" tabindex="5"/>
		 			</td>
		 			<td align="right">*Especialidad:</td>
		 			<td>
		 				<select id="datosGeneralesCmpEspecialidad"  name="datosGeneralesCmpEspecialidad" style="width: 182px;" tabindex="13">
		 					<option value="">- Seleccione -</option>
		 				</select>
					</td>
		        </tr>
		        <tr>
		        	<td id="fechaIngresoFuncionarioComplemento">
		        	</td>
		        	<td align="center" id="fechaIngresoFuncionario">
		        		<p>Fecha de Registro:</p>
		        	</td>
					<td align="right">*Sexo:   </td>
					<td>Masculino <input type="radio" id="datosGeneralesCmpSexoM" name="rbtSexoDatosGenerales" value="M" checked="checked"  tabindex="6"/>
						Femenino &nbsp;<input type="radio" id="datosGeneralesCmpSexoF" name="rbtSexoDatosGenerales" value="F"/>
					</td>
					<td align="right">
		 				<span id="agenciaTag">*<bean:message key="agencia"/>:</span>
		 				<span id="tribunalTag">*Tribunal:</span>
		 				<span id="distritoTag">*Distrito:</span>
		 			</td>
		 			<td>
						<select id="datosGeneralesCbxAgencia" style="width: 182px;" tabindex="14">
							<option value="">- Seleccione -</option>
						</select>
					</td>
		 		</tr>
		 		<tr>
		 			<td align="center">
 		 				<input type="text" class="" style="width: 180px;" maxlength="13" id="idFechaIngreso" width="50px" name="idFechaIngreso">
		 			</td>
					<td align="right">*Fecha de Nacimiento:</td>
		 			<td>
		 				<input type="text" class="" style="width: 180px;" maxlength="3" id="idFechaDate" width="50px" name="idFechaDate" tabindex="8"/>
		 			</td>

					<td align="right"><div id="labelUIE" >*Unidad de investigaci&oacute;n Especializada:</div></td>
		 			<td>
						<select id="jerarquiaUIE" style="width: 182px;" tabindex="15">
							<option value="">- Seleccione -</option>
						</select>

						<!--El combo permanece oculto por que no tiene funcionalidad-->
						<select id="datosGeneralesCmpFuncionario"  name="datosGeneralesCmpFuncionario" style="width: 182px; display: none;" tabindex="14">
							<option value="">- Seleccione -</option>
						</select>
		 			</td>
				</tr>


				<tr>
					<td >
					</td>
					<td align="center" id="idsp2">
						<p>* Entidad Federativa:</p>
					</td>
					<td align="center">
						<select id="comboEntidades"  name="comboEntidades" style="width: 182px;" tabindex="15">
							<option value="">- Seleccione -</option>
						</select>
					</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>

				<tr>
					<td id="idsp3">
					</td>
					<td align="center" id="idsp4">
						<p>* Region:</p>
					</td>
					<td align="center">
						<select id="comboRegiones"  name="comboRegiones" style="width: 182px;" tabindex="16">
							<option value="">- Seleccione -</option>
						</select>
					</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>

				<tr>
					<td>
					</td>
					<td>
					</td>
					<td colspan="2" align="right">

					</td>
					<td>

					</td>
				</tr>
		 	</table>
		 </div>

		 <div id="tabsconsultaprincipal-2">

			<table width="762px" height="313px" border="0" cellspacing="0" cellpadding="0">
		 		<tr>
		 			<td>
		 				<jsp:include page="ingresarMediosContactoView.jsp"/>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td align="center">
		 				<div id="boton2"><input type="button" value="Guardar" id="botonGuarda" class="ui-button ui-corner-all ui-widget" onclick="guardarDatosFuncionario();"/></div>
		 			</td>
		 		</tr>
		 	</table>

		</div>

		<div id="tabsconsultaprincipal-3">

		 	<table width="700px" align="center">
		 		<tr>
		 			<td>
		 				<jsp:include page="darDeAltaUsuarioEnSistema.jsp"/>
		 			</td>
		 		</tr>
		 	</table>

		</div>
	</div>

</body>
</html>
