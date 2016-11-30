import net.trucomanx.pdsplibj.pdsra.*;
//import com.eduardo.funcoes.*;


public class decide_g {

	    
	public PdsMatrix a_n2,g0n,g_d;

	public int ready;

	
	//DECIDE
	public decide DG;

	//MEDIA
	public media_g_pausa MGP;
	
	
	public decide_g(){

		this.a_n2 = new PdsMatrix(1,3);
		this.g0n = new PdsMatrix(1,3);
		this.g_d = new PdsMatrix(1,3);
		this.ready = 0;

		this.DG = new decide(2);
		this.MGP = new media_g_pausa();

	}

	//a_n: input signal  [1x3]
	//g0: input signal  [1x3]
    //Db: input signal  [0 ou 1]
    //return :::
	//a_n2: Aceleração subtraida a gravidade decidida [1x3]

   	public PdsMatrix decide_g_rt(PdsMatrix a_n, PdsMatrix g0, double Db) {
   
		//Obtendo gravidade pausa detectada

		this.g0n = this.MGP.g_pausa_rt(Db,a_n);
		this.ready = this.MGP.get_ready();

		//Decidir

		this.g_d = this.DG.decide_rt(g0,this.g0n,this.ready);
    	
        //Subtrair a_n de g_d

        this.a_n2.SetValue(0,0,a_n.GetValue(0,0) - this.g_d.GetValue(0,0));
		this.a_n2.SetValue(0,1,a_n.GetValue(0,1) - this.g_d.GetValue(0,1));
		this.a_n2.SetValue(0,2,a_n.GetValue(0,2) - this.g_d.GetValue(0,2));

		return(a_n2);
		
	}

	public static void main(String[] args) {

		decide_g var = new decide_g();

		PdsMatrix a_n,g0,a_n2;

		double Db;

		a_n = new PdsMatrix(1,3);
		g0 = new PdsMatrix(1,3);
		a_n2 = new PdsMatrix(1,3);
		

		for (int i=0;i<15;i++){

			Db = 0;

			a_n.SetValue(0,0,15);
			a_n.SetValue(0,1,15);
			a_n.SetValue(0,2,15);

			g0.SetValue(0,0,10);
			g0.SetValue(0,1,10);
			g0.SetValue(0,2,10);
			
			a_n2=var.decide_g_rt(a_n,g0,Db);
						
			System.out.println("["+i+"]a_n2: "+a_n2);
		}

		
		for (int i=15;i<70;i++){

			Db = 1;

			a_n.SetValue(0,0,20);
			a_n.SetValue(0,1,20);
			a_n.SetValue(0,2,20);

			g0.SetValue(0,0,10);
			g0.SetValue(0,1,10);
			g0.SetValue(0,2,10);
			
			a_n2=var.decide_g_rt(a_n,g0,Db);
						
			System.out.println("["+i+"]a_n2: "+a_n2);
		}

		
		for (int i=70;i<90;i++){

			Db = 0;

			a_n.SetValue(0,0,45);
			a_n.SetValue(0,1,45);
			a_n.SetValue(0,2,45);

			g0.SetValue(0,0,10);
			g0.SetValue(0,1,10);
			g0.SetValue(0,2,10);
			
			a_n2=var.decide_g_rt(a_n,g0,Db);
						
			System.out.println("["+i+"]a_n2: "+a_n2);
		}

			
	}



	
}
