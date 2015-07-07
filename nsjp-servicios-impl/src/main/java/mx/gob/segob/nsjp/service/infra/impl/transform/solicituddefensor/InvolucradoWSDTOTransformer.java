/**
* Nombre del Programa : InvolucradoWSDTOTransformer.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 05/11/2012
* Marca de cambio        : N/A
* Descripcion General    : Clase de transformaci&oacute;n para los Involucrados asociados al expediente.Transforma de un Involucrado WSDTO a un Involucrado DTO y viceversa.
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

package mx.gob.segob.nsjp.service.infra.impl.transform.solicituddefensor;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.domicilio.AsentamientoDTO;
import mx.gob.segob.nsjp.dto.domicilio.CiudadDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.dto.domicilio.LugarDTO;
import mx.gob.segob.nsjp.dto.domicilio.MunicipioDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.involucrado.AliasInvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.DetencionDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.MediaFiliacionDTO;
import mx.gob.segob.nsjp.dto.involucrado.SeniaParticularDTO;
import mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO;
import mx.gob.segob.nsjp.dto.persona.CorreoElectronicoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.persona.TelefonoDTO;
import mx.gob.segob.nsjp.service.infra.impl.transform.WsTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviarcarpetainvestigacion.ExpedienteWSDTOTransformer;
import mx.gob.segob.nsjp.ws.cliente.solicituddefensor.CalidadWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicituddefensor.CorreoElectronicoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicituddefensor.DelitoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicituddefensor.DetencionWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicituddefensor.DomicilioWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicituddefensor.InvolucradoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicituddefensor.LugarWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicituddefensor.MediaFiliacionWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicituddefensor.NombreDemograficoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicituddefensor.OrganizacionWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicituddefensor.SeniaParticularWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicituddefensor.TelefonoWSDTO;

import org.apache.log4j.Logger;

/**
 * Clase de transformaci&oacute;n para los Involucrados asociados al expediente.
 * Transforma de un Involucrado WSDTO a un Involucrado DTO y viceversa.
 * 
 * Es utilizado para SolicitudDefensor
 * 
 * @author GustavoBP
 *
 */
public class InvolucradoWSDTOTransformer {
	public final static Logger logger = Logger.getLogger(InvolucradoWSDTOTransformer.class);
	
	private static final Long  ES_VIVO = 1L;
	private static final Long  ES_MUERTO = 0L;

	public static InvolucradoDTO involucradoWsdto2InvolucradoDto(mx.gob.segob.nsjp.dto.involucrado.InvolucradoWSDTO involucradoWSDTO){
		InvolucradoDTO involucradoDto = transforma(involucradoWSDTO);
		return involucradoDto;
	}

