package fun.eduardo.funcoes;

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


}


