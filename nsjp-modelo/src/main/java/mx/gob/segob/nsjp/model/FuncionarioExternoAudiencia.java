/**
 * Nombre del Programa	: FuncionarioExternoAudiencia.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Feb 2013
 * Marca de cambio      : N/A
 * Descripcion General  : FuncionarioExternoAudiencia Entidad de cruce para relacionar los
 * 						  funcionarios externos con la audiencia
 * Programa Dependiente	: N/A
 * Programa Subsecuente	: N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                : N/A
 * Compania             : N/A
 * Proyecto             : N/A                             Fecha: N/A
 * Modificacion         : N/A
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
 * @author AlejandroGA
 * @version 1.0
 * Entidad de cruce para relacionar los funcionarios externos con la audiencia
 */

@Entity
@Table(name = "FuncionarioExternoAudiencia")
public class FuncionarioExternoAudiencia implements java.io.Serializable{

	private static final long serialVersionUID = 5093584248043869649L;
	
	private FuncionarioExternoAudienciaId funcionarioExternoAudienciaId;
	private FuncionarioExterno funcionarioExterno; 
	private Audiencia audiencia;
	private Boolean esPresente;
	private Boolean esTitular;
	
	
	/**
	 * Default constructor
	 */
	public FuncionarioExternoAudiencia() {
	}
	
	/**
	 * Constructor minimo
	 * @param funcionarioExternoAudienciaId
	 */
	public FuncionarioExternoAudiencia(
			FuncionarioExternoAudienciaId funcionarioExternoAudienciaId) {	
		this.funcionarioExternoAudienciaId = funcionarioExternoAudienciaId;
	}

	
	/**
	 * @param funcionarioExternoAudienciaId
	 * @param funcionarioExterno
	 * @param esPresente
	 * @param esTitular
	 */
	public FuncionarioExternoAudiencia(
			FuncionarioExternoAudienciaId funcionarioExternoAudienciaId,
			Boolean esPresente, Boolean esTitular) {
		this.funcionarioExternoAudienciaId = funcionarioExternoAudienciaId;
		this.esPresente = esPresente;
		this.esTitular = esTitular;
	}
	
	
	/**
     * M&eacute;todo de acceso al campo funcionarioExternoAudienciaId.
     * @return El valor del campo funcionarioExternoAudienciaId
     */
	
	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name = "audienciaId", column = @Column(name = "Audiencia_id", nullable = false, precision = 18, scale = 0)),
		@AttributeOverride(name = "funcionarioExternoId", column = @Column(name = "FuncionarioExterno_id", nullable = false, precision = 18, scale = 0))
	})
	public FuncionarioExternoAudienciaId getFuncionarioExternoAudienciaId() {
		return funcionarioExternoAudienciaId;
	}
	
	/**
     * M&eacute;todo de acceso al campo FuncionarioExterno.
     * @return El valor del campo FuncionarioExterno
     */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FuncionarioExterno_id", nullable = false, insertable = false, updatable = false)
	public FuncionarioExterno getFuncionarioExterno() {
		return funcionarioExterno;
	}
	
	/**
	 * M&eacute;todo de acceso al campo audiencia.
	 * @return El valor del campo audiencia
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Audiencia_id", nullable = false, insertable = false, updatable = false)
	public Audiencia getAudiencia() {
		return audiencia;
	}
	
	
	/**
     * M&eacute;todo de acceso al campo esPresente.
     * @return El valor del campo esPresente
     */
    @Column(name = "bEsPresente", precision = 1, scale = 0)
	public Boolean getEsPresente() {
		return esPresente;
	}
	
    /**
     * M&eacute;todo de acceso al campo esTitular.
     * @return El valor del campo esTitular
     */
    @Column(name = "bEsTitular", precision = 1, scale = 0)
	public Boolean getEsTitular() {
		return esTitular;
	}
	
    
    
    /**
     *  Asigna el valor al campo funcionarioExternoAudienciaId.
     * @param funcionarioExternoAudienciaId
     */
	public void setFuncionarioExternoAudienciaId(
			FuncionarioExternoAudienciaId funcionarioExternoAudienciaId) {
		this.funcionarioExternoAudienciaId = funcionarioExternoAudienciaId;
	}
	
	/**
	 * Asigna el valor al campo FuncionarioExterno.
	 * @param funcionarioExterno
	 */
	public void setFuncionarioExterno(FuncionarioExterno funcionarioExterno) {
		this.funcionarioExterno = funcionarioExterno;
	}
	
	/**
	 * Asigna el valor al campo audiencia.
	 * @param audiencia
	 */
	public void setAudiencia(Audiencia audiencia) {
		this.audiencia = audiencia;
	}
	
	 /**
     * Asigna el valor al campo esPresente.
     * @param esPresente el valor esPresente a asignar
     */
	public void setEsPresente(Boolean esPresente) {
		this.esPresente = esPresente;
	}
	
	/**
     * Asigna el valor al campo esTitular.
     * @param esTitular el valor esTitular a asignar
     */
	public void setEsTitular(Boolean esTitular) {
		this.esTitular = esTitular;
	}
}
