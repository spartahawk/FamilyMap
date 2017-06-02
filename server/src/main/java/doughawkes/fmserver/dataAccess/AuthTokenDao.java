package doughawkes.fmserver.dataAccess;
import doughawkes.fmserver.model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;


/**
 * class that deals with the database and makes changes and lookups
 * for AuthToken entries
 */
public class AuthTokenDao extends Dao {
    Connection connection;
    boolean success;
    /**
     * creates new AuthTokenDao object to interact with the database
     */
    public AuthTokenDao() {
        // id integer not null primary key autoincrement,
        // token text not null,
        // loginTime timestamp not null,
        // userName text not null
    }

    /**
     * adds an AuthToken to the database
     * @param a AuthToken of interest
     * @return true or false for success
     */
    boolean addAuthToken(AuthToken a) {
        return false;
    }

    /**
     * looks up a AuthToken in the database
     * @param a AuthToken of interest
     * @return the AuthToken object successfully found
     */
    AuthToken lookup(AuthToken a) {
        return null;
    }

    public String generateAuthToken(String userName) {
        success = false;

        PreparedStatement stmt = null;
        String authTokenString = "";

        try {
            String sql = "insert into authtoken (token, username) values (?, ?)";
            stmt = connection.prepareStatement(sql);
            //random token
            authTokenString = UUID.randomUUID().toString();
            stmt.setString(1, authTokenString);
            //timestamp is auto-generated by the database table itself, not here.
            stmt.setString(2, userName);
            System.out.println("about to execute update");
            if  (stmt.executeUpdate() == 1) {
                System.out.println("AuthToken entry added to database pending transaction commit.");
                try {
                    throw new Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else throw new SQLException();

        } catch (SQLException e) {
            System.out.println("Update to generate and add new AuthToken to database failed.");
            e.printStackTrace();
        }
        finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        success = true;
        return authTokenString;
    }

//    for an update:
//    String sql = "update authtoken" +
//            "set token = ?" +
//            "set timestamp = ?" +
//            "set username = ?";

    /*public String lookupByUserName(String userName) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        //AuthToken authToken = new AuthToken();
        String authToken = "";

        try {
            String sql = "select token from authtoken where username = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            while (rs.next()) {
                // I only need the username rather than the whole authtoken object fields
                authToken = rs.getString("token");

                // TODO : remove these or use them in another method as needed.
                // authToken.setId(rs.getInt(1));
                // authToken.setToken(rs.getString(2));
                // authToken.setTimeStamp(rs.getTimestamp(3));
                // authToken.setUserName(rs.getString(4));

            }

        } catch (SQLException e) {
            System.out.println("Query for authtoken lookup by userName failed.");
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //return authToken.getAuthtoken();
        return authToken;
    }*/

    /**
     * deletes a AuthToken entry in the database
     * @param a AuthToken of interest
     * @return the AuthToken object successfully deleted
     */
    boolean delete(AuthToken a) {
        return false;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean clear() {
        PreparedStatement stmt = null;

        try {
            String sql = "delete from authtoken";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isSuccess() {
        return success;
    }
}
