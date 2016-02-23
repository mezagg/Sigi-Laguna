/**
 * Nombre del Programa 		: IngresarHechosAction.java
 * Autor                     : ArmandoCT
 * Compania                  : Ultrasist
 * Proyecto                  : NSJP                    Fecha: 14/junio/2011
 * Marca de cambio        	: N/A
 * Descripcion General    	: Clase Action para ingresar objetos
 * Programa Dependiente  	: N/A
 * Programa Subsecuente 		: N/A
 * Cond. de ejecucion        : N/A
 * Dias de ejecucion         : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                     : N/A
 * Compania              	: N/A
 * Proyecto                 	: N/A                                 Fecha: N/A
 * Modificacion           	: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.web.hecho.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.enums.expediente.OrigenExpediente;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.delegate.catalogo.CatalogoDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.hecho.ConclusionHechoDelegate;
import mx.gob.segob.nsjp.delegate.hecho.HechoDelegate;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.domicilio.AsentamientoDTO;
import mx.gob.segob.nsjp.dto.domicilio.CiudadDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioExtranjeroDTO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.dto.domicilio.MunicipioDTO;
import mx.gob.segob.nsjp.dto.domicilio.TipoAsentamientoDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.hecho.ConclusionHechoDTO;
import mx.gob.segob.nsjp.dto.hecho.ConclusionNumeroExpedienteDTO;
import mx.gob.segob.nsjp.dto.hecho.HechoDTO;
import mx.gob.segob.nsjp.dto.hecho.TiempoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.hecho.form.HechoForm;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase Action para ingresar hechos.
 * 
 * @version 1.0
 * @author ArmandoCT
 * 
 */
public class IngresarHechosAction extends GenericAction {

	/* Log de clase */
	private static final Logger log = Logger
			.getLogger(IngresarHechosAction.class);

	@Autowired
	private HechoDelegate hechoDelegate;
	
	@Autowired
	private ExpedienteDelegate expedienteDelegate;
	
	@Autowired
	private CatalogoDelegate service;
	
	@Autowired
	private ConclusionHechoDelegate conclusionHechoDelegate;

