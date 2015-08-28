/**
 *
 * Nombre del Programa : BuscarExpedienteServiceImpl.java Autor : Cesar Agustin
 * Compania : Ultrasist Proyecto : NSJP Fecha: 05/04/2011 Marca de cambio : N/A
 * Descripcion General : Implementaci&oacute;n del servicio para la
 * b&uacute;squeda de expedientes. Programa Dependiente :N/A Programa
 * Subsecuente :N/A Cond. de ejecucion :N/A Dias de ejecucion :N/A Horario: N/A
 * MODIFICACIONES
 * ------------------------------------------------------------------------------
 * Autor :N/A Compania :N/A Proyecto :N/A Fecha: N/A Modificacion :N/A
 * ------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.expediente.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoBusquedaCoordinadorAT;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoDeBusquedaDeExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.menu.EstatusMenuJAR;
import mx.gob.segob.nsjp.comun.enums.objeto.Objetos;
import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.documento.AvisoDesignacionDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.domicilio.DomicilioDAO;
import mx.gob.segob.nsjp.dao.elemento.ElementoArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.elemento.ElementoDAO;
import mx.gob.segob.nsjp.dao.expediente.ActividadDAO;
import mx.gob.segob.nsjp.dao.expediente.BitacoraEstatusNumExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.BitacoraPermisoExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.PermisoExpedienteDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.institucion.JerarquiaOrganizacionalDAO;
import mx.gob.segob.nsjp.dao.involucrado.DefensaInvolucradoDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.objeto.ObjetoDAO;
import mx.gob.segob.nsjp.dao.organizacion.OrganizacionDAO;
import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.dao.persona.NombreDemograficoDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoHechoDelictivoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.expediente.BitacoraEstatusNumExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.BitacoraPermisoExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.DatosGeneralesExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.DatosGeneralesExpedienteUAVDDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteViewDTO;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.hecho.HechoDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.Aeronave;
import mx.gob.segob.nsjp.model.Animal;
import mx.gob.segob.nsjp.model.Arma;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.BitacoraEstatusNumExpediente;
import mx.gob.segob.nsjp.model.BitacoraPermisoExpediente;
import mx.gob.segob.nsjp.model.CatDiscriminante;
import mx.gob.segob.nsjp.model.Causa;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.DocumentoOficial;
import mx.gob.segob.nsjp.model.Domicilio;
import mx.gob.segob.nsjp.model.Elemento;
import mx.gob.segob.nsjp.model.Embarcacion;
import mx.gob.segob.nsjp.model.EquipoComputo;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Explosivo;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Hecho;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.Joya;
import mx.gob.segob.nsjp.model.Lugar;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.model.Numerario;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Objeto;
import mx.gob.segob.nsjp.model.ObjetoPericial;
import mx.gob.segob.nsjp.model.ObraArte;
import mx.gob.segob.nsjp.model.Organizacion;
import mx.gob.segob.nsjp.model.Parametro;
import mx.gob.segob.nsjp.model.PermisoExpediente;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.Sustancia;
import mx.gob.segob.nsjp.model.Telefonia;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.model.Vegetal;
import mx.gob.segob.nsjp.model.Vehiculo;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.AudienciaTransformer;
import mx.gob.segob.nsjp.service.bitacora.ConsultarRegistroBitacoraService;
import mx.gob.segob.nsjp.service.caso.impl.transform.CasoTransformer;
import mx.gob.segob.nsjp.service.delito.impl.transform.DelitoTransfromer;
import mx.gob.segob.nsjp.service.documento.ConsultarDocumentoService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.AvisoDesignacionTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.AvisoHechoDelictivoTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.domicilio.impl.transform.DomicilioTransformer;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.expediente.ConsultarDetalleExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ActividadTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.DatosGeneralesExpedienteTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.hecho.impl.transform.HechoTransformer;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.objeto.impl.transform.AlmacenTransformer;
import mx.gob.segob.nsjp.service.objeto.impl.transform.ObjetoTransformer;
import mx.gob.segob.nsjp.service.organizacion.impl.transform.OrganizacionTransformer;
import mx.gob.segob.nsjp.service.persona.impl.transform.NombreDemograficoTransformer;
import mx.gob.segob.nsjp.service.usuario.RolService;
import mx.gob.segob.nsjp.service.usuario.UsuarioService;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Busca expedientes.
 *
 * @version 1.0
 * @author CesarAgustin
 * @version 1.0
 *
 */
@Service
@Transactional
public class BuscarExpedienteServiceImpl implements BuscarExpedienteService {

    private static final int CASO_ID = 0;
    private static final int CNUMEROGENERALCASO = 1;
    private static final int EXPEDIENTE_ID = 2;
    private static final int NUMEROEXPEDIENTE_ID = 3;
    private static final int CNUMEROEXPEDIENTE = 4;
    private static final int CINVOLUCRADO = 5;
    private static final int CNOMBRECALIDAD = 6;
    private static final int CDELITO = 7;
    private static final int BESPRINCIPAL = 8;
    private static final int ICLAVEFUNCIONARIO = 9;
    private static final int CFUNCIONARIO = 10;
    private static final int CAREA = 11;
    private static final int CEDIFICIO = 12;
    private static final int CESTATUS = 13;
    private static final int BESCONSULTA = 14;
    private static final int NTOTALREGISTROS = 15;
    private static final int FECHAAPERTURA = 16;
    private static final int ID_AREA = 17;

    private static final int PJ_NOMBRE = 5;
    private static final int PJ_AP_PATERNO = 6;
    private static final int PJ_AP_MATERNO = 7;
    private static final int PJ_CNOMBRECALIDAD = 8;
    private static final int PJ_TRIBUNAL = 9;
    private static final int PJ_BESCONSULTA = 10;
    private static final int PJ_NTOTALREGISTROS = 11;

    static Map<Long, List<String>> lHmInvolucrados = new HashMap<Long, List<String>>();
    static Map<Long, List<String>> lHmObjetos = new HashMap<Long, List<String>>();
    private static String SEPARADOR = " - ";

    /**
     * Logger.
     */
    private final static Logger logger = Logger
            .getLogger(BuscarExpedienteServiceImpl.class);

    @Autowired
    private ConfInstitucionDAO confInstitucionDAO;
    @Autowired
    private ExpedienteDAO expedienteDAO;
    @Autowired
    private InvolucradoDAO involucradoDAO;
    @Autowired
    private DefensaInvolucradoDAO defensaInvolucradoDAO;
    @Autowired
    private NombreDemograficoDAO nombreDemograficoDAO;
    @Autowired
    private ActividadDAO activiDao;
    @Autowired
    private DelitoDAO delitoDAO;
    @Autowired
    private NumeroExpedienteDAO numeroExpedienteDAO;
    @Autowired
    private NumeroExpedienteDAO noExpDao;
    @Autowired
    private ObjetoDAO objetoDAO;
    @Autowired
    private DocumentoDAO documentoDAO;
    @Autowired
    private ParametroDAO parametroDAO;
    @Autowired
    private OrganizacionDAO organizacionDAO;
    @Autowired
    private ElementoDAO eleDao;
    @Autowired
    private ConsultarDocumentoService consultarDocumentoService;
    @Autowired
    private AvisoDesignacionDAO avisoDesignacionDAO;
    @Autowired
    private DomicilioDAO domicilioDAO;
    @Autowired
    private PermisoExpedienteDAO permisoExpedienteDAO;
    @Autowired
    private SolicitudDAO solicitudDAO;
    @Autowired
    private ConsultarRegistroBitacoraService consultarBitacoraService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ConsultarDetalleExpedienteService consultarDetalleExpedienteService;
    @Autowired
    private JerarquiaOrganizacionalDAO jerarquiaOrganizacionalDAO;
    @Autowired
    ElementoArchivoDigitalDAO elementoArchivoDigitalDAO;
    @Autowired
    private BitacoraPermisoExpedienteDAO bitacoraPermisoExpedienteDAO;
    @Autowired
    private BitacoraEstatusNumExpedienteDAO bitacoraEstatusNumExpedienteDAO;
    @Autowired
    private RolService rolService;

    /**
     *
     */
    @Override
    public List<ExpedienteDTO> buscarExpedientes(
            FiltroExpedienteDTO filtrosBusquedaExpediente)
            throws NSJPNegocioException {

        List<Expediente> expedientes = new ArrayList<Expediente>();

        if (filtrosBusquedaExpediente.getNumeroExpediente() != null) {
            Long areaId = null;
            if (filtrosBusquedaExpediente.getUsuario() != null
                    && filtrosBusquedaExpediente.getUsuario().getAreaActual() != null
                    && filtrosBusquedaExpediente.getUsuario().getAreaActual()
                    .getAreaId() != null) {
                areaId = filtrosBusquedaExpediente.getUsuario().getAreaActual()
                        .getAreaId();
            }
            /*
             * Usado para obtener el discriminante Id
             */
            long discriminanteId = 0L;

            if (filtrosBusquedaExpediente.getUsuario() != null
                    && filtrosBusquedaExpediente.getUsuario().getFuncionario() != null
                    && filtrosBusquedaExpediente.getUsuario().getFuncionario()
                    .getDiscriminante() != null
                    && filtrosBusquedaExpediente.getUsuario().getFuncionario()
                    .getDiscriminante().getCatDiscriminanteId() != null) {

                discriminanteId = filtrosBusquedaExpediente.getUsuario()
                        .getFuncionario().getDiscriminante()
                        .getCatDiscriminanteId();
            }

            expedientes = expedienteDAO.buscarExpedientes("%"
                    + filtrosBusquedaExpediente.getNumeroExpediente() + "%",
                    areaId, discriminanteId);
        } else {
            logger.info("/**** Consultar Expedientes por evidencia ****/");
            List<Long> expLong = expedienteDAO
                    .consultarExpedientesPorEvidencia(
                            filtrosBusquedaExpediente);
            for (Long idExp : expLong) {
                Expediente exp = expedienteDAO.read(idExp);
                CasoDTO casoDTO = CasoTransformer.transformarCasoBasico(exp.getCaso());
                casoDTO.setFiltroExpedienteDTO(filtrosBusquedaExpediente);
                List<Expediente> listExpedientes = expedienteDAO.consultarExpedientesPorIdCaso(casoDTO);
                for (Expediente expediente : listExpedientes) {
                    expedientes.add(expediente);
                }
            }
        }

        final List<ExpedienteDTO> expedientesDto = new ArrayList<ExpedienteDTO>();
        ExpedienteDTO eDto = null;
        List<NumeroExpediente> numeros = null;
        for (Expediente expediente : expedientes) {

            if (filtrosBusquedaExpediente.getUsuario() != null
                    && filtrosBusquedaExpediente.getUsuario().getAreaActual() != null
                    && filtrosBusquedaExpediente.getUsuario().getAreaActual()
                    .getAreaId() != null) {
                numeros = this.noExpDao.consultarNumeroExpedientes(
                        expediente.getExpedienteId(), filtrosBusquedaExpediente
                        .getUsuario().getAreaActual().getAreaId());
            } else {
                numeros = this.noExpDao
                        .consultarNumeroExpedientesXExpediente(expediente
                                .getExpedienteId());
            }

            for (NumeroExpediente ne : numeros) {
                eDto = ExpedienteTransformer
                        .transformarExpedienteBasico(expediente);
                eDto.setNumeroExpediente(ne.getNumeroExpediente());
                expedientesDto.add(eDto);
            }

        }
        return expedientesDto;
    }

