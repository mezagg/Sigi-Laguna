	<%@page import="mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion"%>
	<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
	<% 
	   	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
		
		Long idRolActivo = 0L;
		
		if (usuario != null && usuario.getRolActivo() != null){
			idRolActivo = usuario.getRolACtivo().getRol().getRolId();
		}
	%>
	 <script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	 <script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>
	 <!--Hoja de estilos de Layout-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<!--Hoja de estilos ultrasist-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />	
	<!--Hojas de estilos para los componentes UI de Jquery-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>

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
	var idRolActivo = <%=idRolActivo%>;
    var params = '';
	
	$(document).ready(function() {
		
		jQuery(document).ajaxStop(jQuery.unblockUI);
			      
	      if((typeof(numeroExpediente) !== "undefined") && !isEmpty(numeroExpediente)){
	      	params += '&numeroExpediente=' + numeroExpediente;
	      }else if(!isEmpty($("#numExpPJATP").val())){
	    	params += '&numeroExpediente=' + $("#numExpPJATP").val();  
	      }
	      
	      formaId=4;
	
		/*
		*Funcion que carga el grid, por default con las audiencias del dia
		*/
		jQuery("#gridAcumulacion").jqGrid({ 
			url:'<%= request.getContextPath()%>/consultaAcumulacionCausas.do?'+params,
			datatype: "xml", 
			colNames:[
						'N&uacute;mero Principal',
						'N&uacute;mero Acumulado',
						'Fecha',
						'Funcionario'
					], 
			colModel:[ 	{name:'numeroPrincipal',index:'numeroPrincipal', width:180,align:'center'},
			           	{name:'numeroExpediente',index:'numeroExpediente', width:280,align:'center'},
			           	{name:'fecha',index:'fecha', width:280,align:'center'}, 
			           	{name:'funcionario',index:'funcionario', width:380,align:'center'}
					],
			pager: jQuery('#pager2'),
			rowNum:10,
			caption:"Acumulaci&oacute;n de causas",
			rowList:[10,20,30],
			autowidth: false,
			sortname: 'Acumulaci&oacute;n',
			
			viewrecords: true,
			sortorder: "desc"
		}).navGrid('#pager2',{edit:false,add:false,del:false});	

		$("#gview_gridAcumulacion .ui-jqgrid-bdiv").css('height', '400px');	
		
		if(idRolActivo == '<%=Roles.JUEZPJ.getValorId()%>'){
			$("#btnbuscarCasoExpediente").click(consultarCausa);
		}else{
			$("#trAcumulacion").hide();			
		}
		$("#btnAgregarExpediente").click(acumularCausa);
		


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
	
	function acumularCausa(){
		var idExpediente=$("#idExpediente").val();
		var idNumExpediente=$("#idNumExpediente").val();
		var idcaso=$("#idcaso").val();

		if(idExpediente !=null){
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/acumularCausa.do',
				data: 'idExpediente='+ idExpediente+'&idNumExpediente='+idNumExpediente+'&numeroExpediente='+numeroExpediente,
				async: false,
				dataType: 'xml',
				success: function(xml){
					var respuesta=$(xml).find('Respuesta').text();
						customAlert(respuesta,"",function(){
							$("#gridAcumulacion").trigger("reloadGrid");
						});
				}
			});
		}
	}
	
	
	
	function consultarCausa(){
		var causa=$("#txtNoCausa").val();
		
		if(causa !=null){
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/consultarCausa.do',
				data: 'noCausa='+ causa,
				async: false,
				dataType: 'xml',
				success: function(xml){
					errorCode=$(xml).find('response').find('code').text();
					if(parseInt(errorCode)==0){
						$('#txtNoCaso').val($(xml).find('expedienteDTO').first().find('casoDTO').first().find('numeroGeneralCaso').text());
						$('#idExpediente').val($(xml).find('expedienteDTO').first().find('expedienteId').text());
						$('#idNumExpediente').val($(xml).find('expedienteDTO').first().find('numeroExpedienteId').text());
						$('#idcaso').val($(xml).find('expedienteDTO').first().find('casoDTO').first().find('casoId').text());
						
						
					}
					
				}
			});
		}
		
	}



	</script>



			<table width="950" border="0" cellspacing="0" cellpadding="0">

			    <tr>
			        <td>&nbsp;</td>
			        <td colspan="5" rowspan="6">
			            <table id="gridAcumulacion" width="100%"></table>
			            <div id="pager2"></div></td>
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
			    <tr id="trAcumulacion">
			        <td colspan="2" align="center"> N&uacute;mero de Causa
						<input type="text" id="txtNoCausa" style="width: 200px; border: 0; background: #DDD;">
					</td>
			        
			        <td align="center">
			        N&uacute;mero de Caso
						<input type="text" id="txtNoCaso" style="width: 200px; border: 0; background: #DDD;" readonly="readonly">
						<input type="hidden" id="idExpediente" name="idExpediente" />
						<input type="hidden" id="idNumExpediente" name="idNumExpediente"/>
						<input type="hidden" id="idcaso" name="idcaso"/>
						<input type="button" id="btnbuscarCasoExpediente" value="Buscar Causa" class="btn_Generico">
					</td>
			        
			        <td colspan="1" align="center">
						<input type="button" id="btnAgregarExpediente" value="Acumular Causa" class="btn_Generico">
						
			       	</td>
			       	
			       	<td>
						<form name="frmDocPdf" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
							<!--<input type="hidden" name="documentoId" />-->
						</form>			       	
			       	</td>
			       	

			   
			    </tr>
			</table>

		