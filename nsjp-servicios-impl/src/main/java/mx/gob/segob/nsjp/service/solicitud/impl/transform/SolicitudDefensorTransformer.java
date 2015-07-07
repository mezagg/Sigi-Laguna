/**
* Nombre del Programa : SolicitudDefensorTransformer.java
* Autor                            : Hugo Serrano
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 21 Jun 2011
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
package mx.gob.segob.nsjp.service.solicitud.impl.transform;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.SolicitudDefensorDelito;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.AudienciaTransformer;
import mx.gob.segob.nsjp.service.delito.impl.transform.DelitoTransfromer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.AvisoDesignacionTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.forma.impl.transform.FormaTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

/**
 * Clase que transforma los objetos de tipo SolicitudDefensor
 * @version 1.0
 * @author Tattva-IT
 *
 */
public class SolicitudDefensorTransformer {
	
	public static List<SolicitudDefensorDTO> transformarSolicitudesDefensoria(List<SolicitudDefensor> lstsolDef){
		List<SolicitudDefensorDTO> resp = new ArrayList<SolicitudDefensorDTO>();
		
		for(SolicitudDefensor d : lstsolDef){
			resp.add(transformarSolicitudDefensoria(d));
			
		}
		
		return resp;
		
	}
	
	
	/**
	 * M&eacute;todo para transformar un objeto SolicitudDefensor en
	 * SolicitudDefensorDTO
	 * 
	 * @param solDef
	 *            de tipo SolicitudDefensor
	 * @return SolicitudDefensorDTO
	 */
	public static SolicitudDefensorDTO transformarSolicitudDefensoria(
			SolicitudDefensor solDef) {

		SolicitudDefensorDTO solDTO = null;

		if (solDef != null) {

			solDTO = new SolicitudDefensorDTO();

			solDTO.setDocumentoId(solDef.getDocumentoId());

			solDTO.setNumeroCasoAsociado(solDef.getNumeroCasoAsociado());

			solDTO.setFechaCreacion(solDef.getFechaCreacion());

			solDTO.setStrFechaCreacion(DateUtils.formatear(solDef
					.getFechaCreacion()));

			solDTO.setStrHoraCreacion(DateUtils.formatearHora(solDef
					.getFechaCreacion()));

			solDTO.setFechaLimite(solDef.getFechaLimite());

			solDTO.setStrFechaLimite(DateUtils.formatear(solDef
					.getFechaLimite()));

			solDTO.setStrHoraLimite(DateUtils.formatearHora(solDef
					.getFechaLimite()));

			solDTO.setOrigenDocumento(solDef.getOrigenDocumento());

			solDTO.setDetenido(solDef.getDetenido());

			if (solDef.getConfInstitucion() != null) {
				solDTO.setConfInstitucion(ConfInstitucionTransformer
						.transformarInstitucion(solDef.getConfInstitucion()));
			}

			solDTO.setFechaModificacion(solDef.getFechaModificacion());

			solDTO.setFechaCierre(solDef.getFechaCierre());

			solDTO.setFolioDocumento(solDef.getFolioDocumento());

			solDTO.setFolioSolicitud(solDef.getFolioSolicitud());

			solDTO.setMotivo(solDef.getMotivo());

			if (solDef.getNumeroExpediente() != null
					&& solDef.getNumeroExpediente().getNumeroExpediente() != null
					&& !solDef.getNumeroExpediente().getNumeroExpediente()
							.isEmpty()) {

				solDTO.setNumeroExpediente(solDef.getNumeroExpediente()
						.getNumeroExpediente());
				// solDTO.setExpedienteDTO(ExpedienteTransformer
				// .transformarExpedienteBasico(solDef
				// .getNumeroExpediente()));
				// solDTO.setExpedienteDTO(ExpedienteTransformer
				// .transformarExpedienteDefensoria(solDef
				// .getNumeroExpediente()));
			}

			if (solDef.getExpediente() != null) {
				solDTO.setExpedienteDTO(ExpedienteTransformer
						.transformaExpediente(solDef.getExpediente()));
			}

			// Nombre del documento
			solDTO.setNombreDocumento(solDef.getNombreDocumento());
			// Usuario Solicitante
			solDTO.setUsuarioSolicitante(FuncionarioTransformer
					.transformarFuncionario(solDef.getFuncionarioSolicitante()));
			// Nombre del Solicitante
			solDTO.setNombreSolicitante(solDef.getNombreSolicitante());
			// Solicitante externo
			solDTO.setSolicitanteExterno(solDef.getSolicitanteExterno());

			// Es interno
			if (solDTO.getUsuarioSolicitante() != null
					&& solDTO.getUsuarioSolicitante().getDepartamento() != null) {
				solDTO.setNombreInstitucionSolicitante(solDTO
						.getUsuarioSolicitante().getDepartamento()
						.getNombreDepto());
				if (solDTO.getUsuarioSolicitante().getPersona() != null)
					solDTO.setNombreSolicitanteExternoInterno(solDTO
							.getUsuarioSolicitante().getPersona()
							.getNombreCompleto());
			} else {
				// es externo
				solDTO.setNombreInstitucionSolicitante(null);
				solDTO.setNombreSolicitanteExternoInterno(solDTO
						.getNombreSolicitante());
			}

			if (solDef.getEstatus() != null) {
				solDTO.setEstatus(new ValorDTO(
						solDef.getEstatus().getValorId(), solDef.getEstatus()
								.getValor()));
			}

			if (solDef.getEsDetenido() != null) {
				solDTO.setEsDetenido(solDef.getEsDetenido());
			} else {
				solDTO.setEsDetenido(false);
			}

			DelitoDTO delito = null;

			if (solDef.getSolicitudDefensorDelitos() != null) {
				for (SolicitudDefensorDelito sdd : solDef
						.getSolicitudDefensorDelitos()) {
					delito = DelitoTransfromer.transformarDelito(sdd
							.getDelito());
					solDTO.getDelitos().add(delito);
				}
			}
			// Destinatario
			if (solDef.getDestinatario() != null) {
				solDTO.setDestinatario(FuncionarioTransformer
						.transformarFuncionario(solDef.getDestinatario()));
			}

			// Se transforma la audiencia
			if (solDef.getAudiencia() != null) {
				solDTO.setAudiencia(AudienciaTransformer.transformarDTO(solDef
						.getAudiencia()));
			}

			// Se transforma el tipo de asesoria
			if (solDef.getTipoAsesoria() != null) {
				solDTO.setTipoAsesoria(new ValorDTO(solDef.getTipoAsesoria()
						.getValorId(), solDef.getTipoAsesoria().getValor()));
			}

			// Se transforma la Forma
			if (solDef.getForma() != null) {
				solDTO.setFormaDTO(FormaTransformer.transformarForma(solDef
						.getForma()));
			}

			// Tipo de documento
			if (solDef.getTipoDocumento() != null) {
				solDTO.setTipoDocumentoDTO(ValorTransformer.transformar(solDef
						.getTipoDocumento()));
			}
			// Tipo de solicitud
			if (solDef.getTipoSolicitud() != null) {
				solDTO.setTipoSolicitudDTO(ValorTransformer.transformar(solDef
						.getTipoSolicitud()));
			}
			// Archivo Digital
			if (solDef.getArchivoDigital() != null) {
				solDTO.setArchivoDigital(ArchivoDigitalTransformer
						.transformarArchivoDigital(solDef.getArchivoDigital()));
			}
			// Numero de expediente asociado
			if (solDef.getNumeroExpedienteAsociado() != null
					&& !solDef.getNumeroExpedienteAsociado().trim().isEmpty()) {
				solDTO.setNumeroExpedienteAsociado(solDef
						.getNumeroExpedienteAsociado().trim());
			}

			// Aviso Designacion
			if (solDef.getAvisoDesignacion() != null
					&& solDef.getAvisoDesignacion().getDocumentoId() != null) {
				
				// Prevenir recursividad con el transformer de AvisoDesignacionTransformer
				//Se quita el aviso de designacion
				solDef.getAvisoDesignacion().setSolicitudDefensor(null);
				
				solDTO.setAvisoDesignacion(AvisoDesignacionTransformer
						.transformar(solDef.getAvisoDesignacion()));
				
				solDef.getAvisoDesignacion().setSolicitudDefensor(solDef);
			}
			
			if (solDef.getInvolucradoSolicitante() != null) {
				solDTO.setInvolucradoDTO(InvolucradoTransformer
						.transformarInvolucrado(solDef
								.getInvolucradoSolicitante()));
			}
			
			if (solDef.getNombreDeLaUnidadDeInvestigacionDelSolicitante() != null) {
				solDTO.setNombreDeLaUnidadDeInvestigacionDelSolicitante(solDef
						.getNombreDeLaUnidadDeInvestigacionDelSolicitante());
			}
		}
		return solDTO;
	}
	
