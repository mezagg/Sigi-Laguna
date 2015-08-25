/**
*
* Nombre del Programa : InvolucradoDAOImpl.java                                    
* Autor                            : Cesar Agustin                                               
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 05/04/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Implementaci&oacute;n para el DAO de la entidad Narrativa                      
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
package mx.gob.segob.nsjp.dao.involucrado.impl;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.Valor;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * @author CesarAgustin
 * @version 1.0
 */
@Repository
public class InvolucradoDAOImpl extends GenericDaoHibernateImpl<Involucrado, Long>
		implements InvolucradoDAO {
	
	@SuppressWarnings("unchecked")
	public List<Involucrado> obtenerInvolucradosAll() {
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("FROM Involucrado ");	 			
		Query query = super.getSession().createQuery(queryStr.toString());	
		return query.list();	
	}

	
	public Expediente obtenerExpediente(Integer expedienteId) {
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("SELECT e ")
				.append("FROM Expediente e ")
				.append("WHERE e.expedienteId=:expedienteId");		
		Query query = super.getSession().createQuery(queryStr.toString());	
		query.setParameter("expedienteId", expedienteId);
		return (Expediente)query.uniqueResult();
	}


	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarInvolucradosByExpediente(
			Long expedienteId) {
		final StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT i ")
					.append( "FROM Involucrado i ")
//					.append(" LEFT JOIN i.nombreDemograficos as nd ")
					.append(" WHERE i.expediente.expedienteId=").append(expedienteId)
					.append(" and i.esActivo = true");

//		final PaginacionDTO pag = PaginacionThreadHolder.get();

//		logger.debug("pag :: " + pag);
//		if (pag != null && pag.getCampoOrd() != null) {
//			if (pag.getCampoOrd().equals("1")) {
//				queryString.append(" order by ");
//				queryString.append(" nd.nombre");
//				queryString.append(" ").append(pag.getDirOrd());
//			}
//			if (pag.getCampoOrd().equals("2")) {
//				queryString.append(" order by ");
//				queryString.append(" i.calidad.tipoCalidad.valor");
//				queryString.append(" ").append(pag.getDirOrd());
//			}
//		}
//		return super.ejecutarQueryPaginado(queryString, pag);
		return super.getSession().createQuery(queryString.toString()).list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Involucrado> obtenerInvolucradosPorExpedienteYCalidades(Long expedienteId, Calidades[] calidades, Boolean esActivo) {
		
		final StringBuffer queryString = new StringBuffer();
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		
		queryString.append("FROM Involucrado i ")
					.append(" WHERE i.expediente = ").append(expedienteId);
		
		if(esActivo != null){
			queryString.append(" AND i.esActivo = ").append(esActivo);
		}
			
		if(calidades!= null && calidades.length>0){
			queryString.append(" and i.calidad.tipoCalidad.valorId in ( ");
			for(int iCal = 0; iCal<calidades.length;iCal++){
				queryString.append(iCal>0?","+calidades[iCal].getValorId():calidades[iCal].getValorId());
			}
			queryString.append(")");
		}
		
		return super.ejecutarQueryPaginado(queryString, pag);   
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> obtenerFolioInterInstInvolucradosPorExpedienteYCalidades(
			Long expedienteId, Calidades[] calidades, Boolean esActivo) {
		
		final StringBuffer queryString = new StringBuffer();
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		
		queryString.append("SELECT i.folioElemInterInstitucional FROM Involucrado i ")
					.append(" WHERE i.expediente = ").append(expedienteId)
					.append(" AND i.folioElemInterInstitucional is not null ");
		if(esActivo != null){
			queryString.append(" AND i.esActivo = ").append(esActivo);
		}
			
		if(calidades!= null && calidades.length>0){
			queryString.append(" and i.calidad.tipoCalidad.valorId in ( ");
			for(int iCal = 0; iCal<calidades.length;iCal++)
				queryString.append(iCal>0?","+calidades[iCal].getValorId():calidades[iCal].getValorId());
			queryString.append(")");
		}
		
		
		logger.info("Script:"+ queryString);
		return super.ejecutarQueryPaginado(queryString, pag);   
	}

	
	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarExpedientesByAlias(
			String aliasInvolucrado) {
		final StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT DISTINCT i ")
					.append("FROM Involucrado i, AliasInvolucrado a ")
					.append("WHERE a.alias=:aliasInvolucrado ")
					.append("AND a.involucrado.elementoId=i.elementoId");
		Query query = super.getSession().createQuery(queryString.toString());
		query.setParameter("aliasInvolucrado", aliasInvolucrado);
				
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarExpedientesByAliasLike(
			String aliasInvolucrado) {
		final StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT DISTINCT i ")
					.append("FROM Involucrado i, AliasInvolucrado a ")
					.append("WHERE a.alias like :aliasInvolucrado ")
					.append("AND a.involucrado.elementoId=i.elementoId");
		Query query = super.getSession().createQuery(queryString.toString());
		query.setParameter("aliasInvolucrado", aliasInvolucrado);
				
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarExpedientesByNombre(
			FiltroExpedienteDTO filtroExpedienteDTO) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT DISTINCT i ")
					.append("FROM Involucrado i LEFT JOIN i.nombreDemograficos nd LEFT JOIN i.expediente ex ,NumeroExpediente ne ")
					.append("WHERE ");					
		if (!filtroExpedienteDTO.getApellidos().equals("%%") && !filtroExpedienteDTO.getApellidoMat().equals("%%") && !filtroExpedienteDTO.getNombre().equals("%%")) {
			queryString.append("nd.nombre like '").append(filtroExpedienteDTO.getNombre()).append("' AND ")
						.append("nd.apellidoPaterno like '").append(filtroExpedienteDTO.getApellidos()).append("' AND ")
						.append("nd.apellidoMaterno like '").append(filtroExpedienteDTO.getApellidoMat()).append("'");
		} else if (!filtroExpedienteDTO.getApellidos().equals("%%") && !filtroExpedienteDTO.getNombre().equals("%%")) {
			queryString.append("nd.nombre like '").append(filtroExpedienteDTO.getNombre()).append("' AND ")
						.append("nd.apellidoPaterno like '").append(filtroExpedienteDTO.getApellidos()).append("'");
		} else if (!filtroExpedienteDTO.getApellidoMat().equals("%%") && !filtroExpedienteDTO.getNombre().equals("%%")) {
			queryString.append("nd.nombre like '").append(filtroExpedienteDTO.getNombre()).append("' AND ")
						.append("nd.apellidoMaterno like '").append(filtroExpedienteDTO.getApellidoMat()).append("'");
		} else if (!filtroExpedienteDTO.getApellidos().equals("%%") && !filtroExpedienteDTO.getApellidoMat().equals("%%")) {
			queryString.append("nd.apellidoPaterno like '").append(filtroExpedienteDTO.getApellidos()).append("' AND ")
						.append("nd.apellidoMaterno like '").append(filtroExpedienteDTO.getApellidoMat()).append("'");
		}else if (!filtroExpedienteDTO.getNombre().equals("%%")) {
			queryString.append("nd.nombre like '").append(filtroExpedienteDTO.getNombre()).append("'");
		} else if (!filtroExpedienteDTO.getApellidoMat().equals("%%")) {
			queryString.append("nd.apellidoMaterno like '").append(filtroExpedienteDTO.getApellidoMat()).append("'");
		} else if (!filtroExpedienteDTO.getApellidos().equals("%%")) {
			queryString.append("nd.apellidoPaterno like '").append(filtroExpedienteDTO.getApellidos()).append("'");
		}
		
		if(filtroExpedienteDTO.getUsuario()!=null && 
				filtroExpedienteDTO.getUsuario().getFuncionario()!=null &&
						filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante()!=null){
			queryString.append(" AND ex.discriminante.catDiscriminanteId = ");
			queryString.append(filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante().getCatDiscriminanteId());
			if(filtroExpedienteDTO.getIdFuncionario()!=null){
				queryString.append(" AND ex.expedienteId = ne.expediente.expedienteId");
				queryString.append(" AND ne.funcionario.claveFuncionario = ");
				queryString.append(filtroExpedienteDTO.getIdFuncionario());
			}
			if(filtroExpedienteDTO.getUsuario().getAreaActual()!=null && filtroExpedienteDTO.getUsuario().getAreaActual().getAreaId()!=null){
				queryString.append(" AND ne.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
				queryString.append(filtroExpedienteDTO.getUsuario().getAreaActual().getAreaId());
			}
		}
		
		if(filtroExpedienteDTO.getIdArea()!=null ){
			queryString.append(" AND ne.expediente.expedienteId = ex.expedienteId ");
			queryString.append(" AND ne.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
			queryString.append(filtroExpedienteDTO.getIdArea());
		}
								
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Involucrado> obtenerInvByIdExpAndCalidad(Long idExpediente,
			Long calidadId, Boolean esDetenido) {
		final StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT i ")
					.append(" FROM Involucrado i ")
					.append(" LEFT JOIN i.nombreDemograficos nd ")
					.append(" WHERE i.expediente.expedienteId="+idExpediente)
					.append(" AND i.calidad.tipoCalidad.valorId="+calidadId)
					.append(" and i.esActivo = true");

		if(esDetenido!=null){
			queryString.append(" AND i.esDetenido="+esDetenido);
		}
		
		if(calidadId != null && (calidadId.equals(Calidades.DENUNCIANTE.getValorId()) || calidadId.equals(Calidades.DENUNCIANTE_ORGANIZACION.getValorId()))){
			queryString.append(" ORDER BY i.elementoId desc");

		}else{
			queryString.append(" ORDER BY nd.nombre, nd.apellidoPaterno");
		}

		Query query = super.getSession().createQuery(queryString.toString());
		
		if(calidadId != null && (calidadId.equals(Calidades.DENUNCIANTE.getValorId()) || calidadId.equals(Calidades.DENUNCIANTE_ORGANIZACION.getValorId()))){
			query.setMaxResults(1);
		}

		
		final List<Involucrado> resp =  query.list();
		logger.debug("resp.size() :: "+resp.size());
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Involucrado> obtenerInvolucradosByAudiencia(Long audienciaId, Long calidadId) {
		final StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT ia.involucrado ");
		queryString.append("FROM InvolucradoAudiencia ia ");
//		queryString.append("WHERE ia.id.audienciaId=:audienciaId");
		queryString.append("WHERE ia.id.audienciaId="+audienciaId);
		if(calidadId != -1){
			queryString.append(" AND ia.involucrado.calidad.tipoCalidad.valorId=:calidadId");
		}
		Query query = super.getSession().createQuery(queryString.toString());
//		query.setParameter("audienciaId", audienciaId);
		if(calidadId != -1){
			query.setParameter("calidadId", calidadId);
		}
		return query.list();
	}

	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO#obtenerInvolucradosByCasoYCalidades(java.lang.String, mx.gob.segob.nsjp.comun.enums.calidad.Calidades[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Involucrado> obtenerInvolucradosByCasoYCalidades(
			String numeroCaso, Calidades[] calidades) {
		StringBuilder query = new StringBuilder();
		
		query.append("from Involucrado inv where inv.expediente.caso.numeroGeneralCaso = ? " +
				" and inv.calidad.tipoCalidad.valorId in ( ");
		for(int iCal = 0; iCal<calidades.length;iCal++){
			query.append(iCal>0?","+calidades[iCal].getValorId():calidades[iCal].getValorId());
		}
		query.append(")");
		
		return getHibernateTemplate().find(query.toString(),numeroCaso);
	}

	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarInvolucradosByIds(
			List<Long> idInvolucrados) {
		final StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT i ")
					.append("FROM Involucrado i ");					
		String lista = idInvolucrados.toString();

		if(!lista.trim().isEmpty() && lista.indexOf('[')!= -1){
			lista= lista.substring(1, lista.length()-1);
			logger.info("Lista:"+ lista);
			queryString.append(" WHERE i.elementoId in (")
			.append( lista + ")" );
		}
		logger.info("Query:" + queryString);
		Query query = super.getSession().createQuery(queryString.toString());
//		query.setParameter("expedienteId", expedienteId);
		return query.list();
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> obtenerExpedientesPorCondicionDetencionInvYMes(
			Date fechaInicio, Date fechaFin, Boolean condicionDet) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT MONTH(i.expediente.fechaCreacion), COUNT(*) FROM Involucrado i ")
					.append("WHERE i.esDetenido=").append(condicionDet)
					.append(" AND CONVERT(VARCHAR, i.expediente.fechaCreacion ,112) BETWEEN ")
					.append(DateUtils.formatearBD(fechaInicio)).append(" AND ")
					.append(DateUtils.formatearBD(fechaFin)).append("GROUP BY MONTH(i.expediente.fechaCreacion)");
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}


	@Override
	public Involucrado consultarInvolucradoPorFolioElemento(String folioElemento) {
		final StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append("SELECT i ")
				.append("FROM Involucrado i ")
				.append("WHERE i.folioElemento = '"+folioElemento+"'");

		Query query = super.getSession().createQuery(hqlQuery.toString());
		return (Involucrado)query.uniqueResult();
	}

	@Override
	public Involucrado obtenerInvolucradoByFolioElemento(String folioInvolucrado) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM Involucrado i WHERE ")
					.append("i.folioElemento='").append(folioInvolucrado)
					.append("'");
		Query query = super.getSession().createQuery(queryString.toString());
		return (Involucrado) query.uniqueResult();
//		return (Involucrado) getHibernateTemplate().find(queryString.toString(), folioInvolucrado);
	}

	
	
	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarInvolucradosDeAudienciaPorCalidad(Long idAudiencia, List<Long> calidades,Boolean involucradosVivos) {

    	StringBuffer queryStr = new StringBuffer();
    	
    	String idsCalidades = "";
    	if(calidades != null && calidades.size() > 0){
    		for (Long id : calidades) {
    			idsCalidades = idsCalidades + id + ",";
    		}
    		idsCalidades = idsCalidades.substring(0, idsCalidades.lastIndexOf(","));
    	}
		
    	    	
		queryStr.append("SELECT i FROM Involucrado i where 1=1 ");
		
		if(calidades != null && calidades.size() > 0){
			queryStr.append(" AND i.calidad.tipoCalidad.valorId IN (");
			queryStr.append(idsCalidades);
			queryStr.append(")");
		}
		
		if(involucradosVivos != null){
			queryStr.append(" AND i.esVivo = ");
			queryStr.append(involucradosVivos);
		}
		queryStr.append(" AND i.elementoId IN (");
			queryStr.append(" SELECT ia.involucrado.elementoId FROM InvolucradoAudiencia ia where ia.audiencia.audienciaId = ").append(idAudiencia);
		queryStr.append(")");		
        
       
        final PaginacionDTO pag = PaginacionThreadHolder.get();

		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				queryStr.append(" order by ");
				queryStr.append("i.calidad.tipoCalidad.valorId");
				queryStr.append(" ").append(pag.getDirOrd());
			}
		}
		
		return super.ejecutarQueryPaginado(queryStr, pag);   
    }
	
	@SuppressWarnings("unchecked")
	public List<Involucrado> obtenerDenuncianteVictimaSinPaginado(Long idAudiencia,Long calidadId) {

    	StringBuffer queryStr = new StringBuffer();
    	
		queryStr.append("SELECT i FROM Involucrado i");
		
		queryStr.append(" WHERE i.calidad.tipoCalidad.valorId = ");
		queryStr.append(calidadId);
		
		if(calidadId.equals(Calidades.DENUNCIANTE.getValorId())){
			queryStr.append(" AND i.condicion = 1");
		}
		
		queryStr.append(" AND i.elementoId IN (");
			queryStr.append(" SELECT ia.involucrado.elementoId FROM InvolucradoAudiencia ia where ia.audiencia.audienciaId = ").append(idAudiencia);
		queryStr.append(")");		

		Query query = super.getSession().createQuery(queryStr.toString());
		return query.list();
    }

	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO#obtenerInvolucradoPorCasoYFolioElemento(mx.gob.segob.nsjp.model.Involucrado, mx.gob.segob.nsjp.model.Caso)
	 */
	@Override
	public Involucrado obtenerInvolucradoPorCasoYFolioElemento(Involucrado involucrado, Caso caso) throws NSJPNegocioException{
		
		if(involucrado == null 
				|| involucrado.getFolioElemento() == null 
				|| involucrado.getFolioElemento().isEmpty()
				|| caso == null
				|| caso.getNumeroGeneralCaso() == null
				|| caso.getNumeroGeneralCaso().isEmpty()){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT i ")
				   .append(" FROM Involucrado i ")
				   .append(" WHERE i.folioElemento = :folioElemento ")
				   .append(" AND i.expediente.caso.numeroGeneralCaso = :numeroCaso ");
		Query query = super.getSession().createQuery(queryString.toString());
		query.setParameter("folioElemento", involucrado.getFolioElemento());
		query.setParameter("numeroCaso", caso.getNumeroGeneralCaso());
		
		return (Involucrado) query.uniqueResult();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Involucrado> consultarProbablesResponsablesParaSolucitudDeDefensor(
			Long expedienteId, Long calidadId, Boolean esDetenido,
			Boolean noTieneSolicitudDefensor,Boolean tieneRelDelitoPersona) throws NSJPNegocioException {

		if (expedienteId == null || expedienteId <= 0L || calidadId == null
				|| calidadId <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		final StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT i FROM Involucrado i ")
				.append(" LEFT JOIN i.nombreDemograficos nd ")
				.append(" WHERE i.expediente.expedienteId=" + expedienteId)
				.append(" AND i.calidad.tipoCalidad.valorId=" + calidadId)
				.append(" AND i.esActivo = true");
		
		if (esDetenido != null && esDetenido.equals(true)) {
			queryString.append(" AND i.esDetenido=" + esDetenido);
		}

		if (noTieneSolicitudDefensor != null
				&& noTieneSolicitudDefensor.equals(true)) {
			queryString.append(" AND i.elementoId NOT IN ( ")
					.append(" SELECT isd.involucrado.elementoId FROM")
					.append(" InvolucradoSolicitudDefensor isd ")
					.append(" ) ");

		}
		
		if (tieneRelDelitoPersona != null && tieneRelDelitoPersona.equals(true)) {
			queryString
					.append(" AND i.elementoId IN ( ")
					.append(" SELECT dp.probableResponsable.elementoId FROM")
					.append(" DelitoPersona dp WHERE dp.delito.expediente.expedienteId="
							+ expedienteId)
					.append(" AND dp.victima IS NOT NULL")
					.append(" ) ");

		}

		queryString.append(" ORDER BY nd.nombre, nd.apellidoPaterno");

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return super.ejecutarQueryPaginado(queryString, pag);
	}


	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO#consultarInvolucradosXSituacionSinSentencia(mx.gob.segob.nsjp.model.Valor)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Involucrado> consultarInvolucradosXSituacionSinSentencia(
			Valor situacionJuridica) throws NSJPNegocioException {
		
		if (situacionJuridica == null
				|| situacionJuridica.getValorId() == null
				|| situacionJuridica.getValorId() < 1L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		
		StringBuffer queryString = new StringBuffer("");
		queryString.append(" SELECT Inv ");
		queryString.append(" FROM Involucrado Inv ");
		queryString.append(" WHERE Inv.situacionJuridica.valorId = ");
		queryString.append(situacionJuridica.getValorId());
		queryString.append(" AND Inv.elementoId NOT IN ( ");
		queryString.append(" 	SELECT DISTINCT(s.involucrado.elementoId) ");
		queryString.append(" 	FROM Sentencia s ");
		queryString.append(" ) ");
		
		return super.ejecutarQueryPaginado(queryString, pag);
	}
	
	@Override
	public Long consultarEtapaMaximaInvolucradosDelExpediente(Long expedienteId) {
		StringBuffer queryString = new StringBuffer("");

		queryString
				.append("Select  ce.catEtapaId FROM CatEtapa ce where ce.ordenSecuencia = ( "
						+ " SELECT MAX(I.etapaInvolucrado.ordenSecuencia) "
						+ " FROM Involucrado I " + " WHERE I.esActivo = true "
						+ "  AND I.expediente.expedienteId=")
				.append(expedienteId).append(" )");

		Query query = super.getSession().createQuery(queryString.toString());

		return (Long) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Involucrado> consultarInvolucradosByExpedientePaginacion(
			Long expedienteId) {
		final StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT i ")
					.append( "FROM Involucrado i ")
					.append(" WHERE i.expediente.expedienteId=").append(expedienteId)
					.append(" and i.esActivo = true");
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		
		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("2")) {
				queryString.append(" order by ");
				queryString.append(" i.calidad.tipoCalidad.valor");
				queryString.append(" ").append(pag.getDirOrd());
			}
		}
		
		return super.ejecutarQueryPaginado(queryString, pag);
	}


}
