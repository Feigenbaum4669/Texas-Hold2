public enum Rank {
	two(0), three(1), four(2), five(3), six(4), seven(5), eight(6), nine(
			7), ten(8), jack(9), queen(10), king(11), ace(12);

	private int index;

	private Rank(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
}