package tk.tempmlhub.api.beans;

import org.springframework.context.annotation.Bean;
import net.datafaker.Faker;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class FakerBean {

    @Bean(name = "faker")
    public List<Faker> getFaker() {
        List<Locale> locales = Arrays.asList(new Locale("en"), new Locale("de"),
                new Locale("nl"), new Locale("it"), new Locale("fr"),
                new Locale("pl"), new Locale("es"), new Locale("pt"),
                new Locale("in"), new Locale("en", "NZ"),
                new Locale("en", "AU"), new Locale("en", "IN"));

        List<Faker> fakers = new ArrayList<>();

        locales.stream()
                .forEach(locale -> {
                    fakers.add(new Faker(locale));
                });

        return fakers;
    }
}
