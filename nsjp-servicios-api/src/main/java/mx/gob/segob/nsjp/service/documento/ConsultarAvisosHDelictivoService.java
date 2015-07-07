/**
 * 
 */
package mx.gob.segob.nsjp.service.documento;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.AvisoHechoDelictivoDTO;

/**
 * @author adrian
 *
 */
public interface ConsultarAvisosHDelictivoService {

	/**
	 * Operaci�n que realiza la funcionalidad de consultar los "avisos de posibles hechos delictivos" de acuerdo al estatus que se recibe.
	 * @param estatusNotificacion:idEstatus
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<AvisoHechoDelictivoDTO> consultarAvisosHDelictivoPorEstatus(
			Long estatusNotificacion,Long discriminante)throws NSJPNegocioException;

	/**
	 * Operaci�n que realiza la consulta de un aviso Hecho delictivo por su identificador
	 * @param avisoDTO: idAvisoHechoDelictivo
	 * @return
	 * @throws NSJPNegocioException
	 */
	AvisoHechoDelictivoDTO consultarAvisoHDelictivo(
			AvisoHechoDelictivoDTO avisoDTO)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite consultar el detalle de un aviso
	 * @param avisoId
	 * @return
	 * @throws NSJPNegocioException
	 */
	AvisoHechoDelictivoDTO consultarAvisoHechoXId(Long avisoId)throws NSJPNegocioException;
	/**
	 * Recupera un AvisoHechoDelictivo a partir del aid del expediente
	 * @param idExpediente
	 * @return
	 */
    AvisoHechoDelictivoDTO obtenerAvisoPorIdExpediente(Long idExpediente);

}
