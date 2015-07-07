/**
 * Nombre del Programa : CargarDocumentoService.java
 * Autor                            : Emigdio Hern�ndez
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 26/05/2011
 * Marca de cambio        : N/A
 * Descripcion General    : Define el contrato para el servicio de negocio utilizado para cargar documentos
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

import java.util.Map;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;

/**
 * Define el contrato para el servicio de negocio utilizado para cargar documentos
 * @author Emigdio Hern�ndez
 *
 */
public interface CargarDocumentoService {

	
	 
	 /**
	 * Crea un nuevo documento con la plantilla del tipo de forma enviada como par�metro y llena la plantilla
	 * con los datos del expediente enviado, si ya existe un documento que ha sido guardado parcialmente
	 * correspondiente a ese expediente y con ese tipo de forma carga los datos parcialmente guardados.
	 * @param expediente Expediente para el cu�l se realiza el documento
	 * @param tipoForma Tipo de forma deseada del documento
	 * @param parametrosExtras conjunto de valores llave-valor que representan par�metros extras que no ext�n definidos en la configuraci�n
	 * de campos a reemplazar pero que sin embargo la plantilla los puede contener, se utiliza para incluir informaci�n en la plantilla
	 * que no est� directamente relacionada con un expediente como por ejemplo: solicitudes, acuses de recibo y otros documentos operativos
	 * @param mandaFormaEnConsulta - "si" -> la prioridad de consulta es a partir de la formarId, en caso contrario, el flujo normal
	 * @return Objeto de documento con el formato HTML listo para presentarse
	 */
	 DocumentoDTO cargarDocumentoPorExpedienteYForma(ExpedienteDTO expediente,FormaDTO tipoForma,Map<String, Object> parametrosExtras, String mandaFormaEnConsulta) throws NSJPNegocioException;
	 

	 /**
	  * Variante de <code>cargarDocumentoPorExpedienteYForma(ExpedienteDTO expediente,FormaDTO tipoForma,Map<String, Object> parametrosExtras)</code>
	  * que no utiliza los par�metros extras
	  * @param expediente Expediente para el cu�l se realiza el documento
	  * @param tipoForma Tipo de forma deseada del documento
	  * @return Objeto de documento con el formato HTML listo para presentarse
	  * @throws NSJPNegocioException
	  */
	 DocumentoDTO cargarDocumentoPorExpedienteYForma(ExpedienteDTO expediente,FormaDTO tipoForma) throws NSJPNegocioException;
	 
	 
	 /**
	  * Consulta y carga un documento en base a su identificador
	  * Si el documento no tiene ni texto parcial ni se ha emitido el archivo digital
	  * entonces llena el formato en base al tipo de forma guardada en el documento
	  * @param documentoId Identificador del documento
	  * @return Documento encontrado, null en caso contratio
	  * @throws NSJPNegocioException
	  */
	 DocumentoDTO cargarDocumentoPorId(Long documentoId) throws NSJPNegocioException;
	 /**
	  * Consulta y carga un documento en base a su identificador
	  * Si el documento no tiene ni texto parcial ni se ha emitido el archivo digital
	  * entonces llena el formato en base al tipo de forma guardada en el documento
	  * @param documentoId Identificador del documento
	  * @param exp Expediente para llenar los datos
	  * @return Documento encontrado, null en caso contratio
	  * @throws NSJPNegocioException
	  */
	 DocumentoDTO cargarDocumentoPorId(Long documentoId,ExpedienteDTO exp) throws NSJPNegocioException;
	 
	 
	 DocumentoDTO cargarDocumentoPorAudienciaYForma(AudienciaDTO audiencia, FormaDTO tipoForma) throws NSJPNegocioException;
	
	 DocumentoDTO cargarDocumentoPorResolutivoYForma(ResolutivoDTO resolutivo,FormaDTO tipoForma) throws NSJPNegocioException;
	
	 /**
	  * Carga la plantilla de un documento en base a un idDocumento y a la forma, con los campos
	  * del documento sustituidos por sus valores de nogocio.
	  * @param idDocumento
	  * @param forma
	  * @return
	  * @throws NSJPNegocioException
	  */
	 DocumentoDTO cargarDocumentoPorIdDocumentoYForma(Long idDocumento,
			FormaDTO forma) throws NSJPNegocioException;
	 
	/**
	 * M&eacute;todo que obtiene los par&aacute;metros de campo forma para una
	 * plantilla de sentencia
	 * 
	 * @param sentenciaDTO id de la sentencia a consultar
	 * @param parametrosSentencia llaves con los campos forma
	 * @return parametrosSentencia con los valores deseados
	 * @throws NSJPNegocioException
	 */
	public Map<String, Object> getParametrosExtraSentecia (
			SentenciaDTO sentenciaDTO, Map<String, Object> parametrosSentencia)
			throws NSJPNegocioException;	 
}
