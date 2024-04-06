package br.com.webcrawler

import groovyx.net.http.HttpBuilder
import groovyx.net.http.optional.Download
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class WebCrawler {

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
            Element elementoPadraoTISSVersao = docTISS.getElementsByClass("internal-link") get(0)
            String linkPadraoTISSVersao = elementoPadraoTISSVersao.getElementsByTag("a").attr("href")


            Document docPaginaDownload = getPagina(linkPadraoTISSVersao)
            Element elementoPaginaDownload = docPaginaDownload.getElementsByTag('tbody').first().getElementsByTag('tr').last()
            String linkDeDownload = elementoPaginaDownload.getElementsByTag('a').attr('href')

            String nomeDoArquivo = "PadroTISSComunicao202301.zip"
            String caminho = "Downloads/${nomeDoArquivo}"

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

}
