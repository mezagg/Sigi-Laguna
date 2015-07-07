/**
 * Nombre del Programa	: EnviarAcuseDeReciboDeCarpetaDeInvestigacionServiceImpl.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 19 Dic 2012
 * Marca de cambio      : N/A
 * Descripcion General  : Env&iacute;a el documento de acuse de recibo
 * 						  de la carpeta de investigaci&oacute;n
 * Programa Dependiente	: N/A
 * Programa Subsecuente	: N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                : N/A
 * Compania             : N/A
 * Proyecto             : N/A                                 Fecha: N/A
 * Modificacion         : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.documento.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoMovimiento;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.GuardadoDefinitivoDTO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.RegistroBitacora;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.bitacora.RegistrarBitacoraService;
import mx.gob.segob.nsjp.service.documento.EnviarAcuseDeReciboDeCarpetaDeInvestigacionService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Env&iacute;a el documento de acuse de recibo de la carpeta de
 * investigaci&oacute;n
 * 
 * @author AlejandroGA
 * @version 1.0
 */
@Service
public class EnviarAcuseDeReciboDeCarpetaDeInvestigacionServiceImpl implements
		EnviarAcuseDeReciboDeCarpetaDeInvestigacionService {

	public static final Logger logger = Logger
			.getLogger(EnviarAcuseDeReciboDeCarpetaDeInvestigacionServiceImpl.class);

	@Autowired
	private DocumentoDAO documentoDAO;
	@Autowired
	private ClienteGeneralService clienteGeneralService;
	@Autowired
	private RegistrarBitacoraService registrarBitacoraService;

	
	/**
	 *
	 */
	@Override
	public void enviarAcuseDeReciboDeCarpetaDeInvestigacionService(
			GuardadoDefinitivoDTO guardadoDefinitivoDTO)
			throws NSJPNegocioException {

		logger.debug("**********BIENVENIDO AL SERVICIO PARA ENVIAR ACUSE DE RECIBO**************");
		logger.debug("***************** DE CARPETA DE INVEESTIGACION****************************");

		if (guardadoDefinitivoDTO == null
				|| guardadoDefinitivoDTO.getExpedienteDTO() == null
				|| guardadoDefinitivoDTO.getExpedienteDTO().getCasoDTO() == null
				|| guardadoDefinitivoDTO.getExpedienteDTO().getCasoDTO()
						.getNumeroGeneralCaso() == null
				|| guardadoDefinitivoDTO.getExpedienteDTO().getCasoDTO()
						.getNumeroGeneralCaso().trim().isEmpty()
				|| guardadoDefinitivoDTO.getExpedienteDTO()
						.getNumeroExpedienteId() == null
				|| guardadoDefinitivoDTO.getDocumentoDTO() == null
				|| guardadoDefinitivoDTO.getDocumentoDTO().getDocumentoId() == null
				|| guardadoDefinitivoDTO.getDocumentoDTO().getDocumentoId() <= 0L
				|| guardadoDefinitivoDTO.getFuncionarioDTO() == null
				|| guardadoDefinitivoDTO.getFuncionarioDTO()
						.getClaveFuncionario() == null) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Documento documento = documentoDAO.read(guardadoDefinitivoDTO
				.getDocumentoDTO().getDocumentoId());

		if (documento == null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		Long idDocRespuesta = 0L;

		DocumentoDTO documentoToSend = DocumentoTransformer
				.transformarDocumento(documento);

		// Nueva actividad que sera, registrada del lado del servidor
		ActividadDTO nuevaActividadDTO = new ActividadDTO();
		nuevaActividadDTO
				.setTipoActividad(Actividades.RECIBIR_ACUSE_DE_RECIBO_DE_CARPETA_DE_INVESTIGACION);

		documentoToSend.setActividadDTO(nuevaActividadDTO);

		List<DocumentoDTO> documentosDtoToSend = new ArrayList<DocumentoDTO>();

		documentosDtoToSend.add(documentoToSend);

		idDocRespuesta =  clienteGeneralService.enviarDocumentoAInstitucion(documentosDtoToSend,
						guardadoDefinitivoDTO.getExpedienteDTO().getCasoDTO()
								.getNumeroGeneralCaso().trim(),
						Instituciones.PGJ);

		
		if (idDocRespuesta != null && idDocRespuesta > 0L) {
			// REGISTRAR EN BITACORA DEL EXPEDIENTE
			//TODO AGA CAMBIAR EL TIPO DE MOVIMIENTO A ENVIAR ACUSE DE RECIBO DE CARPETA DE INV. 
			final RegistroBitacora regBitFun = new RegistroBitacora();

			regBitFun.setFechaInicio(new Date());
			regBitFun.setTipoMovimiento(new Valor(
					TipoMovimiento.RECIBIR_CARPETA_DE_INVESTIGACION
							.getValorId()));
			regBitFun.setNuevo(String.valueOf(guardadoDefinitivoDTO
					.getFuncionarioDTO().getClaveFuncionario()));

			regBitFun.setNumeroExpediente(new NumeroExpediente(
					guardadoDefinitivoDTO.getExpedienteDTO()
							.getNumeroExpedienteId()));

			registrarBitacoraService
					.registrarMovimientoDeExpedienteEnBitacora(regBitFun);
		}
	}

}
