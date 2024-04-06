package br.com.webcrawler

import groovyx.net.http.HttpBuilder
import org.jsoup.nodes.Document

class WebCrawler {

    private static Document getPage(String url) {
        return HttpBuilder.configure { request.uri = url }.get() as Document
    }

}
