module edu.miracosta.cs112.moviedatabaseproject_v3 {
    requires javafx.fxml;
    requires javafx.controls;
    requires org.slf4j;

    opens edu.miracosta.cs112.moviedatabaseproject_v3.controller to javafx.fxml;
    exports edu.miracosta.cs112.moviedatabaseproject_v3;
}
