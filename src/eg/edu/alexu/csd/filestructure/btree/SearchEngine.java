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
    private IBTree<String, List<ISearchResult>> searchTree;
    private HashSet<String> deletedIDs = new HashSet<>();
    private HashSet<String> containedIDs = new HashSet<>();
    private HashSet<String> IndexedWebPage = new HashSet<>();
    public SearchEngine(int minDeg) {
        searchTree = new BTree<>(minDeg);
    }

    private void indexDoc(Element element) {
        String s[] = element.getTextContent().toLowerCase().split("\\s+");
        Arrays.sort(s);
        Integer frequency = 0;
        String word = "", id = element.getAttribute("id");
        containedIDs.add(id);
        for (int j = 0; j < s.length; j++) {
            if (word.equals(s[j])) {
                frequency++;
            } else if (word.equals("")) {
                word = s[j];
                frequency = 1;
            } else {
                addOrUpdateNewWord(word, id, frequency);
                frequency = 1;
                word = s[j];
            }
        }
        if (!word.equals("")) addOrUpdateNewWord(word, id, frequency);
    }
    private void addOrUpdateNewWord(String word, String id, int frequency) {
        SearchResult searchResult;
        searchResult = new SearchResult(id, frequency);
        List<ISearchResult> results = this.searchTree.search(word);
        if (results == null) {
            List<ISearchResult> g = new ArrayList<>();
            g.add(searchResult);
            searchTree.insert(word, g);
        } else {
            results.add(searchResult);
        }
    }

    @Override
    public void indexWebPage(String filePath) {
        if (filePath == null) throw new RuntimeErrorException(new Error());
        List<SearchResult> ans = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) throw new RuntimeErrorException(new Error());
        ;
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
        	if(!containedIDs.contains(((Element) nodeList.item(i)).getAttribute("id"))) {
        		 indexDoc((Element) nodeList.item(i));
        	}
        	if(deletedIDs.contains(((Element) nodeList.item(i)).getAttribute("id"))) {
        		deletedIDs.remove(((Element) nodeList.item(i)).getAttribute("id"));
        	}
        	 
        }
       
        IndexedWebPage.add(filePath);
    }

    @Override
    public void indexDirectory(String directoryPath) {
        if (directoryPath == null) throw new RuntimeErrorException(new Error());
        if(!new File(directoryPath).exists())throw new RuntimeErrorException(new Error());
        try (Stream<Path> walk = Files.walk(Paths.get(directoryPath))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());

            for (int i = 0; i < result.size(); i++) {
                this.indexWebPage(result.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWebPage(String filePath) {
        if (filePath == null) throw new RuntimeErrorException(new Error());
        if(!IndexedWebPage.contains(filePath)) {
        	throw new RuntimeErrorException(new Error());
        }
        List<SearchResult> ans = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) throw new RuntimeErrorException(new Error());
        ;
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
            String deletedId = ((Element) nodeList.item(i)).getAttribute("id");
            containedIDs.remove(deletedId);
            this.deletedIDs.add(deletedId);

        }
    }

    @Override
    public List<ISearchResult> searchByWordWithRanking(String word) {
        if (word == null) throw new RuntimeErrorException(new Error());
        word = word.toLowerCase();
        List<ISearchResult> results = this.searchTree.search(word);
        if (results == null) return new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            if (deletedIDs.contains(results.get(i).getId())) {
                results.remove(i);
                i--;
            }
        }
        return results;
    }

    @Override
    public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
        if (sentence == null) throw new RuntimeErrorException(new Error());
        String[] words = sentence.split("\\s+");
        List<ISearchResult> results = new ArrayList<>();
        HashMap<String, List<Integer>> hashMap = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            List<ISearchResult> wordResult = searchByWordWithRanking(words[i]);
            if (wordResult != null) {
                for (int j = 0; j < wordResult.size(); j++) {
                    ISearchResult result = wordResult.get(j);
                    if (hashMap.containsKey(result.getId())) {
                        hashMap.get(result.getId()).add(result.getRank());
                    } else {
                        List<Integer> l = new ArrayList<>();
                        l.add(result.getRank());
                        hashMap.put(result.getId(), l);
                    }
                }
            }
        }
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<Integer>> entry = (Map.Entry) it.next();
            String id = entry.getKey();
            List<Integer> fuckinListCantFindAName = entry.getValue();
            if (fuckinListCantFindAName.size() == words.length) {
                int min = Integer.MAX_VALUE;
                for (int k = 0; k < fuckinListCantFindAName.size(); k++) {
                    min = Math.min(min, fuckinListCantFindAName.get(k));
                }
                results.add(new SearchResult(id, min));
            }
        }
        return results;
    }
}