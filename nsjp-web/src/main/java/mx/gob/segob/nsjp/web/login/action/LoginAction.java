/**
 * Nombre del Programa 	: LoginAction.java
 * Autor               	: Jose Luis
 * Compania            	: Ultrasist
 * Proyecto            	: NSJP              			Fecha:02/02/2011
 * Marca de cambio     	: N/A
 * Descripcion General   : Clase login ejemplo xstream
 * Programa Dependiente  : N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion    : N/A
 * Dias de ejecucion     : N/A                             Horario: N/A
 *                               MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                 :N/A
 * Compania              :N/A
 * Proyecto              :N/A                                   Fecha: N/A
 * Modificacion          :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.web.login.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.menu.TipoMenu;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.configuracion.ConfiguracionDelegate;
import mx.gob.segob.nsjp.delegate.elementomenu.ElementoMenuDelegate;
import mx.gob.segob.nsjp.delegate.parametro.ParametroDelegate;
import mx.gob.segob.nsjp.delegate.usuario.UsuarioDelegate;
import mx.gob.segob.nsjp.delegate.visitante.VisitanteDelegate;
import mx.gob.segob.nsjp.dto.ParametroDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO;
import mx.gob.segob.nsjp.dto.elementomenu.ElementoMenuDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.usuario.FuncionDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioRolDTO;
import mx.gob.segob.nsjp.dto.visitante.VisitanteDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.base.listener.ManejadorSesion;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.kaptcha.Constants;

/**
 * Clase action login ejemplo
 * 
 * @version 1.0
 * @author AndresGG
 */
public class LoginAction extends GenericAction {
	/**
	 * Logger.
	 */
	private static final Logger log = Logger.getLogger(LoginAction.class);
        
        
        private static ConfiguracionDTO cfg;
        
	@Autowired
	private UsuarioDelegate usuarioDelegate;
	
	@Autowired
	private ConfiguracionDelegate configDelegate;
	
	@Autowired
	private ParametroDelegate parametroDelegate;
	
	@Autowired
	private VisitanteDelegate visitanteDelegate;
	
	@Autowired
	private ElementoMenuDelegate elementoMenuDelegate;
	
