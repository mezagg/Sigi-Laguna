<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<title>Consulta Ciudadana</title>

<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilosConsultaCiudadana.css"/>
<link rel="stylesheet" media="screen" href="<%= request.getContextPath()%>/resources/css/consultaCiudadana/screen.css" />

<script src="<%=request.getContextPath()%>/resources/js/consultaCiudadana/jquery-1.9.1.js?n=1"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/consultaCiudadana/jquery.validate.min.js?n=1"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/consultaCiudadana/jquery.maskedinput.js?n=1"></script>


<script>
$.validator.setDefaults({
	submitHandler: function() {
		//Realizar consulta ciudadana
		consultaCiudadana();
	}
});

$().ready(function() {
	
	//$("#txtNumeroExpediente").mask("99999-aaaaaaaa");
	var container = $('div.container');

	var validator = $("#cmxform").validate({
		errorContainer: container,
		errorLabelContainer: $("ol", container),
		wrapper: 'li'
	});
});

function configuraPersonaMoral(){
	limpiaCampos();
	$("#seccionRespuesta").hide();
	$("#seccionApellidoPaterno").hide();
	$("#seccionApellidoMaterno").hide();
}

function configuraPersonaFisica(){
	limpiaCampos();
	$("#seccionRespuesta").hide();
	$("#seccionApellidoPaterno").show();
	$("#seccionApellidoMaterno").show();
}

function limpiaCampos(){
	$('#txtNumeroExpediente').val('');
	$('#txtNombre').val('');
	$('#txtApellidoPaterno').val('');
	$('#txtApellidoMaterno').val('');
}

function consultaCiudadana(){
	
	var esFisica = parseInt($(':radio[name=rdbTipoPersona]:checked').val());
	
	var params;
	params = 'nombre='+ $('#txtNombre').val();	
	params+='&apellidoPaterno=' + (esFisica == 1 ? $('#txtApellidoPaterno').val(): "");
	params+='&apellidoMaterno=' + (esFisica == 1 ? $('#txtApellidoMaterno').val(): "");
	params+='&numeroExpediente=' + $('#txtNumeroExpediente').val();
	
	$.ajax({
		type: 'POST',
		url: '<%= request.getContextPath()%>/realizarConsultaCiudadana.do',
		data: params,
		dataType: 'xml',
		async: true,
		success: function(xml){
			var respuesta = "	<table> " +
			"<thead>" +
			  "<tr align='center' border='1'>" +
			    "<th width='160'>N\u00FAmero de caso</th>" +
			    "<th width='160'>N\u00FAmero de expediente</th>" +
			    "<th width='160'>Victima</th>" +
			    "<th width='160'>Delito</th>" +
			    "<th width='160'>Funcionario</th>" +
			    "<th width='160'>\u00C1rea</th>" +
			    "<th width='160'>Estatus</th>" +
			  "</tr>" +
			 "</thead>";
			 var i  = 1;
			 $(xml).find('ExpedienteViewDTO').each(function(){
				  var numeroDeCaso = $(this).find('numeroGeneralCaso').text();
				  var numeroDeExpediente = $(this).find('numeroExpediente').text();
				  var victima = $(this).find('victima').text();
				  var delitoPrincipal = $(this).find('delito').text();
				  var funcionario = $(this).find('nombreFuncionario').text();
				  var area = $(this).find('area').text();
				  var estatus = $(this).find('estatus').text();
				  
				  respuesta += ( i%2 ? "<tr>": "<tr class='odd'>" ) +
					"<td>" + numeroDeCaso + "</td>" +
					"<td>" + numeroDeExpediente + "</td>" +
					"<td>" + victima + "</td>" +
					"<td>" + delitoPrincipal + "</td>" +
					"<td>" + funcionario + "</td>" +
					"<td>" + area + "</td>" +
					"<td>" + estatus + "</td>" +
				  "</tr>";
				  i++;
	    	});
			 
			 respuesta+= "</table>";
			$("#seccionRespuesta").html(respuesta)
			
			if(i>1){
				$("#seccionSinResultados").hide();
				$("#seccionRespuesta").show();
			}else{
				$("#seccionSinResultados").show();
				$("#seccionRespuesta").hide();
			}
		}
	});		    
}

</script>

</head>
<body>

<h1 id="banner">Consulta Ciudadana</h1>
<div id="main">
	
	<!-- our error container -->
	<div class="container">
		<h4>Hay algunos detalles, por favor revisa el formulario.</h4>
		<ol>
			<li><label for="txtNombre" class="error"></label></li>
			<li><label for="txtApellidoPaterno" class="error"></label></li>
			<li><label for="txtMpellidoMaterno" class="error"></label></li>
			<li><label for="txtNumeroExpediente" class="error"></label></li>
	
		</ol>
	</div>
	
	<br>
	
	<div align="center">
<form class="cmxform" id="cmxform" method="get" action="">
	<fieldset>
		<legend>Consulta ciudadana</legend>
        <p>
		 <label>
         <input type="radio" name="rdbTipoPersona" id="rdbTipoPersona_0" onClick="configuraPersonaFisica()" value="1" checked/>
     	 Persona Física</label>
    
        <label>
        <input type="radio" name="rdbTipoPersona" value="0" id="rdbTipoPersona_1" onClick="configuraPersonaMoral()"/>
        Persona Moral</label>
		</p>
		<p>
			<label for="txtNombre">Nombre(s) <span style="color:#FF0000">*</span></label>
			<input id="txtNombre" name="txtNombre" title="Por favor ingresa el nombre"  required>
		</p>
		<p id="seccionApellidoPaterno">
			<label for="txtApellidoPaterno">Apellido Paterno <span style="color:#FF0000">*</span></label>
			<input id="txtApellidoPaterno" name="txtApellidoPaterno" title="Por favor ingresa el apellido paterno" required minlength="1">
		</p>
		<p id="seccionApellidoMaterno">
			<label for="txtApellidoMaterno">Apellido Materno</label>
			<input id="txtApellidoMaterno" name="txtApellidoMaterno" title="Por favor ingresa el apellido materno">
		</p>
		<p>
			<label for="txtNumeroExpediente">N&uacute;mero de expediente <span style="color:#FF0000">*</span></label>
			<input id="txtNumeroExpediente" name="txtNumeroExpediente" title="Por favor ingresa el numero de expediente" required>
		</p>
		
		<p>
			<input class="submit" type="submit" value="Realizar consulta ciudadana"/>
		</p>
	</fieldset>
</form>

<br/>

<div id="seccionRespuesta" style="display: none"></div>

<div class="alert alert-info" id="seccionSinResultados" style="display: none; width: 700px"><strong>¡No se encontraron registros que coincidan con los criterios de b&uacute;squeda!</strong></div>


</div>
</div>

</body>
</html>
