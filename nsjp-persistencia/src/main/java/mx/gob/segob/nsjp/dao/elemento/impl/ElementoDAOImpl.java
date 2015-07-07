/**
 *
 * Nombre del Programa : DomicilioDAOImpl.java                                    
 * Autor                            : Cesar Agustin                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 30/03/2011 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Implementación para el DAO de la entidad Elemento                      
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
package mx.gob.segob.nsjp.dao.elemento.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.elemento.ElementoDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Elemento;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author CesarAgustin
 * @version 1.0
 * 
 */
@Repository("elementoDAO")
public class ElementoDAOImpl extends GenericDaoHibernateImpl<Elemento, Long>
        implements
            ElementoDAO {
    public Session getHBSession() {
        return super.getSession();
    }

    /**
     * Operación que realiza la funcionalidad de consultar los elementos y las
     * relaciones implícitas de los elementos relacionados al expediente. Recibe
     * el expediente del cual va a buscar la información
     * 
     * @return Devuelve un listado de objetos de tipo Elemento
     */
    @SuppressWarnings("unchecked")
    public List<Elemento> consultarElementosPorCalidad(String numeroExpediente,
            Calidades calidadId) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT el ").append(" FROM Expediente e")
                .append(" JOIN e.elementos el")
                .append(" WHERE e.numeroExpediente like :numeroExpediente ")
                .append(" and el.calidad.calidadId =:calidadId ");
        queryString.append( " AND el.esActivo = true ");
        
        Query query = super.getSession().createQuery(queryString.toString());
        query.setParameter("numeroExpediente", numeroExpediente);
        query.setParameter("calidadId", calidadId.getValorId());
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Elemento> findElementosByExpedienteId(Long expedienteId) {
    	StringBuffer queryStr = new StringBuffer();
       // return getHibernateTemplate().find(
         //       "from Elemento o where o.expediente.expedienteId = ? AND o.esActivo = true",
           //     expedienteId);
        
    	queryStr.append(" FROM Elemento o")
         .append(" WHERE o.expediente.expedienteId = ")
         .append(expedienteId)
         .append(" AND o.esActivo = true");
        
        Query query = super.getSession().createQuery(queryStr.toString());
        return query.list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Elemento> consultarElementoXActividad(Long idActividad) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT elems FROM Actividad a ").
                append("INNER JOIN a.expediente e ").
                append("INNER JOIN e.elementos elems ").
                append("WHERE a = ").append(idActividad);
        sb.append(" AND elems.esActivo = true ");
        
        Query query = super.getSession().createQuery(sb.toString());
        return query.list();
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Elemento> consultarElementoXIdExpedienteTipoValor(Long idExpediente, String valorDescripcion, Boolean esOrganizacion) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT E FROM Elemento E, Valor V ").
                append(" WHERE  E.tipoElemento = V.valorId").
                append(" AND E.expediente.expedienteId = ").append(idExpediente);
                if (esOrganizacion){
                	sb.append(" AND (UPPER(V.valor) LIKE '").append(valorDescripcion).append("'").
                		append(" OR UPPER(V.valor) LIKE 'Organización')");
                }else{
                	sb.append(" AND UPPER(V.valor) LIKE '").append(valorDescripcion).append("'");
                }
                
        sb.append(" AND E.esActivo = true ");
        sb.append(" ORDER BY E.calidad.tipoCalidad.valor");
        logger.info("Query:"+sb);
        Query query = super.getSession().createQuery(sb.toString());
        return query.list();
    }
    
	public Elemento consultarElementoPorFolio(String folioElemento) {
		final StringBuffer hqlQuery = new StringBuffer();
		Elemento loElemento = null;
		hqlQuery.append("SELECT e ")
				.append("FROM Elemento e ")
				.append("WHERE e.folioElemento = '"+folioElemento+"'");

		Query query = super.getSession().createQuery(hqlQuery.toString());
		@SuppressWarnings("unchecked")
		List<Elemento> loLista = (List<Elemento>)query.list();
		if(loLista != null && !loLista.isEmpty() && loLista.size() >0){
			loElemento= loLista.get(0);
		}
		return loElemento;
	}
}
