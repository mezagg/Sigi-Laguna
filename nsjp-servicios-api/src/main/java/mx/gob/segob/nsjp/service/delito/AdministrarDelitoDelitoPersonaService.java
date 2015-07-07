/**
 * Nombre del Programa : EliminarDelitoService.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 16/01/2013
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato de servicio para elminar delitos y dependencia con DelitoPersona.
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
package mx.gob.segob.nsjp.service.delito;

import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;

/**
 * Contrato de servicio para elminar delitos y dependencia con DelitoPersona.
 * 
 * @version 1.0
 * @author GustavoBP
 * 
 */
public interface AdministrarDelitoDelitoPersonaService {

	/**
	 * Servicio que se encarga de consultar los delitos asociados a un
	 * expediente, para eliminarlos. Asi mismo, se hace la eliminacion en
	 * cascada de las relaciones delito Persona.
	 * 
	 * Es ocupado en la actualizacion de la carpeta de investigacion.
	 * 
	 * @param expedienteId
	 * @throws NSJPNegocioException
	 */
	void eliminarDelitosExpediente(Long expedienteId)
			throws NSJPNegocioException;

	/**
	 * Metodo utilizado para registar los delitos de un expedientes Buscando los
	 * folios de los involucrados previamente registrados y asociados al
	 * expediente. Asi mismo, que el delito se encuentre registrado en el
	 * catalodo con la clave interinstitucional. En caso de que no se
	 * identifique el delito por la clave, no se registra y se continua con elo
	 * siguiente registro. Caso de que falte alguno de los datos requeridos,
	 * para crear la relacion Delito-Imputado-Victima, haga falta, no se
	 * registra y se continua con el siguiente registro.
	 * 
	 * @param expedienteDTO
	 * @param relacionesDelitoPersonaDTO
	 * @param folioIdInvolucrados
	 * @throws NSJPNegocioException
	 */
	public void registrarDelitosRelacionDelitoExpediente(
			ExpedienteDTO expedienteDTO,
			List<DelitoPersonaDTO> relacionesDelitoPersonaDTO,
			HashMap<String, Long> folioIdInvolucrados)
			throws NSJPNegocioException;

}
