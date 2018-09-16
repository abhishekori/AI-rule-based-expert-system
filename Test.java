import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import jdk.internal.dynalink.beans.StaticClass;
import jess.JessException;
import jess.Rete;


public class Test {

	static Rete engine = new Rete();

	public static void main(String args[]) {

		// System.out.println(Test.class.getClass().getCanonicalName());
		/// home/abhishek/softwares/Jess71p2/bin/jess

		// System.out.println(args[0]);
		
		float ttt, tft, ttf, tff, tt, ft, ff, tf, t, f;

		try {
			// engine.executeCommand("(bind ?cm (new Test))");
			// engine.executeCommand("(call ?cm callme \"hi my friend\")");
			engine.executeCommand("(clear)");
			engine.executeCommand("(open \"rain1.txt\" file \"w\")");

			engine.executeCommand("(deftemplate house (slot rain) (slot sprinkler) (slot window))");

			engine.executeCommand("(printout file \"\" crlf)");

			engine.executeCommand(
					"(defrule is-it-raining ?res <- (house (rain ?r&:(eq ?r 1))) => (bind ?cm (new Test))(call ?cm callPy 90) )");
			// (printout t \"close the window\" crlf)

			// engine.executeCommand("(defrule its-not-raining ?res <- (house
			// (rain ?r&:(< ?r 0.5))) => (printout t \" \n keep the window
			// open\" crlf))");
			// float rain;
			engine.executeCommand("(reset)");
			// engine.executeCommand("(watch all)");
			System.out.println(args[0]);
			String cmd = "(assert (house (rain " + args[0] + ")))";
			engine.executeCommand(cmd);
			engine.executeCommand("(run)");
			// arg

		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (Integer.parseInt(args[0]) == 0) {
			// System.out.println("its unrelable");

			
			// ttt
			Test test = new Test();
			Search_table st = test.new Search_table();
			// System.out.println(st.search("./grasswet_table.txt","ftt"));
			String resString,resStringArr[];
			String season=null,rainTableName=null;
			if((resString=findSeason())!=null){
				resStringArr=resString.split(",");
				season=resStringArr[0];
				rainTableName=resStringArr[1]+".txt";
				System.out.println(rainTableName);
			}
			
			//String path="";
			
			int threshHoldSummer=14,threshHoldWinter=48,threshHoldRainy=95,pySignal=180,finalRes;

			ttt = st.search("../tables/"+season+"/grasswet_table.txt", "ttt");
			tft = st.search("../tables/"+season+"/grasswet_table.txt", "tft");
			ttf = st.search("../tables/"+season+"/grasswet_table.txt", "ttf");
			tff = st.search("../tables/"+season+"/grasswet_table.txt", "tff");
			tt = st.search("../tables/"+season+"/sprinkler_table.txt", "tt");
			ft = st.search("../tables/"+season+"/sprinkler_table.txt", "ft");
			tf = st.search("../tables/"+season+"/sprinkler_table.txt", "tf");
			ff = st.search("../tables/"+season+"/sprinkler_table.txt", "ff");
			t = st.search("../tables/"+season+"/"+rainTableName, "t");
			f = st.search("../tables/"+season+"/"+rainTableName, "f");

			// System.out.println(ttt+" "+tt+" "+t);
			// System.out.println(tft+" "+ft+" "+t);
			// System.out.println(tff+" "+ff+" "+f);

			ttt = ttt * tt * t;
			tft = tft * ft * t;
			ttf = ttf * tf * f;
			tff = tff * ff * f;
			// System.out.println(ttt+" "+tft+" "+ttf+" "+tff);

			double probAns = (ttt + tft) / (ttt + ttf + tft + tff);
			System.out.println(season);
			finalRes=(int) Math.round(probAns*100);
			 System.out.println(finalRes +" %");
			// System.out.println(0);
			 switch (season) {
			case "summer":
				if(finalRes>threshHoldSummer)
					pySignal=80;
				break;
				
			case "rainy":
				if(finalRes>threshHoldRainy)
					pySignal=80;
				break;
				
			case "winter":
				if(finalRes>threshHoldWinter)
					pySignal=80;
				break;

			default:
				break;
			}
			 System.out.println("py signal "+pySignal);
			callPy(pySignal);

		}

		//
		// Timer timer = new Timer();
		// timer.schedule(new Test(),0,1000);

	}
	
	static String findSeason(){
		DateFormat dateFormat = new SimpleDateFormat("MM");
		Date date = new Date();
		int idate = Integer.parseInt(dateFormat.format(date).toString());
		System.out.println("month is "+idate); //2016/11/16 12:08:43
		 
		if(idate>=3 && idate<=6){
			switch (idate) {
			case 3:
				return "summer,r1";
			case 4:
				return "summer,r1";
			case 5:
				return "summer,r2";
			case 6:
				return "summer,r3";
			}
			
		}else if(idate>=7 && idate<=10){
			switch (idate) {
			case 7:
				return "rainy,r1";
			case 8:
				return "rainy,r2";
			case 9:
				return "rainy,r2";
			case 10:
				return "rainy,r3";
			}
		}else{
			switch (idate) {
			case 11:
				return "winter,r1";
			case 12:
				return "winter,r2";
			case 1:
				return "winter,r2";
			case 2:
				return "winter,r3";
			}
		}
		return null;
		//return idate;
	}

	
	public class Search_table {
		String search_key, file_name, curr_string;
		String[] split_words;

		float result;

		public float search(String fileName, String searchKey) {
			this.file_name = fileName;
			this.search_key = searchKey;
			// TODO Auto-generated constructor stub

			File file = new File(file_name);
			FileReader fileReader;
			try {
				fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				StringBuffer stringBuffer = new StringBuffer();
				String line, curr_key = "";
				int split_length;

				while ((line = bufferedReader.readLine()) != null) {
					stringBuffer.append(line);
					curr_string = stringBuffer.toString();
					// System.out.println("current line is "+curr_string);
					split_words = curr_string.split(",");
					split_length = split_words.length;
					for (int i = 0; i < split_words.length; i++) {
						if (i < split_words.length - 1)
							curr_key += split_words[i];
					}
					// System.out.println("cur key "+curr_key);
					// System.out.println(curr_key);
					if (curr_key.equals(search_key)) {
						// System.out.println("match"+split_words[split_words.length-1]);

						result = Float.parseFloat(split_words[split_words.length - 1]);
						// System.out.println(split_words[split_words.length-1]);
						break;

						// System.out.println(result);
					}
					curr_key = "";
					// System.out.println(split_words[i]);
					// stringBuffer.append("\n");
					stringBuffer = new StringBuffer();
				}
				fileReader.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
		}

	}

	public static void callPy(int value) {
		String s = null;

		int hString = value;

		Runtime r = Runtime.getRuntime();
		System.out.println("angle "+hString);
		try {
			Process p = r.exec("python /home/pi/Project85/servo.py " + hString);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			while ((s = stdInput.readLine()) != null) {
				System.out.println("result sent "+s);
			}

			while ((s = stdError.readLine()) != null) {
				System.out.println("result sent "+s);
			}

			System.exit(0);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(-1);
		}

	}

}
