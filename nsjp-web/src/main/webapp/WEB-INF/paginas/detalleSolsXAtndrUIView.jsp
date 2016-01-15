<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Detalle solicitudes por atender UI</title>

    <!--	Hoja de estilo para los gadgets-->
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
	
	<!--    Hoja de estilo para easyaccordion-->
    <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
    
    <!--script de jquery UI-->
	<!-- <script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-ui.min.js"></script> -->
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/prettify.js"></script>
	
	<!-- <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script> -->
	
	<!--Hojas de estilos para los componentes UI de Jquery-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
	
	<!--Hoja de estilos para el grid-->
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	
	<!--scripts de java script-->
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<!-- <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-ui-1.8.11.custom.min.js"></script>-->
	
	
	
      <!--Hoka de estilo para el texto dentro de los acordeones-->
      <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />
      <!--Hoja de estilo para los popups-->
      <link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>
      
      <!--Scripts necesarios para el funcionamiento de la JSP-->

      <!-- <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script> -->
      <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
      <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
      <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
      <!--Scrip para el idioma del calendario-->
      <script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
      
      <!--Scripts necesarios para la ejecucion del editor-->
	   <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
	   <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	   <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	   

	<!--scripts del gird-->
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jqgrid/jquery.jqGrid.min.js"></script>
      
      <script type="text/javascript">
      var idSolicitud;
      var detalleSolicitudWindowId;
      
	  	//var tipo= '<%=request.getParameter("tipo")!=null?request.getParameter("tipo"):""%>';
		var contextoPagina = "${pageContext.request.contextPath}";	
		jQuery().ready(	function () {
            jQuery(document).ajaxStop(desbloquearPantalla());
			idSolicitud='<%= request.getParameter("idSolicitud")%>';
			detalleSolicitudWindowId='<%= request.getParameter("detalleSolicitudWindowId")%>';
			var tipoUsuario='<%= request.getParameter("tipoUsuario")%>';
			if(parseInt(tipoUsuario)==0)
			{
				//coordinador
				$("#btnAsignar").show();
			}
			else
			{
				//agente
				$("#btnAsignar").hide();
			}
			//Se crean las tabs principales
			$("#tabsprincipalconsulta" ).tabs();
			
			//consultaremos la solicitud
			consultaSolicitud(idSolicitud);
			
			
  		});
		//TERMINA function On Ready

		function consultaSolicitud(idSolicitud)
		{
			//mandamos a consultar las solicitudes
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultaDetalleSolicitudXAtender.do',
				data: 'idSolicitud='+idSolicitud,
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('SolicitudDTO').each(function(){
						seteaDatossolicitud(xml);
						CKEDITOR.on("instanceReady", function (ev) {
		    	  	        var bodyelement = ev.editor.document.$.body;
		    	  	        bodyelement.setAttribute("contenteditable", false);
		    	  	    });
		    	  	    CKEDITOR.replace('editor1');
					});
				}
			});
		}
		
		function seteaDatossolicitud(xml)
		{
			//setea los datos del xml en los campos de detalle
			if($(xml).find('SolicitudDTO').find('asuntoSolicitud').text()!=''){
				$("#txtAsunto").val($(xml).find('SolicitudDTO').find('asuntoSolicitud').text());
			}else{
				$("#asunto").hide();
			}
			$("#txtNoExpediente").val($(xml).find('SolicitudDTO').find('expedienteDTO').find('numeroExpediente').text());
			$("#txtNoCaso").val($(xml).find('SolicitudDTO').find('casoDTO').find('numeroGeneralCaso').text());
			if($(xml).find('SolicitudDTO').find('casoDTO').find('numeroGeneralCaso').text()==''){
				$("#etiqueta_noCaso").hide();
				$("#texto_noCaso").hide();
			}
			$("#txtFolio").val($(xml).find('SolicitudDTO').find('folioSolicitud').text());
			$("#cbxEstatus").val($(xml).find('SolicitudDTO').contents('estatus').find('valor').text());
			$("#txtFechaSol").val($(xml).find('SolicitudDTO').find('strFechaCreacion').text());
			$("#txtFechaLim").val(); 
			$("#etiqueta_fechaLim").hide();
			$("#texto_fechaLim").hide(); 
			$("#cbxTipoSol").val($(xml).find('SolicitudDTO').find('tipoSolicitudDTO').find('valor').text());
			if($(xml).find('SolicitudDTO').find('esUrgente').text()=='false')
			{
				$("#rdbUrgenteNo").attr("checked","checked");
			}
			else
			{
				$("#rdbUrgenteSi").attr("checked","checked");
			}
			$(':radio[name=rdbUrgente]').attr('disabled','disabled');
			$("#texto_urgente").hide();
			$("#opcion_urgente").hide();


			if($(xml).find('SolicitudDTO').find('nombreSolicitanteExternoInterno').text()!=''){
				$("#txtNombreServidorPub").val($(xml).find('SolicitudDTO').find('nombreSolicitanteExternoInterno').text());
			}else if ($(xml).find('SolicitudDTO').find('nombreSolicitante').text()!=''){
				$("#txtNombreServidorPub").val($(xml).find('SolicitudDTO').find('nombreSolicitante').text());
			}

			// AGA Se corrige incidencia de Remitentes en la solicitud
			
			//if($(xml).find('SolicitudDTO').find('usuario').find('nombreFuncionario').text()!=''){
			//	$("#txtNombreServidorPub").val($(xml).find('SolicitudDTO').find('usuario').find('nombreFuncionario').text()+" "+$(xml).find('SolicitudDTO').find('usuario').find('apellidoPaternoFuncionario').text()+" "+$(xml).find('SolicitudDTO').find('usuario').find('apellidoMaternoFuncionario').text());
			//}else if ($(xml).find('SolicitudDTO').find('destinatario').find('nombreFuncionario').text()!=''){
			//	$("#txtNombreServidorPub").val($(xml).find('SolicitudDTO').find('destinatario').find('nombreFuncionario').text()+" "+$(xml).find('SolicitudDTO').find('destinatario').find('apellidoPaternoFuncionario').text()+" "+$(xml).find('SolicitudDTO').find('destinatario').find('apellidoMaternoFuncionario').text());
			//}else{
			//	$("#txtNombreServidorPub").val($(xml).find('SolicitudDTO').find('destinatarioInstExterna').find('nombre').text()+" "
			//			+ $(xml).find('SolicitudDTO').find('destinatarioInstExterna').find('apellidoPaterno').text()+" "
			//			+ $(xml).find('SolicitudDTO').find('destinatarioInstExterna').find('apellidoMaterno').text());
			//}
			
			// AGA Se corrige incidencia de Remitentes en la solicitud
			
			//if ($(xml).find('SolicitudDTO').find('destinatario').find('puesto').find('valor').text()!=''){
			//	$("#txtCargo").val($(xml).find('SolicitudDTO').find('destinatario').find('puesto').find('valor').text());   
			//}else{
			//	$("#txtCargo").val($(xml).find('SolicitudDTO').find('destinatarioInstExterna').find('puesto').text());
			//}
			
			
			if ($(xml).find('SolicitudDTO').find('nombreInstitucionSolicitante').text()!=''){
				$("#txtInstitucion").val($(xml).find('SolicitudDTO').find('nombreInstitucionSolicitante').text());
			}
			
			// AGA Se corrige incidencia de Remitentes en la solicitud
			
			//if ($(xml).find('SolicitudDTO').find('destinatarioInstExterna').find('confInstitucionDTO').find('nombreInst').text()!=''){
			//	$("#txtInstitucion").val($(xml).find('SolicitudDTO').find('destinatarioInstExterna').find('confInstitucionDTO').find('nombreInst').text());
			//}else{
			//	$("#txtInstitucion").val($(xml).find('SolicitudDTO').find('institucion').find('nombreInst').text());  				
			//}
			
			
			// AGA Se corrige incidencia de Remitentes en la solicitud
			
			//if($(xml).find('SolicitudDTO').find('usuario').find('areaActual').find('nombre').text()!=''){
			//	$("#txtAreaAdmin").val($(xml).find('SolicitudDTO').find('usuario').find('areaActual').find('nombre').text());
			//}else if ($(xml).find('SolicitudDTO').find('destinatario').find('departamento').find('area').find('nombre').first().text()!=''){
			//	$("#txtAreaAdmin").val($(xml).find('SolicitudDTO').find('destinatario').find('departamento').find('area').find('nombre').first().text());
			//}else{
			//	$("#txtAreaAdmin").val($(xml).find('SolicitudDTO').find('destinatarioInstExterna').find('area').text());
			//}
			
			if($(xml).find('SolicitudDTO').find('observaciones').text()!=''){
				$("#txtObservaciones").val($(xml).find('SolicitudDTO').find('observaciones').text());
			}else{
				$("#etiqueta_obs").hide();
				$("#texto_obs").hide();
			}
			
			if($(xml).find('SolicitudDTO').find('motivo').text()!=''){
				$("#editor1").val($(xml).find('SolicitudDTO').find('motivo').text());
			}else{
				$("#ceja_motivo").hide();
				$("#editor_motivo").hide();
			}
			//revisamos si la solicitud es del tipo de Carpeta de Investigacion
			var tipoSol=$(xml).find('SolicitudDTO').find('tipoSolicitudDTO').find('idCampo').text();
			//tipo de solicitud de carpeta de investigacion
			if(tipoSol == '<%= TiposSolicitudes.CARPETA_DE_INVESTIGACION.getValorId() %>'){
				if($(xml).find('SolicitudDTO').contents('estatus').find('idCampo').text() != '<%= EstatusSolicitud.CERRADA.getValorId()%>'){
					$("#btnEnviar").show();
				}
			}
			else{
				$("#btnEnviar").hide();
			}
			
			if(tipoSol == '<%= TiposSolicitudes.ATENCION_PSICOLOGICA.getValorId() %>' || tipoSol == '<%= TiposSolicitudes.TRABAJO_SOCIAL.getValorId() %>'
					|| tipoSol == '<%= TiposSolicitudes.ATENCION_JURIDICA.getValorId() %>'){
				modificaSolicitudUAVD(<%= EstatusSolicitud.CERRADA.getValorId() %>);
			}
		}
		
		function enviaCarpeta() {
			bloquearPantalla(true, "Enviando Carpeta de investigaci&oacute;n");  
        	var param ="";
        	param += 'folioSol='+$("#txtFolio").val();
        	param+= '&caso='+$("#txtNoCaso").val();
        	   $.ajax({
        	     type: 'POST',
        	     url: '<%= request.getContextPath()%>/EnviarCarpeta.do',
        		 data: param,
        		 dataType: 'xml',
        		 success: function(xml){
        			 $(xml).find('expedienteDTO').each(function(){
        				//se cambia el estatus a CERRADA
    					$.ajax({
    						type: 'POST',
    						url: '<%=request.getContextPath()%>/cerrarSolicitudUAVD.do',
    						data: 'idSolicitud='+idSolicitud,
    						dataType: 'xml',
    						async: false,
    						success: function(xml){
    							
    						}
 						});
        				desbloquearPantalla();
        				regreso= $(this).find('expedienteId').text()+" ";
        				if(regreso!=0){
        					if(typeof window.parent.cerrarVentanaGenerico == 'function'){
        						customAlert("Se envi&oacute; correctamente la carpeta", "Aviso",
        						function(){		
            						window.parent.cerrarVentanaGenerico(detalleSolicitudWindowId);
        						}
        						);
                			}
            			}else{
                			customAlert("No se logr&oacute; enviar la carpeta de investigaci&oacute;n");
                		}
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
					window.parent.recargaGridSolsXAtndr();
				}
			});
		}
    </script>
  </head>
  <body>
        <div id="tabsprincipalconsulta">
		<ul>
			<li><a href="#tabsconsultaprincipal-1">Solicitud</a></li>
			<li><span id="ceja_motivo"><a href="#tabsconsultaprincipal-2">Motivo</a></span></li>
		</ul>
		<div id="tabsconsultaprincipal-1">
			<table width="100%">
				<tr>
					<td align="right">
						<input type="button" id="btnEnviar" value="Enviar" onclick="enviaCarpeta()" class="ui-button ui-corner-all ui-widget"/>
						<span id="asignar">
							<input type="button" id="btnAsignar" value="Asignar" class="ui-button ui-corner-all ui-widget" />
						</span>
					</td>
				</tr>
				<tr>
					<td>
						<span id="asunto">
							Asunto : <textarea id="txtAsunto" cols="100" rows="2" readonly="readonly"></textarea>
						</span>
					</td>
				</tr>			
			</table>
			<table width="100%">
				<tr>
					<td width="50%">
						<table width="100%">
							<tr>
								<td>
									No. Expediente : 
								</td>
								<td>
									<input type="text" id="txtNoExpediente" maxlength="15" disabled="disabled" size="30"/> 
								</td>
							</tr>
							<tr>
								<td>
									<span id="etiqueta_noCaso">No. Caso :</span> 
								</td>
								<td>
									<span id="texto_noCaso"><input type="text" id="txtNoCaso" maxlength="15" disabled="disabled" size="30"/></span> 
								</td>
							</tr>
							<tr>
								<td>
									Folio : 
								</td>
								<td>
									<input type="text" id="txtFolio" maxlength="15" disabled="disabled" size="30"/> 
								</td>
							</tr>
							<tr>
								<td>
									Estatus : 
								</td>
								<td>
									<input type="text" id="cbxEstatus" maxlength="30" disabled="disabled" size="30"/> 
								</td>
							</tr>
							<tr>
								<td>
									Fecha de solicitud : 
								</td>
								<td>
									<input type="text" id="txtFechaSol" maxlength="10" disabled="disabled" size="30"/> 
								</td>
							</tr>
							<tr>
								<td>
									<span id="etiqueta_fechaLim">Fecha L&iacute;mite :</span> 
								</td>
								<td>
									<span id="texto_fechaLim"><input type="text" id="txtFechaLim" maxlength="10" disabled="disabled" size="30"/></span> 
								</td>
							</tr>
							<tr>
								<td>
									Tipo de Solicitud : 
								</td>
								<td>
									<input type="text" id="cbxTipoSol" maxlength="30" disabled="disabled" size="30"/> 
								</td>
							</tr>
							<tr>
								<td>
									<span id="texto_urgente">Urgente :</span> 
								</td>
								<td>
									<span id="opcion_urgente">
										<input type="radio" id="rdbUrgenteSi" name="rdbUrgente" value="1"  > S&iacute; &nbsp;&nbsp;&nbsp;
										<input type="radio" id="rdbUrgenteNo" name="rdbUrgente" value="0" > No
									</span>
								</td>
							</tr>
						</table>
					</td>
					<td width="50%">
						<table width="100%">
							<tr>
								<td>
									&nbsp; 
								</td>
								<td align="left">
									<b>Informaci&oacute;n Destinatario : </b> 
								</td>
							</tr>
							<tr>
								<td>
									Nombre del Servidor <br/>P&uacute;blico : 
								</td>
								<td>
									<input type="text" id="txtNombreServidorPub" maxlength="30" disabled="disabled" size="40"/> 
								</td>
							</tr>
							<tr>
								<td>
									<!--Cargo : -->
								</td>
								<td>
									<!--<input type="text" id="txtCargo" maxlength="30" disabled="disabled" size="40"/> -->
								</td>
							</tr>
							<tr>
								<td>
									Instituci&oacute;n : 
								</td>
								<td>
									<input type="text" id="txtInstitucion" maxlength="30" disabled="disabled" size="40"/> 
								</td>
							</tr>
							<tr>
								<td>
									<!--&Aacute;rea Administrativa : -->
								</td>
								<td>
									<!--<input type="text" id="txtAreaAdmin" maxlength="30" disabled="disabled" size="40"/> -->
								</td>
							</tr>
							<tr>
								<td>
									<span id="etiqueta_obs"><b>Observaciones : </b></span> 
								</td>
								<td>
									&nbsp; 
								</td>
							</tr>
							<tr>
								<td colspan="2">
									 <span id="texto_obs"><textarea id="txtObservaciones" rows="5" cols="40" readonly="readonly"></textarea></span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
        </div>
        <div id="tabsconsultaprincipal-2">
        	<span id="editor_motivo">
				<textarea class="jquery_ckeditor" cols="150" id="editor1" name="editor1" rows="10"></textarea>
			</span>
		</div>
       </div>
  
  </body>
  <script type="text/javascript">
	var config = {					
		toolbar:
		[
			['Source','-','Cut','Copy','Paste','-','Undo','Redo','-','Find','Replace','-','RemoveFormat'],
			['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
			['NumberedList','BulletedList','-','Outdent','Indent'],
			['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
			['Table','HorizontalRule'],
			'/',
			['Styles','Format','Font','FontSize','TextColor','BGColor','Maximize']
		],
		
		height:'250px',
		width:'900px'
	};			
	$('.jquery_ckeditor').ckeditor(config);

</script>
</html>