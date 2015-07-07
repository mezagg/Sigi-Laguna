/**
 * Nombre del Programa : InvolucradoSolicitudDefensor.java                                    
 * Autor                            : GustavoBP                                             
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 01/11/2012 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Entidad de cruce para relacionar Involucrados a Solicitud de Defensor                      
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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidad de cruce para relacionar Involucrados a Solicitud de Defensor
 * 
 * @author GustavoBP
 */
@Entity
@Table(name = "InvolucradoSolicitudDefensor")
public class InvolucradoSolicitudDefensor implements java.io.Serializable {

	private static final long serialVersionUID = 2926367273818283382L;
	private InvolucradoSolicitudDefensorId id;
	private Involucrado involucrado;
	private SolicitudDefensor solicitudDefensor;

	/** default constructor */
	public InvolucradoSolicitudDefensor() {
	}

	/**
	 * @param id
	 * @param involucrado
	 * @param solicitudDefensor
	 */
	public InvolucradoSolicitudDefensor(InvolucradoSolicitudDefensorId id,
			Involucrado involucrado, SolicitudDefensor solicitudDefensor) {
		this.id = id;
		this.involucrado = involucrado;
		this.solicitudDefensor = solicitudDefensor;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "involucradoId", column = @Column(name = "Involucrado_id", nullable = false, precision = 18, scale = 0)),
			@AttributeOverride(name = "documentoId", column = @Column(name = "SolicitudDefensor_id", nullable = false, precision = 18, scale = 0)) })
	public InvolucradoSolicitudDefensorId getId() {
		return id;
	}

	public void setId(InvolucradoSolicitudDefensorId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Involucrado_id", nullable = false, insertable = false, updatable = false)
	public Involucrado getInvolucrado() {
		return involucrado;
	}

	public void setInvolucrado(Involucrado involucrado) {
		this.involucrado = involucrado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SolicitudDefensor_id", nullable = false, insertable = false, updatable = false)
	public SolicitudDefensor getSolicitudDefensor() {
		return solicitudDefensor;
	}

	public void setSolicitudDefensor(SolicitudDefensor solicitudDefensor) {
		this.solicitudDefensor = solicitudDefensor;
	}

}
