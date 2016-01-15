<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Designar Perito</title>

	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.weekcalendar.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/demo.css" />
		
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/date.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.weekcalendar.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/demo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>

	<!--css para ventanas-->

	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
	
	<script type="text/javascript">

	//Variable que controla si el grid se carga de peritos por primera vez
	var firstGridPeritos = true;
	var primeraVezGridDocumentosDigitales = true;
	var primeraVezGridDocumentosDigitalesNoPdfs = true;
	//Variable con el id de la solicitud pericial
	var solicitudPericialId;
	var estatus;
	var especialidadSolicitud;
	var area;
	var consulta;
	var solicitudHijaMayor=0;
	var numeroExpedienteId;
	
		$(document).ready(function() {
	
			//obtenemos el id de la audiencia
			solicitudPericialId=<%= request.getParameter("solicitudPericialId")%>;
			estatus = '<%=request.getParameter("estatus")%>';
			area = <%=request.getParameter("area")%>;
			consulta = '<%=request.getParameter("consulta")%>';
			
			//Se crean las tabs principales
			$("#tabsprincipalconsulta" ).tabs();
			//Se inicializan los campos
			busquedaEstadoInicial();
			//Se agrega el escuchador de eventos al combo de tipo de consulta
			$("#cbxTipoDeConsulta").change(verificaSeleccion);
			$("#btnPeritoExterno").click(muestraPeritoExterno);
			//Consulta el catalogo de instituciones
			consultarCatalogoDeInstituciones();
			//Consulta el catalogo de especialidad pericial
			consultarCatalogoDeEspecialidadPericial();
			//Obtiene parametros inicialmente VACIOS
			obtenerParametrosYcargarGridPeritos();
			//Carga los datos del usuario loggeado
			cargaDatosDelUsuario();
			consultaDetalleSolicitudPericial(solicitudPericialId);

			cargaDatosSolicitud(solicitudPericialId);

			cargaGridEvidencias(solicitudPericialId);

			$("#guardarListaPeritos").hide();
			//$("#btnGenerarOficio").hide();
			$('#divCkeditor').hide();

			$('#cbxTipoDeConsulta').find("option[value='especialidad']").attr("selected","selected");
			setTimeout('verificaSeleccion()',3000);

			if(area == 2){
				$('#cbxTipoDeConsulta').append('<option value="externo">Servicios Periciales Procuradur&iacute;a</option>');
			}
			
			//if (solicitudHijaMayor > 0){
				//$("#visorDocsFrame").attr("src","<%= request.getContextPath()%>/ConsultarDictamenDeSolicitudPericial.do?documentoId="+solicitudHijaMayor+"&inFrame=true");
			//}
			
			if (consulta != 'null' && consulta != undefined ){
				//$("#tabsprincipalconsulta").tabs('disable',2);
				$('#liDesignacionPerito').hide();
			}

		});
		//FIN ON READY
		
