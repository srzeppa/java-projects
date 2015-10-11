import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.io.Serializable;
import javax.swing.JOptionPane;

class Model{
        char tab[][] = new char[10][10];
}
 
/*USTAWIENIE CALEJ PLANSZY BY WYGLADALA LADNIE I BYLA GRYWALNA*/
public class Plansza extends JFrame{
        Model model = new Model();
        public int tabWielkoscStawu[][] = new int [10][10];
        public int dlugoscGrobli=0;
        
        public void groble(){
        	int i,j;
        	for (i=0;i<10;i++){
        		for (j=0;j<10;j++){
        			model.tab[i][j]='s';
        		}
			}
        }

        JPanel plansza = new JPanel();
        JPanel menu = new JPanel();
       
        JButton help = new JButton("Pomoc");
        JButton prev = new JButton("Cofnij");
        JButton next = new JButton("Dalej");
        JButton neww = new JButton("Nowa Gra");
        JButton tab[][] = new JButton[10][10];//tablica planszy
        JButton load = new JButton("Wczytaj");
        JButton save = new JButton("Zapisz");
 
        static JTextField blad = new JTextField(20);	//Bledy stawow
        static JTextField blad2 = new JTextField(20);	//Bledy grobli
        static JTextField blad3 = new JTextField(20);	//Bledy ogolne gry
        JTextField name = new JTextField(10);
        
        public Historia historia = new Historia();

