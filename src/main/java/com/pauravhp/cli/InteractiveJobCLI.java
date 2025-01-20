package com.pauravhp.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.text.similarity.LevenshteinDistance;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

@Command(name = "InteractiveJobCLI", version = "1.0", description = "A personalized interactive CLI for SaaSquatch.", mixinStandardHelpOptions = true)
public class InteractiveJobCLI implements Runnable {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    private static final String[] AVAILABLE_COMMANDS = { "about", "resume", "projects", "stats", "points", "onboard",
            "help", "quote", "exit", "tech" };

    private static final String UNKNOWN_COMMAND = "Unknown command. Type 'help' for a list of commands.";

    private static int commandCount = 0;
    private static long startTime = System.currentTimeMillis();

    private static int points = 0;

    public static void awardPoints(int point) {
        points += point;
    }

    private static String getClosestMatch(String input) {
        LevenshteinDistance distance = new LevenshteinDistance();

        return Arrays.stream(AVAILABLE_COMMANDS)
                .min((cmd1, cmd2) -> {
                    int dist1 = distance.apply(input, cmd1);
                    int dist2 = distance.apply(input, cmd2);
                    return Integer.compare(dist1, dist2);
                })
                .orElse(UNKNOWN_COMMAND);
    }

    public static void incrementCommandCount() {
        commandCount++;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to MySaaSquatchApplication CLI!");
        System.out.println(
                " ____              ____                    _       _     \n" + //
                        "/ ___|  __ _  __ _/ ___|  __ _ _   _  __ _| |_ ___| |__  \n" + //
                        "\\___ \\ / _` |/ _` \\___ \\ / _` | | | |/ _` | __/ __| '_ \\ \n" + //
                        " ___) | (_| | (_| |___) | (_| | |_| | (_| | || (__| | | |\n" + //
                        "|____/ \\__,_|\\__,_|____/ \\__, |\\__,_|\\__,_|\\__\\___|_| |_|\n" + //
                        "                            |_|                          ");
        System.out.println(
                "This personalized CLI was built to showcase my technical skills and enthusiasm for joining the SaaSquatch team.");
        System.out.println(
                "SaaSquatch is revolutionizing partnership management, and I hope to contribute to that journey.");
        System.out.println(
                "Type " + ANSI_YELLOW + "'help'" + ANSI_RESET + " to see available commands, " +
                        ANSI_GREEN + "'onboard'" + ANSI_RESET + " for a guide on how to get started on this CLI, or " +
                        ANSI_RED + "'exit'" + ANSI_RESET + " to quit.");

        InteractiveJobCLI cli = new InteractiveJobCLI();
        CommandLine cmd = new CommandLine(cli);

        // Custom execution strategy to count valid commands
        cmd.setExecutionStrategy(parseResult -> {
            try {
                // Execute the command
                int result = new CommandLine.RunLast().execute(parseResult);
                // Increment only for successfully executed commands
                incrementCommandCount();
                awardPoints(5 + commandCount);
                return result;
            } catch (Exception e) {
                System.out.println("An error occurred while processing the command. Please try again.");
                return -1;
            }
        });

        cmd.addSubcommand("help", new CommandLine.HelpCommand());

        while (true) {
            System.out.print("> "); // Prompt for user input
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            // Suggest closest match for invalid commands
            String closestMatch = getClosestMatch(input);
            if (!Arrays.asList(AVAILABLE_COMMANDS).contains(input)) {
                System.out.println("Inavlid command. Did you mean \u001B[32m" + closestMatch + "\u001B[0m?");
                continue; // Skip to next iteration to avoid executing invalid commands
            }

            // Execute valid commands
            try {
                cmd.execute(input.split(" "));
            } catch (Exception e) {
                System.out.println("An error occurred while processing the command. Please try again.");
            }
        }

        scanner.close();
    }

    @Override
    public void run() {
        System.out.println("Type 'help' to see available commands.");
    }

