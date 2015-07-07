/**
* Nombre del Programa : ConsultarComplejidadAudienciaService.java
* Autor                            : adrian
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 29/06/2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
* Programa Dependiente  :N/A
* Programa Subsecuente :N/A
* Cond. de ejecucion        :N/A
* Dias de ejecucion          :N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                       :N/A
* Compania               :N/A
* Proyecto                 :N/A                                 Fecha: N/A
* Modificacion           :N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.audiencia;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author adrian
 *
 */
public interface ConsultarComplejidadAudienciaService {

	/**
	 * Operaci&oacute;n que realiza la funcionalidad de consultar la complejidad de una audiencia.
	 * @param audiencia: El identificador de la audiencia.
	 * @return Devuelve la complejidad de la audiencia asociada al identificador de la audiencia, en  caso contrario, regresa null.
	 * @throws NSJPNegocioException
	 */
	ValorDTO consultarComplejidadAudiencia(AudienciaDTO audienciaDTO)throws NSJPNegocioException;

	public void actualizarComplejidadAudiencia(Long tipoAudiencia, Long complejidad) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de la complejidad de un tipo de audiencia.
	 * @param tipoAudienciaDTO - ValorDTO con el identificador del tipo de audiencia que se va a consultar.
	 * @return ValorDTO - ValorDTO con la complejidad asociada al tipo de audiencia consultada. 
	 * @throws NSJPNegocioException - En el caso de que no se env&iacute;en los par&aacute;metros suficientes 
	 * 								  para llevar a cabo la consulta.
	 */
	public ValorDTO consultarComplejidadTipoAudiencia(ValorDTO tipoAudiencia)throws NSJPNegocioException;
}
