/*
 * Copyright (c) 2015. Fernando Pujaico Rivera <fernando.pujaico.rivera@gmail.com>
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package net.trucomanx.pdsplibj.pdsgcp;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import java.awt.Point;
import net.trucomanx.pdsplibj.pdsra.Pds3DPoint;


import net.trucomanx.pdsplibj.pdsra.PdsVector;
import net.trucomanx.pdsplibj.pdsra.PdsMatrix;
import net.trucomanx.pdsplibj.pdsra.PdsPoints;


/** 
 * Esta classe implementa uma ...
 *
 * <br><br> Para usar esta classe é necessário escrever:
 * <pre>
	import net.trucomanx.pdsplibj.pdsgcp.PdsGcp; 
 * </pre>
 *
 *
 * @author Fernando Pujaico Rivera <a href="mailto:fernando.pujaico.rivera@gmail.com">fernando.pujaico.rivera@gmail.com</a>
 * @version 0.01
 * @since 2016-05-01
 * @see <a href="http://pdsplib.sourceforge.net"> PDS Project Libraries in Java </a>
 */
public class PdsGcp{

	private BufferedImage bImage1 = null;
	private BufferedImage bImage2 = null;
	private int W;
	private int H;
	
	/**
	 * Este construtor da classe NÃO é necessário inicializar-lo.
	 * É gerado um objeto do tipo PdsGcp.
	 * 
	 **/	
	public PdsGcp()
	{
		this.H=0;
		this.W=0;
	}


