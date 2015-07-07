/**
 * Nombre del Programa	: ConsultarFuncionarioExternoAudienciaService.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 05 Marzo 2013
 * Marca de cambio      : N/A
 * Descripcion General  : Interface que permite consultar un funcionario externo asociado a 
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
package mx.gob.segob.nsjp.service.audiencia;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaIdDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;

/**
 * Interface que permite consultar un funcionario externo asociado a una
 * audiencia
 * 
 * @author AlejandroGA
 * @version 1.0
 */
public interface ConsultarFuncionarioExternoAudienciaService {

	/**
	 * M&eacute;todo que permite consultar funcionario externo audiencia por Id
	 * 
	 * @param funcionarioExternoAudienciaIdDTO
	 *            , La llave del funcionario externo audiencia
	 *            
	 * @return FuncionarioExternoAudienciaDTO, la asociacion del funcionario
	 *         externo con la audiencia
	 * @throws NSJPNegocioException
	 */
	public FuncionarioExternoAudienciaDTO ConsultarFuncionarioExternoAudienciaPorId(
			FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO)
			throws NSJPNegocioException;

	/**
	 * M&eacute;todo que permite consultar los funcionarios externos asociados a determinada audiencia
	 * por filtro, rolId y audienciaId
	 * @param funcionarioExternoAudienciaDTO, Se obtiene el rolId y la audienciaId
	 * @return List<FuncionarioExternoDTO>, La lista de funcionarios Externos asociados a la audiencia
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioExternoDTO> consultarFuncionarioExternoAudienciaPorRol(
			FuncionarioExternoAudienciaDTO funcionarioExternoAudienciaDTO)
			throws NSJPNegocioException;

	/**
	 * M&eacute;todo que permite consultar los funcionarios externos asociados a determinada audiencia
	 * por audienciaId, con sus respectivas notificiones asociadas
	 * @param funcionarioExternoAudienciaDTO, Filtro Audiencia Id
	 * @return List<FuncionarioExternoDTO>, Lista de funcionarios externos asociados a las audiencia con notificaciones asociadas
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioExternoDTO> consultarFuncionarioExternoAudienciaNotificaciones(
			AudienciaDTO audienciaDTO)
			throws NSJPNegocioException;
}
