/**
 * Nombre del Programa : ActividadDAO.java
 * Autor                            : jfernandezo
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 26 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato para el objeto de acceso a datos de la Actividad
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
package mx.gob.segob.nsjp.dao.expediente;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.ConclusionNumeroExpediente;
import mx.gob.segob.nsjp.model.NumeroExpediente;

/**
 * Contrato para el objeto de acceso a datos de la Actividad.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public interface ConclusionNumeroExpedienteDAO extends GenericDao<ConclusionNumeroExpediente, NumeroExpediente> {
	 public ConclusionNumeroExpediente obtenerConclusionNumeroExpediente(Long numeroExpedienteId) ;

}
