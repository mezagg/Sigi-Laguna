/**
 * Nombre del Programa : IngresarIndividuoAction.java
 * Autor               : Arturo Leon
 * Compania            : Ultrasist
 * Proyecto            : NSJP                    Fecha: 23/02/2011
 * Marca de cambio     : N/A
 * Descripcion General : Clase Action para Ingresar Individuo
 * Programa Dependiente: N/A
 * Programa Subsecuente: N/A
 * Cond. de ejecucion  : N/A
 * Dias de ejecucion   : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor               :N/A
 * Compania            :N/A
 * Proyecto            :N/A                                   Fecha: N/A
 * Modificacion        :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.web.persona.action;

import static mx.gob.segob.nsjp.web.base.action.InvolucradoFormUtil.CALIDAD_DEFENSOR;
import static mx.gob.segob.nsjp.web.base.action.InvolucradoFormUtil.CALIDAD_DEFENSOR_PRIVADO;
import static mx.gob.segob.nsjp.web.base.action.InvolucradoFormUtil.CALIDAD_DENUNCIANTE;
import static mx.gob.segob.nsjp.web.base.action.InvolucradoFormUtil.CALIDAD_PROBABLE_RESPONSABLE;
import static mx.gob.segob.nsjp.web.base.action.InvolucradoFormUtil.CALIDAD_QUIEN_DETUVO;
import static mx.gob.segob.nsjp.web.base.action.InvolucradoFormUtil.CALIDAD_TESTIGO;
import static mx.gob.segob.nsjp.web.base.action.InvolucradoFormUtil.CALIDAD_TRADUCTOR;
import static mx.gob.segob.nsjp.web.base.action.InvolucradoFormUtil.CALIDAD_VICTIMA_PERSONA;
import static mx.gob.segob.nsjp.web.base.action.InvolucradoFormUtil.TIPO_PERSONA_FISICA;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.elemento.Elementos;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.domicilio.AsentamientoDTO;
import mx.gob.segob.nsjp.dto.domicilio.CiudadDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioExtranjeroDTO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.dto.domicilio.MunicipioDTO;
import mx.gob.segob.nsjp.dto.domicilio.TipoAsentamientoDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.ActaCircunstanciadaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.hecho.HechoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoViewDTO;
import mx.gob.segob.nsjp.dto.persona.CorreoElectronicoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.persona.TelefonoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.base.action.InvolucradoFormUtil;
import mx.gob.segob.nsjp.web.caso.form.IngresarActaCircunstanciadaForm;
import mx.gob.segob.nsjp.web.caso.form.IngresarIndividuoForm;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase Action para Ingresar Calidad
 * 
 * @version 1.0
 * @author Arturo Leon Galicia - Ultrasist
 * 
 * 
 */
public class IngresarIndividuoAction extends GenericAction {
	/* Log de clase */
	private static final Logger logger = Logger
			.getLogger(IngresarIndividuoAction.class);
	@Autowired
	public InvolucradoDelegate involucradoDelegate;
	@Autowired
	public ExpedienteDelegate expedienteDelegate;

