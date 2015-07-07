/**
 * 
 */
package mx.gob.segob.nsjp.service.evidencia;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;

/**
 * @author rgama
 *
 */
public interface GeneraEvidenciasConRetrasoService {
	
	/**
	 * Operaci�n que permite identificar aquellas evidencias cuyo ultimo eslabon de salida
	 * ya vencio la fecha final de prestamo
	 * @throws NSJPNegocioException
	 */
	void generaEvidenciasConRetraso()throws NSJPNegocioException;

}
