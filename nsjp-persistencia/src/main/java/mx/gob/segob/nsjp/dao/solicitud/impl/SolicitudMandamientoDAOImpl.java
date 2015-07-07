package mx.gob.segob.nsjp.dao.solicitud.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudMandamientoDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.SolicitudMandamiento;
import mx.gob.segob.nsjp.model.Valor;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class SolicitudMandamientoDAOImpl extends GenericDaoHibernateImpl<SolicitudMandamiento, Long>
		implements SolicitudMandamientoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudMandamiento> consultarSolicitudesMandamientoBy(
			EstatusSolicitud estado) throws NSJPNegocioException {
		final StringBuffer hqlQuery = new StringBuffer();

		hqlQuery.append(" FROM SolicitudMandamiento s")
				.append(" WHERE s.estatus = "+estado.getValorId());
		
		logger.debug("query :: " + hqlQuery);
		
		Query hbq = super.getSession().createQuery(hqlQuery.toString());

		return hbq.list();
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudMandamientoDAO#crearSolicitudMandamientoConInfoExistente(mx.gob.segob.nsjp.model.SolicitudMandamiento)
	 */
	@Override
	public Long crearSolicitudMandamientoConInfoExistente(
			SolicitudMandamiento solicitudMandamiento)
			throws NSJPNegocioException {
		
		boolean tipoMandamiento = Boolean.FALSE;
		boolean mandamiento = Boolean.FALSE;
		
		if (solicitudMandamiento == null
				|| solicitudMandamiento.getDocumentoId() == null
				|| solicitudMandamiento.getDocumentoId() < 1L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		
		if (solicitudMandamiento.getTipoMandamiento() != null
				&& solicitudMandamiento.getTipoMandamiento().getValorId() != null
				&& solicitudMandamiento.getTipoMandamiento().getValorId() > 0L){
			tipoMandamiento = Boolean.TRUE;
		}
		
		if (solicitudMandamiento.getMandamiento() != null
				&& solicitudMandamiento.getMandamiento().getDocumentoId() != null
				&& solicitudMandamiento.getMandamiento().getDocumentoId() > 0L){
			mandamiento = Boolean.TRUE;
		}
		
		final StringBuilder query = new StringBuilder();
		
		query.append(" INSERT INTO SolicitudMandamiento ");
		query.append(" (SolicitudMandamiento_id");
		if (tipoMandamiento){
			query.append(", TipoMandamiento_val");
		}
		if (mandamiento){
			query.append(", Mandamiento_id");
		}
		query.append(") ");
		query.append(" values ( :solicitudMandamientoId");
		if (tipoMandamiento){
			query.append(", :tipoMandamientoId");			
		}
		if (mandamiento){
			query.append(", :mandamientoId");
		}
		query.append(" ) ");
		
		
		
		logger.debug("query :: " + query);
		Query hbq = super.getSession().createSQLQuery(query.toString());
		
		hbq.setParameter("solicitudMandamientoId", solicitudMandamiento.getDocumentoId());
		if (tipoMandamiento){
			hbq.setParameter("tipoMandamientoId", solicitudMandamiento.getTipoMandamiento().getValorId());			
		}
		if (mandamiento){
			hbq.setParameter("mandamientoId", solicitudMandamiento.getMandamiento().getDocumentoId());			
		}

		hbq.executeUpdate();
		
		return solicitudMandamiento.getDocumentoId();
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudMandamientoDAO#consultarSolicitudesMandamientoPorDestinatario(mx.gob.segob.nsjp.model.Funcionario, java.util.List, java.util.List, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudMandamiento> consultarSolicitudesMandamientoPorDestinatario(
			Funcionario destinatario, List<Valor> estatusSolicitud,
			List<Valor> tipoMandamiento, List<Valor> tipoSolicitud)
			throws NSJPNegocioException {
		
		if (destinatario == null
				|| destinatario.getClaveFuncionario() == null
				|| destinatario.getClaveFuncionario() < 1L
				|| estatusSolicitud == null
				|| estatusSolicitud.isEmpty()
				|| tipoMandamiento == null
				|| tipoMandamiento.isEmpty()
				|| tipoSolicitud ==  null
				|| tipoSolicitud.isEmpty()){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		StringBuffer sb = new StringBuffer("");
		sb.append(" SELECT sm ");
		sb.append(" FROM SolicitudMandamiento sm ");
		sb.append(" WHERE sm.destinatario.claveFuncionario = ");
		sb.append(destinatario.getClaveFuncionario());
		sb.append(" AND sm.estatus.valorId IN (");
		sb.append(creaListaIds(estatusSolicitud)+") ");
		sb.append(" AND sm.tipoSolicitud.valorId IN (");
		sb.append(creaListaIds(tipoSolicitud)+") ");
		sb.append(" AND sm.mandamiento.tipoMandamiento.valorId IN (");
		sb.append(creaListaIds(tipoMandamiento)+") ");
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		
		return (List<SolicitudMandamiento>) ejecutarQueryPaginado(sb, pag);
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la 
	 * transformaci&oacute;n de una lista de Valor a una lista
	 * de Long con el identificador de cada uno de los registros.
	 * 
	 * @param valores - List<Valor> de la cual se van a extraer
	 * 					los identificadores del registro.
	 * 
	 * @return identificadores - String con los identificadores separados
	 * 							 por comas de cada uno de los valores que 
	 * 							 se ingresaron como par&aacute;metros.
	 */
	private String creaListaIds(List<Valor> valores){
		StringBuilder identificadores = new StringBuilder("");
		if (valores != null
				&& !valores.isEmpty()){
			for (int i=0; i<valores.size(); i++){
				if (i > 0){
					identificadores.append(", ");
				}
				identificadores.append(valores.get(i).getValorId());
			}
		}
		return identificadores.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudMandamiento> consultarSolicitudesMandatoJudicialPorFiltro(
			SolicitudMandamiento solicitudMandamiento) {

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT sm ");
		sb.append(" FROM SolicitudMandamiento sm WHERE 1=1");

		if (solicitudMandamiento.getMandamiento() != null
				&& solicitudMandamiento.getMandamiento().getDocumentoId() != null) {
			sb.append(" AND sm.mandamiento.documentoId =").append(
					solicitudMandamiento.getMandamiento().getDocumentoId());
		}

		if (solicitudMandamiento.getDocumentoId() != null) {
			sb.append(" AND sm.solicitudMandamientoId = ").append(
					solicitudMandamiento.getDocumentoId());
		}

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return (List<SolicitudMandamiento>) ejecutarQueryPaginado(sb, pag);
	}

}
