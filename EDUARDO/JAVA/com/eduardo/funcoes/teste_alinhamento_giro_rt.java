import net.trucomanx.pdsplibj.pdsra.*;
import net.trucomanx.pdsplibj.pdsextras.*;
//import com.eduardo.funcoes.*;


public class teste_alinhamento_giro_rt{

	
	public static void main(String[] args) {

		//Leitura dos dados		
		
		Ler dados = new Ler("C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\data\\slide4.txt");
		Ler dados_1 = new Ler("C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\data\\slide4.txt");
		Ler DadosAux = new Ler("C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\data\\slide4.txt");
		
		//Variáveis
		
		double Time,TimeAux,Time_1,dt,Db,Sx,Sy,Sz;
		double[] ar,wr;

		PdsMatrix a,g0,w,w0,ruido0,M,a_n,a_n2,ruido_d;

		a = new PdsMatrix(1,3);
		g0 = new PdsMatrix(1,3);
		w = new PdsMatrix(1,3);
		ruido0 = new PdsMatrix(1,1);
		M = new PdsMatrix(3,3);
		a_n = new PdsMatrix(1,3);
		a_n2 = new PdsMatrix(1,3);
		ruido_d = new PdsMatrix(1,1);

		ar = new double [3];
		wr = new double [3];


		Db = 0;
		dt = 0;
		
		//Construtor classes
    
		setup S = new setup(1500);

		matriz_transf_rt MA = new matriz_transf_rt();

		decide_g DEG = new decide_g();

		decide_r DER = new decide_r();

		detectordepausa_rt DETEC = new detectordepausa_rt("C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\JAVA\\CoeficientesFiltros\\ValoresH_so_pausa_fir.dat","C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\JAVA\\CoeficientesFiltros\\ValoresH_so_pausa_media.dat","C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\JAVA\\CoeficientesFiltros\\ValoresH_so_ativo_fir.dat");

		Integrador INT = new Integrador(0);

		//Procedimento

		TimeAux = DadosAux.LerTime();

		for(int i=0;i<2400;i++)
		{
			
			Time = (dados.LerTime())-TimeAux;
			ar[0] = dados.LerAx();
			ar[1] = dados.LerAy();
			ar[2] = dados.LerAz();
			wr[0] = dados.LerWx();
			wr[1] = dados.LerWy();
			wr[2] = dados.LerWz();
			dados.Descarte();

		//Obter dt
			
    	if (i>0)
		{
				
			Time_1 = (dados_1.LerTime())-TimeAux;
			dados_1.Descarte2();
       		dt = (Time-Time_1); 

   		}


		//Processo

		//Inicialização

		ruido0 = S.setup_rt(ar,wr);
		a = S.get_a();
		g0 = S.get_g0();
		w = S.get_w();    	
	
    	//Matriz 
    
		M = MA.matriz_transf(Db,w,dt);
    
    	//Atualizar matriz
     
   		a_n = (M.MulNew(a.TransposeNew())).TransposeNew();

		//Calcular e decidir gravidade
    
    	a_n2 = DEG.decide_g_rt(a_n,g0,Db);
       
    	//Calcular e decidir ruido
    
    	ruido_d = DER.decide_r_rt(a_n,ruido0,Db);
    
    	//Detector de pausa
    
    	Db = DETEC.detector_pausa(a_n2,ruido_d);

		//Integral
    
    	INT.integra_rt(dt,a_n2,Db);

		Sx = INT.get_Sx();
		Sy = INT.get_Sy();
		Sz = INT.get_Sz();			
			
		
		System.out.println(/*"["+i+"]Sx/Sy/Sz: "+*/Sx+" "+Sy+" "+Sz);//+ar[0]+" "+ar[1]+" "+ar[2]+" "+wr[0]+" "+wr[1]+" "+wr[2]);
			
		}

	}
	
}
