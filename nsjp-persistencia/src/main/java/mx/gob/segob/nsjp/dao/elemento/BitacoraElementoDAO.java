/**
* Nombre del Programa 		: BitacoraElementoDAO.java
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
package mx.gob.segob.nsjp.dao.elemento;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.BitacoraElemento;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public interface BitacoraElementoDAO extends GenericDao<BitacoraElemento, Long> {
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de la bit&aacute;cora de los 
	 * Elementos que se pasan como par&aacute;metros.
	 * @param elementos - List<BitacoraElemento> con los identificadores de los
	 * 					  elementos de los cu&aacute;les se va a consultar la
	 * 					  bit&aacute;cora asociada.
	 * @return List<BitacoraElemento> - Lista con la informaci&oacute;n de la
	 * 									bit&aacute;cora asociada a cada uno de 
	 * 									los elementos.
	 * @throws NSJPNegocioException - En el caso de que no se env&iacute;en los 
	 * 								  par&aacute;metros suficientes para llevar 
	 * 								  a cabo la consulta.
	 */
	public List<BitacoraElemento> consultarBitacoraElementos(List <BitacoraElemento> elementos) throws NSJPNegocioException;
	
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
	public BitacoraElemento consultarBitacoraXElemento(BitacoraElemento elemento) throws NSJPNegocioException;
	
}
