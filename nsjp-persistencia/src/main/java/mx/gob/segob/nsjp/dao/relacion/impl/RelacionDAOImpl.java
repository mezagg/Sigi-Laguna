/**
* Nombre del Programa : RelacionDAO.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 19 Apr 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacion de los metodos de acceso a datos de Relacion
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
package mx.gob.segob.nsjp.dao.relacion.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.relacion.RelacionDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Relacion;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementacion de los metodos de acceso a datos de Relacion.
 * @version 1.0
 * @author cesarAgustin
 *
 */
@Repository("relacionDAO")
public class RelacionDAOImpl extends GenericDaoHibernateImpl<Relacion, Long> implements
		RelacionDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<Relacion> obtenerRelacionSimple(Long idElemento, Long tipoRelacion) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT r FROM Relacion r ")
					.append("WHERE (r.elementoByComplementoId=").append(idElemento)
					.append(" OR r.elementoBySujetoId=").append(idElemento).append(") ")
					.append("AND r.catRelacion.catRelacionId=").append(tipoRelacion);
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Relacion> consultarRelacionByElemento(Long elementoId) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM Relacion r ")
					.append("WHERE (r.elementoByComplementoId=").append(elementoId)
					.append(" OR r.elementoBySujetoId=").append(elementoId).append(")");
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Relacion> consultarRelacionByIdElementoAndTipoRelacion(List<Long> idElementos, Long idTipoRelacion, Boolean esActivo) {
		
		String cadenaIdElementos = "";
		if(idElementos!= null && !idElementos.isEmpty())
			cadenaIdElementos = idElementos.toString().substring(1, idElementos.toString().length()-1);
		
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM Relacion r ");
		
		if(esActivo == null){
			queryString.append("WHERE r.esActivo is ").append(esActivo);
		}else{
			queryString.append("WHERE r.esActivo = ").append(esActivo);
		}
		
		if(idElementos!= null && !idElementos.isEmpty())
			queryString.append(" AND (r.elementoByComplementoId in (").append(cadenaIdElementos).append(")")
					.append(" OR r.elementoBySujetoId in (").append(cadenaIdElementos).append(") )");
		
		if(idTipoRelacion!= null)
			queryString.append(" AND r.tipoRelacion = ").append(idTipoRelacion);
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
        				
		return super.ejecutarQueryPaginado(queryString, pag);
	}
	
    /**
     * {@inheritDoc}
     */
	@Override
	public boolean existeRelacion(Long idCatRelacion, Long idElementoSujeto, Long idElementoComplemento) {
	// Cuando se tengan estos registros consultamos el expediente y si
	// alguna de las actividades del expediente corresponde a alguna
	// de las que buscamos.
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT r FROM Relacion r WHERE 1=1 ");
		
		if(idElementoSujeto != null && idElementoSujeto > 0)
			sb.append(" AND r.elementoBySujetoId = ").append(idElementoSujeto).append(" ");
		if(idElementoComplemento != null && idElementoComplemento > 0)
			sb.append("AND r.elementoByComplementoId = ").append(idElementoComplemento).append(" ").
		
		append("AND r.catRelacion = ").append(idCatRelacion).append(" ").
		append("AND r.esActivo = 1");
		
		Query query = super.getSession().createQuery(sb.toString());
		Relacion relacion = (Relacion) query.uniqueResult();
		if (relacion != null)
			return true;
		else
			return false;
	}
	
	@SuppressWarnings("unchecked")
	public Relacion consultarDomicilioNotificacionResidencia(
			Long idElementoComplemento) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM Relacion r")
				.append(" WHERE r.elementoByComplementoId = ")
				.append(idElementoComplemento)
				.append(" ORDER BY r.relacionId ASC");

		Query query = super.getSession().createQuery(queryString.toString());
		List<Relacion> relaciones = query.list();
		Relacion relacion = null;
		if (relaciones != null && !relaciones.isEmpty()) {
			// Se obtiene la primera relaci�n creada para el idElemento
			relacion = relaciones.get(0);
		}
		return relacion;
	}


}
