package com.meizu.javacc.mqsql;

public class QueryWhere {
	public static enum Etype {
		equals, notequals, in, notin,
		// 大于
		gt,
		// 大于等于
		egt,
		// 小于
		lt,
		// 小于等于
		elt, plike
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Etype getEtype() {
		return etype;
	}

	public void setEtype(Etype etype) {
		this.etype = etype;
	}

	private String name;
	private String subName;
	private String fucName;

	public String getFucName() {
		return fucName;
	}

	public void setFucName(String fucName) {
		this.fucName = fucName;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	private Object value;
	private Etype etype;

	public String toString() {
		return "name: " + name + " value: " + value + " etype: " + etype + " subName:" + subName + " fucName: " + fucName
				+ " \n";
	}

}
