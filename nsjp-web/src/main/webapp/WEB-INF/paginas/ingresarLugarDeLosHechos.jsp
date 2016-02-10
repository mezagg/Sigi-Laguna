<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ingresar Lugar de los Hechos</title>

	<!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css" /-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/south-street/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.weekcalendar.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/demo.css" />

	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/ckeditor/adapters/jquery.js"></script>
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
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>

<script type="text/javascript">
	var idindi;
	
	var unidadInvestigacion='';
	
	var numeroExpediente = "";
	
	$(document).ready(function() {
		cargaDelitos();

		//Se crean las tabs principales
		$( "#tabs" ).tabs();
		$("#tabsprincipalconsulta" ).tabs();

		$("#chkAnonima").click(bloqueaDatos);
		deshabilitaFuncionalidadDomicilio();
		iniciarEditorTexto();
	
	});

	  //Funcion que refleja los datos de nombre, apellido paterno, apellido materno a la ventana padre
	function bloqueaDatos(){
		if($("#chkAnonima").is(':checked')){
			$("#solicitanteNombre").val("An&oacute;nimo");
			$("#solicitanteAPaterno").val("");
			$("#solicitanteAMaterno").val("");
			
			$("#solicitanteNombre").attr("disabled","disabled");
			$("#solicitanteAPaterno").attr("disabled","disabled");
			$("#solicitanteAMaterno").attr("disabled","disabled");

		}else{
			$("#solicitanteNombre").val("");
			$("#solicitanteAPaterno").val("");
			$("#solicitanteAMaterno").val("");

			$("#solicitanteNombre").attr("disabled","");
			$("#solicitanteAPaterno").attr("disabled","");
			$("#solicitanteAMaterno").attr("disabled","");
		}
	}

	  
	/*
	*Funcion que dispara ajusta funcionalidad de domicilio para este caso
	*/	
	function deshabilitaFuncionalidadDomicilio(){
		$('#liDom').hide();
		$('#liDom').addClass("tabEstilo");
		killDomicilioNotificaciones();
		killCoordenadasGeograficas();
	}
	
	/*
	*Funcion que dispara el Action para consultar Delitos
	*/	
	function cargaDelitos(){
		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCatalogoDelitos.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('delitos').each(function(){
					$('#chkDelito').append('<option value="' + $(this).find('catDelitoId').text() + '">'+ $(this).find('nombre').last().text() + '</option>');
					
					if($(this).find('departamento').text()){
						unidadInvestigacion+=$(this).find('departamento').find('nombreDepto').text()+',';
					}else{
						unidadInvestigacion+=$(this).find('institucion').find('nombre').last().text()+',';
					}
				});
			}
		});
	}

	
	/*
	*Funcion que recupera los datos de la llamada de auxilio
	*/ 
	function recuperaDatosGenerales(){
		
		 var lsDatosGenerales="";
		 
		 lsDatosGenerales+="delito="+$("#chkDelito option:selected").val();
		 lsDatosGenerales+="&llamadaAnonima="+$("#chkAnonima").is(':checked');
		 lsDatosGenerales+="&calidadImplicado="+$("#chkIngresarLugarCmpCalidad option:selected").val();
		 lsDatosGenerales+="&implicadoNombre="+$('#solicitanteNombre').val();
		 lsDatosGenerales+="&implicadoAPaterno="+$('#solicitanteAPaterno').val();
		 lsDatosGenerales+="&implicadoAMaterno="+$('#solicitanteAMaterno').val();
		 
		 var datosMedios = obtenerMedios();
		 lsDatosGenerales += datosMedios;
		 
		 var domicilio = obtenerParametrosDomicilio();
		 lsDatosGenerales+=domicilio;
		 lsDatosGenerales +="&motivoLlamada=" + escape($('.jquery_ckeditor').val());
		 
		 return lsDatosGenerales;
	}


	/*
	*Funcion que valida, que los datos basicos sean ingresados
	*/
	function validaDatosGenerales(){
		
		if(!$("#chkAnonima").is(':checked')){
			if(($('#solicitanteNombre').val()=="") && ($('#solicitanteAPaterno').val()=="") && ($('#solicitanteAMaterno').val()=="")){
				customAlert("Por favor capture la informaci&oacute;n del solicitante");
				return false;
			}
		}
		   
	   if($("#chkIngresarLugarCmpCalidad option:selected").val() == -1){
			customAlert("Por favor capture la calidad del solicitante");
			return false;
	   }
		   
	   if($("#chkDelito option:selected").val() == -1){
			customAlert("Por favor capture el delito");
			return false;
	   }

	   var recuperaTexto = $('.jquery_ckeditor').val();
	   
	   if(recuperaTexto == ""){
			customAlert("Por favor capture la descripcion de la llamada");
			return false;
	   }
		
		return true;
	}

	/*
	*Funcion para guardar la llanada de auxilio
	*/
	function guardarDatosLugar(){
		
		if(validaDatosGenerales()==true){
			
			var params = recuperaDatosGenerales();
			
			$.ajax({
	  		  type: 'POST',
	  	  	  url: '<%= request.getContextPath()%>/guardarLugarDeLosHechos.do',
	  	  	  data: params,				
	  	      dataType: 'xml',
	  	   	  success: function(xml){
           		$(xml).find('avisoHechoDTO').each(function(){
               		
            			if($(this).find('documentoId').text() != 0){
            				customAlert('Solicitud de apoyo guardada',"",window.parent.cerrarVentanaLugarDeHechos);	            				
            			}
            			else{
        		  		  	customAlert('Error al registrar la solicitud de apoyo');	        		  		  		            				
            			}
           		});
	  	  	  }
		   });
			   
		}	
	}

	
	/*
	*Funcion que inicializa el editor de texto
	*/
	function iniciarEditorTexto(){

		var config = {toolbar:
			[
				['Source','-','Cut','Copy','Paste','-','Undo','Redo','-','Find','Replace','-','RemoveFormat'],
				['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
				['NumberedList','BulletedList','-','Outdent','Indent'],
				['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
				['Table','HorizontalRule'],
				'/',
				['Styles','Format','Font','FontSize','TextColor','BGColor','Maximize']
			],
			height:'350px'
		};

		$('.jquery_ckeditor').ckeditor(config);		
	}
	
</script>

</head>

<body>
    <div class="error1" style="display:none;">
      <img src="<%= request.getContextPath()%>/resources/images/warning.gif" alt="Warning!" width="24" height="24" style="float:left; margin: -5px 10px 0px 0px; " />

      <span></span>.<br clear="all"/>
    </div>

	<div id="tabsprincipalconsulta">
		<ul>
			<li><a href="#tabsconsultaprincipal-1">Reporte</a>
			</li>
			<li><a href="#tabsconsultaprincipal-2">Ingresar lugar de los hechos</a>
			</li>
			<li><a href="#tabsconsultaprincipal-3">Descripci&oacute;n del hecho</a>
			</li>
		</ul>
		<div id="tabsconsultaprincipal-1">
			
			<fieldset>
				<legend>Solicitante</legend>
				<table width="100%">
					<tr align="center">
						<td align="center" colspan="2">
							&iquest;La llamada es an&oacute;nima?
							<input type="checkbox" id="chkAnonima"/>
						</td>
						<td width="64%" rowspan="8">                           
							<jsp:include page="ingresarMediosContactoView.jsp"/>
						</td>
					</tr>
					<tr>
						<td width="12%" align="right">Calidad del solicitante:</td>
						<td width="24%" align="left">
							<select id="chkIngresarLugarCmpCalidad" style="width: 290px;">
								<option value="-1">- Seleccione -</option>
								<option value="215">Denunciante</option>
								<option value="216">Testigo</option>
								<option value="214">V&iacute;ctima</option>
							</select>			
						</td>
					</tr>
					<tr>
						<td align="right">Delito:</td>
						<td align="left">
							<select id="chkDelito"  style="width: 290px;">
								<option value="-1">- Seleccione -</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td align="right">Nombre(s):</td>
						<td><input name="text" type="text" id="solicitanteNombre" onkeypress="return soloLetrasNPunto(event);" size="50" maxlength="50" /></td>
					</tr>
					<tr>
						<td align="right">Apellido paterno:</td>
						<td><input name="text2" type="text" class="" id="solicitanteAPaterno" onkeypress="return soloLetrasNPunto(event);" size="50" maxlength="50" /></td>
					</tr>
					<tr>
						<td align="right">Apellido materno:</td>
						<td><input name="text3" type="text" class="" id="solicitanteAMaterno"  onkeypress="return soloLetrasNPunto(event);" size="50" maxlength="50"/></td>
					</tr>
					<tr>
						<td align="right">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="right">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="8" align="center">
							<div id="boton2">
								<input type="button" value="Enviar"  id="botonGuarda" onclick="guardarDatosLugar();" class="ui-button ui-corner-all ui-widget"/>
							</div>
						</td>
					</tr>
				</table>
			</fieldset>
			
		</div>
		
		<div id="tabsconsultaprincipal-2">
		
			<table width="762px" height="313px" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<jsp:include page="ingresarDomicilioView.jsp"/>
					</td>
				</tr>
			</table>
			
		</div>
		
		<div id="tabsconsultaprincipal-3">
		
			<table width="900px" height="350px" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<textarea class="jquery_ckeditor" cols="160" rows="25" id="notasLLamada" ></textarea>
					</td>
				</tr>
			</table>
			
		</div>

	</div>

</body>
</html>
