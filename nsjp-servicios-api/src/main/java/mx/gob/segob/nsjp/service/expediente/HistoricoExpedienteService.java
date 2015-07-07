/**
* Nombre del Programa : HistoricoExpedienteService.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 29/06/2012
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
package mx.gob.segob.nsjp.service.expediente;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPSistemaException;
import mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public interface HistoricoExpedienteService {
	/**
	 * Servicio que consulta los Hist&oacute;ricos de un Expediente por filtro
	 * 
	 * @param historicoExpediente Filtro de b&uacute;squeda mientras el atributo sea diferente de {@code null}
	 * @return Lista de {@code HistoricoExpediente} con los resultados de la b&uacute;squeda.
	 * @throws NSJPSistemaException
	 */
	List<HistoricoExpedienteDTO> consultarHistoricoExpediente(HistoricoExpedienteDTO historicoExpedienteDTO) throws NSJPSistemaException;

}
