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

import  net.trucomanx.pdsplibj.pdsra.PdsPoints;

/**
 * É uma classe que deve ser vista como uma estrutura que contem variáveis
 * de configuração.
 *
 * <br><br> Para usar esta classe é necessário escrever:
 * <pre>
	import net.trucomanx.pdsplibj.pdspiv.PdsPivConf; 
 * </pre>
 *
 * @author Fernando Pujaico Rivera <a href="mailto:fernando.pujaico.rivera@gmail.com">fernando.pujaico.rivera@gmail.com</a>
 * @version 0.01
 * @since 2015-11-00
 * @see <a href="http://pdsplib.sourceforge.net"> PDS Project Libraries in Java </a>
 */
public class PdsPivConf{

	private int 	roi_window_size;
	private int 	points_by_columns;
	private int 	points_by_lines;
	private double 	search_threshold;

	private int 	search_step_size;
	private int 	search_max_length;
	
	private boolean verbose_flag;

	/**
	 * Este construtor inicializa o objeto do tipo PdsPivConf com o seus valores 
	 * omissão.
	 * <p><ul>
	 * <li> roi_window_size: 64
	 * <li> search_step_size: 16
	 * <li> search_max_length: 128
	 * <li> points_by_columns: 10 
	 * <li> points_by_lines:10
	 * <li> verbose_flag: false
	 * <li> search_threshold:0.8
	 * </ul>
	 */
	public PdsPivConf()
	{
		roi_window_size		=	64;
		search_step_size	=	16;
		search_max_length	=	128;
		points_by_columns	=	10;
		points_by_lines		=	10;
		verbose_flag		=	false;
		search_threshold	=	0.8;
	}
	

	/**
	 * Retorna o valor da variável search_threshold.
	 * search_threshold é usado para indicar o limear a partir do qual
	 * a busca poderá ser considerada como exitosa. 
	 * +1.00 indica uma coincidência do 100%
	 *  0.00 indica sem coincidência
	 * -1.00 indica que as regiões comparadas são opostas.
	 *
	 * @return Retorna o valor da variável search_threshold.
	 */
	public double get_search_threshold()
	{
		return search_threshold;
	}
	
	/**
	 * Atribui o valor da variável search_threshold.
	 * search_threshold é usado para indicar o limear a partir do qual
	 * a busca poderá ser considerada como exitosa. 
	 * +1.00 indica uma coincidência do 100%
	 *  0.00 indica sem coincidência
	 * -1.00 indica que as regiões comparadas são opostas.
	 *
	 * @param val Atribui o valor da variável search_threshold.
	 */	
	public void set_search_threshold(double val)
	{
		search_threshold=val;
	}

	/**
	 * Retorna o valor da variável verbose_flag.
	 * verbose_flag igual a true, habilita a saída de informação pela tela.
	 *
	 * @return Retorna o valor da variável verbose_flag.
	 */
	public boolean get_verbose_flag()
	{
		return verbose_flag;
	}

	/**
	 * Atribui o valor da variável verbose_flag.
	 * verbose_flag igual a true, habilita a saída de informação pela tela.
	 *
	 * @param val Atribui o valor da variável verbose_flag.
	 */	
	public void set_verbose_flag(boolean val)
	{
		verbose_flag=val;
	}

	/**
	 * Retorna o valor da variável roi_window_size.
	 * roi_window_size é o lado em pixeis da região de interesse, esta região é quadrada.
	 *
	 * @return Retorna o valor da variável roi_window_size.
	 */
	public int get_roi_window_size()
	{
		return roi_window_size;
	}
	
	/**
	 * Atribui o valor da variável roi_window_size.
	 * roi_window_size é o lado em pixeis da região de interesse, esta região é quadrada.
	 *
	 * @param val Atribui o valor da variável roi_window_size.
	 */	
	public void set_roi_window_size(int val)
	{
		roi_window_size=val;
	}

	/**
	 * Retorna o valor da variável search_step_size.
	 * search_step_size é o passo, em pixeis, da busca de regiões coincidentes a ROI.
	 *
	 * @return Retorna o valor da variável search_step_size.
	 */
	public int get_search_step_size()
	{
		return search_step_size;
	}

	/**
	 * Atribui o valor da variável search_step_size.
	 * search_step_size é o passo, em pixeis, da busca de regiões coincidentes a ROI.
	 *
	 * @param val Atribui o valor da variável search_step_size.
	 */	
	public void set_search_step_size(int val)
	{
		search_step_size=val;
	}

	/**
	 * Retorna o valor da variável search_max_length.
	 * search_max_length é a distancia máxima, em pixeis, da busca de regiões coincidentes a ROI.
	 *
	 * @return Retorna o valor da variável search_max_length.
	 */
	public int get_search_max_length()
	{
		return search_max_length;
	}

	/**
	 * Atribui o valor da variável search_max_length.
	 * search_max_length é a distancia máxima, em pixeis, da busca de regiões coincidentes a ROI.
	 *
	 * @param val Atribui o valor da variável search_max_length.
	 */	
	public void set_search_max_length(int val)
	{
		search_max_length=val;
	}
	

	/**
	 * Retorna o valor da variável points_by_columns.
	 * points_by_columns é o número de pontos por coluna numa grid.
	 *
	 * @return Retorna o valor da variável points_by_columns.
	 */
	public int get_points_by_columns()
	{
		return points_by_columns;
	}

	/**
	 * Atribui o valor da variável points_by_columns.
	 * points_by_columns é o número de pontos por coluna numa grid.
	 *
	 * @param val Atribui o valor da variável points_by_columns.
	 */	
	public void set_points_by_columns(int val)
	{
		points_by_columns=val;
	}
	

	/**
	 * Retorna o valor da variável points_by_lines.
	 * points_by_lines é o número de pontos por linha numa grid.
	 *
	 * @return Retorna o valor da variável points_by_lines.
	 */
	public int get_points_by_lines()
	{
		return points_by_lines;
	}

	
	/**
	 * Atribui o valor da variável points_by_lines.
	 * points_by_lines é o número de pontos por linha numa grid.
	 *
	 * @param val Atribui o valor da variável points_by_lines.
	 */
	public void set_points_by_lines(int val)
	{
		points_by_lines=val;
	}


}
