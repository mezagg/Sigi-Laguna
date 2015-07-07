/**
* Nombre del Programa : SolicitudPericialDAOImpl.java
* Autor                            : rgama
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 3 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacio de metodos de acceso a datos para la entidad SolicitudPericialDAO
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
package mx.gob.segob.nsjp.dao.solicitud.impl;
import java.awt.geom.Area;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.funcionario.Puestos;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.SolicitudPericial;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;


/**
 * Implementacio de metodos de acceso a datos para la entidad SolicitudPericialDAO.
 * @version 1.0
 * @author rgama
 *
 */
@Repository
public class SolicitudPericialDAOImpl extends
		GenericDaoHibernateImpl<SolicitudPericial, Long> implements
		SolicitudPericialDAO {
	
	@SuppressWarnings("unchecked")
	public List<SolicitudPericial> consultarSolicitudesPericiales(Long tipoSolicitud, Long estatus, String puestoUsuarioSolicitante) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT sp ")
					.append(" FROM SolicitudPericial sp ")
					.append(" WHERE sp.estatus.valorId = :estatus")
					.append(" AND sp.tipoSolicitud.valorId = :tipoSolicitud")
					.append(" AND sp.puestoUsuarioSolicitante LIKE :puestoUsuarioSolicitante");
		Query query = super.getSession().createQuery(queryString.toString());
		query.setParameter("estatus", estatus);
		query.setParameter("tipoSolicitud", tipoSolicitud);
		query.setParameter("puestoUsuarioSolicitante", puestoUsuarioSolicitante);

		return query.list();
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Solicitud> consultarSolicitudesPericialPorTipoEstatusYUsuario(
			Long tipoSol, Long estatusSol, Long idUsuario) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM SolicitudPericial sp WHERE ")
					.append("sp.tipoSolicitud=").append(tipoSol)
					.append(" AND sp.estatus=").append(estatusSol)
					.append(" AND sp.destinatario=").append(idUsuario);

		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("1")) {
            	queryString.append(" order by ");
            	queryString.append("sp.folioSolicitud");
            	queryString.append(" ").append(pag.getDirOrd());
            }
        }
        logger.debug("queryString :: " + queryString);
        final Query query = super.getSession().createQuery(queryString.toString());
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(pag.getFirstResult());
            if (pag.getRows() != null & pag.getRows() > 0) {
                query.setMaxResults(pag.getRows());
            } else {
                query.setMaxResults(PaginacionDTO.DEFAULT_MAX_RESULT); // default
            }
        }
        
        final List<Solicitud> resp = query.list();
        if (logger.isDebugEnabled()) {
            logger.debug("resp.size() :: " + resp.size());
        }
        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(0);
            query.setMaxResults(-1);
            final List<Solicitud> temp = query.list();
            logger.debug("temp.size() :: " + temp.size());
            pag.setTotalRegistros(new Long(temp.size()));
            PaginacionThreadHolder.set(pag);
        }
		
		return resp;
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO#consultarSolicitudesPericialesPorTipoYEstatusDePuestoDestinatario(mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes, mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud, mx.gob.segob.nsjp.comun.enums.funcionario.Puestos)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesPorTipoYEstatusDePuestoDestinatario(
			TiposSolicitudes tipo, EstatusSolicitud estado, Puestos puesto)
			throws NSJPNegocioException {
	    
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM SolicitudPericial sp WHERE ")
					.append("sp.estatus.valorId=").append(estado.getValorId())
					.append(" AND sp.tipoSolicitud.valorId=").append(tipo.getValorId())
					.append(" AND sp.destinatario.puesto.valorId=").append(puesto.getValorId());

		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
        	if (pag.getCampoOrd().equals("1")) {
            	queryString.append(" order by ");
            	queryString.append("sp.numeroExpediente");
            	queryString.append(" ").append(pag.getDirOrd());
        	}else if(pag.getCampoOrd().equals("2")) {
            	queryString.append(" order by ");
            	queryString.append("sp.folioSolicitud");
            	queryString.append(" ").append(pag.getDirOrd());
            }else if (pag.getCampoOrd().equals("5")) {
            	queryString.append(" order by ");
            	queryString.append("sp.especialidad.valor");
            	queryString.append(" ").append(pag.getDirOrd());
            }else if (pag.getCampoOrd().equals("6")) {
            	queryString.append(" order by ");
            	queryString.append("sp.fechaLimite");
            	queryString.append(" ").append(pag.getDirOrd());
            }else if (pag.getCampoOrd().equals("7")) {
            	queryString.append(" order by ");
            	queryString.append("sp.fechaCreacion");
            	queryString.append(" ").append(pag.getDirOrd());
            }
        }
        return super.ejecutarQueryPaginado(queryString, pag);
		
	}

	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO#consultarSolicitudesPericialesPorTipoYEstatusDePuestoDestinatario(mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes, mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud, mx.gob.segob.nsjp.comun.enums.funcionario.Puestos)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesPorTipoEstatusAreaDestinatario(
			TiposSolicitudes tipo, EstatusSolicitud estado, Long area)
			throws NSJPNegocioException {
	    
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM SolicitudPericial sp WHERE ")
					.append("sp.estatus.valorId=").append(estado.getValorId())
					.append(" AND sp.tipoSolicitud.valorId=").append(tipo.getValorId())
					//.append(" AND sp.destinatario.area.jerarquiaOrganizacionalId=").append(area);
					.append(" AND sp.areaDestino=").append(area);

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		if(pag != null && pag.getSearchField() != null ){
			if(pag.getSearchField().equals("2")){
				pag.setSearchField("folioSolicitud");
				queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "sp", false, false));
			}else if(pag.getSearchField().equals("3")){
				pag.setSearchField("numeroGeneralCaso");
				queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "sp.numeroExpediente.expediente.caso", false, false));
			}else if(pag.getSearchField().equals("1")){
				pag.setSearchField("numeroExpediente");
				queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "sp.numeroExpediente", false, false));
			}else if(pag.getSearchField().equals("4")){
				pag.setSearchField("nombreFuncionario");
				queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "sp.funcionarioSolicitante", false, false));
			}else if(pag.getSearchField().equals("5")){
				pag.setSearchField("valor");
				queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "sp.especialidad", false, false));
			}else if(pag.getSearchField().equals("6")){
				pag.setSearchField("fechaLimite");
				queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "sp", false, true));
			}else if(pag.getSearchField().equals("7")){
				pag.setSearchField("fechaCreacion");
				queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "sp", false, true));
			}
		}
		
		
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            
        	if (pag.getCampoOrd().equals("1")) {
            	queryString.append(" order by ");
            	queryString.append("sp.numeroExpediente.numeroExpediente");
            	queryString.append(" ").append(pag.getDirOrd());
        	}else if(pag.getCampoOrd().equals("2")) {
            	queryString.append(" order by ");
            	queryString.append("sp.folioSolicitud");
            	queryString.append(" ").append(pag.getDirOrd());
            }else if (pag.getCampoOrd().equals("5")) {
            	queryString.append(" order by ");
            	queryString.append("sp.especialidad.valor");
            	queryString.append(" ").append(pag.getDirOrd());
            }else if (pag.getCampoOrd().equals("6")) {
            	queryString.append(" order by ");
            	queryString.append("sp.fechaLimite");
            	queryString.append(" ").append(pag.getDirOrd());
            }else if (pag.getCampoOrd().equals("7")) {
            	queryString.append(" order by ");
            	queryString.append("sp.fechaCreacion");
            	queryString.append(" ").append(pag.getDirOrd());
            }
        }
        logger.info("Query Busqueda Lupa: " + queryString);
        return super.ejecutarQueryPaginado(queryString, pag);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO#consultarSolicitudesPericialesPorEstatusYDestinatario(mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud, mx.gob.segob.nsjp.model.Funcionario)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesPorEstatusYDestinatario(
			EstatusSolicitud estatus, Funcionario destinatario, Boolean esCoorPerDef)
			throws NSJPNegocioException {
		StringBuffer consulta = new StringBuffer();
		consulta.append("select sp from SolicitudPericial sp where sp.estatus.valorId = ")
				.append(estatus.getValorId())
				.append(" and sp.destinatario.claveFuncionario = ")
				.append(destinatario.getClaveFuncionario());
		if(esCoorPerDef ){
			consulta.append(" and (sp.areaDestino != ")
			.append(Areas.COORDINACION_PERICIALES_DEF.parseLong())
			.append(" or sp.areaDestino is null )");
		}
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		if(pag != null && pag.getSearchField() != null ){
			if(pag.getSearchField().equals("1")){
				pag.setSearchField("nombreFuncionario");
				consulta.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "sp.funcionarioSolicitante", false, false));
			}else if(pag.getSearchField().equals("2")){
				pag.setSearchField("valor");
				consulta.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "sp.tipoSolicitud", false, false));
			}else if(pag.getSearchField().equals("3")){
				pag.setSearchField("numeroExpediente");
				consulta.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "sp.numeroExpediente", false, false));
			}else if(pag.getSearchField().equals("4")){
				pag.setSearchField("numeroGeneralCaso");
				consulta.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "sp.numeroExpediente.expediente.caso", false, false));
			}else if(pag.getSearchField().equals("5")){
				pag.setSearchField("fechaLimite");
				consulta.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "sp", false, true));
			}else if(pag.getSearchField().equals("6")){
				pag.setSearchField("acuseRecibos");
				consulta.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "sp", false, false));
			}
		}
		
		
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            
        	if (pag.getCampoOrd().equals("3")) {
        		consulta.append(" order by ");
        		consulta.append("sp.numeroExpediente.numeroExpediente");
        		consulta.append(" ").append(pag.getDirOrd());
        	}else if(pag.getCampoOrd().equals("4")) {
        		consulta.append(" order by ");
        		consulta.append("sp.numeroExpediente.expediente.caso.numeroGeneralCaso");
        		consulta.append(" ").append(pag.getDirOrd());
            }else if (pag.getCampoOrd().equals("5")) {
            	consulta.append(" order by ");
            	consulta.append("sp.fechaLimite");
            	consulta.append(" ").append(pag.getDirOrd());
            }
        }
        logger.info("Query Busqueda Lupa: " + consulta);
		return super.ejecutarQueryPaginado(consulta, pag);
	}

	//FIXME ¿Y la funcionalidad?
	@Override
	public List<SolicitudPericial> actualizarStatusSolicitudPericial(
			SolicitudPericial solicitudPericial) {
		logger.debug("Ejecutando servicio para actualizar solicitud pericial");
		StringBuffer queryString = new StringBuffer();		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO#consultarSolicitudesPericialesPorPuestoSolicitanteEstatusYNumeroExpediente(mx.gob.segob.nsjp.comun.enums.funcionario.Puestos, mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud[], java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesPorPuestoSolicitanteEstatusYNumeroExpediente(
			Puestos puesto, EstatusSolicitud[] estados, Long numeroExpedienteId) {
		StringBuffer query = new StringBuffer();
		//Se comenta para poder mostrar todas las solicitudes de periciales existentes en el expediente sin verificar el puesto del usuario logueado
//		query.append("from SolicitudPericial sp where sp.funcionarioSolicitante.puesto.valorId = ? ");
		query.append("from SolicitudPericial sp where 1=1 ");
		if(estados != null){
			for(EstatusSolicitud estado:estados){
				query.append(" and sp.estatus.valorId =  " + estado.getValorId() + " ");
			}
		}
		
		List<Object[]> resultados=null;
		if(estados !=null && estados.length>0 && estados[0].equals(EstatusSolicitud.EN_PROCESO)){
//			query.append(" and sp.documentoId in  " + estado.getValorId() + " ");
			
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT Distinct(cFolioSolicitud),Solicitud_id from Solicitud where NumeroExpediente_id="+numeroExpedienteId+" and Estatus_val="+estados[0].getValorId());
			Query q = super.getSession().createSQLQuery(sb.toString());
			resultados = q.list();
			logger.debug("Ejecutando servicio para actualizar solicitud pericial");
		
			logger.debug("Ejecutando"+resultados.getClass().getName());
//			Se comenta codigo para que aparescan las solicitudes en la pestaña de periciales en el visor de elementos en ui a 
//			reserva de que afecte otro flujo por lo tanto solo se comenta y no se borra el codigo
			if(resultados!=null && !resultados.isEmpty()){
				query.append(" and sp.documentoId in(");
				int j=0;
				for (Object[] objects : resultados) {
					if(j>0){
						query.append(",");
					}
					Object[] list = resultados.get(j);
					query.append(list[1].toString());
					j++;
				}
				query.append(")");
			}
		}
		query.append(" and sp.numeroExpediente.numeroExpedienteId = ?");
				
		return getHibernateTemplate().find(query.toString(),numeroExpedienteId);
	}

	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudPericialDAO#consultarSolicitudesPericialPorFolio(mx.gob.segob.nsjp.model.SolicitudPericial)
	 */
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialPorFolioEstatusNoCerrado(
			SolicitudPericial folioSolicitudPericial)
			throws NSJPNegocioException {
			if(folioSolicitudPericial!=null && folioSolicitudPericial.getFolioSolicitud()!=null && !folioSolicitudPericial.getFolioSolicitud().equals("")){
				List<SolicitudPericial> solicitudes=getHibernateTemplate().find("from SolicitudPericial sp where sp.folioSolicitud = ? and " +
						" sp.estatus.valorId = ?",
						folioSolicitudPericial.getFolioSolicitud(),EstatusSolicitud.CERRADA.getValorId());
						if(!solicitudes.isEmpty()){
							return solicitudes;
						}
			}
			return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialesPorFolioParaReasignacion(SolicitudPericial solicitud)
			throws NSJPNegocioException {
	    
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM SolicitudPericial sp WHERE ")
					.append(" sp.folioSolicitud LIKE '%").append(solicitud.getFolioSolicitud()).append("%'")
					.append(" AND sp.estatus.valorId != ").append(EstatusSolicitud.CERRADA.getValorId())
					.append(" AND sp.tipoSolicitud.valorId = ").append(TiposSolicitudes.DICTAMEN.getValorId())
					.append(" AND sp.areaDestino IS NULL")
					.append(" AND sp.destinatario IS NOT NULL")
					.append(" order by sp.documentoId desc");

		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
        	if (pag.getCampoOrd().equals("1")) {
            	queryString.append(" order by ");
            	queryString.append("sp.numeroExpediente");
            	queryString.append(" ").append(pag.getDirOrd());
        	}else if(pag.getCampoOrd().equals("2")) {
            	queryString.append(" order by ");
            	queryString.append("sp.folioSolicitud");
            	queryString.append(" ").append(pag.getDirOrd());
            }
        }
        return super.ejecutarQueryPaginado(queryString, pag);
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudPericial> consultarSolicitudesPericialPorNumeroExpediente(
			Long numeroExpedienteId) throws NSJPNegocioException {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT sp ")
					.append(" FROM SolicitudPericial sp ")
					.append(" WHERE sp.numeroExpediente.numeroExpedienteId = :numeroExpedienteId");
		Query query = super.getSession().createQuery(queryString.toString());
		query.setParameter("numeroExpedienteId", numeroExpedienteId);
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return ejecutarQueryPaginado(query, pag);
	}


}
