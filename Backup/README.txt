Para compilar o programa deve-se compilar todos os packages e ficheiros do mesmo, através do comando:
	- Para Windows:
		javac connection/*.java file/*.java information/*.java listener/*.java message/*.java protocol/*.java rmi/*.java sender/*.java spacemanaging/*.java threads/*.java workerHandlers/*.java *.java

Para executar a aplicação no lado do cliente, utiliza-se o comando:
java TestApp <peer_access_point> <subprotocolo> <op1> <op2>

onde:
<subprotocolo> pode ser BACKUP, RESTORE, DELETE ou RECLAIM
<op1> pode ser ou o nome do ficheiro usado nos subprotocolos BACKUP, RESTORE ou DELETE ou então o espaço a reclamar através do subprotocolo RECLAIM
<op2> é o valor de replication degree desejado

Exemplo:
java TestApp 1 BACKUP Twitter.png 3

Para executar um peer:
- Primeiro deve-se inicializar o rmi registry através dos comandos:

	- Para Windows: start rmiregistry
	- Para Linux: rmiregistry &

- De seguida, deve-se correr os peers com:
java Peer <version> <access_point> <serverID> <MC_address> <MC_port> <MDB_address> <MDB_port> <MDR_address> <MDR_port>

onde:
<version> é a versão do programa. Caso seja 1.0 são executados os subprotocolos na sua versão "normal", caso seja diferente de 1.0 sã executados os subprotocolos enhanced
<access_point> é o access point do peer
<serverID> é o ID do servidor
<MC_address> <MC_port> <MDB_address> <MDB_port> <MDR_address> <MDR_port> são os endereços dos canais MC, MDB e MDR e todas as suas portas

Exemplo:
java Peer 1.0 1 2 225.4.5.6 5000 226.4.5.6 5001 227.4.5.6 5002


Sara Fernandes, up201405955
Vasco Pereira, up201403485
