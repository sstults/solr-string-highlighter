package org.example;

import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

/**
 * Command-line interface for the text highlighter utility.
 * Usage: java HighlighterCLI <query> <file_path>
 */
public class HighlighterCLI {
    
    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
            System.exit(1);
        }
        
        String query = args[0];
        String filePath = args[1];
        
        TextHighlighter highlighter = new TextHighlighter();
        
        try {
            // Read the file content
            System.out.println("Reading file: " + filePath);
            String textContent = highlighter.readTextFile(filePath);
            
            // Perform highlighting
            System.out.println("Searching for: \"" + query + "\"");
            System.out.println("\n" + "=".repeat(80));
            System.out.println("HIGHLIGHTED RESULTS:");
            System.out.println("=".repeat(80));
            
            String highlightedText = highlighter.highlightText(textContent, query);
            System.out.println(highlightedText);
            
        } catch (IOException e) {
            System.err.println("Error reading file '" + filePath + "': " + e.getMessage());
            System.exit(1);
        } catch (ParseException e) {
            System.err.println("Error parsing query '" + query + "': " + e.getMessage());
            System.err.println("Make sure your query uses valid Lucene query syntax.");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void printUsage() {
        System.out.println("Text Highlighter - Highlights search terms in text files using Solr's UnifiedHighlighter");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  java -jar app.jar <query> <file_path>");
        System.out.println("  ./gradlew run --args=\"<query> <file_path>\"");
        System.out.println();
        System.out.println("Arguments:");
        System.out.println("  <query>     The search query (supports Lucene query syntax)");
        System.out.println("  <file_path> Path to the text file to search");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  ./gradlew run --args=\"\\\"hello world\\\" sample.txt\"");
        System.out.println("  ./gradlew run --args=\"java AND programming document.txt\"");
        System.out.println("  java -jar app.jar \"machine learning\" research.txt");
        System.out.println();
        System.out.println("Note: Query terms will be highlighted with HTML <b> tags in the output.");
    }
}
