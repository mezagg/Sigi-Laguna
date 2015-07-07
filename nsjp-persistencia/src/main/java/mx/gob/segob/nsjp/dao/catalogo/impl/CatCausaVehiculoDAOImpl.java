/**
 * Nombre del Programa : CatDelitoDAOImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 2 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.dao.catalogo.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.catalogo.CatCausaVehiculoDAO;
import mx.gob.segob.nsjp.model.Causa;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Repository
public class CatCausaVehiculoDAOImpl
        extends
            GenericDaoHibernateImpl<Causa, Long> implements CatCausaVehiculoDAO {
	
	  private final static Logger logger = Logger
	            .getLogger(CatCausaVehiculoDAOImpl.class);

    @SuppressWarnings("unchecked")
	@Override
    public List<Causa> consultarCausasVehiculo(Long idPadre) {
    	
        final StringBuffer queryStr = new StringBuffer();
        List<Causa> causas = null;
        
        try{
        	
        	queryStr.append(" from Causa c ");
        	if(idPadre != null && idPadre != -1)
        		queryStr.append(" where c.causaPadre.causaId =" + idPadre);
        	else
        		queryStr.append(" where c.causaPadre IS NULL");
        		
        	queryStr.append(" order by c.causaId");
        	
        	Query query = super.getSession().createQuery(queryStr.toString());
			
			causas = query.list();
			
        }catch(Exception e){
        	logger.error(":: Error al tratar de consultar el catalogo de causas: CatCausaVehiculoDAOImpl - consultarCausaVehiculo, ", e);
        }
        
        
        return causas;
    }

    
	
	
}
