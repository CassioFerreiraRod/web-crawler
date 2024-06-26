package br.com.webcrawler.WebCrawlerUtils

import groovyx.net.http.HttpBuilder
import groovyx.net.http.optional.Download
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class Utils {
    static void criarDiretorio(String diretorio){
        File dirDownloads = new File(diretorio)

        if (!dirDownloads.exists()) {
            dirDownloads.mkdir()
        }
    }

     static void configurarArquivo() {
        try {
            String caminhDoArquivo ="Downloads/historico-versoes-componentes.txt"

            FileWriter fileWriter = new FileWriter(caminhDoArquivo)
            BufferedWriter writer = new BufferedWriter(fileWriter)
            writer.write("Competência\tPublicação\tInício de Vigência\n")
            writer.close()

        } catch (IOException e) {
            System.err.println("Falha ao criar arquivo: " + e.getMessage())
            throw e
        }
    }

    static void adicionarDadosArquivo(String competencia, String publicacao, String inicioVigencia) {
        try {
            String caminhDoArquivo ="Downloads/historico-versoes-componentes.txt"

            FileWriter fileWriter = new FileWriter(caminhDoArquivo,true)
            BufferedWriter writer = new BufferedWriter(fileWriter)

            writer.write("${competencia}\t${publicacao}\t${inicioVigencia}\n")
            writer.close()

        } catch (IOException e) {
            System.err.println("Falha ao criar adicionar dados arquivo: " + e.getMessage())
            throw e
        }
    }

    static void baixarESalvarArquivos(String link, String caminho) {
        File arquivo = new File(caminho)

        HttpBuilder.configure {
            request.uri = link
        }.get {
            Download.toFile(delegate, arquivo)
        }
    }

}
