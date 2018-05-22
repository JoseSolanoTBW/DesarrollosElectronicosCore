package Business;

import java.util.ArrayList;

import Objects.Grupo;
import Objects.Tarea;
import Objects.Tramite;

public class ProcesoManager {

	public Tramite CreateNewTramite(int Numero, String Descripcion) 
	{
		Tramite nuevoTramite = new Tramite();
		nuevoTramite.setNumeroTramite(Numero);
		nuevoTramite.setDescripcion(Descripcion);
		
		return nuevoTramite;
	}
	
	public Tramite AddStepsToProcess(Tramite tramite) 
	{
		Tarea pendingTask = new Tarea();
		Grupo group = new Grupo();
		
		pendingTask.setGrupoUsuarios(group);
		
		return tramite;
		
	}
	
}
