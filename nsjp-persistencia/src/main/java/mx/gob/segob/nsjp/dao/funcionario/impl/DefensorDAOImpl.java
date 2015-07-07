/**
* Nombre del Programa	: DefensorDAOImpl.java
* Autor					: cesarAgustin
* Compania				: Ultrasist
* Proyecto				: NSJP                    Fecha: 18 May 2011
* Marca de cambio		: N/A
* Descripcion General	: Implementaciom de metodos de acceso a datos de la entidad Defensor
* Programa Dependiente	:N/A
* Programa Subsecuente	:N/A
* Cond. de ejecucion	:N/A
* Dias de ejecucion		:N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                 :N/A
* Compania              :N/A
* Proyecto              :N/A                                 Fecha: N/A
* Modificacion          :N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.dao.funcionario.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.funcionario.DefensorDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Funcionario;

import org.springframework.stereotype.Repository;

/**
 * Implementaciom de metodos de acceso a datos de la entidad Defensor.
 * @version 1.0
 * @author cesarAgustin
 *
 */
@Repository
public class DefensorDAOImpl extends GenericDaoHibernateImpl<Funcionario, Long>
		implements DefensorDAO {

	@Override
	public List<Funcionario> consultarDefensoresActivos() {
		
		return null;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> consultarDefensoresActivosPorCatDistritoId(
			Long catDistritoId) {

		final StringBuffer queryString = new StringBuffer();

		queryString.append("SELECT F FROM Funcionario F");
		queryString.append(" LEFT JOIN  F.usuario U ");
		queryString.append(" LEFT JOIN  F.usuario.usuarioRoles as UR ");
		queryString.append(" WHERE UR.rol.rolId = ").append(
				Roles.DEFENSOR.getValorId());
		// Permite filtrar por distrito
		if (catDistritoId != null && catDistritoId > 0L) {
			queryString
					.append(" AND F.discriminante.distrito.catDistritoId = ")
					.append(catDistritoId);
		}
		queryString.append(" AND U.esActivo = ").append(true);
		queryString.append(" ORDER BY F.nombreFuncionario ASC ");

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return super.ejecutarQueryPaginado(queryString, pag);
	}
}
