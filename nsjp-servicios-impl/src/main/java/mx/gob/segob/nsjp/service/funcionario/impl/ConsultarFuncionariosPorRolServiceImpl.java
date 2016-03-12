/**
* Nombre del Programa : ConsultarPorPuestoServiceImpl.java
* Autor                            : rgama
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    
* Marca de cambio        : N/A
* Descripcion General    : Implementacion del servicio para designar un abogado defensor
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
package mx.gob.segob.nsjp.service.funcionario.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.usuario.RolDAO;
import mx.gob.segob.nsjp.dao.usuario.UsuarioRolDAO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoViewDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.CatDiscriminante;
import mx.gob.segob.nsjp.model.CatDistrito;
import mx.gob.segob.nsjp.model.CatUIEspecializada;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Rol;
import mx.gob.segob.nsjp.model.UsuarioRol;
import mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionariosPorRolService;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.RolTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Prueba unitaria para consultar una lista de funcionarios por su puesto
	 * Este metodo sera usado para obtener la informacion de:
	 * -Abogado defensor.
	 * -Coordinador de defensores.
	 * -Coordinador de fiscales.
	 * -Coordinador de servicios periciales.
	 * -Fiscal general.
	 * -Fiscal.
	 * -Juez.
	 * -Magistrado.
 * @version 1.0
 * @author rgama
 */
@Service
@Transactional
public class ConsultarFuncionariosPorRolServiceImpl implements ConsultarFuncionariosPorRolService {
	
	private final static Logger logger = Logger.getLogger(ConsultarFuncionariosPorRolServiceImpl.class);
	
	@Autowired
	private FuncionarioDAO funcionarioDAO;
	@Autowired
	private UsuarioRolDAO usuarioRolDAO;
	@Autowired
	private RolDAO rolDAO;
	
	
	/**
	 * Servicio que permite consultar una lista de funcionarios por su puesto
	 * Este metodo sera usado para obtener la informacion de:
	 * -Abogado defensor.
	 * -Coordinador de defensores.
	 * -Coordinador de fiscales.
	 * -Coordinador de servicios periciales.
	 * -Fiscal general.
	 * -Fiscal.
	 * -Juez.
	 * -Magistrado.
	 * @param idPuesto: Los identificadores se pueden obtener del enum Puestos
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioDTO> consultarFuncionariosPorRol(Long idPuesto)
	throws NSJPNegocioException{
		
		if(logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR UN FUNCIONARIO POR PUESTO ****/");				
		
