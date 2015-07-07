/**
* Nombre del Programa 		: DocumentoIntegracionDAOImpl.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 26/11/2012
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
import mx.gob.segob.nsjp.dao.documento.DocumentoIntegracionDAO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.DocumentoIntegracion;
import mx.gob.segob.nsjp.model.Expediente;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
@Repository
public class DocumentoIntegracionDAOImpl extends GenericDaoHibernateImpl<DocumentoIntegracion, Long>
		implements DocumentoIntegracionDAO {

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.documento.DocumentoIntegracionDAO#consultarDocumentosIntegracionXExpediente(mx.gob.segob.nsjp.model.Expediente)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Documento> consultarDocumentosIntegracionXExpediente(
			Expediente expediente) throws NSJPNegocioException {
		
		if (expediente == null 
				|| expediente.getExpedienteId() == null
				|| expediente.getExpedienteId() < 1L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		StringBuilder queryStr = new StringBuilder("");
		queryStr.append(" SELECT a.documento ");
		queryStr.append(" FROM Actividad a ");
		queryStr.append(" WHERE a.expediente.expedienteId = :expedienteId ");
		queryStr.append(" AND a.documento.tipoDocumento.valorId IN ( ");
		queryStr.append(" 	SELECT DISTINCT(di.tipoDocumento.valorId) ");
		queryStr.append(" 	FROM DocumentoIntegracion di ");
		queryStr.append(" ) ");
		
		Query query = super.getSession().createQuery(queryStr.toString());
		query.setParameter("expedienteId", expediente.getExpedienteId());
		
		return (List<Documento>) query.list();
	}
}
