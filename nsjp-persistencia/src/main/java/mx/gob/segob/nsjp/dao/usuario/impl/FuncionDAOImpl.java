/**
 * Nombre del Programa : FuncionDAOImpl.java
 * Autor                            : cesarAgustin
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 1 Aug 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementacion de metodos de acceso a datos para la entidad Funcion
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
package mx.gob.segob.nsjp.dao.usuario.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.usuario.FuncionDAO;
import mx.gob.segob.nsjp.model.Funcion;
import mx.gob.segob.nsjp.model.Usuario;

/**
 * Implementacion de metodos de acceso a datos para la entidad Funcion.
 * 
 * @version 1.0
 * @author cesarAgustin
 * 
 */
@Repository
public class FuncionDAOImpl extends GenericDaoHibernateImpl<Funcion, Long>
		implements FuncionDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<Funcion> consultarFunciones() {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM Funcion ");

		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Funcion> consultarFuncionesByRol(Long rolId) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM Funcion f LEFT JOIN f.rolFunciones rf ")
				.append("WHERE rf.rol=").append(rolId);
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Funcion> consultarFuncionesXUsuario(Usuario usuario) {
		StringBuffer queryString = new StringBuffer();
		queryString
				.append(" FROM Funcion f WHERE f.funcionId IN ( ")
				.append(" SELECT f.funcionId FROM Funcion f left join f.rolFunciones rf WHERE rf.rol.rolId IN("
						+ " SELECT r.rolId FROM Rol r left join r.usuarioRoles ur WHERE ur.usuario.usuarioId =")
				.append(usuario.getUsuarioId()).append(" )) ");

		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Funcion> validarFuncionXUsuario(Long role, Long fnc) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select new Funcion(f.funcionId, f.nombreFuncion) " +
				"FROM Funcion f join f.modulos m join m.roles r where " +
				"f.funcionId="+fnc+" and  r.rolId in (:idsRoles)");

		Query query = super.getSession().createQuery(queryString.toString());
		query.setLong("idsRoles",role);

		return query.list();
	}

	@Override
	public Funcion consultarFuncionXNombre(Funcion fnc) {
		Funcion resp = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" FROM Funcion f WHERE f.nombreFuncion = '")
				.append(fnc.getNombreFuncion()).append("'");
		Query query = super.getSession().createQuery(queryString.toString());
		if (!query.list().isEmpty()) {
			resp = (Funcion) query.list().get(0);
		}

		return resp;
	}

	@Override
	public boolean instertarFunciones(List<Funcion> lstFunciones) {
		boolean resp = false;
		if (lstFunciones != null) {
			super.createAll(lstFunciones);
			resp = true;
		}
		return resp;
	}

}
