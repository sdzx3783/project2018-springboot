package com.hotent.core.mybatis.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

public class PropertiesHelper {
	public static final int SYSTEM_PROPERTIES_MODE_NEVER = 0;
	public static final int SYSTEM_PROPERTIES_MODE_FALLBACK = 1;
	public static final int SYSTEM_PROPERTIES_MODE_OVERRIDE = 2;
	private int systemPropertiesMode = 0;
	private Properties p;

	public PropertiesHelper(Properties p) {
		this.setProperties(p);
	}

	public PropertiesHelper(Properties p, int systemPropertiesMode) {
		this.setProperties(p);
		if (systemPropertiesMode != 0 && systemPropertiesMode != 1 && systemPropertiesMode != 2) {
			throw new IllegalArgumentException("error systemPropertiesMode mode:" + systemPropertiesMode);
		} else {
			this.systemPropertiesMode = systemPropertiesMode;
		}
	}

	public Properties getProperties() {
		return this.p;
	}

	public void setProperties(Properties props) {
		if (props == null) {
			throw new IllegalArgumentException("properties must be not null");
		} else {
			this.p = props;
		}
	}

	public String getRequiredString(String key) {
		String value = this.getProperty(key);
		if (isBlankString(value)) {
			throw new IllegalStateException("required property is blank by key=" + key);
		} else {
			return value;
		}
	}

	public String getNullIfBlank(String key) {
		String value = this.getProperty(key);
		return isBlankString(value) ? null : value;
	}

	public String getNullIfEmpty(String key) {
		String value = this.getProperty(key);
		return value != null && !"".equals(value) ? value : null;
	}

	public String getAndTryFromSystem(String key) {
		String value = this.getProperty(key);
		if (isBlankString(value)) {
			value = this.getSystemProperty(key);
		}

		return value;
	}

	private String getSystemProperty(String key) {
		String value = System.getProperty(key);
		if (isBlankString(value)) {
			value = System.getenv(key);
		}

		return value;
	}

	public Integer getInteger(String key) {
		String v = this.getProperty(key);
		return v == null ? null : Integer.valueOf(Integer.parseInt(v));
	}

	public int getInt(String key, int defaultValue) {
		return this.getProperty(key) == null ? defaultValue : Integer.parseInt(this.getRequiredString(key));
	}

	public int getRequiredInt(String key) {
		return Integer.parseInt(this.getRequiredString(key));
	}

	public Long getLong(String key) {
		return this.getProperty(key) == null ? null : Long.valueOf(Long.parseLong(this.getRequiredString(key)));
	}

	public long getLong(String key, long defaultValue) {
		return this.getProperty(key) == null ? defaultValue : Long.parseLong(this.getRequiredString(key));
	}

	public Long getRequiredLong(String key) {
		return Long.valueOf(Long.parseLong(this.getRequiredString(key)));
	}

	public Boolean getBoolean(String key) {
		return this.getProperty(key) == null
				? null
				: Boolean.valueOf(Boolean.parseBoolean(this.getRequiredString(key)));
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return this.getProperty(key) == null ? defaultValue : Boolean.parseBoolean(this.getRequiredString(key));
	}

	public boolean getRequiredBoolean(String key) {
		return Boolean.parseBoolean(this.getRequiredString(key));
	}

	public Float getFloat(String key) {
		return this.getProperty(key) == null ? null : Float.valueOf(Float.parseFloat(this.getRequiredString(key)));
	}

	public float getFloat(String key, float defaultValue) {
		return this.getProperty(key) == null ? defaultValue : Float.parseFloat(this.getRequiredString(key));
	}

	public Float getRequiredFloat(String key) {
		return Float.valueOf(Float.parseFloat(this.getRequiredString(key)));
	}

	public Double getDouble(String key) {
		return this.getProperty(key) == null ? null : Double.valueOf(Double.parseDouble(this.getRequiredString(key)));
	}

	public double getDouble(String key, double defaultValue) {
		return this.getProperty(key) == null ? defaultValue : Double.parseDouble(this.getRequiredString(key));
	}

	public Double getRequiredDouble(String key) {
		return Double.valueOf(Double.parseDouble(this.getRequiredString(key)));
	}

	public Object setProperty(String key, int value) {
		return this.setProperty(key, String.valueOf(value));
	}

	public Object setProperty(String key, long value) {
		return this.setProperty(key, String.valueOf(value));
	}

	public Object setProperty(String key, float value) {
		return this.setProperty(key, String.valueOf(value));
	}

	public Object setProperty(String key, double value) {
		return this.setProperty(key, String.valueOf(value));
	}

	public Object setProperty(String key, boolean value) {
		return this.setProperty(key, String.valueOf(value));
	}

