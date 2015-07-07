/**
 * 
 */
package mx.gob.segob.nsjp.service.solicitud.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoSolicitudDefensorDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDAO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.InvolucradoSolicitudDefensor;
import mx.gob.segob.nsjp.model.InvolucradoSolicitudDefensorId;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudDefensorService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementaci&0acute;n del servicio de registro de solicitudes de
 * defensor&iacute;a
 * 
 * @author GustavoBP
 * 
 */
@Service
public class RegistrarSolicitudDefensorServiceImpl implements
		RegistrarSolicitudDefensorService {

	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private SolicitudDefensorDAO solicitudDefensorDAO;
	@Autowired
	private InvolucradoSolicitudDefensorDAO involucradoSolicitudDefensorDAO;

	private static final Logger logger = Logger
			.getLogger(RegistrarSolicitudDefensorServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudDefensoriaService
	 * #registrarSolicitudDefensorInvolucrados(java.util.List,
	 * mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO)
	 */
	@Override
	public Boolean registrarSolicitudDefensorInvolucrados(
			List<InvolucradoDTO> involucradosDTO,
			SolicitudDefensorDTO solicitudDefensorDTO)
			throws NSJPNegocioException {

		Boolean registroExitoso = false;

		if (solicitudDefensorDTO == null
				|| solicitudDefensorDTO.getDocumentoId() == null
				|| involucradosDTO == null || involucradosDTO.isEmpty()) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		SolicitudDefensor solDefensorBD = solicitudDefensorDAO
				.read(solicitudDefensorDTO.getDocumentoId());

		if (solDefensorBD == null) {
			logger.info("La solicitud no se enceuntra registrada en BD:"
					+ solicitudDefensorDTO);
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		for (InvolucradoDTO involucrado : involucradosDTO) {
			if (involucrado.getElementoId() == null
					|| involucrado.getElementoId() < 0) {
				logger.info("Elemento no registrado por no contar con ID:"
						+ involucrado);
				continue;
			}

			Involucrado involucradoBD = involucradoDAO.read(involucrado
					.getElementoId());
			if (involucradoBD != null) {
				logger.info("Involucrado Leido : "
						+ involucradoBD.getElementoId());
				InvolucradoSolicitudDefensor involucradoSolicitudDefensor = new InvolucradoSolicitudDefensor();
				involucradoSolicitudDefensor
						.setSolicitudDefensor(solDefensorBD);
				involucradoSolicitudDefensor.setInvolucrado(involucradoBD);
				involucradoSolicitudDefensor
						.setId(new InvolucradoSolicitudDefensorId(involucradoBD
								.getElementoId(), solDefensorBD
								.getDocumentoId()));
				involucradoSolicitudDefensorDAO
						.create(involucradoSolicitudDefensor);
				logger.info("solicitudDefensor Creada ");
				registroExitoso = true;
			}
		}

		return registroExitoso;
	}

}
