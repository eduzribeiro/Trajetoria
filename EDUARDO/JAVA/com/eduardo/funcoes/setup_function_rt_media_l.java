

public class setup_function_rt_media_l {

	private double Si[];

	public double g0[];

	private int i,L;


	public setup_function_rt_media_l(int L){

		this.Si = new double[3];
		this.g0 = new double[3];

		this.i = 0;

		this.L = L;


	}	

   	public double[] media_de_l(double ar[]) {   

	
		if(this.i<(this.L))
		{
       
   	        this.Si[0] = this.Si[0] + ar[0];               
			this.Si[1] = this.Si[1] + ar[1];               
			this.Si[2] = this.Si[2] + ar[2];

			this.i = this.i+1;                
        	                  
		}
            
        if(this.i == (this.L))
        {      
            this.g0[0] = this.Si[0]/L;
			this.g0[1] = this.Si[1]/L;
			this.g0[2] = this.Si[2]/L;
                
        }

	
        return(g0);
	}

	public double[] get_g0(){
		return this.g0;
	}


	public static void main(String[] args) {

		setup_function_rt_media_l var = new setup_function_rt_media_l(1000);

		double ar[] = new double[3];
		double g0[] = new double[3];

		for (int i=0;i<1010;i++){

			ar[0] = 10+0.05*Math.sin(i*25*2*Math.PI/1000);
			ar[1] = ar[0];
			ar[2] = ar[1];
			g0=var.media_de_l(ar);
			System.out.println("["+i+"]go: "+g0[0]+" "+g0[1]+" "+g0[2]);
		}
	
	}


}


