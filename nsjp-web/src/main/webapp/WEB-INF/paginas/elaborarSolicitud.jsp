<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Areas"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>


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
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/arbolExpediente.js"></script>
	<jsp:include page="/WEB-INF/paginas/encabezadoDocumentos.jsp"/>
	<script type="text/javascript">
	
	var idWindowPdf = 1;
	var esconderArbol = <%=request.getParameter("esconderArbol")!=null?"true":"false"%>;
	var numeroUnicoExpediente = '<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):"Sin numero"%>';
	var idResolutivo= '<%=request.getParameter("idResolutivo")!=null?request.getParameter("idResolutivo"):"Sin Resolutivo"%>';
	var idAudiencia= '<%=request.getParameter("idAudiencia")!=null?request.getParameter("idAudiencia"):"Sin Audiencia"%>';
	var mandamientoJudicialId = <%=request.getParameter("mandamientoId")!=null ? request.getParameter("mandamientoId") : "0"%>;
	var numExpIdGlobal=0;
	var idWindowIngresarDenunciante = 1;
	var idWindowIngresarVictima = 1;
	var idWindowIngresarProbResponsable = 1;
	var idWindowIngresarTestigo = 1;
	var idWindowIngresarTraductor = 1;
	var idWindowIngresarQuienDetuvo = 1;
	
	var idTipoSolicitud=0;
	var documentoParcialGuardado="";
	var contextoPagina = "${pageContext.request.contextPath}";
	var idNumeroExpediente= '<%=request.getParameter("idNumeroExpediente")!=null?request.getParameter("idNumeroExpediente"):""%>';
	
	//Variable global que se actualiza en el primer guardado parcial que se lleve acabo para la forma transaccional
	var documentoId = '<%=request.getParameter("documentoId")!=null?request.getParameter("documentoId"):0 %>';
	
	//Permite registrar una actividad al momento de confirmar el envio
	var actividadId = '<%=request.getParameter("actividadId")!=null?request.getParameter("actividadId"):""%>';
	var idFuncionario = '<%=request.getParameter("idFuncionario")!=null?request.getParameter("idFuncionario"):""%>';
	var idWindowPantallaActuaciones = '<%=request.getParameter("idWindowPantallaActuaciones")!=null?request.getParameter("idWindowPantallaActuaciones"):""%>';
	var idFrame ="iframewindowGenerarDocumento" + idWindowPantallaActuaciones;

		jQuery().ready(function () {
			$('#guardarNarraTiva').hide();
			cargaFechaCaptura();
			cargaHoraCaptura();
			$('#btnEnviarSolicitud').click(crearPdf);
			$('#vistaPreliminarNarrativa').click(elaborarVistaPreliminar);			
			cargarDocumento();
			cargarDatosExpediente();
						
			//Cambios para la pantalla de seleccionar destinatario
			cargaComboInstitucion();		//Funcion que carga el combo de las Instituciones
			$('#instituciones').change(enSeleccionInstitucion);
			cargaCompoDepartamentos();
			$('#areas').change(cargaCompoFuncionarios);
			$('#areas').change(cargaCompoDepartamentos);
			$('#departamentos').change(cargaCompoFuncionarios);
			$('#menuSeleccionarDestinatario').click($('#dialog-confirmSeleccionarDestinatario').show());
			consultarFuncionarios();
		    var numeroExpedienteId=<%= request.getAttribute("numeroExpedienteId")%>;
			idTipoSolicitud = '<%=request.getParameter("idTipoSolicitud")!=null?request.getParameter("idTipoSolicitud"):0 %>';

			numExpIdGlobal=numeroExpedienteId;
			
			cargaDatosEncabezado();
			consultarTipoTamanioPapel();
			$('#dialog-confirmSeleccionarDestinatario').hide();
		});
	
		function cargarDocumento(){
			var mandaFormaEnConsulta="si";

			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/CargarDocumento.do?idAudiencia='+idAudiencia+'&idResolutivo='+idResolutivo+'',
		    	data: 'formaId=<%=request.getParameter("formaId")%>&numeroUnicoExpediente='+numeroUnicoExpediente+'&mandaFormaEnConsulta='+mandaFormaEnConsulta+
		    	'&documentoId=<%=request.getParameter("documentoId")!=null?request.getParameter("documentoId"):""%>',
		    	dataType: 'xml',
		    	success: function(xml){
		    		$('.jquery_ckeditor').val( $(xml).find('<%=ConstantesGenerales.CUERPO_DOCUMENTO_TAG_BUSQUEDA%>').text());
		    		$("#iNumeroOficio").val( $(xml).find('<%=ConstantesGenerales.NUMERO_FOLIO_TAG_BUSQUEDA%>').text());
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
			//Valida que se haya seleccionado un destinatario
			if(!validadDatosSolicitud()){
				return;
			}else{
				customConfirm("¿Est\u00E1 seguro que quiere enviar el oficio de investigaci\u00F3n?", "Aviso",generaDocumentoFormaTransaccional);	
			}
		}
		
		
		function generaDocumentoFormaTransaccional(){
			
			if(!validadDatosSolicitud()){
				return;
			}
			
			//mostramos los divs en el padre de la pestaña de Acciones.
			try{
				if(!window.parent.muestraDIVSCanalizacion()){
					return;
				}
			}catch(e){};
			
			idDocumento = guardarDefinitivamente();
			
			if(idDocumento > 0){
				try{window.parent.documentos();}catch(e){}
				//try{window.parent.recargarActuaciones();}catch(e){}
				consultaPDF(idDocumento);
				customAlert("La solicitud se envi\u00F3 correctamente.", "", cerrarVentaDocumentoActualizarGrid);
				
			}else{
				customAlert("Error al intentar enviar la solictud, inténtelo mas tarde");
			}
		}
		
		
		function guardarDefinitivamente(){
			console.info("NEW ** GUARDADO DEFINITIVO DE FORMA TRANSACCIONAL **");
			var idDocumento = 0;
			
			var params = 'idNumeroExpediente=' + idNumeroExpediente;
			params += '&actuacion=' + actividadId;
			params += '&cveFuncionarioAsignado=' + idFuncionario;
			params += '&seleccionTamanioPapel=' + seleccionTamPapel;			
			params += '&formaId=' + '<%=request.getParameter("formaId")!=null?request.getParameter("formaId"):"" %>';
			params += '&documentoId=' + documentoId;
			params += '&tipoOperacion=' + '<%=request.getParameter("tipoOperacion")!=null?request.getParameter("tipoOperacion"):"" %>';
			params += '&nuevaActividad=' + '<%=request.getParameter("nuevaActividad")!=null?request.getParameter("nuevaActividad"):""%>';
			params += '&numeroUnicoExpediente=' + numeroUnicoExpediente;
			params += '&texto=' + escape($('.jquery_ckeditor').val());
			params += '&catDiscriminanteId=' + $("#cbxAgencia option:selected").val();
			params += '&audienciaId=' + idAudiencia;

			//Parametros necesario para registrar una solicitud
	    	params += '&institucionSolicitante=' + 1;
		    params += '&solicitante=' + "";
		    params += '&numeroExpediente=' + numeroUnicoExpediente;
		    params += '&idsFuncionariosSolicitantes=' + jQuery("#gridFuncionarios").jqGrid('getGridParam','selrow');
		    params += '&idSolicitud=' + $('#idSolicitud').val();
		    params += '&idTipoSolicitud=' + idTipoSolicitud;
		    
		  	//Se eliminan eventos de los botones de guardado u envio
			deshabilitarBotonesGuardado();

			$.ajax({
				type: 'POST',	
			    url: '<%=request.getContextPath()%>/guardarDocumentoDefinitivamente.do',
			    data: params,
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
					activarAvisoAlCerrarVentanEnVisorDocumentos(idFrame, false);

			    	idDocumento = parseInt($(xml).find('documentoDTO').find('documentoId').text());
			    	if (mandamientoJudicialId != 0){
			    		registrarSolicitudMandamiento(idDocumento, mandamientoJudicialId);
			    	}
			    	//Se relacionan eventos de los botones de guardado u envio
					habilitarBotonesGuardado();
				}
			});
	    	return idDocumento;
		}

		function registrarSolicitudMandamiento(idSolicitud, mandamientoJudicialId){
			var params = "idSolicitud="+idSolicitud+"&idMandamiento="+mandamientoJudicialId;
			$.ajax({
				type: 'POST',	
			    url: '<%=request.getContextPath()%>/registrarSolicitudMandamiento.do',
			    data: params,
			    dataType: 'xml',
			    async: false,
			    success: function(xml){
					
				}
			});
		}
		
		function cerrarVentaDocumentoActualizarGrid(){
			if (typeof window.parent.documentos == 'function' ){
				window.parent.documentos();
			}
			if (typeof window.parent.cerrarVentanaDocumento == 'function' ){
				window.parent.cerrarVentanaDocumento(idWindowPantallaActuaciones);
			}
			if (typeof window.parent.cargaGridPoliciaMinisterial == 'function' ){
				window.parent.cargaGridPoliciaMinisterial();
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
		
		//Permite llenar el combo de instituciones
		function cargaComboInstitucion() {
	     $.ajax({
	    	  type: 'POST',
	    	  url: '<%= request.getContextPath()%>/consultarCatalogoInstituciones.do',
	    	  data: '',
	    	  async: false,
	    	  dataType: 'xml',
	    	  success: function(xml){
	    	  	//INICIA: FIX PARA QUE SOLO MUESTRE LA INSTITUCIÓN DEL USUARIO
		    	 	$(xml).find('instituciones').each(function(){
		    	 		<%
		    	 			UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
		    	 			if (usuario != null && usuario.getInstitucion() != null) {
		    	 		%>
		    	 		if ( $(this).find('clave').text() == '<%=usuario.getInstitucion().getConfInstitucionId()%>') {
							$('#instituciones').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
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
	         $.ajax({
		    	  type: 'POST',
		    	  url: '<%= request.getContextPath()%>/consultarCatalogoAreasDependiente.do',
		    	  data: 'idInstitucion=' + selected.val(),	//Parametro para hacer la consulta de Areas por Id de la Institucion
		    	  async: false,
		    	  dataType: 'xml',
		    	  success: function(xml){
		    		  	$('#areas').empty();
		    			$('#areas').append( '<option value="1">-Seleccione-</option>');
		    			$('#departamentos').empty();
		    			$('#departamentos').append( '<option value="1">-Seleccione-</option>');
		    			$('#funcionarios').empty();
		    			$('#funcionarios').append( '<option value="1">-Seleccione-</option>');
		    		  if(idTipoSolicitud == <%= TiposSolicitudes.POLICIA_MINISTERIAL.getValorId() %>){
		    			  $(xml).find('areas').each(function(){
		    				  if($(this).find('clave').text() == '<%= Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong() %>'){
								$('#areas').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		    				  }
							});
		    		  }else{
			    	 	$(xml).find('areas').each(function(){
						$('#areas').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
						   });
		    		  }
		    	  }
		    	});
				
		}

		/*
		*Permite llenar el combo de departamentos: Funcion que dispara el Action para consultar departamentos
		*/	
		function cargaCompoDepartamentos() {
			var selected = $("#areas option:selected");
			$.ajax({
				async: false,
				type: 'POST',
				url: '<%= request.getContextPath()%>/consultarCatalogoDepartamentosDependiente.do',
 	    	    data: 'idArea=' + selected.val(),	//Parametro para hacer la consulta de Areas por Id de la Institucion
				dataType: 'xml',
				success: function(xml){
		    			$('#departamentos').empty();
		    			$('#departamentos').append( '<option value="1">-Seleccione-</option>');
				
					$(xml).find('departamentos').each(function(){
						$('#departamentos').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
				}
			});
		}
		
		//permite llenar el combo de funcionarios
		function cargaCompoFuncionarios() {
			  var institucion = $("#instituciones option:selected").val();
			  var area = $("#areas option:selected").val();
			  var departamento = $("#departamentos option:selected").val();			
				$.ajax({
					async: false,
					type: 'POST',
					url:'<%=request.getContextPath()%>/consultarPersonalOperativoAction.do?institucion='+ institucion +'&area='+ area +'&departamento='+ departamento +'', 
					dataType: 'xml',
					success: function(xml){
		    			$('#funcionarios').empty();
		    			$('#funcionarios').append( '<option value="1">-Seleccione-</option>');
					
						$(xml).find('row').each(function(){
							$('#funcionarios').append('<option value="' + $(this).attr('id') + '">' + $(this).find('nombre').text()+ ", " + $(this).find('puesto').text()+ ", " + $(this).find('email').text() + '</option>');
						});
					}
				});
		}  
		
		function limpiaControles(){
			  $( "#instituciones" ).attr('selectedIndex',0);
			  $( "#departamentos" ).attr('selectedIndex',0);
			  limpiaCombo("#areas");
			  limpiaCombo("#funcionarios");
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
		
		function validadDatosSolicitud(){
			var id = jQuery("#gridFuncionarios").jqGrid('getGridParam','selrow');
			if(parseInt(id) >0 ){
				return true;
			}else{
				customAlert('Debe de seleccionar un destinatario');
				return false;
			}					
		}
		
		/**
		* Funcion que permite limpiar un combo e ingresa la opcion de seleccione
		*/
		function limpiaCombo(idCombo){
			$(idCombo).empty();
			$(idCombo).append('<option value="-1">-Seleccione-</option>');			
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
		
		
		//Variable para controlar el action para consultar pdf's
		var accionConsultarPdf= contextoPagina +"/ConsultarContenidoArchivoDigital.do";
		/* Precondicion:
		 * - contextoPagina
		 * - Definir la forma frmConsultaDoc
		 */

		function consultaPDF(id){
			document.frmConsultaDoc.action=accionConsultarPdf+"?documentoId="+id;
			document.frmConsultaDoc.submit();
		}
		
		
				//Llena el grid con los resultados de la busqueda del funcionario
		var banderaCargarORecargarFuncionario=0;
	    function consultarFuncionarios(){
			var idDistrito = 0;
			var idCatDiscriminante = '';
			var idArea = <%=Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal()%>;
			var idCatUIE = '';
			
			//Inicia grid
			if(banderaCargarORecargarFuncionario==0){
				jQuery("#gridFuncionarios").jqGrid({
					url:'<%=request.getContextPath()%>/consultarFuncionarioPorDepartamento.do?idDistrito='+idDistrito+'&idCatDiscriminante='+idCatDiscriminante+'&idArea='+idArea+'&idCatUIE='+idCatUIE+'', 			
					datatype: "xml",  		
					async: true,
					colNames:['Nombre','Apellido paterno','Apellido materno'], 
					colModel:[  {name:'nombre',index:'1',width:250,align:'center'},
					            {name:'apellidoPaterno',index:'2', width:250,align:'center'},
								{name:'apellidoMaterno',index:'3',width:300,align:'center'}
							],
							pager: jQuery('#pagerGridFuncionarios'), 
							rowNum:5,
							rowList:[5,10,15,20,25,30],
		                    autowidth: "90%",
		                    height:110,
							sortname: '1', 
							viewrecords: true,
		                    sortorder: "asc",
		    				multiselect: false,
		    				caption: "Destinatario",
		    				onSelectRow: function(id){
							}
				});
	        	$("#gridFuncionarios").trigger("reloadGrid");
	        	banderaCargarORecargarFuncionario=1;
		    }else{
		    	
				jQuery("#gridFuncionarios").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarFuncionarioPorDepartamento.do?idDistrito='+idDistrito+'&idCatDiscriminante='+idCatDiscriminante+'&idArea='+idArea+'&idCatUIE='+idCatUIE+'',datatype: "xml" });
				$("#gridFuncionarios").trigger("reloadGrid");		
		    }	            	
			//Fin grid
	    }

		
	</script>
</head>



<body>
	<!-- ETIQUETAS NECESARIAS PARA LOS CAMPOS DEL ENCABEZADO -->
	<table align="center" border="0" width="855px" height="50%">
		<tr><!-- MENU -->
			<td>
				<ul class="toolbar" id="menu_head">
						<li id="btnEnviarSolicitud"><span></span>Enviar</li>
						<li id="vistaPreliminarNarrativa"><span></span>Vista Preliminar</li>
						<li id="seccionCbxTamanioPapel"><span></span>Tamaño de Papel
							<select name="cbxTamanioPapel" id="cbxTamanioPapel" onchange="recuperarTamanioPapel()" style=" border:0; background-color:#EEEEEE;">
		    				</select>
		    			</li>
				</ul>
			</td>
	  	</tr>
	</table>	
	<table align="center" border="0" width="855px" height="50%">
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
			<td width=""><input type="text" title="Numero de Oficio" size="40" id="iNumeroOficio" onkeypress="return letrasNumero(event);" maxlength="20"/></td>
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
				<br>
				<div id="divGridUsuarios">				
					<table align="center" id="gridFuncionarios" width="800px"></table>
					<div id="pagerGridFuncionarios"></div>
				</div>
				<div style="margin-top: 0; margin-bottom: auto; vertical-align: top;margin-right: auto; margin-left: auto">
				<br>	
						
				<jsp:include page="/WEB-INF/paginas/ingresarNarrativaView.jsp" flush="true"></jsp:include>
				
				</div>
			</td>
		</tr>
	</table>
	
	<form name="frmConsultaDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
		<input type="hidden" name="documentoId" />
	</form>
	
	
	
	<form name="frmDoc" action="<%= request.getContextPath() %>/GenerarDocumento.do" method="post">
		<input type="hidden" name="texto" value=""/>
		<input type="hidden" name="parcial" value=""/>
		<input type="hidden" name="formaId" value="<%=request.getParameter("formaId")!=null?request.getParameter("formaId"):"" %>"/>
		<input type="hidden" name="numeroUnicoExpediente" value="<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):"" %>"/>
		<input type="hidden" name="documentoId" value="<%=request.getParameter("documentoId")!=null?request.getParameter("documentoId"):"" %>"/>
		<input type="hidden" name="tipoOperacion" value="<%=request.getParameter("tipoOperacion")!=null?request.getParameter("tipoOperacion"):"" %>"/>
		<input type="hidden" name="estatusSolicitud" value="<%=request.getParameter("estatusSolicitud")!=null?request.getParameter("estatusSolicitud"):"" %>"/>
		<input type="hidden" name="idResolutivo" value="<%=request.getParameter("idResolutivo")!=null?request.getParameter("idResolutivo"):"" %>"/>
		<input type="hidden" name="iNumeroOficio" value="<%=request.getParameter("iNumeroOficio")!=null?request.getParameter("iNumeroOficio"):"" %>"/>
		<input type="hidden" name="seleccionTamanioPapel" value="<%=request.getParameter("seleccionTamanioPapel")!=null?request.getParameter("seleccionTamanioPapel"):"" %>"/>
		<input type="hidden" name="audienciaId" value="<%=request.getParameter("idAudiencia")!=null?request.getParameter("idAudiencia"):"" %>"/>					                               
		<input type="hidden" name="nuevaActividad" value="<%=request.getParameter("nuevaActividad")!=null?request.getParameter("nuevaActividad"):""%>"/>
	</form>
	
	
	
</body>
<script type="text/javascript">
$( "#dialog-confirmSeleccionarDestinatario" ).dialog();
$( "#dialog-confirmSeleccionarDestinatario" ).dialog( "destroy" );
</script>
</html>