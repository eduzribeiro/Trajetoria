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

import net.trucomanx.pdsplibj.pdsra.PdsPoints;

/**
 * É uma classe que deve ser vista como uma estrutura que contem 3 variáveis
 * do tipo PdsPoints. As variaveis são initial_points, final_points e vectors.
 *
 * <br><br> Para usar esta classe é necessário escrever:
 * <pre>
	import net.trucomanx.pdsplibj.pdspiv.PdsPivData; 
 * </pre>
 *
 * @author Fernando Pujaico Rivera <a href="mailto:fernando.pujaico.rivera@gmail.com">fernando.pujaico.rivera@gmail.com</a>
 * @version 0.01
 * @since 2015-11-00
 * @see <a href="http://pdsplib.sourceforge.net"> PDS Project Libraries in Java </a>
 */
public class PdsPivData{
	/**
	 * Esta variável deve ser usada para conter os pontos iniciais do cálculo PIV.
	 */
	public PdsPoints initial_points;
	/**
	 * Esta variável deve ser usada para conter os pontos finais do cálculo PIV.
	 * É dizer o resultado do match com os pontos iniciais. 
	 */
	public PdsPoints final_points;
	/**
	 * Esta variável existe para que nela seja depositada a diferencia entre 
	 * initial_points e final_points. 
	 */
	public PdsPoints vectors;

	/**
	 * Cria um objeto do tipo PdsPivData com as variáveis internas: initial_points
	 * final_points e vectors do tipo PdsPoints com N elementos.
	 *
	 * @param N Número de pontos nas variaveis internas: initial_points
	 * final_points e vectors do tipo PdsPoints.
	 */
	public PdsPivData(int N)
	{
		initial_points = new PdsPoints(N);
		final_points   = new PdsPoints(N);
		vectors        = new PdsPoints(N);
	}
	
	/**
	 * Cria um objeto do tipo PdsPivData com as variaveis internas: initial_points
	 * final_points e vectors do tipo PdsPoints iguais a null.
	 */
	public PdsPivData()
	{
		initial_points = null;
		final_points   = null;
		vectors        = null;
	}


	/*
	 * Função para sobre escribir referencia de los puntos iniciales

	public void set_initial_points(PdsPoints pts)
	{
		initial_points=pts;
	}
	 */

	/*
	 * Função para escribir dados de los puntos iniciales.

	public void set_data_of_initial_points(PdsPoints pts)
	{
		initial_points.set_points(pts);
	}
	 */

	/*
	 * Função para pedir referenci de dados.

	public PdsPoints get_initial_points()
	{
		return initial_points;
	}
	 */

	/*
	 * Função para pedir referenci de dados.

	public PdsPoints get_final_points()
	{
		return final_points;
	
	}
	 */

	/*
	 * Função para pedir referenci de dados.

	public PdsPoints get_vectors()
	{
		return vectors;
	}
	 */
}
