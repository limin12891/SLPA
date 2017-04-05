package edu.scut.ibm.slpa.util;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import edu.scut.ibm.slpa.bean.UserNode;

public class UserNodeGraph {
	private SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph;
	private Map<Integer,UserNode> nodeMap;
	
	public UserNodeGraph(){
		nodeMap = new HashMap<Integer, UserNode>();
	}
	
	public SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> getGraph() {
		return graph;
	}
	
	public void setGraph(SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph) {
		this.graph = graph;
	}

	public Map<Integer, UserNode> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(Map<Integer, UserNode> nodeMap) {
		this.nodeMap = nodeMap;
	}
}
