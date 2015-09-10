/**
* Nombre del Programa : ModificarIndividuoServiceImpl.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 9 May 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacion del servicio para realizar la actualizacion de la informacion de un individuo
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
package mx.gob.segob.nsjp.service.involucrado.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.involucrado.SituacionJuridica;
import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.comun.enums.relacion.TipoRelacion;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.ValorDAO;
import mx.gob.segob.nsjp.dao.involucrado.AliasInvolucradoDAO;
import mx.gob.segob.nsjp.dao.involucrado.DetencionDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoNacionalidadDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoOcupacionDAO;
import mx.gob.segob.nsjp.dao.persona.CorreoElectronicoDAO;
import mx.gob.segob.nsjp.dao.persona.NombreDemograficoDAO;
import mx.gob.segob.nsjp.dao.persona.TelefonoDAO;
import mx.gob.segob.nsjp.dao.relacion.RelacionDAO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO;
import mx.gob.segob.nsjp.model.AliasInvolucrado;
import mx.gob.segob.nsjp.model.Calidad;
import mx.gob.segob.nsjp.model.CorreoElectronico;
import mx.gob.segob.nsjp.model.Detencion;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.InvolucradoNacionalidad;
import mx.gob.segob.nsjp.model.InvolucradoNacionalidadId;
import mx.gob.segob.nsjp.model.InvolucradoOcupacion;
import mx.gob.segob.nsjp.model.InvolucradoOcupacionId;
import mx.gob.segob.nsjp.model.MediaFiliacion;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.model.Relacion;
import mx.gob.segob.nsjp.model.SeniaParticular;
import mx.gob.segob.nsjp.model.Telefono;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.domicilio.IngresarDomicilioService;
import mx.gob.segob.nsjp.service.domicilio.ModificarDomicilioServicio;
import mx.gob.segob.nsjp.service.involucrado.EliminarInvolucradoService;
import mx.gob.segob.nsjp.service.involucrado.ModificarIndividuoService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.organizacion.EliminarOrganizacionService;
import mx.gob.segob.nsjp.service.organizacion.IngresarOrganizacionService;
import mx.gob.segob.nsjp.service.organizacion.ModificarOrganizacionService;
import mx.gob.segob.nsjp.service.relacion.GenerarRelacionService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servicio para realizar la actualizacion de la informacion de un individuo.
 * @version 1.0
 * @author cesarAgustin
 *
 */
@Service
@Transactional
public class ModificarIndividuoServiceImpl implements ModificarIndividuoService {

	/**
	 * 
	 */
	private final static Logger logger = Logger.getLogger(ModificarIndividuoServiceImpl.class);
	
	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private AliasInvolucradoDAO aliasInvolucradoDAO;
	@Autowired
	private InvolucradoNacionalidadDAO involucradoNacionalidadDAO;
	@Autowired
	private InvolucradoOcupacionDAO involucradoOcupacionDAO;
	@Autowired
	private TelefonoDAO telefonoDAO;	   
	@Autowired
	private CorreoElectronicoDAO correoElectronicoDAO;
	@Autowired
	private RelacionDAO relacionDAO; 
	@Autowired
	private NombreDemograficoDAO nombreDemograficoDAO;
	@Autowired
	private EliminarInvolucradoService eliminarInvolucradoService;
	@Autowired
	private EliminarOrganizacionService eliminarOrganizacionService;
	@Autowired
	private IngresarOrganizacionService ingresarOrganizacionService;
	@Autowired
	private ModificarOrganizacionService modificarOrganizacionService;
	@Autowired
	private ModificarDomicilioServicio modificarDomicilioServicio;	
	@Autowired
	private IngresarDomicilioService ingresarDomicilioService;
	@Autowired
	private GenerarRelacionService generarRelacionService;
	@Autowired
	private DetencionDAO detencionDAO;
	@Autowired
	private ValorDAO valorDAO;
	
	@Override
	public Long actualizarIndividuo(InvolucradoDTO involucradoDTO) throws NSJPNegocioException {
		
		if (logger.isDebugEnabled()){
			logger.debug("/**** SERVICIO PARA MODIFICACION DE INVOLUCRADOS ****/");			
		}
		
		if (involucradoDTO.getCalidadDTO()==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);			
		}
		
