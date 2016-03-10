/**
* Nombre del Programa : InformePHAction.java
* Autor                            : SergioDC
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 2 Aug 2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
* Programa Dependiente  :N/A
* Programa Subsecuente :N/A
* Cond. de ejecucion        :N/A
* Dias de ejecucion          :N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                       :N/A
* Compania               :N/A
* Proyecto                 :N/A                                 Fecha: N/A
* Modificacion           :N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.web.ssp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.catalogo.CatalogoDelegate;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.delegate.ssp.informepolicial.InformePolicialHomologadoDelegate;
import mx.gob.segob.nsjp.delegate.ssp.turnolaboral.TurnoLaboralDelegate;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatFaltaAdministrativaDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.objeto.AeronaveDTO;
import mx.gob.segob.nsjp.dto.objeto.ArmaDTO;
import mx.gob.segob.nsjp.dto.objeto.EmbarcacionDTO;
import mx.gob.segob.nsjp.dto.objeto.ExplosivoDTO;
import mx.gob.segob.nsjp.dto.objeto.NumerarioDTO;
import mx.gob.segob.nsjp.dto.objeto.SustanciaDTO;
import mx.gob.segob.nsjp.dto.objeto.VehiculoDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.DelitoIphDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.FaltaAdministrativaIphDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.InformePolicialHomologadoDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.InvolucradoIPHDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.OperativoDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.RespuestaIPHWSDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.TurnoLaboralIphDTO;
import mx.gob.segob.nsjp.dto.ssp.turnolaboral.TurnoLaboralDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.ReporteBaseAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author SergioDC
 *
 */
public class InformePHAction extends ReporteBaseAction{
	private static final Logger log  = Logger.getLogger(InformePHAction.class);
	
	@Autowired
	private InformePolicialHomologadoDelegate informePolicialHomologadoDelegate;
	
	@Autowired
	private FuncionarioDelegate funcionarioDelegate;
	
	@Autowired
	private TurnoLaboralDelegate turnoLaboralDelegate;
	
	@Autowired
	private CatalogoDelegate catalogoDelegate;

