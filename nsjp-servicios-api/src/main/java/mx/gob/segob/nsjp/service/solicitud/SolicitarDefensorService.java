package mx.gob.segob.nsjp.service.solicitud;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.GuardadoDefinitivoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;

/**
 * Nombre del Programa : SolicitarDefensor.java
 * Autor                            : cesarAgustin
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 19 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato del servicio para realizar la solicitud de un defensor
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

/**
 * Contrato del servicio para realizar la solicitud de un defensor.
 * @version 1.0
 * @author cesarAgustin
 *
 */
public interface SolicitarDefensorService {

	/**
	 * 
	 * @param solicitudDefensorDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public SolicitudDefensorDTO solicitarDefensor(ExpedienteDTO expedienteDTO,DocumentoDTO docSolicitudDto)
			throws NSJPNegocioException;

	/**
	 * Consulta las solicitudes defensoria que fueron asignadas a un funcionario que es enviado como parametro.
	 * @author cesarAgustin	
	 * @param usuarioDTO
	 * @return Lista de solicitud asignadas al funcionario
	 * @throws NSJPNegocioException
	 */
	public List<SolicitudDefensorDTO> consultarSolDefensorAsignadas(FuncionarioDTO funcionarioDTO) throws NSJPNegocioException;

	/**
	 * Elimina la asignacion del funcionario enviado como parametro del expediente tambien enviado.
	 * @author cesarAgustin
	 * @param expedienteDTO
	 * @param funcionarioDTO
	 * @throws NSJPNegocioException
	 */
	public SolicitudDefensorDTO reasignarDefensorAExpediente(ExpedienteDTO expedienteDTO,
			FuncionarioDTO funcionarioDTO) throws NSJPNegocioException;
	
	/**
	 * Obtiene las victimas y los denunciantes a partir de las relaciones delito
	 * persona que se obtienen de la lista de probables responsables obtenida
	 * 
	 * @param listaProbRespDTO
	 * @return
	 */
	List<InvolucradoDTO> obtenerVicimasYDenunciantesDeRelacionDelitoPersona(
			List<InvolucradoDTO> listaProbRespDTO, ExpedienteDTO inputExpDTO)
			throws NSJPNegocioException;

	/**
	 * Registra una solicitud(ciudadana) de defensor desde defensor&iacute;a
	 * a defensor&iacute;a 
	 * @param guardadoDefinitivoDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public SolicitudDefensorDTO solicitarDefensorDesdeDefensoria(
			GuardadoDefinitivoDTO guardadoDefinitivoDTO)
			throws NSJPNegocioException;
}
