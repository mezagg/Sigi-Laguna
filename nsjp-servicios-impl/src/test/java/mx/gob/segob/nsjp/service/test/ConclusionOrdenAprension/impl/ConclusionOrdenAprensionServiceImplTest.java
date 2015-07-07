package mx.gob.segob.nsjp.service.test.ConclusionOrdenAprension.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.conclusion.ConclusionOrdenAprensionDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.service.conclusion.ConclusionOrdenAprensionService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

public class ConclusionOrdenAprensionServiceImplTest extends
		BaseTestServicios<ConclusionOrdenAprensionService> {
	
	public void testGuardarConclusionOrdenAprensionDTO(){
		
		ConclusionOrdenAprensionDTO conclusionOrdenAprensionDTO = new ConclusionOrdenAprensionDTO();
		ExpedienteDTO exp = new ExpedienteDTO();
		exp.setNumeroExpedienteId(3L);
		conclusionOrdenAprensionDTO.setNumeroExpediente(exp);
		Long id = null;
		try {
			id = service.guardarConclusionOrdenAprensionDTO(conclusionOrdenAprensionDTO);
		} catch (NSJPNegocioException e) {
		}
		assertNotNull(id);
	}

}
