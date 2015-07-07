/**
 * Nombre del Programa	: FuncionarioExternoAudienciaDAOImpl.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Feb 2013
 * Marca de cambio      : N/A
 * Descripcion General  : Implementacion de FuncionarioExternoAudienciaDAO
 * Programa Dependiente	: N/A
 * Programa Subsecuente	: N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                : N/A
 * Compania             : N/A
 * Proyecto             : N/A                             Fecha: N/A
 * Modificacion         : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.funcionarioexterno.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoAudienciaDAO;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudienciaId;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Implementacion de FuncionarioExternoAudienciaDAO
 * 
 * @author AlejandroGA
 * @version 1.0
 */

@Repository
public class FuncionarioExternoAudienciaDAOImpl
		extends
		GenericDaoHibernateImpl<FuncionarioExternoAudiencia, FuncionarioExternoAudienciaId>
		implements FuncionarioExternoAudienciaDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<FuncionarioExterno> consultarFuncionarioExternoPorRol(
			Long rolId, Long audienciaId) {

		final StringBuffer queryStr = new StringBuffer();

		queryStr.append(" SELECT fea.funcionarioExterno ").append(
				" FROM FuncionarioExternoAudiencia fea WHERE 1=1");
		if (audienciaId != null && audienciaId > 0L) {
			queryStr.append(" AND fea.audiencia.audienciaId = " + audienciaId);
		}
		if (rolId != null && rolId > 0L) {
			queryStr.append(" AND fea.funcionarioExterno.rol.rolId = " + rolId);
		}

		Query qry = super.getSession().createQuery(queryStr.toString());
		logger.info("QUERY:" + qry.toString());
		return qry.list();
	}
}
