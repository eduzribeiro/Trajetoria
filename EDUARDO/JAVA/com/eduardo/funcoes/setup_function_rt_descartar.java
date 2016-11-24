public class setup_function_rt_descartar {


	public double a[];


	private int i,L;


	public setup_function_rt_descartar(int L){

	
		this.a = new double[3];

		this.i = 0;

		this.L = L;	


	}



	public double[] descartar(double ar[]) {

       		      
		if (this.i < this.L)
		{
       
        	this.i = this.i + 1;                
                          
                
         }
            
         if(this.i >= this.L)
         {      
            this.a[0] = ar[0];
			this.a[1] = ar[1];
			this.a[2] = ar[2];
                
         }

	
        return(a);

	}

	public static void main(String[] args) {

		setup_function_rt_descartar var = new setup_function_rt_descartar(1000);

		double ar[] = new double[3];
		double a[] = new double[3];

		for (int i=0;i<1010;i++){

			ar[0] = 10+0.05*Math.sin(i*25*2*Math.PI/1000);
			ar[1] = ar[0];
			ar[2] = ar[1];
			a=var.descartar(ar);
			System.out.println("["+i+"]a: "+a[0]+" "+a[1]+" "+a[2]);
		}


    }
}

