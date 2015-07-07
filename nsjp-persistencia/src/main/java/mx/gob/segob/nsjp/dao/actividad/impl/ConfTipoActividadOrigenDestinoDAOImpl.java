/**
* Nombre del Programa : ConfTipoActividadOrigenDestinoDAOImpl.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 19/09/2012
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
package mx.gob.segob.nsjp.dao.actividad.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.actividad.ConfTipoActividadOrigenDestinoDAO;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.model.ConfTipoActividadOrigenDestino;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
@Repository
public class ConfTipoActividadOrigenDestinoDAOImpl extends
		GenericDaoHibernateImpl<ConfTipoActividadOrigenDestino, Long> implements
		ConfTipoActividadOrigenDestinoDAO {


	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.actividad.ConfTipoActividadOrigenDestinoDAO#consultarConfTipoActividadOrigenDestino(mx.gob.segob.nsjp.model.ConfTipoActividadOrigenDestino)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ConfTipoActividadOrigenDestino> consultarConfTipoActividadOrigenDestino(
			ConfTipoActividadOrigenDestino filtro) throws NSJPNegocioException {
		try {
			if (filtro != null) {
		        StringBuilder stringQuery = new StringBuilder();
		        stringQuery.append(" SELECT conf FROM ConfTipoActividadOrigenDestino conf ");
		        stringQuery.append(" WHERE 1=1 ");
		        if(filtro.getConfTipoActividadOrigenDestinoId() != null) {
		        	stringQuery.append(" AND conf.confTipoActividadOrigenDestinoId =  ");
		        	stringQuery.append(filtro.getConfTipoActividadOrigenDestinoId());
		        	stringQuery.append(" ");
		        }
		        if(filtro.getTipoActividadOrigenVal() != null
		        		&& filtro.getTipoActividadOrigenVal().getValorId() != null
		        		&& filtro.getTipoActividadOrigenVal().getValorId() > 0L){
		        	stringQuery.append(" AND conf.tipoActividadOrigenVal.valorId = ");
		        	stringQuery.append(filtro.getTipoActividadOrigenVal().getValorId());
		        	stringQuery.append(" ");

		        }
		        if(filtro.getTipoActividadDestinoVal() != null
		        		&& filtro.getTipoActividadDestinoVal().getValorId() != null
		        		&& filtro.getTipoActividadDestinoVal().getValorId() > 0L){
		        	stringQuery.append(" AND conf.tipoActividadDestinoVal.valorId =  ");
		        	stringQuery.append(filtro.getTipoActividadDestinoVal().getValorId());
		        	stringQuery.append(" ");
		        	
		        }
		        
		        Query q = getSession().createQuery(stringQuery.toString());
		        return (List<ConfTipoActividadOrigenDestino>) q.list();				
			}
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA,e);
		}
	}

}
