/**
 * Nombre del Programa 		: MandamientoPersonaDAOImpl.java
 * Autor 					: AlejandroGA
 * Compania 				: Ultrasist
 * Proyecto 				: NSJP 								Fecha: 05/06/2013
 * Marca de cambio 			: N/A
 * Descripcion General 		: Clase para
 * Programa Dependiente 	: N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion 		: N/A
 * Dias de ejecucion 		: N/A 								Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor 					: N/A
 * Compania 				: N/A
 * Proyecto 				: N/A 								Fecha: N/A
 * Modificacion 			: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.mandamiento.impl;


import java.util.Calendar;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.util.ConsecutivosUtil;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.mandamiento.MandamientoPersonaDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoPersonaDTO;
import mx.gob.segob.nsjp.model.MandamientoPersona;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * @author AlejandroGA
 * @version 1.0
 */
@Repository
public class MandamientoPersonaDAOImpl extends
		GenericDaoHibernateImpl<MandamientoPersona, Long> implements
		MandamientoPersonaDAO {

	public static final String SIGNO_MENOS = "-";

	@Override
	public Long obtenerUltimoFolioMandamientoPersona() {

		final StringBuffer query = new StringBuffer();

		query.append(" SELECT MAX ( CAST (");
		query.append(" SUBSTRING(mp.folioInterInstitucional,");
		query.append(" (LEN(mp.folioInterInstitucional) ").append(SIGNO_MENOS);
		query.append(
				ConsecutivosUtil.LONGITUD_CONSECUTIVO_MANDAMIENTO_PERSONA_MENOS_UNO)
				.append("),");
		query.append(ConsecutivosUtil.LONGITUD_CONSECUTIVO_MANDAMIENTO_PERSONA);
		query.append(" )  AS long ) )");
		query.append(" FROM MandamientoPersona mp ");
		query.append(" WHERE mp.folioInterInstitucional IS NOT NULL ");
		query.append(" AND mp.folioInterInstitucional like '");
		query.append(Instituciones.PJ.toString());
		query.append(ConsecutivosUtil.SEPARADOR_PREFIJOS);
		query.append(ConsecutivosUtil.PREFIJO_MANDAMIENTO_JUDICIAL);
		query.append(Calendar.getInstance().get(Calendar.YEAR));
		query.append(ConsecutivosUtil.SEPARADOR_CONSECUTIVO);
		query.append("%'");

		logger.debug("query :: " + query);
		Query hbq = super.getSession().createQuery(query.toString());
		return (Long) hbq.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MandamientoPersona> consultarMandamientosPersonaPorFiltro(
			FiltroMandamientoPersonaDTO filtroMandamientoPersonaDTO) {

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		Query query = null;
		final StringBuffer queryStr = new StringBuffer();

		if (pag != null && pag.getCampoOrd() != null
				&& pag.getCampoOrd().equals("probResponsable")) {
			queryStr.append(" SELECT mp FROM MandamientoPersona mp INNER JOIN mp.persona p INNER JOIN p.nombreDemograficos nd WHERE 1=1");
		} else {
			queryStr.append(" SELECT mp FROM MandamientoPersona mp WHERE 1=1 ");
		}

		if (filtroMandamientoPersonaDTO.getMandamientoPersonaId() != null
				&& filtroMandamientoPersonaDTO.getMandamientoPersonaId() > 0L) {
			queryStr.append(" AND mp.mandamientoPersonaId = ").append(
					filtroMandamientoPersonaDTO.getMandamientoPersonaId());
		}

		if (filtroMandamientoPersonaDTO.getEstatusId() != null
				&& filtroMandamientoPersonaDTO.getEstatusId() > 0L) {
			queryStr.append(" AND mp.estatus.valorId = ").append(
					filtroMandamientoPersonaDTO.getEstatusId());
		}

		if (filtroMandamientoPersonaDTO.getFechaEjecucion() != null) {
			queryStr.append(
					" AND CONVERT (varchar, mp.fechaEjecucion, 112) BETWEEN ")
					.append(DateUtils.formatearBD(filtroMandamientoPersonaDTO
							.getFechaEjecucion()))
					.append(" AND ")
					.append(DateUtils.formatearBD(filtroMandamientoPersonaDTO
							.getFechaEjecucion()));
		}

		if (filtroMandamientoPersonaDTO.getFolioInterInstitucional() != null
				&& !filtroMandamientoPersonaDTO.getFolioInterInstitucional()
						.trim().isEmpty()) {
			queryStr.append(" AND mp.folioInterInstitucional LIKE '")
					.append(filtroMandamientoPersonaDTO
							.getFolioInterInstitucional()).append("'");
		}

		if (filtroMandamientoPersonaDTO.getMandamientoId() != null
				&& filtroMandamientoPersonaDTO.getMandamientoId() > 0L) {
			queryStr.append(" AND mp.mandamiento.documentoId = ").append(
					filtroMandamientoPersonaDTO.getMandamientoId());
		}

		if (filtroMandamientoPersonaDTO.getPersonaId() != null
				&& filtroMandamientoPersonaDTO.getPersonaId() > 0L) {
			queryStr.append(" AND mp.persona.elementoId = ").append(
					filtroMandamientoPersonaDTO.getPersonaId());
		}
		
		if (filtroMandamientoPersonaDTO.getTipoMandamiento() != null
				&& !filtroMandamientoPersonaDTO.getTipoMandamiento().isEmpty()) {
			queryStr.append(" AND mp.mandamiento.tipoMandamiento.valorId IN (:listaTiposMandamiento) ");
		}
		
		if (filtroMandamientoPersonaDTO.getAudienciaId() != null
				&& filtroMandamientoPersonaDTO.getAudienciaId() > 0L) {
			queryStr.append(
					" AND mp.mandamiento.resolutivo.audiencia.audienciaId = ")
					.append(filtroMandamientoPersonaDTO.getAudienciaId());
		}

		logger.debug("pag :: " + pag);
		
		if (pag != null && pag.getCampoOrd() != null) {
			
			if (pag.getCampoOrd().equals("fechaResolutivo")) {
				queryStr.append(" ORDER BY ");
				queryStr.append("mp.mandamiento.resolutivo.temporizador");
				queryStr.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("fechaEjecucion")) {
				queryStr.append(" ORDER BY ");
				queryStr.append("mp.fechaEjecucion");
				queryStr.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("probResponsable")) {
				queryStr.append(" ORDER BY ");
				queryStr.append("nd.nombre");
				queryStr.append(" ").append(pag.getDirOrd());
			}
			
		}
		
		query = super.getSession().createQuery(queryStr.toString());
		
		if (filtroMandamientoPersonaDTO.getTipoMandamiento() != null
				&& !filtroMandamientoPersonaDTO.getTipoMandamiento().isEmpty()) {
			query.setParameterList("listaTiposMandamiento",
					filtroMandamientoPersonaDTO.getTipoMandamiento());
		}
		
		return super.ejecutarQueryPaginado(query, pag);
	}

}
