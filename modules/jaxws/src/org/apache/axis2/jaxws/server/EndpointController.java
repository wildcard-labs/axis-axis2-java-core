/*
 * Copyright 2004,2005 The Apache Software Foundation.
 * Copyright 2006 International Business Machines Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.axis2.jaxws.server;

import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.Parameter;
import org.apache.axis2.jaxws.ExceptionFactory;
import org.apache.axis2.jaxws.core.InvocationContext;
import org.apache.axis2.jaxws.core.MessageContext;
import org.apache.axis2.jaxws.description.DescriptionFactory;
import org.apache.axis2.jaxws.description.EndpointDescription;
import org.apache.axis2.jaxws.description.ServiceDescription;
import org.apache.axis2.jaxws.i18n.Messages;
import org.apache.axis2.jaxws.registry.FactoryRegistry;
import org.apache.axis2.jaxws.server.dispatcher.EndpointDispatcher;
import org.apache.axis2.jaxws.server.dispatcher.factory.EndpointDispatcherFactory;
import org.apache.axis2.jaxws.server.endpoint.lifecycle.EndpointLifecycleManager;
import org.apache.axis2.jaxws.server.endpoint.lifecycle.factory.EndpointLifecycleManagerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * The EndpointController is the server side equivalent to the
 * InvocationController on the client side.  It is an abstraction of the server
 * side endpoint invocation that encapsulates all of the Axis2 semantics.
 * 
 * Like the InvocationController, this class is responsible for invoking the 
 * JAX-WS application handler chain along with taking all of the provided 
 * information and setting up what's needed to perform the actual invocation 
 * of the endpoint.
 *
 */
public class EndpointController {
    
    private static final Log log = LogFactory.getLog(EndpointController.class);
	private static final String PARAM_SERVICE_CLASS = "ServiceClass";
    public EndpointController() {}

    /**
     * This method is used to start the JAX-WS invocation of a target endpoint.
     * It takes an InvocationContext, which must have a MessageContext specied
     * for the request.  Once the invocation is complete, the information will
     * be stored  
     */
    public InvocationContext invoke(InvocationContext ic) {
        MessageContext requestMsgCtx = ic.getRequestMessageContext();

        String implClassName = getServiceImplClassName(requestMsgCtx);
            
        Class implClass = loadServiceImplClass(implClassName, 
                 requestMsgCtx.getClassLoader());
            
        ServiceDescription serviceDesc = getServiceDescription(requestMsgCtx, implClass);
        requestMsgCtx.setServiceDescription(serviceDesc);

		MessageContext responseMsgContext = null;
		
		try {
			EndpointLifecycleManager elm = createEndpointlifecycleManager();
			Object serviceInstance = elm.createServiceInstance(requestMsgCtx, implClass);
			EndpointDispatcher dispatcher = getEndpointDispatcher(implClass, serviceInstance);
            
			responseMsgContext = dispatcher.invoke(requestMsgCtx);
        } catch (Exception e) {
            // TODO for now, throw it.  We probably should try to make an XMLFault object and set it on the message
            throw ExceptionFactory.makeWebServiceException(e);
        }

		// The response MessageContext should be set on the InvocationContext
		ic.setResponseMessageContext(responseMsgContext);

        return ic;
    }
    
    /*
	 * Get the appropriate EndpointDispatcher for a given service endpoint.
	 */
	private EndpointDispatcher getEndpointDispatcher(Class serviceImplClass, Object serviceInstance) 
        throws Exception {
        return EndpointDispatcherFactory.createEndpointDispatcher(serviceImplClass, serviceInstance);
    }
	
	/*
     * Tries to load the implementation class that was specified for the
     * target endpoint
	 */
	private Class loadServiceImplClass(String className, ClassLoader cl) {
        if (log.isDebugEnabled()) {
            log.debug("Attempting to load service impl class: " + className);
        }    
        
        try {
		    //TODO: What should be done if the supplied ClassLoader is null?
            Class _class = Class.forName(className, true, cl);
            return _class;
		} catch(ClassNotFoundException cnf ){
			throw ExceptionFactory.makeWebServiceException(Messages.getMessage(
                    "EndpointControllerErr4", className));
		}
	}
    
    private String getServiceImplClassName(MessageContext mc) {
        // The PARAM_SERVICE_CLASS property that is set on the AxisService
        // will tell us what the service implementation class is.
        org.apache.axis2.context.MessageContext axisMsgContext = mc.getAxisMessageContext();
        AxisService as = axisMsgContext.getAxisService();
        Parameter param = as.getParameter(PARAM_SERVICE_CLASS);
        
        // If there was no implementation class, we should not go any further
        if (param == null) {
            throw ExceptionFactory.makeWebServiceException(Messages.getMessage(
                    "EndpointControllerErr2"));
        }
        
        String className = ((String) param.getValue()).trim();
        return className;
    }
    
    /*
     * Gets the ServiceDescription associated with the request that is currently
     * being processed. 
     */
    private ServiceDescription getServiceDescription(MessageContext mc, Class implClass) {
        AxisService axisSvc = mc.getAxisMessageContext().getAxisService();
        
        //Check to see if we've already created a ServiceDescription for this
        //service before trying to create a new one. 
        
        if (axisSvc.getParameter(EndpointDescription.AXIS_SERVICE_PARAMETER) != null) {
            Parameter param = axisSvc.getParameter(EndpointDescription.AXIS_SERVICE_PARAMETER);
            
            ServiceDescription sd = ((EndpointDescription) param.getValue()).getServiceDescription();
            return sd;
        }
        else {
            ServiceDescription sd = DescriptionFactory.
                createServiceDescriptionFromServiceImpl(implClass, axisSvc);
            return sd;
        }
    }
    
   private EndpointLifecycleManager createEndpointlifecycleManager(){
	  EndpointLifecycleManagerFactory elmf =(EndpointLifecycleManagerFactory)FactoryRegistry.getFactory(EndpointLifecycleManagerFactory.class);
	  return elmf.createEndpointLifecycleManager();
   }
  
}
