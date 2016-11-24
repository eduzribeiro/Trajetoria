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

package net.trucomanx.pdsplibj.pdsextras;

import java.io.*;
 
/** 
 * Esta classe escreve dados em um arquivo de texto.
 * <br><br>
 *
 * <br><br> Para usar esta classe é necessário escrever:
 *  <pre>  import net.trucomanx.pdsplibj.pdsextras.PdsWriteData; </pre>
 *
 * <br>Exemplo de código de uso de  PdsWriteData, onde uma linha é escrita.
 *  <pre>
 *  String S="hola";  
 *  PdsWriteData fdout= new PdsWriteData('/ruta/do/arquivo.txt');
 *
 *  S=fdout.Println(S);
 *  
 *  fdout.Close();
 *  </pre>
 *
 * 
 * @author Fernando Pujaico Rivera <a href="mailto:fernando.pujaico.rivera@gmail.com">fernando.pujaico.rivera@gmail.com</a>
 * @version 0.05
 * @since 2015-05-25
 * @see <a href="http://pdsplib.sourceforge.net"> PDS Project Libraries in Java </a>
*/ 
public class PdsWriteData
{

	private FileWriter fichero = null;
	private PrintWriter pw = null;
	
	/**
	 * Este construtor da classe é necessário inicializa-lo indicando o nome
	 * do arquivo que deseja-se abrir em modo escritura.
	 * 
	 * @param path_with_filename É o nome do arquivo a escrever.
	 **/	
	public PdsWriteData(String path_with_filename){
		try{
			this.fichero	= new FileWriter(path_with_filename);
			this.pw		= new PrintWriter(this.fichero);
		} 
		catch (Exception e){
			e.printStackTrace();
			
			try{// Nuevamente aprovechamos el finally para 
				// asegurarnos que se cierra el fichero.
				if (null != this.fichero)	this.fichero.close();
			} 
			catch (Exception e2){
					e2.printStackTrace();
			}
		} 
	}
	

	/**
	 * Esta função escreve um texto no arquivo.
	 *
	 * <br>
	 * @param cadena É o texto a escrever.
	 **/
	public void Print(String cadena){
		try{
			this.pw.print(cadena);
		}
		catch (Exception e){
					e.printStackTrace();
		}
	}


	/**
	 * Esta função escreve um texto no arquivo. Tambem agrega um salto de linha 
	 * ao final.
	 *
	 * <br>
	 * @param cadena É o texto a escrever.
	 **/
	public void Println(String cadena){
		try{
			this.pw.println(cadena);
		}
		catch (Exception e){
					e.printStackTrace();
		}
	}

	/**
	 * Este método fecha o arquivo, que estava-se escrevendo.
	 *
	 * <br>
	 * É fechado o manejador de arquivo que estava aberto em modo escritura.
	 **/
	public void Close(){
		try{
			if(this.fichero!=null)
			{
				this.fichero.close();
			}
			
		}
		catch (Exception e){
					e.printStackTrace();
		}
	}
    
}
