/**
* Nombre del Programa 		: RelacionDocumentoDAOImplTest.java
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
package mx.gob.segob.nsjp.dao.test.documento.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.RelacionDocumentoDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.RelacionDocumento;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public class RelacionDocumentoDAOImplTest extends BaseTestPersistencia<RelacionDocumentoDAO> {
	
	public void testConsultarRelacionesPorDocPrincipal(){
		Documento doc = new Documento();
		doc.setDocumentoId(974L);
		try {
			List<RelacionDocumento> relaciones = daoServcice.consultarRelacionesPorDocPrincipal(doc);
			if (relaciones != null){
				logger.info("Numero de relaciones: "+relaciones.size());
				for (RelacionDocumento rd : relaciones){
					logger.info("Id relacion: " +rd.getRelacionId());
					logger.info("Id Doc principal: "+rd.getDocumentoPrincipal().getDocumentoId());
					logger.info("Id Doc relacionado: "+rd.getDocumentoRelacionado().getDocumentoId());
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
