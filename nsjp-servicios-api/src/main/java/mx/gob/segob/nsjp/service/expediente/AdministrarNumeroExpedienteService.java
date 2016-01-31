/**
 * Nombre del Programa : AdministrarNumeroExpedienteService.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 27/06/2011
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

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.InstitucionDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Este caso de uso permitir� al solicitante del servicio crear un n�mero de expediente
 *  y asignarlo a un expediente, o en su caso crear un nuevo expediente en el sistema.
 *  La informaci�n que tendr� e nuevo n�mero de expediente es:
 *    - Usuario firmado.
 *    
 *  La informaci�n que tendr� el nuevo expediente es:
 *    - N�mero de expediente.
 *    - Usuario firmado.
 * @version 1.0
 * @author GustavoBP
 *
 */
public interface AdministrarNumeroExpedienteService {

	/**
	 * Este servicio permitir� al solicitante crear un n�mero de expediente y 
	 * asignarlo a un expediente, o en su caso crear un nuevo expediente en el sistema.
	 * La informaci�n que tendr� e nuevo n�mero de expediente es:
	 *     - Usuario firmado.
	 *   La informaci�n que tendr� el nuevo expediente es:
	 *     - N�mero de expediente.
	 *     - Usuario firmado.
	 * Para el manejo del N�mero de Expediente (NE) se tiene el siguiente formato:
	 *      LLL/II/DD/UU/AAAA/C-NNNNN   Ejeplo: 000/PR/15/RBO/2011/C-12345
	 *      
	 *      Longitud m�xima: 26 (caracteres incluyendo '/')
	 *      
	 * en donde se devide en 3 partes: 
	 * 
	 * Prefijo:  LLL/II/DD/UU   => 000/PR/15/RBO
	 *     Parte del NE que se podr� configurar en cuanto a la longitud.
	 *     Longitud m�xima: 13 (caracteres incluyendo '/')
	 *     En donde:
	 * 			L = Car�cter Libre para completar la cadena {3 caracteres m�ximo}
	 * 			I = Prefijo Instituci�n {2 caracteres m�ximo}
	 * 			D = Prefijo Distrito {2 caracteres m�ximo}
	 * 			U = Prefijo Unidad {3 caracteres m�ximo}
	 *     
	 * A�o: /AAAA/    => /2011/
	 * 	   Parte del NE que representa el a�o actual del sistema, y es fijo.
	 * 	   Longitud exacta: 6 (caracteres incluyendo '/')
	 * 
	 * Subfijo:  C-NNNNN    => C-12345
	 * 		Parte del NE que es fija y representa el valor consecutivo del NE.
	 * 		Longitud exacta: 7 (caracteres incluyendo '-')
	 * 		En donde:
	 * 			C = Car�cter que es un consecutivo en el rango de {A-Z}
	 * 			N = N�mero entero que representa un consecutivo en el rango {0-9}
	 * 
	 *  //TODO GBP Falta determinar la concurrencia del servicio
	 *  
	 * @param usuarioDTO
	 * @param expedienteDTO
	 * @param institucionDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO crearAsignarNumeroExpediente(UsuarioDTO usuarioDTO, ExpedienteDTO expedienteDTO, InstitucionDTO institucionDTO ) throws NSJPNegocioException;
	

    /**
     * Servicio que realiza la funcionalidad de consultar el �ltimo n�mero del
     * expediente que se tiene registrado en la Instituci�n
     *
     * Regresa el n�mero de expediente de tipo cadena, en caso contrario devuelve null.
     *
     * @param idInstitucion
     * @return
     * @throws NSJPNegocioException
     */
    public String consultarUltimoNumeroExpediente(Long idInstitucion) throws NSJPNegocioException;

	/**
	 * Servicio que realiza la funcionalidad de crear un expediente con:
	 * 	 Fecha de creaci�n: fecha actual del sistema.
	 *   Estatus: EstatusExpedeinte.ABIERTO
	 * Regresa un objeto de tipo Expediente, en caso contrario regresa NULL.
	 * 
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO crearExpediente () throws NSJPNegocioException;

    /**
     * Servicio que realiza la funcionalidad de asociar el N�mero de Expediente a:
     * 	Usuario - Funcionario 
     *  Institucion - Jerarquia Organizacional.
     *  Expediente
     *  
     * @param expedienteDto
     * @throws NSJPNegocioException
     */
    public void asociarNumExpediente(ExpedienteDTO expedienteDto)
            throws NSJPNegocioException;
	
