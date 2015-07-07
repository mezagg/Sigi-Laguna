/**
 * Nombre del Programa : ActualizarEstatusSolicitudServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementación para la actualizacio del estatus de una solicitud.
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
package mx.gob.segob.nsjp.service.solicitud.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.solicitud.ActualizarEstatusSolicitudService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación para la actualizacio del estatus de una solicitud.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service
@Transactional
public class ActualizarEstatusSolicitudServiceImpl
        implements
            ActualizarEstatusSolicitudService {
    /**
     * Logger
     */
    private final static Logger logger = Logger
            .getLogger(ActualizarEstatusSolicitudServiceImpl.class);
    @Autowired
    private SolicitudDAO solicitudDAO;
    @Autowired
    private NumeroExpedienteDAO nueExpedienteDAO;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.segob.nsjp.service.solicitud.ActualizarEstatusSolicitudService
     * #actualizaEstatusSolicitud(mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO,
     * mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud)
     */
    @Override
    public void actualizaEstatusSolicitud(SolicitudDTO solicitud,
            EstatusSolicitud estatus) throws NSJPNegocioException {
        logger.debug("Cambiando al estatus :: " + estatus);
        Solicitud solBD = this.solicitudDAO.read(solicitud.getDocumentoId());
        if(estatus != null){
        	solBD.setEstatus(new Valor(estatus.getValorId()));
        }
        this.solicitudDAO.update(solBD);

    }
    
    @Override
    public void actualizaEstatusSolicitud(String folioSolicitud, EstatusSolicitud estatus) throws NSJPNegocioException {
        logger.debug("Cambiando al estatus :: " + estatus);
        Solicitud solBD = solicitudDAO.obtenerSolicitudPorFolio(folioSolicitud);
        solBD.setEstatus(new Valor(estatus.getValorId()));
        this.solicitudDAO.update(solBD);

    }
    
    @Override 
    //MOD defensorATE
    public Boolean actualizarEstatusSolicitudes(Long nuevoEstatusNumExpId,
			Long numeroExpedienteId) throws NSJPNegocioException {

		logger.debug("*****actualizarEstatusSolicitudAsesoria****");
		logger.debug("Parametros: nuevoEstatusNumExpId:" + nuevoEstatusNumExpId
				+ "-numeroExpedienteId:" + numeroExpedienteId);

		boolean regreso = true;
		
		if (nuevoEstatusNumExpId == null || nuevoEstatusNumExpId <= 0L
				|| numeroExpedienteId == null || numeroExpedienteId <= 0L) {
			regreso = false;
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		// Analisis de conversion entre Estatus, de Expediente a solicitud
		EstatusExpediente estatusExpedienteEnum = EstatusExpediente
				.getByValor(nuevoEstatusNumExpId);
		EstatusSolicitud estatusSolicitudEnum = null;

		if (estatusExpedienteEnum != null) {
			switch (estatusExpedienteEnum) {
			case ABIERTO:
				estatusSolicitudEnum = EstatusSolicitud.ABIERTA;
				break;
			case POR_ASIGNAR:
				estatusSolicitudEnum = EstatusSolicitud.POR_ASIGNAR;
				break;
			case ASIGNADO:
				estatusSolicitudEnum = EstatusSolicitud.ASIGNADO;
				break;
			case EN_PROCESO:
				estatusSolicitudEnum = EstatusSolicitud.EN_PROCESO;
				break;
			case CERRADO:
				estatusSolicitudEnum = EstatusSolicitud.CERRADA;
				break;
			}
			logger.debug("Cambio de estatus Solicitud - Expediente:"
					+ estatusExpedienteEnum + "- Solicitud:"
					+ estatusSolicitudEnum);

			NumeroExpediente numeroExpediente = nueExpedienteDAO
					.read(numeroExpedienteId);
			
			// si se tienen un cambio de estatus registrado
			if (estatusSolicitudEnum != null && numeroExpediente !=null) {

				Solicitud solicitudFiltro = new Solicitud();
				solicitudFiltro.setNumeroExpediente(numeroExpediente);
				List<Solicitud> solicitudes = solicitudDAO
						.consultarSolicitudPorFiltro(solicitudFiltro, true, null, null);

				if (solicitudes != null && !solicitudes.isEmpty()) {

					for (Solicitud solicitud : solicitudes) {
						logger.debug("Cambio de estatus Solicitud ("
								+ solicitud.getDocumentoId() + "):"
								+ solicitud.getEstatus().getValorId() + " -> "
								+ estatusSolicitudEnum.getValorId());
						solicitud.setEstatus(new Valor(estatusSolicitudEnum
								.getValorId()));
						solicitudDAO.update(solicitud);
					}
				}
				
				// Verificar si hay cambio de estatus del numero de expediente
				if (numeroExpediente.getEstatus() != null
						&& !numeroExpediente.getEstatus().getValorId()
								.equals(nuevoEstatusNumExpId)) {
					logger.debug("Se actualizara el estatus del numero de expediente de: "
							+ numeroExpediente.getEstatus().getValorId()
							+ " - A:" + nuevoEstatusNumExpId);
					numeroExpediente
							.setEstatus(new Valor(nuevoEstatusNumExpId));
					nueExpedienteDAO.update(numeroExpediente);
				}
			}
		}else{
			regreso = false;
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		return regreso;
	}
}
