/**
 * Nombre del Programa : FuncionarioDelegate.java Autor : cesarAgustin Compania
 * : Ultrasist Proyecto : NSJP Fecha: 12 May 2011 Marca de cambio : N/A
 * Descripcion General : Contrato de metodos para el acceso a los servicios de
 * Funcionario Programa Dependiente :N/A Programa Subsecuente :N/A Cond. de
 * ejecucion :N/A Dias de ejecucion :N/A Horario: N/A MODIFICACIONES
 * ------------------------------------------------------------------------------
 * Autor :N/A Compania :N/A Proyecto :N/A Fecha: N/A Modificacion :N/A
 * ------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.delegate.funcionario;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.CriterioConsultaFuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoViewDTO;
import mx.gob.segob.nsjp.dto.solicitud.PermisoSolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.dto.tarea.EventoCitaDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Contrato de metodos para el acceso a los servicios de Funcionario.
 *
 * @version 1.0
 * @author cesarAgustin
 *
 */
public interface FuncionarioDelegate {

    /**
     * Consulta el Funcionario por n&uacute;mero de empleado
     *
     * @param FuncionarioDTO el n&uacute;mero de empleado del funcionario
     * @return El Funcionario asociado al n&uacute;mero de empleado solicitado
     *
     * @throws NSJPNegocioException En caso que alguno de {@code evidenciaDto} o
     * {@code evidenciaDto.evidenciaId} sea {@code null}.
     */
    List<FuncionarioDTO> consultarFuncionarioXNumeroEmpleado(FuncionarioDTO evidenciaDto)
            throws NSJPNegocioException;

    /**
     * Consulta el Perito (Funcionario) asociado a la evidencia.
     *
     * @param evidenciaDto La evidencia de la que se busca su funcionario.
     * @return El Perito (Funcionario) asociado a la evidencia o {@code null} en
     * caso que la evidencia buscada no exista o que no tenga un Perito
     * (Funcionario) asociado.
     * @throws NSJPNegocioException En caso que alguno de {@code evidenciaDto} o
     * {@code evidenciaDto.evidenciaId} sea {@code null}.
     */
    FuncionarioDTO consultarPeritoPorEvidencia(EvidenciaDTO evidenciaDto)
            throws NSJPNegocioException;

    /**
     * Devuelve un listado de PERITOS que coinciden con el nombre, apellido
     * paterno y apellido materno que se le pasan dentro del objeto
     *
     * @{code criterios}
     * @return Una lista que contiene los peritos que coinciden con el nombre
     * del perito que se le pasa como par&acute;metro o la lista vac&iacute;a en
     * caso de no encontrar coincidencias.
     * @throws NSJPNegocioException
     */
    List<FuncionarioDTO> consultarPeritosPorNombre(FuncionarioDTO criterios)
            throws NSJPNegocioException;

    /**
     * Consulta los defensores disponibles en la defensoria publica
     *
     * @return Defensores encontrados
     */
    public List<FuncionarioDTO> consultarDefensoresActivos() throws NSJPNegocioException;

    /**
     * Servicio para obtener los defensores activos de acuerdo al distrito
     *
     * @param distrito
     * @return List<FuncionarioDTO>
     * @throws NSJPNegocioException
     */
    List<FuncionarioDTO> consultarDefensoresActivosPorDistrito(
            CatDistritoDTO distrito) throws NSJPNegocioException;

    /**
     * Servicio para obtener la informacion referente al defensor
     *
     * @param defensor
     * @return FuncionarioDTO
     * @throws NSJPNegocioException
     */
    FuncionarioDTO obtenerInformacionDefensor(FuncionarioDTO defensor) throws NSJPNegocioException;

    /**
     * Servicio que designa a un funcionario defensor a una solicitud de
     * defensoria
     *
     * @param solicitudDefensorDTO
     * @throws NSJPNegocioException
     */
    public void designarAbogadoDefensor(SolicitudDefensorDTO solicitudDefensorDTO) throws NSJPNegocioException;

