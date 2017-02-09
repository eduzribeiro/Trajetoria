package fun.eduardo.funcoes;

public class decide_detector {

	double DbDecidido;


	public decide_detector() {

		this.DbDecidido = 0;

	}


	public double decidido(double Db, double Db2) {

		if ((Db == 1) || (Db2 == 1)) {
			this.DbDecidido = 1;

		} else {

			this.DbDecidido = 0;

		}


		return DbDecidido;

	}

}
