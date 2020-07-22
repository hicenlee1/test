package com.meizu.javacc.mqsql;

import java.util.ArrayList;
import java.util.List;

import com.meizu.javacc.mqsql.QueryWhere;
import com.meizu.javacc.mqsql.parser.ParseException;
import com.meizu.javacc.mqsql.parser.QueryParser;


public class Query {
	public static enum WhereJoin {
		and, or
	}

	private List<Column> queryColumn = new ArrayList<Column>();

	public void setQueryColumn(List<Column> queryColumn) {
		this.queryColumn = queryColumn;
	}

	private List<QueryWhere> queryWheres = new ArrayList<QueryWhere>();

	public void setQueryWheres(List<QueryWhere> queryWheres) {
		this.queryWheres = queryWheres;
	}

	private WhereJoin whereJoin;

	public void setWhereJoin(WhereJoin whereJoin) {
		this.whereJoin = whereJoin;
	}

	public List<Column> getQueryColumn() {
		return queryColumn;
	}

	public List<QueryWhere> getQueryWheres() {
		return queryWheres;
	}

	public WhereJoin getWhereJoin() {
		return whereJoin;
	}

	public String toString() {
		return "queryColumn:" + queryColumn + "queryWheres: " + queryWheres + "whereJoin: " + whereJoin+"\n";
	}

	public static void main(String[] st) {
		com.meizu.javacc.mqsql.parser.QueryParser queryPaser = new com.meizu.javacc.mqsql.parser.QueryParser("select  md5(s_imei) as \"0d\", m2 as mm from mq ") ;
		try {
			Query query = queryPaser.parser();
	 
			
			System.out.println(query);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