    /**
     * Consulta el funcionario asociado a un numero de expediente.
     *
     * @param expediente El numero de expediente al que esta asociado el
     * funcionario que estamos buscando.
     * @return El funcionario asociado al expediente o @{code null} en caso de
     * no existir el expediente.
     * @throws NSJPNegocioException
     */
    FuncionarioDTO consultarFuncionarioXExpediente(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException;

    /**
     * Operaci&oacute;n que realiza la funcionalidad de asociar el Perito
     * seleccionado con el n&uacute;mero de expediente Recibe el perito y el
     * n&uacute;mero de expediente
     *
     * @param peritoDto
     * @param expediente
     * @throws NSJPNegocioException
     */
    public void asociarPeritoExpediente(
            FuncionarioDTO peritoDto, ExpedienteDTO numeroExpediente)
            throws NSJPNegocioException;

    /**
     * Metodo que permite consultar los defensores
     *
     * @return Devuelve un listado de defensores
     * @throws NSJPNegocioException
     * @author ricardo
     */
    public List<FuncionarioDTO> consultarDefensores() throws NSJPNegocioException;

    /**
     * Metodo que permite consultar los defensores de acuerdo a un tipo de
     * Defensa
     *
     * @param idTipoDefensa Recibe el tipo de defensa que se va a consultar.
     * @return Devuelve un listado de defensores que ejercen ese tipo de
     * Defensa.
     * @author ricardo
     */
    public List<FuncionarioDTO> consultarDefensorPorTipoDefensa(Long idTipoDefensa) throws NSJPNegocioException;

    /**
     * Registra un periodo de vacaciones o incapacidad para el funcionario
     * definido por <code>funcionario</code>
     *
     * @param funcionario Representa al funcionario al cual se le va a asociar
     * el periodo de vacacioines o incapacidad, es obligatorio que el atributo
     * <code>claveFuncionario</code> contenga un valor diferente de
     * <code>null</code>.
     * @param periodo Objeto de tipo EventoCitaDTO que describe el periodo de
     * vacaciones o incapadiad que ser&aacute; registrado para el funcionario,
     * los aributos obligatorios son <code>fechaInicioEvento</code> Fecha en que
     * inicia el per&iacute;odo <code>fechaFinEvento</code> Fecha en que termina
     * el per&iacute;odo <code>tipoEvento</code> Tipo de Evento que registra el
     * per&iacute;odo Vacaiones o Incapacidad <code>nombreEvento</code> Nombre
     * del per&iacute;odo
     * @return identifador conn el que se registro el periodo en la BDD
     * @throws NSJPNegocioException
     */
    public void registrarVacacionesIncapacidad(FuncionarioDTO funcionario, EventoCitaDTO periodo, UsuarioDTO usuario) throws NSJPNegocioException;

    /**
     * Servicio que permite consultar una lista de funcionarios por su puesto
     * Este metodo sera usado para obtener la informacion de: -Abogado defensor.
     * -Coordinador de defensores. -Coordinador de fiscales. -Coordinador de
     * servicios periciales. -Fiscal general. -Fiscal. -Juez. -Magistrado.
     *
     * @param idPuesto: Los identificadores se pueden obtener del enum Puestos
     * @return FuncionarioDTO con el nombre completo y el puesto
     * @throws NSJPNegocioException
     */
    public List<FuncionarioDTO> consultarFuncionariosPorRol(Long idPuesto)
            throws NSJPNegocioException;

    /**
     * Operaci&oacute;n que realiza la funcionalidad de obtener del conjunto de
     * defensores de un tipo de Defensa, aquellos que tiene los valores de carga
     * de trabajo m&aacute;s baja o menor.
     *
     * Para obtener un subconjunto fue necesario hacer lo siguiente: 1.-Ordenar
     * la lista de funcionarios haciendo uso de la interfaz Comparable y clase
     * Collections 2.-Obtener el promedio de carga de trabajo de los
     * funcionarios 3.-Descartar aquellos funcionarios que tengan una carga de
     * trabajo mayor al promedio.
     *
     * Recibe el conjunto de defensores del tipo de Defensa.
     *
     * @param ldefensoresDTO lista de defensores
     * @return Devuelve un listado o subconjunto de Defensores.
     * @throws NSJPNegocioException
     */
    List<FuncionarioDTO> obtenerDefensoresConCargaMenor(List<FuncionarioDTO> ldefensoresDTO) throws NSJPNegocioException;

    /**
     * Operaci&oacute;n que realiza la funcionalidad de seleccionar de un
     * conjunto de elementos, uno aleatoriamente.
     *
     * Recibe el listado o conjunto de elementos
     *
     * Devuelve un elemento de ese listado seleccionado aleatoriamente.
     *
     * @param lista
     * @return
     * @throws NSJPNegocioException
     */
    public Object asignarAleatoriamenteElemento(Object[] lista) throws NSJPNegocioException;

    /**
     * Obtiene un listado de funcionarios que pertenecen al areada identificcada
     * por <code>area</code> tienen un puesto identificado por
     * <code>puesto</code>
     *
     * @param area identificador del &aacute;rea de la cual se obtendr&aacute;n
     * los funcionarios
     * @param puesto identificador del puesto que tienen los funcionaros
     * @return lista de funcionarios que satisfacen la b&uacute;squeda
     * @throws NSJPNegocioException
     */
    public List<FuncionarioDTO> consultarFuncionariosPorAreayPuesto(Long area, Long puesto) throws NSJPNegocioException;

    /**
     * Consultar el listado de funcionarios de acuerdo al rol. Requiere como
     * parametro el Id del Rol, extraido de la enumeraci&oacute;n de Roles.
     *
     * @param idRol
     * @return
     * @throws NSJPNegocioException
     */
    List<FuncionarioDTO> consultarFuncionariosPorRolMultiRol(Long idRol)
            throws NSJPNegocioException;

    /**
     * Operaci&oacute;n que realiza la funcionalidad de consultar el personal o
     * los funcionarios adscritos a un departamento de la Instituci&oacute;n.
     *
     * @param idDepartamento
     * @return
     * @throws NSJPNegocioException
     */
    public List<FuncionarioDTO> consultarFuncionarioPorDepartamento(Long idDepartamento) throws NSJPNegocioException;

    /**
     * Metodo que permite guardar la informacion de un Funcionario
     *
     * @param peritoDTO: Informacion del Perito
     * @return El identificador con el que fue guardado el Funcionario
     * @throws NSJPNegocioException
     */
    public FuncionarioDTO ingresarFuncionario(FuncionarioDTO peritoDTO)
            throws NSJPNegocioException;

    /**
     * Metodo que permite modificar la informaci&oacute;n del Defensor
     *
     * @param defensorDTO: Informacion del Defensor
     * @return Datos que se guardaron del Defensor
     * @throws NSJPNegocioException
     */
    public FuncionarioDTO modificarDefensor(FuncionarioDTO defensorDTO)
            throws NSJPNegocioException;

    /**
     * Obtiene la lista de los funcionarios subordinados, del usuario que
     * realiza la consulta
     *
     * @param usuario Rol activo del usuario,discriminante y catUIE si es
     * necesario
     * @return Lista de funcionarios subordinados
     * @throws NSJPNegocioException
     */
    public List<FuncionarioDTO> consultarFuncionariosSubordinados(UsuarioDTO usuario) throws NSJPNegocioException;

    /**
     * Obtiene el defensor asignado al expediente enviado como parametro
     *
     * @param expedienteDTO
     * @return El funcionario asignado al expediente requerido
     * @throws NSJPNegocioException
     */
    public FuncionarioDTO obtenerDefensorAsignadoAExpediente(ExpedienteDTO expedienteDTO) throws NSJPNegocioException;

    /**
     *
     * @return @throws NSJPNegocioException
     */
    public List<FuncionarioDTO> obtenerFuncionariosConUsuario() throws NSJPNegocioException;

    /**
     * Obtiene la informacion del funcionario superior, del funcionario que es
     * enviado como parametro
     *
     * @author cesarAgustin
     * @param funcionarioDTO
     * <li>claveFuncionario<li> Identificador del funcionario
     * @return Funcionario superior obtenido
     * @throws NSJPNegocioException
     */
    public FuncionarioDTO obtenerFuncionarioSuperior(FuncionarioDTO funcionarioDTO) throws NSJPNegocioException;

    FuncionarioDTO consultaFuncionarioPorNombreInstitucionYPuesto(FuncionarioDTO funcionarioDto) throws NSJPNegocioException;

    /**
     * Servicio que consultar los funcionarios asociados a un departamento y los
     * asociados al &aacute;rea, a la que pertenece el departamento.
     *
     * @param idDepartamento
     * @return
     * @throws NSJPNegocioException
     */
    List<FuncionarioDTO> consultarFuncionarioPorDepartamentoYArea(Long idDepartamento)
            throws NSJPNegocioException;

    /**
     * Obtiene el funcionario de acuerdo al nombre completo.
     *
     * @author cesarAgustin
     * @param nombreCompleto
     * @return
     * @throws NSJPNegocioException
     */
    FuncionarioDTO obtenerFuncionarioPorNombreCompleto(String nombreCompleto) throws NSJPNegocioException;

    /**
     *
     * @param claveFuncionario
     * @return
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarExpedientesDelFuncionario(UsuarioDTO usuario, List<ValorDTO> estatusExpedientes) throws NSJPNegocioException;

    /**
     *
     * @param claveFuncionario
     * @return
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarExpedientescConPermisoFuncionario(Long claveFuncionario) throws NSJPNegocioException;

    /**
     * Consulta los nu&uacute;meros de expedientes que tiene permisos el
     * funcionario, adem&aacute;s de considerar que el n&uacute;mero de
     * expediente sea de la misma a&acute;rea del rol con el cual se ha logeado
     * el usuario.
     *
     * @param usuarioDTO
     * @param claveFuncionarioPermiso
     * @return
     * @throws NSJPNegocioException
     */
    List<ExpedienteDTO> consultarExpedientesPermisoFuncionarioJerarquiaRol(
            UsuarioDTO usuarioDTO, Long claveFuncionarioPermiso) throws NSJPNegocioException;

    /**
     * Servicio que permite consultar a los funcionarios de una agencia en
     * particular
     *
     * @param idAgencia
     * @return
     * @throws NSJPNegocioException
     */
    List<FuncionarioDTO> consultarFuncionariosXAgencia(Long idAgencia) throws NSJPNegocioException;

    /**
     * Servicio que permite consultar que &aacute;reas existen para una agencia
     *
     * @param idAgencia
     * @return
     * @throws NSJPNegocioException
     */
    List<JerarquiaOrganizacionalDTO> consultarAreasXAgencia(Long idAgencia) throws NSJPNegocioException;

    /**
     * Consulta los funcionarios por el discriminante, que representa a nivel de
     * negocio las Agencias y/o Tribunales. El rol es opcional si se pasa nulo.
     *
     * @param catDiscriminanteId
     * @param idRol
     * @param idUIE (Opcional)
     * @return
     * @throws NSJPNegocioException
     */
    List<FuncionarioDTO> consultarFuncionariosPorDicriminanteYRol(Long catDiscriminanteId, Long idRol, Long idUIE) throws NSJPNegocioException;

    /**
     * Se conecta a la intituci&oacute;n indicada por <code>target</code> para
     * consultar Funcionarios por id del catDiscriminante
     *
     * @param catDiscriminanteId
     * @param target
     * @return
     * @throws NSJPNegocioException
     */
    public List<FuncionarioDTO> consultarFuncionariosXTribunal(
            Long catDiscriminanteId, Instituciones target) throws NSJPNegocioException;

    /**
     * Servicio que es utilizado para ingresar la foto de un funcionario
     *
     * @param funcionario
     * @param adjunto
     * @throws NSJPNegocioException
     */
    void adjuntarArchivoAFuncionario(Long idfuncionario,
            ArchivoDigitalDTO adjunto) throws NSJPNegocioException;

    /**
     * M&eacute;todo utilizado para llevar a cabo la consulta de los
     * funcionarios en base a un criterio din&aacute;mico.
     *
     * @param criterio - El criterio sobre el cual se va a llevar a cabo la
     * consulta.
     * @return List<FuncionarioDTO> - Lista de funcionarios que cumplen con el
     * criterio de b&uacute;squeda.
     * @throws NSJPNegocioException
     */
    public List<FuncionarioDTO> consultarFuncionariosXCriterio(CriterioConsultaFuncionarioExternoDTO criterioConsultaFuncionarioExternoDTO) throws NSJPNegocioException;

    /**
     * M&eacute;todo utilizado para llevar a cabo la consulta de los
     * funcionarios pertenencientes a una instituci&oacute;n en
     * espec&iacute;fico en base a un criterio establecido.
     *
     * @param criterioConsultaFuncionarioExternoDTO - El criterio sobre el cual
     * se va a llevar a cabo la consulta.
     * @param target - La instituci&oacute;n sobre la cual se va a invocar el
     * servicio.
     * @return List<FuncionarioDTO> - Lista de funcionarios que cumplen con el
     * criterio de b&uacute;squeda.
     * @throws NSJPNegocioException
     */
    public List<FuncionarioDTO> consultarFuncionariosExternosXCriterio(CriterioConsultaFuncionarioExternoDTO criterioConsultaFuncionarioExternoDTO, Instituciones target) throws NSJPNegocioException;

    /**
     * Permite consultar funcionarios por idRol y idDistrito
     *
     * @param idRol
     * @param idDistrito
     * @return
     * @throws NSJPNegocioException
     */
    public List<FuncionarioDTO> consultarFuncionariosPorRolyPorDistrito(Long idRol, Long idDistrito) throws NSJPNegocioException;

    /**
     * Consulta el Funcionario por filtro enviado en un FuncionarioDTO
     *
     * @param FuncionarioDTO el filtro diferente de {@code null}.
     * @param rolDTO el rol deferente de {@code null}.
     * @return El Funcionario asociado al n&uacute;mero de empleado solicitado
     *
     * @throws NSJPNegocioException En caso que alguno de {@code evidenciaDto} o
     * {@code evidenciaDto.evidenciaId} sea {@code null}.
     */
    public List<FuncionarioDTO> consultarFuncionarioXFiltroYRol(FuncionarioDTO funcionarioDTO, RolDTO rolDTO, Boolean esCambiarResponsableAExpediente)
            throws NSJPNegocioException;

    /**
     * regresa los funcionarios de la lista con el rol
     *
     * @param List<FuncionarioDTO> funcionarios - Funcionarios a validar
     * @param Long idRol - Rol a validar
     * @return List<InvolucradoViewDTO> - Lista de involucrados que cumplen con
     * el rol
     * @throws NSJPNegocioException - en el caso de que no la lista de
     * funcionarios o el rol sea null
     */
    public List<InvolucradoViewDTO> validarFuncionariosPorRol(List<FuncionarioDTO> funcionarios, Long idRol) throws NSJPNegocioException;

    /**
     * Permite consultar funcionarios por filtro y por un conjunto de areas
     *
     * @param filtro
     * @param idsJerarquiaOrganizacional
     * @return
     */
    public List<FuncionarioDTO> consultarFuncionarioXFiltroYAreas(FuncionarioDTO filtro, List<Long> idsJerarquiaOrganizacional) throws NSJPNegocioException;

    /**
     * Enable JC. Obtiene a todos los subordinados de UAVD. Desarrollado para
     * compartir solicitudes todos a todos. Carga a todos los funcionarios de
     * UAVD, incluyendo coordinadores.
     *
     * @return
     * @throws NSJPNegocioException
     */
    List<FuncionarioDTO> obtenerSubordinadosUAVD() throws NSJPNegocioException;

    /**
     * Enable JC. Obtener solicitudes compartidas UAVD.
     *
     * @return
     * @throws NSJPNegocioException
     */
    List<PermisoSolicitudDTO> consultarSolicitudesConPermisoFuncionario(Long funcionarioId) throws NSJPNegocioException;

    public List<FuncionarioDTO> consultarFuncionariosPorDicriminanteYRolYUIE (Long catDiscriminanteId, Long idRol, Long idUIE)throws NSJPNegocioException;

}