/////////////////////////////////////////////////////////COMIENZA FUNCIONALIDAD DEL LA CEJA SOLICITANTE //////////////////////////////////////////////////
	
	/*
	 * Genera ventana para solicitud de perito externo 
	 *
	 */
	function muestraPeritoExterno()
	{
		$.newWindow({id:"iframewindowPeritoExterno", statusBar: true, posx:200,posy:50,width:900,height:400,title:"Solicitud de Perito Externo ", type:"iframe"});
	    $.updateWindowContent("iframewindowPeritoExterno",'<iframe src="<%=request.getContextPath()%>/solicitarPeritoExterno.do?solicitudPericialId='+<%= request.getParameter("solicitudPericialId")%>+'" width="900" height="400" />'); 
	}
	
	/*
	 * Funcion que consulta el detalle de la solicitud pericial, para 
	 *obtener la fecha de solicitud y el estatus de la solicitud
	 */
	function consultaDetalleSolicitudPericial(solicitudPericialId){
		
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/consultaDetalleSolicitudPericial.do?solicitudPericialId='+solicitudPericialId+'',
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){

    			var errorCode;
				errorCode=$(xml).find('response').find('code').text();
							
				if(parseInt(errorCode)==0){	
					 fechaSol = $(xml).find('fechaCreacion ').first().text();
					 var fechaPos1=fechaSol.indexOf(":", 0);
					 fechaReal=fechaSol.substring(0, fechaPos1-2);
					//Fecha de la solicitud
					 $('#fechaSolicitudDesignarPerito').val(fechaReal);
					 if (consulta != 'null' && consulta != undefined ){
						 $(xml).find('solicitudesHijas').each(function(){
							 var docActual = $(this).find('documentoId').text();
							 if (parseInt(docActual) > solicitudHijaMayor){
								 solicitudHijaMayor = docActual;
							 }
						 });
					 }					
    			}
    		}
    	});	
	}

	/*Funcion que dispara el Action para consultar los datos de usuario*/
    function cargaDatosDelUsuario(){
 	
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/consultarDatosUsuario.do',
    		data: '',
    		dataType: 'xml',
    		async: false,
    		success: function(xml){

    			var errorCode;
				errorCode=$(xml).find('response').find('code').text();				
				if(parseInt(errorCode)==0){	

					//Nombre del funcionario
					$('#nombreFuncionarioDesignarPerito').val(
							$(xml).find('usuarioDTO').find('funcionario').find('nombreFuncionario').first().text() + " " +
							$(xml).find('usuarioDTO').find('funcionario').find('apellidoPaternoFuncionario').first().text() + " "+
							$(xml).find('usuarioDTO').find('funcionario').find('apellidoMaternoFuncionario').first().text()
						);
					//Puesto del funcionario
					$('#puestoFuncionarioDesignarPerito').val($(xml).find('usuarioDTO').find('funcionario').find('puesto').find('valor').first().text() );
					//Area administrativa
					$('#areaFuncionarioDesignarPerito').val( $(xml).find('usuarioDTO').find('areaActual').find('nombre').first().text()); 		
					//Fecha de la solicitud
					$('#fechaSolicitudDesignarPerito').val();
					
					
    			}
    		}
    	});
    }

    
/////////////////////////////////////////////////////////TERMINA FUNCIONALIDAD DEL LA CEJA SOLICITANTE //////////////////////////////////////////////////
		