    @Command(name = "about", description = "Displays information about SaaSquatch and my interest in the company.")
    public void about() {
        System.out.println(ANSI_BLUE + "About SaaSquatch:" + ANSI_RESET);
        System.out
                .println("SaaSquatch is the leading SaaS platform for partnership management, enabling businesses to " +
                        "optimize and automate partnerships across affiliates, influencers, and more.\n");

        System.out.println(ANSI_BLUE + "About Myself:" + ANSI_RESET);
        System.out
                .println(
                        "I am a fourth-year Computer Science Honours student at University of Victoria. I am from India and was born and raised in Indonesia, Jakarta. I am always heads down in some project, exploring new technologies and possibilities for side projects. The goal is to turn project -> product. I also really enjoy playing basketball, football and boardgames in my free time.\n");

        System.out.println(ANSI_BLUE + "Why I'm excited about SaaSquatch:" + ANSI_RESET);
        System.out.println("I admire SaaSquatch's cutting-edge technology and commitment to customer success. " +
                "With my experience in building scalable software solutions, debugging complex systems, " +
                "and delivering high-quality code, I am eager to contribute to your mission of empowering " +
                "top-tier brands and hope I can make a lasting impact.");
    }

    @Command(name = "onboard", description = "Guides you through the CLI features.")
    public void onboard() {
        System.out.println("Welcome to MySaaSquatchApplication CLI!");
        System.out.println("Step 1: Type 'about' to learn about the company and why I am excited to join SaaSquatch!");
        System.out.println("Step 2: Type 'projects' to explore my work.");
        System.out.println("Step 3: Type 'resume' to see my experiences.");
        System.out.println("Type 'help' at any time to see all available commands.");
    }

    @Command(name = "projects", description = "View my projects in a carousel.")
    public void projects() throws Exception {
        // Project details
        String[] projects = {
                ANSI_GREEN + "1. Taxonomy Management Page Rewrite:" + ANSI_RESET +
                        "\n   Refactored a legacy web app using ReactTS, enhancing scalability and maintainability.\n" +
                        "   LinkedIn Writeup: " + ANSI_BLUE
                        + "\u001B]8;;https://www.linkedin.com/in/paurav-h-param-025055264/details/projects/#:~:text=well%20as%20documentation.-,Taxonomy%20Management%20Page%20Rewrite%20%40%20Ocean%20Networks%20Canada,-Taxonomy%20Management%20Page\u001B\\Link\u001B]8;;\u001B\\"
                        + ANSI_RESET,

                ANSI_GREEN + "2. BoarDex:" + ANSI_RESET +
                        "\n   Built a board game encyclopedia with React, Firebase, and Material-UI.\n" +
                        "   GitHub: " + ANSI_BLUE
                        + "\u001B]8;;https://github.com/pauravhp/boardex\u001B\\Link\u001B]8;;\u001B\\" + ANSI_RESET,

                ANSI_GREEN + "3. UTTT Hackathon Automation Suite:" + ANSI_RESET +
                        "\n   Automated tournament management for UVicAI’s Ultimate Tic-Tac-Toe hackathon.\n" +
                        "   GitHub: " + ANSI_BLUE
                        + "\u001B]8;;https://github.com/pauravhp/uttt-tournament-automation\u001B\\Link\u001B]8;;\u001B\\"
                        + ANSI_RESET,
                ANSI_GREEN + "4. Vaya: Your Personalized Restaurant Recommender (Global Hackathon Winner):" + ANSI_RESET
                        +
                        "\n   Developed a real-time location-based restaurant recommender mobile app using React Native, integrating the Google Places API and Julep AI to provide personalized dining options based on user preferences and dietary restrictions. \n"
                        +
                        "   GitHub: " + ANSI_BLUE
                        + "\u001B]8;;https://github.com/AbhayAnoopC/vaya-vaya?tab=readme-ov-file#vaya-your-personalized-restaurant-recommender\u001B\\Link\u001B]8;;\u001B\\"
                        + ANSI_RESET
        };

        // Setup the terminal
        Terminal terminal = TerminalBuilder.builder().system(true).build();

        int currentIndex = 0;
        boolean running = true;

        System.out.println("Use arrow keys and click enter to navigate. Press 'q' to quit.\n");

        while (running) {
            // Clear the screen and display the current project with pagination
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("[" + (currentIndex + 1) + " / " + projects.length + "]\n");
            System.out.println(projects[currentIndex]);
            System.out.println("\nNavigate: ← (left), → (right), q (quit)");

            // Capture input
            int input = terminal.reader().read();

            if (input == '\033') { // Detect escape sequence for arrow keys
                terminal.reader().read(); // Skip the '[' character
                int arrowKey = terminal.reader().read();
                System.out.println(arrowKey);
                if (arrowKey == 'D') { // Left arrow
                    currentIndex = (currentIndex == 0) ? projects.length - 1 : currentIndex - 1;
                } else if (arrowKey == 'C') { // Right arrow
                    currentIndex = (currentIndex == projects.length - 1) ? 0 : currentIndex + 1;
                }
            } else if (input == 'q') { // Quit
                running = false;
                System.out.println("Exiting project carousel.");
            }
        }

        terminal.close();
    }

