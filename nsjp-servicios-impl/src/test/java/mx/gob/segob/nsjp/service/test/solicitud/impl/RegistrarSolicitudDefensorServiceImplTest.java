/**
 * Nombre del Programa : RegistrarSolicitudDefensoriaServiceImplTest.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 02/11/2012
 * Marca de cambio        : N/A
 * Descripcion General    : Prueba unitaria de RegistrarSolicitudDefensoriaServiceImpl
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
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDAO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudDefensorService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Prueba unitaria de RegistrarSolicitudDefensoriaServiceImpl
 * 
 * @author GustavoBP
 * 
 */
public class RegistrarSolicitudDefensorServiceImplTest extends
		BaseTestServicios<RegistrarSolicitudDefensorService> {

	@Autowired
	private SolicitudDefensorDAO solicitudDefensorDAO;

	public void testRegistrarSolicitudDefensorInvolucrados() {
		try {
			// Registra nueva solicitud
			SolicitudDefensor solDefensor = new SolicitudDefensor();
			solDefensor.setTipoSolicitud(new Valor(1772L));
			solDefensor.setFolioSolicitud("FG/200900001");
			solDefensor.setFechaCreacion(new Date());
			solDefensor.setForma(new Forma(392L));
			solDefensor.setNombreDocumento("Solicitud de defensor");
			solDefensor.setTipoDocumento(new Valor(82L));
			Long idSolicitud = solicitudDefensorDAO.create(solDefensor);
			logger.info("Solicitud Creada : " + idSolicitud);

			// Solicitud registrada
			// Long idSolicitud = 111L;
			SolicitudDefensorDTO solicitudDefensorDTO = new SolicitudDefensorDTO(
					idSolicitud);

			Long[] idIn = { 12026L, 12030L, 12033L };
			// List<Long> idInvolucrados = Arrays.asList(idIn);
			List<InvolucradoDTO> involucrados = new ArrayList<InvolucradoDTO>();
			for (Long identificador : idIn) {
				involucrados.add(new InvolucradoDTO(identificador));
			}

			Boolean exitoso = service.registrarSolicitudDefensorInvolucrados(
					involucrados, solicitudDefensorDTO);
			assertFalse("Ocurrio un error en el registro de la solicitud",
					exitoso);
			logger.info("Registro exitoso");
		} catch (NSJPNegocioException e) {
			logger.info("Ocurrio un error en el registro de la solicitud");
		}
	}
}
