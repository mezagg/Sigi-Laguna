/**
 * 
 */
package mx.gob.segob.nsjp.service.visitante;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.visitante.VisitanteDTO;

/**
 * @author LuisMG
 *
 */
public interface VisitanteService {
	
	
	/**
	 * Si no existe un visitante lo registra y si ya existe lo actualiza
	 * @param visitante
	 * @return
	 * @throws NSJPNegocioException
	 */
    public boolean registrarVisitante(VisitanteDTO visitante) throws NSJPNegocioException;
    
    /**
     * Elimina una visita dada una IP y regresa True en caso de existir el registro o false en caso de que no exista el registro
     * @param visitante
     * @return
     * @throws NSJPNegocioException
     */
    public boolean eliminarVisitantePorIP (VisitanteDTO visitante)throws NSJPNegocioException;
    
    /**
     * Consulta un visitante con base en su direcci�n IP y regresa Null en caso de que el visitante no se encuentre registrado
     * @param visitante
     * @return
     * @throws NSJPNegocioException
     */
    public VisitanteDTO consultarVisitantePorIP (VisitanteDTO visitante) throws NSJPNegocioException;
    
    /**
     * M�todo encargado de reiniciar la cuenta de intentos de una direcci�n IP registrada a cero
     * @throws NSJPNegocioException
     */
    void desbloquearVisitante()throws NSJPNegocioException;
}
