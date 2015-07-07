/**
 *
 * Nombre del Programa : BuscarExpedienteService.java                                    
 * Autor                            : Cesar Agustin                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 05/04/2011 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Interface del servicio para obtener expedientes                      
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

package mx.gob.segob.nsjp.service.expediente;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoDeBusquedaDeExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.BitacoraEstatusNumExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.BitacoraPermisoExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.DatosGeneralesExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.DatosGeneralesExpedienteUAVDDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteViewDTO;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Este caso de uso permitir&aacute; al usuario buscar expediente, seg&uacute;n el criterio
 * seleccionado y los datos ingresados, tomando en cuenta la instituci&oacute;n a la
 * que pertenece, el cargo e identificador del usuario.
 * 
 * @version 1.0
 * @author CesarAgustin
 * @version 1.0
 */
public interface BuscarExpedienteService {
    /**
     * Hace la b&uacute;squeda de expedientes a partir del filtro.
     * 
     * En caso de que requiere consultar todos los expedientes por N&uacute;mero de 
     * expediente, sin considerar el &aacute;rea al que pertenece el usuario, 
     * se debe mandar el filtro sin un usuario.
     *  
     * @param filtrosBusquedaExpediente
     *            Filtro de consulta.
     * @return Lista de expedientes que coincidan con el filtro.
     * @throws NSJPNegocioException
     *             En caso de que exista un problema al consultar.
     */
    public List<ExpedienteDTO> buscarExpedientes(
            FiltroExpedienteDTO filtrosBusquedaExpediente)
            throws NSJPNegocioException;
    
