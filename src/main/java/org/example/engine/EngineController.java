package org.example.engine;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.util.List;

public class EngineController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> suggestionList;

    private RedBlackTree suggestionsTree;

    public void initialize() {
        suggestionsTree = new RedBlackTree();
        // Daftar contoh untuk rekomendasi
        suggestionsTree.insert("Juara");
        suggestionsTree.insert("J4ara");
        suggestionsTree.insert("Java");
        suggestionsTree.insert("JavaFX");
        suggestionsTree.insert("JavaScript");
        suggestionsTree.insert("JDBC");
        suggestionsTree.insert("JUnit");
        suggestionsTree.insert("youtube.com");

        // Tambahkan listener untuk menangani input
        searchField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText();
        suggestionList.getItems().clear();

        if (query.isEmpty()) {
            suggestionList.setVisible(false);
            return;
        }

        List<String> suggestions = suggestionsTree.getSuggestions(query);
        suggestionList.getItems().addAll(suggestions);
        suggestionList.setVisible(!suggestionList.getItems().isEmpty());
    }

    @FXML
    private void search() {
        // Implementasi pencarian jika diperlukan
    }
}