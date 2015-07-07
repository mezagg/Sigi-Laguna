package mx.gob.segob.nsjp.service.elemento;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.elemento.ElementoDTO;

public interface AdjuntarArchivoAElementoService {

	
	public void adjuntarArchivoAElemento(ElementoDTO elemento,
			ArchivoDigitalDTO adjunto) throws NSJPNegocioException;
	
	/**
	 * Permite adjuntar un archivo digital a una tabla un elmento, dicha informacion se guardara en la tabla de cruce
	 * ElementoArchivoDigital
	 * @param elemento
	 * @param adjunto
	 * @throws NSJPNegocioException
	 */
	public void adjuntarArchivoAElementoTablaCruce(ElementoDTO elemento,ArchivoDigitalDTO adjunto) throws NSJPNegocioException;
	
	public ArchivoDigitalDTO leeIdIdentificadorMinMax(int operadorMinMax)throws NSJPNegocioException;
	public List<ArchivoDigitalDTO> leeRangosArchivosDigitales(Long inicio)throws NSJPNegocioException;
	public void modificaArchivosDigitales(List<ValorDTO> identificadorRutaArchivoDigital)throws NSJPNegocioException;
	
}
