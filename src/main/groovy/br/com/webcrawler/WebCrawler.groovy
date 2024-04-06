package br.com.webcrawler

import br.com.webcrawler.WebCrawlerUtils.Utils
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

            Utils.baixarESalvarArquivos(linkDeDownload,caminho)

        } catch (IOException e) {
            e.printStackTrace()
        }
    }

    static void obterHistoricoDeVersoesTISS() {
        Utils.criarDiretorio(diretorioDownloads)
        Utils.configurarArquivo()
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

                    Utils.adicionarDadosArquivo(competencia, publicacao, inicioVigencia)
                }

            }

        } catch (IOException e) {
            System.err.println("Falha ao coletar informações: " + e.getMessage())
            throw e
        }

    }

    static void fazerDownloadDaTabelaErrosANS() {
        try {
            Document docTISS = getPagina(obterLinkPaginaTISS())
            Element elementoTabelasRelacionadas = docTISS.getElementsByClass("internal-link").get(2)
            String linkTabelasRelacionadas = elementoTabelasRelacionadas.getElementsByTag("a").attr("href")

            Document docPaginaDownload = getPagina(linkTabelasRelacionadas)
            Element elementoPaginaDownload = docPaginaDownload.getElementsByClass("internal-link").get(0)
            String linkDeDownload = elementoPaginaDownload.getElementsByTag('a').attr('href')

            Utils.criarDiretorio(diretorioDownloads)

            String nomeDoArquivo = "Tabelaerrosenvioparaanspadraotiss__1_.xlsx"
            String caminho = "${diretorioDownloads}/${nomeDoArquivo}"

            Utils.baixarESalvarArquivos(linkDeDownload,caminho)

        }catch (IOException e) {
            e.printStackTrace()
        }

    }

}
