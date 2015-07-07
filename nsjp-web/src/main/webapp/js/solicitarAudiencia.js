// Cuando el documento este listo ejecutamos las siguientes funciones.
    //variables para setear las fechas
	var fechaServidor="";
	var fechaMax="";
	var timeMax="";
	
	
	var fechaSel="";
	
$(document).ready(function() {
    $("#tabprincipal").tabs();
    var fechaActual = new Date();
    
    $("#fechaSolicitud").html(getFechaActual(fechaActual));
    $("#horaSolicitud").html(getHoraMinutoActual(fechaActual));
    // iniciamos la carga de catalogos.
    // llamada a consultarCatalogoTipoAudiencia
    ejecutaAction("/consultarCatalogoTipoAudiencia", function(tiposDeAudiencia){
        $(tiposDeAudiencia).find('institucion').each(function(){
            $('#tipoDeAudiencia').append('<option value="' + $(this).find('clave').text() + '">'+ $(this).find('valor').text() + '</option>');
        });
    });
    var perteneceAPoderJudicial = false;
    
    if (!perteneceAPoderJudicial) {
        // llamada a consultarCasoPorExpediente
        ejecutaAction("/consultarCasoPorExpediente",
            function(caso){
                $("#numeroDeCaso").val($(caso).find("numeroGeneralCaso").text());
                $("#numeroDeCaso").attr("readonly", "true");
            },
            "numeroExpediente=" + obtenParametroDeUrl("numeroExpediente"));
        // nombre del solicitante
        ejecutaAction("/consultarDatosUsuario", function(usuario){
            var nombre = $(usuario).find("nombreFuncionario").last().text();
            var apellidoPaterno = $(usuario).find("apellidoPaternoFuncionario").last().text();
            var apellidoMaterno = $(usuario).find("apellidoMaternoFuncionario").last().text();
            $("#nombreDelSolicitante").val(nombre + " " + apellidoPaterno + " " + apellidoMaterno);
            $("#nombreDelSolicitante").attr("readonly", "true");
            $("#institucionSolicitante").text($(usuario).find("funcionario").find("departamento").last().find("area").find("nombre").text());
        });
        
        gridImputados();
        gridRelacionesDelitoPersonaPorProbResp();
        
        fechaServidor= consultaFechaHoraMaximaServer();
    	fechaMax=getFechaMaximaServerHechos(fechaServidor);
    	timeMax=getHoraMaximaServer(fechaServidor);
    	
        // ponemos un datepicker a la fecha
        $("#fechaLimiteAudiencia").datepicker({
            dateFormat: 'dd/mm/yy',
            yearRange: '-111:+300',
            minDate: fechaMax,
            onSelect: function(date) {
            	fechaSel=date;
            	seteaHoraMinima(date);
				},
            changeMonth: true,
            changeYear: true,
            showOn: "button",
            buttonImage: CONTEXT_ROOT + "/resources/images/date.png",
            buttonImageOnly: true
        });
        
        $('#horaLimiteAudiencia').timeEntry({
            timeSteps:[1,1,0],
            minTime: timeMax,
            show24Hours: true
        });
    // Fin si no pertenece a poder judicial
    }else{

        $("#numeroDeCaso").change(function(){
            var numeroDeCaso = $("#numeroDeCaso").val();
            ejecutaAction("/validarExisteCaso", function(respuesta){
                var existe = $(respuesta).find("boolean").text();
                if(existe == "false"){
                    customAlert("El n&uacute;mero de caso no existe","Aviso:");
                }
            },
            "numeroDeCaso="+numeroDeCaso);
        });
    }

}); // Fin onready de jquery.

function seteaHoraMinima(date){
	
	if(date==fechaMax)
	{
		$('#horaLimiteAudiencia').timeEntry('destroy');
		$('#horaLimiteAudiencia').timeEntry({show24Hours: true, minTime: timeMax});
	}
	else
	{
		$('#horaLimiteAudiencia').timeEntry('destroy');
		$('#horaLimiteAudiencia').timeEntry({show24Hours: true, minTime: null,maxTime: null});
	}
	
}

