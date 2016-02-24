package mx.gob.segob.nsjp.web.solicitud.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoTurno;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.actividad.ActividadDelegate;
import mx.gob.segob.nsjp.delegate.avisodetencion.AvisoDetencionDelegate;
import mx.gob.segob.nsjp.delegate.detencion.CentroDetencionDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.expediente.TurnoDelegate;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.notificacion.NotificacionDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.detencion.CentroDetencionDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDetencionDTO;
import mx.gob.segob.nsjp.dto.domicilio.AsentamientoDTO;
import mx.gob.segob.nsjp.dto.domicilio.CiudadDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioExtranjeroDTO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.dto.domicilio.MunicipioDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.TurnoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.DepartamentoDTO;
import mx.gob.segob.nsjp.dto.involucrado.DefensaInvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.DetencionDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.CorreoElectronicoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.persona.TelefonoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.solicitud.form.solicitanteForm;
import mx.gob.segob.nsjp.web.solicitud.form.solicitudForm;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

public class SolicitudCiudadanaDefensorAction extends GenericAction {

    @Autowired
    private SolicitudDelegate solicitudDelegate;
    @Autowired
    private FuncionarioDelegate funcionarioDelegate;
    @Autowired
    private AvisoDetencionDelegate avisoDetencionDelegate;
    @Autowired
    private TurnoDelegate turnoDelegate;
    @Autowired
    private InvolucradoDelegate involucradoDelegate;
    @Autowired
    private ExpedienteDelegate expedienteDelegate;
    @Autowired
    private NotificacionDelegate notificacionDelegate;
    @Autowired
    private ActividadDelegate actividadDelegate;
    @Autowired
    private CentroDetencionDelegate centroDetencionDelegate;

    private static final Logger log = Logger
            .getLogger(SolicitudCiudadanaDefensorAction.class);

    //TODO MANEJADOR DE ERRORES EN ACTION O EN VISTA
    private static final String STR_ESTA_DETENIDO_AFIRMATIVO = "Si";
    private static final String STR_ESTA_DETENIDO_NEGATIVO = "No";
    private static final String ATENCION_COORDINADOR = "Esta solicitud est\u00e1 en proceso de atenci\u00f3n por parte de otro Coordinador";
    private static final String NO_EXISTE_SOLICITUD = "No se pudo obtener la informaci\u00f3n referente a esta solicitud";
    private static final String CONFIRMAR_SEGUIMIENTO = "confirmarSeguimiento";
    private static final String ABRIR_VISOR = "abrirVisor";
    private static final String NO_EXISTE_USUARIO = "No se puede obtener informaci\u00f3n del usuario, por favor. Cierre sesi\u00f3n e ingrese nuevamente";

