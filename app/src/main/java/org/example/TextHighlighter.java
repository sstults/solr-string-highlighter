package org.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.uhighlight.UnifiedHighlighter;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * TextHighlighter uses Solr's UnifiedHighlighter to highlight search terms in text content.
 */
public class TextHighlighter {
    
    private static final String FIELD_NAME = "content";
    private final Analyzer analyzer;
    
    public TextHighlighter() {
        this.analyzer = new StandardAnalyzer();
    }
    
    /**
     * Highlights the search query terms in the given text content.
     * 
     * @param textContent The text content to search and highlight
     * @param queryString The search query string
     * @return The highlighted text with search terms wrapped in HTML tags
     * @throws IOException If there's an error with indexing or searching
     * @throws ParseException If the query string cannot be parsed
     */
    public String highlightText(String textContent, String queryString) throws IOException, ParseException {
        // Create an in-memory index
        Directory directory = new ByteBuffersDirectory();
        
        try (IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(analyzer))) {
            // Create a document and add the text content
            Document doc = new Document();
            doc.add(new TextField(FIELD_NAME, textContent, Field.Store.YES));
            writer.addDocument(doc);
        }
        
        // Search and highlight
        try (DirectoryReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            
            // Parse the query
            QueryParser parser = new QueryParser(FIELD_NAME, analyzer);
            Query query = parser.parse(queryString);
            
            // Search for documents
            TopDocs topDocs = searcher.search(query, 10);
            
            if (topDocs.scoreDocs.length == 0) {
                return "No matches found for query: " + queryString;
            }
            
            // Create the UnifiedHighlighter
            UnifiedHighlighter highlighter = new UnifiedHighlighter(searcher, analyzer);
            highlighter.setMaxLength(10000000); // Set a large but valid max length
            
            // Highlight the text
            String[] highlights = highlighter.highlight(FIELD_NAME, query, topDocs);
            
            if (highlights.length > 0 && highlights[0] != null) {
                return highlights[0];
            } else {
                return "Query matched but no highlights generated.";
            }
        }
    }
    
    /**
     * Reads text content from a file.
     * 
     * @param filePath The path to the file to read
     * @return The content of the file as a string
     * @throws IOException If the file cannot be read
     */
    public String readTextFile(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath));
    }
}
