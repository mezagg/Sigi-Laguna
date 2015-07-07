/**
 * Nombre del Programa : ConsultaNotificacionServiceImpl.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 20-jul-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.notificacion.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.NotificacionDAO;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.model.Notificacion;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.EventoTransformer;
import mx.gob.segob.nsjp.service.notificacion.ConsultaNotificacionService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @version 1.0
 * @author Jacob Lobaco
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ConsultaNotificacionServiceImpl implements ConsultaNotificacionService {

    /**
     * Logger de la clase.
     */
    private final static Logger logger = Logger.getLogger(ConsultaNotificacionServiceImpl.class);
    @Autowired
    private NotificacionDAO notificacionDao;

    @Override
    public NotificacionDTO consultaNotificacion(NotificacionDTO consulta)
            throws NSJPNegocioException {
    	
		if (consulta == null || consulta.getDocumentoId() == null
				|| consulta.getDocumentoId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Notificacion consultada = notificacionDao.read(consulta
				.getDocumentoId());
		return consultada != null ? NotificacionTransformer
				.transformarNotificacion(consultada) : null;
    }

	@Override
	public List<NotificacionDTO> consultaNotificaciones(Long idAudiencia,
			Long idPersona, Boolean esFuncionario, Boolean esFuncionarioExterno) throws NSJPNegocioException {
		
		if (logger.isDebugEnabled()){
			logger.debug("/**** SERVICIO CONSULTAR NOTIFICACIONES ****/");			
		}

		if (idAudiencia == null || idAudiencia <= 0L || idPersona == null || idPersona <= 0L
				|| (esFuncionarioExterno == null && esFuncionario == null)) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		List<NotificacionDTO> notificacionesDTO = new ArrayList<NotificacionDTO>();
		List<Notificacion> notificaciones = new ArrayList<Notificacion>();
		

		if (esFuncionarioExterno != null && esFuncionarioExterno.equals(true)) {
			notificaciones = notificacionDao
					.consultarNotificacionesPorAudienciaFuncionarioExterno(
							idAudiencia, idPersona);
		} else {
			if (esFuncionario != null && esFuncionario.equals(true)) {
				notificaciones = notificacionDao
						.consultarNotificacionesPorAudienciaFuncionario(
								idAudiencia, idPersona);
			} else {
				notificaciones = notificacionDao
						.consultarNotificacionesPorAudienciaInvolucrado(
								idAudiencia, idPersona);
			}
		}
		notificacionesDTO = EventoTransformer.transformarNotificaciones(notificaciones);
		
		return notificacionesDTO;
	}
}
