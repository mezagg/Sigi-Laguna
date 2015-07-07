/**
 * 
 */
package mx.gob.segob.nsjp.service.catalogo;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;

/**
 * @author AlineGS
 *
 */
public interface ConsultarDistritoService {

	/**
	 * Servicio que permite consultar todos los distritos
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<CatDistritoDTO> consultarDistritos()throws NSJPNegocioException;

	/**
	 * Servicio que permite consultar un distrito
	 * @param distritoID
	 * @return
	 * @throws NSJPNegocioException
	 */
	CatDistritoDTO consultarDistritoXId(Long distritoID)throws NSJPNegocioException;

	/**
	 * M&eacute;todo que consulta el distrito por clave Interinstitucional.
	 * con el objetivo de ligarlo al expediente y/o a la solicitud, en 
	 * Replica del Caso, envio de solicitudes de defensor y audiencia.
	 * 
	 * Si se identifica mas de un Distrito, se selecciona el primero de la lista
	 * 
	 * @param claveclaveDistrito
	 * @return
	 * @throws NSJPNegocioException En caso de que no exista un distrito con la clave proporcionada
	 */
	CatDistritoDTO consultarCatDistritoPorClave(String claveclaveDistrito)
			throws NSJPNegocioException;
}
