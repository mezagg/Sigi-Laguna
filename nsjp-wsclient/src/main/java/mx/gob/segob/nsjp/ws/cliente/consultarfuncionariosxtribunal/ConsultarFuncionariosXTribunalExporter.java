package mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2013-08-16T13:45:18.234-05:00
 * Generated source version: 2.4.2
 * 
 */
@WebService(targetNamespace = "http://ws.service.nsjp.segob.gob.mx/", name = "ConsultarFuncionariosXTribunalExporter")
@XmlSeeAlso({ObjectFactory.class})
public interface ConsultarFuncionariosXTribunalExporter {

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://ws.service.nsjp.segob.gob.mx/ConsultarFuncionariosXTribunalExporter/consultarFuncionariosXTribunalRequest", output = "http://ws.service.nsjp.segob.gob.mx/ConsultarFuncionariosXTribunalExporter/consultarFuncionariosXTribunalResponse", fault = {@FaultAction(className = NSJPNegocioException_Exception.class, value = "http://ws.service.nsjp.segob.gob.mx/ConsultarFuncionariosXTribunalExporter/consultarFuncionariosXTribunal/Fault/NSJPNegocioException")})
    @RequestWrapper(localName = "consultarFuncionariosXTribunal", targetNamespace = "http://ws.service.nsjp.segob.gob.mx/", className = "mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal.ConsultarFuncionariosXTribunal")
    @WebMethod
    @ResponseWrapper(localName = "consultarFuncionariosXTribunalResponse", targetNamespace = "http://ws.service.nsjp.segob.gob.mx/", className = "mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal.ConsultarFuncionariosXTribunalResponse")
    public java.util.List<mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal.FuncionarioWSDTO> consultarFuncionariosXTribunal(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.Long arg0
    ) throws NSJPNegocioException_Exception;
}
