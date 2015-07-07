package mx.gob.segob.nsjp.dao.solicitud.impl;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudTranscricpionAudienciaDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.SolicitudTranscripcionAudiencia;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class SolicitudTranscripcionAudienciaDAOImpl extends GenericDaoHibernateImpl<SolicitudTranscripcionAudiencia, Long> implements
		SolicitudTranscricpionAudienciaDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudTranscripcionAudiencia> consultarSolicitudesTrascripcionAudienciaPendientes() {
        final StringBuffer query = new StringBuffer();
        query.append("from SolicitudTranscripcionAudiencia s");
        query.append(" where s.estatus = "+EstatusSolicitud.ABIERTA.getValorId());
        logger.debug(query);
        Query hbq = super.getSession().createQuery(query.toString());

        return hbq.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudTranscripcionAudiencia> consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(
			Long idAudiencia, Long tipoId, List<Long> estatusId) {
		
		final StringBuffer query = new StringBuffer();
        query.append("from SolicitudTranscripcionAudiencia s")
        .append(" where s.estatus in ("+ crearCadenaIdentificadores(estatusId)+") ")
        .append(" and s.tipoSolicitud = " + tipoId)
        .append(" and s.audiencia.audienciaId = " + idAudiencia)
        .append(" and s.nombreSolicitante is not null");
        logger.debug(query);
        
        final PaginacionDTO pag = PaginacionThreadHolder.get();

        return ejecutarQueryPaginado(query, pag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudTranscripcionAudiencia> consultarSolicitudMaster(
			Long idAudiencia, Long idTipo) {
		final StringBuffer query = new StringBuffer();
        query.append("from SolicitudTranscripcionAudiencia s")        
        .append(" where s.tipoSolicitud = " + idTipo)
        .append(" and s.audiencia.audienciaId = " + idAudiencia)
        .append(" and s.nombreSolicitante is null");
        logger.debug(query);
        Query hbq = super.getSession().createQuery(query.toString());

        return hbq.list();
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudTranscricpionAudienciaDAO#consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudTranscripcionAudiencia> consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(
			Long tipoId, Long estatusId,Long dif,Long discriminanteId, Date fechaIni, Date fechaFin, Funcionario funcionario) {
		
		StringBuffer query = new StringBuffer();
        query.append("from SolicitudTranscripcionAudiencia s")
        .append(" where s.estatus.valorId =")
        .append(estatusId)
        .append(" and s.tipoSolicitud.valorId =")
        .append(tipoId);
        query.append(" and s.audiencia.numeroExpediente.expediente.discriminante.catDiscriminanteId=")
        .append(discriminanteId);
        
        if(fechaIni!=null && fechaFin!=null ){
        	query.append(" AND CONVERT (nvarchar, s.fechaCreacion, 112) between ");
        	query.append(DateUtils.formatearBD(fechaIni));
        	query.append(" AND ").append(DateUtils.formatearBD(fechaFin));
        }
        else if (fechaIni != null) {
				query.append(" AND CONVERT (nvarchar, s.fechaCreacion, 112) = ");
				query.append(DateUtils.formatearBD(fechaIni));
        }
        
        //Se Agrega el funcionario que solicita como filtro        
        if(funcionario != null){
        	query.append(" AND s.funcionarioSolicitante =  ");
        	query.append(" ").append(funcionario.getClaveFuncionario()).append(" ");
        } 
        
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        return super.ejecutarQueryPaginado(query, pag);
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la creaci&oacute;n de una cadena de 
	 * identificadores para ser ingresada dentro de una consulta en SQL.
	 * @param ids - List<Long> con los identificadores de los cuales se va a generar 
	 * 				la cadena.
	 * @return cadenaIds - Cadena con los identificadores de la lista separados por 
	 * 					   comas. 
	 */
	private String crearCadenaIdentificadores(List<Long> ids){
		StringBuilder cadenaIds = new StringBuilder("");
		if (ids != null 
				&& !ids.isEmpty()){
			for (int i=0; i<ids.size(); i++){
				if (i>0){
					cadenaIds.append(", ");
				}
				cadenaIds.append(ids.get(i));
			}
		}
		return cadenaIds.toString();
	}

}