	/**
	 * M�todo utilizado para generar el numero de folio de control de un IPH
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward generarFolioIPH(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
	
			log.info("ejecutando Action generar folio IPH");
			
			UsuarioDTO usuario = getUsuarioFirmado(request);
			ExpedienteDTO expediente = new ExpedienteDTO();
			expediente.setUsuario(usuario);
			
			InformePolicialHomologadoDTO iph = informePolicialHomologadoDelegate.ObtenerFolioIPH(expediente);
			super.setExpedienteTrabajo(request, iph.getExpediente());
			log.info("iph.getExpediente().getNumeroExpedienteId() ... " + iph.getExpediente().getNumeroExpedienteId());
			log.info("iph.getExpediente().getExpedienteId() ... " + iph.getExpediente().getExpedienteId());
			request.getSession().setAttribute("numeroExpedienteId", iph.getExpediente().getNumeroExpedienteId());
			XStream converter=new XStream();
			converter.alias("iphDTO", InformePolicialHomologadoDTO.class);
			String xml = converter.toXML(iph);
			log.info("respuesta generar folio IPH ------- "+xml);

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
	 * M�todo utilizado para Consultar el Catalogo de Turno Laboral
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoTurnoLaboral(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
	
			log.info("ejecutando Action consultar Catalogo Turno Laboral");

			XStream converter=new XStream();
			List<TurnoLaboralDTO> turnoLaboralList = turnoLaboralDelegate.consultarCatalogoTurnoLaboral();
			converter.alias("turnoLaboralDTO", TurnoLaboralDTO.class);
			String xml = converter.toXML(turnoLaboralList);
			log.info("respuesta consultar catalogo de turno laboral ------- "+xml);

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
	 * Metodo utilizado para Consultar el Catalogo de Corporaciones
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoCoorporaciones(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
	
			log.info("ejecutando Action consultar Catalogo Corporaciones");
			List<CatalogoDTO> catalogo = new ArrayList<CatalogoDTO>();
			catalogo = catalogoDelegate.recuperarCatalogo(Catalogos.CORPORACION);

			XStream converter=new XStream();
			converter.alias("corporacion", CatalogoDTO.class);
			String xml = converter.toXML(catalogo);
			log.info("respuesta consultar catalogo de Corporaciones ------- "+xml);

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
	 * M�todo utilizado para Consultar Funcionario Superior de un Funcionario
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultaFuncionarioSuperior(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, NSJPNegocioException {
		try
		{
			log.info("ejecutando Action Obtener Funcionario Superior");
			FuncionarioDTO funcionario = new FuncionarioDTO();
			try
			{
				funcionario.setClaveFuncionario(Long.parseLong(request.getParameter("claveFuncionario")));
			}catch(Exception e)
			{
				log.error(e.getMessage(), e);				
			}
			XStream converter=new XStream();
			funcionario = funcionarioDelegate.obtenerFuncionarioSuperior(funcionario);
			converter.alias("funcionarioDTO", FuncionarioDTO.class);
			String xml = converter.toXML(funcionario);
			log.info("respuesta Obtener Superior Funcionario ------- "+xml);
			
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * M�todo utilizado para Consultar Catalogo de Faltas Administrativas o Catalogo de Delitos
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarSubtipoEvento(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, NSJPNegocioException {
		int tipoEvento = 0;
		try
		{
			log.info("ejecutando Action Consultar Catalogo Falta Administrativa o Delitos");
			try
			{
				tipoEvento = Integer.parseInt(request.getParameter("tipoEvento"));
			}catch(Exception ex)
			{
				log.error(ex.getMessage(), ex);
			}
			String xml="";
			XStream converter=new XStream();
			if(tipoEvento==1)
			{
				List<CatDelitoDTO> catDelito = new ArrayList<CatDelitoDTO>();			
				catDelito = catalogoDelegate.consultarDelito();
				converter.alias("delito", CatDelitoDTO.class);
				xml = converter.toXML(catDelito);
				log.info("respuesta Obtener Catalogo Delitos ------- "+xml);
				
			}if(tipoEvento==2)
			{
				List<CatFaltaAdministrativaDTO> faltaAdministrativa = new ArrayList<CatFaltaAdministrativaDTO>();			
				faltaAdministrativa = catalogoDelegate.consultarCatalogoFaltaAdministrativa();
				converter.alias("falta", CatFaltaAdministrativaDTO.class);
				xml = converter.toXML(faltaAdministrativa);
				log.info("respuesta Obtener Catalogo Falta Administrativa ------- "+xml);
				
			}
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
						
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * M�todo utilizado para Consultar el Catalogo de Tipo de Carreteras
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultaCatalogoTipoCarretera(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, NSJPNegocioException {
		try
		{
			log.info("ejecutando Action Obtener Catalogo tipo de carreteras");
			List<CatalogoDTO> catalogo = new ArrayList<CatalogoDTO>();
			XStream converter=new XStream();
			catalogo = catalogoDelegate.recuperarCatalogo(Catalogos.TIPO_CARRETERA);
			converter.alias("catalogo", CatalogoDTO.class);
			String xml = converter.toXML(catalogo);
			log.info("respuesta Obtener Catalogo Tipos Carretera ------- "+xml);
			
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			
		}catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * M�todo utilizado para guardar los datos del IPH
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */	
	public ActionForward guardarDatosGeneralesIPH(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		try{
			log.info("ejecutando Action Guardar Datos Generales IPH");

			int tipoEvento = new Integer(request.getParameter("tipoEvento"));
			Long subtipoEvento = new Long(request.getParameter("subtipoEvento"));
			String asunto = request.getParameter("asunto");
			String observaciones = request.getParameter("observaciones");
			String numeroExpediente = request.getParameter("numeroExpediente");			
			
			Long fCatDistritoId = NumberUtils.toLong(request.getParameter("fCatDistritoId"));
			Long fCatDiscriminanteId = NumberUtils.toLong(request.getParameter("fCatDiscriminanteId"));
			Long corporacionId = NumberUtils.toLong(request.getParameter("corporacionId"));
			String idturno= request.getParameter("turnoId");
			Long idTurnoAnterior = idturno.equals("")?null:Long.valueOf(idturno);
			Boolean borrarOperativo = Boolean.parseBoolean(request.getParameter("borrarOperativo"));
			InformePolicialHomologadoDTO informe = new InformePolicialHomologadoDTO();

			ExpedienteDTO expedienteDTO = super.getExpedienteTrabajo(request, numeroExpediente);
			
			//-----FORMAR EL DTO DE IPH-----
			//log.info("idFolioIPH ... " + request.getParameter("idFolioIPH"));
			//if(request.getParameter("idFolioIPH") != null){
				//informe.setInformePolicialHomologadoId(NumberUtils.toLong(request.getParameter("idFolioIPH")));
			//}
			
			informe.setFolioIPH(Long.parseLong(request.getParameter("folioIPH")));
			informe.setNumEcoTransporte(request.getParameter("transporteOficialNum"));
			informe.setObjetivosGenerales(observaciones);
			informe.setExpediente(expedienteDTO);	
			informe.setAsunto(asunto);
			informe.setBorrarOperativo(borrarOperativo);
			informe.setTurnoIdAnt(idTurnoAnterior);
			if(corporacionId != 0L){
				informe.setCorporacionId(corporacionId);
			}
			if(fCatDistritoId != 0L){
				informe.setFCatDistritoId(fCatDistritoId);
			}
			
			if(fCatDiscriminanteId != 0L){
				informe.setFCatDiscriminanteId(fCatDiscriminanteId);
			} 
			
			//Para asignar la lista de turnos
			TurnoLaboralDTO turnoLaboralDTO = new TurnoLaboralDTO();
			turnoLaboralDTO.setTurnoLaboralId(NumberUtils.toLong(request.getParameter("turnoLaboralId")));
			final TurnoLaboralIphDTO turnoLaboralIphDTO = new TurnoLaboralIphDTO();
			turnoLaboralIphDTO.setTurnoLaboral(turnoLaboralDTO);
		    List<TurnoLaboralIphDTO> turnoLaboralIphs =	new ArrayList<TurnoLaboralIphDTO>();
		    turnoLaboralIphs.add(turnoLaboralIphDTO);
		    informe.setTurnoLaboralIphs(turnoLaboralIphs);

		    //Para asignar Delito o Falta Administrativa
		    if(tipoEvento == 1){ //Delito
			    CatDelitoDTO catDelitoDTO = new CatDelitoDTO();
			    catDelitoDTO.setCatDelitoId(subtipoEvento);
			    DelitoIphDTO delitoIphDTO = new DelitoIphDTO();
			    delitoIphDTO.setCatDelito(catDelitoDTO);
			    List<DelitoIphDTO> delitoIphDTOs = new ArrayList<DelitoIphDTO>();
			    delitoIphDTOs.add(delitoIphDTO);
			    informe.setDelitoIph(delitoIphDTOs);
			}else if(tipoEvento == 2){//Falta Administrativa
		    	CatFaltaAdministrativaDTO catFaltaAdministrativaDTO = new CatFaltaAdministrativaDTO();
		    	catFaltaAdministrativaDTO.setCatFaltaAdministrativaId(subtipoEvento);
		    	FaltaAdministrativaIphDTO faltaAdministrativaIphDTO = new FaltaAdministrativaIphDTO();
		    	faltaAdministrativaIphDTO.setCatFaltaAdministrativa(catFaltaAdministrativaDTO);
		    	List<FaltaAdministrativaIphDTO> faltaAdministrativaIphDTOs = new ArrayList<FaltaAdministrativaIphDTO>();
		    	faltaAdministrativaIphDTOs.add(faltaAdministrativaIphDTO);
		    	informe.setFaltaIph(faltaAdministrativaIphDTOs);
		    }
		    
			// ----FORMAR DTO DE FUNCIONARIOS (DESTINATARIO/ELABORA)----
			FuncionarioDTO destinatario = new FuncionarioDTO();
			destinatario.setNumeroEmpleado(request.getParameter("numeroEmpleado"));
			informe.setFuncionarioDestinatario(destinatario);
			
			//Para asignar Tipo de Participacion
			ValorDTO tipoParticipacion = new ValorDTO();
			tipoParticipacion.setIdCampo(new Long(request.getParameter("tipoParticipacionId")));
			informe.setTipoParticipacion(tipoParticipacion);
						
			//-----FORMAR DTO DE OPERATIVO (SI EXISTE OPERATIVO)-----//
			OperativoDTO operativo = new OperativoDTO();
			if(request.getParameter("operativoId") != null && !request.getParameter("operativoId").equals("null")){
				operativo.setOperativoId(Long.parseLong(request.getParameter("operativoId")));}
			if(request.getParameter("nombreOperativo") != "" && request.getParameter("nombreOperativo") != null){
				operativo.setNombre(request.getParameter("nombreOperativo"));
				operativo.setNombreComte(request.getParameter("comandanteOperativo"));
				operativo.setNombreComteAgrupto(request.getParameter("comandanteAgrupamiento"));
			}else if (!borrarOperativo){
				   operativo=null;
			}

			/*Guardar Informe Policial Homologado*/			
			Long idNumeroOperativo=informePolicialHomologadoDelegate.ingresarDatosGenerales(informe,operativo);
			
			request.getSession().removeAttribute("numeroExpedienteId");
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<idNumeroOperativo>" + idNumeroOperativo + "</idNumeroOperativo>");
			writer.flush();
			writer.close();
		}catch(Exception ex)
		{
			log.error(ex.getMessage(), ex);
		}
		return null;
		
	}	


