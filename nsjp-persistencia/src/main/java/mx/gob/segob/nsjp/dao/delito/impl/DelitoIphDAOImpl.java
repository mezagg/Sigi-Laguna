/**
 *
 * Nombre del Programa : DelitoIPHDAO.java                                    
 * Autor                            : JIFO                                             
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 24/05/2011 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Implementación para la entidad DelitoIPH.                      
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
package mx.gob.segob.nsjp.dao.delito.impl;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.delito.DelitoIphDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoIph;
import mx.gob.segob.nsjp.model.DelitoIphId;
import mx.gob.segob.nsjp.model.Expediente;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementación para la entidad Delito
 * 
 * @author JIFO
 * @since 1.0
 * 
 */
@Repository("delitoIphDAO")
public class DelitoIphDAOImpl extends GenericDaoHibernateImpl<DelitoIph,DelitoIphId>
		implements DelitoIphDAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eliminaDelitosDelIph(Long foliIph){
		
		StringBuffer queryString = new StringBuffer();
		queryString.append("DELETE FROM DelitoIph d");
		queryString.append(" WHERE d.id.informePolicialHomologadoId in( " +
				" SELECT informePolicialHomologadoId" +
				" FROM InformePolicialHomologado WHERE folioIPH="+foliIph+")");
		Query query=super.getSession().createQuery(queryString.toString());
		query.executeUpdate();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DelitoIph> buscarDelitosIPH(Long idIph){
		StringBuffer queryString = new StringBuffer();
		queryString.append(" FROM DelitoIph ")
				.append(" WHERE id.informePolicialHomologadoId=:idIph ");
		Query query = super.getSession().createQuery(queryString.toString());
		query.setParameter("idIph", idIph);
		return query.list();
	}


}