function enviarSolicitudAudiencia(){
	
	
    var parametros = obtenParametros("parametro");
	
    if(obtenerSeleccionados($("#gridImputadosSolicitudDeAudiencia")).length <= 0){
    	customAlert("Debe seleccionar almenos un "+probableResponsableString,"Aviso:");
         return ;
    }
    	
    	
    if(parametros.indexOf("tipoDeAudiencia=-1") > 0) {
    	customAlert("Debe seleccionar un tipo de audiencia","Aviso:");
        return ;
    }
    
    if(parametros.indexOf("tribunal=-1") > 0){
    	customAlert("Debe seleccionar un tribunal para la audiencia","Aviso:");
    	return;
    }
    if(parametros.indexOf("funcionarioDestinatario=-1") > 0){
    	customAlert("Debe seleccionar un destinatario para la audiencia","Aviso:");
    	return;
    }
   
	parametros += "&idExpedienteSoli=" + idEspedienteSoli;
	if (idNumeroExpediente != undefined && idNumeroExpediente != null
			&& idNumeroExpediente != "") {
		parametros += "&idNumeroExpediente=" + idNumeroExpediente;
	}

	var nombreFuncExt = hashNombreCompletoDestinatarioInstExterna[$("#funcionarioDestinatario option:selected").val()];
	parametros += "&nombreCompletoDestinatarioInstExterna=" + nombreFuncExt;
	//Obtiene los registros seleccionados en el grid de imputados
	parametros += '&imputadosSolAudiencia=' + obtenerSeleccionados($("#gridImputadosSolicitudDeAudiencia"));
			
	//bloquear pantalla
	bloquearPantalla(true, "Solicitando Audiencia");  
	
	$.ajax({
	     type: 'POST',
	     url: CONTEXT_ROOT+'/enviarSolicitudAudiencia.do',
	     data: parametros, 
		 dataType: 'xml',
		 success: function(respuesta){
			 if(parseInt($(respuesta).find('code').text())==0){
				 if($(respuesta).find('body').text() != null 
						 && $(respuesta).find('body').text() != "null"
							 && $(respuesta).find('body').text() != ""){
						customAlert($(respuesta).find('body').text());
						enviarDocumento();
						enviarCorreoAvisoAudienciaSolicitud(parametros);
				 }else{
					 customAlert("Ocurri&oacute; un error al intentar enviar la solicitud de audiencia.<br/>"
							 + "por favor contacte al administrador");
				 }
			 } else {
				 customAlert("Ocurri&oacute; un error al intentar enviar la solicitud de audiencia.<br/>"
						 + "por favor contacte al administrador");
			 }
			 desbloquearPantalla();
		  }
	});
}

function enviarDocumento(){
	if(idDocumentoAdjunto != ''){		
		var parametros = "numeroGeneralCaso=" + $("#numeroDeCaso").val();
		parametros += "&idDocumento=" + idDocumentoAdjunto;
		parametros += "&idFuncionario=" + $("#funcionarioDestinatario").val();
		parametros += "&idInstitucion=" + institucionDestinoDocumento;
		$.ajax({
			type: 'POST',
			url: CONTEXT_ROOT+'/enviarUnDocumentoAInstitucion.do',
			data: parametros, 
			dataType: 'xml',
			success: function(respuesta){
				
			}
		});
	}
}

function ventanaDocumentosAdjuntosASolicitudAudiencia(){
	
	if(typeof(idExpedienteop) != "undefined" && typeof(idExpedienteop) != "null" && idExpedienteop != ""){
		$.newWindow({id:"iframewindowAdjuntarDocumento", statusBar: true, posx:50,posy:50,width:1100,height:400,title:"Adjuntar documento", type:"iframe"});
		$.updateWindowContent("iframewindowAdjuntarDocumento",'<iframe src="' + contextoPagina + '/gridSelectDocumentos.jsp?idExpediente='+idExpedienteop+'&idTipoDocto='+tipoDocumentoSolicitud+'&regresarId=1'+'" width="1100" height="400" />');
	}else{
		if(typeof(idNumeroExpediente) != "undefined" && typeof(idNumeroExpediente) != "null" && idNumeroExpediente != ""){
			$.newWindow({id:"iframewindowAdjuntarDocumento", statusBar: true, posx:50,posy:50,width:1100,height:400,title:"Adjuntar documento", type:"iframe"});
			$.updateWindowContent("iframewindowAdjuntarDocumento",'<iframe src="' + contextoPagina + '/gridSelectDocumentos.jsp?idNumeroExpediente='+idNumeroExpediente+'&idTipoDocto='+tipoDocumentoSolicitud+'&regresarId=1'+'" width="1100" height="400" />');
		}else{
			alert("Imposible adjuntar!");
		}
	}   
}

