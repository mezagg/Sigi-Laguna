/**
} * Nombre del Programa 	: MandamientoPersonaDocumentoDAOImplTest.java
 * Autor 					: AlejandroGA
 * Compania 				: Ultrasist
 * Proyecto 				: NSJP 								Fecha: 06/06/2013
 * Marca de cambio 			: N/A
 * Descripcion General 		: Clase test de MandamientoPersonaDocumentoDAOImpl
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
package mx.gob.segob.nsjp.dao.test.mandamiento.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento;
import mx.gob.segob.nsjp.dao.mandamiento.MandamientoPersonaDocumentoDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.MandamientoPersona;
import mx.gob.segob.nsjp.model.MandamientoPersonaDocumento;
import mx.gob.segob.nsjp.model.MandamientoPersonaDocumentoId;
import mx.gob.segob.nsjp.model.Valor;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class MandamientoPersonaDocumentoDAOImplTest extends
		BaseTestPersistencia<MandamientoPersonaDocumentoDAO> {
	
	public void testGenerarMandamientoPersonaDocumento(){
//		MandamientoPersona mandamientoPersona = new MandamientoPersona();
//		Documento documento = new Documento();
//		
//		mandamientoPersona.setMandamientoPersonaId();
//		documento.setDocumentoId(documentoId);

		MandamientoPersonaDocumentoId mandamientoPersonaDocumentoId = new MandamientoPersonaDocumentoId(
				6L, 1450L);
		
		MandamientoPersonaDocumento mandamientoPersonaDocumento = new MandamientoPersonaDocumento(mandamientoPersonaDocumentoId);
		mandamientoPersonaDocumento.setEstatus(new Valor(EstatusMandamiento.EN_PROCESO.getValorId()));
		daoServcice.create(mandamientoPersonaDocumento);
		
	}
	
	public void testConsultarMandamientosPersonaPorDocumentoId(){
			
		List<MandamientoPersona> listaMandamientosPersona = daoServcice
				.consultarMandamientosPersonaPorDocumentoId(2693L);
		
		assertNotNull(listaMandamientosPersona);
		
		for(MandamientoPersona mandamientoPersona:listaMandamientosPersona){
			logger.info("mandamientoPersona-----");
			logger.info("id="+mandamientoPersona.getMandamientoPersonaId());
			logger.info("folio="+mandamientoPersona.getFolioInterInstitucional());
			logger.info("Estatus="+mandamientoPersona.getEstatus().getValorId());
			logger.info("Fecha ejecucion="+mandamientoPersona.getFechaEjecucion());
		}
	}

}
