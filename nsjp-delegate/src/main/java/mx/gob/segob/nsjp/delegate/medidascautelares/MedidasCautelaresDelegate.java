/**
 * Nombre del Programa : MedidasCautelaresDelegate.java
 * Autor                            : Hugo Serrano
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 6 Jul 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato Delegate para las Medidas Cautelares
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
package mx.gob.segob.nsjp.delegate.medidascautelares;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.medida.MedidaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Contrato Delegate para las Medidas Cautelares
 * 
 * @version 1.0
 * @author Tattva-IT
 * 
 */
public interface MedidasCautelaresDelegate {
    /**
     * Obtiene la lista de medidas cautelares p�r involucrado
     * 
     * @return List<MedidaCautelarDTO>
     * @throws NSJPNegocioException
     */

    List<MedidaCautelarDTO> obtenerMedidasCautelaresPorInvolucrado(
            Long involucradoId) throws NSJPNegocioException;

    /**
     * 
     * @param idMedidaCautelar
     * @param idInvolucrado
     * @return MedidaCautelarDTO
     * @throws NSJPNegocioException
     */
    MedidaCautelarDTO obtenerDetalleMedidaCautelar(Long idMedidaCautelar,
            Long idInvolucrado) throws NSJPNegocioException;

    /**
     * 
     * @param medidaCautelar
     * @return Long
     * @throws NSJPNegocioException
     */
    Long ingresarMedidaCautelar(MedidaCautelarDTO medidaCautelar)
            throws NSJPNegocioException;
    /**
     * Desactiva la medida cautelar.
     * 
     * @param medidaCautelar
     *            Obligatorio <b>documentoId</b>.
     * @throws NSJPNegocioException
     */
    void desactivarMedidaCautelar(MedidaCautelarDTO medidaCautelar)
            throws NSJPNegocioException;

    /**
	 * Consulta las medidas cautelares asociadas a cierto numero de expediente 
	 * @param numeroExpedienteId N�mero de expediente de la medida cautelar buscada
	 * @author Emigdio Hern�ndezd
	 * @return Lista de medidas cautelares encontradas
	 */
	List<MedidaCautelarDTO> obtenerMedidasCautelaresPorExpediente(Long numeroExpedienteId) throws NSJPNegocioException ;
	
	/**
	 * Consulta las medidas cautelares de acuerdo a su Estatus
	 * @param medidaCautelar DTO de la medida cautelar con el estatus deseado
	 * @author Marco Gallardo
	 * @return Lista de medidas cautelares encontradas
	 * @throws NSJPNegocioException 
	 */
	List<MedidaCautelarDTO> consultaMedidasCautelaresPorEstatus(MedidaCautelarDTO medidaCautelar) throws NSJPNegocioException ;
	
	/**
	 * Consulta las medidas cautelares de acuerdo a un rango de fechas o n�mero de expediente, acepta m�s condicionales
	 * @param medidaCautelar
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ExpedienteDTO> consultaMedidasCautelaresPorFiltro(MedidaCautelarDTO medidaCautelar) throws NSJPNegocioException;

	/**
	 * Asocia un archivo digital a una medida.
	 * @author cesarAgustin
	 * @param medidaDTO
	 * 			<li>documentoId<li>
	 * @param archivoDigitalDTO
	 * 			<li>archivoDigitalId<li>
	 * @throws NSJPNegocioException
	 */
	public void asociarArchivoDigitalAMedida(ArchivoDigitalDTO archivoDigitalDTO, MedidaDTO medidaDTO) throws NSJPNegocioException;
	
	/**
	 * Cambia el estatus de una Medida ya sea Cautelar o Alternativa  
	 * @param idMedida : Long el Id de la medida (Cautelar/Alternativa)
	 * @param idEstatus : Long el Id del estatus (Obtenido del Enum EstatusMedida) 
	 * @return void
	 * @throws NSJPNegocioException
	 */
	MedidaCautelarDTO cambiarEstatusMedida(Long idMedida, Long idEstatus) throws NSJPNegocioException;
	
