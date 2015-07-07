package mx.gob.segob.nsjp.ws.cliente.mandamiento;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2013-08-16T13:45:44.285-05:00
 * Generated source version: 2.4.2
 * 
 */
@WebService(targetNamespace = "http://ws.service.nsjp.segob.gob.mx/", name = "RegistrarMandamientoExporter")
@XmlSeeAlso({ObjectFactory.class})
public interface RegistrarMandamientoExporter {

    @Action(input = "http://ws.service.nsjp.segob.gob.mx/RegistrarMandamientoExporter/registrarMandamientoRequest", output = "http://ws.service.nsjp.segob.gob.mx/RegistrarMandamientoExporter/registrarMandamientoResponse", fault = {@FaultAction(className = NSJPNegocioException_Exception.class, value = "http://ws.service.nsjp.segob.gob.mx/RegistrarMandamientoExporter/registrarMandamiento/Fault/NSJPNegocioException")})
    @RequestWrapper(localName = "registrarMandamiento", targetNamespace = "http://ws.service.nsjp.segob.gob.mx/", className = "mx.gob.segob.nsjp.ws.cliente.mandamiento.RegistrarMandamiento")
    @WebMethod
    @ResponseWrapper(localName = "registrarMandamientoResponse", targetNamespace = "http://ws.service.nsjp.segob.gob.mx/", className = "mx.gob.segob.nsjp.ws.cliente.mandamiento.RegistrarMandamientoResponse")
    public void registrarMandamiento(
        @WebParam(name = "arg0", targetNamespace = "")
        mx.gob.segob.nsjp.ws.cliente.mandamiento.MandamientoWSDTO arg0
    ) throws NSJPNegocioException_Exception;
}
