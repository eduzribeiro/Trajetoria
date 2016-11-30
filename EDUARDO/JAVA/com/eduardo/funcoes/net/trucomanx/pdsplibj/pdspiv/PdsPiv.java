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
package net.trucomanx.pdsplibj.pdspiv;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import java.awt.Point;

import net.trucomanx.pdsplibj.pdsra.PdsMatrix;
import net.trucomanx.pdsplibj.pdsra.PdsPoints;


/** 
 * Esta classe implementa uma variante do método PIV (Particle image velocimetry).
 *
 * <br><br> Para usar esta classe é necessário escrever:
 * <pre>
	import net.trucomanx.pdsplibj.pdspiv.PdsPiv; 
 * </pre>
 *
 * O método PIV busca fazer o seguimento de um grupo de regiões de interesse (ROI)
 * entre duas imagens. Na seguinte  figura pode-se ver um grupo de ROI representados
 * pelos pontos vermelhos, e seu seguimento na segunda imagem, representados
 * pelos pontoso azuis.
 * <center><img src="{@docRoot}/data/imagenes/PdsPiv/Cool_jump_piv.png" alt="Particle image velocimetry"></center><br>
 *
 * @author Fernando Pujaico Rivera <a href="mailto:fernando.pujaico.rivera@gmail.com">fernando.pujaico.rivera@gmail.com</a>
 * @version 0.01
 * @since 2015-11-00
 * @see <a href="http://pdsplib.sourceforge.net"> PDS Project Libraries in Java </a>
 * @see PdsPivData
 * @see PdsPivConf
 */
public class PdsPiv{

	private BufferedImage bImage1 = null;
	private BufferedImage bImage2 = null;
	private int W;
	private int H;
	
	/**
	 * Este construtor da classe NÃO é necessário inicializar-lo.
	 * É gerado um objeto do tipo PdsPiv.
	 * 
	 **/	
	public PdsPiv()
	{
		H=0;
		W=0;
	}