	/**
	 * Metodo utilizado para guardar un hecho
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarHecho(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("ejecutando Action guardar hecho");
			HechoForm forma = (HechoForm) form;
			
			log.info("FORMA HECHO:::::::::::::::::::::::");
			
			//Revisamos que el id del hecho no sea nulo
			if(StringUtils.isBlank(forma.getIdHecho()))
			{
				forma.setGcDescripcionHecho("0");
				log.info("Id Hecho --->  ES:null");
			}
			
			log.info("Descripcion="+ forma.getGcDescripcionHecho());	
			
			
			//Revisamos que la descripcion del hecho no sea nula
			if(forma.getGcDescripcionHecho().equalsIgnoreCase("") ){
				forma.setGcDescripcionHecho("");
				log.info("Descripcion, ES:null");
			}
			
			//revisamos que los datos de domicilio no sea nula
			
			if(StringUtils.isBlank(forma.getPais())){
				forma.setPais("");
			}
			if(StringUtils.isBlank(forma.getCodigoPostal())){
				forma.setCodigoPostal("");
			}
			
			if(StringUtils.isBlank(forma.getEntidadFederativa()) || forma.getEntidadFederativa().equalsIgnoreCase("-1")){
				forma.setEntidadFederativa(null);
			}
			
			if(StringUtils.isBlank(forma.getCiudad()) || forma.getCiudad().equalsIgnoreCase("-1")){
				forma.setCiudad(null);
			}
			
			if(StringUtils.isBlank(forma.getDelegacionMunicipio()) || forma.getDelegacionMunicipio().equalsIgnoreCase("-1")){
				forma.setDelegacionMunicipio(null);
			}
			
			if(StringUtils.isBlank(forma.getAsentamientoColonia()) || forma.getAsentamientoColonia().equalsIgnoreCase("-1")){
				forma.setAsentamientoColonia(null);
			}
			
			if(StringUtils.isBlank(forma.getTipoAsentamiento()) || forma.getTipoAsentamiento().equalsIgnoreCase("-1")){
				forma.setTipoAsentamiento(null);
			}
			
			if(StringUtils.isBlank(forma.getTipoCalle()) || forma.getTipoCalle().equalsIgnoreCase("-1")){
				forma.setTipoCalle(null);
			}
			
			if(StringUtils.isBlank(forma.getCalle())){
				forma.setCalle("");
			}
			
			if(StringUtils.isBlank(forma.getNumExterior())){
				forma.setNumExterior("");
			}
			
			if(StringUtils.isBlank(forma.getNumInterior())){
				forma.setNumInterior("");
			}
			
			if(StringUtils.isBlank(forma.getReferencias())){
				forma.setReferencias("");
			}
			
			if(StringUtils.isBlank(forma.getEntreCalle())){
				forma.setEntreCalle("");
			}
			
			if(StringUtils.isBlank(forma.getYcalle())){
				forma.setYcalle("");
			}
			
			if(StringUtils.isBlank(forma.getAliasDomicilio())){
				forma.setAliasDomicilio("");
			}
			
			if(StringUtils.isBlank(forma.getEdificio())){
				forma.setEdificio("");
			}
			
			if(StringUtils.isBlank(forma.getLongitud())){
				forma.setLongitud("");
			}
			
			if(StringUtils.isBlank(forma.getLatitud())){
				forma.setLatitud("");
			}
			
			//Validaciones de la conclusion
			forma.setFechaConclusion(StringUtils.isNotBlank(forma.getFechaConclusion()) ? 
					forma.getFechaConclusion() : null);
			forma.setTipoConclusion(StringUtils.isNotBlank(forma.getTipoConclusion()) && 
					!forma.getTipoConclusion().equalsIgnoreCase("-1") ? 
							forma.getTipoConclusion() : null);
			forma.setTipoSubConclusion(StringUtils.isNotBlank(forma.getTipoSubConclusion()) && 
					!forma.getTipoSubConclusion().equalsIgnoreCase("-1") ? 
							forma.getTipoSubConclusion() : null);
						
			//FIN revisamos que los datos de domicilio no sea nula
					
			//encapsulamos la informacion del tiempo
			TiempoDTO tiempoDTO=new TiempoDTO();
			ValorDTO valorDTO=new ValorDTO();
			boolean conTiempo = false;
			//revisamos que los datos de tiempo no sea nula
			if(Integer.parseInt(forma.getTipoTiempoHecho())>0)
			{
				List<CatalogoDTO> listaCatalogo= catDelegate.recuperarCatalogo(Catalogos.TIPO_TIEMPO);
				//encapsulamos la informacion del tiempo
				if(Integer.parseInt(forma.getTipoTiempoHecho())==1 && !forma.getFecha().isEmpty())//especifico
				{
					valorDTO.setIdCampo(listaCatalogo.get(0).getClave());
					tiempoDTO.setFechaInicio(DateUtils.obtener(forma.getFecha(),forma.getHora()));
					tiempoDTO.setTipoRegistro(valorDTO);
					conTiempo=true;
				}
				else if(Integer.parseInt(forma.getTipoTiempoHecho())==2 && !forma.getFechaInicioLapso().isEmpty()
						&& !forma.getFechaFinLapso().isEmpty())//lapso
				{
					valorDTO.setIdCampo(listaCatalogo.get(2).getClave());
					tiempoDTO.setFechaInicio(DateUtils.obtener(forma.getFechaInicioLapso(),forma.getHoraInicioLapso()));
					tiempoDTO.setFechaFin((DateUtils.obtener(forma.getFechaFinLapso(),forma.getHoraFinLapso())));
					tiempoDTO.setTipoRegistro(valorDTO);
					conTiempo=true;
				}
				else if(Integer.parseInt(forma.getTipoTiempoHecho())==3)//descripcion hecho
				{
					valorDTO.setIdCampo(listaCatalogo.get(1).getClave());
					tiempoDTO.setDescripcion(forma.getGsNarrativa());
					tiempoDTO.setFechaInicio(DateUtils.obtener(forma.getFechaArribo(),forma.getHoraArribo()));
					tiempoDTO.setTipoRegistro(valorDTO);
					conTiempo=true;
				}				
			}
					
			//encapsulamos la informacion del expediente
			//ExpedienteDTO expedienteDTO=(ExpedienteDTO)request.getSession().getAttribute(forma.getNumExpediente());//new ExpedienteDTO(Long.parseLong(forma.getNumExpediente()));
			log.info("num_exp_hecho:: "+forma.getNumExpediente());
			ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request, forma.getNumExpediente());
			log.info("id_num_exp_hecho:: "+forma.getNumeroExpedienteId());
			log.info("num_exp_DTO:: "+ expedienteDTO);
			if(forma.getNumeroExpedienteId()!=null && !forma.getNumeroExpedienteId().equals("null") && !forma.getNumeroExpedienteId().equals(""))
			{
				expedienteDTO.setNumeroExpedienteId(Long.parseLong(forma.getNumeroExpedienteId()));
			}
			log.info("num_exp_id_hecho:: "+ expedienteDTO.getNumeroExpedienteId());
			
			//creamos el hecho a insertar
			HechoDTO hechoDTO=new HechoDTO();
			if(conTiempo){
				hechoDTO.setTiempo(tiempoDTO);
			}			
			hechoDTO.setExpediente(expedienteDTO);
			hechoDTO.setDescNarrativa(forma.getGcDescripcionHecho());
			UsuarioDTO usuarioDTO=new UsuarioDTO();
			usuarioDTO.setIdUsuario(Long.parseLong(forma.getIdUsuario()));
			hechoDTO.setUsuario(usuarioDTO);
			
			//Revisamos que la fecha de arribo no sea nula
			if(!forma.getFechaArribo().equalsIgnoreCase("") && !forma.getHoraArribo().equalsIgnoreCase("")){
				hechoDTO.setFechaDeArribo(DateUtils.obtener(forma.getFechaArribo(),forma.getHoraArribo()));
			}
			
			CalidadDTO calidadDTO=new CalidadDTO();
			calidadDTO.setCalidades(Calidades.LUGAR_HECHOS);
			//Encapsulamos la informacion del domicilio
			if(Long.parseLong(forma.getPais())==10)//Mexico
			{
				DomicilioDTO domicilioDTO=new DomicilioDTO();
				
				//domicilioDTO.setLatitud(forma.getLatitud());
				//domicilioDTO.setLongitud(forma.getLongitud());
				
				if (!(forma.getLatitudN()== null) && !forma.getLatitudN().equals("")) {
					String lat= forma.getLatitudN()+forma.getLatitudGrados()+"�"+forma.getLatitudMinutos()+"'"+forma.getLatitudSegundos()+"\"";
					log.info("lat_hechoDTO:: "+lat);
					domicilioDTO.setLatitud(lat);
				}
				if (!(forma.getLongitudE()== null) && !forma.getLongitudE().equals("")) {
					String longitud= forma.getLongitudE()+forma.getLongitudGrados()+"�"+forma.getLongitudMinutos()+"'"+forma.getLongitudSegundos()+"\"";
					log.info("lon_hechoDTO:: "+longitud);
					domicilioDTO.setLongitud(longitud);
				}
				
				domicilioDTO.setEdificio(forma.getEdificio());
				domicilioDTO.setAlias(forma.getAliasDomicilio());
				domicilioDTO.setEntreCalle2(forma.getYcalle());
				domicilioDTO.setEntreCalle1(forma.getEntreCalle());
				domicilioDTO.setReferencias(forma.getReferencias());
				domicilioDTO.setNumeroInterior(forma.getNumInterior());
				domicilioDTO.setNumeroExterior(forma.getNumExterior());
				domicilioDTO.setCalle(forma.getCalle());
				if(forma.getTipoCalle()!=null)
				{
					domicilioDTO.setValorCalleId(new ValorDTO(Long.parseLong(forma.getTipoCalle())));
				}
				domicilioDTO.setCalidadDTO(calidadDTO);
				domicilioDTO.setExpedienteDTO(expedienteDTO);
				domicilioDTO.setFechaCreacionElemento(new Date());
				//delcaramos el nuevo asentamiento
				AsentamientoDTO asentamientoDTO=new AsentamientoDTO();
				if(forma.getAsentamientoColonia()!=null)
				{
					log.info("ID_COLONIA::: "+forma.getAsentamientoColonia());
					asentamientoDTO.setAsentamientoId(Long.parseLong(forma.getAsentamientoColonia()));
				}
				asentamientoDTO.setCodigoPostal(forma.getCodigoPostal());
				
				//Declaramos el tipo de asentamiento
				if(forma.getTipoAsentamiento()!=null)
				{
					log.info("ID_TIPO_ASENTAMIENTO::: "+forma.getTipoAsentamiento());
					TipoAsentamientoDTO tipoAsentamientoDTO = new TipoAsentamientoDTO(Long.parseLong(forma.getTipoAsentamiento()),"");
					asentamientoDTO.setTipoAsentamientoDTO(tipoAsentamientoDTO);
				}
				//Declaramos el municipio
				MunicipioDTO municipioDTO=new MunicipioDTO();
				if(forma.getDelegacionMunicipio()!=null)
				{
					municipioDTO.setMunicipioId(Long.parseLong(forma.getDelegacionMunicipio()));
				}
				asentamientoDTO.setMunicipioDTO(municipioDTO);

				//declaramos la Ciudad
				CiudadDTO ciudadDTO = new CiudadDTO();
				if(forma.getCiudad()!=null)
				{
					ciudadDTO.setCiudadId(Long.parseLong(forma.getCiudad()));
				}
				//declaramos la entidad federativa
				EntidadFederativaDTO entidadFederativaDTO=new EntidadFederativaDTO();
				if(forma.getEntidadFederativa()!=null)
				{
					entidadFederativaDTO.setEntidadFederativaId(Long.parseLong(forma.getEntidadFederativa()));
				}
				if(forma.getPais()!=null)
				{
					entidadFederativaDTO.setValorIdPais(new ValorDTO(Long.parseLong(forma.getPais())));
				}
				ciudadDTO.setEntidadFederativaDTO(entidadFederativaDTO);
				asentamientoDTO.setCiudadDTO(ciudadDTO);
				domicilioDTO.setCiudadDTO(ciudadDTO);
				domicilioDTO.setEntidadDTO(entidadFederativaDTO);
				domicilioDTO.setAsentamientoDTO(asentamientoDTO);
				domicilioDTO.setMunicipioDTO(municipioDTO);
								
				//seteamos el domicilio al Hecho
				hechoDTO.setLugar(domicilioDTO);
			}
			else//Otro pais
			{
				DomicilioExtranjeroDTO domExtranjreoDTO= new DomicilioExtranjeroDTO();
				
				domExtranjreoDTO.setLatitud(forma.getLatitud());
				domExtranjreoDTO.setLongitud(forma.getLongitud());
				domExtranjreoDTO.setEdificio(forma.getEdificio());
				domExtranjreoDTO.setAlias(forma.getAliasDomicilio());
				domExtranjreoDTO.setEntreCalle2(forma.getYcalle());
				domExtranjreoDTO.setEntreCalle1(forma.getEntreCalle());
				domExtranjreoDTO.setReferencias(forma.getReferencias());
				domExtranjreoDTO.setNumeroInterior(forma.getNumInterior());
				domExtranjreoDTO.setNumeroExterior(forma.getNumExterior());
				domExtranjreoDTO.setCalle(forma.getCalle());
				domExtranjreoDTO.setPais(forma.getPais());
				domExtranjreoDTO.setCodigoPostal(forma.getCodigoPostal());
				domExtranjreoDTO.setCiudad(forma.getCiudad());
				domExtranjreoDTO.setMunicipio(forma.getDelegacionMunicipio());
				domExtranjreoDTO.setAsentamientoExt(forma.getAsentamientoColonia());
				domExtranjreoDTO.setEstado(forma.getEntidadFederativa());
				domExtranjreoDTO.setCalidadDTO(calidadDTO);
				domExtranjreoDTO.setExpedienteDTO(expedienteDTO);
				domExtranjreoDTO.setFechaCreacionElemento(new Date());
				//private String tipoAsentamiento; SE VA EN DOMICILIO EXTRANJERO
				//private String tipoCalle; SE VA EN DOMICILIO EXTRANJERO
								
				//seteamos el domicilio extranjero al Hecho
				hechoDTO.setLugar(domExtranjreoDTO);
			}
			
			//cambiamos el estatus del Expediente
			if(Long.parseLong(forma.getOrigenExpediente())==0)
			{
				//Denuncia
				expedienteDelegate.actualizarTipoExpediente(expedienteDTO, OrigenExpediente.DENUNCIA);
			}
			else
			{
				if(Long.parseLong(forma.getOrigenExpediente())==1){
					//Querella
					expedienteDelegate.actualizarTipoExpediente(expedienteDTO, OrigenExpediente.QUERELLA);
				}else
					expedienteDelegate.actualizarTipoExpediente(expedienteDTO, OrigenExpediente.REPORTE);

			}
			log.info("$$$$$ Ingreso_Hecho - MODIFIQUE EL ORIGEN DEL EXPEDIENTE :::::: "+expedienteDTO.getNumeroExpedienteId());
			log.info("idLugar_hechoDTO:: "+request.getParameter("idLugar"));
			log.info("idTiempo_hechoDTO:: "+request.getParameter("idTiempo"));
			//hacemos la insercion del hecho
			if(Long.parseLong(forma.getIdHecho())==0)
			{
				//esta es una inserci�n de Hecho
				hechoDTO= hechoDelegate.ingresarHecho(hechoDTO);
			}
			else
			{
				//esta es una modificacion de un hecho
				hechoDTO.setHechoId(Long.parseLong(forma.getIdHecho()));
				if (request.getParameter("idLugar")!=null && !request.getParameter("idLugar").isEmpty()) {
					hechoDTO.getLugar().setElementoId(Long.parseLong(request.getParameter("idLugar")));
				} else {
					hechoDTO.getLugar().setElementoId(null);
				}
				
				if (request.getParameter("idTiempo")!=null && !request.getParameter("idTiempo").isEmpty()) {
					hechoDTO.getTiempo().setTiempoId(Long.parseLong(request.getParameter("idTiempo")));
				} else if (conTiempo){
					hechoDTO.getTiempo().setTiempoId(null);
				}
				
				//FIXME aqui abajo iria el llamado al servicio de actualizacion del Hecho
				hechoDTO=hechoDelegate.modificarHecho(hechoDTO);
			}
			
			if(hechoDTO!=null && hechoDTO.getHechoId()!=null)
			{
				
				//Si todo es correcto se inserta o modifica la conclusion del hecho
				if(forma.getFechaConclusion() != null || 
						forma.getTipoConclusion() != null || 
						forma.getTipoSubConclusion() != null){
					
					Date fechaConclusion = null;
					
					if(forma.getFechaConclusion() != null) 
						fechaConclusion = DateUtils.obtener(forma.getFechaConclusion()); 
					
					ConclusionHechoDTO dto = new ConclusionHechoDTO(hechoDTO, 
							fechaConclusion, 
							forma.getTipoConclusion() != null ? new ValorDTO(Long.parseLong(forma.getTipoConclusion())) : null, 
							forma.getTipoSubConclusion() != null ? new ValorDTO(Long.parseLong(forma.getTipoSubConclusion())) : null);
					
					conclusionHechoDelegate.ingresarModificarConclusionHecho(dto);
					
				}
				
				XStream converter=new XStream();
				converter.alias("hechoDTO", HechoDTO.class);
				String xml = converter.toXML(hechoDTO);
				log.info("hechoDTO:: "+xml);
				escribirRespuesta(response, xml);
			}
			else
			{
				hechoDTO.setHechoId(0L);
				XStream converter=new XStream(); 			converter.alias("hechoDTO", HechoDTO.class);
				String xml = converter.toXML(hechoDTO);
				escribirRespuesta(response, xml);
			}
			log.info("Termina ejecucion Action guardar hecho - FIN ");
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
			escribir(response, "", e);
		}
		return null;
	}
	
	
	/**
	 * Metodo para actualizar un hecho con base a su Id
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward modificarHecho(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {

			/**
			 * Por el momento solo se va a modificar la narrativa o descripcion
			 */

