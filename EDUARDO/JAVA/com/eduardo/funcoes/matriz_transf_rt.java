import net.trucomanx.pdsplibj.pdsra.*;

public class matriz_transf_rt {

	private double ew[];

	//public double R[][];

	//public double M[][];

	private double theta,mod_w;

	public PdsMatrix M,R;

	public matriz_transf_rt(){

		this.ew = new double[3];
		
		this.M = new PdsMatrix(3,3);
		this.R = new PdsMatrix(3,3);

		this.M.SetValue(0,0,1);
		this.M.SetValue(0,1,0);
		this.M.SetValue(0,2,0);

		this.M.SetValue(1,0,0);
		this.M.SetValue(1,1,1);
		this.M.SetValue(1,2,0);

		this.M.SetValue(2,0,0);
		this.M.SetValue(2,1,0);
		this.M.SetValue(2,2,1);

		//this.R = new double[3][3];
		//this.M = new double[3][3];

		//this.M[0][0] = 1;
		//this.M[0][1] = 0;
		//this.M[0][2] = 0;

		//this.M[1][0] = 0;
		//this.M[1][1] = 1;
		//this.M[1][2] = 0;

		//this.M[2][0] = 0;
		//this.M[2][1] = 0;
		//this.M[2][2] = 1;

		this.theta = 0;
		this.mod_w = 0;


	}	

	public void matriz_r(double w[], double dt) {
        
		this.mod_w = Math.sqrt((w[0]*w[0])+(w[1]*w[1])+(w[2]*w[2]));

		if(this.mod_w>0)
		{
                
                this.ew[0] = w[0]/(this.mod_w); %ewx
                this.ew[1] = w[1]/(this.mod_w); %ewy
                this.ew[2] = w[2]/(this.mod_w); %ewz
            
                       
            //Obter theta
    
            this.theta = this.mod_w*dt;

			this.R.SetValue(0,0,(Math.cos(this.theta))+((this.ew[0]*this.ew[0])*(1-Math.cos(this.theta))));
			this.R.SetValue(0,1,(this.ew[0]*this.ew[1]*(1-Math.cos(this.theta)))-(this.ew[2]*Math.sin(this.theta)));
			this.R.SetValue(0,2,(this.ew[0]*this.ew[2]*(1-Math.cos(this.theta)))+(this.ew[1]*Math.sin(this.theta)));

			this.R.SetValue(1,0,(this.ew[1]*this.ew[0]*(1-Math.cos(this.theta)))+(this.ew[2]*Math.sin(this.theta)));
			this.R.SetValue(1,1,(Math.cos(this.theta))+((this.ew[1]*this.ew[1])*(1-Math.cos(this.theta))));
			this.R.SetValue(1,2,(this.ew[1]*this.ew[2]*(1-Math.cos(this.theta)))-(this.ew[0]*Math.sin(this.theta)));

			this.R.SetValue(2,0,(this.ew[2]*this.ew[0]*(1-Math.cos(this.theta)))-(this.ew[1]*Math.sin(this.theta)));
			this.R.SetValue(2,1,(this.ew[2]*this.ew[1]*(1-Math.cos(this.theta)))+(this.ew[0]*Math.sin(this.theta)));
			this.R.SetValue(2,2,(Math.cos(this.theta))+((this.ew[2]*this.ew[2])*(1-Math.cos(this.theta))));

			//this.R[0][0] = (Math.cos(this.theta))+((this.ew[0]*this.ew[0])*(1-Math.cos(this.theta)));
        	//this.R[0][1] = (this.ew[0]*this.ew[1]*(1-Math.cos(this.theta)))-(this.ew[2]*Math.sin(this.theta));
        	//this.R[0][2] = (this.ew[0]*this.ew[2]*(1-Math.cos(this.theta)))+(this.ew[1]*Math.sin(this.theta));
        	
			//this.R[1][0] = (this.ew[1]*this.ew[0]*(1-Math.cos(this.theta)))+(this.ew[2]*Math.sin(this.theta));
        	//this.R[1][1] = (Math.cos(this.theta))+((this.ew[1]*this.ew[1])*(1-Math.cos(this.theta)));
        	//this.R[1][2] = (this.ew[1]*this.ew[2]*(1-Math.cos(this.theta)))-(this.ew[0]*Math.sin(this.theta));
        	
			//this.R[2][0] = (this.ew[2]*this.ew[0]*(1-Math.cos(this.theta)))-(this.ew[1]*Math.sin(this.theta));
        	//this.R[2][1] = (this.ew[2]*this.ew[1]*(1-Math.cos(this.theta)))+(this.ew[0]*Math.sin(this.theta));
        	//this.R[2][2] = (Math.cos(this.theta))+((this.ew[2]*this.ew[2])*(1-Math.cos(this.theta)));
            
		}

		if(this.mod_w<=0)
		{

			this.R.SetValue(0,0,1);
			this.R.SetValue(0,1,0);
			this.R.SetValue(0,2,0);

			this.R.SetValue(1,0,0);
			this.R.SetValue(1,1,1);
			this.R.SetValue(1,2,0);

			this.R.SetValue(2,0,0);
			this.R.SetValue(2,1,0);
			this.R.SetValue(2,2,1);

			//this.R[0][0] = 1;
			//this.R[0][1] = 0;
			//this.R[0][2] = 0;

			//this.R[1][0] = 0;
			//this.R[1][1] = 1;
			//this.R[1][2] = 0;

			//this.R[2][0] = 0;
			//this.R[2][1] = 0;
			//this.R[2][2] = 1;		

		}


    }


	public double[][] matriz_transf(int DB) { 

		if(DB==1)
		{
			this.M.SetValue(0,0,1);
			this.M.SetValue(0,1,0);
			this.M.SetValue(0,2,0);

			this.M.SetValue(1,0,0);
			this.M.SetValue(1,1,1);
			this.M.SetValue(1,2,0);

			this.M.SetValue(2,0,0);
			this.M.SetValue(2,1,0);
			this.M.SetValue(2,2,1);			

			//this.M[0][0] = 1;
			//this.M[0][1] = 0;
			//this.M[0][2] = 0;

			//this.M[1][0] = 0;
			//this.M[1][1] = 1;
			//this.M[1][2] = 0;

			//this.M[2][0] = 0;
			//this.M[2][1] = 0;
			//this.M[2][2] = 1;

		}

		if (DB!=1)
		{

			//this.M = this.M*this.R;

			this.M = this.M.MulNew(this.R);
		}

	
		return(M);

	}
   	



}