/////////////////////////////////////////////////////////COMIENZA FUNCIONALIDAD DEL LA CEJA PERITO //////////////////////////////////////////////////
		
	/**Funcion que consulta el catalogo de instituciones*/
	function consultarCatalogoDeInstituciones(){
	
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoInstituciones.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('instituciones').each(function(){
					$('#cbxInstitucionPerito').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					});
			}
		});
	}

	/**Funcion que consulta el catalogo de instituciones*/
	function consultarCatalogoDeEspecialidadPericial(){
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoEspecialidadPericial.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){			
				$(xml).find('catEspecialidadPericial').each(function(){
					$('#cbxEspecialidadPerito').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}
		
	/*
	*Funcion llama a ocultar los campos dependiendo de la seleccion del 
	*cbx de tipo de consulta
	*/
	function verificaSeleccion(){
		var selected = $("#cbxTipoDeConsulta option:selected");

		if(selected.val() == "seleccione" || selected.val() == "externo"){
			busquedaEstadoInicial();
			limpiarTodo("todo");
		}
		
		if(selected.val() == "nombre"){
			busquedaNombrePerito();
			limpiarTodo("institucion");
			limpiarTodo("especialidad");
		}

		if(selected.val() == "institucion"){
			busquedaInstitucionPerito();
			limpiarTodo("nombre");
			limpiarTodo("especialidad");
		}

		if(selected.val() == "especialidad"){

			//setTimeout('busquedaEspecialidadPerito()',3000);
			busquedaEspecialidadPerito();
			limpiarTodo("nombre");
			limpiarTodo("institucion");

			$('#cbxEspecialidadPerito').find("option[value='"+especialidadSolicitud+"']").attr("selected","selected");
			setTimeout('obtenerParametrosYcargarGridPeritos()',3000);
		}
	}

		
	/*
	*Funcion que oculta todos los campos hasta 
	*que se seleccione una opcion en el combo
	*/
	function busquedaEstadoInicial(){
		//Nombre
		$('#divNombrePeritoTag').hide();
		$('#divNombrePeritoTxtField').hide();
		//Apellido paterno
		$('#divApellidoPaternoTag').hide();
		$('#divTxtFieldApPatPerito').hide();
		//Apellido materno
		$('#divApellidoMaternoTag').hide();
		$('#divTxtFieldApMatPerito').hide();
		//Boton buscar por nombre
		$('#divButtonBuscarPerito').hide();
		
		//Institucion
		$('#divInstitucionPeritoTag').hide();
		$('#divInstitucionPeritoCbx').hide();
		//Especialidad
		$('#divEspecialidadPeritoTag').hide();
		$('#divEspecialidadPeritoCbx').hide();
	}

		
	/*
	*Funcion que solo muestra los campos para ingresar
	*la busqueda por nombre
	*/
	function busquedaNombrePerito(){

		busquedaEstadoInicial();

		//Nombre			
		$('#divNombrePeritoTag').show();
		$('#divNombrePeritoTxtField').show();
		//Apellido paterno
		$('#divApellidoPaternoTag').show();
		$('#divTxtFieldApPatPerito').show();
		//Apellido materno
		$('#divApellidoMaternoTag').show();
		$('#divTxtFieldApMatPerito').show();
		//Boton buscar por nombre
		$('#divButtonBuscarPerito').show();				
	}

		
	/*
	*Funcion que solo muestra los campos para ingresar
	*la busqueda por institucion
	*/
	function busquedaInstitucionPerito(){

		busquedaEstadoInicial();
		//Institucion
		$('#divInstitucionPeritoTag').show();
		$('#divInstitucionPeritoCbx').show();
		//Boton buscar por nombre
		$('#divButtonBuscarPerito').show();
		
	}
	

	/*
	*Funcion que solo muestra los campos para ingresar
	*la busqueda por institucion
	*/
	function busquedaEspecialidadPerito(){

		busquedaEstadoInicial();
		//Especialidad
		$('#divEspecialidadPeritoTag').show();
		$('#divEspecialidadPeritoCbx').show();
		//Boton buscar por nombre
		$('#divButtonBuscarPerito').show();
		
		
	}
	
	/**Funcion que limpia todos los campos**/
	function limpiarTodo(limpiaObjeto){

		//Limpia el grid de peritos
		cargaGridPeritos("","","","","");
		
		if(limpiaObjeto == "todo"){	
			$('#txtFieldNombrePerito').val("");
			$('#txtFieldApPatPerito').val("");
			$('#txtFieldApMatPerito').val("");
			$("#cbxInstitucionPerito").attr('selectedIndex', 0);
			$("#cbxEspecialidadPerito").attr('selectedIndex', 0);
			
		}
		if(limpiaObjeto == "nombre"){	
			$('#txtFieldNombrePerito').val("");
			$('#txtFieldApPatPerito').val("");
			$('#txtFieldApMatPerito').val("");
		}
		if(limpiaObjeto == "institucion"){	
			$("#cbxInstitucionPerito").attr('selectedIndex', 0);
		}
		if(limpiaObjeto == "especialidad"){
			$("#cbxEspecialidadPerito").attr('selectedIndex', 0);
		}
	}		

	/**Funcion que obtiene los parametros que se envian, para realizar la busqueda*/
	function obtenerParametrosYcargarGridPeritos(){
		var nombre = $('#txtFieldNombrePerito').val();
		var apPat =  $('#txtFieldApPatPerito').val();
		var apMat = $('#txtFieldApMatPerito').val();
		var institucion = $("#cbxInstitucionPerito option:selected").val();
		var especialidad = $("#cbxEspecialidadPerito option:selected").val();

		var isExterno = false;
		
		cargaGridPeritos(nombre,apPat,apMat,institucion,especialidad,area,isExterno);			
	}


	/*
	*Funcion que carga el grid con las solicitudes periciales no atendidas
	*/
	function cargaGridPeritos(nombre,apPat,apMat,institucion,especialidad,area,isExterno){
		if(firstGridPeritos == true){
			
			jQuery("#gridPerito").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarPeritosADesignar.do?nombre='+nombre+'&apellidoPaterno='+apPat+'&apellidoMaterno='+apMat+'&institucion='+institucion+'&especialidad='+especialidad+'+&area='+area+'+&isExterno='+isExterno+'', 
				datatype: "xml", 
				colNames:['Nombre','Especialidad','Instituci&oacute;n','Asignaciones' ], 
				colModel:[ 	{name:'Nombre',index:'nombre', width:200},
				           	{name:'Especialidad',index:'especialidad', width:200},
				           	{name:'Institucion',index:'instituciozn', width:200},
				           	{name:'Asignaciones',index:'asignaciones', width:120}
						],
				pager: jQuery('#pagerGridPerito'),
				rowNum:10,
				rowList:[10,20,30],
				//autowidth: true,
				//autoheight:true,
				width:810,
				height:240,
				sortname: 'nombre',
				viewrecords: true,
				sortorder: "desc",
				onSelectRow: function(rowid){
						$("#guardarListaPeritos").show();
					//$("#btnGenerarOficio").show();
				}
			}).navGrid('#pagerGridPerito',{edit:false,add:false,del:false});

			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridPeritos=false;
		}
		else{
			jQuery("#gridPerito").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarPeritosADesignar.do?nombre='+nombre+'&apellidoPaterno='+apPat+'&apellidoMaterno='+apMat+'&institucion='+institucion+'&especialidad='+especialidad+'',datatype: "xml" });
			$("#gridPerito").trigger("reloadGrid");			
		}
	}
	
