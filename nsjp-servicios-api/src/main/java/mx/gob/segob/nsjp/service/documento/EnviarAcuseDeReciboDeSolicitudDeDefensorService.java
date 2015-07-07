/**
* Nombre del Programa	: EnviarAcuseDeReciboDeSolicitudDeDefensorService.java
* Autor					: AlejandroGA
* Compania              : Ultrasist
* Proyecto              : NSJP                    Fecha: 29 Nov 2012
* Marca de cambio       : N/A
* Descripcion General   : Interface para enviar los documentos de acuse de recibo
* 						  de la solicitud de defensor
* Programa Dependiente	: N/A
* Programa Subsecuente	: N/A
* Cond. de ejecucion    : N/A
* Dias de ejecucion     : N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                 : N/A
* Compania              : N/A
* Proyecto              : N/A                                 Fecha: N/A
* Modificacion          : N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.documento;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public interface EnviarAcuseDeReciboDeSolicitudDeDefensorService {
	
	/**
	 * Metodo que envia un acuse de solicitud de defensor a la institucion solicitante
	 * a traves de Web Service
	 * @param solicitudDto
	 * @param documentoDTO
	 * @throws NSJPNegocioException
	 */
	public void enviarAcuseDeReciboDeSolicitudDeDefensorService(
			SolicitudDTO solicitudDto, DocumentoDTO documentoDTO)
			throws NSJPNegocioException;
}
