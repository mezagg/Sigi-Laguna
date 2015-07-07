/**
 * Nombre del Programa : SolicitudAudienciaDAOImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 8 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci�n del onjeto de acceso a datos para la entidad SolicitudAudiencia
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

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudAudienciaDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.SolicitudAudiencia;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementaci�n del onjeto de acceso a datos para la entidad
 * SolicitudAudiencia.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Repository
public class SolicitudAudienciaDAOImpl
        extends
            GenericDaoHibernateImpl<SolicitudAudiencia, Long>
        implements
            SolicitudAudienciaDAO {

	@SuppressWarnings("unchecked")
    @Override
    public List<SolicitudAudiencia> consultarSolicitudesAudienciaPendientes(Long discriminanteId) {
        
		final StringBuffer query = new StringBuffer();
        
		query.append(" FROM SolicitudAudiencia s");
		query.append(" WHERE s.audiencia.fechaAsignacionSala IS NULL");
		query.append(" AND s.tipoSolicitud = ").append(
				TiposSolicitudes.AUDIENCIA.getValorId());
		query.append(" AND s.estatus NOT IN ( ")
				.append(EstatusSolicitud.CANCELADA.getValorId()).append(") ");
		query.append(
				" AND s.audiencia.numeroExpediente.expediente.discriminante.catDiscriminanteId=")
				.append(discriminanteId);

        final PaginacionDTO pag = PaginacionThreadHolder.get();
        
        if (pag != null && pag.getCampoOrd() != null) {
        	
			if (pag.getCampoOrd().equals("caso")) {
				query.append(" order by ");
				query.append(" s.audiencia.numeroExpediente.expediente.caso.numeroGeneralCaso ");
				query.append(" ").append(pag.getDirOrd());
			}
			
			if (pag.getCampoOrd().equals("numeroExpediente")) {
				query.append(" order by ");
				query.append(" s.audiencia.numeroExpediente.numeroExpediente ");
				query.append(" ").append(pag.getDirOrd());
			}
			// La fecha de solicitud es igual a la fecha en que, se creo la
			// solicitud
			if (pag.getCampoOrd().equals("fechaHoraSolicitud")) {
				query.append(" order by ");
				query.append(" s.fechaCreacion ");
				query.append(" ").append(pag.getDirOrd());
			}
			
			if (pag.getCampoOrd().equals("institucionSolicitante")) {
				query.append(" order by ");
				query.append(" s.confInstitucion.nombreInst ");
				query.append(" ").append(pag.getDirOrd());
			}			
		}
        logger.debug("pag :: " + pag);

        return super.ejecutarQueryPaginado(query, pag);
    }
    
    @Override
    public SolicitudAudiencia consultarSolicitudesAudienciaPorAudiencia(Long idAudiencia) {
        final StringBuffer query = new StringBuffer();
        query.append("from SolicitudAudiencia s");
        query.append(" where s.audiencia.audienciaId = "+idAudiencia);
        Query hbq = super.getSession().createQuery(query.toString());

        return (SolicitudAudiencia) hbq.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Solicitud> consultarOtrasSolicitudesPendientes() {
        final StringBuffer query = new StringBuffer();
        query.append("from Solicitud s");
        query.append(" where not exists (from SolicitudAudiencia sa where s.documentoId = sa.documentoId)");
        query.append("and s.estatus = "+EstatusSolicitud.ABIERTA.getValorId()); //FIXME VAP string?
        Query hbq = super.getSession().createQuery(query.toString());

        return hbq.list();
    }
    /*
     * (non-Javadoc)
     * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudAudienciaDAO#consultarSolicitudesAudienciaPorTipoyEstado(mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes, mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud)
     */
    @SuppressWarnings("unchecked")
	@Override
	public List<SolicitudAudiencia> consultarSolicitudesAudienciaPorTipoyEstado(
			TiposSolicitudes tipo, EstatusSolicitud estado) {
				
		return getHibernateTemplate().find("from SolicitudAudiencia sa where sa.tipoSolicitud.valorId = ? and sa.estatus.valorId = ?",
				tipo.getValorId(),estado.getValorId());
	}

    /*
     * (non-Javadoc)
     * @see mx.gob.segob.nsjp.dao.solicitud.SolicitudAudienciaDAO#consultarSolicitudesAudienciaConCriterios
     */
    
	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudAudiencia> consultarSolicitudesAudienciaConCriterios (
			SolicitudAudiencia solicitudAudiencia,
			List<Long> lstIdEstatusSolicitud, List<Long> lstIdTipoSolicitud,
			List<Long> lstIdEstatusAudiencia, List<Long> lstIdTipoAudiencia,
			String tipoConsulta
			) throws NSJPNegocioException {

		String opcionConsulta = tipoConsulta != null ? tipoConsulta : Solicitud.TODAS;
		String cadenaIdES = "";
		String cadenaIdTS = "";
		String cadenaIdEA = "";
		String cadenaIdTA = "";
		
		

		if (lstIdEstatusSolicitud != null && !lstIdEstatusSolicitud.isEmpty())
			cadenaIdES = lstIdEstatusSolicitud.toString().substring(1,
					lstIdEstatusSolicitud.toString().length() - 1);

		if (lstIdTipoSolicitud != null && !lstIdTipoSolicitud.isEmpty())
			cadenaIdTS = lstIdTipoSolicitud.toString().substring(1,
					lstIdTipoSolicitud.toString().length() - 1);

		if (lstIdEstatusAudiencia != null && !lstIdEstatusAudiencia.isEmpty())
			cadenaIdEA = lstIdEstatusAudiencia.toString().substring(1,
					lstIdEstatusAudiencia.toString().length() - 1);

		if (lstIdTipoAudiencia != null && !lstIdTipoAudiencia.isEmpty())
			cadenaIdTA = lstIdTipoAudiencia.toString().substring(1,
					lstIdTipoAudiencia.toString().length() - 1);

		final StringBuffer query = new StringBuffer();

		query.append(" FROM SolicitudAudiencia s");
		query.append(" WHERE 1=1 ");

		if (lstIdEstatusSolicitud != null && !lstIdEstatusSolicitud.isEmpty()) {
			query.append(" AND s.estatus in ( " );
			query.append(cadenaIdES);
			query.append(" ) ");
		}

		if (lstIdTipoSolicitud != null && !lstIdTipoSolicitud.isEmpty()) {
			query.append(" AND s.tipoSolicitud in ( " );
			query.append(cadenaIdTS);
			query.append(" ) ");
		}

		if (lstIdEstatusAudiencia != null && !lstIdEstatusAudiencia.isEmpty()) {
			query.append(" AND s.audiencia.estatus in ( " );
			query.append(cadenaIdEA);
			query.append(" ) ");
		}		
		
		if (lstIdTipoAudiencia != null && !lstIdTipoAudiencia.isEmpty()) {
			query.append(" AND s.audiencia.tipo in ( " );
			query.append(cadenaIdTA);
			query.append(" ) ");
		}		
		
		if (solicitudAudiencia != null){
			
			if (solicitudAudiencia.getAreaOrigen() != null && solicitudAudiencia.getAreaOrigen() > 0) {
				query.append(" AND s.areaOrigen = ");
				query.append(solicitudAudiencia.getAreaOrigen());
			}
			
			NumeroExpediente numeroExpediente = solicitudAudiencia.getNumeroExpediente();
			if ( numeroExpediente != null && numeroExpediente.getNumeroExpediente() != null && !numeroExpediente.getNumeroExpediente().equals("") ) {
				query.append(" AND s.numeroExpediente.numeroExpediente like '%");
				query.append(numeroExpediente.getNumeroExpediente());
				query.append("%' ");
			}
			
			if (opcionConsulta.equals(Solicitud.NUMERO_EXPEDIENTE_ID)) {
				if (solicitudAudiencia.getNumeroExpediente() != null
						&& solicitudAudiencia.getNumeroExpediente()
								.getNumeroExpedienteId() != null) {
					query.append(" AND s.numeroExpediente.numeroExpedienteId = ");
					query.append(solicitudAudiencia.getNumeroExpediente()
							.getNumeroExpedienteId());
				}
			}
			else if(opcionConsulta.equals(Solicitud.GENERADAS)) {
				Funcionario solicitante = solicitudAudiencia.getFuncionarioSolicitante();
				if (solicitante != null) {
					query.append(" AND s.funcionarioSolicitante.claveFuncionario = ");
					query.append(solicitante.getClaveFuncionario());
				}
			} else if (opcionConsulta.equals(Solicitud.POR_ATENDER)){
				Funcionario destinatario = solicitudAudiencia.getDestinatario();
				if (destinatario != null) {
					query.append(" AND s.destinatario.claveFuncionario = ");
					query.append(destinatario.getClaveFuncionario());
				}				
			} else if (opcionConsulta.equals(Solicitud.NUMEROCASOASOCIADO)){
					
				if (solicitudAudiencia.getNumeroExpediente() != null){
					if(solicitudAudiencia.getNumeroExpediente().getExpediente() != null){
						if(solicitudAudiencia.getNumeroExpediente().getExpediente().getCaso() != null){
							query.append(" AND s.numeroExpediente.expediente.caso.numeroGeneralCaso = ");
							query.append("'" + solicitudAudiencia.getNumeroExpediente().getExpediente().getCaso()+"'");			
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
    
}
