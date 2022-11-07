O projeto foi desenvolvido com o java17.

Para rodar o projeto é necessário fazer o download ou clonar o repositório e executar o método main do projeto.

Antes de iniciar o Spring Boot ele vai rodar um comando para criar o banco automaticamente, então é necessário alterar o código em dois lugares:

Necessário colocar o usuário e senha do postgres no arquivo DesafioSeniorApplication.java, e após configurar neste arquivo, é necessário mudar o usuário e senha do postgres no application.properties.

DesafioBackApplication.java
![Alt text](/src/main/resources/img/desafiobackapplication.png?raw=true "DesafioSeniorApplication")

application.properties
![Alt text](/src/main/resources/img/application.properties.png?raw=true "application.properties")

O projeto foi dividido em pastas, sendo elas:
* controller - Possui todas os controllers com as rotas do projeto.
* dto - Possui todos o DTOS do projeto.
* form - Possui os forms que são enviados no body da requisição.
* config - Pasta onde está configurado o ControllerAdvice.
* enums - Pasta onde fica todos enums do projeto.
* exception - Pasta onde é localizada as exceptions personalizadas.
* model - Pasta com as entidades do banco.
* repository - Pasta onde está localizada as interfaces implementadas com o JPARepository que faz comunicação com o banco, além disso, estas interfaces estão implementando as interfaces custom, onde é realizado as consultas com queryDSL
* service - Pasta com os serviços que fazem a comunicação entre os controllers e os repositorys com as regras de negócio.
* test - pasta onde estão localizadas os testes dos services.


Para realizar o cálculo de desconto do pedido, foi desenvolvido uma rota para isso, que é passado na url o id do pedido e o percentual de desconto.

Dentro da classe  PedidoServiceImpl (que implementa PedidoService) será verificado se o pedido está com status 'Aberto', caso contrário retorna um mensagem, alertando o usuário sobre isso. Caso todos os produtos do pedido forem do tipo 'Serviço', não será realizado o desconto, e também retornará uma mensagem de erro.

O método basicamente irá somar os valores dos produtos que não são 'Serviço', e aplicará o valor total do desconto no pedido.
![Alt text](/src/main/resources/img/urldesconto.png?raw=true "urldesconto")


Foi realizado todas as validações, seguindo a prova nível III