	/**
	 * Operaci�n que realiza la funcionalidad de asociar la solicitud a la carpeta de investigaci�n.
	 * Recibe como par�metros:
	 * - El n�mero de expediente.
	 * - El identificador de la solicitud.
	 * Regresa verdadero en caso de haber realizado de forma correcta la asociaci�n, 
	 * Arroja una excepci�n si se encontro con alg�n problema.
	 * 
	 * El proceso que sigue es el siguiente:
	 * 1.-Consulta la solicitud por id
     * 2.-Consulta el numero de expediente por NumeroExpediente (cadena)
     *    NOTA: El numero del expediente debe ser �nico, de acuerdo a la regla de negocio, 
     *          caso contrario, se arroja una excepci�n.
     * 3.-Asocia a la solicitud el expediente consultado.
     * 
	 * @param expedienteDTO
	 * @param idSolicitud
	 * @return
	 * @throws NSJPNegocioException
	 */
    public boolean asociarNumCarpetaASolicitud( ExpedienteDTO expedienteDTO, Long idSolicitud)throws NSJPNegocioException;

	/**
	 * A partir de un n�mero de caso que viene de una instituci�n externa se busca
	 * un numero de expediente correspondiente en la base de datos local:
	 * De encontrar el caso:
	 * 	Si existe solo un n�mero de expediente entonces se retorna este
	 *  De existir m�s de un n�mero de expediente entonces no se retorna nada
	 * De no encontrar caso:
	 * 	Se crea un primer expediente y un primer caso
	 * @param numeroCaso
	 * @return
	 */
	ExpedienteDTO buscarOCrearExpedienteDeInstitucionExterna(String numeroCaso) throws NSJPNegocioException;
	
	/**
     * Consulta n�meros de expediente relacionados a un Caso de una solicitud
     * @return Numeros de expediente encontrados
     */
	List<ExpedienteDTO> consultarNumeroExpedientePorCasoDeSolicitud(Long solicitudId);
	
	/**
	 * Permite actualizar el reponsable de un conjunto de numerosExpediente
	 * @param idNumeroExpediente
	 * @param idFuncionario
	 * @throws NSJPNegocioException
	 */
	public void asociarExpedienteAFuncionario(Long idNumeroExpediente, Long idFuncionario) throws NSJPNegocioException;
	
	/**
	 * Servicio que permite crear un nuevo Expediente, asi mismo copiara la informaci�n del:
	 * - Probable Responsable
	 * - Victima
	 * - Delito 
	 * descritos en delitoPersonaDTO
	 * @param delitoPersonaDTO
	 * @return ExpedienteDTO
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO generarNuevoExpedienteUAVD(DelitoPersonaDTO delitoPersonaDTO) throws NSJPNegocioException;
	
	/**
	 * Permite crear un nuevo expediente con un nuevo numero de Expediente y su respectivo caso
	 * @param expedienteDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO generarNuevoExpedienteConCaso(ExpedienteDTO expedienteDTO) throws NSJPNegocioException;

        public CasoDTO generarNuevoNUC(UsuarioDTO usuarioDTO, Long numeroExpedienteId) throws NSJPNegocioException;

        /**
	 * Permite crear un nuevo expediente con un nuevo numero de Expediente
	 * @param expedienteDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO generarNuevoExpedienteSinCaso(ExpedienteDTO expedienteDTO) throws NSJPNegocioException;


	/**
	 * Servicio que permite pasar un involucrado de un expediente a otro expediente
	 * Para hacer esta nueva insercci�n del involucrado, se requiere setear todos los valores, 
	 * referentes a una entidad asociada al involucrado a null, es decir, su id.
	 * 
	 * @param expBD  Expediente a donde se va hacer la copia.
	 * @param aoInvolucrado Involucrado a copiar.
	 * @param aoCalidad 	Calidad del involucrado, en caso de que el involucrado no tenga asociada una calidad.
	 * @return
	 * @throws NSJPNegocioException
	 */
	InvolucradoDTO guardarInvolucradoEnExpediente(ExpedienteDTO expBD,
			InvolucradoDTO aoInvolucrado, Calidades aoCalidad) throws NSJPNegocioException;
	
	/**
	 * Perimite consultar el estatus del numero de expediente por numero expediente Id
	 * @param numeroExpedienteId
	 * @return Estatus Expediente
	 * @throws NSJPNegocioException
	 */
	public Long consultarEstatusNumeroExpedienteByNumeroExpedienteId(Long numeroExpedienteId) throws NSJPNegocioException;
	
	/**
	 * Permite actualizar la jerarquia organizacional de un numerosExpediente
	 * @param idNumeroExpediente
	 * @param jerarquiaOrganizacional
	 * @throws NSJPNegocioException
	 */
	public void actualizarJerarquiaOrganizacionalANumExp(Long idNumeroExpediente, Long jerarquiaOrganizacional) throws NSJPNegocioException;
}
