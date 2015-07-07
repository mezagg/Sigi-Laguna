package mx.gob.segob.nsjp.service.solicitud;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;

public interface RegistrarSolicitudDefensorEncargadoSalaService {

	/**
     * Servicio encargado de enviar una solicitud de Defensor para una lista de imputado
     * previamente seleccionados, los cuales se encuentran en
     * la audiencia representada por <code>audiencia</code>.
     * 
     * @param audiencia
     *            Audiencia a la cual se debe presentar el Defensor una vez
     *            asignado.
     * @param listaProbablesResponsables
     *            Lista de imputados que necesita el Defensor.
     * @return
     * @throws NSJPNegocioException
     */
    SolicitudDefensorDTO registrarSolicitudDefensorPJ(AudienciaDTO audienciaDTO,
            List<InvolucradoDTO> listaProbablesResponsables) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que consulta los imputados que tengan solicitud de defensor
	 * y, opcionalmente, pertenezcan a una audiencia en particular.
	 * 
	 * No requiere manejo de Excepciones ya que es posible mandar los parametros
	 * nulos. En este caso regresa la lista de id de todas las solicitudes.
	 * 
	 * @param audienciaDTO
	 *            - La audiencia a partir de la cual se van a consultar las
	 *            solicitudes de defensor.
	 * @param imputadosId
	 *            - Lista de imputados que se consultara, para saber si ya
	 *            cuentan con almenos una solicitud.
	 * @return List<Long> - Lista de Id de los involucrados que ya cuentan con
	 *         solicitudes de defensor
	 * @throws NSJPNegocioException
	 */
	public List<Long> obtenerInvolucradosIdConSolicitudDefensor(
			AudienciaDTO audienciaDTO, List<Long> imputadosId);
	
}
