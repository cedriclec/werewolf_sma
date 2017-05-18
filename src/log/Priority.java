package log;

public enum Priority {
	
	CRITICAL, //erreur critique, qui doit �tre trait�e imm�diatement
	HIGH, //erreur de haute priorit�
	NORMAL, //erreur de priorit� normale
	LOW, //erreur non bloquante
	INFO, //information systeme
	NOTIFICATION, //message de notification (d'un �v�nement par exemple)
	
}
