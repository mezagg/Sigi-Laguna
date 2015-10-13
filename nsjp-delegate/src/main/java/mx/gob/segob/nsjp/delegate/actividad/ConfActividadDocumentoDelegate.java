/**
* Nombre del Programa : ConfActividadDocumentoDelegate.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 17/08/2011
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
package mx.gob.segob.nsjp.delegate.actividad;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO;
import mx.gob.segob.nsjp.dto.actividad.ConfTipoActividadOrigenDestinoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
public interface ConfActividadDocumentoDelegate {
	
    /**
     * Consulta las configuraciones asociadas a un usuario, el area a la que
     * pertenec, el estado del numero de expediente y, opcionalmente, la 
     * categor&iacute;a a la que pertenece dicha actividad,  
     * que se le pasa como parametro.<br/>
     * Los campos requeridos son: {@code usuario.IdUsuario} y {@code
     * expediente.numeroExpediente}, opcionalmente el usuario deberia
     * venir asociado con su funcionario pero si esto no ocurre el sistema
     * lo consulta automaticamente.
     * @param usuarioDto El usuario de donde se obtendra el funcionario para
     * buscar el area de trabajo.
     * @param expedienteDto El numero de expediente del cual buscamos su
     * estatus.
     * @param idCategoriaActividad El identificador de la Categor&iacute;a de Actividada 
     * con la cual se hace un filtro en la busqueda.
     * @return Los registros de la base que contienen los tipo de actividades
     * y tipos de documentos configurados en el sistema para el usuario y
     * numero de expediente dados. Regresa la lista vacia en caso de no
     * existir registros en la base asociados a los parametros de consulta.
     * @throws NSJPNegocioException Si se cumple alguna de las siguientes
     * condiciones:
     * <ol>
     * <li> {@code usuarioDto == null}
     * <li> {@code usuarioDto.getIdUsuario() == null}
     * <li> {@code expedienteDto == null}
     * <li> {@code expedienteDto.getNumeroExpediente() == null}
     * <li> Si la cadena pasada como numeroExpediente no existe en la base de
     * datos.
     * </ol>
     */
    List<ConfActividadDocumentoDTO> consultarConfActividadDocumento(
            UsuarioDTO usuario, ExpedienteDTO expedienteDto, Long idCategoriaActividad )
            throws NSJPNegocioException;
    
    List<ConfActividadDocumentoDTO> consultarConfActividadDocumento(
            UsuarioDTO usuario, ExpedienteDTO expedienteDto, Long idCategoriaActividad, Boolean sinCatUie )
            throws NSJPNegocioException;
    
    /**
     * Consulta la Configuracion del la Actividad de Documento de acuerdo
     * al id
     * 
     * @param idConfActividadDocumento
     * @return
     */
    ConfActividadDocumentoDTO consultaConfActividadDocumentoPorId(Long idConfActividadDocumento) throws NSJPNegocioException;
   
    /**
     * Consulta los estatus asociados a una Jerarquia Organizacional dentro
     * de la tabla de Configuracion de Actividades
     * 
     * @param idJerarquiaOrganizacional
     * @return
     * @throws NSJPNegocioException
     */
	List<ValorDTO> consultarEstadosPorJerarquiaOrganizacional(Long idJerarquiaOrganizacional) throws NSJPNegocioException;
    
	/**
	 * M&eacute;todo que lleva a cabo la consulta un
	 * <code>ConfActividadDocumentoDTO</code> que corresponde a la
	 * actuaci&oacute;n de respuesta que se debe de regresar como resultado de
	 * la atenci&oacute;n de una solicitud realizada por un usuario de una
	 * instituci&oacute;n externa.
	 * 
	 * @param filtro
	 *            - <code>ConfTipoActividadOrigenDestinoDTO</code> con los
	 *            filtros sobre los cuales se va a llevar a cabo la consulta de
	 *            la actuaci&oacute;n de respuesta.
	 * @return ConfActividadDocumentoDTO - Objeto con la informaci&oacute;n de
	 *         la actuaci&oacute;n de respuesta que corresponde con los datos
	 *         ingresados como filtros.
	 * @throws NSJPNegocioException
	 *             - En el caso de que la consulta de la actuaci&oacute;n no se
	 *             haya podido llevar a cabo.
	 */
	public List<ConfActividadDocumentoDTO> consultarConfActividadDocumentoPorConfTipoActividadDestino(
			ConfTipoActividadOrigenDestinoDTO filtro)
			throws NSJPNegocioException;
    
	/**
	 * M&eacute;todo que lleva a cabo la consulta de registros en la tabla de
	 * confActividadDocumento, en base al tipo de actividad configurada dentro
	 * de la actuaci&oacute;n.
	 * 
	 * @param filtro
	 *            - ConfActividadDocumentoDTO con la informaci&oacute;n de la
	 *            actividad de la cual se va a obtener la actuaci&oacute;n
	 * @return ConfActividadDocumentoDTO - Con la informaci&oacute;n de la
	 *         actuaci&oacute;n relacionada al tipo de actividad.
	 * @throws NSJPNegocioException
	 *             - En el caso de que el filtro no cuente con el id del tipo de
	 *             actividad a consultar.
	 */
	public ConfActividadDocumentoDTO consultaConfActividadDocumentoPorIdActividad(
			ConfActividadDocumentoDTO filtro) throws NSJPNegocioException;
	
	/**
	 * Consulta la configuraci&oacute;n de las actuaciones de acuerdo a los
	 * par&aacute;metros proporcionados en el DTO
	 * 
	 * @param filtroConfActividadDocumentoDTO - Objeto de tipo ConfActividadDocumentoDTO
	 * 									  ingresado como par&aacute;metro.
	 * @return List<ConfActividadDocumentoDTO> - List<ConfActividadDocumentoDTO> que
	 * 											 cumplen con los criterios ingresados
	 * 											 dentro del filtro.
	 * @throws NSJPNegocioException - En el caso de que no se haya podido llevar a cabo 
	 * 								  la consulta de manera correcta.
	 */
	List<ConfActividadDocumentoDTO> consultarConfActividadDocumentoFiltro(
			ConfActividadDocumentoDTO filtroConfActividadDocumentoDTO)
			throws NSJPNegocioException;
}
