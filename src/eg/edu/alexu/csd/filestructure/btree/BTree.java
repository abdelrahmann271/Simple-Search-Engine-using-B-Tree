package eg.edu.alexu.csd.filestructure.btree;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class BTree<K extends Comparable<K>, V> implements IBTree<K, V> {
	
	private int degree;
	private int maxKeys;
	private int minKeys;
	private IBTreeNode<K, V> root = null;
	private IBTreeNode<K, V> NodeToInsertIn = null;
	private IBTreeNode<K, V> NodeToSearchIn = null;
	private Vector<IBTreeNode<K, V>> parents = new Vector<>();
	
	public BTree(int degree) {
		// TODO Auto-generated constructor stub
		this.degree = degree;
		root = new BTreeNode<K,V>(degree);
		this.maxKeys = degree-1;
		this.minKeys = (int) (Math.ceil((double)degree/2)-1);
	}
	
	@Override
	public int getMinimumDegree() {
		// TODO Auto-generated method stub
		return this.degree;
	}

	@Override
	public IBTreeNode<K, V> getRoot() {
		// TODO Auto-generated method stub
		return this.root;
	}

	@Override
	public void insert(K key, V value) {
		// TODO Auto-generated method stub
		searchForInsert(this.root, key);
		NodeToInsertIn.getKeys().add(key);
		NodeToInsertIn.getValues().add(value);
		
		//Inserting at Leaf.
		for(int i = NodeToInsertIn.getKeys().size()-1 ; i > 0 ; i--) {
			
			if( NodeToInsertIn.getKeys().get(i).compareTo(NodeToInsertIn.getKeys().get(i-1)) < 0 ) {
				
				K temp =  NodeToInsertIn.getKeys().get(i);
				NodeToInsertIn.getKeys().set(i,  NodeToInsertIn.getKeys().get(i-1));
				NodeToInsertIn.getKeys().set(i-1, temp);
				
				V temp2 =  NodeToInsertIn.getValues().get(i);
				NodeToInsertIn.getValues().set(i,  NodeToInsertIn.getValues().get(i-1));
				NodeToInsertIn.getValues().set(i-1, temp2);
				
			}
			else {
				break;
			}
			
		}
		int size = NodeToInsertIn.getKeys().size();
		if( size  > maxKeys) {
			int median = ( size-1 ) / 2 ;
			
			K medianKey = NodeToInsertIn.getKeys().get(median);
			V medianValue = NodeToInsertIn.getValues().get(median);
			
			List<K> leftKeys = new ArrayList<K>();
			List<V> leftValues = new ArrayList<V>();
			List<K> rightKeys = new ArrayList<K>();
			List<V> rightValues = new ArrayList<V>();
			
			for(int k = 0 ; k < median ; k++) {
				leftKeys.add(NodeToInsertIn.getKeys().get(k));
				leftValues.add(NodeToInsertIn.getValues().get(k));
			}
			for(int k = median + 1 ; k < size ; k++) {
				rightKeys.add(NodeToInsertIn.getKeys().get(k));
				rightValues.add(NodeToInsertIn.getValues().get(k));
			}
			
			IBTreeNode<K, V> leftNode = new BTreeNode<>(degree);
			IBTreeNode<K, V> rightNode = new BTreeNode<>(degree);
			
			leftNode.setLeaf(true);
			rightNode.setLeaf(true);
			
			leftNode.setKeys(leftKeys);
			leftNode.setValues(leftValues);
			rightNode.setKeys(rightKeys);
			rightNode.setValues(rightValues);
			
			insert_fixup( medianKey, medianValue, leftNode, rightNode	);
			
		}
		
		parents.clear();
		
	}
	
	
	private void insert_fixup(K key , V value , IBTreeNode<K, V> left , IBTreeNode<K, V> right ) {
		
		IBTreeNode<K, V> currentParent = null;
		Boolean flag = true;
		//check if parent is null , its root.
//		System.out.println(parents.size());
		if(parents.size() == 0) {
			currentParent = new BTreeNode<K,V>(this.degree);
			this.root = currentParent;
			currentParent.setLeaf(false);
			flag = false;
		} 
		else {
			currentParent = parents.get(parents.size()-1);
			parents.remove(parents.size()-1);
		}
		

		
		currentParent.getKeys().add(key);
		currentParent.getValues().add(value);
		int i;
		for( i = currentParent.getKeys().size()-1 ; i > 0 ; i--) {
			
			if( currentParent.getKeys().get(i).compareTo(currentParent.getKeys().get(i-1)) < 0 ) {
				
				K temp =  currentParent.getKeys().get(i);
				currentParent.getKeys().set(i,  currentParent.getKeys().get(i-1));
				currentParent.getKeys().set(i-1, temp);
				
				V temp2 =  currentParent.getValues().get(i);
				currentParent.getValues().set(i,  currentParent.getValues().get(i-1));
				currentParent.getValues().set(i-1, temp2);
			}
			else {
				break;
			}
			
		}
		//update children.
		//because we inserted in a not leaf node.
		//change i child and add the right child at the end and put it to i+1 .
		if(flag) {
			currentParent.getChildren().set(i, left);
			currentParent.getChildren().add(right);
			for(int j = currentParent.getChildren().size()-1 ; j>=i+2 ; j--) {
				IBTreeNode<K, V> temp = currentParent.getChildren().get(j-1);
				currentParent.getChildren().set(j-1, currentParent.getChildren().get(j));
				currentParent.getChildren().set(j, temp);
			}
		}
		else {
			currentParent.getChildren().add(left);
			currentParent.getChildren().add(right);
		}

		//check if it overloaded
		int size = currentParent.getKeys().size();
		//Don't forget to insert children at splitting!.
		if( size  > maxKeys) {
			int median = ( size-1 ) / 2 ;
			
			K medianKey = currentParent.getKeys().get(median);
			V medianValue = currentParent.getValues().get(median);
			
			List<K> leftKeys = new ArrayList<K>();
			List<V> leftValues = new ArrayList<V>();
			List<K> rightKeys = new ArrayList<K>();
			List<V> rightValues = new ArrayList<V>();
			List<IBTreeNode<K, V>> leftChildren = new ArrayList<IBTreeNode<K,V>>();
			List<IBTreeNode<K, V>> rightChildren = new ArrayList<IBTreeNode<K,V>>();
			
			for(int k = 0 ; k < median ; k++) {
				leftKeys.add(currentParent.getKeys().get(k));
				leftValues.add(currentParent.getValues().get(k));
			}
			for(int k = median + 1 ; k < size ; k++) {
				rightKeys.add(currentParent.getKeys().get(k));
				rightValues.add(currentParent.getValues().get(k));
			}
			
			for(int k = 0 ; k <= median ; k++) {
				leftChildren.add(currentParent.getChildren().get(k) );
			}
			for(int k = median+1 ; k < currentParent.getChildren().size() ; k++) {
				rightChildren.add(currentParent.getChildren().get(k) );
			}
			
			IBTreeNode<K, V> leftNode = new BTreeNode<>(degree);
			IBTreeNode<K, V> rightNode = new BTreeNode<>(degree);
			
			leftNode.setLeaf(false);
			rightNode.setLeaf(false);
			
			leftNode.setKeys(leftKeys);
			leftNode.setValues(leftValues);
			leftNode.setChildren(leftChildren);
			
			rightNode.setKeys(rightKeys);
			rightNode.setValues(rightValues);
			rightNode.setChildren(rightChildren);
			
			insert_fixup(medianKey, medianValue, leftNode, rightNode);
			
		}
		
		return;
		
	}
	
	private void searchForInsert(IBTreeNode<K, V> node , K key )  {
		if(node.getKeys().size() == 0) {	//Insert at root.
			NodeToInsertIn = this.root;
			
		}
		for(int i = 0 ; i < node.getKeys().size() ; i++) {
			//Continue searching into left.
			if(node.getKeys().get(i).compareTo(key) == 0) {
				NodeToSearchIn = node;
			}
			if(node.isLeaf() == false  && node.getKeys().get(i).compareTo(key) > 0) {
				
				//currentParent = node;
				parents.add(node);
				searchForInsert(node.getChildren().get(i), key);
				return;
				
			}//Continue searching into right.
			else if ( node.isLeaf() == false && i == node.getKeys().size()-1 && node.getKeys().get(i).compareTo(key) < 0 ) {
				
				//currentParent = node;
				parents.add(node);
				searchForInsert(node.getChildren().get(i+1), key);
				return;
				
			}//Key will be inserted in this node, Insert instead of this key and fix-up if necessary.
			else if (node.isLeaf() == true && node.getKeys().get(i).compareTo(key) > 0 ) {
				
				NodeToInsertIn = node;
				return;
				
			}//Insert at the end cause all the elements are smaller than it and it's the end.
			else if (node.isLeaf() == true && i == node.getKeys().size()-1 && node.getKeys().get(i).compareTo(key) < 0 ) {
				
				NodeToInsertIn = node;
				return;
				
			}
		}
	}
	
	@Override
	public V search(K key) {
		// TODO Auto-generated method stub
		NodeToSearchIn = null;
		searchForInsert(this.root, key);
		if(NodeToSearchIn == null) {
			return null;
		}
		for(int i = 0 ; i < NodeToSearchIn.getKeys().size() ; i++) {
			if(NodeToSearchIn.getKeys().get(i).compareTo(key) == 0) {
				parents.clear();
				return NodeToSearchIn.getValues().get(i);
			}
		}
		parents.clear();
		return null;
	}

	@Override
	public boolean delete(K key) {
		// TODO Auto-generated method stub
		return false;
	}
	


}
