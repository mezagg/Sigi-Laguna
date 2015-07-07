/**
 * 
 */
package mx.gob.segob.nsjp.service.documento;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;

/**
 * @author adrian
 *
 */
public interface ActualizarNotificacionService {

	/**
	 * Operaci�n que realiza la funcionalidad de actualizar el estado de una notificaci�n.
	 * @param notificacionDTO: idNotificaci�n
	 * @param estatusNotificacion: idEstatus
	 * @return
	 * @throws NSJPNegocioException
	 */
	NotificacionDTO actualizarEstatusNotificacion(
			NotificacionDTO notificacionDTO, Long estatusNotificacion)throws NSJPNegocioException;

}
