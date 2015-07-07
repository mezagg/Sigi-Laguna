/**
 * Nombre del Programa : AsignarNumeroExpedienteService.java
 * Autor                            : cesar
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 15 Apr 2011
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
package mx.gob.segob.nsjp.service.expediente;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.TurnoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author cesar
 * 
 */
public interface AsignarNumeroExpedienteService {

	/**
     * Genera un nuevo número de expediente.<br>
     * Al generar el número guarda un expediente en la BD.
     * 
     * 
     * @param expedienteDTO
     *            Obligatorio la <b>fechaApertura</b>. <br>
     *            Opcional <b>casoDTO</b>.<br>
     *            Si <b>expedienteId</b> es <code>null</code> se generará un
     *            Expediente completo (con sU respectuvo Número), en caso
     *            contrario sólo se creará un Número de expediente asociado el
     *            expediente.
     * 
     * @return <ul>
     *         <li>expedienteId</il>
     *         <li>numeroExpediente</il>
     *         <li>fechaApertura</il>
     *         </ul>
     * @throws NSJPNegocioException
     *             En caso de ocurrir algún error.
     */
    public ExpedienteDTO asignarNumeroExpediente(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;

    /**
     * Autoasigna los nuevos números de expediente al usuario en sesión
     * @param listaExpedientes
     * @return
     * @throws NSJPNegocioException
     */
    public Boolean asignarNumerosDeExpediente(List<ExpedienteDTO> listaExpedientes)
            throws NSJPNegocioException;

    /**
     * 
     * @param tipoExpediente
     * @return
     * @throws NSJPNegocioException
     */
    public ExpedienteDTO asignarNumeroExpedienteTipo(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;

    /**
     * Genera un nuevo número de expediente a partir de un turno.<br>
     * Al generar el número guarda un expediente en la BD.
     * 
     * @param TurnoDTO
     *            Obligatorios <b>turnoId, usuario.idUsuario</b>.
     * @return <ul>
     *         <li>expedienteId</il>
     *         <li>numeroExpediente</il>
     *         <li>fechaApertura</il>
     *         </ul>
     * @throws NSJPNegocioException
     *             En caso de ocurrir algún error.
     */
    public ExpedienteDTO asignarNumeroExpediente(TurnoDTO turno)
            throws NSJPNegocioException;
    /**
     * Asigna un numero de expediente a una solicitud
     * @param numeroExpedienteId Numero de expediente a asignar
     * @param solicitudId Solicitud a la cuál será asignado
     */
    void asignarNumeroExpedienteASolicitud(Long numeroExpedienteId,Long solicitudId) throws NSJPNegocioException;

	public ExpedienteDTO asignarNumeroExpedienteDefensoria(
			ExpedienteDTO inputExpediente) throws NSJPNegocioException;

	
	/**
	 * Servicio para crear un nuevo de Auditoría, que permita asignar un nuevo número de expediente, número de auditoría, 
	 * considerando el registro en la relación: RelNumExpedienteAuditoria
	 * 
	 * @param listaNumeroExpedienteAuditados
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ExpedienteDTO>  asignarNumeroExpedienteAuditoria(List<ExpedienteDTO> listaNumeroExpedienteAuditados) throws NSJPNegocioException;
	
	/**
	 * Servicio que permite la creación de un nuevo expediente, su numero Expediente "Carpeta de Ejecucion de la Sentencia"
	 * A partir del ID se obtiene el Area, funcionario, numeroExpedientePadre de la CAusa.
	 * Para generar el nuevo expediente de las sentencia, es necesario, pasar:
	 * Areas, CausaPadre (NumeroExpedienteId), TipoExpediente, Esxtatus, Funcionario y el ExpedienteID=null.
	 * TODO GBP Falta pasar la informacion de acuerdo al CU. Y Asginarlo cuando se tenga un mandamiento de tipo Sentencia.
	 * 
	 * @param expedienteId
	 * @return
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO asignarNumeroExpedienteCarpetaEjecucion(Long expedienteId) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la generaci&oacute;n de un n&uacute;mero de expediente alterno en base al
	 * usuario firmado. Y lleva a cabo la actualizaci&oacute;n del n&uacute;mero de expediente alterno.
	 * @param usuario - El usuario del cual se va a obtener la unidad y el discriminante sobre el cual se va a 
	 * 					generar el n&uacute;mero alterno del expediente.
	 * @param expediente - El expediente del cual se va a actualizar el n&uacute;mero de expediente alterno.   
	 * @return numExpAlterno - el n&uacute;mero de expediente alterno generado.
	 * @throws NSJPNegocioException - En el caso de que el usuario pasado como par&aacute;metro, no cuente con un 
	 * 								  distrito asociado.
	 */
	public String obtenerNumeroExpedienteAlterno (UsuarioDTO usuario, ExpedienteDTO expediente, String obtenerNumeroExpedienteAlterno) throws NSJPNegocioException;
	
	
	/**
	 * Servicio que se encarga de generar el siguiente numero de expediente para Poder Judicial
	 * Donde está conformado por:
	 *  NNNNN/CC/AAAA-PJ-ZAC-JNN
	 *  a.	NNNNN – Consecutivo: 5 dígitos para el consecutivo, 
	 *  	iniciará en 00001 hasta llegar al 99999 por año, por juzgado.
	 *  b.	CC – Siglas para identificar el área: 2 caracteres para las siglas:
	 *  	i.	CA – Causa	
	 *  	ii.	EJ – Ejecución
	 *  c.	AAAA – Año: 4 dígitos para el año
	 *  d.	PJ – Siglas de la institución: 2 caracteres para las siglas de la institución: PJ
	 *  e.	ZAC – Siglas entidad federativa: 3 caracteres, para las siglas de la entidad federativa ZAC
	 *  f.	JNN – Juzgado: 3 caracteres, para clave del juzgado por ejemplo: J01
	 *  
	 *  El usuarioDTO debe de tener la Clave del Discriminante al que pertenence, en caso contario, se hace 
	 *  la busqued del funcionario por la clave de funcionario, para obtener el discrimiante.
	 *  
	 * @param esCausa
	 * @param usuarioDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	String obtenerNumeroExpedientePoderJudicial(Boolean esCausa,
			UsuarioDTO usuarioDTO) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la generaci&oacute;n de un n&uacute;mero de expediente alterno en base al
	 * usuario firmado. La unidad para el número de expediente alterno, es específicamente visitaduría
	 * @param usuario
	 * @param expediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public String obtenerNumeroExpedienteAlternoUnidadVisitaduria (UsuarioDTO usuario, ExpedienteDTO expediente) throws NSJPNegocioException;
		
	/**
	 * M&eacute;todo para consultar el numero de expediente Alterno
	 * @param usuario
	 * @param expediente
	 * @return El numero de expediente alterno, si el parametro esta encendido en BD
	 * @throws NSJPNegocioException
	 */
	public String consultarNumeroExpedienteAlterno (ExpedienteDTO expediente) throws NSJPNegocioException;

	
	/**
	 * M&eactute para obtener el número de expediente, si se encuentra un número de expediente con dicho expedienteId
	 * y área, se mantiene, en caso contrario, se genera el número de expediente alterno.
	 * @param expediente
	 * @param areaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public String obtenerNumExpXExpIdAreaId(ExpedienteDTO expediente, Long areaId, String obtenerNumExpXExpIdAreaId) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para la generaci&oacute;n del n&uacute;mero de expediente.
	 * Se define publico para la prueba unitaria
	 * Se requiere cambiar la visibilidad, en la implementaci&oacute;n del m&eactue;todo de 'private' a 'public'.
	 * Y quitar el comentario en la interfaz.
	 *  
	 * Se comenta la firma para evitar errores de compilacion en entrega normal.
	 * 
	 * @param idArea					
	 * @param aoUsuarioFirmado
	 * @param anioCreacionDelExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	//String obtenerNumeroExpediente(Long idArea, UsuarioDTO aoUsuarioFirmado, String anioCreacionDelExpediente) throws NSJPNegocioException;
	
	/**
	 * Metodo que permite reasignar una lista de expedientes, permitira:
	 * - Actualiza el responsable del expediente en policía ministerial
	 * - Si existiera información del policía de la actividad anterior de Generar denuncia, lo cambia a la nueva actividad(Generar denuncia en PM)
	 * - Cambia del responsable anterior al nuevo la actividad de generar denuncia en policía ministerial
	 * - Actualiza la solicitud de línea de investigación para que pueda ser vista por el nuevo responsable
	 * - Registrar en bitacora (HistoricoExpediente)
	 * 
	 * @param idNumerosExpedientes
	 * @param idNuevoFuncionario
	 * @param usuario
	 * @return
	 * @throws NSJPNegocioException
	 */
	public void reasignarNumerosDeExpedientePM(List<Long> idNumerosExpedientes, Long idNuevoFuncionario, UsuarioDTO usuario, Long idFuncionarioActual) throws NSJPNegocioException;
}
