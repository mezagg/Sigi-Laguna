/**
*
* Nombre del Programa : DelitoIphDAO.java                                    
* Autor                  : JIFO                                              
* Compania               : Ultrasist                                                
* Proyecto               : NSJP                    Fecha: 08/12/2014 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Interface para la entidad DelitoIPH.                      
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
package mx.gob.segob.nsjp.dao.delito;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.DelitoIph;
import mx.gob.segob.nsjp.model.DelitoIphId;

/**
 * Interface para la entidad Delito.
 * @author JIFO
 * @since 1.0
 *
 */
public interface DelitoIphDAO extends GenericDao<DelitoIph, DelitoIphId> {
	
	public void eliminaDelitosDelIph(Long foliIph);
	public List<DelitoIph> buscarDelitosIPH(Long idIph);

}
