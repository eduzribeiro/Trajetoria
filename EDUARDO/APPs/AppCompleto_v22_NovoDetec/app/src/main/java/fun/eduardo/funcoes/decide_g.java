package fun.eduardo.funcoes;

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

	
	
}