		//Obtener datos involucrado actual registrado en BD
		Involucrado invoBD = involucradoDAO.read(involucradoDTO.getElementoId());
		
		if(involucradoDTO.getDetenciones() != null){
			logger.info("/** INVOLUCRADO ANTES TRANSFORMER  **/ " + involucradoDTO.getDetenciones().toString());
		}
		//Convertir el involucradoDTO a involucrado
		Involucrado invoMod = InvolucradoTransformer.transformarInvolucrado(involucradoDTO);
		
		//Obtenemos la calidad del involucrado
		Long idCalidadInv = invoMod.getCalidad().getTipoCalidad().getValorId();
		
		//OSD NSJP 47 Modificar denunciante 
//		if (idCalidadInv.equals(Calidades.DENUNCIANTE.getValorId())) {
//			if (logger.isDebugEnabled())
//				logger.debug("/**** EL INDVIDUO ES UN DENUNCIANTE ****/");
//			
//			if (invoBD.getCondicion()!= null && invoMod.getCondicion()!= null && !(invoBD.getCondicion().equals(invoMod.getCondicion()))) {
//				if (logger.isDebugEnabled())
//					logger.debug("/**** LA RELACION DENUNCIANTE-VICTIMA FUE MODIFICADA ****/");
//				if (invoMod.getCondicion().intValue()==1) {
//					List<Involucrado> listVictimas = involucradoDAO.obtenerInvByIdExpAndCalidad(invoBD.getExpediente().getExpedienteId(),
//														Calidades.VICTIMA_PERSONA.getValorId(),null);
//					for (Involucrado indVictima : listVictimas) {
//						logger.debug("/**** ID VICTIMA A ELIMINAR ****/" + indVictima.getElementoId());
//						eliminarInvolucradoService.eliminarInvolucrado(new InvolucradoDTO(indVictima.getElementoId()));						
//					}										
//				} 
//			}
//		}

		if (idCalidadInv.equals(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId())) {
			if (invoBD.getEsDetenido()!= null &&  invoMod.getEsDetenido()!= null && !(invoBD.getEsDetenido().equals(invoMod.getEsDetenido()))) {
				if (!invoMod.getEsDetenido()) {
					List<Detencion> listaDetenciones=detencionDAO.consultarDetencionByInvolucrado(invoBD.getElementoId());
					for (Detencion deten : listaDetenciones) {
						detencionDAO.delete(deten);
					}
				}
			}
		}
		
