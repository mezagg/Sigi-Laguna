/**
 * Nombre del Programa : Eventos.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 1 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Enumeraci�n para el tipo de eventos.
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
package mx.gob.segob.nsjp.comun.enums.audiencia;

/**
 * Enumeraci�n para el tipo de eventos.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public enum Eventos {
    AUDIENCIA("Audiencia"), RECURSO("Recurso");

    private String nombre;
    
    private Eventos(String nom) {
        this.nombre = nom;
    }
    /**
     * M�todo de acceso al campo nombre.
     * 
     * @return El valor del campo nombre
     */
    public String getNombre() {
        return nombre;
    }
}
