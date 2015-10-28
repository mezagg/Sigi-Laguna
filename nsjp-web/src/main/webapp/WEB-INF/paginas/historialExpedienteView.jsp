<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes" %>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.expediente.TipoDeBusquedaDeExpediente" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

	<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />				
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
	 
    
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script src="<%= request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%= request.getContextPath()%>/resources/js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Historial del expediente</title>
	
	<script type="text/javascript">

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
	
	var idWindowNuevaDenuncia=1;
	var idExpediente = '<%= request.getParameter("idExpediente")%>';
	var idNumeroExpediente = '<%= request.getParameter("idNumeroExpediente")%>';
	var idCaso = '<%= request.getParameter("idCaso")%>';
	var numeroExpediente = '<%= request.getParameter("numeroExpediente")%>';
	//Jerarquia del numero de expediente
	//TODO: Validar que el area se obtenga con la pantalla de busqueda avanzada
	var jerarquiaOrganizacional_id = '<%= request.getParameter("jerarquiaOrganizacional_id")%>';
	var idRolActivo = '<%= request.getParameter("idRolActivo")%>';
	var esConsulta = '<%= request.getParameter("esConsulta")%>'
	
		
	$(document).ready(function() {		
		$('#labelConsignador').hide();
		$("#tabsPrincipal").tabs();		
		
		//Grid de Generales del expediente
		jQuery("#gridGeneralesDelExpediente").jqGrid({
			url:'<%=request.getContextPath()%>/consultarGeneralesDeHistorialDeExp.do?idNumeroExpediente='+idNumeroExpediente + '&jerarquiaOrganizacional_id=' + jerarquiaOrganizacional_id + '&idExpediente=' + idExpediente,
			datatype: "xml", 
			colNames:['No. Caso','Denunciante o v&iacute;ctima', 'Delito Principal', 'Tipo'], 
			colModel:[ 	{name:'caso',index:'caso', width:250, align:'center', sortable:false}, 
						{name:'denunciante',index:'denunciante', width:250, align:'center', sortable:false}, 
						{name:'delito',index:'delito', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:false},
						{name:'tipoDenuncia',index:'tipoDenuncia', width:200, align:'center', sortable:false},
					],
			pager: jQuery('#pagerGridGeneralesDelExpediente'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:950,
			caption:"GENERALES DEL EXPEDIENTE",
			sortname: '',
			viewrecords: true,
			id: 'gridGeneralesDelExpediente',
			ondblClickRow: function(id){
				abrirVisorExpediente(idNumeroExpediente,idExpediente, numeroExpediente, null);				
			},
			sortorder: "desc"
		});
		
		
		//Grid de Informacion actual del expediente
		var idsAreaExpediente = '<%=Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong()%>' + "," + '<%=Areas.COORDINACION_UNIDAD_INVESTIGACION.parseLong()%>' + "," + '<%=Areas.UNIDAD_INVESTIGACION.parseLong()%>' + "," + '<%=Areas.ATENCION_TEMPRANA_PG_PENAL.parseLong()%>' + ","  + '<%=Areas.FISCAL_FACILITADOR.parseLong()%>' + ",";
				
		jQuery("#gridInfoActualDelExp").jqGrid({
			url:'<%=request.getContextPath()%>/consultarDetalleDeNumerosExpediente.do?idExpediente='+idExpediente+'&idsAreas='+idsAreaExpediente,
			datatype: "xml", 
			colNames:['Fecha de creaci&oacute;n','&Aacute;rea', 'N&uacute;mero de Expediente', 'Funcionario', 'Estatus actual'], 
			colModel:[ 	{name:'fechaCreacion',index:'1', width:150, align:"center"}, 
						{name:'area',index:'2', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
						{name:'numExpediente',index:'3', width:200, align:"center"},
						{name:'funcionario',index:'funcionario', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:false},
						{name:'estatus',index:'5', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
					],
			pager: jQuery('#pagerGridInfoActualDelExp'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:950,
			caption:"INFORMACI&Oacute;N ACTUAL DEL EXPEDIENTE",
			sortname: '1',
			viewrecords: true,
			id: 'gridInfoActualDelExp', 
			ondblClickRow: function(id){
			},
			sortorder: "desc"
		});
		
		
		
		//Grid del Proceos del expediente
		jQuery("#gridProcesoDelExp").jqGrid({
			url:'<%=request.getContextPath()%>/consultarProcesoDelExpediente.do?idExpediente='+idExpediente + '&idCaso=' + idCaso,
			datatype: "xml", 
			colNames:['Polic&iacute;a Ministerial','Audiencias', 'Detenidos', 'Visitadur&iacute;a', 'UAVD'], 
			colModel:[ 	{name:'polMin',index:'polMin', width:150, sortable:false, align:'center'}, 
						{name:'audiencias',index:'audiencias', width:150, sortable:false, align:'center'}, 
						{name:'detenidos',index:'detenidos', width:200, sortable:false, align:'center'},
						{name:'visitaduria',index:'visitaduria', width:100, sortable:false, align:'center', hidden:true},
						{name:'uavd',index:'uavd', width:100, sortable:false, align:'center'},
					],
			pager: jQuery('#pagerGridProcesoDelExp'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:950,
			caption:"PROCESO DEL EXPEDIENTE",
			sortname: 'fechaCreacion',
			viewrecords: true,
			id: 'gridProcesoDelExp',
			ondblClickRow: function(id){
			},
			sortorder: "desc"
		});
		
		//Grid de detenidos
		jQuery("#gridDetenidosDelExp").jqGrid({
			url:'<%=request.getContextPath()%>/consultarDetencionesPorExpediente.do?idExpediente='+idExpediente,
			datatype: "xml", 
			colNames:['Nombre','Fecha detenci&oacute;n', 'Hora detenci&oacute;n', 'Fecha disponiblidad', 'Hora disponiblidad'], 
			colModel:[ 	{name:'nombre',index:'', width:200,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:false},
						{name:'fechaDetencion',index:'1', width:150, sortable:true, align:'center'}, 
						{name:'horaDetencion',index:'', width:200, sortable:false, align:'center'},
						{name:'fechaDisponibilidad',index:'2', width:150, sortable:true, align:'center'},
						{name:'horaDisponibilidad',index:'', width:100, sortable:false, align:'center'},
					],
			pager: jQuery('#pagerGridDetenidosDelExp'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:800,
			caption:"DETENIDOS",
			sortname: '1',
			viewrecords: true,
			id: 'gridDetenidosDelExp',
			ondblClickRow: function(id){
			},
			sortorder: "desc"
		});
		
		
		//Grid de audiencias
		jQuery("#gridAudienciasDelExp").jqGrid({
			url:'<%=request.getContextPath()%>/consultarAudienciasSolicitadasPorIdExpediente.do?idExpediente='+idExpediente,
			datatype: "xml", 
			colNames:['N&uacute;mero de Expediente','Quien solicit&oacute;', '&Aacute;rea', 'Fecha de solicitud', 'Tipo de audiencia'], 
			colModel:[ 	{name:'numeroExp',index:'', width:150, sortable:false, align:'center', hidden:true}, 
						{name:'solicitante',index:'1', width:150, sortable:false, align:'center'}, 
						{name:'area',index:'', width:200, sortable:false, align:'center', hidden:true},
						{name:'fechaSolicitud',index:'2', width:150, sortable:false, align:'center'},
						{name:'tipoAudiencia',index:'', width:100, sortable:false, align:'center'},
					],
			pager: jQuery('#pagerGridAudienciasDelExp'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:800,
			caption:"AUDIENCIAS",
			sortname: '2',
			viewrecords: true,
			id: 'gridAudienciasDelExp',
			ondblClickRow: function(id){
			},
			sortorder: "desc"
		});
		
		//Grid de Informacion de los expedientes de PM
		var idsAreaPM = '<%=Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong()%>'
		jQuery("#gridInfoActualDelExpPM").jqGrid({
			url:'<%=request.getContextPath()%>/consultarDetalleDeNumerosExpediente.do?idExpediente='+idExpediente+'&idsAreas='+idsAreaPM,
			datatype: "xml", 
			colNames:['Fecha de creaci&oacute;n','&Aacute;rea', 'N&uacute;mero de Expediente', 'Funcionario', 'Estatus actual'], 
			colModel:[ 	{name:'fechaCreacion',index:'1', width:100, align:"center"}, 
						{name:'area',index:'2', width:150, align:"center", hidden:true}, 
						{name:'numExpediente',index:'3', width:200, align:"center"},
						{name:'funcionario',index:'funcionario', width:150, align:"center", sortable:false},
						{name:'estatus',index:'5', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
					],
			pager: jQuery('#pagerGridInfoActualDelExpPM'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:800,
			caption:"POLIC&Iacute;A MINISTERIAL",
			sortname: '1',
			viewrecords: true,
			id: 'gridInfoActualDelExpPM',
			ondblClickRow: function(id){
			},
			sortorder: "desc"
		});
		
		
		//Grid de Informacion de los expedientes de UAVD
		var idsTiposSolicitud = '<%=TiposSolicitudes.ATENCION_PSICOLOGICA.getValorId()%>' + "," + '<%=TiposSolicitudes.TRABAJO_SOCIAL.getValorId()%>' + "," + '<%=TiposSolicitudes.ATENCION_JURIDICA.getValorId()%>';
			
		jQuery("#gridInfoActualDelExpUAVD").jqGrid({
			url:'<%=request.getContextPath()%>/consultarSolicitudesPorTipoYNumeroExps.do?idCaso='+idCaso+'&idsTiposSolicitud='+idsTiposSolicitud,
			datatype: "xml", 
			colNames:['Fecha de creaci&oacute;n','N&uacute;mero de Expediente', 'Funcionario', 'Estatus actual', 'Tipo de atenci&oacute;n'], 
			colModel:[ 	{name:'fechaCreacion',index:'1', width:100, align:"center", sortable:true}, 
						{name:'numExpediente',index:'2', width:150, align:"center", sortable:true}, 
						{name:'funcionario',index:'3', width:200, align:"center", sortable:true},
						{name:'estatus',index:'4', width:150, align:"center", sortable:true},
						{name:'tipoAtencion',index:'5', width:100, align:"center", sortable:true},
					],
			pager: jQuery('#pagerGridInfoActualDelExpUAVD'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:950,
			caption:"UNIDAD DE ATENCI&Oacute;N A VICTIMAS DEL DELITO",
			sortname: '1',
			viewrecords: true,
			id: 'gridInfoActualDelExpPM',
			ondblClickRow: function(id){
			},
			sortorder: "desc"
		});
		
		//Grid bitacora de expedientes
		jQuery("#gridBitacoraPermisos").jqGrid({
			url:'<%=request.getContextPath()%>/obtnerPermisosDeNumeroExpediente.do?idExpediente=' + idExpediente,
			datatype: "xml", 
			colNames:['N&uacute;mero de Expediente', 'Funcionario al que se asign&oacute permisos','&Aacute;rea','Fecha de asignaci&oacute;n', 'Fecha l&iacutemite', 'Activo'], 
			colModel:[ 	
						{name:'numeroExpediente',index:'numeroExpediente', width:200, align:"center"},
						{name:'funcionario',index:'funcionario', width:250, align:"center", sortable:true},
						{name:'area',index:'area', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
			           	{name:'fechaMovimiento',index:'fechaMovimiento', width:150, align:"center"}, 
			           	{name:'fechaLimite',index:'fechaLimite', width:150, align:"center"}, 
						{name:'esActivo',index:'esActivo', width:100, align:"center"},
					],
			pager: jQuery('#pagerGridBitacoraPermisos'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width:1000,
			caption:"PERMISOS",
			sortname: 'fechaMovimiento',
			viewrecords: true,
			id: 'gridBitacoraPermisos',
			ondblClickRow: function(id){
			},
			sortorder: "desc"
		});
		
		
		//Grid bitacora Estatus Numero de expedientes
		jQuery("#gridBitacoraNumerosExpediente").jqGrid({
			url:'<%=request.getContextPath()%>/consultarBitacoraEstatusNumExpedientePorIdExpediente.do?idExpediente=' + idExpediente,
			datatype: "xml", 
			colNames:['Fecha de creaci&oacute;n','&Aacute;rea','N&uacute;mero de Expediente', 'Funcionario','Estatus', 'Actividad', 'Documento'], 
			colModel:[ 	
						{name:'fechaCreacion',index:'1', width:180, align:"center", sortable:true},
						{name:'area',index:'2', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
						{name:'numeroExpediente',index:'3', width:200, align:"center", sortable:true},
						{name:'funcionario',index:'4', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
						{name:'estatus',index:'5', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
						{name:'tipoActividad',index:'6', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
						{name:'tipoDocumento',index:'7', width:0,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }, sortable:true},
					],
			gridview: true,
			viewrecords: true,
			pager: jQuery('#pagerGridBitacoraNumerosExpediente'),
			autowidth: true,
			rowNum:50,
			rowList:[50,100,150,200,250,300,350,400,450,500],
			height: 400,
			caption:"CAMBIO DE ESTATUS DEL EXPEDIENTE",
			sortname: '1',
			viewrecords: true,
			id: 'gridBitacoraNumerosExpediente',
			ondblClickRow: function(id){
			},
			sortorder: "desc"
		});
		
		$('#dialog-detalleDetenidos').hide();
		$('#dialog-detalleAudiencias').hide();
		$('#dialog-detalleExpedientesPM').hide();
		$('#dialog-detalleExpedientesUAVD').hide();
		
		validaMostrarLeyendaDeCanalizacion();
		
		verHistorial(idExpediente);
		
	});
	
	
	//Configuracion para el tap "Responsables del expediente"
	var gridHistorico = { 
		url:"",
		datatype: "local",
		colNames:['N&uacute;mero Expediente','Id Responsable', 'Due&ntilde;o actual', 'Id Asigna', 'Quien realizo el cambio', 'Id Revocado', 'Due&ntilde;o anterior', 'Fecha cambio', 'Fecha Fin'], 
		colModel:[
					{name:'NumeroExpediente',index:'NumeroExpediente',width:200, align:"center"},
					{name:'IdResponsable',index:'FuncionarioActual', sortable:false, hidden:true}, 
					{name:'NombreResponsable',index:'FuncionarioActual',width:200},
					{name:'IdAsigna',index:'FuncionarioAsiigna', sortable:false, hidden:true}, 
					{name:'NombreAsigna',index:'FuncionarioAsigna',width:200},
					{name:'IdAnterior',index:'FuncionarioAnterior', sortable:false, hidden:true}, 
					{name:'NombreAnterior',index:'FuncionarioAnterior',width:200},							
					{name:'FechaCambio',index:'FechaCambio', align:"center", width:200,sortable:false},
					{name:'FechaFin',index:'FechaFin', align:"center", hidden:true}
				],
		width:1000,
		shrinkToFit:true,
		pager: "#pagerGridHistorico",
		rowNum:10,
		rowList:[10,20,30,40,50,60,70,80,90,100],
		autowidth: true,
		sortname: 'historicoExpedienteId',
		viewrecords: true,
		onSelectRow: function(id){},
		ondblClickRow: function(id) {
			//cargarGrid(1);
		},
		caption: "CAMBIO DE DUE&Ntilde;OS DE EXPEDIENTE",
		sortorder: "desc"			
	};
	
	
	function consultaDetenidos(){
		$('#dialog-detalleDetenidos').show();
		muestraPopupDetalleDetenidos();
	}
	
	function consultaAudiencias(){
		$('#dialog-detalleAudiencias').show();
		muestraPopupDetalleAudiencias();
	}
	
	function consultaGridGeneralesDelExpediente()
	{	
		jQuery("#gridGeneralesDelExpediente").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarGeneralesDeHistorialDeExp.do?numeroExpediente='+numeroExpediente+'&tipoObjeto=1&jerarquiaOrganizacional_id=' + jerarquiaOrganizacional_id + '&idExpediente=' + idExpediente,datatype: "xml" });
		$("#gridGeneralesDelExpediente").trigger("reloadGrid");
		
		
	}
	
	function consultaExpedientesPM(){
		$('#dialog-detalleExpedientesPM').show();
		muestraPopupDetalleExpedientesPM();
	}
	
	function consultaExpedientesUAVD(){
		$('#dialog-detalleExpedientesUAVD').show();
		muestraPopupDetalleExpedientesUAVD();
	}
	
	
	function tituloVentana(num){
		$("#iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia+" div.window-titleBar-content").html("Expediente: "+num);
	}
	
	
	function abrirVisorExpediente(idNumeroExpediente,idExpediente, numeroExpediente, idArea) {
		var pantallaSolicitada;
		
		if(idRolActivo != '<%=Roles.PERITOAMP.getValorId()%>'){
			if(esConsulta == "1"){
				
				switch(parseInt(idRolActivo)){
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
			}else{//No tiene permisos para visualizar el expediente
				customAlert("Usted no es el due&ntilde;o del expediente,<br> y no cuenta con permisos para consultarlo.","");
			}		
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
	* Permite consultar un expediente de atenci&oacute;n temprana administrativa
	**/
	function consultaExpedienteDeAtencionTemAdm(numeroExpediente, idNumeroExpediente) {
		$.newWindow({id:"iframewindowRegistraDatosPersona", statusBar: true, posx:200,posy:50,width:1140,height:450,title:"Expediente:  "+numeroExpediente, type:"iframe"});
	    $.updateWindowContent("iframewindowRegistraDatosPersona",'<iframe src="<%= request.getContextPath() %>/BusquedaExpedienteAdminAt.do?formaId=2&numeroExpedienteTempAdmin='+ numeroExpediente +'&idExpedienteTempAdmin='+idNumeroExpediente+'&operacion=CONSULTA " width="1140" height="450" />');
	    $("#" +"iframewindowRegistraDatosPersona .window-maximizeButton").click();	
	}
	
	
	function muestraPopupDetalleDetenidos()
	{
		$( "#dialog-detalleDetenidos" ).dialog({
			resizable: false,
			width:900,
			height:400,					
			modal: true,
			buttons: {			
				"Cerrar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
				}
			}
		});
		$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();	
	}
	
	function muestraPopupDetalleAudiencias()
	{
		$( "#dialog-detalleAudiencias" ).dialog({
			resizable: false,
			width:900,
			height:400,					
			modal: true,
			buttons: {			
				"Cerrar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
				}
			}
		});
		$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();	
	}
	
	function muestraPopupDetalleExpedientesPM()
	{
		$( "#dialog-detalleExpedientesPM" ).dialog({
			resizable: false,
			width:900,
			height:400,					
			modal: true,
			buttons: {			
				"Cerrar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
				}
			}
		});
		$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();	
	}
	
	function muestraPopupDetalleExpedientesUAVD()
	{
		$( "#dialog-detalleExpedientesUAVD" ).dialog({
			resizable: false,
			width:1000,
			height:400,					
			modal: true,
			buttons: {			
				"Cerrar": function() {
					$( this ).dialog( "close" );
					$( "#dialog:ui-dialog" ).dialog( "destroy" );
				}
			}
		});
		$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();	
	}
	
	function validaMostrarLeyendaDeCanalizacion(){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/obtenDetalleDeCanalizacionDeNumeroExpediente.do?idNumeroExpediente='+idNumeroExpediente,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
					if(jerarquiaOrganizacional_id == <%=Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong()%> || jerarquiaOrganizacional_id == <%=Areas.ATENCION_TEMPRANA_PG_PENAL.parseLong()%>){
						//Ocultar grid del proceso
						if(parseInt($(xml).find('respuesta').text())== 0){//No tiene canalizaciones
							$('#tblProcesoExpediente').hide();
						}else{
							if(parseInt($(xml).find('respuesta').text())== 1){//No ha sido asignado por el coordinador
								$('#tblProcesoExpediente').hide();
								$('#labelConsignador').show();							
							}else{
								$('#tblProcesoExpediente').show();//Fue asignada por el coordinador
							}
						}
					}else{
						if(parseInt($(xml).find('respuesta').text())== 1){
							$('#labelConsignador').show();
						}
					}
			}
		});		
	}
	
	
	function verHistorial(id){
		gridHistorico.url = "<%=request.getContextPath()%>/consultarHistoricoExpediente.do";
		gridHistorico.postData = {
				numeroExpediente: id

			};
		gridHistorico.datatype = "xml";			
		cargarHistorico();	
	}
	
	function cargarHistorico() {
		$("#gridHistorico").GridUnload();
	 	$("#gridHistorico").jqGrid(gridHistorico)
					.navGrid("#pagerGridHistorico",{edit:false,add:false,del:false});
		try {
			ajustarGridAlCentro($("#gridHistorico"));
		}catch(e){}
	}


		</script>
	
	<!--ESTILOS A PARA LAS TABS-->
	
	<style>
	
	#tabs { height: 100% } 
	.tabs-bottom { position: relative; } 
	.tabs-bottom .ui-tabs-panel { height: 550px; overflow: visible; } 
	.tabs-bottom .ui-tabs-nav { position: absolute !important; left: 0; bottom: 0; right:0; padding: 0 0.2em 0.2em 0; } 
	.tabs-bottom .ui-tabs-nav li { margin-top: -2px !important; margin-bottom: 1px !important; border-top: none; border-bottom-width: 1px; }
	.ui-tabs-selected { margin-top: -3px !important; }
	.tabEstilo  { height: 350px; overflow: auto; }

	label.vinculo {
		color: blue;
		cursor: pointer;
		text-decoration:underline
	}
	</style>
	
</head>
<body>
	
	<div id="tabsPrincipal">
		<ul>

			<li><a href="#tabsconsultaprincipal-1">Generales</a></li>
			<li><a href="#tabsconsultaprincipal-2">Historial</a></li>
			<li><a href="#tabsconsultaprincipal-3">Responsables del expediente</a></li>		
		</ul>
		
		<div id="tabsconsultaprincipal-1">
		<br>
	
		<div align="center" style="width: 100%">
			<table id="gridGeneralesDelExpediente"></table>
			<!--div id="pagerGridGeneralesDelExpediente"></div -->
			
			<br>
			<table id="gridInfoActualDelExp"></table>
			<div id="pagerGridInfoActualDelExp"></div>
			
			<br>
			<br>
			<div id="tblProcesoExpediente">
				<table id="gridProcesoDelExp"></table>
				<!-- div id="pagerGridProcesoDelExp"></div-->
			</div>
			
			
			<br>			
			<h1 id="labelConsignador">* El expediente no ha sido asignado por el Coordinador</h1>
	
		</div>		
			
			
		</div>
		
		<div id="tabsconsultaprincipal-2">
		<div align="center">			
				<br>
				<table id="gridBitacoraNumerosExpediente"></table>
				<div id="pagerGridBitacoraNumerosExpediente"></div>
				
			</div>
		</div>
			
		<div id="tabsconsultaprincipal-3">
			<br>
			<div align="center">
				<table id="gridHistorico"></table>
				<div id="pagerGridHistorico"></div>
				
				<br>
				<br>
				<table id="gridBitacoraPermisos"></table>
				<div id="pagerGridBitacoraPermisos"></div>
				
			</div>
		</div>			
	</div>
	
	
	<div id="dialog-detalleDetenidos" title="DETALLE" style="display:none">				
			<br>
			<div align="center">
				<table id="gridDetenidosDelExp"></table>
				<div id="pagerGridDetenidosDelExp"></div>
			</div>
	</div>
	
	<div id="dialog-detalleAudiencias" title="DETALLE" style="display:none">				
			<br>
			<div align="center">
				<table id="gridAudienciasDelExp"></table>
				<div id="pagerGridAudienciasDelExp"></div>
			</div>
	</div>
	
	<div id="dialog-detalleExpedientesPM" title="DETALLE" style="display:none">				
			<br>
			<div align="center">
				<table id="gridInfoActualDelExpPM"></table>
				<div id="pagerGridInfoActualDelExpPM"></div>
			</div>
	</div>
	
	<div id="dialog-detalleExpedientesUAVD" title="DETALLE" style="display:none">				
			<br>
			<div align="center">
				<table id="gridInfoActualDelExpUAVD"></table>
				<div id="pagerGridInfoActualDelExpUAVD"></div>
			</div>
	</div>
	
</body>

<script type="text/javascript">
	$( "#dialog-detalleDetenidos" ).dialog();
	$( "#dialog-detalleDetenidos" ).dialog( "destroy" );
</script>

</html>