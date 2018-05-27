package Business;

import java.util.ArrayList;

import DataAccess.MongoDbTransaction;
import Objects.Usuario;

public class AutheticationManager {
	
	
	public Usuario ValidateLogin(String email, String password) 
	{
		MongoDbTransaction transaction = new MongoDbTransaction();
		
		Usuario user = new Usuario();
		
		ArrayList<Object> results = transaction.GetData(new Usuario(), "Users");
		
		for(Object obj : results)
		{
			Usuario userPar = (Usuario)obj;
	    	
			 if(userPar.getMail().equals(email) && userPar.getPassword().equals(password)) 
			 	 user = userPar;			 				 
	    }
		
		return user;		
	}

}
