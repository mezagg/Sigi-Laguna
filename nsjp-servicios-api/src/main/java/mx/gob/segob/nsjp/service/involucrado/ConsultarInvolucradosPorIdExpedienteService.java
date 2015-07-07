/**
* Nombre del Programa : ConsultarInvolucradosPorIdExpedienteService.java
* Autor                            : adrian
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 13/02/2012
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
package mx.gob.segob.nsjp.service.involucrado;

import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author rgama
 *
 */
public interface ConsultarInvolucradosPorIdExpedienteService {

	/**
	 * Operaci�n que realiza la funcionalidad de consultar los involucrados de un expediente.
	 * 
	 * @param idExpediente: El identificador del expediente
	 * @return Devuelve un listado de involucrados asociados al expediente, en caso contrario null.
	 * @throws NSJPNegocioException
	 */
	List<InvolucradoDTO> consultarInvolucradosPorExpediente(Long idExpediente)throws NSJPNegocioException;

	/**
	 * Servicio que consulta los involucrados de un expediente, para obtener el
	 * HashMap con el idElemento y folio del Elemento.
	 * 
	 * @param expedienteId
	 * @return
	 * @throws NSJPNegocioException
	 */
	HashMap<String, Long> consultarInvolucradosIdFolioElemento(Long expedienteId)
			throws NSJPNegocioException;
	
}
