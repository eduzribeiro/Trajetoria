import net.trucomanx.pdsplibj.pdsra.*;

public class Integrador {

	public double Vx,Vy,Vz,Sx,Sy,Sz;


	public Integrador(int initval){

		this.Vx = initval;
		this.Vy = initval;
		this.Vz = initval;

		this.Sx = initval;
		this.Sy = initval;
		this.Sz = initval;

	}	


	//a_n2: input signal  [1x3]
    //dt: input time interval [1x1]
    //Db: input saída Detector de pausa 0 [movimento] ou 1 [pausa]
    //return :::
    //Sx: Deslocamento no eixo x [1x1]
    //Sy: Deslocamento no eixo y [1x1]
    //Sz: Deslocamento no eixo z [1x1]
    //Vx: Deslocamento no eixo x [1x1]
    //Vy: Deslocamento no eixo y [1x1]
    //Vz: Deslocamento no eixo z [1x1]


   	public double integra_rt(double dt, PdsMatrix a_n2, double Db) {   

		this.Vx = this.Vx+a_n2.GetValue(0,0)*dt;
        this.Vy = this.Vy+a_n2.GetValue(0,1)*dt;
        this.Vz = this.Vz+a_n2.GetValue(0,2)*dt;

		if (Db == 1)
        {
        	this.Vx=0;
        	this.Vy=0;
            this.Vz=0;
        
        }		

		this.Sx = this.Sx + this.Vx*dt;
        this.Sy = this.Sy + this.Vy*dt;
        this.Sz = this.Sz + this.Vz*dt;
            
        return (Sx);
		
	}

	public double get_Vx(){
		return(Vx);
	}

	public double get_Vy(){
		return(Vy);
	}

	public double get_Vz(){
		return(Vz);
	}


	public double get_Sx(){
		return(Sx);
	}

	public double get_Sy(){
		return(Sy);
	}

	public double get_Sz(){
		return(Sz);
	}




	public static void main(String[] args) {

		Integrador var = new Integrador(0);

		PdsMatrix an_2 = new PdsMatrix(1,3);
		
		double dt = 0.1;
		double Sx,Sy,Sz;		
		
		double Db = 0;

		for (int i=0;i<5;i++){

			an_2.SetValue(0,0,100);
			an_2.SetValue(0,1,100);
			an_2.SetValue(0,2,100);

			var.integra_rt(dt,an_2,Db);

			Sx = var.get_Sx();
			Sy = var.get_Sy();
			Sz = var.get_Sz();			

			System.out.println("["+i+"]S: "+Sx+" "+Sy+" "+Sz);
		}

		for (int i=5;i<10;i++){

			Db = 1;

			an_2.SetValue(0,0,100);
			an_2.SetValue(0,1,100);
			an_2.SetValue(0,2,100);

			var.integra_rt(dt,an_2,Db);

			Sx = var.get_Sx();
			Sy = var.get_Sy();
			Sz = var.get_Sz();			

			System.out.println("["+i+"]S: "+Sx+" "+Sy+" "+Sz);
		}

		for (int i=10;i<=15;i++){

			Db = 0;

			an_2.SetValue(0,0,100);
			an_2.SetValue(0,1,100);
			an_2.SetValue(0,2,100);

			var.integra_rt(dt,an_2,Db);

			Sx = var.get_Sx();
			Sy = var.get_Sy();
			Sz = var.get_Sz();			

			System.out.println("["+i+"]S: "+Sx+" "+Sy+" "+Sz);
		}
	
	}




}





