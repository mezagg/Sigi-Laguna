<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Elaborar Solicitud</title>
	<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
	<!--iframe que crea una nueva peticion para imprimir un PDF-->
	<iframe id="framePdf" src="" width="0" height="0"></iframe>
	
	<!--		Hojas de estilos asociadas-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>	
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/treeview/jquery.treeview.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.treeview.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
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
	var numExpIdGlobal=0;
	var idWindowIngresarDenunciante = 1;
	var idWindowIngresarVictima = 1;
	var idWindowIngresarProbResponsable = 1;
	var idWindowIngresarTestigo = 1;
	var idWindowIngresarTraductor = 1;
	var idWindowIngresarQuienDetuvo = 1;
	
	var idTipoSolicitud=0;
	var idExpedienteOp="";
	var idRelacionDelito="";
	var documentoParcialGuardado="";
	var idFunDestinatario="";
	var idWindowPantallaActuaciones = '<%=request.getParameter("idWindowPantallaActuaciones")!=null?request.getParameter("idWindowPantallaActuaciones"):""%>';

	
		jQuery().ready(function () {
			$('#guardarNarraTiva').hide();
			cargaFechaCaptura();
			cargaHoraCaptura();
			$('#imprimirNarraTiva').click(crearPdf);
			$('#guardadoParcialNarrativa').click(guardadoParcial);
			$('#vistaPreliminar').click(elaborarVistaPreliminar);
			cargarDocumento();
			cargarDatosExpediente();
						
			//Cambios para la pantalla de seleccionar destinatario
			//cargaComboInstitucion();		//Funcion que carga el combo de las Instituciones
			//$('#instituciones').change(enSeleccionInstitucion);
			//cargaCompoDepartamentos();
			//$('#areas').change(cargaCompoFuncionarios);
			//$('#areas').change(cargaCompoDepartamentos);
			//$('#departamentos').change(cargaCompoFuncionarios);
			
			$('#seleccionarDestinatario').hide();
			$('#menuSeleccionarDestinatario').click($('#seleccionarDestinatario').show());
			$('#tblUsuarioExterno').hide();
			$('#tblUsuarioSistema').hide();
			$('#divGridUsuariosExt').hide();
			//$('#divGridUsuarios').hide();
			//cargaGridUsuarios();
			//cargaGridUsuariosExternos();
			//$('#relacionarElementos').click(abreVentanaRelacionarElementos);
		    var numeroExpedienteId=<%= request.getParameter("numeroExpedienteId")%>;
		    idTipoSolicitud=<%= request.getParameter("idTipoSolicitud")%>;
		    idRelacionDelito='<%= request.getParameter("idRelacionDelito")%>';
			numExpIdGlobal=numeroExpedienteId;
			llenaGridUsuarios();
			
			cargaDatosEncabezado();
			consultarTipoTamanioPapel();
		});
	
		function llenaGridUsuarios()
		{
			jQuery("#gridUsuarios").jqGrid({
				url:'<%= request.getContextPath()%>/consultarDestinatarioAtencionPsicologicaUAVD.do', 
				datatype: "xml",
				height: 110,
				colNames:['ID','Nombre','Puesto', 'Correo','Principal','Copia','AreaId'],
				colModel:[	
						  {name:'id',index:'id', width:60, sorttype:"int",hidden:true},
				          {name:'nombre',index:'nombre', width:200},
				          {name:'puesto',index:'puesto', width:200},
				          {name:'direccion',index:'direccion', width:200},
				          {name:'principal',index:'principal', width:60},
				          {name:'copia',index:'copia', width:60},
				          {name:'areaId',index:'areaId', width:60,hidden:true}
			    ],
			    rowNum:5,
				autowidth: false,
				rowList:[5,10,15,20,25,50],
				pager: jQuery('#pager1'),
				viewrecords: true,								
				multiselect: false,
				hiddengrid: false,
			    caption: "Destinatario(s) Usuarios del sistema",
			    onSelectRow: function(id){
					idFunDestinatario=id;
				},
		    });
		}
		
		
		function guardadoParcial(){
			if(!validaDocumentos()){
				return;
			}
			var recuperaTexto=$('.jquery_ckeditor').val();
			var nuevaActividad='<%=request.getParameter("nuevaActividad")%>';
			var numeroOficio=$('#iNumeroOficio').val();
			var documentoIdParcial='<%=request.getParameter("documentoId")%>';
			if(documentoIdParcial=="" || documentoIdParcial==null || documentoIdParcial=="null"){
				documentoIdParcial=documentoParcialGuardado;
			}
			if(documentoIdParcial==null || documentoIdParcial=="null" || documentoIdParcial==""){documentoIdParcial=="";}

			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/GenerarDocumento.do',
		    	data: 'parcial=true&formaId=<%=request.getParameter("formaId") %>&numeroUnicoExpediente='+numeroUnicoExpediente+
		    	'&documentoId='+documentoIdParcial+'&texto='+escape(recuperaTexto)+
		    	'&iNumeroOficio='+numeroOficio+
		    	'&nuevaActividad='+nuevaActividad+
		    	'&seleccionTamanioPapel='+seleccionTamPapel,
		    	dataType: 'xml',
		    	success: function(xml){
		    		var valorNumDocParcial = $(xml).find('long').first().text();
	    			if(valorNumDocParcial == null || valorNumDocParcial == "" || valorNumDocParcial == "null"){
	    				valorNumDocParcial=$(xml).find('response').find('body').find('horaActual').text();
	    			}
	    			if(valorNumDocParcial == null || valorNumDocParcial == "" || valorNumDocParcial == "null"){
	    				valorNumDocParcial=$(xml).find('response').find('body').find('fechaActual').text();
	    			}	    			
	    			if(valorNumDocParcial == ""){
	    				valorNumDocParcial = $(xml).find('response').find('body').find('string').text();
	    			}
	    			
	    		   var idsDocParcial = valorNumDocParcial.split(',');

	    		   if(idsDocParcial.length==2){
	    			   $('#iNumeroOficio').val(idsDocParcial[1]);
	    			   documentoParcialGuardado=idsDocParcial[0];
	    			}

	    		   try{window.parent.documentos();}catch(e){}
	    		},
		    	error: function(xml){
		    	}
			});
			validadDatosSolicitud();
		}
		function cargarDocumento(){
			var mandaFormaEnConsulta="si";
			$.ajax({
		    	type: 'POST',
		    	url: '<%=request.getContextPath()%>/CargarDocumento.do?idAudiencia='+idAudiencia+'&mandaFormaEnConsulta='+mandaFormaEnConsulta+'&idResolutivo='+idResolutivo+'',
		    	data: 'formaId=<%=request.getParameter("formaId")%>&numeroUnicoExpediente='+numeroUnicoExpediente+
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
			if(validadDatosSolicitud() == 1){
				if(!validaDocumentos()){
					return;
				}
				var recuperaTexto=$('.jquery_ckeditor').val();
				var idSolicitudVar=$('#idSolicitud').val();
				document.frmDoc.parcial.value = "";
				document.frmDoc.texto.value = recuperaTexto;
				document.frmDoc.seleccionTamanioPapel.value = seleccionTamPapel;
				document.frmDoc.action ="<%= request.getContextPath() %>/GenerarDocumentoNotificacionAyudaPsicologicaUAVD.do";
				//separamos los ids
				var idsRelacionDelito = idRelacionDelito.split("_");
				document.frmDoc.idRelDelito.value = idsRelacionDelito[0];//id Relacion-Delito
				document.frmDoc.idPR.value = idsRelacionDelito[1];//id probable responsable
				document.frmDoc.idVictima.value = idsRelacionDelito[2];//id victima
				document.frmDoc.idDelito.value = idsRelacionDelito[3];//id Delito
				document.frmDoc.idSolicitudVa.value = idSolicitudVar;//id TipoSolicitud
				document.frmDoc.idFunDestinatario.value = idFunDestinatario;//idFunDestinatario
				if(document.frmDoc.documentoId.value==""){
					document.frmDoc.documentoId.value = documentoParcialGuardado;
					if(document.frmDoc.documentoId.value == null || document.frmDoc.documentoId.value == "null"){
						document.frmDoc.documentoId.value = "";
					}
				}
				document.frmDoc.submit();
			}
		}
		function validadDatosSolicitud(){
			if(idFunDestinatario!="" && idFunDestinatario.length>0 ){
				enviaDatosSolicitud()
				return 1;
			}else{
				customAlert('Debe de seleccionar un destinatario');
				return 0;
			}					
		}
		
		function enviaDatosSolicitud(){
			if(idTipoSolicitud==null){
				idTipoSolicitud=<%= TiposSolicitudes.ATENCION_PSICOLOGICA.getValorId() %>;
			}
	    	var params = '&institucionSolicitante=' + 1;
		    params += '&solicitante=' + "";
		    params += '&numeroExpediente=' + numeroUnicoExpediente;
		    params += '&idsFuncionariosSolicitantes=' + obtenIdsDestinatariosInternos();
		    params += '&idSolicitud=' + $('#idSolicitud').val();
		    params += '&idTipoSolicitud=' + idTipoSolicitud;
		    params += '&areaDestino=' + obtenerAreaDestino();
		    
		    try{
				$('#imprimirNarraTiva').unbind();
			}catch(e){};
		    
	    $.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/registrarSolicitud.do',
			data: params, 
			async: false,
			dataType: 'xml',
			success: function(xml){										
				 if(parseInt($(xml).find('SolicitudDTO').find('documentoId').text())>0){
					 if($('#idSolicitud').val() == 0)
						 customAlert("La solicitud se envi\u00F3 correctamente");
					 else
						 customAlert("La solicitud se actualiz\u00F3 correctamente");
					 $('#idSolicitud').val(parseInt($(xml).find('SolicitudDTO').find('documentoId').text()));
				  }else
					  customAlert('Error al intentar guardar la solictud, int&eacute;ntelo mas tarde');
			},
			error: function(xml){
				//Guardado Definitivo
				try{
					$('#imprimirNarraTiva').click(crearPdf);
				}catch(e){};
			}
		});			   		
	}
		/** 
		* Esta funcion es utilizada cuando se tiene la posibilidad 
		* de enviar a mas de un destinatario
		*/ 
		function obtenIdsDestinatariosInternos(){
			return idFunDestinatario;
			/* var ids = "";
			var lista = jQuery("#gridUsuarios").getDataIDs();
			for(i=0;i<lista.length;i++){
					rowData= jQuery("#gridUsuarios").jqGrid('getRowData',lista[i]);
					ids = ids + rowData.id + ","; 
			 }
			return ids; */			
		}

		function obtenerAreaDestino(){
			
			var areaDestino = "";
			var lista = jQuery("#gridUsuarios").getDataIDs();
			
			for(i=0;i<lista.length;i++){
				
				rowData= jQuery("#gridUsuarios").jqGrid('getRowData',lista[i]);
				
				if(rowData){
					areaDestino =  rowData.areaId;
				}
				break;
			 }
			 
			return areaDestino; 
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
		
		</script>
</head>



<body>
	<!-- ETIQUETAS NECESARIAS PARA LOS CAMPOS DEL ENCABEZADO -->
	<table align="center" border="0" width="820px" height="50%">
		<tr><!-- MENU -->
			<td colspan="4">
				<ul class="toolbar">
					<div id="menu_head">
                		<!-- <li id="menuSeleccionarDestinatario"  onclick="muestraPopupSeleccionarDestinatario()">Seleccionar Destinatario</li> -->
                        <!-- <li id="agregarElemento">Agregar Elemento</li> -->
                        <!-- <li id="relacionarElementos">Relacionar Elementos</li> -->
                        <!-- <li id="consultarDocumentosDelExpediente">Consultar Documentos del Expediente</li> -->
						<!-- <li id="guardadoParcialNarrativa" class="first">Guardado Parcial</li> -->
						<li id="imprimirNarraTiva"><span></span>Enviar</li>
						<li id="vistaPreliminar"><span></span>Vista Preliminar</li>
						<li id="seccionCbxTamanioPapel"><span></span>Tama&ntilde;o de Papel
							<select name="cbxTamanioPapel" id="cbxTamanioPapel" onchange="recuperarTamanioPapel()" style=" border:0; background-color:#EEEEEE;">
		    				</select>
		    			</li>						
						<!-- <li id="tbarBtnConsultarTurnoAtencion" class="first">Salir</li> -->
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
			<td width=""><input type="text" title="Numero de Oficio" size="40" id="iNumeroOficio"  onkeypress="return letrasNumero(event);" maxlength="20"/></td>
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
				<input type="hidden" size="20px" id="idSolicitud" value="0"/>
				</div>
				<form name="frmDoc" action="<%= request.getContextPath() %>/GenerarDocumentoNotificacionAyudaPsicologicaUAVD.do" method="post">
					<input type="hidden" name="texto" value=""/>
					<input type="hidden" name="parcial" value=""/>
					<input type="hidden" name="formaId" value="<%=request.getParameter("formaId")!=null?request.getParameter("formaId"):"" %>"/>
					<input type="hidden" name="numeroUnicoExpediente" value="<%=request.getParameter("numeroUnicoExpediente")!=null?request.getParameter("numeroUnicoExpediente"):"" %>"/>
					<input type="hidden" name="documentoId" value="<%=request.getParameter("documentoId")!=null?request.getParameter("documentoId"):"" %>"/>
					<input type="hidden" name="tipoOperacion" value="<%=request.getParameter("tipoOperacion")!=null?request.getParameter("tipoOperacion"):"" %>"/>
					<input type="hidden" name="estatusSolicitud" value="<%=request.getParameter("estatusSolicitud")!=null?request.getParameter("estatusSolicitud"):"" %>"/>
					<input type="hidden" name="idResolutivo" value="<%=request.getParameter("idResolutivo")!=null?request.getParameter("idResolutivo"):"" %>"/>
					<input type="hidden" name="seleccionTamanioPapel" value="<%=request.getParameter("seleccionTamanioPapel")!=null?request.getParameter("seleccionTamanioPapel"):"" %>"/>
					<!-- Campos para los ids de la relacion de delito -->
					<input type="hidden" name="idRelDelito" value=""/>
					<input type="hidden" name="idPR" value=""/>
					<input type="hidden" name="idVictima" value=""/>
					<input type="hidden" name="idDelito" value=""/>
					<input type="hidden" name="idSolicitudVa" value=""/>
					<input type="hidden" name="nuevaActividad" value="<%=request.getParameter("nuevaActividad")!=null?request.getParameter("nuevaActividad"):""%>"/>
					<input type="hidden" name="idFunDestinatario" value=""/>
				</form>				
			</td>
		</tr>
	</table>
</body>

</html>