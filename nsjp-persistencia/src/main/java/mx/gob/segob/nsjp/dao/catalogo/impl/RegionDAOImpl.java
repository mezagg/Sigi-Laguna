/**
* Nombre del Programa : RegionDAOImpl.java
* Autor                            : asanchez
* Compania                    : Lucasian Labs
* Proyecto                      : NSJP                    Fecha: 11 Aug 2015
* Marca de cambio        : N/A
* Descripcion General    : Objeto de accedo a datos para la tabla de region.
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
import mx.gob.segob.nsjp.dao.catalogo.RegionDAO;
import mx.gob.segob.nsjp.model.Region;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementacion de persistencia de la tabla entidad federativa.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Repository
public class RegionDAOImpl
        extends
            GenericDaoHibernateImpl<Region, Long>
        implements
            RegionDAO {
	@Override
	@SuppressWarnings("unchecked")
	public List<Region> consultarTodos() {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM Region");
		
		Query query = super.getSession().createQuery(queryString.toString()); 
		return query.list();
	}

}
