package mx.gob.segob.nsjp.dao.exhorto;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Exhorto;

public interface ExhortoDAO extends GenericDao<Exhorto, Long> {
	
	Long registrarExhorto(Exhorto exhorto);
	
	Exhorto consultarExhorto(Long exhorto);
		
	List <Exhorto> consultarExhortos(String idExpediente);
	
}
