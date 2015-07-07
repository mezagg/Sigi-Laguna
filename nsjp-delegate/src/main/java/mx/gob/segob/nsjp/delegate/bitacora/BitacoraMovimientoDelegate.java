/**
* Nombre del Programa : BitacoraMovimientoDelegate.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 31/08/2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
* Programa Dependiente  :N/A
* Programa Subsecuente :N/A
* Cond. de ejecucion        :N/A
* Dias de ejecucion          :N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                       :N/A
* Compania               :N/A
* Proyecto                 :N/A                                 Fecha: N/A
* Modificacion           :N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.delegate.bitacora;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.bitacora.RegistroBitacoraDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;

/**
 * Exposici�n de servicios referentes a la Bitacora de Movimientos IAM
 * @version 1.0
 * @author GustavoBP
 */
public interface BitacoraMovimientoDelegate {

	/**
     * 
     * @param expInput
     *            requerido <b>numeroExpedienteId</b>.
     * @return
     * @throws NSJPNegocioException
     */
    List<RegistroBitacoraDTO> consultarRegistrosByExpediente(
            ExpedienteDTO expInput) throws NSJPNegocioException;
}
