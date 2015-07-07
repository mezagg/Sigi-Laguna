/**
 * Nombre del Programa : MandamientoDAOImpl.java
 * Autor                            : Emigdio
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 23/08/2011
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
package mx.gob.segob.nsjp.dao.documento.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.ConsecutivosUtil;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.documento.MandamientoDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoDTO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Resolutivo;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementación del DAO para el acceso a los mandamientos
 * 
 * @version 1.0
 * @author Emigdio
 * 
 */
@Repository
public class MandamientoDAOImpl
        extends
            GenericDaoHibernateImpl<Mandamiento, Long>
        implements
            MandamientoDAO {

	public static final String SIGNO_MENOS = "-";
	
	private Long[] grupoEstatusBusqueda = {
			EstatusMandamiento.NO_ATENDIDO.getValorId(),
			EstatusMandamiento.EN_PROCESO.getValorId(),
			EstatusMandamiento.ATENDIDO.getValorId() };
	private List<Long> listGrupoEstatusBusqueda = Arrays
			.asList(grupoEstatusBusqueda);

    /*
     * (non-Javadoc)
     * 
     * @see mx.gob.segob.nsjp.dao.documento.MandamientoDAO#
     * consultarMandamientosPorNumeroExpediente(java.lang.String)
     */
	@SuppressWarnings("unchecked")
    @Override
    public List<Mandamiento> consultarMandamientosPorNumeroExpediente(
            String numeroExpediente,Long discriminanteId) {
		
		logger.info("Numero Expediente: " + numeroExpediente);
		
		 return getHibernateTemplate()
	                .find("from Mandamiento m where"
	                        + " m.resolutivo.audiencia.numeroExpediente.numeroExpediente = ? "
	                        + " and m.resolutivo.audiencia.numeroExpediente.expediente.discriminante.catDiscriminanteId = ? "
	                        + " order by m.resolutivo.audiencia.fechaAudiencia desc",
	                        numeroExpediente,discriminanteId);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<NumeroExpediente> consultarMandamientosJudicialesPorFiltro(
			MandamientoDTO mandamiento){
		
		final StringBuffer queryString = new StringBuffer();
		
		queryString.append("SELECT DISTINCT ne FROM Mandamiento ma LEFT JOIN ma.medida m LEFT JOIN m.numeroExpediente ne INNER JOIN ne.expediente e WHERE 1=1 ");
				
		if(mandamiento==null){
			Date hoy  = new Date();
			Calendar resta = Calendar.getInstance();
			// -3 -> meses a restar en la consulta. Dícese, de hace 3 meses a la fecha
			resta.add(Calendar.MONTH, -3);
			queryString.append(" AND CONVERT (varchar, e.fechaCreacion, 112) BETWEEN ").append(DateUtils.formatearBD(resta.getTime()))
						.append(" AND ").append(DateUtils.formatearBD(hoy));
		}
		else{
			if(mandamiento.getExpedienteDTO()!=null &&
			   mandamiento.getExpedienteDTO().getNumeroExpediente()!=null){
				queryString.append(" AND ne.numeroExpediente='"+mandamiento.getExpedienteDTO().getNumeroExpediente()+"'");
			}
			/*
			if(mandamiento.getFechaInicial()!=null && mandamiento.getFechaFinal()!=null){
				queryString.append(" AND CONVERT (varchar, e.fechaCreacion, 112) BETWEEN ")
						   .append(DateUtils.formatearBD(mandamiento.getFechaInicial()))
				           .append(" AND ").append(DateUtils.formatearBD(mandamiento.getFechaFinal()));				
			}*/
		}
		
		if(mandamiento.getUsuario()!=null &&
		   mandamiento.getUsuario().getFuncionario()!=null){
			
			if(mandamiento.getUsuario().getFuncionario().getClaveFuncionario()!=null){
				queryString.append(" AND ne.funcionario.claveFuncionario = " + 
						mandamiento.getUsuario().getFuncionario().getClaveFuncionario());
			}
			if(mandamiento.getUsuario().getFuncionario().getJerarquiaOrganizacional()!=null &&
					mandamiento.getUsuario().getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId()!=null){
					queryString.append(" AND ne.jerarquiaOrganizacional = " + 
							mandamiento.getUsuario().getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId());		
			}
		}
				
		queryString.append(" order by ne.numeroExpediente desc");									

        final PaginacionDTO pag = PaginacionThreadHolder.get();
        return super.ejecutarQueryPaginado(queryString, pag);        
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Mandamiento> consultarMandamientoPorFiltro(
			FiltroMandamientoDTO filtroMandamientoDTO) {

		StringBuffer queryString = new StringBuffer();
		if (filtroMandamientoDTO.getIdInstitucion() != null

				&& filtroMandamientoDTO.getIdInstitucion().equals(
						Instituciones.PGJ.getValorId())) {
			queryString
					.append(" SELECT m FROM Mandamiento m LEFT JOIN m.actividad.expediente.numeroExpedientes numExps WHERE 1=1 ");
		} else {
			queryString.append(" SELECT m FROM Mandamiento m WHERE 1=1");
		}

		if (filtroMandamientoDTO.getNumeroExpediente() != null
				&& !filtroMandamientoDTO.getNumeroExpediente().isEmpty()) {
			
			if (filtroMandamientoDTO.getIdInstitucion() != null
					&& filtroMandamientoDTO.getIdInstitucion().equals(
							Instituciones.PGJ.getValorId())) {
				queryString.append(" AND numExps.numeroExpediente LIKE '%")
						.append(filtroMandamientoDTO.getNumeroExpediente())
						.append("%'");

			} else {
				queryString
						.append(" AND m.resolutivo.audiencia.numeroExpediente.numeroExpediente LIKE'%")
						.append(filtroMandamientoDTO.getNumeroExpediente())
						.append("%'");
			}
			
			queryString.append(" AND  m.estatus.valorId IN ("
					+ obtenerCadenaIds(listGrupoEstatusBusqueda) + ") ");
		}

		if (filtroMandamientoDTO.getFechaInicioBusqueda() != null
				&& filtroMandamientoDTO.getFechaFinBusqueda() != null) {
			queryString
					.append(" AND CONVERT (varchar, m.fechaCreacion, 112) BETWEEN ")
					.append(DateUtils.formatearBD(filtroMandamientoDTO
							.getFechaInicioBusqueda()))
					.append(" AND ")
					.append(DateUtils.formatearBD(filtroMandamientoDTO
							.getFechaFinBusqueda()));
		}

		if (filtroMandamientoDTO.getIdEstatus() != null) {
			queryString.append(" and m.estatus.valorId =").append(
					filtroMandamientoDTO.getIdEstatus());
		}

		if (filtroMandamientoDTO.getIdsTipoMandamiento() != null
				&& !filtroMandamientoDTO.getIdsTipoMandamiento().isEmpty()) {
			queryString.append(" AND m.tipoMandamiento.valorId IN ("
					+ obtenerCadenaIds(filtroMandamientoDTO
							.getIdsTipoMandamiento()) + ") ");
		}

		if (filtroMandamientoDTO.getIdInstitucion() != null
				&& filtroMandamientoDTO.getClaveFuncionario() != null
				&& filtroMandamientoDTO.getIdInstitucion() != null
				&& filtroMandamientoDTO.getIdInstitucion().equals(
						Instituciones.PGJ.getValorId())) {

			queryString.append(" AND numExps.funcionario.claveFuncionario = "
					+ filtroMandamientoDTO.getClaveFuncionario() + " ");

			queryString
					.append(" AND numExps.numeroExpediente.jerarquiaOrganizacional.jerarquiaOrganizacionalId=")
					.append(Areas.UNIDAD_INVESTIGACION.ordinal());
		}

		logger.info(queryString);

		final PaginacionDTO pag = PaginacionThreadHolder.get();

		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				queryString.append(" order by ");
				queryString.append("m.numeroCausa");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("2")) {
				queryString.append(" order by ");
				queryString.append("m.tipoMandamiento.valorId");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("3")) {
				queryString.append(" order by ");
				queryString.append("m.fechaCreacion");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("4")) {
				queryString.append(" order by ");
				queryString.append("m.estatus.valorId");
				queryString.append(" ").append(pag.getDirOrd());
			}
		}
		return super.ejecutarQueryPaginado(queryString, pag);
	}

    @Override
    public Mandamiento obtenerMandamientoPorFolioDoc(String folioDocumento) {

        StringBuffer queryString = new StringBuffer();
        queryString.append(" FROM Mandamiento m  ")
                .append(" WHERE m.folioDocumento = '").append(folioDocumento)
                .append("'");
        Query query = super.getSession().createQuery(queryString.toString());
        return (Mandamiento) query.uniqueResult();
    }

	@Override
	public Mandamiento obtenerMandamientoPorResolutivo(Resolutivo resolutivo)
			throws NSJPNegocioException {

		if (resolutivo == null || resolutivo.getResolutivoId() == null
				|| resolutivo.getResolutivoId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		StringBuffer queryString = new StringBuffer();
		queryString.append(" FROM Mandamiento m  ")
				.append(" WHERE m.resolutivo.resolutivoId = '")
				.append(resolutivo.getResolutivoId()).append("'");
		Query query = super.getSession().createQuery(queryString.toString());
		return (Mandamiento) query.uniqueResult();
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la conversi&oacute;n de una lista 
	 * de Long a una cadena con los identificadores concatenados para ser ingresados 
	 * como par&aacute;metros a una consulta. 
	 * @param listaIdentificadores -  Lista de Long, la cual se convertir&aacute; a 
	 * 								  una cadena de identificadores separados por comas.
	 * @return ids - Cadena con los identificadores separados por comas.
	 */
	private String obtenerCadenaIds(List<Long> listaIdentificadores){
		StringBuilder ids = new StringBuilder("");
		if (listaIdentificadores != null 
				&& !listaIdentificadores.isEmpty()){
			for (int i=0; i<listaIdentificadores.size(); i++){
				if (i>0){
					ids.append(", ");
				}	
				ids.append(listaIdentificadores.get(i).toString());
			}
		}
		return ids.toString();
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.documento.MandamientoDAO#consultarDocumentosRelacionadosMandamientoPorTipo(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarDocumentosRelacionadosMandamientoPorTipo(
			Long mandamientoId, Long idTipoDocumento)
			throws NSJPNegocioException {
		
		if (mandamientoId == null 
				|| mandamientoId <1L
				|| idTipoDocumento == null
				|| idTipoDocumento < 1L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		StringBuilder sb = new StringBuilder("");
		sb.append(" SELECT d ");
		sb.append(" FROM Documento d ");
		sb.append(" WHERE d.documentoId IN( ");
		sb.append("		SELECT rd.documentoRelacionado.documentoId ");
		sb.append("		FROM RelacionDocumento rd ");
		sb.append("		WHERE rd.documentoPrincipal.documentoId IN( ");
		sb.append("			SELECT sm.documentoId ");
		sb.append("			FROM SolicitudMandamiento sm ");
		sb.append("			WHERE sm.mandamiento.documentoId = :mandamientoId ");
		sb.append("		) AND rd.documentoRelacionado.tipoDocumento.valorId = :idTipoDocumento ");
		sb.append(" ) ");
		
		Query query = getSession().createQuery(sb.toString());
		query.setParameter("mandamientoId", mandamientoId);
		query.setParameter("idTipoDocumento", idTipoDocumento);
		
		return (List<Documento>) query.list();
	}

	
	@Override
	public Boolean consultarExistenRelacionesDelitoPersonaPorMandamiento(
			Long audienciaId, Long tipoMandamientoId, Long delitoPersonaId){

		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT COUNT( MandamientoDelitoPersona.Mandamiento_id )");
		sb.append(" FROM Mandamiento INNER JOIN MandamientoDelitoPersona ");
		sb.append(" ON Mandamiento.Mandamiento_id = MandamientoDelitoPersona.Mandamiento_id ");
		sb.append(" INNER JOIN Resolutivo ON Resolutivo.Resolutivo_id = Mandamiento.Resolutivo_id");
		sb.append(" WHERE    Resolutivo.Audiencia_id = ").append(audienciaId);
		sb.append(" AND Mandamiento.TipoMandamiento_val = ").append(
				tipoMandamientoId);
		sb.append(" AND MandamientoDelitoPersona.DelitoPersona_id = ").append(
				delitoPersonaId);
		Query query = super.getSession().createSQLQuery(sb.toString());

		logger.info(" QUERYS:" + query.getQueryString());

		Integer numeroDeManDelPer = (query.uniqueResult() != null ? ((Integer) query
				.uniqueResult()) : 0);

		logger.info("Numero de relaciones delito persona:" + numeroDeManDelPer);

		if (numeroDeManDelPer > 0L) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Long obtenerUltimoFolioMandamientoPorCausa(String numeroDeCausa){

		final StringBuffer query = new StringBuffer();

		query.append(" SELECT MAX ( CAST (");
		query.append(" SUBSTRING(m.folioDocumento,");
		query.append(" (LEN(m.folioDocumento) ").append(SIGNO_MENOS);
		query.append(ConsecutivosUtil.LONGITUD_CONSECUTIVO_MANDAMIENTO_MENOS_UNO)
				.append("),");
		query.append(ConsecutivosUtil.LONGITUD_CONSECUTIVO_MANDAMIENTO);
		query.append(" )  AS long ) )");
		query.append(" FROM Mandamiento m ");
		query.append(" WHERE m.folioDocumento IS NOT NULL ");
		query.append(" AND m.folioDocumento like '" + numeroDeCausa + "%'");

		logger.debug("query :: " + query);
		Query hbq = super.getSession().createQuery(query.toString());
		return (Long) hbq.uniqueResult();
	}
}