    /**
     *
     */
    @Override
    public List<ExpedienteDTO> buscarExpedientesPorNumExpDiscriminanteArea(
            FiltroExpedienteDTO filtrosBusquedaExpediente)
            throws NSJPNegocioException {

        if (filtrosBusquedaExpediente == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        if (filtrosBusquedaExpediente.getUsuario() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        if (filtrosBusquedaExpediente.getUsuario().getInstitucion() == null
                || filtrosBusquedaExpediente.getUsuario().getInstitucion()
                .getConfInstitucionId() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        if (filtrosBusquedaExpediente.getUsuario().getFuncionario() == null
                || filtrosBusquedaExpediente.getUsuario().getFuncionario()
                .getDiscriminante() == null
                || filtrosBusquedaExpediente.getUsuario().getFuncionario()
                .getDiscriminante().getCatDiscriminanteId() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        if (filtrosBusquedaExpediente.getUsuario().getAreaActual() == null
                || filtrosBusquedaExpediente.getUsuario().getAreaActual()
                .getAreaId() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        List<Expediente> expedientes = new ArrayList<Expediente>();
        Long institucionId = 0L;

        institucionId = filtrosBusquedaExpediente.getUsuario()
                .getInstitucion().getConfInstitucionId();

        if (filtrosBusquedaExpediente.getNumeroExpediente() != null) {
            logger.info("/**** Consultar Expedientes por numero expediente****/");
            if (institucionId.equals(Instituciones.PJ.getValorId())) {
                //Permite a todos los usuarios de PJ filtrar unicamente por claveFuncionario y CatDiscriminante
                filtrosBusquedaExpediente.getUsuario().getAreaActual().setAreaId(0L);
                filtrosBusquedaExpediente.getUsuario().getFuncionario().setClaveFuncionario(0L);
            }

            if (filtrosBusquedaExpediente.getUsuario().getAreaActual() != null
                    && filtrosBusquedaExpediente.getUsuario().getAreaActual().getAreaId() != null
                    && filtrosBusquedaExpediente.getUsuario().getAreaActual().getAreaId() > 0L
                    && filtrosBusquedaExpediente.getUsuario().getAreaActual()
                    .getBuscarEnJerarquia() != null
                    && filtrosBusquedaExpediente.getUsuario().getAreaActual()
                    .getBuscarEnJerarquia()) {

                JerarquiaOrganizacional raiz = new JerarquiaOrganizacional(
                        filtrosBusquedaExpediente.getUsuario().getAreaActual().getAreaId());
                List<JerarquiaOrganizacional> lstJerarquiasDependientes = jerarquiaOrganizacionalDAO
                        .getArbolJerarquiasDependientes(raiz);

                if (lstJerarquiasDependientes != null
                        && !lstJerarquiasDependientes.isEmpty()) {
                    if (filtrosBusquedaExpediente.getJerarquiaOrgSubordinadas() == null) {
                        filtrosBusquedaExpediente.setJerarquiaOrgSubordinadas(new HashSet<JerarquiaOrganizacionalDTO>());
                    }
                    for (JerarquiaOrganizacional areaDep : lstJerarquiasDependientes) {
                        filtrosBusquedaExpediente.getJerarquiaOrgSubordinadas().add(new JerarquiaOrganizacionalDTO(areaDep.getJerarquiaOrganizacionalId()));
                    }
                }
            }
            expedientes = expedienteDAO.buscadorDeExpedientes(filtrosBusquedaExpediente);
        } else {
            logger.info("/**** Consultar Expedientes por evidencia ****/");
            List<Long> expLong = expedienteDAO
                    .consultarExpedientesPorEvidencia(
                            filtrosBusquedaExpediente);
            for (Long idExp : expLong) {
                expedientes.add(expedienteDAO.read(idExp));
            }
        }

        final List<ExpedienteDTO> expedientesDto = new ArrayList<ExpedienteDTO>();
        ExpedienteDTO eDto = null;
        List<Long> ids = new ArrayList<Long>();
        if (filtrosBusquedaExpediente.getFiltroEspecificoDeAreaRolActual() != null
                && filtrosBusquedaExpediente.getFiltroEspecificoDeAreaRolActual().equals(Areas.UNIDAD_INVESTIGACION.parseLong())) {
            Map mapa = new HashMap<Long, Expediente>();
            List<Expediente> expedientesCompletos = new ArrayList<Expediente>();
            for (Expediente expediente : expedientes) {
                boolean op = false;
                if (mapa.isEmpty()) {
                    mapa.put(expediente.getExpedienteId(), expediente);
                    expedientesCompletos.add(expediente);
                    op = true;
                } else if (!mapa.containsKey(expediente.getExpedienteId())) {
                    mapa.put(expediente.getExpedienteId(), expediente);
                    expedientesCompletos.add(expediente);
                    op = true;
                }
                if (op) {
                    List<NumeroExpediente> listaAlterna = numeroExpedienteDAO.consultarNumeroExpedientesXExpediente(expediente.getExpedienteId());
                    List<Expediente> expedientesAdjuntos = new ArrayList<Expediente>();
                    boolean existeUI = false;
                    Long idNumeroExpediente = -1L;
                    Long idFuncionario = -1L;
                    if (listaAlterna.isEmpty()) {

                    }
                    for (NumeroExpediente numeroExpediente : listaAlterna) {
                        if (numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().equals(Areas.UNIDAD_INVESTIGACION.parseLong())) {
                            existeUI = true;
                            idNumeroExpediente = numeroExpediente.getNumeroExpedienteId();
                            idFuncionario = numeroExpediente.getFuncionario().getClaveFuncionario();
                        }
                        if (!expediente.getNumeroExpediente().equals(numeroExpediente.getNumeroExpediente()) && (numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().equals(Areas.UNIDAD_INVESTIGACION.parseLong())
                                || numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().equals(Areas.ATENCION_TEMPRANA_PG_PENAL.parseLong())
                                || numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().equals(Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong()))) {
                            Expediente expedienteAlterno = new Expediente();
                            List<NumeroExpediente> lista = new ArrayList<NumeroExpediente>();
                            lista.add(numeroExpediente);
                            Set<NumeroExpediente> set = new HashSet<NumeroExpediente>();
                            set.addAll(lista);
                            expediente.setNumeroExpedientes(set);
                            BeanUtils.copyProperties(expediente, expedienteAlterno);
                            expedienteAlterno.setNumeroExpediente(numeroExpediente.getNumeroExpediente());
                            expedienteAlterno.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
                            expedientesAdjuntos.add(expedienteAlterno);
                            CatDiscriminante catDiscriminante = expedienteDAO.read(expediente.getExpedienteId()).getDiscriminante();
                            expediente.setDiscriminante(catDiscriminante);
                        } else {
                            List<NumeroExpediente> lista = new ArrayList<NumeroExpediente>();
                            lista.add(numeroExpediente);
                            Set<NumeroExpediente> set = new HashSet<NumeroExpediente>();
                            set.addAll(lista);
                            expediente.setNumeroExpedientes(set);
                            CatDiscriminante catDiscriminante = expedienteDAO.read(expediente.getExpedienteId()).getDiscriminante();
                            expediente.setDiscriminante(catDiscriminante);

                        }
                    }
                    Long idnumeroDeexpedientePapa = 0L;
                    boolean entro = false;
                    for (Expediente expediente2 : expedientesAdjuntos) {
                        if (existeUI) {
                            if (filtrosBusquedaExpediente.getUsuario() != null
                                    && filtrosBusquedaExpediente.getUsuario().getFuncionario() != null
                                    && filtrosBusquedaExpediente.getUsuario().getFuncionario().getClaveFuncionario() != null
                                    && filtrosBusquedaExpediente.getUsuario().getFuncionario().getClaveFuncionario().equals(idFuncionario)) {
                                expediente.setNumeroExpedienteId(idNumeroExpediente);
                                expediente2.setNumeroExpedienteId(idNumeroExpediente);
                                expediente.setIdNumeroExpedienteBusquedaATP(idNumeroExpediente);
                                expediente2.setIdNumeroExpedienteBusquedaATP(idNumeroExpediente);
                                expedientesCompletos.add(expediente2);
                                ids.add(idNumeroExpediente);
                                idnumeroDeexpedientePapa = idNumeroExpediente;
                            } else {
                                expediente.setNumeroExpedienteId(-2L);
                                expediente2.setNumeroExpedienteId(-2L);
                                expediente.setIdNumeroExpedienteBusquedaATP(-2L);
                                expediente2.setIdNumeroExpedienteBusquedaATP(-2L);
                                expedientesCompletos.add(expediente2);
                                ids.add(-2L);
                                idnumeroDeexpedientePapa = -2L;
                            }
                        } else {
                            expediente.setNumeroExpedienteId(-1L);
                            expediente2.setNumeroExpedienteId(-1L);
                            expediente.setIdNumeroExpedienteBusquedaATP(-1L);
                            expediente2.setIdNumeroExpedienteBusquedaATP(-1L);
                            expedientesCompletos.add(expediente2);
                            ids.add(-1L);
                            idnumeroDeexpedientePapa = -1L;
                        }
                        entro = true;
                    }
                    if (!entro) {
                        if (existeUI) {
                            if (filtrosBusquedaExpediente.getUsuario() != null
                                    && filtrosBusquedaExpediente.getUsuario().getFuncionario() != null
                                    && filtrosBusquedaExpediente.getUsuario().getFuncionario().getClaveFuncionario() != null
                                    && filtrosBusquedaExpediente.getUsuario().getFuncionario().getClaveFuncionario().equals(idFuncionario)) {
                                idnumeroDeexpedientePapa = idNumeroExpediente;
                            } else {
                                idnumeroDeexpedientePapa = -2L;
                            }
                        } else {
                            idnumeroDeexpedientePapa = -1L;
                        }
                    }
                    ids.add(idnumeroDeexpedientePapa);
                }
            }
            expedientes = null;
            expedientes = expedientesCompletos;
            expedientes = paginacionManualExpedientes(expedientes);
            ids = paginacionManualIds(ids);
            int contador = 0;
            for (Expediente expediente : expedientes) {
                eDto = ExpedienteTransformer
                        .transformarExpedienteBasico(expediente);
                eDto.setNumeroExpediente(expediente.getNumeroExpediente());
                Long id = ids.get(contador);
                if (id == null) {
                    eDto.setIdNumeroExpedienteBusquedaATP(-1L);
                } else {
                    eDto.setIdNumeroExpedienteBusquedaATP(id);
                }

                expedientesDto.add(eDto);
                contador++;
            }

            return expedientesDto;

        }
        for (Expediente expediente : expedientes) {
            eDto = ExpedienteTransformer
                    .transformarExpedienteBasico(expediente);
            eDto.setNumeroExpediente(expediente.getNumeroExpediente());
            expedientesDto.add(eDto);
        }

        return expedientesDto;
    }

    private List<Expediente> paginacionManualExpedientes(
            List<Expediente> expsRespuesta) {
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        if ((pag.getPage() * pag.getRows()) - pag.getRows() > expsRespuesta.size()) {
            pag.setPage(1);
        }
        int inicio = 0;
        if (pag.getPage() <= 1) {
            inicio = 0;
        } else {
            inicio = ((pag.getPage() - 1) * pag.getRows());
        }
        int indiceFinal = inicio + pag.getRows();
        List<Expediente> listaAlterna = expsRespuesta;
        expsRespuesta = new ArrayList<Expediente>();
        if (indiceFinal >= listaAlterna.size()) {
            for (int i = inicio; i < listaAlterna.size(); i++) {
                expsRespuesta.add(listaAlterna.get(i));
            }
        } else {
            for (int i = inicio; i < indiceFinal; i++) {
                expsRespuesta.add(listaAlterna.get(i));
            }
        }
        pag.setTotalRegistros((long) listaAlterna.size());
        pag.setPaginacionHecha(true);
        PaginacionThreadHolder.set(pag);
        return expsRespuesta;
    }

    private List<Long> paginacionManualIds(
            List<Long> expsRespuesta) {
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        if ((pag.getPage() * pag.getRows()) - pag.getRows() > expsRespuesta.size()) {
            pag.setPage(1);
        }
        int inicio = 0;
        if (pag.getPage() <= 1) {
            inicio = 0;
        } else {
            inicio = ((pag.getPage() - 1) * pag.getRows());
        }
        int indiceFinal = inicio + pag.getRows();
        List<Long> listaAlterna = expsRespuesta;
        expsRespuesta = new ArrayList<Long>();
        if (indiceFinal >= listaAlterna.size()) {
            for (int i = inicio; i < listaAlterna.size(); i++) {
                expsRespuesta.add(listaAlterna.get(i));
            }
        } else {
            for (int i = inicio; i < indiceFinal; i++) {
                expsRespuesta.add(listaAlterna.get(i));
            }
        }
        pag.setTotalRegistros((long) listaAlterna.size());
        pag.setPaginacionHecha(true);
        PaginacionThreadHolder.set(pag);
        return expsRespuesta;
    }

    @Override
    public ExpedienteDTO obtenerExpediente(ExpedienteDTO input)
            throws NSJPNegocioException {
        logger.info("/**** Obtener Expediente por ID ****/");
        logger.debug("ExpedienteDTO.numeroExpedienteId :: " + input.getNumeroExpedienteId());
        if (logger.isDebugEnabled()) {
            this.logBanderas(input);
        }
        NumeroExpediente numeroExpediente = new NumeroExpediente();
        ExpedienteDTO output = new ExpedienteDTO();

        if (input.getNumeroExpedienteId() != null) {
            List<InvolucradoDTO> involucradosDto = new ArrayList<InvolucradoDTO>();

            numeroExpediente = numeroExpedienteDAO.read(input
                    .getNumeroExpedienteId());
            final Expediente expediente = numeroExpediente.getExpediente();
            output = extraerExpediente(input, numeroExpediente,
                    involucradosDto, expediente);
            output.setExpedienteId(expedienteDAO.obtenerExpedienteIdPorIdNumerExpediente(input.getNumeroExpedienteId()));
        }
        return output;
    }

    @Override
    public ExpedienteDTO obtenerExpedientePorExpedienteId(ExpedienteDTO input)
            throws NSJPNegocioException {
        logger.info("/**** Obtener Expediente por ID ****/");
        logger.debug("ExpedienteDTO.numeroExpedienteId :: "
                + input.getExpedienteId());
        if (logger.isDebugEnabled()) {
            this.logBanderas(input);
        }
        ExpedienteDTO output = new ExpedienteDTO();

        if (input.getExpedienteId() != null) {
            List<InvolucradoDTO> involucradosDto = new ArrayList<InvolucradoDTO>();

            final Expediente expediente = this.expedienteDAO.read(input
                    .getExpedienteId());
            output = extraerExpediente(input, null,
                    involucradosDto, expediente);
        }
        return output;
    }

    /**
     * @param input
     * @param numeroExpediente
     * @param involucradosDto
     * @param expediente
     * @return
     * @throws NSJPNegocioException
     */
    private ExpedienteDTO extraerExpediente(ExpedienteDTO input,
            NumeroExpediente numeroExpediente,
            List<InvolucradoDTO> involucradosDto, final Expediente expediente)
            throws NSJPNegocioException {
        logger.info("Inicia - extraerExpediente(...)");
        ExpedienteDTO output;
        Hecho hecho;
        List<Involucrado> involucrados = new ArrayList<Involucrado>();
        List<Objeto> objetosExpediente = new ArrayList<Objeto>();
        List<Documento> documentos = new ArrayList<Documento>();
        List<AvisoDesignacion> designaciones = new ArrayList<AvisoDesignacion>();
        // los delitos se obtienen en el transformer
        output = ExpedienteTransformer.transformaExpediente(expediente);
        // se valida que el numero de expediente es nulo para el escenario del
        // clonado del expediente
        if (numeroExpediente != null) {
            output.setNumeroExpedienteId(numeroExpediente
                    .getNumeroExpedienteId());
            output.setFechaApertura(numeroExpediente.getFechaApertura());
            output.setNumeroExpediente(numeroExpediente.getNumeroExpediente());
            output.setArea(new AreaDTO(numeroExpediente
                    .getJerarquiaOrganizacional()
                    .getJerarquiaOrganizacionalId(), numeroExpediente
                    .getJerarquiaOrganizacional().getNombre()));

            if (numeroExpediente.getEtapa() != null) {
                output.setEtapa(new ValorDTO(numeroExpediente.getEtapa()
                        .getValorId(), numeroExpediente.getEtapa().getValor()));
            }

            // ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de 
            // output.getEstatus();
            // Pero si la referencia es al estatus del n&uacute;mero de expediente asociado a ese expediente:
            // output.getEstatusNumeroExpediente();
            if (expediente != null
                    && expediente.getEstatus() != null) {
                output.setEstatus(new ValorDTO(expediente.getEstatus()
                        .getValorId(), expediente.getEstatus().getValor()));
            }

            if (numeroExpediente.getEstatus() != null) {
                output.setEstatusNumeroExpediente(ValorTransformer.transformar(numeroExpediente.getEstatus()));
            }

            // ------------------------------------------------------------------------------------------------
            if (numeroExpediente.getTipoExpediente() != null) {
                output.setTipoExpediente(new ValorDTO(numeroExpediente.getTipoExpediente()
                        .getValorId(), numeroExpediente.getTipoExpediente().getValor()));
            }

            if (numeroExpediente.getAlmacen() != null) {
                output.setAlmacenDTO(AlmacenTransformer
                        .transformarAlmacenBasico(numeroExpediente.getAlmacen()));
            }

            //Duenio o responsable del tramite
            if (numeroExpediente.getFuncionario() != null) {
                output.setResponsableTramite(FuncionarioTransformer.transformarFuncionario(numeroExpediente.getFuncionario()));
            }
        }
        if (input.isAvisosDesignacionSolicitados() && numeroExpediente != null) {
            designaciones = avisoDesignacionDAO
                    .consultarAvisosDesignacionPorIdExpediente(numeroExpediente
                            .getExpediente().getExpedienteId());
            if (designaciones != null) {
                for (AvisoDesignacion aviso : designaciones) {
                    output.getAvisosDesignacion().add(
                            AvisoDesignacionTransformer.transformar(aviso));
                }
            }
        }
        if (input.isObjetosSolicitados() || input.isDatosPlatillaSolicitados()) {
            objetosExpediente = objetoDAO
                    .consultarObjetosByExpediente(expediente.getExpedienteId());
            if (objetosExpediente != null) {
                for (Objeto objeto : objetosExpediente) {
                    if (input.isImagenesAsociadasAElementos()) {
                        objeto.setImagenesAsociadas(elementoArchivoDigitalDAO.consultarArchivosDigitalesSinEvniarXElementoId(objeto.getElementoId()));
                    }
                    ObjetoDTO loObjetoDTO = ObjetoTransformer.transformarObjeto(objeto);
                    output.getObjetosDTO().add(loObjetoDTO);
                }
            }
        }
        if (input.isHechoSolicitado()) {
            hecho = expediente.getHecho();
            if (hecho != null && hecho.getHechoId() != null) {
                output.setHechoDTO(HechoTransformer.transformarHecho(hecho));
                if (hecho.getLugar() != null) {
                    Lugar lg = hecho.getLugar();
                    logger.debug("lg.getClass().getSimpleName()->"
                            + lg.getClass().getSimpleName());

                    if (lg instanceof Domicilio) {
                        logger.debug("EL LUGAR DEL HECHO ES UN DOMICILIO ");
                        DomicilioDTO domHecho = DomicilioTransformer
                                .transformarDomicilio((Domicilio) lg);
                        output.getHechoDTO().setLugar(domHecho);
                    }
                }
            }
        }
        if (input.isInvolucradosSolicitados()) {
            involucrados = involucradoDAO
                    .consultarInvolucradosByExpediente(expediente
                            .getExpedienteId());
            if (involucrados.size() > 0) {
                for (Involucrado involucrado : involucrados) {
                    InvolucradoDTO invoDto = InvolucradoTransformer
                            .transformarInvolucrado(involucrado);
                    if (input.isDomicliosInvolucradoSolicitados()) {
                        Domicilio domicilio = domicilioDAO
                                .obtenerDomicilioByRelacion(invoDto
                                        .getElementoId(), new Long(
                                                Relaciones.RESIDENCIA.ordinal()));
                        Domicilio domicilioNotificacion = domicilioDAO
                                .obtenerDomicilioByRelacion(invoDto
                                        .getElementoId(), new Long(
                                                Relaciones.NOTIFICACION.ordinal()));
                        if (domicilio != null) {
                            invoDto.setDomicilio(DomicilioTransformer
                                    .transformarDomicilio(domicilio));
                        }
                        if (domicilioNotificacion != null) {
                            invoDto.setDomicilioNotificacion(DomicilioTransformer
                                    .transformarDomicilio(domicilioNotificacion));
                        }
                    }
                    // Si es persona Moral Consultar Organizacion
                    if (invoDto.getTipoPersona().equals(0L)) {
                        Organizacion organizacion = organizacionDAO
                                .obtenerOrganizacionByRelacion(
                                        invoDto.getElementoId(),
                                        new Long(
                                                Relaciones.ORGANIZACION_INVOLUCRADA
                                                .ordinal()));
                        invoDto.setOrganizacionDTO(OrganizacionTransformer
                                .transformarOrganizacion(organizacion));
                    }

                    involucradosDto.add(invoDto);
                }
            }
            output.setInvolucradosDTO(involucradosDto);
        }
        if (input.isDocumentosSolicitados()) {
            documentos = documentoDAO
                    .consultarDocumentoPorExpediente(expediente.getExpedienteId(), null, null, null);
            if (documentos != null) {
                for (Documento loDocumento : documentos) {
                    output.getDocumentosDTO().add(
                            DocumentoTransformer
                            .transformarDocumento(loDocumento));
                }
            }
        }
        final Actividad actAct = this.activiDao
                .obtenerActividadActual(expediente.getExpedienteId());
        output.setActividadActual(ActividadTransformer
                .transformarActividad(actAct));
        if (numeroExpediente != null) {
            output.setEtapasPasadas(this.consultarBitacoraService
                    .consultarEtapasPasadas(output));
        }
        output.setEsReplicado(expediente.getEsReplicado() == null ? false : expediente.getEsReplicado());
        logger.info("Fin - extraerExpediente(...)");
        return output;
    }

    @Override
    public ExpedienteDTO obtenerExpedienteDefensoria(ExpedienteDTO input)
            throws NSJPNegocioException {
        logger.info("/**** Obtener Expediente por ID ****/");
        if (logger.isDebugEnabled()) {
            this.logBanderas(input);
        }
        NumeroExpediente numeroExpediente = new NumeroExpediente();
        ExpedienteDTO output = new ExpedienteDTO();
        List<InvolucradoDTO> involucradosDto = new ArrayList<InvolucradoDTO>();

        List<Involucrado> involucrados = new ArrayList<Involucrado>();
        List<Objeto> objetosExpediente = new ArrayList<Objeto>();
        List<Documento> documentos = new ArrayList<Documento>();
        List<AvisoDesignacion> designaciones = new ArrayList<AvisoDesignacion>();
        Hecho hecho = new Hecho();
        Expediente expediente = new Expediente();
        if (input.getNumeroExpedienteId() != null) {
            numeroExpediente = numeroExpedienteDAO.read(input.getNumeroExpedienteId());
            expediente = numeroExpediente.getExpediente();
            //los delitos se obitienen en el transformer
            output = ExpedienteTransformer.transformaExpediente(expediente);
            output.setNumeroExpedienteId(numeroExpediente
                    .getNumeroExpedienteId());
            output.setFechaApertura(numeroExpediente.getFechaApertura());
            output.setNumeroExpediente(numeroExpediente.getNumeroExpediente());
            if (numeroExpediente.getEtapa() != null) {
                output.setEtapa(new ValorDTO(numeroExpediente.getEtapa()
                        .getValorId(), numeroExpediente.getEtapa().getValor()));
            }

    	    // ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de 
            // output.getEstatus();
            // Pero si la referencia es al estatus del n&uacute;mero de expediente asociado a ese expediente:
            // output.getEstatusNumeroExpediente();
            if (numeroExpediente != null
                    && numeroExpediente.getExpediente() != null
                    && numeroExpediente.getExpediente().getEstatus() != null) {
                output.setEstatus(new ValorDTO(numeroExpediente.getExpediente().getEstatus()
                        .getValorId()));
            }

            if (numeroExpediente != null && numeroExpediente.getEstatus() != null) {
                output.setEstatusNumeroExpediente(ValorTransformer.transformar(numeroExpediente.getEstatus()));
            }

            // ------------------------------------------------------------------------------------------------
            if (numeroExpediente.getAlmacen() != null) {
                output.setAlmacenDTO(AlmacenTransformer
                        .transformarAlmacenBasico(numeroExpediente.getAlmacen()));
            }

            if (input.isAvisosDesignacionSolicitados()) {
                designaciones = avisoDesignacionDAO
                        .consultarAvisosDesignacionPorIdExpediente(numeroExpediente
                                .getExpediente().getExpedienteId());
                if (designaciones != null) {
                    for (AvisoDesignacion aviso : designaciones) {
                        output.getAvisosDesignacion().add(
                                AvisoDesignacionTransformer.transformar(aviso));
                    }
                }
            }
            if (input.isObjetosSolicitados()) {
                objetosExpediente = objetoDAO
                        .consultarObjetosByExpediente(expediente
                                .getExpedienteId());
                if (objetosExpediente != null) {
                    for (Objeto objeto : objetosExpediente) {
                        output.getObjetosDTO().add(
                                ObjetoTransformer.transformarObjeto(objeto));
                    }
                }
            }
            if (input.isHechoSolicitado()) {
                hecho = expediente.getHecho();
                if (hecho != null && hecho.getHechoId() != null) {
                    output.setHechoDTO(HechoTransformer.transformarHecho(hecho));
                    if (hecho.getLugar() != null) {
                        Lugar lg = hecho.getLugar();
                        logger.debug("lg.getClass().getSimpleName()->" + lg.getClass().getSimpleName());

                        if (lg instanceof Domicilio) {
                            logger.debug("EL LUGAR DEL HECHO ES UN DOMICILIO ");
                            DomicilioDTO domHecho = DomicilioTransformer
                                    .transformarDomicilio((Domicilio) lg);
                            output.getHechoDTO().setLugar(domHecho);
                        }
                    }
                }
            }
            if (input.isInvolucradosSolicitados()) {
                involucrados = involucradoDAO.consultarInvolucradosByExpediente(expediente.getExpedienteId());
                if (involucrados.size() > 0) {
                    for (Involucrado involucrado : involucrados) {
                        InvolucradoDTO invoDto = InvolucradoTransformer.transformarInvolucrado(involucrado);
                        if (input.isDomicliosInvolucradoSolicitados()) {
                            Domicilio domicilio = domicilioDAO.obtenerDomicilioByRelacion(invoDto.getElementoId(), new Long(Relaciones.RESIDENCIA.ordinal()));
                            Domicilio domicilioNotificacion = domicilioDAO.obtenerDomicilioByRelacion(invoDto.getElementoId(), new Long(Relaciones.NOTIFICACION.ordinal()));
                            if (domicilio != null) {
                                invoDto.setDomicilio(DomicilioTransformer.transformarDomicilio(domicilio));
                            }
                            if (domicilioNotificacion != null) {
                                invoDto.setDomicilioNotificacion(DomicilioTransformer.transformarDomicilio(domicilioNotificacion));
                            }
                        }

                        // Si es persona Moral Consultar Organizacion
                        if (invoDto.getTipoPersona().equals(0L)) {
                            Organizacion organizacion = organizacionDAO.obtenerOrganizacionByRelacion(invoDto.getElementoId(), new Long(Relaciones.ORGANIZACION_INVOLUCRADA.ordinal()));
                            invoDto.setOrganizacionDTO(OrganizacionTransformer.transformarOrganizacion(organizacion));
                        }

                        if (involucrado.getCalidad().getTipoCalidad().getValorId().longValue() == Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId().longValue()) {
                            output.setSolicitante(invoDto);
                        }

                        involucradosDto.add(invoDto);
                    }

                    Involucrado defendido = defensaInvolucradoDAO.consultarInvolucradoNumeroExpedienteDefensoria(input.getNumeroExpedienteId());
                    if (defendido != null) {
                        output.setInputado(InvolucradoTransformer.transformarInvolucrado(defendido));
                    }
                }
                output.setInvolucradosDTO(involucradosDto);
            }
            if (input.isDocumentosSolicitados()) {
                documentos = documentoDAO
                        .consultarDocumentoPorExpediente(expediente
                                .getExpedienteId(), null, null, null);
                if (documentos != null) {
                    for (Documento loDocumento : documentos) {
                        output.getDocumentosDTO().add(
                                DocumentoTransformer
                                .transformarDocumento(loDocumento));
                    }
                }
            }
            final Actividad actAct = this.activiDao
                    .obtenerActividadActual(expediente.getExpedienteId());
            output.setActividadActual(ActividadTransformer
                    .transformarActividad(actAct));
        }

        return output;
    }

    /**
     * Manda al log ls banderas actividas par ala obtenci&oacute;n del
     * expediente.
     *
     * @param input
     */
    private void logBanderas(ExpedienteDTO input) {
        logger.debug("******************** FILTROS *******************");
        if (input.isMedidasCautelaresSolicitadas()) {
            logger.debug("**  medidasCautelaresSolicitadas Activado     **");
        }
        if (input.isInvolucradosSolicitados()) {
            logger.debug("**  involucradosSolicitados Activado          **");
        }
        if (input.isDomicliosInvolucradoSolicitados()) {
            logger.debug("**  domicliosInvolucradoSolicitados Activado  **");
        }
        if (input.isObjetosSolicitados()) {
            logger.debug("**  objetosSolicitados Activado               **");
        }
        if (input.isCadenasCustodiaSolicitadas()) {
            logger.debug("**  cadenasCustodiaSolicitadas Activado       **");
        }
        if (input.isDocumentosSolicitados()) {
            logger.debug("**  documentosSolicitados Activado            **");
        }
        if (input.isHechoSolicitado()) {
            logger.debug("**  hechoSolicitado Activado                  **");
        }
        if (input.isAvisosDesignacionSolicitados()) {
            logger.debug("**  avisosDesignacionSolicitados Activado     **");
        }
        logger.debug("******************** FILTROS *******************");
    }

    @Override
    public List<ExpedienteDTO> consultarExpedientesPorCaso(CasoDTO caso)
            throws NSJPNegocioException {

        List<Expediente> fromBD = null;
        if (caso.getCasoId() != null) {
            fromBD = this.expedienteDAO
                    .consultarExpedientesPorIdCaso(caso.getCasoId(), caso
                            .getUsuario().getAreaActual().getAreaId());
            return ExpedienteTransformer.transformarExpedientesBasico(fromBD);
        } else {
            fromBD = this.expedienteDAO
                    .consultarExpedientesPorNumeroCaso(caso.getNumeroGeneralCaso());
            List<ExpedienteDTO> expedientes = ExpedienteTransformer.transformarExpedientesBasico(fromBD);

            for (ExpedienteDTO expedienteDTO : expedientes) {
                List<Involucrado> involucrados = involucradoDAO
                        .obtenerInvByIdExpAndCalidad(expedienteDTO.getExpedienteId(),
                                Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId(), null);
                for (Involucrado involucrado : involucrados) {
                    expedienteDTO.getInvolucradosDTO().add(InvolucradoTransformer.transformarInvolucrado(involucrado));
                }
            }

            return expedientes;
        }
    }

    @Override
    public List<InvolucradoDTO> buscarExpedientesPorFiltros(
            FiltroExpedienteDTO filtrosBusquedaExpediente)
            throws NSJPNegocioException {

        List<InvolucradoDTO> involucradosDTO = new ArrayList<InvolucradoDTO>();
        List<Involucrado> involucrados = new ArrayList<Involucrado>();

        if (filtrosBusquedaExpediente.getAlias() != null
                && filtrosBusquedaExpediente.getTipoBusqueda().equals(
                        new Long(0))) {

            involucrados = involucradoDAO
                    .consultarExpedientesByAlias(filtrosBusquedaExpediente
                            .getAlias());
        } else if (filtrosBusquedaExpediente.getAlias() != null
                && filtrosBusquedaExpediente.getTipoBusqueda().equals(
                        new Long(1))) {

            involucrados = involucradoDAO.consultarExpedientesByAliasLike("%"
                    + filtrosBusquedaExpediente.getAlias() + "%");
        } else {
            String nombre = "%";
            String apellidos = "%";
            String apellidoMat = "%";

            if (filtrosBusquedaExpediente.getNombre() != null
                    && filtrosBusquedaExpediente.getNombre().length() > 0) {
                nombre = nombre + filtrosBusquedaExpediente.getNombre();
            }
            if (filtrosBusquedaExpediente.getApellidos() != null
                    && filtrosBusquedaExpediente.getApellidos().length() > 0) {
                apellidos = apellidos
                        + filtrosBusquedaExpediente.getApellidos();
            }
            if (filtrosBusquedaExpediente.getApellidoMat() != null && !filtrosBusquedaExpediente.getApellidoMat().isEmpty()) {
                apellidoMat = apellidoMat + filtrosBusquedaExpediente.getApellidoMat();
            }

            nombre = nombre + "%";
            apellidos = apellidos + "%";
            apellidoMat = apellidoMat + "%";
			//Se coloca el siguiente codigo para que se pueda pasar un DTO y los parametros solos
            //esto es para poder pasar mas parametros de filtrado como el discriminante y el usuario
            filtrosBusquedaExpediente.setNombre(nombre);
            filtrosBusquedaExpediente.setApellidos(apellidos);
            filtrosBusquedaExpediente.setApellidoMat(apellidoMat);

            involucrados = involucradoDAO.consultarExpedientesByNombre(filtrosBusquedaExpediente);
        }

        for (Involucrado involucrado : involucrados) {
            involucradosDTO.add(InvolucradoTransformer
                    .transformarInvolucradoBasico(involucrado));
        }

        for (InvolucradoDTO involucradoDTO : involucradosDTO) {
            List<NombreDemografico> nombres = nombreDemograficoDAO
                    .consutarNombresByInvolucrado(involucradoDTO
                            .getElementoId());
            involucradoDTO
                    .setNombresDemograficoDTO(NombreDemograficoTransformer
                            .transformarNombreDemograficoBasico(nombres));
        }

        return involucradosDTO;
    }

    /*
     * TODO Se debe verificar esta funcionalida en general y reemplazar 
     * por el buscar expediente con SP
     */
    @Override
    public List<InvolucradoDTO> buscarExpedientesPorFiltrosYDiscriminante(
            FiltroExpedienteDTO filtrosBusquedaExpediente, UsuarioDTO usuarioFirmado)
            throws NSJPNegocioException {

        List<InvolucradoDTO> involucradosDTO = new ArrayList<InvolucradoDTO>();
        List<Involucrado> involucrados = new ArrayList<Involucrado>();

        if (filtrosBusquedaExpediente.getAlias() != null
                && filtrosBusquedaExpediente.getTipoBusqueda().equals(
                        new Long(0))) {

            involucrados = involucradoDAO
                    .consultarExpedientesByAlias(filtrosBusquedaExpediente
                            .getAlias());
        } else if (filtrosBusquedaExpediente.getAlias() != null
                && filtrosBusquedaExpediente.getTipoBusqueda().equals(
                        new Long(1))) {

            involucrados = involucradoDAO.consultarExpedientesByAliasLike("%"
                    + filtrosBusquedaExpediente.getAlias() + "%");
        } else {
            String nombre = "%";
            String apellidos = "%";
            String apellidoMat = "%";

            if (filtrosBusquedaExpediente.getNombre() != null
                    && filtrosBusquedaExpediente.getNombre().length() > 0) {
                nombre = nombre + filtrosBusquedaExpediente.getNombre();
            }
            if (filtrosBusquedaExpediente.getApellidos() != null
                    && filtrosBusquedaExpediente.getApellidos().length() > 0) {
                apellidos = apellidos
                        + filtrosBusquedaExpediente.getApellidos();
            }
            if (!filtrosBusquedaExpediente.getApellidoMat().isEmpty()) {
                apellidoMat = apellidoMat + filtrosBusquedaExpediente.getApellidoMat();
            }

            nombre = nombre + "%";
            apellidos = apellidos + "%";
            apellidoMat = apellidoMat + "%";
			//Se coloca el siguiente codigo para que se pueda pasar un DTO y los parametros solos
            //esto es para poder pasar mas parametros de filtrado como el discriminante y el usuario
            filtrosBusquedaExpediente.setNombre(nombre);
            filtrosBusquedaExpediente.setApellidos(apellidos);
            filtrosBusquedaExpediente.setApellidoMat(apellidoMat);

            involucrados = involucradoDAO.consultarExpedientesByNombre(filtrosBusquedaExpediente);
        }

        if (usuarioFirmado != null
                && usuarioFirmado.getInstitucion() != null
                && usuarioFirmado.getInstitucion().getConfInstitucionId() != null
                && usuarioFirmado.getInstitucion().getConfInstitucionId().equals(Instituciones.PJ.getValorId())) {

            for (Involucrado involucrado : involucrados) {

                if (usuarioFirmado.getFuncionario() != null
                        && usuarioFirmado.getFuncionario().getDiscriminante() != null
                        && usuarioFirmado.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null
                        && involucrado.getExpediente() != null
                        && involucrado.getExpediente().getDiscriminante() != null
                        && involucrado.getExpediente().getDiscriminante().getCatDiscriminanteId() != null
                        && usuarioFirmado
                        .getFuncionario()
                        .getDiscriminante()
                        .getCatDiscriminanteId()
                        .equals(involucrado.getExpediente()
                                .getDiscriminante()
                                .getCatDiscriminanteId())) {

                    involucradosDTO.add(InvolucradoTransformer
                            .transformarInvolucradoBasico(involucrado));
                }
            }

        } else {
            for (Involucrado involucrado : involucrados) {
                involucradosDTO.add(InvolucradoTransformer
                        .transformarInvolucradoBasico(involucrado));
            }
        }

        for (InvolucradoDTO involucradoDTO : involucradosDTO) {
            List<NombreDemografico> nombres = nombreDemograficoDAO
                    .consutarNombresByInvolucrado(involucradoDTO
                            .getElementoId());
            involucradoDTO
                    .setNombresDemograficoDTO(NombreDemograficoTransformer
                            .transformarNombreDemograficoBasico(nombres));
        }

        return involucradosDTO;
    }

    @Override
    public List<ExpedienteDTO> consultarExpedienteActividadAreaAnio(
            FiltroExpedienteDTO filtroExpedienteDTO)
            throws NSJPNegocioException {
        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA OBTENER LOS EXPEEDIENTES CORRESPONDIENTES A UNA ACTIVIDAD, AREA Y A&Ntilde;O ****/");
        }
        if (filtroExpedienteDTO.getAnio() == null
                || filtroExpedienteDTO.getIdArea() == null
                || filtroExpedienteDTO.getIdActividad() == null) {
            logger.error(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        if (filtroExpedienteDTO.getUsuario() == null
                || filtroExpedienteDTO.getUsuario().getFuncionario() == null
                || filtroExpedienteDTO.getUsuario().getFuncionario()
                .getJerarquiaOrganizacional() == null
                || filtroExpedienteDTO.getUsuario().getFuncionario()
                .getJerarquiaOrganizacional()
                .getJerarquiaOrganizacionalId() == null
                || filtroExpedienteDTO.getUsuario().getFuncionario()
                .getClaveFuncionario() != null
                || filtroExpedienteDTO.getUsuario().getFuncionario()
                .getClaveFuncionario() < 0) {
            logger.error(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        List<NumeroExpediente> expsRespuesta = new ArrayList<NumeroExpediente>();

        //Si es de alguna area de uavd no debe filtrar por catDiscriminante pero SI por clave funcionario
        if (filtroExpedienteDTO.getUsuario().getRolACtivo().getRol()
                .getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId()
                .equals(Areas.ATENCION_JURIDICA.parseLong())
                || filtroExpedienteDTO.getUsuario().getRolACtivo().getRol()
                .getJerarquiaOrganizacionalDTO()
                .getJerarquiaOrganizacionalId()
                .equals(Areas.ATENCION_PSICOLOGICA.parseLong())
                || filtroExpedienteDTO.getUsuario().getRolACtivo().getRol()
                .getJerarquiaOrganizacionalDTO()
                .getJerarquiaOrganizacionalId()
                .equals(Areas.ATENCION_VICTIMAS.parseLong())
                || filtroExpedienteDTO.getUsuario().getRolACtivo().getRol()
                .getJerarquiaOrganizacionalDTO()
                .getJerarquiaOrganizacionalId()
                .equals(Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong())) {
            //Se toma la validacion cuando el PM consulta la bandeja 
            filtroExpedienteDTO.setIdDiscriminante(null);
            filtroExpedienteDTO.setIdFuncionario(filtroExpedienteDTO.getUsuario().getFuncionario().getClaveFuncionario());
            expsRespuesta = expedienteDAO.consultarExpedientesActividadAreaAnio(filtroExpedienteDTO);
        } else {

            //Si no es del area de uavd debe aplicar el filtro por catDiscriminante pero NO por claveFuncionario
            long discriminanteId = 0L;

            if (filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante() != null
                    && filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante().getCatDiscriminanteId() != null) {
                discriminanteId = filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante().getCatDiscriminanteId();
            }

            Long claveFuncionario = filtroExpedienteDTO.getUsuario().getFuncionario().getClaveFuncionario();

            RolDTO rol = filtroExpedienteDTO.getUsuario().getRolACtivo().getRol();
            Roles rolAsociado = obtenEnumRol(rol.getRolId());
            //ESte switch se ejecuta en cascada, es decir, para todos los case se ejecuta la ultima instrucci&oacute;n
            switch (rolAsociado) {
                case COORDINADORAMP:
                case COORDINADORJAR:
                case COORDINADORDEF:
                case COORDINADORPER:
                case COORDINADORVIS:
                case COORDPERFIS: {
                    logger.info(" **** Es Coordinador  **** " + rolAsociado);
                    claveFuncionario = null;
                }
            }
            if (!rolAsociado.equals(Roles.ATPENAL)) {
                filtroExpedienteDTO.setIdDiscriminante(discriminanteId);
            }
            filtroExpedienteDTO.setIdFuncionario(claveFuncionario);

            if (rolAsociado == Roles.COORDINADORJAR && filtroExpedienteDTO.getIdActividad().equals(Actividades.RECIBIR_CANALIZACION_JAR.getValorId())
                    && filtroExpedienteDTO.getEstatusMenuCoorJAr() != null
                    && filtroExpedienteDTO.getEstatusMenuCoorJAr().equals(EstatusMenuJAR.ASIGNADOS.getValorId())) {
                expsRespuesta = expedienteDAO.consultarExpedientesActividadAreaJarAsignados(filtroExpedienteDTO);
            } else {
                expsRespuesta = expedienteDAO.consultarExpedientesActividadAreaAnio(filtroExpedienteDTO);
            }
        }

        if (filtroExpedienteDTO.getEstatusMenuCoorJAr() != null) {
            List<NumeroExpediente> listaAlterna = expsRespuesta;
            expsRespuesta = new ArrayList<NumeroExpediente>();
            for (NumeroExpediente numeroExpediente : listaAlterna) {
                boolean op = true;
                if (filtroExpedienteDTO.getEstatusMenuCoorJAr().equals(EstatusMenuJAR.PORASIGNAR.getValorId())) {
                    Expediente expediente = expedienteDAO.buscarUltimoNumeroPorExpedienteIdAreaId(numeroExpediente.getExpediente().getExpedienteId(), Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong());
                    if (expediente != null) {
                        op = false;
                    }
                } else if (filtroExpedienteDTO.getEstatusMenuCoorJAr().equals(EstatusMenuJAR.ASIGNADOS.getValorId())) {
                    op = false;
                    Expediente expediente = expedienteDAO.buscarUltimoNumeroPorExpedienteIdAreaId(numeroExpediente.getExpediente().getExpedienteId(), Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong());
                    if (expediente != null) {
                        List<Expediente> listExpedientes = expedienteDAO.consultarExpedientesPorActividadActualyExpedienteID(Actividades.ATENDER_CANALIZACION_JAR.getValorId(), numeroExpediente.getExpediente().getExpedienteId());
                        if (listExpedientes != null && !listExpedientes.isEmpty()) {
                            op = true;
                        }

                    }
                } else if (filtroExpedienteDTO.getEstatusMenuCoorJAr().equals(EstatusMenuJAR.PROPIOS.getValorId())) {
                    op = false;
                    Expediente expediente = expedienteDAO.buscarUltimoNumeroPorExpedienteIdAreaId(numeroExpediente.getExpediente().getExpedienteId(), Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong());
                    if (expediente != null) {
                        op = true;
                        List<Expediente> listExpedientes = expedienteDAO.consultarExpedientesPorActividadActualyExpedienteID(Actividades.ATENDER_CANALIZACION_JAR.getValorId(), numeroExpediente.getExpediente().getExpedienteId());
                        if (listExpedientes != null && !listExpedientes.isEmpty()) {
                            op = false;
                        }

                    }
                }
                if (op) {
                    expsRespuesta.add(numeroExpediente);
                }
            }
        }

        List<ExpedienteDTO> expsDTO = new ArrayList<ExpedienteDTO>();
        if (expsRespuesta.isEmpty()) {
            PaginadorUtil.obtenerPaginacionManual(expsDTO);
            return expsDTO;
        }
        if (filtroExpedienteDTO.getEstatusMenuCoorJAr() != null
                && (filtroExpedienteDTO.getEstatusMenuCoorJAr().equals(EstatusMenuJAR.ASIGNADOS.getValorId())
                || filtroExpedienteDTO.getEstatusMenuCoorJAr().equals(EstatusMenuJAR.PORASIGNAR.getValorId())
                || filtroExpedienteDTO.getEstatusMenuCoorJAr().equals(EstatusMenuJAR.PROPIOS.getValorId()))) {
            expsRespuesta = paginacionManualJAR(expsRespuesta);
        }
        for (NumeroExpediente numeroExpediente : expsRespuesta) {

            ExpedienteDTO expedienteDTO = ExpedienteTransformer
                    .transformarExpedienteBasico(numeroExpediente);

            if (numeroExpediente.getExpediente().getActividads() != null) {
                for (Actividad actividad : numeroExpediente.getExpediente()
                        .getActividads()) {
                    if (actividad.getTipoActividad().getValorId()
                            .equals(filtroExpedienteDTO.getIdActividad())) {
                        expedienteDTO.setActividadActual(ActividadTransformer
                                .transformarActividad(actividad));
                    }
                }
            }

            List<Involucrado> involucrados = involucradoDAO
                    .obtenerInvByIdExpAndCalidad(numeroExpediente
                            .getExpediente().getExpedienteId(),
                            Calidades.DENUNCIANTE.getValorId(), null);

            for (Involucrado involucrado : involucrados) {
                expedienteDTO.addInvolucradoDTO(InvolucradoTransformer
                        .transformarInvolucradoBasico(involucrado));
            }

            if (involucrados != null && involucrados.isEmpty()) {
                involucrados = involucradoDAO
                        .obtenerInvByIdExpAndCalidad(numeroExpediente
                                .getExpediente().getExpedienteId(),
                                Calidades.DENUNCIANTE_ORGANIZACION.getValorId(), null);
                for (Involucrado involucrado : involucrados) {
                    InvolucradoDTO involucradoDTO = InvolucradoTransformer
                            .transformarInvolucradoBasico(involucrado);
                    Organizacion organizacion = organizacionDAO.obtenerOrganizacionByRelacion(
                            involucrado.getElementoId(), new Long(Relaciones.ORGANIZACION_INVOLUCRADA.ordinal()));
                    involucradoDTO.setOrganizacionDTO(OrganizacionTransformer.transformarOrganizacionBasico(organizacion));
                    expedienteDTO.addInvolucradoDTO(involucradoDTO);
                }
            }

            List<Delito> listDelitos = delitoDAO
                    .obtenerDelitoPorExpediente(numeroExpediente
                            .getExpediente().getExpedienteId());

            for (Delito delito : listDelitos) {
                if (delito.getEsPrincipal() == true) {
                    DelitoDTO delitoDTO = DelitoTransfromer
                            .transformarDelito(delito);
                    expedienteDTO.setDelitoPrincipal(delitoDTO);
                }
            }

            List<NumeroExpediente> numHijos = numeroExpedienteDAO.consultarnumExpedienteHijos(numeroExpediente.getNumeroExpedienteId());

            if (numHijos != null) {
                List<ExpedienteDTO> numHijosDTO = new ArrayList<ExpedienteDTO>();
                for (NumeroExpediente numeroExpedienteHijo : numHijos) {
                    numHijosDTO.add(ExpedienteTransformer.transformarExpedienteBasico(numeroExpedienteHijo));
                }
                expedienteDTO.setNumExpHijos(numHijosDTO);
            }

            //Si se trata de expedientes de procuraduria
            ConfInstitucion confInstitucion = this.expedienteDAO.consultarInsitucionActual();
            if (confInstitucion.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())) {
                if (numeroExpediente.getExpediente() != null && numeroExpediente.getExpediente().getEstatus() != null) {
                    expedienteDTO.setEstatusExpedientePadre(new ValorDTO(numeroExpediente.getExpediente().getEstatus().getValorId(), numeroExpediente.getExpediente().getEstatus().getValor()));
                }
            }

        	// ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de 
            // expedienteDTO.getEstatus();
            // Pero si la referencia es al estatus del n&uacute;mero de expediente asociado a ese expediente:
            // expedienteDTO.getEstatusNumeroExpediente();
            if (numeroExpediente != null && numeroExpediente.getExpediente() != null
                    && numeroExpediente.getExpediente().getEstatus() != null) {
                expedienteDTO.setEstatus(new ValorDTO(
                        numeroExpediente.getExpediente().getEstatus().getValorId(),
                        numeroExpediente.getExpediente().getEstatus().getValor()));
            }

            if (numeroExpediente != null
                    && numeroExpediente.getEstatus() != null
                    && numeroExpediente.getEstatus().getValor() != null) {
                expedienteDTO.setEstatusNumeroExpediente(new ValorDTO(numeroExpediente.getEstatus().getValorId(),
                        numeroExpediente.getEstatus().getValor()));
            }

        	// ------------------------------------------------------------------------------------------------
            expsDTO.add(expedienteDTO);
        } // for

        return new ArrayList<ExpedienteDTO>(expsDTO);
    }

    private List<NumeroExpediente> paginacionManualJAR(
            List<NumeroExpediente> expsRespuesta) {
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        if ((pag.getPage() * pag.getRows()) - pag.getRows() > expsRespuesta.size()) {
            pag.setPage(1);
        }
        int inicio = 0;
        if (pag.getPage() <= 1) {
            inicio = 0;
        } else {
            inicio = ((pag.getPage() - 1) * pag.getRows());
        }
        int indiceFinal = inicio + pag.getRows();
        List<NumeroExpediente> listaAlterna = expsRespuesta;
        expsRespuesta = new ArrayList<NumeroExpediente>();
        if (indiceFinal >= listaAlterna.size()) {
            for (int i = inicio; i < listaAlterna.size(); i++) {
                expsRespuesta.add(listaAlterna.get(i));
            }
        } else {
            for (int i = inicio; i < indiceFinal; i++) {
                expsRespuesta.add(listaAlterna.get(i));
            }
        }

        pag.setTotalRegistros((long) listaAlterna.size());
        pag.setPaginacionHecha(true);
        PaginacionThreadHolder.set(pag);
        return expsRespuesta;
    }

    @Override
    public List<ExpedienteDTO> consultarCanalizadosCoordinadorAmpGeneral(
            FiltroExpedienteDTO filtroExpedienteDTO)
            throws NSJPNegocioException {

        if (filtroExpedienteDTO == null || filtroExpedienteDTO.getIdArea() == null || filtroExpedienteDTO.getIdActividad() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        List<NumeroExpediente> expsRespuesta = new ArrayList<NumeroExpediente>();

        if (filtroExpedienteDTO.getIdTipoActividadComplemento() != null && filtroExpedienteDTO.getIdTipoActividadComplemento() > 0) {
            expsRespuesta = expedienteDAO
                    .consultarExpedientesCanalizadosNoAtendidos(filtroExpedienteDTO);
        } else {
            expsRespuesta = expedienteDAO
                    .consultarExpedientesCanalizados(filtroExpedienteDTO);
        }

        List<ExpedienteDTO> expsDTO = new ArrayList<ExpedienteDTO>();
        for (NumeroExpediente numeroExpediente : expsRespuesta) {

            ExpedienteDTO expedienteDTO = ExpedienteTransformer
                    .transformarExpedienteBasico(numeroExpediente);

            if (numeroExpediente.getExpediente().getActividads() != null) {
                for (Actividad actividad : numeroExpediente.getExpediente()
                        .getActividads()) {
                    if (actividad.getTipoActividad().getValorId()
                            .equals(filtroExpedienteDTO.getIdActividad())) {
                        expedienteDTO.setActividadActual(ActividadTransformer
                                .transformarActividad(actividad));
                    }
                }
            }

            List<Involucrado> involucrados = involucradoDAO
                    .obtenerInvByIdExpAndCalidad(numeroExpediente
                            .getExpediente().getExpedienteId(),
                            Calidades.DENUNCIANTE.getValorId(), null);
            for (Involucrado involucrado : involucrados) {
                expedienteDTO.addInvolucradoDTO(InvolucradoTransformer
                        .transformarInvolucradoBasico(involucrado));
            }

            List<Delito> listDelitos = delitoDAO
                    .obtenerDelitoPorExpediente(numeroExpediente
                            .getExpediente().getExpedienteId());

            for (Delito delito : listDelitos) {
                if (delito.getEsPrincipal() == true) {
                    DelitoDTO delitoDTO = DelitoTransfromer
                            .transformarDelito(delito);
                    expedienteDTO.setDelitoPrincipal(delitoDTO);
                }
            }

            List<NumeroExpediente> numHijos = numeroExpedienteDAO.consultarnumExpedienteHijos(numeroExpediente.getNumeroExpedienteId());

            if (numHijos != null) {
                List<ExpedienteDTO> numHijosDTO = new ArrayList<ExpedienteDTO>();
                for (NumeroExpediente numeroExpedienteHijo : numHijos) {
                    numHijosDTO.add(ExpedienteTransformer.transformarExpedienteBasico(numeroExpedienteHijo));
                }
                expedienteDTO.setNumExpHijos(numHijosDTO);
            }

            //Si se trata de expedientes de procuraduria
            ConfInstitucion confInstitucion = this.expedienteDAO.consultarInsitucionActual();
            if (confInstitucion.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())) {
                if (numeroExpediente.getExpediente() != null && numeroExpediente.getExpediente().getEstatus() != null) {
                    expedienteDTO.setEstatusExpedientePadre(new ValorDTO(numeroExpediente.getExpediente().getEstatus().getValorId(), numeroExpediente.getExpediente().getEstatus().getValor()));
                }
            }

       	    // ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de 
            // expedienteDTO.getEstatus();
            // Pero si la referencia es al estatus del n&uacute;mero de expediente asociado a ese expediente:
            // expedienteDTO.getEstatusNumeroExpediente();
            if (numeroExpediente != null && numeroExpediente.getExpediente() != null
                    && numeroExpediente.getExpediente().getEstatus() != null) {
                expedienteDTO.setEstatus(new ValorDTO(numeroExpediente.getExpediente().getEstatus()
                        .getValorId(), numeroExpediente.getExpediente().getEstatus().getValor()));
            }

            if (numeroExpediente != null
                    && numeroExpediente.getEstatus() != null && numeroExpediente.getEstatus().getValor() != null) {
                expedienteDTO.setEstatusNumeroExpediente(new ValorDTO(numeroExpediente.getEstatus().getValorId(), numeroExpediente.getEstatus().getValor()));
            }

    		// ------------------------------------------------------------------------------------------------
            expsDTO.add(expedienteDTO);
        } // for

        return new ArrayList<ExpedienteDTO>(expsDTO);
    }

    /**
     * Metodo que permite consultar los datos generales de un expediente
     *
     */
    @Override
    public DatosGeneralesExpedienteDTO obtenerDatosGeneralesExpediente(
            ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
        logger.info("/**** Consultar Datos Generales de Expediente por ID ****/");

        NumeroExpediente numeroExpediente = new NumeroExpediente();
        DatosGeneralesExpedienteDTO expedienteRetorno = new DatosGeneralesExpedienteDTO();

        List<Involucrado> involucrados = new ArrayList<Involucrado>();
        List<Objeto> objetosExpediente = new ArrayList<Objeto>();

        Expediente expediente = new Expediente();
        if (expedienteDTO.getNumeroExpedienteId() != null) {
            numeroExpediente = numeroExpedienteDAO.read(expedienteDTO
                    .getNumeroExpedienteId());
            expediente = numeroExpediente.getExpediente();

            expedienteRetorno.setFechaApertura(DateUtils.formatear(expediente.getFechaCreacion()));
            expedienteRetorno.setExpedienteId(expediente.getExpedienteId());

            //Permite calcular el numero de documentos del expediente
            List<DocumentoDTO> listaDocumentoDTOs = consultarDocumentoService.consultarDocumentosExpediente(expedienteDTO);
            if (listaDocumentoDTOs != null) {
                expedienteRetorno.setTotalDocumentosDelExpediente(listaDocumentoDTOs.size());
            } else {
                expedienteRetorno.setTotalDocumentosDelExpediente(0);
            }

            //SE OBTIENEN LOS INVOLUCRADOS DEL EXPEDIENTE
            involucrados = involucradoDAO.consultarInvolucradosByExpediente(expediente.getExpedienteId());
            //SE OBTIENEN LOS OBJETOS DEL EXPEDIENTE
            objetosExpediente = objetoDAO.consultarObjetosByExpediente(expediente.getExpedienteId());

            expedienteRetorno = DatosGeneralesExpedienteTransformer
                    .transformaDatosGeneralesExpedienteDTO(objetosExpediente,
                            expedienteRetorno);

            //Se obtiene el estatus del expediente
            if (numeroExpediente.getEstatus() != null) {
                expedienteRetorno.setEstatusNumeroExpediente(numeroExpediente
                        .getEstatus().getValor());
            } else {
                expedienteRetorno.setEstatusNumeroExpediente("");
            }

            //Se obtienen la etapa del expediente
            if (numeroExpediente.getEtapa() != null) {
                expedienteRetorno.setEtapaExpediente(numeroExpediente.getEtapa().getValor());
            } else {
                expedienteRetorno.setEtapaExpediente("");
            }
            //Si la institucion es DEFENSORIA se muestra la ETAPA
            ConfInstitucion confInstitucion = expedienteDAO
                    .consultarInsitucionActual();
            if (confInstitucion != null
                    && confInstitucion.getConfInstitucionId().equals(
                            Instituciones.DEF.getValorId())
                    && numeroExpediente.getCatEtapa() != null
                    && numeroExpediente.getCatEtapa().getCatEtapaValor() != null
                    && numeroExpediente.getCatEtapa().getCatEtapaValor()
                    .getValor() != null) {
                expedienteRetorno.setEtapaExpediente(numeroExpediente
                        .getCatEtapa().getCatEtapaValor().getValor());
            }

            //Si se trata de expedientes de procuraduria
            if (confInstitucion.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())) {
                if (numeroExpediente.getExpediente() != null && numeroExpediente.getExpediente().getEstatus() != null) {
                    expedienteDTO.setEstatusExpedientePadre(new ValorDTO(numeroExpediente.getExpediente().getEstatus().getValorId(), numeroExpediente.getExpediente().getEstatus().getValor()));
                    expedienteRetorno.setEstatusNumeroExpediente(numeroExpediente.getExpediente().getEstatus().getValor());
                }
            }

            //Se obtiene el orgien del documento
            if (expediente.getOrigen() != null) {
                expedienteRetorno.setOrigenExpediente(expediente.getOrigen().getValor());
            } else {
                expedienteRetorno.setOrigenExpediente("-");
            }

			//Se obtiene el orgien del Numero de expediente
            //Atenci&oacute;n Temprana Penal / Unidad de Investigaci&oacute;n 
            if (numeroExpediente.getJerarquiaOrganizacional() != null) {
                expedienteRetorno.setOrigenNumeroExpediente("Origen: " + consultarDetalleExpedienteService.
                        consultarOrigendeExpediente(new ExpedienteDTO(expediente.getExpedienteId())).getNombre());
            } else {
                expedienteRetorno.setOrigenNumeroExpediente("");
            }
        }

        // Responsable del expediente
        if (numeroExpediente != null && numeroExpediente.getFuncionario() != null) {
            String responsableTramite = "";
            if (numeroExpediente.getFuncionario().getNombreFuncionario() != null) {
                responsableTramite = numeroExpediente.getFuncionario().getNombreFuncionario();
            }
            if (numeroExpediente.getFuncionario().getApellidoPaternoFuncionario() != null) {
                if (responsableTramite != "") {
                    responsableTramite += " " + numeroExpediente.getFuncionario().getApellidoPaternoFuncionario();
                } else {
                    responsableTramite = numeroExpediente.getFuncionario().getApellidoPaternoFuncionario();
                }
            }
            if (numeroExpediente.getFuncionario().getApellidoMaternoFuncionario() != null) {
                if (responsableTramite != "") {
                    responsableTramite += " " + numeroExpediente.getFuncionario().getApellidoMaternoFuncionario();
                } else {
                    responsableTramite = numeroExpediente.getFuncionario().getApellidoMaternoFuncionario();
                }
            }
            expedienteRetorno.setResponsableTramite(responsableTramite);
        } else {
            expedienteRetorno.setResponsableTramite("");
        }

        // Estatus actuaci&oacute;n
        if (numeroExpediente != null && numeroExpediente.getEstatus() != null
                && numeroExpediente.getEstatus().getValor() != null) {
            expedienteRetorno.setEstatusActuacion(numeroExpediente.getEstatus().getValor());
        } else {
            expedienteRetorno.setEstatusActuacion("");
        }

        //Se agrega el numero de expediente alterno al dto regresado
        expedienteRetorno.setNumeroExpedienteAlterno(numeroExpediente.getNumExpAlterno());

        //Permite saber el Total de DENUNCIANTE(s)
        List<Involucrado> denunciantes = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
                Calidades.DENUNCIANTE.getValorId(), null);
        List<Involucrado> denunciantesOrganizacion = involucradoDAO.obtenerInvByIdExpAndCalidad(
                expediente.getExpedienteId(),
                Calidades.DENUNCIANTE_ORGANIZACION.getValorId(), null);
        expedienteRetorno.setTotalDenunciantes(denunciantes.size() + denunciantesOrganizacion.size());
        //Permite llenar el HashMap de Involucrados
        lHmInvolucrados.put(Calidades.DENUNCIANTE.getValorId(), obtenerDetalleDeInvolucrado(denunciantes));
        lHmInvolucrados.put(Calidades.DENUNCIANTE_ORGANIZACION.getValorId(), obtenerDetalleDeInvolucrado(denunciantesOrganizacion));

        //Permite saber el Total de VICTIMA(s)
        List<Involucrado> victimas = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
                Calidades.VICTIMA_PERSONA.getValorId(), null);
        expedienteRetorno.setTotalVictimas(victimas.size());
        List<String> listaDeInvolucrados = obtenerDetalleDeInvolucrado(victimas);

        //Permite validar si el denunciante es la victima		
        Involucrado loDenunciante = null;
        if (denunciantes != null && denunciantes.size() > 0) {
            loDenunciante = denunciantes.get(0);
        }

        if (loDenunciante != null && loDenunciante.getCondicion() == 1) {
            expedienteRetorno.setTotalVictimas(victimas.size() + 1);
            logger.info("El denunciante es victima");
            logger.info("El id del denunciante es " + loDenunciante.getElementoId());
            listaDeInvolucrados.addAll(obtenerDetalleDeInvolucrado(denunciantes));
        } else {
            expedienteRetorno.setTotalVictimas(victimas.size());
            logger.info("El denunciante NO es victima");
        }
        lHmInvolucrados.put(Calidades.VICTIMA_PERSONA.getValorId(), listaDeInvolucrados);

        //Permite saber el Total de PROBABLES RESPONSABLE(s)
        List<Involucrado> probableResps = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
                Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId(), null);
        List<Involucrado> probableRespsOrganizacion = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
                Calidades.PROBABLE_RESPONSABLE_ORGANIZACION.getValorId(), null);
        int totalPR = probableResps.size() + probableRespsOrganizacion.size();
        expedienteRetorno.setTotalProbablesResposables(totalPR);
        lHmInvolucrados.put(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId(), obtenerDetalleDeInvolucrado(probableResps));
        lHmInvolucrados.put(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION.getValorId(), obtenerDetalleDeInvolucrado(probableRespsOrganizacion));

        //Permite saber el Total de TESTIGO(s)
        List<Involucrado> testigos = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
                Calidades.TESTIGO.getValorId(), null);
        expedienteRetorno.setTotalTestigos(testigos.size());
        lHmInvolucrados.put(Calidades.TESTIGO.getValorId(), obtenerDetalleDeInvolucrado(testigos));

        //Permite saber el Total de TRADUCTOR(s)
        List<Involucrado> traductores = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
                Calidades.TRADUCTOR.getValorId(), null);
        expedienteRetorno.setTotalTraductores(traductores.size());
        lHmInvolucrados.put(Calidades.TRADUCTOR.getValorId(), obtenerDetalleDeInvolucrado(traductores));

        //Permite saber el Total de QUIEN DETUVO(s)
        List<Involucrado> quienesDetuvieron = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
                Calidades.QUIEN_DETUVO.getValorId(), null);
        expedienteRetorno.setQuienDetuvo(String.valueOf(quienesDetuvieron.size()));
        lHmInvolucrados.put(Calidades.QUIEN_DETUVO.getValorId(), obtenerDetalleDeInvolucrado(quienesDetuvieron));
        expedienteRetorno.setInvolucrados(lHmInvolucrados);

