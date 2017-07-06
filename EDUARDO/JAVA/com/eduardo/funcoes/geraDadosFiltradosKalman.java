import net.trucomanx.pdsplibj.pdsra.*;
import net.trucomanx.pdsplibj.pdsdf.*;
import net.trucomanx.pdsplibj.pdsextras.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;
import java.io.File;
import java.io.IOException;


public class geraDadosFiltradosKalman{

		String Path,PathDestino,L;
		double t2,X2,Y2,Z2,GX2,GY2,GZ2,XF,YF,ZF,GXF,GYF,GZF;
		PdsReadData R;
		PdsWriteData W;
		PdsKalman1D filtroX,filtroY,filtroZ,filtroGX,filtroGY,filtroGZ;
		


	public geraDadosFiltradosKalman(String path, String path2){

		this.Path = path;
		this.PathDestino = path2;
		this.R = new PdsReadData(this.Path);		
		this.W = new PdsWriteData(this.PathDestino);
		
		this.filtroX = new PdsKalman1D(0.98,1.0,0.08,0.2);
		this.filtroY = new PdsKalman1D(0.98,1.0,0.1,0.1);
		this.filtroZ = new PdsKalman1D(0.98,1.0,0.1,1.4);

		this.filtroGX = new PdsKalman1D(0.98,1.0,0.1,0.1);
		this.filtroGY = new PdsKalman1D(0.98,1.0,0.1,0.1);
		this.filtroGZ = new PdsKalman1D(0.98,1.0,0.1,0.1);

		SaveFilterData();
		
		
	}	



	public void SaveFilterData(){
		int j=0;
		while(1!=0)//ate o fim do txt)
		{
			// lê a linha toda do txt
			
			this.L = this.R.Scan();

			
			if ((this.L == null) || (this.L.length()<3)){
				break;
			}
			
			Scanner scanner = new Scanner(this.L);
			scanner.useLocale(Locale.US);

			this.t2 = scanner.nextDouble();
			this.X2 = scanner.nextDouble();
			this.Y2 = scanner.nextDouble();
			this.Z2 = scanner.nextDouble();
			this.GX2 = scanner.nextDouble();
			this.GY2 = scanner.nextDouble();
			this.GZ2 = scanner.nextDouble();

			//System.out.println(j+" GZ2: "+this.GZ2);
			this.XF = filtroX.EvaluateValue(this.X2);
			this.YF = filtroY.EvaluateValue(this.Y2);
			this.ZF = filtroZ.EvaluateValue(this.Z2);

			this.GXF = filtroGX.EvaluateValue(this.GX2);
			this.GYF = filtroGY.EvaluateValue(this.GY2);
			this.GZF = filtroGZ.EvaluateValue(this.GZ2);

			//System.out.println(j+" XF: "+this.XF);

						
			W.Print(Double.toString(this.t2));
			W.Print("	"+Double.toString(this.XF));
			W.Print("	"+Double.toString(this.YF));
			W.Print("	"+Double.toString(this.ZF));
			W.Print("	"+Double.toString(this.GXF));
			W.Print("	"+Double.toString(this.GYF));
			W.Println("	"+Double.toString(this.GZF));

			System.out.println(this.t2+"\t"+this.XF+"\t"+this.YF+"\t"+this.ZF+"\t"+this.GXF+"\t"+this.GYF+"\t"+this.GZF);	
	
    		j++;
		}
	}





	public static void main(String[] args) {

		
		geraDadosFiltradosKalman var = new geraDadosFiltradosKalman("C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\data\\Testes Kalman\\NOVOQuedaLivre.txt","C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\data\\FILTERQuedaLivre.txt");

		
							
	
	}



}
