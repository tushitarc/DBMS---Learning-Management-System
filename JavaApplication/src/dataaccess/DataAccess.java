package dataaccess;

import java.sql.*;

public class DataAccess implements IDataAccess{

	public Connection conn;
	
	public Connection connect() throws Exception{
		String connString ="jdbc:sqlserver://dbmsproject11.database.windows.net:1433;database=DBMSFinal;user=dbmsproject11@dbmsproject11;password=DBMSProject0;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
		this.conn = DriverManager.getConnection(connString);
		return conn;
	}
	
	public ResultSet executeQuery(String query) throws Exception{
		Connection conn = connect();
		Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    //conn.close(); 
	    return rs;
	}
	
	public void executeQueryWithoutResult(String query) throws Exception{
		Connection conn = connect();
		Statement stmt = conn.createStatement();
	    stmt.executeUpdate(query);
	}
	
}
