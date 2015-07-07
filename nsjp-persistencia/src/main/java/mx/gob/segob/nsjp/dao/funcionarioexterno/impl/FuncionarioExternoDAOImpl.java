/**
 * Nombre del Programa 		: FuncionarioExternoDAOImpl.java
 * Autor 					: AAAV
 * Compania 					: Ultrasist
 * Proyecto 					: NSJP 							Fecha: 06 Jun 2012
 * Marca de cambio 			: N/A
 * Descripcion General 		: Describir el objetivo de la clase de manera breve
 * Programa Dependiente 		: N/A
 * Programa Subsecuente 		: N/A
 * Cond. de ejecucion 		: N/A
 * Dias de ejecucion 		: N/A 							Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor 					: N/A
 * Compania 					: N/A
 * Proyecto 					: N/A 							Fecha: N/A
 * Modificacion 				: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.funcionarioexterno.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.FuncionarioExterno;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
@Repository
public class FuncionarioExternoDAOImpl extends
		GenericDaoHibernateImpl<FuncionarioExterno, Long> implements
		FuncionarioExternoDAO {

	@Override
	public FuncionarioExterno consultarFuncExternoPorClaveFuncExt(
			Long claveFuncExt, Long claveInst) {

		if (claveFuncExt == null || claveInst == null || claveFuncExt <= 0L
				|| claveInst <= 0L) {
			return null;
		}

		final StringBuffer queryString = new StringBuffer();

		queryString
				.append("SELECT fe FROM FuncionarioExterno fe")
				.append(" WHERE fe.cveFuncionarioInstExt=" + claveFuncExt)
				.append(" AND fe.confInstitucion.confInstitucionId="
						+ claveInst);

		Query query = super.getSession().createQuery(queryString.toString());
		return (FuncionarioExterno) query.uniqueResult();
	}

	private StringBuffer consultarFuncionarioExternoPorFiltro(
			FuncionarioExterno filtro) {

		if (filtro == null) {
			return null;
		}

		final StringBuffer queryString = new StringBuffer();

		queryString
				.append("SELECT DISTINCT fe FROM FuncionarioExterno fe WHERE 1 = 1");
		

		// Nombre
		if (filtro.getNombre() != null && !filtro.getNombre().equals("%%")
				&& !filtro.getNombre().trim().isEmpty()) {

			queryString.append(" AND lower(fe.nombre) like \'%")
					.append(filtro.getNombre().trim().toLowerCase()).append("%\'");
		}
		// apPat
		if (filtro.getApellidoPaterno() != null
				&& !filtro.getApellidoPaterno().equals("%%")
				&& !filtro.getApellidoPaterno().trim().isEmpty()) {

			queryString.append(" AND lower(fe.apellidoPaterno) like \'%")
					.append(filtro.getApellidoPaterno().trim().toLowerCase())
					.append("%\'");
		}
		// apMat
		if (filtro.getApellidoMaterno() != null
				&& !filtro.getApellidoMaterno().equals("%%")
				&& !filtro.getApellidoMaterno().toString().isEmpty()) {

			queryString.append(" AND lower(fe.apellidoMaterno) like \'%")
					.append(filtro.getApellidoMaterno().trim().toLowerCase())
					.append("%\'");
		}

		// FuncionarioExternoId
		if (filtro.getFuncionarioExternoId() != null
				&& filtro.getFuncionarioExternoId() > 0L) {

			queryString.append(" AND fe.funcionarioExternoId="
					+ filtro.getFuncionarioExternoId());
		}

		// Area
		if (filtro.getArea() != null && !filtro.getArea().equals("%%")) {

			queryString.append(" AND lower(fe.area) like \'%")
					.append(filtro.getArea().toLowerCase()).append("%\'");
		}

		// ConfInstitucion
		if (filtro.getConfInstitucion() != null
				&& filtro.getConfInstitucion().getConfInstitucionId() != null
				&& filtro.getConfInstitucion().getConfInstitucionId() > 0L) {
			queryString.append(" AND fe.confInstitucion.confInstitucionId="
					+ filtro.getConfInstitucion().getConfInstitucionId());
		}

		// Clave Funcionario institucion externa
		if (filtro.getCveFuncionarioInstExt() != null
				&& filtro.getCveFuncionarioInstExt() > 0L) {
			queryString.append(" AND fe.cveFuncionarioInstExt="
					+ filtro.getCveFuncionarioInstExt());
		}

		// Email
		if (filtro.getEmail() != null && !filtro.getEmail().equals("%%")) {

			queryString.append(" AND lower(fe.email) like \'%")
					.append(filtro.getEmail().toLowerCase()).append("%\'");
		}

		// Puesto
		if (filtro.getPuesto() != null && !filtro.getPuesto().equals("%%")) {

			queryString.append(" AND lower(fe.puesto) like \'%")
					.append(filtro.getPuesto().toLowerCase()).append("%\'");
		}

		// Rol
		if (filtro.getRol() != null && filtro.getRol().getRolId() != null
				&& filtro.getRol().getRolId() > 0L) {

			queryString.append(" AND fe.rol.rolId="
					+ filtro.getRol().getRolId());
		}

		return queryString;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<FuncionarioExterno> ejecutarQueryConsultarFuncionarioExternoPorFiltro(
			FuncionarioExterno filtro) {

		StringBuffer queryString = consultarFuncionarioExternoPorFiltro(filtro);
		queryString.append(" ORDER BY fe.nombre, fe.apellidoPaterno");

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return super.ejecutarQueryPaginado(queryString, pag);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<FuncionarioExterno> consultarFuncionarioExternoPorFiltroNoAsociadoALaAudiencia(
			FuncionarioExterno filtro, Audiencia audiencia) {

		if (filtro == null || audiencia == null
				|| audiencia.getAudienciaId() <= 0L) {
			return null;
		}

		StringBuffer queryString = consultarFuncionarioExternoPorFiltro(filtro);

		queryString
				.append(" AND fe.funcionarioExternoId NOT IN")
				.append("(")
				.append(" SELECT fea.funcionarioExternoAudienciaId.funcionarioExternoId")
				.append(" FROM FuncionarioExternoAudiencia fea")
				.append(" WHERE fea.funcionarioExternoAudienciaId.audienciaId ="
						+ audiencia.getAudienciaId()).append(")");

		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return super.ejecutarQueryPaginado(queryString, pag);
	}

}
