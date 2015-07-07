/**
 *
 * Nombre del Programa : DomicilioDAOImpl.java                                    
 * Autor                            : Cesar Agustin                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 30/03/2011 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Interface para el DAO de la entidad Caso                      
 * Programa Dependiente  :N/A                                                      
 * Programa Subsecuente :N/A                                                      
 * Cond. de ejecucion        :N/A                                                      
 * Dias de ejecucion          :N/A                             Horario: N/A       
 *                              MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                       :N/A                                                           
 * Compania               :N/A                                                           
 * Proyecto                 :N/A                                   Fecha: N/A       
 * Modificacion           :N/A                                                           
 *------------------------------------------------------------------------------           
 */

package mx.gob.segob.nsjp.dao.caso;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.Elemento;

/**
 * 
 * @author Cesar Agustin
 * @version 1.0
 * 
 */

public interface CasoDAO extends GenericDao<Caso, Long> {

	/**
	 * Recupera el �ltimo n�mero de caso registrado en la BD.
	 * En caso de que monogramaInstitucion sea distinto a nulo, se hace
	 * la consulta del �ltimo n�mero de caso generado en la instituci�n
	 * indicada por el monograma.
	 * 
	 * @param monogramaInstitucion
	 * @return �ltimo n�mero de caso registrado en la BD.
	 */
    public String recuperarUltimoNumero(String monogramaInstitucion);

    /**
     * Recupera los casos que corresponden al parametro recibido.
     * 
     * @param numeroCaso
     * @return
     */
    public List<Caso> consultarCasosPorNumero(String numeroCaso);
    /**
     * 
     * @param idUsuario
     * @return
     */
    List<Caso> consultarCasos(Long idUsuario);

    /**
     * Recupera los casos que tengan una fecha de creacion dentro dle rango enviado como parametro
     * @param fechaCreacionInicio
     * @param fechaCreacionFin
     * @return
     */
	public List<Caso> consultarCasosPorFecha(Date fechaCreacionInicio,
			Date fechaCreacionFin);

	/**
	 * Recupera los casos que correspondan al delito enviado como parametro
	 * @param delitoId
	 * @return
	 */
	public List<Caso> consultarCasosPorDelito(String delito);
	
	/**
     * Consulta los n�meros e id's de casos que tengan la menos un evento que se deba mostrar
     * en el �rbol de consulta de hist�ricos
     * @param usr Usuario que consulta
     * @return Lista de casos encontrados
     */
    List<Caso> consultarCasosConEventosHistoricos(Long usuarioId) throws NSJPNegocioException;

    /**
     * Consulta el caso que ampara al numero de expediente dado
     * @param expedienteId
     * @return
     */
	public Caso consultarCasoPorExpediente(Long expedienteId);

	/**
	 * Obtiene la informacion del caso de acuero al paramtro numero general caso
	 * @param numeroCasoAsociado
	 * @return
	 */
	public Caso obtenerCasoByNumeroGeneral(String numeroCasoAsociado);
	
	  /**
     * Permite buscar las reincidencias de un elemnto:
     * Si es un Automovil, permitira filtrar por el numero de serie y por el numero de placas
     * Si es un Arma, permitira filtrar por el numero de serie
     * Se es una persona, permitira filtrar por el nombre y apellidos
     * @param elemento
     * @return List<Caso> 
     * @throws NSJPNegocioException
     */
	public List<Caso> consultarReincidenciasDeElementos(Elemento elemento);

	/**
     * Consulta un caso por n�mero general de caso
     * @param numeroGeneralCaso 
     * @return Caso 
     * @throws NSJPNegocioException
     */
	public Caso consultarCasoPorNumeroCaso(String numeroGeneralCaso);
	
	/**
     * Consulta el casoId por n�mero general de caso
     * @param numeroGeneralCaso 
     * @throws NSJPNegocioException
     */
	public Long consultarIdXNumeroCaso(String numeroGeneralCaso);
}
