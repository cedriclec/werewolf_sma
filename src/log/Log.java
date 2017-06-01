package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;


/* classe statique permettant d'�crire des logs dans un fichier. */
public class Log {

	private static PrintWriter pw;
	private static File logFile;
	private static Priority priority;
	private static BufferedWriter bw;

	/* m�thode d'initialisation de la classe statique permettant de d�finir un fichier par d�faut ou de le cr�er s'il n'existe pas */
	public static void setup(String logFilePath) 
	{
		try {

			priority = Priority.INFO;
			 logFile = new File(logFilePath);
	         if(!logFile.exists()) {
	        	 logFile.createNewFile();
	         } 
		} catch (Exception ex) {
			System.out.println("Le fichier sp�cifi� est introuvable");
		}
	}
	/* �crit le message pass� en param�tre pr�c�d� de son niveau de priorit� et de la date dans le fichier de log.
	 * le niveau de priorit� est celui actuellement d�fini dans la classe */
	public static void writeLog(String log) throws LogException
	{
		String time = "" + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":"
				+ LocalDateTime.now().getSecond();
		 FileWriter fileWritter;
		try {
			fileWritter = new FileWriter(logFile.getName(),true);
			bw = new BufferedWriter(fileWritter);
	         String data ="["+time+"]"+" "+priority+" : "+log+"\r\n";
	         bw.write(data);
	         bw.close();
		} catch (IOException e) {
			throw new LogException(e.getMessage());
		}
         
	}
	/* �crit un log avec le niveau de priorit� pass� en param�tre */
	public static void writeLog(String log, Priority prio) throws LogException{
		setPriority(prio);
		writeLog(log);
	}
	/* ins�re un s�parateur dans le fichier de log */
	public static void writeSeparator() throws LogException {
		 FileWriter fileWritter;
		try {
			fileWritter = new FileWriter(logFile.getName(),true);
			 bw = new BufferedWriter(fileWritter);
	         String data = "\r\n------------------------------\r\n\r\n";
	         bw.write(data);
	         bw.close();
		} catch (IOException e) {
			throw new LogException(e.getMessage());
		}
        
	}
	/* retourne le chemin du fichier de log */
	public static String getLogFilePath() {
		return logFile.exists() ? 
				 "le fichier sp�cifi� est introuvable ou la m�thode setup() n'a pas �t� appel�e":logFile.getAbsolutePath();
	}
	/* change le fichier de log */
	public static void setLogFilePath(String logFilePath) {
		setup(logFilePath);
	}
	 /* change le niveau de priorit� */
	public static void setPriority(Priority prio) {
		priority = prio;
	}
	/* efface le contenu du fichier de log */
	public static void clearLogFile() {
		FileWriter fileWritter;
		try {
			fileWritter = new FileWriter(logFile.getName());
			 PrintWriter pw = new PrintWriter(fileWritter);
	         pw.print("");
	         pw.close();
		} catch (IOException e) {
			//System.out.println(e.getMessage());
		}

	}

}