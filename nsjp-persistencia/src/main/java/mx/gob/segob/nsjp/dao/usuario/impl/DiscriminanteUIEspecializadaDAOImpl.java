package mx.gob.segob.nsjp.dao.usuario.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.usuario.DiscriminanteUIEspecializadaDAO;
import mx.gob.segob.nsjp.model.DiscriminanteUIEspecializada;
import mx.gob.segob.nsjp.model.DiscriminanteUIEspecializadaId;
import mx.gob.segob.nsjp.model.Rol;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class DiscriminanteUIEspecializadaDAOImpl extends GenericDaoHibernateImpl<DiscriminanteUIEspecializada, DiscriminanteUIEspecializadaId> implements DiscriminanteUIEspecializadaDAO {
	

	@Override
	public DiscriminanteUIEspecializada consultarUIEdeDiscriminante(
			Long catDiscriminanteId) throws NSJPNegocioException {
		DiscriminanteUIEspecializada discriminanteUIEspecializada = null;
		StringBuffer queryString = new StringBuffer();
		Query query = null;
		if (catDiscriminanteId != null) {
			queryString.append(" FROM DiscriminanteUIEspecializada duie WHERE duie.id.catDiscriminanteId = '")
						.append(catDiscriminanteId).append("'");

		}
		query = super.getSession().createQuery(queryString.toString());
		discriminanteUIEspecializada=(DiscriminanteUIEspecializada)query.uniqueResult();
			
		return discriminanteUIEspecializada;
	}

}
