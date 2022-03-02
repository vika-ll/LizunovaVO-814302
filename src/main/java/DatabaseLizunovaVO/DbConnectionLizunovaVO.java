package DatabaseLizunovaVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DbConnectionLizunovaVO extends DBConnectLizunovaVO {
    Connection dbConnection;
        private static DbConnectionLizunovaVO _instance = null;

        private DbConnectionLizunovaVO() throws ClassNotFoundException, SQLException {
            String connectionString = "jdbc:mysql://" + dbHost + ":"
                    + dbPort + "/" + dbName + "?verifyServerCertificate=false" +
                    "&useSSL=false" +
                    "&allowPublicKeyRetrieval=true" +
                    "&requireSSL=false" +
                    "&useLegacyDatetimeCode=false" +
                    "&amp" +
                    "&serverTimezone=UTC";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties properties=new Properties();
            properties.setProperty("user",dbUser);
            properties.setProperty("password",dbPass);
            properties.setProperty("useUnicode","true");
            properties.setProperty("characterEncoding","UTF-8");
            dbConnection= DriverManager.getConnection(connectionString,properties);
        }

    public Connection getDbConnection() {
        return dbConnection;
    }

    public static synchronized DbConnectionLizunovaVO getInstance() throws SQLException, ClassNotFoundException {
            if (_instance == null)
                _instance = new DbConnectionLizunovaVO();
            return _instance;
        }
}
