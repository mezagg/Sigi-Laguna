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

@SuppressWarnings("serial")
@Entity
@Table(name = "DocumentoValor")
@Inheritance(strategy = InheritanceType.JOINED)
public class DocumentoValor implements java.io.Serializable {


    private Long documentoValorId;
    private Valor estatusDocumento;
    private Long idDocumento;

    public DocumentoValor() {
	}

	public DocumentoValor(Long documentoValorId) {
		this.documentoValorId = documentoValorId;
	}

	public DocumentoValor(Long documentoValorId, Valor estatusDocumento,
			Long idDocumento) {
		this.documentoValorId = documentoValorId;
		this.estatusDocumento = estatusDocumento;
		this.idDocumento = idDocumento;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocumentoValor_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getDocumentoValorId() {
		return documentoValorId;
	}

	public void setDocumentoValorId(Long documentoValorId) {
		this.documentoValorId = documentoValorId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EstatusDocumento_val", nullable = false)
	public Valor getEstatusDocumento() {
		return estatusDocumento;
	}

	public void setEstatusDocumento(Valor estatusDocumento) {
		this.estatusDocumento = estatusDocumento;
	}

	@Column(name = "Documento_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

}
