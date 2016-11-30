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

package net.trucomanx.pdsplibj.pdsds;


/**
 * Esta classe gera um quantizador linear.
 * 
 * <br><br> Para usar esta classe é necessário escrever:
 *  <pre>  import net.trucomanx.pdsplibj.pdsds.PdsQuantizer; </pre>
 *
 * The Quantizer block passes its input signal u through a stair-step function 
 * so that many neighboring points on the input axis are mapped to one point 
 * on the output axis. The effect is to quantize a smooth signal into a 
 * stair-step output. The output is computed using the round-to-nearest 
 * method, which produces an output that is symmetric about zero.
 *
 * <center>{@latex.ilb %preamble{\\usepackage{amssymb}} %resolution{150} 
 * $y = Q ~ round(\\frac{u-Offset}{Q})$
 * }</center>
 *
 * where y is the output, u the input, Offset is an offset, and Q the Quantization interval parameter.
 * 
 * <center>{@latex.ilb %preamble{\\usepackage{amssymb}} %resolution{150} 
 * $y = 0 ~ \\forall ~ -Q/2< u-Offset < +Q/2$
 * }</center>
 *
 * Um exemplo de código seria:
 * <pre>
 *  PdsQuantizer quantizator = new PdsQuantizer(offset,Q);
 *  
 *  y=quantizator.EvaluateValue(u);
 * </pre>
 * <br>
 *  <center><img src="{@docRoot}/data/imagenes/PdsQuantizer/PdsQuantizer.png" alt="quantizando a sinal f(x)"></center><br>
 *
 * @author Fernando Pujaico Rivera <a href="mailto:fernando.pujaico.rivera@gmail.com">fernando.pujaico.rivera@gmail.com</a>
 * @version 0.01
 * @since 2015-11-25
 * @see <a href="http://pdsplib.sourceforge.net"> PDS Project Libraries in Java </a>
 */
public class PdsQuantizer {
	private double Q=0;
	private double Offset=0;

	public PdsQuantizer(double offset,double Q)
	{
		this.Q=Q;
		this.Offset=offset;
	}
	
	public double EvaluateValue(double u)
	
	{
		double y;
		
		y=this.Q * Math.round((u-this.Offset)/this.Q);

		return y;
	}
}
