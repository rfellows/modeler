/*
 * ******************************************************************************
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
 *
 * ******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.pentaho.agilebi.modeler.models.annotations;

import org.pentaho.agilebi.modeler.ModelerException;
import org.pentaho.agilebi.modeler.ModelerWorkspace;
import org.pentaho.agilebi.modeler.models.annotations.util.MondrianSchemaHandler;
import org.pentaho.metastore.api.IMetaStore;
import org.pentaho.metastore.persist.MetaStoreAttribute;
import org.pentaho.metastore.persist.MetaStoreElementType;
import org.w3c.dom.Document;

/**
 * Created by rfellows on 12/15/15.
 */
@MetaStoreElementType( name = "HideMeasure", description = "Hide Measure Annotation" )
public class HideLevel extends AnnotationType {
  public static final String NAME_ID = "name";
  public static final String NAME_NAME = "Measure Name";
  public static final int NAME_ORDER = 0;

  public static final String CUBE_ID = "cube";
  public static final String CUBE_NAME = "Cube Name";
  public static final int CUBE_ORDER = 1;

  public static final String DIMENSION_ID = "dimension";
  public static final String DIMENSION_NAME = "Dimension Name";
  public static final int DIMENSION_ORDER = 2;

  @MetaStoreAttribute
  @ModelProperty( id = NAME_ID, name = NAME_NAME, order = NAME_ORDER )
  protected String name;

  @MetaStoreAttribute
  @ModelProperty( id = CUBE_ID, name = CUBE_NAME, order = CUBE_ORDER, hideUI = true  )
  protected String cube;

  @MetaStoreAttribute
  @ModelProperty( id = DIMENSION_ID, name = DIMENSION_NAME, order = DIMENSION_ORDER )
  protected String dimension;

  @Override public boolean apply( ModelerWorkspace workspace, IMetaStore metaStore )
    throws ModelerException {
    return false;
  }

  @Override public boolean apply( Document schema ) throws ModelerException {
    MondrianSchemaHandler mondrianSchemaHandler = new MondrianSchemaHandler( schema );
    return mondrianSchemaHandler.hideLevel( getDimension(), getName() );
  }

  @Override public void validate() throws ModelerException {

  }

  @Override public ModelAnnotation.Type getType() {
    return ModelAnnotation.Type.HIDE_MEASURE;
  }

  @Override public String getSummary() {
    return null;
  }

  @Override public String getName() {
    return name;
  }

  @Override public String getField() {
    return null;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public String getCube() {
    return cube;
  }

  public void setCube( String cube ) {
    this.cube = cube;
  }

  public String getDimension() {
    return dimension;
  }

  public void setDimension( String dimension ) {
    this.dimension = dimension;
  }
}
