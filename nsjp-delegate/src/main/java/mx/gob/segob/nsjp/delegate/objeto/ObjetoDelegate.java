/**
* Nombre del Programa : ObjetoDelegate.java                                    
* Autor                            : Tattva-IT                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 2 May 2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Delegate para las acciones de objetos                      
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
package mx.gob.segob.nsjp.delegate.objeto;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.objeto.Objetos;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.almacen.AlmacenDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.objeto.AeronaveDTO;
import mx.gob.segob.nsjp.dto.objeto.AnimalDTO;
import mx.gob.segob.nsjp.dto.objeto.ArmaDTO;
import mx.gob.segob.nsjp.dto.objeto.DocumentoOficialDTO;
import mx.gob.segob.nsjp.dto.objeto.EmbarcacionDTO;
import mx.gob.segob.nsjp.dto.objeto.EquipoComputoDTO;
import mx.gob.segob.nsjp.dto.objeto.ExplosivoDTO;
import mx.gob.segob.nsjp.dto.objeto.JoyaDTO;
import mx.gob.segob.nsjp.dto.objeto.NumerarioDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoEstudioDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoPericialDTO;
import mx.gob.segob.nsjp.dto.objeto.ObraArteDTO;
import mx.gob.segob.nsjp.dto.objeto.SustanciaDTO;
import mx.gob.segob.nsjp.dto.objeto.TelefoniaDTO;
import mx.gob.segob.nsjp.dto.objeto.VegetalDTO;
import mx.gob.segob.nsjp.dto.objeto.VehiculoDTO;


/**
 * Delegate para obtener los servicios relacionados a los objetos.
 * @version 1.0
 * @author Tattva-IT
 *
 */
public interface ObjetoDelegate {
	
	/**
	 * Guarda en la base de datos los datos relacionados de objetos vehiculo.
	 * @param involucradoDTO
	 * @return Long
	 * @throws NSJPNegocioException
	 */
	
	Long ingresarVehiculo (VehiculoDTO vehiculoDto) throws NSJPNegocioException;
	
	/**
	 * Guarda en la base de datos los datos relacionados de objetos Equipo de Computo
	 * @param equipoComputoDto
	 * @return Long
	 * @throws NSJPNegocioException
	 */
	Long ingresarEquipoDeComputo(EquipoComputoDTO equipoComputoDto) throws NSJPNegocioException;
	
	/**
	 * Guarda en la base de datos los datos relacionados de objetos de Telefonia
	 * @param telefoniaDTO
	 * @return Long
	 * @throws NSJPNegocioException
	 */
	Long ingresarTelefono(TelefoniaDTO telefoniaDTO) throws NSJPNegocioException;
	
	/**
	 * Para indicar a este metodo que se requiere guardar un nuevo registro
         * en la base de datos debe pasar lo siguiente:
         * {@code armaDto.elementoId == null} o {@code armaDto.elementoId == 0}<br/>
         * Los campos requeridos para guardar un arma son:
         * <ol>
         * <li> armaDto.expediente.expedienteId
         * <li> armaDto.calidad.calidades
         * <li> armaDto.valorByCondicionFisicaVal
         * </ol>
         * Para actualizar un arma se requiere que {@code
         * armaDto.elementoId != null} y que {@code armaDto.elementoId > 0}
	 * @param armaDto El arma a guardar o actualizar.
	 * @return Long El id del arma guardada o actualizada.
	 * @throws NSJPNegocioException Siempre se lanza cuando
         * {@code armaDto == null}.<br/>
         * En el caso de guardar un arma esta
         * excepcion se lanza cuando se cumple alguna de las siguiente
         * condiciones:
         * <ol>
         * <li> armaDto.getExpedienteDTO() == null
         * <li> armaDto.getExpedienteDTO().getExpedienteId() == null
         * </ol>
         * En caso de actualizar un arma se lanza esta excepcion cuando
         * {@code armaDto.elementoId < 0}
	 */
	Long ingresarArma(ArmaDTO armaDto) throws NSJPNegocioException;
	
	/**
	 * 
	 * @param explisvoDto
	 * @return Long
	 * @throws NSJPNegocioException
	 */
	
	Long ingresarExplosivo(ExplosivoDTO explisvoDto)throws NSJPNegocioException;
	
	/**
	 * 
	 * @param sustanciaDto
	 * @return Long
	 * @throws NSJPNegocioException
	 */
	Long ingresarSustancia(SustanciaDTO sustanciaDto)throws NSJPNegocioException;
	
	/**
	 * 
	 * @param animalDto
	 * @return Long
	 * @throws NSJPNegocioException
	 */
	Long ingresarAnimal(AnimalDTO animalDto)throws NSJPNegocioException;
	
	/**
	 * 
	 * @param aeronaveDto
	 * @return Long
	 * @throws NSJPNegocioException
	 */
	Long ingresarAeronave(AeronaveDTO aeronaveDto)throws NSJPNegocioException;
	
	/**
	 * 
	 * @param embarcacionDto
	 * @return Long
	 * @throws NSJPNegocioException
	 */
	Long ingresarEmbarcacion(EmbarcacionDTO embarcacionDto)throws NSJPNegocioException;
	
	/**
	 * 
	 * @param numerarioDTO
	 * @return Long
	 * @throws NSJPNegocioException
	 */
	Long ingresarNumerario(NumerarioDTO numerarioDTO)throws NSJPNegocioException;
	
	/**
	 * 
	 * @param vegetalDTO
	 * @return Long
	 * @throws NSJPNegocioException
	 */
	Long ingresarVegetal(VegetalDTO vegetalDTO)throws NSJPNegocioException;
	
