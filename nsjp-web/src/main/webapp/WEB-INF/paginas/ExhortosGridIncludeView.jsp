	<%@page import="mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion"%>
	 <script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	 <script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>
	 <!--Hoja de estilos de Layout-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<!--Hoja de estilos ultrasist-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />	
	<!--Hojas de estilos para los componentes UI de Jquery-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />	

	<script type="text/javascript">	
	
	//Id del evento que viene desde el gird de la bandeja de notificaciones	
	idEvento= '<%=request.getParameter("idEvento")!=null ? request.getParameter("idEvento") : request.getParameter("idAudiencia")%>';
	var numeroUnicoExpediente = '<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):"Sin numero"%>';
	var accionConsultarPdf="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do";

	//Variables de contexto
	var CONTEXT_ROOT = '<%= request.getContextPath()%>';
	var contextoPagina = "${pageContext.request.contextPath}";
	var idWindowVisorExhortoPJ=1;
	var documentoId = 0;

	
	$(document).ready(function() {
		
		jQuery(document).ajaxStop(jQuery.unblockUI);
		
	      var params = '';
	      params += '&numExpedientePJ=' + numeroExpediente;
	      
	      formaId=4;
	
		/*
		*Funcion que carga el grid, por default con las audiencias del dia
		*/
		jQuery("#gridExhortosPJJU").jqGrid({ 
			url:'<%= request.getContextPath()%>/consultaExhortosPJ.do?'+params,
			datatype: "xml", 
			colNames:[
						'FolioAREF',
						'Documento',
						'Expediente',
						'Funcionario',
						'Fecha Vencida',
						'Fecha Diligencia',
						'Diligencia',
						'FechaEnvio',
						'Estado',
						'esParcial'
					], 
			colModel:[ 	{name:'folio',index:'folio', width:180,align:'center'},
			           	{name:'documento',index:'documento', width:180,align:'center', hidden: true}, 
			           	{name:'numeroExpediente',index:'numeroExpediente', width:180,align:'center', hidden: true}, 
			           	{name:'funcionario',index:'funcionario', width:180,align:'center'}, 
						{name:'fechaVencida',index:'fechaVencida', width:80,align:'center'}, 
						{name:'fechaDiligencia',index:'fechaDiligencia', width:100,align:'center'},
						{name:'diligencia',index:'diligencia', width:150,align:'center'},
						{name:'fechaEnvio',index:'fechaEnvio', width:100,align:'center'},
						{name:'estado',index:'estado', width:100,align:'center'},
						{name:'esparcial',index:'esparcial', width:100,align:'center', hidden: true}
					],
			pager: jQuery('#pager1'),
			rowNum:10,
			caption:"Exhortos",
			rowList:[10,20,30],
			autowidth: false,
			sortname: 'folio',
			
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
				var retd = jQuery("#gridExhortosPJJU").jqGrid('getRowData',rowid);
				documento= retd.documento;
				numeroExpediente = retd.numeroExpediente;
				
				if(retd.esparcial == 'true'){
					consultaExhorto(rowid, documento, numeroExpediente);
				}else{
						consultaPDF(documento);
						
					}
			}
		}).navGrid('#pager1',{edit:false,add:false,del:false});	

		$("#gview_gridExhortosPJJU .ui-jqgrid-bdiv").css('height', '400px');	
		
		$("#btnAgenerarExhorto").click(generarExhorto);


	});
	//Fin OnReady
	
		function recargarVisor(idEvento){
			jQuery("#gridExhortosPJJU").jqGrid('setGridParam', {
				url:'<%= request.getContextPath()%>/consultaExhortosPJ.do?numExpedientePJ='+idEvento,datatype: "xml" });
			$("#gridExhortosPJJU").trigger("reloadGrid");
		}
	
		function consultaPDF(id){
			document.frmDocPdf.action=accionConsultarPdf+"?documentoId="+id;
			document.frmDocPdf.submit();

		}
	
	
		/*
	*Funcion que abre la ventana Mandamiento Judicial
	*/
	function generarExhorto(){

		console.info("**ABRIR VENTANA GENERAR EXHORTO**");
		var parametros = '?formaId='+4+'&documentoId=&numeroUnicoExpediente='+numeroExpediente + '&idAudiencia='+ idEvento;
		var url ='<iframe src='+contextoPagina+'/generarExhorto.do'+parametros+' width="920" height="400"/>';
		
		$.newWindow({id:"iframewindowVisorExhorto"+idWindowVisorExhortoPJ, statusBar: true, posx:200,posy:50,width:1140,height:450,title:"Visor de Exhorto", type:"iframe",onWindowClose: function(id){
			idWindowVisorExhortoPJ--;
		}});
		$.updateWindowContent("iframewindowVisorExhorto"+idWindowVisorExhortoPJ,url);
		$("#" +"iframewindowVisorExhorto"+idWindowVisorExhortoPJ+ " .window-maximizeButton").click();
	}
	
	
	
	function consultaExhorto(rowID, documento,numeroExpediente){
		console.info("**ABRIR Consultar GENERAR EXHORTO**");
		
		documentoId = documento;
		var parametros = '?idExhorto='+rowID+'&documentoId='+documento+'&numeroUnicoExpediente='+numeroExpediente + '&idAudiencia='+ idEvento;
		var url ='<iframe src='+contextoPagina+'/generarExhorto.do'+parametros+' width="920" height="400"/>';
		
		$.newWindow({id:"iframewindowVisorExhorto"+idWindowVisorExhortoPJ, statusBar: true, posx:200,posy:50,width:1140,height:450,title:"Consultar  Exhorto", type:"iframe",onWindowClose: function(id){
			idWindowVisorExhortoPJ--;
		}});
		$.updateWindowContent("iframewindowVisorExhorto"+idWindowVisorExhortoPJ,url);
		$("#" +"iframewindowVisorExhorto"+idWindowVisorExhortoPJ+ " .window-maximizeButton").click();		
   	}
	



	</script>



			<table width="950" border="0" cellspacing="0" cellpadding="0">

			    <tr>
			        <td>&nbsp;</td>
			        <td colspan="5" rowspan="6">
			            <table id="gridExhortosPJJU" width="100%"></table>
			            <div id="pager1"></div></td>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td>&nbsp;</td>
			        <td>&nbsp;</td>
			        <td>&nbsp;</td>
			        <td>&nbsp;</td>
			        <td colspan="2">&nbsp;</td>
			        <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td align="right">&nbsp;</td>
			        
			        <td align="right">&nbsp;</td>
			        
			        <td colspan="3" align="center">
						<input type="button" id="btnAgenerarExhorto" value="Agregar Exhorto" class="btn_Generico">
						<!--<input type="submit" id="consultarMandamiento" value="Consultar Mandamiento" />-->
			       	</td>
			       	
			       	<td>
						<form name="frmDocPdf" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
							<!--<input type="hidden" name="documentoId" />-->
						</form>			       	
			       	</td>
			       	

			   
			    </tr>
			</table>

		