    public static InvolucradoDTO transforma(mx.gob.segob.nsjp.dto.involucrado.InvolucradoWSDTO involucradoWSDTO){
        InvolucradoDTO involucradoDTO = new InvolucradoDTO();
        ExpedienteWSDTOTransformer.transforma(involucradoWSDTO, involucradoDTO);
        
        if(involucradoWSDTO.getInstitucionPresenta()!= null)
        	involucradoDTO.setInstitucionPresenta(new ConfInstitucionDTO(involucradoWSDTO.getInstitucionPresenta()));
        
		involucradoDTO.setValorIdIdentificaion(new ValorDTO(involucradoWSDTO.getValorIdIdentificaion()));
		involucradoDTO.setValorIdIdioma(new ValorDTO(involucradoWSDTO.getValorIdIdioma()));
		involucradoDTO.setValorIdEscolaridad(new ValorDTO(involucradoWSDTO.getValorIdEscolaridad()));
		involucradoDTO.setValorIdEstadoCivil(new ValorDTO(involucradoWSDTO.getValorIdEstadoCivil()));
		involucradoDTO.setValorIdReligion(new ValorDTO(involucradoWSDTO.getValorIdReligion()));
		if (involucradoWSDTO.getEsVivo() != null && involucradoWSDTO.getEsVivo().equals(ES_VIVO)) {
			involucradoDTO.setEsVivo(true);
		} else {
			involucradoDTO.setEsVivo(false);
		}
		
		if( involucradoWSDTO.getNombresDemograficos()!= null && !involucradoWSDTO.getNombresDemograficos().isEmpty()){
			List<NombreDemograficoDTO> nombreDemograficosDTO = new ArrayList<NombreDemograficoDTO>();
        	for (mx.gob.segob.nsjp.dto.persona.NombreDemograficoWSDTO nombreDemograficosWSDTO : involucradoWSDTO.getNombresDemograficos()) {
        		nombreDemograficosDTO.add(transforma (nombreDemograficosWSDTO));
			}
        	involucradoDTO.setNombresDemograficoDTO(nombreDemograficosDTO);
		}
		
        if(involucradoWSDTO.getValorIdNacionalidad()!= null && !involucradoWSDTO.getValorIdNacionalidad().isEmpty()){
        	List<ValorDTO> nacionalidades = new ArrayList<ValorDTO>();
        	for (Long idCampo : involucradoWSDTO.getValorIdNacionalidad()) {
        		nacionalidades.add( new ValorDTO(idCampo));
			}
        	involucradoDTO.setValorIdNacionalidad(nacionalidades);
        }
        
        if(involucradoWSDTO.getValorIdOcupacion()!= null && !involucradoWSDTO.getValorIdOcupacion().isEmpty()){
        	List<ValorDTO> ocupaciones = new ArrayList<ValorDTO>();
        	for (Long idCampo : involucradoWSDTO.getValorIdOcupacion()) {
        		ocupaciones.add( new ValorDTO(idCampo));
			}
        	involucradoDTO.setValorIdOcupacion(ocupaciones);
        }
        
        if(involucradoWSDTO.getAliasInvolucrado()!= null && !involucradoWSDTO.getAliasInvolucrado().isEmpty()){
        	List<AliasInvolucradoDTO> aliasInvolucradoDTO = new ArrayList<AliasInvolucradoDTO>();
        	for (String alias :  involucradoWSDTO.getAliasInvolucrado()) {
        		AliasInvolucradoDTO aliasDTO= new AliasInvolucradoDTO();
        		aliasDTO.setAlias(alias);
        		aliasInvolucradoDTO.add(aliasDTO);
			}
        	involucradoDTO.setAliasInvolucradoDTO(aliasInvolucradoDTO);
        }
        
        if(involucradoWSDTO.getTelefonos()!= null && !involucradoWSDTO.getTelefonos().isEmpty()){
        	List<TelefonoDTO> telefonos = new ArrayList<TelefonoDTO>();
        	for (mx.gob.segob.nsjp.dto.persona.TelefonoWSDTO telefonoWSDTO : involucradoWSDTO.getTelefonos()) {
        		telefonos.add(transformar(telefonoWSDTO));
			}
        	involucradoDTO.setTelefonosDTO(telefonos);
        }
        
        if(involucradoWSDTO.getCorreos()!= null && !involucradoWSDTO.getCorreos().isEmpty()){
        	List<CorreoElectronicoDTO> correos = new ArrayList<CorreoElectronicoDTO>(); 
        	for (mx.gob.segob.nsjp.dto.persona.CorreoElectronicoWSDTO correosWSDTO : involucradoWSDTO.getCorreos()) {
        		correos.add(transformar(correosWSDTO));
			}
        	involucradoDTO.setCorreosDTO(correos);
        }
		   
        mx.gob.segob.nsjp.dto.elemento.CalidadWSDTO calidadWsDto = involucradoWSDTO.getCalidad();
        if (calidadWsDto != null) {
            involucradoDTO.setCalidadDTO(transforma(calidadWsDto));
        }
        mx.gob.segob.nsjp.dto.domicilio.DomicilioWSDTO domicilioWsdto = involucradoWSDTO.getDomicilio();
        if (domicilioWsdto != null) {
            involucradoDTO.setDomicilio(transforma(domicilioWsdto));
        }
        mx.gob.segob.nsjp.dto.domicilio.DomicilioWSDTO domicilioNotificacionWsdto = involucradoWSDTO.getDomicilioNotificacion();
        if (domicilioNotificacionWsdto != null) {
            involucradoDTO.setDomicilioNotificacion(transforma(domicilioNotificacionWsdto));
        }
        mx.gob.segob.nsjp.dto.involucrado.MediaFiliacionWSDTO mediaFiliacionWSDTO = involucradoWSDTO.getMediaFiliacionWSDTO();
        if (mediaFiliacionWSDTO != null) {
            involucradoDTO.setMediaFiliacionDTO(transforma(mediaFiliacionWSDTO));
        }
        mx.gob.segob.nsjp.dto.organizacion.OrganizacionWSDTO organizacionWSDTO = involucradoWSDTO.getOrganizacion();
        if (organizacionWSDTO != null) {
            involucradoDTO.setOrganizacionDTO(transforma(organizacionWSDTO));
        }
        
		if (involucradoWSDTO.getDetenciones() != null
				&& !involucradoWSDTO.getDetenciones().isEmpty()) {
			List<DetencionDTO> detencionesDTO = new ArrayList<DetencionDTO>();
			for (mx.gob.segob.nsjp.dto.involucrado.DetencionWSDTO detencionWSDTO : involucradoWSDTO
					.getDetenciones()) {
				detencionesDTO.add(InvolucradoWSDTOTransformer
						.transforma(detencionWSDTO));
			}
			involucradoDTO.setDetenciones(detencionesDTO);
		}
        return involucradoDTO;
    }

