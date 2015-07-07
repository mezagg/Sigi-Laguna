/**
 * Nombre del Programa		: AdministrarMandamientoJudicialDelegateImpl.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP							Fecha: 13/06/2013
 * Marca de cambio			: N/A
 * Descripcion General		: Implementacion de AdministrarMandamientoJudicialDelegate
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
package mx.gob.segob.nsjp.delegate.mandamiento.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.mandamiento.AdministrarMandamientoJudicialDelegate;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoDTO;
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.mandamiento.AdministrarMandamientoJudicialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AlejandroGA
 * @version 1.0
 */

@Service
public class AdministrarMandamientoJudicialDelegateImpl implements
		AdministrarMandamientoJudicialDelegate {

	@Autowired
	private AdministrarMandamientoJudicialService administrarMandamientoJudicialService;

	
	@Override
	public MandamientoDTO generarMandamientoJudicial(
			MandamientoDTO mandamientoDTO) throws NSJPNegocioException {

		return administrarMandamientoJudicialService
				.generarMandamientoJudicial(mandamientoDTO);
	}


	@Override
	public List<MandamientoDTO> consultarMandamientoPorFiltro(
			FiltroMandamientoDTO filtroMandamientoDTO)
			throws NSJPNegocioException {
		return administrarMandamientoJudicialService.consultarMandamientoPorFiltro(filtroMandamientoDTO);
	}


	@Override
	public MandamientoDTO consultarMandamientoPorId(Long mandamientoJudicialId)
			throws NSJPNegocioException {
		return administrarMandamientoJudicialService
				.consultarMandamientoPorId(mandamientoJudicialId);
	}


	@Override
	public List<MandamientoPersonaDTO> consultarMandamientosPersonaPorFiltro(
			FiltroMandamientoPersonaDTO filtroMandamientoPersonaDTO)
			throws NSJPNegocioException {
		return administrarMandamientoJudicialService.consultarMandamientosPersonaPorFiltro(filtroMandamientoPersonaDTO);
	}


	@Override
	public Long actualizarMandamientoPersona(
			List<MandamientoPersonaDTO> listaMandamientosPersona,
			UsuarioDTO usuarioDTO, MandamientoDTO mandamientoDTO,
			DocumentoDTO documentoDTO) throws NSJPNegocioException {
		return administrarMandamientoJudicialService
				.actualizarMandamientoPersona(listaMandamientosPersona,
						usuarioDTO, mandamientoDTO, documentoDTO);

	}


	@Override
	public void enviarMandamientoJudicial(MandamientoDTO mandamientoDTO)
			throws NSJPNegocioException {
		administrarMandamientoJudicialService.enviarMandamientoJudicial(mandamientoDTO);
	}

	@Override
	public void actualizarEstatusMandamiento(MandamientoDTO mandamientoDTO,
			Boolean esCalcularEstatus) throws NSJPNegocioException {
		administrarMandamientoJudicialService.actualizarEstatusMandamiento(
				mandamientoDTO, esCalcularEstatus);
	}


	@Override
	public void enviarDocumentoCambioEstatusMandamiento(
			MandamientoDTO mandamientoDTO, DocumentoDTO documentoDTO)
			throws NSJPNegocioException {

		administrarMandamientoJudicialService
				.enviarDocumentoCambioEstatusMandamiento(mandamientoDTO,
						documentoDTO);
	}


	@Override
	public Long adjuntarDocumentoAMandamientoJudicial(
			DocumentoDTO documentoDTO, MandamientoDTO mandamientoJudicialDTO,
			TipoDocumento tipoDocumento) throws NSJPNegocioException {

		return administrarMandamientoJudicialService
				.adjuntarDocumentoAMandamientoJudicial(documentoDTO,
						mandamientoJudicialDTO, tipoDocumento);
	}
}
