/**
* Nombre del Programa : CompromisoDelegate.java
* Autor                            : vaguirre
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 13 Oct 2011
* Marca de cambio        : N/A
* Descripcion General    : Delegate para lo relacionado a compromiso periodico
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
package mx.gob.segob.nsjp.delegate.compromiso;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.medida.CompromisoPeriodicoDTO;
import mx.gob.segob.nsjp.dto.medida.FechaCompromisoDTO;
import mx.gob.segob.nsjp.dto.medida.FiltroBusquedaCompromisosDTO;
import mx.gob.segob.nsjp.dto.medida.MedidaDTO;

/**
 * Delegate para lo relacionado a compromiso periodico.
 * @version 1.0
 * @author vaguirre
 *
 */
public interface CompromisoDelegate {


    /**
     * 
     * @param medInput
     * @return
     * @throws NSJPNegocioException
     */
    CompromisoPeriodicoDTO obtenerCompromisoPeriodico(MedidaDTO medInput)
            throws NSJPNegocioException;
    /**
     * 
     * @param fechaInicio
     *            usada como inicio de un periodo o para un fecha especifica (si
     *            fechaFin es <code>null</code>)
     * @param fechaFin
     *            usada como fin de periodo
     * @param isIncumplimiento
     *            <code>true</code> para recuperar �nicamente incumplimientos,
     *            <code>false</code> para recuperar las que si se han cumplido,
     *            <code>null</code> para recuperar todas.
     * @return
     * @throws NSJPNegocioException
     */
    List<FechaCompromisoDTO> buscarFechasComprimiso(FiltroBusquedaCompromisosDTO filtro)
            throws NSJPNegocioException;

}
