/**
* Nombre del Programa 		: RelacionDocumentoDAOImpl.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 11/01/2013
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.dao.documento.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.documento.RelacionDocumentoDAO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.RelacionDocumento;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */

@Repository
public class RelacionDocumentoDAOImpl extends GenericDaoHibernateImpl<RelacionDocumento, Long>
		implements RelacionDocumentoDAO {

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.documento.RelacionDocumentoDAO#consultarRelacionesPorDocPrincipal(mx.gob.segob.nsjp.model.Documento)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RelacionDocumento> consultarRelacionesPorDocPrincipal(
			Documento doc) throws NSJPNegocioException {
		
		if (doc == null
				|| doc.getDocumentoId() == null
				|| doc.getDocumentoId() < 1L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		StringBuilder query = new StringBuilder("");
		query.append(" SELECT r ");
		query.append(" FROM RelacionDocumento r ");
		query.append(" WHERE r.documentoPrincipal.documentoId = :documentoId ");
		
		Query q = getSession().createQuery(query.toString());
		q.setParameter("documentoId", doc.getDocumentoId());
		
		return (List<RelacionDocumento>) q.list();
	}

}
