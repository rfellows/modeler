/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.agilebi.modeler.services;

import org.pentaho.agilebi.modeler.gwt.BogoPojo;
import org.pentaho.metadata.model.Domain;

/**
 * User: nbaker Date: Jun 18, 2010
 */
public interface IModelerService {
  Domain generateDomain( String connectionName, String tableName, String dbType, String query, String datasourceName )
    throws Exception;

  BogoPojo gwtWorkaround( BogoPojo pojo );

  String serializeModels( Domain domain, String name ) throws Exception;

  String serializeModels( Domain domain, String name, boolean doOlap ) throws Exception;

  Domain loadDomain( String id ) throws Exception;
}