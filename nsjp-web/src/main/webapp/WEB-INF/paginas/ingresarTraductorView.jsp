<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Ingresar Traductor</title>
			<!--	Hoja de estilo para los gadgets-->
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
			<!--    Hoja de estilo para easyaccordion-->
			<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />

<style type="text/css">
DD P {
	LINE-HEIGHT: 120%
}

#iTraductorAccordionPane {
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

#iTraductorAccordionPane DL {
	WIDTH: 1000px;
	HEIGHT: 355px
}

/*acordeon editar*/
#iTraductorAccordionPane DT {
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

#iTraductorAccordionPane DT.active {
	BACKGROUND: url(<%=request.getContextPath()%>/resources/images/barra_ver_inact.png);
	background-repeat: no-repeat;
	COLOR: #f5f5f5;
	CURSOR: pointer;
	background-position: 30px;
}

#iTraductorAccordionPane DT.hover {
	COLOR: #f5f5f5
}

#iTraductorAccordionPane DT.hover.active {
	COLOR: #f5f5f5
}

#iTraductorAccordionPane DD {
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
#iTraductorAccordionPane .slide-number {
	COLOR: #68889b;
	FONT-WEIGHT: bold;
	LEFT: 30px
}

#iTraductorAccordionPane .active .slide-number {
	COLOR: #fff
}

#iTraductorAccordionPane A {
	COLOR: #58595b;
	font-family: Arial, Helvetica, sans-serif;
}

#iTraductorAccordionPane DD IMG {
	MARGIN: 0px;
	FLOAT: right
}

#iTraductorAccordionPane H2 {
	MARGIN-TOP: 10px;
	FONT-SIZE: 2.5em
}

#iTraductorAccordionPane .more {
	DISPLAY: block;
	PADDING-TOP: 10px
}
</style>

<!--Hoka de estilo para el texto dentro de los acordeones-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
<!--Hoja de estilo para los popups-->
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />

<!--Scripts necesarios para el funcionamiento de la JSP-->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
<!--Scrip para el idioma del calendario-->
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
		
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/cssGrid/ui.jqgrid.css" />
	
	<% 
   		UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
		Long rolId = 0L;
		Boolean esCoordinadorAmpGeneral = false;
	
		if(usuario!=null &&
	   		usuario.getRolACtivo()!=null &&
	   		usuario.getRolACtivo().getRol()!=null &&
	   		usuario.getRolACtivo().getRol().getRolId()!=null){
			rolId=usuario.getRolACtivo().getRol().getRolId();
		}
   	
   		if(rolId.equals(Roles.COORDINADORAMPGENERAL.getValorId())){
   			esCoordinadorAmpGeneral = true;
   		}
   	
	%>
	
		<script type="text/javascript">
		var esCoordinadorAmpGeneral = <%=esCoordinadorAmpGeneral%>;
		var rolId =  <%=rolId%>;
		var verAlias=1;
		var idindi=0;
		var numeroExpediente="";
		var elemntoNuevo="no";
		var deshabilitarCampos = window.parent.deshabilitarCamposPM;
		var modificaGrid=true;
 		var idRolActivo = <%=rolId%>;
 		var idElemento='<%= request.getParameter("idTraductor")%>';
		
		numeroExpediente='<%= request.getParameter("numeroExpediente")%>';
			jQuery().ready(
				function () {
					var id=<%= request.getAttribute("idInvolucrado")%>;
					elemntoNuevo='<%= request.getParameter("elemento")%>';
					if(id!=null){
						datosTraductor(id);
					}
					$( "#tabs" ).tabs();
					$('#iVictimaBtnModificarDatos').hide();
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
					$('#iTraductorAccordionPane').easyAccordion({ 
						autoStart: false, 
						slideInterval: 3000
					});
					var num=parent.num;
					if(num!=null && num!="0"){
						$("#anularInvolucrado").hide();
						$("#anularInv").hide();
						//$("img.ui-datepicker-trigger").hide();
					}
					ocultaDomicilioNotificaciones();
					$("#iVictimaCmpMenorEdad").click(formaCapturaMenorEdad);

					//Codigo para guardar los datos de la pantalla
					$("#iVictimaBtnGuardar").click(guardarTraductor);
					$("#iVictimaBtnModificarDatos").click(avilitaDatos);
					
					//codigo para anular
					$('#anularInvolucrado').hide();
					$("#anularInvolucrado").click(eliminarTraductor);
					
					//$("#iVictimaCmpServidorPublico").click(formaCapturaServidorPublico);
					habilitaDeshabilitaTabAcordeon1("servidorPublicoTab",0);

					var idTraductor='<%= request.getParameter("idTraductor")%>';
					if(idTraductor != "null"){
						$("img.ui-datepicker-trigger").hide();
						elemntoNuevo="no";
						consulta(idTraductor);
						$('#anularInvolucrado').show();
					}
					else{
						inicializaDatosGenerales();
					}
					
					//Instruccion pensada solo para el caso de policia ministerial
					if(deshabilitarCampos == true){
						$(":enabled").attr('disabled','disabled');
					}

					//ocultamos los campos referente a Alias
					$("#trAliasTxt").hide();
					$(".tdAliasCmp").hide();
					
					if(idRolActivo == '<%=Roles.PROCURADOR.getValorId()%>' || idRolActivo == '<%=Roles.SUBPROCURADOR.getValorId()%>' ||
							   idRolActivo == '<%=Roles.DIRECTOR_GENERAL.getValorId()%>' || idRolActivo == '<%=Roles.DIRECTOR_UI.getValorId()%>' || 
							   idRolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>'){
								
						$(":enabled").attr('disabled','disabled');
						$('input[type="submit"]').hide();
						$('input[type="button"]').hide();
						bloqueaCamposMediosDeContactoGrid();
						habilitaDatosEspecificos();			
								
					}
					
					if (rolId == '<%=Roles.DEFENSOR.getValorId()%>' || rolId == '<%=Roles.DEFENSORATE.getValorId()%>' || rolId == '<%=Roles.COORDINADORDEF.getValorId()%>' ){
						$('#iVictimaBtnModificarDatos').hide();
						$('#anularInvolucrado').hide();
						habilitaDatosEspecificos();
					}
				});//FIN funcion onready

			function consulta(id){
				$.ajax({
			    	  type: 'POST',
			    	  url:  '<%= request.getContextPath()%>/consultarInvolucrado.do',
			    	  data: 'idInvolucrado='+id,
			    	  dataType: 'xml',
			    	  async: false,
			    	  success: function(xml){
			    		  datosXML=xml;
			    		  pintaDatosGenerales(xml);
			    		  pintaDatosMultiselect(xml);
			    		  //pintaDatosDomicilio(xml);
			    		  pintaDatosDomicilioTraductor(xml);
			    		  if($(xml).find('involucradoDTO').find('esMismoDomicilio').text() == "false"){
					    	  pintaDatosDomicilioNotif(xml);
	    				  }
			    		  pintaDatosTipoDocIdentificacion(xml);
					  }
			    });

				idindi=id;
				deshabilitarDatosGenerales();
				deshabilitaDatosDomicilio();
				deshabilitarDatosIdentificacion();
				mediosContactoCorreoActualiza();
				mediosContactoTelefonoActualiza();
				$('#iVictimaBtnModificarDatos').show();
				$('#iVictimaBtnGuardar').hide();
			}

			function avilitaDatos(){
				$("img.ui-datepicker-trigger").show();
				habilitarDatosGenerales();
				habilitarDatosDomicilio();
				habilitarDatosIdentificacion();
				$('#anularInvolucrado').show();
				$('#iVictimaBtnGuardar').show();
				$('#iVictimaBtnModificarDatos').hide();
				modificaGrid=true;
			}
			function desavilitaDatos(){
				deshabilitarDatosGenerales();
				deshabilitaDatosDomicilio();
				deshabilitarDatosIdentificacion();
				mediosContactoCorreoActualiza();
				mediosContactoTelefonoActualiza();
				$('#iVictimaBtnModificarDatos').show();
				$('#iVictimaBtnGuardar').hide();
				$('#anularInvolucrado').hide();
			}
			//asociamos la funcion que atiende el evento click del check de servidor publico
			function formaCapturaServidorPublico() {
				if ($("#iVictimaCmpServidorPublico").is(':checked')) {
					habilitaDeshabilitaTabAcordeon1("servidorPublicoTab",1);
				}else{
					habilitaDeshabilitaTabAcordeon1("servidorPublicoTab",0);
				}				
			}

			/*
			* Funcion para deshabilitar el tab de un acordeon, se pasa el id del elemento DT
			* y un 0 para deshabilitar o un 1 para habilitar
			*/
			function habilitaDeshabilitaTabAcordeon1(idTabAcordeon,bandera)
			{
				if(parseInt(bandera)==0)//Deshabilita el tab del acordeon
				{
					$("#"+idTabAcordeon).unbind('click');
					if($("#"+idTabAcordeon).hasClass('active'))
					{
						$("#"+idTabAcordeon).removeClass('active').addClass('inactive');
						$("#"+idTabAcordeon).parent().find('dt.no-more-active:first').click();
					}
					else
					{
						$("#"+idTabAcordeon).removeClass('no-more-active').addClass('inactive');
					}
				}
				else//habilita los tabs del acordeon
				{
					$("#"+idTabAcordeon).removeClass('inactive').addClass('no-more-active');
					$("#"+idTabAcordeon).click(function(){		
						jQuery($("#"+idTabAcordeon)).activateSlide();
						//clearTimeout(timerInstance.value);
					});
				}
			}
			
				function formaCapturaMenorEdad() {
					if ($("#iVictimaCmpMenorEdad").is(':checked')) {
						$("#iVictimaAccordionDialogoMenorEdad").dialog("open");
					}
				}
				/*
				 *Imprime los datos que vienen de la funcion espejoDatos de datos generales, 
				 *en la pantalla ingresar representante legal
				 */
				function imprimeDatosPadre(nombre, apPat, apMat){
					document.getElementById('nombTraductor').innerHTML=nombre;
					document.getElementById('apPatTraductor').innerHTML=apPat;
					document.getElementById('apMatTraductor').innerHTML=apMat;
				}				

				function datosTraductor(id) {
				       				   	  
					      $.ajax({
						      type: 'POST',
						      async: false,
					    	  url: '<%= request.getContextPath()%>/ConsultarIndividuoDatos.do',
					    	  data:'idInvolucrado='+id,
					    	  dataType: 'xml',
					    	  success: function(xml){
						    	
						    	  $('#iTraductorCmpCalidad').val($(xml).find('calidadDTO').find('descripcionEstadoFisico').text());
						    	  pintaDatosGenerales(xml);
						    	  pintaDatosTipoDocIdentificacion(xml);
						    	  espejoDatos();

							     // $('#nombTraductor').val($(xml).find('nombresDemograficoDTO').find('nombreDemograficoDTO').find('nombre').text());
						    	 // $('#apPatTraductor').val($(xml).find('nombresDemograficoDTO').find('nombreDemograficoDTO').find('apellidoPaterno').text());
						    	 // $('#apMatTraductor').val($(xml).find('nombresDemograficoDTO').find('nombreDemograficoDTO').find('apellidoMaterno').text());
					    	  }
					    	});
						}


				/**
				* Funci&oacute;n que guarda los datos de la pantalla
				*/
				function guardarTraductor(){
					$('#iVictimaBtnGuardar').unbind();
					
					var validaRFC_CURP;
					validaRFC_CURP=camposGeneralesValidos();
					if(validaRFC_CURP==1){
					var nombreGeneralOP= trim($('#datosGeneralesCmpNombres').val());
					if(nombreGeneralOP!=""){
						var params = '';
						params += 'idIndividuo='+idindi;
						params += '&calidadDelIndividuo=7';
						params += '&expediente=1';
						params += '&numeroExpediente='+numeroExpediente;
						//params += '&esServidorPublico=' + $('#iVictimaCmpServidorPublico').is(':checked');
						params += '&esTraductor=' + $(':radio[name=iVictimaCmp]:checked').val();						
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
						datosPestania = obtenerMedios();
						params += datosPestania;
	
						//Documento de identificacion
						datosPestania = '&';
						datosPestania += recuperaDatosTipoDocIdentificacion();
						params += datosPestania;

						params += '&esVivo=' + true;
						
						$.ajax({								
					    	  type: 'POST',
					    	  async:false,
					    	  url: '<%= request.getContextPath()%>/guardarIndividuo.do?numeroExpediente='+numeroExpediente +'',
					    	  data: params,				
					    	  dataType: 'xml',
					    	  success: function(xml){
					    		  if(elemntoNuevo=="si"){
										window.parent.refresca();
								    }else{
					    		  		window.parent.cargaTraductor($(xml).find('IngresarIndividuoForm').find('nombre').text(),$(xml).find('IngresarIndividuoForm').find('idIndividuo').text());
								    }					    		  
					    		  idindi=$(xml).find('IngresarIndividuoForm').find('idIndividuo').text();
					    		  alertDinamico('Traductor guardado');
					    		  desavilitaDatos();
					    	  }
					    	});	
					}else{
						alertDinamico('Favor de capturar el nombre del involucrado correctamente');
					}
				}
				else if(validaRFC_CURP==0){
					alertDinamico("Favor de verificar que el CURP ingresado sea correcto");
				}
				else{
					alertDinamico("Favor de verificar que el RFC ingresado sea correcto");
				}
				$("#iVictimaBtnGuardar").click(guardarTraductor);
			}
			
			function eliminarTraductor(){					
				guardarTraductorEliminar();
			}
			
			function guardarTraductorEliminar(){
				if(idindi!='null' && idindi!=0)
				{
					//debemos mostrar un confirm
					customConfirm ("&iquest;Est&aacute; seguro que desea anular al traductor?", "", anularInvolucrado);
				}
			}
			
			//Funcion que manda a anular al involucrado en la BD
			function anularInvolucrado(){
				//primero revisaremos si el involucrado cuenta con relaciones
				$.ajax({								
			    	  type: 'POST',
			    	  url: '<%= request.getContextPath()%>/consultarRelacionesElementoXId.do',
			    	  data: 'idElemento='+idindi,				
			    	  dataType: 'xml',
			    	  success: function(xml){
			    		  	   //revisamo si hubo relaciones o no
			    		  	   if(parseInt($(xml).find('numRel').text())>-1)
							   {
			    		    	   if(parseInt($(xml).find('numRel').text())==0)
			    		    	   {
			    		    		   //no hay reaciones
			    		    		   anularInvolucradoCnRelaciones();
			    		    	   }
			    		    	   else{
			    		    		   //hay relaciones, preguntamos si desea eliminar
				    		    	   var mensaje = "El involucrado tiene relaciones con: <br/>";
				    		    	   //barremos la lista de relaciones
				    		    	   $(xml).find('cadena').each(function(){
				    		    		   mensaje+= $(this).text()+ "<br/>";
			            			   });
				    		    	   mensaje+= "<br/>&iquest;Est&aacute; seguro de querer eliminarlo?";
				    		    	   customConfirm (mensaje, "", anularInvolucradoCnRelaciones);
			    		    	   }
							   }
			    		       else
			    		      {
			    		    	   //casos de error
			    		    	   if(parseInt($(xml).find('numRel').text())>-1)
							   		{
			    		    		   //Lista nula
							   			customAlert("No se logr&oacute; revisar si el involucrado tiene relaciones, intente m&aacute;s tarde");
							   		}
			    		    	   else if(parseInt($(xml).find('numRel').text())>-2)
								   {
			    		    		   //ID no llego
			    		    		   customAlert("Ocurri&oacute; un problema de conexi&oacute;n, intente m&aacute;s tarde");
								   }
			    		    	   else if(parseInt($(xml).find('numRel').text())>-3)
								   {
			    		    		   //excepcion
			    		    		   customAlert("Ocurri&oacute; un problema al tratar de eliminar el involucrado, intente m&aacute;s tarde");
								   }
			    		      }
			    	  }
			    });
			}
			
			//Funcion que manda a borrar un elemento con sus relaciones
			function anularInvolucradoCnRelaciones()
			{
				//el elemento no cuenta con relaciones por lo tanto se puede anular
		    	   $.ajax({								
				    	  type: 'POST',
				    	  url: '<%= request.getContextPath()%>/anularElemento.do',
				    	  data: 'idIndividuo='+idindi,				
				    	  dataType: 'xml',
				    	  success: function(xml){
				    		//procederemos a tratar de eliminar la evidencia
								if(parseInt($(xml).find('banderaOp').text())==1)
								{
									window.parent.eliminarVictima(idindi);
									window.parent.customAlert("S&eacute; logr&oacute; anular al traductor con &eacute;xito");
									cerrarCustomVentana();
									//window.parent.cerrarVentanaVictima();
								}
								else if(parseInt($(xml).find('banderaOp').text())==0)
								{
									//se puede eliminar el objeto sin problemas
									window.parent.customAlert("No se logr&oacute; anular al traductor v&iacute;ctima");
								}
								else if(parseInt($(xml).find('banderaOp').text())==-1)
								{
									window.parent.customAlert("Ocurri&oacute; un error al tratar de anular al traductor,<br/>consulte a su administrador ");
								}
				    	  }
				    });
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
						<td width="55%" align="left"><input type="button" class="ui-button ui-corner-all ui-widget" id="anularInvolucrado" value="Anular Involucrado"></td>						
					</tr>
				</table>
			</td>
		</tr>
		<tr valign="top">
			<td>
				<table id="iVictimaWorkSheet" width="100%"  border="0">
					<tr valign="top">
						<td width="35%">
							<table>
								<tr>
									<td>
										<img alt="foto" src="<%= request.getContextPath() %>/resources/images/foto.png" id="iVictimaCmpFoto">
									</td>
									<td>
										
									</td>
								</tr>
								<tr>
									<td align="right" colspan="2" style="display: none;">
										<!-- Servidor P&uacute;blico&nbsp;&nbsp;<input type="checkbox" value="false" id="iVictimaCmpServidorPublico"/> -->
									</td>
								</tr>
							</table>
						</td>
						<td width="25%">
							<table width="100%" border="0">
								<tr>
									<td style="visibility: hidden;">
										<input type="radio" name="iVictimaCmp" id="iVictimaCmp" value="false">
									</td>
									<td align="left" style="visibility: hidden;">Int&eacute;rprete</td>
									<td align="right">&nbsp;</td>
								</tr>
								<tr>
									<td style="visibility: hidden;">
										<input type="radio" name="iVictimaCmp" id="iVictimaCmp" value="true">
									</td>
									<td align="left" style="visibility: hidden;">Traductor</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
							</table>
						</td>
						<td width="40%">
							<table>
								<tr>
									<td align="right">
										Nombre:
									</td>
									<td>
										<div id="nombTraductor" style="border: 0; background: #DDD;width: 250px;">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td align="right">
										Apellido Paterno:
									</td>
									<td>
										<div id="apPatTraductor" style="border: 0; background: #DDD;width: 250px;">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td align="right">
										Apellido Materno:
									</td>
									<td>
										<div id="apMatTraductor" style="border: 0; background: #DDD;width: 250px;">&nbsp;</div>
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
										<input type="button" value="Guardar" id="iVictimaBtnGuardar" class="ui-button ui-corner-all ui-widget"/>
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
							<div id="iTraductorAccordionPane" style="width: 100%" >
					            <dl>
					                 <dt>Datos Generales</dt>
					                <dd>
					                	 <jsp:include page="datosGeneralesView.jsp"/>
									</dd>
					                <dt>Domicilio</dt>
					                <dd>
					                	<jsp:include page="ingresarDomicilioView.jsp"/>
					                </dd>
					                <dt>Medios de Contacto</dt>
					                <dd>
					                	<jsp:include page="ingresarMediosContactoView.jsp"/>
					                </dd>
					                <dt>Documentos de Identificaci&oacute;n</dt>
					                <dd>
					                	<jsp:include page="ingresarDocumentoIdentificacionView.jsp"/>
					                </dd>
					            </dl>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>