	public String[] getStringArray(String key) {
		String v = this.getProperty(key);
		return v == null ? new String[0] : tokenizeToStringArray(v, ", \t\n\r\f");
	}

	public int[] getIntArray(String key) {
		return toIntArray(this.getStringArray(key));
	}

	public Properties getStartsWithProperties(String prefix) {
		if (prefix == null) {
			throw new IllegalArgumentException("\'prefix\' must be not null");
		} else {
			Properties props = this.getProperties();
			Properties result = new Properties();
			Iterator i$ = props.entrySet().iterator();

			while (i$.hasNext()) {
				Entry entry = (Entry) i$.next();
				String key = (String) entry.getKey();
				if (key != null && key.startsWith(prefix)) {
					result.put(key.substring(prefix.length()), entry.getValue());
				}
			}

			return result;
		}
	}

	public String getProperty(String key, String defaultValue) {
		String value = this.getProperty(key);
		return isBlankString(value) ? defaultValue : value;
	}

	public String getProperty(String key) {
		String propVal = null;
		if (this.systemPropertiesMode == 2) {
			propVal = this.getSystemProperty(key);
		}

		if (propVal == null) {
			propVal = this.p.getProperty(key);
		}

		if (propVal == null && this.systemPropertiesMode == 1) {
			propVal = this.getSystemProperty(key);
		}

		return propVal;
	}

	public Object setProperty(String key, String value) {
		return this.p.setProperty(key, value);
	}

	public void clear() {
		this.p.clear();
	}

	public Set<Entry<Object, Object>> entrySet() {
		return this.p.entrySet();
	}

	public Enumeration<?> propertyNames() {
		return this.p.propertyNames();
	}

	public boolean contains(Object value) {
		return this.p.contains(value);
	}

	public boolean containsKey(Object key) {
		return this.p.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.p.containsValue(value);
	}

	public Enumeration<Object> elements() {
		return this.p.elements();
	}

	public Object get(Object key) {
		return this.p.get(key);
	}

	public boolean isEmpty() {
		return this.p.isEmpty();
	}

	public Enumeration<Object> keys() {
		return this.p.keys();
	}

	public Set<Object> keySet() {
		return this.p.keySet();
	}

	public void list(PrintStream out) {
		this.p.list(out);
	}

	public void list(PrintWriter out) {
		this.p.list(out);
	}

	public void load(InputStream inStream) throws IOException {
		this.p.load(inStream);
	}

	public void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException {
		this.p.loadFromXML(in);
	}

	public Object put(Object key, Object value) {
		return this.p.put(key, value);
	}

	public void putAll(Map<? extends Object, ? extends Object> t) {
		this.p.putAll(t);
	}

	public Object remove(Object key) {
		return this.p.remove(key);
	}

	public void save(OutputStream out, String comments) {
		this.p.save(out, comments);
	}

	public int size() {
		return this.p.size();
	}

	public void store(OutputStream out, String comments) throws IOException {
		this.p.store(out, comments);
	}

	public void storeToXML(OutputStream os, String comment, String encoding) throws IOException {
		this.p.storeToXML(os, comment, encoding);
	}

	public void storeToXML(OutputStream os, String comment) throws IOException {
		this.p.storeToXML(os, comment);
	}

	public Collection<Object> values() {
		return this.p.values();
	}

	public String toString() {
		return this.p.toString();
	}

	private static boolean isBlankString(String value) {
		return value == null || "".equals(value.trim());
	}

	private static String[] tokenizeToStringArray(String str, String seperators) {
		StringTokenizer tokenlizer = new StringTokenizer(str, seperators);
		ArrayList result = new ArrayList();

		while (tokenlizer.hasMoreElements()) {
			Object s = tokenlizer.nextElement();
			result.add(s);
		}

		return (String[]) ((String[]) result.toArray(new String[result.size()]));
	}

	private static int[] toIntArray(String[] array) {
		int[] result = new int[array.length];

		for (int i = 0; i < array.length; ++i) {
			result[i] = Integer.parseInt(array[i]);
		}

		return result;
	}

	public static void main(String[] args) {
		Properties p = new Properties();
		p.put("Dialect.oracle", "com.hotent.base.db.mybatis.dialect.OracleDialect");
		p.put("Dialect.mssql", "com.hotent.base.db.mybatis.dialect.SQLServer2005Dialect");
		p.put("Dialect.mysql", "com.hotent.base.db.mybatis.dialect.MySQLDialect");
		p.put("Dialect.db2", "com.hotent.base.db.mybatis.dialect.DB2Dialect");
		p.put("hello", "word");
		PropertiesHelper help = new PropertiesHelper(p);
		Properties p1 = help.getStartsWithProperties("Dialect.");
		System.out.println(p1.getProperty("oracle"));
	}
}