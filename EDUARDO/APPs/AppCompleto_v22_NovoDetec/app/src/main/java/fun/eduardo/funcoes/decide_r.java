package fun.eduardo.funcoes;

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
		this.MRP = new media_r_pausa("/sdcard/Download/CoeficientesFiltros/ValoresH_media_r_pausa.dat");

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

	
		
}
