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

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.documento.RelacionSolicitudDocumentoFuncionarioDAO;
import mx.gob.segob.nsjp.model.RelacionDocumento;
import mx.gob.segob.nsjp.model.RelacionSolicitudDocumentoFuncionario;
import mx.gob.segob.nsjp.model.Solicitud;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */

@Repository
public class RelacionSolicitudDocumentoFuncionarioDAOImpl extends GenericDaoHibernateImpl<RelacionSolicitudDocumentoFuncionario, Long>
		implements RelacionSolicitudDocumentoFuncionarioDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelacionSolicitudDocumentoFuncionario> consultarRelacionesPorSolicitud(
			Solicitud solicitud) throws NSJPNegocioException {
		
		if (solicitud == null
				|| solicitud.getDocumentoId() == null
				|| solicitud.getDocumentoId() < 1L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		StringBuilder query = new StringBuilder("");
		query.append(" SELECT r ");
		query.append(" FROM RelacionSolicitudDocumentoFuncionario r ");
		query.append(" WHERE r.solicitud.documentoId = :documentoId ");
		
		Query q = getSession().createQuery(query.toString());
		q.setParameter("documentoId", solicitud.getDocumentoId());
		
		return (List<RelacionSolicitudDocumentoFuncionario>) q.list();
	}
	
	public void actualizaFuncionarioDeDocumentosSegunRelacion(Long idSolicitud,
			Long idFuncionarioAnterior, Long idFuncionarioNuevo)
			throws NSJPNegocioException {
		
		StringBuffer query = new StringBuffer("");
		query.append(" UPDATE Documento d");
		query.append(" SET d.responsableDocumento.claveFuncionario = :idFuncionarioNuevo ");
		query.append(" WHERE d.documentoId in ");
		query.append(" (");
		query.append(" SELECT rsdf.documento.documentoId FROM RelacionSolicitudDocumentoFuncionario rsdf ");
		query.append(" WHERE rsdf.solicitud.documentoId = :idSolicitud ");
		query.append(" AND rsdf.funcionario.claveFuncionario = :idFuncionarioAnterior");
		query.append(" )");
		
		super.getSession().createQuery(query.toString())
				.setParameter("idFuncionarioNuevo", idFuncionarioNuevo)
				.setParameter("idSolicitud", idSolicitud)
				.setParameter("idFuncionarioAnterior", idFuncionarioAnterior)
				.executeUpdate();
	}
	
	public void actualizaFuncionarioDeRelacion(Long idSolicitud,
			Long idFuncionarioAnterior, Long idFuncionarioNuevo)
			throws NSJPNegocioException {
		
		StringBuffer query = new StringBuffer("");
		query.append(" UPDATE RelacionSolicitudDocumentoFuncionario rsdf");
		query.append(" SET rsdf.funcionario.claveFuncionario = :idFuncionarioNuevo ");
		query.append(" WHERE rsdf.solicitud.documentoId = :idSolicitud ");
		query.append(" AND rsdf.funcionario.claveFuncionario = :idFuncionarioAnterior");
		
		super.getSession().createQuery(query.toString())
				.setParameter("idFuncionarioNuevo", idFuncionarioNuevo)
				.setParameter("idSolicitud", idSolicitud)
				.setParameter("idFuncionarioAnterior", idFuncionarioAnterior)
				.executeUpdate();
	}
	
	public void actualizaFuncionarioDeActividadSegunRelacion(Long idSolicitud,
			Long idFuncionarioAnterior, Long idFuncionarioNuevo)
			throws NSJPNegocioException {
		
		StringBuffer query = new StringBuffer("");
		query.append(" UPDATE Actividad a");
		query.append(" SET a.funcionario.claveFuncionario = :idFuncionarioNuevo ");
		query.append(" WHERE a.documento.documentoId in ");
		query.append(" (");
		query.append(" SELECT rsdf.documento.documentoId FROM RelacionSolicitudDocumentoFuncionario rsdf ");
		query.append(" WHERE rsdf.solicitud.documentoId = :idSolicitud ");
		query.append(" AND rsdf.funcionario.claveFuncionario = :idFuncionarioAnterior");
		query.append(" )");
		
		super.getSession().createQuery(query.toString())
				.setParameter("idFuncionarioNuevo", idFuncionarioNuevo)
				.setParameter("idSolicitud", idSolicitud)
				.setParameter("idFuncionarioAnterior", idFuncionarioAnterior)
				.executeUpdate();
	}

}
