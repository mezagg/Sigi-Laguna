package mx.gob.segob.nsjp.dto.delito;

import mx.gob.segob.nsjp.dto.base.GenericDTO;

public class CausaDTO extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8528739317757467468L;
	
	private Long causaId;
	private String nombreCausa;
	private String descripcionCausa;
	private CausaDTO causaPadre; 
	
	/**
	 * Constructor por Default
	 */
	public CausaDTO(){
		
	}
	
	public CausaDTO(Long causaId) {
		super();
		this.causaId = causaId;
	}

	public CausaDTO(Long causaId, String nombreCausa, String descripcionCausa, CausaDTO causaPadre) {
		super();
		this.causaId = causaId;
		this.nombreCausa = nombreCausa;
		this.descripcionCausa = descripcionCausa;
		this.causaPadre = causaPadre;
	}
	

	public Long getCausaId() {
		return causaId;
	}

	public void setCausaId(Long causaId) {
		this.causaId = causaId;
	}

	public String getNombreCausa() {
		return nombreCausa;
	}

	public void setNombreCausa(String nombreCausa) {
		this.nombreCausa = nombreCausa;
	}

	public String getDescripcionCausa() {
		return descripcionCausa;
	}

	public void setDescripcionCausa(String descripcionCausa) {
		this.descripcionCausa = descripcionCausa;
	}

	public CausaDTO getCausaPadre() {
		return causaPadre;
	}

	public void setCausaPadre(CausaDTO causaPadre) {
		this.causaPadre = causaPadre;
	}

	
}
