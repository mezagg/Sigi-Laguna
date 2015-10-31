<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lineas de Investigaci&oacute;n</title>
        
        
        <!--		Hojas de estilos asociadas-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>			
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/arbolExpediente.js"></script>
        <script type="text/javascript">
            // El codigo javascript de este jsp se encuentra en solicitarAudiencia.js
            var esconderArbol = <%=request.getParameter("esconderArbol")!=null?"true":"false"%>;
            var numeroUnicoExpediente = '<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):"Sin numero"%>';
	   		var idnumeroExpediente='<%=request.getParameter("idNumeroUnicoExpediente")%>';
            var idHipotesis = "0";
	   		var idLineaInvestigacion = "0";
	   		var idLineaInvestigacionSeleccionada = 0;
	   		var pantallaSolicitada='<%=request.getParameter("pantalla")%>';
	   		var idLineaInvestigacionOrdinal = 0;
           
            jQuery().ready(function () {
            	
            	$('#seccionComentario').hide();  
            	cargaTitulos();
                   	
	   			$("#btnGuardarHipotesis").click(guardarHipotesis);
	   			$("#btnGuardarLineaDeInv").click(guardarLineaDeInvestigacion);
	   			$("#btnGuardarComentario").click(registrarComentario);
            	cargarDatosExpediente();
            	//Se ajusta el tama&ntilde;o del editor de la hipotesis
            	$('#tblHipotesis').hide();
            	$('#tblLineaInvestigacion').hide();
            	
        		$('#seccionDeDetalle').hide();
            	$('#btnNuevaLineaDeInv').click(function(){
            		$('#seccionDeBotones').hide();
            		$('#seccionDeDetalle').show();
                	$('#tblLineaInvestigacion').show();
                	$('#seccionTituloyTema').show();
                	mostrarLineasDeInvestigacion();
            		});
            	$('#btnHipotesis').click(function(){
            		$('#seccionDeBotones').hide();
            		$('#seccionDeDetalle').show();
            		$('#tblHipotesis').show();            		
            	});
            	
             	//if(idHipotesis!= "0"){
            	//}else{
            		//$('#btnHipotesis').click();
            	//}
             	

				if(pantallaSolicitada=='6'){					
					//Debe de ocultar el boton de la hipotesis y activar el de la linea de investigacion
					$('#btnHipotesis').hide();
					$('#seccionDeBotones').hide();
            		$('#seccionDeDetalle').show();
                	$('#tblLineaInvestigacion').show();
                	$('#seccionTituloyTema').show();
                	mostrarLineasDeInvestigacion();
				
				}else{
					consultarHipotesis();
				}
            	
            });
            
            
            
           function cargarDatosExpediente(){
			
			if(!esconderArbol){
				$.ajax({
			    	type: 'POST',
			    	url: '<%=request.getContextPath()%>/CargarArbolExpedienteEnDocumento.do',
			    	data: 'numeroUnicoExpediente='+numeroUnicoExpediente+'&idNumeroUnicoExpediente='+idnumeroExpediente,
			    	dataType: 'xml',
			    	success: function(xml){
			    		//Obtener Parametros de Configuracion
			    		var probableResponsableArbolTituloSeccion = '<bean:message key="probableResponsableArbolTituloSeccion"/>';
			    		var probableResponsableArbolTitulo = '<bean:message key="probableResponsableArbolTitulo"/>';
			    		
			    		contenido = escribirDatosGenerales(xml);			
			    		contenido += escribirInvolucrados(xml,probableResponsableArbolTituloSeccion, probableResponsableArbolTitulo);
			    		contenido += escribirUnidadesEspecializadas(xml);
			    		contenido += escribirListaObjetos(xml);
			    		contenido += escribirDelitos(xml);
			    		//contenido += escribirHechos(xml);
			    		
			    		$("#accordionDatosExpediente").append(contenido);
			    		$("#accordionDatosExpediente").treeview();
			    	}
				});
			}else{
				$("#marcoArbolExpediente").css('display','none');
				$("#idExpedientes").css('display','none');
				$("#tdArbolExp").css('width','1px');
			}
		}
           
        </script>
        
        <style type="text/css">
        .lineasDeInvestigacion{
        	padding:5px;
        }
        .titleLineaInvestigacion{
        	background-color:#D3D3D3;
        	border:1px solid;
        }
        
        .comentario{
        	border-left: 1px solid;
        	border-right: 1px solid;
        	border-bottom: 1px solid;
        	border-color: blue;
        	color: blue;
        }
        .detalleLineaInvestigacion{
        	display:none;
        }        
        </style>
        
        <script type="text/javascript">
        
        /*
	   	* Funcion que permite consultar hipotesis de un expediente
	   	*/
   		function consultarHipotesis(){
			var params;
		    params = '&numeroUnicoExpediente=' + numeroUnicoExpediente;

			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultarHipotesisPorNumExp.do',
				data: params, 
				dataType: 'xml',
				async: false,
				success: function(xml){					
					 if(parseInt($(xml).find('SeguimientoLIDTO').find('seguimientoLIId').text())> 0){
						$('.jquery_ckeditor').val($(xml).find('SeguimientoLIDTO').find('hipotesis').first().text());
						idHipotesis = parseInt($(xml).find('SeguimientoLIDTO').find('seguimientoLIId').first().text());
						//Se cambia leyenda del boton Hipotesis por Consultar hipotesis
						$('#btnHipotesis').val("Actualizar Hip&oacute;tesis");						
					  }
				}
			});
		}
        
        /*
	   	* Funcion para mandar guardar una hipotesis del expediente
	   	*/
   		function guardarHipotesis(){
			if(idHipotesis!=null && idHipotesis!="")
			{
				var hipotesis= escape($('.jquery_ckeditor').val());

				$.ajax({
					type: 'POST',
					url: '<%= request.getContextPath()%>/registrarHipotesisExpediente.do?idHipotesis='+idHipotesis+'&numeroUnicoExpediente='+numeroUnicoExpediente+'&hipotesis='+hipotesis,
					data: '',
					dataType: 'xml',
					async: false,
					success: function(xml){
						
						 if(parseInt($(xml).find('SeguimientoLIDTO').find('seguimientoLIId').text())> 0){
							 if(idHipotesis == 0){
								 customAlert("La hip&oacute;tesis se guard\u00F3 correctamente");
								 idHipotesis = parseInt($(xml).find('SeguimientoLIDTO').find('seguimientoLIId').text());
								$('#btnHipotesis').val("Actualizar Hip&oacute;tesis");
							 }
							 else
								 customAlert("La hip&oacute;tesis se actualiz\u00F3 correctamente")
							$('#seccionDeBotones').show();
		            		$('#seccionDeDetalle').hide();
		            		$('#tblHipotesis').hide();
							 
						  }else
							  customAlert('Error al intentar guardar la hip&oacute;tesis, int&eacute;ntelo m&aacute;s tarde');
						
					}
				});
			}
		}
        
   		/*
	   	* Funcion que permite guardar una linea de investigacion asociada a la hipotesis
	   	*/
   		function guardarLineaDeInvestigacion(){
				var idTitulo = parseInt($("#cbxTituloDeInvestigacion option:selected").val());				
				var params;
				
				if(idTitulo != -1 && !$('.ckeditor_TemaLineaDeInv').val() == ""){					
					params = '&idLineaInvestigacion=' + idLineaInvestigacion;
				    params += '&numeroUnicoExpediente=' + numeroUnicoExpediente;
				    params += '&temaDeLaLinea=' + escape($('.ckeditor_TemaLineaDeInv').val());
				    params += '&idTituloLineaInvestigacion=' + idTitulo;
				    $.ajax({
						type: 'POST',
						url: '<%= request.getContextPath()%>/registrarLineaInvestigacion.do',
						data: params, 
						dataType: 'xml',
						async: false,
						success: function(xml){							
							 if(parseInt($(xml).find('LineaInvestigacionDTO').find('lineaInvestigacionId').text())> 0){
								 if(idLineaInvestigacion == 0){
									 customAlert("La l\u00EDnea de investigaci\u00F3n se guard\u00F3 correctamente");
									 //Se limpian controles
									 $( "#cbxTituloDeInvestigacion" ).attr('selectedIndex',0);
									 $('.ckeditor_TemaLineaDeInv').val("");
									 mostrarLineasDeInvestigacion();
					          		 $('#seccionDeBotones').show();
				            		 $('#seccionTituloyTema').hide();				                	
									 //idLineaInvestigacion = parseInt($(xml).find('LineaInvestigacionDTO').find('lineaInvestigacionId').text());
								 }
								 else
									 customAlert("La l\u00EDnea de investigaci\u00F3n se actualiz\u00F3 correctamente")
								 
							  }else
								  customAlert('Error al intentar guardar la La l\u00EDnea de investigaci\u00F3n, int&eacute;ntelo mas tarde');
							
						}
					});
				}
				else{
					  customAlert("Se debe ingresar el t\u00EDtulo y la descripci\u00F3n de la l\u00EDnea de investigaci\u00F3n");
				}
		}
   		
   		/*
	   	* Funcion que permite guardar un comentario
	   	*/
   		function registrarComentario(){
   			var params;
			if(!$('.ckeditor_Comentario').val() == ""){					
			    params = '&comentario=' + escape($('.ckeditor_Comentario').val());
			    params += '&idLineaInvestigacion=' + idLineaInvestigacionSeleccionada;
			    $.ajax({
					type: 'POST',
					url: '<%= request.getContextPath()%>/registrarComentario.do',
					data: params, 
					dataType: 'xml',
					async: false,
					success: function(xml){							
						 if(parseInt($(xml).find('ComentarioDTO').find('comentarioId').text())> 0){
							 if(idLineaInvestigacion == 0){
								 customAlert("El comentario se guard\u00F3 correctamente");
								 //Se limpian controles
								 $( "#cbxTituloDeInvestigacion" ).attr('selectedIndex',0);
								 $('.ckeditor_Comentario').val("");
								 consultarComentarioXId(parseInt($(xml).find('ComentarioDTO').find('comentarioId').text()));		
				        		 $('#seccionDeBotones').show();
			            		 $('#seccionComentario').hide();
								 //idLineaInvestigacion = parseInt($(xml).find('ComentarioDTO').find('comentarioId').text());
							 }
							 else
								 customAlert("El comentario se actualiz\u00F3 correctamente")
							 
						  }else
							  customAlert('Error al intentar guardar el comentario, int&eacute;ntelo m&aacute;s tarde');
						
					}
				});
			}
			else{
				  customAlert("Se debe ingresar el comentario de la l\u00EDnea de investigaci\u00F3n");
			}
		}
   		
   		/*
	   	* Funcion que consulta y muestra las lineas de investigacion
	   	*/
   		function mostrarLineasDeInvestigacion(){
		    params = '&numeroUnicoExpediente=' + numeroUnicoExpediente;
		    params += '&pantalla=' + pantallaSolicitada;
		    $.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultarLineasInvestigacionXExpedienteId.do',
				data: params, 
				dataType: 'xml',
				async: false,
				success: function(xml){
					$('#divContenidoDeLasLineas').html($(xml).find('string-buffer').text());
				}
			});				
		}
   		/*
	   	* Funcion que permite guardar un comentario
   		*/
   		function ingresarComentario(idLineaInvestigacion){
   			idLineaInvestigacionSeleccionada = idLineaInvestigacion;        
        	$('#seccionTituloyTema').hide();
        	$('#seccionComentario').show(); 
   		 	$('#seccionDeBotones').hide();
   		}
   		
   		/**
   		* Funcionar que permite actualizar el identificador que se muestra en la leyenda
   		* Comentario asociado a la l&iacute;nea de investigaci&oacute;n X: al momento de 'Ingresar un comentario'
   		*/
   		function actualizaEtiqueta(idLineaInvestigacion){
   			idLineaInvestigacionOrdinal = idLineaInvestigacion;
   			$('#lblNumeroDeLinea').text(idLineaInvestigacion);
   		}
   		
   		/**
		* Metodo que permite imprimir una linea de investigacion
		*  - Genera y un documento con la informacion de la linea de investigacion y comentarios pero no se guarda en la BD		*  
		*/
   		function imprimirLinea(idLineaInvestigacion){
   			document.frmDoc.lineaInvestigacionId.value = idLineaInvestigacion;
   			document.frmDoc.esGuardado.value = 0;
   			document.frmDoc.numeroUnicoExpediente.value = numeroUnicoExpediente;
			document.frmDoc.submit();
   		}
		    
		/**
		* Metodo que permite cerrar una linea de investigacion
		*  - Genera y guarda y un documento con la informacion de la linea de investigacion y comentarios 
		*  - Cambia el estatus de la linea de investigacion de tal forma que se puedan mostrar 
		*    los botones de Imprimir, Cerrrar e Ingresar documento.
		*/
   		function cerrarLinea(idLineaInvestigacion){
   			if(confirm("&iquest;Desea cerrar la l&iacute;nea de investigaci&oacute;n?")){				
   				document.frmDoc.lineaInvestigacionId.value = idLineaInvestigacion;
   				document.frmDoc.esGuardado.value = 1;
   				document.frmDoc.numeroUnicoExpediente.value = numeroUnicoExpediente;
				document.frmDoc.submit();			
				$('#trBotonesLinea_'+ idLineaInvestigacion).hide();
			}
			else customAlert("No se ha cerrado la l&iacute;nea de investigaci&oacute;n");
   		}
		
   		/*
	   	* Funcion que consulta un comentario en base a su identificador
	   	*/
   		function consultarComentarioXId(comentarioId){
		    params = '&comentarioId=' + comentarioId;
		    $.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultarComentario.do',
				data: params, 
				dataType: 'xml',
				async: false,
				success: function(xml){
					//Buscamos el div asociado al comentario y actualizamos la linea de investigacion
					$('#idLineaInv_' + idLineaInvestigacionOrdinal).append($(xml).find('string-buffer').text()).fadeIn('slow');
				}
			});				
		}
   		
   		//Funcion que carga el combo de T&iacute;tulo de la l&iacute;nea de investigaci&oacute;n
		function cargaTitulos() {
		  $.ajax({
	    	  type: 'POST',
	    	  url: '<%= request.getContextPath()%>/consultarLineasDeInvestigacion.do',
	    	  data: '',
	    	  dataType: 'xml',
	    	  success: function(xml){
	    		  $('#cbxTituloDeInvestigacion').empty();
		    	  $('#cbxTituloDeInvestigacion').append('<option value="-1">- Seleccione -</option>');
	    		  $(xml).find('catLineasDeInvestigacion').each(function(){
					$('#cbxTituloDeInvestigacion').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				   });
	    	  }
	    	});
		}
   		
	   	</script>
        
    </head>
    <body>
    
    <!-- div para el alert dinamico -->
