<script type="text/javascript">
	
/*
*Funcionalidad estandar para mostrar las notas
*/	
function cargaNota(id,nombre){
	var row=$('#rowNota_'+id);
	$(row).remove();
	$('#tablaNotasExpediente').append('<tr id="rowNota_'+id+'"><td class="noSub" style="cursor:pointer;">&nbsp;&nbsp;&nbsp;<a id="consultarNota_'+id+'" onclick="notaExpediente('+id+')">'+nombre+'</a></td></tr>');
} 

//Variable para la ventana de notas
var idWindowGenerarNotas = 0;

/*
*Funcion que muestra la ventana de generar notas
*/
function notaExpediente(idNota){
	idWindowGenerarNotas++;
	$.newWindow({id:"iframewindowGenerarNotas" + idWindowGenerarNotas, statusBar: true, posx:200,posy:50,width:700, height:450,title:"Nota de Expediente", type:"iframe"});
    $.updateWindowContent("iframewindowGenerarNotas" + idWindowGenerarNotas,'<iframe src="<%= request.getContextPath() %>/capturarNotaExpediente.do?idNumeroExpedienteOp='+idNumeroExpediente +'&idNota='+idNota+'&porFuncionario=true" width="700" height="450" />');
}

/*
*Cierra la ventana de notas
*/
function cerrarVentanaNota(){
	var pantalla ="iframewindowGenerarNotas";
	pantalla += idWindowGenerarNotas;
	$.closeWindow(pantalla);
}

/*
*Consulta las notas asociadas al expediente,
*las muestra en la tabla en forma de ligas
*para posteriormente consultarlas
*/
function consultarNotas(){
	
	var parametros = 'idNumeroExpediente=' + idNumeroExpediente;
	parametros += '&porFuncionario=' + true;

	ejecutaAction("/consultarNotasExpediente", function(respuesta){
		if(parseInt($(respuesta).find('code').text())==0){
			$(respuesta).find('notaExpedienteDTO').each(function(){
				cargaNota($(this).find('idNota').text(),$(this).find('nombreNota').text());
			});
		}else{
			customAlert("Ocurri&oacute; un error al intentar consultar las notas.<br/>" +
				"por favor contacte al administrador");
		}
	} , parametros);
}

</script>

<table width="100%">
	<tr>
		<td>
			<table width="25%" cellpadding="0" cellspacing="0" id="tablaNotasExpediente">
				<tr>
					<td colspan="2">
						&nbsp;&nbsp;&nbsp;
						<input type="button" class="btn_modificar" value="Generar Nota" id="botonGuardarNotas" onclick="notaExpediente(0);" class="ui-button ui-corner-all ui-widget" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>