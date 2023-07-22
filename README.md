# Teste Prático

**Projeto de Lembretes - Backend**

Este é o backend de um projeto de lembretes, uma aplicação Java Spring Boot que fornece endpoints RESTful para criar, listar e excluir lembretes. A aplicação gerencia lembretes agrupados por datas em um sistema de lembretes. O banco de dados utilizado é o MySQL.

**Premissas Assumidas**

Os lembretes são agrupados por data, e a data é representada pelo objeto LocalDate.
A aplicação pressupõe que o banco de dados MySQL esteja em execução em um contêiner Docker local.

**Decisões de Projeto**

A aplicação utiliza o padrão de projeto MVC para separar as preocupações e garantir uma estrutura organizada.
Foi implementado o tratamento de exceções personalizadas para fornecer respostas de erro adequadas em caso de problemas.
O CORS foi configurado para permitir solicitações de qualquer origem.

**Funcionalidades Principais**
O backend possui os seguintes endpoints RESTful:

POST /api/reminders/create: Cria um novo lembrete com base nos dados fornecidos no corpo da requisição.

GET /api/reminders/list: Retorna todos os lembretes agrupados por data.

DELETE /api/reminders/delete/{id}: Deleta o lembrete com o ID especificado.

**Tecnologias Utilizadas**
- Java 17
- Spring Boot 3.1.2
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Docker

**Arquitetura**
A aplicação segue uma arquitetura MVC (Model-View-Controller), onde:
- O pacote "config" contém configurações adicionais para a aplicação, como CORS e tratamento de exceções personalizadas.
- O pacote "controller" contém os controladores da aplicação, que definem os endpoints RESTful para os lembretes.
- O pacote "exception" contém exceções personalizadas que podem ser lançadas pela aplicação.
- O pacote "reminder" contém a entidade Reminder e seu repositório.
- O pacote "reminder_group" contém a entidade ReminderGroup e seu repositório.
- O pacote "service" contém a lógica de negócio da aplicação, que é responsável por criar, listar e excluir lembretes.

**Instruções para executar o sistema**
1. Certifique-se de ter o Java 17 instalado em seu sistema.
2. Instale o Docker em seu sistema. ( Caso seja docker desktop, abra ele )
3. Clone este repositório em seu computador.
4. Navegue para o diretório `server` do projeto.
5. Execute o comando `mvn spring-boot:run` para iniciar a aplicação Spring Boot.
6. O backend estará disponível em `http://localhost:8080`.

**Testes Unitários - Backend**

O backend possui testes unitários implementados para garantir o correto funcionamento das principais funcionalidades da aplicação. Os testes são escritos utilizando a biblioteca JUnit e o framework de mock Mockito. O objetivo dos testes é verificar se as operações do serviço ReminderService estão sendo executadas de acordo com o esperado.

**Executando os Testes**

Para executar os testes unitários, siga os passos abaixo:

**Requisitos**
- Certifique-se de ter o Java Development Kit (JDK) instalado em seu sistema.
- Garanta que o backend (aplicação Java Spring Boot) esteja funcionando corretamente, pois os testes interagem com o banco de dados MySQL.

**Passo a Passo**
1. Abra o terminal ou prompt de comando e navegue até a pasta raiz do projeto backend.
2. Execute o seguinte comando para iniciar a execução dos testes:
`./mvnw test`
Caso esteja utilizando o Windows, utilize o seguinte comando:
`mvnw test`

**Observações**
- O arquivo ReminderServiceTest.java contém os testes unitários para as funcionalidades getAll, deleteById, createReminder e deleteByIdThrowsBadRequestException.
- O teste testGetAll verifica se o método getAll() do ReminderService está retornando a lista de lembretes agrupados por data corretamente.
- O teste testDeleteById verifica se o método deleteById() do ReminderService está excluindo o lembrete especificado pelo ID e, se for o caso, também excluindo o grupo de lembretes caso esteja vazio.
- O teste testCreateReminder verifica se o método createReminder() do ReminderService está criando um novo lembrete corretamente e associando-o ao grupo de lembretes correto.
- O teste testDeleteByIdThrowsBadRequestException verifica se o método deleteById() do ReminderService lança a exceção BadRequestException quando tenta excluir um lembrete inexistente.

**Projeto de Lembretes - Frontend**

Este é o frontend de um projeto de lembretes, uma aplicação web construída com HTML, CSS e JavaScript. A interface do usuário permite criar, listar e excluir lembretes através da interação com o backend do sistema. O backend é uma aplicação Java Spring Boot que gerencia lembretes agrupados por datas em um banco de dados MySQL.

**Funcionalidades Principais**
- Criar um novo lembrete com nome e data especificados pelo usuário.
- Listar todos os lembretes existentes, agrupados por data.
- Excluir um lembrete específico ao clicar no botão "X".

**Tecnologias Utilizadas**
- HTML
- CSS
- JavaScript
- Axios (biblioteca para fazer requisições HTTP)

**Arquivos Principais**
- index.html: Página inicial com a interface do usuário.
- styles.css: Arquivo de estilo para formatar a aparência da aplicação.
- script.js: Arquivo JavaScript contendo a lógica da aplicação, incluindo a interação com o backend através de requisições HTTP.

**Instruções de Uso**
1. Certifique-se de ter o backend (servidor Java Spring Boot) em execução antes de iniciar o frontend.
2. Para utilizar o frontend com o servidor http-server, siga as recomendações de instalação abaixo:

**Recomendações de Instalação do http-server**
- Certifique-se de ter o Node.js instalado em seu sistema. Caso ainda não tenha, você pode baixá-lo e instalá-lo a partir do site oficial: https://nodejs.org
- Após instalar o Node.js, abra o terminal ou prompt de comando e instale o http-server globalmente usando o comando:
`npm install -g http-server`

**Iniciando o Frontend com http-server**
1. Abra o terminal ou prompt de comando e navegue até a pasta onde se encontra o projeto do frontend `client`.
2. Execute o seguinte comando para iniciar o servidor http:
`http-server -p 8046`

3. Com o servidor em execução, abra um navegador web e acesse a URL "http://localhost:8046" para acessar a aplicação frontend de lembretes.

**Observações**
- O backend deve estar disponível no endereço "http://localhost:8080" para que a aplicação frontend possa interagir corretamente com ele.
- O frontend utiliza a biblioteca Axios para fazer as requisições HTTP ao backend.
