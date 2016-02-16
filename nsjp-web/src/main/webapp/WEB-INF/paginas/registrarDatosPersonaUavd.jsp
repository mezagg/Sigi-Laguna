<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>    
<%@page pageEncoding="UTF-8" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html">
	<%@ page import="mx.gob.segob.nsjp.comun.enums.calidad.Calidades" %>
	
	<%@ page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades" %>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
	<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
        <%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
        <%@ page import="mx.gob.segob.nsjp.comun.enums.objeto.Objetos" %>
        <%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.CategoriasActividad" %>
        <%@ page import="mx.gob.segob.nsjp.comun.enums.expediente.OrigenExpediente"%>
        <%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros"%>
        <%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>
        <%@ page import="mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError" %>
        <%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
	<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento"%>
	<%@ page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
        <%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
	
	
	<title>Registrar Datos Persona</title>
        

<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/south-street/jquery-ui.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />


<!--Se importan los scripts necesarios-->
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>

<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/prettify.js"></script>
		
<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	
	<!--ESTILOS PARA LAS TABS-->
	<style>
	#tabs { height: 670px; } 
	.tabs-bottom { position: relative; } 
	.tabs-bottom .ui-tabs-panel { height: 500px; overflow: auto; } 
	.tabs-bottom .ui-tabs-nav { position: absolute !important; left: 0; bottom: 0; right:0; padding: 0 0.2em 0.2em 0; } 
	.tabs-bottom .ui-tabs-nav li { margin-top: -2px !important; margin-bottom: 1px !important; border-top: none; border-bottom-width: 1px; }
	.ui-tabs-selected { margin-top: -3px !important; }
	</style>


</head>
<script type="text/javascript">
            
var resRad;
        var msjProbableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
	var valorVacio = "<div style=\"background-color: #f2f2f2; color:#393939;\"> </div>";
	var valorNO = "<div style=\"background-color: #f2f2f2; color:#393939;\">No </div>";
	var valorSI = "<div style=\"background-color: #f2f2f2; color:#393939;\">Si </div>";
	 var CONTEXT_ROOT = '<%= request.getContextPath()%>';
	 var idSolicitud=0;
	 var idNumeroExpediente=0;
	 var numeroExpediente="";
	 var numeroExpedienteId=0;
	 var idCompuesto="0,0,0,0";
	 var idWindowIngresarVictima=1;
	 var idWindowIngresarProbResponsable=1;
	 var idTipoSolicitud=2810;
	 var numeroSesionn=3;
	 var idWindowGenerarNotas=1;//variable para la ventana de Generar notas
	 var contextoPagina = "${pageContext.request.contextPath}";
         var idNumeroExpedienteOp="";
	 var idExpedienteop;
	 var idWindowPantallaActuaciones = 1;
	 var extensionesPermitidasAudio = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getExtensionesPermitidasAlAdjuntarAudio() %>';
	 var extensionesPermitidasImagen = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getExtensionesPermitidasAlAdjuntarImagen() %>';
         var idWindowIngresarHechos = 1;
         var validaDelitoGrave='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getValidaDelitoGrave() %>';
	 var reloadGridDelito=false;
         var validaDelito=false;
         var actuacion=0;
         var confirmarCierreVentana ='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getMostrarMensajeConfirmacionEnDocumento() %>';       
         var validaTipoExpedienteReporte='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getValidaTipoExpedienteReporte() %>';
         
	 $(document).ready(function() {

		 var areaId;
		
		$("#tabsPrincipal").tabs();
		$("#tabschild6" ).tabs();
		$("#tabsconsultaprincipal-2").tabs();
                $( "#tabschild17" ).tabs();
                $( "#tabschild7" ).tabs();
		$("#btnAgregarNotaEvaluacion").click(guardaNotaEvaluacion);
                $("#ingresarHechos").click(ingresarHechos);
                
                $('#tdCbxAgentesCoorUI').hide();
		$('#tdCbxAgentesCoorUI1').hide();
			 
		$('#tdCbxAgentesCoorJAR').hide();
		$('#tdCbxAgentesCoorJAR1').hide();
                
                $('#idAsignarAgenteMp').hide();
		$("#idAsignarFacilitador").hide();
		$("#idReasignarFacilitador").hide();
                
//		$("#cbxAccionesTabTS").change(seleccionaActuacionTrabajoSocial);
//		$("#cbxAccionesTabJ").change(seleccionaActuacionJuridico);
                
		//Para escuchar los eventos de psicologico
//		$("#cbxAccionesTab").change(seleccionaActuacionPsicologico);
		 
		var pantalla='<%= request.getAttribute("pantalla")%>';
		var asignado='<%= request.getAttribute("asignado")%>';
		
		if('<%= request.getParameter("idCompuesto")%>'!=null)
		{
			idCompuesto='<%= request.getParameter("idCompuesto")%>';
		}
                
                if(confirmarCierreVentana !=null && (confirmarCierreVentana == "1" || confirmarCierreVentana==1)) {
				confirmarCierreVentana = true;
		}else{
				confirmarCierreVentana = false;
		}
		
		var idsNecesarios=idCompuesto.split(",");
		idSolicitud=idsNecesarios[0];
		idNumeroExpediente=idsNecesarios[1];
		numeroExpediente=idsNecesarios[2];
		numeroExpedienteId=idsNecesarios[3];
		idExpedienteop = idsNecesarios[1];
		idTipoSolicitud=idsNecesarios[4];
		cargarInvolucradosExpediente(numeroExpedienteId);
		cargaGridDocumentosDigitalesPropios();
		cargarHechoExpediente(idExpedienteop);
                
                idNumeroExpedienteOp='<%= request.getParameter("idNumeroExpedienteop")%>';
                
                if(idNumeroExpedienteOp == 'null'){
                    idNumeroExpedienteOp='<%= request.getSession().getAttribute("idExpedienteConsul")%>';
		}
                
                $("#tapDelitoYRelaciones").one("click", function() {
		    $('#tapDelitoYRelaciones').addClass("cargando");				
			//Carga grid con el catalogo de los delitos
			cargaGridDelitos();
			//consultamos las actividades dependiendo de los delitos del expediente
		   	muestraActividadesSugeridasEnConsultaExpediente();
        		$('#tapDelitoYRelaciones').removeClass("cargando");
		});
                
                       
                //seteamos los listener de los radios para la relacion de Delitos por Person o por Delito
		$("#rdbMenuInterRelDelXPersona").bind("click",ocultaMuestraTblsRelacionarDelitos);
		$("#rdbMenuInterRelDelXDelito").bind("click",ocultaMuestraTblsRelacionarDelitos);
		$("#rdbMenuInterRelDelXTodos").bind("click",ocultaMuestraTblsRelacionarDelitos);
                
		//grid para los documentos
		jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
			url:'<%=request.getContextPath()%>/consultarDocumentos.do?idExpedienteop='+numeroExpedienteId, 
			datatype: "xml",
			colNames:['&Aacute;rea del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento', 'Documento', 'Es Parcial'],
			colModel:[ 	{name:'area',index:'area', width:200,hidden:true},
						{name:'FechaActividad',index:'fechaActividad', width:170},							
						{name:'NombreActividad',index:'nombreActividad', width:400},
			           	{name:'Tipo',index:'tipo', width:155}, 
						{name:'Nombre',index:'nombre', width:255},
			           	{name:'Fecha',index:'fecha', width:170},
			           	{name:'Documento',index:'documento', width:170},
			           	{name:'EsParcial',index:'esParcial', hidden:true}
						],
			pager: jQuery('#pager1'),
			rowNum:10,
			rowList:[10,20,30,40,50],
			autowidth: false,
			width:1100,
			sortname: 'turno',
			viewrecords: true,
			id: 'divgrid',
			onSelectRow: function(id){
					//consultaPDFUAVD(id);
				var retd = jQuery("#gridDetalleFrmPrincipal").jqGrid('getRowData',id);
				var titulo = retd.Nombre;
				if(retd.EsParcial){
					noEsParcial = retd.EsParcial.indexOf('false');
					if(noEsParcial > 0){//No es parcial
						consultaPDF(id);
					}
					else{//Es parcial
						idWindowPantallaActuaciones++;
						$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:true});
						$.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?documentoId='+id+'&numeroUnicoExpediente='+numeroExpediente+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&esTransaccional='+true+'" width="1140" height="400" />');
						$("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
					}
				 }
				

				},
			sortorder: "desc"
		}).navGrid('#pager1',{edit:false,add:false,del:false});
		$("#gview_gridDetalleFrmPrincipal .ui-jqgrid-bdiv").css('height', '500px');
		
		
		if(pantalla==5){
			if(idTipoSolicitud==<%= TiposSolicitudes.ATENCION_PSICOLOGICA.getValorId() %>)
			{
		 		areaId="<%=Areas.ATENCION_PSICOLOGICA.parseLong()%>";
			}
			if(idTipoSolicitud==<%= TiposSolicitudes.TRABAJO_SOCIAL.getValorId() %>)
			{
				
				areaId="<%=Areas.ATENCION_VICTIMAS.parseLong()%>";
			}
			if(idTipoSolicitud==<%= TiposSolicitudes.ATENCION_JURIDICA.getValorId() %>)
			{
				areaId="<%=Areas.ATENCION_JURIDICA.parseLong()%>";
			}
			pantalla==5;
	 	}else if(idTipoSolicitud==<%= TiposSolicitudes.ATENCION_PSICOLOGICA.getValorId() %>)
		{
			pantalla=1;
		}
		else if(idTipoSolicitud==<%= TiposSolicitudes.TRABAJO_SOCIAL.getValorId() %>)
		{
			pantalla=2;
		}
		else if(idTipoSolicitud==<%= TiposSolicitudes.ATENCION_JURIDICA.getValorId() %>)
		{
			pantalla = 3;
		}
		
		if(pantalla==2){
			$( "#btnSolicitarAyuda" ).hide();
                        cargaActuaciones();
                        ocultaMuestraTabVisor("tabTabsAudiencias",0);
//			cargaActuacionesTs();
		}else if(pantalla==3){
			$( "#btnSolicitarAyuda" ).hide();
                        cargaActuaciones();
                        ocultaMuestraTabVisor("tabTabsAudiencias",0);
//			cargaActuacionesJ();
		}else if(pantalla==1){
			$( "#btnSolicitarAyuda" ).hide();
			cargaActuaciones();
                        ocultaMuestraTabVisor("tabTabsAudiencias",0);
		}else{
			cargaComboFuncionariosXArea(areaId);
		}
		//cargaGridOpciones();
		cargaInformacionDeResumen();
		//Se oculta la pesta&ntilde;a de NOtas
