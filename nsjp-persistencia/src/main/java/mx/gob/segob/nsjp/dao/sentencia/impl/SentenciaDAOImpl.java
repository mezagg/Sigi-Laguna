/**
* Nombre del Programa : SentenciaDAOImpl.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 9 Sep 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacion de metodos de acceso a datos de la entidad Sentencia
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
package mx.gob.segob.nsjp.dao.sentencia.impl;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.sentencia.SentenciaDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.model.Sentencia;
import mx.gob.segob.nsjp.model.Solicitud;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementacion de metodos de acceso a datos de la entidad Sentencia.
 * @author cesarAgustin
 *
 */
@Repository
public class SentenciaDAOImpl extends
		GenericDaoHibernateImpl<Sentencia, Long> implements SentenciaDAO {

	/**
	 * M&eacute;todo que consulta todos las sentencias
	 * @param filtro filtro para discriminar las sentencias
	 * @return Lista de Sentencias
	 * @throws NSJPNegocioException
	 */
    @SuppressWarnings("unchecked")
	public List<Sentencia> consultarSentencias(Sentencia filtro) throws NSJPNegocioException {
        
    	StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append(" FROM Sentencia s ");
        hqlQuery.append(" WHERE 1 = 1 ");
        if (filtro != null){
        	if(filtro.getNumeroExpediente()!= null 
        	&& filtro.getNumeroExpediente().getNumeroExpediente() != null){
        		hqlQuery.append(" AND s.numeroExpediente.numeroExpediente = ");
        		hqlQuery.append(" '").append(filtro.getNumeroExpediente().getNumeroExpediente()).append("' ");
        	}
        }
        
        
        
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        if (pag != null && pag.getCampoOrd() != null) {
            hqlQuery.append(" order by ");
            hqlQuery.append(pag.getCampoOrd());
            hqlQuery.append(" ").append(pag.getDirOrd());
        }
        return super.ejecutarQueryPaginado(hqlQuery, pag);
    }
	
	/**
	 * M&eacute;todo que consulta una Sentencia por id
	 * @return Sentencia
	 * @throws NSJPNegocioException
	 */
	@SuppressWarnings("unchecked")
	public Sentencia consultarSentencia(Sentencia sentencia)throws NSJPNegocioException{
		if (sentencia == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
    	StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append("FROM Sentencia s ");
        hqlQuery.append(" WHERE 1 = 1");
        	
        if(sentencia.getSentenciaId() != null) {
        	hqlQuery.append(" AND s.sentenciaId = ").append(sentencia.getSentenciaId()).append(" ");
        }

        if(sentencia.getNumeroExpediente()!= null 
        		&& sentencia.getNumeroExpediente().getNumeroExpediente() != null) {
        	hqlQuery.append(" AND s.numeroExpediente.numeroExpediente = ");
        	hqlQuery.append(" '").append(sentencia.getNumeroExpediente().getNumeroExpediente()).append("' ");
        }
        
        if (sentencia.getCnumeroCausa() != null){
        	hqlQuery.append(" AND s.cnumeroCausa = ");
        	hqlQuery.append(" '").append(sentencia.getCnumeroCausa()).append("' ");
        }
        
        Query query = super.getSession().createQuery(hqlQuery.toString());

        logger.debug("query :: " + query);
        List<Sentencia> resp = query.list();
        logger.debug("resp.size() :: " + resp.size());
        if(!resp.isEmpty()){
        	return resp.get(0);
        }
        
        return null;
    }

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.sentencia.SentenciaDAO#consultarSentenciasXEstado(mx.gob.segob.nsjp.model.Sentencia)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Sentencia> consultarSentenciasXEstado(Sentencia sentencia)
			throws NSJPNegocioException {
		if (sentencia == null || sentencia.getNumeroExpediente() == null 
				|| sentencia.getNumeroExpediente().getExpediente() == null
				|| sentencia.getNumeroExpediente().getExpediente().getEstatus() == null
				|| sentencia.getNumeroExpediente().getExpediente().getEstatus().getValorId() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Long estatusNumExp = null;
		Long estatusSolicitud = null;
		List<Long> estatusIgnorar = new ArrayList<Long>();
		List<Long> estatusConsiderar = new ArrayList<Long>();

		if (sentencia.getNumeroExpediente().getEstatus() != null
				&& sentencia.getNumeroExpediente().getEstatus().getValorId() != null
				&& sentencia.getNumeroExpediente().getEstatus().getValorId() > 0L){
			estatusNumExp = sentencia.getNumeroExpediente().getEstatus().getValorId();
		}
		if(sentencia.getNumeroExpediente().getSolicituds() != null
				&& !sentencia.getNumeroExpediente().getSolicituds().isEmpty()){
			Solicitud solicitud = null;
			for (Solicitud tmp : sentencia.getNumeroExpediente().getSolicituds()) {
				solicitud = tmp;
			}
			if(solicitud.getEstatus()!= null
					&& solicitud.getEstatus().getValorId() != null
					&& solicitud.getEstatus().getValorId() > 0L){
				estatusSolicitud = solicitud.getEstatus().getValorId();
			}
		}

		StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append(" SELECT s ")
		.append(" FROM Sentencia s ")
		.append(" WHERE 1 = 1 ");
		if(estatusNumExp != null) {
			if(estatusNumExp > 0L) {
				hqlQuery.append(" AND s.numeroExpediente.estatus.valorId =  ");
				hqlQuery.append(sentencia.getNumeroExpediente().getEstatus().getValorId()+" ");
				if (estatusNumExp.equals(EstatusExpediente.ABIERTO.getValorId())){
					estatusIgnorar.add(EstatusSolicitud.ABIERTA.getValorId());
					estatusIgnorar.add(EstatusSolicitud.CERRADA.getValorId());
					hqlQuery.append("AND s.numeroExpediente.numeroExpedienteId NOT IN ( ");
					hqlQuery.append(creaQueryIdsNumExpPorEstatusSol(estatusIgnorar));
					hqlQuery.append(" ) ");
				}
			}
		}
		if (estatusSolicitud != null){
			estatusConsiderar.add(estatusSolicitud);
			hqlQuery.append(" AND s.numeroExpediente.numeroExpedienteId IN ( ");
			hqlQuery.append(creaQueryIdsNumExpPorEstatusSol(estatusConsiderar));
			hqlQuery.append(" ) ");
			if (estatusSolicitud.equals(EstatusSolicitud.CERRADA.getValorId())){
				estatusIgnorar.add(EstatusSolicitud.ABIERTA.getValorId());
				hqlQuery.append(" AND s.numeroExpediente.numeroExpedienteId NOT IN ( ");
				hqlQuery.append(creaQueryIdsNumExpPorEstatusSol(estatusIgnorar));
				hqlQuery.append(" ) ");
			}
		}

		//			if(sentencia.getNumeroExpediente().getSolicituds() != null
		//					&& !sentencia.getNumeroExpediente().getSolicituds().isEmpty()){
		//				Solicitud solicitud = null;
		//				for (Solicitud tmp : sentencia.getNumeroExpediente().getSolicituds()) {
		//					solicitud = tmp;
		//				}
		//				if(solicitud.getEstatus()!= null
		//						&& solicitud.getEstatus().getValorId() != null
		//						&& solicitud.getEstatus().getValorId() > 0L){
		//					hqlQuery.append(" AND ( s.numeroExpediente.numeroExpedienteId IN (  ");
		//						hqlQuery.append(" SELECT sol.numeroExpediente.numeroExpedienteId ")
		//								.append(" FROM Solicitud sol ")
		//								.append(" WHERE sol.estatus.valorId = ")
		//								.append(solicitud.getEstatus().getValorId())
		//								.append(" ")
		//								.append(" AND ( sol.numeroExpediente.numeroExpedienteId = s.numeroExpediente.numeroExpedienteId )")
		//							.append(" ) ) ");
		//							
		//				}
		//			}

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		if (pag != null && pag.getCampoOrd() != null && !pag.getCampoOrd().isEmpty()) {
			hqlQuery.append(" order by ");
			hqlQuery.append(pag.getCampoOrd());
			hqlQuery.append(" ").append(pag.getDirOrd());
		}
		
		return ejecutarQueryPaginado(hqlQuery, pag);
	}
	
	/**
	 * M&eacute;todo que consulta una Sentencia por id
	 * @return Sentencia
	 * @throws NSJPNegocioException
	 */
	@SuppressWarnings("unchecked")
	public List<Sentencia> consultarNUS(NombreDemografico nombreDemografico, Boolean esPorCURP)throws NSJPNegocioException{
    	StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append(" SELECT s FROM Sentencia s  ");
        hqlQuery.append(" LEFT JOIN s.involucrado i "); 
//        hqlQuery.append(" JOIN i.persona p ");
        hqlQuery.append(" LEFT JOIN i.nombreDemograficos nd "); 
        hqlQuery.append(" WHERE ");
        if (esPorCURP) {
        	hqlQuery.append(" nd.curp LIKE :CURP ");
        } else {
        	hqlQuery.append(" nd.nombre LIKE :NOMBRE ");
        	hqlQuery.append(" AND nd.apellidoPaterno LIKE :APATERNO ");
            hqlQuery.append(" AND nd.apellidoMaterno LIKE :AMATERNO ");
        	//hqlQuery.append(" COLLATE SQL_LATIN1_GENERAL_CP1_CI_AI ");
        }
        
        Query query = super.getSession().createQuery(hqlQuery.toString());
        
        if (esPorCURP) {
        	query.setString("CURP", nombreDemografico.getCurp());
        }  else {

        	String nombre = Normalizer.normalize(nombreDemografico.getNombre() != null ?  nombreDemografico.getNombre().trim() : "", Normalizer.Form.NFD);
        	nombre = nombre.replaceAll("[^\\p{ASCII}]", "_");
        	String aPaterno = Normalizer.normalize(nombreDemografico.getApellidoPaterno() != null ?  nombreDemografico.getApellidoPaterno().trim() : "", Normalizer.Form.NFD);
        	aPaterno = aPaterno.replaceAll("[^\\p{ASCII}]", "_");
        	String aMaterno = Normalizer.normalize(nombreDemografico.getApellidoMaterno() != null ?  nombreDemografico.getApellidoMaterno().trim() : "", Normalizer.Form.NFD);
        	aPaterno = aPaterno.replaceAll("[^\\p{ASCII}]", "_");

        	query.setString("NOMBRE", nombre);
        	query.setString("APATERNO", aPaterno);
        	query.setString("AMATERNO", aMaterno);
        }
        
        logger.debug("query :: " + query);
        List<Sentencia> resp = query.list();
        logger.debug("resp.size() :: " + resp.size());
        
        return resp;
    }

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.sentencia.SentenciaDAO#consultarSentenciaPorNusYCausa(mx.gob.segob.nsjp.model.Sentencia)
	 */
	@Override
	public Sentencia consultarSentenciaPorNusYCausa(Sentencia sentencia)
			throws NSJPNegocioException {
		
		if (sentencia == null
				|| sentencia.getCnus() == null
				|| sentencia.getCnumeroCausa() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		StringBuilder sb = new StringBuilder("");
		sb.append(" SELECT s ");						   
		sb.append(" FROM Sentencia s ");
		sb.append(" WHERE s.cnumeroCausa = :numeroCausa ");
		sb.append(" AND s.cnus = :nus ");
		
		Query query = getSession().createQuery(sb.toString());
		query.setParameter("numeroCausa", sentencia.getCnumeroCausa());
		query.setParameter("nus", sentencia.getCnus());
		
		return (Sentencia) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Sentencia> consultarSentenciasJEJ(Sentencia sentencia) throws NSJPNegocioException{

		Long estatusNumExp = sentencia.getNumeroExpediente().getEstatus().getValorId();
		Long estatusSolicitud = sentencia.getNumeroExpediente().getSolicituds().iterator().next().getEstatus().getValorId();
		List<Long> estatusIgnorar = new ArrayList<Long>();
		List<Long> estatusConsiderar = new ArrayList<Long>();
		
		StringBuffer sb = new StringBuffer("");
		sb.append(" SELECT s ");
		sb.append(" FROM Sentencia s ");
		sb.append(" WHERE 1=1 ");
		if(estatusNumExp > 0L) {
			sb.append(" AND s.numeroExpediente.estatus.valorId =  ");
			sb.append(sentencia.getNumeroExpediente().getEstatus().getValorId()+" ");
			if (estatusNumExp.equals(EstatusExpediente.ABIERTO.getValorId())){
				estatusIgnorar.add(EstatusSolicitud.ABIERTA.getValorId());
				estatusIgnorar.add(EstatusSolicitud.CERRADA.getValorId());
				sb.append("AND s.numeroExpediente.numeroExpedienteId NOT IN ( ");
				sb.append(creaQueryIdsNumExpPorEstatusSol(estatusIgnorar));
				sb.append(" ) ");
			}
		}
		if (estatusSolicitud > 0L){
			estatusConsiderar.add(estatusSolicitud);
			sb.append(" AND s.numeroExpediente.numeroExpedienteId IN ( ");
			sb.append(creaQueryIdsNumExpPorEstatusSol(estatusConsiderar));
			sb.append(" ) ");
			if (estatusSolicitud.equals(EstatusSolicitud.CERRADA.getValorId())){
				estatusIgnorar.add(EstatusSolicitud.ABIERTA.getValorId());
				sb.append(" AND s.numeroExpediente.numeroExpedienteId NOT IN ( ");
				sb.append(creaQueryIdsNumExpPorEstatusSol(estatusIgnorar));
				sb.append(" ) ");
			}
		}
		
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        if (pag != null && pag.getCampoOrd() != null && !pag.getCampoOrd().isEmpty()) {
            sb.append(" order by ");
            sb.append(pag.getCampoOrd());
            sb.append(" ").append(pag.getDirOrd());
        }
        return (List<Sentencia>) ejecutarQueryPaginado(sb, pag);
	}
	
	private String creaQueryIdsNumExpPorEstatusSol(List<Long> idEstatus){
		StringBuilder sb = new StringBuilder("");
		sb.append(" SELECT DISTINCT(sol.numeroExpediente.numeroExpedienteId) ");
		sb.append(" FROM Solicitud sol ");
		sb.append(" WHERE sol.estatus.valorId IN (");
		for (int i=0; i<idEstatus.size(); i++){
			if (i>0){
				sb.append(", ");
			}
			sb.append(idEstatus.get(i));
		}
		sb.append(") ");
		sb.append("AND sol.numeroExpediente.numeroExpedienteId IS NOT NULL ");
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sentencia> consultarSentenciasPorIdsNumExp(List<Long> idsNumExp)
			throws NSJPNegocioException {
		
		if (idsNumExp == null
				|| idsNumExp.isEmpty()){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		StringBuilder sb = new StringBuilder("");
		sb.append(" SELECT s ");
		sb.append(" FROM Sentencia s ");
		sb.append(" WHERE s.numeroExpediente.numeroExpedienteId IN (");
		sb.append(" :listaIds ");
		sb.append(" ) ");
		
		Query query = getSession().createQuery(sb.toString());
		query.setParameterList("listaIds", idsNumExp);
		return (List<Sentencia>)query.list();
	}
}