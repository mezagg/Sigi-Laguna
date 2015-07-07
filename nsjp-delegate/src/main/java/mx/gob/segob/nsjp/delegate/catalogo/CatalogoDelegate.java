/**
 * 
 */
package mx.gob.segob.nsjp.delegate.catalogo;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.SalaAudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatAreasNegocioDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatFaltaAdministrativaDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.DiaInhabilDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.delito.CausaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.rol.FiltroRolesDTO;
import mx.gob.segob.nsjp.dto.usuario.FuncionDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;

/**
 * @author vaguirre
 * 
 */
public interface CatalogoDelegate {
    /**
     * Recupera un catalogo.
     * 
     * @param cat
     *            Identificador del catalogo a recuperar.
     * @return Los registros del catalogo
     * @throws NSJPNegocioException
     *             En caso de no poder recuperar el catalogo.
     * 
     */
    List<CatalogoDTO> recuperarCatalogo(Catalogos cat)
            throws NSJPNegocioException;
    /**
     * Recupera un catalogo con todas las columnas del catalogo.
     * 
     * @param cat
     *            Identificador del catalogo a recuperar.
     * @return Los registros del catalogo (llave, valor y demás columnas).
     * @throws NSJPNegocioException
     *             En caso de no poder recuperar el catalogo.
     * 
     */
    List<CatalogoDTO> recuperarCatalogoCompleto(Catalogos cat)
            throws NSJPNegocioException;
    /**
     * Recupera un catalogo dependiente
     * 
     * @param catHijo
     *            Identificador del catalogo a recuperar.
     * @param idValorPadre
     *            Valor de la llave del registro del catalogo padre
     * @return Los registros del catalogo asociado al catalogo padre
     * @throws NSJPNegocioException
     *             En caso de no poder recuperar el catalogo.
     */
    List<CatalogoDTO> recuperarCatalogoDependiente(Catalogos catHijo,
            Long idValorPadre) throws NSJPNegocioException;

    /**
     * Recupera la lista de asentamientos que cumplan con los criterios. <br>
     * Si algún criterio es <code>null</code> no se tomará en cuenta para el
     * filtrado. <br>
     * Al menos un filtro tiene que ser diferente de <code>null</code>.
     * 
     * @param idMpio
     * @param idCiudad
     * @param idTipoAsentamiento
     * @return
     * @throws NSJPNegocioException
     *             Lanzada en caso de que los tres filtros sean
     *             <code>null</code>.
     */
    List<CatalogoDTO> consultarAsentamiento(Long idMpio, Long idCiudad,
            Long idTipoAsentamiento) throws NSJPNegocioException;
    
    /**
     * COnsulta catalogo de delitos.
     * @return Lista de delitos
     * @throws NSJPNegocioException
     */
    List<CatDelitoDTO> consultarDelito() throws NSJPNegocioException;
    
    /**
     * Servicio que permite almacenar un nuevo día inhabil de acuerdo a los atributos recibidos en el dto
     * @param dto información del día inhabil que se va a almacenar
     * @return identificador del nuevo día habil
     * @throws NSJPNegocioException
     */
	public Long guardarDiaInhabil(DiaInhabilDTO dto) throws NSJPNegocioException;
	
	/**
	 * Servicio que permite eliminar un día inhabil previamente existene en el sistema, denominado por dto
	 * @param dto día inhabil a eliminar del sistema
	 * @throws NSJPNegocioException
	 */
	public void eliminarDiaInhabil(DiaInhabilDTO dto) throws NSJPNegocioException;
	
	/**
	 * Consulta los días inhabiles registrados en el sistema
	 * @return lista con los días inhabiles mapeados en obejtos DíaInhabilDTO;
	 * @throws NSJPNegocioException
	 */
	public List<DiaInhabilDTO> consultarDiasInhabiles() throws NSJPNegocioException;
	
	/**
	 * Consulta los días inhabiles registrados en el sistema por Mes
	 * @return lista con los días inhabiles mapeados en obejtos DíaInhabilDTO;
	 * @throws NSJPNegocioException
	 */
	public List<DiaInhabilDTO> consultarDiasInhabilesPorMes(Short mes) throws NSJPNegocioException;
    
