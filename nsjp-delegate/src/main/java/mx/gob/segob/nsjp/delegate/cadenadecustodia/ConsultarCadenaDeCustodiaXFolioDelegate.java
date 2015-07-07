/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.segob.nsjp.delegate.cadenadecustodia;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.cadenadecustoria.CadenaDeCustodiaDTO;

/**
 *
 * @author sawbona
 */
public interface ConsultarCadenaDeCustodiaXFolioDelegate {

    /**
     * Operaci�n que realiza la funcionalidad de consultar la cadena de custodia
     * asociada al folio
     * Los datos que consulta son:
     * - N�mero de la evidencia 
     * - Informaci�n de la evidencia
     * - �ltimo eslab�n asociado
     * - N�mero de eslab�n
     * - Tipo de eslab�n
     * - Almac�n
     * Devuelve 
     * @param folio El folio de la cadena de custodia.
     * @return Un objeto de tipo CadenaDeCustodiaDTO con la informacion
     * mencionada, en caso que el folio buscado no existaregresa NULL.
     * @throws NSJPNegocioException 
     */
    CadenaDeCustodiaDTO consultarCadenaDeCustodiaXFolio(String folio) throws NSJPNegocioException;

}
