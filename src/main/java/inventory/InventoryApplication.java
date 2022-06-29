package inventory;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Inventory API",
        version = "1.0.0",
        description = """
                Alkalmazás ami nyilván tartja az otthon található ritkán használt tárgyakat(Thing entity).<br>
                Amit alkalomszerűen használunk, évente 1-2-szer vagy több évente gyakran elfelejtjük hova is tettük.<br>
                Főleg ha nem csak otthon de szüleinknél vagy akár a nyaralónkba is hagyhatunk dolgokat nehezíti a helyzetet,<br>
                ha van mindenhol padlás, garázs, garázs padlás, pince több melléképület(Location entity).
                """,
        contact = @Contact(
                name = "Bruzsa László",
                email = "bruzsalaci@gmail.com")))
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }

}
