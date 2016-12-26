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


package net.trucomanx.pdsplibj.pdscalc;

/**
 * Esta classe gera um integrador usando o Método dos Trapézios
 * 
 * <br><br> Para usar esta classe é necessário escrever:
 *  <pre>  import net.trucomanx.pdsplibj.pdscalc.PdsIntegrator; </pre>
 * Um exemplo de código seria:
 * <pre>
 * {@code
 *  double Tau=2.0 //seconds
 *  PdsIntegrator integrator=new PdsIntegrator(Tau);
 *
 *  integrator.SetLastOutput(0);//Unnecessary by default last F is zero.
 *  
 *  for(t=a;t<=b;t=t+dt)
 *  {
 *      input=f(t);
 *      F=integrator.EvaluateValue(input,t);
 *  }
 *  integration_of_f_from_a_to_b= F;
 * }
 * </pre>
 * <br>
 *  <center><img src="{@docRoot}/data/imagenes/PdsIntegrator/PdsIntegrator.png" width=800 alt="integrando a sinal f(x)"></center><br>
 *
 * @author Fernando Pujaico Rivera <a href="mailto:fernando.pujaico.rivera@gmail.com">fernando.pujaico.rivera@gmail.com</a>
 * @version 0.01
 * @since 2015-11-25
 * @see <a href="http://pdsplib.sourceforge.net"> PDS Project Libraries in Java </a>
 */

public class PdsIntegrator {
	private double last_output;
	private double last_input;
	private double last_time;
	private double Tau;
	private boolean used;

    /**
     * Este construtor cria um integrador usando o Método dos Trapézios.
	 *
     * <br>
     * Ao ser criada uma instancia o valor anterior da integral é definido em
	 * zero. A instancia é declarada como nunca usada, pelo qual
	 * a seguinte entrada ao integrador usando {@link #EvaluateValue(double input_value,double time)}  
	 * não acumulara valor, só definirá a entrada anterior e o tempo anterior.
     */	
	public PdsIntegrator()
	{
		this.last_output=0;
		this.last_input=0;
		this.last_time=0;
		this.used=false;
		this.Tau=0;
	}

    /**
     * Este construtor cria um integrador usando o Método dos Trapézios com saída
	 * com proteção de acumulação.
	 *
     * <br>
     * Ao ser criada uma instancia o valor anterior da integral é definido em
	 * zero. A instancia é declarada como nunca usada, pelo qual 
	 * a seguinte entrada ao integrador usando {@link #EvaluateValue(double input_value,double time)}  
	 * não acumulara valor, só definirá a entrada anterior e o tempo anterior.
	 *
     * @param tau É o tempo em segundos para que quando a entrada do integrador 
	 * seja 0 a saída seja redefinida a um exp(-1) do seu valor anterior.
	 * Se tau é zero o tempo para ser redefinido é desabilitada.
     */	
	public PdsIntegrator(double tau)
	{
		this.last_output=0;
		this.last_input=0;
		this.last_time=0;
		this.used=false;
		this.Tau=tau;
	}

    /**
     * Esta função define o valor anterior da integral.
	 *
     * <br>
	 * A soma da integral iniciará desde o valor definido por esta função.
     * @param last_output É o valor anterior da integral
     */	
	public void SetLastOutput(double last_output)
	{
		this.last_output=last_output;
	}

    /**
	 * Define o tempo para desvanecer a saída quando a entrada é zero.
	 *
     * Esta função define o tempo em segundos para que quando a entrada do integrador 
	 * seja 0 a saída seja redefinida a um exp(-1) do seu valor anterior.
	 * Se tau é zero o tempo para ser redefinido é desabilitado.
	 *
     * @param tau É o tempo para desvanecer a saída quando a entrada é zero.
     */	
	public void SetTau(double tau)
	{
		this.Tau=tau;
	}

    /**
	 * Reinicia o integrador.
	 *
	 * O reset implica que se defina a ultima saída como last_output, e a
	 * seguinte entrada ao integrador usando {@link #EvaluateValue(double input_value,double time)}  
	 * não acumulara valor, só definirá a entrada anterior e o tempo anterior.
	 *
     * @param last_output É o valor anterior da integral
     */	
	public void Reset(double last_output)
	{
		this.last_output=last_output;
		this.used=false;
	}

    /**
	 * Reinicia o integrador.
	 *
	 * Este reset implica que se defina a ultima saída como zero, e a
	 * seguinte entrada ao integrador usando {@link #EvaluateValue(double input_value,double time)}  
	 * não acumulara valor, só definirá a entrada anterior e o tempo anterior.
     */	
	public void Reset()
	{
		this.last_output=0;
		this.used=false;
	}

    /**
	 * Esta função acumula um diferencial  da integral {@latex.inline $F(t_n)$}
	 * usando o método do trapezio.
	 *
	 * <center>{@latex.ilb %preamble{\\usepackage{amssymb}} %resolution{150} 
	 * $F(t_n) \\leftarrow \\frac{f(t_n)+f(t_{n-1})}{2} (t_n-t_{n-1})+ F(t_{n-1}) $
	 * }</center>
	 *
	 * Se Tau é diferente de cero, e a entrada {@latex.inline $f(t_n)$} do 
	 * integrador é zero, então
	 *
	 * <center>{@latex.ilb %preamble{\\usepackage{amssymb}} %resolution{150} 
	 * $F(t_n) \\leftarrow F(t_n) e^{-\\frac{t_n-t_{n-1}}{Tau}} $
	 * }</center>
	 *
	 * @param input_value  É o valor de entrada ao integrador.{@latex.inline $f(t_n) \\equiv input\\_value$}
	 * @param time         É o tempo da amostra input_value.{@latex.inline $t_n \\equiv time$}
	 * @return Retorn a integral de todas as amostras.
     */	
	public double EvaluateValue(double input_value,double time)	
	{
		double output_value;
		
		if (this.used==true)
		if(time<last_time)	
		{
			throw new IllegalArgumentException("The current time can't be minor to last time");
		}

		if (this.used==false)	
		{
			last_time = time;
			this.used = true;
		}


		output_value = 0.5*(input_value+last_input)*(time-last_time) + last_output;

		if((input_value==0)&&(this.Tau!=0))
		{
			output_value = output_value*Math.exp(-(time-last_time)/this.Tau);
		}

		last_input  = input_value;
		last_output = output_value;
		last_time   = time;
		
		return output_value;
	}
}
