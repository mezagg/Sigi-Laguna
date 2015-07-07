/**
*
* Nombre del Programa : DomicilioDAO.java                                    
* Autor                            : Cesar Agustin                                               
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 30/03/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Interface para DAO de la entidad Domicilio                    
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
package mx.gob.segob.nsjp.dao.domicilio;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Domicilio;

/**
 * @author CesarAgustin
 * @version 1.0
 */
public interface DomicilioDAO extends GenericDao<Domicilio, Long> {

	/**
	 * 
	 * @param elementoId
	 * @param ordinal
	 * @return
	 */
	Domicilio obtenerDomicilioByRelacion(Long elementoId, Long catRelacionId);
	
}
