package mx.gob.segob.nsjp.web.solicitud.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.delegate.actuaciones.ActuacionesDelegate;
import mx.gob.segob.nsjp.delegate.caso.CasoDelegate;
import mx.gob.segob.nsjp.delegate.delito.DelitoDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.solicitud.form.SolicitudAudienciaForm;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jacob Lobaco
 */
public class SolicitarAudienciaAction extends GenericAction {

    private static final Logger logger =
            Logger.getLogger(SolicitarAudienciaAction.class);
    @Autowired
    private InvolucradoDelegate involucradoDelegate;
    @Autowired
    private ActuacionesDelegate actuacionesDelegate;
    @Autowired
    private CasoDelegate casoDelegate;
    @Autowired
	private DelitoDelegate  delitoDelegate;
    
  //TODO JEFP INCLUIR ESTOS STRINGS EN EL UTILS
    private static final String ANONIMO = "An&oacute;nimo";
    private static final String DELITO_SIN_NOMBRE = "Delito sin nombre";
    private static final String SOLICITUD_ENVIADA = "Solicitud enviada correctamente.";
    
    /**
     * Obtiene el "numeroeExpediente" del expediente que se esta trabajando en
     * sesion.
     * @param request
     * @return
     */
    private String obtenNumeroExpediente(HttpServletRequest request) {
        String numeroExpediente = (String) request.getParameter("numeroExpediente");
        if (logger.isDebugEnabled()) {
            logger.debug("numeroExpediente = " + numeroExpediente);
        }
        return numeroExpediente;
    }