	/**
	 * 
	 * @param solDef
	 * @return
	 */
	public static SolicitudDefensorDTO transformarSolicitudDefensoriaBasico(SolicitudDefensor solDef){
		SolicitudDefensorDTO solDTO = new SolicitudDefensorDTO();
		solDTO.setDocumentoId(solDef.getDocumentoId());
		
		solDTO.setNumeroCasoAsociado(solDef.getNumeroCasoAsociado());
		solDTO.setFechaCreacion(solDef.getFechaCreacion());
		solDTO.setStrFechaCreacion(DateUtils.formatear(solDef.getFechaCreacion()));
		solDTO.setStrHoraCreacion(DateUtils.formatearHora(solDef.getFechaCreacion()));
		solDTO.setFechaLimite(solDef.getFechaLimite());
		solDTO.setStrFechaLimite(DateUtils.formatear(solDef.getFechaLimite()));
		solDTO.setStrHoraLimite(DateUtils.formatearHora(solDef.getFechaLimite()));
		solDTO.setOrigenDocumento(solDef.getOrigenDocumento());
		solDTO.setDetenido(solDef.getDetenido());
		if (solDef.getConfInstitucion()!=null)
			solDTO.setInstitucion(ConfInstitucionTransformer.transformarInstitucion( solDef.getConfInstitucion()));
		
		solDTO.setFechaModificacion(solDef.getFechaModificacion());
		solDTO.setFechaCierre(solDef.getFechaCierre());
		
		solDTO.setFolioDocumento(solDef.getFolioDocumento());
		solDTO.setFolioSolicitud(solDef.getFolioSolicitud());
		solDTO.setMotivo(solDef.getMotivo());
		
		if(solDef.getNumeroExpediente()!=null){	
			solDTO.setExpedienteDTO(ExpedienteTransformer.transformarExpedienteBasico(solDef.getNumeroExpediente()));
			
		}
		solDTO.setNombreDocumento(solDef.getNombreDocumento());
		solDTO.setUsuarioSolicitante(FuncionarioTransformer.transformarFuncionario(solDef.getFuncionarioSolicitante()));
		solDTO.setNombreSolicitante(solDef.getNombreSolicitante());
		solDTO.setSolicitanteExterno(solDef.getSolicitanteExterno());
		        
        // Es interno
        if (solDTO.getUsuarioSolicitante() != null && solDTO.getUsuarioSolicitante()
                .getDepartamento()!= null) {        	
        	solDTO.setNombreInstitucionSolicitante(solDTO.getUsuarioSolicitante()
                    .getDepartamento().getNombreDepto());
        	if (solDTO.getUsuarioSolicitante().getPersona()!=null)
        		solDTO.setNombreSolicitanteExternoInterno(solDTO.getUsuarioSolicitante()
                    .getPersona().getNombreCompleto());
        } else {
            // es externo
        	solDTO.setNombreInstitucionSolicitante(null);
        	solDTO.setNombreSolicitanteExternoInterno(solDTO.getNombreSolicitante());
        }
        if (solDef.getEstatus()!=null) {
            solDTO.setEstatus(new ValorDTO(solDef.getEstatus().getValorId(), solDef.getEstatus().getValor()));
        }
        solDTO.setDetenido(solDef.getDetenido());

        if(solDef.getEsDetenido() != null){
        	solDTO.setEsDetenido(solDef.getEsDetenido());
        }else{
        	solDTO.setEsDetenido(false);
        }
        DelitoDTO delito = null;
        if(solDef.getSolicitudDefensorDelitos() != null){
        	for(SolicitudDefensorDelito sdd : solDef.getSolicitudDefensorDelitos()){
        		delito = DelitoTransfromer.transformarDelito(sdd.getDelito());
        	}
        }
        
        if (solDef.getDestinatario()!=null) {
        	solDTO.setDestinatario(FuncionarioTransformer.transformarFuncionario(solDef.getDestinatario()));
        }
        
        //Se transforma la audiencia
        if(solDef.getAudiencia() != null)
        	solDTO.setAudiencia(AudienciaTransformer.transformarDTO(solDef.getAudiencia()));   
        
        //Se transforma el tipo de asesoria
        if(solDef.getTipoAsesoria() != null)
        	solDTO.setTipoAsesoria(new ValorDTO(solDef.getTipoAsesoria().getValorId(), solDef.getTipoAsesoria().getValor()));
        
		return solDTO;
	}
	
