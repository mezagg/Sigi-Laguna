<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.ActividadesRS"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Elaborar Solicitud</title>
	
	<!--		Hojas de estilos asociadas-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/arbolExpediente.js"></script>	
	<jsp:include page="/WEB-INF/paginas/encabezadoDocumentos.jsp"/>
	<jsp:include page="/WEB-INF/paginas/reinsercionSocial/generales/datosAdicionales/envioDocumentosSimpleJEJ.jsp"/>
	<script type="text/javascript">
	
		var idWindowPdf = 1;
		var esconderArbol = <%=request.getParameter("esconderArbol")!=null?"true":"false"%>;
		esconderArbol=true;
		var numeroUnicoExpediente = '<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):"Sin numero"%>';
		var idResolutivo = '<%=request.getParameter("idResolutivo")!=null?request.getParameter("idResolutivo"):"Sin Resolutivo"%>';
		var idAudiencia = '<%=request.getParameter("idAudiencia")!=null?request.getParameter("idAudiencia"):"Sin Audiencia"%>';
		var sentenciaId = '<%=request.getParameter("sentenciaId")!=null?request.getParameter("sentenciaId"):"-1"%>';
		var documentoId = '<%=request.getParameter("documentoId")!=null?request.getParameter("documentoId"):""%>';
		var solicitudOriginalId = '<%=request.getParameter("solicitudOriginalId")!=null ? request.getParameter("solicitudOriginalId"):"0"%>';
		var idSolicitudAnterior = '<%=request.getParameter("idSolicitudAnterior")!=null ? request.getParameter("idSolicitudAnterior") :0 %>';
		var numExpIdGlobal=0;
		var idWindowIngresarDenunciante = 1;
		var idWindowIngresarVictima = 1;
		var idWindowIngresarProbResponsable = 1;
		var idWindowIngresarTestigo = 1;
		var idWindowIngresarTraductor = 1;
		var idWindowIngresarQuienDetuvo = 1;
		var documentoParcialGuardado="<%=request.getParameter("documentoId") != null ? request.getParameter("documentoId") : "" %>";
		
		var idTipoSolicitud=<%=TiposSolicitudes.REINSERCION_SOCIAL.getValorId()%>;
		var contextoPagina = "${pageContext.request.contextPath}";
		//
		var tieneGuardadoParcial = false;
		
		var actividadId = <%=NumberUtils.toLong(request.getParameter("actividadId"))%> 
		var confInstitucion ='';
		
		jQuery().ready(function () {
			try {
			 	jQuery(document).ajaxStop(jQuery.unblockUI); 
			
				$('#idSolicitud').val(documentoId);
			
				$('#guardarNarraTiva').hide();
				cargaFechaCaptura();
				cargaHoraCaptura();
				$('#imprimirNarraTiva').click(crearPdf);
				$('#guardadoParcialNarrativa').click(guardadoParcial);
				$('#vistaPreliminar').click(elaborarVistaPreliminar);
				$('#menuSeleccionarDestinatario').click(muestraPopupSeleccionarDestinatario);
				cargarDocumento();
				cargarDatosExpediente();
							
				//Cambios para la pantalla de seleccionar destinatario
				$("#TipoUsuario_0").click();
				
				//cargaComboInstitucion(false);		//Funcion que carga el combo de las Instituciones
				$('#instituciones').change(enSeleccionInstitucion);
				
				//cargaComboDepartamentos();
				//$('#areas').change(cargaComboDepartamentos);
				$('#areas').change(cargaComboFuncionarios);
				$('#areas').change(cargaComboFacultades);
							
				//$('#departamentos').change(cargaComboFacultades);	
				//$('#departamentos').change(cargaComboFuncionarios);
				
				$('#facultades').change(cargaComboFuncionarios);
				
				$('#seleccionarDestinatario').hide();
				$('#menuSeleccionarDestinatario').click($('#seleccionarDestinatario').show());
//				$('#tblUsuarioExterno').hide();
//				$('#tblUsuarioSistema').hide();
				$('#divGridUsuariosExt').hide();
				$('#divGridUsuarios').hide();
				cargaGridUsuarios();
				cargaGridUsuariosExternos();
				$('#relacionarElementos').click(abreVentanaRelacionarElementos);
			    var numeroExpedienteId=<%= request.getAttribute("numeroExpedienteId") != null 
			    								? request.getAttribute("numeroExpedienteId")
			    								: request.getParameter("numeroExpedienteId")
			    							%>;
			    
			    <%if (request.getParameter("idTipoSolicitud") != null) { %>
			    	idTipoSolicitud=<%= request.getParameter("idTipoSolicitud")%>;		    
			    <% } %>
				numExpIdGlobal=numeroExpedienteId;
				
				jQuery( "#dialog:ui-dialog" ).dialog( "destroy" );
				
				jQuery( "#datosAdicionalesModal" ).dialog({
					resizable: false,
					title: "",
					height:'auto',
					width:'auto',
					modal: true,
					autoOpen: false,
					closeOnEscape: false,
					buttons: {
						"Continuar": function() {
							jQuery( this ).dialog( "close" );
						},
						"Cancelar": function() {
							jQuery( this ).dialog( "close" );					
						}
					}
				});	
				
				cargaDatosEncabezado();
				consultarTipoTamanioPapel();
			} catch(e) {
				console.error(e);
			}
		});
	
		
		function guardadoParcial(){
			bloquearPantalla(true, "Guardando Documento...");
			var recuperaTexto=$('.jquery_ckeditor').val();
			var nuevaActividad='<%=request.getParameter("nuevaActividad")%>';
			var numeroOficio=$('#iNumeroOficio').val();
			
			var params = 'parcial=' + true;
			params += '&formaId=' + '<%=request.getParameter("formaId")!=null?request.getParameter("formaId"):"" %>';
			params += '&numeroUnicoExpediente=' + numeroUnicoExpediente;
			params += '&documentoId=' + documentoParcialGuardado;
			params += '&texto=' + escape(recuperaTexto);
			params += '&iNumeroOficio=' + numeroOficio;
			params += '&seleccionTamanioPapel=' + seleccionTamPapel;			
			params += '&actuacion=' + actividadId;
			params += '&sentenciaId='+sentenciaId;
			
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/guardarDocumentoDefinitivamente.do',
			    data: params,
		    	dataType: 'xml',
		    	success: function(xml){
		    		var idDocGenerado = parseInt($(xml).find('documentoDTO').find('documentoId').text());
					if(idDocGenerado > 0){
						documentoParcialGuardado = idDocGenerado;
		    			tieneGuardadoParcial = true;
		    			$('#idSolicitud').val(documentoParcialGuardado);
		    			customAlert('El documento se ha guardado correctamente');
					}else{
						customAlert("Ocurri&oacute; un error al generar el documento parcial");
					}
		    				    				    		
		    	},
		    	error: function(xml){		    		
		    	}
			});
		}
		
		function cargarDocumento(){
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/CargarDocumento.do',
		    	data:{
					idAudiencia : idAudiencia,
					idResolutivo : idResolutivo,
					formaId : <%=request.getParameter("formaId")%>,
					numeroUnicoExpediente : numeroUnicoExpediente,
		    		documentoId : documentoId,
		    		sentenciaId : sentenciaId,
		    		asignacionProgramaId:  <%=request.getParameter("asignacionProgramaId")%>,
		    		actividadId : actividadId,
		    		mandaFormaEnConsulta : "si"
		    	} ,
		    	dataType: 'xml',
		    	success: function(xml){
		    		$('.jquery_ckeditor').val( $(xml).find('<%=ConstantesGenerales.CUERPO_DOCUMENTO_TAG_BUSQUEDA%>').text());
		    		$("#iNumeroOficio").val( $(xml).find('<%=ConstantesGenerales.NUMERO_FOLIO_TAG_BUSQUEDA%>').text());
		    		documentoParcialGuardado = $(xml).find('documentoId').text();
		    		$('#idSolicitud').val(documentoParcialGuardado);
		    	}
			});
		}
		
		function cargarDatosExpediente(){
			
			if(!esconderArbol){
				$.ajax({
			    	type: 'POST',
			    	url: '<%=request.getContextPath()%>/CargarArbolExpedienteEnDocumento.do',
			    	data: 'numeroUnicoExpediente='+numeroUnicoExpediente,
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

		function refresca(){
			$("#accordionDatosExpediente").empty();
			cargarDatosExpediente();
		}
		
		
		/*
		*Funcion que recuepera el texto del editor, y lo envia como una nueva peticion 
		*para que se imprima con formato PDF
		*/
		function crearPdf(){
			customConfirm("¿Est\u00E1 seguro que quiere guardarlo definitivamente?", "Aviso", aceptarGuardar); 
		}
		
		function aceptarGuardar(){
			if(!tieneGuardadoParcial){
				customAlert("Primero debe de <strong>Guardar Parcialmente</strong> el documento.");
				return false;
			}
	
			if(validadDatosSolicitud() == 1){
				//mostramos los divs en el padre de la pestaña de Acciones.
				//try{window.parent.muestraDIVSCanalizacion();}catch(e){}
			}
		}
		
		
		/*
		*Funcion que dispara el Action para consultar catalogos
		*/
		function cargaCatalogos() {
	
			$('#idDelitosCaso').empty();
		    $.ajax({
				type: 'POST',
		   	  	url: '<%=request.getContextPath()%>/consultaCatalogosCaso.do',
		   	  	data: '',
		   	 	dataType: 'xml',
		   	  	success: function(xml){
					$('#idDelitosCaso').empty();
					$('#idDelitosCaso').append('<option value="-1">- Seleccione -</option>');
					$(xml).find('catCatalogo').each(function(){
						$('#idDelitosCaso').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				   	});
		   	  	}
		   	});
		}
	
		
	   /*
		*Funcion que dispara el Action para consultar catalogos
		*/
		function cargaCatalogos2() {
	
			$('#idFormasParticipacion').empty();
			$.ajax({
				type: 'POST',
		    	url: '<%=request.getContextPath()%>/consultaCatalogosCaso.do',
		    	data: '',
		    	dataType: 'xml',
		    	success: function(xml){
					$('#idFormasParticipacionv').empty();
			    	$('#idFormasParticipacion').append('<option value="-1">- Seleccione -</option>');
		    		$(xml).find('catCatalogo').each(function(){
						$('#idFormasParticipacion').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}
	
	   
	   /*
		*Funcion que carga la fecha actual del sistema y la agrega en la pantalla al campo fecha
		*/
		function cargaFechaCaptura(){
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/ConsultarFechaCaptura.do',
		    	data: '',
		    	dataType: 'xml',
		    	success: function(xml){
		    		$('#generarDocumentoCmpFechaIngreso').val($(xml).find('fechaActual').text());
		    	}
			});
		}
	
	   
	   /*
		*Funcion que carga la hora actual del sistema y la agrega en la pantalla al campo hora
		*/
		function cargaHoraCaptura(){
	    	$.ajax({
	    		type: 'POST',
	    	    url: '<%=request.getContextPath()%>/ConsultarHoraCaptura.do',
	    	    data: '',
	    	    dataType: 'xml',
	    	    success: function(xml){
	    			$('#idHoraDate').val($(xml).find('horaActual').text());
	    		}
			});
	    }

		function imprime(){
			
			var texto=$('.jquery_ckeditor').val();
			
			$.ajax({
				async: false,
	    		type: 'POST',
	    	    url: '<%=request.getContextPath()%>/GenerarDocumento.do',
	    	    data: 'texto='+$('.jquery_ckeditor').val(),
	    	    dataType: 'xml',
	    	    success: function(xml){
	    		}
			});
		}
		
		//Inician cambios de RGG
		function muestraPopupSeleccionarDestinatario()
		{
				$( "#dialog-confirmSeleccionarDestinatario" ).dialog({
					resizable: false,
					height: 'auto',
					width:'auto',
					modal: true,
					buttons: {
						"Agregar": function() {
							var principal = "";
							var copia = "";									
							if($(':radio[name=TipoUsuario]:checked').val()==2){//Usuarios Externos
								var idPO = jQuery('#gridUsuariosExt').jqGrid('getGridParam','records') + 1;
								  var nombre = $("#nombre").val()+ " " + $("#apaterno").val() + " " + $("#amaterno").val();
								  var puesto = $("#puesto").val();				  
								  var correo = $("#correo").val();
								  
								  if(nombre !="" && puesto != ""){
									  if($(':radio[name=rbUsuarioExt]:checked').val()==1){
										  principal = "SI";
										  copia = "";	
									  }else{
										  principal = "";
										  copia = "SI";	
									  }
									  var direccion = $("#direccion").val();
									nuevoDestExt = new usuarioExterno(idPO,nombre, puesto, correo, principal, copia, direccion);
									agregarDestinatarioExterno(nuevoDestExt);
									//limpiaControles
									limpiaControles();
									$( this ).dialog( "close" );
									$( "#dialog:ui-dialog" ).dialog( "destroy" );  
								  }else{
									  customAlert("El nombre y el puesto son obligatorios");
								  }
								  
							}else{//Usuarios Internos								  
								  var idPO = parseInt($("#funcionarios option:selected").val());
								  if(idPO != -1){
								  	  var institucion = jQuery("#instituciones option:selected").text();
								  	  var instId = jQuery("#instituciones option:selected").val();
								  	  confInstitucion = instId;
									  var infoFuncionario =  $("#funcionarios option:selected").text();
									  datos = infoFuncionario.split(',');
									  var nombre = datos[0];
									  var puesto = datos[1];			  
									  var direccion = datos[2];
									  
									  if($(':radio[name=rbUsuario]:checked').val()==1){
										  principal = "SI";
										  copia = "";	
									  }else{
										  principal = "";
										  copia = "SI";	
									  }	
									  var areaDestinoId = $("#areas option:selected").val();
									  var esOtraInst = 	$(':radio[name=TipoUsuario]:checked').val()== 3 ? true : false;		  
									  nuevoDestInt = new usuarioInterno(idPO,nombre, puesto, instId, institucion, direccion, principal, copia, areaDestinoId, esOtraInst);
									agregarDestinatarioInterno(nuevoDestInt);
									limpiaControles();
									$( this ).dialog( "close" );
									$( "#dialog:ui-dialog" ).dialog( "destroy" );
									
									
								  }else{
									  customAlert("Se debe seleccionar un funcionario");
								  }								  
							}
	
						},
						"Terminar": function() {
							$( this ).dialog( "close" );
							$( "#dialog:ui-dialog" ).dialog( "destroy" );
						}
					}
				});
				$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();	
		}	
		
		function esDestinatarioOtrasInstituciones(){
			if($(':radio[name=TipoUsuario]:checked').val()==3){
				return true;
			}
			return false;
		}
		
		//Permite llenar el combo de instituciones
		function cargaComboInstitucion(esExterno) {
			if(esExterno) {
				$("#trFacultades").show();
			} else {
				$("#trFacultades").hide();
			}
			$('#instituciones').empty();
			
		    $.ajax({
		    	type: 'POST',
		    	url: '<%= request.getContextPath()%>/consultarCatalogoInstituciones.do',
		    	data: '',
		    	async: false,
		    	dataType: 'xml',
		    	success: function(xml){
					//INICIA: FIX PARA QUE SOLO MUESTRE LA INSTITUCIÓN DEL USUARIO
					if(esExterno) {
						$('#instituciones').append( '<option value="-1">-Seleccione-</option>');
					}
				    $(xml).find('instituciones').each(function(){
				    	<%
			    			UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
			    			if (usuario != null && usuario.getInstitucion() != null) {
				    	%>   
			    	  	if(esExterno) {
			    	  		if ( $(this).find('clave').text() != '<%=usuario.getInstitucion().getConfInstitucionId()%>'
			    	  				&& $(this).find('clave').text() != '<%=Instituciones.RS.getValorId()%>' ) {
			    	  			$('#instituciones').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
			    	  		}
			    	  	} else {
			    	 		if ( $(this).find('clave').text() == '<%=usuario.getInstitucion().getConfInstitucionId()%>') {
								$('#instituciones').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
							}
						}
						<% } %>
					});
					enSeleccionInstitucion();
					//TERMINA: FIX PARA QUE SOLO MUESTRE LA INSTITUCIÓN DEL USUARIO
				
		    	  }
		    });
	     }
		
		/*
		* Permite llenar el combo de Areas
		* Funcion para deshabilitar combo areas
		* Permite hacer cargar las Areas por Id de la Institucion
		*/
		function enSeleccionInstitucion() {
		  	var selected = $("#instituciones option:selected");
 			$('#areas').empty();
   			$('#areas').append( '<option value="-1">-Seleccione-</option>');
   			$('#departamentos').empty();
   			$('#departamentos').append( '<option value="-1">-Seleccione-</option>');
   			$('#facultades').empty();
   			$('#facultades').append( '<option value="-1">-Seleccione-</option>');   			
   			$('#funcionarios').empty();
   			$('#funcionarios').append( '<option value="-1">-Seleccione-</option>');
 	
	         $.ajax({
		    	  type: 'POST',
		    	  url: '<%= request.getContextPath()%>/consultarCatalogoAreasDependiente.do',
		    	  data: 'idInstitucion=' + selected.val(),	//Parametro para hacer la consulta de Areas por Id de la Institucion
		    	  async: false,
		    	  dataType: 'xml',
		    	  success: function(xml){
			    	 	$(xml).find('areas').each(function(){
						$('#areas').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						   });
		    	  }
		    	});
				
		}

		/*
		*Permite llenar el combo de departamentos: Funcion que dispara el Action para consultar departamentos
		*/	
		function cargaComboDepartamentos() {
			var selected = $("#areas option:selected");
			$.ajax({
				async: false,
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultarCatalogoDepartamentosDependiente.do',
 	    	    data: 'idArea=' + selected.val(),	//Parametro para hacer la consulta de Areas por Id de la Institucion
				dataType: 'xml',
				success: function(xml){
		    			$('#departamentos').empty();
		    			$('#departamentos').append( '<option value="-1">-Seleccione-</option>');
					$(xml).find('departamentos').each(function(){
						$('#departamentos').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}
		
		//permite llenar el combo de funcionarios
		function cargaComboFuncionarios() {
		
				var url ="";
				var institucion = $("#instituciones option:selected").val();
				var area = $("#areas option:selected").val();
				var departamento = $("#departamentos option:selected").val();
				var facultad = $("#facultades option:selected").val();
				
				if(esDestinatarioOtrasInstituciones()){
					if(facultad == -1){
						return false;					
					}
					url = '<%=request.getContextPath()%>/consultarFuncionariosExternos.do';
				} else {
					url = '<%=request.getContextPath()%>/consultarPersonalOperativoAction.do';
				}
		
							
				$.ajax({
					async: false,
					type: 'POST',
					url:url,
					data:{
							institucion 	: institucion,
							area 			: area,
							departamento 	: departamento,
							facultad		: facultad			
							
					},
					dataType: 'xml',
					success: function(xml){
		    			$('#funcionarios').empty();
		    			$('#funcionarios').append( '<option value="-1">-Seleccione-</option>');					
						$(xml).find('row').each(function(){
							$('#funcionarios').append('<option value="' + $(this).attr('id') + '">' + $(this).find('nombre').text()+ ", " + $(this).find('puesto').text()+ ", " + $(this).find('email').text() + '</option>');
						});
					}
				});
		}  

		//permite llenar el combo de facultades
		function cargaComboFacultades() {
				if(!esDestinatarioOtrasInstituciones()){
					return false;					
				}
		
				var url ="<%=request.getContextPath()%>/consultarRolesDelSistema.do";
				var institucion = $("#instituciones option:selected").val();
				var area = $("#areas option:selected").val();
				var departamento = $("#departamentos option:selected").val();
							
				$.ajax({
					async: false,
					type: 'POST',
					url:url,
					data:{
							institucion					: institucion,
							jerarquiaOrganizacionalId	: area,
							departamento 				: departamento,
							esCombo						: true
					},					 
					dataType: 'xml',
					success: function(xml){
		    			$('#funcionarios').empty();
		    			$('#funcionarios').append( '<option value="-1">-Seleccione-</option>');
		    			$('#facultades').empty();
		    			$('#facultades').append( '<option value="-1">-Seleccione-</option>');					
			    	 	$(xml).find('rolDTO').each(function(){
							$('#facultades').append('<option value="' + $(this).find('rolId').text() + '">' + $(this).find('descripcionRol').text() + '</option>');
						});
					}
				});
		}  

				
		//INICIA GARGA DE GRID PARA USUARIO EXTERNOS
		function cargaGridUsuariosExternos(){
			jQuery("#gridUsuariosExt").jqGrid({
				datatype: "local",
				height: 110,
				colNames:['ID','Nombre','Puesto', 'Correo','Principal','Copia','Dirección'],
				colModel:[	
						  {name:'id',index:'id', width:60, sorttype:"int",hidden:true},
				          {name:'nombre',index:'nombre', width:200},
				          {name:'puesto',index:'puesto', width:200},
				          {name:'correo',index:'correo', width:200,hidden:true},
				          {name:'principal',index:'principal', width:60},
				          {name:'copia',index:'copia', width:60},
				          {name:'direccion',index:'direccion', width:260}
			    ],
			    rowNum:10,
				autowidth: false,
				rowList:[10,20,30],
				pager: jQuery('#pager2'),
				viewrecords: true,								
				multiselect: false,
				hidegrid: false,
			    caption: "Destinatario(s) Externo(s)" 
		    }); 
		}
		
		//INICIA GARGA DE GRID PARA USUARIO INTERNOS
		function cargaGridUsuarios(){
			jQuery("#gridUsuarios").jqGrid({
				datatype: "local",
				height: 110,
				colNames:['ID','Nombre','Puesto','InstId','Instituci&oacute;n','Correo','Principal','Copia', 'AreaDestinoId', 'esOtraInst'],
				colModel:[	
						  {name:'id',index:'id', width:60, sorttype:"int"},
				          {name:'nombre',index:'nombre', width:200},
				          {name:'puesto',index:'puesto', width:200},
				          {name:'instId',index:'instId', width:200, hidden:true},
				          {name:'institucion',index:'institucion', width:200},
				          {name:'direccion',index:'direccion', width:200},
				          {name:'principal',index:'principal', width:60},
				          {name:'copia',index:'copia', width:60},
				          {name:'areaDestinoId',index:'areaDestinoId', width:200, hidden:true},
				          {name:'esOtraInst',index:'esOtraInst', width:200, hidden:true},
				          
			    ],
			    rowNum:10,
				autowidth: false,
				rowList:[10,20,30],
				pager: jQuery('#pager1'),
				viewrecords: true,								
				multiselect: false,
				hidegrid: false,
			    caption: "Destinatario(s) Usuarios del sistema" 
		    }); 			
		}
		
		//Se define la clase para un usuario Externo
		function usuarioInterno(id,nombre, puesto, instId, institucion, direccion,principal,copia, areaDestinoId, esOtraInst){
			this.id= id;
		    this.nombre = nombre;
		    this.puesto = puesto;
		    this.instId = instId;
		    this.institucion = institucion;
		    this.direccion = direccion;
		    this.principal = principal;
		    this.copia = copia;
		    this.areaDestinoId = areaDestinoId;
		    this.esOtraInst = esOtraInst;
		} 		
		function agregarDestinatarioInterno(nuevoDesInt){
			var mydata = [
						{
						  	id				: nuevoDesInt.id,
						  	nombre			: nuevoDesInt.nombre,
						  	puesto			: nuevoDesInt.puesto,
						  	instId			: nuevoDesInt.instId,
						  	institucion		: nuevoDesInt.institucion,
						  	direccion		: nuevoDesInt.direccion,
							principal		: nuevoDesInt.principal,
							copia			: nuevoDesInt.copia,
							areaDestinoId	: nuevoDesInt.areaDestinoId,
							esOtraInst		: nuevoDestInt.esOtraInst
						}						  
					];
			for(var i=0;i<=mydata.length;i++)
				jQuery("#gridUsuarios").jqGrid('addRowData',nuevoDesInt.id,mydata[i]);
			$('#divGridUsuarios').show();
		}
		
		//Se define la clase para un usuario Externo
		function usuarioExterno(id,nombre, puesto, correo,principal,copia,direccion){
			this.id= id;
		    this.nombre = nombre;
		    this.puesto = puesto;
		    this.correo = correo;
		    this.principal = principal;
		    this.copia = copia;
		    this.direccion = direccion;
		} 		
		
		function agregarDestinatarioExterno(nuevoDesExt){
			var mydata = [
						  {id:nuevoDesExt.id,nombre:nuevoDesExt.nombre,puesto:nuevoDesExt.puesto,correo:nuevoDesExt.correo,
							  principal:nuevoDesExt.principal,copia:nuevoDesExt.copia,direccion:nuevoDesExt.direccion}						  
		              ];
			for(var i=0;i<=mydata.length;i++)
				jQuery("#gridUsuariosExt").jqGrid('addRowData',i+1,mydata[i]);
			$('#divGridUsuariosExt').show();

		}
		
		function limpiaControles(){
			  $( "#instituciones" ).attr('selectedIndex',0);
			  $( "#departamentos" ).attr('selectedIndex',0);
			  limpiaCombo("#areas");
			  limpiaCombo("#funcionarios");
			  limpiaCombo("#facultades");
			  //$( "#rbUsuario" ).attr('cheked')= false;
			  //$( "#rbUsuarioExt" ).attr('cheked')= false;
			  //$( "#rbUsuario" ).attr('cheked')= false;			  
			  $("#nombre").val("");				  
			  $("#apaterno").val("");				  
			  $("#amaterno").val("");				  
			  $("#puesto").val("");				  
			  $("#direccion").val("");
			  $("#correo").val("");
		}
		
		
		function abreVentanaRelacionarElementos(){
		    $.newWindow({id:"iframewindowRelacionarElementos", statusBar: true, posx:20,posy:20,width:420,height:200,title:"Relacionar elementos", type:"iframe"});
            $.updateWindowContent("iframewindowRelacionarElementos",'<iframe src="<%= request.getContextPath() %>/mostrarPantallaRelacionarElementos.do" width="420" height="200" />');
		}
		
		function validadDatosSolicitud(){
			totalDestinartario = jQuery('#gridUsuariosExt').jqGrid('getGridParam','records') + jQuery('#gridUsuarios').jqGrid('getGridParam','records');
			if(totalDestinartario >0 ){
			
			<%
				Long actividadId =  NumberUtils.toLong(request.getParameter("actividadId"));
				ActividadesRS actividad = ActividadesRS.getByValor(actividadId);
				
				if(actividad != null 
					&& actividad.getcForward() != null
					&& actividad.getcForward() != "") {			
			%>
				pasoIntermedio('<%=actividadId%>');
			<% } else { %>
				enviarDatosFinales();
			<% } %>
				return 1;
			}else{
				customAlert('Se debe agregar al menos un destinatario');
				return 0;
			}					
		}
		
		function enviarDatosFinales(){
			<%				
				if(actividad == null || ( 
					actividad != ActividadesRS.ELABORAR_INFORME_FINAL_DE_REINSERCION_SOCIAL_DEL_SENTENCIADO
					&& actividad != ActividadesRS.AVISAR_DE_INGRESO_A_CERESO
					&& actividad != ActividadesRS.GENERAR_PLAN_REINSERCION_SOCIAL
					&& actividad != ActividadesRS.GENERAR_ACUERDO_DE_AUTORIZACION_DE_REINSERCION_SOCIAL
					&& actividad != ActividadesRS.GENERAR_ACUERDO_DE_RECHAZO_DE_REINSERCION_SOCIAL
					&& actividad != ActividadesRS.ELABORAR_SOLICITUD_REMISION_PARCIAL_PENA
					&& actividad != ActividadesRS.ELABORAR_SOLICITUD_LIBERTAD_CONDICIONAL
					&& actividad != ActividadesRS.ELABORAR_SOLICITUD_PRELIBERTAD
					&& actividad != ActividadesRS.ELABORAR_SOLICITUD_INDULTO
					&& actividad != ActividadesRS.ELABORAR_SOLICITUD_REPARACION_DANIO
					&& actividad != ActividadesRS.ELABORAR_SOLICITUD_VISITA_CONYUGAL
					&& actividad != ActividadesRS.GENERAR_ACUERDO_DE_AUTORIZACION_DE_REMISION_PARCIAL_PENA
					&& actividad != ActividadesRS.GENERAR_ACUERDO_DE_RECHAZO_DE_REMISION_PARCIAL_PENA
					&& actividad != ActividadesRS.ELABORAR_SOLICITUD_DECLARACION_JUDICIAL_EXTINCION_PRISION
					&& actividad != ActividadesRS.ELABORAR_SOLICITUD_PRESCRIPCION_PENA_PRISION
					&& actividad != ActividadesRS.ELABORAR_SOLICITUD_CERTIFICADO_EXTINCION_PENAS_PRISION
					&& actividad != ActividadesRS.ELABORAR_SOLICITUD_PROCEDIMIENTO_EXTINCION_MULTA
					&& actividad != ActividadesRS.ELABORAR_SOLICITUD_PROCEDIMIENTO_EXTINCION_REPARACION_DANIO
					&& actividad != ActividadesRS.ELABORAR_SOLICITUD_CERTIFICADO_EXTINCION_PENAS_PECUNIARIAS
					&& actividad != ActividadesRS.ELABORAR_SOLICITUD_AUTO_FINALIZACION_PROCEDIMIENTO_EJECUCION
					&& actividad != ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_DECLARACION_JUDICIAL_EXTINCION_PRISION
					&& actividad != ActividadesRS.GENERAR_ACUERDO_RECHAZO_DECLARACION_JUDICIAL_EXTINCION_PRISION
					&& actividad != ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_LIBERTAD_CONDICIONAL
					&& actividad != ActividadesRS.GENERAR_ACUERDO_RECHAZO_LIBERTAD_CONDICIONAL
					&& actividad != ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_PRELIBERTAD
					&& actividad != ActividadesRS.GENERAR_ACUERDO_RECHAZO_PRELIBERTAD
					&& actividad != ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_INDULTO
					&& actividad != ActividadesRS.GENERAR_ACUERDO_RECHAZO_INDULTO
					&& actividad != ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_REPARACION_DANIO
					&& actividad != ActividadesRS.GENERAR_ACUERDO_RECHAZO_REPARACION_DANIO
					&& actividad != ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_VISITA_CONYUGAL
					&& actividad != ActividadesRS.GENERAR_ACUERDO_RECHAZO_VISITA_CONYUGAL
					&& actividad != ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_EXTINCION_MULTA
					&& actividad != ActividadesRS.GENERAR_ACUERDO_RECHAZO_EXTINCION_MULTA
					&& actividad != ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_EXTINCION_REPARACION_DANIO
					&& actividad != ActividadesRS.GENERAR_ACUERDO_RECHAZO_EXTINCION_REPARACION_DANIO
					&& actividad != ActividadesRS.ELABORAR_SOLICITUD_INFORMACION_DGPRS
					&& actividad != ActividadesRS.COMUNICAR_ACUERDO_RESOLUCION_DGPRS
					&& actividad != ActividadesRS.INFORMAR_INTEGRACION_PROCEDIMIENTO_EJECUCION)) {			
			%>
					enviaDatosSolicitud();												
				<%} %>
		
				//imprime();
				guardarDefinitivamente();
				
			<%				
				if(actividad != null 
					&& (actividad == ActividadesRS.ELABORAR_INFORME_FINAL_DE_REINSERCION_SOCIAL_DEL_SENTENCIADO
						|| actividad == ActividadesRS.AVISAR_DE_INGRESO_A_CERESO
						|| actividad == ActividadesRS.GENERAR_PLAN_REINSERCION_SOCIAL
						|| actividad == ActividadesRS.GENERAR_ACUERDO_DE_AUTORIZACION_DE_REINSERCION_SOCIAL
						|| actividad == ActividadesRS.GENERAR_ACUERDO_DE_RECHAZO_DE_REINSERCION_SOCIAL
						|| actividad == ActividadesRS.ELABORAR_SOLICITUD_REMISION_PARCIAL_PENA
						|| actividad == ActividadesRS.ELABORAR_SOLICITUD_LIBERTAD_CONDICIONAL
						|| actividad == ActividadesRS.ELABORAR_SOLICITUD_PRELIBERTAD
						|| actividad == ActividadesRS.ELABORAR_SOLICITUD_INDULTO
						|| actividad == ActividadesRS.ELABORAR_SOLICITUD_REPARACION_DANIO
						|| actividad == ActividadesRS.ELABORAR_SOLICITUD_VISITA_CONYUGAL
						|| actividad == ActividadesRS.GENERAR_ACUERDO_DE_AUTORIZACION_DE_REMISION_PARCIAL_PENA
						|| actividad == ActividadesRS.GENERAR_ACUERDO_DE_RECHAZO_DE_REMISION_PARCIAL_PENA
						|| actividad == ActividadesRS.ELABORAR_SOLICITUD_DECLARACION_JUDICIAL_EXTINCION_PRISION
						|| actividad == ActividadesRS.ELABORAR_SOLICITUD_PRESCRIPCION_PENA_PRISION
						|| actividad == ActividadesRS.ELABORAR_SOLICITUD_CERTIFICADO_EXTINCION_PENAS_PRISION
						|| actividad == ActividadesRS.ELABORAR_SOLICITUD_PROCEDIMIENTO_EXTINCION_MULTA
						|| actividad == ActividadesRS.ELABORAR_SOLICITUD_PROCEDIMIENTO_EXTINCION_REPARACION_DANIO
						|| actividad == ActividadesRS.ELABORAR_SOLICITUD_CERTIFICADO_EXTINCION_PENAS_PECUNIARIAS
						|| actividad == ActividadesRS.ELABORAR_SOLICITUD_AUTO_FINALIZACION_PROCEDIMIENTO_EJECUCION
						|| actividad == ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_DECLARACION_JUDICIAL_EXTINCION_PRISION
						|| actividad == ActividadesRS.GENERAR_ACUERDO_RECHAZO_DECLARACION_JUDICIAL_EXTINCION_PRISION
						|| actividad == ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_LIBERTAD_CONDICIONAL
						|| actividad == ActividadesRS.GENERAR_ACUERDO_RECHAZO_LIBERTAD_CONDICIONAL
						|| actividad == ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_PRELIBERTAD
						|| actividad == ActividadesRS.GENERAR_ACUERDO_RECHAZO_PRELIBERTAD
						|| actividad == ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_INDULTO
						|| actividad == ActividadesRS.GENERAR_ACUERDO_RECHAZO_INDULTO
						|| actividad == ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_REPARACION_DANIO
						|| actividad == ActividadesRS.GENERAR_ACUERDO_RECHAZO_REPARACION_DANIO
						|| actividad == ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_VISITA_CONYUGAL
						|| actividad == ActividadesRS.GENERAR_ACUERDO_RECHAZO_VISITA_CONYUGAL
						|| actividad == ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_EXTINCION_MULTA
						|| actividad == ActividadesRS.GENERAR_ACUERDO_RECHAZO_EXTINCION_MULTA
						|| actividad == ActividadesRS.GENERAR_ACUERDO_AUTORIZACION_EXTINCION_REPARACION_DANIO
						|| actividad == ActividadesRS.GENERAR_ACUERDO_RECHAZO_EXTINCION_REPARACION_DANIO
						|| actividad == ActividadesRS.ELABORAR_SOLICITUD_INFORMACION_DGPRS
						|| actividad == ActividadesRS.COMUNICAR_ACUERDO_RESOLUCION_DGPRS
						|| actividad == ActividadesRS.INFORMAR_INTEGRACION_PROCEDIMIENTO_EJECUCION)) {
			%>
					enviarDocumentosAlJuez(actividadId);
		    		if (idSolicitudAnterior != 0){
		    			registrarRelacionDocumento(idSolicitudAnterior,documentoParcialGuardado);			    			
		    		}
			<% } %>
		}
		
		function guardarDefinitivamente(){
			var params = '?parcial=false&texto=' + escape($('.jquery_ckeditor').val());
			params += '&actuacion=' + actividadId;
			params += '&documentoId=' + documentoParcialGuardado;
			params += '&seleccionTamanioPapel=' + seleccionTamPapel;			
			params += '&sentenciaId=' + sentenciaId;
			params += '&formaId=' + '<%=request.getParameter("formaId")!=null?request.getParameter("formaId"):"" %>';
			params += '&tipoOperacion=' + '<%=request.getParameter("tipoOperacion")!=null?request.getParameter("tipoOperacion"):"" %>';
			params += '&nuevaActividad=' + '<%=request.getParameter("nuevaActividad")!=null?request.getParameter("nuevaActividad"):""%>';
			params += '&numeroUnicoExpediente=' + '<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):"" %>';
			//los siguientes parametros no se ocupan dentro de la implementacion, sin embargo se conservan.
			params += '&idResolutivo=' + '<%=request.getParameter("idResolutivo")!=null?request.getParameter("idResolutivo"):"" %>';
			params += '&confInstitucion=' + confInstitucion;
			params += '&estatusSolicitud=' + '<%=request.getParameter("estatusSolicitud")!=null?request.getParameter("estatusSolicitud"):"" %>';

			$.ajax({
				type: 'POST',	
			    url: '<%=request.getContextPath()%>/guardarDocumentoDefinitivamente.do',
			    data: params,
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
			    	var idDocumentoDef = parseInt($(xml).find('documentoDTO').find('documentoId').text());
			    	var msj = "";
			    	if (idDocumentoDef > 0){
			    		if($('#idSolicitud').val() == 0) {
							msj="La solicitud se envi\u00F3 correctamente";
						} else {
							msj="La solicitud se actualiz\u00F3 correctamente";
						}
			    		consultaPDF(idDocumentoDef);
			    		customAlert(msj,"",cerrarCustomVentana);
			    	}else {
						customAlert('Error al intentar guardar la solicitud, int&eacute;ntelo mas tarde');
					}
				}
			});
		}

		function consultaPDF(id){
			document.frmConsultaDoc.documentoId.value = id;
			document.frmConsultaDoc.submit();
		}

		function pasoIntermedio(cForward){
			bloquearPantalla();
			var parametros = "actividadId=" + cForward; 
		
		    $.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/obtenerDatosAdicionalesRS.do',
				data: parametros, 
				async: false,
				dataType: 'html',
				success: function(responseText){
					mostrarDatosAdicionales(responseText);
				}
			});
		}

	function mostrarDatosAdicionales(html){
		jQuery( "#datosAdicionalesText" ).html(html);
		jQuery( "#datosAdicionalesModal" ).dialog( "open" );
	}

		function enviaDatosSolicitud(){
		    	var params = '&institucionSolicitante=' + 1;
			    params += '&solicitante=' + "";
			    params += '&numeroExpediente=' + numeroUnicoExpediente;
			    params += '&idsFuncionariosSolicitantes=' + obtenIdsDestinatariosInternos();
			    params += '&idSolicitud=' + $('#idSolicitud').val();
			    params += '&idTipoSolicitud=' + idTipoSolicitud;
			 bloquearPantalla();
		    $.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/registrarSolicitud.do',
				data: params, 
				async: false,
				dataType: 'xml',
				success: function(xml){
					if(parseInt($(xml).find('SolicitudDTO').find('documentoId').text())>0){
						$('#idSolicitud').val(parseInt($(xml).find('SolicitudDTO').find('documentoId').text()));
					} 
				}
			});			   		
		}

		
		/**
		* Funcion que permite limpiar un combo e ingresa la opcion de seleccione
		*/
		function limpiaCombo(idCombo){
			$(idCombo).empty();
			$(idCombo).append('<option value="-1">-Seleccione-</option>');			
		}
		
		function obtenIdsDestinatariosInternos(){		
			var ids = "";
			var lista = jQuery("#gridUsuarios").getDataIDs();
			for(i=0;i<lista.length;i++){
					rowData= jQuery("#gridUsuarios").jqGrid('getRowData',lista[i]);
					ids = ids + rowData.id + ","; 
			 }
			return ids;			
		}
		
		function inspeccionar(obj)
		{
		  var msg = '';		
		  for (var property in obj)
		  {
			if(typeof obj[property] == 'function')
			{
			  var inicio = obj[property].toString().indexOf('function');
			  var fin = obj[property].toString().indexOf(')')+1;
			  var propertyValue=obj[property].toString().substring(inicio,fin);
			  msg +=(typeof obj[property])+' '+property+' : '+propertyValue+' ;\n';
			}
			else if (typeof obj[property] == 'unknown')
			{
			  msg += 'unknown '+property+' : unknown ;\n';
			}
			else
			{
			  msg +=(typeof obj[property])+' '+property+' : '+obj[property]+' ;\n';
			}
		  }
		  return msg;
	}
	
		/*
		* Funci&oacute;n que lleva a cabo el registro de dos documentos relacionados.
		*/
		function registrarRelacionDocumento(idDocPrincipal, idDocRelacionado){
			var params = "idDocPrincipal="+idDocPrincipal+"&idDocRelacionado="+idDocRelacionado;
			$.ajax({
				type: 'POST',	
			    url: '<%=request.getContextPath()%>/registrarRelacionDocumento.do',
			    data: params,
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
					
				}
			});
		}
			
	</script>
</head>



<body>
	<!--iframe que crea una nueva peticion para imprimir un PDF-->
	<iframe id="framePdf" src="" width="0" height="0"></iframe>
	
	<!-- ETIQUETAS NECESARIAS PARA LOS CAMPOS DEL ENCABEZADO -->
	<table align="center" border="0" width="820px" height="50%">
		<tr><!-- MENU -->
			<td colspan="5">
				<ul class="toolbar">
					<div id="menu_head">
                		<li id="menuSeleccionarDestinatario" >Seleccionar Destinatario</li>
                        <!--li id="agregarElemento">Agregar Elemento</li-->
                        <!--li id="relacionarElementos">Relacionar Elementos</li-->
                        <!--li id="consultarDocumentosDelExpediente">Consultar Documentos del Expediente</li-->
						<li id="guardadoParcialNarrativa" class="first">Guardado Parcial</li>
						<li id="imprimirNarraTiva"><span></span>Guardado Definitivo</li>
						<li id="vistaPreliminar"><span></span>Vista Preliminar</li>
						<li id="seccionCbxTamanioPapel"><span></span>Tamaño de Papel
							<select name="cbxTamanioPapel" id="cbxTamanioPapel" onchange="recuperarTamanioPapel()" style=" border:0; background-color:#EEEEEE;">
		    				</select>
		    			</li>
						<!--li id="tbarBtnConsultarTurnoAtencion" class="first">Salir</li-->
					</div>
				</ul>
			</td>
	  	</tr>
		<tr>
			<td width="20%">Nombre Servidor P&uacute;blico:</td>
			<td width=""><input type="text" title="Nombre del Servidor Publico" size="40" id="iNombreServidorPublico" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			<td width="">Hora de Elaboraci&oacute;n:</td>
			<td width=""><input type="text" id="idHoraDate" disabled="disabled" size="30" style=" border:0; background-color:#EEEEEE;"/></td>
		</tr>
		<tr>
			<td width="20%">Cargo:</td>
			<td width=""><input type="text" title="Puesto" size="40" id="iPuesto" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			<td width="">Fecha de Elaboraci&oacute;n:</td>
			<td width=""><input type="text" id="generarDocumentoCmpFechaIngreso" name="generarDocumentoCmpFechaIngreso" disabled="disabled" size="30" style=" border:0; background-color:#EEEEEE;"/></td>
		</tr>
		<tr>
			<td>&Aacute;rea Administrativa:</td>
			<td><input type="text" title="Area Administrativa" size="40" id="iAreaAdministrativa" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			<td>Estado:</td>
			<td><input type="text" title="Estado" size="30" id="iEstado" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
		</tr>
		<tr>
			<td width="20%">N&uacute;mero de Oficio:</td>
			<td width=""><input type="text" title="Numero de Oficio" size="40" id="iNumeroOficio"  /></td>
			<!--  
			<td>Ciudad:</td>
			<td><input type="text" title="Ciudad" size="30" id="iCiudad" disabled="disabled" style=" border:0; background-color:#EEEEEE;"/></td>
			-->
		</tr>
	</table>
		
	<!-- ETIQUETAS PARA LA SECCION DE LOS ELEMENTOS DEL EXPEDIENTE -->	
	<table align="center" width="1024px" border="0">
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
			<td width="800px" valign="top" align="center">
				<div id="divGridUsuarios">				
					<table align="center" id="gridUsuarios" width="800px"></table>
					<div id="pager1"></div>				
					<br>
				</div>
				<div id="divGridUsuariosExt">
					<table align="center" id="gridUsuariosExt" width="800px"></table>
					<div id="pager2"></div>
				</div>
				<div style="margin-top: 0; margin-bottom: auto; vertical-align: top;margin-right: auto; margin-left: auto">
				<br>	
						
				<jsp:include page="/WEB-INF/paginas/ingresarNarrativaView.jsp" flush="true"></jsp:include>
				
				</div>
				<form name="frmDoc" action="<%= request.getContextPath() %>/GenerarDocumento.do" method="post">
					<input type="hidden" name="texto" value=""/>
					<input type="hidden" name="documentoId" value="<%=request.getParameter("documentoId") != null ? request.getParameter("documentoId") : "" %>"/>
					<input type="hidden" name="formaId" value="<%=request.getParameter("formaId")!=null?request.getParameter("formaId"):"" %>"/>
					<input type="hidden" name="seleccionTamanioPapel" value="<%=request.getParameter("seleccionTamanioPapel")!=null?request.getParameter("seleccionTamanioPapel"):"" %>"/>
				</form>
				<form name="frmConsultaDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
					<input type="hidden" name="documentoId" />
				</form>
			</td>
		</tr>
	</table>
	
	<!-- ETIQUETAS PARA SELECCIONAR DESTINATARIO -->
	<div id="dialog-confirmSeleccionarDestinatario" title="Seleccionar Destinatario: ">
	  <div id="seleccionarDestinatario">
	    <label>
	      <input type="radio" name="TipoUsuario" value="1" id="TipoUsuario_0" onClick="$('#tblUsuarioExterno').hide();$('#tblUsuarioSistema').show(); cargaComboInstitucion(false);" checked="checked">
	      Usuario del sistema</label>
	    <label>
	      <input type="radio" name="TipoUsuario" value="3" id="TipoUsuario_2" onClick="$('#tblUsuarioExterno').hide();$('#tblUsuarioSistema').show(); cargaComboInstitucion(true);">
	      Usuario de otras instituciones</label>	      
	    <label>
	      <input type="radio" name="TipoUsuario" value="2" id="TipoUsuario_1" onClick="$('#tblUsuarioSistema').hide();$('#tblUsuarioExterno').show();">
	      Externo</label>
	    <br>	    
	    <table width="342" cellspacing="0" cellpadding="0" id="tblUsuarioSistema">
	      <tr>
	        <td>Instituci&oacute;n:</td>
	        <td><select name="instituciones" id="instituciones" style="width: 500px;">
	          <!--option value="-1" selected="selected">-Seleccione-</option-->
	          </select></td>
          </tr>
	      <tr>
	        <td width="108">&Aacute;rea:</td>
	        <td width="232"><select name="area" id="areas" style="width: 500px;">
	          <option value="-1" selected="selected">-Seleccione-</option>
	          </select></td>
          </tr>
	      <tr style="display: none;">
	        <td>Departamentos:</td>
	        <td><select name="departamentos" id="departamentos" style="width: 500px;">
	          <option value="-1" selected="selected">-Seleccione-</option>
	          </select></td>
          </tr>
	      <tr id="trFacultades">
	        <td>Facultades:</td>
	        <td><select name="facultades" id="facultades" style="width: 500px;">
	          <option value="-1" selected="selected">-Seleccione-</option>
	          </select></td>
          </tr>
	      <tr>
	        <td>* Funcionario:</td>
	        <td><select name="funcionarios" id="funcionarios"  style="width: 500px;">
	          <option value="-1" selected="selected">-Seleccione-</option>
	          </select></td>
          </tr>	      
	      <tr>
	        <td colspan="2" align="left"><label>
	          <input type="radio" name="rbUsuario" value="1" id="RadioGroup1_2" checked="checked">
	          Destinatario principal</label>
	          <label>
	            <input type="radio" name="rbUsuario" value="2" id="RadioGroup1_3">
	            Destinatario copia</label></td>
          </tr>
        </table>
	    <table width="449" cellspacing="0" cellpadding="0" id="tblUsuarioExterno">
	      <tr>
	        <td>* Nombre:</td>
	        <td><input type="text" size="20px" id="nombre" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/></td>
          </tr>
	      <tr>
	        <td>Apellido Paterno:</td>
	        <td><input type="text" size="20px" id="apaterno" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/></td>
          </tr>
	      <tr>
	        <td>Apellido Materno:</td>
	        <td><input type="text" size="20px" id="amaterno" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/></td>
          </tr>
	      <tr>
	        <td width="133">* Cargo / Puesto</td>
	        <td width="314"><input type="text" size="20px" id="puesto"/></td>
          </tr>
          <tr>
	        <td>Email:</td>
	        <td><input type="text" size="20px" id="correo"/></td>
          </tr>
            <tr>
	        <td width="133">Dirección:</td>
	        <td width="314"><textarea id="direccion" COLS=45 ROWS=2></textarea></td>
          </tr>

	      <tr>
	        <td colspan="2" align="left">
	          <label>
	            <input type="radio" name="rbUsuarioExt" value="1" id="RadioGroup1_0">
	            Destinatario principal</label>
	          <label>
	            <input type="radio" name="rbUsuarioExt" value="2" id="RadioGroup1_1">
	            Destinatario copia</label>
	          <br>
	        </td>
          </tr>
        </table>
        <input type="hidden" size="20px" id="idSolicitud" value="0"/>
      </div>
	</div>
	<div id='datosAdicionalesModal'>
		<span id="datosAdicionalesText">
		</span>
	</div>
	
</body>
<script type="text/javascript">
$( "#dialog-confirmSeleccionarDestinatario" ).dialog();
$( "#dialog-confirmSeleccionarDestinatario" ).dialog( "destroy" );
</script>
</html>