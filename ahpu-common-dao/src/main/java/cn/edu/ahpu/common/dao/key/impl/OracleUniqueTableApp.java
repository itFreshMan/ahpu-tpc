/*
 * Copyright 2010-2011 the original author or authors.
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

package cn.edu.ahpu.common.dao.key.impl;

import javax.sql.DataSource;

/**
 * 
 * @author libinsong1204@gmail.com
 * @version 1.0
 */
public class OracleUniqueTableApp extends DefaultUniqueTableApp {
	
	public OracleUniqueTableApp(DataSource dataSource) {
		super(dataSource);
		
		this.selectSQL = "SELECT code FROM TPC_PRIMARYKEY WHERE name = ? FOR UPDATE";
		this.updateSQL = "UPDATE TPC_PRIMARYKEY SET code = code + ? WHERE name = ? ";
		this.insertSQL = "INSERT INTO TPC_PRIMARYKEY (code, name) VALUES (?, ?)";
	}
}