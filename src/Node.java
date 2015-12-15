import java.util.StringTokenizer;

public class Node {
	private int nodeNum;
	private int[] dest = new int[13];
	private int flushEquivClass;
	private int equivClass;
	
	public Node(String line) {
		StringTokenizer tokenizer = new StringTokenizer(line, ",");
		nodeNum = Integer.parseInt(tokenizer.nextToken());
		for (int i = 0; i < 13; ++i) {
			dest[i] = Integer.parseInt(tokenizer.nextToken());
		}
		flushEquivClass = Integer.parseInt(tokenizer.nextToken());
		equivClass = Integer.parseInt(tokenizer.nextToken());
	}
	
	public int getNodeNum() {
		return nodeNum;
	}
	
	public int getDest(int num) {
		return dest[num];
	}
	
	public int getFlushEquivClass() {
		return flushEquivClass;
	}
	
	public int getEquivClass() {
		return equivClass;
	}
}
