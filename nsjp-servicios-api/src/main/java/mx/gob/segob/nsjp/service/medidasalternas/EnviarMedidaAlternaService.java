/**
* Nombre del Programa : EnviarMedidaAlternaService.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 6 Sep 2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato del servicio para enviar la medida alterna a una entidadad externa de PJ
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
package mx.gob.segob.nsjp.service.medidasalternas;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.medida.MedidaAlternaDTO;

/**
 * Contrato del servicio para enviar la medida alterna a una entidadad externa de PJ.
 * @author cesarAgustin
 *
 */
public interface EnviarMedidaAlternaService {

	/**
	 * 
	 * @return
	 * @throws NSJPNegocioException
	 */
	public MedidaAlternaDTO enviarMedidaAlternaASSP(Long idMedidaAlterna) throws NSJPNegocioException;
	
}