	public static SolicitudDefensor transformarSolicitudDefensoria(SolicitudDefensorDTO solicitudDTO){
		SolicitudDefensor solicitud = new SolicitudDefensor();
		solicitud.setDocumentoId(solicitudDTO.getDocumentoId());
		
		
		solicitud.setFechaCreacion(solicitudDTO.getFechaCreacion());
		solicitud.setFechaLimite(solicitudDTO.getFechaLimite());
		solicitud.setFechaModificacion(solicitudDTO.getFechaModificacion());
		solicitud.setFechaCierre(solicitudDTO.getFechaCierre());
		solicitud.setFolioDocumento(solicitudDTO.getFolioDocumento());
		solicitud.setFolioSolicitud(solicitudDTO.getFolioSolicitud());
		solicitud.setMotivo(solicitudDTO.getMotivo());
        solicitud.setEstatus(new Valor(solicitudDTO.getEstatus().getIdCampo()));
        solicitud.setForma(new Forma(solicitudDTO.getFormaDTO().getFormaId()));
        solicitud.setDetenido(solicitudDTO.getDetenido());
        solicitud.setNumeroCasoAsociado(solicitudDTO.getNumeroCasoAsociado());
        solicitud.setDetenido(solicitudDTO.getDetenido());
        solicitud.setTipoDocumento(new Valor(solicitudDTO.getTipoDocumentoDTO().getIdCampo()));
        solicitud.setTipoSolicitud(new Valor(solicitudDTO.getTipoSolicitudDTO().getIdCampo()));
        solicitud.setNombreDocumento(solicitudDTO.getNombreDocumento());
        if (solicitudDTO.getInvolucradoDTO() != null
        		&& solicitudDTO.getInvolucradoDTO().getFolioElemento() != null){
        	solicitud.setFolioElementoDetenido(solicitudDTO.getInvolucradoDTO().getFolioElemento());
        }
        
        if(solicitudDTO.getAudiencia() != null)
        	solicitud.setAudiencia(AudienciaTransformer.transformarEntity(solicitudDTO.getAudiencia()));        
        
		return solicitud;
	}	
	

}
