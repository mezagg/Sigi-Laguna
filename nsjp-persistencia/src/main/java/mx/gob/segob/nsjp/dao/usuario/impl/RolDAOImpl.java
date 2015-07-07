/**
 * Nombre del Programa  : RolDAOImpl.java
 * Autor                : Daniel Jiménez
 * Compania             : TATTVA-IT
 * Proyecto             : NSJP                    Fecha: 27 Jul 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Acceso a la información de Roles
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
package mx.gob.segob.nsjp.dao.usuario.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.usuario.RolDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.rol.FiltroRolesDTO;
import mx.gob.segob.nsjp.model.Rol;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Acceso a la información de Roles
 * 
 * @version 1.0
 * @author Daniel Jiménez
 * 
 */
@Repository
public class RolDAOImpl extends GenericDaoHibernateImpl<Rol, Long> implements
		RolDAO {

	@SuppressWarnings("unchecked")
	public List<Rol> consultarRoles(FiltroRolesDTO filtroRolesDTO)
			throws NSJPNegocioException {
		StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append("FROM Rol r ");
		hqlQuery.append(" WHERE esActivo = ").append(true)
				.append(" AND institucionPertenece = ")
				.append(filtroRolesDTO.getConfInstitucionId());
		if (filtroRolesDTO.getIdRolSelec() != null
				&& !filtroRolesDTO.getIdRolSelec().equals("")) {
			hqlQuery.append(" AND r.rolId not in ("
					+ filtroRolesDTO.getIdRolSelec() + ")");
		}
		
		if(filtroRolesDTO.getJerarquiaOrganizacionalId() != null 
				&& filtroRolesDTO.getJerarquiaOrganizacionalId()> 0){
			hqlQuery.append(" AND r.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ")
					.append( filtroRolesDTO.getJerarquiaOrganizacionalId())
					.append(" ");
		}

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		if (pag != null && pag.getCampoOrd() != null) {
			logger.debug("pag.getCampoOrd():" + pag.getCampoOrd());
			hqlQuery.append(" order by ");
			if (pag.getCampoOrd().equals("Clave")) {
				hqlQuery.append("r.nombreRol");
			} else {
				hqlQuery.append(pag.getCampoOrd());
			}

			hqlQuery.append(" ").append(pag.getDirOrd());
		}
		return super.ejecutarQueryPaginado(hqlQuery, pag);
	}

	@Override
	public Rol consultarRol(Rol rol) throws NSJPNegocioException {
		Rol rolResp = null;
		StringBuffer queryString = new StringBuffer();
		Query query = null;
		if (rol != null) {
			// Consulta rol por ID
			if (rol.getRolId() != null) {
				queryString.append(" FROM Rol r WHERE r.rolId = '")
						.append(rol.getRolId()).append("'");

			}//Consulta rol por nombre
			else if (!rol.getNombreRol().isEmpty()) {
				queryString.append(" FROM Rol r WHERE r.nombreRol = '")
						.append(rol.getNombreRol()).append("'");
			}
			query = super.getSession().createQuery(queryString.toString());
			if (!query.list().isEmpty()) {
				rolResp = (Rol) query.list().get(0);
			}

		}
		return rolResp;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Rol> consultarSubRoles(Rol rol) throws NSJPNegocioException {
		StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append("FROM Rol r ");
		hqlQuery.append(" WHERE esActivo = ").append(true)
				.append(" AND r.rolPadre = ").append(rol.getRolId());
		Query query = super.getSession().createQuery(hqlQuery.toString());
		logger.debug("query :: " + query);
		List<Rol> resp = query.list();
		logger.debug("resp.size() :: " + resp.size());
		return resp;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Rol consultarRolPadre(Long idRol){
		StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append(" SELECT r.rolPadre FROM Rol r ");
		hqlQuery.append(" WHERE r.esActivo = ").append(true)
				.append(" AND r.rolId = ").append(idRol);
		Query query = super.getSession().createQuery(hqlQuery.toString());
		logger.debug("query :: " + query);
		return (Rol) query.uniqueResult();
	}
	
}
