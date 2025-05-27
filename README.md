# Solr String Highlighter

A Gradle-based Java application that uses Apache Solr's UnifiedHighlighter to highlight search terms in text files.

## Features

- Highlights search terms in text files using Solr's advanced UnifiedHighlighter
- Supports Lucene query syntax (AND, OR, phrase queries, etc.)
- Command-line interface for easy use
- Fast in-memory indexing and searching
- HTML-formatted output with highlighted terms

## Project Structure

The project consists of just two main Java files as requested:

- `app/src/main/java/org/example/TextHighlighter.java` - Core highlighting functionality
- `app/src/main/java/org/example/HighlighterCLI.java` - Command-line interface

## Build and Run

### Prerequisites

- Java 21 or higher
- Gradle (included via wrapper)

### Building the Project

```bash
./gradlew build
```

### Running the Application

```bash
./gradlew run --args="\"<query>\" <file_path>"
```

### Examples

1. Simple term search:
```bash
./gradlew run --args="Solr ../sample.txt"
```

2. Boolean query with AND operator:
```bash
./gradlew run --args="\"highlighter AND text\" ../sample.txt"
```

3. Phrase search:
```bash
./gradlew run --args="\"machine learning\" sample.txt"
```

4. Complex query:
```bash
./gradlew run --args="\"Solr OR Lucene\" sample.txt"
```

### Creating a Standalone JAR

```bash
./gradlew build
# The executable JAR will be in app/build/distributions/
```

Then run with:
```bash
java -jar app/build/libs/app.jar "<query>" <file_path>
```

## Query Syntax

The application supports full Lucene query syntax:

- **Term queries**: `Solr`
- **Phrase queries**: `"machine learning"`
- **Boolean queries**: `highlighter AND text`
- **Wildcard queries**: `Solr*`
- **Field queries**: `content:search` (though only one field is indexed)

## Dependencies

- Apache Solr Core 9.4.1
- Apache Lucene Highlighter 9.4.1
- Apache Lucene Query Parser 9.4.1
- Apache Lucene Analysis Common 9.4.1

## Sample Output

```
Reading file: sample.txt
Searching for: "Solr"

================================================================================
HIGHLIGHTED RESULTS:
================================================================================
<b>Solr</b> powers the search and navigation features of many of the world's largest internet sites.
```

## Error Handling

The application handles common error scenarios:

- File not found
- Invalid query syntax  
- No matches found
- General I/O errors

All errors are reported with descriptive messages to help troubleshoot issues.
