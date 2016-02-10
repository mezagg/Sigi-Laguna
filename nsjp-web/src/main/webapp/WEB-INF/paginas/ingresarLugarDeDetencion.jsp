    <!--CSS, modificada para las tabs del domicilio -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/demo.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>	
    
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estiloTab.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/themes/1.8.10/jquery-ui.min.js"></script>
	
	<!--script de windows engine (frames)-->
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	
	<!--script de jquery UI-->
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	
	<!--scripts del gird-->
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/demo.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.timeentry.js"></script>

   <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
   <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>

	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<!--ESTILOS PARA LAS TABS-->
	<style>
	#tabs { height: 670px; } 
	.tabs-bottom { position: relative; } 
	.tabs-bottom .ui-tabs-panel { height: 500px; overflow: auto; } 
	.tabs-bottom .ui-tabs-nav { position: absolute !important; left: 0; bottom: 0; right:0; padding: 0 0.2em 0.2em 0; } 
	.tabs-bottom .ui-tabs-nav li { margin-top: -2px !important; margin-bottom: 1px !important; border-top: none; border-bottom-width: 1px; }
	.ui-tabs-selected { margin-top: -3px !important; }
	.tabEstilo  { height: 350px; overflow: auto; }
	</style>
	
<script>

    var numeroExpediente = '<%=request.getParameter("numeroExpediente")%>';
    var involucradoId = <%=request.getParameter("involucradoId")%>;
    
	$(document).ready(function() {
		//Se crean las tabs principales
		$( "#tabs" ).tabs();
		deshabilitaFuncionalidadDomicilio();
		$("#btnGuardarDomicilioDetencion").click(guardarDatosLugarDetencion);		
	});

	/*
	*Funcion que dispara ajusta funcionalidad de domicilio para este caso
	*/	
	function deshabilitaFuncionalidadDomicilio(){
		killDomicilioNotificaciones();
		killCoordenadasGeograficas();
	}

	function guardarDatosLugarDetencion(){
		var params = obtenerParametrosDomicilio();
		$.ajax({								
		  	  type: 'POST',
		  	  url: '<%= request.getContextPath()%>/agregarLugarDeDetencion.do?numeroExpediente='+numeroExpediente+'&involucradoId='+involucradoId+'',
		  	  data: params,				
		  	  dataType: 'xml',
		  	  success: function(xml){
		  		  alertDinamicoCerrar('Lugar de detenci&oacute;n guardado de manera correcta');				  
		  	  }
		  	});
	}
	
	//Funci&oacute;n para alertDinamicoCerrar
	function alertDinamicoCerrar(textoAlert){						
		$("#divAlertTextoCerrar").html(textoAlert);
	    $( "#dialog-AlertCerrar" ).dialog({
			autoOpen: true,
			resizable: false,
			modal: true,
			buttons: {				
				
				"Aceptar": function() {						
					window.parent.cerrarVentanaMostrarLugarDeDetencion();
					$( this ).dialog( "close" );
					$("#divAlertTextoCerrar").html("");					
				}				
			}
		});    
	 }
	
</script>

<html>
	<body>
	<!-- div para el alert dinamico antes de cerrar ventana -->
<div id="dialog-AlertCerrar" style="display: none">
	<table align="center">
		<tr>
        	<td align="center">
            	<span id="divAlertTextoCerrar"></span>
            </td>
        </tr>
     </table>              
</div>    	
		<table width="762px" height="313px" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><input type="button" id="btnGuardarDomicilioDetencion" value="Guardar" class="ui-button ui-corner-all ui-widget"></td>
			</tr>
			<tr>
				<td>
					<jsp:include page="ingresarDomicilioView.jsp"/>
				</td>
			</tr>
		</table>			
	</body>
</html>