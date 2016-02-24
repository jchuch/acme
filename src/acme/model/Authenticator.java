package acme.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dbmodel.User;
import acme.util.DBConnector;
import defuse.PasswordStorage;

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


	public User authenticate2(String username, String password) {
		User user = null;
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "select id, username, level, admin, password from user where username = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, username);

				String hashPassword = null;
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setLevel(rs.getString("level"));
					user.setAdmin("Y".equals(rs.getString("admin")));

					hashPassword = rs.getString("password");
				}
				rs.close();

				if (!PasswordStorage.verifyPassword(password, hashPassword)) {
					throw new Exception("Incorrect username or password");
				}

			} catch(Exception e1) {
				throw e1;
			}

		} catch(Exception e) {
			LOG.error("authenticate error!", e);
			user = null;
		}
		return user;
	}


}
