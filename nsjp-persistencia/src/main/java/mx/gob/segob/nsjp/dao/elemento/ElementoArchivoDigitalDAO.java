/**
*
* Nombre del Programa : ElementoArchivoDigitalDAO.java                                    
* Autor                            : Cesar Agustin                                               
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 09/11/2012 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Interface para el DAO de la entidad ElementoArchivoDigital                      
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
package mx.gob.segob.nsjp.dao.elemento;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.ElementoArchivoDigital;
import mx.gob.segob.nsjp.model.ElementoArchivoDigitalId;

public interface ElementoArchivoDigitalDAO extends GenericDao<ElementoArchivoDigital, ElementoArchivoDigitalId> {
	/**
	 * Consulta los archivos digitales ligados a un elemento SIN imagen
	 * @param elementoId
	 * @return
	 */
	public List<ArchivoDigital> consultarArchivosDigitalesXElementoId(Long elementoId);
	
	/**
	 * Consulta los archivos digitales que aun no se envian a otra institucion, 
	 * representa las imagenes asociadas a un objeto que aun no son enviadas
	 * @param elementoId
	 * @return
	 */
	public List<ArchivoDigital> consultarArchivosDigitalesSinEvniarXElementoId(Long elementoId);


}
