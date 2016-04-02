public class Data {	
	
	static char separador = '/';	
	static int  termos[] = {0, 1, 2};
	static int  formato   = 0;
	int  componentes[] = {0, 0, 0}; 

	public Data(Data d) {
		componentes[0] = d.componentes[0];
		componentes[1] = d.componentes[1];
		componentes[2] = d.componentes[2];		
	}		
	public Data(String data){
		
		if (!stringData(data)){
			this.componentes[0] = 0;
			this.componentes[1] = 0;
			this.componentes[2] = 0;
		}
		
	}
	public Data(int dia, int mes, int ano){
		this.componentes[0] = dia;
		this.componentes[1] = mes;
		this.componentes[2] = ano;
		
		if (!consistenciadeData() ){
			this.componentes[0] = 0;
			this.componentes[1] = 0;
			this.componentes[2] = 0;
		}
		
	}

	public boolean stringData(String strdata){
		
		String[] vetdatas = strdata.split("\\/", -1);
		
		int i = 0;
        for (String string : vetdatas) {
        	this.componentes[i++] = Integer.parseInt(string);
        }
        
		return consistenciadeData();		
	}
	
	public String dataString(){		
		return  "" 	+ this.componentes[ termos[0] ] + separador
				    + this.componentes[ termos[1] ] + separador
				    + this.componentes[ termos[2] ];
	}	

	
	static boolean mudaFormato(int f){		
		switch (f) {
        case 0:
            separador = '/';
            termos[0] = 0;
            termos[1] = 1;
            termos[2] = 2;
            formato = f;
            break;
        case 1:
            separador = '/';
            termos[0] = 1;
            termos[1] = 0;
            termos[2] = 2;
            formato = f;
            break;
        case 2:
            separador = '-';
            termos[0] = 0;
            termos[1] = 1;
            termos[2] = 2;
            formato = f;
            break;
        case 3:
            separador = '.';
            termos[0] = 0;
            termos[1] = 1;
            termos[2] = 2;
            formato = f;
            break;
        case 4:
            separador = '.';
            termos[0] = 2;
            termos[1] = 1;
            termos[2] = 1;
            formato = f;
            break;
		}
    
		return (formato == f);
	}

	
	@SuppressWarnings("unused")
	private long dataDias() {		
        long qtdeDias = 0;
        int dia = 1, mes = 1, ano = 1900;
        
        while (dia != this.componentes[0] && mes != this.componentes[1] && ano != this.componentes[2]) 
        {
            int diasNoMes = diasMes(mes);
            if (bissexto(ano)) diasNoMes++;
            
            if (dia < diasNoMes) {
                dia++;
                qtdeDias++;
            } else if (mes < 12) {
                dia = 1;
                mes++;
                qtdeDias++;
            } else {
                dia = 1;
                mes = 1;
                ano++;
                qtdeDias++;
            }
        }
        
        return qtdeDias;
	}
		
	
	@SuppressWarnings("unused")
	private void diasData(long d){	
		int dia = 1;
		int mes = 1;
		int ano = 1900;
		
		for (long dias = d; dias > 1; dias--) {
	        int diasNoMes = diasMes(mes);
	        if (bissexto(ano)) diasNoMes++;
	        
	        if (dia < diasNoMes) dia++;
	        else if (mes < 12) {
	            dia = 1;
	            mes++;
	        } else {
	            dia = 1;
	            mes = 1;
	            ano++;
	        }
	    }
		
	}
	
	public boolean consistenciadeData(){
		
		int dia = this.componentes[0];
		int mes = this.componentes[1];
		int ano = this.componentes[2];
		
		int diasM = diasMes(mes);
		if (bissexto(ano)) diasM++;
				
		return ( diasM!=0 && (dia >0 && dia <= diasM) );
	}
		
	static boolean bissexto(int ano){ 
		
		return ((ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0));
		
	}	

	static int diasMes(int mes){
		
	    switch (mes) {        
        case 2:        	
        	return 28;        	
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            return 31;
        case 4:
        case 6:
        case 9:
        case 11:
            return 30;
        }		
	
	    return 0;
	}	
	
	
	public Data sub(int dias){
		int dia = this.componentes[0];
		int mes = this.componentes[1];
		int ano = this.componentes[2];
        
        for (int idia = dias; idia > 1; idia--) 
        {            
        	if (dia > 1) {
        		dia--;
        	}
            else if (mes > 1) {
                mes--;
                dia = diasMes(mes);
                if (bissexto(ano)) dia++;
            } else {
                ano--;
                mes = 12;
                dia = 31;
            }        	
        }
        
        return new Data(dia, mes, ano);
	}
	
	
	public long sub(Data d){
        long diasSub = 0;
        int dia = d.componentes[0];
        int mes = d.componentes[1];
        int ano = d.componentes[2];
        
        if (ano <  this.componentes[2] || (ano == this.componentes[2] && mes < this.componentes[1]) || 
           (ano == this.componentes[2] &&  mes == this.componentes[1] && dia < this.componentes[1])) {
            return diasSub;
        }
        
        while (dia != this.componentes[0] && mes != this.componentes[1] && ano !=  this.componentes[2]) 
        {
            if (dia > 1) {
                dia--;
                diasSub++;
            } else if (mes > 1) {
                mes--;
                dia = diasMes(mes);
                if (bissexto(ano)) dia++;
                diasSub++;
            } else {
                ano--;
                mes = 12;
                dia = 31;
                diasSub++;
            }            
        }
        
        return diasSub;
	}
		
	public Data soma(int dias){
		int dia = this.componentes[0];
		int mes = this.componentes[1];
		int ano = this.componentes[2];
		
        for (int idia = dias; idia > 1; idia--) {
            int diasNoMes = diasMes(mes);
            if (bissexto(ano)) diasNoMes++;
            
            if (dia < diasNoMes) {
            	dia++;
            }
            else if (mes < 12) {
                dia = 1;
                mes++;
            } 
            else {
                dia = 1;
                mes = 1;
                ano++;
            }
        }
        return new Data(dia, mes, ano);
	}
	
}
