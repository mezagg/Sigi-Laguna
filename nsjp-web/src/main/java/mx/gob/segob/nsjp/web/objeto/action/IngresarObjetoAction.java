/**
 * Nombre del Programa 		: IngresarObjetoAction.java
 * Autor                     : AlejandroGA
 * Compania                  : Ultrasist
 * Proyecto                  : NSJP                    Fecha: 2 May 2011
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
package mx.gob.segob.nsjp.web.objeto.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.enums.objeto.Objetos;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.catalogo.CatalogoDelegate;
import mx.gob.segob.nsjp.delegate.elemento.ElementoDelegate;
import mx.gob.segob.nsjp.delegate.objeto.ObjetoDelegate;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.cadenadecustoria.CadenaDeCustodiaDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.delito.CausaDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.elemento.ElementoDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.objeto.AeronaveDTO;
import mx.gob.segob.nsjp.dto.objeto.AnimalDTO;
import mx.gob.segob.nsjp.dto.objeto.ArmaDTO;
import mx.gob.segob.nsjp.dto.objeto.DocumentoOficialDTO;
import mx.gob.segob.nsjp.dto.objeto.EmbarcacionDTO;
import mx.gob.segob.nsjp.dto.objeto.EquipoComputoDTO;
import mx.gob.segob.nsjp.dto.objeto.ExplosivoDTO;
import mx.gob.segob.nsjp.dto.objeto.JoyaDTO;
import mx.gob.segob.nsjp.dto.objeto.NumerarioDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoEstudioDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoPericialDTO;
import mx.gob.segob.nsjp.dto.objeto.ObraArteDTO;
import mx.gob.segob.nsjp.dto.objeto.SustanciaDTO;
import mx.gob.segob.nsjp.dto.objeto.TelefoniaDTO;
import mx.gob.segob.nsjp.dto.objeto.VegetalDTO;
import mx.gob.segob.nsjp.dto.objeto.VehiculoDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.objeto.form.AeronaveForm;
import mx.gob.segob.nsjp.web.objeto.form.AnimalForm;
import mx.gob.segob.nsjp.web.objeto.form.ArmaForm;
import mx.gob.segob.nsjp.web.objeto.form.DocumentoOficialForm;
import mx.gob.segob.nsjp.web.objeto.form.EmbarcacionForm;
import mx.gob.segob.nsjp.web.objeto.form.EquipoDeComputoForm;
import mx.gob.segob.nsjp.web.objeto.form.EquipoTelefonicoForm;
import mx.gob.segob.nsjp.web.objeto.form.ExplosivoForm;
import mx.gob.segob.nsjp.web.objeto.form.JoyaForm;
import mx.gob.segob.nsjp.web.objeto.form.NumerarioForm;
import mx.gob.segob.nsjp.web.objeto.form.ObjetoPericialForm;
import mx.gob.segob.nsjp.web.objeto.form.ObraDeArteForm;
import mx.gob.segob.nsjp.web.objeto.form.OtrosForm;
import mx.gob.segob.nsjp.web.objeto.form.SustanciaForm;
import mx.gob.segob.nsjp.web.objeto.form.VegetalForm;
import mx.gob.segob.nsjp.web.objeto.form.VehiculoForm;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.beans.factory.annotation.Autowired;

import sun.util.calendar.CalendarUtils;

/**
 * Clase Action para ingresar objetos.
 * 
 * @version 1.0
 * @author AlejandroGA
 * 
 */
public class IngresarObjetoAction extends GenericAction {
	
	private static final String PARAM_TIPO_OBJETO = "tipoObjetoId";

	
	@Autowired
	public ElementoDelegate elementoDelegate;
	
	/* Log de clase */
	private static final Logger log = Logger
			.getLogger(IngresarObjetoAction.class);

	@Autowired
	private ObjetoDelegate objetoDelegate;
	
	@Autowired
	private CatalogoDelegate catDelegate;
	

