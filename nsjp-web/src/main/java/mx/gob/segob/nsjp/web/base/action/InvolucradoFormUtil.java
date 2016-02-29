/**
 * Nombre del Programa : InvolucradoFormUtil.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 05/06/2013
 * Marca de cambio        : N/A
 * Descripcion General    : Clase de utileria para la manipulaci&ocute;n del Form (IngresarInvolucradoForm)  para extraer el DTO (InvolucradoDTO) con todos sus datos.
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
package mx.gob.segob.nsjp.web.base.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.base.GenericDTO;
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
import mx.gob.segob.nsjp.dto.involucrado.AliasInvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.DatosDefuncionDTO;
import mx.gob.segob.nsjp.dto.involucrado.DetencionDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.MediaFiliacionDTO;
import mx.gob.segob.nsjp.dto.involucrado.SeniaParticularDTO;
import mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO;
import mx.gob.segob.nsjp.dto.persona.CorreoElectronicoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.persona.TelefonoDTO;
import mx.gob.segob.nsjp.web.caso.form.IngresarActaCircunstanciadaForm;
import mx.gob.segob.nsjp.web.caso.form.IngresarIndividuoForm;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

/**
 * Clase de utileria para la manipulaci&oacute;n del Form (IngresarInvolucradoForm) 
 * para extraer el DTO (InvolucradoDTO) con todos sus datos.
 * 
 * @author GustavoBP
 */
public class InvolucradoFormUtil {
	private static final Logger logger = Logger
			.getLogger(InvolucradoFormUtil.class);

	public static Short CALIDAD_PROBABLE_RESPONSABLE = new Short((short) 0);
	public static Short CALIDAD_CONTACTO_ORGANIZACIONAL = new Short((short) 1);
	public static Short CALIDAD_VICTIMA_PERSONA = new Short((short) 2);
	public static Short CALIDAD_TUTOR = new Short((short) 3);
	public static Short CALIDAD_DENUNCIANTE = new Short((short) 4);
	public static Short CALIDAD_TESTIGO = new Short((short) 5);
	public static Short CALIDAD_REPRESENTANTE_LEGAL = new Short((short) 6);
	public static Short CALIDAD_TRADUCTOR = new Short((short) 7);
	public static Short CALIDAD_QUIEN_DETUVO = new Short((short) 8);
	public static Short CALIDAD_DEFENSOR = new Short((short) 9);
	public static Short CALIDAD_DEFENSOR_PRIVADO = new Short((short) 10);
	public static Short CALIDAD_SOLICITANTE = new Short((short) 11);
	public static Short CALIDAD_DEFENDIDO = new Short((short) 12);
	public static Long TIPO_PERSONA_MORAL = new Long(0L);
	public static Long TIPO_PERSONA_FISICA = new Long(1L);
	public static Long IDCAMPO_TIPOORGANIZACION = new Long(419L);
	public static Long IDCAMPO_SITUACION_JURIDICA = new Long(61L);

