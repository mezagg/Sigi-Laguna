/**
 * Nombre del Programa : RecibirNotificacionAudienciaServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 2 Sep 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.service.agenda.impl;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaBasicoWSDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.service.audiencia.RecibirNotificacionAudienciaService;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.notificacionaudiencia.AudienciaWSDTOTransformer;
import mx.gob.segob.nsjp.service.notificacion.GuardarNotificacionService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service("recibirNotificacionAudienciaService")
@Transactional
public class RecibirNotificacionAudienciaServiceImpl implements
		RecibirNotificacionAudienciaService {
	/**
     * 
     */
	private final static Logger logger = Logger
			.getLogger(RecibirNotificacionAudienciaServiceImpl.class);

	@Autowired
	private FuncionarioDAO funDao;
	
	@Autowired
	private GuardarNotificacionService guardarNotificacionService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.audiencia.RecibirNotificacionAudienciaService
	 * #recibirNotificacionAudiencia
	 * (mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaBasicoWSDTO)
	 */
	@Override
	public Boolean recibirNotificacionAudiencia(
			SolicitudAudienciaBasicoWSDTO notificacionWS)
			throws NSJPNegocioException {

		if (notificacionWS == null
				|| notificacionWS.getFuncionarioExternoDestinatario() == null
				|| notificacionWS.getFuncionarioExternoDestinatario()
						.getCveFuncionarioInstExt() == null
				|| notificacionWS.getFuncionarioExternoDestinatario()
						.getCveFuncionarioInstExt() <= 0L
				|| notificacionWS.getAudiencia() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Boolean respuesta = false;

		//Obtenemos el funcionario por su clave
		final Funcionario funcionario = funDao
				.read(notificacionWS.getFuncionarioExternoDestinatario()
						.getCveFuncionarioInstExt());

		if (funcionario == null) {
			logger.warn("Funcionario no encontrado");
			return Boolean.FALSE;
		}

		/*
		 * AQUI SE AGREGARIA LA RECEPCION DE LA NOTIFICACION, CUANDO SE ENVIEN,
		 * VIA SISTEMA (archivoDigital, tipoDocumento, etc.)
		 */
		respuesta = guardarNotificacionService
				.registrarAudienciaEnAgendaDeFuncionario(FuncionarioTransformer
						.transformarFuncionario(funcionario),
						AudienciaWSDTOTransformer
								.transformarWsDtoADto(notificacionWS
										.getAudiencia()));

		return respuesta;
	}
}
