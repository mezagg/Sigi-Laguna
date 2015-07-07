<%@ page import="mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Prueba Visor de elementos</title>

	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.tablednd.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>

	<script type="text/javascript">
            $.jgrid.no_legacy_api = true;
	</script>
	
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

    	var contDelitosGraves=0;
		var idExpedienteop="";
		var rolActivo = '<%=rolActivo%>';
    	
		$(document).ready(function(){
			//cargaGridSolicitidesPericialesEnviadasPorExpediente();
			//cargaGridSolicitidesPericialesRespondidasPorExpediente();
			//cargaGridSolicitidesPericialesRegresadasPorExpediente();
		});
		

		/*
		*Funcion que consulta y carga el grid con las solicitudes periciales enviadas
		*/
		function cargaGridSolicitidesPericialesEnviadasPorExpediente(){

			var abierta= <%=EstatusSolicitud.EN_PROCESO.getValorId()%>;
			jQuery("#gridSolicitudesPeri1").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarSolicitudesPericialesPorExpediente.do?numeroExpedienteId='+idNumeroExpedienteOp+'&estatus='+abierta+'', 
				datatype: "xml", 
				colNames:['No. Caso','No. Expediente', 'Folio','Estatus','Fecha Creación','Fecha Limite','Institución','Destinatario'], 
				colModel:[ 	{name:'caso',index:'caso', width:200},
				           	{name:'expediente',index:'expediente', width:180}, 
							{name:'folio',index:'folio', width:100}, 
							{name:'estatus',index:'estatus', width:100, hidden:true}, 
							{name:'fechaCreacion',index:'fechaCreacion', width:100},
							{name:'fechaLimite',index:'fechaLimite', width:100},
							{name:'institucion',index:'institucion', width:100},
							{name:'remitente',index:'remitente', width:200, hidden:true}
						],
				pager: jQuery('#pagerGridSolicitudesPeri1'),
				rowNum:5,
				rowList:[5,10,15],
				autowidth: true,
				caption:"SOLICITUDES ENVIADAS",
				sortname: 'caso',
				viewrecords: true,
				sortorder: "desc"
			});
		}
		

		/*
		*Funcion que consulta y carga el grid con las solicitudes periciales que ya han sido respondidas
		*y estan concluidas
		*/
		function cargaGridSolicitidesPericialesRespondidasPorExpediente(){

			var concluida= <%=EstatusSolicitud.CERRADA.getValorId()%>;
			jQuery("#gridSolicitudesPeri2").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarSolicitudesPericialesPorExpediente.do?numeroExpedienteId='+idNumeroExpedienteOp+'&estatus='+concluida+'', 
				datatype: "xml", 
				colNames:['No. Caso','No. Expediente', 'Folio','Estatus','Fecha Creación','Fecha Limite','Institución','Remitente'], 
				colModel:[ 	{name:'caso',index:'caso', width:200},
				           	{name:'expediente',index:'expediente', width:180}, 
							{name:'folio',index:'folio', width:100}, 
							{name:'estatus',index:'estatus', width:100, hidden:true}, 
							{name:'fechaCreacion',index:'fechaCreacion', width:100},
							{name:'fechaLimite',index:'fechaLimite', width:100},
							{name:'institucion',index:'institucion', width:100},
							{name:'remitente',index:'remitente', width:200,hidden:true}
						],
				pager: jQuery('#pagerGridSolicitudesPeri2'),
				rowNum:5,
				rowList:[5,10,15],
				autowidth: true,
				caption:"RESPUESTA A SOLICITUDES",
				sortname: 'caso',
				viewrecords: true,
				ondblClickRow: function(rowid){
					if (!(rolActivo == '<%=Roles.PROCURADOR.getValorId()%>' || rolActivo == '<%=Roles.SUBPROCURADOR.getValorId()%>' ||
							rolActivo == '<%=Roles.DIRECTOR_GENERAL.getValorId()%>' || rolActivo == '<%=Roles.DIRECTOR_UI.getValorId()%>' || 
							rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>')){
						abrirDetalleSolicitudPericial(rowid,"NA",1,"true");
					}
				},
				sortorder: "desc"
			});
		}

		
		function cargaGridSolicitidesPericialesRegresadasPorExpediente(){

			var concluida= <%=EstatusSolicitud.RESPONDIDA.getValorId()%>;
			jQuery("#gridSolicitudesPeri3").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarSolicitudesPericialesPorExpediente.do?numeroExpedienteId='+idNumeroExpedienteOp+'&estatus='+concluida+'', 
				datatype: "xml", 
				colNames:['No. Caso','No. Expediente', 'Folio','Estatus','Fecha Creación','Fecha Limite','Institución','Remitente'], 
				colModel:[ 	{name:'caso',index:'caso', width:150},
				           	{name:'expediente',index:'expediente', width:130}, 
							{name:'folio',index:'folio', width:125}, 
							{name:'estatus',index:'estatus', width:100,hidden:true}, 
							{name:'fechaCreacion',index:'fechaCreacion', width:125},
							{name:'fechaLimite',index:'fechaLimite', width:125},
							{name:'institucion',index:'institucion', width:125},
							{name:'remitente',index:'remitente', width:200,hidden:true}
						],
				pager: jQuery('#pagerGridSolicitudesPeri3'),
				rowNum:5,
				rowList:[5,10,15,],
				autowidth: true,
				caption:"SOLICITUDES EN PROCESO",
				sortname: 'caso',
				viewrecords: true,
				ondblClickRow: function(rowid){
					if (!(rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>')){
						solicitudesPeritoFaltaDocumentos(rowid);
					}
				},
				sortorder: "desc"
			});
		}

		function solicitudesPeritoFaltaDocumentos(rowid){
			var area = 1;
			 $.newWindow({id:"iframewindowSolicitud", statusBar: true, posx:20,posy:20,width:1140,height:550,title:"Solicitar servicio pericial", type:"iframe"});
	         $.updateWindowContent("iframewindowSolicitud",'<iframe src="<%= request.getContextPath() %>/solicitarServicioPericial.do?numeroExpediente='+numeroExpediente+'&solcitudPericialId='+rowid+'&numeroExpedienteId='+idNumeroExpedienteOp+'&area='+area+'"    width="1140" height="550" />');
	         $("#" +"iframewindowSolicitud" + " .window-maximizeButton").click();
		}
		
		/*
		*Funcion que abre el PDF para ver los documentos asociados al numero de causa
		*/
		function abrirDocsDigAsociadosASol(documentoAsocId){
			if(documentoAsocId != null && documentoAsocId != ""){
				$("#visorDocsFrame").attr("src","<%= request.getContextPath()%>/ConsultarContenidoArchivoDigital.do?documentoId="+documentoAsocId+"&inFrame=true");
			}
			else{
				alert("El documento no puede ser mostrado");
			}
		}
		
		function abrirDetalleSolicitudPericial(solicitudPericialId, estatus, area, consulta){
			$.newWindow({id:"iframewindowConsultarDetalleSolicitudPericial", statusBar: true, posx:50,posy:50,width:1150,height:400,title:"Respuesta de solicitud pericial", type:"iframe"});
			$.updateWindowContent("iframewindowConsultarDetalleSolicitudPericial",'<iframe src="<%= request.getContextPath() %>/designarPerito.do?solicitudPericialId=' +solicitudPericialId +'&estatus='+estatus+'&area='+area+'&consulta='+consulta+'" width="1350" height="400" />');
		}

				
	</script>
</head>
<body>
	<table border="0"  width="1000px">
		<tr>
			<td height="15" colspan="4" align="center" >
				<table id="gridSolicitudesPeri1" width="670px"></table>
				<div id="pagerGridSolicitudesPeri1"></div>
			</td>
		</tr>
		<tr>
			<td height="15" colspan="4" align="center" >
				<table id="gridSolicitudesPeri2" width="670px"></table>
				<div id="pagerGridSolicitudesPeri2"></div>
			</td>
		</tr>
		<tr>
			<td height="15" colspan="4" align="center" >
				<table id="gridSolicitudesPeri3" width="670px"></table>
				<div id="pagerGridSolicitudesPeri3"></div>
			</td>
		</tr>
	</table>
</body>
</html>