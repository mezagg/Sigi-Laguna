/**
* Nombre del Programa 		: ActualizacionSentenciaService.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 20/12/2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.sentencia;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaWSDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public interface ActualizacionSentenciaService {
	
	/**
	 * M&eacute;todo que lleva a cabo la actualizacion de los datos contenidos en la 
	 * sentencia, recibiendo la informaci&oacute;n correspondiente desde una 
	 * instituci&oacute;n externa.
	 * @param sentenciaWSDTO - WSDTO con la informaci&oacute;n a actualizar de la 
	 * 						   sentencia recibida desde una instituci&oacute;n externa.
	 * @throws NSJPNegocioException - En el caso de que los par&aacute;metros m&iacute;nimos
	 * 								  para consultar la sentencia no se hayan recibido.
	 */
	public void actualizarSentencia(SentenciaWSDTO sentenciaWSDTO) throws NSJPNegocioException;

}
