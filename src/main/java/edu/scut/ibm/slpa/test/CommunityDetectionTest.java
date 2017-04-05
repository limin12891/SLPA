package edu.scut.ibm.slpa.test;

import edu.scut.ibm.slpa.util.CommunityDetection;

public class CommunityDetectionTest {
	public static void main(String[] args) {
		CommunityDetection communityDetection = new CommunityDetection();
		communityDetection.initUserNodeGraph(args[0]);
		communityDetection.SLPA(Integer.parseInt(args[1]));
		communityDetection.print();
	}
}
