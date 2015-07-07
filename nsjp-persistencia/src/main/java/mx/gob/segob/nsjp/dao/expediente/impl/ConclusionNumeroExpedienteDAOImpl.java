/**
 * 
 */
package mx.gob.segob.nsjp.dao.expediente.impl;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.expediente.ConclusionNumeroExpedienteDAO;
import mx.gob.segob.nsjp.model.ConclusionNumeroExpediente;
import mx.gob.segob.nsjp.model.NumeroExpediente;

import org.springframework.stereotype.Repository;

/**
 * @author jfernandezo
 *
 */
@Repository
public class ConclusionNumeroExpedienteDAOImpl extends GenericDaoHibernateImpl<ConclusionNumeroExpediente, NumeroExpediente> implements
ConclusionNumeroExpedienteDAO {

	
	
	 @Override
	    public ConclusionNumeroExpediente obtenerConclusionNumeroExpediente(Long numeroExpedienteId) {
	       try{
		 	final StringBuffer queryStr = new StringBuffer();
	        queryStr.append("select new ConclusionNumeroExpediente(v.numeroExpediente, v.fechaConclusion, v.tipoConclusion, v.tipoSubConclusion)");
	        queryStr.append(" from ConclusionNumeroExpediente v ");
	        queryStr.append(" where v.numeroExpediente.numeroExpedienteId = ");
	        queryStr.append(numeroExpedienteId);
	        logger.info("Query:"+ queryStr);
	        return (ConclusionNumeroExpediente) super.getSession().
	                createQuery(queryStr.toString()).uniqueResult();
	        
	       }catch (Exception e) {
	    	   return null;
		}
	    }
}
