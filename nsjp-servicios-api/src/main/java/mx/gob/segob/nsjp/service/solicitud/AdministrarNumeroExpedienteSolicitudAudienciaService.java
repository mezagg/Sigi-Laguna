/**
 * Nombre del Programa 	: AAdministrarNumeroExpedienteSolicitudAudienciaService.java                                    
 * Autor               	: AlejandroGA                                              
 * Compania            	: Ultrasist                                                
 * Proyecto            	: NSJP              			Fecha:16/05/2013 
 * Marca de cambio     	: N/A                                                     
 * Descripcion General  : Clase para lo relacionado con el numero de 
 * 							expediente en las solicitudes de audiencia
 * Programa Dependiente : N/A                                                      
 * Programa Subsecuente : N/A                                                      
 * Cond. de ejecucion   : N/A                                                  
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                               MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                 :N/A                                                           
 * Compania              :N/A                                                           
 * Proyecto              :N/A                                   Fecha: N/A       
 * Modificacion          :N/A                                                           
 *------------------------------------------------------------------------------      
 */
package mx.gob.segob.nsjp.service.solicitud;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public interface AdministrarNumeroExpedienteSolicitudAudienciaService {

	/**
	 * M&eacute;todo para verificar si se puede editar el numero de expediente
	 * en una solicitud de audiencia, recibe como par&aacute;metro el
	 * numeroExpedienteId, y realiza lo siguiente: 1) Verifica si el
	 * par&aacute;metro de edici&oacute;n de n&uacute;mero de esta encendido (es
	 * decir regresa true).
	 * 
	 * 2) Verifica que el numeroExpedienteId "NO" tiene asociadas audiencia, las
	 * cuales tengan una dFechaAudiencia que no sea NULL.
	 * 
	 * Si ambos servicios devuelven TRUE, entonces la respuesta es TRUE, en
	 * cualquier otro caso FALSE.
	 * 
	 * 
	 * @param expedienteDTO
	 *            , par&aacute;metro del cual obtenemos el numeroExpedienteId
	 * @return Booleano con la respuesta, se puede editar o no
	 * @throws NSJPNegocioException
	 */
	Boolean editarNumeroExpedienteSolicitudAudiencia(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para actualizar el N&uacute;mero de expediente (String)
	 * @param expedienteDTO, con el numeroExpedienteId
	 *					   , con el nuevoNumeroExpediente (String) a actualizar
	 * @return Booleano con la respuesta, se pudo actualizar (true) o no (false)
	 * @throws NSJPNegocioException, si NO es posible actualizar.
	 */
	Boolean actualizarNumeroExpedienteSolicitudAudiencia(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException;
}
