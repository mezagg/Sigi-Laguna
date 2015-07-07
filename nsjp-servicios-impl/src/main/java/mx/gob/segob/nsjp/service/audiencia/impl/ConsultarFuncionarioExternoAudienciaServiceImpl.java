/**
 * Nombre del Programa	: ConsultarFuncionarioExternoAudienciaServiceImpl.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 05 Marzo 2013
 * Marca de cambio      : N/A
 * Descripcion General  : Servicio permite consultar un funcionario externo asociado a 
 * 						  una audiencia
 * Programa Dependiente	: N/A
 * Programa Subsecuente	: N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                : N/A
 * Compania             : N/A
 * Proyecto             : N/A                             Fecha: N/A
 * Modificacion         : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.audiencia.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.NotificacionDAO;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoAudienciaDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaIdDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudienciaId;
import mx.gob.segob.nsjp.model.Notificacion;
import mx.gob.segob.nsjp.service.audiencia.ConsultarFuncionarioExternoAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.EventoTransformer;
import mx.gob.segob.nsjp.service.funcionarioexterno.impl.transform.FuncionarioExternoAudienciaTransformer;
import mx.gob.segob.nsjp.service.funcionarioexterno.impl.transform.FuncionarioExternoTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.logging.Logger;

/**
 * Servicio permite consultar un funcionario externo asociado a una audiencia
 * 
 * @author AlejandroGA
 * @version 1.0
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ConsultarFuncionarioExternoAudienciaServiceImpl implements
		ConsultarFuncionarioExternoAudienciaService {

	@Autowired
	FuncionarioExternoAudienciaDAO funcionarioExternoAudienciaDAO;
	@Autowired
	NotificacionDAO notificacionDAO;

	public static final Logger logger = Logger
			.getLogger(ConsultarFuncionarioExternoAudienciaServiceImpl.class);

	@Override
	public FuncionarioExternoAudienciaDTO ConsultarFuncionarioExternoAudienciaPorId(
			FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO)
			throws NSJPNegocioException {

		if (funcionarioExternoAudienciaIdDTO == null
				|| funcionarioExternoAudienciaIdDTO.getFuncionarioExternoId() == null
				|| funcionarioExternoAudienciaIdDTO.getFuncionarioExternoId() <= 0L
				|| funcionarioExternoAudienciaIdDTO.getAudienciaId() == null
				|| funcionarioExternoAudienciaIdDTO.getAudienciaId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		FuncionarioExternoAudienciaId funcionarioExternoAudienciaId = new FuncionarioExternoAudienciaId(
				funcionarioExternoAudienciaIdDTO.getAudienciaId(),
				funcionarioExternoAudienciaIdDTO.getFuncionarioExternoId());

		FuncionarioExternoAudiencia funcionarioExternoAudiencia = funcionarioExternoAudienciaDAO.read(funcionarioExternoAudienciaId);

		return FuncionarioExternoAudienciaTransformer
				.transformar(funcionarioExternoAudiencia);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.audiencia.
	 * ConsultarFuncionarioExternoAudienciaService
	 * #ConsultarFuncionarioExternoAudienciaPorRol
	 * (mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaDTO)
	 */
	@Override
	public List<FuncionarioExternoDTO> consultarFuncionarioExternoAudienciaPorRol(
			FuncionarioExternoAudienciaDTO funcionarioExternoAudienciaDTO)
			throws NSJPNegocioException {

		logger.info("SERVICIO PARA CONSULTAR FUNCIONARIOS EXTERNOS ASOCIADOS A AUDIENCIA");
		if (funcionarioExternoAudienciaDTO == null
				|| funcionarioExternoAudienciaDTO.getAudienciaDTO() == null
				|| funcionarioExternoAudienciaDTO.getAudienciaDTO().getId() == null
				|| funcionarioExternoAudienciaDTO.getAudienciaDTO().getId() <= 0L
				|| funcionarioExternoAudienciaDTO.getFuncionarioExternoDTO() == null
				|| funcionarioExternoAudienciaDTO.getFuncionarioExternoDTO()
						.getRolDTO() == null
				|| funcionarioExternoAudienciaDTO.getFuncionarioExternoDTO()
						.getRolDTO().getRolId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<FuncionarioExternoDTO> funcionarioExternoDTOList = null;

		List<FuncionarioExterno> funcionarioExternoList = funcionarioExternoAudienciaDAO
				.consultarFuncionarioExternoPorRol(
						funcionarioExternoAudienciaDTO
								.getFuncionarioExternoDTO().getRolDTO()
								.getRolId(), funcionarioExternoAudienciaDTO
								.getAudienciaDTO().getId());

		if (funcionarioExternoList != null && !funcionarioExternoList.isEmpty()) {

			funcionarioExternoDTOList = new ArrayList<FuncionarioExternoDTO>();

			for (FuncionarioExterno funcionarioExterno : funcionarioExternoList) {
				funcionarioExternoDTOList.add(FuncionarioExternoTransformer
						.transformar(funcionarioExterno));
			}
		}

		return funcionarioExternoDTOList;
	}

	@Override
	public List<FuncionarioExternoDTO> consultarFuncionarioExternoAudienciaNotificaciones(
			AudienciaDTO audienciaDTO) throws NSJPNegocioException {

		if (audienciaDTO == null || audienciaDTO.getId() == null
				|| audienciaDTO.getId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<FuncionarioExternoDTO> funcionarioExternoDTOList = null;

		List<FuncionarioExterno> funcionarioExternoList = funcionarioExternoAudienciaDAO
				.consultarFuncionarioExternoPorRol(null, audienciaDTO.getId());

		if (funcionarioExternoList != null && !funcionarioExternoList.isEmpty()) {

			funcionarioExternoDTOList = new ArrayList<FuncionarioExternoDTO>();

			for (FuncionarioExterno funcionarioExterno : funcionarioExternoList) {

				FuncionarioExternoDTO funcionarioExternoDTO = new FuncionarioExternoDTO();

				final List<Notificacion> notificacionesAsociadas = notificacionDAO
						.consultarNotificacionesPorAudienciaFuncionarioExterno(
								audienciaDTO.getId(),
								funcionarioExterno.getFuncionarioExternoId());

				funcionarioExternoDTO = FuncionarioExternoTransformer
						.transformar(funcionarioExterno);

				funcionarioExternoDTO.setNotificaciones(EventoTransformer
						.transformarNotificaciones(notificacionesAsociadas));

				funcionarioExternoDTOList.add(funcionarioExternoDTO);
			}
		}

		return funcionarioExternoDTOList;
	}
	
}
