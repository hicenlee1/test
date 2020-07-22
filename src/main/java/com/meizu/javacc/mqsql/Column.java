package com.meizu.javacc.mqsql;


import org.apache.commons.lang3.StringUtils;

public class Column {

	private final static String EXP = "\"|`";
	// 列名称
	private String columnName;
	private String mcolName;
	private String aliName;
	// 列函数
	private String func = "";

	public String getAliName() {
		return aliName;
	}

	public void setAliName(String aliName) {
		this.aliName = escaped(aliName);
	}

	public String getMcolName() {
		return mcolName;
	}

	public void setMcolName(String mcolName) {
		this.mcolName = mcolName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String toString() {
		return "columnName: " + columnName + " func:" + func + " mcolName: " + mcolName + " aliName:" + aliName + " \n";
	}

	protected String escaped(String key) {
		if (StringUtils.isEmpty(key)) {
			return key;
		}
		return key.replaceAll(EXP, StringUtils.EMPTY);
	}
}
