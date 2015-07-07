/**
* Nombre del Programa : MandamientoDAO.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 23/08/2011
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
package mx.gob.segob.nsjp.dao.documento;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoDTO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Resolutivo;

/**
 * DAO para el acceso a los mandamientos judiciales
 * @version 1.0
 * @author Emigdio Hernández
 *
 */
public interface MandamientoDAO extends GenericDao<Mandamiento, Long> {
	/**
	 * Consulta los mandamientos judiciales relacionados a un numero de Expediente
	 * @param numeroExpediente Numero de expediente a filtrar
	 * @return Lista de mandamientos judiciales encontrados
	 */
	List<Mandamiento> consultarMandamientosPorNumeroExpediente(String numeroExpediente,Long discriminanteId);

	/**
	 *  M&eacutetodo que consulta un mandamiento judicial por folio
	 *  @param folioDocumento	,folio del mandamiento judicial
	 *  @return mandamiento encontrado
	 */
	Mandamiento obtenerMandamientoPorFolioDoc(String folioDocumento);

	/**
	 * M&eacutetodo gen&eacute;rico para consultar mandamiento judiciales por
	 * filtro, se puede realizar b&uacute;squeda por: n&uacute;mero de
	 * Expediente, Por fecha de creaci&oacute;n y Estatus del mandamiento, por
	 * funcionario asociado al expediente
	 * 
	 * busqueda por id
	 * busqueda por numero expediente
	 * busqueda por institucion
	 * busqueda por tipos de mandamiento
	 * busqueda por funcionario
	 * busqueda por fecha de inicio
	 * busqueda por fecha de fin
	 * @return lista de mandamientos que superaron el filtro 
	 */
	public List<Mandamiento> consultarMandamientoPorFiltro(
			FiltroMandamientoDTO filtroMandamientoDTO);
	

	/**
	 * Consulta mandamientos judiciales por un filtro, de necesitarse más restricciones, se pueden
	 * agregar, no repercute en el desarrollo actual
	 * @param mandamiento
	 * @return
	 */
	List<NumeroExpediente> consultarMandamientosJudicialesPorFiltro(
			MandamientoDTO mandamiento);
	
	/**
	 * M&eacute;todo para obtener un mandamiento judicial de acuerdo al resolutivo que lo origino
	 * @param resolutivoId, id del resolutivo asociado al mandamientp
	 * @return Mandamiento relacionado al resolutivo
	 */
	public Mandamiento obtenerMandamientoPorResolutivo(Resolutivo resolutivo)throws NSJPNegocioException ;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de los documentos que se encuentran relacionados 
	 * con un mandamiento judicial y que son de un tipo de documento en espec&iacute;fico.
	 * @param mandamientoId - Identificador del mandamiento del cu&aacute;l se consultar&aacute;n los
	 * 						  documentos relacionados.
	 * @param idTipoDocumento - Identificador del tipo de documento sobre el cu&aacute;l se filtrar&aacute;n
	 * 							los resultados obtenidos.
	 * @return List<Documento> - List<Documento> con los resultados regresados por la consulta.
	 * @throws NSJPNegocioException - En el caso de que no se hayan asignado los par&aacute;metros obligatorios.
	 */
	public List<Documento> consultarDocumentosRelacionadosMandamientoPorTipo(Long mandamientoId, Long idTipoDocumento) throws NSJPNegocioException;
	
	 
	/**
	 * Consulta que se una relacion delito persona no haya sido asociada a
	 * a un mismo tipo de mandamiento dentro de la misma audiencia, con anterioridad 
	 * @param audienciaId, audiencia de la cual se obtienen los resolutivos - mandamiento y tipos de mandamiento
	 * @param tipoMandamientoId, tipo de mandamiento que se desea ingresar
	 * @param delitoPersonaId, relacion delito persona
	 * @return verdader, en caso de que encuentre 1 o mas asociaciones, falso en caso contrario
	 */
	public Boolean consultarExistenRelacionesDelitoPersonaPorMandamiento(Long audienciaId,Long tipoMandamientoId,Long delitoPersonaId);
	
	/**
	 * Obtiene el &uacute;ltimo folio del mandamiento, correspondiente a una causa
	 * @param numeroDeCausa, numero de causa
	 * @return &uacute;ltimo folio del mandamiento para dicha causa 
	 */
	public Long obtenerUltimoFolioMandamientoPorCausa(String numeroDeCausa);
}
