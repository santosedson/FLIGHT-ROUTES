# FLIGHT-ROUTES
Esse projeto é um desafio que utiliza o algoritmo de Dijkstra para definir rotas com o menor custo

- Versão: 0.0.1
- Linguagem: Java 8
- Autor: Edson B. S. Filho
- Data: 31.08.2020 
- Local: São Paulo -SP

## Como executar essa aplicação: ## 

 1. Se for rodar a aplicação no Windows, execute o bat `INICIAR-FLIGHT-ROUTES.bat` que se encontra na raiz do projeto.
	Se o arquivo bat não funcionar corretamente, na pasta do projeto execute o seguinte comando :
	```shell
	$ mvnw spring-boot:run
	```
	
	**Obs:** Se for a primeira vez que estiver sendo executado uma aplicação spring boot, irá demorar um pouco baixando as dependências necessárias.
	Quando aparecer o banner significa que a aplicação foi iniciada com sucesso.
 
 2. Ao rodar a aplicação, será iniciado  a interface do Console e interface Rest simultaneamente.
	- **Console:** 
		Para usar essa interface, é só seguir as instruções  das mensagens que já aparecerão na linha de comando após inicializar a aplicação.
		
		**Obs:** Nesse momento não será necessário inserir o nome do arquivo csv, visto que o diretório de arquivos
		já está mapeado e a aplicação pode funcionar com múltiplos arquivos csv.
		
	- **Rest Api:** 
		Por default a interface rest usará a porta 8081. Caso precise mudar a porta, alterar no arquivo `application.properties` localizado em `src\main\resources`
		- Edpoint de consultas:  http://localhost:8081/api/v1/route/find?route=FROM-TO
		- Verbo Http: GET
		- Response code de sucesso: 202
		- Response body: Conteúdo HTML
		- Descrição de utilização: Para acessar esse endpoint, poderá utilizar o navegador do seu computador ou a aplicação Postman.
		Exemplo de formato esperado no parâmetro: 
					```
					REC-SSA
					```
		
		- Edpoint para adicionar novas rotas:  http://localhost:8081/api/v1/route/input
		- Verbo Http: POST
		- Response code de sucesso: 202
		- Response body: Conteúdo HTML
		- Descrição de utilização: Como esse endpoint espera receber um json como conteúdo, para testá-lo poderá ser utilizado o Postman.
		Esse endpoint espera receber um json no seguinte formato:
					**```
					{
						"fileName": "input-routes",
						"route": [
							{
								"from": "GRU",
								"to": "CDG",
								"cost": "100"
							}
						]
					}
					```**
		- No campo `fileName` digite o nome do arquivo csv que deseja inserir as novas rotas(não deve ser informado a extensão do arquivo)
		Dentro da estrutura route, com capacidade para receber uma lista de rotas, no campo `from` digite o aeroporto de origem,
		no campo `to`, digite o aeroporto de destino, no campo `cost`, digite o valor desejado 
		No campo `cost`, se o valor for um número com pontuação flutuante, utilizar o caractere `.` ex: 100.90)

3. Para finalizar a aplicação, basta apenas fechar o terminal.

## Estrutura dos arquivos/pacotes: ## 

- **Estrutura de pacotes:**
	- com.edson.filho.flight.routes : Estrutura base  que contém os demais subpacotes e classe Main da aplicação.
		- commons: 	Nesse pacote se encontram classes Enums com	todas as mensagens da aplicação e configuração de diretório.
		- controller: Nesse pacote se encontram as classes que controlam a interface de console e interface Rest
		- data: Nesse pacote se encontram todas as classes POJO (DTOs)utilizadas  na deserialização do csv e json
		- service: Nesse pacote se encontram todas as classes com a regra de negócio da aplicação.
		- util: Nesse pacote se encontram todas as classes utilitárias do projeto.
		
