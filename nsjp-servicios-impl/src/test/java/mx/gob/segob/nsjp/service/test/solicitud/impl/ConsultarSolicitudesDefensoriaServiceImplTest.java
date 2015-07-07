/**
 * Nombre del Programa : ConsultarSolicitudesDefensoriaServiceImplTestTest.java
 * Autor                            : rgama
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 16 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Prueba unitaria de ConsultarSolicitudesDefensoriaServiceImplTest
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.test.solicitud.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.involucrado.DetencionDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudesDefensoriaService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Prueba unitaria de ConsultarSolicitudesDefensoriaServiceImplTest.
 * 
 * @version 1.0
 * @author rgama
 * 
 */
public class ConsultarSolicitudesDefensoriaServiceImplTest
        extends
            BaseTestServicios<ConsultarSolicitudesDefensoriaService> {
    
	public void testObtenerSolicitudesDefensoria() {
		List<SolicitudDefensorDTO> sol = null;
		try {

			sol = service.obtenerSolicitudesDefensoriaPorEstatus(
					EstatusSolicitud.EN_PROCESO, Instituciones.PGJ);
			assertTrue("La lista debe de ser mayor a cero", sol.size() > 0);

			for (SolicitudDefensorDTO respDTO : sol) {
				// if(respDTO.getExpedienteDTO().getExpedienteId() == 562){

				logger.info("---------SOLICITUD " + respDTO.getDocumentoId()
						+ "---------------------------------------");

				if (respDTO.getExpedienteDTO() != null
						&& respDTO.getExpedienteDTO().getCasoDTO() != null
						&& respDTO.getExpedienteDTO().getCasoDTO()
								.getNumeroGeneralCaso() != null) {
					logger.info("Id del expediente  \t: "
							+ respDTO.getExpedienteDTO().getExpedienteId());
					logger.info("Número de caso  \t: "
							+ respDTO.getExpedienteDTO().getCasoDTO()
									.getNumeroGeneralCaso());
					logger.info("Número de causa \t: "
							+ respDTO.getExpedienteDTO().getNumeroExpediente());
					logger.info("Fecha y hora de vencimiento para atención \t: "
							+ respDTO.getFechaLimite());
				}

				// Muestra la informacion sobre el imputado
				for (InvolucradoDTO elementoDTO : respDTO.getExpedienteDTO()
						.getInvolucradoByCalidad(
								Calidades.PROBABLE_RESPONSABLE_PERSONA)) {
					logger.info("            * Nombre del imputado \t: "
							+ elementoDTO.getNombreCompleto() + "  ID "
							+ elementoDTO.getElementoId());
					if (elementoDTO.getEsDetenido() != null
							&& elementoDTO.getEsDetenido()) {
						logger.info("            * ¿El imputado esta detenido? "
								+ elementoDTO.getEsDetenido());
						// Delitos del imputado
						if (elementoDTO.getDelitosCometidos() != null) {
							logger.info("            * Delitos propios del imputado: ");
							// for (DelitoDTO delitoDTO :
							// elementoDTO.getDelitosCometidos()) {
							// // System.out.print("            * "+
							// delitoDTO.getNombreDelito() + "\n");
							// }
						}

						for (DetencionDTO detencionDTO : elementoDTO
								.getDetenciones()) {
							logger.info("     * Lugar donde se encuentra detenido: "
									+ detencionDTO
											.getLugarDetencionProvicional());
							logger.info("     * Fecha y hora de detencion: "
									+ detencionDTO.getstrfechaFinDetencion());
						}
					}
				}

				// Información asociada a la Audiencia de la solicitud que viene
				// de PoderJudicial (PJ)
				AudienciaDTO loAud = respDTO.getAudiencia();
				if (loAud != null) {
					logger.info("Fecha Audiencia: "
							+ DateUtils.formatear(loAud.getFechaEvento()));
					logger.info("Hora Audiencia: "
							+ DateUtils.formatearHora(loAud.getFechaEvento()));
					logger.info("Tipo audiencia: "
							+ loAud.getTipoAudiencia().getValor());
					logger.info("Sala: " + loAud.getSala().getNombreSala());
					logger.info("Ubicacion Sala: "
							+ loAud.getSala().getUbicacionSala());
					logger.info("Domicilio Sala: "
							+ loAud.getSala().getDomicilioSala());
				}
				 
				logger.info("AvisoDesignacion: "+respDTO.getAvisoDesignacion());
				if(respDTO.getAvisoDesignacion()!=null){
					logger.info("AvisoDesignacion: "+respDTO.getAvisoDesignacion().getDocumentoId());
					logger.info("AvisoDesignacion: "+respDTO.getAvisoDesignacion().getFuncionario());
					logger.info("AvisoDesignacion: "+respDTO.getAvisoDesignacion().getSolicitudDefensor());
				}
				logger.info("---------------------------------------------------");
			}
			// }

		} catch (NSJPNegocioException e) {
			fail(e.getMessage());
		}
	}    
    
    
    public void testConsultarSolicitudesDefensoriaByHistoricoYEstatus () {
    	try {
			List<SolicitudDefensorDTO> respuesta = service.consultarSolicitudesDefensoriaByHistoricoYEstatus(EstatusSolicitud.CERRADA);
			assertTrue("La lista debe de ser mayor a cero", respuesta.size()>0);
			logger.info("La lista debe de ser mayor a cero"+respuesta.size());
			
			for (SolicitudDefensorDTO solicitudDefensorDTO : respuesta) {
				logger.info("----------------------");
				logger.info("Solicitu ID :: "+solicitudDefensorDTO.getDocumentoId());
				logger.info("Origen docto :: "+solicitudDefensorDTO.getOrigenDocumento());
				if (solicitudDefensorDTO.getExpedienteDTO()!=null) {
					logger.info("Num expediente :: "+solicitudDefensorDTO.getExpedienteDTO().getNumeroExpediente());
					if (solicitudDefensorDTO.getExpedienteDTO().getCasoDTO()!=null) {
						logger.info("Num caso :: "+solicitudDefensorDTO.getExpedienteDTO().getCasoDTO().getNumeroGeneralCaso());
					}
				}
				if (solicitudDefensorDTO.getDestinatario()!=null) {
					logger.info("Defensor :: "+solicitudDefensorDTO.getDestinatario().getNombreCompleto());
				}
				if (solicitudDefensorDTO.getInstitucion()!=null)
					logger.info("Defensor :: "+solicitudDefensorDTO.getInstitucion().getNombreInst());
				
				logger.info("----------------------");
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
    }

	public void testConsultarInvolucradosSolicitudDefensor() {
		Long idSolicitudDefensor = 4805L;
		try {
			List<InvolucradoDTO> listaInvolucradosDTO = service
					.consultarInvolucradosSolicitudDefensor(idSolicitudDefensor);
			assertFalse("La lista debe no puede estar vacia",
					listaInvolucradosDTO.isEmpty());
			logger.info("Involucrados:" + listaInvolucradosDTO.size());
			for (InvolucradoDTO involucradoDTO : listaInvolucradosDTO) {
				logger.info("Involucrado:" + involucradoDTO.getElementoId());
				logger.info("Involucrado:" + involucradoDTO.getFolioElemento());
			}

		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Prueba Unitaria para el servisio que consulta el detalle de la solicitud de defensor
	 * Por Id de la solicitud.
	 */
	public void testObtenerSolicitudDefensor(){
		
		Long documentoId = 2902L; //2832L;
		SolicitudDefensorDTO solDefensorDTO = new SolicitudDefensorDTO(
				documentoId);
		try{
			SolicitudDefensorDTO respuestaDTO = service.obtenerSolicitudDefensor(solDefensorDTO);
			assertNotNull("No se obtuvo una solicitu con el ID:"+ documentoId, respuestaDTO);
			
			logger.debug("Información de la solicitud:"+ respuestaDTO);
			logger.debug("TipoSolicitud:"+ respuestaDTO.getTipoSolicitudDTO());
			logger.debug("Involucrado:"+ respuestaDTO.getInvolucradoDTO());
			if(respuestaDTO.getInvolucradoDTO()!=null){
				logger.debug("Involucrado:"+ respuestaDTO.getInvolucradoDTO().getExpedienteDTO());	
			}
			
			logger.debug("Involucrados:"+ respuestaDTO.getInvolucrados());
			
		}catch(NSJPNegocioException e){
			logger.error(e.getMessage(), e);
		}
		
	}
}
