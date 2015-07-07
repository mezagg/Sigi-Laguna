<%@page import="mx.gob.segob.nsjp.comun.enums.elemento.TipoElemento"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.catalogo.EntidadFederativa"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.objeto.Objetos" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ingresar Aeronave</title>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/estilos.css"/>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>		
	
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
    
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script src="<%=request.getContextPath()%>/js/objetos.js"></script>
	
	
	<!--script de windows engine (frames)-->
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	
	
	
	<%
	String rolActivo = "";
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO"); 
	if (usuario != null 
			&& usuario.getRolACtivo() != null 
			&& usuario.getRolACtivo().getRol() != null
			&& usuario.getRolACtivo().getRol().getRolId() != null){
		rolActivo = usuario.getRolACtivo().getRol().getRolId().toString();
	}
	%>
	
	<script type="text/javascript">
	
	var idAeronave="";
	var tipoObjeto="";
	var numeroExpediente="";
	var cadenaCustodia="";
	var fechaLevCadCus="";
	var origenEvdCadCus="";
	var deshabilitarCampos = window.parent.deshabilitarCamposPM;
	var ocultaAnularObjetoCadCus=null;
	
	var contextoPagina = "${pageContext.request.contextPath}";
	
	var esModoConsulta = '<%= request.getParameter("esModoConsulta")%>';
	var rolActivo = '<%=rolActivo%>';
	var entidadFedYuc = '<%=EntidadFederativa.YUCATAN.getValorId()%>';
	var elementoID=<%= request.getParameter("idAeronave")%>;
	//Permite saber si la pantalla del objeto es nueva o proviene de una consulta(idElemento mayor a cero)
	var elementoIDOriginal=<%= request.getParameter("idAeronave")%>;

	
	jQuery().ready(		
		function () {
			
			if(elementoID!=null && elementoID!=0){
				$("#btnAdjuntarImagen").show();
				//elementoID debe de vivir en el papa
				cargaGridArchivosDigitalesXElemento(elementoID);
			}
			
			//Instruccion pensada solo para el caso de policia ministerial
			if(deshabilitarCampos == true){
				$(":enabled").attr('disabled','disabled');
			}	
			//seteamos las variables globales
			numeroExpediente='<%= request.getParameter("numeroExpediente")%>';
			idAeronave='<%= request.getParameter("idAeronave")%>';
			cadenaCustodia='<%= request.getParameter("cadenaCustodia")%>';
			fechaLevCadCus='<%= request.getParameter("fechaLevCadCus")%>';
			origenEvdCadCus='<%= request.getParameter("origenEvdCadCus")%>';
						
			if(idAeronave=='null')
			{
				idAeronave=0;	
			}
			
			//lineas para ocultar la opcion de anular objeto alconsultar desde una cadena de custodia
			//Viene con valor a 1 desde asentarRegCadenaCustodiaView.jsp
			//No es setteado en el menu intermedio
			ocultaAnularObjetoCadCus='<%= request.getParameter("anularConsultaCadCus")%>';
			
			if(parseInt( ocultaAnularObjetoCadCus ) == 1){
				$("#anularElemento").hide("")
			}

			
			$("#gridCatTipoEstudioTD").hide("");
			
			tipoObjeto='<%= request.getParameter("tipoObjeto")%>';
			
			cargaTiposAeronave();
			$('#cbxTipoAeronave').change(cargaMarcasAeronave);
			$('#cbxMarcaAeronave').change(cargaSubMarcasAeronave);
			cargaColores();
			cargaPaises();
			cargaCondicion();
			cargaRelacionesHecho('<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getEntidadFederativaDespliegueId() %>');
			var num=parent.num;
			//revisamos si es una consulta
			if(idAeronave!=null && idAeronave!=0)
			{
				consultaAeronave();
				if(existeCadenaDeCustodiaPorIdObjeto(idAeronave))
				{
					$("#anularElemento").hide("");
					
					//Se muestra el grid de Tipo de Estudio
	    			tipoObjetoId= '<%= Objetos.AERONAVE.getValorId() %>';
	    			$("#gridCatTipoEstudioTD").show("");
					consultarTipoEstudio(tipoObjetoId);
					
					if(existenEslabonesPorIdObjeto(idAeronave)){
						ocultaBotonesGenerico();
						inhabilitaCamposGenerico();
					}
				}
			}
			else
			{
				if(num!=null && num!="0"){
					$("#anularElemento").hide();
				}
			}
			
			if(esModoConsulta != null && esModoConsulta == "1"){
				ocultaBotonesGenerico();
				inhabilitaCamposGenerico();
			}
			
			
			if(rolActivo == '<%=Roles.PROCURADOR.getValorId()%>' || rolActivo == '<%=Roles.SUBPROCURADOR.getValorId()%>' ||
					rolActivo == '<%=Roles.DIRECTOR_GENERAL.getValorId()%>' || rolActivo == '<%=Roles.DIRECTOR_UI.getValorId()%>' || 
					rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>'){
				$(":enabled").attr('disabled','disabled');
				$('input[type="submit"]').hide();
				$('input[type="button"]').hide();
			}
			if (rolActivo == '<%=Roles.DEFENSOR.getValorId()%>' || rolActivo == '<%=Roles.DEFENSORATE.getValorId()%>' || rolActivo == '<%=Roles.COORDINADORDEF.getValorId()%>' ){
				$("#anularElemento").hide();
			}
	});

	/*
	*Funcion para mandar consultar el aeronave
	*/
	function consultaAeronave()
	{
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/ConsultaObjetoPorId.do',
    		data: 'idObjeto='+idAeronave,
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			if(parseInt($(xml).find('code').text())==0)
	    		{
    				//seteamos la informacion del hecho
    				$(xml).find('AeronaveDTO').each(function(){
    						seteaDatosAeronave($(this));
		    	      });
	    		}
    		}	
    	});
	}
	
	function seteaDatosAeronave(xml)
	{
		$('#cbxTipoAeronave').find("option[value='"+$(xml).find('tipoAeroNave').find('idCampo').text()+"']").attr("selected","selected");
		cargaMarcasAeronave();
		$('#cbxMarcaAeronave').find("option[value='"+$(xml).find('marca').find('idCampo').text()+"']").attr("selected","selected");
		cargaSubMarcasAeronave();
		$('#cbxSubMarcaAeronave').find("option[value='"+$(xml).find('subMarca').find('idCampo').text()+"']").attr("selected","selected");
		$('#cbxPaisOrigenAeronave').find("option[value='"+$(xml).find('paisProcedencia').find('idCampo').text()+"']").attr("selected","selected");
		$('#cbxColorAeronave').find("option[value='"+$(xml).find('color').find('idCampo').text()+"']").attr("selected","selected");
		$('#cbxCondicionAeronave').find("option[value='"+$(xml).find('valorByCondicionFisicaVal').find('idCampo').text()+"']").attr("selected","selected");
		$('#cbxRelacionHecho').find("option[value='"+$(xml).find('relacionHechoVal').find('idCampo').text()+"']").attr("selected","selected");
		$('#txtModeloAeronave').val($(xml).find('modelo').text());
		$('#txtMatAeronave').val($(xml).find('matricula').text());
		$('#txtNoSerieAeronave').val($(xml).find('noSerie').text());
		$('#txtNoMotorAeronave').val($(xml).find('noMotor').text());
		if($(xml).find('almacen')){
			$(xml).find('almacen').remove();
		}
		$('#txtBoxDescAeronave').val($(xml).find('descripcion').text());
	}
	
   /**
	*Funcion que limpia los combos que dependen del combo tipo de aeronave
	*/
	function limpiaCombosDependsTipo(){
		$('#cbxMarcaAeronave').empty();
		$('#cbxMarcaAeronave').append('<option value="-1">-Seleccione-</option>');	//Por default seleccione
		$('#cbxSubMarcaAeronave').empty();
		$('#cbxSubMarcaAeronave').append('<option value="-1">-Seleccione-</option>');	//Por default seleccione
	}


	/**
	*Funcion que limpia los combos que dependen del combo tipo de la marca de la aeronave
	*/
	function limpiaCombosDependsMarca(){
		$('#cbxSubMarcaAeronave').empty();
		$('#cbxSubMarcaAeronave').append('<option value="-1">-Seleccione-</option>');	//Por default seleccione
	}
		

	/*
	*Funcion que realiza la carga del combo de tipos de aeronave
	*/
	function cargaTiposAeronave() {
		  
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarTiposAeronave.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catTiposAeronave').each(function(){
					$('#cbxTipoAeronave').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	
	/*
	*Funcion que realiza la carga del combo de marcas de aeronave
	*/
	function cargaMarcasAeronave() {

		var selected = $("#cbxTipoAeronave option:selected");
		
		limpiaCombosDependsTipo();
		
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarMarcasAeronave.do',
			data: 'glTipoAeronaveId='+selected.val(),
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catMarcasAeronave').each(function(){
					$('#cbxMarcaAeronave').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					$('#cbxMarcaAeronave').multiselect('refresh');
						});
			}
		});
	}

	/*
	*Funcion que realiza la carga de  sub marcas de aeronave 
	*/
	function cargaSubMarcasAeronave() {

		var selected = $("#cbxMarcaAeronave option:selected");

		limpiaCombosDependsMarca();
		
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarSubMarcasAeronave.do',
			data: 'glMarcaAeronaveId=' + selected.val(),
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catSubMarcasAeronave').each(function(){
					$('#cbxSubMarcaAeronave').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					$('#cbxSubMarcaAeronave').multiselect('refresh');
						});
			}
		});
	}



	
	/*
	*Funcion que realiza la carga de colores de la aeronave
	*/
	function cargaColores() {
		  
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarColores.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catColores').each(function(){
					$('#cbxColorAeronave').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	
	/*
	*Funcion que realiza la carga del combo de Paises
	*/
	function cargaPaises() {
		  
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarPaises.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catPaises').each(function(){
					$('#cbxPaisOrigenAeronave').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	
	/*
	*Funcion que realiza la carga de la condicion de la aeronave
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
					$('#cbxCondicionAeronave').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}	

//	COMIENZA FUNCIONES PARA EL GUARDADO DE LOS DATOS		
	function obtenerValoresAeronave(){
				
		var paramAeronave = "idAeronave="+idAeronave; 
		paramAeronave += '&glTipoAeronaveId=' + $("#cbxTipoAeronave option:selected").val();
		paramAeronave += '&glMarcaAeronaveId=' +  $("#cbxMarcaAeronave option:selected").val();
		paramAeronave += '&glSubMarcaAeronaveId=' + $("#cbxSubMarcaAeronave option:selected").val(); 
		paramAeronave += '&gcModeloAeronave=' + $('#txtModeloAeronave').val();
		paramAeronave += '&glPaisProcedAeronaveId=' + $("#cbxPaisOrigenAeronave option:selected").val();
		paramAeronave += '&glColorAeronaveId=' + $("#cbxColorAeronave option:selected").val();
		paramAeronave += '&gcMatriculaAeronave=' + $('#txtMatAeronave').val();
		paramAeronave += '&gcNoSerieAeronave=' + $('#txtNoSerieAeronave').val();
		paramAeronave += '&gcNoMotorAeronave=' + $('#txtNoMotorAeronave').val();
		paramAeronave += '&glCondicionAeronaveId=' + $("#cbxCondicionAeronave option:selected").val();
		paramAeronave += '&gcDescripcionAeronave=' + $("#txtBoxDescAeronave").val();
		paramAeronave += '&relacionHechoId=' + $("#cbxRelacionHecho option:selected").val();
		
		if(cadenaCustodia!='null')
		{
			paramAeronave += '&cadenaCustodia=' + cadenaCustodia;
			paramAeronave += '&origenEvdCadCus=' + origenEvdCadCus;
			paramAeronave += '&fechaLevCadCus=' + fechaLevCadCus;
		}
		
		$.ajax({
		
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/ingresarAeronave.do?numeroExpediente='+numeroExpediente +'',
			data: paramAeronave,
			dataType: 'xml',
			success: function(xml){
				 var tipoAeronave = $("#cbxTipoAeronave option:selected").text();
				  var id = $(xml).find('AeronaveForm').find('idAeronave').text();

				  //Se ha agregado nuevo elemento
				  if(idAeronave==0 && id>0)
				  {	  
					  //Desde asentarRegCadenaCustodiaView.jsp 
					  if(parseInt( ocultaAnularObjetoCadCus ) == 1){
						  customAlert("Se guardó correctamente la información");
						  regresarControlCadenaCustodia();
					  }else //Desde el ingresarMenuIntermedio.jsp
					  {
						  window.parent.customAlert('Se guardó correctamente la información.', '',regresarControl(id,tipoAeronave) );
					  }
				  }
				  else if(idAeronave==0 && id==0)
				  {
					  window.parent.customAlert("Favor de revisar la información capturada");
				  }
				  else  //Actualizacion del elemento - solo desde el ingresarMenuIntermedio.jsp 
				  {   
					//Desde asentarRegCadenaCustodiaView.jsp 
					  if(parseInt( ocultaAnularObjetoCadCus ) == 1){
						  regresarControlCadenaCustodiaActualizacion();
						  customAlert("La información se actualizó correctamente");
					  }else //Desde el ingresarMenuIntermedio.jsp
					  {
						  window.parent.customAlert('La información se actualizó correctamente.', '',regresarControl(id,tipoAeronave) );
					  }
				  }
				  
				  elementoID = id;
				  idAeronave = id;
				  
			}
		});
	}

	/**
	* Funcion que permite controlar y evitar que el alert desaparecas y se cierre la ventana
	* solo si viene desde asentarRegCadenaCustodia.sjp
	*/
	function regresarControlCadenaCustodia(){
		if(elementoIDOriginal==0){
			$("#btnAdjuntarImagen").fadeIn(1000);
			$("#btnGuardarAeronave").fadeOut(1000);
		}
		window.parent.cargaObjetoCerrarVentana();
	}
	/**
	* Funcion que permite controlar y evitar que el alert desaparecas y se cierre la ventana
	* solo si viene desde ingresarMenuIntermedio.jsp
	*/	
	function regresarControl(id,tipoAeronave){
		if(elementoIDOriginal==0){
			$("#btnAdjuntarImagen").fadeIn(1000);
		}
		
		window.parent.cargaAeronave(id,tipoAeronave);
	}

	function validaCamposObligatorios(){
		var tipoAeronave = $("#cbxTipoAeronave option:selected").val();
		var marcaAeronave = $("#cbxMarcaAeronave option:selected").val();
		var subMarcaAeronave = $("#cbxSubMarcaAeronave option:selected").val();
		var colorAeronave = $("#cbxColorAeronave option:selected").val();
		var paisOrigenAeronave = $("#cbxPaisOrigenAeronave option:selected").val();
		var condicionAeronave = $("#cbxCondicionAeronave option:selected").val();
		var mensaje = "";
		//Primera validacion por cada campo obligatorio		
		if(parseInt(tipoAeronave) == -1){
			mensaje += "<br />- Tipo de la aeronave";			
		}
		if(parseInt(marcaAeronave) == -1){
			mensaje += "<br />- Marca de la aeronave";			
		}
		if(parseInt(subMarcaAeronave) == -1){
			mensaje += "<br />- Submarca de la aeronave";			
		}
		if(parseInt(colorAeronave) == -1){
			mensaje += "<br />- Color de la aeronave";			
		}
		if(parseInt(paisOrigenAeronave) == -1){
			mensaje += "<br />- País de origen de la aeronave";			
		}
		if(parseInt(condicionAeronave) == -1){
			mensaje += "<br />- Condición de la aeronave";			
		}

		//Comienza segunda validacion para validacion de consistencia de expresiones regulares
		if(mensaje != ""){
			//mensaje += "\n\nEs necesario...."; FUTURAS VALIDACIONES
			if(parseInt( ocultaAnularObjetoCadCus ) ==1)
			{// alert especial cuando agregamos el objeto como evidencia en la cadena de custodia
				window.parent.alertDinamicoEvCadCus("<p align='left'>Favor de revisar los siguientes campos obligatorios: <br />"+mensaje+"</p>");
			}
			else
			{
				window.parent.customAlert("<p align='left'>Favor de revisar los siguientes campos obligatorios: <br />"+mensaje+"</p>");
			}
		}else{			
			obtenerValoresAeronave();
		}
		
	}
	
	/*
	*Funcion que manda a eliminar logicamente el objeto en la BD
	*/
	function anularObjeto(){
		var paramAeronave="idObjeto="+idAeronave;
		$.ajax({		
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/anularObjetoPorId.do',
			data: paramAeronave,
			dataType: 'xml',
			success: function(xml){
				//procederemos a tratar de eliminar la evidencia
				if(parseInt($(xml).find('bandera').text())==1)
				{
					//se anulo exitosamente el objeto , actualizamos el grid de menu intermedio y cerramos la ventana
					window.parent.cargaAeronave(idAeronave,"");
					window.parent.cerrarVentanaAeronave();
				}
				if(cadenaCustodia=='null'){
					//alert("cadCus::"+cadenaCustodia);
					window.parent.customAlert($(xml).find('mensajeOp').text());
				}
			}
		});

	}
	
	/*
	*Funcion para anular el objeto
	*/
	function solicitarAnlrObjeto(){
		//revisamos si es una consulta
		if(idAeronave!=null && idAeronave!=0)
		{
			//procederemos a tratar de eliminar la evidencia
			customConfirm ("¿Está seguro que desea anular el objeto?", "", validarObjEvdncNoEslbns);
		}
	}
	
	/*
	*Funcion que validara si el objeto es evidencia y NO tiene eslabones, de ser asi
	*se debe confirmar que se desea eliminar dado que el objeto ya esta en una cadena de custdia
	*/
	function validarObjEvdncNoEslbns()
	{
		var paramAeronave="idObjeto="+idAeronave;
		$.ajax({		
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/validarObjPorIdEvdncNoEslbns.do',
			data: paramAeronave,
			dataType: 'xml',
			success: function(xml){
				//procederemos a tratar de eliminar la evidencia
				if(parseInt($(xml).find('bandera').text())==1)
				{
					//debemos mostrar un confirm
					customConfirm ("El objeto es ya una evidencia en alguna cadena de custodia <br/> ¿Está seguro que desea anular el objeto?", "", anularObjeto);
				}
				else if(parseInt($(xml).find('bandera').text())==2)
				{
					//se puede eliminar el objeto sin problemas
					anularObjeto();
				}
				else
				{
					window.parent.customAlert($(xml).find('mensajeOp').text());
				}
			}
		});
	}
	
	window.onload = function(){
		var selects = document.getElementsByTagName("textarea");
		for (var i = 0; i < selects.length; i++) { 
			if(selects[i].getAttribute("maxlength") > 0){
				selects[i].onkeydown = function(){
	                            if (this.value.length > this.getAttribute("maxlength")) 
	                                this.value = this.value.substring(0, this.getAttribute("maxlength"));
	                        }
	                        selects[i].onblur = function(){
	                            if (this.value.length > this.getAttribute("maxlength")) 
	                                this.value = this.value.substring(0, this.getAttribute("maxlength"));
	                        }
			}
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
        	<table width="560px" height="448" border="0" cellspacing="0" cellpadding="0" align="center">
		  	 <tr><td>&nbsp;</td></tr>				   
             <tr height="6.25%">
                <td height="21" colspan="3" align="center">
                  <input type="button" value="Anular objeto" class="btn_Generico" id="anularElemento" onclick="solicitarAnlrObjeto()"/>&nbsp;&nbsp;
                  <input type="button" id="btnGuardarAeronave" onclick="validaCamposObligatorios();" value="Guardar" class="btn_Generico" />
                                  
                  &nbsp;&nbsp;<button value="Adjuntar imagen" id="btnAdjuntarImagen" class="btn_Generico" onclick="abreVentanaAdjuntarImagen()" style="display:none">Adjuntar imagen</button>
                  </td>
                  
              </tr>
              <tr><td>&nbsp;</td></tr>
              <tr height="12.5%">
                <td width="28%" align="right">Tipo:</td>
                <td width="36%"><select id="cbxTipoAeronave" style="width:180px">
                  <option value="-1">-Seleccione-</option>
                </select></td>
                <td width="36%" rowspan="7" align="center" valign="top">Descripción:<textarea cols="25" rows="9" id="txtBoxDescAeronave" maxlength="200"></textarea></td>               
              </tr>
              <tr height="6.25%">
                <td width="28%" align="right">Marca:</td>
                <td width="36%"><select id="cbxMarcaAeronave" style="width:180px">
                  <option value="-1">-Seleccione-</option>
                </select></td>
              </tr>
              <tr height="6.25%">
                <td width="28%" align="right">Submarca:</td>
                <td width="36%"><select id="cbxSubMarcaAeronave" style="width:180px">
                  <option value="-1">-Seleccione-</option>
                </select></td>
              </tr>
              <tr height="6.25%">
                <td width="28%" align="right">Color:</td>
                <td width="36%"><select id="cbxColorAeronave" style="width:180px">
                  <option value="-1">-Seleccione-</option>
                </select></td>
              </tr>
              <tr height="6.25%">
                <td width="28%" align="right">Pa&iacute;s de Origen:</td>
                <td width="36%"><select id="cbxPaisOrigenAeronave" style="width:180px">
                  <option value="-1">-Seleccione-</option>
                </select></td>
              </tr>
              <tr height="6.25%">
                <td width="28%" align="right">Modelo: </td>
                <td width="36%"><input type="text" id="txtModeloAeronave" maxlength="25" style="width:175px"/></td>
              </tr>
              <tr height="6.25%">
                <td width="28%" align="right">Matrícula:</td>
                <td width="36%"><input type="text" id="txtMatAeronave" maxlength="15" style="width:175px"/></td>
              </tr>
              <tr height="6.25%">
                <td width="28%" align="right">No. de Motor:</td>
                <td width="36%"><input type="text" id="txtNoMotorAeronave" maxlength="20" style="width:175px"/></td>
              </tr>
              <tr height="6.25%">
                <td width="28%" height="24" align="right">No. de Serie:</td>
                <td width="36%"><input type="text" id="txtNoSerieAeronave" maxlength="20" style="width:175px"/></td>
              </tr>              
              <tr height="6.25%">
                <td width="28%" height="36" align="right">Condici&oacute;n:</td>
                <td width="36%"><select id="cbxCondicionAeronave" style="width:180px">
                  <option value="-1">-Seleccione-</option>
                </select></td>
              </tr>
              <tr id="trRelacionHecho" height="12.5%">
				    	<td align="right">Relaci&oacute;n del hecho: </td>
				    	<td >
				    		<select name="cbxRelacionHecho" id="cbxRelacionHecho" style="width:180px">
				    			<option value="-1">-Seleccione-</option>
				    		</select>
				    	</td>
				    </tr>
            </table>
<table width="450px" border="0" cellpadding="0" cellspacing="0">
			
</table>
		
		
	<!--  Muestra las imanenes asociadas al objeto asi como el grid del tipo de estudio -->	
	<jsp:include page="/WEB-INF/paginas/detalleObjeto.jsp" flush="true"></jsp:include>

</body>
</html>