/**
 * Nombre del Programa		: DocumentoWSDTOTransformer.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP							Fecha: 09/07/2013
 * Marca de cambio			: N/A
 * Descripcion General		: DocumentoWSDTOTransformer
 * Programa Dependiente		: N/A
 * Programa Subsecuente		: N/A
 * Cond. de ejecucion       : N/A
 * Dias de ejecucion        : N/A                           Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                    : N/A
 * Compania               	: N/A
 * Proyecto                 : N/A                           Fecha: N/A
 * Modificacion           	: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.infra.impl.transform.recibiractualizacionmandamiento;

import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoWSDTO;
import mx.gob.segob.nsjp.service.infra.impl.transform.WsTransformer;
import mx.gob.segob.nsjp.dto.documento.DocumentoWSDTO;

/**
 * Transforma un Documento DTO a WSDTO o viceversa
 * 
 * @author AlejandroGA
 * 
 */
public class DocumentoWSDTOTransformer {

	public static DocumentoDTO transformar(DocumentoWSDTO src) {

		DocumentoDTO documentoDTO = null;

		if (src == null) {
			return documentoDTO;
		}
		if (src instanceof MandamientoWSDTO) {
			documentoDTO = new MandamientoDTO();
		} else {
			documentoDTO = new DocumentoDTO();
		}

		documentoDTO.setFechaCreacion(src.getFechaCreacion());
		documentoDTO.setFolioDocumento(src.getFolioDocumento());
		documentoDTO.setNombreDocumento(src.getNombreDocumento());
		documentoDTO.setNumeroFojas(src.getNumeroFojas());
		documentoDTO.setOrigenDocumento(src.getOrigenDocumento());
		documentoDTO.setTextoParcial(src.getTextoParcial());
		documentoDTO.setVersion(src.getVersion());
		documentoDTO.setDescripcion(src.getDescripcion());
		documentoDTO.setEsGuardadoParcial(src.getEsGuardadoParcial());
		documentoDTO.setJerarquiaOrganizacional(src
				.getJerarquiaOrganizacional());

		if (src.getConfInstitucionId() != null) {
			documentoDTO.setConfInstitucion(new ConfInstitucionDTO(src
					.getConfInstitucionId()));
		}

		if (src.getTipoDocumentoDTO() != null) {
			documentoDTO.setTipoDocumentoDTO(new ValorDTO(src
					.getTipoDocumentoDTO()));
		}

		if (src.getArchivoDigital() != null) {
			mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalWSDTO archivoDigitalWSDTO = src
					.getArchivoDigital();

			ArchivoDigitalDTO archivoDigitalDTO = new ArchivoDigitalDTO();

			archivoDigitalDTO.setNombreArchivo(archivoDigitalWSDTO
					.getNombreArchivo());
			archivoDigitalDTO.setTipoArchivo(archivoDigitalWSDTO
					.getTipoArchivo());
			archivoDigitalDTO.setContenido(archivoDigitalWSDTO.getContenido());

			documentoDTO.setArchivoDigital(archivoDigitalDTO);
		}

		return documentoDTO;
	}

	public static mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento.DocumentoWSDTO transformar(
			DocumentoDTO src) {

		mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento.DocumentoWSDTO documentoWSDTO = null;

		if (src == null) {
			return documentoWSDTO;
		}

		if (src instanceof MandamientoDTO) {
			documentoWSDTO = new mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento.MandamientoWSDTO();
		} else {
			documentoWSDTO = new mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento.DocumentoWSDTO();
		}

		documentoWSDTO.setFechaCreacion(WsTransformer.transformFecha(src
				.getFechaCreacion()));
		documentoWSDTO.setFolioDocumento(src.getFolioDocumento());
		documentoWSDTO.setNombreDocumento(src.getNombreDocumento());
		documentoWSDTO.setNumeroFojas(src.getNumeroFojas());
		documentoWSDTO.setOrigenDocumento(src.getOrigenDocumento());
		documentoWSDTO.setTextoParcial(src.getTextoParcial());
		documentoWSDTO.setVersion(src.getVersion());
		documentoWSDTO.setDescripcion(src.getDescripcion());
		documentoWSDTO.setEsGuardadoParcial(src.getEsGuardadoParcial());
		documentoWSDTO.setJerarquiaOrganizacional(src
				.getJerarquiaOrganizacional());

		if (src.getConfInstitucion() != null
				&& src.getConfInstitucion().getConfInstitucionId() != null) {
			documentoWSDTO.setConfInstitucionId(src.getConfInstitucion()
					.getConfInstitucionId());
		}

		if (src.getTipoDocumentoDTO() != null
				&& src.getTipoDocumentoDTO().getIdCampo() != null) {
			documentoWSDTO.setTipoDocumentoDTO(src.getTipoDocumentoDTO()
					.getIdCampo());
		}

		if (src.getArchivoDigital() != null) {

			mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento.ArchivoDigitalWSDTO archivoDigitalWSDTO = new mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento.ArchivoDigitalWSDTO();

			archivoDigitalWSDTO.setNombreArchivo(src.getArchivoDigital()
					.getNombreArchivo());
			archivoDigitalWSDTO.setTipoArchivo(src.getArchivoDigital()
					.getTipoArchivo());
			archivoDigitalWSDTO.setContenido(src.getArchivoDigital()
					.getContenido());

			documentoWSDTO.setArchivoDigital(archivoDigitalWSDTO);
		}

		return documentoWSDTO;
	}

}
