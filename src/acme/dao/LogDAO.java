package acme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dbmodel.Log;
import acme.util.DBConnector;

public class LogDAO {
	private static final Logger LOG = LoggerFactory.getLogger("acme");

	// db action, insert, select

	public LogDAO() {

	}
	
	//join with user and table_security
	public List<Log> getList() {
		List<Log> ls = new ArrayList<Log>();
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "select log.id, log.userid, user.username, log.operation, log.tableid, table_security.name, log.time, log.message from log, user, table_security where log.userid = user.id and log.tableid = table_security.id";
			
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					Log log = new Log();
					
					log.setId(rs.getInt("id"));
					log.setUserId(rs.getInt("userid"));
					log.setUserName(rs.getString("user.username"));
					log.setOperation(rs.getString("operation"));
					log.setTableId(rs.getInt("tableid"));
					log.setTableName(rs.getString("table_security.name"));
					log.setTime(rs.getString("time"));
					log.setMessage(rs.getString("message"));
				
					ls.add(log);
					
					
					
				}
				//LOG.debug("#!$!@#$!@#$!@#$!@#$!@#$!@#$!@#$ls.size"+ ls.size());
				rs.close();

			} catch(SQLException sqle) {
				throw sqle;
			}

		} catch(SQLException e) {
			LOG.error("getList error!", e);
			//throw e;
		}
		return ls;
	}

	public boolean wrLog(Log log) {//throws SQLException
		boolean wrLogSuccess = false;
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "insert into log (userid, operation, tableid, message) values (?,?,?,?)";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				int userid = log.getUserId();
				String operation =log.getOperation();
				int tableid = log.getTableId();
				String message = log.getMessage();
				
				ps.setInt(1, userid);
				ps.setString(2, operation.trim());
				ps.setInt(3, tableid);
				ps.setString(4, message.trim());

				wrLogSuccess = ps.executeUpdate()>0;

			} catch(SQLException sqle) {
				throw sqle;
			}
		} catch(SQLException e) {
			LOG.error("wrLog error!", e);
			//throw e;
		}
		return wrLogSuccess;
	}

}
