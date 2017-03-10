package net.trucomanx.pdsplibj.pdsdf;

public class MeuErro extends Exception{

	//public MeuErro(){}

	public MeuErro(String s){

		super(s);
	}
	public MeuErro(Throwable throwable){

		super(throwable);
	}
	public MeuErro(String s, Throwable throwable){

		super(s,throwable);
	}

}
