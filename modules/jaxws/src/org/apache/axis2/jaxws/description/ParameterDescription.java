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


package org.apache.axis2.jaxws.description;
import javax.jws.WebParam;

public interface ParameterDescription {
// TODO: (JLB) Fix this to not be the WebParam mode
//    public enum Mode{IN, OUT, INOUT};
    public OperationDescription getOperationDescription();
    
    public String getParameterName();
    public String getTargetNamespace();
    public String getPartName();
    
    public boolean isHolderType();
    public Class getParameterType();
    public Class getParameterActualType();
    
    public boolean isHeader();
//  TODO: (JLB) Fix this to not be the WebParam mode
    public WebParam.Mode getMode();

    
}