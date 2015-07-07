/**
* Nombre del Programa : ingresarTutor.js
* Autor               : Arturo Le�n
* Compania            : Ultrasist
* Proyecto            : NSJP                    Fecha: 07/03/2011
* Marca de cambio     : N/A
* Descripcion General : Archivo javaScript con las funciones para la vista de IngresarTutor
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
 * Funci�n que crea los radiobuttons para Condici�n en ingresarTutor
 * @param contexto Contexto de la aplicaci�n
 */
function cargarRbtCondicionTutor(contexto) {
	$.ajax({
		type: 'POST',
	    url:  contexto + '/CargarCondicionTutor.do',
	    data: '',
	    dataType: 'xml',
	    success: function(xml){
	    	$('#tdTutorCondicion').empty();
		    $(xml).find('condicionDTO').each(function(){
			    $('#tdTutorCondicion').append('<input type="radio" name="rbtTutorCondicion" disabled="disabled" value="' + $(this).find('glCatalogoId').text() +'">' + $(this).find('gcDescripcion').text() + '<br/>');
		    });
	    }
	});
}