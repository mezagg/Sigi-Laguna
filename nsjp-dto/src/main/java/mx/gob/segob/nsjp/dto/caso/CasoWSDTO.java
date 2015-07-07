/**
 * Nombre del Programa : CasoWSDTO.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21 Aug 2011
 * Marca de cambio        : N/A
 * Descripcion General    : DTO para relicar la informaci�n del caso
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dto.caso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoWSDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoWSDTO;

/**
 * DTO para relicar la informaci�n del caso.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public class CasoWSDTO extends GenericWSDTO {

    /**
     * Serial
     */
    private static final long serialVersionUID = 6228874715595848841L;
    private String numeroGeneralCaso;
    private Date fechaApertura;
    /**
     * Para replicar la informaci�n del caso con los siguientes �tems:
     * <ul>
     * <li>numeroGeneralCaso</li>
     * <li>distrito origen</li>
     * <li>fechaApertura</li>
     * <li>Probables responsables (con <code>delitosCometidos</code>).</li>
     * <li>V�ctimas.</li>
     * </ul>
     * 
     */
    private List<InvolucradoWSDTO> involucradosDTO = new ArrayList<InvolucradoWSDTO>();
    
    /**
     * Distrito origen donde fue enviado
     * Actualmente se ocupa SOLO para DEFENSORIA.
     */
    private CatDistritoWSDTO distrito;
    
    /**
     * M�todo de acceso al campo numeroGeneralCaso.
     * 
     * @return El valor del campo numeroGeneralCaso
     */
    public String getNumeroGeneralCaso() {
        return numeroGeneralCaso;
    }
    /**
     * Asigna el valor al campo numeroGeneralCaso.
     * 
     * @param numeroGeneralCaso
     *            el valor numeroGeneralCaso a asignar
     */
    public void setNumeroGeneralCaso(String numeroGeneralCaso) {
        this.numeroGeneralCaso = numeroGeneralCaso;
    }
    /**
     * M�todo de acceso al campo fechaApertura.
     * 
     * @return El valor del campo fechaApertura
     */
    public Date getFechaApertura() {
        return fechaApertura;
    }
    /**
     * Asigna el valor al campo fechaApertura.
     * 
     * @param fechaApertura
     *            el valor fechaApertura a asignar
     */
    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }
    /**
     * M�todo de acceso al campo involucradosDTO.
     * 
     * @return El valor del campo involucradosDTO
     */
    public List<InvolucradoWSDTO> getInvolucradosDTO() {
        return involucradosDTO;
    }
    /**
     * Asigna el valor al campo involucradosDTO.
     * 
     * @param involucradosDTO
     *            el valor involucradosDTO a asignar
     */
    public void setInvolucradosDTO(List<InvolucradoWSDTO> involucradosDTO) {
        this.involucradosDTO = involucradosDTO;
    }
	/**
	 * @return the distrito
	 */
	public CatDistritoWSDTO getDistrito() {
		return distrito;
	}
	/**
	 * @param distrito the distrito to set
	 */
	public void setDistrito(CatDistritoWSDTO distrito) {
		this.distrito = distrito;
	}

}
