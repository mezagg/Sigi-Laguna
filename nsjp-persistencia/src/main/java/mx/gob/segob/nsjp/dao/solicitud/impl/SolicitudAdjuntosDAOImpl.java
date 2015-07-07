/**
 * Nombre del Programa  : SolicitudAdjuntosDAOImpl.java
 * Autor                : Daniel Jim�nez
 * Compania             : TATTVA-IT
 * Proyecto             : NSJP                    Fecha: 07 Julio 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Componente de acceso a datos para los elementos SolicitudAdjuntos
 * Programa Dependiente : N/A
 * Programa Subsecuente : N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                :N/A
 * Compania             :N/A
 * Proyecto             :N/A                                 Fecha: N/A
 * Modificacion         :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.solicitud.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.SolicitudAdjuntos;
import mx.gob.segob.nsjp.model.SolicitudAdjuntosId;

import org.springframework.stereotype.Repository;

@Repository
public class SolicitudAdjuntosDAOImpl extends GenericDaoHibernateImpl<SolicitudAdjuntos, SolicitudAdjuntosId>
		implements mx.gob.segob.nsjp.dao.solicitud.SolicitudAdjuntosDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudAdjuntos> consultarAdjuntosXSolicitud(Long IdSolicitud,Boolean esPdf) {
		
		final StringBuffer query = new StringBuffer();
		query.append("from SolicitudAdjuntos sa")
		.append(" where sa.id.solicitudId = ")
		.append(IdSolicitud);
		if(esPdf != null){
			query.append(" AND sa.archivoDigital.tipoArchivo");
			if(esPdf == false){
				query.append(" NOT");
			}
			query.append(" LIKE '%pdf%'");
		}
		logger.debug("query :: " + query);
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return super.ejecutarQueryPaginado(query, pag);
	}

}
