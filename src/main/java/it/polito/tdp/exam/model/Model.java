package it.polito.tdp.exam.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.exam.db.BaseballDAO;

public class Model {
	
	private BaseballDAO dao;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao = new BaseballDAO();
	}
	
	public List<String> getAllTeamsNames(){
		List<String> result = this.dao.getAllTeamsNames();
		return result;
	}
	
	public void creaGrafo(String squadra) {
		// INIZIALIZZO IL GRAFO
		this.grafo = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		// AGGIUNGO I VERTICI
		List<Integer> vertici = this.dao.getVertici(squadra);
		Graphs.addAllVertices(this.grafo, vertici);
		
		// AGGIUNGO GLI ARCHI
		for(Integer anno1 : vertici) {
			for(Integer anno2 : vertici) {
				if(!anno1.equals(anno2)) {
					List<People> lista1 = this.dao.getPeopleFromTeamAndYear(anno1, squadra);
					List<People> lista2 = this.dao.getPeopleFromTeamAndYear(anno2, squadra);
					lista1.retainAll(lista2);
					int peso = lista1.size();
						Graphs.addEdgeWithVertices(this.grafo, anno1, anno2, peso);
				}
			}
		}
	}
	
	
	public int numVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int numArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Integer> getVertici(){
		List<Integer> result = new ArrayList<Integer>(this.grafo.vertexSet());
		return result;
	}
	
	public List<Dettaglio> getDettagli(Integer anno){
		List<Integer> vicini = Graphs.neighborListOf(this.grafo, anno);
		List<Dettaglio> result = new ArrayList<Dettaglio>();
		
		for(Integer i : vicini) {
			DefaultWeightedEdge e = this.grafo.getEdge(anno, i);
			result.add(new Dettaglio(this.grafo.getEdgeTarget(e), (int)this.grafo.getEdgeWeight(e)));
		}
		
		Collections.sort(result);
		return result;
	}
	
	
	
	
}

