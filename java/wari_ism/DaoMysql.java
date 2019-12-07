/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author user
 */
public class DaoMysql {
     private   Connection cnx;
    private PreparedStatement pstm;
    private int ok;
    private ResultSet rs;
    
    public  void getConnection() throws SQLException
	{
          cnx=null;  
	try{		
              Class.forName("com.mysql.jdbc.Driver");
		cnx=DriverManager.getConnection("jdbc:mysql://"+Config.dbhost+":"+Config.dbport+"/"+Config.dbname,
                                                   Config.dbuser,Config.dbpass);
	}catch(ClassNotFoundException e)
	{
		System.out.println(e.getMessage());
	}
       
	} 
   
   public void initPS(String sql) throws SQLException
	{
		getConnection();
		try{
			  if(sql.toLowerCase().startsWith("insert"))
			     {
			    	pstm=cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS );
			     }
			else{
				   pstm=cnx.prepareStatement(sql);
		        }
		    }catch(Exception e)
		{
			e.printStackTrace();
		}



	}
	public int executeMaj()
	{
		try {
			ok = pstm.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return ok;
	}
	public ResultSet executeSelect()
	{
		try {

			rs=pstm.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();

		}
		return rs;
	}
	public PreparedStatement getPstm()
	{
		return  this.pstm;

	}
   
 public void CloseConnection(){
		try{
		if(cnx!=null){
			cnx.close();
		}
		}catch(Exception ex){
			ex.printStackTrace();

		}
	}
 
   public static DaoMysql getInstance(){
     return new DaoMysql();
   }
    
}