			log.info("EJECUTANDO ACTION MODIFICAR HECHO");
			HechoForm forma = (HechoForm) form;

			log.info("************VERIFICANDO PARAMETROS****************");
			log.info("HECHO ID:::::::::::::::::::::::" + forma.getIdHecho());
			log.info("HECHO DESCRIPCION::::::::::::::" + forma.getGcDescripcionHecho());
			log.info("HECHO EXPEDIENTE_ID::::::::::::" + forma.getExpedienteId());

			// Revisamos que el id del hecho no sea nulo
			Long hechoId = NumberUtils.toLong(forma.getIdHecho(), 0L);
			Long expedienteId = NumberUtils.toLong(forma.getExpedienteId(), 0L);
			
			HechoDTO hechoDTO = new HechoDTO();

			if (hechoId != null && hechoId > 0L && expedienteId != null && expedienteId > 0L) {
				
				ExpedienteDTO expedienteDto = new ExpedienteDTO();
				expedienteDto.setExpedienteId(expedienteId);
				
				hechoDTO.setHechoId(hechoId);
				hechoDTO.setExpediente(expedienteDto);
				
				
				if(forma.getGcDescripcionHecho() != null && !forma.getGcDescripcionHecho().trim().isEmpty()){
					hechoDTO.setDescNarrativa(forma.getGcDescripcionHecho());
				}
				
				hechoDTO = hechoDelegate.modificarHecho(hechoDTO);
			}

