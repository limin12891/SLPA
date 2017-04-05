package edu.scut.ibm.slpa.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;

import edu.scut.ibm.slpa.bean.UserNode;
import edu.scut.ibm.slpa.common.GraphReader;

public class CommunityDetection {
	private UserNodeGraph userNodegraph;
	
	public CommunityDetection(){
		userNodegraph = new UserNodeGraph();
	}
	
	public void initUserNodeGraph(String fileName) {
		File file = new File(fileName);
		try {
			userNodegraph.setGraph(GraphReader.readGraph(file));
			int vertexCount = userNodegraph.getGraph().vertexSet().size();//获取顶点的数量
			List<Integer> userIds = new ArrayList<Integer>();
			userIds.addAll(userNodegraph.getGraph().vertexSet());
			for ( int communityId=0; communityId<vertexCount; communityId++ ) {
				UserNode userNode = new UserNode(userIds.get(communityId),communityId);//给每一个节点初始化一个社区
				userNode.updateCommunityDistribution(communityId, 1);
				userNodegraph.getNodeMap().put(userIds.get(communityId), userNode);
			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SLPA(int iterations) {
		for (int i=0; i<iterations; i++) {
			for(Integer userId : userNodegraph.getNodeMap().keySet()){
				updateLabels(userId);
			}
		}
	}
	
	public void updateLabels(Integer userId){
		Set<DefaultWeightedEdge> incomingEdges = userNodegraph.getGraph().incomingEdgesOf(userId);//获取所有该顶点的入度顶点
		Map<Integer, Integer> incomingVotes = new HashMap<Integer, Integer>();//所有speaker顶点投票情况
		
		//For each vertex V with an incoming edge to the current node
		for ( DefaultWeightedEdge edge: incomingEdges ) {
			int speakerId = userNodegraph.getGraph().getEdgeSource(edge);
			UserNode speakerNode = userNodegraph.getNodeMap().get(speakerId);
			
			int votedCommunity = speakerNode.speakerVote();
			int votedCommunitycount = 1;
			if ( incomingVotes.containsKey(votedCommunity)){
				votedCommunitycount += incomingVotes.get(votedCommunity);
			} 
			incomingVotes.put(votedCommunity, votedCommunitycount);
		}
		
		//Find the most popular vote
		Iterator<Entry<Integer, Integer>> it = incomingVotes.entrySet().iterator();
		int popularCommunity=-1;
		int popularCommunityCount=0;
		while ( it.hasNext()) {
			Entry<Integer, Integer> entry = it.next();
			if ( entry.getValue() > popularCommunityCount ) {
				popularCommunity = entry.getKey();
				popularCommunityCount = entry.getValue();
			}
		}
		//Update community distribution of the current node by 1
		UserNode currentNode = userNodegraph.getNodeMap().get(userId);
		currentNode.updateCommunityDistribution(popularCommunity, 1);
	}
	
	public void print(){
		Map<Integer,UserNode> nodeMap = userNodegraph.getNodeMap();
		for(Integer userId : nodeMap.keySet()){
			UserNode usernode = nodeMap.get(userId);
			System.out.print("usernode:"+usernode.getUserId()+"\t");
			Map<Integer,Integer> communityDistributions = usernode.getCommunityDistribution();
			for(Entry<Integer, Integer> entry : communityDistributions.entrySet()){
				System.out.print(entry.getKey()+":"+entry.getValue()+"\t");
			}
			System.out.println();
		}
	}
}
