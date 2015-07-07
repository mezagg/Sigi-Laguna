package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * MedioDeContacto entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MedioDeContacto")
@Inheritance(strategy = InheritanceType.JOINED)
public class MedioDeContacto implements java.io.Serializable {

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = -7302765323223937555L;
	private Long medioDeContactoId;
    private Persona persona;
    private Funcionario funcionario;
    private Implicado implicado;



    // Constructors

    /** default constructor */
    public MedioDeContacto() {
    }

    /** minimal constructor */
    public MedioDeContacto(Long medioDeContactoId) {
        this.medioDeContactoId = medioDeContactoId;
    }
    
    /** minimal constructor */
    public MedioDeContacto(Long medioDeContactoId, Persona persona) {
        this.medioDeContactoId = medioDeContactoId;
        this.persona = persona;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MedioDeContacto_id", unique = true, nullable = false, precision = 18, scale = 0)
    public Long getMedioDeContactoId() {
        return this.medioDeContactoId;
    }

    public void setMedioDeContactoId(Long medioDeContactoId) {
        this.medioDeContactoId = medioDeContactoId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Persona_id", nullable = true)    
    public Persona getPersona() {
        return this.persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iClaveFuncionario", nullable = true)    
	public Funcionario getFuncionario() {
		return funcionario;
	}  

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	/**
	 * M�todo de acceso al campo implicado.
	 * @return El valor del campo implicado
	 */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Implicado_id", nullable = true)
	public Implicado getImplicado() {
		return implicado;
	}

	/**
	 * Asigna el valor al campo implicado.
	 * @param implicado el valor implicado a asignar
	 */
	public void setImplicado(Implicado implicado) {
		this.implicado = implicado;
	}
	
	
}