        //Permite saber si una denuncia es anonima o no
        if (denunciantes.size() > 0) {
            Involucrado loDenun = denunciantes.get(0);
            expedienteRetorno.setEsDesconocido(loDenun.getDesconocido());
        } else {
            expedienteRetorno.setEsDesconocido("");
        }

        if (involucrados.size() > 0) {
            expedienteRetorno.setTotalProbablesResposables(totalPR);
        } else {
            expedienteRetorno.setTotalProbablesResposables(0);
        }

		//Permite obtener el detalle de los objetos
        //Se ingresan los objetos al hashmap de los objetos            
        lHmObjetos.put(Objetos.VEHICULO.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.VEHICULO));
        lHmObjetos.put(Objetos.EQUIPO_DE_COMPUTO.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.EQUIPO_DE_COMPUTO));
        lHmObjetos.put(Objetos.EQUIPO_TELEFONICO.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.EQUIPO_TELEFONICO));
        lHmObjetos.put(Objetos.ARMA.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.ARMA));
        lHmObjetos.put(Objetos.EXPLOSIVO.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.EXPLOSIVO));
        lHmObjetos.put(Objetos.SUSTANCIA.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.SUSTANCIA));
        lHmObjetos.put(Objetos.ANIMAL.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.ANIMAL));
        lHmObjetos.put(Objetos.AERONAVE.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.AERONAVE));
        lHmObjetos.put(Objetos.EMBARCACION.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.EMBARCACION));
        lHmObjetos.put(Objetos.NUMERARIO.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.NUMERARIO));
        lHmObjetos.put(Objetos.VEGETAL.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.VEGETAL));
        lHmObjetos.put(Objetos.DOCUMENTO_OFICIAL.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.DOCUMENTO_OFICIAL));
        lHmObjetos.put(Objetos.JOYA.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.JOYA));
        lHmObjetos.put(Objetos.OBRA_DE_ARTE.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.OBRA_DE_ARTE));
        lHmObjetos.put(Objetos.PERICIAL.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.PERICIAL));
        lHmObjetos.put(Objetos.OTRO.getValorId(), obtenerDetalleDeObjetosPorTipo(objetosExpediente, Objetos.OTRO));
        expedienteRetorno.setObjetos(lHmObjetos);

        return expedienteRetorno;
    }

    @Override
    public ExpedienteDTO obtenerExpedientePorNumeroExpediente(
            String numeroExpediente) throws NSJPNegocioException {

        if (logger.isDebugEnabled()) {
            logger.debug("/**** WRAPPER PARA OBTENER EL NUMERO EXPEDIENTE ID****/");
        }

        ExpedienteDTO resp = new ExpedienteDTO();

        Long expId = this.expedienteDAO
                .consultarExpedientePorNumeroExpediente(numeroExpediente);
        logger.debug("Numero Expediente ID obtenido " + expId);

        if (expId == null) {
            logger.error(CodigoError.FORMATO);
        } else {
            ExpedienteDTO ex = new ExpedienteDTO();
            ex.setNumeroExpedienteId(expId);
            ex.setMedidasCautelaresSolicitadas(true);
            ex.setInvolucradosSolicitados(true);
            resp = this.obtenerExpediente(ex);
        }
        return resp;
    }

    @Override
    public ExpedienteDTO obtenerExpedientePorNumeroExpedienteYNumeroCaso(
            String numeroExpediente, String numCaso) throws NSJPNegocioException {

        if (logger.isDebugEnabled()) {
            logger.debug("/**** WRAPPER PARA OBTENER EL NUMERO EXPEDIENTE ID y numero de caso****/");
        }

        ExpedienteDTO resp = new ExpedienteDTO();

        Long expId = this.expedienteDAO
                .obtenerIdNumExpedientePorNumeroExpedienteYNumeroCaso(numeroExpediente, numCaso);
        logger.debug("numeroExpediente: " + expId);
        logger.debug("numCaso:" + numCaso);

        if (expId == null) {
            logger.error(CodigoError.FORMATO);
        } else {
            ExpedienteDTO ex = new ExpedienteDTO();
            ex.setNumeroExpedienteId(expId);
            ex.setMedidasCautelaresSolicitadas(true);
            ex.setInvolucradosSolicitados(true);
            resp = this.obtenerExpediente(ex);
        }
        return resp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService#
     * consultarExpedientesConEventosHistorico
     * (mx.gob.segob.nsjp.dto.usuario.UsuarioDTO)
     */
    @Override
    public List<ExpedienteDTO> consultarExpedientesConEventosHistorico(
            Long casoId, UsuarioDTO usuario) throws NSJPNegocioException {
        List<ExpedienteDTO> expedientes = new ArrayList<ExpedienteDTO>();
        List<NumeroExpediente> numsExpediente = numeroExpedienteDAO
                .consultarExpedientesConEventosHistorico(casoId,
                        usuario.getIdUsuario());
        for (NumeroExpediente numExp : numsExpediente) {
            expedientes.add(ExpedienteTransformer
                    .transformarExpedienteBasico(numExp));
        }
        return expedientes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService#
     * obtenerNumeroExpedienteByNumExp
     * (mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO)
     */
    @Override
    public ExpedienteDTO obtenerNumeroExpedienteByNumExp(
            ExpedienteDTO expedienteDTO, UsuarioDTO usuario) throws NSJPNegocioException {

        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA OBTENER EL NUMERO EXPEDIENTE POR numeroExpediente ****/");
        }

        if (expedienteDTO.getNumeroExpediente() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        /*
         * Usado para obtener el discriminante Id
         */
        long discriminanteId = 0L;

        if (usuario != null
                && usuario.getFuncionario() != null
                && usuario.getFuncionario().getDiscriminante() != null
                && usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null) {

            discriminanteId = usuario.getFuncionario().getDiscriminante()
                    .getCatDiscriminanteId();
        }

        NumeroExpediente numExpediente = numeroExpedienteDAO
                .obtenerNumeroExpediente(expedienteDTO.getNumeroExpediente(), discriminanteId);

        ExpedienteDTO expRespuestaDTO = ExpedienteTransformer
                .transformarExpedienteBasico(numExpediente);

        return expRespuestaDTO;
    }

    @SuppressWarnings("static-access")
    @Override
    public List<ExpedienteDTO> consultarNumeroExpedienteHistorico(UsuarioDTO usuario)
            throws NSJPNegocioException {

        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA CONSULTAR LAS CAUSAS HISTORICO ****/");
        }

        Parametro param = parametroDAO.obtenerPorClave(Parametros.LIMITE_HISTORICO_CONSULTAS);
        Long dias = new Long(param.getValor());

        Calendar calTempDec = Calendar.getInstance();
        calTempDec.setTime(new Date());
        calTempDec.add(calTempDec.DATE, -dias.intValue());

        /*
         * Usado para obtener el discriminante Id
         */
        long discriminanteId = 0L;

        if (usuario != null
                && usuario.getFuncionario() != null
                && usuario.getFuncionario().getDiscriminante() != null
                && usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null) {

            discriminanteId = usuario.getFuncionario().getDiscriminante()
                    .getCatDiscriminanteId();
        }

        List<NumeroExpediente> respuesta = numeroExpedienteDAO
                .consultarCausasHistorico(calTempDec, discriminanteId);
        List<ExpedienteDTO> listRespuesta = new ArrayList<ExpedienteDTO>();
        for (NumeroExpediente numeroExpediente : respuesta) {
            List<NumeroExpediente> listCarpEjec = numeroExpedienteDAO
                    .consultarCarpetasEjecucionPorCausa(numeroExpediente
                            .getNumeroExpedienteId());
            ExpedienteDTO expRespuesta = ExpedienteTransformer
                    .transformarExpedienteBasico(numeroExpediente);

            List<ExpedienteDTO> listCarpEjecRespuesta = new ArrayList<ExpedienteDTO>();
            for (NumeroExpediente carpetaEjec : listCarpEjec) {
                listCarpEjecRespuesta.add(ExpedienteTransformer
                        .transformarExpedienteBasico(carpetaEjec));
            }
            expRespuesta.setNumExpHijos(listCarpEjecRespuesta);
            listRespuesta.add(expRespuesta);
        }

        return listRespuesta;
    }

    @Override
    public List<ExpedienteDTO> consultarNumeroExpedienteByEstatus(
            TipoExpediente tipoExpediente, EstatusExpediente estatusExpediente)
            throws NSJPNegocioException {

        List<NumeroExpediente> listNumExp = numeroExpedienteDAO
                .consultarNumeroExpedienteByEstatus(tipoExpediente,
                        estatusExpediente);

        List<ExpedienteDTO> listRespuesta = new ArrayList<ExpedienteDTO>();
        for (NumeroExpediente numeroExpediente : listNumExp) {
            listRespuesta.add(ExpedienteTransformer
                    .transformarExpedienteBasico(numeroExpediente));
        }

        return listRespuesta;
    }

    @Override
    public List<ExpedienteDTO> consultarExpedientesUsuarioArea(
            UsuarioDTO usuarioDTO) throws NSJPNegocioException {

        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA CONSULTAR LOS EXPEDIENTES ASOCIADOS A UN USUARIO ****/");
        }

        if (usuarioDTO.getAreaActual() == null
                || usuarioDTO.getAreaActual().getAreaId() == null
                || usuarioDTO.getFuncionario() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        /*
         * Usado para setear la agencia y consultar turnos para agencias de PGJ
         */
        long agenciaId = 0L;

        List<Long> areas = new ArrayList<Long>();
        if (usuarioDTO.getAreaActual() != null && usuarioDTO.getAreaActual().getAreaId() != null) {
            areas.add(usuarioDTO.getAreaActual().getAreaId());
        }

        ConfInstitucion confInstitucionPG = expedienteDAO.consultarInsitucionActual();
        if (confInstitucionPG.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())) {
            if (usuarioDTO != null
                    && usuarioDTO.getFuncionario() != null
                    && usuarioDTO.getFuncionario().getDiscriminante() != null
                    && usuarioDTO.getFuncionario().getDiscriminante()
                    .getCatDiscriminanteId() != null) {

                agenciaId = usuarioDTO.getFuncionario().getDiscriminante().getCatDiscriminanteId();
            }
        }

        List<NumeroExpediente> numsExpedientes = numeroExpedienteDAO
                .consultarByUsuarioArea(usuarioDTO.getFuncionario().getClaveFuncionario(), areas,
                        EstatusExpediente.ABIERTO.getValorId(), agenciaId, null);

        List<ExpedienteDTO> expedientes = new ArrayList<ExpedienteDTO>();
        for (NumeroExpediente numeroExpediente : numsExpedientes) {
            Expediente expediente = numeroExpediente.getExpediente();
            //los delitos se obitienen en el transformer
            ExpedienteDTO expedienteDTO = ExpedienteTransformer.transformaExpediente(expediente);
            expedienteDTO.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
            expedienteDTO.setFechaApertura(numeroExpediente.getFechaApertura());
            expedienteDTO.setNumeroExpediente(numeroExpediente.getNumeroExpediente());

    	    // ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de 
            // expedienteDTO.getEstatus();
            // Pero si la referencia es al estatus del n&uacute;mero de expediente asociado a ese expediente:
            // expedienteDTO.getEstatusNumeroExpediente();
            if (numeroExpediente.getExpediente() != null
                    && numeroExpediente.getExpediente().getEstatus() != null) {
                expedienteDTO.setEstatus(new ValorDTO(numeroExpediente.getExpediente().getEstatus().getValorId()));
            }

            if (numeroExpediente.getEstatus() != null) {
                expedienteDTO.setEstatusNumeroExpediente(ValorTransformer.transformar(numeroExpediente.getEstatus()));
            }

            // ------------------------------------------------------------------------------------------------
            if (numeroExpediente.getEtapa() != null) {
                expedienteDTO.setEtapa(new ValorDTO(numeroExpediente.getEtapa().getValorId()));
            }
            //Setear delito
            if (expedienteDTO.getDelitoPrincipal() != null) {
                expedienteDTO.setDelitoPrincipal(DelitoTransfromer.transformarDelito(delitoDAO.read(expedienteDTO.getDelitoPrincipal().getDelitoId())));
            }

            expedientes.add(expedienteDTO);
        }
        return expedientes;
    }

    @Override
    public List<ExpedienteDTO> consultarExpedientePorAreaEstatusRemitente(
            UsuarioDTO usuarioDTO, AreaDTO area, Long estatusExpediente)
            throws NSJPNegocioException {
        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA CONSULTAR LOS EXPEDIENTES DE UN AREA PARA UN USARIO Y FILTRADO ****/");
        }

        /* Verificar Par&aacute;metros */
        if (usuarioDTO == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        } else if (usuarioDTO.getAreaActual() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        /*
         * Usado para setear la agencia y consultar turnos para agencias de PGJ
         */
        long agenciaId = 0L;

        List<Long> areas = new ArrayList<Long>();
        if (usuarioDTO.getAreaActual() != null && usuarioDTO.getAreaActual().getAreaId() != null) {
            areas.add(usuarioDTO.getAreaActual().getAreaId());
        }

        ConfInstitucion confInstitucionPG = expedienteDAO.consultarInsitucionActual();
        if (confInstitucionPG.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())) {
            if (usuarioDTO != null
                    && usuarioDTO.getFuncionario() != null
                    && usuarioDTO.getFuncionario().getDiscriminante() != null
                    && usuarioDTO.getFuncionario().getDiscriminante()
                    .getCatDiscriminanteId() != null) {

                agenciaId = usuarioDTO.getFuncionario().getDiscriminante().getCatDiscriminanteId();
            }
        }

        List<ExpedienteDTO> expedientesDTO = new ArrayList<ExpedienteDTO>();
			//  if (area != null) {
        // Nota: List<Expediente> expedientes = expedienteDAO
        //       .buscarExpedientesRemitentes(usuarioDTO.getAreaActual()
        //  	 .getAreaId(), area.getAreaId());
        // La consulta no esta construido!!!! devolv&iacute;a null!!!
        //			for (Expediente exp : expedientes) {
        //					expedientesDTO.add(ExpedienteTransformer.transformaExpediente(exp));
        //			}
        // }
        if (estatusExpediente != null) {
            logger.debug("/*** SE CONSULTAN DEL USUARIO POR ESTATUS ++++/");
            List<NumeroExpediente> numsExpedientes = numeroExpedienteDAO
                    .consultarByUsuarioArea(usuarioDTO.getIdUsuario(), areas, estatusExpediente, agenciaId, null);

            for (NumeroExpediente numeroExpediente : numsExpedientes) {
                ExpedienteDTO expeDTO = ExpedienteTransformer.transformarExpedienteDenunciante(numeroExpediente);
                List<Involucrado> ivols = involucradoDAO.consultarInvolucradosByExpediente(numeroExpediente.getExpediente().getExpedienteId());
                List<InvolucradoDTO> denunciantes = new ArrayList<InvolucradoDTO>();
                for (Involucrado inv : ivols) {
                    Elemento elemento = eleDao.read(inv.getElementoId());
                    if (elemento.getCalidad().getCalidadId().equals(Calidades.DENUNCIANTE.getValorId())) {
                        denunciantes.add(InvolucradoTransformer.transformarInvolucrado(inv));
                    }
                }
                expeDTO.setInvolucradosDTO(denunciantes);
                expedientesDTO.add(expeDTO);
            }
        }

        return expedientesDTO;
    }

    @Override
    public List<ExpedienteDTO> consultarHistoricoCausasExpediente() throws NSJPNegocioException {

        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA CONSULTAR LOS EXPEDIENTES DE TIPO CAUSA CON CARPETA DE INESTIGACION EN ESTATUS CERRADO ****/");
        }

        Parametro parametro = parametroDAO.obtenerPorClave(Parametros.LIMITE_HISTORICO_CONSULTAS);
        Long dias = new Long(parametro.getValor());

        Calendar calTemp = Calendar.getInstance();
        calTemp.setTime(new Date());
        calTemp.add(Calendar.DATE, -dias.intValue());

        List<NumeroExpediente> numerosExpCausa = numeroExpedienteDAO.consultarHistoricoCausasExpediente(calTemp.getTime());

        List<ExpedienteDTO> numsExpDTORetorno = new ArrayList<ExpedienteDTO>();
        for (NumeroExpediente numeroExpediente : numerosExpCausa) {
            numsExpDTORetorno.add(ExpedienteTransformer.transformarExpedienteBasico(numeroExpediente));
        }
        return numsExpDTORetorno;
    }

    @Override
    public List<ExpedienteDTO> consultarCarpetasEjecucionPorCausa(
            ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA CONSULTAR LAS CARPETAS DE EJECUCION DE UNA CAUSA ****/");
        }

        if (expedienteDTO.getNumeroExpedienteId() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        List<NumeroExpediente> numerosExpCarpeta = numeroExpedienteDAO.consultarCarpetasEjecucionPorCausa(expedienteDTO.getNumeroExpedienteId());

        List<ExpedienteDTO> numsExpDTORetorno = new ArrayList<ExpedienteDTO>();
        for (NumeroExpediente numeroExpediente : numerosExpCarpeta) {
            numsExpDTORetorno.add(ExpedienteTransformer.transformarExpedienteBasico(numeroExpediente));
        }
        return numsExpDTORetorno;
    }
    /*
     * (non-Javadoc)
     * @see mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService#consultarExpedientesPorFiltro(java.util.Date, java.util.Date, mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO, mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente, java.lang.Long)
     */

    @Override
    public List<ExpedienteDTO> consultarExpedientesPorFiltro(Date fechaInicio,
            Date fechaFin, FuncionarioDTO usuario, TipoExpediente tipo,
            Long numeroExpedientePadreId) throws NSJPNegocioException {

        Funcionario funcionario = FuncionarioTransformer.transformarFuncionario(usuario);

        List<NumeroExpediente> numeros
                = numeroExpedienteDAO.consultarNumeroExpedientePorFiltro(fechaInicio, fechaFin, funcionario, tipo, numeroExpedientePadreId);
        List<ExpedienteDTO> expedientes = new ArrayList<ExpedienteDTO>();
        List<Involucrado> lista = new LinkedList<Involucrado>();
        ExpedienteDTO expediente = null;
        for (NumeroExpediente ne : numeros) {
            expediente = ExpedienteTransformer.transformarExpedienteBasico(ne);
            lista = involucradoDAO.consultarInvolucradosByExpediente(ne.getExpediente().getExpedienteId());
            for (Involucrado inv : lista) {
                expediente.addInvolucradoDTO(InvolucradoTransformer.transformarInvolucradoBasico(inv));
            }
            expedientes.add(expediente);
        }
        return expedientes;
    }
    /*
     * (non-Javadoc)
     * @see mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService#consultarNumeroExpedientePorNumeroCaso(java.lang.String)
     */

    @Override
    public List<ExpedienteDTO> consultarNumeroExpedientePorNumeroCaso(
            String caso) {
        List<ExpedienteDTO> expedientes = new ArrayList<ExpedienteDTO>();
        List<NumeroExpediente> numeros = numeroExpedienteDAO.consultarNumeroExpedientePorNumeroCaso(caso);
        for (NumeroExpediente ne : numeros) {

            expedientes.add(ExpedienteTransformer.transformarExpedienteBasico(ne));

        }
        return expedientes;
    }

    public List<String> obtenerDetalleDeInvolucrado(
            List<Involucrado> involucrados) {
        List<String> llDetalle = new ArrayList<String>();

        for (Involucrado loInvolucrado : involucrados) {
            List<NombreDemografico> lNombreDemografico = new ArrayList<NombreDemografico>();
            if (loInvolucrado.getElementoId() != null
                    && nombreDemograficoDAO != null) {
                if (loInvolucrado.getTipoPersona().equals("Moral")) {
                    Organizacion organizacion = organizacionDAO
                            .obtenerOrganizacionByRelacion(
                                    loInvolucrado.getElementoId(),
                                    new Long(
                                            Relaciones.ORGANIZACION_INVOLUCRADA
                                            .ordinal()));
                    NombreDemografico nombre = new NombreDemografico();
                    nombre.setNombre(organizacion.getNombreOrganizacion());
                    lNombreDemografico.add(nombre);
                } else {
                    lNombreDemografico = nombreDemograficoDAO
                            .consutarNombresByInvolucrado(loInvolucrado
                                    .getElementoId());
                }
            }
            if (lNombreDemografico != null && !lNombreDemografico.isEmpty()) {
                String nombre = "";
                if (lNombreDemografico.get(0).getNombre() != null) {
                    nombre = lNombreDemografico.get(0).getNombre();
                }
                if (lNombreDemografico.get(0).getApellidoPaterno() != null) {
                    nombre = nombre
                            + " " + lNombreDemografico.get(0).getApellidoPaterno();
                }
                if (lNombreDemografico.get(0).getApellidoMaterno() != null) {
                    nombre = nombre
                            + " " + lNombreDemografico.get(0).getApellidoMaterno();
                }
                llDetalle.add(nombre);
            }
        }
        return llDetalle;
    }

    private static List<String> obtenerDetalleDeObjetosPorTipo(List<Objeto> objetosExpediente,
            Objetos tipoObjeto) {
        List<String> llDetalle = new ArrayList<String>();

        for (Objeto objeto : objetosExpediente) {
            if (objeto.getValorByTipoObjetoVal().getValorId().equals(tipoObjeto.getValorId())) {
                llDetalle.add(obtenDetalleDeObjeto(objeto));
            }
        }
        return llDetalle;
    }

    public static String obtenDetalleDeObjeto(Objeto elemento) {
        String cadena = "";

        //OBJETOS
        if (elemento instanceof Vehiculo) {
            Vehiculo loObjeto = (Vehiculo) elemento;
            cadena = cadena + "Veh&#237;culo" + SEPARADOR;
            cadena = (loObjeto.getValorByTipoVehiculo() != null ? cadena + loObjeto.getValorByTipoVehiculo().getValor() + SEPARADOR : cadena);
            cadena = (loObjeto.getPlaca() != null ? cadena + loObjeto.getPlaca() + SEPARADOR : cadena);
        } else if (elemento instanceof EquipoComputo) {
            EquipoComputo loObjeto = (EquipoComputo) elemento;
            cadena = cadena + "Equipo de C&#243;mputo" + SEPARADOR;
            cadena = (loObjeto.getTipoEquipo() != null ? cadena + loObjeto.getTipoEquipo().getValor() + SEPARADOR : cadena);
        } else if (elemento instanceof Telefonia) {
            Telefonia loObjeto = (Telefonia) elemento;
            cadena = cadena + "Equipo telef&#243;nico" + SEPARADOR;
            cadena = (loObjeto.getTipoTelefono() != null ? cadena + loObjeto.getTipoTelefono().getValor() + SEPARADOR : cadena);
        } else if (elemento instanceof Arma) {
            Arma loObjeto = (Arma) elemento;
            cadena = cadena + "Arma" + SEPARADOR;
            cadena = (loObjeto.getTipoArma() != null ? cadena + loObjeto.getTipoArma().getValor() + SEPARADOR : cadena);
        } else if (elemento instanceof Explosivo) {
            Explosivo loObjeto = (Explosivo) elemento;
            cadena = cadena + "Explosivo" + SEPARADOR;
            cadena = (loObjeto.getTipoExplosivo() != null ? cadena + loObjeto.getTipoExplosivo().getValor() + SEPARADOR : cadena);
        } else if (elemento instanceof Aeronave) {
            Aeronave loObjeto = (Aeronave) elemento;
            cadena = cadena + "Aeronave" + SEPARADOR;
            cadena = (loObjeto.getTipoAeroNave() != null ? cadena + loObjeto.getTipoAeroNave().getValor() + SEPARADOR : cadena);
        } else if (elemento instanceof Animal) {
            Animal loObjeto = (Animal) elemento;
            cadena = cadena + "Animal" + SEPARADOR;
            cadena = (loObjeto.getTipoAnimal() != null ? cadena + loObjeto.getTipoAnimal().getValor() + SEPARADOR : cadena);
        } else if (elemento instanceof DocumentoOficial) {
            DocumentoOficial loObjeto = (DocumentoOficial) elemento;
            cadena = cadena + "Documento Oficial" + SEPARADOR;
            cadena = (loObjeto.getTipoDocumento() != null ? cadena + loObjeto.getTipoDocumento().getValor() + SEPARADOR : cadena);
        } else if (elemento instanceof Embarcacion) {
            Embarcacion loObjeto = (Embarcacion) elemento;
            cadena = cadena + "Embarcaci&#243;n" + SEPARADOR;
            cadena = (loObjeto.getTipoEmbarcacion() != null ? cadena + loObjeto.getTipoEmbarcacion().getValor() + SEPARADOR : cadena);
        } else if (elemento instanceof Joya) {
            Joya loObjeto = (Joya) elemento;
            cadena = cadena + "Joya" + SEPARADOR;
            cadena = (loObjeto.getTipoJoya() != null ? cadena + loObjeto.getTipoJoya().getValor() + SEPARADOR : cadena);
        } else if (elemento instanceof Numerario) {
            Numerario loObjeto = (Numerario) elemento;
            cadena = cadena + "Numerario" + SEPARADOR;
            cadena = (loObjeto.getMoneda() != null ? cadena + loObjeto.getMoneda() + SEPARADOR : cadena);
        } else if (elemento instanceof ObraArte) {
            ObraArte loObjeto = (ObraArte) elemento;
            cadena = cadena + "Obra de Arte" + SEPARADOR;
            cadena = (loObjeto.getTipoObraArte() != null ? cadena + loObjeto.getTipoObraArte().getValor() + SEPARADOR : cadena);
        } else if (elemento instanceof ObjetoPericial) {
            ObjetoPericial loObjeto = (ObjetoPericial) elemento;
            cadena = cadena + "Pericial " + SEPARADOR;
            cadena = (loObjeto.getIndicioVal() != null ? cadena + loObjeto.getIndicioVal().getValor() + SEPARADOR : cadena);
        } else if (elemento instanceof Sustancia) {
            Sustancia loObjeto = (Sustancia) elemento;
            cadena = cadena + "Sustancia" + SEPARADOR;
            cadena = (loObjeto.getTipoSustancia() != null ? cadena + loObjeto.getTipoSustancia().getValor() + SEPARADOR : cadena);
        } else if (elemento instanceof Vegetal) {
            Vegetal loObjeto = (Vegetal) elemento;
            cadena = cadena + "Vegetal" + SEPARADOR;
            cadena = (loObjeto.getTipoVegetal() != null ? cadena + loObjeto.getTipoVegetal().getValor() + SEPARADOR : cadena);
        } else {
            cadena = cadena + "Otro" + SEPARADOR;
            cadena = ((elemento.getNombreObjeto() != null && !elemento.getNombreObjeto().isEmpty()) ? cadena + elemento.getNombreObjeto() + SEPARADOR : cadena);
        }

        if (cadena.lastIndexOf(SEPARADOR) != -1) {
            cadena = cadena.substring(0, cadena.lastIndexOf(SEPARADOR));
        }
        return cadena;
    }

    @Override
    public List<ExpedienteDTO> consultarNumeroExpedienteByTipoYEstatus(
            TipoExpediente tipoExpediente, EstatusExpediente estatusExpediente, UsuarioDTO usuario)
            throws NSJPNegocioException {

        /*
         * Usado para obtener el discriminante Id
         */
        long discriminanteId = 0L;

        if (usuario != null
                && usuario.getFuncionario() != null
                && usuario.getFuncionario().getDiscriminante() != null
                && usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null) {

            discriminanteId = usuario.getFuncionario().getDiscriminante()
                    .getCatDiscriminanteId();
        }

        List<NumeroExpediente> numerosExpedientes = numeroExpedienteDAO
                .consultarNumeroExpedienteByTipoYEstatus(tipoExpediente != null
                                ? tipoExpediente.getValorId()
                                : null,
                        estatusExpediente != null
                                ? estatusExpediente.getValorId()
                                : null, discriminanteId);

        List<ExpedienteDTO> expRetorno = new ArrayList<ExpedienteDTO>();
        for (NumeroExpediente numeroExpediente : numerosExpedientes) {
            ExpedienteDTO expDTO = ExpedienteTransformer.transformarExpedienteBasico(numeroExpediente);
            List<AudienciaDTO> audiencias = new ArrayList<AudienciaDTO>();
            for (Audiencia audiencia : numeroExpediente.getAudiencias()) {
                AudienciaDTO audDTO = AudienciaTransformer.transformarDTO(audiencia);
                audiencias.add(audDTO);
            }
            expDTO.setAudiencias(audiencias);
            if (numeroExpediente.getExpediente().getHecho() != null && numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo() != null) {
                HechoDTO hechoDto = new HechoDTO();
                hechoDto.setHechoId(numeroExpediente.getExpediente().getHecho().getHechoId());
                AvisoHechoDelictivoDTO avisoDto = new AvisoHechoDelictivoDTO();
                avisoDto.setDocumentoId(numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getDocumentoId());
                avisoDto.setFolioNotificacion(numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getFolioNotificacion());
                avisoDto.setFolioDocumento(numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getFolioDocumento());
                avisoDto.setFechaAtencion(numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getFechaAtencion());
                avisoDto.setFechaCreacion(numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getFechaCreacion());

                if (numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getCatDelito() != null) {
                    CatDelitoDTO delDto = new CatDelitoDTO();
                    delDto.setCatDelitoId(numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getCatDelito().getCatDelitoId());
                    delDto.setNombre(numeroExpediente.getExpediente().getHecho().getAvisoHechoDelictivo().getCatDelito().getNombre());
                    avisoDto.setCatDelito(delDto);
                }

                hechoDto.setAvisoHechoDelictivo(avisoDto);
                expDTO.setHechoDTO(hechoDto);
            }
            expRetorno.add(expDTO);
        }
        return expRetorno;
    }

    @Override
    public Long obtenerExpedienteIdPorNumExpId(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException {
        return this.expedienteDAO.obtenerExpedienteIdPorIdNumerExpediente(expedienteDTO.getNumeroExpedienteId());
    }

    @Override
    public List<ExpedienteDTO> consultarExpedientesPorUsuarioAreaEstatus(
            UsuarioDTO usuarioDTO, Long estatus) throws NSJPNegocioException {

        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA CONSULTAR LOS EXPEDIENTES ASOCIADOS A UN USUARIO, &Aacute;REA y ESTATUS (Opcional) ****/");
        }

        if (usuarioDTO.getFuncionario() == null || usuarioDTO.getFuncionario().getClaveFuncionario() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        List<NumeroExpediente> numsExpedientes = null;
        List<Long> areas = new ArrayList<Long>();
        Long claveFuncionario = usuarioDTO.getFuncionario().getClaveFuncionario();

        if (usuarioDTO.getAreaActual() != null && usuarioDTO.getAreaActual().getAreaId() != null) {
            areas.add(usuarioDTO.getAreaActual().getAreaId());
        }
        if (usuarioDTO.getRolACtivo() != null && usuarioDTO.getRolACtivo().getRol().getRolId() == Roles.COORDINADORAMP.getValorId()) {
            areas.add(Areas.UNIDAD_INVESTIGACION.parseLong());
            claveFuncionario = 0L;
        }

        /*
         * Usado para setear la agencia y consultar turnos para agencias de PGJ
         */
        long discriminanteId = 0L;

        if (usuarioDTO != null
                && usuarioDTO.getFuncionario() != null
                && usuarioDTO.getFuncionario().getDiscriminante() != null
                && usuarioDTO.getFuncionario().getDiscriminante()
                .getCatDiscriminanteId() != null) {

            discriminanteId = usuarioDTO.getFuncionario().getDiscriminante()
                    .getCatDiscriminanteId();
        }

        if (usuarioDTO.getRolACtivo() != null && usuarioDTO.getRolACtivo().getRol().getRolId() == Roles.AGENTEMP.getValorId()) {
            if (EstatusExpediente.CANALIZADO_JAR.getValorId().equals(estatus)) {
                discriminanteId = 0L;
                usuarioDTO.getFuncionario().getUnidadIEspecializada().setCatUIEId(null);
            }
        }

        if (areas != null && !areas.isEmpty()
                && usuarioDTO.getFuncionario() != null && usuarioDTO.getFuncionario().getUnidadIEspecializada() != null
                && usuarioDTO.getFuncionario().getUnidadIEspecializada().getCatUIEId() != null) {
            numsExpedientes = numeroExpedienteDAO
                    .consultarByUsuarioArea(claveFuncionario, areas, estatus, discriminanteId, usuarioDTO.getFuncionario().getUnidadIEspecializada().getCatUIEId());
        } else if (areas != null && !areas.isEmpty()) {
            numsExpedientes = numeroExpedienteDAO
                    .consultarByUsuarioArea(claveFuncionario, areas, estatus, discriminanteId, null);
        } else {
            numsExpedientes = numeroExpedienteDAO
                    .consultarByUsuarioArea(claveFuncionario, null, estatus, discriminanteId, null);
        }

        List<ExpedienteDTO> expedientes = new ArrayList<ExpedienteDTO>();
        for (NumeroExpediente numeroExpediente : numsExpedientes) {
            Expediente expediente = numeroExpediente.getExpediente();
            //los delitos se obitienen en el transformer
            ExpedienteDTO expedienteDTO = ExpedienteTransformer.transformaExpediente(expediente);
            expedienteDTO.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
            expedienteDTO.setFechaApertura(numeroExpediente.getFechaApertura());
            expedienteDTO.setNumeroExpediente(numeroExpediente.getNumeroExpediente());

    	    // ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de 
            // expedienteDTO.getEstatus();
            // Pero si la referencia es al estatus del n&uacute;mero de expediente asociado a ese expediente:
            // expedienteDTO.getEstatusNumeroExpediente();
            if (numeroExpediente.getExpediente() != null
                    && numeroExpediente.getExpediente().getEstatus() != null) {
                expedienteDTO.setEstatus(
                        new ValorDTO(numeroExpediente.getExpediente().getEstatus().getValorId(), numeroExpediente.getExpediente().getEstatus().getValor()));
            }

            if (numeroExpediente.getEstatus() != null) {
                expedienteDTO.setEstatusNumeroExpediente(ValorTransformer.transformar(numeroExpediente.getEstatus()));
            }

            // ------------------------------------------------------------------------------------------------
            //Si se trata de expedientes de procuraduria
            ConfInstitucion confInstitucion = this.expedienteDAO.consultarInsitucionActual();
            if (confInstitucion.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())) {
                if (numeroExpediente.getEstatus() != null) {
                    expedienteDTO.setEstatusExpedientePadre(new ValorDTO(numeroExpediente.getEstatus().getValorId(), numeroExpediente.getEstatus().getValor()));
                }
            }

            List<Involucrado> involucradosExp = involucradoDAO.consultarInvolucradosByExpediente(expediente.getExpedienteId());
            List<InvolucradoDTO> involucradosDTO = new ArrayList<InvolucradoDTO>();

            for (Involucrado involucrado : involucradosExp) {
                involucradosDTO.add(InvolucradoTransformer.transformarInvolucrado(involucrado));
            }

            expedienteDTO.setInvolucradosDTO(involucradosDTO);
            //Setear delito
            if (expedienteDTO.getDelitoPrincipal() != null) {
                expedienteDTO.setDelitoPrincipal(DelitoTransfromer.transformarDelito(delitoDAO.read(expedienteDTO.getDelitoPrincipal().getDelitoId())));
            }

            expedientes.add(expedienteDTO);
        }
        return expedientes;
    }

    @Override
    public ExpedienteDTO consultarNumExpPorFuncionarioYNumExp(Long claveFuncionario,
            Long numExpId) throws NSJPNegocioException {

        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA OBTENER LA INFORMACION DEL EXPEDIENTE SI EL FUNCIONARIO TIENE LOS PERMISOS REQUERIDOS ****/");
        }

        if (claveFuncionario == null || numExpId == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        NumeroExpediente numeroExpediente = numeroExpedienteDAO.consultarNumExpPorFuncionarioYNumExp(claveFuncionario, numExpId);

        if (numeroExpediente != null) {
            ExpedienteDTO expedienteDTO = ExpedienteTransformer.transformarExpedienteBasico(numeroExpediente);

            PermisoExpediente permisoExpediente = permisoExpedienteDAO.obtnerPermisoFuncionario(claveFuncionario, numExpId);
            if (permisoExpediente != null) {
                expedienteDTO.setEsEscritura(permisoExpediente.getEsEscritura());
                expedienteDTO.setFechaLimitePermiso(permisoExpediente.getFechaLimite());
            } else {
                expedienteDTO.setEsPropietario(true);
            }

            return expedienteDTO;
        }

        return null;
    }

    @Override
    public List<ExpedienteDTO> consultarNumExpPorFuncionario(
            Long claveFuncionario) throws NSJPNegocioException {
        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA OBTENER LOS EXPEDIENTES PROPIOS Y CON PERMISOS DEL FUNCIONARIO ****/");
        }
        if (claveFuncionario == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        List<NumeroExpediente> expedientesFuncionario = numeroExpedienteDAO.consultarNumExpPorFuncionario(claveFuncionario);

        List<ExpedienteDTO> expFuncionarioDTO = new ArrayList<ExpedienteDTO>();

        for (NumeroExpediente numeroExpediente : expedientesFuncionario) {
            expFuncionarioDTO.add(ExpedienteTransformer.transformarExpediente(numeroExpediente));
        }

        return expFuncionarioDTO;
    }

    @Override
    public List<ExpedienteDTO> consultarExpedientesDelFuncionario(
            UsuarioDTO usuario, List<ValorDTO> estatusExpedientes) throws NSJPNegocioException {
        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA OBTENER LOS EXPEDIENTES PROPIOS DEL FUNCIONARIO ****/");
        }
        if (usuario == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        if (usuario.getFuncionario() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        if (usuario.getFuncionario().getClaveFuncionario() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        /*
         * Usado para setear la agencia y consultar turnos para agencias de PGJ
         */
        ConfInstitucion confInstitucionPG = expedienteDAO.consultarInsitucionActual();
        if (!confInstitucionPG.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())) {
            if (usuario != null
                    && usuario.getFuncionario() != null
                    && usuario.getFuncionario().getDiscriminante() != null
                    && usuario.getFuncionario().getDiscriminante()
                    .getCatDiscriminanteId() != null) {
                usuario.getFuncionario().getDiscriminante().setCatDiscriminanteId(0L);
            }
        }

        Funcionario funcionario = FuncionarioTransformer.transformarFuncionario(usuario.getFuncionario());

        if (usuario.getAreaActual() != null
                && usuario.getAreaActual()
                .getBuscarEnJerarquia() != null
                && usuario.getAreaActual()
                .getBuscarEnJerarquia()) {

            funcionario.getArea().setJerarquiaOrgSubordinadas(
                    new HashSet<JerarquiaOrganizacional>());

            JerarquiaOrganizacional raiz = funcionario.getArea();
            List<JerarquiaOrganizacional> lstJerarquiasDependientes = jerarquiaOrganizacionalDAO
                    .getArbolJerarquiasDependientes(raiz);

            if (lstJerarquiasDependientes != null
                    && !lstJerarquiasDependientes.isEmpty()) {
                funcionario.getArea().getJerarquiaOrgSubordinadas()
                        .addAll(lstJerarquiasDependientes);
            }
        }

        Long rolActivoId = (usuario.getRolACtivo() != null
                && usuario.getRolACtivo().getRol() != null
                && usuario.getRolACtivo().getRol().getRolId() != null)
                        ? usuario.getRolACtivo().getRol().getRolId() : null;

		//Para que el funcionario pueda ver todos los expedientes 
        //independientemente del edificio en que se encuentra asignado
        Boolean aplicarFiltroCatDiscriminante = true;
		//Utilizado para que el funcionario pueda consultar los
        //expedientes sin considerar el propietario del mismo
        //por ejemplo para los coordinadores
        Boolean aplicarFiltroFuncionario = true;

        if (rolActivoId != null) {
            if (rolActivoId.equals(Roles.POLICIAMINISTER.getValorId())
                    || rolActivoId.equals(Roles.COORDINADORAMPGENERAL.getValorId())) {
                aplicarFiltroCatDiscriminante = false;
            }
            if (rolActivoId.equals(Roles.COORDINADORAMP.getValorId())
                    || rolActivoId.equals(Roles.COORDINADORAMP.getValorId())
                    || rolActivoId.equals(Roles.COORDINADORJAR.getValorId())
                    || rolActivoId.equals(Roles.COORDINADORPER.getValorId())
                    || rolActivoId.equals(Roles.COORDINADORVIS.getValorId())) {
                aplicarFiltroFuncionario = false;
                //Aplicar la busqueda de la jerarquia subordinadas
                JerarquiaOrganizacional raiz = funcionario.getArea();
                List<JerarquiaOrganizacional> lstJerarquiasDependientes = jerarquiaOrganizacionalDAO
                        .getArbolJerarquiasDependientes(raiz);
                if (lstJerarquiasDependientes != null
                        && !lstJerarquiasDependientes.isEmpty()) {
                    funcionario.getArea().getJerarquiaOrgSubordinadas()
                            .addAll(lstJerarquiasDependientes);
                }
            }
            if (rolActivoId.equals(Roles.FACILITADOR.getValorId())) {
                funcionario.setArea(new JerarquiaOrganizacional(Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong()));
            }

        }

        List<Valor> estatusValor = null;
        if (estatusExpedientes != null
                && !estatusExpedientes.isEmpty()) {
            estatusValor = new ArrayList<Valor>();
            for (ValorDTO estatus : estatusExpedientes) {
                estatusValor.add(new Valor(estatus.getIdCampo()));
            }
        }

        List<NumeroExpediente> expedientesFuncionario = numeroExpedienteDAO
                .consultarExpedientesDelFuncionario(funcionario,
                        aplicarFiltroCatDiscriminante, aplicarFiltroFuncionario, estatusValor);

        List<ExpedienteDTO> expFuncionarioDTO = new ArrayList<ExpedienteDTO>();

        for (NumeroExpediente numeroExpediente : expedientesFuncionario) {
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
            expedienteDTO.setInvolucradosSolicitados(true);
            expedienteDTO = obtenerExpediente(expedienteDTO);

            expFuncionarioDTO.add(expedienteDTO);
        }

        return expFuncionarioDTO;
    }

    @Override
    public List<ExpedienteDTO> consultarExpedientescConPermisoFuncionario(
            Long claveFuncionario) throws NSJPNegocioException {
        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA OBTENER LOS EXPEDIENTES CON PERMISO DEL FUNCIONARIO ****/");
        }
        if (claveFuncionario == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        UsuarioDTO usuarioDTO = usuarioService.consultarUsuarioPorClaveFuncionario(claveFuncionario);

        /*
         * Usado para turnos para agencias de PGJ
         */
        long discriminanteId = 0L;

        if (usuarioDTO != null
                && usuarioDTO.getFuncionario() != null
                && usuarioDTO.getFuncionario().getDiscriminante() != null
                && usuarioDTO.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null) {

            discriminanteId = usuarioDTO.getFuncionario().getDiscriminante()
                    .getCatDiscriminanteId();
        }

        List<NumeroExpediente> expedientesFuncionario = permisoExpedienteDAO.consultarExpedientescConPermisoFuncionario(claveFuncionario, discriminanteId);
        List<ExpedienteDTO> expFuncionarioDTO = new ArrayList<ExpedienteDTO>();

        for (NumeroExpediente numeroExpediente : expedientesFuncionario) {
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
            expedienteDTO.setInvolucradosSolicitados(true);
            expedienteDTO = obtenerExpediente(expedienteDTO);

            PermisoExpediente permisoExpediente = permisoExpedienteDAO.obtnerPermisoFuncionario(claveFuncionario, numeroExpediente.getNumeroExpedienteId());
            expedienteDTO.setFechaLimitePermiso(permisoExpediente.getFechaLimite());
            expedienteDTO.setEsEscritura(permisoExpediente.getEsEscritura());
            expFuncionarioDTO.add(expedienteDTO);
        }

        return expFuncionarioDTO;
    }

    @Override
    public List<ExpedienteDTO> consultarExpedientesPermisoFuncionarioJerarquiaRol(
            UsuarioDTO usuarioDTO, Long claveFuncionarioPermiso) throws NSJPNegocioException {

        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA OBTENER LOS EXPEDIENTES CON PERMISO DEL FUNCIONARIO ****/");
        }
        if (claveFuncionarioPermiso == null || claveFuncionarioPermiso < 0
                || usuarioDTO == null || usuarioDTO.getAreaActual() == null
                || usuarioDTO.getAreaActual().getAreaId() == null
                || usuarioDTO.getAreaActual().getAreaId() < 0) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        List<NumeroExpediente> expedientesFuncionario = new ArrayList<NumeroExpediente>();
        //Se coloca if para modificar la busqueda de los expedientes asociados en jerarquias inferiores(Jerarquias hijas en donde no se generan numeros de expedientes)
        if (usuarioDTO.getAreaActual().getAreaId().equals(Areas.FISCAL_FACILITADOR.parseLong())) {
            expedientesFuncionario = permisoExpedienteDAO
                    .consultarExpedientesPermisoFuncionarioJerarquiaRol(claveFuncionarioPermiso, Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong());
        } else if (usuarioDTO.getAreaActual().getAreaId().equals(Areas.COORDINACION_UNIDAD_INVESTIGACION.parseLong())) {
            expedientesFuncionario = permisoExpedienteDAO
                    .consultarExpedientesPermisoFuncionarioJerarquiaRol(claveFuncionarioPermiso, Areas.UNIDAD_INVESTIGACION.parseLong());
        } else {
            expedientesFuncionario = permisoExpedienteDAO
                    .consultarExpedientesPermisoFuncionarioJerarquiaRol(claveFuncionarioPermiso, usuarioDTO
                            .getAreaActual().getAreaId());
        }

        List<ExpedienteDTO> expFuncionarioDTO = new ArrayList<ExpedienteDTO>();

        for (NumeroExpediente numeroExpediente : expedientesFuncionario) {
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setNumeroExpedienteId(numeroExpediente
                    .getNumeroExpedienteId());
            expedienteDTO.setInvolucradosSolicitados(true);
            expedienteDTO = obtenerExpediente(expedienteDTO);

            PermisoExpediente permisoExpediente = permisoExpedienteDAO
                    .obtnerPermisoFuncionario(claveFuncionarioPermiso, numeroExpediente
                            .getNumeroExpedienteId());
            expedienteDTO.setFechaLimitePermiso(permisoExpediente
                    .getFechaLimite());
            expedienteDTO.setEsEscritura(permisoExpediente.getEsEscritura());
            expFuncionarioDTO.add(expedienteDTO);
        }

        return expFuncionarioDTO;
    }

    public ExpedienteDTO consultarExpedienteRelacionadoAArea(String numeroExpediente, Long areaId) throws NSJPNegocioException {

        ExpedienteDTO expedienteDTO = new ExpedienteDTO();
        if (numeroExpediente == null || numeroExpediente.trim().isEmpty() || areaId == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        Long expedienteId = expedienteDAO.consultarExpedienteIdPorNumeroExpediente(numeroExpediente);
        if (expedienteId == null) {
            throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
        }

        List<NumeroExpediente> listaNumeroExpediente = expedienteDAO.buscarNumeroExpedientes(expedienteId, areaId);

        if (listaNumeroExpediente != null && !listaNumeroExpediente.isEmpty()) {
            expedienteDTO = ExpedienteTransformer.transformarExpediente(listaNumeroExpediente.get(0));
        }
        return expedienteDTO;
    }

    /**
     * Metodo que permite consultar el resumen con los datos generales de un
     * expediente de UAVD	*
     */
    @Override
    public DatosGeneralesExpedienteUAVDDTO obtenerResumenDeExpedienteUAVD(
            SolicitudDTO solicitudDTO) throws NSJPNegocioException {
        logger.info("/**** Consultar Datos Generales de Expediente por ID en UAVD ****/");

        if (solicitudDTO == null || solicitudDTO.getDocumentoId() == null || solicitudDTO.getDocumentoId() <= 0) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        DatosGeneralesExpedienteUAVDDTO loResumen = new DatosGeneralesExpedienteUAVDDTO();
        NumeroExpediente numeroExpediente = null;

        //Se consulta la solicitud para obtener el NumeroExpediente_id asociado
        Solicitud loSolicitudBD = solicitudDAO.consultarSolicitudPorId(solicitudDTO.getDocumentoId());
        logger.info("/**** loSolicitudBD.getNumeroExpediente() UAVD ****/" + loSolicitudBD.getNumeroExpediente().getNumeroExpedienteId());
        if (loSolicitudBD != null) {
            loResumen.setTipoSolicitud(loSolicitudBD.getTipoSolicitud() != null && loSolicitudBD.getTipoSolicitud().getValor() != null
                    ? loSolicitudBD.getTipoSolicitud().getValor() : "-");

            Expediente expediente = new Expediente();
            if (loSolicitudBD.getNumeroExpediente().getNumeroExpedienteId() != null) {
                numeroExpediente = numeroExpedienteDAO.read(loSolicitudBD.getNumeroExpediente().getNumeroExpedienteId());
                expediente = numeroExpediente.getExpediente();

                List<Involucrado> victimas = new ArrayList<Involucrado>();

                //MP Solicitante
                loResumen.setAmpSolicitante(loSolicitudBD.getFuncionarioSolicitante() != null
                        ? loSolicitudBD.getFuncionarioSolicitante().getNombreCompleto() : "-");
                //Area solicitante
                JerarquiaOrganizacional areaOrigen = null;
                if (loSolicitudBD.getAreaOrigen() != null && loSolicitudBD.getAreaOrigen() > 0) {

                    areaOrigen = jerarquiaOrganizacionalDAO.read(loSolicitudBD.getAreaOrigen());

                }

                loResumen.setAreaSolicitante(areaOrigen != null
                        && areaOrigen.getNombre() != null && !areaOrigen.getNombre().isEmpty()
                                ? areaOrigen.getNombre() : "-");

                //Fecha de creacion
                loResumen.setFechaDeCreacionDelExpediente(expediente != null && expediente.getFechaCreacion() != null
                        ? DateUtils.formatear(expediente.getFechaCreacion()) : "-");
                //Estatus del expediente
                loResumen.setEstatusDelExpediente(loSolicitudBD.getNumeroExpediente() != null && loSolicitudBD.getNumeroExpediente().getEstatus() != null
                        ? loSolicitudBD.getNumeroExpediente().getEstatus().getValor() : "-");

                loResumen.setExpedienteId(expediente.getExpedienteId());

                //Permite consultar el nombre de la victima
                victimas = involucradoDAO.obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
                        Calidades.VICTIMA_PERSONA.getValorId(), null);

                if (victimas == null || victimas.size() == 0) {

                    // Permite consultar si un denunciante es victima tambien
                    victimas = involucradoDAO.obtenerInvByIdExpAndCalidad(
                            expediente.getExpedienteId(),
                            Calidades.DENUNCIANTE.getValorId(), null);

                    if (victimas != null && victimas.size() > 0) {
                        if (victimas.get(0).getCondicion() != null
                                && victimas.get(0).getCondicion()
                                .equals(new Short("0"))) {
                            victimas = null;
                        }
                    }
                }

                List<String> listaDeInvolucrados = obtenerDetalleDeInvolucrado(victimas);
                loResumen.setNombreDeLaVictima(listaDeInvolucrados != null && listaDeInvolucrados.size() > 0 ? listaDeInvolucrados.get(0) : "-");

                //Se consulta el delito obtenido
                List<Delito> delitosBD = delitoDAO.obtenerDelitoPorExpediente(expediente.getExpedienteId());
                if (delitosBD != null && delitosBD.size() > 0) {
                    loResumen.setDelito(delitosBD.get(0).getCatDelito() != null && delitosBD.get(0).getCatDelito().getNombre() != null
                            ? delitosBD.get(0).getCatDelito().getNombre() : "-");
                } else {
                    loResumen.setDelito("-");
                }

                if (numeroExpediente != null
                        && numeroExpediente.getEstatus() != null
                        && numeroExpediente.getEstatus().getValor() != null) {
                    loResumen.setEstatusActuacion(numeroExpediente.getEstatus().getValor());
                } else {
                    loResumen.setEstatusActuacion("");
                }

                if (numeroExpediente != null
                        && numeroExpediente.getFuncionario() != null) {
                    String responsableTramite = "";
                    if (numeroExpediente.getFuncionario().getNombreFuncionario() != null) {
                        responsableTramite = numeroExpediente.getFuncionario().getNombreFuncionario();
                    }
                    if (numeroExpediente.getFuncionario().getApellidoPaternoFuncionario() != null) {
                        if (responsableTramite != "") {
                            responsableTramite += " " + numeroExpediente.getFuncionario().getApellidoPaternoFuncionario();
                        } else {
                            responsableTramite = numeroExpediente.getFuncionario().getApellidoPaternoFuncionario();
                        }
                    }
                    if (numeroExpediente.getFuncionario().getApellidoMaternoFuncionario() != null) {
                        if (responsableTramite != "") {
                            responsableTramite += " " + numeroExpediente.getFuncionario().getApellidoMaternoFuncionario();
                        } else {
                            responsableTramite = numeroExpediente.getFuncionario().getApellidoMaternoFuncionario();
                        }
                    }
                    loResumen.setResponsableTramite(responsableTramite);
                } else {
                    loResumen.setResponsableTramite("");
                }
            }
        }
        return loResumen;
    }

    public List<ExpedienteDTO> buscarRemisionesConIPH(EstatusExpediente estatusExpediente) throws NSJPNegocioException {

        List<ExpedienteDTO> expedientesDTO = new ArrayList<ExpedienteDTO>();

        List<NumeroExpediente> numerosExpedientes = numeroExpedienteDAO
                .consultarNumeroExpedienteByTipoYEstatus(null,
                        estatusExpediente != null
                                ? estatusExpediente.getValorId()
                                : null, 0L);

        for (NumeroExpediente numeroExpediente : numerosExpedientes) {
            ExpedienteDTO ex = new ExpedienteDTO();
            ex.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
            ex.setInvolucradosSolicitados(true);

            ExpedienteDTO exDTO = this.obtenerExpediente(ex);

		    // ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de 
            // exDTO.getEstatus();
            // Pero si la referencia es al estatus del n&uacute;mero de expediente asociado a ese expediente:
            // exDTO.getEstatusNumeroExpediente();
            if (numeroExpediente.getEstatus() != null) {
                exDTO.setEstatusNumeroExpediente(new ValorDTO(numeroExpediente.getEstatus()
                        .getValorId(), numeroExpediente.getEstatus().getValor()));
            }

            if (numeroExpediente.getExpediente() != null
                    && numeroExpediente.getExpediente().getEstatus() != null) {
                exDTO.setEstatus(new ValorDTO(
                        numeroExpediente.getExpediente().getEstatus().getValorId(),
                        numeroExpediente.getExpediente().getEstatus().getValor()));
            }

 		    // ------------------------------------------------------------------------------------------------
            expedientesDTO.add(exDTO);
        }

        return expedientesDTO;
    }

    public List<ExpedienteDTO> buscarRemisionesConIPH(EstatusExpediente estatusExpediente, Long idDiscriminante) throws NSJPNegocioException {

        List<ExpedienteDTO> expedientesDTO = new ArrayList<ExpedienteDTO>();

        List<NumeroExpediente> numerosExpedientes = numeroExpedienteDAO
                .consultarNumeroExpedienteByTipoYEstatus(null,
                        estatusExpediente != null
                                ? estatusExpediente.getValorId()
                                : null, idDiscriminante);

        for (NumeroExpediente numeroExpediente : numerosExpedientes) {
            ExpedienteDTO ex = new ExpedienteDTO();
            ex.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
            ex.setInvolucradosSolicitados(true);

            ExpedienteDTO exDTO = this.obtenerExpediente(ex);

		    // ------------------------------------------------------------------------------------------------
            // Ojo!!! si se va a realizar una referencia al estatus del expediente, realizarlo por medio de 
            // exDTO.getEstatus();
            // Pero si la referencia es al estatus del n&uacute;mero de expediente asociado a ese expediente:
            // exDTO.getEstatusNumeroExpediente();
            if (numeroExpediente.getEstatus() != null) {
                exDTO.setEstatusNumeroExpediente(new ValorDTO(numeroExpediente.getEstatus()
                        .getValorId(), numeroExpediente.getEstatus().getValor()));
            }

            if (numeroExpediente.getExpediente() != null
                    && numeroExpediente.getExpediente().getEstatus() != null) {
                exDTO.setEstatus(new ValorDTO(
                        numeroExpediente.getExpediente().getEstatus().getValorId(),
                        numeroExpediente.getExpediente().getEstatus().getValor()));
            }

 		    // ------------------------------------------------------------------------------------------------
            expedientesDTO.add(exDTO);
        }

        return expedientesDTO;
    }

    /**
     * Metodo que consulta los expedientes de acuerdo al filtro
     */
    @Override
    public List<ExpedienteDTO> consultarExpedientesPorFiltroST(
            Date fechaInicio, Date fechaFin, UsuarioDTO usuario,
            List<Long> estatusExpediente, Long discriminanteId, Long rolId, Long idDistrito, Long idFuncionario) throws NSJPNegocioException {

        logger.info("/****CONSULTA LOS EXPEDIENTES PARA SISTEMA TRADICIONAL ****/");

        if (usuario == null || estatusExpediente == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        if (usuario.getFuncionario() == null
                || usuario.getFuncionario().getJerarquiaOrganizacional() == null
                || usuario.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        /*
         * Usado para obtener el discriminante Id
         */
        if (discriminanteId == 0 && usuario.getFuncionario().getDiscriminante() != null
                && usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null) {

            discriminanteId = usuario.getFuncionario().getDiscriminante()
                    .getCatDiscriminanteId();
        }

        List<NumeroExpediente> numerosExpedientes = numeroExpedienteDAO
                .consultarExpedientesPorFiltroST(fechaInicio, fechaFin, usuario
                        .getFuncionario().getJerarquiaOrganizacional()
                        .getJerarquiaOrganizacionalId(), estatusExpediente, discriminanteId, rolId, idDistrito, idFuncionario);

        List<ExpedienteDTO> expedientesDTO = new ArrayList<ExpedienteDTO>();

        for (NumeroExpediente numeroExpediente : numerosExpedientes) {
            ExpedienteDTO ex = new ExpedienteDTO();
            ex.setNumeroExpedienteId(numeroExpediente.getNumeroExpedienteId());
            ex.setInvolucradosSolicitados(true);
            ExpedienteDTO exDTO = this.obtenerExpediente(ex);
            expedientesDTO.add(exDTO);
        }

        return expedientesDTO;
    }

    @Override
    public List<ExpedienteDTO> consultarExpedientesPorIdCaso(CasoDTO caso)
            throws NSJPNegocioException {

        List<Expediente> fromBD = null;

        fromBD = this.expedienteDAO.consultarExpedientesPorIdCaso(caso);

        return ExpedienteTransformer.transformarExpedientesBasico(fromBD);

    }

    @Override
    public List<ExpedienteDTO> consultarNumeroDeExpedienteConHechoPorFiltros(
            EstatusExpediente estatusExpediente, UsuarioDTO usuarioDto,
            Date fechaInicio, Date fechaFin) throws NSJPNegocioException {

        if (logger.isDebugEnabled()) {
            logger.debug("/*BIENVENIDO AL SERVICIO PARA CONSULTAR EXPEDIENTES CON AVISO HECHO DELICTIVO POR FILTRO*/");
        }

        if (estatusExpediente == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        Long discriminante = null;

        if (usuarioDto != null
                && usuarioDto.getFuncionario() != null
                && usuarioDto.getFuncionario().getDiscriminante() != null
                && usuarioDto.getFuncionario().getDiscriminante()
                .getCatDiscriminanteId() != null) {

            discriminante = usuarioDto.getFuncionario().getDiscriminante()
                    .getCatDiscriminanteId();
        }

        List<NumeroExpediente> listaNumerosExpedientes = numeroExpedienteDAO
                .consultarNumeroDeExpedienteConHechoPorFiltros(
                        estatusExpediente, discriminante, fechaInicio, fechaFin);

        List<ExpedienteDTO> expRetorno = new ArrayList<ExpedienteDTO>();

        for (NumeroExpediente numeroExpediente : listaNumerosExpedientes) {

            ExpedienteDTO expDTO = ExpedienteTransformer
                    .transformarExpedienteBasico(numeroExpediente);

            HechoDTO hechoDto = new HechoDTO();

            hechoDto.setHechoId(numeroExpediente.getExpediente().getHecho()
                    .getHechoId());

            AvisoHechoDelictivoDTO avisoDto = AvisoHechoDelictivoTransformer
                    .transformarAvisoDtoBasico(numeroExpediente.getExpediente()
                            .getHecho().getAvisoHechoDelictivo());

            hechoDto.setAvisoHechoDelictivo(avisoDto);
            expDTO.setHechoDTO(hechoDto);
            expRetorno.add(expDTO);
        }
        return expRetorno;
    }

    @Override
    public List<ExpedienteDTO> consultarNumeroDeExpedienteSinHechoPorFiltros(
            EstatusExpediente estatusExpediente, UsuarioDTO usuarioDto,
            Date fechaInicio, Date fechaFin) throws NSJPNegocioException {

        if (logger.isDebugEnabled()) {
            logger.debug("/*BIENVENIDO AL SERVICIO PARA CONSULTAR EXPEDIENTES SIN AVISO HECHO DELICTIVO POR FILTRO*/");
        }

        if (estatusExpediente == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        Long discriminante = null;

        if (usuarioDto != null
                && usuarioDto.getFuncionario() != null
                && usuarioDto.getFuncionario().getDiscriminante() != null
                && usuarioDto.getFuncionario().getDiscriminante()
                .getCatDiscriminanteId() != null) {

            discriminante = usuarioDto.getFuncionario().getDiscriminante()
                    .getCatDiscriminanteId();
        }

        List<NumeroExpediente> listaNumerosExpedientes = numeroExpedienteDAO
                .consultarNumeroDeExpedienteSinHechoPorFiltros(
                        estatusExpediente, discriminante, fechaInicio, fechaFin);

        List<ExpedienteDTO> expRetorno = new ArrayList<ExpedienteDTO>();

        for (NumeroExpediente numeroExpediente : listaNumerosExpedientes) {

            ExpedienteDTO expDTO = ExpedienteTransformer
                    .transformarExpedienteBasico(numeroExpediente);

            HechoDTO hechoDto = new HechoDTO();

            hechoDto.setHechoId(numeroExpediente.getExpediente().getHecho()
                    .getHechoId());

            expDTO.setHechoDTO(hechoDto);
            expRetorno.add(expDTO);
        }
        return expRetorno;
    }

    /**
     * Permite consultar numeros de expedientes asociados a un identificador de
     * expediente (opcionalmente) que tengan como responsable a un usuario que
     * cuente con el rol asociado.
     */
    @Override
    public void actualizarEstatusNumerosDeExpedientesPorRolST(
            List<Long> roles, Long idExpediente, Long nuevoEstatusNumeroExpediente, Long nuevoEstatusExpediente) throws NSJPNegocioException {

        logger.info("/**** NUMEROS DE EXPEDIENTES A ACTUALIZAR PARA SISTEMA TRADICIONAL ****/");

        if (idExpediente == null || nuevoEstatusNumeroExpediente == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        List<NumeroExpediente> numerosExpedientes = numeroExpedienteDAO.consultarNumerosDeExpedientesPorRolST(roles, idExpediente);

        //Se actualiza el estatus de la lista de numeros de expedientes
        for (NumeroExpediente numeroExpediente : numerosExpedientes) {
            NumeroExpediente loNnumeroExpedienteBD = numeroExpedienteDAO.read(numeroExpediente.getNumeroExpedienteId());
            loNnumeroExpedienteBD.setEstatus(new Valor(nuevoEstatusNumeroExpediente));
            numeroExpedienteDAO.update(loNnumeroExpedienteBD);
            logger.info("Numero Expediente actualizado: " + numeroExpediente.getNumeroExpedienteId() + " al estatus: " + nuevoEstatusNumeroExpediente);
        }

        logger.info("Total de Numeros de expedientes actualizados: " + numerosExpedientes.size() + " al estatus: " + nuevoEstatusNumeroExpediente);

        //Se actualiza el estatus del expediente
        if (nuevoEstatusExpediente != null && nuevoEstatusExpediente > 0) {
            Expediente loExpedienteBD = expedienteDAO.read(idExpediente);
            loExpedienteBD.setEstatus(new Valor(nuevoEstatusExpediente));
            expedienteDAO.update(loExpedienteBD);
            logger.info("Expediente actualizado: " + idExpediente + " al estatus: " + nuevoEstatusExpediente);
        }

    }

    @Override
    public List<String> buscarNumerosExpedientesByIdExpediente(
            ExpedienteDTO idExpediente) throws NSJPNegocioException {
        Expediente expediente = expedienteDAO.read(idExpediente.getExpedienteId());
        List<String> numerosExpediente = new ArrayList<String>();
        for (NumeroExpediente numeros : expediente.getNumeroExpedientes()) {
            numerosExpediente.add(numeros.getNumeroExpediente());
        }
        return numerosExpediente;
    }

    @Override
    public List<ExpedienteDTO> consultarExpedienteCoorAT(
            FiltroExpedienteDTO filtroExpedienteDTO)
            throws NSJPNegocioException {
        if (filtroExpedienteDTO == null || filtroExpedienteDTO.getTipoBusquedaCoorAT() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        List<ExpedienteDTO> listExpedienteDTOs = new ArrayList<ExpedienteDTO>();

        try {
            TipoBusquedaCoordinadorAT tipo = TipoBusquedaCoordinadorAT.getByValor(filtroExpedienteDTO.getTipoBusquedaCoorAT());
            List<Expediente> list = new ArrayList<Expediente>();
            switch (tipo) {
                case EXPEDIENTES_ATP_DIA:
                    if (filtroExpedienteDTO.getFechaBusqueda() == null) {
                        filtroExpedienteDTO.setFechaBusqueda(new Date());
                    }
                    break;
                case EXPEDIENTES_ATP_TODOS:
                    break;
                case EXPEDIENTES_ATP_AGENCIA:
                    if (filtroExpedienteDTO.getIdAgencia() == null) {
                        throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
                    }
                    break;
                case EXPEDIENTES_ATP_USUARIO:
                    if (filtroExpedienteDTO.getIdFuncionario() == null) {
                        throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
                    }
                    break;
                case EXPEDIENTES_AT_DIA:
                    if (filtroExpedienteDTO.getFechaBusqueda() == null) {
                        filtroExpedienteDTO.setFechaBusqueda(new Date());
                    }
                    break;
                case EXPEDIENTES_AT_TODOS:
                    break;
                case EXPEDIENTES_AT_AGENCIA:
                    if (filtroExpedienteDTO.getIdAgencia() == null) {
                        throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
                    }
                    break;
                case EXPEDIENTES_AT_USUARIO:
                    if (filtroExpedienteDTO.getIdFuncionario() == null) {
                        throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
                    }
                    break;
                default:
                    throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
            }

            list = expedienteDAO.consultaExpedientesDoorAT(filtroExpedienteDTO);
            for (Expediente expediente : list) {
                ExpedienteDTO expedienteDTO = ExpedienteTransformer.transformaExpediente(expediente);

                List<Involucrado> involucrados = involucradoDAO
                        .obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
                                Calidades.DENUNCIANTE.getValorId(), null);

                for (Involucrado involucrado : involucrados) {
                    expedienteDTO.addInvolucradoDTO(InvolucradoTransformer
                            .transformarInvolucradoBasico(involucrado));
                }

                if (involucrados != null && involucrados.isEmpty()) {
                    involucrados = involucradoDAO
                            .obtenerInvByIdExpAndCalidad(expediente.getExpedienteId(),
                                    Calidades.DENUNCIANTE_ORGANIZACION.getValorId(), null);
                    for (Involucrado involucrado : involucrados) {
                        InvolucradoDTO involucradoDTO = InvolucradoTransformer
                                .transformarInvolucradoBasico(involucrado);
                        Organizacion organizacion = organizacionDAO.obtenerOrganizacionByRelacion(
                                involucrado.getElementoId(), new Long(Relaciones.ORGANIZACION_INVOLUCRADA.ordinal()));
                        involucradoDTO.setOrganizacionDTO(OrganizacionTransformer.transformarOrganizacionBasico(organizacion));
                        expedienteDTO.addInvolucradoDTO(involucradoDTO);
                    }
                }

                List<Delito> listDelitos = delitoDAO
                        .obtenerDelitoPorExpediente(expediente.getExpedienteId());

                for (Delito delito : listDelitos) {
                    if (delito.getEsPrincipal() == true) {
                        DelitoDTO delitoDTO = DelitoTransfromer
                                .transformarDelito(delito);
                        expedienteDTO.setDelitoPrincipal(delitoDTO);
                    }
                }

                listExpedienteDTOs.add(expedienteDTO);
            }

        } catch (Exception e) {
            logger.info("error en consultarExpedienteCoorAT:" + e);
            throw new NSJPNegocioException(CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO);
        }
        return listExpedienteDTOs;
    }

    /* (non-Javadoc)
     * @see mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService#consultarExpedientesRSPorNumeroExpediente(mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO)
     */
    @Override
    public List<ExpedienteDTO> consultarExpedientesRSPorNumeroExpediente(
            FiltroExpedienteDTO filtro) throws NSJPNegocioException {
        List<ExpedienteDTO> expedientesDto = null;
        if (filtro == null
                || filtro.getNumeroExpediente() == null
                || filtro.getNumeroExpediente().trim().isEmpty()
                || filtro.getUsuario() == null
                || filtro.getUsuario().getAreaActual() == null
                || filtro.getUsuario().getAreaActual().getAreaId() == null
                || filtro.getUsuario().getAreaActual().getAreaId() < 1L
                || filtro.getEstatusNumeroExpediente() == null
                || filtro.getEstatusNumeroExpediente().isEmpty()) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        List<Expediente> expedientesConsultados = expedienteDAO.consultarExpedientesRSPorNumeroExpediente(filtro);
        if (expedientesConsultados != null && !expedientesConsultados.isEmpty()) {
            expedientesDto = new ArrayList<ExpedienteDTO>();
            for (Expediente expediente : expedientesConsultados) {
                ExpedienteDTO eDto = ExpedienteTransformer
                        .transformarExpedienteBasico(expediente);
                eDto.setNumeroExpediente(expediente.getNumeroExpediente());
                expedientesDto.add(eDto);
            }
        }
        return expedientesDto;
    }

    public List<ExpedienteViewDTO> consultarExpedientesConSP(TipoDeBusquedaDeExpediente tipoBusqueda,
            HashMap<String, String> valores)
            throws NSJPNegocioException {
        logger.info("EJECUTANDO SERVICIO CONSULTAREXPEDIENTESCONSP");
        Long institucionActual = 0L;
        List<ExpedienteViewDTO> respuesta = new ArrayList<ExpedienteViewDTO>();

        if (tipoBusqueda == null || valores == null || valores.isEmpty()) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        List<Object[]> respuestaBD = expedienteDAO.consultarExpedientesConSP(tipoBusqueda, valores);

        //Verificamos cual es la institucion actual
        ConfInstitucion confInstitucion = confInstitucionDAO.consultarInsitucionActual();
        if (confInstitucion != null && confInstitucion.getConfInstitucionId() != null) {
            institucionActual = confInstitucion.getConfInstitucionId();
        }

        Instituciones institucion = Instituciones.getByValor(institucionActual);

		 //	Se valida la institucion que no sea nula y que sea solo PG o PJ  
        //	ya que solo debe entrar al switch para esos dos casos
        if (institucion == null
                || institucion.getValorId() == null
                || (!institucion.getValorId().equals(
                        Instituciones.PGJ.getValorId()) && !institucion
                .getValorId().equals(Instituciones.PJ.getValorId()))) {
            logger.error("ERROR LA INSTITUCION NO ES PJ, NI PG,");
            throw new NSJPNegocioException(
                    CodigoError.INFORMACION_PARAMETROS_ERRONEA);
        }

        if (!respuestaBD.isEmpty() && respuestaBD.size() > 0) {
            ExpedienteViewDTO loExpedienteViewDTO = null;
            Iterator<Object[]> iterador = respuestaBD.iterator();
            while (iterador.hasNext()) { //Se itera sobre los registros
                Object[] objecto = (Object[]) iterador.next();
                //Se maneja el switch ya que las instituciones pueden aumentar
                switch (institucion) {
                    case PGJ:
                        loExpedienteViewDTO = new ExpedienteViewDTO(
                                (objecto[CASO_ID] != null ? objecto[CASO_ID].toString() : "-"),
                                (objecto[CNUMEROGENERALCASO] != null ? objecto[CNUMEROGENERALCASO].toString() : "-"),
                                (objecto[EXPEDIENTE_ID] != null ? objecto[EXPEDIENTE_ID].toString() : "-"),
                                (objecto[NUMEROEXPEDIENTE_ID] != null ? objecto[NUMEROEXPEDIENTE_ID].toString() : "-"),
                                (objecto[CNUMEROEXPEDIENTE] != null ? objecto[CNUMEROEXPEDIENTE].toString() : "-"),
                                (objecto[CINVOLUCRADO] != null ? objecto[CINVOLUCRADO].toString() : "-"),
                                (objecto[CNOMBRECALIDAD] != null ? objecto[CNOMBRECALIDAD].toString() : "-"),
                                (objecto[CDELITO] != null ? objecto[CDELITO].toString() : "-"),
                                (objecto[BESPRINCIPAL] != null ? objecto[BESPRINCIPAL].toString() : "-"),
                                (objecto[ICLAVEFUNCIONARIO] != null ? objecto[ICLAVEFUNCIONARIO].toString() : "-"),
                                (objecto[CFUNCIONARIO] != null ? objecto[CFUNCIONARIO].toString() : "-"),
                                (objecto[CAREA] != null ? objecto[CAREA].toString() : "-"),
                                (objecto[CEDIFICIO] != null ? objecto[CEDIFICIO].toString() : "-"),
                                (objecto[CESTATUS] != null ? objecto[CESTATUS].toString() : "-"),
                                (objecto[BESCONSULTA] != null ? objecto[BESCONSULTA].toString() : "-"),
                                (objecto[NTOTALREGISTROS] != null ? objecto[NTOTALREGISTROS].toString() : "-"),
                                (objecto[FECHAAPERTURA] != null ? DateUtils.formatear((Date) objecto[FECHAAPERTURA]) : "-"),
                                (objecto[ID_AREA] != null ? objecto[ID_AREA].toString() : "-")
                        );
                        break;
                    case PJ:
                        loExpedienteViewDTO = new ExpedienteViewDTO(
                                (objecto[CASO_ID] != null ? objecto[CASO_ID].toString() : "-"),
                                (objecto[CNUMEROGENERALCASO] != null ? objecto[CNUMEROGENERALCASO].toString() : "-"),
                                (objecto[EXPEDIENTE_ID] != null ? objecto[EXPEDIENTE_ID].toString() : "-"),
                                (objecto[NUMEROEXPEDIENTE_ID] != null ? objecto[NUMEROEXPEDIENTE_ID].toString() : "-"),
                                (objecto[CNUMEROEXPEDIENTE] != null ? objecto[CNUMEROEXPEDIENTE].toString() : "-"),
                                (objecto[PJ_NOMBRE] != null ? objecto[PJ_NOMBRE].toString() : "-"),
                                (objecto[PJ_AP_PATERNO] != null ? objecto[PJ_AP_PATERNO].toString() : "-"),
                                (objecto[PJ_AP_MATERNO] != null ? objecto[PJ_AP_MATERNO].toString() : "-"),
                                (objecto[PJ_CNOMBRECALIDAD] != null ? objecto[PJ_CNOMBRECALIDAD].toString() : "-"),
                                (objecto[PJ_TRIBUNAL] != null ? objecto[PJ_TRIBUNAL].toString() : "-"),
                                (objecto[PJ_BESCONSULTA] != null ? objecto[PJ_BESCONSULTA].toString() : "-"),
                                (objecto[PJ_NTOTALREGISTROS] != null ? objecto[PJ_NTOTALREGISTROS].toString() : "-")
                        );
                        break;
                }
                respuesta.add(loExpedienteViewDTO);
            }
        }

        return respuesta;
    }

    @Override
    public ExpedienteDTO consultarExpedientePorNumeroDeCaso(CasoDTO casoDto,
            UsuarioDTO usuarioDto) throws NSJPNegocioException {

        Expediente expediente = expedienteDAO
                .consultarExpedientePorNumeroDeCaso(casoDto, usuarioDto);

        if (expediente == null || expediente.getExpedienteId() == null) {
            throw new NSJPNegocioException(CodigoError.SIN_CASO_ASOCIADO);
        }
        return ExpedienteTransformer.transformarExpedienteBasico(expediente);
    }

    public ExpedienteViewDTO consultarGeneralesDeHistorialDeExp(Long idNumeroExpediente) throws NSJPNegocioException {
        ExpedienteViewDTO respuesta = new ExpedienteViewDTO();

        if (idNumeroExpediente == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        ExpedienteDTO expDTO = new ExpedienteDTO();
        List<InvolucradoDTO> involucrados = null;
        InvolucradoDTO loDenunciante = null;

        expDTO.setNumeroExpedienteId(idNumeroExpediente);
        expDTO.setInvolucradosSolicitados(true);

        ExpedienteDTO loExpediente = this.obtenerExpediente(expDTO);

        if (loExpediente != null) {
            if (loExpediente.getNumeroExpedienteId() != null) {
                respuesta.setNumeroExpedienteId(loExpediente.getNumeroExpedienteId().toString());
            }
            if (loExpediente.getNumeroExpediente() != null) {
                respuesta.setNumeroExpediente(loExpediente.getNumeroExpediente());
            }

            //Numero de caso
            if (loExpediente.getCasoDTO() != null && loExpediente.getCasoDTO().getNumeroGeneralCaso() != null) {
                respuesta.setNumeroGeneralCaso(loExpediente.getCasoDTO().getNumeroGeneralCaso());
            }

            involucrados = loExpediente.getInvolucradoByCalidad(Calidades.DENUNCIANTE);
            if (involucrados != null && !involucrados.isEmpty()) {
                loDenunciante = involucrados.get(0);
            } else {
                involucrados = loExpediente.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA);
                if (involucrados != null && !involucrados.isEmpty()) {
                    loDenunciante = involucrados.get(0);
                }
            }

            respuesta.setDenunciate((loDenunciante != null && loDenunciante.getNombreCompleto() != null
                    ? loDenunciante.getNombreCompleto() : "-"));

            //Delito Principal
            respuesta.setDelito((loExpediente.getDelitoPrincipal() != null && loExpediente.getDelitoPrincipal().getCatDelitoDTO() != null
                    ? loExpediente.getDelitoPrincipal().getCatDelitoDTO().getNombre() : "-"));

            //Tipo de denuncia
            respuesta.setTipoDenuncia((loExpediente.getOrigen() != null && loExpediente.getOrigen().getValor() != null
                    ? loExpediente.getOrigen().getValor() : "-"));
        }

        return respuesta;
    }

    public List<ExpedienteDTO> consultarNumerosExpedientesPorIdExpediente(Long idExpediente, List<Long> idsJeraruqiasOrganizacionales)
            throws NSJPNegocioException {

        if (idExpediente == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        List<ExpedienteDTO> expedientesDto = new ArrayList<ExpedienteDTO>();

        List<NumeroExpediente> loNumerosDeExpedientes = numeroExpedienteDAO.consultarNumeroExpedientesPorIdExpediente(idExpediente, idsJeraruqiasOrganizacionales);
        if (loNumerosDeExpedientes != null && !loNumerosDeExpedientes.isEmpty()) {
            for (NumeroExpediente numeroExpediente : loNumerosDeExpedientes) {
                ExpedienteDTO loExpedienteDTO = ExpedienteTransformer.transformarExpedienteBasico(numeroExpediente);
                loExpedienteDTO.setFechaAperturaNumeroExp(numeroExpediente.getFechaApertura());
                expedientesDto.add(loExpedienteDTO);
            }
        }
        return expedientesDto;
    }

    public int obtenDetalleDeCanalizacionDeNumeroExpediente(Long idNumeroExpediente) throws NSJPNegocioException {
        return expedienteDAO.obtenDetalleDeCanalizacionDeNumeroExpediente(idNumeroExpediente);
    }

    @Override
    public List<BitacoraPermisoExpedienteDTO> obtenerPermisosDeExpediente(Long idExpediente) throws NSJPNegocioException {

        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA OBTENER LOS EXPEDIENTES CON PERMISO DEL FUNCIONARIO ****/");
        }
        if (idExpediente == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        List<BitacoraPermisoExpedienteDTO> bitacoraPermisoExpedienteDTOs = new ArrayList<BitacoraPermisoExpedienteDTO>();

        List<BitacoraPermisoExpediente> bitacoraPermisoExpedientes = bitacoraPermisoExpedienteDAO.obtenerPermisosDeNumeroExpediente(idExpediente);
        for (BitacoraPermisoExpediente bitacoraPermisoExpediente : bitacoraPermisoExpedientes) {
            bitacoraPermisoExpedienteDTOs.add(ExpedienteTransformer.transformarBitacoraPermisoExpediente(bitacoraPermisoExpediente));
        }
        return bitacoraPermisoExpedienteDTOs;
    }

    public List<BitacoraEstatusNumExpedienteDTO> consultarBitacoraEstatusNumExpedientePorIdExpediente(Long expedienteId) throws NSJPNegocioException {

        List<BitacoraEstatusNumExpedienteDTO> loBitacoraEstatusNumExpedienteDTOs = new ArrayList<BitacoraEstatusNumExpedienteDTO>();

        List<BitacoraEstatusNumExpediente> loBitacoraEstatusNumExpedientes = bitacoraEstatusNumExpedienteDAO.consultarBitacoraEstatusNumExpedientePorIdExpediente(expedienteId);

        if (loBitacoraEstatusNumExpedientes != null && !loBitacoraEstatusNumExpedientes.isEmpty()) {
            for (BitacoraEstatusNumExpediente bitacoraEstatusNumExpediente : loBitacoraEstatusNumExpedientes) {
                loBitacoraEstatusNumExpedienteDTOs.add(ExpedienteTransformer.transformarBitacoraEstatusNumExpediente(bitacoraEstatusNumExpediente));
            }
        }

        return loBitacoraEstatusNumExpedienteDTOs;
    }

    public ValorDTO consultaOrigenExpediente(Long idExpediente) throws NSJPNegocioException {

        if (idExpediente == null || idExpediente == 0) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        Valor loOrigen = null;
        Expediente loExpediente = expedienteDAO.read(idExpediente);

        if (loExpediente != null) {
            loOrigen = loExpediente.getOrigen();
        }
        return ValorTransformer.transformar(loOrigen);
    }

    /**
     * Metodo que intenta obtener una instancia del enum Roles, intetara
     * obtenerla en el siguiente orden: 1. En base al rol que se pasa como
     * parametro siempre y cuando sea diferente a ROL_NO_DEFINIDO 2. En base al
     * rol padre del rol que se pasa como parametro
     *
     * @param idRol
     * @return
     * @throws NSJPNegocioException
     */
    @SuppressWarnings("unused")
    protected Roles obtenEnumRol(long idRol) throws NSJPNegocioException {
        Roles rolEnum = Roles.getByValor(idRol);

        if (rolEnum == Roles.ROL_NO_DEFINIDO) {
            RolDTO rolPadre = rolService.consultarRolPadre(idRol);
            if (rolPadre != null && rolPadre.getRolId() > 0) {
                rolEnum = Roles.getByValor(rolPadre.getRolId().longValue());
            }
        }
        return rolEnum;
    }

    public List<ExpedienteDTO> buscadorDeExpedientesAReasignarPM(FiltroExpedienteDTO filtroExpedienteDTO)
            throws NSJPNegocioException {
        if (logger.isDebugEnabled()) {
            logger.debug("/**** SERVICIO PARA OBTENER LOS EXPEEDIENTES A REASIGNAR POR EL ROL DE COORD DE POL MINISTERIAL ****/");
        }

        if (filtroExpedienteDTO.getUsuario() == null
                || filtroExpedienteDTO.getUsuario().getFuncionario() == null
                || filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante() == null
                || filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante().getDistrito() == null
                || filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante().getDistrito().getCatDistritoId() == null
                || filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante().getDistrito().getCatDistritoId() <= 0) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        List<Long> expsRespuesta = new ArrayList<Long>();

        Boolean consultarSolicituedesDeInvestigacion = (filtroExpedienteDTO.getUsuario().getFuncionario().getClaveFuncionario() > 0
                ? Boolean.TRUE : Boolean.FALSE);

        expsRespuesta = expedienteDAO.buscadorDeExpedientesAReasignarPMQueryNativo(filtroExpedienteDTO, consultarSolicituedesDeInvestigacion);

        List<ExpedienteDTO> expsDTO = new ArrayList<ExpedienteDTO>();
        final PaginacionDTO pag = PaginacionThreadHolder.get();

        for (Long idNumeroExpediente : expsRespuesta) {
            NumeroExpediente numeroExpediente = numeroExpedienteDAO.read(idNumeroExpediente);

            ExpedienteDTO expedienteDTO = ExpedienteTransformer
                    .transformarExpedienteBasico(numeroExpediente);

            List<Delito> listDelitos = delitoDAO
                    .obtenerDelitoPorExpediente(numeroExpediente
                            .getExpediente().getExpedienteId());

            for (Delito delito : listDelitos) {
                if (delito.getEsPrincipal() == true) {
                    DelitoDTO delitoDTO = DelitoTransfromer
                            .transformarDelito(delito);
                    expedienteDTO.setDelitoPrincipal(delitoDTO);
                }
            }

            if (numeroExpediente != null && numeroExpediente.getExpediente() != null
                    && numeroExpediente.getExpediente().getEstatus() != null) {
                expedienteDTO.setEstatus(new ValorDTO(
                        numeroExpediente.getExpediente().getEstatus().getValorId(),
                        numeroExpediente.getExpediente().getEstatus().getValor()));
            }

            if (numeroExpediente != null
                    && numeroExpediente.getEstatus() != null
                    && numeroExpediente.getEstatus().getValor() != null) {
                expedienteDTO.setEstatusNumeroExpediente(new ValorDTO(numeroExpediente.getEstatus().getValorId(),
                        numeroExpediente.getEstatus().getValor()));
            }
            expsDTO.add(expedienteDTO);
        } // for
        PaginacionThreadHolder.set(pag);
        return new ArrayList<ExpedienteDTO>(expsDTO);
    }

    public List<ExpedienteViewDTO> consultaCiudadana(String apaterno, String amaterno, String nombre, String expediente)
            throws NSJPNegocioException {

        logger.info("EJECUTANDO SERVICIO DE CONSULTA CIUDADANA");
        if (expediente == null || nombre == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        return expedienteDAO.consultaCiudadana(apaterno, amaterno, nombre, expediente);
    }

}
