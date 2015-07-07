package mx.gob.segob.nsjp.service.infra;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.model.MedidaAlterna;
/**
 * Cliente para conectarse a los web services de SSP.
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author vaguirre
 *
 */
public interface SSPClienteService {
    /**
     * Envia la Medida Cuatelar a SSP.
     * @param input
     * @throws NSJPNegocioException
     */
	
	/**
	 * Envia la Medida Alterna a SSP.
	 * @param input
	 * @throws NSJPNegocioException
	 */
	public void enviarMedidaAlterna(MedidaAlterna input)throws NSJPNegocioException;
	
	/**
	 *  Envia el mandamiento judicial a SSP.
	 * @param input
	 * @throws NSJPNegocioException
	 */
	public void enviarMandamiento(MandamientoDTO input)throws NSJPNegocioException;
	
}