    /**
     * Consulta los roles del sistema.
     * @return Lista de roles del sistema
     * @throws NSJPNegocioException
     */
	public List<RolDTO> consultarRoles(FiltroRolesDTO filtroRolesDTO) throws NSJPNegocioException;
	
	/**
	 * Consultar las funciones de los roles del sistema
	 * @author cesarAgustin
	 * @return Lista de funciones
	 * @throws NSJPNegocioException
	 */
	public List<FuncionDTO> consultarFunciones() throws NSJPNegocioException;
	
	/**
	 * Consultar catalogo de faltas administrativas
	 * @author mgallardo	
	 * @return Lista de Faltas administrativas
	 * @throws NSJPNegocioException
	 */
	public List<CatFaltaAdministrativaDTO> consultarCatalogoFaltaAdministrativa() throws NSJPNegocioException;
	
	/**
     * Se consulta las &Aacute;reas de acuerdo a un jerarquiaResponsableId y, opcionalmente, una 
     * lista de ids de &Aacute;reas y Departamentos que no van a ser considerados.
     * En caso de que no se pase el ID de la JerarquiaResponsable
     * se toma la instituci&oacute;n actual del sistema.
     * 
	 * @param jerarquiaResponsableId
	 * @param idsJerarquiaOrgADescartar
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<JerarquiaOrganizacionalDTO> consultarDepartamentosExceptoAreasYDepartamentos(Long jerarquiaResponsableId, 
			List<Long> idsAreasDescartar, List<Long> idsDepartamentoDescartar)	throws NSJPNegocioException ;
	
    /**
     * Servicio que se encarga de recuperar el cat&aacute;logo de delitos por cualquiera, 
     * o combinaci&oacute;n, de los siguientes filtros:
     * -catDelitoId
     * -claveDelito
     * -nombre-esGrave
     * -esAccionPenPriv
     * 
     * @param catDelitoFiltro
     * @return
     * @throws NSJPNegocioException
     */
    List<CatDelitoDTO> consultarCatDelitoPorFilro(CatDelitoDTO catDelitoFiltroDTO) throws NSJPNegocioException;

    /**
     * Servicio que se encarga de recuperar el catálogo de delitos exceptuando
     * los que están agregados al grid de delitos denunciados
     * 
     * @param idsGrid
     * @return
     * @throws NSJPNegocioException
     */    
    List<CatDelitoDTO> consultarDelitosSeleccionables(String idsGrid) throws NSJPNegocioException;
    
    /**
	 * Consulta un catDelito mediante su id
	 * @param catDelitoId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public CatDelitoDTO consultarCatDelitoPorId(Long catDelitoId) throws NSJPNegocioException;
	
	/**
	 * Consulta una catAreasNegocio mediante su id
	 * @param catAreaNegId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public CatAreasNegocioDTO consultarCatAreaNegocioPorId(Long catAreaNegId) throws NSJPNegocioException;
	
	/**
	 * Permite guardar o actualizar un objeto de tipo CatDelitoDTO
	 * @param catDelitoDto
	 * @return el objeto con su id, si ocurre un error devuelve 
	 * @throws NSJPNegocioException
	 */
	public CatDelitoDTO guardarActualizarCatDelito(CatDelitoDTO catDelitoDto) throws NSJPNegocioException;
	
	
	/**
	 *  Permite guardar o actualizar un objeto de tipo CatAreasNegocioDTO
	 * @param CatAreasNegocioDto
	 * @return
	 * @throws NSJPNegocioException
	 */
	public CatAreasNegocioDTO guardarActualizarCatAreasNegocio(CatAreasNegocioDTO CatAreasNegocioDto) throws NSJPNegocioException;
	
