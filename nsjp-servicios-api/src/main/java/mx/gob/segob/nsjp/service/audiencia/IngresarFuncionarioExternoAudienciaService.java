/**
 * Nombre del Programa	: IngresarFuncionarioExternoAudienciaService.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Feb 2013
 * Marca de cambio      : N/A
 * Descripcion General  : perimite asociar un funcionario externo con una audiencia
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
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;

/**
 * Perimite asociar un funcionario externo con una audiencia
 * 
 * @author AlejandroGA
 * @version 1.0
 */
public interface IngresarFuncionarioExternoAudienciaService {

	/**
	 * M&eacute;todo que permite relacionar un funcionario externo con una
	 * audiencia, se puede invocar con el parametro Boolean ingresar/Actualizar=
	 * true, de modo que el funcionario Externo puede ser actualizado o
	 * ingresado
	 * 
	 * @param funcionarioExternoDTO
	 * @param audienciaDTO
	 * @param ingresarActualizarFuncExterno
	 * @throws NSJPNegocioException
	 */
	public FuncionarioExternoAudienciaDTO ingresarFuncionarioExternoAudiencia(
			FuncionarioExternoDTO funcionarioExternoDTO,
			AudienciaDTO audienciaDTO, Boolean ingresarActualizarFuncExterno)
			throws NSJPNegocioException;

}
