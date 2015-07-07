/**
 * Nombre del Programa 		: AdministrarMandamientoJudicialService.java
 * Autor 					: AlejandroGA
 * Compania 				: Ultrasist
 * Proyecto 				: nsjp-modelo 					Fecha: 05/06/2013
 * Marca de cambio 			: N/A
 * Descripcion General 		: Clase Service para lo relacionado con mandamiento judicial
 * Programa Dependiente 	: N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion 		: N/A
 * Dias de ejecucion 		: N/A 								Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor 					: N/A
 * Compania 				: N/A
 * Proyecto 				: N/A 								Fecha: N/A
 * Modificacion 			: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.mandamiento;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoDTO;
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public interface AdministrarMandamientoJudicialService {
	
	
	/**
	 * M&eacutetodo gen&eacute;rico para generar un mandamientoJudicial tanto en
	 * PJ como en PG, genera todo lo relacionado con el mandamiento, como:
	 * mandamiento-persona mandamiento-delitoPersona
	 * 
	 * @param mandamientoDTO
	 *            , datos del mandamiento que ser&aacute; generado
	 * @return el mandamiento judicial generado con id
	 * @throws NSJPNegocioException, en caso de excepcion
	 */
	MandamientoDTO generarMandamientoJudicial(MandamientoDTO mandamientoDTO)
			throws NSJPNegocioException;

	/**
	 * M&eacutetodo gen&eacute;rico para consultar mandamiento judiciales por
	 * filtro, se puede realizar b&uacute;squeda por: n&uacute;mero de
	 * Expediente, Por fecha de creaci&oacute;n y Estatus del mandamiento, por
	 * funcionario asociado al expediente
	 * @param filtroMandamientoDTO, con los par&aacute;metros de b&uacute;squeda
	 * @return lista de mandamientos que superaron el filtro
	 * @throws NSJPNegocioException, en caso de excepcio
	 */
	List<MandamientoDTO> consultarMandamientoPorFiltro(
			FiltroMandamientoDTO filtroMandamientoDTO)
			throws NSJPNegocioException;
	
	/**
	 * Consulta los datos de un mandamiento judicial con base a su id
	 * @param mandamientoJudicialId, requerido para la consulta
	 * @return mandamiento judicial con el id de busqueda
	 * @throws NSJPNegocioException, en caso de excepcio
	 */
	MandamientoDTO consultarMandamientoPorId(Long mandamientoJudicialId)
			throws NSJPNegocioException;
	
	
	/**
	 * Consulta las relaciones Mandamiento-Persona por filtro
	 * @param filtroMandamientoPersonaDTO, filtro por el cual seran buscadas la relaciones
	 * @return lista de mandamientos persona que superaron el filtro
	 * @throws NSJPNegocioException, en caso de excepcio
	 */
	List<MandamientoPersonaDTO> consultarMandamientosPersonaPorFiltro(
			FiltroMandamientoPersonaDTO filtroMandamientoPersonaDTO)
			throws NSJPNegocioException;
	
	/**
	 *  Actualiza la informaci&oacute;n de una lista de mandamientos persona y
	 * genera el documento de cambio de estatus de los mandamientos persona
	 * @param listaMandamientosPersona, a actualizar
	 * @param usuarioDTO, usuario que realiza el cambio
	 * @param mandamientoDTO, madamiento a actualizar estatus
	 * @param documentoDTO, remoto en el caso del envio del documento
	 * @return idArchivoDigitel generado
	 * @throws NSJPNegocioException, en caso de excepcion
	 */
	Long actualizarMandamientoPersona(
			List<MandamientoPersonaDTO> listaMandamientosPersona,
			UsuarioDTO usuarioDTO, MandamientoDTO mandamientoDTO,
			DocumentoDTO documentoDTO) throws NSJPNegocioException;
	
	
	/**
	 * Env&iacute; un mandamiento judicial, que debe existir en BD
	 * @param mandamientoDTO, con el id del mandamiento judicial a envíar
	 * @throws NSJPNegocioException, si ocurre alg&uacute;n error 
	 */
	void enviarMandamientoJudicial(MandamientoDTO mandamientoDTO) throws NSJPNegocioException;
	
	
	/**
	 * M&eacute;todo que realiza las siguiente operaciones:
	 * 
	 * si, esCalcularEstatus verdadero: calcula el estatus del madamiento en
	 * base a los estatus de los mandamientos persona siguiendo las siguientes
	 * reglas: 
	 * 
	 * 1) Si todos los mandamientosPersona estan en estatus de
	 * NO_ATENDIDO, el estatus del mandamiento ser&aacute;: NO_ATENDIDO
	 * 
	 * 2) Si todos los mandamientosPersona estan en estatus de
	 * ATENDIDO,CONCLUIDO O CANCELADO el estatus del mandamiento ser&aacute;:
	 * ATENDIDO
	 * 
	 * 3) Si todos los mandamientosPersona estan en estatus de EN_PROCESO, o en
	 * diferentes GRUPOS de estatus el estatus del mandamiento ser&aacute;:
	 * EN_PROCESO
	 * 
	 * si , esCalcularEstatus es false o null: se hace update sobre el estatus
	 * que se pase como par&aacute;metro.
	 * 
	 * @param mandamientoDTO
	 *            , indispensable mandamientoId en ambos casos
	 * @param esCalcularEstatus
	 *            , indica si el estatus es calculado por el servicio o se setea
	 *            el estatus que se pase como par&aacute;metro.
	 * @throws NSJPNegocioException
	 *             , si ocurre alg&uacute;n error
	 */
	void actualizarEstatusMandamiento(MandamientoDTO mandamientoDTO,
			Boolean esCalcularEstatus) throws NSJPNegocioException;
	
	
	/**
	 * M&eacute;todo para el env&iacute;o de un documento de cambio de estatus
	 * 
	 * @param mandamientoDTO
	 *            , mandamiento al que se le actualizar&aacute; el estatus
	 * @param documentoDTO
	 *            , documento de cambio de estatus que ser&aacute; enviado
	 * @throws NSJPNegocioException, en caso de excepcion
	 */
	void enviarDocumentoCambioEstatusMandamiento(MandamientoDTO mandamientoDTO,
			DocumentoDTO documentoDTO) throws NSJPNegocioException;
	
	/**
	 * Adjunta un documento a un mandamiento judicial, sin crear actividad,
	 * directamente en mandamiento adjuntos y sin adjuntarlo al expediente
	 * 
	 * @param documentoDTO, documento que ser&aacute; adjuntado
	 * @param mandamientoJudicialDTO, mandamiento al que ser&aacute; relacionado el documento
	 * @param tipoDocumento, tipo de documento adjuntado. *.pdf o *.jpg
	 * @return id del archivo digital adjunto, null en caso de error
	 * @throws NSJPNegocioException, en caso de alguna excepcion
	 */
	Long adjuntarDocumentoAMandamientoJudicial(DocumentoDTO documentoDTO,
			MandamientoDTO mandamientoJudicialDTO, TipoDocumento tipoDocumento)
			throws NSJPNegocioException;

}
