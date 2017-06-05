package it.polito.tdp.babs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;



public class DBConnect {

	static private final String jdbcUrl = "jdbc:mysql://localhost/babs?user=root";
	static private DataSource ds;

	public static Connection getConnection() {

		if (ds == null) {
			// crea il DataSource
			try {
				ds = DataSources.pooledDataSource(DataSources.unpooledDataSource(jdbcUrl));
			} catch (SQLException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		try {
			Connection c = ds.getConnection();
			return c;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
