/**
 * Nombre del Programa : EvidenciaDelegate.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 30-jun-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.delegate.evidencia;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.almacen.AlmacenDTO;
import mx.gob.segob.nsjp.dto.cadenadecustoria.CadenaDeCustodiaDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudPericialDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Contrato del delegate para los metodos de comunicacion de Evidencia(s) entre
 * la vista y los servicios.
 * 
 * @version 1.0
 * @author Jacob Lobaco
 */
public interface EvidenciaDelegate {

    /**
     * registrar la baja de las evidencias que recibe como parametro.
     * @param evidenciasDto Las evidencias a dar de baja.
     * @throws NSJPNegocioException En caso de que evidenciasDto sea null
     * o que los id de las evidencias sean nulos.
     */
    void registrarBajaEvidencia(List<EvidenciaDTO> evidenciasDto) throws NSJPNegocioException;

    /**
     * Consulta las evidencias asociadas a una cadena de custodia.
     * 
     * @param cadenaDeCustodiaDTO
     *            Objeto con el n�mero de folio de la cadena de custodia.
     * @return Devuelve el listado de evidencias asociadas a la cadena de
     *         custodia. En caso de no existir una cadena de custodia con el
     *         numero de folio o en caso de no existir evidencias asociadas a la
     *         cade de custodia, regresa la lista vacia.
     *         <ol>
     *         De cada evidencia se consultan los siguientes datos:
     *         <li>Identificador de la evidencia
     *         <li>Informaci�n de la evidencia
     *         <li>Hora del levantamiento de la evidencia
     *         <li>Origen de la evidencia
     *         <li>�ltimo eslab�n asociado
     *         <li>N�mero de eslab�n
     *         <li>Tipo de eslab�n
     *         <li>Almac�n (Dentro de Evidencia.Objeto.Almacen)
     *         </ol>
     * @throws NSJPNegocioException
     *             En caso de recibir una {@code cadenaDeCustodiaDTO} nula o en
     *             caso de no contener un numero de folio
     */
    List<EvidenciaDTO> consultarEvidenciasXCadenaCustodia(
            CadenaDeCustodiaDTO cadenaDeCustodiaDTO)
            throws NSJPNegocioException;

    /**
     * Consulta las evidencias resguardadas por un perito, es decir, donde el
     * �ltimo eslab�n es el usuario (firmado) quien recibe.
     * 
     * @param usrFirmado
     *            Requerido: <b>idUsuario</b>.
     * @return Lista de evidencias
     */
    List<EvidenciaDTO> consultarEvidenciasResguardadasPorUsario(
            UsuarioDTO usrFirmado) throws NSJPNegocioException;
    
    /**
     * Operaci�n que realiza la funcionalidad de guardar los datos capturados de la evidencia
     * Los datos qye guarda son
     * - Descripcion o informaci�n de la evidencia
     * - Fecha de levantamiento
     * - Origen de la evidencia
     * - Cadena de Custodia a la que pertenece
     * 
     * @param evidenciaDTO
     * @return
     * @throws NSJPNegocioException
     */
    Long guardarEvidencia(EvidenciaDTO evidenciaDTO)throws NSJPNegocioException;
    /**
     * Consulta las evidencias resguardadas por un perito, es decir, donde el
     * �ltimo eslab�n es el usuario (firmado) quien recibe.
     * 
     * @param usuario
     *            Requerido <b>idUsuario</b>
     * @param cadena
     *            Requerido <b>cadenaDeCustodiaId</b>
     * @return
     * @throws NSJPNegocioException
     */
    List<EvidenciaDTO> consultarEvidenciasResguardadasPorUsario(
            UsuarioDTO usrFirmado, CadenaDeCustodiaDTO cadena)
            throws NSJPNegocioException;
    
    
    /**
     * Realiza una consulta de las evidencias asignadas a una cadena de custodia
     * que est�n implicadas en una solicitud pericial.
     * Por cada evidencia verifica si est� asignada a alguna de las solicitudes hijas,
     * si est� asignada entonces en el funcionario que recibe del �ltimo eslab�n
     * de la evidencia ser� el perito de la solicitu hija donde est� asignada
     * la evidencia (solo en el DTO para fines de mostrarlo en pantalla)
     * @param cadena Cadena de custodia para la cu�l consultar las evidencias
     * @param solicitud Solicitud inicial de pericial sobre la cu�l se est� trabajando
     * @return Lista de evidencias encontradas y ajustadas seg�n los peritos de la solicitud
     * @throws NSJPNegocioException
     */
    List<EvidenciaDTO> consultarEvidenciaXCadenaCustodiaYPeritoDeSolicitudPericial(CadenaDeCustodiaDTO cadena,SolicitudPericialDTO solicitud) throws NSJPNegocioException;

