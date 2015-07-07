/**
* Nombre del Programa : ActualizarEtapaExpediente.java
* Autor                            : Hugo Serrano
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 29 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Servicio para la actualitacion de la etapa del expediente
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
package mx.gob.segob.nsjp.service.expediente;

import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;

/**
 * Servicio para la actualitacion de la etapa del expediente 
 * @version 1.0
 * @author Tattva-IT
 *
 */
public interface ActualizarEtapaExpedienteService {
	
	
	/**
	 * Actualiza la etapa del expediente
	 * @param expDTO
	 * @throws NSJPNegocioException
	 */
	void actualizaEtapaExpediente(ExpedienteDTO expDTO,
			EtapasExpediente etExp)throws NSJPNegocioException;
	
	/**
	 * Servicio que se encarga de cambiar la etapa del involucrado 
	 * y efectuar el algoritmo para cambiar la etapa del expediente, 
	 * de acuerdo a la siguiente Regla de Negocio.
	 * La etapa del Expediente debe de ser la M&Aacute;XIMA etapa de los
	 * involucrados asociados al expediente
	 * 
	 * @param involucradoId
	 * @param etapaValorId
	 * @throws NSJPNegocioException
	 */
	void actualizarEtapaInvolucradoExpediente(Long involucradoId,
			Long etapaValorId) throws NSJPNegocioException;
	
}
