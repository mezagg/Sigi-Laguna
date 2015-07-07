package mx.gob.segob.nsjp.delegate.ssp.turnolaboral;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.ssp.turnolaboral.TurnoLaboralDTO;

/**
 * Contrato de metodos para el acceso a los servicios de Turnos Laborales.
 * @version 1.0
 * @author mgallardo
 *
 */

public interface TurnoLaboralDelegate {
    
	/**
     * Consulta el Catalogo de Turnos Laborales
     * @param 
     * @return Una lista de Turnos Laborales TurnoLaboralDTO 
     * @throws NSJPNegocioException
     */
	List<TurnoLaboralDTO> consultarCatalogoTurnoLaboral() throws NSJPNegocioException;
	
}
