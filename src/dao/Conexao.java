package dao;

import java.sql.Connection;
import java.sql.DriverManager;


abstract class Conexao{
	
    protected Connection conexao;
    
    public Conexao(){
    	
        try{
        	System.out.println("iniciando");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("passou");
            conexao = DriverManager.getConnection(
            		"jdbc:mysql://127.0.0.1:3306/m_entrega","root","root");
            
            
            System.out.println("conectado");
            
//            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//            System.out.println("SUCESSO");
//return DriverManager.getConnection("jdbc:mysql://localhost/youmusiclib","root","admin");
//            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
