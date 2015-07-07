/**
* Nombre del Programa : ConclusionNumeroExpedienteService.java
* Autor                            : Jfernandez
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 06/07/2011
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
package mx.gob.segob.nsjp.service.expediente;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.hecho.ConclusionNumeroExpedienteDTO;

/**
 * Interfaz del servicio de negocio para realizar el guardado de la conclusion * 
 * @version 1.0
 * @author Jorge Fernández
 *
 */
public interface ConclusionNumeroExpedienteService {
	
	Boolean guardarConclusion(ConclusionNumeroExpedienteDTO conclusion) throws NSJPNegocioException;
	public ConclusionNumeroExpedienteDTO buscarConclicionNumeroExpe(Long idNumeroExpe)throws NSJPNegocioException ;

}