	/**
	 * Logout.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
	//	
		try {
			UsuarioDTO usuarioDTO = getUsuarioFirmado(request);
                        
                        this.recuperarConfiguracionGlobal(request);
                        
                        if(usuarioDTO==null)
                            return mapping.findForward("success");
                        HttpSession httpSession = request.getSession();
			this.usuarioDelegate.logout(usuarioDTO);
			httpSession.removeAttribute(KEY_SESSION_USUARIO_FIRMADO);
			ManejadorSesion.invalidate(KEY_SESSION_USUARIO_FIRMADO);
			//httpSession.invalidate();

                        log.info("Logout de sistema");
			return mapping.findForward("success");
		} catch (Exception e) {
			// return mapping.findForward("success");
			log.info("Logout de sistema fallido con excepcion mensaje:"
					+ e.getMessage());
			
			return mapping.findForward("error");
		}
	}

	public ActionForward security(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String ip = (String) request.getRemoteHost();

		VisitanteDTO visitanteDTO = new VisitanteDTO(ip, 0);
		try {
			visitanteDTO = visitanteDelegate.consultarVisitantePorIP(visitanteDTO);
			// visitanteDTO.setiIntentos(3);
			log.info("Los intentos de la IP " + visitanteDTO.getcIP()
					+ " son: " + visitanteDTO.getiIntentos());
			//FIXME Se recupera los datos de la configruaci�n Global
			this.recuperarConfiguracionGlobal(request);
			
			if (visitanteDTO != null) {
				if (visitanteDTO.getiIntentos() != null) {
					if (visitanteDTO.getiIntentos() > 10) {
						request.setAttribute("ip", ip);
						// El bloqueo por IP esta; deshabilitado para usuarios
						// desconocidos
						// return mapping.findForward("fail");
						return mapping.findForward("success");
					}
				}
			}
			
			return mapping.findForward("success");

		} catch (Exception e) {
			log.info("No se encontro la IP");

			log.info("visitanteDTO: " + visitanteDTO);
			log.info("visitanteDTO.ip: " + visitanteDTO.getcIP());
			log.info("visitanteDTO.intentos: " + visitanteDTO.getiIntentos());

			return mapping.findForward("error");
		}

	}

	static private Map<String, String> mapaUsuarios = null;

	/**
	 * Login.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public ActionForward findUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {


		try {
	
			DynaActionForm forma = (DynaActionForm) form;

			String username = (String) forma.get("username");
			String password = (String) forma.get("password");
			String scaptcha = (String) forma.get("scaptcha");
			String captcha = request.getParameter("captcha");
			String ip = (String) request.getRemoteHost();
			String idSesion = request.getRequestedSessionId();
			VisitanteDTO visitanteDTO = new VisitanteDTO(ip, 0);
                        
                        this.recuperarConfiguracionGlobal(request);			
			
                        visitanteDTO = visitanteDelegate
					.consultarVisitantePorIP(visitanteDTO);
			if (visitanteDTO.getiIntentos() != null) {
				if (visitanteDTO.getiIntentos() > 10) {
					request.setAttribute("ip", ip);
					// return mapping.findForward("fail");
				}
			}
			// log.debug("Password: " + password);
			if (username != null && !username.trim().isEmpty()) {
				request.setAttribute("nombreUsuario", username);
				// validar la sesi�n de un usuario
				UsuarioDTO usuarioEnSesion = new UsuarioDTO(username,
						password, ip, idSesion); 
					
				usuarioEnSesion = this.usuarioDelegate
						.validarSesionUsuario(usuarioEnSesion);

				if (captcha != null && usuarioEnSesion != null) {
					if (!captcha.equals("null")) {
						if (Integer.valueOf(captcha) >= 1) {
							String kaptchaExpected = (String) request
									.getSession().getAttribute(
											Constants.KAPTCHA_SESSION_KEY);
							if (scaptcha != null && kaptchaExpected != null
									&& kaptchaExpected.equals(scaptcha)) {
								request.setAttribute("captcha", 0);
								ManejadorSesion.invalidate(usuarioEnSesion
										.getIdSesion());
								this.usuarioDelegate.logout(usuarioEnSesion);
								usuarioEnSesion = null;
							} else {
								request.setAttribute("error", 3);
								request.setAttribute("captcha", 2);
								ManejadorSesion.invalidate(usuarioEnSesion
										.getIdSesion());
								return mapping.findForward("success");
							}
						}
					}
				}
				if (usuarioEnSesion == null) {

					UsuarioDTO usuarioFirmado = new UsuarioDTO(username, password, ip,
							idSesion); 
					usuarioFirmado= this.usuarioDelegate.login(usuarioFirmado);
					

					if (usuarioFirmado != null) {
						List<UsuarioRolDTO> list = new ArrayList<UsuarioRolDTO>();
						for (UsuarioRolDTO usuarioRolDTO : usuarioFirmado
								.getUsuarioRoles()) {
							list.add(usuarioRolDTO);
						}

						if (!list.isEmpty()) {
							Collections.sort(list, UsuarioRolDTO.COMPARA_ROL);
						}
						Set<UsuarioRolDTO> setUs = new HashSet<UsuarioRolDTO>();
						for (UsuarioRolDTO usuarioRolDTO : list) {
							log.debug("Orden roles:: "
									+ usuarioRolDTO.getRol().getNombreRol());
							setUs.add(usuarioRolDTO);
						}
						usuarioFirmado.setUsuarioRoles(setUs);

						log.debug("usuarioFirmado.tama�o en roles:: "
								+ usuarioFirmado.getUsuarioRoles().size());
						if (usuarioFirmado.getiSesion() == 1) {// Validacion
																// para
																// numero
																// de sesiones
							log.debug("usuarioFirmado.getAreaActual().getAreaId() :: "
									+ usuarioFirmado.getAreaActual()
											.getAreaId());

							inicializarFwds();
							// Este codigo solia utilizarse para saber el rol
							// principal, pero se descarta por la funcionalidad
							// de multi roll
							log.debug("NombreRol :: ["
									+ usuarioFirmado.getRolPrincipal().getRol()
											.getNombreRol() + "]");
							// String forward = mapaUsuarios.get(usuarioFirmado
							// .getRolPrincipal().getRol().getNombreRol());
							String forward = null;
							String rolActivo = "";
							Long idJerarquia = 0L;
							AreaDTO areaDTO = new AreaDTO();
							if (usuarioFirmado.getUsuarioRoles() != null
									&& !usuarioFirmado.getUsuarioRoles()
											.isEmpty()) {
								for (UsuarioRolDTO element : usuarioFirmado
										.getUsuarioRoles()) {
									if (element.getEsPrincipal()) {
										log.debug("usuarioFirmado.rolerpincipal:: "
												+ element.getRol()
														.getNombreRol());
										if(element.getRol().getRolPadre()==null){
											forward = mapaUsuarios.get(element
													.getRol().getNombreRol());
											
										}else{
											element.getRol().getRolPadre().setNombreRolPadre(element
													.getRol().getRolPadre().getNombreRol());
											element.getRol().getRolPadre().setNombreRol("");
											forward = mapaUsuarios.get(element
													.getRol().getRolPadre().getNombreRolPadre());
											
										}
										rolActivo = element.getRol()
												.getNombreRol();
										log.debug("usuarioFirmado.rolerpincipaljerarquia:: "
												+ element
														.getRol()
														.getJerarquiaOrganizacionalDTO()
														.getJerarquiaOrganizacionalId());
										idJerarquia = element
												.getRol()
												.getJerarquiaOrganizacionalDTO()
												.getJerarquiaOrganizacionalId();
										areaDTO = new AreaDTO(
												element.getRol()
														.getJerarquiaOrganizacionalDTO()
														.getJerarquiaOrganizacionalId());
										areaDTO.setNombre(element
												.getRol()
												.getJerarquiaOrganizacionalDTO()
												.getNombre());
									}
								}
							}

							usuarioFirmado.setAreaActual(areaDTO);
							usuarioFirmado.getFuncionario()
									.getJerarquiaOrganizacional()
									.setJerarquiaOrganizacionalId(idJerarquia);
							usuarioFirmado.setRolActivo(rolActivo);
							log.debug("forward :: " + forward);
							for (UsuarioRolDTO usuarioRolDTO : usuarioFirmado.getUsuarioRoles()) {
								if(usuarioRolDTO.getRol()!=null && usuarioRolDTO.getRol().getRolPadre()!=null){
									usuarioRolDTO.getRol().getRolPadre().setNombreRolPadre(usuarioRolDTO.getRol().getRolPadre().getNombreRol());
									usuarioRolDTO.getRol().getRolPadre().setNombreRol(null);
								}
								
							}
							
							if (forward == null) {								
								request.setAttribute("error", 0);
								ManejadorSesion.invalidate(idSesion);
								return mapping.findForward("success");
								
							}
							request.getSession()
									.setAttribute(KEY_SESSION_USUARIO_FIRMADO,
											usuarioFirmado);

//							this.recuperarConfiguracionGlobal(request);
							RolDTO rolDTO = usuarioFirmado.getRolACtivo().getRol();
							request.getSession()
							.setAttribute(KEY_SESSION_MENU_DINAMICO_IZQUIERDO, this.getMenuInicial(rolDTO, TipoMenu.IZQUIERDO));

							request.getSession()
							.setAttribute(KEY_SESSION_MENU_DINAMICO_SUPERIOR, this.getMenuInicial(rolDTO, TipoMenu.ARRIBA));
							
							return mapping.findForward(forward);
						} else {// en caso de que la sesion no sea la primera se
								// mandara
								// al request un error de tipo dos que indica
								// que el
								// usuario esta bloqueado por m�s de un intento
								// de
								// loguear
							if (usuarioFirmado.getiSesion() == -1) {
								request.setAttribute("error", 0);
							} else {
								request.setAttribute("error", 2);
							}
							return mapping.findForward("success");

						}

					} else {
						visitanteDTO = new VisitanteDTO(ip, 0);
						visitanteDelegate.registrarVisitante(visitanteDTO);
						visitanteDTO = visitanteDelegate
								.consultarVisitantePorIP(visitanteDTO);
						if (visitanteDTO.getiIntentos() != null) {
							if (visitanteDTO.getiIntentos() > 10) {
								request.setAttribute("ip", ip);
								// Inhabilitado el bloqueo por intentos con
								// usuario
								// desconocido
								// return mapping.findForward("fail");
								ManejadorSesion.invalidate(idSesion);
								return mapping.findForward("success");
							} else {
								ManejadorSesion.invalidate(idSesion);
								request.setAttribute("error", 0);
								return mapping.findForward("success");
							}
						} else {
							request.setAttribute("error", 2);
						}
						return mapping.findForward("success");
					}

				} else {
					request.setAttribute("captcha", 1);
					//ManejadorSesion.invalidate(idSesion);
					return mapping.findForward("success");
				}
			} else {
				request.setAttribute("error", 1);
				request.setAttribute("captcha", 0);
                                //ManejadorSesion.invalidate(idSesion);
				return mapping.findForward("success");

			}

		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
			request.setAttribute("error", 0);
			return mapping.findForward("success");
		}
	}

	/**
	 * Recupera la configuracion global.
	 * 
	 * @param request
	 */
	private void recuperarConfiguracionGlobal(HttpServletRequest request) {
                if(cfg == null)
                    cfg  = this.configDelegate.obtgenerConfiguracionGlobal();
                
		log.debug("Subiendo la configuracion a la sesion");
		HttpSession ses = request.getSession();
                
		ses.setAttribute(KEY_SESSION_CONFIGURACION_GLOBAL, cfg);

		// SUBE A SESION EL LOCAL DE DONDE TOMARA EL PROPERTIES CORRESPONDIENTE
		// A CADA ESTADO
		Locale locale = null;
		if (cfg.getEntidadFederativaDespliegue() != null
				&& !cfg.getEntidadFederativaDespliegue().isEmpty()) {
			locale = new Locale("es", "MX",
					cfg.getEntidadFederativaDespliegue());
		} else {
			locale = new Locale("es", "MX");
		}

		ses.setAttribute(KEY_SESSION_ACTION_LOCALE, locale);
		//
		try {
			ParametroDTO parametro_Habilitar_Turno = parametroDelegate
					.consultarParametro(Parametros.HABILITAR_TURNO);
			if (parametro_Habilitar_Turno != null
					&& parametro_Habilitar_Turno.getValor() != null)
				ses.setAttribute(KEY_SESSION_HABILITAR_TURNO,
						parametro_Habilitar_Turno.getValor());
		} catch (NSJPNegocioException e) {
			log.info(e.getMessage(), e);
		} finally {
			if (ses.getAttribute(KEY_SESSION_HABILITAR_TURNO) == null)
				// En caso de que no se encuentre el valor en BD
				ses.setAttribute(KEY_SESSION_HABILITAR_TURNO, "0");
		}
	}

