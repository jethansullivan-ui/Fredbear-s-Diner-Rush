# Fredbear's Diner Rush

A competitive split-screen arcade maze game built in JavaFX where Fredbear and Springbonnie race to clear their respective halves of the board in parallel.

## Prerequisites & External Dependencies

To build and run this application, ensure you have the following installed and configured:

* **Java Development Kit (JDK):** Java 21 or newer (configured for Java 25)
* **Apache Maven:** For dependency management and building the project
* **External Libraries (Managed via `pom.xml`):**
  * `org.openjfx:javafx-controls` (v21) – UI components and canvas rendering
  * `org.openjfx:javafx-fxml` (v21) – JavaFX layout support
  * `org.openjfx:javafx-media` (v21) – Audio playback and pitch modulation (`AudioClip`)

## Setup & Configuration

1. **Asset Placement:**
   Ensure all visual sprites (`.gif`, `.png`) and the audio file (`munch_1.mp3`) are placed inside the resources directory:
   ```text
   src/main/resources/com/fredbeardinerrush/
   ```
2. **Tally Persistence:**
   The game automatically creates and updates a `tally_data.txt` file in the root directory upon the completion of a round to track player win counts. No manual configuration is required.

## How to Compile and Run

1. **Navigate to the project root directory** (where `pom.xml` is located) in your terminal.
2. **Compile the project and bundle resources:**
   ```bash
   mvn clean compile
   ```
3. **Run the application using the JavaFX Maven plugin:**
   ```bash
   mvn javafx:run
   ```

## Controls

* **Fredbear (Bottom Half):** `W`, `A`, `S`, `D`
* **Springbonnie (Top Half):** `Up`, `Down`, `Left`, `Right`
* **Restart Round:** Press `R` after a winner is declared
