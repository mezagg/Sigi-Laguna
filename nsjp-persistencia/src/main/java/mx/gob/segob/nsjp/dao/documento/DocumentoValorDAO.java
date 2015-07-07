package mx.gob.segob.nsjp.dao.documento;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.DocumentoValor;

public interface DocumentoValorDAO extends GenericDao<DocumentoValor, Long> {

    
	public DocumentoValor consultarDocumentosValorPorDocumentoId(Long documentoId) throws NSJPNegocioException;
	
	public List<DocumentoValor> consultarDocumentosValorPorIds(List<Long> idsDocumentos) throws NSJPNegocioException;
	
}
