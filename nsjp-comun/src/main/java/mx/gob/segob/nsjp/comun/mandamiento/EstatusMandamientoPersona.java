/**
 * Nombre del Programa		: EstatusMandamientoPersona.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP							Fecha: 19/06/2013
 * Marca de cambio			: N/A
 * Descripcion General		: Enum para los estados de Mandamiento Persona
 * Programa Dependiente		: N/A
 * Programa Subsecuente		: N/A
 * Cond. de ejecucion       : N/A
 * Dias de ejecucion        : N/A                           Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                    : N/A
 * Compania               	: N/A
 * Proyecto                 : N/A                           Fecha: N/A
 * Modificacion           	: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.comun.mandamiento;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public enum EstatusMandamientoPersona {
	
	NO_ATENDIDO(9835L),
	EN_PROCESO(9836L),
	ATENDIDO(9837L),
	CANCELADO(9838L),
	CONCLUIDO(9839L);
	
	
	private Long valorId;
    private final static Map<Long, EstatusMandamientoPersona> hash = new HashMap<Long, EstatusMandamientoPersona>();

    static {
    	EstatusMandamientoPersona[] objs = EstatusMandamientoPersona.values();
        int pos = 0;
        while (pos < objs.length) {
            hash.put(objs[pos].getValorId(), objs[pos]);
            pos++;
        }
    }
    public static EstatusMandamientoPersona getByValor(Long valorIdPredefinido) {
        return hash.get(valorIdPredefinido);
    }

    private EstatusMandamientoPersona(Long valorIdPredefinido) {
        this.valorId = valorIdPredefinido;
    }

    /**
     * M&eacute;todo de acceso al campo valorId.
     * 
     * @return El valor del campo valorId asociado en le BD.
     */
    public Long getValorId() {
        return valorId;
    }

}
