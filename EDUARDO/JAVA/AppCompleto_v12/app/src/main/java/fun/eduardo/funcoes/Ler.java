package fun.eduardo.funcoes;

import net.trucomanx.pdsplibj.pdsextras.*;

public class Ler{

	PdsReadData caminho;

	String Time1,ax1,ay1,az1,wx1,wy1,wz1;

	
	public Ler(String filename){

		this.caminho = new PdsReadData(filename);

	}



	public double LerTime() {

				
		this.Time1 = this.caminho.ReadData();
	
		return(Double.parseDouble(Time1));


	}

	public double LerAx() {

		
		this.Time1 = this.caminho.ReadData();

		return(Double.parseDouble(Time1));

	}

	public double LerAy() {

		
		this.Time1 = this.caminho.ReadData();

		return(Double.parseDouble(Time1));

	}

	public double LerAz() {

		
		this.Time1 = this.caminho.ReadData();

		return(Double.parseDouble(Time1));

	}

	public double LerWx() {

		
		this.Time1 = this.caminho.ReadData();

		return(Double.parseDouble(Time1));

	}

	public double LerWy() {

		
		this.Time1 = this.caminho.ReadData();

		return(Double.parseDouble(Time1));

	}

	public double LerWz() {

		
		this.Time1 = this.caminho.ReadData();
	
		return(Double.parseDouble(Time1));
				
	}
		
	public void Descarte(){
		
		for(int i=0;i<=5;i++){

			this.Time1 = this.caminho.ReadData();
		}
		
	}

	public void Descarte2(){
		
		for(int i=0;i<=11;i++){

			this.Time1 = this.caminho.ReadData();
		}
		
	}



	
	
}

