/**
 * Nombre del Programa : DatoPruebaDAOImpl.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 29/09/2011
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
package mx.gob.segob.nsjp.dao.prueba.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.prueba.DatoPruebaDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.DatoPrueba;
import mx.gob.segob.nsjp.model.Involucrado;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementaci�n de los m�todos de accesos a datos de la entidad DatoPrueba.
 * 
 * @version 1.0
 * @author GustavoBP
 * 
 */
@Repository
public class DatoPruebaDAOImpl extends
		GenericDaoHibernateImpl<DatoPrueba, Long> implements DatoPruebaDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<DatoPrueba> buscarDatosDePrueba(Long expedienteId) {
		if (logger.isDebugEnabled()) {
			logger.debug("expedienteId:: [" + expedienteId);
		}
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("FROM DatoPrueba where expediente.expedienteId =");
		queryStr.append(expedienteId);
		logger.info(queryStr);
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return super.ejecutarQueryPaginado(queryStr, pag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatoPrueba> buscarDatosDePruebaPorInvolucrados(List<Long> involucradosId) {

		String cadenaIdInv = "";
		if(involucradosId!= null && !involucradosId.isEmpty())
			cadenaIdInv = involucradosId.toString().substring(1, involucradosId.toString().length()-1);
		if (logger.isDebugEnabled()) {
			logger.debug("involucradosId:: [" + cadenaIdInv);
		}
		
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("SELECT RPI.datoPrueba ");
		queryStr.append("FROM RelacionPruebaInvolucrado RPI WHERE 1=1");
		if ( ! cadenaIdInv.trim().isEmpty()){
			queryStr.append(" AND RPI.involucrado.elementoId IN (");
			queryStr.append( cadenaIdInv );
			queryStr.append(" ) ");
		}
		logger.info(queryStr);
		Query query = super.getSession().createQuery(queryStr.toString());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Involucrado> consultarInvolucradoConRelacionADatoPrueba(Long expedienteId, Long datoPruebaId) {
		logger.debug("expedienteId::" + expedienteId);
		logger.debug("datoPruebaId::" + datoPruebaId);
		
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("SELECT RPI.involucrado ");
		queryStr.append("FROM RelacionPruebaInvolucrado RPI WHERE RPI.datoPrueba.expediente.expedienteId =")
				.append( expedienteId);
		if ( datoPruebaId != null){
			queryStr.append(" AND RPI.datoPrueba.datoPruebaId = ");
			queryStr.append( datoPruebaId );
		}
		logger.info(queryStr);
		Query query = super.getSession().createQuery(queryStr.toString());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Involucrado> consultarInvolucradosDeExpedienteSinRelacionConDatoPrueba(
			Long expedienteId, Long datoPruebaId, Calidades[] calidades,
			Boolean esActivo) {
		
		logger.debug("expedienteId::" + expedienteId);
		logger.debug("datoPruebaId::" + datoPruebaId);

		final StringBuffer queryString = new StringBuffer();
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		
		queryString.append("FROM Involucrado i ")
					.append(" WHERE i.expediente.expedienteId = ").append(expedienteId);
		
		if(esActivo != null){
			queryString.append(" AND i.esActivo = ").append(esActivo);
		}		
		
		if(calidades!= null && calidades.length>0){
			queryString.append(" AND i.calidad.tipoCalidad.valorId IN ( ");
			for(int iCal = 0; iCal<calidades.length;iCal++)
				queryString.append(iCal>0?","+calidades[iCal].getValorId():calidades[iCal].getValorId());
			queryString.append(")");
		}
		
		queryString.append(" AND i.elementoId NOT IN ( ");
		queryString.append(" SELECT RPI.involucrado.elementoId ");
		queryString.append(" FROM RelacionPruebaInvolucrado RPI WHERE RPI.datoPrueba.expediente.expedienteId =")
				.append( expedienteId);
		if ( datoPruebaId != null){
			queryString.append(" AND RPI.datoPrueba.datoPruebaId = ");
			queryString.append( datoPruebaId );
		}
		queryString.append(")");
		
		logger.info(queryString);
		return super.ejecutarQueryPaginado(queryString, pag);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DatoPrueba> buscarPruebasPorExpediente(Long expedienteId) {
		if (logger.isDebugEnabled()) {
			logger.debug("expedienteId:: [" + expedienteId);
		}
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("SELECT DISTINCT(DP) FROM DatoPrueba DP, RelacionDatoMedioPrueba RDPMP " +
				"WHERE DP.datoPruebaId = RDPMP.datoPrueba.datoPruebaId " );
		queryStr.append(" AND  RDPMP.esAceptado = true" );
		queryStr.append(" AND  DP.esAceptado = true" );
		queryStr.append(" AND  DP.expediente.expedienteId =");
		queryStr.append(expedienteId);
		logger.info(queryStr);
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        return super.ejecutarQueryPaginado(queryStr, pag);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<DatoPrueba> buscarDatosDePrueba(Long expedienteId,Long medioPruebaId) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT dp FROM DatoPrueba dp where dp.expediente.expedienteId =");
		sb.append(expedienteId);
		sb.append(" AND dp.datoPruebaId NOT IN(");
			sb.append(" SELECT DISTINCT rmp.datoPrueba.datoPruebaId");
			sb.append(" FROM RelacionDatoMedioPrueba rmp");
			sb.append(" WHERE rmp.medioPrueba=" + medioPruebaId);
		sb.append(" )");
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return super.ejecutarQueryPaginado(sb, pag);
		
	}
	
}
