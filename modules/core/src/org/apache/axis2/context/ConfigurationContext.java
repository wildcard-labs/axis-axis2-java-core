package org.apache.axis2.context;

/*
 * Copyright 2004,2005 The Apache Software Foundation.
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
 *
 *  Runtime state of the engine
 */

import org.apache.axis2.AxisFault;
import org.apache.axis2.description.ServiceDescription;
import org.apache.axis2.engine.AxisConfiguration;
import org.apache.axis2.storage.AxisStorage;
import org.apache.axis2.util.threadpool.ThreadPool;

import javax.xml.namespace.QName;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the biggest memeber of the Axis2 information hierachy, and if this is serialized completly
 * the whole Axis2 is saved to the disc.  
 */

public class ConfigurationContext extends AbstractContext {

    private AxisConfiguration axisConfiguration;
    private AxisStorage storage;

    private Map sessionContextMap;
    private Map moduleContextMap;
    private ThreadPool threadPool;
    private File rootDir;

    /**
     * Map containing <code>MessageID</code> to
     * <code>OperationContext</code> mapping.
     */
    private final Map operationContextMap = new HashMap();

    private final Map serviceContextMap;

    public ConfigurationContext(AxisConfiguration registry) {
        super(null);
        this.axisConfiguration = registry;
        serviceContextMap = new HashMap();
        moduleContextMap = new HashMap();
        sessionContextMap = new HashMap();
    }

    /**
     * The method is used to do the intialization of the EngineContext
     * @throws AxisFault
     */

    public void init() throws AxisFault {

    }

    public synchronized void removeService(QName name) {
        serviceContextMap.remove(name);
    }

    /**
     * @return
     */
    public AxisConfiguration getAxisConfiguration() {
        return axisConfiguration;
    }

    /**
     * @param configuration
     */
    public void setAxisConfiguration(AxisConfiguration configuration) {
        axisConfiguration = configuration;
    }
    
    /**
     * Register a OperationContext agienst a given Message ID.
     * @param messageID
     * @param mepContext
     */
    public synchronized void registerOperationContext(
        String messageID,
        OperationContext mepContext) {
        this.operationContextMap.put(messageID, mepContext);
    }
    /**
     * get a OperationContext given a Message ID
     * @param messageID
     * @return
     */
    public OperationContext getOperationContext(String messageID) {
        return (OperationContext) this.operationContextMap.get(messageID);
    }

    public Map getOperationContextMap() {
        return this.operationContextMap;
    }
    
    /**
     * Register a ServiceContext agienst a given Message ID.
     */
    public synchronized void registerServiceContext(
        String serviceInstanceID,
        ServiceContext serviceContext) {
        this.serviceContextMap.put(serviceInstanceID, serviceContext);
    }

    /**
     * get the ServiceContext given a id
     * @param serviceInstanceID
     * @return
     */
    public ServiceContext getServiceContext(String serviceInstanceID) {
        return (ServiceContext) this.serviceContextMap.get(serviceInstanceID);
    }

    public AxisStorage getStorage() {
        return storage;
    }

    public void setStorage(AxisStorage storage) {
        this.storage = storage;
    }

    public ServiceContext createServiceContext(QName serviceName)
        throws AxisFault {
        ServiceDescription service = axisConfiguration.getService(serviceName);
        if (service != null) {
            return new ServiceContext(service, this);
        } else {
            throw new AxisFault(
                "Service not found service name = " + serviceName);
        }
    }

    /**
     * @return the Gloal ThradPool
     */
    public ThreadPool getThreadPool() {
        if (threadPool == null) {
            threadPool = new ThreadPool();
        }
        return threadPool;
    }
    /**
     * This method allows users to reolve the paths relative to the 
     * root diretory
     * @param path
     * @return
     */
    public File getRealPath(String path) {
        if (rootDir == null) {
            return new File(path);
        } else {
            return new File(rootDir, path);
        }
    }

    /**
     * @param file
     */
    public void setRootDir(File file) {
        rootDir = file;
    }

}
