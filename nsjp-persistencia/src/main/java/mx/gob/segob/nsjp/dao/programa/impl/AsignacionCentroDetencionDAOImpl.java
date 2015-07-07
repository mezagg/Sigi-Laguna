/**
* Nombre del Programa : AsignacionCentroDetencionDAOImpl.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 26/01/2012
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
package mx.gob.segob.nsjp.dao.programa.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.programa.AsignacionCentroDetencionDAO;
import mx.gob.segob.nsjp.model.AsignacionCentroDetencion;
import mx.gob.segob.nsjp.model.Sentencia;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * 
 * Acceso a la informaci&oacute;n de AsignacionCentroDetencion
 * 
 * @version 1.0
 * @author AntonioBV
 *
 */
@Repository
public class AsignacionCentroDetencionDAOImpl extends GenericDaoHibernateImpl<AsignacionCentroDetencion, Long> implements AsignacionCentroDetencionDAO {

	private static final String PARAM_SENTENCIA_ID = "sentenciaId";
	
	/**
	 * M&eacute;todo que consulta una asignacionCentroDetencion por id
	 * @return AsignacionCentroDetencion
	 * @throws NSJPNegocioException
	 */
	@SuppressWarnings("unchecked")
	public AsignacionCentroDetencion consultarAsignacionCentroDetencionPorId(AsignacionCentroDetencion asignacionCentroDetencion)throws NSJPNegocioException{
    	StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append("FROM AsignacionCentroDetencion rev ");
        hqlQuery.append(" WHERE AsignacionCentroDetencion_id = ").append(asignacionCentroDetencion.getAsignacionCentroDetencionId()).append(" ");
        Query query = super.getSession().createQuery(hqlQuery.toString());

        logger.debug("query :: " + query);
        List<AsignacionCentroDetencion> resp = query.list();
        logger.debug("resp.size() :: " + resp.size());
        if(!resp.isEmpty()){
        	return resp.get(0);
        }
        
        return null;
    }

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.programa.AsignacionCentroDetencionDAO#consultarAsignacionCentroDetencionActual(mx.gob.segob.nsjp.model.Sentencia)
	 */
	@Override
	public AsignacionCentroDetencion consultarAsignacionCentroDetencionActual(
			Sentencia sentencia) throws NSJPNegocioException {
		
		if (sentencia == null 
				|| sentencia.getSentenciaId() == null
				|| sentencia.getSentenciaId() < 1L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		StringBuilder hqlQuery = new StringBuilder();
        hqlQuery.append(" SELECT acd ");
        hqlQuery.append(" FROM AsignacionCentroDetencion acd ");
        hqlQuery.append(" WHERE acd.sentencia.sentenciaId = :"+PARAM_SENTENCIA_ID+" ");
        hqlQuery.append(" AND acd.dfechaSalida IS NULL");
        
        Query query = super.getSession().createQuery(hqlQuery.toString());
        
        query.setParameter(PARAM_SENTENCIA_ID, sentencia.getSentenciaId());
        
        AsignacionCentroDetencion actual = null;
        
        try{
        	actual = (AsignacionCentroDetencion) query.uniqueResult();
        }catch(NonUniqueResultException e){
        	logger.error(e.getMessage(),e);
        }
        
        return actual;
	}
}