		//ORGANIZACION
		if (idCalidadInv.equals(Calidades.DENUNCIANTE.getValorId()) ||
				idCalidadInv.equals(Calidades.VICTIMA_PERSONA.getValorId()) ||
				idCalidadInv.equals(Calidades.DENUNCIANTE_ORGANIZACION.getValorId()) ||
				(idCalidadInv.equals(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId()))||
				(idCalidadInv.equals(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION.getValorId()))) {
			//En caso de que se modifique el tipo de persona
			if (invoBD.getTipoPersona()!= null && invoMod.getTipoPersona()!= null && !(invoBD.getTipoPersona().equals(invoMod.getTipoPersona()))) {						
				//Si cambio a Persona Fisica, se elimina la organizaci�n y sus dependencias
				if (invoMod.getTipoPersona().equals("Fisica")) {
					if (logger.isDebugEnabled())
						logger.debug("/**** EL INDVIDUO MODIFICO SU TIPO DE PERSONA A FISICA ****/");

						List<Relacion> lRelaciones = relacionDAO.obtenerRelacionSimple(invoMod.getElementoId(), new Long(Relaciones.ORGANIZACION_INVOLUCRADA.ordinal()) );
						if(lRelaciones !=null &&!lRelaciones.isEmpty() ){
							OrganizacionDTO  organizacionDTO = involucradoDTO.getOrganizacionDTO();
							organizacionDTO.setElementoId(lRelaciones.get(0).getElementoByComplementoId().getElementoId());
							logger.info(" Id de Organizaci�n: "+ organizacionDTO.getElementoId());
	
							eliminarOrganizacionService.eliminarOrganizacion(organizacionDTO);	
						}
				} else if (invoMod.getTipoPersona().equals("Moral")) { //Si cambio a Persona Moral, se ingresa la organiacion
					if (logger.isDebugEnabled())
						logger.debug("/**** EL INDVIDUO MODIFICO SU TIPO DE PERSONA A MORAL ****/");
					//Crear una nueva organizacion con toda sus dependencias.
//					if (involucradoDTO.getOrganizacionDTO()!=null) 
//						ingresarOrganizacionService.ingresarOrganizacion(involucradoDTO.getOrganizacionDTO());											
				}
			}
			//Si es moral DEBE de contener una organizaci�n
//			if (invoMod.getTipoPersona().equals("Moral") && involucradoDTO.getOrganizacionDTO()!= null && involucradoDTO.getOrganizacionDTO().getOrganizacionId()!= null) {
			//Para obtener el Id de organizaci�n
			if (invoMod.getTipoPersona().equals("Moral") && involucradoDTO.getOrganizacionDTO()!= null){
				if (logger.isDebugEnabled())
					logger.debug("/**** EL INDVIDUO ES PERSONA A MORAL Y ES UNA ORGANIZACION ****/");

				//Obtener la relacion de Organizacion
				List<Relacion> lRelaciones = relacionDAO.obtenerRelacionSimple(invoMod.getElementoId(), new Long(Relaciones.ORGANIZACION_INVOLUCRADA.ordinal()) );
				//Realizar actualizacion de organizacion
				if(lRelaciones != null && !lRelaciones.isEmpty() ){
					OrganizacionDTO  organizacionDTO = involucradoDTO.getOrganizacionDTO();
					logger.info(" Id de Organizaci�n: "+ lRelaciones.get(0).getElementoByComplementoId().getElementoId());
					
					organizacionDTO.setElementoId(lRelaciones.get(0).getElementoByComplementoId().getElementoId());
					organizacionDTO.setExpedienteDTO(involucradoDTO.getExpedienteDTO());
					involucradoDTO.setOrganizacionDTO(organizacionDTO);
					
					modificarOrganizacionService.modificarOrganizacion(involucradoDTO.getOrganizacionDTO());
				}
				else{ //Se ingresa una nueva organizaci�n
					Calidad calidad = new Calidad(); 
					OrganizacionDTO organizacionDTO = involucradoDTO.getOrganizacionDTO();
					//Obtener calidad
		        	if(organizacionDTO.getCalidadDTO()!=null)
		        		calidad.setDescripcionEstadoFisico(organizacionDTO.getCalidadDTO().getDescripcionEstadoFisico()!=null?organizacionDTO.getCalidadDTO().getDescripcionEstadoFisico():null);
		        	//Tipo de Organizaci�n
		        	calidad.setTipoCalidad(new Valor(involucradoDTO.getCalidadDTO().getCalidades().getValorId()));        	
		        	invoBD.setCalidad(calidad);
		        	involucradoDTO.getOrganizacionDTO().setExpedienteDTO(involucradoDTO.getExpedienteDTO());
		        	
		        	OrganizacionDTO nuevaOrganizacionDTO = ingresarOrganizacionService.ingresarOrganizacion(involucradoDTO.getOrganizacionDTO());
		        	
		        	Long idOrganizacion = nuevaOrganizacionDTO .getElementoId();
		        	logger.debug("/**** Se genero la organizacion con id" + idOrganizacion + " ****/");
		        	//Generar relacion involucrado - organizacion
		        	if(idOrganizacion>0)
		        		generarRelacionService.generarRelacion(invoBD.getElementoId(), idOrganizacion, Relaciones.ORGANIZACION_INVOLUCRADA, TipoRelacion.IMPLICITA.getValorId().shortValue());
				}
			} 
		}
		
		//Calidades
		if (idCalidadInv.equals(Calidades.QUIEN_DETUVO.getValorId())) {
			List<Relacion> relaciones = relacionDAO.obtenerRelacionSimple(involucradoDTO.getElementoId(), new Long(Relaciones.QUIEN_DETUVO.ordinal()));			
			for (Relacion relacion : relaciones) {
				relacionDAO.delete(relacion);
			}
		}
		
		//Idioma, Escolaridad, estadoCivil, Religi�n, Ocupaci�n. Son modificados en entidad involucrado ver InvolucradoTransformer.
        List<InvolucradoNacionalidad> nacionalidades = involucradoNacionalidadDAO.consultarNacionalidadByInvolucrado(invoBD.getElementoId());
		if (nacionalidades!=null && !nacionalidades.isEmpty()) {
			logger.debug("/**** SE OBTUVIERON NACIONALIDADES DEL INDVIDUO ****/");
			for (InvolucradoNacionalidad invNacionalidad : nacionalidades) {
				involucradoNacionalidadDAO.delete(invNacionalidad);
			}
		}	
		
