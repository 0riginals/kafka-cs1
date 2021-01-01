package org.m2tnsi.cs1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Cs1Application {

    public static void main(String[] args) {
        // -- Note à moi même: Ne pas s'acharner a faire une logique BDD ici, vu que ce n'est pas encore configuré :) --
        SpringApplication.run(Cs1Application.class, args);
    }

}
