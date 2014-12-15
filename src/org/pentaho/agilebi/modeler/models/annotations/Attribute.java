/*!
 * PENTAHO CORPORATION PROPRIETARY AND CONFIDENTIAL
 *
 * Copyright 2002 - 2014 Pentaho Corporation (Pentaho). All rights reserved.
 *
 * NOTICE: All information including source code contained herein is, and
 * remains the sole property of Pentaho and its licensors. The intellectual
 * and technical concepts contained herein are proprietary and confidential
 * to, and are trade secrets of Pentaho and may be covered by U.S. and foreign
 * patents, or patents in process, and are protected by trade secret and
 * copyright laws. The receipt or possession of this source code and/or related
 * information does not convey or imply any rights to reproduce, disclose or
 * distribute its contents, or to manufacture, use, or sell anything that it
 * may describe, in whole or in part. Any reproduction, modification, distribution,
 * or public display of this information without the express written authorization
 * from Pentaho is strictly prohibited and in violation of applicable laws and
 * international treaties. Access to the source code contained herein is strictly
 * prohibited to anyone except those individuals and entities who have executed
 * confidentiality and non-disclosure agreements or other agreements with Pentaho,
 * explicitly covering such access.
 */

package org.pentaho.agilebi.modeler.models.annotations;

import org.pentaho.agilebi.modeler.ModelerWorkspace;
import org.pentaho.agilebi.modeler.geo.GeoRole;

/**
 * @author Rowell Belen
 */
public class Attribute extends AnnotationType {

  private static final long serialVersionUID = 5169827225345800226L;

  @ModelProperty( id = "levelType", name = "Level Type" )
  private ModelAnnotation.LevelType levelType;

  @ModelProperty( id = "attributeType", name = "Attribute Type" )
  private ModelAnnotation.AttributeType attributeType;

  @ModelProperty( id = "formatString", name = "Format String" )
  private String formatString;

  @ModelProperty( id = "geoRole", name = "Geo Role" )
  private GeoRole geoRole;

  public ModelAnnotation.LevelType getLevelType() {
    return levelType;
  }

  public void setLevelType( ModelAnnotation.LevelType levelType ) {
    this.levelType = levelType;
  }

  public ModelAnnotation.AttributeType getAttributeType() {
    return attributeType;
  }

  public void setAttributeType( ModelAnnotation.AttributeType attributeType ) {
    this.attributeType = attributeType;
  }

  public String getFormatString() {
    return formatString;
  }

  public void setFormatString( String formatString ) {
    this.formatString = formatString;
  }

  public GeoRole getGeoRole() {
    return geoRole;
  }

  public void setGeoRole( GeoRole geoRole ) {
    this.geoRole = geoRole;
  }

  @Override public void apply( final ModelerWorkspace workspace, final String column ) {
    throw new UnsupportedOperationException();
  }

  @Override
  public AnnotationSubType getType() {
    return AnnotationSubType.ATTRIBUTE;
  }
}
