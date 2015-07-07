package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Causa")
public class Causa implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8900024566340593763L;
	
	private Long causaId;
	private String nombreCausa;
	private String descripcionCausa;
	private Causa causaPadre;
	private Boolean esActivo;

	/** default constructor */
	public Causa() {
	}

	public Causa(Long causaId) {
		super();
		this.causaId = causaId;
	}


	public Causa(Long causaId, String nombreCausa, String descripcionCausa,
			Causa causaPadre) {
		super();
		this.causaId = causaId;
		this.nombreCausa = nombreCausa;
		this.descripcionCausa = descripcionCausa;
		this.causaPadre = causaPadre;
	}

	
	@Id
	@Column(name = "Causa_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getCausaId() {
		return this.causaId;
	}

	public void setCausaId(Long causaId) {
		this.causaId = causaId;
	}
	
	@Column(name = "cNombre", nullable = false, length = 100)
	public String getNombreCausa() {
		return this.nombreCausa;
	}

	public void setNombreCausa(String nombreCausa) {
		this.nombreCausa = nombreCausa;
	}

	@Column(name = "cDescripcion", nullable = false, length = 100)
	public String getDescripcionCausa() {
		return this.descripcionCausa;
	}

	public void setDescripcionCausa(String descripcionCausa) {
		this.descripcionCausa = descripcionCausa;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CausaPadre_id")
	public Causa getCausaPadre() {
		return this.causaPadre;
	}

	public void setCausaPadre(Causa causaPadre) {
		this.causaPadre = causaPadre;
	}
	
	@Column(name = "bEsActivo", precision = 1, scale = 0)
    public Boolean getEsActivo() {
        return esActivo;
    }

    public void setEsActivo(Boolean esActivo) {
        if (esActivo == null)
            this.esActivo = true;
        else
            this.esActivo = esActivo;
    }

    
	
}