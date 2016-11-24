public class media_g_pausa {

	public double g0n[];

	private double A[];

	private int L,aux; 

	public int ready;

	public media_g_pausa()
	{

		this.ready = 0;
		this.aux = 0;
		this.L = 0;

		this.g0n = new double[3];
		this.A = new double[3];

	}


    public double[] g_pausa_rt(int Db, double an[]) {

       	                
		if (Db == 1)
		{
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

		if (Db == 0 && this.aux == 1)
        {       
        

	    	this.g0n[0] = this.A[0]/this.L;
			this.g0n[1] = this.A[1]/this.L;
			this.g0n[2] = this.A[2]/this.L;
            this.ready = 1;
            this.aux = 0;
            this.L = 0;
            this.A[0] = 0;
			this.A[1] = 0;
			this.A[2] = 0;
               
    	}

		return(g0n);

	}



//	public double[] get_g0n(){
//		return(g0n);
//	}



	public int get_ready(){
		return(ready);
	}



	public static void main(String[] args) {

		media_g_pausa var = new media_g_pausa();

		double an[] = new double[3];
		double g0n[] = new double[3];
		
		int ready;

		int Db = 0;

		
		
		for (int i=0;i<100;i++){

			Db = 0;

			an[0] = 15;
			an[1] = 15;
			an[2] = 15;
		
			
			g0n = var.g_pausa_rt(Db,an);
			//g0n = var.get_g0n();
			ready = var.get_ready();
			System.out.println("["+i+"]g0n/ready: "+g0n[0]+" "+g0n[1]+" "+g0n[2]+" "+ready);
		}

		for (int i=100;i<200;i++){

			Db = 1;

			an[0] = 10;
			an[1] = 10;
			an[2] = 10;

			g0n = var.g_pausa_rt(Db,an);
			//g0n = var.get_g0n();
			ready = var.get_ready();
			System.out.println("["+i+"]g0n/ready: "+g0n[0]+" "+g0n[1]+" "+g0n[2]+" "+ready);
		}

		for (int i=200;i<250;i++){

			Db = 0;

			an[0] = 15;
			an[1] = 15;
			an[2] = 15;

			g0n = var.g_pausa_rt(Db,an);
			//g0n = var.get_g0n();
			ready = var.get_ready();
			System.out.println("["+i+"]g0n/ready: "+g0n[0]+" "+g0n[1]+" "+g0n[2]+" "+ready);

		
		}

		for (int i=250;i<300;i++){

			Db = 1;

			an[0] = 20;
			an[1] = 20;
			an[2] = 20;

			g0n = var.g_pausa_rt(Db,an);
			//g0n = var.get_g0n();
			ready = var.get_ready();
			System.out.println("["+i+"]g0n/ready: "+g0n[0]+" "+g0n[1]+" "+g0n[2]+" "+ready);
		}

		for (int i=300;i<350;i++){

			Db = 0;

			an[0] = 15;
			an[1] = 15;
			an[2] = 15;

			g0n = var.g_pausa_rt(Db,an);
			//g0n = var.get_g0n();
			ready = var.get_ready();
			System.out.println("["+i+"]g0n/ready: "+g0n[0]+" "+g0n[1]+" "+g0n[2]+" "+ready);

		
		}


	
	}




}

