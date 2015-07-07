/**
 * Nombre del Programa : TipoRelacion.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 13 Apr 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Enumeraci�n para los tipos de relaci�n
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
package mx.gob.segob.nsjp.comun.enums.relacion;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeraci�n para los tipos de relaci�n.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public enum TipoRelacion {
	
	//Hace el cambio de acuerdo a la especificaci�n
    IMPLICITA(1L),   //Definidas internamente por el sistema, Ejemplo: involucrado y domicilio 
    EXPLICITA(2L);	 //Definidas por el el usuario, Ejemplo: Relacionar elementos de la aplicaci�n

    private Long valorId;
    private final static Map<Long, TipoRelacion> hash = new HashMap<Long, TipoRelacion>();

    static {
    	TipoRelacion[] acts = TipoRelacion.values();
        int pos = 0;
        while (pos < acts.length) {
            hash.put(acts[pos].getValorId(), acts[pos]);
            pos++;
        }
    }
    private TipoRelacion(Long valorIdPredefinido) {
        this.valorId = valorIdPredefinido;
    }

    public static TipoRelacion getByValor(Long valorIdPredefinido) {
        return hash.get(valorIdPredefinido);
    }

    /**
     * M�todo de acceso al campo valorId.
     * 
     * @return El valor del campo valorId asociado en le BD.
     */
    public Long getValorId() {
        return valorId;
    }
}
