package com.hotent.core.table;

public class SqlTypeConst {
	public static final String ORACLE = "oracle";
	public static final String MYSQL = "mysql";
	public static final String SQLSERVER = "mssql";
	public static final String DB2 = "db2";
	public static final String DERBY = "derby";
	public static final String HBASE = "hbase";
	public static final String HIVE = "hive";
	public static final String H2 = "h2";
	public static final String JTDS = "jtds";
	public static final String MOCK = "mock";
	public static final String HSQL = "hsql";
	public static final String POSTGRESQL = "postgresql";
	public static final String SYBASE = "sybase";
	public static final String SQLITE = "sqlite";
	public static final String MCKOI = "mckoi";
	public static final String CLOUDSCAPE = "cloudscape";
	public static final String INFORMIX = "informix";
	public static final String TIMESTEN = "timesten";
	public static final String AS400 = "as400";
	public static final String SAPDB = "sapdb";
	public static final String JSQLCONNECT = "JSQLConnect";
	public static final String JTURBO = "JTurbo";
	public static final String FIREBIRDSQL = "firebirdsql";
	public static final String INTERBASE = "interbase";
	public static final String POINTBASE = "pointbase";
	public static final String EDBC = "edbc";
	public static final String MIMER = "mimer";
	public static final String DM = "dm";
	private static final String INGRES = "ingres";

	public static String getDbType(String rawUrl) {
		return rawUrl == null
				? null
				: (rawUrl.startsWith("jdbc:derby:")
						? "derby"
						: (rawUrl.startsWith("jdbc:mysql:")
								? "mysql"
								: (rawUrl.startsWith("jdbc:oracle:")
										? "oracle"
										: (!rawUrl.startsWith("jdbc:microsoft:") && !rawUrl.startsWith(
												"jdbc:sqlserver:")
														? (rawUrl.startsWith("jdbc:sybase:Tds:")
																? "sybase"
																: (rawUrl.startsWith("jdbc:jtds:")
																		? "jtds"
																		: (!rawUrl.startsWith("jdbc:fake:") && !rawUrl
																				.startsWith("jdbc:mock:")
																						? (rawUrl.startsWith(
																								"jdbc:postgresql:")
																										? "postgresql"
																										: (rawUrl
																												.startsWith(
																														"jdbc:hsqldb:")
																																? "hsql"
																																: (rawUrl
																																		.startsWith(
																																				"jdbc:db2:")
																																						? "db2"
																																						: (rawUrl
																																								.startsWith(
																																										"jdbc:sqlite:")
																																												? "sqlite"
																																												: (rawUrl
																																														.startsWith(
																																																"jdbc:ingres:")
																																																		? "ingres"
																																																		: (rawUrl
																																																				.startsWith(
																																																						"jdbc:h2:")
																																																								? "h2"
																																																								: (rawUrl
																																																										.startsWith(
																																																												"jdbc:mckoi:")
																																																														? "mckoi"
																																																														: (rawUrl
																																																																.startsWith(
																																																																		"jdbc:cloudscape:")
																																																																				? "cloudscape"
																																																																				: (rawUrl
																																																																						.startsWith(
																																																																								"jdbc:informix-sqli:")
																																																																										? "informix"
																																																																										: (rawUrl
																																																																												.startsWith(
																																																																														"jdbc:timesten:")
																																																																																? "timesten"
																																																																																: (rawUrl
																																																																																		.startsWith(
																																																																																				"jdbc:as400:")
																																																																																						? "as400"
																																																																																						: (rawUrl
																																																																																								.startsWith(
																																																																																										"jdbc:sapdb:")
																																																																																												? "sapdb"
																																																																																												: (rawUrl
																																																																																														.startsWith(
																																																																																																"jdbc:JSQLConnect:")
																																																																																																		? "JSQLConnect"
																																																																																																		: (rawUrl
																																																																																																				.startsWith(
																																																																																																						"jdbc:JTurbo:")
																																																																																																								? "JTurbo"
																																																																																																								: (rawUrl
																																																																																																										.startsWith(
																																																																																																												"jdbc:firebirdsql:")
																																																																																																														? "firebirdsql"
																																																																																																														: (rawUrl
																																																																																																																.startsWith(
																																																																																																																		"jdbc:interbase:")
																																																																																																																				? "interbase"
																																																																																																																				: (rawUrl
																																																																																																																						.startsWith(
																																																																																																																								"jdbc:pointbase:")
																																																																																																																										? "pointbase"
																																																																																																																										: (rawUrl
																																																																																																																												.startsWith(
																																																																																																																														"jdbc:edbc:")
																																																																																																																																? "edbc"
																																																																																																																																: (rawUrl
																																																																																																																																		.startsWith(
																																																																																																																																				"jdbc:mimer:multi1:")
																																																																																																																																						? "mimer"
																																																																																																																																						: (rawUrl
																																																																																																																																								.startsWith(
																																																																																																																																										"jdbc:dm:")
																																																																																																																																												? "dm"
																																																																																																																																												: null))))))))))))))))))))
																						: "mock")))
														: "mssql"))));
	}
}