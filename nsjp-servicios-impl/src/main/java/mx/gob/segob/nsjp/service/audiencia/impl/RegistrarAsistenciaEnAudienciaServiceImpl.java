/**
* Nombre del Programa : RegistrarAsistenciaEnAudienciaServiceImpl.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 09/09/2011
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
package mx.gob.segob.nsjp.service.audiencia.impl;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.audiencia.InvolucradoAudienciaDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioAudienciaDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoAudienciaDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioAudienciaIdDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoAudienciaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoAudienciaIdDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.FuncionarioAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudienciaId;
import mx.gob.segob.nsjp.model.InvolucradoAudiencia;
import mx.gob.segob.nsjp.service.audiencia.RegistrarAsistenciaEnAudienciaService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci�n del servicio de negocio para pasar lista de asistencia de involucrados y 
 * funcionarios en una audiencia
 * @version 1.0
 * @author Emigdio Hern�ndez
 *
 */
@Service
@Transactional
public class RegistrarAsistenciaEnAudienciaServiceImpl implements RegistrarAsistenciaEnAudienciaService {
	@Autowired
	InvolucradoAudienciaDAO involucradoAudienciaDAO;
	
	@Autowired
	FuncionarioAudienciaDAO funcionarioAudienciaDAO;
	
	@Autowired
	FuncionarioExternoAudienciaDAO funcionarioExternoAudienciaDAO;
	
	@Autowired
	FuncionarioDAO funcionarioDAO;
	
