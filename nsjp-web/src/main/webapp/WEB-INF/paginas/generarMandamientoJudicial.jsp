<%@page import="mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TipoMandamiento"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<title>Generar Mandamiento Judicial</title>

<!--css de aplicacion-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />

<!--css para jQuery-->
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />

<!--css para jqGrid-->
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />

<!--css para ventanas windows engine-->
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />	
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />


<!--js para jQuery-->
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>

<!--js para jqGrid-->	
<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>

<!--para controlar las ventanas windows engine-->
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>

<!--js de la aplicacion-->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>

<!-- Script de mandamientos judicial -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/funcionesComunMandJudYMedCautelares.js"></script>

<%
	Long rolActivo = 0L;
	String jerarquia = "";
	Long institucionId = 0L;
	
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO"); 
	if (usuario != null 
			&& usuario.getRolACtivo() != null 
			&& usuario.getRolACtivo().getRol() != null
			&& usuario.getRolACtivo().getRol().getRolId() != null){
		rolActivo = usuario.getRolACtivo().getRol().getRolId();
		if (usuario.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO() != null
				&& usuario.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId() > 0L){
			jerarquia = usuario.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId().toString();
		}
	}
	
	if(usuario != null && usuario.getInstitucion() != null && usuario.getInstitucion().getConfInstitucionId() != null){
		institucionId = usuario.getInstitucion().getConfInstitucionId();
	}
%>	

<script type="text/javascript">

	//variable de contexto
	var CONTEXT_ROOT = '<%= request.getContextPath()%>';
	var contextoPagina = "${pageContext.request.contextPath}";

	/*
	*VARIABLES PARA ESTATUS DE MANDAMIENTOS JUDICIALES
	*/

	/**
	 * Estatus activos
	 */
	var NO_ATENDIDO = <%=EstatusMandamiento.NO_ATENDIDO.getValorId()%>; 
	var ATENDIDO = <%=EstatusMandamiento.ATENDIDO.getValorId()%>;
	var EN_PROCESO = <%=EstatusMandamiento.EN_PROCESO.getValorId()%>;
	var SIN_DOCUMENTO_DE_CREACION = <%=EstatusMandamiento.SIN_DOCUMENTO_DE_CREACION.getValorId()%>;
	var SIN_DOCUMENTO_DE_ESTATUS = <%=EstatusMandamiento.SIN_DOCUMENTO_DE_ESTATUS.getValorId()%>;
	var NO_ENVIADO = <%=EstatusMandamiento.NO_ENVIADO.getValorId()%>;
	var ACTUALIZACION_NO_ENVIADA = <%=EstatusMandamiento.ACTUALIZACION_NO_ENVIADA.getValorId()%>;
	
	/**
	 * Estatus inactivos
	 */
	var CONCLUIDO = <%=EstatusMandamiento.CONCLUIDO.getValorId()%>; 
	var CANCELADO = <%=EstatusMandamiento.CANCELADO.getValorId()%>; 
	var EJECUTADO = <%=EstatusMandamiento.EJECUTADO.getValorId()%>; 

	/*
	*VARIABLES PARA LA RECARGA DEL GRID DESDE LA VENTANA
	*Deben permanecer en cero ya que la generacion, no recarga ninguna bandeja en especial
	*de mandamientos
	*/
	var selectorMandamiento_recarga		= 0;
	var estatusDeFiltrado_recarga		= 0;
	var idsTiposMandamientos_recarga	= 0;
	var banderaPropios_recarga			= 0;

	/*
	*Variables para la operacion del jsp
	*/
	var NUM_COL_RDP = 4;
	//Rol del usuario activo
	var rolActivo =	'<%=rolActivo%>';
	//Parametro para saber si se trata de una consulta o ingreso de mandamiento
	var esConsulta = false;
	//Se obtiene la audienciaId para consultar los Probables Responsables
	var audienciaId = '<%=request.getParameter("idAudiencia")%>';
	//varibale que indica si se consulta por audiencia o por expediente
	// al JSP de consultarMandamientosJudicialesExp.jsp
	var esAudiencia = false;
	//Se obtiene idExpediente para consultar las Relaciones Delito-Persona
	var idExpediente ='<%=request.getParameter("idExpediente")%>';
	//Se obtiene el idResolutivo para ser mandado como parametro cuando se genere el Mandamiento 
	var idResolutivo ='<%=request.getParameter("idResolutivo")%>';
	//Se obtiene el numeroExpediente para ser mostrado en el campo de texto y sea mandado cuando se genere el Mandamiento
	var numeroExpediente = '<%=request.getParameter("numeroCausa")%>';
	//Se obtiene el numeroDeCaso para ser mostrado en el campo de texto respectivo
	var numeroDeCaso = '<%=request.getParameter("numeroDeCaso")%>';
	// Una lista para almacenar los ids Relacion Delito Persona que se enviaran como parametros al generar el Mandamiento
	var listaIdsRelDelPersona;
	// Una lista para almacenar los ids de los Probables Responsables que se enviaran como parametros al generar el Mandamiento
	var listaIdsProbablesResponsables;
	//Mandamiento judicial
	var mandamientoJudicialId = 0;

	jQuery().ready(function () {
		//Se crean las tabs
		crearTabs();
		//Se cargan funciones de la ceja Generar Mandamiento Judicial 
		cargaNumCausaYCasoEnCampos();
		cargaTipoMandamiento();
		cargarGridProbResGenManJud();
		cargarGridSelecRelDelPerGenManJud();
		cargarGridRelDelPerGenManJud();
				
	});
	
	/*
	*Funcion para crear las tabs
	*/
	function crearTabs(){
		$("#tabsGenerarMandamientoJudicial").tabs();
	}
	
