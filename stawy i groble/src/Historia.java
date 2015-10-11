
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.io.Serializable;

class RUCH implements Serializable{
	public int x,y;
	RUCH(int a, int b){
		x=a;
		y=b;
	}
}

public class Historia implements Serializable {
	
	public int element;
	ArrayList<RUCH> ruchy = new ArrayList<RUCH>();
	
	/*DODAWANIE RUCHU DO ARRAYLIST*/
	public void dodajRuch(int a, int b){
		if(element<ruchy.size()){
			while(element!=ruchy.size()){
				ruchy.remove(element);
			}
		}
		ruchy.add(new RUCH(a,b));
		element++;
	}
	
	/*COFANIE RUCHU W PRZYCISKU*/
	public RUCH odejmijRuch(){
		element--;
		RUCH cofanie = ruchy.get(element);
		return cofanie;
	}
	
	/*DODAWANIE RUCHU W PRZYCISKU*/
	public RUCH nastepnyRuch(){
		RUCH nastepny = ruchy.get(element);
		element++;
		return nastepny;
	}

	/*WCZYTYWANIE RUCHÓW Z PLIKU DO ARRAYLISTY*/
	public Historia wczytajRuchy(String nazwa){
		Historia wczytany = new Historia();
		try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(nazwa+".ser"));
				wczytany = (Historia)in.readObject();
				in.close();
				for(int i=0;i<wczytany.ruchy.size();i++){
					RUCH bierzacy = wczytany.ruchy.get(i);
				}
			} catch (ClassNotFoundException e) {
				System.out.println("BLAD ODCZYTU!!");
		} catch (IOException e) {
			System.out.println("NIE UDALO SIE OTWORZYC PLIKU!!");
		}
		return wczytany;
	}
	
	/*SPRAWDZANIE CZY GROBLA NIE JEST ZBYT SZEROKA ("GROBLA NIE MOZE BYC SZERSZA NIZ 2*/
	public int sprawdzGroble(Model obiekt, int x,int y){
		int error=0;
        if(x==0 && y==0){
                if(obiekt.tab[x][y]=='g'&&obiekt.tab[x+1][y]=='g'&&obiekt.tab[x+1][y+1]=='g'&&obiekt.tab[x][y+1]=='g')error=1;
        }
        else if(x==0 && y==9){
                if(obiekt.tab[x][y]=='g'&&obiekt.tab[x+1][y]=='g'&&obiekt.tab[x+1][y+1]=='g'&&obiekt.tab[x][y+1]=='g')error=1;
        }
        else if(x==9 && y==0){
                if(obiekt.tab[x][y]=='g'&&obiekt.tab[x][y+1]=='g'&&obiekt.tab[x-1][y+1]=='g'&&obiekt.tab[x-1][y]=='g')error=1;
        }
        else if(x==9 && y==9){
                if(obiekt.tab[x][y]=='g'&&obiekt.tab[x-1][y]=='g'&&obiekt.tab[x-1][y-1]=='g'&&obiekt.tab[x][y-1]=='g')error=1;
        }else if(y==0){
                if(obiekt.tab[x][y]=='g'&&obiekt.tab[x+1][y]=='g'&&obiekt.tab[x+1][y+1]=='g'&&obiekt.tab[x][y+1]=='g')error=1;
                if(obiekt.tab[x][y]=='g'&&obiekt.tab[x][y+1]=='g'&&obiekt.tab[x-1][y+1]=='g'&&obiekt.tab[x-1][y]=='g')error=1;
        }else if(y==9){
                if(obiekt.tab[x][y]=='g'&&obiekt.tab[x][y-1]=='g'&&obiekt.tab[x-1][y-1]=='g'&&obiekt.tab[x-1][y]=='g')error=1;
                if(obiekt.tab[x][y]=='g'&&obiekt.tab[x][y-1]=='g'&&obiekt.tab[x+1][y-1]=='g'&&obiekt.tab[x+1][y]=='g')error=1;
        }else if(x==0){
                if(obiekt.tab[x][y]=='g'&&obiekt.tab[x+1][y]=='g'&&obiekt.tab[x+1][y-1]=='g'&&obiekt.tab[x][y-1]=='g')error=1;
                if(obiekt.tab[x][y]=='g'&&obiekt.tab[x][y+1]=='g'&&obiekt.tab[x+1][y+1]=='g'&&obiekt.tab[x][y-1]=='g')error=1;
        }else if(x==9){
                if(obiekt.tab[x][y]=='g'&&obiekt.tab[x][y+1]=='g'&&obiekt.tab[x-1][y+1]=='g'&&obiekt.tab[x-1][y-1]=='g')error=1;
                if(obiekt.tab[x][y]=='g'&&obiekt.tab[x][y-1]=='g'&&obiekt.tab[x-1][y-1]=='g'&&obiekt.tab[x-1][y]=='g')error=1;
        }else{
        if(obiekt.tab[x][y]=='g'&&obiekt.tab[x+1][y]=='g'&&obiekt.tab[x+1][y+1]=='g'&&obiekt.tab[x][y+1]=='g')error=1;
        if(obiekt.tab[x][y]=='g'&&obiekt.tab[x+1][y]=='g'&&obiekt.tab[x+1][y-1]=='g'&&obiekt.tab[x][y-1]=='g')error=1;
        if(obiekt.tab[x][y]=='g'&&obiekt.tab[x][y-1]=='g'&&obiekt.tab[x-1][y-1]=='g'&&obiekt.tab[x-1][y]=='g')error=1;
        if(obiekt.tab[x][y]=='g'&&obiekt.tab[x][y+1]=='g'&&obiekt.tab[x-1][y+1]=='g'&&obiekt.tab[x-1][y]=='g')error=1;
        }
        return error;
	}

	public int sprawdzStaw(Model obiekt,int x, int y){
		int sukces=0;
		int ile=0;
		int dalej;
		  if(x==0 && y==0){
			  if(obiekt.tab[0][0]=='s'){
				  ile++;
		          obiekt.tab[0][0]='p';
			  }
	                if(obiekt.tab[x][y+1]=='s'){
	                	obiekt.tab[x][y+1]='p';
	                	ile++;
	                	dalej=sprawdzStaw(obiekt,x,y+1);
	                	ile=ile+dalej;
	                }
	                if(obiekt.tab[x+1][y]=='s'){
	                	ile++;
	                	obiekt.tab[x+1][y]='p';
	                	dalej=sprawdzStaw(obiekt,x+1,y);
	                	ile=ile+dalej;
	                }
	        }
	        else if(x==0 && y==9){
	        	if(obiekt.tab[0][9]=='s'){
					  ile++;
			          obiekt.tab[0][9]='p';
				  }
	                if(obiekt.tab[x+1][y]=='s'){
	                	obiekt.tab[x+1][y]='p';
	                	ile++;
	                	dalej=sprawdzStaw(obiekt,x+1,y);
	                	ile=ile+dalej;
	                }
	                if(obiekt.tab[x][y-1]=='s'){
	                	obiekt.tab[x][y-1]='p';
	                	ile++;
	                	dalej=sprawdzStaw(obiekt,x,y-1);
	                	ile=ile+dalej;
	                }
	        }
	        else if(x==9 && y==0){
	        	if(obiekt.tab[9][0]=='s'){
					  ile++;
			          obiekt.tab[9][0]='p';
				  }
	                if(obiekt.tab[x][y+1]=='s'){
	                	obiekt.tab[x][y+1]='p';
	                	ile++;
	                	dalej=sprawdzStaw(obiekt,x,y+1);
	                	ile=ile+dalej;
	                }
	                if(obiekt.tab[x-1][y]=='s'){
	                	obiekt.tab[x-1][y]='p';
	                	ile++;
	                	dalej=sprawdzStaw(obiekt,x-1,y);
	                	ile=ile+dalej;
	                }
	        }
	        else if(x==9 && y==9){
	        	if(obiekt.tab[9][9]=='s'){
					  ile++;
			          obiekt.tab[9][9]='p';
				  }
	                if(obiekt.tab[x-1][y]=='s'){
	                	obiekt.tab[x-1][y]='p';
	                	ile++;
	                	dalej=sprawdzStaw(obiekt,x-1,y);
	                	ile=ile+dalej;
	                }
	                if(obiekt.tab[x][y-1]=='s'){
	                	obiekt.tab[x][y-1]='p';
	                	ile++;
	                	dalej=sprawdzStaw(obiekt,x,y-1);
	                	ile=ile+dalej;
	                }
	                } else if(y==0){
	                	if(obiekt.tab[x][0]=='s'){
	                		obiekt.tab[x][0]='p';
	                    	ile++;
	                	}
	                        if(obiekt.tab[x+1][y]=='s'){
	                        	obiekt.tab[x+1][y]='p';
	                        	ile++;
	                        	dalej=sprawdzStaw(obiekt,x+1,y);
	    	                	ile=ile+dalej;
	                        }
	                        if(obiekt.tab[x][y+1]=='s'){
	                        	obiekt.tab[x][y+1]='p';
	                        	ile++;
	                        	dalej=sprawdzStaw(obiekt,x,y+1);
	    	                	ile=ile+dalej;
	                        }
	                        if(obiekt.tab[x-1][y]=='s'){
	                        	obiekt.tab[x-1][y]='p';
	                        	ile++;
	                        	dalej=sprawdzStaw(obiekt,x-1,y);
	    	                	ile=ile+dalej;
	                        }
	                }else if(y==9){
	                	if(obiekt.tab[x][9]=='s'){
	                		obiekt.tab[x][9]='p';
	                    	ile++;
	                	}
	                        if(obiekt.tab[x][y-1]=='s'){
	                        	obiekt.tab[x][y-1]='p';
	                        	ile++;
	                        	dalej=sprawdzStaw(obiekt,x,y-1);
	    	                	ile=ile+dalej;
	                        }
	                        if(obiekt.tab[x-1][y]=='s'){
	                        	obiekt.tab[x-1][y]='p';
	                        	ile++;
	                        	dalej=sprawdzStaw(obiekt,x-1,y);
	    	                	ile=ile+dalej;
	                        }
	                        if(obiekt.tab[x+1][y]=='s'){
	                        	obiekt.tab[x+1][y]='p';
	                        	ile++;
	                        	dalej=sprawdzStaw(obiekt,x+1,y);
	    	                	ile=ile+dalej;
	                        }
	                }else if(x==0){
	                	if(obiekt.tab[0][y]=='s'){
	                		obiekt.tab[0][y]='p';
	                		ile++;
	                	}
	                        if(obiekt.tab[x][y+1]=='s'){
	                        	obiekt.tab[x][y+1]='p';
	                        	ile++;
	                        	dalej=sprawdzStaw(obiekt,x,y+1);
	    	                	ile=ile+dalej;
	                        }
	                        if(obiekt.tab[x][y-1]=='s'){
	                        	obiekt.tab[x][y-1]='p';
	                        	ile++;
	                        	dalej=sprawdzStaw(obiekt,x,y-1);
	    	                	ile=ile+dalej;
	                        }
	                        if(obiekt.tab[x+1][y]=='s'){
	                        	obiekt.tab[x+1][y]='p';
	                        	ile++;
	                        	dalej=sprawdzStaw(obiekt,x+1,y);
	    	                	ile=ile+dalej;
	                        }
	                }else if(x==9){
	                	if(obiekt.tab[9][y]=='s'){
	                		obiekt.tab[9][y]='p';
	                		ile++;
	                	}
	                        if(obiekt.tab[x][y+1]=='s'){
	                        	obiekt.tab[x][y+1]='p';
	                        	ile++;
	                        	dalej=sprawdzStaw(obiekt,x,y+1);
	    	                	ile=ile+dalej;
	                        }
	                        if(obiekt.tab[x-1][y]=='s'){
	                        	obiekt.tab[x-1][y]='p';
	                        	ile++;
	                        	dalej=sprawdzStaw(obiekt,x-1,y);
	    	                	ile=ile+dalej;
	                        }
	                        if(obiekt.tab[x][y-1]=='s'){
	                        	obiekt.tab[x][y-1]='p';
	                        	ile++;
	                        	dalej=sprawdzStaw(obiekt,x,y-1);
	    	                	ile=ile+dalej;
	                        }
	                }else{
	                if(obiekt.tab[x][y]=='s'){
	                	obiekt.tab[x][y]='p';
	                	ile++;
	                	dalej=sprawdzStaw(obiekt,x,y);
	                	ile=ile+dalej;
	                }
	                if(obiekt.tab[x+1][y]=='s'){
	                	obiekt.tab[x+1][y]='p';
	                	ile++;
	                	dalej=sprawdzStaw(obiekt,x+1,y);
	                	ile=ile+dalej;
	                }
	                if(obiekt.tab[x-1][y]=='s'){
	                	obiekt.tab[x-1][y]='p';
	                	ile++;
	                	dalej=sprawdzStaw(obiekt,x-1,y);
	                	ile=ile+dalej;
	                } 
	                if(obiekt.tab[x][y+1]=='s'){
	                	obiekt.tab[x][y+1]='p';
	                	ile++;
	                	dalej=sprawdzStaw(obiekt,x,y+1);
	                	ile=ile+dalej;
	                }
	                if(obiekt.tab[x][y-1]=='s'){
	                	obiekt.tab[x][y-1]='p';
	                	ile++;
	                	dalej=sprawdzStaw(obiekt,x,y-1);
	                	ile=ile+dalej;
	                }
	                }
		 // System.out.println("TYLE "+ile);
		
		return ile;
	}
	
	/*SPRAWDZANIE WIELKOSCI GROBLI W CALEJ GRZE*/
    public int wielkoscGrobla(int tab[][]){
    	int i,j;
    	int dlugoscStawu=0;
    	int dlugoscGrobli=0;
    	for(i=0;i<10;i++){
    		for(j=0;j<10;j++){
    			if(tab[i][j]!=0){
    				dlugoscStawu=tab[i][j]+dlugoscStawu;
    			}
    		}
    	}
    	dlugoscGrobli=100-dlugoscStawu;
    	return dlugoscGrobli;
    }

}