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

import java.util.*;
import java.io.*;

/** 
 * Esta classe lee dados de um arquivo de texto.
 * <br><br>
 *
 * <br><br> Para usar esta classe é necessário escrever:
 *  <pre>  import net.trucomanx.pdsplibj.pdsextras.PdsReadData; </pre>
 *
 * <br>Exemplo de código de uso de  PdsReadData, onde uma linha é lida.
 *  <pre>
 *  String S;  
 *  PdsReadData fdin= new PdsReadData('/ruta/do/arquivo.txt');
 *
 *  S=fdin.Scan();
 *  
 *  fdin.Close();
 *  </pre>
 *
 * 
 * @author Fernando Pujaico Rivera <a href="mailto:fernando.pujaico.rivera@gmail.com">fernando.pujaico.rivera@gmail.com</a>
 * @version 0.05
 * @since 2015-05-25
 * @see <a href="http://pdsplib.sourceforge.net"> PDS Project Libraries in Java </a>
*/ 
public class PdsReadData{
	
	private File archivo = null;
	private FileReader fr = null;
	private BufferedReader br = null;
	private Scanner scan = null;

	/**
	 * Este construtor da classe é necessário inicializa-lo indicando o nome
	 * do arquivo que deseja-se abrir em modo leitura.
	 * 
	 * @param path_with_filename É o nome do arquivo a ler.
	 **/	
	public PdsReadData(String path_with_filename){
		try{
			this.archivo	= new File (path_with_filename);
			if(this.archivo.exists())
			{
				this.fr			= new FileReader(this.archivo);
				if(this.fr!=null)
				{
					this.br		= new BufferedReader(this.fr);
					this.scan	= new Scanner(this.br);
				}
				else
				{
					this.archivo=null;
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			
			try{// Nuevamente aprovechamos el finally para 
				// asegurarnos que se cierra el fichero.
				if (null != this.fr)		this.fr.close();
				//if (null != this.archivo)	this.archivo=null;
			} 
			catch (Exception e2){
					e2.printStackTrace();
			}
		} 
	}

	/**
	 * Esta função retorna true si el archivo existe.
	 *
	 * <br>
	 * @return retorna true si el archivo existe.
	 **/	
	public boolean Exist(){
		if(this.archivo!=null)
			return this.archivo.exists();
		else
			return false;
	}


	/**
	 * Esta função lee uma linha do arquivo aberto em modo leitura.
	 * retorna null si no existe linea.
	 *
	 * <br>
	 * @return A linha lida.
	 **/	
	public String Scan(){
		String cadena=null;
		
		if ((this.scan.hasNextLine()) == false)
		{
			return null;
		}

		try{
			cadena=this.scan.nextLine();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return cadena;
	}

	/**
	 * Esta função lee uma linha do arquivo aberto em modo leitura.
	 * Esta função é similar a Scan().
	 *
	 * <br>
	 * @return A linha lida.
	 **/
	public String ReadLine(){

		String cadena=Scan();

		return cadena;
	}
	
	/**
	 * Esta função lee um dado do arquivo aberto em modo leitura.
	 *
	 * <br>
	 * @return O dado lido.
	 **/	
	public String ReadData(){
		String cadena=null;
		
		try{
			
			if(this.scan.hasNext())
			{
				cadena=this.scan.next();
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return cadena;
	}

	/**
	 * Este método fecha o arquivo, que estava-se lendo.
	 *
	 * <br>
	 * É fechado o manejador de arquivo que estava aberto em modo leitura.
	 **/
	public void Close(){
		try{
			if(this.br!=null)
			{
				this.br.close();
				if(this.fr!=null)
				{
					this.fr.close();
				}
			}
			this.archivo=null;
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

   
}
