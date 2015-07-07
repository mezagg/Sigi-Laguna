/**
 * Nombre del Programa : TipoAudiencia.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 24 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Enumeraci�n para el tipo de audiencias.
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

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeraci�n para el tipo de audiencias.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public enum TipoAudiencia { 

	EXTRACCION(292L), APROBACION_DE_CONVENIO(294L), CONTROL(1714L), IMPUTACION(
			1715L), VINCULACION(1716L), CATEO(1717L), APREHENSION(1718L), JUICIO_ORAL(
			2021L), EJECUCION(2097L), INTERMEDIA(2774L), INDIVIDUALIZACION_DE_SANCION(
			2777L), LECTURA(2780L), SSP(2783L), VERIFICACION_SSP(2786L), MASC(
			2789L), VERIFICACION_MASC(2792L), ABREVIADO(2795L), AMPLIACION_DE_PLAZO_DE_CIERRE_DEINVESTIGACION(
			6456L), AMPLIACION_DE_TERMINO_CONSTITUCIONAL(6459L), ANTICIPO_DE_PRUEBA_URGENTE(
			6462L), AUTORIZACION_JUDICIAL_PARA_PRACTICA_URGENTE_DE_PRUEBA_PERICIAL(
			6465L), COMPARECENCIA_ESPONTANEA(6468L), SOLICITUD_DE_EXTRACCION_DE_MUESTRAS_CORPORALES(
			6480L), ORDEN_DE_COMPARECENCIA(6477L), FALLO_DELIBERATORIO(6501L), OTRO_TIPO_DE_AUDIENCIA(
			6528L), IMPUGNACION_DE_DETERMINACION_MINISTERIAL(6779L);
	
	
    private Long valorId;

    private final static Map<Long, TipoAudiencia> hash = new HashMap<Long, TipoAudiencia>();

    static {
        TipoAudiencia[] acts = TipoAudiencia.values();
        int pos = 0;
        while (pos < acts.length) {
            hash.put(acts[pos].getValorId(), acts[pos]);
            pos++;
        }
    }
    private TipoAudiencia(Long valorIdPredefinido) {
        this.valorId = valorIdPredefinido;
    }

    public static TipoAudiencia getByValor(Long valorIdPredefinido) {
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
