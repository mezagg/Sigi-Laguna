package mx.gob.segob.nsjp.dao.usuario;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.DiscriminanteUIEspecializada;
import mx.gob.segob.nsjp.model.DiscriminanteUIEspecializadaId;

public interface DiscriminanteUIEspecializadaDAO extends GenericDao<DiscriminanteUIEspecializada, DiscriminanteUIEspecializadaId> {

	
	
	DiscriminanteUIEspecializada consultarUIEdeDiscriminante(Long catDiscriminanteId) throws NSJPNegocioException;
}
