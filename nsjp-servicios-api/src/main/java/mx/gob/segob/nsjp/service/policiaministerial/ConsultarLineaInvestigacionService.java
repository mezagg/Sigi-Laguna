/**
 * 
 */
package mx.gob.segob.nsjp.service.policiaministerial;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.policiaministerial.LineaInvestigacionDTO;

/**
 * @author adrian
 *
 */
public interface ConsultarLineaInvestigacionService {

	/**
	 * Operaci�n que permite consultar una l�nea de investigaci�n por su ID
	 * @param investigacionDTO: ID
	 * @return
	 * @throws NSJPNegocioException
	 */
	LineaInvestigacionDTO consultarLineaInvestigacion(
			LineaInvestigacionDTO investigacionDTO)throws NSJPNegocioException;

	/**
	 * Operaci�n que permite consultar las l�neas de investigaci�n que pertenezcan a un Expediente
	 * @param expedienteDTO: idexpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<LineaInvestigacionDTO> consultarLineasInvestigacionXExpedienteId(
			ExpedienteDTO expedienteDTO)throws NSJPNegocioException;

}
