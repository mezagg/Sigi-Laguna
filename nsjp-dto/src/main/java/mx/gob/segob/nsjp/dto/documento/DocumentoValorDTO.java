package mx.gob.segob.nsjp.dto.documento;

import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;

public class DocumentoValorDTO extends GenericDTO {

	private static final long serialVersionUID = 1L;
	private Long documentoValorId;
	private ValorDTO estatus;
	private Long documentoId;
	
	public DocumentoValorDTO() {
	}
	
	public DocumentoValorDTO(ValorDTO estatus, Long documentoId) {
		this.estatus = estatus;
		this.documentoId = documentoId;
	}
	public Long getDocumentoValorId() {
		return documentoValorId;
	}
	public void setDocumentoValorId(Long documentoValorId) {
		this.documentoValorId = documentoValorId;
	}
	public ValorDTO getEstatus() {
		return estatus;
	}
	public void setEstatus(ValorDTO estatus) {
		this.estatus = estatus;
	}
	public Long getDocumentoId() {
		return documentoId;
	}
	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}
	
}