function cerrarVentanaDocumentosAdjuntos(){
	$.closeWindow("iframewindowAdjuntarDocumento");
}

function enviarCorreoAvisoAudienciaSolicitud( parametros ){
	
	$.ajax({
		type: 'POST',
		url: CONTEXT_ROOT+'/enviarCorreoAudienciaSolicitud.do',
		data: parametros, 
		async: false,
		dataType: 'xml',
		success: function(xml){
			
		}
	});
}

function getFechaActual(fechaActual){
    var month = fechaActual.getMonth() + 1;
    var mes = month < 10 ? "0" + month: month;
    return fechaActual.getDate() + "/" + mes + "/" +
    fechaActual.getFullYear();
}

/*
 * Funcion para regresar la fecha maxima obtenida desde el servidor
 * fechaCompleta - cadena con el siguiente formato : YYYY-MM-DD HH:MI:SS
 * regresa una cadena con la fecha en el formato : DD/MM/YYYY
 */
function getFechaMaximaServerHechos(fechaCompleta){
	
	var arrFechaHora=fechaCompleta.split(" ");
	var digitosFecha=arrFechaHora[0].split("-");
	return digitosFecha[2]+'/'+digitosFecha[1]+'/'+digitosFecha[0];
}

/*
 * Funcion para regresar la hora maxima obtenida desde el servidor
 * fechaCompleta - cadena con el siguiente formato : YYYY-MM-DD HH:MI:SS
 * regresa una cadena con la hora en el formato : HH:MMAM o HH:MMPM
 */
function getHoraMaximaServer(fechaCompleta){
	
	var arrFechaHora=fechaCompleta;
	var horaC=arrFechaHora[1].substring(0,5);
	var horaD=horaC.substring(0,2);
	var horaCorrecta="";
	if(parseInt(horaD)<13)
	{	
		horaCorrecta=horaC+'AM';
	}
	else
	{
		horaCorrecta=horaC+'PM';
	}
	return horaCorrecta;
}

/*
*Funcion para seleccionar en el gird multiselect
*/
function  seleccionarItems(grid){
		
	var currentPage = grid.getGridParam('page').toString();

	//retrieve any previously stored rows for this page and re-select them
	var retrieveSelectedRows = grid.data(currentPage);
	
	if (retrieveSelectedRows) {
		 $.each(retrieveSelectedRows, function (index, value) {
			 grid.setSelection(value, false);
		});
	}
}				


/*
*Funcion para guardar en el gird multiselect
*/
function guardarItemsSeleccionados(grid) {
	
	var pagerId = grid.getGridParam('pager').toString();
	
	if(pagerId.indexOf("#") != -1){
		pagerId = pagerId.substring(1, pagerId.length);
	}
	// ger paper id like "pager" 
	var pageValue = $('input.ui-pg-input', "#pg_" + $.jgrid.jqID(pagerId)).val(); 
	var saveSelectedRows = grid.getGridParam('selarrrow'); 
	//Store any selected rows 
	grid.data(pageValue.toString(), saveSelectedRows); 
}
					

/*
*Funcion para borrar la seleccion en el gird multiselect
*/		
function eliminarItemsSeleccionados(grid){
	
	guardarItemsSeleccionados(grid);
	grid.jqGrid('resetSelection');
	var retrieveSelectedRows = grid.data();       
	if (retrieveSelectedRows) {
		$.each(retrieveSelectedRows, function (index, value) {
			$.each(value, function (sub_index, sub_value) {
				if(typeof(sub_value)=='string'){
					grid.data(index, "");
				}
			});
		});
	}
	
}

/*
*Funcion para obtener los ids seleccionados del grid multiselect
*/
function obtenerSeleccionados(grid){
	
	guardarItemsSeleccionados(grid);

	var retrieveSelectedRows = grid.data();       
	var arr_values = new Array();

	if (retrieveSelectedRows) {
		$.each(retrieveSelectedRows, function (index, value) {
			$.each(value, function (index, sub_value) {
				if(typeof(sub_value)=='string')
				arr_values.push(sub_value);
			});
		});
	}

	return arr_values;
}			
					
/*
*Funcion para obtener el id de la fila seleccionada
*/			
function obtenerIdFilaSeleccionada(grid){
	var id = grid.jqGrid('getGridParam','selrow');
	if(id) {
		return id;
	} else {
		return false;
	}
}



