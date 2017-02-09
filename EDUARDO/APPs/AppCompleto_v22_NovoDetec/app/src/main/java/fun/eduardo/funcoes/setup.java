package fun.eduardo.funcoes;

import net.trucomanx.pdsplibj.pdsra.*;
//import com.eduardo.funcoes.*;


public class setup {

	public PdsMatrix a,g0,w,w0,ruido0;

	public setup_function_rt_ruido_l RL;

	//ACC
	public setup_function_rt_descartar DES;

	public setup_function_rt_media_l ML;

	//GIRO
	public setup_function_rt_descartar DESG;
	
	public setup_function_rt_media_l MLG;
    

	public setup(int L){

		this.a = new PdsMatrix(1,3);
		this.g0 = new PdsMatrix(1,3);
		this.w = new PdsMatrix(1,3);
		this.w0 = new PdsMatrix(1,3);
		this.ruido0 = new PdsMatrix(1,1);

		this.RL = new setup_function_rt_ruido_l(L,"/sdcard/Download/CoeficientesFiltros/ValoresH_ruido_de_l.dat");

		this.DES = new setup_function_rt_descartar(L);
		this.ML = new setup_function_rt_media_l(L);

		this.DESG = new setup_function_rt_descartar(L);
		this.MLG = new setup_function_rt_media_l(L);


	}

	//ar: input signal  [1x3]
	//wr: input signal  [1x3]
    //return :::
	//ruido0: Ruido dos L primeiros [1x1]	
	//a: Aceleração após descarte dos L primeiros [1x3]        
	//g0: Média dos L primeiros [1x3]
	//w: Girômetro subtraindo os L primeiros [1x3]	

   	public PdsMatrix setup_rt(double ar[], double wr[]) {
   

		//Obtendo dados para calibração inicial do acelerômetro

		this.ruido0 = this.RL.ruido_de_l(ar);
		this.g0 = this.ML.media_de_l(ar);
    	this.a = this.DES.descartar(ar);

		
        //Obtendo dados para calibração inicial do girômetro

    	this.w0 = this.MLG.media_de_l(wr);	
        this.w = this.DESG.descartar(wr);

        //Subtrair w0 de w

        this.w.SetValue(0,0,this.w.GetValue(0,0) - this.w0.GetValue(0,0));
		this.w.SetValue(0,1,this.w.GetValue(0,1) - this.w0.GetValue(0,1));
		this.w.SetValue(0,2,this.w.GetValue(0,2) - this.w0.GetValue(0,2));

		return(ruido0);
		
	}

	public PdsMatrix get_a(){
		return(a);
	}


	public PdsMatrix get_g0(){
		return(g0);
	}


	public PdsMatrix get_w(){
		return(w);
	}

	


}
