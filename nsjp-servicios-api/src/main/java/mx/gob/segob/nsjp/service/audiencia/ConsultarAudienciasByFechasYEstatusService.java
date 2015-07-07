/**
* Nombre del Programa : ConsultarAudienciasByFechasYEstatusService.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 9 Aug 2011
* Marca de cambio        : N/A
* Descripcion General    : Realiza la consulta de audiencias de acuerdo a un estatus y/o rango de fechas
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

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDefensoriaWSDTO;

/**
 * Realiza la consulta de audiencias de acuerdo a un estatus y/o rango de fechas.
 * @version 1.0
 * @author cesarAgustin
 *
 */
public interface ConsultarAudienciasByFechasYEstatusService {

	/**
	 * Consulta las audiencias de acuerdo a los paramtros enviados
	 * @author cesarAgustin
	 * @param audienciaWSDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<AudienciaDefensoriaWSDTO> consultarAudienciasByFechasYEstatus(AudienciaDefensoriaWSDTO audienciaWSDTO) throws NSJPNegocioException;


	/**
	 * Consulta el una audiencia por id
	 * @param audienciaWSDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public AudienciaDefensoriaWSDTO consultarAudienciaById(AudienciaDefensoriaWSDTO audienciaWSDTO) throws NSJPNegocioException;

	/**
	 * Consulta un listado de audiencias, de acuerdo a los par�metros enviados en el audienciaWSDTO
	 * @param audienciaWSDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<AudienciaDefensoriaWSDTO> consultarAudienciasPorFechasYSolicitudTranscripcion(
			AudienciaDefensoriaWSDTO audienciaWSDTO) throws NSJPNegocioException;
	
	
	/**
	 * Permite consultar el total de investigaciones judicializadas por Distrito, Tribunal y Estatus para 
	 * un periodo determinado
	 * @param fechaFin
	 * @param fechaFinal
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<String> consultarIndicadorDeAudienciasPorFechas(String fechaInicial, String fechaFin) throws NSJPNegocioException;
}
