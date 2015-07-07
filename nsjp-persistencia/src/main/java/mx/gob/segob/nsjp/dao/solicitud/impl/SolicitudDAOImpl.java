/**
 * Nombre del Programa : SolicitudDAOImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 16 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementación del objeto de acceso a datos para la entidad Solicitud.
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

import java.util.Calendar;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.SolicitudAudiencia;
import mx.gob.segob.nsjp.model.Valor;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementación del objeto de acceso a datos para la entidad Solicitud.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Repository
public class SolicitudDAOImpl extends GenericDaoHibernateImpl<Solicitud, Long>
		implements SolicitudDAO {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudesPorExpediente(
			Long numeroExpedienteId) {
		final StringBuffer query = new StringBuffer();
		query.append("from Solicitud s");
		query.append(" where s.numeroExpediente.numeroExpedienteId = ");
		query.append(numeroExpedienteId);
		logger.debug("query :: " + query);
		Query hbq = super.getSession().createQuery(query.toString());

		return hbq.list();
	}

	@Override
	public Solicitud consultarSolicitudPorDocumentoId(long solicitudId) {
		final StringBuffer query = new StringBuffer();
		query.append("from Solicitud s");
		query.append(" where s.documentoId = ");
		query.append(solicitudId);
		logger.debug("query :: " + query);
		Query hbq = super.getSession().createQuery(query.toString());
		return (Solicitud) hbq.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudXEstatus(Long estatusSolicitud,
			Long tipoSolicitud) {
		final StringBuffer query = new StringBuffer();

		query.append(" FROM Solicitud s");
		query.append(" WHERE s.estatus = ");
		query.append(estatusSolicitud);
		if (tipoSolicitud != null) {
			query.append(" AND s.tipoSolicitud =");
			query.append(tipoSolicitud);
		}

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				query.append(" order by ");
				query.append("s.folioSolicitud");
				query.append(" ").append(pag.getDirOrd());
			}
		}
		logger.debug("query :: " + query);
		final Query queryStr = super.getSession().createQuery(query.toString());
		if (pag != null && pag.getPage() != null) {
			queryStr.setFirstResult(pag.getFirstResult());
			if (pag.getRows() != null & pag.getRows() > 0) {
				queryStr.setMaxResults(pag.getRows());
			} else {
				queryStr.setMaxResults(PaginacionDTO.DEFAULT_MAX_RESULT); // default
			}
		}

		final List<Solicitud> resp = queryStr.list();
		if (logger.isDebugEnabled()) {
			logger.debug("resp.size() :: " + resp.size());
		}
		if (pag != null && pag.getPage() != null) {
			queryStr.setFirstResult(0);
			queryStr.setMaxResults(-1);
			final List<Solicitud> temp = queryStr.list();
			logger.debug("temp.size() :: " + temp.size());
			
			pag.setTotalRegistros((long)temp.size());
			PaginacionThreadHolder.set(pag);
		}

		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudXEstatusYPuesto(
			Long estatusSolicitud, Long tipoSolicitud, Long puestoId) {

		final StringBuffer query = new StringBuffer();

		query.append(" FROM Solicitud s");
		query.append(" WHERE s.estatus = ");
		query.append(estatusSolicitud);
		if (tipoSolicitud != null) {
			query.append(" AND s.tipoSolicitud =");
			query.append(tipoSolicitud);
		}
		query.append(" AND s.destinatario.puesto.valorId = " + puestoId);

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				query.append(" order by ");
				query.append("s.folioSolicitud");
				query.append(" ").append(pag.getDirOrd());
			}
		}
		logger.debug("query :: " + query);
		final Query queryStr = super.getSession().createQuery(query.toString());
		if (pag != null && pag.getPage() != null) {
			queryStr.setFirstResult(pag.getFirstResult());
			if (pag.getRows() != null & pag.getRows() > 0) {
				queryStr.setMaxResults(pag.getRows());
			} else {
				queryStr.setMaxResults(PaginacionDTO.DEFAULT_MAX_RESULT); // default
			}
		}

		final List<Solicitud> resp = queryStr.list();
		if (logger.isDebugEnabled()) {
			logger.debug("resp.size() :: " + resp.size());
		}
		if (pag != null && pag.getPage() != null) {
			queryStr.setFirstResult(0);
			queryStr.setMaxResults(-1);
			final List<Solicitud> temp = queryStr.list();
			logger.debug("temp.size() :: " + temp.size());
			pag.setTotalRegistros((long)temp.size());
			PaginacionThreadHolder.set(pag);
		}

		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudAudiencia> consultarSolicitudAudienciaXEstatus(
			Long estatusSolicitud, Long tipoSolicitud) {
		final StringBuffer query = new StringBuffer();

		query.append(" FROM SolicitudAudiencia s");
		query.append(" WHERE s.estatus = ");
		query.append(estatusSolicitud);
		if (tipoSolicitud != null) {
			query.append(" AND s.tipoSolicitud =");
			query.append(tipoSolicitud);
		}

		logger.debug("query :: " + query);
		Query hbq = super.getSession().createQuery(query.toString());

		return hbq.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudPorFiltro(
			Solicitud solicitudFiltro, Boolean esAsociadaNumeroExpediente,
			Long distritoId, AvisoDesignacion avisoDesignacionFiltro) {
		ConfInstitucion institucion = this.consultarInsitucionActual();
		final StringBuffer query = new StringBuffer();
		
		query.append(" FROM Solicitud s");
		query.append(" WHERE 1=1 ");

		if (solicitudFiltro != null) {
			if (solicitudFiltro.getNumeroExpediente() != null) {
				if (solicitudFiltro.getNumeroExpediente().getNumeroExpedienteId() != null &&
						solicitudFiltro.getNumeroExpediente().getNumeroExpedienteId()> 0) {
					query.append(" AND s.numeroExpediente.numeroExpedienteId = ")
							.append(solicitudFiltro.getNumeroExpediente()
									.getNumeroExpedienteId());
				}
				if (solicitudFiltro.getNumeroExpediente().getNumeroExpediente() != null
						&& !solicitudFiltro.getNumeroExpediente()
								.getNumeroExpediente().trim().isEmpty()) {
					query.append(" AND s.numeroExpediente.numeroExpediente = '")
							.append(solicitudFiltro.getNumeroExpediente()
									.getNumeroExpediente()).append("' ");
				}
			}
			
			//Permite filtrar las solicitudes asociadas a un expediente
			if (solicitudFiltro.getNumeroExpediente() != null && solicitudFiltro.getNumeroExpediente().getExpediente() != null){
					
					if(solicitudFiltro.getNumeroExpediente().getExpediente().getExpedienteId() != null 
					&& solicitudFiltro.getNumeroExpediente().getExpediente().getExpedienteId() > 0L) {
						query.append(" AND s.numeroExpediente.expediente.expedienteId = ")
							.append(solicitudFiltro.getNumeroExpediente().getExpediente().getExpedienteId()).append(" ");
					}
			}
			// Filtro de distrito solo para defensoria
			if (distritoId != null && distritoId > 0L
					&& institucion.getConfInstitucionId().equals(Instituciones.DEF.getValorId())) {

				// Solicitudes de defensor para defensoria
				if (solicitudFiltro.getTipoSolicitud().getValorId().equals(
						TiposSolicitudes.DEFENSOR.getValorId())) {
					// Se requiere hacer una subconsulta de todos casos
					// asociados al distrito a traves del expediente, 
					// ya que no todas las solicitudes tienen un numero de expediente
					query.append(
							" AND s.numeroCasoAsociado in (SELECT c.numeroGeneralCaso FROM Caso c, Expediente e WHERE e.caso.casoId = c.casoId AND  e.discriminante.distrito.catDistritoId = ")
							.append(distritoId).append(")");
				} 
				//Para la solicitud de Asesoria, no tiene caso asociado, pero si numero de expediente
				else if (solicitudFiltro.getTipoSolicitud().getValorId().equals(
						TiposSolicitudes.ASESORIA_DEFENSORIA.getValorId())) {
					query.append(
							" AND s.numeroExpediente.expediente.discriminante.distrito.catDistritoId = ")
							.append(distritoId);
				}
			}
			
	
			if (solicitudFiltro.getTipoSolicitud() != null
					&& solicitudFiltro.getTipoSolicitud().getValorId() != null
					&& solicitudFiltro.getTipoSolicitud().getValorId() > 0) {
				query.append(" AND s.tipoSolicitud.valorId = ").append(
						solicitudFiltro.getTipoSolicitud().getValorId());
			}
	
			if (solicitudFiltro.getFuncionarioSolicitante() != null
					&& solicitudFiltro.getFuncionarioSolicitante()
							.getClaveFuncionario() != null
					&& solicitudFiltro.getFuncionarioSolicitante()
							.getClaveFuncionario() > 0) {
				query.append(" AND s.funcionarioSolicitante.claveFuncionario = ")
						.append(solicitudFiltro.getFuncionarioSolicitante()
								.getClaveFuncionario());
			}
	
			if (solicitudFiltro.getAreaOrigen() != null
					&& solicitudFiltro.getAreaOrigen() > 0) {
				query.append(" AND s.areaOrigen = ").append(
						solicitudFiltro.getAreaOrigen());
			}
			if (solicitudFiltro.getAreaDestino() != null
					&& solicitudFiltro.getAreaDestino() > 0) {
				query.append(" AND s.areaDestino = ").append(
						solicitudFiltro.getAreaDestino());
			}
			if (solicitudFiltro.getEstatus() != null
					&& solicitudFiltro.getEstatus().getValorId() != null
					&& solicitudFiltro.getEstatus().getValorId() > 0) {
				query.append(" AND s.estatus.valorId = ").append(
						solicitudFiltro.getEstatus().getValorId());
			}
			if (solicitudFiltro.getNombreSolicitante() != null
					&& !solicitudFiltro.getNombreSolicitante().trim().isEmpty()) {
				query.append(" AND s.nombreSolicitante = '")
						.append(solicitudFiltro.getNombreSolicitante())
						.append("' ");
			}
			if (solicitudFiltro.getNumeroCasoAsociado() != null
					&& !solicitudFiltro.getNumeroCasoAsociado().trim().isEmpty()) {
				query.append(" AND s.numeroCasoAsociado = '")
						.append(solicitudFiltro.getNumeroCasoAsociado())
						.append("' ");
			}
			if (solicitudFiltro.getFolioSolicitud() != null
					&& !solicitudFiltro.getFolioSolicitud().trim().isEmpty()) {
				query.append(" AND s.folioSolicitud = '")
						.append(solicitudFiltro.getFolioSolicitud()).append("' ");
			}
			if (solicitudFiltro.getNumeroExpedienteAsociado() != null
					&& !solicitudFiltro.getNumeroExpedienteAsociado().trim()
							.isEmpty()) {
				query.append(" AND s.numeroExpedienteAsociado = '")
						.append(solicitudFiltro.getNumeroExpedienteAsociado())
						.append("' ");
			}
			if (solicitudFiltro.getConfInstitucion() != null
					&& solicitudFiltro.getConfInstitucion()
							.getConfInstitucionId() != null
					&& solicitudFiltro.getConfInstitucion()
							.getConfInstitucionId() > 0L) {
				query.append(" AND s.confInstitucion.confInstitucionId = '")
						.append(solicitudFiltro.getConfInstitucion()
								.getConfInstitucionId()).append("' ");
			}
			if (solicitudFiltro.getClaveDiscriminanteOrigen() != null
					&& !solicitudFiltro.getClaveDiscriminanteOrigen().trim()
							.isEmpty()) {
				query.append(" AND s.claveDiscriminanteOrigen = '")
						.append(solicitudFiltro.getClaveDiscriminanteOrigen())
						.append("' ");
			}
			if (esAsociadaNumeroExpediente != null
					&& esAsociadaNumeroExpediente.equals(true)) {
				query.append(" AND s.numeroExpediente IS NOT NULL");
			}
			if (esAsociadaNumeroExpediente != null
					&& esAsociadaNumeroExpediente.equals(false)) {
				query.append(" AND s.numeroExpediente IS NULL");
			}
			
			//Solo para Solicitudes de Defensor o Solicitudes de Asesoria Legal
			if (avisoDesignacionFiltro != null
					&& (solicitudFiltro.getTipoSolicitud().getValorId().equals(
							TiposSolicitudes.ASESORIA_DEFENSORIA.getValorId()) || solicitudFiltro
							.getTipoSolicitud().getValorId().equals(
									TiposSolicitudes.DEFENSOR.getValorId()))) {

				if (avisoDesignacionFiltro.getEstatus() != null
						&& avisoDesignacionFiltro.getEstatus().getValorId() != null
						&& avisoDesignacionFiltro.getEstatus().getValorId() > 0L) {
					query.append(" AND s.avisoDesignacion.estatus = "
							+ avisoDesignacionFiltro.getEstatus().getValorId());
				}
				
				if (avisoDesignacionFiltro.getFuncionario() != null
						&& avisoDesignacionFiltro.getFuncionario()
								.getClaveFuncionario() != null
						&& avisoDesignacionFiltro.getFuncionario()
								.getClaveFuncionario() > 0L) {
					query.append(" AND s.avisoDesignacion.funcionario.claveFuncionario = "
							+ avisoDesignacionFiltro.getFuncionario()
									.getClaveFuncionario());
				}
			}
			
			query.append(" ORDER BY s.fechaCreacion desc");
		}
		logger.debug("query :: " + query);
		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        return super.ejecutarQueryPaginado(query, pag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudesPorNumeroExpedienteYTipo(
			Long numeroExpedienteId, TiposSolicitudes tipo) {
		final StringBuffer query = new StringBuffer();

		query.append("from Solicitud s");
		query.append(" where s.numeroExpediente.numeroExpedienteId = ? and ");
		query.append(" s.tipoSolicitud.valorId = ?");
		return getHibernateTemplate().find(query.toString(),
				numeroExpedienteId, tipo.getValorId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudesPorExpedienteYTipo(Long idExpediente,
			TiposSolicitudes carpetaInvestigacion) {
		final StringBuffer query = new StringBuffer();
		query.append("from Solicitud s");
		query.append(" where s.numeroExpediente.numeroExpedienteId = ");
		query.append(idExpediente);
		query.append(" and s.tipoSolicitud.valorId = ")
		.append(carpetaInvestigacion.getValorId());
		logger.debug("query :: " + query);
		Query hbq = super.getSession().createQuery(query.toString());
		return hbq.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO#
	 * consultarNumeroExpedienteDeSolicitud(java.lang.Long)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Long consultarNumeroExpedienteDeSolicitud(Long solicitudId) {
		List resultado = getHibernateTemplate().find(
				"select sol.numeroExpediente.numeroExpedienteId from Solicitud sol where "
						+ "sol.documentoId = ?", solicitudId);
		if (resultado != null && resultado.size() > 0) {
			return (Long) (resultado.get(0));
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	public Solicitud consultarSolicitudPorFolio(String folioSolicitud) {

		List resultado = getHibernateTemplate().find(
				"select sol from Solicitud sol where "
						+ "sol.folioSolicitud = ?", folioSolicitud);
		return (resultado != null && !resultado.isEmpty()) ? (Solicitud) (resultado
				.get(0)) : new Solicitud();
	}

	@SuppressWarnings("rawtypes")
	public Expediente consultarExpedienteDeNumeroExpedienteSolicitud(
			Long solicitudId) {
		List resultado = getHibernateTemplate().find(
				"select sol.numeroExpediente.expediente from Solicitud sol where "
						+ "sol.documentoId = ?", solicitudId);
		return (resultado != null && !resultado.isEmpty()) ? (Expediente) (resultado
				.get(0)) : new Expediente();
	}

	@Override
	public NumeroExpediente consultarNumeroExpedienteSolicitud(Long solicitudId) {
		List resultado = getHibernateTemplate().find(
				"select sol.numeroExpediente from Solicitud sol where "
						+ "sol.documentoId = ?", solicitudId);
		return (resultado != null && !resultado.isEmpty()) ? (NumeroExpediente) (resultado
				.get(0)) : new NumeroExpediente();
	}

    public String obtenerUltimoFolioSolicitud() throws NSJPNegocioException {
        final StringBuffer query = new StringBuffer();
        query.append("SELECT MAX(s.folioSolicitud) ");
        query.append("FROM Solicitud s ");
        query.append("WHERE s.folioSolicitud like '%");
        query.append(super.consultarInsitucionActual().getMonograma());
        query.append("%");
        query.append(Calendar.getInstance().get(Calendar.YEAR));
        query.append("%'");
        logger.debug("query :: " + query);
        Query hbq = super.getSession().createQuery(query.toString());
        return (String) hbq.uniqueResult();
    }

	@Override
	public Solicitud obtenerSolicitudPorFolio(String folio)
			throws NSJPNegocioException {
		final StringBuffer query = new StringBuffer();
		query.append("FROM Solicitud s ");
		query.append("WHERE s.folioSolicitud like '%" + folio + "%'");
		logger.debug("query :: " + query);
		Query hbq = super.getSession().createQuery(query.toString());
		return (Solicitud) hbq.uniqueResult();
	}

	@Override
	public Solicitud consultarSolicitudPorId(Long idSolicitud)
			throws NSJPNegocioException {
		final StringBuffer query = new StringBuffer();
		query.append("FROM Solicitud s ");
		query.append("WHERE s.documentoId = " + idSolicitud);
		logger.debug("query :: " + query);
		Query hbq = super.getSession().createQuery(query.toString());
		return (Solicitud) hbq.uniqueResult();
	}

	@Override
	public List<Solicitud> consultarSolicitudesGeneradas(
			List<Long> idEstatusSolicitud, List<Long> idTipoSolicitud,
			Long idAreaOrigen, Long idFuncionarioSolicitante) {
		return consultarSolicitudesGeneradasPorNumeroExpediente(
				idEstatusSolicitud, idTipoSolicitud, idAreaOrigen,
				idFuncionarioSolicitante, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudesGeneradasPorNumeroExpediente(
			List<Long> idEstatusSolicitud, List<Long> idTipoSolicitud,
			Long idAreaOrigen, Long idFuncionarioSolicitante,
			String numeroExpediente) {
		String cadenaIdES = "";
		String cadenaIdTS = "";

		if (idEstatusSolicitud != null && !idEstatusSolicitud.isEmpty())
			cadenaIdES = idEstatusSolicitud.toString().substring(1,
					idEstatusSolicitud.toString().length() - 1);

		if (idTipoSolicitud != null && !idTipoSolicitud.isEmpty())
			cadenaIdTS = idTipoSolicitud.toString().substring(1,
					idTipoSolicitud.toString().length() - 1);

		logger.info("cadenaIdES:" + cadenaIdES);

		final StringBuffer query = new StringBuffer();

		query.append(" FROM Solicitud s");
		query.append(" WHERE 1=1 ");

		if (idFuncionarioSolicitante != null && idFuncionarioSolicitante > 0) {
			query.append(" AND s.funcionarioSolicitante.claveFuncionario = ");
			query.append(idFuncionarioSolicitante);
		}

		if (!cadenaIdES.trim().isEmpty()) {
			query.append(" AND s.estatus in ( ");
			query.append(cadenaIdES);
			query.append(" ) ");
		}

		if (!cadenaIdTS.trim().isEmpty()) {
			query.append(" AND s.tipoSolicitud in (");
			query.append(cadenaIdTS);
			query.append(" ) ");
		}

		if (idAreaOrigen != null && idAreaOrigen > 0) {
			query.append(" AND s.areaOrigen = ");
			query.append(idAreaOrigen);
		}
		if (numeroExpediente != null && !numeroExpediente.trim().isEmpty()) {
			query.append(" AND s.numeroExpediente.numeroExpediente like '%");
			query.append(numeroExpediente);
			query.append("%' ");
		}

		query.append(" order by s.fechaCreacion asc");

		logger.debug("query :: " + query);
//		Query hbq = super.getSession().createQuery(query.toString());
//		return hbq.list();
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        return super.ejecutarQueryPaginado(query, pag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudesParaAtender(
			List<Long> idEstatusSolicitud, List<Long> idTipoSolicitud,
			Long idAreaDestino, Long idFuncionarioDestinatario, Long idCatDiscriminante) {
		String cadenaIdES = "";
		String cadenaIdTS = "";

		if (idEstatusSolicitud != null && !idEstatusSolicitud.isEmpty())
			cadenaIdES = idEstatusSolicitud.toString().substring(1,
					idEstatusSolicitud.toString().length() - 1);

		if (idTipoSolicitud != null && !idTipoSolicitud.isEmpty())
			cadenaIdTS = idTipoSolicitud.toString().substring(1,
					idTipoSolicitud.toString().length() - 1);

		logger.info("cadenaIdES:" + cadenaIdES);
		logger.info("cadenaIdTS:" + cadenaIdTS);

		final StringBuffer query = new StringBuffer();

		query.append(" FROM Solicitud s");
		query.append(" WHERE 1=1 ");

		if (idFuncionarioDestinatario != null) {
			query.append(" AND s.destinatario.claveFuncionario = ");
			query.append(idFuncionarioDestinatario);
		}

		if (!cadenaIdES.trim().isEmpty()) {
			query.append(" AND s.estatus in ( ");
			query.append(cadenaIdES);
			query.append(" ) ");
		}

		if (!cadenaIdTS.trim().isEmpty()) {
			query.append(" AND s.tipoSolicitud in (");
			query.append(cadenaIdTS);
			query.append(" ) ");
		}

		if (idAreaDestino != null && idAreaDestino > 0) {
			query.append(" AND s.areaDestino = ");
			query.append(idAreaDestino);
		}
			
	

		logger.debug("query :: " + query);
		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        
        if(pag != null && pag.getSearchField() != null ){
        	if(pag.getSearchField().equals("expediente")){
				pag.setSearchField("numeroExpediente");
				if(pag.getSearchOper().equals("nu")){
				    query.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag,"s",false,false));
		    	}else{
				    query.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag,"s.numeroExpediente",false,false));
		    	}
			}else if(pag.getSearchField().equals("caso")){
			    pag.setSearchField("numeroCasoAsociado");
			    query.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag,"s",false,false));
        	}else if(pag.getSearchField().equals("folio")){
			    pag.setSearchField("folioSolicitud");
			    query.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag,"s",false,false));
			}else if(pag.getSearchField().equals("estatus")){
				if(pag.getSearchOper().equals("nu")){
				    pag.setSearchField("estatus");
				    query.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag,"s",false,false));
		    	}else{
				    pag.setSearchField("valor");
				    query.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag,"s.estatus",false,false));
		    	}
			}else if(pag.getSearchField().equals("fechaCreacion")){
			    pag.setSearchField("fechaCreacion");
			    query.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag,"s",false,true));
			}else if(pag.getSearchField().equals("fechaLimite")){
			    pag.setSearchField("fechaLimite");
			    query.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag,"s",false,true));
			}
		}
        
    	query.append(" order by s.fechaCreacion asc");
        
        return super.ejecutarQueryPaginado(query, pag);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Solicitud> consultarSolicitudesPorTipoYEstatus(Long tipo,
			Long estatus, Long claveFuncionario,Long discriminanteId) {
		StringBuffer queryString = new StringBuffer();

		queryString.append("FROM Solicitud s")
				.append(" WHERE s.tipoSolicitud="+tipo);
		if (claveFuncionario != null)
			queryString.append(" AND s.funcionarioSolicitante="+claveFuncionario);
		queryString.append(" AND s.estatus="+estatus);
		
		if(discriminanteId != null){
			queryString.append(" AND s.numeroExpediente.expediente.discriminante.catDiscriminanteId=").append(discriminanteId);
		}

		Query query = super.getSession().createQuery(queryString.toString());
		logger.debug("query :: " + queryString);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudesPorTipoYNoEstatus(
			Long tipoSolicitud, Long estatusSolicitud, Long claveFuncionario)
			throws NSJPNegocioException {
		StringBuffer queryString = new StringBuffer();

		queryString.append("FROM Solicitud s")
				.append(" WHERE s.tipoSolicitud=").append(tipoSolicitud)
				.append(" AND s.funcionarioSolicitante=")
				.append(claveFuncionario).append(" AND s.estatus <> ")
				.append(estatusSolicitud);

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		logger.debug("pag :: " + pag);
		return super.ejecutarQueryPaginado(queryString, pag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO#consultarSolicitudesPorFolio
	 * (java.lang.String)
	 */
	@Override
	public List<Solicitud> consultarSolicitudesPorFolio(String folioSolicitud) {
		return getHibernateTemplate().find(
				"from Solicitud s where s.folioSolicitud = ?", folioSolicitud);
	}

	public String obtenerFolioDeSolicitud(Long idSolicitud)
			throws NSJPNegocioException {
		final StringBuffer query = new StringBuffer();
		query.append("SELECT s.folioSolicitud ");
		query.append("FROM Solicitud s ");
		query.append("WHERE s.confInstitucion.esInstalacionActual = true ");
		query.append("AND s.documentoId = ").append(idSolicitud);
		logger.debug("query :: " + query);
		Query hbq = super.getSession().createQuery(query.toString());
		return (String) hbq.uniqueResult();
	}

	/**
	 * M&acutee;todo que crea una solicitud con un DocumentoId ya existente
	 * @param Solicitud: Datos de la solicitud
	 * @return idSolicitud: Identificador de la solicitud
	 * @throws NSJPNegocioException
	 */
	public Long crearSolicitudConDocumentoExistente(Solicitud solicitud) throws NSJPNegocioException {
		
		final Funcionario solicitante = solicitud.getFuncionarioSolicitante() != null ? solicitud.getFuncionarioSolicitante() : new Funcionario();
		final Funcionario destinatario = solicitud.getDestinatario() != null ? solicitud.getDestinatario() : new Funcionario();
		final NumeroExpediente numeroExpediente = solicitud.getNumeroExpediente() != null ? solicitud.getNumeroExpediente() : new NumeroExpediente();
		final Valor tipoSolicitud = solicitud.getTipoSolicitud() != null ? solicitud.getTipoSolicitud() : new Valor();
		final Valor estatus = solicitud.getEstatus() != null ? solicitud.getEstatus() : new Valor();
		final Involucrado involucradoSolicitante = solicitud.getInvolucradoSolicitante() != null ? solicitud.getInvolucradoSolicitante() : new Involucrado();
		final FuncionarioExterno funDestinatarioExterno = solicitud.getFuncionarioDestExt() != null ? 
				solicitud.getFuncionarioDestExt() : new FuncionarioExterno();
		final FuncionarioExterno funSolicitanteExterno = solicitud.getFuncionarioSolExt() != null ? 
				solicitud.getFuncionarioSolExt() : new FuncionarioExterno();
		final StringBuffer query = new StringBuffer();
		
		query.append(" INSERT INTO Solicitud ");
		query.append(" (TipoSolicitud_val, Solicitud_id, NumeroExpediente_id, ");
		query.append(" cNumeroCasoAsociado, cMotivo, Estatus_val, dFechaLimite, ");
		query.append(" dFechaModificacion, dFechaCierre, iFuncionarioSolicitante, ");
		query.append(" iSolicitanteExterno, cNombreSolicitante, bEsUrgente, ");
		query.append(" InvolucradoSolicitante_id, iFuncionarioDestinatario, ");
		query.append(" cFolioSolicitud, cAsuntoSolicitud, cObservaciones, ");
		query.append(" iAreaOrigen, iAreaDestino, iFuncionarioSolExt, iFuncionarioDestExt) ");
		query.append(" values ( ");
		query.append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ");
		query.append(" ) ");
		
		
		logger.debug("query :: " + query);
		Query hbq = super.getSession().createSQLQuery(query.toString());
		
		hbq.setLong(0,  tipoSolicitud.getValorId());
		hbq.setLong(1,  solicitud.getDocumentoId());
		hbq.setLong(2,  numeroExpediente.getNumeroExpedienteId());
		hbq.setString(3,  solicitud.getNumeroCasoAsociado());
		hbq.setText(4,  solicitud.getMotivo());
		hbq.setLong(5,  estatus.getValorId());
		hbq.setDate(6,  solicitud.getFechaLimite());
		hbq.setDate(7,  solicitud.getFechaModificacion());
		hbq.setDate(8,  solicitud.getFechaCierre());
		hbq.setLong(9,  solicitante.getClaveFuncionario());
		hbq.setParameter(10,  solicitud.getSolicitanteExterno());
		hbq.setString(11, solicitud.getNombreSolicitante());
		hbq.setBoolean(12, solicitud.getEsUrgente());
		hbq.setParameter(13, involucradoSolicitante.getElementoId());
		hbq.setParameter(14, destinatario.getClaveFuncionario());
		hbq.setString(15, solicitud.getFolioSolicitud());
		hbq.setString(16, solicitud.getAsuntoSolicitud());
		hbq.setText(17, solicitud.getObservaciones());
		hbq.setParameter(18, solicitud.getAreaOrigen());
		hbq.setParameter(19, solicitud.getAreaDestino());
		hbq.setParameter(20, funSolicitanteExterno.getFuncionarioExternoId());
		hbq.setParameter(21, funDestinatarioExterno.getFuncionarioExternoId());
		
		hbq.executeUpdate();
		
		return solicitud.getDocumentoId();
	}	
		

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudesConCriterios (
			Solicitud solicitud,
			List<Long> lstIdEstatusSolicitud, List<Long> lstIdTipoSolicitud,
			String tipoConsulta
			) throws NSJPNegocioException {

		String opcionConsulta = tipoConsulta != null ? tipoConsulta : Solicitud.TODAS;
		String cadenaIdES = "";
		String cadenaIdTS = "";

		if (lstIdEstatusSolicitud != null && !lstIdEstatusSolicitud.isEmpty())
			cadenaIdES = lstIdEstatusSolicitud.toString().substring(1,
					lstIdEstatusSolicitud.toString().length() - 1);

		if (lstIdTipoSolicitud != null && !lstIdTipoSolicitud.isEmpty())
			cadenaIdTS = lstIdTipoSolicitud.toString().substring(1,
					lstIdTipoSolicitud.toString().length() - 1);
		
		final StringBuffer query = new StringBuffer();

		query.append(" FROM Solicitud s");
		query.append(" WHERE 1=1 ");

		if (!lstIdEstatusSolicitud.isEmpty()) {
			query.append(" AND s.estatus in ( " );
			query.append(cadenaIdES);
			query.append(" ) ");
		}

		if (!lstIdTipoSolicitud.isEmpty()) {
			query.append(" AND s.tipoSolicitud in ( " );
			query.append(cadenaIdTS);
			query.append(" ) ");
		}
		if (solicitud != null){
			
			if (solicitud.getAreaOrigen() != null && solicitud.getAreaOrigen() > 0) {
				query.append(" AND s.areaOrigen = ");
				query.append(solicitud.getAreaOrigen());
			}
			
			NumeroExpediente numeroExpediente = solicitud.getNumeroExpediente();
			if ( numeroExpediente != null){
				if (numeroExpediente.getNumeroExpediente() != null && !numeroExpediente.getNumeroExpediente().equals("") ) {
					query.append(" AND s.numeroExpediente.numeroExpediente like '%");
					query.append(numeroExpediente.getNumeroExpediente());
					query.append("%' ");
				}if (numeroExpediente.getNumeroExpedienteId() != null 
						&& numeroExpediente.getNumeroExpedienteId() > 0L){
					query.append(" AND s.numeroExpediente.numeroExpedienteId = ");
					query.append(numeroExpediente.getNumeroExpedienteId());
					query.append(" ");
				}
			}
			
			if(opcionConsulta.equals(Solicitud.GENERADAS)) {
				Funcionario solicitante = solicitud.getFuncionarioSolicitante();
				if (solicitante != null) {
					query.append(" AND s.funcionarioSolicitante.claveFuncionario = ");
					query.append(solicitante.getClaveFuncionario());
				}
			} else if (opcionConsulta.equals(Solicitud.POR_ATENDER)){
				Funcionario destinatario = solicitud.getDestinatario();
				if (destinatario != null) {
					query.append(" AND s.destinatario.claveFuncionario = ");
					query.append(destinatario.getClaveFuncionario());
				}				
			} else if (opcionConsulta.equals(Solicitud.NUMEROCASOASOCIADO)){
					
				if (solicitud.getNumeroExpediente() != null){
					if(solicitud.getNumeroExpediente().getExpediente() != null){
						if(solicitud.getNumeroExpediente().getExpediente().getCaso() != null){
							query.append(" AND s.numeroExpediente.expediente.caso.numeroGeneralCaso = ");
							query.append("'" + solicitud.getNumeroExpediente().getExpediente().getCaso()+"'");			
						}
					}
				}							
			}
		}

		query.append(" order by s.fechaCreacion asc");

		logger.debug("query :: " + query);
//		Query hbq = super.getSession().createQuery(query.toString());
//		return hbq.list();
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        return super.ejecutarQueryPaginado(query, pag);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO#consultarSolicitudesPorExpedienteTiposDocumentoYEstatus(mx.gob.segob.nsjp.model.Expediente, java.util.List, mx.gob.segob.nsjp.model.Valor)
	 */	
	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudesPorExpedienteTiposDocumentoYEstatus(
			Expediente expediente, List<Valor> tiposDocumento,
			Valor estatusSolicitud) throws NSJPNegocioException {

		if (expediente == null
				||expediente.getExpedienteId() == null
				|| expediente.getExpedienteId() < 1L
				|| tiposDocumento == null
				|| tiposDocumento.isEmpty()){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		StringBuffer queryString = new StringBuffer("");
		queryString.append(" SELECT s ");
		queryString.append(" FROM Solicitud s ");
		queryString.append(" LEFT JOIN s.actividad a ");
		queryString.append(" WHERE a.expediente.expedienteId = ");
		queryString.append(expediente.getExpedienteId());
		queryString.append(" AND s.tipoDocumento.valorId IN ( ");
		for (int i=0; i<tiposDocumento.size(); i++){
			if (i > 0){
				queryString.append(", ");
			}
			queryString.append(tiposDocumento.get(i).getValorId());
		}
		queryString.append(" ) ");
		
		if (estatusSolicitud != null 
				&& estatusSolicitud.getValorId() != null
				&& estatusSolicitud.getValorId() > 0L){
			queryString.append(" AND s.estatus.valorId = ");
			queryString.append(estatusSolicitud.getValorId());
		}
		
		return ejecutarQueryPaginado(queryString, PaginacionThreadHolder.get());
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> consultarSolicitudesPorTipoYNumeroExps(Long idCaso, List<Long> idTipoSolicitud) {
		String cadenaIdTS = "";

		if (idTipoSolicitud != null && !idTipoSolicitud.isEmpty())
			cadenaIdTS = idTipoSolicitud.toString().substring(1,
					idTipoSolicitud.toString().length() - 1);

		final StringBuffer queryString = new StringBuffer();

		queryString.append(" FROM Solicitud s");
		queryString.append(" WHERE 1=1 ");

		if (idCaso != null && idCaso > 0) {
			queryString.append(" AND s.numeroExpediente.expediente.caso.casoId = ");
			queryString.append(idCaso);
		}

		if (!cadenaIdTS.trim().isEmpty()) {
			queryString.append(" AND s.tipoSolicitud in (");
			queryString.append(cadenaIdTS);
			queryString.append(" ) ");
		}

		 logger.info("Query:"+ queryString);

        final PaginacionDTO pag = PaginacionThreadHolder.get();

		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				queryString.append(" order by ");
				queryString.append("s.numeroExpediente.fechaApertura");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("2")) {
				queryString.append(" order by ");
				queryString.append("s.numeroExpediente.numeroExpediente");				
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("3")) {
				queryString.append(" order by ");
				queryString.append("s.numeroExpediente.funcionario.nombreFuncionario");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("4")) {
				queryString.append(" order by ");
				queryString.append("s.numeroExpediente.estatus.valor");
				queryString.append(" ").append(pag.getDirOrd());
			}
				if (pag.getCampoOrd().equals("5")) {
				queryString.append(" order by ");
				queryString.append("s.tipoSolicitud.valor");
				queryString.append(" ").append(pag.getDirOrd());
			}
		}
		return super.ejecutarQueryPaginado(queryString, pag);   
	}

}
