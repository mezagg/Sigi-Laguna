
package mx.gob.segob.nsjp.ws.cliente.administrarPermisosAudiencia;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for codigoError.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="codigoError">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FORMATO"/>
 *     &lt;enumeration value="PARAMETROS_INSUFICIENTES"/>
 *     &lt;enumeration value="EJCUCION_OPERACION_ESTADO_INCORRECTO"/>
 *     &lt;enumeration value="RANGO_FECHAS_CRUZADAS"/>
 *     &lt;enumeration value="ERROR_COMUNICACION"/>
 *     &lt;enumeration value="ERROR_TRANSORMACION_FECHAS"/>
 *     &lt;enumeration value="INFORMACION_PARAMETROS_ERRONEA"/>
 *     &lt;enumeration value="SALA_OCUPADA"/>
 *     &lt;enumeration value="DOCUMENTO_YA_ASOCIADO"/>
 *     &lt;enumeration value="AUDIENCIA_CANCELADA"/>
 *     &lt;enumeration value="FALLA_CANCELACION_AUDIENCIA"/>
 *     &lt;enumeration value="CLAVE_ROMANA_DISTRITO_INEXISTENTE"/>
 *     &lt;enumeration value="INCOMPATIBILIDAD_DE_CATALOGOS_ENTRE_INSTITUCIONES"/>
 *     &lt;enumeration value="FUNCIONARIOS_NO_DISPONILBES"/>
 *     &lt;enumeration value="CLAVE_DELITO_INTERINSTITUCIONAL_INEXISTENTE"/>
 *     &lt;enumeration value="FALLA_OPERACION_BD"/>
 *     &lt;enumeration value="AUDIENCIA_FINALIZADA"/>
 *     &lt;enumeration value="SIN_CASO_ASOCIADO"/>
 *     &lt;enumeration value="CLAVE_DISTRITO_INTERINSTITUCIONAL_INEXISTENTE"/>
 *     &lt;enumeration value="NO_EXISTE_EXPEDIENTE"/>
 *     &lt;enumeration value="NUM_EXP_CON_SOLICITUD_ACTIVA_DE_CARPETA_DE_INVESTIGACION"/>
 *     &lt;enumeration value="MISMO_FUNCIONARIO"/>
 *     &lt;enumeration value="NO_SE_REGISTRO_LA_SOLICITUD_DE_DEFENSOR"/>
 *     &lt;enumeration value="IMPOSIBLE_GENERAR_FOLIO_RELACION_DELITO_PERSONA"/>
 *     &lt;enumeration value="SIN_ACTA_DE_AUDIENCIA"/>
 *     &lt;enumeration value="ACTA_DE_AUDIENCIA_CON_GUARDADO_PARCIAL"/>
 *     &lt;enumeration value="MISMO_NUMERO_DE_EXPEDIENTE"/>
 *     &lt;enumeration value="YA_EXISTE_EL_NUMERO_EXPEDIENTE"/>
 *     &lt;enumeration value="FUNCIONARIO_INEXISTENTE"/>
 *     &lt;enumeration value="AUDIENCIA_INEXISTENTE"/>
 *     &lt;enumeration value="UNA_O_MAS_RELACIONES_YA_ESTAN_ASOCIADAS_AL_TIPO_DE_MANDAMIENTO"/>
 *     &lt;enumeration value="SESSION_NULA"/>
 *     &lt;enumeration value="IMPOSIBLE_GENERAR_SOLICITUD_DE_AUDIENCIA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "codigoError")
@XmlEnum
public enum CodigoError {

    FORMATO,
    PARAMETROS_INSUFICIENTES,
    EJCUCION_OPERACION_ESTADO_INCORRECTO,
    RANGO_FECHAS_CRUZADAS,
    ERROR_COMUNICACION,
    ERROR_TRANSORMACION_FECHAS,
    INFORMACION_PARAMETROS_ERRONEA,
    SALA_OCUPADA,
    DOCUMENTO_YA_ASOCIADO,
    AUDIENCIA_CANCELADA,
    FALLA_CANCELACION_AUDIENCIA,
    CLAVE_ROMANA_DISTRITO_INEXISTENTE,
    INCOMPATIBILIDAD_DE_CATALOGOS_ENTRE_INSTITUCIONES,
    FUNCIONARIOS_NO_DISPONILBES,
    CLAVE_DELITO_INTERINSTITUCIONAL_INEXISTENTE,
    FALLA_OPERACION_BD,
    AUDIENCIA_FINALIZADA,
    SIN_CASO_ASOCIADO,
    CLAVE_DISTRITO_INTERINSTITUCIONAL_INEXISTENTE,
    NO_EXISTE_EXPEDIENTE,
    NUM_EXP_CON_SOLICITUD_ACTIVA_DE_CARPETA_DE_INVESTIGACION,
    MISMO_FUNCIONARIO,
    NO_SE_REGISTRO_LA_SOLICITUD_DE_DEFENSOR,
    IMPOSIBLE_GENERAR_FOLIO_RELACION_DELITO_PERSONA,
    SIN_ACTA_DE_AUDIENCIA,
    ACTA_DE_AUDIENCIA_CON_GUARDADO_PARCIAL,
    MISMO_NUMERO_DE_EXPEDIENTE,
    YA_EXISTE_EL_NUMERO_EXPEDIENTE,
    FUNCIONARIO_INEXISTENTE,
    AUDIENCIA_INEXISTENTE,
    UNA_O_MAS_RELACIONES_YA_ESTAN_ASOCIADAS_AL_TIPO_DE_MANDAMIENTO,
    SESSION_NULA,
    IMPOSIBLE_GENERAR_SOLICITUD_DE_AUDIENCIA;

    public String value() {
        return name();
    }

    public static CodigoError fromValue(String v) {
        return valueOf(v);
    }

}