        // Ocupaciones
		List<InvolucradoOcupacion> ocupaciones = involucradoOcupacionDAO
				.consultarOcupacionByInvolucrado(invoBD.getElementoId());
		if (ocupaciones != null && !ocupaciones.isEmpty()) {
			logger.debug("/**** SE OBTUVIERON OCUPACIONES DEL INDVIDUO ****/");
			for (InvolucradoOcupacion invOcupacion : ocupaciones) {
				involucradoOcupacionDAO.delete(invOcupacion);
			}
		}

        //DATOS GENERALES
        //Nombre Demografico (Uno solo)
		if (invoMod.getNombreDemograficos()!=null && !invoMod.getNombreDemograficos().isEmpty()) {
			List<NombreDemografico> nombres = nombreDemograficoDAO.consutarNombresByInvolucrado(invoBD.getElementoId());
			if (nombres!=null && !nombres.isEmpty()) {
				logger.debug("/**** SE OBTUVIERON NOMBRES DEMOGRAFICOS DEL INDIVIDUO ****/");
				for (NombreDemografico nomDemografico : nombres) {
					nombreDemograficoDAO.delete(nomDemografico);
				}

			}
			
			//Actualizar esVerdadero en true para actualizar
			//Resuelve incidencia en donde al actualizar un individuo,.. y pasar null el valor de esVerdadero
			//se hace la consulta en el action para mostrarse como an�nimo
			for (NombreDemografico nombreDemografico : invoMod.getNombreDemograficos()) {
				nombreDemografico.setEsVerdadero(true);
			}
		}

        
		//Alias
		List<AliasInvolucrado> alias = aliasInvolucradoDAO.consultarAliasByInvolucrado(invoBD.getElementoId());
		if (alias!=null && !alias.isEmpty()) {
			 logger.debug("/**** SE OBTUVIERON ALIAS DEL INDVIDUO ****/");
			 for (AliasInvolucrado aliasInv : alias) {
				aliasInvolucradoDAO.delete(aliasInv);
			}
		}		
		//Medios de Contacto -Telefono
		List<Telefono> telefonos = telefonoDAO.consultarTelefonosByPersona(invoBD.getElementoId());
		if (telefonos!=null && !telefonos.isEmpty()) {
			logger.debug("/**** SE OBTUVIERON TELEFONOS DEL INDIVIDUO ****/");
			for (Telefono telefono : telefonos) {
				telefonoDAO.delete(telefono);
			}
		}
		//Medios de Contacto -Correo electronico		
		List<CorreoElectronico> correos = correoElectronicoDAO.consultarCorreosByPersona(invoBD.getElementoId());
		if (correos!=null && !correos.isEmpty()) {
			logger.debug("/**** SE OBTUVIERON CORREOS DEL INDIVIDUO ****/");
				for (CorreoElectronico correoElectronico : correos) {
					correoElectronicoDAO.delete(correoElectronico);
				}
		}
		
		//Domicilio	Verificar si se Modifica o se Crea uno nuevo
		List<Relacion> lRelaciones = relacionDAO.obtenerRelacionSimple(involucradoDTO.getElementoId(), new Long(Relaciones.RESIDENCIA.ordinal()));
		if (involucradoDTO.getDomicilio()!=null ){
		    involucradoDTO.getDomicilio().setExpedienteDTO(involucradoDTO.getExpedienteDTO());
			//Si existe un domicilio en BD, se actualiza con los nuevos datos, se mantiene la realacion
			if(!lRelaciones.isEmpty() && lRelaciones.get(0).getElementoByComplementoId()!= null ){
				DomicilioDTO domicilioDTO = involucradoDTO.getDomicilio();
				domicilioDTO.setElementoId(lRelaciones.get(0).getElementoByComplementoId().getElementoId());
				modificarDomicilioServicio.modificarDomicilio(domicilioDTO);
			}
			else{  //se da de alta el Domicilio y se crea una nueva relacion
				Long idDomicilio = ingresarDomicilioService.ingresarDomicilio(involucradoDTO.getDomicilio());
				//Se crea la relaci�n entre Domicilio y Representante
				generarRelacionService.generarRelacion(involucradoDTO.getElementoId(), idDomicilio, Relaciones.RESIDENCIA, (short)0);
			}
		}//No se elimina de BD, el domicilio del involucrado
		
