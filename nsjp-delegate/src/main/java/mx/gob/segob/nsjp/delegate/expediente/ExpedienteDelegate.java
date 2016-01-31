/**
 *
 * Nombre del Programa : ExpedienteDelegate.java                                    
 * Autor                            : Cesar Agustin                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 30/03/2011 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Interface del delegate para los servicios de expediente                       
 * Programa Dependiente  :N/A                                                      
 * Programa Subsecuente :N/A                                                      
 * Cond. de ejecucion        :N/A                                                      
 * Dias de ejecucion          :N/A                             Horario: N/A       
 *                              MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                       :N/A                                                           
 * Compania               :N/A                                                           
 * Proyecto                 :N/A                                   Fecha: N/A       
 * Modificacion           :N/A                                                           
 *------------------------------------------------------------------------------           
 */
package mx.gob.segob.nsjp.delegate.expediente;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.OrigenExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoDeBusquedaDeExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatEtapaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ActaCircunstanciadaDTO;
import mx.gob.segob.nsjp.dto.expediente.BitacoraEstatusNumExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.BitacoraPermisoExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.DatosGeneralesExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.DatosGeneralesExpedienteUAVDDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteViewDTO;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.NotaExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.RelNumExpedienteAuditoriaDTO;
import mx.gob.segob.nsjp.dto.expediente.TurnoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.InstitucionDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * @author CesarAgustin
 * @version 1.0
 */
public interface ExpedienteDelegate {

    /**
     * Consulta el expediente de un usuario en una etapa dada.
     * El metodo es implementado usando
     * {@link #consultarExpedientesPorEtapa(EtapasExpediente, Long, Long)
     * consultarExpedientesPorEtapa}
     * @param usuario El usuario del que se consultara un expediente.
     * @param idEtapa La etapa de la que se esta buscando el expediente.
     * @return Los expedientes con la informacion requerida.
     * @throws NSJPNegocioException En caso que usuario o idEtapa sean null
     */
    List<ExpedienteDTO> consultarExpedientesPorUsuarioYEtapa(UsuarioDTO usuario,
            Long idEtapa) throws NSJPNegocioException;

    /**
     * Consulta el expediente de Defensoria por etapa (dinamico) de un usuario
     * 
     * @param usuario El usuario del que se consultara un expediente.
     * @param etapaValorId La etapa de la que se esta buscando el expediente.
     * @return Los expedientes con la informacion requerida.
     * @throws NSJPNegocioException En caso que usuario o etapaValorId sean null
     */
    List<ExpedienteDTO> consultarExpedientesPorEtapaDefensoria(UsuarioDTO usuario,
            Long etapaValorId) throws NSJPNegocioException;
    
    /**
     * Consulta la siguiente informacion de un expediente, dado un expediente y
     * un usuario:
     * <ol>
     * <li> Numero de caso
     * <li> Numero de causa, que en Poder Judicial se cumple que el "numero de
     * causa" == "Numero de expediente"
     * <li> Etapa del expediente
     * <li> Nombre del imputado. Esta informacion se encuentra dentro de
     * ExpedienteDTO.involucrados[0] del expediente consultado.
     * <li> Delitos del imputado. Esta infomacion se encuentra dentro de
     * ExpedienteDTO.delitos
     * <li> Segun el CU "Enviar aviso de designaci&oacute;n" se deberia consultar la
     * informacion "del individuo para el que se solicita el defensor" pero esta
     * informacion es la misma que el "Nombre del imputado"
     * </ol>
     * @param expedienteDto El expediente del cual se obtendra su detalle.
     * @param usuarioDto El usuario (funcionario) asociado al expediente
     * @return El detalle del expediente con la informacion requerida.
     * @throws NSJPNegocioException En caso que alguna de las siguientes
     * condiciones se cumpla:
     * <ol>
     * <li> {@code expedienteDto == null}
     * <li> {@code expedienteDto.ExpedienteId == null}
     * <li> {@code usuarioDto == null}
     * </ol>
     */
    ExpedienteDTO consultarDetalleExpediente(ExpedienteDTO expedienteDto,
            UsuarioDTO usuarioDto) throws NSJPNegocioException;

