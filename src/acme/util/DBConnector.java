package acme.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBConnector {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	private static final ComboPooledDataSource cpds = new ComboPooledDataSource();

	static {
		LOG.debug("DBConnector");
		ResourceBundle rb;
		try {
			rb = ResourceBundle.getBundle("DBConfig");
			LOG.debug("JDBC_DRIVER="+rb.getString("JDBC_DRIVER"));

			cpds.setDriverClass( rb.getString("JDBC_DRIVER") ); //loads the jdbc driver
			cpds.setJdbcUrl( rb.getString("JDBC_URL") );
			cpds.setUser( rb.getString("JDBC_USER") );
			cpds.setPassword( rb.getString("JDBC_PASSWORD") );

			// the settings below are optional -- c3p0 can work with defaults
			cpds.setMinPoolSize( Integer.parseInt(rb.getString("MIN_POOL_SIZE")) );
			cpds.setAcquireIncrement( Integer.parseInt(rb.getString("ACQUIRE_INCREMENT")) );
			cpds.setMaxPoolSize( Integer.parseInt(rb.getString("MAX_POOL_SIZE")) );

		} catch(Throwable t) {
			LOG.error("cpds init error!", t);
		}
	}

	public static Connection getConnection() throws SQLException {
		return cpds.getConnection();
	}


}
