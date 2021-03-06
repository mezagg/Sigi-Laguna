/**
* Nombre del Programa : IngresarObjetoPericialService.java                                    
* Autor                            : GustavoBP                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 12/09/2012 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Contrato del servicio para ingresar Objeto Pericial.                      
* Programa Dependiente  :N/A                                                      
* Programa Subsecuente :N/A                                                      
* Cond. de ejecucion        :N/A                                                      
* Dias de ejecucion          :N/A                             Horario: N/A       
*                              MODIFICACIONES                                       
*------------------------------------------------------------------------------           
* Autor                       :N/A                                                           
* Compania               :N/A                                                           
* Proyecto                 :N/A                                   Fecha: N/A       
* Modificacion           :N/A                                                           
*------------------------------------------------------------------------------           
*/
package mx.gob.segob.nsjp.service.objeto;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.objeto.ObjetoPericialDTO;

/**
 * Contrato del servicio para ingresar Objeto Pericial.
 * @author GustavoBP
 *
 */
public interface IngresarObjetoPericialService {

	/**
	 * Servicio que se encarga de guardar un objeto del tipo Pericial
	 * @param objetoPericialDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long ingresarObjetoPericial(ObjetoPericialDTO objetoPericialDTO) throws NSJPNegocioException;;
}
