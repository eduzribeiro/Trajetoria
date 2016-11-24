import net.trucomanx.pdsplibj.pdsra.*;
import net.trucomanx.pdsplibj.pdsdf.*;

public class setup_function_rt_ruido_l {

	private double Si[];

	private int i,L,JJ;

	public double ruido0;

	public PdsFir fx,fy,fz;

	public setup_function_rt_media_l(int L, String datafile){

		this.Si = new double[3];
		
		this.i = 0;
		this.L = L;
		this.JJ = 0;

		this.ruido0 = 0;

		PdsVector H = new PdsVector(datafile);

		this.fx = new PdsFir(H);
		this.fy = new PdsFir(H);
		this.fz = new PdsFir(H); 
		

	}	


    public double ruido_de_l(double ar[]) {    

		if (this.i < this.L)
		{

			ar[0] = ar[0] - this.fx.EvaluateValue(ar[0]);
			ar[1] = ar[1] - this.fy.EvaluateValue(ar[1]);
			ar[2] = ar[2] - this.fz.EvaluateValue(ar[2]);
				
			//IMPLEMENTAR FILTRO

			if (this.JJ<32)
			{
		
				ar[0]=0;
				ar[1]=0;
				ar[2]=0;
			}

			this.JJ = this.JJ+1;
			
			ar[0] = ar[0]*ar[0];
			ar[1] = ar[1]*ar[1];
			ar[2] = ar[2]*ar[2];

			this.Si[0] = this.Si[0] + ar[0];
			this.Si[1] = this.Si[1] + ar[1];
			this.Si[2] = this.Si[2] + ar[2];

			this.i = this.i+1;
		}
	
		if (this.i == this.L)
		{

			this.ruido0 = Math.sqrt((this.Si[0]/this.L)+(this.Si[1]/this.L)+(this.Si[2]/this.L));			

		}

		return(ruido0);

    }
}

