/**
 * Nombre del Programa : DocumentoDAO.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 18 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Objeto de Acceso a Datos para el documento.
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
package mx.gob.segob.nsjp.dao.documento;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.model.Valor;

/**
 * Objeto de Acceso a Datos para el documento.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public interface DocumentoDAO extends GenericDao<Documento, Long> {

    /**
     * Obtiene los ducumentos de un expediente que ya cuentan con un archivo
     * digital generado
     * 
     * @param expedienteId
     * @param idFuncionario Consulta los documentos del funcionario
     * @return lista de documentos recuperados
     */
    public List<Documento> consultarDocumentoPorExpediente(Long expedienteId, Long idFuncionario, List<Long> idsJeraruqiasOrganizacionales,Long idAreaActual);

    /**
     * Operación que realiza la funcionalidad de consultar los documentos
     * asociados a un expediente y al tipo de documento
     * 
     * @param expedienteDTO
     *            : El número de expediente
     * @param tipoDocumento
     *            : El tipo de documento a consultar.
     * @return Regresa un listado de objetos de tipo Documento, en caso
     *         contrario regresa null.
     */
    public List<Documento> consultarDocumentosXExpedienteYTipoDocumento(
            Long numeroExpedienteId, Long tipoDocumento);

    /**
     * Consulta los Documentos que estén asociados a un Usuario
     * 
     * @param usuario
     *            El usuario del que se consultan sus documentos
     * @param tipoDocumento
     *            El tipo de documento por el que se filtraran los documentos
     *            para la consultar. (Dictamen / Informe) asociados.
     * @return El listado de documentos asociados al Usuario. Si el usuario no
     *         existe en la base de datos o si no tiene documentos asociados, se
     *         regresa la lista vacia.
     */
    List<Documento> consultarDocumentosPorUsuario(Usuario usuario,
            Long tipoDocumento);

    /**
     * Consulta un documento del tipo {@code tipoDocumento} asociado al numero
     * de expediente indicado por {@code expediente.numeroExpediente}.
     * 
     * @param expedienteDto
     *            Indica el numero de expediente del que buscamos su documento
     *            asociado.
     * @param tipoDocumento
     *            El tipo del documento que estamos buscando.
     * @return El documento asociado a los parametros de busqueda o {@code null}
     *         en caso de no existir un documento asociado del tipo indicado.
     */
    Documento consultarDocumentoXExpediente(Expediente expediente,
            Long tipoDocumento);
    /**
     * Obtiene los ducumentos de un expediente que ya cuentan con un archivo
     * digital generado
     * 
     * @param numeroExpedienteId
     *            ID del numero de expediente para el cual se desean consultar
     *            los documentos
     * @return lista de documentos recuperados
     */
    List<Documento> consultarDocumentosPorNumeroExpedienteId(
            Long numeroExpedienteId);

    /**
     * Consulta los documentos que tienen unba forma del tipo de forma enviado,
     * relacionados a una Audiencia.
     * 
     * @author cesarAgustin
     * @param id
     * @param valorId
     * @return
     */
    public List<Documento> consultarDocumentosAudienciaByTipoForma(Long id,
            Long valorId);

    /**
     * Obtiene los ducumentos de un expediente que ya cuentan con un archivo
     * digital generado
     * 
     * @param documentoId
     * @return Un documento:<br>
     *         <code>Documento(d.documentoId, d.nombreDocumento, d.forma.formaId, d.tipoDocumento.valorId, d.tipoDocumento.valor, d.fechaCreacion</code>
     */
    public Documento consultarDocumentoPorDocumentoIdLigero(Long documentoId);

    public Documento consultarDocumentoPorId(Long documentoId);

	public List<Documento> consultarDocumentosXTipoDocumento(Long idTipoDocumento);

	/**
	 * Obtiene el &uacute;ltimo folio documento con el siguiente formato:
	 * 
	 * MonogramaInstitucion/AnioConsecutivo
	 * 
	 * Pe:
	 * 
	 * PJ/2013000001
	 * 
	 * El monograma de la institucion consta de 2 digitos, el Consecutivo 5 digitos
	 * 
	 * @param institucion, institucion de la cual se obtiene el monograma
	 * @return folio de 5 digitos, que es el M&aacute;ximo obtenido
	 * @throws NSJPNegocioException
	 */
	public String obtenerUltimoFolioDocumento(ConfInstitucion institucion) throws NSJPNegocioException;
	
	/**
	 * 
	 * @param expedienteId
	 * @param tipoforma
	 * @return
	 */
	public List<Documento> consultarDocumentosByExpedienteIdYForma(Long expedienteId, Formas tipoforma);
	/**
	 * @param archivoDigitalId
	 * 
	 * @return la lista de documentos que pertenece a este Id
	 */	
    public Documento consultarDocumentoPorArchivoDigital(Long archivoDigitalId);

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
	List<Documento> consultarDocumentosReinsercionSocial(Funcionario funcionario, 
    		Documento documento,
    		NumeroExpediente numeroExpediente)  throws NSJPNegocioException;

	/**
	 * Consulta documento por 
	 * @param folio - folio generado del documento
	 * @param institucionOrigen -  instituci&oacute;n donde fue generado o creado
	 * @return Lista de documentos que cumplan con los filtros de busqueda.
	 * @throws NSJPNegocioException
	 */
	List<Documento> consultarDocumentosPorFolioInstitucion(String folio, Long institucionOrigen);
	
	/**
	 * Permite consultar documentos en base al tipo sin importar si existe actividad
	 * Se usa para consultar medidas cautelares.
	 * @param numExpId
	 * @param tipoDocumento
	 * @return
	 */
	public List<Documento> consultarDocumentosSinActividadXExpedienteYTipoDocumento(
            Long numExpId, Long tipoDocumento);

	
	/**
	 * Permite consultar documentos en base aun filtro
	 * @param filtro con los datos a discriminar
	 * @return Documento encontrado
	 * @throws NSJPNegocioException
	 */
	
	Documento consultarDocumentoFiltro(Documento filtro) throws NSJPNegocioException;
	
	/**
	 * 
	 */
	public List<Documento>consultarDocumentosPorTipoActividadYNumExpedienteId(Long numExpId,Long tipoActividad) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de los documentos asociados a un expediente
	 * y que corresponden con los tipos pasados dentro de la lista de tipos de documento
	 * pasada como par&aacute;metros.
	 * @param expediente - Expediente del cu&aacute;l se van a obtener los documentos asociados.
	 * @param tiposDocumento - List<Valor> con los tipos de documento sobre los cuales se va 
	 * 						   a realizar la consulta.
	 * @return List<Documento> - List<Documento> con los documentos que cumplieron con los filtros
	 * 							 pasados como par&aacute;metros.
	 * @throws NSJPNegocioException - En el caso de que no se env&iacute;en todos los par&aacute;metros
	 * 								  obligatorios.
	 */
	public List<Documento> consultarDocumentosPorExpedienteYTipos(Expediente expediente,
			List<Valor> tiposDocumento) throws NSJPNegocioException;
	

    /**
     * M&eacute;todo que lleva a cabo la consulta de los documentos que se encuentran relacionados con 
     * una lista de identificadores de archivos digitales. 
     * @param lstArchivoDigitalId - Lista de identificadores de archivos digitales de los cuales se van
     * 								a consultar los documentos relacionados.
     * @return List<Documento> - Lista con los documentos asociados a los archivos digitales.
     * @throws NSJPNegocioException - En el caso de que la lista de identificadores de archivos digitales
     * 								  sea nula o se encuentre vac&iacute;a.
     */
    public List<Documento> consultarDocumentosPorArchivosDigitales(List<Long> lstArchivoDigitalId) throws NSJPNegocioException;
    
    /**
     * consulta los documentos por el tipo de actividad y el expediente id
     * @param expedienteId
     * @param tipoActividad 
     * @return List<Documento> - Lista con los documentos del expediente, que sean de este tipo actividad
     * @throws NSJPNegocioException
     */
    public List<Documento> consultarDocumentosPorTipoActividadYExpedienteId(Long expedienteId, Long tipoActividad) throws NSJPNegocioException;
	
}
