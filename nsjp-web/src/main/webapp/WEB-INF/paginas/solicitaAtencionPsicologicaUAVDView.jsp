<%@ page import="org.omg.CORBA.Request"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.CategoriasActividad"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Solicitud</title>

<!--	Hoja de estilo para los gadgets-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />

<!--script de jquery UI-->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/prettify.js"></script>

<!--Hojas de estilos para los componentes UI de Jquery-->
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />

<!--Hoja de estilos para el grid-->
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />

<!--scripts de java script-->
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>

<!--Hoka de estilo para el texto dentro de los acordeones-->
<!--Hoja de estilos ultrasist-->
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />
<!--Hoja de estilo para los popups-->
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />

<!--Scripts necesarios para el funcionamiento de la JSP-->

<!-- <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script> -->
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
<!--Scrip para el idioma del calendario-->
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>

<!--Scripts necesarios para la ejecucion del editor-->
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>

<!--script de jquery UI-->
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/prettify.js"></script>
	
<!--scripts del gird-->
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jqgrid/jquery.jqGrid.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
		
<script type="text/javascript">
	//variables globales
 	var idExpedienteop="";
	var numeroCaso="";
 	var idRelacionDelito="";
 	var idTipoSolicitud=0;
 	var numeroUnicoExpediente="";
 	var idWindowPantallaActuaciones = 1;
 	
	//Comienza funcion on ready del documento
	$(document).ready(function() {
		//seteamos las variables globales
		idExpedienteop='<%= request.getParameter("idExpedienteop")%>';	
		numeroCaso='<%= request.getParameter("numeroCaso")%>';
		numeroUnicoExpediente ='<%= request.getParameter("numeroUnicoExpediente")%>';
		idNumeroExpedienteOp ='<%= request.getParameter("idNumeroExpedienteOp")%>';
		sistemaTradicional='<%= request.getParameter("sistemaTradicional")%>';		
		ocultaCaso(sistemaTradicional);
		//llenamos el catalogo de los tipos de ayuda
		//cargaCatalogoTipoAyuda();
		cargaActuaciones();
		$("#btnEnviarSolictud").click(abrirPantallaEnvioSolicitud);
		
		//cargamos todas las relaciones delito-persona del expediente
	 	var probableResponsableProp = '<bean:message key="probableResponsableTitulo"/>';
		//cargamos el grid con los delitos del PR  
    	jQuery("#gridCatDelitosRDPTodosUAVD").jqGrid({ 
    		url:'<%= request.getContextPath()%>/ConsultarRelacionDelitosPorTodos.do?idExpediente='+idExpedienteop,
			datatype: "xml",
			colNames:[probableResponsableProp,'Delito','Forma de Participaci&oacute;n','V&iacute;ctima'], 
			colModel:[ 	{name:'Probable',index:'probable', width:250},
			           	{name:'Delito',index:'delito', width:250}, 
						{name:'FormaParticipacion',index:'formaParticipacion',width:250},
						{name:'Victima',index:'victima',width:250}
					],
			pager: jQuery('#pagerGridCatDelitosRDPTodosUAVD'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			autowidth: true,
			caption:"LISTA DE RELACIONES",
			sortname: 'Clave',
			viewrecords: true,
			id: 'divgridIzq',
			ondblClickRow: function(rowid) {
				var idsRelacionDelito = rowid.split("_");
				rowid = idsRelacionDelito[0];//id DelitoPersona
			    $.newWindow({id:"consultaDelitoPersona", statusBar: true, posx:20,posy:20,width:500,height:350,title:"Consulta delito persona", type:"iframe"});
         		$.updateWindowContent("consultaDelitoPersona",'<iframe src="<%= request.getContextPath() %>/abrePantallaConsultaDelitoPersona.do?idDelitoPersona='+rowid+'&idNumeroExpediente='+idNumeroExpedienteOp+'" width="500" height="350" />');
			},

			onSelectRow: function(id){
				idRelacionDelito=id;
			},
			sortorder: "desc"
		});//.navGrid('#pagerGridCatDelitosRDPTodosUAVD',{edit:false,add:false,del:false});
    	//$("#gridCatDelitosRDPTodosUAVD").trigger("reloadGrid");
    	
	});
	//Termina funcion on ready del documento
	
	/*
	*Funcion que oculta el renglon de caso, usado solo para sistema
	*tradicional , usuario: agenteSistTrad, ya que los expedientes son 
	*generados sin n&uacute;mero de caso
	*/
	function ocultaCaso(sistemaTradicional){

		if(sistemaTradicional == "1"){
			$("#numCasoTd").hide();
			$("#txtNoCaso").hide();

		}else{
			//seteamos el valor del numero de caso
			$("#txtNoCaso").val(numeroCaso);
		}
	}
	
	/*
	*Procedimiento para carga los tipos de ayuda
	*@deprecated ver cargar Actuaciones
	*/
	function cargaCatalogoTipoAyuda() 
	{
		$('#cbxTipoDeAyuda').empty();
		$('#cbxTipoDeAyuda').append('<option value="-1" selected="selected">-Seleccione-</option>');
		
		//lanzamos la consulta del tipo de solicitudes
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultaTiposSolsXArea.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('ValorDTO').each(function(){
					var idTipoSolicitud = $(this).find('idCampo').text();
					
                                        if(idTipoSolicitud==<%= TiposSolicitudes.ATENCION_PSICOLOGICA.getValorId()%>){						
						$('#cbxTipoDeAyuda').append('<option value="' + idTipoSolicitud + '">' + $(this).find('valor').text() + '</option>');
					}
				});
			}
		});
	}
	
	function cargaActuaciones() {
		var categoriaActividadId = <%= CategoriasActividad.UAVD.getValorId()%>;
		var muestraCombo = false;
		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/cargarActuacionesPorFiltro.do?categoriaActividadId='+categoriaActividadId+'&muestraCombo='+muestraCombo,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catActuaciones').each(function(){
					$('#cbxTipoDeAyuda').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});         
			}
		});
	}
	
	/*
	*Funcion para abrir la ventan del envio de la solicitud
	*/
	function abrirPantallaEnvioSolicitud()
	{
		if(idRelacionDelito=="" || idRelacionDelito.length==0)
		{
			customAlert("Seleccione una relaci&oacute;n en la tabla");
		}
		else if($("#cbxTipoDeAyuda option:selected").val()==-1)
		{
			customAlert("Seleccione un tipo de ayuda");
		}
		else
		{
			//pasaron las validaciones y procederemos a abrir el editor de formas
			idTipoSolicitud=$("#cbxTipoDeAyuda option:selected").val();
			elaboraNotificacionAuditoria();
		}
	}
	
    function elaboraNotificacionAuditoria()
    {     
	  var numeroEX=idExpedienteop;
      var actividad=0;
      var formaID=4;
      var titulo="op";
      var usaeditor="";
      var estatusId="";
      var idConf=$("#cbxTipoDeAyuda option:selected").val();
      var nombreActividad ="";
      //consultamos la forma para la notificacion de auditoria
      $.ajax({
            type: 'POST',
            url: '<%= request.getContextPath()%>/obtenerConfActividadDocumento.do?idConf='+idConf+'',
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
                  idTipoSolicitud = $(xml).find('confActividadDocumentoDTO').find('grupoActividad').text();
            }
      });
      $.newWindow({id:"iframewindowGenerarDocumento"+ idWindowPantallaActuaciones, statusBar: true, posx:20,posy:20,width:1140,height:550,title:nombreActividad, type:"iframe"});
  	  $.updateWindowContent("iframewindowGenerarDocumento"+ idWindowPantallaActuaciones,'<iframe src="<%= request.getContextPath() %>/elaborarNotificacionAyudaPsicologicaUAVD.do?formaId='+formaID+'&numeroExpedienteId='+idExpedienteop+'&numeroUnicoExpediente='+numeroUnicoExpediente+'&idTipoSolicitud='+idTipoSolicitud+'&idRelacionDelito='+idRelacionDelito+'&idWindowPantallaActuaciones='+idWindowPantallaActuaciones+'"    width="1140" height="550" />');
    }
</script>
</head>
<body>
<table width="80%">
	<tr>
		<td id="numCasoTd">&nbsp;&nbsp;&nbsp;&nbsp;N&uacute;mero de caso: </td>
		<td align="left"><input type="text" id="txtNoCaso" disabled="disabled" style="width: 200px;"/></td>
		<td><input type="button" id="btnEnviarSolictud" value="Enviar Solicitud" class="btn_mediano"></td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;Tipo de ayuda : </td>
		<td align="left">
			<select id="cbxTipoDeAyuda">
				<option value="-1" selected="selected">-Seleccione-</option>
			</select>
		</td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td>
			<table id="gridCatDelitosRDPTodosUAVD" width="400px"></table>
			<div id="pagerGridCatDelitosRDPTodosUAVD"></div>
		</td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table>
<table width="80%">
	<tr>
		<td>
			
		</td>
	</tr>
</table>
</body>
</html>