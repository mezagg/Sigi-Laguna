
package mx.gob.segob.nsjp.web.administrador.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.ManejadorArchivos;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.catalogo.CatalogoDelegate;
import mx.gob.segob.nsjp.delegate.elemento.ElementoDelegate;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.delegate.parametro.ParametroDelegate;
import mx.gob.segob.nsjp.delegate.usuario.UsuarioDelegate;
import mx.gob.segob.nsjp.dto.ParametroDTO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.DiaInhabilDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.rol.FiltroRolesDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioRolDTO;
import mx.gob.segob.nsjp.web.base.action.ReporteBaseAction;
import mx.gob.segob.nsjp.web.base.listener.ManejadorSesion;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Cuauhtemoc Paredes S
 *
 */
public class AdministracionSistemaAction extends ReporteBaseAction{

	/**Log de clase*/
	private static final Logger log  = Logger.getLogger(AdministracionSistemaAction.class);
	
	@Autowired
	public CatalogoDelegate catalogoDelegate;
	
	@Autowired
	public UsuarioDelegate usuarioDelegate;
	
	@Autowired
	public FuncionarioDelegate funcionarioDelegate;
	
	@Autowired
	public ElementoDelegate elementoDelegate;
	@Autowired
	public ParametroDelegate parametroDelegate;



