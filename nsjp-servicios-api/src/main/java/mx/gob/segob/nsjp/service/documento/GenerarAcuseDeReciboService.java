/**
 * Nombre del Programa  : GenerarAcuseDeReciboService.java
 * Autor                : Daniel Jim�nez
 * Compania             : TATTVA-IT
 * Proyecto             : NSJP                    Fecha: 24 Jun 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Genera un acuse de recibo y lo asocia a la solicitud correspondiente
 * Programa Dependiente : N/A
 * Programa Subsecuente : N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                :N/A
 * Compania             :N/A
 * Proyecto             :N/A                                 Fecha: N/A
 * Modificacion         :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.documento;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;

/**
 * Genera un acuse de recibo y lo asocia a la solicitud correspondiente
 * 
 * @version 1.0
 * @author Daniel Jim�nez
 * 
 */
public interface GenerarAcuseDeReciboService {

	/**
	 * Servicio que guarda en base de datos el acuse de recibo de una solicitud. Si la solicitud
	 * se origino localmente, lo almacenar� en la base de datos local, de lo contrar�o, envia 
	 * una petici�n al sistem origen para almacenar el acuse de recibo y actualizar el estado
	 * de la solicitud 
	 * @param idSolicitud identificador de la solicitud que espera el Acuse de Recibo
	 * @throws NSJPNegocioException
	 */
	public void generarAcuseDeRecibo(Long idSolicitud) throws NSJPNegocioException;

	/**
	 * Servicio que guarda en base de datos el acuse de recibo de una solicitud. Si la solicitud
	 * se origino localmente, lo almacenar� en la base de datos local, de lo contrar�o, envia 
	 * una petici�n al sistem origen para almacenar el acuse de recibo y actualizar el estado
	 * de la solicitud 
	 * @param folio Folio de la solicitud que espera el Acuse de Recibo
	 * @throws NSJPNegocioException
	 */
	void generarAcuseDeRecibo(String folio) throws NSJPNegocioException;

	
}
