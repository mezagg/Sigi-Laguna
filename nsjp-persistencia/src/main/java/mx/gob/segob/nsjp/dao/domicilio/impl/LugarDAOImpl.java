/**
 *
 * Nombre del Programa : NarrativaDAO.java                                    
 * Autor                            : Cesar Agustin                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 30/03/2011 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Implementaci�n para la entidad Lugar.                      
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
package mx.gob.segob.nsjp.dao.domicilio.impl;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.domicilio.LugarDAO;
import mx.gob.segob.nsjp.model.Lugar;

import org.springframework.stereotype.Repository;

/**
 * Implementaci�n para la entidad Lugar
 * 
 * @author vaguirre
 * @since 1.0
 * 
 */
@Repository
public class LugarDAOImpl extends GenericDaoHibernateImpl<Lugar, Long>
        implements
            LugarDAO {

}
