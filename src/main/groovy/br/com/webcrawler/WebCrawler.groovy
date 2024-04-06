package br.com.webcrawler

import br.com.webcrawler.Utils.Utils
import groovyx.net.http.HttpBuilder
import groovyx.net.http.optional.Download
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class WebCrawler {
    private static String diretorioDownloads = "Downloads"

    private static Document getPagina(String url) {
        return HttpBuilder.configure { request.uri = url }.get() as Document
    }

    static String obterLinkPaginaTISS() {
        String linkTISS = ''
        try {

            Document docANS = getPagina("https://www.gov.br/ans/pt-br")
            Element elementoPrestadores = docANS.getElementById("ce89116a-3e62-47ac-9325-ebec8ea95473")
            String linkPrestadores = elementoPrestadores.getElementsByTag("a").attr("href")

            Document docPrestadores = getPagina(linkPrestadores)
            Element elementoTISS = docPrestadores.getElementsByClass("govbr-card-content").first()
            linkTISS = elementoTISS.getElementsByTag("a").attr("href")

        } catch (IOException e) {
            e.printStackTrace()
        }
        return linkTISS
    }

    static void fazerDownloadDoArquivo() {
        try {
            Document docTISS = getPagina(obterLinkPaginaTISS())
            Element elementoPadraoTISSVersao = docTISS.getElementsByClass("internal-link").get(0)
            String linkPadraoTISSVersao = elementoPadraoTISSVersao.getElementsByTag("a").attr("href")


            Document docPaginaDownload = getPagina(linkPadraoTISSVersao)
            Element elementoPaginaDownload = docPaginaDownload.getElementsByTag('tbody').first().getElementsByTag('tr').last()
            String linkDeDownload = elementoPaginaDownload.getElementsByTag('a').attr('href')

            Utils.criarDiretorio(diretorioDownloads)

            String nomeDoArquivo = "PadroTISSComunicao202301.zip"
            String caminho = "${diretorioDownloads}/${nomeDoArquivo}"

            File arquivo = new File(caminho)

            HttpBuilder.configure {
                request.uri = linkDeDownload
            }.get {
                Download.toFile(delegate, arquivo)
            }

        } catch (IOException e) {
            e.printStackTrace()
        }
    }

    static void obterHistoricoDeVersoesTISS() {
        configurarArquivo()
        try {
            Document docTISS = getPagina(obterLinkPaginaTISS())
            Element elementoHistoricoVersoes = docTISS.getElementsByClass("internal-link").get(1)
            String linkHistoricoVersoes = elementoHistoricoVersoes.getElementsByTag("a").attr("href")

            Document docTabela = Jsoup.connect(linkHistoricoVersoes).get()
            Element elementoTabela = docTabela.getElementsByTag("tbody").first()
            Elements linhasTabela = elementoTabela.getElementsByTag("tr")

            for (Element linhaTabela : linhasTabela) {

                Elements colunasTabela = linhaTabela.getElementsByTag("td")
                String competencia = colunasTabela.get(0).text()

                List<String> competenciaAno = competencia.tokenize("/")
                int ano = Integer.parseInt(competenciaAno[1])

                if (ano >= 2016) {
                    String publicacao = colunasTabela.get(1).text()
                    String inicioVigencia = colunasTabela.get(2).text()

                    adicionarDadosArquivo(competencia, publicacao, inicioVigencia)
                }

            }

        } catch (IOException e) {
            System.err.println("Falha ao coletar informações: " + e.getMessage())
            throw e
        }

    }

    private static void configurarArquivo() {
        Utils.criarDiretorio(diretorioDownloads)
        try {
            String caminhDoArquivo ="Downloads/historico-versoes-componentes"

            FileWriter fileWriter = new FileWriter(caminhDoArquivo)
            BufferedWriter writer = new BufferedWriter(fileWriter)
            writer.write("Competência\tPublicação\tInício de Vigência\n")
            writer.close()

        } catch (IOException e) {
            System.err.println("Falha ao conectar-se ao site: " + e.getMessage())
            throw e
        }
    }

    private static void adicionarDadosArquivo(String competencia, String publicacao, String inicioVigencia) {
        try {
            String caminhDoArquivo ="Downloads/historico-versoes-componentes"
            
            FileWriter fileWriter = new FileWriter(caminhDoArquivo,true)
            BufferedWriter writer = new BufferedWriter(fileWriter)

            writer.write("${competencia}\t${publicacao}\t${inicioVigencia}\n")
            writer.close()

        } catch (IOException e) {
            System.err.println("Falha ao conectar-se ao site: " + e.getMessage())
            throw e
        }
    }


}
