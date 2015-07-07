/**
* Nombre del Programa		: AtenderSolicitudDeDefensorService.java
* Autor						: AlejandroGA
* Compania					: Ultrasist
* Proyecto                  : NSJP                    Fecha: 09 Nov 2012
* Marca de cambio        	: N/A
* Descripcion General    	: Servicio para atender una solicitud de defensor
* Programa Dependiente  	: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion        : N/A
* Dias de ejecucion         : N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                     :N/A
* Compania               	:N/A
* Proyecto                 	:N/A                                 Fecha: N/A
* Modificacion           	:N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.solicitud;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Servicio para atender una solicitud de defensor
 * @version 1.0
 * @author AlejandroGA
 *
 */
public interface AtenderSolicitudDeDefensorService {

	public String atenderSolicitudDeDefensor(
			SolicitudDefensorDTO solicitudDefensorDTO,UsuarioDTO usuarioFirmado)
			throws NSJPNegocioException;

}
