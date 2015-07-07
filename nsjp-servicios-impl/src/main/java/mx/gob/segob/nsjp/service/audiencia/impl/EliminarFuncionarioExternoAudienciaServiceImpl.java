/**
 * Nombre del Programa	: EliminarFuncionarioExternoAudienciaServiceImpl.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 11 Marzo 2013
 * Marca de cambio      : N/A
 * Descripcion General  : permite elminar un funcionario externo de una audiencia
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

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoAudienciaDAO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaIdDTO;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudienciaId;
import mx.gob.segob.nsjp.service.audiencia.EliminarFuncionarioExternoAudienciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.logging.Logger;

/**
 * permite elminar un funcionario externo de una audiencia
 * 
 * @author AlejandroGA
 * @version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EliminarFuncionarioExternoAudienciaServiceImpl implements
		EliminarFuncionarioExternoAudienciaService {

	public static final Logger logger = Logger
			.getLogger(EliminarFuncionarioExternoAudienciaServiceImpl.class);

	@Autowired
	private FuncionarioExternoAudienciaDAO funcionarioExternoAudienciaDAO;

	@Override
	public void eliminarFuncionarioExternoDeAudiencia(
			FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO)
			throws NSJPNegocioException {

		logger.info("***BIENVENIDO AL SERVICIO PARA ELIMINAR UN FUNCIONARIO EXTERNO-AUDIENCIA***");

		if (funcionarioExternoAudienciaIdDTO == null
				|| funcionarioExternoAudienciaIdDTO.getAudienciaId() == null
				|| funcionarioExternoAudienciaIdDTO.getAudienciaId() <= 0L
				|| funcionarioExternoAudienciaIdDTO.getFuncionarioExternoId() == null
				|| funcionarioExternoAudienciaIdDTO.getFuncionarioExternoId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		FuncionarioExternoAudienciaId funcionarioExternoAudienciaId = new FuncionarioExternoAudienciaId();

		funcionarioExternoAudienciaId
				.setAudienciaId(funcionarioExternoAudienciaIdDTO
						.getAudienciaId());

		funcionarioExternoAudienciaId
				.setFuncionarioExternoId(funcionarioExternoAudienciaIdDTO
						.getFuncionarioExternoId());

		FuncionarioExternoAudiencia funcionarioExternoAudiencia = new FuncionarioExternoAudiencia();
		funcionarioExternoAudiencia
				.setFuncionarioExternoAudienciaId(funcionarioExternoAudienciaId);

		funcionarioExternoAudienciaDAO.delete(funcionarioExternoAudiencia);

	}

}
