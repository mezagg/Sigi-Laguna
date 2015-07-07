/**
 * Nombre del Programa 		: FuncionarioExternoServiceImpl.java
 * Autor 					: EdgarTE
 * Compania 					: Ultrasist
 * Proyecto 					: NSJP 							Fecha: 13 Apr 2012
 * Marca de cambio 			: N/A
 * Descripcion General 		: Describir el objetivo de la clase de manera breve
 * Programa Dependiente 		: N/A
 * Programa Subsecuente 		: N/A
 * Cond. de ejecucion 		: N/A
 * Dias de ejecucion 		: N/A 							Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor 					: N/A
 * Compania 					: N/A
 * Proyecto 					: N/A 							Fecha: N/A
 * Modificacion 				: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.funcionarioexterno.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.service.funcionarioexterno.FuncionarioExternoService;
import mx.gob.segob.nsjp.service.funcionarioexterno.impl.transform.FuncionarioExternoTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author EdgarTE
 * 
 */
@Service
@Transactional
public class FuncionarioExternoServiceImpl implements FuncionarioExternoService {

	@Autowired
	private FuncionarioExternoDAO funcionarioExternoDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.funcionarioexterno.FuncionarioExternoService
	 * #crearFuncionarioExterno
	 * (mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO)
	 */
	@Override
	public FuncionarioExternoDTO crearFuncionarioExterno(
			FuncionarioExternoDTO funcionarioExternoDTO) {
		FuncionarioExterno funcionarioExterno = FuncionarioExternoTransformer
				.transformar(funcionarioExternoDTO);
		Long idFuncionario = funcionarioExternoDAO.create(funcionarioExterno);
		funcionarioExternoDAO.flush();
		funcionarioExternoDTO.setFuncionarioExternoId(idFuncionario);
		return funcionarioExternoDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.funcionarioexterno.FuncionarioExternoService
	 * #actualizarFuncionarioExterno
	 * (mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO)
	 */
	@Override
	public void actualizarFuncionarioExterno(
			FuncionarioExternoDTO funcionarioExternoDTO) {
		FuncionarioExterno funcionarioExterno = FuncionarioExternoTransformer
				.transformar(funcionarioExternoDTO);
		funcionarioExternoDAO.merge(funcionarioExterno);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.funcionarioexterno.FuncionarioExternoService
	 * #eliminarFuncionarioExterno
	 * (mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO)
	 */
	@Override
	public void eliminarFuncionarioExterno(
			FuncionarioExternoDTO funcionarioExternoDTO) {
		FuncionarioExterno funcionarioExterno = FuncionarioExternoTransformer
				.transformar(funcionarioExternoDTO);
		funcionarioExternoDAO.delete(funcionarioExterno);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.funcionarioexterno.FuncionarioExternoService
	 * #consultarFuncionarioExternoPorId
	 * (mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO)
	 */
	@Override
	public FuncionarioExternoDTO consultarFuncionarioExternoPorId(
			FuncionarioExternoDTO funcionarioExternoDTO) {
		FuncionarioExterno funcionarioExterno = funcionarioExternoDAO
				.read(funcionarioExternoDTO.getFuncionarioExternoId());
		FuncionarioExternoDTO funcExtDTO = FuncionarioExternoTransformer
				.transformar(funcionarioExterno);
		return funcExtDTO;
	}

	@Override
	public FuncionarioExternoDTO guardarActualizarFuncionarioExternoPorIClaveFuncionarioInsExt(
			FuncionarioExternoDTO funcionarioExternoDTO)
			throws NSJPNegocioException {

		if (funcionarioExternoDTO == null
				|| funcionarioExternoDTO.getCveFuncionarioInstExt() == null
				|| funcionarioExternoDTO.getCveFuncionarioInstExt() <= 0L
				|| funcionarioExternoDTO.getConfInstitucionDTO()
						.getConfInstitucionId() == null
				|| funcionarioExternoDTO.getConfInstitucionDTO()
						.getConfInstitucionId() <= 0L
				|| funcionarioExternoDTO.getNombre().trim().isEmpty()
				|| funcionarioExternoDTO.getApellidoPaterno().trim().isEmpty()) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		FuncionarioExterno funcionarioExternoBD = null;

		/**
		 * Este servicio es usado en un WS,y no se debe transformar el
		 * funcionarioExternoId ya que podría ocasionar porblemas entre las BDs
		 * de las diferentes instituciones.
		 */
		if (funcionarioExternoDTO.getFuncionarioExternoId() != null) {
			/**
			 * Esta parte es para guardar funcionarios externos, localmente( por
			 * el momento PJ)
			 */
			funcionarioExternoBD = funcionarioExternoDAO
					.read(funcionarioExternoDTO.getFuncionarioExternoId());
		} else {
			/**
			 * Si el funcionario viene de otra institucion, lo buscamos por su
			 * clave funcionario e institucion
			 */
			funcionarioExternoBD = funcionarioExternoDAO
					.consultarFuncExternoPorClaveFuncExt(funcionarioExternoDTO
							.getCveFuncionarioInstExt(), funcionarioExternoDTO
							.getConfInstitucionDTO().getConfInstitucionId());
		}

		FuncionarioExterno funcionarioExterno = FuncionarioExternoTransformer
				.transformar(funcionarioExternoDTO);

		if (funcionarioExternoBD == null) {
			funcionarioExternoDTO.setFuncionarioExternoId(funcionarioExternoDAO
					.create(funcionarioExterno));
		} else {
			funcionarioExternoDAO.update(FuncionarioExternoTransformer
					.updateFuncionarioExterno(funcionarioExternoBD,
							funcionarioExterno));
			funcionarioExternoDTO.setFuncionarioExternoId(funcionarioExternoBD
					.getFuncionarioExternoId());
		}

		return funcionarioExternoDTO;
	}

	@Override
	public List<FuncionarioExternoDTO> consultarFuncionarioExternoPorFiltroNoAsociadoALaAudiencia(
			FuncionarioExternoDTO funcionarioExternoDTO,
			AudienciaDTO audienciaDTO) throws NSJPNegocioException {

		if (funcionarioExternoDTO == null || audienciaDTO == null
				|| audienciaDTO.getId() <= 0L) {
			return null;
		}

		FuncionarioExterno funcionarioExternoFiltro = FuncionarioExternoTransformer
				.transformar(funcionarioExternoDTO);

		List<FuncionarioExterno> funcionarioExternoList = funcionarioExternoDAO
				.consultarFuncionarioExternoPorFiltroNoAsociadoALaAudiencia(
						funcionarioExternoFiltro,
						new Audiencia(audienciaDTO.getId()));

		List<FuncionarioExternoDTO> funcionarioExternoDTOList = null;

		if (funcionarioExternoList != null && !funcionarioExternoList.isEmpty()) {

			funcionarioExternoDTOList = new ArrayList<FuncionarioExternoDTO>();

			for (FuncionarioExterno funcExterno : funcionarioExternoList) {
				funcionarioExternoDTOList.add(FuncionarioExternoTransformer
						.transformar(funcExterno));
			}
		}

		return funcionarioExternoDTOList;
	}
}