	/**
	 * Este método carrega duas imagens no objeto PdsPiv, estas imagens logo
	 * serão processadas por outros métodos.<br>
	 * Entre as situações que geram um IllegalArgumentException temos:<br>
	 * <p><ul>
	 * <li> O número de componentes de cor é diferente de 1 
		(as imagens sempre deveriam estar em tons de cinzas sem mascaras).
	 * <li> As imagens tem diferente número de linhas.
	 * <li> As imagens tem diferente número de colunas.
	 * </ul><p>
	 *
	 * @param Filename1 É o endereço completo da primeira imagem.
	 * @param Filename2 É o endereço completo da segunda imagem.
	 **/
	public void load(String Filename1,String Filename2)
	{
		File imagefile1 = null;
		File imagefile2 = null;
		int H1,H2;
		int W1,W2;
		
		String Aviso1="The images only should have 1 color component. ";
		String AvisoW="The images should have the same width. ";
		String AvisoH="The images should have the same height. ";
		
		int numbits1,numbits2;

		try
		{
			imagefile1 = new File(Filename1);
            bImage1 = ImageIO.read(imagefile1);
            
            imagefile2 = new File(Filename2);
            bImage2 = ImageIO.read(imagefile2);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		// Um check si esta en escala de grises.
		numbits1=bImage1.getColorModel().getNumComponents();
		if(numbits1!=1)
		throw new IllegalArgumentException(Aviso1+"Components="+numbits1+" :"+Filename1);
		
		numbits2=bImage2.getColorModel().getNumComponents();
		if(numbits2!=1)
		throw new IllegalArgumentException(Aviso1+"Components="+numbits2+" :"+Filename2);
		
		// Check que las matrices son del mismo tamanho
		W1 = bImage1.getWidth();		H1 = bImage1.getHeight();
		W2 = bImage2.getWidth();		H2 = bImage2.getHeight();
		
		if(W1!=W2)		throw new IllegalArgumentException(AvisoW);
		if(H1!=H2)		throw new IllegalArgumentException(AvisoH);
		
		H=H1;	W=W1;
	}

	/**
	 * Este método procura e faz o match (encontra similitudes) de regiões de 
	 * interesse na primeira imagem carregada com o método {@link #load}, com 
	 * a segunda imagem carregada com o mesmo método.
	 * <p> As regiões de interesse são escolhidas espaçadas uniformemente, formando
	 * uma grid, de a cordo com os parâmetros do método PIV  configurados no
	 * objeto Conf do tipo {@link PdsPivConf}.
	 *
	 * <p>Para fazer um match entre duas imagens, usando a configuração conf 
	 * por omissão pode-se usar.
	 * <pre>
	PdsPivConf	Conf = new PdsPivConf();
	// Dados por omissão são carregados em Conf.
	// Aqui, se desejar, vc pode aribuir em Conf os valores de configuração do algoritmo PIV.
	// Para detalhes ver a documentação de PdsPivConf.

	PdsPiv		piv = new PdsPiv();

	piv.load("/path/to/image1.bmp","/path/to/image2.bmp");

	PdsPivData	dat = piv.match(Conf);
	// Aqui vc deve pedir e procesar os resultados do PIV contidos em dat.
	// Para detalhes ver a documentação de PdsPivData.
	 * </pre>
	 *
	 * <p>Para detalhes de como ler a data dat deve-se ver a documentação de {@link PdsPivData}.
	 * <p>Para detalhes de como configurar os parâmetros do algoritmo PIV deve-se ver a documentação de {@link PdsPivConf}.
	 * @param Conf Os parâmetros de configuração do PIV em modo "match".
	 * Os parâmetros usados são: 
	 * <p><ul>
	 * <li> roi_window_size:
	 * <li> points_by_columns:
	 * <li> points_by_lines:
	 * <li> search_threshold:
	 * <li> search_step_size:
	 * <li> search_max_length:
	 * <li> verbose_flag:	
	 * </ul>
	 *  
	 * @return Retorna uma estrutura do tipo {@link PdsPivData} com os dados
	 * do match.
	 */
	public PdsPivData match(PdsPivConf Conf)
	{	
		int  N;
		PdsPivData D = new PdsPivData();
		
		D.initial_points = make_grid_points(Conf);
		D.final_points   = tracking_points(D.initial_points,Conf);

		D.vectors        = D.final_points.new_diff_with(D.initial_points);

		return D;
	}
	

	/**
	 * Este método procura e faz o seguimento (encontra similitudes) de regiões de 
	 * interesse na primeira imagem carregada com o método {@link #load}, com 
	 * a segunda imagem carregada com o mesmo método.
	 * <p> As regiões de interesse são definidas usando os pontos em initial_points, com um 
	 * tamanho de ROI de a cordo com os parâmetros do método PIV  configurados no
	 * objeto Conf, do tipo {@link PdsPivConf}.
	 *
	 * <p>Para fazer o seguimento entre duas imagens, usando a configuração conf 
	 * por omissão pode-se usar.
	 * <pre>
	PdsPivConf	Conf = new PdsPivConf();
	// Dados por omissão são carregados em Conf.
	// Aqui, se desejar, vc pode aribuir em Conf os valores de configuração do algoritmo PIV.
	// Para detalhes ver a documentação de PdsPivConf.

	PdsPiv		piv = new PdsPiv();

	piv.load("/path/to/image1.bmp","/path/to/image2.bmp");

	PdsPoints	final_points = piv.tracking_points(Conf,initial_points);
	// initial_points estão referenciados à primeira imagem.
	// final_points estão referenciados à segunda imagem.
	 * </pre>
	 * <p>Para detalhes de como ler initial_points deve-se ver a documentação de {@link PdsPoints}.
	 * <p>Para detalhes de como configurar os parâmetros do algoritmo PIV deve-se ver a documentação de {@link PdsPivConf}.
	 * @param initial_points Pontos na quina superior esquerda nas regiões de interesse.
	 * @param Conf Os parâmetros de configuração do PIV em modo "match".
	 * Os parâmetros usados são: 
	 * <p><ul>
	 * <li> roi_window_size:
	 * <li> search_threshold:
	 * <li> search_step_size:
	 * <li> search_max_length:
	 * <li> verbose_flag:	
	 * </ul>
	 * 
	 * @return Retorna um novo objeto PdsPoints com os puntos que representam 
	 * a quina superior esquerda dos ROI, se estes foram achados, ou
	 * retorna null nas regiões que não foram achadas. 
	 */
	public PdsPoints tracking_points(PdsPoints initial_points,PdsPivConf Conf)
	{

		int N=initial_points.get_length();
		PdsPoints final_points = new PdsPoints(N);
		Point p1=null;
		Point p2=null;
		boolean flag=Conf.get_verbose_flag();
		
		for(int i=0;i<N;i++)
		{
			p1=initial_points.get_point_from_id(i);// retorna referencia ao ponto
			
			if(p1!=null)
			{
				p2=tracking_point(p1,Conf);
				if (p2==null)
					final_points.nullifies_the_point(i);
				else
					final_points.set_point(i,p2);
				
				if(flag==true)	
				{
					if((i+1)!=N)
					System.out.print("Analyzing the point "+(i+1)+"/"+N+"     \r");
					else
					System.out.print("Analyzing the point "+(i+1)+"/"+N+"     \n");
				}
			}
			else
			{
				final_points.nullifies_the_point(i);
			}
		}

		return final_points;
	}
	
	/**
	 * Este método procura e faz o seguimento (encontra similitudes) de 1 região de 
	 * interesse na primeira imagem carregada com o método {@link #load}, com 
	 * a segunda imagem carregada com o mesmo método.
	 * <p> A região de interesse é definida usando o ponto initial_point, com um 
	 * tamanho de ROI de a cordo com os parâmetros do método PIV  configurados no
	 * objeto Conf, do tipo {@link PdsPivConf}.
	 *
	 * <p>Para fazer o seguimento entre duas imagens, usando a configuração conf 
	 * por omissão pode-se usar.
	 * <pre>
	PdsPivConf	Conf = new PdsPivConf();
	// Dados por omissão são carregados em Conf.
	// Aqui, se desejar, vc pode aribuir em Conf os valores de configuração do algoritmo PIV.
	// Para detalhes ver a documentação de PdsPivConf.

	PdsPiv	piv = new PdsPiv();
	Point 	initial_point   = new Point(100,120);

	piv.load("/path/to/image1.bmp","/path/to/image2.bmp");

	Point	final_point = piv.tracking_point(initial_point,Conf);
	// initial_point está referenciado à primeira imagem.
	// final_point está referenciado à segunda imagem.
	 * </pre>
	 * <p>Para detalhes de como ler initial_point deve-se ver a documentação de {@link Point}.
	 * <p>Para detalhes de como configurar os parâmetros do algoritmo PIV deve-se ver a documentação de {@link PdsPivConf}.
	 * @param initial_point Ponto na quina superior esquerda na região de interesse.
	 * @param Conf Os parâmetros de configuração do PIV em modo "match".
	 * Os parâmetros usados são: 
	 * <p><ul>
	 * <li> roi_window_size:
	 * <li> search_threshold:
	 * <li> search_step_size:
	 * <li> search_max_length:
	 * <li> verbose_flag:	
	 * </ul>
	 * 
	 * @return Retorna um novo objeto Point com o ponto que representa 
	 * a quina superior esquerda do ROI achado na segunda imagem, se for achado, ou
	 * retorna null se não for achado nenhuma coincidência. 
	 */
	public Point tracking_point(Point initial_point,PdsPivConf Conf)
	{
		int		search_step		= Conf.get_search_step_size();
		int		search_max		= Conf.get_search_max_length();
		double 	search_threshold= Conf.get_search_threshold();
		
		int N=(int)Math.ceil(search_max*1.0/search_step);
		double x2,y2;
		
		Point     final_point = new Point(0,0);
		Point     ID;		
		PdsMatrix C ;
		
		C=get_pearson_correlations_matrix(initial_point,Conf);
		
		//ID va desde -(N-1) a +(N-1) em ambos ejes.
		ID=get_point_of_max_value_in_matrix(C,search_threshold,0);
		
		if(ID!=null)
		{
			x2=initial_point.getX()+ID.getX()*search_step;
			y2=initial_point.getY()+ID.getY()*search_step;
			final_point.setLocation(x2,y2);
			return final_point;
		}
		else return null;
		
	}


	/**
	 * Gera uma grid de pontos espalhados uniformemente na imagem. 
	 *
	 * Os pontos são gerados seguindo os parâmetros de configuração em Conf. 
	 * 0 primeiro ponto estará em (0,0), e os demais em (i*step_lin,j*step_col) 
	 * para i={0, 1, 2, ... } e  j={0, 1, 2, ....}.
	 * Assim para que todos os pixels da imagem sejam analizados, step_lin e 
	 * step_col devem ser iguais a:
	 * <center>{@latex.ilb %preamble{\\usepackage{amssymb}} %resolution{150} 
	 * $step\\_col \\equiv \\frac{W-roi\\_window\\_size}{points\\_by\\_columns-1}$
	 * }</center>
	 * <center>{@latex.ilb %preamble{\\usepackage{amssymb}} %resolution{150} 
	 * $step\\_lin \\equiv \\frac{H-roi\\_window\\_size}{points\\_by\\_lines-1}$
	 * }</center>
	 *
	 * @param Conf Os parâmetros de configuração do PIV.
	 * Os parâmetros usados são: 
	 * <p><ul>
	 * <li> roi_window_size:
	 * <li> points_by_columns:
	 * <li> points_by_lines:
	 * </ul>
	 * 
	 * @return Retorna um conjunto de pontos espalhados uniformemente na imagem. 
	 */
	public PdsPoints make_grid_points(PdsPivConf Conf)
	{
		String AvisoC="To much point by columns";
		String AvisoL="To much point by lines";
		int lin,col,id;
		int points_by_lines   = Conf.get_points_by_lines();
		int points_by_columns = Conf.get_points_by_columns();
		int wsize             = Conf.get_roi_window_size();
		int search_step       = Conf.get_search_step_size();
		int search_max        = Conf.get_search_max_length();
		int no_used;
		
		int step_col=(W-wsize)/(points_by_columns-1);
		if(step_col<=0)		throw new IllegalArgumentException(AvisoC);
		
		int step_lin=(H-wsize)/(points_by_lines-1);
		if(step_lin<=0)		throw new IllegalArgumentException(AvisoL);
		
		no_used=W-(step_col*(points_by_columns-1)+wsize);
		if(no_used>0)		System.out.println("Column pixels no used:"+no_used+" lasts");
		
		no_used=H-(step_lin*(points_by_lines-1)+wsize);
		if(no_used>0)		System.out.println("Lines pixels no used:"+no_used+" lasts");
		
		PdsPoints P = new PdsPoints(points_by_columns*points_by_lines);
		
		id=0;
		for(lin=0;lin<points_by_lines;lin++)
		for(col=0;col<points_by_columns;col++)
		{
			
			P.set_point(id,lin*step_lin,col*step_col);
			id=id+1;
		}
		
		return P;
	}


	/**
	 * Enche uma matriz com correlações entre uma ROI na imagem 1 e 2, estando
	 * a ROI da imagem 1 no ponto P. O centro da matriz representa a correlação
	 * com uma ROI na imagem 2 no ponto P.
	 *
	 * @param P ponto inicial para o analises de correlação de Pearson.
	 * @param Conf Os parâmetros de configuração do PIV.
	 * Os parâmetros usados são: 
	 * <p><ul>
	 * <li> roi_window_size:
	 * <li> search_step_size:
	 * <li> search_max_length:
	 * </ul>
	 *
	 * @return Retorna uma matriz com correlações ao redor de P na figura 2.
	 */
	public PdsMatrix get_pearson_correlations_matrix(Point P,PdsPivConf Conf)
	{
		int wsize             = Conf.get_roi_window_size();
		int search_step       = Conf.get_search_step_size();
		int search_max        = Conf.get_search_max_length();
		
		int N=(int)Math.ceil(search_max*1.0/search_step);
		double corr;
		double x2,y2;
		
		
		PdsMatrix C  = new PdsMatrix(2*N-1,2*N-1);
		Point     P2 = new Point(0,0);
		
		C.InitValue(-1.0);
		
		for( int lin=-(N-1);lin<N;lin++)
		for( int col=-(N-1);col<N;col++)
		{
			x2=P.getX()+lin*search_step;
			y2=P.getY()+col*search_step;
			
			if( (x2>=0)&&(y2>=0)&&(y2<=(W-wsize))&&(x2<=(H-wsize)) )
			{
				P2.setLocation(x2,y2);
				corr=corr_pearson(P,P2,wsize);
				C.SetValue(lin+(N-1),col+(N-1),corr);
			}
		}
		return C;
	}
	
	
	/**
	 * Retorna um novo ponto (x,y) com o valor máximo de todos os elementos da 
	 * matriz. 
	 * É retornando null se o ponto achado tem um valor na matriz menor a
	 * Umbral.
	 *
	 * @param C Matriz a analisar.
	 * @param Umbral Só é considerado como achado se o ponto tem um valor na 
	 * matriz maior ou igual a Umbral.
	 * @param varUmbral Ainda não foi implementado.
	 * 
	 * @return Retorna um novo ponto (x,y) com o valor máximo de todos os 
	 * elementos da matriz. O centro corresponde a (0,0) 
	 * os extremos correspondem aos valores (x,y) positivos e negativos.
	 */
	public Point get_point_of_max_value_in_matrix(PdsMatrix C,double Umbral,double varUmbral)
	{
		int Nlin = C.GetNlin();
		int Ncol = C.GetNcol();
		int lin_max=-1;
		int col_max=-1;
		double max=Umbral;
		double val;
		
		for(int lin=0;lin<Nlin;lin++)
		for(int col=0;col<Ncol;col++)
		{
			val=C.GetValue(lin,col);
			
			if(val>=Umbral)
			{
				Umbral=val;
				lin_max=lin;
				col_max=col;
			}
		}
		
		if(lin_max==-1)	return null;
		
		Point P = new Point(lin_max-(Nlin-1)/2,col_max-(Ncol-1)/2);
		
		return P;
	}
	

	/**
	 * Retorna a correlação de Pearson de uma ROI no ponto P1 na imagem 1 com 
	 * uma ROI ponto P2 na imagem 2.
	 * Faz a correlação na região existente. Aceita fazer a correlação se existe
	 * ate wsize/2 da região. Pontos inexistentes são contabilizados como de valor 
	 * zero.
	 *
	 * @param P1 A quina superior esquerda da região de interesse na imagem 1.
	 * @param P2 A quina superior esquerda da região de interesse na imagem 2.
	 * @param wsize O lado da região de interesse. As regiões de interesse são quadradas.
	 * @return Retorna a correlação de Pearson das regiões nas imagens 1 e 2.
	 */	
	public double corr_pearson(Point P1,Point P2, int wsize)
	{
		double mu1=mean_of_image1(P1,wsize);
		double mu2=mean_of_image2(P2,wsize);
		
		int lin,col;
		double corr=0;
		int X1,Y1;
		int X2,Y2;
		double var1=0;
		double var2=0;
		double cov=0;
		double val1,val2;
		
		for(lin=0;lin<wsize;lin++)
		for(col=0;col<wsize;col++)
		{
			X1=(int)P1.getX()+lin;	Y1=(int)P1.getY()+col;
			X2=(int)P2.getX()+lin;	Y2=(int)P2.getY()+col;
			
			if( (X2>=0) && (X2<(H-wsize/2)) && (Y2>=0) && (Y2<(W-wsize/2)) )
			if( (X1>=0) && (X1<(H-wsize/2)) && (Y1>=0) && (Y1<(W-wsize/2)) )
			{
				val1=get_pixel_of_image1(X1,Y1);
				val2=get_pixel_of_image2(X2,Y2);
				
				cov=cov+(val1-mu1)*(val2-mu2);
				var1=var1+(val1-mu1)*(val1-mu1);
				var2=var2+(val2-mu2)*(val2-mu2);
			}
		}
		
		corr=cov/Math.sqrt(var1*var2);
		
		return corr;
	}
	

	/**
	 * Retorna o valor médio de uma ROI, de lado wsize, no ponto P1 na imagem 1.
	 * Os pontos inexistentes não são descartados, são considerados como 0 no cálculo.
	 * <center>{@latex.ilb %preamble{\\usepackage{amssymb}} %resolution{150} 
	 * $P1=(i_0,j_0)$
	 * }</center>
	 * <center>{@latex.ilb %preamble{\\usepackage{amssymb}} %resolution{150} 
	 * $\\mu_1 = \\frac{1}{wsize*wsize}\\sum \\limits_{i-i_0=0}^{wsize-1} ~ \\sum \\limits_{j-j_0=0}^{wsize-1} Image_1(i,j)$
	 * }</center>
	 *
	 * @param P1 A quina superior esquerda na região de interesse.
	 * @param wsize O lado da região de interesse. a regiao de interesse é quadrada.
	 * @return Retorna o valor médio desde el punto P1 em imagem 1 com um ROI de wsize.
	 */	
	public double mean_of_image1(Point P1, int wsize)
	{
		int lin,col;
		double val=0;
		int X,Y;
		
		for(lin=0;lin<wsize;lin++)
		for(col=0;col<wsize;col++)
		{
			X=(int)P1.getX()+lin;
			Y=(int)P1.getY()+col;
			
			if( (X>=0) && (X<H) && (Y>=0) && (Y<W) )
			val=val+get_pixel_of_image1(X,Y);
		}
		return val/(wsize*wsize);
	}

	/**
	 * Retorna o valor médio de uma ROI, de lado wsize, no ponto P2 na imagem 2.
	 * Os pontos inexistentes não são descartados, são considerados como 0 no cálculo.
	 * <center>{@latex.ilb %preamble{\\usepackage{amssymb}} %resolution{150} 
	 * $P2=(i_0,j_0)$
	 * }</center>
	 * <center>{@latex.ilb %preamble{\\usepackage{amssymb}} %resolution{150} 
	 * $\\mu_2 = \\frac{1}{wsize*wsize}\\sum \\limits_{i-i_0=0}^{wsize-1} ~ \\sum \\limits_{j-j_0=0}^{wsize-1} Image_2(i,j)$
	 * }</center>
	 *
	 * @param P2 A quina superior esquerda na região de interesse.
	 * @param wsize O lado da região de interesse. a região de interesse é quadrada.
	 * @return Retorna o valor médio desde el punto P2 em imagem 2 com um ROI de wsize.
	 */	
	public double mean_of_image2(Point P2, int wsize)
	{
		int lin,col;
		double val=0;
		int X,Y;
		
		for(lin=0;lin<wsize;lin++)
		for(col=0;col<wsize;col++)
		{
			X=(int)P2.getX()+lin;
			Y=(int)P2.getY()+col;
			
			if( (X>=0) && (X<H) && (Y>=0) && (Y<W) )
			val=val+get_pixel_of_image2(X,Y);
		}
		return val/(wsize*wsize);
	}

	
	/**
	 * Retorna o valor de um pixel  na linha e coluna da Image1.
	 * 0 é negro e 255 é branco.
	 *
	 * @param linha A linha em consulta.
	 * @param coluna A coluna em consulta.
	 * @return Retorna o valor de um pixel  na linha e coluna da Image1.
	 */	
	public int get_pixel_of_image1(int linha, int coluna)
	{
		int blue   = bImage1.getRGB(coluna,linha)& 0x000000ff; 
		return blue;
	}
	

	/**
	 * Retorna o valor de um pixel  na linha e coluna da Image2.
	 * 0 é negro e 255 é branco.
	 *
	 * @param linha A linha em consulta.
	 * @param coluna A coluna em consulta.
	 * @return Retorna o valor de um pixel  na linha e coluna da Image2.
	 */	
	public int get_pixel_of_image2(int linha, int coluna)
	{
		int blue   = bImage2.getRGB(coluna,linha)& 0x000000ff; 
		return blue;
	}
		
	/**
	 * Retorna o número de colunas nas imagens.
	 * É similar a {@link #get_image_ncolumns}
	 *
	 * @return Retorna o número de colunas nas imagens.
	 */	
	public int get_image_width()
	{
		return W;
	}
	
	/**
	 * Retorna o número de colunas nas imagens.
	 * É similar a {@link #get_image_width}
	 *
	 * @return Retorna o número de colunas nas imagens.
	 */
	public int get_image_ncolumns()
	{
		return W;
	}	

	/**
	 * Retorna o número de linhas nas imagens.
	 * É similar a {@link #get_image_nlines}
	 *
	 * @return Retorna o número de linhas nas imagens.
	 */
	public int get_image_height()
	{
		return H;
	}
	
	/**
	 * Retorna o número de linhas nas imagens.
	 * É similar a {@link #get_image_height}
	 *
	 * @return Retorna o número de linhas nas imagens.
	 */
	public int get_image_nlines()
	{
		return H;
	}
	
}
