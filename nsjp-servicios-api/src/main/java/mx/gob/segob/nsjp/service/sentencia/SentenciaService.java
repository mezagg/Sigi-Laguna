/**
 * 
 */
package mx.gob.segob.nsjp.service.sentencia;

import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionCentroDetencionDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaWSDTO;

/**
 * @author AntonioBV
 *
 */
public interface SentenciaService {

	
	/**
	 * M&eacute;todo que consulta un NUS en base al CURP del Involucrado 
	 * @param nombreDemografico DTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<SentenciaDTO> consultarNUS(NombreDemograficoDTO nombreDemograficoDTO)throws NSJPNegocioException;
	
	
	SentenciaDTO consultarSentenciaCompleta(SentenciaDTO sentenciaDTO)throws NSJPNegocioException;
	
	void crearSentencia(SentenciaWSDTO sentenciaWSDTO)throws NSJPNegocioException;
	
	boolean enviarSentencia(SentenciaDTO sentenciaDTO, Instituciones institucion) throws NSJPNegocioException;

	/**
	 * M&eacute;todo que crea o actualiza una sentencia. 
	 * @param sentenciaDTO sentencia a crear o actualizar.
	 * @return Sentencia con los datos persistidos.
	 * @throws NSJPNegocioException
	 */
	SentenciaDTO realizarAltasCambiosASentencia(SentenciaDTO sentenciaDTO) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta del centro de detencion actual
	 * para una sentencia.
	 * 
	 * @param sentencia
	 *            - La sentencia de la cual se va a consultar la
	 *            asignaci&oacute;n de centro de detenci&oacute;n actual para la
	 *            sentencia.
	 * @return AsignacionCentroDetencion - La asignaci&oacute;n de centro de
	 *         detencion actual para la sentencia.
	 */
	public AsignacionCentroDetencionDTO consultarAsignacionCentroDetencionActual(
			SentenciaDTO sentenciaDTO) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de los involucrados de acuerdo al tipo 
	 * de situaci&oacute;n jur&iacute;dica en la que se encuentran, filtrando solamente 
	 * aquellos que no cuentan con una sentencia dictada. 
	 * @param situacionJuridica - situaci&oacute;n jur&iacute;dica en base a la cual se
	 * 							  se van a filtrar los involucrados.
	 * @return List<Involucrado> - Lista con los involucrados que se encuentran en la
	 * 							   situaci&oacute;n jur&iacute;dica pasada como 
	 * 							   par&aacute;metro y que no tienen una sentencia 
	 * 							   asociada.
	 * @throws NSJPNegocioException - En el caso de que los argumentos pasados no sean
	 * 								  suficientes para poder llevar a cabo la consulta.
	 */
	public List<InvolucradoDTO> consultarInvolucradosXSituacionSinSentencia(ValorDTO situacionJuridica) 
			throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo el registro de una sentencia ejecutoriada dentro de
	 * la base de datos y env&iacute;a la informaci&oacute;n de la sentencia a las 
	 * dem&aacute;s intituciones.
	 * @param sentenciaDTO - DTO con la informaci&oacute;n de la sentencia a persistir.
	 * @param involucradoDTO - DTO con la informaci&oacute;n del involucrado que se va
	 * 						   a asociar con la sentencia.
	 * @throws NSJPNegocioException - En el caso de que no se hayan obtenido los par&aacute;metros
	 * 								  suficientes para poder llevar a cabo el registro.
	 */
	public void registrarSentenciaEjecutoriada(SentenciaDTO sentenciaDTO, InvolucradoDTO involucradoDTO) 
			throws NSJPNegocioException;
	
	/**
	 * Cliente que permite actualizar la informaci&oacute;n asociada con una sentencia en
	 * otra instituci&oacute;n. 
	 * @param sentenciaDTO - Objeto con la informaci&oacute;n que se actualizar&aacute; en 
	 * 						 la instituci&oacute;n destino.
	 * @param institucion - Instituci&oacute;n en la cual se actualizar&aacute; la 
	 * 						informaci&oacute;n de la sentencia.
	 * @throws NSJPNegocioException - En el caso de que no se hayan enviado los 
	 * 								  par&aacute;metros requeridos.
	 */
	public void actualizarSentenciaExterna(
			SentenciaDTO sentenciaDTO, Instituciones institucion)throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de sentencias en base al identificador del 
	 * n&uacute;mero de expediente pasado como par&aacute;metro dentro de la lista.
	 * @param idsNumExp - List<Long> con los identificadores del n&uacute;mero de expediente 
	 * 					  sobre los cuales se van a filtrar las sentencias.
	 * 
	 * @return Map<Long,SentenciaDTO> - Map<Long,SentenciaDTO> con las sentencias que cumplieron 
	 * 									con el criterio de la consulta. Agrupadas dentro de un 
	 * 									mapa cuya llave corresponde con el identificador del 
	 * 									n&uacute;mero de expediente asociado a la sentencia. 
	 * 
	 * @throws NSJPNegocioException - En el caso de que la lista de identificadores sea nula 
	 * 								  o se encuentre vac&iacute;a.
	 */
	public Map<Long,SentenciaDTO> consultarSentenciasPorIdsNumExp(List<Long> idsNumExp) throws NSJPNegocioException;
}
