<%@page import="org.omg.CORBA.Request"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Ingresar Representante Legal</title>
		<link rel="stylesheet" type="text/css" media="screen"href="<%= request.getContextPath()%>/resources/css/estilos.css" />
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
		<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />				
		<style type="text/css">
			DD P {
				LINE-HEIGHT: 120%
			}

			#iRepLegalAccordionPane {
				PADDING-BOTTOM: 0px;
				PADDING-LEFT: 6px;
				WIDTH: 1000px;
				PADDING-RIGHT: 0px;
				HEIGHT: 362px;
				PADDING-TOP: 10px;
				background-image: url(<%=request.getContextPath()%>/resources/images/back_datos_gral.png);
				background-repeat: no-repeat;
				border: 0px solid #000;
			}

			#iRepLegalAccordionPane DL {
				WIDTH: 1000px;
				HEIGHT: 355px
			}
			
			/*acordeon editar*/
			#iRepLegalAccordionPane DT {
				TEXT-ALIGN: right;
				PADDING-BOTTOM: 16px;
				PADDING-TOP: 2px;
				PADDING-LEFT: 0px;
				LINE-HEIGHT: 35px;
				TEXT-TRANSFORM: none;
				/*acomodo texto*/
				PADDING-RIGHT: 40px;
				FONT-FAMILY: Arial, Helvetica, sans-serif;
				LETTER-SPACING: 1px;
				/*distancia persianas*/
				HEIGHT: 25px;
				COLOR: #f5f5f5;
				FONT-SIZE: 12px;
				FONT-WEIGHT: normal;
				background-image: url(<%=request.getContextPath()%>/resources/images/barra_ver_act.png);
				background-repeat: no-repeat;
				background-position: 28px;
			}
			
			#iRepLegalAccordionPane DT.active {
				BACKGROUND: url(<%=request.getContextPath()%>/resources/images/barra_ver_inact.png);
				background-repeat: no-repeat;
				COLOR: #f5f5f5;
				CURSOR: pointer;
				background-position: 30px;
			}

			#iRepLegalAccordionPane DT.hover {
				COLOR: #f5f5f5
			}
			
			#iRepLegalAccordionPane DT.hover.active {
				COLOR: #f5f5f5
			}
			
			#iRepLegalAccordionPane DD {
				BORDER-BOTTOM: #ffffff 0px solid;
				BORDER-LEFT: 0px;
				PADDING-BOTTOM: 1px;
				PADDING-LEFT: 1px;
				PADDING-RIGHT: 1px;
				/*BACKGROUND: url(<%=request.getContextPath()%>/images/jquery/plugins/easyaccordion/slide.jpg) repeat-x left bottom;*/
				BORDER-TOP: #ffffff 0px solid;
				MARGIN-RIGHT: 1px;
				BORDER-RIGHT: #ffffff 0px solid;
				PADDING-TOP: 1px
			}
			
			/*distancia y color de numero*/
			#iRepLegalAccordionPane .slide-number {
				COLOR: #68889b;
				FONT-WEIGHT: bold;
				LEFT: 30px
			}
			
			#iRepLegalAccordionPane .active .slide-number {
				COLOR: #fff
			}
			
			#iRepLegalAccordionPane A {
				COLOR: #58595b;
				font-family: Arial, Helvetica, sans-serif;
			}

			#iRepLegalAccordionPane DD IMG {
				MARGIN: 0px;
				FLOAT: right
			}
			
			#iRepLegalAccordionPane H2 {
				MARGIN-TOP: 10px;
				FONT-SIZE: 2.5em
			}
			
			#iRepLegalAccordionPane .more {
				DISPLAY: block;
				PADDING-TOP: 10px
			}
		</style>
		<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
		<script type="text/javascript">
		var gingRepLegCalidadDelIndividuo;
		gingRepLegCalidadDelIndividuo='<%= request.getAttribute("idCalidad")%>';
		
		var numeroExpediente="";
		var idOrganizacion="";
		var idUsuario="";
		var idindi=0;
		var idDenunciante="";
		
			jQuery(document).ready(function(){

					var id=<%= request.getAttribute("idInvolucrado")%>;
					idDenunciante='<%= request.getParameter("idDenunciante")%>';
					numeroExpediente='<%= request.getParameter("numeroExpediente")%>';
					idOrganizacion='<%= request.getParameter("idOrganizacion")%>';
					idUsuario='<%= request.getParameter("idUsuario")%>';
					
					if(idDenunciante != "null" || idDenunciante != null){
						//alert("Aqui entro idDnunciante replegal::::"+idDenunciante);
						datosRepresentanteLegal(idDenunciante);
					} else {
						 $("#iVictimaBtnGuardar").show();
			    		 $("#iVictimaBtnModificarDatos").hide();
					}
					$( "#tabs" ).tabs();	
						$("#iVictimaAccordionDialogoMenorEdad").dialog({ autoOpen: false, 
						modal: true, 
						title: 'Menor de Edad', 
						dialogClass: 'alert', 
						width: 500 ,
						maxWidth: 600,
						buttons: {"Aceptar":function() {
											$(this).dialog("close");
										}
									} 
					});		

																
					$('#iRepLegalAccordionPane').easyAccordion({ 
						autoStart: false, 
						slideInterval: 3000
					});
					ocultaDomicilioNotificaciones();
					$("#iVictimaCmpMenorEdad").click(formaCapturaMenorEdad);

					//Codigo para guardar los datos de la pantalla
					$("#iVictimaBtnGuardar").click(guardarReprLegal);
					$("#iVictimaBtnModificarDatos").click(modificarRepLegal);					
					
				});
				
				function formaCapturaMenorEdad() {
					if ($("#iVictimaCmpMenorEdad").is(':checked')) {
						$("#iVictimaAccordionDialogoMenorEdad").dialog("open");
					}
				}
				function datosRepresentanteLegal(id) {
			       
				      $.ajax({
					      type: 'POST',
				    	  url: '<%= request.getContextPath()%>/ConsultarPersonaDatos.do',
				    	  data:'idInvolucrado='+id,
				    	  dataType: 'xml',
				    	  success: function(xml){
					    	
					    	  pintaDatosGenerales(xml);
					    	  pintaDatosTipoDocIdentificacion(xml);
					    	  pintaDatosNacimiento(xml);
					    	  espejoDatos();
					    	  deshabilitaDatosRepLegal()
					    	 // bloqueaCampos();
					    	 					    	 
					    	  if($(xml).find('involucradoDTO').find('elementoId').text()!=""){					    		 	
					    		 	//window.parent.cargaRepLegal($(xml).find('involucradoDTO').find('nombresDemograficoDTO').find('NombreDemograficoDTO').find('nombre').text(),$(xml).find('InvolucradoDTO').find('elementoId').text());
					    		 	$('#btnIngOrgFormalDatosRep').attr("checked","checked");
					    			$('#btnIngOrgFormalDatosRep').attr("disabled","disabled");
					    			$("#iVictimaBtnGuardar").hide();
						    		$("#iVictimaBtnModificarDatos").show();
					    	  }
					    	  else{
									$('#btnIngOrgFormalDatosRep').attr("disabled","");	
									$("#iVictimaBtnGuardar").hide();
						    		$("#iVictimaBtnModificarDatos").show();
					    	  }
						
				    	  }
				    	});
					}

				//function bloqueaCampos(){



			//		}
				/*
				 *Imprime los datos que vienen de la funcion espejoDatos de datos generales, 
				 *en la pantalla ingresar representante legal
				 */
				function imprimeDatosPadre(nombre, apPat, apMat){
					document.getElementById('nombRepresentanteLegal').value=nombre;
					document.getElementById('apPatRepresentanteLegal').value=apPat;
					document.getElementById('apMatRepresentanteLegal').value=apMat;
				}

				/**
				* Funci&oacute;n que guarda los datos de la pantalla
				*/
				function guardarReprLegal(){
					var nombreGeneralOP=$('#datosGeneralesCmpNombres').val();
					var params = '';
					if(nombreGeneralOP!=""){	
					 if(parseInt(idOrganizacion)!=0 || parseInt(idDenunciante)!=0)
					 {
						params += 'idIndividuo='+idDenunciante;
						//params += '&calidadDelIndividuo=6';
						params += '&calidadDelIndividuo=0';
						params += '&numExpediente='+numeroExpediente;
						params += '&idOrganizacion='+idOrganizacion;
						params += '&idUsuario='+idUsuario;
						
						//Datos generales, media filiacion, medios de contacto, documentos de identificacion
						var datosPestania = obtenerParametrosDatosGenerales();
						params += datosPestania;
	
						//Datos nacimiento
						datosPestania = obtenerParametrosDatosNacimiento();
						params += datosPestania;	
	
						//Domicilio
						datosPestania = obtenerParametrosDomicilio();
						params += datosPestania;
	
						//Medios de contacto
						//datosPestania = obtenerMedios();
						//params += datosPestania;
	
						//Documento de identificacion
						datosPestania = '&';
						datosPestania += recuperaDatosTipoDocIdentificacion();					
						params += datosPestania;
	
						   $.ajax({								
						    	  type: 'POST',
						    	  url: '<%= request.getContextPath()%>/guardarRepLegalOrg.do',
						    	  data: params,				
						    	  dataType: 'xml',
						    	  success: function(xml){
						    		  if(parseInt($(xml).find('code').text())==0)
						    		  {
						    			  //deshabilitamos el boton de guardado 
							    		  $("#iVictimaBtnGuardar").hide();
							    		  $("#iVictimaBtnModificarDatos").show();
							    		  
						    			  //window.parent.cargaRepLegal($(xml).find('involucradoDTO').find('nombresDemograficoDTO').find('nombre').text(),$(xml).find('involucradoDTO').find('elementoId').text());
						    			  window.parent.cargaRepLegal($(xml).find('involucradoDTO').find('nombresDemograficoDTO').find('nombreDemograficoDTO').find('nombre').text(),$(xml).find('involucradoDTO').find('elementoId').text());
						    		      idindi=$(xml).find('involucradoDTO').find('elementoId').text();
						    		      
						    			  alertDinamico("El Representante Legal se guard&oacute; exitosamente.");
						    			  window.parent.deshabilitaBotonRepresentante();
						    			  deshabilitaDatosRepLegal();
						    		  }
						    		  else
						    		  {
						    			  alertDinamico("Ocurri&oacute; un error al guardar el Representante Legal.");
						    		  }
						    	  }
						    	});
					 }
					}//end valida nombre
					else{
						alertDinamico("Favor de ingresar el nombre");
					}
				}
				
				function deshabilitaDatosRepLegal() {
					desavilitarDatosGenerales();
					deshabilitaDatosDomicilio();					
					desavilitarDatosIdentificacion();					
				}
				
				function habilitaDatosRepLegal() {
					avilitarDatosGenerales();
					avilitarDatosDomicilio();
					avilitarDatosIdentificacion();					
				}
				
				function modificarRepLegal() {
					habilitaDatosRepLegal();
					$("#iVictimaBtnGuardar").show();
					$("#iVictimaBtnModificarDatos").hide();
				}
				
		</script>
	</head>
