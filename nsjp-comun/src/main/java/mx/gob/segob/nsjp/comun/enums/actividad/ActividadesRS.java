/**
 * NGENERAR_OFICIO_DE_INGRESO_A_REINSERCION_SOCIAL(337L, "genOficioIngresoRS");ombre del Programa : Actividades.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 12 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Enumaraci�n para las actividades
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
package mx.gob.segob.nsjp.comun.enums.actividad;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumaraci&oacute;n para las actividades de Reinserci&oacute;n Social.
 * 
 * @version 1.0
 * @author AntonioBV 
 */

public enum ActividadesRS {
	
	GENERAR_OFICIO_DE_INGRESO_A_REINSERCION_SOCIAL(410L, "genOficioIngresoRS"),
	ELABORAR_CERTIFICADO_PSICOFISICO(376L, null),
	ELABORAR_CERTIFICADO_DE_LESIONES(378L, null),
	ELABORAR_CERTIFICADO_QUIMICO(380L, null),
	ELABORAR_INFORME_FINAL_DE_REINSERCION_SOCIAL_DEL_SENTENCIADO(408L, "informeFinalRS"),
	GENERAR_ACUSE_RECIBO_PERTENENCIAS(6349L, null),
	AVISAR_DE_INGRESO_A_CERESO(372L, "avisarIngresoCERESO"),
	ELABORAR_CERTIFICADO_DE_LIBERACION(366L, null),
	ENTREGAR_VALORES_Y_EFECTOS_DEPOSITADOS(6548L,null),
	GENERAR_PLAN_REINSERCION_SOCIAL(6340L,null),
	GENERAR_ACUERDO_DE_RECHAZO_DE_REINSERCION_SOCIAL(6900L,null),
	GENERAR_ACUERDO_DE_AUTORIZACION_DE_REINSERCION_SOCIAL(6898L,null),
	ELABORAR_ACUERDO_RECEPCION_SENTENCIA(7086L,null),
	SOLICITAR_CONSTANCIAS_CERERESO(7089L,null),
	SOLICITAR_ESTUDIOS_CTI(7092L,null),
	ELABORAR_SOLICITUD_REMISION_PARCIAL_PENA(358L,null),
	ELABORAR_SOLICITUD_LIBERTAD_CONDICIONAL(7136L,null),
	ELABORAR_SOLICITUD_PRELIBERTAD(7138L,null),
	ELABORAR_SOLICITUD_INDULTO(7140L,null),
	ELABORAR_SOLICITUD_REPARACION_DANIO(7142L,null),
	ELABORAR_SOLICITUD_VISITA_CONYUGAL(7144L,null),
	GENERAR_ACUERDO_DE_AUTORIZACION_DE_REMISION_PARCIAL_PENA(7152L, null),
	GENERAR_ACUERDO_DE_RECHAZO_DE_REMISION_PARCIAL_PENA(7154L, null),
	ELABORAR_SOLICITUD_DECLARACION_JUDICIAL_EXTINCION_PRISION(7158L, null),
	ELABORAR_SOLICITUD_PRESCRIPCION_PENA_PRISION(7160L, null),
	ELABORAR_SOLICITUD_CERTIFICADO_EXTINCION_PENAS_PRISION(7162L, null),
	ELABORAR_SOLICITUD_PROCEDIMIENTO_EXTINCION_MULTA(7164L, null),
	ELABORAR_SOLICITUD_PROCEDIMIENTO_EXTINCION_REPARACION_DANIO(7166L, null),
	ELABORAR_SOLICITUD_CERTIFICADO_EXTINCION_PENAS_PECUNIARIAS(7168L, null),
	ELABORAR_SOLICITUD_AUTO_FINALIZACION_PROCEDIMIENTO_EJECUCION(7170L, null),
	GENERAR_ACUERDO_AUTORIZACION_DECLARACION_JUDICIAL_EXTINCION_PRISION(7179L, null),
	GENERAR_ACUERDO_RECHAZO_DECLARACION_JUDICIAL_EXTINCION_PRISION(7181L, null),
	GENERAR_ACUERDO_AUTORIZACION_LIBERTAD_CONDICIONAL(7447L, null),
	GENERAR_ACUERDO_RECHAZO_LIBERTAD_CONDICIONAL(7449L, null),
	GENERAR_ACUERDO_AUTORIZACION_PRELIBERTAD(7453L, null),
	GENERAR_ACUERDO_RECHAZO_PRELIBERTAD(7455L, null),
	GENERAR_ACUERDO_AUTORIZACION_INDULTO(7459L, null),
	GENERAR_ACUERDO_RECHAZO_INDULTO(7461L, null),
	GENERAR_ACUERDO_AUTORIZACION_REPARACION_DANIO(7465L, null),
	GENERAR_ACUERDO_RECHAZO_REPARACION_DANIO(7467L, null),
	GENERAR_ACUERDO_AUTORIZACION_VISITA_CONYUGAL(7471L, null),
	GENERAR_ACUERDO_RECHAZO_VISITA_CONYUGAL(7473L, null),
	GENERAR_ACUERDO_AUTORIZACION_EXTINCION_MULTA(7483L, null),
	GENERAR_ACUERDO_RECHAZO_EXTINCION_MULTA(7485L, null),
	GENERAR_ACUERDO_AUTORIZACION_EXTINCION_REPARACION_DANIO(7489L, null),
	GENERAR_ACUERDO_RECHAZO_EXTINCION_REPARACION_DANIO(7491L, null),
	ELABORAR_SOLICITUD_INFORMACION_DGPRS(7495L, null),
	COMUNICAR_ACUERDO_RESOLUCION_DGPRS(7497L, null),
	INFORMAR_INTEGRACION_PROCEDIMIENTO_EJECUCION(7569L, null),
	REGISTRAR_ACUERDO_PROCEDIMIENTO_EJECUCION(7571L, null);
	
    private Long valorId;
    private String cForward;
   
    private final static Map<Long, ActividadesRS> HASH = new HashMap<Long, ActividadesRS>();

    static {
        final ActividadesRS[] acts = ActividadesRS.values();
        int pos = 0;
        while (pos < acts.length) {
            HASH.put(acts[pos].getValorId(), acts[pos]);
            pos++;
        }
    }
    
    private ActividadesRS(final Long valorId) {
        this.valorId = valorId;
    }

    private ActividadesRS(final Long valorId, final String cForward) {
        this.valorId = valorId;
        this.cForward = cForward;
    }    
    
    public static ActividadesRS getByValor(final Long valorId) {
        return HASH.get(valorId);
    }

    /**
     * Método de acceso al campo valorId.
     * 
     * @return El valor del campo valorId asociado en le BD.
     */
    public Long getValorId() {
        return valorId;
    }

	/**
	 * @return the cForward
	 */
	public String getcForward() {
		return cForward;
	}
}
