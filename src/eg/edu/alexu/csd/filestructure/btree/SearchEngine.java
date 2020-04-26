package eg.edu.alexu.csd.filestructure.btree;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

public class SearchEngine implements ISearchEngine {

	@Override
	public void indexWebPage(String filePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void indexDirectory(String directoryPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteWebPage(String filePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ISearchResult> searchByWordWithRanking(String word) {
		// TODO Auto-generated method stub
		if(word == null) {
			Error e = null;			
			throw new RuntimeErrorException(e);		
		}
		if(word.compareTo("") == 0) {
			return new ArrayList<>();
		}
		return null;
	}

	@Override
	public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
		// TODO Auto-generated method stub
		if(sentence == null) {
			Error e = null;
			return new ArrayList<>();
			//throw new RuntimeErrorException(e);
		}
		return null;
	}

}
