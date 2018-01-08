package dataaccess;

import java.sql.Connection;
import java.sql.ResultSet;

public interface IDataAccess {
	String username = "dbmsproject";
	String password = "DBMSProject0";
	Connection connect() throws Exception;	
	ResultSet executeQuery(String query) throws Exception;
	public void executeQueryWithoutResult(String query) throws Exception;
}