	public static DetencionDTO transforma(
			mx.gob.segob.nsjp.dto.involucrado.DetencionWSDTO detencionWSDTO) {
		if (detencionWSDTO == null) {
			return null;
		}
		DetencionDTO detencionDTO = new DetencionDTO();

		if (detencionWSDTO.getFechaFinDetencion() != null) {
			detencionDTO.setFechaFinDetencion(detencionWSDTO
					.getFechaFinDetencion());
		}

		if (detencionWSDTO.getFechaInicioDetencion() != null) {
			detencionDTO.setFechaInicioDetencion(detencionWSDTO
					.getFechaInicioDetencion());
		}
		if (detencionWSDTO.getFechaRecepcionDetencion() != null) {
			detencionDTO.setFechaRecepcionDetencion(detencionWSDTO
					.getFechaRecepcionDetencion());
		}
		detencionDTO.setMotivoDetencion(detencionWSDTO.getMotivoDetencion());
		detencionDTO.setObservaciones(detencionWSDTO.getObservaciones());
		detencionDTO.setLugarDetencionProvicional(detencionWSDTO
				.getLugarDetencionProvicional());

		if (detencionWSDTO.getLugarDetencion() != null) {
			detencionDTO.setLugarDetencionDTO(InvolucradoWSDTOTransformer
					.transforma(detencionWSDTO.getLugarDetencion()));
		}
		return detencionDTO;
	}

	public static LugarDTO transforma(
			mx.gob.segob.nsjp.dto.domicilio.LugarWSDTO lugarWSDTO) {
		if (lugarWSDTO == null) {
			return null;
		}

		LugarDTO lugarDTO = new LugarDTO();
		// Tranformar datos primitivos que tienen el mismo nombre del metodo
		ExpedienteWSDTOTransformer.transforma(lugarWSDTO, lugarDTO);

		lugarDTO.setDescripcion(lugarWSDTO.getDescripcion());

		if (lugarWSDTO.getValorIdElemento() != null
				&& lugarWSDTO.getValorIdElemento() > 0)
			lugarDTO.setValorIdElemento(new ValorDTO(lugarWSDTO
					.getValorIdElemento()));
		return lugarDTO;
	}
    
    public static OrganizacionDTO transforma(mx.gob.segob.nsjp.dto.organizacion.OrganizacionWSDTO organizacionWSDTO){
        OrganizacionDTO organizacionDTO = new OrganizacionDTO();
        ExpedienteWSDTOTransformer.transforma(organizacionWSDTO, organizacionDTO);
        
        organizacionDTO.setFechaCreacionElemento(new Date());
        mx.gob.segob.nsjp.dto.elemento.CalidadWSDTO calidadWsdto = organizacionWSDTO.getCalidad();
        if (calidadWsdto != null) {
            organizacionDTO.setCalidadDTO(transforma(calidadWsdto));
        }
        mx.gob.segob.nsjp.dto.involucrado.InvolucradoWSDTO representanteLegalWsdto = organizacionWSDTO.getRepresentanteLegal();
        if(representanteLegalWsdto != null){
            organizacionDTO.setRepresentanteLegal(transforma(representanteLegalWsdto));
        }
        Long valorByComunidadVirtualValId = organizacionWSDTO.getValorByComunidadVirtualVal();
        if (valorByComunidadVirtualValId != null) {
            organizacionDTO.setValorByComunidadVirtualVal(new ValorDTO(valorByComunidadVirtualValId));
        }
        Long valorByOrganizacionFormalValId = organizacionWSDTO.getValorByOrganizacionFormalVal();
        if (valorByOrganizacionFormalValId != null) {
            organizacionDTO.setValorByOrganizacionFormalVal(new ValorDTO(valorByOrganizacionFormalValId));
        }
        Long valorBySectorGubernamentalValId = organizacionWSDTO.getValorBySectorGubernamentalVal();
        if (valorBySectorGubernamentalValId != null) {
            organizacionDTO.setValorBySectorGubernamentalVal(new ValorDTO(valorBySectorGubernamentalValId));
        }
        Long valorByTipoOrganizacionValId = organizacionWSDTO.getValorByTipoOrganizacionVal();
        if (valorByTipoOrganizacionValId != null) {
            organizacionDTO.setValorByTipoOrganizacionVal(new ValorDTO(valorByTipoOrganizacionValId));
        }
        return organizacionDTO;
    }



