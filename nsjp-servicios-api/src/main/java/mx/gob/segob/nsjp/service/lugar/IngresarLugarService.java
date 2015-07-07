/**
* Nombre del Programa : IngresarLugarService.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 14 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato del servicio para realizar el ingreso de un Lugar
* Programa Dependiente  :N/A
* Programa Subsecuente :N/A
* Cond. de ejecucion        :N/A
* Dias de ejecucion          :N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                       :N/A
* Compania               :N/A
* Proyecto                 :N/A                                 Fecha: N/A
* Modificacion           :N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.lugar;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.domicilio.LugarDTO;

/**
 * Contrato del servicio para realizar el ingreso de un Lugar. 
 * @version 1.0
 * @author cesarAgustin
 *
 */
public interface IngresarLugarService {

	/**
	 * Metodo para realizar el ingreso de un objeto Lugar
	 * @param lugarDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public LugarDTO ingresarLugar(LugarDTO lugarDTO) throws NSJPNegocioException;
}
