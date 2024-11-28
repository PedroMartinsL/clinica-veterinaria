# Sistema de Gerenciamento de Clínica Veterinária

Este é um sistema de gerenciamento completo para clínicas veterinárias, projetado para otimizar o fluxo de trabalho e melhorar a gestão de consultas, internamentos, histórico médico, estoque de medicamentos e o quadro financeiro. O objetivo é proporcionar um controle eficiente para veterinários, auxiliares e administradores, permitindo uma gestão mais ágil e organizada, o que resulta em melhor atendimento aos pets e maior eficiência na administração da clínica.

![Design sem nome](https://github.com/user-attachments/assets/9a47f6dc-c898-47d3-bafc-8b8125252681)

## Funcionalidades Principais
Entre as funcionalidades principais, o sistema permite o cadastro de pets com informações como CPF do tutor, raça e idade, além da gestão de consultas, onde os usuários podem solicitar consultas e realizar atendimentos. Os veterinários podem realizar diagnósticos e prescrever medicamentos após o atendimento. O sistema também permite o controle de internamento, onde se gerencia o horário de medicação dos pets internados. Além disso, há uma ferramenta para gerenciamento do estoque de medicamentos e uma funcionalidade para exibir o histórico de doenças de cada animal. Por fim, o sistema oferece um módulo de quadro financeiro para monitoramento e gerenciamento das finanças da clínica.

## I. Diagrama de Casos de Uso
O diagrama a seguir ilustra os principais casos de uso do sistema, descrevendo as interações entre os usuários (veterinário, auxiliar do veterinário, administrador) e as funcionalidades do sistema.

![Diagrama de Casos de Uso](https://github.com/user-attachments/assets/2b2da7db-3c9e-44fa-8ecd-11b10aff7324)

### Casos de Uso Principais:

- **Solicitar Consulta**: Clientes (tutores dos pets) podem agendar consultas para seus animais através do sistema.
- **Atender Consulta**: Veterinários podem acessar as consultas agendadas, realizar o atendimento e fazer o diagnóstico.
- **Prescrever Medicamentos**: Após o diagnóstico, veterinários podem prescrever medicamentos para o tratamento.
- **Controle de Medicação**: Auxiliares e veterinários podem controlar a administração de medicamentos e os horários para os pets internados.
- **Gerenciar Estoque de Medicamentos**: Administradores têm a capacidade de adicionar, atualizar e remover medicamentos no estoque da clínica.
- **Consultar Histórico de Doenças**: Veterinários podem acessar o histórico de doenças de cada pet para ajudar no diagnóstico.
- **Gerenciar Quadro Financeiro**: Administradores podem gerenciar as finanças da clínica, monitorando receitas, despesas e balanços financeiros.


**Exemplo de Fluxo de Trabalho**:
1. O tutor de um pet acessa o sistema e solicita uma consulta.
2. O veterinário recebe a notificação e realiza a consulta.
3. Após o atendimento, o veterinário prescreve medicamentos e o auxiliar realiza o controle de medicação.
4. O administrador atualiza o estoque de medicamentos conforme necessário e acompanha o quadro financeiro.


## II. Diagrama de Classes (UML)
O Diagrama de Classes abaixo representa a estrutura do sistema, incluindo as classes, seus atributos e métodos, bem como os relacionamentos entre elas.

![image](https://github.com/user-attachments/assets/66307b2c-b155-47bb-99a9-99e3280cec1b)

### Principais Classes:
1. **Entidades** (Classe abstrata)
   - **Atributos**: nome, cpf, senha

2. **Pet**
   - **Atributos**: cpf (do tutor), idade, raca, alimento
   - **Método**: solicitarAlimentacao()

3. **Funcionario** (Classe base para Veterinário, Auxiliar e Administrador, herda de Entidades)
   - **Atributos**: setor
   - **Métodos**: cobrarConsulta(), solicitarConsulta()

4. **Veterinario** (Herda de Funcionario)
   - **Métodos**: checarDiagnostico(), prescreverMedicamento()

5. **Auxiliar** (Herda de Funcionario)
   - **Métodos**: atender(), internarConsulta()

6. **Administrador** (Herda de Funcionario)
   - **Métodos**: gerenciarEstoque(), consultarHistoricoFinanceiro(), removerClinica()

7. **Clinica**
   - **Atributo**: estoqueMedicamentos
   - **Métodos**: reportMedicamento(), removerMedicamento()

8. **Medicamento**
   - **Métodos**: medicar()

9. **Servico**
   - **Métodos**: financeiro(), iniciarConsulta()

### Outras Classes:
- **MedicamentoFactory**: Implementa o padrão Factory para criar medicamentos.
- **JDBC**: Classe responsável pela comunicação com o banco de dados.


- A classe `Funcionario` é uma classe base para os diferentes tipos de funcionários (veterinário, auxiliar, administrador). Ela contém atributos e métodos comuns, como `setor`, `cobrarConsulta()` e `solicitarConsulta()`.
- A classe `Veterinario` herda de `Funcionario` e adiciona métodos específicos, como `checarDiagnostico()` e `prescreverMedicamento()`, para realizar o atendimento e prescrição dos tratamentos.
- A classe `Auxiliar` também herda de `Funcionario` e tem métodos como `atender()` e `internarConsulta()` para gerenciar o atendimento dos pets e controle de medicação durante o internamento.
- A classe `Administrador` herda de `Funcionario` e adiciona funcionalidades como `gerenciarEstoque()` e `consultarHistoricoFinanceiro()` para administrar as finanças e o estoque de medicamentos da clínica.


## III. Tecnologias Utilizadas
- **Linguagem de Programação**: Java
- **Banco de Dados**: MySQL (ou outro banco de dados relacional)
- **Diagrama UML e Casos de Uso**: Criados utilizando ferramentas de modelagem (draw.io)

## IV. Requisitos do Sistema
- **Java 11+**
- **MySQL**

## V. Método de Instalação do Código

1. **Clonagem do Repositório**:
   - Utilize o GitHub Desktop para clonar o repositório do código. Isso dará acesso ao código localmente.

2. **Caso de Erro na Clonagem**:
   - Após clonar o repositório, você pode executar o código. Se o código não aparecer imediatamente, vá para o passo 3.

3. **Importação do Arquivo**:
   - No GitHub Desktop, vá para a opção de importação. Escolha "General" e selecione "Existing Projects into Workspace". Selecione o arquivo que está localizado na sua máquina. Clique em "Finish" para concluir a importação.

4. **Execução do Código**:
   - Após importar o arquivo, você poderá executar o código para realizar testes.
  
## Configuração do Banco de Dados

1. Crie um banco de dados chamado `clinica_veterinaria` no MySQL.
2. Importe o script SQL fornecido na pasta `database` para criar as tabelas necessárias.
3. Altere as configurações de conexão no arquivo `config.properties` para refletir seu ambiente de banco de dados local.

## Erros Comuns

- **Erro de conexão com o banco de dados**: Certifique-se de que as credenciais e a URL de conexão estejam corretas no arquivo `config.properties`.
- **Problema com a importação do projeto no IDE**: Verifique se a configuração do projeto está adequada para a versão do Java que você está utilizando.


## VI. Links

- **Link - UML**:
  [Diagrama UML](https://app.diagrams.net/#G1jvlncTRlkBArGsaFYYBzOIaKXMqghCRG#%7B%22pageId%22%3A%222Nwkg8L6xev0F9r1x96o%22%7D)

- **Link - Diagrama de Casos de Uso**:
  [Diagrama de Casos de Uso](https://miro.com/app/board/uXjVKg2JXh8=/) (Essa é a versão antiga; consulte o diagrama atualizado acima).

## VII. Como Contribuir

1. **Faça um Fork** do repositório.
2. Crie uma nova branch para a sua feature: 
   ```bash
   git checkout -b feature/nova-feature
   ```
3. Implemente a funcionalidade ou correção desejada e escreva testes para validar sua implementação.
4. Faça um commit com a descrição da mudança:
   ```bash
   git commit -am 'Adiciona nova feature'
   ```
5. Envie as alterações para o seu fork:
   ```bash
   git push origin feature/nova-feature
   ```
6. Abra um **Pull Request** explicando suas mudanças.
7. Certifique-se de que todos os testes estejam passando e que a documentação esteja atualizada.


## VIII. Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.














