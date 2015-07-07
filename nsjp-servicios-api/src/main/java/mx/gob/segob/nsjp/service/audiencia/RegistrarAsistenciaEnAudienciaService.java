/**
* Nombre del Programa : RegistrarAsistenciaEnAudiencia.java
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
package mx.gob.segob.nsjp.service.audiencia;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoAudienciaDTO;

/**
 * Registra la asistencia de involucrados y funcionarios en una audiencia
 * @version 1.0
 * @author Emigdio Hernández
 *
 */
public interface RegistrarAsistenciaEnAudienciaService {
	
	
	/**
	 * Registrar la asistencia o inasistencia de un involucrado en una audiencia
	 * @param involucradoId EL id único del involucrado en audiencia
	 * @param audienciaId ID de la audiencia
	 * @param presente Presente/Ausente
	 */
	void registrarAsistenciaInvolucrado(Long involucradoId, Long audienciaId, boolean presente) throws NSJPNegocioException;
	
	
	/**
	 * Registrar la asistencia o inasistencia de un funcionario en una audiencia
	 * @param claveFuncionario EL id único del funcionario en audiencia
	 * @param audienciaId ID único de la audiencia
	 * @param presente Presente/Ausente
	 */
	void registrarAsistenciaFuncionario(Long claveFuncionario, Long audienciaId ,boolean presente) throws NSJPNegocioException;
	
	/**
	 * Registrar la asistencia o inasistencia de un funcionario en una audiencia
	 * @param claveFuncionario EL id único del funcionario en audiencia
	 * @param audienciaId ID único de la audiencia
	 * @param esTitular indica si el funcionario es titular o suplente
	 * @param presente Presente/Ausente
	 */
	void registrarAsistenciaFuncionario(Long claveFuncionario, Long audienciaId ,boolean presente, Boolean esTitular) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para registrar la asistencia y titularidad de un funcionario externo en una audiencia determinada
	 * @param funcionarioExternoAudienciaDTO, LLave de la asociaci&oacute;n
	 * @throws NSJPNegocioException
	 */
	void registrarAsistenciaFuncionarioExterno(FuncionarioExternoAudienciaDTO funcionarioExternoAudienciaDTO) throws NSJPNegocioException;

	/**
	 * Consultar la asistencia o inasistencia de un funcionario en una audiencia
	 * @param audienciaId ID único de la audiencia
	 * @param claveFuncionario EL id único del funcionario en audiencia
	 */
	FuncionarioAudienciaDTO consultarAsistenciaFuncionario(Long claveFuncionario, Long audienciaId) throws NSJPNegocioException;
	
	/**
	 * Consultar la asistencia o inasistencia de un Involucrado en una audiencia
	 * @param audienciaId ID único de la audiencia
	 * @param claveFuncionario EL id único del funcionario en audiencia
	 */
	InvolucradoAudienciaDTO consultarAsistenciaInvolucradoAudiencia(Long claveInvolucradoAudiencia, Long audienciaId) throws NSJPNegocioException;
}