	/**
	 * Metodo utilizado para guardar de un vehiculo
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarVehiculo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			VehiculoForm retorno = new VehiculoForm();
			log.info("ejecutando Action guaradar vehiculo");
			VehiculoForm forma = (VehiculoForm) form;
			String numeroExpediente=request.getParameter("numeroExpediente");
			ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
			log.info("FORMA VEHICULO:::::::::::::::::::::::");
			log.info("idVehiculo="+ forma.getIdVehiculo());
			log.info("Tipo="+ forma.getGlTipoVehiculoId());	
			log.info("Marca="+ forma.getGlMarcaVehiculoId());			
			log.info("Sub Marca="+ forma.getGlSubMarcaVehiculoId());
			log.info("Color="+ forma.getGlColorVehiculoId());	
			log.info("Pais="+forma.getGlIdPaisOrigenId());
			log.info("Modelo="+forma.getGsModelo());
			log.info("Placas="+forma.getGcNoPlaca());
			log.info("Motor="+forma.getGcNoMotor());
			log.info("No.Serie="+forma.getGcNoSerie());
			log.info("RFV="+forma.getGcNoRegFed());
			log.info("Puertas="+forma.getGsNoPuertas());
			log.info("Cilindros="+forma.getGsNoCilindros());
			log.info("Blindado="+forma.getGbEsBlindado());
			log.info("Descripcion="+forma.getGsDescripcion());
			log.info("Imagen="+forma.getArchivo());
			log.info("Propietario="+forma.getPropietario());
			log.info("CausaId="+forma.getCausaVehiculoId());
			log.info("CausaRecuperadoId="+forma.getCausaVehiculoRecuperadoId());
			log.info("CausaRecuperadoOtrosId="+forma.getCausaVehiculoRecuperadoOtrosId());
			log.info("RelacionHechoId="+forma.getRelacionHechoId());
			log.info("Fecha de recuperacion="+forma.getFechaRecuperado());
			log.info("Lugar de recuperacion="+forma.getLugarRecuperacion());
			log.info("autoridad que recupero="+forma.getAutoridadRecupero());
			log.info("Fecha de entrega="+forma.getFechaEntrega());
			log.info("No.MotorAleterado="+forma.getGbEsNumMotorAlterado());
			log.info("No.SerieAlterado="+forma.getGbEsNumSerieAlterado());
			

			
			if(StringUtils.isNotEmpty(forma.getPropietario()))
			{
				log.info("PROPIETARIO="+forma.getPropietario());
			}
			if(forma.getCausaVehiculoId()<= 0L){
				forma.setCausaVehiculoId(null);
				forma.setCausaVehiculoRecuperadoId(null);
				forma.setCausaVehiculoRecuperadoOtrosId(null);
				log.info("CausaId, es: " + forma.getCausaVehiculoId());
				log.info("CausaRecuperadoId, es: " + forma.getCausaVehiculoRecuperadoId());
				log.info("CausaRecuperadoOtrosId, es: " + forma.getCausaVehiculoRecuperadoOtrosId());
			}
			
			if(forma.getRelacionHechoId()<= 0L){
				forma.setRelacionHechoId(null);
				forma.setFechaEntrega(null);
				forma.setFechaRecuperado(null);
				forma.setLugarRecuperacion(null);
				forma.setAutoridadRecupero(null);
				log.info("RELACION ID, ES:"+forma.getRelacionHechoId());
				log.info("FECHA DE RECUPERACION, ES:"+forma.getFechaRecuperado());
				log.info("LUGAR DE RECUPERACION, ES:"+forma.getLugarRecuperacion());
				log.info("AUTORIDAD QUE RECUPERO, ES:"+forma.getAutoridadRecupero());
				log.info("FECHA DE ENTREGA, ES:"+forma.getFechaEntrega());
				
			}
			
				
			if(StringUtils.isNotEmpty(forma.getCadenaCustodia()))
			{
				log.info("cadenaCustodia="+forma.getCadenaCustodia());
			}
			if(forma.getGlTipoVehiculoId()<= 0L){
				forma.setGlTipoVehiculoId(null);
				log.info("TIPO, ES:"+forma.getGlTipoVehiculoId());
			}
			
			if(forma.getGlMarcaVehiculoId() <= 0L){
				forma.setGlMarcaVehiculoId(null);
				log.info("MARCA, ES:"+forma.getGlMarcaVehiculoId());
			}
				
			if(forma.getGlSubMarcaVehiculoId() <= 0L){
				forma.setGlSubMarcaVehiculoId(null);
				log.info("SUB MARCA, ES:"+forma.getGlSubMarcaVehiculoId());
			}
			
			if(forma.getGlColorVehiculoId() <= 0L){
				forma.setGlColorVehiculoId(null);
				log.info("CALIBRE, ES:" + forma.getGlColorVehiculoId());
			}
			
			if(forma.getGlIdPaisOrigenId() <= 0L){
				forma.setGlIdPaisOrigenId(null);
				log.info("PAIS, ES:"+forma.getGlIdPaisOrigenId());
			}
			
			if(forma.getGsModelo() <= 0L){
				forma.setGsModelo(null);
				log.info("MODELO, ES:"+forma.getGsModelo());
			}
			
			if(forma.getGcNoPlaca().equalsIgnoreCase("") ){
				forma.setGcNoPlaca(null);
				log.info("PLACAS, ES:"+forma.getGcNoPlaca());
			}
			
			if(forma.getGcNoMotor().equalsIgnoreCase("") ){
				forma.setGcNoMotor(null);
				log.info("MOTOR, ES:"+forma.getGcNoMotor());
			}
			
			if(forma.getGcNoSerie().equalsIgnoreCase("") ){
				forma.setGcNoSerie(null);
				log.info("SERIE, ES:"+forma.getGcNoSerie());
			}
			
			if(forma.getGcNoRegFed().equalsIgnoreCase("") ){
				forma.setGcNoRegFed(null);
				log.info("RFV, ES:"+forma.getGcNoRegFed());
			}
			
			if(forma.getGsNoPuertas() <= 0L){
				forma.setGsNoPuertas(null);
				log.info("PUERTAS, ES:"+forma.getGsNoPuertas());
			}
			
			if(forma.getGsNoCilindros() <= 0L){
				forma.setGsNoCilindros(null);
				log.info("CILINDROS, ES:"+forma.getGsNoCilindros());
			}
			
			if(forma.getGlCondicionFisica() <= 0L){
				forma.setGlCondicionFisica(null);
				log.info("CONDICION, ES:"+forma.getGlCondicionFisica());
			}
			
			
			ValorDTO valorDTOPais = new ValorDTO(forma.getGlIdPaisOrigenId());
			ValorDTO valorDTOColor = new ValorDTO(forma.getGlColorVehiculoId());
			ValorDTO valorDTOMarca = new ValorDTO(forma.getGlMarcaVehiculoId());
			ValorDTO valorDTOSubMarca = new ValorDTO(
					forma.getGlSubMarcaVehiculoId());
			ValorDTO valorDTOTipo = new ValorDTO(forma.getGlTipoVehiculoId());
			ValorDTO valorDTOCondicion = new ValorDTO(forma.getGlCondicionFisica());
			
			VehiculoDTO vehiculoDTO = new VehiculoDTO(valorDTOPais,
					valorDTOColor, valorDTOMarca, valorDTOSubMarca,
					valorDTOTipo, forma.getGsModelo(), forma.getGcNoPlaca(),
					forma.getGcNoSerie(), forma.getGcNoMotor(),
					forma.getGcNoRegFed(), forma.getGbEsBlindado(),
					forma.getGsNoPuertas(), forma.getGsNoCilindros());
			
			vehiculoDTO.setEsNumMotorAlterado(forma.getGbEsNumMotorAlterado());
			vehiculoDTO.setEsNumSerieAlterado(forma.getGbEsNumSerieAlterado());
			//Se configura el propietario y el la relacionHecho
			vehiculoDTO.setPropietario(forma.getPropietario());
			
			// se valida causa para setear en el dto a guardar			
			if(forma.getCausaVehiculoId() != null){
				vehiculoDTO.setCausa(new CausaDTO(forma.getCausaVehiculoId()));
				if(forma.getCausaVehiculoId().compareTo(ConstantesGenerales.VEHICULO_CAUSA_RECUPERADO_ID)== 0L && 
						forma.getCausaVehiculoRecuperadoId() > 0L){
					vehiculoDTO.setCausaRecuperado(new CausaDTO(forma.getCausaVehiculoRecuperadoId()));
					if(forma.getCausaVehiculoRecuperadoId().compareTo(ConstantesGenerales.VEHICULO_CAUSA_RECUPERADO_OTROS_ID)== 0L
							&& forma.getCausaVehiculoRecuperadoOtrosId()> 0L){
						vehiculoDTO.setCausaRecuperadoOtros(new CausaDTO(forma.getCausaVehiculoRecuperadoOtrosId()));
					}
				}
			}
			
			ValorDTO valorDTORelacionHechos = new ValorDTO(forma.getRelacionHechoId());
			vehiculoDTO.setRelacionHechoVal(valorDTORelacionHechos);
			
			if(forma.getRelacionHechoId() != null){
				if(forma.getRelacionHechoId().compareTo(ConstantesGenerales.VEHICULO_RELACION_HECHOS_RECUPERADO_ID) == 0L){
					vehiculoDTO.setFechaRecuperado(DateUtils.obtener(forma.getFechaRecuperado()));
					vehiculoDTO.setLugarRecuperacion(forma.getLugarRecuperacion());
					vehiculoDTO.setAutoridadRecupero(forma.getAutoridadRecupero());
					vehiculoDTO.setFechaEntrega(null);
				}else if(forma.getRelacionHechoId().compareTo(ConstantesGenerales.VEHICULO_RELACION_HECHOS_ENTREGADO_ID) == 0L){
					vehiculoDTO.setFechaEntrega(DateUtils.obtener(forma.getFechaEntrega()));
					vehiculoDTO.setFechaRecuperado(null);
					vehiculoDTO.setLugarRecuperacion(null);
					vehiculoDTO.setAutoridadRecupero(null);
				}
			}
			
			vehiculoDTO.setValorByCondicionFisicaVal(valorDTOCondicion);
			vehiculoDTO.setDescripcion(forma.getGsDescripcion());		
			
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			//calidadDTO.setDescripcionEstadoFisico(forma.getGsDescripcion());

			
			vehiculoDTO.setCalidadDTO(calidadDTO);
			vehiculoDTO.setExpedienteDTO(expedienteDTO);
			//agregamos el id del vehiculo
			vehiculoDTO.setElementoId(forma.getIdVehiculo());
			
			retorno.setGcNoPlaca(forma.getGcNoPlaca());
			
			//ACT-20110726 Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(forma.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				vehiculoDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(forma.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=forma.getFechaLevCadCus().split("-");log.info("fecha_jo_jo:: "+DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(forma.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);			    
			    
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al vehiculo la cadena de custodia
				vehiculoDTO.setCadenaDeCustodia(cadenaDTO);
				log.debug("Cadena_custodia , seteo Cadena de custodia a vehiculo - "+forma.getCadenaCustodia());
			}
			
			//Permite guardar la foto de un elemento
			if(forma.getArchivo() != null){
				
				ArchivoDigitalDTO adjunto = new ArchivoDigitalDTO();

				FormFile archivo = forma.getArchivo();
		        String contentType = archivo.getContentType();
		        String fileName    = archivo.getFileName();
		        log.debug(" EL ADJUNTO GAMASOFT: El nombre del archivo es" + archivo.getFileName());
		        byte[] fileData    = archivo.getFileData();
				adjunto.setContenido(fileData);
				adjunto.setNombreArchivo(fileName);
				adjunto.setTipoArchivo(contentType);
				adjunto.setUsuario(super.getUsuarioFirmado(request));
				vehiculoDTO.setFotoDelElemento(adjunto);
			}else
		        log.debug("EL ADJUNTO ES NULO, GAMASOFT ");
			
			log.debug("EL ADJUNTO ES NULO, GAMASOFT :"+forma.getAnular());
			
			if(forma.getAnular()!=null){
				
				vehiculoDTO.setEsActivo(!forma.getAnular());
			}
			log.debug("EL ADJUNTO ES NULO, GAMASOFT :"+vehiculoDTO);
			Long resp = objetoDelegate.ingresarVehiculo(vehiculoDTO);	
			String xml = null;
			//PrintWriter pw = null;
			XStream converter=new XStream();
			converter.alias("VehiculoForm",VehiculoForm.class);
			retorno.setGlTipoVehiculoId(resp);

			xml = converter.toXML(retorno);
			
			escribirRespuesta(response, xml);
//			response.setContentType("text/xml");
//			pw = response.getWriter();
//			pw.print(xml);
//			pw.flush();
//			pw.close();
			
		} catch (Exception e) {
			VehiculoForm retorno = new VehiculoForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("VehiculoForm",VehiculoForm.class);
			retorno.setGlTipoVehiculoId(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para guardar un equipo de computo
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarEquipoComputo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			EquipoDeComputoForm retorno = new EquipoDeComputoForm();
			log.info("ejecutando action guardar equipo de computo");
			EquipoDeComputoForm forma = (EquipoDeComputoForm) form;
			String numeroExpediente=request.getParameter("numeroExpediente");
			ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
			
			log.info("FORMA EQUIPO COMPUTO:::::::::::::::::::::::");
			log.info("idEquipoComputo="+ forma.getIdEquipoComputo());
			log.info("Tipo="+ forma.getGlTipoEquipoComputoId());	
			log.info("Marca="+ forma.getGlMarcaEquipoComputoId());		
			log.info("Color="+ forma.getGlColorEquipoComputoId());	
			log.info("Modelo="+ forma.getGsModeloEquipoComputo());
			log.info("No Serie="+ forma.getGsNoSerieEquipoComputo());	
			log.info("Condicion="+forma.getGlCondicionFisicaEquipoComputoId());
			log.info("descripcion"+forma.getGsDescripcionEquipoComputo());
			log.info("RelacionHechoId="+forma.getRelacionHechoId());
			
			if(forma.getRelacionHechoId()<= 0L){
				forma.setRelacionHechoId(null);
				log.info("RELACION ID, ES:"+forma.getRelacionHechoId());
			}
			
			if(forma.getGlTipoEquipoComputoId() <= 0L){
				forma.setGlTipoEquipoComputoId(null);
				log.info("TIPO EQUIPO, ES:"+forma.getGlTipoEquipoComputoId());
			}
			
			if(forma.getGlMarcaEquipoComputoId() <= 0L){
				forma.setGlMarcaEquipoComputoId(null);
				log.info("MARCA EQUIPO, ES:"+forma.getGlMarcaEquipoComputoId());
			}
			
			if(forma.getGlColorEquipoComputoId() <= 0L){
				forma.setGlColorEquipoComputoId(null);
				log.info("COLOR EQUIPO, ES:"+forma.getGlColorEquipoComputoId());
			}
			
			if(forma.getGsModeloEquipoComputo().equalsIgnoreCase("")){
				forma.setGsModeloEquipoComputo(null);
				log.info("MODELO ES"+forma.getGsModeloEquipoComputo());
			}
			
			if(forma.getGsNoSerieEquipoComputo().equalsIgnoreCase("")){
				forma.setGsNoSerieEquipoComputo(null);
				log.info("NO. DE SERIE ES" + forma.getGsNoSerieEquipoComputo());
			}
			
			if(forma.getGlCondicionFisicaEquipoComputoId() <= 0L){
				forma.setGlCondicionFisicaEquipoComputoId(null);
				log.info("CONDICION FISICA EQUIPO, ES:"+forma.getGlCondicionFisicaEquipoComputoId());
			}
			
			
			
			ValorDTO valorDTOColor = new ValorDTO(forma.getGlColorEquipoComputoId());
			ValorDTO valorDTOMarca = new ValorDTO(forma.getGlMarcaEquipoComputoId());
			ValorDTO valorDTOTipo = new ValorDTO(forma.getGlTipoEquipoComputoId());
			ValorDTO valorDTOCondicion = new ValorDTO(forma.getGlCondicionFisicaEquipoComputoId());
			
			EquipoComputoDTO equipoComputoDTO = new EquipoComputoDTO();
			equipoComputoDTO.setElementoId(forma.getIdEquipoComputo());
			equipoComputoDTO.setColor(valorDTOColor);
			equipoComputoDTO.setMarca(valorDTOMarca);
			equipoComputoDTO.setTipoEquipo(valorDTOTipo);
			equipoComputoDTO.setNoSerie(forma.getGsNoSerieEquipoComputo());
			equipoComputoDTO.setModelo(forma.getGsModeloEquipoComputo());
			equipoComputoDTO.setValorByCondicionFisicaVal(valorDTOCondicion);
			equipoComputoDTO.setDescripcion(forma.getGsDescripcionEquipoComputo());
			
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			//calidadDTO.setDescripcionEstadoFisico(forma.getGsDescripcionEquipoComputo());		
			
			equipoComputoDTO.setCalidadDTO(calidadDTO);
			equipoComputoDTO.setExpedienteDTO(expedienteDTO);
			
			ValorDTO valorDTORelacionHechos = new ValorDTO(forma.getRelacionHechoId());
			equipoComputoDTO.setRelacionHechoVal(valorDTORelacionHechos);
			
			//ACT-20110726 Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(forma.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				equipoComputoDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(forma.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=forma.getFechaLevCadCus().split("-");
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(forma.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);	
				
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al vehiculo la cadena de custodia
				equipoComputoDTO.setCadenaDeCustodia(cadenaDTO);
				log.debug("Cadena_custodia , seteo Cadena de custodia a vehiculo - "+forma.getCadenaCustodia());
			}
			if(forma.getAnular()!=null){
				
				equipoComputoDTO.setEsActivo(!forma.getAnular());
			}
			Long resp = objetoDelegate.ingresarEquipoDeComputo(equipoComputoDTO);
			log.info("OBJETO DELEGATE::::::::"+objetoDelegate);

			
			String xml = null;
			//PrintWriter pw = null;
			XStream converter=new XStream(); 			converter.alias("EquipoDeComputoForm",EquipoDeComputoForm.class);
			retorno.setGlTipoEquipoComputoId(resp);
			
			xml = converter.toXML(retorno);
			
			escribirRespuesta(response, xml);
			if(log.isDebugEnabled())
			{
				log.info(xml);
			}
//			response.setContentType("text/xml");
//			pw = response.getWriter();
//			pw.print(xml);
//			pw.flush();
//			pw.close();
			
		} catch (Exception e) {
			EquipoDeComputoForm retorno = new EquipoDeComputoForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("EquipoDeComputoForm",EquipoDeComputoForm.class);
			retorno.setGlTipoEquipoComputoId(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para guardar un equipo telefonico
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarEquipoTel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			EquipoTelefonicoForm retorno = new EquipoTelefonicoForm();
			log.info("ejecutando action guardar equipo TELEFONICO");
			 
			 EquipoTelefonicoForm forma = (EquipoTelefonicoForm) form;
			 
				log.info("FORMA EQUIPO TELEFONICO:::::::::::::::::::::::");
				log.info("idEquipoTelefonico="+ forma.getIdEquipoTelefonico());
				log.info("Tipo="+ forma.getGlTipoEquipoTelefId());	
				log.info("Marca="+ forma.getGlMarcaEquipoTelefId());			
				log.info("Modelo="+ forma.getGsModeloEquipoTelef());
				log.info("Cantidad="+ forma.getGsCantidadEquipoTelef());	
				log.info("Condicion="+forma.getGlCondicionFisicaEquipoTelefId());
				log.info("descripcion="+forma.getGsDescripcionEquipoTelef());
				log.info("RelacionHechoId="+forma.getRelacionHechoId());
				
				if(forma.getRelacionHechoId()<= 0L){
					forma.setRelacionHechoId(null);
					log.info("RELACION ID, ES:"+forma.getRelacionHechoId());
				}
				
				String numeroExpediente=request.getParameter("numeroExpediente");
				ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
				if(forma.getGlTipoEquipoTelefId() <= 0L){
					forma.setGlTipoEquipoTelefId(null);
					log.info("TIPO EQUIPO, ES:"+forma.getGlTipoEquipoTelefId());
				}
				
				if(forma.getGlMarcaEquipoTelefId() <= 0L){
					forma.setGlMarcaEquipoTelefId(null);
					log.info("MARCA EQUIPO, ES:"+forma.getGlMarcaEquipoTelefId());
				}
					
				if(forma.getGsModeloEquipoTelef().equalsIgnoreCase("")){
					forma.setGsModeloEquipoTelef(null);
					log.info("MODELO, ES:"+forma.getGsModeloEquipoTelef());
				}
				
				if(forma.getGsCantidadEquipoTelef() <= 0L){
					forma.setGsCantidadEquipoTelef(null);
					log.info("NO. DE SERIE, ES:" + forma.getGsCantidadEquipoTelef());
				}
				
				if(forma.getGlCondicionFisicaEquipoTelefId() <= 0L){
					forma.setGlCondicionFisicaEquipoTelefId(null);
					log.info("CONDICION FISICA EQUIPO, ES:"+forma.getGlCondicionFisicaEquipoTelefId());
				}
				
				
			//log.info("expedienteDTO.getExpedienteId() = " + expedienteDTO.getExpedienteId());	
			ValorDTO valorDTOMarca = new ValorDTO(forma.getGlMarcaEquipoTelefId());
			ValorDTO valorDTOTipo = new ValorDTO(forma.getGlTipoEquipoTelefId());
			ValorDTO valorDTOCondicion = new ValorDTO(forma.getGlCondicionFisicaEquipoTelefId());
			
			TelefoniaDTO equipoTelefonicoDTO = new TelefoniaDTO();
			
			equipoTelefonicoDTO.setElementoId(forma.getIdEquipoTelefonico());
			equipoTelefonicoDTO.setMarca(valorDTOMarca);
			equipoTelefonicoDTO.setTipoTelefono(valorDTOTipo);
			equipoTelefonicoDTO.setValorByCondicionFisicaVal(valorDTOCondicion);
			equipoTelefonicoDTO.setDescripcion(forma.getGsDescripcionEquipoTelef());
			equipoTelefonicoDTO.setCantidad(forma.getGsCantidadEquipoTelef());
			equipoTelefonicoDTO.setModelo(forma.getGsModeloEquipoTelef());
			
			ValorDTO valorDTORelacionHechos = new ValorDTO(forma.getRelacionHechoId());
			equipoTelefonicoDTO.setRelacionHechoVal(valorDTORelacionHechos);
						
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			
			equipoTelefonicoDTO.setCalidadDTO(calidadDTO);
			log.info("EXPEDIENTE_ID::: " + expedienteDTO.getExpedienteId());
			equipoTelefonicoDTO.setExpedienteDTO(expedienteDTO);
			
			//ACT-20110726 Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(forma.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				equipoTelefonicoDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(forma.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=forma.getFechaLevCadCus().split("-");
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(forma.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);	
				
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al vehiculo la cadena de custodia
				equipoTelefonicoDTO.setCadenaDeCustodia(cadenaDTO);
				log.debug("Cadena_custodia , seteo Cadena de custodia a vehiculo - "+forma.getCadenaCustodia());
			}
			if(forma.getAnular()!=null){
				
				equipoTelefonicoDTO.setEsActivo(!forma.getAnular());
			}
			Long resp = objetoDelegate.ingresarTelefono(equipoTelefonicoDTO);
			
			String xml = null;
			//PrintWriter pw = null;
			XStream converter=new XStream(); 			converter.alias("EquipoTelefonicoForm",EquipoTelefonicoForm.class);
			
			retorno.setGlTipoEquipoTelefId(resp);

			xml = converter.toXML(retorno);
			escribirRespuesta(response, xml);
//			response.setContentType("text/xml");
//			pw = response.getWriter();
//			pw.print(xml);
//			pw.flush();
//			pw.close();

		} catch (Exception e) {
			EquipoTelefonicoForm retorno = new EquipoTelefonicoForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("EquipoTelefonicoForm",EquipoTelefonicoForm.class);
			retorno.setGlTipoEquipoTelefId(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para guardar una arma
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarArma(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			ArmaForm retorno = new ArmaForm();
			log.info("ejecutando action guardar ARMA:::::");
			 
			 ArmaForm forma = (ArmaForm) form;
				
				log.info("FORMA ARMA:::::::::::::::::::::::");
				log.info("idArma="+ forma.getIdArma());
				log.info("Tipo="+ forma.getGlTipoArmaId());	
				log.info("Marca="+ forma.getGlMarcaArmaId());			
				log.info("Matricula="+ forma.getGcMatriculaArma());
				log.info("Calibre="+ forma.getGcCalibreArma());	
				log.info("Modelo="+forma.getGcModeloArma());
				log.info("Condicion="+forma.getGlCondicionFisicaArmaId());
				log.info("descripcion="+forma.getGcDescripcionArma());
				log.info("RelacionHechoId="+forma.getRelacionHechoId());
				
				if(forma.getRelacionHechoId()<= 0L){
					forma.setRelacionHechoId(null);
					log.info("RELACION ID, ES:"+forma.getRelacionHechoId());
				}
				
				String numeroExpediente=request.getParameter("numeroExpediente");
				ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
				if(forma.getGlTipoArmaId() <= 0L){
					forma.setGlTipoArmaId(null);
					log.info("TIPO EQUIPO, ES:"+forma.getGlTipoArmaId());
				}
				
				if(forma.getGlMarcaArmaId() <= 0L){
					forma.setGlMarcaArmaId(null);
					log.info("MARCA EQUIPO, ES:"+forma.getGlMarcaArmaId());
				}
					
				if(forma.getGcMatriculaArma().equalsIgnoreCase("")){
					forma.setGcMatriculaArma(null);
					log.info("MATRICULA, ES:"+forma.getGcMatriculaArma());
				}
				
				if(forma.getGcCalibreArma().equalsIgnoreCase("")){
					forma.setGcCalibreArma(null);
					log.info("CALIBRE, ES:" + forma.getGcCalibreArma());
				}
				
				if(forma.getGcModeloArma().equalsIgnoreCase("")){
					forma.setGcModeloArma(null);
					log.info("MODELO, ES:"+forma.getGcModeloArma());
				}
				
				if(forma.getGlCondicionFisicaArmaId() <= 0L ){
					forma.setGlCondicionFisicaArmaId(null);
					log.info("CONDICION, ES:"+forma.getGlCondicionFisicaArmaId());
				}
				
				

			ValorDTO valorDTOTipo = new ValorDTO(forma.getGlTipoArmaId());
			ValorDTO valorDTOMarca = new ValorDTO(forma.getGlMarcaArmaId());			
			ValorDTO valorDTOCondicion = new ValorDTO(forma.getGlCondicionFisicaArmaId());
			
			ArmaDTO armaDTO = new ArmaDTO();
			
			armaDTO.setElementoId(forma.getIdArma());
			armaDTO.setTipoArma(valorDTOTipo);
			armaDTO.setMarca(valorDTOMarca);
			armaDTO.setModelo(forma.getGcModeloArma());
			armaDTO.setMatricula(forma.getGcMatriculaArma());
			armaDTO.setCalibre(forma.getGcCalibreArma());
			armaDTO.setValorByCondicionFisicaVal(valorDTOCondicion);
			armaDTO.setDescripcion(forma.getGcDescripcionArma());
			
			ValorDTO valorDTORelacionHechos = new ValorDTO(forma.getRelacionHechoId());
			armaDTO.setRelacionHechoVal(valorDTORelacionHechos);
			
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			
			armaDTO.setCalidadDTO(calidadDTO);
			armaDTO.setExpedienteDTO(expedienteDTO);
			
			//ACT-20110726 Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(forma.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				armaDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(forma.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=forma.getFechaLevCadCus().split("-");
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(forma.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);	
				
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al arma la cadena de custodia
				armaDTO.setCadenaDeCustodia(cadenaDTO);
				log.debug("Cadena_custodia , seteo Cadena de custodia a Arma - "+forma.getCadenaCustodia());
			}
			if(forma.getAnular()!=null){
				
				armaDTO.setEsActivo(!forma.getAnular());
			}
			Long resp = objetoDelegate.ingresarArma(armaDTO);
				
			String xml = null;
			//PrintWriter pw = null;
			XStream converter=new XStream(); 			converter.alias("ArmaForm",ArmaForm.class);
			
			retorno.setGlTipoArmaId(resp);

			xml = converter.toXML(retorno);
			escribirRespuesta(response, xml);
//			response.setContentType("text/xml");
//			pw = response.getWriter();
//			pw.print(xml);
//			pw.flush();
//			pw.close();

		} catch (Exception e) {
			ArmaForm retorno = new ArmaForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("ArmaForm",ArmaForm.class);
			retorno.setGlTipoArmaId(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para guardar un explosivo
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarExplosivo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			ExplosivoForm retorno = new ExplosivoForm();
			log.info("ejecutando action guardar EXPLOSIVO:::::");
			 
			 ExplosivoForm forma = ( ExplosivoForm) form;
			 	
				log.info("FORMA EXPLOSIVO:::::::::::::::::::::::");
				log.info("Tipo="+ forma.getGlTipoExplosivoId());	
				log.info("Cantidad="+ forma.getGsCantidadExplosivo());			
				log.info("Unidad de Medida="+ forma.getGlUnidadMedidaExplosivoId());
				log.info("Condicion="+forma.getGlCondicionFisicaExplosivoId());
				log.info("descripcion="+forma.getGcDescripcionExplosivo());
				log.info("RelacionHechoId="+forma.getRelacionHechoId());
				
				if(forma.getRelacionHechoId()<= 0L){
					forma.setRelacionHechoId(null);
					log.info("RELACION ID, ES:"+forma.getRelacionHechoId());
				}
				
				String numeroExpediente=request.getParameter("numeroExpediente");
				ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
				if(forma.getGlTipoExplosivoId() <= 0L){
					forma.setGlTipoExplosivoId(null);
					log.info("TIPO EQUIPO, ES:"+forma.getGlTipoExplosivoId());
				}
				
				if(forma.getGsCantidadExplosivo() <= 0L){
					forma.setGsCantidadExplosivo(null);
					log.info("CANTIDAD EQUIPO, ES:"+forma.getGsCantidadExplosivo());
				}
					
				
				if(forma.getGlUnidadMedidaExplosivoId() <= 0L ){
					forma.setGlUnidadMedidaExplosivoId(null);
					log.info("UNIDAD DE MEDIDA ES:"+forma.getGlUnidadMedidaExplosivoId());
				}
				
				if(forma.getGlCondicionFisicaExplosivoId()<= 0L ){
					forma.setGlCondicionFisicaExplosivoId(null);
					log.info("CONDICION, ES:"+forma.getGlCondicionFisicaExplosivoId());
				}
				
				

			ValorDTO valorDTOTipo = new ValorDTO(forma.getGlTipoExplosivoId());	
			ValorDTO valorDTOUnidadMedida = new ValorDTO(forma.getGlUnidadMedidaExplosivoId());	
			ValorDTO valorDTOCondicion = new ValorDTO(forma.getGlCondicionFisicaExplosivoId());
			
			ExplosivoDTO explosivoDTO = new ExplosivoDTO();
			
			explosivoDTO.setElementoId(forma.getIdExplosivo());
			explosivoDTO.setTipoExplosivo(valorDTOTipo);
			explosivoDTO.setUnidadMedida(valorDTOUnidadMedida);
			explosivoDTO.setValorByCondicionFisicaVal(valorDTOCondicion);
			explosivoDTO.setCantidad(forma.getGsCantidadExplosivo());
			explosivoDTO.setDescripcion(forma.getGcDescripcionExplosivo());
			
			ValorDTO valorDTORelacionHechos = new ValorDTO(forma.getRelacionHechoId());
			explosivoDTO.setRelacionHechoVal(valorDTORelacionHechos);
			
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			
			explosivoDTO.setCalidadDTO(calidadDTO);
			explosivoDTO.setExpedienteDTO(expedienteDTO);
			
			//ACT-20110726 Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(forma.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				explosivoDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(forma.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=forma.getFechaLevCadCus().split("-");
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(forma.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);	
				
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al vehiculo la cadena de custodia
				explosivoDTO.setCadenaDeCustodia(cadenaDTO);
				log.info("cadeDTO:: "+cadenaDTO);
				log.info("evidenciaDTO:: "+evidencia);
				log.debug("Cadena_custodia , seteo Cadena de custodia a Explosivo - "+forma.getCadenaCustodia());
			}
			if(forma.getAnular()!=null){
				
				explosivoDTO.setEsActivo(!forma.getAnular());
			}
		
			Long resp = objetoDelegate.ingresarExplosivo(explosivoDTO);
				
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("ExplosivoForm",ExplosivoForm.class);
			retorno.setGlTipoExplosivoId(resp);
			xml = converter.toXML(retorno);
			log.info(xml);
			escribirRespuesta(response, xml);
		} catch (Exception e) {
			
			ExplosivoForm retorno = new ExplosivoForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("ExplosivoForm",ExplosivoForm.class);
			retorno.setGlTipoExplosivoId(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para guardar una sustancia
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarSustancia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			SustanciaForm retorno = new SustanciaForm();			
			log.info("ejecutando action guardar SUSTANCIA:::::");
			 
			 SustanciaForm forma = ( SustanciaForm) form;
			 String numeroExpediente=request.getParameter("numeroExpediente");
				ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);		
				log.info("FORMA SUSTANCIA:::::::::::::::::::::::");
				log.info("Tipo="+ forma.getGlTipoSustanciaId());
				log.info("Tipo de empaque="+ forma.getGlEmpaqueSustanciaId());
				log.info("Cantidad="+ forma.getGsCantidadSustancia());			
				log.info("Unidad de Medida="+ forma.getGlUnidadMedidaId());
				log.info("Condicion="+forma.getGlCondicionFisicaSustanciaId());
				log.info("descripcion="+forma.getGcDescripcionSustancia());
				log.info("RelacionHechoId="+forma.getRelacionHechoId());
				
				if(forma.getRelacionHechoId()<= 0L){
					forma.setRelacionHechoId(null);
					log.info("RELACION ID, ES:"+forma.getRelacionHechoId());
				}
			
				if(forma.getGlTipoSustanciaId() <= 0L){
					forma.setGlTipoSustanciaId(null);
					log.info("TIPO EQUIPO, ES:"+forma.getGlTipoSustanciaId());
				}
				
				if(forma.getGlEmpaqueSustanciaId() <= 0L){
					forma.setGlEmpaqueSustanciaId(null);
					log.info("TIPO DE EMPAQUE, ES:"+forma.getGlEmpaqueSustanciaId());
				}
					
				
				if(forma.getGsCantidadSustancia() <= 0L){
					forma.setGsCantidadSustancia(null);
					log.info("CANTIDAD DE SUSTANCIA:"+forma.getGsCantidadSustancia());
				}
				
				if(forma.getGlUnidadMedidaId()<= 0L ){
					forma.setGlUnidadMedidaId(null);
					log.info("UNIDAD DE MEDIDA:"+forma.getGlUnidadMedidaId());
				}
				
				if(forma.getGlCondicionFisicaSustanciaId() <= 0L){
					forma.setGlCondicionFisicaSustanciaId(null);
					log.info("CONDICION, ES:" + forma.getGlCondicionFisicaSustanciaId());
				}
				
				
				
			ValorDTO valorDTOTipo = new ValorDTO(forma.getGlTipoSustanciaId());
			ValorDTO valorDTOTipoEmpaque = new ValorDTO(forma.getGlEmpaqueSustanciaId());
			ValorDTO valorDTOUnidadMedida = new ValorDTO(forma.getGlUnidadMedidaId());
			ValorDTO valorDTOCondicion = new ValorDTO(forma.getGlCondicionFisicaSustanciaId());
			
			SustanciaDTO SustanciaDTO = new SustanciaDTO();
			
			SustanciaDTO.setElementoId(forma.getIdSustancia());
			SustanciaDTO.setTipoSustancia(valorDTOTipo);
			SustanciaDTO.setEmpaque(valorDTOTipoEmpaque);
			SustanciaDTO.setUnidadMedida(valorDTOUnidadMedida);
			SustanciaDTO.setValorByCondicionFisicaVal(valorDTOCondicion);
			SustanciaDTO.setCantidad(forma.getGsCantidadSustancia());			
			SustanciaDTO.setDescripcion(forma.getGcDescripcionSustancia());
			
			ValorDTO valorDTORelacionHechos = new ValorDTO(forma.getRelacionHechoId());
			SustanciaDTO.setRelacionHechoVal(valorDTORelacionHechos);
			
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			
			SustanciaDTO.setCalidadDTO(calidadDTO);
			SustanciaDTO.setExpedienteDTO(expedienteDTO);
		
			//ACT-20110726 Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(forma.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				SustanciaDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(forma.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=forma.getFechaLevCadCus().split("-");
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(forma.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);	
				
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al vehiculo la cadena de custodia
				SustanciaDTO.setCadenaDeCustodia(cadenaDTO);
				log.info("cadeDTO:: "+cadenaDTO);
				log.info("evidenciaDTO:: "+evidencia);
				log.debug("Cadena_custodia , seteo Cadena de custodia a Sustancia - "+forma.getCadenaCustodia());
			}
			if(forma.getAnular()!=null){
				
				SustanciaDTO.setEsActivo(!forma.getAnular());
			}
			
			Long resp = objetoDelegate.ingresarSustancia(SustanciaDTO);	
			
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("SustanciaForm",SustanciaForm.class);
			retorno.setGlTipoSustanciaId(resp);
			xml = converter.toXML(retorno);
			log.info(xml);
			escribirRespuesta(response, xml);
		} catch (Exception e) {
			SustanciaForm retorno = new SustanciaForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("SustanciaForm",SustanciaForm.class);
			retorno.setGlTipoSustanciaId(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para guardar un animal
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarAnimal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			AnimalForm retorno = new AnimalForm();
			log.info("ejecutando action guardar animal:.:::::");
			 
			AnimalForm forma = (AnimalForm) form;
			String numeroExpediente=request.getParameter("numeroExpediente");
			ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
				log.info("FORMA ANIMAL:::::::::::::::::::::::");
				log.info("Tipo="+ forma.getGlTipoAnimalId());
				log.info("Raza="+ forma.getGsRazaAnimal());
				log.info("Estado="+ forma.getGsEstadoAnimal());			
				log.info("Condicion="+forma.getGlCondicionFisicaAnimalId());
				log.info("descripcion="+forma.getGcDescripcionAnimal());
				log.info("RelacionHechoId="+forma.getRelacionHechoId());
				
				if(forma.getRelacionHechoId()<= 0L){
					forma.setRelacionHechoId(null);
					log.info("RELACION ID, ES:"+forma.getRelacionHechoId());
				}
			
				if(forma.getGlTipoAnimalId() <= 0L){
					forma.setGlTipoAnimalId(null);
					log.info("TIPO EQUIPO, ES:"+forma.getGlTipoAnimalId());
				}
				
				if(forma.getGsRazaAnimal().equalsIgnoreCase("")){
					forma.setGsRazaAnimal(null);
					log.info("RAZA, ES:"+forma.getGsRazaAnimal());
				}
					
				
				if(forma.getGsEstadoAnimal().equalsIgnoreCase("")){
					forma.setGsEstadoAnimal(null);
					log.info("ESTADO:"+forma.getGsEstadoAnimal());
				}
				
				if(forma.getGlCondicionFisicaAnimalId() <= 0L){
					forma.setGlCondicionFisicaAnimalId(null);
					log.info("CONDICION, ES:" + forma.getGlCondicionFisicaAnimalId());
				}
				
				
			
			ValorDTO valorDTOTipo = new ValorDTO(forma.getGlTipoAnimalId());
			ValorDTO valorDTOCondicion = new ValorDTO(forma.getGlCondicionFisicaAnimalId());
			
			AnimalDTO animalDTO = new AnimalDTO();
			
			animalDTO .setTipoAnimal(valorDTOTipo);
			animalDTO .setValorByCondicionFisicaVal(valorDTOCondicion);
			animalDTO .setEstadoAnimal(forma.getGsEstadoAnimal());
			animalDTO .setRazaAnimal(forma.getGsRazaAnimal());
			animalDTO .setDescripcion(forma.getGcDescripcionAnimal());
			
			ValorDTO valorDTORelacionHechos = new ValorDTO(forma.getRelacionHechoId());
			animalDTO.setRelacionHechoVal(valorDTORelacionHechos);
			
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			
			animalDTO.setElementoId(forma.getIdAnimal());
			animalDTO .setCalidadDTO(calidadDTO);
			animalDTO .setExpedienteDTO(expedienteDTO);
		
			//ACT-20110726 Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(forma.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				animalDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(forma.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=forma.getFechaLevCadCus().split("-");
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(forma.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);	
				
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al arma la cadena de custodia
				animalDTO.setCadenaDeCustodia(cadenaDTO);
				log.debug("Cadena_custodia , seteo Cadena de custodia a Animal - "+forma.getCadenaCustodia());
			}
			if(forma.getAnular()!=null){
				
				animalDTO.setEsActivo(!forma.getAnular());
			}

			//mandamos guardar el animal
			Long resp = objetoDelegate.ingresarAnimal(animalDTO );
			
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("AnimalForm",AnimalForm.class);
			retorno.setGlTipoAnimalId(resp);
			xml = converter.toXML(retorno);
			log.info(xml);
			escribirRespuesta(response, xml);
		} catch (Exception e) {
			AnimalForm retorno = new AnimalForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("AnimalForm",AnimalForm.class);
			retorno.setGlTipoAnimalId(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para guardar una aeronave
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarAeronave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			AeronaveForm retorno = new AeronaveForm();
			log.info("ejecutando action guardar AERONAVE:::::");
			 
			AeronaveForm forma = (AeronaveForm) form;
			String numeroExpediente=request.getParameter("numeroExpediente");
			ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
				log.info("FORMA AERONAVE::::::::-):-):-)Tipo="
				+ forma.getGlTipoAeronaveId()+"Marca="+ forma.getGlMarcaAeronaveId()+"Submarca="+forma.getGlSubMarcaAeronaveId()
				+"Modelo="+forma.getGcModeloAeronave()+"PaisDeOrigen"+forma.getGlPaisProcedAeronaveId()+
				"Color="+forma.getGlColorAeronaveId()+"Matricula="+forma.getGcMatriculaAeronave()+"Num Serie="+forma.getGcNoSerieAeronave()+
				"Num Motor="+forma.getGcNoMotorAeronave()+"Condicion="+forma.getGlCondicionAeronaveId()+"Descripcion"+forma.getGcDescripcionAeronave());
				
				log.info("FORMA AERONAVE:::::::::::::::::::::::");
				log.info("Tipo="+ forma.getGlTipoAeronaveId());	
				log.info("Marca="+ forma.getGlMarcaAeronaveId());			
				log.info("Sub Marca="+ forma.getGlSubMarcaAeronaveId());	
				log.info("Pais="+forma.getGlPaisProcedAeronaveId());
				log.info("Color="+ forma.getGlColorAeronaveId());
				log.info("Matricula="+forma.getGcMatriculaAeronave());
				log.info("No.Serie="+forma.getGcNoSerieAeronave());
				log.info("Motor="+forma.getGcNoMotorAeronave());
				log.info("Modelo="+forma.getGcModeloAeronave());
				log.info("Condicion="+forma.getGlCondicionAeronaveId());
				log.info("Descripcion="+forma.getGcDescripcionAeronave());
				log.info("RelacionHechoId="+forma.getRelacionHechoId());
			
				if(forma.getRelacionHechoId()<= 0L){
					forma.setRelacionHechoId(null);
					log.info("RELACION ID, ES:"+forma.getRelacionHechoId());
				}

				if(forma.getGlTipoAeronaveId()<= 0L){
					forma.setGlTipoAeronaveId(null);
					log.info("TIPO, ES:"+forma.getGlTipoAeronaveId());
				}
				
				if(forma.getGlMarcaAeronaveId() <= 0L){
					forma.setGlMarcaAeronaveId(null);
					log.info("MARCA, ES:"+forma.getGlMarcaAeronaveId());
				}
					
				if(forma.getGlSubMarcaAeronaveId() <= 0L){
					forma.setGlSubMarcaAeronaveId(null);
					log.info("SUB MARCA, ES:"+forma.getGlSubMarcaAeronaveId());
				}
				
				if(forma.getGlColorAeronaveId() <= 0L){
					forma.setGlColorAeronaveId(null);
					log.info("COLOR, ES:" + forma.getGlColorAeronaveId());
				}
				
				if(forma.getGlPaisProcedAeronaveId() <= 0L){
					forma.setGlPaisProcedAeronaveId(null);
					log.info("PAIS, ES:"+forma.getGlPaisProcedAeronaveId());
				}
				
				if(forma.getGcMatriculaAeronave().equalsIgnoreCase("")){
					forma.setGcMatriculaAeronave(null);
					log.info("MATRICULA, ES:"+forma.getGcMatriculaAeronave());
				}
				
				if(forma.getGcNoSerieAeronave().equalsIgnoreCase("") ){
					forma.setGcNoSerieAeronave(null);
					log.info("PLACAS, ES:"+forma.getGcNoSerieAeronave());
				}
				
				if(forma.getGcNoMotorAeronave().equalsIgnoreCase("") ){
					forma.setGcNoMotorAeronave(null);
					log.info("MOTOR, ES:"+forma.getGcNoMotorAeronave());
				}
				
				if(forma.getGcModeloAeronave().equalsIgnoreCase("") ){
					forma.setGcModeloAeronave(null);
					log.info("MODELO, ES:"+forma.getGcModeloAeronave());
				}
				
				if(forma.getGlCondicionAeronaveId() <= 0L){
					forma.setGlCondicionAeronaveId(null);
					log.info("CONDICION, ES:"+forma.getGlCondicionAeronaveId());
				}
				
				
			
				ValorDTO valorDTOTipo = new ValorDTO(forma.getGlTipoAeronaveId());
				ValorDTO valorDTOMarca = new ValorDTO(forma.getGlMarcaAeronaveId());
				ValorDTO valorDTOSubMarca = new ValorDTO(forma.getGlSubMarcaAeronaveId());
				ValorDTO valorDTOPais= new ValorDTO(forma.getGlPaisProcedAeronaveId());
				ValorDTO valorDTOColor= new ValorDTO(forma.getGlColorAeronaveId());
				ValorDTO valorDTOCondicion = new ValorDTO(forma.getGlCondicionAeronaveId());
				
				AeronaveDTO aeronaveDTO = new AeronaveDTO ();
				
				aeronaveDTO.setElementoId(forma.getIdAeronave());
				aeronaveDTO.setTipoAeroNave(valorDTOTipo);
				aeronaveDTO.setMarca(valorDTOMarca);
				aeronaveDTO.setSubMarca(valorDTOSubMarca);
				aeronaveDTO.setPaisProcedencia(valorDTOPais);
				aeronaveDTO.setColor(valorDTOColor);
				aeronaveDTO.setValorByCondicionFisicaVal(valorDTOCondicion);
				aeronaveDTO.setModelo(forma.getGcModeloAeronave());
				aeronaveDTO.setMatricula(forma.getGcMatriculaAeronave());
				aeronaveDTO.setNoSerie(forma.getGcNoSerieAeronave());
				aeronaveDTO.setNoMotor(forma.getGcNoMotorAeronave());
				aeronaveDTO.setDescripcion(forma.getGcDescripcionAeronave());
				
				ValorDTO valorDTORelacionHechos = new ValorDTO(forma.getRelacionHechoId());
				aeronaveDTO.setRelacionHechoVal(valorDTORelacionHechos);
				
				CalidadDTO calidadDTO = new CalidadDTO();
				calidadDTO.setCalidades(Calidades.EVIDENCIA);
				
				aeronaveDTO.setCalidadDTO(calidadDTO);
				aeronaveDTO.setExpedienteDTO(expedienteDTO);
				
				//ACT-20110726 Se agrega cadena de custodia en caso de contar con ella
				if(StringUtils.isEmpty(forma.getCadenaCustodia()))
				{
					log.debug("Cadena_custodia - vacia");
					aeronaveDTO.setCadenaDeCustodia(null);
				}
				else
				{
					CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
					cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(forma.getCadenaCustodia()));
					//seteo la informacion de la evidencia
					EvidenciaDTO evidencia=new EvidenciaDTO();
					String[] fechaHora=forma.getFechaLevCadCus().split("-");
					evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
					evidencia.setOrigenEvidencia(forma.getOrigenEvdCadCus());
					evidencia.setCodigoBarras("");
					evidencia.setDescripcion("");
					evidencia.setNumeroEvidencia(0L);	
					
					//agregamos la evidencia a la cadena de custodia
					cadenaDTO.setEvidencia(evidencia);
					//agregamos al vehiculo la cadena de custodia
					aeronaveDTO.setCadenaDeCustodia(cadenaDTO);
					log.info("cadeDTO:: "+cadenaDTO);
					log.info("evidenciaDTO:: "+evidencia);
					log.debug("Cadena_custodia , seteo Cadena de custodia a Aeronave - "+forma.getCadenaCustodia());
				}
				if(forma.getAnular()!=null){
					
					aeronaveDTO.setEsActivo(!forma.getAnular());
				}
			
				Long resp = objetoDelegate.ingresarAeronave(aeronaveDTO);
				
				String xml = null;
				XStream converter=new XStream(); 			converter.alias("AeronaveForm",AeronaveForm.class);
				retorno.setIdAeronave(resp);
				xml = converter.toXML(retorno);
				log.info(xml);
				escribirRespuesta(response, xml);
				
		} catch (Exception e) {
			AeronaveForm retorno = new AeronaveForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("AeronaveForm",AeronaveForm.class);
			retorno.setGlTipoAeronaveId(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para guardar una embarcacion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarEmbarcacion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			EmbarcacionForm retorno = new EmbarcacionForm();
			log.info("ejecutando action guardar EMBARCACION:::::");
			 
			EmbarcacionForm forma = (EmbarcacionForm) form;
			 
				log.info("FORMA EMBARCACION:::::::::::::::::::::::");
				log.info("Tipo="+ forma.getGlTipoEmbarcacionId());	
				log.info("Marca="+ forma.getGlMarcaEmbarcacionId());			
				log.info("Sub Marca="+ forma.getGlSubMarcaEmbarcacionId());	
				log.info("Color="+ forma.getGlColorEmbarcacionId());
				log.info("Pais="+forma.getGlPaisEmbarcacionId());
				log.info("Nombre="+forma.getGcNombreEmbarcacion());
				log.info("Motor="+forma.getGcNoMotorEmbarcacion());
				log.info("No.Serie="+forma.getGcNoSerieEmbarcacion());
				log.info("Matricula="+forma.getGcMatriculaEmbarcacion());
				log.info("Condicion="+forma.getGlCondicionFisica());
				log.info("Descripcion="+forma.getGcDescripcion());
				log.info("RelacionHechoId="+forma.getRelacionHechoId());
				
				if(forma.getRelacionHechoId()<= 0L){
					forma.setRelacionHechoId(null);
					log.info("RELACION ID, ES:"+forma.getRelacionHechoId());
				}
				
				String numeroExpediente=request.getParameter("numeroExpediente");
				ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
				if(forma.getGlTipoEmbarcacionId() <= 0L){
					forma.setGlTipoEmbarcacionId(null);
					log.info("TIPO, ES:"+forma.getGlTipoEmbarcacionId());
				}
				
				if(forma.getGlMarcaEmbarcacionId() <= 0L){
					forma.setGlMarcaEmbarcacionId(null);
					log.info("MARCA, ES:"+forma.getGlMarcaEmbarcacionId());
				}
					
				if(forma.getGlSubMarcaEmbarcacionId() <= 0L){
					forma.setGlSubMarcaEmbarcacionId(null);
					log.info("SUB MARCA, ES:"+forma.getGlSubMarcaEmbarcacionId());
				}
				
				if(forma.getGlColorEmbarcacionId() <= 0L){
					forma.setGlColorEmbarcacionId(null);
					log.info("COLOR, ES:" + forma.getGlColorEmbarcacionId());
				}
				
				if(forma.getGlPaisEmbarcacionId() <= 0L){
					forma.setGlPaisEmbarcacionId(null);
					log.info("PAIS, ES:"+forma.getGlPaisEmbarcacionId());
				}
				
				if(forma.getGcNombreEmbarcacion().equalsIgnoreCase("")){
					forma.setGcNombreEmbarcacion(null);
					log.info("NOMBRE, ES:"+forma.getGcNombreEmbarcacion());
				}
				
				if(forma.getGcNoMotorEmbarcacion().equalsIgnoreCase("") ){
					forma.setGcNoMotorEmbarcacion(null);
					log.info("MOTOR, ES:"+forma.getGcNoMotorEmbarcacion());
				}
				
				if(forma.getGcNoSerieEmbarcacion().equalsIgnoreCase("") ){
					forma.setGcNoSerieEmbarcacion(null);
					log.info("SERIE, ES:"+forma.getGcNoSerieEmbarcacion());
				}
				
				if(forma.getGcMatriculaEmbarcacion().equalsIgnoreCase("")){
					forma.setGcMatriculaEmbarcacion(null);
					log.info("MATRICULA, ES:"+forma.getGcMatriculaEmbarcacion());
				}
				
				if(forma.getGlCondicionFisica() <= 0L){
					forma.setGlCondicionFisica(null);
					log.info("PAIS, ES:"+forma.getGlCondicionFisica());
				}
				
				
			
			
			ValorDTO valorDTOTipo = new ValorDTO(forma.getGlTipoEmbarcacionId());
			ValorDTO valorDTOMarca = new ValorDTO(forma.getGlMarcaEmbarcacionId());
			ValorDTO valorDTOSubMarca = new ValorDTO(forma.getGlSubMarcaEmbarcacionId());
			ValorDTO valorDTOColor= new ValorDTO(forma.getGlColorEmbarcacionId());
			ValorDTO valorDTOPais= new ValorDTO(forma.getGlPaisEmbarcacionId());
			ValorDTO valorDTOCondicion = new ValorDTO(forma.getGlCondicionFisica());
			
			EmbarcacionDTO embarcacionDTO= new EmbarcacionDTO();
			
			embarcacionDTO.setElementoId(forma.getIdEmbarcacion());
			embarcacionDTO.setTipoEmbarcacion	(valorDTOTipo);
			embarcacionDTO.setMarca(valorDTOMarca);
			embarcacionDTO.setSubMarca(valorDTOSubMarca);
			embarcacionDTO.setColor(valorDTOColor);
			embarcacionDTO.setPaisOrigen(valorDTOPais);
			embarcacionDTO.setValorByCondicionFisicaVal(valorDTOCondicion);
			embarcacionDTO.setNombreEmbarcacion(forma.getGcNombreEmbarcacion());
			embarcacionDTO.setNoMotor(forma.getGcNoMotorEmbarcacion());
			embarcacionDTO.setNoSerie(forma.getGcNoSerieEmbarcacion());
			embarcacionDTO.setMatricula(forma.getGcMatriculaEmbarcacion());
			embarcacionDTO.setDescripcion(forma.getGcDescripcion());
			
			ValorDTO valorDTORelacionHechos = new ValorDTO(forma.getRelacionHechoId());
			embarcacionDTO.setRelacionHechoVal(valorDTORelacionHechos);
			
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			
			embarcacionDTO.setCalidadDTO(calidadDTO);
			embarcacionDTO.setExpedienteDTO(expedienteDTO);
		
			//ACT-20110726 Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(forma.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				embarcacionDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(forma.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=forma.getFechaLevCadCus().split("-");
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(forma.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);	
				
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al vehiculo la cadena de custodia
				embarcacionDTO.setCadenaDeCustodia(cadenaDTO);
				log.info("cadeDTO:: "+cadenaDTO);
				log.info("evidenciaDTO:: "+evidencia);
				log.debug("Cadena_custodia , seteo Cadena de custodia a Embarcacion - "+forma.getCadenaCustodia());
			}
			if(forma.getAnular()!=null){
				
				embarcacionDTO.setEsActivo(!forma.getAnular());
			}
			Long resp = objetoDelegate.ingresarEmbarcacion(embarcacionDTO);
			
				
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("EmbarcacionForm",EmbarcacionForm.class);
			retorno.setGlTipoEmbarcacionId(resp);
			xml = converter.toXML(retorno);
			log.info(xml);
			escribirRespuesta(response, xml);
				
		} catch (Exception e) {
			EmbarcacionForm retorno = new EmbarcacionForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("EmbarcacionForm",EmbarcacionForm.class);
			retorno.setGlTipoEmbarcacionId(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para guardar numerario
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarNumerario(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			NumerarioForm retorno = new NumerarioForm();
			log.info("ejecutando action guardar NUMERARIO:::::");
			 
			NumerarioForm forma = (NumerarioForm) form;
			String numeroExpediente=request.getParameter("numeroExpediente");
			ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
				log.info("FORMA EMBARCACION:::::::::::::::::::::::");
				log.info("Cantidad="+ forma.getGlCantidad());	
				log.info("Moneda="+ forma.getGcMoneda());			
				log.info("Cta. Tesoreria="+ forma.getGcCtaTesoreria());	
				log.info("Fecha="+ forma.getGcFechaDeposito());
				log.info("Hora="+forma.getGcHoraDeposito());
				log.info("Condicion="+forma.getGlCondicionFisica());
				log.info("Descripcion="+forma.getGcDescripcion());
				log.info("RelacionHechoId="+forma.getRelacionHechoId());
				
				if(forma.getRelacionHechoId()<= 0L){
					forma.setRelacionHechoId(null);
					log.info("RELACION ID, ES:"+forma.getRelacionHechoId());
				}
							
				if(forma.getGlCantidad() <= 0L){
					forma.setGlCantidad(null);
					log.info("CANTIDAD, ES:"+forma.getGlCantidad());
				}
				
				if(forma.getGcMoneda().equalsIgnoreCase("")){
					forma.setGcMoneda(null);
					log.info("MONEDA, ES:"+forma.getGcMoneda());
				}
				
				if(forma.getGcCtaTesoreria().equalsIgnoreCase("")){
					forma.setGcCtaTesoreria(null);
					log.info("CTA TESORERIA, ES:"+forma.getGcCtaTesoreria());
				}
				
				if(forma.getGcFechaDeposito().equalsIgnoreCase("")){
					//Si la fecha es nula, la hora no sera tomada en cuenta
					forma.setGcFechaDeposito(null);
					forma.setGcHoraDeposito(null);
					log.info("FECHA, ES:"+forma.getGcFechaDeposito());
				}
				
				if(forma.getGcHoraDeposito().equalsIgnoreCase("")){
					forma.setGcHoraDeposito(null);
					log.info("HORA, ES:"+forma.getGcHoraDeposito());
				}
					
				if(forma.getGlCondicionFisica() <= 0L){
					forma.setGlCondicionFisica(null);
					log.info("PAIS, ES:"+forma.getGlCondicionFisica());
				}
				
				
				
			ValorDTO valorDTOCondicion = new ValorDTO(forma.getGlCondicionFisica());
			
			NumerarioDTO numerarioDTO = new NumerarioDTO();
			
			numerarioDTO.setElementoId(forma.getIdNumerario());
			numerarioDTO.setCantidad(forma.getGlCantidad());
			numerarioDTO.setMoneda(forma.getGcMoneda());
			//Setear la fecha y la hora
			numerarioDTO.setFechaDeposito(DateUtils.obtener(forma.getGcFechaDeposito(), forma.getGcHoraDeposito()));
			numerarioDTO.setCtaTesoreria(forma.getGcCtaTesoreria());
			numerarioDTO.setValorByCondicionFisicaVal(valorDTOCondicion);
			numerarioDTO.setDescripcion(forma.getGcDescripcion());
			
			ValorDTO valorDTORelacionHechos = new ValorDTO(forma.getRelacionHechoId());
			numerarioDTO.setRelacionHechoVal(valorDTORelacionHechos);
			
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			
			numerarioDTO.setCalidadDTO(calidadDTO);
			numerarioDTO.setExpedienteDTO(expedienteDTO);
			
			//ACT-20110726 Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(forma.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				numerarioDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(forma.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=forma.getFechaLevCadCus().split("-");
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(forma.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);	
				
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al vehiculo la cadena de custodia
				numerarioDTO.setCadenaDeCustodia(cadenaDTO);
				log.info("cadeDTO:: "+cadenaDTO);
				log.info("evidenciaDTO:: "+evidencia);
				log.debug("Cadena_custodia , seteo Cadena de custodia a Explosivo - "+forma.getCadenaCustodia());
			}
			if(forma.getAnular()!=null){
				
				numerarioDTO.setEsActivo(!forma.getAnular());
			}
		
			Long resp = objetoDelegate.ingresarNumerario(numerarioDTO);
			
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("NumerarioForm",NumerarioForm.class);
			retorno.setIdNumerario(resp);
			xml = converter.toXML(retorno);
			log.info(xml);
			escribirRespuesta(response, xml);
				
		} catch (Exception e) {
			NumerarioForm retorno = new NumerarioForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("NumerarioForm",NumerarioForm.class);
			retorno.setIdNumerario(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
		
	/**
	 * Metodo utilizado para guardar vegetal
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarVegetal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			VegetalForm retorno = new VegetalForm();
			
			log.info("ejecutando action guardar VEGETAL:::::");
			 
			VegetalForm formaVegetal = (VegetalForm) form;
			 
				log.info("FORMA VEGETAL:::::::::::::::::::::::");
				log.info("Tipo="+ formaVegetal.getGlTipoVegetalId());	
				log.info("Cantidad="+ formaVegetal.getGsCantidadVegetal());			
				log.info("Unidad Medida="+formaVegetal.getGlUMedidaVegetalId());	
				log.info("Condicion="+formaVegetal.getGlCondicionFisica());
				log.info("Descripcion="+formaVegetal.getGcDescripcion());
				log.info("RelacionHechoId="+formaVegetal.getRelacionHechoId());
				
				if(formaVegetal.getRelacionHechoId()<= 0L){
					formaVegetal.setRelacionHechoId(null);
					log.info("RELACION ID, ES:"+formaVegetal.getRelacionHechoId());
				}
				
				String numeroExpediente=request.getParameter("numeroExpediente");
				ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
				if(formaVegetal.getGlTipoVegetalId() <= 0L){
					formaVegetal.setGlTipoVegetalId(null);
					log.info("TIPO, ES:"+formaVegetal.getGlTipoVegetalId());
				}
				
				if(formaVegetal.getGsCantidadVegetal() <= 0L){
					formaVegetal.setGsCantidadVegetal(null);
					log.info("CANTIDAD, ES:"+formaVegetal.getGsCantidadVegetal());
				}
				
				if(formaVegetal.getGlUMedidaVegetalId() <= 0L){
					formaVegetal.setGlUMedidaVegetalId(null);
					log.info("UNIDAD DE MEDIDA, ES:"+formaVegetal.getGlUMedidaVegetalId());
				}
				
				if(formaVegetal.getGlCondicionFisica() <= 0L){
					formaVegetal.setGlCondicionFisica(null);
					log.info("CONDICION, ES:"+formaVegetal.getGlCondicionFisica());
				}
				
				
				
								
			ValorDTO valorDTOUnidadMedida = new ValorDTO(formaVegetal.getGlUMedidaVegetalId());
			ValorDTO valorDTOTipo = new ValorDTO(formaVegetal.getGlTipoVegetalId());
			ValorDTO valorDTOCondicion = new ValorDTO(formaVegetal.getGlCondicionFisica());
			
			VegetalDTO vegetalDTO = new VegetalDTO();
			
			vegetalDTO.setElementoId(formaVegetal.getIdVegetal());
			vegetalDTO.setUnidadMedida(valorDTOUnidadMedida);
			vegetalDTO.setTipoVegetal(valorDTOTipo);
			vegetalDTO.setValorByCondicionFisicaVal(valorDTOCondicion);
			
			
			vegetalDTO.setTipoVegetal(valorDTOTipo);
			vegetalDTO.setCantidad(formaVegetal.getGsCantidadVegetal());
			vegetalDTO.setUnidadMedida(valorDTOUnidadMedida);
			vegetalDTO.setValorByCondicionFisicaVal(valorDTOCondicion);
			vegetalDTO.setDescripcion(formaVegetal.getGcDescripcion());
			
			ValorDTO valorDTORelacionHechos = new ValorDTO(formaVegetal.getRelacionHechoId());
			vegetalDTO.setRelacionHechoVal(valorDTORelacionHechos);
			
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			
			vegetalDTO.setCalidadDTO(calidadDTO);
			vegetalDTO.setExpedienteDTO(expedienteDTO);
			
			//ACT-20110726 Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(formaVegetal.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				vegetalDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(formaVegetal.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=formaVegetal.getFechaLevCadCus().split("-");
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(formaVegetal.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);	
				
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al vehiculo la cadena de custodia
				vegetalDTO.setCadenaDeCustodia(cadenaDTO);
				log.info("cadeDTO:: "+cadenaDTO);
				log.info("evidenciaDTO:: "+evidencia);
				log.debug("Cadena_custodia , seteo Cadena de custodia a Vegetal - "+formaVegetal.getCadenaCustodia());
			}
			if(formaVegetal.getAnular()!=null){
				
				vegetalDTO.setEsActivo(!formaVegetal.getAnular());
			}
		
			Long resp = objetoDelegate.ingresarVegetal(vegetalDTO);
			
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("VegetalForm",VegetalForm.class);
			retorno.setGlTipoVegetalId(resp);
			xml = converter.toXML(retorno);
			log.info(xml);
			escribirRespuesta(response, xml);
				
		} catch (Exception e) {
			VegetalForm retorno = new VegetalForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("VegetalForm",VegetalForm.class);
			retorno.setGlTipoVegetalId(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	

	/**
	 * Metodo utilizado para guardar documento oficial
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarDocumentoOficial(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			DocumentoOficialForm retorno = new DocumentoOficialForm ();			
			log.info("ejecutando action guardar DOCUMENTO OFICIAL:::::");
			 
			DocumentoOficialForm formaDoc = (DocumentoOficialForm) form;
			 	
				log.info("FORMA DOCUMENTO OFICIAL:::::::::::::::::::::::");
				log.info("Tipo="+ formaDoc.getGlTipoDocOficialId());	
				log.info("Cantidad="+ formaDoc.getGsCantidadDocOficial());	
				log.info("Condicion="+formaDoc.getGlCondicionFisica());
				log.info("Descripcion="+formaDoc.getGcDescripcion());
				log.info("RelacionHechoId="+formaDoc.getRelacionHechoId());
				
				if(formaDoc.getRelacionHechoId()<= 0L){
					formaDoc.setRelacionHechoId(null);
					log.info("RELACION ID, ES:"+formaDoc.getRelacionHechoId());
				}
				
				String numeroExpediente=request.getParameter("numeroExpediente");
				ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
				if(formaDoc.getGlTipoDocOficialId() <= 0L){
					formaDoc.setGlTipoDocOficialId(null);
					log.info("TIPO, ES:"+formaDoc.getGlTipoDocOficialId());
				}
				
				if(formaDoc.getGsCantidadDocOficial() <= 0L){
					formaDoc.setGsCantidadDocOficial(null);
					log.info("CANTIDAD, ES:"+formaDoc.getGsCantidadDocOficial());
				}
				
				if(formaDoc.getGlCondicionFisica() <= 0L){
					formaDoc.setGlCondicionFisica(null);
					log.info("CONDICION, ES:"+formaDoc.getGlCondicionFisica());
				}
				
				
				
			
			ValorDTO valorDTOTipo = new ValorDTO(formaDoc.getGlTipoDocOficialId());
			ValorDTO valorDTOCondicion = new ValorDTO(formaDoc.getGlCondicionFisica());
			
			DocumentoOficialDTO documentoOficialDTO = new DocumentoOficialDTO();

			//setamos el id del documento
			documentoOficialDTO.setElementoId(formaDoc.getIdDocOficial());
			documentoOficialDTO.setTipoDocumento(valorDTOTipo);
			documentoOficialDTO.setValorByCondicionFisicaVal(valorDTOCondicion);
			documentoOficialDTO.setDescripcion(formaDoc.getGcDescripcion());
			documentoOficialDTO.setCantidad(formaDoc.getGsCantidadDocOficial());
			
			ValorDTO valorDTORelacionHechos = new ValorDTO(formaDoc.getRelacionHechoId());
			documentoOficialDTO.setRelacionHechoVal(valorDTORelacionHechos);
			
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			
			documentoOficialDTO.setCalidadDTO(calidadDTO);
			documentoOficialDTO.setExpedienteDTO(expedienteDTO);
			
			//ACT-20110726 Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(formaDoc.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				documentoOficialDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(formaDoc.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=formaDoc.getFechaLevCadCus().split("-");
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(formaDoc.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);	
				
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al vehiculo la cadena de custodia
				documentoOficialDTO.setCadenaDeCustodia(cadenaDTO);
				log.info("cadeDTO:: "+cadenaDTO);
				log.info("evidenciaDTO:: "+evidencia);
				log.debug("Cadena_custodia , seteo Cadena de custodia a Documento Oficial - "+formaDoc.getCadenaCustodia());
			}
			if(formaDoc.getAnular()!=null){
				
				documentoOficialDTO.setEsActivo(!formaDoc.getAnular());
			}
		
			Long resp = objetoDelegate.ingresarDocumentoOficial(documentoOficialDTO);
			
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("DocumentoOficialForm",DocumentoOficialForm.class);
			retorno.setGlTipoDocOficialId(resp);
			xml = converter.toXML(retorno);
			log.info(xml);
			escribirRespuesta(response, xml);
				
		} catch (Exception e) {
						
			DocumentoOficialForm retorno = new DocumentoOficialForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("DocumentoOficialForm",DocumentoOficialForm.class);
			retorno.setGlTipoDocOficialId(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para guardar joya
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarJoya(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			JoyaForm retorno = new JoyaForm();
			log.info("ejecutando action guardar JOYA:::::");
			 
			JoyaForm forma = (JoyaForm) form;
			 
				log.info("FORMA JOYA:::::::::::::::::::::::");
				log.info("Tipo="+ forma.getGlTipoJoyaId());	
				log.info("Material="+ forma.getGcMaterialJoya());
				log.info("Cantidad="+ forma.getGsCantidadJoya());	
				log.info("Condicion="+forma.getGlCondicionFisica());
				log.info("Descripcion="+forma.getGcDescripcion());
				log.info("RelacionHechoId="+forma.getRelacionHechoId());
				
				if(forma.getRelacionHechoId()<= 0L){
					forma.setRelacionHechoId(null);
					log.info("RELACION ID, ES:"+forma.getRelacionHechoId());
				}
				
				String numeroExpediente=request.getParameter("numeroExpediente");
				ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
				if(forma.getGlTipoJoyaId() <= 0L){
					forma.setGlTipoJoyaId(null);
					log.info("TIPO, ES:"+forma.getGlTipoJoyaId());
				}
				
				if(forma.getGcMaterialJoya().equalsIgnoreCase("")){
					forma.setGcMaterialJoya(null);
					log.info("MATERIAL, ES:"+forma.getGcMaterialJoya());
				}
				
				if(forma.getGsCantidadJoya() <= 0L){
					forma.setGsCantidadJoya(null);
					log.info("CANTIDAD, ES:"+forma.getGsCantidadJoya());
				}
				
				if(forma.getGlCondicionFisica() <= 0L){
					forma.setGlCondicionFisica(null);
					log.info("CONDICION, ES:"+forma.getGlCondicionFisica());
				}
				
				
			
			ValorDTO valorDTOTipo = new ValorDTO(forma.getGlTipoJoyaId());
			ValorDTO valorDTOCondicion = new ValorDTO(forma.getGlCondicionFisica());
			
			JoyaDTO joyaDTO = new JoyaDTO();
			
			//seteamos el Id de la joya
			joyaDTO.setElementoId(forma.getIdJoya());
			joyaDTO.setTipoJoya(valorDTOTipo);
			joyaDTO.setValorByCondicionFisicaVal(valorDTOCondicion);
			joyaDTO.setDescripcion(forma.getGcDescripcion());
			joyaDTO.setMaterial(forma.getGcMaterialJoya());
			joyaDTO.setCantidad(forma.getGsCantidadJoya());
			
			ValorDTO valorDTORelacionHechos = new ValorDTO(forma.getRelacionHechoId());
			joyaDTO.setRelacionHechoVal(valorDTORelacionHechos);
			
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			
			joyaDTO.setCalidadDTO(calidadDTO);
			joyaDTO.setExpedienteDTO(expedienteDTO);
			
			//ACT-20110726 Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(forma.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				joyaDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(forma.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=forma.getFechaLevCadCus().split("-");
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(forma.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);	
				
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al vehiculo la cadena de custodia
				joyaDTO.setCadenaDeCustodia(cadenaDTO);
				log.info("cadeDTO:: "+cadenaDTO);
				log.info("evidenciaDTO:: "+evidencia);
				log.debug("Cadena_custodia , seteo Cadena de custodia a Explosivo - "+forma.getCadenaCustodia());
			}
			if(forma.getAnular()!=null){
				
				joyaDTO.setEsActivo(!forma.getAnular());
			}
			Long resp = objetoDelegate.ingresarJoya(joyaDTO);
			
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("JoyaForm",JoyaForm.class);
			retorno.setGlTipoJoyaId(resp);
			xml = converter.toXML(retorno);
			log.info(xml);
			escribirRespuesta(response, xml);	
		} catch (Exception e) {
			
			JoyaForm retorno = new JoyaForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("JoyaForm",JoyaForm.class);
			retorno.setGlTipoJoyaId(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para guardar obras de arte
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarObraDeArte(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			ObraDeArteForm retorno = new ObraDeArteForm();
			log.info("ejecutando action guardar OBRA DE ARTE:::::");
			 
			ObraDeArteForm forma = (ObraDeArteForm) form;
				
				log.info("FORMA OBRA DE ARTE:::::::::::::::::::::::");
				log.info("Tipo="+ forma.getGlTipoObraArteId());	
				log.info("Cantidad="+ forma.getGsCantidadObraArte());	
				log.info("Condicion="+forma.getGlCondicionFisica());
				log.info("Descripcion="+forma.getGcDescripcion());
				log.info("RelacionHechoId="+forma.getRelacionHechoId());
				
				if(forma.getRelacionHechoId()<= 0L){
					forma.setRelacionHechoId(null);
					log.info("RELACION ID, ES:"+forma.getRelacionHechoId());
				}
				
				String numeroExpediente=request.getParameter("numeroExpediente");
				ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
				if(forma.getGlTipoObraArteId() <= 0L){
					forma.setGlTipoObraArteId(null);
					log.info("TIPO, ES:"+forma.getGlTipoObraArteId());
				}
				
				if(forma.getGsCantidadObraArte() <= 0L){
					forma.setGsCantidadObraArte(null);
					log.info("CANTIDAD, ES:"+forma.getGsCantidadObraArte());
				}
				
				if(forma.getGlCondicionFisica() <= 0L){
					forma.setGlCondicionFisica(null);
					log.info("CONDICION, ES:"+forma.getGlCondicionFisica());
				}
				
				

			ValorDTO valorDTOTipo = new ValorDTO(forma.getGlTipoObraArteId());
			ValorDTO valorDTOCondicion = new ValorDTO(forma.getGlCondicionFisica());
			
			ObraArteDTO obraDeArteDTO = new ObraArteDTO();
			
			obraDeArteDTO.setTipoObraArte(valorDTOTipo);
			obraDeArteDTO.setCantidad(forma.getGsCantidadObraArte());
			obraDeArteDTO.setValorByCondicionFisicaVal(valorDTOCondicion);
			obraDeArteDTO.setDescripcion(forma.getGcDescripcion());
			
			ValorDTO valorDTORelacionHechos = new ValorDTO(forma.getRelacionHechoId());
			obraDeArteDTO.setRelacionHechoVal(valorDTORelacionHechos);
			
			//agregamos el id de la obra 
			obraDeArteDTO.setElementoId(forma.getIdObraDeArte());
			
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			
			obraDeArteDTO.setCalidadDTO(calidadDTO);
			obraDeArteDTO.setExpedienteDTO(expedienteDTO);
			
			//ACT-20110726 Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(forma.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				obraDeArteDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(forma.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=forma.getFechaLevCadCus().split("-");
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(forma.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);	
				
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al vehiculo la cadena de custodia
				obraDeArteDTO.setCadenaDeCustodia(cadenaDTO);
				log.info("cadeDTO:: "+cadenaDTO);
				log.info("evidenciaDTO:: "+evidencia);
				log.debug("Cadena_custodia , seteo Cadena de custodia a Obra de Arte - "+forma.getCadenaCustodia());
			}
			if(forma.getAnular()!=null){
				
				obraDeArteDTO.setEsActivo(!forma.getAnular());
			}
		
			Long resp = objetoDelegate.ingresarObraArte(obraDeArteDTO);
		
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("ObraArteForm",ObraDeArteForm.class);
			retorno.setGlTipoObraArteId(resp);
			xml = converter.toXML(retorno);
			log.info(xml);
			escribirRespuesta(response, xml);
		} catch (Exception e) {
			ObraDeArteForm retorno = new ObraDeArteForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("ObraArteForm",ObraDeArteForm.class);
			retorno.setGlTipoObraArteId(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para guardar objeto Otros
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarOtros(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			OtrosForm retorno = new OtrosForm();
			log.info("ejecutando action guardar OBJETO OTROS:::::");
			
			OtrosForm forma = (OtrosForm) form;
			
			log.info("FORMA OTROS:::::::::::::::::::::::");
			log.info("Nombre="+forma.getGcNombre());
			log.info("Descripcion="+forma.getGcDescripcion());
			log.info("RelacionHechoId="+forma.getRelacionHechoId());
			
			if(forma.getRelacionHechoId()<= 0L){
				forma.setRelacionHechoId(null);
				log.info("RELACION ID, ES:"+forma.getRelacionHechoId());
			}
			
			String numeroExpediente=request.getParameter("numeroExpediente");
			ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
			
			if(forma.getIdOtros() <= 0L){
				forma.setIdOtros(null);
				log.info("ID, ES:"+forma.getIdOtros());
			}
			
			if(forma.getGcNombre().equalsIgnoreCase("")){
				forma.setGcNombre(null);
				log.info("NOMBRE, ES:" + forma.getGcNombre());
			}
			
			
			
			ObjetoDTO otrosDTO = new ObjetoDTO();
			
			otrosDTO.setElementoId(forma.getIdOtros());
			otrosDTO.setNombreObjeto(forma.getGcNombre());
			otrosDTO.setDescripcion(forma.getGcDescripcion());
			otrosDTO.setTipoObjeto(Objetos.OTRO);
			otrosDTO.setFechaCreacionElemento(new Date());
			
			ValorDTO valorDTORelacionHechos = new ValorDTO(forma.getRelacionHechoId());
			otrosDTO.setRelacionHechoVal(valorDTORelacionHechos);
			
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			
			otrosDTO.setCalidadDTO(calidadDTO);
			otrosDTO.setExpedienteDTO(expedienteDTO);
			
			//Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(forma.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				otrosDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(forma.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=forma.getFechaLevCadCus().split("-");
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(forma.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);	
				
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al vehiculo la cadena de custodia
				otrosDTO.setCadenaDeCustodia(cadenaDTO);
				log.info("cadeDTO:: "+cadenaDTO);
				log.info("evidenciaDTO:: "+evidencia);
				log.debug("Cadena_custodia , seteo Cadena de custodia a OBJETO_OTRO - "+forma.getCadenaCustodia());
			}
			if(forma.getAnular()!=null){
				
				otrosDTO.setEsActivo(!forma.getAnular());
			}
			
			Long resp = objetoDelegate.ingresarOtroObjeto(otrosDTO);
			
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("OtrosForm",OtrosForm.class);
			retorno.setIdOtros(resp);
			xml = converter.toXML(retorno);
			log.info(xml);
			escribirRespuesta(response, xml);
		} catch (Exception e) {
			OtrosForm retorno = new OtrosForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("OtrosForm",OtrosForm.class);
			retorno.setIdOtros(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	
	/**
	 * Metodo utilizado para guardar la imagen asociada a un Elemento
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarImagenDelElemento(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			VehiculoForm forma = (VehiculoForm) form;
			Long elementoID=Long.parseLong(request.getParameter("elementoID"));		
			log.info("FORMA VEHICULO:::::::::::::::::::::::");
			log.info("idVehiculo="+ forma.getIdVehiculo());			
			log.info("Imagen="+forma.getArchivo());							

			//Permite guardar la foto de un elemento
			if(forma.getArchivo() != null){				
				ArchivoDigitalDTO adjunto = new ArchivoDigitalDTO();

				FormFile archivo = forma.getArchivo();
		        String contentType = archivo.getContentType();
		        String fileName    = archivo.getFileName();
		        log.debug(" EL ADJUNTO GAMASOFT: El nombre del archivo es" + archivo.getFileName());
		        byte[] fileData    = archivo.getFileData();
				adjunto.setContenido(fileData);
				adjunto.setNombreArchivo(fileName);
				adjunto.setTipoArchivo(contentType);
				adjunto.setUsuario(super.getUsuarioFirmado(request));
				ElementoDTO elemento = new ElementoDTO();
				elemento.setElementoId(elementoID);				
				elementoDelegate.adjuntarArchivoAElemento(elemento, adjunto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("success");
	}
	
	
	/**
	 * Metodo utilizado para obtener la imagen de un elemento 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward obtenImagenDeElemento(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        try {
            String idObjeto = request.getParameter("elementoID");

            log.info("$$$$ ID de Objeto solicitado$$$ : " + idObjeto);
            ObjetoDTO objetoDTO = new ObjetoDTO(Long.parseLong(idObjeto));
            objetoDTO = objetoDelegate.obtenerObjeto(objetoDTO);

            response.setContentType("image/gif");
            if (objetoDTO.getFotoDelElemento() != null) {
                byte[] imag = objetoDTO.getFotoDelElemento().getContenido();
                if (imag != null) {
                    ServletOutputStream out = response.getOutputStream();
                    out.write(imag);
                    out.close();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
	
	/**
	 * M�todo utilizado para realizar la carga del combo de Tipo de Objetos en Cadena de Custodia
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward cargarTiposObjetos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Tipo Objeto
			log.debug("ejecutando Action cargarTiposObjetos");
			List<CatalogoDTO> listaCatalogo = catDelegate.recuperarCatalogo(Catalogos.TIPO_OBJETO);
			XStream converter=new XStream();
			converter.alias("listaCatalogo", java.util.List.class);
			converter.alias("catTipoObjetos", CatalogoDTO.class);
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
	 * Metodo utilizado para guardar objeto Pericial
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarObjetoPericial(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			final PaginacionDTO pag = PaginacionThreadHolder.get();
			if (pag != null){
				pag.setCampoOrd(" oe.tipoObjeto.valor ");
				PaginacionThreadHolder.set(pag);
			}

			ObjetoPericialForm retorno = new ObjetoPericialForm();
			log.info("ejecutando action guardar OBJETO pericial:::::");
			 
			ObjetoPericialForm forma = (ObjetoPericialForm) form;
				
				log.info("FORMA PERICIAL:::::::::::::::::::::::");
				log.info("Descripcion="+forma.getGcDescripcion());
				log.info("CategoriaIndicioId="+forma.getCategoriaIndicioId());
				log.info("IndicioId="+forma.getIndicioId());

				String numeroExpediente=request.getParameter("numeroExpediente");
				ExpedienteDTO expedienteDTO=super.getExpedienteTrabajo(request,numeroExpediente);
				
				if(forma.getIdOtros() <= 0L){
					forma.setIdOtros(null);
					log.info("ID, ES:"+forma.getIdOtros());
				}
				
				

			ObjetoPericialDTO objetoPericialDTO = new ObjetoPericialDTO();
			
			objetoPericialDTO.setElementoId(forma.getIdOtros());
			objetoPericialDTO.setDescripcion(forma.getGcDescripcion());
			objetoPericialDTO.setTipoObjeto(Objetos.PERICIAL);
			objetoPericialDTO.setFechaCreacionElemento(new Date());
			
			ValorDTO valorCategoriaIndicio = new ValorDTO(forma.getCategoriaIndicioId());
			objetoPericialDTO.setCategoriaIndicioVal(valorCategoriaIndicio);
			
			ValorDTO valorIndicio = new ValorDTO(forma.getIndicioId());
			objetoPericialDTO.setIndicioVal(valorIndicio);
			
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.EVIDENCIA);
			
			objetoPericialDTO.setCalidadDTO(calidadDTO);
			objetoPericialDTO.setExpedienteDTO(expedienteDTO);
			
			//Se agrega cadena de custodia en caso de contar con ella
			if(StringUtils.isEmpty(forma.getCadenaCustodia()))
			{
				log.debug("Cadena_custodia - vacia");
				objetoPericialDTO.setCadenaDeCustodia(null);
			}
			else
			{
				CadenaDeCustodiaDTO cadenaDTO= new CadenaDeCustodiaDTO();
				cadenaDTO.setCadenaDeCustodiaId(Long.parseLong(forma.getCadenaCustodia()));
				//seteo la informacion de la evidencia
				EvidenciaDTO evidencia=new EvidenciaDTO();
				String[] fechaHora=forma.getFechaLevCadCus().split("-");
				evidencia.setFechaLevantamiento(DateUtils.obtener(fechaHora[0], fechaHora[1]));
				evidencia.setOrigenEvidencia(forma.getOrigenEvdCadCus());
				evidencia.setCodigoBarras("");
				evidencia.setDescripcion("");
				evidencia.setNumeroEvidencia(0L);	
				
				//agregamos la evidencia a la cadena de custodia
				cadenaDTO.setEvidencia(evidencia);
				//agregamos al vehiculo la cadena de custodia
				objetoPericialDTO.setCadenaDeCustodia(cadenaDTO);
				log.info("cadeDTO:: "+cadenaDTO);
				log.info("evidenciaDTO:: "+evidencia);
				log.debug("Cadena_custodia , seteo Cadena de custodia a OBJETO_PERICIAL - "+forma.getCadenaCustodia());
			}
			if(forma.getAnular()!=null){
				
				objetoPericialDTO.setEsActivo(!forma.getAnular());
			}
		
			Long resp = objetoDelegate.ingresarObjetoPericial(objetoPericialDTO);
		
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("ObjetoPericialForm",ObjetoPericialForm.class);
			retorno.setIdOtros(resp);
			xml = converter.toXML(retorno);
			log.info(xml);
			escribirRespuesta(response, xml);
		} catch (Exception e) {
			ObjetoPericialForm retorno = new ObjetoPericialForm();
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("ObjetoPericialForm",ObjetoPericialForm.class);
			retorno.setIdOtros(0L);
			xml = converter.toXML(retorno);
			escribir(response, xml, null);
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de los estudios periciales que se encuentran 
	 * relacionados con un tipo de objeto y son regresados dentro de un xml para su despliegue 
	 * dentro de un grid.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarEstudiosPorTipoObjeto(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String tipoObjeto = request.getParameter(PARAM_TIPO_OBJETO);
		
		if (tipoObjeto != null){
			ValorDTO valor = new ValorDTO(Long.parseLong(tipoObjeto));
			try {
				List<ObjetoEstudioDTO> objetosEstudio = objetoDelegate.consultarEstudiosPorTipoObjeto(valor);
				response.setContentType("text/xml; charset=ISO-8859-1");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer;
				try {
					writer = response.getWriter();
					writer.print("<rows>");
					
					//Configuracion del Paginador
					String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
					writer.print(paginacion);
					
					String datos = crearDatosGrid(objetosEstudio);
					writer.print(datos);
					writer.print("</rows>");
					writer.flush();
					writer.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			} catch (NSJPNegocioException e) {
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la creación de una fila para el grid que muestra los estudios asociados
	 * a un tipo de objeto.
	 * @param objetosEstudio - lista de DTO's que se transformaran a XML con los datos a mostrar dentro del grid.
	 * @return sb - String con la informaci&oacute;n que se va a mostrar dentro del grid.
	 */
	private String crearDatosGrid(List<ObjetoEstudioDTO> objetosEstudio){
		StringBuffer sb = new StringBuffer();
		if (objetosEstudio != null && !objetosEstudio.isEmpty()){
			for (ObjetoEstudioDTO objEst : objetosEstudio){
				sb.append("<row id='"+objEst.getObjetoEstudioId().toString()+"'>");
				sb.append("<cell><![CDATA["+objEst.getEstudioPericial().getValor()+"]]></cell>");
				sb.append("</row>");
			}
		}
		return sb.toString();
	}
	
	
	public ActionForward existeCadenaDeCustodiaPorIdObjeto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try{
			
			Long elementoID= NumberUtils.toLong(request.getParameter("elementoID"), 0L);
			Boolean respuesta = false;
			
			XStream converter=new XStream(); 			converter.alias("respuesta",String.class);
			
			if( elementoID > 0){		
				respuesta = objetoDelegate.existeCadenaDeCustodiaPorIdObjeto(elementoID);
				
				if(respuesta){
					escribirRespuesta(response,converter.toXML("1"));
				}
					escribirRespuesta(response,converter.toXML("0"));				
			}
		}catch(NSJPNegocioException ne){
			log.info(ne.getCause(), ne);
				XStream converter=new XStream(); 			converter.alias("respuesta",String.class);
				escribirRespuesta(response,converter.toXML("-1"));
		}
		