	public boolean autorizarNavegacion(FuncionDTO fncDTO, UsuarioDTO usrDTO) {
		boolean resp = false;
		try {
			resp = usuarioDelegate.validarFuncionXUsuario(usrDTO, fncDTO);
			if (resp == false) {
				log.debug("RECURSO DENEGADO:" + fncDTO.getNombreFuncion()
						+ " AL USUARIO: " + usrDTO.getClaveUsuario()
						+ " CON EL ROL: " + usrDTO.getRolACtivo().getRol().getNombreRol());
                    	}
			resp=true;
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}

		return resp;
		
	}

	/**
	 * Inicializa los forwards para cada rol. <br>
	 * <b> Al crear una nueva pagina index se debe crear un <code>Rol</code> en
	 * base de datos.
	 * 
	 * @return
	 */
	private void inicializarFwds() {
		if (mapaUsuarios == null) {
			mapaUsuarios = new HashMap<String, String>();

			// Revisar si se quedara este nombre de usuario de administrador
			mapaUsuarios.put("administrador", "administrador");

			mapaUsuarios.put("reginicial", "atencionTemprana");
			
			mapaUsuarios.put("visorTurno", "visorTurno");

			mapaUsuarios.put("adminat", "atencionTempranaAdministrativa");
			mapaUsuarios.put("atpenal", "atencionTempranaPenal");
			mapaUsuarios.put("visitador", "visitaduria");
			mapaUsuarios.put("coordinadorVIS", "coorVisitaduria");
			mapaUsuarios.put("facilitador", "alternativaRestaurativa");
			mapaUsuarios.put("coordinadorJAR", "alternativaRestaurativaCoor");
			mapaUsuarios.put("agentemp", "unidadInvestigacion");
			mapaUsuarios.put("coordinadorAmp", "coordinadorAMP");
			mapaUsuarios.put("coordinadorAmpGeneral", "coordinadorAMPGeneral");
			mapaUsuarios.put("policiaMinister", "sspPoliciaDos");
			mapaUsuarios.put("uavd", "uavd");
			mapaUsuarios.put("uavdAtnPsicologica", "uavdpsicologico");
			mapaUsuarios.put("uavdTrabajoSocial", "uavdtsocial");
			mapaUsuarios.put("uavdJuridico", "uavdjuridico");
			mapaUsuarios.put("almacenv", "almacen");
			mapaUsuarios.put("peritoAmp", "peritoAMP");
			mapaUsuarios.put("unidadCapturaPG", "unidadCapturaDos");
			mapaUsuarios.put("coordinadorAT", "coordinadorAT");
			mapaUsuarios.put("directorAprehension", "direccionAprehensiones");

			// Inicia Defensor�a
			mapaUsuarios.put("coordinadorDef", "coordinadorDefensor");
			mapaUsuarios.put("defensor", "defensoria");
			mapaUsuarios.put("defensorAte", "defensorAtencion");
			mapaUsuarios.put("coordinadorPer", "coordinadorPer");
			mapaUsuarios.put("peritoDef", "peritoDef");
			// Fin Defensor�a
			// Inicio Poder Judicial
			mapaUsuarios.put("encargadoDGEPMC", "encargadoDGEPMC");
			mapaUsuarios.put("sspPolicia", "sspPolicia");
			mapaUsuarios.put("sspMedico", "sspMedico");
			mapaUsuarios.put("sspAgenteMP", "sspAgenteMP");
			mapaUsuarios.put("sspProcesal", "sspProcesal");
			mapaUsuarios.put("sspDirProc", "sspDirProc");
			mapaUsuarios.put("sspDirCede", "sspDirCede");
			mapaUsuarios.put("unidadCaptura", "unidadCaptura");
			mapaUsuarios.put("sspEPRS", "sspEPRD");
			// Fin Poder Judicial
			mapaUsuarios.put("encargadoCausa", "encargadoCausa");
			mapaUsuarios.put("encargadoSala", "encargadoSala");
			mapaUsuarios.put("atencionPublico", "PJatencionPub");
			mapaUsuarios.put("encargadoInf", "encargadoInf");
			mapaUsuarios.put("notificador", "notificador");
			//mapaUsuarios.put("encargadoSegIn", "encargadoSegIn");
			mapaUsuarios.put("transcriptorPJ", "transcriptorPJ");
			mapaUsuarios.put("admonPJ", "administrativoPJ");
			mapaUsuarios.put("juezPJ", "juezPJ");
			mapaUsuarios.put("juezEjecucionPJ", "juezEjecucionPJ");

			mapaUsuarios.put("ofEjecucion", "oficialEjecucion");
			mapaUsuarios.put("adminEjecucion", "adminEjecucion");
			// mapaUsuarios.put("mandamientosPJ", new
			// DuplaPuestoFwd("mandamientosJudiciales",);

			mapaUsuarios.put("serviciosPerici", "serviciosPericiales");

			mapaUsuarios.put("almacenExp", "almacenDeExpedientes");

			mapaUsuarios.put("coordPerFis", "coordinadorPerFis");
			mapaUsuarios.put("notificadorv", "notificador");

			mapaUsuarios.put("agentempSisTrad", "agentempSisTrad");
			mapaUsuarios.put("consignador", "consignador");
			mapaUsuarios.put("coordinadorConsig", "coordinadorConsig");

			// reinsercion a ala sociedad

			mapaUsuarios.put("ase", "ase");
			mapaUsuarios.put("cer", "cer");
			mapaUsuarios.put("cmt", "cmt");
			mapaUsuarios.put("cpr", "cpr");
			mapaUsuarios.put("dse", "dse");
			mapaUsuarios.put("med", "med");
			mapaUsuarios.put("pop", "pop");
			
			// Usuarios directivos
			mapaUsuarios.put("director", "directorGeneral");
			mapaUsuarios.put("subprocurador", "subprocurador");
			mapaUsuarios.put("procurador", "procurador");
			mapaUsuarios.put("directorUI", "directorDeUnidadesDeInvestigacion");
			
			// Reasignar expedientes
			mapaUsuarios.put("coordinadorPoliciaMinisterial", "coordinadorPoliciaMinisterial");
		}
	}

