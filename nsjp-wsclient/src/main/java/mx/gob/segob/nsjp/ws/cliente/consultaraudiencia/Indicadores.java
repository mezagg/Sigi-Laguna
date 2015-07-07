
package mx.gob.segob.nsjp.ws.cliente.consultaraudiencia;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for indicadores.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="indicadores">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INDICADOR_1"/>
 *     &lt;enumeration value="INDICADOR_2"/>
 *     &lt;enumeration value="INDICADOR_4"/>
 *     &lt;enumeration value="INDICADOR_6"/>
 *     &lt;enumeration value="INDICADOR_7"/>
 *     &lt;enumeration value="INDICADOR_8"/>
 *     &lt;enumeration value="INDICADOR_9"/>
 *     &lt;enumeration value="INDICADOR_10"/>
 *     &lt;enumeration value="INDICADOR_11"/>
 *     &lt;enumeration value="INDICADOR_12"/>
 *     &lt;enumeration value="INDICADOR_13"/>
 *     &lt;enumeration value="INDICADOR_14"/>
 *     &lt;enumeration value="INDICADOR_17"/>
 *     &lt;enumeration value="INDICADOR_18"/>
 *     &lt;enumeration value="INDICADOR_24"/>
 *     &lt;enumeration value="INDICADOR_25"/>
 *     &lt;enumeration value="INDICADOR_26"/>
 *     &lt;enumeration value="INDICADOR_29"/>
 *     &lt;enumeration value="INDICADOR_30"/>
 *     &lt;enumeration value="INDICADOR_32"/>
 *     &lt;enumeration value="INDICADOR_33"/>
 *     &lt;enumeration value="INDICADOR_34"/>
 *     &lt;enumeration value="INDICADOR_36"/>
 *     &lt;enumeration value="INDICADOR_37"/>
 *     &lt;enumeration value="INDICADOR_38"/>
 *     &lt;enumeration value="INDICADOR_39"/>
 *     &lt;enumeration value="INDICADOR_42"/>
 *     &lt;enumeration value="INDICADOR_47"/>
 *     &lt;enumeration value="INDICADOR_48"/>
 *     &lt;enumeration value="INDICADOR_51"/>
 *     &lt;enumeration value="INDICADOR_52"/>
 *     &lt;enumeration value="INDICADOR_53"/>
 *     &lt;enumeration value="INDICADOR_55"/>
 *     &lt;enumeration value="INDICADOR_56"/>
 *     &lt;enumeration value="INDICADOR_57"/>
 *     &lt;enumeration value="INDICADOR_58"/>
 *     &lt;enumeration value="INDICADOR_59"/>
 *     &lt;enumeration value="INDICADOR_60"/>
 *     &lt;enumeration value="INDICADOR_61"/>
 *     &lt;enumeration value="INDICADOR_62"/>
 *     &lt;enumeration value="INDICADOR_63"/>
 *     &lt;enumeration value="INDICADOR_64"/>
 *     &lt;enumeration value="INDICADOR_65"/>
 *     &lt;enumeration value="INDICADOR_66"/>
 *     &lt;enumeration value="INDICADOR_67"/>
 *     &lt;enumeration value="INDICADOR_68"/>
 *     &lt;enumeration value="INDICADOR_69"/>
 *     &lt;enumeration value="INDICADOR_70"/>
 *     &lt;enumeration value="INDICADOR_71"/>
 *     &lt;enumeration value="INDICADOR_72"/>
 *     &lt;enumeration value="INDICADOR_73"/>
 *     &lt;enumeration value="INDICADOR_74"/>
 *     &lt;enumeration value="INDICADOR_75"/>
 *     &lt;enumeration value="INDICADOR_76"/>
 *     &lt;enumeration value="INDICADOR_77"/>
 *     &lt;enumeration value="INDICADOR_78"/>
 *     &lt;enumeration value="INDICADOR_79"/>
 *     &lt;enumeration value="INDICADOR_80"/>
 *     &lt;enumeration value="INDICADOR_81"/>
 *     &lt;enumeration value="INDICADOR_82"/>
 *     &lt;enumeration value="INDICADOR_83"/>
 *     &lt;enumeration value="INDICADOR_84"/>
 *     &lt;enumeration value="INDICADOR_85"/>
 *     &lt;enumeration value="INDICADOR_86"/>
 *     &lt;enumeration value="INDICADOR_87"/>
 *     &lt;enumeration value="INDICADOR_88"/>
 *     &lt;enumeration value="INDICADOR_89"/>
 *     &lt;enumeration value="INDICADOR_90"/>
 *     &lt;enumeration value="INDICADOR_91"/>
 *     &lt;enumeration value="INDICADOR_92"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "indicadores")
@XmlEnum
public enum Indicadores {

    INDICADOR_1,
    INDICADOR_2,
    INDICADOR_4,
    INDICADOR_6,
    INDICADOR_7,
    INDICADOR_8,
    INDICADOR_9,
    INDICADOR_10,
    INDICADOR_11,
    INDICADOR_12,
    INDICADOR_13,
    INDICADOR_14,
    INDICADOR_17,
    INDICADOR_18,
    INDICADOR_24,
    INDICADOR_25,
    INDICADOR_26,
    INDICADOR_29,
    INDICADOR_30,
    INDICADOR_32,
    INDICADOR_33,
    INDICADOR_34,
    INDICADOR_36,
    INDICADOR_37,
    INDICADOR_38,
    INDICADOR_39,
    INDICADOR_42,
    INDICADOR_47,
    INDICADOR_48,
    INDICADOR_51,
    INDICADOR_52,
    INDICADOR_53,
    INDICADOR_55,
    INDICADOR_56,
    INDICADOR_57,
    INDICADOR_58,
    INDICADOR_59,
    INDICADOR_60,
    INDICADOR_61,
    INDICADOR_62,
    INDICADOR_63,
    INDICADOR_64,
    INDICADOR_65,
    INDICADOR_66,
    INDICADOR_67,
    INDICADOR_68,
    INDICADOR_69,
    INDICADOR_70,
    INDICADOR_71,
    INDICADOR_72,
    INDICADOR_73,
    INDICADOR_74,
    INDICADOR_75,
    INDICADOR_76,
    INDICADOR_77,
    INDICADOR_78,
    INDICADOR_79,
    INDICADOR_80,
    INDICADOR_81,
    INDICADOR_82,
    INDICADOR_83,
    INDICADOR_84,
    INDICADOR_85,
    INDICADOR_86,
    INDICADOR_87,
    INDICADOR_88,
    INDICADOR_89,
    INDICADOR_90,
    INDICADOR_91,
    INDICADOR_92;

    public String value() {
        return name();
    }

    public static Indicadores fromValue(String v) {
        return valueOf(v);
    }

}