	/**
	 * 
	 * @param documentoOdificalDto
	 * @return Long
	 * @throws NSJPNegocioException
	 */
	Long ingresarDocumentoOficial(DocumentoOficialDTO documentoOdificalDto)throws NSJPNegocioException;
	
	
	/**
	 * 
	 * @param joyaDto
	 * @return Long
	 * @throws NSJPNegocioException
	 */
	Long ingresarJoya(JoyaDTO joyaDto)throws NSJPNegocioException;
	
	
	/**
	 * 
	 * @param obraArteDto
	 * @return Long
	 * @throws NSJPNegocioException
	 */
	Long ingresarObraArte(ObraArteDTO obraArteDto)throws NSJPNegocioException;	

	/**
	 * Servicio utlizado para registrar un Objeto de tipo Otro.
	 * 
	 * @param objetoDto
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long ingresarOtroObjeto(ObjetoDTO objetoDto) throws NSJPNegocioException;
	
	/**
	 * Servicio utlizado para registrar un Objeto de tipo Pericial.
	 * 
	 * @param objetoPericialDto
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long ingresarObjetoPericial(ObjetoPericialDTO objetoPericialDto) throws NSJPNegocioException;
	
	
	/**
	 * Consulta los objetos que pertenencen al <code>detenido</code>
	 * @param detenido Involucrado a quien pertenecen los objetos consultados
	 * @return Lista de objetos que pertenecen al detenido
	 * @throws NSJPNegocioException
	 */
	public List<ObjetoDTO> consultarInventarioPertenenciasDetenido(InvolucradoDTO detenido) throws NSJPNegocioException;
	
	/**
	 * Regrista un objeto en la base y lo asocioa al detenido a traves de la relacion pertenece
	 * @param objeto que pertenece al <code>detendio</code> y ser� agregado
	 * @param detenido due�o del <code>objeto</code>
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long registrarPertenenciaDetenido(ObjetoDTO objeto, InvolucradoDTO detenido) throws NSJPNegocioException;
	
	/**
	 * Elimina la relaci�n de un objeto con un involucrado y posteriormente elimina el objeto.
	 * @param objeto se eliminaroa del sistea
	 * @param detenido para eliminar la relacic�on con el objeto
	 * @throws NSJPNegocioException
	 */
	public void eliminarPertenenciaDetenido(ObjetoDTO objeto, InvolucradoDTO detenido) throws NSJPNegocioException;

	
    /**
     * Recupera un objeto de la BD.
     * 
     * @param input
     *            requerido <b>elementoId</b>.
     * @return
     * @throws NSJPNegocioException
     */
    ObjetoDTO obtenerObjeto(ObjetoDTO input) throws NSJPNegocioException;
    
    /**
     * Operaci�n que realiza la funcionalidad de consultar los objetos no asociados a una evidencia
     * @param expedienteDTO
     * @return
     * @throws NSJPNegocioException
     */
    List<ObjetoDTO> consultarObjetosNoEvidencia(ExpedienteDTO expedienteDTO)throws NSJPNegocioException;
    
    /**
     * Operaci�n que permite asignar un objeto-evidencia a un almacen
     * @param objetoDTO: idObjeto
     * @param almacenDTO: idAlmacen
     * @return
     * @throws NSJPNegocioException
     */
    ObjetoDTO asociarObjetoAlmacen (ObjetoDTO objetoDTO, AlmacenDTO almacenDTO)throws NSJPNegocioException;
    
    /**
     * Metodo que permite consultar los objetos tipo de objeto asociados a un expediente
     * @param tipoObjeto: Representa el tipo de objeto a consultar
     * @param expediente.expedienteId 
     * @return List<ObjetoDTO>
     */
    List<ObjetoDTO> consultarObjetosPorTipoConFolioDeCustodia(Objetos tipoObjeto, ExpedienteDTO expediente) throws NSJPNegocioException;
    
	/**
	 * M&eacute;todo que lleva a cabo la consulta de los distintos estudios periciales
	 * que se encuentran asociados con un tipo de objeto.
	 * @param TipoObj - El tipo de objeto a partir del cual se consultar&aacute;n los 
	 * 					estudios periciales asociados.
	 * @return List<ObjetoEstudio> - Lista con la relaci&oacute;n de objetos y estudios 
	 * 								 asociados al tipo de objeto consultado.
	 */
	public List<ObjetoEstudioDTO> consultarEstudiosPorTipoObjeto (ValorDTO tipoObj) throws NSJPNegocioException;
	
	/**
	 * Permite saber si un objeto tiene asociada una cadena de custodia 
	 * @param idObjeto
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Boolean existeCadenaDeCustodiaPorIdObjeto(Long idObjeto)throws NSJPNegocioException;
	
	/**
     * Permite saber si un objeton esta asociado a una evidencia 
     * y a su ves la evdencia tiene eslabones
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Boolean existenEslabonesPorIdObjeto(Long idObjeto)throws NSJPNegocioException;

    /**
     * M&eacute;todo que obtiene los objetos filtrados por expediente y tipo.
     * @param expedienteDTO
     * @param tipoObjeto
     * @return Lista de Objetos
     * @throws NSJPNegocioException
     */
	List<ObjetoDTO> consultarObjetos(
			ExpedienteDTO expedienteDTO, ValorDTO tipoObjeto)
			throws NSJPNegocioException;

        /**
     * M&eacute;todo que obtiene los bienes por enajenar en determinada fecha
     * @param fecha
     * @param diasParaEnajenar
     * @return Lista de Bienes
     * @throws NSJPNegocioException
     */
	List<ObjetoDTO> consultarBienesPorEnajenar(
                           Date fecha,Integer diasParaEnajenar)
			throws NSJPNegocioException;

}