/////////////////////////////////////////////////////////TERMINA FUNCIONALIDAD DEL LA CEJA PERITO //////////////////////////////////////////////////
		
function cargaDatosSolicitud(solicitudId){
	$.ajax({
    	  type: 'POST',
    	  url:  '<%= request.getContextPath()%>/consultaDetalleSolicitudPericial.do',
    	  data: 'solicitudPericialId='+solicitudId,
    	  dataType: 'xml',
    	  async: false,
    	  success: function(xml){
    		  pintaDatosSolicitud(xml);
		  }
    });
}

function pintaDatosSolicitud(xml){
	if($(xml).find('usuarioSolicitante') != null){
   		$('#solDePericialQuienSolicita').val($(xml).find('usuarioSolicitante').find('nombreFuncionario').first().text()+ ' ' +
		$(xml).find('usuarioSolicitante').find('apellidoPaternoFuncionario').first().text()+ ' ' +
		$(xml).find('usuarioSolicitante').find('apellidoMaternoFuncionario').first().text());
    }
    if($(xml).find('usuarioSolicitante') != null){
	   	$('#solDePericialEspecialidad').val($(xml).find('usuarioSolicitante').find('puesto').find('valor').first().text());
    }
    if($(xml).find('motivo') != null){
	    $('#solDePericialMotivo').val($(xml).find('motivo').text());
    }
    if($(xml).find('fechaLimite') != null){
   		$('#solDePericialFechaLimite').val($(xml).find('fechaLimiteStr').first().text());
    }
    if($(xml).find('observaciones') != null){
	   $('#areaDescripcion').val($(xml).find('observaciones').text());
    }
    if($(xml).find('especialidad') != null){
    	especialidadSolicitud = $(xml).find('especialidad').find('idCampo').last().text();
 	}
    if($(xml).find('numeroExpediente') != null){
    	$('#solDePericialNumeroExpediente').val($(xml).find('numeroExpediente').first().text());
 	}
    if($(xml).find('folioSolicitud') != null){
    	$('#solDePericialNumeroFolio').val($(xml).find('folioSolicitud').first().text());
 	}
    numeroExpedienteId=$(xml).find('numeroExpedienteId').first().text();

}

//Envia la solicitud bas&aacute;ndose en solicitudId y el id del perito seleccionado	
function enviarSolicitudDesignarPerito(){
	//Obtenemos el id del perito seleccionado. 
	var row = jQuery("#gridPerito").jqGrid('getGridParam','selrow');

	if(row){
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/enviarSolicitudDesignarPerito.do?solicitudPericialId='+solicitudPericialId+'',
			data: 'peritoId='+row,
			dataType: 'xml',
			async: false,
			success: function(xml){
				parent.cargaGridSolicitudesDeDictamenNoAtendidas();
				customAlert("Asignaci&oacute;n enviada con &eacute;xito","", function(){
					parent.cerrarVisorDesignarPerito();		
						
				});
						
			}
		});
	}else{
		alert("Debe designar un perito");		
	}	
}

