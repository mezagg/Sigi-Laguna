package mx.gob.segob.nsjp.dao.test.documento.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.DocumentoValorDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.DocumentoValor;

public class DocumentoValorDAOImplTest extends BaseTestPersistencia<DocumentoValorDAO> {

	public void test(){
		List<Long> ids = new ArrayList<Long>();
//		ids.add(4104L);
		ids.add(4103L);
		List<DocumentoValor> docs= null;
		try {
			docs = daoServcice.consultarDocumentosValorPorIds(ids);
			for (DocumentoValor documentoValor : docs) {
				System.out.println(String.format("id= %s estatus= %s doc=%s", documentoValor.getDocumentoValorId(),documentoValor.getEstatusDocumento().getValor(),documentoValor.getIdDocumento()));
			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
		assertNotNull(docs);
	}
}