
package library;
import java.sql.*;
/**
 *
 * @author NITC5
 */
public class Connector {
    /**
     * Connection object to create connection
     * @param DBname
     * @param userName
     * @param passwd
     * @return
     * @throws SQLException 
     */
   static  Connection connectDataBase(String DBname,String userName,String passwd) 
           throws SQLException,ClassNotFoundException
    {
        
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/"+
                DBname,userName,passwd);
    }
        /**
    * closing database
    * @param con
    * @return 
    */
    static boolean close(Connection con)
    {
        try
        {
        con.close();
        return true;
        }
        catch(Exception e)
        {
            return false;
        }
       
    }
    
    
}
