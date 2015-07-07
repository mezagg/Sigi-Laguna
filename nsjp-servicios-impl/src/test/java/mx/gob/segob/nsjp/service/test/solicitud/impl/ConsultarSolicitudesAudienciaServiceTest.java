package mx.gob.segob.nsjp.service.test.solicitud.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudesAudienciaService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

public class ConsultarSolicitudesAudienciaServiceTest extends
		BaseTestServicios<ConsultarSolicitudesAudienciaService> {
	
	public void testConsultarOtrasSolicitudes(){
		UsuarioDTO usr = new UsuarioDTO();
		List<SolicitudDTO> lista = service.consultarOtrasSolicitudes(usr);
		if(lista!= null)
			logger.debug("Total: " + lista.size());
		
		for(SolicitudDTO solicitud : lista){
			logger.debug("solicitud :: "+solicitud.getTipoSolicitudDTO());
		}
		logger.debug("Total: " + lista.size());
		
	}

	public void testConsultarSolicitudesAudiencia(){
		UsuarioDTO usr = new UsuarioDTO();
		List<SolicitudAudienciaDTO> lista = service.consultarSolicitudesAudiencia(usr);
		if(lista!= null)
			logger.debug("Total: " + lista.size());
		for(SolicitudDTO solicitud : lista){
			logger.debug("solicitud :: "+solicitud);
		}
	}
	
	public void testExistenSolicitudesAudienciaAsociadasAlNumeroExpediente() {

		ExpedienteDTO expedienteDTO = new ExpedienteDTO();

		expedienteDTO.setNumeroExpedienteId(559L);
		Boolean existenSolicitudes = false;

		try {
			existenSolicitudes = service
					.existenSolicitudesAudienciaAsociadasAlNumeroExpediente(expedienteDTO);
			
			assertEquals(new Boolean(true), existenSolicitudes);

			logger.info("EXISTEN_SOLICITUDES:::" + existenSolicitudes);
		} catch (NSJPNegocioException e) {
			logger.error("Ocurrio una Excepcion" + e);
		}

	}	
}
