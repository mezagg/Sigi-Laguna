/**
* Nombre del Programa : VehiculoDAOImpl.java                                    
* Autor                            : Tattva-IT                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 29/04/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Implementacion del contrato para los metodos de acceso a datos de la entidad Vehiculo                      
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

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.objeto.VehiculoDAO;
import mx.gob.segob.nsjp.model.Vehiculo;

import org.springframework.stereotype.Repository;

/**
 * Implementacion del contrato para los metodos de acceso a datos de la entidad Vehiculo
 * @version 1.0
 * @author Tattva-IT
 *
 */
@Repository
public class VehiculoDAOImpl extends GenericDaoHibernateImpl<Vehiculo, Long> implements
		VehiculoDAO {

}
