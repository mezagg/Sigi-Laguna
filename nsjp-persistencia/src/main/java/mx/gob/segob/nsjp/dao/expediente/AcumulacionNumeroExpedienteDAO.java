package mx.gob.segob.nsjp.dao.expediente;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.AcumulacionNumeroExpediente;

public interface AcumulacionNumeroExpedienteDAO extends GenericDao<AcumulacionNumeroExpediente, Long> {

	public List<AcumulacionNumeroExpediente> consultaAcumulacion(Long numeroExpedienteId)throws NSJPNegocioException;
	
	
}