<div id="dialog-Alert" style="display: none">
	<table align="center">
		<tr>
        	<td align="center">
            	<span id="divAlertTexto"></span>
            </td>
        </tr>
     </table>              
</div>  
    
    
        <div id="tabprincipal">
        	<table  border=1; cellspacing="5" align="center" width="1124px">
            	<tr id="seccionDeBotones">
                	<td align="center" style="vertical-align: top">
                    	<input name="btnNuevaLineaDeInv" type="button" id="btnNuevaLineaDeInv" value="Ingresar nueva l&iacute;nea de investigaci&oacute;n" class="btn_Generico">                    	
                        <input name="btnHipotesis" type="button" id="btnHipotesis" value="Hip&oacute;tesis" class="btn_Generico">

                    </td>
              </tr>
                <tr id="seccionDeDetalle">
                	<td>
                		<!-- ETIQUETAS PARA LA SECCION DE LOS ELEMENTOS DEL EXPEDIENTE -->	
						<table align="center" width="1124px" border="0">
							<tr>			
								<td width="300px" valign="top" id="tdArbolExp">
									<h3><a href="#" id="idExpedientes">Elementos del Expediente</a></h3>
									
									<div style="height: 800px; 
											width: 300px;
											overflow: auto;
											border: 1px solid #666;
											padding: 0px;" id="marcoArbolExpediente">
											<ul id="accordionDatosExpediente" class="filetree"></ul>
									</div>
								</td>
								<td width="1000px" valign="top" align="center">
									<table border="0" width="100%" id="tblHipotesis">
									  <tr>
									    <td width="92%"><h3>Hip&oacute;tesis:</h3></td>
									    <td width="8%"><span style="vertical-align: top">
									      <input name="btnGuardarHipotesis" type="button" id="btnGuardarHipotesis" value="Guardar" class="btn_Generico">
									    </span></td>
								      </tr>
									  <tr>
									    <td colspan="2">
                                        	<jsp:include page="/WEB-INF/paginas/ingresarHipotesisView.jsp" flush="true"></jsp:include>
                                        </td>
								      </tr>
								  </table>
									<table border="0" width="100%" id="tblLineaInvestigacion">
									  <tr>
									    <td width="59%" valign="top">
									     <table border="0" width="100%" id="seccionTituloyTema">
									      <tr>
									        <td width="92%"><h3>T&iacute;tulo de la l&iacute;nea de investigaci&oacute;n</h3></td>
									        <td width="8%"><span style="vertical-align: top">
									          <input name="btnGuardarLineaDeInv" type="button" id="btnGuardarLineaDeInv" value="Guardar" class="btn_Generico">
									          </span></td>
								          </tr>
									      <tr>
									        <td colspan="2"><select name="cbxTituloDeInvestigacion" id="cbxTituloDeInvestigacion" style="width:255px">
									          <option value="-1" selected>- Selecciona -</option>
									          <option value="2690">Entorno de la discusi&oacute;n entre v&iacute;ctima y victimario</option>
									          <option value="2691">Llamadas telef&oacute;nicas an&oacute;nimas recibidas la v&iacute;spera del hecho delictivo</option>
                                            </select></td>
								          </tr>
									      <tr>
									        <td colspan="2">&nbsp;</td>
								          </tr>
									      <tr>
									        <td colspan="2"><h3>Tema de la l&iacute;nea de investigaci&oacute;n</h3></td>
								          </tr>
									      <tr>
									        <td colspan="2"><jsp:include page="/WEB-INF/paginas/ingresarTemaDeLineaDeInvView.jsp" flush="true"></jsp:include></td>
								          </tr>
								         </table>
								         
								         
									     <table border="0" width="100%" id="seccionComentario">
									      <tr>
									        <td width="92%"><h3>Comentario asociado a la l&iacute;nea de investigaci&oacute;n <label id="lblNumeroDeLinea"></label>:</h3></td>
									        <td width="8%"><span style="vertical-align: top">
									          <input name="btnGuardarLineaDeInv" type="button" id="btnGuardarComentario" value="Guardar" class="btn_Generico">
									          </span></td>
								          </tr>									      
									      <tr>
									        <td colspan="2">&nbsp;</td>
								          </tr>
									      <tr>
									        <td colspan="2">
									        	<jsp:include page="/WEB-INF/paginas/ingresarComentarioView.jsp" flush="true"></jsp:include>
									        </td>
								          </tr>
								         </table>
								        
								         
								        </td>
									    <td width="41%" valign="top"><table border="0" width="100%">
									      <tr>
									        <td><h3>L&iacute;nea(s) de investigaci&oacute;n:</h3></td>
								          </tr>
									      <tr>
									        <td><div style="height: 787px; 
											width: 350px;
											overflow: auto;
											border: 1px solid #666;
											padding: 0px;" id="divContenidoDeLasLineas">
                                            Sin registros
								            </div></td>
								          </tr>
								        </table></td>
								      </tr>
							  </table></td>
							</tr>
						</table>
                	</td>
                </tr>
            </table>
        </div>
        
        <form name="frmDoc" action="<%= request.getContextPath() %>/consultarDocumentoPorIDLinea.do" method="post">
		<input type="hidden" name="lineaInvestigacionId" />
		<input type="hidden" name="esGuardado" />
		<input type="hidden" name="numeroUnicoExpediente" />
		</form>
    </body>
</html>