/**
 * 
 */
package mx.gob.segob.nsjp.service.policiaministerial;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.policiaministerial.ComentarioDTO;
import mx.gob.segob.nsjp.dto.policiaministerial.LineaInvestigacionDTO;

/**
 * @author adrian
 *
 */
public interface ConsultarComentarioService {

	/**
	 * Operaci�n que permite consultar un comentario por su identificador
	 * @param comentarioDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	ComentarioDTO consultarComentario(ComentarioDTO comentarioDTO)throws NSJPNegocioException;

	/**
	 * Operaci�n que permite consultar los comentarios de una l�nea de investigaci�n
	 * @param investigacionDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ComentarioDTO> consultarComentariosXLinea(
			LineaInvestigacionDTO investigacionDTO)throws NSJPNegocioException;

}
