/**
 * Nombre del Programa : NotificacionWSDTO.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 26 Sep 2011
 * Marca de cambio        : N/A
 * Descripcion General    : DTO para el servicio d ela notificaci�n.
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
package mx.gob.segob.nsjp.dto.documento;

/**
 * DTO para el servicio d ela notificaci�n.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public class NotificacionWSDTO extends DocumentoWSDTO {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3971811078933385213L;
	private String folioNotificacion;
    private String consecutivoNotificacion;
    /**
     * M�todo de acceso al campo folioNotificacion.
     * 
     * @return El valor del campo folioNotificacion
     */
    public String getFolioNotificacion() {
        return folioNotificacion;
    }
    /**
     * Asigna el valor al campo folioNotificacion.
     * 
     * @param folioNotificacion
     *            el valor folioNotificacion a asignar
     */
    public void setFolioNotificacion(String folioNotificacion) {
        this.folioNotificacion = folioNotificacion;
    }
    /**
     * M�todo de acceso al campo consecutivoNotificacion.
     * 
     * @return El valor del campo consecutivoNotificacion
     */
    public String getConsecutivoNotificacion() {
        return consecutivoNotificacion;
    }
    /**
     * Asigna el valor al campo consecutivoNotificacion.
     * 
     * @param consecutivoNotificacion
     *            el valor consecutivoNotificacion a asignar
     */
    public void setConsecutivoNotificacion(String consecutivoNotificacion) {
        this.consecutivoNotificacion = consecutivoNotificacion;
    }
}
