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

import java.lang.*;
import java.util.*;
import java.io.*;

/** 
 * Esta classe implementa um vetor real com 3 elementos.
 *
 * <br><br> Para usar esta classe é necessário escrever:
 *  <pre>  import net.trucomanx.pdsplibj.pdsra.Pds3DPoint; </pre>
 * <br><br>
 * Os espaços de memoria são salvados em espaços
 * de memoria contínuos, não tem-se feito alocação dinâmica de memoria
 * para os elementos, isto foi para ganhar em velocidade de execução. 
 * <br>
 *
 * @author Fernando Pujaico Rivera <a href="mailto:fernando.pujaico.rivera@gmail.com">fernando.pujaico.rivera@gmail.com</a>
 * @version 0.01
 * @since 2015-05-25
 * @see <a href="http://pdsplib.sourceforge.net"> PDS Project Libraries in Java </a>
 */
public class Pds3DPoint extends PdsVector {


	/**
	 * Este construtor da classe é necessário inicializar-lo indicando o conteudo 
	 * do vetor.
	 * 
	 * @param a É o primeiro elemento do vetor.
	 * @param b É o segundo elemento do vetor.
	 * @param c É o terceiro elemento do vetor.
	 **/
	public Pds3DPoint(double a,double b,double c) {
		super(3);
		super.SetValue(0,a);
		super.SetValue(1,b);
		super.SetValue(2,c);
	}

	/**
	 * Este construtor da classe é necessário inicializar-lo indicando o valor 
	 * inicial de todos os elementos do vetor.
	 * 
	 * @param Val O valor de cada elemento do vetor.
	 **/
	public Pds3DPoint(double Val) {
		super(3);
		super.SetValue(0,Val);
		super.SetValue(1,Val);
		super.SetValue(2,Val);
	}

	/**
	 * Este construtor retorna um ponto em 3 dimenções inicializados com zero.
	 * 
	 **/
	public Pds3DPoint( ) {
		super(3);
	}

	/**
	 * Este construtor da classe é necessário inicializar-lo indicando um ponto a ser clonado.
	 * 
	 * @param PointSrc É o vetor de fonte.
	 **/
	public Pds3DPoint(Pds3DPoint PointSrc) {
		super(PointSrc);
	}

	/**
	 * Este construtor da classe é necessário inicializar-lo com um nome de arquivo
	 * com os dados do vetor.
	 * 
	 * @param datafile É nome do arquivo com os dados do vetor de fonte.
	 **/
	public Pds3DPoint(String datafile) {
		super(3);

		List<Double> list = new ArrayList<Double>();
		Scanner scanner = null;

		try{
			scanner = new Scanner(new File(datafile));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		while (scanner.hasNext()) 
		{

			list.add(Double.parseDouble(scanner.next()));

		}

		int Nel = list.size();
		
		for(int i=0; (i<Nel) && (i<3) ;i++)
			super.SetValue(i,list.get(i));
	}


}
