package mx.gob.segob.nsjp.service.infra.impl;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.MedidaAlterna;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;
import mx.gob.segob.nsjp.service.infra.SSPClienteService;
import mx.gob.segob.nsjp.service.infra.impl.transform.WsTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.registrarmandamiento.MandamientoWSDTOTransformer;
import mx.gob.segob.nsjp.ws.cliente.mandamiento.RegistrarMandamientoExporter;
import mx.gob.segob.nsjp.ws.cliente.mandamiento.RegistrarMandamientoExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.medidaalterna.MedidaAlternaWSDTO;
import mx.gob.segob.nsjp.ws.cliente.medidaalterna.RegistrarMedidaAlternaServiceExporter;
import mx.gob.segob.nsjp.ws.cliente.medidaalterna.RegistrarMedidaAlternaServiceExporterImplService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class SSPClienteServiceImpl implements SSPClienteService {
    /**
     * Logger.
     */
    private final static Logger logger = Logger
            .getLogger(SSPClienteServiceImpl.class);

    @Autowired
    private ConfInstitucionDAO confInsDao;
   
    @Override
    public void enviarMedidaAlterna(MedidaAlterna input)
            throws NSJPNegocioException {
        try {

            if (input.getInvolucrado() == null
                    || input.getInvolucrado().getNombreDemograficos() == null
                    || input.getArchivoDigital() == null
                    || input.getNumeroCarpetaEjecucion() == null
                    || input.getJuezOrdena() == null
                    || input.getNumeroCaso() == null
                    || input.getNumeroCausa() == null
                    || input.getValorPeriodicidad() == null
                    || input.getFechaCreacion() == null
                    || input.getFechaInicio() == null
                    || input.getFechaFin() == null) {
                throw new NSJPNegocioException(
                        CodigoError.PARAMETROS_INSUFICIENTES);
            }

            URL url = new URL(confInsDao.read(Instituciones.SSP.getValorId())
                    .getUrlInst()
                    + "/RegistrarMedidaAlternaServiceExporterImplService?wsdl");

            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "RegistrarMedidaAlternaServiceExporterImplService");

            RegistrarMedidaAlternaServiceExporterImplService ss = new RegistrarMedidaAlternaServiceExporterImplService(
                    url, SERVICE_NAME);

            RegistrarMedidaAlternaServiceExporter port = ss
                    .getRegistrarMedidaAlternaServiceExporterImplPort();

            MedidaAlternaWSDTO toSend = new MedidaAlternaWSDTO();

            toSend.setAnios(input.getAnios());
            toSend.setMeses(input.getMeses());

            NombreDemografico nombre = null;
            if (input.getInvolucrado().getNombreDemograficos() != null) {
                for (NombreDemografico nd : input.getInvolucrado()
                        .getNombreDemograficos()) {
                    if (nd.getEsVerdadero()) {
                        nombre = nd;
                    }
                }
                if (nombre == null) {
                    nombre = input.getInvolucrado().getNombreDemograficos()
                            .iterator().next();
                }
                toSend.setNombreSujeto(nombre.getNombre());
                toSend.setAPaternoSujeto(nombre.getApellidoPaterno());
                toSend.setAMaternoSujeto(nombre.getApellidoMaterno());
            }

            toSend.setDescripcionMedida(input.getDescripcionMedida());
            toSend.setFechaCreacion(WsTransformer.transformFecha(input
                    .getFechaCreacion()));
            toSend.setFechaInicio(WsTransformer.transformFecha(input
                    .getFechaInicio()));
            toSend.setFechaFin(WsTransformer.transformFecha(input.getFechaFin()));
            toSend.setFolioDocumento(input.getFolioDocumento());
            toSend.setIdValorPeriodicidad(input.getValorPeriodicidad()
                    .getValorId());
            toSend.setIdValorTipoMedida(input.getValorTipoMedida().getValorId());
            toSend.setJuezOrdena(input.getJuezOrdena());
            toSend.setNumeroCarpetaEjecucion(input.getNumeroCarpetaEjecucion());
            toSend.setNumeroCaso(input.getNumeroCaso());
            toSend.setNumeroCausa(input.getNumeroCausa());
            toSend.setFormaId(input.getForma().getFormaId());
            toSend.setTipoDocumentoDTO(input.getTipoDocumento().getValorId());

            mx.gob.segob.nsjp.ws.cliente.medidaalterna.ArchivoDigitalWSDTO archivo = new mx.gob.segob.nsjp.ws.cliente.medidaalterna.ArchivoDigitalWSDTO();
            ArchivoDigital original = input.getArchivoDigital();
            ArchivoDigitalDTO temArchivo=ArchivoDigitalTransformer.transformarArchivoDigital(original);
            archivo.setContenido(temArchivo.getContenido());
            archivo.setNombreArchivo(original.getNombreArchivo());
            archivo.setTipoArchivo(original.getTipoArchivo());
            toSend.setArchivoDigital(archivo);
            logger.debug("Enviando medida " + input.getFolioDocumento() + " a través de " + url);
            port.registrarMedidaAlterna(toSend);

        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch (mx.gob.segob.nsjp.ws.cliente.medidaalterna.NSJPNegocioException_Exception e) {
            logger.error(e.getMessage(), e);
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch(WebServiceException e){
        	logger.error(e.getMessage(), e);
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch(Exception e){
        	logger.error(e.getMessage(), e);
        	throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        }
    }

	@Override
	public void enviarMandamiento(MandamientoDTO mandamientoDTO)
			throws NSJPNegocioException {
		URL url;
		try {

			ConfInstitucion confInstitucion = confInsDao.read(Instituciones.PGJ
					.getValorId());

			if (mandamientoDTO == null
					|| mandamientoDTO.getResolutivo() == null
					|| mandamientoDTO.getResolutivo().getAudiencia() == null
					|| mandamientoDTO.getResolutivo().getAudiencia()
							.getExpediente() == null
					|| mandamientoDTO.getResolutivo().getAudiencia()
							.getExpediente().getCasoDTO() == null
					|| mandamientoDTO.getResolutivo().getAudiencia()
							.getExpediente().getCasoDTO()
							.getNumeroGeneralCaso() == null) {
				logger.error("NO EXISTE MANDAMIENTO DTO****");
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			if (confInstitucion == null || confInstitucion.getUrlInst() == null
					|| confInstitucion.getUrlInst().isEmpty()) {
				logger.error("NO EXISTE CONF INSTITUCION****");
				throw new NSJPNegocioException(CodigoError.FALLA_OPERACION_BD);
			}

			url = new URL(confInstitucion.getUrlInst()
					+ "/RegistrarMandamientoExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"RegistrarMandamientoExporterImplService");

			RegistrarMandamientoExporterImplService ss = new RegistrarMandamientoExporterImplService(
					url, SERVICE_NAME);

			RegistrarMandamientoExporter port = ss
					.getRegistrarMandamientoExporterImplPort();

			mx.gob.segob.nsjp.ws.cliente.mandamiento.MandamientoWSDTO toSend = MandamientoWSDTOTransformer
					.Transformar(mandamientoDTO);
			
			//Se agrega el numero de caso
			toSend.setNumeroCaso(mandamientoDTO.getResolutivo().getAudiencia()
					.getExpediente().getCasoDTO().getNumeroGeneralCaso());
			//Se envian en estatus de NO_ATENDIDOS
			toSend.setEstatus(EstatusMandamiento.NO_ATENDIDO.getValorId());
			
			logger.debug("Enviando mandamiento "
					+ mandamientoDTO.getFolioDocumento() + " a traves de "
					+ url);

			port.registrarMandamiento(toSend);

		}

		catch (javax.xml.ws.soap.SOAPFaultException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.mandamiento.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION);
		}
	}
}