		//Domicilio	Verificar si se Modifica o se Crea uno nuevo
		lRelaciones = relacionDAO.obtenerRelacionSimple(involucradoDTO.getElementoId(), new Long(Relaciones.NOTIFICACION.ordinal()));
		if (involucradoDTO.getDomicilioNotificacion()!=null ){
		    involucradoDTO.getDomicilioNotificacion().setExpedienteDTO(involucradoDTO.getExpedienteDTO());
			//Si existe un domicilio en BD, se actualiza con los nuevos datos, se mantiene la realacion
			if(!lRelaciones.isEmpty() && lRelaciones.get(0).getElementoByComplementoId()!= null ){
				DomicilioDTO domicilioDTO = involucradoDTO.getDomicilioNotificacion();
				domicilioDTO.setElementoId(lRelaciones.get(0).getElementoByComplementoId().getElementoId());
				modificarDomicilioServicio.modificarDomicilio(domicilioDTO);
			}
			else{  //se da de alta el Domicilio y se crea una nueva relacion
				Long idDomicilio = ingresarDomicilioService.ingresarDomicilio(involucradoDTO.getDomicilioNotificacion());
				//Se crea la relaci�n entre Domicilio Notificacion y Representante
				generarRelacionService.generarRelacion(involucradoDTO.getElementoId(), idDomicilio, Relaciones.NOTIFICACION, (short)0);
			}
		}//No se elimina de BD, el domicilio de notificacion
		
		//Domicilio de defuncion Verificar si se Modifica o se Crea uno nuevo
		lRelaciones = relacionDAO.obtenerRelacionSimple(involucradoDTO.getElementoId(), new Long(Relaciones.DOMICILIO_DEFUNCION.ordinal()));
		if (involucradoDTO.getDatosDefuncion()!=null && involucradoDTO.getDatosDefuncion().getDomicilioDefuncion()!=null ){
		    involucradoDTO.getDatosDefuncion().getDomicilioDefuncion().setExpedienteDTO(involucradoDTO.getExpedienteDTO());
			//Si existe un domicilio en BD, se actualiza con los nuevos datos, se mantiene la realacion
			if(!lRelaciones.isEmpty() && lRelaciones.get(0).getElementoByComplementoId()!= null ){
				DomicilioDTO domicilioDTO = involucradoDTO.getDatosDefuncion().getDomicilioDefuncion();
				domicilioDTO.setElementoId(lRelaciones.get(0).getElementoByComplementoId().getElementoId());
				modificarDomicilioServicio.modificarDomicilio(domicilioDTO);
			}
			else{  //se da de alta el Domicilio y se crea una nueva relacion
				Long idDomicilio = ingresarDomicilioService.ingresarDomicilio(involucradoDTO.getDomicilio());
				//Se crea la relaci�n entre Domicilio y Representante
				generarRelacionService.generarRelacion(involucradoDTO.getElementoId(), idDomicilio, Relaciones.DOMICILIO_DEFUNCION, (short)0);
			}
		}//No se elimina de BD, el domicilio del involucrado
		
		//Domicilio de denuncia de la defuncion Verificar si se Modifica o se Crea uno nuevo
		lRelaciones = relacionDAO.obtenerRelacionSimple(involucradoDTO.getElementoId(), new Long(Relaciones.DOMICILIO_DEFUNCION_DENUNCIA.ordinal()));
		if (involucradoDTO.getDatosDefuncion()!=null && involucradoDTO.getDatosDefuncion().getDomicilioDefDenuncia()!=null ){
		    involucradoDTO.getDatosDefuncion().getDomicilioDefDenuncia().setExpedienteDTO(involucradoDTO.getExpedienteDTO());
			//Si existe un domicilio en BD, se actualiza con los nuevos datos, se mantiene la realacion
			if(!lRelaciones.isEmpty() && lRelaciones.get(0).getElementoByComplementoId()!= null ){
				DomicilioDTO domicilioDTO = involucradoDTO.getDatosDefuncion().getDomicilioDefDenuncia();
				domicilioDTO.setElementoId(lRelaciones.get(0).getElementoByComplementoId().getElementoId());
				modificarDomicilioServicio.modificarDomicilio(domicilioDTO);
			}
			else{  //se da de alta el Domicilio y se crea una nueva relacion
				Long idDomicilio = ingresarDomicilioService.ingresarDomicilio(involucradoDTO.getDomicilio());
				//Se crea la relaci�n entre Domicilio y Representante
				generarRelacionService.generarRelacion(involucradoDTO.getElementoId(), idDomicilio, Relaciones.DOMICILIO_DEFUNCION_DENUNCIA, (short)0);
			}
		}//No se elimina de BD, el domicilio del involucrado
		
