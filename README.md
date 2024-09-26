# Sistema de Gerenciamento de Clínica Veterinária

# Funcionalidades Principais
Este projeto é um sistema de gerenciamento para uma clínica veterinária, cujo objetivo é facilitar o controle de consultas, internamentos, histórico médico, estoque de medicamentos e o quadro financeiro da clínica. O sistema é voltado para veterinários, auxiliares e administradores, otimizando a gestão diária da clínica.

Entre as funcionalidades principais, o sistema permite o cadastro de pets com informações como CPF do tutor, raça e idade, além da gestão de consultas, onde os usuários podem solicitar consultas e realizar atendimentos. Os veterinários podem realizar diagnósticos e prescrever medicamentos após o atendimento. O sistema também permite o controle de internamento, onde se gerencia o horário de medicação dos pets internados. Além disso, há uma ferramenta para gerenciamento do estoque de medicamentos e uma funcionalidade para exibir o histórico de doenças de cada animal. Por fim, o sistema oferece um módulo de quadro financeiro para monitoramento e gerenciamento das finanças da clínica.

# I. Diagrama de Casos de Uso
O diagrama a seguir ilustra os principais casos de uso do sistema, descrevendo as interações entre os usuários (veterinário, auxiliar do veterinário, administrador) e as funcionalidades do sistema.

![Diagrama de Casos de Uso](https://github.com/user-attachments/assets/2b2da7db-3c9e-44fa-8ecd-11b10aff7324)

Casos de Uso Principais:
I. Solicitar Consulta: Clientes podem solicitar consultas para seus pets.
II. Atender Consulta: Veterinários podem atender as consultas agendadas.
III. Prescrever Medicamentos: Após o atendimento, os veterinários podem prescrever medicamentos.
IV. Controle de Medicação: Quando necessário, o pet pode ser internado na clínica, e o auxiliar ou veterinário pode controlar a medicação e horários.
V. Gerenciar Estoque de Medicamentos: O administrador pode adicionar ou remover medicamentos no sistema.
VI. Consultar Histórico de Doenças: Permite visualizar o histórico de doenças do pet.
VII. Gerenciar Quadro Financeiro: Administradores podem gerenciar as finanças da clínica, monitorando despesas e receitas.

# II. Diagrama de Classes (UML)
O Diagrama de Classes abaixo representa a estrutura do sistema, incluindo as classes, seus atributos e métodos, bem como os relacionamentos entre elas.

![image](https://github.com/user-attachments/assets/0c0e2f6e-3875-47b6-bd5c-ddfe7beafcf8)

# Principais Classes:
I. Entidades (Classe abstrata)

Atributos: nome, cpf, senha

II. Pet

Atributos: cpf (do tutor), idade, raca, alimento

Método: solicitarAlimentacao()

III. Funcionario (Classe base para Veterinário, Auxiliar e Administrador, herda de Entidades)

Atributos: setor

Métodos:

cobrarConsulta()

solicitarConsulta()

IV. Veterinario (Herda de Funcionario)

Métodos:

checarDiagnostico()

prescreverMedicamento()

V. Auxiliar (Herda de Funcionario)

Métodos:

atender()

internarConsulta()


VI. Administrador (Herda de Funcionario)
Métodos:
gerenciarEstoque()
consultarHistoricoFinanceiro()
removerClinica()

VII. Clinica
Atributo: estoqueMedicamentos
Métodos:
reportMedicamento()
removerMedicamento()

VIII. Medicamento
Métodos: medicar()
Subclasses: Viral, Bacteria, Glucide

IX. Servico
Métodos:
financeiro()
iniciarConsulta()

# Outras Classes:

MedicamentoFactory: Implementa o padrão Factory para criar medicamentos.
JDBC: Classe responsável pela comunicação com o banco de dados.

MedicamentoFactory: Implementa o padrão Factory para criar medicamentos.
JDBC: Classe responsável pela comunicação com o banco de dados.

# III. Tecnologias Utilizadas
I. Linguagem de Programação: Java
II. Banco de Dados: MySQL (ou outro banco de dados relacional)
III. Diagrama UML e Casos de Uso: Criados utilizando ferramentas de modelagem (draw.io)

# IV. Requisitos do Sistema
I. Java 11+
II. MySQL

# V. Método de Instalação do Código
1. Clonagem do Repositório
Utilize o GitHub Desktop para clonar o repositório do código. Isso dará acesso ao código localmente.

2. Caso de Erro na Clonagem
Após clonar o repositório, você pode executar o código. Se o código não aparecer imediatamente, vá para o passo 3.

3. Importação do Arquivo
No GitHub Desktop, vá para a opção de importação. Escolha "General" e selecione "Existing Projects into Workspace". Selecione o arquivo que está localizado na sua máquina. Clique em "Finish" para concluir a importação.

4. Execução do Código
Após importar o arquivo, você poderá executar o código para realizar testes.

# VI. Links
Link - UML:
https://miro.com/welcomeonboard/cWVVbm5NRUhGZVU0Y01CTzdFYUMzUGNZVHJ0cFlmTUY0SVNQQ1hWQTdORkQ0TWhTWnc4SEVTV3lGZXFOa2hHMHwzNDU4NzY0NTk1MTcwNDM2MjE3fDI

Link - Diagrama de Casos de Uso:
https://miro.com/app/board/uXjVKg2JXh8=/


