package br.com.webcrawler

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class WebCrawlerTest {

    private static WebCrawler webCrawler
    @BeforeAll
    static void instaciaWebCrawler() {
        webCrawler = new WebCrawler()
    }

    @Test
    void obterLinkPaginaTISSTest() {
        // Given:
        String linkEsperado = "https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss"
        // When:
        String linkObtido = WebCrawler.obterLinkPaginaTISS()
        //Then:
        assertEquals(linkEsperado,linkObtido)
    }

    @Test
    void fazerDownloadDoArquivoTest() {
        // Given:
        String nomeDoArquivoEsperado = "PadroTISSComunicao202301.zip"
        // When:
        WebCrawler.fazerDownloadDoArquivo()
        //Then:
        File arquivo = new File("Downloads/${nomeDoArquivoEsperado}")
        assert arquivo.exists()
    }

    @Test
    void obterHistoricoDeVersoesTISSTest() {
        // Given:
        String nomeDoArquivoEsperado = "historico-versoes-componentes.txt"
        // When:
        WebCrawler.obterHistoricoDeVersoesTISS()
        //Then:
        File arquivo = new File("Downloads/${nomeDoArquivoEsperado}")
        assert arquivo.exists()
    }

    @Test
    void fazerDownloadDaTabelaErrosANSTest() {
        // Given:
        String nomeDoArquivoEsperado = "Tabelaerrosenvioparaanspadraotiss__1_.xlsx"
        // When:
        WebCrawler.fazerDownloadDaTabelaErrosANS()
        //Then:
        File arquivo = new File("Downloads/${nomeDoArquivoEsperado}")
        assert arquivo.exists()
    }
}