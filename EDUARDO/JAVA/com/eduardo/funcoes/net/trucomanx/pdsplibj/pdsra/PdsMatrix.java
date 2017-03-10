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

/** 
 * Esta classe implementa uma matriz real com N elementos.
 *
 * <br><br> Para usar esta classe é necessário escrever:
 *  <pre>  import net.trucomanx.pdsplibj.pdsra.PdsMatrix; </pre>
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
public class PdsMatrix {
	private int Nlin;
	private int Ncol;
	private double[][] M;

	/**
	 * Este construtor da classe é necessário inicializar-lo indicando o 
	 * numero Nlin de linha e Ncol de colunas da matriz.
	 * 
	 * @param Nlin É o numero de linhas da matriz.
	 * @param Ncol É o numero de colunas da matriz.
	 **/
	public PdsMatrix(int Nlin,int Ncol) {
		this.Nlin = Nlin;
		this.Ncol = Ncol;
		this.M=new double[this.Nlin][this.Ncol];
		
		if(M==null){
			this.Nlin = 0;
			this.Ncol = 0;
		}
	}

	/**
	 * Este construtor da classe é necessário inicializar-lo indicando 
	 * uma matriz a ser clonada.
	 * 
	 * @param MatSrc É a matriz de fonte.
	 **/
	public PdsMatrix(PdsMatrix MatSrc) {
		if(MatSrc!=null){
			this.Nlin = MatSrc.Nlin;
			this.Ncol = MatSrc.Ncol;
			
			this.M=new double[this.Nlin][this.Ncol];
			
			if(this.M==null){
				this.Nlin = 0;
				this.Ncol = 0;
			}

			for(int i=0; i<this.Nlin;i++){
				for(int j=0; j<this.Ncol;j++){
					this.M[i][j]=MatSrc.M[i][j];
				}
			}
		}
		else{
			this.Nlin = 0;
			this.Ncol = 0;
			this.M=null;
		}
	}

	/**
	 * Este método inicia toda a matriz com o valor Val
	 * <br>
	 * 
	 * @param Val É o valor que tomará todos os elementos da matriz.
	 **/
	public void InitValue(double Val) {

		for(int i=0;i<this.Nlin;i++)	
			for(int j=0;j<this.Ncol;j++)
				this.M[i][j]=Val;
	}
	
	/**
	 * Este método retorna o número de linhas.
	 * <br>
	 * 
	 * @return retorna o número de linhas.
	 **/
	public int GetNlin() {
		return this.Nlin;	
	}

	/**
	 * Este método retorna o número de colunas.
	 * <br>
	 * 
	 * @return retorna o número de colunas.
	 **/
	public int GetNcol() {
		return this.Ncol;	
	}

	/**
	 * Este método inicia toda a matriz com a matriz MatSrc.
	 * <br>
	 * 
	 * se a matriz MatSrc é menor ou maior, então se copiará ate onde se possa.
	 * @param MatSrc É a matriz de fonte de onde se copiarão os dados.
	 **/
	public void InitMatrix(PdsMatrix MatSrc) {
		if(MatSrc==null){		
			for(int i=0;(i<this.Nlin)&&(i<MatSrc.Nlin);i++)	
				for(int j=0;(j<this.Ncol)&&(j<MatSrc.Ncol);j++)
					this.M[i][j]=MatSrc.M[i][j];
		}
	}

	/**
	 * Este método inicia um elemento da matriz com o valor Val
	 * <br>
	 * 
	 * @param idlin É o indicie da linha do elemento a iniciar, id inicia em cero.
	 *          se o índice esta fora de rango não da erro simplesmente não faz nada.  
	 * @param idcol É o indicie da coluna do elemento a iniciar, id inicia em cero.
	 *          se o índice esta fora de rango não da erro simplesmente não faz nada.  
	 * @param Val É o valor que tomará o elemento da matriz.
	 **/
	public void SetValue(int idlin,int idcol,double Val) {
		if( (idlin>=0) && (idlin<this.Nlin) )
		if( (idcol>=0) && (idcol<this.Ncol) )
		this.M[idlin][idcol]=Val;
	}
	
	/**
	 * Este método lê um elemento da matriz.
	 * <br>
	 * 
	 * @param idlin É o indicie da linha do elemento a ler, id inicia em cero.
	 *          se o índice esta fora de rango não da erro simplesmente não faz nada.  
	 * @param idcol É o indicie da coluna do elemento a ler, id inicia em cero.
	 *          se o índice esta fora de rango não da erro simplesmente não faz nada.  
	 * @return Retorna o valor que toma o elemento (idlin,idcol) da matriz.
	 **/
	public double GetValue(int idlin,int idcol) {
		if( (idlin>=0) && (idlin<this.Nlin) && (idcol>=0) && (idcol<this.Ncol) ){
			return this.M[idlin][idcol];
		}
		else
		{
			return 0.0;
		}
		
	}

	/**
	 * Este método inicia toda a matriz com a matriz identidade de 
	 * diagonal com valor Val
	 * <br>
	 * 
	 * @param Val É o valor que tomará todos os elementos da diagonal 
	 * da matriz, o resto de valores tomarão o valor zero.
	 **/
	public void InitIdentity(double Val) {

		for(int i=0;i<this.Nlin;i++)	
			for(int j=0;j<this.Ncol;j++)
				if(i==j)	this.M[i][j]=Val;
				else		this.M[i][j]=0;

				
	}


	/**
	 * Este método intercambia duas linhas da matriz.
	 * <br>
	 * 
	 * @param i Primeira linha a intercambiar.
	 * @param j Segunda linha a intercambiar.
	 **/
	public void SwapRows(int i,int j) {
		double[] temp = this.M[i];
        this.M[i] = this.M[j];
        this.M[j] = temp;
	}

	/**
	 * alpha*Row[i]+beta*Row[j]-->Row[j]
	 * <br>
	 * 
	 * @param i Primeira linha a somar.
	 * @param j Segunda linha a somar e a que var receber os dados.
	 * @param alpha Multiplicador da primeira linha a somar.
	 * @param beta Multiplicador da segunda linha a somar.
	 **/
	public void AddRows(int i,int j,double alpha,double beta) {

        for(int c=0;c<this.Ncol;c++)
            this.M[j][c] = alpha*this.M[i][c]+beta*this.M[j][c];
	}

	/**
     * Intercambia a linha id com qualquer linha inferior que tenha um valor
     * diferente de 0 na coluna id, solo si necesario.
	 * 
	 * @param id linha a analizar.
     * @return Retorna false si no es posivel fazer o elemento da linha id coluna 
     * id, diferente de cero, por nenhum tipo de intercambio. Caso contrario retorna
     * true.
	 **/
    private boolean swap_row_of_the_diagonal_element_with_the_first_above_non_zero(int id){
        
        if  (this.M[id][id]==0)
        {
            for(int i=id+1;i<this.Nlin;i++)
            {
                if(this.M[i][id]!=0)
                {
                    this.SwapRows(id,i);
                    return true;
                }
                    
            }
            return false;
        }

        return true;  
    }

	/**
	 * Este método multiplica uma matriz M com a matriz MatSrc e retorna 
	 * uma nova matriz.
	 * <br>
	 * Mout=M*Matsrc. Se o numero de linhas da matriz MatSrc e diferente 
	 * que o numero de colunas da matriz, então retorna null.
	 * @param MatSrc É a matriz de fonte para a multiplicaçao Mout=M*Matsrc.
	 * @return Retorna a matriz Mout de Mout=M*Matsrc.
	 **/
	public PdsMatrix MulNew(PdsMatrix MatSrc) {
		PdsMatrix	Mat=this;
		PdsMatrix	Mout=null;
		int N;
		
		if( MatSrc==null )				return null;
		if( Mat.Ncol != MatSrc.Nlin )	return null;
		
		N=Mat.Ncol;
		
		Mout=new PdsMatrix(Mat.Nlin,MatSrc.Ncol);
		if(Mout==null)	return null;
				
		for(int i=0;i<Mat.Nlin;i++)	
		{
			for(int j=0;j<MatSrc.Ncol;j++)
			{
				Mout.M[i][j]=0;
				for(int k=0;k<N;k++)
				{
					Mout.M[i][j]=Mout.M[i][j]+Mat.M[i][k]*MatSrc.M[k][j];
				}
			}
		}
		return Mout;
	}





	public PdsVector MulNewVector(PdsVector MatSrc) {
		PdsMatrix	Mat=this;
		PdsVector	Mout=null;
		int N;
		
		if( MatSrc==null )				return null;
		if( Mat.Ncol != MatSrc.GetNelements() )	return null;
		
		N=Mat.Ncol;
		
		Mout=new PdsVector(Mat.Nlin);
		if(Mout==null)	return null;
				
		for(int i=0;i<Mat.Nlin;i++)	
		{
				Mout.SetValue(i,0);
				for(int k=0;k<N;k++)
				{
					double val = Mout.GetValue(i)+Mat.M[i][k]*MatSrc.GetValue(k);					
					Mout.SetValue(i,val);
				}
		}
		return Mout;
	}


	/**
	 * Este método retorna a transposta de uma matriz M.
	 * <br>
	 * 
	 * @return Retorna a matriz Mout de Mout=M^T.
	 **/
	public PdsMatrix TransposeNew() {
        
        PdsMatrix Mout = new PdsMatrix(this.Ncol, this.Nlin);
        boolean b;
        
        if(Mout==null)	return null;
        
        for (int i = 0; i < this.Nlin; i++)
            for (int j = 0; j < this.Ncol; j++)
                Mout.M[j][i] = this.M[i][j];
        return Mout;
	}

	/**
	 * Realiza uma eliminação Gauss-Jordam sobre a matriz.
	 *
	 * @return Retorna true se foi possivel fazer a eliminação gauss jordam a 
     * todas as linhas, em caso contrario retorna false e a matriz é alterada
     * ate onde foi possivel.
	 * */
	public boolean GaussianElimination() {
        boolean b;
        for (int lin = 0; lin < this.Nlin; lin++)
        {
            // garantizo que el valor de la diagonal no es cero
            b=this.swap_row_of_the_diagonal_element_with_the_first_above_non_zero(lin);
            if(b==false)    return false;
    
            // normalizo el valor de la diagonal a uno.
            b=this.adjust_row_of_the_diagonal_element_with_the_value(lin,1.0);
            if(b==false)    return false;

            for (int j = lin+1; j < this.Nlin; j++)
            {
                this.AddRows(lin,j,-this.M[j][lin],1.0);
            }

        }   

        return true;

    }

	/**
	 * retorna a inversa da matriz numa nova matriz.
	 *
	 * @return Retorna uma nova matriz com a inversa.
	 * */
	public PdsMatrix InverseNew() {

        if(this.Nlin!=this.Ncol)    return null;


        PdsMatrix Mext = new PdsMatrix(this.Nlin, 2*this.Ncol);

        PdsMatrix Minv = new PdsMatrix(this.Nlin, this.Ncol);
        boolean b;
        
        if(Mext==null)	return null;
                
        //creando matriz composta {M I}
        for (int i = 0; i < this.Nlin; i++)
        {
            for (int j = 0; j < this.Ncol; j++)
            {
                Mext.M[i][j] = this.M[i][j];
            }
            Mext.M[i][this.Ncol+i]=1.0;
        }

        //haciendo matriz escalonada
        b=Mext.GaussianElimination();
        if(b==false)    return null;

         //limpando a diagonal superior.
        for (int i = this.Nlin-1; i >=0; i--)
        {
            for(int j=0;j<i;j++)
            Mext.AddRows(i,j,-Mext.M[j][i],1.0);
        }       

        //pegando dados finais
        for (int i = 0; i < this.Nlin; i++)
        {
            for (int j = 0; j < this.Ncol; j++)
            {
                Minv.M[i][j] = Mext.M[i][this.Ncol+j];
            }
        }
        return Minv;
	}

	/**
	 * Multiplica todos os valor da linha id, de modo que el elemento da columna
     * id tenha um valor igual a val.
	 *
     * @param id Linha a analizar.
     * @param val valor que debe ter o elemento da lina id e coluna id.
	 * @return Retorna true si foi possivel ajustar a linha.
	 * */
    private boolean adjust_row_of_the_diagonal_element_with_the_value(int id,double val) {

        if(this.M[id][id]==0)   return false;
        if(id>=this.Ncol)   return false;
        if(id>=this.Nlin)   return false;

        double v=this.M[id][id];

        for(int i=0;i<this.Ncol;i++)
        {
            this.M[id][i]=this.M[id][i]/v;
        }

        return true;
    }

	/**
	 * Este método provoca que se usamos uma instância de PdsMatrix
	 * num contexto que necessita-se ser String, então esta instância
	 * retorna uma descrição da matriz.
	 *
	 * @return Retorna uma descrição da matriz.
	 * */
	public String toString() {
		String TMP;
		TMP="";
		for(int i=0;i<this.Nlin;i++){
			for(int j=0;j<this.Ncol;j++){
				TMP=TMP+ this.M[i][j] + "\t";
			}
			TMP=TMP+"\n";
		}
		
		return TMP;
	}
}
