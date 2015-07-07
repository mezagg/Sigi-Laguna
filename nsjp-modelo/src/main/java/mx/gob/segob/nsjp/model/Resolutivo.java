package mx.gob.segob.nsjp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Resolutivo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Resolutivo")
public class Resolutivo implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 969207323610049706L;
	private Long resolutivoId;
    private Audiencia audiencia;
    private Date temporizador;
    private String detalle;
    private Set<Mandamiento> mandamientos = new HashSet<Mandamiento>(0);

    // Constructors
    /** default constructor */
    public Resolutivo() {
    }

    /** minimal constructor */
    public Resolutivo(long id, Date time, String texto) {
        this.resolutivoId = id;
        this.temporizador = time;
        this.detalle = texto;
        
        
    }

    /** full constructor
    /**
	 * @param resolutivoId
	 * @param audiencia
	 * @param temporizador
	 * @param detalle
	 * @param mandamientos
	 */
	public Resolutivo(Long resolutivoId, Audiencia audiencia,
			Date temporizador, String detalle) {
		this.resolutivoId = resolutivoId;
		this.audiencia = audiencia;
		this.temporizador = temporizador;
		this.detalle = detalle;
	}

	
    // Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Resolutivo_id", unique = true, nullable = false, precision = 18, scale = 0)
    public Long getResolutivoId() {
        return this.resolutivoId;
    }

    public void setResolutivoId(Long resolutivoId) {
        this.resolutivoId = resolutivoId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Audiencia_id", nullable = false)
    public Audiencia getAudiencia() {
        return this.audiencia;
    }

    public void setAudiencia(Audiencia audiencia) {
        this.audiencia = audiencia;
    }

    @Column(name = "dTemporizador", nullable = false, length = 23)
    public Date getTemporizador() {
        return this.temporizador;
    }

    public void setTemporizador(Date dtemporizador) {
        this.temporizador = dtemporizador;
    }

    @Column(name = "cDetalle", nullable = false)
    public String getDetalle() {
        return this.detalle;
    }

    public void setDetalle(String cdetalle) {
        this.detalle = cdetalle;
    }
    
    /**
     * @return
     */
    @OneToMany(mappedBy="resolutivo",fetch = FetchType.LAZY)
    public Set<Mandamiento> getMandamientos() {
		return mandamientos;
	}

	/**
	 * Asigna el valor al campo mandamientos.
	 * @param mandamientos
	 *            el valor mandamientos a asignar
	 */
	public void setMandamientos(Set<Mandamiento> mandamientos) {
		this.mandamientos = mandamientos;
	}   
}