    public static MediaFiliacionDTO transforma(mx.gob.segob.nsjp.dto.involucrado.MediaFiliacionWSDTO mediaFiliacionWSDTO){
        MediaFiliacionDTO mediaFiliacionDTO = new MediaFiliacionDTO();
        ExpedienteWSDTOTransformer.transforma(mediaFiliacionWSDTO, mediaFiliacionDTO);
        mx.gob.segob.nsjp.dto.involucrado.SeniaParticularWSDTO seniaParticularWsdto = mediaFiliacionWSDTO.getSeniaParticular();
        if (seniaParticularWsdto != null) {
            mediaFiliacionDTO.setSeniaParticularDTO(transforma(seniaParticularWsdto));
        }
        return mediaFiliacionDTO;
    }

    public static SeniaParticularDTO transforma(mx.gob.segob.nsjp.dto.involucrado.SeniaParticularWSDTO seniaParticularWSDTO){
        SeniaParticularDTO seniaParticularDTO = new SeniaParticularDTO();
        ExpedienteWSDTOTransformer.transforma(seniaParticularWSDTO, seniaParticularDTO);
        return seniaParticularDTO;
    }

    public static DomicilioDTO transforma(mx.gob.segob.nsjp.dto.domicilio.DomicilioWSDTO domicilioWSDTO){
        DomicilioDTO domicilioDto = new DomicilioDTO();
        ExpedienteWSDTOTransformer.transforma(domicilioWSDTO, domicilioDto);
        mx.gob.segob.nsjp.dto.elemento.CalidadWSDTO calidadWsdto = domicilioWSDTO.getCalidad();
        if (calidadWsdto != null) {
            domicilioDto.setCalidadDTO(transforma(calidadWsdto));
        }
        if( domicilioWSDTO.getAsentamientoId()!= null)
        	domicilioDto.setAsentamientoDTO(new AsentamientoDTO(domicilioWSDTO.getAsentamientoId()));
        if(domicilioWSDTO.getEntidadId() != null )
        	domicilioDto.setEntidadDTO(new EntidadFederativaDTO(domicilioWSDTO.getEntidadId()));
        if(domicilioWSDTO.getMunicipioId()!= null )
        	domicilioDto.setMunicipioDTO(new MunicipioDTO(domicilioWSDTO.getMunicipioId()));
        if(domicilioWSDTO.getCiudadId()!= null )
        	domicilioDto.setCiudadDTO(new CiudadDTO(domicilioWSDTO.getCiudadId()));
        if(domicilioWSDTO.getValorCalleId()!= null )
        	domicilioDto.setValorCalleId(new ValorDTO(domicilioWSDTO.getValorCalleId()));
        
        return domicilioDto;
    }

    public static NombreDemograficoDTO transforma(mx.gob.segob.nsjp.dto.persona.NombreDemograficoWSDTO nomDemograficoWSDTO) {
    	NombreDemograficoDTO nomDemograficoDto = new  NombreDemograficoDTO();
		
		nomDemograficoDto.setNombre(nomDemograficoWSDTO.getNombre());
		nomDemograficoDto.setApellidoPaterno(nomDemograficoWSDTO.getApellidoPaterno());
		nomDemograficoDto.setApellidoMaterno(nomDemograficoWSDTO.getApellidoMaterno());
		nomDemograficoDto.setCurp(nomDemograficoWSDTO.getCurp());
		nomDemograficoDto.setRfc(nomDemograficoWSDTO.getRfc());
		nomDemograficoDto.setFechaNacimiento(nomDemograficoWSDTO.getFechaNacimiento());
		nomDemograficoDto.setLugarNacimiento(nomDemograficoWSDTO.getLugarNacimiento());
		nomDemograficoDto.setEdadAproximada(nomDemograficoWSDTO.getEdadAproximada());
		nomDemograficoDto.setEsVerdadero(nomDemograficoWSDTO.getEsVerdadero());
		nomDemograficoDto.setSexo(nomDemograficoWSDTO.getSexo());
		
		if (nomDemograficoWSDTO.getPaisValorId()!=null)
			nomDemograficoDto.setPaisValorDTO(new ValorDTO(nomDemograficoWSDTO.getPaisValorId()));
		if (nomDemograficoWSDTO.getMunicipioId()!=null)
			nomDemograficoDto.setMunicipioDTO(new MunicipioDTO(nomDemograficoWSDTO.getMunicipioId()));
		if (nomDemograficoWSDTO.getEntidadFederativaId()!=null)
			nomDemograficoDto.setEntidadFederativaDTO(new EntidadFederativaDTO( nomDemograficoWSDTO.getEntidadFederativaId()));
		return nomDemograficoDto;
	}

