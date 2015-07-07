/**
* Nombre del Programa 		: DocumentoIntegracionDAOImplTest.java
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
package mx.gob.segob.nsjp.dao.test.documento.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.DocumentoIntegracionDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public class DocumentoIntegracionDAOImplTest extends BaseTestPersistencia<DocumentoIntegracionDAO> {
	
	public void testConsultarDocumentosIntegracionXExpediente(){
		Expediente expediente = new Expediente();
		expediente.setExpedienteId(602L);
		
		try {
			List<Documento> documentosIntegrados = daoServcice.consultarDocumentosIntegracionXExpediente(expediente);
			if (documentosIntegrados != null){
				for (Documento d : documentosIntegrados){
					logger.info("Id del documento: "+ d.getDocumentoId() );
					logger.info("Id del tipo del documento: "+ d.getTipoDocumento().getValorId() );
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