    /**
     * Obtiene una lista de peritos asignados a una solicitud inicial que no tengan asignaci�n como 
     * �ltimo eslab�n de la cadena de custodia
     * @param cadena Cadena de custodia a tomar en cuenta para las asignaciones
     * @param solicitud Solicitud incial que tiene solicitudes hijas con peritos asignados
     * @return Lista de peritos no asignados
     * @throws NSJPNegocioException
     */
    List<FuncionarioDTO> consultarPeritosSinAsignacionEnCadenaDeCustodia(CadenaDeCustodiaDTO cadena,SolicitudPericialDTO solicitud) throws NSJPNegocioException;
    /**
	 * Asigna una evidencia a los elementos relacionados a la solicitud pericia, este 
	 * m�todo no crea cadenas de custodia ni eslabones
	 * @param evidencia Evidencia a asignar
	 * @param solicitud Solicitud inicial para la cual se debe buscar el perito
	 * donde se va a relacionar la evidencia
	 * @param perito Perito a asignar evidencias 
	 */
	void asignarEvidenciaASolicitudPericial(EvidenciaDTO evidencia,SolicitudPericialDTO solicitud,FuncionarioDTO perito) throws NSJPNegocioException;
	

	/**
	 * Operaci�n que realiza la funcionalidad de consultar las evidencias asociadas al folio de una cadena de custodia en funci�n de la disponibilidad solicitada(estatus).
	 * @param custodiaDTO: idCadena
	 * @param estatus
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<EvidenciaDTO> consultarEvidenciasXCadenaCustodiaYEstatus(CadenaDeCustodiaDTO custodiaDTO, Long estatus)throws NSJPNegocioException;
	/**
	 * Operaci�n que realiza la funcionalidad de asociar el destino legal de una Evidencia
	 * @param evidenciaDTO: idEvidencia
	 * @param destinoLegal
	 * @throws NSJPNegocioException
	 */
	void asociarDestinoLegal(EvidenciaDTO evidenciaDTO, Long destinoLegal)throws NSJPNegocioException;

	/**
	 * Operaci�n que realiza la funcionalidad de consultar evidencia por Id de Solicitud
	 * @author Marco Gallardo
	 * @param idSolicitud: Clave ID de la solicitud
	 */
    
	List<EvidenciaDTO> consultarEvidenciasXIdSolicitud(Long idSolicitud);
	
	/**
	 * Operaci�n que permite consultar el detalle de una evidencia
	 * @param idEvidencia
	 * @return
	 * @throws NSJPNegocioException
	 */
	EvidenciaDTO consultaEvidencia(Long idEvidencia)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite consultar las evidencias por almacen, opcionalmente por estatus y opcionalmente por caso
	 * Ademas permite saber si ya fue atendida una solicitud asoiada a una evidencia
	 * @param usuarioDTO
	 * @param estatusEv: null=sin importar estatus; -1=Solicitudes de AMP; valor=por estatus  
	 * @param casoDTO: idCaso
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<EvidenciaDTO> consultarEvidenciasXAlmacen(UsuarioDTO usuarioDTO, Long estatusEv, CasoDTO casoDTO, Long idAmacen, Boolean tieneSolicitudPorAtender)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite ver los documentos asociados a los eslabones de una evidencia dada
	 * @param evidenciaDTO: idEvidencia
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<DocumentoDTO> consultarDocumentosXEslabonesDEvidencia(EvidenciaDTO evidenciaDTO)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite ver las evidencias que deber�an estar devueltas y no han sido devueltas a la fecha
	 * @param usuarioDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<EvidenciaDTO> consultarEvidenciasNoDevueltas(UsuarioDTO usuarioDTO)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite cambiar el estatus de una evidencia
	 * @param evidenciaDTO: idEvidencia, estatusDeseado
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long cambiarEstatusEvidencia(EvidenciaDTO evidenciaDTO)throws NSJPNegocioException;
	
    /**
     * Elimina fisicamente la evidencia y da de baja l�gica el objeto asociado a dicha evidencia.
     * 
     * @param idEvidencia a ser eliminada
     * @return Boleano que indica si la eliminaci�n se realiz� con �xito.
     * @throws NSJPNegocioException
     */
    Boolean eliminarEvidencia(Long idEvidencia) throws NSJPNegocioException;
    
    /**
     * Permite asociar un conjunto de evidencias a un almacen.
     * @param evidencias
     * @param almacen
     * @throws NSJPNegocioException
     */
    public void asociarEvidenciasPorAlmacen(List<EvidenciaDTO> evidencias, AlmacenDTO almacen)
            throws NSJPNegocioException;

    /**
     * Permite consultar evidencias asociadas a un Funcionario en base a su area y a su agencia
     * @param estatusEv
     * @param idUsuario
     * @param areaId
     * @param discriminanteId
     * @return
     * @throws NSJPNegocioException
     */
    public List<EvidenciaDTO> consultarevidenciaXEstatusXFuncionario(Long estatusEv,UsuarioDTO usuarioDTO) throws NSJPNegocioException;
    

    /**
     * Permite actualizar el campo tieneSolicitudPorAtender con el objetivo de sabe si un almacenista tiene solicitudes por atender 
     * @param evidencia
     * @param tieneSolicitudPorAtender
     * @throws NSJPNegocioException
     */
    void actualizaCampoDeEvidencia(EvidenciaDTO evidencia, Boolean tieneSolicitudPorAtender) throws NSJPNegocioException;
    
}