    public static CalidadDTO transforma(mx.gob.segob.nsjp.dto.elemento.CalidadWSDTO calidadWSDTO){
        CalidadDTO calidadDto = new CalidadDTO();
        ExpedienteWSDTOTransformer.transforma(calidadWSDTO, calidadDto);
        if (calidadWSDTO.getValorIdCalidad()!= null){
        	calidadDto.setValorIdCalidad(new ValorDTO(calidadWSDTO.getValorIdCalidad()));
			calidadDto.setCalidades(Calidades.getByValor(calidadWSDTO.getValorIdCalidad()));
        }
        return calidadDto;
    }


    public static TelefonoDTO transformar (mx.gob.segob.nsjp.dto.persona.TelefonoWSDTO telefonoWSDTO) {
		TelefonoDTO telefonoDTO = new TelefonoDTO();
		
		telefonoDTO.setNumeroTelefonico(telefonoWSDTO.getNumeroTelefonico());
		telefonoDTO.setCodigoPais(telefonoWSDTO.getCodigoPais());
		telefonoDTO.setCodigoArea(telefonoWSDTO.getCodigoArea());
		
		if (telefonoWSDTO.getValorTipoTelefonoId()!=null)
			telefonoDTO.setValorTipoTelefono(new ValorDTO( telefonoWSDTO.getValorTipoTelefonoId()));
		
		return telefonoDTO;
	}
	
	public static CorreoElectronicoDTO transformar(mx.gob.segob.nsjp.dto.persona.CorreoElectronicoWSDTO correoWSDTO) {
		CorreoElectronicoDTO correoDTO = new CorreoElectronicoDTO();
		correoDTO.setDireccionElectronica(correoWSDTO.getDireccionElectronica());
		return correoDTO;
	}
    
	
	
	
	
	
	/*************************/
    
