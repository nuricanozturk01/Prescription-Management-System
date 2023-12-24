package nuricanozturk.dev.service.medicine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.nio.file.Paths;

@Component
@Lazy
public class FileDownloader
{
    private final String BASE_URL = "https://www.titck.gov.tr/dinamikmodul/43";

    private WebClient createWebClient()
    {
        int bufferLimit = 16 * 1024 * 1024;

        var exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(bufferLimit))
                .build();

        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
                .build();
    }

    public void downloadFile()
    {
        var webClient = createWebClient();

        webClient.get()
                .uri(BASE_URL)
                .retrieve()
                .bodyToMono(String.class)
                .map(Jsoup::parse)
                .mapNotNull(doc -> doc.select("a:contains(XLSX)").first())
                .flatMap(element -> downloadFileA(element, webClient))
                .block();
    }

    private Mono<?> downloadFileA(Element element, WebClient webClient)
    {
        var href = element.attr("href");
        var fileUrl = href.startsWith("http") ? href : BASE_URL + href;
        var path = Paths.get("latest_medicine_list.xlsx");

        Flux<DataBuffer> dataBufferFlux = webClient.get()
                .uri(fileUrl)
                .retrieve()
                .bodyToFlux(DataBuffer.class);

        return DataBufferUtils.write(dataBufferFlux, path);
    }
}