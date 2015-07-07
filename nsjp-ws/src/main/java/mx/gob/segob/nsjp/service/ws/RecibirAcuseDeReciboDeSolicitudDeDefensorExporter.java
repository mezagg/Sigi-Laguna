/**
 * Nombre del Programa	: RecibirAcuseDeReciboDeSolicitudDeDefensorExporter.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Nov 2012
 * Marca de cambio      : N/A
 * Descripcion General  : Interfaz para exponer los servicios como web service
 * Programa Dependiente	: N/A
 * Programa Subsecuente	: N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                : N/A
 * Compania             : N/A
 * Proyecto             : N/A                                 Fecha: N/A
 * Modificacion         : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.ws;

import javax.jws.WebService;

import mx.gob.segob.nsjp.service.documento.RecibirAcuseDeReciboDeSolicitudDeDefensorService;

/**
 * Interfaz para exponer el serivio de recibir acuse de solicitud de defensor
 * como web service
 * @author AlejandroGA
 * @version 1.0 
 */

@WebService
public interface RecibirAcuseDeReciboDeSolicitudDeDefensorExporter extends
		RecibirAcuseDeReciboDeSolicitudDeDefensorService {
	
}
