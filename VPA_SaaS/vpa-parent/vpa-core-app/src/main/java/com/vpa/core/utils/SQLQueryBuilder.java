/*************************************************************************************************************
 ** "(c) 2014-2015 Vantage Point Analytics, Inc.  
 ** All rights reserved.  This software program is the confidential and proprietary information of Vantage Point Analytics 
 ** and may not be used, reproduced, modified or distributed without the express written consent of Vantage Point Analytics."
 **
 ** Module      :   VPA-SaaS
 ** File        :   SQLQueryBuilder.java
 ** Version     :   1.0
 ** Description :   Utility class to build SQL query dynamically based on passed arguments. This class based on Builder design patter.
 **                  This class may not support all the complex query. You may add more functionality
 **                 to it. For example about how to use this class take a look at SQLBuilderTest JUnit. If you want to use this class to generate query then
 **                 it is recommended to write JUnit for the SQL you want and add this JUnit is SQLBuilderTest. It is also preferred and recommended to use Criteria Query 
 **                 of JPA if you can.
 **                   
 **
 ** Author      :   Rajesh Parab 
 ** Created Date :  Thursday, 17 Sep 2015
 **************************************************************************************************************
 ** Change History Header:
 **************************************************************************************************************
 ** Date Author     Version Description:
 ** -------- --------   -------- -------------
 **  
 **************************************************************************************************************/
package com.vpa.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class SQLQueryBuilder {

	private List<String> columns = new ArrayList<String>();

	private List<String> tables = new ArrayList<String>();

	private List<String> joins = new ArrayList<String>();

	private List<String> leftJoins = new ArrayList<String>();

	private List<String> wheres = new ArrayList<String>();

	private List<String> orderBys = new ArrayList<String>();

	private List<String> groupBys = new ArrayList<String>();

	private List<String> havings = new ArrayList<String>();

	public SQLQueryBuilder() {

	}

	public SQLQueryBuilder(String table) {
		tables.add(table);
	}

	private void appendList(StringBuilder sql, List<String> list, String init,
			String sep) {
		boolean first = true;
		for (String s : list) {
			if (first) {
				sql.append(init);
			} else {
				sql.append(sep);
			}
			sql.append(s);
			first = false;
		}
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public SQLQueryBuilder column(String name) {
		columns.add(name);
		return this;
	}

	/**
	 * 
	 * @param name
	 * @param groupBy
	 * @return
	 */
	public SQLQueryBuilder column(String name, boolean groupBy) {
		columns.add(name);
		if (groupBy) {
			groupBys.add(name);
		}
		return this;
	}

	/**
	 * 
	 * @param table
	 * @return
	 */
	public SQLQueryBuilder from(String table) {
		tables.add(table);

		return this;
	}

	/**
	 * 
	 * @param table
	 * @param value
	 * @return
	 */
	public SQLQueryBuilder from(String table, String value) {
		if (StringUtils.isNotBlank(value)) {
			tables.add(table);
		}

		return this;
	}

	/**
	 * 
	 * @param expr
	 * @return
	 */
	public SQLQueryBuilder groupBy(String expr) {
		groupBys.add(expr);
		return this;
	}

	/**
	 * 
	 * @param expr
	 * @return
	 */
	public SQLQueryBuilder having(String expr) {
		havings.add(expr);
		return this;
	}

	/**
	 * 
	 * @param join
	 * @return
	 */
	public SQLQueryBuilder join(String join) {
		joins.add(join);
		return this;
	}

	/**
	 * 
	 * @param join
	 * @return
	 */
	public SQLQueryBuilder leftJoin(String join) {
		leftJoins.add(join);
		return this;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public SQLQueryBuilder orderBy(String name) {
		orderBys.add(name);
		return this;
	}

	@Override
	public String toString() {

		StringBuilder sql = new StringBuilder("select ");

		if (columns.size() == 0) {
			sql.append("*");
		} else {
			appendList(sql, columns, "", ", ");
		}

		appendList(sql, tables, " from ", ", ");
		appendList(sql, joins, " join ", " join ");
		appendList(sql, leftJoins, " left join ", " left join ");
		appendList(sql, wheres, " where ", " and ");
		appendList(sql, groupBys, " group by ", ", ");
		appendList(sql, havings, " having ", " and ");
		appendList(sql, orderBys, " order by ", ", ");

		return sql.toString();
	}

	/**
	 * 
	 * @param expr
	 * @return
	 */
	public SQLQueryBuilder where(String expr) {

		wheres.add(expr);

		return this;
	}

	/**
	 * 
	 * @param expr
	 * @param value
	 * @return
	 */
	public SQLQueryBuilder where(String expr, String value) {
		if (StringUtils.isNotBlank(value)) {
			wheres.add(expr+"'"+value+"'");
		}

		return this;
	}
	public SQLQueryBuilder where(String expr, long value) {
		if (value>0) {
			wheres.add(expr+value);
		}

		return this;
	}
}
