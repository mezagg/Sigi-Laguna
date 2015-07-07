/**
* Nombre del Programa : SolicitarCopiaCarpetaDeInvestigacionServiceImpl.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 18 Jul 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacion del servicio para registrar la solicitud de la copia de carpeta de investigacion
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
package mx.gob.segob.nsjp.service.solicitud.impl;

import java.util.Calendar;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoWSDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaBasicoWSDTO;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.funcionarioexterno.impl.transform.FuncionarioExternoTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.solicitudaudiencia.FuncionarioExternoWSDTOTransformer;
import mx.gob.segob.nsjp.service.solicitud.SolicitarCopiaCarpetaDeInvestigacionService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servicio para registrar la solicitud de la copia de carpeta de investigacion.
 * @version 1.0
 * @author cesarAgustin
 *
 */
@Service("solicitarCopiaCarpetaDeInvestigacionService")
@Transactional
public class SolicitarCopiaCarpetaDeInvestigacionServiceImpl implements
		SolicitarCopiaCarpetaDeInvestigacionService {

	/**
	 * 
	 */
	public static final Logger logger = Logger.getLogger(SolicitarCopiaCarpetaDeInvestigacionServiceImpl.class);
	
	@Autowired
	private SolicitudDAO solicitudDAO;	
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	@Autowired
	private FuncionarioDAO funcionarioDAO;

	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.SolicitarCopiaCarpetaDeInvestigacionService#solicitarCopiaCarpetaDeInvestigacion(mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaBasicoWSDTO)
	 */
	@Override
	public SolicitudAudienciaBasicoWSDTO solicitarCopiaCarpetaDeInvestigacion(
			SolicitudAudienciaBasicoWSDTO solicitudWsDto,Long catDiscriminante)
			throws NSJPNegocioException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("/**** SERVICIO PARA RECIBIR LA SOLICITUD DE LA COPIA DE LA CARPETA DE INVESTIGACION ****/");
			logger.debug("Recibiendo solicitud con el folio :: " + solicitudWsDto.getFolioSolicitud());
		}
		Valor estadoSolicitud = new Valor(EstatusSolicitud.ABIERTA.getValorId());
		Valor tipoSolicitud = new Valor(TiposSolicitudes.CARPETA_DE_INVESTIGACION.getValorId());
		
		Long idSolicitud = null;
		SolicitudAudienciaBasicoWSDTO retorno = null;
		
		if (solicitudWsDto!=null) {
			retorno = new SolicitudAudienciaBasicoWSDTO();
			Solicitud solicitud = SolicitudTransformer.solicitudWSTransformerBasico(solicitudWsDto);
			//La busqueda del numero de expediente se efectua por la jerarquia de UI 
			
			NumeroExpediente numeroExpediente = null; 
			
			//Consulta los Numeros de Expedientes asociados al Caso, 
			//Puede haber mas de un expediente, pero se limita la busqueda 
			//al del area de UI
			List<NumeroExpediente> listNumeroExpedientes = numeroExpedienteDAO
					.consultarNumeroExpedientePorNumeroCaso(solicitudWsDto
							.getNumeroCasoAsociado());
			
			for (NumeroExpediente numeroExpedienteTemp : listNumeroExpedientes) {
				if (numeroExpedienteTemp.getJerarquiaOrganizacional()
						.getJerarquiaOrganizacionalId()
						.equals(Areas.UNIDAD_INVESTIGACION.parseLong())) {
					numeroExpediente = numeroExpedienteTemp;
				}
			}
			
			solicitud.setEstatus(estadoSolicitud);
			solicitud.setTipoSolicitud(tipoSolicitud);
			solicitud.setFolioSolicitud(solicitudWsDto.getFolioSolicitud());
			solicitud.setNumeroExpediente(numeroExpediente);
			// CAMPOS OBLIGATORIOS
			solicitud.setTipoDocumento(new Valor(TipoDocumento.SOLICITUD.getValorId()));
			solicitud.setForma(new Forma(Formas.SOLICITUD.getValorId()));
			solicitud.setEstatus(new Valor(EstatusSolicitud.ABIERTA.getValorId()));
			solicitud.setFechaCreacion(Calendar.getInstance().getTime());
			solicitud.setNombreDocumento("SOLICITUD_DE_CARPETA_DE_INVESTIGACION");
			solicitud.setAreaDestino(solicitudWsDto.getAreaDestino());
			solicitud.setCatDiscriminanteOrigen(catDiscriminante);
			
			//Funcionario destinatario
			if (numeroExpediente != null
					&& numeroExpediente.getFuncionario() != null ) {
				logger.debug("Funcionario Solicitante:"+ solicitudWsDto.getFuncionarioExternoSolicitante());
				
				solicitud.setIdFuncionarioSolicitante(numeroExpediente.getFuncionario().getClaveFuncionario());
				solicitud.setDestinatario(new Funcionario(numeroExpediente.getFuncionario().getClaveFuncionario()));
				logger.debug("El id del Funcionario Destinatario es: " + numeroExpediente.getFuncionario().getClaveFuncionario());
			}
			solicitud.setConfInstitucion(new ConfInstitucion(Instituciones.DEF.getValorId()));
			//TERMINA CAMPOS OBLIGATORIOS
			
			
			idSolicitud = solicitudDAO.create(solicitud);
			retorno.setSolicitudId(idSolicitud);
			
			//Datos del Funcionario Destinatario para el regreso
			if(solicitud
					.getDestinatario()!=null && solicitud.getDestinatario().getClaveFuncionario()!=null
					&& solicitud
					.getDestinatario().getClaveFuncionario() > 0L){
				
				Funcionario funcionario = funcionarioDAO.read(solicitud
						.getDestinatario().getClaveFuncionario());
				
				
				FuncionarioDTO funcionarioDestinatarioDTO = FuncionarioTransformer
						.transformarFuncionarioSuperBasico(funcionario);
				
				ConfInstitucion confInstitucion = funcionarioDAO
						.consultarInsitucionActual();
				ConfInstitucionDTO confInstitucionDTO = ConfInstitucionTransformer
						.transformarInstitucion(confInstitucion);
				funcionarioDestinatarioDTO.setInstitucion(confInstitucionDTO);
				
				FuncionarioExternoDTO funcionarioExternoDTO = FuncionarioExternoTransformer
						.transformarFuncionarioLocal(funcionarioDestinatarioDTO);
				FuncionarioExternoWSDTO funcionarioExternoWSDTO = FuncionarioExternoWSDTOTransformer
						.transformar(funcionarioExternoDTO);
				retorno.setFuncionarioExternoDestinatario(funcionarioExternoWSDTO);
				
				retorno.setFolioSolicitud(solicitud.getFolioSolicitud());
			}
		}
		
		
		return retorno;
	}

}
