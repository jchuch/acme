package acme.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dbmodel.User;
import acme.util.DBConnector;

public class Authenticator {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	public Authenticator() {

	}

	public User authenticate(String username, String password) {
		User user = null;
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "select id, username, level, admin from user where username = ? and password = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, username);
				ps.setString(2, password);

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setLevel(rs.getString("level"));
					user.setAdmin("Y".equals(rs.getString("admin")));
				}
				rs.close();

			} catch(SQLException sqle) {
				throw sqle;
			}

		} catch(SQLException e) {
			LOG.error("authenticate error!", e);
			user = null;
		}
		return user;
	}

}
