<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Reasignar Solicitud Pericial</title>

		
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>

	<!--css-->
	<!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" /-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/south-street/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	
	
	<script type="text/javascript">

	var contextoPagina = "${pageContext.request.contextPath}";
	//Variable que controla si el grid se carga de peritos por primera vez
	var firstGridPeritos = true;
	//Variable con el id de la solicitud pericial
	var especialidadSolicitud;
	
		$(document).ready(function() {
	
			//Se crean las tabs principales
			$("#tabsprincipalconsulta" ).tabs();
			//Se inicializan los campos
			busquedaEstadoInicial();
			//Se agrega el escuchador de eventos al combo de tipo de consulta
			$("#cbxTipoDeConsulta").change(verificaSeleccion);
			//Consulta el catalogo de especialidad pericial
			consultarCatalogoDeEspecialidadPericial();
			//Obtiene parametros inicialmente VACIOS
			obtenerParametrosYcargarGridPeritos();
			//$('#cbxTipoDeConsulta').find("option[value='especialidad']").attr("selected","selected");
			$("#btnReasignar").hide();
			jQuery("#gridSolicitudes").jqGrid({ 
				datatype: "xml", 
				colNames:['Folio','Funcionario'], 
				colModel:[ 	{name:'Folio',index:'folio', width:150},
				           	{name:'Funcionario',index:'funcionario', width:150}
						],
				pager: jQuery('#pagerGridSolicitudes'),
				rowNum:10,
				rowList:[10,20,30],
				width:810,
				height:240,
				//autowidth: true,
				sortname: 'Folio',
				viewrecords: true,
				sortorder: "desc",
				multiselect: false
				}).navGrid('#pagerGridSolicitudes',{edit:false,add:false,del:false});

		});
		//FIN ON READY
		
/////////////////////////////////////////////////////////COMIENZA FUNCIONALIDAD DEL LA CEJA SOLICITANTE //////////////////////////////////////////////////
	
function cargaGridSolicitudes(){
			
	var folioSolicitud = $('#folioSolicitud').val();
	if(isEmpty(folioSolicitud)){
		customAlert("Debe ingresar un folio");
		return;
	}
	jQuery("#gridSolicitudes").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarSolicitudesPericialesPorFolioParaReasignacion.do?folioSolicitud='+folioSolicitud+''});
	$("#gridSolicitudes").trigger("reloadGrid",[{page:1}]);
}
    
/////////////////////////////////////////////////////////TERMINA FUNCIONALIDAD DEL LA CEJA SOLICITANTE //////////////////////////////////////////////////
		
/////////////////////////////////////////////////////////COMIENZA FUNCIONALIDAD DEL LA CEJA PERITO //////////////////////////////////////////////////
		
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

		if(selected.val() == "seleccione" ){
			busquedaEstadoInicial();
			limpiarTodo("todo");
		}
		
		if(selected.val() == "nombre"){
			busquedaNombrePerito();
			limpiarTodo("especialidad");
		}

		if(selected.val() == "especialidad"){

			busquedaEspecialidadPerito();
			limpiarTodo("nombre");

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
		cargaGridPeritos("","","","");
		
		if(limpiaObjeto == "todo"){	
			$('#txtFieldNombrePerito').val("");
			$('#txtFieldApPatPerito').val("");
			$('#txtFieldApMatPerito').val("");
			$("#cbxEspecialidadPerito").attr('selectedIndex', 0);
		}
		if(limpiaObjeto == "nombre"){	
			$('#txtFieldNombrePerito').val("");
			$('#txtFieldApPatPerito').val("");
			$('#txtFieldApMatPerito').val("");
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
		var especialidad = $("#cbxEspecialidadPerito option:selected").val();
		
		cargaGridPeritos(nombre,apPat,apMat,especialidad);			
	}


	/*
	*Funcion que carga el grid con las solicitudes periciales no atendidas
	*/
	function cargaGridPeritos(nombre,apPat,apMat,especialidad){
		if(firstGridPeritos == true){
			
			jQuery("#gridPerito").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarPeritosADesignar.do?nombre='+nombre+'&apellidoPaterno='+apPat+'&apellidoMaterno='+apMat+'&especialidad='+especialidad+'', 
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
						$("#btnReasignar").show();
				}
			}).navGrid('#pagerGridPerito',{edit:false,add:false,del:false});

			//cambia la variable a falso para ya no dibujar el grid, solo recargarlo
			firstGridPeritos=false;
		}
		else{
			jQuery("#gridPerito").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarPeritosADesignar.do?nombre='+nombre+'&apellidoPaterno='+apPat+'&apellidoMaterno='+apMat+'&especialidad='+especialidad+'',datatype: "xml" });
			$("#gridPerito").trigger("reloadGrid");			
		}
	}
	
