/**
 * 
 */
package mx.gob.segob.nsjp.delegate.policiaministerial;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.policiaministerial.ComentarioDTO;
import mx.gob.segob.nsjp.dto.policiaministerial.LineaInvestigacionDTO;

/**
 * @author adrian
 *
 */
public interface ComentarioDelegate {
	
	/**
	 * Operaci�n que permite guardar / actualizar un comentario
	 * guardar: Id = 0 o NULL
	 * @param comentarioDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long guardarComentario(ComentarioDTO comentarioDTO)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite consultar un comentario por su identificador
	 * @param comentarioDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ComentarioDTO consultarComentario(ComentarioDTO comentarioDTO)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite consultar los comentarios de una l�nea de investigaci�n
	 * @param investigacionDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ComentarioDTO> consultarComentariosXLinea(LineaInvestigacionDTO investigacionDTO)throws NSJPNegocioException;

}
