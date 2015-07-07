/**
 * Nombre del Programa	: IngresarFuncionarioExternoAudienciaServiceImpl.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Feb 2013
 * Marca de cambio      : N/A
 * Descripcion General  : permite asociar un funcionario externo con una audiencia
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
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoAudienciaDAO;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudienciaId;
import mx.gob.segob.nsjp.service.audiencia.IngresarFuncionarioExternoAudienciaService;
import mx.gob.segob.nsjp.service.funcionarioexterno.FuncionarioExternoService;
import mx.gob.segob.nsjp.service.funcionarioexterno.impl.transform.FuncionarioExternoAudienciaTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.logging.Logger;

/**
 * Permite asociar un funcionario externo con una audiencia
 * 
 * @author AlejandroGA
 * @version 1.0
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class IngresarFuncionarioExternoAudienciaServiceImpl implements
		IngresarFuncionarioExternoAudienciaService {

	@Autowired
	private FuncionarioExternoService funcionarioExternoService;
	@Autowired
	FuncionarioExternoAudienciaDAO funcionarioExternoAudienciaDAO;
	@Autowired
	FuncionarioExternoDAO funcionarioExternoDAO; 
	@Autowired
	AudienciaDAO audienciaDAO;

	public static final Logger logger = Logger
			.getLogger(IngresarFuncionarioExternoAudienciaServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.audiencia.
	 * IngresarFuncionarioExternoAudienciaService
	 * #ingresarFuncionarioExternoAudiencia
	 * (mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO,
	 * mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO)
	 */
	@Override
	public FuncionarioExternoAudienciaDTO ingresarFuncionarioExternoAudiencia(
			FuncionarioExternoDTO funcionarioExternoDTO,
			AudienciaDTO audienciaDTO, Boolean ingresarActualizarFuncExterno)
			throws NSJPNegocioException {

		logger.info("***BIENVENIDO AL SERVICIO PARA INGRESAR UN FUNCIONARIO EXTERNO-AUDIENCIA***");
		
		if (funcionarioExternoDTO == null || audienciaDTO.getId() == null
				|| audienciaDTO.getId() <= 0L) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		if ( audienciaDAO.read(audienciaDTO.getId())== null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		
		if (ingresarActualizarFuncExterno != null
				&& ingresarActualizarFuncExterno.equals(true)) {
			/**
			 * Si se ingresa o actuliza al funcionario externo
			 */
			funcionarioExternoDTO = funcionarioExternoService
					.guardarActualizarFuncionarioExternoPorIClaveFuncionarioInsExt(funcionarioExternoDTO);
		} else {
			/**
			 * Si ya se tiene el id del funcionario externo(localmente PJ)
			 */
			if (funcionarioExternoDTO.getFuncionarioExternoId() == null
					|| funcionarioExternoDTO.getFuncionarioExternoId() <= 0L) {
				logger.info("Funcionario externo Id="
						+ funcionarioExternoDTO.getFuncionarioExternoId());

				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			} else {

				FuncionarioExterno funcionarioExterno = funcionarioExternoDAO
						.read(funcionarioExternoDTO.getFuncionarioExternoId());

				if (funcionarioExterno == null) {
					throw new NSJPNegocioException(
							CodigoError.INFORMACION_PARAMETROS_ERRONEA);
				}
			}
		}

		/**
		 * Validamos que los datos del funcionario se hayan resuelto
		 * correctamente
		 */
		if (funcionarioExternoDTO == null
				|| funcionarioExternoDTO.getFuncionarioExternoId() == null
				|| funcionarioExternoDTO.getFuncionarioExternoId() <= 0L) {

			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
	
		FuncionarioExternoAudienciaId funcionarioExternoAudienciaId = new FuncionarioExternoAudienciaId(
				audienciaDTO.getId(),
				funcionarioExternoDTO.getFuncionarioExternoId());

		FuncionarioExternoAudiencia funcionarioExternoAudienciaInput = null;

		// Verificamos si la relaci&oacute;n existe
		funcionarioExternoAudienciaInput = funcionarioExternoAudienciaDAO.read(funcionarioExternoAudienciaId);

		// Si no existe la relaci&oacute;n la generamos
		if (funcionarioExternoAudienciaInput == null) {
 
			funcionarioExternoAudienciaId = funcionarioExternoAudienciaDAO
					.create(new FuncionarioExternoAudiencia(
							funcionarioExternoAudienciaId));
			funcionarioExternoAudienciaInput = new FuncionarioExternoAudiencia(funcionarioExternoAudienciaId);
		}

		return FuncionarioExternoAudienciaTransformer
				.transformar(funcionarioExternoAudienciaInput);
	}
}
