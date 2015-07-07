/**
 * Nombre del Programa	: DocumentoWSDTOTransformer.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Nov 2012
 * Marca de cambio      : N/A
 * Descripcion General  : Transformer DocumentoWSDTO para el paquete de recibiracusederecibodesolicituddedefensor
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
package mx.gob.segob.nsjp.service.infra.impl.transform.recibiracusederecibodesolicituddedefensor;

import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.service.infra.impl.transform.WsTransformer;
import mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.ActividadWSDTO;
import mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.ArchivoDigitalWSDTO;
import mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.DocumentoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.SolicitudDefensorWSDTO;
import mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.SolicitudWSDTO;

/**
 * Clase para transformar un DocumentoWSDTO para el paquete de
 * recibiracusederecibodesolicituddedefensor
 * 
 * @author AlejandroGA
 * @version 1.0
 */
public class DocumentoWSDTOTransformer {

	public static DocumentoWSDTO transformarWSDTO(DocumentoDTO src) {

		DocumentoWSDTO documentoWSDTO = null;

		if (src == null) {
			return documentoWSDTO;
		}

		if (src instanceof SolicitudDefensorDTO) {
			documentoWSDTO = new SolicitudDefensorWSDTO();
		} else if (src instanceof SolicitudDTO) {
			documentoWSDTO = new SolicitudWSDTO();
		} else {
			documentoWSDTO = new DocumentoWSDTO();
		}

		// Actividad
		if (src.getActividadDTO() != null) {

			ActividadWSDTO actividadWSDTO = new ActividadWSDTO();

			actividadWSDTO.setTipoActividadId(src.getActividadDTO()
					.getTipoActividad().getValorId());
			actividadWSDTO.setNombre(src.getActividadDTO().getNombre());
			documentoWSDTO.setActividad(actividadWSDTO);
		}

		// Archivo Digital
		if (src.getArchivoDigital() != null) {

			ArchivoDigitalWSDTO archivoDigitalWSDTO = new ArchivoDigitalWSDTO();

			ArchivoDigitalDTO archivoDigitalDto = src.getArchivoDigital();
			archivoDigitalWSDTO.setNombreArchivo(archivoDigitalDto
					.getNombreArchivo());
			archivoDigitalWSDTO.setTipoArchivo(archivoDigitalDto
					.getTipoArchivo());
			archivoDigitalWSDTO.setContenido(archivoDigitalDto.getContenido());

			documentoWSDTO.setArchivoDigital(archivoDigitalWSDTO);
		}

		// Clave discriminante origen
		documentoWSDTO.setClaveDiscriminanteOrigen(src
				.getClaveDiscriminanteOrigen());

		// Descripcion
		documentoWSDTO.setDescripcion(src.getDescripcion());

		// Fecha de creacion y ademas str fecha y hora de creacion
		if (src.getFechaCreacion() != null) {
			documentoWSDTO.setStrFechaCreacion(DateUtils.formatear(src
					.getFechaCreacion()));
			documentoWSDTO.setFechaCreacion(WsTransformer.transformFecha(src
					.getFechaCreacion()));
			documentoWSDTO.setStrHoraCreacion(DateUtils.formatearHora(src
					.getFechaCreacion()));
		}

		// Folio
		documentoWSDTO.setFolioDocumento(src.getFolioDocumento());
		// Foma Id
		documentoWSDTO.setFormaId(src.getFormaDTO() != null ? src.getFormaDTO()
				.getFormaId() : Formas.PLANTILLA_EN_BLANCO.getValorId());
		// Nombre
		documentoWSDTO.setNombreDocumento(src.getNombreDocumento());
		// Numero Fojas
		documentoWSDTO.setNumeroFojas(src.getNumeroFojas());
		// Origen
		documentoWSDTO.setOrigenDocumento(src.getOrigenDocumento());
		// texto parcial
		documentoWSDTO.setTextoParcial(src.getTextoParcial());
		// Tipo documento
		documentoWSDTO
				.setTipoDocumentoDTO(src.getTipoDocumentoDTO() != null ? src
						.getTipoDocumentoDTO().getIdCampo()
						: TipoDocumento.ACUSE.getValorId());
		// Version
		documentoWSDTO.setVersion(src.getVersion());

		documentoWSDTO
				.setConfInstitucionId(src.getConfInstitucion() != null ? src
						.getConfInstitucion().getConfInstitucionId() : null);
		documentoWSDTO.setJerarquiaOrganizacional(src.getJerarquiaOrganizacional());
		return documentoWSDTO;
	}

}