function cargaGridEvidencias(solicitudId){
	jQuery("#gridDetalleCadenaCustodia").jqGrid({ 
		url:'<%= request.getContextPath()%>/consultarEvidenciasSolicitud.do?solicitudId='+solicitudId+'',
		data:'',
		datatype: "xml", 
		colNames:['N&uacute;mero de Evidencia','Cadena de Custodia','Objeto'/*,'C&oacute;digo de Barras'*/], 
		colModel:[ 	{name:'NumeroEvidencia',index:'numeroEvidencia', width:150},
		           	{name:'CadenaCustodia',index:'cadenaCustodia', width:150},
		           	{name:'Objeto',index:'objeto', width:150}/*,
		           	{name:'CodigoBarras',index:'codigoBarras', width:150}*/
				],
		pager: jQuery('#pagerCadenaCustodia'),
		rowNum:10,
		rowList:[10,20,30],
		autowidth: true,
		sortname: 'CadenaCustodia',
		viewrecords: true,
		sortorder: "desc",
		multiselect: false
		}).navGrid('#pagerCadenaCustodia',{edit:false,add:false,del:false});
}


function validaExterno(obj){
	if(obj.value == 'externo'){
		$('#divPerito').hide();
		$('#divCkeditor').show();	
		$("#guardarListaPeritos").hide();			
	}else{
		$('#divPerito').show();
		$('#divCkeditor').hide();
	}
}

function cargaGridDocumentosDigitales(){

	if(primeraVezGridDocumentosDigitales == true){
		jQuery("#gridDocumentosDigitales").jqGrid({
			url:'<%=request.getContextPath()%>/consultarDocumentosPropiosAsociadosASolicitudPericial.do?solicitudId='+solicitudPericialId+'&esPdf=true',
			datatype: "xml", 
			colNames:['&Aacute;rea del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Nombre de Documento','Fecha del documento'],
			colModel:[ 	{name:'area',index:'area', width:200,hidden:true},
						{name:'FechaActividad',index:'fechaActividad', width:170,hidden:true},							
						{name:'NombreActividad',index:'nombreActividad', width:400,hidden:true},
			           	{name:'Tipo',index:'tipo', width:155,hidden:true}, 
						{name:'Nombre',index:'nombre', width:255},
			           	{name:'Fecha',index:'fecha', width:170,hidden:true}
						],
			pager: jQuery('#pagerGridDocumentosDigitales'),
			rowNum:20,
			rowList:[10,20,30],
			width:250,
			sortname: 'nombreDocumento',
			viewrecords: true,
			sortorder: "desc",
			//multiselect:true,
			ondblClickRow: function(rowid) {
				if (rowid) {
					var retd = jQuery("#gridDocumentosDigitales").jqGrid('getRowData',rowid);
					var tipo = retd.Tipo;
					if(tipo != (".pdf")){
						consultaArchivo(rowid);
					}else{
						abrirDocsDigAsociadosASol(rowid);
					}
												
				}
			}
		}).navGrid('#pagerGridDocumentosDigitales',{edit:false,add:false,del:false});
		$("#gview_gridDocumentosDigitales .ui-jqgrid-bdiv").css('height', '227px');
		
		primeraVezGridDocumentosDigitales= false;
	}
	else{
		jQuery("#gridDocumentosDigitales").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDocumentosPropiosAsociadosASolicitudPericial.do?solicitudId='+solicitudPericialId+'&esPdf=true',datatype: "xml" });
		$("#gridDocumentosDigitales").trigger("reloadGrid");
	}
}