    public static InvolucradoWSDTO transforma2Send(InvolucradoDTO involucradoDto){
        InvolucradoWSDTO involucradoWsdto = new InvolucradoWSDTO();
        ExpedienteWSDTOTransformer.transforma(involucradoDto, involucradoWsdto);

        if(involucradoDto.getInstitucionPresenta()!= null)
        	involucradoWsdto.setInstitucionPresenta(involucradoDto.getInstitucionPresenta().getConfInstitucionId());
        
        if(involucradoDto.getValorIdIdentificaion()!= null)
        	involucradoWsdto.setValorIdIdentificaion(involucradoDto.getValorIdIdentificaion().getIdCampo());
        
        if( involucradoDto.getNombresDemograficoDTO()!= null && !involucradoDto.getNombresDemograficoDTO().isEmpty()){
        	List<NombreDemograficoWSDTO> nombreDemograficosWSDTO = involucradoWsdto.getNombresDemograficos();
        	for (NombreDemograficoDTO nombreDemograficosDTO : involucradoDto.getNombresDemograficoDTO()) {
        		nombreDemograficosWSDTO.add(transforma (nombreDemograficosDTO));
			}
        }
        
        if(involucradoDto!=null && involucradoDto.getDelitosCometidos()!=null){
        	List<DelitoWSDTO> delitoWSDTO = new ArrayList<DelitoWSDTO>(); 
        	for (DelitoDTO delitoCometido : involucradoDto.getDelitosCometidos()) {
        		DelitoWSDTO delitoCometidoWsdto = new DelitoWSDTO();
            	ExpedienteWSDTOTransformer.transforma(delitoCometido, delitoCometidoWsdto);
            	delitoCometidoWsdto.setIdCatDelito(delitoCometido.getCatDelitoDTO().getCatDelitoId());
            	if(delitoCometido.getCatDelitoDTO() != null && delitoCometido.getCatDelitoDTO().getClaveInterInstitucional() != null)
            		delitoCometidoWsdto.setClaveInterInstitucional(delitoCometido.getCatDelitoDTO().getClaveInterInstitucional());
            	delitoWSDTO.add(delitoCometidoWsdto);
			}
        		involucradoWsdto.getDelitosCometidos().addAll(delitoWSDTO);
        }
        
        if(involucradoDto.getValorIdNacionalidad()!= null && !involucradoDto.getValorIdNacionalidad().isEmpty()){
        	List<Long> idNacionalidades = involucradoWsdto.getValorIdNacionalidad();
        	for (ValorDTO valorDTO : involucradoDto.getValorIdNacionalidad()) {
        		idNacionalidades.add( valorDTO.getIdCampo() );
			}
        }
        
        if(involucradoDto.getValorIdOcupacion()!= null && !involucradoDto.getValorIdOcupacion().isEmpty()){
        	List<Long> idOcupaciones = involucradoWsdto.getValorIdOcupacion();
        	for (ValorDTO valorDTO : involucradoDto.getValorIdOcupacion()) {
        		idOcupaciones.add( valorDTO.getIdCampo() );
			}
        }
        
        if(involucradoDto.getAliasInvolucradoDTO()!= null && !involucradoDto.getAliasInvolucradoDTO().isEmpty()){
        	List<String> alias = involucradoWsdto.getAliasInvolucrado();
        	for (AliasInvolucradoDTO aliasDTO : involucradoDto.getAliasInvolucradoDTO()) {
        		alias.add(aliasDTO.getAlias());
			}
        }
        
        if(involucradoDto.getTelefonosDTO()!= null && !involucradoDto.getTelefonosDTO().isEmpty()){
        	List<TelefonoWSDTO> telefonos = involucradoWsdto.getTelefonos();
        	for (TelefonoDTO telefonoDTO : involucradoDto.getTelefonosDTO()) {
        		telefonos.add(transformar(telefonoDTO));
			}
        }
        
        if(involucradoDto.getCorreosDTO()!= null && !involucradoDto.getCorreosDTO().isEmpty()){
        	List<CorreoElectronicoWSDTO> correos = involucradoWsdto.getCorreos();
        	for (CorreoElectronicoDTO correosDTO : involucradoDto.getCorreosDTO()) {
        		correos.add(transformar(correosDTO));
			}
        }
        CalidadDTO calidadDTO = involucradoDto.getCalidadDTO();
        if (calidadDTO != null) {
            involucradoWsdto.setCalidad(transforma(calidadDTO));
        }
        DomicilioDTO domicilio = involucradoDto.getDomicilio();
        if (domicilio != null) {
            involucradoWsdto.setDomicilio(transforma(domicilio));
        }
        DomicilioDTO domicilioNotificacion = involucradoDto.getDomicilioNotificacion();
        if (domicilioNotificacion != null) {
            involucradoWsdto.setDomicilioNotificacion(transforma(domicilioNotificacion));
        }
        MediaFiliacionDTO mediaFiliacionDTO = involucradoDto.getMediaFiliacionDTO();
        if (mediaFiliacionDTO != null) {
            involucradoWsdto.setMediaFiliacionWSDTO(transforma(mediaFiliacionDTO));
        }
        OrganizacionDTO organizacionDTO = involucradoDto.getOrganizacionDTO();
        if (organizacionDTO != null) {
            involucradoWsdto.setOrganizacion(transforma(organizacionDTO));
        }
        
		if (involucradoDto != null && involucradoDto.getEsVivo() != null) {
			if (involucradoDto.getEsVivo().equals(true)) {
				involucradoWsdto.setEsVivo(ES_VIVO);
			} else {
				involucradoWsdto.setEsVivo(ES_MUERTO);
			}
		}
		//Detenciones
		if(involucradoDto.getDetenciones()!=null && !involucradoDto.getDetenciones().isEmpty()){
			List<DetencionWSDTO> detencionesWSDTO = new ArrayList<DetencionWSDTO>();
			for (DetencionDTO detencionDTO : involucradoDto.getDetenciones()) {
				detencionesWSDTO.add( InvolucradoWSDTOTransformer.transforma(detencionDTO));
			}
			involucradoWsdto.getDetenciones().addAll(detencionesWSDTO);
		}
        logger.debug("folio involucrado :: " + involucradoWsdto.getFolioElemento()); 
        return involucradoWsdto;
    }

	public static DetencionWSDTO transforma(DetencionDTO detencionDTO) {
		if (detencionDTO == null) {
			return null;
		}
		DetencionWSDTO detencionWSDTO = new DetencionWSDTO();

		if (detencionDTO.getFechaFinDetencion() != null) {
			detencionWSDTO.setFechaFinDetencion(WsTransformer
					.transformFecha(detencionDTO.getFechaFinDetencion()));
			detencionWSDTO.setStrFechaFinDetencion(detencionDTO
					.getstrfechaFinDetencion());
		}

		if (detencionDTO.getFechaInicioDetencion() != null) {
			detencionWSDTO.setFechaInicioDetencion(WsTransformer
					.transformFecha(detencionDTO.getFechaInicioDetencion()));
			detencionWSDTO.setStrFechaInicioDetencion(detencionDTO
					.getstrfechaFinDetencion());
		}
		if (detencionDTO.getFechaRecepcionDetencion() != null) {
			detencionWSDTO.setFechaRecepcionDetencion(WsTransformer
					.transformFecha(detencionDTO.getFechaRecepcionDetencion()));
			detencionWSDTO.setStrFechaRecepcionDetencion(detencionDTO
					.getStrFechaRecepcionDetencion());
		}
		detencionWSDTO.setMotivoDetencion(detencionDTO.getMotivoDetencion());
		detencionWSDTO.setObservaciones(detencionDTO.getObservaciones());
		detencionWSDTO.setLugarDetencionProvicional(detencionDTO
				.getLugarDetencionProvicional());
		if (detencionDTO.getLugarDetencionDTO() != null) {
			detencionWSDTO.setLugarDetencion(InvolucradoWSDTOTransformer
					.transforma(detencionDTO.getLugarDetencionDTO()));
		}
		return detencionWSDTO;
	}

