import net.trucomanx.pdsplibj.pdsra.*;
//import com.eduardo.funcoes.*;


public class decide_r {

	    
	public PdsMatrix ruido0n,ruido_d;

	public int ready;

	
	//DECIDE
	public decide DR;

	//MEDIA
	public media_r_pausa MRP;
	
	
	public decide_r(){

		this.ruido0n = new PdsMatrix(1,1);
		this.ruido_d = new PdsMatrix(1,1);
		this.ready = 0;

		this.DR = new decide(1);
		this.MRP = new media_r_pausa("C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\JAVA\\CoeficientesFiltros\\ValoresH_media_r_pausa.dat");

	}

	//a_n: input signal  [1x3]
	//ruido0: input signal  [1x1]
    //Db: input signal  [0 ou 1]
    //return :::
	//ruido_d: Ruido decidido [1x1]

   	public PdsMatrix decide_r_rt(PdsMatrix a_n, PdsMatrix ruido0, double Db) {
   
		//Obtendo ruido pausa detectada

		this.ruido0n = this.MRP.r_pausa_rt(Db,a_n);
		this.ready = this.MRP.get_ready();

		//Decidir

		this.ruido_d = this.DR.decide_rt(ruido0,this.ruido0n,this.ready);
    	

		return(ruido_d);
		
	}

	
		public static void main(String[] args) {

		decide_r var = new decide_r();

		PdsMatrix a_n,ruido0,ruido_d;

		double Db;

		a_n = new PdsMatrix(1,3);
		ruido0 = new PdsMatrix(1,1);
		ruido_d = new PdsMatrix(1,1);
		

		for (int i=0;i<15;i++){

			Db = 0;

			a_n.SetValue(0,0,15);
			a_n.SetValue(0,1,15);
			a_n.SetValue(0,2,15);

			ruido0.SetValue(0,0,10);
			ruido0.SetValue(0,1,10);
			ruido0.SetValue(0,2,10);
			
			ruido_d=var.decide_r_rt(a_n,ruido0,Db);
						
			System.out.println("["+i+"]ruido_d: "+ruido_d);
		}

		
		for (int i=15;i<100;i++){

			Db = 1;

			a_n.SetValue(0,0,20);
			a_n.SetValue(0,1,20);
			a_n.SetValue(0,2,20);

			ruido0.SetValue(0,0,10);
			ruido0.SetValue(0,1,10);
			ruido0.SetValue(0,2,10);
			
			ruido_d=var.decide_r_rt(a_n,ruido0,Db);
						
			System.out.println("["+i+"]ruido_d: "+ruido_d);
		}

		
		for (int i=100;i<115;i++){

			Db = 0;

			a_n.SetValue(0,0,45);
			a_n.SetValue(0,1,45);
			a_n.SetValue(0,2,45);

			ruido0.SetValue(0,0,10);
			ruido0.SetValue(0,1,10);
			ruido0.SetValue(0,2,10);
			
			ruido_d=var.decide_r_rt(a_n,ruido0,Db);
						
			System.out.println("["+i+"]ruido_d: "+ruido_d);
		}

			
	}




	
}
