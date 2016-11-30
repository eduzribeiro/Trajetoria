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
package net.trucomanx.pdsplibj.pdsra;

import java.awt.Point;

/** 
 * Esta classe implementa um conjunto de pontos em 2D de N elementos.
 *
 * <br><br> Para usar esta classe é necessário escrever:
 * <pre>  
	import net.trucomanx.pdsplibj.pdsra.PdsPoints; 
 * </pre>
 * 
 * @author Fernando Pujaico Rivera <a href="mailto:fernando.pujaico.rivera@gmail.com">fernando.pujaico.rivera@gmail.com</a>
 * @version 0.01
 * @since 2015-05-25
 * @see <a href="http://pdsplib.sourceforge.net"> PDS Project Libraries in Java </a>
 */
public class PdsPoints{
	private Point[] P;
	private int Nel;
	

	/**
	 * Este construtor da classe é necessário inicializar-lo indicando o número
	 * Nel de pontos em 2D. Os pontos são inicializados com (0,0).
	 * 
	 * @param Nel É o número de pontos.
	 **/
	public PdsPoints(int Nel) 
	{
		int i;

		this.Nel=Nel;

		P= new Point[this.Nel];

		for(i=0;i<this.Nel;i++)
		{
			P[i]=new Point(0,0);
		}
		
	}

	/**
	 * Retorna um ponto na posição id. 
	 * Retorna o mesmo ponto dentro da estrutura PdsPoints, é dizer retorna o
	 * seu endereço, pelo qual uma modificação neste ponto modifica o ponto
	 * dentro da estrutura PdsPoints.  
	 * 
	 * @param id É a posição do ponto requerido.
	 * @return Se o id está dentro de {@latex.inline $0 \\leq id < Nel$} o ponto é retornando, no caso
	 * contrario retorna null.
	 **/
	public Point get_point_from_id(int id)
	{
		if((id>Nel)||(id<0))	return null;

		return P[id];
	}

	/**
	 * Retorna um ponto copia de ponto na posição id. 
	 * Retorna uma copia do ponto dentro da estrutura PdsPoints, é dizer retorna 
	 * um novo ponto, pelo qual uma modificação neste ponto não modifica o ponto
	 * dentro da estrutura PdsPoints.  
	 * 
	 * @param id É a posição do ponto requerido.
	 * @return Se o id está dentro de {@latex.inline $0 \\leq id < Nel$} um novo 
	 * ponto é retornando, no caso contrario retorna null.
	 * Tambem retorna null se o ponto é null.
	 **/
	public Point get_new_point_from_id(int id)
	{
		if((id>Nel)||(id<0))	return null;
		
		if (P[id]==null)		return null;
		
		return new Point(P[id]);
	}
	
	/**
	 * Retorna um array de dois elementos copia de ponto na posição id. 
	 * Retorna uma copia do ponto dentro da estrutura PdsPoints, pelo qual uma 
	 * modificação neste array não modifica o ponto dentro da estrutura PdsPoints.  
	 * 
	 * @param id É a posição do ponto requerido.
	 * @return Se o id está dentro de {@latex.inline $0 \\leq id < Nel$} um
	 * array é retornando, no caso contrario retorna null.
	 * Tambem retorna null se o ponto é null.
	 **/
	public double[] get_array_from_id(int id)
	{
		double p[]= new double[2];
		
		if((id>=Nel)||(id<0))	return null;
		
		if (P[id]==null)		return null;
		
		p[0]=P[id].getX();
		p[1]=P[id].getY();

		return p;
	}
	
	/**
	 * Retorna um array com todos os valores dos primeiros elementos de todos os
	 * pontos (correspondentes ao eixo X). Retorna uma copia dos elementos X
	 * dentro da estrutura PdsPoints, pelo qual uma 
	 * modificação neste array não modifica os pontos dentro da estrutura PdsPoints.  
	 * 
	 * @return Um array é retornando com todos os valores dos primeiros 
	 * elementos de todos os pontos (correspondentes ao eixo X). Se algum ponto
	 * é null, então é carregado Double.NaN dentro do elemento correspondente no
	 * array.
	 **/
	public double[] get_array_from_value0()
	{
		double p[]= new double[Nel];
		
		for(int i=0;i<Nel;i++)
		{
			if(P[i]!=null)
				p[i]=P[i].getX();
			else
				p[i]=Double.NaN;
		}

		return p;
	}
	
	/**
	 * Retorna um array com todos os valores dos segundos elementos de todos os
	 * pontos (correspondentes ao eixo Y). Retorna uma copia dos elementos Y
	 * dentro da estrutura PdsPoints, pelo qual uma 
	 * modificação neste array não modifica os pontos dentro da estrutura PdsPoints.  
	 * 
	 * @return Um array é retornando com todos os valores dos segundos 
	 * elementos de todos os pontos (correspondentes ao eixo X). Se algum ponto
	 * é null, então é carregado Double.NaN dentro do elemento correspondente no
	 * array.
	 **/
	public double[] get_array_from_value1()
	{
		double p[]= new double[Nel];
		
		for(int i=0;i<Nel;i++)
		{
			if(P[i]!=null)
				p[i]=P[i].getY();
			else
				p[i]=Double.NaN;
		}

		return p;
	}	


