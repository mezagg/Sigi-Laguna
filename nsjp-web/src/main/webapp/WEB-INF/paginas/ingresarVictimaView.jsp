<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO"%>
<%
					UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
					ConfInstitucionDTO confInstitucionDTO = usuarioDTO.getInstitucion();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Ingresar V&iacute;ctima</title>
	
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jquery.easyaccordion.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />

		<style type="text/css">
			DD P {
				LINE-HEIGHT: 120%
			}
			#iVictimaAccordionPane {
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
			#iVictimaAccordionPane DL {
				WIDTH: 1085px; HEIGHT: 355px
			}
			
			/*acordeon editar*/
			#iVictimaAccordionPane DT {
				TEXT-ALIGN: right;
				PADDING-BOTTOM: 16px;
				PADDING-TOP: 2px;
				PADDING-LEFT: 0px;
				LINE-HEIGHT: 35px;
				TEXT-TRANSFORM: none;	
				/*acomodo texto*/PADDING-RIGHT: 40px;
				FONT-FAMILY: Arial, Helvetica, sans-serif;
				LETTER-SPACING: 1px;
				/*distancia persianas*/HEIGHT: 25px;
				COLOR: #f5f5f5;
				FONT-SIZE: 12px;
				FONT-WEIGHT: normal;	
				background-image: url(<%=request.getContextPath()%>/resources/images/barra_ver_act.png);
				background-repeat: no-repeat;
				background-position: 28px;
			}
			#iVictimaAccordionPane DT.active {
				BACKGROUND: url(<%=request.getContextPath()%>/resources/images/barra_ver_inact.png);
				background-repeat: no-repeat; 
				COLOR: #f5f5f5; 
				CURSOR: pointer;
				background-position: 30px;
			}
			#iVictimaAccordionPane DT.hover {
				COLOR: #f5f5f5
			}
			#iVictimaAccordionPane DT.hover.active {
				COLOR: #f5f5f5
			}
			#iVictimaAccordionPane DD {
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
			#iVictimaAccordionPane .slide-number {
				COLOR: #68889b; FONT-WEIGHT: bold; LEFT: 30px
			}
			#iVictimaAccordionPane .active .slide-number {
				COLOR: #fff
			}
			#iVictimaAccordionPane A {
				COLOR: #58595b;
				font-family: Arial, Helvetica, sans-serif;
			}
			#iVictimaAccordionPane DD IMG {
				MARGIN: 0px; FLOAT: right
			}
			#iVictimaAccordionPane H2 {
				MARGIN-TOP: 10px; FONT-SIZE: 2.5em
			}
			#iVictimaAccordionPane .more {
				DISPLAY: block; PADDING-TOP: 10px
			}

		</style>

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />

	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.easyAccordion.js"></script>
 	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/mediaFiliacion.js"></script>
	
		
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
	
		var contextoPagina = "${pageContext.request.contextPath}";
		var rolId =  <%=rolId%>;
		var verAlias=0;
		var muerto="";
		
			$(function(){
				$('#tabs').tabs();
				$("#tabs2").tabs();
				$('#dialog_link, ul#icons li').hover(
					function() { $(this).addClass('ui-state-hover'); }, 
					function() { $(this).removeClass('ui-state-hover'); }
				);
			});
		</script>
		
		<script type="text/javascript">
		//var idWindowIngresarTutor = 1;
		var numeroExpediente ="";
		var idindi=0;
		var elemntoNuevo="no";
		var deshabilitarCampos = window.parent.deshabilitarCamposPM;
		var modificaGrid=true;
		var idOrganizacion=0;//variable para guar la organizacion victima
		var isUavd ="";
		var idVictima='<%= request.getParameter("idVictima")%>';
		var idElemento='<%= request.getParameter("idVictima")%>';
 		var idRolActivo = <%=rolId%>;
 		var detalleXMLDeInvolucrado = "";

 		//variable bandera para saber que ya esta finalizada la Audiencia
		var deshabilitarIngVictima='';
 		
 		var esVictimaMuerta;
 		var esCargaVistaMuerto = false;
 		
		
		if (idVictima == "null"){
			idVictima = '<%= request.getAttribute("idVictima")%>';
		}
		
			jQuery().ready(function () {
				
				configuraEventosCombosDeMediaFiliacion();
				configurarPantallaParaUsuarios();

				//Agregado para asociar una victima a una audiencia
				audienciaId = "<%= request.getParameter("audienciaId")%>";
				
				$('#iVictimavCmpV').attr("checked","checked");
				var id=<%= request.getAttribute("idInvolucrado")%>;
						
				
				numeroExpediente='<%= request.getParameter("numeroExpediente")%>';
				isUavd = '<%= request.getParameter("isUavd")%>';
				elemntoNuevo='<%= request.getParameter("elemento")%>';
				$('#iVictimaBtnModificarDatos').hide();	
				var num=parent.num;
				if(num!==null && num!=="0"){
					$("#anularInvolucrado").hide();
				}
				$( "#tabs,#tabstutor" ).tabs();
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
				$('#iVictimaAccordionPane').easyAccordion({ 
					autoStart: false, 
					slideInterval: 3000
				});
				
				ocultaVistaVictimaMuerta();
				
				
				$("#iVictimaCmpMenorEdad").click(formaCapturaMenorEdad);
			    $("#iVictimadCmpD").click(anonimo);
			    $("#iVictimaBtnModificarDatos").click(avilitaDatos); 
			    
			    //mostramos la seccion de persona fisica y ocultamos la de moral
			    $('#iIngresarOrgWorkSheet').hide();
				$('#VictimaWorkSheet').show();
				cargaOrganizacion();
			    
			    //Codigo para obtener los datos de la pantalla
 				$("#iVictimaBtnGuardar").click(guardarVictima);
 				//Codigo para guardar una victima como persona moral
				$("#iIngOrgBtnGuardar").click(guardarOrgDenunciante);
				ocultaDomicilioNotificaciones();
				habilitaDeshabilitaTabAcordeon1("servidorTab",0);
				//codigo para anular 
				$('#anularInvolucrado').hide();
				$("#anularInvolucrado").click(eliminarVictima);
				
				$("#cbxDenuncianteTipoPerosona").change(function(e){
					changeTipoPersonaMoral();
				}); 


				if(idVictima !== "null"){
					consulta(idVictima);
				}
				else{
					inicializaDatosGenerales();
				}

				if(id!==null){
					datosVictima(id);					
				}
				
				//Instruccion pensada solo para el caso de policia ministerial
				if(deshabilitarCampos == true){
					$(":enabled").attr('disabled','disabled');
				}
				$("#trEsVivo,#trEsMuerto,#trEsDesconocido").show();
				//reviso si isUavd existe, ocultaremos algunos campos en la vista
				if(isUavd===null || isUavd==='null')
				{
					$("#trEsVivo,#trEsMuerto,#trEsDesconocido").show();
				}
				else
				{
					$("#trEsVivo,#trEsMuerto,#trEsDesconocido").hide();
				}
				$("#ddDomicilio").width(790);
				$("#ddDatosGenerales").width(790);
				//ocultamos los campos referente a Alias
				$("#trAliasTxt").hide();
				$(".tdAliasCmp").hide();
				liveDomicilioNotificaciones();
				
				//Se valida si la bandera viene true para deshabilitar el boton Modificar Datos
				deshabilitarIngVictima=<%=request.getParameter("deshabilitar")%>;
				if(deshabilitarIngVictima==true){
					$('#iVictimaBtnModificarDatos').hide();
				}
				
				if(muerto==='false'){
			    	  cargaVistaMuerto();
				}
				
			});//FIN funcion onready

			
			/*
			*Funcion que en base al rolActivo del usuario firmado, configura la pantalla
			*para mostrar,ocultar,deshabilitar o habilotar elementos
			*/
			function configurarPantallaParaUsuarios(){

				var esCoordinadorAmpGeneral = <%=esCoordinadorAmpGeneral%>; 
				
				if(idRolActivo === '<%=Roles.PROCURADOR.getValorId()%>' || idRolActivo === '<%=Roles.SUBPROCURADOR.getValorId()%>' ||
						   idRolActivo === '<%=Roles.DIRECTOR_GENERAL.getValorId()%>' || idRolActivo === '<%=Roles.DIRECTOR_UI.getValorId()%>' || 
						   idRolActivo === '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>'){
							
    				$(":enabled").attr('disabled','disabled');
    				$('input[type="submit"]').hide();
    				$('input[type="button"]').hide();
					bloqueaCamposMediosDeContactoGrid();
					habilitaDatosEspecificos();
							
				}
				
				if (rolId === '<%=Roles.DEFENSOR.getValorId()%>' || rolId === '<%=Roles.DEFENSORATE.getValorId()%>' || rolId === '<%=Roles.COORDINADORDEF.getValorId()%>' ){
					$('#iVictimaBtnModificarDatos').hide();
					$('#anularInvolucrado').hide();
					habilitaDatosEspecificos();
				}

				if (rolId === '<%=Roles.ENCARGADOCAUSA.getValorId()%>' || rolId === '<%=Roles.ENCARGADOSALA.getValorId()%>'
						|| rolId === '<%=Roles.JUEZPJ.getValorId()%>'){
					$('#anularInvolucrado').hide();
					$('#cbxDenuncianteTipoPerosona').attr("disabled","disabled");
				}
			}

			
			function consulta(id){
				
				$.ajax({
			    	  type: 'POST',
			    	  url:  '<%= request.getContextPath()%>/consultarInvolucrado.do',
			    	  data: 'idInvolucrado='+id,
			    	  dataType: 'xml',
			    	  async: false,
			    	  success: function(xml){
			    		  var datosXML=xml;
			    		  var tipoPer=$(xml).find('tipoPersona').text();
			    		  detalleXMLDeInvolucrado = xml;
			    		  if(tipoPer=="0"){
			    			  $('#cbxDenuncianteTipoPerosona').find("option[value='"+tipoPer+"']").attr("selected","selected");
			    			  changeTipoPersonaMoral();
			    			  seteaDatosPersonaMoralConsOrg(xml);
			    			  pintaDatosDomicilioOrganizacion(xml);
								if ($(xml).find('representanteLegal').find('elementoId').text()!="") {
									var nomRepLegal = $(xml).find('representanteLegal').find('nombresDemograficoDTO').find('nombre').text();
									var apPatRepLegal = $(xml).find('representanteLegal').find('nombresDemograficoDTO').find('apellidoPaterno').text();
									var apMatRepLegal = $(xml).find('representanteLegal').find('nombresDemograficoDTO').find('apellidoMaterno').text();
									var nomComRepLegal = nomRepLegal+" "+apPatRepLegal+" "+apMatRepLegal;
									cargaRepLegal(nomComRepLegal,$(xml).find('representanteLegal').find('elementoId').text());
								}
								habilitaDeshabilitaCamposIngOrganizacion(0);
					      }else{
					    	  var esVivo=$(datosXML).find('esVivo').text();			    		  
				    		  if(esVivo!=null){
						   	  	if(esVivo=='true'){
						    	  $('#iVictimavCmpV').attr('checked','checked');					    	  					    		
						    	}else{
						    	  $('#iVictimamCmpM').attr('checked','checked');
						    	  muerto=esVivo;
								}
				    		  }
				    		  
				    		  var esDesconocido=$(datosXML).find('desconocido').text();			    		  
				    		  if(esDesconocido!=null){
						   	  	if(esDesconocido=='true'){
						    	  $('#iVictimaCmpD').attr('checked','checked');	
						    	  anonimo();
						    	}else{
						    	  $('#iVictimaCmpD').removeAttr('disabled');
						    	  anonimo();
								}
				    		  }
				    		  
				    		  if(esVivo!=null){
							   	  	if(esVivo=='false'){
							   	  		pintaDatosDefuncion(datosXML);
							   	  		esVictimaMuerta = true;
							   	  	}
				    		  }
				    		  pintaDatosGenerales(datosXML);
				    		  pintaDatosMultiselect(xml);
				    		  pintaDatosDomicilio(datosXML);
				    		  if($(datosXML).find('involucradoDTO').find('esMismoDomicilio').text() == "false"){
						    	  pintaDatosDomicilioNotif(datosXML);
		    				  }
				    		  pintaDatosMediaFiliacion(datosXML);
				    		  pintaDatosTipoDocIdentificacion(datosXML);
					      }
					  }
			    });
				idindi=id;		
				configuraPantallaModoConsulta();
			}

			/*
			*Configura la pantalla en modo de consulta y por usuario
			*/
			function configuraPantallaModoConsulta(){

				desavilitarDatosGenerales();
				deshabilitaDatosDomicilio();
				deshabilitaDatosDefuncion();
				deshabilitaDatosMediaFiliacion();
				desavilitarDatosIdentificacion();
				bloqueaCamposMediosDeContactoGrid();
				mediosContactoCorreoActualiza();
				mediosContactoTelefonoActualiza();
				
				$('#iVictimaBtnModificarDatos').show();
				$('#iVictimaBtnGuardar').hide();
				$("#iVictimavCmpV,#iVictimamCmpM,#iVictimaCmpD,").attr('disabled', 'disabled');
				//deshabilita el tipo de persona en la consulta
				$("#cbxDenuncianteTipoPerosona").attr('disabled', 'disabled');
				//mostramos el boton para anular al involuvrado
				$("img.ui-datepicker-trigger").hide();
				if (rolId == '<%=Roles.ENCARGADOCAUSA.getValorId()%>' || rolId == '<%=Roles.ENCARGADOSALA.getValorId()%>'
					|| rolId == '<%=Roles.JUEZPJ.getValorId()%>'){
					$('#anularInvolucrado').hide();
					$('#cbxDenuncianteTipoPerosona').attr("disabled","disabled");
				}else{
					$('#anularInvolucrado').show();
				}				
			}

			function avilitaDatos(){
				$("img.ui-datepicker-trigger").show();
				avilitarDatosGenerales();
				avilitarDatosDomicilio();
				habilitaDatosDefuncion();
				habilitaDatosMediaFiliacion();
				avilitarDatosIdentificacion();
				desbloqueaCamposMediosDeContactoGrid();
				$('#iVictimaBtnGuardar').show();
				$('#iVictimaBtnModificarDatos').hide();
				if (rolId != '<%=Roles.ENCARGADOCAUSA.getValorId()%>' && rolId != '<%=Roles.ENCARGADOSALA.getValorId()%>'
					&& rolId != '<%=Roles.JUEZPJ.getValorId()%>'){
					$('#cbxDenuncianteTipoPerosona').attr("disabled","");
					$('#anularInvolucrado').show();
				}
				modificaGrid=true;
			}
			
			function desavilitaDatos(){
				desavilitarDatosGenerales();
				deshabilitaDatosDomicilio();
				deshabilitaDatosDefuncion();
				deshabilitaDatosMediaFiliacion();
				desavilitarDatosIdentificacion();
				bloqueaCamposMediosDeContactoGrid();
				mediosContactoCorreoActualiza();
				mediosContactoTelefonoActualiza();
				$('#iVictimaBtnModificarDatos').show();
				$('#anularInvolucrado').hide();
				$('#iVictimaBtnGuardar').hide();
				$("#iVictimavCmpV,#iVictimamCmpM,#iVictimaCmpD,").attr('disabled', 'disabled');
				//deshabilita el tipo de persona en la consulta
				$("#cbxDenuncianteTipoPerosona").attr('disabled', 'disabled');
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
			
			function asignaclass(){
				 
				   $('#datosGeneralesCmpNombres').addClass('required');
				   $('#datosGeneralesCmpApaterno').addClass('required');
				   $('#datosGeneralesCmpOcupacion').addClass('required');
				   $('#datosGeneralesCmpMaterno').addClass('required');
				   $('#datosGeneralesCmpCurp').addClass('required');
				   $('#datosGeneralesCmpRfc').addClass('required'); 
				   $('#datosGeneralesCmpEstadoCivil').addClass('required');
				   $('#datosGeneralesCmpNombres').addClass('required');
				   
			   }
			
			
				function formaCapturaMenorEdad() {
					if ($("#iVictimaCmpMenorEdad").is(':checked')) {
						creaNuevoTutor();
					}				
				}
				
				/*
				 *Imprime los datos que vienen de la funcion espejoDatos de datos generales, 
				 *en la pantalla ingresar probable responsable
				 */
				function imprimeDatosPadre(nombre, apPat, apMat){
					document.getElementById('iVictimaCmpNombre').innerHTML =nombre;
					document.getElementById('iVictimaCmpApellidoPaterno').innerHTML =apPat;
					document.getElementById('iVictimaCmpApellidoMaterno').innerHTML =apMat;
				}
				/*
				* Funcion para deshabilitar el tab de un acordeon, se pasa el id del elemento DT
				* y un 0 para deshabilitar o un 1 para habilitar
				*/
				function habilitaDeshabilitaTabAcordeon(idTabAcordeon,idTabAcordeonActive,bandera)
				{
					
					if(parseInt(bandera)==0)//Deshabilita el tab del acordeon
					{
						$("#"+idTabAcordeon).unbind('click');
						$("#"+idTabAcordeonActive).click();
						if($("#"+idTabAcordeon).hasClass('active'))
						{
							$("#"+idTabAcordeon).removeClass('active').addClass('inactive');
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

				function habilitaRadioBotones(){
					$('#iVictimavCmpV').removeAttr('disabled');
					$('#iVictimamCmpM').removeAttr('disabled');
					$('#iVictimaCmpD').removeAttr('disabled');
				}
				
				function reglaNombre(){
					if($('#iVictimaCmpD').is(':checked')==false){
						if($('#datosGeneralesCmpNombres').val()=="Desconocido" && $('#datosGeneralesCmpApaterno').val()=="" && $('#datosGeneralesCmpMaterno').val()=="") {
							$('#datosGeneralesCmpNombres').val("");							
							$('#datosGeneralesCmpApaterno').val("");
							$('#datosGeneralesCmpMaterno').val("");
							$('#iVictimaCmpNombre').val("");
							$('#iVictimaCmpApaterno').val("");
							$('#iVictimaCmpMaterno').val("");
						}												
					}
					else{
						$('#datosGeneralesCmpNombres').val("Desconocido");							
						$('#datosGeneralesCmpApaterno').val("");
						$('#datosGeneralesCmpMaterno').val("");
						$('#iVictimaCmpNombre').val("Desconocido");
						$('#iVictimaCmpApaterno').val("");
						$('#iVictimaCmpMaterno').val("");
					}
					espejoDatos();
				}
				
				function anonimo(){
					
					if ($("#iVictimaCmpD").is(':checked')) {
						
						habilitaDeshabilitaTabAcordeon("datosGeneralesTab","cejaMediaFiliacion",0);
						habilitaDeshabilitaTabAcordeon("domicilioTab","cejaMediaFiliacion",0);
						habilitaDeshabilitaTabAcordeon("mContactoTab","documentoTab",0);
						habilitaDeshabilitaTabAcordeon("documentoTab","cejaMediaFiliacion",0);
						habilitaDeshabilitaTabAcordeon("servidorTab","cejaMediaFiliacion",0);
					}else{
						if($("#iVictimamCmpM").is(':checked')){
							cargaVistaMuerto();
						}else{
							cargaVistaVivo();
							habilitaDeshabilitaTabAcordeon("datosGeneralesTab","cejaMediaFiliacion",1);
							habilitaDeshabilitaTabAcordeon("domicilioTab","cejaMediaFiliacion",1);
							habilitaDeshabilitaTabAcordeon("mContactoTab","cejaMediaFiliacion",1);
							habilitaDeshabilitaTabAcordeon("documentoTab","cejaMediaFiliacion",1);
							habilitaDeshabilitaTabAcordeon("servidorTab","cejaMediaFiliacion",1);
						}
				
					}

				}
				   
				function datosVictima(id) {
				      $.ajax({
					      type: 'POST',
				    	  url: '<%= request.getContextPath()%>/ConsultarIndividuoDatos.do',
				    	  data: 'idInvolucrado='+id,
				    	  dataType: 'xml',
				    	  async: false,
				    	  success: function(xml){
					    	
					    	 $('#iVictimaCmpCalidad').val($(xml).find('calidadDTO').find('descripcionEstadoFisico').text());
					    	 if ($(xml).find('esVivo').text() == "1"){
					    		 $('#iVictimavCmpV').attr('checked','checked');
					    		
					    	 }else{
					    		 $('#iVictimamCmpM').attr('checked','checked');
					    		 cargaVistaMuerto();
					    	 }
					    	  pintaDatosGenerales(xml);
				    		  pintaDatosDomicilio(xml);
				    		  pintaDatosMediaFiliacion(xml);
					    	  pintaDatosTipoDocIdentificacion(xml);
					    	  espejoDatos();
					    	 
				    	  }
				    	});
				}	

				function guardarVictima(){
					
					var esVivo;
					var banderaContradiccion=1;
					var datosPestania;
					var params = '';
					
					if($('#iVictimavCmpV').is(':checked')==true)
						esVivo=true;
					else 
						esVivo=false;										
					
					var validaRFC_CURP;					
					validaRFC_CURP=camposGeneralesValidos();
					if(validaRFC_CURP==1){
						
						var nombreGeneralOP= trim($('#datosGeneralesCmpNombres').val());
						var desconocidoOp=$('#iVictimaCmpD').is(':checked');
						
						if(nombreGeneralOP=="Desconocido" && desconocidoOp==true && $('#iVictimaCmpApellidoPaterno').val()!="" && $('#iVictimaCmpApellidoMaterno').val()!=""){
							banderaContradiccion=0;
							alertDinamico("Favor de ingresar el nombre del desconocido &oacute; ingresar los apellidos");									
						}							
						
						if(nombreGeneralOP!==""){							
							params += 'idIndividuo='+idindi;
							params += '&calidadDelIndividuo=2';
							params += '&esVivo='+esVivo;
							params += '&numeroExpediente='+numeroExpediente;
							params +='&idCondicion=' + $(':radio[name=iVictimaCmp]:checked').val();
							params += '&esPersonaMoral=' + $('#personaMoral').is(':checked');
							params +='&esDesconocido=' + $('#iVictimaCmpD').is(':checked');
							params +='&esMenorDeEdad=' + $('#iVictimaCmpMenorEdad').is(':checked');
							
							//Datos generales, media filiacion, medios de contacto, documentos de identificacion
							datosPestania = obtenerParametrosDatosGenerales();//Frame de datos generales
							params += datosPestania;
		
							//Datos nacimiento
							datosPestania = obtenerParametrosDatosNacimiento();
							params += datosPestania;					
							
							//Domicilio
							datosPestania = obtenerParametrosDomicilio();
							params += datosPestania;

							//Documento de identificacion
							datosPestania = '&';
							datosPestania += recuperaDatosTipoDocIdentificacion();					
							params += datosPestania;
		
							//Media Filiacion
							datosPestania = obtenerParametrosMediaFiliacion();
							params += datosPestania;
							
							//Medios de contacto funcion Anterior obtenerMediosanterior() 
							datosPestania = obtenerMedios();
							params += datosPestania;
							
							//Datos defuncion
							datosPestania = obtenerParametrosDefuncion();
							params += datosPestania;

							$.ajax({								
						    	  type: 'POST',
						    	  url: '<%= request.getContextPath()%>/guardarIndividuo.do',
						    	  data: params,				
						    	  dataType: 'xml',
						    	  async: false,
						    	  success: function(xml){
						    		  if(elemntoNuevo=="si"){

						    			//Agregado para asociar la victima a la audiencia
							    		  if(audienciaId != null && audienciaId != ""){
							    			  idindi=$(xml).find('IngresarIndividuoForm').find('idIndividuo').text();
							    			  asociarIndividuoAAudiencia(idindi,audienciaId);
								    	  }
						    			  window.parent.refresca();
								      }else{
						    		  	window.parent.cargaVictima($(xml).find('IngresarIndividuoForm').find('nombre').text(),$(xml).find('IngresarIndividuoForm').find('idIndividuo').text());
								      }
						    		  idindi=$(xml).find('IngresarIndividuoForm').find('idIndividuo').text();
						    		  alertDinamico('V&iacute;ctima Guardada');
						    		  desavilitaDatos();
						    	  }
						    	});	
						}else{
							alertDinamico('Favor de capturar el nombre del involucrado');
						}
					}
					else if(validaRFC_CURP==0){
						alertDinamico("Favor de verificar que el CURP ingresado sea correcto");
					}
					else{
						alertDinamico("Favor de verificar que el RFC ingresado sea correcto");
					}
				}
				
				function eliminarVictima(){					
					guardarVictimaEliminar();
				}
				
				function guardarVictimaEliminar(){
					if(idindi!='null' && idindi!=0)
					{
						//debemos mostrar un confirm
						customConfirm ("&iquest;Est&aacute; seguro que desea anular la v&iacute;ctima?", "", anularInvolucrado);
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
										window.parent.customAlert("S&eacute; logr&oacute; anular la v&iacute;ctima con &eacute;xito");
										cerrarCustomVentana();
										//window.parent.cerrarVentanaVictima();
									}
									else if(parseInt($(xml).find('banderaOp').text())==0)
									{
										//se puede eliminar el objeto sin problemas
										window.parent.customAlert("No se logr&oacute; anular a la v&iacute;ctima");
									}
									else if(parseInt($(xml).find('banderaOp').text())==-1)
									{
										window.parent.customAlert("Ocurri&oacute;n un error al tratar de anular a la v&iacute;ctima,<br/>consulte a su administrador ");
									}
					    	  }
					    });
				}
				
				
				 function obtenerParametrosMediaFiliacion()
				   {
					 <%
						
						if(confInstitucionDTO != null && confInstitucionDTO.getConfInstitucionId()!= null) {
							if (confInstitucionDTO.getConfInstitucionId() == Instituciones.SSP.getValorId()) {
				
					%>
							return ""; // si es SSP no regresa Media Afiliacion
					<%
							}
						}
					%> 
				        var parametros = '&tamanoBoca=' + $('#cmpTamanoBoca option:selected').val();
				        parametros += '&tipoCara=' + $('#cmpTipoCara option:selected').val();
				        parametros += '&formaMenton=' + $('#cmpFormaMenton option:selected').val();
				        parametros += '&tipoMenton=' + $('#cmpTipoMenton option:selected').val();
				        parametros += '&tez=' + $('#cmpTez option:selected').val();
				        parametros += '&inclinacionMenton=' + $('#cmpInclinacionMenton option:selected').val();

				        parametros += '&colorCabello=' + $('#cmpColorCabello option:selected').val();
				        parametros += '&formaCabello=' + $('#cmpFormaCabello option:selected').val();
				        parametros += '&calvieTipo=' + $('#cmpCalvieTipo option:selected').val();
				        parametros += '&cabelloImplantacion=' + $('#cmpCabelloImplantacion option:selected').val();
				        parametros += '&cantidadCabello=' + $('#cmpCantidadCabello option:selected').val();

				        parametros += '&tamanoOreja=' + $('#cmpTamanoOreja option:selected').val();
				        parametros += '&lobuloParticularidad=' + $('#cmpLobuloParticularidad option:selected').val();
				        parametros += '&lobuloDimension=' + $('#cmpLobuloDimension option:selected').val();
				        parametros += '&lobuloAdherencia=' + $('#cmpLobuloAdherencia option:selected').val();
				        parametros += '&helixAnterior=' + $('#cmpHelixAnterior option:selected').val();
				        parametros += '&helixPosterior=' + $('#cmpHelixPosterior option:selected').val();
				        parametros += '&helixContorno=' + $('#cmpHelixContorno option:selected').val();
				        parametros += '&helixAdherencia=' + $('#cmpHelixAdherencia option:selected').val();
				        parametros += '&formaOreja=' + $('#cmpFormaOreja option:selected').val();

				        parametros += '&tamanoOjos=' + $('#cmpTamanoOjos option:selected').val();
				        parametros += '&colorOjos=' + $('#cmpColorOjos option:selected').val();
				        parametros += '&formaOjos=' + $('#cmpFormaOjos option:selected').val();

				        parametros += '&implantacionCejas=' + $('#cmpImplantacionCejas option:selected').val();
				        parametros += '&formaCejas=' + $('#cmpFormaCejas option:selected').val();
				        parametros += '&tamanoCejas=' + $('#cmpTamanoCejas option:selected').val();

						if($("#Cicatrices1").is(':checked')){
							 parametros += '&cicatricesSenas=' + "1";
						}else{
							 parametros += '&cicatricesSenas=' + "0";
						}
						 parametros += '&cicatricesSenasText=' +$('#Cicatricestext').val();
						 if($("#Protesis1").is(':checked')){
							 parametros += '&protesisSenas=' + "1";
						}else{
							 parametros += '&protesisSenas=' + "0";
						}
						 parametros += '&protesisSenasText=' +$('#Protesistext').val();

						 if($("#Tatuajes1").is(':checked')){
							 parametros += '&tatuajeSenas=' + "1";
						}else{
							 parametros += '&tatuajeSenas=' + "0";
						}
						 parametros += '&tatuajeSenasText=' +$('#Tatuajestext').val();
						 parametros += '&otraSenasText=' +$('#Otrastext').val();
						 if($("#Lunares1").is(':checked')){
							 parametros += '&lunaresSenas=' + "1";
						}else{
							 parametros += '&lunaresSenas=' + "0";
						}
						 parametros += '&lunaresSenasText=' +$('#Lunarestext').val();
						 if($("#Defectos1").is(':checked')){
							 parametros += '&defectosSenas=' + "1";
						}else{
							 parametros += '&defectosSenas=' + "0";
						}
						 parametros += '&defectosSenasText=' +$('#Defectostext').val();
						 
						 
						 parametros += '&factorrhOtros=' + $('#cmpFactorRH option:selected').val();
					    
							if($("#lentes1").is(':checked')){
								 parametros += '&lentesOtros=' + "1";
							}else{
								 parametros += '&lentesOtros=' + "0";
							}
							parametros += '&tipoSangreOtros=' + $('#cmpTipoSangre option:selected').val();
								parametros += '&complexion=' + $('#cmpComplexion option:selected').val();
								  
								parametros += '&direccionCeja=' + $('#cmpDireccionCejas option:selected').val();

								parametros += '&helixOriginal=' + $('#cmpHelixOriginal option:selected').val();

								parametros += '&orejaLobContorno=' + $('#cmplobuloContorno option:selected').val();
								
								
								
							 if($("#barba1").is(':checked')){
								 parametros += '&barbaOtros=' + "1";
							}else{
								 parametros += '&barbaOtros=' + "0";
							}
							 parametros += '&pesoOtros=' +$('#peso').val();

							 if($("#bigote1").is(':checked')){
								 parametros += '&bigoteOtros=' + "1";
							}else{
								 parametros += '&bigoteOtros=' + "0";
							}
							 parametros += '&estauraOtros=' +$('#estatura').val();						
				       


				        

                                        //Jacob
                                        parametros += '&alturaNasoLabial=' + $('#altura_nasal option:selected').val();
                                        parametros += '&comisuras=' + $('#comisuras option:selected').val();
                                        parametros += '&espesorLabioInferior=' + $('#espesor_labio_inf option:selected').val();
                                        parametros += '&espesorLabioSuperior=' + $('#espesor_labio_sup option:selected').val();
                                        parametros += '&prominencia=' + $('#prominencia option:selected').val();
                                        parametros += '&anchoNariz=' + $('#ancho_nariz option:selected').val();
                                        parametros += '&alturaNariz=' + $('#altura_nariz option:selected').val();
                                        parametros += '&baseNariz=' + $('#base_nariz option:selected').val();
                                        parametros += '&raizNariz=' + $('#raiz_nariz option:selected').val();
                                        parametros += '&frenteAltura=' + $('#frente_altura option:selected').val();
                                        parametros += '&frenteAncho=' + $('#frente_ancho option:selected').val();
                                        parametros += '&inclinacionFrente=' + $('#inclinacion_frente option:selected').val();
                                        // Fin Jacob
				        
				        return parametros;
				   }

				    function pintaDatosMediaFiliacion(xml){
						 //Media Filiacion Cara
						 var tamanoBoca=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('tamanioBoca').find('idCampo').text();
						 $('#cmpTamanoBoca').find("option[value='"+tamanoBoca+"']").attr("selected","selected");
						 var tipoCara=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('tipoCara').find('idCampo').text();
						 $('#cmpTipoCara').find("option[value='"+tipoCara+"']").attr("selected","selected");
						 var formaMenton=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('formaMenton').find('idCampo').text();
						 $('#cmpFormaMenton').find("option[value='"+formaMenton+"']").attr("selected","selected");
						 var tipoMenton=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('tipoMenton').find('idCampo').text();
						 $('#cmpTipoMenton').find("option[value='"+tipoMenton+"']").attr("selected","selected");
						 var tez=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('tez').find('idCampo').text();
						 $('#cmpTez').find("option[value='"+tez+"']").attr("selected","selected");
						 var inclinacionMenton=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('inclinacionMenton').find('idCampo').text();
						 $('#cmpInclinacionMenton').find("option[value='"+inclinacionMenton+"']").attr("selected","selected");

						 //Media Filiacion Cabello
						 var colorCabello=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('colorCabello').find('idCampo').text();
						 $('#cmpColorCabello').find("option[value='"+colorCabello+"']").attr("selected","selected");
						 var formaCabello=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('formaCabello').find('idCampo').text();
						 $('#cmpFormaCabello').find("option[value='"+formaCabello+"']").attr("selected","selected");
						 var calvieTipo=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('calvicieTipo').find('idCampo').text();
						 $('#cmpCalvieTipo').find("option[value='"+calvieTipo+"']").attr("selected","selected");
						 var cabelloImplantacion=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('cabelloImplantacion').find('idCampo').text();
						 $('#cmpCabelloImplantacion').find("option[value='"+cabelloImplantacion+"']").attr("selected","selected");
						 var cantidadCabello=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('cabelloCantidad').find('idCampo').text();
						 $('#cmpCantidadCabello').find("option[value='"+cantidadCabello+"']").attr("selected","selected");
						 
						 //Media Filiacion Oreja
						 var tamanoOreja=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('orejaTamanio').find('idCampo').text();
						 $('#cmpTamanoOreja').find("option[value='"+tamanoOreja+"']").attr("selected","selected");
						 var lobuloParticularidad=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('orejaLobParticularidad').find('idCampo').text();
						 $('#cmpLobuloParticularidad').find("option[value='"+lobuloParticularidad+"']").attr("selected","selected");
						 var lobuloDimension=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('orejaLobDimension').find('idCampo').text();
						 $('#cmpLobuloDimension').find("option[value='"+lobuloDimension+"']").attr("selected","selected");
						 var lobuloAdherencia=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('orejaLobAdherencia').find('idCampo').text();
						 $('#cmpLobuloAdherencia').find("option[value='"+lobuloAdherencia+"']").attr("selected","selected");
						 var helixAnterior=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('helixSuperior').find('idCampo').text();
						 $('#cmpHelixAnterior').find("option[value='"+helixAnterior+"']").attr("selected","selected");
						 var helixPosterior=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('helixPosterior').find('idCampo').text();
						 $('#cmpHelixPosterior').find("option[value='"+helixPosterior+"']").attr("selected","selected");
						 var helixContorno=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('helixContorno').find('idCampo').text();
						 $('#cmpHelixContorno').find("option[value='"+helixContorno+"']").attr("selected","selected");
						 var helixAdherencia=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('helixAdherencia').find('idCampo').text();
						 $('#cmpHelixAdherencia').find("option[value='"+helixAdherencia+"']").attr("selected","selected");
						 var formaOreja=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('formaOreja').find('idCampo').text();
						 $('#cmpFormaOreja').find("option[value='"+formaOreja+"']").attr("selected","selected");
						 
						 //Media Filiacion Ojos
						 var tamanoOjos=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('tamanioOjo').find('idCampo').text();
						 $('#cmpTamanoOjos').find("option[value='"+tamanoOjos+"']").attr("selected","selected");
						 var colorOjos=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('colorOjos').find('idCampo').text();
						 $('#cmpColorOjos').find("option[value='"+colorOjos+"']").attr("selected","selected");
						 var formaOjos=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('formaOjos').find('idCampo').text();
						 $('#cmpFormaOjos').find("option[value='"+formaOjos+"']").attr("selected","selected");

						 //Media filiacion Labios
						 var labioNasal=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('alturaNasoLabial').find('idCampo').text();
						 $('#altura_nasal').find("option[value='"+labioNasal+"']").attr("selected","selected");
						 var comisurasLabios=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('labioComisuras').find('idCampo').text();
						 $('#comisuras').find("option[value='"+comisurasLabios+"']").attr("selected","selected");
						 var prominenciaLabios=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('labiosProminencia').find('idCampo').text();
						 $('#prominencia').find("option[value='"+prominenciaLabios+"']").attr("selected","selected");
						 var espesorLabiosSup=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('espesorLabioSup').find('idCampo').text();
						 $('#espesor_labio_sup').find("option[value='"+espesorLabiosSup+"']").attr("selected","selected");
						 var espesorLabiosInfe=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('espesorLabioInf').find('idCampo').text();
						 $('#espesor_labio_inf').find("option[value='"+espesorLabiosInfe+"']").attr("selected","selected");

						 var anchonariz=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('anchoNariz').find('idCampo').text();
						 $('#ancho_nariz').find("option[value='"+anchonariz+"']").attr("selected","selected");
						 var alturaNariz=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('alturaNariz').find('idCampo').text();
						 $('#altura_nariz').find("option[value='"+alturaNariz+"']").attr("selected","selected");
						 var raiznariz=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('raizNariz').find('idCampo').text();
						 $('#raiz_nariz').find("option[value='"+raiznariz+"']").attr("selected","selected");
						 var baseNariz=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('baseNariz').find('idCampo').text();
						 $('#base_nariz').find("option[value='"+baseNariz+"']").attr("selected","selected");
						 
						 var alturaFrente=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('frenteAltura').find('idCampo').text();
						 $('#frente_altura').find("option[value='"+alturaFrente+"']").attr("selected","selected");
						 var anchoFrente=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('frenteAncho').find('idCampo').text();
						 $('#frente_ancho').find("option[value='"+anchoFrente+"']").attr("selected","selected");
						 var inclinacionFrente=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('frenteInclinacion').find('idCampo').text();
						 $('#inclinacion_frente').find("option[value='"+inclinacionFrente+"']").attr("selected","selected");
						var implantacionCejas=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('implantacionCeja').find('idCampo').text();
						 $('#cmpImplantacionCejas').find("option[value='"+implantacionCejas+"']").attr("selected","selected");
						 var formaCejas=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('formaCeja').find('idCampo').text();
						 $('#cmpFormaCejas').find("option[value='"+formaCejas+"']").attr("selected","selected");
						 var tamanoCejas=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('tamanioCeja').find('idCampo').text();
						 $('#cmpTamanoCejas').find("option[value='"+tamanoCejas+"']").attr("selected","selected");
						 var cicatricesParti=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('seniaParticularDTO').find('tieneCicatrices').text();
                        if(cicatricesParti=="true"){
                        	$('#Cicatrices1').attr("checked","checked");
                        }else{
                        	$('#Cicatrices2').attr("checked","checked");
                        }
                        var cicatricesPartiText=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('seniaParticularDTO').find('descripcionCicatrices').text();
                        $('#Cicatricestext').val(cicatricesPartiText);
                        var tatuParti=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('seniaParticularDTO').find('tieneTatuajes').text();
                        if(tatuParti=="true"){
                        	$('#Tatuajes1').attr("checked","checked");
                        }else{
                        	$('#Tatuajes2').attr("checked","checked");
                        }
                        var tatuPartiText=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('seniaParticularDTO').find('descripcionTatuajes').text();
                        $('#Tatuajestext').val(tatuPartiText);
                        var lunaresParti=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('seniaParticularDTO').find('tieneLunares').text();
                        if(lunaresParti=="true"){
                        	$('#Lunares1').attr("checked","checked");
                        }else{
                        	$('#Lunares2').attr("checked","checked");
                        }
                        var lunaresPartiText=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('seniaParticularDTO').find('descripcionLunares').text();
                        $('#Lunarestext').val(lunaresPartiText);
                        var defectosParti=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('seniaParticularDTO').find('tieneDefectosFisicos').text();
                        if(defectosParti=="true"){
                        	$('#Defectos1').attr("checked","checked");
                        }else{
                        	$('#Defectos2').attr("checked","checked");
                        }	
                        var defectosPartiText=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('seniaParticularDTO').find('descripcionDefectosFisicos').text();
                        $('#Defectostext').val(defectosPartiText);
                        var protesisParti=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('seniaParticularDTO').find('tieneProtesis').text();
                        if(protesisParti=="true"){
                        	$('#Protesis1').attr("checked","checked");
                        }else{
                        	$('#Protesis2').attr("checked","checked");
                        }
                        var protesisPartiText=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('seniaParticularDTO').find('descripcionProtesis').text();
                        $('#Protesistext').val(protesisPartiText);
                        var otraPartiText=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('seniaParticularDTO').find('descripcionOtraSenia').text();
                        $('#Otrastext').val(otraPartiText);

                        var factorRH=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('factorRH').text();
                        if(factorRH=="Negativo"){
        					$('#cmpFactorRH').find("option[value=0]").attr("selected","selected");
        				}else if(factorRH==""){
        					$('#cmpFactorRH').find("option[value=-1]").attr("selected","selected");
        				}
        				else{        					
        					$('#cmpFactorRH').find("option[value=1]").attr("selected","selected");
        				}
        				var lentes=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('usaLentes').text();
                        if(lentes=="true"){
                        	$('#lentes1').attr("checked","checked");
                        }else{
                        	$('#lentes2').attr("checked","checked");
                        }
                        var tipoSangre=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('tipoSangre').find('idCampo').text();
						 $('#cmpTipoSangre').find("option[value='"+tipoSangre+"']").attr("selected","selected");

						
						 var complexion=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('complexion').find('idCampo').text();
						 $('#cmpComplexion').find("option[value='"+complexion+"']").attr("selected","selected");

						
						 var lobuloContorno=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('orejaLobContorno').find('idCampo').text();
						 $('#cmplobuloContorno').find("option[value='"+lobuloContorno+"']").attr("selected","selected");

						 var helixOriginal=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('helixOriginal').find('idCampo').text();
						 $('#cmpHelixOriginal').find("option[value='"+helixOriginal+"']").attr("selected","selected");

						 var direccionCejas=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('direccionCeja').find('idCampo').text();
						 $('#cmpDireccionCejas').find("option[value='"+direccionCejas+"']").attr("selected","selected");
						 
						 var barba=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('tieneBarba').text();
	                        if(barba=="true"){
	                        	$('#barba1').attr("checked","checked");
	                        }else{
	                        	$('#barba2').attr("checked","checked");
	                        }
	                        var peso=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('peso').text();
	                        $('#peso').val(peso);
	                        var estatura=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('estatura').text();
	                        $('#estatura').val(estatura);
	                        var bigote=$(xml).find('involucradoDTO').find('mediaFiliacionDTO').find('tieneBigote').text();
	                        if(bigote=="true"){
	                        	$('#bigote1').attr("checked","checked");
	                        }else{
	                        	$('#bigote2').attr("checked","checked");
	                        }
					}

					function deshabilitaDatosMediaFiliacion(){
						 $('#cmpTamanoBoca').attr("disabled","disabled");
						 $('#cmpTipoCara').attr("disabled","disabled");
						 $('#cmpFormaMenton').attr("disabled","disabled");
						 $('#cmpTipoMenton').attr("disabled","disabled");
						 $('#cmpTez').attr("disabled","disabled");
						 $('#cmpInclinacionMenton').attr("disabled","disabled");

						 $('#cmpColorCabello').attr("disabled","disabled");
						 $('#cmpFormaCabello').attr("disabled","disabled");
						 $('#cmpCalvieTipo').attr("disabled","disabled");
						 $('#cmpCabelloImplantacion').attr("disabled","disabled");
						 $('#cmpCantidadCabello').attr("disabled","disabled");
						 
						 $('#cmpTamanoOreja').attr("disabled","disabled");
						 $('#cmpLobuloParticularidad').attr("disabled","disabled");
						 $('#cmpLobuloDimension').attr("disabled","disabled");
						 $('#cmpLobuloAdherencia').attr("disabled","disabled");
						 $('#cmpHelixAnterior').attr("disabled","disabled");
						 $('#cmpHelixPosterior').attr("disabled","disabled");
						 $('#cmpHelixContorno').attr("disabled","disabled");
						 $('#cmpHelixAdherencia').attr("disabled","disabled");
						 $('#cmpFormcmpFormaOrejaaMenton').attr("disabled","disabled");
						 $('#cmpFormaOreja').attr("disabled","disabled");
						 
						 $('#cmpTamanoOjos').attr("disabled","disabled");
						 $('#cmpColorOjos').attr("disabled","disabled");
						 $('#cmpFormaOjos').attr("disabled","disabled");

						 $('#altura_nasal').attr("disabled","disabled");
						 $('#comisuras').attr("disabled","disabled");
						 $('#prominencia').attr("disabled","disabled");
						 $('#espesor_labio_sup').attr("disabled","disabled");
						 $('#espesor_labio_inf').attr("disabled","disabled");
						 $('#ancho_nariz').attr("disabled","disabled");
						 $('#altura_nariz').attr("disabled","disabled");
						 $('#raiz_nariz').attr("disabled","disabled");
						 $('#base_nariz').attr("disabled","disabled");
						 $('#frente_altura').attr("disabled","disabled");
						 $('#frente_ancho').attr("disabled","disabled");
						 $('#inclinacion_frente').attr("disabled","disabled");
						 $('#cmpImplantacionCejas').attr("disabled","disabled");
						 $('#cmpFormaCejas').attr("disabled","disabled");
						 $('#cmpTamanoCejas').attr("disabled","disabled");

						 $('#Cicatrices1').attr("disabled","disabled");
						 $('#Cicatrices2').attr("disabled","disabled");
						 $('#Cicatricestext').attr("disabled","disabled");
						 $('#Tatuajes1').attr("disabled","disabled");
						 $('#Tatuajes2').attr("disabled","disabled");
						 $('#Tatuajestext').attr("disabled","disabled");
						 $('#Lunares1').attr("disabled","disabled");
						 $('#Lunares2').attr("disabled","disabled");
						 $('#Lunarestext').attr("disabled","disabled");
						 $('#Defectos1').attr("disabled","disabled");
						 $('#Defectos2').attr("disabled","disabled");
						 $('#Defectostext').attr("disabled","disabled");
						 $('#Protesis1').attr("disabled","disabled");
						 $('#Protesis2').attr("disabled","disabled");
						 $('#Protesistext').attr("disabled","disabled");
						 $('#Otrastext').attr("disabled","disabled");
						 $('#cmpFactorRH').attr("disabled","disabled");
						 $('#lentes1').attr("disabled","disabled");
						 $('#lentes2').attr("disabled","disabled");
						 $('#cmpTipoSangre').attr("disabled","disabled");
						 $('#barba1').attr("disabled","disabled");
						 $('#barba2').attr("disabled","disabled");
						 $('#peso').attr("disabled","disabled");
						 $('#estatura').attr("disabled","disabled");
						 $('#bigote1').attr("disabled","disabled");
						 $('#bigote2').attr("disabled","disabled");
						    
						 $('#cmpComplexion').attr("disabled","disabled");
						  $('#cmplobuloContorno').attr("disabled","disabled");
						  $('#cmpHelixOriginal').attr("disabled","disabled");
						  $('#cmpDireccionCejas').attr("disabled","disabled");
						  $('#cmpFormaOreja').attr("disabled","disabled");
					}

					function habilitaDatosMediaFiliacion(){
//						var $widget = $("select").multiselect();
//						var	state = true; 
//					   state = !state; 
//					   $widget.multiselect(state ? 'disable' : 'enable'); 
						
						 $('#cmpTamanoBoca').attr("disabled","");
						 $('#cmpTipoCara').attr("disabled","");
						 $('#cmpFormaMenton').attr("disabled","");
						 $('#cmpTipoMenton').attr("disabled","");
						 $('#cmpTez').attr("disabled","");
						 $('#cmpInclinacionMenton').attr("disabled","");

						 $('#cmpColorCabello').attr("disabled","");
						 $('#cmpFormaCabello').attr("disabled","");
						 $('#cmpCalvieTipo').attr("disabled","");
						 $('#cmpCabelloImplantacion').attr("disabled","");
						 $('#cmpCantidadCabello').attr("disabled","");
						 
						 $('#cmpTamanoOreja').attr("disabled","");
						 $('#cmpLobuloParticularidad').attr("disabled","");
						 $('#cmpLobuloDimension').attr("disabled","");
						 $('#cmpLobuloAdherencia').attr("disabled","");
						 $('#cmpHelixAnterior').attr("disabled","");
						 $('#cmpHelixPosterior').attr("disabled","");
						 $('#cmpHelixContorno').attr("disabled","");
						 $('#cmpHelixAdherencia').attr("disabled","");
						 $('#cmpFormcmpFormaOrejaaMenton').attr("disabled","");
						 $('#cmpFormaOreja').attr("disabled","");
						 

						 $('#cmpTamanoOjos').attr("disabled","");
						 $('#cmpColorOjos').attr("disabled","");
						 $('#cmpFormaOjos').attr("disabled","");

						 $('#altura_nasal').attr("disabled","");
						 $('#comisuras').attr("disabled","");
						 $('#prominencia').attr("disabled","");
						 $('#espesor_labio_sup').attr("disabled","");
						 $('#espesor_labio_inf').attr("disabled","");
						 $('#ancho_nariz').attr("disabled","");
						 $('#altura_nariz').attr("disabled","");
						 $('#raiz_nariz').attr("disabled","");
						 $('#base_nariz').attr("disabled","");
						 $('#frente_altura').attr("disabled","");
						 $('#frente_ancho').attr("disabled","");
						 $('#inclinacion_frente').attr("disabled","");

						 $('#cmpImplantacionCejas').attr("disabled","");
						 $('#cmpFormaCejas').attr("disabled","");
						 $('#cmpTamanoCejas').attr("disabled","");

						 $('#Cicatrices1').attr("disabled","");
						 $('#Cicatrices2').attr("disabled","");
						 $('#Cicatricestext').attr("disabled","");
						 $('#Tatuajes1').attr("disabled","");
						 $('#Tatuajes2').attr("disabled","");
						 $('#Tatuajestext').attr("disabled","");
						 $('#Lunares1').attr("disabled","");
						 $('#Lunares2').attr("disabled","");
						 $('#Lunarestext').attr("disabled","");
						 $('#Defectos1').attr("disabled","");
						 $('#Defectos2').attr("disabled","");
						 $('#Defectostext').attr("disabled","");
						 $('#Protesis1').attr("disabled","");
						 $('#Protesis2').attr("disabled","");
						 $('#Protesistext').attr("disabled","");
						 $('#Otrastext').attr("disabled","");
						 $('#cmpFactorRH').attr("disabled","");
						 $('#lentes1').attr("disabled","");
						 $('#lentes2').attr("disabled","");
						 $('#cmpTipoSangre').attr("disabled","");
						 $('#barba1').attr("disabled","");
						 $('#barba2').attr("disabled","");
						 $('#peso').attr("disabled","");
						 $('#estatura').attr("disabled","");
						 $('#bigote1').attr("disabled","");
						 $('#bigote2').attr("disabled","");
						 $('#cmpComplexion').attr("disabled","");
						  $('#cmplobuloContorno').attr("disabled","");
						  $('#cmpHelixOriginal').attr("disabled","");
						  $('#cmpDireccionCejas').attr("disabled","");
						  $('#cmpFormaOreja').attr("disabled","");
					} 

					//Fucion para ocultar y mostrar la seccion de datos generales dependiendo del tipo de persona.
					function changeTipoPersonaMoral(){
						var selected = $("#cbxDenuncianteTipoPerosona option:selected");
						if (parseInt(selected.val()) == 1 ){
							  anonimoV();
							  
							  habilitaDeshabilitaTabAcordeon("datosGeneralesTab","domicilioTab",1);
							  habilitaDeshabilitaTabAcordeon("mediafiliacionTab","domicilioTab",1);
							  habilitaDeshabilitaTabAcordeon("documentosIdentificacion","domicilioTab",1);
								
							  $('#iIngresarOrgWorkSheet').hide();
							  $('#iVictimaWorkSheet').show();
							  liveDomicilioNotificaciones();
						}else{
							anonimoV();

							habilitaDeshabilitaTabAcordeon("datosGeneralesTab","domicilioTab",0);
							habilitaDeshabilitaTabAcordeon("mediafiliacionTab","domicilioTab",0);
							habilitaDeshabilitaTabAcordeon("documentosIdentificacion","domicilioTab",0);
							//Selecciona Mexico por default
							seleccionaMexico();
							$('#iIngresarOrgWorkSheet').show();
							$('#iVictimaWorkSheet').hide();
							killDomicilioNotificaciones();
						}
					}
					
					//
					function anonimoV(){
						
						var selected = $("#cbxDenuncianteTipoPerosona option:selected");
						if (parseInt(selected.val()) == 1 ){
							//fisica
								habilitaDeshabilitaTabAcordeon("datosGeneralesTab","datosGeneralesTab",1);
								habilitaDeshabilitaTabAcordeon("domicilioTab","datosGeneralesTab",1);
								
								habilitaDeshabilitaTabAcordeon("cejaMediaFiliacion","datosGeneralesTab",1);
								habilitaDeshabilitaTabAcordeon("mContactoTab","datosGeneralesTab",1);
								habilitaDeshabilitaTabAcordeon("documentoTab","datosGeneralesTab",1);
								$("#datosGeneralesTab").click();
								habilitaDeshabilitaCamposIngOrganizacion(0);
						}else{
							//moral
								habilitaDeshabilitaTabAcordeon("datosGeneralesTab","domicilioTab",0);
								habilitaDeshabilitaTabAcordeon("domicilioTab","domicilioTab",0);
								
								habilitaDeshabilitaTabAcordeon("cejaMediaFiliacion","domicilioTab",0);
								habilitaDeshabilitaTabAcordeon("mContactoTab","domicilioTab",0);
								habilitaDeshabilitaTabAcordeon("documentoTab","domicilioTab",0);
								habilitaDeshabilitaCamposIngOrganizacion(0);
								//llamamos al metodo para limpiar los datos capturados
								limpiarDatosAnonimo();
								$("#domicilioTab").click();
								habilitaDeshabilitaCamposIngOrganizacion(1);
								$("#cbxIngOrgTiposOrganizacion").attr("disabled","");
						}
					}
					
					//Funcion para limpiar los campos cuando seleccionamos Anonimo
					function limpiarDatosAnonimo()
					{
						//limpiamos los datos Generales
						cleanDatosGenerales();
						//limpiamos los datos de Nacimiento
						cleanAllCombosNacimiento();
						//limpia datos domicilio
						limpiarFormulario();
						//limpiamos los campos de coordenadas GPS
						limpiarDtsCrdndsGPS();
						//limpiamos los campos de coordenadas GPS de notificacion
						limpiarDtsCrdndsGPSNotif();
						//limpiamos los medios de contacto
						limpiarDtsMdsDCntct();
						//limpiamos los campos de doc de identificacion
						limpiarCmpsCptrDocId();
					}
					
					/**
					* Funci&oacute;n que guarda los datos de la pantalla
					*/
					function guardarOrgDenunciante(){
						$('#iIngOrgBtnGuardar').unbind();
						if(validaNombreYTipo()){
							$("#iIngOrgBtnGuardar").click(guardarOrgDenunciante);
							return;
						}
						var params = '';
						params += 'idIndividuo='+idOrganizacion;
						params += '&calidadDelIndividuo=0';
						params += '&numeroExpediente='+numeroExpediente;
							params += '&esPersonaMoral=true';
							params += '&idUsuario=0';
							params += '&calidadPersonaMoral=VICTIMA';
							params += '&'+extraeDatosPersonaMoralIngOrg();//datos generales de organizacion
							params += obtenerParametrosDomicilio();//datos del domicilio de la organizacion
							params += obtenerMedios();//datos de los medios de contacto de la organizacion
							//alert(params);
							//llamamos al ajax que guardara la informacion de la organizacion
							$.ajax({								
						    	  type: 'POST',
						    	  url: '<%= request.getContextPath()%>/guardarOrganizacion.do',
						    	  data: params,				
						    	  dataType: 'xml',
						    	  async: false,
						    	  success: function(xml){
						    		  if(parseInt($(xml).find('code').text())==0)
						    		  {  
					    			    //el guardado fue exitoso
					    			    //la variable idOrganizacion corresponde con el involucradoId con el que se relaciona la org
					    			    idOrganizacion=$(xml).find('organizacionId').text();//seteamos el id de la nueva organizacion
							    		//revisamos si se elimino el objeto durante la actualizacion  
										if($('#anularInvolucrado').is(':checked')=='false' || $('#anularInvolucrado').is(':checked')==false)
										{
										  var nombre =$(xml).find('organizacionDTO').find('nombreOrganizacion').text();
			    			 			  window.parent.cargaVictima(nombre,idOrganizacion);
						    			  //habilitamos el boton para poder guardar el representante legal y el contacto organizacional
							    		  //$("#iVictimaBtnGuardar").attr("disabled","disabled");
						    			  $("#iIngOrgBtnIngresarContacto,#btnIngOrgFormalDatosRep").attr("disabled","");
						    			  if (idVictima == "null" &&  audienciaId != null && audienciaId != "null"){
						    				  asociarOrganizacionAAudiencia(idOrganizacion,audienciaId);
						    			  }

						    			  try{window.parent.cargaGridVictimaOrg();}catch(e){}
							    		  alertDinamico("La organizaci&oacute;n se guard&oacute; exitosamente");
							    		  desavilitaDatos();
							    		  habilitaDeshabilitaCamposIngOrganizacion(0);
						    			  deshabilitaDatosDomicilio();
										}
										else
										{
											//mandamos eliminar visualmente el elemento anulado logicamente y luego cerraremos la ventana
											window.parent.eliminarVictima(idOrganizacion);
											window.parent.cerrarVentanaVictima();
										}  
						    		  }
						    		  else
						    		  {
						    			  idOrganizacion=0;
						    			  alertDinamico("Ocurri&oacute; un error al guardar la organizaci&oacute;n");
						    		  }
						    	  }
						    	});
							$("#iIngOrgBtnGuardar").click(guardarOrgDenunciante);
					}

/*************************************************FUNCIONES PARA AUDIENCIAS PJ****************************************************************************/
	
	/*
	* Asocia un involucrado recien creado a la audiencia con el id enviado como parametro
	*/
	function asociarIndividuoAAudiencia(individuo,audiencia){
	
		$.ajax({								
			type: 'POST',
	    	url: '<%= request.getContextPath()%>/asociarInvolucradoAAudiencia.do',
	    	data: 'involucradoId='+individuo+'&audienciaId='+audiencia,				
	    	dataType: 'xml',
	    	success: function(xml){
	    		
	    	}
		});
	}
	
	/*
	* Funcion que lleva a cabo la asociacion de una organizacion a una audiencia
	*/
	function asociarOrganizacionAAudiencia(involucradoId, audienciaId){
		$.ajax({								
			type: 'POST',
	    	url: '<%= request.getContextPath()%>/asociarOrganizacionAAudiencia.do',
	    	data: 'involucradoId='+involucradoId+'&audienciaId='+audienciaId,				
	    	dataType: 'xml',
	    	success: function(xml){
	    		
	    	}
		});
	}
	
	/**
	*
	*/
	function ocultaVistaVictimaMuerta(){
		$("#tdDatosGrlsVictimaMuerta").hide();
		$("#tdSexoNoEspecificado").hide();
		
		habilitaDeshabilitaTabAcordeon1("lugarDefuncionTab", 0);
	}
	
	/**
	* Funcion que muestra la vista de la victima cuando seleccionan que esta muerta
	*/
	function cargaVistaMuerto(){
		
		var confInstitucionId =  <%=confInstitucionDTO.getConfInstitucionId()%>;
		var institucionSSP = <%=Instituciones.SSP.getValorId()%>;
		
		if(confInstitucionId != institucionSSP) {
			
			//deshabilita secciones de victima viva
			$("#tdDatosNacimiento").hide();
			$("#trIdiomaLengua").hide();
			$("#rowDomicilioNotif").hide();
			
			//deshabilita pesta&ntilde;as inecesarias
			habilitaDeshabilitaTabAcordeon1("mContactoTab", 0);
			habilitaDeshabilitaTabAcordeon1("cejaMediaFiliacion",0);
			habilitaDeshabilitaTabAcordeon1("documentoTab",0);
			
			//carga campos de fechas y combos
			if(!esCargaVistaMuerto){
				cargaCalendarioDefuncion();
				cargaTipoDefuncion();
				cargaCertificadaPor();
				cargaSitioDefuncion();
				cargaSitioLesion();
				cargaFueEnTrabajo();
				cargaTipoEvento();
				cargaTipoVictima();
				cargaTipoArma();
				cargaEdadUnidad();
				cargaCondicionActividad();
				cargaCondicionEmbarazo();
				cargaPeriodoPosparto();
				esCargaVistaMuerto = true;
			}
			//habilita secciones defuncion
			$("#tdDatosGrlsVictimaMuerta").show();
			$("#tdSexoNoEspecificado").show();
			//cambia etiqueta colonia por localidad
			cambiaEtiquetaColonia("si");
			
			//habilita pesta&ntilde;a defuncion
			habilitaDeshabilitaTabAcordeon1("lugarDefuncionTab", 1);
		
		}
		
	}
	
	/**
	* Funcion que muestra la vista de la victima cuando seleccionan que esta viva
	*/
	function cargaVistaVivo(){
		
		//habilita secciones de victima viva
		$("#tdDatosNacimiento").show();
		$("#trIdiomaLengua").show();
		$("#rowDomicilioNotif").show();
		
		//habilita pesta&ntilde;as necesarias
		habilitaDeshabilitaTabAcordeon1("mContactoTab",1);
		habilitaDeshabilitaTabAcordeon1("cejaMediaFiliacion",1);
		habilitaDeshabilitaTabAcordeon1("documentoTab",1);
	
		//deshabilita secciones defuncion
		$("#tdDatosGrlsVictimaMuerta").hide();
		$("#tdSexoNoEspecificado").hide();
		//cambia etiqueta colonia por localidad
		cambiaEtiquetaColonia("no");
		
		//deshabilita pesta&ntilde;a defuncion
		habilitaDeshabilitaTabAcordeon1("lugarDefuncionTab", 0);
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
			<td width="1060">
				<table id="iVictimaViewHeader" width="100%" border="0">
					<tr>
						<td  width="111" valign="top" align="left" valign="middle" class="txt_gral_victima">Tipo de Persona</td>
						<!-- <td width="10%" class="seccion">Denuncia </td> -->
						<td align="left"><select id="cbxDenuncianteTipoPerosona">
						              <option value="1">F&iacute;sica</option>
						              <option value="0">Moral</option>
						          </select>&nbsp;&nbsp;&nbsp;&nbsp;
						          <input type="button" class="ui-button ui-corner-all ui-widget" id="anularInvolucrado" value="Anular Involucrado"></td>
						<td width="30%" align="right" class="seccion">Expediente: <span id="expediente"><%= request.getParameter("numeroExpediente")%></span> </td>
					</tr>
				</table>
			</td>
		</tr>
		<tr valign="top">
			<td width="1060">
				<table id="iVictimaWorkSheet" width="1060px" height="176" class="back_victima" border="0">
					<tbody>
					<tr valign="top">
					</tr>
					<tr valign="top">
						<td width="40%" align="center" valign="middle">
							<table width="360" border="0" cellpadding="0" cellspacing="0" class="linea_derecha_gris" >
								<tr>
									<td width="10" height="109">&nbsp;</td>
                					<td width="4">&nbsp;</td>
									<td width="123">
										<img alt="foto" src="<%= request.getContextPath() %>/resources/images/foto.png" id="iVictimaCmpFoto">
									</td>
									<td width="123">
										&nsbp;
									</td>
								</tr>
								
							</table>
						</td>
						<td width="20%" align="left" valign="middle">
							<table width="177" border="0" cellpadding="0" cellspacing="0" class="linea_derecha_gris" onclick="anonimo()">
								<tr id="trEsVivo">
									<td>
										<input type="radio" name="iVictimaCmp" id="iVictimavCmpV" value="false" >
									</td>
									<td align="left">Vivo</td>
									<!-- td align="right">
										<input type="checkbox" value="false" id="iVictimaCmpMenorEdad"/> Menor de Edad
									</td-->
								</tr>
								<tr id="trEsMuerto">
									<td>
										<input type="radio" name="iVictimaCmp" id="iVictimamCmpM" value="false" >
									</td>
									<td align="left">Muerto</td>
									<td>&nbsp;</td>
								</tr>
								<tr id="trEsDesconocido">
									<td>
										<input type="checkbox" name="iVictimaCmpcheck" id="iVictimaCmpD" value="false" onclick="reglaNombre()">
									</td>
									<td align="left">Desconocido</td>
									<!-- td><input  type="button"  value="Hola" onclick="asignaclass();$('#botonvalida').click();"/></td-->
								</tr>
							</table>
						</td>
						<td width="40%" align="left" valign="middle">
							<table width="92%" border="0">
								<!-- tr>
									<td width="32%" height="25" align="right">CALIDAD</td>
		                            <td width="29%" height="25" align="center">VICTIMA</td>
								</tr-->
								<tr>
									<td align="right">
										Nombre:
									</td>
									<td>
										<div id="iVictimaCmpNombre" style="border: 0; background: #DDD;width: 250px;">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td align="right">
										Apellido Paterno:
									</td>
									<td>
										<div id="iVictimaCmpApellidoPaterno" style="border: 0; background: #DDD;width: 250px;">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td align="right">
										Apellido Materno:
									</td>
									<td>
										<div id="iVictimaCmpApellidoMaterno" style="border: 0; background: #DDD;width: 250px;">&nbsp;</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr valign="top" >
						<td colspan="3">
							<table width="100%" >
								<tr valign="top">
									<td valign="top" align="center">
										<input type="button" class="ui-button ui-corner-all ui-widget" value="Modificar Datos" id="iVictimaBtnModificarDatos" onclick="habilitaRadioBotones()"/>
										<input type="button" class="ui-button ui-corner-all ui-widget" value="Guardar" id="iVictimaBtnGuardar"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					</tbody>
				</table>
				<!-- Tabla INGRESAR ORGANIZACION -->
		   		<div id="iIngresarOrgWorkSheet"> <jsp:include page="ingresarOrganizacion.jsp"/></div>
				<!-- FIN Tabla INGRESAR ORGANIZACION -->
<!-- 			</td>
		</tr> -->
		<tr valign="top">
			<td>
				<table width="100%" border="0">
					<tr valign="top">
						<td width="100%" valign="top">
							<div id="iVictimaAccordionPane" style="width: 100%" >
					            <dl>
					                <dt id="datosGeneralesTab">Datos Generales</dt>
					                <dd id="ddDatosGenerales">
					                	<jsp:include page="datosGeneralesView.jsp"/>
									</dd>
					                <dt id="domicilioTab">Domicilio</dt>
					                <dd id="ddDomicilio">
					                	<jsp:include page="ingresarDomicilioView.jsp"/>
						            </dd>
						            <dt id="lugarDefuncionTab">Defunci&#243;n</dt>
					                <dd id="ddLugarDefuncion">
					                	<jsp:include page="ingresarLugarDefuncionView.jsp"/>
						            </dd>
					          <%
								if(confInstitucionDTO != null && confInstitucionDTO.getConfInstitucionId()!= null) {
									if (confInstitucionDTO.getConfInstitucionId() != Instituciones.SSP.getValorId()) {//Solo se muestra si la institucion es diferente de  SSP
								%>
										<dt id="cejaMediaFiliacion">Media Filiaci&oacute;n (Opcional)</dt>  
										<dd>
											<jsp:include page="mediaFiliacionView.jsp" />
										</dd>
								<%
									}
								}
							%>
					                <dt id="mContactoTab">Medios de Contacto</dt>
					                <dd>
					                	<jsp:include page="ingresarMediosContactoView.jsp"/>
					                </dd>
					                <dt id="documentoTab">Documentos de Identificaci&oacute;n </dt>
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

<div id="iVictimaAccordionDialogoMenorEdad" title="Menor de edad">
	<table>
		<tr>
			<td>Denuncia</td>
			<td>&nbsp;</td>
			<td>Expediente</td>
		</tr>
		<tr>
			<td>Calidad:</td>
			<td><input type="text" value="A" size="50" maxlength="40" id="iVictimaDialogoCmpCalidad"/></td>
		</tr>
		<tr>
			<td>Nombre:</td>
			<td><input type="text" value="A" size="50" maxlength="40" id="iVictimaDialogoCmpNombre"/></td>
		</tr>
	</table>
</div>

</body>
</html>