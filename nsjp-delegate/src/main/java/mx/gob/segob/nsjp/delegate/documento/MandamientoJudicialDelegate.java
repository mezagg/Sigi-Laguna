/**
* Nombre del Programa : MandamientoJudicialDelegate.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 24/08/2011
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
package mx.gob.segob.nsjp.delegate.documento;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Delegate para la operaci�n con los mandamiento judiciales
 * @version 1.0
 * @author Emigdio Hern�ndez
 *
 */
public interface MandamientoJudicialDelegate {
	/**
	 * Guarda un nuevo mandamiento judicial.
	 * De acuerdo a un resolutivo se obtiene la informaci�n referente al mandamiento a ingresar.
	 * En caso de que sea Sentencia, se debe ingresar la fecha de inicio y fin de la sentencia, 
	 * asi como el involucrado. El servicio se encarga de crear un expediente nuevo, asociado al
	 * mismo n�mero de caso, un nuevo numero de expediente de tipo "Carpeta de Ejecuci�n", siendo este
	 * una Toca y como causaPadre al numero de experiente origen. 
	 * En este nuevo expedinte se hace una copia del imputado (Probable responsable), 
	 * del documento de la sentencia.
	 * 
	 * @param mandamiento Mandamiento a guardar
	 * @return Mandamiento actualizado que se guard�
	 */
	MandamientoDTO registrarMandamientoJudicial(MandamientoDTO mandamiento) throws NSJPNegocioException;
	
	/**
	 * Consulta de expedientes por mandamientos judiciales, utilizando un filtro de consulta
	 * @param mandamientoDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	@Deprecated
	List<ExpedienteDTO> consultaMandamientosJudicialesPorFiltro(MandamientoDTO mandamientoDTO) throws NSJPNegocioException;
	
	/**
	 * Consulta los mandamientos judiciales relacionados a un numero de Expediente
	 * @param numeroExpediente Numero de expediente a filtrar
	 * @return Lista de mandamientos judiciales encontrados
	 */
	List<MandamientoDTO> consultarMandamientosPorNumeroExpediente(String numeroExpediente,UsuarioDTO usuario) throws NSJPNegocioException;
	
	/**
	 * Consulta los mandamientos judiciales relacionados a un numero de Expediente, se puede realizar b�squeda por:
	 * n�mero de Expediente, fecha de inicio y/o fecha final, estatus del mandamiento
	 * @param mandamiento, numeroExpediente: Campos por filtro para realizar la b�squeda
	 * @return Lista de mandamientos judiciales encontrados
	 */	
	@Deprecated
	List<MandamientoDTO> consultarMandamientoPorFiltro(MandamientoDTO mandamientoDTO, String numeroExpediente, 
			List<Long> idsTipoMandamiento, FuncionarioDTO filtroFuncionario) throws NSJPNegocioException;

	/**
	 * Actualiza los datos de un mandamiento judicial en base a su ID
	 * @param mandamiento Datos fuente para la modificaci�n
	 */
	void actualizarMandamiento(MandamientoDTO mandamiento, Instituciones destino) throws NSJPNegocioException;
	
	/**
	 * Env�a por servicio web en l�nea un mandamiento judicial, el mandamiento judicial ya
	 * debe de existir en la base de datos
	 * @param mandamientoId Identificador del mandamiento judicial
	 * @author Emigdio Hern�ndez
	 */
	@Deprecated
	void enviarMandamientoJudicial(Long mandamientoId) throws NSJPNegocioException;
	
	/**
	 * Adjunta un documento a un mandamiento judicial, sin crear actividad ni adjuntarlo al expediente
	 * @param documentoDTO
	 * @param tipoDocumento
	 * @return
	 * @throws NSJPNegocioException
	 */
	@Deprecated
	public Long adjuntarDocumentoAMandamientoJudicial(DocumentoDTO documentoDTO,MandamientoDTO mandamientoJudicialDTO, TipoDocumento tipoDocumento)throws NSJPNegocioException;
	
	/**
	 * Permite consultar mandamiento judicial por identificador
	 * @param idMandamiento
	 * @return
	 * @throws NSJPNegocioException
	 */
	public MandamientoDTO consultarMandamientoPorId(Long idMandamiento) throws NSJPNegocioException;
	
	/**
	 * Perimte consultar el mandamiento judicial asociado al resolutivo
	 * @param resolutivo
	 * @return mandamiento si es que el resolutivo esta asociado a algun resolutivo
	 * @throws NSJPNegocioException
	 */
	public MandamientoDTO consultarMandamientoPorResolutivo(ResolutivoDTO resolutivo) throws NSJPNegocioException;
}
