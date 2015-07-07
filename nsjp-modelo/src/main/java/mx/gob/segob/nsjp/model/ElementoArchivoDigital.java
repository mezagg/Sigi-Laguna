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
 * ElementoAdjuntos entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ElementoArchivoDigital")
public class ElementoArchivoDigital implements java.io.Serializable {

	private static final long serialVersionUID = 1062097967985679167L;
	
	private ElementoArchivoDigitalId id;
	private Elemento elemento;
	private ArchivoDigital archivoDigital;

	// Constructors

	/** default constructor */
	public ElementoArchivoDigital() {
	}

	/** full constructor */
	public ElementoArchivoDigital(ElementoArchivoDigitalId id, Elemento elemento,
			ArchivoDigital archivoDigital) {
		this.id = id;
		this.elemento = elemento;
		this.archivoDigital = archivoDigital;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "elementoId", column = @Column(name = "Elemento_id", nullable = false, precision = 18, scale = 0)),
			@AttributeOverride(name = "archivoDigitalId", column = @Column(name = "ArchivoDigital_id", nullable = false, precision = 18, scale = 0)) })
	public ElementoArchivoDigitalId getId() {
		return this.id;
	}

	public void setId(ElementoArchivoDigitalId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Elemento_id", nullable = false, insertable = false, updatable = false)
	public Elemento getElemento() {
		return this.elemento;
	}

	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ArchivoDigital_id", nullable = false, insertable = false, updatable = false)
	public ArchivoDigital getArchivoDigital() {
		return this.archivoDigital;
	}

	public void setArchivoDigital(ArchivoDigital archivoDigital) {
		this.archivoDigital = archivoDigital;
	}

}