		return null;
	}
	
	public ActionForward existenEslabonesPorIdObjeto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try{
			
			Long elementoID= NumberUtils.toLong(request.getParameter("elementoID"), 0L);
			Boolean respuesta = false;
			
			XStream converter=new XStream(); 			converter.alias("respuesta",String.class);
			
			if( elementoID > 0){		
				respuesta = objetoDelegate.existenEslabonesPorIdObjeto(elementoID);
				
				if(respuesta){
					escribirRespuesta(response,converter.toXML("1"));
				}
					escribirRespuesta(response,converter.toXML("0"));				
			}
		}catch(NSJPNegocioException ne){
			log.info(ne.getCause(), ne);
				XStream converter=new XStream(); 			converter.alias("respuesta",String.class);
				escribirRespuesta(response,converter.toXML("-1"));
		}
		
		return null;
	}

	
	public ActionForward consultarArchivosDigitalesXElementoId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		try {
			Long elementoID = NumberUtils.toLong(request.getParameter("elementoID"),0L); 
			log.info("ID del elemento :: "+elementoID);

			if(elementoID >0){
				List<ArchivoDigitalDTO> listaDocumentoDTOs = elementoDelegate.consultarArchivosDigitalesXElementoId(elementoID);
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
			
				writer.print("<rows>");
				final PaginacionDTO pag = PaginacionThreadHolder.get();
	            if (pag != null && pag.getTotalRegistros() != null) {
	                writer.print("<total>" + pag.getTotalPaginas() + "</total>");
	                writer.print("<records>" + pag.getTotalRegistros() + "</records>");
	            } else {
	                writer.print("<total>0</total>");
	                writer.print("<records>0</records>");
	            }
				
				for (ArchivoDigitalDTO documentoDTO : listaDocumentoDTOs) {
					writer.print("<row id='"+documentoDTO.getArchivoDigitalId()+"'>");
					writer.print("<cell>"+documentoDTO.getNombreArchivo()+"</cell>");
					writer.print("<cell>"+documentoDTO.getTipoArchivo()+"</cell>");
					writer.print("</row>");
				}
				writer.print("</rows>");
				writer.flush();
				writer.close();
				
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}
	
	
	/**
	 * M�todo utilizado que obtiene los objetos filtrados por expediente y tipo.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarObjetos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Tipo Objeto
			log.debug("ejecutando Action consultarObjetos");
			ExpedienteDTO expedienteDTO = null;
			ValorDTO tipoObjeto = null;
			Long expedienteId = NumberUtils.toLong(request.getParameter("expedienteId"));
			Long tipoObj = NumberUtils.toLong(request.getParameter("tipoObjeto"));
			boolean esGrid = BooleanUtils.toBoolean(request.getParameter("esGrid"));
			StringBuilder xml = new StringBuilder();
			
			if(expedienteId != 0L || tipoObj != 0L){
			
				Objetos objeto = Objetos.getByValor(tipoObj);
				String aliasObjeto = "";
				if(expedienteId != 0L){
					expedienteDTO= new ExpedienteDTO(expedienteId);
				}
				
				if(objeto != null){
					tipoObjeto = new ValorDTO();
					tipoObjeto.setIdCampo(objeto.getValorId());
					aliasObjeto = objeto.getNombreEntity();
				}else{
					aliasObjeto = "Objeto";
				}
				
				List<ObjetoDTO> listaObjetos = objetoDelegate.consultarObjetos(expedienteDTO, tipoObjeto);
				if (esGrid){
					if(listaObjetos != null){
						xml.append("<rows>");
						
						//Configuracion del Paginador
						String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
						xml.append(paginacion);
						
						xml.append(generarGridObjetos(listaObjetos, objeto));
						
						
						xml.append("</rows>");
					}
				}else{
					@SuppressWarnings("rawtypes")
					Class tipo;
					switch (objeto){
						case VEHICULO :
							tipo = VehiculoDTO.class;
							break;
						case EMBARCACION :
							tipo = EmbarcacionDTO.class;
							break;
						case AERONAVE :
							tipo = AeronaveDTO.class;
							break; 
						case SUSTANCIA :
							tipo = SustanciaDTO.class;
							break;
						case ARMA :
							tipo = ArmaDTO.class;
							break;
						case EXPLOSIVO :
							tipo = ExplosivoDTO.class;
							break;
						case NUMERARIO :
							tipo = NumerarioDTO.class;
							break;
						default: 	tipo = ObjetoDTO.class;
									break;
					}
					XStream converter=new XStream(); 			converter.alias("lista"+aliasObjeto, java.util.List.class);
					converter.alias(aliasObjeto, tipo);
					
					xml.append(converter.toXML(listaObjetos));
				}
				response.setContentType("text/xml");
				PrintWriter pw = response.getWriter();
				pw.print(xml.toString());
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}


	/**
	 * M&eacute;todo que genera el XML correspondiente al grid de objetos solicitados.
	 * @param listaObjetos
	 * @param objeto
	 * @return
	 */
	private String generarGridObjetos(List<ObjetoDTO> listaObjetos, Objetos objeto) {
		
		StringBuilder sb = new StringBuilder();
		
		for (ObjetoDTO objetoDTO : listaObjetos) {
			String tmp = ""; 
			switch (objeto){
				case VEHICULO :
					if(objetoDTO instanceof VehiculoDTO){
						VehiculoDTO obj = ((VehiculoDTO)objetoDTO);
						sb.append("<row id='"+obj.getElementoId()+"'>");
						if(obj.getValorByTipoVehiculo() != null && obj.getValorByTipoVehiculo().getValor() != null){
							tmp = obj.getValorByTipoVehiculo().getValor();
						} else {
							tmp = "";
						}
						sb.append("<cell>"+tmp+"</cell>");
						if(obj.getPlaca() != null && !obj.getPlaca().isEmpty()){
							tmp = obj.getPlaca();
						} else {
							tmp = "&nbsp;";
						}					
						sb.append("<cell>"+tmp+"</cell>");
						sb.append("</row>");
					}
					break;
				case EMBARCACION :
					if(objetoDTO instanceof EmbarcacionDTO){
						EmbarcacionDTO obj = ((EmbarcacionDTO)objetoDTO);
						sb.append("<row id='"+obj.getElementoId()+"'>");
						if(obj.getTipoEmbarcacion() != null && obj.getTipoEmbarcacion().getValor() != null){
							tmp = obj.getTipoEmbarcacion().getValor();
						} else {
							tmp = "&nbsp;";
						}
						sb.append("<cell>"+tmp+"</cell>");						
						sb.append("</row>");
					}					
					break;
				case AERONAVE :
					if(objetoDTO instanceof AeronaveDTO){
						AeronaveDTO obj = ((AeronaveDTO)objetoDTO);
						sb.append("<row id='"+obj.getElementoId()+"'>");
						if(obj.getTipoAeroNave() != null && obj.getTipoAeroNave().getValor() != null){
							tmp = obj.getTipoAeroNave().getValor();
						} else {
							tmp = "&nbsp;";
						}
						sb.append("<cell>"+tmp+"</cell>");
						sb.append("</row>");
					}					
					break; 
				case SUSTANCIA :
					if(objetoDTO instanceof SustanciaDTO){
						SustanciaDTO obj = ((SustanciaDTO)objetoDTO);
						sb.append("<row id='"+obj.getElementoId()+"'>");
						if(obj.getTipoSustancia() != null && obj.getTipoSustancia().getValor() != null){
							tmp = obj.getTipoSustancia().getValor();
						} else {
							tmp = "&nbsp;";
						}
						sb.append("<cell>"+tmp+"</cell>");
						sb.append("</row>");
					}										
					break;
				case ARMA :
					if(objetoDTO instanceof ArmaDTO){
						ArmaDTO obj = ((ArmaDTO)objetoDTO);
						sb.append("<row id='"+obj.getElementoId()+"'>");
						if(obj.getTipoArma() != null && obj.getTipoArma().getValor() != null){
							tmp = obj.getTipoArma().getValor();
						} else {
							tmp = "&nbsp;";
						}
						sb.append("<cell>"+tmp+"</cell>");
						sb.append("</row>");
					}					
					
					break;
				case EXPLOSIVO :
					if(objetoDTO instanceof ExplosivoDTO){
						ExplosivoDTO obj = ((ExplosivoDTO)objetoDTO);
						sb.append("<row id='"+obj.getElementoId()+"'>");
						if(obj.getTipoExplosivo() != null && obj.getTipoExplosivo().getValor() != null){
							tmp = obj.getTipoExplosivo().getValor();
						} else {
							tmp = "&nbsp;";
						}
						sb.append("<cell>"+tmp+"</cell>");
						sb.append("</row>");
					}					
					
					break;
				case NUMERARIO :
					if(objetoDTO instanceof NumerarioDTO){
						NumerarioDTO obj = ((NumerarioDTO)objetoDTO);
						sb.append("<row id='"+obj.getElementoId()+"'>");
						if(obj.getMoneda() != null && !obj.getMoneda().isEmpty()){
							tmp = obj.getMoneda();
						} else {
							tmp = "&nbsp;";
						}
						sb.append("<cell>"+tmp+"</cell>");
						sb.append("</row>");
					}					
					break;
				case OTRO :
						sb.append("<row id='"+objetoDTO.getElementoId()+"'>");
						if(objetoDTO.getNombreObjeto() != null && !objetoDTO.getNombreObjeto().isEmpty()){
							tmp = objetoDTO.getNombreObjeto();
						} else {
							tmp = "&nbsp;";
						}
						sb.append("<cell>"+tmp+"</cell>");
						sb.append("</row>");
					break;
				default:
					break;
			}
		}
		return sb.toString();
	}
	
}
