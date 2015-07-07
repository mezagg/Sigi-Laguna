/**
 * 
 */
package mx.gob.segob.nsjp.service.catalogo.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.catalogo.TipoDiscriminante;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.CatDiscriminateDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.model.CatDiscriminante;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.service.catalogo.ConsultarDiscriminanteService;
import mx.gob.segob.nsjp.service.catalogo.ConsultarDistritoService;
import mx.gob.segob.nsjp.service.catalogo.impl.transform.CatDiscriminanteTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AlineGS
 *
 */
@Service
@Transactional
public class ConsultarDiscriminanteServiceImpl implements
		ConsultarDiscriminanteService {
	
	public static final Logger logger = Logger.getLogger(ConsultarDiscriminanteServiceImpl.class);
	
	@Autowired 
	private CatDiscriminateDAO discriminateDAO;
	@Autowired 
	private ConfInstitucionDAO confInstitucionDAO;
	@Autowired 
	private FuncionarioDAO funcionarioDAO;
	@Autowired
	private ConsultarDistritoService consultarDistritoService;
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.catalogo.ConsultarDiscriminanteService#consultarDiscriminantes()
	 */
	@Override
	public List<CatDiscriminanteDTO> consultarDiscriminantes(TipoDiscriminante tipo)
			throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR TODOS LOS DISCRIMINANTES****/");
		
		List<CatDiscriminante> discriminantes=new ArrayList<CatDiscriminante>();
		if(tipo==null)		
			discriminantes = discriminateDAO.findAll("nombre", true);
		else
			discriminantes = discriminateDAO.consultarDiscriminantesXTipo(tipo.ordinal());
		
		List<CatDiscriminanteDTO> discriminatesDTO=new ArrayList<CatDiscriminanteDTO>();
		for (CatDiscriminante discr : discriminantes) {
			discriminatesDTO.add(CatDiscriminanteTransformer.transformarCatDiscriminante(discr));
		}
		
		return discriminatesDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.catalogo.ConsultarDiscriminanteService#consultarDiscriminanteXId(java.lang.Long)
	 */
	@Override
	public CatDiscriminanteDTO consultarDiscriminanteXId(Long discriminanteID)
			throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR DISCRIMINANTE POR ID****/");
		
		if(discriminanteID==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		CatDiscriminante discriminante = discriminateDAO.read(discriminanteID);
		
		return CatDiscriminanteTransformer.transformarCatDiscriminante(discriminante);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.catalogo.ConsultarDiscriminanteService#consultarDiscriminantesXDistrito(java.lang.Long)
	 */
	@Override
	public List<CatDiscriminanteDTO> consultarDiscriminantesXDistrito(
			Long distritoID,TipoDiscriminante tipo) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR DISCRIMINANTES POR DISTRITO****/");
		
		if(distritoID==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		if(tipo == null || tipo.ordinal() < 0 )
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		List<CatDiscriminante> discriminantes = discriminateDAO.consultarDiscriminantesXDistrito(distritoID,tipo.ordinal());

		List<CatDiscriminanteDTO> discriminatesDTO=new ArrayList<CatDiscriminanteDTO>();
		for (CatDiscriminante discr : discriminantes) {
			discriminatesDTO.add(CatDiscriminanteTransformer.transformarCatDiscriminante(discr));
		}
		
		return discriminatesDTO;
	}

	@Override
	public List<CatDiscriminanteDTO> consultarDiscriminantesXDistritoYRol(
			Long distritoId, Long rolId) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR DISCRIMINANTES POR DISTRITO Y ROL****/");

		if (distritoId == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		List<CatDiscriminante> discriminantes = new ArrayList<CatDiscriminante>();

		// Buscar los funcionarios pertenecientes al distrio
		Set<CatDiscriminante> conjuntoDiscriminantes = new HashSet<CatDiscriminante>();
		
		List<Funcionario> funcionarios = funcionarioDAO
				.consultarFuncionariosPorRolyPorDistrito(rolId, distritoId);

		for (Funcionario funcionario : funcionarios) {
			if (funcionario.getDiscriminante() != null) {
				conjuntoDiscriminantes.add(funcionario.getDiscriminante());
			}
		}
		discriminantes.addAll(conjuntoDiscriminantes);

		List<CatDiscriminanteDTO> discriminatesDTO = new ArrayList<CatDiscriminanteDTO>();
		for (CatDiscriminante discr : discriminantes) {
			discriminatesDTO.add(CatDiscriminanteTransformer
					.transformarCatDiscriminante(discr));
		}
		return discriminatesDTO;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.catalogo.ConsultarDiscriminanteService#consultarDiscriminantesXDistrito(java.lang.Long)
	 */
	@Override
	public List<CatDiscriminanteDTO> consultarDiscriminantesXDistritoYTipoInstitucion(
			Long distritoID) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR DISCRIMINANTES POR DISTRITO DE ACUERDO A LA INSTITUCION****/");
		
		if(distritoID==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		ConfInstitucion confInstitucion = confInstitucionDAO.consultarInsitucionActual();
		
		logger.debug(":::::::::::::confInstitucion obtenido::::"+confInstitucion.getConfInstitucionId());
		
		List<CatDiscriminante> discriminantes = new ArrayList<CatDiscriminante>();
		
		if(confInstitucion.getConfInstitucionId().equals(Instituciones.PJ.getValorId())){
			discriminantes = discriminateDAO.consultarDiscriminantesXDistrito(distritoID,TipoDiscriminante.TRIBUNAL.ordinal());
		}
		else if(confInstitucion.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())){
			discriminantes = discriminateDAO.consultarDiscriminantesXDistrito(distritoID,TipoDiscriminante.AGENCIA.ordinal());
		}
		else{
			discriminantes = discriminateDAO.consultarDiscriminantesXDistrito(distritoID,TipoDiscriminante.FANTASMA.ordinal());
		}

		List<CatDiscriminanteDTO> discriminatesDTO=new ArrayList<CatDiscriminanteDTO>();
		for (CatDiscriminante discr : discriminantes) {
			discriminatesDTO.add(CatDiscriminanteTransformer.transformarCatDiscriminante(discr));
		}
		
		return discriminatesDTO;
	}

	public CatDiscriminanteDTO buscarDiscrimianterPorClaveDistritoDefensoria(
			String claveDistrito) throws NSJPNegocioException {

		// DEFENSORIA: Se hace la validaci&oacute para guardar el distrito
		// asociado
		CatDiscriminanteDTO catDiscriminanteDTO = null;
		ConfInstitucion confInstitucion = confInstitucionDAO
				.consultarInsitucionActual();
		if (confInstitucion != null
				&& confInstitucion.getConfInstitucionId().equals(
						Instituciones.DEF.getValorId())) {
			if (claveDistrito != null && !claveDistrito.trim().isEmpty()) {

				logger.debug("Distrito DEF:" + claveDistrito);
				CatDistritoDTO catDistritoDTO = consultarDistritoService
						.consultarCatDistritoPorClave(claveDistrito);

				// Se asocia al expediente
				if (catDistritoDTO != null
						&& catDistritoDTO.getCatDistritoId() != null) {

					// Se busca el discrimiante que sea agencia
					TipoDiscriminante tipo = TipoDiscriminante.FANTASMA;
					List<CatDiscriminanteDTO> discriminantes = this
							.consultarDiscriminantesXDistrito(
									catDistritoDTO.getCatDistritoId(), tipo);
					if (discriminantes != null && !discriminantes.isEmpty()
							&& discriminantes.get(0) != null) {
						catDiscriminanteDTO = discriminantes.get(0);
					}
				}
			}
		}
		return catDiscriminanteDTO;
	}
}
