import net.trucomanx.pdsplibj.pdsra.*;
import net.trucomanx.pdsplibj.pdsextras.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class geraMatriz {

		int A[][], M,posX2,posY2;
		String Path,L;
		ArrayList<PdsVector> W;
		double max, X,Y,Z,t,p,p2,posX,posY;
		PdsReadData R;
		


	public geraMatriz(String path, int sizeMax){

		this.max = 0;
		this.A = new int[sizeMax][sizeMax]; 
		this.Path = path;
		this.W = new ArrayList<PdsVector>();
		
		this.R = new PdsReadData(this.Path);

		loadDataInArrayList(this.Path);

		this.M = sizeMax - 1;

		
	}	

	


   	public int[][] Convert(int c1,int c2) {   

		PdsVector V;
		int i=0;
		int LL=W.size();
		
		while(i<LL)//ate o fim do W)
		{
			
			V=W.get(i);

			//System.out.println("V: "+V.toString());

			//selecciona e transforma coordenadas - Usar V.Getvalue() para fazer os calculos
			//escrever 1 na matriz A

			this.posX=V.GetValue(c1)*((this.M/2)/this.max)+(this.M/2);
			this.posY=V.GetValue(c2)*((this.M/2)/this.max)+(this.M/2);

			//System.out.println("["+i+"]posX: "+this.posX+"posY: "+this.posY);

			this.posX2 = (int)(this.posX);
			this.posY2 = (int)(this.posY);

			System.out.println("["+i+"]posX2: "+this.posX2+"posY2: "+this.posY2);

			this.A[this.posX2][this.posY2] = 255;					

			i++;
		}
		
		SalvarImagem(480,A);

		return A;

		
		
	}



	public void SalvarImagem(int MaxSize, int A[][]){ 

		BufferedImage theImage = new BufferedImage(MaxSize, MaxSize, BufferedImage.TYPE_INT_RGB);

		try{    	

		for(int y = 0; y<MaxSize; y++){
        	for(int x = 0; x<MaxSize; x++){
        	    theImage.setRGB(x, y, A[y][x]);
        	}
    	}
    	File outputfile = new File("TrajetoriaSalva.bmp");
    	ImageIO.write(theImage, "bmp", outputfile);

		}catch (IOException e) {
        	e.printStackTrace();
        }

	}

	

	public void loadDataInArrayList(String path){
		//leo o txt  e carrego ArrayList W
		// obtem |sample| maximos
		
		double tmax;

		while(1!=0)//ate o fim do txt)
		{
			// lee en X Y Z t p desde o txt
			
			this.L = this.R.Scan();

			
			if (this.L == null){
				break;
			}
			
			Scanner scanner = new Scanner(L);
			scanner.useLocale(Locale.US);

			//scanner.useLocale(Locale.ENGLISH);

			this.t = scanner.nextDouble();
			this.X = scanner.nextDouble();
			this.Y = scanner.nextDouble();
			this.Z = scanner.nextDouble();
			this.p = scanner.nextDouble();
			//this.p2 = scanner.nextDouble();

			//System.out.println("X: "+this.X+"Y: "+this.Y);

			if (Math.abs(this.X) > Math.abs(this.Y)){
				
				if (Math.abs(this.X) > Math.abs(this.Z)){
					
					tmax=Math.abs(this.X);

				}else{

					tmax=Math.abs(this.Z);
				}
				 	
		
			}else{

				if (Math.abs(this.Y) > Math.abs(this.Z)){
					
					tmax=Math.abs(this.Y);

				}else{

					tmax=Math.abs(this.Z);
				}

			}
			
			if (this.max<tmax){
				this.max= tmax;
			}

			//System.out.println("max: "+tmax);

			PdsVector V=new PdsVector(5);

			V.SetValue(0,t);
			V.SetValue(1,X);
			V.SetValue(2,Y);
			V.SetValue(3,Z);
			V.SetValue(4,p);
			//V.SetValue(4,p2);

			
			W.add(V);
		}
	}




	public static void main(String[] args) {

		//int A[][];
		//A = new int[480][480]; 
		

		geraMatriz var = new geraMatriz("C:\\Users\\Eduardo\\Desktop\\Diversos\\UFLA\\Mestrado\\Trajetoria\\EDUARDO\\JAVA\\a.txt",480);
		var.Convert(1,2);

					
	
	}




}





