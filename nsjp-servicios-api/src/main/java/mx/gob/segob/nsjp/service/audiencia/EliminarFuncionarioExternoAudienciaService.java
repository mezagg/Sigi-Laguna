/**
 * Nombre del Programa	: EliminarFuncionarioExternoAudienciaService.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 11 Marzo 2013
 * Marca de cambio      : N/A
 * Descripcion General  : Perimite eliminar un funcionario externo de una audiencia
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

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaIdDTO;

/**
 * Perimite eliminar un funcionario externo de una audiencia
 * @author AlejandroGA
 * @version 1.0
 */
public interface EliminarFuncionarioExternoAudienciaService {
	
	
	/**
	 * M&eacute;todo que permite eliminar la asociacion de un funcionario
	 * externo con una audiencia
	 * 
	 * @param funcionarioExternoAudienciaIdDTO
	 *            , LLave de la relacion con funcionarioExternoId y audienciaId
	 * @throws NSJPNegocioException
	 */
	public void eliminarFuncionarioExternoDeAudiencia(
			FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO)
			throws NSJPNegocioException;

}
