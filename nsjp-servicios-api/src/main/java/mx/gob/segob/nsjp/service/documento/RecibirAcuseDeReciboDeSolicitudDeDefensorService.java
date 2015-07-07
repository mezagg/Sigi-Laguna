/**
* Nombre del Programa	: RecibirAcuseDeReciboDeSolicitudDeDefensorService.java
* Autor					: AlejandroGA
* Compania              : Ultrasist
* Proyecto              : NSJP                    Fecha: 28 Nov 2012
* Marca de cambio       : N/A
* Descripcion General   : Interface para recibir los documentos de acuse de recibo
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
import mx.gob.segob.nsjp.dto.documento.DocumentoWSDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudWSDTO;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public interface RecibirAcuseDeReciboDeSolicitudDeDefensorService {
	
	/**
	 * Servicio que permite recibir un documento de acuse de recibo de solicitud
	 * de defensor y dependiento de la institucion (PG o PJ) relaciona el documento
	 * al expediente o lo relaciona a la audiencia, respectivamente
	 * @param solicitudWsDto - solicitud a la que se le relacionara el acuse
	 * @param documentoWsDto - acuse de recibo de la solicitud
	 * @return
	 * @throws NSJPNegocioException
	 */
	public DocumentoWSDTO recibirAcuseDeReciboDeSolicitudDeDefensor(
			SolicitudWSDTO solicitudWsDto, DocumentoWSDTO documentoWsDto)
			throws NSJPNegocioException;

}
