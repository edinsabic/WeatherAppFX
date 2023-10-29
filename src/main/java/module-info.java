module gh.project.weatherappfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens gh.project.weatherappfx to javafx.fxml;
    exports gh.project.weatherappfx;
}