function cargaGridDocumentosDigitalesNoPdfs(){

	if(primeraVezGridDocumentosDigitalesNoPdfs == true){
		jQuery("#gridDocumentosDigitalesNoPdfs").jqGrid({
			url:'<%=request.getContextPath()%>/consultarDocumentosPropiosAsociadosASolicitudPericial.do?solicitudId='+solicitudPericialId+'',
			datatype: "xml", 
			colNames:['&Aacute;rea del responsable','Fecha de la actividad','Nombre de la actividad','Tipo de documento','Otros Documentos','Fecha del documento','Tipo de documento'],
			colModel:[ 	{name:'area',index:'area', width:200,hidden:true},
						{name:'FechaActividad',index:'fechaActividad', width:170,hidden:true},							
						{name:'NombreActividad',index:'nombreActividad', width:400,hidden:true},
			           	{name:'Tipo',index:'tipo', width:155,hidden:true}, 
						{name:'Nombre',index:'nombre', width:255},
			           	{name:'Fecha',index:'fecha', width:170,hidden:true},
						{name:'TipoDoc',index:'tipoDoc', width:170,hidden:true}
						],
			pager: jQuery('#pagerGridDocumentosDigitalesNoPdfs'),
			rowNum:20,
			rowList:[10,20,30],
			width:250,
			sortname: 'nombreDocumento',
			viewrecords: true,
			sortorder: "desc",
			//multiselect:true,
			ondblClickRow: function(rowid) {
				if (rowid) {
					var retd = jQuery("#gridDocumentosDigitalesNoPdfs").jqGrid('getRowData',rowid);
					var tipo = retd.Tipo;
					var titulo = retd.Nombre;
					if(tipo != (".pdf")){
						if(retd.TipoDoc.indexOf('Archivo de audio') > 0){
							abreFrameAudio(rowid,titulo,'archivoDigitalId');
						}else{
							consultaArchivo(rowid);
						}
					}else{
						abrirDocsDigAsociadosASol(rowid);
					}
												
				}
			}
		}).navGrid('#pagerGridDocumentosDigitalesNoPdfs',{edit:false,add:false,del:false});
		$("#gview_gridDocumentosDigitalesNoPdfs .ui-jqgrid-bdiv").css('height', '227px');
		
		primeraVezGridDocumentosDigitalesNoPdfs= false;
	}
	else{
		jQuery("#gridDocumentosDigitalesNoPdfs").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDocumentosPropiosAsociadosASolicitudPericial.do?solicitudId='+solicitudPericialId+'',datatype: "xml" });
		$("#gridDocumentosDigitalesNoPdfs").trigger("reloadGrid");
	}
}

/*
*Funcion que abre el PDF para ver los documentos asociados al numero de causa
*/
function abrirDocsDigAsociadosASol(documentoAsocId){
	if(documentoAsocId != null && documentoAsocId != ""){
		$("#visorDocsFrame").attr("src","<%= request.getContextPath()%>/consultarArchivoDigitalIframe.do?archivoDigitalId="+documentoAsocId+"&inFrame=true");
	}
	else{
		alert("El documento no puede ser mostrado");
	}
}

function consultaArchivo(id){
	document.frmArch.archivoDigitalId.value = id;
	document.frmArch.submit();
}

function cargaGridDocumentosDigitalesAmbos(){
	cargaGridDocumentosDigitales();
	cargaGridDocumentosDigitalesNoPdfs();
}

</script>
</head>

