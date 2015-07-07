package mx.gob.segob.nsjp.dao.evidencia;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Almacen;
import mx.gob.segob.nsjp.model.CadenaDeCustodia;
import mx.gob.segob.nsjp.model.Eslabon;
import mx.gob.segob.nsjp.model.Evidencia;

public interface EvidenciaDAO extends GenericDao<Evidencia, Long> {

    /**
     * Consulta las evidencias asociadas a una cadena de custodia.
     * @param cadenaDeCustodia El n�mero de folio de la cadena de custodia.
     * @return Devuelve el listado de evidencias asociadas a la cadena de
     * custodia.
     * En caso de no existir una cadena de custodia con el numero de
     * folio o en caso de no existir evidencias asociadas a la cade de custodia,
     * regresa la lista vacia.
     *
     * De cada evidencia se consultan los siguientes datos:
     * - Identificador de la evidencia
     * - Informaci�n de la evidencia
     * - Hora del levantamiento de la evidencia
     * - Origen de la evidencia
     * - �ltimo eslab�n asociado (Nota1)
     * - N�mero de eslab�n
     * - Tipo de eslab�n
     * - Almac�n (Nota2)
     * - Tipo de baja de indicio o evidencia
     * @throws NSJPNegocioException
     */
    List<Evidencia> consultarEvidenciasXCadenaCustodia(CadenaDeCustodia cadenaDeCustodia);
    /**
     * 
     * @param surId
     * @param idCadenaCus
     * @return
     */
    List<Eslabon> consultarEvidenciasResguardadasXUsario(Long surId, Long idCadenaCus);
    /**
     * Operaci�n que realiza la funcionalidad de consultar las evidencias asociadas al folio de una cadena de custodia en funci�n de la disponibilidad solicitada(estatus).
     * @param cadenaDeCustodiaId
     * @param estatus
     * @return
     */
	List<Evidencia> consultarEvidenciasXCadenaYEstatus(Long cadenaDeCustodiaId,
			Long estatus);

        List<Evidencia> consultarEvidenciaPorAlmacen(Almacen almacen);
        
    List<Evidencia> consultarEvidenciasXCadena(Long cadenaId);
    
    Evidencia consultarEvidenciaXObjetoId(Long IdObjeto);
	
    /**
	 * Operaci�n que permite consultar las evidencias por almacen, opcionalmente por estatus y opcionalmente por caso
	 * Ademas permite saber si una evidencia tiene solicitudes asociadas a la evidencia
	 * @param almacenDTO: idAlmacen
	 * @param estatusEv: null=sin importar estatus; -1=Solicitudes de AMP; valor=por estatus  
	 * @param casoDTO: idCaso
	 * @return
	 */
    List<Evidencia> consultarevidenciaXAlmacenXEstatus(
			Long identificadorAlmacen, Long estatusEv, Long idCaso, Boolean tieneSolicitudPorAtender );
    
    
    /**
     * Permite consultar evidencias por estatus
     * @param estatus
     * @return
     */
	public List<Evidencia> consultarEvidenciasXEstatus(Long estatus);
	
	/**
	 * Permite consultar las evidencias asociadas a un Funcionario de determinada area
	 * y determinada Agencia por estatus(opcional) de Evidencia 
	 * @param estatusEv
	 * @param idUsuario
	 * @param areaId
	 * @param estatusExpediente
	 * @param discriminanteId
	 * @return
	 */
	public List<Evidencia> consultarevidenciaXEstatusXFuncionario(Long estatusEv,Long idUsuario,
			Long areaId, Long discriminanteId);
    

}
