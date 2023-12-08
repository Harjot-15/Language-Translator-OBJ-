package com.example.languagetranslator.controller;

        import javafx.fxml.FXML;
        import javafx.scene.control.*;
        import javafx.scene.layout.BorderPane;
        import javafx.application.Platform;
        import java.net.http.HttpClient;
        import java.net.http.HttpRequest;
        import java.net.http.HttpResponse;
        import java.net.URI;
        import java.util.Map;
        import java.util.HashMap;
        import org.json.JSONObject;
        import org.json.JSONArray;

public class TranslatorController {

    @FXML
    private ComboBox<String> sourceLanguage;
    @FXML
    private ComboBox<String> targetLanguage;
    @FXML
    private TextArea sourceText;
    @FXML
    private TextArea translatedText;
    @FXML
    private BorderPane root;
    @FXML
    private ToggleButton darkModeToggle;

    // Map to store language options
    private final Map<String, String> languageOptions = new HashMap<>();

    @FXML
    public void initialize() {
        // Initialize the controller when the FXML loads

        // Populate the source and target language dropdowns
        populateLanguageOptions();

        // Listen for changes in selected languages
        sourceLanguage.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> ensureDifferentLanguages());
        targetLanguage.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> ensureDifferentLanguages());

        // Listen for changes in the source text and trigger translation
        sourceText.textProperty().addListener((observable, oldValue, newValue) -> translateText());

        // Listen for changes in dark mode toggle
        darkModeToggle.selectedProperty().addListener((observable, oldValue, newValue) -> toggleModeInternal(newValue));

        // Apply additional styles programmatically
        sourceLanguage.getStyleClass().add("combo-box");
        targetLanguage.getStyleClass().add("combo-box");
        sourceText.getStyleClass().add("text-area");
        translatedText.getStyleClass().add("text-area");
    }

    // Populates the language options dropdowns
    private void populateLanguageOptions() {
        languageOptions.put("en", "English");
        languageOptions.put("es", "Spanish");
        languageOptions.put("pa", "Punjabi");
        languageOptions.put("ur", "Urdu");
        languageOptions.put("fa", "Persian");
        languageOptions.put("fr", "French");
        // Add other languages here

        // Add language options to the dropdowns and set default values
        sourceLanguage.getItems().addAll(languageOptions.values());
        targetLanguage.getItems().addAll(languageOptions.values());

        sourceLanguage.setValue("English");
        targetLanguage.setValue("Punjabi");
    }

    // Ensures that the source and target languages are different
    private void ensureDifferentLanguages() {
        if (sourceLanguage.getValue().equals(targetLanguage.getValue())) {
            // Logic to handle when source and target languages are the same
            // You can add code here to change the target language or display an error message.
        }
    }

    // Swaps the selected source and target languages
    public void swapLanguages() {
        String temp = sourceLanguage.getValue();
        sourceLanguage.setValue(targetLanguage.getValue());
        targetLanguage.setValue(temp);
        translateText();
    }

    // Translates the text from the source language to the target language
    private void translateText() {
        String text = sourceText.getText().trim();
        if (text.isEmpty()) {
            translatedText.setText("");
            return;
        }

        // Get the source and target language codes
        String sourceLang = getKeyByValue(languageOptions, sourceLanguage.getValue());
        String targetLang = getKeyByValue(languageOptions, targetLanguage.getValue());


        String apiKey = "AIzaSyAeQL4DK5vqe8wf69KMaVlVzgfK659yD8A";

        // Build the API request URL and request body
        String url = String.format("https://translation.googleapis.com/language/translate/v2?key=%s", apiKey);
        String requestBody = String.format("{\"q\": \"%s\", \"source\": \"%s\", \"target\": \"%s\", \"format\": \"text\"}", text, sourceLang, targetLang);

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Send an asynchronous request to the Google Translate API
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(responseBody -> {
                        // Parse the translation from the API response and update the translatedText field
                        String translation = parseTranslation(responseBody);
                        Platform.runLater(() -> translatedText.setText(translation));
                    })
                    .exceptionally(e -> {
                        // Handle errors that may occur during the API request
                        e.printStackTrace();
                        return null;
                    });
        } catch (Exception e) {
            // Handle exceptions that may occur during the API request
            e.printStackTrace();
        }
    }

    // Helper method to get the key from a map based on the value
    private String getKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    // Toggle between light and dark modes
    private void toggleModeInternal(boolean darkMode) {
        // Clear previous styles
        root.getStyleClass().remove("light-mode");
        root.getStyleClass().remove("dark-mode");

        // Apply new style based on the darkMode flag
        if (darkMode) {
            root.getStyleClass().add("dark-mode");
        } else {
            root.getStyleClass().add("light-mode");
        }
    }

    // Toggle dark mode when the toggle button is clicked
    public void toggleDarkMode() {
        boolean darkMode = darkModeToggle.isSelected();
        if (darkMode) {
            darkModeToggle.setText("Light Mode");
        } else {
            darkModeToggle.setText("Dark Mode");
        }
        toggleModeInternal(darkMode);
    }

    // Handle translate button click event
    public void handleTranslateAction() {
        translateText();
    }

    // Parse the translation from the API response
    private String parseTranslation(String responseBody) {
        try {
            JSONObject json = new JSONObject(responseBody);

            if (json.has("data")) {
                JSONObject data = json.getJSONObject("data");

                if (data.has("translations")) {
                    JSONArray translations = data.getJSONArray("translations");

                    if (translations.length() > 0) {
                        JSONObject translationObject = translations.getJSONObject(0);

                        if (translationObject.has("translatedText")) {
                            return translationObject.getString("translatedText");
                        } else {
                            return "Translation not found in response";
                        }
                    } else {
                        return "No translations found in response";
                    }
                } else {
                    return "Field 'translations' not found in response";
                }
            } else {
                return "Field 'data' not found in response";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error parsing translation. Response body: " + responseBody);
            return "Error parsing translation: " + e.getMessage();
        }
    }
}

