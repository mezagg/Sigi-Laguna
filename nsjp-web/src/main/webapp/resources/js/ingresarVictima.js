/**
* Nombre del Programa : ingresarVictima.js
* Autor               : Arturo Le�n
* Compania            : Ultrasist
* Proyecto            : NSJP                    Fecha: 07/03/2011
* Marca de cambio     : N/A
* Descripcion General : Archivo javaScript con las funciones para la vista de IngresarVictima
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
 * Funci�n que crea los radiobuttons para Condici�n en ingresarVictima
 * @param contexto Contexto de la aplicaci�n
 */
function cargarRbtCondicionVictima(contexto) {
	$.ajax({
		type: 'POST',
	    url:  contexto + '/CargarCondicionVictima.do',
	    data: '',
	    dataType: 'xml',
	    success: function(xml){
	    	$('#tdVictimaCondicion').empty();
		    $(xml).find('condicionDTO').each(function(){
			    $('#tdVictimaCondicion').append('<input type="radio" name="rbtVictimaCondicion" value="' + $(this).find('glCatalogoId').text() +'">' + $(this).find('gcDescripcion').text() + '<br/>');
		    });
	    }
	});
}