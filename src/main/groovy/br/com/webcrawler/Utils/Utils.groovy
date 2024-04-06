package br.com.webcrawler.Utils

class Utils {
    static void criarDiretorio(String diretorio){
        File dirDownloads = new File(diretorio)

        if (!dirDownloads.exists()) {
            dirDownloads.mkdir()
        }
    }
}
