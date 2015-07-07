/**
* Nombre del Programa		: ValidarSolicitudDeDefensorServiceImpl.java
* Autor						: AlejandroGA
* Compania					: Ultrasist
* Proyecto                  : NSJP                    Fecha: 31 Oct 2012
* Marca de cambio        	: N/A
* Descripcion General    	: Servicio para validar si se puede 
* 							  enviar una solicitud de defensor
* Programa Dependiente  	: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion        : N/A
* Dias de ejecucion         : N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                     : N/A
* Compania               	: N/A
* Proyecto                 	: N/A                                 Fecha: N/A
* Modificacion           	: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.solicitud.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.service.solicitud.ValidarSolicitudDeDefensorService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AlejandroGA
 */

@Service
@Transactional
public class ValidarSolicitudDeDefensorServiceImpl implements
		ValidarSolicitudDeDefensorService {

	public static final Logger logger = Logger.getLogger(ValidarSolicitudDeDefensorServiceImpl.class);
	
	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private DelitoPersonaDAO delitoPersonaDAO;
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.ValidarSolicitudDeDefensorService#validarSolicitudDeDefensorService(mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO)
	 */
	@Override
	public String validarSolicitudDeDefensor(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("BIENVENIDO AL SERVICIO PARA VALIDAR SOLICITUDES DE DEFENSOR");
		}

		if (expedienteDTO == null || expedienteDTO.getExpedienteId() == null || expedienteDTO.getExpedienteId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		String respuesta = "success";

		List<Involucrado> listaProbResponsables = involucradoDAO
				.consultarProbablesResponsablesParaSolucitudDeDefensor(
						expedienteDTO.getExpedienteId(),
						Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId(),
						false, true,false);

		if (listaProbResponsables == null || listaProbResponsables.isEmpty()) {
			respuesta = "No se puede completar la solicitud de defensor."
					+ " No existen probables participes personas fisicas sin defensor";
			return respuesta;
		}

		Boolean existenCandidatos = false;

		for (Involucrado probableResp : listaProbResponsables) {
			List<DelitoPersona> listaRelDelitoPer = delitoPersonaDAO
					.consultarDelitosPorImputadoResponsableConVictima(
							probableResp.getElementoId(),
							expedienteDTO.getExpedienteId());
			if (listaRelDelitoPer != null && !listaRelDelitoPer.isEmpty()) {
				existenCandidatos = true;
			}
		}

		if (existenCandidatos.equals(false)) {
			respuesta = "No se puede completar la solicitud de defensor."
					+ " Verifique que almenos un probable participe, tenga una relacion delito persona";
			return respuesta;
		}

		List<Involucrado> listaDenunciantesFisicos = involucradoDAO
				.obtenerInvByIdExpAndCalidad(expedienteDTO.getExpedienteId(),
						Calidades.DENUNCIANTE.getValorId(), null);

		List<Involucrado> listaDenunciantesMorales = involucradoDAO
				.obtenerInvByIdExpAndCalidad(expedienteDTO.getExpedienteId(),
						Calidades.DENUNCIANTE_ORGANIZACION.getValorId(), null);

		if ((listaDenunciantesFisicos == null || listaDenunciantesFisicos
				.isEmpty())
				&& (listaDenunciantesMorales == null || listaDenunciantesMorales
						.isEmpty())) {
			respuesta = "No se puede completar la solicitud de defensor."
					+ " Verifique que exista un denunciante";
			return respuesta;
		}

		return respuesta;
	}

}
