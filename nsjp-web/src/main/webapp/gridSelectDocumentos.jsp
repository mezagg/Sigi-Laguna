<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento"%>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/themes/1.8.10/${theme.name}/jquery-ui.css" /-->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/themes/1.8.10/${theme.name}/jquery-ui.css" />
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
        <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
        <script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
        
        <script type="text/javascript">
       
        var contextoPagina = "${pageContext.request.contextPath}"; 
        var CONTEXT_ROOT = '<%= request.getContextPath()%>';
         var idExpediente='<%= request.getParameter("idExpediente")%>';
         var idNumeroExpediente='<%= request.getParameter("idNumeroExpediente")%>';
         var numeroGeneralCaso='<%= request.getParameter("numeroGeneralCaso")%>';
         
         //Se agregan los parametros de tipo documento y tipo de actividad para adjuntar dentro del expediente.
         var idTipoActividad ='<%= request.getParameter("idTipoActividad") != null ? request.getParameter("idTipoActividad") : 0 %>';
         var idTipoDocumento ='<%= request.getParameter("idTipoDocto") != null ? request.getParameter("idTipoDocto") : 0 %>';
         
         //Permite adjuntar el documento al expediente  y ademas a un eslabon
         var idEslabon='<%= request.getParameter("idEslabon")%>';
         
         //Permite restringir los tipos de archivos a subir en el servidor
         //Los tipo de archivos(.ext) de deben separar por comas, en minusculas y sin espacios por ejemplo: 
         //.doc,.jpg,.pdf
         var extensionesPermitidas='<%= request.getParameter("extensionesPermitidas")%>';

         var success='<%= request.getParameter("success")%>';
         var regresarId='<%= request.getParameter("regresarId")%>';
         var regresarIdAtt='<%= request.getAttribute("regresarIdAtt")%>';
         var idDocumentoAtt='<%= request.getAttribute("idDocumento")%>';
         var extensionesPermitidasAudio = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getExtensionesPermitidasAlAdjuntarAudio() %>';
 		 var extensionesPermitidasImagen = '<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getExtensionesPermitidasAlAdjuntarImagen() %>';
 		 var tipoDocumentoSolicitud = '<%=TipoDocumento.SOLICITUD_DE_AUDIENCIA.getValorId()%>';
         
         $(document).ready(function() {
        	 $('#btnGuardar').click(guardarDocumentos);
        	 $('#btnAdjuntarDocumento').click(ventanaAdjuntaDocumentoSolicitudAudiencia);
        	 jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
					url:'<%=request.getContextPath()%>/consultarDocumentos.do?idExpedienteop='+idNumeroExpediente, 
					datatype: "xml",
					colNames:['&Aacute;rea del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento', 'Documento', 'Es Parcial'],
					colModel:[ 	{name:'area',index:'da', width:200},
								{name:'FechaActividad',index:'fechaActividad', width:170},							
								{name:'NombreActividad',index:'nombreActividad', width:400},
					           	{name:'Tipo',index:'tipo', width:155}, 
								{name:'Nombre',index:'nombre', width:255},
					           	{name:'Fecha',index:'fecha', width:170},
					           	{name:'Documento',index:'documento', width:170},
					           	{name:'EsParcial',index:'esParcial', hidden:true}
								],
					pager: jQuery('#pager1Documentos'),
					rowNum:10,
					rowList:[10,20,30],
					autowidth: false,
					width:1100,
					viewrecords: true,
					id: 'divgrid',
					multiselect: true,
					sortorder: "desc"
				}).navGrid('#pager1Documentos',{edit:false,add:false,del:false});
         	
         	
         });
         
         function documentos(){
			 jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
						{url:'<%= request.getContextPath()%>/consultarDocumentos.do?idExpedienteop='+idNumeroExpediente, 
						datatype: "xml"  
						});
					 $("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
		}
         
         function ventanaAdjuntaDocumentoSolicitudAudiencia(){
        		var extensionesPermitidas = ".pdf," + extensionesPermitidasAudio + "," + extensionesPermitidasImagen;
        			
   				if(typeof(idNumeroExpediente) != "undefined" && typeof(idNumeroExpediente) != "null" && idNumeroExpediente != ""){
   					$.newWindow({id:"iframewindowAdjuntarDocumento", statusBar: true, posx:50,posy:50,width:450,height:200,title:"Adjuntar documento", type:"iframe"});
   					$.updateWindowContent("iframewindowAdjuntarDocumento",'<iframe src="' + CONTEXT_ROOT + '/adjuntarDocumentoAExpedienteGenerico.jsp?extensionesPermitidas=' + extensionesPermitidas + '&idNumeroExpediente='+idNumeroExpediente+'&idTipoDocto='+tipoDocumentoSolicitud+'&regresarId=1'+'" width="450" height="200" />');
   				}else{
   					customAlert("Imposible adjuntar!");
   				}
   		}
         
         function guardarDocumentos(){
        	 var grid = $("#gridDetalleFrmPrincipal");
             var rowKey = grid.getGridParam("selrow");

             if (!rowKey)
            	 customAlert("No selecciono ningun documento");
             else {
                 var selectedIDs = grid.getGridParam("selarrrow");
                 var result = "";
                 for (var i = 0; i < selectedIDs.length; i++) {
                	 if(i != 0){
                		 result += ",";
                	 }
                     result += selectedIDs[i];
                 }
                 window.parent.setIdDocumentoAdjunto(result);
                 window.parent.cerrarVentanaDocumentosAdjuntos();
             }
         }
         
         

        
         		
        </script>
    </head>
<body>

<table width="1100" border="0" summary="Tabla para registrar un amparo">
   <tr>
     <td align="left">&nbsp;</td>
   </tr>
   <tr>
   		<td>
   			<input type="button" id="btnAdjuntarDocumento" value="Adjuntar documentos"  class="ui-button ui-corner-all ui-widget"/>
   		</td>
   </tr>
   <tr>
     <td align="center">
     	<table id="gridDetalleFrmPrincipal"></table>
     	<div id="pager1Documentos"></div>
     </td>
   </tr>
   <tr>
     <td align="right">       
     	<br/>
     	<input type="button" name="btnGuardar" id="btnGuardar" value="Guardar" tabindex="4" class="ui-button ui-corner-all ui-widget">
     </td>
   </tr>
</table>
</body>
</html>