//		$("#tabNotas").hide();
		
		if(asignado==1 || asignado=='1'){
			$( "#btnSolicitarAyuda" ).hide();
			ocultaFuncionarios();
		}
                
                
                $("input[name='rdActuaciones']").change(function() {
                    var sinCatuie = $(':radio[name=rdActuaciones]:checked').val();
                    cargaActuaciones(sinCatuie);
                });
                
                $("#cbxAccionesTab").delegate('a','click',function(event) {
                    seleccionaActuacion($(this).selectable());
                });
                        
                $("#cbxOficiosTab").delegate('a','click',function(event) {
                    seleccionaActuacion($(this).selectable());
                });
                
                $("#tapActuaciones").one("click", function() {
                    cargaActuaciones(1);
		}); 
                
});

	 function ocultaFuncionarios(){

		 $("#cbxFuncionariosAyuda").hide();
		 $("#tdFuncionario").hide();
	}
	
	/************ FUNCION PARA OCULTAR-MOSTRAR LOS TABS DEL VISOR***************/
	function ocultaMuestraTabVisor(claseTab,bandera)
	{
		if(parseInt(bandera)==0)//oculta
		{
			$("."+claseTab).hide();
		}
		else///muestra
		{
			$("."+claseTab).show();
		}
	}
	
/*******************************************************FUNCIONALIDAD PARA LA CEJA DOCUMENTOS PROPIOS**********************************************************/
/*
*Funcion que carga el grid con los nombre de los documentos digitales asociados 
*al id de la solicitud de serv. periciales
*/
function cargaGridDocumentosDigitalesPropios(){ 

		jQuery("#gridAtencionPsicologica").jqGrid({
			url:'<%=request.getContextPath()%>/consultarSesionesUAVD.do?idNumeroExpediente='+numeroExpedienteId, 
			datatype: "xml", 
			colNames:['Sesi&oacute;n','Tipo de Sesi&oacute;n','Fecha de Atenci&oacute;n','Asisti&oacute;'],
			colModel:[	{name:'Sesion',index:'sesion', width:100},
			          	{name:'Tipo',index:'index1', width:250},
			          	{name:'fecha',index:'fecha', width:160},
			          	{name:'asistencia',index:'index3', width:150},
			       
						],
			pager: jQuery('#pagerGridAtencionPsicologica'),
			rowNum:10,
			rowList:[10,20,30],
			width:700,
			sortname: 'nombreDocumento',
			viewrecords: true,
			sortorder: "desc",
			//multiselect:true,
			ondblClickRow: function(rowid) {
				var renglon = rowid;
				var ok=rowid.split(",");
				
				if (ok[0]==1) {
					
					abrirEntrevistaInicial(ok[1]);							
				}
                if (ok[0]==2) {
					
                	abrirEntrevistaComplementaria(rowid);							
				}
				if (ok[0]>=3) {
					//recuperamos valor de asistencia de la tabla
					var renglon = jQuery("#gridAtencionPsicologica").jqGrid('getRowData',rowid);
					$('input[name=radioAsistio]:eq(0)').attr('checked','checked');
					if(renglon.fecha == valorVacio){
						//abre dialogo para cambiar el estatus de la asistencia
					popopAsistencia(ok[1]);
					}else{
						if(renglon.asistencia == valorNO){
							//opcion seleccionada de asistencia NO
							resRad = "2";
							//abre el visor de las notas
							abrirNotasEvolucion(ok[1]);
				}
						else if(renglon.asistencia == valorSI){
							//opcion seleccionada de asistencia SI
							resRad = "1";
							//abre el visor de las notas
							abrirNotasEvolucion(ok[1]);
						}
					}
				}
			}
		}).navGrid('#pagerGridAtencionPsicologica',{edit:false,add:false,del:false});
		
	
}	

function cerrarVentana(id){
	$.closeWindow(id);
	jQuery("#gridAtencionPsicologica").trigger("reloadGrid");
}

function recargarGridDeSesiones(){
	jQuery("#gridAtencionPsicologica").trigger("reloadGrid");
}


function abrirEntrevistaInicial(id){
	$.newWindow({id:"iframewindowVisorEntrevistaInicial", statusBar: true, posx:151,posy:69,width:939,height:403,title:"Entrevista Inicial", type:"iframe"});
    $.updateWindowContent("iframewindowVisorEntrevistaInicial","<iframe src='<%= request.getContextPath() %>/visorEntrevistaInicial.do?rowid="+id+"' width='939' height='403' />");
    $("#" +"iframewindowVisorEntrevistaInicial" +" .window-maximizeButton").click();
}
function abrirEntrevistaComplementaria(id){

	$.newWindow({id:"iframewindowVisorTipoSesion", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Entrevista Complementaria", type:"iframe"});
    $.updateWindowContent("iframewindowVisorTipoSesion","<iframe src='<%= request.getContextPath() %>/visorEntrevistaComplementaria.do?rowid="+id+"' width='1140' height='400' />");
    $("#" +"iframewindowVisorTipoSesion" +" .window-maximizeButton").click();
}
function abrirNotasEvolucion(id){

	$.newWindow({id:"iframewindowVisorNotasEjecucion", statusBar: true, posx:200,posy:50,width:1140,height:400,title:"Nota de Evoluci&oacute;n", type:"iframe"});
    $.updateWindowContent("iframewindowVisorNotasEjecucion","<iframe src='<%= request.getContextPath() %>/visorNotaEjecucion.do?rowid="+id+"' width='1140' height='400' />");
    $("#" +"iframewindowVisorNotasEjecucion" +" .window-maximizeButton").click();
}

function cargaGridOpciones(){

	var x=3;
	for(var i=1;i<=x; i++){
		
	if(i==1){
		
	var datarow = {Sesion:""+i+"",Tipo:"Entrevista Inicial",fecha:""};
	var su=jQuery("#gridAtencionPsicologica").jqGrid('addRowData',i,datarow);
	}

	if(i==2){
		
		var datarow = {Sesion:""+i+"",Tipo:"Entrevista Complementaria",fecha:""};
		var su=jQuery("#gridAtencionPsicologica").jqGrid('addRowData',i,datarow);
		}
	if(i==3){
		
		var datarow = {Sesion:""+i+"",Tipo:"Nota de Evoluci&oacute;n",fecha:""};
		var su=jQuery("#gridAtencionPsicologica").jqGrid('addRowData',i,datarow);
		}
	}

}


/*
*Funcion para madar consultar la informacion de resumen de visitaduria
*/
function cargaInformacionDeResumen(){
	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/consultarInfoResumenUAVD.do',
		data: 'idSolicitud='+idSolicitud,
		dataType: 'xml',
		success: function(xml){
			$(xml).find('DatosGeneralesExpedienteUAVDDTO').each(function(){
				$("#spanTipoSolicitud").text($(this).find('tipoSolicitud').text());
				$("#spanNombreVictima").text($(this).find('nombreDeLaVictima').text());
				$("#spanDelito").text($(this).find('delito').text());
				$("#spanAMPSolicito").text($(this).find('ampSolicitante').text());
				$("#spanAreaSolicito").text($(this).find('areaSolicitante').text());
				$("#spanFechaCreacion").text($(this).find('fechaDeCreacionDelExpediente').text());
				$("#spanEstatusExpediente").text($(this).find('estatusDelExpediente').text());
				$("#spanEstatusActuaciones").text($(xml).find('estatusActuacion').text());
				$("#spanResponsableTramite").text($(xml).find('responsableTramite').text());
			});
		}
	});
}