			if (hechoDTO != null && hechoDTO.getHechoId() != null) {
				XStream converter=new XStream();
				converter.alias("hechoDTO", HechoDTO.class);
				String xml = converter.toXML(hechoDTO);
				log.info("hechoDTO:: " + xml);
				escribirRespuesta(response, xml);
			}

		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
			escribir(response, "", e);
		}
		return null;
	}
	
	/**
	 * Metodo para consultar un hecho con base a su Id
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarHecho(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {

			log.info("EJECUTANDO ACTION CONSULTAR HECHO");
			
			log.info("EJECUTANDO ACTION MODIFICAR HECHO");
			HechoForm forma = (HechoForm) form;
			UsuarioDTO usuario = super.getUsuarioFirmado(request);
			
			log.info("************VERIFICANDO PARAMETROS****************");
			log.info("HECHO EXPEDIENTE_ID::::::::::::" + forma.getExpedienteId());

			Long expedienteId = NumberUtils.toLong(forma.getExpedienteId(), 0L);
			
			HechoDTO hechoDTO = new HechoDTO();
			List<HechoDTO> listaHechos = null;

			if (expedienteId != null && expedienteId > 0L) {
				
				ExpedienteDTO expedienteDto = new ExpedienteDTO();
				expedienteDto.setExpedienteId(expedienteId);
				hechoDTO.setExpediente(expedienteDto);
				
				listaHechos = hechoDelegate.consultarHechos(hechoDTO);
			}

			if (listaHechos != null && listaHechos.size() > 0 && listaHechos.get(0) != null) {
				listaHechos.get(0).setUsuario(usuario);
				XStream converter=new XStream();
				converter.alias("usuarioDTO", UsuarioDTO.class);
				converter.alias("hechoDTO", HechoDTO.class);
				String xml = converter.toXML(listaHechos.get(0));
				log.info("hechoDTO:: " + xml);
				escribirRespuesta(response, xml);
			}
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
			escribir(response, "", e);
		}
		return null;
	}
	
	/**
	 * M�todo utilizado para realizar la carga del combo Tipo Conclusion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarTipoConclusion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Tipo Conclusion
			log.debug("ejecutando Action cargarCatalogos");
			List<CatalogoDTO> listaCatalogo = service.recuperarCatalogo(Catalogos.TIPO_CONCLUSION);
			
			XStream converter=new XStream();
			converter.alias("listaCatalogo", java.util.List.class);
			converter.alias("catTipoConclusion", CatalogoDTO.class);
			String xml = converter.toXML(listaCatalogo);
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
	 * M�todo utilizado para realizar la carga del combo Tipo SubConclusion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarTipoSubConclusion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Tipo SubConclusion
			Long tipoConclusion = Long.parseLong(request.getParameter("tipoConclusion"));	
			log.debug("ejecutando Action cargarCatalogos");
			List<CatalogoDTO> listaCatalogo = service.recuperarCatalogoDependiente(Catalogos.TIPO_SUBCONCLUSION, tipoConclusion);
			
			XStream converter=new XStream();
			converter.alias("listaCatalogo", java.util.List.class);
			converter.alias("catTipoSubConclusion", CatalogoDTO.class);
			String xml = converter.toXML(listaCatalogo);
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
	 * M�todo utilizado para realizar la carga del combo Tipo SubConclusion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward guardarConclusionNumeroExpe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Tipo SubConclusion
			Long tipoConclusion = Long.parseLong(request.getParameter("tipoConclusion"));
			Long subTipoConclusion = Long.parseLong(request.getParameter("subTipoConclusion"));
			if(subTipoConclusion.equals(-1L)){
				subTipoConclusion=null;
			}
			
			String fecha = request.getParameter("fechaConclusion");
			Long idNumeroExpedinte = Long.parseLong(request.getParameter("idNumeroExpediente"));
			ConclusionNumeroExpedienteDTO conclusion=new ConclusionNumeroExpedienteDTO();
			if(fecha !=null&& fecha !=""){
				conclusion.setFechaConclusion(DateUtils.obtener(fecha));
			}
			conclusion.setNumeroExpediente(idNumeroExpedinte);
			conclusion.setTipoConclusion(new ValorDTO(tipoConclusion));
			conclusion.setTipoSubConclusion(new ValorDTO(subTipoConclusion));
			Boolean guardado=Boolean.FALSE;
			if(!tipoConclusion.equals(-1L)){
				 guardado=conclusionHechoDelegate.guardarConclusion(conclusion);
			}
			XStream converter=new XStream();
			converter.alias("guardado", Boolean.class);
			String xml = converter.toXML(guardado);
			log.info("guardado:: "+xml);
			escribirRespuesta(response, xml);
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
			escribir(response, "", e);
		}
		return null;
	}
	
	public ActionForward consultarConclusionNumeroExpe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Tipo SubConclusion
			
			Long idNumeroExpedinte = Long.parseLong(request.getParameter("idNumeroExpediente"));
			ConclusionNumeroExpedienteDTO conclusion=null;
			if(idNumeroExpedinte!=null){
				 conclusion=conclusionHechoDelegate.consultarConclusionNumeroExpe(idNumeroExpedinte);
			}
			XStream converter=new XStream();
			converter.alias("conclusionNumeroExpedienteDTO", ConclusionNumeroExpedienteDTO.class);
			String xml = converter.toXML(conclusion);
			log.info("guardado:: "+xml);
			escribirRespuesta(response, xml);
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
			escribir(response, "", e);
		}
		return null;
	}
	
}
