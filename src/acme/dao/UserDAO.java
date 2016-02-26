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
import defuse.PasswordStorage;

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


	public List<User> getListWithoutAdmin() {
		List<User> ls = new ArrayList<User>();
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "select id, username, level, admin from user where admin = 'N'";
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

	/*
	 * encryption of password?
	 */
	public void createNewUser(String username, String password) {
		//User user = null;
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "insert into user (username, password) values ('?','?')";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, username);
				ps.setString(2, password);

			/*	ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setLevel(rs.getString("level"));
					user.setAdmin("Y".equals(rs.getString("admin")));
				}
				rs.close();*/

			} catch(SQLException sqle) {
				throw sqle;
			}

		} catch(SQLException e) {
			LOG.error("Create new user error!", e);
			//user = null;
		}
	
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

	
	

	public boolean updatePassword(User user, String oldPassword, String newPassword, String newPassword2) {
		boolean isPasswordUpdated = false;
		if (user!=null && oldPassword!=null && newPassword!=null && newPassword.equals(newPassword2)) {

			try(Connection conn = DBConnector.getConnection()) {

				String hashOldPassword = null;
				String sql = "select id, password from user where id = ?";
				try (PreparedStatement ps = conn.prepareStatement(sql)) {
					ps.setInt(1, user.getId());

					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						hashOldPassword = rs.getString("password");
					}
					rs.close();
				} catch(Exception e1) {
					throw e1;
				}

				LOG.debug("user.getId()="+user.getId()+" , hashOldPassword="+hashOldPassword);

				boolean isOldPasswordMatch = PasswordStorage.verifyPassword(oldPassword, hashOldPassword);
				LOG.debug("isOldPasswordMatch="+isOldPasswordMatch);

				if (isOldPasswordMatch) {

					String sql2 = "update user set password = ? where id = ?";
					try (PreparedStatement ps = conn.prepareStatement(sql2)) {
						ps.setString(1, PasswordStorage.createHash(newPassword));
						ps.setInt(2, user.getId());

						isPasswordUpdated = ps.executeUpdate()>0;

					} catch(Exception e1) {
						throw e1;
					}
				}
			} catch(Exception e) {
				LOG.error("updatePassword error!", e);
				//throw e;
			}

		}
		return isPasswordUpdated;
	}


	// can only reset non-admin password
	public boolean resetPassword(User user, String newPassword, String newPassword2) {
		boolean isPasswordUpdated = false;
		if (user!=null && newPassword!=null && newPassword.equals(newPassword2)) {

			try(Connection conn = DBConnector.getConnection()) {

				String sql2 = "update user set password = ? where id = ? and admin = 'N' ";
				try (PreparedStatement ps = conn.prepareStatement(sql2)) {
					ps.setString(1, PasswordStorage.createHash(newPassword));
					ps.setInt(2, user.getId());

					isPasswordUpdated = ps.executeUpdate()>0;

				} catch(Exception e1) {
					throw e1;
				}

			} catch(Exception e) {
				LOG.error("resetPassword error!", e);
				//throw e;
			}

		}
		return isPasswordUpdated;
	}


}
