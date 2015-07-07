/**
 * Nombre del Programa : InvolucradoSolicitudDefensorDAOImpl.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 01/11/2012
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci&oacute;n para el DAO del numero del expediente
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
package mx.gob.segob.nsjp.dao.involucrado.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoSolicitudDefensorDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.InvolucradoSolicitudDefensor;
import mx.gob.segob.nsjp.model.InvolucradoSolicitudDefensorId;

import org.springframework.stereotype.Repository;

/**
 * Implementaci&eacute;n para el DAO de Involucrado SolicitudDefensor
 * 
 * @author GustavoBP
 */
@Repository
public class InvolucradoSolicitudDefensorDAOImpl
		extends
		GenericDaoHibernateImpl<InvolucradoSolicitudDefensor, InvolucradoSolicitudDefensorId>
		implements InvolucradoSolicitudDefensorDAO {

	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarInvolucradosSolicitudDefensor(
			Long idSolicitudDefensor) {
		
		final StringBuffer query = new StringBuffer();
		
		query.append("SELECT isd.involucrado FROM InvolucradoSolicitudDefensor isd ");
		query.append(" WHERE isd.solicitudDefensor.documentoId = ");
		query.append(idSolicitudDefensor);
		logger.debug("query :: " + query);
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();

		logger.debug("pag :: " + pag);
        return super.ejecutarQueryPaginado(query, pag);
	}
}