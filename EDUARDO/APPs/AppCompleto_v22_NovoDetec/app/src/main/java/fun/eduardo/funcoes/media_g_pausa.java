package fun.eduardo.funcoes;

import net.trucomanx.pdsplibj.pdsra.*;


public class media_g_pausa {

	public PdsMatrix g0n;

	private double A[];

	private int L,aux; 

	public int ready;

	public media_g_pausa()
	{

		this.ready = 0;
		this.aux = 0;
		this.L = 0;

		this.g0n = new PdsMatrix(1,3);
		this.A = new double[3];

	}


	 //an: input signal  [1x3]
     //Db: detector de pausa [0 (não pausa) ou 1 (pausa)]
     //return :::
     //g0n: Nova média calculado enquanto esteve em pausa [1x3]
     //ready: Novo dado pronto [1] ou não [0] para ser utilizado


    public PdsMatrix g_pausa_rt(double Db, PdsMatrix an) {

       	                
		if (Db == 1)
		{
			this.A[0] = this.A[0] + an.GetValue(0,0);               
			this.A[1] = this.A[1] + an.GetValue(0,1);               
			this.A[2] = this.A[2] + an.GetValue(0,2);                
                
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
        

			if (this.L>50)
			{
			
				this.g0n.SetValue(0,0,this.A[0]/this.L);
				this.g0n.SetValue(0,1,this.A[1]/this.L);
				this.g0n.SetValue(0,2,this.A[2]/this.L);
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

		return(g0n);

	}



//	public double[] get_g0n(){
//		return(g0n);
//	}



	public int get_ready(){
		return(ready);
	}



	



}

