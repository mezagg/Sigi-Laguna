/**
 * Nombre del Programa		: AdministrarMandamientoJudicialDelegate.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP							Fecha: 13/06/2013
 * Marca de cambio			: N/A
 * Descripcion General		: Clase delegate para lo relacionado con mandamiento judicial
 * Programa Dependiente		: N/A
 * Programa Subsecuente		: N/A
 * Cond. de ejecucion       : N/A
 * Dias de ejecucion        : N/A                           Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                    : N/A
 * Compania               	: N/A
 * Proyecto                 : N/A                           Fecha: N/A
 * Modificacion           	: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.delegate.mandamiento;

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
public interface AdministrarMandamientoJudicialDelegate {

	/**
	 * M&eacutetodo gen&eacute;rico para generar un mandamientoJudicial tanto en
	 * PJ como en PG, genera todo lo relacionado con el mandamiento, como:
	 * mandamiento-persona mandamiento-delitoPersona
	 * 
	 * @param mandamientoDTO
	 *            , datos del mandamiento que ser&aacute; generado
	 * @return el mandamiento judicial generado con id
	 * @throws NSJPNegocioException
	 *             , en caso de alguna excepcion
	 */
	public MandamientoDTO generarMandamientoJudicial(
			MandamientoDTO mandamientoDTO) throws NSJPNegocioException;

	/**
	 * M&eacutetodo gen&eacute;rico para consultar mandamiento judiciales por
	 * filtro, se puede realizar b&uacute;squeda por: n&uacute;mero de
	 * Expediente, Por fecha de creaci&oacute;n y Estatus del mandamiento, por
	 * funcionario asociado al expediente, etc.
	 * 
	 * @param filtroMandamientoDTO
	 * @return lista de mandamientos que superaron el filtro
	 * @throws NSJPNegocioException
	 *             , en caso de alguna excepcion
	 */
	public List<MandamientoDTO> consultarMandamientoPorFiltro(
			FiltroMandamientoDTO filtroMandamientoDTO)
			throws NSJPNegocioException;

	/**
	 * M&eacutetodo gen&eacute;rico, tanto para PG como para PJ, que consulta un
	 * mandamiento por su id.
	 * 
	 * @param mandamientoJudicialId
	 * @return mandamientoDTO obtenido, otro caso regresa null;
	 * @throws NSJPNegocioException
	 *             , en caso de alguna excepcion
	 */
	public MandamientoDTO consultarMandamientoPorId(Long mandamientoJudicialId)
			throws NSJPNegocioException;

	/**
	 * Consulta las relaciones Mandamiento-Persona por filtro
	 * 
	 * @param filtroMandamientoPersonaDTO
	 *            , filtro por el cual seran buscadas la relaciones
	 * @return lista de mandamientos persona que superaron el filtro
	 * @throws NSJPNegocioException
	 *             , en caso de alguna excepcion
	 */
	List<MandamientoPersonaDTO> consultarMandamientosPersonaPorFiltro(
			FiltroMandamientoPersonaDTO filtroMandamientoPersonaDTO)
			throws NSJPNegocioException;

	/**
	 * Actualiza la informaci&oacute;n de una lista de mandamientos persona y
	 * genera el documento de cambio de estatus de los mandamientos persona
	 * 
	 * @param listaMandamientosPersona
	 *            , a actualizar
	 * @param usuarioDTO
	 *            , usuario que realiza el cambio
	 * @param mandamientoDTO
	 *            , madamiento a actualizar estatus
	 * @param documentoDTO
	 *            , remoto en el caso del envio del documento
	 * @return idArchivoDigital, el id del archivo digital en caso de generarlo
	 * @throws NSJPNegocioException
	 *             , en caso de alguna excepcion
	 */
	Long actualizarMandamientoPersona(
			List<MandamientoPersonaDTO> listaMandamientosPersona,
			UsuarioDTO usuarioDTO, MandamientoDTO mandamientoDTO,
			DocumentoDTO documentoDTO) throws NSJPNegocioException;

	/**
	 * M&eacute;todo para enviar el mandamiento judicial de la insticucion PJ y
	 * PG
	 * 
	 * @param mandamientoDTO
	 * @throws NSJPNegocioException
	 *             , en caso de alguna excepcion
	 */
	public void enviarMandamientoJudicial(MandamientoDTO mandamientoDTO)
			throws NSJPNegocioException;

	/**
	 * M&eacute;todo que realiza las siguiente operaciones:
	 * 
	 * si, esCalcularEstatus verdadero: calcula el estatus del madamiento en
	 * base a los estatus de los mandamientos perosna siguiendo las siguientes
	 * reglas:
	 * 
	 * 1) Si todos los mandamientosPersona estan en estatus de NO_ATENDIDO, el
	 * estatus del mandamiento ser&aacute;: NO_ATENDIDO
	 * 
	 * 2) Si todos los mandamientosPersona estan en estatus de
	 * ATENDIDO,CONCLUIDO O CANCELADO el estatus del mandamiento ser&aacute;:
	 * ATENDIDO
	 * 
	 * 3) Si todos los mandamientosPersona estan en estatus de EN_PROCESO, o en
	 * diferentes GRUPOS de estatus el estatus del mandamiento ser&aacute;:
	 * EN_PROCESO
	 * 
	 * si , esCalcularEstatus igual a false o null: se hace update sobre el
	 * estatus que se pase como par&aacute;metro.
	 * 
	 * @param mandamientoDTO
	 *            , indispensable mandamientoId en ambos casos
	 * @param esCalcularEstatus
	 *            , indica si el estatus es calculado por el servicio o se setea
	 *            el estatus que se pase como par&aacute;metro.
	 * @throws NSJPNegocioException
	 *             , en caso de alguna excepcion
	 */
	public void actualizarEstatusMandamiento(MandamientoDTO mandamientoDTO,
			Boolean esCalcularEstatus) throws NSJPNegocioException;

	/**
	 * M&eacute;todo para el env&iacute;o de un documento de cambio de estatus
	 * entre instituciones PJ y PG
	 * 
	 * @param mandamientoDTO
	 *            , mandamiento al que se le actualizar&aacute; el estatus
	 * @param documentoDTO
	 *            , documento de cambio de estatus que ser&aacute; enviado
	 * @throws NSJPNegocioException
	 *             , en caso de alguna excepcion
	 */
	public void enviarDocumentoCambioEstatusMandamiento(
			MandamientoDTO mandamientoDTO, DocumentoDTO documentoDTO)
			throws NSJPNegocioException;

	/**
	 * Adjunta un documento a un mandamiento judicial, sin crear actividad,
	 * directamente en mandamiento adjuntos y sin adjuntarlo al expediente
	 * 
	 * @param documentoDTO
	 *            , documento que ser&aacute; adjuntado
	 * @param mandamientoJudicialDTO
	 *            , mandamiento al que sera relacionado el documento
	 * @param tipoDocumento
	 *            , tipo de documento adjuntado. *.pdf o *.jpg
	 * @return id del archivo digital adjunto, null en caso de error
	 * @throws NSJPNegocioException
	 *             , en caso de alguna excepcion
	 */
	public Long adjuntarDocumentoAMandamientoJudicial(
			DocumentoDTO documentoDTO, MandamientoDTO mandamientoJudicialDTO,
			TipoDocumento tipoDocumento) throws NSJPNegocioException;
}