    /**
     * Hace la b&uacute;squeda de expedientes a partir del Numero de Expediente
     * y Discriminante 
     * En caso de que la institucion sea PJ no filtra por clave de usuario
     * (jerarquia organizacional), pero Si filtra por discriminante para todas
     * las instituciones 
     *  
     * @param filtrosBusquedaExpediente
     *            Filtro de consulta.
     * @return Lista de expedientes que coincidan con el filtro.
     * @throws NSJPNegocioException
     *             En caso de que exista un problema al consultar.
     */
    public List<ExpedienteDTO> buscarExpedientesPorNumExpDiscriminanteArea(
            FiltroExpedienteDTO filtrosBusquedaExpediente)
            throws NSJPNegocioException;
    
    

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
    ExpedienteDTO obtenerExpediente(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;
    
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
    public ExpedienteDTO obtenerExpedientePorExpedienteId(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;
    
    /**
     * Servicio que recupera el id del Expediente a partir del id del N&uacute;mero Expediente
     * @param expedienteDTO Requerido <b>numeroExpedienteId</b>.
     * @return expedienteId.
     * @throws NSJPNegocioException
     */
    Long obtenerExpedienteIdPorNumExpId(ExpedienteDTO expedienteDTO)
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
     * 
     */
    public List<InvolucradoDTO> buscarExpedientesPorFiltros(
            FiltroExpedienteDTO filtrosBusquedaExpediente)
            throws NSJPNegocioException;
    /**
     * Consulta los expedientes que contengan un involucrado con el alias
     * enviado como parametro
     * 
     * @param filtrosBusquedaExpediente
     * @return Lista de involucrados con su expedinete correspondiente
     * @throws NSJPNegocioException
     * 
     */
    public List<InvolucradoDTO> buscarExpedientesPorFiltrosYDiscriminante(
            FiltroExpedienteDTO filtrosBusquedaExpediente,UsuarioDTO usuarioFirmado)
            throws NSJPNegocioException;

    /**
     * 
     * @param filtroExpedienteDTO
     * @return
     * @throws NSJPNegocioException
     */
    public List<ExpedienteDTO> consultarExpedienteActividadAreaAnio(
            FiltroExpedienteDTO filtroExpedienteDTO)
            throws NSJPNegocioException;

    /**
     * Busca los expedientes canalizados filtrando por &aacute;rea = UI y actividad = RECIBIR_CANALIZACION_UI
     * @param filtroExpedienteDTO
     * @return
     * @throws NSJPNegocioException
     */
    public List<ExpedienteDTO> consultarCanalizadosCoordinadorAmpGeneral(
            FiltroExpedienteDTO filtroExpedienteDTO)
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
    public DatosGeneralesExpedienteDTO obtenerDatosGeneralesExpediente(ExpedienteDTO expedienteDTO)
            		throws NSJPNegocioException;
					
    /**
     * Servicio que obtiene el expedienteDTO a partir del numero de expediente
     * @param numeroExpediente
     * @return ExpedienteDTO
     * @throws NSJPNegocioException
     */
    ExpedienteDTO obtenerExpedientePorNumeroExpediente(String numeroExpediente)throws NSJPNegocioException;
    
    /**
     * Servicio que obtiene el expedienteDTO a partir del numero de expediente y el numero de caso
     * @param numeroExpediente
     * @param numCaso
     * @return ExpedienteDTO
     * @throws NSJPNegocioException
     */
    ExpedienteDTO obtenerExpedientePorNumeroExpedienteYNumeroCaso(String numeroExpediente, String numCaso)throws NSJPNegocioException;
    
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
     * Obtiene el NumeroExpediente, de acuerdo al numero expediente enviado como parametro
     * @author cesarAgustin
     * @param expedienteDTO
     * 			-Cadena de numero de expediente
     * @return
     * @throws NSJPNegocioException
     */
    ExpedienteDTO obtenerNumeroExpedienteByNumExp(ExpedienteDTO expedienteDTO,UsuarioDTO usuario) throws NSJPNegocioException;
    

    /**
     * 
     * @return
     */
	public List<ExpedienteDTO> consultarNumeroExpedienteHistorico(UsuarioDTO usuario) throws NSJPNegocioException;
	
	/**
	 * 
	 * @param tipoExpediente
	 * @param estatusExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarNumeroExpedienteByEstatus(
			TipoExpediente tipoExpediente, EstatusExpediente estatusExpediente) throws NSJPNegocioException;

	/**
	 * Consulta los expedientes relacionados al usuario de un area
	 * @author cesarAgustin
	 * @param usuarioDTO
	 * 			<li>idUsuario<li> identificador del usuario
	 * 			<li>areaActual<li> area del usuario 
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarExpedientesUsuarioArea(
			UsuarioDTO usuarioDTO) throws NSJPNegocioException;

	/**
	 * Consulta los expedientes de un &aacute;rea seg&uacute;n los filtros dados. 
	 * 
	 * @param usuarioDTO: Identificador del usuario firmado y el &aacute;rea a que pertenece (Obligatorio)
	 * @param area: Identificador del &aacute;rea remitente
	 * @param estatusExpediente: Identificador del estado por el cual se filtran los expedientes
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarExpedientePorAreaEstatusRemitente(
			UsuarioDTO usuarioDTO, AreaDTO area,
			Long estatusExpediente)throws NSJPNegocioException;

	/**
     * Consulta los numerosExpediente del tipo Causa que tengan un numeroExpediente de tipo carpeta de investigacion 
     * en el estatus cerrado.
     * @author cesarAgustin
     * @return
     * @throws NSJPNegocioException
     */
	public List<ExpedienteDTO> consultarHistoricoCausasExpediente() throws NSJPNegocioException;

	/**
     * Consulta los numerosExpediente del tipo carpeta de ejecucion perteneciente a la causa enviada oomo parametro
     * @author cesarAgustin
     * @param expedienteDTO
     * @return
     * @throws NSJPNegocioException
     */
	public List<ExpedienteDTO> consultarCarpetasEjecucionPorCausa(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException;
	
	
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
	List<ExpedienteDTO> consultarNumeroExpedientePorNumeroCaso(String caso);

	/**
	 * Obtiene numerosExpediente de un determinado tipo que se encuntren en un estatus especifico
	 * @author cesarAguistin
	 * @param tipoExpediente
	 * @param estatusExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarNumeroExpedienteByTipoYEstatus(
			TipoExpediente tipoExpediente, EstatusExpediente estatusExpediente,UsuarioDTO usuario) throws NSJPNegocioException;
	
	/**
	 * Obtiene los numeros de expedientes por estatus que contienen un hecho DTO 
	 * y contienen un avisoHechoDelictivo
     * y con el discriminante del usuario que pasamos como parametro
	 * @param estatusExpediente
	 * @param usuario
	 * @param FechaInicio
	 * @param FechaFin
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarNumeroDeExpedienteConHechoPorFiltros(
			EstatusExpediente estatusExpediente, UsuarioDTO usuario,
			Date fechaInicio, Date fechaFin) throws NSJPNegocioException;

	
	/**
	 * Obtiene los numeros de expedientes por estatus que contienen un hecho DTO
	 * y NO contienen avisoHechoDelictivo 
     * y con el discriminante del usuario que pasamos como parametro
	 * @param estatusExpediente
	 * @param usuario
	 * @param FechaInicio
	 * @param FechaFin
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarNumeroDeExpedienteSinHechoPorFiltros(
			EstatusExpediente estatusExpediente, UsuarioDTO usuario,
			Date fechaInicio, Date fechaFin) throws NSJPNegocioException;

	
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

	public ExpedienteDTO obtenerExpedienteDefensoria(ExpedienteDTO expedienteDTO) throws NSJPNegocioException;

	/**
	 * Obtiene la informacion del expediente si el funcionario tiene permisos sobre el numeroExpediente.
	 * @author cesarAgustin
	 * @param claveFuncionario
	 * @param numExpId
	 * @return
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO consultarNumExpPorFuncionarioYNumExp (Long claveFuncionario, Long numExpId) throws NSJPNegocioException;
	
	/**
	 * 
	 * @author cesarAgustin
	 * @param claveFuncionario
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ExpedienteDTO> consultarNumExpPorFuncionario (Long claveFuncionario) throws NSJPNegocioException;

	/**
	 * 
	 * @param claveFuncionario
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ExpedienteDTO> consultarExpedientesDelFuncionario(
			UsuarioDTO usuario, List <ValorDTO> estatusExpedientes) throws NSJPNegocioException;

	/**
	 * Consulta los nu&uacute;meros de expedientes que tiene permisos el funcionario, 
	 * considerando el edificio del funcionario
	 * @param claveFuncionario
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ExpedienteDTO> consultarExpedientescConPermisoFuncionario(
			Long claveFuncionario) throws NSJPNegocioException; 
			
	/**
	 * Consulta los nu&uacute;meros de expedientes que tiene permisos el
	 * funcionario, adem&aacute;s de considerar que el n&uacute;mero de
	 * expediente sea de la misma a&acute;rea del rol con el cual se ha logeado el usuario.
	 * 
	 * @param usuarioDTO
	 * @param claveFuncionarioPermiso
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ExpedienteDTO> consultarExpedientesPermisoFuncionarioJerarquiaRol(
			UsuarioDTO usuarioDTO, Long claveFuncionarioPermiso) throws NSJPNegocioException;

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
	ExpedienteDTO consultarExpedienteRelacionadoAArea (String numeroExpediente, Long areaId  ) throws NSJPNegocioException;
	
    /**
     * Consulta el resumen con los datos generales de un expediente en base al identificador de la solicitud.
     * 
     * @param solicitudDTO
     *            Identificador de la solicitud
     * @return DatosGeneralesExpedienteUAVDDTO con el Resumen del expediente
     * @throws NSJPNegocioException
     *             En caso de ocurrir alg&uacute;n error de negocio al consultar.
     */    
	public DatosGeneralesExpedienteUAVDDTO obtenerResumenDeExpedienteUAVD(
			SolicitudDTO solicitudDTO) throws NSJPNegocioException;
	
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
     * Consulta los expedientes de acuerdo a los filtros,
     * si las fechas de los filtros es null, no se tomar&aacute; en cuenta,
     * los demas parametros son requeridos.
     * 
     * @param fechaInicio
     * @param fechaFin
     * @param usuario
     * @param estatusExpediente
	 * @param rolId representa el id del Rol de los funcionarios responsables del Numero de expediente
	 * @param idFuncionario Permite filtrar los Numeros de expedientes por responsable  
     * @return
     * @throws NSJPNegocioException
     */
	List<ExpedienteDTO> consultarExpedientesPorFiltroST(
			Date fechaInicio, Date fechaFin, UsuarioDTO usuario,
			List<Long> estatusExpediente, Long discriminanteId, Long rolId, Long idDistrito, Long idFuncionario)throws NSJPNegocioException;

	/**
	 * Consultar los expedientes ligados al caso enviado como parametro, realizando la consulta por el idcaso
	 * @param caso
	 * @return
	 * @throws NSJPNegocioException
	 * @author CesarAgustin
	 */
	List<ExpedienteDTO> consultarExpedientesPorIdCaso(CasoDTO caso)
			throws NSJPNegocioException;
	
	/**
	 * Permite actualizar el estatus de numeros de expedientes asociados a un expediente asociados a una lista de Roles
	 * @param roles Lista de roles (Opciona)
	 * @param idExpediente (Obligatorio)
	 * @param nuevoEstatusNumeroExpediente (Obligatorio)
	 * @param nuevoEstatusExpediente (opcional)
	 * @return
	 * @throws NSJPNegocioException
	 */
	public void actualizarEstatusNumerosDeExpedientesPorRolST(
			List<Long> roles, Long idExpediente,Long nuevoEstatusNumeroExpediente , Long nuevoEstatusExpediente) throws NSJPNegocioException;

	/**
	 * Permite obtener una lista con los numeros de expedientes que pertenecen al expediente
	 * @param idExpediente de tipo ExpedienteDTO
	 * @return List<String>
	 * @throws NSJPNegocioException
	 */
	public List<String> buscarNumerosExpedientesByIdExpediente(
			ExpedienteDTO idExpediente) throws NSJPNegocioException;
	
	
	/**
     * Consulta dedicada para el coordinadorAT en la cual se basa mediante los filtros de su menu.
     * @param filtroExpedienteDTO
     * @return List<ExpedienteDTO>
     * @throws NSJPNegocioException
     */
	public List<ExpedienteDTO> consultarExpedienteCoorAT(FiltroExpedienteDTO filtroExpedienteDTO)throws NSJPNegocioException ;
	
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
	 * Consulta el expediente &uacute;nico asociado a un caso, por n&uacute;mero de caso y discriminante del
	 * usuario 
	 * @param casoDto
	 * @param usuarioDto
	 * @return ExpedienteDTO encontrado
	 * @throws NSJPNegocioException
	 */
	public ExpedienteDTO consultarExpedientePorNumeroDeCaso(CasoDTO casoDto,
			UsuarioDTO usuarioDto) throws NSJPNegocioException;
	
	/**
	 * Permite consultar el detalle de general de un historial de un expediente 
	 * @param idNumeroExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ExpedienteViewDTO consultarGeneralesDeHistorialDeExp(Long idNumeroExpediente) throws NSJPNegocioException;
	
	
	/**
	 * Consulta los Numeros de expediente asociados a un expediente. 
	 * @param expedienteId
	 * @param lstAreasId Parametro opcional que permite filtrar solo los expedientes que 
	 * pertenescana a las areas indicadas
	 * @return
	 */
	public List<ExpedienteDTO> consultarNumerosExpedientesPorIdExpediente(Long idExpediente,List<Long> idsJeraruqiasOrganizacionales) throws NSJPNegocioException;
	
	
	/**
	 * Permite saber si un expediente tiene canalizaciones a UI, si ya tiene entonces permite saber
	 * si el expediente ya fue asignado o no por el coordinador
	 * @param idNumeroExpediente
	 * @return 0 No tiene canalizaciones
	 * 		   1 No ha sido asignado por el coordinador
	 *         2 Ya fue asignado por el coordinador
	 */
	public int obtenDetalleDeCanalizacionDeNumeroExpediente(Long idNumeroExpediente)throws NSJPNegocioException;

	/**
	 * Permite dejar los registros como inactivos en la bitacora para el funcionario y expediente asociados
	 * @param numExpId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<BitacoraPermisoExpedienteDTO> obtenerPermisosDeExpediente(Long numExpId) throws NSJPNegocioException;
	
	/**
	 * Permite consultar los registros en bitacora del asociados a un identificador de expediente
	 * @param expedienteId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<BitacoraEstatusNumExpedienteDTO> consultarBitacoraEstatusNumExpedientePorIdExpediente(Long expedienteId) throws NSJPNegocioException;

	/**
	 * Permite obtener el origen de un expediente
	 * @param idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ValorDTO consultaOrigenExpediente(Long idExpediente)throws NSJPNegocioException;
	

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
	public List<ExpedienteDTO> buscadorDeExpedientesAReasignarPM(FiltroExpedienteDTO filtroExpedienteDTO) throws NSJPNegocioException;
	
	
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