function popopAsistencia(rowid){ 

	$( "#dialogAsistencia" ).dialog({
		title: '&iquest;Asisti&oacute; la V&iacute;ctima?', 
		autoOpen: true,
		resizable: false,
		height:160,
		width:200,
		modal: true,
		buttons: {
			"Aceptar": function() {
				//recuperamos la opcion seleccionada de asistencia
				resRad= $(':radio[name=radioAsistio]:checked').val();
				if(resRad ==  "1" || resRad ==  "2"){
				abrirNotasEvolucion(rowid);	
				$( this ).dialog( "close" );
				}
			},
			"Cancelar": function() {
				
				$( this ).dialog( "close" );
			}
		}
	});
}
	function documentos(){
		 jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
					{url:'<%= request.getContextPath()%>/consultarDocumentos.do?idExpedienteop='+numeroExpedienteId, 
					datatype: "xml" ,
					success: function(xml){
						 //customAlert('<%= request.getSession().getAttribute("totalRegistrosDocumentos")%>');	
					 } 
					});
		 $("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
	}

	/**
	*@Deprecated Ver function consultaPDF
	*/
	function consultaPDFUAVD(id){
		document.frmDoc.documentoId.value = id;
		document.frmDoc.submit();
	}
	
	//Variable para controlar el action para consultar pdf's
	var accionConsultarPdf="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do";
	
	/*
	*Funcion que aplica submit a la forma para abrir el documento solicitado
	*id= id del documento seleccionado en el grid de documentos
	*As&iacute; se obtenia anteriormente:
	*	
	*document.frmDoc.documentoId.value = id;
	*document.frmDoc.submit();
	*/
	function consultaPDF(id){
		
		document.frmDoc.action=accionConsultarPdf+"?documentoId="+id;
		document.frmDoc.submit();
	}
	
	function cargarInvolucradosExpediente(idNumeroExpedienteA){
    	$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/ConsultaInvolucradosExpedienteUAVD.do',
    		data: 'idNumeroExpediente='+idNumeroExpedienteA,
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    	      $(xml).find('involucradoViewDTO').each(function(){
	    	      
	    	     if($(this).find('calidad').text() == '<%= Calidades.VICTIMA_PERSONA.getValorId() %>'){
			    	      var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificarVictima(' + $(this).find('involucradoId').text() + ');">';
			    	      if($(this).find('nombre').text()=='null'){
							  liga += 'Desconocido';
						  }else{
							  liga += $(this).find('nombre').text();
						  }
						  liga += '</a></td></tr>';
		    	    	  $('#tblVictima').append(liga);
					}
	    	     if($(this).find('calidad').text() == '<%= Calidades.DENUNCIANTE.getValorId() %>' && $(this).find('esVictima').text() == "true"){

	    	    	 var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificarVictima(' + $(this).find('involucradoId').text() + ');">';
	    	    	 if($(this).find('nombre').text()=='null'){
	    	    	 		liga += 'Desconocido';
	    	    	 	}else{
	    	    	 		liga += $(this).find('nombre').text();
	    	    	 	}
	    	    	 liga += '</a></td></tr>';
	    	    	 $('#tblVictima').append(liga);
	    	    }
	    	    	 				  
			      if($(this).find('calidad').text() == '<%= Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId() %>' || $(this).find('calidad').text() == '<%= Calidades.PROBABLE_RESPONSABLE_ORGANIZACION.getValorId() %>'){
		    	      var liga = '<tr id="' + $(this).find('involucradoId').text() + '"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a onclick="modificarProbResponsable(' + $(this).find('involucradoId').text() + ');">';
		    	      if($(this).find('nombre').text()=='null'){
						  liga += 'Desconocido';
					  }else{
						  liga += $(this).find('nombre').text();
					  }
					  liga += '</a></td></tr>';
	    	    	  $('#tblProbableResponsable').append(liga);
	    	      }
	    	       
    	      });
    		}	
    	});
	}

	//Funcion para refrescar el nombre de la victima cuando se guarda nuevamente
	function cargaVictima(nombre,id){
		var row=$('#'+id);
		$(row).remove();
		$('#tblVictima').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarVictima" onclick="modificaVictima('+id+')">'+nombre+'</a></td></tr>');
		cargaInformacionDeResumen();
	} 
		
	//Abre una nueva ventana de crear una nueva victima
	function modificarVictima(id) {
		idWindowIngresarVictima++;
		$.newWindow({id:"iframewindowIngresarVictima" + idWindowIngresarVictima, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Modificar V&iacute;ctima", type:"iframe"});
	    $.updateWindowContent("iframewindowIngresarVictima" + idWindowIngresarVictima,'<iframe src="<%= request.getContextPath() %>/IngresarVictima.do?idVictima='+id +'&numeroExpediente='+numeroExpediente +'&isUavd=1" width="1100" height="530" />');		
	}
	
	//Funcion para quitar la victima del visor de elementos
	function eliminarVictima(id)
	{
		var row=$('#'+id);
		$(row).remove();
	}

	//Funcion para refrescar el nombre del probable responsable cuando se guarda nuevamente
	function cargaProbableResponsable(nombre,id){
		var row=$('#'+id);
		$(row).remove();
		nombre=nombre+" - "+'<bean:message key="indiciado" />';
		$('#tblProbableResponsable').append('<tr id="'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarProbableResponsable" onclick="modificaProbableResponsable('+id+')">'+nombre+'</a></td></tr>');
	}

	//Abre una nueva ventana de probable responsable
	function modificarProbResponsable(id) {
		var probableResponsableProp = '<bean:message key="modProbaleResponsableTitulo"/>';
		idWindowIngresarProbResponsable++;
		var muestraDetenido=0;
		$.newWindow({id:"iframewindowModificarProbResponsable" + idWindowIngresarProbResponsable, statusBar: true, posx:75,posy:30,width:1100,height:530,title:probableResponsableProp, type:"iframe"});
		$.updateWindowContent("iframewindowModificarProbResponsable" + idWindowIngresarProbResponsable,'<iframe src="<%= request.getContextPath() %>/IngresarProbResponsable.do?idProbableResponsable='+id +'&calidadInv=PROBABLE_RESPONSABLE&numeroExpediente='+numeroExpediente +'&detenido='+muestraDetenido+'&isUavd='+1+'" width="1100" height="530" />');			
	}
        
       
	
	/*
	*Funcion que realiza la carga del combo de Actuaciones
	*/
       function cargaActuaciones(sinCatuie) {
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
                               
                                if($(resp).text() == "listaOficios"){
                                    ofic = $(this).find('catActuaciones');
                                    ofic.each(function(){
                                        $('#cbxOficiosTab').append('<li data-value="' + $(this).find('clave').text() + '"><img src="<%=request.getContextPath() %>/resources/images/oficio.jpg" width="15" height="15" align="absmiddle"/><a href="#" class="actuaciones" idselected="'+$(this).find('clave').text()+'">' + $(this).find('valor').text() + '</a></li>');

                                    });
                                }
                                if($(resp).text() == "listaActuaciones"){
                                    act = $(this).find('catActuaciones');
                                    act.each(function(){
                                        $('#cbxAccionesTab').append('<li data-value="' + $(this).find('clave').text() + '"><img src="<%=request.getContextPath() %>/resources/images/play.png" width="15" height="15" align="absmiddle"/><a href="#" idselected="'+$(this).find('clave').text()+'">' + $(this).find('valor').text() + '</a></li>');
                                    });
                                }
                                 
                             });     
                            $('#cbxAccionesTab').removeClass("cargando");
                            $('#cbxOficiosTab').removeClass("cargando");
                            $('#tapActuaciones').removeClass("cargando");
                            if(act.size() == 0 && ofic.size() == 0){ 
                                if(sinCatuie == 1){
                                    $("#rdbConUaei").attr('checked', true);
                                    $("#rdbSinUaei").attr('checked',false);
                                    $("#rdbSinUaei").attr("disabled", true);
                                    cargaActuaciones(0);
                                }else if(sinCatuie == 0) {
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
        
        function despliegaMensaje(contador, textoUno, textoDos, textoTres, tituloConfirm, selected, confActividadId, actividad, formaID, 
			titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave){
		if (contador == 0){
			contador++;
			customConfirm('<span style="font-size:20px">'+ textoUno +'</span>',tituloConfirm,
					function (){
						despliegaMensaje(contador, textoUno, textoDos, textoTres, tituloConfirm, selected, confActividadId, actividad, formaID, 
								titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave);
					});
		}else if (contador == 1){
			contador++;
			customConfirm('<span style="font-size:20px">'+ textoDos +'</span>',tituloConfirm,
					function (){
						despliegaMensaje(contador, textoUno, textoDos, textoTres, tituloConfirm, selected, confActividadId, actividad, formaID, 
								titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave);
					});
		}else if (contador == 2){
			contador++;			
			//Tiene el objetivo de mostrar un mensaje de confirmacion en canalizarAUnidadFiscalesInvestigadores.jsp en el metodo
			//crearPdf(), para el caso de DIRIGIR_A_LA_UNIDAD_DE_FISCALES_INVESTIGADORES y
			// para Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS
			if(actividad == '<%= Actividades.DIRIGIR_A_LA_UNIDAD_DE_FISCALES_INVESTIGADORES.getValorId()%>' ||
			   actividad == '<%= Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS.getValorId()%>'){				
				despliegaMensaje(contador, textoUno, textoDos, textoTres, tituloConfirm, selected, confActividadId, actividad, formaID, 
						titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave);
			}else{
				customConfirm('<span style="font-size:20px">'+ textoTres +'</span>',tituloConfirm,
						function (){
							despliegaMensaje(contador, textoUno, textoDos, textoTres, tituloConfirm, selected, confActividadId, actividad, formaID, 
									titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave);
						});	
			}
		}else if (contador == 3){
			ejecutaActuacion(selected, confActividadId, actividad, formaID, 
					titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave);
		}
	}
        
        
        function seleccionaActuacion(a){
		var selected = $(a);
                var confActividadId = selected.attr('idselected');
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
		if (parametroConfirm == '1' &&
				(actividad == '<%= Actividades.DIRIGIR_A_INSTANCIA_EXTERNA.getValorId() %>'
				|| actividad == '<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS.getValorId() %>' 
				|| actividad == '<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_FISCALES_INVESTIGADORES.getValorId() %>'
				|| actividad == '<%=Actividades.GENERAR_CONSTANCIA_DE_FALTA_DE_INTERES.getValorId() %>'
				|| actividad == '<%=Actividades.NO_ACEPTACION_DEL_SERVICIO_POR_PARTE_DEL_INVITADO.getValorId() %>'
				|| actividad == '<%=Actividades.GENERAR_CONSTANCIA_DE_TERMINACION_DEL_PROCEDIMIENTO.getValorId() %>'
				|| actividad == '<%=Actividades.DIRIGIR_A_ATENCION_TEMPRANA_PENAL.getValorId() %>')){
			var textoUno = '&#191;Est&aacute; seguro que requiere realizar la siguiente actuaci&oacute;n?:<br/>'+ nombreActividad;
			var textoDos = 'La actuaci&oacute;n que acaba de seleccionar cerrar&aacute; su expediente.<br/>'
						  +'&#191;Est&aacute; seguro que requiere '+nombreActividad+'?';
			var textoTres = "Ha aceptado cerrar su expediente.<br/>&#191;Desea Continuar?";
			if (actividad == '<%=Actividades.DIRIGIR_A_INSTANCIA_EXTERNA.getValorId() %>'){
				textoTres = 'Ha aceptado cerrar su expediente, porque no compete a esta Instituci&oacute;n.<br/>&#191;Desea Continuar?';
			}else if (actividad == '<%=Actividades.DIRIGIR_A_ATENCION_TEMPRANA_PENAL.getValorId() %>'){
				textoTres = 'Ha aceptado cerrar su expediente, generando el oficio para dirigir a Atenci&oacute;n Temprana.<br/>&#191;Desea Continuar?';
			}else if (actividad == '<%=Actividades.GENERAR_CONSTANCIA_DE_FALTA_DE_INTERES.getValorId() %>'){
				textoTres = 'Ha aceptado concluir por falta de inter&eacute;s su expediente, generado la constancia correspondiente.<br/>&#191;Desea Continuar?';
			}else if (actividad == '<%=Actividades.NO_ACEPTACION_DEL_SERVICIO_POR_PARTE_DEL_INVITADO.getValorId() %>'){
				textoTres = 'Ha aceptado cerrar su expediente, con la carta de no Aceptaci&oacute;n de Servicio por el Invitado.<br/>&#191;Desea Continuar?';
			}else if (actividad == '<%=Actividades.GENERAR_CONSTANCIA_DE_TERMINACION_DEL_PROCEDIMIENTO.getValorId() %>'){
				textoTres = 'Ha aceptado concluir su expediente, con la constancia de terminaci&oacute;n del procedimiento.<br/>&#191;Desea Continuar?';
			}
			var tituloConfirm = 'Validar actuaci&oacute;n';
			despliegaMensaje(0, textoUno, textoDos, textoTres, tituloConfirm, selected, confActividadId, actividad, formaID, 
			titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave);
		}else{
			ejecutaActuacion(selected, confActividadId, actividad, formaID, titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave);
		}
	}
            
       
        
        function validarReporte(){
            if(validaTipoExpedienteReporte !=null && (validaTipoExpedienteReporte == "1" || validaTipoExpedienteReporte==1)) {
		return true;
            }
		return false;
	}
        
        /*
		*Funcion para validar si se puede realizar la solicitud de
		*defensor
		*/
		function validarEjecucionSolicitudDeDefensor(actividad,estatusId,titulo, formaID){

			var parametros='';
			
			if (idNumeroExpedienteOp != undefined && idNumeroExpedienteOp != null
					&& idNumeroExpedienteOp != "") {
				parametros += "idNumeroExpediente=" + idNumeroExpedienteOp;
			}
			
			if (idExpedienteop != undefined && idExpedienteop != null
					&& idExpedienteop != "") {
				parametros += "&idExpediente=" + idExpedienteop;
			}
			
			if (numeroExpediente != undefined && numeroExpediente != null
					&& numeroExpediente != "") {
				parametros += "&numeroExpediente=" + numeroExpediente;
			}
			
			ejecutaAction("/validarSolicitudDeDefensor", function(respuesta){					
				if(parseInt($(respuesta).find('code').text())==0){
					if($(respuesta).find('body').text() != null 
							&& $(respuesta).find('body').text() != "null"
								&& $(respuesta).find('body').text() != ""){
						if($(respuesta).find('body').text() == "success"){
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
                
            /*
            *Funcion que permite saber si un expedietn tiene un delito grave
            */
            function existeDelitoGrave(){			
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
                
            function validacionUFI(actividad,estatusId,titulo, formaID, numeroExpediente, numeroExpedienteId){
			existeDelitoGrave();	
			if(existeDelitoGraveEnExpediente != ""){
				//Valida Delito Grave deshabilitado 
				 if(validaDelitoGrave !=null && (validaDelitoGrave == "0" || validaDelitoGrave==0)) {
					//Mostrar ventana de Canalizaci&oacute;n a la Unidad de Fiscales Investigadores
						idWindowPantallaActuaciones++;
		     			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:"", type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
		    			$.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath()%>/canalizarAUnidadFiscalesInv.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&actividadId='+actividad+'&numeroExpedienteId='+numeroExpedienteId+'&esTransaccional='+true+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'" width="1140" height="400" />');
				 }
				 else{
					 if( existeDelitoGraveEnExpediente == "false"){
						 customConfirm ("No existe delito grave. &iquest;Desea enviar a la unidad de fiscales investigadores?", "Aviso", 
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
       console.log("filter:"+filter);
                                    
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
 
                
        function ejecutaActuacion(selected, confActividadId, actividad, formaID, titulo, usaeditor, estatusId, habilitarTurno, validaDelitoGrave){
		if( actividad=='<%= Actividades.GENERAR_QUERELLA.getValorId() %>' || actividad=='<%= Actividades.GENERAR_DENUNCIA_EN_ATP.getValorId() %>'){
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/enviarReplicaCaso.do?idExpediente='+idExpedienteop,
				data: '',
				dataType: 'xml',
				async: true
			});
		}
		if(usaeditor== "true"){
		
			if (actividad == '<%= Actividades.SOLICITAR_AUDIENCIA.getValorId() %>') {

				if(confInstitucionId == '<%=Instituciones.PJ.getValorId()%>'){
					nuevaSolicitudPJATP();
				}else{
					//codigo para cambiar el estatus del expediente
					registrarActividadExpediente(actividad,estatusId,0);
	                $.newWindow({id:"iframewindowSolicitarAudiencia", statusBar: true, posx:20,posy:20,width:1030,height:570,title:"Solicitar Audiencia", type:"iframe"});
	                $.updateWindowContent("iframewindowSolicitarAudiencia",'<iframe src="<%= request.getContextPath() %>/solicitarAudiencia.jsp?idNumeroExpediente=' + idNumeroExpedienteOp + '&idExpedienteSoli='+ idExpedienteop+'&numeroExpediente='+numeroExpediente+'"    width="1040" height="570" />');
	                $("#" +"iframewindowSolicitarAudiencia" + " .window-maximizeButton").click();
				}
			}
			else if (actividad == '<%= Actividades.SOLICITAR_DEFENSOR_PUBLICO.getValorId()%>'){
				validarEjecucionSolicitudDeDefensor(actividad,estatusId,titulo, formaID);				
			}
			else if(actividad=='<%= Actividades.SOLICITAR_ATENCION_PSICOLOGICA_A_LA_DIRECCION_DE_ATENCION_A_VICTIMAS.getValorId() %>')
			{
				//verificamos si se tienen relaciones de delito-persona o delito-delito
				if(consultaTotalRelacionesDelitoPorTodos()>0)
				{
					$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:20,posy:20,width:940,height:350,title:"Solicitud de Ayuda a la Unidad de Atenci&oacute;n a V&iacute;ctimas", type:"iframe"});
		            $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/solicitarAyudaPsicologicaUAVD.do?formaId='+formaID+'&idExpedienteop='+idExpedienteop+'&numeroCaso='+numeroCaso+'&numeroUnicoExpediente='+numeroExpediente+'&idNumeroExpedienteOp='+idNumeroExpedienteOp+'"    width="1140" height="550" />');
		            $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
				}
				else
				{
					var texto = "Debe tener registrada la relaci&oacute;n de la v&iacute;ctima \n con el " +
								msjProbableResponsableProp +
								" y el delito"
					alertDinamico(texto);
				}
			}
			else if(actividad=='<%=Actividades.SOLICITAR_ATENCION_MEDICA_A_LA_DIRECCION_DE_ATENCION_A_VICTIMAS
					.getValorId()%>' || actividad=='<%=Actividades.SOLICITAR_ORIENTACION_LEGAL_A_LA_DIRECCION_DE_ATENCION_A_VICTIMAS
					.getValorId()%>' || actividad=='<%=Actividades.SOLICITAR_SEGURIDAD_POLICIAL_A_LA_DIRECCION_DE_ATENCION_A_VICTIMAS
					.getValorId()%>')
			{
                var area = 1;
    		 	idWindowPantallaActuaciones++;
		 		$.newWindow({id:"iframewindowGenerarDocumento"+ idWindowPantallaActuaciones, statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Elaborar Solicitud", type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
         		$.updateWindowContent("iframewindowGenerarDocumento" + idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/elaborarSolicitud.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&actividadId='+actividad+'" width="1140" height="550" />');
         		$("#" +"iframewindowGenerarDocumento"+ idWindowPantallaActuaciones + " .window-maximizeButton").click();
			}
			else if(actividad=='<%= Actividades.SOLICITAR_SERVICIO_PERICIAL.getValorId() %>'){
				 // Variable para definir el &aacute;rea de donde proviene la solicitud.
				 // Para Procuraduria el valor es 1
				 // Para Defensoria el valor es 2
				 //codigo para cambiar el estatus del expediente
				registrarActividadExpediente(actividad,estatusId,0);
				var area = 1;			
	            $.newWindow({id:"iframewindowSolicitud", statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Solicitar servicio pericial", type:"iframe"});
	            $.updateWindowContent("iframewindowSolicitud",'<iframe src="<%= request.getContextPath() %>/solicitarServicioPericial.do?numeroExpediente='+numeroExpediente+'&numeroExpedienteId='+idNumeroExpedienteOp+'&area='+area+'"    width="1140" height="550" />');
	            $("#" +"iframewindowSolicitud" + " .window-maximizeButton").click();
	     	}else if(actividad=='<%=Actividades.ELABORAR_OFICIO_INVESTIGACION_POLICIACA
					.getValorId()%>'){
				 var area = 1;			
				//codigo para cambiar el estatus del expediente
				registrarActividadExpediente(actividad,estatusId,0);
	            $.newWindow({id:"iframewindowSolicitudPolicia", statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Solicitar servicio policial", type:"iframe"});
	            $.updateWindowContent("iframewindowSolicitudPolicia",'<iframe src="<%= request.getContextPath() %>/solicitarServicioPolicial.do?numeroExpediente='+numeroExpediente+'&numeroExpedienteId='+idNumeroExpedienteOp+'&area='+area+'"    width="1140" height="550" />');
	            $("#" +"iframewindowSolicitudPolicia" + " .window-maximizeButton").click();
	     	}
			else if(actividad=='<%=Actividades.GENERAR_CONVENIO_DE_CONCILIACION_MEDIACION
					.getValorId()%>')
			{
				idWindowGenConvenio++;
				//codigo para cambiar el estatus del expediente
				registrarActividadExpediente(actividad,estatusId,0);
				$.newWindow({id:"iframewindowGenConvenio"+idWindowGenConvenio, statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Generar Convenio", type:"iframe"});
	            $.updateWindowContent("iframewindowGenConvenio"+idWindowGenConvenio,'<iframe src="<%= request.getContextPath() %>/generarConvenio.do?numeroExpediente='+numeroExpediente+'&numeroExpedienteId='+idNumeroExpedienteOp+'&area='+area+'&formaID='+formaID+'"    width="1140" height="550" />');
	            $("#" +"iframewindowGenConvenio" + " .window-maximizeButton").click();
			}
			else if(actividad=='<%=Actividades.GENERAR_CONSTANCIA_DE_SEGUIMIENTO_A_CONVENIO
					.getValorId()%>')
 			{
 				idWindowGenConvenio++;
 				//codigo para cambiar el estatus del expediente
				registrarActividadExpediente(actividad,estatusId,0);
 				$.newWindow({id:"iframewindowGenConvenio"+idWindowGenConvenio, statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Seguimiento Convenio", type:"iframe"});
 	            $.updateWindowContent("iframewindowGenConvenio"+idWindowGenConvenio,'<iframe src="<%= request.getContextPath() %>/generarSeguimientoConvenio.do?numeroExpediente='+numeroExpediente+'&numeroExpedienteId='+idNumeroExpedienteOp+'&area='+area+'"    width="1140" height="550" />');
 	           $("#" +"iframewindowGenConvenio" + " .window-maximizeButton").click();
 			}
 			else if(actividad=='<%=Actividades.REPORTE_ORDENES_DE_APREHENSION_CUMPLIDAS
				.getValorId()%>' || actividad=='<%=Actividades.CONCLUSION_POR_VISTA_DE_NO_EJERCICIO
				.getValorId()%>' || actividad=='<%=Actividades.CONCLUSION_POR_REPARACION_DEL_DANO
				.getValorId()%>')
			{
				ventanaNuevaConclusionOrdenAprension(actividad,formaID);
			}
			else{
				//incluir mi validacion
				if(actividad=='<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS
					.getValorId()%>' || actividad=='<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS_SIN_SUSPENDER_EXPEDIENTE.getValorId()%>'){
						if(!existeProbableResponsableDeMenuIntermedio()){
							alertDinamico("Necesita un "+msjProbableResponsableProp+" para poder canalizar a JAR.");
						}else{
							//Valida si aplica Reporte
							if(validarReporte()){
								tipoExpediente = consultaTipoExpediente();   
								if(tipoExpediente == '<%=OrigenExpediente.REPORTE.getValorId()%>' ){
										validacionDeDelitoUSC(actividad,estatusId,titulo, formaID, numeroExpediente);						
								}else{//No es un tipo Reporte
									if(pantallaSolicitada != null && (pantallaSolicitada == AGENTE_MP || pantallaSolicitada == COORDINADOR_AMP) ){
										validacionDeDelitoUSC(actividad,estatusId,titulo, formaID, numeroExpediente);	
									}else{
										alertDinamico("S&oacute;lo los tipo de expediente 'Reporte' se pueden canalizar a JAR");	
									}							
								}
							}else{//En caso de no aplicar la regla de reporte
								validacionDeDelitoUSC(actividad,estatusId,titulo, formaID, numeroExpediente);
							}	
						}
					
				}else{
					if(actividad =='<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_FISCALES_INVESTIGADORES
					.getValorId()%>'){
						
						
						//VALIDA EL TIPO DE EXPEDIENTE, DENUNCIA O QUERELLA
						if(validarReporte()){
							tipoExpediente = consultaTipoExpediente();
							if(tipoExpediente == 0){
								alertDinamico("Se debe registrar la descripci&oacute;n de los hechos");
							}else if(tipoExpediente == '<%=OrigenExpediente.DENUNCIA.getValorId()%>' || tipoExpediente == '<%=OrigenExpediente.QUERELLA.getValorId()%>'){
								validacionUFI(actividad,estatusId,titulo, formaID, numeroExpediente, numeroExpedienteId);						
							}else{//No es un tipo Reporte
								if(pantallaSolicitada != null && (pantallaSolicitada == AGENTE_MP || pantallaSolicitada == COORDINADOR_AMP) ){
									validacionUFI(actividad,estatusId,titulo, formaID, numeroExpediente, numeroExpedienteId);	
								}else{
									alertDinamico("Un expediente de tipo 'Reporte' no puede ser enviado a UI");	
								}							
							}
						}else{//En caso de no aplicar la regla de reporte
							validacionUFI(actividad,estatusId,titulo, formaID, numeroExpediente, numeroExpedienteId);
						}				

					}else{
						if(actividad=='<%=Actividades.ELABORAR_OFICIO_INVESTIGACION_POLICIAL.getValorId()%>'){
					 		var area = 1;
					 		var tipoSolicitud=<%= TiposSolicitudes.POLICIA_MINISTERIAL.getValorId() %>;
					 		idWindowPantallaActuaciones++;
					 		$.newWindow({id:"iframewindowGenerarDocumento"+ idWindowPantallaActuaciones, statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Elaborar Solicitud", type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
		             		$.updateWindowContent("iframewindowGenerarDocumento"+ idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/elaborarSolicitud.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&idTipoSolicitud='+tipoSolicitud+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&actividadId='+actividad+'" width="1140" height="550" />');
		             		$("#" +"iframewindowGenerarDocumento"+ idWindowPantallaActuaciones + " .window-maximizeButton").click();
						}else{
							//codigo para cambiar el estatus del expediente
							idWindowPantallaActuaciones++;
			     			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
			    		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'&actividadId='+actividad+'&esTransaccional='+true+'" width="1140" height="400" />');
			    		    																																																																											
			    		    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();
						}
					}
				}
             }
		}else{
			if (actividad == '<%= Actividades.REGISTRAR_AMPARO.getValorId() %>') {
                $.newWindow({id:"iframewindowRegistrarAmparo", statusBar: true, posx:20,posy:20,width:450,height:500,title:"Registrar Amparo", type:"iframe"});
                $.updateWindowContent("iframewindowRegistrarAmparo",'<iframe src="<%= request.getContextPath() %>/registrarAmparo.jsp?idNumeroExpediente=' + idNumeroExpedienteOp + '&idExpedienteSoli='+ idExpedienteop+'&numeroExpediente='+numeroExpediente+'"    width="450" height="500" />');
                $("#" +"iframewindowRegistrarAmparo" + " .window-maximizeButton").click();
			}else{
				if(actividad !='<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_FISCALES_INVESTIGADORES
					.getValorId()%>' && actividad !='<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_FISCALES_INVESTIGADORES
					.getValorId()%>'){
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
       
//	function cargaActuaciones() {
//		var id=0;
//		$('#cbxAccionesTab').empty();
//		$('#cbxAccionesTab').append('<option value="-1">-Seleccione-</option>');
//		$.ajax({
//			type: 'POST',
//			url: '<%= request.getContextPath()%>/cargarActuaciones.do?id='+id+'&numeroExpediente='+numeroExpediente,
//			data: '',
//			dataType: 'xml',
//			async: false,
//			success: function(xml){
//				$(xml).find('catActuaciones').each(function(){
//					$('#cbxAccionesTab').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
//					});         
//			}
//		});
//	}
	/*
	*Funcion que realiza la carga del combo de Actuaciones
	*/
	function cargaActuacionesTs() {
		var id=0;
		$('#cbxAccionesTabTS').empty();
		$('#cbxAccionesTabTS').append('<option value="-1">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarActuaciones.do?id='+id+'&numeroExpediente='+numeroExpediente,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catActuaciones').each(function(){
					$('#cbxAccionesTabTS').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
			}
		});
	}
	
	/*
	*Funcion que realiza la carga del combo de Actuaciones
	*/
	function cargaActuacionesJ() {

		var id=0;
		$('#cbxAccionesTabJ').empty();
		$('#cbxAccionesTabJ').append('<option value="-1">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarActuaciones.do?id='+id+'&numeroExpediente='+numeroExpediente,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catActuaciones').each(function(){
					$('#cbxAccionesTabJ').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});                  
			}
		});
	}
	
	
	
	function asignarAyuda()
	{	
		var actuacionID;
		var estatusId = <%= EstatusExpediente.POR_ATENDER.getValorId() %>;
		var banderaMensaje = 0;
		
		if(idTipoSolicitud==<%= TiposSolicitudes.ATENCION_PSICOLOGICA.getValorId() %>)
		{
			actuacionID = <%= Actividades.ASIGNAR_SOLICITUD_DE_AYUDA.getValorId() %>;
			<%-- registrarActividadExpediente(<%= Actividades.ASIGNAR_SOLICITUD_DE_AYUDA.getValorId() %>,<%= EstatusExpediente.POR_ATENDER.getValorId() %>,0); --%>
		}
		else if(idTipoSolicitud==<%= TiposSolicitudes.TRABAJO_SOCIAL.getValorId() %>)
		{
			actuacionID = <%= Actividades.ENVIAR_ESTUDIO_SOCIOECONOMICO.getValorId() %>;
			<%-- registrarActividadExpediente(<%= Actividades.ENVIAR_ESTUDIO_SOCIOECONOMICO.getValorId() %>,<%= EstatusExpediente.POR_ATENDER.getValorId() %>,0); --%>
		}
		else if(idTipoSolicitud==<%= TiposSolicitudes.ATENCION_JURIDICA.getValorId() %>)
		{
			actuacionID = <%= Actividades.PROPORCIONAR_APOYO_LEGAL.getValorId() %>;
			<%-- registrarActividadExpediente(<%= Actividades.PROPORCIONAR_APOYO_LEGAL.getValorId() %>,<%= EstatusExpediente.POR_ATENDER.getValorId() %>,0); --%>
		}
		
		var funcio=$('#cbxFuncionariosAyuda option:selected').val();
		if(funcio != 0){
		 $.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/registrafuncionarioNumeroExpediente.do?funcionario='+funcio+'&idNumeroExpediente='+numeroExpedienteId,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){				
				if($(xml).find('respuesta').find('bandera').text() == '1'){
					registrarActividadExpediente(actuacionID,estatusId,0,funcio);
					window.parent.recargarGridSolsXAtendr();
				}
				if($(xml).find('respuesta').find('bandera').text() == '0'){
					customAlert("No se logr&oacute; asignar el expediente");
				}
			}
		 });
		}
		else{
			customAlert("Seleccion&eacute; un funcionario para atedender esta solicitud");
		}
	}
	
	function registrarActividadExpediente(actuacionID,estatusId,banderaMensaje, cveFuncionarioAsignado)
	{
			//Cambia la actividad del expediente
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/registraActividadExpediente.do?idExpediente='+idNumeroExpediente+'&idNumeroExpediente='+numeroExpedienteId+'&actuacion='+actuacionID+'&estatus='+estatusId+'&numExpe='+numeroExpediente+'&cveFuncionarioAsignado='+cveFuncionarioAsignado+'',
				data: '',
				dataType: 'xml',
				async: false,
				success: function(xml)
				{
					if(parseInt($(xml).find('actividadDTO').find('actividadId').text())>0)
					{
						customAlert("Se asign&oacute; correctamente la ayuda.");
						$("#btnSolicitarAyuda").hide();
						$('#cbxFuncionariosAyuda').attr("disabled","disabled");
						modificaSolicitudUAVD(<%= EstatusSolicitud.EN_PROCESO.getValorId() %>);
					}
					else
					{
						customAlert("Ocurri&oacute; un error al registrar la actividad.");
					}
				}
			});
	}
	
	/*
	  *Funcion para guardar una Nota de evaluacion
	  */
	  function guardaNotaEvaluacion()
	  {
	 	
	 	 $.ajax({
	 	     type: 'POST',
	 	     url: '<%=request.getContextPath()%>/guardarNotaEvaluacionNueva.do?sesion='+numeroSesionn+'&idNumeroExpediente='+numeroExpedienteId+'',
	 		 dataType: 'xml',
	 		 async: false,
	 		 success: function(xml){
	 			numeroSesionn=$(xml).find('NotaEvolucionDTO').find('numeroSesion').text();
	 			jQuery("#gridAtencionPsicologica").trigger("reloadGrid");
	 		  }
	 	});
	 	 
			jQuery("#gridAtencionPsicologica").trigger("reloadGrid");
	 	
	  }

	  function seleccionaActuacionPsicologico(){
			 
			var selected = $("#cbxAccionesTab option:selected");

			var confActividadId=selected.val();
			var actividad=0;
			var formaID=4;
			var titulo="op";
			var usaeditor="";
			var estatusId="";
			var nombreActividad="";
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
					nombreActividad = $(xml).find('confActividadDocumentoDTO').find('nombreActividad').text();
				}
			});

			if(usaeditor== "true"){
				registrarActividadExpedienteUsr(actividad,estatusId,0);
				if(actividad=='<%= Actividades.SUSPENDER_AYUDA.getValorId() %>')
				{
					$.newWindow({id:"iframewindowPsicologico", statusBar: true, posx:20,posy:20,width:940,height:350,title:nombreActividad, type:"iframe"});
		            $.updateWindowContent("iframewindowPsicologico",'<iframe src="<%= request.getContextPath() %>/solicitarSeguimientoEstudioSocioeconomicoUAVD.do?formaId='+formaID+'&idExpedienteop='+numeroExpedienteId+'&numeroUnicoExpediente='+numeroExpediente+'&idSolicitud='+idSolicitud+'&idTipoSolicitud='+idTipoSolicitud+'&opcEnvio=4" width="1140" height="550" />');
		            $("#" +"iframewindowPsicologico" + " .window-maximizeButton").click();
				}
				else if(actividad=='<%=Actividades.CONCLUIR_SATISFACTORIAMENTE.getValorId() %>')
				{
					$.newWindow({id:"iframewindowPsicologico", statusBar: true, posx:20,posy:20,width:940,height:350,title:nombreActividad, type:"iframe"});
		            $.updateWindowContent("iframewindowPsicologico",'<iframe src="<%= request.getContextPath() %>/solicitarSeguimientoEstudioSocioeconomicoUAVD.do?formaId='+formaID+'&idExpedienteop='+numeroExpedienteId+'&numeroUnicoExpediente='+numeroExpediente+'&idSolicitud='+idSolicitud+'&idTipoSolicitud='+idTipoSolicitud+'&opcEnvio=5" width="1140" height="550" />');
		            $("#" +"iframewindowPsicologico" + " .window-maximizeButton").click();
				}
				else if(actividad=='<%= Actividades.REINICIAR_AYUDA.getValorId() %>')
				{
					$.newWindow({id:"iframewindowPsicologico", statusBar: true, posx:20,posy:20,width:940,height:350,title:nombreActividad, type:"iframe"});
		            $.updateWindowContent("iframewindowPsicologico",'<iframe src="<%= request.getContextPath() %>/solicitarSeguimientoEstudioSocioeconomicoUAVD.do?formaId='+formaID+'&idExpedienteop='+numeroExpedienteId+'&numeroUnicoExpediente='+numeroExpediente+'&idSolicitud='+idSolicitud+'&idTipoSolicitud='+idTipoSolicitud+'&opcEnvio=6" width="1140" height="550" />');
		            $("#" +"iframewindowPsicologico" + " .window-maximizeButton").click();
				}
				else {
	     			$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:nombreActividad, type:"iframe", confirmarCierreVentana:true});
	    		    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'" width="1140" height="400" />');
	    		    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();					
				}
			}		
		}
	
		function seleccionaActuacionTrabajoSocial()
		{
			var selected = $("#cbxAccionesTabTS option:selected");
			var confActividadId=selected.val();
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
					nombreActividad = $(xml).find('confActividadDocumentoDTO').find('nombreActividad').text();
				}
			});
			actuacion=actividad;
			if(usaeditor== "true")
			{
				if(actividad=='<%= Actividades.ENVIAR_ESTUDIO_SOCIOECONOMICO.getValorId() %>')
				{
					registrarActividadExpedienteUsr(actividad,estatusId,0);
					$.newWindow({id:"iframewindowSolicitud", statusBar: true, posx:20,posy:20,width:940,height:350,title:nombreActividad, type:"iframe"});
		            $.updateWindowContent("iframewindowSolicitud",'<iframe src="<%= request.getContextPath() %>/solicitarSeguimientoEstudioSocioeconomicoUAVD.do?formaId='+formaID+'&idExpedienteop='+numeroExpedienteId+'&numeroUnicoExpediente='+numeroExpediente+'&idSolicitud='+idSolicitud+'&idTipoSolicitud='+idTipoSolicitud+'&opcEnvio=1"    width="1140" height="550" />');
				}
				else if(actividad=='<%= Actividades.SEGUIMIENTO_A_ESTUDIO_SOCIOECONOMICO.getValorId() %>')
				{
					registrarActividadExpedienteUsr(actividad,estatusId,0);
					$.newWindow({id:"iframewindowSolicitud", statusBar: true, posx:20,posy:20,width:940,height:350,title:nombreActividad, type:"iframe"});
		            $.updateWindowContent("iframewindowSolicitud",'<iframe src="<%= request.getContextPath() %>/solicitarSeguimientoEstudioSocioeconomicoUAVD.do?formaId='+formaID+'&idExpedienteop='+numeroExpedienteId+'&numeroUnicoExpediente='+numeroExpediente+'&idSolicitud='+idSolicitud+'&idTipoSolicitud='+idTipoSolicitud+'&opcEnvio=2"    width="1140" height="550" />');
				}
				else {
	     			$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:nombreActividad, type:"iframe", confirmarCierreVentana:true});
	    		    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'" width="1140" height="400" />');
	    		    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();					
				}
			}
		}
		
		function seleccionaActuacionJuridico(){
			var selected = $("#cbxAccionesTabJ option:selected");
			var confActividadId=selected.val();
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
			if(usaeditor== "true")
			{
				if(actividad=='<%= Actividades.ASESORIA_LEGAL_A_VICTIMA.getValorId() %>')
				{
					registrarActividadExpedienteUsr(actividad,estatusId,0);
					$.newWindow({id:"iframewindowSolicitud", statusBar: true, posx:20,posy:20,width:940,height:350,title:"Proporcionar Apoyo Legal", type:"iframe"});
		            $.updateWindowContent("iframewindowSolicitud",'<iframe src="<%= request.getContextPath() %>/solicitarSeguimientoEstudioSocioeconomicoUAVD.do?formaId='+formaID+'&idExpedienteop='+numeroExpedienteId+'&numeroUnicoExpediente='+numeroExpediente+'&idSolicitud='+idSolicitud+'&idTipoSolicitud='+idTipoSolicitud+'&opcEnvio=3"    width="1140" height="550" />');
				}else {
	     			$.newWindow({id:"iframewindowGenerarDocumento", statusBar: true, posx:200,posy:50,width:1140,height:400,title:titulo, type:"iframe", confirmarCierreVentana:true});
	    		    $.updateWindowContent("iframewindowGenerarDocumento",'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'" width="1140" height="400" />');
	    		    $("#" +"iframewindowGenerarDocumento" + " .window-maximizeButton").click();					
				}
			}
			else
			{
				document.frmDoc2.numeroUnicoExpediente.value = numeroExpediente;
				document.frmDoc2.formaId.value = formaID;
				document.frmDoc2.submit();
			}
		}
	
			function registrarActividadExpedienteUsr(actuacionID,estatusId,banderaMensaje){
				//Cambia la actividad del expediente
				$.ajax({
					type: 'POST',
					url: '<%= request.getContextPath()%>/registraActividadExpediente.do?idExpediente='+idNumeroExpediente+'&idNumeroExpediente='+numeroExpedienteId+'&actuacion='+actuacionID+'&estatus='+estatusId+'&numExpe='+numeroExpediente,
					data: '',
					dataType: 'xml',
					async: false,
					success: function(xml){
						if(parseInt(banderaMensaje)==1)
						{
							customAlert("Se asign&oacute; correctamente la ayuda.");	
						}
					}
				});
			}
			
			/********************************** fin FUNCIONES PARA GENERAR NOTAS  **********************************/
			
			function cargaComboFuncionariosXArea(areaId){
				
				$.ajax({
					type: 'POST',
					url: '<%= request.getContextPath()%>/consultarFuncionariosXArea.do?areaId='+areaId+'',
					data: '',
					dataType: 'xml',
					async: false,
					success: function(xml){
				    	$(xml).find('listaFuncionarios').find('funcionario').each(function(){

							var nombre="";
							var apPat="";
							var apMat="";
							
						    nombre = $(this).find('nombreFuncionario').first().text();
						    apPat = $(this).find('apellidoPaternoFuncionario').first().text();
						    apMat = $(this).find('apellidoMaternoFuncionario').first().text();
						    
					    	$('#cbxFuncionariosAyuda').append('<option value="' + $(this).find('claveFuncionario').text() + '">'+nombre+" "+apPat+" "+apMat+'</option>');
				    	});
					}
				});
			}
			
			
			function modificaSolicitudUAVD(estatus){
				//se cambia el estatus a en PROCESO
				$.ajax({
					type: 'POST',
					url: '<%=request.getContextPath()%>/modificaSolicitudUAVD.do',
					data: 'idSolicitud='+idSolicitud+'&estatus='+estatus,
					dataType: 'xml',
					success: function(xml){
						
					}
				});
			}
                        
        function notaExpediente(idNota)
	{
	    idWindowGenerarNotas++;
	    $.newWindow({id:"iframewindowGenerarNotas" + idWindowGenerarNotas, statusBar: true, posx:200,posy:50,width:700, height:450,title:"Nota de Expediente", type:"iframe", modal:true});
	    $.updateWindowContent("iframewindowGenerarNotas" + idWindowGenerarNotas,'<iframe src="<%= request.getContextPath() %>/capturarNotaExpediente.do?idNumeroExpedienteOp='+ numeroExpedienteId +'&idNota='+idNota+'&porFuncionario=true " width="700" height="450" />');
	}
                        
        function cargaNota(id,nombre){
            var row=$('#rowNota_'+id);
            $(row).remove();
            $('#tablaNotasExpediente').append('<tr id="rowNota_'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarNota_'+id+'" onclick="notaExpediente('+id+')">'+nombre+'</a></td></tr>');
            //cerrarVentanaNota();
	} 
         
        /*
	*Consultar las notas del expediente
	*POR EL MOMENTO SOLO SE CONSULTA UNA NOTA
	*/
	function consultarNotas(){
		var notas=$('#editor1').val();
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarNotasExpediente.do?idNumeroExpediente='+numeroExpedienteId+'&porFuncionario=true',
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
        
        $("#tabNotas").one("click", function() {
            consultarNotas();
	});
        
        function cargaIngresoHecho(nombre,id){
            $("#ingresarHechos").hide();
            $('#tableHecho').append('<tr><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="hecho_'+id+'" onclick="consultarHecho('+id+','+idExpedienteop+');">'+nombre+'</a></td></tr>');
            cerrarVentanaHecho();
	}
        
        function cerrarVentanaHecho(){
            var pantalla ="iframewindowHecho";
            pantalla += idWindowIngresarHechos;
            $.closeWindow(pantalla);
	}
        
        function ingresarHechos() {
            idWindowIngresarHechos++;
            $.newWindow({id:"iframewindowHecho" + idWindowIngresarHechos, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Hechos", type:"iframe"});
            $.updateWindowContent("iframewindowHecho" + idWindowIngresarHechos,'<iframe src="<%= request.getContextPath() %>/IngresarHechos.do?numeroExpediente='+numeroExpediente +'&idNumeroExpedienteOp='+idExpedienteop+'&idCalidad=DENUNCIANTE&idHecho=0&menuIntermedio=1'+'&pantallaSolicitada='+ 5 +'" width="1100" height="530" />');
            $("#" +"iframewindowHecho" + idWindowIngresarHechos + " .window-maximizeButton").click();
	}
        
        function consultarHecho(idHecho,idNumeroExpediente) {
            idWindowIngresarHechos++;
	    $.newWindow({id:"iframewindowHecho" + idWindowIngresarHechos, statusBar: true, posx:75,posy:30,width:1100,height:530,title:"Hechos", type:"iframe"});
	    $.updateWindowContent("iframewindowHecho" + idWindowIngresarHechos,'<iframe src="<%= request.getContextPath() %>/IngresarHechos.do?numeroExpediente='+numeroExpediente +'&idNumeroExpedienteOp='+idExpedienteop+'&idCalidad=DENUNCIANTE&idHecho='+idHecho +'&menuIntermedio=1'+'&pantallaSolicitada='+5+'" width="1100" height="530" />');
            
	    $("#" +"iframewindowHecho" + idWindowIngresarHechos + " .window-maximizeButton").click();
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
	    		if(parseInt($(xml).find('code').text())==0){
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
        
        function recargaGridAgenteMP(){
//            window.parent.regresaGrid();
	}

function canalizarControversiaExisteDelitoGrave(actividad,estatusId,titulo, formaID, numeroExpediente ) {
		if(existeProbableResponsableReincidente() == "true"){
						
			var texto = "Existe " +
						msjProbableResponsableProp +
						" reincidente. &iquest;Desea enviar a la unidad de controversias?"
			
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
		if( excede == "null"){
			alertDinamico("Existe un problema con la media aritm&eacute;tica de los delitos");
		}else{
			if(excede == "true"){
				customConfirm ("La media aritm&eacute;tica de los delitos excede lo permitido. &iquest;Desea enviar a la unidad de controversias?", "Aviso", 
						function(){
							canalizarControversiaMediaAritmetica(actividad,estatusId,titulo, formaID, numeroExpediente);
						},
						recargarActuaciones()
						);
			}else if(excede == "false"){
				canalizarControversiaMediaAritmetica(actividad,estatusId,titulo, formaID, numeroExpediente);
			}
		}			
	}

        function canalizarControversiaMediaAritmetica(actividad,estatusId,titulo, formaID, numeroExpediente){
		idWindowPantallaActuaciones++;
		if(actividad == '<%= Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS.getValorId()%>' || 
		   actividad=='<%=Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS_SIN_SUSPENDER_EXPEDIENTE.getValorId()%>'){
			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&actividadId='+actividad+'&idNumeroExpediente='+idNumeroExpedienteOp+'&esTransaccional='+true+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'" width="1140" height="400" />');
		}else{
			registrarActividadExpediente(actividad,estatusId,0);
			$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:confirmarCierreVentana});
		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/generarDocumentoSinCaso.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&actividadId='+actividad+'&idNumeroExpediente='+idNumeroExpedienteOp+'&esTransaccional='+true+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'" width="1140" height="400" />');
		    recargarActuaciones();						
		}
        }
        
                function ocultaMuestraTblsRelacionarDelitos()
		{
			var relacionDelitoPorPErsonaDelito = $(':radio[name=relacionaDelitos]:checked').val();
			if(parseInt(relacionDelitoPorPErsonaDelito)==0)
			{
				//Relacion por persona
				$("#tblRelacionaDelXPersona").show();
				$("#tblRelacionaDelXDelito").hide();
				$("#tblRelacionaDelXTodos").hide();
			}
			else if(parseInt(relacionDelitoPorPErsonaDelito)==1)
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
			if(parseInt(existeUnDelitoPrincipalEnGrid())==0){
				alertDinamico("Debe seleccionar un delito principal");	
				return;
			}
			
			//revisamos que si hay un delito grave se haya seleccionado
			if(existeDelitoGraveEnGrid())
			{
				if(parseInt(existeUnDelitoPrincipalGraveSeleccionado())==0)
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
				if(arrayIDs[i]!=idDelPrincipal)
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
	    		  if(parseInt($(xml).find('code').text())==0)
	    		  {
	    			  isDelitoSaved=true;
	    			//mostramos las leyendas de canalizacion debajo del grid
	    			  if(existeDelitoGraveEnGrid())
	    			  {
//	    				  if(pantallaSolicitada!=AGENTE_MP){
	      					  $("#leyendaUnDelitoGrave").show();
	      					  $("#leyendaNingunDelitoGrave").hide();  
//	    				  }else{
//	    					  $("#leyendaUnDelitoGrave,#leyendaNingunDelitoGrave").hide();
//	    				  }
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
				    		  if(parseInt($(xml).find('code').text())==0)
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
	    				
//	    			  if (typeof cargaGridDelitosAgraviados == 'function' ){
	    				  cargaGridDelitosAgraviados();
//	    			  }
	    			  
	    			  //fin de la consulta de actividades que depende de los delitos almacenados
	    			  alertDinamico("Se guardaron exitosamente los delitos seleccionados");
	    		  }	 
	    		  else
	    		  {
	    			  isDelitoSaved=false;
	    			  alertDinamico("Ocurri&oacute; un error al tratar de guardar los delitos agraviados");
	    		  }   			    		
			  }
	    	});
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
			if(idRdb!=null)
			{
				idRdb=idRdb.split('_')[1];
				if(idRdb!="")
				{
					var resp=jQuery("#gridDelitosAgraviados").jqGrid('getRowData',idRdb);
					if(resp.Gravedad=="No")
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
			
				if(row.GravedadFormateada=="Si") bandera2=0; 
			} 					
			
			if(bandera2==1 && bandera1==0)
			return 0;
			else return 1;
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

        function canalizarInvestigadoresNoExisteDelitoGrave(actividad,estatusId,titulo, formaID, numeroExpediente ){
		//verificamos si se tienen relaciones de delito-persona o delito-delito
		if(consultaTotalRelacionesDelitoPorTodos() <= 0)
		{
			var texto = "No se tienen registradas relaciones de los delitos con el " +
						msjProbableResponsableProp +
						" &iquest;Desea enviar a la unidad de fiscales investigadores?"
			
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
		//Mostrar ventana de Canalizaci&oacute;n a la Unidad de Fiscales Investigadores
		idWindowPantallaActuaciones++;
 		$.newWindow({id:"iframewindowGenerarDocumento"+idWindowPantallaActuaciones, statusBar: true, posx:200,posy:50,width:1140,height:400,title:"", type:"iframe",confirmarCierreVentana:confirmarCierreVentana});
		$.updateWindowContent("iframewindowGenerarDocumento"+idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath()%>/canalizarAUnidadFiscalesInv.do?formaId='+formaID+'&numeroUnicoExpediente='+numeroExpediente+'&actividadId='+actividad+'&numeroExpedienteId='+idNumeroExpedienteOp+'&esTransaccional='+true+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'" width="1140" height="400" />');
	}
        
        /*
	*Funcion para mostrar las actividades sugeridas dependiendo de los delitos en el expediente 
	*cuando se consulta un expediente
	*/
	function muestraActividadesSugeridasEnConsultaExpediente(){
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
            if(idDelPrincipal==null || idDelPrincipal=='null')
            {
		return;	
            }
            var nombreDelPrincipal=jQuery("#gridDelitosAgraviados").jqGrid('getRowData',idDelPrincipal).Tipo;
            var delitosNormales="";			
            //barremos el grid para generar la cadena de IDs de los delitos normales
            for (i=0;i<arrayIDs.length;i++)
            {
		if(arrayIDs[i]!=idDelPrincipal) {
                    var retd = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',arrayIDs[i]);
                    if(delitosNormales.length>0){
                        delitosNormales=delitosNormales+","+retd.Tipo;//arrayIDs[i];
                    }else{
                        delitosNormales=""+retd.Tipo;//arrayIDs[i];
                    }
		}
            } 
            
            //ahora mandamos los delitos al back-end
            var params="delitos="+nombreDelPrincipal+"-"+delitosNormales+"&numExp="+numeroExpediente;
		
            //mostramos las leyendas de canalizacion debajo del grid
  			  if(existeDelitoGraveEnGrid())
  			  {
                            $("#leyendaUnDelitoGrave,#leyendaNingunDelitoGrave").hide();
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
	    		  if(parseInt($(xml).find('code').text())==0)
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
                
                function revisaEsDelitoGraveUno(idRadio)
		{
			//recuperamos el valor de la columna gravedad del delito
			var ret = jQuery("#gridCatDelitos").jqGrid('getRowData',idRadio);
			var retDos = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',idRadio);
			var isGrave="No";
			var delitos=<%=(String)request.getSession().getAttribute("delitosXML")%>;
			var delitosXML=$(''+delitos);
			//mostramos los botones correspondientes dependiento del tipo de delito
			if(ret.Gravedad!=null)
			{
				isGrave=ret.Gravedad;
			}
			else
			{
				isGrave=retDos.Gravedad;
			}
			if(isGrave=="No")
			{
				//revisamos que no exista un delito grave NO seleccionado
				if(existeDelitoGraveEnGrid())
				{
					//se le indica al usuario que seleccione un delito grave como principal
					customAlert("Si no hay reincidencia por parte del Probable Part&iacute;cipe,\n se debe canalizar al Centro de Justicia Restaurativa.");
				}
			}
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
			if(ret.Gravedad!=null)
			{
				isGrave=ret.Gravedad;
			}
			else
			{
				isGrave=retDos.Gravedad;
			}
			if(isGrave=="No")
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
					if($(this).find('claveDelito').text()==idRadio)
					{
						if($(this).find('departamento').text()!="")
						{
							//seteamos el combo de la pesta&ntilde;a de Acciones dependiendo del departamento
							$('#cbxCanalizaAUI').val(parseInt($(this).find('departamento').find('departamentoId').text()));
							$('#cbxCanalizaAUI').multiselect('refresh');
							mostraDivGenerarOficioCanalizacion(2);
						}
						else
						{
							//seteamos el combo de la pesta&ntilde;a de Acciones dependiendo de la institucion
							$('#cbxCanalizaAIE').val(parseInt($(this).find('institucion').find('institucionId').text()));
							$('#cbxCanalizaAIE').multiselect('refresh');
							mostraDivGenerarOficioCanalizacion(3);
						}
					}
				});
			}
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
				if(isGrave.Gravedad=="Yes")
				{
					bandera=true;
				}
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
				if(isGrave.Gravedad=="Yes")
				{
					bandera=true;
				}
			} 
			return bandera;
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
			if(idRdb!=null)
			{
				idRdb=idRdb.split('_')[1];
				if(idRdb!="")
				{
					var resp=jQuery("#gridDelitosAgraviados").jqGrid('getRowData',idRdb);
					if(resp.Gravedad=="Yes")
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
                
                function validaGuardadoDefinitivo()
		{
			//revisamos que selecciono el tipo: Denuncia o Querella en generales
			//obtengo el ID del rdb del delito seleccionado
			var idRdbTipo="";
			var banderaTipo=false;
			idRdbTipo=$('input[name=generales]:checked').attr('id');
			if(idRdbTipo!=null)
			{
				if(idRdbTipo!="")
				{
					//reviso si el delito seleccionado es grave o no
					if(idRdbTipo=="rbtnDenuncia")//Denuncia
					{
						$("#btnAccDenuncia").show();
						$("#tdCbxAccionesTab").show();
						$("#btnAccQuerella").hide();
						//revisamos si ya guardo el delito
						if(isDelitoSaved)
						{
							//$("#btnAccDenuncia").attr("disabled", "");
						}
						else
						{
							alertDinamico("Debe de seleccionar guardar en la pesta&ntilde;a Delito");
							return;
						}
					}
					else//rbtnQuerella querella
					{
						$("#btnAccQuerella").show();
						$("#btnAccDenuncia").hide();
						$("#tdCbxAccionesTab").hide();
					}
					banderaTipo=true;
				}
			}
			if(!banderaTipo)
			{
				alertDinamico("Debe seleccionar el tipo en la pesta&ntilde;a Generales");
				return;
			}
		}
                
                function mostraDivGenerarOficioCanalizacion(idDiv)
		{
			$("#divCanalizaAUI,#divCanalizaAIE,#btnCanalizaAJR").hide();
			if(parseInt(idDiv)==1)
			{
				$("#btnCanalizaAJR").show();
				$("#btnGenerarAcciones").hide();
			}
			else if(parseInt(idDiv)==2)
			{
				$("#divCanalizaAUI").show();
				$("#btnGenerarAcciones").show();
			}
			else if(parseInt(idDiv)==3)
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
				if(parseInt(existeUnDelitoPrincipalGraveSeleccionado())==0)
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
			if(isGrave=="No")
			{
				mostraDivGenerarOficioCanalizacion(1);	
			}
			else
			{
				//barro el pseudo-XML de delitos
				delitosXML.find('catDelitoDTO').each(function()
				{
					if($(this).find('claveDelito').text()==idDelPrincipal)
					{
						if($(this).find('departamento').text()!="")
						{
							//seteamos el combo de la pesta&ntilde;a de Acciones dependiendo del departamento
							$('#cbxCanalizaAUI').val(parseInt($(this).find('departamento').find('departamentoId').text()));
							$('#cbxCanalizaAUI').multiselect('refresh');
							mostraDivGenerarOficioCanalizacion(2);
						}
						else
						{
							//seteamos el combo de la pesta&ntilde;a de Acciones dependiendo de la institucion
							$('#cbxCanalizaAIE').val(parseInt($(this).find('institucion').find('institucionId').text()));
							$('#cbxCanalizaAIE').multiselect('refresh');
							mostraDivGenerarOficioCanalizacion(3);
						}
					}
				});
			}
			return true;
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
           
     
</script>
<body>
    
<div id="tabsPrincipal">
				<ul>
					<li><a href="#tabsconsultaprincipal-1">Resumen</a></li>
					<li><a href="#tabsconsultaprincipal-2">Involucrado</a></li>
                                        <li class="tabTabsHechos"><a href="#tabs-3" id="tapHechos">Hechos</a></li>
                                        <li class="tabTabsRelacionesDelitosPersonas"><a href="#tabs-17" id="tapDelitoYRelaciones" onclick="cargarGridsInvolucradosRelDelitoPersonaPG()">Delito y Relaciones Delito-Persona</a></li>
<!--					<li class="tabPsicologica"><a href="#tabsconsultaprincipal-4">Atenci&oacute;n Psicol&oacute;gica</a></li>
					<li class="tabSocial"><a href="#tabsconsultaprincipal-6">Trabajo Social</a></li>
					<li class="tabJuridica"><a href="#tabsconsultaprincipal-7">Atenci&oacute;n Jur&iacute;dica</a></li>-->
					<li id="tabNotas"><a href="#tabsconsultaprincipal-5">Notas</a></li>
					<li class="tabTabsDocs"><a href="#tabsconsultaprincipal-8" onclick="documentos()">Documentos</a></li>
                                        <li class="tabTabsAcciones"><a href="#tabs-7" id="tapActuaciones">Actuaciones</a></li>
			
					
				</ul>
    
    <!--COMIENZAN TABS INFERIORES DE ACTUACIONES-->
		<div id="tabs-7" class="tabTabsAcciones">		
			<div id="tabschild7" class="tabs-bottom">
				<ul>
					<li class="tabTabsAccionesHijo"><a href="#tabschild7-1">Actuaciones</a></li>
					<li><a href="#tabschild7-2" id="tapRelacionarInfoDeExp">Relacionar Informaci&oacute;n del expediente</a></li>					
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
                                                            <span> 
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
											<button value="Enviar de Investigacion" class="ui-button ui-corner-all ui-widget" onclick="lanzaCarpetaInvestigacionDefensoria()">Enviar carpeta de investigaci&oacute;n</button>
										</td>
									</tr> -->
								</table>
						</td>
						<td>
							<div id="idRadiosBUt" style="display: none;">
							<table>
								<tr>
								<td>
								Mediaci&oacute;n
								</td>
								<td>
								<input type="radio" name="rbConci" id="raio1" checked="checked" />
								</td>
								</tr>
								<tr>
								<td>
								Conciliaci&oacute;n
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
    
    
                <!--COMIENZAN TAB HECHOS-->
		<div id="tabs-3" class="tabTabsHechos">
			<div id="tabschild3" class="tabs-bottom">
				<ul>
					<!--<li><a href="#tabschild3-1">Hechos</a></li>-->				
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
				
		<!--Comienza div para ver los documentos propios del perito-->
				<div id="tabsconsultaprincipal-1">
					<table width="1042px" border="0">
						<tr>
							<td width="50%" align="right">
								<span id="tdFuncionario">Seleccione el funcionario que atender&aacute; la solicitud</span>
							<td>
							<td width="30%" align="right">
								<select id="cbxFuncionariosAyuda" style="width:250px">
									<option value="0">-Seleccione-</option>
								</select>
							<td>
							<td width="20%" align="right">
								 <input type="button" class="btn_mediano" value="Asignar Ayuda" onclick="asignarAyuda();" id="btnSolicitarAyuda"/>
							<td>
						</tr>
					</table>
					<div style="width: 1042px; height: 490px;" class="back_hechos" ><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<table width="50%">
							<tr>
								<td width="10%"></td>
								<td><b>Tipo de solicitud : </b>
								</td>
								<td id="spanTipoSolicitud">
								</td>
							</tr>
							<tr>
								<td width="10%"></td>
								<td><b>Nombre de v&iacute;ctima : </b>
								</td>
								<td id="spanNombreVictima">
								</td>
							</tr>
							<tr>
								<td width="10%"></td>
								<td><b>Delito : </b>
								</td>
								<td id="spanDelito">
								</td>
							</tr>
							<tr>
								<td width="10%"></td>
								<td><b>AMP que solicit&oacute; la ayuda : </b>
								</td>
								<td id="spanAMPSolicito">
								</td>
							</tr>
							<tr>
								<td width="10%"></td>
								<td><b>&Aacute;rea que solicit&oacute; la ayuda : </b>
								</td>
								<td id="spanAreaSolicito">
								</td>
							</tr>
							<tr>
								<td width="10%"></td>
								<td><b>Fecha de creaci&oacute;n del Expediente : </b>
								</td>
								<td id="spanFechaCreacion">
								</td>
							</tr>
							<tr>
								<td width="10%"></td>
								<td><b>Estatus de Expediente : </b>
								</td>
								<td id="spanEstatusExpediente">
								</td>
							</tr>
							<tr>
								<td width="10%"></td>
								<td><b>Estatus de Actuaciones : </b>
								</td>
								<td id="spanEstatusActuaciones">
								</td>
							</tr>														
							<tr>
								<td width="10%"></td>
								<td><b>Responsable del Tr&aacute;mite : </b>
								</td>
								<td id="spanResponsableTramite">
								</td>
							</tr>
						</table>
					</div>
				</div>
						
				<div id="tabsconsultaprincipal-2" class="tabs-bottom">
						<ul>
							<li><a href="#tabschild-2">V&iacute;ctima</a></li>
							<li><a href="#tabschild-3"><bean:message key="probableResponsableTitulo"/></a></li>
						</ul>
						<div id="tabschild-2">
						<div style="width: 1042px; height: 490px;" class="back_hechos">
							<table width="25%" cellpadding="0" cellspacing="0" id="tblVictima" style="padding: .5cm;">
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevaVictima"></a></td>
								</tr>
								
							</table>
						</div>
						</div>
						<div id="tabschild-3">
						<div style="width: 1042px; height: 490px;" class="back_hechos">
							<table width="25%" cellpadding="0" cellspacing="0" id="tblProbableResponsable" style="padding: .5cm;">
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="nuevoProbResponsable"></a></td>
								</tr>
							</table>
						</div>
						</div>
							
				</div>
				<div id="tabsconsultaprincipal-5">
					<table width="25%" cellpadding="0" cellspacing="0" id="tablaNotasExpediente">
							<tr>
								<td>&nbsp;&nbsp;&nbsp;<input type="button" class="ui-button ui-corner-all ui-widget" value="Generar Nota"  id="botonGuardarNotas" onclick="notaExpediente(0);" /></td>
							</tr>
					</table>
				</div>
				<div id="tabsconsultaprincipal-8" class="tabTabsDocs">
					</br>
					<table id="gridDetalleFrmPrincipal"></table>
					<div id="pager1"></div>
					<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
							<input type="hidden" name="documentoId" />
						</form>
						<form name="frmDoc2" action="<%= request.getContextPath() %>/GenerarDocumentoDirecto.do" method="post">
							<input type="hidden" name="formaId" />
							<input type="hidden" name="numeroUnicoExpediente" />
						</form>
				</div>
				<!--Termina div para ver los documentos propios del perito-->
				</div>	
				
				<div id="dialogAsistencia" style="display: none;">
				 <table width="100%" border="0">
  <tr>
    <td width="3%">Si</td>
    <td width="48%">
      <input type="radio" name="radioAsistio" id="radioAsistioSi" value="1" />
    </td>
    <td width="4%">No    </td>
				    <td width="45%"><input type="radio" name="radioAsistio" id="radioAsistioNo" value="2" /></td>
  </tr>
</table>
				</div>	

</body>
</html>