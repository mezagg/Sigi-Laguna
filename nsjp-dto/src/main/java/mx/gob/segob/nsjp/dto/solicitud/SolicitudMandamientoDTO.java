package mx.gob.segob.nsjp.dto.solicitud;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;

public class SolicitudMandamientoDTO extends SolicitudDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1019854865946493888L;
	private ValorDTO tipoMandamiento;
	private MandamientoDTO mandamiento;
	
	
	/**
	 * @return the mandamiento
	 */
	public ValorDTO getTipoMandamiento() {
		return tipoMandamiento;
	}
	
	/**
	 * @param mandamiento the mandamiento to set
	 */
	public void setTipoMandamiento(ValorDTO mandamiento) {
		this.tipoMandamiento = mandamiento;
	}

	/**
	 * M&eacute;todo de acceso al campo mandamiento.
	 * @return El valor del campo mandamiento
	 */
	public MandamientoDTO getMandamiento() {
		return mandamiento;
	}

	/**
	 * Asigna el valor al campo mandamiento.
	 * @param mandamiento el valor mandamiento a asignar
	 */
	public void setMandamiento(MandamientoDTO mandamiento) {
		this.mandamiento = mandamiento;
	}	
}