	/**
	 * Retorna o número de pontos dentro da estrutura PdsPoints.  
	 * 
	 * @return O número de pontos dentro da estrutura PdsPoints.  
	 **/
	public int get_length()
	{
		return this.Nel;
	}

	
	/**
	 * Inicia/sobrescribe os valores dos pontos com os valores dos pontos em pts.
	 * A copia é feita ate onde seja possivel dependendo da quatidade de pontos 
	 * em cada objeto PdsPoints. É permitido fazer copias entre listas de pontos
	 * de distinto tamanho.
	 * 
	 * @param pts É uma lista de pontos a utilizar como fuente para a copia de dados.
	 **/
	public void set_points( PdsPoints pts )
	{
		int Nel2=pts.get_length();
		
		for(int i=0;(i<Nel)&&(i<Nel2);i++)
		P[i].setLocation(pts.get_point_from_id(i));
	}

	/**
	 * Inicia/sobrescribe os valores dos pontos com os valores de val0 e val1.
	 * Os pontos são conformados com a forma (val0[i],val1[i]).
	 * A copia é feita ate onde seja possivel dependendo da quatidade de elementos 
	 * em cada array. É permitido fazer copias entre listas de pontos
	 * de distinto tamanho. Só será feito a copia ate o menor dos arrays, ou de pontos.
	 * 
	 * @param val0 São os valores do eixo X no ponto (x,y).
	 * @param val1 São os valores do eixo Y no ponto (x,y).
	 **/
	public void set_arrays(double val0[],double val1[])
	{
		int N;
		if(val0.length > val1.length)
			N=val1.length;
		else
			N=val0.length;

		for(int i=0;((i<Nel)&&(i<N));i++)
		{
			if(P[i]!=null)
			{
				P[i].setLocation(val0[i],val1[i]);
			}
		}
	}

	/**
	 * Inicia/sobrescribe os valores do ponto na posição id com os valores do 
	 * ponto p. 
	 * 
	 * @param id O indice o elemento a modificar.
	 * @param p É o ponto a utilizar como fuente para a copia de dados.
	 **/
	public void set_point( int id, Point p )
	{
		this.P[id].setLocation(p);
	}

	/**
	 * Inicia/sobrescribe os valores do ponto na posição id com os valores do 
	 * ponto (x,y). 
	 * 
	 * @param id O indice do elemento a modificar.
	 * @param x É o valor do eixo X no ponto (x,y).
	 * @param y É o valor do eixo Y no ponto (x,y).
	 **/
	public void set_point( int id, double x, double y )
	{
		this.P[id].setLocation(x,y);
	}

	/**
	 * Inicia/sobrescribe os valores do ponto na posição id com os valores do 
	 * ponto (x,y). 
	 * 
	 * @param id O indice do elemento a modificar.
	 * @param x É o valor do eixo X no ponto (x,y).
	 * @param y É o valor do eixo Y no ponto (x,y).
	 **/
	public void set_point( int id, int x, int y )
	{
		this.P[id].setLocation(x,y);
	}	


	/**
	 * Se meu objeo chama-se P1, então este método retorna um novo ponto P com o
	 * resutado de P=P1-P2.
	 * P1 e P2 devem ter a mesma quantidade de pontos, caso contrario acontece
	 * IllegalArgumentException.
	 * Se algum ponto dentro de P1 ou P2 é null, então o ponto em P tambem será null. 
	 * 
	 * @param P2 É o ponto a usar como fonte da resta.
	 **/
	public PdsPoints new_diff_with(PdsPoints P2)
	{
		double x,y;
		Point p1;
		Point p2;
		
		if (Nel!=P2.get_length())
		{
			throw new IllegalArgumentException("Subtracting lists of points of different sizes.");
		}

		PdsPoints Pnew = new PdsPoints(Nel);
		
		for(int i=0;i<Nel;i++)
		{
			p1=P[i];
			p2=P2.get_point_from_id(i);
			
			if((p1!=null)&&(p2!=null))
			{
				x=p1.getX()-p2.getX();
				y=p1.getY()-p2.getY();
				Pnew.set_point(i,x,y);
			}
			else
			{
				Pnew.nullifies_the_point(i);
			}
			
		}
		
		return Pnew;
	}

	/**
	 * transforma em null o ponto apontado pelo id.
	 * 
	 * @param id O indice do elemento a nulificar.
	 **/
	public void nullifies_the_point(int id)
	{
		P[id]=null;
	}
	////////////////////////////////////////////////////////////////////
	
	/**
	 * Retorna um String com os valores de todos os pontos. Os pontos são 
	 * separados por um tabulador. Os pontos são escritos com a forma
	 * (x,y).
	 * 
	 * @return String com os valores de todos os pontos.
	 **/
	public String toString()
	{
		String S="";
		for(int i=0;i<Nel;i++)
		{	
			if(get_point_from_id(i)!=null)
			{
				S=S	+"("+	get_point_from_id(i).getX()+ 	","+
							get_point_from_id(i).getY()+	")";
			}
			else
			{
				S=S+"NULL";
			}
	
			if(i!=(Nel-1))	S=S+"\t";
			else			S=S+"\n";
		}
		return S;
	}	
}
