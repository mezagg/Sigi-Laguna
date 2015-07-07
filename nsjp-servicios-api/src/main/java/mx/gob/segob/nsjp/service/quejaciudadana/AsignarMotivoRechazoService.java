/**
 * 
 */
package mx.gob.segob.nsjp.service.quejaciudadana;

import mx.gob.segob.nsjp.comun.enums.quejaciudadana.MotivoRechazo;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;

/**
 * @author adrian
 *
 */
public interface AsignarMotivoRechazoService {

	/**
	 * Operaci�n que permite asignar un motivo de rechazo y actualizar el estatus de una queja registrada
	 * @param idQueja
	 * @param motivoRechazo
	 * @return 
	 * @throws NSJPNegocioException
	 */
	Boolean asignarMotivoRechazo(Long idQueja, MotivoRechazo motivoRechazo)throws NSJPNegocioException;

}
