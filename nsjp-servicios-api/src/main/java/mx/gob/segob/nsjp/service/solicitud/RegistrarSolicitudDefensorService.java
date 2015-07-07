/**
* Nombre del Programa : ConsultarSolicitudesDefensoriaService.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 02/11/2012
* Marca de cambio        : N/A
* Descripcion General    : Contrato del servicio de registro de solicitudes de defensor&iacute;a
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
package mx.gob.segob.nsjp.service.solicitud;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;

/**
 * Contrato del servicio de registro de solicitudes de defensor&iacute;a
 * @author GustavoBP
 */
public interface RegistrarSolicitudDefensorService {

	/**
	 * Registra la solicitud defensor a los involucrados
	 * @param involucrados
	 * @param solicitudDefensor
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean registrarSolicitudDefensorInvolucrados(List<InvolucradoDTO> involucrados,
			SolicitudDefensorDTO solicitudDefensor) throws NSJPNegocioException; 
	
}
