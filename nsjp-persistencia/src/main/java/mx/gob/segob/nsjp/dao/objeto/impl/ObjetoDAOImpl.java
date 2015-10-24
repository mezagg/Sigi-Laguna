/**
 * Nombre del Programa 	: ObjetoDAOImpl.java
 * Autor                : Emigdio Hernández
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 13/06/2011
 * Marca de cambio      : N/A
 * Descripcion General  : Implementación del Objeto de Acceso a Datos para el tipo de Elemento  Objeto de un expediente
 * Programa Dependiente :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion   :N/A
 * Dias de ejecucion    :N/A                             Horario: N/A
 *                             MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                :N/A
 * Compania             :N/A
 * Proyecto             :N/A                                 Fecha: N/A
 * Modificacion         :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.objeto.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.objeto.ObjetoDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Objeto;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementación del Objeto de Acceso a Datos para el tipo de Elemento Objeto
 * de un expediente
 * 
 * @version 1.0
 * @author Emigdio Hernández
 * 
 */
@Repository
public class ObjetoDAOImpl extends GenericDaoHibernateImpl<Objeto, Long>
        implements
            ObjetoDAO {

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.segob.nsjp.dao.objeto.ObjetoDAO#consultarObjetosByExpediente(java
     * .lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Objeto> consultarObjetosByExpediente(Long expedienteId) {

        return getHibernateTemplate().find(
                "from Objeto o where o.expediente.expedienteId = ? and o.esActivo = true",
                expedienteId);

    }
    
    @SuppressWarnings("unchecked")
	public List<Objeto> consultarObjetosPorTipoByExpediente(Long expedienteId, Long idTipoObjeto) {
        final StringBuffer queryString = new StringBuffer();
        queryString
                .append("from Objeto o where o.expediente.expedienteId = ").append(expedienteId);
        queryString.append(" and o.esActivo = true");
        queryString.append(" and o.valorByTipoObjetoVal = ").append(idTipoObjeto);
        logger.debug("queryString :: " + queryString);
        // Query query = super.getSession().createQuery(queryString.toString());
        //return query.list();
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("vehiculo")) {
            	queryString.append(" order by ");
            	queryString.append("o.TipoObjeto_val");
            	queryString.append(" ").append(pag.getDirOrd());
            }
            else if (pag.getCampoOrd().equals("arma")) { 
            	queryString.append(" order by ");
            	queryString.append("o.TipoObjeto_val");
            	queryString.append(" ").append(pag.getDirOrd());
            }
            
            else if (pag.getCampoOrd().equals("equipoComputo")) { 
            	queryString.append(" order by ");
            	queryString.append("o.TipoObjeto_val");
            	queryString.append(" ").append(pag.getDirOrd());
            }
           
        }

        return super.ejecutarQueryPaginado(queryString, pag);


    }


    @SuppressWarnings("unchecked")
    public List<Objeto> consultarInventarioPertenenciasDetenido(Long elementoId) {
        final StringBuffer queryString = new StringBuffer();
        queryString
                .append("SELECT o FROM Objeto o LEFT JOIN o.relacionsForComplementoId r ");
        queryString.append(" WHERE r.elementoBySujetoId = " + elementoId + " ");
        queryString.append(" AND r.catRelacion.catRelacionId = "
                + Relaciones.PROPIETARIA.ordinal());
        queryString.append( " AND o.esActivo = true");

        // final StringBuffer queryString = new StringBuffer();
        // queryString.append("SELECT r.elementoByComplementoId FROM Relacion r ");
        // queryString.append("WHERE r.elementoBySujetoId = "+elementoId+" ");
        // queryString.append("AND r.catRelacion.catRelacionId = "+Relaciones.PROPIETARIA.ordinal());

        Query query = super.getSession().createQuery(queryString.toString());
        return query.list();
    }
    
    @Override
    public Boolean existeCadenaDeCustodiaPorIdObjeto(Long idObjeto) {
    	Long idCadenaCustodia = null;
    		
    	StringBuffer queryString = new StringBuffer();
		queryString
				.append("SELECT o.evidencia.cadenaDeCustodia.cadenaDeCustodiaId ")
				.append(" FROM Objeto o  ")
				.append(" WHERE o.elementoId = ").append(idObjeto);
		
		Query query = super.getSession().createQuery(queryString.toString());
		idCadenaCustodia = (Long) query.uniqueResult();
		
		return (idCadenaCustodia != null && idCadenaCustodia >0 ? true: false);
    }
    
    @Override
    public Boolean existenEslabonesPorIdObjeto(Long idObjeto) {
    	Long idTotalEslabones = null;
    		
    	StringBuffer queryString = new StringBuffer();
		queryString
				.append("SELECT COUNT(e.eslabonId) FROM Eslabon e ")
				.append(" WHERE e.evidencia.objeto.elementoId = ").append(idObjeto);
		
		Query query = super.getSession().createQuery(queryString.toString());
		idTotalEslabones = (Long) query.uniqueResult();
		
		return (idTotalEslabones != null && idTotalEslabones >0 ? true: false);
    }
    
    @SuppressWarnings("unchecked")
    public List<Objeto> consultarBienesPorEnajenar(Date fecha,Integer diasParaEnajenar) {
        SimpleDateFormat formato= new SimpleDateFormat("yyyyMMdd");
        Calendar c=Calendar.getInstance();
        c.setTime(fecha);
        DateUtils.sumarDias(c, diasParaEnajenar*-1);
        
        final StringBuffer queryString = new StringBuffer();
        queryString.append("from Objeto o WHERE o.enajenado = 0 and o.expediente.expedienteId is not null ")
                .append("AND CONVERT (nvarchar, o.fechaCreacionElemento, 112) <= :fechaParaEnajenar");
                
        logger.debug("queryString :: " + queryString);
        Query query = super.getSession().createQuery(queryString.toString());
	query.setParameter("fechaParaEnajenar", formato.format(c.getTime()));
        return query.setMaxResults(100).list();
    }
    

}
