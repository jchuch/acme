package acme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dbmodel.User;
import acme.util.DBConnector;

public class UserDAO {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	// db action, insert, update, delete

	public UserDAO() {

	}


	public List<User> getList() {
		List<User> ls = new ArrayList<User>();
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "select id, username, level, admin from user";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {

				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setLevel(rs.getString("level"));
					user.setAdmin("Y".equals(rs.getString("admin")));
					ls.add(user);
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


	public boolean updateLevel(User user) {//throws SQLException
		boolean isUpdateSuccess = false;
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "update user set level = ? where id = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				String level = user.getLevel();
				if (level!=null && level.trim().length()>0) {
					// TODO: check against valid values
					ps.setString(1, level.trim());
				} else {
					ps.setNull(1, java.sql.Types.VARCHAR);;
				}

				ps.setInt(2, user.getId());

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
