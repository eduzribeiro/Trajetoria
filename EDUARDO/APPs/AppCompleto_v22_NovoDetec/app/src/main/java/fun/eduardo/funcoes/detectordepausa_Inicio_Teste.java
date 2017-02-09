package fun.eduardo.funcoes;

import net.trucomanx.pdsplibj.pdsdf.PdsFir;
import net.trucomanx.pdsplibj.pdsra.*;

public class detectordepausa_Inicio_Teste {

	private double Pi[], Pi2[], D[], y[], alfa;

	public PdsMatrix ap, aa;

	public PdsFir FMx,FMy,FMz,FMx2,FMy2,FMz2;

	public double Db2;


	public detectordepausa_Inicio_Teste(String datafile_media){


		PdsVector HM = new PdsVector(datafile_media);
		this.FMx = new PdsFir(HM);
		this.FMy = new PdsFir(HM);
		this.FMz = new PdsFir(HM);

		PdsVector HM2 = new PdsVector(datafile_media);
		this.FMx2 = new PdsFir(HM2);
		this.FMy2 = new PdsFir(HM2);
		this.FMz2 = new PdsFir(HM2);

		this.Pi = new double[3];
		this.Pi2 = new double[3];
		this.D = new double[3];
		this.y = new double[3];

		this.ap = new PdsMatrix(1,3);
		this.aa = new PdsMatrix(1,3);

		this.alfa = 1.5;


	}	

	
	//ar: input signal  [1x3]
    //return :::
	//g0: A media dos L primeiros [1x3]

   	public double detector_inicio(PdsMatrix a, PdsMatrix ruido0) {


		Pi[0] = this.FMx.EvaluateValue(a.GetValue(0,0));
		Pi[1] = this.FMy.EvaluateValue(a.GetValue(0,1));
		Pi[2] = this.FMz.EvaluateValue(a.GetValue(0,2));


		this.aa.SetValue(0,0,a.GetValue(0,0) - Pi[0]);
		this.aa.SetValue(0,1,a.GetValue(0,1) - Pi[1]);
		this.aa.SetValue(0,2,a.GetValue(0,2) - Pi[2]);


		Pi2[0] = Math.abs(aa.GetValue(0,0));
		Pi2[1] = Math.abs(aa.GetValue(0,1));
		Pi2[2] = Math.abs(aa.GetValue(0,2));


		y[0] = this.FMx2.EvaluateValue(Pi2[0]);
		y[1] = this.FMy2.EvaluateValue(Pi2[1]);
		y[2] = this.FMz2.EvaluateValue(Pi2[2]);


		if (y[0]<=this.alfa*ruido0.GetValue(0,0)) {

			D[0]=1;

		} else {

			D[0]=0;

		}

		if (y[1]<=this.alfa*ruido0.GetValue(0,0)) {

			D[1]=1;

		} else {

			D[1]=0;

		}

		if (y[2]<=this.alfa*ruido0.GetValue(0,0)) {

			D[2]=1;

		} else {

			D[2]=0;

		}


		if ((D[0]+D[1]+D[2])==3) {

			this.Db2=1;

		} else {

			this.Db2=0;

		}


		return(Db2);
	}

	//public PdsMatrix get_g0(){
	//	return (g0);
	//}


	
}

