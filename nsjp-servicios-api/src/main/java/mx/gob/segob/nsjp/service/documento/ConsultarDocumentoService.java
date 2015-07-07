/**
* Nombre del Programa : ConsultarDocumentoService.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 2 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato del servicio para realizar las consultas de Documento
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
package mx.gob.segob.nsjp.service.documento;

import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.enums.documento.TipoForma;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoIntegracionDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.dto.documento.RelacionDocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudMandamientoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Contrato del servicio para realizar las consultas de Documento.
 * @version 1.0
 * @author cesarAgutin
 *
 */
public interface ConsultarDocumentoService {

	/**
	 * Consulta los documentos y obtiene los documentos que estan asociados a un expediente 
	 * y al area del usuario.
	 * @param expedienteDTO
	 * @param usuarioDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<DocumentoDTO> consultarDocumentosExpediente (ExpedienteDTO expedienteDTO, UsuarioDTO usuarioDTO) throws NSJPNegocioException;
	
	/**
		 * Consulta los documentos y obtiene los documentos que estan asociados a un expediente
		 * @param expedienteDTO
		 * @param usuarioDTO
		 * @return Lista de documentos con los siguientes valores
		 * - id Documento
		 * - Tipo documento
		 * - Nombre documento
		 * - Fecha creación
		 * @throws NSJPNegocioException
		 */
		List<DocumentoDTO> consultarDocumentosExpediente(ExpedienteDTO expedienteDTO)
		throws NSJPNegocioException;

	/**
	* Consulta los documentos que tienen unba forma del tipo de forma enviado, 
	* relacionados a una Audiencia. 
	* @author cesarAgustin
	* @param audienciaDTO
	* 		-Identificador de la Audiencia <li>id<li>
	* @return Lista de documentos relacionados a la Audiencia
	* @throws NSJPNegocioException
	*/	
	List<DocumentoDTO> consultarDocumentosAudienciaByTipoForma(
			AudienciaDTO audienciaDTO, TipoForma tipoForma) throws NSJPNegocioException;
	
    /**
     * Consulta los Documentos que estén asociados a un expediente y  Usuario de Reinsercion Social
     * 
     * @param usuario
     *            El usuario del que se consultan sus documentos
     * @param documento
     *            Los datos del documento solicitado, por default el NumeroExpediente_id.
     * @return El listado de documentos asociados al Usuario. Si el usuario no
     *         existe en la base de datos o si no tiene documentos asociados, se
     *         regresa la lista vacia.
     */    
	List<DocumentoDTO> consultarDocumentosReinsercionSocial(FuncionarioDTO funcionarioDTO, DocumentoDTO documentoDTO) throws NSJPNegocioException;	
	
	/**
	* Consulta los documentos relacionados a una Audiencia. 
	* @author AlejandroGA
	* @param audienciaDTO
	* 		-Identificador de la Audiencia <li>id<li>
	* @return Lista de documentos relacionados a la Audiencia
	* @throws NSJPNegocioException
	*/	
	List<DocumentoDTO> consultarDocumentosAudiencia(
			AudienciaDTO audienciaDTO) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de un documento en base a su Identificador.
	 * @param idDocumento - Identificador del documento a consultar.
	 * @return DocumentoDTO - DTO con la informaci&oacute;n asociada al documento en BD.
	 */
	DocumentoDTO consultarDocumentoXId(Long idDocumento);
	
	/**
	 * Consulta los documentos de un expediente por numeroExpediente y tipo del documento, 
	 * Si el tipo de documento es adjunto, entonces es obligatorio el mandamientoId
	 * @param expedienteDto
	 * @param tipoForma
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<DocumentoDTO> consultarDocumentosDeMandamientoJudicialPorExpediente(ExpedienteDTO expedienteDto,MandamientoDTO mandamientoDto,Long tipoDocumento) throws NSJPNegocioException;

	/**
	 * Consulta los documentos de un expediente por numeroExpediente y tipo del documento, 
	 * Si el tipo de documento es adjunto, entonces es obligatorio la medidaCautelarId(documentoId)
	 * @param expedienteDto
	 * @param tipoForma
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<DocumentoDTO> consultarDocumentosDeMedidasCautelaresPorExpedienteYTipo(
			ExpedienteDTO expedienteDto, MedidaCautelarDTO medidaCautelarDTO,
			Long tipoDocumento) throws NSJPNegocioException;
	
	
	/**
	 * Permite consultar documentos en base aun filtro
	 * @param filtro con los datos a discriminar
	 * @return Documento encontrado
	 * @throws NSJPNegocioException 
	 */
	
