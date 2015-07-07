/**
* Nombre del Programa : Alertas.java
* Autor                            : Juan Manuel Fernandez
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 01/08/2011
* Marca de cambio        : N/A
* Descripcion General    : Enumeración de las alertas disponibles en el sistema
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
package mx.gob.segob.nsjp.comun.enums.seguridad;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeración de las alertas disponibles en el sistema
 * @version 1.0
 * @author Juan Manuel Fernandez
 *
 */
public enum Alertas {
	ROL_NO_DEFINIDO(0L),
	ALERTADETENCION(2385L),
	ALERTA(10L);
	
	private Long valorId;

    private final static Map<Long, Alertas> hash = new HashMap<Long, Alertas>();

    static {
    	Alertas[] acts = Alertas.values();
        int pos = 0;
        while (pos < acts.length) {
            hash.put(acts[pos].getValorId(), acts[pos]);
            pos++;
        }
    }
    private Alertas(Long valorIdPredefinido) {
        this.valorId = valorIdPredefinido;
    }

    public static Alertas getByValor(Long valorIdPredefinido) {
    	Alertas loAlerta = hash.get(valorIdPredefinido);
    	if(loAlerta == null){
    		loAlerta = Alertas.ROL_NO_DEFINIDO;
    		
    	}
        return loAlerta;
    }

    /**
     * Método de acceso al campo valorId.
     * 
     * @return El valor del campo valorId asociado en le BD.
     */
    public Long getValorId() {
        return valorId;
    }
}