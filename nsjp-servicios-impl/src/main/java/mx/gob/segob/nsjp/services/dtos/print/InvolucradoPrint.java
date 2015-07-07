/**
* Nombre del Programa : InvolucradoPrint.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 25/08/2011
* Marca de cambio        : N/A
* Descripcion General    : Esta clase tiene como objetivo imprimir lo datos de un involucrado 
* 							en entidad, DTO y WSDTO, para Involucrado, InvolucradoDTO  y InvolucradoWSDTO.
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
package mx.gob.segob.nsjp.services.dtos.print;

import java.util.List;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.dto.domicilio.MunicipioDTO;
import mx.gob.segob.nsjp.dto.involucrado.AliasInvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.DetencionDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.MediaFiliacionDTO;
import mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO;
import mx.gob.segob.nsjp.dto.persona.CorreoElectronicoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.persona.TelefonoDTO;
import mx.gob.segob.nsjp.ws.cliente.enviarcarpetainvestigacion.CorreoElectronicoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.enviarcarpetainvestigacion.DetencionWSDTO;
import mx.gob.segob.nsjp.ws.cliente.enviarcarpetainvestigacion.DomicilioWSDTO;
import mx.gob.segob.nsjp.ws.cliente.enviarcarpetainvestigacion.InvolucradoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.enviarcarpetainvestigacion.MediaFiliacionWSDTO;
import mx.gob.segob.nsjp.ws.cliente.enviarcarpetainvestigacion.NombreDemograficoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.enviarcarpetainvestigacion.OrganizacionWSDTO;
import mx.gob.segob.nsjp.ws.cliente.enviarcarpetainvestigacion.TelefonoWSDTO;

import org.apache.log4j.Logger;



/**
 * Esta clase tiene como objetivo imprimir lo datos de un involucrado 
 * en entidad, DTO y WSDTO, para Involucrado, InvolucradoDTO  y InvolucradoWSDTO.
 * 
 * Se hace uso de logger.info en vez de System.out.println con el objetivo de mandar 
 * a imprimir sin traza.  Se hace con un Find Replace.
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
public class InvolucradoPrint {

	private final static Logger logger = Logger.getLogger(InvolucradoPrint.class);

	/********************************* DTO *********************************/
	public static void imprimirDatosInvolucrado(InvolucradoDTO involucradoDTO){
		
		logger.info("********************************involucradoDTO********************************");
		
		if (involucradoDTO == null)
			return;
		
		logger.info(" InvolucradoDTO :: " + involucradoDTO);
		logger.info(" ElementoID: " + involucradoDTO.getElementoId());
		logger.info(" Condición: " + involucradoDTO.getCondicion());
		if(involucradoDTO.getInstitucionPresenta()!=null)
			logger.info(" InstitucionPresenta - ConfInstitucionId: " + involucradoDTO.getInstitucionPresenta().getConfInstitucionId());
		
		if(involucradoDTO.getExpedienteDTO()!= null)
			logger.info(" ExpedienteID: " + involucradoDTO.getExpedienteDTO().getExpedienteId());
		
		logger.info(" Calidad: " + involucradoDTO.getCalidadDTO());
		if(involucradoDTO.getCalidadDTO()!=null){
			logger.info(" Calidad: "+ involucradoDTO.getCalidadDTO().getValorIdCalidad());
			logger.info(" EstadoFisico: "+ involucradoDTO.getCalidadDTO().getDescripcionEstadoFisico());
			logger.info(" Calidades: "+ involucradoDTO.getCalidadDTO().getCalidades());
			
		}
		logger.info(" TipoPersona: "+involucradoDTO.getTipoPersona());
		logger.info(" SituacionJuridica: "+involucradoDTO.getValorSituacionJuridica());
		
		imprimirDatosGenerales(involucradoDTO);
		
		//FISICA
		if( involucradoDTO.getTipoPersona() == 1 ){
			logger.info("**********************FISICA**********************");
			if (involucradoDTO.getMediaFiliacionDTO()!= null ){
				imprimirMediaFilicacionDTO(involucradoDTO.getMediaFiliacionDTO());
			}
		}
		//MORAL
		if(involucradoDTO.getTipoPersona()==0){
			logger.info("**********************MORAL**********************");
			//Organizacion
			if (involucradoDTO.getOrganizacionDTO()!=null ){
				OrganizacionDTO organizacionDTO = involucradoDTO.getOrganizacionDTO();
				logger.info(" Organización:"+organizacionDTO.getElementoId());
				logger.info(" Nombre:"+organizacionDTO.getNombreOrganizacion());
				logger.info(" Domicilio:"+organizacionDTO.getDomicilioDTO());
				if( organizacionDTO.getDomicilioDTO()!= null ){
					DomicilioDTO domicilioDTO =  organizacionDTO.getDomicilioDTO();
					logger.info(" Descripcion:"+domicilioDTO.getDescripcion());
					logger.info(" Calle:"+domicilioDTO.getCalle());
					logger.info(" NumeroExterior:"+domicilioDTO.getNumeroExterior());
					logger.info(" AsentamientoDTO:"+domicilioDTO.getAsentamientoDTO());
					logger.info(" EntidadDTO:"+domicilioDTO.getEntidadDTO());
					logger.info(" MunicipioDTO:"+domicilioDTO.getMunicipioDTO());
					logger.info(" CiudadDTO:"+domicilioDTO.getCiudadDTO());
					logger.info(" NumeroInterior:"+domicilioDTO.getNumeroInterior());
				}
				if(organizacionDTO.getRepresentanteLegal()!= null){
					InvolucradoDTO representanteLegal =organizacionDTO.getRepresentanteLegal();
					logger.info(" Representante Legal:"+representanteLegal.getElementoId());
					imprimirDatosInvolucrado(representanteLegal);
				}
			}
		}
		logger.info("**** DETENCIONES: "+involucradoDTO.getDetenciones());
		if (!involucradoDTO.getDetenciones().isEmpty()) {
			logger.info(" #Dtenciones "+involucradoDTO.getDetenciones().size());
			List<DetencionDTO> detenciones = involucradoDTO.getDetenciones();
			for (DetencionDTO detencionDTO : detenciones) {
				logger.info(" Detencion -  Detencion:" + detencionDTO.getDetencionId());
				logger.info(" Detencion -  MotivoDetencion:" + detencionDTO.getMotivoDetencion());
				logger.info(" Detencion -  Observaciones:" + detencionDTO.getObservaciones());
				logger.info(" Detencion -  LugarDetencion:" + detencionDTO.getLugarDetencionDTO());
				logger.info(" Detencion -  LugarDetencionProvicional:" + detencionDTO.getLugarDetencionProvicional());
				logger.info(" Detencion -  FechaInicioDetencion:" + detencionDTO.getFechaInicioDetencion());
				logger.info(" Detencion -  HoraInicioDetencion:" + detencionDTO.getHoraInicioDetencion());
				logger.info(" Detencion -  FechaRecepcionDetencion:" + detencionDTO.getFechaRecepcionDetencion());
				logger.info(" Detencion -  HoraRecepcionDetencion:" + detencionDTO.getHoraRecepcionDetencion());
				logger.info(" Detencion -  FechaFinDetencion:" + detencionDTO.getFechaFinDetencion());
				logger.info(" Detencion -  HoraFinDetencion:" + detencionDTO.getHoraFinDetencion());
				logger.info(" Detencion -  AvisoDetencion:" + detencionDTO.getAvisoDetencion());
			}
		}
		logger.info("IdDetenidos:"+involucradoDTO.getIdsDetenidos());
		if (involucradoDTO.getIdsDetenidos() != null
				&& !involucradoDTO.getIdsDetenidos().isEmpty()) {
			for (Long idDetenido : involucradoDTO.getIdsDetenidos()) {
				logger.info("ID-Detenido:"+ idDetenido);
			}
		}
		logger.info("Foto del Elemento::" + involucradoDTO.getFotoDelElemento());
		if(involucradoDTO.getFotoDelElemento()!=null){
			logger.info("Foto del ArchivoDigitalId::" + involucradoDTO.getFotoDelElemento().getArchivoDigitalId());
			logger.info("Foto del NombreArchivo::" + involucradoDTO.getFotoDelElemento().getNombreArchivo());
			logger.info("Foto del TipoArchivo::" + involucradoDTO.getFotoDelElemento().getTipoArchivo());
			logger.info("Foto del Contenido::" + involucradoDTO.getFotoDelElemento().getContenido());
		}
	}
	
	public static void imprimirDatosGenerales(InvolucradoDTO involucradoDTO){
		if (involucradoDTO== null || involucradoDTO.getNombresDemograficoDTO()==null)
			return;
		logger.info("*******Información de Datos Generales: "+ involucradoDTO.getElementoId() + "*******");
		logger.info(" NombreCompleto:" + involucradoDTO.getNombreCompleto());
		
		List<NombreDemograficoDTO> lnombreDemograficoDTO = involucradoDTO.getNombresDemograficoDTO();
		logger.info(" Información de Nombre Demografico #: "+lnombreDemograficoDTO.size());  
		for (NombreDemograficoDTO nombreDemograficoDTO : lnombreDemograficoDTO) {
			logger.info(" Información de NombreDemograficoID: "+ nombreDemograficoDTO.getNombreDemograficoId());
			logger.info(" ApellidoMaterno:" + nombreDemograficoDTO.getApellidoMaterno());
			logger.info(" ApellidoPaterno:" + nombreDemograficoDTO.getApellidoPaterno());
			logger.info(" Curp:" + nombreDemograficoDTO.getCurp());			
			logger.info(" LugarNacimiento:" + nombreDemograficoDTO.getLugarNacimiento());
			logger.info(" Nombre:" + nombreDemograficoDTO.getNombre());
			logger.info(" NombreCompleto:" + nombreDemograficoDTO.getNombreCompleto());
			logger.info(" Rfc:" + nombreDemograficoDTO.getRfc());
			logger.info(" Sexo:" + nombreDemograficoDTO.getSexo());
			logger.info(" StrFechaNacimiento:" + nombreDemograficoDTO.getStrFechaNacimiento());
			logger.info(" EdadAproximada:" + nombreDemograficoDTO.getEdadAproximada());
			logger.info(" EntidadFederativaDTO:" + nombreDemograficoDTO.getEntidadFederativaDTO());
			imprimirEntidadFederativa(nombreDemograficoDTO.getEntidadFederativaDTO());
			logger.info(" EsVerdadero:" + nombreDemograficoDTO.getEsVerdadero());
			logger.info(" FechaNacimiento:" + nombreDemograficoDTO.getFechaNacimiento());
			logger.info(" MunicipioDTO:" + nombreDemograficoDTO.getMunicipioDTO());
			imprimirMunicipio(nombreDemograficoDTO.getMunicipioDTO());
			logger.info(" PaisValorDTO:" + nombreDemograficoDTO.getPaisValorDTO());
			logger.info(" Usuario:" + nombreDemograficoDTO.getUsuario());
		}
		
		logger.info(" ValorIdIdioma:" + involucradoDTO.getValorIdIdioma());
		logger.info(" ValorIdEscolaridad:" + involucradoDTO.getValorIdEscolaridad());
		logger.info(" ValorIdEstadoCivil:" + involucradoDTO.getValorIdEstadoCivil());
		logger.info(" ValorIdReligion:" + involucradoDTO.getValorIdReligion());
		logger.info(" ValorIdNacionalidad:" + involucradoDTO.getValorIdNacionalidad());
		logger.info(" ValorIdIdentificaion:" + involucradoDTO.getValorIdIdentificaion());
		
		logger.info(" OCUPACIONES: **"
				+ involucradoDTO.getValorIdOcupacion());
		if(involucradoDTO.getValorIdOcupacion()!=null && !involucradoDTO.getValorIdOcupacion().isEmpty()){
			List<ValorDTO> ocupaciones = involucradoDTO.getValorIdOcupacion();
			for (ValorDTO valorDTO : ocupaciones) {
				logger.info(" Ocupacion: " + valorDTO.getIdCampo() +"-"+ valorDTO.getValor() );
			}
		}
		
		logger.info(" NACIONALIDADES: **"
				+ involucradoDTO.getValorIdNacionalidad());
		if(involucradoDTO.getValorIdNacionalidad()!=null && !involucradoDTO.getValorIdNacionalidad().isEmpty()){
			List<ValorDTO> nacionalidades = involucradoDTO.getValorIdNacionalidad();
			for (ValorDTO valorDTO : nacionalidades) {
				logger.info(" nacionalidades: " + valorDTO.getIdCampo() +"-"+ valorDTO.getValor() );
			}
		}

		logger.info(" ALIAS: **"
				+ involucradoDTO.getAliasInvolucradoDTO());
		if(involucradoDTO.getAliasInvolucradoDTO()!=null && !involucradoDTO.getAliasInvolucradoDTO().isEmpty()){
			List<AliasInvolucradoDTO> alias = involucradoDTO.getAliasInvolucradoDTO();
			for (AliasInvolucradoDTO aliasDTO : alias) {
				logger.info(" alias: " + aliasDTO.getAliasInvolucradoId() +"-"+aliasDTO.getAlias());
			}
		}

		logger.info("Domicilio: "+involucradoDTO.getDomicilio());
		//Domicilio
		if( involucradoDTO.getDomicilio()!= null){
			DomicilioDTO domicilioDTO = involucradoDTO.getDomicilio();
			logger.info("Domicilio: "+domicilioDTO.getDescripcion());
			logger.info("Domicilio - NumeroExterior:"+domicilioDTO.getNumeroExterior());
			logger.info("Domicilio - AsentamientoDTO:"+domicilioDTO.getAsentamientoDTO());
			logger.info("Domicilio - EntidadDTO:"+domicilioDTO.getEntidadDTO());
			logger.info("Domicilio - MunicipioDTO:"+domicilioDTO.getMunicipioDTO());
			logger.info("Domicilio - CiudadDTO:"+domicilioDTO.getCiudadDTO());
			logger.info("Domicilio - NumeroInterior:"+domicilioDTO.getNumeroInterior());
			logger.info("Domicilio - Referencias: "+domicilioDTO.getReferencias());
			logger.info("Domicilio - Edificio: "+domicilioDTO.getEdificio());
			logger.info("Domicilio - Latitud: "+domicilioDTO.getLatitud());
			logger.info("Domicilio - Longitud: "+domicilioDTO.getLongitud());
		}
		
		logger.info("Domicilio Notificacion: "+involucradoDTO.getDomicilioNotificacion());
		//Domicilio Notificacion
		if( involucradoDTO.getDomicilioNotificacion()!= null){
			DomicilioDTO domicilioDTO = involucradoDTO.getDomicilioNotificacion();
			logger.info("Domicilio Notificacion - Descripcion: "+domicilioDTO.getDescripcion());
			logger.info("Domicilio Notificacion - NumeroExterior:"+domicilioDTO.getNumeroExterior());
			logger.info("Domicilio Notificacion - AsentamientoDTO:"+domicilioDTO.getAsentamientoDTO());
			logger.info("Domicilio Notificacion - EntidadDTO:"+domicilioDTO.getEntidadDTO());
			logger.info("Domicilio Notificacion - MunicipioDTO:"+domicilioDTO.getMunicipioDTO());
			logger.info("Domicilio Notificacion - CiudadDTO:"+domicilioDTO.getCiudadDTO());
			logger.info("Domicilio Notificacion - NumeroInterior:"+domicilioDTO.getNumeroInterior());
			logger.info("Domicilio Notificacion - Referencias: "+domicilioDTO.getReferencias());
			logger.info("Domicilio Notificacion - Edificio: "+domicilioDTO.getEdificio());
			logger.info("Domicilio Notificacion - Latitud: "+domicilioDTO.getLatitud());
			logger.info("Domicilio Notificacion - Longitud: "+domicilioDTO.getLongitud());
		}
		
		if( involucradoDTO.getTelefonosDTO()!= null){
			for (TelefonoDTO telefonoDTO : involucradoDTO.getTelefonosDTO()) {
				logger.info("Telefono:"+telefonoDTO.getNumeroTelefonico());
			}
		}
		logger.info(" delitosCometidos:" + involucradoDTO.getDelitosCometidos());
		if( involucradoDTO.getCorreosDTO()!= null){
			for (CorreoElectronicoDTO CorreoDTO : involucradoDTO.getCorreosDTO()) {
				logger.info("Correo:"+CorreoDTO.getDireccionElectronica());
			}
		}
	}
	
	public static void imprimirMediaFilicacionDTO(MediaFiliacionDTO mediaFiliacionDTO){ 
		if (mediaFiliacionDTO== null)
			return;
		
		logger.info(" Información de MediaFiliación: "+ mediaFiliacionDTO.getMediaFiliacionId());
		
		logger.info("getEstatura: "+mediaFiliacionDTO.getEstatura());
		logger.info("getPeso: "+mediaFiliacionDTO.getPeso());
		logger.info("getPerfil: "+mediaFiliacionDTO.getPerfil());
		logger.info("getTieneBarba: "+mediaFiliacionDTO.getTieneBarba());
		logger.info("getTieneBigote: "+mediaFiliacionDTO.getTieneBigote());
		logger.info("getUsaLentes: "+mediaFiliacionDTO.getUsaLentes());
		logger.info("getFactorRH: "+mediaFiliacionDTO.getFactorRH());
		
		if(mediaFiliacionDTO.getTipoSangre()!= null)
			logger.info("getTipoSangre: " + mediaFiliacionDTO.getTipoSangre().getValor() + "-" + mediaFiliacionDTO.getTipoSangre().getNombreCampo());
		
		if(mediaFiliacionDTO.getDorsoNariz()!= null)
			logger.info("getDorsoNariz: " + mediaFiliacionDTO.getDorsoNariz().getValor());
		
		if(mediaFiliacionDTO.getHelixSuperior()!= null)
			logger.info("getHelixSuperior: " + mediaFiliacionDTO.getHelixSuperior().getValor());

		if(mediaFiliacionDTO.getHelixOriginal()!= null)
			logger.info("getHelixOriginal: " + mediaFiliacionDTO.getHelixOriginal().getValor());
		if(mediaFiliacionDTO.getOrejaLobContorno() != null)
			logger.info("getOrejaLobContorno: " + mediaFiliacionDTO.getOrejaLobContorno().getValor());
		if(mediaFiliacionDTO.getComplexion()!= null )
			logger.info("getComplexion: " + mediaFiliacionDTO.getComplexion().getValor());
		
		//SeniaParticular
		if ( mediaFiliacionDTO.getSeniaParticularDTO()!= null){
			logger.info("seniaParticular:"+ mediaFiliacionDTO.getSeniaParticularDTO());
			logger.info("Cicatrices?:"+ mediaFiliacionDTO.getSeniaParticularDTO().getTieneCicatrices());
			logger.info("Cicatrices:"+ mediaFiliacionDTO.getSeniaParticularDTO().getDescripcionCicatrices());
			logger.info("DefectosFisicos?:"+ mediaFiliacionDTO.getSeniaParticularDTO().getTieneDefectosFisicos());
			logger.info("DefectosFisicos:"+ mediaFiliacionDTO.getSeniaParticularDTO().getDescripcionDefectosFisicos());
			logger.info("Tiene Lunares:"+ mediaFiliacionDTO.getSeniaParticularDTO().getTieneLunares());
			logger.info("Lunares:"+ mediaFiliacionDTO.getSeniaParticularDTO().getDescripcionLunares());
			logger.info("OtraSenia?:"+ mediaFiliacionDTO.getSeniaParticularDTO().getTieneOtraSenia());
			logger.info("OtraSenia:"+ mediaFiliacionDTO.getSeniaParticularDTO().getDescripcionOtraSenia());
			logger.info("Protesis?:"+ mediaFiliacionDTO.getSeniaParticularDTO().getTieneProtesis());
			logger.info("Protesis:"+ mediaFiliacionDTO.getSeniaParticularDTO().getDescripcionProtesis());
			logger.info("Tatuajes?:"+ mediaFiliacionDTO.getSeniaParticularDTO().getTieneTatuajes());
			logger.info("Tatuajes:"+ mediaFiliacionDTO.getSeniaParticularDTO().getDescripcionTatuajes());
		}
		
		
	}
	
	public static void imprimirEntidadFederativa(EntidadFederativaDTO entidadFederativaDTO){
		if (entidadFederativaDTO== null)
			return;
		logger.info(" Información de Entidad Federativa: "+ entidadFederativaDTO.getEntidadFederativaId());
		logger.info(" CzonaGeografica:" + entidadFederativaDTO.getCzonaGeografica());
		logger.info(" NombreEntidad:" + entidadFederativaDTO.getNombreEntidad());
		logger.info(" Abreviacion:" + entidadFederativaDTO.getAbreviacion());
		logger.info(" Usuario:" + entidadFederativaDTO.getUsuario());
		logger.info(" ValorIdPais:" + entidadFederativaDTO.getValorIdPais());
	}

	public static void imprimirMunicipio(MunicipioDTO municipioDTO){
		if (municipioDTO== null)
			return;
		logger.info(" Información de Municipio: "+ municipioDTO.getMunicipioId());
		logger.info(" NombreMunicipio:" + municipioDTO.getNombreMunicipio());
		logger.info(" EntidadFederativaDTO:" + municipioDTO.getEntidadFederativaDTO());
		logger.info(" Usuario:" + municipioDTO.getUsuario());
	}
	
	
	/********************************* WSDTO *********************************/
	public static void imprimirDatosInvolucrado(InvolucradoWSDTO involucradoWSDTO){
		
		logger.info("********************************involucradoWSDTO********************************");
		
		if (involucradoWSDTO == null)
			return;
		
		logger.info(" InvolucradoDTO :: " + involucradoWSDTO);
		logger.info(" ElementoID: N/A");
		logger.info(" InstitucionPresenta - ConfInstitucionId:" + involucradoWSDTO.getInstitucionPresenta());
		
//		if(involucradoWSDTO.getExpedienteDTO()!= null)
//			logger.info(" ExpedienteID: " + involucradoWSDTO.getExpedienteDTO().getExpedienteId());
		
		logger.info(" Calidad: " + involucradoWSDTO.getCalidad());
		if(involucradoWSDTO.getCalidad()!=null){
			logger.info(" Calidad: "+ involucradoWSDTO.getCalidad().getValorIdCalidad());
			logger.info(" EstadoFisico: "+ involucradoWSDTO.getCalidad().getDescripcionEstadoFisico());
			logger.info(" Calidades: "+ "N/A");
		}
		logger.info(" TipoPersona: "+involucradoWSDTO.getTipoPersona());
		logger.info(" SituacionJuridica: N/A");
		
		imprimirDatosGenerales(involucradoWSDTO);
		
		//FISICA
		if( involucradoWSDTO.getTipoPersona() == 1 ){
			logger.info("**********************FISICA**********************");
			if (involucradoWSDTO.getMediaFiliacionWSDTO()!= null ){
				imprimirMediaFilicacionDTO(involucradoWSDTO.getMediaFiliacionWSDTO());
			}
		}
		//MORAL
		if(involucradoWSDTO.getTipoPersona()==0){
			logger.info("**********************MORAL**********************");
			//Organizacion
			if (involucradoWSDTO.getOrganizacion()!=null ){
				OrganizacionWSDTO organizacionWSDTO = involucradoWSDTO.getOrganizacion();
				logger.info(" Organización: N/A");
				logger.info(" Nombre:"+organizacionWSDTO.getNombreOrganizacion());
				logger.info(" Domicilio:"+organizacionWSDTO.getDomicilioWSDTO());
				if( organizacionWSDTO.getDomicilioWSDTO()!= null ){
					DomicilioWSDTO domicilioWSDTO =  organizacionWSDTO.getDomicilioWSDTO();
					logger.info("Domicilio - Descripcion: "+domicilioWSDTO.getDescripcion());
					logger.info("Domicilio - NumeroExterior:"+domicilioWSDTO.getNumeroExterior());
					logger.info("Domicilio - AsentamientoWSDTO:"+domicilioWSDTO.getAsentamientoId());
					logger.info("Domicilio - EntidadWSDTO:"+domicilioWSDTO.getEntidadId());
					logger.info("Domicilio - MunicipioWSDTO:"+domicilioWSDTO.getMunicipioId());
					logger.info("Domicilio - CiudadWSDTO:"+domicilioWSDTO.getCiudadId());
					logger.info("Domicilio - NumeroInterior:"+domicilioWSDTO.getNumeroInterior());
					logger.info("Domicilio - Referencias: "+domicilioWSDTO.getReferencias());
					logger.info("Domicilio - Edificio: "+domicilioWSDTO.getEdificio());
					logger.info("Domicilio - Latitud: "+domicilioWSDTO.getLatitud());
					logger.info("Domicilio - Longitud: "+domicilioWSDTO.getLongitud());
				}
				if(organizacionWSDTO.getRepresentanteLegal()!= null){
					InvolucradoWSDTO representanteLegal =organizacionWSDTO.getRepresentanteLegal();
					logger.info(" Representante Legal: N/A");
					imprimirDatosInvolucrado(representanteLegal);
				}
			}
		}
		logger.info("**** DETENCIONES: "+involucradoWSDTO.getDetenciones());
		if (!involucradoWSDTO.getDetenciones().isEmpty()) {
			logger.info(" #Dtenciones "+involucradoWSDTO.getDetenciones().size());
			List<DetencionWSDTO> detenciones = involucradoWSDTO.getDetenciones();
			for (DetencionWSDTO DetencionWSDTO : detenciones) {
				//logger.info(" Detencion -  Detencion:" + DetencionWSDTO.getDetencionId());
				logger.info(" Detencion -  MotivoDetencion:" + DetencionWSDTO.getMotivoDetencion());
				logger.info(" Detencion -  Observaciones:" + DetencionWSDTO.getObservaciones());
				logger.info(" Detencion -  LugarDetencion:" + DetencionWSDTO.getLugarDetencionWSDTO());
				logger.info(" Detencion -  LugarDetencionProvicional:" + DetencionWSDTO.getLugarDetencionProvicional());
				logger.info(" Detencion -  FechaInicioDetencion:" + DetencionWSDTO.getFechaInicioDetencion());
				logger.info(" Detencion -  HoraInicioDetencion:" + DetencionWSDTO.getHoraInicioDetencion());
				logger.info(" Detencion -  FechaRecepcionDetencion:" + DetencionWSDTO.getFechaRecepcionDetencion());
				logger.info(" Detencion -  HoraRecepcionDetencion:" + DetencionWSDTO.getHoraRecepcionDetencion());
				logger.info(" Detencion -  FechaFinDetencion:" + DetencionWSDTO.getFechaFinDetencion());
				logger.info(" Detencion -  HoraFinDetencion:" + DetencionWSDTO.getHoraFinDetencion());
//				logger.info(" Detencion -  AvisoDetencion:" + DetencionWSDTO.getAvisoDetencion());
			}
		}
		logger.info("IdDetenidos:"+involucradoWSDTO.getIdsDetenidos());
		if (involucradoWSDTO.getIdsDetenidos() != null
				&& !involucradoWSDTO.getIdsDetenidos().isEmpty()) {
			for (Long idDetenido : involucradoWSDTO.getIdsDetenidos()) {
				logger.info("ID-Detenido:"+ idDetenido);
			}
		}
		logger.info("Foto del Elemento::" + involucradoWSDTO.getFotoDelElemento());
		if(involucradoWSDTO.getFotoDelElemento()!=null){
			logger.info("Foto del ConfInstitucionId::" + involucradoWSDTO.getFotoDelElemento().getConfInstitucionId());
			logger.info("Foto del NombreArchivo::" + involucradoWSDTO.getFotoDelElemento().getNombreArchivo());
			logger.info("Foto del TipoArchivo::" + involucradoWSDTO.getFotoDelElemento().getTipoArchivo());
			logger.info("Foto del Contenido::" + involucradoWSDTO.getFotoDelElemento().getContenido());
		}
	}
	
	public static void imprimirDatosGenerales(InvolucradoWSDTO involucradoWSDTO){
		if (involucradoWSDTO== null || involucradoWSDTO.getNombresDemograficos()==null)
			return;
		logger.info("*******Información de Datos Generales: N/A *******");
		logger.info(" NombreCompleto: N/A");
		
		List<NombreDemograficoWSDTO> lnombreDemograficoWSDTO = involucradoWSDTO.getNombresDemograficos();
		logger.info(" Información de Nombre Demografico #: "+lnombreDemograficoWSDTO.size());  
		for (NombreDemograficoWSDTO nombreDemograficoWSDTO : lnombreDemograficoWSDTO) {
			logger.info(" Información de NombreDemograficoID: "+ nombreDemograficoWSDTO.getNombreDemograficoId());
			logger.info(" ApellidoMaterno:" + nombreDemograficoWSDTO.getApellidoMaterno());
			logger.info(" ApellidoPaterno:" + nombreDemograficoWSDTO.getApellidoPaterno());
			logger.info(" Curp:" + nombreDemograficoWSDTO.getCurp());			
			logger.info(" LugarNacimiento:" + nombreDemograficoWSDTO.getLugarNacimiento());
			logger.info(" Nombre:" + nombreDemograficoWSDTO.getNombre());
			logger.info(" NombreCompleto: N/A");
			logger.info(" Rfc:" + nombreDemograficoWSDTO.getRfc());
			logger.info(" Sexo:" + nombreDemograficoWSDTO.getSexo());
			logger.info(" StrFechaNacimiento:" + nombreDemograficoWSDTO.getStrFechaNacimiento());
			logger.info(" EdadAproximada:" + nombreDemograficoWSDTO.getEdadAproximada());
			logger.info(" EntidadFederativaDTO:" + nombreDemograficoWSDTO.getEntidadFederativaId());
//			imprimirEntidadFederativa(nombreDemograficoWSDTO.getEntidadFederativaDTO());
			logger.info(" EsVerdadero: N/A");
			logger.info(" FechaNacimiento:" + nombreDemograficoWSDTO.getFechaNacimiento());
			logger.info(" MunicipioDTO:" + nombreDemograficoWSDTO.getMunicipioId());
//			imprimirMunicipio(nombreDemograficoWSDTO.getMunicipioDTO());
			logger.info(" PaisValorDTO:" + nombreDemograficoWSDTO.getPaisValorId());
			logger.info(" Usuario: N/A");
		}
		
		logger.info(" ValorIdIdioma:" + involucradoWSDTO.getValorIdIdioma());
		logger.info(" ValorIdEscolaridad:" + involucradoWSDTO.getValorIdEscolaridad());
		logger.info(" ValorIdEstadoCivil:" + involucradoWSDTO.getValorIdEstadoCivil());
		logger.info(" ValorIdReligion:" + involucradoWSDTO.getValorIdReligion());
		logger.info(" ValorIdNacionalidad:" + involucradoWSDTO.getValorIdNacionalidad());
		logger.info(" ValorIdIdentificaion:" + involucradoWSDTO.getValorIdIdentificaion());
		
		logger.info(" OCUPACIONES: **"
				+ involucradoWSDTO.getValorIdOcupacion());
		if(involucradoWSDTO.getValorIdOcupacion()!=null && !involucradoWSDTO.getValorIdOcupacion().isEmpty()){
			List<Long> ocupaciones = involucradoWSDTO.getValorIdOcupacion();
			for (Long valor : ocupaciones) {
				logger.info(" Ocupacion: " + valor);
			}
		}
		
		logger.info(" NACIONALIDADES: **"
				+ involucradoWSDTO.getValorIdNacionalidad());
		if(involucradoWSDTO.getValorIdNacionalidad()!=null && !involucradoWSDTO.getValorIdNacionalidad().isEmpty()){
			List<Long> nacionalidades = involucradoWSDTO.getValorIdNacionalidad();
			for (Long valor : nacionalidades) {
				logger.info(" nacionalidades: " + valor);
			}
		}
		logger.info(" ALIAS: **"
				+ involucradoWSDTO.getAliasInvolucrado());
		if(involucradoWSDTO.getAliasInvolucrado()!=null && !involucradoWSDTO.getAliasInvolucrado().isEmpty()){
			List<String> alias = involucradoWSDTO.getAliasInvolucrado();
			for (String alia : alias) {
				logger.info(" alias: " + alia);
			}
		}
		
		if( involucradoWSDTO.getValorIdNacionalidad() !=null )
			for (Long valorWSDTO : involucradoWSDTO.getValorIdNacionalidad()) {
				logger.info(" ValorIdNacionalidad:" + valorWSDTO);
			}
		
		logger.info(" ValorIdOcupacion:" + involucradoWSDTO.getValorIdOcupacion());
		if( involucradoWSDTO.getValorIdOcupacion()!=null)
			for (Long valorWSDTO : involucradoWSDTO.getValorIdOcupacion()) {
				logger.info(" ValorIdOcupacion:" + valorWSDTO);	
			}
		
		logger.info(" AliasInvolucrado:" + involucradoWSDTO.getAliasInvolucrado());
		if(involucradoWSDTO.getAliasInvolucrado()!=null)
		for (String aliasInvolucrado : involucradoWSDTO.getAliasInvolucrado()) {
			logger.info(" AliasInvolucrado:" + aliasInvolucrado);	
		}

		logger.info("Domicilio: "+involucradoWSDTO.getDomicilio());
		//Domicilio
		if( involucradoWSDTO.getDomicilio()!= null){
			DomicilioWSDTO domicilioWSDTO = involucradoWSDTO.getDomicilio();
			logger.info("Domicilio - Descripcion: "+domicilioWSDTO.getDescripcion());
			logger.info("Domicilio - NumeroExterior:"+domicilioWSDTO.getNumeroExterior());
			logger.info("Domicilio - AsentamientoDTO:"+domicilioWSDTO.getAsentamientoId());
			logger.info("Domicilio - EntidadDTO:"+domicilioWSDTO.getEntidadId());
			logger.info("Domicilio - MunicipioDTO:"+domicilioWSDTO.getMunicipioId());
			logger.info("Domicilio - CiudadDTO:"+domicilioWSDTO.getCiudadId());
			logger.info("Domicilio - NumeroInterior:"+domicilioWSDTO.getNumeroInterior());
			logger.info("Domicilio - Referencias: "+domicilioWSDTO.getReferencias());
			logger.info("Domicilio - Edificio: "+domicilioWSDTO.getEdificio());
			logger.info("Domicilio - Latitud: "+domicilioWSDTO.getLatitud());
			logger.info("Domicilio - Longitud: "+domicilioWSDTO.getLongitud());
		}
		
		logger.info("Domicilio Notificacion: "+involucradoWSDTO.getDomicilioNotificacion());
		//Domicilio Notificacion
		if( involucradoWSDTO.getDomicilioNotificacion()!= null){
			DomicilioWSDTO domicilioWSDTO = involucradoWSDTO.getDomicilioNotificacion();
			logger.info("Domicilio Notificacion - Descripcion: "+domicilioWSDTO.getDescripcion());
			logger.info("Domicilio Notificacion - NumeroExterior:"+domicilioWSDTO.getNumeroExterior());
			logger.info("Domicilio Notificacion - AsentamientoDTO:"+domicilioWSDTO.getAsentamientoId());
			logger.info("Domicilio Notificacion - EntidadDTO:"+domicilioWSDTO.getEntidadId());
			logger.info("Domicilio Notificacion - MunicipioDTO:"+domicilioWSDTO.getMunicipioId());
			logger.info("Domicilio Notificacion - CiudadDTO:"+domicilioWSDTO.getCiudadId());
			logger.info("Domicilio Notificacion - NumeroInterior:"+domicilioWSDTO.getNumeroInterior());
			logger.info("Domicilio Notificacion - Referencias: "+domicilioWSDTO.getReferencias());
			logger.info("Domicilio Notificacion - Edificio: "+domicilioWSDTO.getEdificio());
			logger.info("Domicilio Notificacion - Latitud: "+domicilioWSDTO.getLatitud());
			logger.info("Domicilio Notificacion - Longitud: "+domicilioWSDTO.getLongitud());
		}
		
		if( involucradoWSDTO.getTelefonos()!= null){
			for (TelefonoWSDTO telefonoWSDTO : involucradoWSDTO.getTelefonos()) {
				logger.info("Telefono:"+telefonoWSDTO.getNumeroTelefonico());
			}
		}
		if( involucradoWSDTO.getCorreos()!= null){
			for (CorreoElectronicoWSDTO CorreoWSDTO : involucradoWSDTO.getCorreos()) {
				logger.info("Correo:"+CorreoWSDTO.getDireccionElectronica());
			}
		}
	}
	
	public static void imprimirMediaFilicacionDTO(MediaFiliacionWSDTO mediaFiliacionWSDTO){ 
		if (mediaFiliacionWSDTO== null)
			return;
		
		logger.info(" Información de MediaFiliación: N/A");
		
		logger.info("getEstatura: "+mediaFiliacionWSDTO.getEstatura());
		logger.info("getPeso: "+mediaFiliacionWSDTO.getPeso());
		logger.info("getPerfil: "+mediaFiliacionWSDTO.getPerfil());
		logger.info("getTieneBarba: N/A");
		logger.info("getTieneBigote: N/A");
		logger.info("getUsaLentes: N/A");
		logger.info("getFactorRH: "+mediaFiliacionWSDTO.getFactorRH());
		
		if(mediaFiliacionWSDTO.getTipoSangre()!= null)
			logger.info("getTipoSangre: " + mediaFiliacionWSDTO.getTipoSangre());
		
		if(mediaFiliacionWSDTO.getDorsoNariz()!= null)
			logger.info("getDorsoNariz: " + mediaFiliacionWSDTO.getDorsoNariz());
		
		if(mediaFiliacionWSDTO.getHelixSuperior()!= null)
			logger.info("getHelixSuperior: " + mediaFiliacionWSDTO.getHelixSuperior());

		if(mediaFiliacionWSDTO.getHelixOriginal()!= null)
			logger.info("getHelixOriginal: " + mediaFiliacionWSDTO.getHelixOriginal());
		if(mediaFiliacionWSDTO.getOrejaLobContorno() != null)
			logger.info("getOrejaLobContorno: " + mediaFiliacionWSDTO.getOrejaLobContorno());
		if(mediaFiliacionWSDTO.getComplexion()!= null )
			logger.info("getComplexion: " + mediaFiliacionWSDTO.getComplexion());
		
		//SeniaParticular
		if ( mediaFiliacionWSDTO.getSeniaParticular()!= null){
			logger.info("seniaParticular:"+ mediaFiliacionWSDTO.getSeniaParticular());
			logger.info("Cicatrices?:N/A");
			logger.info("Cicatrices:"+ mediaFiliacionWSDTO.getSeniaParticular().getDescripcionCicatrices());
			logger.info("DefectosFisicos? N/A:");
			logger.info("DefectosFisicos:"+ mediaFiliacionWSDTO.getSeniaParticular().getDescripcionDefectosFisicos());
			logger.info("Tiene Lunares: N/A");
			logger.info("Lunares:"+ mediaFiliacionWSDTO.getSeniaParticular().getDescripcionLunares());
			logger.info("OtraSenia?: N/A");
			logger.info("OtraSenia:"+ mediaFiliacionWSDTO.getSeniaParticular().getDescripcionOtraSenia());
			logger.info("Protesis?: N/A");
			logger.info("Protesis:"+ mediaFiliacionWSDTO.getSeniaParticular().getDescripcionProtesis());
			logger.info("Tatuajes?: N/A");
			logger.info("Tatuajes:"+ mediaFiliacionWSDTO.getSeniaParticular().getDescripcionTatuajes());
		}
	}
}