    /**
     * Obtiene el caso asociado al expediente que esta en sesion.
     * @param request
     * @return
     * @throws NSJPNegocioException
     */
    private CasoDTO obtenerCasoPorNumeroExpediente(HttpServletRequest request)
            throws NSJPNegocioException {
        String numeroExpediente = obtenNumeroExpediente(request);
        ExpedienteDTO expedienteDTO = getExpedienteTrabajo(request, numeroExpediente);
        if (logger.isDebugEnabled()) {
            logger.debug("expedienteDTO.getExpedienteId() = " + expedienteDTO.getExpedienteId());
        }
        return expedienteDTO.getCasoDTO();
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
    public ActionForward consultarCasoPorExpediente(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("consultarCasoPorExpediente");
            }
            // Consultamos el caso asociado al expediente que esta en sesion.
            CasoDTO casoDTO = obtenerCasoPorNumeroExpediente(request);
            if (casoDTO == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("No existe una caso asociado al expediente = "
                            + obtenNumeroExpediente(request));
                }
                throw new NSJPNegocioException(CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO);
            } else {
                String xml = converter.toXML(casoDTO);
                escribirRespuesta(response, xml);
                response.setContentType("text/xml");
                PrintWriter pw = response.getWriter();
                pw.print(xml);
                pw.flush();
                pw.close();
            }
        } catch (NSJPNegocioException ex) {
            logger.error("Ocurrio un error en consultarCasoPorExpediente", ex);
            escribirError(response, ex);
        }
        return null;
    }

	public ActionForward consultarInvolucradoPorCalidadCaso(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("consultarInvolucradoPorCalidadCaso");
			}
			// De nuevo obtenemos el caso del expediente en session.
			CasoDTO casoDTO = obtenerCasoPorNumeroExpediente(request);
			UsuarioDTO usuarioFirmado = getUsuarioFirmado(request);

			if (casoDTO == null || usuarioFirmado == null) {
				if (casoDTO == null && logger.isDebugEnabled()) {
					logger.debug("No existe una caso asociado al expediente = "
							+ obtenNumeroExpediente(request));
				}
				if (usuarioFirmado == null && logger.isDebugEnabled()) {
					logger.debug("No se puede recuperar el usuario de session");
				}

				throw new NSJPNegocioException(
						CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO);

			} else {

				List<InvolucradoDTO> involucradosToSend = new ArrayList<InvolucradoDTO>();

				CalidadDTO calidadDTO = new CalidadDTO();
				// Consultamos los involucrados que sean probables responsables.
				calidadDTO.setCalidades(Calidades.PROBABLE_RESPONSABLE_PERSONA);
				calidadDTO.setCalidadId(Calidades.PROBABLE_RESPONSABLE_PERSONA
						.getValorId());

				List<InvolucradoDTO> involucradosProbablesResponsables = involucradoDelegate
						.consultarInvolucradoPorCalidadCaso(casoDTO,
								calidadDTO, usuarioFirmado);

				if (involucradosProbablesResponsables != null
						&& !involucradosProbablesResponsables.isEmpty()) {
					involucradosToSend
							.addAll(involucradosProbablesResponsables);
				}

				// Consultamos los involucrados que sean probables responsables.
				calidadDTO
						.setCalidades(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION);
				calidadDTO
						.setCalidadId(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION
								.getValorId());
				List<InvolucradoDTO> involucradosProbablesResponsablesOrg = involucradoDelegate
						.consultarInvolucradoPorCalidadCaso(casoDTO,
								calidadDTO, usuarioFirmado);

				if (involucradosProbablesResponsablesOrg != null
						&& !involucradosProbablesResponsablesOrg.isEmpty()) {
					involucradosToSend
							.addAll(involucradosProbablesResponsablesOrg);
				}

				// Consultamos a los involucrados que sean victimas.
				calidadDTO.setCalidades(Calidades.VICTIMA_PERSONA);
				calidadDTO.setCalidadId(Calidades.VICTIMA_PERSONA.getValorId());
				List<InvolucradoDTO> involucradosVictimas = involucradoDelegate
						.consultarInvolucradoPorCalidadCaso(casoDTO,
								calidadDTO, usuarioFirmado);

				if (involucradosVictimas != null
						&& !involucradosVictimas.isEmpty()) {
					involucradosToSend.addAll(involucradosVictimas);
				}

				// Consultar los Denunciantes que son víctimas
				calidadDTO.setCalidades(Calidades.DENUNCIANTE);
				calidadDTO.setCalidadId(Calidades.DENUNCIANTE.getValorId());
				List<InvolucradoDTO> involucradosDenunciantesVictimas = involucradoDelegate
						.consultarInvolucradoPorCalidadCaso(casoDTO,
								calidadDTO, usuarioFirmado);

				if (involucradosDenunciantesVictimas != null
						&& !involucradosDenunciantesVictimas.isEmpty()) {
					for (InvolucradoDTO involucradoDTO : involucradosDenunciantesVictimas) {
						// Validar si es víctima
						if (involucradoDTO.getCondicion()
								.equals(new Short("1"))) {
							involucradosToSend.add(involucradoDTO);
						}
					}
				}

				converter.alias("involucrado", InvolucradoDTO.class);
				converter
						.alias("nombreDemografico", NombreDemograficoDTO.class);
				converter.alias("organizacion", OrganizacionDTO.class);

				String xml = converter.toXML(involucradosToSend);

				if (logger.isDebugEnabled()) {
					logger.debug("involucrados = " + xml);
				}
				escribirRespuesta(response, xml);
				response.setContentType("text/xml");
				PrintWriter pw = response.getWriter();
				pw.print(xml);
				pw.flush();
				pw.close();
			}
		} catch (NSJPNegocioException ex) {
			logger.error("Ocurrio un error al consultarInvolucradoPor"
					+ "CalidadCaso", ex);
			escribirError(response, ex);
		}
		return null;
	}

	/**
	 * M&eacute;todo que consulta los probables responsables, de un expediente
	 * que tienen almenos una relaci&oacute;n Delito-Persona
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarProbResParaSolicitarAudiencia(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			logger.debug("EJECUTANDO ACTION consultarProbResParaSolicitarAudiencia");
			// De nuevo obtenemos el caso del expediente en session.
			CasoDTO casoDTO = obtenerCasoPorNumeroExpediente(request);
			UsuarioDTO usuarioFirmado = getUsuarioFirmado(request);
			List<InvolucradoDTO> probablesResponsablesConRelDelPer = null;

			if (casoDTO == null || usuarioFirmado == null) {
				if (casoDTO == null && logger.isDebugEnabled()) {
					logger.debug("No existe una caso asociado al expediente = "
							+ obtenNumeroExpediente(request));
				}
				if (usuarioFirmado == null && logger.isDebugEnabled()) {
					logger.debug("No se puede recuperar el usuario de session");
				}

				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			probablesResponsablesConRelDelPer = involucradoDelegate
					.consultarProbResParaSolicitarAudienciaPorCaso(casoDTO,
							usuarioFirmado);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			PaginadorUtil.obtenerPaginacionManual(probablesResponsablesConRelDelPer);
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (probablesResponsablesConRelDelPer != null
					&& !probablesResponsablesConRelDelPer.isEmpty()) {
				for (InvolucradoDTO invo : probablesResponsablesConRelDelPer) {

					writer.print("<row id='" + invo.getElementoId() + "'>");

					writer.print("<cell><![CDATA[<div class='celdaGrid'>"
							+ ((invo.getNombreCompleto() != null && !invo
									.getNombreCompleto().trim().isEmpty()) ? invo
									.getNombreCompleto() : ANONIMO)
							+ " </div>]]></cell>");

					writer.print("</row>");
				}
			}

			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (NSJPNegocioException ex) {
			logger.error("Ocurrio un error en validarExisteCaso", ex);
			escribirError(response, ex);
		}
		return null;
	}

	
	/**
	 *M&eacute;todo para consultar las relaciones delito persona por probable
	 *responsable seleccionado en el Grid de PR 
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, Se regresa en formato XML
	 * @throws IOException
	 */
	public ActionForward consultarRelDelitoPersonaPorProbableResponsable(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			logger.debug("EJECUTANDO ACTION consultarRelDelitoPersonaPorProbableResponsable");
			logger.debug("VERIFICANDO PARAMETRO..............................");
			logger.debug("Prob. resp Id=" + request.getParameter("probRespId"));
			logger.debug(" idExpediente=" + request.getParameter("idExpediente"));
			
			Long probRespId = NumberUtils.toLong(
					request.getParameter("probRespId"), 0L);
			Long idExpediente = NumberUtils.toLong(
					request.getParameter("idExpediente"), 0L);

			List<DelitoPersonaDTO> listaRelDelPerXInv = delitoDelegate
					.consultarDelitosVictimaPorImputado(probRespId,
							idExpediente);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			PaginadorUtil.obtenerPaginacionManual(listaRelDelPerXInv);
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (listaRelDelPerXInv != null && !listaRelDelPerXInv.isEmpty()) {
				for (DelitoPersonaDTO delitoPersonaDTO : listaRelDelPerXInv) {

					writer.print("<row id='"
							+ delitoPersonaDTO.getDelitoPersonaId() + "'>");

					// Nombre del probable responsable
					writer.print("<cell><![CDATA[<div class='celdaGrid'>"
							+ ((delitoPersonaDTO.getProbableResponsable()
									.getNombreCompleto() != null && !delitoPersonaDTO
									.getProbableResponsable()
									.getNombreCompleto().trim().isEmpty()) ? delitoPersonaDTO
									.getProbableResponsable()
									.getNombreCompleto() : ANONIMO)
							+ " </div>]]></cell>");

					// Nombre del Delito
					writer.print("<cell><![CDATA[<div class='celdaGrid'>"
							+ ((delitoPersonaDTO.getDelito().getCatDelitoDTO() != null
									&& delitoPersonaDTO.getDelito()
											.getCatDelitoDTO().getNombre() != null && !delitoPersonaDTO
									.getDelito().getCatDelitoDTO().getNombre()
									.trim().isEmpty()) ? delitoPersonaDTO
									.getDelito().getCatDelitoDTO().getNombre()
									: DELITO_SIN_NOMBRE) + " </div>]]></cell>");

					// Nombre de la Victima
					writer.print("<cell><![CDATA[<div class='celdaGrid'>"
							+ ((delitoPersonaDTO.getVictima()
									.getNombreCompleto() != null && !delitoPersonaDTO
									.getVictima().getNombreCompleto().trim()
									.isEmpty()) ? delitoPersonaDTO.getVictima()
									.getNombreCompleto() : ANONIMO)
							+ " </div>]]></cell>");

					writer.print("</row>");
				}
			}

			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (NSJPNegocioException ex) {
			logger.error("Ocurrio un error en validarExisteCaso", ex);
			escribirError(response, ex);
		}
		return null;
	}
	
	
    public ActionForward validarExisteCaso(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String numeroDeCaso = request.getParameter("numeroDeCaso");
            if (logger.isDebugEnabled()) {
                logger.debug("numeroDeCaso = " + numeroDeCaso);
            }
            if (numeroDeCaso != null) {
                CasoDTO casoDTO = new CasoDTO();
                casoDTO.setNumeroGeneralCaso(numeroDeCaso);
                List<CasoDTO> casos = casoDelegate.consultarCasoPorNumero(casoDTO);
                Boolean existeCaso = casos != null && casos.size() > 0;
                String existeCasoXML = converter.toXML(existeCaso);
                escribirRespuesta(response, existeCasoXML);
            }
        } catch (NSJPNegocioException ex) {
            logger.error("Ocurrio un error en validarExisteCaso", ex);
            escribirError(response, ex);
        }
        return null;
    }

	/**
	 * Env&iacute;a la solicutd de audiencia a la instituci&oacute;n de poder
	 * judicial y actualmente es invocado en defensor&iacutea; y
	 * procuradur&iacute;a
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward enviarSolicitudAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("enviarSolicitudAudiencia");
		}
		if (form != null) {
			
			try {
				
				SolicitudAudienciaForm forma = (SolicitudAudienciaForm) form;
				
				//Imputados seleccionados para la solicitud de audiencia
				String imputadosSolAudiencia = forma.getImputadosSolAudiencia();

				if (logger.isDebugEnabled()) {
					logger.debug("forma.getImputadosSolAudiencia() = "
							+ forma.getImputadosSolAudiencia());
				}
				List<InvolucradoDTO> listaImputadosSolAudiencia = obtenerIdsImputadosAudiencia(imputadosSolAudiencia);

				if (logger.isDebugEnabled()) {
					logger.debug("Se obtuvo la forma = " + forma);
				}
				
				SolicitudAudienciaDTO solicitudAudienciaDTO = new SolicitudAudienciaDTO();
				
				//Setear tipo de audiencia de soliciitada
				AudienciaDTO audienciaDTO = new AudienciaDTO();
				audienciaDTO.setTipoAudiencia(new ValorDTO(forma.getTipoDeAudiencia()));
				
				//Seteamos los imputados a la audiencia
				audienciaDTO.setInvolucrados(listaImputadosSolAudiencia);
				solicitudAudienciaDTO.setAudiencia(audienciaDTO);
				
				
				//Setear el tipo de solicitud
				solicitudAudienciaDTO.setTipoSolicitudDTO(new ValorDTO(
						TiposSolicitudes.AUDIENCIA.getValorId()));
				
				//Setear Area Actual del usuario firmado
				AreaDTO areaActual = getUsuarioFirmado(request).getAreaActual();
				
				if (areaActual != null) {
					solicitudAudienciaDTO.setAreaOrigen(areaActual.getAreaId());
				}
				
				// Setear la institucion
				UsuarioDTO usuarioFirmado = getUsuarioFirmado(request);
				if (usuarioFirmado.getFuncionario() != null
						&& usuarioFirmado.getFuncionario().getDepartamento() != null
						&& usuarioFirmado.getFuncionario().getDepartamento()
								.getArea() != null) {
					forma.setInstitucionSolicitante(usuarioFirmado
							.getFuncionario().getDepartamento().getArea()
							.getAreaId());
				}

				if (logger.isDebugEnabled()) {
					logger.debug("forma.getInstitucionSolicitante() = "
							+ forma.getInstitucionSolicitante());
				}
				solicitudAudienciaDTO.setInstitucion(null);	//TODO AGA VERIFICAR ESTE CAMPO
				
				//Fundamento de la solicitud
				solicitudAudienciaDTO.setMotivo(forma
						.getFundamentoDeLaSolicitud());
				
				//Setar el nombre del solicitante
				solicitudAudienciaDTO.setNombreSolicitante(forma
						.getNombreDelSolicitante());
				
				//Setear el numero de caso asociado
				solicitudAudienciaDTO.setNumeroCasoAsociado(forma
						.getNumeroDeCaso());
								
				//Setear la fecha de creacion String
				solicitudAudienciaDTO.setStrFechaCreacion(new Date() + "");
				
				//Setear la hora de creacion String 
				solicitudAudienciaDTO.setStrHoraCreacion(System
						.currentTimeMillis() + "");
				
				//Setear la fecha de creacion (va la fecha y la hora)
				solicitudAudienciaDTO.setFechaCreacion(new Date());
				
				
				//Setear la fecha limite (va la fecha y la hora hh:mm)
				// 15/06/2011 14:22
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				
				String fechaCompleta = forma.getFechaLimiteAudiencia() + " "
						+ forma.getHoraLimiteAudiencia();
				
				//Setear la fecha limite de la audiencia
				solicitudAudienciaDTO.setStrFechaLimite(forma
						.getFechaLimiteAudiencia());
				
				try {
					Date fechaLimite = df.parse(fechaCompleta);
					solicitudAudienciaDTO.setFechaLimite(fechaLimite);
					if (logger.isDebugEnabled()) {
						logger.debug("fechaLimite = " + fechaLimite);
					}
				} catch (ParseException ex) {
					logger.error("Error de formato (dd/MM/yyyy HH:mm) con la fecha: "
							+ fechaCompleta);
				}
				
				//Setear el String Hora Limite audiencia
				solicitudAudienciaDTO.setStrHoraLimite(forma
						.getHoraLimiteAudiencia());
				
				//Setear solicitante externo
				solicitudAudienciaDTO.setSolicitanteExterno(usuarioFirmado
						.getFuncionario().getClaveFuncionario());
				
				//Setear usuario solicitante
				solicitudAudienciaDTO.setUsuarioSolicitante(usuarioFirmado
						.getFuncionario());
				
				
				if (usuarioFirmado != null
						&& usuarioFirmado.getFuncionario() != null
						&& usuarioFirmado.getFuncionario().getPuesto() != null
						&& usuarioFirmado.getFuncionario().getPuesto()
								.getValor() != null) {
					solicitudAudienciaDTO
							.getUsuarioSolicitante()
							.getPuesto()
							.setNombreCampo(
									usuarioFirmado.getFuncionario().getPuesto()
											.getValor());
				}
				if (usuarioFirmado != null
						&& usuarioFirmado.getFuncionario() != null
						&& usuarioFirmado.getFuncionario()
								.getJerarquiaOrganizacional() != null
						&& usuarioFirmado.getFuncionario()
								.getJerarquiaOrganizacional().getNombre() != null) {
					solicitudAudienciaDTO
							.getUsuarioSolicitante()
							.getJerarquiaOrganizacional()
							.setNombre(
									usuarioFirmado.getFuncionario()
											.getJerarquiaOrganizacional()
											.getNombre());
				}

				ExpedienteDTO expedienteDTO = new ExpedienteDTO();
				expedienteDTO.setNumeroExpedienteId(NumberUtils.toLong(forma
						.getIdNumeroExpediente()));
				
				/* Por default no se pide que se consulten
				 * expedienteDTO.setEsEscritura(false);
				 * expedienteDTO.setInvolucradosSolicitados(false);
				 * expedienteDTO.setDomicliosInvolucradoSolicitados(false);
				 * expedienteDTO.setImagenesAsociadasAElementos(false);
				 * expedienteDTO.setMedidasCautelaresSolicitadas(false);
				 */
				
				expedienteDTO.setUsuario(usuarioFirmado);
				
				/**
				 * Se agrega lo referente al envío de solicitud
				 */
				if (logger.isDebugEnabled()) {
					logger.debug("**********************************forma.getDistrito() = "
							+ forma.getDistrito());
				}
				if (logger.isDebugEnabled()) {
					logger.debug("**********************************forma.getTribunal() = "
							+ forma.getTribunal());
				}

				if (logger.isDebugEnabled()) {
					logger.debug("**********************************forma.getFuncionarioDestinatario() = "
							+ forma.getFuncionarioDestinatario());
				}

				String respuesta = "";

				logger.info("enviarSolicitudAudiencia - areaUsuarioFirmado : "
						+ usuarioFirmado.getFuncionario()
								.getJerarquiaOrganizacional()
								.getJerarquiaOrganizacionalId());

				
				// se obtienen
				Long idDistrito = forma.getDistrito();
				Long idTribunal = forma.getTribunal();
				Long idClaveFuncionario = forma.getFuncionarioDestinatario();
				
				// Seteamos el funcionario externo para que se guarde en la
				// solicitud que guardaremos de manera local
				if (forma.getNombreCompletoDestinatarioInstExterna() != null
						&& !forma.getNombreCompletoDestinatarioInstExterna().trim()
								.isEmpty()) {
					FuncionarioExternoDTO FuncionarioExternoDto = new FuncionarioExternoDTO();
					
					String nombreCompleto[] = forma
							.getNombreCompletoDestinatarioInstExterna().split(
									":");
					
					if (nombreCompleto != null && nombreCompleto.length >= 1
							&& idClaveFuncionario != null
							&& idClaveFuncionario > 0L) {
						if (nombreCompleto[0] != null
								&& !nombreCompleto[0].trim().isEmpty()) {
							FuncionarioExternoDto.setNombre(nombreCompleto[0]);
							if (nombreCompleto[1] != null
									&& !nombreCompleto[1].trim().isEmpty()) {
								FuncionarioExternoDto
										.setApellidoPaterno(nombreCompleto[1]);
								if (nombreCompleto.length > 1
										&& nombreCompleto[2] != null
										&& !nombreCompleto[2].trim().isEmpty()) {
									FuncionarioExternoDto
											.setApellidoMaterno(nombreCompleto[2]);
								}
								
								FuncionarioExternoDto
										.setCveFuncionarioInstExt(idClaveFuncionario);

								//Se agrega en codigo duro ya que el usuario que se consulta en vista
								//al que se envia la solicitud es forsozamente admonPI
								//y la institucion es PJ 
								FuncionarioExternoDto.setArea(Areas.ADMINISTRACION_JUDICIAL.name());
								ConfInstitucionDTO confInstitucionDTO = new ConfInstitucionDTO();
								confInstitucionDTO.setConfInstitucionId(Instituciones.PJ.getValorId());
								FuncionarioExternoDto.setConfInstitucionDTO(confInstitucionDTO);
								solicitudAudienciaDTO
										.setDestinatarioInstExterna(FuncionarioExternoDto);
							}
						}
					}
				}
				
				//Se envia la solicitud con la informacion necesaria para 
				//registrar el caso, en caso de que no exista en la 
				//institucion destino
				actuacionesDelegate.enviarSolicitudDeAudiencia(
						solicitudAudienciaDTO, expedienteDTO, idDistrito,
						idTribunal, idClaveFuncionario);
			
				respuesta = SOLICITUD_ENVIADA;
				
				//Enviamos respuesta a vista la respuesta
				escribirRespuesta(response, respuesta);

			} catch (NSJPNegocioException ex) {
				logger.error("Ocurrio un error en enviarSolicitudAudiencia", ex);
				escribirError(response, ex);
			}
		}
		return null;
	}
	
	/**
	 * M&eacute;todo utilizado para obtener una lista de involucrados a partir 
	 * del String de la selecci&oacute;n del grid de probables responsables
	 * 
	 * @param imputadosSolAudiencia, String con la selecci&acute;n de invos
	 * @return List<InvolucradoDTO>, Lista de involucrados a partir de su Id
	 * @throws NSJPNegocioException
	 */
	private List<InvolucradoDTO> obtenerIdsImputadosAudiencia(
			String imputadosSolAudiencia) throws NSJPNegocioException {

		List<InvolucradoDTO> listaImputadosAudiencia = null;

		try {
			if (imputadosSolAudiencia != null
					&& !imputadosSolAudiencia.trim().isEmpty()) {

				listaImputadosAudiencia = new ArrayList<InvolucradoDTO>();
				String listaIds[] = imputadosSolAudiencia.split(",");

				for (String id : listaIds) {

					Long involucradoId = NumberUtils.toLong(id, 0L);

					if (involucradoId.equals(0L)) {
						logger.error("La lista de los id´s de imputados contiene un Id nulo");
						throw new NSJPNegocioException(
								CodigoError.INFORMACION_PARAMETROS_ERRONEA);
					}

					InvolucradoDTO imputado = new InvolucradoDTO(involucradoId);
					imputado.setElementoId(involucradoId);
					listaImputadosAudiencia.add(imputado);
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getCause(), e);
		}
		return listaImputadosAudiencia;
	}

    public ActionForward validarPlazoConstitucionalDeAudiencia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        return null;
    }
}
