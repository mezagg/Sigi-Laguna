package mx.gob.segob.nsjp.delegate.expediente;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.AcumulacionNumeroExpedienteDTO;



public interface AcumulacionCausaDelegate {
	
	public List<AcumulacionNumeroExpedienteDTO> consultarAcumulacionCausa(String numeroExpediente)
            throws NSJPNegocioException;
	 public String crearAcumulacion(AcumulacionNumeroExpedienteDTO acumulacion) throws NSJPNegocioException ;
}
