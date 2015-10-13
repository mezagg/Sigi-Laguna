/**
 * Nombre del Programa : ConsultarConfActividadDocumentoServiceImpl.java Autor :
 * Jacob Lobaco Compania : Ultrasist Proyecto : NSJP Fecha: 06-jul-2011 Marca de
 * cambio : N/A Descripcion General : N/A Programa Dependient :N/A Programa
 * Subsecuente :N/A Cond. de ejecucion :N/A Dias de ejecucion :N/A Horario: N/A
 * MODIFICACIONES
 * ------------------------------------------------------------------------------
 * Autor :N/A Compania :N/A Proyecto :N/A Fecha: N/A Modificacion :N/A
 * ------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.actividad.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.TipoJerarquia;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO;
import mx.gob.segob.nsjp.dao.actividad.ConfTipoActividadOrigenDestinoDAO;
import mx.gob.segob.nsjp.dao.expediente.ActividadDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.institucion.JerarquiaOrganizacionalDAO;
import mx.gob.segob.nsjp.dao.usuario.UsuarioDAO;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO;
import mx.gob.segob.nsjp.dto.actividad.ConfTipoActividadOrigenDestinoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.ConfTipoActividadOrigenDestino;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.actividad.ConsultarConfActividadDocumentoService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ActividadTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @version 1.0
 * @author Jacob Lobaco
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ConsultarConfActividadDocumentoServiceImpl implements
        ConsultarConfActividadDocumentoService {

    /**
     * Logger de la clase.
     */
    private final static Logger logger = Logger
            .getLogger(ConsultarConfActividadDocumentoServiceImpl.class);

    @Autowired
    private UsuarioDAO usuarioDao;

    @Autowired
    private ConfActividadDocumentoDAO confActividadDocumentoDao;

    @Autowired
    private NumeroExpedienteDAO NumeroExpedienteDAO;

    @Autowired
    private JerarquiaOrganizacionalDAO jerarquiaDAO;

    @Autowired
    private ActividadDAO actividadDao;

    @Autowired
    private ConfTipoActividadOrigenDestinoDAO confTipoActividadOrigenDestinoDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ConfActividadDocumentoDTO> consultarConfActividadDocumento(
            UsuarioDTO usuarioDto, ExpedienteDTO expedienteDto, Long idCategoriaActividad)
            throws NSJPNegocioException {
        if (usuarioDto == null || usuarioDto.getIdUsuario() == null || expedienteDto == null
                || (expedienteDto.getNumeroExpediente() == null && expedienteDto.getNumeroExpedienteId() == null)) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        Long jerarquiaOrgId = null;
        Long discriminante = null;
        Long catUIE = null;
        if (usuarioDto.getRolACtivo() != null
                && usuarioDto.getRolACtivo().getRol() != null
                && usuarioDto.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO() != null
                && usuarioDto.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId() != null) {
            jerarquiaOrgId = usuarioDto.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId();
        }
        Usuario usuario = UsuarioTransformer.transformarDTO(usuarioDto);
        List<ConfActividadDocumentoDTO> configuracionesDto = Collections.emptyList();
        if (usuario.getFuncionario() == null
                || usuario.getFuncionario().getArea() == null) {
            usuario = usuarioDao.read(usuarioDto.getIdUsuario());
        }
        NumeroExpediente numeroExpediente;
        if (expedienteDto.getNumeroExpedienteId() != null && expedienteDto.getNumeroExpedienteId() > 0) {
            numeroExpediente = NumeroExpedienteDAO.read(expedienteDto.getNumeroExpedienteId());
        } else {
            numeroExpediente = NumeroExpedienteDAO.obtenerNumeroExpediente(expedienteDto.getNumeroExpediente(), null);
        }

        if (numeroExpediente == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No ex posible encontrar el expediente con numero = "
                        + expedienteDto.getNumeroExpediente());
            }
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        Funcionario funcionario = usuario.getFuncionario();
        if (jerarquiaOrgId == null) {
            jerarquiaOrgId = funcionario.getArea().getJerarquiaOrganizacionalId();
        }
        discriminante = funcionario.getDiscriminante().getCatDiscriminanteId();
        if (discriminante != null) {
            catUIE = confActividadDocumentoDao.consultarCatUieIdFuncionario(discriminante);
            logger.info("EL CATUIE DEL USUARIO ES: " + catUIE);
        }
        List<ConfActividadDocumento> configuraciones = null;
        if (catUIE != null) {
            configuraciones = confActividadDocumentoDao.consultarConfActividadDocumentoCatUie(jerarquiaOrgId, numeroExpediente, idCategoriaActividad, catUIE);
        } else {
            configuraciones = confActividadDocumentoDao.consultarConfActividadDocumento(jerarquiaOrgId, numeroExpediente, idCategoriaActividad);
        }
        if (configuraciones != null & !configuraciones.isEmpty()) {
            configuracionesDto = new LinkedList<ConfActividadDocumentoDTO>();
            for (ConfActividadDocumento confActividadDocumento : configuraciones) {
                ConfActividadDocumentoDTO configuracionDto
                        = ConfActividadDocumentoTransformer.
                        transformarConfActividadDocumento(confActividadDocumento);
                configuracionesDto.add(configuracionDto);
            }
        }
        return configuracionesDto;
    }
    
    @Override
    public List<ConfActividadDocumentoDTO> consultarConfActividadDocumento(
            UsuarioDTO usuarioDto, ExpedienteDTO expedienteDto, Long idCategoriaActividad, Boolean sinCatUie)
            throws NSJPNegocioException {
        if (usuarioDto == null || usuarioDto.getIdUsuario() == null || expedienteDto == null
                || (expedienteDto.getNumeroExpediente() == null && expedienteDto.getNumeroExpedienteId() == null)) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        Long jerarquiaOrgId = null;
        Long discriminante = null;
        Long catUIE = null;
        if (usuarioDto.getRolACtivo() != null
                && usuarioDto.getRolACtivo().getRol() != null
                && usuarioDto.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO() != null
                && usuarioDto.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId() != null) {
            jerarquiaOrgId = usuarioDto.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId();
        }
        Usuario usuario = UsuarioTransformer.transformarDTO(usuarioDto);
        List<ConfActividadDocumentoDTO> configuracionesDto = Collections.emptyList();
        if (usuario.getFuncionario() == null
                || usuario.getFuncionario().getArea() == null) {
            usuario = usuarioDao.read(usuarioDto.getIdUsuario());
        }
        NumeroExpediente numeroExpediente;
        if (expedienteDto.getNumeroExpedienteId() != null && expedienteDto.getNumeroExpedienteId() > 0) {
            numeroExpediente = NumeroExpedienteDAO.read(expedienteDto.getNumeroExpedienteId());
        } else {
            numeroExpediente = NumeroExpedienteDAO.obtenerNumeroExpediente(expedienteDto.getNumeroExpediente(), null);
        }

        if (numeroExpediente == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No ex posible encontrar el expediente con numero = "
                        + expedienteDto.getNumeroExpediente());
            }
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        Funcionario funcionario = usuario.getFuncionario();
        if (jerarquiaOrgId == null) {
            jerarquiaOrgId = funcionario.getArea().getJerarquiaOrganizacionalId();
        }
        discriminante = funcionario.getDiscriminante().getCatDiscriminanteId();
        if (discriminante != null) {
            catUIE = confActividadDocumentoDao.consultarCatUieIdFuncionario(discriminante);
            logger.info("EL CATUIE DEL USUARIO ES: " + catUIE);
        }
        List<ConfActividadDocumento> configuraciones = null;
        if (catUIE != null && !sinCatUie) {
            configuraciones = confActividadDocumentoDao.consultarConfActividadDocumentoCatUie(jerarquiaOrgId, numeroExpediente, idCategoriaActividad, catUIE);
        } else {
            configuraciones = confActividadDocumentoDao.consultarConfActividadDocumento(jerarquiaOrgId, numeroExpediente, idCategoriaActividad);
        }
        if (configuraciones != null & !configuraciones.isEmpty()) {
            configuracionesDto = new LinkedList<ConfActividadDocumentoDTO>();
            for (ConfActividadDocumento confActividadDocumento : configuraciones) {
                ConfActividadDocumentoDTO configuracionDto
                        = ConfActividadDocumentoTransformer.
                        transformarConfActividadDocumento(confActividadDocumento);
                configuracionesDto.add(configuracionDto);
            }
        }
        return configuracionesDto;
    }

    @Override
    public List<ConfActividadDocumentoDTO> consultarConfActividadDocumentoFiltro(
            ConfActividadDocumentoDTO filtroConfActividadDocumentoDTO)
            throws NSJPNegocioException {
        List<ConfActividadDocumentoDTO> configuracionesDto = Collections.emptyList();

        logger.info(" Servicio de consultarConfActividadDocumentoFiltro con Filtro:"
                + filtroConfActividadDocumentoDTO);

        ConfActividadDocumento filtroConfActividadDocumento = ConfActividadDocumentoTransformer
                .transformarConfActividadDocumento(filtroConfActividadDocumentoDTO);

        List<ConfActividadDocumento> configuraciones = confActividadDocumentoDao
                .consultarConfActividadDocumentoFiltro(filtroConfActividadDocumento);

        if (!configuraciones.isEmpty()) {
            configuracionesDto = new LinkedList<ConfActividadDocumentoDTO>();
            for (ConfActividadDocumento confActividadDocumento : configuraciones) {
                ConfActividadDocumentoDTO configuracionDto = ConfActividadDocumentoTransformer
                        .transformarConfActividadDocumento(confActividadDocumento);
                configuracionesDto.add(configuracionDto);
            }
        }
        return configuracionesDto;
    }

    @Override
    public ConfActividadDocumentoDTO consultaConfActividadDocumentoPorId(Long idConfActividadDocumento) throws NSJPNegocioException {
        logger.info("Servicion consultaConfActividadDocumentoPorIdActividad");
        if (idConfActividadDocumento == null || idConfActividadDocumento < 0) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        ConfActividadDocumentoDTO confActividadDocumentoDTO = new ConfActividadDocumentoDTO();

        ConfActividadDocumento confActividadDocumento = confActividadDocumentoDao.read(idConfActividadDocumento);

        if (confActividadDocumento != null) {
            confActividadDocumentoDTO = ConfActividadDocumentoTransformer.transformarConfActividadDocumento(confActividadDocumento);
        }

        return confActividadDocumentoDTO;
    }
    
    @Override
    public List<ValorDTO> consultarEstadosPorJerarquiaOrganizacional(Long idJerarquiaOrganizacional) throws NSJPNegocioException {
        if (idJerarquiaOrganizacional == null || idJerarquiaOrganizacional < 0L) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        Set<ValorDTO> listaEstatus = new HashSet<ValorDTO>();

        List<Valor> listaValores = confActividadDocumentoDao.consultarEstadosDistintosPorJerarquiaOrganizacional(idJerarquiaOrganizacional);

        //Obtener el departamento del area
        logger.info("Obtener el area al que pertenece el departamento: " + idJerarquiaOrganizacional);
        JerarquiaOrganizacional departamento = jerarquiaDAO.read(idJerarquiaOrganizacional);

        //Verificar que sea un departamento para obtener el �rea 
        if (departamento.getTipoJerarquia().getValorId().equals(TipoJerarquia.DEPARTAMENTO.getValorId())) {
            JerarquiaOrganizacional area = departamento.getJerarquiaOrgResponsable();
            logger.info("Obtener el estatus para el area: " + area.getJerarquiaOrganizacionalId());
            List<Valor> listaValoresArea = confActividadDocumentoDao.consultarEstadosDistintosPorJerarquiaOrganizacional(area.getJerarquiaOrganizacionalId());

            listaValores.addAll(listaValoresArea);
        }

        for (Valor valor : listaValores) {
            listaEstatus.add(new ValorDTO(valor.getValorId(), valor.getValor()));
        }

        List<ValorDTO> listaOrdenada = new ArrayList<ValorDTO>(listaEstatus);
        Collections.sort(listaOrdenada);
        return listaOrdenada;
    }

    @Override
    public List<ActividadDTO> consultarActividadesPorTipoActividadExpedienteId(Long idExpediente, List<Long> idTipoActividades, Boolean documentoRec) throws NSJPNegocioException {

        logger.info(" consultarActividadesPorTipoActividadExpedienteId : ");
        logger.info(" idExpediente : " + idExpediente);
        logger.info(" idTipoActividades : " + idTipoActividades);
        List<Actividad> actividaes = actividadDao.consultarActividadesPorTipoActividadExpedienteId(idExpediente, idTipoActividades, documentoRec);

        List<ActividadDTO> actividadesDTO = new ArrayList<ActividadDTO>();
        for (Actividad actividad : actividaes) {
            actividadesDTO.add(ActividadTransformer.transformarActividad(actividad));
        }

        return actividadesDTO;
    }


    /* (non-Javadoc)
     * @see mx.gob.segob.nsjp.service.actividad.ConsultarConfActividadDocumentoService#consultarConfActividadDocumentoPorConfTipoActividadDestino(mx.gob.segob.nsjp.dto.actividad.ConfTipoActividadOrigenDestinoDTO)
     */
    @Override
    public List<ConfActividadDocumentoDTO> consultarConfActividadDocumentoPorConfTipoActividadDestino(
            ConfTipoActividadOrigenDestinoDTO filtro)
            throws NSJPNegocioException {
        List<ConfActividadDocumentoDTO> confActividadDocumentoDTO = null;
        try {
            if (filtro != null) {
                ConfTipoActividadOrigenDestino confTipoActividadOrigenDestino = new ConfTipoActividadOrigenDestino();
                Valor tipoActividadOrigen = ValorTransformer.transformar(filtro.getTipoActividadOrigenVal());
                Valor tipoActividadDestino = ValorTransformer.transformar(filtro.getTipoActividadDestinoVal());
                confTipoActividadOrigenDestino.setTipoActividadOrigenVal(tipoActividadOrigen);
                confTipoActividadOrigenDestino.setTipoActividadDestinoVal(tipoActividadDestino);
                if (tipoActividadDestino == null
                        || tipoActividadDestino.getValorId() == null
                        || tipoActividadDestino.getValorId() <= 0L) {
                    List<ConfTipoActividadOrigenDestino> actsDestino = confTipoActividadOrigenDestinoDAO.consultarConfTipoActividadOrigenDestino(confTipoActividadOrigenDestino);
                    if (actsDestino != null
                            && !actsDestino.isEmpty()) {
                        confActividadDocumentoDTO = new ArrayList<ConfActividadDocumentoDTO>();
                        for (ConfTipoActividadOrigenDestino ctaod : actsDestino) {
                            if (ctaod != null) {
                                tipoActividadDestino = ctaod.getTipoActividadDestinoVal();
                            }
                            if (tipoActividadDestino != null) {
                                ConfActividadDocumento confActividadDocumento = confActividadDocumentoDao.consultaConfActividadDocumentoPorIdActividad(tipoActividadDestino.getValorId());
                                confActividadDocumentoDTO.add(ConfActividadDocumentoTransformer.transformarConfActividadDocumento(confActividadDocumento));
                            }
                        }
                    }
                }
            }
            return confActividadDocumentoDTO;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA, e);
        }

    }


    /* (non-Javadoc)
     * @see mx.gob.segob.nsjp.service.actividad.ConsultarConfActividadDocumentoService#consultaConfActividadDocumentoPorIdActividad(mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO)
     */
    @Override
    public ConfActividadDocumentoDTO consultaConfActividadDocumentoPorIdActividad(
            ConfActividadDocumentoDTO filtro) throws NSJPNegocioException {
        if (filtro == null
                || filtro.getTipoActividadId() == null
                || filtro.getTipoActividadId() < 1L) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        ConfActividadDocumento conf = confActividadDocumentoDao.consultaConfActividadDocumentoPorIdActividad(filtro.getTipoActividadId());
        return ConfActividadDocumentoTransformer.transformarConfActividadDocumento(conf);
    }

    @Override
    public ConfActividadDocumentoDTO consultaConfActividadDocumentoPorId(Long idConfActividadDocumento, Boolean sinCauie) throws NSJPNegocioException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
