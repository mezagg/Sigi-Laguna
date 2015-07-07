package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * CorreoElectronico entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CorreoElectronico" )
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "CorreoElectronico_id")
public class CorreoElectronico extends MedioDeContacto {

	// Fields

	private static final long serialVersionUID = -4312213393001105395L;
	private String direccionElectronica;

	// Constructors

	/** default constructor */
	public CorreoElectronico() {
	}


	@Column(name = "cDireccionElectronica", length = 60)
	public String getDireccionElectronica() {
		return this.direccionElectronica;
	}

	public void setDireccionElectronica(String direccionElectronica) {
		this.direccionElectronica = direccionElectronica;
	}

}
