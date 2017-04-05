package edu.scut.ibm.slpa.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.scut.ibm.slpa.RandomNumGenerator;

public class UserNode {
	private int userId;//用户的ID号
	private int communityId;//用户的社区号
	private Map<Integer, Integer> communityDistribution;//用户迭代T次的社区分布
	
	public UserNode(int userId,int communityId){
		this.userId=userId;
		this.communityId=communityId;
		this.communityDistribution=new HashMap<Integer,Integer>();
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public Map<Integer, Integer> getCommunityDistribution() {
		return communityDistribution;
	}

	public void setCommunityDistribution(Map<Integer, Integer> communityDistribution) {
		this.communityDistribution = communityDistribution;
	}

	/**
	 * 更新用户的社区分布
	 * @param votedCommunity
	 * @param voteIncrement
	 */
	public void updateCommunityDistribution(int votedCommunity, int voteIncrement) {
		if ( communityDistribution.containsKey(votedCommunity) ) {
			voteIncrement += communityDistribution.get(votedCommunity);
		}
		communityDistribution.put(votedCommunity, voteIncrement);
	}
	
	//Vote for a community randomly based on the distribution of communities at this node
	public int speakerVote() {
		//Run through each element in the map to create a cumulative distribution
		Set<Integer> communityIds = communityDistribution.keySet();
		ArrayList<Integer> communities = new ArrayList<Integer>();
		ArrayList<Integer> cumulativeCounts = new ArrayList<Integer>();
		
		int sum=-1;
		for (Integer comm: communityIds) {
			sum += communityDistribution.get(comm);
			communities.add(comm);
			cumulativeCounts.add(sum);
		}
	
		//Generate a random integer in the range [0,sum)
		int rand = RandomNumGenerator.getRandomInt(sum+1);
		
		//Find the index of first value greater than rand in cumulativeCounts
		int i=0;
		for (i=0; i<cumulativeCounts.size(); i++) {
			if (cumulativeCounts.get(i)>=rand) 
				break;
		}
			
		//Return the corresponding community
		return communities.get(i);
	}
}