	Logger log = Logger.getLogger(RegistrarAsistenciaEnAudienciaServiceImpl.class);
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.audiencia.RegistrarAsistenciaEnAudienciaService#registrarAsistenciaInvolucrado(java.lang.Long, java.lang.Long, boolean)
	 */
	@Override
	public void registrarAsistenciaInvolucrado(Long involucradoId,
			Long audienciaId, boolean presente) throws NSJPNegocioException{
	
		involucradoAudienciaDAO.actualizarIndicadorPresenteInvolucrado(involucradoId, audienciaId, presente);
		
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.audiencia.RegistrarAsistenciaEnAudienciaService#registrarAsistenciaFuncionario(java.lang.Long, java.lang.Long, boolean)
	 */
	@Override
	public void registrarAsistenciaFuncionario(Long claveFuncionario,
			Long audienciaId, boolean presente) throws NSJPNegocioException{
		
		registrarAsistenciaFuncionario(claveFuncionario,audienciaId,presente,null);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.audiencia.RegistrarAsistenciaEnAudienciaService#registrarAsistenciaFuncionario(java.lang.Long, java.lang.Long, boolean, java.lang.Boolean)
	 */
	@Override
	public void registrarAsistenciaFuncionario(Long claveFuncionario,
			Long audienciaId, boolean presente, Boolean esTitular)
			throws NSJPNegocioException {
		
		if (esTitular != null
				&& esTitular.equals(Boolean.TRUE)){
			Funcionario actual = funcionarioDAO.read(claveFuncionario);
			funcionarioAudienciaDAO.actualizarInvolucradosNoTitularesPorEspecialidad(claveFuncionario, audienciaId, actual.getTipoEspecialidad().getValorId());			
		}
		
		funcionarioAudienciaDAO.actualizarIndicadorPresenteInvolucrado(claveFuncionario, audienciaId, presente,esTitular);	
	}
	
	
	@Override
	public void registrarAsistenciaFuncionarioExterno(FuncionarioExternoAudienciaDTO funcionarioExternoAudienciaDTO)
			throws NSJPNegocioException {
		
		log.info("***BIENVENIDO AL SERVICIO PARA REGISTRAR ASISTENCIA FUNCIONARIO EXTERNO***");
		
		if (funcionarioExternoAudienciaDTO == null
				|| funcionarioExternoAudienciaDTO
						.getFuncionarioExternoAudienciaIdDTO() == null
				|| funcionarioExternoAudienciaDTO
						.getFuncionarioExternoAudienciaIdDTO().getAudienciaId() == null
				|| funcionarioExternoAudienciaDTO
						.getFuncionarioExternoAudienciaIdDTO().getAudienciaId() <= 0L
				|| funcionarioExternoAudienciaDTO
						.getFuncionarioExternoAudienciaIdDTO()
						.getFuncionarioExternoId() == null
				|| funcionarioExternoAudienciaDTO
						.getFuncionarioExternoAudienciaIdDTO()
						.getFuncionarioExternoId() <= 0L){
			log.error("FuncionarioExternoAudienciaIdDTO nula");
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		FuncionarioExternoAudienciaId funcionarioExternoAudienciaId = new FuncionarioExternoAudienciaId(
				funcionarioExternoAudienciaDTO
						.getFuncionarioExternoAudienciaIdDTO().getAudienciaId(),
				funcionarioExternoAudienciaDTO
						.getFuncionarioExternoAudienciaIdDTO()
						.getFuncionarioExternoId());

		FuncionarioExternoAudiencia funcionarioExternoAudiencia = funcionarioExternoAudienciaDAO.read(funcionarioExternoAudienciaId);
		
		if(funcionarioExternoAudiencia == null){
			log.error("No existe la relacion.............,funcionarioExternoAudiencia");
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		
		funcionarioExternoAudiencia.setEsPresente(funcionarioExternoAudienciaDTO.getEsPresente());
		funcionarioExternoAudiencia.setEsTitular(funcionarioExternoAudienciaDTO.getEsTitular());
		
		funcionarioExternoAudienciaDAO.update(funcionarioExternoAudiencia);
		log.info("La relacion ha sido actualizada....ok");
	}
	
	
	@Override
	public FuncionarioAudienciaDTO consultarAsistenciaFuncionario(Long claveFuncionario, Long audienciaId)
			throws NSJPNegocioException {
		FuncionarioAudiencia funcionarioAudiencia = funcionarioAudienciaDAO.consultarIndicadorPresenteInvolucrado(claveFuncionario, audienciaId);
		
		if(funcionarioAudiencia != null){
			FuncionarioAudienciaDTO funcionarioAudienciaDTO = new FuncionarioAudienciaDTO();
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO(funcionarioAudiencia.getFuncionario().getClaveFuncionario());
			AudienciaDTO audienciaDTO = new AudienciaDTO(funcionarioAudiencia.getId().getAudienciaId());
			FuncionarioAudienciaIdDTO id = new FuncionarioAudienciaIdDTO(funcionarioAudiencia.getId().getAudienciaId(),funcionarioAudiencia.getId().getClaveFuncionario());
			
			funcionarioAudienciaDTO.setId(id);
			funcionarioAudienciaDTO.setFuncionario(funcionarioDTO);
			funcionarioAudienciaDTO.setAudiencia(audienciaDTO);
			funcionarioAudienciaDTO.setEsTitular(funcionarioAudiencia.getEsTitular());
			funcionarioAudienciaDTO.setEsPresente(funcionarioAudiencia.getEsPresente());
			return funcionarioAudienciaDTO;
		}else{
			return null;
		}
	}
	
	@Override
	public InvolucradoAudienciaDTO consultarAsistenciaInvolucradoAudiencia(Long claveInvolucradoAudiencia, Long audienciaId)
			throws NSJPNegocioException {
		InvolucradoAudiencia involucradoAudiencia = involucradoAudienciaDAO.consultarIndicadorPresenteInvolucradoAudiencia(claveInvolucradoAudiencia, audienciaId);
		
		if(involucradoAudiencia != null){
			InvolucradoAudienciaDTO involucradoAudienciaDTO = new InvolucradoAudienciaDTO();
			InvolucradoDTO involucradoDTO = new InvolucradoDTO(involucradoAudiencia.getInvolucrado().getElementoId());
			AudienciaDTO audienciaDTO = new AudienciaDTO(involucradoAudiencia.getId().getaudienciaId());
			InvolucradoAudienciaIdDTO id = new InvolucradoAudienciaIdDTO(involucradoAudiencia.getId().getaudienciaId(),involucradoAudiencia.getId().getInvolucradoId());
			
			involucradoAudienciaDTO.setId(id);
			involucradoAudienciaDTO.setInvolucrado(involucradoDTO);
			involucradoAudienciaDTO.setAudiencia(audienciaDTO);
			involucradoAudienciaDTO.setEsPresente(involucradoAudiencia.getEsPresente());
			return involucradoAudienciaDTO;
		}else{
			return null;
		}
	}
}