		if (idPuesto == null) 
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);		
		
		List<Funcionario> loFuncionarios = funcionarioDAO.consultarFuncionariosPorRol(idPuesto);
		
		List<FuncionarioDTO> loFuncionariosDTO = new ArrayList<FuncionarioDTO>();
		for (Funcionario funcionario : loFuncionarios) {
			loFuncionariosDTO.add(FuncionarioTransformer.transformarFuncionario(funcionario));	
		}
		return loFuncionariosDTO;		
	}



	public List<FuncionarioDTO> consultarFuncionariosPorAreayPuesto(Long area,
			Long puesto) throws NSJPNegocioException {
		List<Funcionario> lista = funcionarioDAO.consultarFuncionariosPorAreaYPuesto(area,puesto);
		List<FuncionarioDTO> funcionarios = new LinkedList<FuncionarioDTO>();
		for(Funcionario func : lista){
			funcionarios.add(FuncionarioTransformer.transformarFuncionario(func));
		}
		return funcionarios;
	}

	public List<FuncionarioDTO> consultarFuncionariosPorRolMultiRol(Long idRol) throws NSJPNegocioException {
		if(idRol==null || idRol<0){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		List<Funcionario> lista = funcionarioDAO.consultarFuncionariosPorRolMultiRol(idRol);
		List<FuncionarioDTO> funcionarios = new LinkedList<FuncionarioDTO>();
		for(Funcionario func : lista){
			funcionarios.add(FuncionarioTransformer.transformarFuncionario(func));
		}
		return funcionarios;
	}
	
	//
	@Override
	public List<FuncionarioDTO> consultarFuncionariosSubordinados(
			UsuarioDTO usuarioDTO) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA OBTENER LOS FUNCIONARIOS SUBORDINADOS DE OTRO FUNCIONARIO ****/");
		
		if (usuarioDTO==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		Funcionario funcionario = new Funcionario();
		List<Long> rolesSubordinados = new ArrayList<Long>();
		CatDiscriminante discriminante = new CatDiscriminante();
		CatUIEspecializada unidadEspecializada = null; 
		
		if(usuarioDTO.getFuncionario() != null 
				&& usuarioDTO.getFuncionario().getDiscriminante() != null
				&& usuarioDTO.getFuncionario().getDiscriminante().getDistrito() != null
				&& usuarioDTO.getFuncionario().getDiscriminante().getDistrito().getCatDistritoId() != null
				&& usuarioDTO.getFuncionario().getDiscriminante().getDistrito().getCatDistritoId() > 0){
			
			CatDistrito distrito = new CatDistrito(usuarioDTO.getFuncionario().getDiscriminante().getDistrito().getCatDistritoId());
			discriminante.setDistrito(distrito);
		}
		
		if(usuarioDTO.getFuncionario() != null
				&& usuarioDTO.getFuncionario().getUnidadIEspecializada() != null 
				&& usuarioDTO.getFuncionario().getUnidadIEspecializada().getCatUIEId() != null
				&& usuarioDTO.getFuncionario().getUnidadIEspecializada().getCatUIEId() > 0){
			
			unidadEspecializada = new CatUIEspecializada(usuarioDTO.getFuncionario().getUnidadIEspecializada().getCatUIEId());					
		}
		
		Roles rol = Roles.getByValor(usuarioDTO.getRolACtivo().getRol().getRolId());
		
		
		switch (rol){
			case PROCURADOR:
			case SUBPROCURADOR:
			case DIRECTOR_UI:
			case DIRECTOR_GENERAL:
				rolesSubordinados.add(Roles.PROCURADOR.getValorId());
				rolesSubordinados.add(Roles.SUBPROCURADOR.getValorId());
				rolesSubordinados.add(Roles.DIRECTOR_GENERAL.getValorId());
				rolesSubordinados.add(Roles.DIRECTOR_UI.getValorId());
				rolesSubordinados.add(Roles.COORDINADORVIS.getValorId());
				rolesSubordinados.add(Roles.VISITADOR.getValorId());
				rolesSubordinados.add(Roles.COORDINADORAMPGENERAL.getValorId());
				rolesSubordinados.add(Roles.COORDINADORAMP.getValorId());
				rolesSubordinados.add(Roles.AGENTEMP.getValorId());
				rolesSubordinados.add(Roles.ATPENAL.getValorId());
				rolesSubordinados.add(Roles.COORDINADORJAR.getValorId());
				rolesSubordinados.add(Roles.FACILITADOR.getValorId());
				rolesSubordinados.add(Roles.COORDINADORAT.getValorId());
				rolesSubordinados.add(Roles.POLICIAMINISTER.getValorId());
				rolesSubordinados.add(Roles.ADMINAT.getValorId());
				rolesSubordinados.add(Roles.UAVD.getValorId());
				rolesSubordinados.add(Roles.UAVDATNPSICOLOGICA.getValorId());
				rolesSubordinados.add(Roles.UAVDJURIDICO.getValorId());
				rolesSubordinados.add(Roles.UAVDTRABAJOSOCIAL.getValorId());
				rolesSubordinados.add(Roles.PERITOAMP.getValorId());
				rolesSubordinados.add(Roles.COORDPERFIS.getValorId());
				break;
			case COORDINADORVIS:
				rolesSubordinados.add(Roles.COORDINADORVIS.getValorId());
				rolesSubordinados.add(Roles.VISITADOR.getValorId());
				break;
			case COORDINADORAMPGENERAL:
				rolesSubordinados.add(Roles.COORDINADORAMPGENERAL.getValorId());
				rolesSubordinados.add(Roles.COORDINADORAMP.getValorId());
				rolesSubordinados.add(Roles.AGENTEMP.getValorId());
				rolesSubordinados.add(Roles.POLICIAMINISTER.getValorId());
				funcionario.setDiscriminante(discriminante);
				break;
			case COORDINADORAMP:
				rolesSubordinados.add(Roles.COORDINADORAMP.getValorId());
				rolesSubordinados.add(Roles.AGENTEMP.getValorId());
				funcionario.setDiscriminante(discriminante);
				funcionario.setUnidadIEspecializada(unidadEspecializada);
				break;
			case COORDINADORAT:
				rolesSubordinados.add(Roles.ATPENAL.getValorId());
				rolesSubordinados.add(Roles.COORDINADORAT.getValorId());
				rolesSubordinados.add(Roles.ADMINAT.getValorId());
				funcionario.setDiscriminante(discriminante);
				break;
			case COORDINADORJAR:
				rolesSubordinados.add(Roles.COORDINADORJAR.getValorId());
				rolesSubordinados.add(Roles.FACILITADOR.getValorId());
				funcionario.setDiscriminante(discriminante);
				break;
			case UAVD:
				rolesSubordinados.add(Roles.UAVD.getValorId());
				rolesSubordinados.add(Roles.UAVDATNPSICOLOGICA.getValorId());
				rolesSubordinados.add(Roles.UAVDJURIDICO.getValorId());
				rolesSubordinados.add(Roles.UAVDTRABAJOSOCIAL.getValorId());
				break;
			case COORDPERFIS:
				rolesSubordinados.add(Roles.PERITOAMP.getValorId());
				rolesSubordinados.add(Roles.COORDPERFIS.getValorId());
				break;
			case VISITADOR:
			case AGENTEMP:
			case ATPENAL:
			case ADMINAT:
			case FACILITADOR:
			case POLICIAMINISTER:
			case UAVDATNPSICOLOGICA:
			case UAVDJURIDICO:
			case UAVDTRABAJOSOCIAL:
			case PERITOAMP:
				switch (rol){
				case AGENTEMP:
					funcionario.setUnidadIEspecializada(unidadEspecializada);
				case ATPENAL:
				case ADMINAT:
				case FACILITADOR:
				case POLICIAMINISTER:
					funcionario.setDiscriminante(discriminante);
					break;
				}
				rolesSubordinados.add(rol.getValorId());
				break;
			default:
				rolesSubordinados.add(rol.getValorId());
				break;
		}
		
		logger.info("rolesSubordinados: "+rolesSubordinados.toString());
		
		List<Funcionario> funcionariosSubordiandos = 
			funcionarioDAO.consultarFuncionariosSubordinados(funcionario,rolesSubordinados);
			
		List<FuncionarioDTO> funcionariosRetorno = new ArrayList<FuncionarioDTO>();
		for (Funcionario funcionarioSub : funcionariosSubordiandos) {
			funcionariosRetorno.add(FuncionarioTransformer.transformarFuncionarioIntermedio(funcionarioSub));
		}
				
		return funcionariosRetorno;
	}

	
	@Override
	public FuncionarioDTO obtenerDefensorAsignadoAExpediente(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA OBTENER EL DEFENSOR ASIGNADO A UN EXPEDIENTE ****/");
		
		if (expedienteDTO.getExpedienteId()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		Funcionario funcionario = funcionarioDAO.obtenerDefensorAsignadoAExpediente(expedienteDTO.getExpedienteId());
		
		FuncionarioDTO funcionarioDTO = FuncionarioTransformer.transformarFuncionario(funcionario);
		return funcionarioDTO;
	}

	
	@Override
	public List<FuncionarioDTO> obtenerFuncionarosConUsuario()
			throws NSJPNegocioException {
		List<FuncionarioDTO> funcionarios = new LinkedList<FuncionarioDTO>();
		List<Funcionario> result = funcionarioDAO.obtenerFuncionariosConUsuario();
		for (Funcionario funcionario : result) {
			funcionarios.add(FuncionarioTransformer.transformarNombreyClaveDeFuncionario(funcionario));
		}
		 return funcionarios;
	}


	@Override
	public FuncionarioDTO obtenerFuncionarioSuperior(
			FuncionarioDTO funcionarioDTO) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA OBTENER EL FUNCIONARIO SUPERIOR DE OTRO FUNCIONARIO ****/");
		
		if (funcionarioDTO.getClaveFuncionario()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		Funcionario funSolicitante = funcionarioDAO.read(funcionarioDTO.getClaveFuncionario());
		
		FuncionarioDTO funDTORetorno = new FuncionarioDTO();
		if (funSolicitante.getFuncionarioJefe()!=null &&
				funSolicitante.getFuncionarioJefe().getClaveFuncionario()>0) {
			Funcionario funSuperior = funcionarioDAO.read(funSolicitante.getFuncionarioJefe().getClaveFuncionario());
			funDTORetorno = FuncionarioTransformer.transformarFuncionario(funSuperior);
		}
		
		return funDTORetorno;
	}

	@Override
	public FuncionarioDTO obtenerFuncionarioPorNombreCompleto(
			String nombreCompleto) {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA OBTENER EL FUNCIONARIO DE ACUERDO A SU NOMBRE COMPLETO ****/");
		
		Funcionario funcionario = funcionarioDAO.obtenerFuncionarioPorNombreCompleto(nombreCompleto);
		
		if (funcionario!=null) {
			logger.debug("/**** SE OBTUVO EL FUNCIONARIO SOLICITADO ****/");
			return  FuncionarioTransformer.transformarFuncionario(funcionario);
		}
		
		return null;
	}
	
	
	@Override
	public List<FuncionarioDTO> consultarFuncionariosPorDicriminanteYRol(Long catDiscriminanteId, Long idRol, Long idUIE)
			throws NSJPNegocioException {
		
		logger.info("/**** SERVICIO PARA OBTENER FUNCIONARIOS POR DISCRIMINANTE Y ROL (OPCIONAL) ****/");
		
		if(catDiscriminanteId==null || catDiscriminanteId <= 0 || idRol==null || idRol <= 0 )
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES); 
			
		List<FuncionarioDTO> funcionarios = new LinkedList<FuncionarioDTO>();
		//Se optiene los funcionarios
		List<Funcionario> result = funcionarioDAO.consultarFuncionariosPorDiscriminante(catDiscriminanteId, idUIE);
		
		
		List<Funcionario> listaFuncionario =  new ArrayList();
		siguiente:
		for (Funcionario funcionario : result) {
			String claveUsuario = funcionario.getUsuario()!= null? funcionario.getUsuario().getClaveUsuario():null;
		
			//Obtener la lista de roles del usuario
			if(claveUsuario!=null){
				List<UsuarioRol> listaRoles = usuarioRolDAO.consultarRolesDeUsuario(claveUsuario);
				
				//Buscar entre los roles del usuario(Funcionario) si se encuentra el rol indicado.
				for (UsuarioRol usuarioRol : listaRoles) {
					if( usuarioRol.getRol()!= null && usuarioRol.getRol().getRolId()!= null && 
							usuarioRol.getRol().getRolId().equals(idRol)){
						//Encontrado
						listaFuncionario.add(funcionario);
						continue siguiente;
					}
				}
			}
			result = listaFuncionario;
		}
		
		for (Funcionario funcionario : result) {
			funcionarios.add(FuncionarioTransformer.transformarFuncionario(funcionario));
		}
		
		 return funcionarios;
	}
	
	public List<FuncionarioDTO> consultarFuncionariosPorRolyPorDistrito(Long idRol, Long idDistrito)  throws NSJPNegocioException {
	
		List<Funcionario> lista = funcionarioDAO.consultarFuncionariosPorRolyPorDistrito(idRol, idDistrito);
		List<FuncionarioDTO> funcionarios = new LinkedList<FuncionarioDTO>();
		for(Funcionario func : lista){
			funcionarios.add(FuncionarioTransformer.transformarNombreyClaveDeFuncionario(func));
		}
		return funcionarios;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionariosPorRolService#validarFuncionariosRol(java.util.List, java.lang.Long)
	 */
	public List<InvolucradoViewDTO> validarFuncionariosRol(List<FuncionarioDTO> funcionariosAntes, Long idRol)  throws NSJPNegocioException {

		if (funcionariosAntes == null || funcionariosAntes.isEmpty() || idRol == null || idRol <= 0)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		List<Funcionario> funci = new ArrayList<Funcionario>();
		for(FuncionarioDTO func : funcionariosAntes){
			funci.add(FuncionarioTransformer.transformarFuncionario(func));
		}

		List<Funcionario> lista = funcionarioDAO.validarFuncionariosRol(funci,idRol);

		List<InvolucradoViewDTO> funcionarios = new LinkedList<InvolucradoViewDTO>();
		for(Funcionario func : lista){
			funcionarios.add(InvolucradoTransformer.transformarInvolucradoView(func));
		}
		return funcionarios;
	}

	@Override
	public List<FuncionarioDTO> consultarFuncionariosPorDicriminanteYRolYUIE(Long catDiscriminanteId, Long idRol, Long idUIE) throws NSJPNegocioException {
		return funcionarioDAO.consultarFuncionariosPorDiscriminante(catDiscriminanteId,idRol,idUIE);
	}
}