	/**
	 * Permite eliminar un objeto de tipo catDelito 
	 * @param catDelitoId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long eliminarCatDelito(Long catDelitoId) throws NSJPNegocioException;
	
	/**
	 * Permite eliminar un objeto de tipo catAreasNegocio
	 * @param catDelitoId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long eliminarCatAreasNegocio(Long catAreasNegocioId) throws NSJPNegocioException;

	/**
	 * Servicio para consultar una Sala Audiencia por medio de SalaAudienciaId
	 * 
	 * @param salaAudienciaDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	SalaAudienciaDTO consultarSalaAudiencia(
			SalaAudienciaDTO salaAudienciaDTO) throws NSJPNegocioException;

	/**
	 * Servicio que se encarga de actualizar o modificar la sala de Audiencia (Con o sin Sala JAVS)
	 * donde se muestran los siguientes casos:
	 * Si NO existe el Id (null) de Sala Audiencia: Guardar la Sala Audiencia con la Sala JAVS
	 * Si existe el ID de Sala Auidiencia: se modifica la Sala Audiencia con la Sala JAVS.
	 * 
	 * El servicio que permite la modificación de la SalaAudiencia, considerando la 
	 * modificación de la SalaJavs
	 * En caso de que se cambie el estatus de Inactivo de la SalaAudiencia, 
	 * la SalaJAvs se elimina fisicamente.
	 * 
	 * @param salaAudienciaDTO
	 * @throws NSJPNegocioException
	 */
	SalaAudienciaDTO guardarActualizarSalaAudiencia(SalaAudienciaDTO salaAudienciaDTO) throws NSJPNegocioException;
	
    /**
     * Servicio que se encarga de recuperar el catálogo de areas de negocio
     * @return
     * @throws NSJPNegocioException
     */    
    List<CatAreasNegocioDTO> consultarAreasDeNegocio() throws NSJPNegocioException;
    
	/**
	 * Permite consultar una falta Administrativa por identificador
	 * @param idFaltaAdministrativa
	 * @return
	 * @throws NSJPNegocioException 
	 */
	public CatFaltaAdministrativaDTO consultarFaltaAdministrativa(Long idFaltaAdministrativa) throws NSJPNegocioException;
	
	/**
	 * Permite consultar un Delito en base a su identificador
	 * @param idDelito
	 * @return
	 * @throws NSJPNegocioException
	 */
	public CatDelitoDTO consultarDelito(Long idDelito) throws NSJPNegocioException;
	
	/**
	 * Permite consultar Distritos por medio de Web Services
	 * @param target
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<CatDistritoDTO> consultarDistritos(Instituciones target) throws NSJPNegocioException;
	
	/**
	 * Permite consultar la institucion actual por clave
	 * @param clave
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ConfInstitucionDTO consultarIntitucionPorClave(String clave) throws NSJPNegocioException;
	
	/**
	 * Permite consultar los estatus de expediente diferentes ordenados ascendentenmente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ValorDTO> consultarEstatusDeExpedientesDiferentes() throws NSJPNegocioException;
	
	/**
	 * Permite definir el a�o inicial para la busqueda avanzada de expediente por anio,
     * Con dicho parametro se cargara un combobox con la fecha inicial hata el a�o actual
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<Integer> consultarAniosParaBusquedaAvanzadaExpediente() throws NSJPNegocioException;
	
	/**
	 * Consultar el cat&aacute;logo de delitos por cualquiera, o combinaci&oacute;n, de los 
	 * siguientes filtros:
	 *  -catDelitoId
	 *  -claveDelito
	 *  -nombre
	 *  -esGrave
	 *  -esAccionPenPriv
	 *  Asimismo filtra las categor&iaacute;s de delito que se encuentran previamente asociadas 
	 *  a un expediente.
	 *  
	 * @param catDelitoFiltro - Objeto en el cual se ingresa la informaci&oacute;n de la cual 
	 * 							se van a filtrar los resultados.
	 * @return List<CatDelitoDTO> - Lista de CatDelitoDTO con los registros que cumplieron con 
	 * 								los criterios de b&uacute;squeda.
	 */
	public List<CatDelitoDTO> consultarCatDelitoPorFiltroExpediente(CatDelitoDTO catDelitoFiltro, 
			ExpedienteDTO expediente) throws NSJPNegocioException;
	
	/**
	 * Consulta el catalogo de causas de vehiculos en base al idPadre enviado, en caso de ser nulo
	 * traera todas las causas padre
	 * @return List(CausaDTO)
	 * @throws NSJPNegocioException
	 */
	public List<CausaDTO> consultarCausasVehiculo(Long idPadre) throws NSJPNegocioException;
}
