/**
* Nombre del Programa 		: BitacoraElementoService.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 07/11/2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.elemento;

import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.elemento.BitacoraElementoDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public interface BitacoraElementoService {
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de la bit&aacute;cora de los 
	 * Elementos que se pasan como par&aacute;metros.
	 * @param elementos - List<BitacoraElemento> con los identificadores de los
	 * 					  elementos de los cu&aacute;les se va a consultar la
	 * 					  bit&aacute;cora asociada.
	 * @return Map<Long,BitacoraElemento> - Mapa con la informaci&oacute;n de la
	 * 									bit&aacute;cora asociada a cada uno de 
	 * 									los elementos.
	 * @throws NSJPNegocioException - En el caso de que no se env&iacute;en los 
	 * 								  par&aacute;metros suficientes para llevar 
	 * 								  a cabo la consulta.
	 */
	public Map<Long,BitacoraElementoDTO> consultarBitacoraElementos(List <BitacoraElementoDTO> elementos) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de la bit&aacute;cora asociada a 
	 * un elemento.
	 * @param elemento - El elemento del cual se va a consultar la bit&aacute;cora 
	 * 					 asociada.
	 * @return BitacoraElemento - La bit&aacute;cora asociada con el elemento pasado
	 * 							  como par&aacute;metro.
	 * @throws NSJPNegocioException - En el caso de que no se env&iacute;en los 
	 * 								  par&aacute;metros suficientes para llevar 
	 * 								  a cabo la consulta.
	 */
	public BitacoraElementoDTO consultarBitacoraXElemento(BitacoraElementoDTO elemento) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo el guardado o actualizaci&oacute;n de la 
	 * bit&aacute;cora asociada con un elemento.
	 * @param bitacoraElemento - La bit&aacute;cora de la cual se va a actualizar la
	 * 							 informaci&oacute;n
	 * @return BitacoraElementoDTO - la bit&aacute;cora con la informaci&oacute;n 
	 * 								 actualizada. 
	 */
	public BitacoraElementoDTO actualizaBitacoraElemento (BitacoraElementoDTO bitacoraElemento) throws NSJPNegocioException;

}
