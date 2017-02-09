package fun.eduardo.funcoes;

import net.trucomanx.pdsplibj.pdsra.*;
import net.trucomanx.pdsplibj.pdsdf.*;


public class detectordepausa_rt {

	private double fator,U,Tau,Dx,Dy,Dz;

	public double Dsp,Dsa,Db;

		
	//Filtro só pausa
	
	public PdsFir Fx,Fy,Fz;

	public PdsFir FMx,FMy,FMz;


	//Filtro só ativo

	public PdsFir FFx,FFy,FFz;


	public detectordepausa_rt(String datafile_pausa, String datafile_media, String datafile_ativo){

		this.fator = 3;
		this.U = 0.3;
		this.Tau = 0.05; //Valores mais baixos, decaimento mais lento
		this.Dx = 0;
		this.Dy = 0;
		this.Dz = 0;
		this.Dsp = 0;
		this.Dsa = 0;
		this.Db = 0;

		PdsVector H = new PdsVector(datafile_pausa);
		this.Fx = new PdsFir(H);
		this.Fy = new PdsFir(H);
		this.Fz = new PdsFir(H); 

		PdsVector HM = new PdsVector(datafile_media);
		this.FMx = new PdsFir(HM);
		this.FMy = new PdsFir(HM);
		this.FMz = new PdsFir(HM); 

		PdsVector H1 = new PdsVector(datafile_ativo);
		this.FFx = new PdsFir(H1);
		this.FFy = new PdsFir(H1);
		this.FFz = new PdsFir(H1); 

	}	

	//an: input signal  [1x3]
    //nivel_ruido: calculado nas outras funções [1x1]
    //return :::
    //Db: Pausa detectada [1] ou Pausa não detectada [0] 
        



	public void detector_so_pausa(PdsMatrix a_n2, PdsMatrix ruido) 
	{

		double a_n3[],a_n4[],a_n5[],D[];

		a_n3 = new double[3];
		a_n4 = new double[3];
		a_n5 = new double[3];
		D = new double[3];

		a_n3[0] = this.Fx.EvaluateValue(a_n2.GetValue(0,0));
		a_n3[1] = this.Fy.EvaluateValue(a_n2.GetValue(0,1));
		a_n3[2] = this.Fz.EvaluateValue(a_n2.GetValue(0,2));

		a_n4[0] = Math.abs(a_n3[0]);
		a_n4[1] = Math.abs(a_n3[1]);
		a_n4[2] = Math.abs(a_n3[2]);
					
		a_n5[0] = this.FMx.EvaluateValue(a_n4[0]);
		a_n5[1] = this.FMy.EvaluateValue(a_n4[1]);
		a_n5[2] = this.FMz.EvaluateValue(a_n4[2]);

		if (a_n5[0]<=this.fator*ruido.GetValue(0,0)) {

			D[0]=1;

		} else {

			D[0]=0;

		}

		if (a_n5[1]<=this.fator*ruido.GetValue(0,0)) {

			D[1]=1;

		} else {

			D[1]=0;

		}

		if (a_n5[2]<=this.fator*ruido.GetValue(0,0)) {

			D[2]=1;

		} else {

			D[2]=0;

		}		

		
		if ((D[0]+D[1]+D[2])==3) {

			this.Dsp=1;

		} else {

			this.Dsp=0;

		}	            
                         
             
	}

	
	public void detector_so_ativo(PdsMatrix a_n2, PdsMatrix ruido) 
	{

		double a_x[],a_x2[],D2[];

		a_x = new double[3];
		a_x2 = new double[3];
		D2 = new double[3];

		a_x[0] = a_n2.GetValue(0,0) - this.FFx.EvaluateValue(a_n2.GetValue(0,0));
		a_x[1] = a_n2.GetValue(0,1) - this.FFy.EvaluateValue(a_n2.GetValue(0,1));
		a_x[2] = a_n2.GetValue(0,2) - this.FFz.EvaluateValue(a_n2.GetValue(0,2));

		a_x2[0] = Math.abs(a_x[0]);
		a_x2[1] = Math.abs(a_x[1]);
		a_x2[2] = Math.abs(a_x[2]);
		

		if (a_x2[0]>=this.fator*ruido.GetValue(0,0)) {

			D2[0]=1;

		} else {

			D2[0]=0;

		}

		if (a_x2[1]>=this.fator*ruido.GetValue(0,0)) {

			D2[1]=1;

		} else {

			D2[1]=0;

		}

		if (a_x2[2]>=this.fator*ruido.GetValue(0,0)) {

			D2[2]=1;

		} else {

			D2[2]=0;

		}		
		

		if (D2[0] == 0)
		{
			D2[0] = this.Dx*Math.exp(-this.Tau); //VERIFICAR EXPONENCIAL
		}

		this.Dx = D2[0];
        

		if (D2[1] == 0)
		{
			D2[1] = this.Dy*Math.exp(-this.Tau); //VERIFICAR EXPONENCIAL
		}

		this.Dy = D2[1];

		
		if (D2[2] == 0)
		{
			D2[2] = this.Dz*Math.exp(-this.Tau); //VERIFICAR EXPONENCIAL
		}

		this.Dz = D2[2];

		
		if ((this.Dx>this.U) || (this.Dy>this.U) || (this.Dz>this.U))
		{
			this.Dsa = 1;
		
		} else {
			
			this.Dsa = 0;

		}               

	}


   	public double detector_pausa(PdsMatrix a_n2, PdsMatrix ruido) { 

		this.detector_so_pausa(a_n2,ruido);
		this.detector_so_ativo(a_n2,ruido);  

		if (this.Dsp == 1 && this.Dsa == 0)
		{

			this.Db = 1;

		} else {

			this.Db = 0;

		}

		return(Db);
	}


	
	

}