        public Plansza(){
                setTitle("Slawomir Rzeppa 224688");
                Container ct = getContentPane();
                ct.setLayout(new GridLayout(1,2));//podzial jeden wiersz, dwie kolumny
                ct.add(plansza);
                ct.add(menu);
                
   	/*WYPELNIENIE CYFRAMI WIELKOSCI STAWOW*/
            	
            	tabWielkoscStawu[0][7]=6;
                tabWielkoscStawu[1][4]=4;
                tabWielkoscStawu[2][6]=2;
                tabWielkoscStawu[2][9]=7;
                tabWielkoscStawu[3][1]=6;
                tabWielkoscStawu[5][7]=6;
                tabWielkoscStawu[7][4]=2;
                tabWielkoscStawu[8][9]=8;
                tabWielkoscStawu[9][0]=3;
                tabWielkoscStawu[9][5]=5;
               
                int i,j;
                plansza.setLayout(new GridLayout(10,10)); //podzial 8 wierszy, 8 kolumn
                for(i=0;i<10;i++){
                    for(j=0;j<10;j++){
                            tab[i][j] = new JButton("");
                            plansza.add(tab[i][j]);
                            (tab[i][j]).setBackground(Color.white);
                            model.tab[i][j]='s';
                            if(tabWielkoscStawu[i][j]==0){
                            (tab[i][j]).addActionListener(new USTAW(i,j));
                            }else{
                            	(tab[i][j]).addActionListener(new STAW(i,j));
                            }
                            }
                }
                
                /*CYFRY WIELKOSCI STAWOW NAPISANE NA PLANSZY*/
                tab[0][7].setText("6");
                tab[1][4].setText("4");
                tab[2][6].setText("2");
                tab[2][9].setText("7");
                tab[3][1].setText("6");
                tab[5][7].setText("6");
                tab[7][4].setText("2");
                tab[8][9].setText("8");
                tab[9][0].setText("3");
                tab[9][5].setText("5");
                
                
                /*WYPELNIENIE MENU*/
                menu.setLayout(new GridLayout(8,2));
                menu.add(new Label("Stawy:")); menu.add(blad);
                menu.add(new Label("Groble:")); menu.add(blad2);
                menu.add(prev); menu.add(next);
                menu.add(new Label("Bledy: ")); menu.add(blad3);
                menu.add(new Label()); menu.add(new Label());
                menu.add(new Label("Nazwa Gracza: ")); menu.add(name);
                menu.add(load); menu.add(save);
                menu.add(neww); menu.add(help); 
                
                /*INTERAKCJA PRZYCISKOW*/
                prev.addActionListener(new COFAJ());
                next.addActionListener(new NASTEPNY());
                save.addActionListener(new ZAPISYWANIE());
                load.addActionListener(new WCZYTYWANIE());
                neww.addActionListener(new NOWAGRA());
                help.addActionListener(new POMOC());
               
                /*POZOSTALE USTAWIENIA*/
                setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
        
        /*KLASA USTAW USTAWIA PRZY KLIKNIECIU WARTOSC TABLICY W TYM MIEJSCU NA 'G', ZMIENIA KOLOR TLA TEGO MIEJSCA
         * NA CZARNY, KORZYSTA TEZ ZE SPRAWDZANIA SZEROKOSCI GROBLI
         */
        public class USTAW implements ActionListener{
            int i,j,x,y;
            int mozliwaDlugosc;
            USTAW(int i,int j){this.i=i;this.j=j;}
            public void actionPerformed(ActionEvent e){
                if((tab[i][j]).getBackground()==Color.white){       
                        (tab[i][j]).setBackground(Color.black);
                        model.tab[i][j]='g';
                        if(historia.sprawdzGroble(model, i, j)==1){
                        	blad2.setText("grobla zbyt szeroka");
                        }
                        mozliwaDlugosc=historia.wielkoscGrobla(tabWielkoscStawu);
                        dlugoscGrobli=dlugoscGrobli+1;
                        if(dlugoscGrobli==mozliwaDlugosc){
                        	blad.setText("Możesz sprawdzić stawy!");
                        }else if(dlugoscGrobli>mozliwaDlugosc){
                        	blad2.setText("Przekroczono dlugosc Grobli!");
                        }
                        tab[i][j].setEnabled(false);
                        historia.dodajRuch(i,j);
                }
            }
        }
        
        public class STAW implements ActionListener{
        	int i,j;
        	int a,b;
        	int wynik,ile,wielkosc;
        	STAW(int i, int j){this.i=i;this.j=j;}
        	public void actionPerformed(ActionEvent e){
        	ile=historia.sprawdzStaw(model,i,j);
        	wielkosc=tabWielkoscStawu[i][j];
        	for(a=0;a<10;a++){
        		for(b=0;b<10;b++){
        			if(model.tab[a][b]=='p'){
        			model.tab[a][b]='s';
        			}
        		}
        	}
        	if(ile==wielkosc){
        		tab[i][j].setBackground(Color.green);
        		blad.setText("OK!");
        	}else{
        		tab[i][j].setBackground(Color.red);
        		blad.setText("ZLE!");
        	}
        	}
        }
        /*PRZYCISK COFAJ COFA OSTATNI WYKONANY PRZEZ GRACZA RUCH*/
        public class COFAJ implements ActionListener{
        	public void actionPerformed(ActionEvent e){
        		if(historia.element==0){
        			prev.setEnabled(false);
        			prev.setEnabled(true);
        			blad3.setText("Wykonano maksymalna liczbe posuniec do tylu!");
        		}
        		else{
        			int mozliwaDlugosc=historia.wielkoscGrobla(tabWielkoscStawu);
        			dlugoscGrobli--;
        			 if(dlugoscGrobli==mozliwaDlugosc){
                     	blad.setText("Możesz sprawdzić stawy!");
                     }else if(dlugoscGrobli>mozliwaDlugosc){
                     	blad2.setText("Przekroczono dlugosc Grobli!");
                     }
        			 blad.setText(null);
        			 blad2.setText(null);
	        		 blad3.setText(null);
	        		 RUCH cofanie = historia.odejmijRuch();
	            	 (tab[cofanie.x][cofanie.y]).setBackground(Color.white);
	            	 tab[cofanie.x][cofanie.y].setEnabled(true);
	            	 model.tab[cofanie.x][cofanie.y]='s';
        		}
        	}
        }
        
        /*PRZYCISK NASTEPNY WCZYTUJE OSTATNI WYKONANY PRZEZ GRACZA RUCH*/
        public class NASTEPNY implements ActionListener{
        	public void actionPerformed(ActionEvent e){
        		if(historia.element==historia.ruchy.size()){
        			next.setEnabled(false);
        			next.setEnabled(true);
        			blad3.setText("Wykonano maksymalna liczbe posuniec do przodu!");
        		}
        		else{
        			int mozliwaDlugosc=historia.wielkoscGrobla(tabWielkoscStawu);
        			dlugoscGrobli--;
        			 if(dlugoscGrobli==mozliwaDlugosc){
        				 blad.setText("Możesz sprawdzić stawy!");
                     }else if(dlugoscGrobli>mozliwaDlugosc){
                     	blad2.setText("Przekroczono dlugosc Grobli!");
                     }
        			 blad.setText(null);
        			 blad2.setText(null);
	        		 blad3.setText(null);
        			RUCH nastepny = historia.nastepnyRuch();
            		(tab[nastepny.x][nastepny.y]).setBackground(Color.black);
            		tab[nastepny.x][nastepny.y].setEnabled(false);
            		model.tab[nastepny.x][nastepny.y]='g';
        		}
        	}
        }
        
        /*PRZYCISK ZAPISYWANIE ZAPISUJE DANE I AKTUALNY STAN GRY DANEGO GRACZA DO PLIKU*/
        public class ZAPISYWANIE implements ActionListener{
        	public void actionPerformed(ActionEvent e){
        		 String nazwa = name.getText();
                 File zapis = new File(nazwa+".ser");
                 String warn;
                 try {
                         ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nazwa+".ser"));
                         out.writeObject(historia);
                         out.close();
                         blad3.setText("ZAPISANO STAN GRY");
                 } catch (IOException e1) {
                         blad3.setText("BLAD ZAPISU PLIKU");
                 }
        	}
        }
        
        /*PRZYCISK WCZYTAJ WCZYTUJE DANE DANEGO GRACZA Z PLIKU*/
        public class WCZYTYWANIE implements ActionListener{
        	public void actionPerformed(ActionEvent e){
        		String plik = name.getText();
        		historia = historia.wczytajRuchy(plik);
        		for(int i=0;i<historia.ruchy.size();i++){
                    RUCH bierzacy = historia.ruchy.get(i);
                    System.out.println(bierzacy.x+" "+bierzacy.y);
        		}
        		for(int i=0;i<historia.ruchy.size();i++){
                    RUCH bierzacy = historia.ruchy.get(i);
                    if((tab[bierzacy.x][bierzacy.y]).getBackground()==Color.black){
                    	(tab[bierzacy.x][bierzacy.y]).setBackground(Color.black);
                    	model.tab[bierzacy.x][bierzacy.y]='s';
                    }
                    if((tab[bierzacy.x][bierzacy.y]).getBackground()==Color.white){
                    	(tab[bierzacy.x][bierzacy.y]).setBackground(Color.black);
                    	model.tab[bierzacy.x][bierzacy.y]='g';
                    }
        		}
        	}
        }
        
        /*PRZYCISK "NOWA GRA" CZYSCI WSZYSTKIE RUCHY GRACZA, ZERUJE TABLICE, WPROWADZA WSZYSTKIE DANE OD POCZATKU*/
        public class NOWAGRA implements ActionListener{
        	public void actionPerformed(ActionEvent e){
        		int i,j;
        		for(i=0;i<10;i++){
        			for(j=0;j<10;j++){
        				tab[i][j].setBackground(Color.white);
        				model.tab[i][j]='s';
        				tab[i][j].setEnabled(true);
        			}
        		}
        		for(i=historia.ruchy.size()-1;i>=0;i--){
        			historia.ruchy.remove(i);
        		}
        		historia.element=0;
        	}
        }
        
        /*OKNO POMOC PO KLIKNIECIU W PRZYCISK WYSWIETLA NAM REGULY GRY*/
        public class POMOC extends JFrame implements ActionListener{
	        public void actionPerformed(ActionEvent e) {
	        	POMOC dialog = new POMOC();
	        	setLayout(new FlowLayout(FlowLayout.CENTER));
	        	dialog.setSize(1000, 1000);
	        	dialog.setLocation(200,200);
	        	JOptionPane.showMessageDialog(dialog, "Mamy siatke z kwadratowymi polami (komorkami).\n"+
	        									"Niektore z tych komorek zawieraja cyfry. Naszym celem jest ustalenie, które z komorek siatki\n"+
	        									"są biale, a ktore czarne (Uzywajac nazewnictwa stawy na groblach: zamiast białego i czarnego\n"+
	        									"pola odpowiednio mamy stawy i groble). Czarne pola uformują groble. Czarne pola nie zawieraja cyfr,\n"+
	        									"a takze ga tworzyc kwadratow 2x2 lub wiekszych prostokatow. Biale pola uformuja stawy.\n"+
	        									"Kazda cyfra n musi być częścia n-elementowego pola zlozonego z bialych komorek. Kazda z bialych\n"+
	        									"komorek nalezy tylko do jednej wyspy; kazda wyspa moze posiadac tylko jedna komorke z cyfra.\n"+
	        									"Rozwiązujac to zadanie najczesciej stosuje sie oznaczenia: zaciemnienie komorki, ktora droga\n"+
	        									"dedukcji bedzie czarna i oznaczenie kropka komorek, ktore nie zawieraja w sobie cyfry i droga\n"+
	        									"dedukcji powinny pozostac biale.\n"+
	        									"1. Aby zaczernic komórke nalezy kliknac w nia lewym przyciskiem myszy.\n"+
	        									"2. Przyciski cofnij/dalej pozwalaja nam sie poruszac po ostatnio wykonanych ruchach.\n"+
	        									"3. Gra pozwala zapisac/odczytac stan rozgrywki.");
	        }
        }
        
        public static void main(String[] args) {
            JFrame f = new Plansza();
            f.setSize(900,500);
            f.setLocation(100,100);
            f.setVisible(true);
        }
}