/**
* Nombre del Programa : Roles.java
* Autor                            : Emigdio Hern�ndez
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 01/08/2011
* Marca de cambio        : N/A
* Descripcion General    : Enumeraci�n de los roles disponibles en el sistema
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
 * Enumeraci�n de los roles disponibles en el sistema
 * @version 1.0
 * @author Emigdio Hern�ndez
 *
 */
public enum Roles {
	ROL_NO_DEFINIDO(0L),
	REGINICIAL(1L), 
	ADMINAT(2L), 
	ATPENAL(3L), 
	VISITADOR(4L), 
	FACILITADOR(5L), 
	COORDINADORJAR(6L), 
	AGENTEMP(7L), 
	COORDINADORAMP(8L), 
	COORDINADORAMPGENERAL(57L),
	POLICIAMINISTER(9L), 
	UAVD(10L), 
	ALMACENV(11L), 
	PERITOAMP(12L), 
	COORDINADORDEF(13L), 
	DEFENSOR(14L), 
	DEFENSORATE(15L), 
	ENCARGADOCAUSA(16L), 
	ENCARGADOSALA(17L), 
	ATENCIONPUBLICO(18L), 
	ENCARGADOINF(19L), 
	NOTIFICADORV(20L), 
	ENCARGADOSEGIN(21L), 
	TRANSCRIPTORPJ(22L), 
	ADMONPJ(23L), 
	JUEZPJ(24L), 
	ENCARGADODGEPMC(25L), 
	OFEJECUCION(26L), 
	ADMINEJECUCION(27L), 
	SSPPOLICIA(28L), 
	SSPMEDICO(29L), 
	SSPAGENTEMP(30L), 
	SSPPROCESAL(31L), 
	SSPDIRPROC(32L), 
	SSPDIRCEDE(33L), 
	SERVICIOSPERICI(34L), 
	COORDINADORPER(35L),
	ADMINISTRADOR(36L),
	PERITODEF(37L),
	COORDPERFIS(38L),
	UNIDADCAPTURA(39L),
	JUEZEJECUCIONPJ(40L),
	SSPEPRS(41L),
	COORDINADORVIS(42L),
	UNIDADCAPTURAPG(43L),
	UAVDATNPSICOLOGICA(44L),
	UAVDJURIDICO(45L),
	UAVDTRABAJOSOCIAL(46L),
	AGENTEMPSISTRAD(47L),
	DSE(48L),
	ASE(49L),
	CER(50L),
	POP(51L),
	CPR(52L),
	MED(53L),
	CMT(54L),
	COORDINADOR_CONSIGNACION(55L),
	CONSIGNADOR(56L),
	COORDINADORAT(58L),
	
	DIRECTOR_GENERAL(59l),
	SUBPROCURADOR(60l),
	PROCURADOR(61l),
	DIRECTOR_UI(62l),
	DIRECTOR_APREHENSIONES(63L),
	COORDINADOR_DE_POLICIA_MINISTERIAL(64l);
	
	private Long valorId;

    private final static Map<Long, Roles> hash = new HashMap<Long, Roles>();

    static {
    	Roles[] acts = Roles.values();
        int pos = 0;
        while (pos < acts.length) {
            hash.put(acts[pos].getValorId(), acts[pos]);
            pos++;
        }
    }
    private Roles(Long valorIdPredefinido) {
        this.valorId = valorIdPredefinido;
    }

    public static Roles getByValor(Long valorIdPredefinido) {
    	Roles loRol = hash.get(valorIdPredefinido);
    	if(loRol == null){
    		loRol = Roles.ROL_NO_DEFINIDO;
    		
    	}
        return loRol;
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