	/**
	 * Metodo utilizado para realizar la carga del combo Calidad
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward cargarCalidad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		return null;
	}

	/**
	 * Metodo utilizado para guardar los datos de un individuo dependiendo su
	 * calidad
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward guardarIndividuo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			logger.info("ejecutando Action guardarIndividuo");

			//EXPEDIENTE
			String numeroExpediente=request.getParameter("numeroExpediente");
			ExpedienteDTO expedienteDTO =  super.getExpedienteTrabajo(request,numeroExpediente);
			// se coloca busqueda de expediente por identificador de numero de expediente en caso de que no sea cargada previamente
			// en la sesion del usuario realizada en la linea anterior
			if(expedienteDTO==null && numeroExpediente!=null){
				try{
					ExpedienteDTO exp=new ExpedienteDTO();
					exp.setNumeroExpedienteId(Long.parseLong(numeroExpediente));
					exp.setExpedienteId(expedienteDelegate.obtenerExpedienteIdPorNumExpId(exp));
					expedienteDTO=exp;
				}catch (NumberFormatException e) {
					logger.info("Error Action guardarIndividuo por datos de diferente tipo a los solicitados");
					return null;
				}
			}
			//INVOLUCRADO  
			IngresarIndividuoForm retorno = new IngresarIndividuoForm();
			InvolucradoDTO involucradoDTO = InvolucradoFormUtil.extraerDatosInvolucradoForm(
					expedienteDTO, retorno, form, request);
			
			if (logger.isDebugEnabled()) {
				logger.debug("::::::::::::::INVOLUCRADO:::::::::Ingresar:::::::::::"+ involucradoDTO);
				logger.debug("RETORNO:");
				logger.debug("Nombre:"+retorno.getNombre());
			}
			// TODO:Setear los valores de servidor publico en el dto que es
			// pasado para guardar los datos del individuo
			Long resp=null;
			IngresarIndividuoForm forma = (IngresarIndividuoForm) form;
			String idPropParaDefensor=request.getParameter("idPropParaDefensor");
			
			if(forma.getCalidadDelIndividuo().equals(CALIDAD_DEFENSOR)||forma.getCalidadDelIndividuo().equals(CALIDAD_DEFENSOR_PRIVADO)){
				resp = involucradoDelegate.guardarDefensorAsignadoInvolucrado(involucradoDTO, Long.parseLong(idPropParaDefensor));
			}else{
				resp = involucradoDelegate.guardarInvolucrado(involucradoDTO);
				logger.info(".::Aqui se manda a guardar el individuo::.");
				logger.info("Nombre: " + involucradoDTO.getNombreCompleto());
				logger.info("Estado: " + involucradoDTO.getEsVivo());
				logger.info(".::::::::::::::::FIN:::::::::::::::::::::.");
			}
			
			logger.info("el valor de la respuesta es:" + resp);

			String xml = null;
			PrintWriter pw = null;
			XStream converter=new XStream();
			converter.alias("IngresarIndividuoForm",IngresarIndividuoForm.class);
			if(involucradoDTO.getEsDetenido() != null && involucradoDTO.getEsDetenido()){
				retorno.setEstaDetenido(involucradoDTO.getEsDetenido());
			}
			retorno.setIdIndividuo(resp);
			
			//TODO JORGE Actualizar la forma que se pasa del actioForm
			//Revisar que NO se guarde dos nombre demograficos.
			//Revisar en vista el impacto  
			xml = converter.toXML(retorno);
			if(logger.isDebugEnabled())
			{
				logger.info("Ingresar Individuo::" + xml);
			}
			response.setContentType("text/xml");
			pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			logger.info(e.getCause(), e);
			IngresarIndividuoForm retorno = new IngresarIndividuoForm();
			retorno.setIdIndividuo(0L);
			XStream converter=new XStream();
			String xml = converter.toXML(retorno);
			PrintWriter pw = null;
			response.setContentType("text/xml");
			pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		}
		return null;
	}

	/**
	 * Metodo utilizado para guardar los datos de un ciudadano con
	 * calidad de denunciante  CU Registrar Datos Ciudadano
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward guardarCiudadano(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			logger.info("ejecutando Action guardarCiudadano");
			String numeroExpediente=request.getParameter("numeroExpediente");
			logger.info("ejecutando Action guardarCiudadano mas numero de expediente=#####"+numeroExpediente);
			IngresarIndividuoForm forma = (IngresarIndividuoForm) form;
			InvolucradoDTO involucradoDTO = new InvolucradoDTO();
			involucradoDTO.setExpedienteDTO(super.getExpedienteTrabajo(request,numeroExpediente));
			logger.info("#########$$$$$$$$$Numero expedinte:"+super.getExpedienteTrabajo(request,numeroExpediente));
			logger.info("&&&&&&&&&&&&&Forma:"+forma);
			CalidadDTO calidadDTO = new CalidadDTO();

			involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
			involucradoDTO.setElementoId(Elementos.PERSONA.getValorId());

			calidadDTO.setCalidades(Calidades.DENUNCIANTE);			
			involucradoDTO.setCalidadDTO(calidadDTO);
			involucradoDTO.setMotivoComparecencia(forma.getMotivoComparecencia());

			List<NombreDemograficoDTO> lstDatosGenerales = new ArrayList<NombreDemograficoDTO>();
			NombreDemograficoDTO datosGenerales = new NombreDemograficoDTO();
			datosGenerales.setNombre(forma.getNombre());
			datosGenerales.setApellidoPaterno(forma.getApellidoPaterno());
			datosGenerales.setApellidoMaterno(forma.getApellidoMaterno());
			datosGenerales.setCurp(forma.getCurp());
			datosGenerales.setRfc(forma.getRfc());
			datosGenerales.setSexo(forma.getSexo());
			
			if (forma.getFechaNacimiento() != null && !forma.getFechaNacimiento().trim().equals("")) {
				datosGenerales.setFechaNacimiento(DateUtils.obtener(forma
						.getFechaNacimiento()));
			}

			datosGenerales.setEdadAproximada(forma.getEdadAproximada());
			lstDatosGenerales.add(datosGenerales);

			
			ValorDTO valorGenerico = new ValorDTO();

			valorGenerico = new ValorDTO();
			Long estadoCivil=Long.parseLong(forma.getEstadoCivil());
			valorGenerico.setIdCampo(estadoCivil);
			// log.info("+++++++++++++++++++++++++getEstadoCivil forma:" +
			// forma.getEstadoCivil());
			involucradoDTO.setValorIdEstadoCivil(valorGenerico);

			// TODO: Falta a de alias hasta nacionalidad
			
			involucradoDTO.setNombresDemograficoDTO(lstDatosGenerales);
			involucradoDTO.setEsVivo(forma.getEsVivo());
			DomicilioDTO domicilio = new DomicilioDTO();
			AsentamientoDTO asentamientoDTO = new AsentamientoDTO();

			EntidadFederativaDTO estado = new EntidadFederativaDTO();
			if (!forma.getEntidadFederativa().equals("")
					&& !forma.getEntidadFederativa().equals("-1")) {
				estado.setEntidadFederativaId(new Long(forma
						.getEntidadFederativa()));
			}

			if (!forma.getPais().equals("") && !forma.getPais().equals("-1")) {
				valorGenerico = new ValorDTO();
				valorGenerico.setValor(forma.getPais());
				estado.setValorIdPais(valorGenerico);
			}
			if (!forma.getDelegacionMunicipio().equals("")
					&& !forma.getDelegacionMunicipio().equals("-1")) {
				MunicipioDTO municipio = new MunicipioDTO(new Long(
						forma.getDelegacionMunicipio()), "", estado);
				asentamientoDTO.setMunicipioDTO(municipio);
				domicilio.setMunicipioDTO(municipio);
			}

			CiudadDTO ciudad = new CiudadDTO();
			if (!forma.getCiudad().equals("")
					&& !forma.getCiudad().equals("-1")) {
				ciudad.setCiudadId(new Long(forma.getCiudad()));
			}
			asentamientoDTO.setCiudadDTO(ciudad);

			if (!forma.getAsentamientoColonia().equals("")
					&& !forma.getAsentamientoColonia().equals("-1")) {
				asentamientoDTO.setAsentamientoId(new Long(forma
						.getAsentamientoColonia()));
			}
			domicilio.setAsentamientoDTO(asentamientoDTO);
			domicilio.setCalle(forma.getCalle());
			domicilio.setNumeroExterior(forma.getNumExterior());
			domicilio.setNumeroInterior(forma.getNumInterior());
			domicilio.setEntreCalle1(forma.getEntreCalle());
			domicilio.setEntreCalle2(forma.getYcalle());
			domicilio.setAlias(forma.getAliasDomicilio());
			domicilio.setEdificio(forma.getEdificio());
			domicilio.setReferencias(forma.getReferencias());

			Date fechaCaptura=new Date();
			SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");
			String fech=formato.format(fechaCaptura);

			domicilio.setFechaCreacionElemento(DateUtils.obtener(fech));
			if (!forma.getTipoCalle().equals("")
					&& !forma.getTipoCalle().equals("-1")) {
				domicilio.setValorCalleId(new ValorDTO(new Long(forma
						.getTipoCalle())));// Tipo de calle
			}

			calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.DOMICILIO);
			domicilio.setCalidadDTO(calidadDTO);
			domicilio.setExpedienteDTO(super.getExpedienteTrabajo(request,numeroExpediente));
			involucradoDTO.setDomicilio(domicilio);

			if (logger.isDebugEnabled()) {
				logger.debug("::::::::::::::CIUDADANO:::::::::Ingresar:::::::::::"+ involucradoDTO);
			}
			// TODO:Setear los valores de servidor publico en el dto que es
			// pasado para guardar los datos del individuo
			Long resp = involucradoDelegate.guardarInvolucrado(involucradoDTO);
			logger.info("el valor de la respuesta es:" + resp);

			String xml = null;
			PrintWriter pw = null;
			XStream converter=new XStream();
			converter.alias("IngresarIndividuoForm",IngresarIndividuoForm.class);
			IngresarIndividuoForm retorno = new IngresarIndividuoForm();
			xml = converter.toXML(retorno);
			response.setContentType("text/xml");
			pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			logger.info(e.getCause(), e);
		}
		return null;
	}

	
	/**
	 * Metodo utilizado para realizar la carga del combo Calidad
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward obtenerListaIndividuos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			logger.info("ejecutando Action obtenerListaIndividuos");
			IngresarIndividuoForm forma = (IngresarIndividuoForm) form;
			List<InvolucradoViewDTO> lstInvolucrados = new ArrayList<InvolucradoViewDTO>();
			ExpedienteDTO expedienteDTO = expedienteDelegate
					.obtenerExpediente(super.getExpedienteTrabajo(request,"OBTENER_EL_NUMERO_DEL_FRONT"));
			if (forma.getCalidadDelIndividuo().equals(
					CALIDAD_PROBABLE_RESPONSABLE)) {
				logger.info("Es probable responsable");

				List<InvolucradoDTO> lstFisicos = expedienteDTO
						.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA);
				for (InvolucradoDTO involucradoDTO : lstFisicos) {
					InvolucradoViewDTO involucradoView = new InvolucradoViewDTO();
					List<NombreDemograficoDTO> lstNombresDemografico = involucradoDTO
							.getNombresDemograficoDTO();
					for (NombreDemograficoDTO nombreDemograficoDTO : lstNombresDemografico) {
						involucradoView.setNombre(nombreDemograficoDTO
								.getNombre());
						involucradoView.setApellidoPaterno(nombreDemograficoDTO
								.getApellidoPaterno());
						involucradoView.setApellidoMaterno(nombreDemograficoDTO
								.getApellidoMaterno());
					}
					involucradoView.setInvolucradoId(involucradoDTO
							.getElementoId());
					lstInvolucrados.add(involucradoView);
				}
				List<InvolucradoDTO> lstMorales = expedienteDTO
						.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION);
				for (InvolucradoDTO involucradoDTO : lstMorales) {
					InvolucradoViewDTO involucradoView = new InvolucradoViewDTO();
					List<NombreDemograficoDTO> lstNombresDemografico = involucradoDTO
							.getNombresDemograficoDTO();
					for (NombreDemograficoDTO nombreDemograficoDTO : lstNombresDemografico) {
						involucradoView.setNombre(nombreDemograficoDTO
								.getNombre());
						involucradoView.setApellidoPaterno(nombreDemograficoDTO
								.getApellidoPaterno());
						involucradoView.setApellidoMaterno(nombreDemograficoDTO
								.getApellidoMaterno());
					}
					involucradoView.setInvolucradoId(involucradoDTO
							.getElementoId());
					lstInvolucrados.add(involucradoView);
				}
			} else if (forma.getCalidadDelIndividuo().equals(
					CALIDAD_VICTIMA_PERSONA)) {
				logger.info("Es victima");

				List<InvolucradoDTO> lstVictimas = expedienteDTO
						.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA);
				for (InvolucradoDTO involucradoDTO : lstVictimas) {
					InvolucradoViewDTO involucradoView = new InvolucradoViewDTO();
					List<NombreDemograficoDTO> lstNombresDemografico = involucradoDTO
							.getNombresDemograficoDTO();
					for (NombreDemograficoDTO nombreDemograficoDTO : lstNombresDemografico) {
						involucradoView.setNombre(nombreDemograficoDTO
								.getNombre());
						involucradoView.setApellidoPaterno(nombreDemograficoDTO
								.getApellidoPaterno());
						involucradoView.setApellidoMaterno(nombreDemograficoDTO
								.getApellidoMaterno());
					}
					involucradoDTO.setEsVivo(forma.getEsVivo());
					logger.info("ATENCION! Es Vivo: " + forma.getEsVivo());
					involucradoView.setInvolucradoId(involucradoDTO
							.getElementoId());
					lstInvolucrados.add(involucradoView);
				}
			} else if (forma.getCalidadDelIndividuo().equals(
					CALIDAD_DENUNCIANTE)) {
				logger.info("Es denunciante");

				List<InvolucradoDTO> lstVictimas = expedienteDTO
						.getInvolucradoByCalidad(Calidades.DENUNCIANTE);
				for (InvolucradoDTO involucradoDTO : lstVictimas) {
					InvolucradoViewDTO involucradoView = new InvolucradoViewDTO();
					List<NombreDemograficoDTO> lstNombresDemografico = involucradoDTO
							.getNombresDemograficoDTO();
					for (NombreDemograficoDTO nombreDemograficoDTO : lstNombresDemografico) {
						involucradoView.setNombre(nombreDemograficoDTO
								.getNombre());
						involucradoView.setApellidoPaterno(nombreDemograficoDTO
								.getApellidoPaterno());
						involucradoView.setApellidoMaterno(nombreDemograficoDTO
								.getApellidoMaterno());
					}
					involucradoView.setInvolucradoId(involucradoDTO
							.getElementoId());
					lstInvolucrados.add(involucradoView);
				}
			} else if (forma.getCalidadDelIndividuo().equals(CALIDAD_TESTIGO)) {
				logger.info("Es testigo");

				List<InvolucradoDTO> lstVictimas = expedienteDTO
						.getInvolucradoByCalidad(Calidades.TESTIGO);
				for (InvolucradoDTO involucradoDTO : lstVictimas) {
					InvolucradoViewDTO involucradoView = new InvolucradoViewDTO();
					List<NombreDemograficoDTO> lstNombresDemografico = involucradoDTO
							.getNombresDemograficoDTO();
					for (NombreDemograficoDTO nombreDemograficoDTO : lstNombresDemografico) {
						involucradoView.setNombre(nombreDemograficoDTO
								.getNombre());
						involucradoView.setApellidoPaterno(nombreDemograficoDTO
								.getApellidoPaterno());
						involucradoView.setApellidoMaterno(nombreDemograficoDTO
								.getApellidoMaterno());
					}
					involucradoView.setInvolucradoId(involucradoDTO
							.getElementoId());
					lstInvolucrados.add(involucradoView);
				}
			} else if (forma.getCalidadDelIndividuo().equals(CALIDAD_TRADUCTOR)) {
				logger.info("Es traductor");

				List<InvolucradoDTO> lstVictimas = expedienteDTO
						.getInvolucradoByCalidad(Calidades.TRADUCTOR);
				for (InvolucradoDTO involucradoDTO : lstVictimas) {
					InvolucradoViewDTO involucradoView = new InvolucradoViewDTO();
					List<NombreDemograficoDTO> lstNombresDemografico = involucradoDTO
							.getNombresDemograficoDTO();
					for (NombreDemograficoDTO nombreDemograficoDTO : lstNombresDemografico) {
						involucradoView.setNombre(nombreDemograficoDTO
								.getNombre());
						involucradoView.setApellidoPaterno(nombreDemograficoDTO
								.getApellidoPaterno());
						involucradoView.setApellidoMaterno(nombreDemograficoDTO
								.getApellidoMaterno());
					}
					involucradoView.setInvolucradoId(involucradoDTO
							.getElementoId());
					lstInvolucrados.add(involucradoView);
				}
			} else if (forma.getCalidadDelIndividuo().equals(
					CALIDAD_QUIEN_DETUVO)) {
				logger.info("********************++Es quien detuvo**************************************");

				List<InvolucradoDTO> lstVictimas = expedienteDTO
						.getInvolucradoByCalidad(Calidades.QUIEN_DETUVO);
				logger.debug("victimas quien detuvo:"
						+ lstVictimas.size());
				for (InvolucradoDTO involucradoDTO : lstVictimas) {
					InvolucradoViewDTO involucradoView = new InvolucradoViewDTO();
					List<NombreDemograficoDTO> lstNombresDemografico = involucradoDTO
							.getNombresDemograficoDTO();
					for (NombreDemograficoDTO nombreDemograficoDTO : lstNombresDemografico) {
						involucradoView.setNombre(nombreDemograficoDTO
								.getNombre());
						involucradoView.setApellidoPaterno(nombreDemograficoDTO
								.getApellidoPaterno());
						involucradoView.setApellidoMaterno(nombreDemograficoDTO
								.getApellidoMaterno());
					}
					involucradoView.setInvolucradoId(involucradoDTO
							.getElementoId());
					lstInvolucrados.add(involucradoView);
				}
			}
			XStream converter=new XStream();
			converter.alias("lstInvolucrados", java.util.List.class);
			converter.alias("involucradoViewDTO", InvolucradoViewDTO.class);
			response.setContentType("text/xml");
			logger.info(converter.toXML(lstInvolucrados));
			// request.getSession().setAttribute("expediente", expedienteDTO);
			escribirRespuesta(response, converter.toXML(lstInvolucrados));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	
        
	/**
	 * Metodo utilizado para guardar un Acta Circunstancial
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward registrarActaCircunstanciada(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			Long idCompareciente = NumberUtils.toLong(request.getParameter("idCompareciente"),0L);
			logger.info("idCompareciente:" + idCompareciente);
			
			Long idDomicilio = NumberUtils.toLong(request.getParameter("idDomicilio"),0L);
			logger.info("idDomicilio:" + idDomicilio);
			
			logger.info("ejecutando Action guardar Acta Circunstancial - registrarActaCircunstanciada");
			// hacemos el cast de la forma de Contacto Organizacion
			IngresarActaCircunstanciadaForm formaContOrg = (IngresarActaCircunstanciadaForm) form;
			
			logger.info("FORMA ACTA CIRCUNSTANCIAL:::::::::::::::::::::::");
			
			//revisamos q los datos generales no vengan nulos
			if (StringUtils.isBlank(formaContOrg.getNombre())) {
				formaContOrg.setNombre("");
			}
			if (StringUtils.isBlank(formaContOrg.getApellidoPaterno())) {
				formaContOrg.setApellidoPaterno("");
			}
			if (StringUtils.isBlank(formaContOrg.getApellidoMaterno())) {
				formaContOrg.setApellidoMaterno("");
			}
			if (StringUtils.isBlank(formaContOrg.getCurp())) {
				formaContOrg.setCurp("");
			}
			if (StringUtils.isBlank(formaContOrg.getRfc())) {
				formaContOrg.setRfc("");
			}

			if (StringUtils.isBlank(formaContOrg.getFechaIngreso())) {
				formaContOrg.setFechaIngreso("");
			}
			if (StringUtils.isBlank(formaContOrg.getIdioma())) {
				formaContOrg.setIdioma("");
			}
			if (StringUtils.isBlank(formaContOrg.getEscolaridad())) {
				formaContOrg.setEscolaridad("");
			}
			if (StringUtils.isBlank(formaContOrg.getEstadoCivil())) {
				formaContOrg.setEstadoCivil("");
			}
			if (StringUtils.isBlank(formaContOrg.getSexo())) {
				formaContOrg.setSexo("");
			}
			if (StringUtils.isBlank(formaContOrg.getFechaNacimiento())) {
				formaContOrg.setFechaNacimiento("");
			}
			if (formaContOrg.getEdadAproximada()==null) {
				formaContOrg.setEdadAproximada(Short.parseShort("0"));
			}
			if (StringUtils.isBlank(formaContOrg.getAlias())) {
				formaContOrg.setAlias("");
			}
			if (StringUtils.isBlank(formaContOrg.getOcupacion())) {
				formaContOrg.setOcupacion("");
			}
			if (StringUtils.isBlank(formaContOrg.getNacionalidad())) {
				formaContOrg.setNacionalidad("");
			}
			if (StringUtils.isBlank(formaContOrg.getPaisNacimiento())) {
				formaContOrg.setPaisNacimiento("");
			}
			if (StringUtils.isBlank(formaContOrg.getEntFederativaNacimiento())) {
				formaContOrg.setEntFederativaNacimiento("");
			}
			if (StringUtils.isBlank(formaContOrg.getMunicipioNacimiento())) {
				formaContOrg.setMunicipioNacimiento("");
			}
			logger.info("ACTA CIRCUNSTANCIAL:::: Revision datos generales....");
			//FIN revisamos q los datos generales no vengan nulos

			// revisamos que los datos de domicilio no sea nula

			if (StringUtils.isBlank(formaContOrg.getCodigoPostal())) {
				formaContOrg.setCodigoPostal("");
			}


			if (StringUtils.isBlank(formaContOrg.getCalle())) {
				formaContOrg.setCalle("");
			}

			if (StringUtils.isBlank(formaContOrg.getNumExterior())) {
				formaContOrg.setNumExterior("");
			}

			if (StringUtils.isBlank(formaContOrg.getNumInterior())) {
				formaContOrg.setNumInterior("");
			}

			if (StringUtils.isBlank(formaContOrg.getReferencias())) {
				formaContOrg.setReferencias("");
			}

			if (StringUtils.isBlank(formaContOrg.getEntreCalle())) {
				formaContOrg.setEntreCalle("");
			}

			if (StringUtils.isBlank(formaContOrg.getYcalle())) {
				formaContOrg.setYcalle("");
			}

			if (StringUtils.isBlank(formaContOrg.getAliasDomicilio())) {
				formaContOrg.setAliasDomicilio("");
			}

			if (StringUtils.isBlank(formaContOrg.getEdificio())) {
				formaContOrg.setEdificio("");
			}

			if (StringUtils.isBlank(formaContOrg.getLongitud())) {
				formaContOrg.setLongitud("");
			}

			if (StringUtils.isBlank(formaContOrg.getLatitud())) {
				formaContOrg.setLatitud("");
			}
			logger.info("ACTA CIRCUNSTANCIAL:::: domicilio....");
			// FIN revisamos que los datos de domicilio no sea nula
			
			// Declaramos la instancia a guardar en BD
			
			//revisamos que los datos del documento de identificacion no vengan vacios
			if (StringUtils.isBlank(formaContOrg.getDocIdentificacion())) {
				//formaContOrg.setDocIdentificacion("");
				formaContOrg.setDocIdentificacion(null);
			}
			if (StringUtils.isBlank(formaContOrg.getFolioDoc())) {
				formaContOrg.setFolioDoc(null);
			}
			
			InvolucradoDTO contactoOrgDTO=new InvolucradoDTO();
			if(idCompareciente > 0)
				contactoOrgDTO.setElementoId(idCompareciente);
			
			//seteo el expediente
			logger.info("getExpedienteTrabajo_Ing_ActaCirc :: ["+formaContOrg.getNumExpediente().trim()+"]");
			ExpedienteDTO expedienteDTO = super.getExpedienteTrabajo(request,formaContOrg.getNumExpediente().trim());
			logger.info("expediente obtenido de sesion Ing_ActaCirc :: "+expedienteDTO);
			contactoOrgDTO.setExpedienteDTO(expedienteDTO);
			contactoOrgDTO.setFechaCreacionElemento(new Date());
			logger.info("ACTA CIRCUNSTANCIAL:::: Seteo expediente....");
			//seteo el usuario 				
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setIdUsuario(Long.parseLong(formaContOrg.getIdUsuario()));
			contactoOrgDTO.setUsuario(usuarioDTO);
			logger.info("ACTA CIRCUNSTANCIAL:::: Seteo usuario....");
					
			// Seteamos el domicilio del contacto
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.DOMICILIO);
			
			// Encapsulamos la informacion del domicilio
			if (Long.parseLong(formaContOrg.getPais()) == 10)// Mexico
			{
				DomicilioDTO domicilioDTO = new DomicilioDTO();
				if(idCompareciente > 0)
					domicilioDTO.setElementoId(idDomicilio);
				domicilioDTO.setLatitud(formaContOrg.getLatitud());
				domicilioDTO
						.setLongitud(formaContOrg.getLongitud());
				domicilioDTO.setEdificio(formaContOrg.getEdificio());
				domicilioDTO.setAlias(formaContOrg.getAliasDomicilio());
				domicilioDTO.setEntreCalle2(formaContOrg.getYcalle());
				domicilioDTO.setEntreCalle1(formaContOrg.getEntreCalle());
				domicilioDTO.setReferencias(formaContOrg.getReferencias());
				domicilioDTO.setNumeroInterior(formaContOrg.getNumInterior());
				domicilioDTO.setNumeroExterior(formaContOrg.getNumExterior());
				domicilioDTO.setCalle(formaContOrg.getCalle());
				if (StringUtils.isNotBlank(formaContOrg.getTipoCalle() )&& !formaContOrg.getTipoCalle().equals("-1")) {
					domicilioDTO.setValorCalleId(new ValorDTO(Long
							.parseLong(formaContOrg.getTipoCalle())));
				}
				domicilioDTO.setCalidadDTO(calidadDTO);
				domicilioDTO.setExpedienteDTO(expedienteDTO);
				domicilioDTO.setFechaCreacionElemento(new Date());
				// delcaramos el nuevo asentamiento
				AsentamientoDTO asentamientoDTO = new AsentamientoDTO();
				if (StringUtils.isNotBlank(formaContOrg.getAsentamientoColonia()) && !formaContOrg.getAsentamientoColonia().equals("-1")) {
					asentamientoDTO.setAsentamientoId(Long.parseLong(formaContOrg
							.getAsentamientoColonia()));
				}
				asentamientoDTO.setCodigoPostal(formaContOrg.getCodigoPostal());

				// Declaramos el tipo de asentamiento
				if (StringUtils.isNotBlank(formaContOrg.getTipoAsentamiento()) && !formaContOrg.getTipoAsentamiento().equals("-1")) {
					TipoAsentamientoDTO tipoAsentamientoDTO = new TipoAsentamientoDTO(
							Long.parseLong(formaContOrg.getTipoAsentamiento()), "");
					asentamientoDTO.setTipoAsentamientoDTO(tipoAsentamientoDTO);
				}
				// Declaramos el municipio
				MunicipioDTO municipioDTO = new MunicipioDTO();
				if (StringUtils.isNotBlank(formaContOrg.getDelegacionMunicipio()) && !formaContOrg.getDelegacionMunicipio().equals("-1")) {
					municipioDTO.setMunicipioId(Long.parseLong(formaContOrg
							.getDelegacionMunicipio()));
				}
				
				if (!(formaContOrg.getLatitudN()== null) && !formaContOrg.getLatitudN().equals("")) {
					String lat= formaContOrg.getLatitudN()+formaContOrg.getLatitudGrados()+"°"+formaContOrg.getLatitudMinutos()+"'"+formaContOrg.getLatitudSegundos()+"\"";
					domicilioDTO.setLatitud(lat);
				}
				if (!(formaContOrg.getLongitudE()== null) && !formaContOrg.getLongitudE().equals("")) {
					String longitud= formaContOrg.getLongitudE()+formaContOrg.getLongitudGrados()+"°"+formaContOrg.getLongitudMinutos()+"'"+formaContOrg.getLongitudSegundos()+"\"";
					domicilioDTO.setLongitud(longitud);
				}
				
				asentamientoDTO.setMunicipioDTO(municipioDTO);

				// declaramos la Ciudad
				CiudadDTO ciudadDTO = new CiudadDTO();
				if (StringUtils.isNotBlank(formaContOrg.getCiudad()) && !formaContOrg.getCiudad().equals("-1")) {
					ciudadDTO.setCiudadId(Long.parseLong(formaContOrg.getCiudad()));
				}
				// declaramos la entidad federativa
				EntidadFederativaDTO entidadFederativaDTO = new EntidadFederativaDTO();
				if (StringUtils.isNotBlank(formaContOrg.getEntidadFederativa()) && !formaContOrg.getEntidadFederativa().equals("-1")) {
					entidadFederativaDTO.setEntidadFederativaId(Long
							.parseLong(formaContOrg.getEntidadFederativa()));
				}
				if (StringUtils.isNotBlank(formaContOrg.getPais()) && !formaContOrg.getPais().equals("-1")) {
					entidadFederativaDTO.setValorIdPais(new ValorDTO(Long
							.parseLong(formaContOrg.getPais())));
				}
				ciudadDTO.setEntidadFederativaDTO(entidadFederativaDTO);
				asentamientoDTO.setCiudadDTO(ciudadDTO);
				domicilioDTO.setCiudadDTO(ciudadDTO);
				domicilioDTO.setEntidadDTO(entidadFederativaDTO);
				domicilioDTO.setAsentamientoDTO(asentamientoDTO);
				domicilioDTO.setMunicipioDTO(municipioDTO);
					
				// seteamos el domicilio al Hecho
				contactoOrgDTO.setDomicilio(domicilioDTO);
				
				//REVISAMOS SI ES LA MISMA DIRECCION PARA EL DOMICILIO DE NOTIFICACIONES
//				if(formaContOrg.getMismoDomicilioNotificaciones().equals("true"))
//				{
//					contactoOrgDTO.setDomicilioNotificacion(domicilioDTO);
//				}
				
			} else// Otro pais
			{
				DomicilioExtranjeroDTO domExtranjreoDTO = new DomicilioExtranjeroDTO();
				if(idCompareciente > 0)
					domExtranjreoDTO.setElementoId(idDomicilio);

				domExtranjreoDTO.setLatitud(formaContOrg
						.getLatitud());
				domExtranjreoDTO.setLongitud(formaContOrg
						.getLongitud());
				domExtranjreoDTO.setEdificio(formaContOrg.getEdificio());
				domExtranjreoDTO.setAlias(formaContOrg.getAliasDomicilio());
				domExtranjreoDTO.setEntreCalle2(formaContOrg.getYcalle());
				domExtranjreoDTO.setEntreCalle1(formaContOrg.getEntreCalle());
				domExtranjreoDTO.setReferencias(formaContOrg.getReferencias());
				domExtranjreoDTO.setNumeroInterior(formaContOrg.getNumInterior());
				domExtranjreoDTO.setNumeroExterior(formaContOrg.getNumExterior());
				domExtranjreoDTO.setCalle(formaContOrg.getCalle());
				domExtranjreoDTO.setPais(formaContOrg.getPais());
				domExtranjreoDTO.setPaisValor(new ValorDTO(new Long(formaContOrg.getPais())));
				domExtranjreoDTO.setCodigoPostal(formaContOrg.getCodigoPostal());
				domExtranjreoDTO.setCiudad(formaContOrg.getCiudad());
				domExtranjreoDTO.setMunicipio(formaContOrg.getDelegacionMunicipio());
				domExtranjreoDTO.setAsentamientoExt(formaContOrg
						.getAsentamientoColonia());
				domExtranjreoDTO.setEstado(formaContOrg.getEntidadFederativa());
				domExtranjreoDTO.setCalidadDTO(calidadDTO);
				domExtranjreoDTO.setExpedienteDTO(expedienteDTO);
				domExtranjreoDTO.setFechaCreacionElemento(new Date());
				domExtranjreoDTO.setCalidadDTO(calidadDTO);
				// seteamos el domicilio extranjero al Hecho
				contactoOrgDTO.setDomicilio(domExtranjreoDTO);
				
				//REVISAMOS SI ES LA MISMA DIRECCION PARA EL DOMICILIO DE NOTIFICACIONES
//				if(formaContOrg.getMismoDomicilioNotificaciones().equals("true"))
//				{
//					contactoOrgDTO.setDomicilioNotificacion(domExtranjreoDTO);
//				}
			}
			logger.info("REP LEGAL:::: Seteo domicilio notificaciones....");
			//FIN seteo domicilio
						
			NombreDemograficoDTO nombre= new NombreDemograficoDTO();
			nombre.setNombre(formaContOrg.getNombre());
			nombre.setApellidoPaterno(formaContOrg.getApellidoPaterno());
			nombre.setApellidoMaterno(formaContOrg.getApellidoMaterno());
			nombre.setRfc(formaContOrg.getRfc());
			nombre.setCurp(formaContOrg.getCurp());
			nombre.setSexo(formaContOrg.getSexo());
			
			if(StringUtils.isNotBlank(formaContOrg.getFechaNacimiento()))
			{
				nombre.setFechaNacimiento(DateUtils.obtener(formaContOrg.getFechaNacimiento()));
			}
			else
			{
				nombre.setFechaNacimiento(null);
			}
			
			nombre.setEdadAproximada(formaContOrg.getEdadAproximada());
			
			
			List<NombreDemograficoDTO> nombreL= new ArrayList<NombreDemograficoDTO>();
			nombreL.add(nombre);
			contactoOrgDTO.setNombresDemograficoDTO(nombreL);
			contactoOrgDTO.setFechaCreacionElemento(new Date());
			
			if(formaContOrg.getPais()!= null && !formaContOrg.getPais().isEmpty() && !formaContOrg.getPais().equals("-1")){
				ValorDTO paisValorDTO=new ValorDTO(Long.parseLong(formaContOrg.getPais()));
				nombre.setPaisValorDTO(paisValorDTO);
				EntidadFederativaDTO entidadFederativaDTO=new EntidadFederativaDTO();
				if(formaContOrg.getEntFederativaNacimiento()!= null && !formaContOrg.getEntFederativaNacimiento().isEmpty() && !formaContOrg.getEntFederativaNacimiento().equals("-1"))
				{
					entidadFederativaDTO.setEntidadFederativaId(Long.parseLong(formaContOrg.getEntFederativaNacimiento()));
					nombre.setEntidadFederativaDTO(entidadFederativaDTO);
				}
				else
				{
					nombre.setEntidadFederativaDTO(null);
				}
				
				MunicipioDTO municipioDTO = new MunicipioDTO();
				if(formaContOrg.getMunicipioNacimiento()!= null && !formaContOrg.getMunicipioNacimiento().isEmpty() &&  !formaContOrg.getMunicipioNacimiento().equals("-1"))
				{
					municipioDTO.setMunicipioId((Long.parseLong(formaContOrg.getMunicipioNacimiento())));
					nombre.setMunicipioDTO(municipioDTO);
				}
				else
				{
					nombre.setMunicipioDTO(null);
				}
				
			}else{
				nombre.setPaisValorDTO(null);
			}
			//seteo la calidad
			CalidadDTO calidadInvDTO=new CalidadDTO();
			calidadInvDTO.setCalidades(Calidades.DENUNCIANTE);
			contactoOrgDTO.setCalidadDTO(calidadInvDTO);
			//Tipo de persona
			contactoOrgDTO.setTipoPersona(TIPO_PERSONA_FISICA);
			
			
			if(formaContOrg.getIdioma() != null && !formaContOrg.getIdioma().isEmpty() && !formaContOrg.getIdioma().equals("- Selecciona -")){
				ValorDTO idiomaDTO= new ValorDTO(Long.parseLong(formaContOrg.getIdioma()));
				contactoOrgDTO.setValorIdIdioma(idiomaDTO);
			}
			
			//ValorDTO religionDTO=new ValorDTO(Long.parseLong(formaContOrg.getReligion()));
			//contactoOrgDTO.setValorIdReligion(religionDTO);
			if(formaContOrg.getEscolaridad() != null && !formaContOrg.getEscolaridad().isEmpty() && !formaContOrg.getEscolaridad().equals("- Selecciona -")){
				ValorDTO escolaridadDTO= new ValorDTO(Long.parseLong(formaContOrg.getEscolaridad()));
				contactoOrgDTO.setValorIdEscolaridad(escolaridadDTO);
			}else{
				contactoOrgDTO.setValorIdEscolaridad(null);
			}
			
			if(formaContOrg.getEstadoCivil() != null && !formaContOrg.getEstadoCivil().isEmpty() && !formaContOrg.getEstadoCivil().equals("- Selecciona -"))
			{
				ValorDTO estadoCivilDTO = new ValorDTO(Long.parseLong(formaContOrg.getEstadoCivil()));
				contactoOrgDTO.setValorIdEstadoCivil(estadoCivilDTO);
			}
			else
			{
				contactoOrgDTO.setValorIdEstadoCivil(null);
			}
			//private String alias; NO APLICA PARA CONTACTO ORGANIZACIONAL
			contactoOrgDTO.setAliasInvolucradoDTO(InvolucradoFormUtil.obtenerAliasInvolucrado(formaContOrg));

			//seteo las ocupaciones
			if(!formaContOrg.getOcupacion().equalsIgnoreCase("") && formaContOrg.getOcupacion()!=null && !formaContOrg.getOcupacion().equalsIgnoreCase("undefined"))
			{
				//barremos las ocupaciones
				String[] ocupaciones=formaContOrg.getOcupacion().split(",");
				List<ValorDTO> ocupacionesL=new ArrayList<ValorDTO>();
				for (String ocupacion : ocupaciones) 
				{
				 ValorDTO ocupacionV=new ValorDTO(Long.parseLong(ocupacion));
				 ocupacionesL.add(ocupacionV);
				}
				contactoOrgDTO.setValorIdOcupacion(ocupacionesL);
			}
			//seteo las nacionalidades
			if(!formaContOrg.getNacionalidad().equalsIgnoreCase(""))
			{
				//barremos las ocupaciones
				String[] nacionalidades=formaContOrg.getNacionalidad().split(",");
				List<ValorDTO> nacionalidadesL=new ArrayList<ValorDTO>();
				for (String nacionalidad : nacionalidades) 
				{
				 ValorDTO nacionalidadV=new ValorDTO(Long.parseLong(nacionalidad));
				 nacionalidadesL.add(nacionalidadV);
				}
				contactoOrgDTO.setValorIdNacionalidad(nacionalidadesL);
			}
			logger.info("ACTA CIRCUNSTANCIAL:::: Seteo datos generales....");
			//FIN seteo datos generales
			
			//seteamos los medio de contacto
			List<TelefonoDTO> lstTelefonos = new ArrayList<TelefonoDTO>();
			String strTelefonos = formaContOrg.getMedioContactoTelefono();
			StringTokenizer lstStrTelefonos = new StringTokenizer(strTelefonos,"|");
			while (lstStrTelefonos.hasMoreElements()) {
				String strTelefono = (String) lstStrTelefonos.nextElement();
				String[] datosTelefono = strTelefono.split(",");

				TelefonoDTO telefono = new TelefonoDTO();

				ValorDTO valorTipoTelefono = new ValorDTO();
				if(datosTelefono.length!=0){
					valorTipoTelefono.setIdCampo(new Long(datosTelefono[0]));
					logger.info("&&&&Telefono:"+datosTelefono[0]);
					telefono.setValorTipoTelefono(valorTipoTelefono);
					telefono.setCodigoPais(datosTelefono[1]);
					logger.info("&&&&Telefono:"+datosTelefono[1]);
					telefono.setCodigoArea(datosTelefono[2]);
					logger.info("&&&&Telefono:"+datosTelefono[2]);
					telefono.setNumeroTelefonico(datosTelefono[3]);
					logger.info("&&&&Telefono:"+datosTelefono[3]);
					lstTelefonos.add(telefono);
				}
				
			}
			contactoOrgDTO.setTelefonosDTO(lstTelefonos);

			List<CorreoElectronicoDTO> lstCorreos = new ArrayList<CorreoElectronicoDTO>();
			if(!formaContOrg.getMedioContactoCorreo().trim().isEmpty()){
				String[] datosCorreo = formaContOrg.getMedioContactoCorreo().split(",");
				for (int i = 0; i < datosCorreo.length; i++) {
					CorreoElectronicoDTO correo = new CorreoElectronicoDTO();
					correo.setDireccionElectronica(datosCorreo[i]);
					lstCorreos.add(correo);
				}
			}
			contactoOrgDTO.setCorreosDTO(lstCorreos);
			//fin seteo medios de contacto
			
			//guardamos el acta circunstanciada
			HechoDTO hechoDTO=new HechoDTO();
			hechoDTO.setDescNarrativa(formaContOrg.getMotivoComparecencia());
			hechoDTO.setExpediente(expedienteDTO);
			
			//revisamos si es un insert o un update
			if(formaContOrg.getIdHecho()!=0)
			{
				hechoDTO.setHechoId(formaContOrg.getIdHecho());
			}
			
			//INICIO asignacion documento de identificacion
			if(formaContOrg.getDocIdentificacion() != null){
			
				ValorDTO docIdentificacion=new ValorDTO();
				Long docIdentificacionId = NumberUtils.toLong(formaContOrg.getDocIdentificacion(),0L);
				
				if(docIdentificacionId > 0){
					docIdentificacion.setIdCampo(docIdentificacionId);
					contactoOrgDTO.setValorIdIdentificaion(docIdentificacion);
					if(formaContOrg.getFolioDoc() != null){
						contactoOrgDTO.setFolioIdentificacion(formaContOrg.getFolioDoc());
					}
				}
			}
			//FIN asignacion documento de identificacion
						
			ActaCircunstanciadaDTO actaDTO= new ActaCircunstanciadaDTO(contactoOrgDTO, hechoDTO);
			logger.info("A punto de guardar ACTA CIRCUNSTANCIAL - FIN ");
			
			expedienteDTO=expedienteDelegate.registrarActaCircunstanciada(actaDTO, expedienteDTO);
			
			// revisamos si el guardado fue exitoso para mandar el xml
			// correspondiente
			if (expedienteDTO != null
					&& expedienteDTO.getExpedienteId() != null) {
				logger.info("numeroExpediente_insercion_actaCircunstanciada:: "+expedienteDTO.getNumeroExpediente());
				//consultamos el Acta recien ingresada
				ExpedienteDTO expedienteDTOConsul=new ExpedienteDTO();
				expedienteDTOConsul.setNumeroExpediente(expedienteDTO.getNumeroExpediente());
				expedienteDTOConsul=expedienteDelegate.obtenerNumeroExpedienteByNumExp(expedienteDTOConsul,null);
				actaDTO=expedienteDelegate.consultarActaCircunstaciada(expedienteDTOConsul);
				// regresamos el XML del acata circunstanciada recien ingresada
				if (actaDTO != null && actaDTO.getHechoDTO()!= null && actaDTO.getHechoDTO().getHechoId()>0) {
					XStream converter=new XStream(); 			converter.alias("actaDTO", ActaCircunstanciadaDTO.class);
					String xml = converter.toXML(actaDTO);
					logger.info("acta_circunstanciada:: "+xml);
					escribirRespuesta(response, xml);
				} else {
					ActaCircunstanciadaDTO actadosDTO=new ActaCircunstanciadaDTO(null, new HechoDTO(0L));
					XStream converter=new XStream(); 			converter.alias("actaDTO", ActaCircunstanciadaDTO.class);
					String xml = converter.toXML(actadosDTO);
					logger.info("acta_circunstanciada_vacia:: "+xml);
					escribirRespuesta(response, xml);
				}
				
//				XStream converter=new XStream(); 			converter.alias("expedienteDTO", ExpedienteDTO.class);
//				String xml = converter.toXML(expedienteDTO);
//				logger.info("XML_registrarActaCircunstanciada:: "+xml);
//				escribirRespuesta(response, xml);
			} else {
				expedienteDTO.setExpedienteId(0L);
				XStream converter=new XStream(); 			converter.alias("expedienteDTO", ExpedienteDTO.class);
				String xml = converter.toXML(expedienteDTO);
				logger.info("XML_registrarActaCircunstanciada:: "+xml);
				escribirRespuesta(response, xml);
			}
			logger.info("Termina ejecucion Action guardar ACTA CIRCUNSTANCIAL - FIN ");
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
			escribir(response, "", e);
		}
		return null;
	}


	/**
	 * Metodo utilizado para consultar un Acta Circunstancial
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarActaCircunstanciada(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			 
			logger.info("ejecutando Action consultar Acta Circunstancial - consultarActaCircunstanciada");
			String numExpAtAdmin=request.getParameter("numExpAtAdmin");
			//obtenemos el expediente en sesion
			//ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request, numExpAtAdmin);
			ExpedienteDTO expedienteDTO=new ExpedienteDTO();
			expedienteDTO.setNumeroExpediente(numExpAtAdmin);
			expedienteDTO=expedienteDelegate.obtenerNumeroExpedienteByNumExp(expedienteDTO,null);
			//subimos el expediente a la sesion
			super.setExpedienteTrabajo(request, expedienteDTO);
			ActaCircunstanciadaDTO actaDTO=expedienteDelegate.consultarActaCircunstaciada(expedienteDTO);
			logger.info("numExpAdminAt :: "+numExpAtAdmin);
			
			if(actaDTO != null && actaDTO.getInvolucradoDTO()!=null && actaDTO.getInvolucradoDTO().getDomicilio()!=null){
				if(actaDTO.getInvolucradoDTO().getDomicilio().getLatitud()!=null && !actaDTO.getInvolucradoDTO().getDomicilio().getLatitud().equals("")){
					String latitud=actaDTO.getInvolucradoDTO().getDomicilio().getLatitud();
					
					String subLatitud=latitud.substring(1,latitud.length());//quitamos la letra de la cadena
					String[] arr=subLatitud.split("°");//separlo los grados de los minutos y segundos
					String[] arrDos=arr[1].split("'");//separamos los minutos y segundos
					String segundos=arrDos[1].substring(0,arrDos[1].length()-1);
					//seteamos los valores
					actaDTO.getInvolucradoDTO().getDomicilio().setLatitudN(latitud.substring(0,1));logger.info("domicilio hechoDTO NO NULL!!! 1");
					actaDTO.getInvolucradoDTO().getDomicilio().setLatitudGrados(arr[0]);logger.info("domicilio hechoDTO NO NULL!!! 2");
					actaDTO.getInvolucradoDTO().getDomicilio().setLatitudMinutos(arrDos[0]);logger.info("domicilio hechoDTO NO NULL!!! 3");
					actaDTO.getInvolucradoDTO().getDomicilio().setLatitudSegundos(segundos);logger.info("domicilio hechoDTO NO NULL!!! 4");
				}
				if(actaDTO.getInvolucradoDTO().getDomicilio().getLongitud()!=null && !actaDTO.getInvolucradoDTO().getDomicilio().getLongitud().equals("")){
					String longitud=actaDTO.getInvolucradoDTO().getDomicilio().getLongitud();
					
					String subLongitud=longitud.substring(1,longitud.length());//quitamos la letra de la cadena
					String[] arr=subLongitud.split("°");//separlo los grados de los minutos y segundos
					String[] arrDos=arr[1].split("'");//separamos los minutos y segundos
					String segundos=arrDos[1].substring(0,arrDos[1].length()-1);
					
					actaDTO.getInvolucradoDTO().getDomicilio().setLongitudE(longitud.substring(0,1));logger.info("domicilio hechoDTO NO NULL!!! 5");
					actaDTO.getInvolucradoDTO().getDomicilio().setLongitudGrados(arr[0]);logger.info("domicilio hechoDTO NO NULL!!! 6");
					actaDTO.getInvolucradoDTO().getDomicilio().setLongitudMinutos(arrDos[0]);logger.info("domicilio hechoDTO NO NULL!!! 7");
					actaDTO.getInvolucradoDTO().getDomicilio().setLongitudSegundos(segundos);logger.info("domicilio hechoDTO NO NULL!!! 8");
				}
			}
			
			// correspondiente
			if (actaDTO != null && actaDTO.getHechoDTO()!= null && actaDTO.getHechoDTO().getHechoId()>0) {
				XStream converter=new XStream(); 			converter.alias("actaDTO", ActaCircunstanciadaDTO.class);
				String xml = converter.toXML(actaDTO);
				logger.info("acta_circunstanciada:: "+xml);
				escribirRespuesta(response, xml);
			} else {
				ActaCircunstanciadaDTO actadosDTO=new ActaCircunstanciadaDTO(null, new HechoDTO(0L));
				XStream converter=new XStream(); 			converter.alias("actaDTO", ActaCircunstanciadaDTO.class);
				String xml = converter.toXML(actadosDTO);
				logger.info("acta_circunstanciada_vacia:: "+xml);
				escribirRespuesta(response, xml);
			}
			logger.info("Termina ejecucion Action guardar ACTA CIRCUNSTANCIAL - FIN ");
		} catch (NSJPNegocioException e) {
			ActaCircunstanciadaDTO actadosDTO=new ActaCircunstanciadaDTO(null, new HechoDTO(0L));
			XStream converter=new XStream(); 			converter.alias("actaDTO", ActaCircunstanciadaDTO.class);
			String xml = converter.toXML(actadosDTO);
			logger.info(xml);
			logger.error(e.getMessage(), e);
			escribir(response, "", e);
		}
		return null;
	}
	
	public ActionForward consultaContactosTelefono(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			String idInvolucrado=request.getParameter("idInvolucrado");
			logger.info("%%%%%%%%%%%%Este es el id del involucrado a consultar: "+idInvolucrado);
			InvolucradoDTO involucradoDTO=new InvolucradoDTO();
			involucradoDTO.setElementoId(Long.parseLong(idInvolucrado));
//			involucradoDTO.setCalidadDTO(calidadDTO);
			involucradoDTO=involucradoDelegate.obtenerInvolucrado(involucradoDTO);
			List<TelefonoDTO> telefonoDTOs = new ArrayList<TelefonoDTO>();
			telefonoDTOs= involucradoDTO.getTelefonosDTO();
				
			XStream converter=new XStream();
			converter.alias("listaTelefonos", java.util.ArrayList.class);
			converter.alias("TelefonoDTO", TelefonoDTO.class);
			logger.info("tels_medios_contacto:: "+converter.toXML(telefonoDTOs));
			
			logger.info("Lista de Telefonos" + telefonoDTOs);
			
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			
			writer.print("<rows>");		

			int lTotalRegistros = telefonoDTOs.size();
			writer.print("<records>" + lTotalRegistros + "</records>");
			//NSJYUCPROC2011333WT

			for(TelefonoDTO tareaDTO2 : telefonoDTOs){

				writer.print("<row id='"+tareaDTO2.getMedioDeContadoId()+ "'>");
				
				writer.print("<cell>");
				if(tareaDTO2.getValorTipoTelefono().getIdCampo() !=null){
					writer.print(tareaDTO2.getValorTipoTelefono().getIdCampo());
						}
				writer.print("</cell>");
				writer.print("<cell>");
				if(tareaDTO2.getValorTipoTelefono() !=null){
					writer.print(tareaDTO2.getValorTipoTelefono().getValor());
						}
				writer.print("</cell>");
				writer.print("<cell>");
				if(tareaDTO2.getCodigoPais() !=null){
					writer.print(tareaDTO2.getCodigoPais());
						}
				writer.print("</cell>");
				
				writer.print("<cell>");
				if(tareaDTO2.getCodigoArea() !=null){
					writer.print(tareaDTO2.getCodigoArea());
						}
				writer.print("</cell>");
				
				writer.print("<cell>");
				if(tareaDTO2.getNumeroTelefonico() !=null){
					writer.print(tareaDTO2.getNumeroTelefonico());
						}
				writer.print("</cell>");
				
				
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
			

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public ActionForward consultaContactosCorreo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			logger.info("Consulta Correos");
			String idInvolucrado=request.getParameter("idInvolucrado");
			logger.info("%%%%%%%%%%%%Este es el id del involucrado a consultar: "+idInvolucrado);
			InvolucradoDTO involucradoDTO=new InvolucradoDTO();
			involucradoDTO.setElementoId(Long.parseLong(idInvolucrado));
//			involucradoDTO.setCalidadDTO(calidadDTO);
			involucradoDTO=involucradoDelegate.obtenerInvolucrado(involucradoDTO);
			List<CorreoElectronicoDTO> correoElectronicoDTOs = new ArrayList<CorreoElectronicoDTO>();
			correoElectronicoDTOs= involucradoDTO.getCorreosDTO();
			
			XStream converter=new XStream();
			converter.alias("listaCorreos", java.util.ArrayList.class);
			converter.alias("CorreoElectronicoDTO", CorreoElectronicoDTO.class);
			logger.info("tels_medios_contacto:: "+converter.toXML(correoElectronicoDTOs));
			
			logger.info("Lista de Telefonos" + correoElectronicoDTOs);
			
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			
			writer.print("<rows>");		

			int lTotalRegistros = correoElectronicoDTOs.size();
			writer.print("<records>" + lTotalRegistros + "</records>");
			//NSJYUCPROC2011333WT

			for(CorreoElectronicoDTO  tareaDTO2 : correoElectronicoDTOs){

				writer.print("<row id='"+tareaDTO2.getMedioDeContadoId()+ "'>");
				
				writer.print("<cell>");
				if(tareaDTO2.getDireccionElectronica() !=null){
					writer.print(tareaDTO2.getDireccionElectronica());
						}
				writer.print("</cell>");
				
				
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
			


		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	
}