	public static LugarWSDTO transforma(LugarDTO lugarDTO) {
		if (lugarDTO == null) {
			return null;
		}

		LugarWSDTO lugarWSDTO = new LugarWSDTO();
		// Tranformar datos primitivos que tienen el mismo nombre del metodo
		ExpedienteWSDTOTransformer.transforma(lugarDTO, lugarWSDTO);

		lugarWSDTO.setDescripcion(lugarDTO.getDescripcion());

		if (lugarDTO.getValorIdElemento() != null
				&& lugarDTO.getValorIdElemento().getIdCampo() != null)
			lugarWSDTO.setValorIdElemento(lugarDTO.getValorIdElemento()
					.getIdCampo());
		return lugarWSDTO;
	}
    
    public static OrganizacionWSDTO transforma(OrganizacionDTO organizacionDTO){
        OrganizacionWSDTO organizacionWSDTO = new OrganizacionWSDTO();
        ExpedienteWSDTOTransformer.transforma(organizacionDTO, organizacionWSDTO);
        
        CalidadDTO calidadDTO = organizacionDTO.getCalidadDTO();
        if (calidadDTO != null) {
            organizacionWSDTO.setCalidad(transforma(calidadDTO));
        }
        InvolucradoDTO representanteLegal = organizacionDTO.getRepresentanteLegal();
        if (representanteLegal != null) {
            organizacionWSDTO.setRepresentanteLegal(transforma2Send(representanteLegal));
        }
        ValorDTO valorByComunidadVirtualVal = organizacionDTO.getValorByComunidadVirtualVal();
        if (valorByComunidadVirtualVal != null) {
            organizacionWSDTO.setValorByComunidadVirtualVal(valorByComunidadVirtualVal.getIdCampo());
        }
        ValorDTO valorByOrganizacionFormalVal = organizacionDTO.getValorByOrganizacionFormalVal();
        if (valorByOrganizacionFormalVal != null) {
            organizacionWSDTO.setValorByOrganizacionFormalVal(valorByOrganizacionFormalVal.getIdCampo());
        }
        ValorDTO valorBySectorGubernamentalVal = organizacionDTO.getValorBySectorGubernamentalVal();
        if (valorBySectorGubernamentalVal != null) {
            organizacionWSDTO.setValorBySectorGubernamentalVal(valorBySectorGubernamentalVal.getIdCampo());
        }
        ValorDTO valorByTipoOrganizacionVal = organizacionDTO.getValorByTipoOrganizacionVal();
        if (valorByTipoOrganizacionVal != null) {
            organizacionWSDTO.setValorByTipoOrganizacionVal(valorByTipoOrganizacionVal.getIdCampo());
        }
        return organizacionWSDTO;
    }

    public static MediaFiliacionWSDTO transforma(MediaFiliacionDTO mediaFiliacionDTO){
        MediaFiliacionWSDTO mediaFiliacionWSDTO = new MediaFiliacionWSDTO();
        ExpedienteWSDTOTransformer.transforma(mediaFiliacionDTO, mediaFiliacionWSDTO);
        SeniaParticularDTO seniaParticularDTO = mediaFiliacionDTO.getSeniaParticularDTO();
        if (seniaParticularDTO != null) {
            mediaFiliacionWSDTO.setSeniaParticular(transforma(seniaParticularDTO));
        }
        return mediaFiliacionWSDTO;
    }

    public static SeniaParticularWSDTO transforma(SeniaParticularDTO seniaParticularDTO){
        SeniaParticularWSDTO seniaParticularWSDTO = new SeniaParticularWSDTO();
        ExpedienteWSDTOTransformer.transforma(seniaParticularDTO, seniaParticularWSDTO);
        return seniaParticularWSDTO;
    }
    
