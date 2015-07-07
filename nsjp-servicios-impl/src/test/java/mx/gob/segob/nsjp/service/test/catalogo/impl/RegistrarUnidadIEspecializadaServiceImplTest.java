/**
* Nombre del Programa : RegistrarUnidadIEspecializadaServiceImplTest.java
* Autor                            : AdrianECH
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 06/03/2012
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
package mx.gob.segob.nsjp.service.test.catalogo.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.CatUIEspecializadaDTO;
import mx.gob.segob.nsjp.service.catalogo.RegistrarUnidadIEspecializadaService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Describir el objetivo de la clase con punto al final.
 * Fecha: 06/03/2012
 * @version 1.0
 * @author AdrianECH
 * 
 */
public class RegistrarUnidadIEspecializadaServiceImplTest extends
		BaseTestServicios<RegistrarUnidadIEspecializadaService> {

	/**
	 * Test method for {@link mx.gob.segob.nsjp.service.catalogo.impl.RegistrarUnidadIEspecializadaServiceImpl#registrarUnidadIEspecializada(mx.gob.segob.nsjp.dto.catalogo.CatUIEspecializadaDTO)}.
	 */
	public void testRegistrarUnidadIEspecializada() {
		CatUIEspecializadaDTO dto=new CatUIEspecializadaDTO(null, "0002", "UIE Prueba2", null);
		try {
			CatUIEspecializadaDTO nDto = service.registrarUnidadIEspecializada(dto);
			assertNotNull(nDto);
			logger.info("Se registr� el objeto: "+nDto.getClaveUIE());
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			assertTrue(false);
		}
		
	}

}
