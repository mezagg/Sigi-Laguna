/**
 * Nombre del Programa : DelitoPersonaDAO.java
 * Autor                            : cesarAgustin
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 24 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementacion de metodos de accesos a datos de la entidad Delito Persona
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
package mx.gob.segob.nsjp.dao.persona.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.ConsecutivosUtil;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Expediente;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementacion de metodos de accesos a datos de la entidad Delito Persona.
 * 
 * @version 1.0
 * @author cesarAgustin
 * 
 */
@Repository
public class DelitoPersonaDAOImpl extends
		GenericDaoHibernateImpl<DelitoPersona, Long> implements
		DelitoPersonaDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<DelitoPersona> consultarDelitoPerByInvolucrado(
			Long involucradoId) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM DelitoPersona dp ")
				.append("WHERE (dp.victima.elementoId=").append(involucradoId)
				.append(" OR ").append("dp.probableResponsable.elementoId=")
				.append(involucradoId).append(")");
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<DelitoPersona> consultarDelitosPorImputado(Long involucradoId,
			Long expedienteId) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM DelitoPersona dp ")
				.append("WHERE dp.victima.elementoId=").append(involucradoId)
				.append(" OR ").append("dp.probableResponsable.elementoId=")
				.append(involucradoId)
				.append("and dp.delito.expediente.expedienteId=")
				.append(expedienteId);
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@Deprecated
	@SuppressWarnings("unchecked")
	@Override
	public List<DelitoPersona> consultarDelitosPorImputadoResponsable(
			Long idInvolucrado, Long idExpediente) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM DelitoPersona dp WHERE");
		queryString.append(" dp.probableResponsable.elementoId="
				+ idInvolucrado);
		queryString.append(" AND dp.delito.expediente.expedienteId="
				+ idExpediente);
		queryString.append(" AND dp.esActivo = true");
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DelitoPersona> consultarDelitosPorImputadoResponsableConVictima(
			Long idInvolucrado, Long idExpediente) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM DelitoPersona dp WHERE");
		queryString.append(" dp.probableResponsable.elementoId="
				+ idInvolucrado);
		queryString.append(" AND dp.delito.expediente.expedienteId="
				+ idExpediente);
		queryString.append(" AND dp.victima IS NOT NULL");
		queryString.append(" AND dp.esActivo = true");
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return super.ejecutarQueryPaginado(queryString, pag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DelitoPersona> consultarVictimaImputadoPorDelito(Long idDelito,
			Long idExpediente) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM DelitoPersona dp WHERE");
		queryString.append(" dp.delito.expediente.expedienteId=" + idExpediente);
		if (idDelito != null)
			queryString.append(" AND dp.delito=" + idDelito);
			queryString.append(" AND dp.esActivo = true");
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return super.ejecutarQueryPaginado(queryString, pag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DelitoPersona> consultarDelitosPersonaPorExpedienteIdsDelito(List<Long> idsDelitos,
			Long idExpediente) {
		
		String cadenaIdsDelitos = "";
		if (idsDelitos != null && !idsDelitos.isEmpty())
			cadenaIdsDelitos = idsDelitos.toString().substring(1,
					idsDelitos.toString().length() - 1);
				
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM DelitoPersona dp WHERE");
		queryString.append(" dp.delito.expediente.expedienteId=" + idExpediente);
		if (cadenaIdsDelitos != null && !cadenaIdsDelitos.trim().isEmpty())
			queryString.append(" AND dp.delito in (").append( cadenaIdsDelitos ).append( ")");
		
		queryString.append(" AND dp.esActivo = true");
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}
	
	@Override
	public void eliminarRelacionPorDelito(Delito delPersist) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("DELETE FROM DelitoPersona dp");
		queryString.append(" WHERE dp.delito.delitoId="+delPersist.getDelitoId());
		Query query=super.getSession().createQuery(queryString.toString());
		query.executeUpdate();
		
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO#consultarDelitosPorImputadoResponsable(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DelitoPersona> consultarDelitosPorImputadoResponsable(
			Long idInvolucrado) {
		
		//return getHibernateTemplate().find("from DelitoPersona dp where dp.probableResponsable.elementoId=? AND dp.esActivo = true",idInvolucrado);
		
		StringBuffer query = new StringBuffer();
		query.append("from DelitoPersona dp where dp.probableResponsable.elementoId = ");
		query.append(idInvolucrado);
		query.append(" AND dp.esActivo = true");
		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        return super.ejecutarQueryPaginado(query, pag);
		
	}
	
	@Override
	public void desactivarDelitoPersona(Long delitoPersonaId) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE FROM DelitoPersona dp");
		queryString.append(" SET dp.esActivo = false " );
		queryString.append(" WHERE dp.delitoPersonaId="+ delitoPersonaId);
		Query query=super.getSession().createQuery(queryString.toString());
		query.executeUpdate();
		
	}

	@Override
	public Long obtenerDetenidosPorMesYDelito(Date fechaInicio,
			Date fechaFin, Long catDelito) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT COUNT(*) FROM DelitoPersona dp")
					.append(" WHERE dp.probableResponsable.esDetenido=").append(true)
					.append(" AND dp.delito.catDelito=").append(catDelito).append(" AND ")
					.append("CONVERT(VARCHAR, dp.delito.expediente.fechaCreacion ,112) BETWEEN ")
					.append(DateUtils.formatearBD(fechaInicio)).append(" AND ")
					.append(DateUtils.formatearBD(fechaFin));
		Query query = super.getSession().createQuery(queryString.toString());
		return (Long) query.uniqueResult();
	}

	@Override
	public Boolean existeRelacionProbableResponsableVictimaDelitoFormaParticipacion(Long idDelito, Long idProbableResponsable, Long idVictima, Long idFormaParticipacion) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT dp FROM DelitoPersona dp")
					.append(" WHERE dp.probableResponsable.elementoId=").append(idProbableResponsable)
					.append(" AND dp.delito.delitoId =").append(idDelito).append(" AND ")
					.append(" dp.victima.elementoId= ").append(idVictima);
		if (idFormaParticipacion != null && idFormaParticipacion > 0L){
			queryString.append(" AND ")
			.append(" dp.formaParticipacion.valorId = ").append(idFormaParticipacion);
		}
		Query query = super.getSession().createQuery(queryString.toString());
		return !(query.list().isEmpty());
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<DelitoPersona> consultarRelacionesDelitoPersonaPorIdsImputados(
			List<Long> idsImputados, Long idExpediente)
			throws NSJPNegocioException {

		String cadenaIdInv = "";

		if (idsImputados != null && !idsImputados.isEmpty()) {
			cadenaIdInv = idsImputados.toString().substring(1,
					idsImputados.toString().length() - 1);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("involucradosId:: [" + cadenaIdInv + "]");
		}
		if (cadenaIdInv.trim().isEmpty() || idExpediente == null
				|| idExpediente <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		StringBuffer queryString = new StringBuffer();
		queryString
				.append("FROM DelitoPersona dp WHERE dp.probableResponsable.elementoId IN (");
		queryString.append(cadenaIdInv);
		queryString.append(" ) ");

		queryString.append(" AND dp.delito.expediente.expedienteId="
				+ idExpediente);
		queryString.append(" AND dp.esActivo = true");
		
		queryString.append(" ORDER BY dp.probableResponsable.elementoId, dp.delito.catDelito.nombre, dp.victima.elementoId ");

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return super.ejecutarQueryPaginado(queryString, pag);
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO#consultarDelitosPersonaPorIds(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DelitoPersona> consultarDelitosPersonaPorIds(
			List<Long> idsDelitoPersona) throws NSJPNegocioException {
		
		if (idsDelitoPersona == null
				|| idsDelitoPersona.isEmpty()){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		StringBuilder queryStr = new StringBuilder("");
		queryStr.append(" SELECT dp ");
		queryStr.append(" FROM DelitoPersona dp ");
		queryStr.append(" WHERE dp.delitoPersonaId IN (:idsDelitoPersona) ");
		
		Query query = getSession().createQuery(queryStr.toString());
		query.setParameterList("idsDelitoPersona", idsDelitoPersona);
		return (List<DelitoPersona>) query.list();
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO#consultarDelitoPersonaPorCriterios(mx.gob.segob.nsjp.model.DelitoPersona)
	 */
	@Override
	public DelitoPersona consultarDelitoPersonaPorCriterios(DelitoPersona filtro)
			throws NSJPNegocioException {
		
		if(filtro == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		StringBuilder sb = new StringBuilder("");
		sb.append(" SELECT dp ");
		sb.append(" FROM DelitoPersona dp ");
		sb.append(" WHERE 1=1 ");
		if (filtro.getProbableResponsable() != null
				&& filtro.getProbableResponsable().getFolioElemento() != null){
			sb.append(" AND dp.probableResponsable.folioElemento = '");
			sb.append(filtro.getProbableResponsable().getFolioElemento());
			sb.append("' ");
		}
		if (filtro.getVictima() != null
				&& filtro.getVictima().getFolioElemento() != null){
			sb.append(" AND dp.victima.folioElemento = '");
			sb.append(filtro.getVictima().getFolioElemento());
			sb.append("' ");
		}
		if (filtro.getDelito() != null
				&& filtro.getDelito().getCatDelito() != null
				&& filtro.getDelito().getCatDelito().getClaveInterInstitucional() != null){
			sb.append(" AND dp.delito.catDelito.claveInterInstitucional = '");
			sb.append(filtro.getDelito().getCatDelito().getClaveInterInstitucional());
			sb.append("' ");
		}
		
		Query consulta = getSession().createQuery(sb.toString());		
		return (DelitoPersona) consulta.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DelitoPersona> consultarRelacionesDelitoPersonaDelExpediente(
			Expediente expedienteInput) throws NSJPNegocioException {
		
		if (expedienteInput == null || expedienteInput.getExpedienteId() == null
				|| expedienteInput.getExpedienteId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
			
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("FROM DelitoPersona dp WHERE dp.delito.expediente.expedienteId=")
				.append(expedienteInput.getExpedienteId());
		
		queryString.append(" AND dp.esActivo = true");
		queryString
				.append(" ORDER BY dp.probableResponsable.elementoId, dp.delito.catDelito.nombre, dp.victima.elementoId ");

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return super.ejecutarQueryPaginado(queryString, pag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DelitoPersona> consultarListaRelacionesDelitoPersona(
			List<Long> idsRelsDelPer) throws NSJPNegocioException {
		
		StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append(" FROM DelitoPersona dp WHERE dp.delitoPersonaId IN (:idsRelsDelPer) ");
        
  
        Query query = super.getSession().createQuery(hqlQuery.toString()).
		  setParameterList("idsRelsDelPer", idsRelsDelPer);
		return query.list();
	}

	@Override
	public Long obtenerUltimoFolioDelitoPersona(){
		
		final StringBuffer query = new StringBuffer();
		
		query.append(" SELECT MAX ( CAST (");
		query.append(" SUBSTRING( ");
		query.append(" dp.folioDelitoPersona, ");
		query.append(" 8,");//Se puede usar char index
		query.append(" LEN(dp.folioDelitoPersona) )  AS long ) )");
		query.append(" FROM DelitoPersona dp ");		
		query.append(" WHERE dp.folioDelitoPersona IS NOT NULL ");
		query.append(" AND dp.folioDelitoPersona like '");
	    query.append(super.consultarInsitucionActual().getMonograma());
	    query.append(Calendar.getInstance().get(Calendar.YEAR));
	    query.append(ConsecutivosUtil.SEPARADOR_CONSECUTIVO);
	    query.append("%'");

	    logger.debug("query :: " + query);
        Query hbq = super.getSession().createQuery(query.toString());
        return (Long) hbq.uniqueResult();
	}
	

	
	@Override
	public DelitoPersona consultarRelacionDelitoPersonaPorFolio(
			DelitoPersona filtro) {

		if (filtro == null || filtro.getFolioDelitoPersona() == null
				|| filtro.getFolioDelitoPersona().trim().isEmpty()) {
			return null;
		}

		StringBuilder sb = new StringBuilder("");

		sb.append(" SELECT dp ");
		sb.append(" FROM DelitoPersona dp ");
		sb.append(" WHERE dp.folioDelitoPersona like '").append(
				filtro.getFolioDelitoPersona());
		sb.append("' ");

		Query consulta = getSession().createQuery(sb.toString());
		return (DelitoPersona) consulta.uniqueResult();
	}
}
