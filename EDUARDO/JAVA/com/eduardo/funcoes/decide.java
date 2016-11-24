public class decide {

	public double Xd[];

	private int aux,tipo;



	public decide(int tipo)
	{

	
		this.tipo = tipo;

		this.aux =0;

		//Type:
		//1:      ruido = 0;
       	//2:      gravidade = [0 0 0];

		if (this.tipo == 1) // Bloco decide ruído
		{

			this.Xd = new double[1];

		}
         
		if (this.tipo == 2) // Bloco decide gravidade
		{

			this.Xd = new double[3];

		}   
                
                
	}


    public double[] decide_rt(double x0[], double x0n[], int ready) {
	
		if (ready==0 && this.aux == 0)
		{

			this.Xd = x0; 

		}		


		if (ready==1)
		{

			this.Xd = x0n;
			this.aux = 1;	

		}

		                     
		return(Xd);

    }


	public static void main(String[] args) {

		decide var = new decide(2);

		double x0[] = new double[3];
		double x0n[] = new double[3];
		double Xd[] = new double[3];

		int ready = 0;

		x0[0] = 15;
		x0[1] = 15;
		x0[2] = 15;
		x0n[0] = 30;
		x0n[1] = 30;
		x0n[2] = 30;

		for (int i=0;i<100;i++){

			ready = 0;
		
			
			Xd=var.decide_rt(x0,x0n,ready);
			System.out.println("["+i+"]Xd: "+Xd[0]+" "+Xd[1]+" "+Xd[2]);
		}

		for (int i=100;i<=125;i++){

			ready = 1;
		
			
			Xd=var.decide_rt(x0,x0n,ready);
			System.out.println("["+i+"]Xd: "+Xd[0]+" "+Xd[1]+" "+Xd[2]);
		}

	
	}

}


