import net.trucomanx.pdsplibj.pdsra.*;

import com.eduardo.funcoes.*;

class Descartavel {

	//metodo1


	//met2




    public static void main(String[] args) {

	//double Ax,Ay,Az,Db;
	PdsVector Dat= new PdsVector(4);

        LinearAlignedAcc Procedimento=new LinearAlignedAcc();
        Integrator I=new Integrator(0);
	
	//Read L dados de file data

	for(i=0;i<L;i++)
	{
		Dat=Procedimento.get_data(RawAcc,RawW);
		//Ax=Dat.GetValue(1);
		//Ay=Dat.GetValue(2);
		//Az=Dat.GetValue(3);
		//Db=Dat.GetValue(4);

		I.integra_with_input(Dat);
		//Vx=I.Vx();
		//Vy=I.Vy();
		//Vz=I.Vz();
		//Sx=I.Sx();
		//Sy=I.Sy();
		//Sz=I.Sz();
		block_data.add(I.Dat);
	}
	BMP.make_files(block_data)
    }
}
