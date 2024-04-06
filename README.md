# Projeto Web Scraping para coleta de dados do site do Governo da Agência Nacional de Saúde (ANS)
## Por: Cássio Ferreira Rodrigues
Este projeto tem o objetivo de coletar dados do padrão TISS (Troca de Informações na Saúde Suplementar).
## Funcionalidades deste Projeto
* Fazer raspagem de dados no site da TISS em três URLs diferentes, para baixar arquivos e fazer raspagem de dados para criar e 
  salvar em um arquivo em uma pasta de "Downloads".
## Tecnologias Usadas
* [Groovy](http://www.groovy-lang.org)
* [Gradle](https://gradle.org/)
## Para executar este projeto
***1. Clone este repositório***
```bash
   git clone https://github.com/CassioFerreiraRod/web-crawler.git
``` 
***2. Execute o gradle do programa pelo terminal ou prompt***
* Sistemas Linux e Sistemas baseados em Unix
```shell
  ./gradlew run
  ``` 
* Windows
```shell
  gradlew.bat run
``` 

***3. Após a execução navegue até a pasta Downloads para ver os arquivos obtidos***
```shell
  cd Downloads
``` 