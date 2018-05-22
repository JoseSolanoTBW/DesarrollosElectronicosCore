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
			 user = (Usuario)obj;
	    	
			 if(user.getMail().equals(email) && user.getPassword().equals(password)) 
			 	return user;			 				 
	    }
		
		return user;		
	}

}
