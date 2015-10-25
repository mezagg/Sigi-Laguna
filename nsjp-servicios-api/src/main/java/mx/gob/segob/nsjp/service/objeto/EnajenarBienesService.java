/**
 * Nombre del Programa  : ConsultarObjetosAudienciaService.java
 * Autor                : Adriana Sánchez
 * Compania             : Draconiantech
 * Proyecto             : NSJP                    Fecha: 19 Oct 2015
 * Marca de cambio      : N/A
 * Descripcion General  : Consulta los bienes por enajenar
 * Programa Dependiente : N/A
 * Programa Subsecuente : N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                :N/A
 * Compania             :N/A
 * Proyecto             :N/A                                 Fecha: N/A
 * Modificacion         :N/A
 *------------------------------------------------------------------------------
 */

package mx.gob.segob.nsjp.service.objeto;

import java.util.Date;
import java.util.List;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;

public interface EnajenarBienesService {

	List<ObjetoDTO> consultarBienesPorEnajenar(Date fecha,Integer diasParaEnajenar);
        void enajenarBienes(String idsBienes);
}
