import net.trucomanx.pdsplibj.pdsra.*;
import net.trucomanx.pdsplibj.pdsdf.*;

public class setup_function_rt_ruido_l {

	private double Si[];

	private int i,L,JJ;

	public PdsMatrix ruido0;

	public PdsFir fx,fy,fz;

	public setup_function_rt_ruido_l(int L, String datafile){

		this.Si = new double[3];
		
		this.i = 0;
		this.L = L;
		this.JJ = 0;

		this.ruido0 = new PdsMatrix(1,1);

		PdsVector H = new PdsVector(datafile);

		this.fx = new PdsFir(H);
		this.fy = new PdsFir(H);
		this.fz = new PdsFir(H); 
		

	}	


	//ar: input signal  [3x1]
    //return :::
    //ruido0: Ruido dos L primeiros [1x1]


    public PdsMatrix ruido_de_l(double ar[]) { 

		double a_nx[];

		a_nx = new double[3];   

		if (this.i < this.L)
		{

			a_nx[0] = ar[0] - this.fx.EvaluateValue(ar[0]);
			a_nx[1] = ar[1] - this.fy.EvaluateValue(ar[1]);
			a_nx[2] = ar[2] - this.fz.EvaluateValue(ar[2]);
				
			//IMPLEMENTAR FILTRO

			if (this.JJ<32)
			{
		
				a_nx[0]=0;
				a_nx[1]=0;
				a_nx[2]=0;
			}

			this.JJ = this.JJ+1;
			
			a_nx[0] = a_nx[0]*a_nx[0];
			a_nx[1] = a_nx[1]*a_nx[1];
			a_nx[2] = a_nx[2]*a_nx[2];

			this.Si[0] = this.Si[0] + a_nx[0];
			this.Si[1] = this.Si[1] + a_nx[1];
			this.Si[2] = this.Si[2] + a_nx[2];

			this.i = this.i+1;
		}
	
		if (this.i == this.L)
		{

			this.ruido0.SetValue(0,0,Math.sqrt((this.Si[0]/this.L)+(this.Si[1]/this.L)+(this.Si[2]/this.L)));			

		}

		return(ruido0);

    }


	public static void main(String[] args) {

		setup_function_rt_ruido_l var = new setup_function_rt_ruido_l(1500,"C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\JAVA\\CoeficientesFiltros\\ValoresH_ruido_de_l.dat");

		double ar[] = new double[3];
		PdsMatrix ruido0 = new PdsMatrix(1,1);


		for (int i=0;i<1520;i++){

			ar[0] = i/2;
			ar[1] = i-1;
			ar[2] = i;			
			
			ruido0=var.ruido_de_l(ar);
			System.out.println("["+i+"]ruido0: "+ruido0);
		}
	
	}



}

