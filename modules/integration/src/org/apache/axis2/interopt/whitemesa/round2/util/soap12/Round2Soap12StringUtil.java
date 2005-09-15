package org.apache.axis2.interopt.whitemesa.round2.util;
       //org.apache.axis2.interopt.whitemesa.round2.util
import org.apache.axis2.soap.SOAPEnvelope;
import org.apache.axis2.soap.SOAPFactory;
import org.apache.axis2.soap.SOAPBody;
import org.apache.axis2.om.OMAbstractFactory;
import org.apache.axis2.om.OMElement;

/**
 * Created by IntelliJ IDEA.
 * User: Gayan
 * Date: Sep 5, 2005
 * Time: 3:20:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Round2Soap12StringUtil implements SunRound2ClientUtil{

    public SOAPEnvelope getEchoSoapEnvelope() {

            SOAPFactory omfactory = OMAbstractFactory.getSOAP12Factory();
            SOAPEnvelope reqEnv = omfactory.createSOAPEnvelope();
            //reqEnv.declareNamespace("http://schemas.xmlsoap.org/soap/envelope/", "soapenv");
            //reqEnv.declareNamespace("http://schemas.xmlsoap.org/wsdl/", "xmlns");
            reqEnv.declareNamespace("http://schemas.xmlsoap.org/wsdl/soap12/", "soap12");//xmlns:soap12="
            reqEnv.declareNamespace("http://www.w3.org/2001/XMLSchema", "xsd");
            reqEnv.declareNamespace("http://schemas.xmlsoap.org/soap/encoding/", "SOAP-ENC");
            reqEnv.declareNamespace("http://soapinterop.org/", "tns");
            reqEnv.declareNamespace("http://soapinterop.org/xsd", "s");
            //reqEnv.declareNamespace("http://schemas.xmlsoap.org/wsdl/", "wsdl");
            reqEnv.declareNamespace("http://www.w3.org/2001/XMLSchema-instance","xsi");


            OMElement operation = omfactory.createOMElement("echoString", "http://soapinterop.org/", null);
            SOAPBody body = omfactory.createSOAPBody(reqEnv);
            body.addChild(operation);
            operation.addAttribute("soapenv:encodingStyle", "http://www.w3.org/2003/05/soap-encoding", null);

            OMElement part = omfactory.createOMElement("inputString", "", null);
            part.addAttribute("xsi:type", "xsd:string", null);
            part.addChild(omfactory.createText("String Argument"));

            operation.addChild(part);
            //reqEnv.getBody().addChild(method);
            return reqEnv;

        }

//}   <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
//	<SOAP-ENV:Body>
//		<m:echoString xmlns:m="http://soapinterop.org/" SOAP-ENV:encodingStyle="http://www.w3.org/2003/05/soap-encoding">
//			<inputString xsi:type="xsd:string">String</inputString>
//		</m:echoString>
//	</SOAP-ENV:Body>
//</SOAP-ENV:Envelope>
}
