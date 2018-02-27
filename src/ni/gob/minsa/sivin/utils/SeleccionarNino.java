package ni.gob.minsa.sivin.utils;

public class SeleccionarNino {
	
	
	public Integer numSeleccionado(Integer encuesta,Integer numNinos)
    {
		Integer numSeleccionado=1;
		encuesta = encuesta % 10;
		if(encuesta==0) {
			if(numNinos==1) {
				numSeleccionado=1;
			}
			else if(numNinos==2) {
				numSeleccionado=2;
			}
			else if(numNinos==3) {
				numSeleccionado=2;
			}
			else if(numNinos==4) {
				numSeleccionado=4;
			}
			else if(numNinos==5) {
				numSeleccionado=3;
			}
			else if(numNinos==6) {
				numSeleccionado=6;
			}
		}
		else if(encuesta==1) {
			if(numNinos==1) {
				numSeleccionado=1;
			}
			else if(numNinos==2) {
				numSeleccionado=1;
			}
			else if(numNinos==3) {
				numSeleccionado=3;
			}
			else if(numNinos==4) {
				numSeleccionado=1;
			}
			else if(numNinos==5) {
				numSeleccionado=4;
			}
			else if(numNinos==6) {
				numSeleccionado=1;
			}
		}
		else if(encuesta==2) {
			if(numNinos==1) {
				numSeleccionado=1;
			}
			else if(numNinos==2) {
				numSeleccionado=2;
			}
			else if(numNinos==3) {
				numSeleccionado=1;
			}
			else if(numNinos==4) {
				numSeleccionado=2;
			}
			else if(numNinos==5) {
				numSeleccionado=5;
			}
			else if(numNinos==6) {
				numSeleccionado=2;
			}
		}
		else if(encuesta==3) {
			if(numNinos==1) {
				numSeleccionado=1;
			}
			else if(numNinos==2) {
				numSeleccionado=1;
			}
			else if(numNinos==3) {
				numSeleccionado=2;
			}
			else if(numNinos==4) {
				numSeleccionado=3;
			}
			else if(numNinos==5) {
				numSeleccionado=1;
			}
			else if(numNinos==6) {
				numSeleccionado=3;
			}
		}
		else if(encuesta==4) {
			if(numNinos==1) {
				numSeleccionado=1;
			}
			else if(numNinos==2) {
				numSeleccionado=2;
			}
			else if(numNinos==3) {
				numSeleccionado=3;
			}
			else if(numNinos==4) {
				numSeleccionado=4;
			}
			else if(numNinos==5) {
				numSeleccionado=2;
			}
			else if(numNinos==6) {
				numSeleccionado=4;
			}
		}
		else if(encuesta==5) {
			if(numNinos==1) {
				numSeleccionado=1;
			}
			else if(numNinos==2) {
				numSeleccionado=1;
			}
			else if(numNinos==3) {
				numSeleccionado=1;
			}
			else if(numNinos==4) {
				numSeleccionado=1;
			}
			else if(numNinos==5) {
				numSeleccionado=3;
			}
			else if(numNinos==6) {
				numSeleccionado=5;
			}
		}
		else if(encuesta==6) {
			if(numNinos==1) {
				numSeleccionado=1;
			}
			else if(numNinos==2) {
				numSeleccionado=2;
			}
			else if(numNinos==3) {
				numSeleccionado=2;
			}
			else if(numNinos==4) {
				numSeleccionado=2;
			}
			else if(numNinos==5) {
				numSeleccionado=4;
			}
			else if(numNinos==6) {
				numSeleccionado=6;
			}
		}
		else if(encuesta==7) {
			if(numNinos==1) {
				numSeleccionado=1;
			}
			else if(numNinos==2) {
				numSeleccionado=1;
			}
			else if(numNinos==3) {
				numSeleccionado=3;
			}
			else if(numNinos==4) {
				numSeleccionado=3;
			}
			else if(numNinos==5) {
				numSeleccionado=5;
			}
			else if(numNinos==6) {
				numSeleccionado=1;
			}
		}
		else if(encuesta==8) {
			if(numNinos==1) {
				numSeleccionado=1;
			}
			else if(numNinos==2) {
				numSeleccionado=2;
			}
			else if(numNinos==3) {
				numSeleccionado=1;
			}
			else if(numNinos==4) {
				numSeleccionado=4;
			}
			else if(numNinos==5) {
				numSeleccionado=1;
			}
			else if(numNinos==6) {
				numSeleccionado=2;
			}
		}
		else if(encuesta==9) {
			if(numNinos==1) {
				numSeleccionado=1;
			}
			else if(numNinos==2) {
				numSeleccionado=1;
			}
			else if(numNinos==3) {
				numSeleccionado=2;
			}
			else if(numNinos==4) {
				numSeleccionado=1;
			}
			else if(numNinos==5) {
				numSeleccionado=2;
			}
			else if(numNinos==6) {
				numSeleccionado=3;
			}
		}
        return numSeleccionado;
    }

}
