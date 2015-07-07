package mx.gob.segob.nsjp.dao.solicitud;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.SolicitudMandamiento;
import mx.gob.segob.nsjp.model.Valor;

public interface SolicitudMandamientoDAO extends GenericDao<SolicitudMandamiento, Long> {

	public List<SolicitudMandamiento> consultarSolicitudesMandamientoBy(EstatusSolicitud estado) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que crea una SolicitudMandamiento con un SolicitudId 
	 * y un MandamientoId previamente existentes.
	 * 
	 * @param solicitudMandamiento: Objeto que incluye la informaci&oacute;n
	 * 								del identificador de la solicitud y el 
	 * 								identificador del mandamiento que se encuentran
	 * 								previamente registrados en la base de datos.
	 * 
	 * @return idSolicitudMandamiento: Identificador de la solicitud mandamiento generada.
	 * 
	 * @throws NSJPNegocioException - En el caso de que no se ingresen los par&aacute;metros
	 * 								  necesarios.
	 */
	public Long crearSolicitudMandamientoConInfoExistente(SolicitudMandamiento solicitudMandamiento) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de las solicitudes mandamiento que se encuentran
	 * asociadas a un funcionario y que cumplen con ciertos criterios de b&uacute;squeda como los
	 * estatus de la solicitud, los tipos de mandamiento y los tipos de solicitud. 
	 * 
	 * @param destinatario - Funcionario destinatario al cual se envi&oacute; la solicitud.
	 * @param estatusSolicitud - Estatus bajo el cual se van a filtrar las solicitudes.
	 * @param tipoMandamiento - Tipos de mandamientos que se encuentran asociados con las 
	 * 							solicitudes.
	 * @param tipoSolicitud - Tipos de solicitudes con las cuales se va a filtrar la consulta.
	 * @return List<SolicitudMandamiento> - Lista de <code>SolicitudMandamiento</code> con aquellas
	 * 										Solicitudes de Mandamiento que cumplieron con los 
	 * 										criterios de b&uacute;squeda.
	 * @throws NSJPNegocioException - En el caso de que no se ingresen todos los par&aacute;metros
	 * 								  requeridos.
	 */
	public List<SolicitudMandamiento> consultarSolicitudesMandamientoPorDestinatario(Funcionario destinatario, 
			List<Valor> estatusSolicitud, List<Valor> tipoMandamiento, List<Valor> tipoSolicitud) throws NSJPNegocioException;
	
	/**
	 * Consulta las solicitudes de mandamiento judicial por filtro
	 * 
	 * @param SolicitudMandamiento, con los par&aacute;metros de b&uacute;squeda
	 * @return Lista de solicitudes que superaron el filtro
	 * @throws NSJPNegocioException
	 *             , en caso de alguna excepci&oacute;n
	 */
	public List<SolicitudMandamiento> consultarSolicitudesMandatoJudicialPorFiltro(
			SolicitudMandamiento solicitudMandamiento);

}
