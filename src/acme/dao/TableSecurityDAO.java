package acme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dbmodel.TableSecurity;
import acme.util.DBConnector;
import defuse.PasswordStorage;

public class TableSecurityDAO {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	// db action, insert, update, delete

	public TableSecurityDAO() {

	}


	public TableSecurity getTableSecurity(int id) {
		TableSecurity tableSec = new TableSecurity();
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "select id, name, level from table_security where id = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, id);

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					tableSec.setId(rs.getInt("id"));
					tableSec.setName(rs.getString("name"));
					tableSec.setLevel(rs.getString("level"));
				}
				rs.close();

			} catch(SQLException sqle) {
				throw sqle;
			}

		} catch(SQLException e) {
			LOG.error("getTableSecurity error!", e);
			//throw e;
			tableSec = null;
		}
		return tableSec;
	}


	public List<TableSecurity> getList() {
		List<TableSecurity> ls = new ArrayList<TableSecurity>();
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "select id, name, level from table_security";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {

				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					TableSecurity tableSec = new TableSecurity();
					tableSec.setId(rs.getInt("id"));
					tableSec.setName(rs.getString("name"));
					tableSec.setLevel(rs.getString("level"));
					ls.add(tableSec);
				}
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


	public boolean createNewTable(String tablename, String securitylevel) {
		boolean isSuccess = false;
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "insert into table_security (name, level) values (?,?)";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, tablename);
				ps.setString(2, securitylevel);

				isSuccess = ps.executeUpdate()>0;

			} catch(Exception e) {
				throw e;
			}

		} catch(Exception e) {
			LOG.error("Create new tables error!", e);
		}
		return isSuccess;
	}


	public boolean updateLevel(TableSecurity tableSec) {//throws SQLException
		boolean isUpdateSuccess = false;
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "update table_security set level = ? where id = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				String level = tableSec.getLevel();
				if (level!=null && level.trim().length()>0) {
					// TODO: check against valid values
					ps.setString(1, level.trim());
				} else {
					ps.setNull(1, java.sql.Types.VARCHAR);;
				}

				ps.setInt(2, tableSec.getId());

				isUpdateSuccess = ps.executeUpdate()>0;

			} catch(SQLException sqle) {
				throw sqle;
			}
		} catch(SQLException e) {
			LOG.error("getList error!", e);
			//throw e;
		}
		return isUpdateSuccess;
	}


}
