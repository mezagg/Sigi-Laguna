/**
 * Nombre del Programa  : RegistrarAvisoDeDetencionServiceImpljava
 * Autor                : Daniel Jim�nez
 * Compania             : TATTVA-IT
 * Proyecto             : NSJP                    Fecha: 15 Jul 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Envia un aviso de detenci�n a Coordinaci�n de Defensoria
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
package mx.gob.segob.nsjp.service.solicitud;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
/**
 * Registra una viso de deteneci�n de un imputado a Coordinaci�n de Defensor�a
 * 
 * @version 1.0
 * @author Daniel Jim�nez
 * 
 */
public interface RegistrarAvisoDeDetencionService {

	/**
	 * Envia un aviso de detenci�n a la Coordinaci�n de Defensor�a para el imputado identificado por
	 * <code>imputadoID</code> y el caso <CODE>noCaso</CODE>.
	 * @param imputadoID Identificador del imputado par ael cual se genera el aviso de detenci�n
	 * @param noCaso Numero de Caso al cual se asocia el aviso de detenci�n.
	 * @throws NSJPNegocioException
	 */
	public void registrarAvisoDeDetencion(Long imputadoID, CasoDTO noCaso,Long idAgencia, String claveAgencia)throws NSJPNegocioException;
}
