/**
* Nombre del Programa : ingresarProbableResponsable.js
* Autor               : Arturo Le�n
* Compania            : Ultrasist
* Proyecto            : NSJP                    Fecha: 04/03/2011
* Marca de cambio     : N/A
* Descripcion General : Archivo javaScript con las funciones para la vista de IngresarProbableResponsable
* Programa Dependiente: N/A
* Programa Subsecuente: N/A
* Cond. de ejecucion  : N/A
* Dias de ejecucion   : N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor               :N/A
* Compania            :N/A
* Proyecto            :N/A                                   Fecha: N/A
* Modificacion        :N/A
*------------------------------------------------------------------------------
*/

/**
 * Funci�n que crea los radiobuttons para Condici�n en ingresarProbableResponsable
 * @param contexto Contexto de la aplicaci�n
 */
function cargarRbtCondicion(contexto) {
	$.ajax({
		type: 'POST',
	    url:  contexto + '/CargarCondicionProbableResponsable.do',
	    data: '',
	    dataType: 'xml',
            async: true,
	    success: function(xml){
	    	$('#tdProbResponsableCondicion').empty();
		    $(xml).find('condicionDTO').each(function(){
			    $('#tdProbResponsableCondicion').append('<input type="radio" name="rbtProbResponsableCondicion" value="' + $(this).find('glCatalogoId').text() +'">' + $(this).find('gcDescripcion').text() + '<br/>');
		    });
	    }
	});
}

function inicializarGuardado(contexto){
	$('#ingProbRespBtnModificarDatos').click(function () {
		alert('En construcci�n');
	});
	
	$('#ingProbRespBtnGuardar').click(function () {
		
		$.ajax({
			type: 'POST',
		    url:  contexto + '/guardarIndividuo.do',
		    data: 'idCondicion=' + $(':radio[name=radio]:checked').val() + '&tipoResponsable=' + $("#cbxProbResponsableTipoResp option:selected")	+ '&esMenorDeEdad=' + $('#chkPResponsableMenor').is(':checked')+ '&estaDetenido=' + $('#chkPResponsableDetenido').is(':checked'),
		    dataType: 'xml',
		    success: function(xml){
		    	alert('guardado exitoso');
//		    	$('#tdProbResponsableCondicion').empty();
//			    $(xml).find('condicionDTO').each(function(){
//				    $('#tdProbResponsableCondicion').append('<input type="radio" name="rbtProbResponsableCondicion" value="' + $(this).find('glCatalogoId').text() +'">' + $(this).find('gcDescripcion').text() + '<br/>');
//			    });
		    }
		});
	});
}