<body>
	<!--Comienzan tabs principales-->
	<div id="tabsprincipalconsulta">
		<ul>
			<li><a href="#tabsconsultaprincipal-1">Responsable</a>
			</li>
			<li><a href="#tabsconsultaprincipal-3">Evidencia</a>
			</li>
			<li id="liDesignacionPerito"><a href="#tabsconsultaprincipal-2">Perito</a>
			</li>
			<li id="liSolicitudes"><a href="#tabsconsultaprincipal-4" onclick="cargaGridDocumentosDigitalesAmbos()">Documentos asociados a la solicitud</a>
			</li>
		</ul>
		
		<!--Comienza tab solicitante-->
		<div id="tabsconsultaprincipal-1" align="left">
			<!--<fieldset style="border: 8px ridge #ff00ff; color: #ffffff; width: 700px;">
			<legend style="color: #ff0000; font-family: verdana; font-size: 14pt;">Responsable</legend>-->
			<fieldset style="width: 700px;">
			<legend>Responsable</legend>
			<table width="700" border="0" cellspacing="0" cellpadding="0">
			  <!--<tr>
			    <td width="250">&nbsp;</td>
			    <td width="200">&nbsp;</td>
			    <td width="450">&nbsp;</td>
			  </tr>-->
			  <tr>
			    <td align="right"><strong>Nombre del Servidor P&uacute;blico:</strong></td>
			    <td align="left"><input type="text" id="nombreFuncionarioDesignarPerito" style="width:250px; border: 0; background:#DDD;" disabled="disabled"/></td>
			    <td>&nbsp;</td>
			  </tr>
			  <tr>
			    <td align="right"><strong>Cargo:</strong></td>
			    <td align="left"><input type="text" id="puestoFuncionarioDesignarPerito" style="width:250px; border: 0; background:#DDD;" disabled="disabled"/></td>
			    <td>&nbsp;</td>
			  </tr>
			  <tr>
			    <td align="right"><strong>&Aacute;rea Administrativa:</strong></td>
			    <td align="left"><input type="text" id="areaFuncionarioDesignarPerito" style="width:250px; border: 0; background:#DDD;" disabled="disabled"/></td>
			    <td>&nbsp;</td>
			  </tr>
			  <tr>
			    <td align="right"><strong>Fecha de Solicitud:</strong></td>
			    <td align="left"><input type="text" id="fechaSolicitudDesignarPerito" style="width:250px; border: 0; background:#DDD;" disabled="disabled"/></td>
			    <td>&nbsp;</td>
			  </tr>
			  <!--<tr>
			    <td>&nbsp;</td>
			    <td>&nbsp;</td>
			    <td>&nbsp;</td>
			  </tr>-->
			</table>
			</fieldset>
			<br>
			<fieldset style="width: 700px;">
			<legend>Solicitud</legend>
			<table width="700" border="0">
				<tr>
					<td align="right">
						<strong>N&uacute;mero de Expediente:</strong>
					</td>
					<td align="left">
						<input type="text" class="" size="50" maxlength="50" id="solDePericialNumeroExpediente" style="width:250px; border: 0; background:#DDD;" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<strong>N&uacute;mero de Folio:</strong>
					</td>
					<td align="left">
						<input type="text" class="" size="50" maxlength="50" id="solDePericialNumeroFolio" style="width:250px; border: 0; background:#DDD;" disabled="disabled"/>
					</td>
				</tr>				
				<tr>
					<td align="right">
						<strong>Quien Solicita:</strong>
					</td>
					<td align="left">
						<input type="text" class="" size="50" maxlength="50" id="solDePericialQuienSolicita" style="width:250px; border: 0; background:#DDD;" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<strong>Cargo:</strong>
					</td>
					<td align="left">
						<input type="text" class="" size="50" maxlength="50" id="solDePericialEspecialidad" style="width:250px; border: 0; background:#DDD;" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<strong>Fecha l&iacute;mite:</strong>
					</td>
					<td align="left">
						<input type="text" class="" size="50" maxlength="50" id="solDePericialFechaLimite" style="width:250px; border: 0; background:#DDD;" disabled="disabled"/>
					</td>
				</tr>
			</table>			
			</fieldset>	
		</div>
		<!--Termina tab solicitante-->
		
		<!--Comienza tab perito-->
		<div id="tabsconsultaprincipal-2" align="left">
			
			<table width="1000" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		            <td><br></td>
		        </tr>
		        <tr>
		            <td align="right"><strong>Consulta por:</strong></td>
		            <td align="left" valign="middle">
		            	<select name="cbxTipoDeConsulta" id="cbxTipoDeConsulta" style="width: 180px" onchange="validaExterno(this);">
		                	<option value="seleccione">-Seleccione-</option>
		                	<option value="especialidad">Especialidad</option>	
							<option value="nombre">Nombre del perito</option>
		            	</select>
		            </td>
		            <td rowspan="6" align="left">
		            	<div id="divPerito">
			            	<table id="gridPerito"></table>
			            	<div id="pagerGridPerito"></div>
		            	</div>
		            	<div id="divCkeditor">
		            		<table width="750px" height="200px" border="0" cellspacing="0" cellpadding="0" id="tableHecho" class="back_hechos">
	    						<tr>
		    						<td align="center">
		    							<textarea class="jquery_ckeditor" cols="50" id="editor1" name="editor1" rows="10"></textarea>
		    						</td>
		    					</tr>
		    				</table>
		            	</div>
		            </td>
		        </tr>
		        <tr>
		            <td align="right">&nbsp;</td>
		            <td align="left" valign="middle">&nbsp;</td>
		        </tr>
		        <tr>
		            <td align="right">
		            	<div id="divNombrePeritoTag"><strong>Nombre:</strong></div>
		            	<div id="divInstitucionPeritoTag"><strong>Instituci&oacute;n:</strong></div>
		            	<div id="divEspecialidadPeritoTag"><strong>Especialidad:</strong></div>
		            </td>
		            <td align="left" valign="middle">
		            	<div id="divNombrePeritoTxtField"><input type="text" id="txtFieldNombrePerito" style="width: 180px;" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/></div>
		            	<div id="divInstitucionPeritoCbx">
		            		<select id="cbxInstitucionPerito" style="width: 180px">
		                		<option>-Seleccione-</option>
		            		</select>
		            	</div>
		            	<div id="divEspecialidadPeritoCbx">
		            		<select id="cbxEspecialidadPerito" style="width: 180px" onchange="obtenerParametrosYcargarGridPeritos()">
		                		<option>-Seleccione-</option>
		            		</select>
		            	</div>
		            </td>
		        </tr>
		        <tr>
		            <td align="right">
		            	<div id="divApellidoPaternoTag"><strong>Apellido paterno:</strong></div>
		            </td>
		            <td align="left" valign="middle">
		            	<div id="divTxtFieldApPatPerito">
		            		<input type="text" id="txtFieldApPatPerito" style="width: 180px;" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/>
		            	</div>
		            </td>
		        </tr>
		        <tr>
		            <td align="right">
		            	<div id="divApellidoMaternoTag"><strong>Apellido materno:</strong></div>
		            </td>
		            <td align="left" valign="middle">
		            	<div id="divTxtFieldApMatPerito">
		            		<input type="text" id="txtFieldApMatPerito" style="width: 180px" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/>
		            	</div>
		            </td>
		        </tr>
		        <tr>
		            <td align="right">&nbsp;</td>
		            <td align="left" valign="middle">
		            	<div id="divButtonBuscarPerito">
		            		<input type="button" id="buttonBuscarPeritoPorNombre" value="Buscar" class="ui-button ui-corner-all ui-widget" onclick="obtenerParametrosYcargarGridPeritos();">
		            	</div>
		            </td>
		        </tr>
		        <tr>
		            <td align="left">&nbsp;</td>
		            <td align="left">&nbsp;</td>
		            <td align="right">            
		            	<input type="button" id="guardarListaPeritos" value="Enviar Asignaci&oacute;n" class="ui-button ui-corner-all ui-widget" onclick="enviarSolicitudDesignarPerito();" />
		            	<!--   <input type="button" id="btnGenerarOficio" value="Generar Oficio" class="ui-button ui-corner-all ui-widget" /> -->	            
					</td>
		        </tr>
		        <!--tr>
		        	<td width="1" align="center" valign="top">
		        		<iframe id='visorDocsFrame' width="1" height="1" src="">
		        		</iframe>
		        	</td>
		        </tr-->
		    </table>	
									
		</div>
		<!--Termina tab perito-->
		
		<div id="tabsconsultaprincipal-3">
		<table>
			<tr>
			<td>
			<fieldset style="width: 500px;">
			<legend>Evidencias Solicitadas</legend>
			<table>
				<tr>
					<td width="100%">
						<table id="gridDetalleCadenaCustodia"></table>
						<div id="pagerCadenaCustodia"></div>
					</td>
				</tr>
			</table>
			</fieldset>
			</td>
			<td>
			<fieldset style="width: 500px;">
			<legend>Recomendaciones</legend>
			<table width="100%" border="0" height="90%">
				<tr>
		          <td>
		            <textarea id="areaDescripcion" cols="45" rows="5" style="width: 500px; height:200px;" disabled="disabled"></textarea>
	              </td>
		        </tr>
			</table>
			</fieldset>
			</td>
			</tr>
		</table>
		</div>
		
		
		<div id="tabsconsultaprincipal-4" align="left">
			<table width="1150"  height="530" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td width="250" align="center" valign="top">
	               <table width="250"  height="530" border="0" cellspacing="0" cellpadding="0">
	              	<tr>
	                	<td width="250" align="center" valign="top">
	                        <table id="gridDocumentosDigitales"></table>
	                       	<div id="pagerGridDocumentosDigitales"></div>
	                       </td>
	                   </tr>
	                   <tr>
	                       <td width="250" align="center" valign="top">
	                       	<table id="gridDocumentosDigitalesNoPdfs"></table>
	                       	<div id="pagerGridDocumentosDigitalesNoPdfs"></div>
	                       </td>
	                   </tr>
	               </table>
	           </td>
	              <td width="900" align="center" valign="top">
	             	  <iframe id='visorDocsFrame' width="900" height="500" src="">		               	  
	             	  </iframe>
	              </td>
	            </tr>
            </table>
            <form name="frmArch" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
				<input type="hidden" name="archivoDigitalId" />
			</form>
		</div>
		
	</div>
	<!--Terminan tabs principales-->
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
		height:'150px',
		width:'750px'
	};			
	$('.jquery_ckeditor').ckeditor(config);

</script>
</html>