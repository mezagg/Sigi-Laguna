/**
* Nombre del Programa : RegistrarSolicitudDefensorEncargadoSalaServiceImplTest.java
* Autor                            : vaguirre
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 1 Sep 2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.service.test.solicitud.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudDefensorEncargadoSalaService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author vaguirre
 *
 */
public class RegistrarSolicitudDefensorEncargadoSalaServiceImplTest
        extends
            BaseTestServicios<RegistrarSolicitudDefensorEncargadoSalaService> {

	public void testRegistrarSolicitudDefensorPJ() {
		try {
			Long[] imputadosIds = {3327L};
			List<InvolucradoDTO> listaProbablesResponsables = new ArrayList<InvolucradoDTO>(
					imputadosIds.length);
			for (Long imputadoId : imputadosIds) {
				listaProbablesResponsables.add(new InvolucradoDTO(imputadoId));
			}

			AudienciaDTO aud = new AudienciaDTO();
			aud.setId(694L);
			aud.setExpediente(new ExpedienteDTO(560L));
			
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
			CatDiscriminanteDTO catDiscriminanteDTO = new CatDiscriminanteDTO();
			CatDistritoDTO catDistritoDTO = new CatDistritoDTO(2L, "002",
					"Sin tribunal");
			catDiscriminanteDTO.setDistrito(catDistritoDTO);
			funcionarioDTO.setDiscriminante(catDiscriminanteDTO);

			usuarioDTO.setFuncionario(funcionarioDTO);
			aud.setUsuario(usuarioDTO);
			
			SolicitudDefensorDTO solicitudDefensorDTO = service.registrarSolicitudDefensorPJ(aud,
					listaProbablesResponsables);
			assertNotNull("Ocurrio un error en la generacion de la solicitud :", solicitudDefensorDTO);
			logger.info("Solicitud:"+ solicitudDefensorDTO);
			if(solicitudDefensorDTO.getAudiencia()!=null){
				AudienciaDTO audienciaDTO = solicitudDefensorDTO.getAudiencia();
				logger.info("Audiencia:"+ audienciaDTO);
				logger.info("Audiencia:"+ audienciaDTO.getId());
				logger.info("Audiencia:"+ audienciaDTO.getFolioAudiencia());
				logger.info("Audiencia:"+ audienciaDTO.getNumeroCausa());
				logger.info("Audiencia:"+ audienciaDTO.getNumeroTocaOrCarpeta());
				logger.info("Audiencia Consecutivo:"+ audienciaDTO.getConsecutivo());
				logger.info("Audiencia:"+ audienciaDTO.getFechaEvento());
				logger.info("Audiencia:"+ audienciaDTO.getFechaAsignacionSala());
				
				logger.info("Audiencia TipoAudiencia:"+ audienciaDTO.getTipoAudiencia());
				logger.info("Audiencia DuracionEstimada:"+ audienciaDTO.getDuracionEstimada());
				
				logger.info("Audiencia Sala:"+ audienciaDTO.getSala());
				if(audienciaDTO.getSala()!=null){
					logger.info("Audiencia Sala getSalaAudienciaId:"+ audienciaDTO.getSala().getSalaAudienciaId());
					logger.info("Audiencia Sala:"+ audienciaDTO.getSala().getDomicilioSala());
					logger.info("Audiencia Sala:"+ audienciaDTO.getSala().getMotivo());
					logger.info("Audiencia Sala:"+ audienciaDTO.getSala().getNombreSala());
					logger.info("Audiencia Sala:"+ audienciaDTO.getSala().getTotalEspacios());
					logger.info("Audiencia Sala:"+ audienciaDTO.getSala().getUbicacionSala());
					logger.info("Audiencia Sala:"+ audienciaDTO.getSala().getCatDiscriminanteDTO());
				}
				logger.info("Audiencia:"+ audienciaDTO.getSalaTemporal());
				
				logger.info("Audiencia:"+ audienciaDTO.getEstatusAudiencia());
				
				logger.info("Audiencia FechaHoraFin:"+ audienciaDTO.getFechaHoraFin());
			}
			
		} catch (NSJPNegocioException e) {
			fail(e.getMessage());
		}
	}
    
}