- **Estrutura de arquivos:**
	 - Os arquivos csv estão mapeados no seguinte diretório: `\src\main\resources\csv`
	 - Como a aplicação suporta mais de um arquivo csv, dessa forma mais arquivos poderão ser adicionados a este diretório.
	 **Obs:** A aplicação considera somente arquivos com extensão `.csv` .
	 Outro ponto importante é que os arquivos também deverão estar na mesma formatação do `input-routes.csv` oferecido como exemplo.


	
## Explicando as decisões de design adotadas para a solução: ## 
Construí a aplicação procurando separar as funcionalidades em camadas bem definidas e desacopladas, de uma 
forma que permitisse a reutilização de funcionalidades por ambas as interfaces (Console e Rest Api).
A seguir eu explico com mais detalhes

- commons: 
	- Messages.java:
		Para a exibição de mensagens no console e na interface Rest, optei por centralizar todas as mensagens da aplicação em uma classe enum ,
		pois ficaria mais organizado e seria mais fácil de dar manutenção caso eu precisasse mudar algum texto a ser exibido
	- Directory.java:
		Também para efeitos de organização, controle e manutenção,
		achei importante centralizar o caminho do diretório que pretendo buscar os arquivos csv , bem como especificar um formato de arquivo 
		que minha aplicação espera receber.
- utils:
	- Formater.java:
		No desenvolvimento da aplicação , por várias vezes eu me deparei com situações que era necessário aplicar diversos tipos formatação.
		Exemplo: Para exibir o resultado no navegador, eu achei mais organizado exibir em um formato HTML, 
		porém como a classe que traz o resultado é a mesma para a interface de console, exibir a mensagem com tags HTML nessa interface seria inadequado.
		Dessa forma, para manter mais de um formato de texto de exibição para diferentes interfaces, optei com criar uma classe utilitária
		que cuidaria da formatação. Essa classe também é utilizada para formatar os textos gravados no arquivo csv. 		
	- Dijkstra.java:
		Apesar dessa classe conter a lógica por trás da escolha da rota mais barata, achei que ela se encaixava mais como um utilitário 
		do que uma regra de negócio, visto que esse algoritmo também pode resolver outros problemas de rotas em outros contextos,
		por exemplo, achar o caminho mais curto de um ponto X até o ponto Y.
		- Curiosidade:
		Após tentar por duas vezes criar um algoritmo apenas usando os fundamentos da minha lógica, obtendo sucesso em alguns cenários e em outros não,
		decidi estudar sobre algoritmos de rota. Então abandonei as soluções anteriores e me aprofundei no estudo do algoritmo de Dijkstra 
		(nome dado em homenagem ao cientista Edsger Dijkstra que desenvolveu um algoritmo em 1956 solucionando o problema do caminho mais curto num grafo).
- data:
		Todas as classes desse pacote foram essenciais para que eu pudesse manipular os dados do arquivo csv e entradas do usuário de forma mais coerente 
		e reutilizável. 
- controller:
	  - FlightRoutesConsole.java:
		Para a interface de console, desenvolvi uma classe simples que aguarda por entradas do usuário e funciona de maneira independente da interface Rest. 
	  - FlightRoutesRestEndpoints:
		Para a interface de Rest, desenvolvi uma classe Rest com dois endpoints.Um para consulta e outro para acrescentar novas rotas. 
		Para o endpoint de consulta, a aplicação espera receber um parâmetro na url.
		Já pra o endpoint de adição de novas rotas, a aplicação espera receber um Json com o nome do arquivo a ser editado e os detalhes da rota.
		Optei por utilizar o Json para ter a possibilidade de inserir mais de uma rota em uma única chamada,
		e porque deixa a entrada de dados mais organizada facilitando o processo de deserialização.
	  
- service:	
	 - FileHandler.java:
		Defini um classe somente para manipular o arquivo csv. Essa classe irá fazer todo o processo de varredura em diretórios, 
		selecionar arquivos csv, leitura e edição de arquivo.
	 - FlightRoutesHandler.java:
		Essa classe é o core da aplicação. Nela estão todas as regras de negócio que diz respeito a natureza da aplicação,
		que é traçar a rota de viagem com o menor custo. 
		No código fonte no inicio de cada método existe uma descrição explicando o que cada função faz.
