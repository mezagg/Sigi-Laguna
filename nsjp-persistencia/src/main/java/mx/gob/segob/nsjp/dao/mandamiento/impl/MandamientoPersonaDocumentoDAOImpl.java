/**
 * Nombre del Programa 		: MandamientoPersonaDocumentoDAOImpl.java
 * Autor 					: AlejandroGA
 * Compania 				: Ultrasist
 * Proyecto 				: NSJP 								Fecha: 06/06/2013
 * Marca de cambio 			: N/A
 * Descripcion General 		: Clase para
 * Programa Dependiente 	: N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion 		: N/A
 * Dias de ejecucion 		: N/A 								Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor 					: N/A
 * Compania 				: N/A
 * Proyecto 				: N/A 								Fecha: N/A
 * Modificacion 			: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.mandamiento.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.mandamiento.MandamientoPersonaDocumentoDAO;
import mx.gob.segob.nsjp.model.MandamientoPersona;
import mx.gob.segob.nsjp.model.MandamientoPersonaDocumento;
import mx.gob.segob.nsjp.model.MandamientoPersonaDocumentoId;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * @author AlejandroGA
 * @version 1.0
 */

@Repository
public class MandamientoPersonaDocumentoDAOImpl extends
		GenericDaoHibernateImpl<MandamientoPersonaDocumento, MandamientoPersonaDocumentoId> implements
		MandamientoPersonaDocumentoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<MandamientoPersona> consultarMandamientosPersonaPorDocumentoId(
			Long documentoId) {
		
		final StringBuffer queryStr = new StringBuffer();

		queryStr.append(" SELECT mpd.mandamientoPersona FROM MandamientoPersonaDocumento mpd WHERE ");
		queryStr.append(" mpd.documento.documentoId = ").append(documentoId);
		
		Query query = super.getSession().createQuery(queryStr.toString());

		return query.list();
	}

}
