/**
* Nombre del Programa : TipoSentencia.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 20/10/2011
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
package mx.gob.segob.nsjp.comun.enums.sentencia;

import java.util.HashMap;
import java.util.Map;

/**
 * Lista de los Tipos de Sentencias
 * @version 1.0
 * @author GustavoBP
 *
 */
public enum TipoSentencia {
	ABSOLUTORIA(322L, 189L),
	ALTERNATIVA(323L, 189L),
	PRIVATIVA_DE_LA_LIBERTAD(324L, 189L);
   
    private Long valorId;
    private Long campoCatalogoId;

    private final static Map<Long, TipoSentencia> hash = new HashMap<Long, TipoSentencia>();

    static {
    	TipoSentencia[] objs = TipoSentencia.values();
        int pos = 0;
        while (pos < objs.length) {
            hash.put(objs[pos].getValorId(), objs[pos]);
            pos++;
        }
    }
    public static TipoSentencia getByValor(Long valorIdPredefinido) {
        return hash.get(valorIdPredefinido);
    }

    private TipoSentencia(Long valorIdPredefinido) {
        this.valorId = valorIdPredefinido;
    }
    
    private TipoSentencia(Long valorIdPredefinido, Long campoCatalogoId) {
        this.valorId = valorIdPredefinido;
        this.campoCatalogoId = campoCatalogoId;
    }    

    /**
     * M�todo de acceso al campo valorId.
     * 
     * @return El valor del campo valorId asociado en le BD.
     */
    public Long getValorId() {
        return valorId;
    }

	/**
	 * @return the campoCatalogoId
	 */
	public Long getCampoCatalogoId() {
		return campoCatalogoId;
	}
}
