package netty.action;

public class Data {
	
	private int n;
	
	private int m;
	
	public Data(int n, int m){
		this.n = n;
		this.m = m;
	}
	
	public String toString(){
		int r = n/m;
		return n + "/" + m + "=" + r;
	}

}
