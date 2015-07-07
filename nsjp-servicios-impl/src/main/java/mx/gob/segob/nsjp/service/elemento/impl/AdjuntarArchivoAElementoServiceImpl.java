/**
 * Nombre del Programa  : AdjuntarArchivoASolicitudServiceImpl.java
 * Autor                : Daniel Jiménez
 * Compania             : TATTVA-IT
 * Proyecto             : NSJP                    Fecha: 24 Jun 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Guarda un archivo omo un ArchivoDijital y lo asocia a 
 * 						  una solicitud como archivo adjunto.
 * Programa Dependiente : N/A
 * Programa Subsecuente : N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                :N/A
 * Compania             :N/A
 * Proyecto             :N/A                                 Fecha: N/A
 * Modificacion         :N/A
 *------------------------------------------------------------------------------
 */

package mx.gob.segob.nsjp.service.elemento.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.elemento.ElementoArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.elemento.ElementoDAO;
import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.elemento.ElementoDTO;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.Elemento;
import mx.gob.segob.nsjp.model.ElementoArchivoDigital;
import mx.gob.segob.nsjp.model.ElementoArchivoDigitalId;
import mx.gob.segob.nsjp.model.Parametro;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;
import mx.gob.segob.nsjp.service.elemento.AdjuntarArchivoAElementoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdjuntarArchivoAElementoServiceImpl
        implements
            AdjuntarArchivoAElementoService {

    private static final Logger logger = Logger
            .getLogger(AdjuntarArchivoAElementoServiceImpl.class);

    @Autowired
    private ElementoDAO elementoDAO;

    @Autowired
    private ArchivoDigitalDAO archivoDigitalDAO;
    @Autowired
    private ElementoArchivoDigitalDAO elementoArchivoDigitalDAO;
    @Autowired
    private ParametroDAO parametroDAO;

    @Override
    public void adjuntarArchivoAElemento(ElementoDTO elementoDTO,
            ArchivoDigitalDTO adjuntoDTO) throws NSJPNegocioException {

        logger.debug("SERVICIO QUE ACTUALIZA LA FOTO DE UN ELEMENTO : "
                + elementoDTO.getClass().getName());
        if (elementoDTO != null) {
            logger.info("ElementoId:" + elementoDTO.getElementoId());
        }
        logger.info("Adjunto:" + adjuntoDTO);

        if (elementoDTO == null || elementoDTO.getElementoId() == null
                || adjuntoDTO == null)
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

        Elemento loElementoBD = elementoDAO.read(elementoDTO.getElementoId());
        
        ArchivoDigital ad = ArchivoDigitalTransformer
                .transformarArchivoDigitalDTO(adjuntoDTO);

        Long idAD = archivoDigitalDAO.create(ad);
        ad.setArchivoDigitalId(idAD);
        loElementoBD.setArchivoDigital(ad);
        Parametro parametro=parametroDAO.obtenerPorClave(Parametros.URL_DESTINO_ARCHIVOS);
        String nombreArchivo=idAD.toString();
        if(adjuntoDTO.getTipoArchivo().equals("image/jpeg")){
        	nombreArchivo=nombreArchivo+".jpeg";
        }else{
        	nombreArchivo=nombreArchivo+adjuntoDTO.getTipoArchivo();
        }
        String urlArchivo=parametro.getValor()+""+loElementoBD.getExpediente().getExpedienteId();
        logger.info("ARCHIVO DIGITAL INSERTADO CON ID :: " + idAD +  "URL:"+urlArchivo);
    }
    
    
    public void adjuntarArchivoAElementoTablaCruce(ElementoDTO elemento, ArchivoDigitalDTO adjunto) throws NSJPNegocioException {

        if (elemento == null || elemento.getElementoId() == null
                || adjunto == null)
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        

        if (elemento != null) {
            logger.info("ElementoId:" + elemento.getElementoId());
        }
        logger.info("Adjunto:" + adjunto);
        Long idAD = archivoDigitalDAO.create(ArchivoDigitalTransformer
        		.transformarArchivoDigitalDTO(adjunto));

        
        ElementoArchivoDigitalId loElementoArchivoDigitalId = new ElementoArchivoDigitalId();
        loElementoArchivoDigitalId.setArchivoDigitalId(idAD);
        loElementoArchivoDigitalId.setElementoId(elemento.getElementoId());
        
        ElementoArchivoDigital loElementoArchivoDigital = new ElementoArchivoDigital();
        loElementoArchivoDigital.setId(loElementoArchivoDigitalId);
        
        ElementoArchivoDigitalId elementoArchivoDigitalId = elementoArchivoDigitalDAO.create(loElementoArchivoDigital);
		logger.info("ARCHIVO DIGITAL INSERTADO CON ID :: "
				+ elementoArchivoDigitalId != null ? (elementoArchivoDigitalId
				.getElementoId() + "-" + elementoArchivoDigitalId
				.getArchivoDigitalId()) : "");
    }


	@Override
	public ArchivoDigitalDTO leeIdIdentificadorMinMax(int operadorMinMax)throws NSJPNegocioException {
		ArchivoDigital archivoDigital=archivoDigitalDAO.leeIdIdentificador(operadorMinMax);
		ArchivoDigitalDTO archivoDigitalDTO=null;
		if(archivoDigital!=null && archivoDigital.getArchivoDigitalId()!=null){
			archivoDigitalDTO=new ArchivoDigitalDTO(archivoDigital.getArchivoDigitalId());
		}
		return archivoDigitalDTO;
	}
	@Override
	public List<ArchivoDigitalDTO> leeRangosArchivosDigitales(Long inicio)throws NSJPNegocioException{
		List<ArchivoDigitalDTO> listArchivoDigitalDTOs=new ArrayList<ArchivoDigitalDTO>();
		List<ArchivoDigital>listArchivoDigitals=archivoDigitalDAO.leeRangosArchivosDigitales(inicio);
		for (ArchivoDigital archivoDigital : listArchivoDigitals) {
			ArchivoDigitalDTO archivoDigitalDTO=ArchivoDigitalTransformer.transformarArchivoDigital(archivoDigital);
			if(archivoDigitalDTO!=null){
				listArchivoDigitalDTOs.add(archivoDigitalDTO);
			}
		}
		return listArchivoDigitalDTOs;
	}


	@Override
	public void modificaArchivosDigitales(
			List<ValorDTO> identificadorRutaArchivoDigital)
			throws NSJPNegocioException {
		 if (identificadorRutaArchivoDigital == null)
	            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		 else if (identificadorRutaArchivoDigital.isEmpty())
			 throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		 
		 archivoDigitalDAO.modificaArchivosDigitales(identificadorRutaArchivoDigital);
	}

}
