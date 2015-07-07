/**
* Nombre del Programa : EstatusMandamiento.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 25/08/2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.comun.enums.documento;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeraci�n de estatus de un mandamiento judicial
 * @version 1.0
 * @author Emigdio
 *
 */
public enum EstatusMandamiento {

	/**
	 * Estatus activos
	 */
	NO_ATENDIDO(6359L),
	ATENDIDO(6360L),
	EN_PROCESO(2492L),
	SIN_DOCUMENTO_DE_CREACION(9832L),
	SIN_DOCUMENTO_DE_ESTATUS(9833L),
	NO_ENVIADO(9834L),
	ACTUALIZACION_NO_ENVIADA(9840L),
	
	/**
	 * Estatus inactivos
	 */
	CONCLUIDO(6361L),
	CANCELADO(2493L),
	EJECUTADO(2494L);
	
    private Long valorId;
    private final static Map<Long, EstatusMandamiento> hash = new HashMap<Long, EstatusMandamiento>();

    static {
    	EstatusMandamiento[] objs = EstatusMandamiento.values();
        int pos = 0;
        while (pos < objs.length) {
            hash.put(objs[pos].getValorId(), objs[pos]);
            pos++;
        }
    }
    public static EstatusMandamiento getByValor(Long valorIdPredefinido) {
        return hash.get(valorIdPredefinido);
    }

    private EstatusMandamiento(Long valorIdPredefinido) {
        this.valorId = valorIdPredefinido;
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