    /**
     * Busqueda del siguiente turno a buscar de acuerdo al Tipo de Busqueda
     * pasado desde vista. Se considera el manejo de Distritos
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    //MOD defensorATE
    public ActionForward busquedaSiguienteTurnoDefensoria(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        log.info("ejecutando Action Atencion Temprana de defensoria en buscar siguiente turno");
        try {
            final TurnoDTO filtro = new TurnoDTO();
            //Filtro por Distrito
            UsuarioDTO usuarioDTO = super.getUsuarioFirmado(request);
            filtro.setUsuario(usuarioDTO);

            Long tipoBusqueda = NumberUtils.toLong(request.getParameter("tipoBusqueda"), 0);
            log.debug("Tipo busqueda:" + tipoBusqueda);

            if (tipoBusqueda.equals(new Long(TipoTurno.ASESORIA_LEGAL.ordinal()))) {
                filtro.setTipoTurno(TipoTurno.ASESORIA_LEGAL);
            } else {
                filtro.setTipoTurno(TipoTurno.SOLICITUD_CIUDADANA);
            }

            log.debug("Tipo busqueda:" + filtro.getTipoTurno());

            TurnoDTO turnDTO = turnoDelegate.obtenerTurnoParaAtencion(filtro);

            XStream converter=new XStream();
            converter.alias("turnoDTO", TurnoDTO.class);
            String xml = converter.toXML(turnDTO);
            log.debug("Response:: " + xml);

            escribir(response, xml, null);
        } catch (Exception e) {
            log.info("TurnoSiguienteError");
            log.info(e.getCause(), e);
            escribir(response, "TurnoSiguienteError", null);
        }
        return null;
    }

    /**
     * Registro de la solicitud de Asesoria Legal. Se crea el expediente, numero
     * de expediente y la Solicitud. No se considera la creacion de un caso, No
     * se considera la etapa del catalogo de etapas, se hace uso de las
     * definidas en el enum de EtapasExpediente.java
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    //MOD defensorATE
    public ActionForward registrarSolicitudAsesoriaLegal(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            log.debug(" ACTION registrarSolicitudAsesoriaLegal");
            UsuarioDTO usuarioDTO = super.getUsuarioFirmado(request);

            //GENERAR EL EXPEDIENTE Y NUMERO DE EXPEDIENTE
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setUsuario(usuarioDTO);
            expedienteDTO.setDiscriminante(usuarioDTO.getFuncionario().getDiscriminante());

            SolicitudDefensorDTO solicitudDefensorDTO = solicitudDelegate.registrarSolicitudAsesoriaLegal(expedienteDTO);

            String xml = null;
            converter.alias("solicitudDTO", SolicitudDTO.class);
            xml = converter.toXML(solicitudDefensorDTO);
            escribirRespuesta(response, xml);

        } catch (NSJPNegocioException e) {
            log.error(e.getMessage(), e);
            escribirError(response, e);
        }
        return null;
    }

    public ActionForward solicitudesNoAtendidasGrid(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("Solicitudes no Atendidas");

            List<SolicitudDefensorDTO> defensorDTOs = solicitudDelegate.obtenerSolicitudesDefensoriaPorEstatus(EstatusSolicitud.ABIERTA, Instituciones.DEF);

            String xml = null;
            PrintWriter pw = null;
            XStream converter=new XStream();
            converter.alias("defensorDTOs", java.util.List.class);
            converter.alias("solicitudDefensorDTO", SolicitudDefensorDTO.class);
            xml = converter.toXML(defensorDTOs);
            response.setContentType("text/xml");
            pw = response.getWriter();
            pw.print(xml);
            pw.flush();
            pw.close();
            log.info("lista de solicitudes no atendidas" + defensorDTOs);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Metodo utilizado para la consulta de los avisos de detencion por el
     * estatus de no atendidas
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return null
     * @throws IOException
     *
     */
    public ActionForward solicitudesNoAtendidas(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("Solicitudes no Atendidas");

            Long idDistrito = null;

            //Se obtiene el distrito del usuario firmado en session
            UsuarioDTO usuarioFirmado = getUsuarioFirmado(request);
            if (usuarioFirmado.getFuncionario() != null
                    && usuarioFirmado.getFuncionario().getDiscriminante() != null
                    && usuarioFirmado.getFuncionario().getDiscriminante().getDistrito() != null
                    && usuarioFirmado.getFuncionario().getDiscriminante().getDistrito().getCatDistritoId() != null) {
                log.info("El Distrito del usuario firmado en sesion es: " + usuarioFirmado.getFuncionario().getDiscriminante().getDistrito().getCatDistritoId());
                idDistrito = usuarioFirmado.getFuncionario().getDiscriminante().getDistrito().getCatDistritoId();

            }

            log.info("El discirminante obtenido es: " + idDistrito);

            List<AvisoDetencionDTO> avisoDetencionDTOs = avisoDetencionDelegate.obtenerAvisosDetencionPorEstatus(EstatusNotificacion.NO_ATENDIDA, idDistrito);
            log.info("lista de avisos de detencion no atendidos" + avisoDetencionDTOs);

            response.setContentType("text/xml; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter writer = response.getWriter();

            writer.print("<rows>");

            final PaginacionDTO pag = PaginacionThreadHolder.get();
            log.debug("pag :: " + pag);
            if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }

            for (AvisoDetencionDTO avisoDetencionDTO : avisoDetencionDTOs) {

                writer.print("<row id='" + avisoDetencionDTO.getDocumentoId() + "'>");
                writer.print("<cell>" + avisoDetencionDTO.getFolioNotificacion() + "</cell>");

                if (avisoDetencionDTO.getNumeroCasoAsociado() != null) {
                    writer.print("<cell>" + avisoDetencionDTO.getNumeroCasoAsociado() + "</cell>");
                } else {
                    writer.print("<cell>" + "-" + "</cell>");
                }

                writer.print("<cell>" + avisoDetencionDTO.getDetenido() + "</cell>");
                writer.print("<cell>");
                if (avisoDetencionDTO.getDelitos() != null && !avisoDetencionDTO.getDelitos().isEmpty()) {
                    String delitos = "";
                    for (DelitoDTO delito : avisoDetencionDTO.getDelitos()) {
                        if (delito.getEsPrincipal() != null) {
                            delitos += delito.getCatDelitoDTO().getNombre() + ", ";
                        }
                    }
                    if (!delitos.isEmpty()) {
                        delitos = delitos.substring(0, delitos.length() - 2);
                        writer.print(delitos);
                    } else {
                        writer.print(" - ");
                    }
                } else {
                    writer.print(" - ");
                }
                writer.print("</cell>");
                writer.print("<cell>"
                        + //Agencia que notifico sobre el aviso de detencion 
                        (avisoDetencionDTO.getClaveDiscriminanteOrigen() != null ? avisoDetencionDTO.getClaveDiscriminanteOrigen() : "-")
                        + "</cell>");
                writer.print("<cell>" + DateUtils.formatear(avisoDetencionDTO.getFechaDetencion()) + "-" + DateUtils.formatearHora(avisoDetencionDTO.getFechaDetencion()) + "</cell>");
                writer.print("<cell>" + DateUtils.formatear(avisoDetencionDTO.getFechaCreacion()) + "-" + DateUtils.formatearHora(avisoDetencionDTO.getFechaCreacion()) + "</cell>");

                writer.print("</row>");

            }

            writer.print("</rows>");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public ActionForward solicitudesNoAtendidasAtencionTemprana(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("solicitudesNoAtendidasAtencionTemprana");

            List<SolicitudDefensorDTO> defensorDTOs = solicitudDelegate.obtenerSolicitudesDefensoriaPorEstatus(EstatusSolicitud.ABIERTA, Instituciones.DEF);
            if (log.isDebugEnabled()) {
                log.debug("Lista de Sol AtencionTemprana" + defensorDTOs);
            }
            response.setContentType("text/xml; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter writer = response.getWriter();

            writer.print("<rows>");

            final PaginacionDTO pag = PaginacionThreadHolder.get();
            log.debug("pag :: " + pag);
            if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }

            for (SolicitudDefensorDTO solicitud : defensorDTOs) {
                writer.print("<row id='" + solicitud.getDocumentoId() + "-" + solicitud.getExpedienteDTO().getNumeroExpediente() + "'>");

                writer.print("<cell>");
                if (solicitud.getFolioSolicitud() != null) {
                    writer.print(solicitud.getFolioSolicitud());
                }
                writer.print("</cell>");

                writer.print("<cell>");
                if (solicitud.getNumeroCasoAsociado() != null) {
                    writer.print(solicitud.getNumeroCasoAsociado());
                } else {
                    writer.print("-");
                }
                writer.print("</cell>");

                writer.print("<cell>" + solicitud.getExpedienteDTO().getNumeroExpediente() + "</cell>");

                InvolucradoDTO involucradoDTOs = solicitud.getExpedienteDTO().getInputado();
                if (involucradoDTOs != null) {
                    writer.print("<cell>" + involucradoDTOs.getNombresDemograficoDTO().get(0).getNombreCompleto() + "</cell>");
                    writer.print("<cell>");
                    if (involucradoDTOs.getEsDetenido() != null && involucradoDTOs.getEsDetenido()) {
                        writer.print(STR_ESTA_DETENIDO_AFIRMATIVO);
                    } else {
                        writer.print(STR_ESTA_DETENIDO_NEGATIVO);
                    }
                    writer.print("</cell>");
                } else {
                    writer.print("<cell> - </cell>");
                    writer.print("<cell> - </cell>");
                }

                writer.print("<cell>" + solicitud.getStrFechaCreacion() + "-" + solicitud.getStrHoraCreacion() + "</cell>");
                //writer.print("<cell>"+ solicitud.getExpedienteDTO().getNumeroExpedienteId()+ "</cell>");
                writer.print("</row>");
            }

            writer.print("</rows>");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public ActionForward solicitudesNoEnviadasAtencionTemprana(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("solicitudesNoEnviadasAtencionTemprana");

            List<SolicitudDefensorDTO> defensorDTOs = solicitudDelegate.obtenerSolicitudesDefensoriaPorEstatus(null, Instituciones.DEF);
            if (log.isDebugEnabled()) {
                log.debug("Lista de Sol AtencionTemprana" + defensorDTOs);
            }
            response.setContentType("text/xml; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter writer = response.getWriter();

            writer.print("<rows>");

            final PaginacionDTO pag = PaginacionThreadHolder.get();
            log.debug("pag :: " + pag);
            if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }

            for (SolicitudDefensorDTO solicitud : defensorDTOs) {
                writer.print("<row id='" + solicitud.getDocumentoId() + "'>");

                writer.print("<cell>");
                if (solicitud.getNumeroCasoAsociado() != null) {
                    writer.print(solicitud.getNumeroCasoAsociado());
                } else {
                    writer.print("-");
                }
                writer.print("</cell>");

                writer.print("<cell>" + solicitud.getExpedienteDTO().getNumeroExpediente() + "</cell>");

                InvolucradoDTO involucradoDTOs = solicitud.getExpedienteDTO().getInputado();
                if (involucradoDTOs != null) {
                    writer.print("<cell>" + involucradoDTOs.getNombresDemograficoDTO().get(0).getNombreCompleto() + "</cell>");
                } else {
                    writer.print("<cell> - </cell>");
                }
                if (solicitud.getExpedienteDTO().getEtapa() != null) {
                    writer.print("<cell>" + solicitud.getExpedienteDTO().getEtapa().getValor() + "</cell>");
                } else {
                    writer.print("<cell> - </cell>");
                }
                writer.print("</row>");
            }

            writer.print("</rows>");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public ActionForward solicitudesNoAtendidasPoderJudicial(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        try {

            log.info("solicitudesNoAtendidasPoderJudicial");

            List<SolicitudDefensorDTO> defensorDTOs = solicitudDelegate.obtenerSolicitudesDefensoriaPorEstatus(EstatusSolicitud.ABIERTA, Instituciones.PJ);
            if (log.isDebugEnabled()) {
                log.debug("Lista de Sol Poder Judicial" + defensorDTOs);
            }
            response.setContentType("text/xml; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter writer = response.getWriter();

            writer.print("<rows>");

            final PaginacionDTO pag = PaginacionThreadHolder.get();
            log.debug("pag :: " + pag);
            if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");

            }

            for (SolicitudDefensorDTO solicitud : defensorDTOs) {

                writer.print("<row id='" + solicitud.getDocumentoId() + "'>");
                log.info("solicitud.getDocumentoId() -- " + solicitud.getDocumentoId());
//				writer.print("<row id='"+ solicitud.getDocumentoId() + "-" +solicitud.getExpedienteDTO().getNumeroExpediente()+ "'>");

                writer.print("<cell>");
                if (solicitud.getFolioSolicitud() != null) {
                    writer.print(solicitud.getFolioSolicitud());
                }
                writer.print("</cell>");

                writer.print("<cell>");
                if (solicitud.getNumeroCasoAsociado() != null) {
                    writer.print(solicitud.getNumeroCasoAsociado());
                } else {
                    writer.print("-");
                }
                writer.print("</cell>");

                writer.print("<cell>" + solicitud.getDetenido() + "</cell>");

                writer.print("<cell>");
                String cadenaDelitos = obtenerCadenaDelitos(solicitud.getDelitos());
                if (cadenaDelitos != null) {
                    writer.print(cadenaDelitos);
                } else {
                    writer.print("-");
                }
                writer.print("</cell>");

                if (solicitud.getAudiencia() != null) {
                    writer.print("<cell>" + solicitud.getAudiencia().getTipoAudiencia().getValor() + "</cell>");
                    writer.print("<cell>" + DateUtils.formatear(solicitud.getAudiencia().getFechaEvento()) + " - " + DateUtils.formatearHora(solicitud.getAudiencia().getFechaEvento()) + "</cell>");
                } else {
                    writer.print("<cell>-</cell>");
                    writer.print("<cell>-</cell>");
                }

                writer.print("<cell>");
                if (solicitud.isEsDetenido()) {
                    writer.print(STR_ESTA_DETENIDO_AFIRMATIVO);
                } else {
                    writer.print(STR_ESTA_DETENIDO_NEGATIVO);
                }
                writer.print("</cell>");
                writer.print("<cell>" + DateUtils.formatear(solicitud.getFechaCreacion()) + " - " + DateUtils.formatearHora(solicitud.getFechaCreacion()) + "</cell>");

                writer.print("</row>");
            }

            writer.print("</rows>");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public ActionForward solicitudesNoAtendidasAsesoria(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("solicitudesNoAtendidasAsesoria");

            List<SolicitudDefensorDTO> defensorDTOs = solicitudDelegate.obtenerSolicitudesAsesoriaDefensoriaPorEstatus(EstatusSolicitud.ABIERTA, Instituciones.DEF);
            if (log.isDebugEnabled()) {
                log.debug("Lista de Sol Asesoria" + defensorDTOs);
            }
            response.setContentType("text/xml; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter writer = response.getWriter();

            writer.print("<rows>");

            final PaginacionDTO pag = PaginacionThreadHolder.get();
            log.debug("pag :: " + pag);
            if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }

            for (SolicitudDefensorDTO solicitud : defensorDTOs) {

                writer.print("<row id='" + solicitud.getDocumentoId() + "-" + solicitud.getExpedienteDTO().getNumeroExpediente() + "'>");

                writer.print("<cell>");
                if (solicitud.getFolioSolicitud() != null) {
                    writer.print(solicitud.getFolioSolicitud());
                }
                writer.print("</cell>");
                writer.print("<cell>" + solicitud.getExpedienteDTO().getNumeroExpediente() + "</cell>");

                InvolucradoDTO involucradoDTOs = solicitud.getExpedienteDTO().getInputado();
                if (involucradoDTOs != null) {
                    writer.print("<cell>" + involucradoDTOs.getNombresDemograficoDTO().get(0).getNombreCompleto() + "</cell>");
                } else {
                    writer.print("<cell> - </cell>");
                }

                writer.print("<cell>" + solicitud.getStrFechaCreacion() + "-" + solicitud.getStrHoraCreacion() + "</cell>");
                writer.print("<cell>" + solicitud.getExpedienteDTO().getNumeroExpedienteId() + "</cell>");
                writer.print("</row>");
            }

            writer.print("</rows>");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public ActionForward solicitudesNoEnviadasAsesoria(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("solicitudesNoAtendidasAsesoria");

            List<SolicitudDefensorDTO> defensorDTOs = solicitudDelegate.obtenerSolicitudesAsesoriaDefensoriaPorEstatus(null, Instituciones.DEF);
            if (log.isDebugEnabled()) {
                log.debug("Lista de Sol Asesoria" + defensorDTOs);
            }
            response.setContentType("text/xml; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter writer = response.getWriter();

            writer.print("<rows>");

            final PaginacionDTO pag = PaginacionThreadHolder.get();
            log.debug("pag :: " + pag);
            if (pag != null && pag.getTotalRegistros() != null) {
                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
            } else {
                writer.print("<total>0</total>");
                writer.print("<records>0</records>");
            }

            for (SolicitudDefensorDTO solicitud : defensorDTOs) {

                writer.print("<row id='" + solicitud.getDocumentoId() + "'>");

                writer.print("<cell>");
                if (solicitud.getNumeroCasoAsociado() != null) {
                    writer.print(solicitud.getNumeroCasoAsociado());
                } else {
                    writer.print("-");
                }
                writer.print("</cell>");

                writer.print("<cell>" + solicitud.getExpedienteDTO().getNumeroExpediente() + "</cell>");

                InvolucradoDTO involucradoDTOs = solicitud.getExpedienteDTO().getInputado();
                if (involucradoDTOs != null) {
                    writer.print("<cell>" + involucradoDTOs.getNombresDemograficoDTO().get(0).getNombreCompleto() + "</cell>");
                } else {
                    writer.print("<cell> - </cell>");
                }
                if (solicitud.getExpedienteDTO().getEtapa() != null) {
                    writer.print("<cell>" + solicitud.getExpedienteDTO().getEtapa().getValor() + "</cell>");
                } else {
                    writer.print("<cell> - </cell>");
                }
                writer.print("</row>");
            }

            writer.print("</rows>");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    //@Deprecated Revisar en vista
    public ActionForward generaNumeroAcuse(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            String tipo = request.getParameter("tipo");
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setUsuario(super.getUsuarioFirmado(request));
            Long idNumeroExpediente = NumberUtils.toLong(request.getParameter("idExpediente"), -1);
            if (idNumeroExpediente.longValue() > 0) {
                expedienteDTO.setNumeroExpedienteId(idNumeroExpediente);
                expedienteDTO = expedienteDelegate.obtenerExpediente(expedienteDTO);
            }

            Actividades actividad = null;
            log.debug("tipo :: " + tipo);
//			if(tipo.equalsIgnoreCase("asesoria")){
//			expedienteDTO.setEtapa(new ValorDTO(EtapasExpediente.ASESORIA
//					.getValorId()));
            actividad = Actividades.SOLICITAR_ASESORIA_LEGAL;
			//			}
//			else{
//				actividad = Actividades.GENERAR_ACUSE_ATENCION_CIUDADADA_DEFENSORIA;
//			}

            SolicitudDefensorDTO resp = solicitudDelegate.generarAcuseAtencion(expedienteDTO);

            //FIXME Utilizar ExpedienteSesionDTO
            super.setExpedienteTrabajo(request, resp.getExpedienteDTO());

            actividadDelegate.registrarActividad(resp.getExpedienteDTO(), getUsuarioFirmado(request).getFuncionario(), actividad.getValorId());
            log.info("entro a asesoria para setear la actividad");

            String xml = null;
            PrintWriter pw = null;
            converter.alias("solicitudDefensorDTO", SolicitudDefensorDTO.class);
            xml = converter.toXML(resp);
            response.setContentType("text/xml");
            pw = response.getWriter();
            pw.print(xml);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public ActionForward guardaSolicitud(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            log.info("guardando Solicitud");
            solicitudForm forma = (solicitudForm) form;

            String numExpediente = request.getParameter("numExpediente");
            log.info("numExpediente====" + numExpediente);

            String idExpediente = request.getParameter("idExpediente");
            log.info("idExpediente====" + idExpediente);
            String numSol = request.getParameter("documentoId");
            log.info("numSol====" + numSol);

            /**
             * PARSELONG
             */
			// log.info("Idioma" + valorIdiomaDTO);
            // "ATENCION_TEMPRANA_DEFENSORIA"
            log.info("Idioma 1====" + forma.getIdioma());
            log.info("Idioma 2====" + Long.parseLong(forma.getIdioma()));

            InvolucradoDTO involucradoDTO = new InvolucradoDTO();
            NombreDemograficoDTO nombresDemograficoDTO = new NombreDemograficoDTO();

            SolicitudDefensorDTO solicitudDefensorDTO = new SolicitudDefensorDTO();
            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            solicitudDefensorDTO.setExpedienteDTO(expedienteDTO);
            involucradoDTO.setIdSolicitudDefensor(NumberUtils.toLong(numSol, 0));
            AreaDTO areaDTO = new AreaDTO(Areas.ATENCION_TEMPRANA_DEFENSORIA);
            expedienteDTO.setArea(areaDTO);
            expedienteDTO.setNumeroExpediente(numExpediente);
            expedienteDTO.setExpedienteId(Long.parseLong(idExpediente));
            involucradoDTO.setExpedienteDTO(expedienteDTO);

            nombresDemograficoDTO.setNombre(forma.getNombre());
            nombresDemograficoDTO
                    .setApellidoPaterno(forma.getApellidoPaterno());
            nombresDemograficoDTO
                    .setApellidoMaterno(forma.getApellidoMaterno());
            nombresDemograficoDTO.setCurp(forma.getCurp());
            nombresDemograficoDTO.setRfc(forma.getRfc());
            nombresDemograficoDTO.setSexo(forma.getSexo());

            ValorDTO valorTipoDTO = new ValorDTO();
            valorTipoDTO.setValor("Defensoria");

            involucradoDTO.setTipoPersona(new Long(1));

            involucradoDTO.addNombreDemografico(nombresDemograficoDTO);
            log.info("nombres de la forma" + nombresDemograficoDTO.getNombre());
            log.info("apPat::" + nombresDemograficoDTO.getApellidoPaterno());
            log.info("napMat::" + nombresDemograficoDTO.getApellidoMaterno());

            /**
             * CATALOGOS AL ACTION
             */
            ValorDTO valorIdiomaDTO = new ValorDTO();
            ValorDTO valorEscolaridadDTO = new ValorDTO();
            ValorDTO valorEstadoCivilDTO = new ValorDTO();
            ValorDTO valorOcupacionDTO = new ValorDTO();
            ValorDTO valorNacionalidadDTO = new ValorDTO();
            List<ValorDTO> listaOcupacion = new ArrayList<ValorDTO>();
            List<ValorDTO> listaNacionalidad = new ArrayList<ValorDTO>();

            if (forma.getIdioma() != null && !forma.getIdioma().equals("-1")) {
                valorIdiomaDTO.setIdCampo(NumberUtils.toLong(forma.getIdioma()));
                involucradoDTO.setValorIdIdioma(valorIdiomaDTO);
            }

            if (forma.getEscolaridad() != null && !forma.getEscolaridad().equals("-1")) {
                valorEscolaridadDTO.setIdCampo(NumberUtils.toLong(forma.getEscolaridad()));
                involucradoDTO.setValorIdEscolaridad(valorEscolaridadDTO);
            }

            if (forma.getEstadoCivil() != null && !forma.getEstadoCivil().equals("-1")) {
                valorEstadoCivilDTO.setIdCampo(NumberUtils.toLong(forma.getEstadoCivil()));
                involucradoDTO.setValorIdEstadoCivil(valorEstadoCivilDTO);
            }

            if (forma.getOcupacion() != null && !forma.getOcupacion().equals("-1")) {
                valorOcupacionDTO.setIdCampo(NumberUtils.toLong(forma.getOcupacion()));
                listaOcupacion.add(valorOcupacionDTO);
                involucradoDTO.setValorIdOcupacion(listaOcupacion);
            }

            if (forma.getNacionalidad() != null && !forma.getNacionalidad().equals("-1")) {
                valorNacionalidadDTO.setIdCampo(NumberUtils.toLong(forma.getNacionalidad()));
                listaNacionalidad.add(valorNacionalidadDTO);
                involucradoDTO.setValorIdNacionalidad(listaNacionalidad);
            }

            involucradoDTO = solicitudDelegate.guardarSolicitanteSolicitudDefensoria(involucradoDTO);

            String xml = null;
            PrintWriter pw = null;
            converter.alias("involucradoDTO", InvolucradoDTO.class);
            xml = converter.toXML(involucradoDTO);
            response.setContentType("text/xml");
            pw = response.getWriter();
            pw.print(xml);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    //@Deprecated //BORRAR NO SE OCUPA EN NINGUN LADO
    public ActionForward guardaSolicitante(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            log.info("guardando Solicitante");

            solicitanteForm formaSolicitante = (solicitanteForm) form;

            String idIndividuo = request.getParameter("idIndividuo");
            log.info("&&&&&&&&&&&&&Elemnto id:" + idIndividuo);
            String numExpediente = request.getParameter("numExpediente");
            log.info("numExpediente====" + numExpediente);
            String idExpediente = request.getParameter("idExpediente");
            log.info("idExpediente====" + idExpediente);
            String idNumeroExpediente = request.getParameter("idNumeroExpediente");
            log.info("idExpediente====" + idNumeroExpediente);
            String lugarDelDetenido = request.getParameter("lugarDelDetenido");
            log.info("lugarDelDetenido====" + lugarDelDetenido);
            String numSol = request.getParameter("documentoId");
            log.info("numSol====" + numSol);
            String numDelitos = request.getParameter("numDelitos");
            log.info("numDelitos====" + numDelitos);
            String detenido = request.getParameter("detenido");
            log.info("Detenido" + detenido);
            String fechaIngreso = request.getParameter("fechaIngreso");
            log.info("fechaIngreso" + fechaIngreso);
            String horaIngreso = request.getParameter("horaIngreso");
            log.info("horaIngreso" + horaIngreso);

            //domicilio
            String pais = request.getParameter("pais");
            log.info("PAIS=" + pais);
            String codigoPostal = request.getParameter("codigoPostal");
            log.info("CP=" + codigoPostal);
            String entidadFederativa = request.getParameter("entidadFederativa");
            log.info("ENTIDAD FEDERATIVA=" + entidadFederativa);
            String ciudad = request.getParameter("ciudad");
            log.info("CIUDAD=" + ciudad);
            String delegacionMunicipio = request.getParameter("delegacionMunicipio");
            log.info("DELEGACION-MUNICIPIO=" + delegacionMunicipio);
            String asentamientoColonia = request.getParameter("asentamientoColonia");
            log.info("ASENTAMIENTO COLONIA=" + asentamientoColonia);
            String tipoAsentamiento = request.getParameter("tipoAsentamiento");
            log.info("TIPO ASENTAMIENTO=" + tipoAsentamiento);
            String tipoCalle = request.getParameter("tipoCalle");
            log.info("TIPO CALLE=" + tipoCalle);
            String calle = request.getParameter("calle");
            log.info("CALLE=" + calle);
            String numExterior = request.getParameter("numExterior");
            log.info("NUMERO EXTERIOR=" + numExterior);
            String numInterior = request.getParameter("numInterior");
            log.info("NUMERO INTERIOR=" + numInterior);
            String referencias = request.getParameter("referencias");
            log.info("REFERENCIAS=" + referencias);
            String entreCalle = request.getParameter("entreCalle");
            log.info("ENTRE CALLE=" + entreCalle);
            String ycalle = request.getParameter("ycalle");
            log.info("Y CALLE=" + ycalle);
            String aliasDomicilio = request.getParameter("aliasDomicilio");
            log.info("ALIAS DOMICILIO=" + aliasDomicilio);
            String edificio = request.getParameter("edificio");
            log.info("EDIFICIO=" + edificio);
            String longitud = request.getParameter("longitud");
            log.info("LONGITUD=" + longitud);
            String latitud = request.getParameter("latitud");
            log.info("LATITUD=" + latitud);

            //medios de contacto
            String medioContactoTelefono = request.getParameter("medioContactoTelefono");
            log.info("horaIngreso" + medioContactoTelefono);

            String medioContactoCorreo = request.getParameter("medioContactoCorreo");
            log.info("horaIngreso" + medioContactoCorreo);

            InvolucradoDTO involucradoDTO = new InvolucradoDTO();
            involucradoDTO.setElementoId(NumberUtils.toLong(idIndividuo));

            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            List<DelitoDTO> delitoDTOs = new ArrayList<DelitoDTO>();
            List<DetencionDTO> detencionDTOLista = new ArrayList<DetencionDTO>();
            expedienteDTO.setExpedienteId(NumberUtils.toLong(idExpediente, 0));
            expedienteDTO.setNumeroExpedienteId(NumberUtils.toLong(idNumeroExpediente, 0));
            AreaDTO areaDTO = new AreaDTO(Areas.ATENCION_TEMPRANA_DEFENSORIA);
            expedienteDTO.setArea(areaDTO);
            expedienteDTO.setNumeroExpediente(numExpediente);
            involucradoDTO.setExpedienteDTO(expedienteDTO);
            involucradoDTO.setIdSolicitudDefensor(NumberUtils.toLong(numSol));

            DetencionDTO detencionDTO = new DetencionDTO();

            //Falta Pasar Nombre delito
            if (numDelitos != null) {
                String[] idCatDelitos = numDelitos.split(",");
                for (String idDelito : idCatDelitos) {
                    DelitoDTO delitoDTO = new DelitoDTO();
                    delitoDTO.setDelitoId(NumberUtils.toLong(idDelito));
                    delitoDTO.setEsProbable(false);
                    delitoDTO.setEsPrincipal(false);
                    delitoDTOs.add(delitoDTO);
                }
            }

            involucradoDTO.setDetenciones(detencionDTOLista);
            detencionDTO.setLugarDetencionProvicional(lugarDelDetenido);

            CalidadDTO calidadDTO = new CalidadDTO();
            calidadDTO.setCalidades(Calidades.PROBABLE_RESPONSABLE_PERSONA);
            involucradoDTO.setCalidadDTO(calidadDTO);
            involucradoDTO.setTipoPersona(new Long(1));

            detencionDTO.setFechaFinDetencion(fechaIngreso);
            detencionDTO.setHoraFinDetencion(horaIngreso);
            detencionDTOLista.add(detencionDTO);

            DelitoDTO pDelitoDTO = new DelitoDTO();
            expedienteDTO.setDelitoPrincipal(pDelitoDTO);
            if (detenido.equals("true") || detenido == "true") {
                log.info("entra a es detenido");
                involucradoDTO.setEsDetenido(true);
                involucradoDTO.setDelitosCometidos(delitoDTOs);
                log.info("numDelitos cometidos====" + delitoDTOs);
            }

            NombreDemograficoDTO nombresDemograficoDTO = new NombreDemograficoDTO();

            nombresDemograficoDTO.setNombre(formaSolicitante.getNombre());
            nombresDemograficoDTO.setApellidoPaterno(formaSolicitante.getApellidoPaterno());
            nombresDemograficoDTO.setApellidoMaterno(formaSolicitante.getApellidoMaterno());
            nombresDemograficoDTO.setCurp(formaSolicitante.getCurp());
            nombresDemograficoDTO.setRfc(formaSolicitante.getRfc());
            nombresDemograficoDTO.setSexo(formaSolicitante.getSexo());

            List<NombreDemograficoDTO> listaNombre = new ArrayList<NombreDemograficoDTO>();
            listaNombre.add(nombresDemograficoDTO);

            involucradoDTO.setNombresDemograficoDTO(listaNombre);

            ValorDTO valorIdiomaDTO = new ValorDTO();
            ValorDTO valorEscolaridadDTO = new ValorDTO();
            ValorDTO valorEstadoCivilDTO = new ValorDTO();
            ValorDTO valorOcupacionDTO = new ValorDTO();
            ValorDTO valorNacionalidadDTO = new ValorDTO();
            List<ValorDTO> listaOcupacion = new ArrayList<ValorDTO>();
            List<ValorDTO> listaNacionalidad = new ArrayList<ValorDTO>();

            if (formaSolicitante.getIdioma() != null && !formaSolicitante.getIdioma().equals("-1")) {
                valorIdiomaDTO.setIdCampo(NumberUtils.toLong(formaSolicitante.getIdioma()));
                involucradoDTO.setValorIdIdioma(valorIdiomaDTO);
            }

            if (formaSolicitante.getEscolaridad() != null && !formaSolicitante.getEscolaridad().equals("-1")) {
                valorEscolaridadDTO.setIdCampo(NumberUtils.toLong(formaSolicitante.getEscolaridad()));
                involucradoDTO.setValorIdEscolaridad(valorEscolaridadDTO);
            }

            if (formaSolicitante.getEstadoCivil() != null && !formaSolicitante.getEstadoCivil().equals("-1")) {
                valorEstadoCivilDTO.setIdCampo(NumberUtils.toLong(formaSolicitante.getEstadoCivil()));
                involucradoDTO.setValorIdEstadoCivil(valorEstadoCivilDTO);
            }

            if (formaSolicitante.getOcupacion() != null && !formaSolicitante.getOcupacion().equals("-1")) {
                valorOcupacionDTO.setIdCampo(NumberUtils.toLong(formaSolicitante.getOcupacion()));
                listaOcupacion.add(valorOcupacionDTO);
                involucradoDTO.setValorIdOcupacion(listaOcupacion);
            }

            if (formaSolicitante.getNacionalidad() != null && !formaSolicitante.getNacionalidad().equals("-1")) {
                valorNacionalidadDTO.setIdCampo(NumberUtils.toLong(formaSolicitante.getNacionalidad()));
                listaNacionalidad.add(valorNacionalidadDTO);
                involucradoDTO.setValorIdNacionalidad(listaNacionalidad);
            }

            DomicilioDTO domicilioDTO = new DomicilioDTO();

            //CUANDO EL PAIS ES MEXICO
            if ((NumberUtils.toLong(pais) == 10 || pais.equalsIgnoreCase("mexico") || pais.equalsIgnoreCase("m�xico")) && (NumberUtils.toLong(pais) != -1L)) {

			//parte izquierda de la pantalla ingresar domicilio				
                //entidad federativa
                if (!entidadFederativa.equalsIgnoreCase("")) {

                    if (NumberUtils.toLong(entidadFederativa) != -1L) {
                        EntidadFederativaDTO entidadDTO = new EntidadFederativaDTO();
                        entidadDTO.setEntidadFederativaId(NumberUtils.toLong(entidadFederativa));
                        domicilioDTO.setEntidadDTO(entidadDTO);
                    }
                }

                //ciudad
                if (!ciudad.equalsIgnoreCase("")) {

                    if (NumberUtils.toLong(ciudad) != -1L) {
                        CiudadDTO ciudadDTO = new CiudadDTO();
                        ciudadDTO.setCiudadId(NumberUtils.toLong(ciudad));
                        domicilioDTO.setCiudadDTO(ciudadDTO);
                    }
                }
                //delegacion-municipio
                if (!delegacionMunicipio.equalsIgnoreCase("")) {

                    if (NumberUtils.toLong(delegacionMunicipio) != -1L) {
                        MunicipioDTO municipioDTO = new MunicipioDTO();
                        municipioDTO.setMunicipioId(NumberUtils.toLong(delegacionMunicipio));
                        domicilioDTO.setMunicipioDTO(municipioDTO);
                    }
                }

                //asentamiento-colonia
                if (!asentamientoColonia.equalsIgnoreCase("")) {

                    if (NumberUtils.toLong(asentamientoColonia) != -1L) {
                        AsentamientoDTO asentamientoDTO = new AsentamientoDTO();
                        asentamientoDTO.setAsentamientoId(NumberUtils.toLong(asentamientoColonia));
                        domicilioDTO.setAsentamientoDTO(asentamientoDTO);
                    }
                }

                //tipo de calle
                if (!tipoCalle.equalsIgnoreCase("")) {

                    if (NumberUtils.toLong(tipoCalle) != -1) {

                        ValorDTO valorCalleId = new ValorDTO(NumberUtils.toLong(tipoCalle));
                        domicilioDTO.setValorCalleId(valorCalleId);
                    }
                }

                //parte derecha de la pantalla ingresar domicilio
                domicilioDTO.setCalle(calle);
                domicilioDTO.setNumeroExterior(numExterior);
                domicilioDTO.setNumeroInterior(numInterior);
                domicilioDTO.setEntreCalle1(entreCalle);
                domicilioDTO.setEntreCalle2(ycalle);
                domicilioDTO.setAlias(aliasDomicilio);
                domicilioDTO.setEdificio(edificio);
                domicilioDTO.setReferencias(referencias);

                if (longitud != null && !longitud.equalsIgnoreCase("")) {
                    domicilioDTO.setLongitud(longitud);
                } else {
                    domicilioDTO.setLongitud(null);
                }
                if (latitud != null && !latitud.isEmpty()) {
                    domicilioDTO.setLatitud(latitud);
                } else {
                    domicilioDTO.setLatitud(null);
                }

                //Seteamos la calidad del domicilio
                CalidadDTO calidadDomicilioDTO = new CalidadDTO();
                calidadDomicilioDTO.setCalidades(Calidades.DOMICILIO);
                domicilioDTO.setCalidadDTO(calidadDomicilioDTO);
                domicilioDTO.setFechaCreacionElemento(new Date());

                //Seteamos el testigo con su domicilio
                involucradoDTO.setDomicilio(domicilioDTO);

            } else { //CUANDO EL PAIS NO ES MEXICO

                DomicilioExtranjeroDTO domicilioExtranjeroDTO = new DomicilioExtranjeroDTO();

                //Parte izq de la pantalla ingresar domicilio
                if (!pais.equalsIgnoreCase("")) {
                    if (NumberUtils.toLong(pais) != -1L) {
                        domicilioExtranjeroDTO.setPais(pais);
                    }
                }

                domicilioExtranjeroDTO.setCodigoPostal(codigoPostal);
                domicilioExtranjeroDTO.setEstado(entidadFederativa);
                domicilioExtranjeroDTO.setCiudad(ciudad);
                domicilioExtranjeroDTO.setMunicipio(delegacionMunicipio);
                domicilioExtranjeroDTO.setAsentamientoExt(asentamientoColonia);

                //parte derecha de la pantalla ingresar domicilio
                domicilioExtranjeroDTO.setCalle(calle);
                domicilioExtranjeroDTO.setNumeroExterior(numExterior);
                domicilioExtranjeroDTO.setNumeroInterior(numInterior);
                domicilioExtranjeroDTO.setEntreCalle1(entreCalle);
                domicilioExtranjeroDTO.setEntreCalle2(ycalle);
                domicilioExtranjeroDTO.setAlias(aliasDomicilio);
                domicilioExtranjeroDTO.setEdificio(edificio);
                domicilioExtranjeroDTO.setReferencias(referencias);

                if (!longitud.equalsIgnoreCase("")) {
                    domicilioExtranjeroDTO.setLongitud(longitud);
                } else {
                    domicilioExtranjeroDTO.setLongitud(null);
                }
                if (!latitud.equalsIgnoreCase("")) {
                    domicilioExtranjeroDTO.setLatitud(latitud);
                } else {
                    domicilioExtranjeroDTO.setLatitud(null);
                }

                //Seteamos la calidad del domicilio
                CalidadDTO calidadDomicilioExtranjeroDTO = new CalidadDTO();
                calidadDomicilioExtranjeroDTO.setCalidades(Calidades.DOMICILIO);
                domicilioExtranjeroDTO.setCalidadDTO(calidadDomicilioExtranjeroDTO);
                domicilioExtranjeroDTO.setFechaCreacionElemento(new Date());

                //Seteamos el domicilio extranjero de notificaciones a la persona
                involucradoDTO.setDomicilio(domicilioExtranjeroDTO);
            }

            List<TelefonoDTO> lstTelefonos = new ArrayList<TelefonoDTO>();
            String strTelefonos = medioContactoTelefono;
            StringTokenizer lstStrTelefonos = new StringTokenizer(strTelefonos, "|");

            while (lstStrTelefonos.hasMoreElements()) {

                String strTelefono = (String) lstStrTelefonos.nextElement();
                String[] datosTelefono = strTelefono.split(",");

                TelefonoDTO telefono = new TelefonoDTO();

                ValorDTO valorTipoTelefono = new ValorDTO();
                valorTipoTelefono.setIdCampo(new Long(datosTelefono[0]));
                telefono.setValorTipoTelefono(valorTipoTelefono);
                telefono.setCodigoPais(datosTelefono[1]);
                telefono.setCodigoArea(datosTelefono[2]);
                telefono.setNumeroTelefonico(datosTelefono[3]);
                log.info("==>Telefono: [" + datosTelefono[0] + ":(" + datosTelefono[1] + ")" + datosTelefono[2] + " " + datosTelefono[3] + "]");
                lstTelefonos.add(telefono);
            }
            involucradoDTO.setTelefonosDTO(lstTelefonos);

            List<CorreoElectronicoDTO> lstCorreos = new ArrayList<CorreoElectronicoDTO>();

            if (!medioContactoTelefono.trim().isEmpty()) {
                String[] datosCorreo = medioContactoTelefono.split(",");
                for (int i = 0; i < datosCorreo.length; i++) {
                    CorreoElectronicoDTO correo = new CorreoElectronicoDTO();
                    correo.setDireccionElectronica(datosCorreo[i]);
                    lstCorreos.add(correo);
                }
            }
            involucradoDTO.setCorreosDTO(lstCorreos);
            Long idTipoAsesoria = null;

            String etapaExpediente = request.getParameter("etapaExpediente");
            log.info("&&&&&&&&&&&&&etapaExpediente: " + etapaExpediente);
            EtapasExpediente etapa = null;
            etapa = EtapasExpediente.getByValor(new Long(etapaExpediente));

            involucradoDTO = solicitudDelegate.guardarDefendidoSolicitudDefensoria(involucradoDTO, idTipoAsesoria, etapa);
            log.info(involucradoDTO);

            String xml = null;
            PrintWriter pw = null;
            converter.alias("involucradoDTO", InvolucradoDTO.class);
            xml = converter.toXML(involucradoDTO);
            response.setContentType("text/xml");
            pw = response.getWriter();
            pw.print(xml);
            pw.flush();
            pw.close();

        } catch (Exception e) {
            log.error(e.getCause(), e);
        }
        return null;
    }

    public ActionForward consultarExpedienteDefensoria(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            String idExpediente = request.getParameter("idExpediente");
            if (StringUtils.isNotBlank(idExpediente) && StringUtils.isNumeric(idExpediente)) {
                ExpedienteDTO expedienteDTO = new ExpedienteDTO();
                expedienteDTO.setExpedienteId(NumberUtils.toLong(idExpediente));
                UsuarioDTO usuario = super.getUsuarioFirmado(request);
                List<InvolucradoDTO> involucradoDTOs = involucradoDelegate
                        .consultarInvolucradoExpediente(expedienteDTO,
                                Calidades.PROBABLE_RESPONSABLE_PERSONA, usuario);
                InvolucradoDTO involucrado = involucradoDTOs.get(0);
                response.setContentType("text/xml");
                PrintWriter pw = response.getWriter();
                XStream converter=new XStream();
                converter.alias("imputado", InvolucradoDTO.class);
                converter.alias("nombredemografico", NombreDemograficoDTO.class);
                pw.print(converter.toXML(involucrado));
                pw.flush();
                pw.close();
            }
        } catch (Exception e) {

        }
        return null;
    }

    public ActionForward guardaMotivo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            log.info("guardando Motivo");

            String numExpediente = request.getParameter("numExpediente");
            log.info("numExpediente====" + numExpediente);

            String numeroExpedienteId = request.getParameter("numeroExpedienteId");
            log.info("numeroExpedienteId====" + numeroExpedienteId);

            String documentoId = request.getParameter("documentoId");
            log.info("documentoId====" + documentoId);

            String motivo = request.getParameter("motivo");

            log.info("Pinta Motivo" + motivo);

            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            SolicitudDefensorDTO solicitudDefensorDTO = new SolicitudDefensorDTO();

            expedienteDTO.setNumeroExpediente(numExpediente);
            expedienteDTO.setNumeroExpedienteId(Long.parseLong(numeroExpedienteId));

            solicitudDefensorDTO.setMotivo(motivo);
            solicitudDefensorDTO.setExpedienteDTO(expedienteDTO);
            solicitudDefensorDTO.setDocumentoId(NumberUtils.toLong(documentoId));

            solicitudDelegate.guardarMotivoSolicitudDefensoria(solicitudDefensorDTO);

            String xml = null;
            PrintWriter pw = null;
            converter.alias("exito", SolicitudDefensorDTO.class);

            xml = converter.toXML(solicitudDefensorDTO);
            response.setContentType("text/xml");
            pw = response.getWriter();
            pw.print(xml);
            pw.flush();
            pw.close();

        } catch (Exception e) {
            log.error(e.getCause(), e);

        }
        return null;
    }

    /**
     * M&eacute;todo para consultar los defensores a los que se les puede
     * asignar un expediente de acuerdo al distrito del usuario que los esta
     * consultando (usuario firmado)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward solicitudesDefensorGrid(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("CONSULTAR LISTA DE DEFENSORES......solicitudesDefensorGrid....Action");

            List<FuncionarioDTO> listaDefensores = new ArrayList<FuncionarioDTO>();

            UsuarioDTO usuarioFirmado = super.getUsuarioFirmado(request);

            if (usuarioFirmado != null
                    && usuarioFirmado.getFuncionario() != null
                    && usuarioFirmado.getFuncionario().getDiscriminante() != null
                    && usuarioFirmado.getFuncionario().getDiscriminante().getDistrito() != null) {

                listaDefensores = funcionarioDelegate
                        .consultarDefensoresActivosPorDistrito(usuarioFirmado
                                .getFuncionario().getDiscriminante()
                                .getDistrito());
            }

            request.getSession().setAttribute("listaDefensorDTO",
                    listaDefensores);

            mapping.findForward("listaDefensorDTO");
            converter.alias("listaDefensorDTO", ActionMapping.class);

            response.setContentType("text/xml; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter writer = response.getWriter();

            writer.print("<rows>");
            // Configuracion del Paginador
            String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
            writer.print(paginacion);

            final Map<Long, FuncionarioDTO> funsMap = new HashMap<Long, FuncionarioDTO>();

            for (FuncionarioDTO funcionarioDTO : listaDefensores) {
                funsMap.put(funcionarioDTO.getClaveFuncionario(),
                        funcionarioDTO);
                writer.print("<row id='" + funcionarioDTO.getClaveFuncionario()
                        + "'>");
                writer.print("<cell>" + funcionarioDTO.getNombreFuncionario()
                        + " " + funcionarioDTO.getApellidoPaternoFuncionario()
                        + " " + funcionarioDTO.getApellidoMaternoFuncionario()
                        + "</cell>");
                writer.print("</row>");
            }
            request.getSession().setAttribute("miMapaFuncionarios", funsMap);

            writer.print("</rows>");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public ActionForward solicitudesDefensor(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            Long idFun = NumberUtils.toLong(request.getParameter("idDefensor"), 0L);
            log.info("defensoria:: Modificado");

            @SuppressWarnings("unchecked")
            final Map<Long, FuncionarioDTO> funsMap = (Map<Long, FuncionarioDTO>) request.getSession().getAttribute("miMapaFuncionarios");

            FuncionarioDTO funConsultado = new FuncionarioDTO();
            if (funsMap != null) {
                funConsultado = funsMap.get(idFun);
            }

            String xml = null;
            PrintWriter pw = null;
            XStream converter=new XStream();
            converter.alias("miMapaFuncionarios", java.util.List.class);
            converter.alias("funcionarioDTO", FuncionarioDTO.class);
            converter.alias("DepartamentoDTO", DepartamentoDTO.class);
            converter.alias("TelefonoDTO", TelefonoDTO.class);
            converter.alias("CorreoElectronicoDTO", CorreoElectronicoDTO.class);
            xml = converter.toXML(funConsultado);
            response.setContentType("text/xml");
            pw = response.getWriter();
            pw.print(xml);
            pw.flush();
            pw.close();
            log.info("lista de Defensor por mapa ::::" + xml);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward designarAbogadoDefensorCoordinador(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("designar Abogado Defensor");

            SolicitudDefensorDTO solicitudDefensorDTO = new SolicitudDefensorDTO();
            FuncionarioDTO funcionarioDTO = new FuncionarioDTO();

            String numSolicitud = request.getParameter("numSolicitud");
            log.info(numSolicitud + "designar Abogado Defensor numSolicitud" + numSolicitud);

            String idDefensor = request.getParameter("idDefensor");
            log.info("designar Abogado Defensor" + idDefensor);

            solicitudDefensorDTO.setDocumentoId(NumberUtils.toLong(numSolicitud));
            funcionarioDTO.setClaveFuncionario(NumberUtils.toLong(idDefensor));
            solicitudDefensorDTO.setFuncionario(funcionarioDTO);

            funcionarioDelegate.designarAbogadoDefensor(solicitudDefensorDTO);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            XStream converter=new XStream();
            String xml = converter.toXML("No se pueden registrar vacaciones por que el periodo coincide con un periodo registrado");
            escribir(response, xml, null);
        }
        return null;
    }

    /**
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    //MOD defensorATE, CoordinadorDEF
    public ActionForward designarAbogadoDefensorExpediente(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("********ACTION PARA GENERAR UN AVISO DE DESIGNACION*************");
            log.info("VERIFICANCO PARAMETROS.................");
            log.info("numSolicitudDeDefensor=" + request.getParameter("numSolicitud"));
            log.info("idDefensor=" + request.getParameter("idDefensor"));
            log.info("esAsesoria=" + request.getParameter("esAsesoria"));

            Long idDocumento = NumberUtils.toLong(request.getParameter("numSolicitud"), 0L);
            Long idFuncionario = NumberUtils.toLong(request.getParameter("idDefensor"), 0L);
            Boolean esAsesoria = BooleanUtils.toBooleanObject(request.getParameter("esAsesoria"));

            AvisoDesignacionDTO avisoDesignacionDTO = new AvisoDesignacionDTO();

            SolicitudDefensorDTO solicitudDefensorDTO = new SolicitudDefensorDTO(
                    idDocumento);
            avisoDesignacionDTO.setSolicitudDefensor(solicitudDefensorDTO);

            avisoDesignacionDTO
                    .setFuncionario(new FuncionarioDTO(idFuncionario));

            if (esAsesoria != null && esAsesoria) {
                avisoDesignacionDTO = notificacionDelegate.designarAbogadoDefensorAsesoria(avisoDesignacionDTO);
                log.debug("********************************* avisoDesignacionDTO:"
                        + avisoDesignacionDTO.getSolicitudDefensor() != null ? avisoDesignacionDTO
                                .getSolicitudDefensor().getEstatus() : "");
            } else {
                notificacionDelegate.designarAbogadoDefensor(avisoDesignacionDTO, true, true);
            }

            XStream converter=new XStream();
            converter.alias("avisoDesignacionDTO", AvisoDesignacionDTO.class);
            converter.alias("solicitudDefensorDTO", SolicitudDefensorDTO.class);
            converter.alias("valorDTO", ValorDTO.class);
            String xml = converter.toXML(avisoDesignacionDTO);
            log.debug("Response::" + xml);
            escribirRespuesta(response, xml);
        } catch (NSJPNegocioException e) {
            log.error(e.getMessage(), e);
            escribirError(response, e);
        }
        return null;
    }

    /**
     * M&eacute;todo para reasignar un expediente a un defensor
     *
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward reasignarAbogadoDefensorExpediente(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("********reasignarAbogadoDefensorExpediente ACTION*************");
            log.info("VERIFICANCO PARAMETROS.................");
            log.info("numeroExpedienteId=" + request.getParameter("numeroExpedienteId"));
            log.info("idDefensor=" + request.getParameter("idDefensor"));

            Long numeroExpedienteId = NumberUtils.toLong(request.getParameter("numeroExpedienteId"), 0L);
            Long idFuncionario = NumberUtils.toLong(request.getParameter("idDefensor"), 0L);

            //LLenamos los campos para resignar el defensor
            AvisoDesignacionDTO avisoDesignacionDto = new AvisoDesignacionDTO();

            ExpedienteDTO expedienteDto = new ExpedienteDTO();
            expedienteDto.setNumeroExpedienteId(numeroExpedienteId);
            avisoDesignacionDto.setExpediente(expedienteDto);
            avisoDesignacionDto
                    .setFuncionario(new FuncionarioDTO(idFuncionario));

            notificacionDelegate.reasignarAbogadoDefensorExpediente(avisoDesignacionDto);

            converter.alias("respuesta", String.class);
            escribirRespuesta(response, converter.toXML("exito"));

        } catch (NSJPNegocioException e) {
            escribirError(response, e);
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward consultarListaEstadoExpediente(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("lista de etapa de expediente");
            List<CatalogoDTO> listaCatalogo = catDelegate.recuperarCatalogo(Catalogos.ETAPA_EXPEDIENTE);

            String xml = null;
            PrintWriter pw = null;
            XStream converter=new XStream();
            converter.alias("listaCatalogo", java.util.List.class);
            converter.alias("catEstadoExpediente", CatalogoDTO.class);

            xml = converter.toXML(listaCatalogo);
            response.setContentType("text/xml");
            pw = response.getWriter();
            pw.print(xml);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward actualizaEstatusAvisoDesignacion(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            log.info("designar cambio estatus a notificacion");
            String idDocumento = request.getParameter("idDocumento");
            log.info("designar cambio estatus::::: " + idDocumento);
            notificacionDelegate.cerrarAvisoDesignacion(NumberUtils.toLong(idDocumento));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Action que actualiza el estatus de las solicitudes de acuerdo al numero
     * de expediente proporcionado, tambien cambia el estatus del numero de
     * expediente.
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    //MOD defensor
    public ActionForward actualizarEstatusSolicitudNumeroExpediente(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            log.debug("**CAMBIAR ESTATUS DE LAS SOLICITUDES**");
            Long numeroExpedienteId = NumberUtils.toLong(
                    request.getParameter("numeroExpedienteId"), 0);
            Long nuevoEstatusNumExpId = NumberUtils.toLong(
                    request.getParameter("nuevoEstatusNumExpId"), 0);

            log.info(" nuevoEstatusNumExpId:" + numeroExpedienteId
                    + "estatusId:" + nuevoEstatusNumExpId);

            Boolean respuesta = solicitudDelegate.actualizarEstatusSolicitudes(
                    nuevoEstatusNumExpId, numeroExpedienteId);

            String xml = null;
            converter.alias("respuesta", Boolean.class);
            xml = converter.toXML(respuesta);
            escribirRespuesta(response, xml);

        } catch (NSJPNegocioException e) {
            log.error(e.getMessage(), e);
            escribirError(response, e);
        }
        return null;
    }

    /**
     * Metodo utilizado para la actualizacion de los avisos de detencion a
     * atendidas..
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return null
     * @throws IOException
     */
    public ActionForward actualizaEstatusAvisoDetencion(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("Actualiza el estatus de los avisos de detencion");

            String idDocumento = request.getParameter("idDocumento");
            log.info("documento id::::: " + idDocumento);

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setFuncionario(new FuncionarioDTO(1L));

            AvisoDetencionDTO avisoDetencionDTO = new AvisoDetencionDTO();
            avisoDetencionDTO.setDocumentoId(NumberUtils.toLong(idDocumento));
            avisoDetencionDTO.setUsuario(usuarioDTO);

            avisoDetencionDTO = avisoDetencionDelegate.atenderAvisoDetencion(avisoDetencionDTO);
            log.info("llega aviso de de tencion cambiado a atendido" + avisoDetencionDTO);

            String xml = null;
            PrintWriter pw = null;
            converter.alias("avisoDetencionDTO", AvisoDetencionDTO.class);
            xml = converter.toXML(avisoDetencionDTO);
            response.setContentType("text/xml");
            pw = response.getWriter();
            pw.print(xml);
            pw.flush();
            pw.close();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public ActionForward actualizarEtapaExpedienteSolicitudDefensoria(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("designar cambio estatus");

            SolicitudDefensorDTO solicitudDTO = new SolicitudDefensorDTO();

            String numSolicitud = request.getParameter("documentoId");
            String etapa = request.getParameter("etapaExpediente");
            log.info("muestra etapa::::: " + etapa);
            solicitudDTO.setDocumentoId(NumberUtils.toLong(numSolicitud));

            log.info("designar cambio estatus::::: " + numSolicitud);
            log.info("designar cambio estatus::::: " + etapa);

            EtapasExpediente tipoExpediente = EtapasExpediente.getByValor(NumberUtils.toLong(etapa));

            log.info("Tipo de defensoria::" + etapa);
            log.info("designar cambio estatus:::::lalalal aqui gama " + tipoExpediente);

            if (EtapasExpediente.INTEGRACION.equals(etapa)) {
                log.info("entra al log::::: " + etapa);
                solicitudDelegate.actualizarEtapaExpedienteSolicitudDefensoria(solicitudDTO, EtapasExpediente.INTEGRACION);
            }

            if (EtapasExpediente.INTEGRACION.equals(tipoExpediente)) {
                log.info("entra al log::::: " + etapa);
                solicitudDelegate.actualizarEtapaExpedienteSolicitudDefensoria(solicitudDTO, EtapasExpediente.INTEGRACION);
            }

            if (EtapasExpediente.EJECUCION.equals(tipoExpediente)) {
                solicitudDelegate.actualizarEtapaExpedienteSolicitudDefensoria(solicitudDTO, EtapasExpediente.EJECUCION);
            }
//			

            if (EtapasExpediente.CONCILIACION_Y_MEDIACION.equals(tipoExpediente)) {
                solicitudDelegate.actualizarEtapaExpedienteSolicitudDefensoria(solicitudDTO, EtapasExpediente.CONCILIACION_Y_MEDIACION);
            }
            if (EtapasExpediente.TECNICA.equals(tipoExpediente)) {
                solicitudDelegate.actualizarEtapaExpedienteSolicitudDefensoria(solicitudDTO, EtapasExpediente.TECNICA);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * M&eacute;todo utilizado para la consulta de designaciones de defensor por
     * estatus atendida de los avisos de detenci&oacute;n por estatus atendidos
     * y avisos de incidencias.
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return null
     * @throws IOException En caso de obtener una exception
     */
    @Deprecated  //Se utilizaba en indexDefensoriaAtencionTemprana.jsp y indexDefensoriaCoordinado.jsp 
    public ActionForward recibirDesignaciones(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            //Distrito, tipo de Asesoria
            log.debug("designaciones de defensoria");

            Long distritoId = null;

            Long tipoSolicitudId = NumberUtils.toLong(
                    request.getParameter("tipoSolicitud"), 0L);
            TiposSolicitudes tipoSolicitud = (tipoSolicitudId > 0L ? TiposSolicitudes
                    .getByValor(tipoSolicitudId) : null);

            EstatusNotificacion estadoNotificacion = EstatusNotificacion.NO_ATENDIDA;

            log.debug("**recibirDesignaciones-estadoNotificacion:"
                    + estadoNotificacion + "-Funcionario:null - tipoSolicitud:"
                    + tipoSolicitud + "-distritoId:" + distritoId);

            List<AvisoDesignacionDTO> designaciones = notificacionDelegate
                    .consultarAvisosDesignacion(estadoNotificacion, null, tipoSolicitud, distritoId);

            response.setContentType("text/xml; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter writer = response.getWriter();

            writer.print("<rows>");
            // Configuracion del Paginador
            String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
            writer.print(paginacion);

            String institucion = " - ";
            String numeroGCaso = " - ";
            String numeroExped = " - ";
            String sfuncionari = " Sin Defensor ";
            Long numeroExpedienteId = 1L;
            Long solicitudDefensorId = 0L;

            for (AvisoDesignacionDTO designacion : designaciones) {

                institucion = " - ";
                numeroGCaso = " - ";
                numeroExped = " - ";
                sfuncionari = " Sin Defensor ";

                if (designacion.getSolicitudDefensor() != null) {
                    institucion = designacion.getSolicitudDefensor()
                            .getInstitucion().getNombreInst();
                    numeroGCaso = designacion.getSolicitudDefensor()
                            .getNumeroCasoAsociado();
                    solicitudDefensorId = designacion.getSolicitudDefensor()
                            .getDocumentoId();
                    //@deprecated
                } else if (designacion.getAvisoDetencion() != null) {
                    institucion = designacion.getAvisoDetencion()
                            .getConfInstitucion().getNombreInst();
                    numeroGCaso = designacion.getAvisoDetencion()
                            .getNumeroCasoAsociado();
                }

                if (designacion.getExpediente() != null
                        && designacion.getExpediente().getCasoDTO() != null) {
                    numeroGCaso = designacion.getExpediente().getCasoDTO()
                            .getNumeroGeneralCaso();
                }
                if (numeroGCaso == null) {
                    numeroGCaso = " - ";
                }

                if (designacion.getFuncionario() != null) {
                    sfuncionari = designacion.getFuncionario()
                            .getNombreCompleto();
                } else {
                    if (designacion.getTieneSolicitudDefensorExterno()) {
                        sfuncionari = "En espera de defensor externo";
                    }
                }

                if (designacion.getExpediente() != null) {
                    numeroExped = designacion.getExpediente()
                            .getNumeroExpediente();
                    numeroExpedienteId = designacion.getExpediente()
                            .getNumeroExpedienteId();
                }

                String fechaCreacion = DateUtils.formatear(designacion
                        .getFechaCreacion());
                String horaCreacion = DateUtils.formatearHora(designacion
                        .getFechaCreacion());

                writer.print("<row id='" + designacion.getDocumentoId() + "'>");
                writer.print("<cell>" + institucion + "</cell>");
                writer.print("<cell>" + numeroGCaso + "</cell>");
                writer.print("<cell>" + numeroExped + "</cell>");
                writer.print("<cell>" + sfuncionari + "</cell>");
                writer.print("<cell>" + fechaCreacion + "-" + horaCreacion
                        + "</cell>");
                writer.print("<cell>" + numeroExpedienteId + "</cell>");
                writer.print("<cell>" + solicitudDefensorId + "</cell>");
                writer.print("</row>");
            }

            writer.print("</rows>");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public ActionForward actualizaEstatusDesignacion(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("designar cambio estatus para designaciones");

            SolicitudDefensorDTO solicitudDTO = new SolicitudDefensorDTO();

            String numSolicitud = request.getParameter("numSolicitud");

            solicitudDTO.setDocumentoId(NumberUtils.toLong(numSolicitud));
            log.info("designar cambio estatus designacion::::: " + numSolicitud);

            solicitudDelegate.actualizarEstatusSolicitudDefensoria(solicitudDTO, EstatusSolicitud.EN_PROCESO);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Metodo utilizado para solicitar un abogado defensor externo a la JSP de
     * atencionSolicitudAudienciaNotificador
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return succes - En caso de que el proceso sea correcto
     * @throws IOException En caso de obtener una exception
     */
    public ActionForward solicitarAbogadoDefensorExterno(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            log.info("EJECUTANDO ACTION SOLICITAR ABOGADO DEFENSOR EXTERNO ");

            String formaId = request.getParameter("formaId");
            log.info("LLEGA formaId:::" + formaId);
            request.setAttribute("formaId", formaId);

            String esconderArbol = request.getParameter("esconderArbol");
            log.info("LLEGA esconderArbol=" + esconderArbol);
            request.setAttribute("esconderArbol", esconderArbol);

            Long solicitudId = NumberUtils.toLong(request.getParameter("solicitudId"));
            SolicitudDTO solicitudDTO = new SolicitudDTO();
            solicitudDTO.setDocumentoId(solicitudId);
            solicitudDTO = solicitudDelegate.obtenerDetalleSolicitud(solicitudDTO);
            ExpedienteDTO numeroExpediente = solicitudDTO.getExpedienteDTO();
            log.info("Expediente DTO=" + solicitudDTO.getExpedienteDTO());
            super.setExpedienteTrabajo(request, numeroExpediente);

        } catch (Exception e) {
            log.info(e.getCause(), e);

        }
        return mapping.findForward("succes");
    }

    /**
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward consultarProbablesResponsablesDetenidos(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("consultarProbablesResponsablesDetenidos");

            String expedienteId = request.getParameter("expedienteId");
            log.info("expediente Id" + expedienteId);

            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setExpedienteId(NumberUtils.toLong(expedienteId));

            Boolean esDetenido = true;

            List<InvolucradoDTO> involucradoDTOs = involucradoDelegate.consultarProbablesResponsablesDetenidos(expedienteDTO, esDetenido);

            log.info("lista de involucrados detenidos" + involucradoDTOs);
            response.setContentType("text/xml; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter writer = response.getWriter();

            writer.print("<rows>");

            int lTotalRegistros = involucradoDTOs.size();

            writer.print("<records>" + lTotalRegistros + "</records>");

            for (InvolucradoDTO involucradoDTO : involucradoDTOs) {

                writer.print("<row id='" + involucradoDTO.getElementoId() + "'>");
                writer.print("<cell>" + involucradoDTO.getNombreCompleto() + "</cell>");
                writer.print("<cell>");

                for (DelitoDTO delitoDTO : involucradoDTO.getDelitosCometidos()) {

                    writer.print(delitoDTO.getCatDelitoDTO().getNombre() + "  ");

                }
                writer.print("</cell>");

                DetencionDTO detencion = null;
                if (involucradoDTO.getDetenciones() != null && !involucradoDTO.getDetenciones().isEmpty()) {
                    detencion = involucradoDTO.getDetenciones().get(0);
                    for (DetencionDTO det : involucradoDTO.getDetenciones()) {
                        if ((detencion == null) || (detencion != null && detencion.getFechaInicioDetencion().compareTo(det.getFechaInicioDetencion()) == -1)) {
                            detencion = det;
                        }

                    }
                    writer.print("<cell>" + detencion.getFechaInicioDetencion() + "</cell>");
                    writer.print("<cell>" + detencion.getLugarDetencionProvicional() + "</cell>");
                }
                writer.print("</row>");

            }

            writer.print("</rows>");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Metodo utilizado para solicitar un abogado defensor externo a la JSP de
     * atencionSolicitudAudienciaNotificador
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return succes - En caso de que el proceso sea correcto
     * @throws IOException En caso de obtener una exception
     */
    public ActionForward registrarAvisoDetencion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            log.info("EJECUTANDO ACTION registrarAvisoDetencion ");

            String noCaso = request.getParameter("noCaso");
            log.info("LLEGA noCaso:::" + noCaso);

            CasoDTO casoDTO = new CasoDTO();
            casoDTO.setNumeroGeneralCaso(noCaso);

            String imputadosId = request.getParameter("imputadoId");
            log.info("LLEGAN imputadosId:::" + imputadosId);

            String[] imputadoId = imputadosId.split(",");

            int e = 0;
            String cont = null;

            for (e = 0; e < imputadoId.length; e++) {

                cont = imputadoId[e];

                log.info("LLEGA imputadoId:::" + cont);
                log.info("LLEGA noCaso:::" + noCaso);

                solicitudDelegate.registrarAvisoDeDetencion(NumberUtils.toLong(cont), casoDTO, null, null);

            }

        } catch (Exception e) {
            log.info(e.getCause(), e);

        }
        return mapping.findForward("succes");
    }

    /**
     * Genera un aviso de designaci&oacute;n al cual ser&aacute; asociado un
     * abogado defensor posteriormente y son las notificaciones que se envias
     * desde el coordinador a los defensores.
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Deprecated
    public ActionForward registrarAvisoDesignacion(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            String idSolicitud = request.getParameter("numSolicitud");
            String idDocumento = request.getParameter("idDocumento");
            int caso = Integer.parseInt(request.getParameter("caso"));
            AvisoDesignacionDTO designacion = new AvisoDesignacionDTO();
            UsuarioDTO usuarioDTO = super.getUsuarioFirmado(request);
            designacion.setUsuario(usuarioDTO);

            if (idSolicitud != null && !idSolicitud.isEmpty()) {
                SolicitudDefensorDTO solicitud = new SolicitudDefensorDTO();
                solicitud.setDocumentoId(new Long(idSolicitud));
                designacion.setSolicitudDefensor(solicitud);

            } else {
                AvisoDetencionDTO detencion = new AvisoDetencionDTO();
                detencion.setDocumentoId(new Long(idDocumento));
                designacion.setAvisoDetencion(detencion);
            }

            if (caso == 1) {
                designacion.setExpediente(new ExpedienteDTO());
            } else {
                designacion.setExpediente(null);
            }

            designacion = notificacionDelegate.registrarAvisoDesignacion(designacion);
            designacion.setAvisoDetencion(null);
            designacion.setSolicitudDefensor(null);
            PrintWriter pw = response.getWriter();
            response.setContentType("text/xml");
            converter.alias("designacion", AvisoDesignacionDTO.class);
            pw.print(converter.toXML(designacion));
            pw.flush();
            pw.close();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Metodo utilizado para buscar los probables responsables asociados
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return succes - En caso de que el proceso sea correcto
     * @throws IOException En caso de obtener una exception
     */
    public ActionForward consultarProbablesResponsables(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("consultar Probables Responsables");

            String expedienteId = request.getParameter("idExpediente");

            log.info("expediente Id" + expedienteId);

            ExpedienteDTO expedienteDTO = new ExpedienteDTO();
            expedienteDTO.setExpedienteId(NumberUtils.toLong(expedienteId));
            UsuarioDTO usuario = super.getUsuarioFirmado(request);
            List<DefensaInvolucradoDTO> defensaInvolucrados = involucradoDelegate
                    .consultarInvolucradoExpedienteDefensoria(expedienteDTO,
                            Calidades.PROBABLE_RESPONSABLE_PERSONA, usuario);

            log.info("lista de probables responsables" + defensaInvolucrados);
            response.setContentType("text/xml; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter writer = response.getWriter();

            writer.print("<rows>");

            int lTotalRegistros = defensaInvolucrados.size();

            writer.print("<records>" + lTotalRegistros + "</records>");

            for (DefensaInvolucradoDTO defensa : defensaInvolucrados) {

                writer.print("<row id='" + defensa.getExpediente().getNumeroExpedienteId() + "'>");
                writer.print("<cell>" + defensa.getExpediente().getNumeroExpediente() + "</cell>");
                writer.print("<cell>" + defensa.getInvolucrado().getNombreCompleto() + "</cell>");
                writer.print("</row>");
            }
            writer.print("</rows>");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * M&eacute;todo utilizado para realizar la consulta centros de dentencion
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     */
    public ActionForward cargarCentroDetencion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {

            log.info("ejecutando Action consultar centros de detencion");

            String claveTipoCentroDetencion = request.getParameter("tipoCentroDetencion");
            log.info("clave del tipo de centro de detencionion" + claveTipoCentroDetencion);

            List<CentroDetencionDTO> centroDetencionDTOs = centroDetencionDelegate.consultarCentrosDetencionPorTipo(Long.parseLong(claveTipoCentroDetencion));
            XStream converter=new XStream();
            converter.alias("listaCatalogo", java.util.List.class);
            converter.alias("catCentrosDetencion", CentroDetencionDTO.class);
            String xml = converter.toXML(centroDetencionDTOs);
            response.setContentType("text/xml");
            PrintWriter pw = response.getWriter();
            pw.print(xml);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * M&eacute;todo utilizado para realizar la consulta de un centro de
     * detencion por medio de su id
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     */
    public ActionForward consultarCentroDetencionPorId(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {

            log.info("ejecutando Action consultar centros de detencion por id");

            String claveCentroDetencion = request.getParameter("centroDetencion");
            log.info("clave del tipo de centro de detencion" + claveCentroDetencion);

            CentroDetencionDTO centroDetencionDTO = centroDetencionDelegate.consultarCentroDetencion(Long.parseLong(claveCentroDetencion));
            XStream converter=new XStream();
            converter.alias("centroDetencion", CentroDetencionDTO.class);
            converter.alias("entidadFed", EntidadFederativaDTO.class);
            String xml = converter.toXML(centroDetencionDTO);
            response.setContentType("text/xml");
            PrintWriter pw = response.getWriter();
            pw.print(xml);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public ActionForward atualizarEstatusSolicitudDefensoria(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {

            log.info("ejecutando Action atualizarEstatusSolicitudDefensoria");

            String solicitudDefensoriaID = request.getParameter("solicitudId");
            log.info("solicitudDefensoriaID " + solicitudDefensoriaID);

            SolicitudDefensorDTO solicitudDefensorDTO = new SolicitudDefensorDTO();
            solicitudDefensorDTO.setDocumentoId(new Long(solicitudDefensoriaID));

            solicitudDelegate.actualizarEstatusSolicitudDefensoria(solicitudDefensorDTO, EstatusSolicitud.ABIERTA);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * M&eacute;todo utilitario que lleva a cabo la generaci&oacute;n de cadenas
     * de delitos omitiendo aquellos delitos que se encuentran repetidos dentro
     * de la lista.
     *
     * @param delitos - Lista con los delitos de la cual se va a generar la
     * cadena.
     * @return cadenaDelitos - Cadena con los nombres de los delitos
     * concatenados evitando repeticiones.
     */
    private String obtenerCadenaDelitos(List<DelitoDTO> delitos) {
        StringBuilder sb = null;
        String cadenaDelitos = null;
        Map<Long, String> delitosUnicos = null;
        if (delitos != null
                && !delitos.isEmpty()) {
            delitosUnicos = new HashMap<Long, String>();
            for (DelitoDTO delito : delitos) {
                if (delito.getCatDelitoDTO() != null
                        && delito.getCatDelitoDTO().getCatDelitoId() != null
                        && delito.getCatDelitoDTO().getCatDelitoId() > 0L
                        && !delitosUnicos.containsKey(delito.getCatDelitoDTO().getCatDelitoId())) {
                    delitosUnicos.put(delito.getCatDelitoDTO().getCatDelitoId(), delito.getCatDelitoDTO().getNombre());
                }
            }
            sb = new StringBuilder("");
            int contador = 0;
            for (Long llave : delitosUnicos.keySet()) {
                if (contador > 0) {
                    sb.append(", ");
                }
                sb.append(delitosUnicos.get(llave));
                contador++;
            }
        }
        if (sb != null) {
            cadenaDelitos = sb.toString();
        }
        return cadenaDelitos;
    }

    /**
     * M&eacute;todo para llevar a cabo la consulta de las solicitudes de
     * defensor de acuerdo al estatus de loa solicitud y al la
     * instituci&oacute;n solicitante
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    //MOD defensorATE, defensor (asesoriaLegal)
    public ActionForward consultarSolicitudesDeDefensorPorFiltro(
            ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("******CONSULTAR SOLICITUDES DE DEFENSOR POR ESTATUS E INSTITUCION***************");
            log.info("VERIFICANDO PARAMETROS***************");
            log.info("idInstitucion............."
                    + request.getParameter("idInstitucion"));
            log.info("idEstatusSolicitud........"
                    + request.getParameter("idEstatusSolicitud"));
            log.info("esAsociadaNumeroExpediente........"
                    + request.getParameter("esAsociadaNumeroExpediente"));
            log.info("idTipoSolicitud........"
                    + request.getParameter("idTipoSolicitud"));
            log.info("esConsultaAsesoria........"
                    + request.getParameter("esConsultaAsesoria"));

            Long idInstitucion = NumberUtils.toLong(
                    request.getParameter("idInstitucion"), 0L);

            Long idEstatus = NumberUtils.toLong(
                    request.getParameter("idEstatusSolicitud"), 0L);

            Long idTipoSolicitud = NumberUtils.toLong(
                    request.getParameter("idTipoSolicitud"), 0L);
            //ASESORIAS ASIGNADAS DE DEFENSOR
            Boolean esConsultaAsesoria = BooleanUtils.toBoolean(request
                    .getParameter("esConsultaAsesoria"));

            Boolean esAsociadaNumeroExpediente = null;

            if (request.getParameter("esAsociadaNumeroExpediente") != null && !request.getParameter("esAsociadaNumeroExpediente").isEmpty()) {
                esAsociadaNumeroExpediente = Boolean.parseBoolean(request.getParameter("esAsociadaNumeroExpediente"));
            }

            SolicitudDTO solFiltro = new SolicitudDTO();

            if (idInstitucion > 0L) {
                solFiltro.setInstitucion(new ConfInstitucionDTO(idInstitucion));
            }
            if (idEstatus > 0L) {
                solFiltro.setEstatus(new ValorDTO(idEstatus));
            }

            if (idTipoSolicitud <= 0L) {
                idTipoSolicitud = TiposSolicitudes.DEFENSOR.getValorId();
            }
            solFiltro.setTipoSolicitudDTO(new ValorDTO(idTipoSolicitud));

            //Filtro del distrito
            Long idDistrito = null;
            UsuarioDTO usuarioFirmado = getUsuarioFirmado(request);
            if (usuarioFirmado.getFuncionario() != null
                    && usuarioFirmado.getFuncionario().getDiscriminante() != null
                    && usuarioFirmado.getFuncionario().getDiscriminante()
                    .getDistrito() != null
                    && usuarioFirmado.getFuncionario().getDiscriminante()
                    .getDistrito().getCatDistritoId() != null) {
                //Se define el registro del distrito en los expedientes				
                idDistrito = usuarioFirmado.getFuncionario().getDiscriminante()
                        .getDistrito().getCatDistritoId();

                log.info("El discirminante obtenido es: " + idDistrito);
            }

            List<SolicitudDTO> listaSolicitudesDefensor = null;

            if (esConsultaAsesoria) {
				//Es asignada, se hace la consulta desde el Defensor
                //Son avisos de Designaci&oacute;n (Asesoria Legal)
                //Se requiere pasar el filtro de Aviso de designacion de la solicitud
                AvisoDesignacionDTO avisoDesignacionFiltroDTO = new AvisoDesignacionDTO();
                avisoDesignacionFiltroDTO.setEstatus(new ValorDTO(EstatusNotificacion.NO_ATENDIDA.getValorId()));

                FuncionarioDTO funcionarioDTO = usuarioFirmado.getFuncionario();
                avisoDesignacionFiltroDTO.setFuncionario(funcionarioDTO);

                solFiltro.setAvisoDesignacionDTO(avisoDesignacionFiltroDTO);
            }

            listaSolicitudesDefensor = solicitudDelegate
                    .consultarSolicitudesPorFiltro(solFiltro, esAsociadaNumeroExpediente, idDistrito);

            if (log.isDebugEnabled()) {
                log.debug("Lista de Sol Poder Judicial"
                        + listaSolicitudesDefensor);
            }

            response.setContentType("text/xml; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter writer = response.getWriter();

            writer.print("<rows>");
            // Configuracion del Paginador
            String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
            writer.print(paginacion);

            if (listaSolicitudesDefensor != null
                    && !listaSolicitudesDefensor.isEmpty()) {
                for (SolicitudDTO solicitudDefensorDto : listaSolicitudesDefensor) {

                    writer.print("<row id='"
                            + solicitudDefensorDto.getDocumentoId() + "'>");

                    writer.print("<cell>"
                            + (solicitudDefensorDto.getFolioSolicitud() != null ? solicitudDefensorDto
                                    .getFolioSolicitud() : "-") + "</cell>");

                    writer.print("<cell>"
                            + ((solicitudDefensorDto.getEstatus() != null
                            && solicitudDefensorDto.getEstatus()
                            .getValor() != null && !solicitudDefensorDto
                            .getEstatus().getValor().isEmpty()) ? solicitudDefensorDto
                                    .getEstatus().getValor() : "-")
                            + "</cell>");

                    writer.print("<cell>"
                            + (solicitudDefensorDto.getNumeroCasoAsociado() != null ? solicitudDefensorDto
                                    .getNumeroCasoAsociado() : "-") + "</cell>");

                    writer.print("<cell>"
                            + (solicitudDefensorDto
                            .getNumeroExpedienteAsociado() != null ? solicitudDefensorDto
                                    .getNumeroExpedienteAsociado() : "-")
                            + "</cell>");

                    writer.print("<cell>"
                            + (solicitudDefensorDto.getNombreSolicitante() != null ? solicitudDefensorDto
                                    .getNombreSolicitante() : "-") + "</cell>");

                    writer.print("<cell>"
                            + DateUtils.formatear(solicitudDefensorDto
                                    .getFechaCreacion())
                            + " - "
                            + DateUtils.formatearHora(solicitudDefensorDto
                                    .getFechaCreacion()) + "</cell>");

                    writer.print("<cell>"
                            + ((solicitudDefensorDto
                            .getExpedienteDTO() != null && solicitudDefensorDto
                            .getExpedienteDTO().getExpedienteId() != null) ? solicitudDefensorDto
                                    .getExpedienteDTO().getExpedienteId() : "-")
                            + "</cell>");

                    if (idTipoSolicitud.equals(TiposSolicitudes.DEFENSOR.getValorId())
                            || idTipoSolicitud.equals(TiposSolicitudes.ASESORIA_DEFENSORIA.getValorId())) {
                        writer.print("<cell>"
                                + ((solicitudDefensorDto
                                .getExpedienteDTO() != null && solicitudDefensorDto
                                .getExpedienteDTO().getNumeroExpedienteId() != null) ? solicitudDefensorDto
                                        .getExpedienteDTO().getNumeroExpedienteId() : "-")
                                + "</cell>");
                    }

//					writer.print("<cell>"
//							+ ((solicitudDefensorDto.getDestinatario() != null
//									&& solicitudDefensorDto.getDestinatario()
//											.getNombreCompleto() != null && !solicitudDefensorDto
//									.getDestinatario().getNombreCompleto()
//									.trim().isEmpty()) ? solicitudDefensorDto
//									.getDestinatario().getNombreCompleto()
//									: "---") + "</cell>");
                    writer.print("<cell>"
                            + ((solicitudDefensorDto.getAvisoDesignacionDTO() != null
                            && solicitudDefensorDto.getAvisoDesignacionDTO().getFuncionario() != null
                            && solicitudDefensorDto.getAvisoDesignacionDTO().getFuncionario()
                            .getNombreCompleto() != null
                            && !solicitudDefensorDto.getAvisoDesignacionDTO().getFuncionario()
                            .getNombreCompleto().trim().isEmpty())
                                    ? solicitudDefensorDto.getAvisoDesignacionDTO().getFuncionario()
                                    .getNombreCompleto().trim()
                                    : "-") + "</cell>");
                    writer.print("</row>");
                }
            }
            writer.print("</rows>");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * M&eacute;todo para validar el estatus de la solicitud 1) Si la solicitud
     * esta abierta solo debe preguntar por la confirmacion 2) Si la solicitud
     * NO esta abierta, 2.1) Verificar si el coordinador es el due&ntilde;o y de
     * ser as&iacute; abre el visor 2.2) Si el usuario no es el due&ntilde;o,
     * entonces pregunta si desea ver el detalle 3) En caso que no se encuentre
     * la solicitud, env&iacute;a mensaje de error en vista
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward validarEstatusSolicitudDeDefensor(
            ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("******VALIDAR ESTATUS SOLICITUD DE DEFENSOR***************");
            log.info("VERIFICANDO PARAMETROS***************");
            log.info("solicitudDefensorId............."
                    + request.getParameter("solicitudDefensorId"));
            Long solicitudDefensorId = NumberUtils.toLong(
                    request.getParameter("solicitudDefensorId"), 0L);

            SolicitudDTO solicitudDto = solicitudDelegate.consultarSolicitudXId(solicitudDefensorId);

            String respuesta = "";

            // Verificar si existe la solicitud
            if (solicitudDto != null) {
                UsuarioDTO usuarioFirmadoDto = super.getUsuarioFirmado(request);

                // Verificar si tenemos inf. del usuario firmado
                if (usuarioFirmadoDto != null
                        && usuarioFirmadoDto.getFuncionario() != null
                        && usuarioFirmadoDto.getFuncionario()
                        .getClaveFuncionario() != null) {

                    // Si la solicitud no tiene destinatario
                    if (solicitudDto.getDestinatario() == null
                            || solicitudDto.getDestinatario()
                            .getClaveFuncionario() == null) {
                        respuesta = CONFIRMAR_SEGUIMIENTO;

                    } else {// Si ya tiene destinatario

						// Verificamos si el destinatario y el usu. firmado son
                        // iguales
                        if (solicitudDto
                                .getDestinatario()
                                .getClaveFuncionario()
                                .equals(usuarioFirmadoDto.getFuncionario()
                                        .getClaveFuncionario())) {
                            respuesta = ABRIR_VISOR;
                        } else { // Si el destinatario no es el usu. firmado

                            if (solicitudDto.getDestinatario()
                                    .getNombreCompleto() != null) {
                                respuesta = ATENCION_COORDINADOR
                                        + " "
                                        + solicitudDto.getDestinatario()
                                        .getNombreCompleto();
                            } else {
                                respuesta = ATENCION_COORDINADOR;
                            }
                        }
                    }

                } else {
                    respuesta = NO_EXISTE_USUARIO;
                }
            } else {
                respuesta = NO_EXISTE_SOLICITUD;
            }

            if (log.isDebugEnabled()) {
                log.debug("respuesta====" + respuesta);
            }
            // Enviamos respuesta a vista la respuesta
            escribirRespuesta(response, respuesta);

        } catch (NSJPNegocioException ex) {
            log.error("Ocurrio un error, por favor contacte a su administrador", ex);
            escribirError(response, ex);
        }
        return null;
    }

    /**
     * M&eacute;todo para cambiar el estatus de la solicitud de defensor y
     * verificar si existe un n&uacute;mero de expediente(de
     * defensor&iacute;a)asociado al caso, al cual pertenece la solicitud; de
     * ser as&iacute;, asocia la solicitud al expediente. En caso contrario
     * genera un nuevo n&uacute;mero de expediente(de defensor&iacute;a) y
     * asocia la solicitud a ese nuevo n&uacute;mero
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward atenderSolicitudDeDefensor(
            ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

            log.info("******ATENDER SOLICITUD DE DEFENSOR**************");
            log.info("VERIFICANDO PARAMETROS***************");
            log.info("solicitudDefensorId............."
                    + request.getParameter("solicitudDefensorId"));
            Long solicitudDefensorId = NumberUtils.toLong(
                    request.getParameter("solicitudDefensorId"), 0L);

            String respuesta = "";

            SolicitudDefensorDTO solDefensor = new SolicitudDefensorDTO();
            solDefensor.setDocumentoId(solicitudDefensorId);

            UsuarioDTO usuarioFirmado = super.getUsuarioFirmado(request);

            respuesta = solicitudDelegate.atenderSolicitudDeDefensor(solDefensor, usuarioFirmado);

            if (log.isDebugEnabled()) {
                log.debug("respuesta====" + respuesta);
            }
            // Enviamos respuesta a vista la respuesta
            escribirRespuesta(response, respuesta);

        } catch (NSJPNegocioException ex) {
            log.error("Ocurrio un error, al intentar atender esta solicitud", ex);
            escribirError(response, ex);
        }
        return null;
    }
}
