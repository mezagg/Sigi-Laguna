/**
 * Nombre del Programa : NotificacionTransformer.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 19-jul-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.notificacion.impl;

import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.model.Notificacion;
import mx.gob.segob.nsjp.model.Valor;

/**
 * Realiza las funciones de conversion entre Notificacion y NotificacionDTO.
 * @version 1.0
 * @author Jacob Lobaco
 */
public class NotificacionTransformer {

    /**
     * Transforma un Notificacion en un NotificacionDTO.
     * @param notificacion Un Notificacion basico a tranformar.
     * @return Un NotificacionDTO.
     */
    public static NotificacionDTO transformarNotificacion(Notificacion notificacion){
        NotificacionDTO notificacionDTO = new NotificacionDTO();
        notificacionDTO.setDocumentoId(notificacion.getDocumentoId());
        notificacionDTO.setConsecutivoNotificacion(notificacion.getConsecutivoNotificacion());
        notificacionDTO.setDomicilio(notificacion.getDomicilio());
        notificacionDTO.setFechaCitado(notificacion.getFechaCitado());
        notificacionDTO.setLugar(notificacion.getLugar());
        notificacionDTO.setLugarCitado(notificacion.getLugarCitado());
        notificacionDTO.setMotivo(notificacion.getMotivo());
        notificacionDTO.setPenalidades(notificacion.getPenalidades());
        notificacionDTO.setPersonaAutoriza(notificacion.getPersonaAutoriza());
        notificacionDTO.setPuestoAutoriza(notificacion.getPersonaAutoriza());
        notificacionDTO.setFechaCreacion(notificacion.getFechaCreacion());
        notificacionDTO.setFechaRecepcion(notificacion.getFechaCreacion());
        notificacionDTO.setNombreDocumento(notificacion.getNombreDocumento());
        if (notificacion.getCatFormaNotificacion() != null){
        	notificacionDTO.setCatFormaNotificacionDTO(
        			CatFormaNotificacionTransformer.transformar(notificacion.getCatFormaNotificacion()));
        }
        return notificacionDTO;
    }

    /**
     * Transforma un NotificacionDTO en un Notificacion basico.
     * @param notificacionDTO El DTO a transformar.
     * @return Un objeto de tipo Notificacion
     */
    public static Notificacion transformarNotificacion(NotificacionDTO notificacionDTO){
        Notificacion notificacion = new Notificacion();
        notificacion.setConsecutivoNotificacion(notificacionDTO.getConsecutivoNotificacion());
        notificacion.setDomicilio(notificacionDTO.getDomicilio());
        notificacion.setFechaCitado(notificacionDTO.getFechaCitado());
//        notificacion.setInvolucradoAudiencia(notificacionDTO.getin);
        notificacion.setLugar(notificacionDTO.getLugar());
        notificacion.setLugarCitado(notificacionDTO.getLugarCitado());
        notificacion.setMotivo(notificacionDTO.getMotivo());
        notificacion.setPenalidades(notificacionDTO.getPenalidades());
        notificacion.setPersonaAutoriza(notificacionDTO.getPersonaAutoriza());
        notificacion.setPuestoAutoriza(notificacionDTO.getPuestoAutoriza());
        
        notificacion.setFolioNotificacion(notificacionDTO.getFolioNotificacion());
        if(notificacionDTO.getEstatus()!=null){
        	notificacion.setEstatus(new Valor(notificacionDTO.getEstatus().getIdCampo()));
        }
        
        if (notificacionDTO.getCatFormaNotificacionDTO() != null){
        	notificacion.setCatFormaNotificacion(
        			CatFormaNotificacionTransformer.transformar(notificacionDTO.getCatFormaNotificacionDTO()));
        }
        return notificacion;
    }
    
    public static Notificacion transformarNotificacionDTOupdate(Notificacion notificacionBD, NotificacionDTO notificacionDTO){
        
    	if(notificacionDTO == null ){
    		return notificacionBD;
    	}
    	
    	if(notificacionDTO.getDocumentoId() != null){
    		notificacionBD.setDocumentoId(notificacionDTO.getDocumentoId());
    	}
    	
    	if(notificacionDTO.getFechaCitado() != null){
        	notificacionBD.setFechaCitado(notificacionDTO.getFechaCitado());
        }
    	
    	if(notificacionDTO.getEstatus()!=null){
        	notificacionBD.setEstatus(new Valor(notificacionDTO.getEstatus().getIdCampo()));
        }
    	
    	if(notificacionDTO.getConsecutivoNotificacion() != null){
    		notificacionBD.setConsecutivoNotificacion(notificacionDTO.getConsecutivoNotificacion());
    	}
    	
    	if(notificacionDTO.getDomicilio() != null){
    		notificacionBD.setDomicilio(notificacionDTO.getDomicilio());
    	}
        if(notificacionDTO.getLugar() != null){
        	notificacionBD.setLugar(notificacionDTO.getLugar());
        }
        
        if(notificacionDTO.getLugarCitado() != null){
        	notificacionBD.setLugarCitado(notificacionDTO.getLugarCitado());
        }
        
        if(notificacionDTO.getMotivo() != null){
        	notificacionBD.setMotivo(notificacionDTO.getMotivo());
        }
        
        if(notificacionDTO.getPenalidades() != null){
        	notificacionBD.setPenalidades(notificacionDTO.getPenalidades());
        }
        
        if(notificacionDTO.getPersonaAutoriza() != null){
        	notificacionBD.setPersonaAutoriza(notificacionDTO.getPersonaAutoriza());
        }
        
        if(notificacionDTO.getPuestoAutoriza() != null){
        	notificacionBD.setPuestoAutoriza(notificacionDTO.getPuestoAutoriza());
        }
        
        if(notificacionDTO.getFolioNotificacion() != null){
        	notificacionBD.setFolioNotificacion(notificacionDTO.getFolioNotificacion());
        }
       
        
       
        
        if (notificacionDTO.getCatFormaNotificacionDTO() != null){
        	notificacionBD.setCatFormaNotificacion(
        			CatFormaNotificacionTransformer.transformar(notificacionDTO.getCatFormaNotificacionDTO()));
        }
        
        return notificacionBD;
    }
}
