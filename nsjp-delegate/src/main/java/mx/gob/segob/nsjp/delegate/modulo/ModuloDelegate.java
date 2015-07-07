/**
 * 
 */
package mx.gob.segob.nsjp.delegate.modulo;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.usuario.ModuloDTO;

/**
 * @author LuisMG
 *
 */
public interface ModuloDelegate {

	public List<ModuloDTO> consultarModulos()  throws NSJPNegocioException;
	

}