		invoBD = InvolucradoTransformer.involucradoUpdate(invoBD, invoMod);
		
		if(invoBD.getNombreDemograficos() != null){
			for (NombreDemografico nombreDemografico : invoBD.getNombreDemograficos()) {
				nombreDemografico.setNombreDemograficoId(null);
			}
		}
		
		//Verificar si es probable responsable o victima
        logger.info("/**** PROBABLE_RESPONSABLE_PERSONA:" + involucradoDTO.getCalidadDTO().getCalidades().equals(Calidades.PROBABLE_RESPONSABLE_PERSONA) +
        		" /**** VICTIMA_PERSONA:" + involucradoDTO.getCalidadDTO().getCalidades().equals(Calidades.VICTIMA_PERSONA));
        
        
        if (involucradoDTO.getCalidadDTO().getCalidades().equals(Calidades.PROBABLE_RESPONSABLE_PERSONA)
        		|| involucradoDTO.getCalidadDTO().getCalidades().equals(Calidades.VICTIMA_PERSONA)
        		|| involucradoDTO.getCalidadDTO().getCalidades().equals(Calidades.DEFENDIDO)) {

        	//Si se va a actualizar la MediaFiliacion
    		if (!invoBD.getMediaFiliacions().isEmpty() && involucradoDTO.getMediaFiliacionDTO()!= null){
    			logger.debug("/**** SE OBTUVO MEDIA AFILIACION DEL INDIVIDUO ****/");
    			for (Iterator iterator = invoBD.getMediaFiliacions().iterator(); iterator.hasNext();) {
    				MediaFiliacion mediaFiliacion = (MediaFiliacion) iterator.next();
    				logger.info(" MediaFilacion Id:"+ mediaFiliacion.getMediaFiliacionId());
    				mediaFiliacion = InvolucradoTransformer.transformarMediaFilacionUpdate(mediaFiliacion, involucradoDTO.getMediaFiliacionDTO());
    				
    				//Senia Particular a Actualizar o Nueva
    				if (involucradoDTO.getMediaFiliacionDTO().getSeniaParticularDTO() !=null){
    					logger.debug("/**** SE OBTUVO SENIA PARTICULAR DEL INDIVIDUO ****/");
    					SeniaParticular  seniaParticularN = mediaFiliacion.getSeniaParticular();
    					seniaParticularN = InvolucradoTransformer.transformarSeniaParticularUpdate(seniaParticularN, involucradoDTO.getMediaFiliacionDTO().getSeniaParticularDTO());
    					seniaParticularN.setMediaFiliacion(mediaFiliacion);
    					mediaFiliacion.setSeniaParticular(seniaParticularN);
    				}
    			}
    		}
    		//Si es una nueva MediaFiliacion
    		else if( involucradoDTO.getMediaFiliacionDTO()!= null ){ 
    			logger.debug("/**** SE CREARA NUEVA MEDIA AFILIACION DEL INDIVIDUO ****/");
    			MediaFiliacion mediaFiliacion = InvolucradoTransformer.transformarMediaFilacion(involucradoDTO.getMediaFiliacionDTO() );
    			//Senia Particular Nueva
    			if(involucradoDTO.getMediaFiliacionDTO().getSeniaParticularDTO() != null ){
        			logger.debug("/**** SE CREARA NUEVA SENIA PARTICULAR DEL INDIVIDUO ****/");
    				SeniaParticular  seniaParticularN = InvolucradoTransformer.transformarSeniaParticularDTO(involucradoDTO.getMediaFiliacionDTO().getSeniaParticularDTO());
    				seniaParticularN.setMediaFiliacion(mediaFiliacion);
					mediaFiliacion.setSeniaParticular(seniaParticularN);
    			}
    			//Referencia de Involucrado
    			mediaFiliacion.setInvolucrado(invoBD);
    			Set<MediaFiliacion> filiaciones = new HashSet<MediaFiliacion>();
    			filiaciones.add(mediaFiliacion);
    			invoBD.setMediaFiliacions(filiaciones);
    		}
        }        
        
