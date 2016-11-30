import net.trucomanx.pdsplibj.pdsra.*;

public class setup_function_rt_media_l {

	private double Si[];

	public PdsMatrix g0;

	private int i,L;


	public setup_function_rt_media_l(int L){

		this.Si = new double[3];
		this.g0 = new PdsMatrix(1,3);

		this.i = 0;

		this.L = L;


	}	

	
	//ar: input signal  [1x3]
    //return :::
	//g0: A media dos L primeiros [1x3]

   	public PdsMatrix media_de_l(double ar[]) {   

	
		if(this.i<(this.L))
		{
       
   	        this.Si[0] = this.Si[0] + ar[0];               
			this.Si[1] = this.Si[1] + ar[1];               
			this.Si[2] = this.Si[2] + ar[2];

			this.i = this.i+1;                
        	                  
		}
            
        if(this.i == (this.L))
        {      
            this.g0.SetValue(0,0,this.Si[0]/L);
			this.g0.SetValue(0,1,this.Si[1]/L);
			this.g0.SetValue(0,2,this.Si[2]/L);
                
        }

	
        return(g0);
	}

	//public PdsMatrix get_g0(){
	//	return (g0);
	//}


	public static void main(String[] args) {

		setup_function_rt_media_l var = new setup_function_rt_media_l(1500);

		double ar[] = new double[3];
		PdsMatrix g0 = new PdsMatrix(1,3);

		for (int i=0;i<1520;i++){

			ar[0] = 5;//10+0.05*Math.sin(i*25*2*Math.PI/1000);
			ar[1] = 15;//ar[0];
			ar[2] = 30;//ar[1];
			g0=var.media_de_l(ar);
			System.out.println("["+i+"]go: "+g0);
		}
	
	}


}


