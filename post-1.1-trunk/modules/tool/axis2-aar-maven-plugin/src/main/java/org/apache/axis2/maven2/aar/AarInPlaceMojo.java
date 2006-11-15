package org.apache.axis2.maven2.aar;

/*
 * Copyright 2006 The Apache Software Foundation.
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

import org.apache.maven.plugin.MojoExecutionException;

/**
 * Generates aar in the source directory
 *
 * @goal inplace
 * @requiresDependencyResolution runtime
 */
public class AarInPlaceMojo 
  extends AbstractAarMojo
{

  public void execute()
    throws MojoExecutionException
  {

    getLog().debug("Generating aar in source directory... " + aarDirectory);
    buildExplodedAar();
  }
}
