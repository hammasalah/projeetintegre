module com.example.projetjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;

    // Root package
    exports com.example.projetjavafx.root;
    opens com.example.projetjavafx.root to javafx.fxml;

    // Controller package accesses
    opens com.example.projetjavafx.root.auth to javafx.fxml;
    opens com.example.projetjavafx.root.events to javafx.fxml;
    opens com.example.projetjavafx.root.explore to javafx.fxml;
    opens com.example.projetjavafx.root.organizer to javafx.fxml;
    opens com.example.projetjavafx.root.group to javafx.fxml;
    opens com.example.projetjavafx.root.profile to javafx.fxml;

    // CSS directories
    opens com.example.projetjavafx.auth.css to javafx.fxml;
    opens com.example.projetjavafx.events.css to javafx.fxml;
    opens com.example.projetjavafx.explore.css to javafx.fxml;
    opens com.example.projetjavafx.group.css to javafx.fxml;
    opens com.example.projetjavafx.organizer.css to javafx.fxml;
    opens com.example.projetjavafx.profile.css to javafx.fxml;
    opens com.example.projetjavafx.root.css to javafx.fxml;

    // Explicit exports for public controller classes
    exports com.example.projetjavafx.root.auth;
    exports com.example.projetjavafx.root.events;
    exports com.example.projetjavafx.root.explore;
    exports com.example.projetjavafx.root.organizer;
    exports com.example.projetjavafx.root.group;
    exports com.example.projetjavafx.root.profile;


}