    public static DomicilioWSDTO transforma(DomicilioDTO domicilioDto){
        DomicilioWSDTO domicilioWsdto = new DomicilioWSDTO();
        ExpedienteWSDTOTransformer.transforma(domicilioDto, domicilioWsdto);
        CalidadDTO calidadDTO = domicilioDto.getCalidadDTO();
        if (calidadDTO != null) {
            domicilioWsdto.setCalidad(transforma(calidadDTO));
        }
        
        if( domicilioDto.getAsentamientoDTO()!= null && domicilioDto.getAsentamientoDTO().getAsentamientoId()!= null)
        	domicilioWsdto.setAsentamientoId(domicilioDto.getAsentamientoDTO().getAsentamientoId());
        if(domicilioDto.getEntidadDTO()!= null && domicilioDto.getEntidadDTO().getEntidadFederativaId()!= null)
        	domicilioWsdto.setEntidadId(domicilioDto.getEntidadDTO().getEntidadFederativaId());
        if(domicilioDto.getMunicipioDTO()!= null && domicilioDto.getMunicipioDTO().getMunicipioId()!= null)
        	domicilioWsdto.setMunicipioId(domicilioDto.getMunicipioDTO().getMunicipioId());
        if(domicilioDto.getCiudadDTO()!= null && domicilioDto.getCiudadDTO().getCiudadId()!= null)
        	domicilioWsdto.setCiudadId(domicilioDto.getCiudadDTO().getCiudadId());
        if(domicilioDto.getValorCalleId()!= null )
        	domicilioWsdto.setValorCalleId(domicilioDto.getValorCalleId().getIdCampo());
        
        return domicilioWsdto;
    }

    public static CalidadWSDTO transforma(CalidadDTO calidadDto){
        CalidadWSDTO calidadWsdto = new CalidadWSDTO();
        ExpedienteWSDTOTransformer.transforma(calidadDto, calidadWsdto);
        calidadWsdto.setValorIdCalidad( calidadDto.getValorIdCalidad().getIdCampo());
        return calidadWsdto;
    }

    public static NombreDemograficoWSDTO transforma(NombreDemograficoDTO nomDemograficoDTO) {
    	NombreDemograficoWSDTO nomDemograficoWsdto = new  NombreDemograficoWSDTO();
		
		nomDemograficoWsdto.setNombre(nomDemograficoDTO.getNombre());
		nomDemograficoWsdto.setApellidoPaterno(nomDemograficoDTO.getApellidoPaterno());
		nomDemograficoWsdto.setApellidoMaterno(nomDemograficoDTO.getApellidoMaterno());
		nomDemograficoWsdto.setCurp(nomDemograficoDTO.getCurp());
		nomDemograficoWsdto.setRfc(nomDemograficoDTO.getRfc());
		nomDemograficoWsdto.setFechaNacimiento(WsTransformer.
                transformFecha(nomDemograficoDTO.getFechaNacimiento()));
		nomDemograficoWsdto.setLugarNacimiento(nomDemograficoDTO.getLugarNacimiento());
		nomDemograficoWsdto.setEdadAproximada(nomDemograficoDTO.getEdadAproximada());
		nomDemograficoWsdto.setEsVerdadero(nomDemograficoDTO.getEsVerdadero());
		nomDemograficoWsdto.setSexo(nomDemograficoDTO.getSexo());
		
		if (nomDemograficoDTO.getPaisValorDTO()!=null)
			nomDemograficoWsdto.setPaisValorId(nomDemograficoDTO.getPaisValorDTO().getIdCampo());
		if (nomDemograficoDTO.getMunicipioDTO()!=null)
			nomDemograficoWsdto.setMunicipioId(nomDemograficoDTO.getMunicipioDTO().getMunicipioId());
		if (nomDemograficoDTO.getEntidadFederativaDTO()!=null)
			nomDemograficoWsdto.setEntidadFederativaId(nomDemograficoDTO.getEntidadFederativaDTO().getEntidadFederativaId());
		return nomDemograficoWsdto;
	}


    public static TelefonoWSDTO transformar (TelefonoDTO telefonoDTO) {
		TelefonoWSDTO telefonoWSDTO = new TelefonoWSDTO();
		
		telefonoWSDTO.setNumeroTelefonico(telefonoDTO.getNumeroTelefonico());
		telefonoWSDTO.setCodigoPais(telefonoDTO.getCodigoPais());
		telefonoWSDTO.setCodigoArea(telefonoDTO.getCodigoArea());
		
		if (telefonoDTO.getValorTipoTelefono()!=null)
			telefonoWSDTO.setValorTipoTelefonoId(telefonoDTO.getValorTipoTelefono().getIdCampo());
		
		return telefonoWSDTO;
	}
	
	public static CorreoElectronicoWSDTO transformar(CorreoElectronicoDTO correoDTO) {
		CorreoElectronicoWSDTO correoWSDTO = new CorreoElectronicoWSDTO();
		correoWSDTO.setDireccionElectronica(correoDTO.getDireccionElectronica());
		return correoWSDTO;
	}
}