	/**
	 * Metodo utilizado para consultar los datos del Funcionario
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultarDatosFuncionario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			XStream converter=new XStream();
			log.info("Consultando los datos de un Funcionario en SSP");
			UsuarioDTO usuario = getUsuarioFirmado(request);
			log.info("USUARIO"+usuario);
			converter.alias("usuarioDTO", UsuarioDTO.class);
			String xml = converter.toXML(usuario);
			escribir(response, xml,null);
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}

	/**
	 * Metodo utilizado para consultar los IPH que esten relacionados con un involucrado
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultarIPHPorNombreoFechas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("Consultando los IPH relacionados con un involucrado");
			
			String nombre = request.getParameter("nombre");
			String fechaInicial = request.getParameter("fechaInicial");
			String fechaFinal = request.getParameter("fechaFinal");
			
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaCreacionInicio = null;
			Date fechaCreacionFin = null;
			try {
				fechaCreacionInicio = formato.parse(fechaInicial);
				fechaCreacionFin = formato.parse(fechaFinal);
				log.debug("fecha inicio::" + fechaInicial + " fecha Fin::" + fechaFinal);
			
			} catch (ParseException e) {
			
				e.printStackTrace();
			}

			log.debug("Nombre es:" + nombre );
			
			
			List<InformePolicialHomologadoDTO> listIPH = 
				informePolicialHomologadoDelegate.consultarInformePorNombreoFecha(fechaCreacionInicio, fechaCreacionFin, nombre);

			if (log.isDebugEnabled()) {
				log.debug("##################lista de IPH:::::::::" + listIPH.size());
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

			for (InformePolicialHomologadoDTO iph : listIPH) {
				writer.print("<row id='1'>");
				writer.print("<cell>" + iph.getFolioIPH() + "</cell>");
				writer.print("<cell>" + iph.getTipoParticipacion() + "</cell>");
				writer.print("<cell>" + iph + "</cell>");
				writer.print("<cell>" + iph + "</cell>");
				writer.print("<cell>" + iph.getFechaCaptura() + "</cell>");
				writer.print("<cell>" + iph + "</cell>");
				writer.print("</row>");
			}			

			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para consultar los IPH que esten relacionados con un involucrado
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultarIPHs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("Consultando los IPH capturados");
			ConfiguracionDTO confg= getConfguracionGlobal(request);
			UsuarioDTO user=getUsuarioFirmado(request);
			Boolean condicion = request.getParameter("involucrado") == null ? null :
				Boolean.parseBoolean(request.getParameter("involucrado")); 
			Long entidad=confg.getEntidadFederativaDespliegueId();
			List<InformePolicialHomologadoDTO> listIPH = informePolicialHomologadoDelegate.consultarInformesPorUsuario(condicion, user);

			if (log.isDebugEnabled()) {
				log.debug("##################lista de IPH:::::::::" + listIPH.size());
			}
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);
            
			for (InformePolicialHomologadoDTO iph : listIPH) {
				log.debug(":::::: => " + iph);
				writer.print("<row id='"+iph.getInformePolicialHomologadoId()+"'>");
				writer.print("<cell>" + iph.getFolioIPH() + "</cell>");
				
				if(iph.getDelitoIph() != null && !iph.getDelitoIph().isEmpty()){
					writer.print("<cell>" + "Delito" + "</cell>");
					writer.print("<cell>" + iph.getDelitoIph().get(0).getCatDelito().getNombre() + "</cell>");
				} else if(iph.getFaltaIph() != null && !iph.getFaltaIph().isEmpty()){
					writer.print("<cell>" + "Falta Administrativa" + "</cell>");
					writer.print("<cell>" + iph.getFaltaIph().get(0).getCatFaltaAdministrativa().getNombreFalta() + "</cell>");
				}else{
					writer.print("<cell>" + "---" + "</cell>");
					writer.print("<cell>" + "---" + "</cell>");
				}

// Columna de detencion anterior
//				if(iph.getTipoParticipacion() != null){
//					writer.print("<cell>" + iph.getTipoParticipacion().getValor() + "</cell>");
//				}else{
//					writer.print("<cell>" + "---" + "</cell>");
//				}
				boolean op=false;
				if(iph.getInvolucradoIphs() != null 
						&& !iph.getInvolucradoIphs().isEmpty()){
					for (InvolucradoIPHDTO informePolicialHomologadoDTO : iph.getInvolucradoIphs()) {
						//Validacion para buscar detenidos dentro de la lista de involucrados
						if(informePolicialHomologadoDTO.getInvolucrado()!=null &&
								informePolicialHomologadoDTO.getInvolucrado().getEsDetenido()!=null
								&& !informePolicialHomologadoDTO.getInvolucrado().getEsDetenido()){
							op=true;
						}
					}
				}else{
					op=true;
				}
				if(op){
					writer.print("<cell>false</cell>");

				}else{
					writer.print("<cell>true</cell>");
				}
				
				/*if(involucrado){
					writer.print("<cell> SI </cell>");
				}else{
					writer.print("<cell> NO </cell>");
				}*/
				
