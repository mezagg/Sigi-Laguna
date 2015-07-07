package mx.gob.segob.nsjp.dao.ssp.informepolicial;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.ssp.Operativo;

public interface OperativoDAO 
extends GenericDao<Operativo,Long>{

	
	/**
	 * Consultar el Operativo asociado al IPH
	 * @param idIPH
	 * @return
	 */
	Operativo consultarOperativoPorIdIPH(Long idIPH);	
}