	/**
	 * Metodo de utileria que se encarga de extraer los datos de la forma de Involucrado,  
	 * asi como algunos datos del request.
	 *  
	 * @param expedienteDTO
	 * @param retorno
	 * @param form
	 * @param request
	 * @return InvolucradoDTO con los datos de la forma.
	 * @throws Exception Es atrapada por el Action que invoca.
	 */
	//TODO Modularizar el metodo es muy grande
	public static InvolucradoDTO extraerDatosInvolucradoForm(
			ExpedienteDTO expedienteDTO, IngresarIndividuoForm retorno,
			ActionForm form, HttpServletRequest request) throws Exception {
	
		String esMismoDomicilio=request.getParameter("mismoDomicilioNotificaciones");
		logger.info("ejecutando Action guardarIndividuo esMismoDomicilio "+ esMismoDomicilio);
		
		IngresarIndividuoForm forma = (IngresarIndividuoForm) form;
		InvolucradoDTO involucradoDTO = new InvolucradoDTO();
		involucradoDTO.setExpedienteDTO(expedienteDTO);
		if(esMismoDomicilio != null && esMismoDomicilio.equals("1"))
			involucradoDTO.setEsMismoDomicilio(true);
		else
			involucradoDTO.setEsMismoDomicilio(false);
		
		logger.info("#########$$$$$$$$$Numero expedinte:"+expedienteDTO);
		logger.info("&&&&&&&&&&&&&Forma:"+forma);
		Long idDetencion = NumberUtils.toLong(request.getParameter("idDetencion"),0L);
		logger.info("id de la detencion:"+idDetencion);
		String idIndividuo=request.getParameter("idIndividuo");
		
		logger.info("&&&&&&&&&&&&&Elemnto id:"+idIndividuo);
		involucradoDTO.setElementoId(NumberUtils.toLong(idIndividuo,0L));
		CalidadDTO calidadDTO = new CalidadDTO();
		
		//Se asigna los valores anteriores del tipo evento y subtipo evento
		
		Short tipoEvento = NumberUtils.toShort(request.getParameter("tipoEvento")); 
		Short subtipoEvento = NumberUtils.toShort(request.getParameter("subtipoEvento"));
		involucradoDTO.setTipoEvento(tipoEvento != 0 ? tipoEvento : null);
		involucradoDTO.setSubtipoDeEvento(subtipoEvento != 0 ? subtipoEvento : null);
		
		
		if(forma.getCalidadDelIndividuo().equals(CALIDAD_QUIEN_DETUVO)){
			//agregamos los alias del involucrado
			/*if(StringUtils.isNotBlank(forma.getAlias()))
			{
				List<AliasInvolucradoDTO> listaAliasDTO = new ArrayList<AliasInvolucradoDTO>();
				//obtenemos los alias
				String[] arrAliasInvlocurado=forma.getAlias().split(",");
				logger.info("alias_del_involucrado:: "+arrAliasInvlocurado);	
				for (String alias : arrAliasInvlocurado) {
					AliasInvolucradoDTO aliasDTO = new AliasInvolucradoDTO();
                    aliasDTO.setAlias(alias);
                    listaAliasDTO.add(aliasDTO);
                    aliasDTO=null;
				}
                involucradoDTO.setAliasInvolucradoDTO(listaAliasDTO);
			}*/							
		}
		
		if (forma.getCalidadDelIndividuo().equals(
				CALIDAD_PROBABLE_RESPONSABLE)
				|| forma.getCalidadDelIndividuo().equals(CALIDAD_DEFENDIDO)) {
			
			
			if (forma.getEsPersonaMoral()) {
				logger.info("ingresaremos un probable responsable moral");
				involucradoDTO.setTipoPersona(TIPO_PERSONA_MORAL);
				calidadDTO
						.setCalidades(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION);
				// Datos propios de la pantalla de probable responsable
				// fisico
				OrganizacionDTO organizacionDTO = new OrganizacionDTO();
				organizacionDTO.setNombreOrganizacion(forma.getNombreOrg());
				organizacionDTO.setNombreCorto(forma.getNombreCortoOrg());
				organizacionDTO
						.setDireccionInternet(forma.getDirInternet());

				ValorDTO valorByTipoOrganizacionVal = new ValorDTO();
				valorByTipoOrganizacionVal
						.setIdCampo(IDCAMPO_TIPOORGANIZACION);

				// TODO: Setear las propiedades del tipo de organizacion
				// para que sean guardadas
				if (!forma.getTipoOrganizacion().equals("-1")) {
					valorByTipoOrganizacionVal.setValor(forma
							.getTipoOrganizacion());
				}

				organizacionDTO
						.setValorByTipoOrganizacionVal(valorByTipoOrganizacionVal);
				organizacionDTO.setFechaCreacionElemento(DateUtils
						.obtener(forma.getFechaIngreso()));
				CalidadDTO calidadOrg = new CalidadDTO();
				calidadOrg
						.setCalidades(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION);
				organizacionDTO.setCalidadDTO(calidadOrg);
				involucradoDTO.setOrganizacionDTO(organizacionDTO);

			} else {
				logger.info("ingresaremos un probable responsable fisico");
				involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
				
				if (forma.getCalidadDelIndividuo().equals(
						CALIDAD_PROBABLE_RESPONSABLE)) {
					calidadDTO
							.setCalidades(Calidades.PROBABLE_RESPONSABLE_PERSONA);
				} else {
					calidadDTO.setCalidades(Calidades.DEFENDIDO);
				}
				
				// Datos propios de la pantalla de probable responsable
				// fisico
				involucradoDTO.setCondicion(forma.getCalidadDelIndividuo());
				involucradoDTO.setEsVivo(forma.getEsVivo());
				if (forma.getEsDesconocido()) {
					involucradoDTO.setDesconocido("true");
					forma.setNombre("Desconocido");
					forma.setApellidoPaterno(" ");
					forma.setApellidoMaterno(" ");
				} else {
					involucradoDTO.setDesconocido("false");
				}
				if (forma.getEsMenorDeEdad()) {

				}
				involucradoDTO.setEsDetenido(forma.getEstaDetenido());
				logger.info("Afuera de detenido:"+forma.getEstaDetenido());		
				
				if(forma.getEstaDetenido()){
					
					logger.info("Entra a setear datos de esta detenido:"+forma.getEstaDetenido());						
					ArrayList<DetencionDTO> detencionDTOs = new ArrayList<DetencionDTO>();
					DetencionDTO detencionDTO = new DetencionDTO();
					detencionDTO.setFechaInicioDetencion(forma.getFechaInicioLapso());
					detencionDTO.setHoraInicioDetencion(forma.getHoraInicioLapso());
					detencionDTO.setFechaRecepcionDetencion(forma.getFechaFinLapso());
					detencionDTO.setHoraRecepcionDetencion(forma.getHoraFinLapso());
					if(idDetencion!=0){
						logger.info("Entra a setear el id del objeto detencion:"+ idDetencion);		
						detencionDTO.setDetencionId(idDetencion);
					}
					detencionDTOs.add(detencionDTO);
					involucradoDTO.setDetenciones(detencionDTOs);

					ValorDTO situacionJuridicaDTO = new ValorDTO();
					Long claveSituacionJuridica=0L;
					if(forma.getSituacionJuridica()!=null && ! forma.getSituacionJuridica().equals("- Selecciona -")){
						claveSituacionJuridica=Long.parseLong(forma.getSituacionJuridica());
					}else{
						claveSituacionJuridica=null;
					}
					situacionJuridicaDTO.setIdCampo(claveSituacionJuridica);
					involucradoDTO.setValorSituacionJuridica(situacionJuridicaDTO);

				}
				//agregamos los alias del involucrado
				/*if(StringUtils.isNotBlank(forma.getAlias()))
				{
					List<AliasInvolucradoDTO> listaAliasDTO = new ArrayList<AliasInvolucradoDTO>();
					//obtenemos los alias
					String[] arrAliasInvlocurado=forma.getAlias().split(",");
					logger.info("alias_del_involucrado:: "+arrAliasInvlocurado);	
					for (String alias : arrAliasInvlocurado) {
						AliasInvolucradoDTO aliasDTO = new AliasInvolucradoDTO();
	                    aliasDTO.setAlias(alias);
	                    listaAliasDTO.add(aliasDTO);
	                    aliasDTO=null;
					}
                    involucradoDTO.setAliasInvolucradoDTO(listaAliasDTO);
				}*/
				
				//Guardo objeto media filiacion en el involucrado
				//involucradoDTO.setMediaFiliacionDTO(obtenerMediaFiliacion(forma));
			}
		} else if (forma.getCalidadDelIndividuo().equals(
				CALIDAD_CONTACTO_ORGANIZACIONAL)) {
			logger.info("ingresaremos un contacto de organizacion");
			calidadDTO.setCalidades(Calidades.CONTACTO_ORGANIZACION);
		} else if (forma.getCalidadDelIndividuo().equals(
				CALIDAD_VICTIMA_PERSONA)) {
			logger.info("ingresaremos una victima");
			calidadDTO.setCalidades(Calidades.VICTIMA_PERSONA);
			
				logger.info("ingresaremos un victima fisica");
				involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
				
				//F24.11 - Cambio para que guarde el estado de una victima, saludos
				involucradoDTO.setEsVivo(forma.getEsVivo());
				//Seteamos datos de victima muerta
				if(!forma.getEsVivo())
					involucradoDTO.setDatosDefuncion(obtenerDatosDefuncion(forma, expedienteDTO));
			
			//agregamos los alias del involucrado
			/*if(StringUtils.isNotBlank(forma.getAlias()))
			{
				List<AliasInvolucradoDTO> listaAliasDTO = new ArrayList<AliasInvolucradoDTO>();
				//obtenemos los alias
				String[] arrAliasInvlocurado=forma.getAlias().split(",");
				logger.info("alias_del_involucrado:: "+arrAliasInvlocurado);	
				for (String alias : arrAliasInvlocurado) {
					AliasInvolucradoDTO aliasDTO = new AliasInvolucradoDTO();
                    aliasDTO.setAlias(alias);
                    listaAliasDTO.add(aliasDTO);
                    aliasDTO=null;
				}
                involucradoDTO.setAliasInvolucradoDTO(listaAliasDTO);
			}*/
			
			//Guardo objeto media filiacion en el involucrado
			involucradoDTO.setMediaFiliacionDTO(obtenerMediaFiliacion(forma));
			if (forma.getEsDesconocido()) {
				involucradoDTO.setDesconocido("true");
				forma.setNombre("Desconocido");
				forma.setApellidoPaterno("");
				forma.setApellidoMaterno("");
			} else {
				involucradoDTO.setDesconocido("false");
			}
				logger.info("ingresaremos un victima fisica");
				involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
			
			
		} else if (forma.getCalidadDelIndividuo().equals(CALIDAD_TUTOR)) {
			logger.info("ingresaremos un tutor");
			
				logger.info("ingresaremos un tutor fisica");
				involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
			
				calidadDTO.setCalidades(Calidades.TUTOR);
			
				logger.info("ingresaremos un tutor fisica");
				involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
				involucradoDTO.setEsVivo(forma.getEsVivo());
			
		} else if (forma.getCalidadDelIndividuo().equals(
				CALIDAD_DENUNCIANTE)) {
			logger.info("ingresaremos un denunciante");
			involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
			involucradoDTO.setCondicion((short)0);
			if(forma.getEsVictimayDenunciante()){
				involucradoDTO.setCondicion((short)1);
			}
			involucradoDTO.setDesconocido("");
			if (forma.getEsAnonimo()) {
				involucradoDTO.setDesconocido("true");
			} else {
				involucradoDTO.setDesconocido("false");
			}
			if (forma.getEsAutoridad()) {
				involucradoDTO.setEsAutoridad(true);
			} else {
				involucradoDTO.setEsAutoridad(false);
			}
			if (!forma.getEsPersonaMoral()) {
				logger.info("ingresaremos un Denunciante fisica");
				involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
			}
			
			calidadDTO.setCalidades(Calidades.DENUNCIANTE);
			if (!forma.getEsPersonaMoral()) {
				logger.info("ingresaremos un Denunciante fisica");
				involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
				involucradoDTO.setEsVivo(forma.getEsVivo());
			}
		} else if (forma.getCalidadDelIndividuo().equals(CALIDAD_TESTIGO)) {
			logger.info("ingresaremos un testigo");
			involucradoDTO.setEsProtegido(BooleanUtils.isTrue(forma.getEsProtegido()));
			involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
			calidadDTO.setCalidades(Calidades.TESTIGO);
			involucradoDTO.setEsVivo(forma.getEsVivo());
				logger.info("ingresaremos un testigo fisica");
				involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
			
		} else if (forma.getCalidadDelIndividuo().equals(
				CALIDAD_REPRESENTANTE_LEGAL)) {
			logger.info("ingresaremos un representante legal");
			calidadDTO.setCalidades(Calidades.REPRESENTANTE_LEGAL);
			if (!forma.getEsPersonaMoral()) {
				logger.info("ingresaremos un testigo fisica");
				involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
				involucradoDTO.setEsVivo(forma.getEsVivo());
			}
		} else if (forma.getCalidadDelIndividuo().equals(CALIDAD_TRADUCTOR)) {
			logger.info("ingresaremos un traductor");
			calidadDTO.setCalidades(Calidades.TRADUCTOR);
			involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
			involucradoDTO.setEsVivo(forma.getEsVivo());
			
		} else if(forma.getCalidadDelIndividuo().equals(CALIDAD_QUIEN_DETUVO)){
			logger.info("ingresaremos un traductor");
			calidadDTO.setCalidades(Calidades.QUIEN_DETUVO);
			involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
			
			List<Long> lstDetenidos = new ArrayList<Long>();
			String[] idsDetenidos = forma.getStrDetenidos().split(",");
			for (int i = 0; i < idsDetenidos.length; i++) {
				Long detenido = Long.parseLong(idsDetenidos[i]);
				lstDetenidos.add(detenido);
			}
			involucradoDTO.setIdsDetenidos(lstDetenidos);
			involucradoDTO.setEsVivo(forma.getEsVivo());

		}else if(forma.getCalidadDelIndividuo().equals(CALIDAD_DEFENSOR)){
			logger.info("ingresaremos un defensor");
			calidadDTO.setCalidades(Calidades.DEFENSOR_PUBLICO);
			involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
			involucradoDTO.setEsVivo(forma.getEsVivo());
			//Linea para agregar el id del defensor
			//idPropParaDefensor
		}else if(forma.getCalidadDelIndividuo().equals(CALIDAD_DEFENSOR_PRIVADO)){
			logger.info("ingresaremos un defensor");
			calidadDTO.setCalidades(Calidades.DEFENSOR_PRIVADO);
			involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
			involucradoDTO.setEsVivo(forma.getEsVivo());
		}else if(forma.getCalidadDelIndividuo().equals(CALIDAD_SOLICITANTE)){
			logger.info("INGRESAREMOS UN SOLICITANTE CIUDADANO");
			calidadDTO.setCalidades(Calidades.SOLICITANTE);
			involucradoDTO.setTipoPersona(TIPO_PERSONA_FISICA);
			involucradoDTO.setEsVivo(forma.getEsVivo());
		}
		
		involucradoDTO.setCalidadDTO(calidadDTO);
		
		
		List<NombreDemograficoDTO> lstDatosGenerales = new ArrayList<NombreDemograficoDTO>();
		NombreDemograficoDTO datosGenerales = new NombreDemograficoDTO();
		datosGenerales.setNombre(forma.getNombre());
		datosGenerales.setApellidoPaterno(forma.getApellidoPaterno());
		datosGenerales.setApellidoMaterno(forma.getApellidoMaterno());
		retorno.setNombre(forma.getNombre()+" "+forma.getApellidoPaterno()+" "+forma.getApellidoMaterno());
		
		//TODO JORGE Se debe de persistir desde la primera vez. Revisar impacto en Vista.
		if(forma.getNombre()==null){
			retorno.setNombre("An&oacute;nimo");
		}else if(forma.getNombre().equals("")){
			retorno.setNombre("An&oacute;nimo");
		}
		if (forma.getCalidadDelIndividuo().equals(CALIDAD_VICTIMA_PERSONA)|| forma.getCalidadDelIndividuo().equals(CALIDAD_PROBABLE_RESPONSABLE)){
			if(forma.getNombre()==null){
				retorno.setNombre("Desconocido");
			}else if(forma.getNombre().equals("")){
				retorno.setNombre("Desconocido");
			}
		}
		datosGenerales.setCurp(forma.getCurp());
		datosGenerales.setRfc(forma.getRfc());
		datosGenerales.setSexo(forma.getSexo());
		if (forma.getFechaNacimiento() != null && !forma.getFechaNacimiento().equals("")) {
			datosGenerales.setFechaNacimiento(DateUtils.obtener(forma
					.getFechaNacimiento()));
		}
		datosGenerales.setEdadAproximada(forma.getEdadAproximada());
		
		//seteamos el lugar de nacimiento
		if (!(forma.getEsPersonaMoral() !=null && forma.getEsPersonaMoral()))
		{
			if(forma.getPaisNacimiento()!=null && !forma.getPaisNacimiento().equals("-1"))
			{
				datosGenerales.setPaisValorDTO(new ValorDTO(Long.parseLong(forma.getPaisNacimiento())));
				logger.info("ID_PAIS_NAC::"+forma.getPaisNacimiento());
				if(forma.getEntFederativaNacimiento()!=null && !forma.getEntFederativaNacimiento().equals("-1"))
				{
					datosGenerales.setEntidadFederativaDTO(new EntidadFederativaDTO(Long.parseLong(forma.getEntFederativaNacimiento())));
					logger.info("ID_ENTFED_NAC::"+forma.getEntFederativaNacimiento());
					if(forma.getMunicipioNacimiento()!=null && !forma.getMunicipioNacimiento().equals("-1"))
					{
						datosGenerales.setMunicipioDTO(new MunicipioDTO(Long.parseLong(forma.getMunicipioNacimiento())));
						logger.info("ID_MUN_NAC::"+forma.getMunicipioNacimiento());
					}
					else
					{
						datosGenerales.setMunicipioDTO(null);
					}
				}
				else
				{
					datosGenerales.setEntidadFederativaDTO(null);
				}
			}
			else
			{
				datosGenerales.setPaisValorDTO(null);
			}
		}
		
		//datosGenerales.setPaisValorDTO(new ValorDTO());

		datosGenerales.setEdoFisico(forma.getEdoFisico() > 0L ? new ValorDTO(forma.getEdoFisico()) : null);
		datosGenerales.setEdoConsciencia(forma.getEdoConsciencia() > 0L ? new ValorDTO(forma.getEdoConsciencia()) : null);
		datosGenerales.setEdoConscienciaInconsciente(forma.getEdoConscienciaInconsciente() > 0L ? new ValorDTO(forma.getEdoConscienciaInconsciente()) : null);
		
		lstDatosGenerales.add(datosGenerales);
		if (forma.getFechaIngreso() != null && !forma.getFechaIngreso().isEmpty()){
			involucradoDTO.setFechaCreacionElemento(DateUtils.obtener(forma.getFechaIngreso()));				
		}else{
			involucradoDTO.setFechaCreacionElemento(new Date());
		}

		ValorDTO valorGenerico = new ValorDTO();
		//valorGenerico.setIdCampo(IDCAMPO_TIPOORGANIZACION);
		//valorGenerico.setValor(forma.getIdioma());
		// log.info("+++++++++++++++++++++++++Idioma forma:" +
		// forma.getIdioma());
		// valorGenerico.setIdCampo(idCampo)
		// involucradoDTO.setValorIdIdioma(valorGenerico);

		valorGenerico = new ValorDTO();
//			Long religion=Long.parseLong(forma.getReligion());
//			valorGenerico.setIdCampo(religion);
		// log.info("+++++++++++++++++++++++++getReligion forma:" +
		// forma.getReligion());
//			involucradoDTO.setValorIdReligion(valorGenerico);

		valorGenerico = new ValorDTO();
		Long escolaridad=0L;
		if(forma.getEscolaridad()!=null && ! forma.getEscolaridad().equals("- Selecciona -")){
			escolaridad=Long.parseLong(forma.getEscolaridad());
		}else{
			escolaridad=null;
		}
		
		valorGenerico.setIdCampo(escolaridad);
		// log.info("+++++++++++++++++++++++++getEscolaridad forma:" +
		// forma.getEscolaridad());
		involucradoDTO.setValorIdEscolaridad(valorGenerico);

		valorGenerico = new ValorDTO();
		Long estadoCivil=0L;
		if(forma.getEstadoCivil()!=null && ! forma.getEstadoCivil().equals("- Selecciona -")){
			 estadoCivil=Long.parseLong(forma.getEstadoCivil());
		}else{
			estadoCivil=null;
		}
		
		valorGenerico.setIdCampo(estadoCivil);
		// log.info("+++++++++++++++++++++++++getEstadoCivil forma:" +
		// forma.getEstadoCivil());
		involucradoDTO.setValorIdEstadoCivil(valorGenerico);
		
		valorGenerico = new ValorDTO();
		Long idioma= 0L;
		if(forma.getIdioma()!=null && ! forma.getIdioma().equals("- Selecciona -")){
			idioma=Long.parseLong(forma.getIdioma());
		}else{
			idioma=null;
		}
		valorGenerico.setIdCampo(idioma);
		involucradoDTO.setValorIdIdioma(valorGenerico);
		
		List<ValorDTO> listaValor=new ArrayList<ValorDTO>();
		String[] idsOcupaciones = forma.getOcupacion().split(",");
		//FIXME comentado para ver si corre flujo
		if(idsOcupaciones!=null && idsOcupaciones[0]!=""){
			for (int i = 0; i < idsOcupaciones.length; i++) {
				valorGenerico = new ValorDTO();
				Long ocupacion=Long.parseLong(idsOcupaciones[i]);
				logger.info("ocupaciones "+ ocupacion);
				valorGenerico.setIdCampo(ocupacion);
				listaValor.add(valorGenerico);
			}
		}
		involucradoDTO.setValorIdOcupacion(listaValor);
		
		listaValor=new ArrayList<ValorDTO>();
		String[] idsNacionalidades = forma.getNacionalidad().split(",");
		if(idsNacionalidades!=null && idsNacionalidades[0]!=""){
			for (int i = 0; i < idsNacionalidades.length; i++) {
				valorGenerico = new ValorDTO();
				Long nacionalidad=Long.parseLong(idsNacionalidades[i]);
				valorGenerico.setIdCampo(nacionalidad);
				listaValor.add(valorGenerico);
			}
		}
		involucradoDTO.setValorIdNacionalidad(listaValor);

		// TODO: Falta a de alias hasta nacionalidad
		involucradoDTO.setNombresDemograficoDTO(lstDatosGenerales);

		DomicilioDTO domicilio = new DomicilioDTO();
		DomicilioExtranjeroDTO domExtranjero = new DomicilioExtranjeroDTO();
		EntidadFederativaDTO estado = new EntidadFederativaDTO();
		AsentamientoDTO asentamientoDTO = new AsentamientoDTO();
		CiudadDTO ciudad = new CiudadDTO();

		if (!forma.getPais().equals("") && !forma.getPais().equals("-1")){
			logger.info("/**** Pais :: "+forma.getPais());
			calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.DOMICILIO);				
			
			if (forma.getPais().equals("10")) {
				
				valorGenerico = new ValorDTO();
				valorGenerico.setValor(forma.getPais());
				estado.setValorIdPais(valorGenerico);
				
				if (!forma.getEntidadFederativa().equals("")
						&& !forma.getEntidadFederativa().equals("-1")) {
					estado.setEntidadFederativaId(new Long(forma.getEntidadFederativa()));
					domicilio.setEntidadDTO(estado);
				}
				if (!forma.getDelegacionMunicipio().equals("")
						&& !forma.getDelegacionMunicipio().equals("-1")) {
					MunicipioDTO municipio = new MunicipioDTO(new Long(forma.getDelegacionMunicipio()), "", estado);
					asentamientoDTO.setMunicipioDTO(municipio);
					domicilio.setMunicipioDTO(municipio);
				}										
				if (!forma.getCiudad().equals("")
						&& !forma.getCiudad().equals("-1")) {
					ciudad.setCiudadId(new Long(forma.getCiudad()));
					domicilio.setCiudadDTO(ciudad);
					asentamientoDTO.setCiudadDTO(ciudad);
				}					
				
				if (!forma.getAsentamientoColonia().equals("")
						&& !forma.getAsentamientoColonia().equals("-1")) {
					asentamientoDTO.setAsentamientoId(new Long(forma.getAsentamientoColonia()));
				}
				if (!forma.getTipoAsentamiento().equals("")
						&& !forma.getTipoAsentamiento().equals("-1")) {
					TipoAsentamientoDTO tipoAsentamientoDTO=new TipoAsentamientoDTO(Long.parseLong(forma.getTipoAsentamiento()), "");
					asentamientoDTO.setTipoAsentamientoDTO(tipoAsentamientoDTO);
				}
				if (!(forma.getLatitudN()== null) && !forma.getLatitudN().equals("")) {
					String lat= forma.getLatitudN()+forma.getLatitudGrados()+"°"+forma.getLatitudMinutos()+"'"+forma.getLatitudSegundos()+"\"";
					domicilio.setLatitud(lat);
				}
				if (!(forma.getLongitudE()== null) && !forma.getLongitudE().equals("")) {
					String longitud= forma.getLongitudE()+forma.getLongitudGrados()+"°"+forma.getLongitudMinutos()+"'"+forma.getLongitudSegundos()+"\"";
					domicilio.setLongitud(longitud);
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
				domicilio.setFechaCreacionElemento(DateUtils.obtener(forma.getFechaIngreso()));
				if (!forma.getTipoCalle().equals("")
						&& !forma.getTipoCalle().equals("-1")) {
					domicilio.setValorCalleId(new ValorDTO(new Long(forma.getTipoCalle())));// Tipo de calle
				}
				
				domicilio.setCalidadDTO(calidadDTO);
				domicilio.setExpedienteDTO(expedienteDTO);
				involucradoDTO.setDomicilio(domicilio);
			} else {
				
				domExtranjero.setPaisValor(new ValorDTO(new Long(forma.getPais()), forma.getPais()));
				domExtranjero.setEstado(forma.getEntidadFederativa());
				domExtranjero.setCiudad(forma.getCiudad());
				domExtranjero.setMunicipio(forma.getDelegacionMunicipio());
				domExtranjero.setCodigoPostal(forma.getCodigoPostal());
				domExtranjero.setAsentamientoExt(forma.getAsentamientoColonia());
				
				if (!(forma.getLatitudN()== null) && !forma.getLatitudN().equals("")) {
					String lat= forma.getLatitudN()+forma.getLatitudGrados()+"°"+forma.getLatitudMinutos()+"'"+forma.getLatitudSegundos()+"\"";
					domExtranjero.setLatitud(lat);
				}
				if (!(forma.getLongitudE()== null) && !forma.getLongitudE().equals("")) {
					String longitud= forma.getLongitudE()+forma.getLongitudGrados()+"°"+forma.getLongitudMinutos()+"'"+forma.getLongitudSegundos()+"\"";
					domExtranjero.setLongitud(longitud);
				}
				
				domExtranjero.setCalle(forma.getCalle());
				domExtranjero.setNumeroExterior(forma.getNumExterior());
				domExtranjero.setNumeroInterior(forma.getNumInterior());
				domExtranjero.setEntreCalle1(forma.getEntreCalle());
				domExtranjero.setEntreCalle2(forma.getYcalle());
				domExtranjero.setAlias(forma.getAliasDomicilio());
				domExtranjero.setEdificio(forma.getEdificio());
				domExtranjero.setReferencias(forma.getReferencias());
				domExtranjero.setFechaCreacionElemento(DateUtils.obtener(forma.getFechaIngreso()));
//					if (!forma.getTipoCalle().equals("")
//							&& !forma.getTipoCalle().equals("-1")) {
//						domicilio.setValorCalleId(new ValorDTO(new Long(forma.getTipoCalle())));// Tipo de calle
//					}
				
				domExtranjero.setCalidadDTO(calidadDTO);
				domExtranjero.setExpedienteDTO(expedienteDTO);
				involucradoDTO.setDomicilio(domExtranjero);
			}
		}									

//			if (!forma.getPais().equals("") && !forma.getPais().equals("-1")) {
//				valorGenerico = new ValorDTO();
//				valorGenerico.setValor(forma.getPais());
//				estado.setValorIdPais(valorGenerico);
//			}			
		
		// TODO:Coordenadas geograficas
//			involucradoDTO.setDomicilio(domicilio);

		DomicilioDTO domicilioNotif = new DomicilioDTO();
		asentamientoDTO = new AsentamientoDTO();

		if(esMismoDomicilio != null && esMismoDomicilio.equals("1")){
			involucradoDTO.setDomicilioNotificacion(crearDomicilioOriginal(forma, expedienteDTO));
		}else{
			if (!forma.getPaisNotif().equals("") && !forma.getPaisNotif().equals("-1")) {
				logger.info("/**** Pais NOT :: "+forma.getPaisNotif());
				calidadDTO = new CalidadDTO();
				calidadDTO.setCalidades(Calidades.DOMICILIO);
				
				if (forma.getPaisNotif().equals("10")) {
					logger.info("/**** Domicilio NOT Normal");
					
					valorGenerico = new ValorDTO();
					valorGenerico.setValor(forma.getPaisNotif());
					estado.setValorIdPais(valorGenerico);
					
					estado = new EntidadFederativaDTO();
					if (!forma.getEntidadFederativaNotif().equals("")
							&& !forma.getEntidadFederativaNotif().equals("-1")) {
						estado.setEntidadFederativaId(new Long(forma.getEntidadFederativaNotif()));
						domicilioNotif.setEntidadDTO(estado);
						
					}
					if (!forma.getDelegacionMunicipioNotif().equals("")
							&& !forma.getAsentamientoColoniaNotif().equals("-1")) {
						MunicipioDTO municipio = new MunicipioDTO(new Long(
								forma.getDelegacionMunicipioNotif()), "", estado);
						asentamientoDTO.setMunicipioDTO(municipio);
						domicilioNotif.setMunicipioDTO(municipio);
					}
					
					ciudad = new CiudadDTO();
					if (!forma.getCiudadNotif().equals("")
							&& !forma.getCiudadNotif().equals("-1")) {
						ciudad.setCiudadId(new Long(forma.getCiudadNotif()));
						domicilioNotif.setCiudadDTO(ciudad);				
					}
					asentamientoDTO.setCiudadDTO(ciudad);
					
					if (!forma.getAsentamientoColoniaNotif().equals("")
							&& !forma.getAsentamientoColoniaNotif().equals("-1")) {
						asentamientoDTO.setAsentamientoId(new Long(forma
								.getAsentamientoColoniaNotif()));
					}
					
					/**** Latitud de Notificaciones*****/
					if (!(forma.getLatitudNNotif()== null) && !forma.getLatitudNNotif().equals("")) {
						String latNotif= forma.getLatitudNNotif()+forma.getLatitudGradosNotif()+"°"+forma.getLatitudMinutosNotif()+"'"+forma.getLatitudSegundosNotif()+"\"";
						domicilioNotif.setLatitud(latNotif);
						logger.info("LATITUD:: "+latNotif);
					}
					if (!(forma.getLongitudENotif()== null) && !forma.getLongitudENotif().equals("")) {
						String longitudNotif= forma.getLongitudENotif()+forma.getLongitudGradosNotif()+"°"+forma.getLongitudMinutosNotif()+"'"+forma.getLongitudSegundosNotif()+"\"";
						domicilioNotif.setLongitud(longitudNotif);
						logger.info("LONGITUD:: "+longitudNotif);
					}
					/**** FIN Latitud de Notificaciones*****/
					domicilioNotif.setAsentamientoDTO(asentamientoDTO);
					domicilioNotif.setCalle(forma.getCalleNotif());
					domicilioNotif.setNumeroExterior(forma.getNumExteriorNotif());
					domicilioNotif.setNumeroInterior(forma.getNumInteriorNotif());
					domicilioNotif.setEntreCalle1(forma.getEntreCalleNotif());
					domicilioNotif.setEntreCalle2(forma.getYcalleNotif());
					domicilioNotif.setAlias(forma.getAliasDomicilioNotif());
					domicilioNotif.setEdificio(forma.getEdificioNotif());
					domicilioNotif.setReferencias(forma.getReferenciasNotif());
					domicilioNotif.setFechaCreacionElemento(DateUtils.obtener(forma.getFechaIngreso()));
					
					if (!forma.getTipoCalleNotif().equals("")
							&& !forma.getTipoCalleNotif().equals("-1")) {
						domicilioNotif.setValorCalleId(new ValorDTO(new Long(forma.getTipoCalleNotif())));// Tipo de calle
					}
					
					domicilioNotif.setCalidadDTO(calidadDTO);
					domicilioNotif.setExpedienteDTO(expedienteDTO);
					involucradoDTO.setDomicilioNotificacion(domicilioNotif);
			
		}
			} else {
				DomicilioExtranjeroDTO domExtranjeroNot = new DomicilioExtranjeroDTO();
				
				domExtranjeroNot.setPaisValor(new ValorDTO(new Long(forma.getPais()), forma.getPaisNotif()));			
				domExtranjeroNot.setEstado(forma.getEntidadFederativaNotif());					
				domExtranjeroNot.setCiudad(forma.getCiudadNotif());					
				domExtranjeroNot.setMunicipio(forma.getDelegacionMunicipioNotif());					
				domExtranjeroNot.setCodigoPostal(forma.getCodigoPostalNotif());					
				domExtranjeroNot.setAsentamientoExt(forma.getAsentamientoColoniaNotif());
				
				if (!(forma.getLatitudNNotif()== null) && !forma.getLatitudNNotif().equals("")) {
					String latNotif= forma.getLatitudNNotif()+forma.getLatitudGradosNotif()+"°"+forma.getLatitudMinutosNotif()+"'"+forma.getLatitudSegundosNotif()+"\"";
					domExtranjeroNot.setLatitud(latNotif);						
				}
				if (!(forma.getLongitudENotif()== null) && !forma.getLongitudENotif().equals("")) {
					String longitudNotif= forma.getLongitudENotif()+forma.getLongitudGradosNotif()+"°"+forma.getLongitudMinutosNotif()+"'"+forma.getLongitudSegundosNotif()+"\"";
					domExtranjeroNot.setLongitud(longitudNotif);						
				}
				
				domExtranjeroNot.setCalle(forma.getCalleNotif());
				domExtranjeroNot.setNumeroExterior(forma.getNumExteriorNotif());
				domExtranjeroNot.setNumeroInterior(forma.getNumInteriorNotif());
				domExtranjeroNot.setEntreCalle1(forma.getEntreCalleNotif());
				domExtranjeroNot.setEntreCalle2(forma.getYcalleNotif());
				domExtranjeroNot.setAlias(forma.getAliasDomicilioNotif());
				domExtranjeroNot.setEdificio(forma.getEdificioNotif());
				domExtranjeroNot.setReferencias(forma.getReferenciasNotif());
				domExtranjeroNot.setFechaCreacionElemento(DateUtils.obtener(forma.getFechaIngreso()));
//					if (!forma.getTipoCalle().equals("")
//							&& !forma.getTipoCalle().equals("-1")) {
//						domicilio.setValorCalleId(new ValorDTO(new Long(forma.getTipoCalle())));// Tipo de calle
//					}
				
				domExtranjeroNot.setCalidadDTO(calidadDTO);
				domExtranjeroNot.setExpedienteDTO(expedienteDTO);
				involucradoDTO.setDomicilioNotificacion(domExtranjeroNot);
			}				
		}

//			if (!forma.getPais().equals("") && !forma.getPais().equals("-1")) {
//				valorGenerico = new ValorDTO();
//				valorGenerico.setValor(forma.getPaisNotif());
//				estado.setValorIdPais(valorGenerico);
//			}

		String strTelefonos = forma.getMedioContactoTelefono();
		if(strTelefonos!=null && !strTelefonos.trim().isEmpty()){
			List<TelefonoDTO> lstTelefonos = new ArrayList<TelefonoDTO>();
			StringTokenizer lstStrTelefonos = new StringTokenizer(strTelefonos,"|");
			while (lstStrTelefonos.hasMoreElements()) {
				String strTelefono = (String) lstStrTelefonos.nextElement();
				String[] datosTelefono = strTelefono.split(",");
	
				if(datosTelefono.length!=0){
					TelefonoDTO telefono = new TelefonoDTO();
					ValorDTO valorTipoTelefono = new ValorDTO();
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
			involucradoDTO.setTelefonosDTO(lstTelefonos);
		}
		
		List<CorreoElectronicoDTO> lstCorreos = new ArrayList<CorreoElectronicoDTO>();
		if(!forma.getMedioContactoCorreo().trim().isEmpty()){
			String[] datosCorreo = forma.getMedioContactoCorreo().split(",");
			for (int i = 0; i < datosCorreo.length; i++) {
				CorreoElectronicoDTO correo = new CorreoElectronicoDTO();
				correo.setDireccionElectronica(datosCorreo[i]);
				lstCorreos.add(correo);
			}
		}
		involucradoDTO.setCorreosDTO(lstCorreos);
		logger.info("Ingresasr individuo antes del null pointer:");
		if (!(forma.getDocIdentificacion() == null)) {
			// valorIdIdentificacion.setValor(forma.getDocIdentificacion());
			if (!forma.getDocIdentificacion().equals("")&& !forma.getDocIdentificacion().equals("-1")) {
				ValorDTO valorIdIdentificacion = new ValorDTO();
				valorIdIdentificacion.setIdCampo(new Long(forma.getDocIdentificacion()));
				involucradoDTO.setValorIdIdentificaion(valorIdIdentificacion);
				involucradoDTO.setFolioIdentificacion(forma.getFolioDoc());
			}
		}
		if(forma.getAnular()!=null){
			involucradoDTO.setEsActivo(!forma.getAnular());
		}
		involucradoDTO.setAliasInvolucradoDTO(obtenerAliasInvolucrado(forma));
//			involucradoDTO.setFolioIdentificacion(forma.getFolioDoc());
//			ValorDTO valorTipoDoc = new ValorDTO();
//			Long doc=0L;
//			if(forma.getDocIdentificacion()!=null && ! forma.getDocIdentificacion().equals("- Selecciona -")){
//				doc=Long.parseLong(forma.getDocIdentificacion());
//			}else{
//				doc=null;
//			}
//			valorTipoDoc.setIdCampo(doc);
//			involucradoDTO.setValorIdIdentificaion(valorTipoDoc);
		
		return involucradoDTO;
	}

	/**
	 * Obtiene los datos de la Media Filiacion de la Forma
	 * del involucrado.
	 * Se hace validaciones de los combobox considerando 
	 * que el "Seleccione" tiene el valor de -1
	 * 
	 * @param forma
	 * @return MediaFiliacionDTO con los datos extraidos de la forma.
	 */
	private static MediaFiliacionDTO obtenerMediaFiliacion(IngresarIndividuoForm forma){
		//Datos de Media Filiacion Cara
		MediaFiliacionDTO mediaFiliacionDTO = new MediaFiliacionDTO();
		ValorDTO valorGenericoMF = new ValorDTO();
		Long tamanoBoca = 0L;
		if(forma.getTamanoBoca() != null &&  ! forma.getTamanoBoca() .equals("-1")){
			tamanoBoca=Long.parseLong(forma.getTamanoBoca());
		}else{
			tamanoBoca=null;
		}
		valorGenericoMF.setIdCampo(tamanoBoca);
		mediaFiliacionDTO.setTamanioBoca(valorGenericoMF);
		
		valorGenericoMF = new ValorDTO();
		Long tipoCara = 0L;
		if(forma.getTipoCara() != null &&  ! forma.getTipoCara() .equals("-1")){
			tipoCara=Long.parseLong(forma.getTipoCara());
		}else{
			tipoCara=null;
		}
		valorGenericoMF.setIdCampo(tipoCara);
		mediaFiliacionDTO.setTipoCara(valorGenericoMF);
		
		valorGenericoMF = new ValorDTO();
		Long formaMenton = 0L;
		if(forma.getFormaMenton() != null &&  ! forma.getFormaMenton() .equals("-1")){
			formaMenton=Long.parseLong(forma.getFormaMenton());
		}else{
			formaMenton=null;
		}
		valorGenericoMF.setIdCampo(formaMenton);
		mediaFiliacionDTO.setFormaMenton(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long tipoMenton = 0L;
		if(forma.getTipoMenton() != null &&  ! forma.getTipoMenton() .equals("-1")){
			tipoMenton=Long.parseLong(forma.getTipoMenton());
		}else{
			tipoMenton=null;
		}
		valorGenericoMF.setIdCampo(tipoMenton);
		mediaFiliacionDTO.setTipoMenton(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long tez = 0L;
		if(forma.getTez() != null &&  ! forma.getTez() .equals("-1")){
			tez=Long.parseLong(forma.getTez());
		}else{
			tez=null;
		}
		valorGenericoMF.setIdCampo(tez);
		mediaFiliacionDTO.setTez(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long inclinacionMenton = 0L;
		if(forma.getInclinacionMenton() != null &&  ! forma.getInclinacionMenton() .equals("-1")){
			inclinacionMenton=Long.parseLong(forma.getInclinacionMenton());
		}else{
			inclinacionMenton=null;
		}
		valorGenericoMF.setIdCampo(inclinacionMenton);
		mediaFiliacionDTO.setInclinacionMenton(valorGenericoMF);

		//Datos de Media Filiacion Cabello
		valorGenericoMF = new ValorDTO();
		Long colorCabello = 0L;
		if(forma.getColorCabello() != null &&  ! forma.getColorCabello() .equals("-1")){
			colorCabello=Long.parseLong(forma.getColorCabello());
		}else{
			colorCabello=null;
		}
		valorGenericoMF.setIdCampo(colorCabello);
		mediaFiliacionDTO.setColorCabello(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long formaCabello = 0L;
		if(forma.getFormaCabello() != null &&  ! forma.getFormaCabello() .equals("-1")){
			formaCabello=Long.parseLong(forma.getFormaCabello());
		}else{
			formaCabello=null;
		}
		valorGenericoMF.setIdCampo(formaCabello);
		mediaFiliacionDTO.setFormaCabello(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long calvieTipo = 0L;
		if(forma.getCalvieTipo() != null &&  ! forma.getCalvieTipo() .equals("-1")){
			calvieTipo=Long.parseLong(forma.getCalvieTipo());
		}else{
			calvieTipo=null;
		}
		valorGenericoMF.setIdCampo(calvieTipo);
		mediaFiliacionDTO.setCalvicieTipo(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long cabelloImplantacion = 0L;
		if(forma.getCabelloImplantacion() != null &&  ! forma.getCabelloImplantacion() .equals("-1")){
			cabelloImplantacion=Long.parseLong(forma.getCabelloImplantacion());
		}else{
			cabelloImplantacion=null;
		}
		valorGenericoMF.setIdCampo(cabelloImplantacion);
		mediaFiliacionDTO.setCabelloImplantacion(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long cantidadCabello = 0L;
		if(forma.getCantidadCabello() != null &&  ! forma.getCantidadCabello() .equals("-1")){
			cantidadCabello=Long.parseLong(forma.getCantidadCabello());
		}else{
			cantidadCabello=null;
		}
		valorGenericoMF.setIdCampo(cantidadCabello);
		mediaFiliacionDTO.setCabelloCantidad(valorGenericoMF);

		//Datos de Media Filiacion Oreja
		valorGenericoMF = new ValorDTO();
		Long tamanoOreja = 0L;
		if(forma.getTamanoOreja() != null &&  ! forma.getTamanoOreja() .equals("-1")){
			tamanoOreja=Long.parseLong(forma.getTamanoOreja());
		}else{
			tamanoOreja=null;
		}
		valorGenericoMF.setIdCampo(tamanoOreja);
		mediaFiliacionDTO.setOrejaTamanio(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long lobuloParticularidad = 0L;
		if(forma.getLobuloParticularidad() != null &&  ! forma.getLobuloParticularidad() .equals("-1")){
			lobuloParticularidad=Long.parseLong(forma.getLobuloParticularidad());
		}else{
			lobuloParticularidad=null;
		}
		valorGenericoMF.setIdCampo(lobuloParticularidad);
		mediaFiliacionDTO.setOrejaLobParticularidad(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long lobuloDimension = 0L;
		if(forma.getLobuloDimension() != null &&  ! forma.getLobuloDimension() .equals("-1")){
			lobuloDimension=Long.parseLong(forma.getLobuloDimension());
		}else{
			lobuloDimension=null;
		}
		valorGenericoMF.setIdCampo(lobuloDimension);
		mediaFiliacionDTO.setOrejaLobDimension(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long lobuloAdherencia = 0L;
		if(forma.getLobuloAdherencia() != null &&  ! forma.getLobuloAdherencia() .equals("-1")){
			lobuloAdherencia=Long.parseLong(forma.getLobuloAdherencia());
		}else{
			lobuloAdherencia=null;
		}
		valorGenericoMF.setIdCampo(lobuloAdherencia);
		mediaFiliacionDTO.setOrejaLobAdherencia(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long helixAnterior = 0L;
		if(forma.getHelixAnterior() != null &&  ! forma.getHelixAnterior() .equals("-1")){
			helixAnterior=Long.parseLong(forma.getHelixAnterior());
		}else{
			helixAnterior=null;
		}
		valorGenericoMF.setIdCampo(helixAnterior);
		mediaFiliacionDTO.setHelixSuperior(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long helixPosterior = 0L;
		if(forma.getHelixPosterior() != null &&  ! forma.getHelixPosterior() .equals("-1")){
			helixPosterior=Long.parseLong(forma.getHelixPosterior());
		}else{
			helixPosterior=null;
		}
		valorGenericoMF.setIdCampo(helixPosterior);
		mediaFiliacionDTO.setHelixPosterior(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long helixContorno = 0L;
		if(forma.getHelixContorno() != null &&  ! forma.getHelixContorno() .equals("-1")){
			helixContorno=Long.parseLong(forma.getHelixContorno());
		}else{
			helixContorno=null;
		}
		valorGenericoMF.setIdCampo(helixContorno);
		mediaFiliacionDTO.setHelixContorno(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long helixAdherencia = 0L;
		if(forma.getHelixAdherencia() != null &&  ! forma.getHelixAdherencia() .equals("-1")){
			helixAdherencia=Long.parseLong(forma.getHelixAdherencia());
		}else{
			helixAdherencia=null;
		}
		valorGenericoMF.setIdCampo(helixAdherencia);
		mediaFiliacionDTO.setHelixAdherencia(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long formaOreja = 0L;
		if(forma.getFormaOreja() != null &&  ! forma.getFormaOreja() .equals("-1")){
			formaOreja=Long.parseLong(forma.getFormaOreja());
		}else{
			formaOreja=null;
		}
		valorGenericoMF.setIdCampo(formaOreja);
		mediaFiliacionDTO.setFormaOreja(valorGenericoMF);

		//Datos de Media Filiacion Ojos
		valorGenericoMF = new ValorDTO();
		Long formaOjos = 0L;
		if(forma.getFormaOjos() != null &&  ! forma.getFormaOjos() .equals("-1")){
			formaOjos=Long.parseLong(forma.getFormaOjos());
		}else{
			formaOjos=null;
		}
		valorGenericoMF.setIdCampo(formaOjos);
		mediaFiliacionDTO.setFormaOjos(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long colorOjos = 0L;
		if(forma.getColorOjos() != null &&  ! forma.getColorOjos() .equals("-1")){
			colorOjos=Long.parseLong(forma.getColorOjos());
		}else{
			colorOjos=null;
		}
		valorGenericoMF.setIdCampo(colorOjos);
		mediaFiliacionDTO.setColorOjos(valorGenericoMF);

		valorGenericoMF = new ValorDTO();
		Long tamanoOjos = 0L;
		if(forma.getTamanoOjos() != null &&  ! forma.getTamanoOjos() .equals("-1")){
			tamanoOjos=Long.parseLong(forma.getTamanoOjos());
		}else{
			tamanoOjos=null;
		}
		valorGenericoMF.setIdCampo(tamanoOjos);
		mediaFiliacionDTO.setTamanioOjo(valorGenericoMF);
		
		
		//cejas
		valorGenericoMF = new ValorDTO();
		Long implantacionCejas = 0L;
		if(forma.getImplantacionCejas() != null &&  ! forma.getImplantacionCejas() .equals("-1")){
			implantacionCejas=Long.parseLong(forma.getImplantacionCejas());
		}else{
			implantacionCejas=null;
		}
		valorGenericoMF.setIdCampo(implantacionCejas);
		mediaFiliacionDTO.setImplantacionCeja(valorGenericoMF);
		
		valorGenericoMF = new ValorDTO();
		Long formaCejas = 0L;
		if(forma.getFormaCejas() != null &&  ! forma.getFormaCejas() .equals("-1")){
			formaCejas=Long.parseLong(forma.getFormaCejas());
		}else{
			formaCejas=null;
		}
		valorGenericoMF.setIdCampo(formaCejas);
		mediaFiliacionDTO.setFormaCeja(valorGenericoMF);
		
		valorGenericoMF = new ValorDTO();
		Long tamanoCejas = 0L;
		if(forma.getTamanoCejas() != null &&  ! forma.getTamanoCejas() .equals("-1")){
			tamanoCejas=Long.parseLong(forma.getTamanoCejas());
		}else{
			tamanoCejas=null;
		}
		valorGenericoMF.setIdCampo(tamanoCejas);
		mediaFiliacionDTO.setTamanioCeja(valorGenericoMF);
		
		SeniaParticularDTO seniaParticularDTO=new SeniaParticularDTO();
		if(forma.getCicatricesSenas() !=null && forma.getCicatricesSenas().equals("1")){
			seniaParticularDTO.setTieneCicatrices(true);
		}else{
			seniaParticularDTO.setTieneCicatrices(false);
		}
		seniaParticularDTO.setDescripcionCicatrices(forma.getCicatricesSenasText());
		
		if(forma.getProtesisSenas() !=null && forma.getProtesisSenas().equals("1")){
			seniaParticularDTO.setTieneProtesis(true);
		}else{
			seniaParticularDTO.setTieneProtesis(false);
		}
		seniaParticularDTO.setDescripcionProtesis(forma.getProtesisSenasText());
		
		if(forma.getTatuajeSenas()!=null && forma.getTatuajeSenas().equals("1")){
			seniaParticularDTO.setTieneTatuajes(true);
		}else{
			seniaParticularDTO.setTieneTatuajes(false);
		}
		seniaParticularDTO.setDescripcionTatuajes(forma.getTatuajeSenasText());
		seniaParticularDTO.setDescripcionOtraSenia(forma.getOtraSenasText());
		logger.info("Lunares:::::::::::::::::"+forma.getLunaresSenas());
		if(forma.getLunaresSenas()!=null && forma.getLunaresSenas().equals("1")){
			seniaParticularDTO.setTieneLunares(true);
		}else{
			seniaParticularDTO.setTieneLunares(false);
		}
		seniaParticularDTO.setDescripcionLunares(forma.getLunaresSenasText());
		if(forma.getDefectosSenas()!=null && forma.getDefectosSenas().equals("1")){
			seniaParticularDTO.setTieneDefectosFisicos(true);
		}else{
			seniaParticularDTO.setTieneDefectosFisicos(false);
		}
		seniaParticularDTO.setDescripcionDefectosFisicos(forma.getDefectosSenasText());
		mediaFiliacionDTO.setSeniaParticularDTO(seniaParticularDTO);
		if(forma.getLentesOtros()!=null && forma.getLentesOtros().equals("1")){
			mediaFiliacionDTO.setUsaLentes(true);
		}else{
			mediaFiliacionDTO.setUsaLentes(false);
		}
		if(forma.getBarbaOtros()!=null && forma.getBarbaOtros().equals("1")){
			mediaFiliacionDTO.setTieneBarba(true);
		}else{
			mediaFiliacionDTO.setTieneBarba(false);
		}
		if(forma.getBigoteOtros()!=null && forma.getBigoteOtros().equals("1")){
			mediaFiliacionDTO.setTieneBigote(true);
		}else{
			mediaFiliacionDTO.setTieneBigote(false);
		}
		if(forma.getEstauraOtros()!=null && forma.getEstauraOtros()!=""){
			mediaFiliacionDTO.setEstatura(Double.parseDouble(forma.getEstauraOtros()));
		}
		if(forma.getPesoOtros()!=null && forma.getPesoOtros()!=""){
			mediaFiliacionDTO.setPeso(Double.parseDouble(forma.getPesoOtros()));
		}
		valorGenericoMF = new ValorDTO();
		Long tipoSangre = 0L;
		if(forma.getTipoSangreOtros() != null &&  ! forma.getTipoSangreOtros() .equals("-1")){
			tipoSangre=Long.parseLong(forma.getTipoSangreOtros());
		}else{
			tipoSangre=null;
		}
		valorGenericoMF.setIdCampo(tipoSangre);
		mediaFiliacionDTO.setTipoSangre(valorGenericoMF);
		if(forma.getFactorrhOtros()!=null && forma.getFactorrhOtros().equals("1")){
			mediaFiliacionDTO.setFactorRH("Positivo");
		}else if(forma.getFactorrhOtros()!=null && forma.getFactorrhOtros().equals("0")){
			mediaFiliacionDTO.setFactorRH("Negativo");
		}
		
		valorGenericoMF = new ValorDTO();
		Long complexion = 0L;
		if(forma.getComplexion() != null &&  ! forma.getComplexion() .equals("-1")){
			complexion=Long.parseLong(forma.getComplexion());
		}else{
			complexion=null;
		}
		valorGenericoMF.setIdCampo(complexion);
		mediaFiliacionDTO.setComplexion(valorGenericoMF);
		logger.info(valorGenericoMF+":::compexion art");
		
		 
		valorGenericoMF = new ValorDTO();
		Long direccionCeja = 0L;
		if(forma.getDireccionCeja() != null &&  ! forma.getDireccionCeja() .equals("-1")){
			direccionCeja=Long.parseLong(forma.getDireccionCeja());
		}else{
			direccionCeja=null;
		}
		valorGenericoMF.setIdCampo(direccionCeja);
		mediaFiliacionDTO.setDireccionCeja(valorGenericoMF);
		logger.info(valorGenericoMF+":::direccionCeja art");
		
		  
		valorGenericoMF = new ValorDTO();
		Long helixOriginal = 0L;
		if(forma.getHelixOriginal() != null &&  ! forma.getHelixOriginal() .equals("-1")){
			helixOriginal=Long.parseLong(forma.getHelixOriginal());
		}else{
			helixOriginal=null;
		}
		valorGenericoMF.setIdCampo(helixOriginal);
		mediaFiliacionDTO.setHelixOriginal(valorGenericoMF);
		logger.info(valorGenericoMF+":::helixOriginal art");
		
		valorGenericoMF = new ValorDTO();
		Long orejaLobContorno = 0L;
		if(forma.getOrejaLobContorno() != null &&  ! forma.getOrejaLobContorno() .equals("-1")){
			orejaLobContorno=Long.parseLong(forma.getOrejaLobContorno());
		}else{
			orejaLobContorno=null;
		}
		valorGenericoMF.setIdCampo(orejaLobContorno);
		mediaFiliacionDTO.setOrejaLobContorno(valorGenericoMF);
		logger.info(valorGenericoMF+":::orejaLobContorno art");
		
        // Jacob
        mapeaFormaEnDto(forma, "comisuras", mediaFiliacionDTO, "labioComisuras");
        mapeaFormaEnDto(forma, "alturaNasoLabial", mediaFiliacionDTO, "alturaNasoLabial");
        mapeaFormaEnDto(forma, "espesorLabioInferior", mediaFiliacionDTO, "espesorLabioInf");
        mapeaFormaEnDto(forma, "espesorLabioSuperior", mediaFiliacionDTO, "espesorLabioSup");
        mapeaFormaEnDto(forma, "prominencia", mediaFiliacionDTO, "labiosProminencia");
        mapeaFormaEnDto(forma, "anchoNariz", mediaFiliacionDTO, "anchoNariz");
        mapeaFormaEnDto(forma, "alturaNariz", mediaFiliacionDTO, "alturaNariz");
        mapeaFormaEnDto(forma, "baseNariz", mediaFiliacionDTO, "baseNariz");
        mapeaFormaEnDto(forma, "raizNariz", mediaFiliacionDTO, "raizNariz");
        mapeaFormaEnDto(forma, "frenteAltura", mediaFiliacionDTO, "frenteAltura");
        mapeaFormaEnDto(forma, "frenteAncho", mediaFiliacionDTO, "frenteAncho");
        mapeaFormaEnDto(forma, "inclinacionFrente", mediaFiliacionDTO, "frenteInclinacion");
		return mediaFiliacionDTO;
	}
	
	/**
	 * Metodo que llena el objeto de DatosDefuncionDTO cuando la victima es muerta,
	 * con sus respectivas validaciones
	 * @param forma
	 * @return DatosDefuncionDTO
	 */
	private static DatosDefuncionDTO obtenerDatosDefuncion(IngresarIndividuoForm forma, ExpedienteDTO expediente){
		DatosDefuncionDTO dto = new DatosDefuncionDTO();
		
		dto.setEdadDefuncion(StringUtils.isNotBlank(forma.getEdadDefuncion()) ? NumberUtils.toLong(forma.getEdadDefuncion(), 0L) : null);
		dto.setEdadUnidadDefuncion(forma.getEdadUnidadDefuncion() > 0 ? new ValorDTO (forma.getEdadUnidadDefuncion()) : null);
		dto.setCondicionActividad(forma.getCondicionActividad() > 0 ? new ValorDTO (forma.getCondicionActividad()) : null);
		
		dto.setFolioDefuncion(StringUtils.isNotBlank(forma.getFolioDefuncion()) ? forma.getFolioDefuncion() : null);
		try {
			if(StringUtils.isNotBlank(forma.getFechaAveriguacion()))
				dto.setFechaAveriguacion(DateUtils.obtener(forma.getFechaAveriguacion()));
			if(StringUtils.isNotBlank(forma.getFechaDefuncion()))
				dto.setFechaDefuncion(DateUtils.obtener(forma.getFechaDefuncion()));
		} catch (NSJPNegocioException e) {
			logger.error("Error al tratar de obtener los campos de fecha de la vista Defuncion: InvolucradoFormUtil - obtenerDatosDefuncion ", e);
		}
		dto.setTipoDefuncion(forma.getTipoDefuncion() > 0 ? new ValorDTO (forma.getTipoDefuncion()) : null);
		dto.setCertificadaPor(forma.getCertificadaPor() > 0 ? new ValorDTO (forma.getCertificadaPor()) : null);
		dto.setSitioDefuncion(forma.getSitioDefuncion() > 0 ? new ValorDTO (forma.getSitioDefuncion()) : null);
		dto.setSitioLesion(forma.getSitioLesion() > 0 ? new ValorDTO (forma.getSitioLesion()) : null);
		dto.setFueEnTrabajo(forma.getFueEnTrabajo() > 0 ? new ValorDTO (forma.getFueEnTrabajo()) : null);
		dto.setAgenteExterno(StringUtils.isNotBlank(forma.getAgenteExterno()) ? forma.getAgenteExterno() : null);
		dto.setTipoEventoDefuncion(forma.getTipoEventoDefuncion() > 0 ? new ValorDTO (forma.getTipoEventoDefuncion()) : null);
		dto.setTipoVictima(forma.getTipoVictima() > 0 ? new ValorDTO (forma.getTipoVictima()) : null);
		dto.setTipoArma(forma.getTipoArma() > 0 ? new ValorDTO (forma.getTipoArma()) : null);
		
		CalidadDTO calidadDTO = new CalidadDTO();
		calidadDTO.setCalidades(Calidades.DOMICILIO);
		
		// domicilio de defuncion
		DomicilioExtranjeroDTO domExtranjero = new DomicilioExtranjeroDTO();
		domExtranjero.setEstado(forma.getEntidadFederativaDefuncion());
		domExtranjero.setMunicipio(forma.getDelegacionMunicipioDefuncion());
		domExtranjero.setAsentamientoExt(forma.getAsentamientoColoniaDefuncion());
		domExtranjero.setCalidadDTO(calidadDTO);
		domExtranjero.setExpedienteDTO(expediente);
		//domicilio de denucncia
		DomicilioExtranjeroDTO domDenuncia = new DomicilioExtranjeroDTO();
		domDenuncia.setEstado(forma.getEntidadFederativaDefDenuncia());
		domDenuncia.setMunicipio(forma.getDelegacionMunicipioDefDenuncia());
		domDenuncia.setAsentamientoExt(forma.getAsentamientoColoniaDefDenuncia());
		domDenuncia.setCalidadDTO(calidadDTO);
		domDenuncia.setExpedienteDTO(expediente);
		
		//se obtienen las fechas de creacion
		try {
			domExtranjero.setFechaCreacionElemento(DateUtils.obtener(forma.getFechaIngreso()));
			domDenuncia.setFechaCreacionElemento(DateUtils.obtener(forma.getFechaIngreso()));
		} catch (NSJPNegocioException e) {
			logger.error("Error al tratar de extraer la fecha del domicilio de defuncion: InvolucradoFormUtil - obtenerDatosDefuncion ", e);
		}
		
		//seteamos domicilios
		dto.setDomicilioDefuncion(domExtranjero);
		dto.setDomicilioDefDenuncia(domDenuncia);
		
		dto.setCausaA(StringUtils.isNotBlank(forma.getCausaA()) ? forma.getCausaA() : null);
		dto.setDuracionA(StringUtils.isNotBlank(forma.getDuracionA()) ? forma.getDuracionA() : null);
		dto.setCausaB(StringUtils.isNotBlank(forma.getCausaB()) ? forma.getCausaB() : null);
		dto.setDuracionB(StringUtils.isNotBlank(forma.getDuracionB()) ? forma.getDuracionB() : null);
		dto.setCausaC(StringUtils.isNotBlank(forma.getCausaC()) ? forma.getCausaC() : null);
		dto.setDuracionC(StringUtils.isNotBlank(forma.getDuracionC()) ? forma.getDuracionC() : null);
		dto.setCausaD(StringUtils.isNotBlank(forma.getCausaD()) ? forma.getCausaD() : null);
		dto.setDuracionD(StringUtils.isNotBlank(forma.getDuracionD()) ? forma.getDuracionD() : null);
		dto.setEdoPatologico(StringUtils.isNotBlank(forma.getEdoPatologico()) ? forma.getEdoPatologico() : null);
		dto.setDuracionPatologico(StringUtils.isNotBlank(forma.getDuracionPatologico()) ? forma.getDuracionPatologico() : null);
		
		dto.setCondicionEmbarazo(forma.getCondicionEmbarazo() > 0 ? new ValorDTO (forma.getCondicionEmbarazo()) : null);
		dto.setPeriodoPosparto(forma.getPeriodoPosparto() > 0 ? new ValorDTO (forma.getPeriodoPosparto()) : null);
		
		return dto;
	}
	/**
	 * Metodo que regresa el domicilio de defuncion de la victima
	 * @return DomicilioDTO
	 */
//	private static DomicilioDTO obtenerDomicilioDefuncion(IngresarIndividuoForm forma, ExpedienteDTO expedienteDTO){
//		
//		DomicilioDTO domicilio = new DomicilioDTO();
//		DomicilioExtranjeroDTO domExtranjero = new DomicilioExtranjeroDTO();
//		EntidadFederativaDTO estado = new EntidadFederativaDTO();
//		AsentamientoDTO asentamientoDTO = new AsentamientoDTO();
//		CiudadDTO ciudad = new CiudadDTO();
//		CalidadDTO calidadDTO = null;
//		ValorDTO valorGenerico = null;
//
//		if (!forma.getPaisDefuncion().equals("") && !forma.getPaisDefuncion().equals("-1")){
//			logger.info("/**** Pais :: "+forma.getPais());
//			calidadDTO = new CalidadDTO();
//			calidadDTO.setCalidades(Calidades.DOMICILIO);				
//			
//			if (forma.getPaisDefuncion().equals("10")) {
//				
//				valorGenerico = new ValorDTO();
//				valorGenerico.setValor(forma.getPaisDefuncion());
//				estado.setValorIdPais(valorGenerico);
//				
//				if (!forma.getEntidadFederativaDefuncion().equals("")
//						&& !forma.getEntidadFederativaDefuncion().equals("-1")) {
//					estado.setEntidadFederativaId(new Long(forma.getEntidadFederativaDefuncion()));
//					domicilio.setEntidadDTO(estado);
//				}
//				if (!forma.getDelegacionMunicipioDefuncion().equals("")
//						&& !forma.getDelegacionMunicipioDefuncion().equals("-1")) {
//					MunicipioDTO municipio = new MunicipioDTO(new Long(forma.getDelegacionMunicipioDefuncion()), "", estado);
//					asentamientoDTO.setMunicipioDTO(municipio);
//					domicilio.setMunicipioDTO(municipio);
//				}										
//				if (!forma.getCiudadDefuncion().equals("")
//						&& !forma.getCiudadDefuncion().equals("-1")) {
//					ciudad.setCiudadId(new Long(forma.getCiudadDefuncion()));
//					domicilio.setCiudadDTO(ciudad);
//					asentamientoDTO.setCiudadDTO(ciudad);
//				}					
//				
//				if (!forma.getAsentamientoColoniaDefuncion().equals("")
//						&& !forma.getAsentamientoColoniaDefuncion().equals("-1")) {
//					asentamientoDTO.setAsentamientoId(new Long(forma.getAsentamientoColoniaDefuncion()));
//				}
//				if (!forma.getTipoAsentamientoDefuncion().equals("")
//						&& !forma.getTipoAsentamientoDefuncion().equals("-1")) {
//					TipoAsentamientoDTO tipoAsentamientoDTO=new TipoAsentamientoDTO(Long.parseLong(forma.getTipoAsentamientoDefuncion()), "");
//					asentamientoDTO.setTipoAsentamientoDTO(tipoAsentamientoDTO);
//				}
//				if (!(forma.getLatitudN()== null) && !forma.getLatitudN().equals("")) {
//					String lat= forma.getLatitudN()+forma.getLatitudGrados()+"°"+forma.getLatitudMinutos()+"'"+forma.getLatitudSegundos()+"\"";
//					domicilio.setLatitud(lat);
//				}
//				if (!(forma.getLongitudE()== null) && !forma.getLongitudE().equals("")) {
//					String longitud= forma.getLongitudE()+forma.getLongitudGrados()+"°"+forma.getLongitudMinutos()+"'"+forma.getLongitudSegundos()+"\"";
//					domicilio.setLongitud(longitud);
//				}
//							
//				domicilio.setAsentamientoDTO(asentamientoDTO);
//				domicilio.setCalle(forma.getCalleDefuncion());
//				domicilio.setNumeroExterior(forma.getNumExteriorDefuncion());
//				domicilio.setNumeroInterior(forma.getNumInteriorDefuncion());
//				domicilio.setEntreCalle1(forma.getEntreCalleDefuncion());
//				domicilio.setEntreCalle2(forma.getYcalleDefuncion());
//				domicilio.setAlias(forma.getAliasDomicilioDefuncion());
//				domicilio.setEdificio(forma.getEdificioDefuncion());
//				domicilio.setReferencias(forma.getReferenciasDefuncion());
//				try {
//					domicilio.setFechaCreacionElemento(DateUtils.obtener(forma.getFechaIngreso()));
//				} catch (NSJPNegocioException e) {
//					logger.error("Error al tratar de extraer la fecha del domicilio: InvolucradoFormUtil - obtenerDomicilioDefuncion ", e);
//				}
//				if (!forma.getTipoCalleDefuncion().equals("")
//						&& !forma.getTipoCalleDefuncion().equals("-1")) {
//					domicilio.setValorCalleId(new ValorDTO(new Long(forma.getTipoCalleDefuncion())));// Tipo de calle
//				}
//				
//				domicilio.setCalidadDTO(calidadDTO);
//				domicilio.setExpedienteDTO(expedienteDTO);
//				return domicilio;
//			} else {
//				
//				domExtranjero.setPaisValor(new ValorDTO(new Long(forma.getPaisDefuncion()), forma.getPaisDefuncion()));
//				domExtranjero.setEstado(forma.getEntidadFederativaDefuncion());
//				domExtranjero.setCiudad(forma.getCiudadDefuncion());
//				domExtranjero.setMunicipio(forma.getDelegacionMunicipioDefuncion());
//				domExtranjero.setCodigoPostal(forma.getCodigoPostalDefuncion());
//				domExtranjero.setAsentamientoExt(forma.getAsentamientoColoniaDefuncion());
//				
//				if (!(forma.getLatitudN()== null) && !forma.getLatitudN().equals("")) {
//					String lat= forma.getLatitudN()+forma.getLatitudGrados()+"°"+forma.getLatitudMinutos()+"'"+forma.getLatitudSegundos()+"\"";
//					domExtranjero.setLatitud(lat);
//				}
//				if (!(forma.getLongitudE()== null) && !forma.getLongitudE().equals("")) {
//					String longitud= forma.getLongitudE()+forma.getLongitudGrados()+"°"+forma.getLongitudMinutos()+"'"+forma.getLongitudSegundos()+"\"";
//					domExtranjero.setLongitud(longitud);
//				}
//				
//				domExtranjero.setCalle(forma.getCalleDefuncion());
//				domExtranjero.setNumeroExterior(forma.getNumExteriorDefuncion());
//				domExtranjero.setNumeroInterior(forma.getNumInteriorDefuncion());
//				domExtranjero.setEntreCalle1(forma.getEntreCalleDefuncion());
//				domExtranjero.setEntreCalle2(forma.getYcalleDefuncion());
//				domExtranjero.setAlias(forma.getAliasDomicilioDefuncion());
//				domExtranjero.setEdificio(forma.getEdificioDefuncion());
//				domExtranjero.setReferencias(forma.getReferenciasDefuncion());
//				try {
//					domExtranjero.setFechaCreacionElemento(DateUtils.obtener(forma.getFechaIngreso()));
//				} catch (NSJPNegocioException e) {
//					logger.error("Error al tratar de extraer la fecha del domicilioExtranjero: InvolucradoFormUtil - obtenerDomicilioDefuncion ", e);
//				}
//					if (!forma.getTipoCalle().equals("")
//							&& !forma.getTipoCalle().equals("-1")) {
//						domicilio.setValorCalleId(new ValorDTO(new Long(forma.getTipoCalle())));// Tipo de calle
//					}
//				
//				domExtranjero.setCalidadDTO(calidadDTO);
//				domExtranjero.setExpedienteDTO(expedienteDTO);
//				return domExtranjero;
//			}
//		}									
//		
//	return null;
//	}

    /**
     * Actualiza el campo {@code campoDelDto} del {@code dtoActualizable}
     * con la informacion del campo {@code campoDeLaForma} tomado del Form
     * {@code formaActualizadora}. Actualmente solo actualiza los campos
     * del tipo ValorDTO, Long y String del dto. <p/>
     * Si el tipo del dto es ValorDTO, se crea un nuevo idCampo de tipo Long
     * a partir del campo indicado de la forma; si el tipo de regreso de
     * la forma es un String, se valida que no sea null y distinto de "-1"
     * y se trata de parsear a un Long, si no es parseable se deja como null;
     * si el tipo de regreso de la forma es un Long este es asignado al
     * idCampo del ValorDTO.<p/>
     * Si el tipo del dto es un Long se obtiene uno a partir del campo
     * de la forma de la misma manera que se obtiene el idCampo de para un
     * ValorDTO. <p/>
     * Si el tipo del dto es un String, se obtiene el campo de la forma y
     * si es distinto de null, se invoca a su metodo toString() y este valor
     * se le asigna al dto.
     * @param formaActualizadora La forma de donde se toma el campo para
     * actualizar al DTO.
     * @param campoDeLaForma El campo de donde se tomara el valor para
     * actualizar el dto.
     * @param dtoActualizable El DTO que sera actualizado.
     * @param campoDelDto El campo del DTO que se actualizara.
     */
    public static void mapeaFormaEnDto(ActionForm formaActualizadora,
            String campoDeLaForma, GenericDTO dtoActualizable, String campoDelDto) {
        boolean mapeado = false;
        Method[] metodos = dtoActualizable.getClass().getMethods();
        for (Method metodo : metodos) {
            metodo.setAccessible(true);
            try {
                String nombreMetodoDto = metodo.getName();
                if (nombreMetodoDto.equals("set" + nombreDelCampoToPrimerUpper(campoDelDto))) {
                    Class<?>[] tipos = metodo.getParameterTypes();
                    if (logger.isDebugEnabled()) {
                        logger.debug("tipos.length = " + tipos.length);
                    }
                    if (tipos.length == 1) {
                        Class tipoParametro = tipos[0];
                        if (logger.isDebugEnabled()) {
                            logger.debug("tipoParametro = " + tipoParametro);
                        }
                        // si el set de dto requiere un ValorDTO, lo creamos
                        if (tipoParametro.equals(ValorDTO.class)) {
                            ValorDTO valorDTO = new ValorDTO();
                            Long idCampo = obtenLongDeForma(formaActualizadora, campoDeLaForma);
                            valorDTO.setIdCampo(idCampo);
                            metodo.invoke(dtoActualizable, valorDTO);
                            mapeado = true;
                        }
                        if (tipoParametro.equals(Long.class)) {
                            Long l = obtenLongDeForma(formaActualizadora, campoDeLaForma);
                            metodo.invoke(dtoActualizable, l);
                            mapeado = true;
                        }
                        if (tipoParametro.equals(String.class)) {
                            String parametro = obtenStringDeForma(formaActualizadora, campoDeLaForma);
                            metodo.invoke(dtoActualizable, parametro);
                            mapeado = true;
                        }
                    }
                }
            } catch (IllegalAccessException ex) {
                logger.debug(ex);
            } catch (IllegalArgumentException ex) {
                logger.debug(ex);
            } catch (InvocationTargetException ex) {
                logger.debug(ex);
            }
        }
        if (!mapeado) {
            /**
             * Si se llega a este punto es porque el nombre de un atributo de
             * un ActionForm que se mapeaba con un DTO no corresponde a lo
             * esperado. Se debe verificar cual es el nombre de los campos
             * mapeados y el tipo que espera el setter del dto ya que este metodo
             * solo funciona con los tipos documentados.
             */
            throw new IllegalArgumentException("No fue posible mapear el atributo :"
                    + campoDeLaForma
                    + " de la forma :" + formaActualizadora + " en el campo: "
                    + campoDelDto + " del dto: " + dtoActualizable);
        }
    }

    /**
     * Metodo que extrae los datos del domicilio de la forma de Involucrado.
     * Se considera que en los combobox la opci&oacute;n de "Seleccione"
     * tiene el valor de -1
     * @param forma
     * @param expedienteTrabajo
     * @return DomicilioDTO con los datos extraidos de la forma
     */
    private static DomicilioDTO crearDomicilioOriginal(IngresarIndividuoForm forma, ExpedienteDTO expedienteTrabajo){
		DomicilioDTO domicilio = new DomicilioDTO();
		DomicilioExtranjeroDTO domExtranjero = new DomicilioExtranjeroDTO();
		EntidadFederativaDTO estado = new EntidadFederativaDTO();
		AsentamientoDTO asentamientoDTO = new AsentamientoDTO();
		CiudadDTO ciudad = new CiudadDTO();

		if (!forma.getPais().equals("") && !forma.getPais().equals("-1")){
			logger.info("/**** Pais :: "+forma.getPais());
			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.DOMICILIO);				
			
			if (forma.getPais().equals("10")) {
				
				ValorDTO valorGenerico = new ValorDTO();
				valorGenerico.setValor(forma.getPais());
				estado.setValorIdPais(valorGenerico);
				
				if (!forma.getEntidadFederativa().equals("")
						&& !forma.getEntidadFederativa().equals("-1")) {
					estado.setEntidadFederativaId(new Long(forma.getEntidadFederativa()));
					domicilio.setEntidadDTO(estado);
				}
				if (!forma.getDelegacionMunicipio().equals("")
						&& !forma.getDelegacionMunicipio().equals("-1")) {
					MunicipioDTO municipio = new MunicipioDTO(new Long(forma.getDelegacionMunicipio()), "", estado);
					asentamientoDTO.setMunicipioDTO(municipio);
					domicilio.setMunicipioDTO(municipio);
				}										
				if (!forma.getCiudad().equals("")
						&& !forma.getCiudad().equals("-1")) {
					ciudad.setCiudadId(new Long(forma.getCiudad()));
					domicilio.setCiudadDTO(ciudad);
					asentamientoDTO.setCiudadDTO(ciudad);
				}					
				
				if (!forma.getAsentamientoColonia().equals("")
						&& !forma.getAsentamientoColonia().equals("-1")) {
					asentamientoDTO.setAsentamientoId(new Long(forma.getAsentamientoColonia()));
				}
				if (!forma.getTipoAsentamiento().equals("")
						&& !forma.getTipoAsentamiento().equals("-1")) {
					TipoAsentamientoDTO tipoAsentamientoDTO=new TipoAsentamientoDTO(Long.parseLong(forma.getTipoAsentamiento()), "");
					asentamientoDTO.setTipoAsentamientoDTO(tipoAsentamientoDTO);
				}
				if (!(forma.getLatitudN()== null) && !forma.getLatitudN().equals("")) {
					String lat= forma.getLatitudN()+forma.getLatitudGrados()+"°"+forma.getLatitudMinutos()+"'"+forma.getLatitudSegundos()+"\"";
					domicilio.setLatitud(lat);
				}
				if (!(forma.getLongitudE()== null) && !forma.getLongitudE().equals("")) {
					String longitud= forma.getLongitudE()+forma.getLongitudGrados()+"°"+forma.getLongitudMinutos()+"'"+forma.getLongitudSegundos()+"\"";
					domicilio.setLongitud(longitud);
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
				try {
					domicilio.setFechaCreacionElemento(DateUtils.obtener(forma.getFechaIngreso()));
				} catch (NSJPNegocioException e) {
					logger.error(e);
				}
				if (!forma.getTipoCalle().equals("")
						&& !forma.getTipoCalle().equals("-1")) {
					domicilio.setValorCalleId(new ValorDTO(new Long(forma.getTipoCalle())));// Tipo de calle
				}
				
				domicilio.setCalidadDTO(calidadDTO);
				domicilio.setExpedienteDTO(expedienteTrabajo);
				return domicilio;
			} else {
				
				domExtranjero.setPaisValor(new ValorDTO(new Long(forma.getPais()), forma.getPais()));
				domExtranjero.setEstado(forma.getEntidadFederativa());
				domExtranjero.setCiudad(forma.getCiudad());
				domExtranjero.setMunicipio(forma.getDelegacionMunicipio());
				domExtranjero.setCodigoPostal(forma.getCodigoPostal());
				domExtranjero.setAsentamientoExt(forma.getAsentamientoColonia());
				
				if (!(forma.getLatitudN()== null) && !forma.getLatitudN().equals("")) {
					String lat= forma.getLatitudN()+forma.getLatitudGrados()+"°"+forma.getLatitudMinutos()+"'"+forma.getLatitudSegundos()+"\"";
					domExtranjero.setLatitud(lat);
				}
				if (!(forma.getLongitudE()== null) && !forma.getLongitudE().equals("")) {
					String longitud= forma.getLongitudE()+forma.getLongitudGrados()+"°"+forma.getLongitudMinutos()+"'"+forma.getLongitudSegundos()+"\"";
					domExtranjero.setLongitud(longitud);
				}
				
				domExtranjero.setCalle(forma.getCalle());
				domExtranjero.setNumeroExterior(forma.getNumExterior());
				domExtranjero.setNumeroInterior(forma.getNumInterior());
				domExtranjero.setEntreCalle1(forma.getEntreCalle());
				domExtranjero.setEntreCalle2(forma.getYcalle());
				domExtranjero.setAlias(forma.getAliasDomicilio());
				domExtranjero.setEdificio(forma.getEdificio());
				domExtranjero.setReferencias(forma.getReferencias());
				try {
					domExtranjero.setFechaCreacionElemento(DateUtils.obtener(forma.getFechaIngreso()));
				} catch (NSJPNegocioException e) {
					logger.error(e);
				}				
				domExtranjero.setCalidadDTO(calidadDTO);
				domExtranjero.setExpedienteDTO(expedienteTrabajo);
				return domExtranjero;
			}
		}
		return null;
	}
    
	/**
	 * M&eacute;todo que recupera los alias asignados al involucrado en el JSP y los transforma
	 * a una lista de AliasInvolucradoDTO
	 * @param forma - Forma de struts de la cual se obtienen los alias asignados a cada uno de 
	 * 				  los incolucrados.
	 * @return List<AliasInvolucradoDTO> - Lista con los DTOs que representan los alias del 
	 * 									   involucrado.
	 */
	public static List<AliasInvolucradoDTO> obtenerAliasInvolucrado (ActionForm forma){
		List<AliasInvolucradoDTO> listaAliasDTO = new ArrayList<AliasInvolucradoDTO>();
		String aliases = null;
		if (forma instanceof IngresarIndividuoForm ){
			aliases = ((IngresarIndividuoForm) forma).getAlias();
		}else if (forma instanceof IngresarActaCircunstanciadaForm ){
			aliases = ((IngresarActaCircunstanciadaForm) forma).getAlias();
		}
		if(StringUtils.isNotBlank(aliases)){
			//obtenemos los alias
			String[] arrAliasInvlocurado=aliases.split(",");
			logger.info("alias_del_involucrado:: "+arrAliasInvlocurado);	
			for (String alias : arrAliasInvlocurado) {
				AliasInvolucradoDTO aliasDTO = new AliasInvolucradoDTO();
                aliasDTO.setAlias(alias);
                listaAliasDTO.add(aliasDTO);
                aliasDTO=null;
			}
		}
		return listaAliasDTO;
	}
	
	/**
	 * Cambia el primer caracter el campo en Mayuscula
	 * 
	 * @param campoDelDto
	 * @return 
	 */
	private static String nombreDelCampoToPrimerUpper(String campoDelDto) {
        return "" + campoDelDto.substring(0, 1).toUpperCase() + campoDelDto.substring(1);
    }

	/**
	 * TODO Documentar
	 * 
	 * @param formaActualizadora
	 * @param campoDeLaForma
	 * @return
	 */
    private static Long obtenLongDeForma(ActionForm formaActualizadora, String campoDeLaForma) {
        Method[] metodos = formaActualizadora.getClass().getMethods();
        Long idCampo = null;
        try {
            for (Method metodo : metodos) {
                metodo.setAccessible(true);
                if (metodo.getName().equals("get" + nombreDelCampoToPrimerUpper(campoDeLaForma))) {
                    Class<?> tipoDeRegreso = metodo.getReturnType();
                    // Si el tipo de regreso de la forma es un String.
                    if (tipoDeRegreso != null) {
                        Object valorDeRegreso = metodo.invoke(formaActualizadora);
                        if (tipoDeRegreso.equals(String.class)) {
                            // ... tratamos de parsear el string a un long en caso que el string sea
                            // distinto de -1
                            if (valorDeRegreso != null && !valorDeRegreso.equals("-1")) {
                                try {
                                    idCampo = Long.parseLong((String) valorDeRegreso);
                                } catch (NumberFormatException nfe) {
                                    logger.debug(nfe);
                                }
                            }
                        }
                        if (tipoDeRegreso.equals(Long.class)) {
                            idCampo = (Long) valorDeRegreso;
                        }
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            logger.debug(ex);
        } catch (IllegalArgumentException ex) {
            logger.debug(ex);
        } catch (InvocationTargetException ex) {
            logger.debug(ex);
        }
        return idCampo;
    }
    
    /**
     * TODO Documentar
     * 
     * @param formaActualizadora
     * @param campoDeLaForma
     * @return
     */
    private static String obtenStringDeForma(ActionForm formaActualizadora, String campoDeLaForma) {
        Method[] metodos = formaActualizadora.getClass().getMethods();
        String string = null;
        try {
            for (Method metodo : metodos) {
                metodo.setAccessible(true);
                if (metodo.getName().equals("get" + nombreDelCampoToPrimerUpper(campoDeLaForma))) {
                    Class<?> tipoDeRegreso = metodo.getReturnType();
                    // Si el tipo de regreso de la forma es un String.
                    if (tipoDeRegreso != null) {
                        Object valorDeRegreso = metodo.invoke(formaActualizadora);
                        if (valorDeRegreso != null) {
                            string = valorDeRegreso.toString();
                        }
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            logger.debug(ex);
        } catch (IllegalArgumentException ex) {
            logger.debug(ex);
        } catch (InvocationTargetException ex) {
            logger.debug(ex);
        }
        return string;
    }

}