	public boolean save_result(String Filename2)
	{
		File imagefile2 = null;
		boolean result=false;

		try
		{
			imagefile2 = new File(Filename2);
			String ext = Filename2.substring(Filename2.lastIndexOf(".") + 1);
			result     = ImageIO.write(bImage2, ext,imagefile2);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		return result;
	}
	

	/**
	 * Este método carrega uma imagen no objeto PdsGcp, esta imagen logo
	 * serão processada por outros métodos.<br>
	 * Entre as situações que geram um IllegalArgumentException temos:<br>
	 * <p><ul>
	 * <li> O número de componentes de cor é menor que 3.
	 * <li> ...
	 * </ul><p>
	 *
	 * @param Filename1 É o endereço completo da primeira imagem.
	 **/
	public void load(String Filename1)
	{
		File imagefile1 = null;
		
		String Aviso1="The images should have at least 3 color component. ";
		
		int numbits1;

		try
		{
			imagefile1 = new File(Filename1);
            bImage1 = ImageIO.read(imagefile1);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		// Um check si esta en escala de grises.
		numbits1=bImage1.getColorModel().getNumComponents();
		if(numbits1<3)
		throw new IllegalArgumentException(Aviso1+"Components = "+numbits1+": "+Filename1);

		// Check que las matrices son del mismo tamanho
		this.W = bImage1.getWidth();		this.H = bImage1.getHeight();

		this.bImage2 = new BufferedImage(this.W,this.H,BufferedImage.TYPE_BYTE_GRAY);
	}

	/**
	 * Retorna o valor de um pixel (red,green,blue) na linha e coluna da Imagem.
	 * 0 é mais oscuro e 255 é mais claro.
	 *
	 * @param linha A linha em consulta.
	 * @param coluna A coluna em consulta.
	 * @return Retorna um ponto com valor de um pixel (red,green,blue) na linha 
     * e coluna da Imagem.
	 */	
	public Pds3DPoint get_new_color_point_of_point(int linha, int coluna)
	{
		int color = bImage1.getRGB(coluna,linha); 

		int blue  = (color & 0x000000ff);
		int green = (color & 0x0000ff00) >> 8;
		int red   = (color & 0x00ff0000) >> 16;

		Pds3DPoint P = new Pds3DPoint();
		P.SetValue(0,red);
		P.SetValue(1,green);
		P.SetValue(2,blue);
		return P;
	}


	/**
	 * Retorna o valor de um pixel (red,green,blue) no ponto P=(linha, coluna) da Imagem.
	 * 0 é mais oscuro e 255 é mais claro.
	 *
	 * @param P Ponto (linha,coluna) de onde se leerá o pixel .
	 * @return Retorna um ponto com valor de um pixel (red,green,blue) no
     * ponto P=(linha, coluna) da Imagem.
	 */	
	public Pds3DPoint get_new_color_point_of_point(Point P)
	{
		Pds3DPoint Pout=get_new_color_point_of_point( (int)(P.getX()),(int)(P.getY()) );
		return Pout;
	}


	/**
	 * Retorna o valoren rojo de um pixel na linha e coluna da Imagem.
	 * 0 é mais oscuro e 255 é mais claro.
	 *
	 * @param linha A linha em consulta.
	 * @param coluna A coluna em consulta.
	 * @return Retorna o valor de rojo de um pixel na linha 
     * e coluna da Imagem.
	 */	
	public int get_value_red_of_point(int linha, int coluna)
	{
		int color = bImage1.getRGB(coluna,linha); 
		int red   = (color & 0x00ff0000) >> 16;

		return red;
	}

	/**
	 * Retorna o valoren verde de um pixel na linha e coluna da Imagem.
	 * 0 é mais oscuro e 255 é mais claro.
	 *
	 * @param linha A linha em consulta.
	 * @param coluna A coluna em consulta.
	 * @return Retorna o valor de verde de um pixel na linha 
     * e coluna da Imagem.
	 */	
	public int get_value_green_of_point(int linha, int coluna)
	{
		int color = bImage1.getRGB(coluna,linha); 
		int green = (color & 0x0000ff00) >> 8;

		return green;
	}

	/**
	 * Retorna o valoren azul de um pixel na linha e coluna da Imagem.
	 * 0 é mais oscuro e 255 é mais claro.
	 *
	 * @param linha A linha em consulta.
	 * @param coluna A coluna em consulta.
	 * @return Retorna o valor de azul de um pixel na linha 
     * e coluna da Imagem.
	 */	
	public int get_value_blue_of_point(int linha, int coluna)
	{
		int color = bImage1.getRGB(coluna,linha); 
		int blue  = (color & 0x000000ff);

		return blue;
	}



	/**
	 * Retorna o valor representativo de aplicar o filtro1 sobre um ponto.
	 * Any pixel P=(red,green,blue) with green component greater than the others.
	 *
	 * @param linha  Linha  da imagem onde se leerá o pixel.
	 * @param coluna Coluna da imagem onde se leerá o pixel.
	 * @return Retorna um valore real que representa o o resultado de aplicar o 
	 * filtro.
	 */	
	public int get_filter1_over_point(int linha, int coluna)
	{

		int Y=0;
		int color = bImage1.getRGB(coluna,linha); 

		int blue  = (color & 0x000000ff);
		int green = (color & 0x0000ff00) >> 8;
		int red   = (color & 0x00ff0000) >> 16;

		if ((green > blue) && (green > red))	Y=255;

		return Y;
	}

	/**
	 * Retorna o valor representativo de aplicar o filtro2 sobre um ponto.
	 * Any pixel P=(red,green,blue) with green component greater than the others.
	 * And an angle, between P and P0=(red0,green0,blue0), lower than angle.
	 *
	 * @param linha  Linha  da imagem onde se leerá o pixel.
	 * @param coluna Coluna da imagem onde se leerá o pixel.
	 * @param P0     Pixel (red0,green0,blue0).
	 * @param angle  Angulo entre P e P0.
	 * @return Retorna um valore real que representa o o resultado de aplicar o 
	 * filtro.
	 */	
	public int get_filter2_over_point(int linha, int coluna,Pds3DPoint P0, float angle)
	{

		int Y=0;

		int color = bImage1.getRGB(coluna,linha); 
		int blue  = (color & 0x000000ff);
		int green = (color & 0x0000ff00) >> 8;
		int red   = (color & 0x00ff0000) >> 16;
		double module=Math.sqrt(red*red+green*green+blue*blue);

		double red_p0   = P0.GetValue(0);
		double green_p0 = P0.GetValue(1);
		double blue_p0  = P0.GetValue(2);
		double module_p0= P0.Norm();

		double cos_current=(red_p0*red+green_p0*green+blue_p0*blue)/(module*module_p0);

		if ((green > blue) && (green > red))
		if(cos_current >= Math.cos(angle*Math.PI/180.0))	Y=255;

		return Y;
	}

	/**
	 * Retorna o valor representativo de aplicar o filtro1 sobre uma imagem
	 * Any pixel P=(red,green,blue) with green component greater than the others.
	 *
	 * @return Retorna uma matriz que representa o o resultado de aplicar o 
	 * filtro.
	 */	
	public int[][] get_filter1_over_image()
	{

		
		int[][] M = new int[this.H][this.W];
		
		for(int lin=0;lin<this.H;lin++)
		for(int col=0;col<this.W;col++)
		{
			M[lin][col]=get_filter1_over_point(lin,col);
		}
		
		return M;
	}

	/**
	 * Retorna o valor representativo de aplicar o filtro2 sobre uma imagem
	 * Any pixel P=(red,green,blue) with green component greater than the others.
	 * And an angle, between P and P0=(red0,green0,blue0), lower than angle.
	 *
	 * @param P0     Pixel (red0,green0,blue0).
	 * @param angle  Angulo entre P e P0.
	 * @return Retorna uma matriz que representa o o resultado de aplicar o 
	 * filtro.
	 */	
	public int[][] get_filter2_over_image(Pds3DPoint P0, float angle)
	{

		
		int[][] M = new int[this.H][this.W];
		
		for(int lin=0;lin<this.H;lin++)
		for(int col=0;col<this.W;col++)
		{
			M[lin][col]=get_filter2_over_point(lin,col,P0,angle);
		}
		
		return M;
	}


	/**
	 * Retorna o valor representativo de aplicar o filtro1 sobre uma imagem
	 * Any pixel P=(red,green,blue) with green component greater than the others.
	 *
	 * @return Retorna uma matriz que representa o o resultado de aplicar o 
	 * filtro.
	 */	
	public boolean apply_filter1_over_image()
	{
		int m=0;
		int Color=0;
		for(int lin=0;lin<this.H;lin++)
		for(int col=0;col<this.W;col++)
		{
			m=get_filter1_over_point(lin,col);
			Color=color2argb(0,m,m,m);
			this.bImage2.setRGB(col,lin,Color);
		}
		
		return true;
	}


	/**
	 * Retorna o valor representativo de aplicar o filtro2 sobre uma imagem
	 * Any pixel P=(red,green,blue) with green component greater than the others.
	 * And an angle, between P and P0=(red0,green0,blue0), lower than angle.
	 *
	 * @param P0     Pixel (red0,green0,blue0).
	 * @param angle  Angulo entre P e P0.
	 * @return Retorna uma matriz que representa o o resultado de aplicar o 
	 * filtro.
	 */	
	public boolean apply_filter2_over_image(Pds3DPoint P0, float angle)
	{
		int m=0;
		int Color=0;
		for(int lin=0;lin<this.H;lin++)
		for(int col=0;col<this.W;col++)
		{
			m=get_filter2_over_point(lin,col,P0,angle);
			Color=color2argb(0,m,m,m);
			this.bImage2.setRGB(col,lin,Color);
		}
		
		return true;
	}

	/**
	 * Retorna o número de colunas nas imagens.
	 *
	 * @return Retorna o número de colunas na imagem.
	 */	
	public int get_parameter_width()
	{
		return this.W;
	}
		

	/**
	 * Retorna o número de linhas na imagem.
	 *
	 * @return Retorna o número de linhas na imagem.
	 */
	public int get_parameter_height()
	{
		return H;
	}
	
	/**
	 * Retorna a area em pixels do resultado de aplicar algum filtro a imagem.
	 * Valores >= U em tons de cinza são considerado como +1 na area, 
	 * a area maxima é width x height. See {@link #get_area}.
	 *
	 * @param U Valores >= U em tons de cinza são considerado como +1 na area.
	 * @return Retorna a area em pixels do resultado de aplicar algum filtro a imagem.
	 */	
	public double get_result_area(int U)
	{
		int color, green;
		int lin,col;

		double sum;
		int sum_lin;

		
		for(lin=0,sum=0;lin<this.H;lin++)
		{
			for(col=0,sum_lin=0;col<this.W;col++)
			{
				color = bImage2.getRGB(col,lin); 
				green = (color & 0x0000ff00) >> 8;

				if(green >= U) sum_lin=sum_lin+1;
			
			}
			sum=sum+sum_lin;
		}
		return sum;
	}

	/**
	 * Retorna a area da imagem em pixels. A area mé width x height. 
	 * See {@link #get_parameter_height} and {@link #get_parameter_width}.
	 *
	 * @return Retorna a area da imagem em pixels.
	 */	
	public double get_area()
	{
		return this.H*this.W;
	}

	private static int color2argb(int alpha, int red, int green, int blue)
	{

		int argb=0;

		if(alpha>255)	alpha = 255;
		if(red>255)		red   = 255;
		if(green>255)	green = 255;
		if(blue>255)	blue  = 255;

		argb += alpha;	argb = argb << 8;
		argb += red;	argb = argb << 8;
		argb += green;	argb = argb << 8;
		argb += blue;

		return argb;

	}
}



////////////////////////////////////////////////////////////////////////////////



	/**
	 * Retorna o valor medio de todos os pontos dentro de Pts.
	 *
	 * @param Pts Pontos (linha,coluna) de onde se leerá os pixels .
	 * @return Retorna o valor medio de todos os pontos dentro de Pts.
	 */	
	/*
	public Pds3DPoint get_new_mean_pixel_point_of_image(PdsPoints Pts)
	{
		int Nel=Pts.get_length();
		Pds3DPoint R=new Pds3DPoint(0);

		for(int i=0;i<Nel;i++)
		{
			Pds3DPoint P= get_new_pixel_point_of_image(Pts.get_point_from_id(i));
			R.Add(P);
		}
		R.Mul(1.0/Nel);

		return R;
	}
	*/


