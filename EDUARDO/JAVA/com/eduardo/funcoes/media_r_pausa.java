import net.trucomanx.pdsplibj.pdsra.*;
import net.trucomanx.pdsplibj.pdsdf.*;

public class media_r_pausa {

	private double A[];

	private int aux,L,JJ;	

	public int ready;

	public double ruido0n;

	public PdsFir fx,fy,fz;

	
	public media_r_pausa(String datafile){

		this.A = new double[3];
		
		this.ruido0n = 0;
		this.L = 0;
		this.aux = 0;
		this.JJ = 0;
		this.ready = 0;

		PdsVector H = new PdsVector(datafile);

		this.fx = new PdsFir(H);
		this.fy = new PdsFir(H);
		this.fz = new PdsFir(H); 

	}	



	public double r_pausa_rt(int Db, double an[]) {

                  
		if (Db == 1)
		{

			an[0] = an[0] - this.fx.EvaluateValue(an[0]);
			an[1] = an[1] - this.fy.EvaluateValue(an[1]);
			an[2] = an[2] - this.fz.EvaluateValue(an[2]);
			
			// IMPLEMENTAR FILTRO

			if (this.JJ < 32)
            {       
            	an[0] = 0;
				an[1] = 0;
				an[2] = 0;
                      
            }

			this.JJ = this.JJ + 1;
			
			an[0] = an[0]*an[0];
			an[1] = an[1]*an[1];
			an[2] = an[2]*an[2];

			this.A[0] = this.A[0] + an[0];
			this.A[1] = this.A[1] + an[1];
			this.A[2] = this.A[2] + an[2];

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

				this.ruido0n = ((Math.sqrt(this.A[0]/this.L))+(Math.sqrt(this.A[1]/this.L))+(Math.sqrt(this.A[2]/this.L)))/3; 
                
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

