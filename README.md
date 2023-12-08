# Assignment-2-Language-Translator-OBJ-
JavaFX Based On Google Cloud Translate API

Converting your HTML, CSS, and JavaScript code into a JavaFX application named `LanguageTranslator` involves several steps and detailed code writing. Given the complexity and the extent of the code, I will outline the core components and structure, but the full implementation would be too extensive for this format. Here's a structured approach for your JavaFX project:

### Project Structure

```
LanguageTranslator
│
└───src
    └───main
        ├───java
        │   └───com
        │       └───example
        │           └───languagetranslator
        │               ├───controller
        │               │   └───TranslatorController.java
        │               ├───model
        │               │   └───LanguageModel.java
        │               └───MainApp.java
        │
        └───resources
            ├───com
            │   └───example
            │       └───languagetranslator
            │           └───view
            │               └───translator.fxml
            └───css
                └───style.css
```


## Dependencies
- JavaFX: Provides the graphical framework for building the user interface.
- HttpClient (from `java.net.http`): Used for sending requests to the translation API.
- JSON.org: For parsing JSON responses from the API.

## Packages
- `com.example.languagetranslator.controller`: Contains `TranslatorController.java`, which manages the UI interactions and API communication.
- `com.example.languagetranslator.model`: Intended for data models (currently not used).
- `com.example.languagetranslator`: Contains `Main.java`, the entry point of the application, setting up the primary stage and loading the FXML.

## Running the Application
Ensure you have JavaFX and the other dependencies installed. Compile and run `Main.java` to launch the application.

## Features
- Translation between multiple languages.
- Dark and light mode toggle.
- Interactive UI.

## Contributing
Contributions to the Language Translator are welcome. Please follow the standard fork, branch, and pull request workflow.

---

Author: [Harjot Singh]