	/**
	 * Método para registar los dias inhabiles del sistema
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward obtenerNombreAgencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, NSJPNegocioException {
		try
		{
			log.debug("ENTRA A OBTENER NOMBRE AGENCIA - obtenerNombreAgencia");
			UsuarioDTO usuario = super.getUsuarioFirmado(request);
			String xml="";
			if(usuario.getFuncionario()!=null && usuario.getFuncionario().getDiscriminante()!=null && usuario.getFuncionario().getDiscriminante().getNombre()!=null)
			{
				xml="<respuesta><nombreAgencia>"+usuario.getFuncionario().getDiscriminante().getNombre()+"</nombreAgencia>";
				xml+= "<idInstitucion>"+usuario.getInstitucion().getConfInstitucionId()+"</idInstitucion></respuesta>";
			}
			else
			{
				xml="<respuesta><nombreAgencia></nombreAgencia><idInstitucion>0</idInstitucion></respuesta>";
			}
			log.info("nombreAgencia:: "+xml);
			super.escribirRespuesta(response, xml);
		}
		catch (Exception e) 
		{
			log.info(e.getMessage());
			super.escribirRespuesta(response, "<respuesta><nombreAgencia></nombreAgencia><idInstitucion>0</idInstitucion></respuesta>");
		}
		

		return null;
	}
	
	
	/**
	 * Método para registar los dias inhabiles del sistema
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward ingresarDiaInhabil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, NSJPNegocioException {
		
		log.debug("ENTRA A ADMINISTRAR DIAS INHABILES");
		 
		String motivoDiaInhabil = request.getParameter("motivoDiaInhabil");
		log.debug("Motivo DiaInhabil" + motivoDiaInhabil);
		
		String fecha = request.getParameter("fecha");
		log.debug("Fecha" + fecha);
				
		Date fechaFormato= DateUtils.obtener(fecha);
		log.debug("Fecha con Formato" + fechaFormato);
		
		DiaInhabilDTO diaInhabilDTO = new DiaInhabilDTO();
		diaInhabilDTO.setDescripcion(motivoDiaInhabil);
		diaInhabilDTO.setFecha(fechaFormato);
		
		Long guardaExito = catalogoDelegate.guardarDiaInhabil(diaInhabilDTO);
		log.debug("Regresa Guardado" + guardaExito );

		return null;
	}
			
	/**
	 * Método utilizado para realizar la consulta de dias inhabiles 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoDiasInhabiles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
	
			log.info("ejecutando Action consultar dias inhabiles");
			List<DiaInhabilDTO> diaInhabilDTOs = catalogoDelegate.consultarDiasInhabiles();
						
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			
			writer.print("<rows>");
		
			writer.print("<records>" + diaInhabilDTOs.size() + "</records>");
	
			for (DiaInhabilDTO diaInhabilDTO : diaInhabilDTOs) {
				
				writer.print("<row id='" + diaInhabilDTO.getDiaInhabilId()+ "'>");
				log.info("id del dia inhabil"+diaInhabilDTO.getDiaInhabilId());
				writer.print("<cell>" + diaInhabilDTO.getFecha()+ "</cell>");
				writer.print("<cell>" + diaInhabilDTO.getDescripcion()+ "</cell>");			
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
	 * Método para eliminar los dias inhabiles del sistema
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward eliminarDiaInhabil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, NSJPNegocioException {
		
		log.debug("ENTRA A ELIMINAR DIA INHABIL");
		 
		String idDiaInhabil = request.getParameter("idDiaInhabil");
		log.debug("Motivo DiaInhabil" + idDiaInhabil);
						
		DiaInhabilDTO diaInhabilDTO = new DiaInhabilDTO();
		diaInhabilDTO.setDiaInhabilId(Long.parseLong(idDiaInhabil));
				
		catalogoDelegate.eliminarDiaInhabil(diaInhabilDTO);

		return null;
	}
	
	/**
	 * Método utilizado para realizar la consulta de los roles del sistema 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarRolesDelSistema(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
	
			log.info("ejecutando Action consultar roles del sistema");
			String valor=request.getParameter("idRolesSeleccionados");
			log.info("ejecutando Action consultar roles del sistema ids:"+valor);
			//Se agrega la instituci�n
			Long institucionId = NumberUtils.toLong(request.getParameter("institucion"));
			//se agrega la jerarqu�a 
			Long jerarquiaOrganizacionalId = NumberUtils.toLong(request.getParameter("jerarquiaOrganizacionalId"));
			// la funcionalidad original es para grid y se agrega para regresar la estructura de un combo.
			boolean esCombo = BooleanUtils.toBoolean(request.getParameter("esCombo"));
			
			FiltroRolesDTO filtroRolesDTO=new FiltroRolesDTO();			
			filtroRolesDTO.setIdRolSelec(valor);
			filtroRolesDTO.setConfInstitucionId(institucionId);
			filtroRolesDTO.setJerarquiaOrganizacionalId(jerarquiaOrganizacionalId);
			List<RolDTO> lstRolDTO = catalogoDelegate.consultarRoles(filtroRolesDTO);
			log.info("ejecutando Action consultar roles tamaño consulta de roles :"+lstRolDTO.size());
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			XStream converter=new XStream();
			if (esCombo) {

				converter.alias("lstRolDTO", java.util.List.class);
				converter.alias("rolDTO", RolDTO.class);
				String xml = converter.toXML(lstRolDTO);
				response.setContentType("text/xml");
				writer.print(xml);
			} else {
				writer.print("<rows>");
				//Configuracion del Paginador
				String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
				writer.print(paginacion);
				
				for (RolDTO roDto : lstRolDTO) {
					
					writer.print("<row id='" + roDto.getRolId()+ "'>");
					writer.print("<cell>" + roDto.getNombreRol()+ "</cell>");
					writer.print("<cell><![CDATA[<div>"+"<input type='radio' name='gridRolPrincipal' id='"+roDto.getRolId()+"'> </div>]]></cell>");
					writer.print("<cell>" + roDto.getRolId()+ "</cell>");
					
					RolDTO rolPadreDTO = roDto.getRolPadre();
					writer.print("<cell>");
					if(rolPadreDTO != null) {
						writer.print(rolPadreDTO.getRolId());
					} else {
						writer.print(roDto.getRolId());
					}
					writer.print("</cell>");
					writer.print("</row>");
				}			
				
				writer.print("</rows>");
			}
			writer.flush();
			writer.close();			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Método utilizado para realizar la consulta de los roles del sistema 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward darDeAltaUsuarioEnSistema(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Long exito = 0L;
		try {
	
			log.info("ejecutando Action dar de alta usuario en el sistema");
			
			String nombreUsuario = request.getParameter("nombreUsuario");
//			log.debug("nombre usuario " + nombreUsuario);
			String contrasena = request.getParameter("contrasena");
//			log.debug("contraseña " + contrasena);
			String idFuncionario = request.getParameter("idFuncionario");
//			log.debug("id funcionario" + idFuncionario);			
			String roles = request.getParameter("roles");
//			log.debug("roles" + roles);
			String idRolPrincipal = request.getParameter("idRolPrincipal");
			log.debug("idRolPrincipal" + idRolPrincipal);
			
			String[] renglones;
			
			if(roles != null){
				
				renglones = roles.split(",");
	
				if(nombreUsuario != null){
					nombreUsuario = nombreUsuario.trim();
				}
				
				ArrayList<RolDTO> rolDTOs = new ArrayList<RolDTO>();			
							
				int i=0;
				
				String cont = null;
							
				for(i=0; i<renglones.length; i++){
					cont = renglones[i];
					RolDTO rolDTO = new RolDTO();
					rolDTO.setRolId(Long.parseLong(cont));
					if(idRolPrincipal.endsWith(cont)){
						rolDTO.setEsPrincipal(true);
					}else{
						rolDTO.setEsPrincipal(false);
					}
					rolDTOs.add(rolDTO);
					rolDTO=null;
				}			
				
				log.debug("roles" + rolDTOs);
				
				FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
				funcionarioDTO.setClaveFuncionario(Long.parseLong(idFuncionario));
				
				UsuarioDTO usuarioDTO = new UsuarioDTO();
				usuarioDTO.setClaveUsuario(nombreUsuario);
				usuarioDTO.setPassword(contrasena);
				usuarioDTO.setFuncionario(funcionarioDTO);			
							
				exito = usuarioDelegate.registrarUsuario(usuarioDTO,rolDTOs);
				log.info("se guardo con exito" + exito);						
			}									
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}finally{
			try {
				XStream converter=new XStream();
				String xml = converter.toXML(exito);
				response.setContentType("text/xml");
				PrintWriter pw = response.getWriter();
				pw.print(xml);
				pw.flush();
				pw.close();
				
			} catch (Exception e2) {
				log.error(e2.getMessage(), e2);
			}	
		}
				
		return null;
	}

	/**
	 * Método utilizado para realizar la consulta de los funcionarios ya registrados en el sistema 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward cargarFuncionarioRegistrado(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
	
			log.info("ejecutando Action consultar funcionarios ya registrados en el sistema");
			
			List<FuncionarioDTO> funcionarioDTOs = funcionarioDelegate.obtenerFuncionariosConUsuario();
			
			XStream converter=new XStream();
			converter.alias("listaCatalogo", java.util.List.class);
			converter.alias("funcionariosRegistrados", FuncionarioDTO.class);
			String xml = converter.toXML(funcionarioDTOs);
			//super.escribirRespuesta(response, xml);
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
	 * Metodo que se utiliza para mostrar la vista para registrar de funcionario
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward registrarFuncionario(ActionMapping mapping, ActionForm form,
													 HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando Action consultar funcionarios");
			ServletContext sc = this.getServlet().getServletContext();
			if (sc.getAttribute("funcionarios") == null)
				sc.setAttribute("funcionarios",funcionarioDelegate.consultarTodosFuncionarios());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("success");
	}



	/**
	 * Método utilizado para realizar la consulta de los funcionarios ya registrados en el sistema 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarUsuarioPorClaveFuncionario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
	
			log.info("ejecutando Action consultar usuario por clave funcionario");
			
			String claveFuncionario = request.getParameter("funcionarioId");
			log.info("llega"+claveFuncionario);
			
			UsuarioDTO usuarioDTO = usuarioDelegate.consultarUsuarioPorClaveFuncionario(Long.parseLong(claveFuncionario));
						
			converter.alias("usuarioDTO", UsuarioDTO.class);
			String xml = converter.toXML(usuarioDTO);
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
	 * Método utilizado para realizar la modificacion de un usuario registrado 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward modificarUsuarioEnSistema(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		boolean actualizacion = false;		
		try {
			
			log.info("ejecutando Action modificar usuario en el sistema");
			
			String nombreUsuario = request.getParameter("nombreUsuario");
			//log.debug("nombre usuario" + nombreUsuario);

			String contrasena = request.getParameter("contrasena");
			//log.debug("contraseña" + contrasena);
			
			String idFuncionario = request.getParameter("idFuncionario");
			//log.debug("id funcionario" + idFuncionario);
			
			String idUsuario = request.getParameter("idUsuario");
			//log.debug("id usuario" + idUsuario);
			
			String roles = request.getParameter("roles");
			//log.debug("roles" + roles);
			String idRolPrincipal = request.getParameter("idRolPrincipal");
			//log.debug("idRolPrincipal" + idRolPrincipal);
			String[] renglones;
			
			if(roles != null){
				
				renglones = roles.split(",");
	
				if(nombreUsuario != null){
					nombreUsuario = nombreUsuario.trim();
				}
				
				ArrayList<RolDTO> rolDTOs = new ArrayList<RolDTO>();
										
				int i=0;
				
				String cont = null;
				if(!roles.equals("null"))
					for(i=0; i<renglones.length; i++){
						cont = renglones[i];
						RolDTO rolDTO = new RolDTO();
						rolDTO.setRolId(Long.parseLong(cont));
						if(idRolPrincipal.equals(cont)){
							rolDTO.setEsPrincipal(true);
						}else{
							rolDTO.setEsPrincipal(false);
						}
						rolDTOs.add(rolDTO);
						rolDTO=null;
					}			
				
				log.debug("roles" + rolDTOs);
				
				FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
				funcionarioDTO.setClaveFuncionario(Long.parseLong(idFuncionario));
				
				UsuarioDTO usuarioDTO = new UsuarioDTO();
				usuarioDTO.setIdUsuario(NumberUtils.toLong(idUsuario));			
				usuarioDTO.setClaveUsuario(nombreUsuario);
				usuarioDTO.setPassword(contrasena);
				usuarioDTO.setFuncionario(funcionarioDTO);			
							
				actualizacion=usuarioDelegate.actualizarUsuario(usuarioDTO,rolDTOs);			
			}
			XStream converter=new XStream();
			String xml = converter.toXML(actualizacion);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
						
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}finally{
			try {
				XStream converter=new XStream();
				String xml = converter.toXML(actualizacion);
				response.setContentType("text/xml");
				PrintWriter pw = response.getWriter();
				pw.print(xml);
				pw.flush();
				pw.close();
				
			} catch (Exception e2) {
				log.error(e2.getMessage(), e2);
			}
			
		}

		return null;
	}
	
	//Metodo Para Agregar usuarios al chat
		public ActionForward agregaUsuariosChat(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws IOException {
				log.info("ejecutando Action Administracion en metodo agregaUsuariosChat");
				try {
					usuarioDelegate.agregaUsuariosChat();
			} catch (Exception e) {	
				log.info("ejecutando Action Administracion en metodo agregaUsuariosChat Error");
				log.info(e.getCause(),e);
				escribir(response, "TurnoCanceladoError",null);
			}
			return null;
		}
		//Metodo Para consultar parametro
		public ActionForward buscarParametro(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws IOException {
			log.info("ejecutando Action Administracion en metodo buscarParametro");
				try {
					
					String parametroChat=usuarioDelegate.buscaParametroChat();
					request.getSession().setAttribute("ParametroChat", parametroChat);
					
					escribirRespuesta(response, "<bandera>"+parametroChat+"</bandera>");
				} catch (Exception e) {	
					log.info("ejecutando Action Administracion en metodo buscarParametro Error");
					log.info(e.getCause(),e);
//					escribir(response, "TurnoCanceladoError",null);
					escribirRespuesta(response, "<bandera>0</bandera>");
				}
				return null;
		}
		
		//Metodo Para consultar parametro Usuarios Archivos
		public ActionForward buscarParametroArchivos(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws IOException {
			log.info("ejecutando Action Administracion en metodo buscarParametroArchivos");
				try {
					
					String parametroChat=usuarioDelegate.buscaParametroImagenes();
					request.getSession().setAttribute("ParametroChat", parametroChat);
					
					escribirRespuesta(response, "<bandera>"+parametroChat+"</bandera>");
				} catch (Exception e) {	
					log.info("ejecutando Action Administracion en metodo buscarParametroArchivos Error");
					log.info(e.getCause(),e);
					escribirRespuesta(response, "<bandera>0</bandera>");
				}
				return null;
		}
		
		public ActionForward agregaArchivosDisco(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws IOException {
				log.info("ejecutando Action Administracion en metodo agregaArchivosDisco");
				log.info("ejecutando Action Administracion en metodo agregaArchivosDisco Comiensa:"+DateUtils.formatearHora(new Date()));
			try {
				int min=0;
				int max=0;
				int siguienteInicio=0;
				boolean opMinMax=false;
				ArchivoDigitalDTO archivoDigitalDTO=elementoDelegate.leeIdIdentificadorMinMax(0);
				ArchivoDigitalDTO archivoDigitalDTOMax=elementoDelegate.leeIdIdentificadorMinMax(1);
				ParametroDTO ruta=parametroDelegate.consultarParametro(Parametros.URL_DESTINO_ARCHIVOS);
				if((archivoDigitalDTO!=null && archivoDigitalDTOMax!=null) 
						&& (archivoDigitalDTO.getArchivoDigitalId()!=null && archivoDigitalDTOMax.getArchivoDigitalId()!=null)){
					min=archivoDigitalDTO.getArchivoDigitalId().intValue();
					max=archivoDigitalDTOMax.getArchivoDigitalId().intValue();
					int i=0;
					ManejadorArchivos manejadorArchivos=new ManejadorArchivos();
					String rutaCompleta="";
					do {
						List<ArchivoDigitalDTO> liArchivoDigitalDTOs=elementoDelegate.leeRangosArchivosDigitales((long)min);
						min=liArchivoDigitalDTOs.get(liArchivoDigitalDTOs.size()-1).getArchivoDigitalId().intValue();
						List<ValorDTO> valores=new ArrayList<ValorDTO>();
						for (ArchivoDigitalDTO archivoDigitalDTO2 : liArchivoDigitalDTOs) {
							try{
								if(archivoDigitalDTO2.getContenido()!=null && archivoDigitalDTO2.getTipoArchivo()!=null){
									rutaCompleta=manejadorArchivos.guardaArchivos(archivoDigitalDTO2.getContenido(), ruta.getValor(), archivoDigitalDTO2.getArchivoDigitalId().toString(), archivoDigitalDTO2.getTipoArchivo());
									ValorDTO valorDTO=new ValorDTO(archivoDigitalDTO2.getArchivoDigitalId(), rutaCompleta);
									valores.add(valorDTO);
								}
							}catch (Exception e) {
								log.info("ERROR Controlado del identificador:"+archivoDigitalDTO2.getArchivoDigitalId()+" de archivo digital");
							}
						}
						try{
							elementoDelegate.modificaArchivosDigitales(valores);
						}catch (Exception e) {
							log.info("ERROR Controlado del UPDATE de Archivo Digital rango de archivo digital:"+valores.get(0).getIdCampo()+" a "+ valores.get(valores.size()-1).getIdCampo());
						}
						i++;
					} while (min<max);
					log.info("Numero de 50:"+i);
				}
				parametroDelegate.modificaValorDeParametro("1",Parametros.ACTIVA_DESTINO_ARCHIVOS);
			} catch (Exception e) {	
				log.info("ejecutando Action Administracion en metodo agregaArchivosDisco Error");
				log.info("ejecutando Action Administracion en metodo agregaArchivosDisco Error:"+DateUtils.formatear(new Date()));
				log.info(e.getCause(),e);
				escribir(response, "TurnoCanceladoError",null);
			}
				log.info("ejecutando Action Administracion en metodo agregaArchivosDisco Termina:"+DateUtils.formatearHora(new Date()));	
			return null;
		}
		
		public ActionForward anularUsuario(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws IOException {
			boolean anular = false;
			try {
				String idUsuario = request.getParameter("idUsuario");
				log.debug("/**** id usuario :: " + idUsuario);							
				if(idUsuario != null) { 
					anular = usuarioDelegate.anularUsuarioDeSistema(new Long(idUsuario));
					if(anular){
						UsuarioDTO usuarioDTO=new UsuarioDTO(new Long(idUsuario));
						String idSesion=usuarioDelegate.buscarUsuarioEnSesionId(usuarioDTO);
						ManejadorSesion.invalidate(idSesion);
					}
				}				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}finally{
				try {
					XStream converter=new XStream();
					String xml = converter.toXML(anular);
					response.setContentType("text/xml");
					PrintWriter pw = response.getWriter();
					pw.print(xml);
					pw.flush();
					pw.close();
					
				} catch (Exception e2) {
					log.error(e2.getMessage(), e2);
				}
				
			}

						
			return null;
		}
		/**
		 * Método utilizado para realizar la consulta de los funcionarios ya registrados en el sistema 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 */
		public ActionForward consultarRolesUsuarioPorClaveFuncionario(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws IOException {
			try {
		
				log.info("ejecutando Action consultar usuario por clave funcionario");
				
				String claveFuncionario = request.getParameter("funcionarioId");
				log.info("llega"+claveFuncionario);
				
				UsuarioDTO usuarioDTO = usuarioDelegate.consultarUsuarioPorClaveFuncionario(Long.parseLong(claveFuncionario));
							
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
				for (UsuarioRolDTO roDto : usuarioDTO.getUsuarioRoles()) {
					
					writer.print("<row id='" + roDto.getRol().getRolId()+ "'>");
					writer.print("<cell>" + roDto.getRol().getNombreRol()+ "</cell>");
					if( roDto.getEsPrincipal()){
						writer.print("<cell><![CDATA[<div>"+"<input type='radio' name='gridRolPrincipal' checked='checked' id='"+roDto.getRol().getRolId()+"'> </div>]]></cell>");
					}else{
						writer.print("<cell><![CDATA[<div>"+"<input type='radio' name='gridRolPrincipal' id='"+roDto.getRol().getRolId()+"'> </div>]]></cell>");
					}
					
					writer.print("<cell>" + roDto.getRol().getRolId()+ "</cell>");

					RolDTO rolPadreDTO = roDto.getRol().getRolPadre();
					writer.print("<cell>");
					if(rolPadreDTO != null) {
						writer.print(rolPadreDTO.getRolId());
					} else {
						writer.print(roDto.getRol().getRolId());
					}
					writer.print("</cell>");

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
		
		public ActionForward buscarUsuarioEnSesion(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws IOException {
			boolean sesion = false;
			try {
				String idUsuario = request.getParameter("idUsuario");
				log.debug("/**** id usuario :: " + idUsuario);							
				UsuarioDTO usuarioDTO=new UsuarioDTO();
				usuarioDTO.setIdUsuario(Long.parseLong(idUsuario));
				sesion = usuarioDelegate.buscarUsuarioEnSesion(usuarioDTO);
				XStream converter=new XStream();
				String xml = converter.toXML(sesion);
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
	
	
}
