package Business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

import Objects.Pregunta;
import Objects.Tarea;
import Objects.Tramite;
import Objects.Usuario;

public class TestUi {
	
	static BufferedReader    in;
    static PrintStream       out;
    static Usuario 			 userLogged;
    static ProcesoManager    _pManagerService ;
    
	public static void main(String[] args)  throws IOException
	{
		in  = new BufferedReader(new InputStreamReader(System.in));
		out = System.out;
		_pManagerService =  new ProcesoManager();
		
		StartUpApp();
	}
	
	public static void StartUpApp() throws IOException
	{
		try 
		{
			boolean islogin = false;
			do {
				islogin = AutenticateUser();		
				
				if(!islogin)
					out.println("Error, usuario o contraseña incorrecta");
					
			}while(!islogin);
			ShowMenuForUser();
		}
		catch (Exception e) {
			out.println("Algo ocurrio:" + e.getMessage());
		}
	}
	
	public static boolean AutenticateUser() throws IOException 
	{
		out.println("Ingrese su email");
		String email = ValidateString(in.readLine());
		out.println("Ingrese su password");
		String password = ValidateString(in.readLine());
		
		
		AutheticationManager authManager = new AutheticationManager();
		userLogged = authManager.ValidateLogin(email, password);
		
		return userLogged.getMail() != null;
	}
	
	public static void ShowMenuForUser() throws IOException
	{
		if(userLogged.getGrupos().getNombre().equals("Admin"))
		{
			ShowAdminOptions();
		}
		else 
		{
			ShowClientOptions();
		}		
	}
	
	public static void ShowAdminOptions() throws IOException 
	{
		int option = 0;
		do {
		out.println("Seleccione una opción");
		out.println("1. Crear proceso");
		out.println("2. Crear Usuario");
		
		out.println("10. Salir");
		option = Integer.parseInt(ValidateString(in.readLine()));
		ManageAdminOptions(option);
		
		}while(option != 10);
	}
	
	public static void ShowClientOptions() throws NumberFormatException, IOException 
	{
		int option = 0;
		do {
		out.println("Seleccione una opción");
		out.println("1. Mostrar procesos disponible");
		out.println("2. Mostrar procesos en curso");
		
		out.println("10. Salir");
		option = Integer.parseInt(ValidateString(in.readLine()));
		ManageUserOptions(option);
		
		}while(option != 10);
	}
	
	public static void ManageAdminOptions(int option) throws  IOException 
	{		
		switch(option) 
		{
			case 1:
				StartTramite();
			break;
				
			case 2:
				
			break;
			
			case 3:
			break;	
		}				
	}
	
	public static void ManageUserOptions(int option) throws  IOException 
	{		
		switch(option) 
		{
			case 1:
				ShowAvailableProcess();
			break;
				
			case 2:
			break;
			
			case 3:
			break;	
		}				
	}
	
	public static void StartTramite() throws  IOException 
	{
		out.println("Creando proceso");
		out.println("1. Nombre del proceso");
		String nombreProceso = ValidateString(in.readLine());
		out.println("2. Numero del proceso");
		int numeroProceso = Integer.parseInt(ValidateString(in.readLine()));
		
		Tramite tramiteToCreate = _pManagerService.CreateNewTramite(numeroProceso, nombreProceso);
		int option = 0;
		do
		{
			CreateTasks(tramiteToCreate);
			
			out.println("Desea agregar otra tarea? 2 -> Para no");
			option = Integer.parseInt(ValidateString(in.readLine()));
			
		}while(option != 2);
		
		_pManagerService.SaveProcess(tramiteToCreate);
	}
	
	
	public static String ValidateString(String enterValue)
	{
		if(enterValue == null || enterValue == "" || enterValue.isEmpty()) 
		{
			out.println("Error!, ingrese un valor valido");
			return "-1";
		}
		
		return enterValue;
	}
	
	public static void CreateTasks(Tramite tramite) throws  IOException 
	{
		out.println("1. Nombre de la tarea");
		String nombreTarea = ValidateString(in.readLine());
		out.println("2. Numero del grupo que va a ser capaz de manejarla");
		String numeroGrupo = ValidateString(in.readLine());
		tramite = _pManagerService.AddStepsToProcess(tramite, nombreTarea, numeroGrupo);	
		int option = 0;
		do
		{
			CreateTasksConditions(tramite);
			
			out.println("Desea agregar otra condicion a la tarea? 2 -> Para no");
			option = Integer.parseInt(ValidateString(in.readLine()));
			
		}while(option != 2);
	}
	
	public static void CreateTasksConditions(Tramite tramite) throws IOException
	{
		out.println("1. Escriba la pregunta");
		String pregunta = ValidateString(in.readLine());
		out.println("2. Escriba el tipo de pregunta. (Default, Custom)");
		String tipo = ValidateString(in.readLine());
		ArrayList<String> respuestas =  new ArrayList<String>();
		
		if (!tipo.equals("default")) {
			int option = 0;
			do 
			{
				out.println("Digite la respuesta");
				respuestas.add(ValidateString(in.readLine()));				
				out.println("Desea agregar mas respuestas? 2 -> Para no");
				option = Integer.parseInt(ValidateString(in.readLine()));
				
			}while(option != 2);
		}
		else 
		{
			respuestas.add("1. Si");
			respuestas.add("2. No");
		}
		
		_pManagerService.AddQuestions(tramite, pregunta, respuestas, tipo);
	}
	
	public static void ShowStringList(ArrayList<String> results) 
	{
		for(String obj : results)
		{
			out.println(obj);
		}
	}
	
	public static void ShowAvailableProcess() throws NumberFormatException, IOException 
	{
		ShowStringList(_pManagerService.GetTramites());
		out.println("Seleccione una opcion");
		int tramite = Integer.parseInt(ValidateString(in.readLine()));
		StartProcess(tramite);
	}	
	
	public static void StartProcess(int numberProcess)  throws IOException 
	{
		Tramite startTramite = _pManagerService.GetTramiteByNumber(numberProcess);
		out.println("Proceso : " + startTramite.getDescripcion());
		for(Tarea obj : startTramite.getTareas())
		{
			out.println("Tarea : " + obj.getDescripcion());
			ShowQuestion(obj.getPregunta());
		}
	}
	
	public static void ShowQuestion(ArrayList<Pregunta> questions) throws IOException 
	{
		
		for(Pregunta obj : questions)
		{
			out.println("Condicion : " + obj.getPregunta());
			ShowStringList(obj.getRespuestas());
			String respuesta = in.readLine();
		}		
	}
}
