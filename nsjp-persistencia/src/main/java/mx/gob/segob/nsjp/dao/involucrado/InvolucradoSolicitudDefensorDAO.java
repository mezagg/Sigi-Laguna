/**
 * Nombre del Programa : InvolucradoSolicitudDefensorDAO.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 01/11/2012
 * Marca de cambio        : N/A
 * Descripcion General    : Interface para el DAO de Involucrado SolcitudDefensor
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
package mx.gob.segob.nsjp.dao.involucrado;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.InvolucradoSolicitudDefensor;
import mx.gob.segob.nsjp.model.InvolucradoSolicitudDefensorId;

/**
 * Interface para el DAO de Involucrado SolcitudDefensor
 * 
 * @author GustavoBP
 */
public interface InvolucradoSolicitudDefensorDAO
		extends
		GenericDao<InvolucradoSolicitudDefensor, InvolucradoSolicitudDefensorId> {

	/**
	 * Consultar los involucrados asociados a la Solicitud de Defensor.
	 * 
	 * @param idSolicitudDefensor
	 * @return
	 */
	List<Involucrado> consultarInvolucradosSolicitudDefensor(
			Long idSolicitudDefensor);
}
