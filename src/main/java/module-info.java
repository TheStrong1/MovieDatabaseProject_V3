module edu.miracosta.cs112.moviedatabaseproject_v3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.miracosta.cs112.moviedatabaseproject_v3 to javafx.fxml;
    exports edu.miracosta.cs112.moviedatabaseproject_v3;
}