/**
* Nombre del Programa		: ConsultarProbablesResponsablesParaSolucitudDeDefensorService.java
* Autor						: AlejandroGA
* Compania					: Ultrasist
* Proyecto                  : NSJP                    Fecha: 31 Oct 2012
* Marca de cambio        	: N/A
* Descripcion General    	: Servicio para obtener probables responsables 
* 							  para enviar la solicitud de defensor
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
package mx.gob.segob.nsjp.service.involucrado;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;

/**
 * Servicio para obtener la lista de probables responsables candidatos para
 * enviar la solicitud de defensor
 * 
 * @version 1.0
 * @author AlejandroGA
 * 
 */
public interface ConsultarProbablesResponsablesParaSolucitudDeDefensorService {

	/**
	 * M&eacute;todo para consultar los probables responsables para 
	 * enviar la solicitud de defensor acuerdo a las siguientes reglas:
	 * 
	 * a. La solicitud de defensor &uacute;nicamente se puede realizar para los
	 * 		probables responsables est&eacute;n o no detenidos y sin defensor que son
	 * 		personas f&iacute;sicas. 
	 * b. Debe haber al menos una relaci&oacute;n
	 * 		delito-persona para cada probable responsable del que se enviar&aacute;
	 * 		en la solicitud de defensor.
	 * 
	 * @param expedienteDTO
	 * @return string que se mostrar&aacute; en vistas con la respuesta
	 * @throws NSJPNegocioException
	 */
	public List<InvolucradoDTO> consultarProbablesResponsablesParaSolucitudDeDefensor(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException;

}
