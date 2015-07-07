package mx.gob.segob.nsjp.service.test.solicitud.impl;

import java.util.Date;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

public class GenerarFolioSolicitudServiceImplTest extends BaseTestServicios<GenerarFolioSolicitudService> {

	public void testGenerarFolioSolicitud() throws NSJPNegocioException{
		for(int i = 0; i<5; i++){
			logger.debug("NUEVO FOLIO ==> "+service.generarFolioSolicitud());
		}
	}
	
	public void testGenerarFoliodDocumento(){
		String valor;
		try {
			valor = service.generarFoliodDocumento();
			logger.info("Folio:"+ valor);
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	public void testGenerarFolioMandamientoJudicial(){
		try {
			String folio = service.generarFolioMandamientoJudicial(2851L, new Date(), "00012/CA/2013-PJ-YUC-002");
			logger.debug("NUEVO FOLIO MADAMIENTO==> "+folio);
			assertNotNull(folio);
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	
	public void testGenerarFolioInterinstitucionalMandamientoPersona(){
		try {
			String folio = service.generarFolioInterInstitucionalMandamientoPersona();
			logger.debug("NUEVO FOLIO MADAMIENTO==> "+folio);
			assertNotNull(folio);
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
}

