package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author JoseFP
 *
 */
@Entity
@Table(name = "RelacionSolicitudDocumentoFuncionario")
public class RelacionSolicitudDocumentoFuncionario implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long relacionId;
	private Solicitud solicitud;
	private Documento documento;
	private Funcionario funcionario;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Relacion_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getRelacionId() {
		return this.relacionId;
	}

	public void setRelacionId(Long relacionId) {
		this.relacionId = relacionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Solicitud_id", nullable = false)
	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Documento_id", nullable = false)
	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Funcionario_id", nullable = false)
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

}