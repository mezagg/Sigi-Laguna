/**
* Nombre del Programa : ActualizarCarpetaDeInvestigacionService.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 28/07/2011
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

import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;

/**
 * Definicion de servisios para actualizar la informacion de la carpeta de investigacion (Expediente)
 * que proviene de Procuraduria.
 * @version 1.0
 * @author GustavoBP
 *
 */
public interface ActualizarCarpetaDeInvestigacionService {

	/**
	 * Consultar el expediente por medio del folio de solicitud, a trav�s del 
	 * un N�mero de Expedeinte.
	 * @return
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO consultarExpedientePorFolioSolicitud(String folioSolicitud) throws NSJPNegocioException;
	
	/**
	 * Actualizar la informaci�n relacionada a un expediente.
	 * Se ingresa, datos como 
	 * 	Involucrado.
	 * 
	 * @param expedienteDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO actualizarExpedienteDeCarpetaInvestigacionDefensoria(
			ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException;
	
	/**
	 * Actualiza la informacion del expediente de poer judicial
	 * @param expedienteDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO actualizarExpedienteDePoderJudicial(ExpedienteDTO expedienteDTO,List<DelitoPersonaDTO> relDelitoPersonaDto)throws NSJPNegocioException;

	/**
	 * Actualiza el estatos de un NumeroExpediente que tiene asociada la solicitud con folio folioSolicitud y le asigna el estatus 
	 * @param folioSolicitud
	 * @param estatus
	 * @throws NSJPNegocioException
	 */
	Long actualizarExpedientePorFolioSolicitud(String folioSolicitud,
			EstatusExpediente estatus) throws NSJPNegocioException;
	
}
