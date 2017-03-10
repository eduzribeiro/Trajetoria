import net.trucomanx.pdsplibj.pdsra.*;
import net.trucomanx.pdsplibj.pdsdf.*;
import net.trucomanx.pdsplibj.pdsextras.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;
import java.io.File;
import java.io.IOException;


public class geraDadosKalman {

		String Path,PathDestino,L;
		double t,X,Y,Z,GX,GY,GZ;
		double ta,Xa,Ya,Za,GXa,GYa,GZa;
		PdsReadData R;
		PdsWriteData W;
		

	public geraDadosKalman(String path, String path2){

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
			
			int N = 4;

			if (j == 0) {

				String tmp=Double.toString(this.t)+ 
						"\t"+Double.toString(this.X)+
						"\t"+Double.toString(this.Y)+
						"\t"+Double.toString(this.Z)+
						"\t"+Double.toString(this.GX)+
						"\t"+Double.toString(this.GY)+
						"\t"+Double.toString(this.GZ);
				System.out.println(tmp);
				W.Println(tmp);
			
			}else{			

					for(int i = 1; i<=N; i++){
		
							String tmp2 = Double.toString(this.ta+i*(this.t-this.ta)/N)+
							"\t"+Double.toString(this.Xa+i*(this.X-this.Xa)/N)+
							"\t"+Double.toString(this.Ya+i*(this.Y-this.Ya)/N)+
							"\t"+Double.toString(this.Za+i*(this.Z-this.Za)/N)+
							"\t"+Double.toString(this.GXa+i*(this.GX-this.GXa)/N)+
							"\t"+Double.toString(this.GYa+i*(this.GY-this.GYa)/N)+
							"\t"+Double.toString(this.GZa+i*(this.GZ-this.GZa)/N);
							
							System.out.println(tmp2);
							W.Println(tmp2);
		
	
    				}
					W.Println("");
			}

				this.ta = this.t;
				this.Xa = this.X;
				this.Ya = this.Y;
				this.Za = this.Z;
				this.GXa = this.GX;
				this.GYa = this.GY;
				this.GZa = this.GZ;

				j++;

		}
	}



	public static void main(String[] args) {

		
		geraDadosKalman var = new geraDadosKalman("C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\data\\pausa-quedalivre-pausa.txt","C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\data\\NOVOQuedaLivre.txt");

		
							
	
	}



}
