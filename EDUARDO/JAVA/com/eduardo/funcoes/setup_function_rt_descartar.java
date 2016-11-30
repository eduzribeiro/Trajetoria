import net.trucomanx.pdsplibj.pdsra.*;

public class setup_function_rt_descartar {


	public PdsMatrix a;


	private int i,L;


	public setup_function_rt_descartar(int L){

	
		this.a = new PdsMatrix(1,3);

		this.i = 0;

		this.L = L;	


	}


	//ar: input signal  [1x3]
    //return :::
    //a: Sinal retornado [1x3]

	public PdsMatrix descartar(double ar[]) {

       		      
		if (this.i < this.L)
		{
       
        	this.i = this.i + 1;                
                          
                
         }
            
         if(this.i >= this.L)
         {      
            this.a.SetValue(0,0,ar[0]);
			this.a.SetValue(0,1,ar[1]);
			this.a.SetValue(0,2,ar[2]);
                
         }

	
        return(a);

	}

	public static void main(String[] args) {

		setup_function_rt_descartar var = new setup_function_rt_descartar(1500);

		double ar[] = new double[3];
		PdsMatrix a = new PdsMatrix(1,3);

		for (int i=0;i<1520;i++){

			ar[0] = 5;//10+0.05*Math.sin(i*25*2*Math.PI/1000);
			ar[1] = 2;
			ar[2] = 7;

			a=var.descartar(ar);

			System.out.println("["+i+"]a: "+a);
		}


    }
}

