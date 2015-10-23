/**
 * Nombre del Programa : ConfActividadDocumentoDelegateImpl.java Autor :
 * GustavoBP Compania : Ultrasist Proyecto : NSJP Fecha: 17/08/2011 Marca de
 * cambio : N/A Descripcion General : Describir el objetivo de la clase de
 * manera breve Programa Dependiente :N/A Programa Subsecuente :N/A Cond. de
 * ejecucion :N/A Dias de ejecucion :N/A Horario: N/A MODIFICACIONES
 * ------------------------------------------------------------------------------
 * Autor :N/A Compania :N/A Proyecto :N/A Fecha: N/A Modificacion :N/A
 * ------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.delegate.actividad.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.actividad.ConfActividadDocumentoDelegate;
import mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO;
import mx.gob.segob.nsjp.dto.ConfActividadDocumentoRolDTO;
import mx.gob.segob.nsjp.dto.actividad.ConfTipoActividadOrigenDestinoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.actividad.ConsultarConfActividadDocumentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Describir el objetivo de la clase con punto al final.
 *
 * @version 1.0
 * @author GustavoBP
 *
 */
@Service
public class ConfActividadDocumentoDelegateImpl implements
        ConfActividadDocumentoDelegate {

    @Autowired
    private ConsultarConfActividadDocumentoService consultarConfActividadDocumentoService;

    @Override
    public List<ConfActividadDocumentoDTO> consultarConfActividadDocumento(
            UsuarioDTO usuario, ExpedienteDTO expedienteDto, Long idCategoriaActividad)
            throws NSJPNegocioException {
        //bueno
        return consultarConfActividadDocumentoService.consultarConfActividadDocumento(usuario, expedienteDto, idCategoriaActividad);
    }

    @Override
    public List<ConfActividadDocumentoDTO> consultarConfActividadDocumento(
            UsuarioDTO usuario, ExpedienteDTO expedienteDto, Long idCategoriaActividad, Boolean sinCatUie)
            throws NSJPNegocioException {
        //el que voy a ocupar
        return consultarConfActividadDocumentoService.consultarConfActividadDocumento(usuario, expedienteDto, idCategoriaActividad, sinCatUie);
    }

    @Override
    public ConfActividadDocumentoDTO consultaConfActividadDocumentoPorId(
            Long idConfActividadDocumento) throws NSJPNegocioException {
        return consultarConfActividadDocumentoService.consultaConfActividadDocumentoPorId(idConfActividadDocumento);
    }

    @Override
    public List<ValorDTO> consultarEstadosPorJerarquiaOrganizacional(Long idJerarquiaOrganizacional) throws NSJPNegocioException {
        return consultarConfActividadDocumentoService.consultarEstadosPorJerarquiaOrganizacional(idJerarquiaOrganizacional);
    }

    /* (non-Javadoc)
     * @see mx.gob.segob.nsjp.delegate.actividad.ConfActividadDocumentoDelegate#consultarConfActividadDocumentoPorConfTipoActividadDestino(mx.gob.segob.nsjp.dto.actividad.ConfTipoActividadOrigenDestinoDTO)
     */
    @Override
    public List<ConfActividadDocumentoDTO> consultarConfActividadDocumentoPorConfTipoActividadDestino(
            ConfTipoActividadOrigenDestinoDTO filtro)
            throws NSJPNegocioException {
        if (filtro != null) {
            try {
                return consultarConfActividadDocumentoService.consultarConfActividadDocumentoPorConfTipoActividadDestino(filtro);
            } catch (Exception e) {
                throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA, e);
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.gob.segob.nsjp.delegate.actividad.ConfActividadDocumentoDelegate#consultaConfActividadDocumentoPorIdActividad(mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO)
     */
    @Override
    public ConfActividadDocumentoDTO consultaConfActividadDocumentoPorIdActividad(
            ConfActividadDocumentoDTO filtro) throws NSJPNegocioException {
        return consultarConfActividadDocumentoService.consultaConfActividadDocumentoPorIdActividad(filtro);
    }

    @Override
    public List<ConfActividadDocumentoDTO> consultarConfActividadDocumentoFiltro(
            ConfActividadDocumentoDTO filtroConfActividadDocumentoDTO)
            throws NSJPNegocioException {
        return consultarConfActividadDocumentoService
                .consultarConfActividadDocumentoFiltro(filtroConfActividadDocumentoDTO);
    }

}