    /**
     * Genera un nuevo n&uacute;mero de expediente.<br>
     * Al generar el n&uacute;mero guarda un expediente en la BD.
     * 
     * @param expedienteDTO
     *            Obligatorio la <b>fechaApertura</b> y <b>area.areaId</b>.
     *            Opcional <b>casoDTO</b>.
     * @return <ul>
     *         <li>expedienteId</il>
     *         <li>numeroExpediente</il>
     *         <li>fechaApertura</il>
     *         </ul>
     * @throws NSJPNegocioException
     *             En caso de ocurrir alg&uacute;n error.
     */
    public ExpedienteDTO asignarNumeroExpediente(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;

    /**
     * Asigna los nuevos n&uacute;meros de expediente al funcionario en sesi&oacute;n
     * @param listExpedientes
     * @return
     * @throws NSJPNegocioException
     */
    public Boolean asignarNumerosDeExpediente(List<ExpedienteDTO> listaExpedientes)
            throws NSJPNegocioException;
    
    /**
     * Genera un nuevo n&uacute;mero de expediente a partir de un turno.<br>
     * Al generar el n&uacute;mero guarda un expediente en la BD.
     * 
     * @param TurnoDTO
     *            Obligatorios <b>turnoId, usuario.idUsuario,
     *            expediente.area.areaId</b>.
     * @return <ul>
     *         <li>expedienteId</il>
     *         <li>numeroExpediente</il>
     *         <li>fechaApertura</il>
     * 
     *         </ul>
     * @throws NSJPNegocioException
     *             En caso de ocurrir alg&uacute;n error.
     */
    public ExpedienteDTO asignarNumeroExpediente(TurnoDTO turno)
            throws NSJPNegocioException;

    /**
     * Genera un nuevo n&uacute;mero de expediente a partir de un turno sin generar un caso.<br>
     * Al generar el n&uacute;mero guarda un expediente en la BD.
     * 
     * @param TurnoDTO
     *            Obligatorios <b>turnoId, usuario.idUsuario,
     *            expediente.area.areaId</b>.
     * @return <ul>
     *         <li>expedienteId</il>
     *         <li>numeroExpediente</il>
     *         <li>fechaApertura</il>
     * 
     *         </ul>
     * @throws NSJPNegocioException
     *             En caso de ocurrir alg&uacute;n error.
     */
    public ExpedienteDTO asignarNumeroExpedienteSinCaso(TurnoDTO turno)
            throws NSJPNegocioException;
    
    /**
     * Ejecura la b&uacute;squeda de expedientes en base al filtro.
     * 
     * @param filtrosBusquedaExpediente
     * @return Informaci&oacute;n b&aacute;sica de los expedientes
     * @throws NSJPNegocioException
     */
    public List<ExpedienteDTO> buscarExpedientes(
            FiltroExpedienteDTO filtrosBusquedaExpediente)
            throws NSJPNegocioException;
    
    /**
     * Ejecura la b&uacute;squeda de expedientes en base al filtro.
     * 
     * @param filtrosBusquedaExpediente
     * @return Informaci&oacute;n b&aacute;sica de los expedientes
     * @throws NSJPNegocioException
     */
    public List<ExpedienteDTO> buscarExpedientesPorNumExpDiscriminanteArea(
            FiltroExpedienteDTO filtrosBusquedaExpediente)
            throws NSJPNegocioException;
    
    /**
     * Consulta el detalle de un expediente en base a su identificador.
     * 
     * @param expedienteDTO
     *            Identificador del expdiente en el sistema e identificador del
     *            area: <b>expedienteId</b> y <b>area.areaId</b> <br>
     * @return El expediente.
     * @throws NSJPNegocioException
     *             En caso de ocurrir alg&uacute;n error de negocio al consultar.
     */
    ExpedienteDTO obtenerExpediente(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;
    /**
     * Consulta los expedientes de un caso para pintar el &aacute;rbol de
     * casos-expedientes.
     * 
     * @param caso
     *            caso.<b>casoId</b> es requerido.
     * @return Una lista con los siguientes valores de expedientes asignados:
     *         <ul>
     *         <li>expedienteId</il>
     *         <li>numeroExpediente</il>
     *         </ul>
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarExpedientesPorCaso(CasoDTO caso)
            throws NSJPNegocioException;
    /**
     * Consulta los expedientes que contengan un involucrado con el alias
     * enviado como parametro
     * 
     * @param filtrosBusquedaExpediente
     * @return Lista de involucrados con su expedinete correspondiente
     * @throws NSJPNegocioException
     */
    List<InvolucradoDTO> consultarExpedientesPorFiltros(
            FiltroExpedienteDTO filtrosBusquedaExpediente)
            throws NSJPNegocioException;
    
    /**
     * Consulta los expedientes que contengan un involucrado con el alias
     * enviado como parametro
     * 
     * @param filtrosBusquedaExpediente
     * @return Lista de involucrados con su expedinete correspondiente
     * @throws NSJPNegocioException
     */
    List<InvolucradoDTO> consultarExpedientesPorFiltrosYDiscriminante(
            FiltroExpedienteDTO filtrosBusquedaExpediente,UsuarioDTO usuarioFirmado)
            throws NSJPNegocioException;

    /**
     * Genera el expediente para el area de justicia alterna restaurativa
     * 
     * @param expedienteDTO
     * @return
     * @throws NSJPNegocioException
     *             expediente generado con su identificar
     */
    public ExpedienteDTO generarExpJusticaAltRest(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;

    /**
     * Obtiene los expedientes de acuerdo a una actividad, area y anio.
     * 
     * @param filtroExpedienteDTO
     * @return
     * @throws NSJPNegocioException
     */
    public List<ExpedienteDTO> consultarExpedienteActividadAreaAnio(
            FiltroExpedienteDTO filtroExpedienteDTO)
            throws NSJPNegocioException;
    
    /**
     * Busca los expedientes canalizador filtrando por &aacute;rea = UI y actividad = RECIBIR_CANALIZACION_UI
     * @param filtroExpedienteDTO
     * @return
     * @throws NSJPNegocioException
     */
    public List<ExpedienteDTO> consultarCanalizadosCoordinadorAmpGeneral(
            FiltroExpedienteDTO filtroExpedienteDTO)
            throws NSJPNegocioException;

   /**
    * Operaci&oacute;n que realiza la funcionalidad de relacionar el documento con el nï¿½mero de Causa
    * 
    * @param documento
    * @param el n&uacute;mero de causa
    * @return  
    * @throws NSJPNegocioException
    */
    public void asociarDocumentoCausaTOCA(DocumentoDTO documento, ExpedienteDTO causa, ActividadDTO actividadDTO)throws NSJPNegocioException;
    
	/**
	 * Este servicio permitir&aacute; al solicitante crear un n&uacute;mero de expediente y 
	 * asignarlo a un expediente, o en su caso crear un nuevo expediente en el sistema.
	 * La informaci&oacute;n que tendr&aacute; e nuevo n&uacute;mero de expediente es:
	 *     - Usuario firmado.
	 *   La informaci&oacute;n que tendr&aacute; el nuevo expediente es:
	 *     - N&uacute;mero de expediente.
	 *     - Usuario firmado.
	 * Para el manejo del N&uacute;mero de Expediente (NE) se tiene el siguiente formato:
	 *      LLL/II/DD/UU/AAAA/C-NNNNN   Ejeplo: 000/PR/15/RBO/2011/C-12345
	 *      
	 *      Longitud m&aacute;xima: 26 (caracteres incluyendo '/')
	 *      
	 * en donde se devide en 3 partes: 
	 * 
	 * Prefijo:  LLL/II/DD/UU   => 000/PR/15/RBO
	 *     Parte del NE que se podr&aacute; configurar en cuanto a la longitud.
	 *     Longitud m&aacute;xima: 13 (caracteres incluyendo '/')
	 *     En donde:
	 * 			L = Car&aacute;cter Libre para completar la cadena {3 caracteres mï¿½ximo}
	 * 			I = Prefijo Instituci&oacute;n {2 caracteres m&aacute;ximo}
	 * 			D = Prefijo Distrito {2 caracteres m&aacute;ximo}
	 * 			U = Prefijo Unidad {3 caracteres m&aacute;ximo}
	 *     
	 * AÃ±o: /AAAA/    => /2011/
	 * 	   Parte del NE que representa el aÃ±o actual del sistema, y es fijo.
	 * 	   Longitud exacta: 6 (caracteres incluyendo '/')
	 * 
	 * Subfijo:  C-NNNNN    => C-12345
	 * 		Parte del NE que es fija y representa el valor consecutivo del NE.
	 * 		Longitud exacta: 7 (caracteres incluyendo '-')
	 * 		En donde:
	 * 			C = Car&aacute;cter que es un consecutivo en el rango de {A-Z}
	 * 			N = N&uacute;mero entero que representa un consecutivo en el rango {0-9}
	 * 
	 *  //TODO GBP Falta determinar la concurrencia del servicio
	 *  
	 * @param usuarioDTO
	 * @param expedienteDTO
	 * @param institucionDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO crearAsignarNumeroExpediente(UsuarioDTO usuarioDTO,
			ExpedienteDTO expedienteDTO, InstitucionDTO institucionDTO)
			throws NSJPNegocioException;
	
    /**
     * Servicio que realiza la funcionalidad de consultar el &uacute;ltimo n&uacute;mero del
     * expediente que se tiene registrado en la Instituci&oacute;n
     *
     * Regresa el n&uacute;mero de expediente de tipo cadena, en caso contrario devuelve null.
     *
     * @param idInstitucion
     * @return
     * @throws NSJPNegocioException
     */
    public String consultarUltimoNumeroExpediente(Long idInstitucion) throws NSJPNegocioException;
	
	/**
	 * Servicio que realiza la funcionalidad de crear un expediente con:
	 * 	 Fecha de creaci&oacute;n: fecha actual del sistema.
	 *   Estatus: EstatusExpedeinte.ABIERTO
	 * Regresa un objeto de tipo Expediente, en caso contrario regresa NULL.
	 * 
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO crearExpediente () throws NSJPNegocioException;
        
        /**
	 * Servicio que genera el numero de caso a partir de
	 *   Estatus: EstatusExpedeinte.ABIERTO
	 * Regresa un objeto de tipo Expediente, en caso contrario regresa NULL.
	 * 
	 * @return
	 * @throws NSJPNegocioException
	 */
        public CasoDTO generarNuevoNUC(UsuarioDTO usuario, Long numeroExpedienteId)  throws NSJPNegocioException;
	
	
    /**
     * Servicio que realiza la funcionalidad de asociar el N&uacute;mero de Expediente a:
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
	 * Operaci&oacute;n que realiza la funcionalidad de asociar la solicitud a la carpeta de investigaci&oacute;n.
	 * Recibe como par&aacute;metros:
	 * - El n&uacute;mero de expediente.
	 * - El identificador de la solicitud.
	 * Regresa verdadero en caso de haber realizado de forma correcta la asociaci&oacute;n, 
	 * Arroja una excepci&oacute;n si se encontro con alg&uacute;n problema.
	 * 
	 * El proceso que sigue es el siguiente:
	 * 1.-Consulta la solicitud por id
     * 2.-Consulta el numero de expediente por NumeroExpediente (cadena)
     *    NOTA: El numero del expediente debe ser &uacute;nico, de acuerdo a la regla de negocio, 
     *          caso contrario, se arroja una excepci&oacute;n.
     * 3.-Asocia a la solicitud el expediente consultado.
     * 
	 * @param expedienteDTO
	 * @param idSolicitud
	 * @return
	 * @throws NSJPNegocioException
	 */
    public boolean asociarNumCarpetaASolicitud( ExpedienteDTO expedienteDTO, Long idSolicitud)throws NSJPNegocioException;
	
	/**
	 * Operaci&oacute;n que realiza la funcionalidad de consultar el prefijo definido para la instituci&oacute;n- &aacute;rea.
	 * Devuelve una cadena asignada al prefijo de acuerdo a lo siguiente:
	 * 		LLLL/II/DD/UU   {13 caracteres de longitud m&aacute;xima}
	 * 	En donde:		
	 * 		L = Car&aacute;cter Libre de relleno {3 caracteres}
	 * 		I = Prefijo Instituci&oacute;n {2 caracteres}
	 * 		D = Prefijo Distrito {2 caracteres}
	 * 		U = Prefijo Unidad {3 caracteres}
	 * 
	 * Notas: 
	 * 	1.- En caso de que el &aacute;rea no este asociado a un distrito se completara con -- Ejemplo:  000/PR/--/RBP 
	 *  2.- El car&aacute;cter libre permite completar la longitud del prefijo.
	 *  3.- El car&aacute;cter libre, en una futura implementaci&oacute;n ser&aacute; capturado por el usuario.
	 *  
	 * @param institucionDTO
	 * @return
	 */
	public String consultarPrefijo(InstitucionDTO institucionDTO) throws NSJPNegocioException;
	
	/**
     * Permite filtrar los Expediente en base a:
     * @param etapa Permite filtrar  por las difierentes etapas que puede tener un expediente
     * @param usuarioId Permite filtrar los expedientes designados a un defensor
     * @param areaId Representa el area a la que esta asociada el expediente
     * @return List<Expediente>
     */
    public List<ExpedienteDTO> consultarExpedientesPorEtapa(EtapasExpediente etapa, Long usuarioId,Long areaId) throws NSJPNegocioException;
    
    
    /**
     * Consulta los datos generales de un expediente en base a su identificador.
     * 
     * @param expedienteDTO
     *            Identificador del expdiente en el sistema e identificador del
     *            area: <b>expedienteId</b> y <b>area.areaId</b> <br>
     * @return Lista de documentos con los siguientes valores
		 * - id Documento
		 * - Tipo documento
		 * - Nombre documento
		 * - Fecha creaci&oacute;n
     * @throws NSJPNegocioException
     *             En caso de ocurrir alg&uacute;n error de negocio al consultar.
     */    
    public DatosGeneralesExpedienteDTO obtenerDatosGeneralesDeExpediente(ExpedienteDTO expedienteDTO)
            		throws NSJPNegocioException;
    
    
    /**
	 * Metodo que permite actualizar el tipo de Expediente
	 * @param expedienteDTO.NumeroExpedienteId: Representa el numero del expediente a actualizar
	 * @param tipo: Representa el tipo de expediente, usar enum TipoExpediente
	 * @throws NSJPNegocioException
	 */
	public void actualizarTipoExpediente(ExpedienteDTO expedienteDTO, OrigenExpediente tipo) throws NSJPNegocioException;	
	

    /**
     * Obtiene el NumeroExpediente, de acuerdo al numero expediente enviado como parametro
     * @author cesarAgustin
     * @param expedienteDTO
     * 			-Cadena de numero de expediente
     * @return
     * @throws NSJPNegocioException
     */
    public ExpedienteDTO obtenerNumeroExpedienteByNumExp(ExpedienteDTO expedienteDTO,UsuarioDTO usuario) throws NSJPNegocioException; 

	    /**
     * Obtiene el expedienteDTO a partir del numero de expediente
     * @param numeroExpediente
     * @return ExpedienteDTO
     * @throws NSJPNegocioException
     */
    ExpedienteDTO obtenerExpedientePorNumeroExpediente(String numeroExpediente)throws NSJPNegocioException;

    /**
     * Consulta los expedientes que tienen al menos un evento (audiencia o recurso) con fecha de evento
     * entre la fecha actual y el periodo hacia atr&aacute;s definido por el par&aacute;metro del sistmea de tiempo de 
     * consulta hist&oacute;rica y que sean de alg&uacute;n caso en particular
     * @param casoId caso a buscar
     * @param usuario Usuario que consulta
     * @return Lista de expediente con al menos un evento para el periodo hist&oacute;rico
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarExpedientesConEventosHistorico(Long casoId,UsuarioDTO usuario) throws NSJPNegocioException;
    
  
    /**
     * 
     * @return
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarNumeroExpedienteHistorico (UsuarioDTO usuario) throws NSJPNegocioException;
    
    /**
     * 
     * @param tipoExpediente
     * @param estatusExpediente
     * @return
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarNumeroExpedienteByEstatus (TipoExpediente tipoExpediente, EstatusExpediente estatusExpediente) throws NSJPNegocioException;

    
    /**
	 * Metodo que permite registrar/actualizar las notas asociadas a un Expediente
	 * @param expedienteDTO.NumeroExpedienteId: Representa el numero del expediente a actualizar
	 * @param notas: Son las NotasExpedienteDTO que se desean guardar/actualizar.
	 * 	Si se manda el campo NotaExpedienteDTO.idNota entonces se acutualiza la nota, 
	 *  en caso contrario se crea la nota  
	 * @throws NSJPNegocioException
	 */
	public List<NotaExpedienteDTO> registrarActualizarNotasExpediente(List<NotaExpedienteDTO> notas,  ExpedienteDTO expedienteDTO) throws NSJPNegocioException;
	
	/**
     * Permite consultar las notas asociadas a un expediente
	 * @param expedienteDTO.NumeroExpedienteId: Representa el numero del expediente 
     * @return List<NotaExpedienteDTO> 
     */
	public List<NotaExpedienteDTO> consultarNotasPorExpediente(ExpedienteDTO expedienteDTO) throws NSJPNegocioException;
	
	/**
     * Realiza algunas validaciones para saber si se debe de generar una nueva actividad en base de datos
     * y de esa forma queda canalizado el expediente:
     * @param inputExpediente: Atributos obligatorio
     * 		  - inputExpediente.ExpedienteId Representa el id del expediente que tendra la nueva actividad
     * 		  - inputExpediente.usuario.funcionario.claveFuncionario: Representa el usuario de sesion
     * @param actividadDTO
     * 		  - actividadDTO.tipoActividad : Representa el tipo de la actividad
     * 			* Actividades.CANALIZAR_JUSTICIA_ALTERNATIVA_RESTAURATIVA
     * 			* Actividades.CANALIZAR_UNIDAD_INVESTIGACION
     * @return
     * @throws NSJPNegocioException
     */
	public ActividadDTO canalizarCausa(ExpedienteDTO inputExpediente, ActividadDTO actividadDTO, Boolean bDelitoPrincipalGrave,
			Boolean bInputadoReincidente,
			Boolean bSeguimientoInterno) throws NSJPNegocioException;
	
	/**
	 * Funci&oacute;n que realiza la inserci&oacute;n y registro de los elementos de un acta circunstanciada relacionados a un expediente dado
	 * @param actaDTO
	 * @param expedienteDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO registrarActaCircunstanciada (ActaCircunstanciadaDTO actaDTO, ExpedienteDTO expedienteDTO) throws NSJPNegocioException;

	/**
	 * Consulta los expedientes relacionados al usuario de un area
	 * @author cesarAgustin
	 * @param usuarioDTO
	 * 			<li>idUsuario<li> identificador del usuario
	 * 			<li>areaActual<li> area del usuario 
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarExpedientesUsuarioArea (UsuarioDTO usuarioDTO) throws NSJPNegocioException;
	
	/**
	 * Consulta los expedientes de un &aacute;rea seg&uacute;n los filtros dados. 
	 * 
	 * @param usuarioDTO: Identificador del usuario firmado y el &aacute;rea a que pertenece (Obligatorio)
	 * @param area: Identificador del &aacute;rea remitente
	 * @param estatusExpediente: Identificador del estado por el cual se filtran los expedientes
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarExpedientePorAreaEstatusRemitente (UsuarioDTO usuarioDTO,AreaDTO area, 
			Long estatusExpediente)throws NSJPNegocioException;

	/**
	 * Consulta la etapa en la que se encuentra el Expediente identificado por <code>idExpediente</code>
	 * @param numeroExpedienteId identificador del expediente del cual se quiere conocer la etapa.
	 * @return Etapa actual en la que se encuentra el expediente
	 * @throws NSJPNegocioException en caso de que el expediente no pueda cambiar de etapa
	 */
	public EtapasExpediente consultarEtapaDelExpediente(Long numeroExpedienteId) throws NSJPNegocioException;

	/**
	 * Cambia la etapa del expediente identificado por <code>numeroExpedienteId</code> por <code>etapa</code>
	 * @param numeroExpedienteId expediente al cual se desea cambiar la etapa
	 * @param etapa nueva etapa del expediente
	 * @throws NSJPNegocioException
	 */
	public void cambiarEtapaDelExpediente(Long numeroExpedienteId, EtapasExpediente etapa) throws NSJPNegocioException;


	/**
	 * Consulta n&uacute;meros de expediente relacionados al caso de una solicitud
	 * @param solicitudId 
	 * @return Numeros de expediente encontrados
	 */
	List<ExpedienteDTO> consultarNumeroExpedientePorCasoDeSolicitud(Long solicitudId) throws NSJPNegocioException;
	/**
     * Asigna un numero de expediente a una solicitud
     * @param numeroExpedienteId Numero de expediente a asignar
     * @param solicitudId Solicitud a la cu&aacute;l ser&aacute; asignado
     */
    void asignarNumeroExpedienteASolicitud(Long numeroExpedienteId,Long solicitudId) throws NSJPNegocioException;

    /**
     * Consulta los numerosExpediente del tipo Causa que tengan un numeroExpediente de tipo carpeta de investigacion 
     * en el estatus cerrado.
     * @author cesarAgustin
     * @return
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarHistoricoCausasExpediente() throws NSJPNegocioException;
    
    /**
     * Consulta los numerosExpediente del tipo carpeta de ejecucion perteneciente a la causa enviada oomo parametro
     * @author cesarAgustin
     * @param expedienteDTO
     * @return
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarCarpetasEjecucionPorCausa (ExpedienteDTO expedienteDTO) throws NSJPNegocioException;
    /**
	 * Metodo que permite consultar las causas asociadas a un caso
	 * @param idCaso
	 * @return List<ExpedienteDTO>
	 * @throws NSJPNegocioException
	 */
    List<ExpedienteDTO> consultarCausasPorIdCaso(Long idCaso) throws NSJPNegocioException;

	/**
	 * Permite actualizar el reponsable a un conjunto de numerosExpediente
	 * @param idNumeroExpediente
	 * @param idFuncionario
	 * @throws NSJPNegocioException
	 */
	public void asociarExpedienteAFuncionario(Long idNumeroExpediente, Long idFuncionario) throws NSJPNegocioException;
    /**
	 * Consulta numeros de expediente de cierto tipo (TOCA, CAUSA o CARPETA) en base a los filstros enviados como parametro
	 * si un filtro es nulo entonces no se considera en la cosulta
	 * @param fechaInicio L&iacute;mite inferior para la fecha de apertura
	 * @param fechaFin L&iacute;mite superior para la fecha de apertura
	 * @param usuario Usuario al cu&aacute;l debe de pertenecer el expediente
	 * @param tipo Tipo de expediente
	 * @param numeroExpedientePadreId Si existe el par&aacute;metro se consultan los n&uacute;mero de expedientes
	 * cuya causa padre sea la enviada en este par&aacute;metro
	 * @return Lista con los n&uacute;meros de expediente encontrados
	 */
	List<ExpedienteDTO> consultarExpedientesPorFiltro(Date fechaInicio,Date fechaFin,FuncionarioDTO usuario,
			TipoExpediente tipo,Long numeroExpedientePadreId) throws NSJPNegocioException;
	
	
	/**
     * Consulta n&uacute;meros de expediente asociados a cierto caso
     * @param caso
     * @return
     */
	List<ExpedienteDTO> consultarNumeroExpedientePorNumeroCaso(String caso) throws NSJPNegocioException;
	/**
	 * Operaci&oacute;n que permite realizar la consulta de un Acta Circunstanciada
	 * @param expedienteDTO: idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	ActaCircunstanciadaDTO consultarActaCircunstaciada( ExpedienteDTO expedienteDTO)throws NSJPNegocioException;
	
	/**
     * Enviar Carpera de Procuraduria a Defensoria
     * 
     * Declaracion del servicio que invoca al WS para, de acuerdo a: 
     * numeroGeneralCaso. Para consultar los expedientes asociados al caso, en Procuraduria.
     * folioSolicitud: Folio de la solicitud a la cual se va a guardar la informacion del lado de Defensoria.
     * 
     * @param numeroGeneralCaso  Del caso asociado al expediente
     * @param folioSolicitud  De defensoria en donde se guradar&aacute; la informaci&oacute;n solicitada del expediente de procuraduria
     * @return  expedienteDTO asociado a la consulta obtenida, incluyendo los involucrados y objetos del expediente 
     * @throws NSJPNegocioException
     */
    public ExpedienteDTO enviarCarpetaDeInvestigacion(
    		String numeroGeneralCaso, String folioSolicitud)
            throws NSJPNegocioException;
    
    
    /**
	 * Servicio que permite actualizar el estatus del NumeroExpediente.
	 * Se requiere el Id del Expediente y el Valor del Estatus. 
	 * 
	 * @param expedienteDTO
	 * @return 
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO actualizarEstatusExpediente(ExpedienteDTO expedienteDTO) throws NSJPNegocioException;

	/**
     * Servicio para consultar Nota por Id
     * 
     * @param idNota
     * @return
     * @throws NSJPNegocioException
     */
    NotaExpedienteDTO consultarNotaPorId(Long idNota)throws NSJPNegocioException ;
    
	/**
	 * Obtiene numerosExpediente de un determinado tipo que se encuntren en un estatus especifico
	 * @author cesarAguistin
	 * @param tipoExpediente
	 * @param estatusExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ExpedienteDTO> consultarNumeroExpedienteByTipoYEstatus (TipoExpediente tipoExpediente, EstatusExpediente estatusExpediente) throws NSJPNegocioException;

	/**
     * Obtiene numerosExpediente que se encuntren en un estatus especifico
     * @author vaguirre
     * @param estatusExpediente
     * @return
     * @throws NSJPNegocioException
     * 
     * 
     * 
     */
    List<ExpedienteDTO> consultarNumeroExpedienteByEstatus (EstatusExpediente estatusExpediente) throws NSJPNegocioException;
    
    /**
     * Obtiene los numeros de expedientes por estatus que contienen un hecho DTO
     * y un avisoHechoDelictivo, con el discriminante del usuario que pasamos como parametro
     * @param estatusExpediente
     * @param usuario
     * @param fechaInicio
     * @param fechaFin
     * @return Lista de Expdientes que contienen un hechoDTO
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarNumeroDeExpedienteConHechoPorFiltros(EstatusExpediente estatusExpediente, UsuarioDTO usuario,Date fechaInicio,Date fechaFin) throws NSJPNegocioException;
    
    /**
     * Obtiene los numeros de expedientes por estatus que contienen un hecho DTO 
     * y NO contienen un avisoHechoDelictivo, con el discriminante del usuario que pasamos como parametro
     * @param estatusExpediente
     * @param usuario
     * @param fechaInicio
     * @param fechaFin
     * @return Lista de Expdientes que contienen un hechoDTO
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarNumeroDeExpedienteSinHechoPorFiltros(EstatusExpediente estatusExpediente, UsuarioDTO usuario,Date fechaInicio,Date fechaFin) throws NSJPNegocioException;
    
	/**
	 * Obtiene la informacion a detalle de la carpeta de ejecucion
	 * @author cesarAgustin
	 * @param expedienteDTO
	 * 			<li>numeroExpedienteId<li>
	 * @return Carpeta ejecucion obtenida
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO obtenerDetalleCarpetaEjecucion (ExpedienteDTO expedienteDTO) throws NSJPNegocioException;

	/**
	 * Obtiene los total de expedientes por mes de acuerdo al rango de fechas.
	 * @author cesarAgustin
	 * @param fechaInicial
	 * @param fechaFin
	 * @return
	 */
	List<Object[]> expedientesPorMes(Date fechaInicial, Date fechaFin);

	/**
	 * Obtiene el total por mes de los numeros de expediente que se encuentren en un estatus
	 * determinado y dentro de un rango de fechas.
	 * @author cesarAgustin
	 * @param inicial
	 * @param fin
	 * @param archivoTemporal
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<Object[]> obtenerNumExpPorEstatusYMes(Date inicial, Date fin,
			EstatusExpediente estatusExpediente) throws NSJPNegocioException;
	
    /**
     * Servicio que recupera el id del Expediente a partir del id del N&uacute;mero Expediente
     * @param expedienteDTO Requerido <b>numeroExpedienteId</b>.
     * @return expedienteId.
     * @throws NSJPNegocioException
     */
    Long obtenerExpedienteIdPorNumExpId(ExpedienteDTO expedienteDTO)
    throws NSJPNegocioException;
    
    /**
	 *	Servicio que realiza la consulta por Usuario (Funcionario), &aacute;rea y Estatus
	 *	El &aacute;rea se encuentra denstro del usuarioDTo
	 * 	En caso de que no pase el estatus, se consulta de todos los expedientes por estatus.
	 * 
	 * @param usuarioDTO
	 * @param expedienteDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarExpedientesPorUsuarioAreaEstatus(
			UsuarioDTO usuarioDTO, Long estatus) throws NSJPNegocioException ;
	

	/**
	 * Servicio para crear un nuevo de Auditor&iacute;a, que permita asignar un nuevo n&uacute;mero de expediente, n&uacute;mero de auditor&iacute;a, 
	 * considerando el registro en la relaci&oacute;n: RelNumExpedienteAuditoria
	 * 
	 * @param listaNumeroExpedienteAuditados
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ExpedienteDTO>  asignarNumeroExpedienteAuditoria(List<ExpedienteDTO> listaNumeroExpedienteAuditados) throws NSJPNegocioException;
	
	/**
	 * Servicio que permite la consulta de Numeros de Visitaduria de acuerdo a:
	 * ** RelNumExpedienteAuditoria
	 *   -relNumExpedienteAuditoriaId  Id de la relaci&oacute;n.
	 *   -numeroAuditoriaId   Id del nuevo n&uacute;mero de expediente generado por la auditoria.
	 *   
	 * ** Numero Expediente Auditado (numExpedienteAuditado)
	 *   -numeroExpedienteId
	 *   -jerarquiaOrganizacional.jerarquiaOrganizacionalId
	 *   -funcionario.claveFuncionario
	 *   -estatus.valorId
	 *   
	 * ** Numero Carpeta de Auditoria 
	 *   -numeroExpedienteId
	 *   --jerarquiaOrganizacional.jerarquiaOrganizacionalId
	 *   -funcionario.claveFuncionario
	 *   -estatus.valorId
	 *  
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<RelNumExpedienteAuditoriaDTO> consultarNumeroVisitaduriaPorFiltro(RelNumExpedienteAuditoriaDTO filtro )  throws NSJPNegocioException;

	ExpedienteDTO obtenerExpedienteDefensoria(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException;
	/**
	 * Permite consultar RelNumExpedienteAuditoria en base a un numero de auditoria
	 * @param idAuditoria: Identificador del numero de auditoria (Numero de expediente)
	 * @return RelNumExpedienteAuditoria
	 * @throws NSJPNegocioException
	 */
	RelNumExpedienteAuditoriaDTO consultarRelacionPorIdAuditoria(Long idAuditoria) throws NSJPNegocioException;

	/**
	 * Servicio que permite consultar los funcionarios, visitadores, por
	 * -Departamento del expediente Auditado 
	 * -Estatus de la relaci&oacute;n
	 *  
	 * @param idEstatus
	 * @param idDepartamento
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<FuncionarioDTO> consultarFuncionariosPorEstatusDeparamento(Long idEstatus, Long idDepartamento) 
		throws NSJPNegocioException;
	
	/**
	 * Obtiene la informacion del expediente si el funcionario tiene permisos sobre el numeroExpediente.
	 * @author cesarAgustin
	 * @param claveFuncionario
	 * @param numExpId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO consultarNumExpPorFuncionario(Long claveFuncionario,
			Long numExpId) throws NSJPNegocioException;
	
	/**
	 * 
	 * @param claveFuncionario
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarNumExpPorFuncionario(
			Long claveFuncionario) throws NSJPNegocioException;
	
	/**
	 * 
	 * @param claveFuncionario
	 * @param numExpId
	 * @throws NSJPNegocioException
	 */
	public void asignarPermisoExpedienteFuncionario(Long claveFuncionario, Long numExpId, Date fechaLimite, Boolean permiso, UsuarioDTO usuarioFirmado) throws NSJPNegocioException;
	
	/**
	 * 
	 * @param claveFuncionario
	 * @param numExpId
	 * @throws NSJPNegocioException
	 */
	public void eliminarPermisoExpedienteFuncionario(Long claveFuncionario, Long numExpId, UsuarioDTO usuarioFirmado) throws NSJPNegocioException;

	/**
	 *  Se hace la busqueda del Expediente al que se encuentra asociado, 
	 *  despues se hace la busqueda por el expedienteId y el Area del o los 
	 *  expedientes bajo ese criterio. Si se obtienen varios NEx por dicho filtro
	 *  se considera solo el primero.  
	 * 
	 * @param numeroExpediente
	 * @param areaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO consultarExpedienteRelacionadoAArea (String numeroExpediente, Long areaId  ) throws NSJPNegocioException ;
	/**
	 * Servicio que permite crear un nuevo Expediente, asi mismo copiara la informaci&oacute;n del:
	 * - Probable Responsable
	 * - Victima
	 * - Delito 
	 * descritos en delitoPersonaDTO
	 * @param delitoPersonaDTO
	 * @return ExpedienteDTO
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO generarNuevoExpedienteUAVD(DelitoPersonaDTO delitoPersonaDTO) throws NSJPNegocioException ;
	
	
    /**
     * Consulta el resumen con los datos generales de un expediente en base al identificador de la solicitud.
     * 
     * @param solicitudDTO
     *            Identificador de la solicitud
     * @return DatosGeneralesExpedienteUAVDDTO con el Resumen del expediente
     * @throws NSJPNegocioException
     *             En caso de ocurrir alg&uacute;n error de negocio al consultar.
     */    
    public DatosGeneralesExpedienteUAVDDTO obtenerResumenDeExpedienteUAVD(SolicitudDTO solicitudDTO)
            		throws NSJPNegocioException;
    
    
    /**
     * Metodo que permite crear un nuevo numero de expediente, expediente y su caso
     * @param expedienteDTO
     * @return ExpedienteDTO
     * @throws NSJPNegocioException
     */
    public ExpedienteDTO generarExpediente(ExpedienteDTO expedienteDTO)
		throws NSJPNegocioException;

    /**
     * Metodo que permite crear un nuevo numero de expediente, expediente y su caso
     * @param expedienteDTO
     * @return ExpedienteDTO
     * @throws NSJPNegocioException
     */
    public ExpedienteDTO generarExpedienteSinCaso(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;

    /**
	 * Servicio utilizado para obtener los expedientes recibidos de IPH, es decir, 
	 * remision IPH. 
	 * 
	 * @param estatusExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ExpedienteDTO> buscarRemisionesConIPH(EstatusExpediente estatusExpediente)throws NSJPNegocioException;
	/**
	 * Servicio utilizado para obtener los expedientes recibidos de IPH, es decir, 
	 * remision IPH. 
	 * 
	 * @param estatusExpediente,Cat discriminate
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ExpedienteDTO> buscarRemisionesConIPH(EstatusExpediente estatusExpediente,Long idDiscriminante)throws NSJPNegocioException;
	
    /**
     * Consulta el detalle de un expediente en base a su identificador.
     * 
     * @param expedienteDTO
     *            Identificador del expediente en el sistema e identificador del
     *            area: <b>expedienteId</b> y <b>area.areaId</b> <br>
     * @return El expediente.
     * @throws NSJPNegocioException
     *             En caso de ocurrir alg&uacute;n error de negocio al consultar.
     */
    ExpedienteDTO obtenerExpedientePorExpedienteId(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;
    
   
    /**
     * Consulta los expedientes de acuerdo a los filtros,
     * si las fechas de los filtros es null, no se tomar&aacute; en cuenta,
     * los demas parametros son requeridos.
     * 
     * @param fechaInicio
     * @param fechaFin
     * @param usuario
     * @param estatusExpediente
	 * @param idFuncionario Permite filtrar los Numeros de expedientes por responsable
     * idDistrito
     * @return
     * @throws NSJPNegocioException
     */
	List<ExpedienteDTO> consultarExpedientesPorFiltroST(Date fechaInicio,
			Date fechaFin, UsuarioDTO usuario,
			List<Long> estatusExpediente, Long idDiscriminante , Long rolId, Long idDistrito, Long idFuncionario) throws NSJPNegocioException;
	
	/**
	 * Servicio que consulta en que area se ha iniciado un expediente
	 * @param expediente: expedienteId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public JerarquiaOrganizacionalDTO consultarOrigendeExpediente (ExpedienteDTO expediente)throws NSJPNegocioException;
	
	/**
	 * Permite actualizar el discriminante de un expediente
	 * @param expediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Boolean actualizarCatDiscriminanteDeExpediente (ExpedienteDTO expedienteDto) throws NSJPNegocioException;

	/**
	 * @param relNumExpedienteAuditoriaDTO
	 * 		*relNumExpedienteAuditoriaDTO.numeroExpediente.Area area de la cual se requieren los num expedientes 
	 * 		*relNumExpedienteAuditoriaDTO.numeroExpediente.Estatus estatus en el cual deben estar los num expedientes
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<RelNumExpedienteAuditoriaDTO> consultarNumeroVisitaduriaUIE(
			RelNumExpedienteAuditoriaDTO relNumExpedienteAuditoriaDTO) throws NSJPNegocioException;
	

	/**
	 * Permite consultar numeros de expedientes asociados a un identificador de expediente
	 * que tengan como responsable a un usuario que cuente con el rol asociado.
	 * @param roles
	 * @param idExpediente
	 * @param nuevoEstatusNumeroExpediente
	 * @param nuevoEstatusExpediente
	 * @throws NSJPNegocioException
	 */
	public void actualizarEstatusNumerosDeExpedientesPorRolST(
			List<Long> roles, Long idExpediente,Long nuevoEstatusNumeroExpediente , Long nuevoEstatusExpediente) throws NSJPNegocioException;
	
	/**
	 * Perimite consultar el estatus del numero de expediente por numero expediente Id
	 * @param numeroExpedienteId
	 * @return Estatus Expediente
	 * @throws NSJPNegocioException
	 */
	public Long consultarEstatusNumeroExpedienteByNumeroExpedienteId(Long numeroExpedienteId) throws NSJPNegocioException;

	/**
	 * M&eacute;todo que reasigna un Funcionario a una lista de N&uacute;meros De Expedientes
	 * 
	 * @param lstExpedienteDTO N&uacute;mero del Expediente y clave del nuevo Funcionario responsable. 
	 * @param rolActivo Rol activo en sesion  
	 * @return VERDADERO si realizo la reasignaci&oacute;n de lo contrario regresa FALSO.
	 * @throws NSJPNegocioException
	 */
	
	Boolean reasignarFuncionarioDeNumeroExpediente(List<ExpedienteDTO> lstExpedienteDTO, HistoricoExpedienteDTO historicoExpedienteDTO, RolDTO rolActivo)
			throws NSJPNegocioException;
	
	/**
	 * Permite obtener un expediente en base al numero de Expediente y al numero de caso
	 * @param numeroExpediente
	 * @param numCaso
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO obtenerExpedientePorNumeroExpedienteYNumeroCaso(
			String numeroExpediente, String numCaso) throws NSJPNegocioException;
	/**
	 * Permite obtener una lista con los numeros de expedientes que pertenecen al expediente
	 * @param idExpediente de tipo ExpedienteDTO
	* @return List<String>
	 * @throws NSJPNegocioException
	 */
	public List<String> buscarNumerosExpedientesByIdExpediente(ExpedienteDTO idExpediente)throws NSJPNegocioException;
	
	
	/**
     * Consulta dedicada para el coordinadorAT en la cual se basa mediante los filtros de su menu.
     * @param filtroExpedienteDTO
     * @return List<ExpedienteDTO>
     * @throws NSJPNegocioException
     */
	public List<ExpedienteDTO> consultarExpedienteCoorAT(FiltroExpedienteDTO filtroExpedienteDTO)throws NSJPNegocioException ;
	
	/**
     * Asigna un numero de caso.<br>
     * 
     * @param expedienteDTO
     *            Obligatorio la <b>idExpediente</b> y <b>NumeroGeneralCaso</b>.
     * @return expedienteDTO
     * @throws NSJPNegocioException
     *             En caso de ocurrir alg&uacute;n error.
     */
    public ExpedienteDTO asignarNumeroCasoSolicitudDefensor(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;
    
    
	/**
	 * M&eacute;todo ocupado por las consultas de expedientes en la
	 * instituci&oacute;n de Reinserci&oacute;n social para buscar expedientes
	 * en base al n&uacute;mero de expediente asociado y el &aacute;rea del
	 * usuario que est&aacute; llevando a cabo la consulta.
	 * 
	 * @param filtro
	 *            - DTO en donde se van a cargar los datos en base a los cuales
	 *            se va a consultar la informaci&oacute;n de la base de datos
	 *            filtrada sobre los campos anteriores.
	 * @return List<ExpedienteDTO> - Lista con los expedientes que cumplieron
	 *         con los filtros ingresados como par&aacute;metro.
	 * @throws NSJPNegocioException
	 *             - En el caso de que no se hayan pasado los argumentos
	 *             suficientes para llevar a cabo la consulta.
	 */
	List<ExpedienteDTO> consultarExpedientesRSPorNumeroExpediente(
			FiltroExpedienteDTO filtro) throws NSJPNegocioException;
	/**
	 * Consulta la información relacionada a un expediente
	 * @param tipoBusqueda tipoBusqueda Representa que tipo de busqueda se desea ejucutar
	 * @param valores Representa los parametros necesario para realizar la busqueda
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteViewDTO> consultarExpedientesConSP(TipoDeBusquedaDeExpediente tipoBusqueda,
			HashMap<String, String> valores) throws NSJPNegocioException;
	
	/**
     * Consulta el detalle de un expediente en base a su identificador.
     * 
     * @param expedienteDTO
     *            Identificador del expediente en el sistema e identificador del
     *            area: <b>expedienteId</b> <b></b> <br>
     * @return El expediente.
     * @throws NSJPNegocioException
     *             En caso de ocurrir alg&uacute;n error de negocio al consultar.
     */
    public ExpedienteDTO consultarExpedientePorExpedienteId(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;
		
	/**
	 * Consulta el expediente &uacute;nico asociado a un caso, por n&uacute;mero de caso y discriminante del
	 * usuario 
	 * @param casoDto
	 * @param usuarioDto
	 * @return ExpedienteDTO encontrado
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO consultarExpedientePorNumeroDeCaso(CasoDTO casoDto,
			UsuarioDTO usuarioDto) throws NSJPNegocioException;
	
	/**
	 * Permite actualizar la jerarquia organizacional de un numerosExpediente
	 * @param idNumeroExpediente
	 * @param jerarquiaOrganizacional
	 * @throws NSJPNegocioException
	 */
	public void actualizarJerarquiaOrganizacionalANumExp(Long idNumeroExpediente, Long jerarquiaOrganizacional) throws NSJPNegocioException;
	
	
	
	/**
	 * Permite saber si un expediente tiene canalizaciones a UI, si ya tiene entonces permite saber
	 * si el expediente ya fue asignado o no por el coordinador
	 * @param idNumeroExpediente
	 * @return 0 No tiene canalizaciones
	 * 		   1 No ha sido asignado por el coordinador
	 *         2 Ya fue asignado por el coordinador
	 */
	public int obtenDetalleDeCanalizacionDeNumeroExpediente(Long idNumeroExpediente)throws NSJPNegocioException;
	
	
	public ExpedienteViewDTO consultarGeneralesDeHistorialDeExp(Long idNumeroExpediente) throws NSJPNegocioException;
	
	public List<ExpedienteDTO> consultarNumerosExpedientesPorIdExpediente(Long idExpediente,List<Long> idsJeraruqiasOrganizacionales)throws NSJPNegocioException;
	
	/**
	 * Permite dejar los registros como inactivos en la bitacora para el funcionario y expediente asociados
	 * @param idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<BitacoraPermisoExpedienteDTO> obtenerPermisosDeExpediente(Long idExpediente) throws NSJPNegocioException;
	/**
	 * Permite consultar los registros en bitacora asociados a un identificador de expediente
	 * @param expedienteId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<BitacoraEstatusNumExpedienteDTO> consultarBitacoraEstatusNumExpedientePorIdExpediente(Long expedienteId) throws NSJPNegocioException;

	/**
	 * Servicio que se encarga de consultar la jerarquia de las etapas, 
	 * donde la raíz son los padres y las hojas son las hijas asociadas 
	 * a cada padre. 
	 * Aplica para las etapas de 
	 * 		-Expediente
	 * 		-Involucrado 
	 * Dependiento de la bandera que se está pasando como
	 * parametro. 
	 * Utilizado para Defensoria
	 *  
	 * @param esEtapaExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<CatEtapaDTO> consultarEtapasJerarquiaPorPadre(Boolean esEtapaExpediente)
			throws NSJPNegocioException;
	
	/**
	 * Permite acutalizar la UIE del expediente
	 * @param expedienteDTO
	 * @throws NSJPNegocioException
	 */
	public void actualizarCatUIEDeExpediente(ExpedienteDTO expedienteDTO) throws NSJPNegocioException;
	
	/**
	 * Permite consultar el Origen de un expediente
	 * @param idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ValorDTO consultaOrigenExpediente(Long idExpediente) throws NSJPNegocioException;

	/**
	 * Recupera los expedientes que pueden ser reasignados por el rol de Coordinador Policia Ministerial
	 * Cosnultara los expedientes bajo los siguientes filtros:
	 * - Area de policia ministerial
	 * - Por distrito
	 * - Por el responsable del expediente
	 * 
	 * Adem&acute;s de que consulta los expedientes que existen en Unidad de Investigación 
	 * con una solicitud de línea de investigación pero aún no tienen un expediente en Policía Ministerial.  
	 * @param filtroExpedienteDTO
	 * @return
	 */
	public List<ExpedienteDTO> buscadorDeExpedientesAReasignarPM(FiltroExpedienteDTO filtroExpedienteDTO)throws NSJPNegocioException;
	
	
	/**
	 * Metodo que permite reasignar una lista de expedientes, permitira:
	 * - Actualiza el responsable del expediente en policía ministerial
	 * - Si existiera información del policía de la actividad anterior de Generar denuncia, lo cambia a la nueva actividad(Generar denuncia en PM)
	 * - Cambia del responsable anterior al nuevo la actividad de generar denuncia en policía ministerial
	 * - Actualiza la solicitud de línea de investigación para que pueda ser vista por el nuevo responsable
	 * - Registrar en bitacora (HistoricoExpediente)
	 * @param idNumerosExpedientes
	 * @param idNuevoFuncionario
	 * @param usuario
	 * @throws NSJPNegocioException
	 */
    public void reasignarNumerosDeExpedientePM(List<Long> idNumerosExpedientes, Long idNuevoFuncionario, UsuarioDTO usuario, Long idFuncionarioActual) throws NSJPNegocioException;

	
    /**
     * Permite consultar la lista de expedientes realacionados a una consulta ciudadana
     * @param apaterno
     * @param amaterno
     * @param nombre
     * @param expediente
     * @return
     * @throws NSJPNegocioException
     */
    public List<ExpedienteViewDTO> consultaCiudadana(String apaterno, String amaterno, String nombre, String expediente)
	throws NSJPNegocioException;
    
}
