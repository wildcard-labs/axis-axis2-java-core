
package org.apache.axis2.jaxws.polymorphic.shape.sei;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.test.shape.Shape;

/**
 * This class was generated by the JAXWS SI.
 * JAX-WS RI 2.0_01-b15-fcs
 * Generated source version: 2.0
 * 
 */
@WebService(name = "PolymorphicShapePortType", targetNamespace = "http://sei.shape.polymorphic.jaxws.axis2.apache.org")
public interface PolymorphicShapePortType {


    /**
     * 
     * @param request
     * @return
     *     returns org.test.shape.Shape
     */
    @WebMethod(action = "http://sei.polymorphicshape.jaxws.axis2.apache.org/typesExtension")
    @WebResult(name = "response", targetNamespace = "")
    @RequestWrapper(localName = "draw", targetNamespace = "http://wrapper.shape.test.org", className = "org.test.shape.wrapper.Draw")
    @ResponseWrapper(localName = "drawResponse", targetNamespace = "http://wrapper.shape.test.org", className = "org.test.shape.wrapper.DrawResponse")
    public Shape draw(
        @WebParam(name = "request", targetNamespace = "")
        Shape request);

    /**
     * 
     * @param request
     * @return
     *     returns org.test.shape.Shape
     */
    @WebMethod(action = "http://sei.polymorphicshape.jaxws.axis2.apache.org/typesExtension")
    @WebResult(name = "response", targetNamespace = "")
    @RequestWrapper(localName = "draw3D", targetNamespace = "http://wrapper.shape.test.org", className = "org.test.shape.wrapper.Draw3D")
    @ResponseWrapper(localName = "drawResponse", targetNamespace = "http://wrapper.shape.test.org", className = "org.test.shape.wrapper.DrawResponse")
    public Shape draw3D(
        @WebParam(name = "request", targetNamespace = "")
        Shape request);

}