	/***
	 * Metodo para mandar consultar el menu de los roles a mostrar
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public ActionForward consultaMenuRol(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("ejecutando Action LoginAction en metodo consultaMenuRol:");
			// ElementoMenuDTO elementoMenuDTO = new ElementoMenuDTO();
			UsuarioDTO user = super.getUsuarioFirmado(request);
			List<RolDTO> rolDTOs = new ArrayList<RolDTO>();
			String rolactivo = user.getRolActivo();
			log.info("Roll Activo:" + user.getRolActivo());
			// Codigo comentado a peticion de gibran ya que no se utilisara este
			// desarrollo por el momento por falta de madures.
			// elementoMenuDTO=elementoMenuDelegate.consultarElementoMenuXUsuario(user);
			//
			// List<ElementoMenuDTO>listElementoMenuDTOs=elementoMenuDTO.getElementoMenuHijosDTO();
			// for (ElementoMenuDTO elementoMenuDTO2 : listElementoMenuDTOs) {
			// RolDTO rolDTO=new RolDTO();
			// if(!rolactivo.equals(elementoMenuDTO2.getcComando())){
			// rolDTO.setNombreRol(elementoMenuDTO2.getcComando());
			// rolDTO.setDescripcionRol(elementoMenuDTO2.getcNombre());
			// }
			// rolDTOs.add(rolDTO);
			// }

			for (UsuarioRolDTO usuarioRolDTO2 : user.getUsuarioRoles()) {
				log.info("Rolles:" + usuarioRolDTO2.getRol().getNombreRol());
				if (!rolactivo.equals(usuarioRolDTO2.getRol().getNombreRol())) {
					rolDTOs.add(usuarioRolDTO2.getRol());
				}
			}
			
			if (!rolDTOs.isEmpty()) {
				Collections.sort(rolDTOs, UsuarioRolDTO.COMPARA_ROL);
				log.info("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
			}

			log.info("usuarioRolDTO.size::" + rolDTOs.size());
			// Se comenta codigo ya que solo es para ver los roles en el log que
			// contiene el usuario logueado
			// for (RolDTO rolDTO2 : rolDTOs) {
			// log.info("usuarioRolDTO.size::" + rolDTO2.getNombreRol());
			// }

			converter.alias("rolDTOs", java.util.List.class);
			converter.alias("RolDTO", RolDTO.class);
			String xml = converter.toXML(rolDTOs);
			if (log.isDebugEnabled()) {
				log.debug("listaTipos:: " + xml);
			}
			escribirRespuesta(response, xml);

		} catch (Exception e) {
			log.info(e.getCause(), e);
			escribirRespuesta(response, "");
		}
		return null;
	}

	/**
	 * Metodo que sirve para redireccionar al usuario al rol que a seleccionado
	 * de la opcion facultades.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward redirecRol(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			String rolName = (String) request.getParameter("rolname");
			UsuarioDTO user = super.getUsuarioFirmado(request);
			log.debug("rolname: " + rolName);
			for (UsuarioRolDTO userRol : user.getUsuarioRoles()) {
//Este if es para que en base al rol activo se modifique el area actual del usuario que existe en sesion
				if (userRol.getRol().getNombreRol().equals(rolName)) {
					AreaDTO areaDTO = new AreaDTO(userRol.getRol()
							.getJerarquiaOrganizacionalDTO()
							.getJerarquiaOrganizacionalId());
					areaDTO.setNombre(userRol.getRol()
							.getJerarquiaOrganizacionalDTO().getNombre());
					user.setAreaActual(areaDTO);
					user.getFuncionario()
							.getJerarquiaOrganizacional()
							.setJerarquiaOrganizacionalId(
									userRol.getRol()
											.getJerarquiaOrganizacionalDTO()
											.getJerarquiaOrganizacionalId());
					user.setRolActivo(userRol.getRol().getNombreRol());
				}
			}
			if (rolName != null && !rolName.equals("")) {
				user.setRolActivo(rolName);
			}
			request.getSession()
					.setAttribute(KEY_SESSION_USUARIO_FIRMADO, user);
			inicializarFwds();
			String forward = null;
			if(user.getRolACtivo().getRol()!=null){
				if(user.getRolACtivo().getRol().getRolPadre()==null){
					forward = mapaUsuarios.get(rolName);
				}else{
					forward = mapaUsuarios.get(user.getRolACtivo().getRol().getRolPadre().getNombreRolPadre());
				}
			}else{
				forward = mapaUsuarios.get(rolName);
			}
			log.debug("forward :: " + forward);
			if (forward == null) {
				request.setAttribute("error", 0);
				return mapping.findForward("success");
			}
			
			RolDTO rolDTO = user.getRolACtivo().getRol();
			request.getSession()
			.setAttribute(KEY_SESSION_MENU_DINAMICO_IZQUIERDO, this.getMenuInicial(rolDTO, TipoMenu.IZQUIERDO));

			request.getSession()
			.setAttribute(KEY_SESSION_MENU_DINAMICO_SUPERIOR, this.getMenuInicial(rolDTO, TipoMenu.ARRIBA));
			return mapping.findForward(forward);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			request.setAttribute("error", 0);
			return mapping.findForward("success");
		}
	}

	/***
	 * Metodo para mandar Validar la contrase�a cuando la aplicacion se h
	 * bloqueado
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward validaContrasena(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		log.info("ejecutando Action Login en metodo validaContrase�a");
		try {
			DynaActionForm forma = (DynaActionForm) form;
			String password = (String) forma.get("password");
			String capcha = (String) forma.get("captcha");
			UsuarioDTO usuario = super.getUsuarioFirmado(request);
			String idSesion = request.getRequestedSessionId();
			// log.info("##PAss:" + password);
			// log.info("##Usuario:" + usuario.getClaveUsuario());
			// log.info("##idSesion:" + idSesion);
			usuario.setIdSesion(idSesion);
			usuario.setPassword(password);
			usuario = usuarioDelegate.buscarUsuario(usuario);
			// log.info("##idSesion:" + usuarioDTO2.getIdSesion());
			// log.info("##idSesion:" + idSesion);
			String kaptchaExpected = (String) request.getSession()
					.getAttribute(Constants.KAPTCHA_SESSION_KEY);
			// log.info("##kaptchaExpected:" + kaptchaExpected);
			// log.info("##Usuario:" + usuarioDTO2);
			if (usuario != null) {					
				if (capcha.equals(kaptchaExpected)) {
					if (usuario.getDatosIncorrectos()!=null && !usuario.getDatosIncorrectos()){
						usuario.setDatosIncorrectos(false);					
					}else{
						usuario.setDatosIncorrectos(true);
					}
					
					converter.alias("usuarioDTO", UsuarioDTO.class);
					String xml = converter.toXML(usuario);
					request.getSession().removeAttribute(
							Constants.KAPTCHA_SESSION_KEY);
					escribir(response, xml, null);
				} else {
					// ManejadorSesion.invalidate(idSesion);
					usuario.setDatosIncorrectos(false);
					//se guarda mensaje de error en idSesion
					usuario.setIdSesion("C�digo captcha err�neo,<br/> favor de verificar.");
					converter.alias("usuarioDTO", UsuarioDTO.class);
					String xml = converter.toXML(usuario);
					// request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
					// Aqui se tiene que responder un mensaje de error
					escribir(response, xml, null);
				}
			} else {
				usuario = new UsuarioDTO();
				usuario.setDatosIncorrectos(false);
				//se guarda mensaje de error en idSesion
				usuario.setIdSesion("La sesi�n ha sido terminada,<br/> favor de volver a iniciar sesi�n.");
				converter.alias("usuarioDTO", UsuarioDTO.class);
				String xml = converter.toXML(usuario);
				escribir(response, xml, null);
			}
		} catch (Exception e) {
			log.info("TurnoSiguienteError");
			log.info(e.getCause(), e);
			escribir(response, "TurnoSiguienteError", null);
		}
		return null;
	}
	
	/**
	 * M�todo que obtiene las primeras ramas del menu 
	 * @param rolDTO rol del cual se quiere el men�
	 * @param tipoMenu tipo de men�
	 * @return Lista de ElementoMenuDTO con el men� solicitado
	 * @throws NSJPNegocioException
	 */
	public List<ElementoMenuDTO> getMenuInicial(RolDTO rolDTO, TipoMenu tipoMenu) throws NSJPNegocioException{
		return elementoMenuDelegate.consultarElementosMenuXRol(rolDTO, tipoMenu);
	}	
	
}