				//Fecha del informe
				writer.print("<cell>");
				if(iph.getFechaCaptura() != null){
					Calendar cal = Calendar.getInstance();
					cal.setTime(iph.getFechaCaptura());
					int day = cal.get(Calendar.DATE);
			        int month = cal.get(Calendar.MONTH) + 1;
			        int year = cal.get(Calendar.YEAR);
					writer.print((day < 10 ? "0" + day : day) + "/" + (month < 10 ? "0" + month : month) + "/" + year);
				}
				writer.print("</cell>");
				
				//Hora del informe
				writer.print("<cell>");
				if(iph.getFechaCaptura() != null){
					Calendar cal = Calendar.getInstance();
					cal.setTime(iph.getFechaCaptura());
					int hour = cal.get(Calendar.HOUR_OF_DAY);
			        int minutes = cal.get(Calendar.MINUTE);
					writer.print((hour < 10 ? "0" + hour : hour) + ":" + (minutes < 10 ? "0" + minutes : minutes));
				}
				writer.print("</cell>");
				
				writer.print("</row>");
			}			

			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
	
	/**
	 * Metodo utilizado para consultar el objeto IPH por numero de folio
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultarIPHPorNumeroDeFolio(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("Consultando action consultar IPH por numero de folio");
			
			Long folioIPH =NumberUtils.toLong(request.getParameter("folioIPH"),0) ;
			XStream converter=new XStream();
			if(folioIPH > 0L){
				InformePolicialHomologadoDTO dtoIPH = informePolicialHomologadoDelegate.consultarInformePorFolio(folioIPH);
				converter.alias("dtoIPH", InformePolicialHomologadoDTO.class);
				String xml = converter.toXML(dtoIPH);
				escribir(response, xml,null);
			}

		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
	
	/**
	 * M�todo utilizado para generar el informe policial homologado
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */	
	public ActionForward generarInformeIPH(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		try{
			log.info("ejecutando Action Generar Informe");

			InformePolicialHomologadoDTO informe = new InformePolicialHomologadoDTO();
			informe.setFolioIPH(Long.parseLong(request.getParameter("folioIPH")));
			Long idAgencia = NumberUtils.toLong(request.getParameter("idAgencia"),0);
			
			DocumentoDTO documentoDTO = informePolicialHomologadoDelegate.cargarInformeIPH(informe);
			
			//Se encarga de consultar un IPH por folio e invocar al WS para ser enviado a PGJ
			RespuestaIPHWSDTO loRespuestaIPHWSDTO = informePolicialHomologadoDelegate.consultarEnviarInformePolicialHomologado(informe.getFolioIPH(), idAgencia);
			log.info("***ERRORES AL ENVIAR IPH ***" + loRespuestaIPHWSDTO.getMensajeDeError());
			if (loRespuestaIPHWSDTO.getMensajeDeError()!=null)
				if (!loRespuestaIPHWSDTO.getMensajeDeError().equals("")){
					log.info("ERRORES DE ENVIO... ELIMINANDO DOCUMENTO");
					informePolicialHomologadoDelegate.eliminarDocumentoIphPorErrorEnvio(documentoDTO,informe.getFolioIPH());
					documentoDTO= new DocumentoDTO(0L);
			}

			loRespuestaIPHWSDTO.setIdDocumentoIPH(documentoDTO.getDocumentoId());
			XStream converter=new XStream();
			converter.alias("RespuestaDTO", RespuestaIPHWSDTO.class);
			String respuesta = converter.toXML(loRespuestaIPHWSDTO);
			escribirRespuesta(response, respuesta);
			
		}catch(NSJPNegocioException ex)
		{
			log.info(ex.getMessage());
		}
		return null;	
	}	

	/**
	 * M�todo utilizado para consultar un informe policial homologado seg�n
	 * el folio IPH seleccionado.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */	
	public ActionForward consultarInformePolicialHomologadoPorFolio(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		try{
			log.info("ejecutando consultarInformePolicialHomologadoPorFolio() ... ");
			Long folioIPH = Long.parseLong(request.getParameter("folioIPH"));
			log.info("folioIPH ... " + folioIPH);
			
			InformePolicialHomologadoDTO informePolicialHomologadoDTO = new InformePolicialHomologadoDTO();
			informePolicialHomologadoDTO = informePolicialHomologadoDelegate.consultarInformePolicialHomologadoPorFolio(folioIPH);
			informePolicialHomologadoDTO.setUnicoIdNumeroExpediente(informePolicialHomologadoDTO.getExpediente().getNumeroExpedienteId().toString());
			super.setExpedienteTrabajo(request, informePolicialHomologadoDTO.getExpediente());
			boolean opcionEnvioIPH=false;
			if(informePolicialHomologadoDTO.getExpediente()!=null && informePolicialHomologadoDTO.getExpediente().getDocumentosDTO()!=null){
				for (DocumentoDTO documento : informePolicialHomologadoDTO.getExpediente().getDocumentosDTO()) {
					if(documento.getTipoDocumentoDTO()!=null && documento.getTipoDocumentoDTO().getIdCampo()!=null &&
							documento.getTipoDocumentoDTO().getIdCampo().equals(TipoDocumento.INFORME.getValorId())){
						opcionEnvioIPH=true;
					}
				}
			}
			informePolicialHomologadoDTO.setExisteInforme(opcionEnvioIPH);
			XStream converter=new XStream();
			converter.alias("InformePolicialHomologadoDTO", InformePolicialHomologadoDTO.class);
			converter.alias("InvolucradoDTO", InvolucradoDTO.class);
			converter.alias("VehiculoDTO", VehiculoDTO.class);
			converter.alias("AeronaveDTO", AeronaveDTO.class);
			converter.alias("EmbarcacionDTO", EmbarcacionDTO.class);
			converter.alias("SustanciaDTO", SustanciaDTO.class);
			converter.alias("ArmaDTO", ArmaDTO.class);
			converter.alias("ExplosivoDTO", ExplosivoDTO.class);
			converter.alias("NumerarioDTO", NumerarioDTO.class);
			log.info("COORPORACION "+ informePolicialHomologadoDTO.getCorporacionId());
			String xml = converter.toXML(informePolicialHomologadoDTO);
			log.info("respuesta mostrar folio IPH ------- "+xml);
			escribir(response, xml,null);
			
		}catch(Exception ex)
		{
			log.error(ex.getMessage(), ex);
		}
		return null;	
	}	
	
}
