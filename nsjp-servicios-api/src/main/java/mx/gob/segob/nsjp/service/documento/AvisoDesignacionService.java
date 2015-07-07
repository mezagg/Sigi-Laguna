package mx.gob.segob.nsjp.service.documento;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;

public interface AvisoDesignacionService {

	/**
	 * Consulta los avisos de Designacion por :
	 * @param estadoNotificacion
	 * @param funcionarioDTO - Clave del Funcionario
	 * @param tipoSolicitud
	 * @param distritoId
	 * 
	 * @return
	 * @throws NSJPNegocioException
	 */
	//MOD CoordinadorDef, Defensor
	List<AvisoDesignacionDTO> consultarAvisosDesignacion(
			EstatusNotificacion estadoNotificacion, FuncionarioDTO funcionarioDTO,
			TiposSolicitudes tipoSolicitud, Long distritoId)
			throws NSJPNegocioException;
	
	/**
	 * Registra un nuevo aviso de designación.
	 * @param designacion
	 * @return
	 * @throws NSJPNegocioException
	 */
	@Deprecated
	public AvisoDesignacionDTO registrarAvisoDesignacion (AvisoDesignacionDTO designacion) throws NSJPNegocioException;

	/**
	 * Se crea o actualiza la asignaci&oacute;n de la solicitud de asesr&iacute;a legal. 
	 * 
	 * @param avisoDesignacionDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	AvisoDesignacionDTO designarAbogadoDefensorAsesoria(
			AvisoDesignacionDTO avisoDesignacionDTO)
			throws NSJPNegocioException;

	/**
	 * Asocia un abogado defensor al aviso de Designación para que lo atienda.
	 * @param designacion
	 * @throws NSJPNegocioException
	 */
	void designarAbogadoDefensor(AvisoDesignacionDTO designacion,
			Boolean cambiarEstatusExpediente, Boolean esIterativo)
			throws NSJPNegocioException;
	
	/**
	 * Reasigna un abogado defensor a un expediente, teniendo en cuenta las siguientes reglas:
	 * 1) Si el expediente cuenta con una solicitud de carpeta de investigacion, muestra el mensaje
	 * 	indicando que no se puede realizar la reasignacion, hasta que se cierre la solicitud
	 * 
	 * 2) En caso de que no cuente con solicitud de carpeta de investigacion en estatus abierta o en proceso
	 * 	se cambia la clave funcionario, por la que se trae como parametro, ademas de actualizar los
	 *  avisos de designacion.
	 *  
	 * @param avisoDesignacion
	 * @throws NSJPNegocioException
	 */
	void reasignarAbogadoDefensorExpediente(AvisoDesignacionDTO designacion)throws NSJPNegocioException;

	/**
	 * Cambia el funionario asociado al numero de expediente por el que se encuentra
	 * identificado por claveFuncionario
	 * @param idNumeroExpediente
	 * @param claveFuncionario
	 */
	public void cerrarAvisoDesignacion(Long idAvisoDesignacicon);

	/**
	 * Genera un nuevo aviso de designación para un expediente al cual se le está reasignando 
	 * un abogado defensor
	 * @param input
	 * @return
	 * @throws NSJPNegocioException
	 */
	public AvisoDesignacionDTO registrarAvisoDesignacionPorReasignacionDefensor(ExpedienteDTO input)
			throws NSJPNegocioException;

	
	/**
	 * Obtiene la información concerciente a un aviso de designación por su identificador.
	 * 
	 * @param idAviso
	 * @return
	 * @throws NSJPNegocioException
	 */
	public AvisoDesignacionDTO consultarAvisoDesignacion(Long idAviso)throws NSJPNegocioException;
	
	/**
	 * Servicio que genera la copia de Imputados a Defendidos, considerando 
	 * los datos del involucrado y del domicilio. 
	 * 
	 * @param idSolicitudDefensor
	 * @param expedienteId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Boolean generarCopiaImputadosADefendidoSolicitudDefensor(
			Long solicitudDefensorId, Long expedienteId) throws NSJPNegocioException;
	
}