    @Command(name = "resume", description = "Displays my key skills and experiences.")
    public void resume() {
        System.out.println(ANSI_BLUE + "Paurav's Resume:" + ANSI_RESET);
        System.out.println("- " + ANSI_GREEN + "Skills:" + ANSI_RESET
                + " Java, Spring, SQL, PostgreSQL, JavaScript, ReactTS, Jest, REST APIs, Chrome DevTools, Git.");
        System.out.println(
                "- " + ANSI_GREEN + "Experience:" + ANSI_RESET + " Software Engineer Intern at Ocean Networks Canada.");
        System.out.println("    - Tech Stack worked with: Java, PostgreSQL, React, TypeScript, JavaScript.");
        System.out.println("    - Led ReactTS web app development with Agile practices.");
        System.out.println(
                "    - Enhanced codebase readability by refactoring several hundred lines of Java code written in the OSGI framework, resulting in improved maintainability and scalability and reduced deployment time by 20 seconds.");
        System.out.println("- " + ANSI_GREEN + "Achievements:" + ANSI_RESET
                + " Global Hackathon winner, Events and Outreach Executive for UVicAI.");
        System.out.println("For more, visit: " + ANSI_YELLOW + "https://github.com/pauravhp" + ANSI_RESET + " or "
                + ANSI_YELLOW + "https://pauravhp.github.io/my-portfolio/" + ANSI_RESET);
    }

    @Command(name = "stats", description = "Displays usage statistics.")
    public void stats() {
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println("Total Commands Executed: " + commandCount);
        System.out.println("Session Duration: " + elapsedTime + " seconds.");
    }

    @Command(name = "points", description = "Displays your current points and level.")
    public void points() {
        System.out.println("You have " + points + " points!");
        System.out.println("Keep going to score more points!");
    }

    @Command(name = "tech", description = "Learn about the technologies and features implemented in this CLI.")
    public void tech() {
        System.out.println(ANSI_BLUE + "Technologies Used:" + ANSI_RESET);
        System.out.println("- Java, PicoCLI library for CLI functionality.");
        System.out.println("- Apache Commons Text for fuzzy matching.");
        System.out.println("- ANSI escape codes for colorized output.");
        System.out.println("\nFeatures include:");
        System.out.println("- Suggestion for incorrect command input.");
        System.out.println("- Usage statistics tracking.");
        System.out.println("- Points-based gamification.");
        System.out.println("- Project explorer.");
    }

    @Command(name = "quote", description = "Displays a random inspirational quote that I resonate with.")
    public void quote() {
        String[] quotes = {
                "'You do not lack resources, you lack strategy.'",
                "'For too many trees, don't lose sight of the forest.'",
                "'The more you sweat in practice, the less you bleed at war.'",
                "Either you run the day, or the day runs you"
        };
        System.out.println(quotes[(int) (Math.random() * quotes.length)]);
    }

}
