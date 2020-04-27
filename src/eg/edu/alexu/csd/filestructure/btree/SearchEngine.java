package eg.edu.alexu.csd.filestructure.btree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.management.RuntimeErrorException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchEngine implements ISearchEngine {
	
   // private IBTree<String, IBTree<String, List<ISearchResult>>> searchTree;
	//public IBTree<String, HashSet<ISearchResult>> searchTree;
	public IBTree<String, List<ISearchResult>> searchTree;
    private HashSet<String>filePaths ;
    private HashSet<String> deletedIDs = new HashSet<>();
    public SearchEngine() {
        searchTree = new BTree<>(5);
        filePaths = new HashSet<>();
    }

    private void indexDoc(Element element) {
        String s[] = element.getTextContent().toLowerCase().split("\\s+");
        Arrays.sort(s);
        Integer frequency = 0;
        String word = "", id = element.getAttribute("id");
        for (int j = 0; j < s.length; j++) {
            if (word.equals(s[j])) {
                frequency++;
            } else if (word.equals("")) {
                word = s[j];
                frequency = 1;
            } else {
                addOrUpdateNewWord(word ,id,frequency);
                frequency = 1;
                word = s[j];
            }
        }
        if(!word.equals(""))addOrUpdateNewWord(word ,id,frequency);
    }
   // private void addOrUpdateNewWord(String word , String id , int frequency,IBTree<String, List<ISearchResult>>  tree){
    private void addOrUpdateNewWord(String word , String id , int frequency){
    	
//        System.out.println("Word is : "+ word +" in : " +id +" repeated : "+ frequency + " times");
        
        SearchResult searchResult;
        searchResult =new SearchResult(id, frequency);
        
        List<ISearchResult> results =  this.searchTree.search(word);
        if(results == null) {
        	List<ISearchResult> g = new ArrayList<>();
        	g.add(searchResult);
        	searchTree.insert(word, g);
        }
        else {
        	results.add(searchResult);
        }
       
        
        
        
//        List<ISearchResult> resultList = tree.search(word);
//        if (resultList == null) {
//            resultList = new ArrayList<>();
//            resultList.add(searchResult);
//            tree.insert(word, resultList);
//        } else {
//            resultList.add(searchResult);
//        }
    }
    @Override
    public void indexWebPage(String filePath) {
//        if(searchTree.search(filePath)!=null)return ;
    	if(filePaths.contains(filePath) == true) return;
        if (filePath == null) throw new RuntimeErrorException(new Error());
        List<SearchResult> ans = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document doc;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            doc = documentBuilder.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        filePaths.add(filePath);
        IBTree<String, List<ISearchResult>> wordsBtree = new BTree<>(5);
        NodeList nodeList = doc.getElementsByTagName("doc");
        for (int i = 0; i < nodeList.getLength(); i++) {
            indexDoc((Element) nodeList.item(i));
        }

    }

    @Override
    public void indexDirectory(String directoryPath) {
        if (directoryPath == null) throw new RuntimeErrorException(new Error());
  	  	try (Stream<Path> walk = Files.walk(Paths.get(directoryPath))) {

  			List<String> result = walk.filter(Files::isRegularFile)
  					.map(x -> x.toString()).collect(Collectors.toList());
  			
  			for(int i = 0 ; i < result.size() ; i++) {
  				this.indexWebPage(result.get(i));
  			}
  		} catch (IOException e) {
  			e.printStackTrace();
  		}
    }

    @Override
    public void deleteWebPage(String filePath) {
        if (filePath == null) throw new RuntimeErrorException(new Error());
        File file = new File(filePath);
        if (!file.exists()) return;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document doc;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            doc = documentBuilder.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        NodeList nodeList = doc.getElementsByTagName("doc");
        for (int i = 0; i < nodeList.getLength(); i++) {
        	String deletedId =  ((Element) nodeList.item(i)).getAttribute("id");
        	this.deletedIDs.add(deletedId); 	
        }
    }

    @Override
    public List<ISearchResult> searchByWordWithRanking(String word) {
        if (word == null) throw new RuntimeErrorException(new Error());
        List<ISearchResult> results =  this.searchTree.search(word);
        //Lazy Deletion,delete the search result if its web page is deleted.
        for(int i = 0 ; i < results.size() ; i++) {
//        	System.out.println(results.get(i).getId());
        	if(  deletedIDs.contains(results.get(i).getId() )  ) {
        		results.remove(i);
        	}
        }
        return results;
    }

    @Override
    public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
        if (sentence == null) throw new RuntimeErrorException(new Error());
        return null;
    }
}
