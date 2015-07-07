<%@page import="mx.gob.segob.nsjp.comun.enums.elemento.TipoElemento"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.objeto.Objetos" %>
<%@page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<title>Ingresar veh&iacute;culo</title>

	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/estilos.css"/>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/cssGrid/ui.jqgrid.css"  />
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>

	<!-- JS para la validacion de solo numeros -->
	<script src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>	
	<script src="<%=request.getContextPath()%>/js/objetos.js"></script>
	

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
	var idVehiculo="";
	var tipoObjeto="";
	var numeroExpediente="";
	var cadenaCustodia="";
	var fechaLevCadCus="";
	var origenEvdCadCus="";
	var deshabilitarCampos = window.parent.deshabilitarCamposPM;
	var ocultaAnularObjetoCadCus=null;
	
	var contextoPagina = "${pageContext.request.contextPath}";

	var sistemaTrad=false; 
	
	var esModoConsulta = '<%= request.getParameter("esModoConsulta")%>';
	
	var rolActivo = '<%=rolActivo%>';
	var entidadFedYuc = '';
	var elementoID=<%= request.getParameter("idVehiculo")%>;
	//Permite saber si la pantalla del objeto es nueva o proviene de una consulta(idElemento mayor a cero)
	var elementoIDOriginal=<%= request.getParameter("idVehiculo")%>;

	var fechaServidor="";
	var fechaMax="";
	var timeMax="";
	
	jQuery().ready(			
		function () {
			
			if(elementoID!=null && elementoID!=0){
				$("#btnAdjuntarImagen").show();
				cargaGridArchivosDigitalesXElemento(elementoID);
			}
			
			//Instruccion pensada solo para el caso de policia ministerial
			$('#cbxTipoVehiculo').change(cargaMarcasVehiculo);
			$('#cbxMarcaVehiculo').change(cargaSubMarcasVehiculo);
				
			cargaTiposVehiculo();
			cargaRelacionesHecho();
			$("#idRelacionHechoRecuperado").hide();
			$("#idRelacionHechoEntregado").hide();
			$("#trCausaRecuperado").hide();
			$("#tdCausaRecuperadoOtros").hide();
			
			fechaServidor= consultaFechaHoraMaximaServer();
			fechaMax=getFechaMaximaServerHechos(fechaServidor);
			timeMax=getHoraMaximaServer(fechaServidor);
			
			$("#idFechaRecuperado").datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '-111:+0',
				maxDate: fechaMax,
				onSelect: function(date) {
					$("#idHora").val(timeMax);
					revisaLongitudFechasEspecifica();
				}, 
				changeMonth: true,
				changeYear: true,
				showOn: "button",
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true			
			});
			
			$("#idFechaEntrega").datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '-111:+0',
				maxDate: fechaMax,
				onSelect: function(date) {
					$("#idHora").val(timeMax);
					revisaLongitudFechasEspecifica();
				}, 
				changeMonth: true,
				changeYear: true,
				showOn: "button",
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true			
			});

			

			numeroExpediente='<%= request.getParameter("numeroExpediente")%>';
			idVehiculo=parseInt('<%= request.getParameter("idVehiculo")%>');

			cadenaCustodia='<%= request.getParameter("cadenaCustodia")%>';
			fechaLevCadCus='<%= request.getParameter("fechaLevCadCus")%>';
			origenEvdCadCus='<%= request.getParameter("origenEvdCadCus")%>';
			sistemaTrad='<%= request.getParameter("sistemaTrad")%>';
			
			if(idVehiculo=='null' || idVehiculo==null)
			{
				idVehiculo=0;
				
			}
			
			//Ocultar reincidencia
			if (sistemaTrad=='true') {
				$("#btnBuscarReincidencia").hide("");
				$("#btnConsultarExpedientes").hide("");
			}
			tipoObjeto='<%= request.getParameter("tipoObjeto")%>';
			
			cargaColores();
			cargaPaises();
			cargaCondicion();
			cargarCausaVehiculo();
			var num=window.parent.num;
			
			//lineas para ocultar la opcion de anular objeto alconsultar desde una cadena de custodia
			//Viene con valor a 1 desde asentarRegCadenaCustodiaView.jsp
			//No es setteado en el menu intermedio
			ocultaAnularObjetoCadCus='<%= request.getParameter("anularConsultaCadCus")%>';
			
			if(parseInt( ocultaAnularObjetoCadCus ) == 1){
				$("#anularElemento").hide("")
			}
			
			$("#gridCatTipoEstudioTD").hide("");

			//revisamos si es una consulta
			if(idVehiculo!=null && idVehiculo!=0)
			{//esto se ejecuta cuando llegamos desde cadena de custodia
				consultaVehiculo();
				
				if(existeCadenaDeCustodiaPorIdObjeto(idVehiculo))
				{
					$("#anularElemento").hide("");
					
					//Se muestra el grid de Tipo de Estudio
	    			tipoObjetoId= '<%= Objetos.VEHICULO.getValorId() %>';
	    			$("#gridCatTipoEstudioTD").show("");
					consultarTipoEstudio(tipoObjetoId);
					
					if(existenEslabonesPorIdObjeto(idVehiculo)){
						ocultaBotonesGenerico();
						inhabilitaCamposGenerico();
					}
				}
				
			}
			else{
				if(num!=null && num!="0")
				{
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
				$("#btnBuscarReincidencia").show();
				$("#btnConsultarExpedientes").show();
				$("#btnBuscarReincidencia").attr('disabled','');
				$("#btnConsultarExpedientes").attr('disabled','');
			}
			if (rolActivo == '<%=Roles.DEFENSOR.getValorId()%>' || rolActivo == '<%=Roles.DEFENSORATE.getValorId()%>' 
					|| rolActivo == '<%=Roles.COORDINADORDEF.getValorId()%>'){
				$("#anularElemento").hide();
				$("#btnBuscarReincidencia").hide();
				$("#btnConsultarExpedientes").hide();
				
			}else if (rolActivo == '<%=Roles.ENCARGADOCAUSA.getValorId()%>'){
				$("#btnBuscarReincidencia").hide();
				$("#btnConsultarExpedientes").hide();
			} 
			
			
			if(isEmpty(deshabilitarCampos)){
				deshabilitarCampos = false;
			}
			if(deshabilitarCampos == true){
				$(":enabled").attr('disabled','disabled');
				if (rolActivo == '<%=Roles.POLICIAMINISTER.getValorId()%>'){
					$("#btnBuscarReincidencia").removeAttr('disabled');
					$("#btnConsultarExpedientes").removeAttr('disabled');	
				}
					
			}
			
	});
	
	//Llena el grid con los resultados de la busqueda
	var banderaCargarORecargar=0;
	function cargaGridReincidenciaElemento() {
		var noPlacas = $('#txtPlacasVehiculo').val();	
		var noSerie = $('#txtSerieVehiculo').val();
		if(noPlacas == "")
			noPlacas = null
		if(noSerie == "")
			noSerie = null
			
		if(noSerie == null && noPlacas == null){
			window.parent.customAlert("Es necesario escribir el No. de Placas y el No. de Serie para consultar las reincidencias");
		}
		else{
			if (rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>'){
				muestraPopupReincidenciaElementoConsulta();					
			}
			else{
				muestraPopupReincidenciaElemento();
			}
			
			//Inicia grid
			if(banderaCargarORecargar==0){
				jQuery("#gridReincidenciaElemento").jqGrid({
					url:'<%=request.getContextPath()%>/buscarReincidenciaDeVehiculo.do?noPlacas='+noPlacas +'&noSerie='+noSerie+'&numeroExpediente='+ numeroExpediente+'&idVehiculo='+idVehiculo+'',
								datatype: "xml",
								colNames:['Numero General del caso','Fecha de Apertura','Estatus'],
								colModel:[ 	{name:'NumeroGeneralCaso',index:'numeroGeneralCaso', width:200},
											{name:'FechaApertura',index:'fechaApertura', width:120},							
											{name:'Estatus',index:'estatus', width:100}
											],
					pager: jQuery('#paginadorReincidenciaElemento'),
					rowNum:10,
					rowList:[10,20,30],
					autowidth: false,
					autoheight:false,
					sortname: 'FechaApertura',
					viewrecords: true,
					multiselect: true,
					sortorder: "desc"
				}).navGrid('#paginadorReincidenciaElemento',{edit:false,add:false,del:false});
	        	banderaCargarORecargar=1;
			}
			else{
		    	jQuery("#gridReincidenciaElemento").jqGrid('setGridParam',{
		    		url:'<%=request.getContextPath()%>/buscarReincidenciaDeVehiculo.do?noPlacas='+noPlacas +'&noSerie='+noSerie+'&numeroExpediente='+ numeroExpediente+'&idVehiculo='+idVehiculo+'',
		    				datatype: "xml" });
				$("#gridReincidenciaElemento").trigger("reloadGrid");
		    }//Fin grid
		}
	}
	
	
	//Llena el grid con los resultados de la busqueda de las relaciones entre un elemento y los casos registrados en BD
	var banderaCargarORecargarGrid2=0;
	function cargaGridReincidenciasXElemento() {
		var idElemento = idVehiculo;
		muestraPopupReincidenciaElementoCaso();
		//Inicia grid
		if(banderaCargarORecargarGrid2==0){
			jQuery("#gridReincidenciaElementoCaso").jqGrid({
				url:'<%=request.getContextPath()%>/consultarReincidenciaXElemento.do?numeroExpediente='+numeroExpediente+'&idElemento='+idElemento +'',
							datatype: "xml",
							colNames:['Numero General del caso','Fecha de Apertura','Estatus'],
							colModel:[ 	{name:'NumeroGeneralCaso',index:'numeroGeneralCaso', width:200},
										{name:'FechaApertura',index:'fechaApertura', width:120},							
										{name:'Estatus',index:'estatus', width:100}
										],
				pager: jQuery('#paginadorReincidenciaElementoCaso'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: false,
				autoheight:false,
				sortname: 'FechaApertura',
				viewrecords: true,
				multiselect: false,
				sortorder: "desc"
			}).navGrid('#paginadorReincidenciaElementoCaso',{edit:false,add:false,del:false});
			banderaCargarORecargarGrid2=1;
		}
		else{
	    	jQuery("#gridReincidenciaElementoCaso").jqGrid('setGridParam',{
	    		url:'<%=request.getContextPath()%>/consultarReincidenciaXElemento.do?numeroExpediente='+numeroExpediente+'&idElemento='+idElemento + 
				'',datatype: "xml" });
			$("#gridReincidenciaElementoCaso").trigger("reloadGrid");
	    }//Fin grid		
	}

	function registrarReincidenciasXElemento(){
		var params = "";
	    params = '&idFuncionario=' + 1;
	    params += '&idElemento=' + idVehiculo;
	    params += '&idCasos=' + jQuery("#gridReincidenciaElemento").jqGrid('getGridParam','selarrrow');
	    if(idVehiculo != 0){
	    	$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/registrarReincidenciasXElemento.do',
				data: params,
				async: false,
				dataType: 'xml',
				success: function(xml){
					window.parent.customAlert("La(s) relaci\u00F3n(es) se registraron correctamente");
					window.parent.cargaVehiculo(idVehiculo,null,null);
					cargaGridReincidenciaElemento();
				}
			});
	    }
	    else{
	    	window.parent.customAlert("Debe de guardar primero el elemento")
	    }
	}

	//Inician cambios de RGG
	function muestraPopupReincidenciaElemento()
	{	
		$( "#dialog-Reincidencia").dialog({
			resizable: true,
			width:500,
			height:270,
			modal: true,
			buttons: {
				"Relacionar": function() {
					if(deshabilitarCampos == false){
						var longitudTabla = jQuery("#gridReincidenciaElemento").getDataIDs();
						var numeroExpedientes = longitudTabla.length;

			       		if(numeroExpedientes>0){
			       			if(jQuery("#gridReincidenciaElemento").jqGrid('getGridParam','selarrrow') != ""){
								registrarReincidenciasXElemento();
			       			}else{
			       				window.parent.customAlert("Debe de seleccionar al menos un registro");
			       			}
						}
						else{
							window.parent.customAlert("No hay expedientes por relacionar");
						}
					}
					
				},
	
				"Cerrar": function() {
					$( "#dialog:ui-dialog" ).hide();
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
				}
			}
		});
		$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();	
	}
	
	function muestraPopupReincidenciaElementoConsulta()
	{	
		$( "#dialog-Reincidencia").dialog({
			resizable: true,
			width:500,
			height:270,
			modal: true,
			buttons: {
				"Cerrar": function() {
					$( "#dialog:ui-dialog" ).hide();
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
				}
			}
		});
		$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();	
	}
	
	//Inician cambios de RGG
	function muestraPopupReincidenciaElementoCaso()
	{	
		$( "#dialog-ReincidenciaCaso").dialog({
			resizable: true,
			width:500,
			height:270,
			modal: true,
			buttons: {
				"Cerrar": function() {
					$( "#dialog:ui-dialog" ).hide();
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
				}
			}
		});
		$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();	
	}	

	/*
	*Funcion para mandar consultar el vehiculo
	*/
	function consultaVehiculo()
	{
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/ConsultaObjetoPorId.do',
    		data: 'idObjeto='+idVehiculo,
    		dataType: 'xml',
    		async: false,
    		cache: false,
    		success: function(xml){
    			if(parseInt($(xml).find('code').text())==0)
	    		{
    				var contenido = $(xml).find('contenido').text();
    				
    				if(contenido != null && contenido == ""){
    					$("#imgConFoto").attr("src","<%=request.getContextPath()%>/resources/images/Foto.JPG");
    				}
    				//seteamos la informacion del hecho
    				$(xml).find('VehiculoDTO').each(function(){
						seteaDatosVechiculo($(this));
	    	        });
	    		}
    		}	
    	});
	}
	
	function seteaDatosVechiculo(xml)
	{
		$('#txtModeloVehiculo').val($(xml).find('modelo').text());
		$('#txtPlacasVehiculo').val($(xml).find('placa').text());
		$('#txtSerieVehiculo').val($(xml).find('noSerie').text());
		$('#txtMotorVehiculo').val($(xml).find('noMotor').text());
		$('#txtRegFedVehiculo').val($(xml).find('nrfv').text());
		if($(xml).find('esBlindado').text()=="true")
		{
        	$('#chkBoxBlindadoVehiculo').attr('checked','checked');
		}
		else
		{
			$('#chkBoxBlindadoVehiculo').attr('checked','');
		}
		if($(xml).find('EsNumMotorAlterado').text()=="true")
		{
        	$('#chkBoxNumMotorAlterado').attr('checked','checked');
		}
		else
		{
			$('#chkBoxNumMotorAlterado').attr('checked','');
		}
		if($(xml).find('EsNumSerieAlterado').text()=="true")
		{
        	$('#chkBoxNumSerieAlterado').attr('checked','checked');
		}
		else
		{
			$('#chkBoxNumSerieAlterado').attr('checked','');
		}
		$('#txtPuertas').val($(xml).find('noPuertas').text());
		$('#txtCilindrosVehiculo').val($(xml).find('noCilindros').text());
		if($(xml).find('almacen'))
			$(xml).find('almacen').remove();
		$("#txtBoxDescVehiculo").val($(xml).find('descripcion').text());
		//seteamos los combos
		$('#cbxTipoVehiculo').find("option[value='"+$(xml).find('valorByTipoVehiculo').find('idCampo').text()+"']").attr("selected","selected");
		cargaMarcasVehiculo();
		$('#cbxMarcaVehiculo').find("option[value='"+$(xml).find('valorByMarcaVal').find('idCampo').text()+"']").attr("selected","selected");
		cargaSubMarcasVehiculo();
		$('#cbxSubMarcaVehiculo').find("option[value='"+$(xml).find('valorBySubmarcaVal').find('idCampo').text()+"']").attr("selected","selected");
		$('#cbxPaisOrigenVehiculo').find("option[value='"+$(xml).find('valorByPaisOrigenVal').find('idCampo').text()+"']").attr("selected","selected");
		$('#cbxColorVehiculo').find("option[value='"+$(xml).find('valorByColorVal').find('idCampo').text()+"']").attr("selected","selected");
		$('#cbxCondicionVehiculo').find("option[value='"+$(xml).find('valorByCondicionFisicaVal').find('idCampo').text()+"']").attr("selected","selected");
		
		$("#txtPropietario").val($(xml).find('propietario').text());
		
		//carga combos de causa
		$('#cbxCausaVehiculo').find("option[value='"+$(xml).find('causa').find('causaId').first().text()+"']").attr("selected","selected");
		cargaCausaVehiculoRecuperado();
		$('#cbxCausaRecuperadoVehiculo').find("option[value='"+$(xml).find('causaRecuperado').find('causaId').first().text()+"']").attr("selected","selected");
		cargaCausaVehiculoRecuperadoOtros();
		$('#cbxCausaRecuperadoOtrosVehiculo').find("option[value='"+$(xml).find('causaRecuperadoOtros').find('causaId').first().text()+"']").attr("selected","selected");
		
		$('#cbxRelacionHecho').find("option[value='"+$(xml).find('relacionHechoVal').find('idCampo').text()+"']").attr("selected","selected");
		//carga campos relacionados con relacion hechos
		muestraCamposHechos();
		var fchRec = $(xml).find('fechaRecuperado').text().split(' ');
		var fchRecBien = fchRec[0].split('-');
		$('#idFechaRecuperado').val(fchRecBien[2]+"/"+fchRecBien[1]+"/"+fchRecBien[0]);
		$('#idLugarRecuperado').val($(xml).find('lugarRecuperacion').text());
		$('#idAutoridadRecupero').val($(xml).find('autoridadRecupero').text());
		var fchEnt = $(xml).find('fechaEntrega').text().split(' ');
		var fchEntBien = fchEnt[0].split('-');
		$('#idFechaEntrega').val(fchEntBien[2]+"/"+fchEntBien[1]+"/"+fchEntBien[0]);
		
	}
	
	/**
	*Funcion que limpia los combos que dependen del combo tipo de vehiculo
	*/
	function limpiaCombosDependsTipo(){
		$('#cbxMarcaVehiculo').empty();
		$('#cbxMarcaVehiculo').append('<option value="-1">-Seleccione-</option>');	//Por default seleccione
		$('#cbxSubMarcaVehiculo').empty();
		$('#cbxSubMarcaVehiculo').append('<option value="-1">-Seleccione-</option>');	//Por default seleccione
	}


	/**
	*Funcion que limpia los combos que dependen del combo tipo de la marca del vehiculo
	*/
	function limpiaCombosDependsMarca(){
		$('#cbxSubMarcaVehiculo').empty();
		$('#cbxSubMarcaVehiculo').append('<option value="-1">-Seleccione-</option>');	//Por default seleccione
	}
	
	/*
	*Funcion que realiza la carga del combo de tipos de vehiculo
	*/
	function cargaTiposVehiculo() {
		  
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarTiposVehiculo.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catTiposVehiculo').each(function(){
					$('#cbxTipoVehiculo').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	/*
	*Funcion que realiza la carga de marcas del vehiculo
	*/
	function cargaMarcasVehiculo() {

		var selected = $("#cbxTipoVehiculo option:selected");

		limpiaCombosDependsTipo();
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarMarcasVehiculo.do',
			data: 'glTipoVehiculoId=' + selected.val(),
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catMarcasVehiculo').each(function(){
					$('#cbxMarcaVehiculo').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					$("#cbxMarcaVehiculo").multiselect('refresh');
				});
			}
		});
		
		// 4710 - Corresponde al campo Tipo que tiene el valor "Otro", este no cambia en las BDs, se maneja así,
		// ya que no hay un enum pero dicho catálogo.
		if($("#cbxTipoVehiculo option:selected").val()=="4710"){
			$('#cbxMarcaVehiculo').find("option[value='"+'-1'+"']").attr("selected","selected");
			$('#cbxSubMarcaVehiculo').find("option[value='"+'-1'+"']").attr("selected","selected");
			$("#cbxMarcaVehiculo").attr("disabled","disabled");
			$("#cbxSubMarcaVehiculo").attr("disabled","disabled");
		}
		else{
			$("#cbxMarcaVehiculo").attr("disabled","");
			$("#cbxSubMarcaVehiculo").attr("disabled","");			
		}
	}

	/*
	*Funcion que realiza la carga de sub marcas del vehiculo
	*/
	function cargaSubMarcasVehiculo() {

		var selected = $("#cbxMarcaVehiculo option:selected");

		limpiaCombosDependsMarca();
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarSubMarcasVehiculo.do',
			data: 'glMarcaVehiculoId=' + selected.val(),
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catSubMarcasVehiculo').each(function(){
					$('#cbxSubMarcaVehiculo').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					$('#cbxSubMarcaVehiculo').multiselect('refresh');
				});
			}

		});
	}

	/*
	*Funcion que realiza la carga de colores del vehiculo
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
					$('#cbxColorVehiculo').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
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
					$('#cbxPaisOrigenVehiculo').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}
	
	/**
	* Funcion que realiza la carga del combo Causa
	*/
	function cargarCausaVehiculo(){
		
		$('#cbxCausaRecuperadoVehiculo').empty();
		$('#cbxCausaRecuperadoVehiculo').append('<option value="-1">-Seleccione-</option>');
		$('#cbxCausaRecuperadoOtrosVehiculo').empty();
		$('#cbxCausaRecuperadoOtrosVehiculo').append('<option value="-1">-Seleccione-</option>');
		
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCausasVehiculo.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catCausaVehiculo').each(function(){
					$('#cbxCausaVehiculo').append('<option value="' + $(this).find('causaId').first().text() + '">' + $(this).find('nombreCausa').first().text() + '</option>');
					$("#cbxCausaVehiculo").multiselect('refresh');
				});
			}
		});
	}
	
	/**
	* Funcion que realiza la carga del combo CausaRecuperado
	*/
	function cargaCausaVehiculoRecuperado(){
		
		muestraCamposCausaRecuperado();
		
		var selected = $("#cbxCausaVehiculo option:selected");
		
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCausasVehiculo.do',
			data: 'causaVehiculoId=' + selected.val(),
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catCausaVehiculo').each(function(){
					$('#cbxCausaRecuperadoVehiculo').append('<option value="' + $(this).find('causaId').first().text() + '">' + $(this).find('nombreCausa').first().text() + '</option>');
					$("#cbxCausaRecuperadoVehiculo").multiselect('refresh');
				});
			}
		});
		
	}

	/**
	* Funcion que realiza la carga del combo CausaRecuperadoOtros
	*/
	function cargaCausaVehiculoRecuperadoOtros(){
	
		muestraCamposCausaRecuperadoOtros();
	
		var selected = $("#cbxCausaRecuperadoVehiculo option:selected");
		var selectedPadre = $("#cbxCausaVehiculo option:selected");
		
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCausasVehiculo.do',
			data: 'causaVehiculoRecuperadoId=' + selected.val()+ '&causaVehiculoId=' + selectedPadre.val(),
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catCausaVehiculo').each(function(){
					$('#cbxCausaRecuperadoOtrosVehiculo').append('<option value="' + $(this).find('causaId').first().text() + '">' + $(this).find('nombreCausa').first().text() + '</option>');
					$("#cbxCausaRecuperadoOtrosVehiculo").multiselect('refresh');
				});
			}
		});
		
	}
	
	/**
	* funcion que muestra u oculta los campos asociados al combo causa
	*/
	function muestraCamposCausaRecuperado(){
		
		if($("#cbxCausaVehiculo option:selected").val() == <%=ConstantesGenerales.VEHICULO_CAUSA_RECUPERADO_ID%> ){
			
			$("#trCausaRecuperado").show();
			$('#cbxCausaRecuperadoVehiculo').empty();
			$('#cbxCausaRecuperadoVehiculo').append('<option value="-1">-Seleccione-</option>');
			
		}else{
			$("#trCausaRecuperado").hide();
			$("#tdCausaRecuperadoOtros").hide();
		}
	}
	
	/**
	* funcion que muestra u oculta los campos asociados al combo causa opcion otros
	*/
	function muestraCamposCausaRecuperadoOtros(){
		
		if($("#cbxCausaRecuperadoVehiculo option:selected").val() == <%=ConstantesGenerales.VEHICULO_CAUSA_RECUPERADO_OTROS_ID%> ){
			
			$("#tdCausaRecuperadoOtros").show();
			$('#cbxCausaRecuperadoOtrosVehiculo').empty();
			$('#cbxCausaRecuperadoOtrosVehiculo').append('<option value="-1">-Seleccione-</option>');
		}else{
			$("#tdCausaRecuperadoOtros").hide();
		}
	}
	
	/**
	* funcion que muestra u oculta los campos asociados al combo relacion hechos
	*/
	function muestraCamposHechos(){
			
		
		if($("#cbxRelacionHecho option:selected").val() == <%=ConstantesGenerales.VEHICULO_RELACION_HECHOS_RECUPERADO_ID%> ){
			$("#idRelacionHechoRecuperado").show();
			$("#idRelacionHechoEntregado").hide();
		}else if($("#cbxRelacionHecho option:selected").val() == <%=ConstantesGenerales.VEHICULO_RELACION_HECHOS_ENTREGADO_ID%>){
				$("#idRelacionHechoRecuperado").hide();
				$("#idRelacionHechoEntregado").show();
		}else{
			$("#idRelacionHechoRecuperado").hide();
			$("#idRelacionHechoEntregado").hide();
		}
		
			
	}
	
	/*
	*Funcion que realiza la carga de la condicion del vehiculo
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
					$('#cbxCondicionVehiculo').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}
	
	//Funcionalidad agregada a peticion de lupita (esta pendiente de aceptacion) 16/05/2011
	//function buscarExpedienteVehiculo() {
	//	$.newWindow({id:"iframewindowBuscarExpediente", statusBar: true, posx:255,posy:110,width:653,height:400,title:"Buscar Expediente", type:"iframe"});
	//   	$.updateWindowContent("iframewindowBuscarExpediente",'<iframe src="<%= request.getContextPath() %>/buscarExpediente.do" width="653" height="400" />');		
	//}

	//function buscarCasoVehiculo(){
	//	$.newWindow({id:"iframewindowBuscarCaso", statusBar: true, posx:255,posy:110,width:653,height:400,title:"Buscar Caso", type:"iframe"});
    //	$.updateWindowContent("iframewindowBuscarCaso",'<iframe src="<%= request.getContextPath() %>/buscarCaso.do" width="653" height="400" />');		
	//}
	

	//	COMIENZA FUNCIONES PARA EL GUARDADO DE LOS DATOS		
	function obtenerValoresVehiculo(){		
		var paramVehiculo = 'idVehiculo='+idVehiculo;
		paramVehiculo += '&glIdPaisOrigenId=' + $("#cbxPaisOrigenVehiculo option:selected").val();
		paramVehiculo += '&glColorVehiculoId=' + $("#cbxColorVehiculo option:selected").val();
		paramVehiculo += '&glMarcaVehiculoId=' +  $("#cbxMarcaVehiculo option:selected").val();
		paramVehiculo += '&glSubMarcaVehiculoId=' + $("#cbxSubMarcaVehiculo option:selected").val();
		paramVehiculo += '&glTipoVehiculoId=' + $("#cbxTipoVehiculo option:selected").val();
		paramVehiculo += '&gsModelo=' + $('#txtModeloVehiculo').val();
		paramVehiculo += '&gcNoPlaca=' + $('#txtPlacasVehiculo').val();
		paramVehiculo += '&gcNoSerie=' + $('#txtSerieVehiculo').val();
		paramVehiculo += '&gcNoMotor=' + $('#txtMotorVehiculo').val();
		paramVehiculo += '&gcNoRegFed=' + $('#txtRegFedVehiculo').val();
        paramVehiculo += '&gbEsBlindado=' + $('#chkBoxBlindadoVehiculo').is(':checked');
        
        paramVehiculo += '&gbEsNumMotorAlterado=' + $('#chkBoxNumMotorAlterado').is(':checked');
        paramVehiculo += '&gbEsNumSerieAlterado=' + $('#chkBoxNumSerieAlterado').is(':checked');
        
		paramVehiculo += '&gsNoPuertas=' + $('#txtPuertas').val();
		paramVehiculo += '&gsNoCilindros=' + $('#txtCilindrosVehiculo').val();
		paramVehiculo += '&glCondicionFisica=' + $("#cbxCondicionVehiculo option:selected").val();
		paramVehiculo += '&gsDescripcion=' + $("#txtBoxDescVehiculo").val();
		if(cadenaCustodia!='null')
		{
			paramVehiculo += '&cadenaCustodia=' + cadenaCustodia;
			paramVehiculo += '&origenEvdCadCus=' + origenEvdCadCus;
			paramVehiculo += '&fechaLevCadCus=' + fechaLevCadCus;
		}
		paramVehiculo += '&propietario=' + $('#txtPropietario').val();
		
		paramVehiculo += '&causaVehiculoId=' + $('#cbxCausaVehiculo option:selected').val();
		paramVehiculo += '&causaVehiculoRecuperadoId=' + $('#cbxCausaRecuperadoVehiculo option:selected').val();
		paramVehiculo += '&causaVehiculoRecuperadoOtrosId=' + $('#cbxCausaRecuperadoOtrosVehiculo option:selected').val();

		paramVehiculo += '&relacionHechoId=' + $("#cbxRelacionHecho option:selected").val();
		paramVehiculo += '&fechaRecuperado=' + $('#idFechaRecuperado').val();
		paramVehiculo += '&lugarRecuperacion=' + $('#idLugarRecuperado').val();
		paramVehiculo += '&autoridadRecupero=' + $('#idAutoridadRecupero').val();
		paramVehiculo += '&fechaEntrega=' + $('#idFechaEntrega').val();
				
		$.ajax({		
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/ingresarVehiculo.do?numeroExpediente='+numeroExpediente +'',
			data: paramVehiculo,
			dataType: 'xml',
			success: function(xml){
				  var tipoVehiculo = $("#cbxTipoVehiculo option:selected").text();
				  var placas = $(xml).find('VehiculoForm').find('gcNoPlaca').text();
				  var id = parseInt($(xml).find('VehiculoForm').find('glTipoVehiculoId').text());
				
				  //Se ha agregado nuevo elemento
				  if(idVehiculo==0 && id==0)
				  {
					  window.parent.customAlert("Favor de revisar la información capturada");
				  }
				  else if(idVehiculo==0 && id>0)
				  {
					  					  
					  //Desde asentarRegCadenaCustodiaView.jsp 
					  if(parseInt( ocultaAnularObjetoCadCus ) == 1){
						  customAlert("Se guardó correctamente la información");
						  regresarControlCadenaCustodia();
					  }else //Desde el ingresarMenuIntermedio.jsp
					  {
						  window.parent.customAlert('Se guardó correctamente la información', '',regresarControl(id,tipoVehiculo,placas) );
					  }
				  }
				  else  //Actualizacion del elemento - solo desde el ingresarMenuIntermedio.jsp 
				  {   
					  //Desde asentarRegCadenaCustodiaView.jsp 
					  if(parseInt( ocultaAnularObjetoCadCus ) == 1){
						  regresarControlCadenaCustodiaActualizacion();
						  customAlert("La información se actualizó correctamente");
					  }else //Desde el ingresarMenuIntermedio.jsp
					  {
						  window.parent.customAlert('La información se actualizó correctamente', '',regresarControl(id,tipoVehiculo,placas) );
					  }
				  }
				  
				  elementoID = id;
				  idVehiculo = id;
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
			$("#btnGuardarVehiculo").fadeOut(1000);
		}
		window.parent.cargaObjetoCerrarVentana();
	}
	/**
	* Funcion que permite controlar y evitar que el alert desaparecas y se cierre la ventana
	* solo si viene desde ingresarMenuIntermedio.jsp
	*/	
	function regresarControl(id,tipoVehiculo,placas){
		if(elementoIDOriginal==0){
			$("#btnAdjuntarImagen").fadeIn(1000);
		}
		window.parent.cargaVehiculo(id,tipoVehiculo,placas);
	}
	
	function validaCamposObligatorios(){
		var tipoVehiculo = $("#cbxTipoVehiculo option:selected").val();
		var marcaVehiculo = $("#cbxMarcaVehiculo option:selected").val();
		var subMarcaVehiculo = $("#cbxSubMarcaVehiculo option:selected").val();
		var colorVehiculo = $("#cbxColorVehiculo option:selected").val();
		var paisOrigenVehiculo = $("#cbxPaisOrigenVehiculo option:selected").val();
		var modeloVehiculo = $('#txtModeloVehiculo').val();		
		var condicionVehiculo = $("#cbxCondicionVehiculo option:selected").val();
		var relacionHechoVehiculo = $("#cbxRelacionHecho option:selected").val();	
		var mensaje = "";
		//Primera validacion por cada campo obligatorio		
		if(parseInt(tipoVehiculo) == -1){
			mensaje += "<br />- Tipo";			
		}
		if($("#cbxTipoVehiculo option:selected").val()!="4710"){
			if(parseInt(marcaVehiculo) == -1){
				mensaje += "<br />- Marca";
			}
			if(parseInt(subMarcaVehiculo) == -1){
				mensaje += "<br />- Submarca";
			}
		}
		else{
			if($("#txtBoxDescVehiculo").val()==""){
				mensaje += "<br />- Descripci&oacute;n";
			}
		}
		if(parseInt(colorVehiculo) == -1){
			mensaje += "<br />- Color";			
		}
		if(modeloVehiculo == ""){
			mensaje += "<br />- Modelo";			
		}
		if(parseInt(condicionVehiculo) == -1){
			mensaje += "<br />- Condici&oacute;n";			
		}
		if(parseInt(relacionHechoVehiculo) == -1){
			mensaje += "<br />- Relaci&oacute;n del hecho";			
		}
		//Comienza segunda validacion de consistencia de expresiones regulares
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
			obtenerValoresVehiculo();
		}
	}
	
	/*
	*Funcion que manda a eliminar logicamente el objeto en la BD
	*/
	function anularObjeto(){
		var paramVehiculo="idObjeto="+idVehiculo;
		$.ajax({		
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/anularObjetoPorId.do',
			data: paramVehiculo,
			dataType: 'xml',
			success: function(xml){
				//procederemos a tratar de eliminar la evidencia
				if(parseInt($(xml).find('bandera').text())==1)
				{
					//se anulo exitosamente el objeto , actualizamos el grid de menu intermedio y cerramos la ventana
					window.parent.cargaVehiculo(idVehiculo,"","");
					window.parent.cerrarVentanaVehiculo();
				}
				if(cadenaCustodia=='null'){
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
		if(idVehiculo!=null && idVehiculo!=0)
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
		var paramVehiculo="idObjeto="+idVehiculo;
		$.ajax({		
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/validarObjPorIdEvdncNoEslbns.do',
			data: paramVehiculo,
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
	
<form id="profileForm" class="actionForm"  method="get">
	<table width="700" height="449" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td height="21" colspan="4" align="center">
				<input type="button" value="Anular objeto" class="btn_Generico"id="anularElemento" onclick="solicitarAnlrObjeto()"/>
				<input name="Button" type="button" id="btnGuardarVehiculo" class="btn_Generico" onclick="validaCamposObligatorios();" value="Guardar" />
            	<input type="button" id="btnBuscarReincidencia" value="Buscar reincidencia" class="btn_Generico" onclick="cargaGridReincidenciaElemento()"/>
            	<input type="button" id="btnConsultarExpedientes" value="Consultar expedientes relacionados " class="btn_Generico" onclick="cargaGridReincidenciasXElemento()"/>
            	<input type="button" id="btnAdjuntarImagen" value="Adjuntar imagen" class="btn_Generico" onclick="abreVentanaAdjuntarImagen()" style="display:none">
            </td>                
		</tr>
  		<tr height="6.25%">
    		<td width="30%" align="right">Tipo: </td>
    		<td width="30%"><select id="cbxTipoVehiculo" style="width:180px" onchange="cargaMarcasVehiculo();">
      							<option value="-1">-Seleccione-</option>
    						</select>
    		</td>
    		<td width="40%" rowspan="6" align="right">Descripción:<br>
  <textarea name="txtBoxDescVehiculo" cols="25" rows="9" id="txtBoxDescVehiculo" maxlength="199" ></textarea></td>
	    </tr> 
  		<tr height="6.25%">
    		<td width="30%" align="right">Marca: </td>
    		<td width="30%"><select id="cbxMarcaVehiculo" style="width:180px" onchange="cargaSubMarcasVehiculo();">
      							<option value="-1">-Seleccione-</option>
    						</select>
    		</td>
   		</tr>
  		<tr height="6.25%">
    		<td width="30%" align="right">Submarca: </td>
    		<td width="30%"><select id="cbxSubMarcaVehiculo" style="width:180px">
      							<option value="-1">-Seleccione-</option>
    						</select>
    		</td>
   		</tr>
  		<tr height="6.25%">
    		<td width="30%" align="right">Color: </td>
    		<td width="30%"><select id="cbxColorVehiculo" style="width:180px">
      							<option value="-1">-Seleccione-</option>
    						</select>
    		</td>
   		</tr>
  		<tr height="6.25%">
    		<td width="30%" align="right">Pa&iacute;s de Origen: </td>
    		<td width="30%"><select id="cbxPaisOrigenVehiculo" style="width:180px">
      							<option value="-1">-Seleccione-</option>
    						</select>
    		</td>
   		</tr>
  		<tr height="6.25%">
    		<td width="30%" align="right">Modelo (a&ntilde;o): </td>
    		<td width="30%"><input type="text" id="txtModeloVehiculo" onKeyPress="return solonumeros(event);" maxlength="4" style="width:175px"/></td>
   		</tr>
  		<tr height="6.25%">
    		<td width="30%" align="right">No. de Placas: </td>
    		<td width="30%"><input type="text" id="txtPlacasVehiculo" maxlength="10" style="width:175px"/></td>
    		<td width="40%" align="right">No. de Motor alterado:
   		    <input type="checkbox" id="chkBoxNumMotorAlterado"/></td>
   		</tr>
  		<tr height="6.25%">
    		<td width="30%" align="right">No. de Motor: </td>
    		<td width="30%"><input type="text" id="txtMotorVehiculo" maxlength="20" style="width:175px"/></td>
    		<td width="40%" align="right">No. de Serie alterado:
   		    <input type="checkbox" id="chkBoxNumSerieAlterado"/></td>
   		</tr>
  		<tr height="6.25%">
       		<td width="30%" height="24" align="right">No. de Serie: </td>
       		<td width="30%"><input type="text" id="txtSerieVehiculo" maxlength="20" style="width:175px"/></td>
       		<td width="40%" align="right">Blindado:
     		    <input type="checkbox" id="chkBoxBlindadoVehiculo"/></td>
   		</tr>		
  		<tr height="6.25%">
    		<td width="30%" align="right">No. de Registro P&uacute;blico Vehicular: </td>
    		<td width="30%"><input type="text" id="txtRegFedVehiculo" maxlength="20" style="width:175px"/></td>
    		<td width="40%">&nbsp;</td>
   		</tr>
  		<tr height="6.25%">
    		<td width="30%" align="right">No. de Puertas: </td>
    		<td width="30%"><input type="text" id="txtPuertas" style="width:175px" onKeyPress="return solonumeros(event);" maxlength="1"/></td>
    		<td width="40%">&nbsp;</td>
   		</tr>
  		<tr height="6.25%">
    		<td width="30%" align="right">No. de Cilindros: </td>
    		<td width="30%"><input type="text" id="txtCilindrosVehiculo" style="width:175px" onKeyPress="return solonumeros(event);" maxlength="1"/></td>
    		<td width="40%">&nbsp;</td>
   		</tr>
  		
  		<tr height="6.25%">
    		<td width="30%" height="36" align="right">Condici&oacute;n del objeto: </td>
    		<td width="30%"><select name="cbxTipoJoya6" id="cbxCondicionVehiculo" style="width:180px">
      							<option value="-1">-Seleccione-</option>
    						</select>
    		</td>
    		<td width="40%">&nbsp;</td>
   		</tr>
		<tr height="6.25%">
     		<td width="30%" height="36" align="right">Propietario: </td>
     		<td width="30%"><input type="text" id="txtPropietario" maxlength="100" style="width:175px" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/></td>
     		<td width="40%">&nbsp;</td>
   		</tr>
   		
   		<tr height="6.25%">
    		<td width="30%" align="right">Causa: </td>
    		<td width="30%"><select id="cbxCausaVehiculo" style="width:180px" onchange="cargaCausaVehiculoRecuperado();">
      							<option value="-1">-Seleccione-</option>
    						</select>
    		</td>
    		<td width="40%">&nbsp;</td>
   		</tr>
   		
   		<tr height="6.25%" id="trCausaRecuperado">
    		<td width="30%">&nbsp; </td>
    		<td width="30%" id="idCausaRecuperado"><select id="cbxCausaRecuperadoVehiculo" style="width:180px" onchange="cargaCausaVehiculoRecuperadoOtros();">
      							<option value="-1">-Seleccione-</option>
    						</select>
    		</td>
    		<td width="40%"id="tdCausaRecuperadoOtros" align="right"><select id="cbxCausaRecuperadoOtrosVehiculo" style="width:180px">
      							<option value="-1">-Seleccione-</option>
    						</select>
    		</td>
   		</tr>
   		
    	<tr height="6.25%">
     		<td width="30%" height="36" align="right">Relaci&oacute;n del hecho: </td>
     		<td width="30%"><select name="cbxRelacionHecho" id="cbxRelacionHecho" style="width:180px" onchange="muestraCamposHechos();">
       							<option value="-1">-Seleccione-</option>
     						</select>
     		</td>
     		<td width="40%" rowspan="3" style="vertical-align: text-top;">
     			<table border ="0" id="idRelacionHechoRecuperado">
     				<tr>
     					<td width="100px">Fecha de Recuperaci&oacute;n: </td>
     					<td><input type="text" id="idFechaRecuperado" style="width:110px;" readonly="readonly"/></td>
     				</tr>
     				<tr>
     					<td>Lugar de Recuperaci&oacute;n: </td>
     					<td><input type="text" id="idLugarRecuperado" width="50px"/></td>
     				</tr>
     				<tr>
     					<td>Autoridad que recupero: </td>
     					<td><input type="text" id="idAutoridadRecupero" width="50px"/></td>
     				</tr>
     			</table>
     			<table id="idRelacionHechoEntregado" border="0">
     				<tr>
     					<td width="40%">Fecha de Entrega: </td>
     					<td><input type="text" id="idFechaEntrega" style="width:110px;" readonly="readonly"/></td>
     				</tr>
     			</table>
     		</td>
   		</tr>
   		<tr>
   			<td>&nbsp;</td>
   			<td>&nbsp;</td>
   		</tr>
   		<tr>
   			<td>&nbsp;</td>
   			<td>&nbsp;</td>
   		</tr>
	</table>
</form>

	
	<!--  Muestra las imanenes asociadas al objeto asi como el grid del tipo de estudio -->	
	<jsp:include page="/WEB-INF/paginas/detalleObjeto.jsp" flush="true"></jsp:include>


                  
<!-- ETIQUETAS PARA LA REINCIDENCIA DE UN OBJETO -->
<div id="dialog-Reincidencia" title="Reincidencia(s) asociada(s) al elemento">
	<table id="gridReincidenciaElemento"></table>
	<div id="paginadorReincidenciaElemento"></div>
</div>

<div id="dialog-ReincidenciaCaso" title="Reincidencia(s) registrada(s) al elemento">
	<table id="gridReincidenciaElementoCaso"></table>
	<div id="paginadorReincidenciaElementoCaso"></div>
</div>

</body>
</html>