	/**
	 * Consulta un listado de involucrados por numero expediente/causa y obtiene adicionalmente sus medidas
	 * cautelares si es que las tiene
	 * @param numeroExpediente N�mero de expeidnte/causa a buscar
	 * @return Lista de involucrados con medidas cautelares
	 * @author Emigdio Hern�ndez
	 */
	List<InvolucradoDTO> consultarInvolucradosConMedidasCautelaresPorNumeroExpedienteOCausa(String numeroExpediente) throws NSJPNegocioException;
	/**
	 * 
	 * @param numeroExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<MedidaCautelarDTO>  consultarMedidasCautelaresPorNumeroExpedienteOCausa(String numeroExpediente,UsuarioDTO usuario) throws NSJPNegocioException;
	
	/**
	 * Consulta las medidas cautelares deacuerdo ID
	 * @param idMedidaCautelar Id de la medida cautelar con el estatus deseado
	 * @author Marco Gallardo
	 * @return MedidaCautelarDTO
	 * @throws NSJPNegocioException 
	 */
	MedidaCautelarDTO consultarMedidasCautelaresPorId(Long idMedidaCautelar) throws NSJPNegocioException ;
	
	/**
	 * Asocia un documento con archivo digital a una medida.
	 * @author cesarAgustin
	 * @param documentoDTO
	 * @param medidaCautelarDTO
	 * @throws NSJPNegocioException
	 */
	public void asociarDocumentoConMedidaCautelar(DocumentoDTO documentoDTO, MedidaCautelarDTO medidaCautelarDTO ) throws NSJPNegocioException;
	
	/**
     * Realiza el env�o de una medida cautelar al �rea de SSP
     * Se debe de contar ya con la medida en BD y su documento digital emitido
     * 
     * @param medidaId Medida a enviar
	 * @param institucionEnviar Institucion a la que se va enviar.
	 * @throws NSJPNegocioException
	 */
    void enviarMedidaCautelarInstitucion(Long medidaId, Instituciones institucionEnviar) throws NSJPNegocioException;
    
    /**
	 * Cliente para enviar una Medida Cautelar a una instituci�n en particular,
	 * el servicio lo que hace es actualizar el estatus de dicha medida.
	 * 
	 * @param medidaCautelar
	 * @param institucionDestino
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean actualizarEstatusMedidaCautelarInstitucion(
			Long medidaId, Instituciones institucionDestino)
			throws NSJPNegocioException;
	
    /**
	 * Permite consultar las medidas cautelares que cuentan con una fecha de incumplimiento
	 * del dia anterior a la fecha actual
	 * @return List<MedidaCautelarDTO>
	 * @throws NSJPNegocioException
	 */
	public List<MedidaCautelarDTO> obtenerMedidasCautelaresPorFechaIncumplientoDelDiaAnterior() throws NSJPNegocioException; 
    
	/**
	 * Consulta las Medidas Cautelares por 
	 * -Fecha menor o igual a la indicada
	 * -lista con id de los estatus de las medidas cautelares
	 * Estos parametros son opcionales, es decir, pueden ser nulos.
	 * 
	 * @param fecha
	 * @param estatusId
	 * @return
	 */
	List<MedidaCautelarDTO> obtenerMedidasCautelaresPorFiltro(Date fecha, List<Long> estatusId);
	
	/**
	 * Permite consultar audiencias por filtro:
	 * - Estatus
	 * - Fecha de creaci�n del expediente
	 * - Por numero de causa
	 * @param medidaCautelar
	 * @return
	 */
	public List<MedidaCautelarDTO> obtenerMedidasCautelaresPorFiltro(MedidaCautelarDTO medidaCautelar) throws NSJPNegocioException;
	
	/**
	 * Permite adjuntar documentos y asociarlos a una medida
	 * @param documentoDTO
	 * @param medidaDTO
	 * @param tipoDocumento
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long adjuntarDocumentoAMedida(
			DocumentoDTO documentoDTO, MedidaDTO medidaDTO,
			TipoDocumento tipoDocumento) throws NSJPNegocioException;
	
}
