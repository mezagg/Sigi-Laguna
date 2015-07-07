/**
 * Nombre del Programa : AmparoDelegateImpl.java
 * Autor                            : Emigdio Hern�ndez
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 15 / feb / 12
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci�n del Delegate para manipulaci�n de Amparo
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
package mx.gob.segob.nsjp.delegate.documento.impl;

import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.documento.AmparoDelegate;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoValorDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.service.amparo.AmparoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementaci�n del Delegate para manipulaci�n de Amparo
 * @author rgama
 *
 */
@Service("amparoDelegate")
public class AmparoDelegateImpl implements AmparoDelegate{

	@Autowired
    private AmparoService amparoService;

	@Override
	public List<InvolucradoDTO> consultarInvolucradosXAmparo(Long idAmparo)
			throws NSJPNegocioException {
				return amparoService.consultarInvolucradosXAmparo(idAmparo);
	}

	@Override
	public void insertarEstatusAmparo(DocumentoValorDTO documentoValorDTO)
			throws NSJPNegocioException {
		amparoService.insertarEstatusAmparo(documentoValorDTO);
		return;
	}

	@Override
	public Map<Long, DocumentoValorDTO> consultarEstatusPorIdsDocumentos(
			List<Long> idsDocumentos) throws NSJPNegocioException {
		return amparoService.consultarEstatusPorIdsDocumentos(idsDocumentos);
	}

	@Override
	public DocumentoValorDTO consultarEstatusPorIdDocumento(Long idDocumento)
			throws NSJPNegocioException {
		return amparoService.consultarEstatusPorIdDocumento(idDocumento);
	}

	@Override
	public void actualizaAmparo(DocumentoDTO amparo, DocumentoValorDTO estatus,
			List<Long> idsInvolucrados) throws NSJPNegocioException {
		amparoService.actualizaAmparo(amparo, estatus, idsInvolucrados);
		return;
	}
	
}
