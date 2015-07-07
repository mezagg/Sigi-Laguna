package mx.gob.segob.nsjp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * SolicitudMandamiento entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SolicitudMandamiento")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "SolicitudMandamiento_id")
public class SolicitudMandamiento extends Solicitud{

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = -7827180114047135848L;
	private Valor tipoMandamiento;
	private Mandamiento mandamiento;

    // Constructors

    /** default constructor */
    public SolicitudMandamiento() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoMandamiento_val")
    public Valor getTipoMandamiento() {
        return this.tipoMandamiento;
    }

    public void setTipoMandamiento(Valor valor) {
        this.tipoMandamiento = valor;
    }

	/**
	 * M&eacute;todo de acceso al campo mandamiento.
	 * @return El valor del campo mandamiento
	 */
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Mandamiento_id", nullable = true)
	public Mandamiento getMandamiento() {
		return mandamiento;
	}

	/**
	 * Asigna el valor al campo mandamiento.
	 * @param mandamiento el valor mandamiento a asignar
	 */
	public void setMandamiento(Mandamiento mandamiento) {
		this.mandamiento = mandamiento;
	}

}