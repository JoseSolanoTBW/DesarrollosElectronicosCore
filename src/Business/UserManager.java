package Business;

import java.util.ArrayList;

import DataAccess.MongoDbTransaction;
import Objects.Grupo;
import Objects.Rol;
import Objects.Usuario;

public class UserManager {

	public void CreateUser(String Nombre,  String Apellido, String Password, String NombreGrupo, String Privilegios, String PrivilegioRol, String Mail) 
	{
		Usuario user = new Usuario();
		
		user.setNombre(Nombre);
		user.setApellido(Apellido);
		user.setPassword(Password);
		Grupo group = new Grupo();
		ArrayList<String> privilegios = new ArrayList<String>();
		privilegios.add(Privilegios);
		group.setNombre(NombreGrupo);
		group.setPrivilegios(privilegios);		
		Rol rol = new Rol();
		ArrayList<String> privilegiosRoles = new ArrayList<String>();
		privilegiosRoles.add(PrivilegioRol);
		rol.setPrivilegios(privilegiosRoles);
		user.setRoles(rol);;
		
		MongoDbTransaction transaction =  new MongoDbTransaction();
		transaction.InsertDocuement(user, "Users");
		
	}
	
}
