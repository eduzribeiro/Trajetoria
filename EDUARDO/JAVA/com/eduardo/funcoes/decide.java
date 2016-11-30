import net.trucomanx.pdsplibj.pdsra.*;

public class decide {

	public PdsMatrix Xd;

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

			this.Xd = new PdsMatrix(1,1);

		}
         
		if (this.tipo == 2) // Bloco decide gravidade
		{

			this.Xd = new PdsMatrix(1,3);

		}   
                
                
	}


	//x0: input signal  [1x3] ou [1x1]       
    //x0n: input signal  [1x3] ou [1x1]
    //Ready: novo valor de x pronto para ser utilizado [0 (não pronto) ou 1 (pronto)]
    //return :::
    //Xd: X decidido [1x3] ou [1x1]


    public PdsMatrix decide_rt(PdsMatrix x0, PdsMatrix x0n, int ready) {
	
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

		PdsMatrix x0 = new PdsMatrix(1,3);
		PdsMatrix x0n = new PdsMatrix(1,3);
		PdsMatrix Xd = new PdsMatrix(1,3);

		int ready = 0;

		x0.SetValue(0,0,25);
		x0.SetValue(0,1,15);
		x0.SetValue(0,2,35);
		x0n.SetValue(0,0,60);
		x0n.SetValue(0,1,50);
		x0n.SetValue(0,2,20);

		for (int i=0;i<100;i++){

			ready = 0;
		
			
			Xd=var.decide_rt(x0,x0n,ready);
			System.out.println("["+i+"]Xd: "+Xd);
		}

		for (int i=100;i<=125;i++){

			ready = 1;
		
			
			Xd=var.decide_rt(x0,x0n,ready);
			System.out.println("["+i+"]Xd: "+Xd);
		}

	
	}

}


