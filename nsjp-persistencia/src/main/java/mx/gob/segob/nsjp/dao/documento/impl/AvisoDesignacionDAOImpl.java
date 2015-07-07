package mx.gob.segob.nsjp.dao.documento.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.documento.AvisoDesignacionDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.AvisoDesignacion;

import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AvisoDesignacionDAOImpl extends GenericDaoHibernateImpl<AvisoDesignacion, Long>
		implements AvisoDesignacionDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<AvisoDesignacion> consultarAvisosDesignacion(Long estadoNotificacionId,
			Long claveFuncionario, Long tipoSolicitudId, Long distritoId) throws NSJPNegocioException {
		final StringBuffer query = new StringBuffer();
		query.append("SELECT d FROM AvisoDesignacion d ");
		query.append(" WHERE 1=1 ");
		
		if(estadoNotificacionId!=null && estadoNotificacionId >0L){
			query.append(" AND d.estatus = "+estadoNotificacionId);
		}
		
		if(tipoSolicitudId!=null && tipoSolicitudId > 0L){
			query.append(" AND d.solicitudDefensor.tipoSolicitud.valorId = "
					+ tipoSolicitudId);
		}
		
		if (distritoId != null && distritoId > 0L) {
			query.append(" AND d.solicitudDefensor.numeroExpediente.expediente.discriminante.distrito.catDistritoId = "
					+ distritoId);
		}
		
		if (claveFuncionario != null && claveFuncionario > 0L) {
			query.append(" AND d.funcionario.claveFuncionario = "
					+ claveFuncionario);
		}
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
	    logger.debug("pag :: " + pag);
	    if(pag!=null && pag.getCampoOrd() != null){
	    	query.append(" ORDER BY ");
	    	int orden = NumberUtils.toInt(pag.getCampoOrd(), 0);
	    	switch(orden){
		    	case 2002: //Numero General de Caso
		    		query.append(" d.expediente.caso.numeroGeneralCaso");
		    		break;
		    	case 2006: // Defensor
		    		query.append(" d.funcionario.claveFuncionario");
		    		break;
		    	case 2007: // Fecha hora designacion
		    		query.append(" d.fechaCreacion");
		    		break;
		    	case 2041: // Folio Notificacion
		    		query.append(" d.folioNotificacion");
		    		break;
		    	case 2042: // Audiencia FIXME DAJV poner ruta correcta 
		    		query.append(" d.fechaCreacion");
		    		break;
		    	case 2001: // Institucion Origen
		    		//FIXME VAP Es necesario hacer un ordenamiento en base a la institucion origen de la solicitud y la notificacion
		    	case 2003: // Numero de Expediente
		    		query.delete(0, query.length());
		    		query.append("select d");
		    		query.append(" from AvisoDesignacion d inner join d.expediente e, inner join e.numeroExpedientes n");
		    		query.append(" where d.estatus = "+estadoNotificacionId);
		    		if(claveFuncionario != null){
		    			query.append(" and d.funcionario.claveFuncionario = "+claveFuncionario);		    		
		    		}
		    		query.append(" n.numeroExpediente");
		    	case 2005: // Involucrado es detenido
		    		//FIXME DAJV poner la ruta de ordenamientos
		    	default:
		    		query.append(" d.documentoId");
		    		break;
	    	}
	    	query.append(" "+pag.getDirOrd());
	    }
	    
	    return super.ejecutarQueryPaginado(query, pag);

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AvisoDesignacion> consultarAvisosDesignacionPorIdExpediente(Long idExpediente) throws NSJPNegocioException{
		final StringBuffer query = new StringBuffer();
		query.append("from AvisoDesignacion d");
		query.append(" where d.expediente.expedienteId = "+idExpediente);
		query.append(" order by d.documentoId desc");
		Query hquery = super.getSession().createQuery(query.toString());
		return (List<AvisoDesignacion>)hquery.list();
	}

	@Override
	public AvisoDesignacion consultarAvisoDesignacionPorSolicitudDeDefensor(
			Long solicitudId) throws NSJPNegocioException {
		
		final StringBuffer query = new StringBuffer();
		query.append("from AvisoDesignacion d");
		query.append(" where d.solicitudDefensor.documentoId = "+solicitudId);		
		Query hquery = super.getSession().createQuery(query.toString());
		return (AvisoDesignacion) hquery.uniqueResult();
	}
}
