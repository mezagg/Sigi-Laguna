/**
 * 
 */
package mx.gob.segob.nsjp.dao.expediente;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Convenio;

/**
 * @author adrian
 *
 */
public interface ConvenioDAO extends GenericDao<Convenio, Long> {

	/**
	 * Operaci�n que realiza la funcionalidad de consultar los convenios de conciliaci�n/mediaci�n asociados al expediente.
	 * @param numeroExpedienteId
	 * @return
	 */
	List<Convenio> consultarConveniosPorExpediente(
			Long numeroExpedienteId);
	
	//Operaci�n que realiza la funcionalidad de consultar el detalle de un convenio

}
