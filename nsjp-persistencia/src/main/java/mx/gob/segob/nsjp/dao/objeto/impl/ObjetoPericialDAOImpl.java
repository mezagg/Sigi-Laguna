/**
* Nombre del Programa : ObjetoPericialDAOImpl.java                                    
* Autor                            : GustavoBP                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 12/09/2012 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Implementacion del contrato para los metodos de acceso a datos de la entidad Objeto Pericial                      
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
package mx.gob.segob.nsjp.dao.objeto.impl;

import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.objeto.ObjetoPericialDAO;
import mx.gob.segob.nsjp.model.ObjetoPericial;


/**
 * Implementacion para los metodos de acceso a datos de la entidad Objeto Pericial
 * 
 * @author GustavoBP
 *
 */
@Repository
public class ObjetoPericialDAOImpl extends
		GenericDaoHibernateImpl<ObjetoPericial, Long> implements
		ObjetoPericialDAO {

}
