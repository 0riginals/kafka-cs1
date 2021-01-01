package org.m2tnsi.cs1.runner;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.m2tnsi.cs1.classes.Country;
import org.m2tnsi.cs1.classes.Global;
import org.m2tnsi.cs1.consumer.Cs1;
import org.m2tnsi.cs1.service.CountryService;
import org.m2tnsi.cs1.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private GlobalService globalService;

    @Autowired
    private CountryService countryService;

    @Override
    public void run(String... args) throws Exception {
        Consumer<Long, String> consumer = Cs1.createConsumer();

        while(true) {
            ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));

            consumerRecords.forEach(record -> {
                if (!record.value().isEmpty()) {
                    JSONParser parser = new JSONParser();
                    try {
                        Object obj = parser.parse(record.value());
                        sendToDatabase(obj);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
            consumer.commitAsync();
        }
    }

    public Timestamp parseDateToTimestamp(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            Date parseDate = dateFormat.parse(date);
            return new Timestamp(parseDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendToDatabase(Object obj) {
        JSONObject jsonObject = (JSONObject) obj;
        JSONObject globalObject = (JSONObject) jsonObject.get("Global");
        JSONArray countriesObject = (JSONArray) jsonObject.get("Countries");
        String dateObject = String.valueOf(jsonObject.get("Date"));

        // -- Partie ou l'on gère l'objet Global pour l'insérer en base de donnée --
        int newRecoveredGlobal = Integer.parseInt(String.valueOf(globalObject.get("NewRecovered")));
        int totalConfirmedGlobal = Integer.parseInt(String.valueOf(globalObject.get("TotalConfirmed")));
        int newDeathsGlobal = Integer.parseInt(String.valueOf(globalObject.get("NewDeaths")));
        int totalDeathsGlobal = Integer.parseInt(String.valueOf(globalObject.get("TotalDeaths")));
        int totalRecoveredGlobal = Integer.parseInt(String.valueOf(globalObject.get("TotalRecovered")));
        Timestamp dateConvertedGlobal = parseDateToTimestamp(dateObject);
        Global global = new Global(totalConfirmedGlobal, newDeathsGlobal, totalDeathsGlobal, newRecoveredGlobal,
                totalRecoveredGlobal, dateConvertedGlobal);
        globalService.addGlobalToDatabase(global);

        // -- Partie ou l'on va enregistrer chaque donnée des pays de que l'API obtient --
        for(Object o : countriesObject) {
            if(o instanceof JSONObject) {
                String country = (String) ((JSONObject) o).get("Country");
                String countryCode = (String) ((JSONObject) o).get("CountryCode");
                String slug = (String) ((JSONObject) o).get("Slug");
                int newConfirmed = Integer.parseInt(String.valueOf(((JSONObject) o).get("NewConfirmed")));
                int totalConfirmed = Integer.parseInt(String.valueOf(((JSONObject) o).get("TotalConfirmed")));
                int newDeaths = Integer.parseInt(String.valueOf(((JSONObject) o).get("NewDeaths")));
                int totalDeaths = Integer.parseInt(String.valueOf(((JSONObject) o).get("TotalDeaths")));
                int totalRecovered = Integer.parseInt(String.valueOf(((JSONObject) o).get("TotalRecovered")));
                Timestamp dateConverted = parseDateToTimestamp(String.valueOf(((JSONObject) o).get("Date")));
                Country newCountry = new Country(country, countryCode, slug, newConfirmed, totalConfirmed,
                        newDeaths, totalDeaths, totalRecovered, dateConverted);
                countryService.addCountry(newCountry);
            }
        }

    }
}
