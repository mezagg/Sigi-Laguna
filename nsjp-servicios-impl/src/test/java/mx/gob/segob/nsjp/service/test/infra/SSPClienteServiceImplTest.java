/**
 * Nombre del Programa		: SSPClienteServiceImplTest.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP							Fecha: 26/06/2013
 * Marca de cambio			: N/A
 * Descripcion General		: Clase Tests para SSPClienteServiceImplTest
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
package mx.gob.segob.nsjp.service.test.infra;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.service.infra.SSPClienteService;
import mx.gob.segob.nsjp.service.mandamiento.AdministrarMandamientoJudicialService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author AlejandroGA
 * @version 1.0
 */

public class SSPClienteServiceImplTest  extends BaseTestServicios<SSPClienteService> {
	
	@Autowired
	public AdministrarMandamientoJudicialService administrarMandamientoJudicialService;
	
	public void testEnviarMandamiento() {

		try {
			MandamientoDTO mandamientoDTO = administrarMandamientoJudicialService
					.consultarMandamientoPorId(2664L);

			service.enviarMandamiento(mandamientoDTO);

			assertNotNull(mandamientoDTO);

		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}
}