	DocumentoDTO consultarDocumentoFiltro(DocumentoDTO filtroDTO) throws NSJPNegocioException;	
	
	/**
	 * 
	 * @param filtroDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	DocumentoDTO consultarUltimoDocumentoPorActividadYExpedienteId(DocumentoDTO filtroDTO) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de los documentos de integraci&oacute;n 
	 * que han sido asociados con un expediente particular, se regresa dentro de un mapa
	 * en donde la llave corresponde con el identificador del tipo de documento asociado
	 * al expediente.
	 * @param expedienteDTO - Expediente del cual se van a obtener los documentos de 
	 * 						  integraci&oacute;n asociados.
	 * @return Map<Long,DocumentoDTO> - Mapa en donde se regresan los documentos asociados
	 * 									al expediente y que forman parte de los documentos
	 * 									para integrar el procedimiento de ejecuci&oacute;n
	 * @throws NSJPNegocioException - En el caso de que no se pasen los par&aacute;metros 
	 * 								  suficientes para llevar a cabo la consulta.
	 */
	public Map<Long, DocumentoDTO> consultarDocumentosIntegracionXExpediente(ExpedienteDTO expedienteDTO) throws NSJPNegocioException;
	
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de los documentos de integraci&oacute;n 
	 * que se encuentran registrados dentro de la base de datos.
	 * @param campoOrdenamiento - Cadena con el nombre del campo a partir del cual se 
	 * 							  ordenar&aacute;n los registros regresados por la consulta.
	 * @param ascendente - Bandera que indica si el ordenamiento ser&aacute; ascendente, en 
	 * 					   el caso de que venga en false, el ordenamiento corresponder&aacute;
	 * 					   con descendente.
	 * @return List<DocumentoIntegracionDTO> - Lista con todos los documentosIntegracion que
	 * 										   se encuentran en la base de datos.
	 */
	public List<DocumentoIntegracionDTO> consultarDocumentosIntegracion(String campoOrdenamiento, boolean ascendente);
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de las relaciones de documentos a 
	 * partir del documento principal.
	 * 
	 * @param doc - <code>Documento</code> que contiene la informaci&oacute;n del 
	 * 				documento principal a partir del cual se filtrar&aacute; la 
	 * 				consulta.
	 * @return List<RelacionDocumento> - Lista de relaciones documento que concuerdan 
	 * 									 con los par&aacute;metros ingresados como 
	 * 									 filtro. 
	 * @throws NSJPNegocioException - En el caso de que no se ingresen los 
	 * 								  par&aacute;metros indispensables.
	 */
	public List<RelacionDocumentoDTO> consultarRelacionesPorDocPrincipal(DocumentoDTO doc) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de las solicitudes mandamiento que se encuentran
	 * asociadas a un funcionario y que cumplen con ciertos criterios de b&uacute;squeda como los
	 * estatus de la solicitud, los tipos de mandamiento y los tipos de solicitud. 
	 * 
	 * @param destinatario - Funcionario destinatario al cual se envi&oacute; la solicitud.
	 * @param estatusSolicitud - Estatus bajo el cual se van a filtrar las solicitudes.
	 * @param tipoMandamiento - Tipos de mandamientos que se encuentran asociados con las 
	 * 							solicitudes.
	 * @param tipoSolicitud - Tipos de solicitudes con las cuales se va a filtrar la consulta.
	 * @return List<SolicitudMandamiento> - Lista de <code>SolicitudMandamiento</code> con aquellas
	 * 										Solicitudes de Mandamiento que cumplieron con los 
	 * 										criterios de b&uacute;squeda.
	 * @throws NSJPNegocioException - En el caso de que no se ingresen todos los par&aacute;metros
	 * 								  requeridos.
	 */
	public List<SolicitudMandamientoDTO> consultarSolicitudesMandamientoPorDestinatario(FuncionarioDTO destinatario, 
			List<ValorDTO> estatusSolicitud, List<ValorDTO> tipoMandamiento, 
			List<ValorDTO> tipoSolicitud) throws NSJPNegocioException;

	/**
	 * valida si existe algun documento con cierto tipo actividad en un expediente
	 * @param expedienteId
	 * @param tipoActividad
	 * @return boolean
	 * @throws NSJPNegocioException
	 */
	public Boolean existeDocumentoPorTipoActividadPorExpedienteId(Long expedienteId,
			Long tipoActividad) throws NSJPNegocioException;
}
