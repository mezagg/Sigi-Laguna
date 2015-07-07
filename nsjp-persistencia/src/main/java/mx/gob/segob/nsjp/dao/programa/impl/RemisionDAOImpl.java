/**
* Nombre del Programa : RemisionDAOImpl.java
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
import mx.gob.segob.nsjp.dao.programa.RemisionDAO;
import mx.gob.segob.nsjp.model.Remision;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * 
 * Acceso a la información de Remision
 * 
 * @version 1.0
 * @author AntonioBV
 *
 */
@Repository
public class RemisionDAOImpl extends GenericDaoHibernateImpl<Remision, Long> implements RemisionDAO {

	/**
	 * M&eacute;todo que consulta una remisión por id
	 * @return Remision
	 * @throws NSJPNegocioException
	 */
	@SuppressWarnings("unchecked")
	public Remision consultarRemisionPorId(Remision remision)throws NSJPNegocioException{
    	StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append("FROM Remision rev ");
        hqlQuery.append(" WHERE Remision_id = ").append(remision.getRemisionId()).append(" ");
        Query query = super.getSession().createQuery(hqlQuery.toString());

        logger.debug("query :: " + query);
        List<Remision> resp = query.list();
        logger.debug("resp.size() :: " + resp.size());
        if(!resp.isEmpty()){
        	return resp.get(0);
        }
        
        return null;
    }

	@Override
	public Remision consultaRemisionPorFiltros(Remision remision)
			throws NSJPNegocioException {
		if (remision == null
				|| remision.getSentencia() == null
				|| remision.getSentencia().getSentenciaId() == null
				|| remision.getSentencia().getSentenciaId() < 1L
				|| remision.getCatTipoRemision() == null
				|| remision.getCatTipoRemision().getCatTipoRemisionId() == null
				|| remision.getCatTipoRemision().getCatTipoRemisionId() < 1L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		StringBuilder sb = new StringBuilder("");
		sb.append(" SELECT r ");
		sb.append(" FROM Remision r ");
		sb.append(" WHERE r.sentencia.sentenciaId = :sentenciaId ");
		sb.append(" AND r.catTipoRemision.catTipoRemisionId = :tipoRemisionId ");
		
		Query query = getSession().createQuery(sb.toString());
		query.setParameter("sentenciaId", remision.getSentencia().getSentenciaId());
		query.setParameter("tipoRemisionId", remision.getCatTipoRemision().getCatTipoRemisionId());
		
		return (Remision) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Remision> consultarComplementoRemisiones(
			List<Long> listaCatTipoRemisionId, Long sentenciaId) {

		if (sentenciaId == null || sentenciaId <= 0L) {
			return null;
		}

		StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append(" SELECT rem ");
		hqlQuery.append(" FROM Remision rem ");
		hqlQuery.append(" WHERE rem.sentencia.sentenciaId = " + sentenciaId);

		if (listaCatTipoRemisionId != null && !listaCatTipoRemisionId.isEmpty()) {
			hqlQuery.append(" AND rem.catTipoRemision.catTipoRemisionId NOT IN (:listaCatTipoRemisionId) ");
		}

		Query query = getSession().createQuery(hqlQuery.toString());

		if (listaCatTipoRemisionId != null && !listaCatTipoRemisionId.isEmpty()) {
			query.setParameterList("listaCatTipoRemisionId", listaCatTipoRemisionId);
		}

		return (List<Remision>) query.list();
	}
}