		invoBD.setEsMismoDomicilio(involucradoDTO.getEsMismoDomicilio());

		// SITUACION JURIDICA
		ValorDTO situacionJuridicaDTO = involucradoDTO.getValorSituacionJuridica();

		if( situacionJuridicaDTO == null ) {

			if (involucradoDTO.getCalidadDTO().getCalidades()
					.equals(Calidades.PROBABLE_RESPONSABLE_PERSONA)
					|| involucradoDTO.getCalidadDTO().getCalidades()
					.equals(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION))
				invoBD.setSituacionJuridica(new Valor(
						SituacionJuridica.INDICIADO.getValorId()));
		}else{
			Valor situacionJuridica = valorDAO.read(new Long(situacionJuridicaDTO.getValor()));
			invoBD.setSituacionJuridica(situacionJuridica);
		}

		involucradoDAO.update(invoBD);
		
		// Generar relaciones quien detuvo
		if (involucradoDTO.getCalidadDTO().getCalidades()
				.equals(Calidades.QUIEN_DETUVO)
				&& involucradoDTO.getIdsDetenidos() != null
				&& !involucradoDTO.getIdsDetenidos().isEmpty()) {
			for (Long idDetenido : involucradoDTO.getIdsDetenidos()) {
				generarRelacionService.generarRelacion(invoBD.getElementoId(),
						idDetenido, Relaciones.QUIEN_DETUVO,
						TipoRelacion.EXPLICITA.getValorId().shortValue());
			}
		}

		//Actualizar los datos que estanrelacionados al involucrado Ocupaciones, Nacionalidades, Alias
		
		//Obtener Ocupaciones
        if (involucradoDTO.getValorIdOcupacion()!=null && involucradoDTO.getValorIdOcupacion().size()>0) { 
        	logger.debug("Se obtuvo ocupaciones");
        	for (ValorDTO invOcupacionDTO : involucradoDTO.getValorIdOcupacion()) {	
        		if (invOcupacionDTO.getIdCampo()!=null) {
        			InvolucradoOcupacion invOcupacion = new InvolucradoOcupacion();
					InvolucradoOcupacionId invOcId = new InvolucradoOcupacionId();
					invOcId.setInvolucradoId(invoBD.getElementoId());
					invOcId.setValorId(invOcupacionDTO.getIdCampo());
					invOcupacion.setId(invOcId);
					invOcupacion.setInvolucrado(new Involucrado(invoBD.getElementoId()));
					invOcupacion.setValor(new Valor(invOcupacionDTO.getIdCampo()));
					involucradoOcupacionDAO.create(invOcupacion);
        		}
        	}        
        }   
        
        //Obtener nacionalidades
        if (involucradoDTO.getValorIdNacionalidad()!=null && involucradoDTO.getValorIdNacionalidad().size()>0) {
        	logger.debug("Se obtuvo nacionalidades");
        	for (ValorDTO invNacionalidadDTO : involucradoDTO.getValorIdNacionalidad()) {
        		if (invNacionalidadDTO.getIdCampo()!=null) {
        			InvolucradoNacionalidad invNacionalidad = new InvolucradoNacionalidad();
					InvolucradoNacionalidadId invNacId = new InvolucradoNacionalidadId();
					invNacId.setInvolucradoId(invoBD.getElementoId());
					invNacId.setValorId(invNacionalidadDTO.getIdCampo());
					invNacionalidad.setId(invNacId);
					invNacionalidad.setInvolucrado(new Involucrado(invoBD.getElementoId()));
					invNacionalidad.setValor(new Valor(invNacionalidadDTO.getIdCampo()));
					involucradoNacionalidadDAO.create(invNacionalidad);
        		}
        	}        
        }
		
		return invoBD.getElementoId();
	}
}
