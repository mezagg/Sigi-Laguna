/**
 *
 * Nombre del Programa : InvolucradoSolicitudDefensorId.java                                    
 * Autor                            : GustavoBP                                             
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 01/11/2012 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Llave de la entidad de Cruce InvolucradoSolicitudDefensor                      
 * Programa Dependiente  :N/A                                                      
 * Programa Subsecuente :N/A                                                      
 * Cond. de ejecucion        :N/A                                                      
 * Dias de ejecucion          :N/A                             Horario: N/A       
 *                              MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                       :N/A                                                           
 * Compania               :N/A                                                           
 * Proyecto                 :N/A                                   Fecha: N/A       
 * Modificacion           :N/A                                                           
 *------------------------------------------------------------------------------           
 */
package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Llave de la entidad de Cruce InvolucradoSolicitudDefensor
 * 
 * @author GustavoBP
 */
@Embeddable
public class InvolucradoSolicitudDefensorId implements java.io.Serializable {

	private static final long serialVersionUID = 3752394639809537966L;

	private Long involucradoId;
	private Long solicitudDefensorId;

	/** default constructor */
	public InvolucradoSolicitudDefensorId() {
	}

	public InvolucradoSolicitudDefensorId(Long involucradoId,
			Long solicitudDefensorId) {
		this.involucradoId = involucradoId;
		this.solicitudDefensorId = solicitudDefensorId;
	}

	@Column(name = "Involucrado_id", nullable = false, precision = 18, scale = 0)
	public Long getInvolucradoId() {
		return involucradoId;
	}

	public void setInvolucradoId(Long involucradoId) {
		this.involucradoId = involucradoId;
	}

	@Column(name = "SolicitudDefensor_id", nullable = false, precision = 18, scale = 0)
	public Long getSolicitudDefensorId() {
		return solicitudDefensorId;
	}

	public void setSolicitudDefensorId(Long solicitudDefensorId) {
		this.solicitudDefensorId = solicitudDefensorId;
	}

}
