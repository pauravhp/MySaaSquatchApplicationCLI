# MySaaSquatchApplicationCLI

A personalized CLI application built with Java and PicoCLI to showcase my skills for SaaSquatch.

## Features

- Carousel-style project navigation using arrow keys.
- Colorized terminal output with ANSI escape codes.
- Command suggestions via fuzzy matching (Levenshtein distance).
- Points-based gamification for engagement.
- Detailed project descriptions with terminal hyperlinks.

## Technologies Used

- Java
- PicoCLI
- JLine for arrow key detection
- Apache Commons Text for fuzzy matching

## How to Run

1. Clone this repository:
   git clone https://github.com/username/MySaaSquatchApplicationCLI.git
2. Navigate to the project directory:
   cd MySaaSquatchApplicationCLI
3. Download and Install all dependencies:
   mvn clean install
4. Package the project:
   mvn package
5. Run the application!
   java -cp target/PersonalizedJobCLI-1.0-SNAPSHOT.jar com.pauravhp.cli.InteractiveJobCLI
