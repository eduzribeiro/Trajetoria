import net.trucomanx.pdsplibj.pdsra.*;
import net.trucomanx.pdsplibj.pdsdf.*;
import net.trucomanx.pdsplibj.pdsextras.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;
import java.io.File;
import java.io.IOException;


public class geraDados {

		String Path,PathDestino,L;
		double t,X,Y,Z,GX,GY,GZ;
		double ta,Xa,Ya,Za,GXa,GYa,GZa;
		PdsReadData R;
		PdsWriteData W;
		PdsSpline Sx,Sy,Sz,SGx,SGy,SGz,St;
		

	public geraDados(String path, String path2){

		int N = 8;

		this.Sx = new PdsSpline(N);
		this.Sy = new PdsSpline(N);
		this.Sz = new PdsSpline(N);

		this.SGx = new PdsSpline(N);
		this.SGy = new PdsSpline(N);
		this.SGz = new PdsSpline(N);

		this.St = new PdsSpline(N);

		this.Path = path;
		this.PathDestino = path2;
		this.R = new PdsReadData(this.Path);
		this.W = new PdsWriteData(this.PathDestino);
		
		SaveData();
		
				
	}	



	public void SaveData(){
		long j =0;
		while(1!=0)//ate o fim do txt)
		{
			// lê a linha toda do txt
			
			this.L = this.R.Scan();

			
			if (this.L == null){
				//System.out.println("Break"+this.L);				
				break;
			}
				
			//System.out.println(this.L);

			Scanner scanner = new Scanner(L);
			scanner.useLocale(Locale.US);

			this.t = scanner.nextDouble();
			this.X = scanner.nextDouble();
			this.Y = scanner.nextDouble();
			this.Z = scanner.nextDouble();
			this.GX = scanner.nextDouble();
			this.GY = scanner.nextDouble();
			this.GZ = scanner.nextDouble();
			
			

			PdsVector Xv = this.Sx.EvaluateValue(this.X, this.t,"spline");
			PdsVector Yv = this.Sy.EvaluateValue(this.Y, this.t,"spline");
			PdsVector Zv = this.Sz.EvaluateValue(this.Z, this.t,"spline");

			PdsVector GXv = this.SGx.EvaluateValue(this.GX, this.t,"spline");
			PdsVector GYv = this.SGy.EvaluateValue(this.GY, this.t,"spline");
			PdsVector GZv = this.SGz.EvaluateValue(this.GZ, this.t,"spline");

			PdsVector Tv = this.St.EvaluateValue(this.t, this.t,"uniform");

			int N = Xv.GetNelements();
			int i;
			for (i =0; i<N;i++){

				String TMP = 	Tv.GetValue(i)+"\t"+
								Xv.GetValue(i)+"\t"+
								Yv.GetValue(i)+"\t"+
								Zv.GetValue(i)+"\t"+
								GXv.GetValue(i)+"\t"+
								GYv.GetValue(i)+"\t"+
								GZv.GetValue(i);

				W.Println(TMP);
				if(j>1)
				System.out.println(TMP);

			}
			j++;
		}
	}



	public static void main(String[] args) {

		
		geraDados var = new geraDados("C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\Legado\\logfile_2.txt","C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Arquivos paper\\Paper 2\\SPLINElogfile_2.txt");

		
						
	
	}



}
