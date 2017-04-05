package edu.scut.ibm.slpa.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class GraphReader {
	/* 
	 * Expects a edge list file. 
	 * First line contains total number of vertices and edges in the graph.
	 * 
	 * */
	public static SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> readGraph(File file) throws FileNotFoundException{
		SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		try {
			String relations = null;
			while ((relations = br.readLine()) != null) {
				String[] vertices = relations.split("\\s+");
				Integer sourceV = Integer.parseInt(vertices[0]);// 始顶点
				Integer targetV = Integer.parseInt(vertices[1]);// 终顶点
				if (!graph.containsVertex(sourceV)) {
					graph.addVertex(sourceV);
				}
				if (!graph.containsVertex(targetV)) {
					graph.addVertex(targetV);
				}
				graph.addEdge(sourceV, targetV);
			}
			System.out.println("Finished reading input graph<vertexs:" + graph.vertexSet().size() + ",edges: " + graph.edgeSet().size() + ">");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return graph;
	}
	
	public static void main(String arg[]) {
		try {
			File file = new File(arg[0]);
			GraphReader.readGraph(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
