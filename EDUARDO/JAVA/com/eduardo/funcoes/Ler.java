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





	public static void main(String[] args) {

		Ler var = new Ler("C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\data\\TestClass.txt");

		double Time,Ax,Ay,Az,Wx,Wy,Wz;

			for(int i=0;i<4;i++)
			{

			Time = var.LerTime();
			Ax = var.LerAx();
			Ay = var.LerAy();
			Az = var.LerAz();
			Wx = var.LerWx();
			Wy = var.LerWy();
			Wz = var.LerWz();
			var.Descarte();
			
		
			System.out.println("Time/Ax/Ay/Az/Wx/Wy/Wz: "+Time+" "+Ax+" "+Ay+" "+Az+" "+Wx+" "+Wy+" "+Wz);
			
			}

	}
	
}

