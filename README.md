# 🚀 Spring Batch Journey - Multi-Service Architecture

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" alt="Java">
  <img src="https://img.shields.io/badge/Spring_Boot-3.5.9-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Docker-Enabled-blue?style=for-the-badge&logo=docker" alt="Docker">
  <img src="https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql" alt="MySQL">
</p>

**Ecossistema de Processamento em Lote** — Uma solução robusta desenvolvida para demonstrar a integração entre microserviços, consumo de APIs REST paginadas e persistência resiliente utilizando Spring Batch.

---

## 📌 Sobre o Projeto

O projeto foca na automação de fluxos de dados complexos, simulando um ambiente real onde um Job de processamento deve buscar informações de uma API externa e consolidá-las em um banco de dados relacional de forma eficiente e escalável.

### ✨ Destaques Técnicos
* **Arquitetura em Camadas:** Separação clara entre Readers, Processors (quando aplicável), Writers e Configurações de Job.
* **Processamento orientado a Chunks:** Divisão de carga de trabalho para otimização de memória e transações seguras.
* **Consumo de API Paginada:** Implementação de `ItemReader` customizado integrado ao `RestTemplate` para extração de dados remotos.
* **Isolamento de Dados:** Configuração de múltiplos esquemas de banco de dados gerenciados centralizadamente via Docker para suportar diferentes etapas da jornada.

---

## ☁️ Infraestrutura e Deploy (Home Lab)

Diferente de projetos acadêmicos simples, esta aplicação utiliza uma infraestrutura de containers para garantir a paridade entre ambientes de desenvolvimento e produção.

* **Containerização:** Aplicação e Bancos de Dados (MySQL) orquestrados via Docker Network.
* **Gerenciamento Visual:** Integração com phpMyAdmin para monitoramento em tempo real das tabelas de metadados do Batch e dos dados persistidos.
* **Múltiplos Bancos:** Estrutura organizada por prefixos (ex: `spring_batch`, `spring_batch_projeto2`) permitindo isolamento total entre Jobs.
* **Desafios Superados:** Resolução de conflitos de rede, mapeamento de portas customizadas (3307) e tratamento de erros de conexão I/O.



---

## 🎯 Competências Desenvolvidas

Este projeto consolidou os seguintes conhecimentos práticos:

- [x] **Implementação de Jobs e Steps:** Configuração de fluxos de trabalho sequenciais e independentes.
- [x] **Persistência de Metadados:** Configuração do Job Repository para rastreabilidade e controle de execução.
- [x] **Integração REST Avançada:** Lógica de consumo de dados externos com tratamento de paginação e exceções de conexão.
- [x] **Configuração Dinâmica:** Uso de `@Value` com fallbacks e gerenciamento de arquivos `application.properties` para múltiplos ambientes.
- [x] **Gerenciamento de Logs:** Uso de SLF4J para rastreamento de leitura de chunks e finalização de processos.

---

## 🛠️ Como Executar

1. **Subir Infraestrutura:** ```bash
    docker-compose up -d

2. **Preparar Banco de Dados:**```bash
     Através do phpMyAdmin (localhost:5050), garantir a criação dos esquemas conforme configurado no projeto.

3. **Rodar API de Origem:**```bash
     Certificar-se de que o serviço de dados na porta 8081 está ativo.

4. **Executar Job:***```bash
     mvn spring-boot:run    
