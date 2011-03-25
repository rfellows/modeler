/*
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
 * Copyright (c) 2011 Pentaho Corporation..  All rights reserved.
 * 
 * @author Ezequiel Cuellar
 */

package org.pentaho.agilebi.modeler.util;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.agilebi.modeler.ModelerException;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.metadata.automodel.SchemaTable;
import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.model.LogicalColumn;
import org.pentaho.metadata.model.LogicalModel;
import org.pentaho.metadata.model.LogicalRelationship;
import org.pentaho.metadata.model.LogicalTable;
import org.pentaho.metadata.model.concept.types.LocalizedString;
import org.pentaho.metadata.model.concept.types.RelationshipType;
import org.pentaho.pms.core.exception.PentahoMetadataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiTableModelerSource implements ISpoonModelerSource {

	private ModelGenerator generator;
	private DatabaseMeta databaseMeta;
	private List<LogicalRelationship> joinTemplates;
	public static final String SOURCE_TYPE = MultiTableModelerSource.class.getSimpleName();
	private static Logger logger = LoggerFactory.getLogger(MultiTableModelerSource.class);

	public MultiTableModelerSource(DatabaseMeta databaseMeta, List<LogicalRelationship> joinTemplates) {
		this.databaseMeta = databaseMeta;
		this.joinTemplates = joinTemplates;
		this.generator = new ModelGenerator();
	}

	@Override
	public Domain generateDomain() throws ModelerException {
		return this.generateDomain(this.databaseMeta, this.joinTemplates);
	}

	@Override
	public String getDatabaseName() {
		String name = null;
		if (this.databaseMeta != null) {
			name = this.databaseMeta.getDatabaseName();
		}
		return name;
	}

	@Override
	public void initialize(Domain domain) throws ModelerException {
	}

	@Override
	public void serializeIntoDomain(Domain d) {
		LogicalModel lm = d.getLogicalModels().get(0);
		lm.setProperty("source_type", SOURCE_TYPE);
	}

	@Override
	public String getSchemaName() {
		return null;
	}

	@Override
	public String getTableName() {
		return null;
	}

	@Override
	public DatabaseMeta getDatabaseMeta() {
		return this.databaseMeta;
	}

	public Domain generateDomain(DatabaseMeta databaseMeta, List<LogicalRelationship> joinTemplates) {

		Domain domain = null;

		try {

			// Generate domain based on the table names.

			String locale = LocalizedString.DEFAULT_LOCALE;
			this.generator.setLocale(locale);
			this.generator.setDatabaseMeta(databaseMeta);
			this.generator.setModelName(databaseMeta.getName());

			List<SchemaTable> schemas = new ArrayList<SchemaTable>();
			for (LogicalRelationship joinTemplate : joinTemplates) {
				schemas.add(new SchemaTable("", joinTemplate.getFromTable().getName(locale)));
				schemas.add(new SchemaTable("", joinTemplate.getToTable().getName(locale)));
			}

			SchemaTable tableNames[] = new SchemaTable[schemas.size()];
			tableNames = schemas.toArray(tableNames);
			this.generator.setTableNames(tableNames);
			domain = this.generator.generateDomain();
			domain.setId(databaseMeta.getName());

			// Create and add LogicalRelationships to the LogicalModel from the
			// domain.

			LogicalModel logicalModel = domain.getLogicalModels().get(0);
			for (LogicalRelationship joinTemplate : joinTemplates) {

				String lTable = joinTemplate.getFromTable().getName(locale);
				String rTable = joinTemplate.getToTable().getName(locale);

				LogicalTable fromTable = null;
				LogicalColumn fromColumn = null;
				LogicalTable toTable = null;
				LogicalColumn toColumn = null;

				for (LogicalTable logicalTable : logicalModel.getLogicalTables()) {
					if (logicalTable.getName(locale).equals(lTable)) {
						fromTable = logicalTable;

						for (LogicalColumn logicalColumn : fromTable.getLogicalColumns()) {
							if (logicalColumn.getName(locale).equals(joinTemplate.getFromColumn().getName(locale))) {
								fromColumn = logicalColumn;
							}
						}
					}
					if (logicalTable.getName(locale).equals(rTable)) {
						toTable = logicalTable;

						for (LogicalColumn logicalColumn : toTable.getLogicalColumns()) {
							if (logicalColumn.getName(locale).equals(joinTemplate.getToColumn().getName(locale))) {
								toColumn = logicalColumn;
							}
						}
					}
				}

				LogicalRelationship logicalRelationship = new LogicalRelationship();
				// TODO is this INNER JOIN?
				logicalRelationship.setRelationshipType(RelationshipType._1_1);
				logicalRelationship.setFromTable(fromTable);
				logicalRelationship.setFromColumn(fromColumn);
				logicalRelationship.setToTable(toTable);
				logicalRelationship.setToColumn(toColumn);
				logicalModel.addLogicalRelationship(logicalRelationship);
			}
		} catch (PentahoMetadataException e) {
			e.printStackTrace();
			logger.info(e.getLocalizedMessage());
		}
		return domain;
	}
}