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


package net.trucomanx.pdsplibj.pdsdf;


import  net.trucomanx.pdsplibj.pdsra.*;


public class PdsSpline {
	private int M,N; //N: número de amostras retornadas (este valor pode ser infinito); M: número de amostras necessárias para calcular o polinômio.
	private PdsFifoValue T;
	private PdsFifoValue X;
	private PdsVector C;
	private PdsMatrix Mat;
	
	public PdsSpline(int N) {
		this.N = N;		
		this.M = 4;
		this.T=new PdsFifoValue(this.M);
		this.X=new PdsFifoValue(this.M);
		this.C=new PdsVector(this.M);

		this.T.WriteValue(-4.0);
		this.T.WriteValue(-3.0);
		this.T.WriteValue(-2.0);
		this.T.WriteValue(-1.0);

		//this.C.InitValue(1.0);

		this.Mat = new PdsMatrix(this.M,this.M);
		
	}

	public PdsVector EvaluateValue(double xvalue, double tvalue,String type) {
		PdsVector TMP;
		this.X.WriteValue(xvalue);
		this.T.WriteValue(tvalue);
		double x,t;
		
		TMP=new PdsVector(this.N);

		PdsVector P = GetFuncParam();
		
		//calculando M

		double t1=T.GetValue(1);
		double t2=T.GetValue(2);

		this.Mat.SetValue(0,0,1);
		this.Mat.SetValue(1,0,1);
		this.Mat.SetValue(2,0,0);
		this.Mat.SetValue(3,0,0);

		this.Mat.SetValue(0,1,t1);
		this.Mat.SetValue(1,1,t2);
		this.Mat.SetValue(2,1,1);
		this.Mat.SetValue(3,1,1);

		this.Mat.SetValue(0,2,t1*t1);
		this.Mat.SetValue(1,2,t2*t2);
		this.Mat.SetValue(2,2,2*t1);
		this.Mat.SetValue(3,2,2*t2);

		this.Mat.SetValue(0,3,t1*t1*t1);
		this.Mat.SetValue(1,3,t2*t2*t2);
		this.Mat.SetValue(2,3,3*t1*t1);
		this.Mat.SetValue(3,3,3*t2*t2);

		PdsMatrix MatNew = this.Mat.InverseNew();

		if (P == null)

		System.out.println("P Vazio");

		if (MatNew == null)

		System.out.println("MatNew Vazio"+this.Mat);

		this.C = MatNew.MulNewVector(P);

		//calculo C a partir de P

		for (int i =0; i<this.N;i++){

			t = T.GetValue(2) + i*(T.GetValue(1) - T.GetValue(2))/this.N;
			if(type == "uniform")
				x = X.GetValue(2) + i*(X.GetValue(1) - X.GetValue(2))/this.N;
			else
				x = C.GetValue(3)*t*t*t + C.GetValue(2)*t*t + C.GetValue(1)*t + C.GetValue(0);

			TMP.SetValue(i,x);

		//Falta procedimento
		}

		return TMP;
	}

	private PdsVector GetFuncParam(){

		double m2,m1;

		if (this.T.GetValue(2)==this.T.GetValue(3)){

			m2 = 0;

		}else{

			m2 = ((this.X.GetValue(2) - this.X.GetValue(3))/(this.T.GetValue(2) - this.T.GetValue(3)))*0.5;
			m2 = m2+((this.X.GetValue(1) - this.X.GetValue(2))/(this.T.GetValue(1) - this.T.GetValue(2)))*0.5;

		}


		if (this.T.GetValue(0)==this.T.GetValue(1)){

			m1 = 0;

		}else{

			m1 = ((this.X.GetValue(0) - this.X.GetValue(1))/(this.T.GetValue(0) - this.T.GetValue(1)))*0.5;
			m1 = m1+((this.X.GetValue(1) - this.X.GetValue(2))/(this.T.GetValue(1) - this.T.GetValue(2)))*0.5;

		}


		PdsVector Params = new PdsVector(4);
		Params.SetValue(0,this.X.GetValue(1));
		Params.SetValue(1,this.X.GetValue(2));
		Params.SetValue(2,m1);
		Params.SetValue(3,m2);

		return Params;

	} 
	
		
}
