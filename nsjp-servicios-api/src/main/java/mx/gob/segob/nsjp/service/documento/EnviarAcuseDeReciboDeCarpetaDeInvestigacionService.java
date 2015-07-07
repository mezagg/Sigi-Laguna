/**
* Nombre del Programa	: EnviarAcuseDeReciboDeCarpetaDeInvestigacionService.java
* Autor					: AlejandroGA
* Compania              : Ultrasist
* Proyecto              : NSJP                    Fecha: 19 Dic 2012
* Marca de cambio       : N/A
* Descripcion General   : Interface para enviar los documentos de acuse de recibo
* 						  de la carpeta de investigacion
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
import mx.gob.segob.nsjp.dto.documento.GuardadoDefinitivoDTO;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public interface EnviarAcuseDeReciboDeCarpetaDeInvestigacionService{
	
	/**
	 * M&eacute;todo para enviar el acuse de recibo de la carpeta de investigaci&oacute;n
	 * @param documentoDTO
	 * @throws NSJPNegocioException
	 */
	public void enviarAcuseDeReciboDeCarpetaDeInvestigacionService(
			GuardadoDefinitivoDTO guardadoDefinitivoDTO) throws NSJPNegocioException; 

}
