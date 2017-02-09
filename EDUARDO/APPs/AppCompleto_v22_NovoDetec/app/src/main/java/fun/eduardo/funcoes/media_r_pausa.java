package fun.eduardo.funcoes;

import net.trucomanx.pdsplibj.pdsra.*;
import net.trucomanx.pdsplibj.pdsdf.*;

public class media_r_pausa {

	private double A[];

	private int aux,L,JJ;	

	public int ready;

	public PdsMatrix ruido0n;

	public PdsFir fx,fy,fz;

	
	public media_r_pausa(String datafile){

		this.A = new double[3];
		
		this.ruido0n = new PdsMatrix(1,1);
		this.L = 0;
		this.aux = 0;
		this.JJ = 0;
		this.ready = 0;

		PdsVector H = new PdsVector(datafile);

		this.fx = new PdsFir(H);
		this.fy = new PdsFir(H);
		this.fz = new PdsFir(H); 

	}	



	public PdsMatrix r_pausa_rt(double Db, PdsMatrix an) {

		double a_nx[];

		a_nx = new double[3]; 
                  
		if (Db == 1)
		{

			a_nx[0] = an.GetValue(0,0) - this.fx.EvaluateValue(an.GetValue(0,0));
			a_nx[1] = an.GetValue(0,1) - this.fy.EvaluateValue(an.GetValue(0,1));
			a_nx[2] = an.GetValue(0,2) - this.fz.EvaluateValue(an.GetValue(0,2));
			
			// IMPLEMENTAR FILTRO

			if (this.JJ < 32)
            {       
            	a_nx[0] = 0;
				a_nx[1] = 0;
				a_nx[2] = 0;
                      
            }

			this.JJ = this.JJ + 1;
			
			a_nx[0] = a_nx[0]*a_nx[0];
			a_nx[1] = a_nx[1]*a_nx[1];
			a_nx[2] = a_nx[2]*a_nx[2];

			this.A[0] = this.A[0] + a_nx[0];
			this.A[1] = this.A[1] + a_nx[1];
			this.A[2] = this.A[2] + a_nx[2];

			this.L = this.L + 1;
			this.aux = 1;
			this.ready = 0;	      

		}


		if (Db == 0 && this.aux == 0)
        {       
        
	        this.ready =0;
                
        }

		if (Db == 0 && aux == 1)
        {       
        
			if (this.L>50)
			{

				this.ruido0n.SetValue(0,0,((Math.sqrt(this.A[0]/this.L))+(Math.sqrt(this.A[1]/this.L))+(Math.sqrt(this.A[2]/this.L)))/3); 
                
				this.ready = 1;
                this.aux = 0;
			}

			if (this.L<=50)
			{

				this.ready = 0;

			}

			this.L = 0;
			this.A[0] = 0;
			this.A[1] = 0;
			this.A[2] = 0;
              
		}         
         
        return(ruido0n);
    }


		public int get_ready(){
		return(ready);
	}

	

	


}