/////////////////////////////////////////////////////////TERMINA FUNCIONALIDAD DEL LA CEJA PERITO //////////////////////////////////////////////////


//Envia la solicitud bas&aacute;ndose en solicitudId y el id del perito seleccionado	
function reasignarSolicitudPericial(){
	//Obtenemos el id del perito seleccionado. 
	var rowPerito = jQuery("#gridPerito").jqGrid('getGridParam','selrow');
	//Obtenemos el id de la solicitud seleccionada. 
	var rowSolicitud = jQuery("#gridSolicitudes").jqGrid('getGridParam','selrow');
	
	if(isEmpty(rowPerito)){
		customAlert("Debe seleccionar un perito");
		return;
	}
	if(isEmpty(rowSolicitud)){
		customAlert("Debe seleccionar una solicitud");
		return;
	}

	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/reasignarSolicitudPericial.do',
		data: 'peritoId='+rowPerito+'&solicitudId='+rowSolicitud,
		dataType: 'xml',
		async: false,
		success: function(xml){
			if($(xml).find('bandera').text() == "1"){
				customAlert("Reasignaci&oacute;n exitosa","", function(){
					limpiarTodo("todo");
					cargaGridSolicitudes();
				});
			}else{
				customAlert("Fallo la reasignaci&oacute;n");
			}
		}
	});
}


</script>
</head>

<body>
	<!--Comienzan tabs principales-->
	<div id="tabsprincipalconsulta">
		<ul>
			<li>
				<a href="#tabsconsultaprincipal-1">Solicitud</a>
			</li>
			<li>
				<a href="#tabsconsultaprincipal-2">Perito</a>
			</li>
		</ul>
		
		<!--Comienza tab solicitud-->
		<div id="tabsconsultaprincipal-1" align="left">
			<table width="1000" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		            <td><br></td>
		        </tr>
		        <tr>
		            <td align="right"><strong>Folio Solicitud:</strong></td>
		            <td align="left" valign="middle">
		            	<input type="text" id="folioSolicitud" style="width: 150px;"/>
		            </td>
		            <td rowspan="6" align="left">
			            <table id="gridSolicitudes"></table>
						<div id="pagerGridSolicitudes"></div>
		            </td>
		        </tr>
		        <tr>
		            <td align="right">&nbsp;</td>
		            <td align="left" valign="middle">
		            	<input type="button" id="buttonBuscarSolicitud" value="Buscar" class="ui-button ui-corner-all ui-widget" onclick="cargaGridSolicitudes();">
		            </td>
		        </tr>
		        <tr>
		            <td align="right">&nbsp;</td>
		            <td align="left" valign="middle">&nbsp;</td>
		        </tr>
		        <tr>
		            <td align="right">&nbsp;</td>
		            <td align="left" valign="middle">&nbsp;</td>
		        </tr>
		        <tr>
		            <td align="right">&nbsp;</td>
		            <td align="left" valign="middle">&nbsp;</td>
		        </tr>
		        <tr>
		            <td align="right">&nbsp;</td>
		            <td align="left" valign="middle">&nbsp;</td>
		        </tr>
		    </table>
		</div>
		<!--Termina tab solicitud-->
		
		<!--Comienza tab perito-->
		<div id="tabsconsultaprincipal-2" align="left">
			
			<table width="1000" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		            <td><br></td>
		        </tr>
		        <tr>
		            <td align="right"><strong>Consulta por:</strong></td>
		            <td align="left" valign="middle">
		            	<select name="cbxTipoDeConsulta" id="cbxTipoDeConsulta" style="width: 180px" >
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
		            </td>
		        </tr>
		        <tr>
		            <td align="right">&nbsp;</td>
		            <td align="left" valign="middle">&nbsp;</td>
		        </tr>
		        <tr>
		            <td align="right">
		            	<div id="divNombrePeritoTag"><strong>Nombre:</strong></div>
		            	<div id="divEspecialidadPeritoTag"><strong>Especialidad:</strong></div>
		            </td>
		            <td align="left" valign="middle">
		            	<div id="divNombrePeritoTxtField"><input type="text" id="txtFieldNombrePerito" style="width: 180px;" onkeypress="return soloLetrasNPunto(event,this.id);" onblur="validaSoloLetras(this);"/></div>
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
		            	<input type="button" id="btnReasignar" value="Reasignar Solicitud" class="ui-button ui-corner-all ui-widget" onclick="reasignarSolicitudPericial();" />
					</td>
		        </tr>
		    </table>	
									
		</div>
		<!--Termina tab perito-->
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