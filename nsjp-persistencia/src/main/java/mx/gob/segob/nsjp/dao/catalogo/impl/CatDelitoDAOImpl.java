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

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.CatDelito;
import mx.gob.segob.nsjp.model.Expediente;

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
public class CatDelitoDAOImpl
        extends
            GenericDaoHibernateImpl<CatDelito, Long> implements CatDelitoDAO {

    @SuppressWarnings("unchecked")
	@Override
    public List<CatDelito> consultarTodos() {
        final StringBuffer queryStr = new StringBuffer();
//        queryStr.append("select new CatDelito(v.claveDelito, v.nombre, esGrave,");
//        queryStr.append(" d.departamentoId, d.nombreDepto, i.institucionId, i.nombre)");
        queryStr.append(" from CatDelito v ");
//        queryStr.append(" left outer join v.departamento as d ");
//        queryStr.append(" left outer join v.institucion as i ");
//        queryStr.append(" where 1=1");

        queryStr.append(" order by v.nombre");
        
        logger.debug("queryStr :: " + queryStr); 
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("1")) {
                queryStr.append(" order by ");
                queryStr.append("v.claveDelito");
                queryStr.append(" ").append(pag.getDirOrd());
            }
            if (pag.getCampoOrd().equals("2")) {
                queryStr.append(" order by ");
                queryStr.append("v.cNombre");
                queryStr.append(" ").append(pag.getDirOrd());
            }
        }

        return super.ejecutarQueryPaginado(queryStr, pag);
    }

    
    @SuppressWarnings("unchecked")
	@Override
    public List<CatDelito> consultarDelitosSinIdsGrid(String idsGrid, Boolean esAccPenalPrivada) {
    	
		final StringBuffer queryStr = new StringBuffer();
		
		queryStr.append("SELECT c ");
		queryStr.append(" FROM CatDelito c WHERE 1=1");
		
		if(idsGrid != null && idsGrid.length()>0){
			queryStr.append(" AND c.catDelitoId NOT IN  (");
			queryStr.append(idsGrid + ")");
		}
		
		if(esAccPenalPrivada != null && esAccPenalPrivada.equals(true)){
			queryStr.append(" AND c.esAccionPenPriv = 1");
		}

        logger.debug("queryStr :: " + queryStr); 
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        
        if(pag != null && pag.getSearchField() != null && 
        		pag.getSearchOper() != null && pag.getSearchString() != null &&
        		!pag.getSearchField().isEmpty() && !pag.getSearchOper().isEmpty() && !pag.getSearchString().isEmpty()){
        	
        	if (pag.getSearchField().equals("claveDB")){
        		queryStr.append(" AND c.claveDelito");
        	}else if (pag.getSearchField().equals("2")){
        		queryStr.append(" AND c.nombre");
        	}else if (pag.getSearchField().equals("4")){
        		queryStr.append(" AND c.esGrave");
        		if(pag.getSearchString().equalsIgnoreCase("si") ){
        			pag.setSearchString("1");
        		}else if (pag.getSearchString().equalsIgnoreCase("no") ){
        			pag.setSearchString("0");
        		}
        		
        	}
        	
        	if (pag.getSearchOper().equals("eq")){
        		queryStr.append(" = '");
        		queryStr.append(pag.getSearchString());
        		queryStr.append(" ' ");
        	}else if (pag.getSearchOper().equals("cn")){
        		queryStr.append(" LIKE '%");
        		queryStr.append(pag.getSearchString());
        		queryStr.append("%'");
        	}
        }
        
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("1")) {
                queryStr.append("  ORDER BY ");
                queryStr.append("c.claveDelito");
                queryStr.append(" ").append(pag.getDirOrd());
            }
            if (pag.getCampoOrd().equals("2")) {
                queryStr.append("  ORDER BY ");
                queryStr.append("c.nombre");
                queryStr.append(" ").append(pag.getDirOrd());
            }
            if (pag.getCampoOrd().equals("4")) {
                queryStr.append("  ORDER BY ");
                queryStr.append("c.esGrave");
                queryStr.append(" ").append(pag.getDirOrd());
            }
        }
        else {
    		queryStr.append(" ORDER BY c.claveDelito");
        }

        return super.ejecutarQueryPaginado(queryStr, pag);
	}       

    @Override
	public List<CatDelito> consultarDelitoPorGravedad(CatDelito gravedad) {
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append(" from CatDelito v ");
		queryStr.append(" where v.esGrave = " + gravedad.getEsGrave());
		queryStr.append(" order by v.claveDelito");
		
		@SuppressWarnings("unchecked")
        List<CatDelito> resp = super.getHibernateTemplate().find(
                queryStr.toString());
        if (logger.isDebugEnabled()) {

            logger.debug("resp.size() :: " + resp.size());
        }
        return resp;		
	}
    @SuppressWarnings("unchecked")
    @Override
    public List<CatDelito> consultarCatDelitoPorFilro(CatDelito catDelitoFiltro){
    	final StringBuffer queryStr = new StringBuffer();
		queryStr.append(" FROM CatDelito v WHERE 1=1 ");
		
		if( catDelitoFiltro!=null && catDelitoFiltro.getCatDelitoId()!=null)
			queryStr.append(" AND v.catDelitoId = ").append(catDelitoFiltro.getCatDelitoId());
		
		if( catDelitoFiltro!=null && catDelitoFiltro.getClaveDelito()!=null && !catDelitoFiltro.getClaveDelito().trim().isEmpty())
			queryStr.append(" AND v.claveDelito = '").append(catDelitoFiltro.getClaveDelito()).append("' ");
		
		if( catDelitoFiltro!=null && catDelitoFiltro.getNombre()!=null && !catDelitoFiltro.getNombre().trim().isEmpty())
			queryStr.append(" AND v.nombre = '").append(catDelitoFiltro.getNombre()).append("' ");
		
		if( catDelitoFiltro!=null && catDelitoFiltro.getEsGrave()!=null)
			queryStr.append(" AND v.esGrave = " ).append(catDelitoFiltro.getEsGrave());
		
		if( catDelitoFiltro!=null && catDelitoFiltro.getEsAccionPenPriv()!=null)
			queryStr.append(" AND v.esAccionPenPriv = " ).append(catDelitoFiltro.getEsAccionPenPriv());
		
		if( catDelitoFiltro!=null && catDelitoFiltro.getClaveInterInstitucional()!=null){
			queryStr.append(" AND LOWER(v.claveInterInstitucional) = '" ).append(catDelitoFiltro.getClaveInterInstitucional().toLowerCase());
			queryStr.append("'");
		}
			
		queryStr.append(" order by v.claveDelito");
        
        return super.getHibernateTemplate().find(
                queryStr.toString());	
    }


	@Override
	public CatDelito consultarCatDelitoPorFiltro(CatDelito catDelitoFiltro) {
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("FROM CatDelito catDel WHERE 1=1");
		
		if( catDelitoFiltro!=null && catDelitoFiltro.getCatDelitoId()!=null)
			queryStr.append(" AND catDel.catDelitoId = ").append(catDelitoFiltro.getCatDelitoId());
		
		if( catDelitoFiltro!=null && catDelitoFiltro.getClaveDelito()!=null && !catDelitoFiltro.getClaveDelito().trim().isEmpty())
			queryStr.append(" AND catDel.claveDelito = '").append(catDelitoFiltro.getClaveDelito()).append("' ");
		
		if( catDelitoFiltro!=null && catDelitoFiltro.getClaveInterInstitucional()!=null && !catDelitoFiltro.getClaveInterInstitucional().trim().isEmpty())
			queryStr.append(" AND catDel.claveInterInstitucional = '").append(catDelitoFiltro.getClaveInterInstitucional()).append("' ");
		
		Query query = super.getSession().createQuery(queryStr.toString());
		
		return  (CatDelito) query.uniqueResult();
}


	@Override
	public Long consultarNumeroDelitosPorCatDelitoId(CatDelito catDelitoFiltro) {
		final StringBuffer queryStr = new StringBuffer();
		
		if (catDelitoFiltro != null && catDelitoFiltro.getCatDelitoId() != null) {
			queryStr.append(
					"SELECT COUNT(delitoId) FROM Delito WHERE catDelito.catDelitoId=")
					.append(catDelitoFiltro.getCatDelitoId());
		}
		
		Query query = super.getSession().createQuery(queryStr.toString());
		return (Long)query.uniqueResult();
	}


	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.catalogo.CatDelitoDAO#consultarCatDelitoPorFiltroExpediente(mx.gob.segob.nsjp.model.CatDelito, mx.gob.segob.nsjp.model.Expediente)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CatDelito> consultarCatDelitoPorFiltroExpediente(
			CatDelito catDelitoFiltro, Expediente expediente) {
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append(" FROM CatDelito v WHERE 1=1 ");
			
		if( catDelitoFiltro!=null && catDelitoFiltro.getCatDelitoId()!=null)
			queryStr.append(" AND v.catDelitoId = ").append(catDelitoFiltro.getCatDelitoId());
		
		if( catDelitoFiltro!=null && catDelitoFiltro.getClaveDelito()!=null && !catDelitoFiltro.getClaveDelito().trim().isEmpty())
			queryStr.append(" AND v.claveDelito = '").append(catDelitoFiltro.getClaveDelito()).append("' ");
		
		if( catDelitoFiltro!=null && catDelitoFiltro.getNombre()!=null && !catDelitoFiltro.getNombre().trim().isEmpty())
			queryStr.append(" AND v.nombre = '").append(catDelitoFiltro.getNombre()).append("' ");
		
		if( catDelitoFiltro!=null && catDelitoFiltro.getEsGrave()!=null)
			queryStr.append(" AND v.esGrave = " ).append(catDelitoFiltro.getEsGrave());
		
		if( catDelitoFiltro!=null && catDelitoFiltro.getEsAccionPenPriv()!=null)
			queryStr.append(" AND v.esAccionPenPriv = " ).append(catDelitoFiltro.getEsAccionPenPriv());
		
		if( catDelitoFiltro!=null && catDelitoFiltro.getClaveInterInstitucional()!=null){
			queryStr.append(" AND LOWER(v.claveInterInstitucional) = '" ).append(catDelitoFiltro.getClaveInterInstitucional().toLowerCase());
			queryStr.append("'");
		}
		
		if (expediente != null
				&& expediente.getExpedienteId() != null
				&& expediente.getExpedienteId() > 0L){
			queryStr.append(" AND v.catDelitoId NOT IN ( ");
			queryStr.append(" 	SELECT DISTINCT del.catDelito.catDelitoId ");
			queryStr.append(" 	FROM Expediente exp ");
			queryStr.append(" 	LEFT JOIN exp.delitos del ");
			queryStr.append(" 	WHERE exp.expedienteId = ");
			queryStr.append(expediente.getExpedienteId());
			queryStr.append("	AND del IS NOT NULL	)");
		}
				
		queryStr.append(" order by v.claveDelito");
		
		return (List<CatDelito>)ejecutarQueryPaginado(queryStr, pag);	
	}
	
	
}
