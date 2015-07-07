package mx.gob.segob.nsjp.dao.documento.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.documento.DocumentoValorDAO;
import mx.gob.segob.nsjp.model.DocumentoValor;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentoValorDAOImpl extends GenericDaoHibernateImpl<DocumentoValor, Long>
        implements
            DocumentoValorDAO {

	@Override
	public DocumentoValor consultarDocumentosValorPorDocumentoId(
			Long documentoId) throws NSJPNegocioException {
		if (documentoId == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		StringBuffer queryString = new StringBuffer("");
		queryString.append(" SELECT d ");
		queryString.append(" FROM DocumentoValor d ");
		queryString.append(" WHERE d.idDocumento = ");
		queryString.append(documentoId);
		Query query = super.getSession().createQuery(queryString.toString());
        return (DocumentoValor) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoValor> consultarDocumentosValorPorIds(
			List<Long> idsDocumentos) throws NSJPNegocioException {
		if (idsDocumentos == null
				|| idsDocumentos.isEmpty()){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		StringBuffer queryString = new StringBuffer("");
		queryString.append(" SELECT d ");
		queryString.append(" FROM DocumentoValor d ");
		queryString.append(" WHERE d.idDocumento IN (:identificadores) ");
		Query query = super.getSession().createQuery(queryString.toString());
		query.setParameterList("identificadores", idsDocumentos);
		return query.list();
	}

}
