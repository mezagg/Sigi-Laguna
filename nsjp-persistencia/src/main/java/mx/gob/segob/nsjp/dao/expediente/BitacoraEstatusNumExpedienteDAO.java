/**
 * Nombre del Programa : BitacoraEstatusNumExpedienteDAO.java
 * Autor                            : rgama
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21 Ene 2013
 * Marca de cambio        : N/A
 * Descripcion General    : Interface para el DAO del numero del expediente
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

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.BitacoraEstatusNumExpediente;

/**
 * Interface para el DAO del numero del expediente.
 * 
 * @version 1.0
 * @author rgama
 * 
 */
public interface BitacoraEstatusNumExpedienteDAO extends GenericDao<BitacoraEstatusNumExpediente, Long> {
    
	/**
	 * Permite consultar los registros en bitacora del asociados a un identificador de expediente
	 * @param expedienteId
	 * @return
	 */
	public List<BitacoraEstatusNumExpediente> consultarBitacoraEstatusNumExpedientePorIdExpediente(Long expedienteId);
}