<body>
	<div id="dialog-Alert" style="display: none">
		<table align="center">
			<tr>
				<td align="center">
					<span id="divAlertTexto"></span>
				</td>
			</tr>
		</table>	
	</div>
	<table border="0">
		<tr valign="top">
			<td>
				<table id="iVictimaViewHeader" width="100%" border="0">
					<tr>
						<td width="10%">Denuncia</td>
						<td width="70%">&nbsp;</td>
						<td width="30%" align="right"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr valign="top">
			<td>
				<table id="iVictimaWorkSheet" width="100%"  border="0">
					<tr valign="top">
						<td>
							<table>
								<tr>
									<td>
										<img alt="foto" src="<%= request.getContextPath() %>/resources/images/foto.png" id="iVictimaCmpFoto">
									</td>
									<td>
										
									</td>
								</tr>
								<tr>
									<td align="right" colspan="2">
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table width="100%" border="0">
								<tr>
									<td>
										
									</td>
									<td align="left">&nbsp;</td>
									<td align="right">&nbsp;</td>
								</tr>
								<tr>
									<td>							
									</td>
									<td align="left">&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
							</table>
						</td>
						<td>
							<table width="100%" align="right">
								<tr>
									<td align="right">
										Nombre:
									</td>
									<td align="right">
										<input name="nombre" title="Escribir nombre" type="text" size="40" id="nombRepresentanteLegal" disabled="disabled" onkeypress="return soloLetrasNPunto(event,this.id);"/>
									</td>
								</tr>
								<tr>
									<td align="right">
										Apellido Paterno:
									</td>
									<td align="right">
										<input name="aPaterno" title="Escribir apellido paterno" type="text" size="40" id="apPatRepresentanteLegal" disabled="disabled" onkeypress="return soloLetrasNPunto(event,this.id);"/>
									</td>
								</tr>
								<tr>
									<td align="right">
										Apellido Materno:
									</td>
									<td align="right">
										<input name="aMaterno" title="Escribir apellido Materno" type="text" size="40" id="apMatRepresentanteLegal" disabled="disabled" onkeypress="return soloLetrasNPunto(event,this.id);"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr valign="top">
						<td colspan="3">
							<table width="100%" >
								<tr valign="top">
									<td align="center">
										<input type="button" value="Modificar Datos" id="iVictimaBtnModificarDatos" class="ui-button ui-corner-all ui-widget"/>
										<input type="button" value="Guardar" id="iVictimaBtnGuardar" class="ui-button ui-corner-all ui-widget" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr valign="top">
			<td>
				<table width="100%" border="0">
					<tr valign="top">
						<td width="100%" valign="top">
							<div id="iRepLegalAccordionPane" style="width: 100%" >
					            <dl>
					                <dt id="cejaDatosGenerales">Datos Generales</dt>
					                <dd><jsp:include page="datosGeneralesView.jsp"/></dd>
					                <dt id="cejaDomicilio">Domicilio</dt>
					                <dd><jsp:include page="ingresarDomicilioView.jsp"/></dd>
					                <!-- <dt>Medios de Contacto</dt>
					                <dd><jsp:include page="ingresarMediosContactoView.jsp"/></dd> -->
					                <dt id="cejaDocumentosIdentificacion">Documentos de identificaci&oacute;n</dt>
					                <dd><jsp:include page="ingresarDocumentoIdentificacionView.jsp"/></dd>
					            </dl>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
<script type="text/javascript">
	//oculatmos campos dependiendo de la calidad del individuo
	ocultaCamposPorCalidadIndividuo();
</script>
</body>


</html>