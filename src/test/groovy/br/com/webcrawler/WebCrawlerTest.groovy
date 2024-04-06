package br.com.webcrawler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        String arquivoEsperado = "PadroTISSComunicao202301.zip"
        // When:
        WebCrawler.fazerDownloadDoArquivo()
        //Then:
        File arquivo = new File("Downloads/${arquivoEsperado}")
        assert arquivo.exists()
    }
}