/*********************************************** FUNCIONES CEJA GENERAR MANDAMIENTO JUDICIAL ***********************************************************/
	/**
	* Funcion que realiza la carga del numero de causa y de numero de caso
	* en los campos de texto
	*/
	function cargaNumCausaYCasoEnCampos(){
		$("#numeroCasoGenManJudTxt").val(numeroDeCaso);
		$("#numeroCausaGenManJudTxt").val(numeroExpediente);

		//Ceja mandamientos judiciales del expediente
		//Correspondientes a la ceja de Administrar Mandamientos Judiciales del expediente
		$("#numeroCasoGenManJudTxtExp").val(numeroDeCaso);
		$("#numeroCausaGenManJudTxtExp").val(numeroExpediente);
	}
	/**
	* Funcion que realiza la carga del catalogo de tipos de mandamiento
	*/
	function cargaTipoMandamiento() {

		$('#tipoMandamientoGenManJudCbx').empty();
		$('#tipoMandamientoGenManJudCbx').append('<option value="0">-Seleccione-</option>');	
		
		$.ajax({
			async: true,
			type: 'POST',
			url:'<%=request.getContextPath()%>/consultarCatalogoTipoMandamiento.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				
				$(xml).find('tipoMandamiento').each(function(){
					$('#tipoMandamientoGenManJudCbx').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	
	 
	/**
	* Funcion que carga el grid con los probables responsables
	* en base a la audiencia 
	*/
	var gridProbResGenManJud = true;
	
	function cargarGridProbResGenManJud(){
		
		if(gridProbResGenManJud){
			jQuery("#gridProbResGenManJud").jqGrid({
				url:'<%=request.getContextPath()%>/consultarProbablesResponsablesPorAudiencia.do?audienciaId='+audienciaId+'',
				datatype: "xml", 
				colNames:['<bean:message key="probableResponsable"/>'], 
				colModel:[  
							{name:'probResponsable',index:'probResponsable', width:350, align:'center'},
						],
				pager: jQuery('#pagerGridProbResGenManJud'),
				rowNum:10,
				rowList:[10,20,30],
				width:550,
				sortname:'probResponsable',
				toolbar: [true,"top"],
				viewrecords:true,
				hidegrid:false,
				caption:'<bean:message key="selProbableResponsable"/>',
				sortorder:"desc",
				onSelectRow: function(rowid) {
					cargarGridSelecRelDelPerGenManJud(rowid);
				}
			});

			$("#t_gridProbResGenManJud").append("<input type='button' class='btn_Generico' value='Ver Mandamientos Judiciales' style='height:20px;font-size:-3'/>");
			$("input","#t_gridProbResGenManJud").click(function(){
 
				var probRespId = jQuery("#gridProbResGenManJud").jqGrid('getGridParam','selrow');
				if(probRespId){
					var datosProbResp = jQuery("#gridProbResGenManJud").jqGrid('getRowData',probRespId);
					verMandamientoJudicialXProbResp(probRespId,datosProbResp.probResponsable,audienciaId);
				}else{
					customAlert("Seleccione un "+'<bean:message key="probableResponsable"/>');
				}
			});
						
			gridProbResGenManJud = false;
		}else{
			jQuery("#gridProbResGenManJud").jqGrid('setGridParam', { onSelectRow: function(rowid) { cargarGridSelecRelDelPerGenManJud(rowid); } });
			$("#gridProbResGenManJud").trigger("reloadGrid");
		}
		
	}

	/**
	*Funcion que carga el grid multiselect con las relaciones Delito-Persona 
	*en base al Probable Responsable seleccionado y el idExpediente
	*/
	var banderaGridSelecRelDelPerGenManJud = true;
	
	function cargarGridSelecRelDelPerGenManJud(probRespId){
		var params = '';
		params += '&idExpediente='+idExpediente;
		params += '&probRespId='+probRespId;
					
		if(banderaGridSelecRelDelPerGenManJud){
			jQuery("#gridSelecRelDelPerGenManJud").jqGrid({
				url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=NUM_COL_RDP',
				datatype: "xml", 
				colNames:['<bean:message key="probableResponsable"/>','Delito','Victima','idProbResponsableSel'], 
				colModel:[  {name:'probResponsable',index:'probResponsable', width:150, align:'center',sortable:false},						
				           	{name:'delito',index:'delito', width:150, align:'center',sortable:false}, 
				           	{name:'victima',index:'victima', width:150,align:'center',sortable:false}, 
				           	{name:'idProbResp',index:'idProbResp', width:50, align:'center', hidden:true,sortable:false},
						],
				pager: jQuery('#pagerGridSelecRelDelPerGenManJud'),
				rowNum:10,
				rowList:[10,20,30],
				width:560,
				hidegrid:false,
				height:200,
				sortname:'probResponsable',
				viewrecords:true,
				sortorder:"desc",
				multiselect:true,
				gridComplete: function () {
					seleccionarItems($(this));
				},
				onPaging: function () {
					guardarItemsSeleccionados($(this)); 
				},
				onSortCol: function(){
					eliminarItemsSeleccionados($(this));
				},
				caption:"Informaci&oacute;n de las relaciones Delito - Persona"

			});
			
			
			banderaGridSelecRelDelPerGenManJud = false;
		}else{
			jQuery("#gridSelecRelDelPerGenManJud").jqGrid('setGridParam',{url:'<%=request.getContextPath()%>/consultarRelDelitoPersonaPorProbableResponsableEnMandamientos.do?parametros='+params+'',datatype: "xml" });
			$("#gridSelecRelDelPerGenManJud").trigger("reloadGrid",[{page:1}]);
		}
		
	}
	
	 
	/**
	*Funcion que carga el grid con las relaciones Delito-Persona 
	*selecionadas
	*/
	var banderaGridRelDelPerGenManJud = true;
	
	function cargarGridRelDelPerGenManJud(){
				
			jQuery("#gridRelDelPerGenManJud").jqGrid({
				
				datatype: "xml",
				colNames:['<bean:message key="probableResponsable"/>','Delito','Victima','idProbResponsable'], 
				colModel:[  {name:'probResponsable',index:'probResponsable', width:150, align:'center'},						
				           	{name:'delito',index:'delito', width:150, align:'center'}, 
				           	{name:'victima',index:'victima', width:150,align:'center'}, 
				           	{name:'idProbResp',index:'idProbResp', width:50, align:'center', hidden:true},
						],
				
				rowNum:100,
				rowList:[10,20,30],
				width:470,
				sortname:'probResponsable',
				height:185,
				viewrecords:true,
				hidegrid:false,
				sortorder:"desc",
				toolbar: [true,"top"],
				caption:"Informaci&oacute;n de las relaciones Delito - Persona"
			});
			
			$("#gview_gridRelDelPerGenManJud .ui-jqgrid-bdiv").css('height', '185px');

			
			$("#t_gridRelDelPerGenManJud").append("<input type='button' class='btn_Generico' value='Eliminar Relaci&oacute;n' style='height:20px; align=right;font-size:-3'/>");
			$("input","#t_gridRelDelPerGenManJud").click(eliminarRelacionDelitoPersona);
		
	}
	
	//Variable que controla la apertura del jsp consultarMandamientoJudXProbResponsable
	var idWindowVerMandamientoJudicialXProbResp = 0;
	/*
	* Funcion que abre la ventana para ver el Mandamiento Judicial
	* por Probable Responsable
	*/
	function verMandamientoJudicialXProbResp(probRespId,nombreProbResp,audienciaId){
		
		var params = '';
		params += '&probRespId='+probRespId;
		params += '&idExpediente='+idExpediente;
		params += '&mandamientoJudicialId='+mandamientoJudicialId;
		params += '&nombreProbResp='+nombreProbResp;
		params += '&audienciaId='+audienciaId;
		params += '&esAudiencia='+true;
		
		
		idWindowVerMandamientoJudicialXProbResp++;
		$.newWindow({id:"iframeWindowVerMandamientoJudicialXProbResp"+idWindowVerMandamientoJudicialXProbResp, statusBar: true, posx:200,posy:50,width:1000,height:450,title:"Mandamientos Judiciales Asociados al Probable Responsable Por Audiencia", type:"iframe",onWindowClose: function(id){
			idWindowVerMandamientoJudicialXProbResp--;
		}});
		$.updateWindowContent("iframeWindowVerMandamientoJudicialXProbResp"+idWindowVerMandamientoJudicialXProbResp,
				'<iframe src="<%= request.getContextPath()%>/verMandamientoJudicialXProbResp.do?params='+params+'" width="1000" height="450" />');
		//$("#" +"iframeWindowVerMandamientoJudicialXProbResp"+idWindowVerMandamientoJudicialXProbResp+ " .window-maximizeButton").click();

	}
	
	/**
	* Funcion que elimina un reglon seleccionado del Grid gridRelDelPerGenManJud
	*/
	function eliminarRelacionDelitoPersona(){
		var idRenglonSeleccionado = jQuery("#gridRelDelPerGenManJud").jqGrid('getGridParam','selrow');
		if(idRenglonSeleccionado != null && isNaN(parseInt(idRenglonSeleccionado.length, 10))==false){
			jQuery("#gridRelDelPerGenManJud").jqGrid('delRowData',idRenglonSeleccionado);
			//Se elimina en el hashMap
			hashRelDelitoPersonaSelecciondas[idRenglonSeleccionado] = null;
			customAlert("Se ha eliminado la relaci&oacute;n");
		}else{
			customAlert("Seleccione la relaci&oacute;n a eliminar");
		}
	}
	
	/**FUNCIONES PARA EL HASHMAP*/
	
	
	//Variable para almacenar las relaciones delito persona
	//del grid de seleccionadas
	var hashRelDelitoPersonaSelecciondas = new Object();
		
	/**
	* Funcion que agrega renglones del Grid Izquierdo al Grid Derecho
	*/
	function agregarRelacionDelitoPersona(){
		
		//Obtiene los id seleccionados
		var idsSeleccionados = new Array();
		idsSeleccionados = obtenerSeleccionados($("#gridSelecRelDelPerGenManJud"));

		//Valida si el arreglo es vacio 
		if(typeof(idsSeleccionados) != "undefined" && typeof(idsSeleccionados) != "null" && idsSeleccionados != "" && isNaN(parseInt(idsSeleccionados.length, 10))==false){
			
			var respuesta = validaSeleccion(idsSeleccionados);
			
			if(respuesta == true){
				agregaSeleccionados(idsSeleccionados);
			}
			
		}else{
			customAlert("Seleccione al menos un registro");
		}
	}
	
	/**
	* Funcion que valida los ids de los renglones seleccionados
	* para ver si ya estan el Grid Izquierdo
	*/
	function validaSeleccion(idsSeleccionados){
		
		var tamanioSeleccionados = parseInt(idsSeleccionados.length, 10);
		
		//Por cada elemento seleccionado
		for(var contador = 0; contador < tamanioSeleccionados; contador++){
			
			//obtiene el identificador del item seleccionado
			var itemSeleccionado = idsSeleccionados[contador];
			
			
			var valorObtenido = hashRelDelitoPersonaSelecciondas[itemSeleccionado];
			
			//verifica si el registro ya se encuentra guardado
			if(valorObtenido != null){ 
				
				customAlert("Una o m&aacute;s relaciones ya fueron agregadas.<br>"
						+"Por favor rectificar la informaci&oacute;n seleccionada.");
				return false;
			}
		}
		 
		
		return true;
	}
	
	/**
	* Funcion que agrega los regnlones selecconados al hashMap y al grid de  lado derecho
	*/
	function agregaSeleccionados(idsSeleccionados){
		
		var tamanioSeleccionados = parseInt(idsSeleccionados.length, 10);
		
		//Por cada elemento seleccionado
		for(var contador = 0; contador < tamanioSeleccionados; contador++){
			
			//obtiene el identificador del item seleccionado
			var itemSeleccionado = idsSeleccionados[contador];
			
			//obtiene los datos del registro seleccionado
			var datosItem = jQuery("#gridSelecRelDelPerGenManJud").jqGrid('getRowData',itemSeleccionado);
			
			jQuery("#gridRelDelPerGenManJud").jqGrid('addRowData',itemSeleccionado,datosItem);
			
			//Se agrega en el hashMap
			hashRelDelitoPersonaSelecciondas[itemSeleccionado] = datosItem.idProbResp; 
		}
	}


	//bloquear pantalla
	bloquearPantalla(true, "Generando Mandamiento");  
	
	/*
	*Funcion para generar el Mandamiento Judicial  
	*/
	function generarMandamientoJudicial(){

		//Obtiene los parametros de forma global
		obtenerIdsRelDelPerYIdsProbsResposables();
		
		if(validaParametrosDeGuardadoGenManJud() == true){
			
			var params = '';
			params += recuperaDatosMandamientoJudicial();
					
			$.ajax({								
		    	  type: 'POST',
		    	  url: '<%= request.getContextPath()%>/generarMandamiento.do?params='+params+'',
		    	  async: false,
		    	  success: function(respuesta){
		    		  if(parseInt($(respuesta).find('code').text()) == 0){
			    		  
						 mandamientoJudicialId = parseInt($(respuesta).find('documentoId').text());
		    			  
		 				 if(mandamientoJudicialId > 0){
		 						customAlert("Mandamiento judicial generado de forma correcta");
		 						abrirEditorDocumentosMandamientos(mandamientoJudicialId,<%=Actividades.GENERAR_MANDAMIENTO_JUDICIAL.getValorId()%>,true,true);
		 						//ocultamos el boton
		 						$('#generarMandamiento').hide();
		 				 }else{
		 					 customAlert("Ocurri&oacute; un error al intentar generar el mandamiento.<br/>"
		 							 + "por favor contacte al administrador");
		 				 }
		 			 } else {
		 				customAlert("Una o m&aacute;s relaciones ya están asociadas al mandamiento judicial.<br/>"
	 							 + "Por favor rectificar la informaci&oacute;n seleccionada.");
	 							 
		 			 }
		    		   
		    		  
		 			 desbloquearPantalla();
		    	 }
		    });
		}
	}
	
	/*
	*Funcion que valida que se hayan ingresado todos los campos correctamente
	*/
	function validaParametrosDeGuardadoGenManJud(){
				
		var tipoMandamiento = $('#tipoMandamientoGenManJudCbx option:selected').val();
		//Se valida si selecciono un tipo de mandamiento en el combo		
		if( parseInt(tipoMandamiento,10) == 0){
			customAlert("Seleccione un tipo de mandamiento");
			return false; 
		}
		
		//Se valida que las listas que se llenan con el hashMap no esten vacias
		if(listaIdsRelDelPersona==null || listaIdsRelDelPersona=='' || listaIdsProbablesResponsables==null || listaIdsProbablesResponsables==''){
			customAlert("Seleccione almenos una relaci&oacute;n delito-persona");
			return false;
		}
		return true;
	}
	
	/**
	* Funcion que recupera los datos de la pestaña Generar Mandamiento Judicial
	*/
	function recuperaDatosMandamientoJudicial(){
        var parametros = '&idResolutivo=' + idResolutivo;
        parametros += '&numeroExpediente=' + numeroExpediente;
        parametros += '&tipoMandamiento=' +  $('#tipoMandamientoGenManJudCbx option:selected').val();
             
        parametros += '&listaIdsRelDelPersona=' +  listaIdsRelDelPersona;
        parametros += '&listaIdsProbablesResponsables=' +  listaIdsProbablesResponsables;
		return parametros;
	}
	
	/**
	* Funcion que obtiene del HashMap los ids Relacion Delito-Persona y ids Probables responsables asociados 
	* 
	*/
	function obtenerIdsRelDelPerYIdsProbsResposables(){
		listaIdsRelDelPersona='';
		listaIdsProbablesResponsables='';
		for(var iterador in hashRelDelitoPersonaSelecciondas){
			var value = hashRelDelitoPersonaSelecciondas[iterador];
			if(value!=null){
				listaIdsRelDelPersona += iterador + ","; // El iterator es el id del Delito-Persona
				listaIdsProbablesResponsables += value + ","; //El valor es id de Probable responsable asociado al Delito-Persona	
			}
			
		}
		
	}

/*********************************************** TERMINAN FUNCIONES CEJA GENERAR MANDAMIENTO JUDICIAL ******************************************************/

</script>
</head>
<body>

	<!-- Comienza definiciones de Tabs -->
	<div id="tabsGenerarMandamientoJudicial">
		<ul>
			<li class="tabGenerarMandamientoJudicial"><a href="#generarMandamientoJudicial"> <bean:message key="mandamiento.judicial.generarMandamiento" /></a></li>
			<li class="tabDocumentosMandamientosJudiciales"><a href="#tabDocumentosGenManJud" onclick="inicializaTabDocumentos()"> Documentos</a></li>
			<li class="tabMandamientosDelExpediente"><a href="#tabMandamientosExpedienteGenManJud" onclick="inicializaTabMandamientosDelExpediente()"> Mandamientos judiciales del expediente </a></li>
		</ul>

		<!-- Comienza Cuerpo de Tabs -->

		<!-- Comienza cuerpo generar mandamiento judicial -->
		<div id="generarMandamientoJudicial">
			<table width="100%" border="0">
				<tr>
					<td width="20%">&nbsp;</td>
					<td width="20%">&nbsp;</td>
					<td width="5%">&nbsp;</td>
					<td width="5%">&nbsp;</td>
					<td width="50%">&nbsp;</td>
				</tr>
				<tr>
					<td><strong>N&uacute;mero de Caso:</strong></td>
					<td><input type="text" id="numeroCasoGenManJudTxt"
						disabled="disabled" tabindex="1" style="width: 250px" /></td>
					<td>&nbsp;</td>
					<td colspan="2" rowspan="7" align="center">
						<table id="gridProbResGenManJud" border="0"></table>
						<div id="pagerGridProbResGenManJud"></div>
					</td>
				</tr>
				<tr>
					<td><strong>N&uacute;mero de Causa:</strong></td>
					<td><input type="text" id="numeroCausaGenManJudTxt"
						disabled="disabled" tabindex="2" style="width: 250px" /></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><strong>Tipo de mandamiento
							Judicial</strong>:</td>
					<td><select id="tipoMandamientoGenManJudCbx" tabindex="3"
						style="width: 255px">
					</select></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<table id="gridSelecRelDelPerGenManJud" border="0">
						</table>
						<div id="pagerGridSelecRelDelPerGenManJud"></div>
					</td>
					<td align="center"><input type="button" id="pasarIzqDer"
						value='&gt;&gt;' class="btn_Generico"
						onclick="agregarRelacionDelitoPersona();"></td>
					<td colspan="2" align="center">
						<table id="gridRelDelPerGenManJud" border="0">
						</table>
						<div id="pagerGridRelDelPerGenManJud"></div>
					</td>
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="right"><input type="button" id="generarMandamiento"
						value='<bean:message key="mandamiento.judicial.generarMandamiento"/>'
						class="btn_Generico" onclick="generarMandamientoJudicial();">
					</td>
				</tr>
			</table>
		</div>
		<!-- Termina cuerpo generar mandamiento judicial -->

		<!-- Comienza cuerpo Documentos mandamiento judicial -->
		<div id="tabDocumentosGenManJud">
			<jsp:include page="/WEB-INF/paginas/consultarDocumentosDelExpediente.jsp" flush="true"></jsp:include>
		</div>
		<!-- Termina cuerpo Documentos mandamiento judicial -->
		
		<!-- Inicia cuerpo Mandamientos judiciales del expediente -->
		<div id="tabMandamientosExpedienteGenManJud">
	    	<jsp:include page="/WEB-INF/paginas/consultarMandamientosJudicialesExp.jsp" flush="true"></jsp:include>
		</div>
		<!-- Termina cuerpo Mandamientos judiciales del expediente -->
		
	</div>
	<!-- Terminan definiciones de Tabs -->
	
	<!--Form para la consulta del archivo digital-->
	<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
	</form>
</body>
</html>