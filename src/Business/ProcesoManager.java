package Business;

import java.util.ArrayList;

import DataAccess.MongoDbTransaction;
import Objects.Grupo;
import Objects.Pregunta;
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
	
	public Tramite AddStepsToProcess(Tramite tramite, String descripcion, String nombreGrupo ) 
	{
		Tarea pendingTask = new Tarea();
		Grupo group = new Grupo();
		
		pendingTask.setDescripcion(descripcion);
		group.setNombre(nombreGrupo);
		
		pendingTask.setGrupoUsuarios(group);
		tramite.setTarea(pendingTask);		
		
		return tramite;		
	}
	
	public Tramite AddQuestions(Tramite tramite, String pregunta, ArrayList<String> respuestas, String tipo) 
	{
		ArrayList<Tarea> tareas = tramite.getTareas();
		Tarea lastTarea = tareas.get(tareas.size() - 1);
		Pregunta newPregunta = new Pregunta();
		
		newPregunta.setPregunta(pregunta);
		newPregunta.setRespuestas(respuestas);
		newPregunta.setTipo(tipo);
		
		lastTarea.setPregunta(newPregunta);	
		
		return tramite;		
	}
	
	public void SaveProcess(Tramite traminte) 
	{
		MongoDbTransaction transaction = new MongoDbTransaction();
		transaction.InsertDocuement(traminte, "Tramites");
	}
	
}
