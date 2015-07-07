/**
* Nombre del Programa : JerarquiaOrgTipoSolicitudDAOImpl.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 16/08/2011
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
package mx.gob.segob.nsjp.dao.institucion.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.institucion.JerarquiaOrgTipoSolicitudDAO;
import mx.gob.segob.nsjp.model.JerarquiaOrgTipoSolicitud;
import mx.gob.segob.nsjp.model.Valor;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementaci�n del DAO para la entidad de cruce entre 
 * Jerarquia Organizacional y Tipo Solicitud
 *  
 * @version 1.0
 * @author GustavoBP
 *
 */
@Repository
public class JerarquiaOrgTipoSolicitudDAOImpl extends
		GenericDaoHibernateImpl<JerarquiaOrgTipoSolicitud, Long> implements
		JerarquiaOrgTipoSolicitudDAO {

	@Override
	public List<Valor> consultarTipoSolicitudPorJerarquiaOrganizacional(
			Long jerarquiaOrganizacionalId) {
		final StringBuffer query = new StringBuffer();
		query.append(" SELECT JOTP.tipoSolicitud FROM JerarquiaOrgTipoSolicitud JOTP ");
		query.append(" WHERE JOTP.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
		query.append(jerarquiaOrganizacionalId);
		query.append(" ORDER BY JOTP.tipoSolicitud.valor");

		Query hbq = super.getSession().createQuery(query.toString());

		return hbq.list();
	}
}
